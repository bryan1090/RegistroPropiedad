package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoIncidencia;
import com.rm.empresarial.servicio.TipoIncidenciaServicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
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

@Named("controladorTipoIncidencia")
@ViewScoped
public class ControladorTipoIncidencia implements Serializable {

    @EJB
    private TipoIncidenciaServicio servicioTipoIncidenciaServicio;
    private List<TipoIncidencia> items = null;
    private TipoIncidencia selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private List<String> listaCodigoHijo = new ArrayList<>();

    @Getter
    @Setter
    private String codigoHijo;

    public ControladorTipoIncidencia() {
    }

    @PostConstruct
    public void inicializar() {
        cargarCodigoHijo();
    }

    public TipoIncidencia getSelected() {
        return selected;
    }

    public void setSelected(TipoIncidencia selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoIncidenciaServicio getFacade() {
        return servicioTipoIncidenciaServicio;
    }

    public TipoIncidencia prepareCreate() {
        selected = new TipoIncidencia();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void cargarCodigoHijo() {
        listaCodigoHijo.add("COMPARECIENTES");//CODIGO HIJO = 1
        listaCodigoHijo.add("FECHA OTORGAMIENTO");//CODIGO HIJO = 2
        listaCodigoHijo.add("ANTECEDENTES");//CODIGO HIJO= 3
        listaCodigoHijo.add("OBJETO CONTRATO");//CODIGO HIJO = 4
        listaCodigoHijo.add("LINDEROS");//CODIGO HIJO= 5
        listaCodigoHijo.add("CUANTIAS");//CODIGO HIJO= 6
        listaCodigoHijo.add("GRAVAMENES");//CODIGO HIJO= 7
        listaCodigoHijo.add("OBSERVACIONES");//CODIGO HIJO= 8

    }

    public void setCodigoHijo() {
        switch (codigoHijo) {
            case "COMPARECIENTES":
                selected.setTipCodigoHijo("1");
                break;
            case "FECHA OTORGAMIENTO":
                selected.setTipCodigoHijo("2");
                break;
            case "ANTECEDENTES":
                selected.setTipCodigoHijo("3");
                break;
            case "OBJETO CONTRATO":
                selected.setTipCodigoHijo("4");
                break;
            case "LINDEROS":
                selected.setTipCodigoHijo("5");
                break;
            case "CUANTIAS":
                selected.setTipCodigoHijo("6");
                break;
            case "GRAVAMENES":
                selected.setTipCodigoHijo("7");
                break;
            case "OBSERVACIONES":
                selected.setTipCodigoHijo("8");
                break;
            default:
                selected.setTipCodigoHijo("0");    
                break;
                
        }

    }

    public void create() {
        if (!selected.getTipCodigoPadre().trim().isEmpty()) {
            setCodigoHijo();
            if(selected.getTipCodigoPadre().trim().equals("0")){
                selected.setTipCodigoHijo("0");
            }            
            selected.setTidFHR(Calendar.getInstance().getTime());
            selected.setTidUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoIncidenciaCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
            clear();
        } else {
            JsfUtil.addErrorMessage("Ingrese el Código Padre");
        }
    }

    public void update() {
        if (!selected.getTipCodigoPadre().trim().isEmpty()) {
            setCodigoHijo();
            if(selected.getTipCodigoPadre().trim().equals("0")){
                selected.setTipCodigoHijo("0");
            }
           
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoIncidenciaUpdated"));
            clear();
        } else {
            JsfUtil.addErrorMessage("Ingrese el Código Padre");
        }
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoIncidenciaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoIncidencia> getItems() throws ServicioExcepcion {
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

    public TipoIncidencia getTipoIncidencia(java.lang.Long id) {
        return getFacade().find(new TipoIncidencia(), id);
    }

    public List<TipoIncidencia> getItemsAvailableSelectMany() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    public List<TipoIncidencia> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoIncidencia.class)
    public static class TipoIncidenciaControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoIncidencia controller = (ControladorTipoIncidencia) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorTipoIncidencia");
            return controller.getTipoIncidencia(getKey(value));
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
            if (object instanceof TipoIncidencia) {
                TipoIncidencia o = (TipoIncidencia) object;
                return getStringKey(o.getTidId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoIncidencia.class.getName()});
                return null;
            }
        }

    }

}
