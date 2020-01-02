package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.EMensaje;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.EMensajeServicio;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.inject.Inject;

@Named("eMensajeController")
@SessionScoped
public class ControladorEMensaje implements Serializable {

    @EJB
    private EMensajeServicio servicioEMensaje;
    private List<EMensaje> items = null;
    private EMensaje selected;
    
    @Inject
    DataManagerSesion dataManagerSession;

    public ControladorEMensaje() {
    }

    public EMensaje getSelected() {
        return selected;
    }

    public void setSelected(EMensaje selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EMensajeServicio getFacade() {
        return servicioEMensaje;
    }

    public EMensaje prepareCreate() {
        selected = new EMensaje();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        selected.setEMsgFHR(Calendar.getInstance().getTime());
        selected.setEMsgUser(dataManagerSession.getUsuarioLogeado().getUsuLogin());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EMensajeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EMensajeUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EMensajeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EMensaje> getItems() throws ServicioExcepcion {
        if (items == null) {
       //     items = getFacade().listarTodo();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                 //   getFacade().edit(selected);
                } else {
                 //   getFacade().remove(selected);
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

    public EMensaje getEMensaje(java.lang.Long id) {
        return null;
       // return getFacade().find(new EMensaje(), id);
    }

//    public List<EMensaje> getItemsAvailableSelectMany() throws ServicioExcepcion {
//        return getFacade().listarTodo();
//    }

    public List<EMensaje> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return null;
        //return getFacade().listarTodo();
    }

    @FacesConverter(forClass = EMensaje.class)
    public static class EMensajeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorEMensaje controller = (ControladorEMensaje) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "eMensajeController");
            return controller.getEMensaje(getKey(value));
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
            if (object instanceof EMensaje) {
                EMensaje o = (EMensaje) object;
                return getStringKey(o.getEMsIdSec());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EMensaje.class.getName()});
                return null;
            }
        }

    }

}
