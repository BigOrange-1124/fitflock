package com.orange.fitflock.controller;

import com.mybatisflex.core.paginate.Page;
import com.orange.fitflock.entity.GroupMember;
import com.orange.fitflock.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 小组成员表 控制层。
 *
 * @author g1310
 * @since 2024-02-11
 */
@RestController
@RequestMapping("/groupMember")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    /**
     * 添加小组成员表。
     *
     * @param groupMember 小组成员表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody GroupMember groupMember) {
        return groupMemberService.save(groupMember);
    }

    /**
     * 根据主键删除小组成员表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return groupMemberService.removeById(id);
    }

    /**
     * 根据主键更新小组成员表。
     *
     * @param groupMember 小组成员表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody GroupMember groupMember) {
        return groupMemberService.updateById(groupMember);
    }

    /**
     * 查询所有小组成员表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<GroupMember> list() {
        return groupMemberService.list();
    }

    /**
     * 根据小组成员表主键获取详细信息。
     *
     * @param id 小组成员表主键
     * @return 小组成员表详情
     */
    @GetMapping("getInfo/{id}")
    public GroupMember getInfo(@PathVariable Serializable id) {
        return groupMemberService.getById(id);
    }

    /**
     * 分页查询小组成员表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<GroupMember> page(Page<GroupMember> page) {
        return groupMemberService.page(page);
    }

}
