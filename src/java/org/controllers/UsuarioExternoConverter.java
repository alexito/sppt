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
  
  private List<Usuario> listaExternos = Select.selectMappedUsuariosExtInt(0);
 
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {           
          for (Usuario user : listaExternos) {
            if (user.getId() == Integer.parseInt(value)) {
              return user;
            }
          }
        }
        else {
            return null;
        }
        return null;
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