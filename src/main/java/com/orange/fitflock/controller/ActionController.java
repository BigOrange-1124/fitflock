package com.orange.fitflock.controller;

import com.mybatisflex.core.paginate.Page;
import com.orange.fitflock.entity.Action;
import com.orange.fitflock.service.ActionService;
import com.orange.fitflock.vo.ActionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 动作表 控制层。
 *
 * @author g1310
 * @since 2024-02-11
 */
@RestController
@RequestMapping("/action")
public class ActionController {

    @Autowired
    private ActionService actionService;

    /**
     * 添加动作表。
     *
     * @param action 动作表
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Action action) {
        return actionService.save(action);
    }

    /**
     * 根据主键删除动作表。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return actionService.removeById(id);
    }

    /**
     * 根据主键更新动作表。
     *
     * @param action 动作表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Action action) {
        return actionService.updateById(action);
    }

    /**
     * 查询所有动作表。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public ActionVo list() {
        return actionService.getActionData();
    }

    /**
     * 根据动作表主键获取详细信息。
     *
     * @param id 动作表主键
     * @return 动作表详情
     */
    @GetMapping("getInfo/{id}")
    public Action getInfo(@PathVariable Serializable id) {
        return actionService.getById(id);
    }

    /**
     * 分页查询动作表。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Action> page(Page<Action> page) {
        return actionService.page(page);
    }

}
