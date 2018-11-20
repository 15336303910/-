package manage.dataupdate.web;

import base.util.CommonUtil;
import base.util.ErrorMessage;
import base.web.PaginationAction;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import manage.dataupdate.service.DataService;
import manage.domain.pojo.DomainBean;
import manage.pipe.pojo.LedupInfoBean;
import manage.pipe.pojo.PipeInfoBean;
import manage.pipe.pojo.PipeSegmentInfoBean;
import manage.pipe.pojo.TubeInfoBean;
import manage.pipe.pojo.WellInfoBean;
import manage.poleline.pojo.PoleInfoBean;
import manage.poleline.pojo.PolelineInfoBean;
import manage.poleline.pojo.PolelineSegmentInfoBean;
import manage.poleline.pojo.SupportInfoBean;
import manage.poleline.pojo.SuspensionWireInfoBean;
import manage.poleline.pojo.SuspensionWireSegInfoBean;
import manage.user.pojo.UserInfoBean;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts2.ServletActionContext;

public class DataAction extends PaginationAction
{
  private static final Logger log = Logger.getLogger(DataAction.class);
  private String message;
  private ErrorMessage errorMessage;
  private PipeInfoBean pipe;
  private PipeSegmentInfoBean pipeseg;
  private PolelineInfoBean poleline;
  private TubeInfoBean tube;
  private PoleInfoBean pole;
  private WellInfoBean well;
  private LedupInfoBean Ledup;
  private SupportInfoBean support;
  private SuspensionWireInfoBean suspension;
  private PolelineSegmentInfoBean polelinesegment;
  private SuspensionWireSegInfoBean suspensionwireseg;
  private File upFile;
  private UserInfoBean userInfoBean;
  private boolean success = false;
  private DataService dataservice;
  private String filepath;
  private String ids;
  private String type;

  public String insertpipe()
  {
    return "success";
  }

  public String getPipelist()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      String modelPath = "";
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_pipe.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      if (this.ids != null) {
        
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getPipe", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertPoleline()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        for (int i = 3; i <= rows; ++i) {
          PolelineInfoBean p = new PolelineInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);
          HSSFCell cell12 = row.getCell(12);
          HSSFCell cell13 = row.getCell(13);
          HSSFCell cell14 = row.getCell(14);
          HSSFCell cell15 = row.getCell(15);
          HSSFCell cell16 = row.getCell(16);
          HSSFCell cell17 = row.getCell(17);
          HSSFCell cell18 = row.getCell(18);
          HSSFCell cell19 = row.getCell(19);
          HSSFCell cell20 = row.getCell(20);
          HSSFCell cell21 = row.getCell(21);

