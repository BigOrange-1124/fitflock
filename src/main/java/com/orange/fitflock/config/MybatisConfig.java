package com.orange.fitflock.config;


import com.mybatisflex.core.audit.AuditManager;
import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.context.annotation.Configuration;

/**
 * @title: MyabtisConfig
 * @author: 郭皓隆
 * @date: 2023-08-09
 * @Description: 监听全局配置类
 */

@Configuration
public class MybatisConfig implements ConfigurationCustomizer {

    public MybatisConfig() {
    }

    /**
     * 重写SQL审计打印方式
     *
     * @param flexConfiguration
     */
    @Override
    public void customize(FlexConfiguration flexConfiguration) {
        //platform：平台，或者是运行的应用
        //module：应用模块
        //url：执行这个 SQL 涉及的 URL 地址
        //user：执行这个 SQL 涉及的 平台用户
        //userIp：执行这个 SQL 的平台用户 IP 地址
        //hostIp：执行这个 SQL 的服务器 IP 地址
        //query：SQL 内容
        //queryParams：SQL 参数
        //queryTime：SQL 执行的时间点（当前时间）
        //elapsedTime：SQL 执行的消耗时间（毫秒）
        //metas：其他扩展元信息
        AuditManager.setAuditEnable(true);
        AuditManager.setMessageReporter(new MyMessageReporter());
        flexConfiguration.setLogImpl(StdOutImpl.class);
    }
}
