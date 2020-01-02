/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.NuevasMatriculasDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.TipoPropiedad;
import com.rm.empresarial.modelo.TipoPropiedadClase;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.UnidMedida;
import com.rm.empresarial.servicio.NuevasMatriculasServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.BloqueDao;
import com.rm.empresarial.dao.MatriculacionDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.RepresentanteDao;
import com.rm.empresarial.dao.TipoPropiedadDao;
import com.rm.empresarial.dao.TmpAlicuotaDao;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Alicuota;
import com.rm.empresarial.modelo.Bloque;
import com.rm.empresarial.modelo.Compania;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Provincia;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Representante;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TmpAlicuota;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.AlicuotaServicio;
import com.rm.empresarial.servicio.CompaniaServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.LinderoServicio;
import com.rm.empresarial.servicio.ParroquiaServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.PropiedadDetalleServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.PropietarioServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TipoPropiedadClaseServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import com.rm.empresarial.servicio.UnidMedidaServicio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
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
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorNuevasMatriculasPH")
@ViewScoped
public class ControladorNuevasMatriculasPH implements Serializable {

    @Inject
    DataManagerSesion DataManagerSesion;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Inject
    private TramiteUtil tramiteUtil;

    @EJB
    NuevasMatriculasServicio NuevasMatriculasServicio;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @EJB
    private PropiedadDetalleServicio servicioPropiedadDetalle;

    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;

    @EJB
    private LinderoServicio servicioLindero;

    @EJB
    private ParroquiaServicio servicioParroquia;

    @EJB
    private PersonaServicio servicioPersona;

    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @EJB
    private RepertorioServicio servicioRepertorio;

    @EJB
    private TramiteServicio servicioTramite;

    @EJB
    private PropiedadServicio propiedadServicio;

    @EJB
    private RepertorioPropiedadDao servicioRepertorioPropiedad;

    @EJB
    TramiteValorServicio servicioTramiteValor;

    @EJB
    TipoTramiteServicio servicioTipoTramite;

    @EJB
    MatriculacionDao MatriculacionDao;

    @EJB
    RepresentanteDao representanteDao;

    @EJB
    TipoPropiedadClaseServicio servicioTipoPropiedadClase;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    private UnidMedidaServicio servicioUnidadMedida;

    @EJB
    private AlicuotaServicio alicuotaServicio;

    @EJB
    private PersonaServicio personaServicio;

    @EJB
    private PropietarioServicio propietarioServicio;

    @EJB
    private TipoPropiedadDao tipoPropiedadDao;

    @EJB
    private RepertorioUsuarioServicio repertorioUsuarioServicio;

    @EJB
    private CompaniaServicio companiaServicio;

    @EJB
    private TmpAlicuotaDao tmpAlicuotaDao;

    @EJB
    private BloqueDao bloqueDao;

    @Getter
    @Setter
    private String estadoFocusMatricula;

    @Getter
    @Setter
    private Secuencia secuencia;

    @Getter
    @Setter
    private Compania nuevaCompania;

    @Getter
    @Setter
    private Boolean renderedMensaje = false;
    @Getter
    @Setter
    private Boolean renderedPanelDeclaratoria = false;
    @Getter
    @Setter
    private Boolean renderedPanelRevocatoria = false;
    @Getter
    @Setter
    private Boolean rendTabAclaratoriaEliminarProp = false;
    @Getter
    @Setter
    private Boolean disabledBtnProcRevoc = true;
    @Getter
    @Setter
    private Boolean disabledTipoBloque = false;
    @Getter
    @Setter
    private Boolean renderedNumeroAlicTmp;
    @Getter
    @Setter
    private Boolean renderedLetraAlicTmp = false;

    @Getter
    @Setter
    private Boolean renderedSubTipoBlq = false;

    @Getter
    @Setter
    private Boolean renderedNombreBlq = false;

    @Getter
    @Setter
    private Boolean renderedCantAlicTmp;

    @Getter
    @Setter
    private Boolean renderedInicialAlicTmp = false;
    @Getter
    @Setter
    private Boolean renderedLetNumlicTmp = false;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Inject
    @Getter
    @Setter
    private TransformadorNumerosLetrasUtil TransformadorNumerosLetrasUtil;

    @Inject
    @Getter
    @Setter
    private PersonaUtil personaUtil;

    @Getter
    @Setter
    private Persona personaCompania;

    @Getter
    @Setter
    private int idTipoParroquia;
    @Getter
    @Setter
    private TipoPropiedad tipoPropiedadSeleccionada;
    @Getter
    @Setter
    private List<Parroquia> listaParroquia = new ArrayList<>();
    @Getter
    @Setter
    private List<Parroquia> listaParroquiaTramValor = new ArrayList<>();
    @Getter
    @Setter
    private List<TipoPropiedad> listaTipoPropiedad = new ArrayList<>();
    @Getter
    @Setter
    private List<UnidMedida> listaUnidMedida = new ArrayList<>();
    @Getter
    @Setter
    private List<TipoPropiedadClase> listaGrupo = new ArrayList<>();
    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetComparecientes = new ArrayList<>();
    @Getter
    @Setter
    private List<Propiedad> listaPropiedadHija = new ArrayList<>();
    @Getter
    @Setter
    private List<Bloque> listaBloque = new ArrayList<>();
    @Getter
    @Setter
    private List<Bloque> listaBloqueFiltro = new ArrayList<>();

    @Getter
    @Setter
    private List<Alicuota> listaAlicuotaGral = new ArrayList<>();
    @Getter
    @Setter
    private List<Propiedad> listaPropiedadHijaUV = new ArrayList<>();

    @Getter
    @Setter
    private List<Compania> listaCompania = new ArrayList<>();
    @Getter
    @Setter
    private List<Parroquia> listaParriquias = new ArrayList<>();
    @Getter
    @Setter
    private List<Alicuota> listaAlicuota = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropieadadUV = new ArrayList<>();
    @Getter
    @Setter
    private List<TmpAlicuota> listaTmpAlicUV = new ArrayList<>();
    @Getter
    @Setter
    private List<Alicuota> listaAlicuotaMostrar = new ArrayList<>();

//    @Getter
//    @Setter
//    private List<Lindero> listaLindero = new ArrayList<>();
    @Getter
    @Setter
    private String matricula;
    @Getter
    @Setter
    private String matriculaHija;
    @Getter
    @Setter
    private String matriculalPadre;
    @Getter
    @Setter
    private Long numRepertorio;
    @Getter
    @Setter
    private int cantidadAlicuatasTmp;
    @Getter
    @Setter
    private int inicialAlicuatasTmp;
    @Getter
    @Setter
    private int numAlicuatasTmp;
    @Getter
    @Setter
    private String letraAlicuatasTmp;
    @Getter
    @Setter
    private String letraInicAlicuatasTmp;
    @Getter
    @Setter
    private String bloqueNombreFiltro;

    @Getter
    @Setter
    private Boolean esVendibleTmpAlicuota;
    @Getter
    @Setter
    private Boolean renderedPanelPropiedad = false;
    @Getter
    @Setter
    private Boolean renderedPanelPropiedadRev = false;

    @Getter
    @Setter
    private Propiedad propiedadHijaSelec = new Propiedad();

    @Getter
    @Setter
    private Alicuota alicuotaGralSelec = new Alicuota();
    @Getter
    @Setter
    private String tipoNumeracionAlicTmp;

    @Getter
    @Setter
    private String catastro;
    @Getter
    @Setter
    private String predio;
    @Getter
    @Setter
    private String espacial;
    @Getter
    @Setter
    private String superficie;
    @Getter
    @Setter
    private String superficieLetras;
    @Getter
    @Setter
    private String superficieTipoSeleccionada;
    @Getter
    @Setter
    private Parroquia parroquiaSeleccionada;
    @Getter
    @Setter
    private Parroquia parroquiaCompania;
    @Getter
    @Setter
    private Propiedad propiedadNueva;
    @Getter
    @Setter
    private Bloque bloque = new Bloque();

    @Getter
    @Setter
    private Propiedad propiedadPadreMostrar;
    @Getter
    @Setter
    private Propiedad propiedadPadre;

    @Getter
    @Setter
    private String estadoPropiedad;
    @Getter
    @Setter
    private String texto;
    @Getter
    @Setter
    private Lindero linderoConsultado;

    @Getter
    @Setter
    private Integer numDocumento;

    @Getter
    @Setter
    private Boolean boolDocEncontrado = false;

    @Getter
    @Setter
    private Boolean boolOcultarCmbParroquia = false;

    @Getter
    @Setter
    private Boolean disabledBtnGuardarDerAcc = false;

    @Getter
    @Setter
    private Boolean disabledBtnAgregarDerAcc = false;

    @Getter
    @Setter
    private Boolean disabledBtn = false;

    @Getter
    @Setter
    private Boolean disabledTab = true;
    @Getter
    @Setter
    private Boolean disabledTabUndViv = true;

    @Getter
    @Setter
    private Propiedad propiedadSelec;

    @Getter
    @Setter
    private Propiedad propiedadAlicuota;

    @Getter
    @Setter
    private Propiedad propUV = new Propiedad();
    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropiedadDetalle;

    @Getter
    @Setter
    private PropiedadDetalle propiedadDetalleSeleccionada;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleSelec;

    @Getter
    @Setter
    private List<Lindero> listaLinderos;

    @Getter
    @Setter
    private List<Lindero> listaLinderosH;

    @Getter
    @Setter
    private List<Persona> listaPersonaCompania;

    @Getter
    @Setter
    private Lindero linderoSeleccionado;

    @Getter
    @Setter
    private Lindero linderoHSeleccionado;

    @Getter
    @Setter
    private Persona personaSeleccionada;

    @Getter
    @Setter
    private TipoPropiedad tipoProp;

    @Getter
    @Setter
    private String numIdentificacionPersona;

    @Getter
    @Setter
    private BigDecimal totalAcc;

    @Getter
    @Setter
    private Integer filaSeleccionada;

    @Getter
    @Setter
    private Integer indiceTab;

    @Getter
    @Setter
    private Integer indiceTabNuevaProp;

    @Getter
    @Setter
    private Boolean esPropietario;

    @Getter
    @Setter
    private Boolean esBloqueNuevo = true;

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesUnf;

    @Getter
    @Setter
    private Propiedad propiedadSeleccionadaUnf;

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesDiv;

    @Getter
    @Setter
    private Propiedad propiedadSeleccionadaDiv;

    @Getter
    @Setter
    private String nombreComp;
    @Getter
    @Setter
    private String construcComp;

    @Getter
    @Setter
    private BigDecimal totalAlicuotasPropiedad = BigDecimal.ZERO;

    @Getter
    @Setter
    private BigDecimal totalAlicuotasTmpAlic = BigDecimal.ZERO;

    @Getter
    @Setter
    private PropiedadDetalle propiedadDetElim;

    @Getter
    @Setter
    private Repertorio repertorioSeleccionado;

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor;

    @Getter
    @Setter
    private List<TipoTramite> listaTipoTramite;

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValorUnf;

    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropDetActualizarEstado;

    @Getter
    @Setter
    private List<TmpAlicuota> listaTmpAlicuotas = new ArrayList<>();

    @Getter
    @Setter
    private List<TmpAlicuota> listaTmpAlicuotasUV = new ArrayList<>();

    @Getter
    @Setter
    private List<Alicuota> listaAlicuotasUV = new ArrayList<>();

    @Getter
    @Setter
    private List<Alicuota> listaAlicuotasUVSelec = new ArrayList<>();

    @Getter
    @Setter
    private List<TmpAlicuota> listaTmpAlicuotasSelec = new ArrayList<>();

    @Getter
    @Setter
    private TramiteValor tramiteValorSeleccionado;
    @Getter
    @Setter
    private TramiteValor nuevoTramiteValor;

    @Getter
    @Setter
    private Propiedad propiedadHijaSeleccionada;

    @Getter
    @Setter
    private Alicuota alicuota;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleSeleccionado;

    @Getter
    @Setter
    private TmpAlicuota tmpAlicuota;

    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSeleccionado;

    @Getter
    @Setter
    private Boolean mostrarComboGrupo;

    @Getter
    @Setter
    private Boolean esPropiedadNueva;

    @Getter
    @Setter
    private Boolean esTmpAlicuotaNueva;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private String mensajeRev;

    @Getter
    @Setter
    private Boolean revisado;

    @Getter
    @Setter
    private Boolean mostrarTabView;

    @Getter
    @Setter
    private Boolean renderedBtnTerminar;

    @Getter
    @Setter
    private Boolean disabledBtnTerminar;

    @Getter
    @Setter
    private Boolean boolSeleccionadaPropPrincipalUnif = false;

    @Getter
    @Setter
    private String centerGeoMap;

    @Getter
    @Setter
    private MapModel geoModel;

    @Getter
    @Setter
    private int zoom;

    @Getter
    @Setter
    private String direccion;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private Double lat;

    @Getter
    @Setter
    private Double lng;

    public ControladorNuevasMatriculasPH() throws ServicioExcepcion {
        estadoFocusMatricula = "false";
        secuencia = new Secuencia();
        boolOcultarCmbParroquia = false;
        propiedadNueva = new Propiedad();
        propiedadNueva.setPrdEspacial("");
        listaLinderos = new ArrayList<>();
        listaLinderosH = new ArrayList<>();
        listaPropiedadDetalle = new ArrayList<>();
        totalAcc = new BigDecimal(0);
        listaPropiedadesUnf = new ArrayList<>();
        listaPropiedadesDiv = new ArrayList<>();
        propiedadNueva = new Propiedad();
        repertorioSeleccionado = new Repertorio();
        mostrarComboGrupo = true;
        esPropiedadNueva = true;
        esTmpAlicuotaNueva = true;
        revisado = false;
        listaTipoTramite = new ArrayList<>();
        mostrarTabView = false;
        tramiteValorSeleccionado = new TramiteValor();
        nuevoTramiteValor = new TramiteValor();
        repertorioUsuarioSeleccionado = new RepertorioUsuario();
        listaTramiteValorUnf = new ArrayList<>();
        propiedadDetElim = new PropiedadDetalle();
        listaPropDetActualizarEstado = new ArrayList<>();
        disabledBtnTerminar = true;
        renderedBtnTerminar = true;
        tramiteDetalleSeleccionado = new TramiteDetalle();
        nuevaCompania = new Compania();
        parroquiaCompania = new Parroquia();
        propiedadHijaSeleccionada = new Propiedad();
        alicuota = new Alicuota();
        propiedadPadre = new Propiedad();
        propiedadAlicuota = new Propiedad();
        personaCompania = new Persona();
        listaPersonaCompania = new ArrayList<>();
        listaTmpAlicuotas = new ArrayList<>();
        renderedNumeroAlicTmp = false;
        renderedCantAlicTmp = false;
        propiedadPadreMostrar = new Propiedad();
        tipoProp = new TipoPropiedad();
        centerGeoMap = "-0.2029,-78.4911";
        geoModel = new DefaultMapModel();
        zoom = 15;
    }

