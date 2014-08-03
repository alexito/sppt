package auth.security;

import org.models.Usuario;
import auth.security.managedBean.AuthBean;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthSuperAdminFilter implements Filter {

  private FilterConfig configuration;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    this.configuration = filterConfig;
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    if (((HttpServletRequest) request).getSession().getAttribute(
            AuthBean.USER_KEY) == null || !((Usuario) ((HttpServletRequest) request).getSession().getAttribute(
                    AuthBean.USER_KEY)).getRol().equals("admin")) {
      //((HttpServletResponse) response).sendRedirect("../forbidden.jspx");
      ((HttpServletResponse) response).sendRedirect("../index.jspx");
    } else {
      chain.doFilter(request, response);
    }
  }

  @Override
  public void destroy() {
    this.configuration = null;
  }

}
