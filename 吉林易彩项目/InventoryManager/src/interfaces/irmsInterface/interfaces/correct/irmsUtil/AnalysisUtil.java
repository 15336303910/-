package interfaces.irmsInterface.interfaces.correct.irmsUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import edu.emory.mathcs.backport.java.util.LinkedList;
import base.util.JsonUtil;
import base.util.TextUtil;


/**
 * 解析工具类
 * @author chenqp
 *
 */
public class AnalysisUtil {

	/**
	 * 将综资返回的串信息
	 * @param inStr
	 * @return
	 */
	public static String getWaitStr(String inStr){
		inStr = "{\"totalCount\":1,"
				+"\"root\":[{\"ID\":5874791,\"FLOW_ID\":1484190451946,\"FLOW_NO\":\"HN-751-161213-22\","
				+"\"FLOW_TYPE\":\"网络割接\",\"FLOW_TYPEID\":\"7\",\"PROCESSINSTID\":\"2782320\",\"TOPPROCESSINSTID\":\"2782320\","
				+"\"PROCESSDEFNAME\":\"com.inspur.app.hn.kanwu.process.kanwu\",\"PROCESSCHNAME\":\"勘误流程\","
				+"\"ACTIVITYINSTID\":\"19634851\",\"ACTIVITYDEFID\":\"zykwfd\",\"ACTIVITYINSTNAME\":\"综合资源接口人派发\","
				+"\"SEND_MAN\":\"罗丹\",\"SEND_MANID\":\"8911\",\"SEND_DEPT\":\"网络部\",\"PROCESSINSTNAME\":\"基站和传输跨专业关联无线网元电路缺失-长沙星沙步步高FZG-B870058TF\","
				+"\"CURRENT_STATE\":\"2\",\"FLOW_URL\":null,\"PARTICIPANTID\":\"8911\",\"PARTICIPANTNAME\":\"罗丹\",\"PARTICIPANTTYPE\":\"person\","
				+"\"PARTIINTYPE\":\"EXE\",\"CREATETIME\":\"2017-01-12 11:07:31\",\"WORKITEMID\":\"12330834\","
				+"\"CURRENTSTATE\":\"10\",\"EXTEND1\":null,\"ACTIONURL\":null,\"APPID\":\"irms\",\"LIMIT_TIME\":\"2017-01-14 11:07:31\","
				+"\"ACCEPT_TIME\":\"2017-01-12 11:07:31\",\"SENT_TASTNO\":null,\"COMPANYNAME\":\"长沙分公司\",\"VERSION\":\"1.1.1\",\"CRM_NO\":null,\"CUSTOM_NAME\":null,"
				+"\"WORKCREATETIME\":\"2017-01-12 11:09:38\",\"INSPUREXTEND2\":null,\"INSPUREXTEND3\":null,"
				+"\"APP_TYPE\":null,\"WHOLELIMITTIME\":\"1970-01-01 07:00:00\",\"SEND_COMPANYID\":478,\"KWFLAG\":\"电路缺失\","
				+"\"PROFERSIONAL\":\"传输\",\"NOWNUMBER\":1.78671296296296296296296296296296296296,"
				+"\"LIMITNUMBER\":null,\"RN\":1}"
				+"]}";
		String outStr = "";
		try{
			Map<String, Object> map= JsonUtil.getMap4Json(inStr);
			List<Map<String, Object>> list = JsonUtil.getList4Json(map.get("root")+"", Map.class);
			List<FormPojo> formList = new LinkedList();
			for(Map<String, Object> resMap : list){
				FormPojo formObj = AnalysisUtil.getWaitedStr(resMap);
				formList.add(formObj);
			}
			if(TextUtil.isNotNull(formList)){
				outStr = JsonUtil.getJsonString4List(formList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return outStr;
	}
	/**
	 * 得到实例
	 * @param map
	 * @return
	 */
	public static FormPojo getWaitedStr(Map<String, Object> map){
		FormPojo obj = new FormPojo();
		obj.setId(map.get("ID")+"");obj.setFlowId(map.get("FLOW_ID")+"");obj.setFlowNo(map.get("FLOW_NO")+"");
		obj.setFlowType(map.get("FLOW_TYPE")+"");obj.setFlowTypeId(map.get("FLOW_TYPEID")+"");obj.setProcessInstid(map.get("PROCESSINSTID")+"");
		obj.setProcessdefName(map.get("PROCESSDEFNAME")+"");obj.setProcesschName(map.get("PROCESSCHNAME")+"");
		obj.setActivityInstid(map.get("ACTIVITYINSTID")+"");obj.setActivityDefid(map.get("ACTIVITYDEFID")+"");
		obj.setSendMan(map.get("SEND_MAN")+"");obj.setSendManId(map.get("SEND_MANID")+"");obj.setSendDept(map.get("SEND_DEPT")+"");
		obj.setProcessinstName(map.get("PROCESSINSTNAME")+"");obj.setCurrentState(map.get("CURRENT_STATE")+"");
		obj.setParticiPantid(map.get("PARTICIPANTID")+"");obj.setParticiPantname(map.get("PARTICIPANTNAME")+"");
		obj.setParticiPanttype(map.get("PARTICIPANTTYPE")+"");obj.setCreateTime(map.get("CREATETIME")+"");
		obj.setWorkItemid(map.get("WORKITEMID")+""); obj.setLimitTime(map.get("LIMIT_TIME")+"");obj.setAcceptTime(map.get("ACCEPT_TIME")+"");
		obj.setCompanyName(map.get("COMPANYNAME")+"");obj.setProfersonal(map.get("PROFERSIONAL")+"");
		obj.setKwFlag(map.get("KWFLAG")+"");
		return obj;
	}
	
	/**
	 * 将综资返回的串信息
	 * @param inStr
	 * @return
	 */
	public static String getClaimedStr(String inStr){
		String outStr = "";
		/*inStr= "{\"totalCount\":1,"
				+"\"root\":[{\"ID\":4978982,\"FORMNO\":\"HN-751-160425-3\",\"TITLE\":\"郴州北湖区船洞路管道19号井勘误流程\","
				+"\"FLOWNAME\":\"管线勘误\",\"FLOWTYPE\":\"网络割接\",\"FLOWTYPEID\":\"7\",\"FLOWMODEL\":\"com.inspur.app.hn.kanwu.process.kanwuGx\","
				+"\"SENDTIME\":\"2016-04-25 15:46:28\",\"HANDLETIME\":\"2016-04-25 16:09:00\",\"HANDLEMAN\":\"马慧芳\","
				+"\"HANDLEMANID\":\"9292\",\"CURRENTSTATE\":3,\"PROCINSID\":\"1832977\",\"FLOWID\":1461570388571,"
				+"\"FLOWURL\":null,\"WPSORBPS\":\"bps\",\"SENT_TASTNO\":null,\"SENDMANID\":\"9292\",\"SENDMAN\":\"马慧芳\","
				+"\"APP_TYPE\":null,\"VERSION\":\"1.1.1\",\"CRM_NO\":null,\"CUSTOM_NAME\":null,\"RN\":1}]}";*/
		try{
			Map<String, Object> map= JsonUtil.getMap4Json(inStr);
			List<Map<String, Object>> list = JsonUtil.getList4Json(map.get("root")+"", Map.class);
			List<ClaimedPojo> formList = new LinkedList();
			for(Map<String, Object> resMap : list){
				ClaimedPojo cliamedObj = AnalysisUtil.getClaimedObj(resMap);
				formList.add(cliamedObj);
			}
			if(TextUtil.isNotNull(formList)){
				outStr = JsonUtil.getJsonString4List(formList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return outStr;
	}
	
	/**
	 * 得到工单
	 * @param map
	 * @return
	 */
	public static ClaimedPojo getClaimedObj(Map<String, Object> map){
		ClaimedPojo obj = new ClaimedPojo();
		obj.setId(map.get("ID")+"");obj.setFormNo(map.get("FORMNO")+"");
		obj.setTitle(map.get("TITLE")+"");obj.setFlowName(map.get("FLOWNAME")+"");
		obj.setFlowType(map.get("FLOWTYPE")+"");obj.setFlowTypeid(map.get("FLOWTYPEID")+"");
		obj.setFlowModel(map.get("FLOWMODEL")+"");obj.setSendTime(map.get("SENDTIME")+"");
		obj.setHandleTime(map.get("HANDLETIME")+"");obj.setHandleMan(map.get("HANDLEMAN")+"");
		obj.setHandleManid(map.get("HANDLEMANID")+"");obj.setCurrentState(map.get("CURRENTSTATE")+"");
		obj.setProcInstid(map.get("PROCINSID")+"");obj.setFlowId(map.get("FLOWID")+"");
		obj.setSendMan(map.get("SENDMAN")+"");obj.setSendManid(map.get("SENDMANID")+"");
		return obj;
	}
	
	
	
	/**
	 * 解析返回串
	 * @param inStr
	 * @return
	 */
	public static String getTaskInfo(String inStr){
		inStr ="{\"internalInfo\":[],\"message\":\"success\",\"recordInfo\":["
				+"{\"row\":["
				+"{\"fieldChName\":\"工单编号\",\"fieldContent\":\"HN-751-161213-22\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"工单主题\",\"fieldContent\":\"测试\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"派单人\",\"fieldContent\":\"丁毅\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"联系方式\",\"fieldContent\":\"13807316885\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"派单部门\",\"fieldContent\":\"网络部\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"派单时间\",\"fieldContent\":\"2016-04-08 09:57:23\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"受理时限\",\"fieldContent\":\"2016-04-11 10:00:28\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"处理时限\",\"fieldContent\":\"2016-04-11 10:00:28\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"勘误专业\",\"fieldContent\":\"管线\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"备注\",\"fieldContent\":\"123\",\"fieldEdit\":\"\",\"fieldEnName\":\"\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"}]"
				+"}],\"result\":\"\"}"
				+"";
		String outStr = "";
		try{
			Map<String, Object> map = JsonUtil.getMap4Json(inStr);
			String message = map.get("message")+"";
			if(TextUtil.isNotNull(message) && message.equals("success")){
				JSONArray array =  JSONArray.fromObject(map.get("recordInfo")+"");
				Iterator<Object> it = array.iterator();
				List<TaskInfoPojo> taskList = new LinkedList();
				while (it.hasNext()) {
	                JSONObject ob = (JSONObject) it.next();
	                JSONArray rowArray = JSONArray.fromObject(ob.get("row"));
	                Iterator<Object> subit = rowArray.iterator();
	                while(subit.hasNext()){
	                	TaskInfoPojo info = new TaskInfoPojo();
	                	JSONObject subObj = (JSONObject) subit.next();
	                	info.setFieldChName(subObj.get("fieldChName")+"");
	                	info.setFieldContent(subObj.get("fieldContent")+"");
	                	info.setFieldEnName(subObj.getString("fieldEnName")+"");
	                	info.setFieldEvent(subObj.get("fieldEvent")+"");
	                	taskList.add(info);
	                }
	            }
				outStr = JsonUtil.getJsonString4List(taskList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return outStr;
	}
	
	
	/**
	 * 得到历史信息
	 * @param inStr
	 * @return
	 */
	public static String getTaskHisInfo(String inStr){
		/*inStr ="{\"internalInfo\":[],\"message\":\"success\",\"recordInfo\":[{\"row\":["
				+"{\"fieldChName\":\"id\",\"fieldContent\":\"8089804\",\"fieldEdit\":\"\",\"fieldEnName\":\"id\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"},"
				+"{\"fieldChName\":\"workItemId\",\"fieldContent\":\"8169527\",\"fieldEdit\":\"\",\"fieldEnName\":\"workItemId\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"},"
				+"{\"fieldChName\":\"formId\",\"fieldContent\":\"4932875\",\"fieldEdit\":\"\",\"fieldEnName\":\"formId\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"},"
				+"{\"fieldChName\":\"activeId\",\"fieldContent\":\"12735249\",\"fieldEdit\":\"\",\"fieldEnName\":\"activeId\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"},"
				+"{\"fieldChName\":\"环节英文名\",\"fieldContent\":\"zykwfd\",\"fieldEdit\":\"\",\"fieldEnName\":\"activeName\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"},"
				+"{\"fieldChName\":\"环节名称\",\"fieldContent\":\"综合资源接口人派发\",\"fieldEdit\":\"\",\"fieldEnName\":\"activeDescribe\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"handleManId\",\"fieldContent\":\"8903\",\"fieldEdit\":\"\",\"fieldEnName\":\"handleManId\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"},"
				+"{\"fieldChName\":\"处理人员\",\"fieldContent\":\"杨曾\",\"fieldEdit\":\"\",\"fieldEnName\":\"handleMan\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"flowId\",\"fieldContent\":\"1460080643586\",\"fieldEdit\":\"\",\"fieldEnName\":\"flowId\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"},"
				+"{\"fieldChName\":\"处理时间\",\"fieldContent\":\"2016-04-08 14:12:05\",\"fieldEdit\":\"\",\"fieldEnName\":\"handleTime\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"processDefName\",\"fieldContent\":\"com.inspur.app.hn.kanwu.process.kanwuGx\",\"fieldEdit\":\"\",\"fieldEnName\":\"processDefName\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"}"
				+"]}]}";*/
		String outStr = "";
		try{
			Map<String, Object> map = JsonUtil.getMap4Json(inStr);
			String message = map.get("message")+"";
			if(TextUtil.isNotNull(message) && message.equals("success")){
				JSONArray array =  JSONArray.fromObject(map.get("recordInfo")+"");
				Iterator<Object> it = array.iterator();
				List<List<TaskInfoPojo>> taskList = new LinkedList();
				while (it.hasNext()) {
	                JSONObject ob = (JSONObject) it.next();
	                JSONArray rowArray = JSONArray.fromObject(ob.get("row"));
	                Iterator<Object> subit = rowArray.iterator();
	                List<TaskInfoPojo> subList = new LinkedList();
	                while(subit.hasNext()){
	                	TaskInfoPojo info = new TaskInfoPojo();
	                	JSONObject subObj = (JSONObject) subit.next();
	                	info.setFieldChName(subObj.get("fieldChName")+"");
	                	info.setFieldContent(subObj.get("fieldContent")+"");
	                	info.setFieldEnName(subObj.getString("fieldEnName")+"");
	                	info.setFieldEvent(subObj.get("fieldEvent")+"");
	                	subList.add(info);
	                }
	                taskList.add(subList);
	            }
				outStr = JsonUtil.getJsonString4List(taskList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return outStr;
	}
	
	/**
	 * 返回工单处理页面
	 * @param inStr
	 * @return
	 */
	public static String getTaskDealInfo(String inStr){
		/*inStr = "{\"internalInfo\":[],\"message\":\"success\",\"recordInfo\":["
				+"{\"row\":["
				+"{\"fieldChName\":\"处理人\",\"fieldContent\":\"赵自舜\",\"fieldEdit\":\"\",\"fieldEnName\":\"ownerName\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"联系方式\",\"fieldContent\":\"13787045449\",\"fieldEdit\":\"\",\"fieldEnName\":\"cellPhone\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"处理部门\",\"fieldContent\":\"浪潮(系统保留勿删)\",\"fieldEdit\":\"\",\"fieldEnName\":\"deptName\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"处理时间\",\"fieldContent\":\"2017-01-13 16:31:11\",\"fieldEdit\":\"\",\"fieldEnName\":\"dealTime\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"当前环节处理时限\",\"fieldContent\":\"\",\"fieldEdit\":\"\",\"fieldEnName\":\"currentLimitTime\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"受理时限\",\"fieldContent\":\"2017-01-15 16:31:11\",\"fieldEdit\":\"\",\"fieldEnName\":\"acceptTime\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"},"
				+"{\"fieldChName\":\"回复时限\",\"fieldContent\":\"2017-01-17 16:31:11\",\"fieldEdit\":\"\",\"fieldEnName\":\"replyTime\",\"fieldEvent\":\"false\",\"fieldView\":\"0\"},"
				+"{\"fieldChName\":\"处理人\",\"fieldContent\":\"\",\"fieldEdit\":\"\",\"fieldEnName\":\"ownerName\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"联系方式\",\"fieldContent\":\"\",\"fieldEdit\":\"\",\"fieldEnName\":\"cellPhone\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"处理部门\",\"fieldContent\":\"\",\"fieldEdit\":\"\",\"fieldEnName\":\"deptName\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"处理时间\",\"fieldContent\":\"\",\"fieldEdit\":\"\",\"fieldEnName\":\"dealTime\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"当前环节处理时限\",\"fieldContent\":\"\",\"fieldEdit\":\"\",\"fieldEnName\":\"currentLimitTime\",\"fieldEvent\":\"false\",\"fieldView\":\"1\"},"
				+"{\"fieldChName\":\"审核意见\",\"fieldContent\":\"\",\"fieldEdit\":\"\",\"fieldEnName\":\"isPass\",\"fieldEvent\":\"false\",\"fieldView\":\"4\"},"
				+"{\"fieldChName\":\"处理意见\",\"fieldContent\":\"\",\"fieldEdit\":\"\",\"fieldEnName\":\"remark\",\"fieldEvent\":\"false\",\"fieldView\":\"2B\"},"
				+"{\"fieldChName\":\"提交\",\"fieldContent\":\"\",\"fieldEdit\":\"\",\"fieldEnName\":\"submit\",\"fieldEvent\":\"false\",\"fieldView\":\"5A\"}"
				+"]}"
				+"],\"result\":\"1\"}";*/
		String outStr = "";
		try{
			Map<String, Object> map = JsonUtil.getMap4Json(inStr);
			String message = map.get("message")+"";
			if(TextUtil.isNotNull(message) && message.equals("success")){
				JSONArray array =  JSONArray.fromObject(map.get("recordInfo")+"");
				Iterator<Object> it = array.iterator();
				List<TaskInfoPojo> taskList = new LinkedList();
				while (it.hasNext()) {
	                JSONObject ob = (JSONObject) it.next();
	                JSONArray rowArray = JSONArray.fromObject(ob.get("row"));
	                Iterator<Object> subit = rowArray.iterator();
	                while(subit.hasNext()){
	                	TaskInfoPojo info = new TaskInfoPojo();
	                	JSONObject subObj = (JSONObject) subit.next();
	                	info.setFieldChName(subObj.get("fieldChName")+"");
	                	info.setFieldContent(subObj.get("fieldContent")+"");
	                	info.setFieldEnName(subObj.getString("fieldEnName")+"");
	                	info.setFieldEvent(subObj.get("fieldEvent")+"");
	                	taskList.add(info);
	                }
	            }
				outStr = JsonUtil.getJsonString4List(taskList);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return outStr;
	}
	
	/**
	 * 得到解析列表
	 * @param inStr
	 * @return
	 */
	public static String getAssignDealer(String inStr){
		/*if(inStr.contains("company")){
			inStr ="{\"totalCount\":1,\"root\":[{\"id\":\"8907\",\"parentId\":\"\",\"text\":\"长沙分公司\"}]}";
		}else if(inStr.contains("dept")){
			inStr ="{\"totalCount\":2,\"root\":[{\"id\":\"8908\",\"parentId\":\"8907\",\"text\":\"网运部\"},"
					+ "{\"id\":\"8909\",\"parentId\":\"8907\",\"text\":\"网管中心\"}]}";
		}else {
			inStr ="{\"totalCount\":4,\"root\":[{\"id\":\"9907\",\"parentId\":\"8908\",\"text\":\"陶特勇\"},"
					+ "{\"id\":\"9908\",\"parentId\":\"8908\",\"text\":\"李四\"},"
					+ "{\"id\":\"9909\",\"parentId\":\"8909\",\"text\":\"张三\"},"
					+ "{\"id\":\"9910\",\"parentId\":\"8909\",\"text\":\"王二麻子\"}]}";
		}*/
		String outStr = "";
		try{
			Map<String, Object> map = JsonUtil.getMap4Json(inStr);
			JSONArray array =  JSONArray.fromObject(map.get("root")+"");
			Iterator<Object> it = array.iterator();
			List<AssignDealerPojo> list = new LinkedList();
			while (it.hasNext()) {
                JSONObject ob = (JSONObject) it.next();
                AssignDealerPojo obj = new AssignDealerPojo();
                obj.setId(ob.get("id")+"");
                obj.setParentId(ob.getString("parentId"));
                obj.setText(ob.getString("text"));
                list.add(obj);
            }
			outStr = JsonUtil.getJsonString4List(list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return outStr;
	}
	
	public static void main(String[] args) {
		//String inStr = "{\"totalCount\":1,\"root\":[{\"id\":\"478\",\"parentId\":\"\",\"text\":\"长沙分公司\"}]}";
		//String inStr ="{\"totalCount\":24,\"root\":[{\"id\":\"2480\",\"parentId\":\"478\",\"text\":\"公共资源\"},{\"id\":\"2500\",\"parentId\":\"478\",\"text\":\"集客专业\"},{\"id\":\"2540\",\"parentId\":\"478\",\"text\":\"集客流程\"},{\"id\":\"2561\",\"parentId\":\"478\",\"text\":\"test\"},{\"id\":\"2742\",\"parentId\":\"478\",\"text\":\"家客流程\"},{\"id\":\"2762\",\"parentId\":\"478\",\"text\":\"天心区铁通\"},{\"id\":\"2782\",\"parentId\":\"478\",\"text\":\"望城区铁通\"},{\"id\":\"2280\",\"parentId\":\"478\",\"text\":\"网络部\"},{\"id\":\"2281\",\"parentId\":\"478\",\"text\":\"交换组\"},{\"id\":\"2282\",\"parentId\":\"478\",\"text\":\"网络部\"},{\"id\":\"2283\",\"parentId\":\"478\",\"text\":\"交换组\"},{\"id\":\"2286\",\"parentId\":\"478\",\"text\":\"交换组\"},{\"id\":\"2342\",\"parentId\":\"478\",\"text\":\"卫星同步专业\"},{\"id\":\"2344\",\"parentId\":\"478\",\"text\":\"卫星同步组\"},{\"id\":\"2360\",\"parentId\":\"478\",\"text\":\"动环专业\"},{\"id\":\"2376\",\"parentId\":\"478\",\"text\":\"无线专业\"},{\"id\":\"2392\",\"parentId\":\"478\",\"text\":\"传送专业\"},{\"id\":\"2408\",\"parentId\":\"478\",\"text\":\"数据专业\"},{\"id\":\"3042\",\"parentId\":\"478\",\"text\":\"财务部\"},{\"id\":\"3082\",\"parentId\":\"478\",\"text\":\"家客代维\"},{\"id\":\"3182\",\"parentId\":\"478\",\"text\":\"EOMS自维\"},{\"id\":\"13042\",\"parentId\":\"478\",\"text\":\"网优中心\"},{\"id\":\"13069\",\"parentId\":\"478\",\"text\":\"公司领导\"},{\"id\":\"13174\",\"parentId\":\"478\",\"text\":\"工建中心\"}]}";
		//String inStr = "{\"totalCount\":5,\"root\":[{\"ID\":5891100,\"FORMNO\":\"HN-751-170213-10\",\"TITLE\":\"测试152\",\"FLOWNAME\":\"勘误流程\",\"FLOWTYPE\":\"网络割接\",\"FLOWTYPEID\":\"7\",\"FLOWMODEL\":\"com.inspur.app.hn.kanwu.process.kanwuGx\",\"SENDTIME\":\"2017-02-13 11:16:42\",\"HANDLETIME\":\"2017-02-21 16:07:08\",\"HANDLEMAN\":\"资费\",\"HANDLEMANID\":\"9722\",\"CURRENTSTATE\":2,\"PROCINSID\":\"2800717\",\"FLOWID\":1486955803190,\"FLOWURL\":null,\"WPSORBPS\":\"bps\",\"SENT_TASTNO\":null,\"SENDMANID\":\"42484\",\"SENDMAN\":\"root\",\"APP_TYPE\":null,\"VERSION\":\"1.1.1\",\"CRM_NO\":null,\"CUSTOM_NAME\":null,\"RN\":1},{\"ID\":5891100,\"FORMNO\":\"HN-751-170213-10\",\"TITLE\":\"测试152\",\"FLOWNAME\":\"勘误流程\",\"FLOWTYPE\":\"网络割接\",\"FLOWTYPEID\":\"7\",\"FLOWMODEL\":\"com.inspur.app.hn.kanwu.process.kanwuGx\",\"SENDTIME\":\"2017-02-13 11:16:42\",\"HANDLETIME\":\"2017-02-21 15:53:10\",\"HANDLEMAN\":\"资费\",\"HANDLEMANID\":\"9722\",\"CURRENTSTATE\":2,\"PROCINSID\":\"2800717\",\"FLOWID\":1486955803190,\"FLOWURL\":null,\"WPSORBPS\":\"bps\",\"SENT_TASTNO\":null,\"SENDMANID\":\"42484\",\"SENDMAN\":\"root\",\"APP_TYPE\":null,\"VERSION\":\"1.1.1\",\"CRM_NO\":null,\"CUSTOM_NAME\":null,\"RN\":2},{\"ID\":5891100,\"FORMNO\":\"HN-751-170213-10\",\"TITLE\":\"测试152\",\"FLOWNAME\":\"勘误流程\",\"FLOWTYPE\":\"网络割接\",\"FLOWTYPEID\":\"7\",\"FLOWMODEL\":\"com.inspur.app.hn.kanwu.process.kanwuGx\",\"SENDTIME\":\"2017-02-13 11:16:42\",\"HANDLETIME\":\"2017-02-21 15:34:32\",\"HANDLEMAN\":\"资费\",\"HANDLEMANID\":\"9722\",\"CURRENTSTATE\":2,\"PROCINSID\":\"2800717\",\"FLOWID\":1486955803190,\"FLOWURL\":null,\"WPSORBPS\":\"bps\",\"SENT_TASTNO\":null,\"SENDMANID\":\"42484\",\"SENDMAN\":\"root\",\"APP_TYPE\":null,\"VERSION\":\"1.1.1\",\"CRM_NO\":null,\"CUSTOM_NAME\":null,\"RN\":3},{\"ID\":5891100,\"FORMNO\":\"HN-751-170213-10\",\"TITLE\":\"测试152\",\"FLOWNAME\":\"勘误流程\",\"FLOWTYPE\":\"网络割接\",\"FLOWTYPEID\":\"7\",\"FLOWMODEL\":\"com.inspur.app.hn.kanwu.process.kanwuGx\",\"SENDTIME\":\"2017-02-13 11:16:42\",\"HANDLETIME\":\"2017-02-21 14:48:54\",\"HANDLEMAN\":\"资费\",\"HANDLEMANID\":\"9722\",\"CURRENTSTATE\":2,\"PROCINSID\":\"2800717\",\"FLOWID\":1486955803190,\"FLOWURL\":null,\"WPSORBPS\":\"bps\",\"SENT_TASTNO\":null,\"SENDMANID\":\"42484\",\"SENDMAN\":\"root\",\"APP_TYPE\":null,\"VERSION\":\"1.1.1\",\"CRM_NO\":null,\"CUSTOM_NAME\":null,\"RN\":4},{\"ID\":5891100,\"FORMNO\":\"HN-751-170213-10\",\"TITLE\":\"测试152\",\"FLOWNAME\":\"勘误流程\",\"FLOWTYPE\":\"网络割接\",\"FLOWTYPEID\":\"7\",\"FLOWMODEL\":\"com.inspur.app.hn.kanwu.process.kanwuGx\",\"SENDTIME\":\"2017-02-13 11:16:42\",\"HANDLETIME\":\"2017-02-21 14:43:07\",\"HANDLEMAN\":\"资费\",\"HANDLEMANID\":\"9722\",\"CURRENTSTATE\":2,\"PROCINSID\":\"2800717\",\"FLOWID\":1486955803190,\"FLOWURL\":null,\"WPSORBPS\":\"bps\",\"SENT_TASTNO\":null,\"SENDMANID\":\"42484\",\"SENDMAN\":\"root\",\"APP_TYPE\":null,\"VERSION\":\"1.1.1\",\"CRM_NO\":null,\"CUSTOM_NAME\":null,\"RN\":5}]}";
		String inStr ="{\"totalCount\":458,\"root\":[{\"id\":\"9718\",\"parentId\":\"2280\",\"text\":\"伍建华（账号已废除，勿选）\"},{\"id\":\"9720\",\"parentId\":\"2280\",\"text\":\"屈海翔\"},{\"id\":\"9721\",\"parentId\":\"2280\",\"text\":\"李帅（账号已废除，勿选）\"},{\"id\":\"9722\",\"parentId\":\"2280\",\"text\":\"资费\"},{\"id\":\"9723\",\"parentId\":\"2280\",\"text\":\"毛昱昊\"},{\"id\":\"8650\",\"parentId\":\"2280\",\"text\":\"长沙电调测试（账号已废除，勿选）\"},{\"id\":\"8724\",\"parentId\":\"2280\",\"text\":\"黎翊文\"},{\"id\":\"42578\",\"parentId\":\"2280\",\"text\":\"杨宏宇（账号已废除，勿选）\"},{\"id\":\"42586\",\"parentId\":\"2280\",\"text\":\"周新\"},{\"id\":\"8706\",\"parentId\":\"2280\",\"text\":\"陈飞宇\"},{\"id\":\"8707\",\"parentId\":\"2280\",\"text\":\"郑毅\"},{\"id\":\"8708\",\"parentId\":\"2280\",\"text\":\"贺云松\"},{\"id\":\"8709\",\"parentId\":\"2280\",\"text\":\"薛颖\"},{\"id\":\"8710\",\"parentId\":\"2280\",\"text\":\"陈陶\"},{\"id\":\"8711\",\"parentId\":\"2280\",\"text\":\"陈刚\"},{\"id\":\"8712\",\"parentId\":\"2280\",\"text\":\"梁京\"},{\"id\":\"8713\",\"parentId\":\"2280\",\"text\":\"吴洪\"},{\"id\":\"8715\",\"parentId\":\"2280\",\"text\":\"夏天\"},{\"id\":\"8716\",\"parentId\":\"2280\",\"text\":\"李强\"},{\"id\":\"8717\",\"parentId\":\"2280\",\"text\":\"田维清\"},{\"id\":\"8718\",\"parentId\":\"2280\",\"text\":\"朱毅\"},{\"id\":\"8719\",\"parentId\":\"2280\",\"text\":\"张哲川\"},{\"id\":\"8720\",\"parentId\":\"2280\",\"text\":\"王敏\"},{\"id\":\"8721\",\"parentId\":\"2280\",\"text\":\"郭唯（账号已废除，勿选）\"},{\"id\":\"8722\",\"parentId\":\"2280\",\"text\":\"邓权湘\"},{\"id\":\"8723\",\"parentId\":\"2280\",\"text\":\"付聪\"},{\"id\":\"8902\",\"parentId\":\"2280\",\"text\":\"周坚\"},{\"id\":\"8903\",\"parentId\":\"2280\",\"text\":\"杨曾\"},{\"id\":\"8904\",\"parentId\":\"2280\",\"text\":\"朱小波（账号已废除，勿选）\"},{\"id\":\"8905\",\"parentId\":\"2280\",\"text\":\"周红宇\"},{\"id\":\"8906\",\"parentId\":\"2280\",\"text\":\"许淼\"},{\"id\":\"8908\",\"parentId\":\"2280\",\"text\":\"吴臻（账号已废除，勿选）\"},{\"id\":\"8909\",\"parentId\":\"2280\",\"text\":\"傅明亮\"},{\"id\":\"8910\",\"parentId\":\"2280\",\"text\":\"徐帆\"},{\"id\":\"8911\",\"parentId\":\"2280\",\"text\":\"罗丹\"},{\"id\":\"8912\",\"parentId\":\"2280\",\"text\":\"李研\"},{\"id\":\"8913\",\"parentId\":\"2280\",\"text\":\"苏鹏程\"},{\"id\":\"8914\",\"parentId\":\"2280\",\"text\":\"孙正军\"},{\"id\":\"8915\",\"parentId\":\"2280\",\"text\":\"陶斐\"},{\"id\":\"8916\",\"parentId\":\"2280\",\"text\":\"李丹\"},{\"id\":\"8917\",\"parentId\":\"2280\",\"text\":\"刘琪\"},{\"id\":\"42531\",\"parentId\":\"2280\",\"text\":\"饶帅\"},{\"id\":\"10053\",\"parentId\":\"2280\",\"text\":\"陈子青\"},{\"id\":\"10054\",\"parentId\":\"2280\",\"text\":\"何索\"},{\"id\":\"43918\",\"parentId\":\"2280\",\"text\":\"张萱（账号已废除，勿选）\"},{\"id\":\"43919\",\"parentId\":\"2280\",\"text\":\"封晓燕（账号已废除，勿选）\"},{\"id\":\"43109\",\"parentId\":\"2280\",\"text\":\"黄蕊\"},{\"id\":\"43110\",\"parentId\":\"2280\",\"text\":\"袁义（账号已废除，勿选）\"},{\"id\":\"43111\",\"parentId\":\"2280\",\"text\":\"吴列（账号已废除，勿选）\"},{\"id\":\"43112\",\"parentId\":\"2280\",\"text\":\"李文\"},{\"id\":\"43113\",\"parentId\":\"2280\",\"text\":\"李飞\"},{\"id\":\"43114\",\"parentId\":\"2280\",\"text\":\"邓雍琰\"},{\"id\":\"43115\",\"parentId\":\"2280\",\"text\":\"谭绍彦\"},{\"id\":\"43116\",\"parentId\":\"2280\",\"text\":\"杨明\"},{\"id\":\"43117\",\"parentId\":\"2280\",\"text\":\"张冶\"},{\"id\":\"43118\",\"parentId\":\"2280\",\"text\":\"雷灿\"},{\"id\":\"43119\",\"parentId\":\"2280\",\"text\":\"谢志平\"},{\"id\":\"43120\",\"parentId\":\"2280\",\"text\":\"石家维（账号已废除，勿选）\"},{\"id\":\"43121\",\"parentId\":\"2280\",\"text\":\"胡惠文\"},{\"id\":\"43122\",\"parentId\":\"2280\",\"text\":\"尹君\"},{\"id\":\"43123\",\"parentId\":\"2280\",\"text\":\"王梦洁\"},{\"id\":\"43125\",\"parentId\":\"2280\",\"text\":\"邓琦\"},{\"id\":\"43126\",\"parentId\":\"2280\",\"text\":\"滕贻茂\"},{\"id\":\"43127\",\"parentId\":\"2280\",\"text\":\"谭植尹\"},{\"id\":\"43128\",\"parentId\":\"2280\",\"text\":\"杨家宏\"},{\"id\":\"43129\",\"parentId\":\"2280\",\"text\":\"杨果\"},{\"id\":\"43130\",\"parentId\":\"2280\",\"text\":\"张鸿斌\"},{\"id\":\"43131\",\"parentId\":\"2280\",\"text\":\"唐也\"},{\"id\":\"43132\",\"parentId\":\"2280\",\"text\":\"唐竞成（账号已废除，勿选）\"},{\"id\":\"43133\",\"parentId\":\"2280\",\"text\":\"江波\"},{\"id\":\"43134\",\"parentId\":\"2280\",\"text\":\"彭严\"},{\"id\":\"43135\",\"parentId\":\"2280\",\"text\":\"胡铁牛\"},{\"id\":\"43136\",\"parentId\":\"2280\",\"text\":\"李骥翩\"},{\"id\":\"43137\",\"parentId\":\"2280\",\"text\":\"侯乐天\"},{\"id\":\"43138\",\"parentId\":\"2280\",\"text\":\"陈龙\"},{\"id\":\"43139\",\"parentId\":\"2280\",\"text\":\"李文利（账号已废除，勿选）\"},{\"id\":\"43140\",\"parentId\":\"2280\",\"text\":\"范孝春\"},{\"id\":\"43141\",\"parentId\":\"2280\",\"text\":\"袁田\"},{\"id\":\"43142\",\"parentId\":\"2280\",\"text\":\"阳正凯\"},{\"id\":\"43143\",\"parentId\":\"2280\",\"text\":\"周景龙\"},{\"id\":\"43144\",\"parentId\":\"2280\",\"text\":\"谭亮（账号已废除，勿选）\"},{\"id\":\"43145\",\"parentId\":\"2280\",\"text\":\"谌颖亮\"},{\"id\":\"43146\",\"parentId\":\"2280\",\"text\":\"严刚\"},{\"id\":\"43147\",\"parentId\":\"2280\",\"text\":\"刘兴\"},{\"id\":\"43148\",\"parentId\":\"2280\",\"text\":\"赵崇理\"},{\"id\":\"43149\",\"parentId\":\"2280\",\"text\":\"叶涛祥\"},{\"id\":\"43150\",\"parentId\":\"2280\",\"text\":\"刘创（账号已废除，勿选）\"},{\"id\":\"43151\",\"parentId\":\"2280\",\"text\":\"龙泳（账号已废除，勿选）\"},{\"id\":\"43152\",\"parentId\":\"2280\",\"text\":\"刘泽勇（账号已废除，勿选）\"},{\"id\":\"43153\",\"parentId\":\"2280\",\"text\":\"汪进（账号已废除，勿选）\"},{\"id\":\"43154\",\"parentId\":\"2280\",\"text\":\"肖腾\"},{\"id\":\"43155\",\"parentId\":\"2280\",\"text\":\"瞿湘湘\"},{\"id\":\"43156\",\"parentId\":\"2280\",\"text\":\"张文治\"},{\"id\":\"43157\",\"parentId\":\"2280\",\"text\":\"赵亮（账号已废除，勿选）\"},{\"id\":\"43158\",\"parentId\":\"2280\",\"text\":\"叶剑（账号已废除，勿选）\"},{\"id\":\"43159\",\"parentId\":\"2280\",\"text\":\"郭鸿媛（账号已废除，勿选）\"},{\"id\":\"43160\",\"parentId\":\"2280\",\"text\":\"彭世伟（账号已废除，勿选）\"},{\"id\":\"43161\",\"parentId\":\"2280\",\"text\":\"刘家乐（账号已废除，勿选）\"},{\"id\":\"43162\",\"parentId\":\"2280\",\"text\":\"汪建国（账号已废除，勿选）\"},{\"id\":\"43163\",\"parentId\":\"2280\",\"text\":\"刘雪群（账号已废除，勿选）\"},{\"id\":\"43164\",\"parentId\":\"2280\",\"text\":\"杨艳（账号已废除，勿选）\"},{\"id\":\"43165\",\"parentId\":\"2280\",\"text\":\"关金星（账号已废除，勿选）\"},{\"id\":\"43166\",\"parentId\":\"2280\",\"text\":\"李铮\"},{\"id\":\"43168\",\"parentId\":\"2280\",\"text\":\"冯亚雄（账号已废除，勿选）\"},{\"id\":\"43169\",\"parentId\":\"2280\",\"text\":\"罗行之（账号已废除，勿选）\"},{\"id\":\"43170\",\"parentId\":\"2280\",\"text\":\"虢浩（账号已废除，勿选）\"},{\"id\":\"43171\",\"parentId\":\"2280\",\"text\":\"盛振浩（账号已废除，勿选）\"},{\"id\":\"43172\",\"parentId\":\"2280\",\"text\":\"江红\"},{\"id\":\"43173\",\"parentId\":\"2280\",\"text\":\"纪伟民（账号已废除，勿选）\"},{\"id\":\"43174\",\"parentId\":\"2280\",\"text\":\"陈红专（账号已废除，勿选）\"},{\"id\":\"42831\",\"parentId\":\"2280\",\"text\":\"易斌\"},{\"id\":\"42832\",\"parentId\":\"2280\",\"text\":\"郭厦\"},{\"id\":\"42833\",\"parentId\":\"2280\",\"text\":\"余映天\"},{\"id\":\"42834\",\"parentId\":\"2280\",\"text\":\"尹煌\"},{\"id\":\"43175\",\"parentId\":\"2280\",\"text\":\"程赠文（账号已废除，勿选）\"},{\"id\":\"43667\",\"parentId\":\"2280\",\"text\":\"杜宏伟（账号已废除，勿选）\"},{\"id\":\"43659\",\"parentId\":\"2280\",\"text\":\"刘娟\"},{\"id\":\"43660\",\"parentId\":\"2280\",\"text\":\"雷紫薇\"},{\"id\":\"43661\",\"parentId\":\"2280\",\"text\":\"李炉焦（账号已废除，勿选）\"},{\"id\":\"43668\",\"parentId\":\"2280\",\"text\":\"谢斌\"},{\"id\":\"43669\",\"parentId\":\"2280\",\"text\":\"刘鑫\"},{\"id\":\"43670\",\"parentId\":\"2280\",\"text\":\"鲁钊\"},{\"id\":\"43671\",\"parentId\":\"2280\",\"text\":\"游张扬（账号已废除，勿选）\"},{\"id\":\"43673\",\"parentId\":\"2280\",\"text\":\"刘鹏\"},{\"id\":\"43674\",\"parentId\":\"2280\",\"text\":\"王浩\"},{\"id\":\"43675\",\"parentId\":\"2280\",\"text\":\"沈益锋\"},{\"id\":\"43662\",\"parentId\":\"2280\",\"text\":\"汪敏\"},{\"id\":\"9961\",\"parentId\":\"2280\",\"text\":\"崔海\"},{\"id\":\"9962\",\"parentId\":\"2280\",\"text\":\"彭烨（账号已废除，勿选）\"},{\"id\":\"9963\",\"parentId\":\"2280\",\"text\":\"田飞\"},{\"id\":\"9964\",\"parentId\":\"2280\",\"text\":\"周才录\"},{\"id\":\"9965\",\"parentId\":\"2280\",\"text\":\"温泉\"},{\"id\":\"9966\",\"parentId\":\"2280\",\"text\":\"冯湘韶\"},{\"id\":\"9967\",\"parentId\":\"2280\",\"text\":\"李海亮\"},{\"id\":\"9968\",\"parentId\":\"2280\",\"text\":\"罗华\"},{\"id\":\"9969\",\"parentId\":\"2280\",\"text\":\"张弛\"},{\"id\":\"9970\",\"parentId\":\"2280\",\"text\":\"曾庆微\"},{\"id\":\"9971\",\"parentId\":\"2280\",\"text\":\"吴波\"},{\"id\":\"9972\",\"parentId\":\"2280\",\"text\":\"严凯平（账号已废除，勿选）\"},{\"id\":\"9973\",\"parentId\":\"2280\",\"text\":\"喻彩斌（账号已废除，勿选）\"},{\"id\":\"9974\",\"parentId\":\"2280\",\"text\":\"易立军\"},{\"id\":\"43288\",\"parentId\":\"2280\",\"text\":\"赵杰能\"},{\"id\":\"43558\",\"parentId\":\"2280\",\"text\":\"张南辉\"},{\"id\":\"43559\",\"parentId\":\"2280\",\"text\":\"叶红（账号已废除，勿选）\"},{\"id\":\"43560\",\"parentId\":\"2280\",\"text\":\"曾羽\"},{\"id\":\"43177\",\"parentId\":\"2280\",\"text\":\"彭鹏（账号已废除，勿选）\"},{\"id\":\"43176\",\"parentId\":\"2280\",\"text\":\"侯勇（账号已废除，勿选）\"},{\"id\":\"43178\",\"parentId\":\"2280\",\"text\":\"鲍建军（账号已废除，勿选）\"},{\"id\":\"43179\",\"parentId\":\"2280\",\"text\":\"胡小林（账号已废除，勿选）\"},{\"id\":\"43180\",\"parentId\":\"2280\",\"text\":\"苟蕾蕾（账号已废除，勿选）\"},{\"id\":\"43183\",\"parentId\":\"2280\",\"text\":\"柳周（账号已废除，勿选）\"},{\"id\":\"43184\",\"parentId\":\"2280\",\"text\":\"黄一雄（账号已废除，勿选）\"},{\"id\":\"43185\",\"parentId\":\"2280\",\"text\":\"谭觅\"},{\"id\":\"43186\",\"parentId\":\"2280\",\"text\":\"柳佳（账号已废除，勿选）\"},{\"id\":\"43187\",\"parentId\":\"2280\",\"text\":\"杨璧瑕（账号已废除，勿选）\"},{\"id\":\"43188\",\"parentId\":\"2280\",\"text\":\"张江月\"},{\"id\":\"43189\",\"parentId\":\"2280\",\"text\":\"谢波（账号已废除，勿选）\"},{\"id\":\"43190\",\"parentId\":\"2280\",\"text\":\"梁娟（账号已废除，勿选）\"},{\"id\":\"43191\",\"parentId\":\"2280\",\"text\":\"马渲力\"},{\"id\":\"43192\",\"parentId\":\"2280\",\"text\":\"毛勇（账号已废除，勿选）\"},{\"id\":\"43193\",\"parentId\":\"2280\",\"text\":\"刘泛中（账号已废除，勿选）\"},{\"id\":\"43194\",\"parentId\":\"2280\",\"text\":\"潘运伦（账号已废除，勿选）\"},{\"id\":\"43195\",\"parentId\":\"2280\",\"text\":\"朱蓓经（账号已废除，勿选）\"},{\"id\":\"43196\",\"parentId\":\"2280\",\"text\":\"王博\"},{\"id\":\"43197\",\"parentId\":\"2280\",\"text\":\"王忠民（账号已废除，勿选）\"},{\"id\":\"43198\",\"parentId\":\"2280\",\"text\":\"李天添（账号已废除，勿选）\"},{\"id\":\"43199\",\"parentId\":\"2280\",\"text\":\"高勇\"},{\"id\":\"43200\",\"parentId\":\"2280\",\"text\":\"刘国华\"},{\"id\":\"43201\",\"parentId\":\"2280\",\"text\":\"彭锦超\"},{\"id\":\"43202\",\"parentId\":\"2280\",\"text\":\"黄茁\"},{\"id\":\"43203\",\"parentId\":\"2280\",\"text\":\"吴东山（账号已废除，勿选）\"},{\"id\":\"43204\",\"parentId\":\"2280\",\"text\":\"何兵\"},{\"id\":\"43206\",\"parentId\":\"2280\",\"text\":\"刘一玮\"},{\"id\":\"43207\",\"parentId\":\"2280\",\"text\":\"宁红斌\"},{\"id\":\"43208\",\"parentId\":\"2280\",\"text\":\"陈亚平\"},{\"id\":\"43209\",\"parentId\":\"2280\",\"text\":\"杜学其\"},{\"id\":\"43210\",\"parentId\":\"2280\",\"text\":\"曹林\"},{\"id\":\"43211\",\"parentId\":\"2280\",\"text\":\"唐云山\"},{\"id\":\"43212\",\"parentId\":\"2280\",\"text\":\"任治华\"},{\"id\":\"43213\",\"parentId\":\"2280\",\"text\":\"郭炬\"},{\"id\":\"43214\",\"parentId\":\"2280\",\"text\":\"李定-长沙\"},{\"id\":\"43215\",\"parentId\":\"2280\",\"text\":\"雷音\"},{\"id\":\"43216\",\"parentId\":\"2280\",\"text\":\"张翔\"},{\"id\":\"43217\",\"parentId\":\"2280\",\"text\":\"贺达\"},{\"id\":\"43218\",\"parentId\":\"2280\",\"text\":\"沈立嘉\"},{\"id\":\"43219\",\"parentId\":\"2280\",\"text\":\"曾庆民\"},{\"id\":\"43220\",\"parentId\":\"2280\",\"text\":\"张铮\"},{\"id\":\"43221\",\"parentId\":\"2280\",\"text\":\"黄斌\"},{\"id\":\"43222\",\"parentId\":\"2280\",\"text\":\"刘耀华\"},{\"id\":\"43223\",\"parentId\":\"2280\",\"text\":\"李淼\"},{\"id\":\"43224\",\"parentId\":\"2280\",\"text\":\"陈进\"},{\"id\":\"43225\",\"parentId\":\"2280\",\"text\":\"曹东友\"},{\"id\":\"43226\",\"parentId\":\"2280\",\"text\":\"刘湘平\"},{\"id\":\"43227\",\"parentId\":\"2280\",\"text\":\"倪欢\"},{\"id\":\"43228\",\"parentId\":\"2280\",\"text\":\"潘登\"},{\"id\":\"43229\",\"parentId\":\"2280\",\"text\":\"彭浩\"},{\"id\":\"43230\",\"parentId\":\"2280\",\"text\":\"黄蓉\"},{\"id\":\"43231\",\"parentId\":\"2280\",\"text\":\"罗印桢\"},{\"id\":\"43232\",\"parentId\":\"2280\",\"text\":\"彭美艳\"},{\"id\":\"43233\",\"parentId\":\"2280\",\"text\":\"贺伟\"},{\"id\":\"43234\",\"parentId\":\"2280\",\"text\":\"陈璇（账号已废除，勿选）\"},{\"id\":\"43235\",\"parentId\":\"2280\",\"text\":\"屈力佳\"},{\"id\":\"43236\",\"parentId\":\"2280\",\"text\":\"刘洋\"},{\"id\":\"43237\",\"parentId\":\"2280\",\"text\":\"唐钟恩（账号已废除，勿选）\"},{\"id\":\"43238\",\"parentId\":\"2280\",\"text\":\"陈圆（账号已废除，勿选）\"},{\"id\":\"43239\",\"parentId\":\"2280\",\"text\":\"黄超\"},{\"id\":\"43240\",\"parentId\":\"2280\",\"text\":\"罗容（账号已废除，勿选）\"},{\"id\":\"43241\",\"parentId\":\"2280\",\"text\":\"朱洁\"},{\"id\":\"43242\",\"parentId\":\"2280\",\"text\":\"金敏\"},{\"id\":\"43243\",\"parentId\":\"2280\",\"text\":\"陈舒\"},{\"id\":\"43244\",\"parentId\":\"2280\",\"text\":\"蒋炼（账号已废除，勿选）\"},{\"id\":\"43245\",\"parentId\":\"2280\",\"text\":\"陶周武\"},{\"id\":\"43246\",\"parentId\":\"2280\",\"text\":\"张冉\"},{\"id\":\"43247\",\"parentId\":\"2280\",\"text\":\"郑广\"},{\"id\":\"43248\",\"parentId\":\"2280\",\"text\":\"彭珊\"},{\"id\":\"43249\",\"parentId\":\"2280\",\"text\":\"莫丽梅\"},{\"id\":\"43250\",\"parentId\":\"2280\",\"text\":\"唐洁\"},{\"id\":\"43251\",\"parentId\":\"2280\",\"text\":\"徐恋\"},{\"id\":\"43252\",\"parentId\":\"2280\",\"text\":\"傅林\"},{\"id\":\"43253\",\"parentId\":\"2280\",\"text\":\"杨芹\"},{\"id\":\"43254\",\"parentId\":\"2280\",\"text\":\"岳林轩\"},{\"id\":\"43255\",\"parentId\":\"2280\",\"text\":\"彭淼\"},{\"id\":\"43256\",\"parentId\":\"2280\",\"text\":\"孙莹\"},{\"id\":\"43257\",\"parentId\":\"2280\",\"text\":\"唐倩（账号已废除，勿选）\"},{\"id\":\"43258\",\"parentId\":\"2280\",\"text\":\"郑靖\"},{\"id\":\"43259\",\"parentId\":\"2280\",\"text\":\"王瑞国\"},{\"id\":\"43260\",\"parentId\":\"2280\",\"text\":\"姚青\"},{\"id\":\"43261\",\"parentId\":\"2280\",\"text\":\"王国威\"},{\"id\":\"43262\",\"parentId\":\"2280\",\"text\":\"谭波\"},{\"id\":\"43263\",\"parentId\":\"2280\",\"text\":\"苏轩\"},{\"id\":\"43264\",\"parentId\":\"2280\",\"text\":\"陈卉\"},{\"id\":\"43265\",\"parentId\":\"2280\",\"text\":\"王安\"},{\"id\":\"43266\",\"parentId\":\"2280\",\"text\":\"李英武（账号已废除，勿选）\"},{\"id\":\"43267\",\"parentId\":\"2280\",\"text\":\"张剑（账号已废除，勿选）\"},{\"id\":\"43268\",\"parentId\":\"2280\",\"text\":\"单再攀（账号已废除，勿选）\"},{\"id\":\"43269\",\"parentId\":\"2280\",\"text\":\"陈友超\"},{\"id\":\"43270\",\"parentId\":\"2280\",\"text\":\"游张洋（账号已废除，勿选）\"},{\"id\":\"43271\",\"parentId\":\"2280\",\"text\":\"石磊\"},{\"id\":\"43272\",\"parentId\":\"2280\",\"text\":\"李虎（账号已废除，勿选）\"},{\"id\":\"43273\",\"parentId\":\"2280\",\"text\":\"柳杰\"},{\"id\":\"43274\",\"parentId\":\"2280\",\"text\":\"熊聪\"},{\"id\":\"43275\",\"parentId\":\"2280\",\"text\":\"陈晗\"},{\"id\":\"43276\",\"parentId\":\"2280\",\"text\":\"陈灿\"},{\"id\":\"43872\",\"parentId\":\"2280\",\"text\":\"李剑文\"},{\"id\":\"43898\",\"parentId\":\"2280\",\"text\":\"燕云帆\"},{\"id\":\"43899\",\"parentId\":\"2280\",\"text\":\"卢军\"},{\"id\":\"43900\",\"parentId\":\"2280\",\"text\":\"吴魁林\"},{\"id\":\"43901\",\"parentId\":\"2280\",\"text\":\"熊楚才\"},{\"id\":\"43902\",\"parentId\":\"2280\",\"text\":\"田源\"},{\"id\":\"43940\",\"parentId\":\"2280\",\"text\":\"姜姿\"},{\"id\":\"43941\",\"parentId\":\"2280\",\"text\":\"刘一峰（账号已废除，勿选）\"},{\"id\":\"43942\",\"parentId\":\"2280\",\"text\":\"周一帆\"},{\"id\":\"43943\",\"parentId\":\"2280\",\"text\":\"姚瑛\"},{\"id\":\"43944\",\"parentId\":\"2280\",\"text\":\"汪鑫\"},{\"id\":\"43945\",\"parentId\":\"2280\",\"text\":\"邓煜宇\"},{\"id\":\"43946\",\"parentId\":\"2280\",\"text\":\"彭昕怡\"},{\"id\":\"45003\",\"parentId\":\"2280\",\"text\":\"聂卫群\"},{\"id\":\"45004\",\"parentId\":\"2280\",\"text\":\"张竞宇\"},{\"id\":\"45005\",\"parentId\":\"2280\",\"text\":\"邓飞\"},{\"id\":\"45006\",\"parentId\":\"2280\",\"text\":\"杜曲\"},{\"id\":\"54029\",\"parentId\":\"2280\",\"text\":\"黄有智\"},{\"id\":\"44194\",\"parentId\":\"2280\",\"text\":\"屈娅\"},{\"id\":\"44195\",\"parentId\":\"2280\",\"text\":\"袁征\"},{\"id\":\"45002\",\"parentId\":\"2280\",\"text\":\"陈娴\"},{\"id\":\"54033\",\"parentId\":\"2280\",\"text\":\"唐隽\"},{\"id\":\"54050\",\"parentId\":\"2280\",\"text\":\"周威\"},{\"id\":\"54051\",\"parentId\":\"2280\",\"text\":\"任妮\"},{\"id\":\"43888\",\"parentId\":\"2280\",\"text\":\"长沙测试用户（账号已废除，勿选）\"},{\"id\":\"44175\",\"parentId\":\"2280\",\"text\":\"王慧禹\"},{\"id\":\"44176\",\"parentId\":\"2280\",\"text\":\"张皓诚\"},{\"id\":\"44184\",\"parentId\":\"2280\",\"text\":\"王龙刚\"},{\"id\":\"44191\",\"parentId\":\"2280\",\"text\":\"张伟龙\"},{\"id\":\"54025\",\"parentId\":\"2280\",\"text\":\"张伟毅\"},{\"id\":\"55286\",\"parentId\":\"2280\",\"text\":\"谢冲\"},{\"id\":\"55667\",\"parentId\":\"2280\",\"text\":\"谢彪\"},{\"id\":\"56826\",\"parentId\":\"2280\",\"text\":\"杨李\"},{\"id\":\"56827\",\"parentId\":\"2280\",\"text\":\"马俊\"},{\"id\":\"56828\",\"parentId\":\"2280\",\"text\":\"范群哲\"},{\"id\":\"56829\",\"parentId\":\"2280\",\"text\":\"李婧婧\"},{\"id\":\"56830\",\"parentId\":\"2280\",\"text\":\"周运龙\"},{\"id\":\"56833\",\"parentId\":\"2280\",\"text\":\"刘思平\"},{\"id\":\"56486\",\"parentId\":\"2280\",\"text\":\"左一凡\"},{\"id\":\"56766\",\"parentId\":\"2280\",\"text\":\"胡倩\"},{\"id\":\"55506\",\"parentId\":\"2280\",\"text\":\"熊天文（账号已废除，勿选）\"},{\"id\":\"55507\",\"parentId\":\"2280\",\"text\":\"程宇辉\"},{\"id\":\"55508\",\"parentId\":\"2280\",\"text\":\"文静姣\"},{\"id\":\"56306\",\"parentId\":\"2280\",\"text\":\"林威仪\"},{\"id\":\"56307\",\"parentId\":\"2280\",\"text\":\"秦世权\"},{\"id\":\"56308\",\"parentId\":\"2280\",\"text\":\"(设计院)历晓\"},{\"id\":\"56309\",\"parentId\":\"2280\",\"text\":\"(设计院)方悟杰\"},{\"id\":\"56310\",\"parentId\":\"2280\",\"text\":\"(设计院)苏灿\"},{\"id\":\"56311\",\"parentId\":\"2280\",\"text\":\"(设计院)龙腾\"},{\"id\":\"56312\",\"parentId\":\"2280\",\"text\":\"甘泉\"},{\"id\":\"56313\",\"parentId\":\"2280\",\"text\":\"邓宏烛\"},{\"id\":\"56314\",\"parentId\":\"2280\",\"text\":\"蒋佳\"},{\"id\":\"56315\",\"parentId\":\"2280\",\"text\":\"阳成羿\"},{\"id\":\"56316\",\"parentId\":\"2280\",\"text\":\"李雯\"},{\"id\":\"56317\",\"parentId\":\"2280\",\"text\":\"雷芳\"},{\"id\":\"56318\",\"parentId\":\"2280\",\"text\":\"赵勇\"},{\"id\":\"56319\",\"parentId\":\"2280\",\"text\":\"胡瑶\"},{\"id\":\"56320\",\"parentId\":\"2280\",\"text\":\"周绮\"},{\"id\":\"55007\",\"parentId\":\"2280\",\"text\":\"余贵强\"},{\"id\":\"55008\",\"parentId\":\"2280\",\"text\":\"方宁\"},{\"id\":\"55009\",\"parentId\":\"2280\",\"text\":\"游彤\"},{\"id\":\"55010\",\"parentId\":\"2280\",\"text\":\"李灿\"},{\"id\":\"55386\",\"parentId\":\"2280\",\"text\":\"朱荟璇（账号已废除，勿选）\"},{\"id\":\"55387\",\"parentId\":\"2280\",\"text\":\"彭勇\"},{\"id\":\"57986\",\"parentId\":\"2280\",\"text\":\"郭开武\"},{\"id\":\"58246\",\"parentId\":\"2280\",\"text\":\"蒋瑜\"},{\"id\":\"58247\",\"parentId\":\"2280\",\"text\":\"文接宗\"},{\"id\":\"58248\",\"parentId\":\"2280\",\"text\":\"戴建成\"},{\"id\":\"58249\",\"parentId\":\"2280\",\"text\":\"熊源利\"},{\"id\":\"58250\",\"parentId\":\"2280\",\"text\":\"易国中\"},{\"id\":\"58251\",\"parentId\":\"2280\",\"text\":\"王利平\"},{\"id\":\"58252\",\"parentId\":\"2280\",\"text\":\"贺孟奇\"},{\"id\":\"58253\",\"parentId\":\"2280\",\"text\":\"陈金国\"},{\"id\":\"58254\",\"parentId\":\"2280\",\"text\":\"钟炽成\"},{\"id\":\"58255\",\"parentId\":\"2280\",\"text\":\"阳宗仁\"},{\"id\":\"58256\",\"parentId\":\"2280\",\"text\":\"陈冲\"},{\"id\":\"58257\",\"parentId\":\"2280\",\"text\":\"李略\"},{\"id\":\"58258\",\"parentId\":\"2280\",\"text\":\"徐菲\"},{\"id\":\"58259\",\"parentId\":\"2280\",\"text\":\"向曼丽（账号已废除，勿选）\"},{\"id\":\"58260\",\"parentId\":\"2280\",\"text\":\"黄宁\"},{\"id\":\"58426\",\"parentId\":\"2280\",\"text\":\"刘欢\"},{\"id\":\"58427\",\"parentId\":\"2280\",\"text\":\"周才军\"},{\"id\":\"58428\",\"parentId\":\"2280\",\"text\":\"雷少兵\"},{\"id\":\"58429\",\"parentId\":\"2280\",\"text\":\"肖维\"},{\"id\":\"58430\",\"parentId\":\"2280\",\"text\":\"罗高为\"},{\"id\":\"58431\",\"parentId\":\"2280\",\"text\":\"周桃丰（账号已废除，勿选）\"},{\"id\":\"58432\",\"parentId\":\"2280\",\"text\":\"钟建明（账号已废除，勿选）\"},{\"id\":\"58433\",\"parentId\":\"2280\",\"text\":\"周冲\"},{\"id\":\"58434\",\"parentId\":\"2280\",\"text\":\"郭亮\"},{\"id\":\"58435\",\"parentId\":\"2280\",\"text\":\"朱嘹亮\"},{\"id\":\"58436\",\"parentId\":\"2280\",\"text\":\"蔡文桂\"},{\"id\":\"58437\",\"parentId\":\"2280\",\"text\":\"伍如成\"},{\"id\":\"58438\",\"parentId\":\"2280\",\"text\":\"唐瑞林\"},{\"id\":\"58439\",\"parentId\":\"2280\",\"text\":\"唐招继\"},{\"id\":\"58440\",\"parentId\":\"2280\",\"text\":\"潘章（账号已废除，勿选）\"},{\"id\":\"58441\",\"parentId\":\"2280\",\"text\":\"彭晶（账号已废除，勿选）\"},{\"id\":\"58442\",\"parentId\":\"2280\",\"text\":\"张蓓蕾（账号已废除，勿选）\"},{\"id\":\"58443\",\"parentId\":\"2280\",\"text\":\"刘志伟\"},{\"id\":\"58586\",\"parentId\":\"2280\",\"text\":\"文磊\"},{\"id\":\"58587\",\"parentId\":\"2280\",\"text\":\"符俊安\"},{\"id\":\"58646\",\"parentId\":\"2280\",\"text\":\"严曙辉\"},{\"id\":\"58647\",\"parentId\":\"2280\",\"text\":\"龚旦旭\"},{\"id\":\"58648\",\"parentId\":\"2280\",\"text\":\"陈果\"},{\"id\":\"58726\",\"parentId\":\"2280\",\"text\":\"曾时军\"},{\"id\":\"59126\",\"parentId\":\"2280\",\"text\":\"钟光明\"},{\"id\":\"59127\",\"parentId\":\"2280\",\"text\":\"邓冬祥\"},{\"id\":\"59366\",\"parentId\":\"2280\",\"text\":\"王家林\"},{\"id\":\"59369\",\"parentId\":\"2280\",\"text\":\"张丽琳\"},{\"id\":\"59370\",\"parentId\":\"2280\",\"text\":\"乔琴\"},{\"id\":\"59371\",\"parentId\":\"2280\",\"text\":\"杨昆龙\"},{\"id\":\"59372\",\"parentId\":\"2280\",\"text\":\"李星\"},{\"id\":\"59373\",\"parentId\":\"2280\",\"text\":\"孙燕宇\"},{\"id\":\"59374\",\"parentId\":\"2280\",\"text\":\"龚哲\"},{\"id\":\"59375\",\"parentId\":\"2280\",\"text\":\"李慧\"},{\"id\":\"59376\",\"parentId\":\"2280\",\"text\":\"李林颖\"},{\"id\":\"59377\",\"parentId\":\"2280\",\"text\":\"陈乔高杨\"},{\"id\":\"59431\",\"parentId\":\"2280\",\"text\":\"何海斌\"},{\"id\":\"59433\",\"parentId\":\"2280\",\"text\":\"叶旭光\"},{\"id\":\"59817\",\"parentId\":\"2280\",\"text\":\"雍佩林\"},{\"id\":\"59818\",\"parentId\":\"2280\",\"text\":\"杨勇兵\"},{\"id\":\"59819\",\"parentId\":\"2280\",\"text\":\"熊赛兰\"},{\"id\":\"59820\",\"parentId\":\"2280\",\"text\":\"黄湘瑶\"},{\"id\":\"59821\",\"parentId\":\"2280\",\"text\":\"刘雁\"},{\"id\":\"59833\",\"parentId\":\"2280\",\"text\":\"徐灿\"},{\"id\":\"59834\",\"parentId\":\"2280\",\"text\":\"刘志斌\"},{\"id\":\"59866\",\"parentId\":\"2280\",\"text\":\"袁新明\"},{\"id\":\"59867\",\"parentId\":\"2280\",\"text\":\"陈媛\"},{\"id\":\"59938\",\"parentId\":\"2280\",\"text\":\"毛彪\"},{\"id\":\"59939\",\"parentId\":\"2280\",\"text\":\"肖伯文\"},{\"id\":\"59946\",\"parentId\":\"2280\",\"text\":\"卞惠\"},{\"id\":\"59947\",\"parentId\":\"2280\",\"text\":\"程知阳\"},{\"id\":\"59948\",\"parentId\":\"2280\",\"text\":\"陈姗姗\"},{\"id\":\"59949\",\"parentId\":\"2280\",\"text\":\"崔厅\"},{\"id\":\"59950\",\"parentId\":\"2280\",\"text\":\"邓淑芳\"},{\"id\":\"59951\",\"parentId\":\"2280\",\"text\":\"段婧\"},{\"id\":\"59952\",\"parentId\":\"2280\",\"text\":\"龚垚\"},{\"id\":\"59953\",\"parentId\":\"2280\",\"text\":\"韩璐\"},{\"id\":\"59954\",\"parentId\":\"2280\",\"text\":\"贺佳\"},{\"id\":\"59955\",\"parentId\":\"2280\",\"text\":\"皇甫百慧\"},{\"id\":\"59956\",\"parentId\":\"2280\",\"text\":\"姜园\"},{\"id\":\"59957\",\"parentId\":\"2280\",\"text\":\"刘晴\"},{\"id\":\"59958\",\"parentId\":\"2280\",\"text\":\"李欣\"},{\"id\":\"59959\",\"parentId\":\"2280\",\"text\":\"吕佳琪\"},{\"id\":\"59960\",\"parentId\":\"2280\",\"text\":\"彭昕蓓\"},{\"id\":\"59961\",\"parentId\":\"2280\",\"text\":\"曲丽丽\"},{\"id\":\"59962\",\"parentId\":\"2280\",\"text\":\"王倩玲\"},{\"id\":\"59963\",\"parentId\":\"2280\",\"text\":\"万佳妮\"},{\"id\":\"59964\",\"parentId\":\"2280\",\"text\":\"肖婷\"},{\"id\":\"59965\",\"parentId\":\"2280\",\"text\":\"熊智彬\"},{\"id\":\"59966\",\"parentId\":\"2280\",\"text\":\"许雯静\"},{\"id\":\"59967\",\"parentId\":\"2280\",\"text\":\"杨满珍\"},{\"id\":\"59968\",\"parentId\":\"2280\",\"text\":\"叶柳青\"},{\"id\":\"59969\",\"parentId\":\"2280\",\"text\":\"张静静\"},{\"id\":\"59970\",\"parentId\":\"2280\",\"text\":\"张小妹\"},{\"id\":\"59971\",\"parentId\":\"2280\",\"text\":\"赵奕\"},{\"id\":\"59972\",\"parentId\":\"2280\",\"text\":\"周琳\"},{\"id\":\"59973\",\"parentId\":\"2280\",\"text\":\"周艳华\"},{\"id\":\"59586\",\"parentId\":\"2280\",\"text\":\"王艳红\"},{\"id\":\"59378\",\"parentId\":\"2280\",\"text\":\"刘佩\"},{\"id\":\"59432\",\"parentId\":\"2280\",\"text\":\"刘奇\"},{\"id\":\"59687\",\"parentId\":\"2280\",\"text\":\"谭亮\"},{\"id\":\"59367\",\"parentId\":\"2280\",\"text\":\"朱艳红\"},{\"id\":\"59368\",\"parentId\":\"2280\",\"text\":\"王猛\"},{\"id\":\"59498\",\"parentId\":\"2280\",\"text\":\"刘丹\"},{\"id\":\"59499\",\"parentId\":\"2280\",\"text\":\"颜群\"},{\"id\":\"59500\",\"parentId\":\"2280\",\"text\":\"丁毅\"},{\"id\":\"59501\",\"parentId\":\"2280\",\"text\":\"王曾\"},{\"id\":\"59628\",\"parentId\":\"2280\",\"text\":\"吴婷\"},{\"id\":\"59666\",\"parentId\":\"2280\",\"text\":\"刘秀平\"},{\"id\":\"59686\",\"parentId\":\"2280\",\"text\":\"蒋博\"},{\"id\":\"59786\",\"parentId\":\"2280\",\"text\":\"李宇舟\"},{\"id\":\"59787\",\"parentId\":\"2280\",\"text\":\"喻小茗\"},{\"id\":\"59788\",\"parentId\":\"2280\",\"text\":\"崔慧琳\"},{\"id\":\"60208\",\"parentId\":\"2280\",\"text\":\"李勤\"},{\"id\":\"60209\",\"parentId\":\"2280\",\"text\":\"郑柱红\"},{\"id\":\"60210\",\"parentId\":\"2280\",\"text\":\"傅天武\"},{\"id\":\"60211\",\"parentId\":\"2280\",\"text\":\"李舟\"},{\"id\":\"60212\",\"parentId\":\"2280\",\"text\":\"谢俏\"},{\"id\":\"60213\",\"parentId\":\"2280\",\"text\":\"张晶\"},{\"id\":\"60214\",\"parentId\":\"2280\",\"text\":\"张麒\"},{\"id\":\"60215\",\"parentId\":\"2280\",\"text\":\"李宁波\"},{\"id\":\"60216\",\"parentId\":\"2280\",\"text\":\"胡明\"},{\"id\":\"60217\",\"parentId\":\"2280\",\"text\":\"王薪权\"},{\"id\":\"60218\",\"parentId\":\"2280\",\"text\":\"李海森\"},{\"id\":\"60219\",\"parentId\":\"2280\",\"text\":\"周国华\"},{\"id\":\"60220\",\"parentId\":\"2280\",\"text\":\"朱小龙\"},{\"id\":\"60221\",\"parentId\":\"2280\",\"text\":\"邓超然\"},{\"id\":\"60222\",\"parentId\":\"2280\",\"text\":\"邱坤\"},{\"id\":\"60223\",\"parentId\":\"2280\",\"text\":\"邓小文\"},{\"id\":\"60224\",\"parentId\":\"2280\",\"text\":\"欧阳杰\"},{\"id\":\"60225\",\"parentId\":\"2280\",\"text\":\"王帅\"},{\"id\":\"60226\",\"parentId\":\"2280\",\"text\":\"罗勇\"},{\"id\":\"60227\",\"parentId\":\"2280\",\"text\":\"张如\"},{\"id\":\"60228\",\"parentId\":\"2280\",\"text\":\"沈明霞\"},{\"id\":\"60229\",\"parentId\":\"2280\",\"text\":\"孟永强\"},{\"id\":\"60230\",\"parentId\":\"2280\",\"text\":\"戴仲乙\"},{\"id\":\"60231\",\"parentId\":\"2280\",\"text\":\"刘罗生\"},{\"id\":\"60232\",\"parentId\":\"2280\",\"text\":\"王伟\"},{\"id\":\"60233\",\"parentId\":\"2280\",\"text\":\"冯奕\"},{\"id\":\"60234\",\"parentId\":\"2280\",\"text\":\"黎冬\"},{\"id\":\"60235\",\"parentId\":\"2280\",\"text\":\"周英\"},{\"id\":\"60236\",\"parentId\":\"2280\",\"text\":\"王志群\"},{\"id\":\"60237\",\"parentId\":\"2280\",\"text\":\"王方贵\"},{\"id\":\"60238\",\"parentId\":\"2280\",\"text\":\"张文静\"},{\"id\":\"60239\",\"parentId\":\"2280\",\"text\":\"柳腾达\"},{\"id\":\"60240\",\"parentId\":\"2280\",\"text\":\"阳昕\"},{\"id\":\"60241\",\"parentId\":\"2280\",\"text\":\"陈嘉锋\"},{\"id\":\"60242\",\"parentId\":\"2280\",\"text\":\"周龙\"},{\"id\":\"60243\",\"parentId\":\"2280\",\"text\":\"谷江峰\"},{\"id\":\"60244\",\"parentId\":\"2280\",\"text\":\"成果\"},{\"id\":\"60245\",\"parentId\":\"2280\",\"text\":\"陆阳\"},{\"id\":\"60246\",\"parentId\":\"2280\",\"text\":\"卢丰文\"},{\"id\":\"60247\",\"parentId\":\"2280\",\"text\":\"周智武\"},{\"id\":\"60248\",\"parentId\":\"2280\",\"text\":\"胡志斌\"},{\"id\":\"60527\",\"parentId\":\"2280\",\"text\":\"刘文\"}]}";
		String outStr = AnalysisUtil.getAssignDealer(inStr);
		System.out.println(outStr);
	}
}
