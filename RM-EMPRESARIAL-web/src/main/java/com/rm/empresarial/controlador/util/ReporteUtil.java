/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.controlador.DataManagerSesion;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Steven
 */
@Dependent
public class ReporteUtil implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @EJB
    TramiteServicio servicioTramite;

    @Getter
    @Setter
    String numTramite;

    @Getter
    @Setter
    Tramite tramite;

    @PostConstruct
    public void init() {

    }

    public void imprimirReporteEnPDF(String nombreArchivoJasper, String nombreArchivoPDF,Map<String, Object> parametros) throws JRException, IOException, NamingException, SQLException {

        List<Tramite> datos = new ArrayList<>();
        
//        String fileName = "Informe de empresa...";
        String jasperPath = "/reportes/"+nombreArchivoJasper+".jasper";

//        tramite=servicioTramite.find(tramite,new Long(numTramite));
//        datasource.add(tramite);
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("jdbc/rm-empresarial");
        Connection connection = dataSource.getConnection();     

//        datos.add(servicioTramite.find(new Tramite(), new Long("18000240")));
        this.generarReporteEnPDF(parametros, jasperPath, datos, nombreArchivoPDF, connection);
    }
    
    
    public ByteArrayOutputStream crearReporteEnPDF(String nombreArchivoJasper, String nombreArchivoPDF,Map<String, Object> parametros) throws JRException, IOException, NamingException, SQLException {

        List<Tramite> datos = new ArrayList<>();
        
//        String fileName = "Informe de empresa...";
        String jasperPath = "/reportes/"+nombreArchivoJasper+".jasper";

//        tramite=servicioTramite.find(tramite,new Long(numTramite));
//        datasource.add(tramite);
        InitialContext initialContext = new InitialContext();
        DataSource dataSource = (DataSource) initialContext.lookup("jdbc/rm-empresarial");
        Connection connection = dataSource.getConnection();     

//        datos.add(servicioTramite.find(new Tramite(), new Long("18000240")));
        return generarReportePDFenStream(parametros, jasperPath, datos, nombreArchivoPDF, connection);
    }

    public ByteArrayOutputStream generarReportePDFenStream(Map<String, Object> params, String jasperPath, List<?> datos, String nombreArchivoPDF, Connection connection) throws JRException, IOException, NamingException, SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getCurrentInstance().getExternalContext();
//        String nombreArchivo = "reporte";
        String extension = "pdf";
        String contentType = "application/pdf";
        String relativeWebPath = ec.getRealPath(jasperPath);
//        String pathPdfString = ec.getRealPath("/paginas/Reportes/") + "/prueba.pdf";
        File file = new File(relativeWebPath);

//        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datos, false);
//        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params);
//        JasperPrint print = JasperFillManager.fillReport(file.getPath(), null, source);
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, connection);
//        JasperExportManager.exportReportToPdfFile(print, pathPdfString); //crea un archivo pdf en el directorio del deploy, NOTA:este archivo se borra en cada corrida del programa
//        JasperViewer.viewReport(print, false);


//        Integer tamaño = JasperExportManager.exportReportToPdf(print).length;

        //FORMATO RESPUESTA
//        OutputStream output = ec.getResponseOutputStream();
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
//        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
//        ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
//        ec.setResponseContentLength(JasperExportManager.exportReportToPdf(print).length); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
//        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivoPDF + "." + extension + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.
        JasperExportManager.exportReportToPdfStream(print, baos);

//        fc.responseComplete();
        return baos;
    }
    
    public OutputStream generarReporteEnPDF(Map<String, Object> params, String jasperPath, List<?> datos, String nombreArchivoPDF, Connection connection) throws JRException, IOException, NamingException, SQLException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getCurrentInstance().getExternalContext();
//        String nombreArchivo = "reporte";
        String extension = "pdf";
        String contentType = "application/pdf";
        String relativeWebPath = ec.getRealPath(jasperPath);
//        String pathPdfString = ec.getRealPath("/paginas/Reportes/") + "/prueba.pdf";
        File file = new File(relativeWebPath);

//        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datos, false);
//        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params);
//        JasperPrint print = JasperFillManager.fillReport(file.getPath(), null, source);
        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params, connection);
//        JasperExportManager.exportReportToPdfFile(print, pathPdfString); //crea un archivo pdf en el directorio del deploy, NOTA:este archivo se borra en cada corrida del programa
//        JasperViewer.viewReport(print, false);


//        Integer tamaño = JasperExportManager.exportReportToPdf(print).length;

        //FORMATO RESPUESTA
        OutputStream output = ec.getResponseOutputStream();
        ByteArrayOutputStream baos=null;
        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        ec.setResponseContentLength(JasperExportManager.exportReportToPdf(print).length); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivoPDF + "." + extension + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.
        JasperExportManager.exportReportToPdfStream(print, output);

        fc.responseComplete();
        return output;
    }
}
