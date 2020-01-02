/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.ActaDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TipoLibroDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.Tomo;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.modelo.Volumen;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.MargenServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TomoServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import com.rm.empresarial.servicio.VolumenServicio;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author Marco
 */
@Named(value = "controladorGenerarLibro")
@ViewScoped
public class ControladorGenerarLibro implements Serializable {

    @Inject
    @Getter
    @Setter
    private DataManagerSesion dataManagerSesion;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Inject
    private PdfTempUtil pdfTempUtil;

    @Inject
    private TramiteUtil tramiteUtil;

    @EJB
    private ActaDao actaDao;

    @EJB
    private RepertorioDao repertorioDao;

    @EJB
    private TipoLibroDao tipoLibroDao;

    @EJB
    private TramiteDao tramiteDao;

    @EJB
    private RepertorioUsuarioDao repertorioUsuarioDao;

    @EJB
    private MarginacionServicio servicioMarginacion;

    @EJB
    private TomoServicio servicioTomo;

    @EJB
    private VolumenServicio servicioVolumen;

    @EJB
    private MargenServicio servicioMargen;

    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private CargaLaboralServicio servicioCargaLaboral;

    @EJB
    private UsuarioServicio servicioUsuario;

    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @EJB
    private TipoTramiteServicio servicioTipoTramite;

    @Getter
    @Setter
    private String dirCarpetaTemp;

    @Getter
    @Setter
    private Margen margen;

    @Getter
    @Setter
    private Acta acta;

    @Getter
    @Setter
    private Tramite tramite;

    @Getter
    @Setter
    private Acta actaSeleccionada;

    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuario;

    @Getter
    @Setter
    private Date fecha;

    @Getter
    @Setter
    private Long libroId;

    @Getter
    @Setter
    private Long volumen;

    @Getter
    @Setter
    private Volumen volumenEncontrado;

    @Getter
    @Setter
    private int tomo;

    @Getter
    @Setter
    private Tomo tomoEncontrado;

    @Getter
    @Setter
    private Long repertorioNumero;

    @Getter
    @Setter
    private Long actaNumero;

    @Getter
    @Setter
    private BigInteger actInscripcion;

    @Getter
    @Setter
    private Usuario usuarioAsignado;

    @Getter
    @Setter
    private List<Acta> listaActa = new ArrayList<>();

    @Getter
    @Setter
    private List<Acta> listaActaProcesar = new ArrayList<>();

    @Getter
    @Setter
    private List<Acta> listaActaBuscar = new ArrayList<>();

    @Getter
    @Setter
    private Acta actaPorIdLibro;

    @Getter
    @Setter
    private List<TipoLibro> listaTipoLibro = new ArrayList<>();

    @Getter
    @Setter
    private List<RepertorioUsuario> listaRepertorioUsuario = new ArrayList<>();

    @Getter
    @Setter
    private List<RepertorioUsuario> listaRepertorioUsuarioActualizar = new ArrayList<>();

    @Getter
    @Setter
    private TipoLibro tipoLibro;

    @Getter
    @Setter
    private Repertorio repertorio;

    @Getter
    @Setter
    private String libros;

    @Getter
    @Setter
    private String dirArchivoComoRecurso;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private int totalHojas;

    @Getter
    @Setter
    private int totHojasMasBdHojas;

    @Getter
    @Setter
    private Integer ultimaInscripcion;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private Boolean repertorioUsuarioVerificado = false;

    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSelec;

    @Getter
    @Setter
    List<RepertorioUsuario> listRepUsuSinTerminar = new ArrayList<>();

    @Getter
    private ConfigDetalle cd;

    @PostConstruct
    public void cargarDatos() {
        acta = new Acta();
        tipoLibro = new TipoLibro();
        repertorio = new Repertorio();
        tramite = new Tramite();
        actaPorIdLibro = new Acta();
        volumenEncontrado = new Volumen();
        tramiteAccion = new TramiteAccion();
        repertorioUsuarioSelec = new RepertorioUsuario();
        totalHojas = 0;
        totHojasMasBdHojas = 0;
        try {
            listaTipoLibro = tipoLibroDao.listarTodo();
            cd = servicioConfigDetalle.buscarPorConfig("NUMHOJPERMITIDOVOLUM");
        } catch (ServicioExcepcion e) {
            System.out.println(e);
        }
    }

