/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.database;

import com.sun.xml.ws.rx.util.SuspendedFiberStorage;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import org.models.Solicitud;
import org.models.Usuario;

/**
 *
 * @author User
 */
public class Select {

  static Statement sentencia;
  static ResultSet result;

  public static String[] Loggin(String user, String password) {
    String[] Datos = new String[6];
    ConnectDB con = new ConnectDB();
    try {
      String SQL = "SELECT id, nombre, apellido, rol FROM dbo.usuario WHERE cedula=" + user + " and clave=" + password + " and estado=1";

      sentencia = con.getConnection().createStatement();
      result = sentencia.executeQuery(SQL);
      while (result.next()) {
        Datos[0] = String.valueOf(result.getInt(1));
        Datos[1] = result.getString(2);
        Datos[2] = result.getString(3);
        Datos[3] = result.getString(4);
      }
      return Datos;

    } catch (SQLException e) {
    } finally {
      if (result != null) {
        try {
          result.close();
        } catch (SQLException e) {
        }
      }
      if (sentencia != null) {
        try {
          sentencia.close();
        } catch (SQLException e) {
        }
      }
      if (con.getConnection() != null) {
        try {
          con.getConnection().close();
        } catch (SQLException e) {
        }
      }
    }

    return Datos;
  }

  public static List<Solicitud> selectSolicitudes() {
    List<Solicitud> listSolicitudes = null;
    ConnectDB con = new ConnectDB();
    try {
      String SQL = " SELECT * FROM dbo.solicitud";

      sentencia = con.getConnection().createStatement();
      result = sentencia.executeQuery(SQL);
      
      listSolicitudes = new ArrayList<Solicitud>();
      
      while (result.next()) {
//        Usuario u_solicita = SelectUsuarioById(result.getInt("id_usuario_solicita"));
//        Usuario u_conductor = SelectUsuarioById(result.getInt("id_usuario_conductor"));
//        Usuario u_crea = SelectUsuarioById(result.getInt("id_usuario_crea"));
        Usuario u_solicita = null;
        Usuario u_conductor = null;
        Usuario u_crea = null;
        listSolicitudes.add(new Solicitud(result.getInt("id"), result.getString("origen"),
                result.getString("destino"), result.getDate("f_salida"), result.getDate("f_llegada"),
                result.getString("hospedaje"), result.getBoolean("estado"), result.getString("novedades"),
                u_solicita, u_conductor, u_crea));
      }
      return listSolicitudes;

    } catch (SQLException e) {
    } finally {
      if (result != null) {
        try {
          result.close();
        } catch (SQLException e) {
        }
      }
      if (sentencia != null) {
        try {
          sentencia.close();
        } catch (SQLException e) {
        }
      }
      if (con.getConnection() != null) {
        try {
          con.getConnection().close();
        } catch (SQLException e) {
        }
      }
    }

    return listSolicitudes;
  }
  
  public static Usuario SelectUsuarioById(int id) {
    Usuario usuario = new Usuario();
    ConnectDB con = new ConnectDB();
    try {
      String SQL = " SELECT * FROM dbo.usuario WHERE id=" + id;

      sentencia = con.getConnection().createStatement();
      result = sentencia.executeQuery(SQL);
      
      while (result.next()) {
        usuario.setApellido(result.getString("apellido"));
        usuario.setNombre(result.getString("nombre"));
        usuario.setApellido(result.getString("apellido"));
        usuario.setCedula(result.getString("cedula"));
        usuario.setEmail(result.getString("email"));
        usuario.setTelefono(result.getString("telefono"));
        usuario.setEstado(result.getBoolean("estado"));
        usuario.setRol(result.getString("rol"));
      }
      return usuario;

    } catch (SQLException e) {
    } finally {
      if (result != null) {
        try {
          result.close();
        } catch (SQLException e) {
        }
      }
      if (sentencia != null) {
        try {
          sentencia.close();
        } catch (SQLException e) {
        }
      }
      if (con.getConnection() != null) {
        try {
          con.getConnection().close();
        } catch (SQLException e) {
        }
      }
    }

    return usuario;
  }

  public static String get_Informacion_Adicional(int Id_oq, int id_solicitud) {
    String informacion = "";
    ConnectDB con = new ConnectDB();
    try {
      String SQL = "SELECT \n"
              + "  Informacion\n"
              + "FROM \n"
              + "  dbo.InformacionAdicionalOQ\n"
              + "  Where Id_oq=" + Id_oq + " and id_solicitud=" + id_solicitud;

      sentencia = con.getConnection().createStatement();
      result = sentencia.executeQuery(SQL);
      while (result.next()) {
        informacion = result.getString(1);
      }
      return informacion;

    } catch (SQLException e) {
    } finally {
      if (result != null) {
        try {
          result.close();
        } catch (SQLException e) {
        }
      }
      if (sentencia != null) {
        try {
          sentencia.close();
        } catch (SQLException e) {
        }
      }
      if (con.getConnection() != null) {
        try {
          con.getConnection().close();
        } catch (SQLException e) {
        }
      }
    }

    return informacion;
  }
}
