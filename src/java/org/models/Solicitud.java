package org.models;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.database.Select;
import org.primefaces.event.SelectEvent;

public class Solicitud implements java.io.Serializable {

    private int id;
    private Date FCreacion;
    private Date FSalida;
    private Date FLlegada;
    private String hospedaje;
    private Boolean estado;
    private Boolean estadoEnfermeria = false;
    private Boolean emergencia = false;
    private Boolean listaAprobador;
    private Boolean es_creador;
    private String novedades;
    private String direccionOrigen;
    private String direccionDestino;
    private Distancia distanciaById;
    private Usuario usuarioByIdUsuarioSolicita;
    private Usuario usuarioByIdUsuarioConductor;
    private Usuario usuarioByIdUsuarioConductor2;
    private Usuario usuarioByIdUsuarioAprobador;
    private Usuario usuarioByIdUsuarioEnfermero;
    private String nuevoUsuarioExterno;
    private String ids_interno;
    private String ids_externo;
    private String ids_interno_retorno;
    private String ids_externo_retorno;
    private String nombres_interno;
    private String nombres_externo;
    private String nombres_interno_retorno;
    private String nombres_externo_retorno;
    private List<Usuario> listaInternosSeleccionados;
    private List<Usuario> listaExternosSeleccionados;
    private List<Usuario> listaInternosSeleccionados_retorno;
    private List<Usuario> listaExternosSeleccionados_retorno;
    private Boolean cancelado = false;
    private Emergencia emergenciaById;
    private String id_solicitud_relacion;
    private String tipo_solicitud_legible;
    private Boolean retorno = false;
    private Date FRetorno;
    private String retornoObservacion = "";
    private String estado_conductor;
//    private List<Solicitud> listaAsignaciones;
    private List<AsignacionSolicitud> lista_asignaciones_conductor = new ArrayList<AsignacionSolicitud>();

    public Solicitud() {
        this.distanciaById = new Distancia();
        this.usuarioByIdUsuarioSolicita = new Usuario();
        this.usuarioByIdUsuarioConductor = new Usuario();
        this.usuarioByIdUsuarioConductor2 = new Usuario();
        this.usuarioByIdUsuarioAprobador = new Usuario();
        this.usuarioByIdUsuarioEnfermero = new Usuario();
        this.emergenciaById = new Emergencia();
        this.listaAprobador = false;
        this.cancelado = false;
        this.listaInternosSeleccionados = new ArrayList<Usuario>();
        this.listaExternosSeleccionados = new ArrayList<Usuario>();
        //lista_asignaciones_conductor.add(new AsignacionSolicitud("ver", "ver", null, null, null, retornoObservacion, estado_conductor));
    }

    public Solicitud(int id) {
        this.id = id;
    }

