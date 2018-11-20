/*    */ package base.database.impl;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Modifier;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ 
/*    */ public class ReflectUtil
/*    */ {
/* 11 */   private static final Log logger = LogFactory.getLog(ReflectUtil.class);
/*    */ 
/*    */   public static void setFieldValue(Object target, String fname, Class ftype, Object fvalue)
/*    */   {
/* 15 */     if ((target == null) || 
/* 16 */       (fname == null) || 
/* 17 */       ("".equals(fname)) || (
/* 18 */       (fvalue != null) && 
/* 19 */       (!(ftype
/* 19 */       .isAssignableFrom(fvalue.getClass()))))) {
/* 20 */       return;
/*    */     }
/* 22 */     Class clazz = target.getClass();
/*    */     try {
/* 24 */       Method method = clazz.getDeclaredMethod("set" + 
/* 25 */         Character.toUpperCase(fname.charAt(0)) + 
/* 26 */         fname.substring(1), new Class[] { ftype });
/* 27 */       if (!(Modifier.isPublic(method.getModifiers()))) {
/* 28 */         method.setAccessible(true);
/*    */       }
/* 30 */       method.invoke(target, new Object[] { fvalue });
/*    */     }
/*    */     catch (Exception me) {
/* 33 */       if (logger.isDebugEnabled())
/* 34 */         logger.debug(me);
/*    */       try
/*    */       {
/* 37 */         Field field = clazz.getDeclaredField(fname);
/* 38 */         if (!(Modifier.isPublic(field.getModifiers()))) {
/* 39 */           field.setAccessible(true);
/*    */         }
/* 41 */         field.set(target, fvalue);
/*    */       } catch (Exception fe) {
/* 43 */         if (logger.isDebugEnabled())
/* 44 */           logger.debug(fe);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Object getFieldValue(Object target, String fname)
/*    */   {
/* 51 */     Object reslut = null;
/* 52 */     if ((target == null) || (fname == null) || ("".equals(fname))) {
/* 53 */       return null;
/*    */     }
/* 55 */     Class clazz = target.getClass();
/*    */     try {
/* 57 */       Field field = clazz.getDeclaredField(fname);
/* 58 */       reslut = field.get(fname);
/*    */     }
/*    */     catch (Exception me) {
/* 61 */       if (logger.isDebugEnabled()) {
/* 62 */         logger.debug(me);
/*    */       }
/*    */     }
/* 65 */     return reslut;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.database.impl.ReflectUtil
 * JD-Core Version:    0.5.3
 */