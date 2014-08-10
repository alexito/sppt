package org.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
  private Map<String, String> tipos_rol = new HashMap<String, String>();
  private Usuario usuario;

  public void saveUsuario() throws IOException, SQLException {
    Insert.InsertUsuario(usuario); 
    FacesContext context = FacesContext.getCurrentInstance();
    ExternalContext extContext = context.getExternalContext();
    extContext.redirect("usuarios.jspx");
  }

  public void onRowEdit(RowEditEvent event) throws SQLException {
    Usuario editedUsuario = (Usuario) event.getObject();
    Update.UpdateUsuario(editedUsuario);
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
    tipos_rol.put("Administrador", "super");
    tipos_rol.put("Usuario", "admin");
  }
  
  public Map<String, String> getTipos_rol() {
        return tipos_rol;
    }

    public void setTipos_rol(Map<String, String> tipo_rol) {
        this.tipos_rol = tipo_rol;
    }

}
