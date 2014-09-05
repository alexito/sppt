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
  private Boolean listaAprobador;
  private String novedades;
  private String direccionOrigen;
  private String direccionDestino;
  private Distancia distanciaById;
  private Usuario usuarioByIdUsuarioSolicita;
  private Usuario usuarioByIdUsuarioConductor;
  
  public Solicitud() {
    this.distanciaById = new Distancia();
    this.usuarioByIdUsuarioSolicita = new Usuario();
    this.usuarioByIdUsuarioConductor = new Usuario();
    this.listaAprobador = false;
  }

  public Solicitud(int id) {
    this.id = id;
  }

  public Solicitud(int id, Date FCreacion, Date FSalida, Date FLlegada, String direccionOrigen, String direccionDestino, String hospedaje, Boolean estado, String novedades, Distancia distancia, Usuario usuarioByIdUsuarioSolicita, Usuario usuarioByIdUsuarioConductor) {
    this.id = id;
    this.distanciaById = distancia;
    this.usuarioByIdUsuarioSolicita = usuarioByIdUsuarioSolicita;
    this.usuarioByIdUsuarioConductor = usuarioByIdUsuarioConductor;
    this.FCreacion = FCreacion;
    this.FSalida = FSalida;
    this.FLlegada = FLlegada;
    this.direccionOrigen = direccionOrigen;
    this.direccionDestino = direccionDestino;
    this.hospedaje = hospedaje;
    this.estado = estado;
    this.novedades = novedades;
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
    return this.usuarioByIdUsuarioConductor;
  }

  public void setUsuarioByIdUsuarioConductor(Usuario usuarioByIdUsuarioConductor) {
    this.usuarioByIdUsuarioConductor = usuarioByIdUsuarioConductor;
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

  

  public String getNovedades() {
    return this.novedades;
  }

  public void setNovedades(String novedades) {
    this.novedades = novedades;
  }

  public String getNombre_solicita() {
    return "";
  }
  

}
