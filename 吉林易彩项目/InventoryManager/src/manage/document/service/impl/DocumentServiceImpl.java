package manage.document.service.impl;

import base.database.DataBase;
import base.exceptions.DataAccessException;
import base.util.CommonUtil;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import manage.document.pojo.DocEqutInfoBean;
import manage.document.pojo.DocJumpInfoBean;
import manage.document.pojo.DocPointInfoBean;
import manage.document.service.DocumentService;
import manage.equt.pojo.EqutModelInfoBean;
import manage.route.pojo.CableInfoBean;
import manage.route.pojo.RouteInfoBean;
import manage.user.pojo.UserInfoBean;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class DocumentServiceImpl extends DataBase
  implements DocumentService
{
  private static final String INSERT_DOC_EQUT = "document.insertDocEqut";
  private static final String INSERT_DOC_POINT_LIST = "document.insertDocPoint";
  private static final String INSERT_DOC_ROUTE_LIST = "document.insertDocRoute";
  private static final String INSERT_DOC_CABLE_LIST = "document.insertDocCable";
  private static final String GAT_DOC_EQUT_GRID = "document.gatDocEqutGrid";
  private static final String GAT_DOC_EQUT_COUNT = "document.gatDocEqutCount";
  private static final String DELETE_DOCEQUTINFO = "document.delDocequtinfo";
  private static final String DELETE_DOCEQUTINFOJUMP = "document.delDocequtinfojump";
  private static final String GAT_DOC_POINT_GRID = "document.gatDocPointGrid";
  private static final String GAT_DOC_POINT_COUNT = "document.gatDocPointCount";
  private static final String INSERT_DOC_WORK = "document.insertDocWork";
  private static final String INSERT_DOC_EQUTCON = "document.insertDocEqutcon";
  private static final String GET_HSR_COUNT = "document.getHsrCount";
  private static final String GET_HSC_COUNT = "document.getHscCount";
  private static final String GetEqutEmodel = "document.getEqutModel";
  private static final String INSERT_DOC_Jump_LIST = "document.insertDocJump";

  public DocEqutInfoBean loadDocumentEqutGrid(DocEqutInfoBean searchdequt)
    throws DataAccessException
  {
    DocEqutInfoBean docEqut = new DocEqutInfoBean();
    List list = getObjects("document.gatDocEqutGrid", searchdequt);
    int total = getCount("document.gatDocEqutCount", searchdequt);
    docEqut.setDocEquts(list);
    docEqut.setTotal(Integer.valueOf(total));
    return docEqut;
  }

  public int inportDocumentInfo(List<DocEqutInfoBean> docEquts)
    throws Exception
  {
    int n = 0;
    POIFSFileSystem fs = null;
    HSSFWorkbook wb = null;
    for (int i = 0; i < docEquts.size(); ++i) {
      DocEqutInfoBean docEqut = (DocEqutInfoBean)docEquts.get(i);
      fs = new POIFSFileSystem(new FileInputStream(docEqut.getExcle()));
      wb = new HSSFWorkbook(fs);
      DocEqutInfoBean equt = importEqut(docEqut, wb);
      if (equt != null) {
        n += importPoint(equt, wb);
      }
    }
    return n;
  }

  private DocEqutInfoBean importEqut(DocEqutInfoBean docEqut, HSSFWorkbook wb)
    throws Exception
  {
    int groupCount = 16;
    Date eventTime = new Date();
    HSSFSheet sheet = wb.getSheetAt(0);
    int rows = sheet.getLastRowNum();
    DecimalFormat df = new DecimalFormat("#");

    HSSFRow row = sheet.getRow(1);
    HSSFCell cell = row.getCell(1);
    String ename = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        ename = df.format(cell.getNumericCellValue());
      else {
        ename = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(2);
    cell = row.getCell(1);
    String ecode = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        ecode = df.format(cell.getNumericCellValue());
      else {
        ecode = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(3);
    cell = row.getCell(1);
    String etype = "";
    if (cell != null) {
      if (cell.getCellType() == 0) {
        etype = df.format(cell.getNumericCellValue());
      } else {
        etype = cell.getStringCellValue();
        etype = CommonUtil.getEtypeInt(etype);
      }

    }

    row = sheet.getRow(4);
    cell = row.getCell(1);
    String emodel = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        emodel = df.format(cell.getNumericCellValue());
      else {
        emodel = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(5);
    cell = row.getCell(1);
    String estat = "";
    if (cell != null) {
      if (cell.getCellType() == 0) {
        estat = df.format(cell.getNumericCellValue());
      }
      else {
        estat = cell.getStringCellValue();
        estat = CommonUtil.getEststInt(estat);
      }

    }

    row = sheet.getRow(6);
    cell = row.getCell(1);
    String station = "";
    if (cell != null) {
      if (cell.getCellType() == 0) {
        station = df.format(cell.getNumericCellValue());
      }
      else {
        station = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(7);
    cell = row.getCell(1);
    String eaddr = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        eaddr = df.format(cell.getNumericCellValue());
      else {
        eaddr = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(8);
    cell = row.getCell(1);
    String lon = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        lon = df.format(cell.getNumericCellValue());
      else {
        lon = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(9);
    cell = row.getCell(1);
    String lat = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        lat = df.format(cell.getNumericCellValue());
      else {
        lat = cell.getStringCellValue();
      }
    }

    row = sheet.getRow(10);
    cell = row.getCell(1);
    String note = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        note = df.format(cell.getNumericCellValue());
      else {
        note = cell.getStringCellValue();
      }

    }

    sheet = wb.getSheetAt(2);
    String nomap = "";
    String tempstrString = "";

    row = sheet.getRow(1);
    int rowno = 0;
    for (int i = 0; i < groupCount; ++i)
    {
      tempstrString = "";
      rowno = groupCount * i + 1;
      row = sheet.getRow(rowno);
      cell = row.getCell(0);
      if (cell != null) {
        if (cell.getCellType() == 0)
          tempstrString = df.format(cell.getNumericCellValue());
        else {
          tempstrString = cell.getStringCellValue();
        }
      }

      if (tempstrString != "") {
        tempstrString = tempstrString + "%0000";
        tempstrString = tempstrString.substring(0, 5);
      } else {
        tempstrString = "%0000";
      }
      nomap = nomap + tempstrString;
    }

    for (int i = 0; i < groupCount; ++i) {
      for (int j = 0; j < groupCount; ++j) {
        tempstrString = "";
        rowno = groupCount * i + j + 1;
        row = sheet.getRow(rowno);

        cell = row.getCell(1);

        if (cell != null) {
          if (cell.getCellType() == 0)
            tempstrString = df.format(cell.getNumericCellValue());
          else {
            tempstrString = cell.getStringCellValue();
          }
        }

        if (tempstrString != "") {
          tempstrString = tempstrString + "%0000";
          tempstrString = tempstrString.substring(0, 5);
        } else {
          tempstrString = "%0000";
        }
        nomap = nomap + tempstrString;
      }
    }
    docEqut.setDid(CommonUtil.getUuid());
    docEqut.setEname(ename);
    docEqut.setEcode(ecode);
    docEqut.setEtype(etype);
    docEqut.setEmodel(emodel);
    docEqut.setEstat(estat);
    docEqut.setStation(station);
    docEqut.setEaddr(eaddr);
    docEqut.setLon(lon);
    docEqut.setLat(lat);
    docEqut.setNote(note);
    docEqut.setNomap(nomap);
    docEqut.setMtime(eventTime);

    insert("document.insertDocEqut", docEqut);
    return docEqut; }

  public void insertDocEuqt(DocEqutInfoBean docs) throws Exception {
    insert("document.insertDocEqut", docs);
  }

  private int importPoint(DocEqutInfoBean docEqut, HSSFWorkbook wb)
    throws Exception
  {
    int rows = 0;
    HSSFSheet sheet = wb.getSheetAt(1);
    rows = sheet.getLastRowNum();
    DecimalFormat df = new DecimalFormat("#");
    Date eventTime = new Date();

    DocPointInfoBean docPoint = new DocPointInfoBean();
    List<RouteInfoBean> routeList = new ArrayList();
    List<CableInfoBean> cableList = new ArrayList();
    List pointList = new ArrayList();

    for (int i = 1; i <= rows; ++i) {
      HSSFRow row = sheet.getRow(i);
      HSSFCell cell = row.getCell(0);
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

      String pid = null;
      if (cell == null) break;
      if (cell.getCellType() == 0)
        pid = df.format(cell.getNumericCellValue());
      else {
        pid = cell.getStringCellValue();
      }
      if (pid == null) break; if (pid.equals("")) {
        break;
      }
      pid = pid.trim();
      pid = "0" + pid;
      pid = pid.substring(pid.length() - 6, pid.length());

      String pboardno = null;
      if (cell1 != null) {
        if (cell1.getCellType() == 0)
          pboardno = df.format(cell1.getNumericCellValue());
        else {
          pboardno = cell1.getStringCellValue();
        }

      }

      String plineno = null;
      if (cell2 != null) {
        if (cell2.getCellType() == 0)
          plineno = df.format(cell2.getNumericCellValue());
        else {
          plineno = cell2.getStringCellValue();
        }

      }

      String prowno = null;
      if (cell3 != null) {
        if (cell3.getCellType() == 0)
          prowno = df.format(cell3.getNumericCellValue());
        else {
          prowno = cell3.getStringCellValue();
        }
      }

      String pcode = null;
      if (cell4 != null) {
        if (cell4.getCellType() == 0)
          pcode = df.format(cell4.getNumericCellValue());
        else {
          pcode = cell4.getStringCellValue();
        }
      }

      String pstat = null;
      if (cell5 != null) {
        if (cell5.getCellType() == 0) {
          pstat = df.format(cell5.getNumericCellValue());
        } else {
          pstat = cell5.getStringCellValue();
          pstat = CommonUtil.getPstatInt(pstat);
        }
      }
      else {
        pstat = "1";
      }

      String direction = null;
      if (cell6 != null) {
        if (cell6.getCellType() == 0) {
          direction = df.format(cell6.getNumericCellValue());
        } else {
          direction = cell6.getStringCellValue();
          direction = CommonUtil.getDirectionInt(direction);
        }
      }
      else {
        direction = "1";
      }

      String ptype = null;
      if (cell7 != null)
        if (cell7.getCellType() == 0) {
          ptype = df.format(cell7.getNumericCellValue());
        } else {
          ptype = cell7.getStringCellValue();
          ptype = CommonUtil.getPtypeInt(ptype);
        }
      else {
        ptype = "1";
      }

      String servno = null;
      if (cell8 != null) {
        if (cell8.getCellType() == 0)
          servno = df.format(cell8.getNumericCellValue());
        else {
          servno = cell8.getStringCellValue();
        }

      }

      String servtype = null;
      if (cell9 != null) {
        if (cell9.getCellType() == 0)
          servtype = df.format(cell9.getNumericCellValue());
        else {
          servtype = cell9.getStringCellValue();
        }

      }

      String pserv = null;
      if (cell10 != null) {
        if (cell10.getCellType() == 0)
          pserv = df.format(cell10.getNumericCellValue());
        else {
          pserv = cell10.getStringCellValue();
        }

      }

      String fiberstationname = null;
      if (cell11 != null) {
        if (cell11.getCellType() == 0)
          fiberstationname = df.format(cell11.getNumericCellValue());
        else {
          fiberstationname = cell11.getStringCellValue();
        }

      }

      String fibname = null;
      if (cell12 != null) {
        if (cell12.getCellType() == 0)
          fibname = df.format(cell12.getNumericCellValue());
        else {
          fibname = cell12.getStringCellValue();
        }

      }

      String cablename = null;
      if (cell13 != null) {
        if (cell13.getCellType() == 0)
          cablename = df.format(cell13.getNumericCellValue());
        else {
          cablename = cell13.getStringCellValue();
        }

      }

      String routename = null;
      if (cell14 != null) {
        if (cell14.getCellType() == 0)
          routename = df.format(cell14.getNumericCellValue());
        else {
          routename = cell14.getStringCellValue();
        }

      }

      String ofpcode = null;
      if (cell15 != null) {
        if (cell15.getCellType() == 0)
          ofpcode = df.format(cell15.getNumericCellValue());
        else {
          ofpcode = cell15.getStringCellValue();
        }

      }

      String ofpname = null;
      if (cell16 != null) {
        if (cell16.getCellType() == 0)
          ofpname = df.format(cell16.getNumericCellValue());
        else {
          ofpname = cell16.getStringCellValue();
        }
      }

      String oppo_ename = "";
      if (cell17 != null) {
        if (cell17.getCellType() == 0)
          oppo_ename = df.format(cell17.getNumericCellValue());
        else {
          oppo_ename = cell17.getStringCellValue();
        }
      }

      String oppo_ecode = "";
      if (cell18 != null) {
        if (cell18.getCellType() == 0)
          oppo_ecode = df.format(cell18.getNumericCellValue());
        else {
          oppo_ecode = cell18.getStringCellValue();
        }
      }

      String oppo_pcode = null;
      if (cell19 != null) {
        if (cell19.getCellType() == 0)
          oppo_pcode = df.format(cell19.getNumericCellValue());
        else {
          oppo_pcode = cell19.getStringCellValue();
        }
      }

      String note = null;
      if (cell20 != null) {
        if (cell20.getCellType() == 0)
          note = df.format(cell20.getNumericCellValue());
        else {
          note = cell20.getStringCellValue();
        }

      }

      docPoint = new DocPointInfoBean();
      docPoint.setDid(docEqut.getDid());
      docPoint.setAreano(docEqut.getAreano());
      docPoint.setPid(pid);
      docPoint.setPboardno(pboardno);
      docPoint.setPlineno(plineno);
      docPoint.setProwno(prowno);
      docPoint.setPcode(pcode);
      docPoint.setPstat(pstat);
      docPoint.setDirection(direction);
      docPoint.setPtype(ptype);
      docPoint.setServno(servno);
      docPoint.setServtype(servtype);
      docPoint.setPserv(pserv);
      docPoint.setFiberstationname(fiberstationname);
      docPoint.setFibname(fibname);
      docPoint.setRoutename(routename);
      docPoint.setCablename(cablename);
      docPoint.setOfpcode(ofpcode);
      docPoint.setOfpname(ofpname);
      docPoint.setOppo_ename(oppo_ename);
      docPoint.setOppo_ecode(oppo_ecode);
      docPoint.setOppo_pcode(oppo_pcode);
      docPoint.setNote(note);
      docPoint.setMtime(eventTime);
      docPoint.setMp(docEqut.getMp());

      pointList.add(docPoint);

      boolean insertRoute = false;
      boolean insertCable = false;
      if ((routename == null) || (cablename == null) || (routename.equals("")) || 
        (cablename.equals("")))
        continue;
      if (routeList.size() < 1)
      {
        RouteInfoBean hsr = new RouteInfoBean();
        hsr.setRoutename(routename);
        int hn = getCount("document.getHsrCount", hsr);
        if (hn == 0)
          insertRoute = true;
        else
          insertRoute = false;
      }
      else {
        for (RouteInfoBean route : routeList) {
          insertRoute = true;
          if (route.getRoutename().equals(routename)) {
            insertRoute = false;
            break;
          }

          RouteInfoBean hsr2 = new RouteInfoBean();
          hsr2.setRoutename(routename);
          int hn = getCount("document.getHsrCount", hsr2);
          if (hn == 0)
            insertRoute = true;
          else {
            insertRoute = false;
          }
        }
      }

      if (insertRoute) {
        RouteInfoBean r = new RouteInfoBean();
        r.setRoutename(routename);
        r.setDid(docEqut.getDid());
        r.setAreano(docEqut.getAreano());
        routeList.add(r);
      }
      int hn2;
      if (cableList.size() < 1)
      {
        CableInfoBean hsc = new CableInfoBean();
        hsc.setCablename(cablename);
        hsc.setRoutename(routename);
        hn2 = getCount("document.getHscCount", hsc);
        if (hn2 == 0)
          insertCable = true;
        else
          insertCable = false;
      }
      else {
        for (CableInfoBean cable : cableList) {
          insertCable = true;
          if ((cable.getCablename().equals(cablename)) && (cable.getRoutename().equals(routename))) {
            insertCable = false;
            break;
          }
          CableInfoBean hsc = new CableInfoBean();
          hsc.setCablename(cablename);
          hsc.setRoutename(routename);
          hn2 = getCount("document.getHscCount", hsc);
          if (hn2 == 0)
            insertCable = true;
          else {
            insertCable = false;
          }
        }
      }

      if (insertCable) {
        CableInfoBean c = new CableInfoBean();
        c.setCablename(cablename);
        c.setRoutename(routename);
        c.setDid(docEqut.getDid());
        c.setAreano(docEqut.getAreano());
        cableList.add(c);
      }
    }

    batchInsert("document.insertDocPoint", pointList);
    batchInsert("document.insertDocRoute", routeList);
    batchInsert("document.insertDocCable", cableList);
    return 1; }

  public void insertDocPoint(List<DocPointInfoBean> docs) throws Exception {
    batchInsert("document.insertDocPoint", docs);
  }

  public int deleteDocEqutInfo(List<String> ids)
    throws DataAccessException
  {
    int n = 0;
    if ((ids != null) && (!(ids.isEmpty()))) {
      for (int i = 0; i < ids.size(); ++i) {
        DocEqutInfoBean e = new DocEqutInfoBean();
        e.setDid((String)ids.get(i));
        int x = delete("document.delDocequtinfo", e);
        n += x;
      }
    }
    return n; }

  public int deleteDocEqutInfoJump(List<String> ids) throws DataAccessException {
    int n = 0;
    if ((ids != null) && (!(ids.isEmpty()))) {
      for (int i = 0; i < ids.size(); ++i) {
        DocEqutInfoBean e = new DocEqutInfoBean();
        e.setDid((String)ids.get(i));
        int x = delete("document.delDocequtinfojump", e);
        n += x;
      }
    }
    return n;
  }

  public DocPointInfoBean loadDocPointGrid(DocPointInfoBean searchdpoint) throws DataAccessException
  {
    DocPointInfoBean point = new DocPointInfoBean();
    List list = getObjects("document.gatDocPointGrid", searchdpoint);
    int total = getCount("document.gatDocPointCount", searchdpoint);
    point.setDocPoints(list);
    point.setTotal(Integer.valueOf(total));
    return point;
  }

  public int inportDocumentInfoWorkerorder(List<DocEqutInfoBean> docEquts, String[] mnstr, UserInfoBean userInfoBean) throws Exception
  {
    int n = 0;
    POIFSFileSystem fs = null;
    HSSFWorkbook wb = null;
    for (int i = 0; i < docEquts.size(); ++i) {
      DocEqutInfoBean docEqut = (DocEqutInfoBean)docEquts.get(i);
      fs = new POIFSFileSystem(new FileInputStream(docEqut.getExcle()));
      wb = new HSSFWorkbook(fs);
      DocEqutInfoBean equt = importWorkerorder(docEqut, wb, mnstr[i], userInfoBean);
      if (equt != null) {
        n += importPoint(equt, wb);
      }
    }
    return n;
  }

  private DocEqutInfoBean importWorkerorder(DocEqutInfoBean docEqut, HSSFWorkbook wb, String mn, UserInfoBean userInfoBean) throws Exception
  {
    int groupCount = 16;
    Date eventTime = new Date();
    HSSFSheet sheet = wb.getSheetAt(0);
    int rows = sheet.getLastRowNum();
    DecimalFormat df = new DecimalFormat("#");

    HSSFRow row = sheet.getRow(1);
    HSSFCell cell = row.getCell(1);
    String ename = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        ename = df.format(cell.getNumericCellValue());
      else {
        ename = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(2);
    cell = row.getCell(1);
    String ecode = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        ecode = df.format(cell.getNumericCellValue());
      else {
        ecode = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(3);
    cell = row.getCell(1);
    String etype = "";
    if (cell != null) {
      if (cell.getCellType() == 0) {
        etype = df.format(cell.getNumericCellValue());
      } else {
        etype = cell.getStringCellValue();
        etype = CommonUtil.getEtypeInt(etype);
      }

    }

    row = sheet.getRow(4);
    cell = row.getCell(1);
    String emodel = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        emodel = df.format(cell.getNumericCellValue());
      else {
        emodel = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(5);
    cell = row.getCell(1);
    String estat = "";
    if (cell != null) {
      if (cell.getCellType() == 0) {
        estat = df.format(cell.getNumericCellValue());
      }
      else {
        estat = cell.getStringCellValue();
        estat = CommonUtil.getEststInt(estat);
      }

    }

    row = sheet.getRow(6);
    cell = row.getCell(1);
    String station = "";
    if (cell != null) {
      if (cell.getCellType() == 0) {
        station = df.format(cell.getNumericCellValue());
      }
      else {
        station = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(7);
    cell = row.getCell(1);
    String eaddr = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        eaddr = df.format(cell.getNumericCellValue());
      else {
        eaddr = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(8);
    cell = row.getCell(1);
    String lon = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        lon = df.format(cell.getNumericCellValue());
      else {
        lon = cell.getStringCellValue();
      }

    }

    row = sheet.getRow(9);
    cell = row.getCell(1);
    String lat = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        lat = df.format(cell.getNumericCellValue());
      else {
        lat = cell.getStringCellValue();
      }
    }

    row = sheet.getRow(10);
    cell = row.getCell(1);
    String note = "";
    if (cell != null) {
      if (cell.getCellType() == 0)
        note = df.format(cell.getNumericCellValue());
      else {
        note = cell.getStringCellValue();
      }

    }

    sheet = wb.getSheetAt(2);
    String nomap = "";
    String tempstrString = "";

    row = sheet.getRow(1);
    int rowno = 0;
    for (int i = 0; i < groupCount; ++i)
    {
      tempstrString = "";
      rowno = groupCount * i + 1;
      row = sheet.getRow(rowno);
      cell = row.getCell(0);
      if (cell != null) {
        if (cell.getCellType() == 0)
          tempstrString = df.format(cell.getNumericCellValue());
        else {
          tempstrString = cell.getStringCellValue();
        }
      }

      if (tempstrString != "") {
        tempstrString = tempstrString + "%0000";
        tempstrString = tempstrString.substring(0, 5);
      } else {
        tempstrString = "%0000";
      }
      nomap = nomap + tempstrString;
    }

    for (int i = 0; i < groupCount; ++i) {
      for (int j = 0; j < groupCount; ++j) {
        tempstrString = "";
        rowno = groupCount * i + j + 1;
        row = sheet.getRow(rowno);

        cell = row.getCell(1);

        if (cell != null) {
          if (cell.getCellType() == 0)
            tempstrString = df.format(cell.getNumericCellValue());
          else {
            tempstrString = cell.getStringCellValue();
          }
        }

        if (tempstrString != "") {
          tempstrString = tempstrString + "%0000";
          tempstrString = tempstrString.substring(0, 5);
        } else {
          tempstrString = "%0000";
        }
        nomap = nomap + tempstrString;
      }
    }
    docEqut.setDid(CommonUtil.getUuid());
    docEqut.setEname(ename);
    docEqut.setEcode(ecode);
    docEqut.setEtype(etype);
    docEqut.setEmodel(emodel);
    docEqut.setEstat(estat);
    docEqut.setStation(station);
    docEqut.setEaddr(eaddr);
    docEqut.setLon(lon);
    docEqut.setLat(lat);
    docEqut.setNote(note);
    docEqut.setNomap(nomap);
    docEqut.setMtime(eventTime);

    insert("document.insertDocEqut", docEqut);

    String uuid = CommonUtil.getUuid();
    EqutModelInfoBean em = new EqutModelInfoBean();
    em.setModelName(mn);
    em = (EqutModelInfoBean)getObject("document.getEqutModel", em);
    return docEqut;
  }

  public int importDocumentEqutInfo(List<DocEqutInfoBean> docs) throws Exception {
    batchInsert("document.insertDocEqut", docs);
    return 1; }

  public int importDocumentPointInfo(List<DocPointInfoBean> docs) throws Exception {
    batchInsert("document.insertDocPoint", docs);
    return 1; }

  public int importDocumentCableInfo(List<CableInfoBean> docs) throws Exception {
    batchInsert("document.insertDocCable", docs);
    return 1; }

  public int importDocumentRouteInfo(List<RouteInfoBean> docs) throws Exception {
    batchInsert("document.insertDocRoute", docs);
    return 1; }

  public int importDocumentJumpInfo(List<DocJumpInfoBean> docs) throws Exception {
    batchInsert("document.insertDocJump", docs);
    return 1;
  }
}
