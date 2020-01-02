package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.Sucursal;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.SucursalServicio;
import com.rm.empresarial.servicio.ZonaServicio;

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

@Named("sucursalController")
@ViewScoped
public class ControladorSucursal implements Serializable {

    @EJB
    private SucursalServicio servicioSucursal;
    private List<Sucursal> items = null;
    private Sucursal selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorSucursal() {
    }

    public Sucursal getSelected() {
        return selected;
    }

    public void setSelected(Sucursal selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private SucursalServicio getFacade() {
        return servicioSucursal;
    }

    public Sucursal prepareCreate() {
        selected = new Sucursal();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioSucursal.validarNombreCrear(selected.getSucNombre())) {
            selected.setSucFHR(Calendar.getInstance().getTime());
            selected.setSucUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("SucursalCreated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioSucursal.validarNombreEditar(selected.getSucId(), selected.getSucNombre())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("SucursalUpdated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("SucursalDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Sucursal> getItems() throws ServicioExcepcion {
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

    public Sucursal getSucursal(java.lang.Long id) {
        return getFacade().find(new Sucursal(), id);
    }

    public List<Sucursal> getItemsAvailableSelectMany() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    public List<Sucursal> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Sucursal.class)
    public static class SucursalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorSucursal controller = (ControladorSucursal) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "sucursalController");
            return controller.getSucursal(getKey(value));
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
            if (object instanceof Sucursal) {
                Sucursal o = (Sucursal) object;
                return getStringKey(o.getSucId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Sucursal.class.getName()});
                return null;
            }
        }

    }

}