          if ((cell1 != null) && (cell1.getStringCellValue() != null) && (!(cell1.getStringCellValue().equals("")))) {
            p.setPoleLineName(cell1.getStringCellValue());
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段杆路名称未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell2 != null) && (cell2.getStringCellValue() != null) && (!(cell2.getStringCellValue().equals("")))) {
            DomainBean domain = new DomainBean();
            if (cell2.getCellType() == 0)
              domain.setDomainName(df.format(cell2.getNumericCellValue()));
            else {
              domain.setDomainName(cell2.getStringCellValue());
            }
            domain = this.dataservice.getDomain(domain);
            if (domain != null) {
              p.setMaintenanceAreaId(domain.getDomainId());
            }
            this.message = "对不起，" + (i + 1) + "所属维护区域未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + (i + 1) + "行必填字段所属维护区域未填的，请填好后重新导入！";
          if ((cell3 != null) && (cell3.getStringCellValue() != null) && (!(cell3.getStringCellValue().equals("")))) {
            String level = cell3.getStringCellValue();
            if (level.equals("一干"))
              p.setPoleLineLevel("1");
            else if (level.equals("二干"))
              p.setPoleLineLevel("2");
            else if (level.equals("本地"))
              p.setPoleLineLevel("3");
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段杆路级别未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell4 != null) && (cell4.getStringCellValue() != null) && (!(cell4.getStringCellValue().equals("")))) {
            String maintainType = "";
            if (cell4.getCellType() == 0)
              maintainType = df.format(cell4.getNumericCellValue());
            else {
              maintainType = cell4.getStringCellValue();
            }
            if (maintainType.equals("自维")) {
              p.setMaintenanceModeEnumId(Integer.valueOf(1));
              if ((cell5 != null) && (cell5.getStringCellValue() != null) && (!(cell5.getStringCellValue().equals("")))) {
                p.setMaintenanceOrgId(cell5.getStringCellValue());
              }
              this.message = "对不起，" + (i + 1) + "自维时维护单位为必填字段，请填好后重新导入！";
              return "success";
            }
            if (maintainType.equals("代维")) {
              p.setMaintenanceModeEnumId(Integer.valueOf(2));
              if ((cell6 != null) && (cell6.getStringCellValue() != null) && (!(cell6.getStringCellValue().equals("")))) {
                p.setThirdPartyMaintenanceOrg(cell6.getStringCellValue());
              } else {
                this.message = "对不起，" + (i + 1) + "代维时第三方维护单位为必填字段，请填好后重新导入！";
                return "success";
              }
            }
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段维护方式未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell7 != null) && (cell7.getCellType() == 0))
            label940: p.setPoleLineLength(df.format(cell7.getNumericCellValue()));
          else {
            p.setPoleLineLength(cell7.getStringCellValue());
          }

          if ((cell8 != null) && (cell8.getStringCellValue() != null) && (!(cell8.getStringCellValue().equals("")))) {
            p.setMaintainerId(cell8.getStringCellValue());
          }

          if ((cell9 != null) && (cell9.getStringCellValue() != null) && (!(cell9.getStringCellValue().equals("")))) {
            p.setAlias(cell9.getStringCellValue());
          }

          if ((cell10 != null) && (cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals("")))) {
            p.setRenewalExpirationDate(sdf1.parse(cell10.getStringCellValue()));
          }

          if ((cell11 != null) && (cell11.getStringCellValue() != null) && (!(cell11.getStringCellValue().equals("")))) {
            if (cell11.getStringCellValue().equals("硬件维修"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(1));
            else if (cell11.getStringCellValue().equals("紧急备件支持"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(2));
            else if (cell11.getStringCellValue().equals("软件补丁版本支持"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(3));
            else if (cell11.getStringCellValue().equals("技术支持服务（电话咨询、电话支持、远程支持、现场支持、网络预警、重要通信保障）"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(4));
            else if (cell11.getStringCellValue().equals("无")) {
              p.setMaintenanceTypeEnumId(Integer.valueOf(5));
            }

          }

          if ((cell12 != null) && (cell12.getCellType() == 0))
            p.setPurchasedMaintenanceTime(df.format(cell12.getNumericCellValue()));
          else {
            p.setPurchasedMaintenanceTime(cell12.getStringCellValue());
          }

          if ((cell13 != null) && (cell13.getStringCellValue() != null) && (!(cell13.getStringCellValue().equals("")))) {
            if (cell13.getCellType() == 0)
              p.setProjectCode(df.format(cell13.getNumericCellValue()));
            else {
              p.setProjectCode(cell13.getStringCellValue());
            }

          }

          if ((cell14 != null) && (cell14.getStringCellValue() != null) && (!(cell14.getStringCellValue().equals("")))) {
            p.setProjectName(cell14.getStringCellValue());
          }

          if ((cell15 != null) && (cell15.getStringCellValue() != null) && (!(cell15.getStringCellValue().equals("")))) {
            p.setProjectWarrantyExpireDate(sdf1.parse(cell15.getStringCellValue()));
          }

          if ((cell16 != null) && (cell16.getStringCellValue() != null) && (!(cell16.getStringCellValue().equals("")))) {
            if (cell16.getStringCellValue().equals("设计状态"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(1));
            else if (cell16.getStringCellValue().equals("工程建设期（入网带业务前）"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(2));
            else if (cell16.getStringCellValue().equals("工程可用期（已入网带业务）"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(3));
            else if (cell16.getStringCellValue().equals("工程初验后试运行"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(4));
            else if (cell16.getStringCellValue().equals("工程终验后在网"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(5));
            else if (cell16.getStringCellValue().equals("退网状态")) {
              p.setResourceLifePeriodEnumId(Integer.valueOf(6));
            }

          }

          if ((cell17 != null) && (cell17.getStringCellValue() != null) && (!(cell17.getStringCellValue().equals("")))) {
            p.setResourceLifePeriodEnumDate(sdf1.parse(cell17.getStringCellValue()));
          }

          if ((cell18 != null) && (cell18.getStringCellValue() != null) && (!(cell18.getStringCellValue().equals("")))) {
            if (cell18.getStringCellValue().equals("是"))
              p.setIsAuthorizationManagement(Integer.valueOf(1));
            else if (cell18.getStringCellValue().equals("否")) {
              p.setIsAuthorizationManagement(Integer.valueOf(2));
            }

          }

          if ((cell19 != null) && (cell19.getStringCellValue() != null) && (!(cell19.getStringCellValue().equals("")))) {
            p.setAuthorizationManagementUnit(cell19.getStringCellValue());
          }

          if ((cell20 != null) && (cell20.getStringCellValue() != null) && (!(cell20.getStringCellValue().equals("")))) {
            p.setDesignPurposes(cell20.getStringCellValue());
          }

          if ((cell21 != null) && (cell21.getStringCellValue() != null) && (!(cell21.getStringCellValue().equals("")))) {
            p.setNote(cell21.getStringCellValue());
          }

          list.add(p);
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertPoleline((PolelineInfoBean)list.get(i));
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertpoleline", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getPolelinelist()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();
      String modelPath = "";
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_poleline.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      if (this.ids != null) {
        this.poleline = new PolelineInfoBean();
        this.poleline.setIds(this.ids);
        List lists = this.dataservice.getPolelinelist(this.poleline);
        for (int j = 0; j < lists.size(); ++j) {
          PolelineInfoBean p = (PolelineInfoBean)lists.get(j);
          if (p != null) {
            HSSFRow row = sheet.createRow((short)j + 3);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);
            HSSFCell cell4 = row.createCell(4);
            HSSFCell cell5 = row.createCell(5);
            HSSFCell cell6 = row.createCell(6);
            HSSFCell cell7 = row.createCell(7);
            HSSFCell cell8 = row.createCell(8);
            HSSFCell cell9 = row.createCell(9);
            HSSFCell cell10 = row.createCell(10);
            HSSFCell cell11 = row.createCell(11);
            HSSFCell cell12 = row.createCell(12);
            HSSFCell cell13 = row.createCell(13);
            HSSFCell cell14 = row.createCell(14);
            HSSFCell cell15 = row.createCell(15);
            HSSFCell cell16 = row.createCell(16);
            HSSFCell cell17 = row.createCell(17);
            HSSFCell cell18 = row.createCell(18);
            HSSFCell cell19 = row.createCell(19);
            HSSFCell cell20 = row.createCell(20);
            HSSFCell cell21 = row.createCell(21);

            cell1.setCellType(1);
            cell2.setCellType(1);
            cell3.setCellType(1);
            cell4.setCellType(1);
            cell5.setCellType(1);
            cell6.setCellType(1);
            cell7.setCellType(1);
            cell8.setCellType(1);
            cell9.setCellType(1);
            cell10.setCellType(1);
            cell11.setCellType(1);
            cell12.setCellType(1);
            cell13.setCellType(1);
            cell14.setCellType(1);
            cell15.setCellType(1);
            cell16.setCellType(1);
            cell17.setCellType(1);
            cell18.setCellType(1);
            cell19.setCellType(1);
            cell20.setCellType(1);
            cell21.setCellType(1);

            if (p.getPoleLineName() != null) {
              cell1.setCellValue(p.getPoleLineName());
            }
            if (p.getMaintenanceAreaName() != null) {
              cell2.setCellValue(p.getMaintenanceAreaName());
            }

            if (p.getPoleLineLevel() != null) {
              if (p.getPoleLineLevel().equals("1"))
                cell3.setCellValue("一干");
              else if (p.getPoleLineLevel().equals("2"))
                cell3.setCellValue("二干");
              else if (p.getPoleLineLevel().equals("3")) {
                cell3.setCellValue("本地");
              }
            }

            if (p.getMaintenanceModeEnumId() != null) {
              if (p.getMaintenanceModeEnumId().intValue() == 1) {
                cell4.setCellValue("自维");
                if (p.getMaintenanceOrgId() != null)
                  cell5.setCellValue(p.getMaintenanceOrgId());
              }
              else if (p.getMaintenanceModeEnumId().intValue() == 2) {
                cell4.setCellValue("代维");
                if (p.getThirdPartyMaintenanceOrg() != null) {
                  cell6.setCellValue(p.getThirdPartyMaintenanceOrg());
                }
              }
            }

            if (p.getPoleLineLength() != null) {
              cell7.setCellValue(p.getPoleLineLength());
            }
            if (p.getMaintainerId() != null) {
              cell8.setCellValue(p.getMaintainerId());
            }
            if (p.getAlias() != null) {
              cell9.setCellValue(p.getAlias());
            }

            if (p.getRenewalExpirationDate() != null) {
              cell10.setCellValue(sdf1.format(p.getRenewalExpirationDate()));
            }

            if (p.getMaintenanceTypeEnumId() != null) {
              if (p.getMaintenanceTypeEnumId().intValue() == 1)
                cell11.setCellValue("硬件维修");
              else if (p.getMaintenanceTypeEnumId().intValue() == 2)
                cell11.setCellValue("紧急备件支持");
              else if (p.getMaintenanceTypeEnumId().intValue() == 3)
                cell11.setCellValue("软件补丁版本支持");
              else if (p.getMaintenanceTypeEnumId().intValue() == 4)
                cell11.setCellValue("技术支持服务（电话咨询、电话支持、远程支持、现场支持、网络预警、重要通信保障）");
              else if (p.getMaintenanceTypeEnumId().intValue() == 5) {
                cell11.setCellValue("无");
              }
            }

            if (p.getPurchasedMaintenanceTime() != null) {
              cell12.setCellValue(p.getPurchasedMaintenanceTime());
            }
            if (p.getProjectCode() != null) {
              cell13.setCellValue(p.getProjectCode());
            }
            if (p.getProjectName() != null) {
              cell14.setCellValue(p.getProjectName());
            }
            if (p.getProjectWarrantyExpireDate() != null) {
              cell15.setCellValue(sdf1.format(p.getProjectWarrantyExpireDate()));
            }

            if (p.getResourceLifePeriodEnumId() != null) {
              if (p.getResourceLifePeriodEnumId().intValue() == 1)
                cell16.setCellValue("设计状态");
              else if (p.getResourceLifePeriodEnumId().intValue() == 2)
                cell16.setCellValue("工程建设期（入网带业务前）");
              else if (p.getResourceLifePeriodEnumId().intValue() == 3)
                cell16.setCellValue("工程可用期（已入网带业务）");
              else if (p.getResourceLifePeriodEnumId().intValue() == 4)
                cell16.setCellValue("工程初验后试运行");
              else if (p.getResourceLifePeriodEnumId().intValue() == 5)
                cell16.setCellValue("工程终验后在网");
              else if (p.getResourceLifePeriodEnumId().intValue() == 6) {
                cell16.setCellValue("退网状态");
              }
            }

            if (p.getResourceLifePeriodEnumDate() != null) {
              cell17.setCellValue(sdf1.format(p.getResourceLifePeriodEnumDate()));
            }

            if (p.getIsAuthorizationManagement() != null) {
              if (p.getIsAuthorizationManagement().intValue() == 1)
                cell18.setCellValue("是");
              else if (p.getIsAuthorizationManagement().intValue() == 2) {
                cell18.setCellValue("否");
              }
            }

            if (p.getAuthorizationManagementUnit() != null) {
              cell19.setCellValue(p.getAuthorizationManagementUnit());
            }
            if (p.getDesignPurposes() != null) {
              cell20.setCellValue(p.getDesignPurposes());
            }
            if (p.getNote() != null) {
              cell21.setCellValue(p.getNote());
            }
          }
        }

        String filename = dlTempPath + File.separator + "_" + "杆路信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "杆路信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getPipe", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertPipeseg()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        for (int i = 3; i <= rows; ++i) {
          PipeSegmentInfoBean p = new PipeSegmentInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);
          HSSFCell cell12 = row.getCell(12);
          HSSFCell cell13 = row.getCell(13);
          HSSFCell cell14 = row.getCell(14);
          HSSFCell cell15 = row.getCell(15);
          HSSFCell cell16 = row.getCell(16);
          HSSFCell cell17 = row.getCell(17);
          HSSFCell cell18 = row.getCell(18);
          HSSFCell cell19 = row.getCell(19);
          HSSFCell cell20 = row.getCell(20);
          HSSFCell cell21 = row.getCell(21);
          HSSFCell cell22 = row.getCell(22);
          HSSFCell cell23 = row.getCell(23);
          HSSFCell cell24 = row.getCell(24);
          HSSFCell cell25 = row.getCell(25);
          HSSFCell cell26 = row.getCell(26);
          HSSFCell cell27 = row.getCell(27);
          HSSFCell cell28 = row.getCell(28);
          HSSFCell cell29 = row.getCell(29);
          HSSFCell cell30 = row.getCell(30);
          HSSFCell cell31 = row.getCell(31);
          HSSFCell cell32 = row.getCell(32);
          HSSFCell cell33 = row.getCell(33);
          HSSFCell cell34 = row.getCell(34);
          HSSFCell cell35 = row.getCell(35);
          HSSFCell cell36 = row.getCell(36);
          HSSFCell cell37 = row.getCell(37);

          if ((cell1 != null) && (cell1.getStringCellValue() != null) && (!(cell1.getStringCellValue().equals("")))) {
            p.setPipeSegmentName(cell1.getStringCellValue());
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段管道段名称未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell2 != null) && (cell2.getCellType() == 0)) {
            p.setPipeSegmentCode(df.format(cell2.getNumericCellValue()));
          }
          else if ((cell2.getStringCellValue() != null) && (!(cell2.getStringCellValue().equals("")))) {
            p.setPipeSegmentCode(cell2.getStringCellValue());
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段管道段编号未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell3 != null) && (cell3.getStringCellValue() != null) && (!(cell3.getStringCellValue().equals("")))) {
            DomainBean domain = new DomainBean();
            domain.setDomainName(cell3.getStringCellValue());
            domain = this.dataservice.getDomain(domain);
            if (domain != null) {
              p.setMaintenanceAreaId(domain.getDomainId());
            }
            this.message = "对不起，" + (i + 1) + "所属维护区域未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + (i + 1) + "行必填字段所属维护区域未填的，请填好后重新导入！";
          if ((cell4 != null) && (cell4.getCellType() == 0)) {
            p.setPipeSegmentLength(cell4.getNumericCellValue()+"");
          }
          else if ((cell4.getStringCellValue() != null) && (!(cell4.getStringCellValue().equals("")))) {
            p.setPipeSegmentLength(cell4.getStringCellValue());
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段管道段长度未填的，请填好后重新导入！";
            return "success";
          }
          String type;
          if ((cell5 != null) && (cell5.getStringCellValue() != null) && (!(cell5.getStringCellValue().equals("")))) {
            type = cell5.getStringCellValue();
            if (type.equals("人井管段"))
              p.setPipeSegmentType("1");
            else if (type.equals("引上管段"))
              p.setPipeSegmentType("2");
            else if (type.equals("引入管段"))
              p.setPipeSegmentType("3");
            else if (type.equals("桥梁管段"))
              p.setPipeSegmentType("4");
            else if (type.equals("通道"))
              p.setPipeSegmentType("5");
            else if (type.equals("其他"))
              p.setPipeSegmentType("6");
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段杆路级别未填的，请填好后重新导入！";
            return "success";
          }
          WellInfoBean well;
          PoleInfoBean pole = null;
          if ((cell7 != null) && (cell7.getStringCellValue() != null) && (!(cell7.getStringCellValue().equals("")))) {
            type = cell7.getStringCellValue();
            if (type.equals("人/手井")) {
              p.setStartDeviceType("1");
              if ((cell6 != null) && (cell6.getStringCellValue() != null) && (!(cell6.equals("")))) {
                well = new WellInfoBean();
                well.setWellNameSub(cell6.getStringCellValue());
                well = this.dataservice.getWell(well);
                if (well != null) {
                  p.setStartDeviceId(well.getWellId());
                }
                this.message = "对不起，" + (i + 1) + "行必填字段未查到此起始设施未填的，请核对后重新导入！";
                return "success";
              }

              this.message = "对不起，" + (i + 1) + "行必填字段起始设施名称未填的，请填好后重新导入！";
              return "success";
            }
            if (type.equals("电杆")) {
              p.setStartDeviceType("2");
              if ((cell6 != null) && (cell6.getStringCellValue() != null) && (!(cell6.equals("")))) {
                pole = new PoleInfoBean();
                pole.setPoleNameSub(cell6.getStringCellValue());
                pole = this.dataservice.getPole(pole);
                if (pole != null) {
                  p.setStartDeviceId(pole.getPoleId());
                }
                this.message = "对不起，" + (i + 1) + "行必填字段未查到此起始设施未填的，请核对后重新导入！";
                return "success";
              }

              this.message = "对不起，" + (i + 1) + "行必填字段起始设施名称未填的，请填好后重新导入！";
              return "success";
            }
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段起始设施类型未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell9 != null) && (cell9.getStringCellValue() != null) && (!(cell9.getStringCellValue().equals("")))) {
            type = cell9.getStringCellValue();
            if (type.equals("人/手井")) {
              p.setEndDeviceType("1");
              if ((cell8 != null) && (cell8.getStringCellValue() != null) && (!(cell8.equals("")))) {
            	well = new WellInfoBean();
            	well.setWellNameSub(cell8.getStringCellValue());
            	well = this.dataservice.getWell(well);
                if (pole != null) {
                  p.setEndDeviceId(well.getWellId());
                }
                this.message = "对不起，" + (i + 1) + "行必填字段未查到此终止设施未填的，请核对后重新导入！";
                return "success";
              }

              this.message = "对不起，" + (i + 1) + "行必填字段终止设施名称未填的，请填好后重新导入！";
              return "success";
            }
            if (type.equals("电杆")) {
              p.setEndDeviceType("2");
              if ((cell8 != null) && (cell8.getStringCellValue() != null) && (!(cell8.equals("")))) {
                pole = new PoleInfoBean();
                pole.setPoleNameSub(cell8.getStringCellValue());
                pole = this.dataservice.getPole(pole);
                if (pole != null) {
                  p.setEndDeviceId(pole.getPoleId());
                }
                this.message = "对不起，" + (i + 1) + "行必填字段未查到此终止设施未填的，请核对后重新导入！";
                return "success";
              }

              this.message = "对不起，" + (i + 1) + "行必填字段终止设施名称未填的，请填好后重新导入！";
              return "success";
            }
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段终止设施类型未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell10 != null) && (cell10.getCellType() == 0)) {
            label1806: p.setHoleQuantity(df.format(cell10.getNumericCellValue()));
          }
          else if ((cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals("")))) {
            p.setHoleQuantity(cell10.getStringCellValue());
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段管孔数目未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell11 != null) && (cell11.getStringCellValue() != null) && (!(cell11.getStringCellValue().equals("")))) {
            if (cell11.getStringCellValue().equals("共建")) {
              p.setConstructionSharingEnumId(Integer.valueOf(1));
              if ((cell12 != null) && (cell12.getStringCellValue() != null) && (!(cell12.getStringCellValue().equals("")))) {
                p.setConstructionSharingOrg(cell12.getStringCellValue());
              }
              this.message = "对不起，" + (i + 1) + "行共建/共享时共建/共享方为必填字段，请填好后重新导入！";
              return "success";
            }
            if (cell11.getStringCellValue().equals("共享")) {
              p.setConstructionSharingEnumId(Integer.valueOf(2));
              if ((cell12 != null) && (cell12.getStringCellValue() != null) && (!(cell12.getStringCellValue().equals("")))) {
                p.setConstructionSharingOrg(cell12.getStringCellValue());
              } else {
                this.message = "对不起，" + (i + 1) + "行共建/共享时共建/共享方为必填字段，请填好后重新导入！";
                return "success";
              }
              if ((cell13 != null) && (cell13.getStringCellValue() != null) && (!(cell13.getStringCellValue().equals("")))) {
                if (cell13.getStringCellValue().equals("我方共享他方"))
                  p.setSharingTypeEnumId(Integer.valueOf(1));
                else if (cell13.getStringCellValue().equals("他方共享我方"))
                  p.setSharingTypeEnumId(Integer.valueOf(2));
              }
              else {
                this.message = "对不起，" + (i + 1) + "行共享时共享类型为必填字段，请填好后重新导入！";
                return "success";
              }
            }
            else if (cell11.getStringCellValue().equals("自建自用（不包括自建预留）")) {
              p.setConstructionSharingEnumId(Integer.valueOf(3));
            } else if (cell11.getStringCellValue().equals("自建预留")) {
              p.setConstructionSharingEnumId(Integer.valueOf(4));
            }
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段共建/共享类型未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell14 != null) && (cell14.getStringCellValue() != null) && (!(cell14.getStringCellValue().equals("")))) {
            if (cell14.getStringCellValue().equals("是"))
              label2311: p.setRentFlag(Integer.valueOf(1));
            else if (cell14.getStringCellValue().equals("否"))
              p.setRentFlag(Integer.valueOf(2));
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段是否租用未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell15 != null) && (cell15.getStringCellValue() != null) && (!(cell15.getStringCellValue().equals("")))) {
            p.setAssetsownership(cell15.getStringCellValue());
          }

          if ((cell16 != null) && (cell16.getStringCellValue() != null) && (!(cell16.getStringCellValue().equals("")))) {
            p.setRoadName(cell16.getStringCellValue());
          }

          if ((cell17 != null) && (cell17.getCellType() == 0))
            p.setBuriedDepth(cell17.getNumericCellValue()+"");
          else {
            p.setBuriedDepth(cell17.getStringCellValue());
          }

          if ((cell18 != null) && (cell18.getCellType() == 0))
            p.setOccupyHoleQuantity(cell18.getNumericCellValue()+"");
          else {
            p.setOccupyHoleQuantity(cell18.getStringCellValue());
          }

          if ((cell19 != null) && (cell19.getCellType() == 0))
            p.setReserveHoleQuantity(cell19.getNumericCellValue()+"");
          else {
            p.setReserveHoleQuantity(cell19.getStringCellValue());
          }

          if ((cell20 != null) && (cell20.getStringCellValue() != null) && (!(cell20.getStringCellValue().equals("")))) {
            p.setFixedAssetsCode(cell20.getStringCellValue());
          }

          if ((cell21 != null) && (cell21.getCellType() == 0))
            p.setBuildAndShareNum(cell21.getNumericCellValue()+"");
          else {
            p.setBuildAndShareNum(cell21.getStringCellValue());
          }

          if ((cell22 != null) && (cell22.getCellType() == 0))
            p.setOccupiedSharingHoleQuantity(cell22.getNumericCellValue()+"");
          else {
            p.setOccupiedSharingHoleQuantity(cell22.getStringCellValue());
          }

          if ((cell23 != null) && (cell23.getCellType() == 0))
            p.setEndNestTopElevation(cell23.getNumericCellValue()+"");
          else {
            p.setEndNestTopElevation(cell23.getStringCellValue());
          }

          if ((cell24 != null) && (cell24.getCellType() == 0))
            p.setEndNestBottomElevation(cell24.getNumericCellValue()+"");
          else {
            p.setEndNestBottomElevation(cell24.getStringCellValue());
          }

          if ((cell25 != null) && (cell25.getStringCellValue() != null) && (!(cell25.getStringCellValue().equals("")))) {
            if (cell25.getStringCellValue().equals("一字坡"))
              p.setPipeLineLayingEnumId(Integer.valueOf(1));
            else if (cell25.getStringCellValue().equals("人字坡")) {
              p.setPipeLineLayingEnumId(Integer.valueOf(2));
            }

          }

          if ((cell26 != null) && (cell26.getCellType() == 0))
            p.setStartNestTopElevation(cell26.getNumericCellValue()+"");
          else {
            p.setStartNestTopElevation(cell23.getStringCellValue());
          }

          if ((cell27 != null) && (cell27.getCellType() == 0))
            p.setStartNestBottomElevation(cell27.getNumericCellValue()+"");
          else {
            p.setStartNestBottomElevation(cell27.getStringCellValue());
          }

          if ((cell28 != null) && (cell28.getStringCellValue() != null) && (!(cell28.getStringCellValue().equals("")))) {
            p.setRentStartDate(sdf1.parse(cell28.getCellFormula()));
          }

          if ((cell29 != null) && (cell29.getStringCellValue() != null) && (!(cell29.getStringCellValue().equals("")))) {
            p.setRentEndDate(sdf1.parse(cell29.getCellFormula()));
          }

          if ((cell30 != null) && (cell30.getStringCellValue() != null) && (!(cell30.getStringCellValue().equals("")))) {
            p.setRentOrg(cell30.getStringCellValue());
          }

          if ((cell31 != null) && (cell31.getStringCellValue() != null) && (!(cell31.getStringCellValue().equals("")))) {
            p.setRentChargingCode(cell31.getStringCellValue());
          }

          if ((cell32 != null) && (cell32.getStringCellValue() != null) && (!(cell32.getStringCellValue().equals("")))) {
            if (cell32.getStringCellValue().equals("设计状态"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(1));
            else if (cell32.getStringCellValue().equals("工程建设期（入网带业务前）"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(2));
            else if (cell32.getStringCellValue().equals("工程可用期（已入网带业务）"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(3));
            else if (cell32.getStringCellValue().equals("工程初验后试运行"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(4));
            else if (cell32.getStringCellValue().equals("工程终验后在网"))
              p.setResourceLifePeriodEnumId(Integer.valueOf(5));
            else if (cell32.getStringCellValue().equals("退网状态")) {
              p.setResourceLifePeriodEnumId(Integer.valueOf(6));
            }

          }

          if ((cell33 != null) && (cell33.getStringCellValue() != null) && (!(cell33.getStringCellValue().equals("")))) {
            p.setResourceLifePeriodEnumDate(sdf1.parse(cell33.getStringCellValue()));
          }

          if ((cell34 != null) && (cell34.getStringCellValue() != null) && (!(cell34.getStringCellValue().equals("")))) {
            if (cell34.getStringCellValue().equals("是"))
              p.setIsAuthorizationManagement(Integer.valueOf(1));
            else if (cell34.getStringCellValue().equals("否")) {
              p.setIsAuthorizationManagement(Integer.valueOf(2));
            }

          }

          if ((cell35 != null) && (cell35.getStringCellValue() != null) && (!(cell35.getStringCellValue().equals("")))) {
            p.setAuthorizationManagementUnit(cell35.getStringCellValue());
          }

          if ((cell36 != null) && (cell36.getStringCellValue() != null) && (!(cell36.getStringCellValue().equals("")))) {
            p.setDesignPurposes(cell36.getStringCellValue());
          }

          if ((cell37 != null) && (cell37.getStringCellValue() != null) && (!(cell37.getStringCellValue().equals("")))) {
            p.setNote(cell37.getStringCellValue());
          }

          list.add(p);
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertPipeseg((PipeSegmentInfoBean)list.get(i));
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertpoleline", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getPipeseglist()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();
      String modelPath = "";
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_pipeseg.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      if (this.ids != null) {
        this.pipeseg = new PipeSegmentInfoBean();
        this.pipeseg.setIds(this.ids);
        List lists = this.dataservice.getPipeseglist(this.pipeseg);
        for (int j = 0; j < lists.size(); ++j) {
          PipeSegmentInfoBean p = (PipeSegmentInfoBean)lists.get(j);
          if (p != null) {
            HSSFRow row = sheet.createRow((short)j + 3);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);
            HSSFCell cell4 = row.createCell(4);
            HSSFCell cell5 = row.createCell(5);
            HSSFCell cell6 = row.createCell(6);
            HSSFCell cell7 = row.createCell(7);
            HSSFCell cell8 = row.createCell(8);
            HSSFCell cell9 = row.createCell(9);
            HSSFCell cell10 = row.createCell(10);
            HSSFCell cell11 = row.createCell(11);
            HSSFCell cell12 = row.createCell(12);
            HSSFCell cell13 = row.createCell(13);
            HSSFCell cell14 = row.createCell(14);
            HSSFCell cell15 = row.createCell(15);
            HSSFCell cell16 = row.createCell(16);
            HSSFCell cell17 = row.createCell(17);
            HSSFCell cell18 = row.createCell(18);
            HSSFCell cell19 = row.createCell(19);
            HSSFCell cell20 = row.createCell(20);
            HSSFCell cell21 = row.createCell(21);
            HSSFCell cell22 = row.createCell(22);
            HSSFCell cell23 = row.createCell(23);
            HSSFCell cell24 = row.createCell(24);
            HSSFCell cell25 = row.createCell(25);
            HSSFCell cell26 = row.createCell(26);
            HSSFCell cell27 = row.createCell(27);
            HSSFCell cell28 = row.createCell(28);
            HSSFCell cell29 = row.createCell(29);
            HSSFCell cell30 = row.createCell(30);
            HSSFCell cell31 = row.createCell(31);
            HSSFCell cell32 = row.createCell(32);
            HSSFCell cell33 = row.createCell(33);
            HSSFCell cell34 = row.createCell(34);
            HSSFCell cell35 = row.createCell(35);
            HSSFCell cell36 = row.createCell(36);
            HSSFCell cell37 = row.createCell(37);

            cell1.setCellType(1);
            cell2.setCellType(1);
            cell3.setCellType(1);
            cell4.setCellType(1);
            cell5.setCellType(1);
            cell6.setCellType(1);
            cell7.setCellType(1);
            cell8.setCellType(1);
            cell9.setCellType(1);
            cell10.setCellType(1);
            cell11.setCellType(1);
            cell12.setCellType(1);
            cell13.setCellType(1);
            cell14.setCellType(1);
            cell15.setCellType(1);
            cell16.setCellType(1);
            cell17.setCellType(1);
            cell18.setCellType(1);
            cell19.setCellType(1);
            cell20.setCellType(1);
            cell21.setCellType(1);
            cell22.setCellType(1);
            cell23.setCellType(1);
            cell24.setCellType(1);
            cell25.setCellType(1);
            cell26.setCellType(1);
            cell27.setCellType(1);
            cell28.setCellType(1);
            cell29.setCellType(1);
            cell30.setCellType(1);
            cell31.setCellType(1);
            cell32.setCellType(1);
            cell33.setCellType(1);
            cell34.setCellType(1);
            cell35.setCellType(1);
            cell36.setCellType(1);
            cell37.setCellType(1);

            if (p.getPipeSegmentName() != null) {
              cell1.setCellValue(p.getPipeSegmentName());
            }
            if (p.getPipeSegmentCode() != null) {
              cell2.setCellValue(p.getPipeSegmentCode());
            }
            if (p.getMaintenanceAreaName() != null) {
              cell3.setCellValue(p.getMaintenanceAreaName());
            }
            if (p.getPipeSegmentLength() != null) {
              cell4.setCellValue(p.getPipeSegmentLength());
            }
            if (p.getPipeSegmentType() != null) {
              if (p.getPipeSegmentType().equals("1"))
                cell5.setCellValue("人井管段");
              else if (p.getPipeSegmentType().equals("2"))
                cell5.setCellValue("引上管段");
              else if (p.getPipeSegmentType().equals("3"))
                cell5.setCellValue("引入管段");
              else if (p.getPipeSegmentType().equals("4"))
                cell5.setCellValue("桥梁管段");
              else if (p.getPipeSegmentType().equals("5"))
                cell5.setCellValue("通道");
              else if (p.getPipeSegmentType().equals("6")) {
                cell5.setCellValue("其他");
              }
            }
            if (p.getStartDeviceName() != null) {
              cell6.setCellValue(p.getStartDeviceName());
            }
            if (p.getStartDeviceType() != null) {
              cell7.setCellValue(p.getStartDeviceType());
            }
            if (p.getEndDeviceName() != null) {
              cell8.setCellValue(p.getEndDeviceName());
            }
            if (p.getEndDeviceType() != null) {
              cell9.setCellValue(p.getEndDeviceType());
            }
            if (p.getHoleQuantity() != null) {
              cell10.setCellValue(p.getHoleQuantity());
            }
            if (p.getConstructionSharingEnumId() != null) {
              if (p.getConstructionSharingEnumId().intValue() == 1) {
                cell11.setCellValue("共建");
                if (p.getConstructionSharingOrg() != null)
                  cell12.setCellValue(p.getConstructionSharingOrg());
              }
              else if (p.getConstructionSharingEnumId().intValue() == 2) {
                cell11.setCellValue("共享");
                if (p.getConstructionSharingOrg() != null) {
                  cell12.setCellValue(p.getConstructionSharingOrg());
                }
                if (p.getSharingTypeEnumId() != null) {
                  if (p.getSharingTypeEnumId().intValue() == 1)
                    cell13.setCellValue("我方共享他方");
                  else if (p.getSharingTypeEnumId().intValue() == 2)
                    cell13.setCellValue("他方共享我方");
                }
              }
              else if (p.getConstructionSharingEnumId().intValue() == 3) {
                cell11.setCellValue("自建自用（不包括自建预留）");
              } else if (p.getConstructionSharingEnumId().intValue() == 4) {
                cell11.setCellValue("自建预留");
              }
            }
            if (p.getRentFlag() != null) {
              if (p.getRentFlag().intValue() == 1)
                cell14.setCellValue("是");
              else if (p.getRentFlag().intValue() == 2) {
                cell14.setCellValue("否");
              }
            }
            if (p.getAssetsownership() != null) {
              cell15.setCellValue(p.getAssetsownership());
            }
            if (p.getRoadName() != null) {
              cell16.setCellValue(p.getRoadName());
            }
            if (p.getBuriedDepth() != null) {
              cell17.setCellValue(p.getBuriedDepth());
            }
            if (p.getOccupyHoleQuantity() != null) {
              cell18.setCellValue(p.getOccupyHoleQuantity());
            }
            if (p.getReserveHoleQuantity() != null) {
              cell19.setCellValue(p.getReserveHoleQuantity());
            }
            if (p.getFixedAssetsCode() != null) {
              cell20.setCellValue(p.getFixedAssetsCode());
            }
            if (p.getBuildAndShareNum() != null) {
              cell21.setCellValue(p.getBuildAndShareNum());
            }
            if (p.getOccupiedSharingHoleQuantity() != null) {
              cell22.setCellValue(p.getOccupiedSharingHoleQuantity());
            }
            if (p.getEndNestTopElevation() != null) {
              cell23.setCellValue(p.getEndNestTopElevation());
            }
            if (p.getEndNestBottomElevation() != null) {
              cell24.setCellValue(p.getEndNestBottomElevation());
            }
            if (p.getPipeLineLayingEnumId() != null) {
              if (p.getPipeLineLayingEnumId().intValue() == 1)
                cell25.setCellValue("一字坡");
              else if (p.getPipeLineLayingEnumId().intValue() == 2) {
                cell25.setCellValue("人字坡");
              }
            }

            if (p.getStartNestTopElevation() != null) {
              cell26.setCellValue(p.getStartNestTopElevation());
            }
            if (p.getStartNestBottomElevation() != null) {
              cell27.setCellValue(p.getStartNestBottomElevation());
            }
            if (p.getRentStartDate() != null) {
              cell28.setCellValue(sdf1.format(p.getRentStartDate()));
            }
            if (p.getRentEndDate() != null) {
              cell29.setCellValue(sdf1.format(p.getRentEndDate()));
            }
            if (p.getRentOrg() != null) {
              cell30.setCellValue(p.getRentOrg());
            }
            if (p.getRentChargingCode() != null) {
              cell31.setCellValue(p.getRentChargingCode());
            }
            if (p.getResourceLifePeriodEnumId() != null) {
              if (p.getResourceLifePeriodEnumId().intValue() == 1)
                cell32.setCellValue("设计状态");
              else if (p.getResourceLifePeriodEnumId().intValue() == 2)
                cell32.setCellValue("工程建设期（入网带业务前）");
              else if (p.getResourceLifePeriodEnumId().intValue() == 3)
                cell32.setCellValue("工程可用期（已入网带业务）");
              else if (p.getResourceLifePeriodEnumId().intValue() == 4)
                cell32.setCellValue("工程初验后试运行");
              else if (p.getResourceLifePeriodEnumId().intValue() == 5)
                cell32.setCellValue("工程终验后在网");
              else if (p.getResourceLifePeriodEnumId().intValue() == 6) {
                cell32.setCellValue("退网状态");
              }
            }

            if (p.getResourceLifePeriodEnumDate() != null) {
              cell33.setCellValue(sdf1.format(p.getResourceLifePeriodEnumDate()));
            }

            if (p.getIsAuthorizationManagement() != null) {
              if (p.getIsAuthorizationManagement().intValue() == 1)
                cell34.setCellValue("是");
              else if (p.getIsAuthorizationManagement().intValue() == 2) {
                cell34.setCellValue("否");
              }
            }

            if (p.getAuthorizationManagementUnit() != null) {
              cell35.setCellValue(p.getAuthorizationManagementUnit());
            }
            if (p.getDesignPurposes() != null) {
              cell36.setCellValue(p.getDesignPurposes());
            }
            if (p.getNote() != null) {
              cell37.setCellValue(p.getNote());
            }
          }
        }

        String filename = dlTempPath + File.separator + "_" + "管道段信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "管道段信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getPipe", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertTube()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        List<TubeInfoBean> listson = new ArrayList();
        TubeInfoBean t = new TubeInfoBean();
        for (int i = 3; i <= rows; ++i) {
          t = new TubeInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);
          HSSFCell cell12 = row.getCell(12);
          HSSFCell cell13 = row.getCell(13);
          HSSFCell cell14 = row.getCell(14);
          HSSFCell cell15 = row.getCell(15);
          HSSFCell cell16 = row.getCell(16);
          HSSFCell cell17 = row.getCell(17);
          HSSFCell cell18 = row.getCell(18);
          HSSFCell cell19 = row.getCell(19);
          HSSFCell cell20 = row.getCell(20);
          HSSFCell cell21 = row.getCell(21);
          HSSFCell cell22 = row.getCell(22);
          HSSFCell cell23 = row.getCell(23);
          HSSFCell cell24 = row.getCell(24);
          HSSFCell cell25 = row.getCell(25);

          if ((cell1 == null) || (cell1.getStringCellValue() == null) || (cell1.getStringCellValue().equals(""))) {
            this.message = "对不起，" + (i + 1) + "行必填字段管孔、子孔名称未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell2 != null) && (cell2.getCellType() == 0)) {
            t.setInPipesegCode(df.format(cell2.getNumericCellValue()));
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段管孔、子孔编号未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell3 != null) && (cell3.getStringCellValue() != null) && (!(cell3.getStringCellValue().equals("")))) {
            PipeSegmentInfoBean p = new PipeSegmentInfoBean();
            p.setPipeSegmentName(cell3.getStringCellValue());
            p = this.dataservice.getPipeseg(p);
            if (p != null) {
              t.setPipeSegmentId(p.getPipeSegmentId()+"");
            }
            this.message = "对不起，" + (i + 1) + "行未查到此管道段，请先导入管道段！";
            return "success";
          }

          this.message = "对不起，" + (i + 1) + "行必填字段所属管道段未填的，请填好后重新导入！";
          if ((cell7 != null) && (cell7.getStringCellValue() != null) && (!(cell7.getStringCellValue().equals("")))) {
            String type = cell7.getStringCellValue();
            if (type.equals("占用")) {
              t.setTubeStatusEnumId(Integer.valueOf(1));
            } else if (type.equals("空闲")) {
              t.setTubeStatusEnumId(Integer.valueOf(2));
            } else if (type.equals("预占")) {
              t.setTubeStatusEnumId(Integer.valueOf(3));
            } else if (type.equals("暂拆")) {
              t.setTubeStatusEnumId(Integer.valueOf(4));
            } else if (type.equals("损坏")) {
              t.setTubeStatusEnumId(Integer.valueOf(5));
            } else if (type.equals("废弃")) {
              t.setTubeStatusEnumId(Integer.valueOf(6));
            } else if (type.equals("外租")) {
              t.setTubeStatusEnumId(Integer.valueOf(7));
            } else if (type.equals("出租")) {
              t.setTubeStatusEnumId(Integer.valueOf(8));
              if ((cell8 != null) && (cell8.getStringCellValue() != null) && (!(cell8.getStringCellValue().equals("")))) {
                t.setRentOrg(cell8.getStringCellValue());
              } else {
                this.message = "对不起，" + (i + 1) + "行在管孔状态为出租时，承租方为必填字段，请填好后重新导入！";
                return "success";
              }
            }
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段管孔状态未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell9 != null) && (cell9.getStringCellValue() != null) && (!(cell9.getStringCellValue().equals("")))) {
            if (cell9.getStringCellValue().equals("共建")) {
              t.setConstructionSharingEnumId(Integer.valueOf(1));
              if ((cell10 != null) && (cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals("")))) {
                t.setConstructionSharingOrg(cell10.getStringCellValue());
              }
              this.message = "对不起，" + (i + 1) + "行共建/共享时共建/共享方为必填字段，请填好后重新导入！";
              return "success";
            }
            if (cell9.getStringCellValue().equals("共享")) {
              t.setConstructionSharingEnumId(Integer.valueOf(2));
              if ((cell10 != null) && (cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals("")))) {
                t.setConstructionSharingOrg(cell10.getStringCellValue());
              } else {
                this.message = "对不起，" + (i + 1) + "行共建/共享时共建/共享方为必填字段，请填好后重新导入！";
                return "success";
              }
              if ((cell11 != null) && (cell11.getStringCellValue() != null) && (!(cell11.getStringCellValue().equals("")))) {
                if (cell11.getStringCellValue().equals("我方共享他方"))
                  t.setSharingTypeEnumId(Integer.valueOf(1));
                else if (cell11.getStringCellValue().equals("他方共享我方"))
                  t.setSharingTypeEnumId(Integer.valueOf(2));
              }
              else {
                this.message = "对不起，" + (i + 1) + "行共享时共享类型为必填字段，请填好后重新导入！";
                return "success";
              }
            }
            else if (cell9.getStringCellValue().equals("自建自用")) {
              t.setConstructionSharingEnumId(Integer.valueOf(3));
            } else if (cell9.getStringCellValue().equals("自建预留")) {
              t.setConstructionSharingEnumId(Integer.valueOf(4));
            }
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段共建/共享类型未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell12 != null) && (cell12.getStringCellValue() != null) && (!(cell12.getStringCellValue().equals("")))) {
            if (cell12.getStringCellValue().equals("是"))
              label1354: t.setRentFlag("1");
            else if (cell12.getStringCellValue().equals("否"))
              t.setRentFlag("2");
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段是否租用未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell13 != null) && (cell13.getStringCellValue() != null) && (!(cell13.getStringCellValue().equals("")))) {
            t.setAssetsownership(cell13.getStringCellValue());
          }

          if ((cell15 != null) && (cell15.getCellType() == 0))
            t.setTubeDiameter(cell15.getNumericCellValue()+"");
          else {
            t.setTubeDiameter(cell15.getStringCellValue());
          }

          if ((cell16 != null) && (cell16.getStringCellValue() != null) && (!(cell16.getStringCellValue().equals("")))) {
            String color = cell16.getStringCellValue();
            if (color.equals("红"))
              t.setTubeColor("1");
            else if (color.equals("橙"))
              t.setTubeColor("2");
            else if (color.equals("黄"))
              t.setTubeColor("3");
            else if (color.equals("绿"))
              t.setTubeColor("4");
            else if (color.equals("青"))
              t.setTubeColor("5");
            else if (color.equals("蓝"))
              t.setTubeColor("6");
            else if (color.equals("紫"))
              t.setTubeColor("7");
            else if (color.equals("白"))
              t.setTubeColor("8");
            else if (color.equals("黑"))
              t.setTubeColor("9");
          }
          String s;
          if ((cell17 != null) && (cell17.getStringCellValue() != null) && (cell17.getStringCellValue().equals(""))) {
            s = cell17.getStringCellValue();
            if (s.equals("塑料管"))
              t.setTubeMaterial("1");
            else if (s.equals("陶瓷管"))
              t.setTubeMaterial("2");
            else if (s.equals("水泥管"))
              t.setTubeMaterial("3");
            else if (s.equals("钢管")) {
              t.setTubeMaterial("4");
            }

          }

          if ((cell18 != null) && (cell18.getStringCellValue() != null) && (!(cell18.getStringCellValue().equals("")))) {
            s = cell18.getStringCellValue();
            if (s.equals("圆形"))
              t.setTubeShape("1");
            else if (s.equals("方形"))
              t.setTubeShape("2");
            else if (s.equals("梅花孔形")) {
              t.setTubeShape("3");
            }

          }

          if ((cell19 != null) && (cell19.getStringCellValue() != null) && (!(cell19.getStringCellValue().equals("")))) {
            if (cell19.getStringCellValue().equals("设计状态"))
              t.setResourceLifePeriodEnumId(Integer.valueOf(1));
            else if (cell19.getStringCellValue().equals("工程建设期（入网带业务前）"))
              t.setResourceLifePeriodEnumId(Integer.valueOf(2));
            else if (cell19.getStringCellValue().equals("工程可用期（已入网带业务）"))
              t.setResourceLifePeriodEnumId(Integer.valueOf(3));
            else if (cell19.getStringCellValue().equals("工程初验后试运行"))
              t.setResourceLifePeriodEnumId(Integer.valueOf(4));
            else if (cell19.getStringCellValue().equals("工程终验后在网"))
              t.setResourceLifePeriodEnumId(Integer.valueOf(5));
            else if (cell19.getStringCellValue().equals("退网状态")) {
              t.setResourceLifePeriodEnumId(Integer.valueOf(6));
            }

          }

          if ((cell20 != null) && (cell20.getStringCellValue() != null) && (!(cell20.getStringCellValue().equals("")))) {
            t.setResourceLifePeriodEnumDate(sdf1.parse(cell20.getStringCellValue()));
          }

          if ((cell21 != null) && (cell21.getStringCellValue() != null) && (!(cell21.getStringCellValue().equals("")))) {
            t.setBearCableSegment(cell21.getStringCellValue());
          }

          if ((cell22 != null) && (cell22.getStringCellValue() != null) && (!(cell22.getStringCellValue().equals("")))) {
            if (cell22.getStringCellValue().equals("是"))
              t.setIsAuthorizationManagement(Integer.valueOf(1));
            else if (cell22.getStringCellValue().equals("否")) {
              t.setIsAuthorizationManagement(Integer.valueOf(2));
            }

          }

          if ((cell23 != null) && (cell23.getStringCellValue() != null) && (!(cell23.getStringCellValue().equals("")))) {
            t.setAuthorizationManagementUnit(cell23.getStringCellValue());
          }

          if ((cell24 != null) && (cell24.getStringCellValue() != null) && (!(cell24.getStringCellValue().equals("")))) {
            t.setDesignPurposes(cell24.getStringCellValue());
          }

          if ((cell25 != null) && (cell25.getStringCellValue() != null) && (!(cell25.getStringCellValue().equals("")))) {
            t.setNote(cell25.getStringCellValue());
          }

          if ((cell4 != null) && (cell4.getStringCellValue() != null) && (!(cell4.getStringCellValue().equals("")))) {
            t.setFatherPoreName(cell4.getStringCellValue());
            listson.add(t);
          } else {
            if ((cell5 != null) && (cell5.getStringCellValue() != null) && (!(cell5.getStringCellValue().equals("")))) {
              t.setSubTubeArrangeMode(cell5.getStringCellValue());
            }
            if (cell6.getCellType() == 0) {
              t.setSubTubeAmount(Integer.valueOf(Integer.parseInt(df.format(cell6.getNumericCellValue()))));
            }
            if (cell14.getCellType() == 0) {
              t.setOccupysubTube(df.format(cell14.getNumericCellValue()));
            }
            list.add(t);
          }
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertTube((TubeInfoBean)list.get(i));
        }
        for (TubeInfoBean t1 : listson) {
          TubeInfoBean ts = new TubeInfoBean();
          PipeSegmentInfoBean pipeseg = new PipeSegmentInfoBean();
          int num = t1.getFatherPoreName().indexOf("/");
          String pipesegname = t1.getFatherPoreName().substring(0, num);
          String code = t1.getFatherPoreName().substring(num);
          String fathercode = code.substring(0, code.indexOf("."));
          t1.setInPipesegCode(code);
          pipeseg.setPipeSegmentName(pipesegname);
          pipeseg = this.dataservice.getPipeseg(pipeseg);
          if (pipeseg != null) {
            ts.setInPipesegCode(fathercode);
            ts.setPipeSegmentId(pipeseg.getPipeSegmentId()+"");
            ts = this.dataservice.getTube(ts);
            if (ts != null) {
              t1.setFatherPoreId(ts.getTubeId());
            }
            this.dataservice.insertTube(t1);
          } else {
            this.dataservice.insertTube(t1);
          }
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertpoleline", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getTubelist()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();
      String modelPath = "";
      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_tube.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);
      if (this.ids != null) {
        this.tube = new TubeInfoBean();
        this.tube.setIds(this.ids);
        List lists = this.dataservice.getTubelist(this.tube);
        for (int j = 0; j < lists.size(); ++j) {
          TubeInfoBean t = (TubeInfoBean)lists.get(j);
          if (t != null) {
            HSSFRow row = sheet.createRow((short)j + 3);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);
            HSSFCell cell4 = row.createCell(4);
            HSSFCell cell5 = row.createCell(5);
            HSSFCell cell6 = row.createCell(6);
            HSSFCell cell7 = row.createCell(7);
            HSSFCell cell8 = row.createCell(8);
            HSSFCell cell9 = row.createCell(9);
            HSSFCell cell10 = row.createCell(10);
            HSSFCell cell11 = row.createCell(11);
            HSSFCell cell12 = row.createCell(12);
            HSSFCell cell13 = row.createCell(13);
            HSSFCell cell14 = row.createCell(14);
            HSSFCell cell15 = row.createCell(15);
            HSSFCell cell16 = row.createCell(16);
            HSSFCell cell17 = row.createCell(17);
            HSSFCell cell18 = row.createCell(18);
            HSSFCell cell19 = row.createCell(19);
            HSSFCell cell20 = row.createCell(20);
            HSSFCell cell21 = row.createCell(21);
            HSSFCell cell22 = row.createCell(22);
            HSSFCell cell23 = row.createCell(23);
            HSSFCell cell24 = row.createCell(24);
            HSSFCell cell25 = row.createCell(25);

            cell1.setCellType(1);
            cell2.setCellType(1);
            cell3.setCellType(1);
            cell4.setCellType(1);
            cell5.setCellType(1);
            cell6.setCellType(1);
            cell7.setCellType(1);
            cell8.setCellType(1);
            cell9.setCellType(1);
            cell10.setCellType(1);
            cell11.setCellType(1);
            cell12.setCellType(1);
            cell13.setCellType(1);
            cell14.setCellType(1);
            cell15.setCellType(1);
            cell16.setCellType(1);
            cell17.setCellType(1);
            cell18.setCellType(1);
            cell19.setCellType(1);
            cell20.setCellType(1);
            cell21.setCellType(1);
            cell22.setCellType(1);
            cell23.setCellType(1);
            cell24.setCellType(1);
            cell25.setCellType(1);

            if ((t.getPipeSegmentName() != null) && (t.getInPipesegCode() != null)) {
              cell1.setCellValue(t.getPipeSegmentName() + "/" + t.getInPipesegCode());
              if (t.getInPipesegCode().indexOf(".") == -1) {
                cell2.setCellValue(t.getInPipesegCode());
              }
              cell3.setCellValue(t.getPipeSegmentName());
            }
            if ((t.getFatherPoreName() != null) && (t.getFatherPoreName().equals(""))) {
              cell4.setCellValue(t.getFatherPoreName());
            } else {
              if ((t.getSubTubeArrangeMode() != null) && (t.getSubTubeArrangeMode().equals(""))) {
                cell5.setCellValue(t.getSubTubeArrangeMode());
              }
              if ((t.getSubTubeAmount() != null) && (t.getSubTubeAmount().equals(""))) {
                cell6.setCellValue(t.getSubTubeAmount().intValue());
              }
              if ((t.getOccupysubTube() != null) && (t.getOccupysubTube().equals(""))) {
                cell14.setCellValue(t.getOccupysubTube());
              }
            }
            if (t.getTubeStatusEnumId() != null) {
              if (t.getTubeStatusEnumId().intValue() == 1) {
                cell7.setCellValue("占用");
              } else if (t.getTubeStatusEnumId().intValue() == 2) {
                cell7.setCellValue("空闲");
              } else if (t.getTubeStatusEnumId().intValue() == 3) {
                cell7.setCellValue("预占");
              } else if (t.getTubeStatusEnumId().intValue() == 4) {
                cell7.setCellValue("暂拆");
              } else if (t.getTubeStatusEnumId().intValue() == 5) {
                cell7.setCellValue("损坏");
              } else if (t.getTubeStatusEnumId().intValue() == 6) {
                cell7.setCellValue("废弃");
              } else if (t.getTubeStatusEnumId().intValue() == 7) {
                cell7.setCellValue("外租");
              } else if (t.getTubeStatusEnumId().intValue() == 8) {
                cell7.setCellValue("出租");
                if ((t.getRentOrg() != null) && (t.getRentOrg().equals(""))) {
                  cell8.setCellValue(t.getRentOrg());
                }
              }
            }

            if (t.getConstructionSharingEnumId() != null) {
              if (t.getConstructionSharingEnumId().intValue() == 1) {
                cell9.setCellValue("共建");
                if (t.getConstructionSharingOrg() != null)
                  cell10.setCellValue(t.getConstructionSharingOrg());
              }
              else if (t.getConstructionSharingEnumId().intValue() == 2) {
                cell9.setCellValue("共享");
                if (t.getConstructionSharingOrg() != null) {
                  cell10.setCellValue(t.getConstructionSharingOrg());
                }
                if (t.getSharingTypeEnumId() != null) {
                  if (t.getSharingTypeEnumId().intValue() == 1)
                    cell11.setCellValue("我方共享他方");
                  else if (t.getSharingTypeEnumId().intValue() == 2)
                    cell11.setCellValue("他方共享我方");
                }
              }
              else if (t.getConstructionSharingEnumId().intValue() == 3) {
                cell9.setCellValue("自建自用（不包括自建预留）");
              } else if (t.getConstructionSharingEnumId().intValue() == 4) {
                cell9.setCellValue("自建预留");
              }
            }
            if (t.getRentFlag() != null) {
              if (t.getRentFlag().equals("1"))
                cell12.setCellValue("是");
              else if (t.getRentFlag().equals("2")) {
                cell12.setCellValue("否");
              }
            }

            if (t.getAssetsownership() != null) {
              cell13.setCellValue(t.getAssetsownership());
            }
            if ((t.getTubeDiameter() != null) && (t.getTubeDiameter().equals(""))) {
              cell15.setCellValue(t.getTubeDiameter());
            }
            if (t.getTubeColor() != null) {
              if (t.getTubeColor().equals("1"))
                cell16.setCellValue("红");
              else if (t.getTubeColor().equals("2"))
                cell16.setCellValue("橙");
              else if (t.getTubeColor().equals("3"))
                cell16.setCellValue("黄");
              else if (t.getTubeColor().equals("4"))
                cell16.setCellValue("绿");
              else if (t.getTubeColor().equals("5"))
                cell16.setCellValue("青");
              else if (t.getTubeColor().equals("6"))
                cell16.setCellValue("蓝");
              else if (t.getTubeColor().equals("7"))
                cell16.setCellValue("紫");
              else if (t.getTubeColor().equals("8"))
                cell16.setCellValue("白");
              else if (t.getTubeColor().equals("9")) {
                cell16.setCellValue("黑");
              }
            }
            if (t.getTubeMaterial() != null) {
              if (t.getTubeMaterial().equals("1"))
                cell17.setCellValue("塑料管");
              else if (t.getTubeMaterial().equals("2"))
                cell17.setCellValue("陶瓷管");
              else if (t.getTubeMaterial().equals("3"))
                cell17.setCellValue("水泥管");
              else if (t.getTubeMaterial().equals("4")) {
                cell17.setCellValue("钢管");
              }
            }
            if (t.getTubeShape() != null) {
              if (t.getTubeShape().equals("1"))
                cell18.setCellValue("圆形");
              else if (t.getTubeShape().equals("2"))
                cell18.setCellValue("方形");
              else if (t.getTubeShape().equals("3")) {
                cell18.setCellValue("梅花孔形");
              }
            }
            if (t.getResourceLifePeriodEnumId() != null) {
              if (t.getResourceLifePeriodEnumId().intValue() == 1)
                cell19.setCellValue("设计状态");
              else if (t.getResourceLifePeriodEnumId().intValue() == 2)
                cell19.setCellValue("工程建设期（入网带业务前）");
              else if (t.getResourceLifePeriodEnumId().intValue() == 3)
                cell19.setCellValue("工程可用期（已入网带业务）");
              else if (t.getResourceLifePeriodEnumId().intValue() == 4)
                cell19.setCellValue("工程初验后试运行");
              else if (t.getResourceLifePeriodEnumId().intValue() == 5)
                cell19.setCellValue("工程终验后在网");
              else if (t.getResourceLifePeriodEnumId().intValue() == 6) {
                cell19.setCellValue("退网状态");
              }
            }

            if (t.getResourceLifePeriodEnumDate() != null) {
              cell20.setCellValue(sdf1.format(t.getResourceLifePeriodEnumDate()));
            }
            if (t.getBearCableSegment() != null) {
              cell21.setCellValue(t.getBearCableSegment());
            }
            if (t.getIsAuthorizationManagement() != null) {
              if (t.getIsAuthorizationManagement().intValue() == 1)
                cell22.setCellValue("是");
              else if (t.getIsAuthorizationManagement().intValue() == 2) {
                cell22.setCellValue("否");
              }
            }

            if (t.getAuthorizationManagementUnit() != null) {
              cell23.setCellValue(t.getAuthorizationManagementUnit());
            }
            if (t.getDesignPurposes() != null) {
              cell24.setCellValue(t.getDesignPurposes());
            }
            if (t.getNote() != null) {
              cell25.setCellValue(t.getNote());
            }
          }
        }

        String filename = dlTempPath + File.separator + "_" + "管孔信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "管孔信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getPipe", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertpole()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        for (int i = 3; i <= rows; ++i) {
          PoleInfoBean p = new PoleInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);
          HSSFCell cell12 = row.getCell(12);
          HSSFCell cell13 = row.getCell(13);
          HSSFCell cell14 = row.getCell(14);
          HSSFCell cell15 = row.getCell(15);
          HSSFCell cell16 = row.getCell(16);
          HSSFCell cell17 = row.getCell(17);
          HSSFCell cell18 = row.getCell(18);
          HSSFCell cell19 = row.getCell(19);
          HSSFCell cell20 = row.getCell(20);
          HSSFCell cell21 = row.getCell(21);
          HSSFCell cell22 = row.getCell(22);
          HSSFCell cell23 = row.getCell(23);
          HSSFCell cell24 = row.getCell(24);
          HSSFCell cell25 = row.getCell(25);

          if ((cell1 != null) && (cell1.getStringCellValue() != null) && (!(cell1.getStringCellValue().equals("")))) {
            String temp = "";
            if (cell1.getCellType() == 0)
              temp = df.format(cell1.getNumericCellValue());
            else {
              temp = cell1.getStringCellValue();
            }
            if (temp.indexOf("P") != -1)
              if (temp.indexOf("_") != -1) {
                p.setPoleNameSub(temp);
                p.setPoleName(temp.substring(0, temp.indexOf("P")));
                p.setPoleNo(temp.substring(temp.indexOf("P") + 1, temp.indexOf("_")));
                p.setPoleSubNo(temp.substring(temp.indexOf("_") + 1, temp.length()));
              } else {
                p.setPoleNameSub(temp);
                p.setPoleName(temp.substring(0, temp.indexOf("P")));
                p.setPoleNo(temp.substring(temp.indexOf("P") + 1, temp.length()));
              }
            else
              p.setPoleName(temp);
          }
          else
          {
            this.message = "对不起，" + (i + 1) + "行必填字段电杆名称未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell2 != null) && (cell2.getStringCellValue() != null) && (!(cell2.getStringCellValue().equals("")))) {
            DomainBean domain = new DomainBean();
            if (cell2.getCellType() == 0)
              domain.setDomainName(df.format(cell2.getNumericCellValue()));
            else {
              domain.setDomainName(cell2.getStringCellValue());
            }
            domain = this.dataservice.getDomain(domain);
            if (domain != null) {
              p.setMaintenanceAreaId(domain.getDomainId());
            }
            this.message = "对不起，" + (i + 1) + "所属维护区域未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + (i + 1) + "行必填字段所属维护区域未填的，请填好后重新导入！";
          if ((cell3 != null) && (cell3.getStringCellValue() != null) && (!(cell3.getStringCellValue().equals("")))) {
            String level = "";
            if (cell3.getCellType() == 0)
              level = df.format(cell3.getNumericCellValue());
            else {
              level = cell3.getStringCellValue();
            }
            if (level.equals("普通杆"))
              p.setPoleTypeEnumId(Integer.valueOf(1));
            else if (level.equals("单接杆"))
              p.setPoleTypeEnumId(Integer.valueOf(2));
            else if (level.equals("双接杆"))
              p.setPoleTypeEnumId(Integer.valueOf(3));
            else if (level.equals("H型杆"))
              p.setPoleTypeEnumId(Integer.valueOf(4));
            else if (level.equals("A型杆"))
              p.setPoleTypeEnumId(Integer.valueOf(5));
            else if (level.equals("L型杆"))
              p.setPoleTypeEnumId(Integer.valueOf(6));
            else if (level.equals("三角杆"))
              p.setPoleTypeEnumId(Integer.valueOf(7));
            else if (level.equals("井型杆"))
              p.setPoleTypeEnumId(Integer.valueOf(8));
            else if (level.equals("引上杆"))
              p.setPoleTypeEnumId(Integer.valueOf(9));
            else if (level.equals("终端杆"))
              p.setPoleTypeEnumId(Integer.valueOf(10));
            else if (level.equals("角杆杆"))
              p.setPoleTypeEnumId(Integer.valueOf(11));
            else if (level.equals("分歧杆"))
              p.setPoleTypeEnumId(Integer.valueOf(12));
            else if (level.equals("T型杆"))
              p.setPoleTypeEnumId(Integer.valueOf(13));
            else if (level.equals("跨路杆"))
              p.setPoleTypeEnumId(Integer.valueOf(14));
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段电杆类型未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell4 != null) && (cell4.getStringCellValue() != null) && (!(cell4.getStringCellValue().equals("")))) {
            String maintainType = "";
            if (cell4.getCellType() == 0)
              maintainType = df.format(cell4.getNumericCellValue());
            else {
              maintainType = cell4.getStringCellValue();
            }
            if (maintainType.equals("钢筋混凝土电杆"))
              p.setPoleMaterial("1");
            else if (maintainType.equals("木质电杆"))
              p.setPoleMaterial("2");
            else if (maintainType.equals("铁质电杆"))
              p.setPoleMaterial("3");
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段电杆材质未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell5 != null) && (cell5.getStringCellValue() != null) && (!(cell5.getStringCellValue().equals("")))) {
            if (cell5.getCellType() == 0)
              p.setPoleLength(df.format(cell5.getNumericCellValue()));
            else
              p.setPoleLength(cell5.getStringCellValue());
          }
          else
          {
            this.message = "对不起，" + (i + 1) + "行必填字段电杆程式未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell6 != null) && (cell6.getCellType() == 0)) {
            p.setPoleLongitude(cell6.getNumericCellValue()+"");
          }
          else if ((cell6 != null) && (cell6.getStringCellValue() != null) && (!(cell6.getStringCellValue().equals("")))) {
            p.setPoleLongitude(cell6.getStringCellValue());
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段电杆经度未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell7 != null) && (cell7.getCellType() == 0)) {
            p.setPoleLatitude(cell7.getNumericCellValue()+"");
          }
          else if ((cell7 != null) && (cell7.getStringCellValue() != null) && (!(cell7.getStringCellValue().equals("")))) {
            p.setPoleLatitude(cell7.getStringCellValue());
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段电杆纬度未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell8 != null) && (cell8.getStringCellValue() != null) && (!(cell8.getStringCellValue().equals("")))) {
            String demp = "";
            if (cell8.getCellType() == 0)
              demp = df.format(cell8.getNumericCellValue());
            else {
              demp = cell8.getStringCellValue();
            }
            if (demp.equals("自维")) {
              p.setMaintenanceModeEnumId(Integer.valueOf(1));
              if ((cell9 != null) && (cell9.getStringCellValue() != null) && (!(cell9.getStringCellValue().equals(""))) && (cell18 != null) && (cell18.getStringCellValue() != null) && (!(cell18.getStringCellValue().equals("")))) {
                p.setMaintenanceOrgId(cell9.getStringCellValue());
                p.setMaintainerId(cell18.getStringCellValue());
              }
              this.message = "对不起，" + (i + 1) + "自维时维护单位和包机人为必填字段，请填好后重新导入！";
              return "success";
            }
            if (demp.equals("代维")) {
              p.setMaintenanceModeEnumId(Integer.valueOf(2));
              if ((cell10 != null) && (cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals("")))) {
                p.setThirdPartyMaintenanceOrg(cell10.getStringCellValue());
              } else {
                this.message = "对不起，" + (i + 1) + "代维时第三方维护单位为必填字段，请填好后重新导入！";
                return "success";
              }
            }
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段维护方式未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell11 != null) && (cell11.getStringCellValue() != null) && (!(cell11.getStringCellValue().equals("")))) {
            if (cell11.getCellType() == 0)
              label1973: p.setPoleAlias(df.format(cell11.getNumericCellValue()));
            else {
              p.setPoleAlias(cell11.getStringCellValue());
            }

          }

          if ((cell12 != null) && (cell12.getCellType() == 0)) {
            p.setAssetTagNumber(cell12.getNumericCellValue()+"");
          }
          else if ((cell12 != null) && (cell12.getStringCellValue() != null) && (!(cell12.getStringCellValue().equals("")))) {
            p.setAssetTagNumber(cell12.getStringCellValue());
          }

          if ((cell13 != null) && (cell13.getCellType() == 0)) {
            p.setBuriedDepth(cell13.getNumericCellValue()+"");
          }
          else if ((cell13 != null) && (cell13.getStringCellValue() != null) && (!(cell13.getStringCellValue().equals("")))) {
            p.setBuriedDepth(cell13.getStringCellValue());
          }

          if ((cell14 != null) && (cell14.getCellType() == 0)) {
            p.setPoleRadius(cell14.getNumericCellValue()+"");
          }
          else if ((cell14 != null) && (cell14.getStringCellValue() != null) && (!(cell14.getStringCellValue().equals("")))) {
            p.setPoleRadius(cell14.getStringCellValue());
          }

          if ((cell15 != null) && (cell15.getStringCellValue() != null) && (!(cell15.getStringCellValue().equals("")))) {
            if (cell15.getCellType() == 0)
              p.setPoleAddress(df.format(cell15.getNumericCellValue()));
            else {
              p.setPoleAddress(cell15.getStringCellValue());
            }

          }

          if ((cell16 != null) && (cell16.getCellType() == 0)) {
            p.setXCoordinate(cell16.getNumericCellValue()+"");
          }
          else if ((cell16 != null) && (cell16.getStringCellValue() != null) && (!(cell16.getStringCellValue().equals("")))) {
            p.setXCoordinate(cell16.getStringCellValue());
          }

          if ((cell17 != null) && (cell17.getCellType() == 0)) {
            p.setYCoordinate(cell17.getNumericCellValue()+"");
          }
          else if ((cell17 != null) && (cell17.getStringCellValue() != null) && (!(cell17.getStringCellValue().equals("")))) {
            p.setYCoordinate(cell17.getStringCellValue());
          }

          if ((cell19 != null) && (cell19.getStringCellValue() != null) && (!(cell19.getStringCellValue().equals("")))) {
            p.setRenewalExpirationDate(sdf1.parse(cell19.getStringCellValue()));
          }

          if ((cell20 != null) && (cell20.getStringCellValue() != null) && (!(cell20.getStringCellValue().equals("")))) {
            if (cell20.getStringCellValue().equals("硬件维修"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(1));
            else if (cell20.getStringCellValue().equals("紧急备件支持"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(2));
            else if (cell20.getStringCellValue().equals("软件补丁版本支持"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(3));
            else if (cell20.getStringCellValue().equals("技术支持服务"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(4));
            else if (cell20.getStringCellValue().equals("电话咨询"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(4));
            else if (cell20.getStringCellValue().equals("电话支持"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(4));
            else if (cell20.getStringCellValue().equals("远程支持"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(4));
            else if (cell20.getStringCellValue().equals("现场支持"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(4));
            else if (cell20.getStringCellValue().equals("网络预警"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(4));
            else if (cell20.getStringCellValue().equals("重要通信保障"))
              p.setMaintenanceTypeEnumId(Integer.valueOf(4));
            else if (cell20.getStringCellValue().equals("无")) {
              p.setMaintenanceTypeEnumId(Integer.valueOf(5));
            }

          }

          if ((cell21 != null) && (cell21.getCellType() == 0)) {
            p.setPurchasedMaintenanceTime(cell21.getNumericCellValue()+"");
          }
          else if ((cell21 != null) && (cell21.getStringCellValue() != null) && (!(cell21.getStringCellValue().equals("")))) {
            p.setPurchasedMaintenanceTime(cell21.getStringCellValue());
          }

          if ((cell22 != null) && (cell22.getStringCellValue() != null) && (!(cell22.getStringCellValue().equals("")))) {
            if (cell22.getStringCellValue().equals("是"))
              p.setIsAuthorizationManagement(Integer.valueOf(1));
            else if (cell22.getStringCellValue().equals("否")) {
              p.setIsAuthorizationManagement(Integer.valueOf(2));
            }

          }

          if ((cell23 != null) && (cell23.getStringCellValue() != null) && (!(cell23.getStringCellValue().equals("")))) {
            p.setAuthorizationManagementUnit(cell23.getStringCellValue());
          }

          if ((cell24 != null) && (cell24.getStringCellValue() != null) && (!(cell24.getStringCellValue().equals("")))) {
            p.setDesignPurposes(cell24.getStringCellValue());
          }

          if ((cell25 != null) && (cell25.getStringCellValue() != null) && (!(cell25.getStringCellValue().equals("")))) {
            p.setNote(cell25.getStringCellValue());
          }

          list.add(p);
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertPole((PoleInfoBean)list.get(i));
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertPole", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getPolelist()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      String ecodeString = "";
      String modelPath = "";
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "pole.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);

      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      if (this.ids != null) {
        this.pole = new PoleInfoBean();
        this.pole.setIds(this.ids);
        List lists = this.dataservice.getPolelist(this.pole);
        for (int j = 0; j < lists.size(); ++j) {
          PoleInfoBean p = (PoleInfoBean)lists.get(j);
          if (p == null)
            continue;
          HSSFRow row = sheet.createRow((short)j + 3);
          HSSFCell cell1 = row.createCell(1);
          HSSFCell cell2 = row.createCell(2);
          HSSFCell cell3 = row.createCell(3);
          HSSFCell cell4 = row.createCell(4);
          HSSFCell cell5 = row.createCell(5);
          HSSFCell cell6 = row.createCell(6);
          HSSFCell cell7 = row.createCell(7);
          HSSFCell cell8 = row.createCell(8);
          HSSFCell cell9 = row.createCell(9);
          HSSFCell cell10 = row.createCell(10);
          HSSFCell cell11 = row.createCell(11);
          HSSFCell cell12 = row.createCell(12);
          HSSFCell cell13 = row.createCell(13);
          HSSFCell cell14 = row.createCell(14);
          HSSFCell cell15 = row.createCell(15);
          HSSFCell cell16 = row.createCell(16);
          HSSFCell cell17 = row.createCell(17);
          HSSFCell cell18 = row.createCell(18);
          HSSFCell cell19 = row.createCell(19);
          HSSFCell cell20 = row.createCell(20);
          HSSFCell cell21 = row.createCell(21);
          HSSFCell cell22 = row.createCell(22);
          HSSFCell cell23 = row.createCell(23);
          HSSFCell cell24 = row.createCell(24);
          HSSFCell cell25 = row.createCell(25);

          cell1.setCellType(1);
          cell2.setCellType(1);
          cell3.setCellType(1);
          cell4.setCellType(1);
          cell5.setCellType(1);
          cell6.setCellType(1);
          cell7.setCellType(1);
          cell8.setCellType(1);
          cell9.setCellType(1);
          cell10.setCellType(1);
          cell11.setCellType(1);
          cell12.setCellType(1);
          cell13.setCellType(1);
          cell14.setCellType(1);
          cell15.setCellType(1);
          cell16.setCellType(1);
          cell17.setCellType(1);
          cell18.setCellType(1);
          cell19.setCellType(1);
          cell20.setCellType(1);
          cell21.setCellType(1);
          cell22.setCellType(1);
          cell23.setCellType(1);
          cell24.setCellType(1);
          cell25.setCellType(1);

          if (p.getPoleNameSub() != null)
            cell1.setCellValue(p.getPoleNameSub());
          else {
            cell1.setCellValue(p.getPoleName());
          }

          if (p.getMaintenanceAreaName() != null) {
            cell2.setCellValue(p.getMaintenanceAreaName());
          }

          if (p.getPoleTypeEnumId() != null) {
            if (p.getPoleTypeEnumId().intValue() == 1)
              cell3.setCellValue("普通杆");
            else if (p.getPoleTypeEnumId().intValue() == 2)
              cell3.setCellValue("单接杆");
            else if (p.getPoleTypeEnumId().intValue() == 3)
              cell3.setCellValue("双接杆");
            else if (p.getPoleTypeEnumId().intValue() == 4)
              cell3.setCellValue("H型杆");
            else if (p.getPoleTypeEnumId().intValue() == 5)
              cell3.setCellValue("A型杆");
            else if (p.getPoleTypeEnumId().intValue() == 6)
              cell3.setCellValue("L型杆");
            else if (p.getPoleTypeEnumId().intValue() == 7)
              cell3.setCellValue("三角杆");
            else if (p.getPoleTypeEnumId().intValue() == 8)
              cell3.setCellValue("井型杆");
            else if (p.getPoleTypeEnumId().intValue() == 9)
              cell3.setCellValue("引上杆");
            else if (p.getPoleTypeEnumId().intValue() == 10)
              cell3.setCellValue("终端杆");
            else if (p.getPoleTypeEnumId().intValue() == 11)
              cell3.setCellValue("角杆杆");
            else if (p.getPoleTypeEnumId().intValue() == 12)
              cell3.setCellValue("分歧杆");
            else if (p.getPoleTypeEnumId().intValue() == 13)
              cell3.setCellValue("T型杆");
            else if (p.getPoleTypeEnumId().intValue() == 14) {
              cell3.setCellValue("跨路杆");
            }
          }

          if (p.getPoleMaterial() != null) {
            if (p.getPoleMaterial().equals("1"))
              cell4.setCellValue("钢筋混凝土电杆");
            else if (p.getPoleMaterial().equals("2"))
              cell4.setCellValue("木质电杆");
            else if (p.getPoleMaterial().equals("3")) {
              cell4.setCellValue("铁质电杆");
            }
          }

          if (p.getPoleLength() != null) {
            cell5.setCellValue(p.getPoleLength());
          }

          if (p.getPoleLongitude() != null) {
            cell6.setCellValue(p.getPoleLongitude());
          }

          if (p.getPoleLatitude() != null) {
            cell7.setCellValue(p.getPoleLatitude());
          }

          if (p.getMaintenanceModeEnumId() != null) {
            if (p.getMaintenanceModeEnumId().intValue() == 1) {
              cell8.setCellValue("自维");
              if (p.getMaintenanceOrgId() != null) {
                cell9.setCellValue(p.getMaintenanceOrgId());
              }
              if (p.getMaintainerId() != null)
                cell18.setCellValue(p.getMaintainerId());
            }
            else if (p.getMaintenanceModeEnumId().intValue() == 2) {
              cell8.setCellValue("代维");
              if (p.getThirdPartyMaintenanceOrg() != null) {
                cell10.setCellValue(p.getThirdPartyMaintenanceOrg());
              }
            }
          }

          if (p.getPoleAlias() != null) {
            cell11.setCellValue(p.getPoleAlias());
          }

          if (p.getAssetTagNumber() != null) {
            cell12.setCellValue(p.getAssetTagNumber());
          }

          if (p.getBuriedDepth() != null) {
            cell13.setCellValue(p.getBuriedDepth());
          }

          if (p.getPoleRadius() != null) {
            cell14.setCellValue(p.getPoleRadius());
          }

          if (p.getPoleAddress() != null) {
            cell15.setCellValue(p.getPoleAddress());
          }

          if (p.getXCoordinate() != null) {
            cell16.setCellValue(p.getXCoordinate());
          }

          if (p.getYCoordinate() != null) {
            cell17.setCellValue(p.getYCoordinate());
          }

          if (p.getRenewalExpirationDate() != null) {
            cell19.setCellValue(sdf1.format(p.getRenewalExpirationDate()));
          }

          if (p.getMaintenanceTypeEnumId() != null) {
            if (p.getMaintenanceTypeEnumId().intValue() == 1)
              cell20.setCellValue("硬件维修");
            else if (p.getMaintenanceTypeEnumId().intValue() == 2)
              cell20.setCellValue("紧急备件支持");
            else if (p.getMaintenanceTypeEnumId().intValue() == 3)
              cell20.setCellValue("软件补丁版本支持");
            else if (p.getMaintenanceTypeEnumId().intValue() == 4)
              cell20.setCellValue("技术支持服务");
            else if (p.getMaintenanceTypeEnumId().intValue() == 5) {
              cell20.setCellValue("无");
            }
          }

          if (p.getPurchasedMaintenanceTime() != null) {
            cell21.setCellValue(p.getPurchasedMaintenanceTime());
          }

          if (p.getIsAuthorizationManagement() != null) {
            if (p.getIsAuthorizationManagement().intValue() == 1)
              cell22.setCellValue("是");
            else if (p.getIsAuthorizationManagement().intValue() == 2) {
              cell22.setCellValue("否");
            }
          }
          if (p.getAuthorizationManagementUnit() != null) {
            cell23.setCellValue(p.getAuthorizationManagementUnit());
          }
          if (p.getDesignPurposes() != null) {
            cell24.setCellValue(p.getDesignPurposes());
          }

          if (p.getNote() != null) {
            cell25.setCellValue(p.getNote());
          }

        }

        String filename = dlTempPath + File.separator + "_" + "电杆信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        String sourceFilePath = dlTempPath + File.separator;
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "电杆信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getPole", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertwell()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        for (int i = 3; i <= rows; ++i) {
          WellInfoBean p = new WellInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);
          HSSFCell cell12 = row.getCell(12);
          HSSFCell cell13 = row.getCell(13);
          HSSFCell cell14 = row.getCell(14);
          HSSFCell cell15 = row.getCell(15);
          HSSFCell cell16 = row.getCell(16);
          HSSFCell cell17 = row.getCell(17);
          HSSFCell cell18 = row.getCell(18);
          HSSFCell cell19 = row.getCell(19);
          HSSFCell cell20 = row.getCell(20);
          HSSFCell cell21 = row.getCell(21);
          HSSFCell cell22 = row.getCell(22);
          HSSFCell cell23 = row.getCell(23);
          HSSFCell cell24 = row.getCell(24);
          HSSFCell cell25 = row.getCell(25);
          HSSFCell cell26 = row.getCell(26);
          HSSFCell cell27 = row.getCell(27);
          HSSFCell cell28 = row.getCell(28);
          HSSFCell cell29 = row.getCell(29);
          HSSFCell cell30 = row.getCell(30);
          HSSFCell cell31 = row.getCell(31);
          HSSFCell cell32 = row.getCell(32);
          HSSFCell cell33 = row.getCell(33);
          HSSFCell cell34 = row.getCell(34);
          HSSFCell cell35 = row.getCell(35);
          HSSFCell cell36 = row.getCell(36);
          HSSFCell cell37 = row.getCell(37);
          HSSFCell cell38 = row.getCell(38);
          HSSFCell cell39 = row.getCell(39);
          HSSFCell cell40 = row.getCell(40);
          HSSFCell cell41 = row.getCell(41);
          HSSFCell cell42 = row.getCell(42);
          HSSFCell cell43 = row.getCell(43);
          HSSFCell cell44 = row.getCell(44);
          HSSFCell cell45 = row.getCell(45);
          HSSFCell cell46 = row.getCell(46);

          if ((cell1 != null) && (cell1.getStringCellValue() != null) && (!(cell1.getStringCellValue().equals("")))) {
            String temp = "";
            if (cell1.getCellType() == 0)
              temp = df.format(cell1.getNumericCellValue());
            else {
              temp = cell1.getStringCellValue();
            }
            if (temp.indexOf("#") != -1)
              if (temp.indexOf("_") != -1) {
                p.setWellNameSub(temp);
                p.setWellName(temp.substring(0, temp.indexOf("#")));
                p.setWellNo(temp.substring(temp.indexOf("#") + 1, temp.indexOf("_")));
                p.setWellSubNo(temp.substring(temp.indexOf("_") + 1, temp.length()));
              } else {
                p.setWellNameSub(temp);
                p.setWellName(temp.substring(0, temp.indexOf("#")));
                p.setWellNo(temp.substring(temp.indexOf("#") + 1, temp.length()));
              }
            else
              p.setWellName(temp);
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段人/手井名称未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell2 != null) && (cell2.getStringCellValue() != null) && (!(cell2.getStringCellValue().equals("")))) {
            DomainBean domain = new DomainBean();
            if (cell2.getCellType() == 0)
              domain.setDomainName(df.format(cell2.getNumericCellValue()));
            else {
              domain.setDomainName(cell2.getStringCellValue());
            }
            domain = this.dataservice.getDomain(domain);
            this.message = "对不起，" + (i + 1) + "所属维护区域未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + (i + 1) + "行必填字段所属维护区域未填的，请填好后重新导入！";
          return "success";
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertWell((WellInfoBean)list.get(i));
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertwell", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getWelllist()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      String ecodeString = "";
      String modelPath = "";
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "well.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);

      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      if (this.ids != null) {
        this.well = new WellInfoBean();
        this.well.setIds(this.ids);
        List lists = this.dataservice.getWelllist(this.well);
        for (int j = 0; j < lists.size(); ++j) {
          WellInfoBean p = (WellInfoBean)lists.get(j);
          if (p == null)
            continue;
          HSSFRow row = sheet.createRow((short)j + 3);
          HSSFCell cell1 = row.createCell(1);
          HSSFCell cell2 = row.createCell(2);
          HSSFCell cell3 = row.createCell(3);
          HSSFCell cell4 = row.createCell(4);
          HSSFCell cell5 = row.createCell(5);
          HSSFCell cell6 = row.createCell(6);
          HSSFCell cell7 = row.createCell(7);
          HSSFCell cell8 = row.createCell(8);
          HSSFCell cell9 = row.createCell(9);
          HSSFCell cell10 = row.createCell(10);
          HSSFCell cell11 = row.createCell(11);
          HSSFCell cell12 = row.createCell(12);
          HSSFCell cell13 = row.createCell(13);
          HSSFCell cell14 = row.createCell(14);
          HSSFCell cell15 = row.createCell(15);
          HSSFCell cell16 = row.createCell(16);
          HSSFCell cell17 = row.createCell(17);
          HSSFCell cell18 = row.createCell(18);
          HSSFCell cell19 = row.createCell(19);
          HSSFCell cell20 = row.createCell(20);
          HSSFCell cell21 = row.createCell(21);
          HSSFCell cell22 = row.createCell(22);
          HSSFCell cell23 = row.createCell(23);
          HSSFCell cell24 = row.createCell(24);
          HSSFCell cell25 = row.createCell(25);
          HSSFCell cell26 = row.createCell(26);
          HSSFCell cell27 = row.createCell(27);
          HSSFCell cell28 = row.createCell(28);
          HSSFCell cell29 = row.createCell(29);
          HSSFCell cell30 = row.createCell(30);
          HSSFCell cell31 = row.createCell(31);
          HSSFCell cell32 = row.createCell(32);
          HSSFCell cell33 = row.createCell(33);
          HSSFCell cell34 = row.createCell(34);
          HSSFCell cell35 = row.createCell(35);
          HSSFCell cell36 = row.createCell(36);
          HSSFCell cell37 = row.createCell(37);
          HSSFCell cell38 = row.createCell(38);
          HSSFCell cell39 = row.createCell(39);
          HSSFCell cell40 = row.createCell(40);
          HSSFCell cell41 = row.createCell(41);
          HSSFCell cell42 = row.createCell(42);
          HSSFCell cell43 = row.createCell(43);
          HSSFCell cell44 = row.createCell(44);
          HSSFCell cell45 = row.createCell(45);
          HSSFCell cell46 = row.createCell(46);

          cell1.setCellType(1);
          cell2.setCellType(1);
          cell3.setCellType(1);
          cell4.setCellType(1);
          cell5.setCellType(1);
          cell6.setCellType(1);
          cell7.setCellType(1);
          cell8.setCellType(1);
          cell9.setCellType(1);
          cell10.setCellType(1);
          cell11.setCellType(1);
          cell12.setCellType(1);
          cell13.setCellType(1);
          cell14.setCellType(1);
          cell15.setCellType(1);
          cell16.setCellType(1);
          cell17.setCellType(1);
          cell18.setCellType(1);
          cell19.setCellType(1);
          cell20.setCellType(1);
          cell21.setCellType(1);
          cell22.setCellType(1);
          cell23.setCellType(1);
          cell24.setCellType(1);
          cell25.setCellType(1);
          cell26.setCellType(1);
          cell27.setCellType(1);
          cell28.setCellType(1);
          cell29.setCellType(1);
          cell30.setCellType(1);
          cell31.setCellType(1);
          cell32.setCellType(1);
          cell33.setCellType(1);
          cell34.setCellType(1);
          cell35.setCellType(1);
          cell36.setCellType(1);
          cell37.setCellType(1);
          cell38.setCellType(1);
          cell38.setCellType(1);
          cell39.setCellType(1);
          cell40.setCellType(1);
          cell41.setCellType(1);
          cell42.setCellType(1);
          cell43.setCellType(1);
          cell44.setCellType(1);
          cell45.setCellType(1);
          cell46.setCellType(1);

          if (p.getWellNameSub() != null)
            cell1.setCellValue(p.getWellNameSub());
          else {
            cell1.setCellValue(p.getWellName());
          }

          if (p.getLongitude() != null) {
            cell5.setCellValue(p.getLongitude());
          }

          if (p.getLatitude() != null) {
            cell6.setCellValue(p.getLatitude());
          }

          if (p.getConstructionSharingEnumId() != null) {
            if (p.getConstructionSharingEnumId().intValue() == 1) {
              cell7.setCellValue("共建");
              if (p.getConstructionSharingOrg() != null)
                cell8.setCellValue(p.getConstructionSharingOrg());
            }
            else if (p.getConstructionSharingEnumId().intValue() == 2) {
              cell7.setCellValue("共享");
              if (p.getConstructionSharingOrg() != null) {
                cell8.setCellValue(p.getConstructionSharingOrg());
              }
              if (p.getSharingTypeEnumId() != null) {
                if (p.getSharingTypeEnumId().intValue() == 1)
                  cell9.setCellValue("我方共享他方");
                else if (p.getSharingTypeEnumId().intValue() == 2)
                  cell9.setCellValue("他方共享我方");
              }
            }
            else if (p.getConstructionSharingEnumId().intValue() == 3) {
              cell7.setCellValue("自建自用");
            } else if (p.getConstructionSharingEnumId().intValue() == 4) {
              cell7.setCellValue("自建预留");
            }

          }

          if (p.getRentFlag() != null) {
            if (p.getRentFlag().intValue() == 1)
              cell10.setCellValue("是");
            else if (p.getRentFlag().intValue() == 2) {
              cell10.setCellValue("否");
            }
          }

          if (p.getAlias() != null) {
            cell11.setCellValue(p.getAlias());
          }


          if (p.getAssetTagNumber() != null) {
            cell13.setCellValue(p.getAssetTagNumber());
          }

          if (p.getAssetsOwnership() != null) {
            cell14.setCellValue(p.getAssetsOwnership());
          }

          if (p.getXCoordinate() != null) {
            cell15.setCellValue(p.getXCoordinate());
          }

          if (p.getYCoordinate() != null) {
            cell16.setCellValue(p.getYCoordinate());
          }

          if (p.getResourceLifePeriodEnumDate() != null) {
            cell42.setCellValue(sdf1.format(p.getResourceLifePeriodEnumDate()));
          }

          if (p.getIsAuthorizationManagement() != null) {
            if (p.getIsAuthorizationManagement().intValue() == 1)
              cell43.setCellValue("是");
            else if (p.getIsAuthorizationManagement().intValue() == 2) {
              cell43.setCellValue("否");
            }
          }

          if (p.getAuthorizationManagementUnit() != null) {
            cell44.setCellValue(p.getAuthorizationManagementUnit());
          }
          if (p.getDesignPurposes() != null) {
            cell45.setCellValue(p.getDesignPurposes());
          }

          if (p.getNote() != null) {
            cell46.setCellValue(p.getNote());
          }

        }

        String filename = dlTempPath + File.separator + "_" + "井信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        String sourceFilePath = dlTempPath + File.separator;
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "井信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getWell", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertLedup()
  {
    try
    {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");

        List list = new ArrayList();
        for (int i = 3; i <= rows; ++i) {
          LedupInfoBean p = new LedupInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);
          HSSFCell cell12 = row.getCell(12);
          HSSFCell cell13 = row.getCell(13);
          HSSFCell cell14 = row.getCell(14);
          HSSFCell cell15 = row.getCell(15);
          HSSFCell cell16 = row.getCell(16);
          HSSFCell cell17 = row.getCell(17);
          HSSFCell cell18 = row.getCell(18);

          if ((cell1 != null) && (cell1.getStringCellValue() != null) && (!(cell1.getStringCellValue().equals("")))) {
            if (cell1.getCellType() == 0)
              p.setLedupPointName(df.format(cell1.getNumericCellValue()));
            else
              p.setLedupPointName(cell1.getStringCellValue());
          }
          else {
            this.message = "对不起，" + (i + 1) + "行必填字段引上点名称未填的，请填好后重新导入！";
            return "success";
          }

          if (cell2.getCellType() == 0) {
            p.setLedupPointCode(cell2.getNumericCellValue()+"");
          }
          else if ((cell2 != null) && (cell2.getStringCellValue() != null) && (!(cell2.getStringCellValue().equals("")))) {
            p.setLedupPointCode(cell2.getStringCellValue());
          } else {
            this.message = "对不起，" + (i + 1) + "行必填字段引上点编号未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell3 != null) && (cell3.getStringCellValue() != null) && (!(cell3.getStringCellValue().equals("")))) {
            DomainBean domain = new DomainBean();
            if (cell3.getCellType() == 0)
              domain.setDomainName(df.format(cell3.getNumericCellValue()));
            else {
              domain.setDomainName(cell3.getStringCellValue());
            }
            domain = this.dataservice.getDomain(domain);
            if (domain != null) {
              p.setRegion(String.valueOf(domain.getDomainId()));
            }
            this.message = "对不起，" + i + "所属维护区域未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段所属维护区域未填的，请填好后重新导入！";
          String temp ="";
          if ((cell4 != null) && (cell4.getStringCellValue() != null) && (!(cell4.getStringCellValue().equals("")))) {
            temp = "";
            if (cell4.getCellType() == 0)
              temp = df.format(cell4.getNumericCellValue());
            else {
              temp = cell4.getStringCellValue();
            }
            WellInfoBean wel = new WellInfoBean();
            wel.setWellNameSub(temp);
            wel = this.dataservice.getWell(wel);
            if (wel != null) {
              p.setWellId(wel.getWellId());
            }
            this.message = "对不起，" + i + "所属人/手井未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + (i + 1) + "行必填字段所属人/手井未填的，请填好后重新导入！";
          if ((cell5 != null) && (cell5.getStringCellValue() != null) && (!(cell5.getStringCellValue().equals("")))) {
            label803: temp = "";
            if (cell5.getCellType() == 0)
              temp = df.format(cell5.getNumericCellValue());
            else {
              temp = cell5.getStringCellValue();
            }
            if (temp.equals("电杆")) {
              p.setLedupType("1");
              if ((cell6 != null) && (cell6.getStringCellValue() != null) && (!(cell6.getStringCellValue().equals("")))) {
                PoleInfoBean pol = new PoleInfoBean();
                pol.setPoleNameSub(cell6.getStringCellValue());
                pol = this.dataservice.getPole(pol);
                if (pol != null) {
                  p.setPoleId(pol.getPoleId());
                }
                this.message = "对不起，" + i + "所属人/手井未查到，请核对后重新导入！";
                return "success";
              }

              this.message = "对不起，" + (i + 1) + "引上点类型是电杆时，所属电杆为必填字段，请填好后重新导入！";
              return "success";
            }
            if (temp.equals("墙壁")) {
              p.setLedupType("2");
              if ((cell7 != null) && (cell7.getStringCellValue() != null) && (!(cell7.getStringCellValue().equals("")))) {
                p.setWalladdr(cell7.getStringCellValue());
              } else {
                this.message = "对不起，" + (i + 1) + "引上点类型是墙壁时，墙壁地址为必填字段，请填好后重新导入！";
                return "success";
              }
              if ((cell8 != null) && (cell8.getCellType() == 0)) {
                p.setLongitude(cell8.getNumericCellValue()+"");
              }
              else if ((cell8 != null) && (cell8.getStringCellValue() != null) && (!(cell8.getStringCellValue().equals("")))) {
                p.setLongitude(cell8.getStringCellValue());
              } else {
                this.message = "对不起，" + (i + 1) + "引上点类型是墙壁时，经度为必填字段，请填好后重新导入！";
                return "success";
              }

              if ((cell9 != null) && (cell9.getCellType() == 0)) {
                p.setLatitude(cell9.getNumericCellValue()+"");
              }
              else if ((cell9 != null) && (cell9.getStringCellValue() != null) && (!(cell9.getStringCellValue().equals("")))) {
                p.setLatitude(cell9.getStringCellValue());
              } else {
                this.message = "对不起，" + (i + 1) + "引上点类型是墙壁时，纬度为必填字段，请填好后重新导入！";
                return "success";
              }
            }
          }
          else
          {
            this.message = "对不起，" + (i + 1) + "行必填字段引上点类型未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell10 != null) && (cell10.getCellType() == 0)) {
            label1360: p.setXCoordinate(cell10.getNumericCellValue()+"");
          }
          else if ((cell10 != null) && (cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals("")))) {
            p.setXCoordinate(cell10.getStringCellValue());
          }

          if ((cell11 != null) && (cell11.getCellType() == 0)) {
            p.setYCoordinate(cell11.getNumericCellValue()+"");
          }
          else if ((cell11 != null) && (cell11.getStringCellValue() != null) && (!(cell11.getStringCellValue().equals("")))) {
            p.setYCoordinate(cell11.getStringCellValue());
          }

          if ((cell12 != null) && (cell12.getCellType() == 0)) {
            p.setTubeQuantity(cell12.getNumericCellValue()+"");
          }
          else if ((cell12 != null) && (cell12.getStringCellValue() != null) && (!(cell12.getStringCellValue().equals("")))) {
            p.setTubeQuantity(cell12.getStringCellValue());
          }

          if ((cell13 != null) && (cell13.getCellType() == 0)) {
            p.setOccupiedTubeQuantity(cell13.getNumericCellValue()+"");
          }
          else if ((cell13 != null) && (cell13.getStringCellValue() != null) && (!(cell13.getStringCellValue().equals("")))) {
            p.setOccupiedTubeQuantity(cell13.getStringCellValue());
          }

          if ((cell14 != null) && (cell14.getCellType() == 0)) {
            p.setReservedTubeQuantity(cell14.getNumericCellValue()+"");
          }
          else if ((cell14 != null) && (cell14.getStringCellValue() != null) && (!(cell14.getStringCellValue().equals("")))) {
            p.setReservedTubeQuantity(cell14.getStringCellValue());
          }

          if ((cell15 != null) && (cell15.getStringCellValue() != null) && (!(cell15.getStringCellValue().equals("")))) {
            if (cell15.getStringCellValue().equals("是"))
              p.setIsAuthorizationManagement(Integer.valueOf(1));
            else if (cell15.getStringCellValue().equals("否")) {
              p.setIsAuthorizationManagement(Integer.valueOf(2));
            }

          }

          if ((cell16 != null) && (cell16.getStringCellValue() != null) && (!(cell16.getStringCellValue().equals("")))) {
            p.setAuthorizationManagementUnit(cell16.getStringCellValue());
          }

          if ((cell17 != null) && (cell17.getStringCellValue() != null) && (!(cell17.getStringCellValue().equals("")))) {
            p.setDesignPurposes(cell17.getStringCellValue());
          }

          if ((cell18 != null) && (cell18.getStringCellValue() != null) && (!(cell18.getStringCellValue().equals("")))) {
            p.setNote(cell18.getStringCellValue());
          }

          list.add(p);
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertLedup((LedupInfoBean)list.get(i));
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertLedup", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getLeduplist()
  {
    try {
      this.userInfoBean = ((UserInfoBean)getSession().getAttribute("userBean"));
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      String ecodeString = "";
      String modelPath = "";
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "Ledup.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);

      if (this.ids != null) {
        this.Ledup = new LedupInfoBean();
        this.Ledup.setIds(this.ids);
        List lists = this.dataservice.getLeduplist(this.Ledup);
        for (int j = 0; j < lists.size(); ++j) {
          LedupInfoBean p = (LedupInfoBean)lists.get(j);
          if (p == null)
            continue;
          HSSFRow row = sheet.createRow((short)j + 3);
          HSSFCell cell1 = row.createCell(1);
          HSSFCell cell2 = row.createCell(2);
          HSSFCell cell3 = row.createCell(3);
          HSSFCell cell4 = row.createCell(4);
          HSSFCell cell5 = row.createCell(5);
          HSSFCell cell6 = row.createCell(6);
          HSSFCell cell7 = row.createCell(7);
          HSSFCell cell8 = row.createCell(8);
          HSSFCell cell9 = row.createCell(9);
          HSSFCell cell10 = row.createCell(10);
          HSSFCell cell11 = row.createCell(11);
          HSSFCell cell12 = row.createCell(12);
          HSSFCell cell13 = row.createCell(13);
          HSSFCell cell14 = row.createCell(14);
          HSSFCell cell15 = row.createCell(15);
          HSSFCell cell16 = row.createCell(16);
          HSSFCell cell17 = row.createCell(17);
          HSSFCell cell18 = row.createCell(18);

          cell1.setCellType(1);
          cell2.setCellType(1);
          cell3.setCellType(1);
          cell4.setCellType(1);
          cell5.setCellType(1);
          cell6.setCellType(1);
          cell7.setCellType(1);
          cell8.setCellType(1);
          cell9.setCellType(1);
          cell10.setCellType(1);
          cell11.setCellType(1);
          cell12.setCellType(1);
          cell13.setCellType(1);
          cell14.setCellType(1);
          cell15.setCellType(1);
          cell16.setCellType(1);
          cell17.setCellType(1);
          cell18.setCellType(1);

          if (p.getLedupPointName() != null) {
            cell1.setCellValue(p.getLedupPointName());
          }

          if (p.getLedupPointCode() != null) {
            cell2.setCellValue(p.getLedupPointCode());
          }

          if (p.getMaintenanceAreaName() != null) {
            cell3.setCellValue(p.getMaintenanceAreaName());
          }

          if (p.getWellName() != null) {
            cell4.setCellValue(p.getWellName());
          }

          if (p.getLedupType() != null) {
            if (p.getLedupType().equals("1")) {
              cell5.setCellValue("电杆");
              if (p.getPoleName() != null)
                cell6.setCellValue(p.getPoleName());
            }
            else if (p.getLedupType().equals("2")) {
              cell5.setCellValue("墙壁");
              if (p.getWalladdr() != null) {
                cell7.setCellValue(p.getWalladdr());
              }
              if (p.getLongitude() != null) {
                cell8.setCellValue(p.getLongitude());
              }
              if (p.getLatitude() != null) {
                cell9.setCellValue(p.getLatitude());
              }
            }
          }

          if (p.getXCoordinate() != null) {
            cell10.setCellValue(p.getXCoordinate());
          }

          if (p.getYCoordinate() != null) {
            cell11.setCellValue(p.getYCoordinate());
          }

          if (p.getTubeQuantity() != null) {
            cell12.setCellValue(p.getTubeQuantity());
          }

          if (p.getOccupiedTubeQuantity() != null) {
            cell13.setCellValue(p.getOccupiedTubeQuantity());
          }

          if (p.getReservedTubeQuantity() != null) {
            cell14.setCellValue(p.getReservedTubeQuantity());
          }

          if (p.getIsAuthorizationManagement() != null) {
            if (p.getIsAuthorizationManagement().intValue() == 1)
              cell15.setCellValue("是");
            else if (p.getIsAuthorizationManagement().intValue() == 2) {
              cell15.setCellValue("否");
            }
          }

          if (p.getAuthorizationManagementUnit() != null) {
            cell16.setCellValue(p.getAuthorizationManagementUnit());
          }

          if (p.getDesignPurposes() != null) {
            cell17.setCellValue(p.getDesignPurposes());
          }

          if (p.getNote() != null) {
            cell18.setCellValue(p.getNote());
          }

        }

        String filename = dlTempPath + File.separator + "_" + "引上点信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        String sourceFilePath = dlTempPath + File.separator;
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "引上点信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getLedup", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertsupport()
  {
    try {
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        for (int i = 3; i <= rows; ++i) {
          SupportInfoBean s = new SupportInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);
          HSSFCell cell12 = row.getCell(12);
          HSSFCell cell13 = row.getCell(13);
          HSSFCell cell14 = row.getCell(14);

          if ((cell1.getStringCellValue() != null) && (!(cell1.getStringCellValue().equals(""))))
          {
            s.setSupportName(cell1.getStringCellValue());
          }
          else {
            this.message = "对不起，" + i + "行必填字段撑点名称未填的，请填好后重新导入撑点名称！";
            return "success";
          }

          if ((cell2.getStringCellValue() != null) && (!(cell2.getStringCellValue().equals("")))) {
            DomainBean domain = new DomainBean();
            if (cell2.getCellType() == 0)
              domain.setDomainName(df.format(cell2.getNumericCellValue()));
            else {
              domain.setDomainName(cell2.getStringCellValue());
            }
            domain = this.dataservice.getDomain(domain);
            if (domain != null) {
              s.setRegionId(domain.getDomainId());
            }
            this.message = "对不起，" + i + "所属维护区域未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段所属维护区域未填的，请填好后重新导入！";
          if ((cell3.getStringCellValue() != null) && (!(cell3.getStringCellValue().equals(""))))
          {
            label445: s.setAddress(cell3.getStringCellValue());
          }
          else {
            this.message = "对不起，" + i + "行必填字段所在位置未填的，请填好后重新导入所在位置！";
            return "success";
          }

          if ((cell4 != null) && (cell4.getStringCellValue() != null) && (!(cell4.getStringCellValue().equals("")))) {
            String level = "";
            if (cell4.getCellType() == 0)
              level = df.format(cell4.getNumericCellValue());
            else {
              level = cell4.getStringCellValue();
            }
            if (level.equals("终端撑点"))
              s.setSupportType("1");
            else if (level.equals("分叉撑点"))
              s.setSupportType("2");
            else if (level.equals("拐点撑点"))
              s.setSupportType("3");
          }
          else {
            this.message = "对不起，" + i + "行必填字段撑点类型未填的，请填好后重新导入！";
            return "success";
          }

          if (cell5.getCellType() == 0) {
            s.setLon(cell5.getNumericCellValue()+"");
          }
          else if ((cell5.getStringCellValue() != null) && (!(cell5.getStringCellValue().equals("")))) {
            s.setLon(cell5.getStringCellValue());
          } else {
            this.message = "对不起，" + i + "行必填字段经度未填的，请填好后重新导入经度！";
            return "success";
          }

          if (cell6.getCellType() == 0) {
            s.setLat(cell6.getNumericCellValue()+"");
          }
          else if ((cell6.getStringCellValue() != null) && (!(cell6.getStringCellValue().equals("")))) {
            s.setLat(cell6.getStringCellValue());
          } else {
            this.message = "对不起，" + i + "行必填字段纬度未填的，请填好后重新导入纬度！";
            return "success";
          }

          if ((cell7 != null) && (cell7.getStringCellValue() != null) && (!(cell7.getStringCellValue().equals("")))) {
            if (cell7.getCellType() == 0)
              s.setSupportBieName(cell7.getNumericCellValue()+"");
            else
              s.setSupportBieName(cell7.getStringCellValue()+"");
          }
          else {
            s.setSupportBieName(null);
          }

          if (cell8 != null) {
            if (cell8.getCellType() == 0)
              s.setPlaneRightAngleX(cell8.getNumericCellValue()+"");
            else if ((cell8.getStringCellValue() != null) && (!(cell8.getStringCellValue().equals(""))))
              s.setPlaneRightAngleX(cell8.getStringCellValue());
          }
          else {
            s.setPlaneRightAngleX(null);
          }

          if (cell9 != null) {
            if (cell9.getCellType() == 0)
              s.setPlaneRightAngleY(cell9.getNumericCellValue()+"");
            else if ((cell9.getStringCellValue() != null) && (!(cell9.getStringCellValue().equals(""))))
              s.setPlaneRightAngleY(cell9.getStringCellValue());
          }
          else {
            s.setPlaneRightAngleY(null);
          }

          if ((cell10 != null) && (cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals("")))) {
            if (cell10.getStringCellValue().equals("三角架"))
              s.setSupportJZ("1");
            else if (cell10.getStringCellValue().equals("卡钉"))
              s.setSupportJZ("2");
            else if (cell10.getStringCellValue().equals("铁件")) {
              s.setSupportJZ("3");
            }
          }
          else {
            s.setSupportType(null);
          }

          if (cell11 != null) {
            if (cell11.getCellType() == 0)
              s.setHight(cell11.getNumericCellValue()+"");
            else
              s.setHight(cell11.getStringCellValue());
          }
          else {
            s.setHight(null);
          }

          if ((cell12 != null) && (cell12.getStringCellValue() != null) && (!(cell12.getStringCellValue().equals("")))) {
            if (cell12.getStringCellValue().equals("设计状态"))
              s.setLifecycle("1");
            else if (cell12.getStringCellValue().equals("工程建设期(入网带业务前)"))
              s.setLifecycle("2");
            else if (cell12.getStringCellValue().equals("工程可用期(已入网带业务)"))
              s.setLifecycle("3");
            else if (cell12.getStringCellValue().equals("工程初验后试运行"))
              s.setLifecycle("4");
            else if (cell12.getStringCellValue().equals("工程终验后在网"))
              s.setLifecycle("5");
            else if (cell12.getStringCellValue().equals("退网状态"))
              s.setLifecycle("6");
          }
          else {
            s.setLifecycle(null);
          }

          if ((cell13 != null) && (cell13.getStringCellValue() != null) && (!(cell13.getStringCellValue().equals("")))) {
            s.setLifecycleTime(sdf1.parse(cell13.getStringCellValue()));
          }
          else {
            s.setLifecycleTime(null);
          }

          if ((cell14 != null) && (cell14.getStringCellValue() != null) && (!(cell14.getStringCellValue().equals(""))))
            s.setNote(cell14.getStringCellValue());
          else {
            s.setNote(null);
          }

          list.add(s);
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertsupport((SupportInfoBean)list.get(i));
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertsupport", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getsupportlist()
  {
    try {
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      String modelPath = "";
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_support.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);

      if (this.ids != null) {
        this.support = new SupportInfoBean();
        this.support.setIds(this.ids);
        List lists = this.dataservice.getSupportlist(this.support);
        for (int j = 0; j < lists.size(); ++j) {
          SupportInfoBean s = (SupportInfoBean)lists.get(j);
          if (s != null) {
            HSSFRow row = sheet.createRow((short)j + 3);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);
            HSSFCell cell4 = row.createCell(4);
            HSSFCell cell5 = row.createCell(5);
            HSSFCell cell6 = row.createCell(6);
            HSSFCell cell7 = row.createCell(7);
            HSSFCell cell8 = row.createCell(8);
            HSSFCell cell9 = row.createCell(9);
            HSSFCell cell10 = row.createCell(10);
            HSSFCell cell11 = row.createCell(11);
            HSSFCell cell12 = row.createCell(12);
            HSSFCell cell13 = row.createCell(13);
            HSSFCell cell14 = row.createCell(14);

            cell1.setCellType(1);
            cell2.setCellType(1);
            cell3.setCellType(1);
            cell4.setCellType(1);
            cell5.setCellType(1);
            cell6.setCellType(1);
            cell7.setCellType(1);
            cell8.setCellType(1);
            cell9.setCellType(1);
            cell10.setCellType(1);
            cell11.setCellType(1);
            cell12.setCellType(1);
            cell13.setCellType(1);
            cell14.setCellType(1);

            if (s.getSupportName() != null) {
              cell1.setCellValue(s.getSupportName());
            }
            if (s.getRegion() != null) {
              cell2.setCellValue(s.getRegion());
            }
            if (s.getAddress() != null) {
              cell3.setCellValue(s.getAddress());
            }
            if (s.getSupportType() != null) {
              if (s.getSupportType().equals("1"))
                cell4.setCellValue("终端撑点");
              else if (s.getSupportType().equals("2"))
                cell4.setCellValue("分叉撑点");
              else if (s.getSupportType().equals("3")) {
                cell4.setCellValue("拐点撑点");
              }

            }

            if (s.getLon() != null) {
              cell5.setCellValue(s.getLon());
            }
            if (s.getLat() != null) {
              cell6.setCellValue(s.getLat());
            }
            if (s.getSupportBieName() != null) {
              cell7.setCellValue(s.getSupportBieName());
            }
            if (s.getPlaneRightAngleX() != null) {
              cell8.setCellValue(s.getPlaneRightAngleX());
            }
            if (s.getPlaneRightAngleY() != null) {
              cell9.setCellValue(s.getPlaneRightAngleY());
            }

            if (s.getSupportJZ() != null) {
              if (s.getSupportJZ().equals("1"))
                cell10.setCellValue("三角架");
              else if (s.getSupportJZ().equals("2"))
                cell10.setCellValue("卡钉");
              else if (s.getSupportJZ().equals("3")) {
                cell10.setCellValue("铁件");
              }

            }

            if (s.getHight() != null) {
              cell11.setCellValue(s.getHight());
            }

            if (s.getLifecycle() != null) {
              if (s.getLifecycle().equals("1"))
                cell12.setCellValue("设计状态");
              else if (s.getLifecycle().equals("2"))
                cell12.setCellValue("工程建设期(入网带业务前)");
              else if (s.getLifecycle().equals("3"))
                cell12.setCellValue("工程可用期(已入网带业务)");
              else if (s.getLifecycle().equals("4"))
                cell12.setCellValue("工程初验后试运行");
              else if (s.getLifecycle().equals("5"))
                cell12.setCellValue("工程终验后在网");
              else if (s.getLifecycle().equals("6")) {
                cell12.setCellValue("退网状态");
              }

            }

            if (s.getLifecycleTime() != null)
            {
              cell13.setCellValue(sdf1.format(s.getLifecycleTime()));
            }
            if (s.getNote() != null) {
              cell14.setCellValue(s.getNote());
            }
          }
        }
        String filename = dlTempPath + File.separator + "_" + "撑点信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        String sourceFilePath = dlTempPath + File.separator;
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "撑点信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误,请联系系统管理员!");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getsupportlist", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertsuspension()
  {
    try
    {
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        for (int i = 3; i <= rows; ++i) {
          SuspensionWireInfoBean sw = new SuspensionWireInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);

          if ((cell1.getStringCellValue() != null) && (!(cell1.getStringCellValue().equals(""))))
          {
            sw.setSuspensionWireName(cell1.getStringCellValue());
          }
          else {
            this.message = "对不起，" + i + "行必填字段吊线名称未填的，请填好后重新导入吊线名称！";
            return "success";
          }

          if ((cell2.getStringCellValue() != null) && (!(cell2.getStringCellValue().equals(""))))
          {
            sw.setSuspensionWireCode(cell2.getStringCellValue());
          }
          else {
            this.message = "对不起，" + i + "行必填字段吊线编号未填的，请填好后重新导入所在位置！";
            return "success";
          }

          if ((cell3.getStringCellValue() != null) && (!(cell3.getStringCellValue().equals("")))) {
            DomainBean domain = new DomainBean();
            if (cell3.getCellType() == 0)
              domain.setDomainName(df.format(cell3.getNumericCellValue()));
            else {
              domain.setDomainName(cell3.getStringCellValue());
            }
            domain = this.dataservice.getDomain(domain);
            if (domain != null) {
              sw.setRegionId(domain.getDomainId());
            }
            this.message = "对不起，" + i + "所属维护区域未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段所属维护区域未填的，请填好后重新导入！";
          if ((cell4.getStringCellValue() != null) && (!(cell4.getStringCellValue().equals("")))) {
            PolelineInfoBean poleline = new PolelineInfoBean();
            if (cell4.getCellType() == 0)
              poleline.setPoleLineName(df.format(cell4.getNumericCellValue()));
            else {
              poleline.setPoleLineName(cell4.getStringCellValue());
            }
            poleline = this.dataservice.getPoleline(poleline);
            if (poleline != null) {
              sw.setPoleLineId(poleline.getPoleLineId());
            }
            this.message = "对不起，" + i + "所属杆路名称未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段所属杆路名称未填的，请填好后重新导入！";
          if (cell5.getCellType() == 0) {
            sw.setLength(cell5.getNumericCellValue()+"");
          }
          else if ((cell5 != null) && (cell5.getStringCellValue() != null) && (!(cell5.getStringCellValue().equals(""))))
            sw.setLength(cell5.getStringCellValue());
          else {
            sw.setLength(null);
          }

          if ((cell6 != null) && (cell6.getStringCellValue() != null) && (!(cell6.getStringCellValue().equals("")))) {
            String level = "";
            if (cell6.getCellType() == 0)
              level = df.format(cell6.getNumericCellValue());
            else {
              level = cell6.getStringCellValue();
            }
            if (level.equals("设计状态"))
              sw.setResourceLifePeriodEnumId(Integer.valueOf(1));
            else if (level.equals("工程建设期(入网带业务前)"))
              sw.setResourceLifePeriodEnumId(Integer.valueOf(2));
            else if (level.equals("工程可用期(已入网带业务)"))
              sw.setResourceLifePeriodEnumId(Integer.valueOf(3));
            else if (level.equals("工程初验后试运行"))
              sw.setResourceLifePeriodEnumId(Integer.valueOf(4));
            else if (level.equals("工程终验后在网"))
              sw.setResourceLifePeriodEnumId(Integer.valueOf(5));
            else if (level.equals("退网状态"))
              sw.setResourceLifePeriodEnumId(Integer.valueOf(6));
          }
          else {
            sw.setResourceLifePeriodEnumId(null);
          }

          if ((cell7 != null) && (cell7.getStringCellValue() != null) && (!(cell7.getStringCellValue().equals("")))) {
            sw.setLifecycleTime(sdf1.parse(cell7.getStringCellValue()));
          }
          else {
            sw.setLifecycleTime(null);
          }

          if ((cell8 != null) && (cell8.getStringCellValue() != null) && (!(cell8.getStringCellValue().equals("")))) {
            String YN = "";
            if (cell6.getCellType() == 0)
              YN = df.format(cell8.getNumericCellValue());
            else {
              YN = cell8.getStringCellValue();
            }
            if (YN.equals("是"))
              sw.setIsAuthorizationManagement(Integer.valueOf(1));
            else if (YN.equals("否"))
              sw.setIsAuthorizationManagement(Integer.valueOf(2));
          }
          else {
            sw.setIsAuthorizationManagement(null);
          }

          if ((cell9 != null) && (cell9.getStringCellValue() != null) && (!(cell9.getStringCellValue().equals("")))) {
            if (cell9.getCellType() == 0)
              sw.setAuthorizationManagementUnit(cell9.getNumericCellValue()+"");
            else
              sw.setAuthorizationManagementUnit(cell9.getStringCellValue());
          }
          else {
            sw.setAuthorizationManagementUnit(null);
          }

          if ((cell10 != null) && (cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals("")))) {
            if (cell10.getCellType() == 0)
              sw.setDesignPurposes(cell10.getNumericCellValue()+"");
            else
              sw.setDesignPurposes(cell10.getStringCellValue());
          }
          else {
            sw.setDesignPurposes(null);
          }

          if (sw.getNote() != null) {
            cell11.setCellValue(sw.getNote());
          }
          list.add(sw);
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertsuspension((SuspensionWireInfoBean)list.get(i));
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertsuspension", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getsuspensionlist()
  {
    try
    {
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      String modelPath = "";
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_suspension.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);

      if (this.ids != null) {
        this.suspension = new SuspensionWireInfoBean();
        this.suspension.setIds(this.ids);
        List lists = this.dataservice.getSuspensionlist(this.suspension);
        for (int j = 0; j < lists.size(); ++j) {
          SuspensionWireInfoBean sw = (SuspensionWireInfoBean)lists.get(j);
          if (sw != null) {
            HSSFRow row = sheet.createRow((short)j + 3);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);
            HSSFCell cell4 = row.createCell(4);
            HSSFCell cell5 = row.createCell(5);
            HSSFCell cell6 = row.createCell(6);
            HSSFCell cell7 = row.createCell(7);
            HSSFCell cell8 = row.createCell(8);
            HSSFCell cell9 = row.createCell(9);
            HSSFCell cell10 = row.createCell(10);
            HSSFCell cell11 = row.createCell(11);

            cell1.setCellType(1);
            cell2.setCellType(1);
            cell3.setCellType(1);
            cell4.setCellType(1);
            cell5.setCellType(1);
            cell6.setCellType(1);
            cell7.setCellType(1);
            cell8.setCellType(1);
            cell9.setCellType(1);
            cell10.setCellType(1);
            cell11.setCellType(1);

            if (sw.getSuspensionWireName() != null) {
              cell1.setCellValue(sw.getSuspensionWireName());
            }

            if (sw.getSuspensionWireCode() != null) {
              cell2.setCellValue(sw.getSuspensionWireCode());
            }

            if (sw.getRegion() != null) {
              cell3.setCellValue(sw.getRegion());
            }

            if (sw.getPoleLineName() != null) {
              cell4.setCellValue(sw.getPoleLineName());
            }

            if (sw.getLength() != null) {
              cell5.setCellValue(sw.getLength());
            }

            if (sw.getResourceLifePeriodEnumId() != null) {
              if (String.valueOf(sw.getResourceLifePeriodEnumId()).equals("1"))
                cell6.setCellValue("设计状态");
              else if (String.valueOf(sw.getResourceLifePeriodEnumId()).equals("2"))
                cell6.setCellValue("工程建设期(入网带业务前)");
              else if (String.valueOf(sw.getResourceLifePeriodEnumId()).equals("3"))
                cell6.setCellValue("工程可用期(已入网带业务)");
              else if (String.valueOf(sw.getResourceLifePeriodEnumId()).equals("4"))
                cell6.setCellValue("工程初验后试运行");
              else if (String.valueOf(sw.getResourceLifePeriodEnumId()).equals("5"))
                cell6.setCellValue("工程终验后在网");
              else if (String.valueOf(sw.getResourceLifePeriodEnumId()).equals("6")) {
                cell6.setCellValue("退网状态");
              }

            }

            if (sw.getLifecycleTime() != null) {
              cell7.setCellValue(sdf1.format(sw.getLifecycleTime()));
            }

            if (sw.getIsAuthorizationManagement() != null) {
              if (String.valueOf(sw.getIsAuthorizationManagement()).equals("1"))
                cell8.setCellValue("是");
              else if (String.valueOf(sw.getIsAuthorizationManagement()).equals("2")) {
                cell8.setCellValue("否");
              }
            }

            if (sw.getAuthorizationManagementUnit() != null) {
              cell9.setCellValue(sw.getAuthorizationManagementUnit());
            }

            if (sw.getDesignPurposes() != null) {
              cell10.setCellValue(sw.getDesignPurposes());
            }

            if (sw.getNote() != null) {
              cell11.setCellValue(sw.getNote());
            }
          }
        }
        String filename = dlTempPath + File.separator + "_" + "吊线信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        String sourceFilePath = dlTempPath + File.separator;
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "吊线信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误,请联系系统管理员!");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getsuspensionlist", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertPoleLS()
  {
    try
    {
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        for (int i = 3; i <= rows; ++i) {
          PolelineSegmentInfoBean pls = new PolelineSegmentInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);
          HSSFCell cell12 = row.getCell(12);
          HSSFCell cell13 = row.getCell(13);
          HSSFCell cell14 = row.getCell(14);

          if ((cell1.getStringCellValue() != null) && (!(cell1.getStringCellValue().equals(""))))
          {
            pls.setPoleLineSegmentName(cell1.getStringCellValue());
          }
          else {
            this.message = "对不起，" + i + "行必填字段杆路段名称未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell2.getStringCellValue() != null) && (!(cell2.getStringCellValue().equals("")))) {
            DomainBean domain = new DomainBean();
            if (cell2.getCellType() == 0)
              domain.setDomainName(df.format(cell2.getNumericCellValue()));
            else {
              domain.setDomainName(cell2.getStringCellValue());
            }
            domain = this.dataservice.getDomain(domain);
            if (domain != null) {
              pls.setMaintenanceAreaId(domain.getDomainId());
            }
            this.message = "对不起，" + i + "所属维护区域未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段所属维护区域未填的，请填好后重新导入！";
          if (cell3.getCellType() == 0) {
            label445: pls.setPoleLineSegmentLength(cell3.getNumericCellValue()+"");
          }
          else if ((cell3 != null) && (cell3.getStringCellValue() != null) && (!(cell3.getStringCellValue().equals("")))) {
            pls.setPoleLineSegmentLength(cell3.getStringCellValue());
          } else {
            this.message = "对不起，" + i + "行必填字段杆路段长度未填的，请填好后重新导入纬度！";
            return "success";
          }

          if ((cell4 != null) && (cell4.getStringCellValue() != null) && (!(cell4.getStringCellValue().equals("")))) {
            String ZT = "";
            if (cell4.getCellType() == 0)
              ZT = df.format(cell4.getNumericCellValue());
            else {
              ZT = cell4.getStringCellValue();
            }
            if (ZT.equals("占用"))
              pls.setStatus("1");
            else if (ZT.equals("空闲"))
              pls.setStatus("2");
            else if (ZT.equals("预占"))
              pls.setStatus("3");
            else if (ZT.equals("暂拆"))
              pls.setStatus("4");
            else if (ZT.equals("损坏"))
              pls.setStatus("5");
            else if (ZT.equals("废弃"))
              pls.setStatus("6");
            else if (ZT.equals("外租"))
              pls.setStatus("7");
            else if (ZT.equals("出租"))
              pls.setStatus("8");
          }
          else {
            this.message = "对不起，" + i + "行必填字段杆路段状态未填的，请填好后重新导入！";
            return "success";
          }
          PoleInfoBean pole;
          if ((cell5.getStringCellValue() != null) && (!(cell5.getStringCellValue().equals("")))) {
            pole = new PoleInfoBean();
            if (cell5.getCellType() == 0)
              pole.setPoleNameSub(df.format(cell5.getNumericCellValue()));
            else {
              pole.setPoleNameSub(cell5.getStringCellValue());
            }
            pole = this.dataservice.getPole(pole);
            if (pole != null) {
              pls.setStartDeviceId(pole.getPoleId());
            }
            this.message = "对不起，" + i + "起始电杆名称未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段起始电杆名称未填的，请填好后重新导入！";
          if ((cell6.getStringCellValue() != null) && (!(cell6.getStringCellValue().equals("")))) {
            label968: pole = new PoleInfoBean();
            if (cell6.getCellType() == 0)
              pole.setPoleNameSub(df.format(cell6.getNumericCellValue()));
            else {
              pole.setPoleNameSub(cell6.getStringCellValue());
            }
            pole = this.dataservice.getPole(pole);
            if (pole != null) {
              pls.setEndDeviceId(pole.getPoleId());
            }
            this.message = "对不起，" + i + "终止电杆名称未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段终止电杆名称未填的，请填好后重新导入！";
          if ((cell7 != null) && (cell7.getStringCellValue() != null) && (!(cell7.getStringCellValue().equals("")))) {
            if (cell7.getCellType() == 0)
              label1125: pls.setPoleLineSegmentBieName(cell7.getNumericCellValue()+"");
            else
              pls.setPoleLineSegmentBieName(cell7.getStringCellValue());
          }
          else {
            pls.setPoleLineSegmentBieName(null);
          }

          if (cell8 != null) {
            if (cell8.getCellType() == 0)
              pls.setAssetTag(cell8.getNumericCellValue()+"");
            else if ((cell8.getStringCellValue() != null) && (!(cell8.getStringCellValue().equals(""))))
              pls.setAssetTag(cell8.getStringCellValue());
          }
          else
            pls.setAssetTag(null);
          String level;
          if ((cell9 != null) && (cell9.getStringCellValue() != null) && (!(cell9.getStringCellValue().equals("")))) {
            level = "";
            if (cell9.getCellType() == 0)
              level = df.format(cell9.getNumericCellValue());
            else {
              level = cell9.getStringCellValue();
            }
            if (level.equals("共建"))
              pls.setConstructionSharingEnumId(Integer.valueOf(1));
            else if (level.equals("共享"))
              pls.setConstructionSharingEnumId(Integer.valueOf(2));
            else if (level.equals("自建自用(不包括自建预留)"))
              pls.setConstructionSharingEnumId(Integer.valueOf(3));
            else if (level.equals("自建预留"))
              pls.setConstructionSharingEnumId(Integer.valueOf(4));
          }
          else {
            pls.setConstructionSharingEnumId(null);
          }

          if ((cell10 != null) && (cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals("")))) {
            if (cell10.getCellType() == 0)
              pls.setConstructionSharingOrg(cell10.getNumericCellValue()+"");
            else
              pls.setConstructionSharingOrg(cell10.getStringCellValue());
          }
          else {
            pls.setConstructionSharingOrg(null);
          }

          if ((cell11 != null) && (cell11.getStringCellValue() != null) && (!(cell11.getStringCellValue().equals("")))) {
            level = "";
            if (cell11.getCellType() == 0)
              level = df.format(cell11.getNumericCellValue());
            else {
              level = cell11.getStringCellValue();
            }
            if (level.equals("我方共享给他方"))
              pls.setSharingTypeEnumId(Integer.valueOf(1));
            else if (level.equals("他方共享给我方"))
              pls.setSharingTypeEnumId(Integer.valueOf(2));
          }
          else {
            pls.setSharingTypeEnumId(null);
          }

          if ((cell12 != null) && (cell12.getStringCellValue() != null) && (!(cell12.getStringCellValue().equals("")))) {
            if (cell12.getStringCellValue().equals("设计状态"))
              pls.setResourceLifePeriodEnumId(Integer.valueOf(1));
            else if (cell12.getStringCellValue().equals("工程建设期(入网带业务前)"))
              pls.setResourceLifePeriodEnumId(Integer.valueOf(2));
            else if (cell12.getStringCellValue().equals("工程可入期(已入网带业务)"))
              pls.setResourceLifePeriodEnumId(Integer.valueOf(3));
            else if (cell12.getStringCellValue().equals("工程初验后试运行"))
              pls.setResourceLifePeriodEnumId(Integer.valueOf(4));
            else if (cell12.getStringCellValue().equals("工程终验后在网"))
              pls.setResourceLifePeriodEnumId(Integer.valueOf(5));
            else if (cell12.getStringCellValue().equals("退网状态"))
              pls.setResourceLifePeriodEnumId(Integer.valueOf(6));
          }
          else {
            pls.setResourceLifePeriodEnumId(null);
          }

          if ((cell13 != null) && (cell13.getStringCellValue() != null) && (!(cell13.getStringCellValue().equals("")))) {
            pls.setLifecycleTime(sdf1.parse(cell13.getStringCellValue()));
          }
          else {
            pls.setLifecycleTime(null);
          }

          if ((cell14 != null) && (cell14.getStringCellValue() != null) && (!(cell14.getStringCellValue().equals(""))))
            pls.setNote(cell14.getStringCellValue());
          else {
            pls.setNote(null);
          }

          list.add(pls);
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertPoleLS((PolelineSegmentInfoBean)list.get(i));
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertPoleLS", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getpoleLineSegmentlist()
  {
    try
    {
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      String modelPath = "";
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_poleLSManage.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);

      if (this.ids != null) {
        this.polelinesegment = new PolelineSegmentInfoBean();
        this.polelinesegment.setIds(this.ids);
        List lists = this.dataservice.getpoleLineSegmentlist(this.polelinesegment);
        for (int j = 0; j < lists.size(); ++j) {
          PolelineSegmentInfoBean pls = (PolelineSegmentInfoBean)lists.get(j);
          if (pls != null) {
            HSSFRow row = sheet.createRow((short)j + 3);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);
            HSSFCell cell4 = row.createCell(4);
            HSSFCell cell5 = row.createCell(5);
            HSSFCell cell6 = row.createCell(6);
            HSSFCell cell7 = row.createCell(7);
            HSSFCell cell8 = row.createCell(8);
            HSSFCell cell9 = row.createCell(9);
            HSSFCell cell10 = row.createCell(10);
            HSSFCell cell11 = row.createCell(11);
            HSSFCell cell12 = row.createCell(12);
            HSSFCell cell13 = row.createCell(13);
            HSSFCell cell14 = row.createCell(14);

            cell1.setCellType(1);
            cell2.setCellType(1);
            cell3.setCellType(1);
            cell4.setCellType(1);
            cell5.setCellType(1);
            cell6.setCellType(1);
            cell7.setCellType(1);
            cell8.setCellType(1);
            cell9.setCellType(1);
            cell10.setCellType(1);
            cell11.setCellType(1);
            cell12.setCellType(1);
            cell13.setCellType(1);
            cell14.setCellType(1);

            if (pls.getPoleLineSegmentName() != null) {
              cell1.setCellValue(pls.getPoleLineSegmentName());
            }

            if (pls.getMaintenanceAreaName() != null) {
              cell2.setCellValue(pls.getMaintenanceAreaName());
            }

            if (pls.getPoleLineSegmentLength() != null) {
              cell3.setCellValue(pls.getPoleLineSegmentLength());
            }

            if (pls.getStatus() != null) {
              if (pls.getStatus().equals("1"))
                cell4.setCellValue("占用");
              else if (pls.getStatus().equals("2"))
                cell4.setCellValue("空闲");
              else if (pls.getStatus().equals("3"))
                cell4.setCellValue("预占");
              else if (pls.getStatus().equals("4"))
                cell4.setCellValue("暂拆");
              else if (pls.getStatus().equals("5"))
                cell4.setCellValue("损坏");
              else if (pls.getStatus().equals("6"))
                cell4.setCellValue("废弃");
              else if (pls.getStatus().equals("7"))
                cell4.setCellValue("外租");
              else if (pls.getStatus().equals("8")) {
                cell4.setCellValue("出租");
              }
            }

            if (pls.getStartDeviceName() != null) {
              cell5.setCellValue(pls.getStartDeviceName());
            }

            if (pls.getEndDeviceName() != null) {
              cell6.setCellValue(pls.getEndDeviceName());
            }

            if (pls.getPoleLineSegmentBieName() != null) {
              cell7.setCellValue(pls.getPoleLineSegmentBieName());
            }

            if (pls.getAssetTag() != null) {
              cell8.setCellValue(pls.getAssetTag());
            }

            if (pls.getConstructionSharingEnumId() != null) {
              if (String.valueOf(pls.getConstructionSharingEnumId()).equals("1"))
                cell9.setCellValue("共建");
              else if (String.valueOf(pls.getConstructionSharingEnumId()).equals("2"))
                cell9.setCellValue("共享");
              else if (String.valueOf(pls.getConstructionSharingEnumId()).equals("2"))
                cell9.setCellValue("自建自用(不包括自建预留)");
              else if (String.valueOf(pls.getConstructionSharingEnumId()).equals("2")) {
                cell9.setCellValue("自建预留");
              }

            }

            if (pls.getConstructionSharingOrg() != null) {
              cell10.setCellValue(pls.getConstructionSharingOrg());
            }

            if (pls.getSharingTypeEnumId() != null) {
              if (String.valueOf(pls.getSharingTypeEnumId()).equals("1"))
                cell11.setCellValue("我方共享给他方");
              else if (String.valueOf(pls.getSharingTypeEnumId()).equals("2")) {
                cell11.setCellValue("他方共享给我方");
              }
            }

            if (pls.getResourceLifePeriodEnumId() != null) {
              if (String.valueOf(pls.getResourceLifePeriodEnumId()).equals("1"))
                cell12.setCellValue("设计状态");
              else if (String.valueOf(pls.getResourceLifePeriodEnumId()).equals("2"))
                cell12.setCellValue("工程建设期(入网带业务前)");
              else if (String.valueOf(pls.getResourceLifePeriodEnumId()).equals("3"))
                cell12.setCellValue("工程可用期(已入网带业务)");
              else if (String.valueOf(pls.getResourceLifePeriodEnumId()).equals("4"))
                cell12.setCellValue("工程初验后试运行");
              else if (String.valueOf(pls.getResourceLifePeriodEnumId()).equals("5"))
                cell12.setCellValue("工程终验后在网");
              else if (String.valueOf(pls.getResourceLifePeriodEnumId()).equals("6")) {
                cell12.setCellValue("退网状态");
              }

            }

            if (pls.getLifecycleTime() != null)
            {
              cell13.setCellValue(sdf1.format(pls.getLifecycleTime()));
            }

            if (pls.getNote() != null) {
              cell14.setCellValue(pls.getNote());
            }
          }
        }
        String filename = dlTempPath + File.separator + "_" + "杆路段信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        String sourceFilePath = dlTempPath + File.separator;
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "杆路段信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误,请联系系统管理员!");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getpoleLineSegmentlist", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public String insertsuspensionseg()
  {
    try
    {
      if (this.upFile != null) {
        POIFSFileSystem fs = null;
        HSSFWorkbook wb = null;
        fs = new POIFSFileSystem(new FileInputStream(this.upFile));
        wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int rows = sheet.getLastRowNum();
        DecimalFormat df = new DecimalFormat("#");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        List list = new ArrayList();
        for (int i = 3; i <= rows; ++i) {
          SuspensionWireSegInfoBean sws = new SuspensionWireSegInfoBean();
          HSSFRow row = sheet.getRow(i);
          HSSFCell cell1 = row.getCell(1);
          HSSFCell cell2 = row.getCell(2);
          HSSFCell cell3 = row.getCell(3);
          HSSFCell cell4 = row.getCell(4);
          HSSFCell cell5 = row.getCell(5);
          HSSFCell cell6 = row.getCell(6);
          HSSFCell cell7 = row.getCell(7);
          HSSFCell cell8 = row.getCell(8);
          HSSFCell cell9 = row.getCell(9);
          HSSFCell cell10 = row.getCell(10);
          HSSFCell cell11 = row.getCell(11);
          HSSFCell cell12 = row.getCell(12);
          HSSFCell cell13 = row.getCell(13);
          HSSFCell cell14 = row.getCell(14);
          HSSFCell cell15 = row.getCell(15);
          HSSFCell cell16 = row.getCell(16);
          HSSFCell cell17 = row.getCell(17);
          HSSFCell cell18 = row.getCell(18);

          if ((cell1.getStringCellValue() != null) && (!(cell1.getStringCellValue().equals(""))))
          {
            sws.setSuspensionWireSegName(cell1.getStringCellValue());
          }
          else {
            this.message = "对不起，" + i + "行必填字段吊线段名称未填的，请填好后重新导入！";
            return "success";
          }

          if (cell2.getCellType() == 0) {
            sws.setSuspensionWireSegCode(cell2.getNumericCellValue()+"");
          }
          else if ((cell2 != null) && (cell2.getStringCellValue() != null) && (!(cell2.getStringCellValue().equals("")))) {
            sws.setSuspensionWireSegCode(cell2.getStringCellValue());
          } else {
            this.message = "对不起，" + i + "行必填字段吊线段编号未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell3.getStringCellValue() != null) && (!(cell3.getStringCellValue().equals("")))) {
            DomainBean domain = new DomainBean();
            if (cell3.getCellType() == 0)
              domain.setDomainName(df.format(cell3.getNumericCellValue()));
            else {
              domain.setDomainName(cell3.getStringCellValue());
            }
            domain = this.dataservice.getDomain(domain);
            if (domain != null) {
              sws.setRegionId(domain.getDomainId());
            }
            this.message = "对不起，" + i + "所属维护区域未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段所属维护区域未填的，请填好后重新导入！";
          if ((cell4.getStringCellValue() != null) && (!(cell4.getStringCellValue().equals("")))) {
            SuspensionWireInfoBean suspension = new SuspensionWireInfoBean();
            if (cell4.getCellType() == 0)
              suspension.setSuspensionWireName(df.format(cell4.getNumericCellValue()));
            else {
              suspension.setSuspensionWireName(cell4.getStringCellValue());
            }
            suspension = this.dataservice.getSuspension(suspension);
            if (suspension != null) {
              sws.setSuspensionWireId(suspension.getSuspensionWireId()); 
            }
            this.message = "对不起，" + i + "所属吊线名称未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段所属吊线名称未填的，请填好后重新导入！";
          PoleInfoBean pole;
          SupportInfoBean su;
          if ((cell5.getStringCellValue() != null) && (!(cell5.getStringCellValue().equals("")))) {
            pole = new PoleInfoBean();
            su = new SupportInfoBean();
            if (cell5.getCellType() == 0) {
              pole.setPoleNameSub(df.format(cell5.getNumericCellValue()));
              su.setSupportName(df.format(cell5.getNumericCellValue()));
            } else {
              pole.setPoleNameSub(cell5.getStringCellValue());
              su.setSupportName(cell5.getStringCellValue());
            }
            pole = this.dataservice.getPole(pole);
            su = this.dataservice.getSupport(su);

            if (pole != null) {
              sws.setStartId(pole.getPoleId()); }
            if (su != null) {
              sws.setStartId(su.getSupportId());
            }

            this.message = "对不起，" + i + "起始电杆或撑点名称未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段起始电杆或撑点名称未填的，请填好后重新导入！";
          if ((cell6.getStringCellValue() != null) && (!(cell6.getStringCellValue().equals("")))) {
            label963: pole = new PoleInfoBean();
            su = new SupportInfoBean();
            if (cell6.getCellType() == 0) {
              pole.setPoleNameSub(df.format(cell6.getNumericCellValue()));
              su.setSupportName(df.format(cell6.getNumericCellValue()));
            } else {
              pole.setPoleNameSub(cell6.getStringCellValue());
              su.setSupportName(cell6.getStringCellValue());
            }
            pole = this.dataservice.getPole(pole);
            su = this.dataservice.getSupport(su);
            if (pole != null) {
              sws.setEndId(pole.getPoleId());  }
            if (su != null) {
              sws.setEndId(su.getSupportId());
            }

            this.message = "对不起，" + i + "终止电杆或撑点名称未查到，请核对后重新导入！";
            return "success";
          }

          this.message = "对不起，" + i + "行必填字段终止电杆或撑点名称未填的，请填好后重新导入！";
         String level;
          if ((cell7 != null) && (cell7.getStringCellValue() != null) && (!(cell7.getStringCellValue().equals("")))) {
            level = "";
            if (cell7.getCellType() == 0)
              level = df.format(cell7.getNumericCellValue());
            else {
              level = cell7.getStringCellValue();
            }
            if (level.equals("电杆"))
              sws.setStartType("1");
            else if (level.equals("撑点"))
              sws.setStartType("2");
          }
          else {
            this.message = "对不起，" + i + "行必填字段起始端点类型未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell8 != null) && (cell8.getStringCellValue() != null) && (!(cell8.getStringCellValue().equals("")))) {
            level = "";
            if (cell8.getCellType() == 0)
              level = df.format(cell8.getNumericCellValue());
            else {
              level = cell8.getStringCellValue();
            }
            if (level.equals("电杆"))
              sws.setEndType("1");
            else if (level.equals("撑点"))
              sws.setEndType("2");
          }
          else {
            this.message = "对不起，" + i + "行必填字段终止端点类型未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell9 != null) && (cell9.getStringCellValue() != null) && (!(cell9.getStringCellValue().equals("")))) {
            level = "";
            if (cell9.getCellType() == 0)
              level = df.format(cell9.getNumericCellValue());
            else {
              level = cell9.getStringCellValue();
            }
            if (level.equals("钢绞线"))
              sws.setCaizhi("1");
            else if (level.equals("钢心铝绞线"))
              sws.setCaizhi("2");
            else if (level.equals("铁线"))
              sws.setCaizhi("3");
          }
          else {
            this.message = "对不起，" + i + "行必填字段材质未填的，请填好后重新导入！";
            return "success";
          }

          if ((cell10.getStringCellValue() != null) && (!(cell10.getStringCellValue().equals(""))))
          {
            sws.setGuige(cell10.getStringCellValue());
          }
          else {
            this.message = "对不起，" + i + "行必填字段规格未填的，请填好后重新导入！";
            return "success";
          }

          if (cell11.getCellType() == 0) {
            sws.setTiaoshu(cell11.getNumericCellValue()+"");
          }
          else if ((cell11 != null) && (cell11.getStringCellValue() != null) && (!(cell11.getStringCellValue().equals("")))) {
            sws.setTiaoshu(cell11.getStringCellValue());
          } else {
            this.message = "对不起，" + i + "行必填字段条数未填的，请填好后重新导入！";
            return "success";
          }

          if (cell12.getCellType() == 0) {
            sws.setLength(cell12.getNumericCellValue()+"");
          }
          else if ((cell12 != null) && (cell12.getStringCellValue() != null) && (!(cell12.getStringCellValue().equals(""))))
            sws.setLength(cell12.getStringCellValue());
          else {
            sws.setLength(null);
          }

          if ((cell13 != null) && (cell13.getStringCellValue() != null) && (!(cell13.getStringCellValue().equals("")))) {
            if (cell13.getStringCellValue().equals("设计状态"))
              sws.setResourceLifePeriodEnumId(Integer.valueOf(1));
            else if (cell13.getStringCellValue().equals("工程建设期(入网带业务前)"))
              sws.setResourceLifePeriodEnumId(Integer.valueOf(2));
            else if (cell13.getStringCellValue().equals("工程可入期(已入网带业务)"))
              sws.setResourceLifePeriodEnumId(Integer.valueOf(3));
            else if (cell13.getStringCellValue().equals("工程初验后试运行"))
              sws.setResourceLifePeriodEnumId(Integer.valueOf(4));
            else if (cell13.getStringCellValue().equals("工程终验后在网"))
              sws.setResourceLifePeriodEnumId(Integer.valueOf(5));
            else if (cell13.getStringCellValue().equals("退网状态"))
              sws.setResourceLifePeriodEnumId(Integer.valueOf(6));
          }
          else {
            sws.setResourceLifePeriodEnumId(null);
          }

          if ((cell14 != null) && (cell14.getStringCellValue() != null) && (!(cell14.getStringCellValue().equals("")))) {
            sws.setLifecycleTime(sdf1.parse(cell14.getStringCellValue()));
          }
          else {
            sws.setLifecycleTime(null);
          }

          if ((cell15 != null) && (cell15.getStringCellValue() != null) && (!(cell15.getStringCellValue().equals("")))) {
            if (cell15.getStringCellValue().equals("是"))
              sws.setIsAuthorizationManagement(Integer.valueOf(1));
            else if (cell15.getStringCellValue().equals("否"))
              sws.setIsAuthorizationManagement(Integer.valueOf(2));
          }
          else {
            sws.setIsAuthorizationManagement(null);
          }

          if ((cell16 != null) && (cell16.getStringCellValue() != null) && (!(cell16.getStringCellValue().equals(""))))
          {
            sws.setAuthorizationManagementUnit(cell16.getStringCellValue());
          }
          else {
            sws.setAuthorizationManagementUnit(null);
          }

          if ((cell17 != null) && (cell17.getStringCellValue() != null) && (!(cell17.getStringCellValue().equals(""))))
          {
            sws.setDesignPurposes(cell17.getStringCellValue());
          }
          else {
            sws.setDesignPurposes(null);
          }

          if ((cell18 != null) && (cell18.getStringCellValue() != null) && (!(cell18.getStringCellValue().equals(""))))
            sws.setNote(cell18.getStringCellValue());
          else {
            sws.setNote(null);
          }

          list.add(sws);
        }
        for (int i = 0; i < list.size(); ++i) {
          this.dataservice.insertsuspensionseg((SuspensionWireSegInfoBean)list.get(i));
        }
        this.success = true;
        return "success";
      }
      this.message = "对不起，请您选择一个文件！";
      return "success";
    }
    catch (Exception e) {
      log.error("DataAction.insertsuspensionseg", e);
      this.message = "发生了错误，请联系系统管理员！"; }
    return "success";
  }

  public String getsuspensionseglist()
  {
    try
    {
      String randomPath = "temp_" + CommonUtil.getUuid();
      String dlTempPath = ServletActionContext.getServletContext().getRealPath("/") + "downloadtemp" + File.separator + randomPath;
      File dirfile = new File(dlTempPath);
      dirfile.mkdirs();

      SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
      String modelPath = "";
      modelPath = getServletContext().getRealPath("/") + "tmpl" + File.separator + "excel_suspensionseg.xls";
      POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(modelPath));
      HSSFWorkbook wb = new HSSFWorkbook(fs);
      HSSFSheet sheet = wb.getSheetAt(0);

      if (this.ids != null) {
        this.suspensionwireseg = new SuspensionWireSegInfoBean();
        this.suspensionwireseg.setIds(this.ids);
        List lists = this.dataservice.getSuspensionWireSeglist(this.suspensionwireseg);
        for (int j = 0; j < lists.size(); ++j) {
          SuspensionWireSegInfoBean sws = (SuspensionWireSegInfoBean)lists.get(j);
          if (sws != null) {
            HSSFRow row = sheet.createRow((short)j + 3);
            HSSFCell cell1 = row.createCell(1);
            HSSFCell cell2 = row.createCell(2);
            HSSFCell cell3 = row.createCell(3);
            HSSFCell cell4 = row.createCell(4);
            HSSFCell cell5 = row.createCell(5);
            HSSFCell cell6 = row.createCell(6);
            HSSFCell cell7 = row.createCell(7);
            HSSFCell cell8 = row.createCell(8);
            HSSFCell cell9 = row.createCell(9);
            HSSFCell cell10 = row.createCell(10);
            HSSFCell cell11 = row.createCell(11);
            HSSFCell cell12 = row.createCell(12);
            HSSFCell cell13 = row.createCell(13);
            HSSFCell cell14 = row.createCell(14);
            HSSFCell cell15 = row.createCell(15);
            HSSFCell cell16 = row.createCell(16);
            HSSFCell cell17 = row.createCell(17);
            HSSFCell cell18 = row.createCell(18);

            cell1.setCellType(1);
            cell2.setCellType(1);
            cell3.setCellType(1);
            cell4.setCellType(1);
            cell5.setCellType(1);
            cell6.setCellType(1);
            cell7.setCellType(1);
            cell8.setCellType(1);
            cell9.setCellType(1);
            cell10.setCellType(1);
            cell11.setCellType(1);
            cell12.setCellType(1);
            cell13.setCellType(1);
            cell14.setCellType(1);
            cell15.setCellType(1);
            cell16.setCellType(1);
            cell17.setCellType(1);
            cell18.setCellType(1);

            if (sws.getSuspensionWireSegName() != null) {
              cell1.setCellValue(sws.getSuspensionWireSegName());
            }

            if (sws.getSuspensionWireSegCode() != null) {
              cell2.setCellValue(sws.getSuspensionWireSegCode());
            }

            if (sws.getRegion() != null) {
              cell3.setCellValue(sws.getRegion());
            }

            if (sws.getSuspensionWireName() != null) {
              cell4.setCellValue(sws.getSuspensionWireName());
            }

            if (sws.getStartName() != null) {
              cell5.setCellValue(sws.getStartName());
            }

            if (sws.getEndName() != null) {
              cell6.setCellValue(sws.getEndName());
            }

            if (sws.getStartType() != null) {
              if (sws.getStartType().equals("1"))
                cell7.setCellValue("电杆");
              else if (sws.getStartType().equals("2")) {
                cell7.setCellValue("撑点");
              }
            }

            if (sws.getEndType() != null) {
              if (sws.getEndType().equals("1"))
                cell8.setCellValue("电杆");
              else if (sws.getEndType().equals("2")) {
                cell8.setCellValue("撑点");
              }
            }

            if (sws.getCaizhi() != null) {
              if (sws.getCaizhi().equals("1"))
                cell9.setCellValue("钢绞线");
              else if (sws.getCaizhi().equals("2"))
                cell9.setCellValue("钢心铝绞线");
              else if (sws.getCaizhi().equals("3")) {
                cell9.setCellValue("铁线");
              }
            }

            if (sws.getGuige() != null) {
              cell10.setCellValue(sws.getGuige());
            }

            if (sws.getTiaoshu() != null) {
              cell11.setCellValue(sws.getTiaoshu());
            }

            if (sws.getLength() != null) {
              cell12.setCellValue(sws.getLength());
            }

            if (sws.getResourceLifePeriodEnumId() != null) {
              if (String.valueOf(sws.getResourceLifePeriodEnumId()).equals("1"))
                cell13.setCellValue("设计状态");
              else if (String.valueOf(sws.getResourceLifePeriodEnumId()).equals("2"))
                cell13.setCellValue("工程建设期(入网带业务前)");
              else if (String.valueOf(sws.getResourceLifePeriodEnumId()).equals("3"))
                cell13.setCellValue("工程可用期(已入网带业务)");
              else if (String.valueOf(sws.getResourceLifePeriodEnumId()).equals("4"))
                cell13.setCellValue("工程初验后试运行");
              else if (String.valueOf(sws.getResourceLifePeriodEnumId()).equals("5"))
                cell13.setCellValue("工程终验后在网");
              else if (String.valueOf(sws.getResourceLifePeriodEnumId()).equals("6")) {
                cell13.setCellValue("退网状态");
              }

            }

            if (sws.getLifecycleTime() != null)
            {
              cell14.setCellValue(sdf1.format(sws.getLifecycleTime()));
            }

            if (sws.getIsAuthorizationManagement() != null) {
              if (sws.getIsAuthorizationManagement().equals(Integer.valueOf(1)))
                cell15.setCellValue("是");
              else if (sws.getIsAuthorizationManagement().equals(Integer.valueOf(2))) {
                cell15.setCellValue("否");
              }
            }

            if (sws.getAuthorizationManagementUnit() != null) {
              cell16.setCellValue(sws.getAuthorizationManagementUnit());
            }

            if (sws.getDesignPurposes() != null) {
              cell17.setCellValue(sws.getDesignPurposes());
            }

            if (sws.getNote() != null) {
              cell18.setCellValue(sws.getNote());
            }
          }
        }
        String filename = dlTempPath + File.separator + "_" + "吊线段信息列表.xls";
        FileOutputStream fo = new FileOutputStream(filename);
        wb.write(fo);
        fo.close();
        String sourceFilePath = dlTempPath + File.separator;
        this.filepath = "downloadtemp" + File.separator + randomPath + File.separator + "_" + "吊线段信息列表.xls";
        return "exportsuccess";
      }
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误,请联系系统管理员!");
      return "error";
    }
    catch (Exception e) {
      log.error("DataAction.getsuspensionseglist", e);
      this.errorMessage = new ErrorMessage();
      this.errorMessage.setMessage("发生了错误，请联系系统管理员！"); }
    return "error";
  }

  public ErrorMessage getErrorMessage()
  {
    return this.errorMessage;
  }

  public void setErrorMessage(ErrorMessage errorMessage) {
    this.errorMessage = errorMessage;
  }

  public PipeInfoBean getPipe() {
    return this.pipe;
  }

  public void setPipe(PipeInfoBean pipe) {
    this.pipe = pipe;
  }

  public PipeSegmentInfoBean getPipeseg() {
    return this.pipeseg;
  }

  public void setPipeseg(PipeSegmentInfoBean pipeseg) {
    this.pipeseg = pipeseg;
  }

  public File getUpFile() {
    return this.upFile;
  }

  public void setUpFile(File upFile) {
    this.upFile = upFile;
  }

  public UserInfoBean getUserInfoBean() {
    return this.userInfoBean;
  }

  public void setUserInfoBean(UserInfoBean userInfoBean) {
    this.userInfoBean = userInfoBean;
  }

  public boolean isSuccess() {
    return this.success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public DataService getDataservice() {
    return this.dataservice;
  }

  public void setDataservice(DataService dataservice) {
    this.dataservice = dataservice;
  }

  public String getFilepath() {
    return this.filepath;
  }

  public void setFilepath(String filepath) {
    this.filepath = filepath;
  }

  public String getIds() {
    return this.ids;
  }

  public void setIds(String ids) {
    this.ids = ids;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getMessage() {
    return this.message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public PolelineInfoBean getPoleline() {
    return this.poleline;
  }

  public void setPoleline(PolelineInfoBean poleline) {
    this.poleline = poleline;
  }

  public TubeInfoBean getTube() {
    return this.tube;
  }

  public void setTube(TubeInfoBean tube) {
    this.tube = tube;
  }

  public PoleInfoBean getPole() {
    return this.pole;
  }

  public void setPole(PoleInfoBean pole) {
    this.pole = pole;
  }

  public WellInfoBean getWell() {
    return this.well;
  }

  public void setWell(WellInfoBean well) {
    this.well = well;
  }

  public LedupInfoBean getLedup() {
    return this.Ledup;
  }

  public void setLedup(LedupInfoBean ledup) {
    this.Ledup = ledup;
  }
}