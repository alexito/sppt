package org.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.database.Insert;
import org.database.Select;
import org.database.Update;
import org.models.Emergencia;
import org.models.Localidad;
import org.models.Solicitud;
import org.models.Usuario;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@RequestScoped
public class SolicitudBean {
  
  private List<Solicitud> listaSolicitudes;
  private List<Solicitud> listaSolicitudesAprobadas;
  private List<Solicitud> listaSolicitudesPendientes;
  private List<Solicitud> listaSolicitudesCanceladas;
  private List<Solicitud> listaSolicitudesEmergencias;
  private List<Solicitud> listaSolicitudesXAprobar;
  private List<Solicitud> listaSolicitudesEnfermeriaXAprobar;
  private List<Solicitud> listaSolicitudesEnfermeriaAprobadas;
  private Map<String, Integer> listaLocalidades;
  private Map<String, Integer> listaUsuarios;
  private Map<String, Integer> listaConductores;
  private List<Usuario> listaInternos;
  private List<Usuario> listaExternos;  
  private Solicitud solicitud;
  private Solicitud solicitudEmergencia;
  private int loc_origen;
  private Usuario usuario;
  private Date currentDate = new Date();  
  private Map<String, Integer> listaEmergencia;

    
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

  public Solicitud getSolicitudEmergencia() {
    return solicitudEmergencia;
  }

