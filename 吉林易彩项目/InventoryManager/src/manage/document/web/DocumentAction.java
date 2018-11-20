package manage.document.web;

import base.util.CommonUtil;
import base.util.ErrorMessage;
import base.web.PaginationAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;
import manage.document.pojo.DocEqutInfoBean;
import manage.document.pojo.DocJumpInfoBean;
import manage.document.pojo.DocPointInfoBean;
import manage.document.service.DocumentService;
import manage.tree.web.TreeAction;
import manage.user.pojo.UserInfoBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

public class DocumentAction extends PaginationAction
{
  private static final Logger log = Logger.getLogger(TreeAction.class);
  private ErrorMessage errorMessage;
  private DocEqutInfoBean dequtGrid;
  private DocPointInfoBean dpointGrid;
  private DocEqutInfoBean searchdequt;
  private DocPointInfoBean searchdpoint;
  private String modelName;
  private UserInfoBean userInfoBean;
  private String sort;
  private String dir;
  private Integer total;
  private Integer start;
  private Integer limit;
  private DocumentService documentService;
  private boolean success = false;

  private String verifyMsg = null;
  private String areano;
  private File[] excle;
  private String[] excleFileName;
  private List<String> ids;
  private List<DocEqutInfoBean> docs;
  private List<DocPointInfoBean> pointList;
  private List<DocJumpInfoBean> jumpList;
  private Integer col_amount;
  private static final int COL_AMOUNT = 8;
  private static final int POINT_AMOUNT = 12;

  public String loadDocEqutGrid()
    throws Exception
  {
    try
    {
      if (this.searchdequt == null) {
        this.searchdequt = new DocEqutInfoBean();
      }
      this.searchdequt.setSort(this.sort);
      this.searchdequt.setDir(this.dir);
      this.searchdequt.setStart(this.start);
      this.searchdequt.setLimit(this.limit);
      this.dequtGrid = this.documentService.loadDocumentEqutGrid(this.searchdequt);

      return "loadDocumentEqut";
    }
    catch (Exception e) {
      log.error("DocumentAction.loadDocumentEqut", e);
      e.printStackTrace();
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String importInfo() throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      List docs = new ArrayList();
      int n = 0;
      if ((this.excle != null) && (this.excle.length != 0))
      {
        for (int i = 0; i < this.excle.length; ++i) {
          File file = this.excle[i];
          String fileName = this.excleFileName[i];
          if (file != null) {
            String ftype = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if ("xls".equals(ftype)) {
              DocEqutInfoBean docEqut = new DocEqutInfoBean();
              docEqut.setAreano(this.areano);
              docEqut.setExcle(file);
              docEqut.setFilename(fileName);
              docEqut.setMp((this.userInfoBean == null) ? "" : this.userInfoBean.getUsername());
              docs.add(docEqut);
            } else {
              this.success = false;
              this.verifyMsg = "上传失败!存在文件类型不正确!";
              return "importInfo";
            }
          }
        }

        n = this.documentService.inportDocumentInfo(docs);
        if (n == this.excle.length) {
          this.success = true;

          StringBuffer description = new StringBuffer("用户");
          description.append(this.userInfoBean.getRealname());
          description.append("上传档案成功");

          return "importInfo";
        }
        this.success = false;
        this.verifyMsg = "上传失败!文件读取过程中出错!请联系管理员或重新尝试";
        return "importInfo";
      }

      this.success = false;
      this.verifyMsg = "上传失败!请选择至少一个Excel文档!";
      return "importInfo";
    }
    catch (Exception e)
    {
      log.error("DocumentAction.importInfo", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      this.success = false;
      this.verifyMsg = "发生了错误，请联系系统管理员！"; }
    return "importInfo";
  }

  public String importWorkerorder() throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      List docs = new ArrayList();
      int n = 0;

      if ((this.excle != null) && (this.excle.length != 0))
      {
        for (int i = 0; i < this.excle.length; ++i) {
          File file = this.excle[i];
          String fileName = this.excleFileName[i];
          if (file != null) {
            String ftype = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if ("xls".equals(ftype)) {
              DocEqutInfoBean docEqut = new DocEqutInfoBean();
              docEqut.setAreano(this.areano);
              docEqut.setExcle(file);
              docEqut.setFilename(fileName);
              docEqut.setMp((this.userInfoBean == null) ? "" : this.userInfoBean.getUsername());
              docs.add(docEqut);
            } else {
              this.success = false;
              this.verifyMsg = "上传失败!存在文件类型不正确!";
              return "importInfo";
            }
          }
        }
        String[] mnstr = this.modelName.split(",");

        n = this.documentService.inportDocumentInfoWorkerorder(docs, mnstr, this.userInfoBean);
        if (n == this.excle.length) {
          this.success = true;

          StringBuffer description = new StringBuffer("用户");
          description.append(this.userInfoBean.getRealname());
          description.append("上传档案成功");

          return "importInfo";
        }
        this.success = false;
        this.verifyMsg = "上传失败!文件读取过程中出错!请联系管理员或重新尝试";
        return "importInfo";
      }

      this.success = false;
      this.verifyMsg = "上传失败!请选择至少一个Excel文档!";
      return "importInfo";
    }
    catch (Exception e)
    {
      log.error("DocumentAction.importInfo", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      this.success = false;
      this.verifyMsg = "发生了错误，请联系系统管理员！"; }
    return "importInfo";
  }

