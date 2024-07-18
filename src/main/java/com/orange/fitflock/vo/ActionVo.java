package com.orange.fitflock.vo;

import lombok.Data;

import java.util.List;

@Data
public class ActionVo {

    private List<PartList> actionList;

    @Data
    public static class PartList {
        private Integer partId;
        private String partName;
        private List<ActionTypeList> actionTypeList;
    }

    @Data
    public static class ActionTypeList {
        private Integer actionTypeId;
        private String actionTypeName;
        private List<ActionList> actionList;
    }

    @Data
    public static class ActionList {
        private Integer actionId;
        private String actionName;
        private String actionDesciption;
        private String actionPicture;

    }

}