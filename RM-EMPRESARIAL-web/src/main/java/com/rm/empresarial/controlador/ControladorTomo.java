package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Tomo;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.ConfigServicio;
import com.rm.empresarial.servicio.TomoServicio;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("controladorTomo")
@ViewScoped
public class ControladorTomo implements Serializable {

    @EJB
    private TomoServicio servicioTomo;
    private List<Tomo> items = null;
    private Tomo selected;
    
    @Setter
    private List<ConfigDetalle> listaConfigDetalle;
    
    @Inject
    DataManagerSesion dataManagerSesion;
    
    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;

    public Tomo getSelected() {
        return selected;
    }

    public void setSelected(Tomo selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TomoServicio getFacade() {
        return servicioTomo;
    }

    public Tomo prepareCreate() {
        selected = new Tomo();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear (){
        setSelected(null);
    }
    
    public List<ConfigDetalle> getListaConfigDetalle() throws ServicioExcepcion{
        setListaConfigDetalle(servicioConfigDetalle.listarPorConfigDesc("ANIO"));
        return listaConfigDetalle;
    }

    public void create() {
        selected.setTomUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        selected.setTomFHR(Calendar.getInstance().getTime());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TomoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TomoUpdated"));
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TomoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Tomo> getItems() throws ServicioExcepcion {
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

    public Tomo getTomo(java.lang.Long id) {
        return getFacade().find(new Tomo(), id);
    }

    public List<Tomo> getItemsAvailableSelectMany() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    public List<Tomo> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Tomo.class)
    public static class ControladorTomoConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTomo controller = (ControladorTomo) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorTomo");
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