    @PostConstruct
    public void postConstructor() {
        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        numRepertorio = new Long(params.get("paramRepertorio").toString());
        repertorioSeleccionado = servicioRepertorio.find(new Repertorio(), numRepertorio);
//        matricula = new Long(params.get("matricula").toString());
//        catastro = new String(params.get("catastro").toString());
//        predio = new String(params.get("predio").toString());
//        numRepertorio = new Long(params.get("numRepertorio").toString());
//        numTramite = new Long(params.get("numTramite").toString());
//        fecha = new String(params.get("fecha").toString());

        cargarDatosSeleccion();
//
        cargarCombos();
        //sumarAlicuotaPropiedades();
        try {
            Tramite tramiteSeleccionado = repertorioSeleccionado.getTraNumero();
            TramiteDetalle tramDet = servicioTramiteDetalle.buscarPorNumTramite(tramiteSeleccionado.getTraNumero());
            TipoTramite tipoTramit = servicioTipoTramite.buscarID(tramDet.getTdtTtrId().intValue());
            if (tipoTramit.getTtrCodigo() == 9) {
                renderedPanelPropiedad = false;
                renderedPanelPropiedadRev = true;
                rendTabAclaratoriaEliminarProp = false;
            } else {
                renderedPanelPropiedad = true;
                renderedPanelPropiedadRev = false;
                rendTabAclaratoriaEliminarProp = false;
            }
            //ACLARATORIA
            if (tipoTramit.getTtrCodigo() == 10) {
                rendTabAclaratoriaEliminarProp = true;
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void cargarDatosSeleccion() {
        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
        TipoTramite tipoTramiteSelec = servicioTipoTramite.find(new TipoTramite(), new Long(repertorioSeleccionado.getRepTtrId()));

        try {
            listaTramiteValor = servicioTramiteValor.ListarPor_TipoTramite_NumTramite_Estado_CertificadoNulo(tramiteSelec, tipoTramiteSelec);
            for (Iterator<TramiteValor> iterator = listaTramiteValor.iterator(); iterator.hasNext();) {
                TramiteValor tramiteValorSiguiente = iterator.next();
                if (tramiteValorSiguiente.getTrvNumCatastro().equals("0") && tramiteValorSiguiente.getTraNumPredio().equals("0")) {
                    tramiteValorSiguiente.setTrvAccion(1);
                    servicioTramiteValor.edit(tramiteValorSiguiente);
                    iterator.remove();
                }
            }
            if (listaTramiteValor.isEmpty() && tramiteSelec.getTraClase().equals("J")) {
                disabledBtnTerminar = false;
            } else {
                renderedBtnTerminar = false;
            }
            //VERIFICAR SI EXISTE PROPIEDAD Y TIENE PROPIEDADES HIJAS
            List<TramiteValor> listTramValor = new ArrayList<>();
            for (TramiteValor tramiteValor : listaTramiteValor) {
                Propiedad prop = new Propiedad();
                prop = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tramiteValor.getTraNumPredio().toString(), tramiteValor.getTrvNumCatastro().toString());
                if (prop != null) {
                    List<Propiedad> listProp = new ArrayList<>();
                    listProp = propiedadServicio.listarPorMatriculaPadre(prop.getPrdMatricula());
                    if (listProp.isEmpty()) {
                        tramiteValor.setTienePropHija(false);
                    } else {
                        tramiteValor.setTienePropHija(true);
                    }
                }
                listTramValor.add(tramiteValor);
            }
            listaTramiteValor.clear();
            listaTramiteValor.addAll(listTramValor);

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorNuevasMatriculasPH.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //cargar lista de parroquias para tramite valor y propiedad

            setListaParroquiaTramValor(MatriculacionDao.ListarParroquia());
            setListaParroquia(MatriculacionDao.ListarParroquia());

            //**//
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorNuevasMatriculasPH.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorNuevasMatriculasPH.class.getName()).log(Level.SEVERE, null, ex);
        }

//        parroquiaSeleccionada = listaParroquia.get(0);
//        parroquiaSeleccionada = listaParroquia.get(0);
    }

    public void preCrearNuevoTramiteValor() {

        parroquiaSeleccionada = new Parroquia();
        nuevoTramiteValor = new TramiteValor();

    }

    public void crearTramiteValor() throws ServicioExcepcion {
        if (!nuevoTramiteValor.getTrvNumCatastro().trim().isEmpty()) {
//----        campos requeridos-----
            nuevoTramiteValor.setTtrId(servicioTipoTramite.buscarPorDescripcion(repertorioSeleccionado.getRepTtrDescripcion()));
            nuevoTramiteValor.setTraNumero(repertorioSeleccionado.getTraNumero());
            nuevoTramiteValor.setTrvValor(BigDecimal.ZERO);
            nuevoTramiteValor.setTrvEstado("A");
            nuevoTramiteValor.setTrvUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
            // poner mayusculas y setear catastro o predio en caso de alguno de estos no se haya ingresado
            if (!nuevoTramiteValor.getTrvNumCatastro().trim().isEmpty() && (nuevoTramiteValor.getTraNumPredio().trim().isEmpty() || nuevoTramiteValor.getTraNumPredio() == null)) {
                nuevoTramiteValor.setTrvNumCatastro(nuevoTramiteValor.getTrvNumCatastro().trim().toUpperCase());
                nuevoTramiteValor.setTraNumPredio(nuevoTramiteValor.getTrvNumCatastro().trim().toUpperCase());
            } else if (!nuevoTramiteValor.getTraNumPredio().trim().isEmpty() && (nuevoTramiteValor.getTrvNumCatastro().trim().isEmpty() || nuevoTramiteValor.getTrvNumCatastro() == null)) {
                nuevoTramiteValor.setTraNumPredio(nuevoTramiteValor.getTraNumPredio().trim().toUpperCase());
                nuevoTramiteValor.setTrvNumCatastro(nuevoTramiteValor.getTraNumPredio());
            }
            //-----------
            //llenando datos parroquia-----
            nuevoTramiteValor.setTrvParId(parroquiaSeleccionada.getParId());
            nuevoTramiteValor.setTrvParNombre(parroquiaSeleccionada.getParNombre());
            //-------------------------------
            nuevoTramiteValor.setTrvFHR(Calendar.getInstance().getTime());
            nuevoTramiteValor.setTrvAccion(0);
            nuevoTramiteValor.setTrvIva(BigDecimal.ZERO);
            nuevoTramiteValor.setTrvCantidad(BigDecimal.ZERO);
            nuevoTramiteValor.setTtrvPorIva(institucionServicio.porcentajeIva());
            nuevoTramiteValor.setTienePropHija(false);

            Boolean esValorRepetido = false;
            for (TramiteValor tramiteValor : listaTramiteValor) {
                if (tramiteValor.getTrvNumCatastro().trim().toUpperCase().equals(nuevoTramiteValor.getTrvNumCatastro().trim().toUpperCase())) {
                    esValorRepetido = true;
                    JsfUtil.addErrorMessage("Error al guardar. Número de Catastro repetido");
                    break;
                }
                if (tramiteValor.getTraNumPredio().trim().toUpperCase().equals(nuevoTramiteValor.getTraNumPredio().trim().toUpperCase())) {
                    esValorRepetido = true;
                    JsfUtil.addErrorMessage("Error al guardar. Número de Predio repetido");
                    break;
                }
            }

            /**
             * ************************
             */
            /* VALIDAR SI ENCUENTRA UNA PROPIEDAD CON PREDIO Y CATASTRO INGRESADOS Y 
        QUE ÉSTA PERTENESCA A UNO DE LOS COMPARECIENTES*/
            Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
            try {
//            Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();
                listaTramDetComparecientes.clear();
                RepertorioUsuario repertorioUsuarioSelec = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
                        repertorioSeleccionado.getRepNumero(), DataManagerSesion.getUsuarioLogeado().getUsuId(), "MPH");
                listaTramDetComparecientes = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "N");

                esPropietario = false;
                if (tramiteSelec.getTraClase().equals("J")) {
                    Propiedad propiedad = new Propiedad();
                    propiedad = propiedadServicio.buscarPropiedadPor_predio_o_catastro(nuevoTramiteValor.getTraNumPredio(), nuevoTramiteValor.getTrvNumCatastro());
                    if (propiedad != null) {
                        List<Propietario> listaPropietario = new ArrayList<>();
                        listaPropietario.clear();
                        for (TramiteDetalle listaTramDetCompareciente : listaTramDetComparecientes) {
                            List<Propietario> listaPro = new ArrayList<>();
                            if (listaTramDetCompareciente.getTtcId().getTtcPropietario().trim().equals("N")) {
                                listaPro = propietarioServicio.buscarPor_Id_Persona_Por_Matricula(listaTramDetCompareciente.getPerId().getPerId(), propiedad.getPrdMatricula());
                            }
                            listaPropietario.addAll(listaPro);
                        }
                        if (!listaPropietario.isEmpty()) {
                            esPropietario = true;
                        }

                    }
                } else if (!esValorRepetido) {
                    servicioTramiteValor.create(nuevoTramiteValor);
                    listaTramiteValor.add(nuevoTramiteValor);
                    JsfUtil.addSuccessMessage("Ingresado");

                }

            } catch (Exception e) {
            }

            /**
             * ****************************************************
             */
            if (!esValorRepetido && esPropietario == true && tramiteSelec.getTraClase().equals("J")) {
                servicioTramiteValor.create(nuevoTramiteValor);
                listaTramiteValor.add(nuevoTramiteValor);
                JsfUtil.addSuccessMessage("Ingresado");
            } else if (esPropietario == false && tramiteSelec.getTraClase().equals("J")) {
                JsfUtil.addErrorMessage("Propiedad no pertenece a ningún compareciente");
            }
            nuevoTramiteValor = new TramiteValor();

            if (listaTramiteValor.isEmpty() && tramiteSelec.getTraClase().equals("J")) {
                disabledBtnTerminar = false;
                renderedBtnTerminar = true;
            } else {
                renderedBtnTerminar = false;
            }
        } else {
            JsfUtil.addErrorMessage("Trámite Valor no creado, campo Catastro vacío.");
        }

    }

    public void borrarTramiteValor() {
        //elimino de forma logica----
        tramiteValorSeleccionado.setTrvEstado("I");
        servicioTramiteValor.edit(tramiteValorSeleccionado);
        //--------------
        //elimino de la lista
        listaTramiteValor.remove(tramiteValorSeleccionado);
        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
        if (listaTramiteValor.isEmpty() && tramiteSelec.getTraClase().equals("J")) {
            disabledBtnTerminar = false;
            renderedBtnTerminar = true;
        } else {
            renderedBtnTerminar = false;
        }
    }

    public void seleccionarTramiteValor() {
        if (!tramiteValorSeleccionado.getTrvNumCatastro().trim().isEmpty()) {
            propiedadNueva = new Propiedad();

            actualizarTramiteValor();
            esPropiedadNueva = true;
            try {
                inicializarPropiedad();
            } catch (ServicioExcepcion ex) {
                Logger.getLogger(ControladorNuevasMatriculasPH.class.getName()).log(Level.SEVERE, null, ex);
            }

            for (TramiteValor traVal : listaTramiteValor) {
                if (tramiteValorSeleccionado != traVal) {
                    traVal.setTrvPrincipal(false);

                }
            }
            renderedPanelDeclaratoria = true;
            renderedPanelRevocatoria = false;
            mostrarTabView = true;
            cargarPropiedadDetalle();
        } else {
            JsfUtil.addErrorMessage("Ingrese el Catrastro en el Trámite Valor seleccionado");
        }
    }

    public void actualizarTramiteValor() {
        //actualizo tramite valor seleccionado
        //busco el objeto parroquia para almacenar el id correspondiente
        Parroquia parroquiaSelec = new Parroquia();
        for (Parroquia parroquia : listaParroquiaTramValor) {
            if (parroquia.getParNombre().equals(tramiteValorSeleccionado.getTrvParNombre())) {
                parroquiaSelec = parroquia;
            }
        }
        tramiteValorSeleccionado.setTrvParId(parroquiaSelec.getParId());
        // poner mayusculas y setear catastro o predio en caso de alguno de estos no se haya ingresado
        if (!tramiteValorSeleccionado.getTrvNumCatastro().trim().isEmpty() && (tramiteValorSeleccionado.getTraNumPredio().trim().isEmpty() || tramiteValorSeleccionado.getTraNumPredio() == null)) {
            tramiteValorSeleccionado.setTrvNumCatastro(tramiteValorSeleccionado.getTrvNumCatastro().trim().toUpperCase());
            tramiteValorSeleccionado.setTraNumPredio(tramiteValorSeleccionado.getTrvNumCatastro());
        } else if (!tramiteValorSeleccionado.getTraNumPredio().trim().isEmpty() && (tramiteValorSeleccionado.getTrvNumCatastro().trim().isEmpty() || tramiteValorSeleccionado.getTrvNumCatastro() == null)) {
            tramiteValorSeleccionado.setTraNumPredio(tramiteValorSeleccionado.getTraNumPredio().trim().toUpperCase());
            tramiteValorSeleccionado.setTrvNumCatastro(tramiteValorSeleccionado.getTraNumPredio());
        }
        /**
         * ***
         */
        catastro = tramiteValorSeleccionado.getTrvNumCatastro();
        predio = tramiteValorSeleccionado.getTraNumPredio();

//        Integer aux = 0;
//        aux = tramiteValorSeleccionado.getTrvAccion();
//        if (aux == null) {
//            tramiteValorSeleccionado.setTrvAccion(1);
//        } else {
//            tramiteValorSeleccionado.setTrvAccion(aux + 1);
//        }
        servicioTramiteValor.edit(tramiteValorSeleccionado);
    }

    public void obtenertipoParroquia() {
        setTipoPropiedadSeleccionada(NuevasMatriculasServicio.ObtenerTipoPropiedad(idTipoParroquia));
    }

