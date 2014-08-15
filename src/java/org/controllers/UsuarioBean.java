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
import org.primefaces.event.*;

@ManagedBean
@Dependent
public class UsuarioBean {

  private List<Usuario> listaUsuarios;
  private List<Usuario> listaConductores;

  private Map<String, String> tipos_rol = new HashMap<String, String>();
  private Usuario usuario;
  private Usuario conductor;

  public List<Usuario> saveUsuario() throws IOException, SQLException {
    Insert.InsertUsuario(usuario); 
    usuario = new Usuario();   
    listaUsuarios = Select.selectUsuarios("usuarios");
    return listaUsuarios;
  }
  
  public List<Usuario> saveConductor() throws IOException, SQLException {
    Insert.InsertUsuario(conductor); 
    conductor = new Usuario();
    conductor.setRol("conductor");
    listaConductores = Select.selectUsuarios("conductores");
    return listaConductores;
  }

  public void onRowEdit(RowEditEvent event) throws SQLException {
    Usuario editedUsuario = (Usuario) event.getObject();
    Update.UpdateUsuario(editedUsuario);
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
  
  public Usuario getConductor() {
    return conductor;
  }

  public void setConductor(Usuario conductor) {
    this.conductor = conductor;
  }

  public List<Usuario> getListaUsuarios() {
    return listaUsuarios;
  }

  public void setListaUsuarios(List<Usuario> listaUsuarios) {
    this.listaUsuarios = listaUsuarios;
  }
  
  public List<Usuario> getListaConductores() {
    return listaConductores;
  }

  public void setListaConductores(List<Usuario> listaConductores) {
    this.listaConductores = listaConductores;
  }

  public UsuarioBean() {
    usuario = new Usuario();   
    listaUsuarios = Select.selectUsuarios("usuarios");
    
    conductor = new Usuario();
    conductor.setRol("conductor");
    listaConductores = Select.selectUsuarios("conductores");
    
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
