/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.database;

import java.sql.CallableStatement;
import java.util.Date;

/**
 *
 * @author User
 */
public class Insert {

    public static String Guardar_Usuario(String Nombre, String Apellido, String Rol, String Cedula, String Password, String Email, String Telefono) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_guardar_usuario (?,?,?,?,?,?,?) }");
            prcProcedimientoAlmacenado.setString(1, Nombre);
            prcProcedimientoAlmacenado.setString(2, Apellido);
            prcProcedimientoAlmacenado.setString(3, Rol);
            prcProcedimientoAlmacenado.setString(4, Cedula);
            prcProcedimientoAlmacenado.setString(5, Password);
            prcProcedimientoAlmacenado.setString(6, Email);
            prcProcedimientoAlmacenado.setString(7, Telefono);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("CIUDAD GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }

    public static String Guardar_Descripcion(String Item, String User_reference, String Descripcion1, String Descripcion2, String DescripcionLarga, String SearchText) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_guardar_descripcion (?,?,?,?,?,?) }");
            prcProcedimientoAlmacenado.setString(1, Item);
            prcProcedimientoAlmacenado.setString(2, User_reference);
            prcProcedimientoAlmacenado.setString(3, Descripcion1);
            prcProcedimientoAlmacenado.setString(4, Descripcion2);
            prcProcedimientoAlmacenado.setString(5, DescripcionLarga);
            prcProcedimientoAlmacenado.setString(6, SearchText);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("DESCRIPCIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }

    public static boolean Guardar_Asignacion(int id_usuario, int id_oq, String Informacion, int reasignacion, Date Death_Line, int semana, int id_estado, int id_prioridad, int id_evento) {
        String error = "";
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_asignar_usuario_responsable (?,?,?,?,?,?,?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_oq);
            prcProcedimientoAlmacenado.setInt(2, id_usuario);
            java.sql.Date sqlDate = new java.sql.Date(Death_Line.getTime());
            prcProcedimientoAlmacenado.setDate(3, sqlDate);
            prcProcedimientoAlmacenado.setString(4, Informacion);
            prcProcedimientoAlmacenado.setInt(5, reasignacion);
            prcProcedimientoAlmacenado.setInt(6, semana);
            prcProcedimientoAlmacenado.setInt(7, id_estado);
            prcProcedimientoAlmacenado.setInt(8, id_prioridad);
            prcProcedimientoAlmacenado.setInt(9, id_evento);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            return true;

        } catch (Exception ex) {
            error = ex.getMessage();
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }

        return false;
    }

    public static String Guardar_Cambio_Estado(String informacion, int id_usuario, int id_oq, int id_estado) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_asignar_estados (?,?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_estado);
            prcProcedimientoAlmacenado.setInt(2, id_oq);
            prcProcedimientoAlmacenado.setInt(3, id_usuario);
            prcProcedimientoAlmacenado.setString(4, informacion);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("ASIGNACIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }

    public static String Guardar_Comentarios(String comentario, int id_usuario, int id_oq) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_agregar_comentario (?,?,?) }");
            prcProcedimientoAlmacenado.setString(1, comentario);
            prcProcedimientoAlmacenado.setInt(2, id_usuario);
            prcProcedimientoAlmacenado.setInt(3, id_oq);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("ASIGNACIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }

    public static String Guardar_Asignacion_Usuario(int id_oq, int id_usuario, String informacion) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_asignar_usuario (?,?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_oq);
            prcProcedimientoAlmacenado.setInt(2, id_usuario);
            prcProcedimientoAlmacenado.setString(3, informacion);
            //Como parametro 1 xq es reaginacion(bool)
            prcProcedimientoAlmacenado.setInt(4, 1);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("ASIGNACIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }

    public static String Guardar_Asignacion_Death_Line(Date Death_Line, String descripcion, int id_oq, int id_usuario) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_agregar_death_line (?,?,?,?) }");
            java.sql.Date sqlDate = new java.sql.Date(Death_Line.getTime());
            prcProcedimientoAlmacenado.setDate(1, sqlDate);
            prcProcedimientoAlmacenado.setString(2, descripcion);
            prcProcedimientoAlmacenado.setInt(3, id_oq);
            prcProcedimientoAlmacenado.setInt(4, id_usuario);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("ASIGNACIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }

    public static String Guardar_Orden_Finalizacion(int id_oq, int orden, String tipo) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_guardar_orden (?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1,orden );
            prcProcedimientoAlmacenado.setInt(2, id_oq);
            prcProcedimientoAlmacenado.setString(3, tipo);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("DESCRIPCIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }

    public static String Guardar_Comentarios_por_orden(String comentario, int id_usuario, float order_number) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_agregar_cometarios_por_orden (?,?,?) }");
            prcProcedimientoAlmacenado.setString(1, comentario);
            prcProcedimientoAlmacenado.setInt(2, id_usuario);
            prcProcedimientoAlmacenado.setFloat(3, order_number);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("ASIGNACIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }

    public static int Guardar_Solicitud(String OQs, int id_Usuario, Date licitacion, String comentario) {
        int resultado = 0;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_guardar_solicitud(?,?,?,?,?) }");
            prcProcedimientoAlmacenado.setString(1, OQs);
            prcProcedimientoAlmacenado.setInt(2, id_Usuario);
            java.sql.Date sqlDate = new java.sql.Date(licitacion.getTime());
            prcProcedimientoAlmacenado.setDate(3, sqlDate);
            prcProcedimientoAlmacenado.setString(4, comentario);
            prcProcedimientoAlmacenado.registerOutParameter(5, java.sql.Types.INTEGER);
            prcProcedimientoAlmacenado.execute();
            resultado = prcProcedimientoAlmacenado.getInt(5);
            prcProcedimientoAlmacenado.close();
//            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            String error = ex.getMessage();
            resultado = -1;
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
//        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
//            resultado = ("DESCRIPCIÓN GUARDADA CORRECTAMENTE");
//        }
        return resultado;
    }
    
        public static int Guardar_inf_adicional_reporte(int id_oq, String informacion, int id_solicitud) {
        int resultado = 0;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_guardar_inf_adicional_reporte(?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1,id_oq);
            prcProcedimientoAlmacenado.setString(2, informacion);
            prcProcedimientoAlmacenado.setInt(3, id_solicitud);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
//            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            String error = ex.getMessage();
            resultado = -1;
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
//        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
//            resultado = ("DESCRIPCIÓN GUARDADA CORRECTAMENTE");
//        }
        return resultado;
    }

    public static String Guardar_Evento(String informacion) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_guardar_evento (?) }");
            prcProcedimientoAlmacenado.setString(1, informacion);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("ASIGNACIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }

    public static String Asignar_Prioridad(int id_oq, int id_prioridad, int id_usuario, String informacion) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_asignar_prioridad (?,?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_oq);
            prcProcedimientoAlmacenado.setInt(2, id_prioridad);
            prcProcedimientoAlmacenado.setInt(3, id_usuario);
            prcProcedimientoAlmacenado.setString(4, informacion);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("ASIGNACIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }
    
        public static String Asignar_Evento(int id_oq, int id_evento, int id_usuario, String informacion) {
        String resultado;
        ConnectDB conexion = new ConnectDB();
        try {
             
            CallableStatement prcProcedimientoAlmacenado = conexion.getConnection().prepareCall("{ call p_asignar_evento (?,?,?,?) }");
            prcProcedimientoAlmacenado.setInt(1, id_oq);
            prcProcedimientoAlmacenado.setInt(2, id_evento);
            prcProcedimientoAlmacenado.setInt(3, id_usuario);
            prcProcedimientoAlmacenado.setString(4, informacion);
            prcProcedimientoAlmacenado.execute();
            prcProcedimientoAlmacenado.close();
            resultado = ("Se guarado correctamente");
        } catch (Exception ex) {
            resultado = (ex.getMessage());
        } finally {
            if (conexion.getConnection() != null) {
                try {
                    conexion.getConnection().close();
                } catch (Exception e) {
                }
            }
        }
        if (resultado.equals("La instrucción no devolvió un conjunto de resultados.")) {
            resultado = ("ASIGNACIÓN GUARDADA CORRECTAMENTE");
        }
        return resultado;
    }
}
