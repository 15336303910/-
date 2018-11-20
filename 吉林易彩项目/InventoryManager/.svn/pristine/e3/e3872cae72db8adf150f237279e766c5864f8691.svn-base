package interfaces.pdainterface.images;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.images.pojo.ResourceImage;
import manage.images.service.ResourceImageService;
import manage.user.pojo.UserInfoBean;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import base.util.GetProperties;
import base.util.ImageUtil;
import base.util.TextUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ResImage extends HttpServlet {
	private UserInfoBean user;
	private static final Logger log = Logger.getLogger(ResImage.class);
	private ResourceImageService resourceImageService= null;
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
		String resNum = request.getParameter("resNum");
		
		ResourceImage resImage = new ResourceImage();
		if(TextUtil.isNotNull(resId)){
			resImage.setId(Integer.parseInt(resId));
		}else{
			resImage.setResNum(resNum);
		}
		GetProperties getProperties = new GetProperties();
		String imagePath = getProperties.getValueByKey("imagePath");
		List<ResourceImage> list = this.resourceImageService.getResImage(resImage);
		response.setContentType("image/jpeg");  
		response.setDateHeader("expries", -1);  
        response.setHeader("Cache-Control", "no-cache");  
        response.setHeader("Pragma", "no-cache");  
		
		if(TextUtil.isNotNull(list)){
			ServletOutputStream out = response.getOutputStream();
			FileInputStream fis = null;
			BufferedImage imgs[] = new BufferedImage[list.size()];
			for(int i=0;i<list.size();i++){
				ResourceImage resImg = list.get(i);
				imgs[i]=ImageUtil.getBufferedImage(imagePath+"upload/"+resImg.getImagePath());
			}
			BufferedImage outImg = ImageUtil.mergeImage(false,imgs);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(outImg, "gif", os);
			InputStream is = new ByteArrayInputStream(os.toByteArray());
			byte[] buf = new byte[2048];
			int readCount = 0;
			while((readCount = is.read(buf)) != -1)
			{
				out.write(buf, 0, readCount);
			}
			out.close();
			fis.close();
		}
		
	}
	
	String getStr(InputStream is){
		String str = "";
		try{
			byte[] bytes = new byte[1024 * 1024];  
	        int nRead = 1;  
	        int nTotalRead = 0;  
	        while (nRead > 0) {  
	            nRead = is.read(bytes, nTotalRead, bytes.length - nTotalRead);  
	            if (nRead > 0)  
	                nTotalRead = nTotalRead + nRead;  
	        }  
	        str = new String(bytes, 0, nTotalRead, "utf-8");
		}catch(Exception e){
			e.printStackTrace();
		}
        return str;
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
			resourceImageService = (ResourceImageService) wac
					.getBean("resourceImageService");
		} catch (Exception e) {
			log.error("Login.init", e);
		}
	}

}
