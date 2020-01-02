package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.UsuarioCaja;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.CajaServicio;
import com.rm.empresarial.servicio.UsuarioCajaServicio;
import com.rm.empresarial.servicio.UsuarioServicio;

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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("usuarioCajaController")
@ViewScoped
public class ControladorUsuarioCaja implements Serializable {

    @EJB
    private UsuarioCajaServicio servicioUsuarioCaja;
    private List<UsuarioCaja> items = null;
    private UsuarioCaja selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorUsuarioCaja() {
    }

    public UsuarioCaja getSelected() {
        return selected;
    }

    public void setSelected(UsuarioCaja selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UsuarioCajaServicio getFacade() {
        return servicioUsuarioCaja;
    }

    public UsuarioCaja prepareCreate() {
        selected = new UsuarioCaja();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioUsuarioCaja.validacionIngresarCrear(selected.getCajId().getCajId(), selected.getUsuId().getUsuId())) {
            selected.setUscFHR(Calendar.getInstance().getTime());
            selected.setUscUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCajaCreated"));
        } else {
            addErrorMessage("Caja ya asignada al usuario");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioUsuarioCaja.validacionIngresarEditar(selected.getUscId(), selected.getCajId().getCajId(), selected.getUsuId().getUsuId())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCajaUpdated"));
        } else {
            addErrorMessage("Caja ya asignada al usuario");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioCajaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UsuarioCaja> getItems() throws ServicioExcepcion {
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

    public UsuarioCaja getUsuarioCaja(java.lang.Long id) {
        return getFacade().find(new UsuarioCaja(), id);
    }

//    public List<UsuarioCaja> getItemsAvailableSelectMany() throws ServicioExcepcion {
//        return getFacade().listarTodo();
//    }
    public List<UsuarioCaja> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = UsuarioCaja.class)
    public static class UsuarioCajaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorUsuarioCaja controller = (ControladorUsuarioCaja) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "usuarioCajaController");
            return controller.getUsuarioCaja(getKey(value));
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
            if (object instanceof UsuarioCaja) {
                UsuarioCaja o = (UsuarioCaja) object;
                return getStringKey(o.getUscId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UsuarioCaja.class.getName()});
                return null;
            }
        }

    }

}
