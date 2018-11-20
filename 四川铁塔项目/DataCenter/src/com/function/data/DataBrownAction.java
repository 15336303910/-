package com.function.data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.function.rules.util.StyleUtil;
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.data.DataBrownAction")
@RequestMapping(value="/dataBrownAction")
public class DataBrownAction {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	查询列表
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
		Integer count = 0;
		List<Map<String,Object>> datas = new ArrayList<Map<String,Object>>();	
		if(conditonMap.containsKey("BIND_TABLE") && conditonMap.get("BIND_TABLE")!=null && !"-1".equals(conditonMap.get("BIND_TABLE").toString())){
			/*首先：获取模型对应数据源链接*/
			Map<String,Object> dbObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB WHERE ID IN (SELECT BELONG_DB FROM BASIC_DB_TABLE WHERE ID = "+conditonMap.get("BIND_TABLE").toString()+")");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@"+dbObject.get("IP_ADDRESS").toString()+":"+dbObject.get("PORT").toString()+":"+dbObject.get("SID").toString()+"",dbObject.get("USER_NAME").toString(),dbObject.get("USER_PASS").toString());
			Statement stateMent = connection.createStatement();
			/*然后：拼接SQL*/
			Map<String,Object> modelObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB_TABLE WHERE ID = "+conditonMap.get("BIND_TABLE").toString());
			String tableName = modelObject.get("TABLE_NAME").toString();
			String nameColumn = "";
			String columnSql = "";
			String cityColumn = "";
			List<Map<String,Object>> columns = jdbcTemplate.queryForList("SELECT COLUMN_ALIAS,COLUMN_NAME,IS_EXPORT,IS_PRIMARY_KEY,DATA_TYPE FROM BASIC_DB_COLUMN WHERE BELONG_TABLE = "+conditonMap.get("BIND_TABLE").toString()+" ORDER BY ID ASC");
			for(int i=0;i<columns.size();i++){
				/*组装包含具体字段的SQL*/
				Map<String,Object> columnObj = columns.get(i);
				if("".equals(columnSql)){
					columnSql = columnObj.get("COLUMN_NAME").toString();
				}else{
					columnSql+= ","+columnObj.get("COLUMN_NAME").toString();
				}
				if(i==(columns.size()-1)){
					columnSql+= ",ROWNUM AS RN";
				}
				/*查找资源[名称]Column*/
				if("Y".equals(columnObj.get("IS_EXPORT").toString()) && "N".equals(columnObj.get("IS_PRIMARY_KEY").toString())){
					nameColumn = columnObj.get("COLUMN_NAME").toString();
				}
				/*查找资源[地市]Column*/
				if(columnObj.get("DATA_TYPE")!=null && "地市".equals(columnObj.get("DATA_TYPE").toString())){
					cityColumn = columnObj.get("COLUMN_NAME").toString();
				}
			}
			String sql = "SELECT "+columnSql+" FROM "+tableName+" WHERE 1 = 1";
			if(conditonMap.containsKey("NAME_KEY") && conditonMap.get("NAME_KEY")!=null && !"".equals(conditonMap.get("NAME_KEY").toString())){
				sql+=" AND "+nameColumn+" LIKE '%"+conditonMap.get("NAME_KEY").toString()+"%'";
			}
			/*分权分域*/
			if(conditonMap.containsKey("CITY_NAME") && conditonMap.get("CITY_NAME")!=null && !"".equals(conditonMap.get("CITY_NAME").toString())){
				sql+=" AND "+cityColumn+" LIKE '%"+conditonMap.get("CITY_NAME").toString()+"%'";
			}else{
				Boolean isProvince = false;
				String belongArea = "";
				Object loginObject = request.getSession().getAttribute("LoginUserInfo");
				if(loginObject!=null){
					Map<String,Object> loginUser = (HashMap<String,Object>)loginObject;
					if(loginUser.get("BELONG_AREA").toString().indexOf("川")!=-1 || loginUser.get("BELONG_AREA").toString().indexOf("省")!=-1){
						isProvince = true;
					}else if(loginUser.get("USER_NAME").toString().toUpperCase().indexOf("ROOT")!=-1){
						isProvince = true;
					}else{
						belongArea = loginUser.get("BELONG_AREA").toString();
					}
					if(belongArea.length()>2){
						belongArea = belongArea.substring(0,2);
					}
				}
				if(!isProvince && !"".equals(cityColumn)){
					sql+=" AND "+cityColumn+" LIKE '%"+belongArea+"%'";
				}
			}
			/*1.查询符合条件的记录数*/
			ResultSet resultSet = stateMent.executeQuery("SELECT COUNT(1) AS ROW_COUNT FROM ("+sql+")");
			resultSet.next();
			count = resultSet.getInt("ROW_COUNT");
			/*2.分页数据*/
			sql = "SELECT W.* FROM ("+sql+" AND ROWNUM <= "+(displayStart+iDisplayLength)+") W WHERE W.RN > "+displayStart;
			resultSet = stateMent.executeQuery(sql);
			while(resultSet.next()){
				Map<String,Object> item = new HashMap<String,Object>();
				for(int i=0;i<columns.size();i++){
					Map<String,Object> columnObj = columns.get(i);
					String columnName = columnObj.get("COLUMN_NAME").toString();
					item.put(columnName,resultSet.getObject(columnName)==null?"":resultSet.getObject(columnName).toString());
					if("Y".equals(columnObj.get("IS_EXPORT").toString()) && "Y".equals(columnObj.get("IS_PRIMARY_KEY").toString())){
						item.put("RES_ID",resultSet.getObject(columnName)==null?"":resultSet.getObject(columnName).toString());
					}
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
	 * 	查询表头
	 * 
	 * */
	@RequestMapping("/findColumns.ilf")
	public void findColumns(
		@RequestParam String modelId,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		try{
			List<Map<String,Object>> columns = jdbcTemplate.queryForList("SELECT COLUMN_ALIAS,COLUMN_NAME,IS_EXPORT,IS_PRIMARY_KEY FROM BASIC_DB_COLUMN WHERE BELONG_TABLE = "+modelId+" ORDER BY ID ASC");
			if(columns.size()>0){
				jsonObject.put("is_column",true);
				jsonObject.put("columns",columns);
			}else{
				jsonObject.put("is_column",false);
			}
			/*
			 * 	验证是否存在地市字段
			 * 
			 * */
			List<Map<String,Object>> CITY_COLUMNS = jdbcTemplate.queryForList("SELECT * FROM BASIC_DB_COLUMN WHERE BELONG_TABLE = "+modelId+" AND DATA_TYPE = '地市'");
			if(CITY_COLUMNS.size()>0){
				jsonObject.put("IS_CITY_COLUMN_EXIST",true);
				List<String> CITY_LIST = new ArrayList<String>();
				/*
				 * 	获取地市列表
				 * 
				 * */
				String COLUMN_NAME = CITY_COLUMNS.get(0).get("COLUMN_NAME").toString();
				String BELONG_TABLE = CITY_COLUMNS.get(0).get("BELONG_TABLE").toString();
				Map<String,Object> MODEL_OBJ = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB_TABLE WHERE ID = "+BELONG_TABLE);
				String MODEL_NAME = MODEL_OBJ.get("TABLE_NAME").toString();
				Map<String,Object> dbObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB WHERE ID IN (SELECT BELONG_DB FROM BASIC_DB_TABLE WHERE ID = "+BELONG_TABLE+")");
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection connection = DriverManager.getConnection(
					"jdbc:oracle:thin:@"+dbObject.get("IP_ADDRESS").toString()+":"+dbObject.get("PORT").toString()+":"+dbObject.get("SID").toString()+"",
					dbObject.get("USER_NAME").toString(),
					dbObject.get("USER_PASS").toString()
				);
				Statement stateMent = connection.createStatement();
				String CITY_SQL = "SELECT DISTINCT("+COLUMN_NAME+") AS DQM_CITY_NAME FROM "+MODEL_NAME+" WHERE "+COLUMN_NAME+" IS NOT NULL";
				Boolean IS_PROVINCE = loginUserUtil.isProvince(request);
				if(!IS_PROVINCE){
					String BELONG_CITY = loginUserUtil.getBelongArea(request);
					CITY_SQL+=" AND "+COLUMN_NAME+" LIKE '%"+BELONG_CITY+"%'";
				}else{
					CITY_LIST.add("-");
				}
				ResultSet resultSet = stateMent.executeQuery(CITY_SQL);
				while(resultSet.next()){
					String CITY_NAME_CAR = resultSet.getObject("DQM_CITY_NAME").toString();
					CITY_LIST.add(CITY_NAME_CAR);
				}
				jsonObject.put("CITY_LIST",CITY_LIST);
				/*
				 * 	关闭链接
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
			}else{
				jsonObject.put("IS_CITY_COLUMN_EXIST",false);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObject.toString());
		}
	}
	
	/*
	 * 	删除资源数据
	 * 
	 * */
	@RequestMapping("/removeResData.ilf")
	public void removeResData(@RequestParam String modelId,@RequestParam String checkedResId,HttpServletResponse response)throws Exception{
		JSONObject jsonObject = JSONObject.fromObject("{success:true}");
		Boolean isPrimaryString = false;
		String tableName = "";
		String primaryKey = "";
		try{
			/*1.获取模型所在数据源的数据库链接.*/
			Map<String,Object> dbObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB WHERE ID IN (SELECT BELONG_DB FROM BASIC_DB_TABLE WHERE ID = "+modelId+")");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@"+dbObject.get("IP_ADDRESS").toString()+":"+dbObject.get("PORT").toString()+":"+dbObject.get("SID").toString()+"",dbObject.get("USER_NAME").toString(),dbObject.get("USER_PASS").toString());
			Statement stateMent = connection.createStatement();
			/*2.获取模型名称.*/
			Map<String,Object> modelObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB_TABLE WHERE ID = "+modelId);
			tableName = modelObject.get("TABLE_NAME").toString();
			/*3.获取主键字段名称、主键字段类型.*/
			List<Map<String,Object>> columns = jdbcTemplate.queryForList("SELECT COLUMN_ALIAS,COLUMN_NAME,DATA_TYPE,IS_EXPORT,IS_PRIMARY_KEY FROM BASIC_DB_COLUMN WHERE BELONG_TABLE = "+modelId+" ORDER BY ID ASC");
			for(int i=0;i<columns.size();i++){
				Map<String,Object> columnObj = columns.get(i);
				if("Y".equals(columnObj.get("IS_EXPORT").toString()) && "Y".equals(columnObj.get("IS_PRIMARY_KEY").toString())){
					primaryKey = columnObj.get("COLUMN_NAME").toString();
					if("字符串".equals(columnObj.get("DATA_TYPE").toString())){
						isPrimaryString = true;
					}else{
						isPrimaryString = false;
					}
					break;
				}
			}
			/*3.执行资源清除.*/
			String sql = "";
			if(isPrimaryString){
				sql = "DELETE FROM "+tableName+" WHERE "+primaryKey+" = '"+checkedResId+"'";
			}else{
				sql = "DELETE FROM "+tableName+" WHERE "+primaryKey+" = "+checkedResId;
			}
			stateMent.execute(sql);
			/*
			 * 	关闭数据库链接
			 * 
			 * */
			if(stateMent!=null){
				stateMent.close();
			}
			if(connection!=null){
				connection.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObject.toString());
		}
	}
	
	/*
	 * 	资源数据下载.
	 * 
	 * */
	@RequestMapping("/exportData.ilf")
	public void exportData(
		@RequestParam String modelId,
		@RequestParam String belongCity,
		@RequestParam String nameKey,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		belongCity = java.net.URLDecoder.decode(belongCity,"UTF-8");
		nameKey = java.net.URLDecoder.decode(nameKey,"UTF-8");
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
			String CITY_COLUMN = "";
			String RES_NAME_COLUMN = "";
			String modelName = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB_TABLE WHERE ID = "+modelId).get("TABLE_NAME").toString();
			String columnSql = "";
			List<Map<String,Object>> columns = jdbcTemplate.queryForList("SELECT COLUMN_ALIAS,COLUMN_NAME,IS_EXPORT,IS_PRIMARY_KEY,DATA_TYPE FROM BASIC_DB_COLUMN WHERE BELONG_TABLE = "+modelId+" ORDER BY ID ASC");
			for(int i=0;i<columns.size();i++){
				Map<String,Object> columnObj = columns.get(i);
				if("".equals(columnSql)){
					columnSql = columnObj.get("COLUMN_NAME").toString();
				}else{
					columnSql+= ","+columnObj.get("COLUMN_NAME").toString();
				}
				if("地市".equals(columnObj.get("DATA_TYPE").toString())){
					CITY_COLUMN = columnObj.get("COLUMN_NAME").toString();
				}
				if("N".equals(columnObj.get("IS_PRIMARY_KEY").toString()) && "Y".equals(columnObj.get("IS_EXPORT").toString())){
					RES_NAME_COLUMN = columnObj.get("COLUMN_NAME").toString();
				}
			}
			String sql = "SELECT "+columnSql+" FROM "+dbObject.get("USER_NAME").toString()+"."+modelName+" WHERE 1 = 1";
			if(belongCity!=null && !"".equals(belongCity) && !"-".equals(belongCity) && !"".equals(CITY_COLUMN)){
				sql+=" AND "+CITY_COLUMN+" = '"+belongCity+"'";
			}
			if(nameKey!=null && !"".equals(nameKey) && !"-".equals(nameKey) && !"".equals(RES_NAME_COLUMN)){
				sql+=" AND "+RES_NAME_COLUMN+" LIKE '%"+nameKey+"%'";
			}
			System.out.println("=>"+sql);
			/*1.查询符合条件的记录数*/
			ResultSet resultSet = stateMent.executeQuery(sql);
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
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+fileName)
			);
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
	
	/*
	 * 	资源模板下载.
	 * 
	 * */
	@RequestMapping("/exportTemplate.ilf")
	public void exportTemplate(@RequestParam String modelId,HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String modelName = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB_TABLE WHERE ID = "+modelId).get("TABLE_NAME").toString();
			List<Map<String,Object>> columns = jdbcTemplate.queryForList("SELECT COLUMN_ALIAS,COLUMN_NAME,IS_EXPORT,IS_PRIMARY_KEY FROM BASIC_DB_COLUMN WHERE BELONG_TABLE = "+modelId+" ORDER BY ID ASC");
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
	        XSSFCellStyle centerWhite = StyleUtil.getStyle(workBook,HSSFCellStyle.ALIGN_CENTER_SELECTION,HSSFColor.WHITE.index);
	        XSSFCellStyle centerGray = StyleUtil.getStyle(workBook,HSSFCellStyle.ALIGN_CENTER_SELECTION,HSSFColor.GREY_25_PERCENT.index);
	        XSSFRow tableHead = xssfSheet.createRow(1);
	        tableHead.setHeight(new Integer(430).shortValue());
	        for(int r=1;r<=6;r++){
				XSSFCell cell = tableHead.createCell(r);
				if(r==1 || r==3 || r==5){
					cell.setCellStyle(centerGray);
					if(r==1){
						cell.setCellValue("模型名称：");
					}else if(r==3){
						cell.setCellValue("主键是否指定序列：");
					}else if(r==5){
						cell.setCellValue("序列：");
					}
				}else{
					cell.setCellStyle(centerWhite);
					if(r==2){
						cell.setCellValue(modelName);
					}else if(r==4){
						cell.setCellValue("否");
					}else if(r==6){
						cell.setCellValue("");
					}
				}
			}
	        /*字段列表*/
	        XSSFRow columnHead = xssfSheet.createRow(2);
	        columnHead.setHeight(new Integer(430).shortValue());
	        for(int r=1;r<=columns.size();r++){
	        	XSSFCell hCell = columnHead.createCell(r);
	        	hCell.setCellStyle(centerWhite);
	        	hCell.setCellValue(columns.get(r-1).get("COLUMN_ALIAS").toString());
	        	xssfSheet.setColumnWidth(r,((columns.get(r-1).get("COLUMN_ALIAS").toString().length())*1200));
			}
	        for(int r=3;r<=5;r++){
	        	XSSFRow newRow = xssfSheet.createRow(r);
	        	newRow.setHeight(new Integer(430).shortValue());
	        	for(int w=1;w<=columns.size();w++){
		        	XSSFCell dCell = newRow.createCell(w);
		        	dCell.setCellStyle(centerWhite);
		        	dCell.setCellValue("");
				}
	        }
			String fileName = new Date().getTime()+".xlsx";
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+fileName
			));
			workBook.write(fileOutputStream);
			fileOutputStream.close();
			fileInputStreams.close();
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
	
	/*
	 * 	批量上传数据
	 * 
	 * */
	@RequestMapping(value="/uploadData.ilf",method=RequestMethod.POST)
	public void uploadData(@RequestParam("TemplateUpload")MultipartFile fileToUpload,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			if(!fileToUpload.isEmpty()){
				String strDirPath = request.getSession().getServletContext().getRealPath("/")+"uploads/viewdata/"+fileToUpload.getOriginalFilename();
				File file = new File(strDirPath);
				if(file.exists()){
					file.delete();
				}
				fileToUpload.transferTo(file);
				/*解析文件并查询入库*/
				FileInputStream fis = new FileInputStream(file);
				XSSFWorkbook wk = new XSSFWorkbook(fis);
				Iterator<XSSFSheet> sheetCar = wk.iterator();
				XSSFSheet carSheet = null;
				while(sheetCar.hasNext()){
					carSheet = (XSSFSheet)sheetCar.next();
					break;
				}
				if(carSheet!=null){
					/*开始解析入库*/
					String modelName = carSheet.getRow(1).getCell(2).getStringCellValue();
					List<Map<String,Object>> models = jdbcTemplate.queryForList("SELECT * FROM BASIC_DB_TABLE WHERE TABLE_NAME = '"+modelName+"'");
					if(models.size()==0){
						resultObject.put("success",false);
						resultObject.put("message","模型名称配置不正确，请确认.");
					}else{
						/*首先获取数据库连接*/
						Integer modelId = Integer.parseInt(models.get(0).get("ID").toString());
						Map<String,Object> dbObject = jdbcTemplate.queryForMap("SELECT * FROM BASIC_DB WHERE ID IN (SELECT BELONG_DB FROM BASIC_DB_TABLE WHERE ID = "+modelId+")");
						Class.forName("oracle.jdbc.driver.OracleDriver");
						Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@"+dbObject.get("IP_ADDRESS").toString()+":"+dbObject.get("PORT").toString()+":"+dbObject.get("SID").toString()+"",dbObject.get("USER_NAME").toString(),dbObject.get("USER_PASS").toString());
						connection.setAutoCommit(false);
						String columnSql = "";
						String tokenSql = "";
						Integer keyIndex = 0;
						Boolean keyPrimary = false;
						XSSFRow xssfRow = carSheet.getRow(2);
						List<Map<String,Object>> columns = jdbcTemplate.queryForList("SELECT * FROM BASIC_DB_COLUMN WHERE BELONG_TABLE = "+modelId+" ORDER BY ID ASC");
						for(int i=1;i<=columns.size();i++){
							String columnTitle = xssfRow.getCell(i).getStringCellValue();
							for(int j=0;j<columns.size();j++){
								Map<String,Object> columnObj = columns.get(j);
								if(columnTitle.equals(columnObj.get("COLUMN_ALIAS").toString())){
									/*columnSql*/
									columnTitle = columnObj.get("COLUMN_NAME").toString();
									if("".equals(columnSql)){
										columnSql = columnTitle;
									}else{
										columnSql+= ","+columnTitle;
									}
									/*?,?,?,?,?*/
									if("Y".equals(columnObj.get("IS_PRIMARY_KEY").toString())){
										keyIndex = i;
										String isHavePriKey = carSheet.getRow(1).getCell(4).getStringCellValue();
										if("是".equals(isHavePriKey)){
											String sequenceName = carSheet.getRow(1).getCell(6).getStringCellValue();
											if("".equals(tokenSql)){
												tokenSql = sequenceName+".NEXTVAL";
											}else{
												tokenSql+= ","+sequenceName+".NEXTVAL";
											}
											keyPrimary = true;
										}else{
											if("".equals(tokenSql)){
												tokenSql = "?";
											}else{
												tokenSql+= ",?";
											}
										}
									}else{
										if("".equals(tokenSql)){
											tokenSql = "?";
										}else{
											tokenSql+= ",?";
										}
									}
									break;
								}
							}
							
						}
						String prepareSql = "insert into "+modelName+"("+columnSql+")values("+tokenSql+")";
						PreparedStatement pStatement = connection.prepareStatement(prepareSql);
						Integer questionIndex = 1;
						for(int r=3;r<=carSheet.getLastRowNum();r++){
							/*记录*/
							XSSFRow dataRow = carSheet.getRow(r);
							if(dataRow!=null){
								for(int v=1;v<=columns.size();v++){
									if(v==keyIndex && keyPrimary){
										continue;
									}else{
										/*字段*/
										XSSFCell dataCell = dataRow.getCell(v);
										String cellValue = "";
										if(dataCell.getCellType() == dataCell.CELL_TYPE_STRING){
											cellValue = dataCell.getStringCellValue();
						                }else if(dataCell.getCellType() == dataCell.CELL_TYPE_NUMERIC){
						                	Double doubleValue = Double.parseDouble(dataCell.getNumericCellValue()+"");
						                	cellValue = doubleValue.intValue()+"";
						                }
										Boolean isNumber = true;
										String columnName = carSheet.getRow(2).getCell(v).getStringCellValue();
										for(int m=0;m<=columns.size();m++){
											if(columnName.equals(columns.get(m).get("COLUMN_ALIAS").toString())){
												if("数字".equals(columns.get(m).get("DATA_TYPE").toString())){
													isNumber = true;
													cellValue = Integer.parseInt(cellValue)+"";
												}else{
													isNumber = false;
												}
												break;
											}
										}
										if(isNumber){
											pStatement.setInt(questionIndex,Integer.parseInt(cellValue));
										}else{
											pStatement.setString(questionIndex,cellValue);
										}
										questionIndex++;
									}
								}
								pStatement.addBatch();
							}
							if(r%4000==0){
								pStatement.executeBatch();
								connection.commit();
								pStatement.clearBatch();
							}
						}
						pStatement.executeBatch();
						connection.commit();
						pStatement.clearBatch();
						/*
						 * 	关闭数据库连接.
						 * 
						 * */
						if(pStatement!=null){
							pStatement.close();
						}
						if(connection!=null){
							connection.close();
						}
					}
				}
			}else{
				resultObject.put("success",false);
				resultObject.put("message","服务端异常，请联系系统管理员.");
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
			resultObject.put("message","服务端异常，请联系系统管理员.");
		}finally{
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
