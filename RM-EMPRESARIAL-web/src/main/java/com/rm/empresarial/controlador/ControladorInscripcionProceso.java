/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

 /*
codigo 4: TESTAMENTO
codigo 12: FIDEICOMISO
codigo 14: GENERICO COMPRAVENTA
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.controlador.tipoTramites.Adjudicacion;
import com.rm.empresarial.controlador.tipoTramites.CancelacionHipoteca;
import com.rm.empresarial.controlador.tipoTramites.Compraventa;
import com.rm.empresarial.controlador.tipoTramites.Declaratoria;
import com.rm.empresarial.controlador.tipoTramites.Hipoteca;
import com.rm.empresarial.controlador.tipoTramites.Testamento;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.FechasUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.ActaDao;
import com.rm.empresarial.dao.ActaRazonDao;
import com.rm.empresarial.dao.CantonDao;
import com.rm.empresarial.dao.CargaLaboralDao;
import com.rm.empresarial.dao.CertificadoDao;
import com.rm.empresarial.dao.FacturaDetalleDao;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.InscripcionDao;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.NotariaDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.PropietarioDao;
import com.rm.empresarial.dao.RazonNuevaDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TipoActaDao;
import com.rm.empresarial.dao.TipoCertificadoDao;
import com.rm.empresarial.dao.TipoComparecienteDao;
import com.rm.empresarial.dao.TipoLibroDao;
import com.rm.empresarial.dao.TipoTramiteComparecienteDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.dao.TramiteUsuarioDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Canton;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoPK;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.GravamenDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.Notaria;
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
import com.rm.empresarial.modelo.TipoCertificado;
import com.rm.empresarial.modelo.TipoCompareciente;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoSuspension;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.ConfigServicio;
import com.rm.empresarial.servicio.GravamenDetalleServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.PropiedadDetalleServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoSuspensionServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteUsuarioServicio;
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
import java.util.List;
import java.util.Objects;
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
@Named(value = "controladorInscripcionProceso")
@ViewScoped
public class ControladorInscripcionProceso implements Serializable {

    @Inject
    private TramiteUtil tramiteUtil;
    @Inject
    private PersonaUtil personaUtil;
    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;
    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;
    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControladorRazon;
    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;
    @Inject
    @Getter
    @Setter
    private Compraventa compraventa;
    @Inject
    @Getter
    @Setter
    private Testamento testamento;
    @Inject
    @Getter
    @Setter
    private Adjudicacion adjudicacion;
    @Inject
    @Getter
    @Setter
    private Hipoteca hipoteca;
    @Inject
    @Getter
    @Setter
    private CancelacionHipoteca cancelacionHipoteca;
    @Inject
    @Getter
    @Setter
    private Declaratoria declaratoria;
    @Getter
    @Setter
    private FechasUtil fechasUtil;

    @Getter
    @Setter
    private List<Repertorio> listaRepertorioFecha = new ArrayList<>();
    @Getter
    @Setter
    private List<Repertorio> preListaRepertorioFecha = new ArrayList<>();
    @Getter
    @Setter
    private List<Gravamen> listaGravamen = new ArrayList<>();
    @Getter
    @Setter
    private List<Gravamen> listaGravamenMostrar = new ArrayList<>();
    @Getter
    @Setter
    private List<RepertorioPropiedad> listaRepertorioPropiedad = new ArrayList<>();
    @Getter
    @Setter
    private List<Propiedad> listaPropiedadComparecientes = new ArrayList<>();
    @Getter
    @Setter
    List<TramiteDetalle> listaTramiteDetalle = new ArrayList<>();
//    @Getter
//    @Setter
//    private List<TramiteDetalle> listaTramDetalleCompS = new ArrayList<>();
//    @Getter
//    @Setter
//    private List<TramiteDetalle> listaTramDetalleCompN = new ArrayList<>();
    @Getter
    @Setter
    private List<TipoCompareciente> listaTipoCompareciente = new ArrayList<>();
    @Getter
    @Setter
    private List<Canton> listaCanton = new ArrayList<>();
    @Getter
    @Setter
    private List<RepertorioPropiedad> listaRepPropiedad = new ArrayList<>();
    @Getter
    @Setter
    private List<Notaria> listaNumeroNotario = new ArrayList<>();
    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetComparecientes = new ArrayList<>();
    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropiedadDetalle = new ArrayList<>();
    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropDetActualizarEstado = new ArrayList<>();
    @Getter
    @Setter
    private List<RepertorioPropiedad> listaRepertorioPropR = new ArrayList<>();
    @Getter
    @Setter
    private List<Propiedad> listaPropiedadCertificado = new ArrayList<>();
    @Getter
    @Setter
    private List<Propietario> listaPropiedadPorMatricula = new ArrayList<>();
    @Getter
    @Setter
    List<String> numMatricula = new ArrayList<>();
    @Getter
    @Setter
    private List<TipoSuspension> listaTipoSuspension = new ArrayList<>();
    @Getter
    @Setter
    private List<CertificadoDatos> listaCertificadoDatos = new ArrayList<>();

    @Getter
    @Setter
    private Date FHR;
    @Getter
    @Setter
    private String numRepertorio;
    @Getter
    @Setter
    private String numTramite;
    @Getter
    @Setter
    private String contratoDescripcion;
    @Getter
    @Setter
    private String StrNumRepertorio;
    @Getter
    @Setter
    private String StrNumTramite;
    @Getter
    @Setter
    private String StrContratoDescripcion;
    /**
     * ***************************************
     */
    /**
     * ** TITULOS DE CUADROS DE TEXTO *******
     */
    @Getter
    @Setter
    private String antecedentes;
    @Getter
    @Setter
    private String compareciente;
    @Getter
    @Setter
    private String cuantias;
    @Getter
    @Setter
    private String gravamenes;
    @Getter
    @Setter
    private String linderos;
    @Getter
    @Setter
    private String objetoContrato;
    @Getter
    @Setter
    private String observaciones;
    @Getter
    @Setter
    private String otorgamiento;
    @Getter
    @Setter
    private String txtEditAntecedentes;
    /**
     * **************************************************
     */
    /**
     * ********** VARIABLES CUADORS DE TEXTO ************
     */
    @Getter
    @Setter
    private String txtEditCompareciente;
    @Getter
    @Setter
    private String txtEditCuantias;
    @Getter
    @Setter
    private String txtEditGravamenes;
    @Getter
    @Setter
    private String txtEditLinderos;
    @Getter
    @Setter
    private String txtEditObjetoContrato;
    @Getter
    @Setter
    private String txtEditObservaciones;
    @Getter
    @Setter
    private String txtEditOtorgamiento;
    @Getter
    @Setter
    private String txtResponsables;
    @Getter
    @Setter
    private String txtEditCopiaTextual;
    @Getter
    @Setter
    private String txtEditCopiaTextObjContr;
    @Getter
    @Setter
    private String txtEditCopiaTextAdquisicion;
    /* PROPIEDAD READ ONLY PARA TEXT EDITOR */
    @Getter
    @Setter
    private Boolean readOnly_ObjCont;
    @Getter
    @Setter
    private Boolean readOnly_Antec;
    @Getter
    @Setter
    private Boolean readOnly_Lind;
    @Getter
    @Setter
    private Boolean readOnly_Grav;
    @Getter
    @Setter
    private Boolean readOnly_Obs;

    /**
     * **************************************
     */
    @Getter
    @Setter
    private String cabeceraHTML;
    @Getter
    @Setter
    private String cabeceraPDF;
    @Getter
    @Setter
    private TramiteDetalle tramiteDetalle;
    @Getter
    @Setter
    private Long ttrId;
    /*VARIABLES FECHA OTORGAMIENTO */
    @Getter
    @Setter
    private Date fechaOtorgamiento;
    @Getter
    @Setter
    private String numeroNotario;
    @Getter
    @Setter
    private String canton;
    @Getter
    @Setter
    private Canton cantonRazon;
    @Getter
    @Setter
    private String nombreNotario;

    /**
     * ****************************
     */
    /**
     * **** VARIABLES CUANTIA *****
     */
    @Getter
    @Setter
    private float cuantiaAlcabalas;

    @Getter
    @Setter
    private float cuantiaPlusvalia;

    @Getter
    @Setter
    private float cuantiaRegistro;

    @Getter
    @Setter
    private float cuantiaMonto;

    @Getter
    @Setter
    private String cuantiaTipo;
    @Getter
    @Setter
    private ArrayList<String> listaTipoCuantia = new ArrayList<>();

    @Getter
    @Setter
    private String cuantiaObservacion;

    /**
     * ***************************
     */
    /**
     * *****************
     */
    @Getter
    @Setter
    private Boolean renderedTxtEdit = false;

    /**
     * ********************************
     */
    @Getter
    @Setter
    private TipoTramite tipoTramite;
    @Getter
    @Setter
    private Acta actaGuardar = new Acta();
    @Getter
    @Setter
    private Repertorio repertorio = new Repertorio();
    @Getter
    @Setter
    private String actaHTML;
    @Getter
    @Setter
    private String actaPDF;
    @Getter
    @Setter
    private Boolean deshabilitarBotonGuardar;
    @Getter
    @Setter
    private String testamentoString;
    @Getter
    @Setter
    private TipoLibro tipoLibro;
    @Getter
    @Setter
    private String tipoComparecienteId;
    @Getter
    @Setter
    private Boolean esActaNueva = false;
    @Getter
    @Setter
    private int indiceTab;
    @Getter
    @Setter
    private Boolean disabledTabActa;
    @Getter
    @Setter
    private Boolean disabledTabRazon;
    @Getter
    @Setter
    private Boolean disabledTabCertifcado;
    @Getter
    @Setter
    private Boolean renderedTabRazon;
    @Getter
    @Setter
    private Boolean renderedTabCertifcado;
    @Getter
    @Setter
    private Boolean renderedTabView;
    @Getter
    @Setter
    private Boolean renderedTabActa;
    @Getter
    @Setter
    private Boolean renderedBtnsTab;
    @Getter
    @Setter
    private String stringMatricula;
    @Getter
    @Setter
    private String tramDetalleId;
    @Getter
    @Setter
    private Propiedad matricula = new Propiedad();
    @Getter
    @Setter
    private Secuencia secuencia;
    @Getter
    @Setter
    private Secuencia secuenciaRaz;
    @Getter
    @Setter
    private Boolean mostrarGrid1;
    @Getter
    @Setter
    private Acta actaEncontrada;
    @Getter
    @Setter
    private Factura factura;
    @Getter
    @Setter
    private CargaLaboral buscarCargaLaboral;
    @Getter
    @Setter
    private PropiedadDetalle propiedadDetalleSeleccionada;
    @Getter
    @Setter
    private BigDecimal totalAcc;
    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuario;
    @Getter
    @Setter
    private Propiedad propiedadMatriculaDerAcc;
    @Getter
    @Setter
    private Boolean disabledBtnGuardarDerAcc = false;
    @Getter
    @Setter
    private String razonInscripcion;
    @Getter
    @Setter
    private String observacionCert;
    @Getter
    @Setter
    private Date fechaIngresoCertificado;

    @Getter
    @Setter
    private Boolean disabledBtnAgregarDerAcc = false;
    @Getter
    @Setter
    private PropiedadDetalle propiedadDetElim;
    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleSeleccionado;
    @Getter
    @Setter
    private Boolean disabledRegistroCuantia;
    @Getter
    @Setter
    private Boolean mostrarDlgPreviewActa;
    @Getter
    @Setter
    private String certificadoGenerado;
    @Getter
    @Setter
    private String certificadoTotalGenerado;
    @Getter
    @Setter
    private String descripcionPropiedadCert;
    @Getter
    @Setter
    private String antecedentesCert;
    @Getter
    @Setter
    private String nombresPropietarios;
    @Getter
    @Setter
    private Usuario usuarioAsignado;
    @Getter
    @Setter
    private String identificacion;
    @Getter
    @Setter
    private String gravamenesCert;
    @Getter
    @Setter
    private Tramite tramiteSeleccionado;
    @Getter
    @Setter
    private Tramite tramSeleccTerminar = new Tramite();
    @Getter
    @Setter
    private Boolean todosEstadoProcTerminado = false;
    @Getter
    @Setter
    private Institucion institucion;
    @Getter
    @Setter
    private Boolean renderedBtnGuardar = false;
    @Getter
    @Setter
    private Tramite tramiteR;
    @Getter
    @Setter
    private String cerNumero;
    @Getter
    @Setter
    short secuencialCert;
    @Getter
    @Setter
    private BigDecimal numPorcentajeAccionesRestantes;
    @Getter
    @Setter
    private Repertorio repertorioSelec;
    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSelec;
    @Getter
    @Setter
    private Propiedad propiedadCertificadoSeleccionada;
    @Getter
    @Setter
    private int codigoTipoTramite = 0;

    @EJB
    private InscripcionDao inscripcionDao;
    @EJB
    private TramiteDao tramiteDao;
    @EJB
    private TramiteDetalleDao tramiteDetalleDao;
    @EJB
    private RepertorioPropiedadDao repertorioPropiedadDao;
    @EJB
    private GravamenDao gravamenDao;
    @EJB
    private TipoTramiteDao tipoTramiteDao;
    @EJB
    private TipoLibroDao tipoLibroDao;
    @EJB
    private ActaDao actaDao;
    @EJB
    private RepertorioDao repertorioDao;
    @EJB
    private PropietarioDao propietarioDao;
    @EJB
    private TipoTramiteComparecienteDao tipoTramiteComparecienteDao;
    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;
    @EJB
    private SecuenciaServicio secuenciaServicio;
    @EJB
    private PropiedadDao propiedadDao;
    @EJB
    private TipoActaDao tipoActaDao;
    @EJB
    private FacturaDetalleDao facturaDetalleDao;
    @EJB
    private CantonDao cantonDao;
    @EJB
    private NotariaDao notariaDao;
    @EJB
    private RepertorioUsuarioDao RepertorioUsuarioDao;
    @EJB
    private PropiedadDetalleServicio servicioPropiedadDetalle;
    @EJB
    private PersonaServicio personaServicio;
    @EJB
    private ActaRazonDao actaRazonDao;
    @EJB
    private TramiteServicio servicioTramite;
    @EJB
    private TipoCertificadoDao tipoCertificadoDao;
    @EJB
    private CertificadoDao certificadoDao;
    @EJB
    private CertificadoServicio servicioCertificado;
    @EJB
    private CargaLaboralDao cargaLaboralDao;
    @EJB
    private TipoSuspensionServicio servicioTipoSuspension;
    @EJB
    private TipoComparecienteDao tipoComparecienteDao;
    @EJB
    private InstitucionDao institucionDao;
    @EJB
    private RazonNuevaDao razonNuevaDao;
    @EJB
    private TipoTramiteServicio servicioTipoTramite;
    @EJB
    private TramiteAccionServicio servicioTramiteAccion;
    @EJB
    private CargaLaboralServicio servicioCargaLaboral;
    @EJB
    private UsuarioServicio servicioUsuario;
    @EJB
    private TramiteUsuarioServicio serviciotramiteUsuario;

    @EJB
    private MarginacionServicio servicioMarginacion;
    @Inject
    private PdfTempUtil pdfTempUtil;
    @EJB
    private GravamenDetalleServicio gravamenDetalleServicio;

    @EJB
    private ConfigServicio servicioConfig;
    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;

    @Getter
    @Setter
    private boolean bolAntecedentesSoloLectura = false;

    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    @Getter
    @Setter
    private String urlActaPdf;

    @Getter
    @Setter
    private String urlRazonPdf;

    @Getter
    @Setter
    private String urlCertificadoPdf;

    private int numSaltosLinea;

    private int numSaltosLineaRazon;

    private int numSaltosLineaCertificado;
    private int numTotalSaltosLinea;
    @Getter
    @Setter
    private Boolean procesarSumarSaltoLinea = Boolean.FALSE;
    @Getter
    @Setter
    private Boolean procesarAgregarImagen = Boolean.FALSE;
    @Getter
    @Setter
    private Boolean agregarObservacion = Boolean.FALSE;
    @Getter
    @Setter
    private Boolean saltoLinea = Boolean.FALSE;
    @Getter
    @Setter
    private Boolean procesarRestarSaltoLinea = Boolean.FALSE;
    @Getter
    @Setter
    private Boolean renderedBtnSuspension;

    @Getter
    @Setter
    private boolean disableBtnGuardar;
    @Getter
    @Setter
    private boolean bolAgregarImagenEspacioEnBlancoActa;
    @Getter
    @Setter
    private boolean bolAgregarImagenEspacioEnBlancoRazon;
    @Getter
    @Setter
    private boolean bolAgregarImagenEspacioEnBlancoCertificado;

    private String urlImagenEspacioEnBlanco;
    @Getter
    @Setter
    private String texto;
    @Getter
    @Setter
    private String agregarImagen = "noAgregar";

    public ControladorInscripcionProceso() {
        antecedentes = "";
        compareciente = "";
        cuantias = "";
        gravamenes = "";
        linderos = "";
        objetoContrato = "";
        observaciones = "";
        otorgamiento = "";
        txtEditAntecedentes = "";
        txtEditCompareciente = "";
        txtEditCuantias = "";
        txtEditGravamenes = "";
        txtEditLinderos = "";
        txtEditObjetoContrato = "";
        txtEditObservaciones = "";
        txtEditOtorgamiento = "";
        tipoTramite = new TipoTramite();
        secuencia = new Secuencia();
        indiceTab = 0;
        factura = new Factura();
        disabledTabActa = true;
        disabledTabRazon = true;
        disabledTabCertifcado = true;
        readOnly_ObjCont = true;
        readOnly_Antec = true;
        readOnly_Lind = true;
        readOnly_Grav = true;
        readOnly_Obs = true;
        renderedTabActa = true;
        renderedTabRazon = false;
        renderedTabCertifcado = false;
        propiedadDetalleSeleccionada = new PropiedadDetalle();
        propiedadDetElim = new PropiedadDetalle();
        renderedTabView = false;
        tramiteAccion = new TramiteAccion();
        numPorcentajeAccionesRestantes = new BigDecimal(100);
        repertorioSelec = new Repertorio();
        propiedadCertificadoSeleccionada = new Propiedad();
        disableBtnGuardar = true;
        renderedBtnSuspension = false;
    }

    @PostConstruct
    public void postConstructor() {
        setFHR(Calendar.getInstance().getTime());
        secuencia = new Secuencia();
        tramiteSeleccionado = new Tramite();
        tipoLibro = new TipoLibro();
//        actaA = new Acta();
        buscarCargaLaboral = new CargaLaboral();
        renderedTxtEdit = false;
        deshabilitarBotonGuardar = true;
        //propiedadSeleccionada = new Propiedad();
        totalAcc = new BigDecimal(0);
        propiedadMatriculaDerAcc = new Propiedad();
        propiedadDetElim = new PropiedadDetalle();
        propiedadDetalleSeleccionada = new PropiedadDetalle();
        disabledRegistroCuantia = false;
        mostrarDlgPreviewActa = false;
        inicializar();
        cargarlistaTipoCuantia();
        cuantiaTipo = "Normal";

    }

    public void inicializar() {
        try {
            llenarDatos();
            urlImagenEspacioEnBlanco = servicioConfigDetalle.buscarPorConfig("IMAGENBLANCO").getConfigDetalleTexto();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void cargarlistaTipoCuantia() {
        listaTipoCuantia.add("Normal");
        listaTipoCuantia.add("Determinado");
        listaTipoCuantia.add("Indeterminado");
        listaTipoCuantia.add("Exonerado");
    }

    public void habilitarGuardar() {
        disableBtnGuardar = false;
    }

    public void llenarDatos() throws ServicioExcepcion, ParseException {
        preListaRepertorioFecha.clear();
        listaPropiedadPorMatricula.clear();
        limpiarCamposTxtEdit();
        renderedBtnGuardar = true;
        renderedTabView = false;
        indiceTab = 0;
        disabledTabActa = true;
        disabledTabRazon = false;
        disabledTabCertifcado = false;
        renderedBtnSuspension = false;

        renderedBtnsTab = false;
        renderedTxtEdit = false;
        renderedTabActa = false;
        renderedTabRazon = true;
        renderedTabCertifcado = true;
        setPreListaRepertorioFecha(inscripcionDao.ListarPorFHRPorUsrLog(FHR, dataManagerSesion.getUsuarioLogeado().getUsuId()));
        listaRepertorioFecha.clear();
        for (Repertorio repertorio1 : preListaRepertorioFecha) {
            Tramite tramite = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());

            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramite.getTraNumero().longValue()) && !tramite.getTraEstadoRegistro().trim().equals("RZ")) {
                RepertorioUsuario repUsu = RepertorioUsuarioDao.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorio1.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
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

    public void cargarDatosCantonNotaria() throws ServicioExcepcion, ParseException {

        listaCanton = cantonDao.listarTodo();
        listaNumeroNotario = notariaDao.listarTodo();

    }

    public void limpiarCamposTxtEdit() {
        txtEditAntecedentes = "";
        txtEditCompareciente = "";
        txtEditCuantias = "";
        txtEditGravamenes = "";
        txtEditLinderos = "";
        txtEditObjetoContrato = "";
        txtEditObservaciones = "";
        txtEditOtorgamiento = "";
        txtEditCopiaTextual = "";
        antecedentes = "";
        compareciente = "";
        cuantias = "";
        gravamenes = "";
        linderos = "";
        objetoContrato = "";
        observaciones = "";
        otorgamiento = "";
        cabeceraHTML = "";
        cabeceraPDF = "";
        numRepertorio = "";
        numTramite = "";
        contratoDescripcion = "";
        nombreNotario = "";
        razonInscripcion = "";
        certificadoGenerado = "";
        txtEditCopiaTextObjContr = "";
        txtEditCopiaTextAdquisicion = "";
        listaPropiedadPorMatricula.clear();
    }

    public void txtCuantias() {
        txtEditCuantias = compraventa.txtCuantias(cuantiaAlcabalas, cuantiaMonto,
                cuantiaPlusvalia, cuantiaRegistro, cuantiaTipo, cuantiaObservacion);

    }

    public void prepararCuantia() {
        cuantiaAlcabalas = 0;
        cuantiaMonto = 0;
        cuantiaPlusvalia = 0;
        cuantiaRegistro = 0;
        cuantiaTipo = "Normal";
        cuantiaObservacion = "";
    }

    public void prepararOtorgamiento() {
        fechaOtorgamiento = new Date();
        numeroNotario = "";
        canton = "";
        nombreNotario = "";
    }

    public void txtOtorgamiento() throws ServicioExcepcion {
        txtEditOtorgamiento = compraventa.txtOtorgamiento(fechaOtorgamiento, numeroNotario, canton, nombreNotario);
    }

//    public void listarPropiedadPorMatricula() throws ServicioExcepcion {
//        try {
//            TramiteDetalle tramDet = new TramiteDetalle();
//            TipoTramite tipoTram = new TipoTramite();
//            tramDet = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numTramite), contratoDescripcion);
//            tipoTram = tipoTramiteDao.buscarPorID(tramDet.getTdtTtrId());
//            listaPropiedadPorMatricula.clear();
//
//            List<Propiedad> listaPropiedadMatric = new ArrayList<>();
//            listaPropiedadMatric = propiedadDao.listarPropiedadPorMatriculaPorTramitePorPredioPorCatastro(Long.valueOf(numTramite), tipoTram.getTtrId());
//
//            List<Propietario> listaPropietar = new ArrayList<>();
//            for (Propiedad propiedad : listaPropiedadMatric) {
//                listaPropietar = propietarioDao.buscarPor_PropiedadMatricula_Estado(propiedad.getPrdMatricula());
//                listaPropiedadPorMatricula.addAll(listaPropietar);
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }
    public void onRowSelect(SelectEvent event) throws ServicioExcepcion, ParseException {
        renderedTabView = false;
        readOnly_ObjCont = true;
        readOnly_Antec = true;
        readOnly_Lind = true;
        readOnly_Grav = true;
        readOnly_Obs = true;
        bolAntecedentesSoloLectura = false;
        codigoTipoTramite = 0;
        limpiarCamposTxtEdit();
        cargarDatosCantonNotaria();

        setRepertorioUsuarioSelec(servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorioSelec.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS"));

        numRepertorio = "" + (((Repertorio) event.getObject()).getRepNumero()).toString() + "";
        numTramite = "" + (((Repertorio) event.getObject()).getTraNumero().getTraNumero()).toString() + "";
        contratoDescripcion = "" + (((Repertorio) event.getObject()).getRepTtrDescripcion()) + "";

        tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numTramite), contratoDescripcion);
        tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
        if (tipoTramite != null) {
            if (tipoTramite.getTtrCodigo() != null) {
                codigoTipoTramite = tipoTramite.getTtrCodigo();
            }

        }
        ttrId = tramiteDetalle.getTdtTtrId();
        repertorio = repertorioDao.encontrarRepertorioPorId(numRepertorio);

        StrNumTramite = numTramite;
        StrNumRepertorio = numRepertorio;
        StrContratoDescripcion = contratoDescripcion;
        renderedBtnSuspension = true;

        verGravamenes();
        tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
        if (tipoLibro.getTplPropietario()) {
            if (listaGravamenMostrar.isEmpty()) {
                procesar();
            }

        } else {
            procesar();
        }
    }

    public void procesar() throws ServicioExcepcion, ParseException {
        buscarActa();

        //listarPropiedadPorMatricula();
        if (esActaNueva) {

            llenarPorTipoTramite();

        } else {
            antecedentes = compraventa.tituloAntecedentes();
            compareciente = compraventa.tituloCompareciente();
            linderos = compraventa.tituloLinderos();
            gravamenes = compraventa.tituloGravamen();
            objetoContrato = compraventa.tituloObjetoContrato();
            observaciones = compraventa.tituloObservacion();
            otorgamiento = compraventa.tituloOtorgamiento();
            cuantias = compraventa.tituloCuantias();
            txtEditAntecedentes = actaEncontrada.getActDescripcion3();
            txtEditCompareciente = actaEncontrada.getActDescripcion1();
            txtEditCuantias = actaEncontrada.getActDescripcion6();
            txtEditGravamenes = actaEncontrada.getActDescripcion7();
            txtEditLinderos = actaEncontrada.getActDescripcion5();
            txtEditObjetoContrato = actaEncontrada.getActDescripcion4();
            txtEditObservaciones = actaEncontrada.getActDescripcion8();
            txtEditOtorgamiento = actaEncontrada.getActDescripcion2();
            //codigo 4: TESTAMENTO
            if (tipoTramite.getTtrCodigo() != null && tipoTramite.getTtrCodigo() == 4) {
                txtEditCopiaTextObjContr = actaEncontrada.getActDescripcion3();
                txtEditCopiaTextAdquisicion = actaEncontrada.getActDescripcion9();
                txtEditCopiaTextual = actaEncontrada.getActDescripcion15();
            }
            //CODIGO 12: FIDEICOMISO
            if (tipoTramite.getTtrCodigo() != null && tipoTramite.getTtrCodigo() == 12) {
                txtEditCopiaTextObjContr = actaEncontrada.getActDescripcion3();
                txtEditCopiaTextAdquisicion = actaEncontrada.getActDescripcion9();
                txtEditCopiaTextual = actaEncontrada.getActDescripcion15();
            }
            //CODIGO 14: GENERICO COMPRAVENTA
            if (tipoTramite.getTtrCodigo() != null && tipoTramite.getTtrCodigo() == 14) {
                txtEditCopiaTextObjContr = actaEncontrada.getActDescripcion3();
                txtEditCopiaTextAdquisicion = actaEncontrada.getActDescripcion9();
                txtEditCopiaTextual = actaEncontrada.getActDescripcion15();
            }

        }

        renderedTabView = true;
        renderedTxtEdit = true;
        deshabilitarBotonGuardar = true;

        disabledTabActa = false;
        disabledTabRazon = true;
        disabledTabCertifcado = true;
        renderedBtnsTab = true;
        renderedTabActa = true;
        renderedTabRazon = false;
        renderedTabCertifcado = false;
        indiceTab = 0;
        if (txtEditAntecedentes.trim().isEmpty() || txtEditAntecedentes.trim().equals("MIGRADA") || txtEditAntecedentes.trim().equals("<p>MIGRADA</p>")) {
            bolAntecedentesSoloLectura = true;
        }
        disableBtnGuardar = true;

        if (tipoTramite != null) {
            if (tipoTramite.getTtrCodigo() != null) {
                if (tipoTramite.getTtrCodigo() == 4 || tipoTramite.getTtrCodigo() == 12 || tipoTramite.getTtrCodigo() == 14) {
                    casoTestamento();
                }
            }

        }
    }

    public void casoTestamento() throws ServicioExcepcion, ParseException {
        try {
            cabeceraHTML = "";
            tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
            //libro = tipoTramite.getTplId();

            setTestamentoString("<strong>" + compareciente + "</strong></br>"
                    + txtEditCompareciente + "<br></br>"
                    + "<strong>" + otorgamiento + "</strong></br>"
                    + txtEditOtorgamiento + "<br></br>"
                    + "<strong>" + antecedentes + "</strong></br>"
                    + txtEditAntecedentes + "<br></br>"
                    + "<strong>" + objetoContrato + "</strong></br>"
                    + txtEditObjetoContrato + "<br></br>"
                    + "<strong>" + linderos + "</strong></br>"
                    + txtEditLinderos + "<br></br>"
                    + "<strong>" + cuantias + "</strong></br>"
                    + txtEditCuantias + "<br></br>"
                    + "<strong>" + gravamenes + "</strong></br>"
                    + txtEditGravamenes + "<br></br>"
                    + "<strong>" + observaciones + "</strong></br>"
                    + txtEditObservaciones + "<br></br>");
        } catch (Exception e) {
            System.out.println(e);
            actaHTML = "";
        }
    }

    public void llenarPorTipoTramite() throws ServicioExcepcion, ParseException {//metodo que genera cabecera
        FacesContext context = FacesContext.getCurrentInstance();
        cabeceraHTML = "";
        cabeceraPDF = "";
        if (tipoTramite.getTtrCodigo() != null) {
            for (int i = 0; i < numSaltosLinea; i++) {
                cabeceraHTML += "<br>";
                cabeceraPDF += "<br>";
            }
            System.out.println("Codigo Tipo de tramite: " + tipoTramite.getTtrCodigo());
            switch (tipoTramite.getTtrCodigo()) {
                case 4:
                case 12:
                case 14:
                    cabeceraHTML += testamento.generarCabeceraHTML(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    cabeceraPDF += testamento.generarCabeceraPDF(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    renderedTxtEdit = true;
                    break;
                case 5:
                    antecedentes = compraventa.tituloAntecedentes();
                    compareciente = compraventa.tituloCompareciente();
                    linderos = compraventa.tituloLinderos();
                    gravamenes = compraventa.tituloGravamen();
                    objetoContrato = compraventa.tituloObjetoContrato();
                    observaciones = compraventa.tituloObservacion();
                    otorgamiento = compraventa.tituloOtorgamiento();
                    cuantias = compraventa.tituloCuantias();
                    txtResponsables = compraventa.responsables(numTramite);
                    if (txtEditAntecedentes.isEmpty()) {
                        txtEditAntecedentes = compraventa.txtAntecedentes(numRepertorio);
                    }
                    txtEditCompareciente = compraventa.txtCompareciente(numRepertorio, numTramite);
                    //txtEditCuantias = compraventa.txtCuantias(cuantiaAlcabalas, cuantiaMonto, cuantiaPlusvalia, cuantiaRegistro, cuantiaTipo);                
                    txtEditLinderos = compraventa.txtLinderos(numRepertorio);
                    txtEditGravamenes = compraventa.txtGravamenes(numRepertorio, numTramite);
                    txtEditObjetoContrato = compraventa.txtObjetoContrato();
                    txtEditObservaciones = compraventa.txtObservaciones(txtEditObservaciones);
                    //txtEditOtorgamiento = compraventa.txtOtorgamiento(fechaOtorgamiento, numeroNotario, canton, nombreNotario);
                    cabeceraHTML += compraventa.generarCabeceraHTML(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    cabeceraPDF += compraventa.generarCabeceraPDF(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    renderedTxtEdit = true;
                    break;
                case 6:
                    antecedentes = cancelacionHipoteca.tituloAntecedentes();
                    compareciente = cancelacionHipoteca.tituloCompareciente();
                    linderos = cancelacionHipoteca.tituloLinderos();
                    gravamenes = cancelacionHipoteca.tituloGravamen();
                    objetoContrato = cancelacionHipoteca.tituloObjetoContrato();
                    observaciones = cancelacionHipoteca.tituloObservacion();
                    otorgamiento = cancelacionHipoteca.tituloOtorgamiento();
                    cuantias = cancelacionHipoteca.tituloCuantias();
                    txtResponsables = cancelacionHipoteca.responsables(numTramite);
                    if (txtEditAntecedentes.isEmpty()) {
                        txtEditAntecedentes = cancelacionHipoteca.txtAntecedentes(numRepertorio);
                    }
                    txtEditCompareciente = cancelacionHipoteca.txtCompareciente(numRepertorio, numTramite);
                    //txtEditCuantias = compraventa.txtCuantias(cuantiaAlcabalas, cuantiaMonto, cuantiaPlusvalia, cuantiaRegistro, cuantiaTipo);
                    txtEditGravamenes = cancelacionHipoteca.txtGravamenes(numRepertorio, numTramite);
                    txtEditLinderos = cancelacionHipoteca.txtLinderos(numRepertorio);
                    txtEditObjetoContrato = cancelacionHipoteca.txtObjetoContrato();
                    txtEditObservaciones = cancelacionHipoteca.txtObservaciones(txtEditObservaciones);
                    //txtEditOtorgamiento = compraventa.txtOtorgamiento(fechaOtorgamiento, numeroNotario, canton, nombreNotario);
                    cabeceraHTML += cancelacionHipoteca.generarCabeceraHTML(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    cabeceraPDF += cancelacionHipoteca.generarCabeceraPDF(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    renderedTxtEdit = true;
                    break;
                case 7:
                    antecedentes = declaratoria.tituloAntecedentes();
                    compareciente = declaratoria.tituloCompareciente();
                    linderos = declaratoria.tituloLinderos();
                    gravamenes = declaratoria.tituloGravamen();
                    objetoContrato = declaratoria.tituloObjetoContrato();
                    observaciones = declaratoria.tituloObservacion();
                    otorgamiento = declaratoria.tituloOtorgamiento();
                    cuantias = declaratoria.tituloCuantias();
                    txtResponsables = declaratoria.responsables(numTramite);
                    if (txtEditAntecedentes.isEmpty()) {
                        txtEditAntecedentes = declaratoria.txtAntecedentes(numRepertorio);
                    }
                    txtEditCompareciente = declaratoria.txtCompareciente(numRepertorio, numTramite);
                    //txtEditCuantias = compraventa.txtCuantias(cuantiaAlcabalas, cuantiaMonto, cuantiaPlusvalia, cuantiaRegistro, cuantiaTipo);
                    txtEditGravamenes = declaratoria.txtGravamenes(numRepertorio, numTramite);
                    txtEditLinderos = declaratoria.txtLinderos(numRepertorio);
                    txtEditObjetoContrato = declaratoria.txtObjetoContrato();
                    txtEditObservaciones = declaratoria.txtObservaciones(txtEditObservaciones);
                    //txtEditOtorgamiento = compraventa.txtOtorgamiento(fechaOtorgamiento, numeroNotario, canton, nombreNotario);
                    cabeceraHTML += declaratoria.generarCabeceraHTML(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    cabeceraPDF += declaratoria.generarCabeceraPDF(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    renderedTxtEdit = true;
                    break;
                case 8:
                    antecedentes = hipoteca.tituloAntecedentes();
                    compareciente = hipoteca.tituloCompareciente();
                    linderos = hipoteca.tituloLinderos();
                    gravamenes = hipoteca.tituloGravamen();
                    objetoContrato = hipoteca.tituloObjetoContrato();
                    observaciones = hipoteca.tituloObservacion();
                    otorgamiento = hipoteca.tituloOtorgamiento();
                    cuantias = hipoteca.tituloCuantias();
                    txtResponsables = hipoteca.responsables(numTramite);
                    if (txtEditAntecedentes.isEmpty()) {
                        txtEditAntecedentes = hipoteca.txtAntecedentes(numRepertorio);
                    }
                    txtEditCompareciente = hipoteca.txtCompareciente(numRepertorio, numTramite);
                    //txtEditCuantias = compraventa.txtCuantias(cuantiaAlcabalas, cuantiaMonto, cuantiaPlusvalia, cuantiaRegistro, cuantiaTipo);
                    txtEditGravamenes = hipoteca.txtGravamenes(numRepertorio, numTramite);
                    txtEditLinderos = hipoteca.txtLinderos(numRepertorio);
                    txtEditObjetoContrato = hipoteca.txtObjetoContrato();
                    txtEditObservaciones = hipoteca.txtObservaciones(txtEditObservaciones);
                    //txtEditOtorgamiento = compraventa.txtOtorgamiento(fechaOtorgamiento, numeroNotario, canton, nombreNotario);
                    cabeceraHTML += hipoteca.generarCabeceraHTML(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    cabeceraPDF += hipoteca.generarCabeceraPDF(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    renderedTxtEdit = true;
                    break;
                case 11:
                    antecedentes = adjudicacion.tituloAntecedentes();
                    compareciente = adjudicacion.tituloCompareciente();
                    linderos = adjudicacion.tituloLinderos();
                    gravamenes = adjudicacion.tituloGravamen();
                    objetoContrato = adjudicacion.tituloObjetoContrato();
                    observaciones = adjudicacion.tituloObservacion();
                    otorgamiento = adjudicacion.tituloOtorgamiento();
                    cuantias = adjudicacion.tituloCuantias();
                    txtResponsables = adjudicacion.responsables(numTramite);
                    if (txtEditAntecedentes.isEmpty()) {
                        txtEditAntecedentes = adjudicacion.txtAntecedentes(numRepertorio);
                    }
                    txtEditCompareciente = adjudicacion.txtCompareciente(numRepertorio, numTramite);

                    txtEditLinderos = adjudicacion.txtLinderos(numRepertorio);
                    txtEditGravamenes = adjudicacion.txtGravamenes(numRepertorio, numTramite);
                    txtEditObjetoContrato = adjudicacion.txtObjetoContrato();
                    txtEditObservaciones = adjudicacion.txtObservaciones(txtEditObservaciones);
                    cabeceraHTML += adjudicacion.generarCabeceraHTML(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    cabeceraPDF += adjudicacion.generarCabeceraPDF(ttrId, numTramite, numRepertorio, contratoDescripcion);
                    renderedTxtEdit = true;
                    break;
                default:
                    String mensaje = " Tipo de Trámite no parametrizado";
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", mensaje));
            }

        } else {
            String mensaje = "Código no parametrizado para el Tipo de Trámite seleccionado";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", mensaje));
        }

//        if (!txtEditAntecedentes.isEmpty()) {
//            bolAntecedentesSoloLectura = true;
//        }
    }

    public void sumarSaltoLinea() throws IOException, ServicioExcepcion {
        try {
            numSaltosLinea++;
            generarActaParaHTML();
            generarActaParaPDF();
        } catch (ParseException ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void restarSaltoLinea() throws IOException, ServicioExcepcion {
        try {
            if (numSaltosLinea > 0) {
                numSaltosLinea--;
                generarActaParaHTML();
                generarActaParaPDF();
            }

        } catch (ParseException ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sumarSaltoLineaRazon() throws IOException, ServicioExcepcion {
        try {
            numSaltosLineaRazon++;
            txtRazonInscripcion(tramiteR);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        indiceTab = 0;

    }

    public void restarSaltoLineaRazon() throws IOException, ServicioExcepcion {
        try {
            if (numSaltosLineaRazon > 0) {
                numSaltosLineaRazon--;
                txtRazonInscripcion(tramiteR);
            }

        } catch (ParseException ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        indiceTab = 0;

    }

    public void sumarSaltoLineaCertificado() throws IOException, ServicioExcepcion {
        try {
            numSaltosLineaCertificado++;
            procesarSumarSaltoLinea = Boolean.TRUE;
            saltoLinea = Boolean.TRUE;
            generarCertificado();
        } catch (ParseException ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        indiceTab = 1;

    }

    public void restarSaltoLineaCertificado() throws IOException, ServicioExcepcion {
        try {
            procesarRestarSaltoLinea = Boolean.TRUE;
            saltoLinea = Boolean.TRUE;
            for (CertificadoDatos certDat : listaCertificadoDatos) {
                if (certDat.getPropiedad().getPrdMatricula().equals(propiedadCertificadoSeleccionada.getPrdMatricula())) {
                    numSaltosLineaCertificado = certDat.getNumSaltoLinea();
                }
            }
            if (numSaltosLineaCertificado > 0) {
                numSaltosLineaCertificado--;
                generarCertificado();
            }

        } catch (ParseException ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        indiceTab = 1;

    }

    public void agregarImagenBlancoActa() {
        try {
//            bolAgregarImagenEspacioEnBlancoActa = true;
            generarActaParaHTML();
            generarActaParaPDF();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarImagenBlancoRazon() {
        try {
//            bolAgregarImagenEspacioEnBlancoRazon = true;
            txtRazonInscripcion(tramiteR);
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        indiceTab = 0;

    }

    public void agregarImagenBlancoCertificado() {
        try {
            switch (agregarImagen) {
                case "agregar":
                    bolAgregarImagenEspacioEnBlancoCertificado = true;
                    break;
                case "noAgregar":
                    bolAgregarImagenEspacioEnBlancoCertificado = false;
                    break;
            }
            procesarAgregarImagen = Boolean.TRUE;
            generarCertificado();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        indiceTab = 1;

    }

    public void buscarPersona() {

        if (getTramitesControladorBb().getIdentificacion() != null && !getTramitesControladorBb().getIdentificacion().equals("")) {
            //getTramitesControladorBb().setIdentificacion(identificacion);            

            try {
                getTramitesControladorBb().setPersona(personaUtil.obtenerDatosPersona(getTramitesControladorBb().getIdentificacion()));
                if (getTramitesControladorBb().getPersona() != null) {
                    if (getTramitesControladorBb().getPersona().getPerIdentificacion() != null) {
                        nombrePersona();
                    } else {
                        getTramitesControladorBb().setIdentificacion(" ");
                        getTramitesControladorBb().setNombrePersona(" ");
                    }
                } else {
                    getTramitesControladorBb().setPersona(null);
                    getTramitesControladorBb().setNombrePersona("");
                    getTramitesControladorBb().setEstado(Boolean.FALSE);
                    addErrorMessage("Persona no existe");
                }

            } catch (Exception e) {
                addErrorMessage("Ingrese Cédula/RUC/Pas");
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

    public void onRowSelectComparecientes(SelectEvent event) throws ServicioExcepcion, ParseException {

        tramDetalleId = "" + (((TramiteDetalle) event.getObject()).getTdtId()).toString() + "";

    }

    public void eliminarCompareciente(Long id) throws ServicioExcepcion, ParseException {
        try {
            TramiteDetalle tramiteDetal = new TramiteDetalle();
            tramiteDetal = tramiteDetalleDao.buscarPorId(Long.valueOf(id));
            //listaTramiteDetalle.remove(tramiteDetal);
            List<TramiteDetalle> listTramDet = new ArrayList<>();
            listTramDet = listaTramiteDetalle;
            String prop = "";
            int aux = 0;
            //Boolean existeComp = false;                                  
            BigDecimal ttcId = BigDecimal.valueOf(0);
            if (listaTramiteDetalle.contains(tramiteDetal)) {
                ttcId = tramiteDetal.getTtcId().getTtcId();
                prop = tramiteDetal.getTtcId().getTtcPropietario();
            }
            //for (TramiteDetalle tramiteDetalle1 : listaTramiteDetalle) {

            // ttcId = tramiteDetalle1.getTtcId().getTtcId();
            // existeComp = false;                 
            for (TramiteDetalle tramiteDetalle2 : listTramDet) {
                if (tramiteDetalle2.getTtcId().getTtcId().compareTo(ttcId) == 0) {

                    if (tramiteDetalle2.getTtcId().getTtcPropietario().equals(prop)) {
                        aux++;

                    }
                }

            }
            if (aux >= 2) {
                tramiteDetal.setTdtEstado("I");
                tramiteDetalleDao.edit(tramiteDetal);
            } else {
                FacesContext context = FacesContext.getCurrentInstance();
                String mensaje = "No se puede eliminar el registro porque debe existir al menos un " + tramiteDetal.getTdtTpcDescripcion();
                context.addMessage(null, new FacesMessage(mensaje, ""));
            }

            listarComparecientes();
            //}

        } catch (ServicioExcepcion | ParseException e) {
            System.out.println(e);

        }

    }

    public void guardarTramiteDetalle() throws ServicioExcepcion, ParseException {
        try {
            if (getTramitesControladorBb().getPersona() != null && getTramitesControladorBb().getPersona().getPerId() != null) {
                TipoCompareciente tipoComparec = new TipoCompareciente();
                tipoComparec = tipoComparecienteDao.encontrarTipoComparecientePorId(tipoComparecienteId);
                TipoTramiteCompareciente tipoTramCompareciente = new TipoTramiteCompareciente();
                tipoTramCompareciente = tipoTramiteComparecienteDao.buscarPorIdTramitePorTipoCompareciente(
                        tipoTramite.getTtrId(), tipoComparec.getTpcId());
                short idLibro = tipoTramite.getTplId().getTplId().shortValue();
                TramiteDetalle tramDetalle = new TramiteDetalle();
                tramDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(repertorio.getTraNumero().getTraNumero(), tipoTramite.getTtrDescripcion());

                if (!existeTramiteDetalle(getTramitesControladorBb().getPersona().getPerId())) {
                    TramiteDetalle tramDet = new TramiteDetalle();
                    getTramitesControladorBb().setPersonaConyugue(getTramitesControladorBb().getPersona().getPerIdConyuge());
                    tramDet.setPerId(getTramitesControladorBb().getPersona());
                    tramDet.setTdtConyuguePerId(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerId());
                    tramDet.setTdtConyuguePerNombre(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerNombre() + " " + getTramitesControladorBb().getPersona().getPerIdConyuge().getPerApellidoPaterno());
                    tramDet.setTdtConyuguePerTipoIden(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerTipoIdentificacion());
                    tramDet.setTdtEstado("A");
                    tramDet.setTdtFHR(Calendar.getInstance());
                    tramDet.setTdtFechaRegistro(Calendar.getInstance());
                    tramDet.setTdtNumeroRepertorio(Integer.valueOf(numRepertorio));
                    tramDet.setTdtPerApellidoPaterno(getTramitesControladorBb().getPersona().getPerApellidoPaterno());
                    tramDet.setTdtPerApellidoMaterno(getTramitesControladorBb().getPersona().getPerApellidoMaterno());
                    tramDet.setTdtPerIdentificacion(getTramitesControladorBb().getPersona().getPerIdentificacion());
                    tramDet.setTdtPerNombre(getTramitesControladorBb().getPersona().getPerNombre());
                    tramDet.setTdtPerTipoContribuyente(" ");
                    tramDet.setTdtPerTipoIdentificacion(getTramitesControladorBb().getPersona().getPerTipoIdentificacion());
                    tramDet.setTdtTpcCodigo(tipoComparec.getTpcCodigo());
                    tramDet.setTdtTpcDescripcion(tipoComparec.getTpcDescripcion());
                    tramDet.setTdtTpcId(tipoComparec.getTpcId());
                    tramDet.setTdtTplId(idLibro);
                    tramDet.setTdtTtrDescripcion(tipoTramite.getTtrDescripcion());
                    tramDet.setTdtTtrId(tipoTramite.getTtrId());
                    tramDet.setTdtUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    tramDet.setTraNumero(repertorio.getTraNumero());
                    tramDet.setTtcId(tipoTramCompareciente);
                    tramDet.setTdtTplDescripcion(tipoTramite.getTtrDescripcion());
                    tramDet.setTdtParId(tramDetalle.getTdtParId());
                    tramDet.setTdtParNombre(tramDetalle.getTdtParNombre());
                    tramDet.setTdtCatastro(tramDetalle.getTdtCatastro());
                    tramDet.setTdtPredio(tramDetalle.getTdtPredio());
                    tramiteDetalleDao.guardarDetalleTramite(tramDet);

                    listarComparecientes();
                    getTramitesControladorBb().setIdentificacion("");
                    getTramitesControladorBb().setNombrePersona("");
                    System.out.println("tramite detalle guardado");
                } else {
                    JsfUtil.addErrorMessage("Error, la persona ya esta agregada");
                }
            } else {
                JsfUtil.addErrorMessage("Error, no se seleccionó una persona.");
            }

        } catch (ServicioExcepcion | NumberFormatException | ParseException e) {
            System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.guardarTramiteDetalle()");
            System.err.println(e);
        }

    }

    public boolean existeTramiteDetalle(Long perId) {
        for (TramiteDetalle tramDeta : listaTramiteDetalle) {
            if (Objects.equals(tramDeta.getPerId().getPerId(), perId)) {
                return true;

            }
        }
        return false;
    }

    public void cargarPropiedadesComparecientes() throws ServicioExcepcion, ParseException {
        listaTramiteDetalle.clear();
        listaPropiedadComparecientes.clear();
        listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                repertorio.getTraNumero().getTraNumero(), repertorio.getRepTtrId(), Long.valueOf(numRepertorio));
        listaRepertorioPropiedad.clear();
        List<RepertorioPropiedad> listRepPropAux = new ArrayList<>();
        listRepPropAux.clear();
        listRepPropAux = repertorioPropiedadDao.buscarPorNumRepertorio(Long.valueOf(numRepertorio));
        //BUSCA SI LOS COMPARECIENTES SON PROPIETARIOS USANDO LA MATRICULA DE REPERTORIO PROPIEDAD PARA AGREGAR A LA LISTA
        for (RepertorioPropiedad repertorioPropiedad : listRepPropAux) {
            for (TramiteDetalle tramDet : listaTramiteDetalle) {
                Propietario propietario = propietarioDao.buscarPropietarioPor_Persona_Matricula_Estado(tramDet.getPerId().getPerId(), repertorioPropiedad.getPrdMatricula().getPrdMatricula(), "A");
                if (propietario != null) {
                    if (!listaRepertorioPropiedad.contains(repertorioPropiedad)) {
                        listaRepertorioPropiedad.add(repertorioPropiedad);
                    }
                }

            }

        }
        for (RepertorioPropiedad repertorioPropiedad : listaRepertorioPropiedad) {
            if (!listaPropiedadComparecientes.contains(repertorioPropiedad.getPrdMatricula())) {
                listaPropiedadComparecientes.add(repertorioPropiedad.getPrdMatricula());
            }
        }
        //BUSCA SI LOS COMPARECIENTES SON PROPIETARIOS (de cualquier propiedad) PARA AGREGAR A LA LISTA
        for (TramiteDetalle tramDet : listaTramiteDetalle) {
            List<Propietario> listPropietario = new ArrayList<>();
            listPropietario.clear();
            listPropietario = propietarioDao.listarPor_Id_Persona(tramDet.getPerId().getPerId());
            for (Propietario propietario : listPropietario) {
                if (!listaPropiedadComparecientes.contains(propietario.getPrdMatricula())) {
                    listaPropiedadComparecientes.add(propietario.getPrdMatricula());
                }

            }

        }

        //elimina de repertorio propiedad 
        for (RepertorioPropiedad repProp : listRepPropAux) {
            if (!listaPropiedadComparecientes.contains(repProp.getPrdMatricula())) {
                repertorioPropiedadDao.remove(repProp);
            }
        }

    }

    public void verGravamenes() throws ServicioExcepcion {
        try {
            listaGravamenMostrar.clear();
            listaGravamen.clear();
//            listaRepertorioPropiedad.clear();
//            listaRepertorioPropiedad = repertorioPropiedadDao.buscarPorNumRepertorio(Long.valueOf(numRepertorio));

            cargarPropiedadesComparecientes();

            //verifcar propiedades unifcados o divididas
            List<Propiedad> listPropVerificadas = new ArrayList<>();
            listPropVerificadas.clear();
            for (Propiedad propied : listaPropiedadComparecientes) {
                Propiedad prop = verificarPropiedad(propied);
                if (!listPropVerificadas.contains(prop)) {
                    listPropVerificadas.add(prop);
                }
            }

            //buscar gravamenes
            for (Propiedad propied : listPropVerificadas) {

                listaGravamen = gravamenDao.buscarPorMatricula(propied);

                if (!listaGravamen.isEmpty() || listaGravamen != null) {
                    listaGravamenMostrar.addAll(listaGravamen);
                }

            }

            //buscar gravamenes
//            for (RepertorioPropiedad repProp : listaRepertorioPropiedad) {
//
//                listaGravamen = gravamenDao.buscarPorMatricula(repProp.getPrdMatricula());
//
//                if (!listaGravamen.isEmpty() || listaGravamen != null) {
//                    listaGravamenMostrar.addAll(listaGravamen);
//                }
//
//            }
            if (listaGravamenMostrar.isEmpty() || listaGravamenMostrar == null) {
                //onCompleteGravamen = "PF('dlgVerGravamenes').hide()";

            } else {
                //onCompleteGravamen ="PF('dlgVerGravamenes').show()";
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dlgVerGravamenes').show();");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void previsualizarActa() throws ServicioExcepcion, ParseException {
//        numSaltosLinea = 0;
//        agregarImagenEspacioEnBlanco=false;

        if (!txtEditCompareciente.equals("") || txtEditCompareciente != null
                || !txtEditOtorgamiento.equals("") || txtEditOtorgamiento != null
                || !txtEditAntecedentes.equals("") || txtEditAntecedentes != null
                || !txtEditObjetoContrato.equals("") || txtEditObjetoContrato != null
                || !txtEditLinderos.equals("") || txtEditLinderos != null
                || !txtEditCuantias.equals("") || txtEditCuantias != null
                || !txtEditGravamenes.equals("") || txtEditGravamenes != null
                || !txtEditObservaciones.equals("") || txtEditObservaciones != null) {

            generarActaParaHTML();
            generarActaParaPDF();//genera el codigo de la acta
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dlgPreviewActa').show();");
        }
//        generarActaParaHTML();
//        generarActaParaPDF();//genera el codigo de la acta

    }

    public void generarPdfActa() {
        try {
            pdfTempUtil.crearPDFconTextoHTML("acta.pdf", actaPDF, "LIB");
            pdfTempUtil.generarLinksAccesoAlPdf();
            urlActaPdf = pdfTempUtil.getUrl();
//            pdfTempUtil.agregarImagenEspacioEnBlanco();
            System.out.println("url: " + pdfTempUtil.getUrl());
            System.out.println("direccionRecurso: " + pdfTempUtil.getDirArchivoComoRecurso());
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarPdfRazon(int numeroCopias) {
        try {
            pdfTempUtil.crearPDFconTextoHTML_Repetir("razon.pdf", razonInscripcion, "RAZ", numeroCopias);
            pdfTempUtil.generarLinksAccesoAlPdf();
            urlRazonPdf = pdfTempUtil.getUrl();
            System.out.println("url: " + pdfTempUtil.getUrl());
            System.out.println("direccionRecurso: " + pdfTempUtil.getDirArchivoComoRecurso());
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarPdfCertificado() {
        try {
            pdfTempUtil.crearPDFconTextoHTML("certificado.pdf", certificadoGenerado, "CER");
            //pdfTempUtil.crearPDFconTextoHTML("certificado.pdf", certificadoTotalGenerado, "CER");
            pdfTempUtil.generarLinksAccesoAlPdf();
            urlCertificadoPdf = pdfTempUtil.getUrl();
            System.out.println("url: " + pdfTempUtil.getUrl());
            System.out.println("direccionRecurso: " + pdfTempUtil.getDirArchivoComoRecurso());
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarActaParaHTML() throws ServicioExcepcion, ParseException {

        if (tramiteDetalle != null && !tramiteDetalle.getTdtTtrDescripcion().trim().equals("TESTAMENTO")) {
            try {
                llenarPorTipoTramite();
                //cabeceraHTML = "";
                //cabeceraHTML = compraventa.generarCabeceraHTML(ttrId, numTramite, numRepertorio, contratoDescripcion);

                String cuerpoActa = "<strong>" + compareciente + "</strong><br></br>"
                        + txtEditCompareciente
                        + "<strong>" + otorgamiento + "</strong><br></br>"
                        + txtEditOtorgamiento
                        + "<strong>" + antecedentes + "</strong><br></br>"
                        + txtEditAntecedentes
                        + "<strong>" + objetoContrato + "</strong><br></br>"
                        + txtEditObjetoContrato
                        + "<strong>" + linderos + "</strong><br></br>"
                        + txtEditLinderos
                        + "<strong>" + cuantias + "</strong><br></br>"
                        + txtEditCuantias
                        + "<strong>" + gravamenes + "</strong><br></br>"
                        + txtEditGravamenes
                        + "<strong>" + observaciones + "</strong>"
                        + txtEditObservaciones + "<br></br><br></br><br></br>" + txtResponsables;
                actaHTML = cabeceraHTML + "<br></br>" + cuerpoActa;
                actaHTML = actaHTML.replaceAll("null", "");

                deshabilitarBotonGuardar = false;

            } catch (Exception e) {
                System.out.println(e);
                actaHTML = "";
            }
        } else {
            setActaHTML(cabeceraHTML + "<br></br>" + getTestamentoString());
            actaHTML = actaHTML.replaceAll("null", "");
            deshabilitarBotonGuardar = false;
        }
//no esta cambiando testamento para actualizar//
        if (bolAgregarImagenEspacioEnBlancoActa) {
            actaPDF += "<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }

    }

    public boolean verificarTipo(Tramite tramiteParam) throws ServicioExcepcion {

        boolean tipo5 = false;
        boolean tipo8 = false;
        boolean verificado = false;
        try {
            List<RepertorioUsuario> listaRepUsuSel = servicioRepertorioUsuario.
                    listarPor_Tramite_Tipo_Estado_Usr("INS", tramiteParam.getTraNumero(), "A", dataManagerSesion.getUsuarioLogeado().getUsuId());

            for (RepertorioUsuario repUsu : listaRepUsuSel) {
                String tipoTram = repUsu.getRepNumero().getRepTtrDescripcion().trim();
                TipoTramite tipoTramiteSelec = servicioTipoTramite.buscarPorDescripcion(tipoTram);

                switch (tipoTramiteSelec.getTtrCodigo()) {
                    case 5:
                        tipo5 = true;
                        break;
                    case 8:
                        tipo8 = true;
                        break;
                }
            }

            TipoTramite tipoTramiteSelecParam = servicioTipoTramite.buscarPorDescripcion(repertorio.getRepTtrDescripcion().trim());
            if (tipo5 && tipo8 && (tipoTramiteSelecParam.getTtrCodigo() == 8)) {
                verificado = true;
            }

//            for (Iterator<RepertorioUsuario> iterator = listaRepUsuSel.iterator(); iterator.hasNext();) {
//                RepertorioUsuario next = iterator.next();
//                String tipoTram = next.getRepNumero().getRepTtrDescripcion().trim();
//                TipoTramite tipoTramiteSelec = servicioTipoTramite.buscarPorDescripcion(tipoTram);
//                if (tipoTramiteSelec.getTplId().getTplPropietario() == null
//                        || !tipoTramiteSelec.getTplId().getTplPropietario()) {
//                    iterator.remove();
//                }
//            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ocurrió un error");
            e.printStackTrace(System.out);
        }
        return verificado;

    }

    public List<Marginacion> obtenerListaMarginacionesAlt3() throws ServicioExcepcion {
        List<Marginacion> listaMarginacionAlt2 = servicioMarginacion.listarPorMrgAlt2(repertorio.getRepNumero().toString());
        List<Marginacion> listaMarginacionAlt3 = new ArrayList<>();
        for (Marginacion marginacion : listaMarginacionAlt2) {
            if (marginacion.getMrgAlt3() != null && !marginacion.getMrgAlt3().isEmpty()) {
                listaMarginacionAlt3.add(marginacion);
            }
        }
        return listaMarginacionAlt3;
    }

    public void preGuardarActa() throws ServicioExcepcion, ParseException {
        tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(Long.valueOf(numTramite));
        List<Marginacion> listaMarginacionAlt3 = obtenerListaMarginacionesAlt3();
        List<Acta> listaActas = new ArrayList<>();

        if (verificarTipo(tramiteSeleccionado) && !listaMarginacionAlt3.isEmpty()) {
            boolean guardarActaCasoEspecial = false;
            for (Marginacion marginacion : listaMarginacionAlt3) {
                Acta actaSelec = actaDao.buscarActaPorNumRepertorio(new Long(marginacion.getMrgAlt3().trim()));
                if (actaSelec == null) {
                    JsfUtil.addErrorMessage("Error, primero debe crear el acta de compraventa");
                } else {
                    guardarActaCasoEspecial = true;
                    Marginacion marginacionBuscar = servicioMarginacion.obtenerPorActNumero_PorNumRepertorio(actaSelec.getActNumero(), repertorio.getRepNumero().toString());
                    if (marginacionBuscar == null) {

                        Marginacion nuevaMarginacion = new Marginacion();
                        //campos requeridos
                        nuevaMarginacion.setMrgUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        nuevaMarginacion.setMrgFHR(Calendar.getInstance().getTime());
                        nuevaMarginacion.setMrgAlt1(marginacion.getMrgAlt1());
                        //campos no requeridos
                        nuevaMarginacion.setTmcId(marginacion.getTmcId());
                        nuevaMarginacion.setActNumero(actaSelec);
                        nuevaMarginacion.setMrgAlt2(marginacion.getMrgAlt2());
                        nuevaMarginacion.setMrgAlt3("");
                        nuevaMarginacion.setMrgFch(repertorio.getRepFHR());
                        servicioMarginacion.create(nuevaMarginacion);
                        JsfUtil.addSuccessMessage("Marginaciones creadas");
                    }
                }
            }
            if (guardarActaCasoEspecial) {
                guardarActa();
            }
        } else {
//            System.out.println("guardado normal");
            guardarActa();
        }

    }

    public void guardarActa() throws ServicioExcepcion, ParseException {
        FacesContext context = FacesContext.getCurrentInstance();
        tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(Long.valueOf(numTramite));

        prepararActaParaGuardar();

        tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
        if (esActaNueva) {
            actaDao.guardarSalida(actaGuardar);
            servicioRepertorioUsuario.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(numRepertorio), "INS");
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, secuenciaControlador.getControladorBb().getNumeroTramite().toString(),
                    "Acta " + secuenciaControlador.getControladorBb().getNumeroTramite()
                    + " creada por el usuario " + dataManagerSesion.getUsuarioLogeado().getUsuLogin(),
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), null);

            //crear gravamen, gravamen detalle
            //if (tipoLibro.getTplGravamen()) {
            //codigo 14: GENERICO COMPRAVENTA 
            if (tipoTramite.getTtrCodigo() == 8 || tipoTramite.getTtrCodigo() == 14) {
                crearGravamen();
            }

            //CREAR GRAVAMEN FIDEICOMISO
            if (tipoTramite.getTtrCodigo() == 12) {
                crearGravamenFideicomiso();
            }

        } else {
            actaDao.edit(actaGuardar);
            servicioRepertorioUsuario.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(numRepertorio), "INS");
            //codigo 14: GENERICO COMPRAVENTA            
            if (tipoTramite.getTtrCodigo() == 8 || tipoTramite.getTtrCodigo() == 14) {
                crearGravamen();
            }
            //CREAR GRAVAMEN FIDEICOMISO
            if (tipoTramite.getTtrCodigo() == 12) {
                crearGravamenFideicomiso();
            }
        }

        //confirmarActa();
        context.addMessage(null, new FacesMessage("Acta Guardada", ""));

        indiceTab = 0;
        disabledTabActa = true;
        disabledTabRazon = false;
        disabledTabCertifcado = true;
        renderedTabRazon = false;
        renderedTabCertifcado = false;
        renderedTabActa = true;
        renderedBtnsTab = false;
        renderedTxtEdit = false;
        deshabilitarBotonGuardar = true;

        if (tipoLibro.getTplPropietario()) {

            listarComparecientes();
            for (TramiteDetalle tramDetalle : listaTramiteDetalle) {

                TipoTramiteCompareciente tipoTramComparec = new TipoTramiteCompareciente();
                tipoTramComparec = tipoTramiteComparecienteDao.buscarPorId(tramDetalle.getTtcId().getTtcId());
                if (tipoTramComparec.getTtcPropietario().equals("S")) {
                    for (Propiedad propied : listaPropiedadComparecientes) {

                        Boolean existePropietario = propietarioDao.existePropietarioPor_Persona_Matricula_Repertorio(tramDetalle.getPerId().getPerId(), propied.getPrdMatricula(), new Long(numRepertorio));
                        if (existePropietario != null && !existePropietario) {
                            Propietario propietario = new Propietario();
                            propietario.setPerId(tramDetalle.getPerId());
                            propietario.setPprPerIdentificacion(tramDetalle.getTdtPerIdentificacion());
                            propietario.setPprPerNombre(tramDetalle.getTdtPerNombre());
                            propietario.setPprPerApellidoPaterno(tramDetalle.getTdtPerApellidoPaterno());
                            propietario.setPpPerApellidoMaterno(tramDetalle.getTdtPerApellidoMaterno());
                            propietario.setPprEstado("A");
                            propietario.setPprFHR(Calendar.getInstance().getTime());
                            propietario.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            propietario.setPrdMatricula(propied);
                            propietario.setPrdRepertorio(Integer.parseInt(numRepertorio));
                            if (tipoTramite.getTtrCodigo() == 12) {
                                propietario.setPrdFideicomiso("S");
                            }
                            propietarioDao.create(propietario);
                        }
                    }
                } else if (tipoTramComparec.getTtcPropietario().equals("N")) {
                    if (tipoTramite.getTtrCodigo() != 12) { //en caso que sea FIDEICOMISO NO CAMBIA EL ESTADO A "I"                         

                        List<Propietario> listaPropietario = new ArrayList<>();
                        for (Propiedad propied : listaPropiedadComparecientes) {
                            listaPropietario.clear();
                            listaPropietario = propietarioDao.buscarPor_Id_Persona_Por_Matricula(tramDetalle.getPerId().getPerId(), propied.getPrdMatricula());
                            for (Propietario propietario : listaPropietario) {
                                propietario.setPprEstado("I");
                                propietarioDao.edit(propietario);
                            }
                            //crea propietario con el nuevo repertorio
                            Boolean existePropietario = propietarioDao.existePropietarioPor_Persona_Matricula_Repertorio_Estado(tramDetalle.getPerId().getPerId(), propied.getPrdMatricula(), new Long(numRepertorio), "I");
                            if (existePropietario != null && !existePropietario) {
                                Propietario propietarioNuevoS = new Propietario();
                                propietarioNuevoS.setPerId(tramDetalle.getPerId());
                                propietarioNuevoS.setPprPerIdentificacion(tramDetalle.getTdtPerIdentificacion());
                                propietarioNuevoS.setPprPerNombre(tramDetalle.getTdtPerNombre());
                                propietarioNuevoS.setPprPerApellidoPaterno(tramDetalle.getTdtPerApellidoPaterno());
                                propietarioNuevoS.setPpPerApellidoMaterno(tramDetalle.getTdtPerApellidoMaterno());
                                propietarioNuevoS.setPprEstado("I");
                                propietarioNuevoS.setPprFHR(Calendar.getInstance().getTime());
                                propietarioNuevoS.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                propietarioNuevoS.setPrdMatricula(propied);
                                propietarioNuevoS.setPrdRepertorio(Integer.parseInt(numRepertorio));
                                propietarioDao.create(propietarioNuevoS);

                            }

                        }
                    }

                }

            }

        }

        llenarDatos();
        numSaltosLinea = 0;
        bolAgregarImagenEspacioEnBlancoActa = false;

    }

    public void crearGravamenFideicomiso() throws ServicioExcepcion {
        listaTramiteDetalle.clear();
        listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                repertorio.getTraNumero().getTraNumero(), repertorio.getRepTtrId(), Long.valueOf(numRepertorio));
        List<PropiedadDetalle> listPropDet = new ArrayList<>();

        for (Propiedad propied : listaPropiedadComparecientes) {
            for (TramiteDetalle tramiteDetalle1 : listaTramiteDetalle) {
                if (tramiteDetalle1.getTtcId().getTtcPropietario().equals("S")) {
                    Propietario propietario = new Propietario();
                    propietario = propietarioDao.buscarPropietarioPor_Persona_Matricula_Estado(tramiteDetalle1.getPerId().getPerId(), propied.getPrdMatricula(), "A");
                    if (propietario != null) {
                        Gravamen gravamenBuscar = gravamenDao.buscarTopPorMatriculaYRepertorioYEstado(repertorio.getRepNumero().intValue(), propied.getPrdMatricula(), "A");
                        Gravamen gravamen;
                        if (gravamenBuscar != null) {
                            gravamen = gravamenBuscar;
                        } else {
                            gravamen = new Gravamen();
                            gravamen.setPrdMatricula(propied);
                            gravamen.setRepNumero(repertorio);
                            gravamen.setGraDescripcion("Gravamen " + tipoLibro.getTplDescripcion());
                            gravamen.setGraEstado("A");
                            gravamen.setGraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            gravamen.setGraFHR(Calendar.getInstance().getTime());
                            gravamenDao.create(gravamen);
                        }

                    }

                }
            }

        }
    }

    public void crearGravamen() throws ServicioExcepcion {
        listaTramiteDetalle.clear();
        listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                repertorio.getTraNumero().getTraNumero(), repertorio.getRepTtrId(), Long.valueOf(numRepertorio));
        List<PropiedadDetalle> listPropDet = new ArrayList<>();

        for (Propiedad propied : listaPropiedadComparecientes) {
            for (TramiteDetalle tramiteDetalle1 : listaTramiteDetalle) {
                if (tramiteDetalle1.getTtcId().getTtcPropietario().equals("S")) {
                    Propietario propietario = new Propietario();
                    propietario = propietarioDao.buscarPropietarioPor_Persona_Matricula_Estado(tramiteDetalle1.getPerId().getPerId(), propied.getPrdMatricula(), "A");
                    if (propietario != null) {
                        Gravamen gravamenBuscar = gravamenDao.buscarTopPorMatriculaYRepertorioYEstado(repertorio.getRepNumero().intValue(), propied.getPrdMatricula(), "A");
                        Gravamen gravamen;
                        if (gravamenBuscar != null) {
                            gravamen = gravamenBuscar;
                        } else {
                            gravamen = new Gravamen();
                            gravamen.setPrdMatricula(propied);
                            gravamen.setRepNumero(repertorio);
                            gravamen.setGraDescripcion("Gravamen " + tipoLibro.getTplDescripcion());
                            gravamen.setGraEstado("A");
                            gravamen.setGraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            gravamen.setGraFHR(Calendar.getInstance().getTime());
                            gravamenDao.create(gravamen);
                        }

                        listPropDet.clear();
                        listPropDet = servicioPropiedadDetalle.listar_Por_Matricula_PorEstadoActivo(propied.getPrdMatricula());
                        for (PropiedadDetalle propiedadDetalle : listPropDet) {

                            if (tramiteDetalle1.getPerId().getPerId() == propiedadDetalle.getPdtPerId().longValue()) {
                                GravamenDetalle gravDet = new GravamenDetalle();
                                GravamenDetalle gravDetBuscar = gravamenDetalleServicio.buscarPor_IdGravamne_IdPersona_Estado(gravamen.getGraId(), tramiteDetalle1.getPerId().getPerId(), "A");
                                if (gravDetBuscar == null) {
                                    gravDet.setGvdEstado("A");
                                    gravDet.setGvdFHR(Calendar.getInstance().getTime());
                                    gravDet.setGvdPorcentaje(propiedadDetalle.getPdtValor());
                                    gravDet.setGvdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                    gravDet.setGraId(gravamen);
                                    gravDet.setPerId(tramiteDetalle1.getPerId());
                                    gravamenDetalleServicio.create(gravDet);
                                }
                            }

                        }
                    }

                }
            }

        }
    }

    public void prepararActaParaGuardar() throws ServicioExcepcion, ParseException {

        try {
            //OBTENER UNA MATRICULA REFERENCIAL
            numMatricula.clear();
            stringMatricula = "";
            String sql = "SELECT PrdMatricula FROM RepertorioPropiedad where RepNumero = " + numRepertorio + " ORDER BY PrdMatricula";
            numMatricula = (repertorioPropiedadDao.ObtenerPropiedadNumMatricula(sql));
            for (String numeroMatricula : numMatricula) {
                System.out.println("ACC NUM MAT: " + numeroMatricula);
                stringMatricula = "";
                stringMatricula = String.join(" ", numeroMatricula);
                matricula.setPrdMatricula(stringMatricula);

            }
            //
            FacesContext context = FacesContext.getCurrentInstance();

            //codigo 14: generico COMPRAVENTA
            //codigo 4: TESTAMENTO
            //codigo 12: FIDEICOMISO
            if (tipoTramite.getTtrCodigo() != null && tipoTramite.getTtrCodigo() != 4 && tipoTramite.getTtrCodigo() != 12 && tipoTramite.getTtrCodigo() != 14) {
                if (txtEditAntecedentes == "" || txtEditAntecedentes == null
                        || txtEditCompareciente == "" || txtEditCompareciente == null
                        || (txtEditCuantias == "" || txtEditCuantias == null)
                        || (txtEditGravamenes == "" || txtEditGravamenes == null)
                        || (txtEditLinderos == "" || txtEditLinderos == null)
                        || (txtEditObjetoContrato == "" || txtEditObjetoContrato == null)
                        || (txtEditObservaciones == "" || txtEditObservaciones == null)
                        || (txtEditOtorgamiento == "" || txtEditOtorgamiento == null)) {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Existen campos vacios", ""));

                } else {

                    generarActaParaHTML();
                    generarActaParaPDF();

                    if (esActaNueva) {

                        Propiedad propiedad = new Propiedad();
                        if (!stringMatricula.equals("")) {
                            propiedad = propiedadDao.encontrarPropiedadPorId(stringMatricula);
                            if (propiedad.getPrdMatricula() == null) {
                                propiedad = propiedadDao.obtenerPorMatricula("0");
                            }
                        } else {
                            propiedad = propiedadDao.obtenerPorMatricula("0");
                        }

                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                        Date today = new Date();

                        Date todayWithZeroTime = formatter.parse(formatter.format(today));

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(today);

                        int anio = calendar.get(Calendar.YEAR);

                        Long idTipoActa = actaDao.buscarTipoActa();

                        // BigInteger numInscripcion = actaInscripcion.getActInscripcion();
                        //BigInteger numInscripcion  = (BigInteger.valueOf(1));
                        // numInscripcion = numInscripcion.add(BigInteger.valueOf(1));
                        // numInscripcion = (BigInteger.valueOf(1));
                        Long tipoAct = new Long(1);
                        //tipoActa.setTpaId(tipoAct);
                        if (!actaHTML.equals("") && !actaPDF.equals("")) {

                            secuenciaControlador.generarSecuencia("ACT");
                            //obtengo el siguiente valor de la secuencia
                            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
                            int auxSecuencia = getSecuencia().getSecActual();
                            getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
                            Long sec = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
                            Long idGenerado = new Long(sec);
                            secuenciaServicio.guardar(getSecuencia());

                            actaGuardar.setActActa(actaHTML);
                            actaGuardar.setActActaPdf(actaPDF);
                            actaGuardar.setActNumero(idGenerado);
                            actaGuardar.setRepNumero(repertorio);
                            actaGuardar.setPrdMatricula(propiedad); //guarda una matricula referencial
                            actaGuardar.setActCatastro(propiedad.getPrdCatastro());
                            actaGuardar.setActPredio(propiedad.getPrdPredio());
                            actaGuardar.setActTomo(0);
                            actaGuardar.setActBis(String.valueOf(0));
                            actaGuardar.setTpaId(tipoActaDao.find(new TipoActa(), idTipoActa));
                            actaGuardar.setActVolumen(new Long(0));
                            actaGuardar.setActFoja(new Long(0));
                            actaGuardar.setActInscripcion(BigInteger.valueOf(0));
                            actaGuardar.setTplId(tipoTramite.getTplId());
                            actaGuardar.setActUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            actaGuardar.setActFHR(Calendar.getInstance().getTime());
                            actaGuardar.setActFch(todayWithZeroTime);
                            actaGuardar.setActAnio(anio);
                            actaGuardar.setActEstado("A");
                            actaGuardar.setActDescripcion1(txtEditCompareciente);
                            actaGuardar.setActDescripcion2(txtEditOtorgamiento);
                            actaGuardar.setActDescripcion3(txtEditAntecedentes);
                            actaGuardar.setActDescripcion4(txtEditObjetoContrato);
                            actaGuardar.setActDescripcion5(txtEditLinderos);
                            actaGuardar.setActDescripcion6(txtEditCuantias);
                            actaGuardar.setActDescripcion7(txtEditGravamenes);
                            actaGuardar.setActDescripcion8(txtEditObservaciones);

                        }

                    } else {
                        actaGuardar = actaEncontrada;
                        actaGuardar.setActActa(actaHTML);
                        actaGuardar.setActActaPdf(actaPDF);
                        actaGuardar.setActDescripcion1(txtEditCompareciente);
                        actaGuardar.setActDescripcion2(txtEditOtorgamiento);
                        actaGuardar.setActDescripcion3(txtEditAntecedentes);
                        actaGuardar.setActDescripcion4(txtEditObjetoContrato);
                        actaGuardar.setActDescripcion5(txtEditLinderos);
                        actaGuardar.setActDescripcion6(txtEditCuantias);
                        actaGuardar.setActDescripcion7(txtEditGravamenes);
                        actaGuardar.setActDescripcion8(txtEditObservaciones);
                    }

                }

            } else if (tipoTramite.getTtrCodigo() != null && (tipoTramite.getTtrCodigo() == 4 || tipoTramite.getTtrCodigo() == 12 || tipoTramite.getTtrCodigo() == 14)) {
                generarActaParaHTML();
                generarActaParaPDF();

                if (esActaNueva) {

                    Propiedad propiedad = new Propiedad();
                    if (!stringMatricula.equals("")) {
                        propiedad = propiedadDao.encontrarPropiedadPorId(stringMatricula);
                        if (propiedad.getPrdMatricula() == null) {
                            propiedad = propiedadDao.obtenerPorMatricula("0");
                        }
                    } else {
                        propiedad = propiedadDao.obtenerPorMatricula("0");
                    }

                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

                    Date today = new Date();

                    Date todayWithZeroTime = formatter.parse(formatter.format(today));

                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(today);

                    int anio = calendar.get(Calendar.YEAR);

                    Long idTipoActa = actaDao.buscarTipoActa();

                    // BigInteger numInscripcion = actaInscripcion.getActInscripcion();
                    //BigInteger numInscripcion  = (BigInteger.valueOf(1));
                    // numInscripcion = numInscripcion.add(BigInteger.valueOf(1));
                    // numInscripcion = (BigInteger.valueOf(1));
                    Long tipoAct = new Long(1);
                    //tipoActa.setTpaId(tipoAct);
                    if (!actaHTML.equals("") && !actaPDF.equals("")) {

                        secuenciaControlador.generarSecuencia("ACT");
                        //obtengo el siguiente valor de la secuencia
                        setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
                        int auxSecuencia = getSecuencia().getSecActual();
                        getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
                        Long sec = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
                        Long idGenerado = new Long(sec);
                        secuenciaServicio.guardar(getSecuencia());

                        actaGuardar.setActActa(actaHTML);
                        actaGuardar.setActActaPdf(actaPDF);
                        actaGuardar.setActNumero(idGenerado);
                        actaGuardar.setRepNumero(repertorio);
                        actaGuardar.setPrdMatricula(propiedad); //guarda una matricula referencial
                        actaGuardar.setActCatastro(propiedad.getPrdCatastro());
                        actaGuardar.setActPredio(propiedad.getPrdPredio());
                        actaGuardar.setActTomo(0);
                        actaGuardar.setActBis(String.valueOf(0));
                        actaGuardar.setTpaId(tipoActaDao.find(new TipoActa(), idTipoActa));
                        actaGuardar.setActVolumen(new Long(0));
                        actaGuardar.setActFoja(new Long(0));
                        actaGuardar.setActInscripcion(BigInteger.valueOf(0));
                        actaGuardar.setTplId(tipoTramite.getTplId());
                        actaGuardar.setActUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        actaGuardar.setActFHR(Calendar.getInstance().getTime());
                        actaGuardar.setActFch(todayWithZeroTime);
                        actaGuardar.setActAnio(anio);
                        actaGuardar.setActEstado("A");
                        actaGuardar.setActDescripcion1("");
                        actaGuardar.setActDescripcion2("");
                        actaGuardar.setActDescripcion3(txtEditCopiaTextObjContr);
                        actaGuardar.setActDescripcion4("");
                        actaGuardar.setActDescripcion5("");
                        actaGuardar.setActDescripcion6("");
                        actaGuardar.setActDescripcion7("");
                        actaGuardar.setActDescripcion8("");
                        actaGuardar.setActDescripcion9(txtEditCopiaTextAdquisicion);
                        actaGuardar.setActDescripcion15(txtEditCopiaTextual);

                        //llenarDatos();
                        //limpiarCamposTxtEdit();
                    }

                } else {
                    actaGuardar = actaEncontrada;
                    actaGuardar.setActActa(actaHTML);
                    actaGuardar.setActActaPdf(actaPDF);
                    actaGuardar.setActDescripcion1("");
                    actaGuardar.setActDescripcion2("");
                    actaGuardar.setActDescripcion3(txtEditCopiaTextObjContr);
                    actaGuardar.setActDescripcion4("");
                    actaGuardar.setActDescripcion5("");
                    actaGuardar.setActDescripcion6("");
                    actaGuardar.setActDescripcion7("");
                    actaGuardar.setActDescripcion8("");
                    actaGuardar.setActDescripcion9(txtEditCopiaTextAdquisicion);
                    actaGuardar.setActDescripcion15(txtEditCopiaTextual);
                }

            }

        } catch (Exception e) {
            System.out.println(e);
            //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo guardar el Acta", ""));
        }

        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No guardar el Acta", ""));
    }

    public void listarComparecientes() throws ServicioExcepcion, ParseException {
        Repertorio repert = new Repertorio();
        repert = repertorioDao.encontrarRepertorioPorId(numRepertorio);
        listaTramiteDetalle.clear();
        listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                repert.getTraNumero().getTraNumero(), repert.getRepTtrId(), Long.valueOf(numRepertorio));

        //listaTipoTramiteCompareciente = tipoTramiteComparecienteDao.listarTipoTramiteComparecientePorTipoTramite(Long.valueOf(repert.getRepTtrId()));
        listaTipoCompareciente.clear();
        listaTipoCompareciente = tipoTramiteComparecienteDao.listarTipoComparecientePorTipoTramite(Long.valueOf(repert.getRepTtrId()));
        if (listaTipoCompareciente != null && !listaTipoCompareciente.isEmpty()) {
            tipoComparecienteId = listaTipoCompareciente.get(0).getTpcId().toString();
        }
        txtEditCompareciente = compraventa.txtCompareciente(numRepertorio, numTramite);

//        for (TramiteDetalle tramiteDetalle1 : listaTramiteDetalle) {
//            if (tramiteDetalle1.getTtcId().getTpcId().getTpcDescripcion().equals("N")) {
//                listaTramDetalleCompN.add(tramiteDetalle);
//            } else if (tramiteDetalle1.getTtcId().getTpcId().getTpcDescripcion().equals("S")) {
//                listaTramDetalleCompS.add(tramiteDetalle);
//            }
//
//        }
    }

    public void generarActaParaPDF() throws ServicioExcepcion, ParseException {

        if (tramiteDetalle != null) {
//CODIGO 4: TESTAMENTO
//CODIGO 12: FIDEICOMISO
//CODIGO 14: GENERICO COMPRAVENTA
            try {
                llenarPorTipoTramite();
//                cabeceraPDF = "";
//                cabeceraPDF = compraventa.generarCabeceraPDF(ttrId, numTramite, numRepertorio, contratoDescripcion);
                tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
                String cuerpoActa = "";
                switch (tipoTramite.getTtrCodigo()) {
                    case 4:
                    case 12:
                    case 14:
                        cuerpoActa = txtEditCopiaTextual;
                        break;
                    default:
                        cuerpoActa = "<p><strong>" + compareciente + "</strong> </p>"
                                + "<p align='justify'>" + txtEditCompareciente + "</p>"
                                + "<p><strong>" + otorgamiento + "</strong> </p>"
                                + "<p align='justify'>" + txtEditOtorgamiento + "</p>"
                                + "<p><strong>" + antecedentes + "</strong> </p>"
                                + "<p align='justify'>" + txtEditAntecedentes + "</p>"
                                + "<p><strong>" + objetoContrato + "</strong> </p>"
                                + "<p align='justify'>" + txtEditObjetoContrato + "</p>"
                                + "<p><strong>" + linderos + "</strong> </p>"
                                + "<p align='justify'>" + txtEditLinderos + "</p>"
                                + "<p><strong>" + cuantias + "</strong> </p>"
                                + "<p align='justify'>" + txtEditCuantias + "</p>"
                                + "<p><strong>" + gravamenes + "</strong> </p>"
                                + "<p align='justify'>" + txtEditGravamenes + "</p>"
                                + "<p><strong>" + observaciones + "</strong> </p>"
                                + "<p align='justify'>" + txtEditObservaciones + "</p>" + "<br></br><br></br><br></br>"
                                + "<p>" + txtResponsables + "</p>";

                }
                actaPDF = "<br></br><p>" + cabeceraPDF + "</p>" + "<br></br>" + cuerpoActa;
                actaPDF = actaPDF.replaceAll("null", "");
                if (bolAgregarImagenEspacioEnBlancoActa) {
                    actaPDF += "<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
                }

                deshabilitarBotonGuardar = false;

                generarPdfActa();

            } catch (Exception e) {
                System.out.println(e);
                actaPDF = "";
            }
        }

    }

    public void buscarActa() {
        try {
            actaEncontrada = actaDao.buscarActaPorNumRepertorio(Long.valueOf(numRepertorio));
            if (actaEncontrada != null) {
                esActaNueva = false;
            } else {
                esActaNueva = true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    /**
     * ******************************** DERECHOS Y ACCIONES
     * *****************************
     */
    public void mostrarPropiedades() throws ServicioExcepcion, ParseException {
        listaRepPropiedad = repertorioPropiedadDao.buscarPorNumRepertorio(Long.valueOf(StrNumRepertorio));
    }

    public void cargarListaComparecientes() {
        try {
            Tramite tramiteSelec = tramiteDao.buscarTramitePorNumero(Long.valueOf(StrNumTramite));
            Repertorio repertorioSeleccionado = repertorioDao.encontrarRepertorioPorId(StrNumRepertorio);
            listaTramDetComparecientes.clear();
            RepertorioUsuario repertorioUsuarioSelec = RepertorioUsuarioDao.obtenerRepUsu_Por_Rep_Usr_Tipo(
                    repertorioSeleccionado.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
            listaTramDetComparecientes = tramiteDetalleDao.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "N");

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void preCrearDetallePropiedad() {

        propiedadDetalleSeleccionada = new PropiedadDetalle();
        propiedadDetalleSeleccionada.setPdtValor(BigDecimal.ZERO);
        propiedadDetalleSeleccionada.setPdtEstado("A");
        propiedadDetalleSeleccionada.setPdtClase("I");
        cargarListaComparecientes();
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

    public void calcularTotalAcciones() {
        try {
            totalAcc = new BigDecimal(0);
//            for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
//                System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.calcularTotalAcciones()");
//                if (propiedadDetalle.getPdtEstado().equals("A")) {
//                    totalAcc = totalAcc.add(propiedadDetalle.getPdtValor()); //totalAcc += propiedadDetalle.getPdtValor();
//                }
//            }
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

    public boolean verificarExiste(BigInteger numPerId) {
        try {
            Repertorio repertorioSeleccionado = repertorioDao.encontrarRepertorioPorId(StrNumRepertorio);
            Tramite tramSelec = repertorioSeleccionado.getTraNumero();
            List<TramiteDetalle> lista = tramiteDetalleDao.listarPor_NumTramite_TipoTramite_Persona(tramSelec.getTraNumero(), repertorioSeleccionado.getRepTtrId(), numPerId);
            return lista.isEmpty();//deshabilito si la lista esta vaica
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

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

    public void crearDetallePropiedadCompradores() throws ServicioExcepcion {
        calcularTotalAcciones();
        Repertorio repertorioSeleccionado = repertorioDao.encontrarRepertorioPorId(StrNumRepertorio);
        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
        listaTramDetComparecientes.clear();
        RepertorioUsuario repertorioUsuarioSelec = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
                repertorioSeleccionado.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
        listaTramDetComparecientes = tramiteDetalleDao.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "S");
        for (TramiteDetalle tramDetCompComprador : listaTramDetComparecientes) {

            if (tramiteDetalleSeleccionado != null) { //verifica si seleccionó un compareciente
                propiedadDetalleSeleccionada = new PropiedadDetalle();
                propiedadDetalleSeleccionada.setPdtClase("C");//CONJUNTO
                propiedadDetalleSeleccionada.setPdtValor(new BigDecimal(100).subtract(totalAcc));
                propiedadDetalleSeleccionada.setPdtEstado("A");
                propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(tramDetCompComprador.getPerId().getPerId()));
                BigInteger idusuario = BigInteger.valueOf(tramDetCompComprador.getPerId().getPerId());
                Persona persona = personaServicio.find(new Persona(), idusuario.longValue());
                propiedadDetalleSeleccionada.setPersona(persona);
                listaPropiedadDetalle.add(propiedadDetalleSeleccionada);
            } else {
                JsfUtil.addErrorMessage("Debe seleccionar una persona");
            }
            generarDescripcion();
        }
        calcularTotalAcciones();

        disabledBtnGuardarDerAcc = false;
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

                if (tramiteDetalleSeleccionado != null) {
                    //propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(personaSeleccionada.getPerId()));
                    //BigInteger idusuario = BigInteger.valueOf(personaSeleccionada.getPerId());
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

    /**
     * *************************************************************************************************
     */
    public void tipoCuantia() {
        if (cuantiaTipo.equals("Exonerado")) {
            disabledRegistroCuantia = true;
        } else {
            disabledRegistroCuantia = false;
        }
    }

    public void razon() throws ServicioExcepcion, ParseException {

        indiceTab = 1;
        disabledTabActa = true;
        disabledTabRazon = false;
        disabledTabCertifcado = true;
        renderedTabRazon = true;
        renderedTabCertifcado = true;
        renderedTabActa = false;
    }

    public void certificado() {
        indiceTab = 2;
        disabledTabActa = true;
        disabledTabRazon = true;
        disabledTabCertifcado = false;
    }

    public void generarCertificado() throws ServicioExcepcion, ParseException {

        Tramite tramite = tramiteR;
        agregarImagen = "noAgregar";
        certificadoGenerado = "";
        String matriculas = "";
        String fechaIngresoCert = "";
        String fechaReferenciasCert = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
        String tipoPropiedad = "";
        String descripcionPropiedad = "";
        String parroquia = "";
        String matriculaCert = "";
        String referencias = "";
        String signoPunt = "";
        int numeroSaltoLinea = 0;
        int count = 0;
        descripcionPropiedadCert = "";
        gravamenesCert = "";
        nombresPropietarios = "";
        antecedentesCert = "";
        cerNumero = "";

        if (saltoLinea == false) {
            if (procesarSumarSaltoLinea == true) {
                numSaltosLineaCertificado = 0;
            }
        }

        numTotalSaltosLinea = 0;
        certificadoGenerado = "";
        for (CertificadoDatos certDat : listaCertificadoDatos) {
            if (certDat.getPropiedad().getPrdMatricula().equals(propiedadCertificadoSeleccionada.getPrdMatricula())) {

                numTotalSaltosLinea = certDat.getNumSaltoLinea();
                numeroSaltoLinea = numTotalSaltosLinea;
                for (int i = 0; i < numSaltosLineaCertificado + numTotalSaltosLinea; i++) {
                    certificadoGenerado += "<br>";
                }

                if (agregarObservacion == false) {
                    observacionCert = certDat.getObservacion();
                }
                if (procesarAgregarImagen == false) {
                    bolAgregarImagenEspacioEnBlancoCertificado = certDat.isAgregarImagen();
                    if (certDat.isAgregarImagen() == true) {
                        agregarImagen = "agregar";
                    } else {
                        agregarImagen = "noAgregar";
                    }

                }

            }

        }
        if (Boolean.TRUE.equals(procesarSumarSaltoLinea)) {
            certificadoGenerado = "";
            for (int i = 0; i < numSaltosLineaCertificado + numTotalSaltosLinea; i++) {
                certificadoGenerado += "<br>";
            }
            numeroSaltoLinea = numSaltosLineaCertificado + numTotalSaltosLinea;
        }
        if (Boolean.TRUE.equals(procesarRestarSaltoLinea)) {
            certificadoGenerado = "";
            for (int i = 0; i < numSaltosLineaCertificado; i++) {
                certificadoGenerado += "<br>";
            }
            numeroSaltoLinea = numSaltosLineaCertificado;
        }

        /*------- Generar Numero Certifiado ------------------------------*/
        FacturaDetalle facturaDet = facturaDetalleDao.PorNumeroTramite(tramiteR.getTraNumero().intValue());
        factura = facturaDet.getFacId();
        String numCertificado = generarNumeroCertificado();
        secuencialCert = obtenerNumeroSecuencia();
        cerNumero = numCertificado;

        /**
         * ********************************************************************
         */
        List<Repertorio> listaRep = new ArrayList<>();

        Acta act = new Acta();;
        listaRep = repertorioDao.listarPorNumTramite(tramite.getTraNumero());
        for (Repertorio repertorio1 : listaRep) {

            act = actaDao.buscarActaPorNumRepertorio(repertorio1.getRepNumero());
            if (act != null) {

                if (act.getActDescripcion3() != null) {
                    antecedentesCert = antecedentesCert + act.getActDescripcion3() + "<br>";
                }
                if (act.getActDescripcion7() != null) {
                    //gravamenesCert = gravamenesCert + act.getActDescripcion7() + "<br>" + act.getActDescripcion8() + "<br>";

                    count++;
                    if (count < listaRep.size()) {
                        signoPunt = ", ";
                    } else {
                        signoPunt = ".";
                    }
                    fechaReferenciasCert = format2.format(act.getActFch());
                    referencias = referencias + fechaReferenciasCert + "-" + repertorio1.getRepNumero()
                            + "-" + act.getActNumero() + signoPunt;
                }
            }

        }
//        List<RepertorioPropiedad> repProp = new ArrayList<>();
//        List<RepertorioPropiedad> repPropMat = new ArrayList<>();
//
        for (Repertorio repertorio1 : listaRep) {
            fechaIngresoCertificado = repertorio1.getRepFHR();
            fechaIngresoCert = format.format(repertorio1.getRepFHR());
        }

        /*------------------------ Crea lista de matriculas sin repetir ---------------*/
//        List<String> listaNumMat = new ArrayList<>();
//        for (RepertorioPropiedad repertorioPropiedad : repPropMat) {
//            if (!listaNumMat.contains(repertorioPropiedad.getPrdMatricula().getPrdMatricula())) {
//                listaNumMat.add(repertorioPropiedad.getPrdMatricula().getPrdMatricula());
//            }
//        }

        /*--------------------- Matriculas, Propietarios ----------------*/
        int aux = 0;
        String signoOrtog;
//        for (String numMat : listaNumMat) {
//            aux++;
//            if (aux < listaNumMat.size()) {
//                signoOrtog = ", ";
//            } else {
//                signoOrtog = ".";
//            }
        matriculas = propiedadCertificadoSeleccionada.getPrdMatricula();
        Propiedad propiedadCert = propiedadCertificadoSeleccionada;
        List<Propietario> listaPropietarios = propietarioDao.buscarPor_PropiedadMatricula_Estado(propiedadCert.getPrdMatricula());
        for (Propietario propietario : listaPropietarios) {
            Persona persona = propietario.getPerId();
            nombresPropietarios = " " + persona.getPerApellidoPaterno().trim()
                    + " " + persona.getPerApellidoMaterno().trim()
                    + " " + persona.getPerNombre().trim() + " con cédula de identidad"
                    + " " + persona.getPerIdentificacion().trim();
            if (persona.getPerIdConyuge() != null && persona.getPerIdConyuge().getPerId() != 0) {
                nombresPropietarios += " casado con"
                        + " " + persona.getPerIdConyuge().getPerApellidoPaterno().trim()
                        + " " + persona.getPerIdConyuge().getPerApellidoMaterno().trim()
                        + " " + persona.getPerIdConyuge().getPerNombre().trim() + " con cédula de identidad"
                        + " " + persona.getPerIdConyuge().getPerIdentificacion().trim() + "." + "<br>";
            }
        }
        tipoPropiedad = propiedadCert.getTpdId().getTpdNombre();
        descripcionPropiedad = propiedadCert.getPrdDescripcion2() + " " + propiedadCert.getPrdDescripcion1();
        parroquia = propiedadCert.getPrdTdtParNombre();
        matriculaCert = propiedadCert.getPrdMatricula();
        descripcionPropiedadCert += descripcionPropiedad
                + ", con matrícula número " + matriculaCert + "<br>";
        //}

        /*---------------- Gravamenes --------------------------*/
        //for (String numMat : listaNumMat) {
        List<Gravamen> listGrav = new ArrayList<>();
        listGrav.clear();
        listGrav = gravamenDao.buscarporMatricula(propiedadCertificadoSeleccionada.getPrdMatricula());
        String gravamen = "";
        aux = 0;
        for (Gravamen gravamen1 : listGrav) {
            aux++;
            if (aux < listGrav.size()) {
                signoOrtog = ", ";
            } else {
                signoOrtog = ".";
            }
            gravamen = gravamen + gravamen1.getGraDescripcion() + signoOrtog;
        }
        gravamenesCert = gravamenesCert + "Matrícula: " + propiedadCertificadoSeleccionada.getPrdMatricula() + ", Gravamen: " + gravamen + "<br>";
        //}
        /*--------------------- Responsable -------------------------- */

        String nombreResponsable = dataManagerSesion.getUsuarioLogeado().getPerId().getPerNombre().trim()
                + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoPaterno().trim()
                + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno().trim();

        /**
         * **************************************************
         */
        if (observacionCert.equals("") || observacionCert == null) {
            observacionCert = "Ninguna";
        }
        institucion = institucionDao.obtenerInstitucion();
        cantonRazon = cantonDao.encontrarCantonPorId(institucion.getInsCanId().toString());

        certificadoGenerado += ""
                + "<p align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + cantonRazon.getCanNombre().toUpperCase() + "</strong></p>"
                + "<p align='center'><strong>CERTIFICADO No.: </strong>" + cerNumero + "</p>"
                + "<p align='center'><strong>FECHA DE INGRESO:</strong>" + " " + fechaIngresoCert + "</p>"
                + "<p><br></p>"
                + "<h3 align='center'><strong>CERTIFICACIÓN</strong></h3>"
                + "<p>&nbsp;</p>"
                + "<p align='justify'><strong>Referencias:</strong>" + " " + referencias + "</p>"
                + "<p align='justify'><strong>Matriculas: </strong>" + matriculas + "</p><br>"
                + "<p align='justify'>El infrascrito Registrador de la Propiedad de este Cantón,"
                + " luego de revisados los índices y libros que reposan en el archivo, "
                + "en legal y debida forma certifica que:</p><br>"
                + "<p><strong>1.- DESCRIPCION DE LA PROPIEDAD:</strong> </p>"
                + "<p align='justify'>" + descripcionPropiedadCert + ".</p>"
                + "<p><strong>2.- PROPIETARIO(S):</strong></p>"
                + "<p align='justify'>"
                + nombresPropietarios
                + "</p>"
                + "<p><strong>3.- FORMA DE ADQUISICION Y ANTECEDENTES:</strong> </p>"
                + "<p align='justify'>" + antecedentesCert + ".</p>"
                + "<p><strong>4.- GRAVAMENES:</strong> </p>"
                + "<p align='justify'>" + gravamenesCert + "</p>"
                + "<p><strong>5.- OBSERVACIONES GENERALES:</strong> </p>"
                + "<p align='justify'>" + observacionCert + ".</p><br><br>"
                + "<p><strong>Responsable:</strong> " + nombreResponsable + "</p><br>"
                + "<p><br></p><p><strong> </strong></p><p><strong>EL REGISTRADOR</strong></p><br>"
                + "<p><strong> </strong>&nbsp;</p><p>" + cerNumero + "</p>";

        /**
         * ** IMAGEN EN BLANCO ***
         */
        if (bolAgregarImagenEspacioEnBlancoCertificado) {
            certificadoGenerado += "<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }

        /**
         * ******
         */
        generarPdfCertificado();
        procesarSumarSaltoLinea = Boolean.FALSE;
        procesarRestarSaltoLinea = Boolean.FALSE;
        saltoLinea = Boolean.FALSE;
        agregarObservacion = Boolean.FALSE;
        procesarAgregarImagen = Boolean.FALSE;
        CertificadoDatos certificadoDatos;
//borra el registro si ya existe y agrega el nuevo registro a la lista
        List<CertificadoDatos> certDatoBorrar = new ArrayList<>();
        if (!listaCertificadoDatos.isEmpty()) {
            for (CertificadoDatos certDatos : listaCertificadoDatos) {
                if (certDatos.getPropiedad().getPrdMatricula().equals(propiedadCertificadoSeleccionada.getPrdMatricula())) {
                    certDatoBorrar.add(certDatos);
                }
            }
            if (listaCertificadoDatos.containsAll(certDatoBorrar)) {
                listaCertificadoDatos.removeAll(certDatoBorrar);
            }
            certificadoDatos = new CertificadoDatos(
                    propiedadCertificadoSeleccionada, fechaIngresoCertificado, antecedentesCert,
                    certificadoGenerado, descripcionPropiedadCert, nombresPropietarios,
                    gravamenesCert, cerNumero, secuencialCert);
            certificadoDatos.setNumSaltoLinea(numeroSaltoLinea);
            certificadoDatos.setObservacion(observacionCert);
            certificadoDatos.setAgregarImagen(bolAgregarImagenEspacioEnBlancoCertificado);
            listaCertificadoDatos.add(certificadoDatos);

        } else {
            certificadoDatos = new CertificadoDatos(
                    propiedadCertificadoSeleccionada, fechaIngresoCertificado, antecedentesCert,
                    certificadoGenerado, descripcionPropiedadCert, nombresPropietarios,
                    gravamenesCert, cerNumero, secuencialCert);
            certificadoDatos.setNumSaltoLinea(numSaltosLineaCertificado + numTotalSaltosLinea);
            certificadoDatos.setObservacion(observacionCert);
            certificadoDatos.setAgregarImagen(bolAgregarImagenEspacioEnBlancoCertificado);
            listaCertificadoDatos.add(certificadoDatos);
        }
        numSaltosLineaCertificado = 0;
        observacionCert = "";
        bolAgregarImagenEspacioEnBlancoCertificado = false;
        /**/

    }

    /*
    public void guardarRazon() throws ServicioExcepcion, ParseException {
        
        if (!razonInscripcion.equals("")) {

            secuenciaControlador.generarSecuencia("RAZ");
            //obtengo el siguiente valor de la secuencia
            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
            int auxSecuencia = getSecuencia().getSecActual();
            getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
            Long sec = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
            Long idGenerado = new Long(sec);
            secuenciaServicio.guardar(getSecuencia());

            ActaRazon actaRazon = new ActaRazon();
            actaRazon.setAtrNumero(idGenerado);
            actaRazon.setAtrFHR(Calendar.getInstance().getTime());
            actaRazon.setAtrUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            actaRazon.setAtrRazonHtml(razonInscripcion);
            actaRazon.setAtrRazonPdf(razonInscripcion);
            actaRazon.setActNumero(actaGuardar);

            actaRazonDao.create(actaRazon);

            deshabilitarBotonGuardar = true;
            renderedTxtEdit = true;

            procesarEnRepertorioUsuario();            

        }
    }

     */
    public void procesarRazon_RepertorioUsuario(Tramite tramite) throws ServicioExcepcion {

        try {
            int numRepUsuActualizados = 0;

//            numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(tramite.getTraNumero(),
//                    dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");
            tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(tramite.getTraNumero());
            List<Repertorio> listaRep = new ArrayList<>();
            listaRep = repertorioDao.listarPorNumTramite(tramite.getTraNumero());
            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(tramite.getTraNumero(), "IMP"));
            if (tramiteAccion == null) {
                //codigo CargaLaboral
                //selecciono un repertorio usuario de referencia
                RepertorioUsuario repUs = new RepertorioUsuario();
                Repertorio rep = new Repertorio();
                rep = listaRep.get(0);
                repUs = (servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(rep.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS"));

                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("IMP", "Impresion", 1);
                cargaLaboralUtil.restarCargaAsignadaAlUsuario(repUs.getUsuId().getUsuId(), repUs.getRpuTipo());
            } else {
                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                int auxCarga = cargaAdd.getCarAsignado() + 1;
                cargaAdd.setCarAsignado((short) auxCarga);
                servicioCargaLaboral.edit(cargaAdd);
            }

            for (Repertorio repertorio1 : listaRep) {
                numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(repertorio1.getRepNumero(),
                        dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
                numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(repertorio1.getRepNumero(),
                        dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");
                RepertorioUsuario repertorioUsuario = new RepertorioUsuario();
                repertorioUsuario.setRpuTipo("IMP");
                repertorioUsuario.setUsuId(usuarioAsignado);
                repertorioUsuario.setRpuEstado("A");
                repertorioUsuario.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                repertorioUsuario.setRpuFHR(Calendar.getInstance().getTime());
                repertorioUsuario.setRepNumero(repertorio1);

                servicioRepertorioUsuario.create(repertorioUsuario);
                tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, secuenciaControladorRazon.getControladorBb().getNumeroTramite().toString(),
                        "Razon: " + secuenciaControladorRazon.getControladorBb().getNumeroTramite()
                        + " asignada al usuario " + usuarioAsignado.getUsuLogin(),
                        tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), usuarioAsignado.getUsuLogin());

            }

            tramiteSeleccionado.setTraEstado("IMP");
            servicioTramite.edit(tramiteSeleccionado);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteSeleccionado.getTraNumero().toString(),
                    "ACTUALIZACIÓN DEL ESTADO DE TRAMITE A 'IMP'",
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), null);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }

    public void guardarCertificado(Tramite tramite) throws ServicioExcepcion {
        try {
            Certificado certificado = new Certificado();
            FacturaDetalle facturaDet = facturaDetalleDao.PorNumeroTramite(tramite.getTraNumero().intValue());
//            factura = facturaDet.getFacId();
            TipoCertificado tipoCert = tipoCertificadoDao.buscarPorNombre("GRAVAMEN");
            String predio = "";
            String catastro = "";
            int cont = 0;
            String simbolo = "";

            //for (RepertorioPropiedad repPropie : repPropMat) {
            //String numCertificado = generarNumeroCertificado();
            //short secuencial = obtenerNumeroSecuencia();
            //String cerNumero = numCertificado;
            for (CertificadoDatos certDat : listaCertificadoDatos) {
                certificado.setFacId(facturaDet.getFacId());
                certificado.setCerCatastro(certDat.getPropiedad().getPrdCatastro());
                certificado.setCerPredio(certDat.getPropiedad().getPrdPredio());
                certificado.setTroId(tipoCert);
                certificado.setCerFechaIngreso(certDat.getFechaIngreso());
                certificado.setCerCuantia(new BigDecimal(0));
                certificado.setCerAdquisicion(certDat.getAntecedentes());
                certificado.setCerCertificado(certDat.getCertficadoPDF());
                certificado.setCerPropiedad(certDat.getDescripcion());
                certificado.setCerPropietario(certDat.getNombrePropiedatarios());
                certificado.setCerEstado("A");
                certificado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                certificado.setCerFHR(Calendar.getInstance().getTime());
                certificado.setCerGravamen(certDat.getGravamenes());
                certificado.setCerObservacion(certDat.getObservacion());
                certificado.setCertificadoPK(new CertificadoPK(certDat.getCertNumero(), certDat.getSecuencial()));
                certificadoDao.create(certificado);

                Tramite tramiteSel = tramiteDao.buscarTramitePorNumero(tramite.getTraNumero());
                guardarCertificadoTramite(certificado, tramiteSel);

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void guardarCertificadoTramite(Certificado cert, Tramite tram) {
        List<Tramite> listaTramite = new ArrayList<>();
        List<Certificado> listaCertificado = new ArrayList<>();
        listaTramite.add(tram);
        listaCertificado.add(cert);
        tram.setCertificadoList(listaCertificado);
        cert.setTramiteList(listaTramite);
        servicioTramite.edit(tram);
        servicioCertificado.edit(cert);
    }

    public String darFormatoANumero(int cantidadCeros, int numero) {
        return String.format("%0" + cantidadCeros + "d", numero);
    }

    public short obtenerNumeroSecuencia() {
        short secuenciaCert = servicioCertificado.obtenerSecuencialSiguiente(factura.getFacId());
//        System.out.println("secuencia: " + secuencia);

        return secuenciaCert;
    }

    public String generarNumeroCertificado() {

        String secuenciaFormateada = darFormatoANumero(3, obtenerNumeroSecuencia());

        String numCertificado = "C" + factura.getFacNumero() + secuenciaFormateada;
        return numCertificado;
    }

    public void verificarEstadoProceso(Tramite tramiteTerm) {
        List<RepertorioUsuario> listaRepUsu = new ArrayList<>();
        for (Repertorio repertor : listaRepertorioFecha) {
            if (repertor.getTraNumero().getTraNumero().longValue() == tramiteTerm.getTraNumero().longValue()) {
                RepertorioUsuario repUsu = RepertorioUsuarioDao.obtenerRepUsu_Por_Rep_Usr_Tipo(repertor.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
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

    public void confirmarActa(Tramite tramite) throws ServicioExcepcion {

        tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(tramite.getTraNumero());

        //usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("IMP", "Impresion", 1);
//        Boolean exiteCargaLaboral = cargaLaboralDao.existeCargaLaboral(dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");
//
//        if (exiteCargaLaboral == true) {
//
//            buscarCargaLaboral = cargaLaboralDao.buscarCargaLaboral(dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");
//            short carga = (short) (buscarCargaLaboral.getCarAsignado() + 1);
//            buscarCargaLaboral.setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
//            buscarCargaLaboral.setCarFHR(Calendar.getInstance());
//            buscarCargaLaboral.setCarTipo("RAZ");
//            buscarCargaLaboral.setCarAsignado(carga);
//            buscarCargaLaboral.setUsuId(dataManagerSesion.getUsuarioLogeado());
//            cargaLaboralDao.edit(buscarCargaLaboral);
//
//        } else {
//            CargaLaboral cargaLab = new CargaLaboral();
//            short carga = 1;
//            cargaLab.setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
//            cargaLab.setCarFHR(Calendar.getInstance());
//            cargaLab.setCarTipo("RAZ");
//            cargaLab.setCarAsignado(carga);
//            cargaLab.setUsuId(dataManagerSesion.getUsuarioLogeado());
//            cargaLaboralDao.create(cargaLab);
//
//        }
        try {
            List<Repertorio> listaRep = new ArrayList<>();
            listaRep = repertorioDao.listarPorNumTramite(tramiteSeleccionado.getTraNumero());
            for (Repertorio repertorio1 : listaRep) {
//                repertorioUsuario = new RepertorioUsuario();
//                repertorioUsuario.setRpuTipo("RAZ");
//                repertorioUsuario.setUsuId(dataManagerSesion.getUsuarioLogeado());
//                repertorioUsuario.setRpuEstado("A");
//                repertorioUsuario.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
//                repertorioUsuario.setRpuFHR(Calendar.getInstance().getTime());
//                repertorioUsuario.setRepNumero(repertorio1);
//
//                servicioRepertorioUsuario.create(repertorioUsuario);

                List<Acta> listaActa = new ArrayList<>();
                listaActa = actaDao.listarActaPorNumRepertorio(repertorio1.getRepNumero().intValue());
                for (Acta acta : listaActa) {
                    tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, acta.getActNumero().toString(),
                            "Acta " + acta.getActNumero()
                            + " asignada al usuario " + dataManagerSesion.getUsuarioLogeado().getUsuLogin(),
                            tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), null);
                }
                int numRepUsuActualizados = 0;
//                numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(repertorio1.getRepNumero(),
//                        dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
                System.out.println("repertorios actualizados: " + numRepUsuActualizados);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        tramiteSeleccionado.setTraEstado("RAZ");
//        servicioTramite.edit(tramiteSeleccionado);
//        tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteSeleccionado.getTraNumero().toString(),
//                "ACTUALIZACIÓN DEL ESTADO DE TRAMITE A 'RAZ'",
//                tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), dataManagerSesion.getUsuarioLogeado().getUsuLogin());

    }

    public void clear() {
        cuantiaAlcabalas = (float) 0.0;
        cuantiaMonto = (float) 0.0;
        cuantiaRegistro = (float) 0.0;
        cuantiaPlusvalia = (float) 0.0;
    }

    public void txtRazonInscripcion(Tramite tramite) throws ServicioExcepcion, ParseException {
        try {
            razonInscripcion = "";
            for (int i = 0; i < numSaltosLineaRazon; i++) {
                razonInscripcion += "<br>";
            }
            String linea2 = "";
            List<RepertorioPropiedad> listRepProp = new ArrayList<>();
            List<TramiteDetalle> listTramDet = new ArrayList<>();
            List<String> listaNumMat = new ArrayList<>();
            List<Repertorio> listRepert = new ArrayList<>();
            listaRepertorioPropR.clear();
            listaTramiteDetalle.clear();
            for (Repertorio repertor : listaRepertorioFecha) {
                listRepProp.clear();
                listTramDet.clear();
                if (repertor.getTraNumero().getTraNumero().longValue() == tramite.getTraNumero().longValue()) {
                    //tipoTramite = tipoTramiteDao.buscarPorDescripcion(repertor.getRepTtrDescripcion());
                    tipoTramite = tipoTramiteDao.buscarPorID(Long.valueOf(String.valueOf(repertor.getRepTtrId())));
                    tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
                    Acta act = actaDao.buscarActaPorNumRepertorio(repertor.getRepNumero());
                    linea2 = linea2 + tipoTramite.getTplId().getTplDescripcion().toUpperCase()
                            + ", " + "tomo " + act.getActTomo() + ", repertorio(s) - " + repertor.getRepNumero() + ".<br><br>";
                    if (!listRepert.contains(repertor)) {
                        listRepert.add(repertor);
                    }

                    listRepProp = repertorioPropiedadDao.buscarPorNumRepertorio(repertor.getRepNumero());

                    listaRepertorioPropR.addAll(listRepProp);

                    //Crea lista de matriculas sin repetir
                    for (RepertorioPropiedad repertorioPropiedad : listaRepertorioPropR) {
                        if (!listaNumMat.contains(repertorioPropiedad.getPrdMatricula().getPrdMatricula())) {
                            listaNumMat.add(repertorioPropiedad.getPrdMatricula().getPrdMatricula());
                        }
                    }
                    /**/

                    System.out.println("ttrId: " + repertor.getRepTtrId());
                    System.out.println("ttrId: " + repertor.getRepTtrDescripcion());

                    Repertorio repert = repertorioDao.encontrarRepertorioPorId(repertor.getRepNumero().toString());
                    System.out.println("RttrId: " + repert.getRepTtrId());
                    System.out.println("RttrId: " + repert.getRepTtrDescripcion());
                    listTramDet = tramiteDetalleDao.listarPorNumTramiteYporIdTipoTramite(
                            repert.getTraNumero().getTraNumero(), repert.getRepTtrId());
                    listaTramiteDetalle.addAll(listTramDet);
                }
            }

            institucion = institucionDao.obtenerInstitucion();
            cantonRazon = cantonDao.encontrarCantonPorId(institucion.getInsCanId().toString());

            String titulo = "<p align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN "
                    + cantonRazon.getCanNombre() + "</strong></p><br></br>"
                    + "<p align='center'><strong>Razón de Inscripción </strong></p><br></br><br></br>";

            String linea1 = "Con esta fecha queda inscrita la presente escritura en el: <br></br><br></br>";

            String txtMatriculas = "Matrículas Asignadas.-<br></br>";
            String lineasTodasMatriculas = "";
            //verificar propiedades unificadas o divididas
            List<Propiedad> listPropVerificadas = new ArrayList<>();
            listPropVerificadas.clear();
            for (String numMat : listaNumMat) {
                Propiedad propiedad = propiedadDao.encontrarPropiedadPorId(numMat);
                Propiedad propVerificada = verificarPropiedad(propiedad);
                if (!listPropVerificadas.contains(propVerificada)) {
                    listPropVerificadas.add(propVerificada);
                }
            }

            for (Propiedad propiedadVerif : listPropVerificadas) {
//                Propiedad propiedad = new Propiedad();
//                propiedad = propiedadDao.encontrarPropiedadPorId(numMat);
                String lineasMatriculas = propiedadVerif.getPrdTdtParNombre() + " "
                        + propiedadVerif.getPrdMatricula()
                        + " Departamento situado en la parroquia "
                        + propiedadVerif.getPrdTdtParNombre()
                        + " de este Cantón"
                        + " Catastro: " + propiedadVerif.getPrdCatastro()
                        + " Predio: " + propiedadVerif.getPrdPredio()
                        + "<br></br>";
                lineasTodasMatriculas = lineasTodasMatriculas + lineasMatriculas;

            }

            String MES[] = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
            String DIASEMANA[] = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
            String DIA[] = {"UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE",
                "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISÉIS", "DIECISIETE,",
                "DIECIOCHO", "DIECINUEVE", "VEINTE", "VEINTIUNO", "VEINTIDÓS", "VEINTITRES", "VEINTICUATRO",
                "VEINTICINCO", "VEINTISÉIS", "VEINTISIETE", "VEINTIOCHO", "VEINTINUEVE", "TREINTA",
                "TREINTA Y UNO"};

            Calendar calendar = Calendar.getInstance();

            Date hora = calendar.getTime();

            String strDateFormat = "hh:mm:ss a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate = dateFormat.format(hora);

            Repertorio rep = new Repertorio();
            rep = repertorioDao.encontrarRecientePorTramite(tramite.getTraNumero());
            String fecha = "";
            fecha = fechasUtil.convertirDiaSemana_dia_mesLetras_anio_hhmmss_am_pm(rep.getRepFHR());
//            fecha = DIASEMANA[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", "
//                    + calendar.get(Calendar.DAY_OF_MONTH) + " "
//                    + MES[calendar.get(Calendar.MONTH)] + " "
//                    + calendar.get(Calendar.YEAR) + ", "
//                    + formattedDate;

            String tituloRegistrador = "EL REGISTRADOR <br></br><br></br>";
            String txtContrantes = "Contratantes.-";

            String lineasNombresContratantes = "";
            for (TramiteDetalle tramiteDet : listaTramiteDetalle) {
                if (tramiteDet.getTdtEstado().equals("A")) {

                    String apMaterno = "";
                    if (tramiteDet.getTdtPerApellidoMaterno() != null) {
                        apMaterno = tramiteDet.getTdtPerApellidoMaterno();
                    } else {
                        apMaterno = "";
                    }

                    String nombreContratante = tramiteDet.getTdtPerNombre().trim()
                            + " " + tramiteDet.getTdtPerApellidoPaterno().trim()
                            + " " + apMaterno.trim()
                            + " en su calidad de " + tramiteDet.getTdtTpcDescripcion().trim() + "."
                            + "<br></br>";
                    lineasNombresContratantes = lineasNombresContratantes + nombreContratante;
                }

            }

            String textoFinal = "Los número de matrícula le servirán para cualquier trámite posterior.<br></br><br></br>"
                    + "Responsables.- <br></br>";
            //ASESOR
            String apellidoMaternoAsesor = "";
            if (dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno() != null) {
                apellidoMaternoAsesor = dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno().trim();
            }
            String nombreAsesor = dataManagerSesion.getUsuarioLogeado().getPerId().getPerNombre().trim()
                    + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoPaterno().trim()
                    + " " + apellidoMaternoAsesor;
            //REVISOR
            String apellidoMaternoRevisor = "";
            TramiteUsuario tramUsuario = new TramiteUsuario();
            tramUsuario = serviciotramiteUsuario.buscarPorTramite(tramite);
            if (tramUsuario.getUsuId().getPerId().getPerApellidoMaterno() != null) {
                apellidoMaternoRevisor = tramUsuario.getUsuId().getPerId().getPerApellidoMaterno().trim();
            }
            String nombreRevisor = tramUsuario.getUsuId().getPerId().getPerNombre().trim()
                    + " " + tramUsuario.getUsuId().getPerId().getPerApellidoPaterno().trim()
                    + " " + apellidoMaternoRevisor;

            String asesor = "Asesor.- " + nombreAsesor + "<br></br>";
            String revisor = "Revisor.- " + nombreRevisor + "<br></br><br></br><br></br>";
            String numRazon = "RR-";

            razonInscripcion += titulo + linea1 + linea2 + txtMatriculas
                    + lineasTodasMatriculas + "<br></br><br></br>" + fecha
                    + "<br></br><br></br><br></br><br></br><br></br><br></br>" + tituloRegistrador
                    + txtContrantes + "<br></br>" + lineasNombresContratantes
                    + "<br></br><br></br>" + textoFinal
                    + asesor + revisor + numRazon;

            if (bolAgregarImagenEspacioEnBlancoRazon) {
                razonInscripcion += "<br><img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
            }
            //String copiaRazon = razonInscripcion;
//               if(rep.getRepNumeroImpresion() != null){
//                for(int i = 1; i <= rep.getRepNumeroImpresion()-1; i++){                    
//                    razonInscripcion = razonInscripcion + "<div class=\"pagebreak\"> </div>" + copiaRazon;
//                
//            }
//            }

            generarPdfRazon(rep.getRepNumeroImpresion() - 1);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public Propiedad verificarPropiedad(Propiedad propiedad) {
        Propiedad propiedadEncontrada;
        PropiedadDetalle propDet = new PropiedadDetalle();
        propDet = servicioPropiedadDetalle.obtener_Por_PdtPrdMatricula_EstadoActivo(propiedad.getPrdMatricula(), "A");
        if (propDet != null) {
            switch (propDet.getPdtTipo()) {
                case "UNF":
                    propiedadEncontrada = propDet.getPrdMatricula();
                    break;
                case "DIV":
                    propiedadEncontrada = propiedad;
                    break;
                default:
                    propiedadEncontrada = propiedad;
                    break;
            }
        } else {
            propiedadEncontrada = propiedad;
        }
        return propiedadEncontrada;
    }

    public void terminarProceso(Tramite tramite) {
        try {
            observacionCert = "";
            tramiteR = new Tramite();
            tramiteR = tramite;
            FacesContext context = FacesContext.getCurrentInstance();
            //guardarActa();
            /**
             * ** GENERAR LISTA PROPIEDADES **************************
             */

            List<Repertorio> listaRep = new ArrayList<>();
            listaRep = repertorioDao.listarPorNumTramite(tramite.getTraNumero());
            List<RepertorioPropiedad> repProp = new ArrayList<>();
            listaPropiedadCertificado.clear();
            for (Repertorio repertorio1 : listaRep) {
                repProp.clear();
                repProp = repertorioPropiedadDao.buscarPorNumRepertorio(repertorio1.getRepNumero());
                for (RepertorioPropiedad repertorioPropiedad : repProp) {
                    Propiedad prop = verificarPropiedad(repertorioPropiedad.getPrdMatricula());
                    if (!listaPropiedadCertificado.contains(prop)) {
                        listaPropiedadCertificado.add(prop);
                    }

                }

            }
            /**
             * **********************************************************
             */

            verificarEstadoProceso(tramite);
            if (todosEstadoProcTerminado) {

                txtRazonInscripcion(tramite);

                //generarCertificado();
                renderedBtnGuardar = true;
                indiceTab = 0;
                disabledTabActa = true;
                disabledTabRazon = false;
                disabledTabCertifcado = false;

                renderedBtnsTab = false;
                renderedTxtEdit = false;
                renderedTabActa = false;
                renderedTabRazon = true;
                renderedTabCertifcado = true;
                renderedTabView = true;

                //guardarRazon();
                //guardarCertificado();
                //guardarRazon();
                //guardarCertificado();
            } else {

                String mensaje = "Aún existen Procesos sin Terminar";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aún existen Procesos sin Terminar", mensaje));

            }

//            servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(Long.valueOf(repertorio),
//                    dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");
//
//            tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(Long.valueOf(tramite));
            //llenarDatos();
            //limpiarCamposTxtEdit();
            numSaltosLinea = 0;
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error al Guardar", ""));
            System.out.println(e);
        }

    }

    public void reset() {
        indiceTab = 0;
        disabledTabActa = true;
        disabledTabRazon = true;
        disabledTabCertifcado = true;
        renderedBtnsTab = false;
        renderedTxtEdit = false;
    }

    public void guardarRazon() throws ServicioExcepcion {
        secuenciaControladorRazon.generarSecuencia("RAZ");
        //obtengo el siguiente valor de la secuencia
        setSecuenciaRaz(getSecuenciaControladorRazon().getControladorBb().getSecuencia());
        int auxSecuencia = getSecuenciaRaz().getSecActual();
        getSecuenciaRaz().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
        Long sec = new Long(secuenciaControladorRazon.getControladorBb().getNumeroTramite());
        Long idGenerado = new Long(sec);
        secuenciaServicio.guardar(getSecuenciaRaz());
        Razon razon = new Razon();
        razon.setRazId(idGenerado);
        razon.setTraNumero(tramiteR);
        razon.setRazHtml(razonInscripcion);
        razon.setRazFHR(Calendar.getInstance().getTime());
        razon.setRazUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        razonNuevaDao.create(razon);

        procesarRazon_RepertorioUsuario(tramiteR);

    }

    public void guardar() throws ServicioExcepcion, ParseException {
        List<Repertorio> listaRep = new ArrayList<>();
        List<RepertorioPropiedad> repProp = new ArrayList<>();
        List<RepertorioPropiedad> repPropMat = new ArrayList<>();
        listaRep = repertorioDao.listarPorNumTramite(tramiteR.getTraNumero());
        List<Propiedad> listPropiedad = new ArrayList<>();
        //obtener todas las Propiedades
        for (Repertorio repertorio1 : listaRep) {
            repProp.clear();
            repProp = repertorioPropiedadDao.buscarPorNumRepertorio(repertorio1.getRepNumero());
            for (RepertorioPropiedad repertorioPropiedad : repProp) {
                if (!listPropiedad.contains(repertorioPropiedad.getPrdMatricula())) {
                    listPropiedad.add(repertorioPropiedad.getPrdMatricula());
                }
            }

        }
        //obtener propiedades que faltan por generar certificado
        for (Propiedad propied : listPropiedad) {
            for (CertificadoDatos certifDato : listaCertificadoDatos) {
                if (certifDato.getPropiedad().getPrdMatricula().equals(propied.getPrdMatricula())) {

                }
            }

        }
        if (listaCertificadoDatos.size() == listPropiedad.size()) {

            confirmarActa(tramiteR);
            guardarRazon();
            guardarCertificado(tramiteR);

            /* CAMBIAR A ESTADO INACTIVO DE LAS ACTAS */
            List<Repertorio> listRep = new ArrayList<>();
            listRep = repertorioDao.listarPorNumTramite(tramiteR.getTraNumero());
            for (Repertorio repertorio1 : listRep) {
                TipoTramite tipoTamRepActual = tipoTramiteDao.buscarID(repertorio1.getRepTtrId());
                List<Marginacion> listMarg = new ArrayList<>();
                listMarg = servicioMarginacion.listarPorMrgAlt2(repertorio1.getRepNumero().toString());
                for (Marginacion marginacion : listMarg) {
                    Repertorio repert = new Repertorio();
                    repert = repertorioDao.encontrarRepertorioPorId(marginacion.getMrgAlt2());
                    TipoTramite tipoTam = new TipoTramite();
                    tipoTam = tipoTramiteDao.buscarID(repert.getRepTtrId());
                    if (tipoTam.getTplId().getTplPropietario() == true) {
                        if (tipoTam.getTtrCodigo() != null && tipoTamRepActual.getTtrCodigo() != null) {

                            if (tipoTam.getTtrCodigo().intValue() == tipoTamRepActual.getTtrCodigo().intValue()) {
                                Acta actaEditEstado = new Acta();
                                actaEditEstado = actaDao.obtenerActaPorNumeroActa(marginacion.getActNumero().getActNumero());
                                actaEditEstado.setActEstado("I");
                                actaDao.edit(actaEditEstado);
                            }
                        }
                    }

                }
            }

            llenarDatos();
            indiceTab = 0;
            disabledTabActa = true;
            disabledTabRazon = true;
            disabledTabCertifcado = true;
            renderedBtnsTab = false;
            renderedTxtEdit = false;
            renderedTabActa = true;
            renderedTabRazon = false;
            renderedTabCertifcado = false;
            renderedBtnGuardar = false;
            renderedTabView = false;
            listaCertificadoDatos.clear();
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            String mensaje = "Aún no se han revisado los Certificados de todas las propiedades.";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso. Aún no se han revisado los Certificados de todas las propiedades.", mensaje));

        }

    }

    public void observacionCertificado() {
        observacionCert = "";
    }

}

class CertificadoDatos {

    private Propiedad propiedad;
    private Date fechaIngreso;
    private String antecedentes;
    private String certficadoPDF;
    private String descripcion;
    private String nombrePropiedatarios;
    private String gravamenes;
    private String certNumero;
    private Short secuencial;
    private String observacion;
    private boolean agregarImagen;
    private int numSaltoLinea;

    public CertificadoDatos(Propiedad propiedad, Date fechaIngreso, String antecedentes, String certficadoPDF, String descripcion, String nombrePropiedatarios, String gravamenes, String certNumero, Short secuencial) {
        this.propiedad = propiedad;
        this.fechaIngreso = fechaIngreso;
        this.antecedentes = antecedentes;
        this.certficadoPDF = certficadoPDF;
        this.descripcion = descripcion;
        this.nombrePropiedatarios = nombrePropiedatarios;
        this.gravamenes = gravamenes;
        this.certNumero = certNumero;
        this.secuencial = secuencial;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getCertficadoPDF() {
        return certficadoPDF;
    }

    public void setCertficadoPDF(String certficadoPDF) {
        this.certficadoPDF = certficadoPDF;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombrePropiedatarios() {
        return nombrePropiedatarios;
    }

    public void setNombrePropiedatarios(String nombrePropiedatarios) {
        this.nombrePropiedatarios = nombrePropiedatarios;
    }

    public String getGravamenes() {
        return gravamenes;
    }

    public void setGravamenes(String gravamenes) {
        this.gravamenes = gravamenes;
    }

    public String getCertNumero() {
        return certNumero;
    }

    public void setCertNumero(String certNumero) {
        this.certNumero = certNumero;
    }

    public Short getSecuencial() {
        return secuencial;
    }

    public void setSecuencial(Short secuencial) {
        this.secuencial = secuencial;
    }

    public int getNumSaltoLinea() {
        return numSaltoLinea;
    }

    public void setNumSaltoLinea(int numSaltoLinea) {
        this.numSaltoLinea = numSaltoLinea;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isAgregarImagen() {
        return agregarImagen;
    }

    public void setAgregarImagen(boolean agregarImagen) {
        this.agregarImagen = agregarImagen;
    }

}
