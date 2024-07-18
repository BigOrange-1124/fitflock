package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.entity.GroupMember;
import com.orange.fitflock.mapper.GroupMemberMapper;
import com.orange.fitflock.service.GroupMemberService;
import org.springframework.stereotype.Service;

/**
 * 小组成员表 服务层实现。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Service
public class GroupMemberServiceImpl extends ServiceImpl<GroupMemberMapper, GroupMember> implements GroupMemberService {

}
