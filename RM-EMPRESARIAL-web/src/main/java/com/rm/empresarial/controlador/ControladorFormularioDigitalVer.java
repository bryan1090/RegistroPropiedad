/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.FormularioDigital;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.RepertorioDigital;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.servicio.FormularioDigitalServicio;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.RepertorioDigitalServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Prueba
 */
@Named(value = "controladorFormularioDigitalVer")
@ViewScoped
public class ControladorFormularioDigitalVer implements Serializable {
    
     
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;
    @EJB
    private FormularioDigitalServicio servicioFormularioDigital;
    @EJB
    private ParametroPathServicio servicioParametroPath;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @EJB
    private SecuenciaServicio secuenciaServicio;
    @Getter
    @Setter
    private List<FormularioDigital> listaFormularioDigital;
    @Getter
    @Setter
    private FormularioDigital formularioDigitalSeleccionado;
    @Getter
    @Setter
    private ParametroPath parametroPath;
    @Getter
    @Setter
    private Secuencia secuencia;
    @Getter
    @Setter
    private Date fechaInicio;
    @Getter
    @Setter
    private Date fechaFin;
    @Getter
    @Setter
    private Boolean filtro = Boolean.FALSE;
    @Getter
    @Setter
    private String numeroFactura;

    public ControladorFormularioDigitalVer() {
        listaFormularioDigital = new ArrayList<>();
        setFechaInicio(Calendar.getInstance().getTime());
        setFechaFin(Calendar.getInstance().getTime());
    }

    @PostConstruct
    public void postConstructor() {
        try {
             inicializar();
        } catch (Exception e) {
        }
       
       

    }

    public void inicializar() throws ParseException {
        try {
            System.out.println("Ini" + fechaInicio);
            System.out.println("Fin" + fechaFin);
            listaFormularioDigital.clear();
        //     DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        //fechaInicio = df.parse(df.format(fechaInicio));
        //fechaFin = df.parse(df.format(fechaFin));
            if(fechaFin.equals(fechaInicio)){
                listaFormularioDigital = servicioFormularioDigital.listarPorPorFecha(fechaFin);                
            }
            else{
                listaFormularioDigital = servicioFormularioDigital.listarPorPorFechaInicFechaFin(fechaInicio, fechaFin);
            }
                
            
            
        } catch (ServicioExcepcion ex) {
            ex.printStackTrace();
            Logger.getLogger(ControladorFormularioDigitalVer.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            nombreArchivo = formularioDigitalSeleccionado.getFmdNombreArchivo();
            subDirectorio=formularioDigitalSeleccionado.getFmdPath();
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

    public void subirArchivo(FileUploadEvent event) throws ParseException {

        //creo nuevo repertorio digital
        try {

            InputStream is = event.getFile().getInputstream();
            System.out.println(" extension:" + event.getFile().getContentType());

//            Path folder;
            String extension = "pdf";
            Path file;
            String nombreArchivo = "";
            String dirPrincipal = "";
            String subDirectorio = "formulario\\";
            Path direccion;
            parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            dirPrincipal = parametroPath.getPrpPath();

            //obtengo la secuencia
            try {
                secuenciaControlador.generarSecuencia("DIG");
                //obtengo el siguiente valor de la secuencia
                setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuencia = getSecuencia().getSecActual();
                getSecuencia().setSecActual(auxSecuencia + 1);
                //asigno la secuencia
                nombreArchivo = formularioDigitalSeleccionado.getFmdNombreArchivo() + "_" + secuenciaControlador.getControladorBb().getNumeroTramite().toString();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al obtener la Secuencia");
                e.printStackTrace();
            }

            //Crear archivo y copiar
            try (InputStream input = is) {
                direccion = Paths.get(dirPrincipal + subDirectorio + nombreArchivo + "." + extension);
                file = Files.createFile(direccion);
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);

                //NUEVO FORMULARIO DIGITAL
                FormularioDigital nuevoFormDig = null;
                nuevoFormDig = new FormularioDigital(0L,
                        nombreArchivo,
                        formularioDigitalSeleccionado.getFmdPath(),
                        formularioDigitalSeleccionado.getFmdExtension(),                        
                        "A",dataManagerSesion.getUsuarioLogeado().getUsuLogin(),Calendar.getInstance().getTime());
                nuevoFormDig.setFacId(formularioDigitalSeleccionado.getFacId());
                servicioFormularioDigital.create(nuevoFormDig);

                //FORMULARIO DIGITAL - Poner como inactivo
                for (FormularioDigital formularioDigital : listaFormularioDigital) {
                    if (formularioDigital.getFmdId()==formularioDigitalSeleccionado.getFmdId()) {
                        formularioDigital.setFmdEstado("I");
                        servicioFormularioDigital.edit(formularioDigital);
                    }
                }

                //actualizo secuencia
//                System.out.println("secuencia: " + secuenciaControlador.getControladorBb().getNumeroTramite());
                secuenciaServicio.guardar(getSecuencia());

                JsfUtil.addSuccessMessage("Exito. ¡Archivo subido! ");

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al subir archivo");
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println(e);
            JsfUtil.addErrorMessage("Error al ingresar repertorio digital");
        }
        inicializar();
    }
    
    public void busquedaPorFactura() {
        setFiltro(Boolean.TRUE);
        listaFormularioDigital.clear();
        
    }

    public void busquedaPorFechas() {
        setFiltro(Boolean.FALSE);
        listaFormularioDigital.clear();
    }
    
    public void buscarPorNumeroFactura() throws ServicioExcepcion{
        listaFormularioDigital.clear();
        listaFormularioDigital = servicioFormularioDigital.listarNumeroFactura(numeroFactura);
        
    }

    
}
