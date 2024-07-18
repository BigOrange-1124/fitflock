package com.orange.fitflock.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RecordDateVo {

    private List<Year> yearList;

    @Data
    public static class Year {
        private int year;
        private Map<Integer, String> monthList; // 修改为String类型，存储拼接后的长字符串
    }
}