    public void clear() {
        listaActaBuscar.clear();
        listaActa.clear();
        listaActaProcesar.clear();
        listaRepertorioUsuario.clear();
        listaRepertorioUsuarioActualizar.clear();
        totalHojas = 0;
        totHojasMasBdHojas = 0;
    }

    public void buscarActa() throws ServicioExcepcion, ParseException {
        System.out.println("fecha: " + fecha);
        System.out.println("libro: " + libroId);
        repertorioUsuarioVerificado = false;
        String user = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
        Long userId = dataManagerSesion.getUsuarioLogeado().getUsuId();
        clear();
        try {
            verificarProcesoTermidado();
            if (repertorioUsuarioVerificado) {

                listaActaBuscar = actaDao.buscarActaPorFechaYLibro(fecha, libroId);
                actaPorIdLibro = actaDao.buscarActaPorTipoLibro(libroId);
                tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(String.valueOf(libroId));
                SimpleDateFormat pattern = new SimpleDateFormat("YYYY");
                tomoEncontrado = servicioTomo.buscarPorAnioYtipoLib(tipoLibro.getTplId(), Long.valueOf(pattern.format(fecha)));
                tomo = tomoEncontrado.getTomTomo();
                ultimaInscripcion = actaDao.obtenerUltimoNumInsPorTplYAnio(tipoLibro.getTplId(), Integer.parseInt(pattern.format(fecha))).getActInscripcion().intValue();
                int aux = 0;
                for (Acta actaLista : listaActaBuscar) {

                    listaRepertorioUsuario = repertorioUsuarioDao.listarPorRepNumPorEstadoPorUsrLog(actaLista.getRepNumero().getRepNumero(), userId);
                    for (RepertorioUsuario repertorioUsu : listaRepertorioUsuario) {
                        listaRepertorioUsuarioActualizar.add(repertorioUsu);
                    }

                    if (!listaRepertorioUsuario.isEmpty()) {
                        repertorioNumero = actaLista.getRepNumero().getRepNumero();
                        actaNumero = actaLista.getActNumero();
                        actInscripcion = actaLista.getActInscripcion();

                        listaActa.add(actaLista);

                    } else {
                        volumen = new Long(0);
                        //tomo = 0;
                        System.out.println("lista no hay ");
                    }
                    aux++;
                    actaLista.setActInscripcion(BigInteger.valueOf(ultimaInscripcion + aux));

                }

                if (!actaPorIdLibro.equals(null) && !listaActa.isEmpty() && listaActa.size() != 0) {
                    volumenEncontrado = servicioVolumen.buscarPorTomo(tomoEncontrado.getTomId());
                    volumen = volumenEncontrado.getVleVolumen();
                    System.out.println("vol: " + volumen);
                    System.out.println("tomo: " + tomo);

                    numPaginasPorActa();
                    totHojasMasBdHojas = volumenEncontrado.getVleHojas() + totalHojas;
                    if (totHojasMasBdHojas > cd.getConfigDetalleNumero().intValueExact()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Se ha superado el máximo de hojas (" + (volumenEncontrado.getVleHojas() + totalHojas) + " de " + cd.getConfigDetalleNumero().intValueExact() + ")"));
                    }
                    setListaActaProcesar(listaActa);
                } else {
                    volumen = new Long(0);
                    //tomo = 0;
                }
            }

        } catch (Exception e) {
            System.out.println("com.rm.empresarial.controlador.ControladorGenerarLibro.buscarActa()");
            System.out.println(e);
        }

    }

    public void verificarProcesoTermidado() throws ServicioExcepcion {
        repertorioUsuarioVerificado = false;
        List<TipoTramite> listTipoTram = new ArrayList<>();
        List<Repertorio> listRep = new ArrayList<>();
        List<RepertorioUsuario> listRepUsuarioTemporal = new ArrayList<>();
        List<RepertorioUsuario> listRepUsuario = new ArrayList<>();
        listTipoTram = servicioTipoTramite.listarPorTipoLibro(libroId);
        //obtener todos los elementos de Repertorio Usuario donde los repertorios correspondan a la fecha ingresada...
        //... y tambien tengan el cualquier tipo de Tramite que sea parte del tipo de libro seleccionado 
        for (TipoTramite tipoTramite : listTipoTram) {
            listRepUsuarioTemporal.clear();
            listRepUsuarioTemporal = servicioRepertorioUsuario.listarPorFechaRepertorio_IdTipoTramite_Tipo(fecha, tipoTramite.getTtrId());
            for (RepertorioUsuario repUsu : listRepUsuarioTemporal) {
                if (!listRepUsuario.contains(repUsu)) {
                    listRepUsuario.add(repUsu);
                }
            }
        }

        //verificar que todos estos elementos de repertorio usuario esten en estado "I"
        int contador = 0;
        listRepUsuSinTerminar.clear();
        for (RepertorioUsuario repertorioUsuario1 : listRepUsuario) {
            if (repertorioUsuario1.getRpuEstado().equals("I")) {
                contador++;
            } else {
                if (!listRepUsuSinTerminar.contains(repertorioUsuario1)) {
                    listRepUsuSinTerminar.add(repertorioUsuario1);
                }

            }

        }
        if(!listRepUsuario.isEmpty()){
            if (contador == listRepUsuario.size()) {
            repertorioUsuarioVerificado = true;
        }
            else{
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dlgRepUsuario').show();");

            }
        }
        
       

    }

