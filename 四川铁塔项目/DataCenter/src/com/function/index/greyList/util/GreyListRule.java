package com.function.index.greyList.util;

import java.util.ArrayList;
import java.util.List;

public enum GreyListRule {
	R01("因存量交割原因导致的空站起租(适用于冗余站址清理或交维起租)"), R02("新建或共享改造已交维但未达到起租时间的,在系统中标识“已交维但未达到交维起租时间”(适用于交维起租)"),
	R03("因订单共享不能解绑导致资源管理系统资源信息与CRM系统产品信息不一致的(适用于站址清理)"), R04("已退租暂不拆除,作为战略资源储备,在系统中标识“已退租暂不拆除”(适用于冗余站址清理)"),
	R05("因系统支撑不及时(如:审计回传不及时导致项目无法关闭或销项的；发起资产报废、调拨或闲置，但资源系统推送不及时),影响站址编码不能及时废除的(适用于冗余站址清理)"),
	R06("共享站中一方退租,另一方共享折扣不变导致收支不匹配(适用于维护费收支配比)"), R07("共享站中一方退租,另一方共享折扣不变导致收支不匹配(适用于场租费收支配比)"),
	R08("剔除室分(包括高铁、地铁等重大项目)异常盈利的站址(适用于单站盈亏异常)"), R09("运营商产权方交塔不交房,共享方增加有一体化机柜及配套,维护费计费规则导致收支倒挂(适用于维护费收支配比)"),
	R10("剔除因存量接收价值过高导致异常亏损的站址(适用于单站盈亏异常)");

	private final String value;

	public String getValue() {
		return value;
	}

	GreyListRule(String value) {
		this.value = value;
	}

	public static List<String> toList() {
		List<String> list = new ArrayList<String>();
		GreyListRule[] enums = GreyListRule.values();
		for (int i = 0; i < enums.length; i++) {
			list.add(enums[i].value);
		}
		return list;
	}

}
