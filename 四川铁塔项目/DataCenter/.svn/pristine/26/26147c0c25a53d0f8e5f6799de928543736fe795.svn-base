package com.function.rules.service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.springframework.stereotype.Repository;
import com.function.dbmanage.model.BasicColumn;
import com.function.dbmanage.model.BasicDbTable;
import com.function.dbmanage.service.BasicColumnService;
import com.function.dbmanage.service.BasicDbTableService;
import com.function.rules.model.RuleDetail;
import com.function.rules.util.StyleUtil;
@Repository("reportBuilder")
public class ReportBuilder{
	
	@Autowired
	private RuleJobService ruleJobService;
	
	@Autowired
	private JobDataService jobDataService;
	
	@Autowired
	private JobItemService jobItemService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public Boolean createReport(Integer ruleId)throws Exception{
		System.out.println("Clean: Report File Create Job Start.");
		try{
			RuleDetail ruleDetail = ruleDetailService.selectModel(ruleId);
			File file = new File(
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+ruleDetail.getFILE_REPORT()
			);
			if(file.exists()){
				file.delete();
			}
			BasicDbTable bindingTable = basicDbTableService.selectModel(ruleDetail.getBIND_TABLE());
			/*
			 * 	主键字段；中文标识字段
			 * 
			 * */
			String PRIMARY_NAME = "";
			String CHINESE_NAME = "";
			List<BasicColumn> columns = basicColumnService.selectModelsByHql("from BasicColumn where BELONG_TABLE = "+bindingTable.getID()+" and IS_EXPORT = 'Y'");
			if(columns.size()>0){
				for(int l=0;l<columns.size();l++){
					if("Y".equals(columns.get(l).getIS_PRIMARY_KEY())){
						/*主键*/
						PRIMARY_NAME = columns.get(l).getCOLUMN_NAME();
					}else{
						/*名称*/
						CHINESE_NAME = columns.get(l).getCOLUMN_NAME();
					}
				}
			}
			/*
			 * 	加载[模板文件]并获取[Sheet].
			 * 
			 * */
			XSSFSheet xssfSheet = null;
	        FileInputStream fileInputStreams = new FileInputStream(new File(
	        	jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'templateFile'").get("PRO_VALUE").toString()		
	        ));
	        XSSFWorkbook workBook = new XSSFWorkbook(fileInputStreams);      
	        Iterator<XSSFSheet> sheetCar = workBook.iterator();
	        while(sheetCar.hasNext()){
	        	xssfSheet = (XSSFSheet)sheetCar.next();
	        	break;
	        }
	        /*
	         * 	定义常用的Style
	         * 
	         * */
	        XSSFCellStyle leftWhites = StyleUtil.getStyle(workBook,HSSFCellStyle.ALIGN_LEFT,HSSFColor.WHITE.index);
	        XSSFCellStyle centerGray = StyleUtil.getStyle(workBook,HSSFCellStyle.ALIGN_CENTER_SELECTION,HSSFColor.GREY_25_PERCENT.index);
	        /*
	         * 	填充报告头数据：目标模型，规则名称，核查目的，解决建议，核查日期
	         * 
	         * */
	        for(int h=2;h<=6;h++){
	        	XSSFCell hCell = xssfSheet.getRow(h).getCell(2);
	        	hCell.setCellStyle(leftWhites);
	        	switch(h){
					case 2:
						/*模型名称*/
						hCell.setCellValue(bindingTable.getTABLE_ALIAS()+"("+bindingTable.getTABLE_NAME()+")");
						break;
					case 3:
						/*规则名称*/
						hCell.setCellValue(ruleDetail.getRULE_NAME().trim());
						break;
					case 4:
						/*核查目的*/
						hCell.setCellValue(ruleDetail.getRULE_DESC().trim());
						break;
					case 5:
						/*解决建议*/
						hCell.setCellValue(ruleDetail.getRESOLVE_METHOD().trim());
						break;
					case 6:
						/*核查日期*/
						hCell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
						break;
	        	}
	        }
	        Integer editingRow = 7;
	        /*
			 * 	生产行信息[主键；中文标识；问题描述]
			 * 
			 * */ 
			XSSFRow tableHead = xssfSheet.createRow(editingRow);
			tableHead.setHeight(new Integer(430).shortValue());
			for(int r=1;r<=7;r++){
				XSSFCell sCell = tableHead.createCell(r);
				sCell.setCellStyle(centerGray);
			}
			xssfSheet.addMergedRegion(new CellRangeAddress(editingRow,editingRow,3,7));
			for(int c=1;c<=3;c++){
				XSSFCell colsCell = xssfSheet.getRow(editingRow).getCell(c);
	        	switch(c){
					case 1:
						colsCell.setCellValue("主键（"+PRIMARY_NAME+"）");
						break;
					case 2:
						colsCell.setCellValue("资源名称（"+CHINESE_NAME+"）");
						break;
					case 3:
						colsCell.setCellValue("问题描述");
						break;						
	        	}
			}
			editingRow++;        
	        String sql = "";
	        sql+="SELECT PRIMARY_VALUE,NAME_VALUE,LISTAGG(PROBLEM_DESC,';') WITHIN GROUP(ORDER BY PRIMARY_VALUE ASC) AS PROBLEM_DESCRIBE FROM(";
	        sql+="	SELECT * FROM RULE_JOB_DATA WHERE JOB_ITEM_ID IN(";
	        sql+="		SELECT ID AS ITEM_ID FROM RULE_JOB_ITEM WHERE JOB_ID IN(";
	        sql+="			SELECT ID FROM RULE_JOB WHERE RULE_ID = "+ruleId;
	        sql+="		)";
	        sql+="	)ORDER BY JOB_ITEM_ID ASC,PRIMARY_VALUE ASC";
	        sql+=")GROUP BY PRIMARY_VALUE,NAME_VALUE ORDER BY PRIMARY_VALUE ASC";
	        Integer totalCount = jdbcTemplate.queryForInt("SELECT COUNT(1) FROM("+sql+")");
	        Integer pageNumber = 0;
	        if(totalCount>0){
	        	if(totalCount!=0 && totalCount<=2000){
					pageNumber = 1;
				}else{
					if(totalCount%2000==0){
						pageNumber = totalCount/2000;
					}else{
						pageNumber = totalCount/2000+1;
					}
				}
	        	System.out.println("Clean: Now find "+pageNumber+" page of fatal records need to be writed.");
	        	for(int p=1;p<=pageNumber;p++){
	        		String pageSql = "";
	        		pageSql+="SELECT H.* FROM(";
	        		pageSql+="	 SELECT P.*,ROWNUM AS RN FROM("+sql+") P WHERE ROWNUM <= "+(p*2000);
	        		pageSql+=") H WHERE H.RN > "+((p-1)*2000);
	        		List<Map<String,Object>> datas = jdbcTemplate.queryForList(pageSql);
	        		Map<String,Object> $map = null;
		        	for(int i=0;i<datas.size();i++){
		        		$map = datas.get(i);
		        		/*
		        		 * 	创建数据容器
		        		 * 	
		        		 * */
		        		XSSFRow dataRow = xssfSheet.createRow(editingRow);
		        		dataRow.setHeight(new Integer(430).shortValue());
		    			for(int r=1;r<=7;r++){
		    				XSSFCell xssfCell = dataRow.createCell(r);
		    				xssfCell.setCellStyle(leftWhites);
		    			}
		    			xssfSheet.addMergedRegion(new CellRangeAddress(editingRow,editingRow,3,7));
		    			/*
		        		 * 	填充数据
		        		 * 	
		        		 * */
		    			for(int c=1;c<=3;c++){
		    				XSSFCell colsCell = xssfSheet.getRow(editingRow).getCell(c);
		    				colsCell.setCellStyle(leftWhites);
		    	        	switch(c){
		    					case 1:
		    						colsCell.setCellValue($map.get("PRIMARY_VALUE")==null?"-":$map.get("PRIMARY_VALUE").toString());
		    						break;
		    					case 2:
		    						colsCell.setCellValue($map.get("NAME_VALUE")==null?"-":$map.get("NAME_VALUE").toString());
		    						break;
		    					case 3:
		    						colsCell.setCellValue($map.get("PROBLEM_DESCRIBE")==null?"-":$map.get("PROBLEM_DESCRIBE").toString());
		    						break;						
		    	        	}
		    			}        		
		        		editingRow++;
		        	}
		        	if(((p*2000)%10000)==0){
						System.out.println("Clean: Step to "+(p*2000)+" index have been writed.");
					}
	        	}
	        }
			/*
			 * 	将数据报告写出保存至文件.
			 * 
			 * */
	        String fileName = new Date().getTime()+".xlsx";
			FileOutputStream fileOutputStream = new FileOutputStream(new File(
				jdbcTemplate.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'reportFolder'").get("PRO_VALUE").toString()+fileName
			));
			workBook.write(fileOutputStream);
			fileOutputStream.close();
			fileInputStreams.close();
			ruleDetail.setFILE_REPORT(fileName);
			ruleDetailService.updateModel(ruleDetail);
			System.out.println("Clean: The Audit Report Named By '"+fileName+"' Has Created Successfully.");
			System.out.println("Clean: Report File Create Job Done.");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
		
	/*
	 * 	ModelConfig
	 * 
	 * */
	
	@Autowired
	private BasicDbTableService basicDbTableService;
	
	@Autowired
	private BasicColumnService basicColumnService;
	
	/*
	 * 	Rule
	 * 
	 * */
	
	@Autowired
	private RuleDetailService ruleDetailService;
}