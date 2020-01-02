/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;


import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.RepertorioDigital;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.RepertorioDigitalServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorRepertorioDigitalVer")
@ViewScoped
public class ControladorRepertorioDigitalVer implements Serializable {

    //servicios
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;
    @EJB
    private RepertorioDigitalServicio servicioRepertorioDigital;
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
    private List<RepertorioDigital> listaRepertorioDigital;
    @Getter
    @Setter
    private RepertorioDigital repertorioDigitalSeleccionado;
    @Getter
    @Setter
    private ParametroPath parametroPath;
    @Getter
    @Setter
    private Secuencia secuencia;

    public ControladorRepertorioDigitalVer() {
        listaRepertorioDigital = new ArrayList<>();
    }

    @PostConstruct
    public void postConstructor() {
        inicializar();

    }

    public void inicializar() {
        try {
            listaRepertorioDigital = servicioRepertorioDigital.listarTodo();
        } catch (ServicioExcepcion ex) {
            ex.printStackTrace();
            Logger.getLogger(ControladorRepertorioDigitalVer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abrirArchivo() {
        String dirPrincipal = "";
        String subDirectorio = "repertorio\\";
//        String url = "";
        String nombreArchivo = "";
        String contentType = "application/pdf";
        String extension = "pdf";
        String path = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();

        try {
            //OBTENGO DIRECCION 
            parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            dirPrincipal = parametroPath.getPrpPath();
            nombreArchivo = repertorioDigitalSeleccionado.getRtdNombreArchivo();
            subDirectorio=repertorioDigitalSeleccionado.getRtdPath();
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
            ec.setResponseHeader("Content-Disposition", "inline; filename=" + nombreArchivo + "." + extension); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

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

            //REPERTORIO DIGITAL
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al cargar el archivo");
        }
    }

    public void subirArchivo(FileUploadEvent event) {

        //creo nuevo repertorio digital
        try {

            InputStream is = event.getFile().getInputstream();
            System.out.println(" extension:" + event.getFile().getContentType());

//            Path folder;
            String extension = "pdf";
            Path file;
            String nombreArchivo = "";
            String dirPrincipal = "";
            String subDirectorio = "tramite\\";
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
                nombreArchivo = repertorioDigitalSeleccionado.getRepNumero().getTraNumero().getTraNumero() + "_" + secuenciaControlador.getControladorBb().getNumeroTramite().toString();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al obtener la Secuencia");
                e.printStackTrace();
            }

            //Crear archivo y copiar
            try (InputStream input = is) {
                direccion = Paths.get(dirPrincipal + subDirectorio + nombreArchivo + "." + extension);
                file = Files.createFile(direccion);
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);

                //NUEVO REPERTORIO DIGITAL
                RepertorioDigital nuevoRepDig = null;
                nuevoRepDig = new RepertorioDigital(0L,
                        nombreArchivo,
                        repertorioDigitalSeleccionado.getRtdPath(),
                        repertorioDigitalSeleccionado.getRtdExtension(),
                        dataManagerSesion.getUsuarioLogeado().getUsuLogin(),
                        Calendar.getInstance().getTime(), "A");
                nuevoRepDig.setRepNumero(repertorioDigitalSeleccionado.getRepNumero());
                servicioRepertorioDigital.create(nuevoRepDig);

                //REPERTORIO DIGITAL - Poner como inactivo
                for (RepertorioDigital repertorioDigital : listaRepertorioDigital) {
                    if (repertorioDigital.getRepNumero().equals(repertorioDigitalSeleccionado.getRepNumero())) {
                        repertorioDigital.setRtdEstado("I");
                        servicioRepertorioDigital.edit(repertorioDigital);
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
            JsfUtil.addErrorMessage("Error al ingresar repertorio digital");
        }
        inicializar();
    }

}
