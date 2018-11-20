package manage.quartz;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.JdbcTemplate;

import base.util.JsonUtil;
import base.util.TextUtil;
import manage.approval.pojo.ApprovalTaskPojo;
import manage.approval.service.impl.IapprovalTaskService;
import net.sf.ezmorph.bean.MorphDynaBean;

public class PatchTask extends TimerTask{
	private JdbcTemplate jdbcTemplate;
	private IapprovalTaskService approvalService;
	@Override
	public void run() {
		String taskIds = "";
		String[] ids  = taskIds.split(",");
		if(TextUtil.isNotNull(taskIds)) {
			for(String taskId : ids) {
				ApprovalTaskPojo task = approvalService.getApprovalObj(taskId+"");
				String sql = "select * from approval_collect where taskId ='"+taskId+"'";
				List<Map<String, Object>> collList = this.jdbcTemplate.queryForList(sql);
				if(TextUtil.isNull(collList)) {
					boolean falg = false;
					if(TextUtil.isNull(task.getSegNum())) {
						falg =true;
					}else {
						if(task.getSegNum().equals("0")) {
							falg = false;
						}else {
							falg = true;
						}
					}
					if(falg) {
						String json = approvalService.getMapStr(task, "now");
						Integer segNum = 0;
						Integer pointNum = 0;
						json = this.replaceBlank(json);
						List<Map<String, Object>> list =JsonUtil.getList4Json(json, Map.class);
						for(int i=0;i<list.size();i++) {
							Map<String, Object> map = list.get(i);
							if(map.get("id")!=null) {
								segNum++;
							}
							if(map.get("start")!=null) {
								MorphDynaBean start = (MorphDynaBean) map.get("start");
								pointNum++;
								
							}
							if(map.get("end")!=null) {
								MorphDynaBean end = (MorphDynaBean) map.get("end");
							}
						}
						task.setSegNum(segNum+"");
						task.setPointNum(pointNum+"");
						approvalService.upApprovalObj(task);
						approvalService.batchResCollect(list,taskId+"");
					}
					
				}
			}
		}
		
	}
	
	public String replaceBlank(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public IapprovalTaskService getApprovalService() {
		return approvalService;
	}
	public void setApprovalService(IapprovalTaskService approvalService) {
		this.approvalService = approvalService;
	}	
}
