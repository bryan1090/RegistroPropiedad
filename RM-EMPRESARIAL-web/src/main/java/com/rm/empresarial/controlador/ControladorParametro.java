/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Parametros;
import com.rm.empresarial.servicio.ParametrosServicio;
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

/**
 *
 * @author Prueba
 */
@Named("controladorParametro")
@ViewScoped
public class ControladorParametro implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private ParametrosServicio ejbFacade;
    private List<Parametros> items = null;
    private Parametros selected;

    public ControladorParametro() {
    }

    public Parametros getSelected() {
        return selected;
    }

    public void setSelected(Parametros selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ParametrosServicio getFacade() {
        return ejbFacade;
    }

    public Parametros prepareCreate() {
        selected = new Parametros();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() {
//        selected.setPrmValor3(Calendar.getInstance().getTime());
////        selected.setEstUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
//      
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ParametrosCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ParametrosUpdated"));
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ParametrosDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Parametros> getItems() throws ServicioExcepcion {
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

    public Parametros getParametros(java.math.BigDecimal id) {
        return getFacade().find(selected, id);
    }

//    public List<Parametros> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
//    public List<Parametros> getItemsAvailableSelectOne() {
//        return getFacade().findAll();
//    }
//
//    @FacesConverter(forClass = Parametros.class)
//    public static class ParametrosControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            ParametrosController controller = (ParametrosController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "parametrosController");
//            return controller.getParametros(getKey(value));
//        }
//
//        java.math.BigDecimal getKey(String value) {
//            java.math.BigDecimal key;
//            key = new java.math.BigDecimal(value);
//            return key;
//        }
//
//        String getStringKey(java.math.BigDecimal value) {
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
//            if (object instanceof Parametros) {
//                Parametros o = (Parametros) object;
//                return getStringKey(o.getPrmId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Parametros.class.getName()});
//                return null;
//            }
//        }
//
//    }
}
