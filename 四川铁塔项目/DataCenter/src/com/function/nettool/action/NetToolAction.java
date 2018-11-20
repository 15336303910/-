package com.function.nettool.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.function.nettool.model.NetToolDetail;
import com.function.nettool.service.NetToolDetailService;
@Controller("com.function.nettool.action.NetToolAction")
@RequestMapping(value="/netToolAction")
public class NetToolAction{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NetToolDetailService netToolDetailService;
	
	@SuppressWarnings("unchecked")
	public Integer getLoginUserId(HttpServletRequest request){
		Object loginObj = request.getSession().getAttribute("LoginUserInfo");
		if(loginObj!=null){
			Map<String,Object> userObject = (Map<String,Object>)loginObj;
			return Integer.parseInt(userObject.get("ID").toString());
		}else{
			return null;
		}
	}
	
	/*
	 * 	保存
	 * 
	 * */
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(@RequestParam String params,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'工具配置成功.'}");
		NetToolDetail toolDetail = null;
		try{
			final JSONObject jsonObject = JSONObject.fromObject(params);
			if(jsonObject.get("ID")!=null && !"-1".equals(jsonObject.get("ID").toString())){
				toolDetail = netToolDetailService.selectOne(Integer.parseInt(jsonObject.get("ID").toString()));
			}else{
				toolDetail = new NetToolDetail();
			}
			toolDetail.setAPP_ICON(jsonObject.get("APP_ICON")==null?"":jsonObject.get("APP_ICON").toString());
			toolDetail.setAPP_NAME(jsonObject.get("APP_NAME")==null?"":jsonObject.get("APP_NAME").toString());
			toolDetail.setAPP_URL(jsonObject.get("APP_URL")==null?"":jsonObject.get("APP_URL").toString());
			toolDetail.setPUBLISH_USER_CODE(getLoginUserId(request));
			toolDetail.setPUBLISH_DATE(new Date());
			toolDetail.setAPP_VERSION(jsonObject.get("APP_VERSION")==null?"":jsonObject.get("APP_VERSION").toString());
			toolDetail.setAPP_DESC(jsonObject.get("APP_DESC")==null?"":jsonObject.get("APP_DESC").toString());
			toolDetail.setAPP_LANGUAGE(jsonObject.get("APP_LANGUAGE")==null?"":jsonObject.get("APP_LANGUAGE").toString());
			toolDetail.setSEARCH_KEY(jsonObject.get("SEARCH_KEY")==null?"":jsonObject.get("SEARCH_KEY").toString());
			toolDetail.setOTHER_DESC(jsonObject.get("OTHER_DESC")==null?"":jsonObject.get("OTHER_DESC").toString());
			toolDetail.setAPP_DESC_IMAGE(jsonObject.get("APP_DESC_IMAGE")==null?"":jsonObject.get("APP_DESC_IMAGE").toString());
			toolDetail.setIS_EXPORT(jsonObject.get("IS_EXPORT")==null?"":jsonObject.get("IS_EXPORT").toString());
			toolDetail.setIS_DOWNLOAD(jsonObject.get("IS_DOWNLOAD")==null?"":jsonObject.get("IS_DOWNLOAD").toString());
			toolDetail.setTOOL_NAME(jsonObject.get("TOOL_NAME")==null?"":jsonObject.get("TOOL_NAME").toString());
			if(jsonObject.get("ID")!=null && !"-1".equals(jsonObject.get("ID").toString())){
				netToolDetailService.updateModel(toolDetail);
			}else{
				Integer newCode = netToolDetailService.insertModel(toolDetail);
				toolDetail.setID(newCode);
			}
			resultObject.put("newCode",toolDetail.getID());
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false,message:'系统异常.'}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	/*
	 * 	上传应用对应图片
	 * 
	 * */
	@RequestMapping(value="/uploadFile.ilf",method=RequestMethod.POST)
	public void uploadFile(@RequestParam("TemplateUpload")MultipartFile fileToUpload,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			if(!fileToUpload.isEmpty()){
				String strDirPath = request.getSession().getServletContext().getRealPath("/")+"uploads/";
				String newFileName = new Date().getTime()+".png";
				File file = new File(strDirPath+newFileName);
				if(file.exists()){
					file.delete();
				}
				fileToUpload.transferTo(file);
				resultObject.put("IMAGE_NAME",newFileName);
			}else{
				resultObject.put("IMAGE_NAME","");
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	修改
	 * 
	 * */
	@RequestMapping("/updateAudit.ilf")
	public void updateAudit(@RequestParam String detailId,@RequestParam String columnName,@RequestParam String newValue,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'应用信息编辑成功.'}");
		try{
			jdbcTemplate.execute("UPDATE NET_TOOL_DETAIL SET "+columnName+" = '"+newValue+"' WHERE ID = "+detailId);
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false,message:'系统异常.'}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	/*
	 * 	修改
	 * 
	 * */
	@RequestMapping("/deleteAudit.ilf")
	public void deleteAudit(@RequestParam String detailId,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true,message:'应用信息编辑成功.'}");
		try{
			jdbcTemplate.execute("DELETE FROM NET_TOOL_DETAIL WHERE ID = "+detailId);
		}catch(Exception e){
			e.printStackTrace();
			resultObject = JSONObject.fromObject("{success:false,message:'系统异常.'}");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}

	/*
	 * 	获取一个详情
	 * 
	 * */
	@RequestMapping("/findOne.ilf")
	public void findOne(@RequestParam String netToolDetailId,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			/*
			 * 	应用详情
			 * 
			 * */
			Map<String,Object> detailObject = jdbcTemplate.queryForMap("SELECT A.*,B.EMPLOYEE_NAME FROM NET_TOOL_DETAIL A,S_SYSTEM_USER B WHERE A.ID = "+netToolDetailId+" AND A.PUBLISH_USER_CODE = B.ID");
			/*
			 * 	其他相关应用
			 * 
			 * */
			String sql = "";
			sql+="SELECT M.ID,M.APP_ICON,M.APP_NAME FROM (";
			sql+="	SELECT A.*,B.EMPLOYEE_NAME,ROWNUM AS RN FROM NET_TOOL_DETAIL A,S_SYSTEM_USER B WHERE A.PUBLISH_USER_CODE = "+detailObject.get("PUBLISH_USER_CODE").toString()+" AND A.ID != "+netToolDetailId+" AND A.PUBLISH_USER_CODE = B.ID ORDER BY A.ID ASC";
			sql+=") M WHERE M.RN <= 3";
			detailObject.put("BELONG_APPS",jdbcTemplate.queryForList(sql));
			resultObject.put("detailObject",detailObject);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}		
	}
	
	/*
	 * 	获取APP总数
	 * 
	 * */
	@RequestMapping("/findCount.ilf")
	public void findCount(@RequestParam String conditions,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			HashMap<String,Object> conditonMap = new HashMap<String,Object>();
			JSONArray condition = JSONArray.fromObject(conditions);
			if(conditions!=null && condition.size()!=0){
				for(int i=0;i<condition.size();i++){
					JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
					if(jsonObject.get("value")!=null && !"".equals(jsonObject.getString("value"))){
						conditonMap.put(jsonObject.getString("name"),jsonObject.getString("value"));
					}
				}
			}
			Integer totalCount = netToolDetailService.getCount(conditonMap);
			resultObject.put("totalCount",totalCount);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}		
	}
	
