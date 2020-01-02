package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.rm.empresarial.modelo.Caja;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.CajaServicio;
import java.io.Serializable;
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

@Named("cajaController")
@ViewScoped
public class ControladorCaja implements Serializable {

    @EJB
    private CajaServicio servicioCaja;
    private List<Caja> items = null;
    private Caja selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorCaja() {
    }

    public Caja getSelected() {
        return selected;
    }

    public void setSelected(Caja selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CajaServicio getFacade() {
        return servicioCaja;
    }

    public Caja prepareCreate() {
        selected = new Caja();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioCaja.validacionIngresarCrear(selected.getCajNombre(), selected.getSucId().getSucId())) {
            if (servicioCaja.validarNombreCrear(selected.getCajNombre())) {
                selected.setCajFHR(Calendar.getInstance().getTime());
                selected.setCajUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CajaCreated"));
            } else {
                addErrorMessage("El nombre ingresado ya existe");
            }
        } else {
            addErrorMessage("Nombre ya asignado a esta sucursal");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioCaja.validacionIngresarEditar(selected.getCajId(), selected.getCajNombre(), selected.getSucId().getSucId())) {
            if (servicioCaja.validarNombreEditar(selected.getCajId(), selected.getCajNombre())) {
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CajaUpdated"));
            } else {
                addErrorMessage("El nombre ingresado ya existe");
            }
        } else {
            addErrorMessage("Nombre ya asignado a esta sucursal");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CajaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Caja> getItems() throws ServicioExcepcion {
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

    public Caja getCaja(Long id) {
        return getFacade().find(new Caja(), id);
    }

//    public List<Caja> getItemsAvailableSelectMany() {
//        return getFacade().listarTodo();
//    }
    public List<Caja> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Caja.class)
    public static class CajaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorCaja controller = (ControladorCaja) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "cajaController");
            return controller.getCaja(getKey(value));
        }

        Long getKey(String value) {
            Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Caja) {
                Caja o = (Caja) object;
                return getStringKey(o.getCajId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Caja.class.getName()});
                return null;
            }
        }

    }

}
