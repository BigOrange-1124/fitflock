package com.orange.fitflock.service;

import com.mybatisflex.core.service.IService;
import com.orange.fitflock.entity.Action;
import com.orange.fitflock.vo.ActionVo;

import java.util.List;
import java.util.Map;

/**
 * 动作表 服务层。
 *
 * @author g1310
 * @since 2024-02-11
 */
public interface ActionService extends IService<Action> {

    ActionVo getActionData();

}
