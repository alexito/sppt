package org.models;

public class Distancia implements java.io.Serializable {

  private int id;
  private String distancia;
  private Localidad localidadByIdOrigen;
  private Localidad localidadByIdDestino;

  public Distancia() {
    this.distancia = "0";
    this.localidadByIdOrigen = new Localidad();
    this.localidadByIdDestino = new Localidad();
  }

  public Distancia(int id) {
    this.id = id;
  }

  public Distancia(Localidad localidadByIdOrigen, Localidad localidadByIdDestino, String distancia) {
    this.distancia = distancia;
    this.localidadByIdOrigen = localidadByIdOrigen;
    this.localidadByIdDestino = localidadByIdDestino;    
  }
  
  public Distancia(int id, String distancia, Localidad localidadByIdOrigen, Localidad localidadByIdDestino) {
    this.id = id;
    this.distancia = distancia;
    this.localidadByIdOrigen = localidadByIdOrigen;
    this.localidadByIdDestino = localidadByIdDestino;    
  }
  
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getDistancia() {
    return distancia;
  }

  public void setDistancia(String distancia) {
    this.distancia = distancia;
  }

  public Localidad getLocalidadByIdOrigen() {
    return localidadByIdOrigen;
  }

  public void setLocalidadByIdOrigen(Localidad localidadByIdOrigen) {
    this.localidadByIdOrigen = localidadByIdOrigen;
  }

  public Localidad getLocalidadByIdDestino() {
    return localidadByIdDestino;
  }

  public void setLocalidadByIdDestino(Localidad localidadByIdDestino) {
    this.localidadByIdDestino = localidadByIdDestino;
  }
  
  @Override
  public String toString() {
    return "";
  }
  

}
