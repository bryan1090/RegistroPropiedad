/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.servicio.TipoLibroServicio;
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
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author Richard
 */
@Named("controladorTipoLibro")
@ViewScoped

public class ControladorTipoLibro implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private TipoLibroServicio servicioTipoLibro;
    private List<TipoLibro> items = null;
    private TipoLibro selected;

    public ControladorTipoLibro() {
    }

    public TipoLibro getSelected() {
        return selected;
    }

    public void setSelected(TipoLibro selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoLibroServicio getFacade() {
        return servicioTipoLibro;
    }

    public TipoLibro prepareCreate() {
        selected = new TipoLibro();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void handleClose(CloseEvent event) {
        clear();
    }

    public void create() throws ServicioExcepcion {
        if (servicioTipoLibro.validarDescripCrear(selected.getTplDescripcion())) {
            selected.setTplFHR(Calendar.getInstance().getTime());
            selected.setTplUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoLibroCreated"));
        } else {
            addErrorMessage("La descripción ingresada ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();

    }

    public void update() throws ServicioExcepcion {
        if (servicioTipoLibro.validarDescripEditar(selected.getTplId(), selected.getTplDescripcion())) {
            persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoLibroUpdated"));
        } else {
            addErrorMessage("La descripción ingresada ya existe");
        }
        clear();

    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoLibroDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoLibro> getItems() throws ServicioExcepcion {
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

    public TipoLibro getTipoLibro(Long id) {
        return getFacade().find(new TipoLibro(), id);
    }

//    public List<TipoLibro> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
    public List<TipoLibro> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoLibro.class)
    public static class ControladorTipoLibroConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoLibro controller = (ControladorTipoLibro) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorTipoLibro");
            return controller.getTipoLibro(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoLibro) {
                TipoLibro o = (TipoLibro) object;
                return getStringKey(o.getTplId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoLibro.class.getName()});
                return null;
            }
        }
    }
}
