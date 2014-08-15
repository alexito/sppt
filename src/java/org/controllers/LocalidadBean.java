package org.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.database.Insert;
import org.database.Select;
import org.database.Update;
import org.models.Localidad;
import org.models.Usuario;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@Dependent
public class LocalidadBean {

  private List<Localidad> listaLocalidades;
  private Localidad localidad;

  public List<Localidad> getListaLocalidades() {
    return listaLocalidades;
  }

  public void setListaLocalidades(List<Localidad> listaLocalidades) {
    this.listaLocalidades = listaLocalidades;
  }

  public Localidad getLocalidad() {
    return localidad;
  }

  public void setLocalidad(Localidad localidad) {
    this.localidad = localidad;
  }
  
    
  public List<Localidad> saveLocalidad() throws IOException, SQLException, ParseException {
    Insert.InsertLocalidad(localidad); 
    localidad = new Localidad();
    listaLocalidades = Select.selectLocalidades();
    return listaLocalidades;
  }
  
  public void onRowEdit(RowEditEvent event) throws SQLException, ParseException {
    Localidad editedLocalidad = (Localidad) event.getObject();
    Update.UpdateLocalidad(editedLocalidad);
  }

  public void onRowCancel(RowEditEvent event) {

  }

 
  public LocalidadBean() {
    localidad = new Localidad();   
    listaLocalidades = Select.selectLocalidades();
    
  }
  
}
