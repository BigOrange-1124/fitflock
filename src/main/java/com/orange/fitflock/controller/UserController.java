package com.orange.fitflock.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mybatisflex.core.paginate.Page;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.dto.LoginDto;
import com.orange.fitflock.entity.User;
import com.orange.fitflock.manager.LoginManager;
import com.orange.fitflock.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 小程序用户表 控制层。
 *
 * @author g1310
 * @since 2024-02-11
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private LoginManager loginManager;
    @Autowired
    private UserService userService;

    @PostMapping("login")
    public Result login(@RequestBody LoginDto loginDto, HttpServletRequest request) throws JsonProcessingException {
        return loginManager.toLogin(loginDto, request);
    }

    @GetMapping("checkToken")
    public Result checkToken() {
        return Result.success();
    }

    /**
     * 添加小程序用户表。
     *
     * @param user 小程序用户表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * 根据主键删除小程序用户表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return userService.removeById(id);
    }

    /**
     * 根据主键更新小程序用户表。
     *
     * @param user 小程序用户表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody User user) {
        return userService.updateById(user);
    }

    /**
     * 查询所有小程序用户表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<User> list() {
        return userService.list();
    }

    /**
     * 根据小程序用户表主键获取详细信息。
     *
     * @param id 小程序用户表主键
     * @return 小程序用户表详情
     */
    @GetMapping("getInfo/{id}")
    public User getInfo(@PathVariable Serializable id) {
        return userService.getById(id);
    }

    /**
     * 分页查询小程序用户表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<User> page(Page<User> page) {
        return userService.page(page);
    }

}
