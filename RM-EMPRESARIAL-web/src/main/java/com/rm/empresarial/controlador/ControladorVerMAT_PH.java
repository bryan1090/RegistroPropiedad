/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.servicio.LinderoServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Prueba
 */
@Named(value = "controladorVerMAT_PH")
@ViewScoped
public class ControladorVerMAT_PH implements Serializable {

    @Getter
    @Setter
    private Propiedad propiedad;
    @Getter
    @Setter
    private Boolean rendPanelPropPH = false;
    @Getter
    @Setter
    private Boolean rendPanelTramite = false;
    @Getter
    @Setter
    private Boolean rendPanelPropiedad = false;
    @Getter
    @Setter
    private Boolean rendPanelRepertorio = false;

    @Getter
    @Setter
    private List<Lindero> listaLindero;

    @EJB
    private PropiedadServicio servicioPropiedad;

    @EJB
    private LinderoServicio servicioLindero;

    public ControladorVerMAT_PH() {
    }

    public void mostrar(TramiteAccion tramAccion) {
        try {
            if (tramAccion.getTmaObservacion().contains("PROPIEDAD CREADA CON MATR")) {
                setPropiedad(servicioPropiedad.encontrarPropiedadPorId(tramAccion.getTmaNumeroDocumento()));
                setListaLindero(servicioLindero.listarPorNumMatricula(tramAccion.getTmaNumeroDocumento()));
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('MatPHDialogo').show();");
                rendPanelPropiedad = true;
                rendPanelPropPH = false;
                rendPanelRepertorio = false;
                rendPanelTramite = false;

            } else if (tramAccion.getTmaObservacion().contains("ACTUALIZACIÓN DEL ESTADO DE TRAMITE")) {
                rendPanelPropiedad = false;
                rendPanelPropPH = false;
                rendPanelRepertorio = false;
                rendPanelTramite = false;

            } else if (tramAccion.getTmaObservacion().contains("ASIGNADO REPERTORIO")) {
                rendPanelPropiedad = false;
                rendPanelPropPH = false;
                rendPanelRepertorio = false;
                rendPanelTramite = false;

            } else if (tramAccion.getTmaObservacion().contains("PROPIEDAD CREADA CON MATRICULA PH")) {
                setPropiedad(servicioPropiedad.encontrarPropiedadPorId(tramAccion.getTmaNumeroDocumento()));
                setListaLindero(servicioLindero.listarPorNumMatricula(tramAccion.getTmaNumeroDocumento()));
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('MatPHDialogo').show();");
                rendPanelPropiedad = true;
                rendPanelPropPH = false;
                rendPanelRepertorio = false;
                rendPanelTramite = false;

            }

        } catch (ServicioExcepcion ex) {
            JsfUtil.addWarningMessage("Propiedad no encontrada");
        }
    }

}
