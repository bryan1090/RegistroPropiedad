/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Mensaje;
import com.rm.empresarial.servicio.MensajeServicio;
import java.io.Serializable;
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
import javax.inject.Named;

/**
 *
 * @author Prueba
 */
@Named("controladorMensaje")
@ViewScoped
public class ControladorMensaje implements Serializable {

    @EJB
    private MensajeServicio servicioMensaje;
    private List<Mensaje> items = null;
    private Mensaje selected;

    public ControladorMensaje() {
    }

    public Mensaje getSelected() {
        return selected;
    }

    public void setSelected(Mensaje selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MensajeServicio getFacade() {
        return servicioMensaje;
    }

    public Mensaje prepareCreate() {
        selected = new Mensaje();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear(){
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioMensaje.validarNumeroCrear(selected.getMsjNumero())) {
            if (servicioMensaje.validarDescripCrear(selected.getMsjDescripcion())) {
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MensajeCreated"));
            } else {
                addErrorMessage("La descripción ingresada ya existe");
            }
        } else {
            addErrorMessage("El número ingresado ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioMensaje.validarNumeroEditar(selected.getMsjId(), selected.getMsjNumero())) {
            if (servicioMensaje.validarDescripEditar(selected.getMsjId(), selected.getMsjDescripcion())) {
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MensajeUpdated"));
            } else {
                addErrorMessage("La descripción ingresada ya existe");
            }
        } else {
            addErrorMessage("El número ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MensajeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Mensaje> getItems() throws ServicioExcepcion {
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

    public Mensaje getMensaje(Long id) {
        return getFacade().find(selected, id);
    }

//    public List<Mensaje> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<Mensaje> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

//    @FacesConverter(forClass = Mensaje.class)
//    public static class MensajeControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            ControladorMensaje controller = (ControladorMensaje) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "mensajeController");
//            return controller.getMensaje(getKey(value));
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
//            if (object instanceof Mensaje) {
//                Mensaje o = (Mensaje) object;
//                return getStringKey(o.getMsjId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Mensaje.class.getName()});
//                return null;
//            }
//        }
//
//    }
}
