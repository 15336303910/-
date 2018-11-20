package interfaces.pdainterface.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.user.pojo.UserInfoBean;
import manage.user.service.UserInfoService;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class UserIface extends HttpServlet{
	private static final Logger log = Logger.getLogger(UserIface.class);
	private UserInfoService userInfoService;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.addUser(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.addUser(req, resp);
	}
	
	
	public void addUser(HttpServletRequest request, HttpServletResponse response){
		String result ="";
		try{
			String queryStr = request.getQueryString();
			UserInfoBean userInfo = new UserInfoBean();
			String[] strs = queryStr.split("=");
			userInfo.setUsername(strs[0]);
			userInfo.setPassword(strs[1]);
			userInfo.setRealname(strs[0]);
			userInfo.setModulestr("0100000000000000000000000000000011000000000000000000000000000000");
			userInfo.setPhoneNumber("18216566595");
			userInfo.setAreano("0");
			userInfo.setRoleId(40);
			userInfo.setPowerstr("3201,32,03,25,02,23,12,01,62,06,5701,57,05,5704,5707,5503,55,5506,5201,52,5302,53,5601,56,3101,31,5802,58,27,291,71,07,21,26,11,63,5702,5705,5501,5504,5101,51,5202,5401,54,5602,3102,5803,28,292,24,22,61,41,04,3202,5703,5706,5502,5505,5102,5301,5402,33,5801,5804,29");
			userInfoService.insertNewUser(userInfo);
			result="创建成功，请登录系统";
		}catch(Exception e){
			e.printStackTrace();
			result ="应用服务器异常";
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out =null;
		try {
			out = response.getWriter();
			out.print(result.toString());
	        out.flush();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally{
			if(out!=null)out.close();
		}
	}
	@Override
	public void destroy() {
		super.destroy();
	}
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		 try {
		      ServletContext servletContext = getServletContext();
		      WebApplicationContext wac = null;
		      wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		      this.setUserInfoService((UserInfoService)wac.getBean("userInfoService"));
		    } catch (Exception e) {
		      log.error("Login.init", e);
		    }
	}
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	public static Logger getLog() {
		return log;
	}
	
}
