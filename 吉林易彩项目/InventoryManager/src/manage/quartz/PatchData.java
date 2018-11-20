package manage.quartz;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.jdbc.core.JdbcTemplate;

import base.util.TextUtil;
import interfaces.irmsInterface.interfaces.opticTran.service.impl.IirmsOpticTranService;
import interfaces.irmsInterface.interfaces.outLine.service.impl.IirmsOutLineService;
import interfaces.pdainterface.equt.service.PDAEqutInfoService;
import interfaces.pdainterface.pipe.service.PDAPipeService;
import interfaces.pdainterface.poleline.service.PDAPolelineService;
import manage.equt.pojo.EqutInfoBean;
import manage.leadup.pojo.LeadupPojo;
import manage.leadup.service.impl.IleadupService;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;

public class PatchData extends TimerTask{
	private JdbcTemplate jdbcTemplate;	
	private PDAPipeService pdaPipeService;	
	private PDAPolelineService pdaPolelineService;
	private PDAEqutInfoService pdaEqutInfoService;
	private IleadupService leadupService;
	private IirmsOutLineService irmsOutLineService;
	private IirmsOpticTranService irmsOpticTranService;
	
	@Override
	public void run() {
		Date date = new Date();
		int hour = date.getHours();
		String before = this.getBeforeDay(10);
		String sql = "";
		//处理引上段数据
		sql ="select segId,segName,segType "
				+ " from poleMap t"
				+ " where startResNum is not null and t.resNum is null and "
				+ "  endResNum is not null and"
				+ " unix_timestamp(t.createTime) >= unix_timestamp('"+before+"')";
		if(hour == 23) {
			sql ="select segId,segName,segType "
					+ " from leadMap t"
					+ " where resNum is null and startResNum is not null"
					+ " and endResNum is not null and"
					+ " unix_timestamp(t.createTime) >= unix_timestamp('"+before+"')";
		}
		//杆路
		if(hour == 1) {
			sql ="select segId,segName,segType "
					+ " from poleMap t"
					+ " where startResNum is not null and t.resNum is null and "
					+ "  endResNum is not null and"
					+ " unix_timestamp(t.createTime) >= unix_timestamp('"+before+"')";
		}
		//管井
		if(hour == 3) {
			sql ="select segId,segName,segType "
					+ " from pipeMap t"
					+ " where resNum is null and startResNum is not null"
					+ " and endResNum is not null and"
					+ " unix_timestamp(t.createTime) >= unix_timestamp('"+before+"')";
		}
		//光交
		if(hour == 5) {
			sql ="select  distinct eid,opticalName,'optical' as segType"
					+ " from patch_data where patchState ='0'";
		}
		try {
			if(TextUtil.isNotNull(sql)) {
				List<Map<String, Object>> list = this.jdbcTemplate.queryForList(sql);
				for(Map<String, Object> map : list) {
					String segType = map.get("segType")+"";
					String id = map.get("id")+"";
					//同步光交箱数据
					if(segType.equals("optical")) {
						EqutInfoBean equt = new EqutInfoBean();
						equt.setEid(map.get("eid")+"");
						equt = this.pdaEqutInfoService.getEqutObj(equt);
						
						if(TextUtil.isNotNull(equt.getDel()) && equt.getDel()==0) {
							if(TextUtil.isNull(equt.getResNum())) {
								irmsOpticTranService.addOptiTranBox(equt);
							}else {
								irmsOpticTranService.moveOptiTranBox(equt);
							}
							String upSql = "update patch_data set patchState ='1'"
									+ " where opticalName = '"+equt.getEname()+"'";
							this.jdbcTemplate.execute(upSql);
						}
					}
					//同步杆路
					if(segType.equals("pole")) {
						PolelineSegmentInfoBean poleSeg = new PolelineSegmentInfoBean();
						poleSeg.setPoleLineSegmentId(Integer.parseInt(map.get("segId")+""));
						poleSeg = this.pdaPolelineService.getPolelineSegObj(poleSeg);
						if(!(poleSeg.getMaintenanceAreaName().equals("null_null_"))) {
							this.irmsOutLineService.addPoleLine(poleSeg);
						}
					}
					//同步管道
					if(segType.equals("pipe")) {
						PipeSegmentInfoBean pipeSeg = new PipeSegmentInfoBean();
						pipeSeg.setPipeSegmentId(Integer.parseInt(map.get("segId")+""));
						pipeSeg = this.pdaPipeService.getPipeSegObj(pipeSeg);
						if(!(pipeSeg.getMaintenanceAreaName().equals("null_null_"))) {
							this.irmsOutLineService.addPipeSeg(pipeSeg);
						}
					}
					//同步引上
					if(segType.equals("lead")) {
						LeadupPojo lead = new LeadupPojo();
						lead.setId(Integer.parseInt(map.get("segId")+""));
						lead = this.leadupService.getLeadUpObj(lead);
						if(!(lead.getMantainance().equals("null_null_"))) {
							this.irmsOutLineService.addLeadUp(lead);
						}
					}
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	/** 
     * 获取当前时间的前一天时间 
     * @param cl 
     * @return 
     */  
    private String getBeforeDay(int days){ 
    	Calendar cl = Calendar.getInstance();
    	cl.setTime(new java.util.Date());
        int day = cl.get(Calendar.DATE);  
        cl.set(Calendar.DATE, day-days);  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date before = cl.getTime();
        return sdf.format(before);  
    } 
	public IirmsOpticTranService getIrmsOpticTranService() {
		return irmsOpticTranService;
	}
	public void setIrmsOpticTranService(IirmsOpticTranService irmsOpticTranService) {
		this.irmsOpticTranService = irmsOpticTranService;
	}
	public PDAEqutInfoService getPdaEqutInfoService() {
		return pdaEqutInfoService;
	}
	public void setPdaEqutInfoService(PDAEqutInfoService pdaEqutInfoService) {
		this.pdaEqutInfoService = pdaEqutInfoService;
	}
	public PDAPolelineService getPdaPolelineService() {
		return pdaPolelineService;
	}
	public void setPdaPolelineService(PDAPolelineService pdaPolelineService) {
		this.pdaPolelineService = pdaPolelineService;
	}
	public IleadupService getLeadupService() {
		return leadupService;
	}
	public void setLeadupService(IleadupService leadupService) {
		this.leadupService = leadupService;
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public PDAPipeService getPdaPipeService() {
		return pdaPipeService;
	}
	public void setPdaPipeService(PDAPipeService pdaPipeService) {
		this.pdaPipeService = pdaPipeService;
	}
	public IirmsOutLineService getIrmsOutLineService() {
		return irmsOutLineService;
	}
	public void setIrmsOutLineService(IirmsOutLineService irmsOutLineService) {
		this.irmsOutLineService = irmsOutLineService;
	}
	
}
