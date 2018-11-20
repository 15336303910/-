package com.function.index.plugin;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.system.LoginUserUtil;
import com.systemConfig.model.DataTableResult;
@Controller("com.function.index.plugin.ClientQuestionCount")
@RequestMapping(value="/clientQuestionCount")
public class ClientQuestionCount{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	客户诉求工单统计
	 * 
	 * */
	@RequestMapping("/findDatas.ilf")
	public void findDatas(
		@RequestParam String CONSUMER_TYPE,
		@RequestParam String REQUEST_TYPE,
		@RequestParam String START_DATE,
		@RequestParam String LIMIT_DATE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		if(START_DATE==null || "".equals(START_DATE) || "-".equals(START_DATE) || "-1".equals(START_DATE)){
			START_DATE = "1970-01-01";
		}
		if(LIMIT_DATE==null || "".equals(LIMIT_DATE) || "-".equals(LIMIT_DATE) || "-1".equals(LIMIT_DATE)){
			LIMIT_DATE = "2099-12-30";
		}
		JSONObject resultObject = JSONObject.fromObject("{success:true}");
		try{
			Boolean IS_PROVINCE = loginUserUtil.isProvince(request);
			String CITY_NAME = loginUserUtil.getBelongArea(request);
			String sql = "";
			sql+="SELECT (";
			sql+="	  CASE WHEN G.COMPANYID = 1 THEN G.NAME ELSE G.ABBRE END) 地市,NVL(A.NUMM,0) 总工单数,NVL(B.NUMM,0) 已接单工单数,NVL(C.NUMM,0) 未接单工单数,NVL(D.NUMM,0) 正常处理工单数,NVL(E.NUMM,0) 超时处理工单数,NVL(F.NUMM,0) 已归档工单数,";
			sql+="	  CASE WHEN NVL(A.NUMM,0)=0 THEN 0 ELSE ROUND(NVL(F.NUMM,0)/A.NUMM,2) END 工单完成率  ";
			sql+="	  FROM( ";
			sql+="		  SELECT CITY_ID, COUNT(1) NUMM FROM (";
			sql+="			  SELECT A.SEND_COMPANYID CITY_ID ";
			sql+="			  FROM NEWIRMS.T_BPM_FORM_INFO A,NEWIRMS.T_CUSTOMER_PROBLEM_INFO B,NEWIRMS.T_BNS_CUSTOMER_PROBLEM C ";
			sql+="			  WHERE A.FLOW_ID = B.FLOWID AND C.FLOW_ID = A.FLOW_ID AND A.FLOW_MODEL = 'com.inspur.app.tieta.customerProblem.process.cusPro' AND (A.SEND_TIME >= TO_DATE('"+START_DATE+"','yyyy-mm-dd') AND A.SEND_TIME <= TO_DATE('"+LIMIT_DATE+"','yyyy-mm-dd')) ";
			if(CONSUMER_TYPE!=null && !"".equals(CONSUMER_TYPE) && !"-".equals(CONSUMER_TYPE)){
				sql+=" AND B.TECLO = '"+CONSUMER_TYPE+"'";
			}
			if(REQUEST_TYPE!=null && !"".equals(REQUEST_TYPE) && !"-".equals(REQUEST_TYPE)){
				sql+=" AND B.PROBLEM_TYPE = '"+REQUEST_TYPE+"'";
			}
			sql+="		  )GROUP BY CITY_ID) A,(";
			sql+="		  SELECT CITY_ID, COUNT(1) NUMM FROM (";
			sql+="			  SELECT A.SEND_COMPANYID CITY_ID ";
			sql+="			  FROM NEWIRMS.T_BPM_FORM_INFO A,NEWIRMS.T_CUSTOMER_PROBLEM_INFO B,NEWIRMS.T_BNS_CUSTOMER_PROBLEM  C ";
			sql+="			  WHERE A.FLOW_ID = B.FLOWID AND C.FLOW_ID = A.FLOW_ID AND A.FLOW_MODEL = 'com.inspur.app.tieta.customerProblem.process.cusPro' AND EXISTS (";
			sql+="				  SELECT 1 FROM NEWIRMS.T_CUSTOMER_PROBLEM_DEAL_INFO WHERE PROBLEM_ID = B.ID ";
			sql+="			  ) AND (A.SEND_TIME >= TO_DATE('"+START_DATE+"','yyyy-mm-dd') AND A.SEND_TIME <= TO_DATE('"+LIMIT_DATE+"','yyyy-mm-dd')) ";
			if(CONSUMER_TYPE!=null && !"".equals(CONSUMER_TYPE) && !"-".equals(CONSUMER_TYPE)){
				sql+=" AND B.TECLO = '"+CONSUMER_TYPE+"' ";
			}
			if(REQUEST_TYPE!=null && !"".equals(REQUEST_TYPE) && !"-".equals(REQUEST_TYPE)){
				sql+=" AND B.PROBLEM_TYPE = '"+REQUEST_TYPE+"'";
			}
			sql+="		  )GROUP BY CITY_ID) B,( ";
			sql+="		  SELECT CITY_ID, COUNT(1) NUMM FROM ( ";
			sql+="			  SELECT A.SEND_COMPANYID CITY_ID ";
			sql+="			  FROM NEWIRMS.T_BPM_FORM_INFO A,NEWIRMS.T_CUSTOMER_PROBLEM_INFO B,NEWIRMS.T_BNS_CUSTOMER_PROBLEM C ";
			sql+="			  WHERE A.FLOW_ID = B.FLOWID AND C.FLOW_ID = A.FLOW_ID AND A.FLOW_MODEL = 'com.inspur.app.tieta.customerProblem.process.cusPro' AND NOT EXISTS (";
			sql+="				  SELECT 1 FROM NEWIRMS.T_CUSTOMER_PROBLEM_DEAL_INFO WHERE PROBLEM_ID = B.ID";
			sql+="			  ) AND (A.SEND_TIME >= TO_DATE('"+START_DATE+"','yyyy-mm-dd') AND A.SEND_TIME <= TO_DATE('"+LIMIT_DATE+"','yyyy-mm-dd')) ";
			if(CONSUMER_TYPE!=null && !"".equals(CONSUMER_TYPE) && !"-".equals(CONSUMER_TYPE)){
				sql+=" AND B.TECLO = '"+CONSUMER_TYPE+"' ";
			}
			if(REQUEST_TYPE!=null && !"".equals(REQUEST_TYPE) && !"-".equals(REQUEST_TYPE)){
				sql+=" AND B.PROBLEM_TYPE = '"+REQUEST_TYPE+"'";
			}
			sql+="		  )GROUP BY CITY_ID) C,( ";
			sql+="		  SELECT CITY_ID, COUNT(1) NUMM FROM (";
			sql+="			  SELECT A.SEND_COMPANYID CITY_ID ";
			sql+="			  FROM NEWIRMS.T_BPM_FORM_INFO A,NEWIRMS.T_CUSTOMER_PROBLEM_INFO B,NEWIRMS.T_BNS_CUSTOMER_PROBLEM C ";
			sql+="			  WHERE A.FLOW_ID = B.FLOWID AND C.FLOW_ID = A.FLOW_ID AND A.FLOW_MODEL = 'com.inspur.app.tieta.customerProblem.process.cusPro' AND C.REPLY_TIME >= SYSDATE AND A.CURRENT_STATE <> 3 AND B.TECLO = '"+CONSUMER_TYPE+"' AND (A.SEND_TIME >= TO_DATE('"+START_DATE+"','yyyy-mm-dd') AND A.SEND_TIME <= TO_DATE('"+LIMIT_DATE+"','yyyy-mm-dd')) AND B.PROBLEM_TYPE = '"+REQUEST_TYPE+"'";
			sql+="		  )GROUP BY CITY_ID) D,(";
			sql+="		  SELECT CITY_ID, COUNT(1) NUMM FROM (";
			sql+="			  SELECT A.SEND_COMPANYID CITY_ID ";
			sql+="			  FROM NEWIRMS.T_BPM_FORM_INFO A,NEWIRMS.T_CUSTOMER_PROBLEM_INFO B,NEWIRMS.T_BNS_CUSTOMER_PROBLEM  C ";
			sql+="			  WHERE A.FLOW_ID = B.FLOWID AND C.FLOW_ID = A.FLOW_ID AND A.FLOW_MODEL = 'com.inspur.app.tieta.customerProblem.process.cusPro' AND C.REPLY_TIME < SYSDATE AND A.CURRENT_STATE <> 3 AND EXISTS (";
			sql+="				  SELECT 1 FROM NEWIRMS.T_CUSTOMER_PROBLEM_DEAL_INFO WHERE PROBLEM_ID = B.ID";
			sql+="			  ) AND (A.SEND_TIME >= TO_DATE('"+START_DATE+"','yyyy-mm-dd') AND A.SEND_TIME <= TO_DATE('"+LIMIT_DATE+"','yyyy-mm-dd')) ";
			if(CONSUMER_TYPE!=null && !"".equals(CONSUMER_TYPE) && !"-".equals(CONSUMER_TYPE)){
				sql+=" AND B.TECLO = '"+CONSUMER_TYPE+"' ";
			}
			if(REQUEST_TYPE!=null && !"".equals(REQUEST_TYPE) && !"-".equals(REQUEST_TYPE)){
				sql+=" AND B.PROBLEM_TYPE = '"+REQUEST_TYPE+"'";
			}
			sql+="		  ) GROUP BY CITY_ID) E,(";
			sql+="		  SELECT CITY_ID, COUNT(1) NUMM FROM (";
			sql+="			  SELECT A.SEND_COMPANYID CITY_ID ";
			sql+="			  FROM NEWIRMS.T_BPM_FORM_INFO A,NEWIRMS.T_CUSTOMER_PROBLEM_INFO B,NEWIRMS.T_BNS_CUSTOMER_PROBLEM  C ";
			sql+="			  WHERE A.FLOW_ID = B.FLOWID AND C.FLOW_ID = A.FLOW_ID AND A.FLOW_MODEL = 'com.inspur.app.tieta.customerProblem.process.cusPro' AND A.CURRENT_STATE = 3 AND (A.SEND_TIME >= TO_DATE('"+START_DATE+"','yyyy-mm-dd') AND A.SEND_TIME <= TO_DATE('"+LIMIT_DATE+"','yyyy-mm-dd')) ";
			if(CONSUMER_TYPE!=null && !"".equals(CONSUMER_TYPE) && !"-".equals(CONSUMER_TYPE)){
				sql+=" AND B.TECLO = '"+CONSUMER_TYPE+"' ";
			}
			if(REQUEST_TYPE!=null && !"".equals(REQUEST_TYPE) && !"-".equals(REQUEST_TYPE)){
				sql+=" AND B.PROBLEM_TYPE = '"+REQUEST_TYPE+"'";
			}
			sql+="		  )GROUP BY CITY_ID) F,NEWAUTH.COMPANY G";
			sql+="	WHERE A.CITY_ID(+) = G.COMPANYID AND B.CITY_ID(+) = G.COMPANYID AND C.CITY_ID(+) = G.COMPANYID AND D.CITY_ID(+) = G.COMPANYID AND E.CITY_ID(+) = G.COMPANYID AND F.CITY_ID(+) = G.COMPANYID ORDER BY 地市 ";
			if(!IS_PROVINCE){
				sql = "SELECT W.* FROM ("+sql+") W WHERE W.地市   LIKE '%"+CITY_NAME+"%'";
			}
			resultObject.put("DATAS",jdbcTemplate.queryForList(sql));
		}catch(Exception e){
			e.printStackTrace();
			resultObject.put("success",false);
		}finally{
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().print(resultObject.toString());
		}
	}
	
