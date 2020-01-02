/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.FechasUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
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
import com.rm.empresarial.modelo.Persona;
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
import com.rm.empresarial.modelo.TipoSuspension;
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
import com.rm.empresarial.servicio.TipoSuspensionServicio;
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
@Named(value = "controladorSentenciaPE")
@ViewScoped
public class ControladorSentenciaPE implements Serializable {

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
    List<Propiedad> listaPropiedadMatric = new ArrayList<>();

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
    private List<Propietario> listaPropiedadPorMatricula = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesSeleccionadas = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoJuicio> listaTipoJuicio = new ArrayList<>();

    @Getter
    @Setter
    private List<Parroquia> listaParroquia = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetCompardDerAcc = new ArrayList<>();

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
    private Boolean renderedPanelTab;

    @Getter
    @Setter
    private Long tipoJuicioId;

    @Getter
    @Setter
    private Boolean esPropiedadNueva;

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
    private Boolean disabledBtnGuardarDerAcc = false;

    @Getter
    @Setter
    private Boolean disabledBtnAgregarDerAcc = false;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleSeleccionado;

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
    private Propiedad propiedadMatriculaDerAcc;

    @Getter
    @Setter
    private Short numeroJuicio;

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
    private Boolean todosEstadoProcTerminado = false;

    @Getter
    @Setter
    private Usuario usuarioAsignado;

    @Getter
    @Setter
    private Tramite tramiteR;

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
    private PropiedadDetalle propiedadDetalleSeleccionada;

    @Getter
    @Setter
    private PropiedadDetalle propiedadDetElim;

    @Getter
    @Setter
    private Persona personaSeleccionada;

    @Getter
    @Setter
    private String numIdentificacionPersona;

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
    private List<PropiedadDetalle> listaPropiedadDetalle;

    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropDetActualizarEstado;

    @Getter
    @Setter
    private BigDecimal totalAcc;

    @Getter
    @Setter
    private Boolean renderedTabView;

    @Getter
    @Setter
    private Propiedad propiedadNueva;

    @Getter
    @Setter
    private Propiedad propiedadMatricula;

    @Getter
    @Setter
    private List<TipoSuspension> listaTipoSuspension;

    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    @Getter
    @Setter
    private Institucion institucion = new Institucion();
    @Getter
    @Setter
    private Canton canton = new Canton();

    @Getter
    @Setter
    private BigDecimal numPorcentajeAccionesRestantes;

    @EJB
    private CantonDao cantonDao;

    @EJB
    private RepertorioDao repertorioDao;

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
    private RazonNuevaDao razonNuevaDao;

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
    private PersonaServicio personaServicio;

    @EJB
    private TipoJuicioServicio tipoJuicioServicio;

    @EJB
    private ParroquiaServicio parroquiaServicio;

    @EJB
    private TramiteServicio tramiteServicio;

    @EJB
    private ActaServicio actaServicio;

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
    private PropiedadDetalleServicio servicioPropiedadDetalle;

    @EJB
    private TipoSuspensionServicio servicioTipoSuspension;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private CargaLaboralServicio servicioCargaLaboral;

    @EJB
    private UsuarioServicio servicioUsuario;

    @EJB
    private GravamenDetalleDao gravamenDetalleDao;

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

    public ControladorSentenciaPE() {
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
        deshabilitarBoton = true;
        renderedPanel = false;
        renderedPanelTab = false;
        deshabilitarTabActa = true;
        deshabilitarTabDatosGen = true;
        deshabilitarTabRazon = true;
        renderedBtnSuspension = false;
        esPropiedadNueva = true;
        listaPropiedadDetalle = new ArrayList<>();
        totalAcc = new BigDecimal(0);
        propiedadNueva = new Propiedad();
        propiedadMatriculaDerAcc = new Propiedad();
        propiedadDetElim = new PropiedadDetalle();
        listaPropDetActualizarEstado = new ArrayList<>();
        listaTipoSuspension = new ArrayList<>();
        tramiteDetalleSeleccionado = new TramiteDetalle();
        renderedTabView = false;
        tramiteAccion = new TramiteAccion();
        numPorcentajeAccionesRestantes = new BigDecimal(100);
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

        renderedBtnSuspension = false;

        deshabilitarBoton = true;

        deshabilitarTabActa = true;
        deshabilitarTabDatosGen = true;
        deshabilitarTabRazon = true;
        renderedPanel = true;
        renderedPanelTab = false;
        limpiarCampos();
        deshabilitarBoton = true;
        renderedTabView = false;
        activeIndex = 0;
        renderedTabActa = false;
        renderedTabRazon = true;
        renderedTabView = false;
        listaRepertorioFecha.clear();
        preListaRepertorioFecha.clear();
        listaTramiteDetalle.clear();

        setPreListaRepertorioFecha(inscripcionDao.ListarRepertorioINA_PorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId()));

