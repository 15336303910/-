package manage.opticalTerminal.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import base.util.CommonUtil;
import base.util.ExcelUtil;
import base.util.TextUtil;
import base.util.md5;
import manage.dictionary.service.impl.IdictService;
import manage.equt.pojo.ODMInfoBean;
import manage.main.pojo.MenuInfoBean;
import manage.opticalTerminal.pojo.OpticalTerminalObj;
import manage.opticalTerminal.service.impl.IopticalTerminalService;
import manage.user.pojo.RoleInfoBean;
import manage.user.pojo.UserInfoBean;

public class OpticalTerminalService extends DataBase implements IopticalTerminalService{
	private IdictService dictService;
	private JdbcTemplate jdbcTemplate;
	private static String opticalTerGrid ="opticalTer.getOpticalTerGrid";
	private static String opticalTerCount = "opticalTer.getOpticalTerCount";
	private static String getOpticalTer = "opticalTer.getOpticalTer";
	private static String updateOpticalTer = "opticalTer.updateOpticalTer";
	private static String insertOpticalTer = "opticalTer.insertOpticalTer";
	private static String delOpticalTer = "opticalTer.delOpticalTer";
	/**
	 * 得到查询
	 * @param obj
	 * @param start
	 * @param length
	 * @return
	 */
	public List<OpticalTerminalObj> getOpticalTerGrid(OpticalTerminalObj obj){
		if(TextUtil.isNotNull(obj.getTerminalName())){
			String terName = obj.getTerminalName().trim();
			if(terName.contains(" ")){
				terName= terName.replaceAll(" +", "%");
			}
			obj.setTerminalName("%"+terName+"%");
		}
		List<OpticalTerminalObj> list = getObjects(opticalTerGrid, obj);
		return list;
	}
	
	/**
	 * 得到光终端盒实例
	 * @param obj
	 * @return
	 */
	public OpticalTerminalObj getOptTerObj(OpticalTerminalObj obj){
		OpticalTerminalObj opt = (OpticalTerminalObj) getObject(getOpticalTer, obj);
		return opt;
	}
	
	
	/**
	 * 得到光终端盒查询
	 * @param obj
	 * @return
	 */
	public List<OpticalTerminalObj> getOpticalTer(OpticalTerminalObj obj){
		List<OpticalTerminalObj> list = getObjects(getOpticalTer, obj);
		return list;
	}
	
	
	/**
	 * 得到数据
	 * @param obj
	 * @return
	 */
	public int getOpticalTerCount(OpticalTerminalObj obj){
		return getCount(opticalTerCount, obj);
	}
	
	/**
	 * 修改光终端盒
	 * @param obj
	 * @return
	 */
	public int updateOpticalTer(OpticalTerminalObj obj){
		return this.update(updateOpticalTer, obj);
	}
	
