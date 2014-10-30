package org.database;

import static auth.security.managedBean.AuthBean.USER_KEY;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.models.AsignacionSolicitud;
import org.models.Distancia;
import org.models.Emergencia;
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
            String SQL = "SELECT id, EMPLCDGO, EMPLFAPR, PRSNNMBR, PRSNAPLL, PRSNCDLA, clave, rol FROM usuario WHERE PRSNCDLA='" + user + "' and clave='" + password + "' and estado=1";

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

    public static Solicitud selectSolicitudById(int sid) {
        ConnectDB con = new ConnectDB();
        Solicitud s = new Solicitud();
        try {

            String SQL = "SELECT * FROM solicitud WHERE id=" + sid;
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            Map<Integer, Localidad> map_localidades;
            Map<Integer, Integer> id_usuarios = new HashMap<Integer, Integer>();
            Map<Integer, Usuario> map_usuarios;
            Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
            Map<Integer, Distancia> map_distancias;

            while (result.next()) {
                id_usuarios.put(result.getInt("id_usuario_solicita"), result.getInt("id_usuario_solicita"));
                id_usuarios.put(result.getInt("id_usuario_conductor"), result.getInt("id_usuario_conductor"));
                id_usuarios.put(result.getInt("id_usuario_conductor2"), result.getInt("id_usuario_conductor2"));
                id_usuarios.put(result.getInt("id_usuario_aprobador"), result.getInt("id_usuario_aprobador"));
                id_usuarios.put(result.getInt("id_usuario_enfermero"), result.getInt("id_usuario_enfermero"));
                id_distancias.put(result.getInt("id_distancia"), result.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_usuarios = selectMappedUsuarios(false, false, id_usuarios);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            while (result.next()) {
                int id = result.getInt("id");
                Timestamp f_creacion = result.getTimestamp("f_creacion"),
                        f_salida = result.getTimestamp("f_salida"),
                        f_llegada = result.getTimestamp("f_llegada");

                String hospedaje = result.getString("hospedaje"),
                        novedades = result.getString("novedades"),
                        direccionOrigen = result.getString("direccion_origen"),
                        direccionDestino = result.getString("direccion_destino");
                Boolean estado = result.getBoolean("estado"),
                        cancelado = result.getBoolean("cancelado"),
                        estado_enfermeria = result.getBoolean("estado_enfermeria"),
                        es_creador = false,
                        emergencia = result.getBoolean("emergencia");

                int eid = result.getInt("id_tipo_emergencia");
                Emergencia emergencia_tipo = (eid == 0) ? new Emergencia() : selectEmergenciaById(eid).get(0);

                s = new Solicitud(
                        id,
                        f_creacion,
                        f_salida,
                        f_llegada,
                        direccionOrigen,
                        direccionDestino,
                        hospedaje,
                        estado,
                        estado_enfermeria,
                        novedades,
                        es_creador,
                        map_distancias.get(result.getInt("id_distancia")),
                        map_usuarios.get(result.getInt("id_usuario_solicita")),
                        map_usuarios.get(result.getInt("id_usuario_conductor")),
                        map_usuarios.get(result.getInt("id_usuario_conductor2")),
                        map_usuarios.get(result.getInt("id_usuario_aprobador")),
                        map_usuarios.get(result.getInt("id_usuario_enfermero")),
                        result.getString("ids_interno"),
                        result.getString("ids_externo"),
                        emergencia,
                        emergencia_tipo,
                        cancelado,
                        result.getString("id_solicitud_relacion")
                );
                if (result.getBoolean("retorno")) {
                    s.setRetorno(true);
                    s.setFRetorno(result.getTimestamp("f_retorno"));
                    s.setRetornoObservacion(result.getString("retorno_observacion"));
                }
                if (result.getString("ids_interno") != null) {
                    s.setListaInternosSeleccionados(Select.selectUsuariosById(result.getString("ids_interno")));
                } else {
                    s.setListaInternosSeleccionados(new ArrayList<Usuario>());
                }

                if (result.getString("ids_externo") != null) {
                    s.setListaExternosSeleccionados(Select.selectUsuariosById(result.getString("ids_externo")));
                } else {
                    s.setListaExternosSeleccionados(new ArrayList<Usuario>());
                }

            }
            return s;

        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }
        return s;
    }

    public static List<Solicitud> selectMisSolicitudesEmergencia(int uid) {
        List<Solicitud> listSolicitudes = null;
        ConnectDB con = new ConnectDB();
        try {

            String SQL = "SELECT * FROM solicitud WHERE emergencia=1 AND id_usuario_solicita=" + uid + " ORDER BY id DESC";
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            Map<Integer, Localidad> map_localidades;
            Map<Integer, Integer> id_usuarios = new HashMap<Integer, Integer>();
            Map<Integer, Usuario> map_usuarios;
            Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
            Map<Integer, Distancia> map_distancias;

            listSolicitudes = new ArrayList<Solicitud>();
            while (result.next()) {
                id_usuarios.put(result.getInt("id_usuario_solicita"), result.getInt("id_usuario_solicita"));
                id_usuarios.put(result.getInt("id_usuario_conductor"), result.getInt("id_usuario_conductor"));
                id_usuarios.put(result.getInt("id_usuario_conductor2"), result.getInt("id_usuario_conductor2"));
                id_usuarios.put(result.getInt("id_usuario_aprobador"), result.getInt("id_usuario_aprobador"));
                id_usuarios.put(result.getInt("id_usuario_enfermero"), result.getInt("id_usuario_enfermero"));
                id_distancias.put(result.getInt("id_distancia"), result.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_usuarios = selectMappedUsuarios(false, false, id_usuarios);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            while (result.next()) {
                int id = result.getInt("id");
                Timestamp f_creacion = result.getTimestamp("f_creacion"),
                        f_salida = result.getTimestamp("f_salida"),
                        f_llegada = result.getTimestamp("f_llegada");

                String hospedaje = result.getString("hospedaje"),
                        novedades = result.getString("novedades"),
                        direccionOrigen = result.getString("direccion_origen"),
                        direccionDestino = result.getString("direccion_destino");
                Boolean estado = result.getBoolean("estado"),
                        cancelado = result.getBoolean("cancelado"),
                        estado_enfermeria = result.getBoolean("estado_enfermeria"),
                        es_creador = false,
                        emergencia = result.getBoolean("emergencia");

                int eid = result.getInt("id_tipo_emergencia");
                Emergencia emergencia_tipo = (eid == 0) ? new Emergencia() : selectEmergenciaById(eid).get(0);

                if (uid != 0) {
                    es_creador = true;
                }

                Solicitud s = new Solicitud(
                        id,
                        f_creacion,
                        f_salida,
                        f_llegada,
                        direccionOrigen,
                        direccionDestino,
                        hospedaje,
                        estado,
                        estado_enfermeria,
                        novedades,
                        es_creador,
                        map_distancias.get(result.getInt("id_distancia")),
                        map_usuarios.get(result.getInt("id_usuario_solicita")),
                        map_usuarios.get(result.getInt("id_usuario_conductor")),
                        map_usuarios.get(result.getInt("id_usuario_conductor2")),
                        map_usuarios.get(result.getInt("id_usuario_aprobador")),
                        map_usuarios.get(result.getInt("id_usuario_enfermero")),
                        result.getString("ids_interno"),
                        result.getString("ids_externo"),
                        emergencia,
                        emergencia_tipo,
                        cancelado,
                        result.getString("id_solicitud_relacion")
                );
                if (result.getBoolean("retorno")) {
                    s.setRetorno(true);
                    s.setFRetorno(result.getTimestamp("f_retorno"));
                    s.setRetornoObservacion(result.getString("retorno_observacion"));
                }
                if (result.getString("ids_interno") != null) {
                    s.setListaInternosSeleccionados(Select.selectUsuariosById(result.getString("ids_interno")));
                } else {
                    s.setListaInternosSeleccionados(new ArrayList<Usuario>());
                }

                if (result.getString("ids_externo") != null) {
                    s.setListaExternosSeleccionados(Select.selectUsuariosById(result.getString("ids_externo")));
                } else {
                    s.setListaExternosSeleccionados(new ArrayList<Usuario>());
                }

                listSolicitudes.add(s);
            }
            return listSolicitudes;

        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return listSolicitudes;
    }

    public static List<Solicitud> selectMisSolicitudesCanceladas(int uid) {
        List<Solicitud> listSolicitudes = null;
        ConnectDB con = new ConnectDB();
        try {

            String SQL = " SELECT * FROM solicitud  ORDER BY id DESC";
            SQL = " SELECT * FROM solicitud WHERE cancelado=1 AND id_usuario_solicita=" + uid + " ORDER BY id DESC";
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            Map<Integer, Localidad> map_localidades;
            Map<Integer, Integer> id_usuarios = new HashMap<Integer, Integer>();
            Map<Integer, Usuario> map_usuarios;
            Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
            Map<Integer, Distancia> map_distancias;

            listSolicitudes = new ArrayList<Solicitud>();
            while (result.next()) {
                id_usuarios.put(result.getInt("id_usuario_solicita"), result.getInt("id_usuario_solicita"));
                id_usuarios.put(result.getInt("id_usuario_conductor"), result.getInt("id_usuario_conductor"));
                id_usuarios.put(result.getInt("id_usuario_conductor2"), result.getInt("id_usuario_conductor2"));
                id_usuarios.put(result.getInt("id_usuario_aprobador"), result.getInt("id_usuario_aprobador"));
                id_usuarios.put(result.getInt("id_usuario_enfermero"), result.getInt("id_usuario_enfermero"));
                id_distancias.put(result.getInt("id_distancia"), result.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_usuarios = selectMappedUsuarios(false, false, id_usuarios);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            while (result.next()) {
                int id = result.getInt("id");
                Timestamp f_creacion = result.getTimestamp("f_creacion"),
                        f_salida = result.getTimestamp("f_salida"),
                        f_llegada = result.getTimestamp("f_llegada");

                String hospedaje = result.getString("hospedaje"),
                        novedades = result.getString("novedades"),
                        direccionOrigen = result.getString("direccion_origen"),
                        direccionDestino = result.getString("direccion_destino");
                Boolean estado = result.getBoolean("estado"),
                        cancelado = result.getBoolean("cancelado"),
                        estado_enfermeria = result.getBoolean("estado_enfermeria"),
                        es_creador = false,
                        emergencia = result.getBoolean("emergencia");

                int eid = result.getInt("id_tipo_emergencia");
                Emergencia emergencia_tipo = (eid == 0) ? new Emergencia() : selectEmergenciaById(eid).get(0);

                if (uid != 0) {
                    es_creador = true;
                }

                Solicitud s = new Solicitud(
                        id,
                        f_creacion,
                        f_salida,
                        f_llegada,
                        direccionOrigen,
                        direccionDestino,
                        hospedaje,
                        estado,
                        estado_enfermeria,
                        novedades,
                        es_creador,
                        map_distancias.get(result.getInt("id_distancia")),
                        map_usuarios.get(result.getInt("id_usuario_solicita")),
                        map_usuarios.get(result.getInt("id_usuario_conductor")),
                        map_usuarios.get(result.getInt("id_usuario_conductor2")),
                        map_usuarios.get(result.getInt("id_usuario_aprobador")),
                        map_usuarios.get(result.getInt("id_usuario_enfermero")),
                        result.getString("ids_interno"),
                        result.getString("ids_externo"),
                        emergencia,
                        emergencia_tipo,
                        cancelado,
                        result.getString("id_solicitud_relacion")
                );
                if (result.getBoolean("retorno")) {
                    s.setRetorno(true);
                    s.setFRetorno(result.getTimestamp("f_retorno"));
                    s.setRetornoObservacion(result.getString("retorno_observacion"));
                }
                if (result.getString("ids_interno") != null) {
                    s.setListaInternosSeleccionados(Select.selectUsuariosById(result.getString("ids_interno")));
                } else {
                    s.setListaInternosSeleccionados(new ArrayList<Usuario>());
                }

                if (result.getString("ids_externo") != null) {
                    s.setListaExternosSeleccionados(Select.selectUsuariosById(result.getString("ids_externo")));
                } else {
                    s.setListaExternosSeleccionados(new ArrayList<Usuario>());
                }

                listSolicitudes.add(s);
            }
            return listSolicitudes;

        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return listSolicitudes;
    }

    public static List<Solicitud> selectMisSolicitudesAprobadas(int uid) {
        List<Solicitud> listSolicitudes = null;
        ConnectDB con = new ConnectDB();
        try {

            String SQL = " SELECT * FROM solicitud  ORDER BY id DESC";
            SQL = " SELECT * FROM solicitud WHERE estado=1 AND estado_enfermeria=1 AND cancelado=0 AND id_usuario_solicita=" + uid + " ORDER BY id DESC";
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            Map<Integer, Localidad> map_localidades;
            Map<Integer, Integer> id_usuarios = new HashMap<Integer, Integer>();
            Map<Integer, Usuario> map_usuarios;
            Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
            Map<Integer, Distancia> map_distancias;

            listSolicitudes = new ArrayList<Solicitud>();
            while (result.next()) {
                id_usuarios.put(result.getInt("id_usuario_solicita"), result.getInt("id_usuario_solicita"));
                id_usuarios.put(result.getInt("id_usuario_conductor"), result.getInt("id_usuario_conductor"));
                id_usuarios.put(result.getInt("id_usuario_conductor2"), result.getInt("id_usuario_conductor2"));
                id_usuarios.put(result.getInt("id_usuario_aprobador"), result.getInt("id_usuario_aprobador"));
                id_usuarios.put(result.getInt("id_usuario_enfermero"), result.getInt("id_usuario_enfermero"));
                id_distancias.put(result.getInt("id_distancia"), result.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_usuarios = selectMappedUsuarios(false, false, id_usuarios);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            while (result.next()) {
                int id = result.getInt("id");
                Timestamp f_creacion = result.getTimestamp("f_creacion"),
                        f_salida = result.getTimestamp("f_salida"),
                        f_llegada = result.getTimestamp("f_llegada");

                String hospedaje = result.getString("hospedaje"),
                        novedades = result.getString("novedades"),
                        direccionOrigen = result.getString("direccion_origen"),
                        direccionDestino = result.getString("direccion_destino");
                Boolean estado = result.getBoolean("estado"),
                        cancelado = result.getBoolean("cancelado"),
                        estado_enfermeria = result.getBoolean("estado_enfermeria"),
                        es_creador = false,
                        emergencia = result.getBoolean("emergencia");

                int eid = result.getInt("id_tipo_emergencia");
                Emergencia emergencia_tipo = (eid == 0) ? new Emergencia() : selectEmergenciaById(eid).get(0);

                if (uid != 0) {
                    es_creador = true;
                }

                Solicitud s = new Solicitud(
                        id,
                        f_creacion,
                        f_salida,
                        f_llegada,
                        direccionOrigen,
                        direccionDestino,
                        hospedaje,
                        estado,
                        estado_enfermeria,
                        novedades,
                        es_creador,
                        map_distancias.get(result.getInt("id_distancia")),
                        map_usuarios.get(result.getInt("id_usuario_solicita")),
                        map_usuarios.get(result.getInt("id_usuario_conductor")),
                        map_usuarios.get(result.getInt("id_usuario_conductor2")),
                        map_usuarios.get(result.getInt("id_usuario_aprobador")),
                        map_usuarios.get(result.getInt("id_usuario_enfermero")),
                        result.getString("ids_interno"),
                        result.getString("ids_externo"),
                        emergencia,
                        emergencia_tipo,
                        cancelado,
                        result.getString("id_solicitud_relacion")
                );
                if (result.getBoolean("retorno")) {
                    s.setRetorno(true);
                    s.setFRetorno(result.getTimestamp("f_retorno"));
                    s.setRetornoObservacion(result.getString("retorno_observacion"));
                }
                if (result.getString("ids_interno") != null) {
                    s.setListaInternosSeleccionados(Select.selectUsuariosById(result.getString("ids_interno")));
                } else {
                    s.setListaInternosSeleccionados(new ArrayList<Usuario>());
                }

                if (result.getString("ids_externo") != null) {
                    s.setListaExternosSeleccionados(Select.selectUsuariosById(result.getString("ids_externo")));
                } else {
                    s.setListaExternosSeleccionados(new ArrayList<Usuario>());
                }

                listSolicitudes.add(s);
            }
            return listSolicitudes;

        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return listSolicitudes;
    }

    public static List<Solicitud> selectMisSolicitudesPendientes(int uid) {
        List<Solicitud> listSolicitudes = null;
        ConnectDB con = new ConnectDB();
        try {

            String SQL = " SELECT * FROM solicitud WHERE (estado=0 OR estado_enfermeria=0) AND cancelado=0 AND id_usuario_solicita=" + uid + " ORDER BY id DESC";
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            Map<Integer, Localidad> map_localidades;
            Map<Integer, Integer> id_usuarios = new HashMap<Integer, Integer>();
            Map<Integer, Usuario> map_usuarios;
            Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
            Map<Integer, Distancia> map_distancias;

            listSolicitudes = new ArrayList<Solicitud>();
            while (result.next()) {
                id_usuarios.put(result.getInt("id_usuario_solicita"), result.getInt("id_usuario_solicita"));
                id_usuarios.put(result.getInt("id_usuario_conductor"), result.getInt("id_usuario_conductor"));
                id_usuarios.put(result.getInt("id_usuario_conductor2"), result.getInt("id_usuario_conductor2"));
                id_usuarios.put(result.getInt("id_usuario_aprobador"), result.getInt("id_usuario_aprobador"));
                id_usuarios.put(result.getInt("id_usuario_enfermero"), result.getInt("id_usuario_enfermero"));
                id_distancias.put(result.getInt("id_distancia"), result.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_usuarios = selectMappedUsuarios(false, false, id_usuarios);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            while (result.next()) {
                int id = result.getInt("id");
                Timestamp f_creacion = result.getTimestamp("f_creacion"),
                        f_salida = result.getTimestamp("f_salida"),
                        f_llegada = result.getTimestamp("f_llegada");

                String hospedaje = result.getString("hospedaje"),
                        novedades = result.getString("novedades"),
                        direccionOrigen = result.getString("direccion_origen"),
                        direccionDestino = result.getString("direccion_destino");
                Boolean estado = result.getBoolean("estado"),
                        cancelado = result.getBoolean("cancelado"),
                        estado_enfermeria = result.getBoolean("estado_enfermeria"),
                        es_creador = false,
                        emergencia = result.getBoolean("emergencia");

                int eid = result.getInt("id_tipo_emergencia");
                Emergencia emergencia_tipo = (eid == 0) ? new Emergencia() : selectEmergenciaById(eid).get(0);

                if (uid != 0) {
                    es_creador = true;
                }

                Solicitud s = new Solicitud(
                        id,
                        f_creacion,
                        f_salida,
                        f_llegada,
                        direccionOrigen,
                        direccionDestino,
                        hospedaje,
                        estado,
                        estado_enfermeria,
                        novedades,
                        es_creador,
                        map_distancias.get(result.getInt("id_distancia")),
                        map_usuarios.get(result.getInt("id_usuario_solicita")),
                        map_usuarios.get(result.getInt("id_usuario_conductor")),
                        map_usuarios.get(result.getInt("id_usuario_conductor2")),
                        map_usuarios.get(result.getInt("id_usuario_aprobador")),
                        map_usuarios.get(result.getInt("id_usuario_enfermero")),
                        result.getString("ids_interno"),
                        result.getString("ids_externo"),
                        emergencia,
                        emergencia_tipo,
                        cancelado,
                        result.getString("id_solicitud_relacion")
                );
                if (result.getBoolean("retorno")) {
                    s.setRetorno(true);
                    s.setFRetorno(result.getTimestamp("f_retorno"));
                    s.setRetornoObservacion(result.getString("retorno_observacion"));
                }
                if (result.getString("ids_interno") != null) {
                    s.setListaInternosSeleccionados(Select.selectUsuariosById(result.getString("ids_interno")));
                } else {
                    s.setListaInternosSeleccionados(new ArrayList<Usuario>());
                }

                if (result.getString("ids_externo") != null) {
                    s.setListaExternosSeleccionados(Select.selectUsuariosById(result.getString("ids_externo")));
                } else {
                    s.setListaExternosSeleccionados(new ArrayList<Usuario>());
                }

                listSolicitudes.add(s);
            }
            return listSolicitudes;

        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return listSolicitudes;
    }

    /**
     *
     * @param estadoSolicitud
     * @param uid
     * @param all
     * @return
     */
    public static List<Solicitud> selectSolicitudes(int estadoSolicitud, int uid, boolean all) {
        List<Solicitud> listSolicitudes = null;
        ConnectDB con = new ConnectDB();
        try {

            String SQL = " SELECT * FROM solicitud  ORDER BY id DESC";
            if (!all) {
                if (uid != 0) {
                    SQL = " SELECT * FROM solicitud WHERE estado=" + estadoSolicitud + " AND estado_enfermeria=" + estadoSolicitud + " AND cancelado=0 AND id_usuario_solicita=" + uid + " ORDER BY id DESC";
                } else {
                    SQL = " SELECT * FROM solicitud WHERE estado=" + estadoSolicitud + " AND estado_enfermeria=" + estadoSolicitud + " AND cancelado=0 ORDER BY id ASC";
                }
            }

            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            Map<Integer, Localidad> map_localidades;
            Map<Integer, Integer> id_usuarios = new HashMap<Integer, Integer>();
            Map<Integer, Usuario> map_usuarios;
            Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
            Map<Integer, Distancia> map_distancias;

            listSolicitudes = new ArrayList<Solicitud>();
            while (result.next()) {
                id_usuarios.put(result.getInt("id_usuario_solicita"), result.getInt("id_usuario_solicita"));
                id_usuarios.put(result.getInt("id_usuario_conductor"), result.getInt("id_usuario_conductor"));
                id_usuarios.put(result.getInt("id_usuario_conductor2"), result.getInt("id_usuario_conductor2"));
                id_usuarios.put(result.getInt("id_usuario_aprobador"), result.getInt("id_usuario_aprobador"));
                id_usuarios.put(result.getInt("id_usuario_enfermero"), result.getInt("id_usuario_enfermero"));
                id_distancias.put(result.getInt("id_distancia"), result.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_usuarios = selectMappedUsuarios(false, false, id_usuarios);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            while (result.next()) {
                int id = result.getInt("id");
                Timestamp f_creacion = result.getTimestamp("f_creacion"),
                        f_salida = result.getTimestamp("f_salida"),
                        f_llegada = result.getTimestamp("f_llegada");

                String hospedaje = result.getString("hospedaje"),
                        novedades = result.getString("novedades"),
                        direccionOrigen = result.getString("direccion_origen"),
                        direccionDestino = result.getString("direccion_destino");
                Boolean estado = result.getBoolean("estado"),
                        cancelado = result.getBoolean("cancelado"),
                        estado_enfermeria = result.getBoolean("estado_enfermeria"),
                        es_creador = false,
                        emergencia = result.getBoolean("emergencia");

                int eid = result.getInt("id_tipo_emergencia");
                Emergencia emergencia_tipo = (eid == 0) ? new Emergencia() : selectEmergenciaById(eid).get(0);

                if (uid != 0) {
                    es_creador = true;
                }

                Solicitud s = new Solicitud(
                        id,
                        f_creacion,
                        f_salida,
                        f_llegada,
                        direccionOrigen,
                        direccionDestino,
                        hospedaje,
                        estado,
                        estado_enfermeria,
                        novedades,
                        es_creador,
                        map_distancias.get(result.getInt("id_distancia")),
                        map_usuarios.get(result.getInt("id_usuario_solicita")),
                        map_usuarios.get(result.getInt("id_usuario_conductor")),
                        map_usuarios.get(result.getInt("id_usuario_conductor2")),
                        map_usuarios.get(result.getInt("id_usuario_aprobador")),
                        map_usuarios.get(result.getInt("id_usuario_enfermero")),
                        result.getString("ids_interno"),
                        result.getString("ids_externo"),
                        emergencia,
                        emergencia_tipo,
                        cancelado,
                        result.getString("id_solicitud_relacion")
                );
                if (result.getBoolean("retorno")) {
                    s.setRetorno(true);
                    s.setFRetorno(result.getTimestamp("f_retorno"));
                    s.setRetornoObservacion(result.getString("retorno_observacion"));
                }
                if (result.getString("ids_interno") != null) {
                    s.setListaInternosSeleccionados(Select.selectUsuariosById(result.getString("ids_interno")));
                } else {
                    s.setListaInternosSeleccionados(new ArrayList<Usuario>());
                }

                if (result.getString("ids_externo") != null) {
                    s.setListaExternosSeleccionados(Select.selectUsuariosById(result.getString("ids_externo")));
                } else {
                    s.setListaExternosSeleccionados(new ArrayList<Usuario>());
                }

                listSolicitudes.add(s);
            }
            return listSolicitudes;

        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return listSolicitudes;
    }

    public static List<Solicitud> selectSolicitudesXAprobar(Usuario usuario) {
        List<Solicitud> listSolicitudes = null;
        ConnectDB con = new ConnectDB();
        ResultSet res = null;
        Statement sent = null;
        try {
            String SQL = "";
            if ("enfermero".equals(usuario.getRol())) {
                SQL = " SELECT * FROM solicitud WHERE estado_enfermeria=0 AND cancelado=0 ORDER BY id ASC";
            } else {
                SQL = " SELECT id FROM usuario WHERE EMPLFAPR='" + usuario.getCodemp() + "'";

                sent = con.getConnection().createStatement();
                res = sent.executeQuery(SQL);

                String where = "";
                if (res.next()) {
                    where += " id_usuario_solicita='" + res.getInt("id") + "'";
                    while (res.next()) {
                        where += " OR id_usuario_solicita='" + res.getInt("id") + "'";
                    }
                } else {
                    return listSolicitudes;
                }

                SQL = " SELECT * FROM solicitud WHERE estado=0 AND cancelado=0 AND (" + where + ")";
                SQL += " ORDER BY id DESC";
            }
            con = new ConnectDB();
            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);

            Map<Integer, Localidad> map_localidades;
            Map<Integer, Integer> id_usuarios = new HashMap<Integer, Integer>();
            Map<Integer, Usuario> map_usuarios;
            Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
            Map<Integer, Distancia> map_distancias;

            listSolicitudes = new ArrayList<Solicitud>();
            while (res.next()) {
                id_usuarios.put(res.getInt("id_usuario_solicita"), res.getInt("id_usuario_solicita"));
                id_usuarios.put(res.getInt("id_usuario_conductor"), res.getInt("id_usuario_conductor"));
                id_usuarios.put(res.getInt("id_usuario_conductor2"), res.getInt("id_usuario_conductor2"));
                id_usuarios.put(res.getInt("id_usuario_aprobador"), res.getInt("id_usuario_aprobador"));
                id_usuarios.put(res.getInt("id_usuario_enfermero"), res.getInt("id_usuario_enfermero"));
                id_distancias.put(res.getInt("id_distancia"), res.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_usuarios = selectMappedUsuarios(false, false, id_usuarios);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            con = new ConnectDB();
            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);

            while (res.next()) {
                int id = res.getInt("id");
                Timestamp f_creacion = res.getTimestamp("f_creacion"),
                        f_salida = res.getTimestamp("f_salida"),
                        f_llegada = res.getTimestamp("f_llegada");

                String hospedaje = res.getString("hospedaje"),
                        novedades = res.getString("novedades"),
                        direccionOrigen = res.getString("direccion_origen"),
                        direccionDestino = res.getString("direccion_destino");
                Boolean estado = res.getBoolean("estado"),
                        estado_enfermeria = res.getBoolean("estado_enfermeria"),
                        es_creador = false,
                        cancelado = res.getBoolean("cancelado"),
                        emergencia = res.getBoolean("emergencia");

                int eid = res.getInt("id_tipo_emergencia");
                Emergencia emergencia_tipo = (eid == 0) ? new Emergencia() : selectEmergenciaById(eid).get(0);

                Solicitud s = new Solicitud(
                        id,
                        f_creacion,
                        f_salida,
                        f_llegada,
                        direccionOrigen,
                        direccionDestino,
                        hospedaje,
                        estado,
                        estado_enfermeria,
                        novedades,
                        es_creador,
                        map_distancias.get(res.getInt("id_distancia")),
                        map_usuarios.get(res.getInt("id_usuario_solicita")),
                        map_usuarios.get(res.getInt("id_usuario_conductor")),
                        map_usuarios.get(res.getInt("id_usuario_conductor2")),
                        map_usuarios.get(res.getInt("id_usuario_aprobador")),
                        map_usuarios.get(res.getInt("id_usuario_enfermero")),
                        res.getString("ids_interno"),
                        res.getString("ids_externo"),
                        emergencia,
                        emergencia_tipo,
                        cancelado,
                        res.getString("id_solicitud_relacion")
                );

                s.setListaAprobador(true);

                if (res.getBoolean("retorno")) {
                    s.setRetorno(true);
                    s.setFRetorno(res.getTimestamp("f_retorno"));
                    s.setRetornoObservacion(res.getString("retorno_observacion"));
                }

                if ("enfermero".equals(usuario.getRol())) {
                    s.setListaAprobador(false);
                }

                if (res.getString("ids_interno") != null) {
                    s.setListaInternosSeleccionados(Select.selectUsuariosById(res.getString("ids_interno")));
                } else {
                    s.setListaInternosSeleccionados(new ArrayList<Usuario>());
                }

                if (res.getString("ids_externo") != null) {
                    s.setListaExternosSeleccionados(Select.selectUsuariosById(res.getString("ids_externo")));
                } else {
                    s.setListaExternosSeleccionados(new ArrayList<Usuario>());
                }

                listSolicitudes.add(s);
            }
            return listSolicitudes;

        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sent, res, con);
        }

        return listSolicitudes;
    }

    public static Map<Integer, Distancia> selectMappedDistancias(boolean all, Map<Integer, Integer> ids, Map<Integer, Localidad> mlocalidades) {
        ConnectDB con = new ConnectDB();
        Map<Integer, Distancia> response = new HashMap<Integer, Distancia>();

        String SQL = "SELECT * FROM distancia WHERE";
        boolean ban = false;
        ResultSet res = null;
        Statement sent = null;
        try {
            if (!all) {
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
            } else {
                SQL = "SELECT * FROM distancia";
            }

            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);

            while (res.next()) {
                Distancia distancia = new Distancia();

                distancia.setId(res.getInt("id"));
                distancia.setDistancia(res.getString("distancia"));
                distancia.setLocalidadByIdOrigen(mlocalidades.get(res.getInt("id_origen")));
                distancia.setLocalidadByIdDestino(mlocalidades.get(res.getInt("id_destino")));

                response.put(res.getInt("id"), distancia);
            }
            return response;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sent, res, con);
        }
        return response;
    }

    public static Map<Integer, Localidad> selectMappedLocalidades(boolean all, Map<Integer, Integer> ids) {
        ConnectDB con = new ConnectDB();
        Map<Integer, Localidad> response = new HashMap<Integer, Localidad>();
        String SQL = "SELECT * FROM localidad WHERE";

        boolean ban = false;
        ResultSet res = null;
        Statement sent = null;
        try {
            if (!all) {
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
            } else {
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
        } finally {
            CloseCurrentConnection(sent, res, con);
        }
        return response;
    }

    public static String selectLocalidadNombreById(int lid) throws SQLException {
        ConnectDB con = new ConnectDB();
        String SQL = "SELECT * FROM localidad WHERE id='" + lid + "'";
        Statement sent = con.getConnection().createStatement();
        String response = "";
        ResultSet res = sent.executeQuery(SQL);

        while (res.next()) {
            response = res.getString("nombre");
        }
        return response;

    }

    public static Map<Integer, Usuario> selectMappedConductoresByDate(Date fs, Date fl) {
        ConnectDB con = new ConnectDB();
        Map<Integer, Usuario> response = new HashMap<Integer, Usuario>();
        Timestamp f_salida = new java.sql.Timestamp(fs.getTime());
        Timestamp f_llegada = new java.sql.Timestamp(fl.getTime());

        String SQL = "SELECT id_usuario_conductor FROM solicitud WHERE "
                + "f_salida BETWEEN '" + f_salida + "' AND '" + f_llegada + "' OR "
                + "f_llegada BETWEEN '" + f_salida + "' AND '" + f_llegada + "' OR ("
                + "(f_salida < '" + f_salida + "'  AND f_llegada > '" + f_llegada + "'))";

        ResultSet res = null;
        Statement sent = null;
        try {
            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);
            String where = " id!=0";
            while (res.next()) {
                where += " AND id!=" + res.getInt("id_usuario_conductor");
            }

            SQL = "SELECT * FROM usuario WHERE rol='conductor' AND estado=1 AND (" + where + ")";
            con = new ConnectDB();
            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);

            while (res.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(res.getInt("id"));
                usuario.setCodemp(res.getString("EMPLCDGO"));
                usuario.setCodapr(res.getString("EMPLFAPR"));
                usuario.setApellido(res.getString("PRSNAPLL"));
                usuario.setNombre(res.getString("PRSNNMBR"));
                usuario.setCedula(res.getString("PRSNCDLA"));
                usuario.setEmail(res.getString("PRSNMAIL"));
                usuario.setTelefono(res.getString("PRSNTLFN"));
                usuario.setMovil(res.getString("PRSNMVIL"));
                usuario.setEstado(res.getBoolean("estado"));
                usuario.setRol(res.getString("rol"));

                response.put(res.getInt("id"), usuario);
            }
            return response;
        } catch (SQLException e) {
        } finally {
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
            if (!all) {
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
            } else {
                if (isConductor) {
                    SQL = "SELECT * FROM usuario WHERE rol='conductor' AND estado=1 AND es_interno=1";
                } else {
                    SQL = "SELECT * FROM usuario WHERE rol='admin' AND estado=1 AND es_interno=1";
                }
            }
            SQL += " ORDER BY PRSNAPLL ASC";

            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);

            while (res.next()) {
                Usuario usuario = new Usuario();

                usuario.setId(res.getInt("id"));
                usuario.setCodemp(res.getString("EMPLCDGO"));
                usuario.setCodapr(res.getString("EMPLFAPR"));
                usuario.setApellido(res.getString("PRSNAPLL"));
                usuario.setNombre(res.getString("PRSNNMBR"));
                usuario.setCedula(res.getString("PRSNCDLA"));
                usuario.setEmail(res.getString("PRSNMAIL"));
                usuario.setTelefono(res.getString("PRSNTLFN"));
                usuario.setMovil(res.getString("PRSNMVIL"));
                usuario.setEstado(res.getBoolean("estado"));
                usuario.setEsInterno(res.getBoolean("es_interno"));
                usuario.setFDisponible(res.getTimestamp("f_disponible"));
                usuario.setFDisponible(res.getTimestamp("f_disponible2"));
                usuario.setObservacion(res.getString("observacion"));
                usuario.setObservacion2(res.getString("observacion2"));
                usuario.setRol(res.getString("rol"));

                response.put(res.getInt("id"), usuario);
            }
            return response;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sent, res, con);
        }
        return response;
    }

    /**
     * Id = 0 for all Id = >0 for specific type
     *
     * @param ids
     * @return
     */
    public static List<Emergencia> selectEmergenciaById(int id) {
        ConnectDB con = new ConnectDB();
        List<Emergencia> listEmergencia = null;
        String SQL = "SELECT * FROM emergencia";
        String where = "";
        if (id != 0) {
            where = " WHERE id=" + id;
        }

        SQL += where;

        ResultSet res = null;
        Statement sent = null;
        try {

            SQL += " ORDER BY nombre ASC";

            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);
            listEmergencia = new ArrayList<Emergencia>();
            while (res.next()) {
                Emergencia s = new Emergencia(res.getInt("id"), res.getString("nombre"), res.getBoolean("estado"));
                listEmergencia.add(s);
            }
            return listEmergencia;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sent, res, con);
        }
        return listEmergencia;
    }

    /**
     * Ids can be separated by comma. Eg 24,39,14
     *
     * @param ids
     * @return
     */
    public static List<Usuario> selectUsuariosById(String ids) {
        ConnectDB con = new ConnectDB();
        List<Usuario> listUsuarios = null;

        String[] tem_ids = ids.split(",");
        String where = "";
        for (String id : tem_ids) {
            if (!"".equals(where)) {
                where += " OR ";
            }
            where += "id=" + id;
        }

        String SQL = "SELECT * FROM usuario WHERE " + where;
        ResultSet res = null;
        Statement sent = null;
        try {

            SQL += " ORDER BY PRSNAPLL ASC";

            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);
            listUsuarios = new ArrayList<Usuario>();
            while (res.next()) {
                Usuario s = new Usuario(res.getInt("id"), res.getString("PRSNNMBR"), res.getString("PRSNAPLL"),
                        res.getString("PRSNCDLA"), res.getString("clave"), res.getString("PRSNMAIL"), res.getString("PRSNTLFN"),
                        res.getString("PRSNMVIL"), res.getBoolean("estado"), res.getBoolean("es_interno"), res.getString("observacion"),
                        res.getString("observacion2"), res.getString("rol"), res.getString("EMPLCDGO"), res.getString("EMPLFAPR"),
                        res.getTimestamp("f_disponible"), res.getTimestamp("f_disponible2"));
                listUsuarios.add(s);
            }
            return listUsuarios;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sent, res, con);
        }
        return listUsuarios;
    }

    public static List<Usuario> selectMappedUsuariosExtInt(int es_interno) {
        ConnectDB con = new ConnectDB();
        List<Usuario> listUsuarios = null;

        String SQL = "SELECT * FROM usuario WHERE es_interno=" + es_interno;
        ResultSet res = null;
        Statement sent = null;
        try {

            SQL += " ORDER BY PRSNAPLL ASC";

            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);
            listUsuarios = new ArrayList<Usuario>();
            while (res.next()) {
                Usuario s = new Usuario(res.getInt("id"), res.getString("PRSNNMBR"), res.getString("PRSNAPLL"),
                        res.getString("PRSNCDLA"), res.getString("clave"), res.getString("PRSNMAIL"), res.getString("PRSNTLFN"),
                        res.getString("PRSNMVIL"), res.getBoolean("estado"), res.getBoolean("es_interno"), res.getString("observacion"),
                        res.getString("observacion2"), res.getString("rol"), res.getString("EMPLCDGO"), res.getString("EMPLFAPR"),
                        res.getTimestamp("f_disponible"), res.getTimestamp("f_disponible2"));
                listUsuarios.add(s);
            }
            return listUsuarios;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sent, res, con);
        }
        return listUsuarios;
    }

    public static List<Usuario> selectUsuarios(String filtro) {
        List<Usuario> listUsuarios = null;
        ConnectDB con = new ConnectDB();
        String SQL = "";
        String fields = "";
        if (filtro.equals("enfermeros")) {
            SQL = " SELECT * FROM usuario WHERE rol = 'enfermero' ORDER BY PRSNAPLL ASC";
        } else if (filtro.equals("conductores")) {
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
                        result.getString("PRSNMVIL"), result.getBoolean("estado"), result.getBoolean("es_interno"), result.getString("observacion"),
                        result.getString("observacion2"), result.getString("rol"), result.getString("EMPLCDGO"), result.getString("EMPLFAPR"),
                        result.getTimestamp("f_disponible"), result.getTimestamp("f_disponible2"));
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
     * Si avoidId = 0 retorna la lista normal de las localidades De lo contrario
     * hace una busqueda y comparacion con datos de distancia
     *
     * @param avoidId
     * @return
     */
    public static List<Localidad> selectLocalidades(int avoidId) {
        List<Localidad> listLocalidades = null;
        ConnectDB con = new ConnectDB();
        String SQL = " SELECT * FROM localidad WHERE id!=" + avoidId + " ORDER BY nombre ASC";

        try {
            listLocalidades = new ArrayList<Localidad>();

            if (avoidId == 0) {
                sentence = con.getConnection().createStatement();
                result = sentence.executeQuery(SQL);
                while (result.next()) {
                    Localidad s = new Localidad(result.getInt("id"), result.getString("nombre"));
                    listLocalidades.add(s);
                }
                return listLocalidades;
            } else {
                Map<Integer, String> dist_values = selectDistanciasById(avoidId);
                sentence = con.getConnection().createStatement();
                result = sentence.executeQuery(SQL);
                String distancia;
                while (result.next()) {
                    distancia = dist_values.get(result.getInt("id"));
                    if (distancia == null) {
                        distancia = "0";
                    }
                    Localidad s = new Localidad(result.getInt("id"), result.getString("nombre"), distancia);
                    listLocalidades.add(s);
                }
                return listLocalidades;
            }

        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return listLocalidades;
    }

    public static List<Solicitud> selectSolicitudesDelConductor(int cid) throws SQLException {
        List<Solicitud> lista = null;
        ConnectDB con = new ConnectDB();
        String SQL = "SELECT * FROM solicitud WHERE id_usuario_conductor='" + cid + "' OR id_usuario_conductor2='" + cid + "' ORDER BY f_salida DESC";

        Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
        Map<Integer, Distancia> map_distancias;
        Map<Integer, Integer> id_usuarios = new HashMap<Integer, Integer>();
        Map<Integer, Usuario> map_usuarios;
        Map<Integer, Localidad> map_localidades;

        try {
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);
            while (result.next()) {
                id_usuarios.put(result.getInt("id_usuario_solicita"), result.getInt("id_usuario_solicita"));
                id_distancias.put(result.getInt("id_distancia"), result.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            map_usuarios = selectMappedUsuarios(false, false, id_usuarios);

            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            lista = new ArrayList<Solicitud>();
            while (result.next()) {

                Solicitud s = new Solicitud();

                s.setId(result.getInt("id"));
                s.setUsuarioByIdUsuarioSolicita(map_usuarios.get(result.getInt("id_usuario_solicita")));
                s.setDistanciaById(map_distancias.get(result.getInt("id_distancia")));
                s.setFSalida(result.getTimestamp("f_salida"));
                s.setFLlegada(result.getTimestamp("f_llegada"));
                s.setEstado(result.getBoolean("estado"));
                s.setEstadoEnfermeria(result.getBoolean("estado"));
                s.setCancelado(result.getBoolean("cancelado"));
                s.setEmergencia(result.getBoolean("emergencia"));

                lista.add(s);
            }
            return lista;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return lista;
    }

    public static Map<Integer, String> selectDistanciasById(int id) {
        Map<Integer, String> response = new HashMap<Integer, String>();

        ConnectDB con = new ConnectDB();
        try {
            String SQL = "SELECT id_destino, distancia FROM distancia WHERE id_origen=" + id;

            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);
            while (result.next()) {
                response.put(result.getInt("id_destino"), result.getString("distancia"));
            }
            return response;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return response;
    }

    public static Integer selectUsuarioIDByEMPLFAPR(String emplcdgo) {

        ConnectDB con = new ConnectDB();
        int id = 0;
        try {
            String SQL = "SELECT id FROM usuario WHERE EMPLCDGO='" + emplcdgo + "'";

            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);
            while (result.next()) {
                id = result.getInt("id");
            }
            return id;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return id;
    }

    public static String selectUsuarioEmailById(int uid) {

        ConnectDB con = new ConnectDB();
        String email = "";
        try {
            String SQL = "SELECT PRSNMAIL FROM usuario WHERE id='" + uid + "'";

            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);
            while (result.next()) {
                email = result.getString("PRSNMAIL");
            }
            return email;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return email;
    }

    public static Map<Integer, Emergencia> selectMappedEmergencia() {
        ConnectDB con = new ConnectDB();
        Map<Integer, Emergencia> response = new HashMap<Integer, Emergencia>();

        String SQL = "Select * from emergencia where estado='True'";
        boolean ban = false;
        ResultSet res = null;
        Statement sent = null;
        try {
            sent = con.getConnection().createStatement();
            res = sent.executeQuery(SQL);

            while (res.next()) {
                Emergencia emergencia = new Emergencia();
                emergencia.setId(res.getInt("id"));
                emergencia.setEstado(true);
                emergencia.setNombre(res.getString("nombre"));
                response.put(res.getInt("id"), emergencia);
            }
            return response;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sent, res, con);
        }
        return response;
    }

    public static Usuario LoggedUser() throws IOException {
        Usuario logged_user = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_KEY);
        if (logged_user == null) {
            String url = " ";
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext extContext = context.getExternalContext();
            url = extContext.encodeActionURL(context.getApplication().getViewHandler().getActionURL(context, "/index.xhtml"));
            extContext.redirect(url);
        }
        return logged_user;
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

    public static String selectEstadoConductor(int id, String fecha) {
        String response = "Disponible";

        ConnectDB con = new ConnectDB();
        try {
            String SQL = "SELECT \n"
                    + "  direccion_origen,\n"
                    + "  direccion_destino,\n"
                    + "  f_salida,\n"
                    + "  f_llegada,\n"
                    + "  f_disponible\n"
                    + " FROM \n"
                    + "  dbo.Asignacion_conductores\n"
                    + " WHERE\n"
                    + " f_salida >='" + fecha + "' and f_disponible>='" + fecha + "' and id_Conductor=" + id;

            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);
            while (result.next()) {
                response = "El conductor tiene un viaje asignado: \n Origen: " + result.getString(1) + "\n Destino: " + result.getString(2) + "\n Fecha Salida: " + result.getString(3) + "\n Fecha Llegada: " + result.getString(4) + "\n Fecha Disponible: " + result.getString(5);
            }

            return response;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return response;
    }

    public static String selectNumeroViajesConductor(int id, String fecha) {
        String response = " \t\n\rViajes: 0";

        ConnectDB con = new ConnectDB();
        try {
            String SQL = "SELECT count(f_salida) as nviajes FROM dbo.Asignacion_conductores WHERE "
                    + " f_salida >='" + fecha + " 00:00:00.0' and f_salida <='" + fecha + " 23:59:00.0' and id_Conductor='" + id + "'";

            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);
            while (result.next()) {
                response = " \t\n\rViajes: " + result.getString("nviajes");
            }

            return response;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return response;
    }

    public static List<Solicitud> selectViajesConductor(int id, String fecha) {

        List<Solicitud> listSolicitudes = null;
        listSolicitudes = new ArrayList<Solicitud>();

        ConnectDB con = new ConnectDB();
        try {
            String SQL = "SELECT * FROM dbo.Asignacion_conductores WHERE "
                    + " f_salida >='" + fecha + " 00:00:00.0' and f_salida <='" + fecha + " 23:59:00.0' and id_Conductor='" + id + "'";

            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            Map<Integer, Localidad> map_localidades;
            Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
            Map<Integer, Distancia> map_distancias;

            listSolicitudes = new ArrayList<Solicitud>();
            while (result.next()) {
                id_distancias.put(result.getInt("id_distancia"), result.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            while (result.next()) {
                Solicitud s = new Solicitud();
                s.setId(result.getInt("id"));
                s.setDistanciaById(map_distancias.get(result.getInt("id_distancia")));
                s.setFSalida(result.getTimestamp("f_salida"));
                s.setFLlegada(result.getTimestamp("f_llegada"));
                s.setEstado(result.getBoolean("estado"));
                listSolicitudes.add(s);
            }

            return listSolicitudes;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return listSolicitudes;
    }

    public static List<AsignacionSolicitud> selectViajesConductorNew(int id, String fecha) {

        List<AsignacionSolicitud> listSolicitudes = null;
        listSolicitudes = new ArrayList<AsignacionSolicitud>();

        ConnectDB con = new ConnectDB();
        try {
            String SQL = "SELECT  direccion_origen,\n"
                    + "  direccion_destino,\n"
                    + "  f_salida,\n"
                    + "  f_llegada,\n"
                    + "  f_retorno,\n"
                    + "  retorno_observacion,\n"
                    + "  PRSNNMBR+' '+  PRSNAPLL,\n"
                    + "  id_distancia\n"
                    + " FROM dbo.Asignacion_conductores WHERE "
                    + " f_salida >='" + fecha + " 00:00:00.0' and f_salida <='" + fecha + " 23:59:00.0' and id_Conductor='" + id + "'";

            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            Map<Integer, Localidad> map_localidades;
            Map<Integer, Integer> id_distancias = new HashMap<Integer, Integer>();
            Map<Integer, Distancia> map_distancias;

            
            while (result.next()) {
                id_distancias.put(result.getInt("id_distancia"), result.getInt("id_distancia"));
            }

            map_localidades = selectMappedLocalidades(true, null);
            map_distancias = selectMappedDistancias(false, id_distancias, map_localidades);
            
            sentence = con.getConnection().createStatement();
            result = sentence.executeQuery(SQL);

            while (result.next()) {
                AsignacionSolicitud new_asignacion=new AsignacionSolicitud(
                        result.getString(1),
                        result.getString(2),
                        result.getTimestamp(3),
                        result.getTimestamp(4),
                        result.getTimestamp(5),
                        result.getString(6),
                        result.getString(7),
                        map_distancias.get(result.getInt("id_distancia")
                        )
                );
                listSolicitudes.add(new_asignacion);
            }

            return listSolicitudes;
        } catch (SQLException e) {
        } finally {
            CloseCurrentConnection(sentence, result, con);
        }

        return listSolicitudes;
    }
}
