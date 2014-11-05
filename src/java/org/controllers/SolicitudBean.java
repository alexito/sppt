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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import org.database.Insert;
import org.database.Select;
import org.database.Update;
import org.models.Emergencia;
import org.models.Localidad;
import org.models.Solicitud;
import org.models.Usuario;
import org.other.emailSender;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;

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
  private Solicitud solicitudRetorno;
  private Solicitud solicitudEmergenciaRetorno;
  private Solicitud solicitudEmergencia;
  private int loc_origen;
  private Usuario usuario;
  private Date currentDate = new Date();  
  private Map<String, Integer> listaEmergencia;
  private List<Usuario> listaEnfermeros;

    
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
  
  public Solicitud getSolicitudRetorno() {
    if(solicitudRetorno == null){
      solicitudRetorno = Select.selectLastSolicitud();
    }
    return solicitudRetorno;
  }

  public void setSolicitudRetorno(Solicitud solicitudRetorno) {
    this.solicitudRetorno = solicitudRetorno;
  }
  
  public Solicitud getSolicitudEmergenciaRetorno() {
    if(solicitudEmergenciaRetorno == null){
      solicitudEmergenciaRetorno = Select.selectLastEmergenciaSolicitud();
    }
    return solicitudEmergenciaRetorno;
  }

  public void setSolicitudEmergenciaRetorno(Solicitud solicitudEmergenciaRetorno) {
    this.solicitudEmergenciaRetorno = solicitudEmergenciaRetorno;
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
    return listaSolicitudesEnfermeriaAprobadas;
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
    
    try {      
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
    } catch (Exception ex) {
      Logger.getLogger(SolicitudBean.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public List<Usuario> completeUsuarioInterno(String query){
    query = query.toLowerCase();
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
    query = query.toLowerCase();
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
  
  private String pickUsuarioInternoIds(List<Usuario> lista, boolean is_insert){
    String uids = "";
    
    if(is_insert)
      uids = "" + usuario.getId();
    
    for (Usuario user : lista) {
        if(!"".equals(uids))
          uids += ",";
        uids += user.getId();
    }
    if(uids.length() > 2 && uids.contains(","))
      uids = removeDuplicated(uids);
    return uids;
  }
  
  private String removeDuplicated(String uids) {
    String[] input = uids.split(",");
    String[] response = new String[input.length];
    int j;
    for (j = 0; j < input.length; j++){
      response[j] = "";  
    }
    
    for (int i = 0; i < input.length; i++) {
      for (j = 0; j < input.length; j++){
        if(response[j].equals(input[i]))
          break;        
        else if(response[j].equals("")){
          response[j] = input[i];
          break;
        }          
      }
    }
    String new_uids = "";
    for (j = 0; j < response.length; j++){
      if("".equals(response[j]))
        continue;
      if(!"".equals(new_uids))
          new_uids += ",";
        new_uids += response[j];
    }
    return new_uids;
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
        if(nombre.length() > 2)
          uids += Insert.InsertNewUsuarioExterno(nombre);
      }
    }
    if(uids.length() > 2 && uids.contains(","))
      uids = removeDuplicated(uids);
    return uids;
  }
  
  public List<Solicitud> cancelSolicitudyDatosRetorno() throws IOException, SQLException, ParseException, AddressException, Exception {
    return null;
  }
  
  public void saveSolicitudyDatosRetorno() throws IOException, SQLException, ParseException, AddressException, Exception {
    
    solicitudRetorno.setRetorno(true);
    solicitudRetorno.setIds_interno_retorno(pickUsuarioInternoIds(solicitudRetorno.getListaInternosSeleccionados_retorno(), true));
    solicitudRetorno.setIds_externo_retorno(pickUsuarioExternoIds(solicitudRetorno.getListaExternosSeleccionados_retorno(), false, solicitudRetorno));
    
    Update.UpdateSolicitudRetorno(solicitudRetorno);
    
  }
  
  public List<Solicitud> saveSolicitudyEditarRetorno() throws IOException, SQLException, ParseException, AddressException, Exception {
    saveSolicitud();
    return listaSolicitudesAprobadas;
  }
  
  public List<Solicitud> saveSolicitudEmergenciayEditarRetorno() throws IOException, SQLException, ParseException, AddressException, Exception {
    saveSolicitudEmergencia();
    return listaSolicitudesEmergencias;
  }
  
  public List<Solicitud> saveSolicitud() throws IOException, SQLException, ParseException, AddressException, Exception {
    Usuario logged_user = Select.LoggedUser();       
    solicitud.setUsuarioByIdUsuarioSolicita(logged_user);
    int h = solicitud.getFSalida().getHours();
    int m = solicitud.getFSalida().getMinutes();
    if ((h == 15 && m > 30) || h >= 16) {
      solicitud.setEstado(false);      
    } else {
      solicitud.setEstado(true);
    }
    
    if(!solicitud.getRetorno())
      solicitud.setFRetorno(new Date());
    
    solicitud.setFCreacion(new Date());
    solicitud.setCancelado(false);
    solicitud.setIds_interno(pickUsuarioInternoIds(solicitud.getListaInternosSeleccionados(), true));
    solicitud.setIds_externo(pickUsuarioExternoIds(solicitud.getListaExternosSeleccionados(), true, solicitud));
    
    solicitud.setIds_interno_retorno(solicitud.getIds_interno());
    solicitud.setIds_externo_retorno(solicitud.getIds_externo());
    
    String sol_id = Insert.InsertSolicitud(solicitud);
    
    solicitudRetorno = new Solicitud();
    solicitudRetorno.setId(Integer.parseInt(sol_id));
    solicitudRetorno.setIds_interno_retorno(solicitud.getIds_interno());
    solicitudRetorno.setIds_externo_retorno(solicitud.getIds_externo());
    solicitudRetorno.setFRetorno(solicitud.getFLlegada());
    
    if(!solicitud.getEstado()){
      emailSender es = new emailSender();
      listaEnfermeros = Select.selectUsuarios("enfermeros");
      
      Address[] responder_a = new Address[listaEnfermeros.size()];
      Address[] enviar_a = new Address[1];
      
      for(int i = 0; i < listaEnfermeros.size(); i++){
        Usuario u = listaEnfermeros.get(i);
        responder_a[i] = new InternetAddress(u.getEmail());
      }
      
      int id_aprobador = Select.selectUsuarioIDByEMPLFAPR(solicitud.getUsuarioByIdUsuarioSolicita().getCodapr());
      String email = Select.selectUsuarioEmailById(id_aprobador);
      enviar_a[0] = new InternetAddress(email);
      
      String origen = Select.selectLocalidadNombreById(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId());
      String destino = Select.selectLocalidadNombreById(solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
      
      //Envia al aprobador
      es.send(enviar_a, responder_a, "Solicitud Especial", "La solicitud Numero: " + sol_id + " necesita su aprobacion. Los datos son los siguientes:\n"
              + "\nNombre del solicitante: " + usuario.getNombrecompleto()
              + "\nOrigen: " + origen + " \nDestino: " + destino + " \nDistancia: " + solicitud.getDistanciaById().getDistancia()
              + "\nFecha Salida: " + ( 1900 + solicitud.getFSalida().getYear()) + "-" + (solicitud.getFSalida().getMonth()+1) + "-" + solicitud.getFSalida().getDate() + " " + solicitud.getFSalida().getHours() + ":" +solicitud.getFSalida().getMinutes()
              + "\nFecha Llegada " +(1900 + solicitud.getFLlegada().getYear()) + "-" + (solicitud.getFLlegada().getMonth()+1) + "-" + solicitud.getFLlegada().getDate() + " " + solicitud.getFLlegada().getHours() + ":" +solicitud.getFLlegada().getMinutes()
              + "");
      
      //Envia a los enfermeros
      es = new emailSender();
      Address[] responder_a_defecto = new Address[]{new InternetAddress ("noreply@mail.com")};       
      es.send(responder_a, responder_a_defecto, "Solicitud Especial", "Se ha generado una nueva Solicitud de Transporte con id Numero: " + sol_id + ". Los datos son los siguientes:\n"
              + "\nNombre del solicitante: " + usuario.getNombrecompleto()
              + "\nOrigen: " + origen + " \nDestino: " + destino + " \nDistancia: " + solicitud.getDistanciaById().getDistancia()
              + "\nFecha Salida: " + ( 1900 + solicitud.getFSalida().getYear()) + "-" + (solicitud.getFSalida().getMonth()+1) + "-" + solicitud.getFSalida().getDate() + " " + solicitud.getFSalida().getHours() + ":" +solicitud.getFSalida().getMinutes()
              + "\nFecha Llegada " +(1900 + solicitud.getFLlegada().getYear()) + "-" + (solicitud.getFLlegada().getMonth()+1) + "-" + solicitud.getFLlegada().getDate() + " " + solicitud.getFLlegada().getHours() + ":" +solicitud.getFLlegada().getMinutes()
              + "");
    }

    solicitud = new Solicitud();
    updateInfoSolicitudes();

    return listaSolicitudesAprobadas;
  }
  
  public List<Solicitud> saveSolicitudEmergencia() throws IOException, SQLException, ParseException {
    Usuario logged_user = Select.LoggedUser();       
    solicitudEmergencia.setUsuarioByIdUsuarioSolicita(logged_user);
   
    solicitudEmergencia.setEstado(true);
    solicitudEmergencia.setEmergencia(true);
    solicitudEmergencia.setFCreacion(new Date());
    solicitudEmergencia.setCancelado(false);
    solicitudEmergencia.setIds_interno(pickUsuarioInternoIds(solicitudEmergencia.getListaInternosSeleccionados(), false));
    solicitudEmergencia.setIds_externo(pickUsuarioExternoIds(solicitudEmergencia.getListaExternosSeleccionados(), true, solicitudEmergencia));
    
    String sol_id = Insert.InsertSolicitudEmergencia(solicitudEmergencia);
    
    solicitudRetorno = new Solicitud();
    solicitudRetorno.setId(Integer.parseInt(sol_id));
    solicitudRetorno.setIds_interno_retorno(solicitud.getIds_interno());
    solicitudRetorno.setIds_externo_retorno(solicitud.getIds_externo());
    solicitudRetorno.setFRetorno(solicitud.getFLlegada());

    solicitudEmergencia = new Solicitud();
    solicitudEmergencia.setEmergencia(true);
    //updateInfoSolicitudes();

    return listaSolicitudesAprobadas;
  }

  public List<Solicitud> onRowEdit(RowEditEvent event) throws SQLException, ParseException, IOException, AddressException, Exception {
    Solicitud editedSolicitud = (Solicitud) event.getObject();
    
    int h = editedSolicitud.getFSalida().getHours();
    int m = editedSolicitud.getFSalida().getMinutes();
    
    //Check if the current solicitud is updated by an Aprobador
    //If true both conditions then is approved
    if(!editedSolicitud.getEstado() || !editedSolicitud.getEstadoEnfermeria()){
      if(!editedSolicitud.getListaAprobador()){
        if ((h == 15 && m > 30) || h >= 16) {
          editedSolicitud.setEstado(false);
        } else {
          editedSolicitud.setEstado(true);
        }
      }
    }
    
    editedSolicitud.setIds_interno(pickUsuarioInternoIds(editedSolicitud.getListaInternosSeleccionados(), false));
    editedSolicitud.setIds_externo(pickUsuarioExternoIds(editedSolicitud.getListaExternosSeleccionados(), false, editedSolicitud));
    
    if(editedSolicitud.getFRetorno() != null){
//      editedSolicitud.setIds_interno_retorno(editedSolicitud.getIds_interno());
//      editedSolicitud.setIds_externo_retorno(editedSolicitud.getIds_externo());
      Update.UpdateSolicitudRetorno(editedSolicitud);
    }
    
    boolean es_relacion = false;
    
    if("enfermero".equals(usuario.getRol())){
      //caso q relaciona solicitud
      if(!editedSolicitud.getId_solicitud_relacion().equals("0") && !editedSolicitud.getId_solicitud_relacion().equals("")) {        
        editedSolicitud.setUsuarioByIdUsuarioEnfermero(usuario);
        Update.UpdateSolicitudRelacion(editedSolicitud); 
        notificarSolicitudActualizada(editedSolicitud, true, false);
      }else if(editedSolicitud.getEstadoEnfermeria() && editedSolicitud.getUsuarioByIdUsuarioConductor() != null && editedSolicitud.getUsuarioByIdUsuarioConductor().getFDisponible() != null){
          editedSolicitud.setUsuarioByIdUsuarioEnfermero(usuario);
          Update.UpdateUsuarioConductor(editedSolicitud.getUsuarioByIdUsuarioConductor());
          if(editedSolicitud.getUsuarioByIdUsuarioConductor2().getId() != 0){
            Update.UpdateSolicitudConductor2(editedSolicitud);
            Update.UpdateUsuarioConductor2(editedSolicitud.getUsuarioByIdUsuarioConductor2());
          }       
          notificarSolicitudActualizada(editedSolicitud, true, false);
      }else if(editedSolicitud.getCancelado()){
        Update.UpdateSolicitudCancelar(editedSolicitud);
        notificarSolicitudActualizada(editedSolicitud, false, true);
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
  
  public void notificarSolicitudActualizada(Solicitud sol, Boolean aprobado, Boolean cancelado) throws AddressException, SQLException, Exception{
    emailSender es = new emailSender();
      Address[] enviar_a = new Address[3];
      enviar_a[0] = new InternetAddress("noemail@gmail.com");
      enviar_a[1] = new InternetAddress("noemail@gmail.com");
      enviar_a[2] = new InternetAddress("noemail@gmail.com");
      
      String email = Select.selectUsuarioEmailById(sol.getUsuarioByIdUsuarioSolicita().getId());
      enviar_a[0] = new InternetAddress(email);
      
      String origen = Select.selectLocalidadNombreById(sol.getDistanciaById().getLocalidadByIdOrigen().getId());
      String destino = Select.selectLocalidadNombreById(sol.getDistanciaById().getLocalidadByIdDestino().getId());
      
      Address[] responder_a_defecto = new Address[]{new InternetAddress ("noreply@mail.com")};
      
      //Caso cancelado
      if(cancelado){
        
        es.send(enviar_a, responder_a_defecto, "Solicitud actualizada", "La solicitud Numero: " + sol.getId() + " ha sido CANCELADA. Los datos son los siguientes:\n"
              //  + "\nNombre del solicitante: " + usuario.getNombrecompleto()
                + "\nOrigen: " + origen + " \nDestino: " + destino + " \nDistancia: " + sol.getDistanciaById().getDistancia()
                + "\nFecha Salida: " + ( 1900 + sol.getFSalida().getYear()) + "-" + (sol.getFSalida().getMonth()+1) + "-" + sol.getFSalida().getDate() + " " + sol.getFSalida().getHours() + ":" +sol.getFSalida().getMinutes()
                + "\nFecha Llegada " +(1900 + sol.getFLlegada().getYear()) + "-" + (sol.getFLlegada().getMonth()+1) + "-" + sol.getFLlegada().getDate() + " " + sol.getFLlegada().getHours() + ":" +sol.getFLlegada().getMinutes()
                + "");
      }
      
      if(aprobado){
        email = Select.selectUsuarioEmailById(sol.getUsuarioByIdUsuarioConductor().getId());
        if(email != "")
          enviar_a[1] = new InternetAddress(email);
        if(sol.getUsuarioByIdUsuarioConductor2() != null && sol.getUsuarioByIdUsuarioConductor2().getId() != 0){
          email = Select.selectUsuarioEmailById(sol.getUsuarioByIdUsuarioConductor2().getId());
          if(email != "")
            enviar_a[2] = new InternetAddress(email);
        }
        es.send(enviar_a, responder_a_defecto, "Solicitud actualizada", "La solicitud Numero: " + sol.getId() + " ha sido APROBADA. Los datos son los siguientes:\n"
              //  + "\nNombre del solicitante: " + usuario.getNombrecompleto()
                + "\nOrigen: " + origen + " \nDestino: " + destino + " \nDistancia: " + sol.getDistanciaById().getDistancia()
                + "\nFecha Salida: " + ( 1900 + sol.getFSalida().getYear()) + "-" + (sol.getFSalida().getMonth()+1) + "-" + sol.getFSalida().getDate() + " " + sol.getFSalida().getHours() + ":" +sol.getFSalida().getMinutes()
                + "\nFecha Llegada " +(1900 + sol.getFLlegada().getYear()) + "-" + (sol.getFLlegada().getMonth()+1) + "-" + sol.getFLlegada().getDate() + " " + sol.getFLlegada().getHours() + ":" +sol.getFLlegada().getMinutes()
                + "");
      }
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
      listaSolicitudes = Select.selectSolicitudes(0, 0, true);
    }
    else if ("super".equals(usuario.getRol())) {      
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
    updateInfoSolicitudes();
  }
  
  public Date getCurrentDate() {
      return currentDate;
  }
  
  public void onDateSelect(SelectEvent event) throws ParseException {
    
    
    FacesContext facesContext = FacesContext.getCurrentInstance();
    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    Object o = event.getObject();
    
    
    
    facesContext = FacesContext.getCurrentInstance();
    
    int h = solicitud.getFSalida().getHours();
    int m = solicitud.getFSalida().getMinutes();
    if ((h == 15 && m > 30) || h >= 16) {
      facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", "La solicitud tiene la Fecha y hora de Salida fuera del horario permitido y se notificará a su Aprobador. Permitido de 8:00 a.m. a 15:30 p.m."));
    } 
    if(solicitud.getFLlegada() != null){
      if(solicitud.getFLlegada().getDate() < solicitud.getFSalida().getDate()){
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", "La Fecha y hora de Llegada debe ser mayor a la Fecha y hora de Salida."));
      }
      else if(solicitud.getFLlegada().getDate() == solicitud.getFSalida().getDate()){
        if(solicitud.getFLlegada().getHours() < solicitud.getFSalida().getHours()){
          facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", "La Fecha y hora de Llegada debe ser mayor a la Fecha y hora de Salida."));
        }
        else if(solicitud.getFLlegada().getHours() == solicitud.getFSalida().getHours()){
          if(solicitud.getFLlegada().getMinutes() < solicitud.getFSalida().getMinutes()){
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", "La Fecha y hora de Llegada debe ser mayor a la Fecha y hora de Salida."));
          }
        } 
      }
    }
    
  }
  
}
