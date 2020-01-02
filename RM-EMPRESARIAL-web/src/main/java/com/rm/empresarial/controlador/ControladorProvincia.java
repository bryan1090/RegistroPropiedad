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
import com.rm.empresarial.modelo.Pais;
import com.rm.empresarial.modelo.Provincia;
import com.rm.empresarial.servicio.PaisServicio;
import com.rm.empresarial.servicio.ProvinciaServicio;
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
@Named("controladorProvincia")
@ViewScoped
public class ControladorProvincia implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private ProvinciaServicio servicioProvincia;
    private List<Provincia> items = null;
    private Provincia selected;

    public ControladorProvincia() {
    }

    public Provincia getSelected() {
        return selected;
    }

    public void setSelected(Provincia selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProvinciaServicio getFacade() {
        return servicioProvincia;
    }

    public Provincia prepareCreate() {
        selected = new Provincia();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioProvincia.validarNombreCrear(selected.getProNombre())) {
            selected.setProFHR(Calendar.getInstance().getTime());
            selected.setProUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProvinciaCreated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioProvincia.validarNombreEditar(selected.getProId(), selected.getProNombre())) {
            persist(JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProvinciaUpdated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProvinciaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Provincia> getItems() throws ServicioExcepcion {
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

    public Provincia getProvincia(Long id) {
        return getFacade().find(new Provincia(), id);
    }

//    public List<Provincia> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
    public List<Provincia> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Provincia.class)
    public static class ProvinciaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorProvincia controller = (ControladorProvincia) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorProvincia");
            return controller.getProvincia(getKey(value));
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
            if (object instanceof Provincia) {
                Provincia o = (Provincia) object;
                return getStringKey(o.getProId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Provincia.class.getName()});
                return null;
            }
        }

    }

}
