/*    */ package interfaces.pdainterface.interfaceUtil;
/*    */ 
/*    */ public class ReturnData
/*    */ {
/*    */   private Integer result;
/*    */   private String info;
/*    */ 
/*    */   public Integer getResult()
/*    */   {
/*  8 */     return this.result; }
/*    */ 
/*    */   public void setResult(Integer result) {
/* 11 */     this.result = result; }
/*    */ 
/*    */   public String getInfo() {
/* 14 */     return this.info; }
/*    */ 
/*    */   public void setInfo(String info) {
/* 17 */     this.info = info;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 22 */     return "JsonResponse [result=" + this.result + ", info=" + this.info + "]";
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.pdainterface.interfaceUtil.ReturnData
 * JD-Core Version:    0.5.3
 */