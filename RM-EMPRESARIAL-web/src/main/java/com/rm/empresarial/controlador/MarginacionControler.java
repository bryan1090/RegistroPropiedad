/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.UtilitarioPdf;
import com.rm.empresarial.dao.MarginacionDetalleDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.MarginacionDetalle;
import com.rm.empresarial.modelo.MarginacionPH;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoMarginacion;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.MarginacionPhServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.ParroquiaServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TipoLibroServicio;
import com.rm.empresarial.servicio.TipoMarginacionServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Java
 */
@Named(value = "marginacioncontroler")
@ViewScoped

public class MarginacionControler implements Serializable {

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
    private TramiteDetalleDao tramiteDetalleDao;

    @EJB
    private TipoTramiteDao tipoTramiteDao;

    @EJB
    private TramiteServicio servicioTramite;

    @EJB
    private PropiedadServicio servicioPropiedad;

    @EJB
    private CargaLaboralServicio servicioCargaLaboral;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

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
    private TipoTramiteServicio servicioTipoTramite;
    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;

    @EJB
    private MarginacionPhServicio servicioMarginacionPh;

    @EJB
    private MarginacionDetalleDao servicioMarginacionDetalle;

    @EJB
    private UsuarioServicio servicioUsuario;

    @EJB
    private ParroquiaServicio servicioParroquia;

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
    private Date fechaMarginacion;

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
    private Boolean rendBtnCrearActa = false;

    @Getter
    @Setter
    private boolean plnmarginacion;

    @Getter
    @Setter
    private String areamarginacion;

    @Getter
    @Setter
    private Integer numRepPendientes;

    @Getter
    @Setter
    private Integer numRepRealizados;

    @Getter
    @Setter
    private TipoTramite tipoTramite;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalle;

//    @Getter
//    @Setter
//    private boolean mostrarComboTipoTram;
    @Getter
    @Setter
    private TipoTramite tipoTramiteSel;

    @Getter
    @Setter
    private List<TipoTramite> listaTipoTramite;

    @Getter
    @Setter
    private List<RepertorioUsuario> listaRepSelMrg;

    @Getter
    @Setter
    private Long idRepertorio2;

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesHijas;

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesSelec;

    @Getter
    @Setter
    private Boolean bolDisableGuardar = Boolean.FALSE;

    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSelec;

    // marginacion detalle 
    @Getter
    @Setter
    private Double porcentajeDerAcn;

    @Getter
    @Setter
    private String txtEdificioBloque;

    @Getter
    @Setter
    private String txtNumOficio;
    @Getter
    @Setter
    private Date fechaOficio;

    @Getter
    @Setter
    private String txtNotario;

    @Getter
    @Setter
    private String txtObservacion;

    @Getter
    @Setter
    private List<MarginacionDetalle> listaMarginacionDetalle;

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

    public MarginacionControler() {
        plnmarginacion = false;
        numRepPendientes = 0;
        numRepRealizados = 0;
        tramiteAccion = new TramiteAccion();
        repertorioUsuarioSelec = new RepertorioUsuario();
        fechaMarginacion = Calendar.getInstance().getTime();
        tipo="I";

    }

    @PostConstruct
    public void postConstructor() {
        IDusuarioLogeado = dataManagerSesion.getUsuarioLogeado().getUsuId();
        listaMarginacionNuevos = new ArrayList<>();
        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        repertorioNumero = new Long(params.get("numero").toString());
        repertorioParam = servicioRepertorio.find(new Repertorio(), repertorioNumero);
        tramiteParam = repertorioParam.getTraNumero();
        setRepertorioUsuarioSelec(servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
                repertorioParam.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "MRG"));

