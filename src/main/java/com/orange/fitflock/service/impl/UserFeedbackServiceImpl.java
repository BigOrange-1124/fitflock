package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.entity.UserFeedback;
import com.orange.fitflock.mapper.UserFeedbackMapper;
import com.orange.fitflock.service.UserFeedbackService;
import org.springframework.stereotype.Service;

/**
 * 用户反馈表 服务层实现。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Service
public class UserFeedbackServiceImpl extends ServiceImpl<UserFeedbackMapper, UserFeedback> implements UserFeedbackService {

}
