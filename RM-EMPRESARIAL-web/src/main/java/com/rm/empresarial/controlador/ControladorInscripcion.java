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
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.AccidenteDao;
import com.rm.empresarial.dao.ActaDao;
import com.rm.empresarial.dao.ActaRazonDao;
import com.rm.empresarial.dao.CantonDao;
import com.rm.empresarial.dao.CargaLaboralDao;
import com.rm.empresarial.dao.CertificadoDao;
import com.rm.empresarial.dao.FacturaDetalleDao;
import com.rm.empresarial.dao.FormatoTramiteDao;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.GravamenDetalleDao;
import com.rm.empresarial.dao.InscripcionDao;
import com.rm.empresarial.dao.LinderoDao;
import com.rm.empresarial.dao.ListaLinderoSuperficieDao;
import com.rm.empresarial.dao.ListaLinderosDao;
import com.rm.empresarial.dao.ListaTxtComparecienteDao;
import com.rm.empresarial.dao.MarginacionDao;
import com.rm.empresarial.dao.NotariaDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.PropietarioDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TipoActaDao;
import com.rm.empresarial.dao.TipoCertificadoDao;
import com.rm.empresarial.dao.TipoComparecienteDao;
import com.rm.empresarial.dao.TipoLibroDao;
import com.rm.empresarial.dao.TipoTramiteClaseDao;
import com.rm.empresarial.dao.TipoTramiteComparecienteDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.dao.TramiteValorDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Accidente;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.ActaRazon;
import com.rm.empresarial.modelo.Canton;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoPK;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FormatoTramite;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.GravamenDetalle;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.ListaLinderoSuperficie;
import com.rm.empresarial.modelo.ListaLinderos;
import com.rm.empresarial.modelo.ListaTxtCompareciente;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import com.rm.empresarial.modelo.Propietario;
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
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.PropiedadDetalleServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoSuspensionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 * @author Marco
 */
@Named(value = "controladorInscripcion")
@ViewScoped
public class ControladorInscripcion implements Serializable {

    List<String> listFinal = new ArrayList<>();

    //String[] info = new String[2];
    @Getter
    @Setter
    private List<String> variablesTexto = new ArrayList<>();

    private List<String> listTxtCompareciente = new ArrayList<>();
    private List<ListaLinderos> listListaLinderos = new ArrayList<>();
    private List<ListaLinderoSuperficie> listListaLinderosSuperficie = new ArrayList<>();
    private List<String> numMatricula = new ArrayList<>();
    private List<String> listaPropiedad = new ArrayList<>();
    private List<String> listaPropiedadGrav = new ArrayList<>();
    private List<String> TraNotaria = new ArrayList<>();

    @Getter
    @Setter
    private List<RepertorioPropiedad> listaRepertorioPropiedad = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoTramiteCompareciente> listaTipoTramiteCompareciente = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoCompareciente> listaTipoCompareciente = new ArrayList<>();

    @Getter
    @Setter
    List<TramiteDetalle> listaTramiteDetalle = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaProp = new ArrayList<>();

    @Getter
    @Setter
    private List<Repertorio> listaRepertorioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<Repertorio> preListaRepertorioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<Lindero> listaLindero = new ArrayList<>();

    @Getter
    @Setter
    private List<Lindero> listaLinderoFinal = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetalleCompS = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetalleCompN = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValorCuantia = new ArrayList<>();

    @Getter
    @Setter
    private List<FormatoTramite> formatoTramiteList = new ArrayList<>();

    @Getter
    @Setter
    private List<Accidente> listaAccidente = new ArrayList<>();

    @Getter
    @Setter
    private List<RepertorioPropiedad> listaRepPropiedad = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetComparecientes = new ArrayList<>();

    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropDetActualizarEstado = new ArrayList<>();

    @Getter
    @Setter
    private List<Gravamen> listaGravamen = new ArrayList<>();

    @Getter
    @Setter
    private List<Gravamen> listaGravamenMostrar = new ArrayList<>();

    @Getter
    @Setter
    private List<String> listaAccidentePorMatricula = new ArrayList<>();

    @Getter
    @Setter
    private List<Canton> listaCanton = new ArrayList<>();

    @Getter
    @Setter
    private List<Notaria> listaNumeroNotario = new ArrayList<>();

    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropiedadDetalle = new ArrayList<>();

    @Getter
    @Setter
    private String[] mostrarTexto;

    @Getter
    @Setter
    private int auxGravamen;

    @Getter
    @Setter
    private Propiedad matricula = new Propiedad();

    @Getter
    @Setter
    private Propiedad propiedad = new Propiedad();
    
    @Getter
    @Setter
    private Tramite tramSeleccTerminar = new Tramite();

    @Getter
    @Setter
    String prdMatriculaAux;

    @Getter
    @Setter
    String prdMatricula;

    @Getter
    @Setter
    private List<String> mostrarTextoLista = new ArrayList<>();

    @Getter
    @Setter
    private String txtEditCompareciente;

    @Getter
    @Setter
    private float cuantiaMonto;

    @Getter
    @Setter
    private String cuantiaTipo;

    @Getter
    @Setter
    private String tipoComparecienteId;

    @Getter
    @Setter
    private Long ttrId;
    
    @Getter
    @Setter
    private int indiceTab;

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
    private String cabeceraHTML;

    @Getter
    @Setter
    private String cabeceraPDF;

    @Getter
    @Setter
    private String stringMatricula;

    @Getter
    @Setter
    private String txtEditGravamenesTemp;

    @Getter
    @Setter
    private String[] partsLindero;

    @Getter
    @Setter
    private String[] partsSuperficie;

    @Getter
    @Setter
    private String[] partsGravamenes;

    @Getter
    @Setter
    private String[] partsMatricula;

    @Getter
    @Setter
    private String[] partsMatriculaGravamenes;

    @Getter
    @Setter
    private String tituloCabecera;
    
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
    
    /*****************************************/

    @Getter
    @Setter
    private String txtEditAntecedentes;

    @Getter
    @Setter
    private String txtEditOtorgamiento;

    @Getter
    @Setter
    private String txtEditObjetoContrato;

    @Getter
    @Setter
    private String txtEditLinderos;

    @Getter
    @Setter
    private String txtLinderosDescripcion1;

    @Getter
    @Setter
    private String txtLinderosSuperficie;

    @Getter
    @Setter
    private String txtLind;

    @Getter
    @Setter
    private String txtEditCuantias;

    @Getter
    @Setter
    private String txtEditGravamenes;

    @Getter
    @Setter
    private String txtEditObservaciones;

    @Getter
    @Setter
    private BigDecimal totalAcc;
    
    @Getter
    @Setter
    private String certificadoGenerado;

    @Getter
    @Setter
    private PropiedadDetalle propiedadDetalleSeleccionada;

    @Getter
    @Setter
    private String libro;

    @Getter
    @Setter
    private String tempGravamen;
    
    @Getter
    @Setter
    private Factura factura;

    @Getter
    @Setter
    private boolean renderedTxtEdit;

    @Getter
    @Setter
    private CargaLaboral buscarCargaLaboral;

    @Getter
    @Setter
    private Boolean disabledBtnGuardarDerAcc = false;

    @Getter
    @Setter
    private Boolean disabledBtnAgregarDerAcc = false;
    
    @Getter
    @Setter
    private Boolean renderedBtnsTab = false;

    @EJB
    private InscripcionDao inscripcionDao;

    @EJB
    private RepertorioPropiedadDao repertorioPropiedadDao;

    @EJB
    private TramiteDetalleDao tramiteDetalleDao;

    @EJB
    private ListaTxtComparecienteDao listaTxtComparecienteDao;

    @EJB
    private AccidenteDao accidenteDao;

    @EJB
    private PropiedadDetalleServicio servicioPropiedadDetalle;
    
    @EJB
    private CertificadoServicio servicioCertificado;

    @EJB
    private PropiedadDao propiedadDao;

    @EJB
    private TramiteDao tramiteDao;
    
    @EJB
    private CertificadoDao certificadoDao;

    @EJB
    private TipoComparecienteDao tipoComparecienteDao;

    @EJB
    private ListaLinderoSuperficieDao listaLinderoSuperficieDao;

    @EJB
    private RepertorioUsuarioDao RepertorioUsuarioDao;

    @EJB
    private TramiteValorDao tramiteValorDao;

    @EJB
    private ActaDao actaDao;

    @EJB
    private PropietarioDao propietarioDao;

    @EJB
    private TipoTramiteComparecienteDao tipoTramiteComparecienteDao;

    @EJB
    private LinderoDao linderoDao;

    @EJB
    private CargaLaboralDao cargaLaboralDao;

    @EJB
    private TipoActaDao tipoActaDao;

    @EJB
    private TipoLibroDao tipoLibroDao;

    @EJB
    private RepertorioDao repertorioDao;
    
    @EJB
    private TipoCertificadoDao tipoCertificadoDao;

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;
    
    @Inject
    @Getter
    @Setter
    private ControladorRazon controladorRazon;

    @Getter
    @Setter
    private Date FHR;

    @Getter
    @Setter
    private Repertorio repertorioNum = new Repertorio();

    @Getter
    @Setter
    private RepertorioPropiedad repertorioPropiedad = new RepertorioPropiedad();

    @Getter
    @Setter
    private Repertorio repertorioSelected = new Repertorio();

    @Getter
    @Setter
    private TipoLibro tipoLibro;
    
    @Getter
    @Setter
    private Acta actaEncontrada;

    @Getter
    @Setter
    private TipoActa tipoActa = new TipoActa();

    @Getter
    @Setter
    private String fechaPopup;

    @Getter
    @Setter
    private String repertorio;

    @Getter
    @Setter
    private String numRepertorioStr;

    @Getter
    @Setter
    private String tramDetalleId;

    @Getter
    @Setter
    private String numTramite;

    @Getter
    @Setter
    private String contrato;

    @Getter
    @Setter
    private String tramite;

    @Getter
    @Setter
    private String numTramiteStr;

    @Getter
    @Setter
    private String strContrato;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalle;

    @Getter
    @Setter
    private TipoTramite tipoTramite;

    @Getter
    @Setter
    private Acta actaGuardar = new Acta();

    @Getter
    @Setter
    private Propiedad propiedadSeleccionada;

    @Getter
    @Setter
    private Acta actaA;

    @Getter
    @Setter
    BigInteger numInscripcion;

    @EJB
    TipoTramiteDao tipoTramiteDao;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleCuantia;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleRepertorio;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleSeleccionado;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private Boolean mostrarDlg = Boolean.FALSE;

    @Getter
    @Setter
    private String texto3;

    @Getter
    @Setter
    private String numTramAccidente;

    @Getter
    @Setter
    private String compareciente;

    @Getter
    @Setter
    private String otorgamiento;

    @Getter
    @Setter
    private String antecedentes;
    
    @Getter
    @Setter    
    private String razonInscripcion;
    
    @Getter
    @Setter    
    private Boolean todosEstadoProcTerminado = false;

    @Getter
    @Setter
    private Boolean disabledRegistroCuantia;

    @Getter
    @Setter
    private PropiedadDetalle propiedadDetElim;

    @Getter
    @Setter
    private String objetoContrato;

    @Getter
    @Setter
    private String linderos;

    @Getter
    @Setter
    private String cuantias;

    @Getter
    @Setter
    private String gravamenes;

    @Getter
    @Setter
    private String observaciones;

    @Getter
    @Setter
    private Boolean mostrarDlgPreviewActa;

//    @Getter
//    @Setter
//    private FormatoTramite formatoTramite;
    @Getter
    @Setter
    private String fmtTexto;

    @Getter
    @Setter
    private String where;

    @Getter
    @Setter
    private String totalCuantiaValor;

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
    private Propiedad propiedadMatriculaDerAcc;

    @Getter
    @Setter
    private String nombreNotario;
    
    @Getter
    @Setter
    private Boolean esActaNueva = false;
    
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
    private Boolean renderedTabActa;
    
    @Getter
    @Setter
    private Boolean renderedTabRazon;
    
    @Getter
    @Setter
    private Boolean renderedTabCertifcado;

    @Getter
    @Setter
    private String actaHTML;

    @Getter
    @Setter
    private String actaPDF;
    
    @Getter
    @Setter
    private String nombresPropietarios;
    
    @Getter
    @Setter
    private String antecedentesCert;
    
    @Getter
    @Setter
    private String gravamenesCert;
    
    @Getter
    @Setter
    private String descripcionPropiedadCert;

    @Getter
    @Setter
    private Secuencia secuencia;

    @Getter
    @Setter
    private Usuario usuarioAsignado;

    @Getter
    @Setter
    private List<TipoSuspension> listaTipoSuspension = new ArrayList<>();
    
    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuario;

    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioBuscar;

    @Getter
    @Setter
    private Boolean deshabilitarBotonGuardar;
    @Getter
    @Setter
    private String testamento;
    
    @Getter
    @Setter
    private Boolean mostrarGrid1;

    @EJB
    private RepertorioServicio servicioRepertorio;

    @EJB
    private PersonaServicio personaServicio;

    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @EJB
    private FormatoTramiteDao formatoTramiteDao;

    @EJB
    private CantonDao cantonDao;

    @EJB
    private NotariaDao notariaDao;

    @EJB
    private GravamenDao gravamenDao;

    @EJB
    private GravamenDetalleDao gravamenDetalleDao;

    @EJB
    private MarginacionDao marginacionDao;

    @EJB
    private TramiteServicio servicioTramite;    
    
    @EJB
    private TipoSuspensionServicio servicioTipoSuspension;
    
    @EJB
    private FacturaDetalleDao facturaDetalleDao;
    
    @EJB
    private ActaRazonDao actaRazonDao;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @Inject
    private TransformadorNumerosLetrasUtil transformadorNumerosLetrasUtil;

    @Getter
    @Setter
    private FechasUtil fechasUtil;
    
    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSelec;

    @Inject
    private PersonaUtil personaUtil;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Inject
    private TramiteUtil tramiteUtil;
    
