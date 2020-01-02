package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Incidencia;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.TipoIncidencia;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.IncidenciaServicio;
import com.rm.empresarial.servicio.TipoIncidenciaServicio;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("controladorIncidencia")
@ViewScoped
public class ControladorIncidencia implements Serializable {

    @EJB
    private TipoIncidenciaServicio servicioTipoIncidencia;
    @EJB
    private UsuarioServicio servicioUsuario;
    @EJB
    private IncidenciaServicio servicioIncidencia;
    private List<Incidencia> items = null;
    private Incidencia selected;

    @Inject
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private List<TipoIncidencia> listaTipoIncidencia;
    @Getter
    @Setter
    private List<Usuario> listaUsuario;

    @EJB
    private RepertorioDao repertorioDao;

    @PostConstruct
    public void postControladorIncidencia() {
        cargarListasCombos();
    }

    public ControladorIncidencia() {
    }

    public Incidencia getSelected() {
        return selected;
    }

    public void setSelected(Incidencia selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private IncidenciaServicio getFacade() {
        return servicioIncidencia;
    }

    public Incidencia prepareCreate() {
        selected = new Incidencia();
        initializeEmbeddableKey();
        return selected;
    }

    public void clear() {
        setSelected(null);
    }

    public void create() {
        if (selected.getIncRepNumero() != null && selected.getIncRepNumero() != 0) {
            if (selected.getIncRepFecha() != null) {
                Repertorio repertorio = repertorioDao.encontrarRepertorio_PorNumero_Fecha(selected.getIncRepNumero(), selected.getIncRepFecha());
                if (repertorio != null) {
                    crearRegistro();
                } else {
                    JsfUtil.addErrorMessage("Repertorio no encontrado. Verifique el número de repertorio y la fecha correspondiente");
                }
            } else {
                JsfUtil.addErrorMessage("Ingrese la fecha de Repertorio");
            }
        } else {
            selected.setIncRepNumero(null);
            selected.setIncRepFecha(null);
            crearRegistro();
        }

    }

    public void crearRegistro() {
        selected.setIncFHR(Calendar.getInstance().getTime());
        selected.setIncUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        persist(com.rm.empresarial.controlador.util.JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("IncidenciaCreated"));

        if (!com.rm.empresarial.controlador.util.JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() {
        if (selected.getIncRepNumero() != null && selected.getIncRepNumero() != 0) {
            if (selected.getIncRepFecha() != null) {
                Repertorio repertorio = repertorioDao.encontrarRepertorio_PorNumero_Fecha(selected.getIncRepNumero(), selected.getIncRepFecha());
                if (repertorio != null) {
                    persist(com.rm.empresarial.controlador.util.JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("IncidenciaUpdated"));
                    clear();
                } else {
                    JsfUtil.addErrorMessage("Repertorio no encontrado. Verifique el número de repertorio y la fecha correspondiente");
                }
            } else {
                JsfUtil.addErrorMessage("Ingrese la fecha de Repertorio");
            }
        } else {
            selected.setIncRepNumero(null);
            selected.setIncRepFecha(null);
            persist(com.rm.empresarial.controlador.util.JsfUtil.PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("IncidenciaUpdated"));
            clear();
        }

    }

    public void destroy() {
        persist(com.rm.empresarial.controlador.util.JsfUtil.PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("IncidenciaDeleted"));
        if (!com.rm.empresarial.controlador.util.JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Incidencia> getItems() throws ServicioExcepcion {
        items = getFacade().listarTodo();
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

    public Incidencia getIncidencia(Long id) {
        return getFacade().find(new Incidencia(), id);
    }
//
//    public List<Incidencia> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//

    public List<Incidencia> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().listarTodo();
    }

    @FacesConverter(forClass = Incidencia.class)
    public static class ControladorIncidenciaConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorIncidencia controller = (ControladorIncidencia) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "controladorIncidecia");
            return controller.getIncidencia(getKey(value));
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
            if (object instanceof Incidencia) {
                Incidencia o = (Incidencia) object;
                return getStringKey(o.getIncId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Incidencia.class.getName()});
                return null;
            }
        }

    }

    public void cargarListasCombos() {
        setListaTipoIncidencia(servicioTipoIncidencia.listarTodo());
        setListaUsuario(servicioUsuario.listarTodo());
    }

}
