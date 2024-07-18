package com.orange.fitflock.controller;

import com.mybatisflex.core.paginate.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.orange.fitflock.entity.ActionCapacity;
import com.orange.fitflock.service.ActionCapacityService;
import org.springframework.web.bind.annotation.RestController;
import java.io.Serializable;
import java.util.List;

/**
 *  控制层。
 *
 * @author g1310
 * @since 2024-02-24
 */
@RestController
@RequestMapping("/actionCapacity")
public class ActionCapacityController {

    @Autowired
    private ActionCapacityService actionCapacityService;

    /**
     * 添加。
     *
     * @param actionCapacity 
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody ActionCapacity actionCapacity) {
        return actionCapacityService.save(actionCapacity);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return actionCapacityService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param actionCapacity 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody ActionCapacity actionCapacity) {
        return actionCapacityService.updateById(actionCapacity);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<ActionCapacity> list() {
        return actionCapacityService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public ActionCapacity getInfo(@PathVariable Serializable id) {
        return actionCapacityService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<ActionCapacity> page(Page<ActionCapacity> page) {
        return actionCapacityService.page(page);
    }

}
