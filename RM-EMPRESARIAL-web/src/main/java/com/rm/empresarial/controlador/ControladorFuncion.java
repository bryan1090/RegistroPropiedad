package com.rm.empresarial.controlador;


import com.rm.empresarial.modelo.Funcion;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.FuncionServicio;
import com.rm.empresarial.servicio.RolServicio;

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

@Named("funcionController")
@ViewScoped
public class ControladorFuncion implements Serializable {

    @EJB
    private FuncionServicio servicioFuncion;
    private List<Funcion> items = null;
    private Funcion selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorFuncion() {
    }

    public Funcion getSelected() {
        return selected;
    }

    public void setSelected(Funcion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private FuncionServicio getFacade() {
        return servicioFuncion;
    }

    public Funcion prepareCreate() {
        selected = new Funcion();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioFuncion.validarIngresoCrear(selected.getRolId().getRolId(), selected.getFunPrograma())) {
            selected.setFunFHR(Calendar.getInstance().getTime());
            selected.setFunUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("FuncionCreated"));
        } else {
            addErrorMessage("Rol ya asignado a este Programa");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioFuncion.validarIngresoEditar(selected.getFunId(), selected.getRolId().getRolId(), selected.getFunPrograma())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("FuncionUpdated"));
        } else {
            addErrorMessage("Rol ya asignado a este Programa");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("FuncionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Funcion> getItems() throws ServicioExcepcion {
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

    public Funcion getFuncion(java.lang.Long id) {
        return getFacade().find(new Funcion(), id);
    }

//    public List<Funcion> getItemsAvailableSelectMany() throws ServicioExcepcion {
//        return getFacade().listarTodo();
//    }
    public List<Funcion> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Funcion.class)
    public static class FuncionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorFuncion controller = (ControladorFuncion) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "funcionController");
            return controller.getFuncion(getKey(value));
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
            if (object instanceof Funcion) {
                Funcion o = (Funcion) object;
                return getStringKey(o.getFunId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Funcion.class.getName()});
                return null;
            }
        }

    }

}
