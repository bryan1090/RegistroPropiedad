package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Menu;
import com.rm.empresarial.modelo.RolMenu;
import com.rm.empresarial.servicio.MenuServicio;
import com.rm.empresarial.servicio.RolMenuServicio;
import com.rm.empresarial.servicio.RolServicio;

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
import javax.faces.bean.RequestScoped;
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
import org.primefaces.event.CloseEvent;

@Named("rolMenuController")
@ViewScoped
public class ControladorRolMenu implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @EJB
    private RolMenuServicio servicioRolMenu;
//    @EJB
//    private MenuServicio servicioMenu;
//    @EJB
//    private RolServicio ejbFacadeRol;
    private List<RolMenu> items = null;
    //private PaginationHelper pagination;
//    private int selectedItemIndex;
    private RolMenu selected;

    @Getter
    @Setter
    private Boolean rolMenInsert;
    @Getter
    @Setter
    private Boolean rolMenUpdate;
    @Getter
    @Setter
    private Boolean rolMenDelete;
    @Getter
    @Setter
    private Boolean rolMenExcel;
    @Getter
    @Setter
    private Boolean rolMenPdf;
    @Getter
    @Setter
    private Boolean rolMenAcceso;
    @Getter
    @Setter
    private Boolean rolMenDisplay;

    public ControladorRolMenu() {
    }

    private RolMenuServicio getFacade() {
        return servicioRolMenu;
    }

    public RolMenu getSelected() {
        return selected;
    }

    public void setSelected(RolMenu selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    public RolMenu prepareCreate() {
        selected = new RolMenu();
        initializeEmbeddableKey();
        return selected;
    }

    public void prepareEdit() {
        setRolMenAcceso(selected.getRolMenAcceso().equals("S"));
        setRolMenDelete(selected.getRolMenDelete().equals("S"));
        setRolMenDisplay(selected.getRolMenDisplay().equals("S"));
        setRolMenExcel(selected.getRolMenExcel().equals("S"));
        setRolMenInsert(selected.getRolMenInsert().equals("S"));
        setRolMenPdf(selected.getRolMenPdf().equals("S"));
        setRolMenUpdate(selected.getRolMenUpdate().equals("S"));
    }

    public void clear() {
        setRolMenAcceso(false);
        setRolMenDelete(false);
        setRolMenDisplay(false);
        setRolMenExcel(false);
        setRolMenInsert(false);
        setRolMenPdf(false);
        setRolMenUpdate(false);
        setSelected(null);
    }

    public void handleClose(CloseEvent event) {
        clear();
    }

    public void create() throws ServicioExcepcion {

        if (rolMenAcceso) {
            selected.setRolMenAcceso("S");
        } else {
            selected.setRolMenAcceso("N");
        }
        if (rolMenDelete) {
            selected.setRolMenDelete("S");
        } else {
            selected.setRolMenDelete("N");
        }
        if (rolMenDisplay) {
            selected.setRolMenDisplay("S");
        } else {
            selected.setRolMenDisplay("N");
        }
        if (rolMenExcel) {
            selected.setRolMenExcel("S");
        } else {
            selected.setRolMenExcel("N");
        }
        if (rolMenInsert) {
            selected.setRolMenInsert("S");
        } else {
            selected.setRolMenInsert("N");
        }
        if (rolMenPdf) {
            selected.setRolMenPdf("S");
        } else {
            selected.setRolMenPdf("N");
        }
        if (rolMenUpdate) {
            selected.setRolMenUpdate("S");
        } else {
            selected.setRolMenUpdate("N");
        }
        selected.setRolMenFHR(Calendar.getInstance().getTime());
        selected.setRolMenUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        if (servicioRolMenu.existeRolMenu(selected.getMenId(), selected.getRolId())) {
            addErrorMessage("RolMenu ya existe");
        } else {
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RolMenuCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }

        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        if (rolMenAcceso) {
            selected.setRolMenAcceso("S");
        } else {
            selected.setRolMenAcceso("N");
        }
        if (rolMenDelete) {
            selected.setRolMenDelete("S");
        } else {
            selected.setRolMenDelete("N");
        }
        if (rolMenDisplay) {
            selected.setRolMenDisplay("S");
        } else {
            selected.setRolMenDisplay("N");
        }
        if (rolMenExcel) {
            selected.setRolMenExcel("S");
        } else {
            selected.setRolMenExcel("N");
        }
        if (rolMenInsert) {
            selected.setRolMenInsert("S");
        } else {
            selected.setRolMenInsert("N");
        }
        if (rolMenPdf) {
            selected.setRolMenPdf("S");
        } else {
            selected.setRolMenPdf("N");
        }
        if (rolMenUpdate) {
            selected.setRolMenUpdate("S");
        } else {
            selected.setRolMenUpdate("N");
        }

        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RolMenuUpdated"));
        clear();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RolMenuDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<RolMenu> getItems() throws ServicioExcepcion {
        items = getFacade().ListarTodo();
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

    public RolMenu getRolMenu(Long id) {
        return getFacade().find(new RolMenu(), id);
    }

    /*
    public List<RolMenu> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }
     */
    public List<RolMenu> getItemsAvailableSelectOne() throws ServicioExcepcion {
        return getFacade().ListarTodo();
    }

    @FacesConverter(forClass = RolMenu.class)
    public static class RolMenuControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ControladorRolMenu controller = (ControladorRolMenu) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "rolMenuController");
            return controller.getRolMenu(getKey(value));
        }

        Long getKey(String value) {
            Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof RolMenu) {
                RolMenu o = (RolMenu) object;
                return getStringKey(o.getRolMenId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), RolMenu.class.getName()});
                return null;
            }
        }

    }
}
