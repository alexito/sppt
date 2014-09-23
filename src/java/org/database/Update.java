/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import org.models.Emergencia;
import org.models.Localidad;
import org.models.Solicitud;
import org.models.Usuario;

/**
 *
 * @author User
 */
public class Update {
  
  public static String UpdateUsuario(Usuario usuario) throws SQLException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE usuario SET EMPLCDGO=?, EMPLFAPR=?, PRSNNMBR=?, PRSNAPLL=?,"
            + " PRSNCDLA=?, PRSNMAIL=?, PRSNTLFN=?, PRSNMVIL=?, clave=?, estado=?, rol=?,"
            + " f_disponible=?, observacion=?, es_interno=?  WHERE id=?";
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    psUpdate.setString(1, usuario.getCodemp());
    psUpdate.setString(2, usuario.getCodapr());
    psUpdate.setString(3, usuario.getNombre());
    psUpdate.setString(4, usuario.getApellido());
    psUpdate.setString(5, usuario.getCedula());
    psUpdate.setString(6, usuario.getEmail());
    psUpdate.setString(7, usuario.getTelefono());
    psUpdate.setString(8, usuario.getMovil());
    psUpdate.setString(9, usuario.getClave());
    psUpdate.setBoolean(10, usuario.getEstado());
    psUpdate.setString(11, usuario.getRol());
    psUpdate.setTimestamp(12, new java.sql.Timestamp(usuario.getFDisponible().getTime()));
    psUpdate.setString(13, usuario.getObservacion());
    psUpdate.setBoolean(14, usuario.getEsInterno());
    psUpdate.setInt(15, usuario.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  public static String UpdateUsuarioConductor2(Usuario usuario) throws SQLException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE usuario SET f_disponible=?, observacion=?  WHERE id=?";
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);    
    psUpdate.setTimestamp(1, new java.sql.Timestamp(usuario.getFDisponible2().getTime()));
    psUpdate.setString(2, usuario.getObservacion2());
    psUpdate.setInt(3, usuario.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  public static String UpdateUsuarioConductor(Usuario usuario) throws SQLException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE usuario SET f_disponible=?, observacion=?  WHERE id=?";
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);    
    psUpdate.setTimestamp(1, new java.sql.Timestamp(usuario.getFDisponible().getTime()));
    psUpdate.setString(2, usuario.getObservacion());
    psUpdate.setInt(3, usuario.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  public static String UpdateLocalidad(Localidad localidad) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE localidad SET nombre=? WHERE id=?";
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    
    psUpdate.setString(1, localidad.getNombre());
    psUpdate.setInt(2, localidad.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  public static String UpdateEmergencia(Emergencia emergencia) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE emergencia SET nombre=? WHERE id=?";
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    
    psUpdate.setString(1, emergencia.getNombre());
    psUpdate.setInt(2, emergencia.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  public static String UpdateSolicitudOwner(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE solicitud SET id_distancia=?, f_creacion=?, f_salida=?, f_llegada=?,"
            + " direccion_origen=?, direccion_destino=?, estado=?, estado_enfermeria=?,"
            + " novedades=?, id_usuario_solicita=?, id_usuario_aprobador=?, id_solicitud_relacion=?,"
            + " ids_interno=?, ids_externo=? WHERE id=?";
    
    int dist_id = Insert.checkExistRelation(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
    
    int id_aprobador = 0;
    if(solicitud.getUsuarioByIdUsuarioAprobador() == null){
      id_aprobador = Select.selectUsuarioIDByEMPLFAPR(solicitud.getUsuarioByIdUsuarioSolicita().getCodapr());
      if(id_aprobador == 0){
        id_aprobador = solicitud.getUsuarioByIdUsuarioSolicita().getId();
      }
    }
    else{
      id_aprobador = solicitud.getUsuarioByIdUsuarioAprobador().getId();
    }
    
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    psUpdate.setInt(1, dist_id);
    psUpdate.setTimestamp(2, new java.sql.Timestamp(solicitud.getFCreacion().getTime()));
    psUpdate.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psUpdate.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psUpdate.setString(5, solicitud.getDireccionOrigen());
    psUpdate.setString(6, solicitud.getDireccionDestino());
    psUpdate.setBoolean(7, solicitud.getEstado());
    psUpdate.setBoolean(8, solicitud.getEstadoEnfermeria());
    psUpdate.setString(9, solicitud.getNovedades());
    psUpdate.setInt(10, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psUpdate.setInt(11, id_aprobador);
    psUpdate.setString(12, solicitud.getId_solicitud_relacion());
    psUpdate.setString(13, solicitud.getIds_interno());
    psUpdate.setString(14, solicitud.getIds_externo());
    psUpdate.setInt(15, solicitud.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  
  public static String UpdateSolicitudEmergenciaOwner(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE solicitud SET id_distancia=?, f_creacion=?, f_salida=?, f_llegada=?,"
            + " direccion_origen=?, direccion_destino=?, estado=?, estado_enfermeria=?, emergencia=?,"
            + " id_tipo_emergencia=?, novedades=?, id_usuario_solicita=?,"
            + " id_usuario_aprobador=?, id_solicitud_relacion=? WHERE id=?";
    
    int dist_id = Insert.checkExistRelation(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
    
    int id_aprobador = 0;
    if(solicitud.getUsuarioByIdUsuarioAprobador() == null){
      id_aprobador = Select.selectUsuarioIDByEMPLFAPR(solicitud.getUsuarioByIdUsuarioSolicita().getCodapr());
      if(id_aprobador == 0){
        id_aprobador = solicitud.getUsuarioByIdUsuarioSolicita().getId();
      }
    }
    else{
      id_aprobador = solicitud.getUsuarioByIdUsuarioAprobador().getId();
    }
    
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    psUpdate.setInt(1, dist_id);
    psUpdate.setTimestamp(2, new java.sql.Timestamp(solicitud.getFCreacion().getTime()));
    psUpdate.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psUpdate.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psUpdate.setString(5, solicitud.getDireccionOrigen());
    psUpdate.setString(6, solicitud.getDireccionDestino());
    psUpdate.setBoolean(7, solicitud.getEstado());
    psUpdate.setBoolean(8, solicitud.getEstadoEnfermeria());
    psUpdate.setBoolean(9, solicitud.getEmergencia());
    psUpdate.setInt(10, solicitud.getEmergenciaById().getId());
    psUpdate.setString(11, solicitud.getNovedades());
    psUpdate.setInt(12, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psUpdate.setInt(13, id_aprobador);
    psUpdate.setString(14, solicitud.getId_solicitud_relacion());
    psUpdate.setInt(15, solicitud.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  
  public static String UpdateSolicitud(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE solicitud SET id_distancia=?, f_creacion=?, f_salida=?, f_llegada=?,"
            + " direccion_origen=?, direccion_destino=?, estado=?, estado_enfermeria=?,"
            + " novedades=?, id_usuario_solicita=?, id_usuario_conductor=?,"
            + " id_usuario_aprobador=?, id_usuario_enfermero=?, cancelado=?, id_solicitud_relacion=? WHERE id=?";
    
    int dist_id = Insert.checkExistRelation(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
    
    int id_aprobador = 0;
    if(solicitud.getUsuarioByIdUsuarioAprobador() == null){
      id_aprobador = Select.selectUsuarioIDByEMPLFAPR(solicitud.getUsuarioByIdUsuarioSolicita().getCodapr());
      if(id_aprobador == 0){
        id_aprobador = solicitud.getUsuarioByIdUsuarioSolicita().getId();
      }
    }
    else{
      id_aprobador = solicitud.getUsuarioByIdUsuarioAprobador().getId();
    }
    
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    psUpdate.setInt(1, dist_id);
    psUpdate.setTimestamp(2, new java.sql.Timestamp(solicitud.getFCreacion().getTime()));
    psUpdate.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psUpdate.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psUpdate.setString(5, solicitud.getDireccionOrigen());
    psUpdate.setString(6, solicitud.getDireccionDestino());
    psUpdate.setBoolean(7, solicitud.getEstado());
    psUpdate.setBoolean(8, solicitud.getEstadoEnfermeria());
    psUpdate.setString(9, solicitud.getNovedades());
    psUpdate.setInt(10, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psUpdate.setInt(11, solicitud.getUsuarioByIdUsuarioConductor().getId());
    psUpdate.setInt(12, id_aprobador);
    psUpdate.setInt(13, solicitud.getUsuarioByIdUsuarioEnfermero().getId());
    psUpdate.setBoolean(14, solicitud.getCancelado());
    psUpdate.setString(15, solicitud.getId_solicitud_relacion());
    psUpdate.setInt(16, solicitud.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  public static void UpdateSolicitudRelacion(Solicitud solicitud) throws SQLException, ParseException{
    Solicitud s = Select.selectSolicitudById(Integer.parseInt(solicitud.getId_solicitud_relacion()));
    
    if(!s.getCancelado() && s.getEstado()){
      if(s.getUsuarioByIdUsuarioConductor() != null)
        solicitud.setUsuarioByIdUsuarioConductor(s.getUsuarioByIdUsuarioConductor());
      if(s.getUsuarioByIdUsuarioConductor2() != null)
        solicitud.setUsuarioByIdUsuarioConductor2(s.getUsuarioByIdUsuarioConductor2());
      solicitud.setEstado(true);
      solicitud.setEstadoEnfermeria(true);
    }
    UpdateSolicitud(solicitud);
    UpdateSolicitud(s);
  }
  
  public static String UpdateSolicitudConductor2(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE solicitud SET id_usuario_conductor2=? WHERE id=?";
       
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);   
    psUpdate.setInt(1, solicitud.getUsuarioByIdUsuarioConductor2().getId());
    psUpdate.setInt(2, solicitud.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  public static String UpdateSolicitudCancelar(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE solicitud SET cancelado=? WHERE id=?";
       
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);   
    psUpdate.setBoolean(1, true);
    psUpdate.setInt(2, solicitud.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  
  public static String UpdateSolicitudEmergencia(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE solicitud SET id_distancia=?, f_creacion=?, f_salida=?, f_llegada=?,"
            + " direccion_origen=?, direccion_destino=?, estado=?, estado_enfermeria=?, emergencia=?,"
            + " id_tipo_emergencia=?, novedades=?, id_usuario_solicita=?, id_usuario_conductor=?,"
            + " id_usuario_aprobador=?, id_usuario_enfermero=?, cancelado=?, id_solicitud_relacion=? WHERE id=?";
    
    int dist_id = Insert.checkExistRelation(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
    
    int id_aprobador = 0;
    if(solicitud.getUsuarioByIdUsuarioAprobador() == null){
      id_aprobador = Select.selectUsuarioIDByEMPLFAPR(solicitud.getUsuarioByIdUsuarioSolicita().getCodapr());
      if(id_aprobador == 0){
        id_aprobador = solicitud.getUsuarioByIdUsuarioSolicita().getId();
      }
    }
    else{
      id_aprobador = solicitud.getUsuarioByIdUsuarioAprobador().getId();
    }
    
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    psUpdate.setInt(1, dist_id);
    psUpdate.setTimestamp(2, new java.sql.Timestamp(solicitud.getFCreacion().getTime()));
    psUpdate.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psUpdate.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psUpdate.setString(5, solicitud.getDireccionOrigen());
    psUpdate.setString(6, solicitud.getDireccionDestino());
    psUpdate.setBoolean(7, solicitud.getEstado());
    psUpdate.setBoolean(8, solicitud.getEstadoEnfermeria());
    psUpdate.setBoolean(9, solicitud.getEmergencia());
    psUpdate.setInt(10, solicitud.getEmergenciaById().getId());
    psUpdate.setString(11, solicitud.getNovedades());
    psUpdate.setInt(12, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psUpdate.setInt(13, solicitud.getUsuarioByIdUsuarioConductor().getId());
    psUpdate.setInt(14, id_aprobador);
    psUpdate.setInt(15, solicitud.getUsuarioByIdUsuarioEnfermero().getId());
    psUpdate.setBoolean(16, solicitud.getCancelado());
    psUpdate.setString(17, solicitud.getId_solicitud_relacion());
    psUpdate.setInt(18, solicitud.getId());
    return RunSQL(con, psUpdate);
    
  }
  
  
  private static String RunSQL(ConnectDB con, PreparedStatement psUpdate) {
    
    try {
      psUpdate.executeUpdate();
      con.getConnection().commit();
      return "Los datos se guardaron Correctamente";
    } catch (SQLException ex) {
      return "No se pudo guardar los datos.";
    } finally {
      if (psUpdate != null) {
        try {
          psUpdate.close();
        } catch (SQLException e) {
          return "Ocurrio un error inesperado";
        }
      }
      if (con.getConnection() != null) {
        try {
          con.getConnection().close();
        } catch (SQLException e) {
          return "Ocurrio un error inesperado";
      }
    }    
  }
}
}