    public Solicitud(int id, Date FCreacion, Date FSalida, Date FLlegada, String direccionOrigen, String direccionDestino, String hospedaje, Boolean estado, Boolean estadoEnfermeria,
            String novedades, Boolean es_creador, Distancia distanciaById, Usuario usuarioByIdUsuarioSolicita, Usuario usuarioByIdUsuarioConductor, Usuario usuarioByIdUsuarioConductor2, Usuario usuarioByIdUsuarioAprobador, Usuario usuarioByIdUsuarioEnfermero,
            String ids_interno, String ids_externo, Boolean emergencia, Emergencia emergenciaById, Boolean cancelado, String id_solicitud_relacion) {
        this.id = id;
        this.distanciaById = (distanciaById == null) ? new Distancia() : distanciaById;
        this.usuarioByIdUsuarioSolicita = (usuarioByIdUsuarioSolicita == null) ? new Usuario() : usuarioByIdUsuarioSolicita;
        this.usuarioByIdUsuarioConductor = (usuarioByIdUsuarioConductor == null) ? new Usuario() : usuarioByIdUsuarioConductor;
        this.usuarioByIdUsuarioConductor2 = (usuarioByIdUsuarioConductor2 == null) ? new Usuario() : usuarioByIdUsuarioConductor2;
        this.usuarioByIdUsuarioAprobador = (usuarioByIdUsuarioAprobador == null) ? new Usuario() : usuarioByIdUsuarioAprobador;
        this.usuarioByIdUsuarioEnfermero = (usuarioByIdUsuarioEnfermero == null) ? new Usuario() : usuarioByIdUsuarioEnfermero;
        this.emergenciaById = (emergenciaById == null) ? new Emergencia() : emergenciaById;
        this.FCreacion = FCreacion;
        this.FSalida = FSalida;
        this.FLlegada = FLlegada;
        this.direccionOrigen = direccionOrigen;
        this.direccionDestino = direccionDestino;
        this.hospedaje = hospedaje;
        this.estado = estado;
        this.estadoEnfermeria = estadoEnfermeria;
        this.novedades = novedades;
        this.es_creador = es_creador;
        this.listaAprobador = false;
        this.emergencia = emergencia;
        this.cancelado = cancelado;
        this.ids_interno = ids_interno;
        this.ids_externo = ids_externo;
        this.id_solicitud_relacion = id_solicitud_relacion;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Distancia getDistanciaById() {
        return this.distanciaById;
    }

    public void setDistanciaById(Distancia distanciaById) {
        this.distanciaById = distanciaById;
    }

    public Usuario getUsuarioByIdUsuarioSolicita() {
        return this.usuarioByIdUsuarioSolicita;
    }

    public void setUsuarioByIdUsuarioSolicita(Usuario usuarioByIdUsuarioSolicita) {
        this.usuarioByIdUsuarioSolicita = usuarioByIdUsuarioSolicita;
    }

    public Usuario getUsuarioByIdUsuarioConductor() {
        if (this.usuarioByIdUsuarioConductor == null) {
            this.usuarioByIdUsuarioConductor = new Usuario();
        }
        return this.usuarioByIdUsuarioConductor;
    }

    public void setUsuarioByIdUsuarioConductor(Usuario usuarioByIdUsuarioConductor) {
        this.usuarioByIdUsuarioConductor = usuarioByIdUsuarioConductor;
    }

    public Usuario getUsuarioByIdUsuarioAprobador() {
        return this.usuarioByIdUsuarioAprobador;
    }

    public void setUsuarioByIdUsuarioAprobador(Usuario usuarioByIdUsuarioAprobador) {
        this.usuarioByIdUsuarioAprobador = usuarioByIdUsuarioAprobador;
    }

    public Usuario getUsuarioByIdUsuarioEnfermero() {
        return this.usuarioByIdUsuarioEnfermero;
    }

    public void setUsuarioByIdUsuarioEnfermero(Usuario usuarioByIdUsuarioEnfermero) {
        this.usuarioByIdUsuarioEnfermero = usuarioByIdUsuarioEnfermero;
    }

    public Date getFSalida() throws ParseException {
        return this.FSalida;
    }

    public void setFSalida(Date FSalida) {
        this.FSalida = FSalida;
    }

    public Date getFCreacion() {
        return this.FCreacion;
    }

    public void setFCreacion(Date FCreacion) {
        this.FCreacion = FCreacion;
    }

    public Date getFLlegada() {
        return this.FLlegada;
    }

    public void setFLlegada(Date FLlegada) {
        this.FLlegada = FLlegada;
    }

    public String getHospedaje() {
        return this.hospedaje;
    }

    public void setHospedaje(String hospedaje) {
        this.hospedaje = hospedaje;
    }

    public String getDireccionOrigen() {
        return this.direccionOrigen;
    }

    public void setDireccionOrigen(String direccionOrigen) {
        this.direccionOrigen = direccionOrigen;
    }

    public String getDireccionDestino() {
        return this.direccionDestino;
    }

    public void setDireccionDestino(String direccionDestino) {
        this.direccionDestino = direccionDestino;
    }

    public Boolean getListaAprobador() {
        return this.listaAprobador;
    }

    public void setListaAprobador(Boolean listaAprobador) {
        this.listaAprobador = listaAprobador;
    }

    public Boolean getEstado() {
        if (this.estado == null) {
            this.estado = false;
        }
        return this.estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getEstado_legible() {
        return (getEstado()) ? "Aprobado" : "Pendiente";
    }

    public String getEstadoEnfermeria_legible() {
        return (getEstadoEnfermeria()) ? "Aprobado" : "Pendiente";
    }

    public String getEstadoGeneral_legible() {
        return (getCancelado()) ? "Cancelado" : (getEstadoEnfermeria()) ? "Aprobado" : "Pendiente";
    }

    public String getEstadoCancelado_legible() {
        return (getCancelado()) ? "Cancelado" : "Activo";
    }

    public String getRetorno_legible() {
        return (getRetorno()) ? "Si" : "No";
    }

    public Boolean getEstadoEnfermeria() {
        if (this.estadoEnfermeria == null) {
            this.estadoEnfermeria = false;
        }
        return estadoEnfermeria;
    }

    public void setEstadoEnfermeria(Boolean estadoEnfermeria) {
        this.estadoEnfermeria = estadoEnfermeria;
    }

    public Boolean getEmergencia() {
        if (this.emergencia == null) {
            this.emergencia = false;
        }
        return emergencia;
    }

    public void setEmergencia(Boolean emergencia) {
        this.emergencia = emergencia;
    }

    public String getNovedades() {
        return this.novedades;
    }

    public void setNovedades(String novedades) {
        this.novedades = novedades;
    }

    public Boolean getEs_creador() {
        if (this.es_creador == null) {
            this.es_creador = false;
        }
        return es_creador;
    }

    public void setEs_creador(Boolean es_creador) {
        this.es_creador = es_creador;
    }

    public List<Usuario> getListaInternosSeleccionados() {
        if (listaInternosSeleccionados == null) {
            listaInternosSeleccionados = new ArrayList<Usuario>();
        }
        return listaInternosSeleccionados;
    }

    public void setListaInternosSeleccionados(List<Usuario> listaInternosSeleccionados) {
        this.listaInternosSeleccionados = listaInternosSeleccionados;
    }

    public List<Usuario> getListaExternosSeleccionados() {
        if (listaExternosSeleccionados == null) {
            listaExternosSeleccionados = new ArrayList<Usuario>();
        }
        return listaExternosSeleccionados;
    }

    public void setListaExternosSeleccionados(List<Usuario> listaExternosSeleccionados) {
        this.listaExternosSeleccionados = listaExternosSeleccionados;
    }

    public String getNuevoUsuarioExterno() {
        return nuevoUsuarioExterno;
    }

    public void setNuevoUsuarioExterno(String nuevoUsuarioExterno) {
        this.nuevoUsuarioExterno = nuevoUsuarioExterno;
    }

    public String getIds_interno() {
        return ids_interno;
    }

    public void setIds_interno(String ids_interno) {
        this.ids_interno = ids_interno;
    }

    public String getIds_externo() {
        return ids_externo;
    }

    public void setIds_externo(String ids_externo) {
        this.ids_externo = ids_externo;
    }

    public String getNombres_interno() {
        String names = "";
        for (Usuario user : listaInternosSeleccionados) {
            if (!"".equals(names)) {
                names += ", ";
            }
            names += user.getNombrecompleto();
        }
        return names;
    }

    public void setNombres_interno(String nombres_interno) {
        this.nombres_interno = nombres_interno;
    }

    public String getNombres_externo() {
        String names = "";
        for (Usuario user : listaExternosSeleccionados) {
            if (!"".equals(names)) {
                names += ", ";
            }
            names += user.getNombrecompleto();
        }
        return names;
    }

    public void setNombres_externo(String nombres_externo) {
        this.nombres_externo = nombres_externo;
    }

    public String getNombre_solicita() {
        return "";
    }

    public Usuario getUsuarioByIdUsuarioConductor2() {
        return usuarioByIdUsuarioConductor2;
    }

    public void setUsuarioByIdUsuarioConductor2(Usuario usuarioByIdUsuarioConductor2) {
        this.usuarioByIdUsuarioConductor2 = usuarioByIdUsuarioConductor2;
    }

    public Boolean getCancelado() {
        return cancelado;
    }

    public void setCancelado(Boolean cancelado) {
        this.cancelado = cancelado;
    }

    public Emergencia getEmergenciaById() {
        return emergenciaById;
    }

    public void setEmergenciaById(Emergencia emergenciaById) {
        this.emergenciaById = emergenciaById;
    }

    public String getId_solicitud_relacion() {
        if (id_solicitud_relacion == null) {
            id_solicitud_relacion = "";
        }
        return id_solicitud_relacion;
    }

    public void setId_solicitud_relacion(String id_solicitud_relacion) {
        this.id_solicitud_relacion = id_solicitud_relacion;
    }

    public String getTipo_solicitud_legible() {
        if (this.emergencia) {
            return "Emergencia";
        } else {
            return "Normal";
        }
    }

    public Boolean getRetorno() {
        return retorno;
    }

    public void setRetorno(Boolean retorno) {
        this.retorno = retorno;
    }

    public Date getFRetorno() {
        return FRetorno;
    }

    public void setFRetorno(Date FRetorno) {
        this.FRetorno = FRetorno;
    }

    public String getRetornoObservacion() {
        return retornoObservacion;
    }

    public void setRetornoObservacion(String retornoObservacion) {
        this.retornoObservacion = retornoObservacion;
    }

    public void setTipo_solicitud_legible(String tipo_solicitud_legible) {
        this.tipo_solicitud_legible = tipo_solicitud_legible;
    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        estado_conductor = Select.selectEstadoConductor(usuarioByIdUsuarioConductor.getId(), sdf.format(FSalida));
        sdf = new SimpleDateFormat("MM/dd/yyyy");
        estado_conductor += Select.selectNumeroViajesConductor(usuarioByIdUsuarioConductor.getId(), sdf.format(FSalida));
    }

    public void handleEstadoChange() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
//        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        estado_conductor = Select.selectEstadoConductor(usuarioByIdUsuarioConductor.getId(), sdf.format(FSalida));
//        sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf = new SimpleDateFormat("dd/MM/yyyy");
        estado_conductor += Select.selectNumeroViajesConductor(usuarioByIdUsuarioConductor.getId(), sdf.format(FSalida));
//        listaAsignaciones = Select.selectViajesConductor(usuarioByIdUsuarioConductor.getId(), sdf.format(FSalida));
//        lista_asignaciones_conductor=Select.selectViajesConductorNew(usuarioByIdUsuarioConductor.getId(), sdf.format(FSalida));
//        cargar_asignaciones();
        VariablesEstaticas.fecha = sdf.format(FSalida);
        VariablesEstaticas.id_conductor = usuarioByIdUsuarioConductor.getId();
        VariablesEstaticas.obtener_datos = true;
    }

    public String getEstado_conductor() {
        return estado_conductor;
    }

    public void setEstado_conductor(String estado_conductor) {
        this.estado_conductor = estado_conductor;
    }

    public List<AsignacionSolicitud> getLista_asignaciones_conductor() {
        return lista_asignaciones_conductor;
    }

    public void setLista_asignaciones_conductor(List<AsignacionSolicitud> lista_asignaciones_conductor) {
        this.lista_asignaciones_conductor = lista_asignaciones_conductor;
    }

    public String getIds_interno_retorno() {
        return ids_interno_retorno;
    }

    public void setIds_interno_retorno(String ids_interno_retorno) {
        this.ids_interno_retorno = ids_interno_retorno;
    }

    public String getIds_externo_retorno() {
        return ids_externo_retorno;
    }

    public void setIds_externo_retorno(String ids_externo_retorno) {
        this.ids_externo_retorno = ids_externo_retorno;
    }

    public String getNombres_interno_retorno() {
        return nombres_interno_retorno;
    }

    public void setNombres_interno_retorno(String nombres_interno_retorno) {
        this.nombres_interno_retorno = nombres_interno_retorno;
    }

    public String getNombres_externo_retorno() {
        return nombres_externo_retorno;
    }

    public void setNombres_externo_retorno(String nombres_externo_retorno) {
        this.nombres_externo_retorno = nombres_externo_retorno;
    }

    public List<Usuario> getListaInternosSeleccionados_retorno() {
        return listaInternosSeleccionados_retorno;
    }

    public void setListaInternosSeleccionados_retorno(List<Usuario> listaInternosSeleccionados_retorno) {
        this.listaInternosSeleccionados_retorno = listaInternosSeleccionados_retorno;
    }

    public List<Usuario> getListaExternosSeleccionados_retorno() {
        return listaExternosSeleccionados_retorno;
    }

    public void setListaExternosSeleccionados_retorno(List<Usuario> listaExternosSeleccionados_retorno) {
        this.listaExternosSeleccionados_retorno = listaExternosSeleccionados_retorno;
    }

    public void cargar_asignaciones() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        lista_asignaciones_conductor = Select.selectViajesConductorNew(usuarioByIdUsuarioConductor.getId(), sdf.format(FSalida));
    }

    public void ListaAsiganciones() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        extContext.redirect("ac.jspx");
    }

}
