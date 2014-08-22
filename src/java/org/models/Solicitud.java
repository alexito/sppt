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
  private String novedades;
  private Distancia distanciaById;
  private Usuario usuarioByIdUsuarioSolicita;
  private Usuario usuarioByIdUsuarioConductor;
  private Usuario usuarioByIdUsuarioCrea;
  
  public Solicitud() {
    this.distanciaById = new Distancia();
    this.usuarioByIdUsuarioSolicita = new Usuario();
    this.usuarioByIdUsuarioConductor = new Usuario();
    this.usuarioByIdUsuarioCrea = new Usuario();
  }

  public Solicitud(int id) {
    this.id = id;
  }

  public Solicitud(int id, Distancia distancia, Usuario usuarioByIdUsuarioSolicita, Usuario usuarioByIdUsuarioConductor, Usuario usuarioByIdUsuarioCrea, Date FCreacion, Date FSalida, Date FLlegada, String hospedaje, Boolean estado, String novedades) {
    this.id = id;
    this.distanciaById = distancia;
    this.usuarioByIdUsuarioSolicita = usuarioByIdUsuarioSolicita;
    this.usuarioByIdUsuarioConductor = usuarioByIdUsuarioConductor;
    this.usuarioByIdUsuarioCrea = usuarioByIdUsuarioCrea;
    this.FCreacion = FCreacion;
    this.FSalida = FSalida;
    this.FLlegada = FLlegada;
    this.hospedaje = hospedaje;
    this.estado = estado;
    this.novedades = novedades;
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

  public Usuario getUsuarioByIdUsuarioCrea() {
    return this.usuarioByIdUsuarioCrea;
  }

  public void setUsuarioByIdUsuarioCrea(Usuario usuarioByIdUsuarioCrea) {
    this.usuarioByIdUsuarioCrea = usuarioByIdUsuarioCrea;
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

  public Boolean getEstado() {
    return this.estado;
  }
  
  public String getEstado_legible() {            
    return (getEstado()) ? "Activo" : "Inactivo";
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
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
