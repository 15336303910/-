package interfaces.irmsInterface.interfaces.station.service;

import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import manage.equt.pojo.EqutInfoBean;
import manage.generator.pojo.GeneratorInfoBean;

import org.springframework.jdbc.core.JdbcTemplate;

import interfaces.irmsInterface.interfaces.station.service.impl.IirmsQueryService;
import interfaces.irmsInterface.utils.InterfaceAddr;
import interfaces.irmsInterface.utils.RequestUtil;
import interfaces.irmsInterface.utils.StationUtil;
import base.database.DataBase;
import base.util.TextUtil;

public class IrmsQueryService extends DataBase implements IirmsQueryService{
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 根据站点ID得到机房信息
	 * @param siteId
	 * @return
	 */
	public List<GeneratorInfoBean> getGeneratorBySite(String siteId){
		String sql = "select stationBaseId,resNum,lat ,lon,stationAddr,region,stationName"
				+ " from job_stationbase where stationBaseId ='"+siteId+"'";
		List<Map<String, Object>> queryList = this.jdbcTemplate.queryForList(sql);
		List<GeneratorInfoBean> list = new LinkedList<GeneratorInfoBean>();
		
		try{
			if(TextUtil.isNotNull(queryList)){
				Map<String, Object> resMap = queryList.get(0);
				String resNum = resMap.get("resNum")+"";
				if(TextUtil.isNotNull(resNum)){
					String param = "<params>"
							+ "<param key=\"related_site_id\" value=\""+resNum+"\"/>"
							+ "<param key=\"start\" value=\"1\"/>"
							+ "<param key=\"end\" value=\"20\"/>"
							+ "</params>";
					String jsonString = "params="+URLEncoder.encode(param, "UTF-8");
					
					String outIN = RequestUtil.HttpRequest(InterfaceAddr.QUERY_GENERATOR, "POST", jsonString);
					list = StationUtil.getGenerator(outIN);
					if(TextUtil.isNotNull(list)){
						for(GeneratorInfoBean gener : list){
							gener.setAreano(siteId);
							gener.setLat(resMap.get("lat")+"");
							gener.setLon(resMap.get("lon")+"");
							gener.setRegion(resMap.get("region")+"");
							gener.setGeneratorAddr(resMap.get("stationAddr")+"");
							gener.setAreano(resMap.get("stationBaseId")+"");
							gener.setStation(resMap.get("stationName")+"");
							
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 得到机架信息
	 * @param generId
	 * @return
	 */
	public List<EqutInfoBean> getEqutList(String generId){
		List<EqutInfoBean> list = new LinkedList<EqutInfoBean>();
		try{
			if(TextUtil.isNotNull(generId)){
				String lifeparams ="<params>"
						+ "<param key=\"pro_task_id\" value=\"\"/>"
						+ "<param key=\"status\" value=\"3\"/>"
						+ "<param key=\"photo\" value=\"0\"/>"
						+ "</params>"
						+ "</params>";
				String param = "<params>"
						+ "<param key=\"room_id\" value=\""+generId+"\"/>"
						+ "</params>";
				String jsonString = "lifeparams="+URLEncoder.encode(lifeparams, "UTF-8")
						+"&params="+URLEncoder.encode(param, "UTF-8");
				String outIN = RequestUtil.HttpRequest(InterfaceAddr.QUERY_RACK, "POST", jsonString);
				list = StationUtil.getEqutInfo(outIN);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
