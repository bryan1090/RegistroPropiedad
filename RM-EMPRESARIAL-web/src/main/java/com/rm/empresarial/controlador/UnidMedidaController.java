package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.UnidMedida;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.dao.UnidMedidaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.UnidMedidaServicio;

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

@Named("unidMedidaController")
@ViewScoped
public class UnidMedidaController implements Serializable {

    @EJB
    private UnidMedidaDao servicioUnidMedida;
    private List<UnidMedida> items = null;
    private UnidMedida selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public UnidMedidaController() {
    }

    public UnidMedida getSelected() {
        return selected;
    }

    public void setSelected(UnidMedida selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UnidMedidaDao getFacade() {
        return servicioUnidMedida;
    }

    public UnidMedida prepareCreate() {
        selected = new UnidMedida();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioUnidMedida.validarNombreCrear(selected.getUmdNombre())) {
            if (servicioUnidMedida.validarAbreviaturaCrear(selected.getUmdAbreviatura())) {
                selected.setUmdFHR(Calendar.getInstance().getTime());
                selected.setUmdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UnidMedidaCreated"));
            } else {
                addErrorMessage("La Abreviatura ingresada ya existe");
            }
        } else {
            addErrorMessage("El Nombre ingresado ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioUnidMedida.validarNombreEditar(selected.getUmdId(), selected.getUmdNombre())) {
            if (servicioUnidMedida.validarAbreviaturaEditar(selected.getUmdId(), selected.getUmdAbreviatura())) {
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UnidMedidaUpdated"));
            } else {
                addErrorMessage("La Abreviatura ingresada ya existe");
            }
        } else {
            addErrorMessage("El Nombre ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UnidMedidaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UnidMedida> getItems() throws ServicioExcepcion {
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

    public UnidMedida getUnidMedida(java.lang.Long id) {
        return getFacade().find(new UnidMedida(), id);
    }

//    public List<UnidMedida> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
//    public List<UnidMedida> getItemsAvailableSelectOne() {
//        return getFacade().findAll();
//    }
    @FacesConverter(forClass = UnidMedida.class)
    public static class UnidMedidaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UnidMedidaController controller = (UnidMedidaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "unidMedidaController");
            return controller.getUnidMedida(getKey(value));
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
            if (object instanceof UnidMedida) {
                UnidMedida o = (UnidMedida) object;
                return getStringKey(o.getUmdId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UnidMedida.class.getName()});
                return null;
            }
        }

    }

}