    public ControladorInscripcion() {
        indiceTab = 0;
        factura = new Factura();
        disabledTabActa = true;
        disabledTabRazon = true;
        disabledTabCertifcado = true;
        readOnly_ObjCont = true;
        readOnly_Antec  = true;
        readOnly_Lind = true;
        readOnly_Grav = true;
        readOnly_Obs = true;
        renderedTabActa = true;
        renderedTabRazon = false;
        renderedTabCertifcado = false;
        repertorioUsuarioSelec = new RepertorioUsuario();
        
    }

    @PostConstruct
    public void postConstructor() {
        setFHR(Calendar.getInstance().getTime());
        secuencia = new Secuencia();
        tramiteSeleccionado = new Tramite();
        tipoLibro = new TipoLibro();
        actaA = new Acta();
        buscarCargaLaboral = new CargaLaboral();
        renderedTxtEdit = false;
        deshabilitarBotonGuardar = true;
        propiedadSeleccionada = new Propiedad();
        totalAcc = new BigDecimal(0);
        propiedadMatriculaDerAcc = new Propiedad();
        propiedadDetElim = new PropiedadDetalle();
        propiedadDetalleSeleccionada = new PropiedadDetalle();
        cuantiaTipo = "Normal";
        disabledRegistroCuantia = false;
        mostrarDlgPreviewActa = false;
        inicializar();
    }

    public void inicializar() {
        try {
            llenarDatos();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    

    public void suspender() {
        
        listaTipoSuspension = servicioTipoSuspension.listarPorTipoInscripcion();
        mostrarGrid1 = true;
    

    }

    public void llenarDatos() throws ServicioExcepcion, ParseException {

        reset();
        preListaRepertorioFecha.clear();
        setPreListaRepertorioFecha(inscripcionDao.ListarPorFHRPorUsrLog(FHR, dataManagerSesion.getUsuarioLogeado().getUsuId()));
        listaRepertorioFecha.clear();
        for (Repertorio repertorio1 : preListaRepertorioFecha) {

            Tramite tramite = new Tramite();
            tramite = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());
            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramite.getTraNumero().longValue()) && !tramite.getTraEstadoRegistro().trim().equals("RZ")) {
                listaRepertorioFecha.add(repertorio1);
            }

        }
        if (!listaRepertorioFecha.isEmpty()) {

        } else {

            limpiarCamposTxtEdit();
            renderedTxtEdit = false;
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
        repertorio = "";
        tramite = "";
        contrato = "";
        nombreNotario = "";
        razonInscripcion = "";
        certificadoGenerado = "";

    }

