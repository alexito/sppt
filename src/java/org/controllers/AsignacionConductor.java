package org.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.database.Select;
import org.models.AsignacionSolicitud;
import org.models.VariablesEstaticas;

@ManagedBean
@RequestScoped
public class AsignacionConductor {

    public AsignacionConductor() {

        lista_asignaciones = Select.selectViajesConductorNew(VariablesEstaticas.id_conductor, VariablesEstaticas.fecha);
        VariablesEstaticas.obtener_datos = false;

    }

    private List<AsignacionSolicitud> lista_asignaciones = new ArrayList<AsignacionSolicitud>();

    public List<AsignacionSolicitud> getLista_asignaciones() {
        return lista_asignaciones;
    }

    public void setLista_asignaciones(List<AsignacionSolicitud> lista_asignaciones) {
        this.lista_asignaciones = lista_asignaciones;
    }

}
