package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.entity.User;
import com.orange.fitflock.entity.table.EntityTable;
import com.orange.fitflock.mapper.UserMapper;
import com.orange.fitflock.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 小程序用户表 服务层实现。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public int getUserId(String openid) {
        return this.getOne(EntityTable.USER.OPEN_ID.eq(openid)).getId();
    }

    @Override
    public boolean exitsOpenid(String openid) {
        return this.exists(EntityTable.USER.OPEN_ID.eq(openid));
    }

    @Override
    public void register(String openid, String sessionKey) {
        User build = User.builder()
                .openId(openid)
                .sessionKey(sessionKey)
                .build();
        this.save(build);
    }
}
