package org.controllers;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import org.database.Select;
import org.database.Update;
import org.models.Localidad;
import org.models.Solicitud;
import org.models.Usuario;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@Dependent
public class SolicitudBean {

  private List<Solicitud> listaSolicitudes;
  private Map<String, Integer> listaLocalidades;
  private Map<String, Integer> listaUsuarios;
  private int id_localidad;

  public Map<String, Integer> getListaUsuarios() {
    return listaUsuarios;
  }

  public void setListaUsuarios(Map<String, Integer> listaUsuarios) {
    this.listaUsuarios = listaUsuarios;
  }
  
  public Map<String, Integer> getListaLocalidades() {
    return listaLocalidades;
  }

  private Solicitud solicitud;

  public Solicitud getSolicitud() {
    return solicitud;
  }

  public void setSolicitud(Solicitud solicitud) {
    this.solicitud = solicitud;
  }

  public List<Solicitud> getListaSolicitudes() {
    return listaSolicitudes;
  }

  public void setListaSolicitudes(List<Solicitud> listaSolicitudes) {
    this.listaSolicitudes = listaSolicitudes;
  }

  public SolicitudBean() {
    solicitud = new Solicitud();
    listaSolicitudes = Select.selectSolicitudes();
    Map<Integer, Localidad> locs = Select.selectMappedLocalidades(true, null);
    Map<Integer, Usuario> usus = Select.selectMappedUsuarios(true, null);
    listaLocalidades = mapLocalidad(locs);
    listaUsuarios = mapUsuario(usus);
  }

  public void onRowEdit(RowEditEvent event) throws SQLException {
    Usuario editedUsuario = (Usuario) event.getObject();
    Update.UpdateUsuario(editedUsuario);
//        FacesMessage msg = new FacesMessage("OQ Edited", ((OQs) event.getObject()).getDescription2());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
  }

  public void onRowCancel(RowEditEvent event) {

  }

  public Map<String, Integer> mapLocalidad(Map<Integer, Localidad> objs) {
    Map<String, Integer> res = new HashMap<String, Integer>();
    Iterator iterator = objs.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry mapEntry = (Map.Entry) iterator.next();
      Localidad obj = (Localidad) mapEntry.getValue();
      res.put(obj.getNombre(), obj.getId());
    }
    return res;
  }
  
  public Map<String, Integer> mapUsuario(Map<Integer, Usuario> objs) {
    Map<String, Integer> res = new HashMap<String, Integer>();
    Iterator iterator = objs.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry mapEntry = (Map.Entry) iterator.next();
      Usuario obj = (Usuario) mapEntry.getValue();
      res.put(obj.getApellido() + " " + obj.getNombre(), obj.getId());
    }
    return res;
  }
}
