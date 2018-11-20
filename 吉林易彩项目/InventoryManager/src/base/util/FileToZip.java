/*    */ package base.util;
/*    */ 
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.BufferedOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileInputStream;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import org.apache.tools.zip.ZipEntry;
/*    */ import org.apache.tools.zip.ZipOutputStream;
/*    */ 
/*    */ public final class FileToZip
/*    */ {
/*    */   public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName)
/*    */   {
/* 27 */     boolean flag = false;
/* 28 */     File sourceFile = new File(sourceFilePath);
/* 29 */     FileInputStream fis = null;
/* 30 */     BufferedInputStream bis = null;
/* 31 */     FileOutputStream fos = null;
/* 32 */     ZipOutputStream zos = null;
/*    */ 
/* 34 */     if (sourceFile.exists()) {
/*    */       try {
/* 36 */         File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
/* 37 */         if (zipFile.exists()) {
/* 38 */           zipFile.delete();
/*    */         }
/* 40 */         File[] sourceFiles = sourceFile.listFiles();
/* 41 */         int fileAmount = 0;
/* 42 */         for (int i = 0; i < sourceFiles.length; ++i) {
/* 43 */           if (sourceFiles[i].isFile()) {
/* 44 */             ++fileAmount;
/* 45 */             sourceFiles[i].deleteOnExit();
/*    */           }
/*    */         }
/* 48 */         if (fileAmount > 1) {
/* 49 */           fos = new FileOutputStream(zipFile);
/* 50 */           zos = new ZipOutputStream(new BufferedOutputStream(fos));
/* 51 */           zos.setEncoding("gbk");
/* 52 */           byte[] bufs = new byte[10240];
/* 53 */           for (int i = 0; i < sourceFiles.length; ++i)
/*    */           {
/* 55 */             if (!(sourceFiles[i].isFile()))
/*    */               continue;
/* 57 */             ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
/* 58 */             zos.putNextEntry(zipEntry);
/*    */ 
/* 60 */             fis = new FileInputStream(sourceFiles[i]);
/* 61 */             bis = new BufferedInputStream(fis, 10240);
/* 62 */             int read = 0;
/* 63 */             while ((read = bis.read(bufs, 0, 10240)) != -1) {
/* 64 */               zos.write(bufs, 0, read);
/*    */             }
/*    */           }
/*    */ 
/* 68 */           flag = true;
/*    */         }
/*    */       } catch (FileNotFoundException e) {
/* 71 */         e.printStackTrace();
/* 72 */         throw new RuntimeException(e);
/*    */       } catch (IOException e) {
/* 74 */         e.printStackTrace();
/* 75 */         throw new RuntimeException(e);
/*    */       }
/*    */       finally {
/*    */         try {
/* 79 */           if (bis != null) bis.close();
/* 80 */           if (zos != null) zos.close();
/*    */         } catch (IOException e) {
/* 82 */           e.printStackTrace();
/* 83 */           throw new RuntimeException(e);
/*    */         }
/* 85 */         sourceFile.deleteOnExit();
/*    */       }
/*    */     }
/* 88 */     return flag;
/*    */   }
/*    */ }

/* Location:           F:\下载\apache\tomcat1\webapps\InventoryManager\WEB-INF\classes\
 * Qualified Name:     base.util.FileToZip
 * JD-Core Version:    0.5.3
 */