package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.common.UserThreadLocal;
import com.orange.fitflock.common.enums.BusinessCode;
import com.orange.fitflock.entity.GroupMember;
import com.orange.fitflock.entity.UserGroup;
import com.orange.fitflock.entity.table.EntityTable;
import com.orange.fitflock.mapper.UserGroupMapper;
import com.orange.fitflock.service.GroupMemberService;
import com.orange.fitflock.service.UserGroupService;
import com.orange.fitflock.service.UserinfoService;
import com.orange.fitflock.utils.RedisUtil;
import com.orange.fitflock.vo.GroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 用户小组表 服务层实现。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Service
public class UserGroupServiceImpl extends ServiceImpl<UserGroupMapper, UserGroup> implements UserGroupService {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final Random RANDOM = new Random();
    @Autowired
    private GroupMemberService groupMemberService;
    @Autowired
    private UserinfoService userinfoService;

    @Override
    @Transactional
    public Result createGroup(UserGroup userGroup) {
        try {
            int userid = UserThreadLocal.get();
            long count = groupMemberService.count(EntityTable.GROUP_MEMBER.USER_ID.eq(userid));
            if (count == 1) {
                return Result.fail(BusinessCode.GROUP_CODE_MAX);
            }
            userGroup.setCreatorId(userid);
            userGroup.setMemberCount(1);
            String groupId = generateGroupid();
            userGroup.setGroupId(groupId);
            this.save(userGroup);
            groupMemberService.save(GroupMember.builder().groupId(groupId).userId(userid).build());
            return Result.success("小组创建成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
    }


    /**
     * 加入小组
     *
     * @param userGroup 小组信息
     * @return 结果对象
     */
    @Override
    @Transactional
    public Result joinGroup(UserGroup userGroup) {
        try {
            int userid = UserThreadLocal.get();
            String groupId = userGroup.getGroupId();
            if (groupId.isEmpty()) {
                return Result.fail(BusinessCode.GROUP_CODE_NOT_NULL);
            }
            if (groupMemberService.count(EntityTable.GROUP_MEMBER.GROUP_ID.eq(groupId)) == 0) {
                return Result.fail(BusinessCode.GROUP_CODE_NOT_EXIST);
            }
            // 用户是否在该小组中
            long count = groupMemberService.count(EntityTable.GROUP_MEMBER.USER_ID.eq(userid).and(EntityTable.GROUP_MEMBER.GROUP_ID.eq(groupId)));
            if (count != 0) {
                return Result.fail(BusinessCode.GROUP_CODE_HAS_EXIST);
            }

            // 用户是否没有小组
            long count1 = groupMemberService.count(EntityTable.GROUP_MEMBER.USER_ID.eq(userid));
            if (count1 == 1) {
                return Result.fail(BusinessCode.GROUP_CODE_MAX);
            }
            int memberCount = this.getOne(EntityTable.USER_GROUP.GROUP_ID.eq(groupId)).getMemberCount();
            if (memberCount == 6) {
                return Result.fail(BusinessCode.GROUP_CODE_GROUP_USER_MAX);
            }
            // 保存用户加入小组信息
            groupMemberService.save(GroupMember.builder().groupId(groupId).userId(userid).build());
            // 更新小组成员数量
            userGroup.setMemberCount(memberCount + 1);
            this.updateById(userGroup);
            return Result.success("加入小组成功");
        } catch (Exception e) {
            // 设置事务回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail();
        }
    }

    @Override
    public Result groupList(int userId) {
        List<GroupMember> list = groupMemberService.list(EntityTable.GROUP_MEMBER.USER_ID.eq(userId));
        List<GroupVo> voList = new ArrayList<>();
        for (GroupMember groupMember : list) {
            List<String> nameList = new ArrayList<>();
            String groupId = groupMember.getGroupId();
            UserGroup one = this.getOne(EntityTable.USER_GROUP.GROUP_ID.eq(groupId));
            String groupName = one.getGroupName();
            int memberCount = one.getMemberCount();
            List<GroupMember> list1 = groupMemberService.list(EntityTable.GROUP_MEMBER.GROUP_ID.eq(groupId));
            for (GroupMember groupMember1 : list1) {
                int userid = groupMember1.getUserId();
                nameList.add(userinfoService.getById(userid).getNickName());
            }
            voList.add(new GroupVo(groupId, groupName, memberCount,nameList.toString()));
        }
        return Result.success(voList);
    }

    @Override
    @Transactional
    public Result removeUserGroup(int userId, UserGroup userGroup) {
        try {
            int memberCount = this.getOne(EntityTable.USER_GROUP.GROUP_ID.eq(userGroup.getGroupId())).getMemberCount();
            if (memberCount > 1) {
                this.update(UserGroup.builder().memberCount(memberCount - 1).build(), EntityTable.USER_GROUP.GROUP_ID.eq(userGroup.getGroupId()));
            } else {
                this.remove(EntityTable.USER_GROUP.GROUP_ID.eq(userGroup.getGroupId()));
            }
            groupMemberService.remove(EntityTable.GROUP_MEMBER.USER_ID.eq(userId).and(EntityTable.GROUP_MEMBER.GROUP_ID.eq(userGroup.getGroupId())));
            return Result.success("退出小组成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(BusinessCode.GROUP_CODE_REMOVE_FAIL);
        }
    }

    private String generateGroupid() {
        int length = 6;
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        String string = stringBuilder.toString();
        if (RedisUtil.hasKey("groupid:" + string)) {
            return generateGroupid();
        } else {
            RedisUtil.set("groupid:" + string, "1");
        }
        return string;
    }
}
