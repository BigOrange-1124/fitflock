package com.orange.fitflock.service;

import com.mybatisflex.core.service.IService;
import com.orange.fitflock.entity.ActionCapacity;

import java.sql.Date;

/**
 * 服务层。
 *
 * @author g1310
 * @since 2024-02-24
 */
public interface ActionCapacityService extends IService<ActionCapacity> {

    /**
     * 插入或增量更新某动作当日容量
     *
     * @param userId
     * @param actionId
     * @param capacity
     * @param date
     */
    void insertOrUpdate(int userId, int actionId, double capacity, double maxWeight, Date date);

}
