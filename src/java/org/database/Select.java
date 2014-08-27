package org.database;

import static auth.security.managedBean.AuthBean.USER_KEY;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.models.Localidad;
import org.models.Solicitud;
import org.models.Usuario;

public class Select {

  static Statement sentence;
  static ResultSet result;

  public static Usuario Loggin(String user, String password) {
    Usuario usuario = new Usuario();
    ConnectDB con = new ConnectDB();
    try {
      String SQL = "SELECT id, EMPLCDGO, EMPLFAPR, PRSNNMBR, PRSNAPLL, PRSNCDLA, clave, rol FROM usuario WHERE PRSNCDLA=" + user + " and clave=" + password + " and estado=1";

      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);
      while (result.next()) {
        usuario.setId(result.getInt("id"));
        usuario.setCodemp(result.getString("EMPLCDGO"));
        usuario.setCodapr(result.getString("EMPLFAPR"));
        usuario.setNombre(result.getString("PRSNNMBR"));
        usuario.setApellido(result.getString("PRSNAPLL"));
        usuario.setCedula(result.getString("PRSNCDLA"));
        usuario.setClave(result.getString("clave"));
        usuario.setRol(result.getString("rol"));
      }
      return usuario;

    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }
    return usuario;
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
        id_usuarios.put(result.getInt("id_usuario_solicita"), result.getInt("id_usuario_solicita"));
        id_usuarios.put(result.getInt("id_usuario_conductor"), result.getInt("id_usuario_conductor"));
        id_usuarios.put(result.getInt("id_usuario_crea"), result.getInt("id_usuario_crea"));
      }

      map_localidades = selectMappedLocalidades(false, id_localidades);
      map_usuarios = selectMappedUsuarios(false, false, id_usuarios);
      sentence = con.getConnection().createStatement(); 
      result = sentence.executeQuery(SQL);

      while (result.next()) {
        int id = result.getInt("id");
        Timestamp f_salida = result.getTimestamp("f_salida"),
                f_llegada = result.getTimestamp("f_llegada");
        String hospedaje = result.getString("hospedaje");
        Boolean estado = result.getBoolean("estado");
        String novedades = result.getString("novedades");
//        Solicitud s = new Solicitud(id, map_localidades.get(result.getInt("origen")),
//                map_localidades.get(result.getInt("destino")), f_salida, f_llegada, 
//                hospedaje, estado, novedades, map_usuarios.get(result.getInt("id_usuario_solicita")),
//                map_usuarios.get(result.getInt("id_usuario_conductor")),
//                map_usuarios.get(result.getInt("id_usuario_crea")));
//        listSolicitudes.add(s);
      }
      return listSolicitudes;

    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }

    return listSolicitudes;
  }

  public static Map<Integer, Localidad> selectMappedLocalidades(boolean all, Map<Integer, Integer> ids) {
    ConnectDB con = new ConnectDB();
    Map<Integer, Localidad> response = new HashMap<Integer, Localidad>();    
    String SQL = "SELECT * FROM localidad WHERE";
    
    boolean ban = false;
    ResultSet res = null;
    Statement sent = null;
    try {
      if(!all){
        Iterator iterator = ids.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry mapEntry = (Map.Entry) iterator.next();
          if (!ban) {
            SQL += " id=" + mapEntry.getValue();
            ban = true;
          } else {
            SQL += " OR id=" + mapEntry.getValue();
          }
        }
        SQL += " ORDER BY nombre ASC";
      }else{
        SQL = "SELECT * FROM localidad";
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
  
    public static Map<Integer, Usuario> selectMappedUsuarios(boolean all, boolean isConductor, Map<Integer, Integer> ids) {
    ConnectDB con = new ConnectDB();
    Map<Integer, Usuario> response = new HashMap<Integer, Usuario>();
    
    String SQL = "SELECT * FROM usuario WHERE";
    boolean ban = false;
    ResultSet res = null;
    Statement sent = null;
    try {
      if(!all){
      Iterator iterator = ids.entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry mapEntry = (Map.Entry) iterator.next();
        if (!ban) {
          SQL += " id=" + mapEntry.getValue();
          ban = true;
        } else {
          SQL += " OR id=" + mapEntry.getValue();
        }
      }
      }else{
        if(isConductor)
          SQL = "SELECT * FROM usuario WHERE rol='conductor' AND estado=1";
        else
          SQL = "SELECT * FROM usuario WHERE rol='admin' AND estado=1";
      }
      SQL += " ORDER BY apellido ASC";
      
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
    
  public static Usuario LoggedUser() throws IOException {
    Usuario logged_user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_KEY);
    if(logged_user == null){    
      String url = " ";
      FacesContext context = FacesContext.getCurrentInstance();
      ExternalContext extContext = context.getExternalContext();
      url = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/index.xhtml"));            
      extContext.redirect(url);
    }
    return logged_user;
  }
  public static List<Usuario> selectUsuarios(String filtro) {
    List<Usuario> listUsuarios = null;
    ConnectDB con = new ConnectDB();
    String SQL = "";
    String fields = "";
    if (filtro.equals("conductores")) {
      SQL = " SELECT * FROM usuario WHERE rol = 'conductor' ORDER BY PRSNAPLL ASC";
    } else {
      SQL = " SELECT * FROM usuario WHERE rol = 'super' OR rol = 'admin' ORDER BY PRSNAPLL ASC";
    }
    try {
      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);

      listUsuarios = new ArrayList<Usuario>();

      while (result.next()) {
        Usuario s = new Usuario(result.getInt("id"), result.getString("PRSNNMBR"), result.getString("PRSNAPLL"),
                result.getString("PRSNCDLA"), result.getString("clave"), result.getString("PRSNMAIL"), result.getString("PRSNTLFN"),
                result.getString("PRSNMVIL"), result.getBoolean("estado"), result.getString("rol"), result.getString("EMPLCDGO"), result.getString("EMPLFAPR"));
        listUsuarios.add(s);
      }
      return listUsuarios;

    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }

    return listUsuarios;
  }
  /**
   * Si avoidId = 0 retorna la lista normal de las localidades
   * De lo contrario hace una busqueda y comparacion con datos de distancia
   * @param avoidId
   * @return 
   */
  public static List<Localidad> selectLocalidades(int avoidId) {
    List<Localidad> listLocalidades = null;
    ConnectDB con = new ConnectDB();
    String SQL = " SELECT * FROM localidad WHERE id!=" + avoidId + " ORDER BY nombre ASC";
   
    try {
      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);

      listLocalidades = new ArrayList<Localidad>();
      
      if(avoidId == 0){

        while (result.next()) {
          Localidad s = new Localidad(result.getInt("id"), result.getString("nombre"));
          listLocalidades.add(s);
        }
      return listLocalidades;
      }else{
        Map<Integer, String> dist_values = selectDistanciasById(avoidId);
        int a = 0, b;
        b=a;
//        while (result.next()) {
//          Localidad s = new Localidad(result.getInt("id"), result.getString("nombre"));
//          listLocalidades.add(s);
//        }
      }
        
    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }

    return listLocalidades;
  }
  
  public static Map<Integer, String> selectDistanciasById(int id){
    Map<Integer, String> response = new HashMap<Integer, String>();  
    
    ConnectDB con = new ConnectDB();
    try {
      String SQL = "SELECT id, distancia FROM distancia WHERE id=" + id;

      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);
      while (result.next()) {
        response.put(result.getInt("id"), result.getString("distancia"));        
      }
      return response;
    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }
    
    return response;
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
