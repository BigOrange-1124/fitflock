package com.orange.fitflock.controller;

import com.orange.fitflock.common.Result;
import com.orange.fitflock.common.UserThreadLocal;
import com.orange.fitflock.constant.LoggerConstant;
import com.orange.fitflock.entity.Userinfo;
import com.orange.fitflock.service.UserinfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 用户信息表 控制层。
 *
 * @author g1310
 * @since 2024-02-12
 */
@RestController
@RequestMapping("/userinfo")
public class UserinfoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstant.USERINFO_LOG);

    @Autowired
    private UserinfoService userinfoService;

    /**
     * 根据主键更新用户信息表。
     *
     * @param userinfo 用户信息表
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PostMapping("update")
    public boolean update(@RequestBody Userinfo userinfo) {
        userinfo.setUserId(UserThreadLocal.get());
        LOGGER.info("用户修改个人信息：" + userinfo);
        return userinfoService.updateById(userinfo);
    }

    /**
     * 查询所有用户信息表。
     *
     * @return 所有数据
     */
//    @GetMapping("list")
//    public List<Userinfo> list() {
//        return userinfoService.list();
//    }

    /**
     * 根据用户信息表主键获取详细信息。
     *
     * @return 用户信息表详情
     */
    @GetMapping("getInfo")
    public Userinfo getInfo() {
        Userinfo byId = userinfoService.getById(UserThreadLocal.get());
        LOGGER.info("用户id：" + UserThreadLocal.get() + "获取用户信息表详情：" + byId);
        return byId;
    }

    @PostMapping("uploadAvatar")
    public Result uploadAvatar(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        return userinfoService.uploadAvatar(file);
    }

}
