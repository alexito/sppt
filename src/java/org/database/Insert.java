package org.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Date;
import static org.database.Select.CloseCurrentConnection;
import org.models.Distancia;
import org.models.Emergencia;
import org.models.Localidad;
import org.models.Solicitud;
import org.models.Usuario;

public class Insert {
  
  public static String InsertNewUsuarioExterno(String nombre) throws SQLException {
    if(nombre.length() < 3)
      return "";
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO usuario (PRSNNMBR, es_interno) VALUES (?,?)";
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);
    psInsert.setString(1, nombre);
    psInsert.setBoolean(2, false);
    
    RunSQL(con, psInsert);
    
    Statement sentence = null;
    ResultSet result = null;
    con = new ConnectDB();
    try {
      SQL = "SELECT id FROM usuario WHERE PRSNNMBR='" + nombre + "'";
      sentence = con.getConnection().createStatement();
      result = sentence.executeQuery(SQL);
      result.next();
      return result.getInt("id") + "";
    } catch (SQLException e) {
    } finally {
      CloseCurrentConnection(sentence, result, con);
    }
    return "";
  }
  
  public static String InsertUsuario(Usuario usuario) throws SQLException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO usuario (EMPLCDGO, EMPLFAPR, PRSNNMBR, PRSNAPLL, PRSNCDLA, clave, PRSNMAIL,"
            + " PRSNTLFN, estado, rol, f_disponible, f_disponible2, observacion, observacion2, es_interno)"
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    java.sql.Timestamp currentDate = new java.sql.Timestamp(new Date().getTime());
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);
    psInsert.setString(1, usuario.getCodemp());
    psInsert.setString(2, usuario.getCodapr());
    psInsert.setString(3, usuario.getNombre());
    psInsert.setString(4, usuario.getApellido());
    psInsert.setString(5, usuario.getCedula());
    psInsert.setString(6, usuario.getClave());
    psInsert.setString(7, usuario.getEmail());
    psInsert.setString(8, usuario.getTelefono());
    psInsert.setBoolean(9, usuario.getEstado());
    psInsert.setString(10, usuario.getRol());
    psInsert.setTimestamp(11, currentDate);
    psInsert.setTimestamp(12, currentDate);
    psInsert.setString(13, usuario.getObservacion());
    psInsert.setString(14, "");
    psInsert.setBoolean(15, usuario.getEsInterno());
    
    return RunSQL(con, psInsert);
    
  }
  
  public static String InsertSolicitud(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO solicitud (id_distancia, f_creacion, f_salida, f_llegada,"
            + " direccion_origen, direccion_destino, estado, estado_enfermeria,"
            + " novedades, id_usuario_solicita, id_usuario_aprobador,"
            + " ids_interno, ids_externo, retorno, f_retorno, retorno_observacion)"
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    int dist_id = checkExistRelation(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
    
    if(dist_id == 0){
      InsertDistanciaNoDistance(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
      dist_id = checkExistRelation(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
    }
    
    int id_aprobador = 0;
    if(solicitud.getUsuarioByIdUsuarioAprobador() == null || solicitud.getUsuarioByIdUsuarioAprobador().getId() == 0){
      id_aprobador = Select.selectUsuarioIDByEMPLFAPR(solicitud.getUsuarioByIdUsuarioSolicita().getCodapr());
      if(id_aprobador == 0){
        id_aprobador = solicitud.getUsuarioByIdUsuarioSolicita().getId();
      }
    }
    else{
      id_aprobador = solicitud.getUsuarioByIdUsuarioAprobador().getId();
    }
    
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);
    psInsert.setInt(1, dist_id);
    psInsert.setTimestamp(2, new java.sql.Timestamp(solicitud.getFCreacion().getTime()));
    psInsert.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psInsert.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psInsert.setString(5, solicitud.getDireccionOrigen());
    psInsert.setString(6, solicitud.getDireccionDestino());
    psInsert.setBoolean(7, solicitud.getEstado());
    psInsert.setBoolean(8, solicitud.getEstadoEnfermeria());
    psInsert.setString(9, solicitud.getNovedades()); 
    psInsert.setInt(10, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psInsert.setInt(11, id_aprobador);
    psInsert.setString(12, solicitud.getIds_interno()); 
    psInsert.setString(13, solicitud.getIds_externo()); 
    psInsert.setBoolean(14, solicitud.getRetorno());
    psInsert.setTimestamp(15, new java.sql.Timestamp(solicitud.getFRetorno().getTime()));
    psInsert.setString(16, solicitud.getRetornoObservacion()); 
    
    return RunSQL(con, psInsert);
    
  }
  
  
  
  public static String InsertSolicitudEmergencia(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO solicitud (id_distancia, f_creacion, f_salida, f_llegada,"
            + " direccion_origen, direccion_destino, estado, estado_enfermeria, emergencia,"
            + " id_tipo_emergencia, novedades, id_usuario_solicita, id_usuario_aprobador,"
            + " ids_interno, ids_externo, retorno, f_retorno, retorno_observacion)"
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    
    int dist_id = checkExistRelation(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
    
    if(dist_id == 0){
      InsertDistanciaNoDistance(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
      dist_id = checkExistRelation(solicitud.getDistanciaById().getLocalidadByIdOrigen().getId(),
            solicitud.getDistanciaById().getLocalidadByIdDestino().getId());
    }
    
    int id_aprobador = 0;
    if(solicitud.getUsuarioByIdUsuarioAprobador() == null || solicitud.getUsuarioByIdUsuarioAprobador().getId() == 0){
      id_aprobador = Select.selectUsuarioIDByEMPLFAPR(solicitud.getUsuarioByIdUsuarioSolicita().getCodapr());
      if(id_aprobador == 0){
        id_aprobador = solicitud.getUsuarioByIdUsuarioSolicita().getId();
      }
    }
    else{
      id_aprobador = solicitud.getUsuarioByIdUsuarioAprobador().getId();
    }
    
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);
    psInsert.setInt(1, dist_id);
    psInsert.setTimestamp(2, new java.sql.Timestamp(solicitud.getFCreacion().getTime()));
    psInsert.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psInsert.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psInsert.setString(5, solicitud.getDireccionOrigen());
    psInsert.setString(6, solicitud.getDireccionDestino());
    psInsert.setBoolean(7, solicitud.getEstado());
    psInsert.setBoolean(8, solicitud.getEstadoEnfermeria());
    psInsert.setBoolean(9, solicitud.getEmergencia());    
    psInsert.setInt(10, solicitud.getEmergenciaById().getId());
    psInsert.setString(11, solicitud.getNovedades()); 
    psInsert.setInt(12, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psInsert.setInt(13, id_aprobador);
    psInsert.setString(14, solicitud.getIds_interno()); 
    psInsert.setString(15, solicitud.getIds_externo()); 
    psInsert.setBoolean(16, solicitud.getRetorno());
    psInsert.setTimestamp(17, new java.sql.Timestamp(solicitud.getFRetorno().getTime()));
    psInsert.setString(18, solicitud.getRetornoObservacion()); 
        
    return RunSQL(con, psInsert);
    
  }
  
  
  public static String InsertLocalidad(Localidad localidad) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO localidad (nombre ) VALUES (?)";
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);    
    psInsert.setString(1, localidad.getNombre());        
    
    return RunSQL(con, psInsert);
    
  }
  
  public static String InsertEmergencia(Emergencia emergencia) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO emergencia (nombre, estado) VALUES (?,?)";
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);    
    psInsert.setString(1, emergencia.getNombre());
    psInsert.setBoolean(2, true);
    
    return RunSQL(con, psInsert);
    
  }
  
  public static void InsertDistancia(Distancia objDis) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO distancia (distancia, id_origen, id_destino) VALUES (?,?,?)";    
    
    if(checkExistRelation(objDis.getLocalidadByIdOrigen().getId(), objDis.getLocalidadByIdDestino().getId()) != 0){
      SQL = "UPDATE distancia SET distancia=? WHERE id_origen=? AND id_destino=?";
      
      PreparedStatement psUpdate = con.getConnection().prepareStatement(SQL);    
      psUpdate.setFloat(1, Float.parseFloat(objDis.getDistancia()));
      psUpdate.setInt(2, objDis.getLocalidadByIdOrigen().getId());
      psUpdate.setInt(3, objDis.getLocalidadByIdDestino().getId());
      
      RunSQL(con, psUpdate);
      
      con = new ConnectDB();
      psUpdate = con.getConnection().prepareStatement(SQL);    
      psUpdate.setFloat(1, Float.parseFloat(objDis.getDistancia()));
      psUpdate.setInt(2, objDis.getLocalidadByIdDestino().getId());
      psUpdate.setInt(3, objDis.getLocalidadByIdOrigen().getId());
      
      RunSQL(con, psUpdate);
      
    }else{
      PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);    
      psInsert.setFloat(1, Float.parseFloat(objDis.getDistancia()));
      psInsert.setInt(2, objDis.getLocalidadByIdOrigen().getId());
      psInsert.setInt(3, objDis.getLocalidadByIdDestino().getId());
      
      RunSQL(con, psInsert);
      
      con = new ConnectDB();
      PreparedStatement psInsert2 = con.getConnection().prepareStatement(SQL);    
      psInsert2.setFloat(1, Float.parseFloat(objDis.getDistancia()));
      psInsert2.setInt(2, objDis.getLocalidadByIdDestino().getId());
      psInsert2.setInt(3, objDis.getLocalidadByIdOrigen().getId());
      
      RunSQL(con, psInsert2);
      
    }   
  }
  
  public static void InsertDistanciaNoDistance(int id_origen, int id_destino) throws SQLException{
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO distancia (distancia, id_origen, id_destino) VALUES (?,?,?)";    
    
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);    
    psInsert.setFloat(1, (float)0.0);
    psInsert.setInt(2, id_origen);
    psInsert.setInt(3, id_destino);

    RunSQL(con, psInsert);

    con = new ConnectDB();
    PreparedStatement psInsert2 = con.getConnection().prepareStatement(SQL);    
    psInsert2.setFloat(1, (float)0.0);
    psInsert2.setInt(2, id_destino);
    psInsert2.setInt(3, id_origen);

    RunSQL(con, psInsert2);
  }
  
  public static int checkExistRelation(int id_origen, int id_destino) throws SQLException{
    ConnectDB con = new ConnectDB();
    String SQL = "SELECT * FROM distancia WHERE id_origen=" + id_origen + " AND id_destino=" + id_destino;

    Statement sentence = con.getConnection().createStatement();
    ResultSet result = sentence.executeQuery(SQL);
    while (result.next()) {
      return result.getInt("id");
    }
    return 0;
  }

  private static String RunSQL(ConnectDB con, PreparedStatement psInsert) {
    
    try {
      String ret = String.valueOf(psInsert.executeUpdate());
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
