/*    */ package interfaces.hwinterface.interfaces.equt.pojo;
/*    */ 
/*    */ import base.util.CommonUtil;
/*    */ import interfaces.hwinterface.interfaceUtil.pojo.DataStruct;
/*    */ import interfaces.hwinterface.interfaceUtil.util.ValueUtil;
/*    */ import java.util.Date;
/*    */ import manage.point.pojo.PointEventInfoBean;
/*    */ 
/*    */ public class EqutWebAlarmStruct extends DataStruct
/*    */ {
/*    */   public EqutWebAlarmStruct(DataStruct dataStruct)
/*    */   {
/* 14 */     super(dataStruct);
/*    */   }
/*    */ 
/*    */   public PointEventInfoBean getPointWebAlarmBean(String eip) {
/* 18 */     PointEventInfoBean pam = new PointEventInfoBean();
/* 19 */     String strPandS = ValueUtil.bytesToHexString(this.datload);
/* 20 */     int pNo = 0;
/* 21 */     String state = "";
/* 22 */     String pid = "";
/* 23 */     if (this.p1 == 0) {
/* 24 */       pam.setType("0");
/* 25 */       pNo = Integer.parseInt(strPandS.substring(0, 2), 16);
/* 26 */       state = strPandS.substring(3);
/* 27 */       pid = CommonUtil.creatPid(this.u32bdg, this.u32dev, pNo);
/* 28 */     } else if (this.p1 == 2) {
/* 29 */       pam.setType("1");
/* 30 */       state = strPandS;
/* 31 */       pid = CommonUtil.creatPid(this.u32bdg, this.u32dev);
/* 32 */     } else if (this.p1 == 1) {
/* 33 */       pam.setType("2");
/* 34 */       state = strPandS;
/* 35 */       pid = CommonUtil.creatPid(this.u32bdg);
/*    */     }
/* 37 */     pam.setPid(pid);
/* 38 */     pam.setEip(eip);
/* 39 */     switch (Integer.parseInt(state))
/*    */     {
/*    */     case 0:
/* 41 */       pam.setState("2");
/* 42 */       break;
/*    */     case 1:
/* 45 */       pam.setState("4");
/*    */     }
/*    */ 
/* 50 */     pam.setAlarmTime(new Date());
/* 51 */     pam.setIsLatest("1");
/* 52 */     pam.setUserId(Integer.valueOf(-100));
/* 53 */     return pam;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     interfaces.hwinterface.interfaces.equt.pojo.EqutWebAlarmStruct
 * JD-Core Version:    0.5.3
 */