    public void onRowSelectEvent(SelectEvent event) {
        totHojasMasBdHojas += ((Acta) event.getObject()).getActNumeroHojas();
        totalHojas += ((Acta) event.getObject()).getActNumeroHojas();
    }

    public void onRowUnselectEvent(UnselectEvent event) {
        totHojasMasBdHojas -= ((Acta) event.getObject()).getActNumeroHojas();
        totalHojas -= ((Acta) event.getObject()).getActNumeroHojas();
    }

    public void procesar() throws ServicioExcepcion, ParseException {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (volumen != null) {
                if (volumen >= volumenEncontrado.getVleVolumen()) {
                    if (!listaActaProcesar.isEmpty() && !listaRepertorioUsuarioActualizar.isEmpty()) {
                        for (Acta act : listaActaProcesar) {

                            act.setActVolumen(volumen);
                            act.setActTomo(tomo);

                            actaDao.edit(act);

                            repertorio = repertorioDao.encontrarRepertorioPorId(String.valueOf(act.getRepNumero().getRepNumero()));
                            tramite = tramiteDao.buscarTramitePorNumero(repertorio.getTraNumero().getTraNumero());

                            tramiteUtil.insertarTramiteAccion(repertorio.getTraNumero(), String.valueOf(act.getActNumero()),
                                    "ACTA PROCESADA CON TOMO: " + tomo + " Y VOLUMEN: " + volumen,
                                    "IMP", tramite.getTraEstadoRegistro(), "");
                        }
                        for (RepertorioUsuario repertorioUsu : listaRepertorioUsuarioActualizar) {
                            repertorioUsu.setRpuEstado("I");
                            repertorioUsuarioDao.edit(repertorioUsu);
                        }
                        crearRepUsuario_TramAccion();
                        String inscripcionFinal = null;
                        for (int i = 0; i < listaActaProcesar.size(); i++) {
                            if (i == listaActaProcesar.size() - 1) {
                                inscripcionFinal = listaActaProcesar.get(i).getActInscripcion().toString();
                            }
                        }
                        if (volumen.equals(volumenEncontrado.getVleVolumen())) {
                            if ((volumenEncontrado.getVleHojas() + totalHojas) > cd.getConfigDetalleNumero().intValueExact()) {
                                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Se ha superado el máximo de hojas (" + (volumenEncontrado.getVleHojas() + totalHojas) + " de " + cd.getConfigDetalleNumero().intValueExact() + ")"));
                            }
                            volumenEncontrado.setVleHojas(volumenEncontrado.getVleHojas() + totalHojas);
                            volumenEncontrado.setVleInscripcionFinal(inscripcionFinal);
                            servicioVolumen.edit(volumenEncontrado);
                        } else if (volumen > volumenEncontrado.getVleVolumen()) {
                            String inscripcionInicial = ultimaInscripcion.toString();
                            Volumen nuevoVol = new Volumen(volumen, totalHojas, BigInteger.ZERO,
                                    BigInteger.ZERO, "A", dataManagerSesion.getUsuarioLogeado().getUsuLogin(),
                                    Calendar.getInstance().getTime(), null, null, null, null, tomoEncontrado,
                                    inscripcionInicial, inscripcionFinal);
                            servicioVolumen.create(nuevoVol);
                        }
                        totalHojas = 0;
                        totHojasMasBdHojas = 0;
                        //FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage("", "Acta(s) guardada(s)"));
                    } else {
                        //FacesContext context = FacesContext.getCurrentInstance();
                        String mensaje = "No hay actas para procesar";

                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", mensaje));

                    }
                } else if (volumen < volumenEncontrado.getVleVolumen()) {
                    String mensaje = "El volumen debe ser mayor o igual a " + volumenEncontrado.getVleVolumen();

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", mensaje));
                }
            } else {
                // FacesContext context = FacesContext.getCurrentInstance();
                String mensaje = "Ingrese un valor de Volumen";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", mensaje));
            }
            buscarActa();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void crearRepUsuario_TramAccion() throws ServicioExcepcion {
        for (RepertorioUsuario repUsuario : listaRepertorioUsuarioActualizar) {
            tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(repUsuario.getRepNumero().getTraNumero().getTraNumero());

            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(tramiteSeleccionado.getTraNumero(), "FME"));
            if (tramiteAccion == null) {
                //codigo CargaLaboral
                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("FME", "FirmaElectronica", 1);
                cargaLaboralUtil.restarCargaAsignadaAlUsuario(repUsuario.getUsuId().getUsuId(), repUsuario.getRpuTipo());
            } else {
                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                int auxCarga = cargaAdd.getCarAsignado() + 1;
                cargaAdd.setCarAsignado((short) auxCarga);
                servicioCargaLaboral.edit(cargaAdd);
            }

            RepertorioUsuario repertorioUsuario = new RepertorioUsuario();
            repertorioUsuario.setRpuTipo("FME");
            repertorioUsuario.setUsuId(usuarioAsignado);
            repertorioUsuario.setRpuEstado("A");
            repertorioUsuario.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            repertorioUsuario.setRpuFHR(Calendar.getInstance().getTime());
            repertorioUsuario.setRepNumero(repUsuario.getRepNumero());

            repertorioUsuarioDao.create(repertorioUsuario);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteSeleccionado.getTraNumero().toString(),
                    "Tramite: " + tramiteSeleccionado.getTraNumero()
                    + " asignada al usuario " + usuarioAsignado.getUsuLogin(),
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), usuarioAsignado.getUsuLogin());

