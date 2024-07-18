package com.orange.fitflock.dto;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class RecordDto {
    private List<RecordList> recordList;
    private Date date;

    @Data
    public static class RecordList {
        private int actionId;
        private String actionName;
        private double actionCapacity;
        private List<InputDto> inputs;
    }

    @Data
    public static class InputDto {
        private int id;
        private double weight;
        private int reps;
    }
}