	/*
	 * 	客户诉求工单明细
	 * 
	 * */
	@RequestMapping("/findGdCountDetails.ilf")
	public void findGdCountDetails(
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
		String START_DATE = null;
		String LIMIT_DATE = null;
		if(conditonMap.containsKey("START_DATE") && !"".equals(conditonMap.get("START_DATE").toString())){
			START_DATE = conditonMap.get("START_DATE").toString();
		}else{
			START_DATE = "1970-01-01";
		}
		if(conditonMap.containsKey("LIMIT_DATE") && !"".equals(conditonMap.get("LIMIT_DATE").toString())){
			LIMIT_DATE = conditonMap.get("LIMIT_DATE").toString();
		}else{
			LIMIT_DATE = "2099-12-30";
		}
		String sql = "";
		sql+="SELECT ";
		sql+="	  A.FLOW_NO 工单编号,A.FLOW_TITLE 工单名称,";
		sql+="	  B.TECLO 运营商,B.PROBLEM_TYPE 问题类别,B.PROBLEM_TYPE2 问题细分类型,B.PROBLEM_DESC 问题描述,B.REQUIRE_TIME 客户要求完成时间,";
		sql+="	  B.PROBLEM_LEVEL 问题级别,B.PROBLEM_SOURCE 问题来源,A.SEND_TIME 派单时间,B.OP_CITY 派单市州,B.OP_DEPT 派单部门,B.COPY_CITY 抄送市州,B.COPY_DEPT 抄送部门,(";
		sql+="		  SELECT MIN(OPERATE_TIME) FROM NEWIRMS.T_CUSTOMER_PROBLEM_DEAL_INFO WHERE PROBLEM_ID = B.ID";
		sql+="	  ) 接单时间,D.REASON 问题原因,D.MEASURE 解决举措,D.PROGRESS 解决进度,B.IS_DEAL 是否已解决,B.IS_COMMUNICATE 是否向客户沟通,B.IS_ACCEPT 客户是否认可,A.END_TIME 归档时间  ";
		sql+="FROM NEWIRMS.T_BPM_FORM_INFO A,NEWIRMS.T_CUSTOMER_PROBLEM_INFO B,NEWIRMS.T_CUSTOMER_PROBLEM_DEAL_INFO D ";
		sql+="WHERE ";
		sql+="	  A.FLOW_MODEL = 'com.inspur.app.tieta.customerProblem.process.cusPro' AND ";
		sql+="	  A.FLOW_ID = B.FLOWID AND ";
		sql+="	  D.PROBLEM_ID = B.ID AND ";
		sql+="	  A.SEND_TIME >= TO_DATE('"+START_DATE+"','YYYY-MM-DD') AND ";
		sql+="	  A.SEND_TIME <= TO_DATE('"+LIMIT_DATE+"','YYYY-MM-DD') ";
		if(loginUserUtil.isProvince(request)){
			if(conditonMap.containsKey("CITY_NAME") && !"".equals(conditonMap.get("CITY_NAME").toString()) && !"-".equals(conditonMap.get("CITY_NAME").toString())){
				sql+=" AND A.FLOW_TITLE LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
			}
		}else{
			sql+=" AND A.FLOW_TITLE LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
		}
		Integer count = jdbcTemplate.queryForInt("SELECT COUNT(*) FROM ("+sql+")");
		String pageSql = "";
		pageSql+="SELECT Z.* FROM (";
		pageSql+="	  SELECT M.*,ROWNUM AS RN FROM (";
		pageSql+="		  SELECT W.* FROM ("+sql+") W ORDER BY W.工单编号 ASC";
		pageSql+="	  ) M WHERE ROWNUM <= "+(displayStart+iDisplayLength);
		pageSql+=") Z WHERE Z.RN > "+displayStart;
		List<Map<String,Object>> jobDetails = jdbcTemplate.queryForList(pageSql);
		DataTableResult<Map<String,Object>> tableData = new DataTableResult<Map<String,Object>>();
		tableData.setsEcho(sEcho);
		tableData.setAaData(jobDetails);
		tableData.setiTotalRecords(count);
		tableData.setiTotalDisplayRecords(count);
		response.setContentType("application/json; charset=UTF-8");
		response.getWriter().print(JSONObject.fromObject(tableData).toString());
	}
	
