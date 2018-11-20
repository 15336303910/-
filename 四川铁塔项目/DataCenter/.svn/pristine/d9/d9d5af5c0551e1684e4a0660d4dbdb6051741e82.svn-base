package com.function.index.greyList.service;

import java.util.List;

import com.function.index.greyList.model.GreyHistory;
import com.function.index.greyList.model.GreyList;

/**
 * 灰名单数据处理
 */
public interface IGreyListService {
	// 新增灰名单数据
	public void insertGreyOrder(GreyList record);

	// 数据修改
	public void updateGreyOrder(GreyList record);

	// 获取分页列表
	public List<GreyList> getGreyListPage(final String hql, final Integer displayStart, final Integer iDisplayLength);

	// 数据列表查询，非分页
	public List<GreyList> getGreyList(final String hql);

	// 根据ID查询
	public List<GreyList> getGreyListById(final String hql, final Long id);

	// 保留数据历史记录
	public void insertGreyListHisData(GreyHistory hisData);

	// 查询历史记录
	public List<GreyHistory> getGreyListHisData(GreyHistory hisData);

	// 续期和解除流程（数据存入历史表，原数据修改为新流程）
	public Integer dataBackup(Integer id, String applyType);

	// 流程审批失败时，数据回转
	public Integer dataRecovery(Integer id);
}
