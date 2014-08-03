/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.database;

import java.sql.CallableStatement;

/**
 *
 * @author User
 */
public class Update {
    
    public static String Actualizar_Usuario(int id_Usuario, String Nombre, String Apellido, String Rol, String Cedula, String Password, String Email, String Telefono) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {            
            CallableStatement prcProcedimientoAlmacenado = conexion.getConexion().prepareCall("{ call p_actualizar_usuario (?,?,?,?,?,?,?,?) }");
            prcProcedimientoAlmacenado.setString(1, Nombre);
            prcProcedimientoAlmacenado.setString(2, Apellido);
            prcProcedimientoAlmacenado.setString(3, Rol);
            prcProcedimientoAlmacenado.setString(4, Cedula);
            prcProcedimientoAlmacenado.setString(5, Password);
            prcProcedimientoAlmacenado.setInt(6, id_Usuario);
            prcProcedimientoAlmacenado.setString(7, Email);
            prcProcedimientoAlmacenado.setString(8, Telefono);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se actualizo correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConexion() != null) {
                try {
                    conexion.getConexion().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("USUARIO ACTULIZADO CORRECTAMENTE");
        }
        return resultado;
    }
    
    public static String Actualizar_Estado_Usuario(int id_Usuario, boolean estado) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConexion().prepareCall("{ call p_actualizar_estado_usuario (?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_Usuario);
            prcProcedimientoAlmacenado.setBoolean(2, estado);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se actualizo correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConexion() != null) {
                try {
                    conexion.getConexion().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("USUARIO ACTULIZADO CORRECTAMENTE");
        }
        return resultado;
    }
    
    public static String Actualizar_Password(int id_Usuario, String contraseña) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConexion().prepareCall("{ call p_cambiar_password(?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_Usuario);
            prcProcedimientoAlmacenado.setString(2, contraseña);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se actualizo correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConexion() != null) {
                try {
                    conexion.getConexion().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("USUARIO ACTUALIZADO CORRECTAMENTE");
        }
        return resultado;
    }
    
    public static String Actualizar_costo(int id_oq, float cantidad, String supplier, float costo) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConexion().prepareCall("{ call p_actualizar_cost(?,?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_oq);
            prcProcedimientoAlmacenado.setFloat(2, cantidad);
            prcProcedimientoAlmacenado.setString(3, supplier);
            prcProcedimientoAlmacenado.setFloat(4, costo);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se actualizo correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConexion() != null) {
                try {
                    conexion.getConexion().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("USUARIO ACTUALIZADO CORRECTAMENTE");
        }
        return resultado;
    }
    
    
     public static String Actualizar_Estados(int id_estado, String descripcion, String descripcion_larga) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConexion().prepareCall("{ call p_actualizar_estados (?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_estado);
            prcProcedimientoAlmacenado.setString(2, descripcion);
            prcProcedimientoAlmacenado.setString(3, descripcion_larga);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se actualizo correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConexion() != null) {
                try {
                    conexion.getConexion().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("USUARIO ACTULIZADO CORRECTAMENTE");
        }
        return resultado;
    }
     
     
     
     public static String Actualizar_Evento(String detalle, int estado, int id_evento) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConexion().prepareCall("{ call p_act_desactivar_evento (?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_evento);
            prcProcedimientoAlmacenado.setString(2, detalle);
            prcProcedimientoAlmacenado.setInt(3, estado);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se actualizo correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConexion() != null) {
                try {
                    conexion.getConexion().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("USUARIO ACTULIZADO CORRECTAMENTE");
        }
        return resultado;
    }
}
