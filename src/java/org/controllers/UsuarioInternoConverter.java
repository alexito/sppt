package org.controllers;
 
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.database.Select;
 
import org.models.Usuario;
 
@FacesConverter("usuarioInternoConverter")
public class UsuarioInternoConverter implements Converter {
  
  private List<Usuario> listaInternos;
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            listaInternos = Select.selectMappedUsuariosExtInt(1);            
            return listaInternos.get(Integer.parseInt(value));
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