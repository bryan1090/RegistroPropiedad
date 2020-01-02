/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
Para el caso de Cancelacion de Fideicomiso, el compareciente tipo "S" es el propietario original de la propiedad,
mientras que el compareciente tipo "N" es aquel que fue asignado (creado) tambien como propietario en el proceso de Fideicomiso
Por lo tanto el proceso de Cancelacion de Fideicomiso inactivara en la tabla Propietarios, en caso de existir, a los propietarios (comparecientes tipo "N")
tomando en cuenta la matricula del gravamen que exista asociado al propietario Original (comaprecoiente tipo "S")
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
import com.rm.empresarial.controlador.util.UtilTexto;
import com.rm.empresarial.dao.ActaRazonDao;
import com.rm.empresarial.dao.CantonDao;
import com.rm.empresarial.dao.CargaLaboralDao;
import com.rm.empresarial.dao.EquivalenciaTramiteDao;
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
import com.rm.empresarial.dao.TramiteUsuarioDao;
import com.rm.empresarial.dao.TramiteValorDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Canton;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.EquivalenciaTramite;
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
@Named(value = "controladorCancelaciones")
@ViewScoped
public class ControladorCancelacion implements Serializable {
    
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
    List<Long> listaIdPersona = new ArrayList();
    
    @Getter
    @Setter
    List<Long> listaIdPerGravDet = new ArrayList();
    
    @Getter
    @Setter
    List<Long> listaIdGrav = new ArrayList<>();
    
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
    private List<Propietario> listaPropiedadPorMatricula = new ArrayList<>();
    
    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesSeleccionadas = new ArrayList<>();
    
    @Getter
    @Setter
    private List<Gravamen> listaGravamenSeleccionados = new ArrayList<>();
    
    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropDetSeleccionadas = new ArrayList<>();
    
    @Getter
    @Setter
    private List<GravamenDetalle> listaGravamenDetSeleccionados = new ArrayList<>();
    
    @Getter
    @Setter
    private List<GravamenDetalle> listaGravamenDetCancelar = new ArrayList<>();
    
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
    List<Gravamen> listaGrav = new ArrayList<>();
    
    @Getter
    @Setter
    List<Gravamen> listaGravAuxiliar = new ArrayList<>();
    
    @Getter
    @Setter
    List<Gravamen> listaGravamenesComp = new ArrayList<>();
    
    @Getter
    @Setter
    List<Gravamen> listaGravamenesCancelar = new ArrayList<>();
    
    List<Gravamen> listaGravSelecAcum = new ArrayList<>();
    
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
    private Boolean renderedTabView;
    
    @Getter
    @Setter
    private Boolean propiedadSeleccionada;
    
    @Getter
    @Setter
    private Boolean todosEstadoProcTerminado = false;
    
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
    private Boolean renderedPanelTab;
    
    @Getter
    @Setter
    private Boolean renderedTabDatosGen;
    
    @Getter
    @Setter
    private Boolean renderedTabActa;
    
    @Getter
    @Setter
    private Boolean renderedTabRazon;
    
    @Getter
    @Setter
    private Boolean siDialogoGravDetalle;
    
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
    Long auxIdPersona;
    
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
    private String tipoJuicioDescripcion;
    
    @Getter
    @Setter
    private TipoJuicio tipoJuicio = new TipoJuicio();
    
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
    private Tramite tramiteR;
    
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
    private int aux;
    
    @Getter
    @Setter
    private String mensajeCancelar;
    
    @Getter
    @Setter
    private Long idPersonaGravamen;
    
    @Getter
    @Setter
    private Long idGravamen;
    
    @Getter
    @Setter
    private TramiteAccion tramiteAccion;
    
    @Getter
    @Setter
    private BigDecimal numPorcentajeAccionesRestantes;
    
    @Getter
    @Setter
    private BigDecimal totalAcc;
    
    @Getter
    @Setter
    private Institucion institucion = new Institucion();
    @Getter
    @Setter
    private Canton canton = new Canton();
    
    @EJB
    private RepertorioDao repertorioDao;
    
    @EJB
    private EquivalenciaTramiteDao equivalenciaTramiteDao;
    
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
    private RazonNuevaDao razonNuevaDao;
    
    @EJB
    private CargaLaboralDao cargaLaboralDao;
    
    @EJB
    private GravamenDao gravamenDao;
    
    @EJB
    private PropietarioDao propietarioDao;
    
    @EJB
    private RepertorioUsuarioServicio repertorioUsuarioServicio;
    
    @EJB
    private TramiteAccionServicio servicioTramiteAccion;
    
    @EJB
    private CargaLaboralServicio servicioCargaLaboral;
    
    @EJB
    private UsuarioServicio servicioUsuario;
    
    @EJB
    private CantonDao cantonDao;
    
    @EJB
    private TipoLibroDao servicioTipoLibro;
    
    @EJB
    private TipoTramiteDao servicioTipoTramite;
    @EJB
    private TramiteUsuarioDao servicioTramiteUsuario;
    
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
    
    public ControladorCancelacion() {
        
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
        renderedPanel = true;
        renderedPanelTab = false;
        deshabilitarTabActa = true;
        deshabilitarTabDatosGen = true;
        deshabilitarTabRazon = true;
        renderedBtnSuspension = false;
        siDialogoGravDetalle = false;
        auxIdPersona = new Long(0);
        aux = 0;
        renderedTabRazon = false;
        renderedTabActa = false;
        renderedTabDatosGen = false;
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
        listaPropiedadPorMatricula.clear();
        listaGrav.clear();
        listaGravamenSeleccionados.clear();
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
        setPreListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId(), "INC"));
        listaRepertorioFecha.clear();
        for (Repertorio repertorio1 : preListaRepertorioFecha) {
            
            Tramite tramit = new Tramite();
            tramit = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());
            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramit.getTraNumero().longValue()) && !tramit.getTraEstadoRegistro().trim().equals("RZ")) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorio1.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INC");
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
        setPreListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId(), "INC"));
        for (Repertorio repertorio1 : preListaRepertorioFecha) {
            
            Tramite tramit = new Tramite();
            tramit = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());
            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramit.getTraNumero().longValue()) && !tramit.getTraEstadoRegistro().trim().equals("RZ")) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorio1.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INC");
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
        listaPropDetSeleccionadas.clear();
        auxIdPersona = new Long(0);
        listaGravamenesCancelar.clear();
        listaGravSelecAcum.clear();
        aux = 1;
        listaIdPersona.clear();
        listaIdPersona.add(new Long(0));
        listaIdPerGravDet.clear();
        listaIdGrav.clear();
        listaIdPerGravDet.add(new Long(0));
        
        listaGravamenDetCancelar.clear();
        listaGravamenSeleccionados.clear();
        renderedTabView = true;
        
        if (tipoTramite.getTtrCodigo() == 13) {
            mostrarListaGravamenesFideicomiso();
        } else {
            mostrarListaGravamenes();
        }
        
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
            
            listaGrav.clear();
            
            List<Gravamen> listaPreviaGravamen = new ArrayList<>();
            EquivalenciaTramite equivTram;
            List<Propietario> listaPropt = new ArrayList<>();
            listaPropt = propietarioDao.listarPor_Id_Persona(idPersona);
            
            for (Propietario propietario : listaPropt) {
                listaPreviaGravamen = gravamenDao.buscarPorMatricula(propietario.getPrdMatricula());
            }
            listaGrav.addAll(listaPreviaGravamen);
