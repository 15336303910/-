 package interfaces.pdainterface.property.action;
 
 import base.web.InterfaceAction;
 import interfaces.pdainterface.property.service.PDAPropertyService;
 import java.io.File;
 import java.util.List;
 import manage.property.pojo.PropertyInfoBean;
 import org.apache.log4j.Logger;
 
 public class PDAProperty extends InterfaceAction
 {
   private static final long serialVersionUID = -3486300674796123267L;
   private static final Logger log = Logger.getLogger(PDAProperty.class);
   private PDAPropertyService pdaPropertyService;
 
   public String getProperty()
   {
     try
     {
       PropertyInfoBean property = (PropertyInfoBean)getRequestObject(PropertyInfoBean.class);
       if (property != null) {
         List propertyList = this.pdaPropertyService.getProperty(property);
         sendResponse(Integer.valueOf(0), propertyList);
       }else{
    	   sendResponse(Integer.valueOf(2), "请求参数错误。");
       }
     }
     catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDAProperty.getProperty ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
      return "success";
   }
 
   public String insertProperty() {
     try {
       PropertyInfoBean property = (PropertyInfoBean)getRequestObject(PropertyInfoBean.class);
       if (property != null) {
         int id = this.pdaPropertyService.insertProperty(property);
         PropertyInfoBean g = new PropertyInfoBean();
         g.setPropertyId(Integer.valueOf(id));
         sendResponse(Integer.valueOf(0), g);
       }else{
    	 sendResponse(Integer.valueOf(2), "请求参数错误。");
       }
       
     }
     catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDAProperty.insertProperty ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
      return "success";
   }
 
   public String updateProperty() {
     try {
       PropertyInfoBean property = (PropertyInfoBean)getRequestObject(PropertyInfoBean.class);
       if (property != null) {
         this.pdaPropertyService.updateProperty(property);
         sendResponse(Integer.valueOf(0), "修改成功。");
       }else{
    	 sendResponse(Integer.valueOf(2), "请求参数错误。");  
       }
       
     }
     catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDAProperty.updateProperty ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
     return "success";
   }
 
   public String getObject() {
     try {
       PropertyInfoBean property = (PropertyInfoBean)getRequestObject(PropertyInfoBean.class);
       if (property != null) {
         property = this.pdaPropertyService.getObject(property);
         sendResponse(Integer.valueOf(0), property); 
       }else{
    	 sendResponse(Integer.valueOf(2), "请求参数错误。");  
       }
       
     }
     catch (Exception e) {
       this.exception = e;
       sendResponse(Integer.valueOf(3), "应用服务器异常。");
       log.error("PDAProperty.updateProperty ERROR\nJsonRequest:" + getJsonRequest() + "\n" + getJsonResponse(), e);
     }
      return "success";
   }
 
   public String getFileList() {
     String result = "[";
     String filePath = getServletContext().getRealPath("/") + "file";
     File dir = new File(filePath);
     String[] fileList = dir.list();
     if (fileList != null) {
       for (String f : fileList) {
         int fileType = 0;
         result = result + "{";
         if ((f.endsWith(".doc")) || (f.endsWith(".docx"))) {
           fileType = 1;
         }
         if ((f.endsWith(".xls")) || (f.endsWith(".xlsx"))) {
           fileType = 2;
         }
         if (f.endsWith(".ppt")) {
           fileType = 3;
         }
         if (f.endsWith(".mp4")) {
           fileType = 4;
         }
         result = result + "\"filetype\": " + fileType + ",";
         result = result + "\"filename\": " + f;
         result = result + "},";
       }
     }
     if (result.endsWith(","))
       result = result.substring(0, result.length() - 1);
     result = "]";
     sendResponse(Integer.valueOf(0), result);
     return "success";
   }
 
   public PDAPropertyService getPdaPropertyService() {
     return this.pdaPropertyService;
   }
 
   public void setPdaPropertyService(PDAPropertyService pdaPropertyService) {
     this.pdaPropertyService = pdaPropertyService;
   }
 }

