package interfaces.pdainterface.common.action;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import base.web.InterfaceAction;
import edu.emory.mathcs.backport.java.util.LinkedList;

/**
 *维护单位、维护区域
 */
public class PDALoadDataAction extends InterfaceAction{
	private static final long serialVersionUID = 5247555786107340868L;
	private static final Logger log = Logger.getLogger(PDALoadDataAction.class);
	
	/**
	 * 维护单位
	 * @return
	 */
	public String getMaintainCompanyData() {
		List<Map<String, Object>> dataList = getMaintainList("_无线资源_维护单位");
		sendResponse(Integer.valueOf(0), dataList);
		return "success";
	}
	
	/**
	 * 维护区域
	 * @return
	 */
	public String getMaintainAreaData() {
		List<Map<String, Object>> dataList = getMaintainList("_无线资源_所属综合维护区域");
		sendResponse(Integer.valueOf(0), dataList);
		return "success";
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMaintainList(String index){
		List<Map<String, Object>> list = new LinkedList();
		String sql = "select a.value , a.name from g_com_dict a , g_com_dict b "
				+ "where a.super_code = b.code and b.dict_index= '"+index+"' ";
		list = this.getJdbcTemplate().queryForList(sql);
		return list;
	}
	
	public static Logger getLog() {
		return log;
	}
}
