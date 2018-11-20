package base.filters;

import base.session.SessionContext;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InterfaceFilter extends HttpServlet
  implements Filter
{
  private static final long serialVersionUID = 1L;

  public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
    throws IOException, ServletException
  {
    HttpServletRequest request = (HttpServletRequest)arg0;
    HttpServletResponse response = (HttpServletResponse)arg1;
    String url = request.getRequestURI();
   if ((!(url.contains("login.interface"))) && (!(url.contains("getAuthCode.interface"))) && (!(url.contains("loginError.interface"))) && (!(url.contains("pdaFile")))) {
      SessionContext sc = SessionContext.getInstance();
      String uid = request.getParameter("UID");
      if ((uid != null) && (!(uid.equals("")))) {
        HttpSession session = sc.getSession(uid);
        if (session == null) {
          response.sendRedirect(request.getContextPath() + "/pdaLogin!loginError.interface");
          return;
        }
      } else {
        response.sendRedirect(request.getContextPath() + "/pdaLogin!loginError.interface");
        return;
      }
    }

    arg2.doFilter(arg0, arg1);
  }

  public void init(FilterConfig arg0)
    throws ServletException
  {
  }
}