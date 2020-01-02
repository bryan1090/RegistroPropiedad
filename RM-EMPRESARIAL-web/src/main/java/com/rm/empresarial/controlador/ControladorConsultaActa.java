package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.UtilitarioPdf;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoMarginacion;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TipoLibroServicio;
import com.rm.empresarial.servicio.TipoMarginacionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorConsultaActa")
@ViewScoped
public class ControladorConsultaActa implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Inject
    private TramiteUtil tramiteUtil;

    @Inject
    private UtilitarioPdf utilitarioPdf;

    @Getter
    @Setter
    @EJB
    private TipoLibroServicio servicioTipoLibro;

    @Getter
    @Setter
    @EJB
    private TipoMarginacionServicio servicioTipoMarginacion;

    @Getter
    @Setter
    @EJB
    private ActaServicio servicioActa;

    @EJB
    private RepertorioServicio servicioRepertorio;

    @EJB
    private TramiteServicio servicioTramite;

    @Getter
    @Setter
    private TipoLibro tipoLibroSeleccionado;

    @Getter
    @Setter
    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @Getter
    @Setter
    @EJB
    private MarginacionServicio servicioMarginacion;

    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Getter
    @Setter
    List<RepertorioUsuario> listaRepUsu;

//    @Getter
//    @Setter
//    List<Marginacion> listaMarginacion;
    @Getter
    @Setter
    List<Marginacion> listaMarginacion;

    @Getter
    @Setter
    List<Marginacion> listaMarginacionNuevos;

    @Getter
    @Setter
    private Acta actaSeleccionada;

    @Getter
    @Setter
    private TipoMarginacion tipoMarginacionSeleccionado;

    @Getter
    @Setter
    private Marginacion nuevaMarginacion;

    @Getter
    @Setter
    private Long repertorioNumero;

    @Getter
    @Setter
    private Long repertorioNumero2;

    @Getter
    @Setter
    private Long IDusuarioLogeado;

//    @Getter
//    @Setter
//    private Boolean habilitarCampos;
    @Getter
    @Setter
    private String textoActa;

    @Getter
    @Setter
    private String dirArchivoComoRecurso;

    @Getter
    @Setter
    private Long IDtipoLibroSeleccionado;

    @Getter
    @Setter
    private Long IDtipoMarginacionSeleccionado;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private Date fechaRep;

    @Getter
    @Setter
    private Repertorio repertorioParam;

    @Getter
    @Setter
    private Tramite tramiteParam;

    //-----------------------------------------------------------------------------
    @Getter
    @Setter
    private String fecha;

    @Getter
    @Setter
    private String repertorio;

    @Getter
    @Setter
    private String inscripcion;

    @Getter
    @Setter
    private String fojas;

    @Getter
    @Setter
    private String tomo;

    @Getter
    @Setter
    private String volumen;

    @Getter
    @Setter
    private String bis;

    @Getter
    @Setter
    private boolean plnmarginacion;

    @Getter
    @Setter
    private String areamarginacion;

    @Getter
    @Setter
    private String urlPDF;

    @Getter
    @Setter
    private String extension;

    @Getter
    @Setter
    private String urlRTF;

    @Getter
    @Setter
    private Boolean rendBtnDescargarRTF = false;
    @Getter
    @Setter
    private StreamedContent file;
    @Getter
    @Setter
    private String tipo;

    public ControladorConsultaActa() {
        plnmarginacion = false;
        tipo="I";
    }

    @PostConstruct
    public void postConstructor() {
        IDusuarioLogeado = dataManagerSesion.getUsuarioLogeado().getUsuId();
        listaMarginacionNuevos = new ArrayList<>();
    }

    public List<TipoLibro> getlistaTipoLibro() throws ServicioExcepcion {

        return servicioTipoLibro.listarTodo();
    }

    public List<TipoMarginacion> getlistaTipoMarginacion() throws ServicioExcepcion {

        return servicioTipoMarginacion.listarTodo();
    }

