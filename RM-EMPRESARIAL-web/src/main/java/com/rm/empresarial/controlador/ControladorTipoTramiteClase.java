package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.TipoTramiteClase;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoPropiedadClase;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.servicio.TipoPropiedadClaseServicio;
import com.rm.empresarial.servicio.TipoTramiteClaseServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;

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
import lombok.Getter;
import lombok.Setter;

@Named("tipoTramiteClaseController")
@SessionScoped
public class ControladorTipoTramiteClase implements Serializable {

    @EJB
    private TipoTramiteClaseServicio servicioTipoTramiteClase;
    private List<TipoTramiteClase> items = null;
    private TipoTramiteClase selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorTipoTramiteClase() {
    }

    public TipoTramiteClase getSelected() {
        return selected;
    }

    public void setSelected(TipoTramiteClase selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoTramiteClaseServicio getFacade() {
        return servicioTipoTramiteClase;
    }

    public TipoTramiteClase prepareCreate() {
        selected = new TipoTramiteClase();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() throws ServicioExcepcion {
        selected.setTdcFHR(Calendar.getInstance().getTime());
        selected.setTdcUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoTramiteClaseCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() throws ServicioExcepcion {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoTramiteClaseUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoTramiteClaseDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoTramiteClase> getItems() throws ServicioExcepcion {
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

    public TipoTramiteClase getTipoTramiteClase(java.lang.Long id) {
        return getFacade().find(new TipoTramiteClase(), id);
    }

//    public List<TipoTramiteClase> getItemsAvailableSelectMany() throws ServicioExcepcion {
//        return getFacade().listarTodo();
//    }
    public List<TipoTramiteClase> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoTramiteClase.class)
    public static class TipoTramiteClaseControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoTramiteClase controller = (ControladorTipoTramiteClase) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoTramiteClaseController");
            return controller.getTipoTramiteClase(getKey(value));
        }

        Long getKey(String value) {
            Long key;
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
            if (object instanceof TipoTramiteClase) {
                TipoTramiteClase o = (TipoTramiteClase) object;
                return getStringKey(o.getTdcId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoTramiteClase.class.getName()});
                return null;
            }
        }
    }
    
}
