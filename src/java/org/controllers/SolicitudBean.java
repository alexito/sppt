package org.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import org.database.Insert;
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
  private List<Solicitud> listaSolicitudesAprobadas;
  private List<Solicitud> listaSolicitudesPendientes;
  private List<Solicitud> listaSolicitudesXAprobar;
  private Map<String, Integer> listaLocalidades;
  private Map<String, Integer> listaUsuarios;
  private Map<String, Integer> listaConductores;
  private Solicitud solicitud;
  private int loc_origen;
  private Usuario usuario;

  public int getLoc_origen() {
    return loc_origen;
  }

  public void setLoc_origen(int loc_origen) {
    this.loc_origen = loc_origen;
  }

  public Map<String, Integer> getListaConductores() {
    return listaConductores;
  }

  public void setListaConductores(Map<String, Integer> listaConductores) {
    this.listaConductores = listaConductores;
  }

  public Map<String, Integer> getListaUsuarios() {
    return listaUsuarios;
  }

  public void setListaUsuarios(Map<String, Integer> listaUsuarios) {
    this.listaUsuarios = listaUsuarios;
  }

  public Map<String, Integer> getListaLocalidades() {
    return listaLocalidades;
  }

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
  
  public List<Solicitud> getListaSolicitudesXAprobar() {
    return listaSolicitudesXAprobar;
  }

  public void setListaSolicitudesXAprobar(List<Solicitud> listaSolicitudesXAprobar) {
    this.listaSolicitudesXAprobar = listaSolicitudesXAprobar;
  }
  
  public List<Solicitud> getListaSolicitudesAprobadas() {
    return listaSolicitudesAprobadas;
  }

  public void setListaSolicitudesAprobadas(List<Solicitud> listaSolicitudesAprobadas) {
    this.listaSolicitudesAprobadas = listaSolicitudesAprobadas;
  }

  public List<Solicitud> getListaSolicitudesPendientes() {
    return listaSolicitudesPendientes;
  }

  public void setListaSolicitudesPendientes(List<Solicitud> listaSolicitudesPendientes) {
    this.listaSolicitudesPendientes = listaSolicitudesPendientes;
  }

  public SolicitudBean() throws IOException {
    usuario = Select.LoggedUser();
    updateInfoSolicitudes();
    Map<Integer, Localidad> locs = Select.selectMappedLocalidades(true, null);
    Map<Integer, Usuario> usus = Select.selectMappedUsuarios(true, false, null);
    Map<Integer, Usuario> cond = Select.selectMappedUsuarios(true, true, null);

    solicitud = new Solicitud();    

    listaLocalidades = mapLocalidad(locs);
    listaUsuarios = mapUsuario(usus);
    listaConductores = mapUsuario(cond);
  }

  public List<Solicitud> saveSolicitud() throws IOException, SQLException, ParseException {
    Usuario logged_user = Select.LoggedUser();
    solicitud.setUsuarioByIdUsuarioSolicita(logged_user);
    int h = solicitud.getFSalida().getHours();
    int m = solicitud.getFSalida().getMinutes();
    if ((h > 15 && m > 29) || h >= 16) {
      solicitud.setEstado(false);
    } else {
      solicitud.setEstado(true);
    }
    solicitud.setFCreacion(new Date());
    Insert.InsertSolicitud(solicitud);

    solicitud = new Solicitud();
    updateInfoSolicitudes();

    return listaSolicitudesAprobadas;
  }

  public List<Solicitud> onRowEdit(RowEditEvent event) throws SQLException, ParseException, IOException {
    Solicitud editedSolicitud = (Solicitud) event.getObject();
    
    int h = editedSolicitud.getFSalida().getHours();
    int m = editedSolicitud.getFSalida().getMinutes();
    
    //Check if the current solicitud is updated by an Aprobador
    if(!editedSolicitud.getListaAprobador()){
      if ((h > 15 && m > 29) || h >= 16) {
        editedSolicitud.setEstado(false);
      } else {
        editedSolicitud.setEstado(true);
      }
    }
    Update.UpdateSolicitud(editedSolicitud);
    solicitud = new Solicitud();
    updateInfoSolicitudes();
    return listaSolicitudes;
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

  private void updateInfoSolicitudes() {
    if ("admin".equals(usuario.getRol())) {
      listaSolicitudesAprobadas = Select.selectSolicitudes(1, usuario.getId(), false);
      listaSolicitudesPendientes = Select.selectSolicitudes(0, usuario.getId(), false);
      listaSolicitudesXAprobar = Select.selectSolicitudesXAprobar(usuario);      
    } 
    else if ("super".equals(usuario.getRol())) {
      listaSolicitudes = Select.selectSolicitudes(1, 0, true);
      listaSolicitudes = Select.selectSolicitudes(0, 0, true);
    }
  }
  
  
  public void filterConductor() throws ParseException{
    Date fs = solicitud.getFSalida();
    Date fl = solicitud.getFLlegada();
    if(fs != null && fl != null){
      Map<Integer, Usuario> cond = Select.selectMappedConductoresByDate(fs, fl);
      listaConductores = mapUsuario(cond);
    }
  }

  public void refreshAll() {
  }

}
