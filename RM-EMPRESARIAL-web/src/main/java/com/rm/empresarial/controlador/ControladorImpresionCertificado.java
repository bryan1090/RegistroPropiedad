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
import com.rm.empresarial.controlador.util.FooterPdfEvent;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.dao.CertificadoAccionDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoAccion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.MargenServicio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorImpresionCertificado")
@ViewScoped
public class ControladorImpresionCertificado implements Serializable {

    @Getter
    @Setter
    private List<CertificadoCarga> listaCertificadoCarga;

    @Getter
    @Setter
    private List<CertificadoCarga> seleccionCertificadoCarga;

    @Getter
    @Setter
    private List<Certificado> listaCertificado;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String dirCarpetaTemp;

    @Getter
    @Setter
    private Factura facturaSeleccionada;

    @Getter
    @Setter
    private Margen margen;

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

    @Inject
    DataManagerSesion dataManagerSesion;

    @Inject
    PdfTempUtil pdfTempUtil;

    @PostConstruct
    private void init() {
        try {
            setListaCertificadoCarga(servicioCertificadoCarga.listarPorEstadoPorUsrLogPorTipo(dataManagerSesion.getUsuarioLogeado().getUsuId(), "IMC"));
        } catch (ServicioExcepcion ex) {
        }
    }

    public void redirect() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/Certificados/impresionCertificados.xhtml");
    }

    public void buscarCertificadosYcrearPDF() {
        setUrl(null);
        if (!seleccionCertificadoCarga.isEmpty()) {
            setListaCertificado(new ArrayList<Certificado>());
            for (CertificadoCarga certificadoCarga : seleccionCertificadoCarga) {
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
                }
            }
            try {
                crearPDFconTextoHTML();
            } catch (IOException | ServicioExcepcion ex) {
            }
        }
    }

    private void crearPDFconTextoHTML() throws IOException, ServicioExcepcion {
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
                }
                String nombreArchivo = "certificadosImp.pdf";
                pdfTempUtil.setNombreArchivo(nombreArchivo);//Agregar el nombre archivo final a la dependencia
                OutputStream fos = new FileOutputStream(dirCarpetaTemp + nombreArchivo);//Crear archivo final
                PdfTempUtil.doMerge(listPdfs, fos);//Combinar pdfs que se agregaron en la lista
                setUrl(pdfTempUtil.generarLinksAccesoAlPdf());
                facturaSeleccionada.setFacEstadoCertificado("I");
                servicioFactura.edit(facturaSeleccionada);
            }
        } catch (DocumentException ex) {
        }
    }

    public void generarCertificadoAccion(String numCertificado, String observacion, String estado, String nombreUsuario) {
        CertificadoAccion certificadoAccionNuevo = new CertificadoAccion(null,
                numCertificado, estado, "A", observacion, nombreUsuario, Calendar.getInstance().getTime());
        servicioCertificadoAccion.create(certificadoAccionNuevo);
    }

}
