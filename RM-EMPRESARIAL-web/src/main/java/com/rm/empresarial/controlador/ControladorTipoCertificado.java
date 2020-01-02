package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoCertificado;
import com.rm.empresarial.servicio.TipoCertificadoServicio;

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
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("tipoCertificadoController")
@SessionScoped
public class ControladorTipoCertificado implements Serializable {

    @EJB
    private TipoCertificadoServicio ejbFacade;
    private List<TipoCertificado> items = null;
    private TipoCertificado selected;

    @Getter
    @Setter
    private Boolean tipoCertificadoIva;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorTipoCertificado() {
    }

    public TipoCertificado getSelected() {
        return selected;
    }

    public void setSelected(TipoCertificado selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoCertificadoServicio getFacade() {
        return ejbFacade;
    }

    public TipoCertificado prepareCreate() {
        selected = new TipoCertificado();
        initializeEmbeddableKey();
        return selected;
    }

    public void prepareEdit() {
        setTipoCertificadoIva(selected.getTroIva().equals("S"));
    }

    public void clear() {
        setSelected(null);
    }

    public void create() {
        if (tipoCertificadoIva) {
            selected.setTroIva("S");
        } else {
            selected.setTroIva("N");
        }
        selected.setTroFhr(Calendar.getInstance().getTime());
        selected.setTroUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoCertificadoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        if (tipoCertificadoIva) {
            selected.setTroIva("S");
        } else {
            selected.setTroIva("N");
        }
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoCertificadoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoCertificadoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoCertificado> getItems() throws ServicioExcepcion {
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

    public TipoCertificado getTipoCertificado(Long id) {
        return getFacade().find(new TipoCertificado(), id);
    }

//    public List<TipoCertificado> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<TipoCertificado> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoCertificado.class)
    public static class TipoCertificadoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoCertificado controller = (ControladorTipoCertificado) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoCertificadoController");
            return controller.getTipoCertificado(getKey(value));
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
            if (object instanceof TipoCertificado) {
                TipoCertificado o = (TipoCertificado) object;
                return getStringKey(o.getTroId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoCertificado.class.getName()});
                return null;
            }
        }

    }

}
