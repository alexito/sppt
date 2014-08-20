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
    String SQL = "UPDATE usuario SET nombre=?, apellido=?, cedula=?, clave=?, email=?, telefono=?, estado=?, rol=? WHERE id=?";
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    psUpdate.setString(1, usuario.getNombre());
    psUpdate.setString(2, usuario.getApellido());
    psUpdate.setString(3, usuario.getCedula());
    psUpdate.setString(4, usuario.getClave());
    psUpdate.setString(5, usuario.getEmail());
    psUpdate.setString(6, usuario.getTelefono());
    psUpdate.setBoolean(7, usuario.getEstado());
    psUpdate.setString(8, usuario.getRol());
    psUpdate.setInt(9, usuario.getId());
    
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
  
  public static String UpdateSolicitud(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "UPDATE solicitud SET origen=?, destino=?, f_salida=?, f_llegada=?, hospedaje=?, estado=?, novedades=?, id_usuario_solicita=?, id_usuario_conductor=?, id_usuario_crea=? WHERE id=?";
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    psUpdate.setInt(1, solicitud.getLocalidadByOrigen().getId());
    psUpdate.setInt(2, solicitud.getLocalidadByDestino().getId());
    psUpdate.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psUpdate.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psUpdate.setString(5, solicitud.getHospedaje());
    psUpdate.setBoolean(6, solicitud.getEstado());
    psUpdate.setString(7, solicitud.getNovedades());
    psUpdate.setInt(8, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psUpdate.setInt(9, solicitud.getUsuarioByIdUsuarioConductor().getId());
    psUpdate.setInt(10, solicitud.getUsuarioByIdUsuarioCrea().getId());
    psUpdate.setInt(11, solicitud.getId());
    
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
