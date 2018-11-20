package com.quartz.job;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.quartz.util.ApplicationUtil;
@Repository("authority")
public class Authority implements Job{
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException{
		System.out.println("=>Authority Data Synchronization Job Begin.");
		try{				
			JdbcTemplate jdbcTool = (JdbcTemplate)ApplicationUtil.getBean("jdbcTemplate");
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection authorityConnection = DriverManager.getConnection(
				jdbcTool.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'auth_jdbc_url'").get("PRO_VALUE").toString(),
				jdbcTool.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'auth_username'").get("PRO_VALUE").toString(),
				jdbcTool.queryForMap("SELECT * FROM S_SYSTEM_PROPERTY WHERE PROPERTY_NAME = 'auth_password'").get("PRO_VALUE").toString()
			);
			Statement authorityStateMent = authorityConnection.createStatement();
			/*
			 * 	获取本地链接
			 * 
			 * */
			DataSource dataSource = jdbcTool.getDataSource();
			Connection localConnection = dataSource.getConnection();
			localConnection.setAutoCommit(false);
			/*
			 * 	同步权限系统的组织数据：公司
			 * 
			 * */
			System.out.println("=>（01/08）开始同步[公司/部门]数据.");
			jdbcTool.execute("DELETE FROM S_SYSTEM_ORGANIZATION");
			String companySql = "";
			companySql+="insert into S_SYSTEM_ORGANIZATION(";
			companySql+="	ID,ORGANIZATION_NAME,ORGANIZATION_DESC,ORGANIZATION_LEVEL,PARENT_CODE";
			companySql+=")values(?,?,?,?,?)";					
			PreparedStatement localPrepare = localConnection.prepareStatement(companySql);
			ResultSet resultSet = authorityStateMent.executeQuery("SELECT COMPANYID,NAME FROM COMPANY");
			while(resultSet.next()){
				localPrepare.setInt(1,resultSet.getInt("COMPANYID"));
				localPrepare.setString(2,resultSet.getString("NAME"));
				localPrepare.setString(3,resultSet.getString("NAME"));
				localPrepare.setString(4,"总公司");
				localPrepare.setInt(5,0);
				localPrepare.addBatch();
			}
			localPrepare.executeBatch();
			localConnection.commit();
			localPrepare.clearBatch();
			/*
			 * 	同步组织数据：部门
			 * 
			 * */
			resultSet = authorityStateMent.executeQuery("SELECT UNITID,NAME,COMPANYID FROM UNIT WHERE DELFLAG = 'N'");
			String departSql = "";
			departSql+="insert into S_SYSTEM_ORGANIZATION(";
			departSql+="	ID,ORGANIZATION_NAME,ORGANIZATION_DESC,ORGANIZATION_LEVEL,PARENT_CODE";
			departSql+=")values(?,?,?,?,?)";
			localPrepare = localConnection.prepareStatement(departSql);
			while(resultSet.next()){
				localPrepare.setInt(1,resultSet.getInt("UNITID"));
				localPrepare.setString(2,resultSet.getString("NAME"));
				localPrepare.setString(3,resultSet.getString("NAME"));
				localPrepare.setString(4,"部门");
				localPrepare.setInt(5,resultSet.getInt("COMPANYID"));
				localPrepare.addBatch();
			}
			localPrepare.executeBatch();
			localConnection.commit();
			localPrepare.clearBatch();
			/*
			 * 	同步[用户组]数据.
			 * 
			 * */
			System.out.println("=>（02/08）开始同步[用户组]数据.");
			jdbcTool.execute("DELETE FROM S_SYSTEM_USERGROUP");
			String userTeamSql = "";
			userTeamSql+="insert into S_SYSTEM_USERGROUP(";
			userTeamSql+="	  GROUP_ID,GROUP_EN_NAME,GROUP_CN_NAME,COMPANY_ID";
			userTeamSql+=")values(?,?,?,?)";					
			localPrepare = localConnection.prepareStatement(userTeamSql);
			resultSet = authorityStateMent.executeQuery("SELECT GROUPID,GROUPNAME,DISPLAYNAME,COMPANYID FROM USERGROUP");
			while(resultSet.next()){
				localPrepare.setInt(1,resultSet.getObject("GROUPID")==null?-1:resultSet.getInt("GROUPID"));
				localPrepare.setString(2,resultSet.getObject("GROUPNAME")==null?"":resultSet.getString("GROUPNAME"));
				localPrepare.setString(3,resultSet.getObject("DISPLAYNAME")==null?"":resultSet.getString("DISPLAYNAME"));
				localPrepare.setInt(4,resultSet.getObject("COMPANYID")==null?-1:resultSet.getInt("COMPANYID"));
				localPrepare.addBatch();
			}
			localPrepare.executeBatch();
			localConnection.commit();
			localPrepare.clearBatch();
			/*
			 * 	同步[用户组]与[账户]关联数据.
			 * 
			 * */
			System.out.println("=>（03/08）开始同步[用户组/用户]数据.");
			jdbcTool.execute("DELETE FROM S_SYSTEM_GROUP_USER");
			String guSql = "";
			guSql+="insert into S_SYSTEM_GROUP_USER(";
			guSql+="	USER_ID,GROUP_ID";
			guSql+=")values(?,?)";					
			localPrepare = localConnection.prepareStatement(guSql);
			resultSet = authorityStateMent.executeQuery("SELECT USERID,GROUPID FROM GRPMEMBER");
			while(resultSet.next()){
				localPrepare.setInt(1,resultSet.getObject("USERID")==null?-1:resultSet.getInt("USERID"));
				localPrepare.setInt(2,resultSet.getObject("GROUPID")==null?-1:resultSet.getInt("GROUPID"));
				localPrepare.addBatch();
			}
			localPrepare.executeBatch();
			localConnection.commit();
			localPrepare.clearBatch();
			/*
			 * 	同步[用户组]与[角色]关联数据.
			 * 
			 * */
			System.out.println("=>（04/08）开始同步[用户组/角色]数据.");
			jdbcTool.execute("DELETE FROM S_SYSTEM_GROUP_ROLE");
			String grSql = "";
			grSql+="insert into S_SYSTEM_GROUP_ROLE(";
			grSql+="	ROLE_ID,GROUP_ID";
			grSql+=")values(?,?)";					
			localPrepare = localConnection.prepareStatement(grSql);
			resultSet = authorityStateMent.executeQuery("SELECT ROLEID,GROUPID FROM GROUPROLE");
			while(resultSet.next()){
				localPrepare.setInt(1,resultSet.getObject("ROLEID")==null?-1:resultSet.getInt("ROLEID"));
				localPrepare.setInt(2,resultSet.getObject("GROUPID")==null?-1:resultSet.getInt("GROUPID"));
				localPrepare.addBatch();
			}
			localPrepare.executeBatch();
			localConnection.commit();
			localPrepare.clearBatch();
			/*
			 * 	同步账户数据
			 * 
			 * */
			System.out.println("=>（05/08）开始同步[用户]数据.");
			jdbcTool.execute("DELETE FROM S_SYSTEM_USER");
			String accountSql = "";
			accountSql+="SELECT A.USERID,A.EMPID,A.USERACCOUNT,A.PASSWORD,A.LOCKED,B.NAME AS EMP_NAME,B.MOBILE,C.ABBRE,C.COMPANYID,C.NAME AS COM_NAME,D.UNITID,D.NAME AS DEPART_NAME ";
			accountSql+="FROM USERINFO A,EMPLOYEE B,COMPANY C,UNIT D ";
			accountSql+="WHERE A.EMPID = B.EMPID AND B.COMPANYID = C.COMPANYID AND B.UNITID = D.UNITID ";
			resultSet = authorityStateMent.executeQuery(accountSql);
			String accountInsertSql = "";
			accountInsertSql+="insert into S_SYSTEM_USER(";
			accountInsertSql+="		ID,USER_NAME,USER_PASS,IS_LENGTHY_USE,IS_LOCKED,IS_HAVE_INVITE_ABLE,";
			accountInsertSql+="		EMPLOYEE_NAME,EMPLOYEE_SEX,EMPLOYEE_ID_CARD,EMPLOYEE_PHONE,ROLE_CODE,";
			accountInsertSql+="		BELONG_AREA,EMPLOYEE_COMPANY_CODE,EMPLOYEE_COMPANY,EMPLOYEE_DEPART_CODE,EMPLOYEE_DEPARTNAME,USER_ICON";
			accountInsertSql+=")values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			localPrepare = localConnection.prepareStatement(accountInsertSql);
			while(resultSet.next()){
				localPrepare.setInt(1,resultSet.getInt("USERID"));
				localPrepare.setString(2,resultSet.getObject("USERACCOUNT")==null?" ":resultSet.getString("USERACCOUNT"));
				localPrepare.setString(3,resultSet.getObject("PASSWORD")==null?" ":resultSet.getString("PASSWORD"));
				localPrepare.setString(4,"Y");
				localPrepare.setString(5,"N");
				localPrepare.setString(6,"N");
				localPrepare.setString(7,resultSet.getObject("EMP_NAME")==null?" ":resultSet.getString("EMP_NAME"));
				localPrepare.setString(8,"-");
				localPrepare.setString(9,resultSet.getObject("EMPID")==null?" ":resultSet.getString("EMPID"));
				localPrepare.setString(10,resultSet.getObject("MOBILE")==null?" ":resultSet.getString("MOBILE"));
				localPrepare.setInt(11,3);
				localPrepare.setString(12,resultSet.getObject("ABBRE")==null?" ":resultSet.getString("ABBRE"));
				localPrepare.setInt(13,resultSet.getInt("COMPANYID"));
				localPrepare.setString(14,resultSet.getObject("COM_NAME")==null?" ":resultSet.getString("COM_NAME"));
				localPrepare.setInt(15,resultSet.getInt("UNITID"));
				localPrepare.setString(16,resultSet.getObject("DEPART_NAME")==null?" ":resultSet.getString("DEPART_NAME"));
				localPrepare.setString(17,"1016.png");
				localPrepare.addBatch();
			}
			localPrepare.executeBatch();
			localConnection.commit();
			localPrepare.clearBatch();
			/*
			 * 	同步角色数据
			 * 
			 * */
			System.out.println("=>（06/08）开始同步[角色]数据.");
			resultSet = authorityStateMent.executeQuery("SELECT ROLEID,ENNAME,CNNAME FROM ROLES");
			String roleInsertSql = "";
			roleInsertSql+="insert into S_SYSTEM_ROLE(";
			roleInsertSql+="	ID,ROLE_NAME,ROLE_EN_NAME,IS_USING";
			roleInsertSql+=")values(?,?,?,?)";
			localPrepare = localConnection.prepareStatement(roleInsertSql);
			while(resultSet.next()){
				if(jdbcTool.queryForInt("SELECT COUNT(1) FROM S_SYSTEM_ROLE WHERE ID = "+resultSet.getInt("ROLEID"))==0){
					localPrepare.setInt(1,resultSet.getInt("ROLEID"));
					localPrepare.setString(2,resultSet.getObject("CNNAME")==null?" ":resultSet.getString("CNNAME"));
					localPrepare.setString(3,resultSet.getObject("ENNAME")==null?" ":resultSet.getString("ENNAME"));
					localPrepare.setString(4,"Y");
					localPrepare.addBatch();
				}
			}
			localPrepare.executeBatch();
			localConnection.commit();
			localPrepare.clearBatch();
			/*
			 * 	同步用户角色数据
			 * 
			 * */
			System.out.println("=>（07/08）开始同步[用户/角色]数据.");
			jdbcTool.execute("DELETE FROM S_SYSTEM_USER_ROLE");
			resultSet = authorityStateMent.executeQuery("SELECT * FROM USERROLE");
			String userRoleSql = "";
			userRoleSql+="insert into S_SYSTEM_USER_ROLE(";
			userRoleSql+="	  USER_ID,ROLE_ID";
			userRoleSql+=")values(?,?)";
			localPrepare = localConnection.prepareStatement(userRoleSql);
			while(resultSet.next()){
				localPrepare.setInt(1,resultSet.getInt("USERID"));
				localPrepare.setInt(2,resultSet.getInt("ROLEID"));
				localPrepare.addBatch();
			}
			localPrepare.executeBatch();
			localConnection.commit();
			localPrepare.clearBatch();
			/*
			 * 	同步<角色>绑定的<功能菜单>信息
			 * 
			 * */
			System.out.println("=>（08/08）开始同步[角色/菜单]数据.");
			jdbcTool.execute("DELETE FROM S_SYSTEM_ROLE_FUNC");
			String sql = "";
			sql+="SELECT ROLEFUNCID,ROLEID,FUNCID FROM ROLEFUNC WHERE FUNCID IN(";
			sql+="	  SELECT FUNCID FROM FUNCTIONS WHERE FUNCTYPEID IN(";
			sql+="		  SELECT FUNCTYPEID FROM FUNCTYPE WHERE TYPECNNAME LIKE '%运营支撑%'";
			sql+="	  )";
			sql+=")";
			resultSet = authorityStateMent.executeQuery(sql);/*开始进行录入*/
			String roleFuncSql = "";
			roleFuncSql+="insert into S_SYSTEM_ROLE_FUNC(";
			roleFuncSql+="	  ROLEFUNCID,ROLEID,FUNCID";
			roleFuncSql+=")values(?,?,?)";
			localPrepare = localConnection.prepareStatement(roleFuncSql);
			while(resultSet.next()){
				localPrepare.setInt(1,resultSet.getInt("ROLEFUNCID"));
				localPrepare.setInt(2,resultSet.getInt("ROLEID"));
				localPrepare.setInt(3,resultSet.getInt("FUNCID"));
				localPrepare.addBatch();
			}
			localPrepare.executeBatch();
			localConnection.commit();
			localPrepare.clearBatch();
			/*
			 * 	同步<MENU_ID>绑定的<FUNC_ID>信息
			 * 
			 * */
			List<Map<String,Object>> allMenus = jdbcTool.queryForList("SELECT MENU_NAME,MENU_EN_NAME,BIND_FUNC_ID FROM S_SYSTEM_MENU WHERE MENU_EN_NAME IS NOT NULL");
			String menuStr = "";
			if(allMenus.size()>0){
				for(int i=0;i<allMenus.size();i++){
					if("".equals(menuStr)){
						menuStr = "'"+allMenus.get(i).get("MENU_EN_NAME").toString()+"'"; 
					}else{
						menuStr+= ",'"+allMenus.get(i).get("MENU_EN_NAME").toString()+"'";
					}
				}
			}
			resultSet = authorityStateMent.executeQuery("SELECT FUNCID,FUNCENNAME,FUNCCNNAME FROM FUNCTIONS WHERE FUNCTYPEID = 659 AND FUNCENNAME IN("+menuStr+")");/*开始进行录入*/
			Statement statement = localConnection.createStatement();
			while(resultSet.next()){
				statement.execute("UPDATE S_SYSTEM_MENU SET BIND_FUNC_ID = "+resultSet.getInt("FUNCID")+" WHERE MENU_EN_NAME = '"+resultSet.getString("FUNCENNAME")+"'");
				localConnection.commit();
			}
			/*
			 * 	关闭权限数据库链接
			 * 
			 * */
			localConnection.setAutoCommit(true);
			if(resultSet!=null){
				resultSet.close();
			}
			if(authorityStateMent!=null){
				authorityStateMent.close();
			}
			if(authorityConnection!=null){
				authorityConnection.close();
			}
			if(localPrepare!=null){
				localPrepare.close();
			}
			if(localConnection!=null){
				localConnection.close();
			}
			System.out.println("=>Authority Data Synchronization Job done.");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
