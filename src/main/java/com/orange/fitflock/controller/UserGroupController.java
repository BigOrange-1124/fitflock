package com.orange.fitflock.controller;

import com.mybatisflex.core.paginate.Page;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.common.UserThreadLocal;
import com.orange.fitflock.entity.UserGroup;
import com.orange.fitflock.service.UserGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 用户小组表 控制层。
 *
 * @author g1310
 * @since 2024-02-11
 */
@RestController
@RequestMapping("/userGroup")
public class UserGroupController {

    @Autowired
    private UserGroupService userGroupService;

    /**
     * 添加用户小组表。
     *
     * @param userGroup 用户小组表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public Result createGroup(@RequestBody UserGroup userGroup) {
        return userGroupService.createGroup(userGroup);
    }

    @PostMapping("join")
    public Result join(@RequestBody UserGroup userGroup) {
        return userGroupService.joinGroup(userGroup);
    }

    @GetMapping("joinGroupList")
    public Result groupList() {
        return userGroupService.groupList(UserThreadLocal.get());
    }

    /**
     * 根据主键删除用户小组表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @PostMapping("remove")
    public Result remove(@RequestBody UserGroup userGroup) {
        return userGroupService.removeUserGroup(UserThreadLocal.get(), userGroup);
    }

    /**
     * 根据主键更新用户小组表。
     *
     * @param userGroup 用户小组表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody UserGroup userGroup) {
        return userGroupService.updateById(userGroup);
    }

    /**
     * 查询所有用户小组表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<UserGroup> list() {
        return userGroupService.list();
    }

    /**
     * 根据用户小组表主键获取详细信息。
     *
     * @param id 用户小组表主键
     * @return 用户小组表详情
     */
    @GetMapping("getInfo/{id}")
    public UserGroup getInfo(@PathVariable Serializable id) {
        return userGroupService.getById(id);
    }

    /**
     * 分页查询用户小组表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<UserGroup> page(Page<UserGroup> page) {
        return userGroupService.page(page);
    }

}
