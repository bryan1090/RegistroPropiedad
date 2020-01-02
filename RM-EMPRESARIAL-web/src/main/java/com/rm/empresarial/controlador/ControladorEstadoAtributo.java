/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Estado;
import com.rm.empresarial.modelo.EstadoAtributo;
import com.rm.empresarial.servicio.EstadoAtributoServicio;
import com.rm.empresarial.servicio.EstadoServicio;
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
 * @author Richard
 */
@Named("controladorEstadoAtributo")
@ViewScoped
public class ControladorEstadoAtributo implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private EstadoAtributoServicio servicioEstadoAtributo;
    private List<EstadoAtributo> items = null;
    private EstadoAtributo selected;

    public ControladorEstadoAtributo() {
    }

    public EstadoAtributo getSelected() {
        return selected;
    }

    public void setSelected(EstadoAtributo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EstadoAtributoServicio getFacade() {
        return servicioEstadoAtributo;
    }

    public EstadoAtributo prepareCreate() {
        selected = new EstadoAtributo();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        selected.setEstAtrFHR(Calendar.getInstance().getTime());
        selected.setEstAtrUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EstadoAtributoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EstadoAtributoUpdated"));
        clear();
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EstadoAtributoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<EstadoAtributo> getItems() throws ServicioExcepcion {
        items = getFacade().listarTodo();
        return items;
    }
//public List<Estado> getItemsXID() throws ServicioExcepcion {
//        if (itemsEstado == null) {
//            itemsEstado = getEstadoFacade().listarXId();
//        }
//        return itemsEstado;
//    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
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

    public EstadoAtributo getEstadoAtributo(Long id) {
        return getFacade().find(new EstadoAtributo(), id);
    }

//    public List<EstadoAtributo> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
    public List<EstadoAtributo> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = EstadoAtributo.class)
    public static class EstadoAtributoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorEstadoAtributo controller = (ControladorEstadoAtributo) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorEstadoAtributo");
            return controller.getEstadoAtributo(getKey(value));
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
            if (object instanceof EstadoAtributo) {
                EstadoAtributo o = (EstadoAtributo) object;
                return getStringKey(o.getEstAtrId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), EstadoAtributo.class.getName()});
                return null;
            }
        }

    }

}
