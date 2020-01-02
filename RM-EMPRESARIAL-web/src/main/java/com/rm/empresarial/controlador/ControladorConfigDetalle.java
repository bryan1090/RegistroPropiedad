/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.ConfigServicio;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Prueba
 */
@Named("controladorConfigDetalle")
@ViewScoped
public class ControladorConfigDetalle implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private ConfigDetalleServicio ejbFacade;
    private List<ConfigDetalle> items = null;
    private ConfigDetalle selected;

    public ControladorConfigDetalle() {
    }

    public ConfigDetalle getSelected() {
        return selected;
    }

    public void setSelected(ConfigDetalle selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ConfigDetalleServicio getFacade() {
        return ejbFacade;
    }

    public ConfigDetalle prepareCreate() {
        selected = new ConfigDetalle();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear(){
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        selected.setConfigDetalleFHR(Calendar.getInstance().getTime());
        selected.setConfigDetalleUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ConfigDetalleCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ConfigDetalleUpdated"));
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ConfigDetalleDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ConfigDetalle> getItems() throws ServicioExcepcion {
        return getFacade().listarTodo();
//if (items == null) {
//            items = getFacade().listarTodo();
//        }
//        return items;
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

    public ConfigDetalle getConfigDetalle(Long id) {
        return getFacade().find(new ConfigDetalle(), id);
    }

//    public List<ConfigDetalle> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<ConfigDetalle> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = ConfigDetalle.class)
    public static class ConfigDetalleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorConfigDetalle controller = (ControladorConfigDetalle) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "configDetalleController");
            return controller.getConfigDetalle(getKey(value));
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
            if (object instanceof ConfigDetalle) {
                ConfigDetalle o = (ConfigDetalle) object;
                return getStringKey(o.getConfigDetalleCodigo());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ConfigDetalle.class.getName()});
                return null;
            }
        }

    }

}
