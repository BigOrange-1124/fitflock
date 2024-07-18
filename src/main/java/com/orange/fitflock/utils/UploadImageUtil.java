package com.orange.fitflock.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import com.orange.fitflock.common.OrangeException;
import com.orange.fitflock.constant.LoggerConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @title: UploadImageUtil
 * @author: 郭皓隆
 * @date: 2023-08-08
 * @Description: 上传图片工具类
 */
public final class UploadImageUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstant.UPLOAD_IMAGE_LOG);

    /**
     * 上传图片文件
     *
     * @param imageFile  图片文件
     * @param uploadPath 上传路径
     * @return 上传结果
     * @throws IOException IO异常
     */
    public static String uploadImage(MultipartFile imageFile, String uploadPath) throws IOException {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new OrangeException("上传图片为空");
        }

        String originalFilename = imageFile.getOriginalFilename();
        String newFilename = IdUtil.getSnowflakeNextId() + "." + FileUtil.extName(originalFilename);
        LOGGER.info("图片上传路径：{}, 图片名称：{}", uploadPath, newFilename);

        Path uploadDirPath = Paths.get(uploadPath);
        if (!Files.exists(uploadDirPath)) {
            Files.createDirectories(uploadDirPath);
        }

        Path newFilePath = uploadDirPath.resolve(newFilename);
        imageFile.transferTo(newFilePath.toFile());
        LOGGER.info("图片上传服务器成功！");
        return newFilename;
    }
}
