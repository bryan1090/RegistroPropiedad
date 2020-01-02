/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.FooterPdfEvent;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.dao.CertificadoAccionDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoAccion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.MargenServicio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorImpresionCertificadosUsuario")
@ViewScoped
public class ControladorImpresionCertificadosUsuario implements Serializable {

    @Getter
    @Setter
    private List<CertificadoCarga> listaCertificadoCarga;

    @Getter
    @Setter
    private List<Certificado> listaCertificado;

    @Getter
    @Setter
    private String url;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Getter
    @Setter
    private String dirCarpetaTemp;

    @Getter
    @Setter
    private Factura facturaSeleccionada;

    @Getter
    @Setter
    private Margen margen;

    @Getter
    @Setter
    private ConfigDetalle configDetalle;

    @Getter
    @Setter
    private Date cerFHR;

    @Getter
    @Setter
    private boolean btnDisablePorFecha;

//    @Getter
//    @Setter
//    private String msgfechaHabilitada;
    @EJB
    private CertificadoCargaServicio servicioCertificadoCarga;

    @EJB
    private CertificadoServicio servicioCertificado;

    @EJB
    private CertificadoAccionDao servicioCertificadoAccion;

    @EJB
    private FacturaServicio servicioFactura;

    @EJB
    private MargenServicio servicioMargen;

    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;

    @Inject
    DataManagerSesion dataManagerSesion;

    @Inject
    PdfTempUtil pdfTempUtil;

    @PostConstruct
    private void init() {
        try {
            setListaCertificadoCarga(servicioCertificadoCarga.listarPorTipoPorEstadoPorUsuIdPorFecha(dataManagerSesion.getUsuarioLogeado().getUsuId(), "IMC", Calendar.getInstance().getTime()));
            setCerFHR(Calendar.getInstance().getTime());
            setBtnDisablePorFecha(false);
        } catch (ServicioExcepcion | ParseException ex) {
        }
    }

//    public void comprobarFecha(Date fechaReg) throws ParseException, ServicioExcepcion {
//        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Calendar fechaSumada = Calendar.getInstance();
//        fechaSumada.setTime((formatoFecha.parse(formatoFecha.format(fechaReg)))); //tuFechaBase es un Date;
//        setConfigDetalle(servicioConfigDetalle.buscarPorConfig());
//        switch (configDetalle.getConfigDetalleTexto()) {
//            case "H":
//                fechaSumada.add(Calendar.HOUR, configDetalle.getConfigDetalleNumero().intValueExact()); //horasASumar es int.
//                break;
//            case "D":
//                fechaSumada.add(Calendar.DAY_OF_YEAR, configDetalle.getConfigDetalleNumero().intValueExact()); //diasASumar es int.
//                break;
//        }
//        Calendar fechaActual = Calendar.getInstance();
//        fechaActual.setTime((formatoFecha.parse(formatoFecha.format(Calendar.getInstance().getTime())))); //tuFechaBase es un Date;
////        System.out.println("Fecha Actual: " + fechaActual.getTime());
////        System.out.println("Fecha Sumada: " + fechaSumada.getTime());
//        if (fechaSumada.getTime().before(fechaActual.getTime()) || fechaSumada.getTime().equals(fechaActual.getTime())) {
////            System.out.println("Se puede imprimir");
//            setBtnDisablePorFecha(false);
//        } else if (fechaSumada.getTime().after(fechaActual.getTime())) {
////            System.out.println("No se Puede imprimir");
////            setMsgfechaHabilitada("Los certificados de esta fecha se habilitan el " + fechaSumada.getTime());
//            setBtnDisablePorFecha(true);
//            String pattern = "dd/MM/yyyy HH:mm";
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
//            JsfUtil.addWarningMessage("Fecha para imprimir certificados: " + simpleDateFormat.format(fechaSumada.getTime()));
//        }
//    }
    public void buscarPorFecha() throws ServicioExcepcion, ParseException {
        if (cerFHR != null) {
            SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy");
            if (pattern.parse(pattern.format(cerFHR.getTime())).after(pattern.parse(pattern.format(Calendar.getInstance().getTime())))) {
                setBtnDisablePorFecha(true);
            } else {
                setBtnDisablePorFecha(false);
            }
            setListaCertificadoCarga(servicioCertificadoCarga.listarPorTipoPorEstadoPorUsuIdPorFecha(dataManagerSesion.getUsuarioLogeado().getUsuId(), "IMC", cerFHR));
        }
    }