//            for (Gravamen gravamen : listaPreviaGravamen) {
//                equivTram = new EquivalenciaTramite();
//                equivTram = equivalenciaTramiteDao.buscarPorTipoTramiteId1_PorTipoTramiteId2(Long.valueOf(repertorio.getRepTtrId()), Long.valueOf(gravamen.getRepNumero().getRepTtrId()));
//                if (equivTram != null) {
//                    listaGrav.add(gravamen);
//                }
//            }

            List<GravamenDetalle> gravDetalle = new ArrayList<>();
            for (Gravamen gravamen : listaGrav) {
                gravDetalle.clear();
                gravDetalle = gravamenDetalleDao.listarPorGravamenId_PorIdPersona(idPersona, gravamen.getGraId());
                if (gravDetalle.isEmpty()) {
                    gravamen.setExisteDetalleGrav(false);
                } else {
                    gravamen.setExisteDetalleGrav(true);
                }
                
            }
            
            if (!listaIdPersona.contains(idPersona)) {
                listaGravamenSeleccionados.clear();
                for (Gravamen gravamen : listaGrav) {
                    TramiteValor tramValor = tramiteValorDao.obtenerPor_NumTramite_Propiedad(
                            Long.valueOf(numeroTramite), gravamen.getPrdMatricula().getPrdPredio(), gravamen.getPrdMatricula().getPrdCatastro());
                    if (tramValor != null && gravamen.isExisteDetalleGrav() == false) {
                        listaGravamenSeleccionados.add(gravamen);
                    }
                    
                }
            }
            
            listaIdPersona.add(idPersona);
            aux++;

            /**
             *
             */
            listaPropiedad = propieadadDao.listarPropiedad_ConMatricula_DistintaCero_Por_Propietario(idPersona);
