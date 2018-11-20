package manage.stone.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;





import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import base.util.ExcelUtil;
import base.util.TextUtil;
import manage.dictionary.service.impl.IdictService;
import manage.stone.pojo.StoneInfoBean;
import manage.stone.service.impl.IstoneInfoService;

public class StoneInfoService extends DataBase implements IstoneInfoService{
	private IdictService dictService;
	private JdbcTemplate jdbcTemplate;
	private static String stoneGrid = "stone.getStoneGrid";
	private static String stoneCount = "stone.getStoneCount";
	private static String getStone ="stone.getStone";
	private static String updateStone ="stone.updateStone";
	private static String insertStone = "stone.insertStone";
	private static String delStones = "stone.delStones";
	
	/**
	 * 得到所有的标石信息
	 * @param obj
	 * @return
	 */
	public List<StoneInfoBean> getStoneGrid(StoneInfoBean obj){
		if(TextUtil.isNotNull(obj.getBuriedId())){
			obj.setLate(null);
			obj.setLats(null);
			obj.setLatitude(null);
			obj.setLone(null);
			obj.setLons(null);
			obj.setLongitude(null);
		}
		if(TextUtil.isNotNull(obj.getStoneName())){
			String stoneName = obj.getStoneName();
			if(stoneName.contains(" ")){
				stoneName= stoneName.replaceAll(" +", "%");
			}
			obj.setStoneName("%"+stoneName+"%");
		}
		//进行跨地市限制
		 if(TextUtil.isNotNull(obj.getStoneArea()) && obj.getStoneArea().contains("*")) {
		     String[] areas = obj.getStoneArea().split("\\*");
		     String sql = "";
		     for(String area : areas) {
		       sql +=" instr(stoneArea, '"+area+"') > 0 or";
		     }
		     if(TextUtil.isNotNull(sql) && sql.endsWith("or")) {
		       sql = sql.substring(0,sql.length()-2);
		       obj.setStoneArea(null);
		       obj.setExtendsSql(sql);
		     }
		   }
		List<StoneInfoBean> list = getObjects(stoneGrid, obj);
		return list;
	}
	
	/**
	 * 得到标石列表
	 * @param obj
	 * @return
	 */
	public List<StoneInfoBean> getStone(StoneInfoBean obj){
		List<StoneInfoBean> list = getObjects(getStone, obj);
		return list;
	}
	
	/**
	 * 得到具体的标石
	 * @param obj
	 * @return
	 */
	public StoneInfoBean getStoneObj(StoneInfoBean obj){
		return (StoneInfoBean) getObject(getStone, obj);
	}
	
	
	/**
	 * 根据主键得到
	 * @param stoneId
	 * @return
	 */
	public StoneInfoBean getStoneById(Integer stoneId){
		StoneInfoBean stone = new StoneInfoBean();
		stone.setStoneId(stoneId);
		return (StoneInfoBean) getObject(getStone, stone);
	}
	/**
	 * 得到查询的条数
	 * @param obj
	 * @return
	 */
	public int getStoneCount(StoneInfoBean obj){
		return getCount(stoneCount, obj);
	}
	
	/**
	 * 修改标石信息
	 * @param obj
	 * @return
	 */
	public int updateStone(StoneInfoBean obj){
		return this.update(updateStone, obj);
	}
	
	
	
	/**
	 * 更新直埋段数据
	 * @param stoneId
	 * @param stoneName
	 */
	public void upBuriedSeg(Integer stoneId,String stoneName){
		String startSql = "update buriedpartinfo"
				+ " set buriedPartStart ='"+stoneName+"' ,"
				+ " buriedPartName = concat('"+stoneName+"','-',buriedPartEnd)"
				+ " where buriedPartStartId = '"+stoneId+"' and deleteflag ='0'";
		this.jdbcTemplate.execute(startSql);
		String endSql = "update buriedpartinfo "
				+ " set buriedPartEnd='"+stoneName+"', "
				+ " buriedPartName = concat( buriedPartStart ,'-','"+stoneName+"')"
				+ " where buriedPartEndId ='"+stoneId+"' and deleteflag='0'";
		this.jdbcTemplate.execute(endSql);
	}
	
	
	
