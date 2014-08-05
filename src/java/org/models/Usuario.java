package org.models;

import java.util.HashSet;
import java.util.Set;

public class Usuario  implements java.io.Serializable {


     private int id;
     private String nombre;
     private String apellido;
     private String cedula;
     private String clave;
     private String email;
     private String telefono;
     private Boolean estado;
     private String rol;
     private Set<Solicitud> solicitudsForIdUsuarioSolicita = new HashSet<Solicitud>(0);
     private Set<Solicitud> solicitudsForIdUsuarioCrea = new HashSet<Solicitud>(0);
     private Set<Solicitud> solicitudsForIdUsuarioConductor = new HashSet<Solicitud>(0);

    public Usuario() {
    }

	
    public Usuario(int id, String nombre, String apellido, String rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
    }
    public Usuario(int id, String nombre, String apellido, String cedula, String email, String telefono, Boolean estado, String rol) {
       this.id = id;
       this.nombre = nombre;
       this.apellido = apellido;
       this.cedula = cedula;
       this.email = email;
       this.telefono = telefono;
       this.estado = estado;
       this.rol = rol;
    }
    public Usuario(int id, String nombre, String apellido, String cedula, String clave, String email, String telefono, Boolean estado, String rol, Set<Solicitud> solicitudsForIdUsuarioSolicita, Set<Solicitud> solicitudsForIdUsuarioCrea, Set<Solicitud> solicitudsForIdUsuarioConductor) {
       this.id = id;
       this.nombre = nombre;
       this.apellido = apellido;
       this.cedula = cedula;
       this.clave = clave;
       this.email = email;
       this.telefono = telefono;
       this.estado = estado;
       this.rol = rol;
       this.solicitudsForIdUsuarioSolicita = solicitudsForIdUsuarioSolicita;
       this.solicitudsForIdUsuarioCrea = solicitudsForIdUsuarioCrea;
       this.solicitudsForIdUsuarioConductor = solicitudsForIdUsuarioConductor;
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
    public String getRol() {
        return this.rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Set<Solicitud> getSolicitudsForIdUsuarioSolicita() {
        return this.solicitudsForIdUsuarioSolicita;
    }
    
    public void setSolicitudsForIdUsuarioSolicita(Set<Solicitud> solicitudsForIdUsuarioSolicita) {
        this.solicitudsForIdUsuarioSolicita = solicitudsForIdUsuarioSolicita;
    }
    public Set<Solicitud> getSolicitudsForIdUsuarioCrea() {
        return this.solicitudsForIdUsuarioCrea;
    }
    
    public void setSolicitudsForIdUsuarioCrea(Set<Solicitud> solicitudsForIdUsuarioCrea) {
        this.solicitudsForIdUsuarioCrea = solicitudsForIdUsuarioCrea;
    }
    public Set<Solicitud> getSolicitudsForIdUsuarioConductor() {
        return this.solicitudsForIdUsuarioConductor;
    }
    
    public void setSolicitudsForIdUsuarioConductor(Set<Solicitud> solicitudsForIdUsuarioConductor) {
        this.solicitudsForIdUsuarioConductor = solicitudsForIdUsuarioConductor;
    }
    
    @Override
    public String toString() {
        return nombre + apellido;
    }
}


