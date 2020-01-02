/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.bb.ReTramiteProformaBb;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteUsuarioServicio;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author WILSON
 */
@ViewScoped
@Named
public class ControladorCopiaModelo extends JsfUtil implements Serializable {

    private static final long serialVersionUID = 112889626544006816L;

    @EJB
    private TramiteServicio tramiteServicio;

    @EJB
    private TramiteUsuarioServicio tramiteUsuarioServicio;

    @EJB
    private TramiteDetalleServicio tramiteDetalleServicio;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Inject
    @Getter
    @Setter
    private ReTramiteProformaBb reTramiteProformaBb;

    @Inject
    private DataManagerSesion dataManagerSesion;

    @PostConstruct
    public void inicializar() {
        getTramitesControladorBb().inicializar();
        getTramitesControladorBb().setUsuario(dataManagerSesion.getUsuarioLogeado());
        listarTramites();

    }

    public void listarTramites() {
        try {
            if (getTramitesControladorBb().getFiltro()) {
                getTramitesControladorBb().setListaTramiteUsuario(tramiteUsuarioServicio.buscarTodos());

            } else {
                getTramitesControladorBb().setListaTramiteUsuario(tramiteUsuarioServicio.buscarTramitePorModeloTodos());
            }
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCopiaModelo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelect(SelectEvent event) throws ServicioExcepcion {
        getTramitesControladorBb().setCopia(Boolean.TRUE);

        getTramitesControladorBb().setTramite(tramiteServicio.buscarTramitePorNumero(((TramiteUsuario) event.getObject()).getTraNumero().getTraNumero()));

        if (getTramitesControladorBb().getTramite() != null) {

            getTramitesControladorBb().setListaTramitesDetalle(tramiteDetalleServicio.buscarPorNumeroDeTramite(((TramiteUsuario) event.getObject()).getTraNumero().getTraNumero()));
            if (getTramitesControladorBb().getTramite().getTraModelo().equals("S")) {
                getTramitesControladorBb().setModelo(Boolean.TRUE);
            }
        } else {
            getTramitesControladorBb().setModelo(Boolean.FALSE);
        }

    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void actualizarTramite() throws ServicioExcepcion {
        actualizarTramiteModelo();
        tramiteServicio.guardar(getTramitesControladorBb().getTramite());
        listarTramites();
    }

    public void actualizarTramiteModelo() {
        if (getTramitesControladorBb().getModelo()) {
            getTramitesControladorBb().getTramite().setTraModelo("S");
        } else {
            getTramitesControladorBb().getTramite().setTraModelo("N");
        }
    }
}
