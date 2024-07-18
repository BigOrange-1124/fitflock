package com.orange.fitflock.controller;

import com.mybatisflex.core.paginate.Page;
import com.orange.fitflock.entity.Images;
import com.orange.fitflock.service.ImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 控制层。
 *
 * @author g1310
 * @since 2024-02-14
 */
@RestController
@RequestMapping("/images")
public class ImagesController {

    @Autowired
    private ImagesService imagesService;

    @GetMapping("getBackground")
    public List<String> getBackGround() {
        return imagesService.getBackground();
    }

    /**
     * 添加。
     *
     * @param images
     * @return {@code true} 添加成功，{@code false} 添加失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Images images) {
        return imagesService.save(images);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Serializable id) {
        return imagesService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param images
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Images images) {
        return imagesService.updateById(images);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public List<Images> list() {
        return imagesService.list();
    }

    /**
     * 根据主键获取详细信息。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public Images getInfo(@PathVariable Serializable id) {
        return imagesService.getById(id);
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Images> page(Page<Images> page) {
        return imagesService.page(page);
    }

}
