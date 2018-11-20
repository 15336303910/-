package interfaces.pdainterface.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import manage.images.pojo.ResourceImage;
import manage.images.service.ResourceImageService;
import manage.images.service.impl.ResourceImageServiceImpl;
import manage.user.pojo.UserInfoBean;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import base.util.GetProperties;
import base.util.JsonUtil;
import base.util.TextUtil;

public class upload extends HttpServlet
{
  private UserInfoBean user;
  private static final Logger log = Logger.getLogger(upload.class);
  public static final String MODULENAME = "LOGIN";
  private ResourceImageService resourceImageService= null;

  public void destroy()
  {
    super.destroy();
  }

  /*public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
	  String result = "";
	  ResourceImage image = execLogin(request, response);
	  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
	  result = gson.toJson(image);
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
  }*/

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
	  String result = "";
	  ResourceImage image = execLogin(request, response);
	  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss Z").create();
	  result = gson.toJson(image);
	  response.setContentType("text/html;charset=utf-8");
	  PrintWriter out =null;
	  try {
		out = response.getWriter();
		out.print(result.toString());
	    out.flush();
	  } catch (Exception e) {
		e.printStackTrace();
	  }finally{
		if(out!=null)out.close();
	  }
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
    try {
      ServletContext servletContext = getServletContext();
      WebApplicationContext wac = null;
      wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
      //注入图片上传
      resourceImageService = (ResourceImageService)wac.getBean("resourceImageService");
    } catch (Exception e) {
      log.error("Login.init", e);
    }
  }

  public ResourceImage execLogin(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
	ResourceImage image = new ResourceImage();  
    request.setCharacterEncoding("UTF-8");
    DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
    diskFileItemFactory.setSizeThreshold(1024*1024*5);
    ServletFileUpload servletFileUpload = new ServletFileUpload(
      diskFileItemFactory);
    try
    {
      List <FileItem>fileItems = servletFileUpload.parseRequest(request);
      for (FileItem fileItem : fileItems)
      {
        if (fileItem.isFormField()){
        	continue;
        }
        String fileName = fileItem.getName();
        if ((fileItem.getName() == null) || ("".equals(fileItem.getName()))){
        	continue;
        }
        GetProperties getProperties = new GetProperties();
		String rootDirProject = getProperties.getValueByKey("imagePath");
        //解析文件名
        String[] names = fileName.split("_");
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		String datePath=sdf.format(new Date());
		String filePath = rootDirProject+"upload";
		if(names.length >1){
			filePath = filePath +"/"+names[0]+"/"+datePath;
		}
        File saveFileUploadFile = new File(filePath);
        if (!(saveFileUploadFile.exists())) {
          saveFileUploadFile.mkdirs();
        }
        File saveTheFileUpload = new File(saveFileUploadFile, 
          fileName);
        fileItem.write(saveTheFileUpload);
        //保存数据库信息
        image = this.saveImage(fileName);
      }
    }
    catch (Exception e)
    {
    	e.printStackTrace();
    }
	return image;
  }
  
  /**
   * 保存图片信息
   * @param filePath
   * @param fileName
   */
  private ResourceImage saveImage( String fileName ){
	  String[] names = fileName.split("_");
	  ResourceImage resImage = new ResourceImage();
	  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
	  String datePath=sdf.format(new Date());
	  if(names.length >1){
		  resImage.setImageName(fileName);
	      resImage.setResourceId(names[names.length-2]);
	      resImage.setImagePath(names[0]+"/"+datePath+"/"+fileName);
	      resImage.setType(names[0]);
	      if(TextUtil.isNull(names[1]) || names[1].equals("null")){
	    	  String resNum = this.resourceImageService.getResStr(fileName);
	    	  resImage.setResNum(resNum);
	      }else{
	    	  resImage.setResNum(names[1]);
	      }
	  }else{
		  resImage.setImageName(fileName);
	      resImage.setResourceId("0");
	      resImage.setImagePath(fileName);
	      resImage.setType("");
	  }
      int id = resourceImageService.insertResImage(resImage);
      //resImage.setId(id);
      return resImage;
  }

  public UserInfoBean getUser() {
    return this.user;
  }
  public ResourceImageService getResourceImageService() {
	return resourceImageService;
  }
  public void setResourceImageService(ResourceImageService resourceImageService) {
	this.resourceImageService = resourceImageService;
  }
  public void setUser(UserInfoBean user) {
    this.user = user;
  }
}