//            for (Propiedad propiedad : listaPropiedad) {
//                TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(Long.valueOf(numeroTramite), new Long(propiedad.getPrdPredio()), new Long(propiedad.getPrdCatastro()));
//                if (tramiteValorPorPropiedad != null) {
//                    listaPropiedadesSeleccionadas.add(propiedad);
//                }
//            }

        } catch (Exception e) {
        }
    }
    
    public void mostrarGravamenes() throws ServicioExcepcion {
        try {
            //tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numeroTramite), descripcionContrato);
            //tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());

            //listaGravamenesComp.clear();
            if (siDialogoGravDetalle == false) {
                listaGravamenDetSeleccionados.clear();
                for (Gravamen gravamen : listaGrav) {
                    if (gravamen.isExisteDetalleGrav() == true) {
                        listaGravamenDetalle = gravamenDetalleDao.listarPorGravamenId_PorIdPersona(idPersonaGravamen, gravamen.getGraId());
                        for (GravamenDetalle gravDetalle : listaGravamenDetalle) {
                            listaGravamenDetSeleccionados.add(gravDetalle);
                        }
                    }
                    
                }
            }
            siDialogoGravDetalle = false;

            /**
             * ***************************************************************************
             */
            for (Gravamen listaGravSelec : listaGravamenSeleccionados) {
                if (!listaGravamenesComp.contains(listaGravSelec)) {
                    listaGravamenesComp.add(listaGravSelec);
                }
                if (!listaGravSelecAcum.contains(listaGravSelec)) {
                    listaGravSelecAcum.add(listaGravSelec);
                }
                
            }
            
            for (Gravamen gravamen : listaGrav) {
                for (GravamenDetalle listaGravDetSelec : listaGravamenDetSeleccionados) {
                    if (listaGravDetSelec.getGraId().getGraId().longValue() == gravamen.getGraId().longValue()) {
                        if (!listaGravamenesComp.contains(gravamen)) {
                            listaGravamenesComp.add(gravamen);
                        }
                        
                    }
                }
            }
//            if(auxIdPersona.longValue() != idPersonaGravamen.longValue()){ 
//                
//                listaGravamenesCancelar.addAll(listaGravamenesComp);
//                
//            }
//            else{
//                listaGravamenesCancelar.clear();
//                listaGravamenesCancelar.addAll(listaGravamenesComp);
//                
//            }
            auxIdPersona = idPersonaGravamen;

            /**/
            for (Gravamen gravamen : listaGravamenesComp) {
                if (!listaGravamenesCancelar.contains(gravamen)) {
                    listaGravamenesCancelar.add(gravamen);
                } else {

                    //            if(!listaGravamenesComp.isEmpty()){
                    for (Gravamen grav : listaGravamenesCancelar) {
                        if (!listaGravamenesComp.contains(grav)) {
                            listaGravamenesCancelar.remove(grav);
                        }
                        
                    }
                    //}

                }
                
            }
            
            if (listaGravamenSeleccionados.isEmpty()) {
                for (Gravamen gravamn : listaGrav) {
                    if (listaGravamenesCancelar.contains(gravamn) && gravamn.isExisteDetalleGrav() == false) {
                        listaGravamenesCancelar.remove(gravamn);
                    }
                }
            }
            
        } catch (Exception e) {
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

        //List<String> listaParroquias = new ArrayList<>();
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
    
    public void procesoTerminar() throws ServicioExcepcion, ParseException {
        Tramite tramite = new Tramite();
        tramite = tramiteDao.buscarTramitePorNumero(Long.valueOf(numeroTramite));
        renderedPanel = false;
        renderedPanelTab = true;
        cambiarEstadoGravamenes();
        if (tramite.getTraClase().equals("J")) {
            
            deshabilitarTabDatosGen = false;
            deshabilitarTabActa = true;
            deshabilitarTabRazon = true;
            activeIndex = 0;
            renderedTabRazon = false;
            renderedTabActa = false;
            renderedTabDatosGen = true;
            
        } else if (tramite.getTraClase().equals("E")) {
            guardarActa();
            deshabilitarTabDatosGen = true;
            deshabilitarTabActa = false;
            deshabilitarTabRazon = true;
            activeIndex = 1;
            renderedTabRazon = false;
            renderedTabActa = true;
            renderedTabDatosGen = false;
            
        }
        
    }
    
    public void cargarListaProvinciaTipoJucio() throws ServicioExcepcion {
        
        listaTipoJuicio = tipoJuicioServicio.listarTodo();
        listaParroquia = parroquiaServicio.listarTodo();
        
    }
    
    public void cargarDatosGenerales() throws ServicioExcepcion {
        
        tramite = tramiteServicio.buscarTramitePorNumero(Long.valueOf(numeroTramite));
        
        if (tramite.getTraJucio() == null) {
            numeroJuicio = 0;
        } else {
            numeroJuicio = tramite.getTraJucio();
        }
        if (tramite.getTraNumeroJusgado() == null) {
            juzgado = 0;
        } else {
            juzgado = tramite.getTraNumeroJusgado();
        }
        
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
//                actaNueva.setActActa(" ");
//                actaNueva.setActActaPdf(" ");
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
            actaNueva.setActParroquia(nombreParroquia);
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
            Tramite tramite = new Tramite();
            tramite = tramiteDao.buscarTramitePorNumero(Long.valueOf(numeroTramite));
            if (tramite.getTraClase().equals("J")) {
                if (actaEncontrada != null) {
                    if (!fechaDe.after(fechaSentencia) && !fechaDe.after(fechaIngreso) && !fechaSentencia.after(fechaIngreso)) {
                        actaServicio.edit(actaNueva);
                        repertorioUsuarioServicio.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(numeroRepertorio), "INC");
                        acta();
                        //razon();
                        llenarDatosActualizar();
                        mostrarTabActa();
                        
                    } else {
                        
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La Fecha Auto debe ser menor o igual a la Fecha de Sentencia. La fecha de Sentencia debe ser menor o igual a la Fecha de Ingreso", ""));
                    }
                    
                } else {
                    if (!fechaDe.after(fechaSentencia) && !fechaDe.after(fechaIngreso) && !fechaSentencia.after(fechaIngreso)) {
                        actaServicio.create(actaNueva);
                        repertorioUsuarioServicio.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(numeroRepertorio), "INC");
                        acta();
                        //razon();
                        //llenarDatos();
                        llenarDatosActualizar();
                        mostrarTabActa();
                    } else {
                        
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La Fecha Auto debe ser menor o igual a la Fecha de Sentencia. La fecha de Sentencia debe ser menor o igual a la Fecha de Ingreso", ""));
                    }
                    
                }
                
            } else if (tramite.getTraClase().equals("E")) {
                if (actaEncontrada != null) {
                    
                    actaServicio.edit(actaNueva);
                    repertorioUsuarioServicio.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(numeroRepertorio), "INC");
                    
                    acta();
                    //razon();
                    //llenarDatos();
                    llenarDatosActualizar();
                    mostrarTabActa();
                    
                } else {
                    
                    actaServicio.create(actaNueva);
                    acta();
                    //razon();
                    //llenarDatos();
                    llenarDatosActualizar();
                    mostrarTabActa();
                    
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
        renderedTabView = true;
        activeIndex = 0;
        
    }
    
    public void acta() throws ServicioExcepcion {
        
        tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
        
        Tramite tramite = new Tramite();
        tramite = tramiteDao.buscarTramitePorNumero(Long.valueOf(numeroTramite));
        String juicioPenalActa = "";
        String numeroJuicioActa = "";
        String fechaDeActa = "";
        String nombreDemandante = "";
        String nombreDemandado = "";
        
        institucion = institucionServicio.obtenerInstitucion();
        canton = cantonDao.encontrarCantonPorId(institucion.getInsCanId().toString());
        
        String tituloActa = "<h3 align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + canton.getCanNombre().toUpperCase() + "</strong></h3>";
        String linea2Acta = "<h4 align='center'><strong>Acta de Inscripción</strong></h4>";
        String contratoActa = "<h4 align='center'><strong>" + descripcionContrato + "</strong></h4>";
        String numInscripcion = "<strong  >Nro. de Inscripción: </strong> &nbsp;&nbsp;&nbsp;" + "<br>";
        String numTramiteActa = "<strong>Nro. de Trámite: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + numeroTramite + "<br>";
        String numRepertorioActa = "<strong>Nro. de Repertorio: </strong>&nbsp;&nbsp;&nbsp;&nbsp;" + numeroRepertorio + "<br>";
        String txtFecha = "" + "<strong>Fecha: </strong>" + UtilTexto.generarEspaciosEnBlanco(25) + FechasUtil.convertirFechaA_ddMMAAAA_hhmmss(repertorio.getRepFHR()) + "<br>";
        String txtLibro = "" + "<strong>Libro: </strong>" + UtilTexto.generarEspaciosEnBlanco(27) + servicioTipoLibro.encontrarPor_Repertorio(repertorio.getRepNumero()).getTplDescripcion() + "<br>";
        String txtContrato = "" + "<strong>Tipo de Contrato: </strong>" + UtilTexto.generarEspaciosEnBlanco(7) + repertorio.getRepTtrDescripcion() + "<br>";
        String parroquiaActa = "<strong>Parroquia: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + nombreParroquia.toUpperCase() + "<br>";
//        String tomoActa = "<strong>Tomo: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + tipoLibro.getTplTomo() + "<br>";
//        String fechaActa = "<strong>Fecha: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + fechasUtil.convertirFechaA_ddMMAAAA_hhmmss(fechaIngreso) + "<br>";

        cabeceraActa = "";
        for (int i = 0; i < numSaltosLinea; i++) {
            cabeceraActa += "<br>";
        }
        
        cabeceraActa += "<strong>" + tituloActa + "</strong><br>"
                + "<strong>" + linea2Acta + "</strong><br>" + "<strong>" + contratoActa + "</strong><br>"
                + numInscripcion + numTramiteActa
                + numRepertorioActa + txtFecha + txtLibro + parroquiaActa + txtContrato
                + "<br></br>";
        
        if (tramite.getTraClase().equals("J")) {
            tipoJuicio = new TipoJuicio();
            tipoJuicio = tipoJuicioServicio.encontrarTipoJuicioPorId(tipoJuicioId);
            tipoJuicioDescripcion = tipoJuicio.getTpjDescripcion();
            juicioPenalActa = tipoJuicioDescripcion;
            fechaDeActa = fechasUtil.convertirFecha_A_letras(fechaDe);;
            numeroJuicioActa = (transformadorNumerosLetrasUtil.transformador(String.valueOf(juzgado))).toString();
            
        } else {
            tipoJuicioDescripcion = "";
        }
        
        String fechaIngresoLetras = fechasUtil.convertirFecha_A_letras(fechaIngreso);
        String horaIngresoLetras = fechasUtil.convertirHora_A_letras(fechaIngreso);
        String descripcionParroquiaActa = descripcionParroquia;
        String juzgadoActa = "";
        
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
        
        if (tramite.getTraClase().equals("J")) {
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
            
        } else if (tramite.getTraClase().equals("E")) {
            try {
                
                String comparecientes = "";
                for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {
                    comparecientes = comparecientes + tramiteDetalle1.getTdtPerNombre().trim() + " "
                            + tramiteDetalle1.getTdtPerApellidoPaterno().trim() + " "
                            + tramiteDetalle1.getTdtPerApellidoMaterno().trim() + " "
                            + tramiteDetalle1.getTdtTpcDescripcion().trim() + "<br></br>";
                }
                for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompS) {
                    comparecientes = comparecientes + tramiteDetalle1.getTdtPerNombre().trim() + " "
                            + tramiteDetalle1.getTdtPerApellidoPaterno().trim() + " "
                            + tramiteDetalle1.getTdtPerApellidoMaterno().trim() + " "
                            + tramiteDetalle1.getTdtTpcDescripcion().trim() + "<br></br>";
                }
                if (cuerpoActa == null && cuerpoActa.isEmpty()) {
                    cuerpoActa = comparecientes;
                }
                
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
        
        if (bolAgregarImagenEspacioEnBlancoActa) {
            cuerpoActa += "<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        } else {
            cuerpoActa = cuerpoActa.replace("<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>", "");
        }
        
        Persona responsable = servicioTramiteUsuario.buscarPorTramite(repertorio.getTraNumero()).getUsuId().getPerId();
        Persona inscriptor = getDataManagerSesion().getUsuarioLogeado().getPerId();
        String pieActa = ""
                + "<br><br><br>"
                + "<strong>RESPONSABLES:</strong>"
                + "<strong>REVISOR: </strong>" + responsable.getPerApellidoPaterno() + " " + responsable.getPerApellidoMaterno() + " " + responsable.getPerNombre() + "<br>"
                + "<strong>INSCRIPTOR(A): </strong>" + inscriptor.getPerApellidoPaterno() + " " + inscriptor.getPerApellidoMaterno() + " " + inscriptor.getPerNombre() + "<br>"
                + "<strong>ANALISTA 3: </strong>" + "<br>";
        actaDocumento = cabeceraActa + cuerpoActa + pieActa;
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
                        + act.getActTomo() + ", repertorio(s)- " + repertor.getRepNumero() + ".<br><br>";

                //fechaRazon = fechasUtil.convertirFechaA_diaSemana_ddMMAAAA_hhmmss(repertor.getRepFHR());
                listTramDet = tramiteDetalleDao.listarPorNumTramiteYporIdTipoTramite(
                        repertor.getTraNumero().getTraNumero(), repertor.getRepTtrId());
                listaTramDetComparecientes.addAll(listTramDet);
            }
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
        
        String lineaCompS = "";
        String lineaCompN = "";
        
        TramiteDetalle tramDet1 = listaTramDetalleComparS.get(0);
        if (tramDet1.getTtcId().getTtcPropietario().equals("S")) {
            lineaCompS = tramDet1.getTdtTpcDescripcion();
        }
        TramiteDetalle tramDet2 = listaTramDetalleComparN.get(0);
        if (tramDet2.getTtcId().getTtcPropietario().equals("N")) {
            lineaCompN = tramDet2.getTdtTpcDescripcion();
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
                + linea5Razon + "<br></br>" + linea6Razon + "<br><br><br>"
                + nombreComparecienteRazon + "<br>"
                + lineaCompS + "<br></br><br></br><br></br>"
                + nombreDemandadoRazon + "<br></br>"
                + lineaCompN + "<br></br><br></br><br></br>" + linea9Razon + "<br></br><br></br><br></br>"
                + nombreResponsable;
        if (bolAgregarImagenEspacioEnBlancoRazon) {
            txtRazon += "<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        } else {
            txtRazon = txtRazon.replace("<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>", "");
        }
        generarPdfRazon();
        
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
            //cancelarGravamenes();
            deshabilitarTabActa = true;
            deshabilitarTabDatosGen = true;
            deshabilitarTabRazon = false;
            activeIndex = 2;
            renderedPanel = false;
            renderedPanelTab = false;
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

            //cancelarGravamenes();  
            renderedBtnSuspension = false;
            listaGrav.clear();
            
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
                        dataManagerSesion.getUsuarioLogeado().getUsuId(), "INC");
                
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
    
    public void guardarGravamen() {
        
        try {
            
            listaTramiteValor = tramiteValorDao.listarPorNumTramitePorTipoTramite(Long.valueOf(numeroTramite), tipoTramite.getTtrId());
            List<Propiedad> listaProp = new ArrayList<>();
            for (TramiteValor tramValor : listaTramiteValor) {
                Propiedad prop = new Propiedad();
                prop = propieadadDao.buscarPropiedadPor_predio_o_catastro(tramValor.getTraNumPredio().toString(), tramValor.getTrvNumCatastro().toString());
                if (prop != null) {
                    listaProp.add(prop);
                }
                
            }

            /**
             * *************** ACTUALIZAR GRAVAMEN Y PROPIEDAD DETALLE
             * (DERECHOS Y ACCIONES) **************
             */
            List<Propietario> listaPropietario = new ArrayList<>();
            List<Gravamen> listaGravamen = new ArrayList<>();
            List<PropiedadDetalle> listaPropDet = new ArrayList<>();
            for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {
                listaPropietario.clear();
                listaPropietario = propietarioDao.listarPornumRepYPerId(tramiteDetalle1.getPerId().getPerId(), Integer.valueOf(numeroRepertorio));
                for (Propietario propietario : listaPropietario) {
                    //GRAVAMEN
                    listaGravamen.clear();
                    listaGravamen = gravamenDao.buscarPorMatriculaYRepertorio(Integer.valueOf(numeroRepertorio), propietario.getPrdMatricula().getPrdMatricula());
                    for (Gravamen gravamen : listaGravamen) {
                        gravamen.setGraEstado("I");
                        gravamenDao.edit(gravamen);
                    }
                    //PROPIEDAD DETALLE
                    listaPropDet.clear();
                    listaPropDet = propiedadDetalleServicio.listar_Por_Matricula(propietario.getPrdMatricula().getPrdMatricula());
                    for (PropiedadDetalle propiedadDetalle : listaPropDet) {
                        propiedadDetalle.setPdtEstado("I");
                        propiedadDetalleServicio.edit(propiedadDetalle);
                    }
                }
            }
            
        } catch (Exception e) {
            System.out.println(e);
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
    
    public void cargarPropiedadDetalle(String numMatricula) throws ServicioExcepcion {
        listaPropDetSeleccionadas.clear();
        listaPropiedadDetalle = propiedadDetalleServicio.listar_Por_Matricula_PorEstadoActivo(numMatricula);
        for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
            Persona persona = personaServicio.buscarPersonaPorId(propiedadDetalle.getPdtPerId().longValue());
            propiedadDetalle.setPersona(persona);
            Long perId = propiedadDetalle.getPdtPerId().longValue();
            if (perId.longValue() == idPersona.longValue()) {
                listaPropDetSeleccionadas.add(propiedadDetalle);
            }
        }
        
    }

    /* BUSCAR LOS GRAVAMENES (CASO FIDEICOMISO)PERTENECIENTES A LOS COMPARECIENTES PARA USARSE EN EL METODO CANCELAR GRAVAMENES */
    public void buscarGravamenesFideicomiso(Tramite tramiteSeleccionado) throws ServicioExcepcion {
        List<TramiteDetalle> listTramDetalle = new ArrayList<>();
        listaGravAuxiliar.clear();
        for (Repertorio repertor : listaRepertorioFecha) {
            if (repertor.getTraNumero().getTraNumero().longValue() == tramiteSeleccionado.getTraNumero().longValue()) {
                listTramDetalle.clear();
                listTramDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                        repertor.getTraNumero().getTraNumero(), repertor.getRepTtrId(), repertor.getRepNumero());
                for (TramiteDetalle tramiteDetalle1 : listTramDetalle) {
                    if (tramiteDetalle1.getTtcId().getTtcPropietario().equals("S")) {                        
                        List<Gravamen> listaPreviaGravamen = new ArrayList<>();
                        List<Gravamen> listaGravm = new ArrayList<>();
                        listaGravm.clear();
                        List<Propietario> propietario = new ArrayList<>();
                        propietario = propietarioDao.listarPor_Id_Persona(tramiteDetalle1.getPerId().getPerId());
                        
                        List<Gravamen> listAuxGravamen = new ArrayList<>();
                        for (Propietario propietario1 : propietario) {
                            listAuxGravamen.clear();
                            listAuxGravamen = gravamenDao.listarPorMatriculaPorEstadoR_EstadoP(propietario1.getPrdMatricula());
                            for (Gravamen gravamen : listAuxGravamen) {
                                if (!listaGravm.contains(gravamen)) {
                                    listaGravm.add(gravamen);
                                }
                            }
                            {
                                
                            }
                            
                        }
                        for (Gravamen gravamen : listaGravm) {
                            if (!listaPreviaGravamen.contains(gravamen)) {
                                listaPreviaGravamen.add(gravamen);
                            }
                        }
                        
                        listaGravAuxiliar.addAll(listaPreviaGravamen);
                    }
                }
            }
            
        }
    }


    /* BUSCAR LOS GRAVAMENES PERTENECIENTES A LOS COMPARECIENTES PARA USARSE EN EL METODO CANCELAR GRAVAMENES */
    public void buscarGravamenes(Tramite tramiteSeleccionado) throws ServicioExcepcion {
        List<TramiteDetalle> listTramDetalle = new ArrayList<>();
        listaGravAuxiliar.clear();
        for (Repertorio repertor : listaRepertorioFecha) {
            if (repertor.getTraNumero().getTraNumero().longValue() == tramiteSeleccionado.getTraNumero().longValue()) {
                listTramDetalle.clear();
                listTramDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                        repertor.getTraNumero().getTraNumero(), repertor.getRepTtrId(), repertor.getRepNumero());
                for (TramiteDetalle tramiteDetalle1 : listTramDetalle) {
                    List<Gravamen> listaPreviaGravamen = new ArrayList<>();
                    EquivalenciaTramite equivTram;
                    List<Gravamen> listaGravm = new ArrayList<>();
                    listaGravm.clear();
                    
                    if (tramiteDetalle1.getTtcId().getTtcPropietario().equals("S")) {
                        List<Propietario> propietario = new ArrayList<>();
                        propietario = propietarioDao.listarPor_Id_Persona(tramiteDetalle1.getPerId().getPerId());
                        if (!propietario.isEmpty()) {
                            List<GravamenDetalle> listGravDet = new ArrayList<>();
                            listGravDet.clear();
                            listGravDet = gravamenDetalleDao.listarPorIdPersona(tramiteDetalle1.getPerId().getPerId());
                            
                        } else {
                            Gravamen grav = new Gravamen();
                            grav = gravamenDao.buscarGrav_PorIdPersona_EstadoA_EstadoP(tramiteDetalle1.getPerId().getPerId());
                            if (grav != null) {
                                if (!listaPreviaGravamen.contains(grav)) {
                                    listaPreviaGravamen.add(grav);
                                }
                            }
                        }
                        List<Gravamen> listAuxGravamen = new ArrayList<>();
                        for (Propietario propietario1 : propietario) {
                            listAuxGravamen.clear();
                            listAuxGravamen = gravamenDao.buscarPorMatriculaPorEstado(propietario1.getPrdMatricula());
                            for (Gravamen gravamen : listAuxGravamen) {
                                if (!listaGravm.contains(gravamen)) {
                                    listaGravm.add(gravamen);
                                }
                            }
                            {
                                
                            }
                            
                        }
                        for (Gravamen gravamen : listaGravm) {
                            if (!listaPreviaGravamen.contains(gravamen)) {
                                listaPreviaGravamen.add(gravamen);
                            }
                        }
                    }
                    listaGravAuxiliar.addAll(listaPreviaGravamen);
                }
            }
            
        }
    }

    /* MUESTRA EL GRAVAMEN DETALLE (SI ES QUE TIENE)DEL GRAVAMEN SELECCIONADO*/
    public void mostrarGravamenDetalle(Long idGravamen) {
        listaGravamenDetalle.clear();
        List<GravamenDetalle> listGrav = new ArrayList<>();
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompS) {
            listGrav = gravamenDetalleDao.listarPorGravamenId_PorIdPersona_PorEstadosAP(tramiteDetalle1.getPerId().getPerId(), idGravamen);
            listaGravamenDetalle.addAll(listGrav);
        }
        for (GravamenDetalle gravamenDetalle : listaGravamenDetalle) {
            if (listaGravamenDetCancelar.contains(gravamenDetalle)) {
                listaGravamenDetSeleccionados.add(gravamenDetalle);
            }
            if (gravamenDetalle.getGvdEstado().equals("P")) {
                listaGravamenDetSeleccionados.add(gravamenDetalle);
            }
        }
        
    }

    /* CARGA LA LISTA DE GRAVAMENES PERTENECIENTES A LOS COMPARECIENTES  */
    public void mostrarListaGravamenesFideicomiso() throws ServicioExcepcion {
        listaGrav.clear();
        List<Gravamen> listaPreviaGravamen = new ArrayList<>();
        
        List<Gravamen> listaGravm = new ArrayList<>();
        listaGravm.clear();
        for (TramiteDetalle tramiteDetalle1 : listaTramiteDetalle) {
            List<Propietario> propietario = new ArrayList<>();
            propietario = propietarioDao.listarPor_Id_Persona(tramiteDetalle1.getPerId().getPerId());
            
            List<Gravamen> listAuxGravamen = new ArrayList<>();
            for (Propietario propietario1 : propietario) {
                if (propietario1.getPrdFideicomiso() != null) {                    
                    
                    if (propietario1.getPrdFideicomiso().equals("S")) {
                        listAuxGravamen.clear();
                        listAuxGravamen = gravamenDao.listarPorMatriculaPorEstadoR_EstadoP(propietario1.getPrdMatricula());
                        for (Gravamen gravamen : listAuxGravamen) {
                            if (!listaPreviaGravamen.contains(gravamen)) {
                                listaPreviaGravamen.add(gravamen);
                            }
                        }
                    }
                }
                
            }
            
        }
        listaGrav.addAll(listaPreviaGravamen);
        
        List<GravamenDetalle> gravDetalle = new ArrayList<>();
        for (Gravamen gravamen : listaGrav) {
            
            gravDetalle.clear();
            gravDetalle = gravamenDetalleDao.listarPorGravamenId(gravamen.getGraId());
            if (gravDetalle.isEmpty()) {
                gravamen.setExisteDetalleGrav(false);
            } else {
                gravamen.setExisteDetalleGrav(true);
            }
        }

        //listaGravamenSeleccionados.clear();
        for (Gravamen gravamen : listaGrav) {
            if (gravamen.getGraEstado().equals("R")) {
                TramiteValor tramValor = tramiteValorDao.obtenerPor_NumTramite_Propiedad(
                        Long.valueOf(numeroTramite), gravamen.getPrdMatricula().getPrdPredio(), gravamen.getPrdMatricula().getPrdCatastro());
                if (tramValor != null && gravamen.isExisteDetalleGrav() == false) {
                    listaGravamenSeleccionados.add(gravamen);
                }
                
            }
            
            if (gravamen.getGraEstado().equals("P")) {
                listaGravamenSeleccionados.add(gravamen);
            }
            
        }
        
    }

    /* CARGA LA LISTA DE GRAVAMENES PERTENECIENTES A LOS COMPARECIENTES  */
    public void mostrarListaGravamenes() throws ServicioExcepcion {
        listaGrav.clear();
        List<Gravamen> listaPreviaGravamen = new ArrayList<>();
        EquivalenciaTramite equivTram;
        List<Gravamen> listaGravm = new ArrayList<>();
        listaGravm.clear();
        for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompS) {
            List<Propietario> propietario = new ArrayList<>();
            propietario = propietarioDao.listarPor_Id_Persona(tramiteDetalle1.getPerId().getPerId());
            if (!propietario.isEmpty()) {
                List<GravamenDetalle> listGravDet = new ArrayList<>();
                listGravDet = gravamenDetalleDao.listarPorIdPersona(tramiteDetalle1.getPerId().getPerId());
//                for (Propietario propietario1 : propietario) {
//                    for (GravamenDetalle gravamenDet : listGravDet) {
//                        if (gravamenDet.getGraId().getPrdMatricula().getPrdMatricula().equals(propietario1.getPrdMatricula().getPrdMatricula())) {
//                            if (!listaPreviaGravamen.contains(gravamenDet.getGraId())) {
//                                //listaPreviaGravamen.add(gravamenDet.getGraId())
//                            }
//                        }
//
//                    }
//
//                }

            } else {
                Gravamen grav = new Gravamen();
                grav = gravamenDao.buscarGrav_PorIdPersona_EstadoA_EstadoP(tramiteDetalle1.getPerId().getPerId());
                if (grav != null) {
                    if (!listaPreviaGravamen.contains(grav)) {
                        listaPreviaGravamen.add(grav);
                    }
                }
            }
            List<Gravamen> listAuxGravamen = new ArrayList<>();
            for (Propietario propietario1 : propietario) {
                listAuxGravamen.clear();
                listAuxGravamen = gravamenDao.buscarPorMatriculaPorEstado(propietario1.getPrdMatricula());
                for (Gravamen gravamen : listAuxGravamen) {
                    if (!listaGravm.contains(gravamen)) {
                        listaGravm.add(gravamen);
                    }
                }
                {
                    
                }
                
            }
            for (Gravamen gravamen : listaGravm) {
                if (!listaPreviaGravamen.contains(gravamen)) {
                    listaPreviaGravamen.add(gravamen);
                }
            }
            //listaPreviaGravamen.addAll(listaGravm);
        }
        listaGrav.addAll(listaPreviaGravamen);

