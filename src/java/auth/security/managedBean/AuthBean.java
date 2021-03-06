package auth.security.managedBean;

import org.database.Select;
import org.models.Usuario;
import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@ManagedBean
@RequestScoped
public class AuthBean {

  public final static String USER_KEY = "auth_user";
  private String user;
  private String password;
  private String message;

  public AuthBean() {
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public void doLogin(ActionEvent e) throws IOException {
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext extContext = context.getExternalContext();
    String url = "";
    try {
      Usuario usuario = Select.Loggin(user, password);
      if ("super".equals(usuario.getRol())) {
        url = extContext.encodeActionURL(
                context.getApplication().getViewHandler().getActionURL(context, "/views/super/index.xhtml"));
            extContext.getSessionMap().put(USER_KEY, usuario);
        extContext.redirect(url);
        return;
      }
      else if ("admin".equals(usuario.getRol())) {
        url = extContext.encodeActionURL(
                context.getApplication().getViewHandler().getActionURL(context, "/views/admin/index.xhtml"));
            extContext.getSessionMap().put(USER_KEY, usuario);
        extContext.redirect(url);
        return;
      }
      else if ("enfermero".equals(usuario.getRol())) {
        url = extContext.encodeActionURL(
                context.getApplication().getViewHandler().getActionURL(context, "/views/enfermeria/index.xhtml"));
            extContext.getSessionMap().put(USER_KEY, usuario);
        extContext.redirect(url);
        return;
      }
    } catch (IOException ex) {}
    message = "Usuario y/o clave invalida";

  }

  private boolean isAdmin(String user, String password) {
    return user.equals("admin") && password.equals("admin");
    //Aqui se puede validar hacia una base de datos
  }

  private boolean isSuperAdmin(String user, String password) {
    return user.equals("super") && password.equals("super");
    //Aqui se puede validar hacia una base de datos
  }

  public String getLoggedUserName() {
    return ((Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_KEY)).toString();
  }

  public void logout() throws IOException {
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext extContext = context.getExternalContext();
    extContext.getSessionMap().remove(USER_KEY);
    String url = extContext.encodeActionURL(
            context.getApplication().getViewHandler().getActionURL(context, "/index.jspx"));
    extContext.redirect(url);
    context = null;
    extContext.getSessionMap().clear();
  }
}
