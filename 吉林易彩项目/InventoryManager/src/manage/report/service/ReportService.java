package manage.report.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.jdbc.core.JdbcTemplate;

import edu.emory.mathcs.backport.java.util.LinkedList;
import manage.report.pojo.ReportBean;
import manage.report.service.impl.IreportService;
import base.database.DataBase;
import base.util.DateUtil;
import base.util.ExcelUtil;
import base.util.MapUtil;
import base.util.TextUtil;
import base.util.pojo.Point;

public class ReportService  extends DataBase implements IreportService{
	private JdbcTemplate jdbcTemplate;

	
	
	/**
	 * 得到各地市
	 * 的热点数据
	 * @return
	 */
	public List<Map<String, Object>> getCityHot(){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		try{
			String yesterday = DateUtil.getDateByNum(-1);
			//String yesterday ="2016-8-29";
			String sql = "select a.city as city , count(1) as num  from ("
					+ " select"
					+ " domainName,(select domainCode from sys_domain where domainId = t.parentId) as city"
					+ " from sys_domain t  where t.parentId !='1' "
					+ " ) a,"
					+ " (select t.userName,("
					+ " select domainName from sys_domain where domainCode = t.areaNo"
					+ " ) as area from log_login t where t.loginTime >= '"+yesterday+"' order by t.id desc"
					+ " ) b where a.domainName = b.area and a.city is not null "
					+ " group by a.city "
					+ "";
			list = this.jdbcTemplate.queryForList(sql);
			System.out.println(sql);
			for(Map<String, Object> map : list){
				String city = map.get("city").toString();
				map.put("city", getPropert.getValueByKey(city));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	
	/**
	 * 得到资源数据
	 * @param object
	 * @return
	 */
	public List<Map<String, Object>> getresList(ReportBean object){
		String sql = "";
		sql = this.getResSql(object);
		List<Map<String, Object>>  list = new ArrayList<Map<String,Object>>();
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	/**
	 * 得到资源总数
	 * @param object
	 * @return
	 */
	public Integer getResCount(ReportBean object){
		String sql = "";
		object.setStart(null);
		sql +="select count(1) from (";
		sql += this.getResSql(object);
		sql +=") b";
		Integer result = this.jdbcTemplate.queryForInt(sql);
		return result;
	}
	
	
	/**
	 * 导出所有的数据
	 * @param report
	 * @param request
	 * @param response
	 */
	public void expAllData(ReportBean report,HttpServletRequest request,HttpServletResponse response){
		try{
			String caption = "资源数据";
			HSSFWorkbook workbook = new HSSFWorkbook();
			String[] names ={"站点","机房","光交","电杆","杆路","人手井","管道","标石","直埋","撑点","挂墙"};
			String[] type ={"station","gener","optical","pole","poleLine","well","pipe","stone","buried","supportPoint","hangwall"};
			HSSFCellStyle cellStyle = ExcelUtil.getValueStyle(workbook);
			for(int z = 0;z<names.length;z++){
				HSSFSheet sheet=workbook.createSheet();// 生成一个表格
				workbook.setSheetName(z,names[z]);
				sheet.setDefaultColumnWidth(15);
				HSSFRow row=sheet.createRow(0);
				HSSFCell cell=row.createCell(0);
				cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
				int col=0;
				
				ReportBean obj = new ReportBean();
				obj.setResType(type[z]);
				obj.setResTime(report.getResTime());
				
				Map<String, Object> map = this.getResData(obj);
				List<String> head = (List<String>) map.get("head");
				List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
				//文件的头信息
				for(int i=0;i<head.size();i++){
					cell=row.createCell(col++);
				    cell.setCellValue(head.get(i));
				    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
				}
				if(TextUtil.isNotNull(data)){
					for(int i=0;i<data.size();i++){
						HSSFRow rows=sheet.createRow(i+1);
						Map<String, Object> dMap = data.get(i);
						int j=0;
						for (String key : dMap.keySet()) {
							ExcelUtil.createCell(rows, j++, TextUtil.isNull(dMap.get(key)) ? " " : dMap.get(key).toString(), cellStyle);
						}
					}
				}
				
			}
			ExcelUtil.downloadFile(caption, workbook, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出经纬度
	 * @param request
	 * @param response
	 */
	public void expCoord(HttpServletRequest request,HttpServletResponse response){
		try{
			String caption = "经纬度导出";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();// 生成一个表格
			sheet.setDefaultColumnWidth(15);
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			HSSFCellStyle cellStyle = ExcelUtil.getValueStyle(workbook);
			int col=0;
			String sql ="select longitude as lon,latitude as lat from wellinfo_1122";
			List<Map<String, Object>> data = this.jdbcTemplate.queryForList(sql);
			/*for(int i=0;i<data.size();i++){
				Map<String, Object> dMap = data.get(i);
				String lon = dMap.get("lon")+"";
				String lat = dMap.get("lat")+"";
			}*/
			if(TextUtil.isNotNull(data)){
				for(int i=0;i<data.size();i++){
					HSSFRow rows=sheet.createRow(i+1);
					Map<String, Object> dMap = data.get(i);
					int j=0;
					String lon = dMap.get("lon")+"";
					String lat = dMap.get("lat")+"";
					ExcelUtil.createCell(rows, 0, lon, cellStyle);
					ExcelUtil.createCell(rows, 1, lat, cellStyle);
					Point point = MapUtil.wgs_gcj_encrypts(Double.parseDouble(lat), Double.parseDouble(lon));
					ExcelUtil.createCell(rows, 2, point.getLng()+"", cellStyle);
					ExcelUtil.createCell(rows, 3, point.getLat()+"", cellStyle);
					Point wgsPoint = MapUtil.gcj_wgs_encrypts(point.getLng(),point.getLat());
					ExcelUtil.createCell(rows, 4, wgsPoint.getLng()+"", cellStyle);
					ExcelUtil.createCell(rows, 5, wgsPoint.getLat()+"", cellStyle);
					String dis = MapUtil.Distance(Double.parseDouble(lon), Double.parseDouble(lat), wgsPoint.getLng(), wgsPoint.getLat());
					ExcelUtil.createCell(rows, 6, dis+"", cellStyle);
				}
			}
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
	public void expData(ReportBean report,HttpServletRequest request,HttpServletResponse response){
		try{
			String caption = "资源数据";
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet=workbook.createSheet();// 生成一个表格
			sheet.setDefaultColumnWidth(15);
			HSSFRow row=sheet.createRow(0);
			HSSFCell cell=row.createCell(0);
			cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			HSSFCellStyle cellStyle = ExcelUtil.getValueStyle(workbook);
			int col=0;
			//得到数据
			Map<String, Object> map = this.getResData(report);
			List<String> head = (List<String>) map.get("head");
			List<Map<String, Object>> data = (List<Map<String, Object>>) map.get("data");
			//文件的头信息
			for(int i=0;i<head.size();i++){
				cell=row.createCell(col++);
			    cell.setCellValue(head.get(i));
			    cell.setCellStyle(ExcelUtil.getTitleStyle(workbook));
			}
			if(TextUtil.isNotNull(data)){
				for(int i=0;i<data.size();i++){
					HSSFRow rows=sheet.createRow(i+1);
					Map<String, Object> dMap = data.get(i);
					int j=0;
					for (String key : dMap.keySet()) {
						ExcelUtil.createCell(rows, j++, TextUtil.isNull(dMap.get(key)) ? " " : dMap.get(key).toString(), cellStyle);
					}
				}
			}
			
			ExcelUtil.downloadFile(caption, workbook, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到资源数据
	 * @param report
	 * @return
	 */
	Map<String, Object> getResData(ReportBean report){
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> head = new LinkedList();
		String sql ="";
		List<Map<String, Object>> data = new LinkedList();
		if(report.getResType().equals("station")){
			head.add("编号");head.add("地市");head.add("站名");head.add("经度");head.add("纬度");
			head.add("采集时间");head.add("处理人");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("optical")){
			head.add("编号");head.add("地市");head.add("光交名称");head.add("经度");head.add("纬度");
			head.add("采集时间");head.add("处理人");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("gener")){
			head.add("编号");head.add("地市");head.add("机房名称");head.add("经度");head.add("纬度");
			head.add("采集时间");head.add("处理人");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("pole")){
			head.add("编号");head.add("地市");head.add("电杆名称");head.add("经度");head.add("纬度");
			head.add("采集时间");head.add("处理人");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("well")){
			head.add("编号");head.add("地市");head.add("井名称");head.add("经度");head.add("纬度");
			head.add("采集时间");head.add("处理人");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("stone")){
			head.add("编号");head.add("地市");head.add("标石名称");head.add("经度");head.add("纬度");
			head.add("采集时间");head.add("处理人");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("poleLine")){
			head.add("编号");head.add("杆路名称");head.add("地市");
			head.add("起始杆");head.add("起始经度");head.add("起始纬度");
			head.add("终止杆");head.add("终止经度");head.add("终止纬度");
			head.add("计算长度");head.add("维护长度");
			head.add("处理人");head.add("采集时间");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("pipe")){
			head.add("编号");head.add("管道名称");head.add("地市");
			head.add("起始井");head.add("起始经度");head.add("起始纬度");
			head.add("终止井");head.add("终止经度");head.add("终止纬度");
			head.add("计算长度");head.add("维护长度");
			head.add("处理人");head.add("采集时间");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("buried")){
			head.add("编号");head.add("直埋名称");head.add("地市");
			head.add("起始标石");head.add("起始经度");head.add("起始纬度");
			head.add("终止标石");head.add("终止经度");head.add("终止纬度");
			head.add("计算长度");head.add("维护长度");
			head.add("处理人");head.add("采集时间");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("supportPoint")){
			head.add("编号");head.add("地市");head.add("撑点名称");head.add("经度");head.add("纬度");
			head.add("采集时间");head.add("处理人");head.add("资源标识");
			sql = this.getResSql(report);
		}else if(report.getResType().equals("hangwall")){
			head.add("编号");head.add("挂墙名称");head.add("地市");
			head.add("起始撑点");head.add("起始经度");head.add("起始纬度");
			head.add("终止撑点");head.add("终止经度");head.add("终止纬度");
			head.add("计算长度");head.add("维护长度");
			head.add("处理人");head.add("采集时间");head.add("资源标识");
			sql = this.getResSql(report);
		}
		data = this.jdbcTemplate.queryForList(sql);
		//data = this.getGcjList(data);
		map.put("head", head);
		map.put("data", data);
		return map;
	}
	
	/**
	 * 将现在的坐标
	 * 转换成GCJ坐标
	 * @param data
	 * @return
	 */
	List<Map<String, Object>> getGcjList(List<Map<String, Object>> data){
		for(Map<String, Object> map : data){
			//点资源
			if(map.get("lon")!=null){
				Point point = MapUtil.wgs_gcj_encrypts(Double.parseDouble(map.get("lat")+""), Double.parseDouble(map.get("lon")+""));
				map.put("lon", point.getLng()+"");
				map.put("lat", point.getLat()+"");
			}
			//段资源
			if(map.get("startLon") != null){
				Point point = MapUtil.wgs_gcj_encrypts(Double.parseDouble(map.get("startLat")+""), Double.parseDouble(map.get("startLon")+""));
				map.put("startLon", point.getLng()+"");
				map.put("startLat", point.getLat()+"");
			}
			if(map.get("endLon") !=null ){
				Point point = MapUtil.wgs_gcj_encrypts(Double.parseDouble(map.get("endLat")+""), Double.parseDouble(map.get("endLon")+""));
				map.put("endLon", point.getLng()+"");
				map.put("endLat", point.getLat()+"");
			}
		}
		return data;
	}
	
	/**
	 * 获取管孔信息
	 * @param object
	 * @return
	 */
	List<Map<String, Object>> getTubeNum(ReportBean object){
		List<Map<String, Object>> list = new LinkedList();
		String sql = " select p.pipeSegmentId as segId,p.pipeSegmentName as segName,"
				+ " count(1) as mnum,t.subTubeAmount as tnum from pipesegmentinfo p, tubeinfo t"
				+ " where p.pipeSegmentId =  t.pipeSegmentId and t.isfather =1  group by t.pipeSegmentId "
				+ "";
		if(TextUtil.isNotNull(object.getCity())){
			sql +=" and p.maintenanceAreaName like '%"+object.getCity()+"%'";
		}
		if(TextUtil.isNotNull(object.getResName())){
			sql +=" and poleLineSegmentName like '%"+object.getResName()+"%'";
		}
		list = this.jdbcTemplate.queryForList(sql);
		return list;
	}
	
	
	/**
	 * 得到查询语句
	 * @param object
	 * @return
	 */
	String getResSql(ReportBean object){
		StringBuffer sql = new StringBuffer();
		try{
			String time = "";
			if(TextUtil.isNotNull(object.getResTime())){
				time = object.getResTime();
				SimpleDateFormat sdf  =   new  SimpleDateFormat( "yyyy-MM-dd" ); 
				Date date = sdf.parse(object.getResTime());
				SimpleDateFormat sp=new SimpleDateFormat("y-M-d");
				time = sp.format(date);
			}else{
				time = DateUtil.getDateByNum(-7);
			}
			if(TextUtil.isNotNull(object.getCity())){
				object.setCity(this.getCity(object.getCity()));
			}
			
			//站点
			if(object.getResType().equals("station")){
				if(TextUtil.isNotNull(object.getStart())){
					sql.append("select * from (");
				}
				sql.append("select stationBaseId as id ,"
						+ " region as region ,stationName as name , "
						+ " lon as lon,lat as lat,date_format(lastUpdateDate,'%Y-%c-%d %h:%i:%s') as dealtime,"
						+ " parties as dealer,resNum as resNum"
						+ " from job_stationbase"
						+ " where lastUpdateDate >= '"+time+"'");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and region like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and stationName like '%"+object.getResName()+"%'");
				}
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +")b ");
				}
			}
			//光交
			if(object.getResType().equals("optical")){
				if(TextUtil.isNotNull(object.getStart())){
					sql.append("select * from (");
				}
				sql.append("select id as id,eaddr as region,ename as name,"
						+ " lon as lon,lat as lat,date_format(MTIME,'%Y-%c-%d %h:%i:%s') as dealtime,"
						+ " parties as dealer,resNum as resNum"
						+ " from job_equtinfo where ETYPE =3"
						+ " and mtime >='"+time+"'");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and eaddr like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and ename like '%"+object.getResName()+"%'");
				}
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +")b ");
				}
			}
			//机房
			if(object.getResType().equals("gener")){
				if(TextUtil.isNotNull(object.getStart())){
					sql.append("select * from (");
				}
				sql.append("select generatorId as id , region as region,  generatorName as name ,"
						+ " lon as lon , lat as lat,date_format(lastUpdateDate,'%Y-%c-%d %h:%i:%s') as dealtime,"
						+ " dataQualityPrincipal as dealer , resNum as resNum"
						+ " from job_generator where lastUpdateDate >= '"+time+"' ");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and region like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and generatorName like '%"+object.getResName()+"%'");
				}
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +")b ");
				}
			}
			//人手井
			if(object.getResType().equals("well")){
				if(TextUtil.isNotNull(object.getStart())){
					sql.append("select * from (");
				}
				sql.append("select distinct id, region,name,lon,lat,dealtime,"
						+ " dealer,resNum"
						+ " from ("
						+ " select wellId as id, region as region ,wellName as name ,"
						+ " longitude as lon,latitude as lat,date_format(creationDate,'%Y-%c-%d %h:%i:%s') as dealtime,"
						+ " parties as dealer, resNum as resNum"
						+ " from wellinfo where DATE_FORMAT(creationDate,'%Y-%C-%D') >='"+time+"' "
						+ " and lastUpdateDate is null and deletedFlag ='0'");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and region like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and wellName like '%"+object.getResName()+"%'");
				}
				sql.append(" union all"
						+ " select wellId as id, region as region ,wellName as name ,"
						+ " longitude as lon,latitude as lat,date_format(lastUpdateDate,'%Y-%c-%d %h:%i:%s') as dealtime,"
						+ " parties as dealer, resNum as resNum"
						+ " from wellinfo where DATE_FORMAT(lastUpdateDate,'%Y-%C-%D') >='"+time+"' and lastUpdateDate is not null"
						+ " and deletedFlag ='0'");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and region like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and wellName like '%"+object.getResName()+"%'");
				}
				sql.append(" ) res "
						+ "");
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +")b ");
				}
			}
			//电杆
			if(object.getResType().equals("pole")){
				if(TextUtil.isNotNull(object.getStart())){
					sql.append("select * from (");
				}
				sql.append("select distinct id, region, name,lon,lat,dealtime,dealer,resNum"
						+ " from ("
						+ " select poleId as id ,region as region ,poleName  as name ,"
						+ " poleLongitude as lon,poleLatitude as lat,"
						+ " date_format(creationDate,'%Y-%c-%d %h:%i:%s') as dealtime ,"
						+ " parties as dealer,resNum as resNum"
						+ " from poleinfo where creationDate >= '"+time+"' and lastUpdateDate is null ");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and region like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and poleName like '%"+object.getResName()+"%'");
				}
				sql.append(" and deletedFlag ='0' "
						+ " union all"
						+ " select poleId as id ,region as region ,poleName  as name ,"
						+ " poleLongitude as lon,poleLatitude as lat,"
						+ " date_format(lastUpdateDate,'%Y-%c-%d %h:%i:%s') as dealtime ,"
						+ " parties as dealer,resNum as resNum"
						+ " from poleinfo where lastUpdateDate >= '"+time+"' and lastUpdateDate is not null "
						+ " ");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and region like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and poleName like '%"+object.getResName()+"%'");
				}
				sql.append(" and deletedFlag ='0' ) res ");
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +")b ");
				}
			}
			//标石
			if(object.getResType().equals("stone")){
				if(TextUtil.isNotNull(object.getStart())){
					sql.append("select * from (");
				}
				sql.append(" select distinct id, region, name,lon,lat,dealtime,dealer,resNum"
						+ " from ( "
						+ " select stoneId as id,stoneArea as region,"
						+ " stoneName as name,longitude as lon,latitude as lat,"
						+ " date_format(createTime,'%Y-%c-%d %h:%i:%s') as dealtime,"
						+ " dataQualitier as dealer,resNum as resNum"
						+ " from stoneinfo"
						+ " where DATE_FORMAT(createTime,'%Y-%C-%D') >='"+time+"' and lastUpTime is null and deleteflag ='0'"
						+ " ");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and stoneArea like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and stoneName like '%"+object.getResName()+"%'");
				}
				sql.append(" union all"
						+ " select stoneId as id,stoneArea as region,"
						+ " stoneName as name,longitude as lon,latitude as lat,"
						+ " date_format(lastUpTime,'%Y-%c-%d %h:%i:%s') as dealtime,"
						+ " dataQualitier as dealer,resNum as resNum"
						+ " from stoneinfo"
						+ " where DATE_FORMAT(lastUpTime,'%Y-%C-%D') >='2016-8-22' and lastUpTime is not null "
						+ " and deleteflag ='0'");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and stoneArea like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and stoneName like '%"+object.getResName()+"%'");
				}
				sql.append(" ) res");
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +")b ");
				}
			}
			//杆路
			if(object.getResType().equals("poleLine")){
				sql.append("select distinct b.poleLineSegmentId as id ,b.maintenanceAreaName as region ,"
						+ " b.poleLineSegmentName as name,"
						+ " (select poleName from poleinfo where poleId = b.startDeviceId) as startRes,"
						+ " (select poleLongitude from poleinfo where poleId = b.startDeviceId) as startLon,"
						+ " (select poleLatitude from poleinfo where poleId = b.startDeviceId) as startLat,"
						+ " (select poleName from poleinfo where poleId = b.endDeviceId) as endRes,"
						+ " (select poleLongitude from poleinfo where poleId = b.endDeviceId) as endLon,"
						+ " (select poleLatitude from poleinfo where poleId = b.endDeviceId) as endLat,"
						+ " b.poleLineSegmentLength as segLength,b.maintainLength as maintainLength,"
						+ " b.parties as dealer,date_format(b.dealTime,'%Y-%c-%d %h:%i:%s') as dealtime, b.resNum as resNum "
						+ " from ("
						+ "");
				sql.append(""
						+ " select poleLineSegmentId,poleLineSegmentName,maintenanceAreaName,startDeviceId,"
						+ " endDeviceId,poleLineSegmentLength,maintainLength,parties,creationDate as dealTime,"
						+ " resNum as resNum"
						+ " from polelinesegmentinfo t where t.deletedFlag ='0' and t.creationDate >='"+time+"' and t.lastUpdateDate is null "
						+ "");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and maintenanceAreaName like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and poleLineSegmentName like '%"+object.getResName()+"%'");
				}
				sql.append(""
						+ " union all "
						+ " select poleLineSegmentId,poleLineSegmentName,maintenanceAreaName,startDeviceId,"
						+ " endDeviceId,poleLineSegmentLength,maintainLength,parties,lastUpdateDate as dealTime,"
						+ " resNum as resNum "
						+ " from polelinesegmentinfo t where t.deletedFlag ='0' and t.lastUpdateDate >='"+time+"' and t.lastUpdateDate is not null "
						+ "");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and maintenanceAreaName like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and poleLineSegmentName like '%"+object.getResName()+"%'");
				}
				sql.append(" )b ");
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" "
							+ " limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +"");
				}
			}
			//管道
			if(object.getResType().equals("pipe")){
				sql.append("select distinct b.pipeSegmentId as id , b.maintenanceAreaName as region,b.pipeSegmentName as name ,"
						+ " (select wellName from wellinfo where wellId = b.startDeviceId) as startRes, "
						+ " (select longitude from wellinfo where wellId = b.startDeviceId) as startLon,"
						+ " (select latitude from wellinfo where wellId = b.startDeviceId) as startLat,"
						+ " (select wellName from wellinfo where wellId = b.endDeviceId) as endRes,"
						+ " (select longitude from wellinfo where wellId = b.endDeviceId) as endLon,"
						+ " (select latitude from wellinfo where wellId = b.endDeviceId) as endLat,"
						+ " b.pipeSegmentLength as segLength , b.maintainLength as maintainLength,"
						+ " b.parties as dealer, date_format(b.dealTime,'%Y-%c-%d %h:%i:%s') as dealTime,b.resNum "
						+ " from ("
						+ "");
				sql.append(" select "
						+ " t.pipeSegmentId,t.pipeSegmentName,t.maintenanceAreaName,"
						+ " t.startDeviceId,t.endDeviceId,"
						+ " t.pipeSegmentLength , t.maintainLength ,"
						+ " t.parties , t.creationDate as dealTime,t.resNum"
						+ " from pipesegmentinfo t where t.deletedFlag ='0'"
						+ " and date_format(t.creationDate,'%Y-%C-%D %h:%i:%s') >='"+time+"' and t.lastUpdateDate is null "
						+ " ");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and maintenanceAreaName like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and pipeSegmentName like '%"+object.getResName()+"%'");
				}
				sql.append(" union all"
						+ " select"
						+ " t.pipeSegmentId,t.pipeSegmentName,t.maintenanceAreaName,"
						+ " t.startDeviceId,t.endDeviceId,"
						+ " t.pipeSegmentLength , t.maintainLength ,"
						+ " t.parties , t.lastUpdateDate as dealTime,t.resNum"
						+ " from pipesegmentinfo t where t.deletedFlag ='0'"
						+ " and date_format(t.lastUpdateDate,'%Y-%C-%D %h:%i:%s') >='"+time+"' and t.lastUpdateDate is not null ");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and maintenanceAreaName like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and pipeSegmentName like '%"+object.getResName()+"%'");
				}
				sql.append(" ) b ");
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" "
							+ " limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +"");
				}
			}
			//直埋
			if(object.getResType().equals("buried")){
				sql.append("select distinct b.id as id,b.buriedPartArea as region,b.buriedPartName as name,"
						+ " (select stoneName from stoneinfo where stoneId = b.buriedPartStartId) as startRes,"
						+ " (select longitude from stoneinfo where stoneId = b.buriedPartStartId) as startLon,"
						+ " (select latitude from stoneinfo where stoneId = b.buriedPartStartId) as startLat,"
						+ " (select stoneName from stoneinfo where stoneId = b.buriedPartEndId) as endRes,"
						+ " (select longitude from stoneinfo where stoneId = b.buriedPartEndId) as endLon,"
						+ " (select latitude from stoneinfo where stoneId = b.buriedPartEndId) as endLat,"
						+ " b.buriedPartLength as segLength, b.maintainLength as maintainLength,"
						+ " b.creater as dealer,date_format(b.dealTime,'%Y-%c-%d %h:%i:%s') as dealtime,b.resNum"
						+ " from (");
				sql.append(" select t.id,t.buriedPartName ,buriedPartArea ,buriedPartStartId,buriedPartEndId,"
						+ " t.buriedPartLength ,t.maintainLength,"
						+ " t.creater ,t.createTime as dealTime, t.resNum "
						+ " from buriedpartinfo t where t.deleteflag ='0'"
						+ " and DATE_FORMAT(t.createTime,'%Y-%C-%D') >='"+time+"' and t.lastUpTime is null ");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and buriedPartArea like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and buriedPartName like '%"+object.getResName()+"%'");
				}
				sql.append(" union all"
						+ " select t.id,t.buriedPartName ,buriedPartArea ,buriedPartStartId,buriedPartEndId,"
						+ " t.buriedPartLength ,t.maintainLength,"
						+ " t.creater ,t.lastUpTime as dealTime, t.resNum"
						+ " from buriedpartinfo t where t.deleteflag ='0' "
						+ " and DATE_FORMAT(t.lastUpTime,'%Y-%C-%D') >='"+time+"' and t.lastUpTime is not null ");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and buriedPartArea like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and buriedPartName like '%"+object.getResName()+"%'");
				}
				sql.append(" ) b ");
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" "
							+ " limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +"");
				}
			}
			//撑点
			if(object.getResType().equals("supportPoint")){
				if(TextUtil.isNotNull(object.getStart())){
					sql.append("select * from (");
				}
				sql.append(" select distinct id, region, name,lon,lat,dealtime,dealer,resNum"
						+ " from ( "
						+ " select id as id,maintArea as region,"
						+ " sportName as name,longitude as lon,latitude as lat,"
						+ " date_format(createTime,'%Y-%c-%d %h:%i:%s') as dealtime,"
						+ " maintainer as dealer,resNum as resNum"
						+ " from job_support_point"
						+ " where DATE_FORMAT(createTime,'%Y-%C-%D') >='"+time+"' and deleteflag ='0' and lastUpTime is null "
						+ " ");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and maintArea like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and sportName like '%"+object.getResName()+"%'");
				}
				sql.append(" union all"
						+ " select id as id,maintArea as region,"
						+ " sportName as name,longitude as lon,latitude as lat,"
						+ " date_format(lastUpTime,'%Y-%c-%d %h:%i:%s') as dealtime,"
						+ " maintainer as dealer,resNum as resNum"
						+ " from job_support_point"
						+ " where DATE_FORMAT(lastUpTime,'%Y-%C-%D') >='2016-8-22' and lastUpTime is not null "
						+ " and deleteflag ='0'");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and maintArea like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and sportName like '%"+object.getResName()+"%'");
				}
				sql.append(" ) res");
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +")b ");
				}
			}
			//挂墙
			if(object.getResType().equals("hangwall")){
				sql.append("select distinct b.id as id ,b.maintArea as region ,"
						+ " b.hangWallName as name,"
						+ " (select sportName from job_support_point where id = b.startDeviceId) as startRes,"
						+ " (select longitude from job_support_point where id = b.startDeviceId) as startLon,"
						+ " (select latitude from job_support_point where id = b.startDeviceId) as startLat,"
						+ " (select sportName from job_support_point where id = b.endDeviceId) as endRes,"
						+ " (select longitude from job_support_point where id = b.endDeviceId) as endLon,"
						+ " (select latitude from job_support_point where id = b.endDeviceId) as endLat,"
						+ " b.hangLength as segLength,b.maintLength as maintainLength,"
						+ " b.maintainer as dealer,date_format(b.dealTime,'%Y-%c-%d %h:%i:%s') as dealtime, b.resNum as resNum "
						+ " from ("
						+ "");
				sql.append(""
						+ " select id,hangWallName,maintArea,startDeviceId,"
						+ " endDeviceId,hangLength,maintLength,maintainer,createTime as dealTime,"
						+ " resNum as resNum"
						+ " from hang_wall t where t.deleteflag =0 and t.createTime >='"+time+"'"
						+ "");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and maintArea like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and hangWallName like '%"+object.getResName()+"%'");
				}
				sql.append(""
						+ " union all "
						+ " select id,hangWallName,maintArea,startDeviceId,"
						+ " endDeviceId,hangLength,maintLength,maintainer,createTime as dealTime,"
						+ " resNum as resNum"
						+ " from hang_wall t where t.deleteflag =0 and t.createTime >='"+time+"'"
						+ "");
				if(TextUtil.isNotNull(object.getCity())){
					sql.append(" and maintArea like '%"+object.getCity()+"%'");
				}
				if(TextUtil.isNotNull(object.getResName())){
					sql.append(" and hangWallName like '%"+object.getResName()+"%'");
				}
				sql.append(" ) b ");
				if(TextUtil.isNotNull(object.getStart())){
					sql.append(" "
							+ " limit "+object.getStart() +", "+(object.getStart() + object.getLimit()) +"");
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return sql.toString();
	}
	
	
	String getCity(String city){
		String result  = "";
		if(city.length() > 2 && city.length() <=5){
			result = city.substring(0,2);
		}else if(city.length() > 5){
			result = city.substring(0,3);
		}else {
			result = city;
		}
		return result;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
