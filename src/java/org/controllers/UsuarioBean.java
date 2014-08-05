package org.controllers;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.database.Select;
import org.models.Usuario;

@Named(value = "usuarioBean")
@Dependent
public class UsuarioBean {
  private List<Usuario> listaUsuarios;

  public List<Usuario> getListaUsuarios() {
    return listaUsuarios;
  }

  public void setListaUsuarios(List<Usuario> listaUsuarios) {
    this.listaUsuarios = listaUsuarios;
  }
  
  public UsuarioBean() {
    listaUsuarios = Select.selectUsuarios();
  }
  
}
