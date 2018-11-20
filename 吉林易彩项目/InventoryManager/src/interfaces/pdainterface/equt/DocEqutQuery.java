package interfaces.pdainterface.equt;

import base.exceptions.DataAccessException;
import interfaces.pdainterface.equt.service.PDAEqutInfoService;
import interfaces.pdainterface.interfaceUtil.InterfaceUtil;
import interfaces.pdainterface.interfaceUtil.XMLHeader;
import interfaces.pdainterface.login.service.PDALoginService;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import manage.document.pojo.DocEqutInfoBean;
import manage.user.pojo.UserInfoBean;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class DocEqutQuery extends HttpServlet
{
  private static final Logger log = Logger.getLogger(DocEqutQuery.class);
  private PDALoginService loginService;
  private PDAEqutInfoService pdaEqutInfoService;
  private static final long serialVersionUID = 1L;
  private UserInfoBean user;
  public static final String MODULENAME = "DOCEQUTQUERY";
  public static final int DEFAULTTYPE = 2;
  private Integer domainid;
  private XMLHeader header = new XMLHeader();

  public void destroy()
  {
    super.destroy();
  }

  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    try
    {
      execDocEqutQuery(request, response);
    } catch (Exception e) {
      log.error("EqutQuery.doPost", e);
    }
  }

  public void init(ServletConfig config)
    throws ServletException
  {
    super.init(config);
    try {
      ServletContext servletContext = getServletContext();
      WebApplicationContext wac = null;
      wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
      setLoginService((PDALoginService)wac.getBean("loginService"));
      setPdaEqutInfoService((PDAEqutInfoService)wac.getBean("pdaEqutInfoService"));
    } catch (Exception e) {
      log.error("EqutQuery.init", e);
    }
  }

  private DocEqutInfoBean AnalyticDocEqutqueryInfo(String xmlStr)
    throws Exception
  {
    DocEqutInfoBean docEqut = new DocEqutInfoBean();
    try {
      Document doc = DocumentHelper.parseText(xmlStr);
      Element element = doc.getRootElement();

      Iterator iter = element.elementIterator("BODY");

      while (iter.hasNext()) {
        Element recordEle = (Element)iter.next();
        this.header.setSerialno(recordEle.elementTextTrim("SERIALNO"));
        this.header.setVersion(recordEle.elementTextTrim("VERSION"));
        this.header.setUser(recordEle.elementTextTrim("USER"));
        this.header.setPassword(recordEle.elementTextTrim("PASSWORD"));

        Iterator iters = recordEle.elementIterator("EqutQueryRequest");
        while (iters.hasNext()) {
          Element itemEle = (Element)iters.next();
          docEqut.setEname(itemEle.elementTextTrim("ENAME"));
          docEqut.setEcode(itemEle.elementTextTrim("ECODE"));
          docEqut.setEaddr(itemEle.elementTextTrim("EADDR"));
        }

      }

      if ((this.header.getUser() == null) || (this.header.getPassword() == null))
        this.header.setErrDes("请求报文错误");
    }
    catch (Exception e)
    {
      this.header.setErrDes("读取XML文档错误");
    }
    return docEqut;
  }

  public void execDocEqutQuery(HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    String xmlStr = request.getParameter("xmlstr");
    String serialNoString = "";
    String errdescsString = "";
    String equtInfoSrString = "";
    String resultString = "";
    String areano = "";
    UserInfoBean tmp = new UserInfoBean();
    Date eventDate = new Date();
    DocEqutInfoBean docEqut = new DocEqutInfoBean();
    try {
      docEqut = AnalyticDocEqutqueryInfo(xmlStr);
      if (this.header.getErrDes() == null)
      {
        serialNoString = this.header.getSerialno();
        this.user = new UserInfoBean();
        this.user.setUsername(this.header.getUser());
        this.user.setPassword(this.header.getPassword());
        tmp = this.loginService.login(this.user);
        if (tmp != null) {
          areano = tmp.getAreano();
          docEqut.setAreano(areano);
          List<DocEqutInfoBean> list = this.pdaEqutInfoService.getDocEqutList(docEqut);

          equtInfoSrString = "<LIST>";
          for (DocEqutInfoBean d : list) {
            equtInfoSrString = equtInfoSrString + "<EqutInfo><DID>" + 
              d.getDid() + "</DID>" + 
              "<ECODE>" + d.getEcode() + "</ECODE>" + 
              "<ENAME>" + d.getEname() + "</ENAME>" + 
              "<FILENAME>" + d.getFilename() + "</FILENAME>" + 
              "<EADDR>" + d.getEaddr() + "</EADDR></EqutInfo>";
          }
          equtInfoSrString = equtInfoSrString + "</LIST>";

          resultString = "0";
          errdescsString = "查询成功";
        }

        resultString = "1";
        errdescsString = "登录失败";
      }

      resultString = "1";
      errdescsString = this.header.getErrDes();
    }
    catch (DataAccessException e) {
      log.error("DocEqutQuery.execDocEqutQuery", e);
      resultString = "1";
      errdescsString = "应用错误";
    } catch (Exception e) {
      log.error("DocEqutQuery.execDocEqutQuery", e);
      resultString = "1";
      errdescsString = "应用错误";
    }

    label439: InterfaceUtil.packageReturn(response, serialNoString, resultString, errdescsString, equtInfoSrString);
  }

  public void setLoginService(PDALoginService loginService)
  {
    this.loginService = loginService;
  }

  public PDALoginService getLoginService() {
    return this.loginService;
  }

  public Integer getDomainid()
  {
    return this.domainid; }

  public void setDomainid(Integer domainid) {
    this.domainid = domainid; }

  public PDAEqutInfoService getPdaEqutInfoService() {
    return this.pdaEqutInfoService; }

  public void setPdaEqutInfoService(PDAEqutInfoService pdaEqutInfoService) {
    this.pdaEqutInfoService = pdaEqutInfoService;
  }
}