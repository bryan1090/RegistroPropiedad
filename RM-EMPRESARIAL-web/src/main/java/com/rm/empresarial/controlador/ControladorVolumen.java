package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.Tomo;
import com.rm.empresarial.modelo.Volumen;
import com.rm.empresarial.servicio.TomoServicio;
import com.rm.empresarial.servicio.VolumenServicio;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("controladorVolumen")
@ViewScoped
public class ControladorVolumen implements Serializable {

    @EJB
    private VolumenServicio servicioVolumen;
    @EJB
    private TomoServicio servicioTomo;
    private List<Volumen> items = null;
    private Volumen selected;
    
    @Getter
    @Setter
    private List<Tomo> listaTomo;
    
    @Getter
    @Setter
    private Long tplId;
    
    @Inject
    DataManagerSesion dataManagerSesion;
    

    public ControladorVolumen() {
    }

    public Volumen getSelected() {
        return selected;
    }

    public void setSelected(Volumen selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private VolumenServicio getFacade() {
        return servicioVolumen;
    }

    public Volumen prepareCreate() {
        selected = new Volumen();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void prepareEdit() throws ServicioExcepcion{
        setTplId(selected.getTomId().getTplId().getTplId());
        cargarListaTomo();
    }
    
    public void clear(){
        setSelected(null);
    }

    public void create() {
        selected.setVleUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        selected.setVleFHR(Calendar.getInstance().getTime());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("VolumenCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("VolumenUpdated"));
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("VolumenDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void cargarListaTomo() throws ServicioExcepcion{
        setListaTomo(servicioTomo.listarPorTplId(getTplId()));
    }

    public List<Volumen> getItems() throws ServicioExcepcion {
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

    public Volumen getVolumen(java.lang.Long id) {
        return getFacade().find(new Volumen(), id);
    }

    public List<Volumen> getItemsAvailableSelectMany() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    public List<Volumen> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Volumen.class)
    public static class ControladorVolumenConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorVolumen controller = (ControladorVolumen) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorVolumen");
            return controller.getVolumen(getKey(value));
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
            if (object instanceof Volumen) {
                Volumen o = (Volumen) object;
                return getStringKey(o.getVleId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Volumen.class.getName()});
                return null;
            }
        }

    }
    
    public Tomo getTomo(Long id) {
        return servicioTomo.find(new Tomo(), id);
    }
    
    @FacesConverter("ConvertidorTomId")
    public static class ControladorTomoConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorVolumen controller = (ControladorVolumen) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorVolumen");
            return controller.getTomo(getKey(value));
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
            if (object instanceof Tomo) {
                Tomo o = (Tomo) object;
                return getStringKey(o.getTomId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Tomo.class.getName()});
                return null;
            }
        }

    }

}
