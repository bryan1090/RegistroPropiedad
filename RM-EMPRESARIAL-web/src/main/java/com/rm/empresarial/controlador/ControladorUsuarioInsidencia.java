package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.modelo.UsuarioInsidencia;
import com.rm.empresarial.servicio.IncidenciaServicio;
import com.rm.empresarial.servicio.UsuarioInsidenciaServicio;
import com.rm.empresarial.servicio.UsuarioServicio;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("controladorUsuarioInsidencia")
@SessionScoped
public class ControladorUsuarioInsidencia implements Serializable {

    @EJB
    private UsuarioInsidenciaServicio servicioUsuarioInsidencia;
    @EJB
    private UsuarioServicio servicioUsuario;
    @EJB
    private IncidenciaServicio servicioIncidencia;
    private List<UsuarioInsidencia> items = null;
    private UsuarioInsidencia selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private List<UsuarioInsidencia> listaUsuarioInsidencia;
    @Getter
    @Setter
    private List<Usuario> listaUsuario;

    @PostConstruct
    public void postControladorUsuarioInsidencia() {
        cargarListasCombos();
    }

    public ControladorUsuarioInsidencia() {
    }

    public UsuarioInsidencia getSelected() {
        return selected;
    }

    public void setSelected(UsuarioInsidencia selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UsuarioInsidenciaServicio getFacade() {
        return servicioUsuarioInsidencia;
    }

    public UsuarioInsidencia prepareCreate() {
        selected = new UsuarioInsidencia();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() {
        persist(com.rm.empresarial.controlador.util.JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioInsidenciaCreated"));

        if (!com.rm.empresarial.controlador.util.JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() {
        persist(com.rm.empresarial.controlador.util.JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("UsuarioInsidenciaUpdated"));
        clear();
    }

    public void destroy() {
        persist(com.rm.empresarial.controlador.util.JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("UsuarioInsidenciaDeleted"));
        if (!com.rm.empresarial.controlador.util.JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<UsuarioInsidencia> getItems() throws ServicioExcepcion {
        if (items == null) {
            items = getFacade().listarTodo();

        }
        return items;
    }

    private void persist(com.rm.empresarial.controlador.util.JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != com.rm.empresarial.controlador.util.JsfUtil.PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage(msg);
                } else {
                    com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public UsuarioInsidencia getUsuarioInsidencia(Long id) {
        return getFacade().find(new UsuarioInsidencia(), id);
    }
//
//    public List<UsuarioInsidencia> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//

    public List<UsuarioInsidencia> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = UsuarioInsidencia.class)
    public static class ControladorUsuarioInsidenciaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorUsuarioInsidencia controller = (ControladorUsuarioInsidencia) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorUsuarioInsidencia");
            return controller.getUsuarioInsidencia(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = new java.lang.Long(value);
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
            if (object instanceof UsuarioInsidencia) {
                UsuarioInsidencia o = (UsuarioInsidencia) object;
                return getStringKey(o.getUinId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), UsuarioInsidencia.class.getName()});
                return null;
            }
        }

    }

    public void cargarListasCombos() {
        setListaUsuarioInsidencia(servicioUsuarioInsidencia.listarTodo());
        setListaUsuario(servicioUsuario.listarTodo());
    }

}
