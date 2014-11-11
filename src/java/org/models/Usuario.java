package org.models;

import java.text.ParseException;
import java.util.Date;

public class Usuario implements java.io.Serializable {

  private int id;
  private String codemp;
  private String codapr;
  private String nombre;
  private String apellido;
  private String cedula;
  private String clave;
  private String email;
  private String telefono;
  private String movil;
  private Boolean estado;
  private Boolean esInterno = true;
  private String observacion;
  private String observacion2;
  private String rol;
  private Date FDisponible;
  private Date FDisponible2;
  private String nombrecompleto;
  private int numero_pasajeros;
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

  public Usuario(int id, String nombre, String apellido, String cedula, String clave, String email, String telefono,
          String movil, Boolean estado, Boolean esInterno, String observacion, String observacion2, String rol, String codemp, String codapr, Date FDisponible, Date FDisponible2, int numero_pasajeros) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.cedula = cedula;
    this.clave = clave;
    this.email = email;
    this.telefono = telefono;
    this.movil = movil;
    this.estado = estado;
    this.esInterno = esInterno;
    this.observacion = observacion;
    this.observacion2 = observacion2;
    this.rol = rol;
    this.codemp = codemp;
    this.codapr = codapr;
    this.FDisponible = FDisponible;
    this.FDisponible2 = FDisponible2;
    this.numero_pasajeros=numero_pasajeros;
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }
  
  public String getCodemp() {
    return codemp;
  }

  public void setCodemp(String codemp) {
    this.codemp = codemp;
  }

  public String getCodapr() {
    return codapr;
  }

  public void setCodapr(String codapr) {
    this.codapr = codapr;
  }
  
  public String getNombre() {
    if(this.nombre == null)
      this.nombre = "";
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellido() {
    if(this.apellido == null)
      this.apellido = "";
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
  
  public String getMovil() {
    return this.movil;
  }

  public void setMovil(String movil) {
    this.movil = movil;
  }
  
  public String getObservacion() {
    if(this.observacion2 == null)
      this.observacion2 = "";
    return this.observacion;
  }

  public void setObservacion(String observacion) {
    this.observacion = observacion;
  }

  public Boolean getEstado() {
    return this.estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }
  
  public Boolean getEsInterno() {
    return this.esInterno;
  }

  public void setEsInterno(Boolean esInterno) {
    this.esInterno = esInterno;
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
  
  public Date getFDisponible() {
    if(FDisponible == null)
      FDisponible = new Date();
    return FDisponible;
  }

  public void setFDisponible(Date FDisponible) {
    this.FDisponible = FDisponible;
  }
  
  public String getNombrecompleto() {
    if(this.apellido == null)
      this.apellido = "";
    if(this.nombre == null)
      this.nombre = "";
    return this.apellido + " " + this.nombre;
  }

  public void setNombrecompleto(String nombrecompleto) {
    this.nombrecompleto = nombrecompleto;
  }
  
  
  public String getObservacion2() {
    if(this.observacion2 == null)
      this.observacion2 = "";
    return observacion2;
  }

  public void setObservacion2(String observacion2) {
    this.observacion2 = observacion2;
  }

  public Date getFDisponible2() {
    return FDisponible2;
  }

  public void setFDisponible2(Date FDisponible2) {
    this.FDisponible2 = FDisponible2;
  }

    public int getNumero_pasajeros() {
        return numero_pasajeros;
    }

    public void setNumero_pasajeros(int numero_pasajeros) {
        this.numero_pasajeros = numero_pasajeros;
    }
  
  
  

  @Override
  public String toString() {
    return nombre + apellido;
  }
}
