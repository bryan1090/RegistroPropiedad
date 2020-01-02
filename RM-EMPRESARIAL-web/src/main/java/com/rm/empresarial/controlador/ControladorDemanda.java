/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.FechasUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.ActaRazonDao;
import com.rm.empresarial.dao.CantonDao;
import com.rm.empresarial.dao.CargaLaboralDao;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.GravamenDetalleDao;
import com.rm.empresarial.dao.InscripcionDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.PropietarioDao;
import com.rm.empresarial.dao.RazonNuevaDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.TipoActaDao;
import com.rm.empresarial.dao.TipoComparecienteDao;
import com.rm.empresarial.dao.TipoLibroDao;
import com.rm.empresarial.dao.TipoTramiteComparecienteDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.dao.TramiteValorDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Canton;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.GravamenDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Razon;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoActa;
import com.rm.empresarial.modelo.TipoCompareciente;
import com.rm.empresarial.modelo.TipoJuicio;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.ParroquiaServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.PropiedadDetalleServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoJuicioServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Prueba
 */
@Named(value = "controladorDemanda")
@ViewScoped
public class ControladorDemanda implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Inject
    private PersonaUtil personaUtil;

    @Getter
    @Setter
    private List<Repertorio> listaRepertorioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramiteDetalle = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetalleCompS = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetalleCompN = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoCompareciente> listaTipoCompareciente = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoTramiteCompareciente> listaTipoTramiteCompareciente = new ArrayList<>();

    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropiedadDetalle = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor = new ArrayList<>();

    @Getter
    @Setter
    private List<Repertorio> preListaRepertorioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropiedad = new ArrayList<>();

    @Getter
    @Setter
    List<Propiedad> listPropiedades_S = new ArrayList<>();

    @Getter
    @Setter
    private List<Propietario> listaPropiedadPorMatricula = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesSeleccionadas = new ArrayList<>();

    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropDetSeleccionadas = new ArrayList<>();

    @Getter
    @Setter
    List<GravamenDetalle> listaGravamenDetalle = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoJuicio> listaTipoJuicio = new ArrayList<>();

    @Getter
    @Setter
    private List<Parroquia> listaParroquia = new ArrayList<>();

    @Getter
    @Setter
    private Date fecha;

    @Getter
    @Setter
    private String cabeceraActa;

    @Getter
    @Setter
    private String cuerpoActa;

    @Getter
    @Setter
    private String actaDocumento;

    @Getter
    @Setter
    private Boolean renderedTabRazon;

    @Getter
    @Setter
    private Boolean renderedTabActa;

    @Getter
    @Setter
    private Boolean renderedTabDatosGen;

    @Getter
    @Setter
    private Boolean renderedTabView;

    @Getter
    @Setter
    private Tramite tramiteR;

    @Getter
    @Setter
    private String txtRazon;

    @Getter
    @Setter
    private Long idGenerado;

    @Getter
    @Setter
    private int activeIndex;

    @Getter
    @Setter
    private Long idRazonGenerado;

    @Getter
    @Setter
    BigInteger numInscripcion;

    @Getter
    @Setter
    private Secuencia secuencia;

    @Getter
    @Setter
    private Boolean propiedadSeleccionada;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private String tipoComparecienteId;

    @Getter
    @Setter
    private String nombreCompareciente;

    @Getter
    @Setter
    private String apellidoPaternoCompareciente;

    @Getter
    @Setter
    private Long idPersona;

    @Getter
    @Setter
    private String apellidoMaternoCompareciente;

    @Getter
    @Setter
    private String numeroTramite;

    @Getter
    @Setter
    private String numeroRepertorio;

    @Getter
    @Setter
    private String descripcionContrato;

    @Getter
    @Setter
    private Boolean deshabilitarBoton;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalle;

    @Getter
    @Setter
    private Repertorio repertorio;

    @Getter
    @Setter
    private TipoTramite tipoTramite;

    @Getter
    @Setter
    private Boolean renderedPanel;

    @Getter
    @Setter
    private Boolean todosEstadoProcTerminado = false;

    @Getter
    @Setter
    private Boolean renderedPanelTab;

    @Getter
    @Setter
    private Long tipoJuicioId;

    @Getter
    @Setter
    private String descripcionParroquia;

    @Getter
    @Setter
    private String nombreParroquia;

    @Getter
    @Setter
    private Date fechaIngreso;

    @Getter
    @Setter
    private Date fechaSentencia;

    @Getter
    @Setter
    private Date fechaDe;

    @Getter
    @Setter
    private Tramite tramite;

    @Getter
    @Setter
    private Acta acta;

    @Getter
    @Setter
    private Acta actaEncontrada;

    @Getter
    @Setter
    private TipoLibro tipoLibro;

    @Getter
    @Setter
    private Acta actaActualizar;

    @Getter
    @Setter
    private Propiedad propiedadMatricula;

    @Getter
    @Setter
    private Short numeroJuicio;

    @Getter
    @Setter
    private int numeroMatriculaProp;

    @Getter
    @Setter
    private Short juzgado;

    @Getter
    @Setter
    private Short notaria;

    @Getter
    @Setter
    private Long numeroOficio;

    @Getter
    @Setter
    private String observacion;

    @Getter
    @Setter
    private FechasUtil fechasUtil;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private String parroquias;

    @Getter
    @Setter
    private Usuario usuarioAsignado;

    @Getter
    @Setter
    private Boolean deshabilitarTabDatosGen;

    @Getter
    @Setter
    private Boolean renderedBtnSuspension;

    @Getter
    @Setter
    private Boolean deshabilitarTabActa;

    @Getter
    @Setter
    private Boolean deshabilitarTabRazon;

    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    @Getter
    @Setter
    private Institucion institucion = new Institucion();
    @Getter
    @Setter
    private Canton canton = new Canton();

    @EJB
    private CantonDao cantonDao;

    @EJB
    private RepertorioDao repertorioDao;

    @EJB
    private GravamenDetalleDao gravamenDetalleDao;

    @EJB
    private InscripcionDao inscripcionDao;

    @EJB
    private TipoTramiteDao tipoTramiteDao;

    @EJB
    private TramiteDetalleDao tramiteDetalleDao;

    @EJB
    private TipoActaDao tipoActaDao;

    @EJB
    private TipoTramiteComparecienteDao tipoTramiteComparecienteDao;

    @EJB
    private TipoComparecienteDao tipoComparecienteDao;

    @EJB
    private PropiedadDao propieadadDao;

    @EJB
    private TramiteValorDao tramiteValorDao;

    @EJB
    private TramiteDao tramiteDao;

    @EJB
    private TramiteValorServicio servicioTramiteValor;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    private TipoJuicioServicio tipoJuicioServicio;

    @EJB
    private ParroquiaServicio parroquiaServicio;

    @EJB
    private TramiteServicio tramiteServicio;

    @EJB
    private PersonaServicio personaServicio;

    @EJB
    private ActaServicio actaServicio;

    @EJB
    private PropiedadDetalleServicio propiedadDetalleServicio;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @EJB
    private TipoLibroDao tipoLibroDao;

    @EJB
    private ActaRazonDao actaRazonDao;

    @EJB
    private CargaLaboralDao cargaLaboralDao;

    @EJB
    private GravamenDao gravamenDao;

    @EJB
    private PropietarioDao propietarioDao;

    @EJB
    private RepertorioUsuarioServicio repertorioUsuarioServicio;

    @EJB
    private RazonNuevaDao razonNuevaDao;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private CargaLaboralServicio servicioCargaLaboral;

    @EJB
    private UsuarioServicio servicioUsuario;

    @Inject
    private TransformadorNumerosLetrasUtil transformadorNumerosLetrasUtil;

    @Inject
    private TramiteUtil tramiteUtil;

    @Inject
    private PdfTempUtil pdfTempUtil;
    @Getter
    @Setter
    private boolean bolAgregarImagenEspacioEnBlancoActa;

    @Getter
    @Setter
    private boolean bolAgregarImagenEspacioEnBlancoRazon;

    @Getter
    @Setter
    private Boolean deshabilitarBotonGuardar;

    private String urlImagenEspacioEnBlanco;

    private int numSaltosLinea;

    private int numSaltosLineaRazon;

    @Getter
    @Setter
    private String urlActaPdf;

    @Getter
    @Setter
    private String urlRazonPdf;

    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;

    public ControladorDemanda() {

        setFecha(Calendar.getInstance().getTime());
        propiedadSeleccionada = false;
        tramiteDetalle = new TramiteDetalle();
        tipoTramite = new TipoTramite();
        repertorio = new Repertorio();
        tramite = new Tramite();
        secuencia = new Secuencia();
        propiedadMatricula = new Propiedad();
        acta = new Acta();
        actaActualizar = new Acta();
        tramiteSeleccionado = new Tramite();
        usuarioAsignado = new Usuario();
        tipoLibro = new TipoLibro();
        fechasUtil = new FechasUtil();
        tramiteAccion = new TramiteAccion();
        deshabilitarBoton = true;
        renderedPanel = false;
        renderedPanelTab = false;
        deshabilitarTabActa = true;
        deshabilitarTabDatosGen = true;
        deshabilitarTabRazon = true;
        renderedBtnSuspension = false;
        renderedTabRazon = false;
        renderedTabActa = false;
        renderedTabDatosGen = false;
        renderedTabView = false;

    }

    @PostConstruct
    public void postConstructor() {
        inicializar();

    }

    public void inicializar() {
        try {
            llenarDatos();
            cargarListaProvinciaTipoJucio();
            deshabilitarBotonGuardar = true;
            urlImagenEspacioEnBlanco = servicioConfigDetalle.buscarPorConfig("IMAGENBLANCO").getConfigDetalleTexto();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void llenarDatos() throws ServicioExcepcion, ParseException {
        listaPropiedadPorMatricula.clear();
        renderedBtnSuspension = false;
        preListaRepertorioFecha.clear();
        listaTramiteDetalle.clear();
        deshabilitarBoton = true;
        activeIndex = 0;
        deshabilitarTabActa = true;
        deshabilitarTabDatosGen = true;
        deshabilitarTabRazon = true;
        renderedPanel = true;
        renderedPanelTab = false;
        limpiarCampos();
        deshabilitarBoton = true;
        renderedTabView = false;

        setPreListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId(), "IND"));
        listaRepertorioFecha.clear();
        for (Repertorio repertorio1 : preListaRepertorioFecha) {

            Tramite tramit = new Tramite();
            tramit = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());
            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramit.getTraNumero().longValue()) && !tramit.getTraEstadoRegistro().trim().equals("RZ")) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorio1.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "IND");
                if (repUsu.getRpuEstado().equals("A")) {
                    if (repUsu.getRpuEstadoProceso() != null && !repUsu.getRpuEstadoProceso().equals("")) {
                        repertorio1.setEstadoProceso(repUsu.getRpuEstadoProceso());
                    } else {
                        repertorio1.setEstadoProceso("EN PROCESO");
                    }

                }
                listaRepertorioFecha.add(repertorio1);
            }

        }

        //setListaRepertorioFecha(inscripcionDao.ListarRepertorioINJPorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId()));
    }

    public void llenarDatosActualizar() throws ServicioExcepcion, ParseException {

        listaRepertorioFecha.clear();
        preListaRepertorioFecha.clear();
        setPreListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId(), "IND"));
        setPreListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId(), "IND"));

        for (Repertorio repertorio1 : preListaRepertorioFecha) {

            Tramite tramit = new Tramite();
            tramit = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());
            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramit.getTraNumero().longValue()) && !tramit.getTraEstadoRegistro().trim().equals("RZ")) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorio1.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "IND");
                if (repUsu.getRpuEstado().equals("A")) {
                    if (repUsu.getRpuEstadoProceso() != null && !repUsu.getRpuEstadoProceso().equals("")) {
                        repertorio1.setEstadoProceso(repUsu.getRpuEstadoProceso());
                    } else {
                        repertorio1.setEstadoProceso("EN PROCESO");
                    }

                }
                listaRepertorioFecha.add(repertorio1);
            }

        }

    }

    public void onRowSelect(SelectEvent event) throws ServicioExcepcion, ParseException {

        limpiarCampos();
        numeroRepertorio = "" + (((Repertorio) event.getObject()).getRepNumero()).toString() + "";
        numeroTramite = "" + (((Repertorio) event.getObject()).getTraNumero().getTraNumero()).toString() + "";
        descripcionContrato = "" + (((Repertorio) event.getObject()).getRepTtrDescripcion()) + "";
        repertorio = repertorioDao.encontrarRepertorioPorId(numeroRepertorio);

        deshabilitarTabActa = true;
        deshabilitarTabDatosGen = true;
        deshabilitarTabRazon = true;
        renderedPanel = true;
        renderedPanelTab = false;

        cargarTablaComparecientes();
        listarPropiedadPorMatricula();
        deshabilitarBoton = false;
        cargarDatosGenerales();
        mostrarPropiedades();
        renderedBtnSuspension = true;
        listaPropDetSeleccionadas.clear();
        renderedTabView = true;

        numSaltosLinea = 0;
        numSaltosLineaRazon = 0;
        bolAgregarImagenEspacioEnBlancoActa = false;
        bolAgregarImagenEspacioEnBlancoRazon = false;
        deshabilitarBotonGuardar = true;

        cabeceraActa = "";
        cuerpoActa = "";

    }

    public void limpiarCampos() {
        numeroRepertorio = "";
        numeroTramite = "";
        descripcionContrato = "";
        numeroJuicio = 0;
        notaria = 0;
        juzgado = 0;
        descripcionParroquia = "";
        nombreParroquia = "";
        numeroOficio = new Long(0);
        observacion = "";
        fechaIngreso = null;
        fechaDe = null;
        fechaSentencia = null;
        txtRazon = "";
        cabeceraActa = "";
        cuerpoActa = "";
        listaPropiedadPorMatricula.clear();
        listaTramiteDetalle.clear();
        listPropiedades_S.clear();

    }

    public void cargarTablaComparecientes() throws ServicioExcepcion {
        try {
            listaTramiteDetalle.clear();
            listaTipoTramiteCompareciente.clear();
            Repertorio repert = new Repertorio();
            repert = repertorioDao.encontrarRepertorioPorId(numeroRepertorio);

            listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                    repert.getTraNumero().getTraNumero(), repert.getRepTtrId(), Long.valueOf(numeroRepertorio));

            listaTipoCompareciente = tipoTramiteComparecienteDao.listarTipoComparecientePorTipoTramite(Long.valueOf(repert.getRepTtrId()));

            listaTramDetalleCompN.clear();
            listaTramDetalleCompS.clear();
            for (TramiteDetalle tramDetalle : listaTramiteDetalle) {
                TipoTramiteCompareciente tipoTramCompareciente = new TipoTramiteCompareciente();
                tipoTramCompareciente = tipoTramiteComparecienteDao.buscarPorId(tramDetalle.getTtcId().getTtcId());
                if (tipoTramCompareciente.getTtcPropietario().equals("N")) {
                    listaTramDetalleCompN.add(tramDetalle);
                } else if (tipoTramCompareciente.getTtcPropietario().equals("S")) {
                    listaTramDetalleCompS.add(tramDetalle);

                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void buscarPersona() throws IOException, ServicioExcepcion {

        if (getTramitesControladorBb().getIdentificacion() != null && !getTramitesControladorBb().getIdentificacion().equals("")) {
            //getTramitesControladorBb().setIdentificacion(identificacion);

            try {

                getTramitesControladorBb().setPersona(personaUtil.obtenerDatosPersona(getTramitesControladorBb().getIdentificacion()));

                if (getTramitesControladorBb().getPersona().getPerIdentificacion() != null) {
                    nombrePersona();
                } else {
                    getTramitesControladorBb().setIdentificacion(" ");
                    getTramitesControladorBb().setNombrePersona(" ");

                }

            } catch (Exception ex) {
                Logger.getLogger(ControladorInscripcion.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            addErrorMessage("Ingrese Cédula/RUC/Pas");
        }

    }

    public void nombrePersona() {
        getTramitesControladorBb().setNombrePersona(getTramitesControladorBb().getPersona().getPerApellidoPaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerApellidoMaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerNombre().trim());
        String Nombre = getTramitesControladorBb().getNombrePersona();
        getTramitesControladorBb().setNombrePersona(Nombre.replace("null", ""));
        getTramitesControladorBb().setEstado(Boolean.TRUE);

    }

    public void guardarTramiteDetalle() throws ServicioExcepcion, ParseException {
        try {
            TipoCompareciente tipoComparec = new TipoCompareciente();
            tipoComparec = tipoComparecienteDao.encontrarTipoComparecientePorId(tipoComparecienteId);
            TipoTramiteCompareciente tipoTramCompareciente = new TipoTramiteCompareciente();
            tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numeroTramite), descripcionContrato);
            tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
            tipoTramCompareciente = tipoTramiteComparecienteDao.buscarPorIdTramitePorTipoCompareciente(
                    tipoTramite.getTtrId(), tipoComparec.getTpcId());
            short idLibro = tipoTramite.getTplId().getTplId().shortValue();
            TramiteDetalle tramDetalle = new TramiteDetalle();
            tramDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(repertorio.getTraNumero().getTraNumero(), tipoTramite.getTtrDescripcion());

            TramiteDetalle tramiteDet = new TramiteDetalle();
            getTramitesControladorBb().setPersonaConyugue(getTramitesControladorBb().getPersona().getPerIdConyuge());
            tramiteDet.setPerId(getTramitesControladorBb().getPersona());
            tramiteDet.setTdtConyuguePerId(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerId());
            tramiteDet.setTdtConyuguePerNombre(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerNombre() + " " + getTramitesControladorBb().getPersona().getPerIdConyuge().getPerApellidoPaterno());
            tramiteDet.setTdtConyuguePerTipoIden(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerTipoIdentificacion());
            tramiteDet.setTdtEstado("A");
            tramiteDet.setTdtFHR(Calendar.getInstance());
            tramiteDet.setTdtFechaRegistro(Calendar.getInstance());
            tramiteDet.setTdtNumeroRepertorio(Integer.valueOf(numeroRepertorio));
            tramiteDet.setTdtPerApellidoPaterno(getTramitesControladorBb().getPersona().getPerApellidoPaterno());
            tramiteDet.setTdtPerApellidoMaterno(getTramitesControladorBb().getPersona().getPerApellidoMaterno());
            tramiteDet.setTdtPerIdentificacion(getTramitesControladorBb().getPersona().getPerIdentificacion());
            tramiteDet.setTdtPerNombre(getTramitesControladorBb().getPersona().getPerNombre());
            tramiteDet.setTdtPerTipoContribuyente(" ");
            tramiteDet.setTdtPerTipoIdentificacion(getTramitesControladorBb().getPersona().getPerTipoIdentificacion());
            tramiteDet.setTdtTpcCodigo(tipoComparec.getTpcCodigo());
            tramiteDet.setTdtTpcDescripcion(tipoComparec.getTpcDescripcion());
            tramiteDet.setTdtTpcId(tipoComparec.getTpcId());
            tramiteDet.setTdtTplId(idLibro);
            tramiteDet.setTdtTtrDescripcion(tipoTramite.getTtrDescripcion());
            tramiteDet.setTdtTtrId(tipoTramite.getTtrId());
            tramiteDet.setTdtUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            tramiteDet.setTraNumero(repertorio.getTraNumero());
            tramiteDet.setTtcId(tipoTramCompareciente);
            tramiteDet.setTdtTplDescripcion(tipoTramite.getTtrDescripcion());
            tramiteDet.setTdtParId(tramDetalle.getTdtParId());
            tramiteDet.setTdtParNombre(tramDetalle.getTdtParNombre());
            tramiteDet.setTdtCatastro(tramDetalle.getTdtCatastro());
            tramiteDet.setTdtPredio(tramDetalle.getTdtPredio());
            tramiteDetalleDao.guardarDetalleTramite(tramiteDet);

            cargarTablaComparecientes();
            mostrarPropiedades();
            getTramitesControladorBb().setIdentificacion("");
            getTramitesControladorBb().setNombrePersona("");
            System.out.println("tramite detalle guardado");

        } catch (Exception e) {
            System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.guardarTramiteDetalle()");
            System.err.println(e);
        }

    }

    public void listarPropiedadPorMatricula() throws ServicioExcepcion {
        try {
            tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numeroTramite), descripcionContrato);
            tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());

            listaPropiedadPorMatricula.clear();

            List<Propiedad> listaPropiedadMatric = new ArrayList<>();
            listaPropiedadMatric = propieadadDao.listarPropiedadPorMatriculaPorTramitePorPredioPorCatastro(Long.valueOf(numeroTramite), tipoTramite.getTtrId());

            List<Propietario> listaPropietar = new ArrayList<>();
            for (Propiedad propiedad : listaPropiedadMatric) {
                listaPropietar = propietarioDao.buscarPor_PropiedadMatricula_Estado(propiedad.getPrdMatricula());
                listaPropiedadPorMatricula.addAll(listaPropietar);
            }

            //setear campo @Transient "CantidadPropiedadDetalle" de Propietario
            List<PropiedadDetalle> propDetalle = new ArrayList<>();
            for (Propietario propietario : listaPropiedadPorMatricula) {
                propDetalle = propiedadDetalleServicio.listar_Por_Propiedad_Tipo_Estado(propietario.getPrdMatricula().getPrdMatricula(), "GDA");
                if (!propDetalle.isEmpty()) {
                    propietario.setCantidadPropiedadDetalle(propDetalle.size());
                }

            }

            //setear campo @Transient "existe" de Propietario
            for (Propietario propietario : listaPropiedadPorMatricula) {
                for (TramiteDetalle tramiteDetalle1 : listaTramiteDetalle) {
                    if (propietario.getPerId().getPerId().longValue() == tramiteDetalle1.getPerId().getPerId().longValue()) {
                        propietario.setExiste(1);
                    } else {
                        propietario.setExiste(0);
                    }
                }
                System.out.println("per " + propietario.getPerId().getPerId() + " exsite " + propietario.getExiste());
            }

            mostrarParroquias();

            for (Propiedad prop : listaPropiedadMatric) {
                propiedadMatricula = propieadadDao.encontrarPropiedadPorId(prop.getPrdMatricula().toString());
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void mostrarParroquias() {
        parroquias = "";
        int count = 0;

        Set< String> listaParroquiasUnicas = new HashSet< String>();
        for (Propietario propietario : listaPropiedadPorMatricula) {

            if (!propietario.getPrdMatricula().getPrdTdtParNombre().equals("0")) {

                listaParroquiasUnicas.add(propietario.getPrdMatricula().getPrdTdtParNombre());

            }

        }
        for (String uniqueItem : listaParroquiasUnicas) {
            count++;
            if (count < listaParroquiasUnicas.size()) {
                parroquias = parroquias + uniqueItem + ", ";
            } else {
                parroquias = parroquias + uniqueItem + " ";
            }

        }
        nombreParroquia = parroquias;

    }

    public void eliminarCompareciente(Long id) throws ServicioExcepcion, ParseException {
        try {
            TramiteDetalle tramiteDet = new TramiteDetalle();
            tramiteDet = tramiteDetalleDao.buscarPorId(Long.valueOf(id));
            tramiteDet.setTdtEstado("I");
            tramiteDetalleDao.edit(tramiteDet);
            cargarTablaComparecientes();

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public void procesoTerminar() {
        deshabilitarTabDatosGen = false;
        deshabilitarTabActa = true;
        deshabilitarTabRazon = true;
        activeIndex = 0;
        renderedTabRazon = false;
        renderedTabActa = false;
        renderedTabDatosGen = true;
        renderedTabView = true;
        renderedPanelTab = true;
        renderedPanel = false;

    }

    public void cargarListaProvinciaTipoJucio() throws ServicioExcepcion {

        listaTipoJuicio = tipoJuicioServicio.listarTodo();
        listaParroquia = parroquiaServicio.listarTodo();

    }

    public void cargarDatosGenerales() throws ServicioExcepcion {

        tramite = tramiteServicio.buscarTramitePorNumero(Long.valueOf(numeroTramite));
        numeroJuicio = tramite.getTraJucio();
        juzgado = tramite.getTraNumeroJusgado();
        notaria = tramite.getTraNotaria();
        fechaIngreso = repertorio.getRepFHR();

        buscarActa();

        if (actaEncontrada != null) {
            parroquias = actaEncontrada.getActParroquia();
            descripcionParroquia = actaEncontrada.getActDescripcion();
            fechaDe = actaEncontrada.getActFechaDe();
            fechaSentencia = actaEncontrada.getActFechaSentencia();
            numeroJuicio = actaEncontrada.getActNumeroJucio().shortValue();
            juzgado = actaEncontrada.getActJuzgado().shortValue();
            notaria = actaEncontrada.getActNotaria().shortValue();
            numeroOficio = actaEncontrada.getActNumeroOficio().longValue();
            observacion = actaEncontrada.getActObservacion();

        }

    }

    public void guardarActa() throws ServicioExcepcion, ParseException {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Acta actaNueva = new Acta();
            buscarActa();
            idGenerado = new Long(0);
            if (actaEncontrada != null) {
                acta();
                idGenerado = actaEncontrada.getActNumero();
                actaNueva.setActActa(actaDocumento);
                actaNueva.setActActaPdf(actaDocumento);
            } else {
                secuenciaControlador.generarSecuencia("ACT");
                //obtengo el siguiente valor de la secuencia
                setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuencia = getSecuencia().getSecActual();
                getSecuencia().setSecActual(auxSecuencia + 1);
                Long sec = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
                idGenerado = new Long(sec);
                secuenciaServicio.guardar(getSecuencia());
                acta();
                actaNueva.setActActa(actaDocumento);
                actaNueva.setActActaPdf(actaDocumento);

            }

            Long idTipoActa = actaServicio.buscarTipoActa();
            Date today = new Date();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date todayWithZeroTime = formatter.parse(formatter.format(today));
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            int anio = calendar.get(Calendar.YEAR);
            //numInscripcion = actaServicio.buscarPorAnioYTipoLibroId(anio, tipoTramite.getTplId().getTplId());

            Propiedad propiedad = new Propiedad();
            if (propiedadMatricula.getPrdMatricula() == null) {
                propiedad = propieadadDao.obtenerPorMatricula("0");
            } else {
                propiedad = propiedadMatricula;
            }
            nombreParroquia = parroquias;
            actaNueva.setActNumero(idGenerado);
            actaNueva.setRepNumero(repertorio);
            actaNueva.setPrdMatricula(propiedad);
            actaNueva.setActCatastro(propiedad.getPrdCatastro());
            actaNueva.setActPredio(propiedad.getPrdPredio());
            actaNueva.setActTomo(0);
            actaNueva.setActBis(String.valueOf(0));
            actaNueva.setTpaId(tipoActaDao.find(new TipoActa(), idTipoActa));
            actaNueva.setActVolumen(new Long(0));
            actaNueva.setActFoja(new Long(0));
            actaNueva.setActInscripcion(new BigInteger("0"));
            actaNueva.setTplId(tipoTramite.getTplId());
            actaNueva.setActUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            actaNueva.setActFHR(Calendar.getInstance().getTime());
            actaNueva.setActFch(todayWithZeroTime);
            actaNueva.setActAnio(anio);
            actaNueva.setActEstado("A");
            actaNueva.setActParroquia(nombreParroquia.toUpperCase());
            actaNueva.setActDescripcion(descripcionParroquia.toUpperCase());
            actaNueva.setActTipoContrato(descripcionContrato);
            actaNueva.setActFechaIngreso(fechaIngreso);
            actaNueva.setActFechaDe(fechaDe);
            actaNueva.setActFechaSentencia(fechaSentencia);
            actaNueva.setActTpjId(tipoJuicioId);
            actaNueva.setActNumeroJucio(numeroJuicio.intValue());
            actaNueva.setActJuzgado(juzgado.intValue());
            actaNueva.setActNotaria(notaria.intValue());
            actaNueva.setActNumeroOficio(numeroOficio.intValue());
            actaNueva.setActObservacion(observacion.toUpperCase());

//        actaNueva.setActDescripcion1(txtEditCompareciente);
//        actaNueva.setActDescripcion2(txtEditOtorgamiento);
//        actaNueva.setActDescripcion3(txtEditAntecedentes);
//        actaNueva.setActDescripcion4(txtEditObjetoContrato);
//        actaNueva.setActDescripcion5(txtEditLinderos);
//        actaNueva.setActDescripcion6(txtEditCuantias);
//        actaNueva.setActDescripcion7(txtEditGravamenes);
//        actaNueva.setActDescripcion8(txtEditObservaciones);
            if (actaEncontrada != null) {
                if (!fechaDe.after(fechaSentencia) && !fechaDe.after(fechaIngreso) && !fechaSentencia.after(fechaIngreso)) {
                    actaServicio.edit(actaNueva);
                    repertorioUsuarioServicio.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(numeroRepertorio), "IND");
                    acta();
                    llenarDatosActualizar();
                    mostrarTabActa();

                } else {

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La Fecha Auto debe ser menor o igual a la Fecha de Sentencia. La fecha de Sentencia debe ser menor o igual a la Fecha de Ingreso", ""));
                }

            } else {
                if (!fechaDe.after(fechaSentencia) && !fechaDe.after(fechaIngreso) && !fechaSentencia.after(fechaIngreso)) {
                    actaServicio.create(actaNueva);
                    repertorioUsuarioServicio.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(numeroRepertorio), "IND");
                    acta();
                    llenarDatosActualizar();
                    mostrarTabActa();
                } else {

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La Fecha Auto debe ser menor o igual a la Fecha de Sentencia. La fecha de Sentencia debe ser menor o igual a la Fecha de Ingreso", ""));
                }

            }

        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public void mostrarTabActa() {

        deshabilitarTabActa = false;
        deshabilitarTabDatosGen = true;
        deshabilitarTabRazon = true;
        renderedTabDatosGen = false;
        renderedTabActa = true;
        renderedTabRazon = false;
        renderedPanel = false;
        renderedPanelTab = true;
        activeIndex = 0;
        renderedTabView = true;

    }

    public void acta() throws ServicioExcepcion {

        institucion = institucionServicio.obtenerInstitucion();
        canton = cantonDao.encontrarCantonPorId(institucion.getInsCanId().toString());
        tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());

        String tituloActa = "<h3 align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + canton.getCanNombre().toUpperCase() + "</strong></h3><br>";
        String linea2Acta = "<h4 align='center'><strong>Acta de Inscripción</strong></h4><br>";
        String contratoActa = "<h4 align='center'><strong>" + descripcionContrato + "</strong></h4><br>";
        String numTramiteActa = "<strong>Nro. de Trámite: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + numeroTramite + "<br>";
        String numRepertorioActa = "<strong>Nro. de Repertorio: </strong>&nbsp;&nbsp;&nbsp;&nbsp;" + numeroRepertorio + "<br>";
        String parroquiaActa = "<strong>Parroquia: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + nombreParroquia.toUpperCase() + "<br>";
        String tomoActa = "<strong>Tomo: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + tipoLibro.getTplTomo() + "<br>";
        String fechaActa = "<strong>Fecha: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + fechasUtil.convertirFechaA_ddMMAAAA_hhmmss(fechaIngreso) + "<br>";

        cabeceraActa = "";
        for (int i = 0; i < numSaltosLinea; i++) {
            cabeceraActa += "<br>";
        }

        cabeceraActa += tituloActa + linea2Acta + contratoActa
                + numTramiteActa + numRepertorioActa + parroquiaActa
                + tomoActa + fechaActa + "<br>";

        TipoJuicio tipoJuicio = new TipoJuicio();
        tipoJuicio = tipoJuicioServicio.encontrarTipoJuicioPorId(tipoJuicioId);
        String fechaIngresoLetras = fechasUtil.convertirFecha_A_letras(fechaIngreso);
        String horaIngresoLetras = fechasUtil.convertirHora_A_letras(fechaIngreso);
        String descripcionParroquiaActa = descripcionParroquia;
        String juzgadoActa = "";
        String fechaDeActa = fechasUtil.convertirFecha_A_letras(fechaDe);;
        String juicioPenalActa = tipoJuicio.getTpjDescripcion();
        String numeroJuicioActa = (transformadorNumerosLetrasUtil.transformador(String.valueOf(juzgado))).toString();
        String nombreDemandante = "";
        String nombreDemandado = "";
        String signoPuntuacion = "";
        int contadorS = 0;
        int contadorN = 0;

        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {
            contadorS++;
            if (contadorS < listaTramDetalleCompS.size()) {
                signoPuntuacion = ", ";
            } else {
                signoPuntuacion = ", ";
            }
            String appMaterno = "";
            if (tramiteDetalle1.getTdtPerApellidoMaterno() != null) {
                appMaterno = tramiteDetalle1.getTdtPerApellidoMaterno();
            } else {
                appMaterno = "";
            }
            nombreDemandante = nombreDemandante + tramiteDetalle1.getTdtPerNombre().trim().toUpperCase() + " "
                    + tramiteDetalle1.getTdtPerApellidoPaterno().trim().toUpperCase() + " "
                    + appMaterno.trim().toUpperCase() + signoPuntuacion;
        }
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompS) {
            contadorN++;
            if (contadorN < listaTramDetalleCompN.size()) {
                signoPuntuacion = ", ";
            } else {
                signoPuntuacion = ". ";
            }
            String appMaterno = "";
            if (tramiteDetalle1.getTdtPerApellidoMaterno() != null) {
                appMaterno = tramiteDetalle1.getTdtPerApellidoMaterno();
            } else {
                appMaterno = "";
            }
            nombreDemandado = nombreDemandado + tramiteDetalle1.getTdtPerNombre().trim().toUpperCase() + " "
                    + tramiteDetalle1.getTdtPerApellidoPaterno().trim().toUpperCase() + " "
                    + appMaterno.trim().toUpperCase() + signoPuntuacion;
        }
        if (cuerpoActa == null || cuerpoActa.isEmpty()) {
            cuerpoActa = "En Quito, a " + fechaIngresoLetras + " a las " + horaIngresoLetras
                    + ", se me presentó el siguiente embargo, cuya copia fotostática"
                    + " se adjunta en _______ fojas, " + descripcionParroquiaActa.toUpperCase() + " ______ ubicado en, "
                    + " de la Parroquia de " + nombreParroquia.toUpperCase() + " ________. La presente " + descripcionContrato
                    + " ha sido realizado por los señores __________ y _____________ "
                    + " , en sus calidades de Alguacil"
                    + " y Depositario Judicial, respectivamente, dando cumplimiento a lo ordenado"
                    + " por el Señor Juez PRIMERO, en auto de " + fechaDeActa
                    + ", dictado dentro del juicio ______ " + juicioPenalActa + ", Nro. UNO"
                    + ", que sigue " + nombreDemandante + " en contra de " + nombreDemandado
                    + "<br></br><br></br> EL REGISTRADOR.-";
        }

        if (bolAgregarImagenEspacioEnBlancoActa) {
            cuerpoActa += "<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        } else {
            cuerpoActa = cuerpoActa.replace("<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>", "");
        }

        actaDocumento = cabeceraActa + cuerpoActa;
        generarPdfActa();

    }

    public void habilitarGuardar() {
        deshabilitarBotonGuardar = false;
    }

    public void previsualizarActa() throws ServicioExcepcion, ParseException {
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlgPreviewActa').show();");
        acta();
        deshabilitarBotonGuardar = false;
        generarPdfActa();
    }

    public void generarPdfActa() {
        try {
            pdfTempUtil.crearPDFconTextoHTML("acta.pdf", actaDocumento, "LIB");
            pdfTempUtil.generarLinksAccesoAlPdf();
            urlActaPdf = pdfTempUtil.getUrl();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sumarSaltoLinea() throws IOException, ServicioExcepcion {
        numSaltosLinea++;
        acta(); //se genera el documento acta

    }

    public void restarSaltoLinea() throws IOException, ServicioExcepcion {
        if (numSaltosLinea > 0) {
            numSaltosLinea--;
            acta(); //se genera el documento acta
        }

    }

    public void agregarImagenBlancoActa() throws ServicioExcepcion {
        acta(); //se genera el documento acta
    }

    public void razon(Tramite tramite) throws ServicioExcepcion {
        institucion = institucionServicio.obtenerInstitucion();
        canton = cantonDao.encontrarCantonPorId(institucion.getInsCanId().toString());
        String tituloRazon = "REGISTRO DE LA PROPIEDAD DEL CANTÓN " + canton.getCanNombre().toUpperCase();
        String linea2Razon = "Razón de Inscripción";
        String linea3Razon = "Con esta fecha queda inscrito el presente";
        String linea4Razon = "";
        String fechaRazon = "";
        List<RepertorioPropiedad> listRepProp = new ArrayList<>();
        List<TramiteDetalle> listTramDet = new ArrayList<>();
        List<TramiteDetalle> listaTramDetComparecientes = new ArrayList<>();
        List<TramiteDetalle> listaTramDetalleComparN = new ArrayList<>();
        List<TramiteDetalle> listaTramDetalleComparS = new ArrayList<>();
        for (Repertorio repertor : listaRepertorioFecha) {
            listRepProp.clear();
            listTramDet.clear();
            if (repertor.getTraNumero().getTraNumero().longValue() == tramite.getTraNumero().longValue()) {
                //tipoTramite = tipoTramiteDao.buscarPorDescripcion(repertor.getRepTtrDescripcion());
                tipoTramite = tipoTramiteDao.buscarPorID(Long.valueOf(String.valueOf(repertor.getRepTtrId())));
                tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
                Acta act = actaServicio.buscarActaPorNumRepertorio(repertor.getRepNumero());
                linea4Razon = linea4Razon + repertor.getRepTtrDescripcion() + " en el "
                        + tipoTramite.getTplId().getTplDescripcion().toUpperCase() + ", tomo "
                        + act.getActTomo() + ", repertorio(s)- " + repertor.getRepNumero() + ".<br></br><br></br>";
//                Repertorio rep = new Repertorio();
//                rep = repertorioDao.encontrarRepertorioPorId(repertor.getRepNumero().toString());
//                fechaRazon = fechasUtil.convertirFechaA_diaSemana_ddMMAAAA_hhmmss(rep.getRepFHR());
            }

            listTramDet = tramiteDetalleDao.listarPorNumTramiteYporIdTipoTramite(
                    repertor.getTraNumero().getTraNumero(), repertor.getRepTtrId());
            listaTramDetComparecientes.addAll(listTramDet);
        }
        Repertorio rep = new Repertorio();
        rep = repertorioDao.encontrarRecientePorTramite(tramite.getTraNumero());
        fechaRazon = fechasUtil.convertirFechaA_diaSemana_ddMMAAAA_hhmmss(rep.getRepFHR());

        for (TramiteDetalle listaTramDetComp : listaTramDetComparecientes) {
            if (listaTramDetComp.getTdtEstado().equals("A")) {
                TipoTramiteCompareciente tipoTramCompareciente = new TipoTramiteCompareciente();
                tipoTramCompareciente = tipoTramiteComparecienteDao.buscarPorId(listaTramDetComp.getTtcId().getTtcId());
                if (tipoTramCompareciente.getTtcPropietario().equals("N")) {
                    listaTramDetalleComparN.add(listaTramDetComp);
                } else if (tipoTramCompareciente.getTtcPropietario().equals("S")) {
                    listaTramDetalleComparS.add(listaTramDetComp);

                }
            }

        }

        //String fechaRazon = fechasUtil.convertirFechaA_diaSemana_ddMMAAAA_hhmmss(fechaIngreso);
        String linea5Razon = "EL REGISTRADOR";
        String linea6Razon = "Contratantes.- ";

        String nombreComparecienteRazon = "";

        String lineaDemandados = "";
        String lineaActores = "";
        TramiteDetalle tramDet1 = listaTramDetalleComparS.get(0);
        if (tramDet1.getTtcId().getTtcPropietario().equals("S")) {
            lineaDemandados = tramDet1.getTdtTpcDescripcion();
        }
        TramiteDetalle tramDet2 = listaTramDetalleComparN.get(0);
        if (tramDet2.getTtcId().getTtcPropietario().equals("N")) {
            lineaActores = tramDet2.getTdtTpcDescripcion();
        }
        //String linea7Razon = "COMPARECIENTE(S)";

        String nombreDemandadoRazon = "";
        //String linea8Razon = "DEMANDADO(S)";
        String linea9Razon = "Responsable.-";
        String nombreResponsable = dataManagerSesion.getUsuarioLogeado().getPerId().getPerNombre().trim().toUpperCase()
                + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoPaterno().trim().toUpperCase()
                + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno().trim().toUpperCase();
        int contadorS = 0;
        int contadorN = 0;
        String signoPuntuacion = "";
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleComparN) {
            contadorS++;
            if (contadorS < listaTramDetalleComparS.size()) {
                signoPuntuacion = "<br></br>";
            } else {
                signoPuntuacion = ", en su calidad de";
            }
            String apMaterno = "";
            if (tramiteDetalle1.getTdtPerApellidoMaterno() != null) {
                apMaterno = tramiteDetalle1.getTdtPerApellidoMaterno();
            } else {
                apMaterno = "";
            }
            nombreComparecienteRazon = nombreComparecienteRazon + tramiteDetalle1.getTdtPerNombre().trim().toUpperCase() + " "
                    + tramiteDetalle1.getTdtPerApellidoPaterno().trim().toUpperCase() + " "
                    + apMaterno.trim().toUpperCase() + signoPuntuacion;
        }
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleComparS) {
            contadorN++;
            if (contadorN < listaTramDetalleComparN.size()) {
                signoPuntuacion = "<br></br>";
            } else {
                signoPuntuacion = ", en su calidad de ";
            }
            String apMaterno = "";
            if (tramiteDetalle1.getTdtPerApellidoMaterno() != null) {
                apMaterno = tramiteDetalle1.getTdtPerApellidoMaterno();
            } else {
                apMaterno = "";
            }
            nombreDemandadoRazon = nombreDemandadoRazon + tramiteDetalle1.getTdtPerNombre().trim().toUpperCase() + " "
                    + tramiteDetalle1.getTdtPerApellidoPaterno().trim().toUpperCase() + " "
                    + apMaterno.trim().toUpperCase() + signoPuntuacion;
        }
        txtRazon = "";
        for (int i = 0; i < numSaltosLineaRazon; i++) {
            txtRazon += "<br>";
        }
        txtRazon += "<strong>" + tituloRazon + "</strong><br></br>"
                + linea2Razon + "<br></br>" + linea3Razon + "<br></br>"
                + linea4Razon + "<br></br>" + fechaRazon + "<br></br>"
                + linea5Razon + "<br></br>" + linea6Razon + "<br></br><br></br><br></br>"
                + nombreComparecienteRazon + "<br></br>"
                + lineaActores + "<br></br><br></br><br></br>"
                + nombreDemandadoRazon + "<br></br>"
                + lineaDemandados + "<br></br><br></br><br></br>" + linea9Razon + "<br></br><br></br><br></br>"
                + nombreResponsable;
        if (bolAgregarImagenEspacioEnBlancoRazon) {
            txtRazon += "<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }
        generarPdfRazon();
    }

    public void mostrarPropiedades() throws ServicioExcepcion {
        cargarTablaComparecientes();

        List<Propietario> listTmpPropietarioS = new ArrayList<>();
        listPropiedades_S.clear();
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompS) {
            listTmpPropietarioS.clear();
            listTmpPropietarioS = propietarioDao.listarPorPerId(tramiteDetalle1.getPerId().getPerId());
            for (Propietario propietario : listTmpPropietarioS) {
                if (!listPropiedades_S.contains(propietario.getPrdMatricula())) {
                    listPropiedades_S.add(propietario.getPrdMatricula());
                }
            }
        }

    }

    public void crearGravamen() throws ServicioExcepcion {

        //obtener comparecientes S, N
        cargarTablaComparecientes();

        //CREAR GRAVAMEN
        Propiedad propiedadMatriculaCero = new Propiedad();
        propiedadMatriculaCero = propieadadDao.encontrarPropiedadPorId("0");

        List<Propietario> propietarioS = new ArrayList<>();
        List<Propiedad> propiedadS = new ArrayList<>();
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompS) {
            propietarioS = propietarioDao.listarPorPerId(tramiteDetalle1.getPerId().getPerId());
            if (!propietarioS.isEmpty()) {
                for (Propietario propietario : propietarioS) {
                    if (!propiedadS.contains(propietario.getPrdMatricula())) {
                        propiedadS.add(propietario.getPrdMatricula());
                    }
                }
                for (Propiedad propiedad : propiedadS) {
                    Gravamen gravBuscar = gravamenDao.buscarTopPorMatriculaYRepertorioYEstado(repertorio.getRepNumero().intValue(), propiedad.getPrdMatricula(), "R");
                    if (gravBuscar == null) {

                        Gravamen gravamen = new Gravamen();
                        gravamen.setPrdMatricula(propiedad);
                        gravamen.setRepNumero(repertorio);
                        gravamen.setGraDescripcion("Gravamen " + descripcionContrato);
                        gravamen.setGraEstado("R");
                        gravamen.setGraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        gravamen.setGraFHR(Calendar.getInstance().getTime());
                        gravamenDao.create(gravamen);

                        BigInteger idPersona = BigInteger.valueOf(tramiteDetalle1.getPerId().getPerId());
                        PropiedadDetalle propDet = propiedadDetalleServicio.obtener_Por_PrdMatricula_idPersona_Estado(propiedad.getPrdMatricula(), idPersona, "A");
                        GravamenDetalle gravDetalle = new GravamenDetalle();
                        BigDecimal gravamenPorcentaje = new BigDecimal(0);
                        if (propDet != null) {
                            gravamenPorcentaje = propDet.getPdtValor();
                        } else {
                            gravamenPorcentaje = new BigDecimal(100);
                        }
                        gravDetalle.setGvdEstado("R");
                        gravDetalle.setGraId(gravamen);
                        gravDetalle.setGvdFHR(Calendar.getInstance().getTime());
                        gravDetalle.setGvdPorcentaje(gravamenPorcentaje);
                        gravDetalle.setGvdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        gravDetalle.setPerId(tramiteDetalle1.getPerId());
                        gravamenDetalleDao.create(gravDetalle);
                    }
                }

            } else {
                Gravamen gravBuscar = gravamenDao.buscarTopPorMatriculaYRepertorioYEstado(repertorio.getRepNumero().intValue(), propiedadMatriculaCero.getPrdMatricula(), "R");
                if (gravBuscar == null) {

                    Gravamen gravamen = new Gravamen();
                    gravamen.setPrdMatricula(propiedadMatriculaCero);
                    gravamen.setRepNumero(repertorio);
                    gravamen.setGraDescripcion("Gravamen " + descripcionContrato);
                    gravamen.setGraEstado("R");
                    gravamen.setGraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    gravamen.setGraFHR(Calendar.getInstance().getTime());
                    gravamenDao.create(gravamen);

                    GravamenDetalle gravDetalle = new GravamenDetalle();
                    gravDetalle.setGvdEstado("R");
                    gravDetalle.setGraId(gravamen);
                    gravDetalle.setGvdFHR(Calendar.getInstance().getTime());
                    gravDetalle.setGvdPorcentaje(new BigDecimal(0));
                    gravDetalle.setGvdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    gravDetalle.setPerId(tramiteDetalle1.getPerId());
                    gravamenDetalleDao.create(gravDetalle);
                } else {
                    GravamenDetalle gravDetBuscar = gravamenDetalleDao.buscarPor_IdGravamne_IdPersona_Estado(gravBuscar.getGraId(), tramiteDetalle1.getPerId().getPerId(), "R");
                    if (gravDetBuscar == null) {
                        GravamenDetalle gravDetalle = new GravamenDetalle();
                        gravDetalle.setGvdEstado("R");
                        gravDetalle.setGraId(gravBuscar);
                        gravDetalle.setGvdFHR(Calendar.getInstance().getTime());
                        gravDetalle.setGvdPorcentaje(new BigDecimal(0));
                        gravDetalle.setGvdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        gravDetalle.setPerId(tramiteDetalle1.getPerId());
                        gravamenDetalleDao.create(gravDetalle);

                    }

                }
            }

        }

    }

    public void generarPdfRazon() {
        try {
            pdfTempUtil.crearPDFconTextoHTML("razon.pdf", txtRazon, "LIB");
            pdfTempUtil.generarLinksAccesoAlPdf();
            urlRazonPdf = pdfTempUtil.getUrl();
            System.out.println("urlRazonPdf" + urlRazonPdf);
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sumarSaltoLineaRazon() throws IOException, ServicioExcepcion {
        numSaltosLineaRazon++;
        razon(tramiteR);
    }

    public void restarSaltoLineaRazon() throws IOException, ServicioExcepcion {
        if (numSaltosLineaRazon > 0) {
            numSaltosLineaRazon--;
            razon(tramiteR);
        }

    }

    public void agregarImagenBlancoRazon() throws ServicioExcepcion {
        razon(tramiteR);
    }

    public void sobreEscribirActa() {
        try {
            actaDocumento = cabeceraActa + cuerpoActa;
            actaActualizar = actaServicio.obtenerActaPorNumeroActa(idGenerado);
            actaActualizar.setActActa(actaDocumento);
            actaActualizar.setActActaPdf(actaDocumento);
            actaServicio.edit(actaActualizar);

            deshabilitarTabActa = true;
            deshabilitarTabDatosGen = true;
            deshabilitarTabRazon = false;
            activeIndex = 2;
            renderedPanel = false;
            renderedPanelTab = false;
            crearGravamen();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void guardarRazon(Tramite tramite) throws ServicioExcepcion {
        try {
            Razon razon = new Razon();

            secuenciaControlador.generarSecuencia("RAZ");

            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
            int auxSecuencia = getSecuencia().getSecActual();
            getSecuencia().setSecActual(auxSecuencia + 1);

            Long sec = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
            idRazonGenerado = new Long(sec);
            secuenciaServicio.guardar(getSecuencia());

            razon.setRazId(idRazonGenerado);
            razon.setRazFHR(Calendar.getInstance().getTime());
            razon.setRazUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            razon.setRazHtml(txtRazon);
            razon.setTraNumero(tramite);
            razonNuevaDao.create(razon);

            //procesarRepertorioUsuario_TramiteAccion();
            FacesContext context = FacesContext.getCurrentInstance();
            //context.addMessage(null, new FacesMessage("Razón Guardada", ""));

            deshabilitarTabActa = true;
            deshabilitarTabDatosGen = true;
            deshabilitarTabRazon = true;
            renderedPanel = true;
            renderedPanelTab = false;
            limpiarCampos();
            deshabilitarBoton = true;
            llenarDatos();
            renderedBtnSuspension = false;

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void procesarRepertorioUsuario_TramiteAccion(Tramite tramite) throws ServicioExcepcion {

        try {
            tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(tramite.getTraNumero());

            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(tramiteSeleccionado.getTraNumero(), "IMP"));
            if (tramiteAccion == null) {
                //codigo CargaLaboral
                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("IMP", "Impresion", 1);
            } else {
                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                int auxCarga = cargaAdd.getCarAsignado() + 1;
                cargaAdd.setCarAsignado((short) auxCarga);
                servicioCargaLaboral.edit(cargaAdd);
            }

            List<Repertorio> listRepertorio = new ArrayList<>();
            listRepertorio = repertorioDao.listarPorNumTramite(tramite.getTraNumero());
            for (Repertorio repertorio1 : listRepertorio) {
                RepertorioUsuario repertorioUsuario = new RepertorioUsuario();
                repertorioUsuario.setRpuTipo("IMP");
                repertorioUsuario.setUsuId(usuarioAsignado);
                repertorioUsuario.setRpuEstado("A");
                repertorioUsuario.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                repertorioUsuario.setRpuFHR(Calendar.getInstance().getTime());
                repertorioUsuario.setRepNumero(repertorio1);

                repertorioUsuarioServicio.create(repertorioUsuario);

                //ACTUALIZAR REPORTORIO USUARIO 
                int numRepUsuActualizados = 0;

                numRepUsuActualizados = repertorioUsuarioServicio.actualizarEstadoRepUsuPorRepPorUsrPorTipo(repertorio1.getRepNumero(),
                        dataManagerSesion.getUsuarioLogeado().getUsuId(), "IND");

            }

            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, idRazonGenerado.toString(),
                    "Razon: " + secuenciaControlador.getControladorBb().getNumeroTramite()
                    + " asignada al usuario " + usuarioAsignado.getUsuLogin(),
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), usuarioAsignado.getUsuLogin());

            tramiteSeleccionado.setTraEstado("IMP");
            tramiteDao.edit(tramiteSeleccionado);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, idRazonGenerado.toString(),
                    "ACTUALIZACIÓN DEL ESTADO DE TRAMITE A 'IMP'",
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void buscarActa() throws ServicioExcepcion {
        try {
            actaEncontrada = actaServicio.buscarActaPorNumRepertorio(Long.valueOf(numeroRepertorio));

        } catch (Exception e) {
            System.out.println(e);
            actaEncontrada = null;
        }

    }

    public void verificarEstadoProceso(Tramite tramiteTerm) {
        List<RepertorioUsuario> listaRepUsu = new ArrayList<>();
        for (Repertorio repertor : listaRepertorioFecha) {
            if (repertor.getTraNumero().getTraNumero().longValue() == tramiteTerm.getTraNumero().longValue()) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertor.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "IND");
                listaRepUsu.add(repUsu);
            }
        }
        int aux = 0;
        for (RepertorioUsuario repertorioUsu1 : listaRepUsu) {
            if (repertorioUsu1.getRpuEstadoProceso() != null) {
                if (repertorioUsu1.getRpuEstadoProceso().equals("TERMINADO")) {
                    aux++;
                }
            }

        }
        if (aux == listaRepUsu.size()) {
            todosEstadoProcTerminado = true;
        } else {
            todosEstadoProcTerminado = false;
        }

    }

    public void terminarProceso(Tramite tramite) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            //guardarActa();
            tramiteR = new Tramite();
            tramiteR = tramite;
            verificarEstadoProceso(tramite);
            if (todosEstadoProcTerminado) {
                razon(tramite);
                renderedPanel = false;
                renderedPanelTab = true;
                deshabilitarTabDatosGen = true;

                activeIndex = 0;

                deshabilitarTabDatosGen = true;
                deshabilitarTabActa = true;
                deshabilitarTabRazon = false;
                renderedTabRazon = true;
                renderedTabActa = false;
                renderedTabDatosGen = false;
                renderedTabView = true;

            } else {

                String mensaje = "Aún exsiten Procesos sin Terminar";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aún existen Procesos sin Terminar", mensaje));

            }

//         
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error al Guardar", ""));
            System.out.println(e);
        }
    }

    public void guardar() throws ServicioExcepcion, ParseException {
        guardarRazon(tramiteR);
        procesarRepertorioUsuario_TramiteAccion(tramiteR);

        llenarDatos();

        activeIndex = 0;
        renderedPanel = false;
        renderedPanelTab = false;
        renderedTabRazon = false;
        renderedTabView = false;

    }

}
