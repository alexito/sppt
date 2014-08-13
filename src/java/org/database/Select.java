package org.database;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.models.Localidad;
import org.models.Solicitud;
import org.models.Usuario;

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
      String SQL = " SELECT * FROM solicitud";

      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);

      Map<Integer, Integer> id_localidades = new HashMap<Integer, Integer>();
      Map<Integer, Localidad> map_localidades;
      Map<Integer, Integer> id_usuarios = new HashMap<Integer, Integer>();
      Map<Integer, Usuario> map_usuarios;

      listSolicitudes = new ArrayList<Solicitud>();
      while (result.next()) {
        id_localidades.putIfAbsent(result.getInt("origen"), result.getInt("origen"));
        id_localidades.putIfAbsent(result.getInt("destino"), result.getInt("destino"));
        id_usuarios.putIfAbsent(result.getInt("id_usuario_solicita"), result.getInt("id_usuario_solicita"));
        id_usuarios.putIfAbsent(result.getInt("id_usuario_conductor"), result.getInt("id_usuario_conductor"));
        id_usuarios.putIfAbsent(result.getInt("id_usuario_crea"), result.getInt("id_usuario_crea"));
      }

      map_localidades = selectMappedLocalidades(id_localidades);
      map_usuarios = selectMappedUsuarios(id_usuarios);
      sentence = con.getConnection().createStatement(); 
      result = sentence.executeQuery(SQL);

      while (result.next()) {
        int id = result.getInt("id");
        Date f_salida = result.getDate("f_salida"),
                f_llegada = result.getDate("f_llegada");
        String hospedaje = result.getString("hospedaje");
        Boolean estado = result.getBoolean("estado");
        String novedades = result.getString("novedades");
        Solicitud s = new Solicitud(id, map_localidades.get(result.getInt("origen")),
                map_localidades.get(result.getInt("destino")), f_salida, f_llegada, 
                hospedaje, estado, novedades, map_usuarios.get(result.getInt("id_usuario_solicita")),
                map_usuarios.get(result.getInt("id_usuario_conductor")),
                map_usuarios.get(result.getInt("id_usuario_crea")));
        listSolicitudes.add(s);
      }
      return listSolicitudes;

    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }

    return listSolicitudes;
  }

  private static Map<Integer, Localidad> selectMappedLocalidades(Map<Integer, Integer> ids) {
    ConnectDB con = new ConnectDB();
    Map<Integer, Localidad> response = new HashMap<Integer, Localidad>();
    Iterator iterator = ids.entrySet().iterator();
    String SQL = "SELECT * FROM localidad WHERE";
    boolean ban = false;
    ResultSet res = null;
    Statement sent = null;
    try {
      
      while (iterator.hasNext()) {
        Map.Entry mapEntry = (Map.Entry) iterator.next();
        if (!ban) {
          SQL += " id=" + mapEntry.getValue();
          ban = true;
        } else {
          SQL += " OR id=" + mapEntry.getValue();
        }
      }
      
      sent = con.getConnection().createStatement();
      res = sent.executeQuery(SQL);

      while (res.next()) {
        Localidad localidad = new Localidad(res.getInt("id"), res.getString("nombre"));
        response.put(res.getInt("id"), localidad);
      }
      return response;
    } catch (SQLException e) {
    }finally {
      CloseCurrentConnection(sent, res, con);
    }
    return response;
  }
  
    private static Map<Integer, Usuario> selectMappedUsuarios(Map<Integer, Integer> ids) {
    ConnectDB con = new ConnectDB();
    Map<Integer, Usuario> response = new HashMap<Integer, Usuario>();
    Iterator iterator = ids.entrySet().iterator();
    String SQL = "SELECT * FROM usuario WHERE";
    boolean ban = false;
    ResultSet res = null;
    Statement sent = null;
    try {
      
      while (iterator.hasNext()) {
        Map.Entry mapEntry = (Map.Entry) iterator.next();
        if (!ban) {
          SQL += " id=" + mapEntry.getValue();
          ban = true;
        } else {
          SQL += " OR id=" + mapEntry.getValue();
        }
      }
      
      sent = con.getConnection().createStatement();
      res = sent.executeQuery(SQL);

      while (res.next()) {
        Usuario usuario = new Usuario();
        
        usuario.setId(res.getInt("id"));
        usuario.setApellido(res.getString("apellido"));
        usuario.setNombre(res.getString("nombre"));
        usuario.setApellido(res.getString("apellido"));
        usuario.setCedula(res.getString("cedula"));
        usuario.setEmail(res.getString("email"));
        usuario.setTelefono(res.getString("telefono"));
        usuario.setEstado(res.getBoolean("estado"));
        usuario.setRol(res.getString("rol"));
        
        response.put(res.getInt("id"), usuario);
      }
      return response;
    } catch (SQLException e) {
    }finally {
      CloseCurrentConnection(sent, res, con);
    }
    return response;
  }
    
  public static List<Usuario> selectUsuarios(String filtro) {
    List<Usuario> listUsuarios = null;
    ConnectDB con = new ConnectDB();
    String SQL = "";
    if (filtro.equals("conductores")) {
      SQL = " SELECT * FROM usuario WHERE rol = 'conductor' ORDER BY apellido ASC";
    } else {
      SQL = " SELECT * FROM usuario WHERE rol = 'super' OR rol = 'admin' ORDER BY apellido ASC";
    }
    try {
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

  public static void CloseCurrentConnection(Statement sentence, ResultSet result, ConnectDB con) {
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
