/*     */ package base.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class PropertiesUtil
/*     */ {
/*  16 */   private static Logger logger = Logger.getLogger(PropertiesUtil.class);
/*     */   Properties configInfo;
/*     */   private static PropertiesUtil instance;
/*     */ 
/*     */   public static PropertiesUtil getInstance()
/*     */   {
/*  20 */     if (instance == null) {
/*  21 */       instance = new PropertiesUtil();
/*     */     }
/*  23 */     return instance;
/*     */   }
/*     */ 
/*     */   public PropertiesUtil() {
/*  27 */     Properties pro = ReadServerConfig();
/*  28 */     setConfigInfo(pro);
/*     */   }
/*     */ 
/*     */   public Properties ReadServerConfig()
/*     */   {
/*  33 */     String url = super.getClass().getResource("/").getPath() + "dbconfig.properties";
/*     */ 
/*  35 */     Properties pro = new Properties();
/*     */     try {
/*  37 */       InputStream in = new BufferedInputStream(new FileInputStream(url));
/*  38 */       pro.load(in);
/*  39 */       in.close();
/*     */     } catch (IOException e) {
/*  41 */       logger.error("无法获取配置文件" + e.getMessage());
/*     */     }
/*     */ 
/*  44 */     return pro;
/*     */   }
/*     */ 
/*     */   public static Properties readProperties(String filePath)
/*     */   {
/*  49 */     Properties props = new Properties();
/*  50 */     InputStream ips = null;
/*     */     try {
/*  52 */       ips = new BufferedInputStream(new FileInputStream(filePath));
/*  53 */       props.load(ips);
/*     */     } catch (FileNotFoundException e) {
/*  55 */       logger.error("未找到" + filePath + "文件；" + e.getMessage());
/*     */     } catch (IOException e) {
/*  57 */       logger.error("PropertiesUtil.readProperties" + e.getMessage());
/*     */     } finally {
/*     */       try {
/*  60 */         ips.close();
/*     */       } catch (IOException e) {
/*  62 */         e.printStackTrace();
/*  63 */         logger.error("输入关闭出错");
/*     */       }
/*     */     }
/*  66 */     return props;
/*     */   }
/*     */ 
/*     */   public static boolean writeProperties(Properties props, String filePath, Map<String, String> keyVal, boolean over)
/*     */   {
/*  77 */     boolean flag = false;
/*  78 */     FileOutputStream fos = null;
/*  79 */     if (over)
/*     */       try {
/*  81 */         fos = new FileOutputStream(filePath);
/*     */ 
/*  84 */         for (Object str : keyVal.keySet().toArray()) {
/*  85 */           props.setProperty(str.toString(), (String)keyVal.get(str));
/*     */         }
/*  87 */         props.store(fos, "set");
/*  88 */         flag = true;
/*     */       } catch (IOException e) {
/*  90 */         e.printStackTrace();
/*     */       } finally {
/*     */         try {
/*  93 */           fos.close();
/*     */         } catch (IOException e) {
/*  95 */           e.printStackTrace();
/*  96 */           logger.error("输出流无法关闭");
/*     */         }
/*     */       }
/*     */     else {
/*     */       try {
/* 101 */         fos = new FileOutputStream(filePath);
/* 102 */         for (Object key : keyVal.keySet().toArray()) {
/* 103 */           props.setProperty(key.toString(), ((String)keyVal.get(key)).toString());
/*     */         }
/* 105 */         props.store(fos, "");
/* 106 */         flag = true;
/*     */       } catch (FileNotFoundException e) {
/* 108 */         e.printStackTrace();
/*     */       } catch (IOException e) {
/* 110 */         e.printStackTrace();
/*     */       }
/*     */       finally {
/*     */         try {
/* 114 */           fos.close();
/*     */         } catch (IOException e) {
/* 116 */           e.printStackTrace();
/* 117 */           logger.error("输出流关闭出错");
/*     */         }
/*     */       }
/*     */     }
/* 121 */     return flag;
/*     */   }
/*     */ 
/*     */   public Properties getConfigInfo()
/*     */   {
/* 126 */     return this.configInfo;
/*     */   }
/*     */ 
/*     */   public void setConfigInfo(Properties configInfo) {
/* 130 */     this.configInfo = configInfo;
/*     */   }
/*     */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.PropertiesUtil
 * JD-Core Version:    0.5.3
 */