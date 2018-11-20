package base.interceptor;

import base.session.SessionContext;
import base.util.CommonUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.interceptor.Interceptor;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import manage.event.pojo.EventInfoBean;
import manage.event.service.EventService;
import manage.user.pojo.UserInfoBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.json.JSONResult;

public class TurnPageInterceptor
  implements Interceptor
{
  private static final long serialVersionUID = -1704916984064873265L;
  private static Log log = LogFactory.getLog(TurnPageInterceptor.class);
  private EventService eventService;

  public void destroy()
  {
  }

  public void init()
  {
  }

  public String intercept(ActionInvocation invo)
    throws Exception
  {
    beforeInvoke(invo);
    String strResult = invo.invoke();
    afterInvoke(invo);

    return strResult;
  }

  public void beforeInvoke(ActionInvocation invo)
  {
    HttpServletRequest request = (HttpServletRequest)invo.getInvocationContext().get(
      "com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    String url = request.getRequestURI();
    String param = "";

    StringBuffer params = new StringBuffer();
    Enumeration paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String name = (String)paramNames.nextElement();
      Object obj = request.getParameter(name);
      String value = "";
      if (obj instanceof String)
        value = (String)obj;
      else {
        value = obj.toString();
      }

      if (value != null)
      {
        value = value.replaceAll("\"", "%22");

        value = value.replaceAll("&", "%26");

        value = value.replaceAll("\\?", "%3F");

        value = value.replaceAll("=", "%3D");

        value = value.replaceAll("'", "%27");
      }

      params.append(name + "=" + value + "&");
    }
    param = params.toString();
    if (param.endsWith("&")) {
      param = param.substring(0, param.length() - 1);
    }

    ActionContext.getContext().getParameters().put("url", url);
    ActionContext.getContext().getParameters().put("param", param);
  }

  public void afterInvoke(ActionInvocation invo) throws Exception
  {
    Result actionResult = invo.getResult();
    Object action = invo.getAction();

    List attrList = new ArrayList();

    if (actionResult instanceof JSONResult) {
      JSONResult jsonResult = (JSONResult)actionResult;
      if (jsonResult.getIncludePropertiesList() != null) {
        for (Pattern pattern : jsonResult.getIncludePropertiesList()) {
          String attr = pattern.pattern();
          attrList.add(attr);
        }
      }
      if (jsonResult.getRoot() != null) {
        attrList.add(jsonResult.getRoot());
      }
    }

    String json = CommonUtil.getAttributesToJSON(action, attrList);

    HttpServletRequest request = (HttpServletRequest)invo.getInvocationContext().get(
      "com.opensymphony.xwork2.dispatcher.HttpServletRequest");

    String url = request.getRequestURI();
    String[] MM = url.split("!");
    String module = MM[0].substring(MM[0].lastIndexOf("/") + 1);
    String method = MM[1].substring(0, MM[1].indexOf(46));

    boolean fromInterface = false;
    String UID = "";
    if (url.contains(".interface")) {
      fromInterface = true;
    }

    String ip = request.getRemoteAddr();

    String requestParams = "";
    Enumeration paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String paramName = (String)paramNames.nextElement();

      String[] paramValues = request.getParameterValues(paramName);
      if (paramValues.length == 1) {
        String paramValue = paramValues[0];
        if (paramValue.length() != 0) {
          requestParams = requestParams + paramName + " = " + paramValue + "\n";
          if ((fromInterface) && (paramName.equals("UID"))) {
            UID = paramValue;
          }
        }
      }
    }

    UserInfoBean user = new UserInfoBean();
    boolean hasLogedIn = false;
    String type = "";
    if ((fromInterface) && (!(UID.equals("")))) {
      type = "1";
      SessionContext sc = SessionContext.getInstance();
      HttpSession session = sc.getSession(UID);
      if (session != null) {
        user = (UserInfoBean)sc.getSession(UID).getAttribute("userBean");
        hasLogedIn = true;
      }
    }
    if (!(fromInterface)) {
      type = "2";
      user = (UserInfoBean)request.getSession().getAttribute("userBean");
      if (user != null) {
        hasLogedIn = true;
      }
    }
    EventInfoBean event = new EventInfoBean();
    event.setSeriesNo("111");
    event.setEventDate(new Date());
    event.setIp(ip);
    event.setModule(module);
    event.setMethod(method);
    event.setRequestStr(requestParams);

    event.setResponseStr(json);
    event.setType(type);
    this.eventService.saveEvent(event);

  }

  public EventService getEventService() {
    return this.eventService;
  }

  public void setEventService(EventService eventService) {
    this.eventService = eventService;
  }
}