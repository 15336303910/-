package interfaces.pdainterface.weixin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.stone.service.impl.IstoneInfoService;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class WeixinIface extends HttpServlet{
	
	private IstoneInfoService stoneService = null;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.weixinMag(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.weixinMag(req, resp);
	}
	
	/**
	 * 微信公共入口
	 * @param request
	 * @param response
	 */
	public void weixinMag(HttpServletRequest request,HttpServletResponse response){
		stoneService.upVal();
	}

	public void destroy() {
		super.destroy();
	}

	public void init(ServletConfig config)
		    throws ServletException{
		   super.init(config);
		ServletContext servletContext = getServletContext();
	    WebApplicationContext wac = null;
	    wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	    stoneService = (IstoneInfoService)wac.getBean("stoneService");
	}

	public IstoneInfoService getStoneService() {
		return stoneService;
	}

	public void setStoneService(IstoneInfoService stoneService) {
		this.stoneService = stoneService;
	}

	
}
