package com.orange.fitflock.service;

import com.mybatisflex.core.service.IService;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.entity.Userinfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 用户信息表 服务层。
 *
 * @author g1310
 * @since 2024-02-11
 */
public interface UserinfoService extends IService<Userinfo> {
    void register(int userid, String nickName, String avatarUrl, String uuid);

    Result uploadAvatar(MultipartFile file) throws IOException;
}
