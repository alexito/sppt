package org.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.database.Insert;
import org.database.Select;
import org.models.Usuario;

@ManagedBean
@Dependent
public class UsuarioBean {
  private List<Usuario> listaUsuarios;
  private Usuario usuario;
  
  public void saveUsuario() throws IOException, SQLException{
    Insert.InsertUsuario(usuario);
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext extContext = context.getExternalContext();
    extContext.redirect("usuarios.jspx");
  }
  
  
  public Usuario getUsuario() {
    return usuario;
  }

  public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
  }

  public List<Usuario> getListaUsuarios() {
    return listaUsuarios;
  }

  public void setListaUsuarios(List<Usuario> listaUsuarios) {
    this.listaUsuarios = listaUsuarios;
  }
  
  public UsuarioBean() {
    usuario = new Usuario();
    listaUsuarios = Select.selectUsuarios();
  }
  
}
