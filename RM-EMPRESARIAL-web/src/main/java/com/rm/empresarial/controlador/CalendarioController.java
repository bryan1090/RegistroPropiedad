package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.Calendario;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.dao.CalendarioDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.CalendarioServicio;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

@Named("calendarioController")
@ViewScoped
public class CalendarioController implements Serializable {

    @EJB
    private CalendarioDao calendarioServicio;
    private List<Calendario> items = null;
    private Calendario selected;
    @Getter
    @Setter
    private Boolean diaFestivo;

    public CalendarioController() {
    }

    public Calendario getSelected() {
        return selected;
    }

    public void setSelected(Calendario selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CalendarioDao getFacade() {
        return calendarioServicio;
    }

    public Calendario prepareCreate() {
        selected = new Calendario();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void prepareEdit() {
        setDiaFestivo(selected.getCldFestivo().equals("S"));
    }

    public void create() {
        if(diaFestivo)
        {
            selected.setCldFestivo("S");
        }
        else{
            selected.setCldFestivo("N");
        }
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CalendarioCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        if(diaFestivo)
        {
            selected.setCldFestivo("S");
        }
        else{
            selected.setCldFestivo("N");
        }
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CalendarioUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CalendarioDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Calendario> getItems() throws ServicioExcepcion {
        if (items == null) {
            items = getFacade().listarTodo();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Calendario getCalendario(java.lang.Long id) {
        return getFacade().find(new Calendario(), id);
    }

//    public List<Calendario> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
//    public List<Calendario> getItemsAvailableSelectOne() {
//        return getFacade().findAll();
//    }
    @FacesConverter(forClass = Calendario.class)
    public static class CalendarioControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CalendarioController controller = (CalendarioController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "calendarioController");
            return controller.getCalendario(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Calendario) {
                Calendario o = (Calendario) object;
                return getStringKey(o.getCldId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Calendario.class.getName()});
                return null;
            }
        }

    }

}
