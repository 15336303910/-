/*    */ package base.exceptions;
/*    */ 
/*    */ public class DataAccessIntegrityViolationException extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 1345111443834498940L;
/*    */ 
/*    */   public DataAccessIntegrityViolationException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public DataAccessIntegrityViolationException(Exception e)
/*    */   {
/* 12 */     super(e);
/*    */   }
/*    */ 
/*    */   public DataAccessIntegrityViolationException(String msg) {
/* 16 */     super(msg);
/*    */   }
/*    */ 
/*    */   public DataAccessIntegrityViolationException(String msg, Throwable ex) {
/* 20 */     super(msg, ex);
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.exceptions.DataAccessIntegrityViolationException
 * JD-Core Version:    0.5.3
 */