//    public void obtenerMatriculaGenerada() throws ServicioExcepcion{
//        NuevasMatriculasServicio.obtenerSecuencia();
//        setMatricula(NuevasMatriculasServicio.obtenerSecuencia().getSecActual());
//        cargarDatos();
//    }
    public void cargarCombos() {
        try {
            //consulto propiedad para editar//
//            setPropiedadNueva(NuevasMatriculasServicio.obtenerPropiedad(getCatastro(), getPredio()));
//            tramiteDetalleSelec = servicioTramiteDetalle.buscarPorNumRepertorio(superficie);
            setListaTipoPropiedad(NuevasMatriculasServicio.listaTipoPropiedad());
            //ORDEN A LISTA
            Collections.sort(listaTipoPropiedad, new Comparator<TipoPropiedad>() {

                public int compare(TipoPropiedad o1, TipoPropiedad o2) {

                    return o1.getTpdNombre().compareTo(o2.getTpdNombre());
                }
            });

            setListaUnidMedida(NuevasMatriculasServicio.listarUnidadMedidad());
            listaGrupo = NuevasMatriculasServicio.listarTipoPropiedadClase();
//            setListaLindero(NuevasMatriculasServicio.listarLindero());
//            setLinderoConsultado(NuevasMatriculasServicio.obtenerLinderoPorMatricula(getMatricula().intValue()));

            /**
             * **** cargar lista parroquia para tramite valor y propiedad*****
             */
            setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
            setListaParroquia(NuevasMatriculasServicio.listaParroquia());

            /**
             * ******************
             */
            listaTipoTramite = NuevasMatriculasServicio.listarTipoTramite();
//            setEstadoPropiedad(getPropiedadNueva().getPrdEstadoPropiedad());
            listaCompania = companiaServicio.listarTodo();
        } catch (Exception e) {
            System.out.print("FALLO AL CONSULTAR");
        }
    }

    public void inicializarPropiedad() throws ServicioExcepcion {
        Propiedad propiedadCargada = null;
        catastro = tramiteValorSeleccionado.getTrvNumCatastro().toString();
        predio = tramiteValorSeleccionado.getTraNumPredio().toString();
        try {
            propiedadCargada = NuevasMatriculasServicio.buscarPropiedadConCatastroPredio(catastro, predio);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
//SI NO ENCONTRÓ LA PROPIEDAD
        if (propiedadCargada == null) {

            //-- campos de Tramite Valor
            getPropiedadNueva().setPrdCatastro(catastro);
            getPropiedadNueva().setPrdPredio(predio);
            getPropiedadNueva().setPrdTdtParIdH(tramiteValorSeleccionado.getTrvParId());
            getPropiedadNueva().setPrdTdtParNombreH(tramiteValorSeleccionado.getTrvParNombre());
            getPropiedadNueva().setPrdTdtParId(tramiteValorSeleccionado.getTrvParId());
            getPropiedadNueva().setPrdTdtParNombre(tramiteValorSeleccionado.getTrvParNombre());
            propiedadNueva.setPrdEstadoPropiedad("AC");
            propiedadNueva.setPrdEspacial("0");
            propiedadNueva.setPrdClasePropiedad("H");
            try {
                propiedadNueva.setUmdId(servicioUnidadMedida.buscarUnidadMedidadPorAbreviatura("M2"));
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
            //--------//

//            getPropiedadNueva().setPrdTdtParId(getListaParroquia().get(0).getParId());
//            getPropiedadNueva().setPrdTdtParNombre(getListaParroquia().get(0).getParNombre());
//
//            getPropiedadNueva().setTpdId(getListaTipoPropiedad().get(0));
//            getPropiedadNueva().setPrdSuperficie(BigDecimal.ONE);
//            getPropiedadNueva().setUmdId(getListaUnidMedida().get(0));
//            getPropiedadNueva().setPrdEspacial("0");
//            getPropiedadNueva().setPrdEstadoPropiedad("AC");
//            getPropiedadNueva().setPrdClasePropiedad("H");
            //JsfUtil.addSuccessMessage("Propiedad Nueva");
            actualizarMatricula();
//ejecutando metodos ajax
            seleccionarParroquiaHistorica();
            llenarDescripcion1();
            llenarDescripcion2();
            esPropiedadNueva = true;
            listaLinderos.clear();

        } else {
            propiedadNueva = propiedadCargada;
            matricula = propiedadNueva.getPrdMatricula();
            esPropiedadNueva = false;
            //JsfUtil.addSuccessMessage("Propiedad Cargada para actualizar");

            try {
                listaLinderos.clear();
                listaLinderos = servicioLindero.listarPorNumMatricula(getPropiedadNueva().getPrdMatricula());

            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }

        }

    }

    public void llenarDescripcion1() {
        String texto1 = "";
        String texto2 = "";
        try {
            texto1 = (TransformadorNumerosLetrasUtil.transformador(getPropiedadNueva().getPrdSuperficie().toString())).toString();
            texto2 = getPropiedadNueva().getUmdId().getUmdNombre();
        } catch (Exception e) {
        }
        getPropiedadNueva().setPrdDescripcion1(texto1 + texto2);
//        System.out.println("desc1: " + getPropiedadNueva().getPrdDescripcion1());
    }

    public void seleccionarParroquiaActual() {

        Parroquia parroquiaActual = servicioParroquia.find(new Parroquia(), getPropiedadNueva().getPrdTdtParId());
        getPropiedadNueva().setPrdTdtParNombre(parroquiaActual.getParNombre());

    }

    public void llenarDescripcion2() {

        String text = "";
        String tipoPropiedad = "";
        String parroquia = "";
        try {
            if (esPropiedadNueva == true) {

                propiedadNueva.setPrdTdtParIdH(propiedadNueva.getPrdTdtParId());
                propiedadNueva.setPrdTdtParNombreH(propiedadNueva.getPrdTdtParNombre());
            }
            if (getPropiedadNueva().getTpdId() != null) {
                tipoPropiedad = getPropiedadNueva().getTpdId().getTpdNombre().trim();
                parroquia = getPropiedadNueva().getPrdTdtParNombre().trim();
                text = tipoPropiedad + " " + getPropiedadNueva().getPrdUnidadVivienda() + " situado en la Parroquia " + parroquia + " de este Cantón";
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        getPropiedadNueva().setPrdDescripcion2(text);
    }

    public void llenarDescripcion2PropHija() {

        String text = "";
        String tipoPropiedad = "";
        String parroquia = "";
        try {
//            if(esPropiedadNueva == true){                
//                propiedadNueva.setPrdTdtParIdH(propiedadNueva.getPrdTdtParId());
//                propiedadNueva.setPrdTdtParNombreH(propiedadNueva.getPrdTdtParNombre());
//            }
            if (getPropiedadNueva().getTpdId() != null) {
                tipoPropiedad = getPropiedadNueva().getTpdId().getTpdNombre().trim();
                parroquia = getPropiedadNueva().getPrdTdtParNombre().trim();
                text = tipoPropiedad + " " + getPropiedadNueva().getPrdUnidadVivienda() + " situado en la Parroquia " + parroquia + " de este Cantón";
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        getPropiedadNueva().setPrdDescripcion2(text);
    }

    public void seleccionarClase() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.seleccionarClase()");
        System.out.println("" + propiedadNueva.getPrdClasePropiedad());
        if (propiedadNueva.getPrdClasePropiedad().equals("H")) {
            mostrarComboGrupo = true;
        } else {
            mostrarComboGrupo = false;
        }

    }

    public void preCrearLindero() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.preCrearLindero()");
        linderoSeleccionado = new Lindero();
        linderoHSeleccionado = new Lindero();
    }

    public void crearLindero() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.crearLindero()");
        FacesContext context = FacesContext.getCurrentInstance();

        if (listaLinderos.size() < 4) {
            listaLinderos.add(linderoSeleccionado);
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Solo puede agregar 4 linderos", ""));
        }
        linderoSeleccionado = new Lindero();
    }

    public void borrarLindero() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.borrarLinero()");
        System.out.println(linderoSeleccionado.getLdrNombre());
        Iterator<Lindero> iterador = listaLinderos.iterator();
        while (iterador.hasNext()) {
            Lindero next = iterador.next();
            if (next.getLdrNombre().equals(linderoSeleccionado.getLdrNombre())) {
                iterador.remove();
                break;
            }
        }
        if (linderoSeleccionado.getLdrId() != null) {
            servicioLindero.remove(linderoSeleccionado);
        }
        linderoSeleccionado = new Lindero();
    }

    public void marcarComoRevisado() {
        Integer aux = 0;
        aux = tramiteValorSeleccionado.getTrvAccion();
        if (aux == null) {
            tramiteValorSeleccionado.setTrvAccion(1);
        } else {
            tramiteValorSeleccionado.setTrvAccion(aux + 1);
        }
        servicioTramiteValor.edit(tramiteValorSeleccionado);
    }

    public void actualizarEstProcRepUsu() {
        try {
            Boolean todosRevisados = false;
            Integer contador = 0;
            for (TramiteValor trmVal : listaTramiteValor) {
                if (trmVal.getTrvAccion() != null) {
                    if (trmVal.getTrvAccion() != null) {
                        if (trmVal.getTrvAccion() > 0) {
                            contador++;
                        }
                    }

                }

            }
            if (contador == listaTramiteValor.size()) {
                todosRevisados = true;
            }
            if (todosRevisados) {
                RepertorioUsuario repertorioUsuarioSeleccionado = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
                        repertorioSeleccionado.getRepNumero(), DataManagerSesion.getUsuarioLogeado().getUsuId(), "MPH");
                repertorioUsuarioSeleccionado.setRpuEstadoProceso("TERMINADO");
                servicioRepertorioUsuario.edit(repertorioUsuarioSeleccionado);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void guardarPropiedad() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.guardarPropiedad()");
        FacesContext context = FacesContext.getCurrentInstance();

        marcarComoRevisado();

        actualizarEstProcRepUsu();

        try {

            //obtengo la secuencia
            secuenciaControlador.generarSecuenciaMatricula("MAT");
            //obtengo el siguiente valor de la secuencia
            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
            int auxSecuencia = getSecuencia().getSecActual();
            getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
            //Long idGenerado = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
//        setMatricula(idGenerado);
            //secuenciaServicio.guardar(getSecuencia());

            Compania compania = companiaServicio.buscarPorIdCompania(propiedadNueva.getPrdConjuntoId());
            propiedadNueva.setPrdConjunto(compania.getComNombre());
            propiedadNueva.setPrdAgregado("A");
            propiedadNueva.setPrdPH("S");

            if (esPropiedadNueva) {
                if (!propiedadNueva.getPrdDescripcion2().trim().isEmpty()) {
                    if (listaLinderos.size() >= 2) {
                        try {
                            //propiedadNueva.setPrdMatricula(idGenerado.intValue());
                            propiedadNueva.setPrdUserCreador(" ");
                            //propiedadNueva.setPrdClasePropiedad(" ");
                            propiedadNueva.setPrdEstadoRegistro("A");
                            propiedadNueva.setPrdSector(" ");
                            propiedadNueva.setPrdFHM(Calendar.getInstance().getTime());
                            propiedadNueva.setPrdFHR(Calendar.getInstance().getTime());
                            propiedadNueva.setPrdUserModificacion(" ");
                            propiedadNueva.setPrdUbicacion(" ");
                            propiedadNueva.setPrdTdtParIdH(propiedadNueva.getPrdTdtParId());
                            propiedadNueva.setPrdTdtParNombreH(propiedadNueva.getPrdTdtParNombre());

                            TipoPropiedadClase tipoPropiedadClasePorDefecto;
                            tipoPropiedadClasePorDefecto = servicioTipoPropiedadClase.find(new TipoPropiedadClase(), "NIN");
                            propiedadNueva.setTclId(tipoPropiedadClasePorDefecto);

                            NuevasMatriculasServicio.create(propiedadNueva);
                            
                            tramiteValorSeleccionado.setTrvParId(propiedadNueva.getPrdTdtParId());
                            tramiteValorSeleccionado.setTrvParNombre(propiedadNueva.getPrdTdtParNombre());
                            servicioTramiteValor.edit(tramiteValorSeleccionado);
                            context.addMessage(null, new FacesMessage("Propiedad Creada", ""));
                        } catch (Exception e) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad no creada", ""));

                        }

                        try {
                            for (Lindero lindero : listaLinderos) {
                                lindero.setLdrFHR(Calendar.getInstance().getTime());
                                lindero.setLdrUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                lindero.setPrdMatricula(propiedadNueva);
                                servicioLindero.create(lindero);
                            }
                            context.addMessage(null, new FacesMessage("Linderos agregados", ""));
                        } catch (Exception e) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Linderos no creados ", ""));
                        }

                        tramiteUtil.insertarTramiteAccion(repertorioSeleccionado.getTraNumero(),
                                propiedadNueva.getPrdMatricula().toString(),
                                "Propiedad creada con matricula "
                                + propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
                                + " y predio " + propiedadNueva.getPrdPredio(),
                                "MPH",
                                repertorioSeleccionado.getTraNumero().getTraEstadoRegistro(),
                                null);

                        indiceTab = 1;
                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La propiedad debe tener mínimo 2 linderos.", ""));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Descripción de la Propiedad", ""));
                }

            } else {
                if (!propiedadNueva.getPrdDescripcion2().trim().isEmpty()) {
                    propiedadNueva.setTpdId(propiedadNueva.getTpdId());
                    if (listaLinderos.size() >= 2) {
                        NuevasMatriculasServicio.edit(propiedadNueva);
                        
                        tramiteValorSeleccionado.setTrvParId(propiedadNueva.getPrdTdtParId());
                        tramiteValorSeleccionado.setTrvParNombre(propiedadNueva.getPrdTdtParNombre());
                        servicioTramiteValor.edit(tramiteValorSeleccionado);
                        //context.addMessage(null, new FacesMessage("Propiedad Actualizada", ""));
                        tramiteUtil.insertarTramiteAccion(repertorioSeleccionado.getTraNumero(),
                                propiedadNueva.getPrdMatricula().toString(),
                                "Propiedad actualizada con matricula "
                                + propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
                                + " y predio " + propiedadNueva.getPrdPredio(),
                                repertorioSeleccionado.getTraNumero().getTraEstado(),
                                repertorioSeleccionado.getTraNumero().getTraEstadoRegistro(),
                                null);

                        for (Lindero lindero : listaLinderos) {
                            lindero.setLdrFHR(Calendar.getInstance().getTime());
                            lindero.setLdrUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            lindero.setPrdMatricula(propiedadNueva);
                            servicioLindero.edit(lindero);
                        }
                        //context.addMessage(null, new FacesMessage("Linderos Actualizados", ""));
                        indiceTab = 1;
                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La propiedad debe tener mínimo 2 linderos.", ""));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Descripción de la Propiedad", ""));
                }

            }
            try {
                propiedadHijaSelec = new Propiedad();
                listaTmpAlicuotasSelec.clear();
                listaPropiedadHija = propiedadServicio.listarPorMatriculaPadre(propiedadNueva.getPrdMatricula());
                listaPropiedadHijaUV = propiedadServicio.listarPorMatriculaPadre_PrdAgregado(propiedadNueva.getPrdMatricula());
                listaTmpAlicuotas = tmpAlicuotaDao.listarPorMatriculaPadre_SinEstadoTP(propiedadNueva.getPrdMatricula());
                listaTmpAlicuotasUV = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoN_EstadoA(propiedadNueva.getPrdMatricula());
                listaBloque = bloqueDao.listarPorMatricula(propiedadNueva.getPrdMatricula());
                cargarUnidadVivienda();
                //ACTUALIZAR TAB GENERAL
                listaAlicuotaGral.clear();
                List<Alicuota> listAlic = new ArrayList<>();
                for (Alicuota alicuota1 : listaAlicuotasUV) {
                    listAlic.clear();
                    listAlic = alicuotaServicio.listarPorMatricula(alicuota1.getPrdMatricula().getPrdMatricula());
                    for (Alicuota alicuota2 : listAlic) {
                        if (!listaAlicuotaGral.contains(alicuota2)) {
                            listaAlicuotaGral.add(alicuota2);
                        }
                    }

                }
            } catch (Exception e) {
                System.out.println(e);
            }

        } catch (ServicioExcepcion e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad No Actualizada", "");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
        try {
            guardarRepertorioPropiedad();

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        //mostrarTabView = false;
        disabledTab = false;
        disabledTabUndViv = true;
        propiedadPadreMostrar = propiedadServicio.obtenerPorMatricula(matricula);
    }

    public void guardarRepertorioPropiedad() {
        //GUARDANDO REPERTORIO PROPIEDAD
        try {
            if (!servicioRepertorioPropiedad.existeRepertorioPropiedad(repertorioSeleccionado.getRepNumero(), propiedadNueva.getPrdMatricula())) {
                RepertorioPropiedad nuevoRepPro = new RepertorioPropiedad();
                nuevoRepPro.setPrdMatricula(propiedadNueva);
                nuevoRepPro.setRepNumero(repertorioSeleccionado);
                nuevoRepPro.setRpdFHR(Calendar.getInstance().getTime());
                nuevoRepPro.setRpdUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                servicioRepertorioPropiedad.create(nuevoRepPro);
                tramiteUtil.insertarTramiteAccion(nuevoRepPro.getRepNumero().getTraNumero(),
                        nuevoRepPro.getRepNumero().getRepNumero().toString(),
                        "Asignado Repertorio (id Repertorio Propiedad): " + nuevoRepPro.getRpdId() + " a la Propiedad con matricula "
                        + propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
                        + " y predio " + propiedadNueva.getPrdPredio(),
                        nuevoRepPro.getRepNumero().getTraNumero().getTraEstado(),
                        nuevoRepPro.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                        null);
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

//    public void preBuscar() {
//        numDocumento = null;
//        boolDocEncontrado = false;
//    }
    public void buscar() {
        propiedadSelec = new Propiedad();

        try {
            propiedadSelec = NuevasMatriculasServicio.listarPorNumCatastroPredioMatricula(numDocumento);
            JsfUtil.addSuccessMessage("Documento cargado.");
            boolDocEncontrado = true;

        } catch (Exception e) {
            JsfUtil.addErrorMessage("No se encontró documento");
            boolDocEncontrado = false;
        }

    }

    public void seleccionarParroquiaHistorica() {

        Parroquia parroquiaHistorica = servicioParroquia.find(new Parroquia(), getPropiedadNueva().getPrdTdtParIdH());
        getPropiedadNueva().setPrdTdtParNombreH(parroquiaHistorica.getParNombre());

        getPropiedadNueva().setPrdTdtParNombre(parroquiaHistorica.getParNombre());
        getPropiedadNueva().setPrdTdtParId(parroquiaHistorica.getParId());
        llenarDescripcion2();

    }

    //******************** DERECHOS Y ACCIONES *****************
//    public void generarDescripcion() {
//        try {
//            propiedadDetalleSeleccionada.setPdtDescripcion(TransformadorNumerosLetrasUtil.transformador(propiedadDetalleSeleccionada.getPdtValor().toString())
//                    + "POR CIENTO DE ACCIONES Y DERECHOS SOBRE " + propiedadNueva.getTpdId().getTpdNombre().trim()
//                    + " UBICADO EN "
//                    + propiedadNueva.getPrdTdtParNombre() + " DE ESTE CANTON");
//        } catch (Exception e) {
//            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//        }
//
//    }
    public void cargarDatosPersona() {
        personaSeleccionada = new Persona();
        try {
            if (personaSeleccionada != null) {
                personaSeleccionada = personaUtil.obtenerDatosPersona(numIdentificacionPersona);
                //JsfUtil.addSuccessMessage("Persona encontrada");
            } else {
                JsfUtil.addErrorMessage("Identificación incorrecta o no válida");
            }

        } catch (ServicioExcepcion ex) {
            JsfUtil.addErrorMessage("Ocurrió un error");
            Logger.getLogger(ControladorNuevasMatriculasPH.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void preCrearDetallePropiedad() {
//        propiedadDetalleSeleccionada = new PropiedadDetalle();
//        numIdentificacionPersona = "";
//         
//        try {
//            Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
//            listaTramDetComparecientes.clear();
//            RepertorioUsuario repertorioUsuarioSelec = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
//                        repertorioSeleccionado.getRepNumero(), DataManagerSesion.getUsuarioLogeado().getUsuId(), "MAT");
//            listaTramDetComparecientes = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "N");
//            
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//            
//    }
//    public void crearDetallePropiedad() {
//        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.crearDetallePropiedad()");
//
//        calcularTotalAcciones();
//        BigDecimal valorAdicional = new BigDecimal(0);
//        valorAdicional = propiedadDetalleSeleccionada.getPdtValor();
//        BigDecimal bigDec100 = new BigDecimal(100);
//        
//        if ((totalAcc.add(valorAdicional)).compareTo(bigDec100) <= 0 ) {  //if (totalAcc + valorAdicional <= 100) {
//            //propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(personaSeleccionada.getPerId()));
//            //BigInteger idusuario = BigInteger.valueOf(personaSeleccionada.getPerId());
//            if(tramiteDetalleSeleccionado != null){
//                propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(tramiteDetalleSeleccionado.getPerId().getPerId()));
//            BigInteger idusuario = BigInteger.valueOf(tramiteDetalleSeleccionado.getPerId().getPerId());
//            Persona persona = personaServicio.find(new Persona(), idusuario.longValue());
//            propiedadDetalleSeleccionada.setPersona(persona);
//            propiedadDetalleSeleccionada.setPdtEstado("A");
//            listaPropiedadDetalle.add(propiedadDetalleSeleccionada);
//            calcularTotalAcciones();
//            }
//            else{
//                JsfUtil.addErrorMessage("Debe seleccionar una persona");
//            }
//            
//
//        } else {
//            JsfUtil.addErrorMessage("No puede sobrepasar el 100% (total Acciones: " + totalAcc + ")");
//        }
//        preCrearDetallePropiedad();
//
//    }
//    public void calcularTotalAcciones() {
//        try {
//            totalAcc = new BigDecimal(0);
//        for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
//            System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.calcularTotalAcciones()");
//            if(propiedadDetalle.getPdtEstado().equals("A")){
//                totalAcc = totalAcc.add(propiedadDetalle.getPdtValor());//totalAcc += propiedadDetalle.getPdtValor();                                   
//                
//            }
//            
//        }
//        BigDecimal bigDec100 = new BigDecimal(100);
//        if(totalAcc.compareTo(bigDec100) == 0){
//            disabledBtnGuardarDerAcc = false;
//            disabledBtnAgregarDerAcc = true;
//        }
//        else{
//            disabledBtnGuardarDerAcc = true;
//            disabledBtnAgregarDerAcc = false;
//        }
//            
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//        
//
//    }
//    public void borrarDetallePropiedad(int row) {
//        propiedadDetElim.setPdtEstado("I");
//        listaPropiedadDetalle.set(row, propiedadDetElim);
//        listaPropDetActualizarEstado.add(propiedadDetElim);
//        listaPropiedadDetalle.remove(row);
//        calcularTotalAcciones();
//    }
//    public void guardarDerAcc() throws ServicioExcepcion {
//        
//        calcularTotalAcciones();
//        
//        BigDecimal bigDec100 = new BigDecimal(100);
//        if (totalAcc.compareTo(bigDec100) == 0) {   //if (totalAcc == 100) {
//            for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
//                if(propiedadDetalle.getPdtEstado().equals("A") ){                    
//                    
//                propiedadDetalle.setPdtTipo("GDA");
//                propiedadDetalle.setPdtUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
//                propiedadDetalle.setPdtUmdId(BigInteger.valueOf(propiedadNueva.getUmdId().getUmdId()));
//                propiedadDetalle.setPdtUmdAbreviatura(propiedadNueva.getUmdId().getUmdAbreviatura());
//                propiedadDetalle.setPdtFHR(Calendar.getInstance().getTime());
//                propiedadDetalle.setPrdMatricula(propiedadNueva);
//                propiedadDetalle.setPdtEstado("A");
//                servicioPropiedadDetalle.guardar(propiedadDetalle);
//                }
//                else{
//                  
//                    servicioPropiedadDetalle.edit(propiedadDetalle);
//                }
//            }
//            for (PropiedadDetalle propDetalle : listaPropDetActualizarEstado) {
//                servicioPropiedadDetalle.edit(propDetalle);
//            }
//            disabledBtnGuardarDerAcc = true;
//            disabledBtnAgregarDerAcc = true;
//            guardarRepertorioPropiedad();
//            JsfUtil.addSuccessMessage("Derechos y Acciones guardado");
//        } else {
//            JsfUtil.addErrorMessage("Debe llenar el 100%");
//        }
//    }
    public void cargarPropiedadDetalle() {
        try {
            listaPropiedadDetalle.clear();
            List<PropiedadDetalle> resultado = new ArrayList<>();
            resultado = servicioPropiedadDetalle.listar_Por_Propiedad_Tipo_Estado(propiedadNueva.getPrdMatricula(), "GDA");

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

    //******** UNIFICACIONES *****
    public void seleccionarPropiedadDeUnificacion(TramiteValor tramValParam) {
//        System.out.println("num fila: " + numFila);
//        for (TramiteValor tramVal : listaTramiteValor) {
//            System.out.println("indice: " + listaTramiteValor.indexOf(tramVal));
//
//            if (listaTramiteValor.indexOf(tramVal) != numFila) {
//                tramVal.setTrvPrincipal(false);
//            }
//        }
        listaPropiedadesUnf = new ArrayList<>();
//        if (tramiteValorSeleccionado.getTrvAccion() <= 1)//si no ha sido revisado, no puede ser seleccionado
//        {
        tramiteValorSeleccionado.setTrvPrincipal(false);
//        } else {
        for (TramiteValor tramVal : listaTramiteValor) {
            if (tramVal != tramValParam) {
                tramVal.setTrvPrincipal(false);//solo uno puede seleccionarse, el resto como falso.
            }
            servicioTramiteValor.edit(tramVal);//actualizo todos los tramites valor
        }
//        }

    }

//    public void preBuscarPropiedadUnf() {
//        numDocumento = null;
//        boolDocEncontrado = false;
//        listaTramiteValorUnf = new ArrayList<>();
//        for (TramiteValor tramiteValor : listaTramiteValor) {
//            if (tramiteValor.getTrvAccion() != null) {
//                if (tramiteValor.getTrvAccion() >= 1) //si ya fue revisado
//                {
//                    if (!tramiteValor.getTrvPrincipal()) { //si no esta seleccionado como propiedad principal de unificacion
//                        if (!listaTramiteValorUnf.contains(tramiteValor)) {//si no está ya agregado, agrego
//                            listaTramiteValorUnf.add(tramiteValor);
//                        }
//                    }
//
//                }
//
//            }
//        }
//    }
    public void buscarPropiedadUnf(TramiteValor traval) {
        propiedadSeleccionadaUnf = new Propiedad();
        try {
            propiedadSeleccionadaUnf = NuevasMatriculasServicio.buscarPropiedadConCatastroPredio(
                    traval.getTrvNumCatastro().toString(), traval.getTraNumPredio().toString());
            if (!listaPropiedadesUnf.contains(propiedadSeleccionadaUnf)) {
                listaPropiedadesUnf.add(propiedadSeleccionadaUnf);
                JsfUtil.addSuccessMessage("Propiedad agregada.");
            }
//            propiedadSeleccionadaUnf = NuevasMatriculasServicio.listarPorNumCatastroPredioMatricula(numDocumento);
//            boolDocEncontrado = true;

        } catch (Exception e) {
            e.printStackTrace();
//            JsfUtil.addErrorMessage("No se encontró documento");
//            boolDocEncontrado = false;
        }

//        propiedadSeleccionadaUnf = new Propiedad();
//
//        try {
//            propiedadSeleccionadaUnf = NuevasMatriculasServicio.listarPorNumCatastroPredioMatricula(numDocumento);
//            JsfUtil.addSuccessMessage("Documento encontrado.");
//            boolDocEncontrado = true;
//
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage("No se encontró documento");
//            boolDocEncontrado = false;
//        }
    }

//    public void agregarPropiedadListaUnf() {
//        if (!listaPropiedadesUnf.contains(propiedadSeleccionadaUnf)) {
//            listaPropiedadesUnf.add(propiedadSeleccionadaUnf);
//        } else {
//            JsfUtil.addErrorMessage("Las propiedades no se pueden repetir");
//        }
//    }
//    public void eliminarPropiedadListaUnf() {
//        listaPropiedadesUnf.remove(propiedadSeleccionadaUnf);
//    }
//    public void guardarPropiedadUnf() {
//
//        try {
//            FacesContext context = FacesContext.getCurrentInstance();
//
//            //obtengo la secuencia
//            secuenciaControlador.generarSecuencia("MAT");
//            //obtengo el siguiente valor de la secuencia
//            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
//            int auxSecuencia = getSecuencia().getSecActual();
//            getSecuencia().setSecActual(auxSecuencia + 1);
////                System.out.print("Secuencia-- " + auxSecuencia);
//            Long idGenerado = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
////        setMatricula(idGenerado);
//            secuenciaServicio.guardar(getSecuencia());
//
//            try {
//                propiedadNueva.setPrdMatricula(idGenerado.intValue());
//                propiedadNueva.setPrdUserCreador(" ");
//                propiedadNueva.setPrdClasePropiedad(" ");
//                propiedadNueva.setPrdEstadoRegistro(" ");
//                propiedadNueva.setPrdSector(" ");
//                propiedadNueva.setPrdTdtParIdH(1L);
//                propiedadNueva.setPrdFHM(Calendar.getInstance().getTime());
//                propiedadNueva.setPrdFHR(Calendar.getInstance().getTime());
//                propiedadNueva.setPrdUserModificacion(" ");
//                propiedadNueva.setPrdUbicacion(" ");
//                getPropiedadNueva().setPrdTdtParId(getListaParroquia().get(0).getParId());
//
//                NuevasMatriculasServicio.create(propiedadNueva);
//                context.addMessage(null, new FacesMessage("Propiedad Creada", ""));
//            } catch (Exception e) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad no creada", ""));
//
//            }
//
//        } catch (Exception e) {
//            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Propiedad No Actualizada", "");
//            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
//        }
//
//    }
//    public void guardarUnificaciones() {
//        try {
//            if (listaPropiedadesUnf.size() >= 2) {
//                guardarPropiedad();
//                for (Propiedad propiedad : listaPropiedadesUnf) {
//                    propiedad.setPrdEstadoPropiedad("AN");
//                    NuevasMatriculasServicio.edit(propiedad);
//
//                    PropiedadDetalle propiedadDetalleNuevo = new PropiedadDetalle();
//                    propiedadDetalleNuevo.setPrdMatricula(propiedadNueva);
//                    propiedadDetalleNuevo.setPdtPrdMatricula(propiedad.getPrdMatricula());
//                    propiedadDetalleNuevo.setPdtUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
//                    propiedadDetalleNuevo.setPdtFHR(Calendar.getInstance().getTime());
//                    propiedadDetalleNuevo.setPdtTipo("UNF");
//                    propiedadDetalleNuevo.setPdtUmdId(BigInteger.valueOf(propiedadNueva.getUmdId().getUmdId()));
//                    propiedadDetalleNuevo.setPdtUmdAbreviatura(propiedadNueva.getUmdId().getUmdAbreviatura());
//                    servicioPropiedadDetalle.create(propiedadDetalleNuevo);
//                }
//                JsfUtil.addSuccessMessage("Unificaciones realizadas");
//            } else {
//                JsfUtil.addErrorMessage("Necesita mínimo 2 elementos");
//
//            }
//        } catch (Exception e) {
//            Logger.getLogger(ControladorNuevasMatriculasPH.class
//                    .getName()).log(Level.SEVERE, null, e);
//
//        }
//    }
    //DIVISIONES
    public void seleccionarPropiedadDiv() {
        try {

            propiedadSeleccionadaDiv = propiedadSelec;

            listaPropiedadesDiv = new ArrayList<>();
            JsfUtil.addSuccessMessage("Propiedad seleccionada");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al seleccionar propiedad");

        }

    }

    public void actualizarMatricula() throws ServicioExcepcion {
        secuenciaControlador.generarSecuenciaMatricula("MAT");
        //obtengo el siguiente valor de la secuencia
        setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
        int auxSecuencia = getSecuencia().getSecActual();
        getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
        String idGenerado = secuenciaControlador.getControladorBb().getNumeroMatricula();

        //        setMatricula(idGenerado);
        secuenciaServicio.guardar(getSecuencia());

        setMatricula(idGenerado);
        propiedadNueva.setPrdMatricula(idGenerado);

    }

    public void agregarPropiedadListaDv() throws ServicioExcepcion {
        Double totalSuperficie = 0.0;

        if (propiedadSeleccionadaDiv != null) {
            if (listaLinderos.size() >= 2) {
                for (Propiedad propiedad : listaPropiedadesDiv) {
                    totalSuperficie += propiedad.getPrdSuperficie().doubleValue();
                }

                if (totalSuperficie + propiedadNueva.getPrdSuperficie().doubleValue() <= propiedadSeleccionadaDiv.getPrdSuperficie().doubleValue()) {
                    Boolean yaExistePropiedad = NuevasMatriculasServicio.existePropiedadConCatastroPredio(
                            getPropiedadNueva().getPrdCatastro(), getPropiedadNueva().getPrdPredio());
                    if (!yaExistePropiedad) {
                        propiedadNueva.setListalinderos(listaLinderos);
                        listaPropiedadesDiv.add(propiedadNueva);
                        JsfUtil.addSuccessMessage("Propiedad agregada");
                        inicializarPropiedad();
                        listaLinderos = new ArrayList<>();

                    } else {
                        JsfUtil.addErrorMessage("Ya existe una propiedad con el mismo Predio o Catastro");

                    }

                } else {
                    JsfUtil.addErrorMessage("No puede sobrepasar la longitud original");
                }
            } else {
                JsfUtil.addErrorMessage("La propiedad debe tener mínimo 2 linderos.");
            }

        } else {
            JsfUtil.addErrorMessage("Debe seleccionar una propiedad para la división");

        }

    }

    public void eliminarPropiedadListaDiv(Propiedad propiedad) {
        listaPropiedadesDiv.remove(propiedad);
    }

    public void guardarDivisiones() {
        try {
            Double totalSuperficie = 0.0;
            secuenciaControlador.generarSecuenciaMatricula("MAT");
            //obtengo el siguiente valor de la secuencia
            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
            int auxSecuencia = getSecuencia().getSecActual();
            getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
            String idGenerado = secuenciaControlador.getControladorBb().getNumeroMatricula();

            //        setMatricula(idGenerado);
            secuenciaServicio.guardar(getSecuencia());

            setMatricula(idGenerado);
            for (Propiedad propiedad : listaPropiedadesDiv) {
                totalSuperficie += propiedad.getPrdSuperficie().doubleValue();
            }
            if (totalSuperficie == propiedadSeleccionadaDiv.getPrdSuperficie().doubleValue()) {
                for (Propiedad propiedad : listaPropiedadesDiv) {
                    propiedad.setPrdMatricula(idGenerado);
                    propiedad.setPrdUserCreador(" ");
                    propiedad.setPrdClasePropiedad(" ");
                    propiedad.setPrdEstadoRegistro(" ");
                    propiedad.setPrdSector(" ");
                    propiedad.setPrdTdtParIdH(1L);
                    propiedad.setPrdFHM(Calendar.getInstance().getTime());
                    propiedad.setPrdFHR(Calendar.getInstance().getTime());
                    propiedad.setPrdUserModificacion(" ");
                    propiedad.setPrdUbicacion(" ");
                    propiedad.setPrdEstadoPropiedad("AC");
                    propiedad.setPrdPadreMatricula(propiedadSeleccionadaDiv);
                    NuevasMatriculasServicio.create(propiedad);

                    for (Lindero lindero : propiedad.getListalinderos()) {
                        lindero.setLdrFHR(Calendar.getInstance().getTime());
                        lindero.setLdrUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        lindero.setPrdMatricula(propiedad);
                        servicioLindero.create(lindero);
                    }
                }
                propiedadSeleccionadaDiv.setPrdEstadoPropiedad("AN");
                NuevasMatriculasServicio.edit(propiedadSeleccionadaDiv);
                JsfUtil.addSuccessMessage("Divisiones realizadas");
            } else {
                JsfUtil.addErrorMessage("El total: " + totalSuperficie + " no corresponde al valor "
                        + propiedadSeleccionadaDiv.getPrdSuperficie().doubleValue()
                        + " de la superficie a dividir");

            }
        } catch (Exception e) {
            Logger.getLogger(ControladorNuevasMatriculasPH.class
                    .getName()).log(Level.SEVERE, null, e);

        }
    }

//    public void terminarProcesoMatriculacion() throws ServicioExcepcion, IOException {
//
//        Usuario usuariologeado = DataManagerSesion.getUsuarioLogeado();
//        Tramite tramiteParam = repertorioSeleccionado.getTraNumero();
//        Boolean todosRevisados = false;
//        Integer contador = 0;
//        for (TramiteValor tramiteValor : listaTramiteValor) {
//            if (tramiteValor.getTrvAccion() > 0) {
//                contador++;
//            }
//        }
//        if (contador == listaTramiteValor.size()) {
//            todosRevisados = true;
//        }
//        if (todosRevisados) {
//
//            //ACTUALIZANDO REPERTORIOS USUARIO
//            int numRepUsuActualizados = 0;
//
////            numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(repertorioSeleccionado.getRepNumero(),
////                    usuariologeado.getUsuId(), "MAT");
//            numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(repertorioSeleccionado.getTraNumero().getTraNumero(),
//                    usuariologeado.getUsuId(), "MAT");
//            System.out.println("# repertorios actualizados" + numRepUsuActualizados);
//
//            //ACTUALIZANDO EL TRAMITE
//            tramiteParam.setTraEstado("INS");
//            servicioTramite.edit(tramiteParam);
//            tramiteUtil.insertarTramiteAccion(tramiteParam, tramiteParam.getTraNumero().toString(),
//                    "Actualización del estado de Tramite a 'INS'",
//                    tramiteParam.getTraEstado(), tramiteParam.getTraEstadoRegistro(), null);
//
//            try {
//                Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("INS", "Inscripcion", 1);
//                RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
//
//                nuevoRepUsu.setRpuTipo("INS");
//                nuevoRepUsu.setUsuId(usuarioAsignado);
//                nuevoRepUsu.setRpuEstado("A");
//                nuevoRepUsu.setRpuUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
//                nuevoRepUsu.setRpuFHR(Calendar.getInstance().getTime());
//                nuevoRepUsu.setRepNumero(repertorioSeleccionado);
//                servicioRepertorioUsuario.create(nuevoRepUsu);
//                tramiteUtil.insertarTramiteAccion(nuevoRepUsu.getRepNumero().getTraNumero(),
//                        nuevoRepUsu.getRepNumero().getRepNumero().toString(),
//                        "Repertorio Usuario " + nuevoRepUsu.getRepNumero().getRepNumero()
//                        + " asignado al usuario: " + usuarioAsignado.getUsuLogin(),
//                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
//                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
//                        usuarioAsignado.getUsuLogin());
//
//            } catch (Exception e) {
//                Logger.getLogger(ControladorNuevasMatriculas.class
//                        .getName()).log(Level.SEVERE, null, e);
//                JsfUtil.addErrorMessage("Error al generar nuevos repertorios usuario con estado 'INS'");
//            }
//            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/matriculacion/Matriculacion.xhtml");
//
//        } else {
//            JsfUtil.addErrorMessage("Faltan " + (listaTramiteValor.size() - contador) + " propiedades por revisar");
//        }
//    }
//        Repertorio repertorioSeleccionado = servicioRepertorio.find(new Repertorio(), getNumRepertorio());
    public void redireccionarPaginaSeleccion() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MatriculacionPH/Matriculacion.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(ControladorNuevasMatriculasPH.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void terminarProceso() throws ServicioExcepcion {
        try {
            Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
            TipoTramite tipoTramiteSelec = servicioTipoTramite.find(new TipoTramite(), new Long(repertorioSeleccionado.getRepTtrId()));
            int numRegistrosActualizados = 0;
            numRegistrosActualizados = repertorioUsuarioServicio.actualizarEstadoProcesoPor_NumRepertorio_Tipo(repertorioSeleccionado.getRepNumero(), "MAT");
            TramiteValor tramValorBuscar = new TramiteValor();
            tramValorBuscar = servicioTramiteValor.obtenerPor_NumTramite_TipoTramite_Castrato_Predio(tramiteSelec.getTraNumero(), tipoTramiteSelec.getTtrId(), "0", "0");
            if (tramValorBuscar == null) {

                TramiteValor tramValor = new TramiteValor();
                tramValor.setTtrId(tipoTramiteSelec);
                tramValor.setTraNumero(tramiteSelec);
                tramValor.setTrvValor(new BigDecimal(0));
                tramValor.setTrvEstado("A");
                tramValor.setTrvUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                tramValor.setTrvFHR(Calendar.getInstance().getTime());
                tramValor.setTrvIva(new BigDecimal(0));
                tramValor.setTrvValorBien(new BigDecimal(0));
                tramValor.setTraNumPredio("0");
                tramValor.setTrvNumCatastro("0");
                tramValor.setTtrvPorIva(new BigDecimal(0));
                tramValor.setTrvCantidad(new BigDecimal(0));
                tramValor.setTrvParId(new Long(0));
                tramValor.setTrvParNombre("");
                tramValor.setTrvAccion(0);
                servicioTramiteValor.create(tramValor);
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MatriculacionPH/Matriculacion.xhtml");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void nuevaMatriculaHija() {
        try {

            matriculalPadre = matricula;
            propiedadPadre = propiedadServicio.obtenerPorMatricula(matriculalPadre);
            //propiedadNueva = propiedadPadre;
            //obtengo la secuencia
            secuenciaControlador.generarSecuenciaMatricula("MAT");
            //obtengo el siguiente valor de la secuencia
            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
            int auxSecuencia = getSecuencia().getSecActual();
            getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
            String idGenerado = secuenciaControlador.getControladorBb().getNumeroMatricula();
//        setMatricula(idGenerado);

            //Compania compania = companiaServicio.buscarPorIdCompania(propiedadNueva.getPrdConjuntoId()); 
            Propiedad prop = new Propiedad();
            prop = propiedadServicio.buscarPorMatriculaPadre_UnidViv(propiedadPadre.getPrdMatricula());
            int numUnidViv;
            if (prop == null) {
                numUnidViv = 1;
            } else {
                if (prop.getPrdUnidadVivienda().equals("")) {
                    numUnidViv = 1;
                } else {
                    numUnidViv = Integer.valueOf(prop.getPrdUnidadVivienda()) + 1;
                }

            }

            matriculaHija = idGenerado;
            propiedadNueva.setPrdMatricula(idGenerado);
            propiedadNueva.setPrdPadreMatricula(propiedadPadre);
            propiedadNueva.setPrdCatastro("");
            propiedadNueva.setPrdPredio("");
            propiedadNueva.setPrdAgregado("I");
            propiedadNueva.setPrdSuperficie(new BigDecimal(0));
            propiedadNueva.setPrdDescripcion1("");
            propiedadNueva.setPrdUnidadVivienda(String.valueOf(numUnidViv));
            propiedadNueva.setPrdConjunto(propiedadPadre.getPrdConjunto());
            propiedadNueva.setPrdConjuntoId(propiedadPadre.getPrdConjuntoId());
            propiedadNueva.setUmdId(propiedadPadre.getUmdId());
            propiedadNueva.setTclId(propiedadPadre.getTclId());
            propiedadNueva.setPrdClasePropiedad(propiedadPadre.getPrdClasePropiedad());
            propiedadNueva.setTpdId(propiedadPadre.getTpdId());
            propiedadNueva.setPrdFHM(Calendar.getInstance().getTime());
            propiedadNueva.setPrdUserCreador(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
            propiedadNueva.setPrdDescripcion2(propiedadPadre.getPrdDescripcion2());
            propiedadNueva.setPrdTdtParId(propiedadPadre.getPrdTdtParId());
            propiedadNueva.setPrdTdtParNombre(propiedadPadre.getPrdTdtParNombre());
            propiedadNueva.setPrdTdtParIdH(propiedadPadre.getPrdTdtParIdH());
            propiedadNueva.setPrdEstadoPropiedad(propiedadPadre.getPrdEstadoPropiedad());
            propiedadNueva.setPrdTdtParNombreH(propiedadPadre.getPrdTdtParNombreH());
            propiedadNueva.setPrdLatitud(propiedadPadre.getPrdLatitud());
            propiedadNueva.setPrdLongitud(propiedadPadre.getPrdLongitud());
            propiedadNueva.setPrdUbicacion(propiedadPadre.getPrdUbicacion());
            propiedadNueva.setPrdSector(propiedadPadre.getPrdSector());
            propiedadNueva.setPrdNivel(propiedadPadre.getPrdNivel());
            propiedadNueva.setPrdNumero(propiedadPadre.getPrdNumero());
            propiedadNueva.setPrdEspacial(propiedadPadre.getPrdEspacial());
            //propiedadNueva.setPrdEstadoRegistro(propiedadPadre.getPrdEstadoRegistro());
            propiedadNueva.setPrdEstadoRegistro("A");
            propiedadNueva.setPrdFHR(Calendar.getInstance().getTime());
            propiedadNueva.setPrdArea(" ");
            propiedadNueva.setPrdAlicuota(BigDecimal.valueOf(0));
            propiedadNueva.setPrdPiso(" ");

            esPropiedadNueva = true;
            indiceTabNuevaProp = 0;
            listaLinderos.clear();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void onRowEditLindero(RowEditEvent event) {
        Lindero linder = (Lindero) event.getObject();
        linder.setLdrNombre(linder.getLdrNombre().toUpperCase());

        servicioLindero.edit(linder);
        FacesMessage msg = new FacesMessage("Alicuota Editada", ((TmpAlicuota) event.getObject()).getTalNumero());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancelLindero(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("", ((TmpAlicuota) event.getObject()).getTalNumero());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void guardarPropiedadHija() {
        System.out.println("mat padre " + propiedadPadre.getPrdMatricula());
        FacesContext context = FacesContext.getCurrentInstance();

        //marcarComoRevisado();
        //actualizarEstProcRepUsu();
        try {
            if (esPropiedadNueva) {
                if (!propiedadNueva.getPrdDescripcion2().trim().isEmpty()) {
                    if (listaLinderos.size() >= 2) {
                        Propiedad prop = propiedadServicio.buscarPropiedadPor_predio_o_catastro(propiedadNueva.getPrdPredio(), propiedadNueva.getPrdCatastro());
                        if (prop == null) {
                            try {

//                        propiedadNueva.setPrdUserCreador(" ");
//                        propiedadNueva.setPrdClasePropiedad(" ");
//                        propiedadNueva.setPrdEstadoRegistro(" ");
//                        propiedadNueva.setPrdSector(" ");
                                propiedadNueva.setPrdFHR(Calendar.getInstance().getTime());
                                propiedadNueva.setPrdUserModificacion(" ");
//                        propiedadNueva.setPrdUbicacion(" ");
                                propiedadNueva.setPrdVendio("N");
                                propiedadNueva.setPrdPH("S");

                                TipoPropiedadClase tipoPropiedadClasePorDefecto;
                                tipoPropiedadClasePorDefecto = servicioTipoPropiedadClase.find(new TipoPropiedadClase(), "NIN");
                                //propiedadNueva.setTclId(tipoPropiedadClasePorDefecto);
//                        if(propiedadNueva.getPrdCatastro().length() == 0 || propiedadNueva.getPrdCatastro().length() > 100){
//                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El tamaño del campo Catastro debe ser entre 1 y 100", ""));
//                        }
//                        else{

                                NuevasMatriculasServicio.create(propiedadNueva);
                                //Crear TmpAlicuota
                                crearAlicuotaParaPropidadHija(propiedadNueva);
                                tramiteUtil.insertarTramiteAccion(repertorioSeleccionado.getTraNumero(),
                                        propiedadNueva.getPrdMatricula().toString(),
                                        "Propiedad creada con matricula PH"
                                        + propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
                                        + " y predio " + propiedadNueva.getPrdPredio(),
                                        "MPH",
                                        repertorioSeleccionado.getTraNumero().getTraEstadoRegistro(),
                                        null);

                                secuenciaServicio.guardar(getSecuencia());
                                //guardarRepertorioPropiedad();

                                //context.addMessage(null, new FacesMessage("Propiedad Creada", ""));
//                        }
                            } catch (Exception e) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad no creada", ""));

                            }

                            try {
                                for (Lindero lindero : listaLinderos) {
                                    lindero.setLdrFHR(Calendar.getInstance().getTime());
                                    lindero.setLdrUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                    lindero.setPrdMatricula(propiedadNueva);
                                    servicioLindero.create(lindero);

                                }
                                propiedadNueva = new Propiedad();
                                nuevaMatriculaHija();
                                //context.addMessage(null, new FacesMessage("Linderos agregados", ""));
                            } catch (Exception e) {
                                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Linderos no creados ", ""));
                            }

                        } else {
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya existe una Propiedad con Predio: "
                                    + propiedadNueva.getPrdPredio() + " y Catastro: " + propiedadNueva.getPrdCatastro(), "Propiedad no creada"));
                        }

                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La propiedad debe tener mínimo 2 linderos.", ""));
                    }

                    //secuenciaServicio.guardar(getSecuencia());
                    listarPropiedadesHijas();
                    listaPropiedadHijaUV = propiedadServicio.listarPorMatriculaPadre_PrdAgregado(matricula);
                    //listaTmpAlicuotasUV = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoActivo(matricula.intValue());
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Descripción de la Propiedad", ""));
                }
            } else {
                //propiedadNueva.setTpdId(propiedadNueva.getTpdId());
                if (!propiedadNueva.getPrdDescripcion2().trim().isEmpty()) {
                    if (listaLinderos.size() >= 2) {
                        Propiedad prop = propiedadServicio.buscarPropiedadEditarPor_predio_o_catastro(propiedadNueva.getPrdPredio(), propiedadNueva.getPrdCatastro(), propiedadNueva.getPrdMatricula());
                        if (prop == null) {

                            NuevasMatriculasServicio.edit(propiedadNueva);
                            //editar Alicuota
                            TmpAlicuota tmpAlicEdit = tmpAlicuotaDao.buscarPorPredio_Catastro(propiedadNueva.getPrdPredio(), propiedadNueva.getPrdCatastro());
                            if (tmpAlicEdit != null) {
                                editarAlicuotaParaPropidadHija(propiedadNueva, tmpAlicEdit);
                            }

                            //guardarRepertorioPropiedad();
                            context.addMessage(null, new FacesMessage("Propiedad Actualizada", ""));
                            tramiteUtil.insertarTramiteAccion(repertorioSeleccionado.getTraNumero(),
                                    propiedadNueva.getPrdMatricula().toString(),
                                    "Propiedad actualizada con matricula "
                                    + propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
                                    + " y predio " + propiedadNueva.getPrdPredio(),
                                    repertorioSeleccionado.getTraNumero().getTraEstado(),
                                    repertorioSeleccionado.getTraNumero().getTraEstadoRegistro(),
                                    null);

                            for (Lindero lindero : listaLinderos) {
                                lindero.setLdrFHR(Calendar.getInstance().getTime());
                                lindero.setLdrUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                lindero.setPrdMatricula(propiedadNueva);
                                servicioLindero.edit(lindero);
                            }
                            //context.addMessage(null, new FacesMessage("Linderos Actualizados", ""));
                            indiceTab = 2;
                        } else {
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya existe una Propiedad con Predio: "
                                    + propiedadNueva.getPrdPredio() + " y Catastro: " + propiedadNueva.getPrdCatastro(), "Propiedad no creada"));
                        }
                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La propiedad debe tener mínimo 2 linderos.", ""));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Descripción de la Propiedad", ""));
                }

            }
            listarPropiedadesHijas();
            sumarAlicuotaPropiedades();
            listaPropiedadHijaUV = propiedadServicio.listarPorMatriculaPadre_PrdAgregado(matricula);
            listaTmpAlicuotasUV = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoN_EstadoA(matricula);
            verificarTramValor_PropHija();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad No Creada", "");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
        try {
            //guardarRepertorioPropiedad();

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        //mostrarTabView = false;
        //disabledTab = false;
        indiceTab = 2;

    }

    public void crearAlicuotaParaPropidadHija(Propiedad propiedad) {
        TmpAlicuota tmpAlic = new TmpAlicuota();

        tmpAlic.setTalNumero(propiedad.getPrdUnidadVivienda());
        tmpAlic.setTalMatricula(matricula);
        tmpAlic.setTalAlicuota(propiedad.getPrdAlicuota());
        tmpAlic.setTalArea(propiedad.getPrdBloque());
        tmpAlic.setTalPiso(propiedad.getPrdPiso());
        tmpAlic.setTalDescripcion(propiedad.getTpdId().getTpdNombre());
        tmpAlic.setTalPredio(propiedad.getPrdPredio());
        tmpAlic.setTalCatastro(propiedad.getPrdCatastro());
        tmpAlic.setTalUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
        tmpAlic.setTalFHR(Calendar.getInstance().getTime());
//                            tmpAlic.setTalEstado("I");
        tmpAlic.setTalEstado("TP"); //ESTADO AUXILIAR PARA  LA TmpAlicuota con campo principal = 1 QUE SE CREA CUANDO SE CREA LA MATRICULA PH   
        tmpAlic.setTaPrincipal(Short.valueOf("1"));
        tmpAlic.setTraAgrupacion("1");
        tmpAlic.setTraConjunto(propiedad.getPrdConjunto());
        tmpAlic.setTraConjuntoId(propiedad.getPrdConjuntoId());
        tmpAlic.setTalPath(" ");
        tmpAlic.setTalVersion(Short.valueOf("0"));
        tmpAlic.setTalObservacion(propiedad.getPrdUnidadVivienda());
        tmpAlic.setTalTipoMedidaCodigo(propiedad.getUmdId().getUmdCodigo());
        tmpAlic.setTalTipoPropiedadCodigo(propiedad.getTpdId().getTpdCodigo());
        tmpAlic.setTalSuperficie(propiedad.getPrdSuperficie());
        tmpAlic.setTalSecuencia(null);
        tmpAlicuotaDao.create(tmpAlic);
    }

    public void editarAlicuotaParaPropidadHija(Propiedad propiedad, TmpAlicuota tmpAlic) {

        tmpAlic.setTalNumero(propiedad.getPrdUnidadVivienda());
        tmpAlic.setTalMatricula(matricula);
        tmpAlic.setTalAlicuota(propiedad.getPrdAlicuota());
        tmpAlic.setTalArea(propiedad.getPrdBloque());
        tmpAlic.setTalPiso(propiedad.getPrdPiso());
        tmpAlic.setTalDescripcion(propiedad.getTpdId().getTpdNombre());
        tmpAlic.setTalPredio(propiedad.getPrdPredio());
        tmpAlic.setTalCatastro(propiedad.getPrdCatastro());
        tmpAlic.setTalUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
        tmpAlic.setTalFHR(Calendar.getInstance().getTime());
        //tmpAlic.setTalEstado("I");
        tmpAlic.setTalEstado("TP"); //ESTADO AUXILIAR PARA  LA TmpAlicuota con campo principal = 1 QUE SE CREA CUANDO SE CREA LA MATRICULA PH   
        tmpAlic.setTaPrincipal(Short.valueOf("1"));
        tmpAlic.setTraAgrupacion("1");
        tmpAlic.setTraConjunto(propiedad.getPrdConjunto());
        tmpAlic.setTraConjuntoId(propiedad.getPrdConjuntoId());
        tmpAlic.setTalPath(" ");
        tmpAlic.setTalVersion(Short.valueOf("0"));
        tmpAlic.setTalObservacion(propiedad.getPrdUnidadVivienda());
        tmpAlic.setTalTipoMedidaCodigo(propiedad.getUmdId().getUmdCodigo());
        tmpAlic.setTalTipoPropiedadCodigo(propiedad.getTpdId().getTpdCodigo());
        tmpAlic.setTalSuperficie(propiedad.getPrdSuperficie());
        tmpAlic.setTalSecuencia(null);
        tmpAlicuotaDao.edit(tmpAlic);
    }

    //VERIFICAR SI EXISTE PROPIEDAD Y TIENE PROPIEDADES HIJAS
    public void verificarTramValor_PropHija() throws ServicioExcepcion {
        List<TramiteValor> listTramValor = new ArrayList<>();
        for (TramiteValor tramiteValor : listaTramiteValor) {
            Propiedad prop = new Propiedad();
            prop = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tramiteValor.getTraNumPredio().toString(), tramiteValor.getTrvNumCatastro().toString());
            if (prop != null) {
                List<Propiedad> listProp = new ArrayList<>();
                listProp = propiedadServicio.listarPorMatriculaPadre(prop.getPrdMatricula());
                if (listProp.isEmpty()) {
                    tramiteValor.setTienePropHija(false);
                } else {
                    tramiteValor.setTienePropHija(true);
                }
            }
            listTramValor.add(tramiteValor);
        }
        listaTramiteValor.clear();
        listaTramiteValor.addAll(listTramValor);
    }

    public void listarPropiedadesHijas() {
        try {
            matriculalPadre = matricula;
            listaPropiedadHija.clear();
            listaPropiedadHija = propiedadServicio.listarPorMatriculaPadre(matriculalPadre);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void cargarDatosAlicuota(Propiedad propiedad) {

        alicuota.setPrdMatricula(propiedad);

    }

    public void listarAlicuota(Propiedad propiedad) throws ServicioExcepcion {
        propiedadAlicuota = propiedad;
        listaAlicuota.clear();
        listaAlicuota = alicuotaServicio.listarPorMatricula(propiedad.getPrdMatricula());
        cargarDatosAlicuota(propiedad);
    }

    public void guardarAlicuota() throws ServicioExcepcion {
        alicuota.setAltFHR(Calendar.getInstance().getTime());
        alicuota.setAltUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
        alicuota.setAltEstado("A");
        alicuota.setAltNumero(alicuota.getAltNumero().toUpperCase());
        alicuota.setAltArea(alicuota.getAltArea().toUpperCase());
        alicuota.setAltDescripcion(alicuota.getAltDescripcion().toUpperCase());
        alicuota.setAltObservacion(alicuota.getAltObservacion().toUpperCase());
        alicuotaServicio.create(alicuota);
        //Actualizar alicuota de la propiedad        
        Propiedad prop = propiedadServicio.obtenerPorMatricula(alicuota.getPrdMatricula().getPrdMatricula());
        BigDecimal totalAlic = prop.getPrdAlicuota();
        totalAlic = totalAlic.add(alicuota.getAltAlicuota());
        prop.setPrdAlicuota(totalAlic);
        propiedadServicio.edit(prop);
        listaPropiedadHija = propiedadServicio.listarPorMatriculaPadre(matricula);
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Alicuota Creada", "");
        FacesContext.getCurrentInstance().addMessage("", facesMsg);
        //
        alicuota.setAltNumero("");
        alicuota.setAltArea("");
        alicuota.setAltPiso("");
        alicuota.setAltAlicuota(new BigDecimal(0));
        alicuota.setAltDescripcion("");
        alicuota.setAltObservacion("");

        listarAlicuota(alicuota.getPrdMatricula());

    }

    public void tabLinderos() {
        indiceTabNuevaProp = 1;
    }

    public void tabDatosDesc() {
        indiceTabNuevaProp = 2;
    }

    public void tabAlicuotas() {
        indiceTabNuevaProp = 3;
    }

    public void cargarPropiedadHija(Propiedad propiedad) throws ServicioExcepcion {

        try {
            propiedadNueva = propiedadServicio.encontrarPropiedadPorId(propiedad.getPrdMatricula());
            //matricula = propiedadNueva.getPrdMatricula().longValue();
            esPropiedadNueva = false;
            listaLinderos.clear();
            listaLinderos = servicioLindero.listarPorNumMatricula(getPropiedadNueva().getPrdMatricula());

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * ****************** ELIMINAR PROPIEDADES HIJAS *******************
     */
    public void eliminarPropiedad(Propiedad propiedad) {
        try {
            //ELIMINA LOS REGISTROS DE REPERTORIO PROPIEDAD ASOCIADOS A ESTA PROPIEDAD
//            List<RepertorioPropiedad> listaRepPropiedad = new ArrayList<>();
//            listaRepPropiedad = servicioRepertorioPropiedad.listarPorNumRepertorioPorNumMatricula(numRepertorio, propiedad.getPrdMatricula());
//            for (RepertorioPropiedad repertorioPropiedad : listaRepPropiedad) {
//                servicioRepertorioPropiedad.remove(repertorioPropiedad);
//            }
            //ELIMINA LOS LINDEROS ASOCIADOS A ESTA PROPIEDAD
//            List<Lindero> listaLinderos = new ArrayList<>();
//            listaLinderos = servicioLindero.listarPorNumMatricula(propiedad.getPrdMatricula());
//            for (Lindero listaLindero : listaLinderos) {
//                servicioLindero.remove(listaLindero);
//            }
            //ELIMINA (CAMBIA A ESTADO = I) LA PROPIEDAD              
            //propiedadServicio.remove(propiedad);
            propiedad.setPrdEstadoRegistro("I");
            propiedadServicio.edit(propiedad);
            listarPropiedadesHijas();
            sumarAlicuotaPropiedades();
            listaPropiedadHijaUV = propiedadServicio.listarPorMatriculaPadre_PrdAgregado(matricula);
            verificarTramValor_PropHija();
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo eliminar la Propiedad. Existen Alicuotas asociadas a esta Propiedad. Elimine las Alicuotas de esta Propiedad", "");
            FacesContext.getCurrentInstance().addMessage("", facesMsg);
            System.out.println(e);
        }

    }

    public void eliminarAlicuota(Alicuota alicuota) {
        try {
            alicuotaServicio.remove(alicuota);
            listarAlicuota(propiedadAlicuota);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * ***************************************************************************************
     */
    /**
     * ******************************* CREAR COMPANIA
     * **************************************
     */
    public void nombrePersona() {
        getTramitesControladorBb().setNombrePersona(getTramitesControladorBb().getPersona().getPerApellidoPaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerApellidoMaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerNombre().trim());
        String Nombre = getTramitesControladorBb().getNombrePersona();
        getTramitesControladorBb().setNombrePersona(Nombre.replace("null", ""));
        getTramitesControladorBb().setEstado(Boolean.TRUE);

    }

    public void buscarPersona() throws IOException, ServicioExcepcion {

        if (getTramitesControladorBb().getIdentificacion() != null || !getTramitesControladorBb().getIdentificacion().equals("")) {
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

    public void agregarPersonaCompania() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (getTramitesControladorBb().getIdentificacion() != null && !getTramitesControladorBb().getIdentificacion().equals("")) {
            personaCompania = personaServicio.buscarPersona(getTramitesControladorBb().getIdentificacion());
            if (personaCompania != null) {
                if (!listaPersonaCompania.contains(personaCompania)) {
                    listaPersonaCompania.add(personaCompania);
                }

            } else {
                String mensaje = "Persona no encontrada";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", mensaje));
            }

        } else {
            String mensaje = "Ingrese la Identificación";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", mensaje));
        }
        getTramitesControladorBb().setIdentificacion("");
        getTramitesControladorBb().setNombrePersona("");

    }

    public void listarParroquias() {
        nuevaCompania = new Compania();
        identificacion = "";
        nombreComp = "";
        construcComp = "";
        listaPersonaCompania.clear();
        getTramitesControladorBb().setNombrePersona("");
        listaParriquias = servicioParroquia.listarTodo();
        parroquiaCompania = null;
    }

    public void setearDatosCompania() {
        nuevaCompania.setComNombre(nombreComp.toUpperCase());
        nuevaCompania.setComConstructora(construcComp.toUpperCase());
        nuevaCompania.setParId(parroquiaCompania);
    }

    public void guardarCompania() throws ServicioExcepcion {
        nuevaCompania.setComCodigo("0");
        nuevaCompania.setComRevocatoria(Short.valueOf("0"));
        nuevaCompania.setComAclaratoria(Short.valueOf("0"));
        nuevaCompania.setComUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
        nuevaCompania.setComFHR(Calendar.getInstance().getTime());
        //nuevaCompania.setParId(parroquiaCompania);
        if (nuevaCompania.getComNombre() != null && !nuevaCompania.getComNombre().trim().isEmpty()) {
            if (nuevaCompania.getComConstructora() != null && !nuevaCompania.getComConstructora().trim().isEmpty()) {
                if (nuevaCompania.getParId() != null) {

                    if (!listaPersonaCompania.isEmpty()) {
                        companiaServicio.create(nuevaCompania);
                        Compania compania = companiaServicio.buscarCompaniaReciente();
                        for (Persona persona : listaPersonaCompania) {
                            Representante representante = new Representante();
                            representante.setComId(compania);
                            representante.setPerId(persona);
                            representanteDao.create(representante);
                        }
                        listaCompania = companiaServicio.listarTodo();
                        nuevaCompania = new Compania();
                        listaPersonaCompania.clear();
                        parroquiaCompania = null;
                        nombreComp = "";
                        construcComp = "";
                        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Compañía Creada", "");
                        FacesContext.getCurrentInstance().addMessage("", facesMsg);

                    } else {
                        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Agregue Representante de la Compañía", "");
                        FacesContext.getCurrentInstance().addMessage("", facesMsg);
                    }
                } else {
                    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione la parroquia", "");
                    FacesContext.getCurrentInstance().addMessage("", facesMsg);

                }
            } else {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Constructora", "");
                FacesContext.getCurrentInstance().addMessage("", facesMsg);

            }
        } else {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el nombre de la Compañía", "");
            FacesContext.getCurrentInstance().addMessage("", facesMsg);

        }

    }

    public void eliminarRepresentante(Persona persona) {

        listaPersonaCompania.remove(persona);
    }

    /**
     * ***************************************************************************************
     */
    /**
     * *********************************** TMPALICUOTA
     * ***************************************
     */
    public void nuevaAlicuotaTmp() {
        propiedadPadre = propiedadServicio.obtenerPorMatricula(matricula);
        tmpAlicuota = new TmpAlicuota();
        tmpAlicuota.setTalMatricula(propiedadPadre.getPrdMatricula());
        tmpAlicuota.setTalAlicuota(new BigDecimal(0));
        tmpAlicuota.setTalEstado("A");
        tmpAlicuota.setTaPrincipal(Short.valueOf("0"));
        tmpAlicuota.setTraAgrupacion("");
        tmpAlicuota.setTraConjunto(propiedadPadre.getPrdConjunto());
        tmpAlicuota.setTraConjuntoId(propiedadPadre.getPrdConjuntoId());
        tmpAlicuota.setTalVersion(Short.valueOf("1"));
        tmpAlicuota.setTalPath(" ");
        tmpAlicuota.setTalTipoPropiedadCodigo("");
        tmpAlicuota.setTalTipoMedidaCodigo("");
        tmpAlicuota.setTalSuperficie(new BigDecimal(0));
        tmpAlicuota.setTalUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
        tmpAlicuota.setTalFHR(Calendar.getInstance().getTime());
        tipoProp = new TipoPropiedad();
        cantidadAlicuatasTmp = 0;
        inicialAlicuatasTmp = 0;
        letraAlicuatasTmp = "";
        letraInicAlicuatasTmp = "";
        numAlicuatasTmp = 0;

    }

    public void cargarAlicuotaTmp(TmpAlicuota TmpAlicuota) throws ServicioExcepcion {

        try {
            tmpAlicuota = tmpAlicuotaDao.encontrarTmpAlicuotaPorId(TmpAlicuota.getTalId());
            esTmpAlicuotaNueva = false;

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void eliminarTmpAlicuota(TmpAlicuota tmpAlicuota) {
        try {

            //ELIMINA (CAMBIAR A ESTADO = E) LA tmpAlicuota              
            //tmpAlicuotaDao.remove(tmpAlicuota);
            tmpAlicuota.setTalEstado("E");
            tmpAlicuotaDao.edit(tmpAlicuota);
            listarTmpAlicuotas();
            listaTmpAlicuotasUV = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoN_EstadoA(matricula);
            disabledTabUndViv = true;

        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ha ocurrido un error", "");
            FacesContext.getCurrentInstance().addMessage("", facesMsg);
            System.out.println(e);
        }

    }

    public void listarTmpAlicuotas() {
        try {
            listaTmpAlicuotas.clear();
            listaTmpAlicuotas = tmpAlicuotaDao.listarPorMatriculaPadre_SinEstadoTP(matricula);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void prepararTmpAlicuota(TmpAlicuota trmpAli) {
        esVendibleTmpAlicuota = false;
        trmpAli.setTalMatricula(propiedadPadre.getPrdMatricula());
        trmpAli.setTalTipoMedidaCodigo(propiedadPadre.getUmdId().getUmdCodigo());
        trmpAli.setTalEstado("A");
        trmpAli.setTaPrincipal(Short.valueOf("0"));
        trmpAli.setTraAgrupacion("");
        trmpAli.setTraConjunto(propiedadPadre.getPrdConjunto());
        trmpAli.setTraConjuntoId(propiedadPadre.getPrdConjuntoId());
        trmpAli.setTalVersion(Short.valueOf("1"));
        trmpAli.setTalPath(" ");
        trmpAli.setTalTipoPropiedadCodigo(tmpAlicuota.getTalTipoPropiedadCodigo());
        trmpAli.setTalTipoMedidaCodigo(propiedadPadre.getUmdId().getUmdCodigo());
        trmpAli.setTalSuperficie(new BigDecimal(0));
        trmpAli.setTalUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
        trmpAli.setTalFHR(Calendar.getInstance().getTime());
        //trmpAli.setTalAlicuota(tmpAlicuota.getTalAlicuota());
        trmpAli.setTalAlicuota(BigDecimal.ZERO);
        trmpAli.setTalPiso(tmpAlicuota.getTalPiso().toUpperCase());
        trmpAli.setTalArea(tmpAlicuota.getTalArea().toUpperCase());
        trmpAli.setTalDescripcion(tmpAlicuota.getTalDescripcion());
        trmpAli.setTalObservacion(tmpAlicuota.getTalObservacion().toUpperCase());

    }

    public void guardarAlicuotaTmp() throws ServicioExcepcion {

        if (esTmpAlicuotaNueva = true) {
            Short principal = Short.valueOf("0");
            String estado = "";
            List<TmpAlicuota> listTmpAlic = new ArrayList<>();
            if (esVendibleTmpAlicuota == true) {
                principal = Short.valueOf("1");
                estado = "A";
            } else {
                principal = Short.valueOf("0");
                estado = "N"; //N: NO VENDIBLE
            }
            switch (tipoNumeracionAlicTmp) {
                case "numerico":
                    listTmpAlic.clear();
                    for (int i = 1; i <= cantidadAlicuatasTmp; i++) {
                        TmpAlicuota tmpAlc = new TmpAlicuota();
                        prepararTmpAlicuota(tmpAlc);
                        String numeroAlcTmp = String.valueOf(i);
                        tmpAlc.setTalNumero(numeroAlcTmp.toUpperCase());

                        listTmpAlic.add(tmpAlc);
                    }
                    if (verificarExistePisoBloque(listTmpAlic) == false) {
                        for (TmpAlicuota tmpAlicuota1 : listTmpAlic) {
                            tmpAlicuota1.setTaPrincipal(principal);
                            tmpAlicuota1.setTalEstado(estado);
                            tmpAlicuotaDao.create(tmpAlicuota1);
                        }
                        mensajeTmpAlicutasCreadas();
                    } else {
                        mensajeErrorTmpAlicutas();
                    }
                    break;
                case "letras":
                    listTmpAlic.clear();
                    for (int i = 0; i < cantidadAlicuatasTmp; i++) {
                        TmpAlicuota tmpAlc = new TmpAlicuota();
                        prepararTmpAlicuota(tmpAlc);
                        String letra = generarSecuencialLetras(i);
                        String numeroAlcTmp = letra;
                        tmpAlc.setTalNumero(numeroAlcTmp.toUpperCase());
                        System.out.println(tmpAlc.getTalNumero());
                        listTmpAlic.add(tmpAlc);
                    }
                    if (verificarExistePisoBloque(listTmpAlic) == false) {
                        for (TmpAlicuota tmpAlicuota1 : listTmpAlic) {
                            System.out.println("NUM: " + tmpAlicuota1.getTalNumero());
                            tmpAlicuota1.setTaPrincipal(principal);
                            tmpAlicuota1.setTalEstado(estado);
                            tmpAlicuotaDao.create(tmpAlicuota1);
                        }
                        mensajeTmpAlicutasCreadas();
                    } else {
                        mensajeErrorTmpAlicutas();
                    }
                    break;
                case "manual":
                    listTmpAlic.clear();
                    TmpAlicuota tmpAlcM = new TmpAlicuota();
                    prepararTmpAlicuota(tmpAlcM);
                    tmpAlcM.setTalNumero(tmpAlicuota.getTalNumero().toUpperCase());
                    listTmpAlic.add(tmpAlcM);
                    if (verificarExistePisoBloque(listTmpAlic) == false) {
                        for (TmpAlicuota tmpAlicuota1 : listTmpAlic) {
                            tmpAlicuota1.setTaPrincipal(principal);
                            tmpAlicuota1.setTalEstado(estado);
                            tmpAlicuotaDao.create(tmpAlicuota1);
                        }
                        mensajeTmpAlicutasCreadas();
                    } else {
                        mensajeErrorTmpAlicutas();
                    }
                    break;
                case "Por Piso":
                    listTmpAlic.clear();
                    int finalNum = inicialAlicuatasTmp + cantidadAlicuatasTmp;
                    for (int i = inicialAlicuatasTmp; i < finalNum; i++) {
                        TmpAlicuota tmpAlc = new TmpAlicuota();
                        prepararTmpAlicuota(tmpAlc);
                        String numeroAlcTmp = String.valueOf(i);
                        tmpAlc.setTalNumero(numeroAlcTmp.toUpperCase());
                        listTmpAlic.add(tmpAlc);
                    }
                    if (verificarExistePisoBloque(listTmpAlic) == false) {
                        for (TmpAlicuota tmpAlicuota1 : listTmpAlic) {
                            tmpAlicuota1.setTaPrincipal(principal);
                            tmpAlicuota1.setTalEstado(estado);
                            tmpAlicuotaDao.create(tmpAlicuota1);
                        }
                        mensajeTmpAlicutasCreadas();
                    } else {
                        mensajeErrorTmpAlicutas();
                    }

                    break;
                case "Letra_Numero":
                    listTmpAlic.clear();
                    int finalLN = inicialAlicuatasTmp + cantidadAlicuatasTmp;
                    for (int i = inicialAlicuatasTmp; i < finalLN; i++) {
                        TmpAlicuota tmpAlc = new TmpAlicuota();
                        prepararTmpAlicuota(tmpAlc);
                        String numeroAlcTmp = letraAlicuatasTmp + "-" + String.valueOf(i);
                        tmpAlc.setTalNumero(numeroAlcTmp.toUpperCase());
                        listTmpAlic.add(tmpAlc);
                    }
                    if (verificarExistePisoBloque(listTmpAlic) == false) {
                        for (TmpAlicuota tmpAlicuota1 : listTmpAlic) {
                            tmpAlicuota1.setTaPrincipal(principal);
                            tmpAlicuota1.setTalEstado(estado);
                            tmpAlicuotaDao.create(tmpAlicuota1);
                        }
                        mensajeTmpAlicutasCreadas();
                    } else {
                        mensajeErrorTmpAlicutas();
                    }

                    break;
                case "Numero_Letra":
                    listTmpAlic.clear();
                    int j = 0;
                    for (int i = 0; i <= 100000; i++) {

                        String let = generarSecuencialLetras(i);
                        if (letraInicAlicuatasTmp.equals(let)) {
                            j = i;
                        }
                    }
                    int rang = j + cantidadAlicuatasTmp;
                    for (int k = j; k < rang; k++) {
                        TmpAlicuota tmpAlc = new TmpAlicuota();
                        prepararTmpAlicuota(tmpAlc);
                        String letra = generarSecuencialLetras(k);
                        String numeroAlcTmp = numAlicuatasTmp + "-" + letra;
                        tmpAlc.setTalNumero(numeroAlcTmp.toUpperCase());
                        listTmpAlic.add(tmpAlc);
                    }
                    if (verificarExistePisoBloque(listTmpAlic) == false) {
                        for (TmpAlicuota tmpAlicuota1 : listTmpAlic) {
                            tmpAlicuota1.setTaPrincipal(principal);
                            tmpAlicuota1.setTalEstado(estado);
                            tmpAlicuotaDao.create(tmpAlicuota1);
                        }
                        mensajeTmpAlicutasCreadas();
                    } else {
                        mensajeErrorTmpAlicutas();
                    }

                    break;

            }
            tipoProp = new TipoPropiedad();
            cantidadAlicuatasTmp = 0;

//            else{
//                 FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Componente PH no Creado. Ya existe componente con bloque y piso", "");
//        FacesContext.getCurrentInstance().addMessage("", facesMsg);
//            }
        } else {
            tmpAlicuotaDao.edit(tmpAlicuota);
        }
        cantidadAlicuatasTmp = 0;
        inicialAlicuatasTmp = 0;
        letraAlicuatasTmp = "";
        letraInicAlicuatasTmp = "";
        numAlicuatasTmp = 0;
        indiceTab = 3;
        tmpAlicuota = new TmpAlicuota();
        tmpAlicuota.setTalMatricula(propiedadPadre.getPrdMatricula());
        listarTmpAlicuotas();
        listaTmpAlicuotasUV = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoN_EstadoA(matricula);
        disabledTabUndViv = true;

    }

    public void mensajeErrorTmpAlicutas() {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Componente PH no Creado. Ya existe una combinación para el mismo NÚMERO, BLOQUE y PISO", "");
        FacesContext.getCurrentInstance().addMessage("", facesMsg);
    }

    public void mensajeTmpAlicutasCreadas() {
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Componente PH Creado", "");
        FacesContext.getCurrentInstance().addMessage("", facesMsg);
    }

    public boolean verificarExistePisoBloque(List<TmpAlicuota> listTmpAl) {
        int aux = 0;
        for (TmpAlicuota tmpAlicuo : listTmpAl) {
            String auxArea = tmpAlicuota.getTalArea().toUpperCase();
            String auxPiso = tmpAlicuota.getTalPiso().toUpperCase();
            String auxNumero = tmpAlicuo.getTalNumero().toUpperCase();
            for (TmpAlicuota tmpAlicuota1 : listaTmpAlicuotas) {
                if (auxArea.equals(tmpAlicuota1.getTalArea().toUpperCase())
                        && auxPiso.equals(tmpAlicuota1.getTalPiso().toUpperCase())
                        && auxNumero.equals(tmpAlicuota1.getTalNumero().toUpperCase())) {
                    aux++;
                }

            }
        }
        if (aux == 0) {
            return false;
        } else {
            return true;
        }

    }

    public void llenarDescripcionTmpAlicuaota() {

        Propiedad propPadre = propiedadServicio.obtenerPorMatricula(matricula);

        texto = "";
        String tipoPropiedad = "";
        String parroquia = "";
        try {
            tipoPropiedad = tipoProp.getTpdNombre().trim();
            parroquia = propPadre.getPrdTdtParNombre().trim();
            //texto = tipoPropiedad + " situado en la Parroquia " + parroquia + " de este Cantón";
            texto = tipoPropiedad;
            tmpAlicuota.setTalDescripcion(texto);
            tmpAlicuota.setTalTipoPropiedadCodigo(tipoProp.getTpdCodigo());
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void tipoNumeracionAlicuotaTmp() {
        switch (tipoNumeracionAlicTmp) {
            case "numerico":
                renderedNumeroAlicTmp = false;
                renderedCantAlicTmp = true;
                renderedInicialAlicTmp = false;
                renderedLetraAlicTmp = false;
                break;
            case "letras":
                renderedNumeroAlicTmp = false;
                renderedCantAlicTmp = true;
                renderedInicialAlicTmp = false;
                renderedLetraAlicTmp = false;
                renderedLetNumlicTmp = false;
                break;
            case "manual":
                renderedNumeroAlicTmp = true;
                renderedCantAlicTmp = false;
                renderedInicialAlicTmp = false;
                renderedLetraAlicTmp = false;
                renderedLetNumlicTmp = false;
                cantidadAlicuatasTmp = 0;
                inicialAlicuatasTmp = 0;
                break;
            case "Por Piso":
                renderedNumeroAlicTmp = false;
                renderedInicialAlicTmp = true;
                renderedCantAlicTmp = true;
                renderedLetraAlicTmp = false;
                renderedLetNumlicTmp = false;
                break;
            case "Letra_Numero":
                renderedNumeroAlicTmp = false;
                renderedInicialAlicTmp = true;
                renderedCantAlicTmp = true;
                renderedLetraAlicTmp = true;
                renderedLetNumlicTmp = false;
                break;
            case "Numero_Letra":
                renderedNumeroAlicTmp = false;
                renderedInicialAlicTmp = false;
                renderedCantAlicTmp = true;
                renderedLetraAlicTmp = false;
                renderedLetNumlicTmp = true;
                break;
        }

    }

    public void onRowEdit(RowEditEvent event) throws ServicioExcepcion {
        TmpAlicuota tmpAlicEdit = (TmpAlicuota) event.getObject();
        tmpAlicEdit.setTalNumero(tmpAlicEdit.getTalNumero().toUpperCase());
        tmpAlicEdit.setTalArea(tmpAlicEdit.getTalArea().toUpperCase());
        tmpAlicEdit.setTalPiso(tmpAlicEdit.getTalPiso().toUpperCase());
        tmpAlicEdit.setTalDescripcion(tmpAlicEdit.getTalDescripcion());
        tmpAlicEdit.setTalObservacion(tmpAlicEdit.getTalObservacion().toUpperCase());
        tmpAlicuotaDao.edit(tmpAlicEdit);
        listaTmpAlicuotasUV = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoN_EstadoA(matricula);
        FacesMessage msg = new FacesMessage("Componente Editado", ((TmpAlicuota) event.getObject()).getTalNumero());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        disabledTabUndViv = true;
        indiceTab = 3;

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("", ((TmpAlicuota) event.getObject()).getTalNumero());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String generarSecuencialLetras(int cantidad) {

        return cantidad < 0 ? "" : generarSecuencialLetras((cantidad / 26) - 1) + (char) (65 + cantidad % 26);

    }

    /**
     * ***************************************************************************************
     */
    /**
     * ****************** UNIDAD DE VIVIENDA ********************************
     */
    public String actualizarDescripcion2CasoCrear(Propiedad propiedad, List<TmpAlicuota> listaTmpAlicuota) {

        String text = "";
        String tipoPropiedad = "";
        String parroquia = "";
        try {
            if (listaTmpAlicuota.isEmpty()) {
                Propiedad propPadre = propiedadServicio.obtenerPorMatricula(propiedad.getPrdPadreMatricula().getPrdMatricula());
                if (propPadre.getTpdId() != null) {
                    tipoPropiedad = propPadre.getTpdId().getTpdNombre().trim();
                }
            } else {
                String descripcion = "";
                List<TmpAlicuota> listTmpAlic = new ArrayList<>();
                listTmpAlic = tmpAlicuotaDao.listarPorIdAlicuota_PorNumMatricAlicuota(propiedad.getPrdMatricula());
                for (TmpAlicuota tmpAlicuota1 : listTmpAlic) {
                    if (!listaTmpAlicuota.contains(tmpAlicuota1)) {
                        listaTmpAlicuota.add(tmpAlicuota1);
                    }
                }
                for (TmpAlicuota tmlAlic : listaTmpAlicuota) {
                    //TipoPropiedad tipoProp = new TipoPropiedad();                    
                    //tipoProp = tipoPropiedadDao.buscarPorCodigo(tmlAlic.getTalTipoPropiedadCodigo());
                    //if (tipoProp != null) {
                    descripcion = descripcion + tmlAlic.getTalDescripcion().trim()
                            + ", Piso: " + tmlAlic.getTalPiso()
                            + ", Número: " + tmlAlic.getTalNumero() + ". ";
                    //}                    
                }
                tipoPropiedad = descripcion;
            }
            parroquia = propiedad.getPrdTdtParNombre().trim();
            text = tipoPropiedad + " Situado en la Parroquia " + parroquia + " de este Cantón.";

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return text;
    }

    public void actualizarDescripcion2CasoDeshacer(List<Propiedad> listaProp) {

        String text = "";
        String tipoPropiedad = "";
        String parroquia = "";
        List<String> listaTipoProp = new ArrayList<>();
        String descripcion = "";
        try {
            for (Propiedad propiedad : listaProp) {
                List<TmpAlicuota> listTmpAlic = new ArrayList<>();
                listTmpAlic = tmpAlicuotaDao.listarPorIdAlicuota_PorNumMatricAlicuota(propiedad.getPrdMatricula());
//                for (TmpAlicuota tmpAlicuota1 : listTmpAlic) {
//                    if (!listaTipoProp.contains(tmpAlicuota1.getTalTipoPropiedadCodigo())) {
//                        listaTipoProp.add(tmpAlicuota1.getTalTipoPropiedadCodigo());
//                    }                    
//                    
//                }
                for (TmpAlicuota tmpAlic : listTmpAlic) {
//                    TipoPropiedad tipoProp = new TipoPropiedad();                    
//                    tipoProp = tipoPropiedadDao.buscarPorCodigo(codigo);
//                    if (tipoProp != null) {
                    descripcion = descripcion + tmpAlic.getTalDescripcion().trim()
                            + ", Piso: " + tmpAlic.getTalPiso()
                            + ", Número: " + tmpAlic.getTalNumero() + ". ";
                    //}                    
                }
                tipoPropiedad = descripcion;

                if (listTmpAlic.isEmpty()) {
                    Propiedad propPadre = propiedadServicio.obtenerPorMatricula(propiedad.getPrdPadreMatricula().getPrdMatricula());
                    if (propPadre.getTpdId() != null) {
                        tipoPropiedad = propPadre.getTpdId().getTpdNombre().trim();
                        text = tipoPropiedad + " situado en la Parroquia " + parroquia + " de este Cantón.";
                    }
                } else {
                    text = tipoPropiedad + " Situado en la Parroquia " + parroquia + " de este Cantón.";
                }
                parroquia = propiedad.getPrdTdtParNombre().trim();
                propiedad.setPrdDescripcion2(text);
                propiedadServicio.edit(propiedad);

            }

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void filtrarPorBloque() throws ServicioExcepcion {
        listaPropiedadHijaUV.clear();
        listaTmpAlicuotasUV.clear();
        switch (bloqueNombreFiltro) {
            case "TODOS":
                listaPropiedadHijaUV = propiedadServicio.listarPorMatriculaPadre_PrdAgregado(matricula);
                listaTmpAlicuotasUV = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoN_EstadoA(matricula);
                break;
            default:
                listaPropiedadHijaUV = propiedadServicio.listarPorMatriculaPadre_PrdAgregado_Bloque(matricula, bloqueNombreFiltro);
                listaTmpAlicuotasUV = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoActivo_Bloque(matricula, bloqueNombreFiltro);
        }
    }

    public void crearUnidadVivienda() throws ServicioExcepcion {
        if (propiedadHijaSelec != null) {
            Propiedad prop = propiedadServicio.obtenerPorMatricula(propiedadHijaSelec.getPrdMatricula());
            BigDecimal totalAlic = prop.getPrdAlicuota();
            String descProp = "";
            List<TmpAlicuota> listaTmpAlic = new ArrayList<>();
            BigDecimal superficieTmpAlic = new BigDecimal("0");
            superficieTmpAlic = propiedadHijaSelec.getPrdSuperficie().divide(BigDecimal.valueOf(listaTmpAlicuotasSelec.size()));
            for (TmpAlicuota tmpAlic : listaTmpAlicuotasSelec) {
                Alicuota alicuota = new Alicuota();
                alicuota.setPrdMatricula(propiedadHijaSelec);
                alicuota.setAltNumero(tmpAlic.getTalNumero().toUpperCase());
                alicuota.setAltAlicuota(tmpAlic.getTalAlicuota());
                alicuota.setAltArea(tmpAlic.getTalArea().toUpperCase());
                alicuota.setAltPiso(tmpAlic.getTalPiso());
                alicuota.setAltDescripcion(tmpAlic.getTalDescripcion().toUpperCase());
                alicuota.setAltObservacion(tmpAlic.getTalObservacion().toUpperCase());
                alicuota.setAltUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                alicuota.setAltFHR(Calendar.getInstance().getTime());
                alicuota.setAltEstado("A");
                totalAlic = totalAlic.add(tmpAlic.getTalAlicuota());

                alicuotaServicio.create(alicuota);

                Alicuota alic = alicuotaServicio.obtenerUltima(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                tmpAlic.setTalEstado("I");
                tmpAlic.setTalAltId(alic.getAltId());
                if (tmpAlic.getTalSuperficie().compareTo(BigDecimal.ZERO) == 0) {
                    tmpAlic.setTalSuperficie(superficieTmpAlic);
                }
                tmpAlicuotaDao.edit(tmpAlic);
                //listaAlicuotasUV.add(alic);

                listaPropiedadHijaUV.remove(propiedadHijaSelec);
                listaTmpAlicuotasUV.removeAll(listaTmpAlicuotasSelec);

                listaTmpAlic.add(tmpAlic);
                //BUSCAR TMPALICUOTA Y ACTUALIZAR PISO
                TmpAlicuota trmpAlicActualizar = new TmpAlicuota();
                trmpAlicActualizar = tmpAlicuotaDao.buscarPorPredio_Catastro(propiedadHijaSelec.getPrdPredio(), propiedadHijaSelec.getPrdCatastro());
                if (trmpAlicActualizar != null) {
                    trmpAlicActualizar.setTalPiso(tmpAlic.getTalPiso());
                    tmpAlicuotaDao.edit(trmpAlicActualizar);
                }

            }

            String nuevaDesc = actualizarDescripcion2CasoCrear(propiedadHijaSelec, listaTmpAlic);
            propiedadHijaSelec.setPrdAlicuota(totalAlic);
            propiedadHijaSelec.setPrdDescripcion2(nuevaDesc);

            propiedadHijaSelec.setPrdAgregado("A");
//             if(totalAlic.compareTo(BigDecimal.ONE) == 0){
//                        propiedadHijaSelec.setPrdValidacion("V");
//                    }
//                    else{
//                        propiedadHijaSelec.setPrdValidacion(null);
//                    }
            propiedadServicio.edit(propiedadHijaSelec);
            // ACTUALIZAR LISTA DE UNIDADES DE VIVIENDA 
            listaAlicuotasUV.clear();
            List<Propiedad> listPropHijas = new ArrayList<>();
            listPropHijas = propiedadServicio.listarPorMatriculaPadre(matricula);
            List<Alicuota> listAlicuot = new ArrayList<>();
            for (Propiedad listPropHija : listPropHijas) {
                listAlicuot.clear();
                listAlicuot = alicuotaServicio.listarPorMatricula(listPropHija.getPrdMatricula());
                listaAlicuotasUV.addAll(listAlicuot);
            }

        }
        listaTmpAlicuotasSelec.clear();
        indiceTab = 4;
        //ACTUALIZAR TAB COMPONENTES PH
        listaTmpAlicuotas.clear();
        listaTmpAlicuotas = tmpAlicuotaDao.listarPorMatriculaPadre_SinEstadoTP(matricula);
        //ACTUALIZAR TAB MATRICULA PH
        listaPropiedadHija = propiedadServicio.listarPorMatriculaPadre(matricula);
        //ACTUALIZAR TAB UNIDAD VIVIENDA
        List<Alicuota> listAlic = new ArrayList<>();
        listaAlicuotaGral.clear();
        for (Alicuota alicuota1 : listaAlicuotasUV) {
            listAlic.clear();
            listAlic = alicuotaServicio.listarPorMatricula(alicuota1.getPrdMatricula().getPrdMatricula());
            for (Alicuota alicuota2 : listAlic) {
                if (!listaAlicuotaGral.contains(alicuota2)) {
                    listaAlicuotaGral.add(alicuota2);
                }
            }
        }

    }

    public void deshacerUnidadVivienda() throws ServicioExcepcion {
        List<Propiedad> listProp = new ArrayList<>();
        for (Alicuota alicuota1 : listaAlicuotasUVSelec) {
            if (!alicuota1.getAltEstado().equals("I")) {
                Propiedad propiedad = new Propiedad();
                propiedad = propiedadServicio.obtenerPorMatricula(alicuota1.getPrdMatricula().getPrdMatricula());
                if (!propiedad.getPrdEstadoRegistro().equals("I")) {
                    BigDecimal totalAlc = propiedad.getPrdAlicuota();
                    totalAlc = totalAlc.subtract(alicuota1.getAltAlicuota());
                    propiedad.setPrdAlicuota(totalAlc);
                    propiedad.setPrdAgregado("I");
//                    if(totalAlc.compareTo(BigDecimal.ONE) == 0){
//                        propiedad.setPrdValidacion("V");
//                    }
//                    else{
//                        propiedad.setPrdValidacion(null);
//                    }
                    propiedadServicio.edit(propiedad);
                    Propiedad prop = new Propiedad();
                    prop = propiedadServicio.obtenerPorMatricula(propiedad.getPrdMatricula());
                    if (!listProp.contains(prop)) {
                        listProp.add(prop);
                    }

//                    if (!listaPropiedadHijaUV.contains(propiedad)) {
//                        listaPropiedadHijaUV.add(propiedad);
//                    }
                    TmpAlicuota tmpAlic = tmpAlicuotaDao.buscarPorIdAlicuotaAsociada(alicuota1.getAltId());
                    if (tmpAlic != null) {
                        tmpAlic.setTalEstado("A");
                        //tmpAlic.setTalAltId(null);
                        tmpAlicuotaDao.edit(tmpAlic);
                    }

                    alicuotaServicio.remove(alicuota1);
                    //listaAlicuotasUV.remove(alicuota1);
                }
            }
        }
        // ACTUALIZAR LISTA DE UNIDADES DE VIVIENDA 
        actualizarDescripcion2CasoDeshacer(listProp);
        /**
         * ******************************************************
         */

        for (Alicuota alicuota1 : listaAlicuotasUVSelec) {
            TmpAlicuota tmpAlic = tmpAlicuotaDao.buscarPorIdAlicuotaAsociada(alicuota1.getAltId());
            if (tmpAlic != null) {
                tmpAlic.setTalAltId(null);
                tmpAlicuotaDao.edit(tmpAlic);
            }
            Alicuota alic = alicuotaServicio.buscarPorNumMatricula(alicuota1.getPrdMatricula().getPrdMatricula());
            if (alic != null) {
                Propiedad prop = propiedadServicio.obtenerPorMatricula(alic.getPrdMatricula().getPrdMatricula());
                prop.setPrdAgregado("A");
                propiedadServicio.edit(prop);
            }

        }

        listaAlicuotasUV.clear();
        List<Propiedad> listPropHijas = new ArrayList<>();
        listPropHijas = propiedadServicio.listarPorMatriculaPadre(matricula);
        List<Alicuota> listAlicuot = new ArrayList<>();
        for (Propiedad listPropHija : listPropHijas) {
            listAlicuot.clear();
            listAlicuot = alicuotaServicio.listarPorMatricula(listPropHija.getPrdMatricula());
            listaAlicuotasUV.addAll(listAlicuot);
        }
        /**
         * ***********
         */
        filtrarPorBloque();
        //listaPropiedadHijaUV = propiedadServicio.listarPorMatriculaPadre_PrdAgregado(matricula);        
        listaAlicuotasUVSelec.clear();
        //listaTmpAlicuotasUV.clear();
        //listaTmpAlicuotasUV = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoN(matricula);
        indiceTab = 4;
        //ACTUALIZAR TAB COMPONENTES PH
        listaTmpAlicuotas.clear();
        listaTmpAlicuotas = tmpAlicuotaDao.listarPorMatriculaPadre_SinEstadoTP(matricula);
        //ACTUALIZAR TAB MATRICULA PH
        listaPropiedadHija = propiedadServicio.listarPorMatriculaPadre(matricula);

        //ACTUALIZAR TAB UNIDAD VIVIENDA
        List<Alicuota> listAlic = new ArrayList<>();
        listaAlicuotaGral.clear();
        for (Alicuota alicuota1 : listaAlicuotasUV) {
            listAlic.clear();
            listAlic = alicuotaServicio.listarPorMatricula(alicuota1.getPrdMatricula().getPrdMatricula());
            for (Alicuota alicuota2 : listAlic) {
                if (!listaAlicuotaGral.contains(alicuota2)) {
                    listaAlicuotaGral.add(alicuota2);
                }
            }

        }

    }

    public void cargarUnidadVivienda() throws ServicioExcepcion {
        listaAlicuotasUV.clear();
        List<Propiedad> listPropHijas = new ArrayList<>();
        List<Alicuota> listAlic = new ArrayList<>();
        listPropHijas = propiedadServicio.listarPorMatriculaPadre(matricula);

        for (Propiedad listPropHija : listPropHijas) {
            listAlic.clear();
            listAlic = alicuotaServicio.listarPorMatricula(listPropHija.getPrdMatricula());
            listaAlicuotasUV.addAll(listAlic);

        }

    }

    public Boolean rowColor(int numMatricula) {
        if (numMatricula % 2 == 0) {
            return true;
        } else {
            return false;
        }

    }

    public void listarDetalleAlicuotas(Propiedad propiedad) throws ServicioExcepcion {
        propUV = propiedad;
        listaAlicuotaMostrar.clear();
        listaAlicuotaMostrar = alicuotaServicio.listarPorMatricula(propiedad.getPrdMatricula());
    }

    /**
     * ******************************* BLOQUE ******************************
     */
    public void listarBloque() throws ServicioExcepcion {
        propiedadPadre = propiedadServicio.obtenerPorMatricula(matricula);
        listaBloque = bloqueDao.listarPorMatricula(propiedadPadre.getPrdMatricula());
        Collections.sort(listaBloque, new Comparator<Bloque>() {

            public int compare(Bloque o1, Bloque o2) {

                return o1.getBloNombre().compareTo(o2.getBloNombre());
            }
        });
    }

    public void eliminarBloque(Bloque bloque) {
        try {

            bloqueDao.remove(bloque);
            listarBloque();

        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo eliminar el Bloque.", "");
            FacesContext.getCurrentInstance().addMessage("", facesMsg);
            System.out.println(e);
        }

    }

    public void cargarBloque(Bloque bloq) throws ServicioExcepcion {

        try {
            bloque = bloqueDao.encontrarBloquePorId(bloq.getBloId());
            esBloqueNuevo = false;

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void nuevoBloque() throws ServicioExcepcion {
        bloque = new Bloque();
        esBloqueNuevo = true;
        propiedadPadre = propiedadServicio.obtenerPorMatricula(matricula);
        bloque.setPrdMatricula(propiedadPadre);
        disabledTipoBloque = false;
        Bloque bloq = bloqueDao.encontrarBloquePorNumMatricula(matricula);
        if (bloq != null) {
            bloque.setBloTipo(bloq.getBloTipo());
            disabledTipoBloque = true;
            tipoBloque();
            if (renderedSubTipoBlq == true) {
                subTipoBloque();
            }

        } else {
            renderedSubTipoBlq = false;
            renderedNombreBlq = false;
        }

    }

    public void guardarBloque() throws ServicioExcepcion, ServicioExcepcion {

        try {
            switch (bloque.getBloTipo()) {
                case "CONJUNTO HABITACIONAL":
                    bloque.setBloCodigo("01");

                    break;
                case "EDIFICIOS":
                    bloque.setBloCodigo("02");
                    bloque.setBloSubTipo(" ");
                    bloque.setBloNombre(bloque.getBloNombre().toUpperCase());

                    break;

                case "ESTADIOS":
                    bloque.setBloCodigo("03");
                    bloque.setBloSubTipo(" ");
                    bloque.setBloNombre(bloque.getBloNombre().toUpperCase());
                    break;
                case "CENTROS COMERCIALES":
                    bloque.setBloCodigo("04");
                    bloque.setBloSubTipo(" ");
                    bloque.setBloNombre(bloque.getBloNombre().toUpperCase());
                    break;
                default:
                    bloque.setBloCodigo(" ");
            }
            switch (bloque.getBloSubTipo()) {
                case "EDIFICIO":
                    bloque.setBloNombre(bloque.getBloNombre().toUpperCase());

                    break;
                case "CASA":
                    bloque.setBloNombre("CASA");
                    break;

                default:

            }

            if (esBloqueNuevo == true) {
                bloque.setBloFHR(Calendar.getInstance().getTime());
                bloque.setBloUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                bloqueDao.create(bloque);
                renderedSubTipoBlq = false;
                renderedNombreBlq = false;
            } else {
                bloqueDao.edit(bloque);
                renderedSubTipoBlq = false;
                renderedNombreBlq = false;
            }
            bloque = new Bloque();
            bloque.setPrdMatricula(propiedadPadre);
            listaBloque = bloqueDao.listarPorMatricula(propiedadPadre.getPrdMatricula());

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void tipoBloque() {
        switch (bloque.getBloTipo()) {
            case "CONJUNTO HABITACIONAL":
                renderedSubTipoBlq = true;
                renderedNombreBlq = false;
                break;
            default:
                renderedSubTipoBlq = false;
                renderedNombreBlq = true;
        }
        bloque.setBloSubTipo("");

    }

    public void subTipoBloque() {
        switch (bloque.getBloSubTipo()) {
            case "EDIFICIO":
                renderedNombreBlq = true;
                break;
            default:
                renderedNombreBlq = false;
        }

    }

    public void validarPropiedadAlicuota(Propiedad propiedad) {
        try {
            Propiedad prop = new Propiedad();
            prop = propiedadServicio.obtenerPorMatricula(propiedad.getPrdMatricula());
            if (prop.getPrdAlicuota().compareTo(BigDecimal.ONE) == 0) {
                prop.setPrdValidacion("V");
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Unidad de Vivienda guardada correctamente", "");
                FacesContext.getCurrentInstance().addMessage("", facesMsg);
            } else {
                prop.setPrdValidacion(null);
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Revise las alicuotas (el total de alicuotas para la propiedad debe ser igual a 1)", "");
                FacesContext.getCurrentInstance().addMessage("", facesMsg);
            }
            propiedadServicio.edit(prop);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * ******************************* REVOCATORIA
     * ****************************************
     */
    public void propiedadesVendidas() throws ServicioExcepcion {
        inicializarPropiedad();
        List<Propiedad> propHijas = new ArrayList<>();
        propHijas = propiedadServicio.listarPropiedadesVendidaPorMatriculaPadre(propiedadNueva.getPrdMatricula());
        if (propHijas.isEmpty()) {
            renderedMensaje = false;
            disabledBtnProcRevoc = false;
            mensajeRev = "";

        } else {
            renderedMensaje = true;
            disabledBtnProcRevoc = true;
            mensajeRev = "Ya existen Propiedades Vendidas";
        }
        renderedPanelDeclaratoria = false;
        renderedPanelRevocatoria = true;
        listaPropiedadHija.clear();
        listaPropiedadHija = propiedadServicio.listarPorMatriculaPadre(matricula);
        propiedadPadreMostrar = propiedadServicio.obtenerPorMatricula(matricula);
    }

    public void procesarRevocatoria() throws ServicioExcepcion {
        try {
            List<Propiedad> propHijasV = new ArrayList<>();
            propHijasV = propiedadServicio.listarPropiedadesVendidaPorMatriculaPadre(propiedadNueva.getPrdMatricula());
            if (propHijasV.isEmpty()) {
                //CAMBIAR ESTADO PROPIEDAD PADRE
                propiedadNueva.setPrdEstadoRegistro("A");
                propiedadServicio.edit(propiedadNueva);

                //CAMBIAR ESTADO PROPIEDADES HIJAS
                List<Propiedad> propHijas = new ArrayList<>();
                propHijas = propiedadServicio.listarPorMatriculaPadre(propiedadNueva.getPrdMatricula());
                for (Propiedad propHija : propHijas) {
                    propHija.setPrdEstadoRegistro("I");
                    propiedadServicio.edit(propHija);
                }
                //CAMBIAR ESTADO ALICUOTAS 
                List<Alicuota> listAlicuota = new ArrayList<>();
                for (Propiedad propHija : propHijas) {
                    listAlicuota.clear();
                    listAlicuota = alicuotaServicio.listarPorMatricula(propHija.getPrdMatricula());
                    for (Alicuota alicuota1 : listAlicuota) {
                        alicuota1.setAltEstado("I");
                        alicuotaServicio.edit(alicuota1);
                    }

                }

                //CAMBIAR ESTADO TMPALICUOTA
                List<TmpAlicuota> listTmpAlicuota = new ArrayList<>();
                listTmpAlicuota = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoDistintoE(propiedadNueva.getPrdMatricula());
                for (TmpAlicuota tmpAlicuota1 : listTmpAlicuota) {
                    tmpAlicuota1.setTalEstado("E"); //E: ELIMINADA
                    tmpAlicuotaDao.edit(tmpAlicuota1);
                }

                renderedMensaje = false;
                disabledBtnProcRevoc = true;
                //ACTUALIZAR TRAMITE VALOR ACCION (REVISADO-NO REVISADO)
                int tramValorAccion = 0;
                if (tramiteValorSeleccionado.getTrvAccion() != null) {
                    tramValorAccion = tramiteValorSeleccionado.getTrvAccion() + 1;
                } else {
                    tramValorAccion = 1;
                }
                Tramite tramiteSeleccionado = repertorioSeleccionado.getTraNumero();
                TramiteDetalle tramDet = servicioTramiteDetalle.buscarPorNumTramite(tramiteSeleccionado.getTraNumero());
                TipoTramite tipoTramit = servicioTipoTramite.buscarID(tramDet.getTdtTtrId().intValue());
                tramiteValorSeleccionado.setTrvAccion(tramValorAccion);
                servicioTramiteValor.edit(tramiteValorSeleccionado);
                listaTramiteValor = servicioTramiteValor.ListarPor_TipoTramite_NumTramite_Estado_CertificadoNulo(tramiteSeleccionado, tipoTramit);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * ********** ACLARATORIA *****************************
     * *******************************************************
     */
    public void aclaratoria(Propiedad propiedad) throws ServicioExcepcion {
        if (!propiedad.getPrdVendio().equals("S")) {
            //CAMBIAR ESTADO PROPIEDAD SELECCIONADA          
            propiedad.setPrdEstadoRegistro("I");
            propiedadServicio.edit(propiedad);

            //CAMBIAR ESTADO ALICUOTAS y tmpAlicuotas
            List<Alicuota> listAlicuota = new ArrayList<>();
            List<TmpAlicuota> listTmpAlic = new ArrayList<>();

            listAlicuota.clear();
            listAlicuota = alicuotaServicio.listarPorMatricula(propiedad.getPrdMatricula());
            for (Alicuota alicuota1 : listAlicuota) {
                listTmpAlic.clear();
                listTmpAlic = tmpAlicuotaDao.listarPorIdAlicuotaAsociada(alicuota1.getAltId());
                for (TmpAlicuota tmpAlicuota1 : listTmpAlic) {
                    tmpAlicuota1.setTalEstado("E");//E: ELIMINADA
                    tmpAlicuotaDao.edit(tmpAlicuota1);
                }
                alicuota1.setAltEstado("I");
                alicuotaServicio.edit(alicuota1);
            }
            listaPropiedadHija.clear();
            listaPropiedadHija = propiedadServicio.listarPorMatriculaPadre(matricula);
            listaPropiedadHijaUV.clear();
            listaPropiedadHijaUV = propiedadServicio.listarPorMatriculaPadre_PrdAgregado(matricula);
            indiceTab = 6;

        } else {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad vendida. No se puede eliminar", "");
            FacesContext.getCurrentInstance().addMessage("", facesMsg);
        }
    }

    /**
     * *******************************************************
     */
    public void sumarAlicuotaPropiedades() {
        totalAlicuotasPropiedad = BigDecimal.ZERO;
        try {
            for (Propiedad propiedad : listaPropiedadHija) {
                if (propiedad.getPrdAlicuota() != null) {
                    totalAlicuotasPropiedad = totalAlicuotasPropiedad.add(propiedad.getPrdAlicuota());
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void sumarAlicuotaTmpAlicuota() {
        totalAlicuotasTmpAlic = BigDecimal.ZERO;
        try {
            for (TmpAlicuota listaTmpAlicuota : listaTmpAlicuotas) {
                if (listaTmpAlicuota.getTalAlicuota() != null) {
                    totalAlicuotasTmpAlic = totalAlicuotasTmpAlic.add(listaTmpAlicuota.getTalAlicuota());
                }
            }
            if (totalAlicuotasTmpAlic.compareTo(BigDecimal.valueOf(100)) == 0) {
                disabledTabUndViv = false;
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Total de alicuotas validadas correctamente", "");
                FacesContext.getCurrentInstance().addMessage("", facesMsg);
            } else {
                disabledTabUndViv = true;
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Para crear Unidades de Vivienda el total de alicuotas debe ser 100.", "");
                FacesContext.getCurrentInstance().addMessage("", facesMsg);
            }
            indiceTab = 3;

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * **************************************************************
     */
    /**
     * ****************** GEOLOCALIZACION **************************
     */
    public void verificarPosicion() throws IOException {
        if (!propiedadNueva.getPrdTdtParNombreH().isEmpty()) {
            localizarPorParroquia();
        }
        if (propiedadNueva.getPrdEspacial().equals("") || propiedadNueva.getPrdEspacial().equals("0")) {

        } else {
            setGeoModel(new DefaultMapModel());
            LatLng latlng = new LatLng(Double.parseDouble(propiedadNueva.getPrdLatitud()), Double.parseDouble(propiedadNueva.getPrdLongitud()));
            Marker point = new Marker(latlng, propiedadNueva.getPrdMatricula().toString());
            geoModel.addOverlay(point);
            setCenterGeoMap(latlng.getLat() + "," + latlng.getLng());
            setZoom(17);
        }
    }

    public void onZoomAction(StateChangeEvent event) {
        setZoom(event.getZoomLevel());
    }

    public void onPointSelect(PointSelectEvent event) {
        setGeoModel(new DefaultMapModel());
        LatLng latlng = event.getLatLng();
        Marker point = new Marker(latlng);
        geoModel.addOverlay(point);
        setCenterGeoMap(latlng.getLat() + "," + latlng.getLng());
        setLat(latlng.getLat());
        setLng(latlng.getLng());
    }

    public void localizarPorParroquia() throws IOException {
        try {
            char espacio = ' ';
            char reemplazo = '%';
            String replace = propiedadNueva.getPrdTdtParNombreH().replace(espacio, reemplazo);

            setUrl("https://maps.googleapis.com/maps/api/geocode/json?address=Ecuador," + replace + "&key=AIzaSyC7R9us6s7z77X-uy5zaAc6DzUM6u0-7c4");
            URL dir = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) dir.openConnection();

            int responseCode = conn.getResponseCode();
            System.out.print(responseCode);
            if (responseCode == 0) {

            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json1 = new JSONObject(response.toString());
                JSONArray jsonArray = json1.getJSONArray("results");
                JSONObject json2 = jsonArray.getJSONObject(0);
                JSONObject json3 = json2.getJSONObject("geometry");
                JSONObject jsonLocation = json3.getJSONObject("location");
                setCenterGeoMap(jsonLocation.getDouble("lat") + "," + jsonLocation.getDouble("lng"));
                setZoom(15);
                System.out.print(jsonLocation);
            }

        } catch (MalformedURLException | JSONException e) {
            JsfUtil.addErrorMessage("Error con JSON");
        }

    }

    public void localizarPorDireccion() throws IOException {
        try {
            String reemplazo = "%20";
            String replace = direccion.replaceAll("\\s", reemplazo);

            setUrl("https://maps.googleapis.com/maps/api/geocode/json?address=" + replace + ",Ecuador&key=AIzaSyC7R9us6s7z77X-uy5zaAc6DzUM6u0-7c4");
            URL dir = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) dir.openConnection();

            int responseCode = conn.getResponseCode();
            System.out.print(responseCode);
            if (responseCode == 400) {
                JsfUtil.addErrorMessage("Url inválido");
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject json1 = new JSONObject(response.toString());
                JSONArray jsonArray = json1.getJSONArray("results");
                JSONObject json2 = jsonArray.getJSONObject(0);
                JSONObject json3 = json2.getJSONObject("geometry");
                JSONObject jsonLocation = json3.getJSONObject("location");
                setCenterGeoMap(jsonLocation.getDouble("lat") + "," + jsonLocation.getDouble("lng"));
                setZoom(17);

                geoModel = new DefaultMapModel();
                LatLng latlng = new LatLng(jsonLocation.getDouble("lat"), jsonLocation.getDouble("lng"));
                Marker point = new Marker(latlng);
                geoModel.addOverlay(point);
                setLat(jsonLocation.getDouble("lat"));
                setLng(jsonLocation.getDouble("lng"));
                direccion = "";
                System.out.print(jsonLocation);
            }

        } catch (MalformedURLException | JSONException e) {
            JsfUtil.addWarningMessage("Dirección no encontrada");
        }

    }

    public void guadarLocalizacion() {
        if (lat != null && lng != null) {
            getPropiedadNueva().setPrdEspacial(lat + "," + lng);
            getPropiedadNueva().setPrdLatitud(String.valueOf(lat));
            getPropiedadNueva().setPrdLongitud(String.valueOf(lng));
        } else {
            JsfUtil.addWarningMessage("Seleccione una ubicación");
        }

    }

}
