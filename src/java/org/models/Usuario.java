package org.models;

public class Usuario implements java.io.Serializable {

  private int id;
  private String nombre;
  private String apellido;
  private String cedula;
  private String clave;
  private String email;
  private String telefono;
  private Boolean estado;
  private String rol;
  private Usuario usuarioByIdUsuarioAprobador;

  public Usuario() {
  }

  public Usuario(int id, String nombre, String apellido, String cedula, String clave, String rol) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.cedula = cedula;
    this.clave = clave;
    this.rol = rol;
  }

  public Usuario(int id, String nombre, String apellido, String cedula, String clave, String email, String telefono, Boolean estado, String rol) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.cedula = cedula;
    this.clave = clave;
    this.email = email;
    this.telefono = telefono;
    this.estado = estado;
    this.rol = rol;
  }

  public Usuario(int id, String nombre, String apellido, String cedula, String clave, String email, String telefono, Boolean estado, String rol, Usuario usuarioByIdUsuarioAprobador) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.cedula = cedula;
    this.clave = clave;
    this.email = email;
    this.telefono = telefono;
    this.estado = estado;
    this.rol = rol;
    this.usuarioByIdUsuarioAprobador = usuarioByIdUsuarioAprobador;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    return this.apellido;
  }

  public void setApellido(String apellido) {
    this.apellido = apellido;
  }

  public String getCedula() {
    return this.cedula;
  }

  public void setCedula(String cedula) {
    this.cedula = cedula;
  }

  public String getClave() {
    return this.clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTelefono() {
    return this.telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public Boolean getEstado() {
    return this.estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }

  public String getEstado_legible() {
    return (getEstado()) ? "Activo" : "Inactivo";
  }

  public String getRol_legible() {
    return (getRol().equals("super")) ? "Administrador" : (getRol().equals("admin")) ? "Usuario" : (getRol().equals("conductor")) ? "Conductor" : (getRol().equals("aprobador")) ? "Aprobador" : "Recursos H";
  }

  public String getRol() {
    return this.rol;
  }

  public void setRol(String rol) {
    this.rol = rol;
  }

  public Usuario getUsuarioByIdUsuarioAprobador() {
    return usuarioByIdUsuarioAprobador;
  }

  public void setUsuarioByIdUsuarioAprobador(Usuario usuarioByIdUsuarioAprobador) {
    this.usuarioByIdUsuarioAprobador = usuarioByIdUsuarioAprobador;
  }

  @Override
  public String toString() {
    return nombre + apellido;
  }
}
