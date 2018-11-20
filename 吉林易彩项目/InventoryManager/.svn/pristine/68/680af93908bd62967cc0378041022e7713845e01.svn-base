package manage.quartz;

import java.util.Map;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.springframework.jdbc.core.JdbcTemplate;

import base.util.SmsUtil;
import base.util.TextUtil;

/**
 * 用来给北京的用户派发短信
 * @author chenqp
 *
 */
public class DispatchSms extends TimerTask{
	private JdbcTemplate jdbcTemplate;

	@Override
	public void run() {
		Date date = new Date();
		int hour = date.getHours();
		//每天八点定时派发短信
		if(hour == 8) {
			String sql = "select"
					+ " group_concat(t.taskTitle) as sumTitle,"
					+ " group_concat(t.id) as sumId,"
					+ " (select group_concat(phoneNumber) from sys_user where groupId = t.groupId group by groupId) as phoneNumber"
					+ " from approval_task t where t.taskState ='已派发' and t.delFlag ='Y' and t.smsFlag is null group by t.groupId ";
			List<Map<String, Object>> smsList = this.jdbcTemplate.queryForList(sql);
			if(TextUtil.isNotNull(smsList)) {
				String sumTask = "";
				for(Map<String, Object> map : smsList) {
					sumTask += map.get("sumId")+",";
					String smsTitle = map.get("sumTitle")+"";
					String phone = map.get("phoneNumber")+"";
					if(TextUtil.isNotNull(phone)) {
						String[] nums = phone.split(",");
						for(String num : nums) {
							SmsUtil.sendSms(num, "易采客户端提醒您工单:"+smsTitle+";工单已派发请及时签收!");
						}
					}
				}
				if(TextUtil.isNotNull(sumTask)) {
					if(sumTask.endsWith(",")) {
						sumTask = sumTask.substring(0, sumTask.length()-1);
					}
					String upSql = "update approval_task set smsFlag ='Y'"
							+ " where id in ("+sumTask+")";
					this.jdbcTemplate.execute(upSql);
				}
			}
		}
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}	
}
