/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.util.FooterPdfEvent;

import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.dao.CertificadoCargaDao;
import com.rm.empresarial.dao.CertificadoEntregaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.CertificadoEntrega;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.MargenServicio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.sql.rowset.serial.SerialException;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorReimpresionCertificado")
@ViewScoped
public class controladorReimpresionCertificado implements Serializable {

    @Getter
    @Setter
    private List<Certificado> listaCertificados;

    @Getter
    @Setter
    private List<CertificadoCarga> listaCertificadosEntrega;

    @Getter
    @Setter
    private List<Certificado> seleccionCertificado;
    
    
    @Getter
    @Setter
    private Certificado nCertificado;
    
    @Getter
    @Setter
    private String descripcionEntrega;
    

     @Getter
    @Setter
    private List<CertificadoCarga> seleccionCertificadoCarga;

    @Getter
    @Setter
    private String tBusqueda;
    
    @Getter
    @Setter
    private CertificadoEntrega certificadoEntrega;

    @Getter
    @Setter
    private Date cerFHRI;

    @Getter
    @Setter
    private Date cerFHRF;

    @Getter
    @Setter
    private String cerNumero;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String dirCarpetaTemp;

    @Getter
    @Setter
    private Margen margen;

    @EJB
    private CertificadoServicio servicioCertificado;

    @EJB
    private MargenServicio servicioMargen;
    
    @EJB
    private CertificadoEntregaDao certificadoEntregaDao;

    @EJB
    private CertificadoCargaDao certificadoCargaDao;

    @Inject
    private PdfTempUtil pdfTempUtil;
    
    @Inject
    private DataManagerSesion dataManagerSesion;

    @PostConstruct
    public void init() {
        setTBusqueda("fecha");
    }

    public void buscar() {
        setListaCertificados(null);
        switch (tBusqueda) {
            case "fecha":
                listarPorFecha();
                break;
            case "nCertificado":
                listarPorNcertificado();
                break;
            default:
                break;
        }
    }

    public void filtrar() throws ServicioExcepcion {
        setListaCertificadosEntrega(null);
        switch (tBusqueda) {
            case "fecha":
                listarPorFechaETC();

                break;
            case "nCertificado":
                listarPorCertificadoETC();
                break;
            default:
                break;
        }
    }

    private void listarPorFecha() {
        if (cerFHRI != null && cerFHRF != null) {
            if (servicioCertificado.listarPorCerFHR(cerFHRI, cerFHRF) != null) {
                SimpleDateFormat pattern = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    setListaCertificados(servicioCertificado.listarPorCerFHR(pattern.parse(pattern.format(cerFHRI.getTime())), pattern.parse(pattern.format(cerFHRF))));
                } catch (ParseException ex) {
                }
            } else {
                JsfUtil.addWarningMessage("No se encontraron resultados");
            }
        }
    }
    
    private void listarPorFechaETC() {
        if (cerFHRI != null && cerFHRF != null) {
            if (servicioCertificado.listarPorCerFHR(cerFHRI, cerFHRF) != null) {
                try {
                    setListaCertificadosEntrega(certificadoCargaDao.listarFechaETC(cerFHRI, cerFHRF, dataManagerSesion.getUsuarioLogeado().getUsuId()));
                } catch (Exception ex) {
                }
            } else {
                JsfUtil.addWarningMessage("No se encontraron resultados");
            }
        }
    }

    private void listarPorNcertificado() {
        if (cerNumero != null && !cerNumero.equals("")) {
            setListaCertificados(servicioCertificado.listarPorCerNumeroLikeTop50(cerNumero));
        }
    }
    
    private void listarPorCertificadoETC() throws ServicioExcepcion{
      if (cerNumero != null && !cerNumero.equals("")) {
           setListaCertificadosEntrega(certificadoCargaDao.listarETC(cerNumero, dataManagerSesion.getUsuarioLogeado().getUsuId()));
        }  
    }

    public void crearPDFconTextoHTML() {
        try {

            List<InputStream> listPdfs = new ArrayList<>();
            setMargen(servicioMargen.obtenerPorMarTipo("CER"));
//            String nombreArchivo = "certificadosReImp";
//            FileOutputStream fos = new FileOutputStream(pdfTempUtil.directorio(nombreArchivo));
//            Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());
//            PdfWriter writer = PdfWriter.getInstance(document, fos);
//            writer.setPageEvent(new FooterPdfEvent());
//            document.setMarginMirroring(true);
//            document.open();
            if (!seleccionCertificado.isEmpty() && seleccionCertificado.size() >= 1) {
                int i = 1;
                setDirCarpetaTemp(pdfTempUtil.directorio());//Creo el directorio temporal sin los archivos
                for (Certificado certificado : seleccionCertificado) {
                    String nombreArchivo = "certificadoReImp" + i + ".pdf";//Nombre por cada archivo
                    String dirFinalArchivo = dirCarpetaTemp + nombreArchivo;//Directorio final por cada archivo
                    FileOutputStream fos = new FileOutputStream(dirFinalArchivo);
                    Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());
                    PdfWriter writer = PdfWriter.getInstance(document, fos);
                    writer.setPageEvent(new FooterPdfEvent());
                    document.setMarginMirroring(true);
                    document.open();

                    Paragraph parrafo = new Paragraph();
                    parrafo = pdfTempUtil.convertirHtmlConFormatoAParrafoPdfOld(parrafo, certificado.getCerCertificado());
                    parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                    document.add(parrafo);

                    document.close();//cierro el documento
                    writer.close();//cierro la escritura

                    i++;
                    listPdfs.add(new FileInputStream(new File(dirFinalArchivo)));//Agregar el directorio de cada archivo a la lista
                }
                String nombreArchivo = "certificadosReImp.pdf";
                pdfTempUtil.setNombreArchivo(nombreArchivo);//Agregar el nombre archivo final a la dependencia
                OutputStream fos = new FileOutputStream(dirCarpetaTemp + nombreArchivo);//Crear archivo final
                PdfTempUtil.doMerge(listPdfs, fos);//Combinar pdfs que se agregaron en la lista
                setUrl(pdfTempUtil.generarLinksAccesoAlPdf());//Generar la url que se mostrará en la lista
            }
        } catch (DocumentException | IOException | ServicioExcepcion ex) {
            ex.getMessage();
        }
    }
    
    public void crearpdfETC() {
        try {
            for(CertificadoCarga certificadoCarga : seleccionCertificadoCarga){
                certificadoEntrega = new CertificadoEntrega();
                certificadoEntrega.setCertificado(servicioCertificado.obtenerPorCerNumero(certificadoCarga.getCdcCertificado()));
                certificadoEntrega.setCetDecripcion(descripcionEntrega);
                certificadoEntrega.setCetFHR(Calendar.getInstance().getTime());
                certificadoEntrega.setUsuId(dataManagerSesion.getUsuarioLogeado());
                certificadoEntregaDao.create(certificadoEntrega);
                
                certificadoCarga.setCdcEstado("I");
                certificadoCarga.setCdcEstadoProceso("TERMINADO");
                certificadoCargaDao.edit(certificadoCarga); 
            } 
            filtrar();
            addSuccessMessage("Se entrego correctamente");
            
        } catch (Exception e) {
           addErrorMessage("No se pudo completar la entrega");
        }
           
               
        
    }

}
