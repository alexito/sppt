package org.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import org.models.Solicitud;
import org.models.Usuario;

public class Insert {

  public static String InsertUsuario(Usuario usuario) throws SQLException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO usuario ( nombre, apellido, cedula, clave, email, telefono, estado, rol) VALUES (?,?,?,?,?,?,?,?)";
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);
    psInsert.setString(1, usuario.getNombre());
    psInsert.setString(2, usuario.getApellido());
    psInsert.setString(3, usuario.getCedula());
    psInsert.setString(4, usuario.getClave());
    psInsert.setString(5, usuario.getEmail());
    psInsert.setString(6, usuario.getTelefono());
    psInsert.setBoolean(7, usuario.getEstado());
    psInsert.setString(8, usuario.getRol());
    
    return RunSQL(con, psInsert);
    
  }
  
  public static String InsertSolicitud(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO solicitud (origen, destino, f_salida, f_llegada, hospedaje, estado, novedades, id_usuario_solicita, id_usuario_conductor, id_usuario_crea ) VALUES (?,?,?,?,?,?,?,?,?,?)";
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
        
    return RunSQL(con, psUpdate);
    
  }

  private static String RunSQL(ConnectDB con, PreparedStatement psInsert) {
    
    try {
      psInsert.executeUpdate();
      con.getConnection().commit();
      return "Los datos se guardaron Correctamente";
    } catch (SQLException ex) {
      return "No se pudo guardar los datos.";
    } finally {
      if (psInsert != null) {
        try {
          psInsert.close();
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
