package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Descuento;
import com.rm.empresarial.servicio.DescuentoServicio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("controladorDescuento")
@ViewScoped
public class ControladorDescuento implements Serializable {

    @EJB
    private DescuentoServicio servicioDescuento;
    private List<Descuento> items = null;
    private Descuento selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorDescuento() {
    }

    public Descuento getSelected() {
        return selected;
    }

    public void setSelected(Descuento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DescuentoServicio getFacade() {
        return servicioDescuento;
    }

    public Descuento prepareCreate() {
        selected = new Descuento();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear() {
        setSelected(null);
    }

    public void create() {
        selected.setDesFHR(Calendar.getInstance().getTime());
        selected.setDesUsuario(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DescuentoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DescuentoUpdated"));
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DescuentoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Descuento> getItems() throws ServicioExcepcion {
        items = getFacade().listarTodo();
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

    public Descuento getDescuento(java.lang.Long id) {
        return getFacade().find(new Descuento(), id);
    }

    public List<Descuento> getItemsAvailableSelectMany() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    public List<Descuento> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Descuento.class)
    public static class DescuentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorDescuento controller = (ControladorDescuento) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorDescuento");
            return controller.getDescuento(getKey(value));
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
            if (object instanceof Descuento) {
                Descuento o = (Descuento) object;
                return getStringKey(o.getDesId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Descuento.class.getName()});
                return null;
            }
        }

    }

}