	/**
	 * 插入光终端盒
	 * @param obj
	 * @return
	 */
	public int insertOpticalTer(OpticalTerminalObj obj){
		return (Integer) this.insert(insertOpticalTer, obj);
	}
	
	
	/**
	 * 删除光终端盒
	 * @param ids
	 */
	public void delOpticalTer(String ids){
		this.delete(delOpticalTer, ids);
	}
	
	
	/**
	 * 导出数据模板
	 * @param request
	 * @param response
	 */
	public void expTemp(HttpServletRequest request,HttpServletResponse response){
		try{
			String caption = "光终端盒数据模板";
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
		    cell.setCellValue("光终端盒名称");
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("经度");
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("纬度");
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("行数");
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("列数");
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("归属点名称");
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("归属点类型");
		    ExcelUtil.setContent(hiddensheet,0,cur,"归属点类型");
		    Map<String, String> map = dictService.getDicMap("attachType");
		    sheet.addValidationData(ExcelUtil.setHiddenCell(cur, col, "归属点类型", workbook, map, hiddensheet));
		    cur++;
		    cell=row.createCell(col++);
		    cell.setCellValue("设备地址");
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
	public void expData(OpticalTerminalObj obj,HttpServletRequest request,HttpServletResponse response){
		try{
			String caption = "光终端盒数据";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();// 生成一个表格
			sheet.setDefaultColumnWidth(15);
			//得到光终端盒所有导出数据
			List<OpticalTerminalObj> list = this.getOpticalTer(obj);
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			int col=0;
			cell=row.createCell(col++);
		    cell.setCellValue("光终端盒名称");
		    cell=row.createCell(col++);
		    cell.setCellValue("经度");
		    cell=row.createCell(col++);
		    cell.setCellValue("纬度");
		    cell=row.createCell(col++);
		    cell.setCellValue("行数");
		    cell=row.createCell(col++);
		    cell.setCellValue("列数");
		    cell=row.createCell(col++);
		    cell.setCellValue("归属点名称");
		    cell=row.createCell(col++);
		    cell.setCellValue("归属点类型");
		    cell=row.createCell(col++);
		    cell.setCellValue("设备地址");
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
		    		OpticalTerminalObj object = list.get(i);
		    		HSSFRow rows=sheet.createRow(i+1);
		    		ExcelUtil.createCell(rows, 0, TextUtil.isNull(object.getTerminalName()) ? " " : object.getTerminalName().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 1, TextUtil.isNull(object.getLongitude()) ? " " : object.getLongitude().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 2, TextUtil.isNull(object.getLatitude()) ? " " : object.getLatitude().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 3, TextUtil.isNull(object.getRowNum()) ? " " : object.getRowNum().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 4, TextUtil.isNull(object.getColNum()) ? " " : object.getColNum().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 5, TextUtil.isNull(object.getAttachName()) ? " " : object.getAttachName().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 6, TextUtil.isNull(object.getAttachTypeStr()) ? " " : object.getAttachTypeStr().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 7, TextUtil.isNull(object.getTerminalAddres()) ? " " : object.getTerminalAddres().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 8, TextUtil.isNull(object.getDataQualitier()) ? " " : object.getDataQualitier().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 9, TextUtil.isNull(object.getMaintainer()) ? " " : object.getMaintainer().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 10, TextUtil.isNull(object.getCreateTime()) ? " " : object.getCreateTime().toString(), ExcelUtil.getValueStyle(workbook));
		    		ExcelUtil.createCell(rows, 11, TextUtil.isNull(object.getCreater()) ? " " : object.getCreater().toString(), ExcelUtil.getValueStyle(workbook));
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
	    	HSSFSheet sheet = null;
	    	for(int j =0;j<workBook.getNumberOfSheets();j++){
	    		sheet=workBook.getSheetAt(j);
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
					row=sheet.getRow(i);
					cell=row.getCell(0);
					UserInfoBean userInfo = new UserInfoBean();
					userInfo.setAreaName(getCellValue(row.getCell(0)));
					userInfo.setRealname(getCellValue(row.getCell(1)));
					userInfo.setUsername(getCellValue(row.getCell(2)));
					userInfo.setPassword(getCellValue(row.getCell(3)));
					userInfo.setPhoneNumber(getCellValue(row.getCell(4)));
					userInfo.setModulestr("0100000000000000000000000000000011000000000000000000000000000000");
					userInfo.setPowerstr("3201,32,03,25,02,23,12,01,62,06,5701,57,05,5704,5707,5503,55,5506,5201,52,5302,53,5601,56,3101,31,5802,58,27,291,71,07,21,26,11,63,5702,5705,5501,5504,5101,51,5202,5401,54,5602,3102,5803,28,292,24,22,61,41,04,3202,5703,5706,5502,5505,5102,5301,5402,33,5801,5804,29");
					this.addUser(userInfo);
				}
	    	}
			
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return jsonstr;
	}
	
	/**
	 * 增加用户
	 * @param user
	 */
	void addUser(UserInfoBean user){
		user.setPassword(md5.strToMD5(user.getPassword()));
		RoleInfoBean role = new RoleInfoBean();
		role.setRoleName(user.getAreaName()+"组");
	    role = (RoleInfoBean)getObject("user.getRoleList", role);
	    user.setRoleId(role.getRoleId());
	    user.setAreano(role.getAreano());
	    String[] powers = user.getPowerstr().split(",");
	    int uid = ((Integer)insert("user.insertNewUwer", user)).intValue();
	     List menus = new ArrayList();
	     for (int i = 0; i < powers.length; ++i) {
	       MenuInfoBean power = new MenuInfoBean();
	       power.setUserId(Integer.valueOf(uid));
	       power.setCode(powers[i].trim());
	       menus.add(power);
	       MenuInfoBean parent = (MenuInfoBean)getObject("user.getParentMenu", power);
	       if(parent!=null && parent.getCode()!=null ){
	    	   if (CommonUtil.noHasMenu(menus, parent)) {
	    	         parent.setUserId(Integer.valueOf(uid));
	    	         menus.add(parent);
	    	         if (Integer.parseInt(parent.getCode()) > 10) {
	    	           power = new MenuInfoBean();
	    	           power.setCode(parent.getCode());
	    	           parent = new MenuInfoBean();
	    	           parent = (MenuInfoBean)getObject("user.getParentMenu", power);
	    	           if (CommonUtil.noHasMenu(menus, parent)) {
	    	             parent.setUserId(Integer.valueOf(uid));
	    	             menus.add(parent);
	    	           }
	    	         }
	    	       }
	       }
	     }
	     batchInsert("user.insertUserPowers", menus);
	}
	
	String getCellValue(HSSFCell cell){
		String value = "";
		NumberFormat nf=NumberFormat.getNumberInstance(Locale.CHINA);
		nf.setGroupingUsed(false);
		nf.setMaximumFractionDigits(0);
		switch(cell.getCellType()){
		 case HSSFCell.CELL_TYPE_NUMERIC:value=nf.format(cell.getNumericCellValue());break;
		 case HSSFCell.CELL_TYPE_STRING:value=cell.getStringCellValue(); break;
		 case HSSFCell.CELL_TYPE_BLANK:value=" ";break;
		}
		return value;
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
