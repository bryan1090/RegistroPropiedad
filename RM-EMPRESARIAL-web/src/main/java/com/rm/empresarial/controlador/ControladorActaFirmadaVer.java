/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.cargaDiferida.ModeloCargaDiferidaActa;
import com.rm.empresarial.controlador.cargaDiferida.TramiteAccionLazyDataModel;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.UtilitarioPdf;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorActaFirmadaVer")
@ViewScoped
public class ControladorActaFirmadaVer implements Serializable {

    /**
     * Creates a new instance of ControladorActaFirmadaVer
     */
    @Inject
    private UtilitarioPdf utilitarioPdf;

    @EJB
    private ActaServicio servicioActa;

    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;
    @Getter
    @Setter
    private List<Acta> listaActasTabla;

    @Getter
    @Setter
    private Repertorio repertorioSeleccionado;

    @Getter
    @Setter
    private Acta actaSeleccionada;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @Getter
    @Setter
    private List<TramiteAccion> listaTramiteAccion;

    @Inject
    @Getter
    @Setter
    ModeloCargaDiferidaActa modeloCargaDiferidaActa;

    public ControladorActaFirmadaVer() {
    }

    @PostConstruct
    public void postControladorActaFirmadaVer() {

    }

    public void buscarActa() throws ServicioExcepcion {
        try {
//            listaActasTabla= modeloCargaDiferidaActa.getListaActa();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            JsfUtil.addErrorMessage("Error, No se encontró actas.");
        }
    }

    public void abrirArchivo() {
        utilitarioPdf.abrirPDFenServidorComoNuevoTab("FIRMADIGITAL", actaSeleccionada.getActNumero().toString() + "_firmado");
//        abrirPDFenServidorComoNuevoTab("FIRMADIGITAL", actaSeleccionada.getActNumero().toString() + "_firmado");
    }

    public void verActa(Acta acta) {
        System.out.println("com.rm.empresarial.controlador.ControladorActaFirmadaVer.verActa()");
    }

}
