package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.Menu;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.MenuServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;

import java.io.Serializable;
import java.util.Calendar;
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
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("menuController")
@ViewScoped
public class ControladorMenu implements Serializable {

    @EJB
    private MenuServicio servicioMenu;
    private List<Menu> items = null;
    private Menu selected;

    @Getter
    @Setter
    private String ninguno = null;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorMenu() {
    }

    public Menu getSelected() {
        return selected;
    }

    public void setSelected(Menu selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private MenuServicio getFacade() {
        return servicioMenu;
    }

    public Menu prepareCreate() {
        selected = new Menu();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void clear(){
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        if (servicioMenu.validarIdCrear(selected.getMenId())) {
            if (servicioMenu.validarNombreCrear(selected.getMenNombre())) {
                selected.setMenUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                selected.setMenFHR(Calendar.getInstance().getTime());
                persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MenuCreated"));
            } else {
                addErrorMessage("El Nombre ingresado ya existe.");
            }
        } else {
            addErrorMessage("El Id ingresado ya existe.");
        }
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if(servicioMenu.validarNombreEditar(selected.getMenId(), selected.getMenNombre())){
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MenuUpdated"));
        } else {
            addErrorMessage("El Nombre ingresado ya existe.");
        }
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MenuDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Menu> getItems() throws ServicioExcepcion {
        items = getFacade().Listar();
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

    public Menu getMenu(String id) {
        return getFacade().find(new Menu(), id);
    }

//    public List<Menu> getItemsAvailableSelectMany() throws ServicioExcepcion {
//        return getFacade().ListarTodo();
//    }
    public List<Menu> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().ListarTodo();
    }

    @FacesConverter(forClass = Menu.class)
    public static class MenuControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorMenu controller = (ControladorMenu) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "menuController");
            return controller.getMenu(getKey(value));
        }

        String getKey(String value) {
            String key = value;
            return key;
        }

        String getStringKey(String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Menu) {
                Menu o = (Menu) object;
                return getStringKey(o.getMenId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Menu.class.getName()});
                return null;
            }
        }

    }

}