        try {
            contabilizarCertificados();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(MarginacionControler.class.getName()).log(Level.SEVERE, null, ex);
        }
        urlRTF = "";
    }

    public void contabilizarCertificados() throws ServicioExcepcion {
        listaRepUsu = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(IDusuarioLogeado, "MRG");
        numRepPendientes = 0;
        numRepRealizados = 0;
        for (RepertorioUsuario repertorioUsuario : listaRepUsu) {
            if (repertorioUsuario.getRepNumero().getTraNumero().equals(tramiteParam)) {
                if (repertorioUsuario.getRpuEstadoProceso() == null) {
                    numRepPendientes++;
                } else {
                    if (!repertorioUsuario.getRpuEstadoProceso().equalsIgnoreCase("TERMINADO")) {
                        numRepPendientes++;
                    } else {
                        numRepRealizados++;
                    }

                }

            }
        }
//        Long idUsuario = dataManagerSesion.getUsuarioLogeado().getUsuId();
//
//        numTrabajosAsignados = servicioCertificadoCarga.numCerPendientesPor_usuario_tipo_estado_fecha_Activo(idUsuario, "CER", fecha, Boolean.FALSE).intValue();
//        numTrabajosHabilitados = servicioCertificadoCarga.numCerPendientesPor_usuario_tipo_estado_fecha_Activo(idUsuario, "CER", fecha, Boolean.TRUE).intValue();
//        numCertificadosRealizados = servicioCertificadoCarga.numCerRealizadosPor_usuario_tipo_estado_fecha(idUsuario, "CER", fecha).intValue();
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

    public void limpiar() {
        dirArchivoComoRecurso = null;
        actaSeleccionada = null;
        listaMarginacion = null;
        rendBtnCrearActa = false;
        urlPDF = "";
        rendBtnDescargarRTF = false;
    }

    public void buscarActa() throws ServicioExcepcion, IOException {
        limpiar();
        if (repertorioNumero2 != null && fechaRep != null && IDtipoLibroSeleccionado != null) {
            try {

                actaSeleccionada = servicioActa.buscarActaPorNumRepFechaRepTipoLibro(repertorioNumero2, fechaRep, IDtipoLibroSeleccionado);
                if (actaSeleccionada == null) {
                    JsfUtil.addErrorMessage("Acta no Encontrada");
                    rendBtnCrearActa = true;
                }
                listaMarginacion = servicioMarginacion.listarPor_NumActa(actaSeleccionada.getActNumero());

                /*--------*/
//                crearPDFconTextoHTML(actaSeleccionada.getActActa());
//                JsfUtil.addSuccessMessage("Acta cargada.");
                tipoLibroSeleccionado = servicioTipoLibro.find(new TipoLibro(), IDtipoLibroSeleccionado);
                //caso especial
                listaPropiedadesHijas = new ArrayList<>();
                if (tipoLibroSeleccionado.getTplCodigo().equals("3")) {
                    Repertorio rep = actaSeleccionada.getRepNumero();
                    listaPropiedadesHijas = servicioPropiedad.listarPropiedadesHijasPH_Por_Repertorio(rep.getRepNumero());
                    for (Propiedad propiedad : listaPropiedadesHijas) {
                        if (propiedad.getPrdVendio().equals("S")) {
                            propiedad.setBolVendioSN(true);
                        } else {
                            propiedad.setBolVendioSN(false);
                        }

                    }
                }

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
                System.out.println(e);
            }
        }
    }

    public void cargarArchivoRTF(String path) {
        //InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/demo/images/boromir.jpg");
        InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(path);
        file = new DefaultStreamedContent(stream, "appication/x-rtf", "documento.rtf");
    }

    public void redirigirCrearActa() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MantenimientoTramite/MantenimientoTramite.xhtml");
    }

    public List<MarginacionDetalle> listarMarginacionesDetalle(Marginacion marginacion) {
        listaMarginacionDetalle = servicioMarginacionDetalle.listarPor_idMarginacion(marginacion.getMrgId());
        return listaMarginacionDetalle;
    }

    public boolean disableVendio(Propiedad propiedad) {
        return propiedad.getPrdVendio().equals("S") && !existeMarginacionPh(propiedad.getPrdMatricula());
    }

    public void cambiarEstadoVendido() {
//        System.out.println(propiedad.getPrdVendio());
        System.out.println("hola");

    }

    public Boolean existeMarginacionPh(String prdMatricula) {
        MarginacionPH margPh = servicioMarginacionPh.obtenerPor_Propiedad_Repertorio(prdMatricula, repertorioParam.getRepNumero());
        return margPh != null;
    }

    public String buscarNombreParroquia() {
        String nombreParroquia = "";
        try {
            Parroquia parroquiaSel = servicioParroquia.find(new Parroquia(), tramiteParam.getTraParId());
            nombreParroquia = parroquiaSel.getParNombre();
        } catch (Exception e) {
            System.out.println(e);
        }
        return nombreParroquia;

    }

    public void preGuardarMarginacion() { //se ejecuta antes de abrir el dialogo para marginar
        nuevaMarginacion = new Marginacion();
        tipoMarginacionSeleccionado = servicioTipoMarginacion.find(new TipoMarginacion(), IDtipoMarginacionSeleccionado);
        nuevaMarginacion.setMrgAlt1(tipoMarginacionSeleccionado.getTmcTextoFijo());

        plnmarginacion = true;
        listaPropiedadesSelec = new ArrayList<>();
        //caso especial
        if (!listaPropiedadesHijas.isEmpty()) {
            bolDisableGuardar = !listaPropiedadesHijas.isEmpty();
        }

        limpiarDatosMarginacionDetalle();
    }

    public void limpiarDatosMarginacionDetalle() {
        porcentajeDerAcn = null;
        txtEdificioBloque = null;
        txtNumOficio = null;
        fechaOficio = null;
        txtNotario = null;
        txtObservacion = null;
    }

    public void seleccionarPropiedades()//metodo que se ejecuta al seleccionar las propiedades en el dialogo para marginar
    {
        bolDisableGuardar = listaPropiedadesSelec.isEmpty();//false
    }

    public void actualizarCampoVendio() {
        for (Propiedad propiedad : listaPropiedadesHijas) {
            {
                if (propiedad.getBolVendioSN()) {
                    propiedad.setPrdVendio("S");
                    servicioPropiedad.edit(propiedad);
                } else {
                    propiedad.setPrdVendio("N");
                    servicioPropiedad.edit(propiedad);
                }
            }
        }
    }

    public void guardarmarginacion() {
        try {

            nuevaMarginacion.setMrgUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            nuevaMarginacion.setMrgFHR(fechaMarginacion);

            nuevaMarginacion.setTmcId(tipoMarginacionSeleccionado);
            nuevaMarginacion.setActNumero(actaSeleccionada);
            nuevaMarginacion.setMrgAlt2(repertorioNumero.toString());
            if (idRepertorio2 != null) {
                nuevaMarginacion.setMrgAlt3(idRepertorio2.toString());
            }

            nuevaMarginacion.setMrgFch(Calendar.getInstance().getTime());
//        tipoLibroSeleccionado = servicioTipoLibro.find(new TipoLibro(), IDtipoLibroSeleccionado);
            servicioMarginacion.create(nuevaMarginacion);
            listaMarginacionNuevos.add(nuevaMarginacion); //lleno lista nuevos
            /*Limpiar campos*/
//        PrimeFaces.current().resetInputs("formMarginacion:pnlMarginacionP");

            tramiteUtil.insertarTramiteAccion(tramiteParam, nuevaMarginacion.getMrgAlt2(),
                    "Marginación de Repertorio ",
                    tramiteParam.getTraEstado(), tramiteParam.getTraEstadoRegistro(), null);

            //caso especial
            if (listaPropiedadesHijas.isEmpty()) {
                actualizarCampoVendio();
                for (Propiedad propiedad : listaPropiedadesHijas) {
                    if (propiedad.getPrdVendio().equals("S")) {
                        if (!existeMarginacionPh(propiedad.getPrdMatricula())) {
                            propiedad.setPrdVendio("S");
                            servicioPropiedad.edit(propiedad);
                            MarginacionPH nuevaMarginacionPh = new MarginacionPH(0L, nuevaMarginacion.getMrgAlt2(), Calendar.getInstance().getTime(), dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            nuevaMarginacionPh.setPrdMatricula(propiedad);
                            nuevaMarginacionPh.setRepNumero(repertorioParam);
                            nuevaMarginacionPh.setMrpEstado("A");
                            servicioMarginacionPh.create(nuevaMarginacionPh);
                        }
                    } else {
                        if (existeMarginacionPh(propiedad.getPrdMatricula())) {
                            MarginacionPH margPh = servicioMarginacionPh.obtenerPor_Propiedad_Repertorio(propiedad.getPrdMatricula(), repertorioParam.getRepNumero());
                            if (margPh != null) {
                                margPh.setMrpEstado("I");
                                servicioMarginacionPh.edit(margPh);

                            }
                        }
                    }

                }
            }
            switch (tipoMarginacionSeleccionado.getTmcCodigo()) {
                case "01": //compraventa y donaciones 

                    if (fechaMarginacion != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(fechaMarginacion));
                    }
                    if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Tipo Propiedad",
                                actaSeleccionada.getPrdMatricula().getPrdDescripcion2() + " - "
                                + actaSeleccionada.getPrdMatricula().getPrdDescripcion1());
                    }
                    if (fechaRep != null && repertorioNumero2 != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        guardarMarginacionDetalle(nuevaMarginacion, "Vendido", simpleDateFormat.format(fechaRep) + " - " + repertorioNumero2);
                    }
                    if (porcentajeDerAcn != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "% Derechos y acciones", porcentajeDerAcn.toString());
                    }

                    break;
                case "02": //hipoteca
                    if (fechaMarginacion != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(fechaMarginacion));
                    }
                    if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Tipo Propiedad",
                                actaSeleccionada.getPrdMatricula().getPrdDescripcion2() + " - "
                                + actaSeleccionada.getPrdMatricula().getPrdDescripcion1());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Hipoteca", actaSeleccionada.getPrdMatricula().getPrdDescripcion2());
                    }
                    if (porcentajeDerAcn != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "% Derechos y acciones", porcentajeDerAcn.toString());
                    }
                    break;
                case "03": //declaratoria y modificatoria
                    if (fechaMarginacion != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(fechaMarginacion));
                    }
                    if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (txtEdificioBloque != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Edificio o Bloque", txtEdificioBloque);
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Vendido", actaSeleccionada.getPrdMatricula().getPrdDescripcion2());
                    }
                    if (porcentajeDerAcn != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "% Derechos y acciones", porcentajeDerAcn.toString());
                    }
                    break;
                case "04": //cancelacion de hipoteca
                    if (fechaMarginacion != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(fechaMarginacion));
                    }
                     if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (txtNumOficio != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Num oficio", txtNumOficio);
                    }
                    if (fechaOficio != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Oficio", fechaOficio.toString());
                    }
                    if (txtNotario != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Notario", txtNotario);

                    }
                    if (txtObservacion != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Observación", txtObservacion);
                    }
                    if (fechaRep != null && repertorioNumero2 != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        guardarMarginacionDetalle(nuevaMarginacion, "Cancelación de Hipoteca", simpleDateFormat.format(fechaRep) + " - " + repertorioNumero2);
                    }
                    break;
                case "05": //aclaratoria posesion efectiva
                    
                    if (fechaMarginacion != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(fechaMarginacion));
                    }
                     if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Tipo Propiedad",
                                actaSeleccionada.getPrdMatricula().getPrdDescripcion2() + " - "
                                + actaSeleccionada.getPrdMatricula().getPrdDescripcion1());
                    }
                     if (fechaRep != null && repertorioNumero2 != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        guardarMarginacionDetalle(nuevaMarginacion, "Aclaratoria de Posesión Efectiva", simpleDateFormat.format(fechaRep) + " - " + repertorioNumero2);
                    }
                    if (txtObservacion != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Observaciones", txtObservacion);
                    }
                    break;
                case "06": //sentencias varias
                    if (fechaMarginacion != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(fechaMarginacion));
                    }
                     if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Tipo Propiedad",
                                actaSeleccionada.getPrdMatricula().getPrdDescripcion2() + " - "
                                + actaSeleccionada.getPrdMatricula().getPrdDescripcion1());
                    }
                     if (fechaRep != null && repertorioNumero2 != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        guardarMarginacionDetalle(nuevaMarginacion, "Aclaratoria de Posesión Efectiva", simpleDateFormat.format(fechaRep) + " - " + repertorioNumero2);
                    }
                    if (txtObservacion != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Observaciones", txtObservacion);
                    }
                    break;
                default:
                    break;
            }

            actualizarEstProcRepUsu();
            contabilizarCertificados();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actas marginadas correctamente", ""));
        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al marginar", ""));

        }

