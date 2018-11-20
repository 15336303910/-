package interfaces.pdainterface.file.action;

import base.web.InterfaceAction;
import interfaces.pdainterface.file.pojo.FileInfoBean;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class PDAFile extends InterfaceAction
{
  private static final long serialVersionUID = -3486300674796123267L;
  private static final Logger log = Logger.getLogger(PDAFile.class);

  public String getFileList() {
    FileInfoBean file = (FileInfoBean)getRequestObject(FileInfoBean.class);
    String filename = null;
    if ((file != null) && (file.getFilename() != null) && (!(file.getFilename().equals("")))) {
      filename = file.getFilename();
    }
    List result = new ArrayList();
    String filePath = getServletContext().getRealPath("/") + "file";
    File dir = new File(filePath);
    String[] fileList = dir.list();
    if (fileList != null) {
      for (String f : fileList)
      {
        int fileType = 0;
        if ((f.endsWith(".doc")) || (f.endsWith(".docx"))) {
          fileType = 1;
        }
        if ((f.endsWith(".xls")) || (f.endsWith(".xlsx"))) {
          fileType = 2;
        }
        if (f.endsWith(".ppt")) {
          fileType = 3;
        }
        if (f.endsWith(".mp4")) {
          fileType = 4;
        }
        if ((filename == null) || ((filename != null) && (f.toLowerCase().contains(filename.toLowerCase())))) {
          FileInfoBean localFile = new FileInfoBean();
          localFile.setFiletype(Integer.valueOf(fileType));
          localFile.setFilename(f);
          result.add(localFile);
        }
      }
    }
    if (result.size() == 0)
      sendResponse(Integer.valueOf(2), "抱歉，由于学院刚刚建成，未收录相关文档。");
    else {
      sendResponse(Integer.valueOf(0), result);
    }
    return "success";
  }
}