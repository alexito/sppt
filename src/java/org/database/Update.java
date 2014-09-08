/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.database;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
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
  
  public static String UpdateLocalidad(Localidad localidad) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE localidad SET nombre=? WHERE id=?";
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    
    psUpdate.setString(1, localidad.getNombre());
    psUpdate.setInt(2, localidad.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  public static String UpdateSolicitudOwner(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE solicitud SET id_distancia=?, f_creacion=?, f_salida=?, f_llegada=?,"
            + " direccion_origen=?, direccion_destino=?, estado=?, estado_enfermeria=?, emergencia=?,"
            + " emergencia_razon=?, novedades=?, id_usuario_solicita=?,"
            + " id_usuario_aprobador=? WHERE id=?";
    
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
    psUpdate.setString(10, solicitud.getEmergenciaRazon());
    psUpdate.setString(11, solicitud.getNovedades());
    psUpdate.setInt(12, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psUpdate.setInt(13, id_aprobador);
    psUpdate.setInt(14, solicitud.getId());
    
    return RunSQL(con, psUpdate);
    
  }
  
  public static String UpdateSolicitud(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE solicitud SET id_distancia=?, f_creacion=?, f_salida=?, f_llegada=?,"
            + " direccion_origen=?, direccion_destino=?, estado=?, estado_enfermeria=?, emergencia=?,"
            + " emergencia_razon=?, novedades=?, id_usuario_solicita=?, id_usuario_conductor=?,"
            + " id_usuario_aprobador=?, id_usuario_enfermero=? WHERE id=?";
    
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
    psUpdate.setString(10, solicitud.getEmergenciaRazon());
    psUpdate.setString(11, solicitud.getNovedades());
    psUpdate.setInt(12, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psUpdate.setInt(13, solicitud.getUsuarioByIdUsuarioConductor().getId());
    psUpdate.setInt(14, id_aprobador);
    psUpdate.setInt(15, solicitud.getUsuarioByIdUsuarioEnfermero().getId());
    psUpdate.setInt(16, solicitud.getId());
    
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
