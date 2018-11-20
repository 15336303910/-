package interfaces.pdainterface.common.action;

import interfaces.pdainterface.common.service.impl.ICommonService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import base.util.ZxingUtil;

/**
 * 生成二维码
 * @author chenqp
 *
 */
public class ResScan extends HttpServlet{
	
	private ICommonService commonService;
	private static final Logger log = Logger.getLogger(ResScan.class);
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getResStream(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getResStream(request, response);
	}
	
	/**
	 * 返回文件信息
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void getResStream(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String result = "";
		String resId = request.getParameter("resId");
		String resType = request.getParameter("resType");
		
		String content = commonService.getScanCode(resId, resType);
		
		File file = ZxingUtil.encoderQRCode(content, resType+"_"+resId+"_"+getDateStr()+".jpg", 8);
		FileInputStream fis = new FileInputStream(file);
		ServletOutputStream out = response.getOutputStream();
		//将文件封装成流
		StringBuilder postStrBuf = new StringBuilder();
		byte[] buf = new byte[512];
		int readCount = 0;
		while((readCount = fis.read(buf)) != -1)
		{
			out.write(buf, 0, readCount);
		}
		out.close();
		fis.close();
	}
	
	
	String getDateStr(){
        String res;
        Date date = new Date();
        long ts = date.getTime();
        res = String.valueOf(ts);
        int num = (int)(1+Math.random()*(10-1+1));
        return res+num;
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
			commonService = (ICommonService) wac
					.getBean("commonService");
		} catch (Exception e) {
			log.error("Login.init", e);
		}
	}
}
