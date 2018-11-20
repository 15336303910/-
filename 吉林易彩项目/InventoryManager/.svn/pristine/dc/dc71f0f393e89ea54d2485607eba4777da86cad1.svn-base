package interfaces.pdainterface.hcgd;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.gd.gdManage.pojo.AppRequestParam;
import manage.gd.gdManage.service.impl.IhcgdManageService;
import Decoder.BASE64Decoder;
import base.web.InterfaceAction;
public class HcgdAppManageAction extends InterfaceAction{

	private static final long serialVersionUID = -4147057159222455762L;
	
	private IhcgdManageService hcgdManageService;
	
	/**
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void findJobs()throws Exception{
		try{	
			AppRequestParam  paramObject = (AppRequestParam)getRequestObject(AppRequestParam.class);	
			String taskState = paramObject.getTaskState();
			if(taskState!=null && !"".equals(taskState)){
				JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
				String sql = "select * from gd_main where 1 = 1";
				if("UnAccepted".equals(taskState)){
					String userAccount = super.getLonginer();
					List<Map<String,Object>> regionList = jdbcTemplate.queryForList("select b.domainName from sys_user a,sys_domain b where a.username = '"+userAccount+"' and a.areano = b.domainCode");
					if(regionList.size()>0){
						String domainName = regionList.get(0).get("domainName").toString();
						sql+=" and task_state = '未接单' and receive_region_name like '%"+domainName+"%'";
					}
				}else{
					String userId = "";
					String userAccount = super.getLonginer();
					List<Map<String,Object>> userList = jdbcTemplate.queryForList("select a.userId from sys_user a,sys_domain b where a.username = '"+userAccount+"' and a.areano = b.domainCode");
					if(userList.size()>0){
						userId = userList.get(0).get("userId").toString();
					}
					if("HaveAccepted".equals(taskState)){
						sql+=" and task_state = '已接单' and current_action_usercode = '"+userId+"'";
					}else if("HaveFinished".equals(taskState)){
						sql+=" and task_state = '已完成' and current_action_usercode = '"+userId+"'";
					}
				}
				sql+=" order by create_datetime desc";
				List<Map<String,Object>> tasks = jdbcTemplate.queryForList(sql);
				if(tasks.size()>0){
					for(int w=0;w<tasks.size();w++){
						Map<String,Object> taskObject = tasks.get(w);
						String taskCode = taskObject.get("id").toString();
						List<Map<String,Object>> resOfJob = jdbcTemplate.queryForList("select * from gd_main_resource where parent_code = 0 and belong_gd = "+taskCode);
						tasks.get(w).put("resource",resOfJob);					
					}
				}				
				/*0,正常；1,传入参数有误；2,查询数据不对；3,报错*/
				String thisJson = JSONArray.fromObject(tasks).toString(); 
				sendResponse(0,thisJson);
			}else{
				sendResponse(1,"必要参数缺失");
			}
		}catch(Exception e){
			e.printStackTrace();
			sendResponse(3,"服务端异常");
		}
	}
	
	/*抢单*/
	public void acceptAssignedJob()throws Exception{
		try{
			AppRequestParam  paramObject = (AppRequestParam)getRequestObject(AppRequestParam.class);
			String taskCode = paramObject.getTaskCode();
			if(taskCode==null || "".equals(taskCode)){
				sendResponse(1,"必要参数缺失");
			}else{
				String userAccount = super.getLonginer();
				JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
				List<Map<String,Object>> userList = jdbcTemplate.queryForList("select a.userId,a.realname from sys_user a,sys_domain b where a.username = '"+userAccount+"' and a.areano = b.domainCode");
				if(userList.size()>0){
					String userCode = userList.get(0).get("userId").toString();
					String userName = userList.get(0).get("realname").toString();
					jdbcTemplate.execute("update gd_main set task_state = '已接单',current_action_usercode = '"+userCode+"',current_action_username = '"+userName+"' where id = "+taskCode);
					sendResponse(0,"抢单成功");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sendResponse(3,"服务端异常");
		}
	}
	
	/**
	 * 获取资源详情
	 * @throws Exception
	 */
	public void getResourceDetail()throws Exception{
		try{
			AppRequestParam  paramObject = (AppRequestParam)getRequestObject(AppRequestParam.class);
			String gdCode = paramObject.getTaskCode();
			String resourceCode = paramObject.getResourceCode();			
			if(resourceCode==null || gdCode==null || "".equals(resourceCode) || "".equals(gdCode)){
				sendResponse(1,"必要参数缺失");
			}else{
				JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
				/*组装数据*/
				Map<String,Object> detailObject = findUniqueObject("select a.*,b.resource_en_name,b.rms_table_name,b.code_column,b.value_column from gd_main_resource a,config_resource_table b where a.belong_gd = "+gdCode+" and a.id = "+resourceCode+" and a.res_dimension = b.resource_cn_name");
				String resourceEnName = detailObject.get("resource_en_name").toString();
				List<Map<String,Object>> checkItems = jdbcTemplate.queryForList("select * from config_checks where belong_dimension = '"+resourceEnName+"'");
				String sql = "select "+detailObject.get("code_column").toString()+" as '资源编号',"+detailObject.get("value_column").toString()+" as '资源名称'";
				if(checkItems.size()>0){
					for(int j=0;j<checkItems.size();j++){
						Map<String,Object> columnObject = checkItems.get(j);
						if(columnObject.get("is_consider")!=null && "Y".equals(columnObject.get("is_consider").toString())){
							sql+=","+columnObject.get("consider_column").toString()+" as '"+columnObject.get("item_desc").toString()+"'";
						}				
					}
				}
				sql+=" from "+detailObject.get("rms_table_name").toString()+" where "+detailObject.get("code_column").toString()+" = '"+detailObject.get("resource_code").toString()+"'";
				Map<String,Object> thisObject = findUniqueObject(sql);
				if(thisObject==null){
					sendResponse(3,"服务端异常");
				}else{
					JSONObject result = new JSONObject();
					/*获取统计数据*/
					List<Map<String,Object>> countData = jdbcTemplate.queryForList("select res_dimension as '资源类型',count(1) as '数量' from gd_main_resource where belong_gd = "+gdCode+" and parent_code = "+resourceCode+" group by res_dimension");
					result.put("CountItems",countData);
					/*获取核查内容*/
					List<Map<String,Object>> checks = jdbcTemplate.queryForList("select * from gd_main_check where belong_resource = "+resourceCode);
					if(checks.size()>0){
						Set<String> keys = thisObject.keySet();					
						for(int g=0;g<checks.size();g++){
							Map<String,Object> checkItem = checks.get(g);
							String checkDesc = checkItem.get("check_item").toString();
							Iterator<String> car = keys.iterator();
							while(car.hasNext()){
								String columnName = car.next();
								if(checkDesc.equals(columnName)){
									String columnValue = thisObject.get(columnName)+"";
									checks.get(g).put("column_value",columnValue);	
									thisObject.remove(columnName);					
									break;
								}						
							}				
						}
						Iterator<String> cars = thisObject.keySet().iterator();
						while(cars.hasNext()){
							String columnName = cars.next();
							String columnValue = thisObject.get(columnName).toString();
							Map<String,Object> attachObject = new HashMap<String,Object>();
							attachObject.put("is_need_check","N");
							attachObject.put("check_item",columnName);
							attachObject.put("column_value",columnValue);
							attachObject.put("edit_type","常规");
							checks.add(attachObject);				
						}
					}			
					result.put("Checks",checks);
					sendResponse(0,result);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			sendResponse(3,"服务端异常");
		}
	}
	
	/*获取某项资源在本工单范围内的下属资源*/
	public void findSubResources()throws Exception{
		AppRequestParam  paramObject = (AppRequestParam)getRequestObject(AppRequestParam.class);
		String resourceType = paramObject.getResourceType();
		String parentCode = paramObject.getParentCode();
		if(resourceType!=null && !"".equals(resourceType) && parentCode!=null && !"".equals(parentCode)){
			try{
				JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
				List<Map<String,Object>> resOfParentRes = jdbcTemplate.queryForList("select * from gd_main_resource where res_dimension = '"+resourceType+"' and parent_code = "+parentCode);
				sendResponse(0,JSONArray.fromObject(resOfParentRes).toString());
			}catch(Exception e){
				e.printStackTrace();
				sendResponse(3,"服务端异常");
			}
		}else{
			sendResponse(1,"必要参数缺失");
		}
	}
	
	/*上传照片*/
	public void uploadPhotoImage()throws Exception{
		AppRequestParam paramObject = (AppRequestParam)getRequestObject(AppRequestParam.class);
		String photoString = paramObject.getPhotoString();
		if(photoString!=null && !"".equals(photoString)){
			byte[] b = new BASE64Decoder().decodeBuffer(photoString);
			for(int i=0;i<b.length;++i){
	            if(b[i]<0){
	                b[i]+=256;
	            }
	        }
			ServletContext servletContext = this.getRequest().getSession().getServletContext();
	        String filesFolder = servletContext.getRealPath("/")+"/uploadImages/";
	        File fileFolder = new File(filesFolder);
	        if(!fileFolder.exists()){
	        	fileFolder.mkdirs();
	        }
	        String fileName = new Date().getTime()+".jpg";
	        String newFile = filesFolder+fileName;
	        OutputStream out = new FileOutputStream(newFile);
	        out.write(b);
	        out.flush();
	        out.close();
	        sendResponse(0,fileName);
		}else{
			sendResponse(1,"必要参数缺失");
		}	
	}
	
	/*提交核查内容*/
	public void flushChecksOfJob()throws Exception{
		try{
			String params = super.getJsonRequest();
			if(params!=null && !"".equals(params)){
				JSONObject jsonObject = JSONObject.fromObject(params);
				JSONArray items = JSONArray.fromObject(jsonObject.get("Checks").toString());
				System.out.println(jsonObject.toString());
				JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
				if(items.size()>0){
					for(int i=0;i<items.size();i++){
						JSONObject thisItem = items.getJSONObject(i);
						String updateSql = "update gd_main_check set is_checked = 'Y',check_datetime = now()";
						/*数据是否正常*/
						if(thisItem.get("is_ok")!=null && !"".equals(thisItem.getString("is_ok"))){
							updateSql+=",is_ok = '"+thisItem.getString("is_ok")+"'";
							/*修正数据*/
							if("N".equals(thisItem.getString("is_ok")) && thisItem.get("new_value")!=null && !"".equals(thisItem.getString("new_value"))){
								updateSql+=",new_value = '"+thisItem.getString("new_value")+"'";
							}else{
								updateSql+=",new_value = ''";
							}
						}			
						/*更新备注*/
						if(thisItem.get("contents")!=null && !"".equals(thisItem.getString("contents")) && !"{}".equals(thisItem.getString("contents"))){
							updateSql+=",contents = '"+thisItem.getString("contents")+"'";
						}else{
							updateSql+=",contents = ''";
						}
						updateSql+=" where id = "+thisItem.getString("id");
						jdbcTemplate.execute(updateSql);
						
						//将工单资源核查标记
						String resMainSql = "update gd_main_resource set is_checked ='Y'"
								+ " where id ='"+thisItem.getString("belong_resource")+"'";
						jdbcTemplate.execute(resMainSql);
					}
				}
				sendResponse(0,"核查信息提交成功");
			}else{
				sendResponse(1,"必要参数缺失");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void printString(String string, String contentType) throws Exception {
		this.getResponse().setHeader("Cache-Control","no-cache");
		this.getResponse().setContentType(contentType);
		this.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter pw = this.getResponse().getWriter();
		pw.write(string);
		pw.close();
	}
	
	public Map<String,Object> findUniqueObject(String sql){
		JdbcTemplate jdbcTemplate = hcgdManageService.getTemplate();
		List<Map<String,Object>> reses = jdbcTemplate.queryForList(sql);
		if(reses.size()>0){
			return reses.get(0);
		}else{
			return null;
		}
	}
	
	public IhcgdManageService getHcgdManageService() {
		return hcgdManageService;
	}

	public void setHcgdManageService(IhcgdManageService hcgdManageService) {
		this.hcgdManageService = hcgdManageService;
	}
}