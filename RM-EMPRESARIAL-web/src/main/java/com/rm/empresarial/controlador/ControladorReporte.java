/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.activation.DataSource;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
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
@Named(value = "controladorReporte")
@ViewScoped
public class ControladorReporte implements Serializable {

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

    public void printPDF() throws JRException, IOException, NamingException {

        List<Tramite> datos = new ArrayList<>();
        Map<String, Object> params = new HashMap<>();
        String fileName = "Informe de empresa...";
        String jasperPath = "/paginas/Reportes/Tramite.jasper";

//        tramite=servicioTramite.find(tramite,new Long(numTramite));
//        datasource.add(tramite);
//        InitialContext initialContext = new InitialContext();
//        DataSource dataSource = (DataSource) initialContext.lookup("jdbc/rm-empresarial");
//        Connection connection = dataSource.getConnection();
        params.put("NumeroTramite", 18000229);

        datos.add(servicioTramite.find(new Tramite(), new Long("18000229")));

        this.PDF(params, jasperPath, datos, fileName);
    }

    public void PDF(Map<String, Object> params, String jasperPath, List<?> datos, String fileName) throws JRException, IOException, NamingException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String contentType = "application/pdf";
        String relativeWebPath = ec.getRealPath(jasperPath);
        String pathPdfString=ec.getRealPath("/paginas/Reportes/")+"/prueba.pdf";
        File file = new File(relativeWebPath);
        
        
        JRBeanCollectionDataSource source = new JRBeanCollectionDataSource(datos, false);
//        JasperPrint print = JasperFillManager.fillReport(file.getPath(), params);

        JasperPrint print = JasperFillManager.fillReport(file.getPath(), null, source);
        JasperExportManager.exportReportToPdfFile(print, pathPdfString); //crea un archivo pdf
                

                    

        
//        JasperViewer.viewReport(print, false);

        Long tamaño = file.length();
        String nombreArchivo = "reporte";

        String extension = "pdf";

        //FORMATO RESPUESTA
//        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
//        ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
//        ec.setResponseContentLength(tamaño.intValue()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
//        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "." + extension + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

        
//        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//        response.addHeader("Content-disposition", "attachment:fileName" + fileName);
//        ServletOutputStream stream = response.getOutputStream();
//        FacesContext.getCurrentInstance().responseComplete();
    }
}