    public void txtCompareciente() throws ServicioExcepcion {

        formatoTramiteList = formatoTramiteDao.buscarFormatoTramite(ttrId, "txtCompareciente");

        for (FormatoTramite formatoTramite : formatoTramiteList) {
            switch (formatoTramite.getFmtLinea()) {
                case 10:
                    compareciente = formatoTramite.getFmtTexto();

                    break;
                case 20:
                    fmtTexto = formatoTramite.getFmtTexto();
                    int count = 0;
                    List<String> listFinal = new ArrayList<>();
                    String[] info = new String[0];

                    Pattern p = Pattern.compile("'([^']*)'");
                    Matcher m = p.matcher(fmtTexto);

                    if (verificarTexto(fmtTexto)) {
                        where = " WHERE TraNumero = " + Long.parseLong(tramite) + "AND TdtEstado = 'A' AND TdtNumeroRepertorio = " + repertorio;
                        // txtCompareciente(fmtTexto, where);

                        try {
                            while (m.find()) {
                                count++;
                                String d = m.group(0);
                                int count3 = -1;
                                String sql = "SELECT " + m.group(1) + " FROM ListaTxtCompareciente" + where;
                                System.out.println("SQL: " + sql);
                                List<String> lista = new ArrayList<>();
                                listTxtCompareciente = (listaTxtComparecienteDao.ObtenerDatosCompareciente(sql));

                                if (count == 1) {
                                    info = new String[listTxtCompareciente.size()];
                                }

                                for (String string : listTxtCompareciente) {
                                    System.out.println("lista: " + string);

                                    count3++;
                                    if (count == 1) {
                                        for (int i = 0; i < info.length; i++) {
                                            if (count3 == i) {

                                                info[i] = fmtTexto;
                                                Pattern p3 = Pattern.compile("'([^']*)'");
                                                Matcher m3 = p3.matcher(info[i]);
                                                while (m3.find()) {
                                                    if (info[i].contains(d)) {
                                                        switch (d) {
                                                            case "'PerEstadoCivil'":
                                                                switch (string.trim()) {
                                                                    case "S":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "soltero(a)");
                                                                        break;
                                                                    case "C":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "casado(a)");
                                                                        break;
                                                                    case "D":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "divorciado(a)");
                                                                        break;
                                                                    case "V":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "viudo(a)");
                                                                        break;
                                                                    case "UH":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "unión de hecho");
                                                                        break;

                                                                }

                                                                break;
                                                            case "'TdtPerTipoIdentificacion'":
                                                                switch (string.trim()) {
                                                                    case "C":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "cédula");
                                                                        break;
                                                                    case "R":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "RUC");
                                                                        break;
                                                                    case "P":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "pasaporte");
                                                                        break;

                                                                }
                                                                break;
                                                            case "'TdtTpcDescripcion'":
                                                                info[i] = info[i].replaceAll(m3.group(0), "<strong>" + string.trim() + "</strong>");
                                                                break;
                                                            default:
                                                                info[i] = info[i].replaceAll(m3.group(0), string.trim());

                                                        }

                                                    }

                                                }
                                                Pattern p4 = Pattern.compile("'([^']*)'");
                                                Matcher m4 = p4.matcher(info[i]);
                                                if (!m4.find()) {

                                                    listFinal.add(info[i] + " nlnl1 ");
                                                    //txtEditCompareciente = String.join(", ", listFinal);
                                                    txtEditCompareciente = listFinal.toString();
                                                    txtEditCompareciente = txtEditCompareciente.replaceAll("nlnl1 ,", "<p></p>");
                                                    txtEditCompareciente = txtEditCompareciente.replaceAll("nlnl1", "<p></p>");
                                                    txtEditCompareciente = txtEditCompareciente.replace("null", "");
                                                }
                                            }
                                        }
                                    } else {

                                        for (int i = 0; i < info.length; i++) {
                                            if (count3 == i) {

                                                String dato = info[i];

                                                Pattern p3 = Pattern.compile("'([^']*)'");
                                                Matcher m3 = p3.matcher(dato);
                                                while (m3.find()) {

                                                    if (info[i].contains(d)) {
                                                        switch (d) {
                                                            case "'PerEstadoCivil'":
                                                                switch (string.trim()) {
                                                                    case "S":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "soltero(a)");
                                                                        break;
                                                                    case "C":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "casado(a)");
                                                                        break;
                                                                    case "D":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "divorciado(a)");
                                                                        break;
                                                                    case "V":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "viudo(a)");
                                                                        break;
                                                                    case "UH":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "unión de hecho");
                                                                        break;

                                                                }

                                                                break;
                                                            case "'TdtPerTipoIdentificacion'":
                                                                switch (string.trim()) {
                                                                    case "C":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "cédula");
                                                                        break;
                                                                    case "R":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "RUC");
                                                                        break;
                                                                    case "P":
                                                                        info[i] = info[i].replaceAll(m3.group(0), "pasaporte");
                                                                        break;

                                                                }
                                                                break;
                                                            case "'TdtTpcDescripcion'":
                                                                info[i] = info[i].replaceAll(m3.group(0), "<strong>" + string.trim() + "</strong>");
                                                                break;
                                                            default:
                                                                info[i] = info[i].replaceAll(m3.group(0), string.trim());

                                                        }

                                                    }

                                                }

                                                Pattern p4 = Pattern.compile("'([^']*)'");
                                                Matcher m4 = p4.matcher(info[i]);
                                                if (!m4.find()) {

                                                    listFinal.add(info[i] + " nlnl1 ");
                                                    txtEditCompareciente = String.join(",", listFinal);
                                                    txtEditCompareciente = txtEditCompareciente.replaceAll("nlnl1 ,", "</br>");
                                                    txtEditCompareciente = txtEditCompareciente.replaceAll("nlnl1", "</br>");
                                                    txtEditCompareciente = txtEditCompareciente.replace("null", "");

                                                }

                                            }

                                        }
                                    }

                                }

                            }

                        } catch (Exception e) {
                            System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.txtCompareciente()");
                            System.out.println(e);

                            break;

                        }
                    } else {
                        txtEditCompareciente = fmtTexto;
                    }

            }

        }

    }

    public void txtOtorgamiento() throws ServicioExcepcion {

        if (tramite != null && contrato != null) {

            formatoTramiteList = formatoTramiteDao.buscarFormatoTramite(ttrId, "txtOtorgamiento");

            for (FormatoTramite formatoTramite : formatoTramiteList) {
                switch (formatoTramite.getFmtLinea()) {
                    case 10:

                        otorgamiento = formatoTramite.getFmtTexto();

                        break;
                    case 20:
                        fmtTexto = formatoTramite.getFmtTexto();
                        if (verificarTexto(fmtTexto)) {
                            fmtTexto = formatoTramite.getFmtTexto();
                        } else {
                            txtEditOtorgamiento = fmtTexto;
                            txtEditOtorgamiento = txtEditOtorgamiento.replace("null", "");
                        }

                        String MES[] = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
                        String DIASEMANA[] = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
                        String DIA[] = {"UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE",
                            "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISÉIS", "DIECISIETE,",
                            "DIECIOCHO", "DIECINUEVE", "VEINTE", "VEINTIUNO", "VEINTIDÓS", "VEINTITRES", "VEINTICUATRO",
                            "VEINTICINCO", "VEINTISÉIS", "VEINTISIETE", "VEINTIOCHO", "VEINTINUEVE", "TREINTA",
                            "TREINTA Y UNO"};

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fechaOtorgamiento);

                        String año = (transformadorNumerosLetrasUtil.transformador(String.valueOf(calendar.get(Calendar.YEAR)))).toString();
                        String fechaLetras = "";

                        fechaLetras = (DIA[calendar.get(Calendar.DAY_OF_MONTH) - 1] + " DE " + MES[calendar.get(Calendar.MONTH)] + " DEL " + año);

                        int count = 0;
                        List<String> listFinal = new ArrayList<>();
                        String[] info = new String[1];

                        Pattern p = Pattern.compile("'([^']*)'");
                        Matcher m = p.matcher(fmtTexto);

                        try {

                            Notaria notaria = notariaDao.encontrarPorNumNotariaTopUno(Short.valueOf(numeroNotario));

                            while (m.find()) {
                                count++;
                                String d = m.group(0);
                                int count3 = -1;

                                count3++;
                                if (count == 1) {
                                    for (int i = 0; i < info.length; i++) {
                                        if (count3 == i) {

                                            info[i] = fmtTexto;
                                            Pattern p3 = Pattern.compile("'([^']*)'");
                                            Matcher m3 = p3.matcher(info[i]);
                                            while (m3.find()) {
                                                if (info[i].contains(d)) {

                                                    info[i] = info[i].replaceAll(m3.group(0), fechaLetras);

                                                }

                                            }
                                            Pattern p4 = Pattern.compile("'([^']*)'");
                                            Matcher m4 = p4.matcher(info[i]);
                                            if (!m4.find()) {

                                                listFinal.add(info[i] + " nlnl1 ");

                                                txtEditOtorgamiento = String.join(" ", listFinal);
                                                txtEditOtorgamiento = txtEditOtorgamiento.replace("null", "");
                                            }
                                        }
                                    }
                                } else {

                                    for (int i = 0; i < info.length; i++) {
                                        if (count3 == i) {

                                            String dato = info[i];

                                            Pattern p3 = Pattern.compile("'([^']*)'");
                                            Matcher m3 = p3.matcher(dato);
                                            while (m3.find()) {
                                                if (info[i].contains(d)) {

                                                    if (d.equals("'numeroNotario'")) {

                                                        info[i] = info[i].replaceAll(m3.group(0), notaria.getNotNotario().trim());
                                                    }

                                                    if (d.equals("'canton'")) {
                                                        info[i] = info[i].replaceAll(m3.group(0), canton);
                                                    }

                                                    if (d.equals("'nombreNotario'")) {
                                                        info[i] = info[i].replaceAll(m3.group(0), nombreNotario.toUpperCase());
                                                    }

                                                }

                                            }

                                            Pattern p4 = Pattern.compile("'([^']*)'");
                                            Matcher m4 = p4.matcher(info[i]);
                                            if (!m4.find()) {

                                                listFinal.add(info[i] + " ");
                                                txtEditOtorgamiento = String.join(" ", listFinal);
                                                txtEditOtorgamiento = txtEditOtorgamiento.replace("null", "");

                                            }

                                        }

                                    }
                                }

                            }
                            nombreNotario = "";

                        } catch (Exception e) {
                            System.out.println(e);

                        }

                        break;

                    default:
                        System.out.println("no match");
                }

            }

        }

    }

    public void txtAntecedentes() throws ServicioExcepcion {

        formatoTramiteList = formatoTramiteDao.buscarFormatoTramite(ttrId, "txtAntecedentes");

        for (FormatoTramite formatoTramite : formatoTramiteList) {
            switch (formatoTramite.getFmtLinea()) {
                case 10:

                    antecedentes = formatoTramite.getFmtTexto();

                    break;
                case 20:
                    try {
                        /*            
                        tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
                        TipoLibro tipoL = new TipoLibro();
                        tipoL = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
                        Acta actaAnterior = actaDao.encontrarActaAnterior(tipoL.getTplId());
                        txtEditAntecedentes = actaAnterior.getActDescripcion4();
                        System.out.println("id tipoL " + tipoL.getTplId());
                        System.out.println("acta ant " + actaAnterior.getActNumero());
                         */
                        txtEditAntecedentes = "";
                        List<Long> listaNumActasDesdeMarginacion = new ArrayList<>();
                        listaNumActasDesdeMarginacion = marginacionDao.listarPor_NumRepertorio(repertorio);
                        for (Long numActa : listaNumActasDesdeMarginacion) {
                            Acta actaAnterior = actaDao.obtenerActaPorNumeroActa(numActa);
                            if (actaAnterior.getActDescripcion4() != null) {
                                txtEditAntecedentes = txtEditAntecedentes + actaAnterior.getActDescripcion4() + "<br>";
                            }

                        }
                        
                        if(txtEditAntecedentes.equals("") || txtEditAntecedentes == null){
                            readOnly_Antec = true;
                        }

//                        fmtTexto = formatoTramite.getFmtTexto();
//
//                        int count = 0;
//                        List<String> listFinal = new ArrayList<>();
//                        String[] info = new String[0];
//
//                        Pattern p = Pattern.compile("'([^']*)'");
//                        Matcher m = p.matcher(fmtTexto);
//
//                        if (verificarTexto(fmtTexto)) {
//                            where = " WHERE TraNumero = " + Long.parseLong(tramite) + " AND TdtNumeroRepertorio = " + repertorio;
//
//                        } else {
//
//                            txtEditAntecedentes = fmtTexto;
//
//                        }
                    } catch (Exception e) {
                        System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.txtAntecedentes()");
                        System.out.println(e);
                    }

                    break;

                default:
                    System.out.println("no match");
            }

        }

    }

    public void txtObjetoContrato() throws ServicioExcepcion, ParseException {

        formatoTramiteList = formatoTramiteDao.buscarFormatoTramite(ttrId, "txtObjetoContrato");

        for (FormatoTramite formatoTramite : formatoTramiteList) {
            switch (formatoTramite.getFmtLinea()) {
                case 10:
                    objetoContrato = formatoTramite.getFmtTexto();

                    break;
                case 20:
                    try {
                        fmtTexto = formatoTramite.getFmtTexto();
                        if (verificarTexto(fmtTexto)) {
                            fmtTexto = formatoTramite.getFmtTexto();
                        } else {
                            txtEditObjetoContrato = fmtTexto;
                            txtEditObjetoContrato = txtEditOtorgamiento.replace("null", "");
                        }
                        int count = 0;
                        List<String> listFinal = new ArrayList<>();
                        String[] info = new String[0];

                        Pattern p = Pattern.compile("'([^']*)'");
                        Matcher m = p.matcher(fmtTexto);

                        List<ListaTxtCompareciente> listaListTxtComp = new ArrayList<>();
                        listaListTxtComp = listaTxtComparecienteDao.listarCompareciente(Long.parseLong(tramite), Long.valueOf(repertorio));
                        String comparecietes = "";
                        String estadoCivil = "";
                        String tipoIdentificacion = "";
                        String signoPuntuacion = "";
                        int aux = 0;
                        for (ListaTxtCompareciente listaTxtCompareciente : listaListTxtComp) {
                            aux++;
                            if (aux < listaListTxtComp.size()) {
                                signoPuntuacion = ", ";
                            } else {
                                signoPuntuacion = ".";
                            }
                            switch (listaTxtCompareciente.getTdtPerTipoIdentificacion().trim()) {
                                case "C":
                                    tipoIdentificacion = "cédula";
                                    break;
                                case "R":
                                    tipoIdentificacion = "RUC";
                                    break;
                                case "P":
                                    tipoIdentificacion = "pasaporte";
                                    break;

                            }
                            if(listaTxtCompareciente.getPerEstadoCivil() == null){
                                comparecietes = comparecietes + listaTxtCompareciente.getPersona() + " "
                                    + listaTxtCompareciente.getTdtTpcDescripcion() + " con "
                                    + tipoIdentificacion + ": " + listaTxtCompareciente.getTdtPerIdentificacion().trim()
                                    + "" + signoPuntuacion;
                            }
                            else{
                                switch (listaTxtCompareciente.getPerEstadoCivil().trim()) {
                                case "S":
                                    estadoCivil = "soltero(a)";
                                    break;
                                case "C":
                                    estadoCivil = "casado(a)";
                                    break;
                                case "D":
                                    estadoCivil = "divorciado(a)";
                                    break;
                                case "V":
                                    estadoCivil = "viudo(a)";
                                    break;
                                case "UH":
                                    estadoCivil = "unión de hecho";
                                    break;

                            }
                             comparecietes = comparecietes + listaTxtCompareciente.getPersona() + " "
                                    + listaTxtCompareciente.getTdtTpcDescripcion() + " con "
                                    + tipoIdentificacion + ": " + listaTxtCompareciente.getTdtPerIdentificacion().trim()
                                    + " " + estadoCivil + signoPuntuacion;   
                            }
                            
                            
                        }
                        while (m.find()) {
                            txtEditObjetoContrato = fmtTexto.replace(m.group(0), comparecietes);
                        }
                        
                        if(txtEditObjetoContrato.equals("") || txtEditObjetoContrato == null){
                            readOnly_ObjCont = false;
                        }

                        /*
                    where = " WHERE RepNumero = " + Long.parseLong(repertorio);

                   
                        
                        
                        

                        count++;

                        int count3 = -1;
                        String sql = "SELECT PrdDescripcion2 FROM ListaLinderoSuperficie" + where;

                        listTxtCompareciente = (listaTxtComparecienteDao.ObtenerDatosCompareciente(sql));

                        if (count == 1) {
                            info = new String[listTxtCompareciente.size()];
                        }

                        for (String string : listTxtCompareciente) {

                            count3++;
                            if (count == 1) {
                                for (int i = 0; i < info.length; i++) {
                                    if (count3 == i) {

                                        listFinal.add(string);

                                        txtEditObjetoContrato = String.join(" ", listFinal);
                                        txtEditObjetoContrato = txtEditObjetoContrato.replace("null", "");

                                    }
                                }
                            }

                        }
                         */
                    } catch (Exception e) {
                        System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.txtObjetoContrato()");
                        System.out.println(e);
                    }

                    break;

                default:
                    System.out.println("no match");
            }

        }

    }

    public void txtLinderos() throws ServicioExcepcion, ParseException {

        formatoTramiteList = formatoTramiteDao.buscarFormatoTramite(ttrId, "txtLinderos");

        for (FormatoTramite formatoTramite : formatoTramiteList) {

            switch (formatoTramite.getFmtLinea()) {
                case 10:
                    linderos = formatoTramite.getFmtTexto();
                    break;
                case 20:
                    //if (tipo == "linderos") {
                    try {

                        listaRepertorioPropiedad.clear();
                        fmtTexto = "";
                        fmtTexto = formatoTramite.getFmtTexto();

                        int count = 0;
                        List<String> listFinal = new ArrayList<>();
                        String[] info = new String[0];

                        Pattern p = Pattern.compile("'([^']*)'");
                        Matcher m = p.matcher(fmtTexto);

                        if (verificarTexto(fmtTexto)) {
                            where = " WHERE RepNumero = " + Long.parseLong(repertorio);

                            //txtLinderos(fmtTexto, formatoTramite.getFmtLinea(), where);
                            String sql = "";

                            /*
                        sql = "SELECT * FROM ListaLinderos ORDER BY PrdMatricula";
                        System.out.println("Linderos SQL: " + sql);
                        listListaLinderos = (listaTxtLinderosDao.buscarLinderos(sql));
                        System.out.println("SQL: " + sql);
                             */
                            listaRepertorioPropiedad.clear();
                            listaRepertorioPropiedad = repertorioPropiedadDao.buscarPorNumRepertorio(Long.valueOf(repertorio));
                            for (RepertorioPropiedad repertorioProp : listaRepertorioPropiedad) {
                                listaLindero.clear();
                                listaLindero = linderoDao.listarPorNumMatricula(repertorioProp.getPrdMatricula().getPrdMatricula());
                                listaLinderoFinal.clear();
                                listaLinderoFinal.addAll(listaLindero);

                                Collections.sort(listaLinderoFinal, new Comparator<Lindero>() {

                                    public int compare(Lindero o1, Lindero o2) {

                                        return o1.getPrdMatricula().getPrdMatricula().compareTo(o2.getPrdMatricula().getPrdMatricula());
                                    }
                                });

                            }

                            int aux = 0;
                            while (m.find()) {
                                count++;
                                String d = m.group(0);
                                int count3 = -1;
                                sql = "";

                                if (count == 1) {
                                    info = new String[listaLinderoFinal.size()];

                                }

                                for (Lindero string : listaLinderoFinal) {
                                    //System.out.println("listaLinSQL: " + string.getLdrNombre().toString() +" " +string.getPrdMatricula() +" "+ string.getRepNumero());

                                    prdMatricula = string.getPrdMatricula().getPrdMatricula();
                                    aux++;
                                    if (aux == 1) {
                                        prdMatriculaAux = prdMatricula;

                                    }

                                    count3++;
                                    if (count == 1) {

                                        for (int i = 0; i < info.length; i++) {
                                            if (count3 == i) {

                                                info[i] = fmtTexto;
                                                Pattern p3 = Pattern.compile("'([^']*)'");
                                                Matcher m3 = p3.matcher(info[i]);
                                                while (m3.find()) {
                                                    if (info[i].contains(d)) {
                                                        info[i] = info[i].replaceAll(m3.group(0), string.getLdrNombre().trim());

                                                    }

                                                }
                                                Pattern p4 = Pattern.compile("'([^']*)'");
                                                Matcher m4 = p4.matcher(info[i]);
                                                if (!m4.find()) {

                                                    if (prdMatricula == prdMatriculaAux) {
                                                        listFinal.add(info[i] + " ");
                                                        //prdMatriculaAux = prdMatricula;
                                                    }
                                                    if (prdMatricula != prdMatriculaAux) {
                                                        listFinal.add(" nlnl1 " + info[i]);

                                                    }

                                                    prdMatriculaAux = prdMatricula;

                                                    txtLinderosDescripcion1 = String.join(" ", listFinal);
                                                    //System.out.println("Linderos " + txtLinderosDescripcion1);

                                                    // txtLind = txtLinderosDescripcion1 + txtLinderosSuperficie;
                                                }
                                            }
                                        }
                                    } else {

                                        for (int i = 0; i < info.length; i++) {
                                            if (count3 == i) {

                                                String dato = info[i];

                                                Pattern p3 = Pattern.compile("'([^']*)'");
                                                Matcher m3 = p3.matcher(dato);
                                                while (m3.find()) {
                                                    if (info[i].contains(d)) {
                                                        info[i] = info[i].replaceAll(m3.group(0), string.getLdrNombre().trim());
                                                    }
                                                }

                                                Pattern p4 = Pattern.compile("'([^']*)'");
                                                Matcher m4 = p4.matcher(info[i]);
                                                if (!m4.find()) {

                                                    if (prdMatricula == prdMatriculaAux) {
                                                        listFinal.add(info[i] + " ");
                                                        //prdMatriculaAux = prdMatricula;
                                                    }
                                                    if (prdMatricula != prdMatriculaAux) {
                                                        listFinal.add(" nlnl1 " + info[i]);

                                                    }

                                                    prdMatriculaAux = prdMatricula;

                                                    txtLinderosDescripcion1 = String.join(" ", listFinal);
                                                    txtLinderosDescripcion1 = txtLinderosDescripcion1.replace("null", "");

                                                    // txtLind = txtLinderosDescripcion1 + txtLinderosSuperficie;
                                                }

                                            }

                                        }
                                    }

                                }

                            }
                            //String separador = Pattern.quote("nlnl1");
                            String separador = Pattern.quote("nlnl1");
                            partsLindero = txtLinderosDescripcion1.split(separador);
                            for (int i = 0; i < partsLindero.length; i++) {
                                System.out.println("PARTES LIN" + partsLindero[i]);
                            }
                        } else {
                            txtEditLinderos = fmtTexto;
                        }
                        if(txtEditLinderos.equals("") || txtEditLinderos == null){
                            readOnly_Lind = false;
                        }
                        
                    } catch (Exception e) {
                    }

                    //}
                    break;
                case 30:

                    try {

                        fmtTexto = "";
                        fmtTexto = formatoTramite.getFmtTexto();

                        int countSup = 0;
                        List<String> listFinalSup = new ArrayList<>();
                        String[] infoSup = new String[0];

                        Pattern pSup = Pattern.compile("'([^']*)'");
                        Matcher mSup = pSup.matcher(fmtTexto);

                        if (verificarTexto(fmtTexto)) {
                            where = " WHERE RepNumero = " + Long.parseLong(repertorio);

                            try {

                                // if (tipo == "superficie") {
                                String sql2 = "";

                                /**
                                 * ********************************* SUPERFICIE
                                 * ****************************************
                                 */
                                while (mSup.find()) {
                                    countSup++;
                                    String d = mSup.group(0);
                                    int count3 = -1;
                                    sql2 = "";

                                    sql2 = "SELECT * FROM ListaLinderoSuperficie" + where + " ORDER BY PrdMatricula";
                                    listListaLinderosSuperficie = (listaLinderoSuperficieDao.buscarLinderosSuperficie(sql2));

                                    System.out.println("SQL: " + sql2);

                                    if (countSup == 1) {
                                        infoSup = new String[listListaLinderosSuperficie.size()];
                                    }

                                    for (ListaLinderoSuperficie string : listListaLinderosSuperficie) {
                                        System.out.println("listaSup: " + string.getPrdDescripcion1());
                                        listaPropiedad.add(String.valueOf(string.getPrdMatricula()) + " nlnl1 ");

                                        count3++;
                                        if (countSup == 1) {
                                            for (int i = 0; i < infoSup.length; i++) {
                                                if (count3 == i) {

                                                    infoSup[i] = fmtTexto;
                                                    Pattern p3 = Pattern.compile("'([^']*)'");
                                                    Matcher m3 = p3.matcher(infoSup[i]);
                                                    while (m3.find()) {
                                                        if (infoSup[i].contains(d)) {
                                                            infoSup[i] = infoSup[i].replaceAll(m3.group(0), string.getPrdDescripcion1().trim());

                                                        }

                                                    }
                                                    Pattern p4 = Pattern.compile("'([^']*)'");
                                                    Matcher m4 = p4.matcher(infoSup[i]);
                                                    if (!m4.find()) {

                                                        listFinalSup.add(infoSup[i] + " nlnl1 ");

                                                        txtLinderosSuperficie = String.join(" ", listFinalSup);
                                                        System.out.println("SUPERFICIES " + txtLinderosSuperficie);

                                                        // txtLind = txtLinderosDescripcion1 + txtLinderosSuperficie;
                                                    }
                                                }
                                            }
                                        } else {
                                            for (int i = 0; i < infoSup.length; i++) {
                                                if (count3 == i) {

                                                    String dato = infoSup[i];

                                                    Pattern p3 = Pattern.compile("'([^']*)'");
                                                    Matcher m3 = p3.matcher(dato);
                                                    while (m3.find()) {
                                                        if (infoSup[i].contains(d)) {
                                                            infoSup[i] = infoSup[i].replaceAll(m3.group(0), string.getPrdDescripcion1().trim());
                                                        }
                                                    }

                                                    Pattern p4 = Pattern.compile("'([^']*)'");
                                                    Matcher m4 = p4.matcher(infoSup[i]);
                                                    if (!m4.find()) {
                                                        listFinalSup.add(infoSup[i] + " nlnl1 ");

                                                        txtLinderosSuperficie = String.join(" ", listFinalSup);
                                                        txtLinderosSuperficie = txtLinderosSuperficie.replace("null", "");

                                                        //  txtLind = txtLinderosDescripcion1 + txtLinderosSuperficie;
                                                    }

                                                }

                                            }
                                        }

                                    }

                                }
                                String strPrdMatricula = String.join("", listaPropiedad);
                                String separador2 = Pattern.quote("nlnl1");
                                partsSuperficie = txtLinderosSuperficie.split(separador2);

                                partsMatricula = strPrdMatricula.split(separador2);
                                for (int i = 0; i < partsSuperficie.length; i++) {
                                    System.out.println("PARTES SUP " + partsSuperficie[i]);

                                }
                                List<String> listaCombinada = new ArrayList<>();
                                for (int i = 0; i < partsLindero.length; i++) {
                                    for (int j = 0; j < partsSuperficie.length; j++) {
                                        for (int k = 0; k < partsMatricula.length; k++) {
                                            if (j == i && j == k && i == k) {
                                                listaCombinada.add("Para la matricula " + partsMatricula[i].trim() + ": "
                                                        + partsLindero[j] + partsSuperficie[j]);
                                                txtEditLinderos = String.join("", listaCombinada);
                                                txtEditLinderos = txtEditLinderos.replace("null", "");

                                            }

                                        }
                                    }
                                }
                                if(txtEditLinderos.equals("") || txtEditLinderos == null){
                            readOnly_Lind = false;
                        }

                                // }
                            } catch (Exception e) {
                                System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.txtLinderos()");
                                System.out.println(e);
                            }
                        } else {
                            txtEditLinderos = fmtTexto;
                        }

                    } catch (Exception e) {
                    }

                    break;
            }
        }

    }

    public void txtCuantias() throws ServicioExcepcion {

        if (tramite != null && contrato != null) {

            formatoTramiteList = formatoTramiteDao.buscarFormatoTramite(ttrId, "txtCuantias");
            /**
             * * ********************** OBTENER VALORES EN LETRAS PARA CUANTIAS
             * ***********************************
             */
            float totalCuantia = cuantiaAlcabalas + cuantiaMonto + cuantiaPlusvalia + cuantiaRegistro;

            String totalCuantiaTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(totalCuantia))).toString();
            String cuantiaAlcabalasTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(cuantiaAlcabalas))).toString();
            String cuantiaMontoTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(cuantiaMonto))).toString();
            String cuantiaPlusvaliaTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(cuantiaPlusvalia))).toString();
            String cuantiaRegistroTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(cuantiaRegistro))).toString();

            /**
             * *****************************************************************************************************
             */
            for (FormatoTramite formatoTramite : formatoTramiteList) {

                switch (cuantiaTipo) {
                    case "Normal":
                        switch (formatoTramite.getFmtLinea()) {
                            case 10:
                                cuantias = formatoTramite.getFmtTexto();

                                break;
                            case 20:
                                if (cuantiaAlcabalas != 0
                                        && cuantiaMonto != 0
                                        && cuantiaPlusvalia != 0
                                        && cuantiaRegistro != 0) {

                                    fmtTexto = formatoTramite.getFmtTexto();
                                    if (verificarTexto(fmtTexto)) {
                                        fmtTexto = formatoTramite.getFmtTexto();
                                    } else {
                                        txtEditCuantias = fmtTexto;
                                    }

                                    int count = 0;
                                    List<String> listFinal = new ArrayList<>();
                                    String[] info = new String[1];

                                    Pattern p = Pattern.compile("'([^']*)'");
                                    Matcher m = p.matcher(fmtTexto);

                                    try {

                                        while (m.find()) {
                                            count++;
                                            String d = m.group(0);
                                            int count3 = -1;

                                            count3++;
                                            if (count == 1) {
                                                for (int i = 0; i < info.length; i++) {
                                                    if (count3 == i) {

                                                        info[i] = fmtTexto;
                                                        Pattern p3 = Pattern.compile("'([^']*)'");
                                                        Matcher m3 = p3.matcher(info[i]);
                                                        while (m3.find()) {
                                                            if (info[i].contains(d)) {
                                                                if (totalCuantia != 0.00) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), String.valueOf(totalCuantiaTexto));
                                                                } else {
                                                                    info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                }

                                                            }

                                                        }
                                                        Pattern p4 = Pattern.compile("'([^']*)'");
                                                        Matcher m4 = p4.matcher(info[i]);
                                                        if (!m4.find()) {

                                                            listFinal.add(info[i] + " nlnl1 ");

                                                            txtEditCuantias = String.join(", ", listFinal);
                                                            txtEditCuantias = txtEditCuantias.replace("null", "");
                                                        }
                                                    }
                                                }
                                            } else {

                                                for (int i = 0; i < info.length; i++) {
                                                    if (count3 == i) {

                                                        String dato = info[i];

                                                        Pattern p3 = Pattern.compile("'([^']*)'");
                                                        Matcher m3 = p3.matcher(dato);
                                                        while (m3.find()) {
                                                            if (info[i].contains(d)) {
                                                                if (d.equals("'monto'") && (cuantiaMonto != 0.0 || cuantiaMonto != 0 || cuantiaMonto != 0.00)) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaMontoTexto));
                                                                } else {
                                                                    if (d.equals("'monto'")) {
                                                                        info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                    }
                                                                }
                                                                if (d.equals("'alcabalas'") && (cuantiaAlcabalas != 0.0 || cuantiaAlcabalas != 0 || cuantiaAlcabalas != 0.00)) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaAlcabalasTexto));
                                                                } else {
                                                                    if (d.equals("'alcabalas'")) {
                                                                        info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                    }
                                                                }
                                                                if (d.equals("'registro'") && (cuantiaRegistro != 0.0 || cuantiaRegistro != 0 || cuantiaRegistro != 0.00)) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaRegistroTexto));
                                                                } else {
                                                                    if (d.equals("'registro'")) {
                                                                        info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                    }
                                                                }
                                                                if (d.equals("'plusvalia'") && (cuantiaPlusvalia != 0.0 || cuantiaPlusvalia != 0 || cuantiaPlusvalia != 0.00)) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaPlusvaliaTexto));
                                                                } else {
                                                                    if (d.equals("'plusvalia'")) {
                                                                        info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                    }
                                                                }

                                                            }

                                                        }

                                                        Pattern p4 = Pattern.compile("'([^']*)'");
                                                        Matcher m4 = p4.matcher(info[i]);
                                                        if (!m4.find()) {

                                                            listFinal.add(info[i] + " ");
                                                            txtEditCuantias = String.join(" ", listFinal);
                                                            txtEditCuantias = txtEditCuantias.replace("null", "");

                                                        }

                                                    }

                                                }
                                            }

                                        }

                                    } catch (Exception e) {
                                        System.out.println(e);

                                    }
                                } else {
                                    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "En cuantía NORMAL los valores ingresados no deben ser CERO", "");
                                    FacesContext.getCurrentInstance().addMessage("", facesMsg);
                                }

                                break;

                            default:
                                System.out.println("No existe una linea asociada ");
                        }
                        break;
                    case "Indeterminado":
                        switch (formatoTramite.getFmtLinea()) {
                            case 10:
                                cuantias = formatoTramite.getFmtTexto();

                                break;
                            case 25:

                                fmtTexto = formatoTramite.getFmtTexto();
                                if (verificarTexto(fmtTexto)) {
                                    fmtTexto = formatoTramite.getFmtTexto();
                                } else {
                                    txtEditCuantias = fmtTexto;
                                }

                                int count = 0;
                                List<String> listFinal = new ArrayList<>();
                                String[] info = new String[1];

                                Pattern p = Pattern.compile("'([^']*)'");
                                Matcher m = p.matcher(fmtTexto);

                                try {

                                    while (m.find()) {
                                        count++;
                                        String d = m.group(0);
                                        int count3 = -1;

                                        count3++;
                                        if (count == 1) {
                                            for (int i = 0; i < info.length; i++) {
                                                if (count3 == i) {

                                                    info[i] = fmtTexto;
                                                    Pattern p3 = Pattern.compile("'([^']*)'");
                                                    Matcher m3 = p3.matcher(info[i]);
                                                    while (m3.find()) {
                                                        if (info[i].contains(d)) {
                                                            if (totalCuantia != 0.00) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(totalCuantiaTexto));
                                                            } else {
                                                                info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                            }

                                                        }

                                                    }
                                                    Pattern p4 = Pattern.compile("'([^']*)'");
                                                    Matcher m4 = p4.matcher(info[i]);
                                                    if (!m4.find()) {

                                                        listFinal.add(info[i] + " nlnl1 ");

                                                        txtEditCuantias = String.join(", ", listFinal);
                                                        txtEditCuantias = txtEditCuantias.replace("null", "");
                                                    }
                                                }
                                            }
                                        } else {

                                            for (int i = 0; i < info.length; i++) {
                                                if (count3 == i) {

                                                    String dato = info[i];

                                                    Pattern p3 = Pattern.compile("'([^']*)'");
                                                    Matcher m3 = p3.matcher(dato);
                                                    while (m3.find()) {
                                                        if (info[i].contains(d)) {
                                                            if (d.equals("'monto'") && (cuantiaMonto != 0.0 || cuantiaMonto != 0 || cuantiaMonto != 0.00)) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaMontoTexto));
                                                            } else {
                                                                if (d.equals("'monto'")) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                }
                                                            }
                                                            if (d.equals("'alcabalas'") && (cuantiaAlcabalas != 0.0 || cuantiaAlcabalas != 0 || cuantiaAlcabalas != 0.00)) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaAlcabalasTexto));
                                                            } else {
                                                                if (d.equals("'alcabalas'")) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                }
                                                            }
                                                            if (d.equals("'registro'") && (cuantiaRegistro != 0.0 || cuantiaRegistro != 0 || cuantiaRegistro != 0.00)) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaRegistroTexto));
                                                            } else {
                                                                if (d.equals("'registro'")) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                }
                                                            }
                                                            if (d.equals("'plusvalia'") && (cuantiaPlusvalia != 0.0 || cuantiaPlusvalia != 0 || cuantiaPlusvalia != 0.00)) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaPlusvaliaTexto));
                                                            } else {
                                                                if (d.equals("'plusvalia'")) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                }
                                                            }

                                                        }

                                                    }

                                                    Pattern p4 = Pattern.compile("'([^']*)'");
                                                    Matcher m4 = p4.matcher(info[i]);
                                                    if (!m4.find()) {

                                                        listFinal.add(info[i] + " ");
                                                        txtEditCuantias = String.join(" ", listFinal);
                                                        txtEditCuantias = txtEditCuantias.replace("null", "");

                                                    }

                                                }

                                            }
                                        }

                                    }

                                } catch (Exception e) {
                                    System.out.println(e);

                                }

                                break;

                            default:
                                System.out.println("No existe una linea asociada ");
                        }
                        break;
                    case "Exonerado":
                        switch (formatoTramite.getFmtLinea()) {
                            case 10:
                                cuantias = formatoTramite.getFmtTexto();

                                break;
                            case 27:
                                fmtTexto = formatoTramite.getFmtTexto();
                                if (verificarTexto(fmtTexto)) {
                                    fmtTexto = formatoTramite.getFmtTexto();
                                } else {
                                    txtEditCuantias = fmtTexto;
                                }

                                int count = 0;
                                List<String> listFinal = new ArrayList<>();
                                String[] info = new String[1];

                                Pattern p = Pattern.compile("'([^']*)'");
                                Matcher m = p.matcher(fmtTexto);

                                try {

                                    while (m.find()) {
                                        count++;
                                        String d = m.group(0);
                                        int count3 = -1;

                                        count3++;
                                        if (count == 1) {
                                            for (int i = 0; i < info.length; i++) {
                                                if (count3 == i) {

                                                    info[i] = fmtTexto;
                                                    Pattern p3 = Pattern.compile("'([^']*)'");
                                                    Matcher m3 = p3.matcher(info[i]);
                                                    while (m3.find()) {
                                                        if (info[i].contains(d)) {
                                                            if (totalCuantia != 0.00) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(totalCuantiaTexto));
                                                            } else {
                                                                info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                            }

                                                        }

                                                    }
                                                    Pattern p4 = Pattern.compile("'([^']*)'");
                                                    Matcher m4 = p4.matcher(info[i]);
                                                    if (!m4.find()) {

                                                        listFinal.add(info[i] + " nlnl1 ");

                                                        txtEditCuantias = String.join(", ", listFinal);
                                                        txtEditCuantias = txtEditCuantias.replace("null", "");
                                                    }
                                                }
                                            }
                                        } else {

                                            for (int i = 0; i < info.length; i++) {
                                                if (count3 == i) {

                                                    String dato = info[i];

                                                    Pattern p3 = Pattern.compile("'([^']*)'");
                                                    Matcher m3 = p3.matcher(dato);
                                                    while (m3.find()) {
                                                        if (info[i].contains(d)) {
                                                            if (d.equals("'monto'") && (cuantiaMonto != 0.0 || cuantiaMonto != 0 || cuantiaMonto != 0.00)) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaMontoTexto));
                                                            } else {
                                                                if (d.equals("'monto'")) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                }
                                                            }
                                                            if (d.equals("'alcabalas'") && (cuantiaAlcabalas != 0.0 || cuantiaAlcabalas != 0 || cuantiaAlcabalas != 0.00)) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaAlcabalasTexto));
                                                            } else {
                                                                if (d.equals("'alcabalas'")) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                }
                                                            }
                                                            if (d.equals("'registro'") && (cuantiaRegistro != 0.0 || cuantiaRegistro != 0 || cuantiaRegistro != 0.00)) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaRegistroTexto));
                                                            } else {
                                                                if (d.equals("'registro'")) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                }
                                                            }
                                                            if (d.equals("'plusvalia'") && (cuantiaPlusvalia != 0.0 || cuantiaPlusvalia != 0 || cuantiaPlusvalia != 0.00)) {
                                                                info[i] = info[i].replaceAll(m3.group(0), String.valueOf(cuantiaPlusvaliaTexto));
                                                            } else {
                                                                if (d.equals("'plusvalia'")) {
                                                                    info[i] = info[i].replaceAll(m3.group(0), "CERO");
                                                                }
                                                            }

                                                        }

                                                    }

                                                    Pattern p4 = Pattern.compile("'([^']*)'");
                                                    Matcher m4 = p4.matcher(info[i]);
                                                    if (!m4.find()) {

                                                        listFinal.add(info[i] + " ");
                                                        txtEditCuantias = String.join(" ", listFinal);
                                                        txtEditCuantias = txtEditCuantias.replace("null", "");

                                                    }

                                                }

                                            }
                                        }

                                    }

                                } catch (Exception e) {
                                    System.out.println(e);

                                }

                                break;

                            default:
                                System.out.println("No existe una linea asociada ");
                        }
                        break;

                }

            }
            clear();

        }

    }

    public void txtGravamenes() throws ServicioExcepcion, ParseException {

        formatoTramiteList = formatoTramiteDao.buscarFormatoTramite(ttrId, "txtGravamenes");

        for (FormatoTramite formatoTramite : formatoTramiteList) {
            switch (formatoTramite.getFmtLinea()) {
                case 10:
                    gravamenes = formatoTramite.getFmtTexto();
                    break;
                case 20:
                    String txtGravamen = "";
                    for (Gravamen gravamen : listaGravamenMostrar) {
                        String fecha = fechasUtil.convertirFecha_A_letras(gravamen.getGraFHR());
                        txtGravamen = txtGravamen + "Matrícula: " + gravamen.getPrdMatricula().getPrdMatricula()
                                + ", " + gravamen.getGraDescripcion().trim() + " con fecha: "
                                + fecha + "<br>";

                    }
                    List<TramiteDetalle> listaTramDetalleN = tramiteDetalleDao.listar_por_repertorio_tramite_propietarioTipoTramComp(Integer.valueOf(repertorio), Long.valueOf(tramite), "N");
                    List<GravamenDetalle> listGravDet = new ArrayList<>();
                    for (TramiteDetalle tramDet : listaTramDetalleN) {
                        listGravDet = gravamenDetalleDao.listarPorIdPersona(tramDet.getPerId().getPerId());
                        for (GravamenDetalle gravamenDetalle : listGravDet) {
                            String fecha = fechasUtil.convertirFecha_A_letras(gravamenDetalle.getGraId().getGraFHR());
                            txtGravamen = txtGravamen + "Matrícula: " + gravamenDetalle.getGraId().getPrdMatricula().getPrdMatricula()
                                    + ", " + gravamenDetalle.getGraId().getGraDescripcion().trim() + " con fecha: "
                                    + fecha + "<br>";

                        }
                    }
                    if(listaGravamenMostrar.isEmpty() && listGravDet.isEmpty()){
                        txtEditGravamenes = "No existen gravámenes";
                    }else{
                        txtEditGravamenes = txtGravamen;
                    }
                    
                    if(txtEditGravamenes.equals("") || txtEditGravamenes == null){
                            readOnly_Grav = false;
                        }
                    
                    /************************************/

                    String sql = "SELECT PrdMatricula FROM RepertorioPropiedad where RepNumero = " + repertorio + " ORDER BY PrdMatricula";
                    numMatricula = (repertorioPropiedadDao.ObtenerPropiedadNumMatricula(sql));
                    for (String numeroMatricula : numMatricula) {
                        System.out.println("ACC NUM MAT: " + numeroMatricula);
                        stringMatricula = String.join(" ", numeroMatricula);
                        matricula.setPrdMatricula(stringMatricula);

                    }

                    

                    /*
                    fmtTexto = formatoTramite.getFmtTexto();
                    int count = 0;
                    List<String> listFinal = new ArrayList<>();
                    String[] info = new String[0];

                    Pattern p = Pattern.compile("'([^']*)'");
                    Matcher m = p.matcher(fmtTexto);
                    String sql = "SELECT PrdMatricula FROM RepertorioPropiedad where RepNumero = " + repertorio + " ORDER BY PrdMatricula";
                    numMatricula = (repertorioPropiedadDao.ObtenerPropiedadNumMatricula(sql));

                    listaAccidentePorMatricula.addAll(numMatricula);
                    int k = 0;
                    partsMatriculaGravamenes = new String[listaAccidentePorMatricula.size()];

                    for (String numMatric : listaAccidentePorMatricula) {
                        partsMatriculaGravamenes[k++] = numMatric;
                    }
                    for (String string : listaAccidentePorMatricula) {
                        System.out.println("LLLMMMAAAA: " + string);

                    }
                    for (int i = 0; i < partsMatriculaGravamenes.length; i++) {
                        System.out.println("aaaoooa:" + partsMatriculaGravamenes[i]);
                    }
                    auxGravamen = 0;
                    for (String numeroMatricula : numMatricula) {
                        System.out.println("ACC NUM MAT: " + numeroMatricula);
                        stringMatricula = String.join(" ", numeroMatricula);;
                        matricula.setPrdMatricula(Integer.valueOf(stringMatricula));
                        where = " WHERE AccEstado = 'A' AND PrdMatricula = " + Integer.valueOf(numeroMatricula);
                        auxGravamen++;

                        try {

                            count++;

                            int count3 = -1;
                            String sql2 = "SELECT * FROM Accidente" + where + " ORDER BY PrdMatricula";

                            listaAccidente = (accidenteDao.buscarAccidentes(sql2));

                            if (count == 1) {
                                info = new String[listaAccidente.size()];
                            }
                            int aux = 0;
                            for (Accidente string : listaAccidente) {
                                prdMatricula = string.getPrdMatricula().getPrdMatricula();
                                aux++;
                                if (aux == 1) {
                                    prdMatriculaAux = prdMatricula;

                                }

                                count3++;
                                if (count == 1) {
                                    for (int i = 0; i < info.length; i++) {
                                        if (count3 == i) {

                                            if (prdMatricula == prdMatriculaAux) {
                                                listFinal.add(string.getAccDescripcion());

                                            }
                                            if (prdMatricula != prdMatriculaAux) {
                                                listFinal.add(" nlnl1 " + string.getAccDescripcion());

                                            }

                                            prdMatriculaAux = prdMatricula;
                                            txtEditGravamenesTemp = String.join(", ", listFinal);
                                            txtEditGravamenesTemp = txtEditGravamenesTemp.replace("null", "");

                                        }
                                    }

                                }

                            }

                            String txtTemp = "";
                            txtTemp = txtEditGravamenesTemp + txtTemp;

                            String separador = Pattern.quote("nlnl1");
                            partsGravamenes = new String[0];
                            partsGravamenes = txtTemp.split(separador);

                            List<String> listaCombinada = new ArrayList<>();
                            for (int i = 0; i < partsGravamenes.length; i++) {

                                if (txtTemp.equals("") || txtTemp.equals("null")) {
                                    listaCombinada.add("");
                                    tempGravamen = String.join("", listaCombinada);
                                    tempGravamen = tempGravamen.replace("null", "");

                                } else {
                                    listaCombinada.add("Para la matricula: " + partsMatriculaGravamenes[auxGravamen - 1] + ": "
                                            + partsGravamenes[i] + ".");
                                    tempGravamen = String.join("", listaCombinada);
                                    tempGravamen = tempGravamen.replace("null", "");

                                }

                            }
                            for (int i = 0; i < partsGravamenes.length; i++) {
                                partsGravamenes[i] = null;

                            }
                            txtEditGravamenesTemp = "";
                            txtEditGravamenes = tempGravamen + txtEditGravamenes;

                        } catch (Exception e) {
                            System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.txtGravamenes()");
                            System.out.println(e);
                        }
                    }
                     */
                    break;

                default:
                    System.out.println("no match");
            }

        }

    }

    public void txtObservaciones() throws ServicioExcepcion {

        formatoTramiteList = formatoTramiteDao.buscarFormatoTramite(ttrId, "txtObservaciones");

        for (FormatoTramite formatoTramite : formatoTramiteList) {
            switch (formatoTramite.getFmtLinea()) {
                case 10:

                    observaciones = formatoTramite.getFmtTexto();

                    break;
                case 20:
                    fmtTexto = formatoTramite.getFmtTexto();
                    int count = 0;
                    List<String> listFinal = new ArrayList<>();
                    String[] info = new String[0];

                    Pattern p = Pattern.compile("'([^']*)'");
                    Matcher m = p.matcher(fmtTexto);
                    if (verificarTexto(fmtTexto)) {

                        where = " WHERE TraNumero = " + Long.parseLong(tramite) + " AND TdtNumeroRepertorio = " + repertorio;
                        //txtObservaciones(fmtTexto);

                        try {

                        } catch (Exception e) {
                            System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.txtObservaciones()");
                            System.out.println(e);
                        }
                    } else {
                        txtEditObservaciones = fmtTexto;
                    }
                    if(txtEditObservaciones.equals("") || txtEditObservaciones == null){
                            readOnly_Obs = false;
                        }

                    break;

                default:
                    System.out.println("no match");
            }

        }

    }

    /**
     * Obtener los titulos (inputText) para los cuadros de texto (TextEditor) *
     */
    public void fmtTextoTitulos() throws ServicioExcepcion {
        tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(tramite), contrato);

        formatoTramiteList = formatoTramiteDao.buscarPorIdTramite(tramiteDetalle.getTdtTtrId());

        for (FormatoTramite formatoTramite : formatoTramiteList) {

            if (formatoTramite.getFmtTitulo().equals("S") && formatoTramite.getFmtIdCuadro().equals("txtOtorgamiento")) {
                otorgamiento = formatoTramite.getFmtTexto();
            }

            if (formatoTramite.getFmtTitulo().equals("S") && formatoTramite.getFmtIdCuadro().equals("txtCuantias")) {
                cuantias = formatoTramite.getFmtTexto();
            }

            /*
            if (formatoTramite.getFmtTitulo().equals("S") && formatoTramite.getFmtIdCuadro().equals("txtCabecera")) {
                tituloCabecera = formatoTramite.getFmtTexto();
            }

            if (formatoTramite.getFmtTitulo().equals("S") && formatoTramite.getFmtIdCuadro().equals("txtCompareciente")) {
                compareciente = formatoTramite.getFmtTexto();
            }
            if (formatoTramite.getFmtTitulo().equals("S") && formatoTramite.getFmtIdCuadro().equals("txtAntecedentes")) {
                antecedentes = formatoTramite.getFmtTexto();
            }
            if (formatoTramite.getFmtTitulo().equals("S") && formatoTramite.getFmtIdCuadro().equals("txtObjetoContrato")) {
                objetoContrato = formatoTramite.getFmtTexto();
            }
            if (formatoTramite.getFmtTitulo().equals("S") && formatoTramite.getFmtIdCuadro().equals("txtLinderos")) {
                linderos = formatoTramite.getFmtTexto();
            }
            if (formatoTramite.getFmtTitulo().equals("S") && formatoTramite.getFmtIdCuadro().equals("txtGravamenes")) {
                gravamenes = formatoTramite.getFmtTexto();
            }
            if (formatoTramite.getFmtTitulo().equals("S") && formatoTramite.getFmtIdCuadro().equals("txtObservaciones")) {
                observaciones = formatoTramite.getFmtTexto();
            }
        
             */
        }

    }

    /* Obtener el texto que se muestra en cada TextEditor */
 /*
    public void fmtTextoContenido() throws ServicioExcepcion, ParseException {
        tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(tramite), contrato);

        formatoTramiteList = formatoTramiteDao.buscarPorIdTramite(tramiteDetalle.getTdtTtrId());

        for (FormatoTramite formatoTramite : formatoTramiteList) {
            if (formatoTramite.getFmtTitulo().equals("N") && formatoTramite.getFmtIdCuadro().equals("txtCompareciente")) {
                

            }

            if (formatoTramite.getFmtTitulo().equals("N") && formatoTramite.getFmtIdCuadro().equals("txtAntecedentes")) {
               

            }

            if (formatoTramite.getFmtTitulo().equals("N") && formatoTramite.getFmtIdCuadro().equals("txtObjetoContrato")) {
               

            }

            if (formatoTramite.getFmtTitulo().equals("N") && formatoTramite.getFmtIdCuadro().equals("txtLinderos")) {
                

            }
           

            if (formatoTramite.getFmtTitulo().equals("N") && formatoTramite.getFmtIdCuadro().equals("txtGravamenes")) {
                
            }

            if (formatoTramite.getFmtTitulo().equals("N") && formatoTramite.getFmtIdCuadro().equals("txtObservaciones")) {
              

            }
        }

    }
     */
    public void onRowSelect(SelectEvent event) throws ServicioExcepcion, ParseException {
        
        readOnly_ObjCont = true;
        readOnly_Antec  = true;
        readOnly_Lind = true;
        readOnly_Grav = true;
        readOnly_Obs = true;

        limpiarCamposTxtEdit();
        cargarDatosCantonNotaria();

        repertorio = "" + (((Repertorio) event.getObject()).getRepNumero()).toString() + "";
        tramite = "" + (((Repertorio) event.getObject()).getTraNumero().getTraNumero()).toString() + "";
        contrato = "" + (((Repertorio) event.getObject()).getRepTtrDescripcion()) + "";

        numRepertorioStr = repertorio;
        numTramiteStr = tramite;
        strContrato = contrato;

        tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(tramite), contrato);

        ttrId = tramiteDetalle.getTdtTtrId();

        repertorioNum = repertorioDao.encontrarRepertorioPorId(repertorio);
        //repertorioNum.setRepNumero(Long.valueOf(repertorio));

        repertorioSelected = repertorioDao.encontrarRepertorioPorId(repertorio);

        List<RepertorioUsuario> listaRepUsr = new ArrayList<>();

        listaRepUsr = RepertorioUsuarioDao.listarPorTipoPorRepertorioPorEstadoPorUsrLog("INS",
                ((Repertorio) event.getObject()).getRepNumero(),
                "A", dataManagerSesion.getUsuarioLogeado().getUsuId());

        if (!listaRepUsr.isEmpty()) {
            setRepertorio(repertorio);
            setNumTramite(tramite);
            setContrato(contrato);
            setFHR(FHR);

            //llenarDatosDePopup();
            mostrarDlg = true;
        }

        buscarActa();
        fmtTextoTitulos();
        txtCabecera();
        if(esActaNueva){
            //IMPORTANTE: MANTENER ESTE ORDEN
        
        txtAntecedentes();
        txtCompareciente();
        txtLinderos();
        verGravamenes();
        txtGravamenes();
        txtObjetoContrato();
        txtObservaciones();            
        }
        else{
            txtEditAntecedentes = actaEncontrada.getActDescripcion3();
            txtEditCompareciente = actaEncontrada.getActDescripcion1();
            txtEditCuantias = actaEncontrada.getActDescripcion6();
            txtEditGravamenes = actaEncontrada.getActDescripcion7();
            txtEditLinderos = actaEncontrada.getActDescripcion5();
            txtEditObjetoContrato = actaEncontrada.getActDescripcion4();
            txtEditObservaciones = actaEncontrada.getActDescripcion8();           
            txtEditOtorgamiento = actaEncontrada.getActDescripcion2();
                    
        }


        //***************
        renderedTxtEdit = true;
        deshabilitarBotonGuardar = true;
        mostrarDlgPreviewActa = false;
        disabledTabActa = false;
        disabledTabRazon = true;
        disabledTabCertifcado = true;
        renderedBtnsTab = true;
        

        if (tramiteDetalle != null && tramiteDetalle.getTdtTtrDescripcion().equals("TESTAMENTO")) {

            try {
                cabeceraHTML = "";
                txtCabecera();
                tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
                //libro = tipoTramite.getTplId();
                System.err.println("LIBRO NUMERO: " + libro);
                setTestamento("<strong>" + compareciente + "</strong></br>"
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
            } catch (ServicioExcepcion | ParseException e) {
                System.out.println(e);
                actaHTML = "";
            }
        }

    }

    public boolean verificarTexto(String fmtText) {

        Pattern p = Pattern.compile("'([^']*)'");
        Matcher m = p.matcher(fmtText);

        if (m.find()) {
            return true;

        } else {
            return false;
        }

    }

    public void txtCabecera() throws ServicioExcepcion, ParseException {

        if (tramite != null && contrato != null && tramiteDetalle != null) {
            tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
            Date today = new Date();
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            Date todayWithZeroTime = formatter.parse(formatter.format(today));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(today);
            int anio = calendar.get(Calendar.YEAR);
            numInscripcion = actaDao.buscarPorAnioYTipoLibroId(anio, tipoTramite.getTplId().getTplId());
            String sql = "SELECT TraNotaria from Tramite WHERE TraNumero = " + tramite;
            TraNotaria = (tramiteDao.ObtenerTramiteNotaria(sql));

            formatoTramiteList = formatoTramiteDao.buscarFormatoTramite(ttrId, "txtCabecera");
            String titulo = "";
            String numeroInscripcion = "";
            String numeroTramite = "";
            String numeroRepertorio = "";
            String fechaRepertorio = "";
            String tomo = "";
            String notaria = "";
            String parroquia = "";
            String tipoContrato = "";
            String matriculaActa = "";
            String tituloPDF = "";
            String numeroInscripcionPDF = "";
            String numeroTramitePDF = "";
            String numeroRepertorioPDF = "";
            String fechaRepertorioPDF = "";
            String tomoPDF = "";
            String notariaPDF = "";
            String parroquiaPDF = "";
            String tipoContratoPDF = "";
            String matriculaActaPDF = "";

            for (FormatoTramite formatoTramite : formatoTramiteList) {
                fmtTexto = formatoTramite.getFmtTexto();
                switch (formatoTramite.getFmtLinea()) {
                    case 10:
                        tituloCabecera = formatoTramite.getFmtTexto();

                        break;
                    case 20:
                        tituloPDF = "<h3 style=\"font-weight: bold>\"" + formatoTramite.getFmtTexto() + "</h3><br></br>";
                        titulo = "<strong>" + formatoTramite.getFmtTexto() + "</strong></br>";
                        cabeceraHTML = cabeceraHTML + titulo;
                        cabeceraPDF = cabeceraPDF + tituloPDF;
                        break;
                    case 30:
                        numeroInscripcion = "<strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + numInscripcion + "</br>";
                        numeroInscripcionPDF = "<p><strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + numInscripcion + "</p><br></br>";
                        cabeceraHTML = cabeceraHTML + numeroInscripcion;
                        cabeceraPDF = cabeceraPDF + numeroInscripcionPDF;
                        break;
                    case 40:
                        numeroTramite = "<strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + numTramite + "</br>";
                        numeroTramitePDF = "<p><strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + numTramite + "</p><br></br>";
                        cabeceraHTML = cabeceraHTML + numeroTramite;
                        cabeceraPDF = cabeceraPDF + numeroTramitePDF;
                        break;
                    case 50:
                        numeroRepertorio = "<strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + repertorio + "</br>";
                        numeroRepertorioPDF = "<p><strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + repertorio + " </p><br></br>";
                        cabeceraHTML = cabeceraHTML + numeroRepertorio;
                        cabeceraPDF = cabeceraPDF + numeroRepertorioPDF;
                        break;
                    case 60:
                        String MES[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

                        String DIA[] = {"01", "02", "03", "04", "05", "06", "07",
                            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17,",
                            "18", "19", "20", "21", "22", "23", "24",
                            "25", "26", "27", "28", "29", "30",
                            "31"};

                        //Calendar calendar = Calendar.getInstance();
                        calendar.setTime(repertorioSelected.getRepFHR());

                        String fecha = "";

                        fecha = (DIA[calendar.get(Calendar.DAY_OF_MONTH) - 1] + "/" + MES[calendar.get(Calendar.MONTH)] + "/" + calendar.get(Calendar.YEAR))
                                + " " + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))
                                + ":" + String.valueOf(calendar.get(Calendar.MINUTE))
                                + ":" + String.valueOf(calendar.get(Calendar.SECOND));

                        fechaRepertorio = "<strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + fecha + "</br>";
                        fechaRepertorioPDF = "<p><strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + fecha + "</p><br></br>";
                        cabeceraHTML = cabeceraHTML + fechaRepertorio;
                        cabeceraPDF = cabeceraPDF + fechaRepertorioPDF;
                        break;
                    case 70:
                        TipoLibro tipoL = new TipoLibro();
                        tipoL = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
                        tomo = "<strong>" + formatoTramite.getFmtTexto() + "</strong>" + tipoL.getTplTomo() + "</br>";
                        tomoPDF = "<p><strong>" + formatoTramite.getFmtTexto() + "</strong>" + tipoL.getTplTomo() + "</p>";
                        cabeceraHTML = cabeceraHTML + tomo;
                        cabeceraPDF = cabeceraPDF + tomoPDF;
                        break;
                    case 80:
                        notaria = "<strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + String.join(", ", TraNotaria) + "</br>";
                        notariaPDF = "<p><strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + String.join(", ", TraNotaria) + "</p><br></br>";
                        cabeceraHTML = cabeceraHTML + notaria;
                        cabeceraPDF = cabeceraPDF + notariaPDF;
                        break;
                    case 90:
                        tipoContrato = "<strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + contrato + "</br>";
                        tipoContratoPDF = "<p><strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + contrato + "</p><br></br>";
                        cabeceraHTML = cabeceraHTML + tipoContrato;
                        cabeceraPDF = cabeceraPDF + tipoContratoPDF;
                        break;
                    case 100:
                        matriculaActa = "<strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + String.join(", ", numMatricula) + "</br>";
                        matriculaActaPDF = "<p><strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + String.join(", ", numMatricula) + "</p><br></br>";
                        cabeceraHTML = cabeceraHTML + matriculaActa;
                        cabeceraPDF = cabeceraPDF + matriculaActaPDF;
                        break;
                    case 11:
                        parroquia = "<strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + tramiteDetalle.getTdtParNombre() + "</br>";
                        parroquiaPDF = "<p><strong>" + formatoTramite.getFmtTexto() + "</strong>" + " " + tramiteDetalle.getTdtParNombre() + " </p><br></br>";
                        cabeceraHTML = cabeceraHTML + parroquia;
                        cabeceraPDF = cabeceraPDF + parroquiaPDF;
                        break;

                    default:
                        System.out.println("no match");
                }
            }
            cabeceraHTML = cabeceraHTML.replace("null", "");
            cabeceraPDF = cabeceraPDF.replace("null", "");

        } else {
            cabeceraHTML = "";
            cabeceraPDF = "";
        }

    }

    public void generarActaParaHTML() throws ServicioExcepcion, ParseException {

        if (tramiteDetalle != null && !tramiteDetalle.getTdtTtrDescripcion().equals("TESTAMENTO")) {
            try {
                cabeceraHTML = "";
                txtCabecera();
                tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());

                String cuerpoActa = "<strong>" + compareciente + "</strong></br>"
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
                        + txtEditObservaciones + "<br></br>";
                actaHTML = cabeceraHTML + "</br>" + cuerpoActa;
                actaHTML = actaHTML.replaceAll("null", "");

                deshabilitarBotonGuardar = false;

            } catch (Exception e) {
                System.out.println(e);
                actaHTML = "";
            }
        } else {
            setActaHTML(cabeceraHTML + "</br>" + getTestamento());
            actaHTML = actaHTML.replaceAll("null", "");
            deshabilitarBotonGuardar = false;
        }
