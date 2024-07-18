package com.orange.fitflock.service;

import com.mybatisflex.core.service.IService;
import com.orange.fitflock.common.Result;
import com.orange.fitflock.dto.RecordDto;
import com.orange.fitflock.dto.RecordGroupDateDto;
import com.orange.fitflock.entity.TrainingRecord;
import com.orange.fitflock.entity.UserGroup;
import com.orange.fitflock.vo.RecordDateVo;
import com.orange.fitflock.vo.RecordGroupDateVo;
import com.orange.fitflock.vo.RecordUserDateVo;

import java.io.IOException;

/**
 * 训练记录表 服务层。
 *
 * @author g1310
 * @since 2024-02-11
 */
public interface TrainingRecordService extends IService<TrainingRecord> {

    Result insertRecord(RecordDto trainingRecord) throws CloneNotSupportedException;

    RecordDateVo getRecordDateList(Integer userId);

    RecordDateVo getGroupRecordDateList(Integer userId, UserGroup userGroup);

    RecordUserDateVo getUserDateList(Integer userId, String date) throws IOException;

    RecordGroupDateVo getRecordGroupDateList(RecordGroupDateDto recordGroupDateDto);
}
