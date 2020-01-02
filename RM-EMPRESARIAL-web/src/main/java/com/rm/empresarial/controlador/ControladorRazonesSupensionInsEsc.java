/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoSuspension;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteSuspension;
import com.rm.empresarial.servicio.TipoSuspensionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteSuspensionServicio;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorRazonesSuspensionInsEsc")
@ViewScoped
public class ControladorRazonesSupensionInsEsc implements Serializable {

    @Inject
    private DataManagerSesion dataManagerSesion;

    @Inject
    private TramiteUtil tramiteUtil;

    @EJB
    private TipoSuspensionServicio servicioTipoSuspension;
    @EJB
    private TramiteServicio servicioTramite;

    @EJB
    private TramiteSuspensionServicio servicioTramiteSuspension;

    @Getter
    @Setter
    private List<Tramite> listaTramitesIns;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private Boolean togglePanel;

    public ControladorRazonesSupensionInsEsc() {
        togglePanel = false;
    }

    @PostConstruct
    public void postConstructor() {

    }

    public List<Tramite> getListaTramitesIns() throws ServicioExcepcion {
        if (dataManagerSesion.getUsuarioLogeado().getRolId().getRolId() == 1) {
            return servicioTramite.buscarTramitePorVariosEstados();
        } else {
            return servicioTramite.listaTramitePor_Usuario_VariosEstados(dataManagerSesion.getUsuarioLogeado().getUsuId());
        }
    }

    public void seleccionarTramite() {
        //tramiteseleccionado enviado como param desde la vista
    }

}
