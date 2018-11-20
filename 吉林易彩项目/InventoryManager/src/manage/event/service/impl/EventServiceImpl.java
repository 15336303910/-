/*    */ package manage.event.service.impl;
/*    */ 
/*    */ import base.database.DataBase;
/*    */ import base.exceptions.DataAccessException;
/*    */ import manage.event.pojo.EventInfoBean;
/*    */ import manage.event.service.EventService;
/*    */ 
/*    */ public class EventServiceImpl extends DataBase
/*    */   implements EventService
/*    */ {
/*    */   private static final String INSERT_EVENT = "event.insertEvent";
/*    */ 
/*    */   public void saveEvent(EventInfoBean event)
/*    */     throws DataAccessException
/*    */   {
/* 14 */     insert("event.insertEvent", event);
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.event.service.impl.EventServiceImpl
 * JD-Core Version:    0.5.3
 */