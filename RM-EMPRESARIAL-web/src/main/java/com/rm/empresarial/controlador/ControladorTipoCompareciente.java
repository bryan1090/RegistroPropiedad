package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.RolControllerBb;
import com.rm.empresarial.modelo.TipoCompareciente;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.TipoComparecienteServicio;

import java.io.Serializable;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("controladorTipoCompareciente")
@ViewScoped
public class ControladorTipoCompareciente implements Serializable {

    @Inject
    private DataManagerSesion dataManagerSesion;

    @EJB
    private TipoComparecienteServicio servicioTipoCompareciente;
    private List<TipoCompareciente> items = null;
    private TipoCompareciente selected;

    public ControladorTipoCompareciente() {
    }

    public TipoCompareciente getSelected() {
        return selected;
    }

    public void setSelected(TipoCompareciente selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoComparecienteServicio getFacade() {
        return servicioTipoCompareciente;
    }

    public TipoCompareciente prepareCreate() {
        selected = new TipoCompareciente();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioTipoCompareciente.validarCodigoCrear(selected.getTpcCodigo())) {
            if (servicioTipoCompareciente.validarDescripCrear(selected.getTpcDescripcion())) {
                selected.setTpcUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                selected.setTpcFHR(Calendar.getInstance().getTime());
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoComparecienteCreated"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La descripción ingresada ya existe", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El código ingresado ya existe", ""));
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioTipoCompareciente.validarCodigoEditar(selected.getTpcId(), selected.getTpcCodigo())) {
            if (servicioTipoCompareciente.validarDescripEditar(selected.getTpcId(), selected.getTpcDescripcion())) {
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoComparecienteUpdated"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La descripción ingresada ya existe", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El código ingresado ya existe", ""));
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoComparecienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoCompareciente> getItems() throws ServicioExcepcion {
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

    public TipoCompareciente getTipoCompareciente(Long id) {
        return getFacade().find(new TipoCompareciente(), id);
    }
//
//    public List<TipoCompareciente> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//

    public List<TipoCompareciente> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoCompareciente.class)
    public static class TipoComparecienteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoCompareciente controller = (ControladorTipoCompareciente) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorTipoCompareciente");
            return controller.getTipoCompareciente(getKey(value));
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
            if (object instanceof TipoCompareciente) {
                TipoCompareciente o = (TipoCompareciente) object;
                return getStringKey(o.getTpcId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoCompareciente.class.getName()});
                return null;
            }
        }

    }

}
