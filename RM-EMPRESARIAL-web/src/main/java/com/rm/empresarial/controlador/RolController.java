/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.RolControllerBb;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Rol;
import com.rm.empresarial.servicio.RolServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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

/**
 *
 * @author Bryan_Mora
 */
@Named("rolController")
@ViewScoped
public class RolController implements Serializable {

    private static final long serialVersionUID = 2271483766885573832L;

    @Inject
    private DataManagerSesion dataManagerSesion;
    @Getter
    @Setter
    @Inject
    private RolControllerBb rolControllerBb;

    @EJB
    private RolServicio rolServicio;

    private RolServicio getFacade() {
        return rolServicio;
    }

    private List<Rol> items;

    public RolController() {
        items = new ArrayList<>();
    }

    @PostConstruct
    public void postConstructor() {
        items = rolServicio.listarTodo();
    }

    public Rol prepareCreate() {
        rolControllerBb.nuevoRol();
        return rolControllerBb.getRol();
    }
    
    public void clear() {
        rolControllerBb.setRol(null);
    }

    public void create() throws ServicioExcepcion {
        if (rolServicio.validarNombreCrear(rolControllerBb.getRol().getRolNombre())) {
            rolControllerBb.getRol().setRolUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RolCreated"));
        } else {
            addErrorMessage("El Nombre ingresado ya existe.");
        }
//        if (!JsfUtil.isValidationFailed()) {
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
        clear();
    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (rolControllerBb.getRol() != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {

                    rolServicio.edit(rolControllerBb.getRol());
                } else {
                    rolServicio.remove(rolControllerBb.getRol());
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

    public void update() throws ServicioExcepcion {
        if (rolServicio.validarNombreEditar(rolControllerBb.getRol().getRolId(), rolControllerBb.getRol().getRolNombre())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RolUpdated"));
        } else {
            addErrorMessage("El Nombre ingresado ya existe.");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RolDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            rolControllerBb.setRol(null); // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Rol> getItems() throws ServicioExcepcion {
        items = rolServicio.listarTodo();
        return items;
    }

    public void setItems(List<Rol> listaRols) {
        this.items = listaRols;
    }

    public Rol getRol(java.lang.Long id) {
        return getFacade().find(new Rol(), id);
    }

    public List<Rol> getItemsAvailableSelectMany() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    public List<Rol> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Rol.class)
    public static class RolControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RolController controller = (RolController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rolController");
            return controller.getRol(getKey(value));
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
            if (object instanceof Rol) {
                Rol o = (Rol) object;
                return getStringKey(o.getRolId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Rol.class.getName()});
                return null;
            }
        }

    }

}
