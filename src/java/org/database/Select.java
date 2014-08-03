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

/**
 *
 * @author User
 */
public class Select {

  static Statement sentencia;
  static ResultSet resultado;

  public static String[] Loggin(String user, String password) {
    String[] Datos = new String[6];
    ConnectDB con = new ConnectDB();
    try {
      String SQL = "SELECT id, nombre, apellido, rol FROM dbo.usuario WHERE cedula=" + user + " and clave=" + password + " and estado=1";

      sentencia = con.getConexion().createStatement();
      resultado = sentencia.executeQuery(SQL);
      while (resultado.next()) {
        Datos[0] = String.valueOf(resultado.getInt(1));
        Datos[1] = resultado.getString(2);
        Datos[2] = resultado.getString(3);
        Datos[3] = resultado.getString(4);
      }
      return Datos;

    } catch (SQLException e) {
    } finally {
      if (resultado != null) {
        try {
          resultado.close();
        } catch (SQLException e) {
        }
      }
      if (sentencia != null) {
        try {
          sentencia.close();
        } catch (SQLException e) {
        }
      }
      if (con.getConexion() != null) {
        try {
          con.getConexion().close();
        } catch (SQLException e) {
        }
      }
    }

    return Datos;
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

      sentencia = con.getConexion().createStatement();
      resultado = sentencia.executeQuery(SQL);
      while (resultado.next()) {
        informacion = resultado.getString(1);
      }
      return informacion;

    } catch (Exception e) {
    } finally {
      if (resultado != null) {
        try {
          resultado.close();
        } catch (Exception e) {
        }
      }
      if (sentencia != null) {
        try {
          sentencia.close();
        } catch (Exception e) {
        }
      }
      if (con.getConexion() != null) {
        try {
          con.getConexion().close();
        } catch (Exception e) {
        }
      }
    }

    return informacion;
  }
}