//    public List<Marginacion> getlistaMarginacion() throws ServicioExcepcion {
//        return servicioMarginacion.listarTodo(); //lleno lista antiguos
//    }
    public void llenarDatosActa() {

//        fecha = "31/05/2011";
//        repertorio = "40616";
//        inscripcion = "15057";
//        fojas = "20";
//        tomo = "142";
//        volumen = "130";
//        bis = "0";
//        url = "http://localhost:8084/RM-EMPRESARIAL-web/temp/acta1.pdf";
    }

    public void buscarActa() throws ServicioExcepcion, IOException {

        if (repertorioNumero2 != null && fechaRep != null && IDtipoLibroSeleccionado != null) {
            try {
                actaSeleccionada = servicioActa.buscarActaPorNumRepFechaRepTipoLibro(repertorioNumero2, fechaRep, IDtipoLibroSeleccionado);
                listaMarginacion = servicioMarginacion.listarPor_NumActa(actaSeleccionada.getActNumero());
//                crearPDFconTextoHTML(actaSeleccionada.getActActa());
//                JsfUtil.addSuccessMessage("Acta cargada.");
//caso especial , acta migrada
                if (actaSeleccionada.getActMigrado() != null && actaSeleccionada.getActMigrado().equals("SI")) {

                    String nombreArchivoConExt = "";
                    String nombreArchivoSinExt = "";
                    String dirPrincipal = "";
                    String subDirectorio = "";
                    switch (tipo) {
                        case "I":
                            if(actaSeleccionada.getActNombreArchivo()!=null && actaSeleccionada.getActNombreEscritura()!=null)
                            {
                             nombreArchivoConExt = actaSeleccionada.getActNombreArchivo();
                            subDirectorio = actaSeleccionada.getActPath();   
                            }else{
                                JsfUtil.addErrorMessage("Error, nombre o ubicación del archivo faltantes o con error");
                                return;
                            }
                            
                            break;
                        case "E":
                            if(actaSeleccionada.getActNombreEscritura()!=null && actaSeleccionada.getActPathPdf()!=null)
                            {
                            nombreArchivoConExt = actaSeleccionada.getActNombreEscritura();
                            subDirectorio = actaSeleccionada.getActPathPdf();
                            }else{
                                JsfUtil.addErrorMessage("Error, nombre o ubicación del archivo faltantes o con error");
                                return;
                            }
                            break;
                    }
                    extension = nombreArchivoConExt.substring(nombreArchivoConExt.indexOf(".") + 1);
                    nombreArchivoSinExt = nombreArchivoConExt.substring(0, nombreArchivoConExt.indexOf("."));
                    switch (extension) {
                        case "pdf":
                            utilitarioPdf.abrirPDFenServidorComoRecurso(subDirectorio, nombreArchivoSinExt);
                            urlPDF = utilitarioPdf.getDirArchivoComoRecurso();
                            break;
                        case "rtf":
                            ConfigDetalle cfgDet = servicioConfigDetalle.buscarPorConfig("MIGRACION");
                            dirPrincipal = cfgDet.getConfigDetalleTexto();
                            utilitarioPdf.descargarRTFDesdeDirectorio(subDirectorio, nombreArchivoSinExt);
                            //urlRTFDownload = utilitarioPdf.getDirArchivoComoRecurso();    
                            //cargarArchivoRTF(dirPrincipal + actaSeleccionada.getActPath() + nombreArchivoConExt);
                            InputStream inputActaTRF = utilitarioPdf.getFileRTF();
                            file = new DefaultStreamedContent(inputActaTRF, "application/rtf", "documentRTF.rtf");
                            rendBtnDescargarRTF = true;
                            break;
                    }

                }

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error, No se encontró el acta.");
            }
        }
    }

    public void preGuardarMarginacion() {
        nuevaMarginacion = new Marginacion();
        plnmarginacion = true;
    }

    public void guardarmarginacion() {
        try {
            tipoMarginacionSeleccionado = servicioTipoMarginacion.find(new TipoMarginacion(), IDtipoMarginacionSeleccionado);

            nuevaMarginacion.setMrgUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            nuevaMarginacion.setMrgFHR(Calendar.getInstance().getTime());

            nuevaMarginacion.setTmcId(tipoMarginacionSeleccionado);
            nuevaMarginacion.setActNumero(actaSeleccionada);
            nuevaMarginacion.setMrgAlt2(repertorioNumero.toString());
            nuevaMarginacion.setMrgFch(repertorioParam.getRepFHR());
//        tipoLibroSeleccionado = servicioTipoLibro.find(new TipoLibro(), IDtipoLibroSeleccionado);
            servicioMarginacion.create(nuevaMarginacion);
            listaMarginacionNuevos.add(nuevaMarginacion); //lleno lista nuevos
            /*Limpiar campos*/
//        PrimeFaces.current().resetInputs("formMarginacion:pnlMarginacionP");

            tramiteUtil.insertarTramiteAccion(tramiteParam, nuevaMarginacion.getMrgAlt2(),
                    "Marginación de Repertorio ",
                    tramiteParam.getTraEstado(), tramiteParam.getTraEstadoRegistro(), null);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actas marginadas correctamente", ""));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al marginar", ""));

        }
