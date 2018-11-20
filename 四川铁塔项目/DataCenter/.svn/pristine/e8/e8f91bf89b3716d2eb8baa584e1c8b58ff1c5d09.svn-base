package com.function.index.region;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.system.LoginUserUtil;
@Controller("com.function.index.region.JobDispatch")
@RequestMapping(value="/jobDispatch")
public class JobDispatch{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private LoginUserUtil loginUserUtil;
	
	/*
	 * 	查询任务明细
	 * 
	 * */
	@RequestMapping("/findDatas.ilf")
	public void findDatas(
		HttpServletRequest request,
		HttpServletResponse response
	)throws Exception{
		JSONObject jsonObj = JSONObject.fromObject("{success:true}");
		try{
			Boolean IS_PROVINCE = loginUserUtil.isProvince(request);
			jsonObj.put("IS_PROVINCE",IS_PROVINCE);
			String sqlA = "";
			String sqlB = "";
			if(IS_PROVINCE){
				sqlA+="SELECT F.*,ROWNUM AS RN FROM (";
				sqlA+="	  SELECT E.* FROM (";
				sqlA+="		  SELECT DISTINCT(C.FLOW_TITLE),C.SEND_MAN,C.SEND_TIME,C.END_TIME,C.ID  ";
				sqlA+="		  FROM NEWAUTH.EMPLOYEE A,NEWAUTH.COMPANY B,NEWIRMS.T_BPM_FORM_INFO C,NEWIRMS.WFWORKITEM D  ";
				sqlA+="		  WHERE TO_CHAR(A.EMPID) = TO_CHAR(D.PARTICIPANT) AND A.COMPANYID = B.COMPANYID AND C.PROC_INS_ID = D.ROOTPROCINSTID AND C.FLOW_MODEL = 'com.inspur.app.tieta.taskAssignment.process.taskAssign' AND C.SEND_COMPANY LIKE '%省公司%' AND D.WORKITEMNAME = '地市接单反馈' AND B.COMPANYID <> 1 ";
				sqlA+="	  ) E ORDER BY E.ID DESC";
				sqlA+=") F WHERE ROWNUM <= 10";
				sqlB+="SELECT A.*,ROWNUM AS RN FROM(";
				sqlB+="	   SELECT FLOW_TITLE,SEND_TIME,SEND_MAN,END_TIME FROM NEWIRMS.T_BPM_FORM_INFO WHERE FLOW_MODEL = 'com.inspur.app.tieta.taskAssignment.process.taskAssign2' ORDER BY ID DESC";
				sqlB+=") A WHERE ROWNUM <= 10";
			}else{
				sqlA+="SELECT F.*,ROWNUM AS RN FROM (";
				sqlA+="	  SELECT E.* FROM (";
				sqlA+="		  SELECT DISTINCT(C.FLOW_TITLE),C.SEND_MAN,C.SEND_TIME,C.END_TIME,C.ID  ";
				sqlA+="		  FROM NEWAUTH.EMPLOYEE A,NEWAUTH.COMPANY B,NEWIRMS.T_BPM_FORM_INFO C,NEWIRMS.WFWORKITEM D  ";
				sqlA+="		  WHERE TO_CHAR(A.EMPID) = TO_CHAR(D.PARTICIPANT) AND A.COMPANYID = B.COMPANYID AND C.PROC_INS_ID = D.ROOTPROCINSTID AND C.FLOW_MODEL = 'com.inspur.app.tieta.taskAssignment.process.taskAssign' AND C.SEND_COMPANY LIKE '%省公司%' AND D.WORKITEMNAME = '地市接单反馈' AND B.COMPANYID <> 1 AND B.ABBRE LIKE '%"+loginUserUtil.getBelongArea(request)+"%'";
				sqlA+="	  ) E ORDER BY E.ID DESC";
				sqlA+=") F WHERE ROWNUM <= 10";
				sqlB+="SELECT A.*,ROWNUM AS RN FROM(";
				sqlB+="	   SELECT FLOW_TITLE,SEND_TIME,SEND_MAN,END_TIME FROM NEWIRMS.T_BPM_FORM_INFO WHERE FLOW_MODEL = 'com.inspur.app.tieta.taskAssignment.process.taskAssign2' AND SEND_COMPANY LIKE '%"+loginUserUtil.getBelongArea(request)+"%' ORDER BY ID DESC";
				sqlB+=") A WHERE ROWNUM <= 10";
			}
			if(!"".equals(sqlA)){
				List<Map<String,Object>> jobs = jdbcTemplate.queryForList(sqlA);
				jsonObj.put("PROVINCE_ITEMS",jobs);
			}
			if(!"".equals(sqlB)){
				List<Map<String,Object>> jobs = jdbcTemplate.queryForList(sqlB);
				jsonObj.put("CITY_ITEMS",jobs);
			}
		}catch(Exception e){
			e.printStackTrace();
			jsonObj.put("success",false);
		}finally{
			response.setContentType("application/json; charset=UTF-8");
			response.getWriter().print(jsonObj.toString());
		}
	}
}
