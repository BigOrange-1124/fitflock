package com.orange.fitflock.controller;

import com.mybatisflex.core.paginate.Page;
import com.orange.fitflock.entity.Template;
import com.orange.fitflock.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 计划模板表 控制层。
 *
 * @author g1310
 * @since 2024-02-11
 */
@RestController
@RequestMapping("/template")
public class TemplateController {

    @Autowired
    private TemplateService templateService;

    /**
     * 添加计划模板表。
     *
     * @param template 计划模板表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Template template) {
        return templateService.save(template);
    }

    /**
     * 根据主键删除计划模板表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return templateService.removeById(id);
    }

    /**
     * 根据主键更新计划模板表。
     *
     * @param template 计划模板表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Template template) {
        return templateService.updateById(template);
    }

    /**
     * 查询所有计划模板表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<Template> list() {
        return templateService.list();
    }

    /**
     * 根据计划模板表主键获取详细信息。
     *
     * @param id 计划模板表主键
     * @return 计划模板表详情
     */
    @GetMapping("getInfo/{id}")
    public Template getInfo(@PathVariable Serializable id) {
        return templateService.getById(id);
    }

    /**
     * 分页查询计划模板表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Template> page(Page<Template> page) {
        return templateService.page(page);
    }

}
