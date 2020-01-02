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
import com.rm.empresarial.modelo.TipoMarginacion;
import com.rm.empresarial.servicio.TipoMarginacionServicio;
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
@Named("controladorTipoMarginacion")
@ViewScoped
public class ControladorTipoMarginacion implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private TipoMarginacionServicio servicioTipoMarginacion;
    private List<TipoMarginacion> items = null;
    private TipoMarginacion selected;

    public ControladorTipoMarginacion() {
    }

    public TipoMarginacion getSelected() {
        return selected;
    }

    public void setSelected(TipoMarginacion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoMarginacionServicio getFacade() {
        return servicioTipoMarginacion;
    }

    public TipoMarginacion prepareCreate() {
        selected = new TipoMarginacion();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear(){
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioTipoMarginacion.validarNombreCrear(selected.getTmcNombre())) {
            selected.setTmcFHR(Calendar.getInstance().getTime());
            selected.setTmcUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoMarginacionCreated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioTipoMarginacion.validarNombreEditar(selected.getTmcId(), selected.getTmcNombre())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoMarginacionUpdated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoMarginacionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoMarginacion> getItems() throws ServicioExcepcion {
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

    public TipoMarginacion getTipoMarginacion(Long id) {
        return getFacade().find(new TipoMarginacion(), id);
    }

//    public List<TipoMarginacion> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<TipoMarginacion> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoMarginacion.class)
    public static class TipoMarginacionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoMarginacion controller = (ControladorTipoMarginacion) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorTipoMarginacion");
            return controller.getTipoMarginacion(getKey(value));
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
            if (object instanceof TipoMarginacion) {
                TipoMarginacion o = (TipoMarginacion) object;
                return getStringKey(o.getTmcId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoMarginacion.class.getName()});
                return null;
            }
        }

    }

}
