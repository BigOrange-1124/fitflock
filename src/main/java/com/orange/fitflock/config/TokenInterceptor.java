package com.orange.fitflock.config;

import com.orange.fitflock.common.OrangeException;
import com.orange.fitflock.common.UserThreadLocal;
import com.orange.fitflock.common.enums.BusinessCode;
import com.orange.fitflock.entity.UserLog;
import com.orange.fitflock.service.UserLogService;
import com.orange.fitflock.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.math.BigInteger;

/**
 * @title: TokenInterceptor
 * @author: 郭皓隆
 * @date: 2023-08-11
 * @Description: 拦截器
 */
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private UserLogService userLogService;

    /**
     * 全局统一拦截器
     *
     * @param request
     * @param response
     * @param handler
     * @return
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (token == null || !TokenUtil.checkToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new OrangeException(BusinessCode.TOKEN_UNAUTHORIZED);
        }
        TokenUtil.renewToken(token);
        int userid = TokenUtil.getUserId(token);
        UserThreadLocal.set(userid);
        request.setAttribute("startTime", System.currentTimeMillis());
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String clientIP = request.getHeader("X-Real-IP");
        if (clientIP == null) {
            clientIP = request.getHeader("X-Forwarded-For");
        }
        String requestURI = request.getRequestURI().substring("/orange".length());
        long startTime = (long) request.getAttribute("startTime");
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        userLogService.save(UserLog.builder()
                .ip(clientIP)
                .userId(UserThreadLocal.get())
                .requestURL(requestURI)
                .elapsedTime(BigInteger.valueOf(elapsedTime)).build());
        UserThreadLocal.remove();
    }
}
