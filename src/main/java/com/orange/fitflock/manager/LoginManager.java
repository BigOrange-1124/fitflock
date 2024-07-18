package com.orange.fitflock.manager;

import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.common.enums.BusinessCode;
import com.orange.fitflock.constant.GlobalConstant;
import com.orange.fitflock.constant.LoggerConstant;
import com.orange.fitflock.dto.JsCode2sessionDto;
import com.orange.fitflock.dto.LoginDto;
import com.orange.fitflock.entity.UserLog;
import com.orange.fitflock.service.UserLogService;
import com.orange.fitflock.service.UserService;
import com.orange.fitflock.service.UserinfoService;
import com.orange.fitflock.utils.JsonUtil;
import com.orange.fitflock.utils.RedisUtil;
import com.orange.fitflock.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Random;

@Service
public class LoginManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstant.LOGIN_LOG);

    private static final int MAX_USER_ID = 99999999;
    private static final int MIN_USER_ID = 10000000;
    private static final int CODE_INVALID = 40029;
    private static final int CODE_OFTEN = 45011;
    private static final int CODE_BLOCKED = 40026;
    private static final int CODE_SYSTEM_BUSY = -1;
    @Autowired
    private UserinfoService userinfoService;
    @Autowired
    private UserLogService userLogService;
    @Autowired
    private UserService userService;
    @Value("${vx.url.getOpenId}")
    private String url;
    @Value("${vx.appId}")
    private String appId;
    @Value("${vx.appSecret}")
    private String appSecret;
    @Value("${vx.default.nickName}")
    private String defaultNickName;
    @Value("${vx.default.avatarUrl}")
    private String defaultAvatarUrl;

    @Transactional
    public Result toLogin(LoginDto loginDto, HttpServletRequest request) {
        try {
            LOGGER.info("用户登录code:" + loginDto.getCode());
            JsCode2sessionDto dto = getOpenid(loginDto.getCode());
            int errCode = dto.getErrcode();
            Result result = errCodeCheck(errCode);
            if (result.getCode() != Result.success().getCode()) {
                return result;
            }
            String openid = dto.getOpenid();
            String sessionKey = dto.getSession_key();
            int userId = 0;
            if (!userService.exitsOpenid(openid)) {
                userService.register(openid, sessionKey);
                userId = userService.getUserId(openid);
                String uuid = String.valueOf(generateUuid());
                userinfoService.register(userId, defaultNickName, defaultAvatarUrl, uuid);
                LOGGER.info("用户首次登录，注册成功，openid: " + openid);
            }
            if (userId == 0) {
                userId = userService.getUserId(openid);
            }
            String clientIP = request.getHeader("X-Real-IP");
            if (clientIP == null) {
                clientIP = request.getHeader("X-Forwarded-For");
            }
            userLogService.save(UserLog.builder()
                    .userId(userId)
                    .brand(loginDto.getBrand())
                    .model(loginDto.getModel())
                    .system(loginDto.getSystem())
                    .platform(loginDto.getPlatform())
                    .version(loginDto.getVersion())
                    .ip(clientIP)
                    .requestURL("/user/login").build());
//            userLogService.eventLog(userId, loginDto.getBrand(), loginDto.getModel(), loginDto.getSystem(), loginDto.getPlatform(), loginDto.getVersion(), GlobalConstant.EVENT_LOGIN);
            LOGGER.info("用户日志表记录成功!");
            String token = TokenUtil.generateAndStoreToken(String.valueOf(userId));
            LOGGER.info("用户id：" + userId + ",获取token成功：" + token);
            return Result.success("登录成功", token);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            LOGGER.info("登录失败，事务已回滚", e.getCause());
            return Result.fail(BusinessCode.LOGIN_CODE_INVALID);
        }
    }

    private int generateUuid() {
        Random random = new Random();
        int uuid = random.nextInt(MAX_USER_ID - MIN_USER_ID) + MIN_USER_ID;
        if (RedisUtil.hasKey("uuid:" + uuid)) {
            return generateUuid();
        } else {
            RedisUtil.set("uuid:" + uuid, "1");
        }
        return uuid;
    }

    /**
     * 获取openid
     *
     * @param code code
     * @return openid
     */
    private JsCode2sessionDto getOpenid(String code) throws JsonProcessingException {
        // 构建请求地址
        String getUrl = url + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=authorization_code";
        LOGGER.info("http请求地址：" + getUrl);
        // 发送http请求
        JsCode2sessionDto dto = JsonUtil.jsonToObject(HttpUtil.get(getUrl), JsCode2sessionDto.class);
        LOGGER.info("http请求返回信息：" + dto);
        return dto;
    }


    /**
     * 校验错误码
     *
     * @param errCode 错误码
     * @return 校验结果
     */
    private Result errCodeCheck(int errCode) {
        // 如果错误码为无效、频繁或系统繁忙，则返回登录错误码无效
        if (errCode == CODE_INVALID ||
                errCode == CODE_OFTEN ||
                errCode == CODE_SYSTEM_BUSY) {
            return Result.fail(BusinessCode.LOGIN_CODE_INVALID);
        }
        // 如果错误码为封禁，则返回登录错误码封禁
        if (errCode == CODE_BLOCKED) {
            return Result.fail(BusinessCode.LOGIN_CODE_BLOCKED);
        }
        // 其他情况返回成功
        return Result.success();
    }
}
