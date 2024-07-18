package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.entity.Images;
import com.orange.fitflock.entity.table.EntityTable;
import com.orange.fitflock.mapper.ImagesMapper;
import com.orange.fitflock.service.ImagesService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务层实现。
 *
 * @author g1310
 * @since 2024-02-14
 */
@Service
public class ImagesServiceImpl extends ServiceImpl<ImagesMapper, Images> implements ImagesService {
    @Override
    public List<String> getBackground() {
        List<Images> imagesList = this.list(EntityTable.IMAGES.SORT.eq("1").and(EntityTable.IMAGES.STATUS.eq(1)));
        return imagesList.stream()
                .map(Images::getImagesUrl)
                .collect(Collectors.toList());
    }

}
