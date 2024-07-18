package com.orange.fitflock.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

/**
 * @title: JsonUtil
 * @author: 郭皓隆
 * @date: 2023-09-25
 * @Description: json与类对象互转工具类
 */
public class JsonUtil {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将 JSON 字符串转换为 Java 对象
     *
     * @param jsonString
     * @param valueType
     * @param <T>
     * @return
     * @throws JsonProcessingException
     */
    public static <T> T jsonToObject(String jsonString, Class<T> valueType) throws JsonProcessingException {
        return OBJECT_MAPPER.readValue(jsonString, valueType);
    }

    /**
     * 将 Java 对象转换为 JSON 字符串
     *
     * @param object
     * @return
     * @throws JsonProcessingException
     */
    public static String objectToJson(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    /**
     * 将 JSON 字符串转换为 Map
     *
     * @param jsonString
     * @return
     * @throws IOException
     */
    public static Map<String, Object> jsonToMap(String jsonString) throws IOException {
        return OBJECT_MAPPER.readValue(jsonString, new TypeReference<Map<String, Object>>() {
        });
    }

    /**
     * 将 Map 转换为 JSON 字符串
     *
     * @param map
     * @return
     * @throws JsonProcessingException
     */
    public static String mapToJson(Map<String, Object> map) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(map);
    }
}
