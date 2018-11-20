package com.function.index.plugin;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.usermodel.HSSFFont;
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
import org.apache.poi.xssf.usermodel.XSSFFont;
import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.index.plugin.DealExpireAlarm")
@RequestMapping(value="/dealExpireAlarm")
public class DealExpireAlarm{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	合同续签提醒
	 * 
	 * */
	@RequestMapping("/findDatas.ilf")
	public void findDatas(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		String userName = "RMW";
		try{
			Boolean IS_PROVINCE = false;
			String CITY_NAME = "";
			Object loginObject = request.getSession().getAttribute("LoginUserInfo");
			if(loginObject!=null){
				Map<String,Object> loginUser = (HashMap<String,Object>)loginObject;
				if(loginUser.get("BELONG_AREA").toString().indexOf("省")!=-1){
					IS_PROVINCE = true;
				}else{
					IS_PROVINCE = false;
					CITY_NAME = loginUser.get("BELONG_AREA").toString();
				}
				if(CITY_NAME.length()>2){
					CITY_NAME = CITY_NAME.substring(0,2);
				}
			}
			String sql = "";
			if(IS_PROVINCE){
				sql+="SELECT ";
				sql+="	  AA.CITY AS 地区,NVL(AA.HTZS, 0) AS 站址数,";
				sql+="	  NVL(BB.YYDQZS, 0) AS 六个月内的到期合同数,";
				sql+="	  NVL(CC.GQZS, 0) AS 已过期合同数,NVL(ROUND((BB.YYDQZS + CC.GQZS) / AA.HTZS, 2), 0) * 100 || '%' AS 六月内已到期及过期合同占比,";
				sql+="	  NVL(DD.zero_count, 0) AS 零场租站数,NVL(EE.no_contract, 0) AS 无合同站数   ";
				sql+="FROM ";
				sql+="	  (SELECT COUNT(*) AS HTZS,CITY FROM RMW.ZG_SITE WHERE IS_VALID = '是'AND STATEFLAG = '0' GROUP BY CITY) AA,";
				sql+="	  (SELECT COUNT(*) YYDQZS, CITY FROM RMW.WY_CONTRACT_INFO WHERE IS_COMPLETE != 1 AND CONTRACT_STATUS = '有效' AND (TRUNC(TO_DATE(TERMINATION, 'YYYY/MM/DD')) - TRUNC(SYSDATE)) > 0 AND (TRUNC(TO_DATE(TERMINATION,'YYYY/MM/DD')) - TRUNC(SYSDATE)) < 180 GROUP BY CITY) BB,";
				sql+="	  (SELECT COUNT(*) AS GQZS, CITY FROM RMW.WY_CONTRACT_INFO WHERE IS_COMPLETE != 1 AND CONTRACT_STATUS = '有效' AND (TRUNC(TO_DATE(TERMINATION, 'YYYY/MM/DD')) - TRUNC(SYSDATE)) < 0 GROUP BY CITY) CC,";
				sql+="	  (SELECT COUNT(*) AS ZERO_COUNT, CITY FROM (";
				sql+="		  SELECT DISTINCT SITE_CODE,CITY FROM TOWERCRNOP.WY_PROPERTY_INFO WHERE IS_EFFECTIVE = '是' AND ZERO_FIELD_REASON IS NOT NULL AND SITE_CODE NOT IN(";
				sql+="			  SELECT DISTINCT SITE_CODE FROM TOWERCRNOP.WY_PROPERTY_INFO WHERE IS_EFFECTIVE = '是' AND ZERO_FIELD_REASON IS NULL";
				sql+="		  )";
				sql+="	  )GROUP BY CITY) DD,";
				sql+="	  (SELECT CITY, COUNT(*) NO_CONTRACT FROM RMW.ZG_SITE T WHERE T.STATEFLAG = '0' AND T.SITE_CODE NOT IN(";
				sql+="		  SELECT DISTINCT SITE_CODE FROM TOWERCRNOP.WY_PROPERTY_INFO WHERE IS_EFFECTIVE = '是' AND ZERO_FIELD_REASON IS NOT NULL";
				sql+="	  )AND T.SITE_CODE NOT IN(";
				sql+="		  SELECT DISTINCT A.SITE_CODE FROM RMW.WY_CONTRACT_INFO A WHERE A.IS_COMPLETE != 1 AND A.CONTRACT_STATUS = '有效' AND A.TYPE_OF_CONTRACT IN ('租赁', '电租一体')";
				sql+="	  )GROUP BY CITY) EE ";
				sql+="WHERE AA.CITY = BB.CITY(+) AND AA.CITY = CC.CITY(+) AND AA.CITY = DD.CITY AND AA.CITY = EE.CITY ";
				List<Map<String,Object>> proviceView = jdbcTemplate.queryForList(sql);
				resultObject.put("DATAS",proviceView);
			}else{
				sql+="SELECT ";
				sql+="	  AA.CITY AS 地区,NVL(AA.HTZS, 0) AS 站址数,";
				sql+="	  NVL(BB.YYDQZS, 0) AS 六个月内的到期合同数,";
				sql+="	  NVL(CC.GQZS, 0) AS 已过期合同数,NVL(ROUND((BB.YYDQZS + CC.GQZS) / AA.HTZS, 2), 0) * 100 || '%' AS 六月内已到期及过期合同占比,";
				sql+="	  NVL(DD.zero_count, 0) AS 零场租站数,NVL(EE.no_contract, 0) AS 无合同站数   ";
				sql+="FROM ";
				sql+="	  (SELECT COUNT(*) AS HTZS,CITY FROM RMW.ZG_SITE WHERE IS_VALID = '是'AND STATEFLAG = '0' GROUP BY CITY) AA,";
				sql+="	  (SELECT COUNT(*) YYDQZS, CITY FROM RMW.WY_CONTRACT_INFO WHERE IS_COMPLETE != 1 AND CONTRACT_STATUS = '有效' AND (TRUNC(TO_DATE(TERMINATION, 'YYYY/MM/DD')) - TRUNC(SYSDATE)) > 0 AND (TRUNC(TO_DATE(TERMINATION,'YYYY/MM/DD')) - TRUNC(SYSDATE)) < 180 GROUP BY CITY) BB,";
				sql+="	  (SELECT COUNT(*) AS GQZS, CITY FROM RMW.WY_CONTRACT_INFO WHERE IS_COMPLETE != 1 AND CONTRACT_STATUS = '有效' AND (TRUNC(TO_DATE(TERMINATION, 'YYYY/MM/DD')) - TRUNC(SYSDATE)) < 0 GROUP BY CITY) CC,";
				sql+="	  (SELECT COUNT(*) AS ZERO_COUNT, CITY FROM (";
				sql+="		  SELECT DISTINCT SITE_CODE,CITY FROM TOWERCRNOP.WY_PROPERTY_INFO WHERE IS_EFFECTIVE = '是' AND ZERO_FIELD_REASON IS NOT NULL AND SITE_CODE NOT IN(";
				sql+="			  SELECT DISTINCT SITE_CODE FROM TOWERCRNOP.WY_PROPERTY_INFO WHERE IS_EFFECTIVE = '是' AND ZERO_FIELD_REASON IS NULL";
				sql+="		  )";
				sql+="	  )GROUP BY CITY) DD,";
				sql+="	  (SELECT CITY, COUNT(*) NO_CONTRACT FROM RMW.ZG_SITE T WHERE T.STATEFLAG = '0' AND T.SITE_CODE NOT IN(";
				sql+="		  SELECT DISTINCT SITE_CODE FROM TOWERCRNOP.WY_PROPERTY_INFO WHERE IS_EFFECTIVE = '是' AND ZERO_FIELD_REASON IS NOT NULL";
				sql+="	  )AND T.SITE_CODE NOT IN(";
				sql+="		  SELECT DISTINCT A.SITE_CODE FROM RMW.WY_CONTRACT_INFO A WHERE A.IS_COMPLETE != 1 AND A.CONTRACT_STATUS = '有效' AND A.TYPE_OF_CONTRACT IN ('租赁', '电租一体')";
				sql+="	  )GROUP BY CITY) EE ";
				sql+="WHERE AA.CITY LIKE '%"+CITY_NAME+"%' AND AA.CITY = BB.CITY(+) AND AA.CITY = CC.CITY(+) AND AA.CITY = DD.CITY AND AA.CITY = EE.CITY ";
				List<Map<String,Object>> proviceView = jdbcTemplate.queryForList(sql);
				resultObject.put("DATAS",proviceView);
			}
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	在途工单：合同续签提醒
	 * 
	 * */
	@RequestMapping("/findDoingJobs.ilf")
	public void findDoingJobs(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			String siteCode = request.getParameter("siteCode");
			String sql = "";
			sql+="SELECT ";
			sql+="	 A.INT_ID,C.FLOW_NO 工单编号,C.FLOW_TITLE 工单名称,B.SITE_CODE 站址编码,B.SITE_NAME 站址名称,B.CITY 地市,B.AREA 区县,";
			sql+="	 B.CONTRACT_CODE 合同编码,B.CONTRACT_NAME 合同名称,B.TYPE_OF_CONTRACT 合同类型,B.TIME_OF_SIGNING_OF_CONTRACT 合同签订日期,B.CONTRACT_START_TIME 合同起始日期,";
			sql+="	 B.TERMINATION 合同终止日期,B.TOTAL_CONTRACT_AMOUNT 合同总金额,B.CONTRACT_STATUS 合同状态,A.NEW_HTCODE 续签新合同编号,(CASE A.IS_PAY WHEN 1 THEN '已续签' ELSE '未续签' END) 续签状态  ";
			sql+="FROM NEWIRMS.DFPAY_TASK_INFO A ";
			sql+="	 INNER JOIN RMW.WY_CONTRACT_INFO B ON B.INT_ID = A.HT_ID";
			sql+="	 INNER JOIN NEWIRMS.T_BPM_FORM_INFO C ON C.FLOW_ID = A.FLOW_ID ";
			sql+="WHERE A.FLOW_ID IN（";
			sql+="	 SELECT FLOW_ID FROM NEWIRMS.T_BPM_FORM_INFO WHERE CURRENT_STATE = 2";
			sql+=") AND A.TYPE_FLAG = 'HT'";
			if(siteCode!=null && !"".equals(siteCode)){
				sql+=" AND B.SITE_CODE LIKE '%"+siteCode+"%'";
			}
			Boolean IS_PROVINCE = loginUserUtil.isProvince(request);
			if(!IS_PROVINCE){
				String CITY_NAME = loginUserUtil.getBelongArea(request);
				sql = "SELECT W.* FROM ("+sql+") W WHERE W.工单名称   LIKE '%"+CITY_NAME+"%'";
			}
			List<Map<String,Object>> proviceView = jdbcTemplate.queryForList(sql);
			resultObject.put("DATAS",proviceView);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	数据下载.
	 * 
	 * */
	@RequestMapping("/exportDetails.ilf")
	public void exportDetails(
		@RequestParam String cityName,
		@RequestParam String siteCode,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		try{
			cityName = java.net.URLDecoder.decode(cityName,"UTF-8");
			siteCode = java.net.URLDecoder.decode(siteCode,"UTF-8");
			String sql = "";
			sql+="SELECT ZH_LABEL,CITY 地市,AREA 区县,SITE_CODE 站址编码,SITE_NAME 站址名称,CONTRACT_CODE 合同编码,CONTRACT_NAME 合同名称,TYPE_OF_CONTRACT 合同类型,TOTAL_CONTRACT_AMOUNT 合同金额,CONTRACT_START_TIME 开始日期,TERMINATION 结束日期   "; 
			sql+="FROM RMW.WY_CONTRACT_INFO ";
			sql+="WHERE CITY = '"+cityName+"' AND CONTRACT_STATUS='有效' AND (TRUNC(TO_DATE(TERMINATION,'YYYY/MM/DD'))-TRUNC(SYSDATE))<90 ";
			if(siteCode!=null && !"".equals(siteCode)){
				sql+=" AND SITE_CODE LIKE '%"+siteCode+"%'";
			}
			sql+=" ORDER BY (TRUNC(TO_DATE(TERMINATION,'YYYY/MM/DD'))-TRUNC(SYSDATE))";
			List<Map<String,Object>> proviceViews = jdbcTemplate.queryForList(sql);
			/*
			 * 	写文件.
			 * 
			 * */
			XSSFWorkbook wk = new XSSFWorkbook();
	        XSSFFont font = wk.createFont();
	        font.setFontName("宋体");
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	        font.setFontHeightInPoints((short)10);
	        font.setColor((short)16711680);       
	        XSSFCellStyle style = wk.createCellStyle();
	        style.setFont(font);
	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);       
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);              
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);           
	        style.setFillForegroundColor(HSSFColor.WHITE.index);
	        XSSFSheet sheet = wk.createSheet();
	        for(int i=0;i<=10;i++){
	        	if(i==0){
	        		sheet.setColumnWidth(i,800);
	        	}else if(i==4 || i==6){
	        		sheet.setColumnWidth(i,15000);
	        	}else{
	        		sheet.setColumnWidth(i,7000);
	        	}
	        }
	        /*ROW-0:Title*/
	        XSSFRow row1 = sheet.createRow(1);            
	        row1.setHeight(new Integer(700).shortValue());
	        for(int i=1;i<=10;i++){
	        	XSSFCell cell1i = row1.createCell(i);
	        	cell1i.setCellStyle(style);
	        }
	        XSSFCell cell11 = row1.getCell(1);
	        sheet.addMergedRegion(new CellRangeAddress(1,1,1,10));            
	        cell11.setCellValue("场租续签明细 ");        
	        /*ROW-1:Column*/
	        XSSFRow row2 = sheet.createRow(2);            
	        row2.setHeight(new Integer(500).shortValue());
	        for(int i=1;i<=10;i++){
	        	XSSFCell cell2i = row2.createCell(i);
	        	cell2i.setCellStyle(style);
	        	if(i==1){
	        		cell2i.setCellValue("地市");
	        	}else if(i==2){
	        		cell2i.setCellValue("区县");
	        	}else if(i==3){
	        		cell2i.setCellValue("站址编码");
	        	}else if(i==4){
	        		cell2i.setCellValue("站址名称");
	        	}else if(i==5){
	        		cell2i.setCellValue("合同编码");
	        	}else if(i==6){
	        		cell2i.setCellValue("合同名称");
	        	}else if(i==7){
	        		cell2i.setCellValue("合同类型");
	        	}else if(i==8){
	        		cell2i.setCellValue("合同金额");
	        	}else if(i==9){
	        		cell2i.setCellValue("开始日期");
	        	}else if(i==10){
	        		cell2i.setCellValue("到期日期");
	        	}
	        }
	        /*ROW-2:开始写数据*/
	        Integer editingRow = 3;
	        if(proviceViews.size()>0){
	        	for(int i=0;i<proviceViews.size();i++){
	        		Map<String,Object> view = proviceViews.get(i);
	        		XSSFRow editRow = sheet.createRow(editingRow); 
	        		editRow.setHeight(new Integer(500).shortValue());
	        		for(int j=1;j<=10;j++){
	    	        	XSSFCell editCell = editRow.createCell(j);
	    	        	editCell.setCellStyle(style);
	    	        	if(j==1){
	    	        		editCell.setCellValue(view.get("地市")==null?"-":view.get("地市").toString());
	    	        	}else if(j==2){
	    	        		editCell.setCellValue(view.get("区县")==null?"-":view.get("区县").toString());
	    	        	}else if(j==3){
	    	        		editCell.setCellValue(view.get("站址编码")==null?"-":view.get("站址编码").toString());
	    	        	}else if(j==4){
	    	        		editCell.setCellValue(view.get("站址名称")==null?"-":view.get("站址名称").toString());
	    	        	}else if(j==5){
	    	        		editCell.setCellValue(view.get("合同编码")==null?"-":view.get("合同编码").toString());
	    	        	}else if(j==6){
	    	        		editCell.setCellValue(view.get("合同名称")==null?"-":view.get("合同名称").toString());
	    	        	}else if(j==7){
	    	        		editCell.setCellValue(view.get("合同类型")==null?"-":view.get("合同类型").toString());
	    	        	}else if(j==8){
	    	        		editCell.setCellValue(view.get("合同金额")==null?"-":view.get("合同金额").toString());
	    	        	}else if(j==9){
	    	        		editCell.setCellValue(view.get("开始日期")==null?"-":view.get("开始日期").toString());
	    	        	}else if(j==10){
	    	        		editCell.setCellValue(view.get("结束日期")==null?"-":view.get("结束日期").toString());
	    	        	}
	    	        }
	        		editingRow++;
	        	}
	        }
			String fileName = new Date().getTime()+".xlsx";
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+fileName)
			);
			wk.write(fileOutputStream);
			fileOutputStream.close();
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
	 * 	工单数据下载.
	 * 
	 * */
	@RequestMapping("/exportData.ilf")
	public void exportData(HttpServletRequest request,HttpServletResponse response)throws Exception{
		try{
			String siteCode = request.getParameter("siteCode");
			String sql = "";
			sql+="SELECT ";
			sql+="	 A.INT_ID,C.FLOW_NO 工单编号,C.FLOW_TITLE 工单名称,B.SITE_CODE 站址编码,B.SITE_NAME 站址名称,B.CITY 地市,B.AREA 区县,";
			sql+="	 B.CONTRACT_CODE 合同编码,B.CONTRACT_NAME 合同名称,B.TYPE_OF_CONTRACT 合同类型,B.TIME_OF_SIGNING_OF_CONTRACT 合同签订日期,B.CONTRACT_START_TIME 合同起始日期,";
			sql+="	 B.TERMINATION 合同终止日期,B.TOTAL_CONTRACT_AMOUNT 合同总金额,B.CONTRACT_STATUS 合同状态,A.NEW_HTCODE 续签新合同编号,(CASE A.IS_PAY WHEN 1 THEN '已续签' ELSE '未续签' END) 续签状态  ";
			sql+="FROM NEWIRMS.DFPAY_TASK_INFO A ";
			sql+="	 INNER JOIN RMW.WY_CONTRACT_INFO B ON B.INT_ID = A.HT_ID";
			sql+="	 INNER JOIN NEWIRMS.T_BPM_FORM_INFO C ON C.FLOW_ID = A.FLOW_ID ";
			sql+="WHERE A.FLOW_ID IN（";
			sql+="	 SELECT FLOW_ID FROM NEWIRMS.T_BPM_FORM_INFO WHERE CURRENT_STATE = 2";
			sql+=") AND A.TYPE_FLAG = 'HT'";
			if(siteCode!=null && !"".equals(siteCode)){
				sql+=" AND B.SITE_CODE LIKE '%"+siteCode+"%'";
			}
			Boolean IS_PROVINCE = loginUserUtil.isProvince(request);
			if(!IS_PROVINCE){
				String CITY_NAME = loginUserUtil.getBelongArea(request);
				sql = "SELECT W.* FROM ("+sql+") W WHERE W.工单名称   LIKE '%"+CITY_NAME+"%'";
			}
			List<Map<String,Object>> proviceViews = jdbcTemplate.queryForList(sql);
			/*
			 * 	写文件.
			 * 
			 * */
			XSSFWorkbook wk = new XSSFWorkbook();
	        XSSFFont font = wk.createFont();
	        font.setFontName("宋体");
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	        font.setFontHeightInPoints((short)10);
	        font.setColor((short)16711680);       
	        XSSFCellStyle style = wk.createCellStyle();
	        style.setFont(font);
	        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
	        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);       
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);              
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);           
	        style.setFillForegroundColor(HSSFColor.WHITE.index);
	        XSSFSheet sheet = wk.createSheet();
	        for(int i=0;i<=15;i++){
	        	if(i==0){
	        		sheet.setColumnWidth(i,800);
	        	}else if(i==4 || i==6 || i==8){
	        		sheet.setColumnWidth(i,15000);
	        	}else{
	        		sheet.setColumnWidth(i,7000);
	        	}
	        }
	        /*ROW-0:Title*/
	        XSSFRow row1 = sheet.createRow(1);            
	        row1.setHeight(new Integer(700).shortValue());
	        for(int i=1;i<=15;i++){
	        	XSSFCell cell1i = row1.createCell(i);
	        	cell1i.setCellStyle(style);
	        }
	        XSSFCell cell11 = row1.getCell(1);
	        sheet.addMergedRegion(new CellRangeAddress(1,1,1,15));            
	        cell11.setCellValue("在途工单：场租续签提醒 ");        
	        /*ROW-1:Column*/
	        XSSFRow row2 = sheet.createRow(2);            
	        row2.setHeight(new Integer(500).shortValue());
	        for(int i=1;i<=15;i++){
	        	XSSFCell cell2i = row2.createCell(i);
	        	cell2i.setCellStyle(style);
	        	if(i==1){
	        		cell2i.setCellValue("地市");
	        	}else if(i==2){
	        		cell2i.setCellValue("区县");
	        	}else if(i==3){
	        		cell2i.setCellValue("工单编号");
	        	}else if(i==4){
	        		cell2i.setCellValue("工单名称");
	        	}else if(i==5){
	        		cell2i.setCellValue("站址编码");
	        	}else if(i==6){
	        		cell2i.setCellValue("站址名称");
	        	}else if(i==7){
	        		cell2i.setCellValue("合同编码");
	        	}else if(i==8){
	        		cell2i.setCellValue("合同名称");
	        	}else if(i==9){
	        		cell2i.setCellValue("合同类型");
	        	}else if(i==10){
	        		cell2i.setCellValue("合同签订日期");
	        	}else if(i==11){
	        		cell2i.setCellValue("合同起始日期");
	        	}else if(i==12){
	        		cell2i.setCellValue("合同终止日期");
	        	}else if(i==13){
	        		cell2i.setCellValue("合同金额");
	        	}else if(i==14){
	        		cell2i.setCellValue("合同状态");
	        	}else if(i==15){
	        		cell2i.setCellValue("续签状态");
	        	}
	        }
	        /*ROW-2:开始写数据*/
	        Integer editingRow = 3;
	        if(proviceViews.size()>0){
	        	for(int i=0;i<proviceViews.size();i++){
	        		Map<String,Object> view = proviceViews.get(i);
	        		XSSFRow editRow = sheet.createRow(editingRow); 
	        		editRow.setHeight(new Integer(500).shortValue());
	        		for(int j=1;j<=15;j++){
	    	        	XSSFCell editCell = editRow.createCell(j);
	    	        	editCell.setCellStyle(style);
	    	        	if(j==1){
	    	        		editCell.setCellValue(view.get("地市")==null?"-":view.get("地市").toString());
	    	        	}else if(j==2){
	    	        		editCell.setCellValue(view.get("区县")==null?"-":view.get("区县").toString());
	    	        	}else if(j==3){
	    	        		editCell.setCellValue(view.get("工单编号")==null?"-":view.get("工单编号").toString());
	    	        	}else if(j==4){
	    	        		editCell.setCellValue(view.get("工单名称")==null?"-":view.get("工单名称").toString());
	    	        	}else if(j==5){
	    	        		editCell.setCellValue(view.get("站址编码")==null?"-":view.get("站址编码").toString());
	    	        	}else if(j==6){
	    	        		editCell.setCellValue(view.get("站址名称")==null?"-":view.get("站址名称").toString());
	    	        	}else if(j==7){
	    	        		editCell.setCellValue(view.get("合同编码")==null?"-":view.get("合同编码").toString());
	    	        	}else if(j==8){
	    	        		editCell.setCellValue(view.get("合同名称")==null?"-":view.get("合同名称").toString());
	    	        	}else if(j==9){
	    	        		editCell.setCellValue(view.get("合同类型")==null?"-":view.get("合同类型").toString());
	    	        	}else if(j==10){
	    	        		editCell.setCellValue(view.get("合同签订日期")==null?"-":view.get("合同签订日期").toString());
	    	        	}else if(j==11){
	    	        		editCell.setCellValue(view.get("合同起始日期")==null?"-":view.get("合同起始日期").toString());
	    	        	}else if(j==12){
	    	        		editCell.setCellValue(view.get("合同终止日期")==null?"-":view.get("合同终止日期").toString());
	    	        	}else if(j==13){
	    	        		editCell.setCellValue(view.get("合同总金额")==null?"-":view.get("合同总金额").toString());
	    	        	}else if(j==14){
	    	        		editCell.setCellValue(view.get("合同状态")==null?"-":view.get("合同状态").toString());
	    	        	}else if(j==15){
	    	        		editCell.setCellValue(view.get("续签状态")==null?"-":view.get("续签状态").toString());
	    	        	}
	    	        }
	        		editingRow++;
	        	}
	        }
			String fileName = new Date().getTime()+".xlsx";
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+fileName)
			);
			wk.write(fileOutputStream);
			fileOutputStream.close();
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
	 * 	合同续签提醒
	 * 
	 * */
	@RequestMapping("/findDetails.ilf")
	public void findDetails(
		@RequestParam String CITY_NAME,
		@RequestParam String SITE_CODE,
		@RequestParam String DEAL_TYPE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			String sql = "";
			sql+="SELECT ZH_LABEL,CITY 地市,AREA 区县,SITE_CODE 站址编码,SITE_NAME 站址名称,CONTRACT_CODE 合同编码,CONTRACT_NAME 合同名称,TYPE_OF_CONTRACT 合同类型,TOTAL_CONTRACT_AMOUNT 合同金额,CONTRACT_START_TIME 开始日期,TERMINATION 结束日期   "; 
			sql+="FROM RMW.WY_CONTRACT_INFO ";
			sql+="WHERE CITY LIKE '%"+CITY_NAME+"%' AND IS_COMPLETE != 1 AND CONTRACT_STATUS = '有效' AND (((TRUNC(TO_DATE(TERMINATION, 'YYYY/MM/DD')) - TRUNC(SYSDATE)) > 0 AND (TRUNC(TO_DATE(TERMINATION,'YYYY/MM/DD'))-TRUNC(SYSDATE))<180) OR (TRUNC(TO_DATE(TERMINATION, 'YYYY/MM/DD'))-TRUNC(SYSDATE))<0)";
			if(DEAL_TYPE!=null && !"-".equals(DEAL_TYPE)){
				sql+=" AND CONTRACT_CODE LIKE '%DCHT%'";
			}else{
				sql+=" AND CONTRACT_CODE NOT LIKE '%DCHT%'";
			}
			if(SITE_CODE!=null && !"".equals(SITE_CODE)){
				sql+=" AND SITE_CODE LIKE '%"+SITE_CODE+"%'";
			}
			sql+=" ORDER BY CITY ASC ";
			List<Map<String,Object>> proviceView = jdbcTemplate.queryForList(sql);
			resultObject.put("DATAS",proviceView);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	反馈信息
	 * 
	 * */
	@RequestMapping("/replyMessage.ilf")
	public void replyMessage(@RequestParam String params,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			JSONObject paramJson = JSONObject.fromObject(params);
			String EMPLOYEE_NAME = loginUserUtil.getEmployeeName(request);
			jdbcTemplate.execute("INSERT INTO DEAL_EXPIRE_MESSAGE(CITY_NAME,REPLY_MESSAGE,REPLY_DATE,REPLY_USER) VALUES('"+paramJson.getString("CONTRACT_CODE")+"','"+paramJson.getString("REPLY_MESSAGE")+"',SYSDATE,'"+EMPLOYEE_NAME+"')");
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	查询已反馈信息
	 * 
	 * */
	@RequestMapping("/haveReplyMessages.ilf")
	public void haveReplyMessages(@RequestParam String params,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			JSONObject paramJson = JSONObject.fromObject(params);
			List<Map<String,Object>> replyMessages = jdbcTemplate.queryForList("SELECT * FROM DEAL_EXPIRE_MESSAGE WHERE CITY_NAME = '"+paramJson.getString("CONTRACT_CODE")+"' ORDER BY REPLY_DATE DESC");
			resultObject.put("MESSAGES",replyMessages);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	查询已反馈信息：在途工单
	 * 
	 * */
	@RequestMapping("/jobHaveReplyMessages.ilf")
	public void jobHaveReplyMessages(@RequestParam String jobCode,HttpServletRequest request,HttpServletResponse response)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			String sql = "";
			sql+="SELECT C.USER_NAME 反馈人,C.REMARK 详情,C.TIME_STAMP 反馈时间,C.COMPANY_ID 所属地市ID,C.USER_ID 用户ID,C.TASK_ID ";
			sql+="FROM NEWIRMS.DFPAY_HIS_CAUSE C ";
			sql+="WHERE C.TASK_ID = '"+jobCode+"' ORDER BY TIME_STAMP DESC";
			resultObject.put("MESSAGES",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	分页查询
	 * 
	 * */
	@RequestMapping("/findContractsMaybe.ilf")
	public void findContractsMaybe(
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
		String basicSql = "";
		basicSql+="SELECT SITE_CODE,SITE_NAME,CONTRACT_CODE,CONTRACT_STATUS,CONTRACT_START_TIME ";
		basicSql+="FROM RMW.WY_CONTRACT_INFO ";
		basicSql+="WHERE ";
		basicSql+="	  SITE_CODE IN (SELECT SITE_CODE FROM RMW.WY_CONTRACT_INFO WHERE ZH_LABEL = '"+conditonMap.get("CONTRACT_ZH_LABEL").toString()+"') AND ";
		basicSql+="   CONTRACT_STATUS = '有效' AND ";
		basicSql+="	  ZH_LABEL NOT IN ('"+conditonMap.get("CONTRACT_ZH_LABEL").toString()+"') AND ";
		basicSql+="   to_date(CONTRACT_START_TIME,'yyyy-MM-dd') > (";
		basicSql+="		  SELECT to_date(CONTRACT_START_TIME,'yyyy-MM-dd') ";
		basicSql+="		  FROM RMW.WY_CONTRACT_INFO ";
		basicSql+="		  WHERE ZH_LABEL = '"+conditonMap.get("CONTRACT_ZH_LABEL").toString()+"'";
		basicSql+="	  )";
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+basicSql+") A");
		Integer pageLimit = displayStart+iDisplayLength;
		String pageSql = "";
		pageSql+="SELECT B.* FROM (";
		pageSql+="	  SELECT A.*,ROWNUM AS RN FROM("+basicSql+") A WHERE ROWNUM <= "+pageLimit;
		pageSql+=") B WHERE B.RN > "+displayStart;
		pageSql+="";
		List<Map<String,Object>> objectList = jdbcTemplate.queryForList(pageSql);
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(objectList);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	保存续签关系
	 * 
	 * */
	@RequestMapping("/saveContractConnect.ilf")
	public void saveContractConnect(
		@RequestParam String ZH_LABEL,
		@RequestParam String NEW_CONTRACT_CODE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "UPDATE RMW.WY_CONTRACT_INFO SET IS_COMPLETE = 1,CONTRACT_CODE_NEW = '"+NEW_CONTRACT_CODE+"' WHERE ZH_LABEL = '"+ZH_LABEL+"'";
			jdbcTemplate.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	保存续签关系
	 * 
	 * */
	@RequestMapping("/noExtendSingal.ilf")
	public void noExtendSingal(
		@RequestParam String ZH_LABEL,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject resultObject = JSONObject.fromObject("{SUCCESS:true}");
		try{
			String sql = "UPDATE RMW.WY_CONTRACT_INFO SET IS_COMPLETE = 1,WXQ_REMARK = '转改直合同无续签' WHERE ZH_LABEL = '"+ZH_LABEL+"'";
			jdbcTemplate.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("SUCCESS",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
}
