package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.common.UserThreadLocal;
import com.orange.fitflock.common.enums.BusinessCode;
import com.orange.fitflock.constant.GlobalConstant;
import com.orange.fitflock.constant.LoggerConstant;
import com.orange.fitflock.entity.Userinfo;
import com.orange.fitflock.mapper.UserinfoMapper;
import com.orange.fitflock.service.UserinfoService;
import com.orange.fitflock.utils.UploadImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 用户信息表 服务层实现。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements UserinfoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstant.USERINFO_LOG);

    @Override
    public void register(int userid, String nickName, String avatarUrl, String uuid) {
        final Userinfo build = Userinfo.builder().userId(userid).nickName(nickName).avatarUrl(avatarUrl).uuid(uuid).build();
        this.save(build);
    }

    @Override
    public Result uploadAvatar(MultipartFile file) {
        try {
            String saveUrl = UploadImageUtil.uploadImage(file, GlobalConstant.UPLOAD_AVATAR_URL);
            String avatarUrl = GlobalConstant.URL + saveUrl;
            Userinfo build = Userinfo.builder()
                    .avatarUrl(avatarUrl)
                    .userId(UserThreadLocal.get())
                    .build();
            this.updateById(build);
            LOGGER.info("图片上传成功：" + avatarUrl);
            return Result.success("图片上传成功", avatarUrl);
        } catch (IOException e) {
            LOGGER.info("图片上传失败：" + e.getCause());
            return Result.fail(BusinessCode.UPLOAD_CODE_INVALID);
        }

    }
}
