package com.orange.fitflock.controller;

import com.mybatisflex.core.paginate.Page;
import com.orange.fitflock.entity.UserFeedback;
import com.orange.fitflock.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 用户反馈表 控制层。
 *
 * @author g1310
 * @since 2024-02-11
 */
@RestController
@RequestMapping("/userFeedback")
public class UserFeedbackController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    /**
     * 添加用户反馈表。
     *
     * @param userFeedback 用户反馈表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody UserFeedback userFeedback) {
        return userFeedbackService.save(userFeedback);
    }

    /**
     * 根据主键删除用户反馈表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return userFeedbackService.removeById(id);
    }

    /**
     * 根据主键更新用户反馈表。
     *
     * @param userFeedback 用户反馈表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody UserFeedback userFeedback) {
        return userFeedbackService.updateById(userFeedback);
    }

    /**
     * 查询所有用户反馈表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<UserFeedback> list() {
        return userFeedbackService.list();
    }

    /**
     * 根据用户反馈表主键获取详细信息。
     *
     * @param id 用户反馈表主键
     * @return 用户反馈表详情
     */
    @GetMapping("getInfo/{id}")
    public UserFeedback getInfo(@PathVariable Serializable id) {
        return userFeedbackService.getById(id);
    }

    /**
     * 分页查询用户反馈表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<UserFeedback> page(Page<UserFeedback> page) {
        return userFeedbackService.page(page);
    }

}
