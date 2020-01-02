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
import com.rm.empresarial.modelo.Contratante;
import com.rm.empresarial.servicio.ContratanteServicio;
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
@Named("controladorContratante")
@ViewScoped
public class ControladorContratante implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private ContratanteServicio servicioContratante;
    private List<Contratante> items = null;
    private Contratante selected;

    public ControladorContratante() {
    }

    public Contratante getSelected() {
        return selected;
    }

    public void setSelected(Contratante selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ContratanteServicio getFacade() {
        return servicioContratante;
    }

    public Contratante prepareCreate() {
        selected = new Contratante();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear(){
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioContratante.validarCodigoCrear(selected.getConCodigo())) {
            if (servicioContratante.validarDescripCrear(selected.getConDescripcion())) {
                selected.setConFHR(Calendar.getInstance().getTime());
                selected.setConUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ContratanteCreated"));
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
        if (servicioContratante.validarCodigoEditar(selected.getConId(), selected.getConCodigo())) {
            if (servicioContratante.validarDescripEditar(selected.getConId(), selected.getConDescripcion())) {
                persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ContratanteUpdated"));
            } else {
                addErrorMessage("La descripción ingresada ya existe");
            }
        } else {
            addErrorMessage("El código ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ContratanteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Contratante> getItems() throws ServicioExcepcion {
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

    public Contratante getContratante(Long id) {
        return getFacade().find(new Contratante(), id);
    }

//    public List<Contratante> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<Contratante> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

//    @FacesConverter(forClass = Contratante.class)
//    public static class ContratanteControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            ContratanteController controller = (ContratanteController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "contratanteController");
//            return controller.getContratante(getKey(value));
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
//            if (object instanceof Contratante) {
//                Contratante o = (Contratante) object;
//                return getStringKey(o.getConId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Contratante.class.getName()});
//                return null;
//            }
//        }
//
//    }
}
