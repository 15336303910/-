/*    */ package manage.property.service.impl;
/*    */ 
/*    */ import base.database.DataBase;
/*    */ import base.exceptions.DataAccessException;
/*    */ import java.util.List;
/*    */ import manage.property.pojo.PropertyInfoBean;
/*    */ import manage.property.service.PropertyService;
/*    */ 
/*    */ public class PropertyServiceImpl extends DataBase
/*    */   implements PropertyService
/*    */ {
/* 11 */   private static String GET_PROPERTY = "property.getProperty";
/* 12 */   private static String GET_PROPERTY_TOTAL = "property.getPropertyTotal";
/*    */ 
/*    */   public PropertyInfoBean getproperty(PropertyInfoBean property)
/*    */     throws DataAccessException
/*    */   {
/* 17 */     List list = getObjects(GET_PROPERTY, property);
/* 18 */     int total = getCount(GET_PROPERTY_TOTAL, property);
/* 19 */     property = new PropertyInfoBean();
/* 20 */     property.setPropertys(list);
/* 21 */     property.setTotal(total);
/* 22 */     return property;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     manage.property.service.impl.PropertyServiceImpl
 * JD-Core Version:    0.5.3
 */