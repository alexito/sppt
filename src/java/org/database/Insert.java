package org.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
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
