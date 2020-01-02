package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.TipoPropiedadClase;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.TipoPropiedadClaseServicio;

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

@Named("tipoPropiedadClaseController")
@ViewScoped
public class ControladorTipoPropiedadClase implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @EJB
    private TipoPropiedadClaseServicio servicioTipoPropiedadClase;
    private List<TipoPropiedadClase> items = null;
    private TipoPropiedadClase selected;

    public ControladorTipoPropiedadClase() {
    }

    public TipoPropiedadClase getSelected() {
        return selected;
    }

    public void setSelected(TipoPropiedadClase selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoPropiedadClaseServicio getFacade() {
        return servicioTipoPropiedadClase;
    }

    public TipoPropiedadClase prepareCreate() {
        selected = new TipoPropiedadClase();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioTipoPropiedadClase.validarIdCrear(selected.getTclId())) {
            if (servicioTipoPropiedadClase.validarNombreCrear(selected.getTclNombre())) {
                selected.setTclFHR(Calendar.getInstance().getTime());
                selected.setTclUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoPropiedadClaseCreated"));
            } else {
                addErrorMessage("El nombre ingresado ya existe");
            }
        } else {
            addErrorMessage("El id ingresado ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioTipoPropiedadClase.validarNombreEditar(selected.getTclId(), selected.getTclNombre())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoPropiedadClaseUpdated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoPropiedadClaseDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoPropiedadClase> getItems() throws ServicioExcepcion {
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

    public TipoPropiedadClase getTipoPropiedadClase(java.lang.String id) {
        return getFacade().find(new TipoPropiedadClase(), id);
    }

//    public List<TipoPropiedadClase> getItemsAvailableSelectMany() throws ServicioExcepcion {
//        return getFacade().listarTodo();
//    }
    public List<TipoPropiedadClase> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoPropiedadClase.class)
    public static class TipoPropiedadClaseControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoPropiedadClase controller = (ControladorTipoPropiedadClase) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoPropiedadClaseController");
            return controller.getTipoPropiedadClase(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoPropiedadClase) {
                TipoPropiedadClase o = (TipoPropiedadClase) object;
                return getStringKey(o.getTclId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoPropiedadClase.class.getName()});
                return null;
            }
        }

    }

}
