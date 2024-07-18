package com.orange.fitflock.config;

import com.mybatisflex.core.audit.AuditMessage;
import com.mybatisflex.core.audit.MessageReporter;
import com.orange.fitflock.constant.LoggerConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @title: MyMessageReporter
 * @author: 郭皓隆
 * @date: 2023-08-14
 * @Description: 自定义SQL审计打印
 */
public class MyMessageReporter implements MessageReporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstant.AUDIT_SQL);

    /**
     * 自定义SQL审计打印
     *
     * @param list
     */
    @Override
    public void sendMessages(List<AuditMessage> list) {
        for (AuditMessage message : list) {
            System.out.println(message.toString());
            LOGGER.info(message.toString());
        }
    }
}
