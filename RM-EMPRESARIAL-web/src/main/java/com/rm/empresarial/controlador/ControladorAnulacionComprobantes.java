/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.ControladorConsultaComprobantesBb;
import com.rm.empresarial.controlador.util.DescargarArchivo;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.PathDocEleDao;
import com.rm.empresarial.dao.consultaComprobantesDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.EMensaje;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.PathDocEle;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteSuspension;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.EMensajeServicio;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.PathDocEleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteSuspensionServicio;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Wilson Herrera
 */
@ViewScoped
@Named
public class ControladorAnulacionComprobantes extends JsfUtil implements Serializable {

    @EJB
    private FacturaServicio facturaServicio;
    @EJB
    private EMensajeServicio mensajeServicio;
    @EJB
    private consultaComprobantesDao consultaComprobantesDao;
    @EJB
    private FacturaDetalleServicio facturaDetalleServicio;
    @EJB
    private InstitucionServicio institucionServicio;
    @EJB
    private CertificadoCargaServicio certificadoCargaServicio;
    @EJB
    private TramiteServicio tramiteServicio;
    @EJB
    private TramiteSuspensionServicio tramiteSuspensionServicio;

    @Inject
    @Getter
    @Setter
    private ControladorConsultaComprobantesBb consultaComprobantesBb;

    @Inject
    @Getter
    @Setter
    private ControladorGenerarRide controladorGenerarRide;

    @Getter
    @Setter
    private PathDocEle path;

    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;

    @Getter
    @Setter
    private StreamedContent files;

    @Getter
    @Setter
    private Institucion institucion;
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private List<EMensaje> listarespuestaSRI;

    @Getter
    @Setter
    private List<EMensaje> listaVer;

    @Getter
    @Setter
    private EMensaje respuestaSRI;

    @Getter
    @Setter
    private String serie;

    @Getter
    @Setter
    private String numero;

    public void inicializar() {
        consultaComprobantesBb.init();
        listarespuestaSRI = new ArrayList<>();
        listaVer = new ArrayList<>();
    }

    public void obtenerInformacion(EMensaje idmensaje) throws ServicioExcepcion {
        listaVer = new ArrayList<>();
        setRespuestaSRI(mensajeServicio.obtenerPorID(idmensaje.getEMsIdSec()));
        listaVer.add(getRespuestaSRI());
    }

    public void listarPorFechaFacturas() throws ServicioExcepcion, ParseException {
        if (!getConsultaComprobantesBb().getFiltro()) {
            getConsultaComprobantesBb().setListaFactura(facturaServicio.listarFactura(getConsultaComprobantesBb().getFechaIinicio(), getConsultaComprobantesBb().getFechaFin()));
        } else {
            getConsultaComprobantesBb().setListaFactura(facturaServicio.listarPorComprobante(getConsultaComprobantesBb().getNumeroFactura()));
        }

    }

