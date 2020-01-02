/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.servicio.TipoComparecienteServicio;
import com.rm.empresarial.servicio.TipoTramiteComparecienteServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
@Named("controladorTipoTramiteCompareciente")
@ViewScoped
public class ControladorTipoTramiteCompareciente implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @Inject
    ControladorAccesoPantallas controladorAccesoPantallas;

    @EJB
    private TipoTramiteComparecienteServicio servicioTipoTramiteCompareciente;
    private List<TipoTramiteCompareciente> items = null;
    private TipoTramiteCompareciente selected;

    @Getter
    @Setter
    private boolean ttcPropietario;

    public ControladorTipoTramiteCompareciente() {

    }

    private String link = "/paginas/MANTENIMIENTOS/tipoTramiteCompareciente/List.xhtml";

    @PostConstruct
    public void validarAcceso() {

        try {
            controladorAccesoPantallas.accederPantallas(link);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public TipoTramiteCompareciente getSelected() {
        return selected;
    }

    public void setSelected(TipoTramiteCompareciente selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoTramiteComparecienteServicio getFacade() {
        return servicioTipoTramiteCompareciente;
    }

    public TipoTramiteCompareciente prepareCreate() {
        selected = new TipoTramiteCompareciente();
        initializeEmbeddableKey();
        return selected;
    }

    public void prepareEdit() {
        setTtcPropietario(selected.getTtcPropietario().equals("S"));
    }

    public void validarCheckBox(boolean ttcProp) {
        if (ttcPropietario) {
            selected.setTtcPropietario("S");
        } else {
            selected.setTtcPropietario("N");
        }
    }

    public void clear() {
        setTtcPropietario(false);
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        validarCheckBox(ttcPropietario);
        selected.setTtcFHR(Calendar.getInstance().getTime());
        selected.setTtcUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoTramiteComparecienteCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        validarCheckBox(ttcPropietario);
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoTramiteComparecienteUpdated"));
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoTramiteComparecienteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoTramiteCompareciente> getItems() throws ServicioExcepcion {
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

    public TipoTramiteCompareciente getTipoTramiteCompareciente(java.math.BigDecimal id) {
        return getFacade().find(new TipoTramiteCompareciente(), id);
    }

//    public List<TipoTramiteCompareciente> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<TipoTramiteCompareciente> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoTramiteCompareciente.class)
    public static class ControladorTipoTramiteComparecienteConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoTramiteCompareciente controller = (ControladorTipoTramiteCompareciente) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorTipoTramiteCompareciente");
            return controller.getTipoTramiteCompareciente(getKey(value));
        }

        java.math.BigDecimal getKey(String value) {
            java.math.BigDecimal key;
            key = new java.math.BigDecimal(value);
            return key;
        }

        String getStringKey(java.math.BigDecimal value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof TipoTramiteCompareciente) {
                TipoTramiteCompareciente o = (TipoTramiteCompareciente) object;
                return getStringKey(o.getTtcId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoTramiteCompareciente.class.getName()});
                return null;
            }
        }

    }

    @PreDestroy
    private void preDestroy() {
    }

}
