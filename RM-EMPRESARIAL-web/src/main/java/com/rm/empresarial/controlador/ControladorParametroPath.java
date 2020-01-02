package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.ParametroPathServicio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;


@Named("controladorParametroPath")
@SessionScoped
public class ControladorParametroPath implements Serializable {

    
    @Inject
    private DataManagerSesion dataManagerSesion;
    
    
    
    @EJB
    private ParametroPathServicio servicioParametroPath;
    private List<ParametroPath> items = null;
    private ParametroPath selected;

    public ControladorParametroPath() {
    }

    @PostConstruct
    public void postControlador() {
        
       

    }

    public ParametroPath getSelected() {
        return selected;
    }

    public void setSelected(ParametroPath selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ParametroPathServicio getServicio() {
        return servicioParametroPath;
    }

    public ParametroPath prepareCreate() {
        selected = new ParametroPath();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        selected.setPrpUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        selected.setPrpFHR(Calendar.getInstance().getTime());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ParametroPathCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ParametroPathUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ParametroPathDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ParametroPath> getItems() throws ServicioExcepcion {

        return getServicio().listarTodo();
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getServicio().edit(selected);
                } else {
                    getServicio().remove(selected);
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

    

//    public ParametroPath getParametroPath(Long id) {
//        return getServicio().find(id);
//    }
//
//    public List<ParametroPath> getItemsAvailableSelectMany() {
//        return getServicio().findAll();
//    }
//
//    public List<ParametroPath> getItemsAvailableSelectOne() {
//        return getServicio().findAll();
//    }
//    @FacesConverter(forClass = ParametroPath.class)
//    public static class ParametroPathControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            ControladorParametroPath controller = (ControladorParametroPath) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "parametroPathController");
//            return controller.getParametroPath(getKey(value));
//        }
//
//        Long getKey(String value) {
//            Long key;
//            key = new Long(value);
//            return key;
//        }
//
//        String getStringKey(Long value) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(value);
//            return sb.toString();
//        }
//
//        @Override
//        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//            if (object == null) {
//                return null;
//            }
//            if (object instanceof ParametroPath) {
//                ParametroPath o = (ParametroPath) object;
//                return getStringKey(o.getPrpId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ParametroPath.class.getName()});
//                return null;
//            }
//        }
//
//    }
}
