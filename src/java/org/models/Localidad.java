package org.models;

import java.util.HashSet;
import java.util.Set;

public class Localidad implements java.io.Serializable {

  private int id;
  private String nombre;
  private Set solicitudsForOrigen = new HashSet(0);
  private Set solicitudsForDestino = new HashSet(0);

  public Localidad() {
  }

  public Localidad(int id) {
    this.id = id;
  }
  
  public Localidad(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }
  
  public Localidad(int id, String nombre, Set solicitudsForOrigen, Set solicitudsForDestino) {
    this.id = id;
    this.nombre = nombre;
    this.solicitudsForOrigen = solicitudsForOrigen;
    this.solicitudsForDestino = solicitudsForDestino;
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

  public Set getSolicitudsForOrigen() {
    return this.solicitudsForOrigen;
  }

  public void setSolicitudsForOrigen(Set solicitudsForOrigen) {
    this.solicitudsForOrigen = solicitudsForOrigen;
  }

  public Set getSolicitudsForDestino() {
    return this.solicitudsForDestino;
  }

  public void setSolicitudsForDestino(Set solicitudsForDestino) {
    this.solicitudsForDestino = solicitudsForDestino;
  }
}
