package com.orange.fitflock.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.orange.fitflock.entity.Action;
import com.orange.fitflock.mapper.ActionMapper;
import com.orange.fitflock.service.ActionService;
import com.orange.fitflock.vo.ActionVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 动作表 服务层实现。
 *
 * @author g1310
 * @since 2024-02-11
 */
@Service
public class ActionServiceImpl extends ServiceImpl<ActionMapper, Action> implements ActionService {
    @Override
    public ActionVo getActionData() {

        List<Action> list = this.list();
        // 创建 ActionVo 对象
        ActionVo actionVo = new ActionVo();
        List<ActionVo.PartList> partList = new ArrayList<>();

        // 使用 Map 来临时存储 partId 对应的 PartList 对象
        Map<Integer, ActionVo.PartList> partMap = new HashMap<>();

        // 遍历 Action 实体列表
        for (Action action : list) {
            // 获取动作对应的 partId 和 actionTypeId
            Integer partId = action.getPartId();
            Integer actionTypeId = action.getActionTypeId();

            // 如果 partMap 中没有该 partId 对应的 PartList，则创建一个新的 PartList 对象
            if (!partMap.containsKey(partId)) {
                ActionVo.PartList part = new ActionVo.PartList();
                part.setPartId(partId);
                String partName = "";
                switch (action.getPartId()){
                    case 1: partName="胸";break;
                    case 2: partName="背";break;
                    case 3: partName="肩";break;
                    case 4: partName="腿";break;
                    case 5: partName="二头";break;
                    case 6: partName="三头";break;
                    case 7: partName="腹";break;
                    default: partName="其他";break;
                }
                part.setPartName(partName);
                part.setActionTypeList(new ArrayList<>());
                partMap.put(partId, part);
                partList.add(part);
            }

            // 获取对应的 PartList 对象
            ActionVo.PartList part = partMap.get(partId);
            List<ActionVo.ActionTypeList> actionTypeList = part.getActionTypeList();

            // 检查该 PartList 中是否已经存在对应的 ActionTypeList
            boolean existsActionTypeList = false;
            for (ActionVo.ActionTypeList actionType : actionTypeList) {
                if (actionType.getActionTypeId().equals(actionTypeId)) {
                    existsActionTypeList = true;
                    break;
                }
            }

            // 如果不存在对应的 ActionTypeList，则创建一个新的 ActionTypeList 对象并添加到 PartList 中
            if (!existsActionTypeList) {
                ActionVo.ActionTypeList actionType = new ActionVo.ActionTypeList();
                actionType.setActionTypeId(actionTypeId);
                String actionTypeName = "";
                switch(actionTypeId) {
                    case 1: actionTypeName="杠铃";break;
                    case 2: actionTypeName="哑铃";break;
                    case 3: actionTypeName="绳索";break;
                    case 4: actionTypeName="固定器械";break;
                    case 5: actionTypeName="自重";break;
                    default: actionTypeName="其他";break;
                }
                actionType.setActionTypeName(actionTypeName);
                actionType.setActionList(new ArrayList<>());
                actionTypeList.add(actionType);
            }

            // 获取对应的 ActionTypeList 对象
            ActionVo.ActionTypeList actionType = null;
            for (ActionVo.ActionTypeList at : actionTypeList) {
                if (at.getActionTypeId().equals(actionTypeId)) {
                    actionType = at;
                    break;
                }
            }

            // 创建新的 ActionList 对象并添加到 ActionTypeList 中
            ActionVo.ActionList actionList = new ActionVo.ActionList();
            actionList.setActionId(action.getActionId());
            actionList.setActionName(action.getActionName());
            actionList.setActionDesciption(action.getActionDescription());
            actionList.setActionPicture(action.getActionPicture());
            actionType.getActionList().add(actionList);
        }

        // 将 partList 设置到 ActionVo 对象中
        actionVo.setActionList(partList);
        return actionVo;
    }

}
