package com.function.data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.function.rules.util.StyleUtil;
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.data.DataDesignAction")
@RequestMapping(value="/dataDesignAction")
public class DataDesignAction{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	查询列表（点击模型获取其包含的字段，用于定义订阅条件）
	 * 
	 * */
	@RequestMapping("/findItems.ilf")
	public void findItems(@RequestParam String tableparam,@RequestParam String conditions,HttpServletRequest request,HttpServletResponse response)throws Exception{
		Long sEcho = 0L;
		Integer displayStart = 0;
		Integer iDisplayLength = 0;
		JSONArray jsons = JSONArray.fromObject(tableparam);
		HashMap<String,Object> conditonMap = new HashMap<String,Object>();
		if(jsons!=null && jsons.size()!=0){
			for(int i=0;i<jsons.size();i++){
				JSONObject json = JSONObject.fromObject(jsons.get(i));
				String key = json.getString("name");
				if(key.equals("sEcho")){
					sEcho = Long.parseLong(json.getString("value"));
				}else if(key.equals("iDisplayStart")){
					displayStart = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayStart",displayStart);
				}else if(key.equals("iDisplayLength")){
					iDisplayLength = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayLength",iDisplayLength);
				}
			}
		}
		JSONArray condition = JSONArray.fromObject(conditions);
		if(conditions!=null && condition.size()!=0){
			for(int i=0;i<condition.size();i++){
				JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
				if(jsonObject.get("value")!=null && !"".equals(jsonObject.getString("value"))){
					conditonMap.put(jsonObject.getString("name"),jsonObject.getString("value"));
				}
			}
		}
		String loginUserAccount = loginUserUtil.getUserAccount(request);
		String sql = "";
		sql+="SELECT A.ID,A.COLUMN_ALIAS,A.DATA_TYPE,A.COLUMN_NAME,A.IS_PRIMARY_KEY,B.IS_DESIGN,B.FILTER ";
		sql+="FROM BASIC_DB_COLUMN A LEFT JOIN DATA_DESIGN B ON A.ID = B.COLUMN_ID AND B.USER_ACCOUNT = '"+loginUserAccount+"' ";
		if(conditonMap.containsKey("BELONG_TEMPLATE") && !"-".equals(conditonMap.get("BELONG_TEMPLATE").toString()) && !"".equals(conditonMap.get("BELONG_TEMPLATE").toString())){
			sql+=" AND B.BELONG_TEMPLATE = '"+conditonMap.get("BELONG_TEMPLATE").toString()+"' ";
		}
		sql+="WHERE A.BELONG_TABLE = "+conditonMap.get("BIND_TABLE").toString()+" ORDER BY ID ASC ";
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM ("+sql+")");
		List<Map<String,Object>> datas = jdbcTemplate.queryForList(sql);
		/*查看是否已定制*/
		for(int i=0;i<datas.size();i++){
			Map<String,Object> columnMap = datas.get(i);
			if(columnMap.get("IS_DESIGN")==null || "N".equals(columnMap.get("IS_DESIGN").toString())){
				datas.get(i).put("IS_DESIGN",false);
				datas.get(i).put("FILTER","");
			}else{
				datas.get(i).put("IS_DESIGN",true);
				
			}
		}
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(datas);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	保存/修改订阅条件.
	 * 
	 * */
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(
		@RequestParam String modelCode,
		@RequestParam String templateName,
		@RequestParam String params,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			templateName = java.net.URLDecoder.decode(templateName,"UTF-8");
			/*
			 * 	根据'UserAccount','ModelId','TemplateName'
			 * 
			 * */
			jdbcTemplate.execute("DELETE FROM DATA_DESIGN WHERE USER_ACCOUNT = '"+loginUserUtil.getUserAccount(request)+"' AND BELONG_TEMPLATE = '"+templateName+"' AND MODEL_ID = "+modelCode);
			/*
			 * 	保存入库.
			 * 
			 * */
			final JSONArray columnsConfig = JSONArray.fromObject(java.net.URLDecoder.decode(params,"UTF-8"));
			String sql = "";
			sql+="insert all ";
			Integer nextOrder = jdbcTemplate.queryForInt("SELECT MAX(ID) FROM DATA_DESIGN")+1;
			for(int i=0;i<columnsConfig.size();i++){
				JSONObject jsonObject = columnsConfig.getJSONObject(i);
				sql+="into DATA_DESIGN(ID,USER_ACCOUNT,MODEL_ID,COLUMN_ID,IS_DESIGN,FILTER,BELONG_TEMPLATE)values(";
					sql+=nextOrder+",'"+loginUserUtil.getUserAccount(request)+"',"+modelCode+","+jsonObject.getInt("ID")+",'"+(jsonObject.getBoolean("IS_DESIGN")?"Y":"N")+"','"+(jsonObject.get("FILTER_CON")!=null && !"".equals(jsonObject.get("FILTER_CON").toString())?jsonObject.getString("FILTER_CON"):" ")+"','"+templateName+"'";
				sql+=") ";
				nextOrder++;
				if(i==(columnsConfig.size()-1)){
					sql+="SELECT 1 FROM DUAL";
				}
			}
			jdbcTemplate.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
			resultObject.put("message","系统异常.");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	/*
	 * 	获取已配置的订阅模板.
	 * 
	 * */
	@RequestMapping("/findTemplatesConfiged.ilf")
	public void findTemplatesConfiged(
		@RequestParam String modelId,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			resultObject.put("OPTIONS",jdbcTemplate.queryForList("SELECT DISTINCT(BELONG_TEMPLATE) FROM DATA_DESIGN WHERE USER_ACCOUNT = '"+loginUserUtil.getUserAccount(request)+"' AND MODEL_ID = "+modelId));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
			resultObject.put("message","系统异常.");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	/*
	 * 	删除订阅模板.
	 * 
	 * */
	@RequestMapping("/removeTemplate.ilf")
	public void removeTemplate(
		@RequestParam String modelCode,
		@RequestParam String templateName,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		templateName = java.net.URLDecoder.decode(templateName,"UTF-8");
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			jdbcTemplate.execute("DELETE FROM DATA_DESIGN WHERE USER_ACCOUNT = '"+loginUserUtil.getUserAccount(request)+"' AND MODEL_ID = "+modelCode+" AND BELONG_TEMPLATE = '"+templateName+"'");
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
	
	/*
	 * 	查询表头（动态订阅模型的模型标识）
	 * 
	 * */
	@RequestMapping("/findColumns.ilf")
	public void findColumns(
		@RequestParam String modelId,
		@RequestParam String templateName,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		templateName = java.net.URLDecoder.decode(templateName,"UTF-8");
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		try{
			String columnSql = "";
			columnSql+="SELECT A.ID AS DESIGN_ID,B.COLUMN_ALIAS,B.COLUMN_NAME,B.IS_EXPORT,B.IS_PRIMARY_KEY ";
			columnSql+="FROM DATA_DESIGN A,BASIC_DB_COLUMN B ";
			columnSql+="WHERE A.COLUMN_ID = B.ID AND A.IS_DESIGN = 'Y' AND A.MODEL_ID = "+modelId+" AND A.BELONG_TEMPLATE = '"+templateName+"' AND A.USER_ACCOUNT = '"+loginUserUtil.getUserAccount(request)+"' ORDER BY A.COLUMN_ID ASC";
			List<Map<String,Object>> columns = jdbcTemplate.queryForList(columnSql);
			if(columns.size()>0){
				jsonObject.put("is_column",true);
				jsonObject.put("columns",columns);
			}else{
				jsonObject.put("is_column",false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObject.toString());
		}
	}
	
	/*
	 * 	查询我的订阅列表
	 * 
	 * */
	@RequestMapping("/findMyItems.ilf")
	public void findMyItems(@RequestParam String tableparam,@RequestParam String conditions,HttpServletRequest request,HttpServletResponse response)throws Exception{
		Long sEcho = 0L;
		Integer displayStart = 0;
		Integer iDisplayLength = 0;
		JSONArray jsons = JSONArray.fromObject(tableparam);
		HashMap<String,Object> conditonMap = new HashMap<String,Object>();
		if(jsons!=null && jsons.size()!=0){
			for(int i=0;i<jsons.size();i++){
				JSONObject json = JSONObject.fromObject(jsons.get(i));
				String key = json.getString("name");
				if(key.equals("sEcho")){
					sEcho = Long.parseLong(json.getString("value"));
				}else if(key.equals("iDisplayStart")){
					displayStart = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayStart",displayStart);
				}else if(key.equals("iDisplayLength")){
					iDisplayLength = Integer.parseInt(json.getString("value"));
					conditonMap.put("iDisplayLength",iDisplayLength);
				}
			}
		}
		JSONArray condition = JSONArray.fromObject(conditions);
		if(conditions!=null && condition.size()!=0){
			for(int i=0;i<condition.size();i++){
				JSONObject jsonObject = JSONObject.fromObject(condition.get(i));
				if(jsonObject.get("value")!=null && !"".equals(jsonObject.getString("value"))){
					conditonMap.put(jsonObject.getString("name"),jsonObject.getString("value"));
				}
			}
		}		
		Integer count = 0;
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();	
		if(conditonMap.containsKey("BIND_TABLE") && conditonMap.get("BIND_TABLE")!=null && !"-1".equals(conditonMap.get("BIND_TABLE").toString())){
			/*
			 * 	首先：获取<模型>对应<数据源>链接.
			 * 
			 * */
			Map<String,Object> dbObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB WHERE ID IN (SELECT BELONG_DB FROM BASIC_DB_TABLE WHERE ID = "+conditonMap.get("BIND_TABLE").toString()+")");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(
				"jdbc:oracle:thin:@"+dbObject.get("IP_ADDRESS").toString()+":"+dbObject.get("PORT").toString()+":"+dbObject.get("SID").toString()+"",
				dbObject.get("USER_NAME").toString(),
				dbObject.get("USER_PASS").toString()
			);
			Statement stateMent = connection.createStatement();
			/*然后：拼接SQL*/
			Map<String,Object> modelObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB_TABLE WHERE ID = "+conditonMap.get("BIND_TABLE").toString());
			String tableName = modelObject.get("TABLE_NAME").toString();
			String columnSql = "";
			String cityColumn = "";
			String sql = "";
			sql+="SELECT A.ID AS DESIGN_ID,B.COLUMN_ALIAS,B.COLUMN_NAME,B.DATA_TYPE,B.IS_EXPORT,B.IS_PRIMARY_KEY,A.FILTER ";
			sql+="FROM DATA_DESIGN A,BASIC_DB_COLUMN B ";
			sql+="WHERE A.COLUMN_ID = B.ID AND A.IS_DESIGN = 'Y' AND A.BELONG_TEMPLATE = '"+conditonMap.get("TEMPLATE_NAME").toString()+"' AND A.MODEL_ID = "+conditonMap.get("BIND_TABLE").toString()+" AND A.USER_ACCOUNT = '"+loginUserUtil.getUserAccount(request)+"' ORDER BY A.COLUMN_ID ASC";
			List<Map<String,Object>> columns = jdbcTemplate.queryForList(sql);
			for(int i=0;i<columns.size();i++){
				Map<String,Object> columnObj = columns.get(i);
				if(columnObj.get("DATA_TYPE")!=null && "地市".equals(columnObj.get("DATA_TYPE").toString())){
					cityColumn = columnObj.get("COLUMN_NAME").toString();
				}
				/*
				 * 	组装ColumnSql.
				 * 
				 * */
				if("".equals(columnSql)){
					columnSql = columnObj.get("COLUMN_NAME").toString();
				}else{
					columnSql+= ","+columnObj.get("COLUMN_NAME").toString();
				}
				if(i==(columns.size()-1)){
					columnSql+= ",ROWNUM AS RN";
				}
			}
			String finalSql = "SELECT "+columnSql+" FROM "+tableName;
			for(int w=0;w<columns.size();w++){
				Map<String,Object> columnObj = columns.get(w);
				if(columnObj.get("FILTER")!=null && !"".equals(columnObj.get("FILTER").toString()) && !" ".equals(columnObj.get("FILTER").toString())){
					/*
					 * 	特殊字符处理：将双引号替换为单引号.
					 * 
					 * */
					if(columnObj.get("FILTER").toString().indexOf("\"")!=-1 || columnObj.get("FILTER").toString().indexOf("”")!=-1 || columnObj.get("FILTER").toString().indexOf("“")!=-1){
						columnObj.put("FILTER",columnObj.get("FILTER").toString().replaceAll("\"","'"));
						columnObj.put("FILTER",columnObj.get("FILTER").toString().replaceAll("”","'"));
						columnObj.put("FILTER",columnObj.get("FILTER").toString().replaceAll("“","'"));
					}
					if(finalSql.indexOf("WHERE")==-1){
						finalSql+=" WHERE "+columnObj.get("COLUMN_NAME").toString()+" "+columnObj.get("FILTER").toString();
					}else{
						finalSql+=" AND "+columnObj.get("COLUMN_NAME").toString()+" "+columnObj.get("FILTER").toString();
					}
				}
			}
			if(!"".equals(cityColumn)){
				Boolean IS_PROVINCE = false;
				String CITY_NAME = "";
				Object loginObject = request.getSession().getAttribute("LoginUserInfo");
				if(loginObject!=null){
					Map<String,Object> loginUser = (HashMap<String,Object>)loginObject;
					if(loginUser.get("BELONG_AREA").toString().indexOf("四川")!=-1 || loginUser.get("BELONG_AREA").toString().indexOf("省")!=-1){
						IS_PROVINCE = true;
					}
					CITY_NAME = loginUser.get("BELONG_AREA").toString();
				}
				if(!IS_PROVINCE){
					if(finalSql.indexOf("WHERE")==-1){
						finalSql+=" WHERE "+cityColumn+" LIKE '%"+CITY_NAME+"%'";
					}else{
						finalSql+=" AND "+cityColumn+" LIKE '%"+CITY_NAME+"%'";
					}
				}
			}
			/*1.查询符合条件的记录数*/
			ResultSet resultSet = stateMent.executeQuery("SELECT COUNT(1) AS ROW_COUNT FROM ("+finalSql+")");
			resultSet.next();
			count = resultSet.getInt("ROW_COUNT");
			/*2.分页数据*/
			if(finalSql.indexOf("WHERE")!=-1){
				sql = "SELECT W.* FROM ("+finalSql+" AND ROWNUM <= "+(displayStart+iDisplayLength)+") W WHERE W.RN > "+displayStart;
			}else{
				sql = "SELECT W.* FROM ("+finalSql+" WHERE ROWNUM <= "+(displayStart+iDisplayLength)+") W WHERE W.RN > "+displayStart;
			}
			resultSet = stateMent.executeQuery(sql);
			while(resultSet.next()){
				Map<String,Object> item = new HashMap<String,Object>();
				for(int i=0;i<columns.size();i++){
					Map<String,Object> columnObj = columns.get(i);
					String columnName = columnObj.get("COLUMN_NAME").toString();
					item.put(columnName,resultSet.getObject(columnName)==null?"":resultSet.getObject(columnName).toString());
				}
				datas.add(item);
			}
			/*
			 * 	关闭数据库链接
			 * 
			 * */
			if(resultSet!=null){
				resultSet.close();
			}
			if(stateMent!=null){
				stateMent.close();
			}
			if(connection!=null){
				connection.close();
			}
		}
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(datas);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	资源数据下载.
	 * 
	 * */
	@RequestMapping("/exportData.ilf")
	public void exportData(
		@RequestParam String modelId,
		@RequestParam String templateName,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		templateName = java.net.URLDecoder.decode(templateName,"UTF-8");
		try{
			/*首先：获取模型对应数据源链接*/
			Map<String,Object> dbObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB WHERE ID IN (SELECT BELONG_DB FROM BASIC_DB_TABLE WHERE ID = "+modelId+")");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection(
				"jdbc:oracle:thin:@"+dbObject.get("IP_ADDRESS").toString()+":"+dbObject.get("PORT").toString()+":"+dbObject.get("SID").toString()+"",
				dbObject.get("USER_NAME").toString(),
				dbObject.get("USER_PASS").toString()
			);
			Statement stateMent = connection.createStatement();
			/*然后：拼接SQL*/
			String columnSql = "";
			String modelName = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB_TABLE WHERE ID = "+modelId).get("TABLE_NAME").toString();			
			String sql = "";
			sql+="SELECT A.ID AS DESIGN_ID,B.COLUMN_ALIAS,B.COLUMN_NAME,B.IS_EXPORT,B.IS_PRIMARY_KEY,A.FILTER ";
			sql+="FROM DATA_DESIGN A,BASIC_DB_COLUMN B ";
			sql+="WHERE A.COLUMN_ID = B.ID AND A.IS_DESIGN = 'Y' AND A.BELONG_TEMPLATE = '"+templateName+"' AND A.MODEL_ID = "+modelId+" AND A.USER_ACCOUNT = '"+loginUserUtil.getUserAccount(request)+"' ORDER BY A.COLUMN_ID ASC";
			List<Map<String,Object>> columns = jdbcTemplate.queryForList(sql);
			for(int i=0;i<columns.size();i++){
				Map<String,Object> columnObj = columns.get(i);
				if("".equals(columnSql)){
					columnSql = columnObj.get("COLUMN_NAME").toString();
				}else{
					columnSql+= ","+columnObj.get("COLUMN_NAME").toString();
				}
			}
			String finalSql = "SELECT "+columnSql+" FROM "+modelName;
			for(int w=0;w<columns.size();w++){
				Map<String,Object> columnObj = columns.get(w);
				if(columnObj.get("FILTER")!=null && !"".equals(columnObj.get("FILTER").toString()) && !" ".equals(columnObj.get("FILTER").toString())){
					if(finalSql.indexOf("WHERE")==-1){
						finalSql+=" WHERE "+columnObj.get("COLUMN_NAME").toString()+" "+columnObj.get("FILTER").toString();
					}else{
						finalSql+=" OR "+columnObj.get("COLUMN_NAME").toString()+" "+columnObj.get("FILTER").toString();
					}
				}
			}
			/*1.查询符合条件的记录数*/
			ResultSet resultSet = stateMent.executeQuery(finalSql);
			XSSFSheet xssfSheet = null;
	        FileInputStream fileInputStreams = new FileInputStream(new File(
	        	jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'dataExportTemplate'").get("PRO_VALUE").toString()
	        ));
	        XSSFWorkbook workBook = new XSSFWorkbook(fileInputStreams);      
	        Iterator<XSSFSheet> sheetCar = workBook.iterator();
	        while(sheetCar.hasNext()){
	        	xssfSheet = (XSSFSheet)sheetCar.next();
	        	break;
	        }
	        XSSFCellStyle leftWhites = StyleUtil.getStyle(workBook,HSSFCellStyle.ALIGN_LEFT,HSSFColor.WHITE.index);
	        XSSFCellStyle centerGray = StyleUtil.getStyle(workBook,HSSFCellStyle.ALIGN_CENTER_SELECTION,HSSFColor.GREY_25_PERCENT.index);
	        /*表头*/
	        XSSFRow tableHead = xssfSheet.createRow(1);
	        tableHead.setHeight(new Integer(430).shortValue());
	        for(int r=1;r<=columns.size();r++){
				XSSFCell cell = tableHead.createCell(r);
				cell.setCellStyle(centerGray);
			}
	        xssfSheet.addMergedRegion(new CellRangeAddress(1,1,1,columns.size()));
	        xssfSheet.getRow(1).getCell(1).setCellValue(modelName);
	        /*字段列表*/
	        XSSFRow columnHead = xssfSheet.createRow(2);
	        columnHead.setHeight(new Integer(430).shortValue());
	        for(int r=1;r<=columns.size();r++){
	        	XSSFCell hCell = columnHead.createCell(r);
	        	hCell.setCellStyle(leftWhites);
	        	hCell.setCellValue(columns.get(r-1).get("COLUMN_ALIAS").toString());
	        	xssfSheet.setColumnWidth(r,((columns.get(r-1).get("COLUMN_ALIAS").toString().length())*1200));
			}
	        /*渲染具体的数据*/
	        Integer currentRowIndex = 3;
			while(resultSet.next()){
				XSSFRow dataRow = xssfSheet.createRow(currentRowIndex);
				dataRow.setHeight(new Integer(430).shortValue());
				for(int r=1;r<=columns.size();r++){
		        	XSSFCell dCell = dataRow.createCell(r);
		        	dCell.setCellStyle(leftWhites);
		        	dCell.setCellValue(
		        		resultSet.getObject(columns.get(r-1).get("COLUMN_NAME").toString())==null?"":resultSet.getObject(columns.get(r-1).get("COLUMN_NAME").toString()).toString()           
		        	);
				}
				currentRowIndex++;
			}
			String fileName = new Date().getTime()+".xlsx";
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+fileName
			));
			workBook.write(fileOutputStream);
			fileOutputStream.close();
			fileInputStreams.close();
			/*关闭数据库链接*/
			if(resultSet!=null){
				resultSet.close();
			}
			if(stateMent!=null){
				stateMent.close();
			}
			if(connection!=null){
				connection.close();
			}
			File dataFile = new File(
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+fileName
			);
			if(dataFile.exists()){
				FileInputStream fileInputStream = new FileInputStream(dataFile);
				long filesize = dataFile.length();
				response.setContentType("text/html;charset=gb2312");
				response.addHeader("content-type","application/x-msdownload;");
				response.setHeader("Content-Disposition","attachment;filename=\""+new String((dataFile.getName()).getBytes(),"ISO8859-1"));
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
