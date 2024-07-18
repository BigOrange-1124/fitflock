package com.orange.fitflock.service;

import com.mybatisflex.core.service.IService;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.entity.UserGroup;

/**
 * 用户小组表 服务层。
 *
 * @author g1310
 * @since 2024-02-11
 */
public interface UserGroupService extends IService<UserGroup> {

    Result createGroup(UserGroup userGroup);

    Result joinGroup(UserGroup userGroup);

    Result groupList(int userId);

    Result removeUserGroup(int userId, UserGroup userGroup);
}
