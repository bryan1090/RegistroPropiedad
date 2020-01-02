package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.dao.CantonDao;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Canton;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.servicio.InstitucionServicio;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

@Named("institucionController")
@ViewScoped
public class ControladorInstitucion implements Serializable {

    @EJB
    private InstitucionDao institucionServicio;
    private List<Institucion> items = null;
    private Institucion selected;
    
    @EJB
    private CantonDao cantonDao;
    
      
    @Getter
    @Setter
    private List<Canton> listaCanton = new ArrayList<>();
    
    @Getter
    @Setter
    private Canton cantonSeleccionado = new Canton();

    public ControladorInstitucion() {
    }

    @PostConstruct
    public void inicializar(){
        try {
            cargarCarton();
        } catch (Exception e) {
        }
        
    }
    
    public Institucion getSelected() {
        return selected;
    }

    public void setSelected(Institucion selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private InstitucionDao getFacade() {
        return institucionServicio;
    }

    public Institucion prepareCreate() {
        selected = new Institucion();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear() {
        setSelected(null);
    }
    
    public void cargarCarton() throws ServicioExcepcion{
        listaCanton = cantonDao.listarTodo();
    }

    public void create() {
        Institucion inst = institucionServicio.obtenerUltimoId();
        Long idInstitucion = Long.valueOf(0);
        if(inst != null){
            idInstitucion =  Long.sum(inst.getInsId(), 1);
        }
        selected.setInsId(idInstitucion);
        selected.setInsCanId(cantonSeleccionado.getCanId());
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("InstitucionCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() {
        selected.setInsCanId(cantonSeleccionado.getCanId());
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("InstitucionUpdated"));
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("InstitucionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Institucion> getItems() throws ServicioExcepcion {
        if (items == null) {
            items = getFacade().listarTodo();
        }
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

    public Institucion getInstitucion(java.lang.Long id) {
        return getFacade().find(new Institucion(), id);
    }

//    public List<Institucion> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
    public List<Institucion> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Institucion.class)
    public static class InstitucionControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorInstitucion controller = (ControladorInstitucion) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "institucionController");
            return controller.getInstitucion(getKey(value));
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
            if (object instanceof Institucion) {
                Institucion o = (Institucion) object;
                return getStringKey(o.getInsId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Institucion.class.getName()});
                return null;
            }
        }

    }

}
