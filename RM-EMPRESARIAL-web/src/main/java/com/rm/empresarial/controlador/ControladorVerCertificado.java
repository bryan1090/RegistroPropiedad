package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FormularioDigital;
import com.rm.empresarial.modelo.FormularioWeb;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.FormularioDigitalServicio;
import com.rm.empresarial.servicio.FormularioWebServicio;
import com.rm.empresarial.servicio.MargenServicio;
import com.rm.empresarial.servicio.ParametroPathServicio;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorVerCertificado")
@ViewScoped
public class ControladorVerCertificado implements Serializable {
    
    @EJB
    private FacturaServicio servicioFactura;
    
    @EJB
    private FacturaDetalleServicio servicioFacturaDetalle;
    
    @EJB
    private CertificadoServicio servicioCertificado;
    
    @EJB
    private MargenServicio servicioMargen;
    
    @EJB
    private FormularioWebServicio servicioFormularioWeb;
    
    @EJB
    private ParametroPathServicio servicioParametroPath;
    
    @EJB
    private FormularioDigitalServicio servicioFormularioDigital;
    
    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;
    
    @Inject
    private PdfTempUtil pdfTempUtil;
    
    @Inject
    @Getter
    @Setter
    private ControladorGenerarRide controladorGenerarRide;
    
    @Getter
    @Setter
    private Factura factura;
    @Getter
    @Setter
    private List<FacturaDetalle> facturaDetalle;
    
    @Getter
    @Setter
    private String url;
    
    @Getter
    @Setter
    private Margen margen;
    
    @Getter
    @Setter
    private Certificado certificado;
    
    @Getter
    @Setter
    private FormularioWeb formularioWeb;
    
    @Getter
    @Setter
    private FormularioDigital formularioDigital;
    
    @Getter
    @Setter
    private String path;
    
    @Getter
    @Setter
    private ParametroPath parametroPath;
    
    @Getter
    @Setter
    private List<FormularioDigital> listaFormularioDigital;
    
    public ControladorVerCertificado() {
        factura = new Factura();
        facturaDetalle = new ArrayList<>();
    }
    
    public void clear() {
        
    }
    
    public void mostrar(String doc) {
        
    }
    
    public void mostrarCAJ(String doc) {
        try {
            Certificado certificado = new Certificado();
            certificado = servicioCertificado.obtenerPorCerNumero(doc);
            setFactura(null);
            setFactura(servicioFactura.buscarPorNumFactura(certificado.getFacId().getFacNumero()));
            setFacturaDetalle(servicioFacturaDetalle.listarPorFacId(getFactura().getFacId()));
        } catch (ServicioExcepcion ex) {
        }
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
    
    public void mostrarRFW(String doc) throws ServicioExcepcion {
        mostrarDFC(doc);
    }
    
    public void mostrarDFC(String doc) throws ServicioExcepcion {
        setListaFormularioDigital(servicioFormularioDigital.listarNumeroFactura(servicioCertificado.obtenerPorCerNumero(doc).getFacId().getFacNumero()));
    }
    
    public void abrirArchivo() {
        String dirPrincipal = "";
        String subDirectorio = "formulario\\";
//        String url = "";
        String nombreArchivo = "";
        String contentType = "application/pdf";
        String extension = "pdf";
        String path = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();

        try {
            //OBTENER DIRECCION 
            parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            dirPrincipal = parametroPath.getPrpPath();
            nombreArchivo = formularioDigital.getFmdNombreArchivo();
            subDirectorio = formularioDigital.getFmdPath();
//            url = "file:///" + dirPrincipal + subDirectorio + nombreArchivo + "." + extension;
            path = dirPrincipal + subDirectorio + nombreArchivo + "." + extension;
            
            Path file = Paths.get(path);
            InputStream input = null;
//            BufferedOutputStream bos = null;
            OutputStream output = ec.getResponseOutputStream();
            File file2 = new File(path);
            Long tamaño = file2.length();
            input = new BufferedInputStream(Files.newInputStream(file));

//FORMATO RESPUESTA
            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
            ec.setResponseContentLength(tamaño.intValue()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
            ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + nombreArchivo + "." + extension + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

//LEER EXISTENTE Y MODIFICAR
//            Document document = new Document();
//            PdfReader reader = new PdfReader(path);
//            document.open();
//            System.out.println("# paginas: " + document.getPageNumber());
//            document.close();
            //ESCRIBIR NUEVO
//                        Document document = new Document();
//            FileOutputStream fos = new FileOutputStream(path);
//            PdfWriter writer = PdfWriter.getInstance(document, fos);
//            document.open();
//            document.add(new Paragraph("Hola mundo"));
//            document.close();
//            writer.close();
//ESCRIBIR A LA SALIDA OUTPUT
            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = input.read(bytesBuffer)) > 0) {
                output.write(bytesBuffer, 0, bytesRead);
            }

//            output.write(input);
            fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al cargar el archivo");
        }
    }
    
    public void mostrarCER(String doc) {
        
    }
    
    public void mostrarIMC(String cerNumero) {
        try {
            setCertificado(servicioCertificado.obtenerPorCerNumero(cerNumero));
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al generar el Pdf");
        }
    }
    
    public void mostrarFIC(String doc) {
        setCertificado(servicioCertificado.obtenerPorCerNumero(doc));
    }
}
