package com.function.data;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
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

import com.function.compare.model.CompareColumn;
import com.function.compare.model.CompareDetail;
import com.function.compare.model.CompareQuartz;
import com.function.rules.util.StyleUtil;
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.data.ElecPayHistory")
@RequestMapping(value="/elecPayHistory")
public class ElecPayHistory{

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
		String sql = "";
		sql+="SELECT ";
		sql+="	  SERIAL_NUM,CITY 地市,AREA 区县,SITE_CODE 站址编码,SITE_NAME 站址名称,ELECTRI_FEE 电费所属期间,TOTAL_ELECTRI 总电量,MONEY 缴费金额,";
		sql+="	  PLAYDATE_START 购电开始日期,PLAYDATE_END 购电截止日期,ELECTRI_START 用电起始度数,ELECTRI_END 用电终止度数,PAY_NUMBER 缴费户号,";
		sql+="	  AMMETER_CODE 电表编码,AMMETER_TYPE 电表类型,POWER_LOSS 电损,OTHER_FEE 其他费用,UNIT_PRICE 单价,ROWNUM AS RN ";
		sql+="FROM ";
		sql+="	  RMW."+conditonMap.get("MODEL_NAME").toString()+" ";
		sql+="WHERE ";
		sql+="	  1 = 1 ";
		if(conditonMap.containsKey("SITE_NAME") && !"".equals(conditonMap.get("SITE_NAME").toString())){
			sql+=" AND SITE_NAME LIKE '%"+conditonMap.get("SITE_NAME").toString()+"%'";
		}
		if(!loginUserUtil.isProvince(request)){
			sql+=" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
		}
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+sql+")");
		List<Map<String,Object>> datas = jdbcTemplate.queryForList("SELECT W.* FROM ("+sql+" AND ROWNUM <= "+(displayStart+iDisplayLength)+") W WHERE W.RN > "+displayStart);
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
		@RequestParam String modelName,
		@RequestParam String keyWord,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		try{
			keyWord = java.net.URLDecoder.decode(keyWord,"UTF-8");
			String sql = "";
			sql+="SELECT ";
			sql+="	  SERIAL_NUM,CITY 地市,AREA 区县,SITE_CODE 站址编码,SITE_NAME 站址名称,ELECTRI_FEE 电费所属期间,TOTAL_ELECTRI 总电量,MONEY 缴费金额,";
			sql+="	  PLAYDATE_START 购电开始日期,PLAYDATE_END 购电截止日期,ELECTRI_START 用电起始度数,ELECTRI_END 用电终止度数,PAY_NUMBER 缴费户号,";
			sql+="	  AMMETER_CODE 电表编码,AMMETER_TYPE 电表类型,POWER_LOSS 电损,OTHER_FEE 其他费用,UNIT_PRICE 单价,ROWNUM AS RN ";
			sql+="FROM ";
			sql+="	  RMW."+modelName+" ";
			sql+="WHERE ";
			sql+="	  1 = 1 ";
			if(keyWord!=null && !"".equals(keyWord)){
				sql+=" AND SITE_NAME LIKE '%"+keyWord+"%'";
			}
			if(!loginUserUtil.isProvince(request)){
				sql+=" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
			}
			List<Map<String,Object>> objs = jdbcTemplate.queryForList(sql);
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
	        for(int r=1;r<=17;r++){
				XSSFCell cell = tableHead.createCell(r);
				cell.setCellStyle(centerGray);
			}
	        xssfSheet.addMergedRegion(new CellRangeAddress(1,1,1,17));
	        xssfSheet.getRow(1).getCell(1).setCellValue("电费支付台账数据导出");
	        /*字段列表*/
	        XSSFRow columnHead = xssfSheet.createRow(2);
	        columnHead.setHeight(new Integer(430).shortValue());
	        for(int r=1;r<=17;r++){
	        	XSSFCell hCell = columnHead.createCell(r);
	        	hCell.setCellStyle(leftWhites);
	        	if(r==1){
	        		hCell.setCellValue("地市");
	        	}else if(r==2){
	        		hCell.setCellValue("区县");
	        	}else if(r==3){
	        		hCell.setCellValue("站址编码");
	        	}else if(r==4){
	        		hCell.setCellValue("站址名称");
	        	}else if(r==5){
	        		hCell.setCellValue("电费所属期间");
	        	}else if(r==6){
	        		hCell.setCellValue("总电量");
	        	}else if(r==7){
	        		hCell.setCellValue("缴费金额（元）");
	        	}else if(r==8){
	        		hCell.setCellValue("购电开始日期");
	        	}else if(r==9){
	        		hCell.setCellValue("购电截止日期");
	        	}else if(r==10){
	        		hCell.setCellValue("用电起始度数");
	        	}else if(r==11){
	        		hCell.setCellValue("用电终止度数");
	        	}else if(r==12){
	        		hCell.setCellValue("缴费户号");
	        	}else if(r==13){
	        		hCell.setCellValue("电表编码");
	        	}else if(r==14){
	        		hCell.setCellValue("电表类型");
	        	}else if(r==15){
	        		hCell.setCellValue("电损");
	        	}else if(r==16){
	        		hCell.setCellValue("其他费用（元）");
	        	}else if(r==17){
	        		hCell.setCellValue("单价（元）");
	        	}
	        	xssfSheet.setColumnWidth(r,5000);
			}
	        /*渲染具体的数据*/
	        Integer currentRowIndex = 3;
	        for(int v=0;v<objs.size();v++){
	        	Map<String,Object> dataMap = objs.get(v);
	        	XSSFRow dataRow = xssfSheet.createRow(currentRowIndex);
				dataRow.setHeight(new Integer(430).shortValue());
				for(int r=1;r<=17;r++){
		        	XSSFCell dCell = dataRow.createCell(r);
		        	dCell.setCellStyle(leftWhites);
		        	if(r==1){
		        		dCell.setCellValue(dataMap.get("地市")==null?"":dataMap.get("地市").toString());
		        	}else if(r==2){
		        		dCell.setCellValue(dataMap.get("区县")==null?"":dataMap.get("区县").toString());
		        	}else if(r==3){
		        		dCell.setCellValue(dataMap.get("站址编码")==null?"":dataMap.get("站址编码").toString());
		        	}else if(r==4){
		        		dCell.setCellValue(dataMap.get("站址名称")==null?"":dataMap.get("站址名称").toString());
		        	}else if(r==5){
		        		dCell.setCellValue(dataMap.get("电费所属期间")==null?"":dataMap.get("电费所属期间").toString());
		        	}else if(r==6){
		        		dCell.setCellValue(dataMap.get("总电量")==null?"":dataMap.get("总电量").toString());
		        	}else if(r==7){
		        		dCell.setCellValue(dataMap.get("缴费金额")==null?"":dataMap.get("缴费金额").toString());
		        	}else if(r==8){
		        		dCell.setCellValue(dataMap.get("购电开始日期")==null?"":dataMap.get("购电开始日期").toString());
		        	}else if(r==9){
		        		dCell.setCellValue(dataMap.get("购电截止日期")==null?"":dataMap.get("购电截止日期").toString());
		        	}else if(r==10){
		        		dCell.setCellValue(dataMap.get("用电起始度数")==null?"":dataMap.get("用电起始度数").toString());
		        	}else if(r==11){
		        		dCell.setCellValue(dataMap.get("用电终止度数")==null?"":dataMap.get("用电终止度数").toString());
		        	}else if(r==12){
		        		dCell.setCellValue(dataMap.get("缴费户号")==null?"":dataMap.get("缴费户号").toString());
		        	}else if(r==13){
		        		dCell.setCellValue(dataMap.get("电表编码")==null?"":dataMap.get("电表编码").toString());
		        	}else if(r==14){
		        		dCell.setCellValue(dataMap.get("电表类型")==null?"":dataMap.get("电表类型").toString());
		        	}else if(r==15){
		        		dCell.setCellValue(dataMap.get("电损")==null?"":dataMap.get("电损").toString());
		        	}else if(r==16){
		        		dCell.setCellValue(dataMap.get("其他费用")==null?"":dataMap.get("其他费用").toString());
		        	}else if(r==17){
		        		dCell.setCellValue(dataMap.get("单价")==null?"":dataMap.get("单价").toString());
		        	}
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
	 * 	查询站址列表
	 * 
	 * */
	@RequestMapping("/findSiteInfos.ilf")
	public void findSiteInfos(
		@RequestParam String tableparam,
		@RequestParam String conditions,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
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
		String sql = "SELECT CITY,REGION_ID,SITE_CODE,SITE_NAME,ROWNUM AS RN FROM RMW.ZG_SITE WHERE 1 = 1 ";
		if(conditonMap.containsKey("SITE_NAME") && !"".equals(conditonMap.get("SITE_NAME").toString())){
			sql+=" AND SITE_NAME LIKE '%"+conditonMap.get("SITE_NAME").toString()+"%'";
		}
		if(!loginUserUtil.isProvince(request)){
			sql+=" AND CITY LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
		}
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+sql+")");
		List<Map<String,Object>> datas = jdbcTemplate.queryForList("SELECT W.* FROM ("+sql+" AND ROWNUM <= "+(displayStart+iDisplayLength)+") W WHERE W.RN > "+displayStart);
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(datas);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	保存/修改
	 * 
	 * */
	@RequestMapping("/saveAudit.ilf")
	public void saveAudit(
		@RequestParam String params,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true,message:'保存成功.'}");
		try{
			final JSONObject jsonObject = JSONObject.fromObject(params);
			String sql = "";
			sql+="INSERT INTO RMW."+jsonObject.getString("SUPPLY_TYPE")+"(";
			sql+="	  SERIAL_NUM,CITY,AREA,SITE_CODE,SITE_NAME,ELECTRI_FEE,TOTAL_ELECTRI,MONEY,PLAYDATE_START,PLAYDATE_END,ELECTRI_START,ELECTRI_END,PAY_NUMBER,AMMETER_CODE,AMMETER_TYPE,POWER_LOSS,OTHER_FEE,UNIT_PRICE";
			sql+=")VALUES(";
			sql+="RMW.SEQ_WY_ELECTRI_FEE.NEXTVAL,";
			sql+="'"+(jsonObject.get("CITY_NAME")==null?"":jsonObject.getString("CITY_NAME"))+"',";
			sql+="'"+(jsonObject.get("REGION_NAME")==null?"":jsonObject.getString("REGION_NAME"))+"',";
			sql+="'"+(jsonObject.get("SITE_CODE")==null?"":jsonObject.getString("SITE_CODE"))+"',";
			sql+="'"+(jsonObject.get("SITE_NAME")==null?"":jsonObject.getString("SITE_NAME"))+"',";
			sql+="'"+(jsonObject.get("DATE_CIRCLE")==null?"":jsonObject.getString("DATE_CIRCLE"))+"',";
			sql+=Double.parseDouble((jsonObject.get("TOTAL")==null?"0":jsonObject.getString("TOTAL")))+",";
			sql+=Double.parseDouble((jsonObject.get("PAYOUT_COUNT")==null?"0":jsonObject.getString("PAYOUT_COUNT")))+",";
			sql+="'"+(jsonObject.get("BUY_BEGIN_DATE")==null?"":jsonObject.getString("BUY_BEGIN_DATE"))+"',";
			sql+="'"+(jsonObject.get("BUY_LIMIT_DATE")==null?"":jsonObject.getString("BUY_LIMIT_DATE"))+"',";
			sql+=Double.parseDouble((jsonObject.get("BUY_BEGIN_COUNT")==null?"0":jsonObject.getString("BUY_BEGIN_COUNT")))+",";
			sql+=Double.parseDouble((jsonObject.get("BUY_LIMIT_COUNT")==null?"0":jsonObject.getString("BUY_LIMIT_COUNT")))+",";
			sql+="'"+(jsonObject.get("USER_ACCOUNT")==null?"":jsonObject.getString("USER_ACCOUNT"))+"',";
			sql+="'"+(jsonObject.get("AMETER_CODE")==null?"":jsonObject.getString("AMETER_CODE"))+"',";
			sql+="'"+(jsonObject.get("AMETER_TYPE")==null?"":jsonObject.getString("AMETER_TYPE"))+"',";
			sql+=Double.parseDouble((jsonObject.get("ELEC_LOST")==null?"0":jsonObject.getString("ELEC_LOST")))+",";
			sql+=Double.parseDouble((jsonObject.get("OTHER_COST")==null?"0":jsonObject.getString("OTHER_COST")))+",";
			sql+=Double.parseDouble((jsonObject.get("ELEC_PRICE")==null?"0":jsonObject.getString("ELEC_PRICE")));
			sql+=")";
			jdbcTemplate.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
			resultObject.put("message","系统异常.");
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}	
	}
}
