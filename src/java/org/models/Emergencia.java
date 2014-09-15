package org.models;

public class Emergencia implements java.io.Serializable {

  private int id;
  private String nombre;
  private Boolean estado;

  public Emergencia() {
  }

  public Emergencia(int id) {
    this.id = id;
  }
  
  public Emergencia(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
    this.estado = true;
  }
  
  public Emergencia(int id, String nombre, Boolean estado) {
    this.id = id;
    this.nombre = nombre;
    this.estado = estado;
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
  
  public Boolean isEstado() {
    return estado;
  }

  public void setEstado(Boolean estado) {
    this.estado = estado;
  }
}
