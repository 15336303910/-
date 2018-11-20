/*    */ package manage.poleline.pojo;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class SusLocationBean
/*    */ {
/*    */   private Integer SusId;
/*    */   private String lon;
/*    */   private String lat;
/*    */   private List<PoleInfoBean> poleList;
/*    */   private List<SupportInfoBean> supList;
/*    */   private List<SuspensionWireSegInfoBean> susSegList;
/*    */ 
/*    */   public Integer getSusId()
/*    */   {
/* 28 */     return this.SusId;
/*    */   }
/*    */ 
/*    */   public void setSusId(Integer susId) {
/* 32 */     this.SusId = susId;
/*    */   }
/*    */ 
/*    */   public List<PoleInfoBean> getPoleList() {
/* 36 */     return this.poleList;
/*    */   }
/*    */ 
/*    */   public void setPoleList(List<PoleInfoBean> poleList) {
/* 40 */     this.poleList = poleList;
/*    */   }
/*    */ 
/*    */   public List<SupportInfoBean> getSupList() {
/* 44 */     return this.supList;
/*    */   }
/*    */ 
/*    */   public void setSupList(List<SupportInfoBean> supList) {
/* 48 */     this.supList = supList; }
/*    */ 
/*    */   public List<SuspensionWireSegInfoBean> getSusSegList() {
/* 51 */     return this.susSegList; }
/*    */ 
/*    */   public void setSusSegList(List<SuspensionWireSegInfoBean> susSegList) {
/* 54 */     this.susSegList = susSegList;
/*    */   }
/*    */ 
/*    */   public String getLon() {
/* 58 */     return this.lon;
/*    */   }
/*    */ 
/*    */   public void setLon(String lon) {
/* 62 */     this.lon = lon;
/*    */   }
/*    */ 
/*    */   public String getLat() {
/* 66 */     return this.lat;
/*    */   }
/*    */ 
/*    */   public void setLat(String lat) {
/* 70 */     this.lat = lat;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.poleline.pojo.SusLocationBean
 * JD-Core Version:    0.5.3
 */