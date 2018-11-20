 package interfaces.hwinterface.interfaces.equt.sender;
 
 import interfaces.hwinterface.interfaces.equt.pojo.EqutChangeDevmapStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutCompleteStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutCurrentDevmapStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutFalshLedStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutInspectStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutIpConfigStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutResetStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutTagBundingStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutTagOnLineStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutTagReadStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutTagUnBundingStruct;
 import interfaces.hwinterface.interfaces.equt.pojo.EqutVersionUpdateStruct;
 import interfaces.hwinterface.socketClient.sender.UDPSocketSender;
 import java.io.PrintStream;
 import java.util.List;
 import manage.equt.pojo.EqutInfoBean;
 import manage.point.pojo.PointEventInfoBean;
 import manage.point.pojo.PointInfoBean;
 
 public class EqutSender
 {
   public static List<PointEventInfoBean> getEqutInspectInfo(EqutInfoBean equtInfoBean)
     throws Exception
   {
     EqutInspectStruct eis = new EqutInspectStruct();
     return eis.getEventList();
   }
 
   public static Boolean setEqutIpConfig(EqutInfoBean equtInfoBean)
     throws Exception
   {
     return true;
    
   }
 
   public static Boolean equtReboot(EqutInfoBean equtInfoBean)
     throws Exception
   {
     return true;
   }
 
   public static Boolean equtFalseLED(PointInfoBean point, short LED)
     throws Exception
   {
     return true;
   }
 
   public static boolean hasJumpPoint(PointInfoBean point)
     throws Exception
   {
     return true;
   }
 
   public static boolean unBunding(PointInfoBean point) throws Exception {
	   return true;
   }
 
   public static boolean Bunding(PointInfoBean point)
     throws Exception
   {
	   return true;
   }
 
   public static String readTag(PointInfoBean point)
     throws Exception
   {
     return "";
   }
 
   public static String readPointFibercode(PointInfoBean point)
     throws Exception
   {
     return "";
   }
 
   public static PointEventInfoBean analyzePointEventState(PointEventInfoBean point) throws Exception {
     EqutTagReadStruct etr = new EqutTagReadStruct(point);
     Thread.sleep(80L);
     etr = (EqutTagReadStruct)UDPSocketSender.sendMeassage(point.getEip(), etr);
     String[] codes = etr.getFibercode();
     String tagCode = codes[0];
     String pointCode = codes[1];
     if (point.getState().equals("2")){
    	 point.setFibercode(pointCode);
     }else {
       point.setFibercode(tagCode);
     }
     point.setState(etr.analyzePointAlarmState(Integer.parseInt(point.getState()))+"");
     return point;
   }
 
 
   public static boolean isChangOdm(PointInfoBean point)
     throws Exception
   {
	   return true;
   }
 
 
   public static EqutCompleteStruct getDevmap(EqutInfoBean e) {
     return null;
   }
 
   public static boolean changeDevmap(PointEventInfoBean point) {
     try {
       EqutCompleteStruct ecd = new EqutCompleteStruct();
       ecd = (EqutCompleteStruct)UDPSocketSender.sendMeassage(point.getEip(), ecd);
       Thread.sleep(80L);
 
       EqutCurrentDevmapStruct ecurd = new EqutCurrentDevmapStruct();
       ecurd = (EqutCurrentDevmapStruct)UDPSocketSender.sendMeassage(point.getEip(), ecurd);
       Thread.sleep(80L);
       int[][] equtDevmap = ecd.parseDevmap();
       int[][] curDevmap = ecurd.parseDevmap();
       String pid = point.getPid();
       int i1 = Integer.parseInt(pid.substring(0, 2));
       int i2 = Integer.parseInt(pid.substring(2, 4));
 
       equtDevmap[(i1 - 1)][(i2 - 1)] = curDevmap[(i1 - 1)][(i2 - 1)];
 
       EqutChangeDevmapStruct changeDev = new EqutChangeDevmapStruct(equtDevmap);
       changeDev = (EqutChangeDevmapStruct)UDPSocketSender.sendMeassage(point.getEip(), changeDev);
       if (changeDev != null)
         return true;
     } catch (Exception e) {
       e.printStackTrace();
       return false;
     }
 
     return false;
   }
 
   public static boolean changeDevmap(PointInfoBean point) {
     return false;
   }
}