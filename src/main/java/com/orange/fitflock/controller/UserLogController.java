package com.orange.fitflock.controller;

import com.mybatisflex.core.paginate.Page;
import com.orange.fitflock.entity.UserLog;
import com.orange.fitflock.service.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 用户操作日志表 控制层。
 *
 * @author g1310
 * @since 2024-02-12
 */
@RestController
@RequestMapping("/userLog")
public class UserLogController {

    @Autowired
    private UserLogService userLogService;

    /**
     * 添加用户操作日志表。
     *
     * @param userLog 用户操作日志表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody UserLog userLog) {
        return userLogService.save(userLog);
    }

    /**
     * 根据主键删除用户操作日志表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return userLogService.removeById(id);
    }

    /**
     * 根据主键更新用户操作日志表。
     *
     * @param userLog 用户操作日志表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody UserLog userLog) {
        return userLogService.updateById(userLog);
    }

    /**
     * 查询所有用户操作日志表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<UserLog> list() {
        return userLogService.list();
    }

    /**
     * 根据用户操作日志表主键获取详细信息。
     *
     * @param id 用户操作日志表主键
     * @return 用户操作日志表详情
     */
    @GetMapping("getInfo/{id}")
    public UserLog getInfo(@PathVariable Serializable id) {
        return userLogService.getById(id);
    }

    /**
     * 分页查询用户操作日志表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<UserLog> page(Page<UserLog> page) {
        return userLogService.page(page);
    }

}
