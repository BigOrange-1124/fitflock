package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.entity.Template;
import com.orange.fitflock.mapper.TemplateMapper;
import com.orange.fitflock.service.TemplateService;
import org.springframework.stereotype.Service;

/**
 * 计划模板表 服务层实现。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements TemplateService {

}
