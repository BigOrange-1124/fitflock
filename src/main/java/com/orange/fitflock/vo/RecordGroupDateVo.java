package com.orange.fitflock.vo;

import lombok.Data;

import java.util.List;

@Data
public class RecordGroupDateVo {
    private List<RecordGroupDate1Vo> recordGroupDate1VoList;

    @Data
    public static class RecordGroupDate1Vo {
        private int userId;
        private String nickName;
        private String avatarUrl;
        private double allCapacity;
        private List<RecordList> recordList;
    }

    @Data
    public static class RecordList {
        private int actionId;
        private String actionName;
        private double capacity;
        private List<RecordUserDateVo.InputDto> inputs;
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