            tramiteSeleccionado.setTraEstado("FME");
            tramiteDao.edit(tramiteSeleccionado);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteSeleccionado.getTraNumero().toString(),
                    "ACTUALIZACIÓN DEL ESTADO DE TRAMITE A 'FME'",
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), null);

        }

    }

//    public void mostrarLibros() {
//        libros = "";
//        for (Acta actaSelec : listaActa) {
//            libros += actaSelec.getActActa();
//        }
//    }
    public Path crearCarpetaTemporalEnDirWebDeDeploy(String dirPadre, String nombreCarpetaTemp) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        System.out.println("1" + ec.getRequestContextPath());// /RM-EMPRESARIAL-web
        System.out.println("1" + ec.getRequestServletPath()); // /paginas/PROCESOS/Marginacion/Marginacion.xhtml
        System.out.println("1" + ec.getRealPath("/")); //F:\RM\Proyectos Netbeans\BM_RME\RM-EMPRESARIAL-ear\target\gfdeploy\RM-EMPRESARIAL-ear\RM-EMPRESARIAL-web-1.0-SNAPSHOT_war\

        String dirWebRealDeploy = ec.getRealPath("/");//**Se debe crear la carpeta y el archivo dentro del directorio real**

        Path tempDir = Files.createTempDirectory(Paths.get(dirWebRealDeploy + dirPadre + "/"),
                nombreCarpetaTemp + "_");//creo un directorio temporal en los directorios del deploy de la aplicacion.

        tempDir.toFile().deleteOnExit();//al cerrar la JVM, es decir al detener el glassfish se elimina la carpeta temporal

        // tempDir.getParent()=> F:\RM\Proyectos Netbeans\BM_RME\RM-EMPRESARIAL-ear\target\gfdeploy\RM-EMPRESARIAL-ear\RM-EMPRESARIAL-web-1.0-SNAPSHOT_war\temp 
        // tempDir.getFileName().toString() => BMORA_2023083052387587915
        // nombreArchivo => prueba.pdf
        // dirFinalArchivo => F:\RM\Proyectos Netbeans\BM_RME\RM-EMPRESARIAL-ear\target\gfdeploy\RM-EMPRESARIAL-ear\RM-EMPRESARIAL-web-1.0-SNAPSHOT_war\temp/BMORA_6348744837621346013/prueba.pdf
        return tempDir;
    }

    public void generarLinksAccesoAlPdf(String dirPadre, Path carpetaTemporal, String nombreArchivo) {

        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        String dirContexto = ec.getRequestContextPath();

        dirArchivoComoRecurso = dirContexto + "/" + dirPadre + "/" + carpetaTemporal.getFileName().toString() + "/" + nombreArchivo;
        url = "/" + "temp" + "/" + carpetaTemporal.getFileName().toString() + "/" + nombreArchivo;
        System.out.println("url: " + url);

    }

