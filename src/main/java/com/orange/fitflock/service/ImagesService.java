package com.orange.fitflock.service;

import com.mybatisflex.core.service.IService;
import com.orange.fitflock.entity.Images;

import java.util.List;

/**
 * 服务层。
 *
 * @author g1310
 * @since 2024-02-14
 */
public interface ImagesService extends IService<Images> {

    List<String> getBackground();
}
