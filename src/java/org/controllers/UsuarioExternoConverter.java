package org.controllers;
 
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.database.Select;
 
import org.models.Usuario;
 
@FacesConverter("usuarioExternoConverter")
public class UsuarioExternoConverter implements Converter {
  
  private List<Usuario> listaExternos;
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            listaExternos = Select.selectMappedUsuariosExtInt(0);            
            return listaExternos.get(Integer.parseInt(value));
        }
        else {
            return null;
        }
    }
 
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((Usuario) object).getId());
        }
        else {
            return null;
        }
    }   
}     