//    public void crearPDFconTextoHTML() throws DocumentException, IOException, ServicioExcepcion {
//        String nombreArchivo = "prueba.pdf";//nombre del archivo
//        String dirPadre = "temp";
//        Path tempDir = crearCarpetaTemporalEnDirWebDeDeploy(dirPadre, dataManagerSesion.getUsuarioLogeado().getUsuLogin());
//        String dirFinalArchivo = tempDir.getParent().toString() + "/" + tempDir.getFileName().toString() + "/" + nombreArchivo;//path final del archivo
//
//        FileOutputStream fos = new FileOutputStream(dirFinalArchivo);//defino la salida
//        Document document = new Document();//creo nuevo documento pdf
//        PdfWriter writer = PdfWriter.getInstance(document, fos);//declaro guardar el documento en el dir creado
//
//        document.open();//abro el documento para editar
//        document.setMargins(180, 0, 0, 0); //pongo márgenes
//
//        PdfPTable table = new PdfPTable(2);
//        table.setWidths(new float[]{70, 30});
//        //*****------------------------------
//        //PARRAFO ACTA
//        Paragraph parrafoIzq = new Paragraph();
//        parrafoIzq.setAlignment(Element.ALIGN_JUSTIFIED);
//        parrafoIzq = convertirHtmlConFormatoAParrafoPdf(parrafoIzq, listaActa.get(0).getActActa());
//        //------------------------------*****
//        //*****------------------------------
//        //PARRAFO MARGINACION
//        Font font = new Font(FontFamily.HELVETICA, 10);
//        Paragraph parrafoDer = new Paragraph();
//        parrafoDer.setFont(font);
//        parrafoDer.setAlignment(Element.ALIGN_JUSTIFIED);
//        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
//        SimpleDateFormat sdfDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        for (Marginacion mrg : servicioMarginacion.listarTodo()) {
//            parrafoDer.add(sdfDateTime.format(mrg.getMrgFHR()));
//            parrafoDer.add("\n");
//            parrafoDer.add(mrg.getMrgUser());
//            parrafoDer.add("\n");
//            parrafoDer.add(mrg.getMrgAlt1());
//            parrafoDer.add("\n");
//            parrafoDer.add(mrg.getMrgAlt2());
//            parrafoDer.add("\n");
//            parrafoDer.add(sdfDate.format(mrg.getMrgFch()));
//            parrafoDer.add("\n");
//            parrafoDer.add(Chunk.NEWLINE);
//        }
//
//        //------------------------------*****
//        PdfPCell celdaIzq = new PdfPCell(parrafoIzq);
//        PdfPCell celdaDer = new PdfPCell(parrafoDer);
//        celdaIzq.setBorder(Rectangle.NO_BORDER);
//        celdaDer.setBorder(Rectangle.NO_BORDER);
//        table.addCell(celdaIzq);
//        table.addCell(celdaDer);
//
////        writer.setPageEvent(new Watermark()));
//        document.add(table);//añado la tabla al documento
//        document.close();//cierro el documento
//        writer.close();//cierro la escritura
//        generarLinksAccesoAlPdf(dirPadre, tempDir, nombreArchivo); //enlaces desde la vista
//
//        
//        
//        
//        
//        
//        
////        PdfReader reader = new PdfReader(dirFinalArchivo);
////        System.out.println("# paginas: " + reader.getNumberOfPages());
////
////        Document document2 = new Document();
////        String dirFinalArchivo2 = tempDir.getParent().toString() + "/" + tempDir.getFileName().toString() + "/" + "prueba222.pdf";//path final del archivo
////
////        FileOutputStream fos2 = new FileOutputStream(dirFinalArchivo2);//defino la salida
////        PdfWriter writer2 = PdfWriter.getInstance(document2, fos2);
////        PdfImportedPage importedPage;
////
////        for (int page = 1; page <= reader.getNumberOfPages(); page++) {
//////            importedPage = copy.getImportedPage(reader, page);
////
//////            importedPage = writer.getImportedPage(reader, page);
//////        importedPage = writer.getImportedPage(reader, page);
//////            document2.add(Image.getInstance(importedPage));
////            fos2.write(reader.getPageContent(page));
////
////        }
////        PdfCopy copy = new PdfCopy(
////                document2, new FileOutputStream(dirFinalArchivo2));
////        document2.open();
////         int n = reader.getNumberOfPages();
////        for (int i = 0; i < n;) {
////            copy.addPage(copy.getImportedPage(reader, ++i));
////        }
////        document2.close();
////
////        reader.close();
////        writer2.close();
////        generarLinksAccesoAlPdf(dirPadre, tempDir, "prueba222.pdf"); //enlaces desde la vista
//
//    }
//    public Paragraph convertirHtmlConFormatoAParrafoPdf(Paragraph parrafo, String textoHtml) throws IOException {
////CODIGO QUE LEE EL TEXTO EN FORMATO XHTML Y LO COPIA A UN PARRAFO DEL ARCHIVO PDF CON EL FORMAT CORRESPONDIENTE
//        StringReader strReader = new StringReader(textoHtml);
//        ArrayList p = new ArrayList();
//        p = (ArrayList) HTMLWorker.parseToList(strReader, null);
//
////        parrafoIzq.setAlignment(Element.ALIGN_JUSTIFIED);
//        for (int k = 0; k < p.size(); ++k) {
////            System.out.println("-> " + p.get(k));
//            parrafo.add((Element) p.get(k));
//        }
//        return parrafo;
//    }
//    class formatoPagina extends PdfPageEventHelper {
//
//        public formatoPagina() {
//
//        }
//
////        @Override
////        public void onParagraphEnd(PdfWriter writer, Document document, float paragraphPosition) {
////            drawLine(writer.getDirectContent(),
////                    document.left(), document.right(),
////                    paragraphPosition - 5);
////        }
//        @Override
//        public void onEndPage(PdfWriter writer, Document document) {
//            super.onEndPage(writer, document); //To change body of generated methods, choose Tools | Templates.
//        }
//
//        public void drawLine(PdfContentByte cb,
//                float x1, float x2, float y) {
//            cb.moveTo(x1, y);
//            cb.lineTo(x2, y);
//            cb.stroke();
//        }
//
//    }
//
//    class Watermark extends PdfPageEventHelper {
//
//        Font FONT
//                = new Font(FontFamily.HELVETICA, 52, Font.BOLD, new GrayColor(0.75f));
//
//        public void onEndPage(PdfWriter writer, Document document) {
//            ColumnText.showTextAligned(writer.getDirectContentUnder(),
//                    Element.ALIGN_CENTER, new Phrase("FOOBAR FILM FESTIVAL", FONT),
//                    297.5f, 421, writer.getPageNumber() % 2 == 1 ? 45 : -45);
//        }
//
//    }
    public void numPaginasPorActa() throws IOException, ServicioExcepcion {
        int nPags = 0;
        try {
            setMargen(servicioMargen.obtenerPorMarTipo("LIB"));
            //*****------------------------------
            //PARRAFO ACTA
            if (!listaActa.isEmpty() && listaActa.size() >= 1) {
                int i = 1;
                setDirCarpetaTemp(pdfTempUtil.directorio());//Creo el directorio temporal sin los archivos
                for (Acta acta : listaActa) {
                    if (acta.getActActa() != null) {
                        String nombreArchivo = "nlib" + i + ".pdf";
                        String dirFinalArchivo = dirCarpetaTemp + nombreArchivo;//Directorio final por cada archivo
                        FileOutputStream fos = new FileOutputStream(dirFinalArchivo);
                        Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());
                        PdfWriter writer = PdfWriter.getInstance(document, fos);
                        document.open();
                        Paragraph parrafo = new Paragraph();
                        parrafo = pdfTempUtil.convertirHtmlConFormatoAParrafoPdfOld(parrafo, acta.getActActa());
                        parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                        document.add(parrafo);
                        nPags = writer.getPageNumber();
                        acta.setActNumeroHojas(nPags);
                        totalHojas += nPags;
                        document.close();//cierro el documento
                        writer.close();//cierro la escritura
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
//            JsfUtil.addErrorMessage("Error al generar el Pdf");
        }
    }
}
