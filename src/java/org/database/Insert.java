package org.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import org.models.Localidad;
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
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);
    psInsert.setInt(1, solicitud.getLocalidadByOrigen().getId());
    psInsert.setInt(2, solicitud.getLocalidadByDestino().getId());
    psInsert.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psInsert.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psInsert.setString(5, solicitud.getHospedaje());
    psInsert.setBoolean(6, solicitud.getEstado());
    psInsert.setString(7, solicitud.getNovedades());
    psInsert.setInt(8, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psInsert.setInt(9, solicitud.getUsuarioByIdUsuarioConductor().getId());
    psInsert.setInt(10, solicitud.getUsuarioByIdUsuarioCrea().getId());
        
    return RunSQL(con, psInsert);
    
  }
  
  public static String InsertLocalidad(Localidad localidad) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO localidad (nombre ) VALUES (?)";
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);    
    psInsert.setString(1, localidad.getNombre());        
    
    return RunSQL(con, psInsert);
    
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