	/*
	 * 	导出明细
	 * 
	 * */
	@RequestMapping("/exportDetailsToFile.ilf")
	public void exportDetailsToFile(
		@RequestParam String START_DATE,
		@RequestParam String LIMIT_DATE,
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		/*
		 * 	查询数据.
		 * 
		 * */
		if(START_DATE==null || "".equals(START_DATE)){
			START_DATE = "1970-01-01";
		}
		if(LIMIT_DATE==null || "".equals(LIMIT_DATE)){
			LIMIT_DATE = "2099-12-30";
		}
		String sql = "";
		sql+="SELECT ";
		sql+="	  A.FLOW_NO 工单编号,A.FLOW_TITLE 工单名称,";
		sql+="	  B.TECLO 运营商,B.PROBLEM_TYPE 问题类别,B.PROBLEM_TYPE2 问题细分类型,B.PROBLEM_DESC 问题描述,B.REQUIRE_TIME 客户要求完成时间,";
		sql+="	  B.PROBLEM_LEVEL 问题级别,B.PROBLEM_SOURCE 问题来源,A.SEND_TIME 派单时间,B.OP_CITY 派单市州,B.OP_DEPT 派单部门,B.COPY_CITY 抄送市州,B.COPY_DEPT 抄送部门,(";
		sql+="		  SELECT MIN(OPERATE_TIME) FROM NEWIRMS.T_CUSTOMER_PROBLEM_DEAL_INFO WHERE PROBLEM_ID = B.ID";
		sql+="	  ) 接单时间,D.REASON 问题原因,D.MEASURE 解决举措,D.PROGRESS 解决进度,B.IS_DEAL 是否已解决,B.IS_COMMUNICATE 是否向客户沟通,B.IS_ACCEPT 客户是否认可,A.END_TIME 归档时间  ";
		sql+="FROM NEWIRMS.T_BPM_FORM_INFO A,NEWIRMS.T_CUSTOMER_PROBLEM_INFO B,NEWIRMS.T_CUSTOMER_PROBLEM_DEAL_INFO D ";
		sql+="WHERE ";
		sql+="	  A.FLOW_MODEL = 'com.inspur.app.tieta.customerProblem.process.cusPro' AND ";
		sql+="	  A.FLOW_ID = B.FLOWID AND ";
		sql+="	  D.PROBLEM_ID = B.ID AND ";
		sql+="	  A.SEND_TIME >= TO_DATE('"+START_DATE+"','YYYY-MM-DD') AND ";
		sql+="	  A.SEND_TIME <= TO_DATE('"+LIMIT_DATE+"','YYYY-MM-DD') ";
		if(!loginUserUtil.isProvince(request)){
			sql+=" AND A.FLOW_TITLE LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
		}
		List<Map<String,Object>> jobDetails = jdbcTemplate.queryForList(sql);
		/*
		 * 	执行导出.
		 * 
		 * */
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();
        XSSFFont xssfFont = xssfWorkbook.createFont();
        xssfFont.setFontName("宋体");
        xssfFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        xssfFont.setFontHeightInPoints((short)8);
        xssfFont.setColor((short)16711680); 
        /*
		 * 	Title_Style（纵向:居中,横向:居中,底色:浅灰,边框:有）.
		 * 
		 * */
        XSSFCellStyle titleStyle = xssfWorkbook.createCellStyle();
        titleStyle.setFont(xssfFont);
        titleStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);              
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        titleStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);           
        titleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        /*
		 * 	Text_Style（纵向:居中,横向:居左,底色:白色,边框:有）.
		 * 
		 * */
        XSSFCellStyle normalStyle = xssfWorkbook.createCellStyle();
        normalStyle.setFont(xssfFont);
        normalStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        normalStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        normalStyle.setWrapText(true);
        normalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);              
        normalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        normalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        normalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        normalStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);           
        normalStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        /*
		 * 	Style（纵向:居中,横向:居左,底色:白色,边框:无）.
		 * 
		 * */
        XSSFCellStyle noBorderStyle = xssfWorkbook.createCellStyle();
        noBorderStyle.setFont(xssfFont);
        noBorderStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        noBorderStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        noBorderStyle.setFillForegroundColor(HSSFColor.WHITE.index);
        /*
         * 	开始写入数据.
         * 
         * */
        XSSFSheet xssfSheet = xssfWorkbook.createSheet();
        for(int i=0;i<=22;i++){
        	if(i==0){
                xssfSheet.setColumnWidth(i,500);
        	}else{
        		xssfSheet.setColumnWidth(i,6000);
        	}
        }
        XSSFRow nullRow = xssfSheet.createRow(0);            
        nullRow.setHeight(new Integer(200).shortValue());
        for(int k=0;k<=22;k++){
            XSSFCell nullCell = nullRow.createCell(k);
            nullCell.setCellStyle(noBorderStyle);
        }
        xssfSheet.addMergedRegion(new CellRangeAddress(0,0,0,22));
        /*创建标题.*/
        XSSFRow headRow = xssfSheet.createRow(1);            
        headRow.setHeight(new Integer(500).shortValue());
        for(int k=0;k<=22;k++){
            XSSFCell headCell = headRow.createCell(k);
            if(k==0){
            	headCell.setCellStyle(noBorderStyle);
            	headCell.setCellValue("");
            }else{
            	headCell.setCellStyle(titleStyle);
            	if(k==1){
	            	headCell.setCellValue("工单编号");
	            }else if(k==2){
	            	headCell.setCellValue("工单名称");
	            }else if(k==3){
	            	headCell.setCellValue("运营商");
	            }else if(k==4){
	            	headCell.setCellValue("问题类别");
	            }else if(k==5){
	            	headCell.setCellValue("问题细分类型");
	            }else if(k==6){
	            	headCell.setCellValue("问题描述");
	            }else if(k==7){
	            	headCell.setCellValue("客户要求完成时间");
	            }else if(k==8){
	            	headCell.setCellValue("问题级别");
	            }else if(k==9){
	            	headCell.setCellValue("问题来源");
	            }else if(k==10){
	            	headCell.setCellValue("派单时间");
	            }else if(k==11){
	            	headCell.setCellValue("派单市州");
	            }else if(k==12){
	            	headCell.setCellValue("派单部门");
	            }else if(k==13){
	            	headCell.setCellValue("抄送市州");
	            }else if(k==14){
	            	headCell.setCellValue("抄送部门");
	            }else if(k==15){
	            	headCell.setCellValue("接单时间");
	            }else if(k==16){
	            	headCell.setCellValue("问题原因");
	            }else if(k==17){
	            	headCell.setCellValue("解决举措");
	            }else if(k==18){
	            	headCell.setCellValue("解决进度");
	            }else if(k==19){
	            	headCell.setCellValue("是否已解决");
	            }else if(k==20){
	            	headCell.setCellValue("是否和客户沟通");
	            }else if(k==21){
	            	headCell.setCellValue("客户是否认可");
	            }else if(k==22){
	            	headCell.setCellValue("归档时间");
	            }
            }
        }
        if(jobDetails.size()>0){
        	for(int rowIndex=2;rowIndex<(jobDetails.size()+2);rowIndex++){
            	Map<String,Object> jobDetail = jobDetails.get(rowIndex-2);
            	XSSFRow dataRow = xssfSheet.createRow(rowIndex);
            	dataRow.setHeight(new Integer(500).shortValue());
            	for(int j=0;j<=22;j++){
            		XSSFCell xssfCell = dataRow.createCell(j);
            		if(j==0){
            			xssfCell.setCellStyle(noBorderStyle);
            			xssfCell.setCellValue("");
    	            }else{
    	            	xssfCell.setCellStyle(normalStyle);
    	            	if(j==1){
    		            	xssfCell.setCellValue(jobDetail.get("工单编号")==null?"":jobDetail.get("工单编号").toString());
    		            }else if(j==2){
    		            	xssfCell.setCellValue(jobDetail.get("工单名称")==null?"":jobDetail.get("工单名称").toString());
    		            }else if(j==3){
    		            	xssfCell.setCellValue(jobDetail.get("运营商")==null?"":jobDetail.get("运营商").toString());
    		            }else if(j==4){
    		            	xssfCell.setCellValue(jobDetail.get("问题类别")==null?"":jobDetail.get("问题类别").toString());
    		            }else if(j==5){
    		            	xssfCell.setCellValue(jobDetail.get("问题细分类型")==null?"":jobDetail.get("问题细分类型").toString());
    		            }else if(j==6){
    		            	xssfCell.setCellValue(jobDetail.get("问题描述")==null?"":jobDetail.get("问题描述").toString());
    		            }else if(j==7){
    		            	xssfCell.setCellValue(jobDetail.get("客户要求完成时间")==null?"":jobDetail.get("客户要求完成时间").toString());
    		            }else if(j==8){
    		            	xssfCell.setCellValue(jobDetail.get("问题级别")==null?"":jobDetail.get("问题级别").toString());
    		            }else if(j==9){
    		            	xssfCell.setCellValue(jobDetail.get("问题来源")==null?"":jobDetail.get("问题来源").toString());
    		            }else if(j==10){
    		            	if(jobDetail.get("派单时间")!=null){
    		            		java.util.Date jobSendDate = (java.util.Date)jobDetail.get("派单时间");
    							xssfCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(jobSendDate));
    						}else{
    							xssfCell.setCellValue("");
    						}
    		            }else if(j==11){
    		            	xssfCell.setCellValue(jobDetail.get("派单市州")==null?"":jobDetail.get("派单市州").toString());
    		            }else if(j==12){
    		            	xssfCell.setCellValue(jobDetail.get("派单部门")==null?"":jobDetail.get("派单部门").toString());
    		            }else if(j==13){
    		            	xssfCell.setCellValue(jobDetail.get("抄送市州")==null?"":jobDetail.get("抄送市州").toString());
    		            }else if(j==14){
    		            	xssfCell.setCellValue(jobDetail.get("抄送部门")==null?"":jobDetail.get("抄送部门").toString());
    		            }else if(j==15){
    		            	xssfCell.setCellValue(jobDetail.get("接单时间")==null?"":jobDetail.get("接单时间").toString());
    		            }else if(j==16){
    		            	xssfCell.setCellValue(jobDetail.get("问题原因")==null?"":jobDetail.get("问题原因").toString());
    		            }else if(j==17){
    		            	xssfCell.setCellValue(jobDetail.get("解决举措")==null?"":jobDetail.get("解决举措").toString());
    		            }else if(j==18){
    		            	xssfCell.setCellValue(jobDetail.get("解决进度")==null?"":jobDetail.get("解决进度").toString());
    		            }else if(j==19){
    		            	xssfCell.setCellValue(jobDetail.get("是否已解决")==null?"":jobDetail.get("是否已解决").toString());
    		            }else if(j==20){
    		            	xssfCell.setCellValue(jobDetail.get("是否向客户沟通")==null?"":jobDetail.get("是否向客户沟通").toString());
    		            }else if(j==21){
    		            	xssfCell.setCellValue(jobDetail.get("客户是否认可")==null?"":jobDetail.get("客户是否认可").toString());
    		            }else if(j==22){
    		            	if(jobDetail.get("归档时间")!=null){
    		            		java.util.Date rebackInFileDate = (java.util.Date)jobDetail.get("归档时间");
    							xssfCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(rebackInFileDate));
    						}else{
    							xssfCell.setCellValue("");
    						}
    		            }
    	            }
    	        }
            }
        }
        String folderPath = request.getSession().getServletContext().getRealPath("/")+"jsp"+File.separator+"firstIndex"+File.separator+"reports"+File.separator+"files";
		File fileFolder = new File(folderPath);
		if(!fileFolder.exists()){
			fileFolder.mkdirs();
		}
		String fileName = new Date().getTime()+".xlsx";
		String finalExportPath = folderPath+File.separator+fileName;
		File finalExportFile = new File(finalExportPath);
		if(finalExportFile.exists()){
			finalExportFile.delete();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(finalExportFile);
		xssfWorkbook.write(fileOutputStream);
		fileOutputStream.close();
		/*
		 * 	将文件写出.
		 * 
		 * */
		finalExportFile = new File(finalExportPath);
		FileInputStream fileInputStream = new FileInputStream(finalExportFile);
		long filesize = finalExportFile.length();
		response.setContentType("text/html;charset=gb2312");
		response.addHeader("content-type","application/x-msdownload;");
		response.setHeader("Content-Disposition","attachment;filename=\""+new String((finalExportFile.getName()).getBytes(),"ISO8859-1"));
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