    public void listarPorFechaFacturaWeb() throws ServicioExcepcion, ParseException {
        if (!getConsultaComprobantesBb().getFiltro()) {
            getConsultaComprobantesBb().setListaFacturaWeb(facturaServicio.listarFacturaWeb(getConsultaComprobantesBb().getFechaIinicio(), getConsultaComprobantesBb().getFechaFin(), dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
        } else {
            getConsultaComprobantesBb().setListaFacturaWeb(facturaServicio.FacturaNumeroWeb(getConsultaComprobantesBb().getNumeroFactura(), dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
        }

    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void generarNV() {
        try {
            String numeroFactura = getConsultaComprobantesBb().getFacturaSeleccionada().getFacNumero();
            BigDecimal total = getConsultaComprobantesBb().getFacturaSeleccionada().getFacTotal();
            String pathImagen = institucionServicio.encontrarInstitucionPorId("1").getInsLogo();
            String nomApePer = getConsultaComprobantesBb().getFacturaSeleccionada().getFacPerApellidoPaterno().trim()
                    + " " + getConsultaComprobantesBb().getFacturaSeleccionada().getFacPerApellidoMaterno().trim()
                    + " " + getConsultaComprobantesBb().getFacturaSeleccionada().getFacPerNombre().trim();
            setInstitucion(consultaComprobantesDao.obtenerInstitucion(num_provincias));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numeroFactura", numeroFactura);
            parametros.put("nomApePer", nomApePer);
            parametros.put("pathImagen", pathImagen);
            parametros.put("codigoVerif", getConsultaComprobantesBb().getFacturaSeleccionada().getFacCodigoVerificacion());
            parametros.put("valorNum", total);
            parametros.put("valorAlf", TransformadorNumerosLetrasUtil.transformador(total.toString()).toString());

            reporteUtil.imprimirReporteEnPDF("Factura", "Comprobante" + numeroFactura, parametros);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Comprobante Generado"));
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Al Generar NV");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    public void busquedaPorFactura() {
        getConsultaComprobantesBb().setFiltro(Boolean.TRUE);
    }

    public void busquedaPorFechas() {
        getConsultaComprobantesBb().setFiltro(Boolean.FALSE);
    }

    public void anularFactura() {
        if (getConsultaComprobantesBb().getFacturaSeleccionada().getFacEstado().equals("A")) {
            getConsultaComprobantesBb().getFacturaSeleccionada().setFacEstado("I");
            facturaServicio.edit(getConsultaComprobantesBb().getFacturaSeleccionada());
        }
    }

    public void anularTramite() {
        if (getConsultaComprobantesBb().getFacturaSeleccionada().getFacEstado().equals("A")) {
            if (getConsultaComprobantesBb().getFacturaSeleccionada().getFacTroId() == null) {
                List<Integer> numTramites = facturaDetalleServicio.listarTramitePorFactura(getConsultaComprobantesBb().getFacturaSeleccionada().getFacNumero());
                List<Tramite> listaTramites = new ArrayList<>();
                List<TramiteSuspension> listaTramiteSuspension = new ArrayList<>();
                for (Integer tramite : numTramites) {
                    try {
                        listaTramites.add(tramiteServicio.buscarTramitePorNumero(new Long(tramite)));
                    } catch (ServicioExcepcion ex) {
                    }
                }
                if (!listaTramites.isEmpty()) {
                    for (Tramite tramite : listaTramites) {
                        if (!tramite.getTraEstado().equals("IMP")) {
                            TramiteSuspension tramiteSuspension = new TramiteSuspension();
                            tramiteSuspension.setTmsUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            tramiteSuspension.setTmsFHR(Calendar.getInstance().getTime());
                            tramiteSuspension.setTmsNombre("TRÁMITE SUSPENDIDO POR ANULACIÓN DE FACTURA");
                            tramiteSuspension.setTraNumero(tramite);
                            tramiteSuspension.setTmsTpsId(new Long(0));
                            tramiteSuspension.setTmsEstado("N");
                            tramiteSuspension.setTrmTipo("FAC");
                            listaTramiteSuspension.add(tramiteSuspension);
                            tramite.setTraEstadoRegistro("RZ");
                        }
                    }
                    if (getConsultaComprobantesBb().getFacturaSeleccionada().getFacEstado().equals("A")) {
                        getConsultaComprobantesBb().getFacturaSeleccionada().setFacEstado("I");
                        facturaServicio.edit(getConsultaComprobantesBb().getFacturaSeleccionada());
                        try {
                            tramiteServicio.guardarLote(listaTramites);
                            tramiteSuspensionServicio.guardarLote(listaTramiteSuspension);
                        } catch (ServicioExcepcion ex) {
                        }
                    }
                }
            } else if (getConsultaComprobantesBb().getFacturaSeleccionada().getFacTroId() != null) {
                CertificadoCarga certificadoCarga = certificadoCargaServicio.buscarPorNumFactura(getConsultaComprobantesBb().getFacturaSeleccionada().getFacNumero());
                if (!certificadoCarga.getCdcTipo().equals("IMC")) {
                    if (getConsultaComprobantesBb().getFacturaSeleccionada().getFacEstado().equals("A")) {
                        getConsultaComprobantesBb().getFacturaSeleccionada().setFacEstado("I");
                        certificadoCarga.setCdcEstado("I");
                        facturaServicio.edit(getConsultaComprobantesBb().getFacturaSeleccionada());
                        certificadoCargaServicio.edit(certificadoCarga);
                    }
                }
            }
        }
    }
}
