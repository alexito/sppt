package org.models;

import java.util.Date;

public class Solicitud implements java.io.Serializable {

  private int id;
  private Localidad localidadByOrigen;
  private Localidad localidadByDestino;
  private Usuario usuarioByIdUsuarioSolicita;
  private Usuario usuarioByIdUsuarioConductor;
  private Usuario usuarioByIdUsuarioCrea;
  private Date FSalida;
  private Date FLlegada;
  private String hospedaje;
  private Boolean estado;
  private String novedades;

  public Solicitud() {
  }

  public Solicitud(int id) {
    this.id = id;
  }

  public Solicitud(int id, Localidad localidadByOrigen, Localidad localidadByDestino, Usuario usuarioByIdUsuarioSolicita, Usuario usuarioByIdUsuarioConductor, Usuario usuarioByIdUsuarioCrea, Date FSalida, Date FLlegada, String hospedaje, Boolean estado, String novedades) {
    this.id = id;
    this.localidadByOrigen = localidadByOrigen;
    this.localidadByDestino = localidadByDestino;
    this.usuarioByIdUsuarioSolicita = usuarioByIdUsuarioSolicita;
    this.usuarioByIdUsuarioConductor = usuarioByIdUsuarioConductor;
    this.usuarioByIdUsuarioCrea = usuarioByIdUsuarioCrea;
    this.FSalida = FSalida;
    this.FLlegada = FLlegada;
    this.hospedaje = hospedaje;
    this.estado = estado;
    this.novedades = novedades;
  }

  public Solicitud(int id, Localidad localidadByOrigen, Localidad localidadByDestino, Date FSalida, Date FLlegada, String hospedaje, Boolean estado, String novedades, Usuario usuarioByIdUsuarioSolicita, Usuario usuarioByIdUsuarioConductor, Usuario usuarioByIdUsuarioCrea) {
    this.id = id;
    this.localidadByOrigen = localidadByOrigen;
    this.localidadByDestino = localidadByDestino;
    this.FSalida = FSalida;
    this.FLlegada = FLlegada;
    this.hospedaje = hospedaje;
    this.estado = estado;
    this.novedades = novedades;
    this.usuarioByIdUsuarioSolicita = usuarioByIdUsuarioSolicita;
    this.usuarioByIdUsuarioConductor = usuarioByIdUsuarioConductor;
    this.usuarioByIdUsuarioCrea = usuarioByIdUsuarioCrea;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Localidad getLocalidadByOrigen() {
    return this.localidadByOrigen;
  }

  public void setLocalidadByOrigen(Localidad localidadByOrigen) {
    this.localidadByOrigen = localidadByOrigen;
  }

  public Localidad getLocalidadByDestino() {
    return this.localidadByDestino;
  }

  public void setLocalidadByDestino(Localidad localidadByDestino) {
    this.localidadByDestino = localidadByDestino;
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

  public Date getFSalida() {
    return this.FSalida;
  }

  public void setFSalida(Date FSalida) {
    this.FSalida = FSalida;
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
