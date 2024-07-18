package com.orange.fitflock.controller;

import com.mybatisflex.core.paginate.Page;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.common.UserThreadLocal;
import com.orange.fitflock.dto.RecordDto;
import com.orange.fitflock.dto.RecordGroupDateDto;
import com.orange.fitflock.entity.TrainingRecord;
import com.orange.fitflock.entity.UserGroup;
import com.orange.fitflock.service.TrainingRecordService;
import com.orange.fitflock.vo.RecordDateVo;
import com.orange.fitflock.vo.RecordGroupDateVo;
import com.orange.fitflock.vo.RecordUserDateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * 训练记录表 控制层。
 *
 * @author g1310
 * @since 2024-02-11
 */
@RestController
@RequestMapping("/trainingRecord")
public class TrainingRecordController {

    @Autowired
    private TrainingRecordService trainingRecordService;

    /**
     * 添加训练记录表。
     *
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public Result save(@RequestBody RecordDto recordList) throws CloneNotSupportedException {
        return trainingRecordService.insertRecord(recordList);
    }

    /**
     * 获取用户自己某个日期的训练记录 日历
     *
     * @return
     */
    @GetMapping("getUserRecordDate")
    public RecordDateVo getRecordDateList() {
        return trainingRecordService.getRecordDateList(UserThreadLocal.get());
    }


    /**
     * 获取小组成员所有训练的日期 日历
     *
     * @param userGroup
     * @return
     */
    @PostMapping("getGroupRecordDate")
    public RecordDateVo getGroupRecordDateList(@RequestBody UserGroup userGroup) {
        return trainingRecordService.getGroupRecordDateList(UserThreadLocal.get(), userGroup);
    }

    /**
     * 获取用户自己当天的训练记录 LIST
     *
     * @param json
     * @return
     * @throws IOException
     */
    @PostMapping("getUserRecordByDate")
    public RecordUserDateVo getRecordUserDate(@RequestBody String json) throws IOException {
        return trainingRecordService.getUserDateList(UserThreadLocal.get(), json);
    }

    /**
     * 获取小组所有人某个日期的训练记录 LIST
     *
     * @param recordGroupDateDto
     * @return
     */
    @PostMapping("getGroupRecordByDate")
    public RecordGroupDateVo getRecordGroupDateList(@RequestBody RecordGroupDateDto recordGroupDateDto) {
        return trainingRecordService.getRecordGroupDateList(recordGroupDateDto);
    }

    /**
     * 根据主键删除训练记录表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return trainingRecordService.removeById(id);
    }

    /**
     * 根据主键更新训练记录表。
     *
     * @param trainingRecord 训练记录表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody TrainingRecord trainingRecord) {
        return trainingRecordService.updateById(trainingRecord);
    }

    /**
     * 查询所有训练记录表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<TrainingRecord> list() {
        return trainingRecordService.list();
    }

    /**
     * 根据训练记录表主键获取详细信息。
     *
     * @param id 训练记录表主键
     * @return 训练记录表详情
     */
    @GetMapping("getInfo/{id}")
    public TrainingRecord getInfo(@PathVariable Serializable id) {
        return trainingRecordService.getById(id);
    }

    /**
     * 分页查询训练记录表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<TrainingRecord> page(Page<TrainingRecord> page) {
        return trainingRecordService.page(page);
    }

}
