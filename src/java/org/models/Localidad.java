package org.models;

public class Localidad implements java.io.Serializable {

  private int id;
  private String nombre;
  private String distancia;

  public Localidad() {
  }

  public Localidad(int id) {
    this.id = id;
  }
  
  public Localidad(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }
  
  public Localidad(int id, String nombre, String distancia) {
    this.id = id;
    this.nombre = nombre;
    this.distancia = distancia;
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
  
  public String getDistancia() {
    return distancia;
  }

  public void setDistancia(String distancia) {
    this.distancia = distancia;
  }
}
