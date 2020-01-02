package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.dao.ActaDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.TipoActaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.TipoActa;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.TipoLibroServicio;

import java.io.Serializable;
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
import lombok.Getter;
import lombok.Setter;

@Named("actaController")
@SessionScoped
public class ActaController implements Serializable {

    @EJB
    private ActaDao servicioActa;
    private List<Acta> items = null;
    private Acta selected;
    
    @EJB
    private PropiedadDao servicioPropiedad;
    
    @EJB
    private TipoActaDao servicioTipoActa;
    
    @EJB
    private TipoLibroServicio servicioTipoLibro;
    
    @EJB
    private RepertorioDao servicioRepertorio;

    @Getter
    @Setter
    private String prdMatricula;
    
    @Getter
    @Setter
    private String repNumero;
    
    @Getter
    @Setter
    private String tpaId;
    
    @Getter
    @Setter
    private String tplId;

    public ActaController() {
    }

    public Acta getSelected() {
        return selected;
    }

    public void setSelected(Acta selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ActaDao getFacade() {
        return servicioActa;
    }

    public Acta prepareCreate() {
        selected = new Acta();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() throws ServicioExcepcion {
        selected.setRepNumero(servicioRepertorio.encontrarRepertorioPorId(repNumero));
        selected.setTpaId(servicioTipoActa.encontrarTipoActaPorId(tpaId));
        selected.setPrdMatricula(servicioPropiedad.encontrarPropiedadPorId(prdMatricula));
        selected.setTplId(servicioTipoLibro.encontrarTipoLibroPorId(tplId));
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ActaCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() throws ServicioExcepcion {
        selected.setRepNumero(servicioRepertorio.encontrarRepertorioPorId(repNumero));
        selected.setTpaId(servicioTipoActa.encontrarTipoActaPorId(tpaId));
        selected.setPrdMatricula(servicioPropiedad.encontrarPropiedadPorId(prdMatricula));
        selected.setTplId(servicioTipoLibro.encontrarTipoLibroPorId(tplId));
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ActaUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ActaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Acta> getItems() throws ServicioExcepcion {
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

    public Acta getActa(java.lang.Long id) {
        return getFacade().find(new Acta(), id);
    }

//    public List<Acta> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<Acta> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Acta.class)
    public static class ActaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ActaController controller = (ActaController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "actaController");
            return controller.getActa(getKey(value));
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
            if (object instanceof Acta) {
                Acta o = (Acta) object;
                return getStringKey(o.getActNumero());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Acta.class.getName()});
                return null;
            }
        }

    }

}