//        for (Gravamen gravamen : listaPreviaGravamen) {
//            equivTram = new EquivalenciaTramite();
//            equivTram = equivalenciaTramiteDao.buscarPorTipoTramiteId1_PorTipoTramiteId2(Long.valueOf(repertorio.getRepTtrId()), Long.valueOf(gravamen.getRepNumero().getRepTtrId()));
//            if (equivTram != null) {
//                listaGrav.add(gravamen);
//            }
//        }
        List<GravamenDetalle> gravDetalle = new ArrayList<>();
        for (Gravamen gravamen : listaGrav) {
            
            gravDetalle.clear();
            gravDetalle = gravamenDetalleDao.listarPorGravamenId(gravamen.getGraId());
            if (gravDetalle.isEmpty()) {
                gravamen.setExisteDetalleGrav(false);
            } else {
                gravamen.setExisteDetalleGrav(true);
            }
        }

        //listaGravamenSeleccionados.clear();
        for (Gravamen gravamen : listaGrav) {
            if (gravamen.getGraEstado().equals("A")) {
                TramiteValor tramValor = tramiteValorDao.obtenerPor_NumTramite_Propiedad(
                        Long.valueOf(numeroTramite), gravamen.getPrdMatricula().getPrdPredio(), gravamen.getPrdMatricula().getPrdCatastro());
                if (tramValor != null && gravamen.isExisteDetalleGrav() == false) {
                    listaGravamenSeleccionados.add(gravamen);
                }
                
            }
            
            if (gravamen.getGraEstado().equals("P")) {
                listaGravamenSeleccionados.add(gravamen);
            }
//            else {
//                listaGravamenDetalle.clear();
//                List<GravamenDetalle> listGrav = new ArrayList<>();
//                for (TramiteDetalle tramiteDetalle1 : listaTramDetalleCompN) {
//                    listGrav = gravamenDetalleDao.listarPorGravamenId_PorIdPersona(tramiteDetalle1.getPerId().getPerId(), gravamen.getGraId());
//                    listaGravamenDetalle.addAll(listGrav);
//                }
//                listaGravamenDetSeleccionados.addAll(listaGravamenDetalle);
//            }

        }
        
    }
    
    public void agregarListaGravSeleccionado() {
        
        for (GravamenDetalle gravamenDetalle : listaGravamenDetalle) {
            
            if (!listaGravamenDetSeleccionados.contains(gravamenDetalle)) {
                listaGravamenDetCancelar.remove(gravamenDetalle);
            }
            
        }
        
        for (GravamenDetalle listaGravDetSelec : listaGravamenDetSeleccionados) {
            
            if (!listaGravamenDetCancelar.contains(listaGravDetSelec)) {
                listaGravamenDetCancelar.add(listaGravDetSelec);
            }
        }
        if (listaGravamenDetSeleccionados.isEmpty()) {
            for (GravamenDetalle gravamenDetalle : listaGravamenDetalle) {
                listaGravamenDetCancelar.remove(gravamenDetalle);
            }
            
        }
        
    }
    
    public void cambiarEstadoGravamenesFideicomiso() {
        //reseteo el estado (Estado=R) de Gravamen y GravamenDetalle 
        List<GravamenDetalle> gravDetalle = new ArrayList<>();
        for (Gravamen grav : listaGrav) {
            gravDetalle.clear();
            grav.setGraEstado("R");
            gravamenDao.edit(grav);
            gravDetalle = gravamenDetalleDao.listarPorGravamenId(grav.getGraId());
            for (GravamenDetalle gravamenDetalle : gravDetalle) {
                gravamenDetalle.setGvdEstado("R");
                gravamenDetalleDao.edit(gravamenDetalle);
            }
        }

        //Cambiar de estado los gravamenes seleccionados
        for (Gravamen gravSelec : listaGravamenSeleccionados) {
            gravDetalle.clear();
            gravDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(gravSelec.getGraId(), "R");
            if (gravDetalle.isEmpty()) {
                gravSelec.setGraEstado("P");
                gravamenDao.edit(gravSelec);
            }
            
        }

        //CAMBIAR ESTADO GRAVAMEN DETALLE Y LUEGO ACTUALIZAR ESTADO GRAVAMEN EN CASO QUE SE HAY PUESTO EN ESTADO "P" UN GRAVAMEN QUE AUN TIENE GRAVAMEN DETALLE ACTIVO
        for (GravamenDetalle listaGravDetSelec : listaGravamenDetCancelar) {
            listaGravDetSelec.setGvdEstado("P");
            gravamenDetalleDao.edit(listaGravDetSelec);
            Gravamen grav = new Gravamen();
            grav = listaGravDetSelec.getGraId();
            gravDetalle.clear();
            gravDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(grav.getGraId(), "R");
            if (gravDetalle.isEmpty()) {
                grav.setGraEstado("P");
                gravamenDao.edit(grav);
            } else {
                grav.setGraEstado("R");
                gravamenDao.edit(grav);
            }
            
        }
        
    }
    
    public void cambiarEstadoGravamenes() {
        //reseteo el estado (Estado=A) de Gravamen y GravamenDetalle 
        List<GravamenDetalle> gravDetalle = new ArrayList<>();
        for (Gravamen grav : listaGrav) {
            gravDetalle.clear();
            grav.setGraEstado("A");
            gravamenDao.edit(grav);
            gravDetalle = gravamenDetalleDao.listarPorGravamenId(grav.getGraId());
            for (GravamenDetalle gravamenDetalle : gravDetalle) {
                gravamenDetalle.setGvdEstado("A");
                gravamenDetalleDao.edit(gravamenDetalle);
            }
        }

        //Cambiar de estado los gravamenes seleccionados
        for (Gravamen gravSelec : listaGravamenSeleccionados) {
            gravDetalle.clear();
            gravDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(gravSelec.getGraId(), "A");
            if (gravDetalle.isEmpty()) {
                gravSelec.setGraEstado("P");
                gravamenDao.edit(gravSelec);
            }
            
        }

        //CAMBIAR ESTADO GRAVAMEN DETALLE Y LUEGO ACTUALIZAR ESTADO GRAVAMEN EN CASO QUE SE HAY PUESTO EN ESTADO "P" UN GRAVAMEN QUE AUN TIENE GRAVAMEN DETALLE ACTIVO
        for (GravamenDetalle listaGravDetSelec : listaGravamenDetCancelar) {
            listaGravDetSelec.setGvdEstado("P");
            gravamenDetalleDao.edit(listaGravDetSelec);
            Gravamen grav = new Gravamen();
            grav = listaGravDetSelec.getGraId();
            gravDetalle.clear();
            gravDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(grav.getGraId(), "A");
            if (gravDetalle.isEmpty()) {
                grav.setGraEstado("P");
                gravamenDao.edit(grav);
            } else {
                grav.setGraEstado("A");
                gravamenDao.edit(grav);
            }
            
        }
        
    }
    
    public String mensajeElementosCancelar() {
        int cantGrav = listaGravamenSeleccionados.size();
        int cantGravDet = listaGravamenDetCancelar.size();
        mensajeCancelar = "Se han seleccionado " + cantGrav + " Gravamenes y "
                + cantGravDet + " Detalles de Gravamen para Cancelar. ¿Desea Continuar?";
        return mensajeCancelar;
    }
    
    public void cancelarGravamenes() throws ServicioExcepcion {

        //INACTIVAR GRAVAMENES Y GRAVAMEN DETALLE SELECCIONADOS.
        List<GravamenDetalle> gravDetalle = new ArrayList<>();
        for (Gravamen gravSelec : listaGravamenSeleccionados) {
            
            gravDetalle.clear();
            gravDetalle = gravamenDetalleDao.listarPorGravamenId_NoEstadoInactivo(gravSelec.getGraId());
            if (gravDetalle.isEmpty()) {
                gravSelec.setGraEstado("I");
                gravamenDao.edit(gravSelec);
                //caso Fideicomiso, inactivar propietario 
                if (tipoTramite.getTtrCodigo() == 13) {
                    cancelarPropietarioFidecomiso(gravSelec);
                }
            }
            
        }
        for (GravamenDetalle listaGravDetSelec : listaGravamenDetCancelar) {
            listaGravDetSelec.setGvdEstado("I");
            gravamenDetalleDao.edit(listaGravDetSelec);
            Gravamen grav = new Gravamen();
            grav = listaGravDetSelec.getGraId();
            gravDetalle.clear();
            gravDetalle = gravamenDetalleDao.listarPorGravamenId_NoEstadoInactivo(grav.getGraId());
            if (gravDetalle.isEmpty()) {
                grav.setGraEstado("I");
                gravamenDao.edit(grav);
            }
        }

        /* INACTIVAR GRAVAMENES Y GRAVAMEN DETALLE QUE HAYAN QUEDADO CON ESTADO "P"
        CUANDO SE TERMINA EL PROCESO DIRECTAMENTE (SIN PASAR NUEVAMENTE POR EL PROCESO
        DE SELECCIONAR LOS GRAVAMEN Y GRAVAMEN DETALLE QUE SE QUIEREN CANCELAR, Y CREAR ACTA )*/
        for (Gravamen gravamen : listaGravAuxiliar) {
            List<GravamenDetalle> listGravDetalle = new ArrayList<>();
            listGravDetalle.clear();
            listGravDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(gravamen.getGraId(), "P");
            for (GravamenDetalle gravamenDetalle : listGravDetalle) {
                gravamenDetalle.setGvdEstado("I");
                gravamenDetalleDao.edit(gravamenDetalle);
            }
            if (gravamen.getGraEstado().equals("P")) {
                gravamen.setGraEstado("I");
                gravamenDao.edit(gravamen);
                //caso Fideicomiso, inactivar propietario 
                if (tipoTramite.getTtrCodigo() == 13) {
                    cancelarPropietarioFidecomiso(gravamen);
                }
            }
        }

        //ACTUALIZAR ESTADO DE GRAVAMEN EN CASO QUE SE HAYA INACTIVADO UN GRAVAMEN QUE AUN TIENE GRAVAMEN DETALLE ACTIVO.
        List<GravamenDetalle> listGravDetalle = new ArrayList<>();
//        for (Gravamen gravamen : listaGrav) {           
//             listGravDetalle.clear();
//             listGravDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(gravamen.getGraId(), "A");
//             if(!listGravDetalle.isEmpty()){
//                gravamen.setGraEstado("A");
//                gravamenDao.edit(gravamen);
//             }
//        }
        for (Gravamen gravamen : listaGravAuxiliar) {
            listGravDetalle.clear();
            listGravDetalle = gravamenDetalleDao.listarPorGravamenId_Estado(gravamen.getGraId(), "A");
            if (!listGravDetalle.isEmpty()) {
                gravamen.setGraEstado("A");
                gravamenDao.edit(gravamen);
            }
        }
    }
    
    public void cancelarPropietarioFidecomiso(Gravamen gravamen) throws ServicioExcepcion {
        try {
            
            List<TramiteDetalle> listTramDetalle = new ArrayList<>();
            for (Repertorio repertor : listaRepertorioFecha) {
                if (repertor.getTraNumero().getTraNumero().longValue() == tramiteR.getTraNumero().longValue()) {
                    listTramDetalle.clear();
                    listTramDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                            repertor.getTraNumero().getTraNumero(), repertor.getRepTtrId(), repertor.getRepNumero());
                    
                    for (TramiteDetalle tramiteDetalle1 : listTramDetalle) {
                        if (tramiteDetalle1.getTtcId().getTtcPropietario().equals("N")) {
                            Propietario propietario = new Propietario();
                            propietario = propietarioDao.buscarPropietarioPor_Persona_Matricula_Estado_Fideicomiso(tramiteDetalle1.getPerId().getPerId(), gravamen.getPrdMatricula().getPrdMatricula(), "A", "S");
                            if (propietario != null) {
                                propietario.setPrdFideicomiso("N");
                                propietario.setPprEstado("I");
                                propietarioDao.edit(propietario);
                            }
                            
                        }
                    }
                    
                }
            }
        } catch (Exception e) {
            System.err.println(e);
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
                
                if (tipoTramite.getTtrCodigo() == 13) {
                    buscarGravamenesFideicomiso(tramite);
                } else {
                    buscarGravamenes(tramite);
                }
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
    
    public void verificarEstadoProceso(Tramite tramiteTerm) {
        List<RepertorioUsuario> listaRepUsu = new ArrayList<>();
        for (Repertorio repertor : listaRepertorioFecha) {
            if (repertor.getTraNumero().getTraNumero().longValue() == tramiteTerm.getTraNumero().longValue()) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertor.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INC");
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
    
    public void guardar() throws ServicioExcepcion, ParseException {
        cancelarGravamenes();
        guardarRazon(tramiteR);
        
        procesarRepertorioUsuario_TramiteAccion(tramiteR);
        
        llenarDatos();
        
        activeIndex = 0;
        renderedTabRazon = false;
        renderedTabView = false;
        
    }
    
}