  public String deleteDocument()
    throws Exception
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      int n = this.documentService.deleteDocEqutInfo(this.ids);
      int m = this.documentService.deleteDocEqutInfoJump(this.ids);
      if (n != 0) {
        this.success = true;
        this.verifyMsg = "您成功删除了&nbsp;" + this.ids.size() + "&nbsp;条设备信息!&nbsp" + (n - this.ids.size()) + "&nbsp;条端子信息!" + m + "&nbsp;条跳纤信息";

        StringBuffer description = new StringBuffer("用户");
        description.append(this.userInfoBean.getRealname());
        description.append("删除档案成功");
      }
      else {
        this.success = false;
        this.verifyMsg = "删除档案信息失败!";
      }
      return "delete";
    } catch (Exception e) {
      log.error("DocumentAction.deleteDocument", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      this.success = false;
      this.verifyMsg = "发生了错误，请联系系统管理员！"; }
    return "delete";
  }

  public String loadDocPointGrid()
    throws Exception
  {
    try
    {
      if (this.searchdpoint == null) {
        this.searchdpoint = new DocPointInfoBean();
      }
      this.searchdpoint.setSort(this.sort);
      this.searchdpoint.setDir(this.dir);
      this.searchdpoint.setStart(this.start);
      this.searchdpoint.setLimit(this.limit);
      this.dpointGrid = this.documentService.loadDocPointGrid(this.searchdpoint);
      return "loadDocPointGrid";
    } catch (Exception e) {
      log.error("DocumentAction.deleteDocument", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      this.success = false;
      this.verifyMsg = "发生了错误，请联系系统管理员！"; }
    return "loadDocPointGrid";
  }

  public String importData() throws Exception
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      int n = 0;
      this.docs = new ArrayList();
      this.pointList = new ArrayList();
      this.jumpList = new ArrayList();
      if ((this.excle != null) && (this.excle.length != 0))
      {
        for (int i = 0; i < this.excle.length; ++i) {
          File file = this.excle[i];
          String fileName = this.excleFileName[i];
          if (file != null) {
            String ftype = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            if ("xls".equals(ftype)) {
              POIFSFileSystem fs = null;
              HSSFWorkbook wb = null;
              fs = new POIFSFileSystem(new FileInputStream(file));
              wb = new HSSFWorkbook(fs);
              HSSFSheet sheet = wb.getSheetAt(0);
              HSSFRow row = sheet.getRow(1);
              HSSFCell cell = row.getCell(0);
              if (cell.getStringCellValue().equals("框号")) {
                System.out.println("######ODF##########");
                createEqutODF(sheet, fileName);
              } else {
                createEqutOCCNew(wb, fileName);
              }

            }

          }
          else
          {
            this.success = false;
            this.verifyMsg = "上传失败!存在文件类型不正确!";
            return "importInfo";
          }
        }
      }
      this.documentService.importDocumentEqutInfo(this.docs);
      this.documentService.importDocumentPointInfo(this.pointList);
      if (this.jumpList.size() != 0) {
        this.documentService.importDocumentJumpInfo(this.jumpList);
      }

      this.success = true;

      StringBuffer description = new StringBuffer("用户");
      description.append(this.userInfoBean.getRealname());
      description.append("上传档案成功");

      return "importInfo";
    }
    catch (Exception e)
    {
      log.error("DocumentAction.importInfo", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      this.success = false;
      this.verifyMsg = "发生了错误，请联系系统管理员！"; }
    return "importInfo";
  }

  public static String[] getLine(HSSFSheet sheet, int lineNo)
  {
    String[] line = new String[8];
    HSSFRow row = sheet.getRow(lineNo);
    DecimalFormat df = new DecimalFormat("#");

    for (int i = 0; i < 8; ++i) {
      HSSFCell cell = row.getCell(i);

      String str = getCellValue(cell);
      line[i] = str;
    }
    return line;
  }

  public static String[] getLine(List<CellRangeAddress> rangeList, HSSFSheet sheet, int lineNo) {
    String[] line = new String[8];
    HSSFRow row = sheet.getRow(lineNo);
    DecimalFormat df = new DecimalFormat("#");

    for (int i = 0; i < 8; ++i) {
      HSSFCell cell = row.getCell(i);
      try
      {
        String str = isCombineCell(rangeList, cell, sheet);
        line[i] = str;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return line;
  }

  public static List<CellRangeAddress> getCombineCell(Sheet sheet)
  {
    List list = new ArrayList();

    int sheetmergerCount = sheet.getNumMergedRegions();

    for (int i = 0; i < sheetmergerCount; ++i)
    {
      CellRangeAddress ca = sheet.getMergedRegion(i);
      list.add(ca);
    }
    return list;
  }

  public static String isCombineCell(List<CellRangeAddress> listCombineCell, Cell cell, Sheet sheet)
    throws Exception
  {
    if (cell != null) {
      int firstC = 0;
      int lastC = 0;
      int firstR = 0;
      int lastR = 0;
      String cellValue = null;
      Iterator localIterator = listCombineCell.iterator();
      while (true) { CellRangeAddress ca = (CellRangeAddress)localIterator.next();

        firstC = ca.getFirstColumn();
        lastC = ca.getLastColumn();
        firstR = ca.getFirstRow();
        lastR = ca.getLastRow();
        if ((cell.getRowIndex() >= firstR) && (cell.getRowIndex() <= lastR)) {
          if ((cell.getColumnIndex() >= firstC) && 
            (cell.getColumnIndex() <= lastC)) {
            Row fRow = sheet.getRow(firstR);
            Cell fCell = fRow.getCell(firstC);
            cellValue = getCellValue(fCell);
          }
        }
        else {
          cellValue = getCellValue(cell);

          return cellValue; } }
    }
    return "";
  }

  public static String getCellValue(Cell cell)
  {
    if (cell == null)
      return "";
    if (cell.getCellType() == 1)
      return cell.getStringCellValue().replace(".0", "");
    if (cell.getCellType() == 4)
      return String.valueOf(cell.getBooleanCellValue()).replace(".0", "");
    if (cell.getCellType() == 2)
      return cell.getCellFormula().replace(".0", "");
    if (cell.getCellType() == 0) {
      return String.valueOf(cell.getNumericCellValue()).replace(".0", "");
    }
    return "";
  }

  public static String createPid(String mod, String index) {
    String pid1 = mod;
    int pointIndex = Integer.parseInt(index);
    int pid2 = 0;
    int pid3 = 0;
    if (pointIndex % 12 == 0) {
      pid2 = pointIndex / 12;
      pid3 = 12;
    } else {
      pid2 = pointIndex / 12 + 1;
      pid3 = pointIndex % 12;
    }

    String pid = pid1 + ((pid2 > 9) ? pid2 : new StringBuilder("0").append(pid2).toString()) + 
      ((pid3 > 9) ? pid3 : new StringBuilder("0").append(pid3).toString());
    return pid;
  }

  public static String createPid18(String mod, String index) {
    int indexInt = Integer.parseInt(index);
    int pid1 = (indexInt % 72 == 0) ? indexInt / 72 : indexInt / 72 + 1;

    int pid2 = 0;
    int pid3 = 0;
    if (indexInt % 18 == 0) {
      pid2 = indexInt / 18 - ((pid1 - 1) * 4);
      pid3 = 18;
    } else {
      pid2 = indexInt / 18 + 1 - ((pid1 - 1) * 4);
      pid3 = indexInt % 18;
    }
    String pid = "0" + pid1 + ((pid2 > 9) ? pid2 : new StringBuilder("0").append(pid2).toString()) + 
      ((pid3 > 9) ? pid3 : new StringBuilder("0").append(pid3).toString());
    return pid;
  }

  public void createEqutODF(HSSFSheet sheet, String fileName) {
    DocEqutInfoBean ee = new DocEqutInfoBean();
    DocPointInfoBean pp = new DocPointInfoBean();
    DocJumpInfoBean jj = new DocJumpInfoBean();
    int amount = sheet.getLastRowNum();
    try {
      List rangeList = getCombineCell(sheet);

      String[] line = getLine(rangeList, sheet, 0);
      String equtStr = line[0].replaceAll(" ", "");
      equtStr = equtStr.replaceAll("\n", "");
      ee.setEname(equtStr);
      ee.setEcode(equtStr);
      ee.setDid(CommonUtil.getUuid());
      ee.setFilename(fileName);
      ee.setMp((this.userInfoBean == null) ? "" : this.userInfoBean.getUsername());
      ee.setEtype("1");
      ee.setEstat("3");
      ee.setAreano(this.areano);
      ee.setMtime(new Date());
      this.docs.add(ee);
      for (int i = 2; i <= amount; ++i) {
        pp = new DocPointInfoBean();
        jj = new DocJumpInfoBean();
        try {
          line = getLine(rangeList, sheet, i);
          pp.setPid(createPid("0" + line[0], line[1]));
          pp.setPcode(ee.getEcode() + line[0] + "框" + line[1]);
          String pserv = line[2];
          if (!(pserv.equals("")))
            pp.setPstat("4");
          else {
            pp.setPstat("1");
          }
          pp.setDirection("1");
          pp.setPtype("1");
          pp.setPserv(pserv);
          pp.setDid(ee.getDid());
          pp.setAreano(this.areano);
          pp.setMtime(new Date());
          pp.setMp(ee.getMp());

          if ((line[3] != null) && (!(line[3].equals("")))) {
            if (line[3].indexOf("箱") != -1) {
              pp.setOppo_ecode(line[3].substring(0, line[3].indexOf("箱") + 1));
              pp.setOppo_ename(line[3].substring(0, line[3].indexOf("箱") + 1));
              pp.setOppo_pcode(line[3]);
            } else if (line[3].indexOf("架") != -1) {
              pp.setOppo_ecode(line[3].substring(0, line[3].indexOf("架") + 1));
              pp.setOppo_ename(line[3].substring(0, line[3].indexOf("架") + 1));
              pp.setOppo_pcode(line[3]);
            } else {
              pp.setOppo_pcode(line[3]);
            }
          }

          if ((line[4] != null) && (!(line[4].equals(""))) && (line[5] != null) && (!(line[5].equals(""))) && (line[6] != null) && (!(line[6].equals(""))))
          {
            String j_ecode = "";
            String j_pcode = "";
            for (int k = 0; k < ee.getEcode().length(); ++k) {
              if ((ee.getEcode().charAt(k) >= '0') && (ee.getEcode().charAt(k) <= '9')) {
                j_ecode = ee.getEcode().substring(0, k) + line[4].replace("-", "") + "架";
                break;
              }
            }
            if ((line[5] != null) && (!(line[5].equals(""))) && (line[6] != null) && (!(line[6].equals(""))))
              j_pcode = j_ecode + line[5] + "框" + line[6];
            else {
              j_pcode = line[7];
            }
            pp.setNote(pp.getPcode() + "跳纤至" + j_pcode);
            jj.setEcode1(ee.getEcode());
            jj.setEcode2(j_ecode);
            jj.setEname1(ee.getEname());
            jj.setEname2(j_ecode);
            jj.setPcode1(pp.getPcode());
            jj.setPcode2(j_pcode);
            jj.setDid(ee.getDid());
            this.jumpList.add(jj);
          }
          this.pointList.add(pp);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace(); }
  }

  public void createEqutOCCNew(HSSFWorkbook wb, String fileName) {
    String[] equtinfo = new String[11];
    DocEqutInfoBean ee = new DocEqutInfoBean();
    DocPointInfoBean pp = new DocPointInfoBean();
    DocJumpInfoBean jj = new DocJumpInfoBean();
    for (int sheetNo = 0; sheetNo < 2; ++sheetNo) {
      HSSFSheet sheet = wb.getSheetAt(0);
      try {
        sheet = wb.getSheetAt(sheetNo);
      } catch (Exception e) {
        e.printStackTrace();
        return;
      }
      List rangeList = getCombineCell(sheet);

      int amount = 0;
      int q = 0;
      String[] line;
      String equtStr;
      if (sheetNo == 0)
      {
        line = getLine(rangeList, sheet, 0);

        equtStr = line[0].replaceAll(" ", "");
        equtStr = equtStr.replaceAll("\n", "");
        String station = equtStr.substring(equtStr.indexOf("局向：") + 3, equtStr.indexOf("箱号："));
        String code = equtStr.substring(equtStr.indexOf("箱号：") + 3, equtStr.indexOf("位置："));
        String addr = equtStr.substring(equtStr.indexOf("位置：") + 3, equtStr.indexOf("容量："));
        amount = Integer.parseInt(equtStr.substring(equtStr.indexOf("容量：") + 3, equtStr.length())) + 5;

        ee.setDid(CommonUtil.getUuid());
        for (int k = 0; k < fileName.length(); ++k) {
          if ((fileName.charAt(k) >= '0') && (fileName.charAt(k) <= '9')) {
            fileName = fileName.substring(0, k) + "GJX" + fileName.substring(k, fileName.length());
            break;
          }
        }
        ee.setFilename(fileName);
        ee.setMp((this.userInfoBean == null) ? "" : this.userInfoBean.getUsername());
        boolean flag = false;
        for (int l = 0; l < code.length(); ++l) {
          if ((code.charAt(l) >= '0') && (code.charAt(l) <= '9')) {
            flag = true;
          }
        }
        if (!(flag)) {
          this.success = false;
          this.verifyMsg = fileName + "文件数据错误，请核查后上传！";
          return;
        }

        ee.setEname(fileName.substring(0, fileName.indexOf(".")));
        ee.setEcode(fileName.substring(0, fileName.indexOf(".")));
        if (!(addr.equals("")))
          ee.setEaddr(addr);
        else if ((addr.equals("")) && (!(station.equals("")))) {
          ee.setEaddr(station);
        }
        ee.setEtype("3");
        ee.setEstat("3");
        ee.setAreano(this.areano);

        ee.setMtime(new Date());
        this.docs.add(ee);
        q = 4;
      }
      else {
        line = getLine(rangeList, sheet, 0);

        equtStr = line[0].replaceAll(" ", "");
        equtStr = equtStr.replaceAll("\n", "");
        try {
          amount = Integer.parseInt(equtStr.substring(equtStr.indexOf("容量：") + 3, equtStr.length())) + 5;
          q = 4;
        } catch (Exception e) {
          amount = 292;
          q = 3;
          e.printStackTrace();
        }
      }

      for (int i = 0; i < amount; ++i)
        if (i > q) {
          pp = new DocPointInfoBean();
          jj = new DocJumpInfoBean();
          try
          {
            String[] line1 = getLine(rangeList, sheet, i);

            pp.setPid(createPid("0" + (sheetNo + 1), line1[0]));
            pp.setPcode(ee.getEcode() + ((sheetNo == 0) ? "A" : "B") + "面" + line1[0]);
            String pserv = line1[1];
            if (!(pserv.equals("")))
              pp.setPstat("4");
            else {
              pp.setPstat("1");
            }
            pp.setDirection("1");
            pp.setPtype("1");
            pp.setPserv(pserv);
            pp.setDid(ee.getDid());
            pp.setAreano(this.areano);
            pp.setMtime(new Date());
            pp.setMp(ee.getMp());
            if ((line1[4] != null) && (!(line1[4].equals(""))))
            {
              String j_ecode = ee.getEcode();
              String j_pcode = "";
              if (line1[4].contains("A"))
                j_pcode = ee.getEcode() + "A面" + ((line1[4].contains("A面")) ? line1[4].replace("A面", "") : line1[4].replace("A", ""));
              else if (line1[4].contains("B"))
                j_pcode = ee.getEcode() + "B面" + ((line1[4].contains("B面")) ? line1[4].replace("B面", "") : line1[4].replace("B", ""));
              else {
                j_pcode = ee.getEcode() + ((sheetNo == 0) ? "A" : "B") + "面" + line1[4];
              }
              if (!(j_pcode.equals(pp.getPcode()))) {
                pp.setNote(((line1[5] == "") ? "" : new StringBuilder("局向:").append(line1[5]).toString()) + ((line1[6] == "") ? "" : new StringBuilder(" 用户缆-用户名:").append(line1[6]).toString()) + ((line1[7] == "") ? "" : new StringBuilder("用户缆-芯数:").append(line1[7]).toString()));
                jj.setEcode1(ee.getEcode());
                jj.setEcode2(j_ecode);
                jj.setEname1(ee.getEname());
                jj.setEname2(j_ecode);
                jj.setPcode1(pp.getPcode());
                jj.setPcode2(j_pcode);
                jj.setDid(ee.getDid());
                this.jumpList.add(jj);
              }
            }
            this.pointList.add(pp);
          } catch (Exception pserv) {
        	  pserv.printStackTrace();
          }
        }
    }
  }

  public void createEqutOCC3418(HSSFWorkbook wb, String fileName)
  {
    String[] equtinfo = new String[11];
    DocEqutInfoBean ee = new DocEqutInfoBean();
    DocPointInfoBean pp = new DocPointInfoBean();
    DocJumpInfoBean jj = new DocJumpInfoBean();
    for (int sheetNo = 0; sheetNo < 2; ++sheetNo) {
      HSSFSheet sheet = wb.getSheetAt(sheetNo);
      List rangeList = getCombineCell(sheet);
      ee = new DocEqutInfoBean();
      int amount = 0;

      String[] line = getLine(rangeList, sheet, 0);

      String equtStr = line[0].replaceAll(" ", "");
      equtStr = equtStr.replaceAll("\n", "");
      String station = equtStr.substring(equtStr.indexOf("局向：") + 3, equtStr.indexOf("箱号："));
      String code = equtStr.substring(equtStr.indexOf("箱号：") + 3, equtStr.indexOf("位置："));
      String addr = equtStr.substring(equtStr.indexOf("位置：") + 3, equtStr.indexOf("容量："));
      amount = Integer.parseInt(equtStr.substring(equtStr.indexOf("容量：") + 3, equtStr.length())) + 5;

      ee.setDid(CommonUtil.getUuid());
      for (int k = 0; k < fileName.length(); ++k) {
        if ((fileName.charAt(k) >= '0') && (fileName.charAt(k) <= '9')) {
          if (sheetNo == 0) {
            fileName = fileName.substring(0, k) + "GJX" + fileName.substring(k, fileName.indexOf(".")) + "A面" + fileName.substring(fileName.indexOf("."), fileName.length()); break;
          }
          fileName = fileName.replace("A", "B");

          break;
        }
      }
      ee.setFilename(fileName);
      ee.setMp((this.userInfoBean == null) ? "" : this.userInfoBean.getUsername());
      boolean flag = false;
      for (int l = 0; l < code.length(); ++l) {
        if ((code.charAt(l) >= '0') && (code.charAt(l) <= '9')) {
          flag = true;
        }
      }
      if (!(flag)) {
        this.success = false;
        this.verifyMsg = fileName + "文件数据错误，请核查后上传！";
        return;
      }

      ee.setEname(fileName.substring(0, fileName.indexOf(".")));
      ee.setEcode(fileName.substring(0, fileName.indexOf(".")));
      if (!(addr.equals("")))
        ee.setEaddr(addr);
      else if ((addr.equals("")) && (!(station.equals("")))) {
        ee.setEaddr(station);
      }
      ee.setEtype("3");
      ee.setEstat("3");
      ee.setAreano(this.areano);
      ee.setMtime(new Date());
      this.docs.add(ee);
      if (sheetNo == 0) {
        amount = 221;
      }
      for (int i = 5; i < amount; ++i) {
        pp = new DocPointInfoBean();
        jj = new DocJumpInfoBean();
        try {
          line = getLine(rangeList, sheet, i);

          if (sheetNo == 0)
            pp.setPid(createPid18("0" + (sheetNo + 1), line[0]));
          else {
            pp.setPid(createPid("01", line[0]));
          }
          pp.setPcode(ee.getEcode() + line[0]);
          String pserv = line[1];
          if (!(pserv.equals("")))
            pp.setPstat("4");
          else {
            pp.setPstat("1");
          }
          pp.setDirection("1");
          pp.setPtype("1");
          pp.setPserv(pserv);
          pp.setDid(ee.getDid());
          pp.setAreano(this.areano);
          pp.setMtime(new Date());
          pp.setMp(ee.getMp());
          if ((line[4] != null) && (!(line[4].equals(""))))
          {
            String j_pcode = "";
            if (line[4].contains("A")) {
              if (ee.getEcode().contains("A"))
                j_pcode = ee.getEcode().replace("A面", "") + "A面" + ((line[4].contains("A面")) ? line[4].replace("A面", "") : line[4].replace("A", ""));
              else
                j_pcode = ee.getEcode().replace("B面", "") + "A面" + ((line[4].contains("A面")) ? line[4].replace("A面", "") : line[4].replace("A", ""));
            }
            else if (line[4].contains("B")) {
              if (ee.getEcode().contains("A"))
                j_pcode = ee.getEcode().replace("A面", "") + "B面" + ((line[4].contains("B面")) ? line[4].replace("B面", "") : line[4].replace("B", ""));
              else {
                j_pcode = ee.getEcode().replace("B面", "") + "B面" + ((line[4].contains("B面")) ? line[4].replace("B面", "") : line[4].replace("B", ""));
              }
            }
            else {
              j_pcode = ee.getEcode() + line[4];
            }
            String j_ecode = j_pcode.substring(0, j_pcode.indexOf("面") + 1);
            if (!(j_pcode.equals(pp.getPcode()))) {
              if (j_pcode.contains("框")) {
                Integer num1 = Integer.valueOf(pp.getPcode().substring(pp.getPcode().indexOf("面") + 1, pp.getPcode().length()));
                Integer num2 = Integer.valueOf(Integer.valueOf(j_pcode.substring(j_pcode.indexOf("面") + 1, j_pcode.indexOf("框"))).intValue() - 1);
                Integer num3 = Integer.valueOf(j_pcode.substring(j_pcode.indexOf("框") + 1, j_pcode.length()));
                if (!(num1.equals(Integer.valueOf(num2.intValue() * 72 + num3.intValue())))) {
                  jj.setEcode1(ee.getEcode());
                  jj.setEcode2(j_ecode);
                  jj.setEname1(ee.getEname());
                  jj.setEname2(j_ecode);
                  jj.setPcode1(pp.getPcode());
                  jj.setPcode2(j_pcode);
                  jj.setDid(ee.getDid());
                  this.jumpList.add(jj);
                  pp.setNote(pp.getPcode() + "跳纤至" + j_pcode);
                }
              } else {
                jj.setEcode1(ee.getEcode());
                jj.setEcode2(j_ecode);
                jj.setEname1(ee.getEname());
                jj.setEname2(j_ecode);
                jj.setPcode1(pp.getPcode());
                jj.setPcode2(j_pcode);
                jj.setDid(ee.getDid());
                this.jumpList.add(jj);
                pp.setNote(pp.getPcode() + "跳纤至" + j_pcode);
              }
            }
          }
          this.pointList.add(pp);
        } catch (Exception e) {
          e.printStackTrace(); }
      }
    }
  }

  public ErrorMessage getErrorMessage() {
    return this.errorMessage;
  }

  public void setErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
  }

  public static Logger getLog() {
    return log;
  }

  public DocEqutInfoBean getDequtGrid() {
    return this.dequtGrid;
  }

  public void setDequtGrid(DocEqutInfoBean dequtGrid) {
    this.dequtGrid = dequtGrid;
  }

  public DocEqutInfoBean getSearchdequt() {
    return this.searchdequt;
  }

  public void setSearchdequt(DocEqutInfoBean searchdequt) {
    this.searchdequt = searchdequt;
  }

  public String getSort() {
    return this.sort;
  }

  public void setSort(String sort) {
    this.sort = sort;
  }

  public String getDir() {
    return this.dir;
  }

  public void setDir(String dir) {
    this.dir = dir;
  }

  public Integer getTotal() {
    return this.total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  public Integer getStart() {
    return this.start;
  }

  public void setStart(Integer start) {
    this.start = start;
  }

  public Integer getLimit() {
    return this.limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  public DocumentService getDocumentService() {
    return this.documentService;
  }

  public void setDocumentService(DocumentService documentService) {
    this.documentService = documentService;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getVerifyMsg() {
    return this.verifyMsg;
  }

  public void setVerifyMsg(String verifyMsg) {
    this.verifyMsg = verifyMsg;
  }

  public String getAreano() {
    return this.areano;
  }

  public void setAreano(String areano) {
    this.areano = areano;
  }

  public UserInfoBean getUserInfoBean()
  {
    return this.userInfoBean;
  }

  public void setUserInfoBean(UserInfoBean userInfoBean) {
    this.userInfoBean = userInfoBean;
  }

  public File[] getExcle() {
    return this.excle;
  }

  public void setExcle(File[] excle) {
    this.excle = excle;
  }

  public String[] getExcleFileName() {
    return this.excleFileName;
  }

  public void setExcleFileName(String[] excleFileName) {
    this.excleFileName = excleFileName;
  }

  public List<String> getIds() {
    return this.ids;
  }

  public void setIds(List<String> ids) {
    this.ids = ids;
  }

  public DocPointInfoBean getDpointGrid() {
    return this.dpointGrid;
  }

  public void setDpointGrid(DocPointInfoBean dpointGrid) {
    this.dpointGrid = dpointGrid;
  }

  public DocPointInfoBean getSearchdpoint() {
    return this.searchdpoint;
  }

  public void setSearchdpoint(DocPointInfoBean searchdpoint) {
    this.searchdpoint = searchdpoint;
  }

  public String getModelName() {
    return this.modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }

  public List<DocEqutInfoBean> getDocs() {
    return this.docs;
  }

  public void setDocs(List<DocEqutInfoBean> docs) {
    this.docs = docs;
  }

  public List<DocPointInfoBean> getPointList() {
    return this.pointList;
  }

  public void setPointList(List<DocPointInfoBean> pointList) {
    this.pointList = pointList;
  }

  public List<DocJumpInfoBean> getJumpList() {
    return this.jumpList;
  }

  public void setJumpList(List<DocJumpInfoBean> jumpList) {
    this.jumpList = jumpList;
  }

  public Integer getCol_amount() {
    return this.col_amount;
  }

  public void setCol_amount(Integer col_amount) {
    this.col_amount = col_amount;
  }
}