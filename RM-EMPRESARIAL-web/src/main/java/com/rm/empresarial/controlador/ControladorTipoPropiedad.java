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
import com.rm.empresarial.modelo.TipoPropiedad;
import com.rm.empresarial.servicio.TipoPropiedadServicio;
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
@Named("controladorTipoPropiedad")
@ViewScoped
public class ControladorTipoPropiedad implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private TipoPropiedadServicio servicioTipoPropiedad;
    private List<TipoPropiedad> items = null;
    private TipoPropiedad selected;

    public ControladorTipoPropiedad() {
    }

    public TipoPropiedad getSelected() {
        return selected;
    }

    public void setSelected(TipoPropiedad selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoPropiedadServicio getFacade() {
        return servicioTipoPropiedad;
    }

    public TipoPropiedad prepareCreate() {
        selected = new TipoPropiedad();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioTipoPropiedad.validarNombreCrear(selected.getTpdNombre())) {
            selected.setTpdFHR(Calendar.getInstance().getTime());
            selected.setTpdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoPropiedadCreated"));
        } else {
            addErrorMessage("El nombre ingresado ya exite");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioTipoPropiedad.validarNombreEditar(selected.getTpdId(), selected.getTpdNombre())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoPropiedadUpdated"));
        } else {
            addErrorMessage("El nombre ingresado ya exite");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoPropiedadDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoPropiedad> getItems() throws ServicioExcepcion {
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

    public TipoPropiedad getTipoPropiedad(Long id) {
        return getFacade().find(new TipoPropiedad(), id);
    }

//    public List<TipoPropiedad> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<TipoPropiedad> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoPropiedad.class)
    public static class ControladorTipoPropiedadConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoPropiedad controller = (ControladorTipoPropiedad) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorTipoPropiedad");
            return controller.getTipoPropiedad(getKey(value));
        }

        Long getKey(String value) {
            Long key;
            key = new Long(value);
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
            if (object instanceof TipoPropiedad) {
                TipoPropiedad o = (TipoPropiedad) object;
                return getStringKey(o.getTpdId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoPropiedad.class.getName()});
                return null;
            }
        }

    }

}
