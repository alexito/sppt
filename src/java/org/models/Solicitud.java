package org.models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Solicitud implements java.io.Serializable {

  private int id;  
  private Date FCreacion;
  private Date FSalida;
  private Date FLlegada;
  private String hospedaje;
  private Boolean estado;
  private Boolean estadoEnfermeria = false;
  private Boolean emergencia = false;  
  private Boolean listaAprobador;
  private Boolean es_creador;
  private String novedades;
  private String direccionOrigen;
  private String direccionDestino;
  private Distancia distanciaById;
  private Usuario usuarioByIdUsuarioSolicita;
  private Usuario usuarioByIdUsuarioConductor;
  private Usuario usuarioByIdUsuarioConductor2;
  private Usuario usuarioByIdUsuarioAprobador;
  private Usuario usuarioByIdUsuarioEnfermero;
  private String nuevoUsuarioExterno;
  private String ids_interno;
  private String ids_externo;
  private String nombres_interno;
  private String nombres_externo;
  private List<Usuario> listaInternosSeleccionados;
  private List<Usuario> listaExternosSeleccionados;
  private Boolean cancelado;
  private Emergencia emergenciaById;
  private String id_solicitud_relacion;

  public Solicitud() {
    this.distanciaById = new Distancia();
    this.usuarioByIdUsuarioSolicita = new Usuario();
    this.usuarioByIdUsuarioConductor = new Usuario();
    this.usuarioByIdUsuarioConductor2 = new Usuario();
    this.usuarioByIdUsuarioAprobador = new Usuario();
    this.usuarioByIdUsuarioEnfermero = new Usuario();
    this.emergenciaById = new Emergencia();
    this.listaAprobador = false;
    this.cancelado = false;
    this.listaInternosSeleccionados = new ArrayList<Usuario>();
    this.listaExternosSeleccionados = new ArrayList<Usuario>();
  }

  public Solicitud(int id) {
    this.id = id;
  }

  public Solicitud(int id, Date FCreacion, Date FSalida, Date FLlegada, String direccionOrigen, String direccionDestino, String hospedaje, Boolean estado, Boolean estadoEnfermeria,
          String novedades, Boolean es_creador, Distancia distancia, Usuario usuarioByIdUsuarioSolicita, Usuario usuarioByIdUsuarioConductor, Usuario usuarioByIdUsuarioConductor2, Usuario usuarioByIdUsuarioAprobador, Usuario usuarioByIdUsuarioEnfermero,
          String ids_interno, String ids_externo, Emergencia emergenciaById, Boolean cancelado, String id_solicitud_relacion) {
    this.id = id;
    this.distanciaById = distancia;
    this.usuarioByIdUsuarioSolicita = usuarioByIdUsuarioSolicita;
    this.usuarioByIdUsuarioConductor = usuarioByIdUsuarioConductor;
    this.usuarioByIdUsuarioConductor2 = usuarioByIdUsuarioConductor2;
    this.usuarioByIdUsuarioAprobador = usuarioByIdUsuarioAprobador;
    this.usuarioByIdUsuarioEnfermero = usuarioByIdUsuarioEnfermero;
    this.emergenciaById = emergenciaById;
    this.FCreacion = FCreacion;
    this.FSalida = FSalida;
    this.FLlegada = FLlegada;
    this.direccionOrigen = direccionOrigen;
    this.direccionDestino = direccionDestino;
    this.hospedaje = hospedaje;
    this.estado = estado;
    this.estadoEnfermeria = estadoEnfermeria;
    this.novedades = novedades;
    this.es_creador = es_creador;
    this.listaAprobador = false;
    this.cancelado = cancelado;
    this.ids_interno = ids_interno;
    this.ids_externo = ids_externo;
    this.id_solicitud_relacion = id_solicitud_relacion;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Distancia getDistanciaById() {
    return this.distanciaById;
  }

  public void setDistanciaById(Distancia distanciaById) {
    this.distanciaById = distanciaById;
  }

  public Usuario getUsuarioByIdUsuarioSolicita() {    
    return this.usuarioByIdUsuarioSolicita;
  }

  public void setUsuarioByIdUsuarioSolicita(Usuario usuarioByIdUsuarioSolicita) {
    this.usuarioByIdUsuarioSolicita = usuarioByIdUsuarioSolicita;
  }

  public Usuario getUsuarioByIdUsuarioConductor() {
    if(this.usuarioByIdUsuarioConductor == null)
      this.usuarioByIdUsuarioConductor = new Usuario();  
    return this.usuarioByIdUsuarioConductor;
  }

  public void setUsuarioByIdUsuarioConductor(Usuario usuarioByIdUsuarioConductor) {
    this.usuarioByIdUsuarioConductor = usuarioByIdUsuarioConductor;
  }
  
  public Usuario getUsuarioByIdUsuarioAprobador() {    
    return this.usuarioByIdUsuarioAprobador;
  }

  public void setUsuarioByIdUsuarioAprobador(Usuario usuarioByIdUsuarioAprobador) {
    this.usuarioByIdUsuarioAprobador = usuarioByIdUsuarioAprobador;
  }
  
  public Usuario getUsuarioByIdUsuarioEnfermero() {    
    return this.usuarioByIdUsuarioEnfermero;
  }

  public void setUsuarioByIdUsuarioEnfermero(Usuario usuarioByIdUsuarioEnfermero) {
    this.usuarioByIdUsuarioEnfermero = usuarioByIdUsuarioEnfermero;
  }

  public Date getFSalida() throws ParseException {
    return this.FSalida;
  }

  public void setFSalida(Date FSalida) {
    this.FSalida = FSalida;
  }

  public Date getFCreacion() {
    return this.FCreacion;
  }

  public void setFCreacion(Date FCreacion) {
    this.FCreacion = FCreacion;
  }
  
  public Date getFLlegada() {
    return this.FLlegada;
  }

  public void setFLlegada(Date FLlegada) {
    this.FLlegada = FLlegada;
  }

  public String getHospedaje() {
    return this.hospedaje;
  }

  public void setHospedaje(String hospedaje) {
    this.hospedaje = hospedaje;
  }
  
  public String getDireccionOrigen() {
    return this.direccionOrigen;
  }

  public void setDireccionOrigen(String direccionOrigen) {
    this.direccionOrigen = direccionOrigen;
  }

  public String getDireccionDestino() {
    return this.direccionDestino;
  }

  public void setDireccionDestino(String direccionDestino) {
    this.direccionDestino = direccionDestino;
  }
  
  public Boolean getListaAprobador() {
    return this.listaAprobador;
  }
  
  public void setListaAprobador(Boolean listaAprobador) {
    this.listaAprobador = listaAprobador;
  }
  
  public Boolean getEstado() {
    if(this.estado == null)
      this.estado = false;
    return this.estado;
  }
  
  public void setEstado(Boolean estado) {
    this.estado = estado;
  }
  
  public String getEstado_legible() {            
    return (getEstado()) ? "Aprobado" : "Pendiente";
  }
  
  public String getEstadoEnfermeria_legible() {            
    return (getEstadoEnfermeria()) ? "Aprobado" : "Pendiente";
  }
  
  public String getEstadoCancelado_legible() {            
    return (getCancelado()) ? "Cancelado" : "Activo";
  }

  public Boolean getEstadoEnfermeria() {
    if(this.estadoEnfermeria == null)
      this.estadoEnfermeria = false;
    return estadoEnfermeria;
  }

  public void setEstadoEnfermeria(Boolean estadoEnfermeria) {
    this.estadoEnfermeria = estadoEnfermeria;
  }

  public Boolean getEmergencia() {
    if(this.emergencia == null)
      this.emergencia = false;
    return emergencia;
  }

  public void setEmergencia(Boolean emergencia) {
    this.emergencia = emergencia;
  }

  public String getNovedades() {
    return this.novedades;
  }

  public void setNovedades(String novedades) {
    this.novedades = novedades;
  }
  
  public Boolean getEs_creador() {
    if(this.es_creador == null)
      this.es_creador = false;
    return es_creador;
  }

  public void setEs_creador(Boolean es_creador) {
    this.es_creador = es_creador;
  }
  
  public List<Usuario> getListaInternosSeleccionados() {
    if(listaInternosSeleccionados == null)
      listaInternosSeleccionados = new ArrayList<Usuario>();
    return listaInternosSeleccionados;
  }

  public void setListaInternosSeleccionados(List<Usuario> listaInternosSeleccionados) {
    this.listaInternosSeleccionados = listaInternosSeleccionados;
  }

  public List<Usuario> getListaExternosSeleccionados() {
    if(listaExternosSeleccionados == null)
      listaExternosSeleccionados = new ArrayList<Usuario>();
    return listaExternosSeleccionados;
  }

  public void setListaExternosSeleccionados(List<Usuario> listaExternosSeleccionados) {
    this.listaExternosSeleccionados = listaExternosSeleccionados;
  }
  
  public String getNuevoUsuarioExterno() {
    return nuevoUsuarioExterno;
  }

  public void setNuevoUsuarioExterno(String nuevoUsuarioExterno) {
    this.nuevoUsuarioExterno = nuevoUsuarioExterno;
  }
  
  public String getIds_interno() {
    return ids_interno;
  }

  public void setIds_interno(String ids_interno) {
    this.ids_interno = ids_interno;
  }

  public String getIds_externo() {
    return ids_externo;
  }

  public void setIds_externo(String ids_externo) {
    this.ids_externo = ids_externo;
  }
  
  public String getNombres_interno() {
    String names = "";
    for (Usuario user : listaInternosSeleccionados) {
        if(!"".equals(names))
          names += ", ";
        names += user.getNombrecompleto();
    }
    return names;
  }

  public void setNombres_interno(String nombres_interno) {
    this.nombres_interno = nombres_interno;
  }

  public String getNombres_externo() {
    String names = "";
    for (Usuario user : listaExternosSeleccionados) {
        if(!"".equals(names))
          names += ", ";
        names += user.getNombrecompleto();
    }
    return names;
  }

  public void setNombres_externo(String nombres_externo) {
    this.nombres_externo = nombres_externo;
  }

  public String getNombre_solicita() {
    return "";
  }
  
  public Usuario getUsuarioByIdUsuarioConductor2() {
    return usuarioByIdUsuarioConductor2;
  }

  public void setUsuarioByIdUsuarioConductor2(Usuario usuarioByIdUsuarioConductor2) {
    this.usuarioByIdUsuarioConductor2 = usuarioByIdUsuarioConductor2;
  }

  public Boolean getCancelado() {
    return cancelado;
  }

  public void setCancelado(Boolean cancelado) {
    this.cancelado = cancelado;
  }

  public Emergencia getEmergenciaById() {
    return emergenciaById;
  }

  public void setEmergenciaById(Emergencia emergenciaById) {
    this.emergenciaById = emergenciaById;
  }
  
  public String getId_solicitud_relacion() {
    if(id_solicitud_relacion == null)
      id_solicitud_relacion = "";
    return id_solicitud_relacion;
  }

  public void setId_solicitud_relacion(String id_solicitud_relacion) {
    this.id_solicitud_relacion = id_solicitud_relacion;
  }

}

