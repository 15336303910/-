package interfaces.pdainterface.version;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.version.pojo.VersionControl;
import manage.version.service.impl.IversionControlService;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;

public class VersionIface extends HttpServlet{
	private static final Logger log = Logger.getLogger(VersionIface.class);
	private IversionControlService versionControlService = null;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.getVersion(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.getVersion(req, resp);
	}
	
	/**
	 * 获取version
	 * @param request
	 * @param response
	 */
	public void getVersion(HttpServletRequest request, HttpServletResponse response){
		String result ="";
		try{
			VersionControl version = new VersionControl();
			//versionControlService.addRes();
			version = versionControlService.getNewestVersion(version);
			//versionControlService.setLonLat();
			Gson gson = new Gson();
			result = gson.toJson(version);
		}catch(Exception e){
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
	
	
	
	public static final byte[] readBytes(InputStream is, int contentLen) {
        if (contentLen > 0) {
   	 	 int readLen = 0;
            int readLengthThisTime = 0;
            byte[] message = new byte[contentLen];
            try {
                while (readLen != contentLen) {
                    readLengthThisTime = is.read(message, readLen, contentLen
                                    - readLen);
                    if (readLengthThisTime == -1) {// Should not happen.
                            break;
                    }
                    readLen += readLengthThisTime;
                }
                return message;
            } catch (Exception e) {
            }
        }
        return new byte[] {};
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
		      this.setVersionControlService((IversionControlService)wac.getBean("versionControlService"));
		    } catch (Exception e) {
		      log.error("Login.init", e);
		    }
	}

	public IversionControlService getVersionControlService() {
		return versionControlService;
	}
	public void setVersionControlService(
			IversionControlService versionControlService) {
		this.versionControlService = versionControlService;
	}

}
