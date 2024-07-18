package com.orange.fitflock.service;

import com.mybatisflex.core.service.IService;
import com.orange.fitflock.entity.User;

/**
 * 小程序用户表 服务层。
 *
 * @author g1310
 * @since 2024-02-11
 */
public interface UserService extends IService<User> {
    int getUserId(String openid);

    boolean exitsOpenid(String openid);

    void register(String openid, String sessionKey);
}