        for (Repertorio repertorio1 : preListaRepertorioFecha) {

            Tramite tramit = new Tramite();
            tramit = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());
            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramit.getTraNumero().longValue()) && !tramit.getTraEstadoRegistro().trim().equals("RZ")) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorio1.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INA");
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

        setPreListaRepertorioFecha(inscripcionDao.ListarRepertorioINA_PorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId()));

        for (Repertorio repertorio1 : preListaRepertorioFecha) {

            Tramite tramit = new Tramite();
            tramit = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());
            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramit.getTraNumero().longValue()) && !tramit.getTraEstadoRegistro().trim().equals("RZ")) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorio1.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INA");
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
        renderedBtnSuspension = true;
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

            /*
        List<TipoCompareciente> listaPreviaTipoCompareciente = new ArrayList<>();   
        listaPreviaTipoCompareciente = tipoTramiteComparecienteDao.listarTipoComparecientePorTipoTramite(Long.valueOf(repert.getRepTtrId()));
        for (TipoCompareciente tipoCompareciente : listaPreviaTipoCompareciente) {
            TipoTramiteCompareciente tipoTramCompareciente = new TipoTramiteCompareciente();
            for (TramiteDetalle tramDetalle : listaPreviaTramiteDetalle) {
            tipoTramCompareciente = tipoTramiteComparecienteDao.buscarPorId(tramDetalle.getTtcId().getTtcId());
           if(tipoTramCompareciente.getTtcPropietario().equals("N")){
            listaTipoCompareciente.add(tipoCompareciente);
        } 
            }
        }
             */
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

            } catch (ServicioExcepcion ex) {
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

            TramiteDetalle tramiteDetalle = new TramiteDetalle();
            getTramitesControladorBb().setPersonaConyugue(getTramitesControladorBb().getPersona().getPerIdConyuge());
            tramiteDetalle.setPerId(getTramitesControladorBb().getPersona());
            tramiteDetalle.setTdtConyuguePerId(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerId());
            tramiteDetalle.setTdtConyuguePerNombre(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerNombre() + " " + getTramitesControladorBb().getPersona().getPerIdConyuge().getPerApellidoPaterno());
            tramiteDetalle.setTdtConyuguePerTipoIden(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerTipoIdentificacion());
            tramiteDetalle.setTdtEstado("A");
            tramiteDetalle.setTdtFHR(Calendar.getInstance());
            tramiteDetalle.setTdtFechaRegistro(Calendar.getInstance());
            tramiteDetalle.setTdtNumeroRepertorio(Integer.valueOf(numeroRepertorio));
            tramiteDetalle.setTdtPerApellidoPaterno(getTramitesControladorBb().getPersona().getPerApellidoPaterno());
            tramiteDetalle.setTdtPerApellidoMaterno(getTramitesControladorBb().getPersona().getPerApellidoMaterno());
            tramiteDetalle.setTdtPerIdentificacion(getTramitesControladorBb().getPersona().getPerIdentificacion());
            tramiteDetalle.setTdtPerNombre(getTramitesControladorBb().getPersona().getPerNombre());
            tramiteDetalle.setTdtPerTipoContribuyente(" ");
            tramiteDetalle.setTdtPerTipoIdentificacion(getTramitesControladorBb().getPersona().getPerTipoIdentificacion());
            tramiteDetalle.setTdtTpcCodigo(tipoComparec.getTpcCodigo());
            tramiteDetalle.setTdtTpcDescripcion(tipoComparec.getTpcDescripcion());
            tramiteDetalle.setTdtTpcId(tipoComparec.getTpcId());
            tramiteDetalle.setTdtTplId(idLibro);
            tramiteDetalle.setTdtTtrDescripcion(tipoTramite.getTtrDescripcion());
            tramiteDetalle.setTdtTtrId(tipoTramite.getTtrId());
            tramiteDetalle.setTdtUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            tramiteDetalle.setTraNumero(repertorio.getTraNumero());
            tramiteDetalle.setTtcId(tipoTramCompareciente);
            tramiteDetalle.setTdtTplDescripcion(tipoTramite.getTtrDescripcion());
            tramiteDetalle.setTdtParId(tramDetalle.getTdtParId());
            tramiteDetalle.setTdtParNombre(tramDetalle.getTdtParNombre());
            tramiteDetalle.setTdtCatastro(tramDetalle.getTdtCatastro());
            tramiteDetalle.setTdtPredio(tramDetalle.getTdtPredio());
            tramiteDetalleDao.guardarDetalleTramite(tramiteDetalle);

            cargarTablaComparecientes();
            getTramitesControladorBb().setIdentificacion("");
            getTramitesControladorBb().setNombrePersona("");
            System.out.println("tramite detalle guardado");

        } catch (Exception e) {
            System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.guardarTramiteDetalle()");
            System.err.println(e);
        }

    }

    public void seleccionarPropiedades(Long idPersona) throws ServicioExcepcion {
        try {
            listaPropiedad.clear();
            //listaPropiedad = propieadadDao.listarPropiedad_Por_Propietario(idPersona);
            listaPropiedad = propieadadDao.listarPropiedad_ConMatricula_DistintaCero_Por_Propietario(idPersona);
            for (Propiedad propiedad : listaPropiedad) {
                TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(Long.valueOf(numeroTramite), propiedad.getPrdPredio(), propiedad.getPrdCatastro());
                if (tramiteValorPorPropiedad != null) {
                    listaPropiedadesSeleccionadas.add(propiedad);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void guardarPropiedades() throws ServicioExcepcion {
        try {
            tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numeroTramite), descripcionContrato);
            tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());

            for (Propiedad propiedad : listaPropiedad) {
                //consulto registro en tramite valor
                TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(Long.valueOf(numeroTramite), propiedad.getPrdPredio(), propiedad.getPrdCatastro());
                if (listaPropiedadesSeleccionadas.contains(propiedad)) { //si propiedad fue seleccionada
                    if (tramiteValorPorPropiedad == null) { //si no existe tramite valor, creo nuevo
                        //si ya existe un tramite valor lo elimino para crear
                        TramiteValor tramiteValor = new TramiteValor();
                        tramiteValor.setTraNumero(tramiteDetalle.getTraNumero());
                        tramiteValor.setTraNumPredio(propiedad.getPrdPredio());
                        tramiteValor.setTrvNumCatastro(propiedad.getPrdCatastro());
                        tramiteValor.setTrvEstado("A");
                        tramiteValor.setTrvFHR(Calendar.getInstance().getTime());
                        tramiteValor.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        tramiteValor.setTtrId(tipoTramite);
                        tramiteValor.setTrvValor(new BigDecimal(0));
                        tramiteValor.setTrvParId(tramiteDetalle.getTdtParId());
                        tramiteValor.setTrvParNombre(tramiteDetalle.getTdtParNombre());
                        tramiteValor.setTrvAccion(0);
                        tramiteValor.setTrvIva(BigDecimal.ZERO);
                        tramiteValor.setTrvCantidad(BigDecimal.ZERO);
                        tramiteValor.setTtrvPorIva(institucionServicio.porcentajeIva());
                        tramiteValorDao.guardar(tramiteValor);

                    }//si existe tramite valor, no hago nada
                } else { //si la propiedad fue deseleccionada
                    if (tramiteValorPorPropiedad != null) { //si ya existe tramite valor, lo elimino
                        servicioTramiteValor.remove(tramiteValorPorPropiedad);
                    }//si no existe tramite valor, no hago nada
                }
            }
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dlgPropiedades').hide();");
            listarPropiedadPorMatricula();

//        for (Propiedad listaPropiedadesSeleccionada : listaPropiedadesSeleccionadas) {            
//            TramiteValor tramiteValor = new TramiteValor();
//            tramiteValor.setTraNumero(tramiteDetalle.getTraNumero());
//            tramiteValor.setTraNumPredio(Long.valueOf(listaPropiedadesSeleccionada.getPrdPredio()));
//            tramiteValor.setTrvNumCatastro(Long.valueOf(listaPropiedadesSeleccionada.getPrdCatastro()));
//            tramiteValor.setTrvEstado("A");
//            tramiteValor.setTrvFHR(Calendar.getInstance().getTime());
//            tramiteValor.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
//            tramiteValor.setTtrId(tipoTramite);
//            tramiteValor.setTrvValor(new BigDecimal(0));
//            tramiteValorDao.guardar(tramiteValor);            
//        }
//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null, new FacesMessage("", "Propiedad guardada"));
        } catch (Exception e) {
        }

    }

    public void listarPropiedadPorMatricula() throws ServicioExcepcion {
        try {
            tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numeroTramite), descripcionContrato);
            tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
            listaPropiedadPorMatricula.clear();

            listaPropiedadMatric = propieadadDao.listarPropiedadPorMatriculaPorTramitePorPredioPorCatastro(Long.valueOf(numeroTramite), tipoTramite.getTtrId());

            List<Propietario> listaPropietar = new ArrayList<>();
            List<Propietario> preListaProp = new ArrayList<>();
            preListaProp.clear();
            for (Propiedad propiedad : listaPropiedadMatric) {
                listaPropietar = propietarioDao.buscarPor_PropiedadMatricula_Estado(propiedad.getPrdMatricula());
                preListaProp.addAll(listaPropietar);
            }
            //verificar que la propiedad pertenesca a algun compareciente tipo S
            for (Propietario propietario : preListaProp) {
                for (TramiteDetalle tramDet : listaTramDetalleCompS) {
                    Propietario propt = propietarioDao.buscarPropietarioPor_Persona_Matricula_Estado(tramDet.getPerId().getPerId(), propietario.getPrdMatricula().getPrdMatricula(), "A");
                    if (propt != null) {
                        if (!listaPropiedadPorMatricula.contains(propt)) {
                            listaPropiedadPorMatricula.add(propt);
                        }
                    }
                }

            }
            mostrarParroquias();

            // limpiar lista y agregar solo las propiedades que son parte de un compareciente "S"
            listaPropiedadMatric.clear();
            for (Propietario propietario : listaPropiedadPorMatricula) {
                if (!listaPropiedadMatric.contains(propietario.getPrdMatricula())) {
                    listaPropiedadMatric.add(propietario.getPrdMatricula());
                }
            }

            //matricula referencial para acta
            propiedadMatricula = listaPropiedadMatric.get(0);

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
                //listaParroquias.add(propietario.getPrdMatricula().getPrdTdtParNombre());
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

    public void procesoTerminar() throws ServicioExcepcion {
        renderedPanel = false;
        renderedPanelTab = true;
        //guardarGravamen();
        crearPropietario();
        crearGravamen();
        deshabilitarTabActa = true;
        deshabilitarTabDatosGen = false;
        deshabilitarTabRazon = true;
        activeIndex = 0;
        renderedTabRazon = false;
        renderedTabActa = false;
        renderedTabDatosGen = true;

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

//                actaNueva.setActActa(" ");
//                actaNueva.setActActaPdf(" ");
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
                    repertorioUsuarioServicio.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(numeroRepertorio), "INA");
                    acta();
                    llenarDatosActualizar();
                    mostrarTabActa();

                } else {

                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La Fecha Auto debe ser menor o igual a la Fecha de Sentencia. La fecha de Sentencia debe ser menor o igual a la Fecha de Ingreso", ""));
                }

            } else {
                if (!fechaDe.after(fechaSentencia) && !fechaDe.after(fechaIngreso) && !fechaSentencia.after(fechaIngreso)) {
                    actaServicio.create(actaNueva);
                    repertorioUsuarioServicio.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(numeroRepertorio), "INA");
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

    public void acta() throws ServicioExcepcion {

        institucion = institucionServicio.obtenerInstitucion();
        canton = cantonDao.encontrarCantonPorId(institucion.getInsCanId().toString());
        tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());

        String tituloActa = "<h3 align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + canton.getCanNombre().toUpperCase() + "</strong></h3>";
        String linea2Acta = "<h4 align='center'><strong>Acta de Inscripción</strong></h4>";
        String contratoActa = "<h4 align='center'><strong>" + descripcionContrato + "</strong></h4>";
        String numTramiteActa = "<strong>Nro. de Trámite: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + numeroTramite + "<br>";
        String numRepertorioActa = "<strong>Nro. de Repertorio: </strong>&nbsp;&nbsp;&nbsp;&nbsp;" + numeroRepertorio + "<br>";
        String parroquiaActa = "<strong>Parroquia: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + nombreParroquia.toUpperCase() + "<br>";
        String tomoActa = "<strong>Tomo: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + tipoLibro.getTplTomo() + "<br>";
        String fechaActa = "<strong>Fecha: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + fechasUtil.convertirFechaA_ddMMAAAA_hhmmss(fechaIngreso) + "<br>";

        cabeceraActa = "";
        for (int i = 0; i < numSaltosLinea; i++) {
            cabeceraActa += "<br>";
        }

        cabeceraActa += "<strong>" + tituloActa + "</strong><br>"
                + "<strong>" + linea2Acta + "</strong><br>" + "<strong>" + contratoActa + "</strong><br>"
                + numTramiteActa
                + numRepertorioActa + parroquiaActa
                + tomoActa + fechaActa + "<br></br>";

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

        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompS) {
            contadorS++;
            if (contadorS < listaTramDetalleCompS.size()) {
                signoPuntuacion = ", ";
            } else {
                signoPuntuacion = ", ";
            }
            String apMaterno = "";
            if (tramiteDetalle1.getTdtPerApellidoMaterno() != null) {
                apMaterno = tramiteDetalle1.getTdtPerApellidoMaterno();
            } else {
                apMaterno = "";
            }
            nombreDemandante = nombreDemandante + tramiteDetalle1.getTdtPerNombre().trim().toUpperCase() + " "
                    + tramiteDetalle1.getTdtPerApellidoPaterno().trim().toUpperCase() + " "
                    + apMaterno.trim().toUpperCase() + signoPuntuacion;
        }
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {
            contadorN++;
            if (contadorN < listaTramDetalleCompN.size()) {
                signoPuntuacion = ", ";
            } else {
                signoPuntuacion = ". ";
            }
            String apMaterno = "";
            if (tramiteDetalle1.getTdtPerApellidoMaterno() != null) {
                apMaterno = tramiteDetalle1.getTdtPerApellidoMaterno();
            } else {
                apMaterno = "";
            }
            nombreDemandado = nombreDemandado + tramiteDetalle1.getTdtPerNombre().trim().toUpperCase() + " "
                    + tramiteDetalle1.getTdtPerApellidoPaterno().trim().toUpperCase() + " "
                    + apMaterno.trim().toUpperCase() + signoPuntuacion;
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
                    + ", que sigue " + nombreDemandante + " en favor de " + nombreDemandado
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

        String linea7Razon = "";
        String linea8Razon = "";

        TramiteDetalle tramDet1 = listaTramDetalleComparS.get(0);
        if (tramDet1.getTtcId().getTtcPropietario().equals("S")) {
            linea7Razon = tramDet1.getTdtTpcDescripcion();
        }
        TramiteDetalle tramDet2 = listaTramDetalleComparN.get(0);
        if (tramDet2.getTtcId().getTtcPropietario().equals("N")) {
            linea8Razon = tramDet2.getTdtTpcDescripcion();
        }

        //String linea7Razon = "COMPARECIENTE(S)";
        String nombreDemandadoRazon = "";
        //String linea8Razon = "HEREDERO(S)";
        String linea9Razon = "Responsable.-";
        String nombreResponsable = dataManagerSesion.getUsuarioLogeado().getPerId().getPerNombre().trim().toUpperCase()
                + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoPaterno().trim().toUpperCase()
                + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno().trim().toUpperCase();
        int contadorS = 0;
        int contadorN = 0;
        String signoPuntuacion = "";
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleComparS) {
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
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleComparN) {
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
                + nombreComparecienteRazon + "<br>"
                + linea7Razon + "<br></br>"
                + nombreDemandadoRazon + "<br></br>"
                + linea8Razon + "<br></br><br></br><br></br>" + linea9Razon + "<br></br><br></br><br></br>"
                + nombreResponsable;
        if (bolAgregarImagenEspacioEnBlancoRazon) {
            txtRazon += "<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }
        generarPdfRazon();

    }

    public void sobreEscribirActa() {
        try {
            actaDocumento = cabeceraActa + cuerpoActa;
            actaActualizar = actaServicio.obtenerActaPorNumeroActa(idGenerado);
            actaActualizar.setActActa(actaDocumento);
            actaActualizar.setActActaPdf(actaDocumento);
            actaServicio.edit(actaActualizar);

            //activeIndex = 2;
            deshabilitarTabActa = true;
            deshabilitarTabDatosGen = true;
            deshabilitarTabRazon = true;
            renderedTabActa = false;
            renderedTabRazon = false;
            renderedTabDatosGen = false;
            renderedPanel = false;
            renderedPanelTab = false;
            activeIndex = 0;
            renderedBtnSuspension = false;

            limpiarCampos();

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
                        dataManagerSesion.getUsuarioLogeado().getUsuId(), "INA");

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

    public void crearGravamen() throws ServicioExcepcion {

        //obtener comparecientes S, N
        cargarTablaComparecientes();

        //ELIMINAR GRAVAMEN, GRAVAMEN DETALLE SI YA HA SIDO CREADO.
        for (Propietario propietario : listaPropiedadPorMatricula) {
            Gravamen gravBuscar = gravamenDao.buscarTopPorMatriculaYRepertorioYEstado(repertorio.getRepNumero().intValue(), propietario.getPrdMatricula().getPrdMatricula(), "R");
            if (gravBuscar != null) {
                for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {
                    GravamenDetalle gravDetBuscar = gravamenDetalleDao.buscarPor_IdGravamne_IdPersona_Estado(gravBuscar.getGraId(), tramiteDetalle1.getPerId().getPerId(), "R");
                    if (gravDetBuscar != null) {
                        gravamenDetalleDao.remove(gravDetBuscar);
                    }
                }
                gravamenDao.remove(gravBuscar);
            }

        }

        //CREAR GRAVAMEN , GRAVAMEN DETALLE
        for (Propietario propietario : listaPropiedadPorMatricula) {

            Gravamen gravamen = new Gravamen();
            gravamen.setPrdMatricula(propietario.getPrdMatricula());
            gravamen.setRepNumero(repertorio);
            gravamen.setGraDescripcion("Gravamen " + descripcionContrato);
            gravamen.setGraEstado("R");
            gravamen.setGraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            gravamen.setGraFHR(Calendar.getInstance().getTime());
            gravamenDao.create(gravamen);
            for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {
                BigInteger idPersona = BigInteger.valueOf(tramiteDetalle1.getPerId().getPerId());
                PropiedadDetalle propDet = servicioPropiedadDetalle.obtener_Por_PrdMatricula_idPersona_Estado(propietario.getPrdMatricula().getPrdMatricula(), idPersona, "A");
                if (propDet != null) {
                    GravamenDetalle gravDetalle = new GravamenDetalle();
                    gravDetalle.setGvdEstado("R");
                    gravDetalle.setGraId(gravamen);
                    gravDetalle.setGvdFHR(Calendar.getInstance().getTime());
                    gravDetalle.setGvdPorcentaje(propDet.getPdtValor());
                    gravDetalle.setGvdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    gravDetalle.setPerId(tramiteDetalle1.getPerId());
                    gravamenDetalleDao.create(gravDetalle);
                }

            }

        }

    }

    public void crearPropietario() throws ServicioExcepcion {
        try {
            List<Propietario> listaNuevosPropietarios = new ArrayList<>();
            for (Propiedad propiedad : listaPropiedadMatric) {

                //CREA LISTA DE NUEVOS PROPIETARIOS
                for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {
                    Propietario nuevoPropietario = new Propietario();
                    nuevoPropietario.setPerId(tramiteDetalle1.getPerId());
                    nuevoPropietario.setPprPerIdentificacion(tramiteDetalle1.getPerId().getPerIdentificacion());
                    nuevoPropietario.setPprPerNombre(tramiteDetalle1.getPerId().getPerNombre());
                    nuevoPropietario.setPprPerApellidoPaterno(tramiteDetalle1.getPerId().getPerApellidoPaterno());
                    nuevoPropietario.setPpPerApellidoMaterno(tramiteDetalle1.getPerId().getPerApellidoMaterno());
                    nuevoPropietario.setPprEstado("A");
                    nuevoPropietario.setPprFHR(Calendar.getInstance().getTime());
                    nuevoPropietario.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    nuevoPropietario.setPrdMatricula(propiedad);
                    nuevoPropietario.setPrdRepertorio(Integer.valueOf(numeroRepertorio));
                    listaNuevosPropietarios.add(nuevoPropietario);
                    //propietarioDao.guardar(nuevoPropietario);                                         

                }
                //ACTUALIZA A LOS ANTERIORES PROPIETARIOS A ESTADO I
                List<Propietario> listaPropietarioActualizar = new ArrayList<>();
                for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompS) {
                    listaPropietarioActualizar.clear();
                    listaPropietarioActualizar = propietarioDao.buscarPor_Id_Persona_Por_Matricula(tramiteDetalle1.getPerId().getPerId(), propiedad.getPrdMatricula());
                    for (Propietario propietario : listaPropietarioActualizar) {
                        propietario.setPprEstado("I");
                        propietarioDao.edit(propietario);
                    }
                    //CREA NUEVO PROPIEPIETARIO CON ESTADO "I" A PARTIR DEL ANTIGUO PROPIETARIO 
                    for (Propietario propietario : listaPropietarioActualizar) {
                        Propietario nuevoPropietario = new Propietario();
                        nuevoPropietario.setPerId(propietario.getPerId());
                        nuevoPropietario.setPprPerIdentificacion(propietario.getPerId().getPerIdentificacion());
                        nuevoPropietario.setPprPerNombre(propietario.getPerId().getPerNombre());
                        nuevoPropietario.setPprPerApellidoPaterno(propietario.getPerId().getPerApellidoPaterno());
                        nuevoPropietario.setPpPerApellidoMaterno(propietario.getPerId().getPerApellidoMaterno());
                        nuevoPropietario.setPprEstado("I");
                        nuevoPropietario.setPprFHR(Calendar.getInstance().getTime());
                        nuevoPropietario.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        nuevoPropietario.setPrdMatricula(propietario.getPrdMatricula());
                        nuevoPropietario.setPrdRepertorio(Integer.valueOf(numeroRepertorio));
                        propietarioDao.create(nuevoPropietario);
                    }

                }

            }
            //GUARDA LOS NUEVOS PROPIETARIOS SI ES QUE AUN NO SE ENCUENTAN EN LA BASE
            List<Propietario> listaPropietarioBuscar = new ArrayList<>();
            for (Propietario listaNuevosPropietario : listaNuevosPropietarios) {
                listaPropietarioBuscar.clear();
                listaPropietarioBuscar = propietarioDao.listarPorPerIdPorNumRepertorioPorNumMatricula(
                        listaNuevosPropietario.getPerId().getPerId(), listaNuevosPropietario.getPrdRepertorio(), listaNuevosPropietario.getPrdMatricula().getPrdMatricula());
                if (listaPropietarioBuscar.isEmpty()) {
                    propietarioDao.guardar(listaNuevosPropietario);
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        /*
        Propiedad propiedadMatriculaCero = new Propiedad();
            propiedadMatriculaCero = propieadadDao.encontrarPropiedadPorId("0");

            for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {

              
                List<Propietario> listaPropietar = new ArrayList<>();
                listaPropietar = propietarioDao.listarPor_Id_Persona(tramiteDetalle1.getPerId().getPerId());

                if (listaPropietar.isEmpty()) {
                    Propietario nuevoPropietario = new Propietario();
                    nuevoPropietario.setPerId(tramiteDetalle1.getPerId());
                    nuevoPropietario.setPprPerIdentificacion(tramiteDetalle1.getPerId().getPerIdentificacion());
                    nuevoPropietario.setPprPerNombre(tramiteDetalle1.getPerId().getPerNombre());
                    nuevoPropietario.setPprPerApellidoPaterno(tramiteDetalle1.getPerId().getPerApellidoPaterno());
                    nuevoPropietario.setPpPerApellidoMaterno(tramiteDetalle1.getPerId().getPerApellidoMaterno());
                    nuevoPropietario.setPprEstado("A");
                    nuevoPropietario.setPprFHR(Calendar.getInstance().getTime());
                    nuevoPropietario.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    nuevoPropietario.setPrdMatricula(propiedadMatriculaCero);
                    nuevoPropietario.setPrdRepertorio(Integer.valueOf(numeroRepertorio));
                    propietarioDao.create(nuevoPropietario);                    

                    

                } else {

                    boolean existePropiedad = false;
                    for (Propietario propietario : listaPropietar) {

                        existePropiedad = propieadadDao.existeMatriculaConPredioCatastroDistintoCero(propietario.getPrdMatricula().getPrdMatricula());
                        if (existePropiedad == false) {
                            Propietario nuevoPropietario = new Propietario();
                            nuevoPropietario.setPerId(tramiteDetalle1.getPerId());
                            nuevoPropietario.setPprPerIdentificacion(tramiteDetalle1.getPerId().getPerIdentificacion());
                            nuevoPropietario.setPprPerNombre(tramiteDetalle1.getPerId().getPerNombre());
                            nuevoPropietario.setPprPerApellidoPaterno(tramiteDetalle1.getPerId().getPerApellidoPaterno());
                            nuevoPropietario.setPpPerApellidoMaterno(tramiteDetalle1.getPerId().getPerApellidoMaterno());
                            nuevoPropietario.setPprEstado("A");
                            nuevoPropietario.setPprFHR(Calendar.getInstance().getTime());
                            nuevoPropietario.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            nuevoPropietario.setPrdMatricula(propiedadMatriculaCero);
                            nuevoPropietario.setPrdRepertorio(Integer.valueOf(numeroRepertorio));
                            propietarioDao.create(nuevoPropietario);                        

                        }

                    }

                }

            }
         */
    }

    public void guardarGravamen() {

        /*
        try {
            
            
            listaTramiteValor = tramiteValorDao.listarPorNumTramitePorTipoTramite(Long.valueOf(numeroTramite), tipoTramite.getTtrId());
            List<Propiedad> listaProp = new ArrayList<>();
            for (TramiteValor tramValor : listaTramiteValor) {
                Propiedad prop = new Propiedad();
                prop = propieadadDao.buscarPropiedadPor_predio_o_catastro(tramValor.getTraNumPredio().toString(), tramValor.getTrvNumCatastro().toString());
                listaProp.add(prop);
            }
            for (Propiedad propiedad : listaProp) {
                
                 List<Gravamen> listaGravamen= new ArrayList<>();
            listaGravamen = gravamenDao.buscarPorMatriculaYRepertorioYEstado(Integer.valueOf(numeroRepertorio), propiedad.getPrdMatricula(),"A");
            if(listaGravamen.isEmpty()){
                Gravamen gravamen = new Gravamen();
                gravamen.setPrdMatricula(propiedad);
                gravamen.setRepNumero(repertorio);
                gravamen.setGraDescripcion("Gravamen " + descripcionContrato);
                gravamen.setGraEstado("A");
                gravamen.setGraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                gravamen.setGraFHR(Calendar.getInstance().getTime());
                gravamenDao.create(gravamen);                
            }
                
            }           
            
            
            Propiedad propiedadMatriculaCero = new Propiedad(); 
            propiedadMatriculaCero = propieadadDao.encontrarPropiedadPorId("0");
            
            for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {
                
                
                
                List<Propietario> listaPropietar = new ArrayList<>();
                listaPropietar = propietarioDao.listarPor_Id_Persona(tramiteDetalle1.getPerId().getPerId());

                
                if(listaPropietar.isEmpty()){
                    Propietario nuevoPropietario = new Propietario();
                    nuevoPropietario.setPerId(tramiteDetalle1.getPerId());
                    nuevoPropietario.setPprPerIdentificacion(tramiteDetalle1.getPerId().getPerIdentificacion());
                    nuevoPropietario.setPprPerNombre(tramiteDetalle1.getPerId().getPerNombre());
                    nuevoPropietario.setPprPerApellidoPaterno(tramiteDetalle1.getPerId().getPerApellidoPaterno());
                    nuevoPropietario.setPpPerApellidoMaterno(tramiteDetalle1.getPerId().getPerApellidoMaterno());
                    nuevoPropietario.setPprEstado("A");
                    nuevoPropietario.setPprFHR(Calendar.getInstance().getTime());
                    nuevoPropietario.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    nuevoPropietario.setPrdMatricula(propiedadMatriculaCero);
                    nuevoPropietario.setPrdRepertorio(Integer.valueOf(numeroRepertorio));
                    propietarioDao.create(nuevoPropietario);
                    
                    gravamenDao.actualizarEstadoPorNumRepPorNumMat(Long.valueOf(
                            numeroRepertorio), propiedadMatriculaCero.getPrdMatricula(), dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    
                     Gravamen gravamen = new Gravamen();
                gravamen.setPrdMatricula(propiedadMatriculaCero);
                gravamen.setRepNumero(repertorio);
                gravamen.setGraDescripcion("Gravamen " + descripcionContrato);
                gravamen.setGraEstado("A");
                gravamen.setGraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                gravamen.setGraFHR(Calendar.getInstance().getTime());
                gravamenDao.create(gravamen);
                    
                }
                else{
                    
                boolean existePropiedad = false;
                for (Propietario propietario : listaPropietar) {
                     
                     existePropiedad = propieadadDao.existeMatriculaConPredioCatastroDistintoCero(propietario.getPrdMatricula().getPrdMatricula());
                     if(existePropiedad == false){
                         Propietario nuevoPropietario = new Propietario();
                    nuevoPropietario.setPerId(tramiteDetalle1.getPerId());
                    nuevoPropietario.setPprPerIdentificacion(tramiteDetalle1.getPerId().getPerIdentificacion());
                    nuevoPropietario.setPprPerNombre(tramiteDetalle1.getPerId().getPerNombre());
                    nuevoPropietario.setPprPerApellidoPaterno(tramiteDetalle1.getPerId().getPerApellidoPaterno());
                    nuevoPropietario.setPpPerApellidoMaterno(tramiteDetalle1.getPerId().getPerApellidoMaterno());
                    nuevoPropietario.setPprEstado("A");
                    nuevoPropietario.setPprFHR(Calendar.getInstance().getTime());
                    nuevoPropietario.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    nuevoPropietario.setPrdMatricula(propiedadMatriculaCero);
                    nuevoPropietario.setPrdRepertorio(Integer.valueOf(numeroRepertorio));
                    propietarioDao.create(nuevoPropietario);
                    
                    gravamenDao.actualizarEstadoPorNumRepPorNumMat(Long.valueOf(
                            numeroRepertorio), propiedadMatriculaCero.getPrdMatricula(), dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    
                    Gravamen gravamen = new Gravamen();
                gravamen.setPrdMatricula(propiedadMatriculaCero);
                gravamen.setRepNumero(repertorio);
                gravamen.setGraDescripcion("Gravamen " + descripcionContrato);
                gravamen.setGraEstado("A");
                gravamen.setGraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                gravamen.setGraFHR(Calendar.getInstance().getTime());
                gravamenDao.create(gravamen);
                
                     }   

                }
                    
                }
                
                
                
                        
                
            }
            
            
            

        } catch (Exception e) {
            System.out.println(e);
        }
        
         */
    }

    public void buscarActa() throws ServicioExcepcion {
        try {
            actaEncontrada = actaServicio.buscarActaPorNumRepertorio(Long.valueOf(numeroRepertorio));

        } catch (Exception e) {
            System.out.println(e);
            actaEncontrada = null;
        }

    }

    //******************** DERECHOS Y ACCIONES *****************
    public void preCrearDetallePropiedad() {
        propiedadDetalleSeleccionada = new PropiedadDetalle();
        propiedadDetalleSeleccionada.setPdtValor(BigDecimal.ZERO);
        propiedadDetalleSeleccionada.setPdtEstado("A");
        propiedadDetalleSeleccionada.setPdtClase("I");
        cargarListaComparecientes();
    }

    public boolean verificarExiste(BigInteger numPerId) {
        try {
            Repertorio repertorioSeleccionado = repertorioDao.encontrarRepertorioPorId(numeroRepertorio);
            Tramite tramSelec = repertorioSeleccionado.getTraNumero();
            List<TramiteDetalle> lista = tramiteDetalleDao.listarPor_NumTramite_TipoTramite_Persona(tramSelec.getTraNumero(), repertorioSeleccionado.getRepTtrId(), numPerId);
            return lista.isEmpty();//deshabilito si la lista esta vaica
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public void cargarListaComparecientes() {
        try {
            Tramite tramiteSelec = tramiteDao.buscarTramitePorNumero(Long.valueOf(numeroTramite));
            Repertorio repertorioSeleccionado = repertorioDao.encontrarRepertorioPorId(numeroRepertorio);
            listaTramDetCompardDerAcc.clear();
            RepertorioUsuario repertorioUsuarioSelec = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(
                    repertorioSeleccionado.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INA");
            listaTramDetCompardDerAcc = tramiteDetalleDao.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "N");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void guardarDerAcc() throws ServicioExcepcion {
        calcularTotalAcciones();
        BigDecimal bigDec100 = new BigDecimal(100);
        if (totalAcc.compareTo(bigDec100) == 0) {
            for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
                if (propiedadDetalle.getPdtEstado().equals("A")) {
                    propiedadDetalle.setPdtTipo("GDA");
                    propiedadDetalle.setPdtUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    propiedadDetalle.setPdtUmdId(BigInteger.valueOf(propiedadMatriculaDerAcc.getUmdId().getUmdId()));
                    propiedadDetalle.setPdtUmdAbreviatura(propiedadMatriculaDerAcc.getUmdId().getUmdAbreviatura());
                    propiedadDetalle.setPdtFHR(Calendar.getInstance().getTime());
                    propiedadDetalle.setPrdMatricula(propiedadMatriculaDerAcc);
                    propiedadDetalle.setPdtEstado("A");
                    servicioPropiedadDetalle.guardar(propiedadDetalle);
                } else {
                    //servicioPropiedadDetalle.edit(propiedadDetElim);
                    servicioPropiedadDetalle.edit(propiedadDetalle);
                }

            }
            for (PropiedadDetalle propDetalle : listaPropDetActualizarEstado) {
                servicioPropiedadDetalle.edit(propDetalle);
            }
            disabledBtnGuardarDerAcc = true;
            disabledBtnAgregarDerAcc = true;
            JsfUtil.addSuccessMessage("Derechos y Acciones guardado");
        } else {
            JsfUtil.addErrorMessage("Debe llenar el 100%");
        }
    }

    public void crearDetallePropiedad() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.crearDetallePropiedad()");

        calcularTotalAcciones();
        BigDecimal valorAdicional = new BigDecimal(0);
        valorAdicional = propiedadDetalleSeleccionada.getPdtValor();
        BigDecimal bigDec100 = new BigDecimal(100);
        BigDecimal bigDec0 = new BigDecimal(0);
        if (totalAcc.add(valorAdicional).compareTo(bigDec0) == 1)//valor a agregar es mayor que 0
        {
            if ((totalAcc.add(valorAdicional)).compareTo(bigDec100) <= 0) {//if (totalAcc + valorAdicional <= 100) {

                //propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(personaSeleccionada.getPerId()));
                //BigInteger idusuario = BigInteger.valueOf(personaSeleccionada.getPerId());
                if (tramiteDetalleSeleccionado != null) {
                    propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(tramiteDetalleSeleccionado.getPerId().getPerId()));
                    BigInteger idusuario = BigInteger.valueOf(tramiteDetalleSeleccionado.getPerId().getPerId());
                    Persona persona = personaServicio.find(new Persona(), idusuario.longValue());
                    propiedadDetalleSeleccionada.setPersona(persona);
                    propiedadDetalleSeleccionada.setPdtEstado("A");
                    boolean yaExiste = false;
                    //listaPropiedadDetalle.add(propiedadDetalleSeleccionada);
                    for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) { //recorre la lista para encontrar un registro con el mismo id de persona y solo sumar el valor
                        if (tramiteDetalleSeleccionado.getPerId().getPerId().equals(propiedadDetalle.getPdtPerId().longValue())) {
                            propiedadDetalle.setPdtValor(propiedadDetalle.getPdtValor().add(propiedadDetalleSeleccionada.getPdtValor()));
                            propiedadDetalle.setPdtDescripcion(TransformadorNumerosLetrasUtil.transformador(propiedadDetalle.getPdtValor().toString())
                                    + "POR CIENTO DE ACCIONES Y DERECHOS SOBRE " + propiedadMatriculaDerAcc.getTpdId().getTpdNombre().trim()
                                    + " UBICADO EN "
                                    + propiedadMatriculaDerAcc.getPrdTdtParNombre() + " DE ESTE CANTON");
                            yaExiste = true;
                            break;
                        }
                    }
                    if (!yaExiste) {
                        listaPropiedadDetalle.add(propiedadDetalleSeleccionada);
                    }
                    calcularTotalAcciones();
                } else {
                    JsfUtil.addErrorMessage("Debe seleccionar una persona");
                }

            } else {
                JsfUtil.addErrorMessage("No puede sobrepasar el 100% (total Acciones: " + totalAcc + ")");
            }
        } else {
            JsfUtil.addErrorMessage("Debe ingresar un valor mayor a 0");
        }
        //propiedadDetalleSeleccionada = new PropiedadDetalle();
        preCrearDetallePropiedad();
    }

    public void calcularTotalAcciones() {
        try {
            totalAcc = new BigDecimal(0);
            for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
                if (propiedadDetalle.getPdtEstado().equals("A") && propiedadDetalle.getPdtClase().equals("I")) {
                    totalAcc = totalAcc.add(propiedadDetalle.getPdtValor());//totalAcc += propiedadDetalle.getPdtValor();                                   
                }
            }
            for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
                if (propiedadDetalle.getPdtEstado().equals("A") && propiedadDetalle.getPdtClase().equals("C")) {
                    totalAcc = totalAcc.add(propiedadDetalle.getPdtValor());//totalAcc += propiedadDetalle.getPdtValor();                                   
                    break; //solo suma el primero y sale del bucle
                }
            }
            BigDecimal bigDec100 = new BigDecimal(100);
            if (totalAcc.compareTo(bigDec100) == 0) {
                disabledBtnGuardarDerAcc = false;
                disabledBtnAgregarDerAcc = true;
            } else {
                disabledBtnGuardarDerAcc = true;
                disabledBtnAgregarDerAcc = false;
            }
            numPorcentajeAccionesRestantes = bigDec100.subtract(totalAcc);
            if (numPorcentajeAccionesRestantes.compareTo(BigDecimal.ZERO) == 0) { //si es igual a 0, que no mueste con exponenciales
                numPorcentajeAccionesRestantes = BigDecimal.ZERO;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void cargarPropiedadDetalle() {
        try {
            listaPropiedadDetalle.clear();
            List<PropiedadDetalle> resultado = new ArrayList<>();
            resultado = servicioPropiedadDetalle.listar_Por_Propiedad_Tipo_Estado(propiedadMatriculaDerAcc.getPrdMatricula(), "GDA");

            BigDecimal totAcc = new BigDecimal(0);
            for (PropiedadDetalle propDet : resultado) {
                BigInteger idusuario = propDet.getPdtPerId();
                Persona persona = personaServicio.find(new Persona(), idusuario.longValue());
                propDet.setPersona(persona);
                listaPropiedadDetalle.add(propDet);

                totAcc = totAcc.add(propDet.getPdtValor());

            }
            BigDecimal bigDec100 = new BigDecimal(100);
            if (totAcc.compareTo(bigDec100) == 0) {
                disabledBtnGuardarDerAcc = false;
                disabledBtnAgregarDerAcc = true;
            } else {
                disabledBtnGuardarDerAcc = true;
                disabledBtnAgregarDerAcc = false;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void generarDescripcion() {
        try {
            propiedadDetalleSeleccionada.setPdtDescripcion(TransformadorNumerosLetrasUtil.transformador(propiedadDetalleSeleccionada.getPdtValor().toString())
                    + "POR CIENTO DE ACCIONES Y DERECHOS SOBRE " + propiedadMatriculaDerAcc.getTpdId().getTpdNombre().trim()
                    + " UBICADO EN "
                    + propiedadMatriculaDerAcc.getPrdTdtParNombre() + " DE ESTE CANTON");
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void cargarDatosPersona() {
        personaSeleccionada = new Persona();
        try {
            if (personaSeleccionada != null) {
                personaSeleccionada = personaUtil.obtenerDatosPersona(numIdentificacionPersona);

                //    JsfUtil.addSuccessMessage("Persona encontrada");
            } else {
                JsfUtil.addErrorMessage("Identificación incorrecta o no válida");
            }

        } catch (ServicioExcepcion ex) {
            JsfUtil.addErrorMessage("Ocurrió un error");
            Logger
                    .getLogger(ControladorNuevasMatriculas.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void borrarDetallePropiedad(int row) {
        propiedadDetElim.setPdtEstado("I");
        servicioPropiedadDetalle.edit(propiedadDetElim);
        listaPropiedadDetalle.set(row, propiedadDetElim);
        if (propiedadDetElim.getPdtId() != null) {
            listaPropDetActualizarEstado.add(propiedadDetElim);
        }

        listaPropiedadDetalle.remove(row);
        calcularTotalAcciones();
        disabledBtnAgregarDerAcc = false;
    }

    /**
     * *************************************************************
     */
    public void verificarEstadoProceso(Tramite tramiteTerm) {
        List<RepertorioUsuario> listaRepUsu = new ArrayList<>();
        for (Repertorio repertor : listaRepertorioFecha) {
            if (repertor.getTraNumero().getTraNumero().longValue() == tramiteTerm.getTraNumero().longValue()) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertor.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INA");
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
