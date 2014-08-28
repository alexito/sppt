package org.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import org.database.Insert;
import org.database.Select;
import org.database.Update;
import org.models.Distancia;
import org.models.Localidad;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

@ManagedBean
@ViewScoped
public class LocalidadBean {

  private Localidad localidadSeleccionada;
  private List<Localidad> listaLocalidades;
  private List<Localidad> listaEditarDistancia;
  private Localidad localidad;

  public List<Localidad> getListaLocalidades() {
    return listaLocalidades;
  }

  public void setListaLocalidades(List<Localidad> listaLocalidades) {
    this.listaLocalidades = listaLocalidades;
  }

  public Localidad getLocalidadSeleccionada() {
    return localidadSeleccionada;
  }

  public void setLocalidadSeleccionada(Localidad localidadSeleccionada) {
    this.localidadSeleccionada = localidadSeleccionada;
  }
  
  public List<Localidad> getListaEditarDistancia() {
    return listaEditarDistancia;
  }

  public void setListaEditarDistancia(List<Localidad> listaEditarDistancia) {
    this.listaEditarDistancia = listaEditarDistancia;
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
    listaLocalidades = Select.selectLocalidades(0);
    return listaLocalidades;
  }
  
  public void onRowEdit(RowEditEvent event) throws SQLException, ParseException {
    Localidad editedLocalidad = (Localidad) event.getObject();
    Update.UpdateLocalidad(editedLocalidad);
  }

  public void onRowCancel(RowEditEvent event) {

  }
  
  public void onCellEdit(CellEditEvent event) throws SQLException, ParseException {
    int a, b;
    Localidad loc_ori = this.getLocalidadSeleccionada();
    Localidad loc_des = listaEditarDistancia.get(event.getRowIndex());    
    Distancia dist = new Distancia(loc_ori, loc_des, loc_des.getDistancia());
    Insert.InsertDistancia(dist);    
  }
  
  public void onRowSelect(SelectEvent event) {
    Localidad loc = (Localidad) event.getObject();
    listaEditarDistancia = Select.selectLocalidades(loc.getId());
  }
 
  public LocalidadBean() {
    localidad = new Localidad();  
    localidadSeleccionada = new Localidad();
    listaLocalidades = Select.selectLocalidades(0);
    
  }
  
}
