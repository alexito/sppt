package org.models;

import java.text.ParseException;
import java.util.Date;

public class Solicitud implements java.io.Serializable {

  private int id;  
  private Date FCreacion;
  private Date FSalida;
  private Date FLlegada;
  private String hospedaje;
  private Boolean estado;
  private Boolean estadoEnfermeria = false;
  private Boolean emergencia = false;
  private String emergenciaRazon = "";
  private Boolean listaAprobador;
  private Boolean es_creador;
  private String novedades;
  private String direccionOrigen;
  private String direccionDestino;
  private Distancia distanciaById;
  private Usuario usuarioByIdUsuarioSolicita;
  private Usuario usuarioByIdUsuarioConductor;
  private Usuario usuarioByIdUsuarioAprobador;
  private Usuario usuarioByIdUsuarioEnfermero;
  
  public Solicitud() {
    this.distanciaById = new Distancia();
    this.usuarioByIdUsuarioSolicita = new Usuario();
    this.usuarioByIdUsuarioConductor = new Usuario();
    this.usuarioByIdUsuarioAprobador = new Usuario();
    this.usuarioByIdUsuarioEnfermero = new Usuario();
    this.listaAprobador = false;
  }

  public Solicitud(int id) {
    this.id = id;
  }

  public Solicitud(int id, Date FCreacion, Date FSalida, Date FLlegada, String direccionOrigen, String direccionDestino, String hospedaje, Boolean estado, Boolean estadoEnfermeria,
          String novedades, Boolean es_creador, Distancia distancia, Usuario usuarioByIdUsuarioSolicita, Usuario usuarioByIdUsuarioConductor, Usuario usuarioByIdUsuarioAprobador, Usuario usuarioByIdUsuarioEnfermero) {
    this.id = id;
    this.distanciaById = distancia;
    this.usuarioByIdUsuarioSolicita = usuarioByIdUsuarioSolicita;
    this.usuarioByIdUsuarioConductor = usuarioByIdUsuarioConductor;
    this.usuarioByIdUsuarioAprobador = usuarioByIdUsuarioAprobador;
    this.usuarioByIdUsuarioEnfermero = usuarioByIdUsuarioEnfermero;
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

  public Boolean getEstadoEnfermeria() {
    return estadoEnfermeria;
  }

  public void setEstadoEnfermeria(Boolean estadoEnfermeria) {
    this.estadoEnfermeria = estadoEnfermeria;
  }

  public Boolean getEmergencia() {
    return emergencia;
  }

  public void setEmergencia(Boolean emergencia) {
    this.emergencia = emergencia;
  }

  public String getEmergenciaRazon() {
    return emergenciaRazon;
  }

  public void setEmergenciaRazon(String emergenciaRazon) {
    this.emergenciaRazon = emergenciaRazon;
  }

  public String getNovedades() {
    return this.novedades;
  }

  public void setNovedades(String novedades) {
    this.novedades = novedades;
  }
  
  public Boolean getEs_creador() {
    return es_creador;
  }

  public void setEs_creador(Boolean es_creador) {
    this.es_creador = es_creador;
  }

  public String getNombre_solicita() {
    return "";
  }
  

}
