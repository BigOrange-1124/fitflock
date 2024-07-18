package com.orange.fitflock.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.common.UserThreadLocal;
import com.orange.fitflock.common.enums.BusinessCode;
import com.orange.fitflock.constant.LoggerConstant;
import com.orange.fitflock.dto.RecordDto;
import com.orange.fitflock.dto.RecordGroupDateDto;
import com.orange.fitflock.entity.GroupMember;
import com.orange.fitflock.entity.TrainingRecord;
import com.orange.fitflock.entity.UserGroup;
import com.orange.fitflock.entity.Userinfo;
import com.orange.fitflock.entity.table.EntityTable;
import com.orange.fitflock.mapper.TrainingRecordMapper;
import com.orange.fitflock.service.*;
import com.orange.fitflock.utils.JsonUtil;
import com.orange.fitflock.vo.RecordDateVo;
import com.orange.fitflock.vo.RecordGroupDateVo;
import com.orange.fitflock.vo.RecordUserDateVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 训练记录表 服务层实现。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Service
public class TrainingRecordServiceImpl extends ServiceImpl<TrainingRecordMapper, TrainingRecord> implements TrainingRecordService {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstant.Record_LOG);
    @Autowired
    private GroupMemberService groupMemberService;
    @Autowired
    private ActionService actionService;
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private ActionCapacityService actionCapacityService;

    @Override
    @Transactional
    public Result insertRecord(RecordDto recordDto) {
        try {
            List<RecordDto.RecordList> recordList = recordDto.getRecordList();
            List<TrainingRecord> trainingRecordList = new ArrayList<>();
            int userId = UserThreadLocal.get();
            LOGGER.info("用户id：{}", userId);

            for (RecordDto.RecordList record : recordList) {
                double actionMaxWeight = 0.0;
                int actionId = record.getActionId();
                double actionCapacity = record.getActionCapacity();
                for (RecordDto.InputDto input : record.getInputs()) {
                    if ((input.getReps() != 0 && Double.compare(input.getWeight(), 0.0) == 0) || (input.getReps() == 0 && Double.compare(input.getWeight(), 0.0) != 0)) {
                        String actionName = actionService.getOne(EntityTable.ACTION.ACTION_ID.eq(actionId)).getActionName();
                        return Result.fail(BusinessCode.FAIL.getCode(), "动作:" + actionName + ",ID:" + input.getId() + "的次数和重量不能同时为空");
                    }
                    if (input.getReps() != 0 && Double.compare(input.getWeight(), 0.0) != 0) {
                        TrainingRecord trainingRecordCopy = new TrainingRecord();
                        trainingRecordCopy.setActionId(actionId);
                        trainingRecordCopy.setUserId(userId);
                        trainingRecordCopy.setWeight(input.getWeight());
                        trainingRecordCopy.setRepsPerSet(input.getReps());
                        trainingRecordCopy.setInputId(input.getId());
                        trainingRecordCopy.setTrainingDate(LocalDateTime.now());
                        trainingRecordList.add(trainingRecordCopy);
//                        actionCapacity += input.getWeight() * input.getReps();
                        if (Double.compare(input.getWeight(), actionMaxWeight) > 0) {
                            actionMaxWeight = input.getWeight();
                        }
                    }
                }
                actionCapacityService.insertOrUpdate(userId, actionId, actionCapacity, actionMaxWeight, recordDto.getDate());
            }

            LOGGER.info("训练记录：{}", trainingRecordList);
            this.saveBatch(trainingRecordList);
            return Result.success();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LOGGER.info("用户ID：{},记录提交失败,已回滚", UserThreadLocal.get(), e);
            return Result.fail(BusinessCode.FAIL.getCode(), "记录提交失败，请重试");
        }
    }

    @Override
    public RecordDateVo getRecordDateList(Integer userId) {
        List<TrainingRecord> list = this.list(QueryWrapper.create().select("DISTINCT training_date").orderBy("training_date", true).where(EntityTable.TRAINING_RECORD.USER_ID.eq(userId)));
        Map<Integer, Map<Integer, StringBuilder>> dateStringsByYearAndMonth = new HashMap<>();
        list.forEach(record -> {
            LocalDateTime date = record.getTrainingDate();
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();

            StringBuilder sbForMonth = dateStringsByYearAndMonth.computeIfAbsent(year, k -> new HashMap<>()).computeIfAbsent(month, m -> new StringBuilder());
            sbForMonth.append("y").append(year).append("m").append(month).append("d").append(day).append(":").append("'spot'").append(",");
        });

        RecordDateVo result = new RecordDateVo();
        result.setYearList(dateStringsByYearAndMonth.entrySet().stream()
                .map(entry -> {
                    RecordDateVo.Year yearObj = new RecordDateVo.Year();
                    yearObj.setYear(entry.getKey());

                    Map<Integer, String> monthList = entry.getValue().entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString().substring(0, e.getValue().length() - 1))); // 去掉最后一个逗号

                    yearObj.setMonthList(monthList);
                    return yearObj;
                })
                .collect(Collectors.toList()));

        return result;
    }

    @Override
    public RecordDateVo getGroupRecordDateList(Integer userId, UserGroup userGroup) {
        List<GroupMember> list1 = groupMemberService.list(EntityTable.GROUP_MEMBER.GROUP_ID.eq(userGroup.getGroupId()));
        Set<Integer> userList = new HashSet<>();
        for (GroupMember groupMember : list1) {
            Integer groupIdUser = groupMember.getUserId();
            userList.add(groupIdUser);
        }

        // 获取用户组中所有用户的训练记录并分组统计
        Map<LocalDateTime, Integer> dateCountMap = new HashMap<>();
        for (Integer id : userList) {
            List<TrainingRecord> recordsForUser = this.list(QueryWrapper.create().select("DISTINCT training_date").orderBy("training_date", true).where(EntityTable.TRAINING_RECORD.USER_ID.eq(id)));
            recordsForUser.forEach(record -> {
                LocalDateTime date = record.getTrainingDate();
                dateCountMap.merge(date, 1, Integer::sum);
            });
        }
        Map<Integer, Map<Integer, StringBuilder>> dateStringsByYearAndMonth = new HashMap<>();
        dateCountMap.forEach((date, count) -> {
            int year = date.getYear();
            int month = date.getMonthValue();
            int day = date.getDayOfMonth();

            String spotString = switch (count) {
                case 1 -> "'spot-1'";
                case 2 -> "'spot-2'";
                case 3 -> "'spot-3'";
                case 4 -> "'spot-4'";
                case 5 -> "'spot-5'";
                case 6 -> "'spot-6'";
                default -> "’spot‘";
            };
            StringBuilder sbForMonth = dateStringsByYearAndMonth.computeIfAbsent(year, k -> new HashMap<>()).computeIfAbsent(month, m -> new StringBuilder());
            sbForMonth.append("y").append(year).append("m").append(month).append("d").append(day).append(":").append(spotString).append(",");
        });
        RecordDateVo result = new RecordDateVo();
        result.setYearList(dateStringsByYearAndMonth.entrySet().stream()
                .map(entry -> {
                    RecordDateVo.Year yearObj = new RecordDateVo.Year();
                    yearObj.setYear(entry.getKey());

                    Map<Integer, String> monthList = entry.getValue().entrySet().stream()
                            .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().substring(0, e.getValue().length() - 1))); // 去掉最后一个逗号

                    yearObj.setMonthList(monthList);
                    return yearObj;
                })
                .collect(Collectors.toList()));

        return result;
    }

    /**
     * 根据用户ID和日期获取训练记录列表
     *
     * @param userId 用户ID
     * @param json   日期的JSON字符串
     * @return 训练记录列表
     * @throws IOException 当解析JSON出错时抛出
     */
    @Override
    public RecordUserDateVo getUserDateList(Integer userId, String json) throws IOException {
        // 解析日期
        Object date = JsonUtil.jsonToMap(json).get("date");
        final double[] allCapacity = {0.0};
        // 查询训练记录列表
        List<TrainingRecord> list = this.list(EntityTable.TRAINING_RECORD.USER_ID.eq(userId)
                .and(EntityTable.TRAINING_RECORD.TRAINING_DATE.eq(date)));
        // 构建记录列表映射
        Map<Integer, RecordUserDateVo.RecordList> recordMap = new HashMap<>();
        Map<Integer, Integer> actionInputIdCounter = new HashMap<>(); // 存储动作ID与对应的inputId计数器
        list.forEach(record1 -> {
            // 获取动作ID和动作名称
            Integer actionId = record1.getActionId();
            String actionName = actionService.getOne(EntityTable.ACTION.ACTION_ID.eq(actionId)).getActionName();

            int inputId = actionInputIdCounter.computeIfAbsent(actionId, k -> 0) + 1;
            actionInputIdCounter.put(actionId, inputId);
            // 获取或创建记录列表
            RecordUserDateVo.RecordList recordList = recordMap.computeIfAbsent(actionId, k -> {
                RecordUserDateVo.RecordList rl = new RecordUserDateVo.RecordList();
                rl.setActionId(actionId);
                rl.setActionName(actionName);
                Double capacity = actionCapacityService.getOne(EntityTable.ACTION_CAPACITY.ACTION_ID.eq(actionId).and(EntityTable.ACTION_CAPACITY.DATE.eq(date)).and(EntityTable.ACTION_CAPACITY.USER_ID.eq(userId))).getCapacity();
                rl.setCapacity(capacity);
                allCapacity[0] = allCapacity[0] + capacity;
                rl.setInputs(new ArrayList<>());
                return rl;
            });
            // 添加输入数据到记录列表
            recordList.getInputs().add(new RecordUserDateVo.InputDto(
                    inputId,
                    record1.getWeight(),
                    record1.getRepsPerSet()
            ));
        });
        // 构建返回的训练记录对象
        RecordUserDateVo record = new RecordUserDateVo();
        record.setRecordList(new ArrayList<>(recordMap.values()));
        record.setAllCapacity(allCapacity[0]);
        return record;
    }

    /**
     * 根据分组ID和日期获取训练记录列表
     *
     * @param recordGroupDateDto 分组ID和日期的DTO对象
     * @return 训练记录列表的VO对象
     */
    @Override
    public RecordGroupDateVo getRecordGroupDateList(RecordGroupDateDto recordGroupDateDto) {
        // 获取分组成员列表
        List<GroupMember> memberList = groupMemberService.list(EntityTable.GROUP_MEMBER.GROUP_ID.eq(recordGroupDateDto.getGroupId()));
        List<Integer> userIds = new ArrayList<>();
        for (GroupMember member : memberList) {
            userIds.add(member.getUserId());
        }
        // 根据用户ID查询训练记录列表
        Map<Integer, List<TrainingRecord>> userRecordsMap = new HashMap<>();
        for (Integer userId : userIds) {
            List<TrainingRecord> list = this.list(EntityTable.TRAINING_RECORD.USER_ID.eq(userId)
                    .and(EntityTable.TRAINING_RECORD.TRAINING_DATE.eq(recordGroupDateDto.getDate())));
            userRecordsMap.put(userId, list);
        }

        // 创建并设置RecordGroupDate1Vo对象
        RecordGroupDateVo recordGroupDateVo = new RecordGroupDateVo();
        List<RecordGroupDateVo.RecordGroupDate1Vo> recordGroupDate1VoList = new ArrayList<>();

        for (Map.Entry<Integer, List<TrainingRecord>> entry : userRecordsMap.entrySet()) {
            final double[] allCapacity = {0.0};
            Integer userId = entry.getKey();
            Userinfo userinfo = userinfoService.getOne(EntityTable.USERINFO.USER_ID.eq(userId));
            String nickName = userinfo.getNickName();
            String avatarUrl = userinfo.getAvatarUrl();
            List<TrainingRecord> recordsForUser = entry.getValue();

            RecordGroupDateVo.RecordGroupDate1Vo recordGroupDate1Vo = new RecordGroupDateVo.RecordGroupDate1Vo();
            recordGroupDate1Vo.setUserId(userId);
            recordGroupDate1Vo.setNickName(nickName);
            recordGroupDate1Vo.setAvatarUrl(avatarUrl);

            // 组织RecordList
            List<RecordGroupDateVo.RecordList> recordLists = new ArrayList<>();
            Map<Integer, RecordGroupDateVo.RecordList> actionRecordMap = new HashMap<>();
            Map<Integer, Integer> actionInputIdCounter = new HashMap<>(); // 存储动作ID与对应的inputId计数器
            for (TrainingRecord record1 : recordsForUser) {
                Integer actionId = record1.getActionId();
                String actionName = actionService.getOne(EntityTable.ACTION.ACTION_ID.eq(actionId)).getActionName();

                int inputId = actionInputIdCounter.computeIfAbsent(actionId, k -> 0) + 1;
                actionInputIdCounter.put(actionId, inputId);
                RecordGroupDateVo.RecordList recordList = actionRecordMap.computeIfAbsent(actionId, k -> {
                    RecordGroupDateVo.RecordList rl = new RecordGroupDateVo.RecordList();
                    rl.setActionId(actionId);
                    rl.setActionName(actionName);
                    Double capacity = actionCapacityService.getOne(EntityTable.ACTION_CAPACITY.ACTION_ID.eq(actionId).and(EntityTable.ACTION_CAPACITY.DATE.eq(recordGroupDateDto.getDate())).and(EntityTable.ACTION_CAPACITY.USER_ID.eq(userId))).getCapacity();
                    rl.setCapacity(capacity);
                    rl.setInputs(new ArrayList<>());
                    allCapacity[0] = allCapacity[0] + capacity;
                    return rl;
                });

                // 假设 TrainingRecord 中存在 inputId、inputWeight 和 inputReps 字段
                recordList.getInputs().add(new RecordUserDateVo.InputDto(
                        inputId,
                        record1.getWeight(),
                        record1.getRepsPerSet()
                ));
            }
            recordLists.addAll(actionRecordMap.values());
            recordGroupDate1Vo.setAllCapacity(allCapacity[0]);
            recordGroupDate1Vo.setRecordList(recordLists);
            if (!recordLists.isEmpty()) {
                recordGroupDate1VoList.add(recordGroupDate1Vo);
            }
        }

        recordGroupDateVo.setRecordGroupDate1VoList(recordGroupDate1VoList);
        return recordGroupDateVo;
    }


}