//        try {
//            crearPDFconTextoHTML(actaSeleccionada.getActActa());
//        } catch (Exception e) {
//            e.printStackTrace();
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al crear PDF", ""));
//        }
    }

    public void guardarMarginacionDetalle(Marginacion mrg, String descripcion, String valor) {
        MarginacionDetalle nuevaMarginacionDetalle = new MarginacionDetalle();
        nuevaMarginacionDetalle.setMrgId(mrg);
        nuevaMarginacionDetalle.setMgdDescripcion(descripcion);
        nuevaMarginacionDetalle.setMgdValor(valor);
        nuevaMarginacionDetalle.setMgdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        nuevaMarginacionDetalle.setMgdFHR(Calendar.getInstance().getTime());
        servicioMarginacionDetalle.edit(nuevaMarginacionDetalle);

    }

    public boolean verificarMostrarComboMrg() throws ServicioExcepcion {

        Boolean mostrarComboTipoTram1 = false;
        boolean tipo5 = false;
        boolean tipo8 = false;

        try {
            listaRepSelMrg = servicioRepertorioUsuario.
                    listarPor_Tramite_Tipo_Estado_Usr("MRG", tramiteParam.getTraNumero(), "A", dataManagerSesion.getUsuarioLogeado().getUsuId());

            for (RepertorioUsuario repUsu : listaRepSelMrg) {
                String tipoTram = repUsu.getRepNumero().getRepTtrDescripcion().trim();
                TipoTramite tipoTramiteSelec = servicioTipoTramite.buscarPorDescripcion(tipoTram);

                if (tipoTramiteSelec != null && (tipoTramiteSelec.getTtrCodigo() != null)) {
                    switch (tipoTramiteSelec.getTtrCodigo()) {
                        case 5:
                            tipo5 = true;
                            break;
                        case 8:
                            tipo8 = true;
                            break;
                    }
                }

            }
            TipoTramite tipoTramiteSelecParam = servicioTipoTramite.buscarPorDescripcion(repertorioParam.getRepTtrDescripcion().trim());
            if (tipoTramiteSelecParam.getTtrCodigo() != null) {
                if (tipo5 && tipo8 && (tipoTramiteSelecParam.getTtrCodigo() == 8)) {
                    mostrarComboTipoTram1 = true;
                } else {
                    mostrarComboTipoTram1 = false;
                }
            } else {
                mostrarComboTipoTram1 = false;
            }

//            mostrarComboTipoTram1 = tipo5 && tipo8;
            for (Iterator<RepertorioUsuario> iterator = listaRepSelMrg.iterator(); iterator.hasNext();) {
                RepertorioUsuario next = iterator.next();
                String tipoTram = next.getRepNumero().getRepTtrDescripcion().trim();
                TipoTramite tipoTramiteSelec = servicioTipoTramite.buscarPorDescripcion(tipoTram);
                if (tipoTramiteSelec.getTplId().getTplPropietario() == null
                        || !tipoTramiteSelec.getTplId().getTplPropietario()) {
                    iterator.remove();
                }
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ocurrió un error");
            e.printStackTrace(System.out);
        }

        return mostrarComboTipoTram1;
    }

    public void actualizarEstProcRepUsu() throws Exception {

        RepertorioUsuario repertorioUsuarioSeleccionado = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
                repertorioParam.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "MRG");
        repertorioUsuarioSeleccionado.setRpuEstadoProceso("TERMINADO");
        servicioRepertorioUsuario.edit(repertorioUsuarioSeleccionado);

    }

    public void cambiarEstado() throws ServicioExcepcion {

        Boolean estanTodosRepertoriosMarginados = servicioMarginacion.estanTodosRepertoriosMarginados(
                tramiteParam.getTraNumero());
        boolean estanTodosEnEstadoTerminado = false;
        listaRepUsu = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(IDusuarioLogeado, "MRG");
        for (RepertorioUsuario repertorioUsuario : listaRepUsu) {
            if (repertorioUsuario.getRepNumero().getTraNumero().equals(tramiteParam)) {
                if (repertorioUsuario.getRpuEstadoProceso() != null && repertorioUsuario.getRpuEstadoProceso().equals("TERMINADO")) {
                    estanTodosEnEstadoTerminado = true;
                } else {
                    estanTodosEnEstadoTerminado = false;
                    break;
                }
            }
        }
        if (estanTodosRepertoriosMarginados && estanTodosEnEstadoTerminado) {

            for (RepertorioUsuario repertorioUsuario : listaRepUsu) {
                if (repertorioUsuario.getRepNumero().getTraNumero().equals(tramiteParam)) {
                    repertorioUsuario.setRpuEstado("I");
                    servicioRepertorioUsuario.edit(repertorioUsuario);
                }
            }

            try {
                tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(repertorioParam.getTraNumero().getTraNumero(), repertorioParam.getRepTtrDescripcion());
                tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
                if (tipoTramite.getTtrCodigo() != null) {
                    Usuario usuarioAsignado;
                    switch (tipoTramite.getTtrCodigo()) {
                        case 7:
                        case 9:
                        case 10:
                            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(repertorioParam.getTraNumero().getTraNumero(), "MPH"));
                            if (tramiteAccion == null) {
                                //codigo CargaLaboral
                                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MPH", "PHMATRICULACION", 1);
                                cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSelec.getUsuId().getUsuId(), repertorioUsuarioSelec.getRpuTipo());
                            } else {
                                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                                int auxCarga = cargaAdd.getCarAsignado() + 1;
                                cargaAdd.setCarAsignado((short) auxCarga);
                                servicioCargaLaboral.edit(cargaAdd);
                            }
                            for (RepertorioUsuario repUsu : listaRepUsu) {
                                if (tramiteParam.equals(
                                        repUsu.getRepNumero().getTraNumero())) {
                                    RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
                                    nuevoRepUsu = repUsu;
                                    nuevoRepUsu.setRpuTipo("MPH");
                                    nuevoRepUsu.setUsuId(usuarioAsignado);
                                    nuevoRepUsu.setRpuEstado("A");
                                    nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
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

                            break;
                        default:
                            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(repertorioParam.getTraNumero().getTraNumero(), "MAT"));
                            if (tramiteAccion == null) {
                                //codigo CargaLaboral
                                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MAT", "Matriculacion", 1);
                                cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSelec.getUsuId().getUsuId(), repertorioUsuarioSelec.getRpuTipo());
                            } else {
                                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                                int auxCarga = cargaAdd.getCarAsignado() + 1;
                                cargaAdd.setCarAsignado((short) auxCarga);
                                servicioCargaLaboral.edit(cargaAdd);
                            }
                            for (RepertorioUsuario repUsu : listaRepUsu) {
                                if (tramiteParam.equals(
                                        repUsu.getRepNumero().getTraNumero())) {
                                    RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
                                    nuevoRepUsu = repUsu;
                                    nuevoRepUsu.setRpuTipo("MAT");
                                    nuevoRepUsu.setUsuId(usuarioAsignado);
                                    nuevoRepUsu.setRpuEstado("A");
                                    nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
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

                            break;
                    }

                } else {
                    Usuario usuarioAsignado = new Usuario();
                    setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(repertorioParam.getTraNumero().getTraNumero(), "MAT"));
                    if (tramiteAccion == null) {
                        //codigo CargaLaboral
                        usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MAT", "Matriculacion", 1);
                        cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSelec.getUsuId().getUsuId(), repertorioUsuarioSelec.getRpuTipo());
                    } else {
                        usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                        CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                        int auxCarga = cargaAdd.getCarAsignado() + 1;
                        cargaAdd.setCarAsignado((short) auxCarga);
                        servicioCargaLaboral.edit(cargaAdd);
                    }
                    for (RepertorioUsuario repUsu : listaRepUsu) {
                        if (tramiteParam.equals(
                                repUsu.getRepNumero().getTraNumero())) {
                            RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
                            nuevoRepUsu = repUsu;
                            nuevoRepUsu.setRpuTipo("MAT");
                            nuevoRepUsu.setUsuId(usuarioAsignado);
                            nuevoRepUsu.setRpuEstado("A");
                            nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
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

                }

            } catch (Exception e) {
                System.out.println(e);
                JsfUtil.addErrorMessage("Error al generar nuevos repertorios usuario con estado 'MAT'");
            }
            try {
                tramiteParam.setTraEstado("MAT");
                servicioTramite.edit(tramiteParam);

                tramiteUtil.insertarTramiteAccion(tramiteParam, tramiteParam.getTraNumero().toString(),
                        "Actualización del estado de Tramite a 'MAT'",
                        "MRG", tramiteParam.getTraEstadoRegistro(), null);

                JsfUtil.addSuccessMessage("Tramite actualizado");
            } catch (Exception e) {
                System.out.println(e);
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
//                    +servicioMarginacion.listarRepertoriosNoMarginados(tramiteParam.getTraNumero()
        }

    }

    public void redireccionarPaginaSeleccion() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/Marginacion/MarginacionSelectRep.xhtml");

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
