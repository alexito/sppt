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
            + " PRSNCDLA=?, PRSNMAIL=?, PRSNTLFN=?, PRSNMVIL=?, clave=?, estado=?, rol=?  WHERE id=?";
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
    psUpdate.setInt(12, usuario.getId());
    
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
    String SQL = "UPDATE solicitud SET id_distancia=?, f_creacion=?, f_salida=?, f_llegada=?,"
            + " direccion_origen=?, direccion_destino=?, hospedaje=?, estado=?, novedades=?, id_usuario_solicita=?, id_usuario_conductor=? WHERE id=?";
    
    int dist_id = Insert.checkExistRelation(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
    
    PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);
    psUpdate.setInt(1, dist_id);
    psUpdate.setTimestamp(2, new java.sql.Timestamp(solicitud.getFCreacion().getTime()));
    psUpdate.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psUpdate.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psUpdate.setString(5, solicitud.getDireccionOrigen());
    psUpdate.setString(6, solicitud.getDireccionDestino());
    psUpdate.setString(7, solicitud.getHospedaje());
    psUpdate.setBoolean(8, solicitud.getEstado());
    psUpdate.setString(9, solicitud.getNovedades());
    psUpdate.setInt(10, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psUpdate.setInt(11, solicitud.getUsuarioByIdUsuarioConductor().getId());
    psUpdate.setInt(12, solicitud.getId());
    
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
