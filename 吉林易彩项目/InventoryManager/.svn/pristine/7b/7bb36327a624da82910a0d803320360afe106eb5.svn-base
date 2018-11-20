package manage.buried.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import base.util.ExcelUtil;
import base.util.TextUtil;
import manage.buried.pojo.BuriedInfoObj;
import manage.buried.service.impl.IburiedService;
import manage.dictionary.service.impl.IdictService;

public class BuriedService extends DataBase implements IburiedService{
	private IdictService dictService;
	private JdbcTemplate jdbcTemplate;
	private static String buriedGrid="buried.getBuriedGrid";
	private static String buriedCount="buried.getBuriedCount";
	private static String getBuried = "buried.getBuried";
	private static String updateBuried = "buried.updateBuried";
	private static String insertBuried = "buried.insertBuried";
	private static String delBurieds    = "buried.delBurieds";
	/**
	 * 得到分页数据
	 * @param object
	 * @param start
	 * @param length
	 * @return
	 */
	public List<BuriedInfoObj> getBuriedGrid(BuriedInfoObj object){
		List<BuriedInfoObj> list =getObjects(buriedGrid, object);
		return list;
	}
	
	
	/**
	 * 得到直埋列表
	 * @param object
	 * @return
	 */
	public List<BuriedInfoObj> getBuried(BuriedInfoObj object){
		List<BuriedInfoObj> list = getObjects(getBuried, object);
		return list;
	}
	
	/**
	 * 删除标石数据
	 * @param ids
	 */
	public void delBurieds(String ids){
		this.delete(delBurieds, ids);
	}
	
	/**
	 * 得到分页总数
	 * @param object
	 * @return
	 */
	public int getBuriedCount(BuriedInfoObj object){
		return getCount(buriedCount, object);
	}
	
	/**
	 * 修改直埋信息
	 * @param object
	 * @return
	 */
	public int updateBuried(BuriedInfoObj object){
		return this.update(updateBuried, object);
	}
	
	/**
	 * 新建直埋信息
	 * @param object
	 * @return
	 */
	public int insertBuried(BuriedInfoObj object){
		return (Integer) this.insert(insertBuried, object);
	}
	
