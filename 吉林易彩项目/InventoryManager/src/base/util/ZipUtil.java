package base.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipInputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

/**
 * 文件、文件夹压缩为 zip包 <br />
 * zip包解压 <br />  
 * 依赖jar包 <br />
 * <dependency> 
 *  <groupId>org.apache.ant</groupId>
 *  <artifactId>apache-ant-zip</artifactId> 
 *  <version>1.8.0</version>
 * </dependency>
 */

public class ZipUtil {

    private static final int BUFFER = 8192;
	public static final String FILE_SEPARATOR = System
	.getProperty("file.separator");

    /**
     * 设置压缩编码，解决中文文件名乱码问题
     */
    private static final String encoding = System.getProperty("sun.jnu.encoding");

    static {
        /**
         * 解压依据的编码是sun.zip.encoding 所以需要设置 解压编码
         */
        System.setProperty("sun.zip.encoding", encoding);
    }


    /**
     * 释放资源
     * @param resources
     */
    private static void closeResource(Closeable... resources){
        if(resources==null || resources.length < 1) return;
        for (Closeable cur : resources) {
            if(cur == null) continue;
            if(cur instanceof ZipInputStream){
                ZipInputStream zis = (ZipInputStream)cur;
                try {
                    zis.closeEntry();
                } catch (IOException e) {
                }
            }
            if(cur instanceof ZipOutputStream){
                ZipOutputStream zos = (ZipOutputStream)cur;
                try {
                    zos.closeEntry();
                } catch (IOException e) {
                }
            }
            try {
                cur.close();
            } catch (IOException e) {
            }
        }
    }

    /**
     * 执行压缩
     * @param file
     * @param out
     * @param basedir
     */
    private static void doCompress(File file, ZipOutputStream out, String basedir) {
        if (!file.exists()){
            return;
        }
        if (file.isDirectory()) {
            //压缩目录
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                doCompress(files[i], out, basedir + file.getName() + "/");
            }
        } else {
            //压缩文件
            BufferedInputStream bis = null;
            try {
                bis = new BufferedInputStream(new FileInputStream(file));
                ZipEntry entry = new ZipEntry(basedir + file.getName());
                out.putNextEntry(entry);
                int count;
                byte data[] = new byte[BUFFER];
                while ((count = bis.read(data, 0, BUFFER)) != -1) {
                    out.write(data, 0, count);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }finally{
                closeResource(bis);
            }
        }
    }

    /**
     * 获取当前时间
     * @return
     */
    private static String getNowStr(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 配置压缩属性
     * @param out
     */
    private static void configCompressProperties(ZipOutputStream out){
        //设置文件名编码方式
        out.setEncoding(encoding);
        //设置压缩说明
        out.setComment("\n压缩时间："+getNowStr()+" \n");
    }

    /**
     * Zip压缩文件或者文件夹
     * @param srcPathName 待压缩文件或者文件夹路径
     * @param zipFileName 压缩后压缩文件存放路径
     */
    public static void zipFile(String srcPathName, String zipFileName) {
        File file = new File(srcPathName);
        File zipFile = new File(zipFileName);
        if (!file.exists()){
            throw new RuntimeException(srcPathName + "不存在！");
        }
        boolean isSuccessful = true;
        ZipOutputStream out = null;
        try {
            CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(zipFile), new CRC32());
            out = new ZipOutputStream(cos);
            //配置压缩属性
            configCompressProperties(out);
            //执行压缩
            doCompress(file, out, "");
        } catch (Exception e) {
            isSuccessful = false;
            throw new RuntimeException(e);
        }finally{
            closeResource(out);
            System.out.println("压缩处理"+(isSuccessful ? "成功！": "失败！"));
        }
    }

    /**
	 * 将指定的文件解压缩到指定的文件夹，解压后的文件夹目录和给定的压缩文件名相同.
	 * 
	 * @param zipFilePath
	 *            全路径
	 * @param unZipDirectory
	 *            全路径
	 * @return 解压缩文件是否成功.
	 * @throws IOException
	 */
	public static boolean unZipFile(String zipFilePath, String unZipDirectory)
			throws IOException {
		ZipFile zipFile = new ZipFile(zipFilePath);
		Enumeration<?> entries = zipFile.getEntries();
		if (zipFile == null) {
			return false;
		}
		while (entries.hasMoreElements()) {
			ZipEntry zipEntry = (ZipEntry) entries.nextElement();			
			File f = new File(unZipDirectory + FILE_SEPARATOR
					+ zipEntry.getName());
			if (zipEntry.isDirectory()) 
			{
				if (!f.exists() && !f.mkdirs())
					throw new IOException("Couldn't create directory: " + f);
			} else {
				BufferedInputStream is = null;
				BufferedOutputStream os = null;
				try {
					is = new BufferedInputStream(zipFile
							.getInputStream(zipEntry));
					File destDir = f.getParentFile();
					if (!destDir.exists() && !destDir.mkdirs()) {
						throw new IOException("Couldn't create dir " + destDir);
					}
					os = new BufferedOutputStream(new FileOutputStream(f));
					int b = -1;
					while ((b = is.read()) != -1) {
						os.write(b);
					}
				} finally {
					if (is != null)
						is.close();
					if (os != null)
						os.close();
				}
			}
		}
		zipFile.close();
		return true;
	}

    /**
     * 测试用例
     */
    public static void main(String[] args) {
        String srcDir = "F:\\xiyou";
        String tarFile = "F:\\xiyou.zip";
        ZipUtil.zipFile(srcDir, tarFile);
        try {
			ZipUtil.unZipFile(tarFile, "d:\\mytestdir_uncompress");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
