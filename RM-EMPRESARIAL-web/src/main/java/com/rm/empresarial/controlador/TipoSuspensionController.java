package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.RolControllerBb;
import com.rm.empresarial.bb.TipoSuspensionControllerBb;
import com.rm.empresarial.modelo.TipoSuspension;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Rol;
import com.rm.empresarial.servicio.TipoSuspensionServicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

@Named
@ViewScoped
public class TipoSuspensionController implements Serializable {

    @Inject
    private DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    @Inject
    private TipoSuspensionControllerBb tipoSuspensionControllerBb;

    @EJB
    private TipoSuspensionServicio tipoSuspensionServicio;

    private List<TipoSuspension> lista;

    public TipoSuspensionController() {
        lista = new ArrayList<>();
    }

    @PostConstruct
    public void postConstructor() {

        try {
            lista = tipoSuspensionServicio.listarTodo();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(RolController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public TipoSuspension preCrear() {
        tipoSuspensionControllerBb.nuevoTipoSuspension();
        return tipoSuspensionControllerBb.getTipoSuspension();
    }

    public void clear() {
        tipoSuspensionControllerBb.setTipoSuspension(null);
    }

    public void crear() throws ServicioExcepcion {
        if (tipoSuspensionServicio.validarNombreCrear(tipoSuspensionControllerBb.getTipoSuspension().getTpsNombre())) {
            tipoSuspensionControllerBb.getTipoSuspension().setTpsUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            tipoSuspensionControllerBb.getTipoSuspension().setTpsFHR(Calendar.getInstance().getTime());
            persistir(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TipoSuspensionCreated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        clear();
    }

    public void actualizar() throws ServicioExcepcion {
        if (tipoSuspensionServicio.validarNombreEditar(tipoSuspensionControllerBb.getTipoSuspension().getTpsId(), tipoSuspensionControllerBb.getTipoSuspension().getTpsNombre())) {
            persistir(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TipoSuspensionUpdated"));
        } else {
            addErrorMessage("El nombre ingresado ya existe");
        }
        clear();
    }

    public void eliminar() {
        persistir(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TipoSuspensionDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            tipoSuspensionControllerBb.setTipoSuspension(null); // Remove selection
            lista = null;    // Invalidate list of lista to trigger re-query.
        }
    }

    private void persistir(PersistAction persistAction, String successMessage) {
        if (tipoSuspensionControllerBb.getTipoSuspension() != null) {

            try {
                if (persistAction != PersistAction.DELETE) {
                    tipoSuspensionServicio.edit(tipoSuspensionControllerBb.getTipoSuspension());
                } else {
                    tipoSuspensionServicio.remove(tipoSuspensionControllerBb.getTipoSuspension());
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

    public List<TipoSuspension> getLista() throws ServicioExcepcion {
        lista = tipoSuspensionServicio.listarTodo();
        return lista;
    }

    public void setLista(List<TipoSuspension> listaRols) {
        this.lista = listaRols;
    }

}
