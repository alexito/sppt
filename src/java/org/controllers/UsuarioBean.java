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
import org.database.Update;
import org.models.Usuario;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@Dependent
public class UsuarioBean {

  private List<Usuario> listaUsuarios;
  private Usuario usuario;

  public void saveUsuario() throws IOException, SQLException {
    Insert.InsertUsuario(usuario);
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext extContext = context.getExternalContext();
    extContext.redirect("usuarios.jspx");
  }

  public void onRowEdit(RowEditEvent event) throws SQLException {
    int a = 0;
    int b = 0;
    Usuario editedUsuario = (Usuario) event.getObject();
    Update.UpdateUsuario(editedUsuario);
    b = a;
//        OQs aux=((OQs) event.getObject());
//        Actualizar.Actualizar_costo(aux.getId_oq(),aux.getQuantity_To_Receive(),aux.getSupplier_Name(),aux.getUnit_Cost());
//        FacesMessage msg = new FacesMessage("OQ Edited", ((OQs) event.getObject()).getDescription2());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void onRowCancel(RowEditEvent event) {

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
