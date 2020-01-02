package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.DocumentoEntregaTramite;
import com.rm.empresarial.servicio.DocumentoEntregaTramiteServicio;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;

@Named("controladorDocumentoEntregaTramite")
@ViewScoped
public class ControladorDocumentoEntregaTramite implements Serializable {

    @EJB
    private DocumentoEntregaTramiteServicio servicioDocumentoEntregaTramite;
    private List<DocumentoEntregaTramite> items = null;
    private DocumentoEntregaTramite selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorDocumentoEntregaTramite() {
    }

    public DocumentoEntregaTramite getSelected() {
        return selected;
    }

    public void setSelected(DocumentoEntregaTramite selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private DocumentoEntregaTramiteServicio getFacade() {
        return servicioDocumentoEntregaTramite;
    }

    public DocumentoEntregaTramite prepareCreate() {
        selected = new DocumentoEntregaTramite();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioDocumentoEntregaTramite.validacionIngresarCrear(selected.getDteId().getDteId(), selected.getTtrId().getTtrId())) {
            selected.setDetFHR(Calendar.getInstance().getTime());
            selected.setDetUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("DocumentoEntregaTramiteCreated"));
        } else {
            addErrorMessage("Documento Entrega ya asignado al Tipo de Trámite");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (servicioDocumentoEntregaTramite.validacionIngresarEditar(selected.getDetId(), selected.getDteId().getDteId(), selected.getTtrId().getTtrId())) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("DocumentoEntregaTramiteUpdated"));
        } else {
            addErrorMessage("Documento Entrega ya asignado al Tipo de Trámite");
        }
        
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("DocumentoEntregaTramiteDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<DocumentoEntregaTramite> getItems() throws ServicioExcepcion {
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

    public DocumentoEntregaTramite getDocumentoEntregaTramite(java.lang.Long id) {
        return getFacade().find(new DocumentoEntregaTramite(), id);
    }

    public List<DocumentoEntregaTramite> getItemsAvailableSelectMany() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    public List<DocumentoEntregaTramite> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = DocumentoEntregaTramite.class)
    public static class DocumentoEntregaTramiteControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorDocumentoEntregaTramite controller = (ControladorDocumentoEntregaTramite) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorDocumentoEntregaTramite");
            return controller.getDocumentoEntregaTramite(getKey(value));
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
            if (object instanceof DocumentoEntregaTramite) {
                DocumentoEntregaTramite o = (DocumentoEntregaTramite) object;
                return getStringKey(o.getDetId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), DocumentoEntregaTramite.class.getName()});
                return null;
            }
        }

    }

}
