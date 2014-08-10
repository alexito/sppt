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

  static Statement sentence;
  static ResultSet result;

  public static String[] Loggin(String user, String password) {
    String[] Datos = new String[6];
    ConnectDB con = new ConnectDB();
    try {
      String SQL = "SELECT id, nombre, apellido, rol FROM dbo.usuario WHERE cedula=" + user + " and clave=" + password + " and estado=1";

      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);
      while (result.next()) {
        Datos[0] = String.valueOf(result.getInt(1));
        Datos[1] = result.getString(2);
        Datos[2] = result.getString(3);
        Datos[3] = result.getString(4);
      }
      return Datos;

    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }
    return Datos;
  }

  public static List<Solicitud> selectSolicitudes() {
    List<Solicitud> listSolicitudes = null;
    ConnectDB con = new ConnectDB();
    try {
      String SQL = " SELECT * FROM dbo.solicitud";

      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);

      listSolicitudes = new ArrayList<Solicitud>();

      while (result.next()) {
        int id = result.getInt("id");
        String origen = result.getString("origen"),
        destino = result.getString("destino");
        Date f_salida = result.getDate("f_salida"),
        f_llegada = result.getDate("f_llegada");
        String hospedaje = result.getString("hospedaje");
        Boolean estado = result.getBoolean("estado");
        String novedades = result.getString("novedades");
        int a = result.getInt("id_usuario_solicita");
        int b = result.getInt("id_usuario_conductor");
        int c = result.getInt("id_usuario_crea");
        Usuario u_solicita = SelectUsuarioById(a);
        Usuario u_conductor = SelectUsuarioById(b);
        Usuario u_crea = SelectUsuarioById(c);
        Solicitud s = new Solicitud(id, origen, destino, f_salida, f_llegada, hospedaje,
                estado, novedades, u_solicita, u_conductor, u_crea);
        listSolicitudes.add(s);
      }
      return listSolicitudes;

    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }

    return listSolicitudes;
  }

  public static Usuario SelectUsuarioById(int id) {
    Usuario usuario = new Usuario();
    ConnectDB con = new ConnectDB();
    try {
      String SQL = " SELECT * FROM dbo.usuario WHERE id=" + id;

      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);

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
      CloseCurrentConnection(sentence, result, con);
    }

    return usuario;
  }

public static List<Usuario> selectUsuarios() {
    List<Usuario> listUsuarios = null;
    ConnectDB con = new ConnectDB();
    try {
      String SQL = " SELECT * FROM usuario ORDER BY apellido ASC";

      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);

      listUsuarios = new ArrayList<Usuario>();

      while (result.next()) {
        Usuario s = new Usuario(result.getInt("id"), result.getString("nombre"), result.getString("apellido"),
        result.getString("cedula"), result.getString("clave"), result.getString("email"), result.getString("telefono"), result.getBoolean("estado"),
        result.getString("rol"));
        listUsuarios.add(s);
      }
      return listUsuarios;

    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }

    return listUsuarios;
  }

  public static void CloseCurrentConnection(Statement sentence, ResultSet result, ConnectDB con){
    if (result != null) {
        try {
          result.close();
        } catch (SQLException e) {
        }
      }
      if (sentence != null) {
        try {
          sentence.close();
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
}