	/**
	 * 更新数据
	 */
	public void upVal(){
		String sql  = "select text,val from at ";
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		for(int i=0;i<list.size();i++){
			String rest = "";
			Map<String, Object> map = list.get(i);
			String[] vals = map.get("val").toString().split(",");
			String text = map.get("text").toString();
			for(int j=0;j<vals.length;j++){
				String cabSql = "select cableid from job_cable where resNum='"+vals[j]+"'";
				
				String id = this.jdbcTemplate.queryForInt(cabSql)+"";
				if(TextUtil.isNotNull(id)){
					rest +=id+",";
				}
			}
			String upSql = "update at set val='"+rest+"' where text ='"+text+"'";
			System.out.println(upSql);
		}
	}
	
	
	/**
	 * 添加标石信息
	 * @param obj
	 * @return
	 */
	public int insertStone(StoneInfoBean obj){
		return (Integer) this.insert(insertStone, obj);
	}
	
	/**
	 * 删除标石数据
	 * @param ids
	 */
	public void delStones(String ids){
		this.delete(delStones, ids);
	}
	
	/**
	 * 得到标石的敷设
	 * @param stone
	 * @return
	 */
	public boolean getStoneLay(StoneInfoBean stone){
		StringBuffer sql = new StringBuffer();
		sql.append("select id from opticab_lay where atype in (9110,0) and aid in (");
		sql.append(stone.getStoneId());
		if(TextUtil.isNotNull(stone.getResNum())){
			sql.append(","+stone.getResNum()+" ");
		}
		sql.append(") and deleteFlag = '0' ");
		sql.append(" union all ");
		sql.append(" select id from opticab_lay where ztype in (9110,0)  and zid in ( ");
		sql.append(stone.getStoneId());
		if(TextUtil.isNotNull(stone.getResNum())){
			sql.append(","+stone.getResNum()+" ");
		}
		sql.append(" ) and deleteFlag = '0'");
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql.toString());
		if(TextUtil.isNotNull(list)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 导出数据模板
	 * @param request
	 * @param response
	 */
	public void expTemp(HttpServletRequest request,HttpServletResponse response){
		try{
			String caption = "标石数据模板";
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
		    cell.setCellValue("标石名称");
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cur++;
		    
		    cell=row.createCell(col++);
		    cell.setCellValue("标石方位");
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    ExcelUtil.setContent(hiddensheet,0,cur,"标石方位");
		    sheet.addValidationData(ExcelUtil.setHiddenCell(cur, col, "标石方位", workbook, dictService.getDicMap("stonePosition"), hiddensheet));
		    cur++;
		    
		    cell=row.createCell(col++);
		    cell.setCellValue("标石序号");
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("所属区域");
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("经度");
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("纬度");
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("产权性质");
		    ExcelUtil.setContent(hiddensheet,0,cur,"产权性质");
		    sheet.addValidationData(ExcelUtil.setHiddenCell(cur, col, "产权性质", workbook, dictService.getDicMap("propertyNature"), hiddensheet));
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("产权单位");
		    ExcelUtil.setContent(hiddensheet,0,cur,"产权单位");
		    sheet.addValidationData(ExcelUtil.setHiddenCell(cur, col, "产权单位", workbook, dictService.getDicMap("propertyComp"), hiddensheet));
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("数据质量责任人");
		    cur++;
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
	public void expData(StoneInfoBean obj, HttpServletRequest request,HttpServletResponse response){
		try{
			String caption = "标石数据";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();// 生成一个表格
			sheet.setDefaultColumnWidth(15);
			//得到标石所有的数据
			List<StoneInfoBean> list = this.getStone(obj);
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			int col=0;
			cell=row.createCell(col++);
		    cell.setCellValue("标石名称");
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    
		    cell=row.createCell(col++);
		    cell.setCellValue("标石方位");
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    
		    cell=row.createCell(col++);
		    cell.setCellValue("标石序号");
		    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
		    cell=row.createCell(col++);
		    cell.setCellValue("所属区域");
		    cell=row.createCell(col++);
		    cell.setCellValue("经度");
		    cell=row.createCell(col++);
		    cell.setCellValue("纬度");
		    cell=row.createCell(col++);
		    cell.setCellValue("产权性质");
		    cell=row.createCell(col++);
		    cell.setCellValue("产权单位");
		    cell=row.createCell(col++);
		    cell.setCellValue("数据质量责任人");
		    cell=row.createCell(col++);
		    cell.setCellValue("一线维护人");
		    cell=row.createCell(col++);
		    cell.setCellValue("创建时间");
		    cell=row.createCell(col++);
		    cell.setCellValue("创建人");
		    if(TextUtil.isNotNull(list)){
		    	for(int i=0;i<list.size();i++){
		    		StoneInfoBean stone = list.get(i);
		    		HSSFRow rows=sheet.createRow(i+1);
		    		ExcelUtil.createCell(rows, 0, TextUtil.isNull(stone.getStoneName()) ? " " : stone.getStoneName().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 1, TextUtil.isNull(stone.getStoneName()) ? " " : stone.getStoneName().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 2, TextUtil.isNull(stone.getStoneNum()) ? " " : stone.getStoneNum().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 3, TextUtil.isNull(stone.getStoneArea()) ? " " : stone.getStoneArea().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 4, TextUtil.isNull(stone.getLongitude()) ? " " : stone.getLongitude().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 5, TextUtil.isNull(stone.getLatitude()) ? " " : stone.getLatitude().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 6, TextUtil.isNull(stone.getPropertyNatureStr()) ? " " : stone.getPropertyNatureStr().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 7, TextUtil.isNull(stone.getPropertyCompStr()) ? " " : stone.getPropertyCompStr().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 8, TextUtil.isNull(stone.getDataQualitier()) ? " " : stone.getDataQualitier().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 9, TextUtil.isNull(stone.getMaintainer()) ? " " : stone.getMaintainer().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 10, TextUtil.isNull(stone.getCreateTime()) ? " " : stone.getCreateTime().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 11, TextUtil.isNull(stone.getCreater()) ? " " : stone.getCreater().toString(), ExcelUtil.getValueStyle(workbook));
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
				sb = new StringBuffer("INSERT into stoneInfo(stoneName,stonePosition,stoneNum,stoneArea,longitude,latitude,propertyNature,"
						+ "propertyComp,dataQualitier,maintainer,deleteflag,createTime,creater)"
						+ "VALUES(");
				row=sheet.getRow(i);
				for(int j=0; j<maxCell; j++){
					cell=row.getCell(j);
					switch(cell.getCellType()){
					 case HSSFCell.CELL_TYPE_NUMERIC:value=nf.format(cell.getNumericCellValue());break;
					 case HSSFCell.CELL_TYPE_STRING:value=cell.getStringCellValue(); break;
					 case HSSFCell.CELL_TYPE_BLANK:value=" ";break;
					}
					if(j ==1){
						value = dictService.getDicValue("stonePosition", value);
					}else if(j==6){
						value = dictService.getDicValue("propertyNature", value);
					}else if(j == 7){
						value = dictService.getDicValue("propertyComp", value);
					}
					
					if(j == maxCell -1){
						sb.append("'"+value+"','0','"+time+"','root'");
					}else{
						sb.append("'"+value+"',");
					}
				}
				sb.append(")");
				this.jdbcTemplate.execute(sb.toString());
				//this.jdbcTemplate.execute("select * from buriedInfo");
			}
			
			
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return jsonstr;
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
