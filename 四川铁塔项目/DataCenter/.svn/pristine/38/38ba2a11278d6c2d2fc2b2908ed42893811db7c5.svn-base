package com.function.index.greyList.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.function.index.greyList.model.GreyHistory;
import com.function.index.greyList.model.GreyList;

/**
 * 灰名单数据处理实现类
 */
@Repository("greyListService")
public class GreyListService implements IGreyListService {
	@Autowired
	private HibernateTemplate ht;

	@Override
	public void insertGreyOrder(GreyList record) {
		ht.save(record);
	}

	@Override
	public void updateGreyOrder(GreyList record) {
		ht.update(record);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GreyList> getGreyListPage(final String hql, final Integer displayStart, final Integer iDisplayLength) {
		@SuppressWarnings("rawtypes")
		List<GreyList> list = ht.executeFind(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult(displayStart);
				query.setMaxResults(iDisplayLength);
				List<GreyList> list = query.list();
				return list;
			}
		});

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GreyList> getGreyList(String hql) {
		return ht.find(hql);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GreyList> getGreyListById(String hql, Long id) {
		return ht.find(hql, id);
	}

	@Override
	public void insertGreyListHisData(GreyHistory hisData) {
		ht.save(hisData);// 注意，历史表的数据主键与正表一致，这里新增时也需要传入
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GreyHistory> getGreyListHisData(GreyHistory hisData) {
		String hql = "from GreyHistory where 1=1 ";// 查询条件拼接在下面，注意添加注释

		if (StringUtils.isNotEmpty(hisData.getSaName())) {
			hql += "and  saName like '%" + hisData.getSaName() + "%'";// saName
		}

		return ht.find(hql);
	}

	@Override
	public Integer dataBackup(Integer id, String applyType) {
		// 获取原数据
		String hql = "from GreyList where id=" + id;
		List<GreyList> dataList = ht.find(hql);

		if (dataList == null || dataList.size() <= 0) {
			return 0;
		}

		GreyList data = dataList.get(0);

		// 将数据添加到hisData
		GreyHistory hisData = new GreyHistory();

		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			BeanUtils.copyProperties(hisData, data);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// 修改数据
		//data.setLaunchTime(new Date());
		data.setCityApprover("");
		data.setCaTime(null);
		data.setProvinceApprover("");
		data.setPaTime(null);
		data.setRejectReason("");
		data.setProcedureSegment("1");
		data.setProcedureStatus(applyType);

		// 添加数据处理
		// Transaction transaction = session.beginTransaction();
		hisData.setHistorySource("remove");// 添加历史表数据来源
		ht.save(hisData);
		ht.update(data);
		// transaction.commit();

		return 1;
	}

	@Override
	public Integer dataRecovery(Integer id) {
		// 取出hisData
		List<GreyHistory> hisDataList = ht.find("from GreyHistory where id=" + id);

		if (hisDataList == null || hisDataList.size() <= 0) {
			return 0;
		}

		GreyHistory hisData = hisDataList.get(0);

		// 删除历史数据，并将原数据还原
		GreyList data = new GreyList();

		try {
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);
			BeanUtils.copyProperties(hisData, data);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		// 数据处理
		ht.delete(hisData);
		ht.update(data);
		return 1;
	}
}
