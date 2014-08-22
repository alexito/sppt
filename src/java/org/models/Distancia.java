package org.models;

public class Distancia implements java.io.Serializable {

  private int id;
  private float distancia;
  private Localidad localidadByIdOrigen;
  private Localidad localidadByIdDestino;

  public Distancia() {
    this.distancia = (float) 0.0;
    this.localidadByIdOrigen = new Localidad();
    this.localidadByIdDestino = new Localidad();
  }

  public Distancia(int id) {
    this.id = id;
  }

  public Distancia(int id, float distancia, Localidad localidadByIdOrigen, Localidad localidadByIdDestino) {
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

  public float getDistancia() {
    return distancia;
  }

  public void setDistancia(float distancia) {
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
