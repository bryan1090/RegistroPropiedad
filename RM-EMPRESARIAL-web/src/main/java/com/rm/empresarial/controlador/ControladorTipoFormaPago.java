package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.rm.empresarial.modelo.TipoFormaPago;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.TipoFormaPagoServicio;
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


@Named("tipoFormaPagoController")
@ViewScoped
public class ControladorTipoFormaPago implements Serializable {

    @EJB
    private TipoFormaPagoServicio servicioTipoFormaPago;
    private List<TipoFormaPago> items = null;
    private TipoFormaPago selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorTipoFormaPago() {
    }

    public TipoFormaPago getSelected() {
        return selected;
    }

    public void setSelected(TipoFormaPago selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoFormaPagoServicio getFacade() {
        return servicioTipoFormaPago;
    }

    public TipoFormaPago prepareCreate() {
        selected = new TipoFormaPago();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioTipoFormaPago.validarDescripcionCrear(selected.getTpfDescripcion())) {
            selected.setTpfFHR(Calendar.getInstance().getTime());
            selected.setTpfUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoFormaPagoCreated"));
        } else {
            addErrorMessage("La Descripción ingresada ya existe.");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioTipoFormaPago.validarDescripcionEditar(selected.getTpfId(), selected.getTpfDescripcion())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoFormaPagoUpdated"));
        } else {
            addErrorMessage("La Descripción ingresada ya existe.");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoFormaPagoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoFormaPago> getItems() throws ServicioExcepcion {
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

    public TipoFormaPago getTipoFormaPago(java.lang.Long id) {
        return getFacade().find(new TipoFormaPago(), id);
    }

    public List<TipoFormaPago> getItemsAvailableSelectMany() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    public List<TipoFormaPago> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoFormaPago.class)
    public static class TipoFormaPagoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoFormaPago controller = (ControladorTipoFormaPago) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoFormaPagoController");
            return controller.getTipoFormaPago(getKey(value));
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
            if (object instanceof TipoFormaPago) {
                TipoFormaPago o = (TipoFormaPago) object;
                return getStringKey(o.getTpfId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoFormaPago.class.getName()});
                return null;
            }
        }

    }

}
