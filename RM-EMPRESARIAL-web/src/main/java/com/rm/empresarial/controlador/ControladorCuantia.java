/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Cuantia;
import com.rm.empresarial.servicio.CuantiaServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
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
@Named("controladorCuantia")
@ViewScoped
public class ControladorCuantia implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private CuantiaServicio servicioCuantia;
    private List<Cuantia> items = null;
    private Cuantia selected;
    @EJB
    private TipoTramiteServicio facadeTipoTramite;
    @Getter
    @Setter
    private String idTipoTramite;

    public ControladorCuantia() {
    }

    public Cuantia getSelected() {
        return selected;
    }

    public void setSelected(Cuantia selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CuantiaServicio getFacade() {
        return servicioCuantia;
    }

    public Cuantia prepareCreate() {
        selected = new Cuantia();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        selected.setCuaFHR(Calendar.getInstance().getTime());
        selected.setTtrId(facadeTipoTramite.buscarTramitePorID(idTipoTramite));
        selected.setCuaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CuantiaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
//        selected.setTtrId(facadeTipoTramite.buscarTramitePorID(idTipoTramite));
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CuantiaUpdated"));
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CuantiaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Cuantia> getItems() throws ServicioExcepcion {
        items = getFacade().ListarTodos();
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

    public Cuantia getCuantia(Long id) {
        return getFacade().find(selected, id);
    }

//    public List<Cuantia> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<Cuantia> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().ListarTodos();
    }

    @FacesConverter(forClass = Cuantia.class)
    public static class ControladorCuantiaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorCuantia controller = (ControladorCuantia) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorCuantia");
            return controller.getCuantia(getKey(value));
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
            if (object instanceof Cuantia) {
                Cuantia o = (Cuantia) object;
                return getStringKey(o.getCuaId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Cuantia.class.getName()});
                return null;
            }
        }

    }

}
