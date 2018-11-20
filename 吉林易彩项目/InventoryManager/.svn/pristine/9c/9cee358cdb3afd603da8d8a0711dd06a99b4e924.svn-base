/*    */ package interfaces.pdainterface.property.service.impl;
/*    */ 
/*    */ import base.database.DataBase;
/*    */ import base.exceptions.DataAccessException;
/*    */ import interfaces.pdainterface.property.service.PDAPropertyService;
/*    */ import java.util.List;
/*    */ import manage.equt.pojo.EqutInfoBean;
/*    */ import manage.generator.pojo.HighFrequencySwitchingPowerSupplyInfoBean;
/*    */ import manage.property.pojo.PropertyInfoBean;
/*    */ 
/*    */ public class PDAPropertyServiceImpl extends DataBase
/*    */   implements PDAPropertyService
/*    */ {
/*    */   private static final String GET_PROPERTY = "pdaproperty.getProperty";
/*    */   private static final String UPDATE_PROPERTY = "pdaproperty.updateProperty";
/*    */   private static final String INSERT_PROPERTY = "pdaproperty.insertProperty";
/*    */   private static final String getPower = "pdagenerator.getHighFrequencySwitchingPowerSupply";
/*    */   private static final String getEqut = "pdaequt.getEqut";
/*    */ 
/*    */   public List<PropertyInfoBean> getProperty(PropertyInfoBean property)
/*    */     throws DataAccessException
/*    */   {
/* 21 */     return getObjects("pdaproperty.getProperty", property);
/*    */   }
/*    */ 
/*    */   public int insertProperty(PropertyInfoBean property) throws DataAccessException {
/* 25 */     return ((Integer)insert("pdaproperty.insertProperty", property)).intValue();
/*    */   }
/*    */ 
/*    */   public int updateProperty(PropertyInfoBean property) throws DataAccessException {
/* 29 */     return update("pdaproperty.updateProperty", property);
/*    */   }
/*    */ 
/*    */   public PropertyInfoBean getObject(PropertyInfoBean property) throws DataAccessException
/*    */   {
/* 34 */     String proper_zcbqh = property.getProper_zcbqh();
/* 35 */     EqutInfoBean e = new EqutInfoBean();
/* 37 */     e = (EqutInfoBean)getObject("pdaequt.getEqut", e);
/* 38 */     if (e != null) {
/* 39 */       if (e.getEtype().equals("1")) {
/* 40 */         property.setType("2");
/* 41 */         property.setOdf(e);
/*    */       } else {
/* 43 */         property.setType("1");
/* 44 */         property.setOcc(e);
/*    */       }
/*    */     } else {
/* 47 */       HighFrequencySwitchingPowerSupplyInfoBean power = new HighFrequencySwitchingPowerSupplyInfoBean();
/* 48 */       power.setFixedAssetsCode(proper_zcbqh);
/* 49 */       power = (HighFrequencySwitchingPowerSupplyInfoBean)getObject("pdagenerator.getHighFrequencySwitchingPowerSupply", power);
/* 50 */       if (power != null) {
/* 51 */         property.setType("3");
/* 52 */         property.setSwitching(power);
/*    */       } else {
/* 54 */         property.setType("0");
/*    */       }
/*    */     }
/*    */ 
/* 58 */     return property;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.pdainterface.property.service.impl.PDAPropertyServiceImpl
 * JD-Core Version:    0.5.3
 */