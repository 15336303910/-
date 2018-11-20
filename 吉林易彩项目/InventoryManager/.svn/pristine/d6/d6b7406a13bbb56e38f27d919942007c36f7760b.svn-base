package manage.images.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import base.database.DataBase;
import base.util.TextUtil;
import manage.images.pojo.ResourceImage;
import manage.images.service.ResourceImageService;

public class ResourceImageServiceImpl extends DataBase implements ResourceImageService{
	
	private static String insert_res = "resourceImage.insertResImage";
	private static String get_res ="resourceImage.getResourceImage";
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 保存数据
	 * @param resImage
	 * @return
	 */
	public int insertResImage(ResourceImage resImage){
		int id = (Integer) insert(insert_res, resImage);
		return id;
	}
	
	
	/**
	 * 得到图片数据
	 * @param resImage
	 * @return
	 */
	public List<ResourceImage> getResImage(ResourceImage resImage){
		List<ResourceImage> resImageList = getObjects(get_res, resImage);
		return resImageList;
	}
	
	/**
	 * 执行删除
	 * @param imag
	 */
	public void delResImage(ResourceImage imag) {
		String sql = "";
		if(TextUtil.isNotNull(imag.getId())) {
			sql ="delete from resource_images where Id = '"+imag.getId()+"'";
		}else if(TextUtil.isNotNull(imag.getType()) && TextUtil.isNotNull(imag.getResourceId())) {
			sql ="delete from resource_images where type='"+imag.getType()+"'"
					+ " and resourceId = '"+imag.getResourceId()+"'";
		}
		if(TextUtil.isNotNull(sql)) {
			this.jdbcTemplate.execute(sql);
		}
	}
	
	/**
	 * 得到资源信息
	 * @param fileName
	 * @return
	 */
	public String getResStr(String fileName){
		String resId = "";
		 String[] names = fileName.split("_");
		String sql = "";
		if(fileName.contains("s")){
			//站点
			sql="select resNum from job_stationbase"
				+ " where stationBaseId ='"+names[names.length-2]+"'";
		}
		if(fileName.contains("g")){
			//机房
			sql="select resNum from job_generator"
			   + " where generatorId ='"+names[names.length-2]+"'";
		}
		if(fileName.contains("pole")){
			//电杆
			sql ="select resNum from poleinfo"
				+ " where poleId ='"+names[names.length-2]+"'";
		}
		if(fileName.contains("w") || fileName.contains("well")){
			//人手井
			sql = "select resNum from wellinfo"
				+ " where wellId ='"+names[names.length-2]+"'";
		}
		if(fileName.contains("joint")){
			//接头盒
			sql = "select resNum from job_joint"
				+ " where jointId ='"+names[names.length-2]+"'";
		}
		if(fileName.contains("support")){
			//撑点
			sql = "select resNum from"
				+ " job_support_point where id ='"+names[names.length-2]+"'";
		}
		if(fileName.contains("EIU")){
			sql = "select resNum from job_equtinfo"
				+ " where EID='"+names[names.length-3]+"_"+names[names.length-2]+"'";
		}
		//查询
		List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
		if(TextUtil.isNotNull(list)){
			Map<String, Object> map= list.get(0);
			if((map.get("resNum")+"").equals("null")){
				resId ="";
			}else{
				resId = map.get("resNum")+"";
			}
		}
		return resId;
	}
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