//no esta cambiando testamento para actualizar//
    }

    public void previsualizarActa() throws ServicioExcepcion, ParseException {

        if (!txtEditCompareciente.equals("") || txtEditCompareciente != null
                || !txtEditOtorgamiento.equals("") || txtEditOtorgamiento != null
                || !txtEditAntecedentes.equals("") || txtEditAntecedentes != null
                || !txtEditObjetoContrato.equals("") || txtEditObjetoContrato != null
                || !txtEditLinderos.equals("") || txtEditLinderos != null
                || !txtEditCuantias.equals("") || txtEditCuantias != null
                || !txtEditGravamenes.equals("") || txtEditGravamenes != null
                || !txtEditObservaciones.equals("") || txtEditObservaciones != null) {

            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dlgPreviewActa').show();");
        }
        generarActaParaHTML();

    }

    public void generarActaParaPDF() throws ServicioExcepcion, ParseException {

        if (tramiteDetalle != null) {

            try {
                cabeceraPDF = "";
                txtCabecera();
                tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
                //libro = tipoTramite.getTplId();
                System.err.println("LIBRO NUMERO: " + libro);

                String cuerpoActa = "<p><strong>" + compareciente + "</strong> </p><br></br>"
                        + "<p style=\"text-align: justify;\">" + txtEditCompareciente + "</p>" + "<br></br><br></br>"
                        + "<p><strong>" + otorgamiento + "</strong> </p><br></br>"
                        + "<p style=\"text-align: justify;\">" + txtEditOtorgamiento + "</p>" + "<br></br><br></br>"
                        + "<p><strong>" + antecedentes + "</strong> </p><br></br>"
                        + "<p style=\"text-align: justify;\">" + txtEditAntecedentes + "</p>" + "<br></br><br></br>"
                        + "<p><strong>" + objetoContrato + "</strong> </p><br></br>"
                        + "<p style=\"text-align: justify;\">" + txtEditObjetoContrato + "</p>" + "<br></br><br></br>"
                        + "<p><strong>" + linderos + "</strong> </p><br></br>"
                        + "<p style=\"text-align: justify;\">" + txtEditLinderos + "</p>" + "<br></br><br></br>"
                        + "<p><strong>" + cuantias + "</strong> </p><br></br>"
                        + "<p style=\"text-align: justify;\">" + txtEditCuantias + "</p>" + "<br></br><br></br>"
                        + "<p><strong>" + gravamenes + "</strong> </p><br></br>"
                        + "<p style=\"text-align: justify;\">" + txtEditGravamenes + "</p>" + "<br></br><br></br>"
                        + "<p><strong>" + observaciones + "</strong> </p><br></br>"
                        + "<p style=\"text-align: justify;\">" + txtEditObservaciones + "</p>" + "<br></br><br></br>";

                actaPDF = "<br></br><p>" + cabeceraPDF + "</p>" + "<br></br>" + cuerpoActa;
                actaPDF = actaPDF.replaceAll("null", "");
                deshabilitarBotonGuardar = false;

            } catch (Exception e) {
                System.out.println(e);
                actaPDF = "";
            }
        }

    }

    public void prepararActaParaGuardar() throws ServicioExcepcion, ParseException {

            
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            if (stringMatricula != "" || stringMatricula != null) {

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
                    if(esActaNueva){                       
                    
                    propiedad = propiedadDao.encontrarPropiedadPorId(stringMatricula);
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
                    if (actaHTML != "" && actaPDF != "") {

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
                        actaGuardar.setRepNumero(repertorioNum);
                        actaGuardar.setPrdMatricula(matricula); //guarda solo una de todas las matriculas
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
                        //llenarDatos();
                        //limpiarCamposTxtEdit();

                    }
                        
                    }
                    else{
                        actaGuardar = actaEncontrada;
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

            }

        } catch (Exception e) {
            System.out.println(e);
            //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo guardar el Acta", ""));
        }

        //context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No guardar el Acta", ""));
    }
    
    public void buscarActa(){
         try {
            actaEncontrada = actaDao.buscarActaPorNumRepertorio(Long.valueOf(repertorio));
            if(actaEncontrada != null){                
                esActaNueva = false;
            }
            else{
                esActaNueva = true;
            }
            
        } catch (Exception e) {
            System.out.println(e);            
        }
    }
    
    public void guardarActa() throws ServicioExcepcion, ParseException{
        FacesContext context = FacesContext.getCurrentInstance();
        prepararActaParaGuardar();
        tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
        if(esActaNueva){
            actaDao.guardarSalida(actaGuardar);
            
                        if (tipoLibro.getTplGravamen()) {

                            for (RepertorioPropiedad repPropiedad : listaRepertorioPropiedad) {
                                Gravamen gravamen = new Gravamen();
                                gravamen.setPrdMatricula(repPropiedad.getPrdMatricula());
                                gravamen.setRepNumero(repertorioNum);
                                gravamen.setGraDescripcion("Gravamen " + tipoLibro.getTplDescripcion());
                                gravamen.setGraEstado("A");
                                gravamen.setGraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                gravamen.setGraFHR(Calendar.getInstance().getTime());
                                gravamenDao.create(gravamen);

                            }
                        }            
        }
        else{
            actaDao.edit(actaGuardar);
        }
        
        
                        //confirmarActa();
                        mostrarDlgPreviewActa = true;
                        context.addMessage(null, new FacesMessage("Acta Guardada", ""));
                        
                        indiceTab = 0;
        disabledTabActa = true;
        disabledTabRazon = false;
        disabledTabCertifcado = true;
        renderedTabRazon = false;
        renderedTabCertifcado = false;
        renderedTabActa = true;

                        renderedTxtEdit = false;
                        deshabilitarBotonGuardar = true;

                        

                        if (tipoLibro.getTplPropietario()) {
                            listarComparecientes();
                            for (TramiteDetalle tramDetalle : listaTramiteDetalle) {

                                TipoTramiteCompareciente tipoTramComparec = new TipoTramiteCompareciente();
                                tipoTramComparec = tipoTramiteComparecienteDao.buscarPorId(tramDetalle.getTtcId().getTtcId());
                                if (tipoTramComparec.getTtcPropietario().equals("N")) {
                                    for (RepertorioPropiedad repPropiedad : listaRepertorioPropiedad) {

                                        Propietario propietario = new Propietario();
                                        propietario.setPerId(tramDetalle.getPerId());
                                        propietario.setPprPerIdentificacion(tramDetalle.getTdtPerIdentificacion());
                                        propietario.setPprPerNombre(tramDetalle.getTdtPerNombre());
                                        propietario.setPprPerApellidoPaterno(tramDetalle.getTdtPerApellidoPaterno());
                                        propietario.setPpPerApellidoMaterno(tramDetalle.getTdtPerApellidoMaterno());
                                        propietario.setPprEstado("A");
                                        propietario.setPprFHR(Calendar.getInstance().getTime());
                                        propietario.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                        propietario.setPrdMatricula(repPropiedad.getPrdMatricula());
                                        propietario.setPrdRepertorio(Integer.parseInt(repertorio));
                                        propietarioDao.create(propietario);

                                    }
                                } else if (tipoTramComparec.getTtcPropietario().equals("S")) {
                                    List<Propietario> listaPropietario = new ArrayList<>();
                                    for (RepertorioPropiedad repPropiedad : listaRepertorioPropiedad) {
                                        listaPropietario.clear();
                                        listaPropietario = propietarioDao.buscarPor_Id_Persona_Por_Matricula(tramDetalle.getPerId().getPerId(), repPropiedad.getPrdMatricula().getPrdMatricula());
                                        for (Propietario propietario : listaPropietario) {
                                            propietario.setPprEstado("I");
                                            propietarioDao.edit(propietario);
                                        }
                                        //crea propietario con el nuevo repertorio
                                        Propietario propietarioNuevoS = new Propietario();
                                        propietarioNuevoS.setPerId(tramDetalle.getPerId());
                                        propietarioNuevoS.setPprPerIdentificacion(tramDetalle.getTdtPerIdentificacion());
                                        propietarioNuevoS.setPprPerNombre(tramDetalle.getTdtPerNombre());
                                        propietarioNuevoS.setPprPerApellidoPaterno(tramDetalle.getTdtPerApellidoPaterno());
                                        propietarioNuevoS.setPpPerApellidoMaterno(tramDetalle.getTdtPerApellidoMaterno());
                                        propietarioNuevoS.setPprEstado("I");
                                        propietarioNuevoS.setPprFHR(Calendar.getInstance().getTime());
                                        propietarioNuevoS.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                        propietarioNuevoS.setPrdMatricula(repPropiedad.getPrdMatricula());
                                        propietarioNuevoS.setPrdRepertorio(Integer.parseInt(repertorio));
                                        propietarioDao.create(propietarioNuevoS);

                                    }

                                }

                            }

                        }
                        servicioRepertorioUsuario.actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long.valueOf(repertorio),"INS");
        
    }

    public void confirmarActa() throws ServicioExcepcion {

        tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(Long.valueOf(tramite));

        //usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("IMP", "Impresion", 1);
        Boolean exiteCargaLaboral = cargaLaboralDao.existeCargaLaboral(dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");

        if (exiteCargaLaboral == true) {

            buscarCargaLaboral = cargaLaboralDao.buscarCargaLaboral(dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");
            short carga = (short) (buscarCargaLaboral.getCarAsignado() + 1);
            buscarCargaLaboral.setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            buscarCargaLaboral.setCarFHR(Calendar.getInstance());
            buscarCargaLaboral.setCarTipo("RAZ");
            buscarCargaLaboral.setCarAsignado(carga);
            buscarCargaLaboral.setUsuId(dataManagerSesion.getUsuarioLogeado());
            cargaLaboralDao.edit(buscarCargaLaboral);

        } else {
            CargaLaboral cargaLab = new CargaLaboral();
            short carga = 1;
            cargaLab.setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            cargaLab.setCarFHR(Calendar.getInstance());
            cargaLab.setCarTipo("RAZ");
            cargaLab.setCarAsignado(carga);
            cargaLab.setUsuId(dataManagerSesion.getUsuarioLogeado());
            cargaLaboralDao.create(cargaLab);

        }

        try {

            repertorioUsuario = new RepertorioUsuario();
            repertorioUsuario.setRpuTipo("RAZ");
            repertorioUsuario.setUsuId(dataManagerSesion.getUsuarioLogeado());
            repertorioUsuario.setRpuEstado("A");
            repertorioUsuario.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            repertorioUsuario.setRpuFHR(Calendar.getInstance().getTime());
            repertorioUsuario.setRepNumero(repertorioNum);

            servicioRepertorioUsuario.create(repertorioUsuario);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, secuenciaControlador.getControladorBb().getNumeroTramite().toString(),
                    "Acta " + secuenciaControlador.getControladorBb().getNumeroTramite()
                    + " asignada al usuario " + dataManagerSesion.getUsuarioLogeado().getUsuLogin(),
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), dataManagerSesion.getUsuarioLogeado().getUsuLogin());

        } catch (Exception e) {
            e.printStackTrace();
        }
        tramiteSeleccionado.setTraEstado("RAZ");
        servicioTramite.edit(tramiteSeleccionado);
        tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, secuenciaControlador.getControladorBb().getNumeroTramite().toString(),
                "ACTUALIZACIÓN DEL ESTADO DE TRAMITE A 'RAZ'",
                tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), dataManagerSesion.getUsuarioLogeado().getUsuLogin());

        int numRepUsuActualizados = 0;
        numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(repertorioNum.getRepNumero(),
                dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
        System.out.println("repertorios actualizados: " + numRepUsuActualizados);

    }

    public void clear() {
        cuantiaAlcabalas = (float) 0.0;
        cuantiaMonto = (float) 0.0;
        cuantiaRegistro = (float) 0.0;
        cuantiaPlusvalia = (float) 0.0;
    }

    public void verGravamenes() throws ServicioExcepcion {
        try {
            listaGravamenMostrar.clear();
            listaGravamen.clear();

            for (RepertorioPropiedad repProp : listaRepertorioPropiedad) {

                listaGravamen = gravamenDao.buscarPorMatricula(repProp.getPrdMatricula());

                if (!listaGravamen.isEmpty() || listaGravamen != null) {
                    listaGravamenMostrar.addAll(listaGravamen);
                }

            }
//            listaProp.clear();
//            for (RepertorioPropiedad repProp : listaRepertorioPropiedad) {
//                Propiedad propiedad =new  Propiedad();
//                propiedad = propiedadDao.encontrarPropiedadPorId(repProp.getPrdMatricula().getPrdMatricula().toString());
//                listaProp.add(propiedad);              
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
        }

    }

    public void listarComparecientes() throws ServicioExcepcion, ParseException {
        Repertorio repert = new Repertorio();
        repert = repertorioDao.encontrarRepertorioPorId(repertorio);
        listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                repert.getTraNumero().getTraNumero(), repert.getRepTtrId(), Long.valueOf(repertorio));

        //listaTipoTramiteCompareciente = tipoTramiteComparecienteDao.listarTipoTramiteComparecientePorTipoTramite(Long.valueOf(repert.getRepTtrId()));
        listaTipoCompareciente = tipoTramiteComparecienteDao.listarTipoComparecientePorTipoTramite(Long.valueOf(repert.getRepTtrId()));
        txtCompareciente();

        for (TramiteDetalle tramiteDetalle1 : listaTramiteDetalle) {
            if (tramiteDetalle1.getTtcId().getTpcId().getTpcDescripcion().equals("N")) {
                listaTramDetalleCompN.add(tramiteDetalle);
            } else if (tramiteDetalle1.getTtcId().getTpcId().getTpcDescripcion().equals("S")) {
                listaTramDetalleCompS.add(tramiteDetalle);
            }

        }
    }

    public void buscarPersona() throws IOException, ServicioExcepcion {

        if (identificacion != null) {
            getTramitesControladorBb().setIdentificacion(identificacion);

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
            tipoTramCompareciente = tipoTramiteComparecienteDao.buscarPorIdTramitePorTipoCompareciente(
                    tipoTramite.getTtrId(), tipoComparec.getTpcId());
            short idLibro = tipoTramite.getTplId().getTplId().shortValue();
            TramiteDetalle tramDetalle = new TramiteDetalle();
            tramDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(repertorioNum.getTraNumero().getTraNumero(), tipoTramite.getTtrDescripcion());

            TramiteDetalle tramDet = new TramiteDetalle();
            getTramitesControladorBb().setPersonaConyugue(getTramitesControladorBb().getPersona().getPerIdConyuge());
            tramDet.setPerId(getTramitesControladorBb().getPersona());
            tramDet.setTdtConyuguePerId(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerId());
            tramDet.setTdtConyuguePerNombre(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerNombre() + " " + getTramitesControladorBb().getPersona().getPerIdConyuge().getPerApellidoPaterno());
            tramDet.setTdtConyuguePerTipoIden(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerTipoIdentificacion());
            tramDet.setTdtEstado("A");
            tramDet.setTdtFHR(Calendar.getInstance());
            tramDet.setTdtFechaRegistro(Calendar.getInstance());
            tramDet.setTdtNumeroRepertorio(Integer.valueOf(repertorio));
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
            tramDet.setTraNumero(repertorioNum.getTraNumero());
            tramDet.setTtcId(tipoTramCompareciente);
            tramDet.setTdtTplDescripcion(tipoTramite.getTtrDescripcion());
            tramDet.setTdtParId(tramDetalle.getTdtParId());
            tramDet.setTdtParNombre(tramDetalle.getTdtParNombre());
            tramDet.setTdtCatastro(tramDetalle.getTdtCatastro());
            tramDet.setTdtPredio(tramDetalle.getTdtPredio());
            tramiteDetalleDao.guardarDetalleTramite(tramDet);

            listarComparecientes();
            System.out.println("tramite detalle guardado");

        } catch (ServicioExcepcion | NumberFormatException | ParseException e) {
            System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.guardarTramiteDetalle()");
            System.err.println(e);
        }

    }

    public void onRowSelectComparecientes(SelectEvent event) throws ServicioExcepcion, ParseException {

        tramDetalleId = "" + (((TramiteDetalle) event.getObject()).getTdtId()).toString() + "";

    }

    public void eliminarCompareciente(Long id) throws ServicioExcepcion, ParseException {
        try {
            TramiteDetalle tramiteDetalle = new TramiteDetalle();
            tramiteDetalle = tramiteDetalleDao.buscarPorId(Long.valueOf(id));
            tramiteDetalle.setTdtEstado("I");
            tramiteDetalleDao.edit(tramiteDetalle);
            listarComparecientes();

        } catch (ServicioExcepcion | ParseException e) {
            System.out.println(e);

        }

    }

    ////////////////////////////////////////////////////////////////////////////////////////
    /**
     * ******************************** DERECHOS Y ACCIONES
     * *****************************
     */
    public void mostrarPropiedades() throws ServicioExcepcion, ParseException {
        listaRepPropiedad = repertorioPropiedadDao.buscarPorNumRepertorio(Long.valueOf(numRepertorioStr));
    }

    public void preCrearDetallePropiedad() {

        propiedadDetalleSeleccionada = new PropiedadDetalle();
        Persona personaSeleccionada = new Persona();

        try {
            Tramite tramiteSelec = tramiteDao.buscarTramitePorNumero(Long.valueOf(numTramiteStr));
            Repertorio repertorioSeleccionado = repertorioDao.encontrarRepertorioPorId(numRepertorioStr);
            listaTramDetComparecientes.clear();
            RepertorioUsuario repertorioUsuarioSelec = RepertorioUsuarioDao.obtenerRepUsu_Por_Rep_Usr_Tipo(
                    repertorioSeleccionado.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
            listaTramDetComparecientes = tramiteDetalleDao.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "N");

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
            for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
                System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.calcularTotalAcciones()");
                if (propiedadDetalle.getPdtEstado().equals("A")) {
                    totalAcc = totalAcc.add(propiedadDetalle.getPdtValor()); //totalAcc += propiedadDetalle.getPdtValor();
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

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void borrarDetallePropiedad(int row) {

        propiedadDetElim.setPdtEstado("I");
        listaPropiedadDetalle.set(row, propiedadDetElim);
        listaPropDetActualizarEstado.add(propiedadDetElim);
        listaPropiedadDetalle.remove(row);

        //servicioPropiedadDetalle.edit(propiedadDetElim);
        calcularTotalAcciones();
    }

    public void crearDetallePropiedad() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.crearDetallePropiedad()");

        calcularTotalAcciones();
        BigDecimal valorAdicional = new BigDecimal(0);
        valorAdicional = propiedadDetalleSeleccionada.getPdtValor();
        BigDecimal bigDec100 = new BigDecimal(100);
        if ((totalAcc.add(valorAdicional)).compareTo(bigDec100) <= 0) {//if (totalAcc + valorAdicional <= 100) {

            if (tramiteDetalleSeleccionado != null) {
                //propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(personaSeleccionada.getPerId()));
                //BigInteger idusuario = BigInteger.valueOf(personaSeleccionada.getPerId());
                propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(tramiteDetalleSeleccionado.getPerId().getPerId()));
                BigInteger idusuario = BigInteger.valueOf(tramiteDetalleSeleccionado.getPerId().getPerId());
                Persona persona = personaServicio.find(new Persona(), idusuario.longValue());
                propiedadDetalleSeleccionada.setPersona(persona);
                propiedadDetalleSeleccionada.setPdtEstado("A");
                listaPropiedadDetalle.add(propiedadDetalleSeleccionada);
                calcularTotalAcciones();
            } else {
                JsfUtil.addErrorMessage("Debe seleccionar una persona");
            }

        } else {
            JsfUtil.addErrorMessage("No puede sobrepasar el 100% (total Acciones: " + totalAcc + ")");
        }
        propiedadDetalleSeleccionada = new PropiedadDetalle();
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

    public void tipoCuantia() {
        if (cuantiaTipo.equals("Exonerado")) {
            disabledRegistroCuantia = true;
        } else {
            disabledRegistroCuantia = false;
        }
    }
    

    public void razon() throws ServicioExcepcion, ParseException{
    
        indiceTab = 1;
        disabledTabActa = true;
        disabledTabRazon = false;
        disabledTabCertifcado = true;
        renderedTabRazon = true;
        renderedTabCertifcado = true;
        renderedTabActa = false;
    }
    
    public void certificado(){
        indiceTab = 2;
        disabledTabActa = true;
        disabledTabRazon = true;
        disabledTabCertifcado = false;
    }
    public void generarCertificado() {

        certificadoGenerado = "";
        String matriculas = "";
        String fechaActa = "";
        String tipoPropiedad = "";
            String descripcionPropiedad = "";
            String parroquia = "";
            String matriculaCert = "";
            descripcionPropiedadCert = "";
            gravamenesCert = "";
            nombresPropietarios = "";
            antecedentesCert = "";
            
        matriculas = String.join(", ", numMatricula);        
        
        if (actaGuardar != null) {
            
            for (String numMatric : numMatricula) {
                Propiedad propiedadCert = propiedadDao.obtenerPorMatricula(numMatric);
                List<Propietario> listaPropietarios = propietarioDao.buscarPor_PropiedadMatricula_Estado(propiedadCert.getPrdMatricula());
                for (Propietario propietario : listaPropietarios) {
                Persona persona = propietario.getPerId();
                nombresPropietarios += persona.getPerApellidoPaterno().trim()
                        + " " + persona.getPerApellidoMaterno().trim()
                        + " " + persona.getPerNombre().trim() + " con cédula de identidad"
                        + " " + persona.getPerIdentificacion().trim() + " casado con"
                        + " " + persona.getPerIdConyuge().getPerApellidoPaterno().trim()
                        + " " + persona.getPerIdConyuge().getPerApellidoMaterno().trim()
                        + " " + persona.getPerIdConyuge().getPerNombre().trim() + " con cédula de identidad"
                        + " " + persona.getPerIdConyuge().getPerIdentificacion().trim() + "; ";
            }
                 tipoPropiedad = propiedadCert.getTpdId().getTpdNombre();
                descripcionPropiedad = propiedadCert.getPrdDescripcion2() + " " + propiedadCert.getPrdDescripcion1();
                parroquia = propiedadCert.getPrdTdtParNombre();
                matriculaCert = propiedadCert.getPrdMatricula().toString();
                descripcionPropiedadCert +=  tipoPropiedad + " " + descripcionPropiedad
                    + ", situado en la parroquia " + parroquia
                    + ", de este cantón, con matrícula número " + matriculaCert + "<br>";
            }

            
            
            
            
            
            
            
            
            if (actaGuardar.getActDescripcion3() != null) {
                antecedentesCert = actaGuardar.getActDescripcion3();
            }
            if (actaGuardar.getActDescripcion7() != null) {
                gravamenesCert = actaGuardar.getActDescripcion7() + "<br>" + actaGuardar.getActDescripcion8();

            }
            if (actaGuardar.getActFch() != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                fechaActa = format.format(actaGuardar.getActFch());
            }            
            
            String nombreResponsable = dataManagerSesion.getUsuarioLogeado().getPerId().getPerNombre().trim()
                    + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoPaterno().trim()
                    + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno().trim(); 

            certificadoGenerado = ""
                    
                    + "<center>"
                    + "<p><center><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN QUITO </strong></center></p>"
                    + "<p><center><strong>CERTIFICADO No.: </strong></center></p>"
                    + "<p><center><strong>FECHA DE INGRESO:</strong>" + fechaActa + "</center></p>"
                    + "<p><br></p>"
                    + "<h3><center><strong> CERTIFICACION</strong></center></h3>"
                    + "<p>&nbsp;</p>"
                    + "</center>"
                    + "<p><strong>Referencias:</strong> 13/02/2009-PO-11370f-4466i-11586r</p>"                    
                    + "<p><strong>Matriculas:</strong>"+ matriculas +"</p><br>"
                    + "<p>El infrascrito Registrador de la Propiedad de este Cantón,"
                    + " luego de revisados los índices y libros que reposan en el archivo, "
                    + "en legal y debida forma certifica que:</p><br>"
                    + "<p><strong>1.- DESCRIPCION DE LA PROPIEDAD:</strong> </p>"
                    + "<p>" + descripcionPropiedadCert + ".</p><br>"
                    + "<p><strong>2.- PROPIETARIO(S):</strong></p>"
                    + "<p>"
                    + nombresPropietarios
                    + "</p><br>"
                    + "<p><strong>3.- FORMA DE ADQUISICION Y ANTECEDENTES:</strong> </p>"
                    + "<p>" + antecedentesCert + ".</p>"
                    + "<p><strong>4.- GRAVAMENES Y OBSERVACIONES:</strong> </p>"
                    + "<p>" + gravamenesCert + ".</p>"
                    + "<p><strong>Responsable:</strong> " + nombreResponsable + "</p><br>"
                    + "<p><br></p><p><strong> </strong></p><p><strong> EL REGISTRADOR</strong></p><br>"
                    + "<p><strong> </strong>&nbsp;</p><p>C11542801.001</p>";
        }
    }
    
    
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

            //limpiarCamposTxtEdit();
            //llenarDatos();

        }
    }
    public void procesarEnRepertorioUsuario() throws ServicioExcepcion {

        try {
            int numRepUsuActualizados = 0;

            numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(Long.valueOf(repertorio),
                    dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");
            
            setRepertorioUsuarioSelec(servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
                Long.valueOf(repertorio), dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ"));

            tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(Long.valueOf(tramite));

            usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("IMP", "Impresion", 1);
            
            cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSelec.getUsuId().getUsuId(), repertorioUsuarioSelec.getRpuTipo() );

            RepertorioUsuario repertorioUsuario = new RepertorioUsuario();
            repertorioUsuario.setRpuTipo("IMP");
            repertorioUsuario.setUsuId(usuarioAsignado);
            repertorioUsuario.setRpuEstado("A");
            repertorioUsuario.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            repertorioUsuario.setRpuFHR(Calendar.getInstance().getTime());
            repertorioUsuario.setRepNumero(repertorioNum);

            servicioRepertorioUsuario.create(repertorioUsuario);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, secuenciaControlador.getControladorBb().getNumeroTramite().toString(),
                    "Razon: " + secuenciaControlador.getControladorBb().getNumeroTramite()
                    + " asignada al usuario " + usuarioAsignado.getUsuLogin(),
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), usuarioAsignado.getUsuLogin());

            tramiteSeleccionado.setTraEstado("IMP");
            servicioTramite.edit(tramiteSeleccionado);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, secuenciaControlador.getControladorBb().getNumeroTramite().toString(),
                    "ACTUALIZACIÓN DEL ESTADO DE TRAMITE A 'IMP'",
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), dataManagerSesion.getUsuarioLogeado().getUsuLogin());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    public void guardarCertificado() throws ServicioExcepcion{
        try {
            Certificado certificado = new Certificado();
        FacturaDetalle facturaDet = facturaDetalleDao.PorNumeroTramite(Integer.valueOf(tramite));
        factura = facturaDet.getFacId();
        TipoCertificado tipoCert = tipoCertificadoDao.buscarPorNombre("GRAVAMEN");
        String predio = "";
        String catastro = "";
        int cont = 0;
        String simbolo = "";
        for (String string : numMatricula) {
            cont ++;
            Propiedad prop = propiedadDao.obtenerPorMatricula(string);
            if(cont < numMatricula.size()){
                simbolo = "-";
            }
            else{
                simbolo = "";
            }
            predio += prop.getPrdPredio() + simbolo;
            catastro += prop.getPrdCatastro() + simbolo;
            
        }
        
        String numCertificado = generarNumeroCertificado();
            short secuencial = obtenerNumeroSecuencia();
            String cerNumero = numCertificado;
        
        certificado.setFacId(facturaDet.getFacId());
        certificado.setCerCatastro(catastro);
        certificado.setCerPredio(predio);
        certificado.setTroId(tipoCert);
        certificado.setCerFechaIngreso(actaGuardar.getActFHR());
        certificado.setCerCuantia(new BigDecimal(0));
        certificado.setCerAdquisicion(antecedentesCert);
        certificado.setCerCertificado(certificadoGenerado);
        certificado.setCerPropiedad(descripcionPropiedadCert);        
        certificado.setCerPropietario(nombresPropietarios);
        certificado.setCerEstado("A");
        certificado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        certificado.setCerFHR(Calendar.getInstance().getTime());
        certificado.setCerGravamen(gravamenesCert);
        certificado.setCerObservacion("Ninguna");
        certificado.setCertificadoPK(new CertificadoPK(cerNumero, secuencial));
        certificadoDao.create(certificado);
        
        } catch (Exception e) {
            System.out.println(e);
        }
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
    
    public void verificarEstadoProceso(){
        List<RepertorioUsuario> listaRepUsu = new ArrayList<>();
        for (Repertorio repertor : listaRepertorioFecha) {
                if(repertor.getTraNumero().getTraNumero().longValue() == tramSeleccTerminar.getTraNumero().longValue()){
                    RepertorioUsuario repUsu= RepertorioUsuarioDao.obtenerRepUsu_Por_Rep_Usr_Tipo(repertor.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
                    listaRepUsu.add(repUsu);
                }
            }
        int aux = 0;
        for (RepertorioUsuario repertorioUsu1 : listaRepUsu) {
            if(repertorioUsu1.getRpuEstadoProceso().equals("TERMINADO")){
                aux ++;
            }          
            
        }
        if(aux == listaRepUsu.size()){
                todosEstadoProcTerminado = true;
            }
        
    }
    public void terminarProceso(){
        try {
            
            FacesContext context = FacesContext.getCurrentInstance();
            //guardarActa();
            verificarEstadoProceso();
            if(todosEstadoProcTerminado){
                confirmarActa();
                razon();
                generarCertificado();
                
                renderedTabRazon = true;
                renderedTabCertifcado = true;
                renderedTabActa = false;
                disabledTabActa = true;
                disabledTabRazon = false;
                disabledTabCertifcado = false;
                indiceTab = 1;
                
            //guardarRazon();
            //guardarCertificado();
            }
            else{
                
String mensaje = "Aún exsiten Procesos sin Terminar";
             context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", mensaje));

            }
            
            
//            servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(Long.valueOf(repertorio),
//                    dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");
//
//            tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(Long.valueOf(tramite));
            
            limpiarCamposTxtEdit();
            llenarDatos();
            razonInscripcion = "";
            indiceTab = 0;
            disabledTabActa = true;          
            disabledTabRazon = true;          
            disabledTabCertifcado = true;  
            renderedBtnsTab = false;
            renderedTxtEdit = false;
            renderedTabActa = false;            
            
            
            
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error al Guardar", ""));
            System.out.println(e);
        }
    }   
    
    
    public void reset(){
        indiceTab = 0;
            disabledTabActa = true;          
            disabledTabRazon = true;          
            disabledTabCertifcado = true;  
            renderedBtnsTab = false;
            renderedTxtEdit = false;
    }

}
