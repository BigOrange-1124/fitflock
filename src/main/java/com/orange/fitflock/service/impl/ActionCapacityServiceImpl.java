package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.constant.LoggerConstant;
import com.orange.fitflock.entity.ActionCapacity;
import com.orange.fitflock.entity.table.EntityTable;
import com.orange.fitflock.mapper.ActionCapacityMapper;
import com.orange.fitflock.service.ActionCapacityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;

/**
 * 服务层实现。
 *
 * @author g1310
 * @since 2024-02-24
 */
@Service
public class ActionCapacityServiceImpl extends ServiceImpl<ActionCapacityMapper, ActionCapacity> implements ActionCapacityService {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoggerConstant.ACTION_CAPACITY_LOG);

    @Override
    public void insertOrUpdate(int userId, int actionId, double capacity, double maxWeight, Date date) {
        ActionCapacity one = this.getOne(EntityTable.ACTION_CAPACITY.USER_ID.eq(userId).and(EntityTable.ACTION_CAPACITY.ACTION_ID.eq(actionId)).and(EntityTable.ACTION_CAPACITY.DATE.eq(date)));
        if (one == null) {
            LOGGER.info("用户ID：{},日期：{},动作：{},新增容量：{},新增最大重量：{}", userId, date, actionId, capacity, maxWeight);
            this.save(ActionCapacity.builder().userId(userId).actionId(actionId).date(date).capacity(capacity).maxWeight(maxWeight).build());
        } else {
            double oldCapacity = one.getCapacity();
            double newCapacity = oldCapacity + capacity;
            final Double oldMaxWeight = one.getMaxWeight();
            if (Double.compare(maxWeight, oldMaxWeight) > 0) {
                LOGGER.info("用户ID：{},日期：{},动作：{},原容量：{},新增容量：{},修改后容量：{},原最大重量：{},修改后最大重量：{}", userId, date, actionId, oldCapacity, capacity, newCapacity, oldMaxWeight, maxWeight);
                this.update(ActionCapacity.builder().capacity(newCapacity).maxWeight(maxWeight).build(), EntityTable.ACTION_CAPACITY.CAPACITY_ID.eq(one.getCapacityId()));
            } else {
                LOGGER.info("用户ID：{},日期：{},动作：{},原容量：{},新增容量：{},修改后容量：{},最大重量不变：{}", userId, date, actionId, oldCapacity, capacity, newCapacity, oldMaxWeight);
                this.update(ActionCapacity.builder().capacity(newCapacity).build(), EntityTable.ACTION_CAPACITY.CAPACITY_ID.eq(one.getCapacityId()));
            }
        }
    }
}
