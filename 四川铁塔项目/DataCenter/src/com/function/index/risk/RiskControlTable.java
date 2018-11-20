package com.function.index.risk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum RiskControlTable {
	table01("60", "ORC_CGI_DETAIL"),
	table02("61", "ORC_CER_DETAIL"),
	table03("62", "ORC_OSP_DETAIL"),
	table04("405326", "ORC_NVOTM_DETAIL"),
	table05("405327","ORC_RPA_DETAIL"),
	table06("405328","ORC_CTP_DETAIL"),
	table07("405329","ORC_PCOND_DETAIL"),
	table08("405330","ORC_OM_DETAIL"),
	table09("678869","ORC_CACDD_DETAIL"),
	table10("678870","ORC_CTNH_DETAIL"),
	table11("678871","ORC_POOI_DETAIL"),
	table12("678872","ORC_NSCEA_DETAIL"),
	table13("678873","ORC_TRMCD_DETAIL"),
	table14("678874","ORC_RAOBTI_DETAIL"),
	table15("678876","ORC_TFHE_DETAIL"),
	table16("678877","ORC_REMA_DETAIL"),
	table17("678878","ORC_ZCCA_DETAIL"),
	table18("678879","ORC_RCA_DETAIL");
 
	private final String key;
	private final String value;
 
	public String getKey() {
		return key;
	}
 
	public String getValue() {
		return value;
	}
 
	RiskControlTable(String key, String value) {
		this.key = key;
		this.value = value;
	}
 
	/**
	 * 根据key获取value
	 * 
	 * @param key
	 *            : 键值key
	 * @return String
	 */
	public static String getValueByKey(String key) {
		RiskControlTable[] enums = RiskControlTable.values();
		for (int i = 0; i < enums.length; i++) {
			if (enums[i].getKey().equals(key)) {
				return enums[i].getValue();
			}
		}
		return "";
	}
	/**
	 * 转换为MAP集合
	 * 
	 * @returnMap<String, String>
	 */
	public static Map<String, String> toMap() {
		Map<String, String> map = new HashMap<String, String>();
		RiskControlTable[] enums = RiskControlTable.values();
		for (int i = 0; i < enums.length; i++) {
			map.put(enums[i].getKey().toString(), enums[i].getValue());
		}
		return map;
	}
	
	
}
