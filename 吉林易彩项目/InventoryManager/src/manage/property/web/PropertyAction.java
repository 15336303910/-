/*    */ package manage.property.web;
/*    */ 
/*    */ import base.exceptions.DataAccessException;
/*    */ import base.util.ErrorMessage;
/*    */ import base.web.PaginationAction;
/*    */ import manage.equt.web.EqutAction;
/*    */ import manage.property.pojo.PropertyInfoBean;
/*    */ import manage.property.service.PropertyService;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class PropertyAction extends PaginationAction
/*    */ {
/* 14 */   private static final Logger log = Logger.getLogger(EqutAction.class);
/*    */   private ErrorMessage errorMessage;
/*    */   private PropertyInfoBean property;
/*    */   private PropertyService propertyService;
/*    */   private String dir;
/*    */   private String sort;
/*    */   private Integer start;
/*    */   private Integer limit;
/*    */ 
/*    */   public String getPropertyInfo()
/*    */   {
/*    */     try
/*    */     {
/* 42 */       if (this.property == null) {
/* 43 */         this.property = new PropertyInfoBean();
/*    */       }
/* 45 */       this.property.setLimit(this.limit);
/* 46 */       this.property.setStart(this.start);
/* 47 */       this.property.setSort(this.sort);
/* 48 */       this.property.setDir(this.dir);
/* 49 */       this.property = this.propertyService.getproperty(this.property);
/*    */     } catch (DataAccessException e) {
/* 51 */       log.error("PropertyAction.getPropertyInfo 获取信息异常...", e);
/* 52 */       this.errorMessage = new ErrorMessage();
/* 53 */       this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
/* 54 */       return "error";
/*    */     }
/*    */ 
/* 57 */     return "getPropertyInfo";
/*    */   }
/*    */ 
/*    */   public PropertyInfoBean getProperty()
/*    */   {
/* 66 */     return this.property; }
/*    */ 
/*    */   public void setProperty(PropertyInfoBean property) {
/* 69 */     this.property = property; }
/*    */ 
/*    */   public PropertyService getPropertyService() {
/* 72 */     return this.propertyService; }
/*    */ 
/*    */   public void setPropertyService(PropertyService propertyService) {
/* 75 */     this.propertyService = propertyService; }
/*    */ 
/*    */   public String getDir() {
/* 78 */     return this.dir; }
/*    */ 
/*    */   public void setDir(String dir) {
/* 81 */     this.dir = dir; }
/*    */ 
/*    */   public String getSort() {
/* 84 */     return this.sort; }
/*    */ 
/*    */   public void setSort(String sort) {
/* 87 */     this.sort = sort; }
/*    */ 
/*    */   public Integer getStart() {
/* 90 */     return this.start; }
/*    */ 
/*    */   public void setStart(Integer start) {
/* 93 */     this.start = start; }
/*    */ 
/*    */   public Integer getLimit() {
/* 96 */     return this.limit; }
/*    */ 
/*    */   public void setLimit(Integer limit) {
/* 99 */     this.limit = limit;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.property.web.PropertyAction
 * JD-Core Version:    0.5.3
 */