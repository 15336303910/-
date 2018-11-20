package base.util;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFName;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;

public class ExcelUtil {

	/**
	 * 把文件导出下载
	 * @param caption
	 * @param workbook
	 * @param response
	 * @throws Exception
	 */
	public static void downloadFile(String caption,HSSFWorkbook workbook,HttpServletResponse response) throws Exception{
		response.reset();
		response.setHeader("Content-Disposition", "attachment;filename="+new String((caption+".xls").getBytes("gbk"),"iso-8859-1"));
		response.setContentType("application/octet-stream;charset=UTF-8");
		BufferedOutputStream bos=null;
		OutputStream  out=null;
		try{
			
			out=response.getOutputStream();
			bos=new BufferedOutputStream(out);
			workbook.write(bos);
			bos.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
			if(bos!=null)bos.close();
			if(out!=null)out.close();
		}
	}
	
	/**
	 * 这是表头的样式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook){
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
	    headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
	    headerStyle.setWrapText(false);
	    
	    //设置字体
	    HSSFFont headerfont = workbook.createFont();
	    headerfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	    headerfont.setFontName("宋体");
	    headerfont.setFontHeight((short) 300);
	    headerStyle.setFont(headerfont);
		return headerStyle;
	}
	
	/**
	 * 标题样式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getTitleStyle(HSSFWorkbook workbook){
		HSSFCellStyle cellStyle = workbook.createCellStyle();
	    cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格左对齐
	    cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	    cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	    cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
	    cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
	    cellStyle.setWrapText(true);// 指定单元格自动换行
	    
	    //设置标题字体
	    HSSFFont font = workbook.createFont();
	    font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
	    font.setFontName("宋体");
	    font.setFontHeight((short) 230);
	    cellStyle.setFont(font);
	    return cellStyle;
	}
	
	/**
	 * 值对应的样式
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle getValueStyle(HSSFWorkbook workbook){
		HSSFCellStyle valuestyle = workbook.createCellStyle();
	    valuestyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	    valuestyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	    valuestyle.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	    valuestyle.setBorderTop(HSSFCellStyle.BORDER_THIN); 
	    valuestyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
	    valuestyle.setWrapText(true);
	    
	    return valuestyle;
	}
	
	/**
	 * 创建一个隐藏的sheet
	 * @param workbook
	 * @param sheetname
	 * @return
	 */
	public static HSSFSheet createHiddenSheet(HSSFWorkbook workbook,String sheetname){
		  //拼接查询字段
		  HSSFSheet sheet=workbook.createSheet(sheetname);
		  workbook.setSheetHidden(workbook.getSheetIndex(sheetname), true);
		  return sheet;
	}
	
	/**
	 * 根据索引号获取当前列坐标
	 * @param ColNum
	 * @return
	 */
	public static  String ConvertToColName(int ColNum){  
	    int ll_mod;  
	    String ColName="";  
	    while (ColNum > 0){  
	        ll_mod = ColNum % 26;  
	        if(ll_mod==0)  {  
	            ColName="Z"+ColName;  
	            ll_mod = 1;  
	        }else{  
	            ColName=(char)(ll_mod + 64)+ColName;  
	        }  
	        ColNum = (ColNum - ll_mod) / 26;  
	     }   
	    return ColName;  
	} 
	
	
	/**
	 * 设置单元格引用
	 * @param key
	 * @param fistRow
	 * @param endRow
	 * @param fistCol
	 * @param endCol
	 * @param flag
	 * @return
	 */
	public static  HSSFDataValidation getdata_validation(String key,int fistRow,int endRow,int fistCol,int endCol,boolean flag){
		DVConstraint constraint;
		if(flag){
		     constraint = DVConstraint.createFormulaListConstraint(key);
		}else{
		     String[] explicitListValues=new String[0];
		     constraint=DVConstraint.createExplicitListConstraint(explicitListValues);
		}
		//绑定下拉框和作用区域   
		CellRangeAddressList regions = new CellRangeAddressList(fistRow,endRow,fistCol,endCol);   
		HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
		return data_validation;
	}
	
	/**
	 * 
	 * @param workbook
	 * @param keyName
	 * @param expression
	 * @param flag
	 */
	public static void setQuoteName(HSSFWorkbook workbook,String keyName,String expression, boolean flag){
		HSSFName refer = workbook.createName(); 
		if(flag){
		   refer.setRefersToFormula(expression);  
		}
		refer.setNameName(keyName);
	}
	
	/**
	 * 设置隐藏的标题
	 * @param sheet
	 * @param rowIndex
	 * @param colIndex
	 * @param value
	 */
	public static void  setContent(HSSFSheet sheet,int rowIndex,int colIndex,String value){
		HSSFRow row=sheet.getRow(rowIndex);
		if(row==null){
			row=sheet.createRow(rowIndex);
		}
		HSSFCell cell=row.createCell(colIndex);
		cell.setCellValue(new HSSFRichTextString(value));
	}
	
	/**  
	* 创建单元格  
	* @param row 行  
	* @param column 列位置  
	* @param value 值  
	* @param style 样式  
	*/  
	public static void createCell(HSSFRow row,int column,Object value,HSSFCellStyle style){  
	   HSSFCell cell = row.createCell((short)column);  
	   cell.setCellValue(String.valueOf(value));  
	   cell.setCellStyle(style);  
	}  
	
	/**
	 *  cell=row.createCell(col++);
        cell.setCellValue("在用状态");
        map=DicTypeUtil.getDicTypeById("VEHICEL_STATE");
        FileUtil.setContent(hiddensheet,0,cur,"在用状态");
        FileUtil.setContent(hiddensheet,0,cur,"所属专业");
	    sheet.addValidationData(this.setHiddenCell(cur, col, "在用状态", workbook, map, hiddensheet));
		cur++;
		this.setHiddenCell(cur, col, "所属专业", workbook, map, hiddensheet)
	 * 设置隐藏域值
	 * @param cur
	 * @param col
	 * @param name
	 * @param workbook
	 * @param map
	 * @param hiddensheet
	 * @return
	 */
	public static HSSFDataValidation setHiddenCell(int cur,int col,String name,HSSFWorkbook workbook,Map<String,String> map,HSSFSheet hiddensheet){
		int k=1;
	    for(Iterator<String> it=map.keySet().iterator();it.hasNext();){
	    	HSSFRow hiddenRow=hiddensheet.getRow(k);
		    if(hiddenRow==null){
		         hiddenRow=hiddensheet.createRow(k);
		    }
		    k++;
		    HSSFCell hiddencell=hiddenRow.createCell(cur);
	    	String mkey=it.next();
	    	//设置字典项
	    	hiddencell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    	hiddencell.setCellValue(new HSSFRichTextString(map.get(mkey)));
	    }
	    cur++;
	    setQuoteName(workbook,name,"hiddensheet!$"+ConvertToColName(cur)+"$2:$"+ConvertToColName(cur)+"$"+k,map.keySet().size()>0);
	    HSSFDataValidation data_validation = getdata_validation(name,1,65533,col-1,col-1,map.keySet().size()>0);
	    return data_validation;
	}
}