//        try {
//            crearPDFconTextoHTML(actaSeleccionada.getActActa());
//        } catch (Exception e) {
//            e.printStackTrace();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear PDF", ""));
//        }

    }

    public void cambiarEstado() throws ServicioExcepcion {

        Boolean estanTodosRepertoriosMarginados = servicioMarginacion.estanTodosRepertoriosMarginados(
                tramiteParam.getTraNumero());
        if (estanTodosRepertoriosMarginados) {
            listaRepUsu = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(IDusuarioLogeado, "MRG");
            for (RepertorioUsuario repertorioUsuario : listaRepUsu) {
                if (repertorioUsuario.getRepNumero().getTraNumero().equals(tramiteParam)) {
                    repertorioUsuario.setRpuEstado("I");
                    servicioRepertorioUsuario.edit(repertorioUsuario);
                }
            }

            try {
                Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MAT", "Matriculacion", 1);
                for (RepertorioUsuario repUsu : listaRepUsu) {
                    if (tramiteParam.equals(
                            repUsu.getRepNumero().getTraNumero())) {
                        RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
                        nuevoRepUsu = repUsu;
                        nuevoRepUsu.setRpuTipo("MAT");
                        nuevoRepUsu.setUsuId(usuarioAsignado);
                        nuevoRepUsu.setRpuEstado("A");
                        servicioRepertorioUsuario.create(nuevoRepUsu);

                        tramiteUtil.insertarTramiteAccion(nuevoRepUsu.getRepNumero().getTraNumero(),
                                nuevoRepUsu.getRepNumero().getRepNumero().toString(),
                                "Repertorio Usuario " + nuevoRepUsu.getRepNumero().getRepNumero()
                                + "asignado al usuario: " + usuarioAsignado.getUsuLogin(),
                                nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
                                nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                usuarioAsignado.getUsuLogin());
                    }
                }
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al generar nuevos repertorios usuario con estado 'MAT'");
            }
            try {
                tramiteParam.setTraEstado("MAT");
                servicioTramite.edit(tramiteParam);

                tramiteUtil.insertarTramiteAccion(tramiteParam, tramiteParam.getTraNumero().toString(),
                        "Actualización del estado de Tramite a 'MAT'",
                        tramiteParam.getTraEstado(), tramiteParam.getTraEstadoRegistro(), null);

                JsfUtil.addSuccessMessage("Tramite actualizado");
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar estado del Trámite");

            }

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(
                        "/RM-EMPRESARIAL-web/paginas/PROCESOS/Marginacion/MarginacionSelectRep.xhtml");
            } catch (IOException ex) {
                JsfUtil.addErrorMessage("Error al redireccionar");

                Logger.getLogger(MarginacionControler.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            JsfUtil.addErrorMessage("Aún no se han marginado todos "
                    + "los repertorios asignados al trámite " + tramiteParam.getTraNumero());

        }

    }

//    public void crearPDFconTextoHTML(String textoActa) throws IOException, DocumentException {
//        FacesContext fc = FacesContext.getCurrentInstance();
//        ExternalContext ec = fc.getExternalContext();
//
//        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
//
//        System.out.println("1" + ec.getRequestContextPath());// /RM-EMPRESARIAL-web
//        System.out.println("1" + ec.getRequestServletPath()); // /paginas/PROCESOS/Marginacion/Marginacion.xhtml
//        System.out.println("1" + ec.getRealPath("/")); //F:\RM\Proyectos Netbeans\BM_RME\RM-EMPRESARIAL-ear\target\gfdeploy\RM-EMPRESARIAL-ear\RM-EMPRESARIAL-web-1.0-SNAPSHOT_war\
//
//        String dirContexto = ec.getRequestContextPath();
//        String dirRealAplicacion = ec.getRealPath("/");//**Se debe crear la carpeta y el archivo dentro del directorio real**
//
//        Document document = new Document();//creo nuevo documento pdf
//
//        Path tempDir = Files.createTempDirectory(Paths.get(dirRealAplicacion + "temp/"),
//                dataManagerSesion.getUsuarioLogeado().getUsuLogin() + "_");//creo un directorio temporal en los directorios del deploy de la aplicacion.
//
//        tempDir.toFile().deleteOnExit();//al cerrar la JVM, es decir al detener el glassfish se elimina la carpeta temporal
//
//        String nombreArchivo = "prueba.pdf";//nombre del archivo
//        String dirFinalArchivo = tempDir.getParent().toString() + "/" + tempDir.getFileName().toString() + "/" + nombreArchivo;//path final del archivo
//        // tempDir.getParent()=> F:\RM\Proyectos Netbeans\BM_RME\RM-EMPRESARIAL-ear\target\gfdeploy\RM-EMPRESARIAL-ear\RM-EMPRESARIAL-web-1.0-SNAPSHOT_war\temp 
//        // tempDir.getFileName().toString() => BMORA_2023083052387587915
//        // nombreArchivo => prueba.pdf
//        // dirFinalArchivo => F:\RM\Proyectos Netbeans\BM_RME\RM-EMPRESARIAL-ear\target\gfdeploy\RM-EMPRESARIAL-ear\RM-EMPRESARIAL-web-1.0-SNAPSHOT_war\temp/BMORA_6348744837621346013/prueba.pdf
//        System.out.println("2" + dirFinalArchivo);
//        dirArchivoComoRecurso = dirContexto + "/" + "temp" + "/" + tempDir.getFileName().toString() + "/" + nombreArchivo;
//        url = "/" + "temp" + "/" + tempDir.getFileName().toString() + "/" + nombreArchivo;
//
//        FileOutputStream fos = new FileOutputStream(dirFinalArchivo);//defino la salida
//
////        FileOutputStream fos = new FileOutputStream(path);
//        PdfWriter writer = PdfWriter.getInstance(document, fos);//declaro guardar el documento en el dir creado
//
//        document.open();//abro el documento para editar
//
////        BaseFont bf1 = BaseFont.createFont("c:/windows/fonts/arial.ttf",
////            BaseFont.CP1252, BaseFont.EMBEDDED);
////        Font font1 = new Font(bf1, 12);
//        PdfPTable table = new PdfPTable(2);
//        table.setWidths(new float[]{70, 30});
//        table.setWidthPercentage(100);
////------------------------------
////CODIGO QUE LEE EL TEXTO EN FORMATO XHTML Y LO COPIA A UN PARRAFO DEL ARCHIVO PDF CON EL FORMAT CORRESPONDIENTE
//        StringReader strReader = new StringReader(textoActa);
//        ArrayList p = new ArrayList();
//        p = (ArrayList) HTMLWorker.parseToList(strReader, null);
//        Paragraph parrafoIzq = new Paragraph();
//        for (int k = 0; k < p.size(); ++k) {
////            System.out.println("-> " + p.get(k));
//            parrafoIzq.add((Element) p.get(k));
//        }
////------------------------------
//        Font font = new Font(FontFamily.HELVETICA, 10);
//        Paragraph parrafoDer = new Paragraph();
//        parrafoDer.setFont(font);
//        parrafoIzq.setAlignment(Element.ALIGN_JUSTIFIED);
//        parrafoDer.setAlignment(Element.ALIGN_JUSTIFIED);
//
//        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
//        SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        try {
//            for (Marginacion mrg : servicioMarginacion.listarTodo()) {
//                parrafoDer.add(sdfDateTime.format(mrg.getMrgFHR()));
//                parrafoDer.add("\n");
//                parrafoDer.add(mrg.getMrgUser());
//                parrafoDer.add("\n");
//                parrafoDer.add(mrg.getMrgAlt1());
//                parrafoDer.add("\n");
//                parrafoDer.add(mrg.getMrgAlt2());
//                parrafoDer.add("\n");
//                parrafoDer.add(sdfDate.format(mrg.getMrgFch()));
//                parrafoDer.add("\n");
//                parrafoDer.add(Chunk.NEWLINE);
//            }
//        } catch (ServicioExcepcion ex) {
//            Logger.getLogger(MarginacionControler.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        PdfPCell celdaIzq = new PdfPCell(parrafoIzq);
//        PdfPCell celdaDer = new PdfPCell(parrafoDer);
//        celdaIzq.setBorder(Rectangle.NO_BORDER);
//        celdaDer.setBorder(Rectangle.NO_BORDER);
//
//        table.addCell(celdaIzq);
//        table.addCell(celdaDer);
////        document.add(parrafo); //añado un parrafo al documento pdf
//        document.add(table);
//        document.close();//cierro el documento
//        writer.close();//cierro la escritura
//    }
}
