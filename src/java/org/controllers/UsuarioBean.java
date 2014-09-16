package org.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
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
  private List<Usuario> listaEnfermeros;

  private Map<String, String> tipos_rol = new HashMap<String, String>();
  private Usuario usuario;
  private Usuario conductor;
  private Usuario enfermero;

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
  
  public List<Usuario> saveEnfermero() throws IOException, SQLException {
    Insert.InsertUsuario(enfermero); 
    enfermero = new Usuario();
    enfermero.setRol("enfermero");
    listaEnfermeros = Select.selectUsuarios("enfermeros");
    return listaEnfermeros;
  }

  public void onRowEdit(RowEditEvent event) throws SQLException {
    Usuario editedUsuario = (Usuario) event.getObject();
    Update.UpdateUsuario(editedUsuario);
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
  
  public Usuario getEnfermero() {
    return enfermero;
  }

  public void setEnfermero(Usuario enfermero) {
    this.enfermero = enfermero;
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
  
  public List<Usuario> getListaEnfermeros() {
    return listaEnfermeros;
  }

  public void setListaEnfermeros(List<Usuario> listaEnfermeros) {
    this.listaEnfermeros = listaEnfermeros;
  }

  public UsuarioBean() {
    usuario = new Usuario();
    usuario.setEsInterno(true);
    listaUsuarios = Select.selectUsuarios("usuarios");
    
    conductor = new Usuario();
    conductor.setRol("conductor");
    listaConductores = Select.selectUsuarios("conductores");
    
    enfermero = new Usuario();
    enfermero.setRol("enfermero");
    listaEnfermeros = Select.selectUsuarios("enfermeros");
    
    tipos_rol.put("Superadmin", "super");
    tipos_rol.put("Administrador", "admin");
    
    
  }
  
  public Map<String, String> getTipos_rol() {
    return tipos_rol;
  }

  public void setTipos_rol(Map<String, String> tipo_rol) {
    this.tipos_rol = tipo_rol;
  }

}
