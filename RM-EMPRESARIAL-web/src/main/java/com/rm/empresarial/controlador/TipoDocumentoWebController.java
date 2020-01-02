package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.TipoDocumentoWeb;
import com.rm.empresarial.servicio.TipoDocumentoWebServicio;
import com.rm.empresarial.servicio.TipoLibroServicio;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoDocumentoWeb;

import java.io.Serializable;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.List;
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
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("tipoDocumentoWebController")
@ViewScoped
public class TipoDocumentoWebController implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @EJB
    private TipoDocumentoWebServicio servicioTipoDocumentoWeb;
    private List<TipoDocumentoWeb> items = null;
    private TipoDocumentoWeb selected;

    public TipoDocumentoWebController() {
    }

    public TipoDocumentoWeb getSelected() {
        return selected;
    }

    public void setSelected(TipoDocumentoWeb selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoDocumentoWebServicio getFacade() {
        return servicioTipoDocumentoWeb;
    }

    public TipoDocumentoWeb prepareCreate() {
        selected = new TipoDocumentoWeb();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioTipoDocumentoWeb.validarNombreCrear(selected.getTdwNombre())) {
            selected.setTdwFHR(Calendar.getInstance().getTime());
            selected.setTdwUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoDocumentoWebCreated"));
        } else {
            addErrorMessage("El Nombre ingresado ya existe");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioTipoDocumentoWeb.validarNombreEditar(selected.getTdwId(), selected.getTdwNombre())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoDocumentoWebUpdated"));
        } else {
            addErrorMessage("El Nombre ingresado ya existe");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoDocumentoWebDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoDocumentoWeb> getItems() throws ServicioExcepcion {
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

    public TipoDocumentoWeb getTipoDocumentoWeb(java.lang.Long id) {
        return getFacade().find(new TipoDocumentoWeb(), id);
    }

//    public List<TipoDocumentoWeb> getItemsAvailableSelectMany() throws ServicioExcepcion {
//        return getFacade().ListarTodo();
//    }
    public List<TipoDocumentoWeb> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoDocumentoWeb.class)
    public static class TipoDocumentoWebControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TipoDocumentoWebController controller = (TipoDocumentoWebController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "hostMailController");
            return controller.getTipoDocumentoWeb(getKey(value));
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
            if (object instanceof TipoDocumentoWeb) {
                TipoDocumentoWeb o = (TipoDocumentoWeb) object;
                return getStringKey(o.getTdwId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoDocumentoWeb.class.getName()});
                return null;
            }
        }

    }

}
