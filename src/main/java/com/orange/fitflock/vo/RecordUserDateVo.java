package com.orange.fitflock.vo;

import lombok.Data;

import java.util.List;

@Data
public class RecordUserDateVo {
    private double allCapacity;
    private List<RecordList> recordList;

    @Data
    public static class RecordList {
        private int actionId;
        private String actionName;
        private double capacity;
        private List<InputDto> inputs;
    }

    @Data
    public static class InputDto {
        private int id;
        private double weight;
        private int reps;

        public InputDto(int id, Double weight, int reps) {
            this.id = id;
            this.weight = weight;
            this.reps = reps;
        }
    }
}