    public void buscarCertificadosYcrearPDF() {
         Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("ETC", "ENTREGACERTIFICADO", 1);
        setUrl(null);
        if (!listaCertificadoCarga.isEmpty()) {
            setListaCertificado(new ArrayList<Certificado>());
            for (CertificadoCarga certificadoCarga : listaCertificadoCarga) {
                if (certificadoCarga.getCdcDocumento() != null) {
                    Certificado cert = servicioCertificado.obtenerPorCerNumero(certificadoCarga.getCdcCertificado());
                    if (cert != null) {
                        listaCertificado.add(cert);
                    }
                    if (facturaSeleccionada == null) {
                        setFacturaSeleccionada(certificadoCarga.getFacId());
                    }
                    certificadoCarga.setCdcEstado("I");
                    servicioCertificadoCarga.edit(certificadoCarga);
                        CertificadoCarga nuevoCertCar = new CertificadoCarga();
                            //nuevoCertCar = certCarga;
                            nuevoCertCar.setCdcDocumento(certificadoCarga.getCdcDocumento());
                            nuevoCertCar.setCdcTipo("ETC");
                            nuevoCertCar.setUsuId(usuarioAsignado);
                            nuevoCertCar.setCdcEstado("A");
                            nuevoCertCar.setFacId(certificadoCarga.getFacId());
                            nuevoCertCar.setCdcCertificado(certificadoCarga.getCdcCertificado());
                            nuevoCertCar.setCdcEstadoProceso("ACTIVO");
                            nuevoCertCar.setCdcCantidadCertificado(certificadoCarga.getCdcCantidadCertificado());
                            nuevoCertCar.setCdcCantidadDerecho(certificadoCarga.getCdcCantidadDerecho());
                            nuevoCertCar.setCdcUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            nuevoCertCar.setCdcFHR(Calendar.getInstance().getTime());
                            servicioCertificadoCarga.create(nuevoCertCar);
                }
            }
            try {
                crearPDFconTextoHTML(usuarioAsignado);
            } catch (IOException | ServicioExcepcion ex) {
            }
        }
    }

    private void crearPDFconTextoHTML(Usuario usuarioAsignado) throws IOException, ServicioExcepcion {
        try {
            List<InputStream> listPdfs = new ArrayList<>();
            setMargen(servicioMargen.obtenerPorMarTipo("CER"));
            if (!listaCertificado.isEmpty() && listaCertificado.size() >= 1) {
                int i = 1;
                setDirCarpetaTemp(pdfTempUtil.directorio());//Creo el directorio temporal sin los archivos
                for (Certificado certificado : listaCertificado) {
                    String nombreArchivo = "certificadoImp" + i + ".pdf";
                    String dirFinalArchivo = dirCarpetaTemp + nombreArchivo;//Directorio final por cada archivo
                    FileOutputStream fos = new FileOutputStream(dirFinalArchivo);
                    Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());
                    PdfWriter writer = PdfWriter.getInstance(document, fos);
                    writer.setPageEvent(new FooterPdfEvent());
                    document.setMarginMirroring(true);
                    document.open();

                    Paragraph parrafo = new Paragraph();
                    parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                    parrafo = pdfTempUtil.convertirHtmlConFormatoAParrafoPdfOld(parrafo, certificado.getCerCertificado());
                    document.add(parrafo);

                    document.close();//cierro el documento
                    writer.close();//cierro la escritura

                    i++;
                    listPdfs.add(new FileInputStream(new File(dirFinalArchivo)));//Agregar el directorio de cada archivo a la lista

                    generarCertificadoAccion(certificado.getCertificadoPK().getCerNumero(), "IMPRESIÓN DE CERTIFICADO", "IMC", dataManagerSesion.getUsuarioLogeado().getUsuLogin());

                    CertificadoAccion certAccion = new CertificadoAccion();
                            certAccion.setCtaNumeroDocumento(certificado.getCertificadoPK().getCerNumero());
                            certAccion.setCtaEstadoProceso("ETC");
                            certAccion.setCtaEstadoRegistro("A");
                            certAccion.setCtaObservacion("ENTREGA CERTIFICADO");
                            certAccion.setCtaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            certAccion.setCtaFHR(Calendar.getInstance().getTime());
                            certAccion.setCtaUsuarioAsignado(usuarioAsignado.getUsuLogin());
                            servicioCertificadoAccion.create(certAccion);
                            
                        
                }
                String nombreArchivo = "certificadosImp.pdf";
                pdfTempUtil.setNombreArchivo(nombreArchivo);//Agregar el nombre archivo final a la dependencia
                OutputStream fos = new FileOutputStream(dirCarpetaTemp + nombreArchivo);//Crear archivo final
                PdfTempUtil.doMerge(listPdfs, fos);//Combinar pdfs que se agregaron en la lista
                setUrl(pdfTempUtil.generarLinksAccesoAlPdf());
                facturaSeleccionada.setFacEstadoCertificado("I");
                servicioFactura.edit(facturaSeleccionada);
                buscarPorFecha();
            }
        } catch (DocumentException | ParseException ex) {
        }
    }

    public void generarCertificadoAccion(String numCertificado, String observacion, String estado, String nombreUsuario) {
        CertificadoAccion certificadoAccionNuevo = new CertificadoAccion(null,
                numCertificado, estado, "A", observacion, nombreUsuario, Calendar.getInstance().getTime());
        servicioCertificadoAccion.create(certificadoAccionNuevo);
    }

}