	/*
	 * 	查询分页
	 * 
	 * */
	@RequestMapping("/findItems.ilf")
	public void findItems(@RequestParam String conditions,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			HashMap<String,Object> conditonMap = new HashMap<String,Object>();
			JSONArray condition = JSONArray.fromObject(conditions);
			if(conditions!=null && condition.size()!=0){
				for(int i=0;i<condition.size();i++){
					JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
					if(jsonObject.get("value")!=null && !"".equals(jsonObject.getString("value"))){
						conditonMap.put(jsonObject.getString("name"),jsonObject.getString("value"));
					}
				}
			}
			List<NetToolDetail> toolDetails = netToolDetailService.getDbPage(conditonMap);
			resultObject.put("items",JSONArray.fromObject(toolDetails));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	应用下载.
	 * 
	 * */
	@RequestMapping("/downloadApp.ilf")
	public void downloadApp(@RequestParam String fileName,HttpServletRequest request,HttpServletResponse response)throws Exception{
		String strDirPath = request.getSession().getServletContext().getRealPath("/")+"apps/"+fileName;
		File templateFile = new File(strDirPath);
		if(templateFile.exists()){
			FileInputStream fileInputStream = new FileInputStream(templateFile);
			long filesize = templateFile.length();
			response.setContentType("text/html;charset=gb2312");
			response.addHeader("content-type","application/x-msdownload;");
			response.setHeader("Content-Disposition","attachment;filename=\""+new String((templateFile.getName()).getBytes(),"ISO8859-1"));
			response.addHeader("content-length",Long.toString(filesize));
			OutputStream output = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int i = 0;    
			while((i=fileInputStream.read(bytes))>0){
				output.write(bytes,0,i);    
			}    
			output.flush();
			output.close();
			fileInputStream.close();
		}
	}
}
