package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoDescuento;
import com.rm.empresarial.servicio.TipoDescuentoServicio;
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
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("tipoDescuentoController")
@SessionScoped
public class ControladorTipoDescuento implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;
    @Inject
    private PersonaUtil personaUtil;
    @Getter
    @Setter
    private Boolean esPublico = false;
    @EJB
    //private contrl.TipoDescuentoFacade ejbFacade;
    private TipoDescuentoServicio tipoDescuentoServicio;
    private List<TipoDescuento> items = null;
    private TipoDescuento selected;

    public ControladorTipoDescuento() {
    }

    public TipoDescuento getSelected() {
        return selected;
    }

    public void setSelected(TipoDescuento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TipoDescuentoServicio getFacade() {
        return tipoDescuentoServicio;
    }

    public TipoDescuento prepareCreate() {
        getTramitesControladorBb().setIdentificacion("");
        getTramitesControladorBb().setNombrePersona("");
        selected = new TipoDescuento();
        initializeEmbeddableKey();
        return selected;
    }

    public void prepareEdit() {
        getTramitesControladorBb().setIdentificacion("");
        getTramitesControladorBb().setNombrePersona("");
        crearNombrePersona(selected.getTpdIdentificacion());

    }

    public void clear() {
        setSelected(null);
        getTramitesControladorBb().setIdentificacion("");
        getTramitesControladorBb().setNombrePersona("");
        esPublico = false;
    }
    
    public void refrescar() throws ServicioExcepcion{
        items = tipoDescuentoServicio.listarTodo();
        
    }

    public void create() throws ServicioExcepcion {
        getTramitesControladorBb().setPersona(personaUtil.obtenerDatosPersona(getTramitesControladorBb().getIdentificacion()));
        if (getTramitesControladorBb().getPersona() != null) {
            selected.setTpdIdPersona(getTramitesControladorBb().getPersona().getPerId());
            selected.setTpdIdentificacion(getTramitesControladorBb().getPersona().getPerIdentificacion());
        }
            selected.setTpdDescripcion(selected.getTpdDescripcion().toUpperCase());
            selected.setTpdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            selected.setTpdFHR(Calendar.getInstance().getTime());
            selected.setTpdEstado("A");
            if (esPublico) {
                selected.setTpdPublico("S");
            } else {
                selected.setTpdPublico("N");
            }
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoDescuentoCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
            clear();       
    }

    public void update() throws ServicioExcepcion {
        getTramitesControladorBb().setPersona(personaUtil.obtenerDatosPersona(getTramitesControladorBb().getIdentificacion()));
        if (getTramitesControladorBb().getPersona() != null) {
            selected.setTpdIdPersona(getTramitesControladorBb().getPersona().getPerId());
            selected.setTpdIdentificacion(getTramitesControladorBb().getPersona().getPerIdentificacion());
        }
            selected.setTpdDescripcion(selected.getTpdDescripcion().toUpperCase());
            if (esPublico) {
                selected.setTpdPublico("S");
            } else {
                selected.setTpdPublico("N");
            }
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoDescuentoUpdated"));
            clear();
            refrescar();
        
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoDescuentoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TipoDescuento> getItems() throws ServicioExcepcion {
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

    public void nombrePersona() {
        getTramitesControladorBb().setNombrePersona(getTramitesControladorBb().getPersona().getPerApellidoPaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerApellidoMaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerNombre().trim());
        String Nombre = getTramitesControladorBb().getNombrePersona();
        getTramitesControladorBb().setNombrePersona(Nombre.replace("null", ""));
        getTramitesControladorBb().setEstado(Boolean.TRUE);

    }

    public String crearNombrePersona(String identificacion) {
        getTramitesControladorBb().setIdentificacion(identificacion);
        buscarPersonaCargarNombre();
        return getTramitesControladorBb().getNombrePersona();

    }

    public void buscarPersona() {

        if (getTramitesControladorBb().getIdentificacion() != null && !getTramitesControladorBb().getIdentificacion().equals("")) {
            //getTramitesControladorBb().setIdentificacion(identificacion);            

            try {
                getTramitesControladorBb().setPersona(personaUtil.obtenerDatosPersona(getTramitesControladorBb().getIdentificacion()));
                if (getTramitesControladorBb().getPersona() != null) {
                    if (getTramitesControladorBb().getPersona().getPerIdentificacion() != null) {
                        nombrePersona();
                    } else {
                        getTramitesControladorBb().setIdentificacion(" ");
                        getTramitesControladorBb().setNombrePersona(" ");
                    }
                } else {
                    getTramitesControladorBb().setPersona(null);
                    getTramitesControladorBb().setNombrePersona("");
                    getTramitesControladorBb().setEstado(Boolean.FALSE);
                    addErrorMessage("Persona no existe");
                }

            } catch (Exception e) {
                addErrorMessage("Ingrese Cédula/RUC/Pas");
            }

        } else {
            addErrorMessage("Ingrese Cédula/RUC/Pas");
        }

    }

    public void buscarPersonaCargarNombre() {

        if (getTramitesControladorBb().getIdentificacion() != null && !getTramitesControladorBb().getIdentificacion().equals("")) {
            //getTramitesControladorBb().setIdentificacion(identificacion);            

            try {
                getTramitesControladorBb().setPersona(personaUtil.obtenerDatosPersona(getTramitesControladorBb().getIdentificacion()));
                if (getTramitesControladorBb().getPersona() != null) {
                    if (getTramitesControladorBb().getPersona().getPerIdentificacion() != null) {
                        nombrePersona();
                    } else {
                        getTramitesControladorBb().setIdentificacion(" ");
                        getTramitesControladorBb().setNombrePersona(" ");
                    }
                } else {
                    getTramitesControladorBb().setPersona(null);
                    getTramitesControladorBb().setNombrePersona("");
                    getTramitesControladorBb().setEstado(Boolean.FALSE);
                    addErrorMessage("Persona no existe");
                }

            } catch (Exception e) {
                addErrorMessage("Ingrese Cédula/RUC/Pas");
            }

        } else {
            getTramitesControladorBb().setIdentificacion("");
            getTramitesControladorBb().setNombrePersona("");
        }

    }

    public TipoDescuento getTipoDescuento(Long id) {
        return getFacade().find(new TipoDescuento(), id);
    }

//    public List<TipoDescuento> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
    public List<TipoDescuento> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = TipoDescuento.class)
    public static class TipoDescuentoControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorTipoDescuento controller = (ControladorTipoDescuento) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tipoDescuentoController");
            return controller.getTipoDescuento(getKey(value));
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
            if (object instanceof TipoDescuento) {
                TipoDescuento o = (TipoDescuento) object;
                return getStringKey(o.getTpdId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoDescuento.class.getName()});
                return null;
            }
        }

    }

}
