package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.entity.UserLog;
import com.orange.fitflock.mapper.UserLogMapper;
import com.orange.fitflock.service.UserLogService;
import org.springframework.stereotype.Service;

/**
 * 用户操作日志表 服务层实现。
 *
 * @author g1310
 * @since 2024-02-12
 */
@Service
public class UserLogServiceImpl extends ServiceImpl<UserLogMapper, UserLog> implements UserLogService {
//    @Override
//    public void eventLog(UserLog userLog) {
//        final UserLog build = UserLog.builder()
//                .userId(userid)
//                .brand(brand)
//                .model(model)
//                .system(system)
//                .platform(platform)
//                .version(version)
//                .event(event)
//                .build();
//        this.save(userLog);
//    }
}
