package interfaces.pdainterface.pipe.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.DirectionJudge;
import base.util.MapUtil;
import base.util.TextUtil;
import interfaces.pdainterface.pipe.service.PDAPipeCustomService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import manage.pipe.pojo.FaceInfoBean;
import manage.pipe.pojo.PipeSegCustomInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.WellCustomInfoBean;
import manage.pipe.pojo.WellInfoBean;

public class PDAPipeCustomServiceImpl extends DataBase implements PDAPipeCustomService {
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WellCustomInfoBean> getWellCustom(WellCustomInfoBean well) throws DataAccessException {
		if (TextUtil.isNotNull(well.getInt_id())) {
			well.setLone(null);
			well.setLate(null);
			well.setLongitude(null);
			well.setLons(null);
			well.setLatitude(null);
			well.setLats(null);
		}

		if (TextUtil.isNotNull(well.getZh_label())) {
			String wellName = well.getZh_label();
			if (wellName.contains(" ")) {
				wellName = wellName.replaceAll(" +", "%");
			}
			well.setZh_label("%" + wellName + "%");
		}
		return getObjects("pdapipe.getWellCustom", well);
	}

	@Override
	public Integer insertWellCustom(WellCustomInfoBean well) throws DataAccessException {
		WellCustomInfoBean we = new WellCustomInfoBean();
		we.setZh_label(well.getZh_label());
		we = (WellCustomInfoBean) getObject("pdapipe.checkWellCustomName", we);
		if (we == null) {
			int wellId = ((Integer) insert("pdapipe.insertWellCustom", well)).intValue();
			List mian = new ArrayList();
			mian.add("西北");
			mian.add("北");
			mian.add("东北");
			mian.add("东");
			mian.add("东南");
			mian.add("南");
			mian.add("西南");
			mian.add("西");
			int i = 0;
			while (true) {
				FaceInfoBean face = new FaceInfoBean();
				face.setCols(Integer.valueOf(1));
				face.setRows(Integer.valueOf(1));
				face.setWellId(Integer.valueOf(wellId));
				face.setLocationNo(mian.get(i).toString());
				insert("pdapipe.insertFace", face);

				++i;
				if (i >= mian.size()) {
					return Integer.valueOf(wellId);
				}
			}
		} else {
			return Integer.valueOf(-1);
		}
	}

	@Override
	public Integer updateWellCustom(WellCustomInfoBean well) throws DataAccessException {
		WellCustomInfoBean we = new WellCustomInfoBean();
	     we.setZh_label(well.getZh_label());
	     we = (WellCustomInfoBean)getObject("pdapipe.checkWellCustomName", we);
	     //代表修改当前井
	     if(we == null ||  we.getInt_id().equals(well.getInt_id())) {
	    	 return Integer.valueOf(update("pdapipe.updateWellCustom", well));
	     }else {
	    	 //代表存在重复
	    	 return Integer.valueOf(-1);
	     }
	}

	@Override
	public Integer deleteWellCustom(WellCustomInfoBean well) throws DataAccessException {
		return Integer.valueOf(update("pdapipe.deleteWellCustom", well));
	}

	@Override
	public PipeSegCustomInfoBean setPipeSegLength(PipeSegCustomInfoBean pipeseg) {
		String sql ="select longitude as lon,latitude as lat from"
		  		+ " rms_landmark where int_id in ('"+pipeseg.getDest_point_name()+"','"+pipeseg.getOrig_point_name()+"')";
	   List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
	   Map<String, Object> start = list.get(0);
	   Map<String, Object> end = list.get(1);
	   String distinct = MapUtil.Distance(Double.parseDouble(start.get("lon")+""),
				  	Double.parseDouble(start.get("lat")+""),
				  	Double.parseDouble(end.get("lon")+""),
				  	Double.parseDouble(end.get("lat")+""));
		pipeseg.setLength(distinct);
	   return  pipeseg;
	}

	@Override
	public Integer insertPipesegCustom(PipeSegCustomInfoBean pipeseg) throws DataAccessException {
		PipeSegCustomInfoBean ps = new PipeSegCustomInfoBean();
	     ps.setZh_label(pipeseg.getZh_label());
	     ps = (PipeSegCustomInfoBean)getObject("pdapipe.checkPipesegNameCustom", ps);
	     if (ps == null) {
	       int id = ((Integer)insert("pdapipe.insertPipeSegCustom", pipeseg)).intValue();
	       return Integer.valueOf(id);
	     }else{
	    	 return Integer.valueOf(-1);
	     }
	}

	@Override
	public List<PipeSegCustomInfoBean> getPipeSegCustom(PipeSegCustomInfoBean pipeseg) throws DataAccessException {
		if(TextUtil.isNotNull(pipeseg.getZh_label())){
			 String segName = pipeseg.getZh_label();
			 if(segName.contains(" ")){
				 segName= segName.replaceAll(" +", "%");
			 }
			 pipeseg.setZh_label("%"+segName+"%");
		 }
	     return getObjects("pdapipe.getPipeSegCustom", pipeseg);
	}

	@Override
	public Integer updatePipeSegCustom(PipeSegCustomInfoBean pipeseg) throws DataAccessException {
		 PipeSegCustomInfoBean ps = new PipeSegCustomInfoBean();
	     ps.setZh_label(pipeseg.getZh_label());
	     ps = (PipeSegCustomInfoBean)getObject("pdapipe.checkPipesegNameCustom", ps);
		//System.out.println("================="+ps.getInt_id());
		//System.out.println("=============="+pipeseg.getInt_id());
	     if (ps == null || ps.getInt_id().equals(pipeseg.getInt_id())) {
	       int r = Integer.valueOf(update("pdapipe.updatePipeSegCustom", pipeseg));
	//		 System.out.println("================="+r);
	       return r;
	     }else{
	    	 return Integer.valueOf(-1);
	     }
	}

	@Override
	public Integer deletePipeSegCustom(PipeSegCustomInfoBean pipeseg) throws DataAccessException {
		 return Integer.valueOf(update("pdapipe.deletePipeSegCustom", pipeseg));
	}
	
	
	
}
