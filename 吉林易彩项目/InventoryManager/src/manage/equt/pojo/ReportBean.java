/*    */ package manage.equt.pojo;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ public class ReportBean
/*    */ {
/*    */   private String name;
/*    */   private Integer num;
/*    */   List<ReportBean> reports;
/*    */ 
/*    */   public String getName()
/*    */   {
/* 11 */     return this.name; }
/*    */ 
/*    */   public void setName(String name) {
/* 14 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public List<ReportBean> getReports() {
/* 18 */     return this.reports; }
/*    */ 
/*    */   public void setReports(List<ReportBean> reports) {
/* 21 */     this.reports = reports; }
/*    */ 
/*    */   public Integer getNum() {
/* 24 */     return this.num; }
/*    */ 
/*    */   public void setNum(Integer num) {
/* 27 */     this.num = num;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.equt.pojo.ReportBean
 * JD-Core Version:    0.5.3
 */