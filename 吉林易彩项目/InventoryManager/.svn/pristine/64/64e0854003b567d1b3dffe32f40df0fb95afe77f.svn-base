package manage.quartz;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.jdbc.core.JdbcTemplate;

import base.util.TextUtil;

import com.inspur.eoms.common.platform.core.Platform;

public class DelResQuartz extends TimerTask{
	private JdbcTemplate jdbcTemplate;	
	private JdbcTemplate irmsjdbcTemplate;
	@Override
	public void run() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    int hour = Calendar.HOUR_OF_DAY;
	    //生成相应的序列	
	    long currentTime = System.currentTimeMillis();
	    int randD = (int) (Math.random() * 1000);
	    long flowId = currentTime + randD;
	    	
	    String addSql = "insert into del_res(resId,resName,resType,resNum,taskId,dealTime,flowID)"
	    		+ " select w.wellId,w.wellName,'well',w.resNum,t.id,now(),'"+flowId+"' from wellinfo w,approval_task t"
	    		+ " where w.dataQualityPrincipal is null"
	    		+ " and MBRWithin(POLYGONFROMTEXT(CONCAT( 'POINT(', w.longitude, ' ', w.latitude, ')' )),t.polygongeo) = 1"
	    		+ " and t.taskState = '已派发' and t.polygongeo is not null and w.dataQualityPrincipal is null and w.parties is null"
	    		+ " and w.deletedFlag ='0'";
	    this.jdbcTemplate.execute(addSql);
	    String upSql = "update wellinfo w ,del_res d"
	    		+ " set w.deletedFlag =1"
	    		+ " where w.wellId = d.resId and d.resType ='well'"
	    		+ "  and d.flowID ='"+flowId+"' and w.deletedFlag = 0 ";
	    this.jdbcTemplate.execute(upSql);
	    String pipSql = " update  del_res d,pipesegmentinfo p "
	    		+ " set p.deletedFlag =1 "
	    		+ " where p.endDeviceId = d.resId"
	    		+ " and d.flowID ='"+flowId+"'"
	    		+ " and p.dataQualityPrincipal is null"
	    		+ " and p.deletedFlag = 0 ";
	    this.jdbcTemplate.execute(pipSql);
	}
	
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public JdbcTemplate getIrmsjdbcTemplate() {
		return irmsjdbcTemplate;
	}
	public void setIrmsjdbcTemplate(JdbcTemplate irmsjdbcTemplate) {
		this.irmsjdbcTemplate = irmsjdbcTemplate;
	}

}
