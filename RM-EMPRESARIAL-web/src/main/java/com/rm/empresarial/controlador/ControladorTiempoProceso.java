package com.rm.empresarial.controlador;

import com.rm.empresarial.dao.TiempoProcesoDao;
import com.rm.empresarial.modelo.TiempoProceso;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;

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

@Named("controladorTiempoProceso")
@ViewScoped
public class ControladorTiempoProceso implements Serializable {

    @EJB
    private TiempoProcesoDao servicioTiempoProceso;

    @Inject
    private DataManagerSesion dataManagerSesion;

    private List<TiempoProceso> items = null;
    private TiempoProceso selected;

    public ControladorTiempoProceso() {
        selected=new TiempoProceso();
    }

    public TiempoProceso getSelected() {
        return selected;
    }

    public void setSelected(TiempoProceso selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TiempoProcesoDao getFacade() {
        return servicioTiempoProceso;
    }

    public TiempoProceso prepareCreate() {
        selected = new TiempoProceso();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TiempoProcesoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TiempoProcesoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TiempoProcesoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TiempoProceso> getItems() {
        if (items == null) {
            items = getFacade().listarTodo();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    selected.setTpoEstado("A");
                    selected.setTpoUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    selected.setTpoFHR(Calendar.getInstance().getTime());
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

    public TiempoProceso getTiempoProceso(Long id) {
        return getFacade().find(new TiempoProceso(), id);
    }

    public List<TiempoProceso> getItemsAvailableSelectMany() {
        return getFacade().listarTodo();
    }

    public List<TiempoProceso> getItemsAvailableSelectOne() {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TiempoProceso.class)
    public static class TiempoProcesoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTiempoProceso controller = (ControladorTiempoProceso) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorTiempoProceso");
            return controller.getTiempoProceso(getKey(value));
        }

        Long getKey(String value) {
            Long key;
            key = new Long(value);
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
            if (object instanceof TiempoProceso) {
                TiempoProceso o = (TiempoProceso) object;
                return getStringKey(o.getTpoId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TiempoProceso.class.getName()});
                return null;
            }
        }

    }

}