	/**
	 * 导出数据模板
	 * @param request
	 * @param response
	 */
	public void expTemp(HttpServletRequest request,HttpServletResponse response){
		try{
			String caption = "直埋数据模板";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();// 生成一个表格
			sheet.setDefaultColumnWidth(15);
			//创建一个隐藏列
			HSSFSheet hiddensheet=ExcelUtil.createHiddenSheet(workbook,"hiddensheet");
			int k=1;
			//创建表头
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			int col=0;
	        int cur=0;
	        
	        cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
	        cell=row.createCell(col++);
		    cell.setCellValue("直埋名称");
		    cur++;
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cell=row.createCell(col++);
		    cell.setCellValue("维护区域");
		    cur++;
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cell=row.createCell(col++);
		    cell.setCellValue("维护方式");
		    ExcelUtil.setContent(hiddensheet,0,cur,"维护方式");
		    sheet.addValidationData(ExcelUtil.setHiddenCell(cur, col, "维护方式", workbook,dictService.getDicMap("buriedModel"), hiddensheet));

		    cur++;
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cell=row.createCell(col++);
		    cell.setCellValue("维护单位");
		    cur++;
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cell=row.createCell(col++);
		    cell.setCellValue("直埋长度");
		    cur++;
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cell=row.createCell(col++);
		    cell.setCellValue("质量责任人");
		    cur++;
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cell=row.createCell(col++);
		    cell.setCellValue("一线维护人");
		    cur++;
	        
	      //下载附件
	      ExcelUtil.downloadFile(caption, workbook, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出数据
	 * @param request
	 * @param response
	 */
	public void expData(BuriedInfoObj object,HttpServletRequest request,HttpServletResponse response){
		try{
			String caption = "直埋数据";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();// 生成一个表格
			sheet.setDefaultColumnWidth(15);
			List<BuriedInfoObj> list = this.getBuried(object);
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			int col=0;
			cell=row.createCell(col++);
		    cell.setCellValue("直埋名称");
		    cell=row.createCell(col++);
		    cell.setCellValue("维护区域");
		    cell=row.createCell(col++);
		    cell.setCellValue("维护方式");
		    cell=row.createCell(col++);
		    cell.setCellValue("维护单位");
		    cell=row.createCell(col++);
		    cell.setCellValue("直埋长度");
		    cell=row.createCell(col++);
		    cell.setCellValue("质量责任人");
		    cell=row.createCell(col++);
		    cell.setCellValue("一线维护人");
		    cell=row.createCell(col++);
		    cell.setCellValue("创建时间");
		    cell=row.createCell(col++);
		    cell.setCellValue("创建人");
		    cell=row.createCell(col++);
		    cell.setCellValue("备注");
		    
		    
		    if(TextUtil.isNotNull(list)){
		    	for(int i=0;i<list.size();i++){
		    		BuriedInfoObj buried = list.get(i);
		    		HSSFRow rows=sheet.createRow(i+1);
		    		ExcelUtil.createCell(rows, 0, TextUtil.isNull(buried.getBuriedName()) ? " " : buried.getBuriedName().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 1, TextUtil.isNull(buried.getBuriedArea()) ? " " : buried.getBuriedArea().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 2, TextUtil.isNull(buried.getBuriedModelStr()) ? " " : buried.getBuriedModelStr().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 3, TextUtil.isNull(buried.getBuriedDept()) ? " " : buried.getBuriedDept().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 4, TextUtil.isNull(buried.getBuriedLength()) ? " " : buried.getBuriedLength().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 5, TextUtil.isNull(buried.getDataQualitier()) ? " " : buried.getDataQualitier().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 6, TextUtil.isNull(buried.getMaintainer()) ? " " : buried.getMaintainer().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 7, TextUtil.isNull(buried.getCreateTime()) ? " " : buried.getCreateTime().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 8, TextUtil.isNull(buried.getCreater()) ? " " : buried.getCreater().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 9, TextUtil.isNull(buried.getRemark()) ? " " : buried.getRemark().toString(), ExcelUtil.getValueStyle(workbook));
		    	
		    	}
		    }
			
			
			ExcelUtil.downloadFile(caption, workbook, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导入数据
	 * @param file
	 * @param fileName
	 * @return
	 */
	public String impData(File file,String fileName){
		int succ=0;
	    String jsonstr="";
	    try{
	    	HSSFWorkbook workBook = this.getWorkbook(file);
			HSSFSheet sheet=workBook.getSheetAt(0);
			int maxRow=sheet.getLastRowNum();
			HSSFRow row=sheet.getRow(1);
			int maxCell=row.getLastCellNum();
			HSSFCell cell=null;
			StringBuffer sb=null;
			String value="";
			NumberFormat nf=NumberFormat.getNumberInstance(Locale.CHINA);
			nf.setGroupingUsed(false);
			nf.setMaximumFractionDigits(0);
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time=sdf.format(date);
			for(int i = 1; i <= maxRow; i++){
				sb = new StringBuffer("INSERT into buriedInfo("
						+ "buriedName,buriedArea,buriedModel,buriedDept,buriedLength,"
						+ "dataQualitier,maintainer,deleteflag,createTime,creater)"
						+ "VALUES(");
				row=sheet.getRow(i);
				for(int j=0; j<maxCell; j++){
					cell=row.getCell(j);
					switch(cell.getCellType()){
					 case HSSFCell.CELL_TYPE_NUMERIC:value=nf.format(cell.getNumericCellValue());break;
					 case HSSFCell.CELL_TYPE_STRING:value=cell.getStringCellValue(); break;
					 case HSSFCell.CELL_TYPE_BLANK:value=" ";break;
					}
					if(j ==2){
						value = dictService.getDicValue("buriedModel", value);
					}
					
					if(j == maxCell -1){
						sb.append("'"+value+"','0','"+time+"','root'");
					}else{
						sb.append("'"+value+"',");
					}
				}
				sb.append(")");
				this.jdbcTemplate.execute(sb.toString());
			}
			
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return jsonstr;
	}
	
	/**
	 * 得到直埋map
	 * @param obj
	 * @return
	 */
	public Map<String, String> getBuriedMap(BuriedInfoObj obj){
		Map<String, String> map = new HashMap<String, String>();
		List<BuriedInfoObj> list = getObjects(getBuried, obj);
		if(TextUtil.isNotNull(list)){
			for(Iterator<BuriedInfoObj> it = list.iterator();it.hasNext();){
				BuriedInfoObj buried = it.next();
				if(!map.containsKey(buried.getBuriedId()+"")){
					map.put(buried.getBuriedId()+"", buried.getBuriedName());
				}
			}
		}
		
		return map;
	}
	
	private HSSFWorkbook getWorkbook(File file){
		FileInputStream fis=null;
		try{
			fis=new FileInputStream(file);//获得流文件
			POIFSFileSystem poifs=new POIFSFileSystem(fis);//解析
			HSSFWorkbook workbook=new HSSFWorkbook(poifs);//HSSFWorkbook读取excel
			
			return workbook;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			if(fis!=null)
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public IdictService getDictService() {
		return dictService;
	}
	public void setDictService(IdictService dictService) {
		this.dictService = dictService;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
