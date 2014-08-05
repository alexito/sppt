package org.models;
import java.util.Date;

public class Solicitud implements java.io.Serializable {

  private int id;
  private Usuario usuarioByIdUsuarioSolicita;
  private Usuario usuarioByIdUsuarioConductor;
  private Usuario usuarioByIdUsuarioCrea;
  private String origen;
  private String destino;
  private Date FSalida;
  private Date FLlegada;
  private String hospedaje;
  private Boolean estado;
  private String novedades;
  private String nombre_solicita;

  private String nombre_conductor;
  private String nombre_crea;

  public Solicitud() {
  }

  public Solicitud(int id) {
    this.id = id;
  }

  public Solicitud(int id, String origen, String destino, Date FSalida, Date FLlegada, String hospedaje, Boolean estado, String novedades, String u_solicita, String u_conductor, String u_crea) {
    this.id = id;
    this.origen = origen;
    this.destino = destino;
    this.FSalida = FSalida;
    this.FLlegada = FLlegada;
    this.hospedaje = hospedaje;
    this.estado = estado;
    this.novedades = novedades;
    this.nombre_solicita = u_solicita;
    this.nombre_conductor = u_conductor;
    this.nombre_crea = u_crea;
  }

  public Solicitud(int id, String origen, String destino, Date FSalida, Date FLlegada, String hospedaje, Boolean estado, String novedades, Usuario usuarioByIdUsuarioSolicita, Usuario usuarioByIdUsuarioConductor, Usuario usuarioByIdUsuarioCrea) {
    this.id = id;
    this.origen = origen;
    this.destino = destino;
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

  public String getOrigen() {
    return this.origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }

  public String getDestino() {
    return this.destino;
  }

  public void setDestino(String destino) {
    this.destino = destino;
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
    return nombre_solicita;
  }

  public void setNombre_solicita(String nombre_solicita) {
    this.nombre_solicita = nombre_solicita;
  }
  
  public String getNombre_conductor() {
    return nombre_conductor;
  }

  public void setNombre_conductor(String nombre_conductor) {
    this.nombre_conductor = nombre_conductor;
  }

  public String getNombre_crea() {
    return nombre_crea;
  }

  public void setNombre_crea(String nombre_crea) {
    this.nombre_crea = nombre_crea;
  }

  @Override
  public String toString() {
    return "";
  }
}
