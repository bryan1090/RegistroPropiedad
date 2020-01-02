/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
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
@Named(value = "controladorVerCAJ")
@ViewScoped
public class ControladorVerCAJ implements Serializable{

    @Getter
    @Setter
    private Factura factura;
    
    @Getter
    @Setter
    private List<FacturaDetalle> facturaDetalle;
    
    @EJB
    private FacturaServicio servicioFactura;
    
    @EJB
    private FacturaDetalleServicio servicioFacturaDetalle;
    
    @Inject
    @Getter
    @Setter
    private ControladorGenerarRide controladorGenerarRide;
    
    public ControladorVerCAJ() {
    }
    
    public void clear(){
        setFactura(null);
        setFacturaDetalle(null);
    }
    
    public void mostrar(String doc){
        try {
            setFactura(null);
            setFactura(servicioFactura.buscarPorNumFactura(doc));
            setFacturaDetalle(servicioFacturaDetalle.listarPorFacId(getFactura().getFacId()));
        } catch (ServicioExcepcion ex) {}
    }
    
    public void generarRide() throws InterruptedException, IOException {

        String contentType = "application/pdf";
        String numeroFac = getFactura().getFacNumero();
        String claveAcce = getFactura().getFacClaveAcceso();
        String fechaAutrizacion = "2018-12-06 18:48:54";
        String xml = getFactura().getFacXml();
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();
        if (getFactura().getFacXml() == null) {
            JsfUtil.addErrorMessage("Error al generar");
        } else {
            String path = controladorGenerarRide.generarRide(numeroFac, claveAcce, fechaAutrizacion, xml);
            Path file = Paths.get(path);
            InputStream input = null;
            OutputStream output = ec.getResponseOutputStream();
            File file2 = new File(path);
            Long tamaño = file2.length();
            input = new BufferedInputStream(Files.newInputStream(file));

//FORMATO RESPUESTA
            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
            ec.setResponseContentLength(tamaño.intValue()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
            ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + "xxx" + "." + "pdf" + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

//ESCRIBIR A LA SALIDA OUTPUT
            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = input.read(bytesBuffer)) > 0) {
                output.write(bytesBuffer, 0, bytesRead);
            }

//            output.write(input);
            fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.

            //REPERTORIO DIGITAL
        }
    }   
        
    
}
