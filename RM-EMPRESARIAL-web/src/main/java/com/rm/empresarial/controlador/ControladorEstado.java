/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Estado;
import com.rm.empresarial.servicio.EstadoServicio;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author David
 */
@Named("controladorEstado")
@ViewScoped
public class ControladorEstado implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private EstadoServicio servicioEstado;
    private List<Estado> items = null;
    private Estado selected;

    public ControladorEstado() {
    }

    public Estado getSelected() {
        return selected;
    }

    public void setSelected(Estado selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private EstadoServicio getFacade() {
        return servicioEstado;
    }

    public Estado prepareCreate() {
        selected = new Estado();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear(){
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioEstado.validarCodigoCrear(selected.getEstCodigo())) {
            if (servicioEstado.validarDescripCrear(selected.getEstDescripcion())) {
                selected.setEstFHR(Calendar.getInstance().getTime());
                selected.setEstUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("EstadoCreated"));
            } else {
                addErrorMessage("La descripción ingresada ya existe");
            }
        } else {
            addErrorMessage("El código ingresado ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioEstado.validarCodigoEditar(selected.getEstId(), selected.getEstCodigo())) {
            if (servicioEstado.validarDescripEditar(selected.getEstId(), selected.getEstDescripcion())) {
                persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("EstadoUpdated"));
            } else {
                addErrorMessage("La descripción ingresada ya existe");
            }
        } else {
            addErrorMessage("El código ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("EstadoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Estado> getItems() throws ServicioExcepcion {
        items = getFacade().listarTodo();
        return items;
    }

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

    public Estado getEstado(Long id) {
        return getFacade().find(new Estado(), id);
    }

//    public List<Estado> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
    public List<Estado> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Estado.class)
    public static class EstadoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorEstado controller = (ControladorEstado) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorEstado");
            return controller.getEstado(getKey(value));
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
            if (object instanceof Estado) {
                Estado o = (Estado) object;
                return getStringKey(o.getEstId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Estado.class.getName()});
                return null;
            }
        }

    }

}
