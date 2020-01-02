package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.TipoMarginacionMensaje;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.TipoMarginacionMensajeServicio;

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

@Named("tipoMarginacionMensajeController")
@ViewScoped
public class ControladorTipoMarginacionMensaje implements Serializable {

    @EJB
    private TipoMarginacionMensajeServicio servicioTipoMarginacionMensaje;
    private List<TipoMarginacionMensaje> items = null;
    private TipoMarginacionMensaje selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorTipoMarginacionMensaje() {
    }

    public TipoMarginacionMensaje getSelected() {
        return selected;
    }

    public void setSelected(TipoMarginacionMensaje selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoMarginacionMensajeServicio getFacade() {
        return servicioTipoMarginacionMensaje;
    }

    public TipoMarginacionMensaje prepareCreate() {
        selected = new TipoMarginacionMensaje();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioTipoMarginacionMensaje.validarVaribleCrear(selected.getTmmVariable())) {
            selected.setTmmFHR(Calendar.getInstance().getTime());
            selected.setTmmUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoMarginacionMensajeCreated"));
        } else {
            addErrorMessage("La Variable ingresada ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioTipoMarginacionMensaje.validarVaribleEditar(selected.getTmmId(), selected.getTmmVariable())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoMarginacionMensajeUpdated"));
        } else {
            addErrorMessage("La Variable ingresada ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoMarginacionMensajeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoMarginacionMensaje> getItems() throws ServicioExcepcion {
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

    public TipoMarginacionMensaje getTipoMarginacionMensaje(Long id) {
        return getFacade().find(new TipoMarginacionMensaje(), id);
    }

    public List<TipoMarginacionMensaje> getItemsAvailableSelectMany() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    public List<TipoMarginacionMensaje> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoMarginacionMensaje.class)
    public static class TipoMarginacionMensajeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoMarginacionMensaje controller = (ControladorTipoMarginacionMensaje) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoMarginacionMensajeController");
            return controller.getTipoMarginacionMensaje(getKey(value));
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
            if (object instanceof TipoMarginacionMensaje) {
                TipoMarginacionMensaje o = (TipoMarginacionMensaje) object;
                return getStringKey(o.getTmmId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoMarginacionMensaje.class.getName()});
                return null;
            }
        }

    }

}
