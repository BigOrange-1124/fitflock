package com.orange.fitflock.vo;

import lombok.Data;

import java.util.List;

@Data
public class GroupVo {
    private String groupId;
    private String groupName;
    private int memberCount;
    private String names;

    public GroupVo(String groupId, String groupName, int memberCount, String names) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.memberCount = memberCount;
        this.names = names;
    }
}
