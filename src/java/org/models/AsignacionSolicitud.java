/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.models;

import java.sql.Timestamp;

/**
 *
 * @author User
 */
public class AsignacionSolicitud {
    private String direccion_origen;
    private String direccion_destino;
    private Timestamp fecha_salida;
    private Timestamp fecha_llegada;
    private Timestamp fecha_retorno;
    private String retorno_observacion;
    private String usuario_solicitante;
    private Distancia distancia;

  public Distancia getDistancia() {
    return distancia;
  }

  public void setDistancia(Distancia distancia) {
    this.distancia = distancia;
  }

    public String getDireccion_origen() {
        return direccion_origen;
    }

    public void setDireccion_origen(String direccion_origen) {
        this.direccion_origen = direccion_origen;
    }

    public String getDireccion_destino() {
        return direccion_destino;
    }

    public void setDireccion_destino(String direccion_destino) {
        this.direccion_destino = direccion_destino;
    }

    public Timestamp getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Timestamp fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public Timestamp getFecha_llegada() {
        return fecha_llegada;
    }

    public void setFecha_llegada(Timestamp fecha_llegada) {
        this.fecha_llegada = fecha_llegada;
    }

    public Timestamp getFecha_retorno() {
        return fecha_retorno;
    }

    public void setFecha_retorno(Timestamp fecha_retorno) {
        this.fecha_retorno = fecha_retorno;
    }

    public String getRetorno_observacion() {
        return retorno_observacion;
    }

    public void setRetorno_observacion(String retorno_observacion) {
        this.retorno_observacion = retorno_observacion;
    }

    public String getUsuario_solicitante() {
        return usuario_solicitante;
    }

    public void setUsuario_solicitante(String usuario_solicitante) {
        this.usuario_solicitante = usuario_solicitante;
    }

    public AsignacionSolicitud(String direccion_origen, String direccion_destino, Timestamp fecha_salida, Timestamp fecha_llegada, Timestamp fecha_retorno, String retorno_observacion, String usuario_solicitante, Distancia distancia) {
        this.direccion_origen = direccion_origen;
        this.direccion_destino = direccion_destino;
        this.fecha_salida = fecha_salida;
        this.fecha_llegada = fecha_llegada;
        this.fecha_retorno = fecha_retorno;
        this.retorno_observacion = retorno_observacion;
        this.usuario_solicitante = usuario_solicitante;
        this.distancia = distancia;
    }
    
    
}