  public void setSolicitudEmergencia(Solicitud solicitudEmergencia) {
    this.solicitudEmergencia = solicitudEmergencia;
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
  
  public List<Solicitud> getListaSolicitudesEnfermeriaXAprobar() {
    return listaSolicitudesEnfermeriaXAprobar;
  }

  public void setListaSolicitudesEnfermeriaXAprobar(List<Solicitud> listaSolicitudesEnfermeriaXAprobar) {
    this.listaSolicitudesEnfermeriaXAprobar = listaSolicitudesEnfermeriaXAprobar;
  }
  
  public List<Solicitud> getListaSolicitudesEnfermeriaAprobadas() {
    return listaSolicitudesEnfermeriaXAprobar;
  }

  public void setListaSolicitudesEnfermeriaAprobadas(List<Solicitud> listaSolicitudesEnfermeriaAprobadas) {
    this.listaSolicitudesEnfermeriaAprobadas = listaSolicitudesEnfermeriaAprobadas;
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
  
  public List<Solicitud> getListaSolicitudesCanceladas() {
    return listaSolicitudesCanceladas;
  }

  public void setListaSolicitudesCanceladas(List<Solicitud> listaSolicitudesCanceladas) {
    this.listaSolicitudesCanceladas = listaSolicitudesCanceladas;
  }
  
  public List<Solicitud> getListaSolicitudesEmergencias() {
    return listaSolicitudesEmergencias;
  }

  public void setListaSolicitudesEmergencias(List<Solicitud> listaSolicitudesEmergencias) {
    this.listaSolicitudesEmergencias = listaSolicitudesEmergencias;
  }
  
  public Map<String, Integer> getListaEmergencia() {
    return listaEmergencia;
  }

  public void setListaEmergencia(Map<String, Integer> listaEmergencia) {
    this.listaEmergencia = listaEmergencia;
  }

  public SolicitudBean() throws IOException {
    usuario = Select.LoggedUser();
    updateInfoSolicitudes();
    Map<Integer, Localidad> locs = Select.selectMappedLocalidades(true, null);
    Map<Integer, Usuario> usus = Select.selectMappedUsuarios(true, false, null);
    Map<Integer, Usuario> cond = Select.selectMappedUsuarios(true, true, null);
    
    Map<Integer, Emergencia> emer = Select.selectMappedEmergencia();
    listaEmergencia = mapEmergencias(emer);
    
    solicitud = new Solicitud();    
    solicitudEmergencia = new Solicitud();    
    listaInternos = Select.selectMappedUsuariosExtInt(1);
    listaExternos = Select.selectMappedUsuariosExtInt(0);    
    listaLocalidades = mapLocalidad(locs);
    listaUsuarios = mapUsuario(usus);
    listaConductores = mapUsuario(cond);    
  }
  
  public List<Usuario> completeUsuarioInterno(String query){
    
    List<Usuario> usuariosFiltrados = new ArrayList<Usuario>();
    for (Usuario user : listaInternos) {
      if (user.getApellido().toLowerCase().startsWith(query) || user.getNombre().toLowerCase().startsWith(query)
              || (user.getApellido() + " " + user.getNombre()).toLowerCase().startsWith(query)) {
        usuariosFiltrados.add(user);
        if(usuariosFiltrados.size() == 10){
          break;
        }
      }
    }
    return usuariosFiltrados;
  }
  
  public List<Usuario> completeUsuarioExterno(String query){
    
    List<Usuario> usuariosFiltrados = new ArrayList<Usuario>();
    for (Usuario user : listaExternos) {
      if (user.getNombre().toLowerCase().startsWith(query)) {
        usuariosFiltrados.add(user);
        if(usuariosFiltrados.size() == 10){
          break;
        }
      }
    }    
    return usuariosFiltrados;
  }
  
  private String pickUsuarioInternoIds(List<Usuario> lista){
    String uids = "" + usuario.getId();
    for (Usuario user : lista) {
        if(!"".equals(uids))
          uids += ",";
        uids += user.getId();
    }
    return uids;
  }
  
  private String pickUsuarioExternoIds(List<Usuario> lista, boolean is_insert, Solicitud solic) throws SQLException{
    String uids = "";
    for (Usuario user : lista) {
        if(!"".equals(uids))
          uids += ",";
        uids += user.getId();
    }
    if(is_insert){
      String[] nombres;
      nombres = solic.getNuevoUsuarioExterno().split(",");
      String nombre;
      for(int i = 0; i < nombres.length; i++){
        if(!"".equals(uids))
            uids += ",";      
        nombre = nombres[i].trim().toUpperCase();
        uids += Insert.InsertNewUsuarioExterno(nombre);
      }
    }
    return uids;
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
    solicitud.setCancelado(false);
    solicitud.setIds_interno(pickUsuarioInternoIds(solicitud.getListaInternosSeleccionados()));
    solicitud.setIds_externo(pickUsuarioExternoIds(solicitud.getListaExternosSeleccionados(), true, solicitud));
    
    Insert.InsertSolicitud(solicitud);

    solicitud = new Solicitud();
    updateInfoSolicitudes();

    return listaSolicitudesAprobadas;
  }
  
  public List<Solicitud> saveSolicitudEmergencia() throws IOException, SQLException, ParseException {
    Usuario logged_user = Select.LoggedUser();       
    solicitudEmergencia.setUsuarioByIdUsuarioSolicita(logged_user);
    int h = solicitudEmergencia.getFSalida().getHours();
    int m = solicitudEmergencia.getFSalida().getMinutes();
    solicitudEmergencia.setEstado(true);
    solicitudEmergencia.setFCreacion(new Date());
    solicitudEmergencia.setCancelado(false);
    solicitudEmergencia.setIds_interno(pickUsuarioInternoIds(solicitudEmergencia.getListaInternosSeleccionados()));
    solicitudEmergencia.setIds_externo(pickUsuarioExternoIds(solicitudEmergencia.getListaExternosSeleccionados(), true, solicitudEmergencia));
    
    Insert.InsertSolicitudEmergencia(solicitudEmergencia);

    solicitudEmergencia = new Solicitud();
    solicitudEmergencia.setEmergencia(true);
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
    
    editedSolicitud.setIds_interno(pickUsuarioInternoIds(editedSolicitud.getListaInternosSeleccionados()));
    editedSolicitud.setIds_externo(pickUsuarioExternoIds(editedSolicitud.getListaExternosSeleccionados(), false, editedSolicitud));
    
    if("enfermero".equals(usuario.getRol())){
      if(editedSolicitud.getEstadoEnfermeria() && editedSolicitud.getUsuarioByIdUsuarioConductor() != null && editedSolicitud.getUsuarioByIdUsuarioConductor().getFDisponible() != null){
          editedSolicitud.setUsuarioByIdUsuarioEnfermero(usuario);
      }else{
        return listaSolicitudes;
      }
    }
    if(editedSolicitud.getEs_creador() || editedSolicitud.getListaAprobador()){
      if(editedSolicitud.getEmergencia()){
        Update.UpdateSolicitudEmergenciaOwner(editedSolicitud);      
      }
      else{
        Update.UpdateSolicitudOwner(editedSolicitud);      
      }
    }
    else{
      if(editedSolicitud.getEmergencia()){
        Update.UpdateSolicitudEmergencia(editedSolicitud);      
      }else{
        Update.UpdateSolicitud(editedSolicitud);      
      }
    }
    
    solicitud = new Solicitud();
    solicitudEmergencia = new Solicitud();
    solicitudEmergencia.setEmergencia(true);
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
      listaSolicitudesAprobadas = Select.selectMisSolicitudesAprobadas(usuario.getId());
      listaSolicitudesPendientes = Select.selectMisSolicitudesPendientes(usuario.getId());
      listaSolicitudesCanceladas = Select.selectMisSolicitudesCanceladas(usuario.getId());
      listaSolicitudesEmergencias = Select.selectMisSolicitudesEmergencia(usuario.getId());
      listaSolicitudesXAprobar = Select.selectSolicitudesXAprobar(usuario);             
    } 
    else if ("enfermero".equals(usuario.getRol())) {
      listaSolicitudesEnfermeriaXAprobar = Select.selectSolicitudesXAprobar(usuario);
    }
    else if ("super".equals(usuario.getRol())) {
      listaSolicitudes = Select.selectSolicitudes(1, 0, true);
      listaSolicitudes = Select.selectSolicitudes(0, 0, true);
    }
  }
  
  public Map<String, Integer> mapEmergencias(Map<Integer, Emergencia> objs) {
    Map<String, Integer> res = new HashMap<String, Integer>();
    Iterator iterator = objs.entrySet().iterator();
    while (iterator.hasNext()) {
      Map.Entry mapEntry = (Map.Entry) iterator.next();
      Emergencia obj = (Emergencia) mapEntry.getValue();
      res.put(obj.getNombre(), obj.getId());
    }
    return res;
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
  
  public Date getCurrentDate() {
      return currentDate;
  }

}
