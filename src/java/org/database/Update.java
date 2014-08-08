/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.database;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
