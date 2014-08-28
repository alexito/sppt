package org.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import org.models.Distancia;
import org.models.Localidad;
import org.models.Solicitud;
import org.models.Usuario;

public class Insert {

  public static String InsertUsuario(Usuario usuario) throws SQLException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO usuario (EMPLCDGO, EMPLFAPR, PRSNNMBR, PRSNAPLL, PRSNCDLA, clave, PRSNMAIL, PRSNTLFN, estado, rol) VALUES (?,?,?,?,?,?,?,?,?,?)";
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
    
    return RunSQL(con, psInsert);
    
  }
  
  public static String InsertSolicitud(Solicitud solicitud) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO solicitud (origen, destino, f_salida, f_llegada, hospedaje, estado, novedades, id_usuario_solicita, id_usuario_conductor, id_usuario_crea ) VALUES (?,?,?,?,?,?,?,?,?,?)";
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);
//    psInsert.setInt(1, solicitud.getLocalidadByOrigen().getId());
//    psInsert.setInt(2, solicitud.getLocalidadByDestino().getId());
    psInsert.setTimestamp(3, new java.sql.Timestamp(solicitud.getFSalida().getTime()));
    psInsert.setTimestamp(4, new java.sql.Timestamp(solicitud.getFLlegada().getTime()));
    psInsert.setString(5, solicitud.getHospedaje());
    psInsert.setBoolean(6, solicitud.getEstado());
    psInsert.setString(7, solicitud.getNovedades());
    psInsert.setInt(8, solicitud.getUsuarioByIdUsuarioSolicita().getId());
    psInsert.setInt(9, solicitud.getUsuarioByIdUsuarioConductor().getId());
//    psInsert.setInt(10, solicitud.getUsuarioByIdUsuarioCrea().getId());
        
    return RunSQL(con, psInsert);
    
  }
  
  public static String InsertLocalidad(Localidad localidad) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO localidad (nombre ) VALUES (?)";
    PreparedStatement psInsert = con.getConnection().prepareStatement(SQL);    
    psInsert.setString(1, localidad.getNombre());        
    
    return RunSQL(con, psInsert);
    
  }
  
  public static String InsertDistancia(Distancia objDis) throws SQLException, ParseException {
    ConnectDB con = new ConnectDB();
    String SQL = "INSERT INTO distancia (distancia, id_origen, id_destino) VALUES (?,?,?)";    
    
    if(checkExistRelation(objDis.getLocalidadByIdOrigen().getId(), objDis.getLocalidadByIdDestino().getId())){
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
    return "ok";    
  }
  
  private static Boolean checkExistRelation(int id_origen, int id_destino) throws SQLException{
    ConnectDB con = new ConnectDB();
    String SQL = "SELECT * FROM distancia WHERE id_origen=" + id_origen + " AND id_destino=" + id_destino;

    Statement sentence = con.getConnection().createStatement();
    ResultSet result = sentence.executeQuery(SQL);
    while (result.next()) {
      return true;
    }
    return false;
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
