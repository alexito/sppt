package org.controllers;

import java.util.List;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import org.database.Select;
import org.models.Solicitud;

@Named(value = "solicitudBean")
@Dependent
public class SolicitudBean {
  private List<Solicitud> listaSolicitudes;

  public List<Solicitud> getListaSolicitudes() {
    return listaSolicitudes;
  }

  public void setListaSolicitudes(List<Solicitud> listaSolicitudes) {
    this.listaSolicitudes = listaSolicitudes;
  }
  public SolicitudBean() {
    listaSolicitudes = Select.selectSolicitudes();
  }
  
}
