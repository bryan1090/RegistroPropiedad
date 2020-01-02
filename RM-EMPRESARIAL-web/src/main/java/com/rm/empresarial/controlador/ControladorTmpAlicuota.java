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
import static com.rm.empresarial.controlador.util.VerificarReCaptcha.url;
import com.rm.empresarial.dao.BloqueDao;
import com.rm.empresarial.dao.CompaniaDao;
import com.rm.empresarial.dao.ConfigDetalleDao;
import com.rm.empresarial.dao.MatriculacionDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.RepresentanteDao;
import com.rm.empresarial.dao.TipoPropiedadDao;
import com.rm.empresarial.dao.TmpAlicuotaDao;
import com.rm.empresarial.dao.UnidMedidaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Alicuota;
import com.rm.empresarial.modelo.Bloque;
import com.rm.empresarial.modelo.Compania;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Representante;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoPropiedad;
import com.rm.empresarial.modelo.TipoPropiedadClase;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TmpAlicuota;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.UnidMedida;
import com.rm.empresarial.servicio.AlicuotaServicio;
import com.rm.empresarial.servicio.CompaniaServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.LinderoServicio;
import com.rm.empresarial.servicio.NuevasMatriculasServicio;
import com.rm.empresarial.servicio.ParroquiaServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.PropiedadDetalleServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.PropietarioServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoPropiedadClaseServicio;
import com.rm.empresarial.servicio.TipoPropiedadServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import com.rm.empresarial.servicio.UnidMedidaServicio;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
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
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author Java
 */
@Named(value = "controladorTmpAlicuota")
@ViewScoped
public class ControladorTmpAlicuota implements Serializable {

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Getter
    @Setter
    private List<TmpAlicuota> listaTmpAlicuota;
    @Getter
    @Setter
    private List<linderosExcel> ListalinderosExcel = new ArrayList<>();
    @Getter
    @Setter
    private List<Bloque> ListaBloqueExcel = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadHija = new ArrayList<>();

    @Getter
    @Setter
    List<String> listaErrores;

    @EJB
    private TipoPropiedadServicio servicioTipoPropiedad;

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
    private CompaniaDao companiaDao;

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
    TipoPropiedadClaseServicio servicioTipoPropiedadClase;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    private TmpAlicuotaDao tmpAlicuotaDao;

    @EJB
    private UnidMedidaServicio servicioUnidadMedida;
    @EJB
    private UnidMedidaDao unidMedidaDao;

    @EJB
    private AlicuotaServicio alicuotaServicio;

    @EJB
    private PersonaServicio personaServicio;

    @EJB
    private TipoPropiedadDao tipoPropiedadDao;

    @EJB
    private PropietarioServicio propietarioServicio;

    @EJB
    private RepertorioUsuarioServicio repertorioUsuarioServicio;

    @EJB
    private CompaniaServicio companiaServicio;

    @EJB
    RepresentanteDao representanteDao;

    @EJB
    PropiedadDao propiedadDao;

    @EJB
    ConfigDetalleDao configDetalleDao;

    @EJB
    BloqueDao bloqueDao;

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
    private String mensajeRev;
    @Getter
    @Setter
    private Boolean renderedMensaje = false;
    @Getter
    @Setter
    private Boolean disabledBtnProcRevoc = true;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Getter
    @Setter
    private Boolean renderedPanelPropiedad = false;
    @Getter
    @Setter
    private Boolean renderedPanelPropiedadRev = false;
    @Getter
    @Setter
    private Boolean rendBtnAclaratoriaDescargar = false;
    @Getter
    @Setter
    private Boolean renderedPanelDeclaratoria = false;
    @Getter
    @Setter
    private Boolean renderedPanelRevocatoria = false;
    @Getter
    @Setter
    private Double lat;

    @Getter
    @Setter
    private Double lng;
    @Getter
    @Setter
    private String url;

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
    private Propiedad propiedadPadreMostrar = new Propiedad();

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
    private List<TipoPropiedad> listaTipoProp = new ArrayList<>();
    @Getter
    @Setter
    private List<Compania> listaCompania = new ArrayList<>();

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
    private List<Persona> listaPersonaCompania = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadHijaGrid = new ArrayList<>();
    @Getter
    @Setter
    private List<Compania> listaCompaniaExcel = new ArrayList<>();
    @Getter
    @Setter
    private List<Parroquia> listaParriquias = new ArrayList<>();

    @Getter
    @Setter
    private List<UnidMedida> listUnMed = new ArrayList<>();

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
    private InputStream is;
    @Getter
    @Setter
    private InputStream inpStr;
    @Getter
    @Setter
    private String matriculalPadre;
    @Getter
    @Setter
    private Long numRepertorio;
    @Getter
    @Setter
    private String nombreComp;
    @Getter
    @Setter
    private String construcComp;

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
    private Propiedad propiedadHija = new Propiedad();
    @Getter
    @Setter
    private String estadoPropiedad;
    @Getter
    @Setter
    private Lindero linderoConsultado;
    @Getter
    @Setter
    private Propiedad propUV = new Propiedad();

    @Getter
    @Setter
    private Integer numDocumento;
    @Getter
    @Setter
    private StreamedContent excelErrores;

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
    private Propiedad propiedadSelec;

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
    private List<Lindero> listaLinderos = new ArrayList<>();

    @Getter
    @Setter
    private List<Lindero> listaLinderosH;

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
    private TramiteValor tramiteValorSeleccionado = new TramiteValor();
    @Getter
    @Setter
    private TramiteValor nuevoTramiteValor = new TramiteValor();

    
    @Getter
    @Setter
    private Propiedad propiedadHijaSeleccionada;

    @Getter
    @Setter
    private Propiedad propiedadPadre = new Propiedad();

    @Getter
    @Setter
    private Alicuota alicuota;

    @Getter
    @Setter
    private Persona personaCompania = new Persona();

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleSeleccionado;

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
    private Boolean renderedPanelError = false;
    @Getter
    @Setter
    private Boolean renderedPanelExcel = false;

    @Getter
    @Setter
    private Boolean boolSeleccionadaPropPrincipalUnif = false;
    @Getter
    @Setter
    private StreamedContent file;
    @Getter
    @Setter
    private StreamedContent fileExcelAclar;
    @Getter
    @Setter
    private FechasUtil fechasUtil;
    @Getter
    @Setter
    private Path direccion;
    @Getter
    @Setter
    private String numMatriculaHija;
    @Getter
    @Setter
    private String centerGeoMap;
    @Getter
    @Setter
    private String direccionGeolocalizacion;
    @Getter
    @Setter
    private MapModel geoModel;
    @Getter
    @Setter
    private int zoom;

    public ControladorTmpAlicuota() {
        listaErrores = new ArrayList<>();
        centerGeoMap = "-0.2029,-78.4911";
        geoModel = new DefaultMapModel();
        zoom = 15;
    }

    @PostConstruct
    public void inicializar() {
        try {
            listaErrores = new ArrayList<>();
            listaTmpAlicuota = new ArrayList<>();

            Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            numRepertorio = new Long(params.get("paramRepertorio2").toString());
            repertorioSeleccionado = servicioRepertorio.find(new Repertorio(), numRepertorio);

            cargarCombos();
            cargarDatosSeleccion();
            listUnMed = servicioUnidadMedida.listarTodo();
            listaTipoProp = NuevasMatriculasServicio.listaTipoPropiedad();
            listaCompaniaExcel = companiaDao.listarTodo();
            descargarPlantillaPH();
            inicializarPropiedad();
            /**
             * ***
             */
            Tramite tramiteSeleccionado = repertorioSeleccionado.getTraNumero();
            TramiteDetalle tramDet = servicioTramiteDetalle.buscarPorNumTramite(tramiteSeleccionado.getTraNumero());
            TipoTramite tipoTramit = servicioTipoTramite.buscarID(tramDet.getTdtTtrId().intValue());
            if (tipoTramit.getTtrCodigo() == 9) {
                renderedPanelPropiedad = false;
                renderedPanelPropiedadRev = true;
                rendBtnAclaratoriaDescargar = false;
            } else {
                renderedPanelPropiedad = true;
                renderedPanelPropiedadRev = false;
                rendBtnAclaratoriaDescargar = false;
            }
            //ACLARATORIA
            if (tipoTramit.getTtrCodigo() == 10) {
                rendBtnAclaratoriaDescargar = true;
            }
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorTmpAlicuota.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void descargarPlantillaPH() {
        try {
            llenarHojasExcelDeTipos();
            InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/plantillaExcel/PH_PLANTILLA.xlsx");

            file = new DefaultStreamedContent(stream, "application/vnd.ms-excel", "PH_PLANTILLA.xlsx");

//                    InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/resources/plantillaExcel/p.jpg");
//        file = new DefaultStreamedContent(stream, "image/jpg", "downloaded_boromir.jpg");
        } catch (Exception e) {
            System.out.println(e);
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

    public void cargarDatosSeleccion() {
        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
        TipoTramite tipoTramiteSelec = servicioTipoTramite.find(new TipoTramite(), new Long(repertorioSeleccionado.getRepTtrId()));

        try {
            listaTramiteValor = servicioTramiteValor.ListarPor_TipoTramite_NumTramite_Estado_CertificadoNulo(tramiteSelec, tipoTramiteSelec);
            tramiteValorSeleccionado = listaTramiteValor.get(0);
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
            /**
             * ****
             */
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

    public void redireccionarPaginaSeleccion() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MatriculacionPH/Matriculacion.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(ControladorNuevasMatriculasPH.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void inicializarPropiedad() throws ServicioExcepcion {
        Propiedad propiedadCargada = null;
        catastro = tramiteValorSeleccionado.getTrvNumCatastro();
        predio = tramiteValorSeleccionado.getTraNumPredio();

        try {
            propiedadCargada = NuevasMatriculasServicio.buscarPropiedadConCatastroPredio(catastro, predio);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
//SI NO ENCONTRÓ LA PROPIEDAD
        if (propiedadCargada == null) {
            //-- campos de Tramite Valor
            propiedadNueva = new Propiedad();
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
            JsfUtil.addSuccessMessage("Propiedad Nueva");
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
            JsfUtil.addSuccessMessage("Propiedad Cargada para actualizar");

            try {
                listaLinderos.clear();
                listaLinderos = servicioLindero.listarPorNumMatricula(getPropiedadNueva().getPrdMatricula());

            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }

        }
    }

    public void seleccionarParroquiaHistorica() {

        Parroquia parroquiaHistorica = servicioParroquia.find(new Parroquia(), getPropiedadNueva().getPrdTdtParIdH());
        getPropiedadNueva().setPrdTdtParNombreH(parroquiaHistorica.getParNombre());

        getPropiedadNueva().setPrdTdtParNombre(parroquiaHistorica.getParNombre());
        getPropiedadNueva().setPrdTdtParId(parroquiaHistorica.getParId());
        llenarDescripcion2();

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

        String texto = "";
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
                texto = tipoPropiedad + " " + getPropiedadNueva().getPrdUnidadVivienda() + " situado en la Parroquia " + parroquia + " de este Cantón";

            }

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        getPropiedadNueva().setPrdDescripcion2(texto);
    }

    public void cargarCombos() {
        try {
            //consulto propiedad para editar//
//            setPropiedadNueva(NuevasMatriculasServicio.obtenerPropiedad(getCatastro(), getPredio()));
//            tramiteDetalleSelec = servicioTramiteDetalle.buscarPorNumRepertorio(superficie);
            setListaTipoPropiedad(NuevasMatriculasServicio.listaTipoPropiedad());
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
            listaCompaniaExcel = companiaServicio.listarTodo();

//            setEstadoPropiedad(getPropiedadNueva().getPrdEstadoPropiedad());
        } catch (Exception e) {
            System.out.print("FALLO AL CONSULTAR");
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

    public String generarMatricula() throws ServicioExcepcion {

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

        return idGenerado;

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

    public void listarParroquias() {
        listaParriquias = servicioParroquia.listarTodo();
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
            String idGenerado = secuenciaControlador.getControladorBb().getNumeroMatricula();
//        setMatricula(idGenerado);
            secuenciaServicio.guardar(getSecuencia());

            Compania compania = companiaServicio.buscarPorIdCompania(propiedadNueva.getPrdConjuntoId());
            propiedadNueva.setPrdConjunto(compania.getComNombre());

            if (esPropiedadNueva) {
                if (!propiedadNueva.getPrdDescripcion2().trim().isEmpty()) {
                    if (listaLinderos.size() >= 2) {
                        try {
                            propiedadNueva.setPrdMatricula(idGenerado);
                            propiedadNueva.setPrdUserCreador(" ");
                            //propiedadNueva.setPrdClasePropiedad(" ");
                            propiedadNueva.setPrdEstadoRegistro(" ");
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

                            propiedadDao.create(propiedadNueva);
                            
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
//                        context.addMessage(null, new FacesMessage("Linderos agregados", ""));
                        } catch (Exception e) {
                            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Linderos no creados ", ""));
                        }

                        tramiteUtil.insertarTramiteAccion(repertorioSeleccionado.getTraNumero(),
                                propiedadNueva.getPrdMatricula().toString(),
                                "Propiedad creada con matricula "
                                + propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
                                + " y predio " + propiedadNueva.getPrdPredio(),
                                repertorioSeleccionado.getTraNumero().getTraEstado(),
                                repertorioSeleccionado.getTraNumero().getTraEstadoRegistro(),
                                null);

                        indiceTab = 2;
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
                        propiedadDao.edit(propiedadNueva);
                        tramiteValorSeleccionado.setTrvParId(propiedadNueva.getPrdTdtParId());
                        tramiteValorSeleccionado.setTrvParNombre(propiedadNueva.getPrdTdtParNombre());
                        servicioTramiteValor.edit(tramiteValorSeleccionado);
//                    context.addMessage(null, new FacesMessage("Propiedad Actualizada", ""));
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
//                    context.addMessage(null, new FacesMessage("Linderos Actualizados", ""));
                        indiceTab = 1;
                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La propiedad debe tener mínimo 2 linderos.", ""));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Descripción de la Propiedad", ""));
                }

            }
            try {
                listaPropiedadHija = propiedadServicio.listarPorMatriculaPadre(propiedadNueva.getPrdMatricula());
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
        esPropiedadNueva = false;
        //mostrarTabView = false;
        disabledTab = false;
        propiedadPadre = propiedadServicio.obtenerPorMatricula(matricula);
        propiedadPadreMostrar = propiedadServicio.obtenerPorMatricula(matricula);
    }

    public void guardarRepertorioPropiedad() {
        //GUARDANDO REPERTORIO PROPIEDAD
        if (!servicioRepertorioPropiedad.existeRepertorioPropiedad(repertorioSeleccionado.getRepNumero(), propiedadNueva.getPrdMatricula())) {
            RepertorioPropiedad nuevoRepPro = new RepertorioPropiedad();
            nuevoRepPro.setPrdMatricula(propiedadNueva);
            nuevoRepPro.setRepNumero(repertorioSeleccionado);
            nuevoRepPro.setRpdFHR(Calendar.getInstance().getTime());
            nuevoRepPro.setRpdUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
            servicioRepertorioPropiedad.create(nuevoRepPro);
            tramiteUtil.insertarTramiteAccion(nuevoRepPro.getRepNumero().getTraNumero(),
                    nuevoRepPro.getRepNumero().getRepNumero().toString(),
                    "Asignado Repertorio " + nuevoRepPro.getRpdId() + " a la Propiedad con matricula "
                    + propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
                    + " y predio " + propiedadNueva.getPrdPredio(),
                    nuevoRepPro.getRepNumero().getTraNumero().getTraEstado(),
                    nuevoRepPro.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                    null);
        }
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
                    if (trmVal.getTrvAccion() > 0) {
                        contador++;
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

    public void guardarCompania() throws ServicioExcepcion {
        nuevaCompania.setComCodigo("0");
        nuevaCompania.setComRevocatoria(Short.valueOf("0"));
        nuevaCompania.setComAclaratoria(Short.valueOf("0"));
        nuevaCompania.setComUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
        nuevaCompania.setComFHR(Calendar.getInstance().getTime());
        nuevaCompania.setParId(parroquiaCompania);
        companiaServicio.create(nuevaCompania);
        listaCompaniaExcel = companiaServicio.listarTodo();
        FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Compañia Creada", "");
        FacesContext.getCurrentInstance().addMessage("", facesMsg);
        nuevaCompania = new Compania();
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

    public void guardarPropiedadHija() {
        System.out.println("mat padre " + propiedadNueva.getPrdMatricula());
        FacesContext context = FacesContext.getCurrentInstance();

        //marcarComoRevisado();
        //actualizarEstProcRepUsu();
        try {
            if (!propiedadNueva.getPrdDescripcion2().trim().isEmpty()) {
                if (listaLinderos.size() >= 2) {
                    try {

                        propiedadNueva.setPrdUserCreador(" ");
                        propiedadNueva.setPrdClasePropiedad(" ");
                        propiedadNueva.setPrdEstadoRegistro("A");
                        propiedadNueva.setPrdSector(" ");

                        propiedadNueva.setPrdFHR(Calendar.getInstance().getTime());
                        propiedadNueva.setPrdUserModificacion(" ");
                        propiedadNueva.setPrdUbicacion(" ");
                        propiedadNueva.setPrdVendio("N");
                        propiedadNueva.setPrdPH("S");

                        TipoPropiedadClase tipoPropiedadClasePorDefecto;
                        tipoPropiedadClasePorDefecto = servicioTipoPropiedadClase.find(new TipoPropiedadClase(), "NIN");
                        propiedadNueva.setTclId(tipoPropiedadClasePorDefecto);
                        if (propiedadNueva.getPrdCatastro().length() == 0 || propiedadNueva.getPrdCatastro().length() > 100) {
                            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El tamaño del campo Catastro debe ser entre 1 y 100", ""));
                        } else {

                        }

                        propiedadDao.create(propiedadNueva);
//                    context.addMessage(null, new FacesMessage("Propiedad Creada", ""));

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
                            repertorioSeleccionado.getTraNumero().getTraEstado(),
                            repertorioSeleccionado.getTraNumero().getTraEstadoRegistro(),
                            null);
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La propiedad debe tener mínimo 2 linderos.", ""));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Descripción de la Propiedad", ""));
            }

            secuenciaServicio.guardar(getSecuencia());
            listarPropiedadesHijas();

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad No Ingresada", "");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
        try {
            guardarRepertorioPropiedad();

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        //mostrarTabView = false;
        //disabledTab = false;

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

    //METODOS PROPIOS
    public void subirArchivo(FileUploadEvent event) {
        try {
            ListaBloqueExcel.clear();
            ListalinderosExcel.clear();
            listaTmpAlicuota = new ArrayList<>();
            Boolean error = false;
            listaErrores = new ArrayList<>();
            String auxPrincipal = "";
            String strLindero1 = "";
            String strLindero2 = "";
            String strLindero3 = "";
            String strLindero4 = "";
            String strLindero5 = "";
            String strLindero6 = "";
            String strLindero7 = "";
            String strLindero8 = "";
            String talNombreUnidad = "";
            if (event.getFile() != null) {
                is = event.getFile().getInputstream();
                inpStr = event.getFile().getInputstream();

                Workbook workbook = WorkbookFactory.create(is);
                Sheet sheet = workbook.getSheetAt(0);

                Cell celdaActual;
                // Formatea para recibir todo string
                DataFormatter formatter = new DataFormatter(Locale.US);

                Boolean leerSiguiente = true;
                for (int fila = 2; fila <= sheet.getLastRowNum(); fila++) {
                    Row row = sheet.getRow(fila);
                    if (row == null) {
                        // empty row, skip
                    } else {
                        if (leerSiguiente == true) {
                            celdaActual = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            auxPrincipal = formatter.formatCellValue(celdaActual);
                            Boolean campoExcVacio = esCampoVacio(celdaActual);
                            if (campoExcVacio == true) {
                                leerSiguiente = false;
                                listaErrores.add("Error. Campo vacío. Celda: " + celdaActual.getAddress());
                            }
                        }
                        if (leerSiguiente == true) {
//                        switch(campoExcVacio.toString()){
//                            case "false":

// talnumero (SECUENCIAL)
                            celdaActual = row.getCell(6, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String TalNumero = formatter.formatCellValue(celdaActual);
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add("Error campo vacío. Celda: " + celdaActual.getAddress());
                            }
// tal alicuota
                            celdaActual = row.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String TalAlicuotaString = formatter.formatCellValue(celdaActual).replace(",", ".");
                            BigDecimal TalAlicuota = new BigDecimal("0");
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add("Error campo vacío. Celda: " + celdaActual.getAddress());
                            }
                            if (ValidarNumeros(TalAlicuotaString)) {
                                listaErrores.add("Error no se puede convertir la alicuota en numerico. Celda: " + celdaActual.getAddress());
//                            addErrorMessage("Error no se puede convertir la alicuota en numerico. Celda: " + celdaActual.getAddress());
                            } else {
                                TalAlicuota = new BigDecimal(formatter.formatCellValue(celdaActual).replace(",", "."));
                            }
// talarea(bloque)
                            celdaActual = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String TalArea = formatter.formatCellValue(celdaActual);
                            if (esCampoVacio(celdaActual)) {
                                celdaActual.setCellType(CellType.BLANK);
                                listaErrores.add("Error campo vacío. Celda: " + row.getCell(3).getAddress());
                            }
// codigo tipo (Bloque)
                            celdaActual = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String codigoTipoStr = formatter.formatCellValue(celdaActual).replace(",", "");
                            String tipoBloq = "";
                            String subTipoBloqString = "";
                            //String TalSuperficieString = formatter.formatCellValue(celdaActual);
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add("Error campo vacío. Celda: " + celdaActual.getAddress());
                            }

                            if (ValidarNumeros(codigoTipoStr)) {
                                listaErrores.add("Error no se puede convertir el Código Tipo en numerico (Ingrese 01, 02, 03 o 04). Celda: " + celdaActual.getAddress());

                            } else {
                                //codTipo = new BigDecimal(formatter.formatCellValue(row.getCell(8)).replace(",", "."));
                                if (ValidarCodigoTipo(codigoTipoStr)) {
                                    listaErrores.add("Error. El Código Tipo debe ser 01, 02, 03 o 04 . Celda: " + celdaActual.getAddress());
                                } else {
                                    // SubTipo Bloque
                                    celdaActual = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

                                    switch (codigoTipoStr) {
                                        case "01":
                                            tipoBloq = "CONJUNTO HABITACIONAL";
                                            if (esCampoVacio(celdaActual)) {
                                                listaErrores.add("Error campo vacío. Celda: " + celdaActual.getAddress());

                                            } else {
                                                subTipoBloqString = formatter.formatCellValue(celdaActual);
                                                if (!subTipoBloqString.equals("EDIFICIO") && !subTipoBloqString.equals("CASA")) {
                                                    listaErrores.add("Error. El SubTipo debe ser EDIFICIO o CASA: " + celdaActual.getAddress());
                                                }
                                                if (subTipoBloqString.equals("CASA")) {
                                                    TalArea = "CASA";
                                                }
                                            }
                                            break;
                                        case "02":
                                            tipoBloq = "EDIFICIOS";
                                            subTipoBloqString = " ";
                                            break;

                                        case "03":;
                                            tipoBloq = "ESTADIOS";
                                            subTipoBloqString = " ";
                                            break;
                                        case "04":
                                            tipoBloq = "CENTROS COMERCIALES";
                                            subTipoBloqString = " ";
                                            break;
                                    }

                                }
                            }

// talpiso
                            celdaActual = row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String TalPiso = formatter.formatCellValue(celdaActual);
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add("Error campo vacío. Celda: " + celdaActual.getAddress());
                            }

// talTipoPropiedad(Nombre)
                            celdaActual = row.getCell(8, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add("Error campo vacío. Celda: " + celdaActual.getAddress());
                            }
                            String talNombreTipoPropiedad = formatter.formatCellValue(celdaActual);

// talCatastro
                            celdaActual = row.getCell(12, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String talCatastroString = formatter.formatCellValue(celdaActual);
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add("Error, campo Catastro vacío. Celda: " + celdaActual.getAddress());

                                //listaErrores.add(mensajeError.Error_vacio.toString() + celdaActual.getAddress());
                            }
                            BigInteger talCatastro = null;
                            if (ValidarNumeros(talCatastroString)) {
                                addErrorMessage(mensajeError.Error_numero.toString() + celdaActual.getAddress());
                            } else {
                                talCatastro = new BigInteger(formatter.formatCellValue(celdaActual).replace(",", "."));
                            }
// talPredio
                            celdaActual = row.getCell(13, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String talPredioString = formatter.formatCellValue(celdaActual);
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add("Error, campo  Predio vacío. Celda: " + celdaActual.getAddress());

                                //listaErrores.add(mensajeError.Error_vacio.toString() + celdaActual.getAddress());
                            }
                            BigInteger talPredio = null;
                            if (ValidarNumeros(talPredioString)) {
                                addErrorMessage(mensajeError.Error_numero.toString() + celdaActual.getAddress());
                            } else {
                                talPredio = new BigInteger(formatter.formatCellValue(celdaActual).replace(",", "."));
                            }
                            if (!talCatastroString.equals("0") && !talPredioString.equals("0")) {
// talnombreunidad
                                celdaActual = row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                if (esCampoVacio(celdaActual)) {
                                    listaErrores.add("Error campo vacío. Celda: " + celdaActual.getAddress());
                                }
                                talNombreUnidad = formatter.formatCellValue(celdaActual);
// Lindero 1
                                celdaActual = row.getCell(14, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                if (esCampoVacio(celdaActual)) {
                                    listaErrores.add("Error campo Lindero 1 vacío. Celda: " + celdaActual.getAddress());
                                }
                                strLindero1 = formatter.formatCellValue(celdaActual);
// Lindero 2
                                celdaActual = row.getCell(15, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                if (esCampoVacio(celdaActual)) {
                                    listaErrores.add("Error campo Lindero 2 vacío. Celda: " + celdaActual.getAddress());
                                }
                                strLindero2 = formatter.formatCellValue(celdaActual);
// Lindero 3
                                celdaActual = row.getCell(16, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                if (esCampoVacio(celdaActual)) {
                                    listaErrores.add("Error campo Lindero 3 vacío. Celda: " + celdaActual.getAddress());
                                }
                                strLindero3 = formatter.formatCellValue(celdaActual);
// Lindero 4
                                celdaActual = row.getCell(17, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                if (esCampoVacio(celdaActual)) {
                                    listaErrores.add("Error campo Lindero 4 vacío. Celda: " + celdaActual.getAddress());
                                }
                                strLindero4 = formatter.formatCellValue(celdaActual);
// Lindero 5
                                celdaActual = row.getCell(18, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                strLindero5 = formatter.formatCellValue(celdaActual);
// Lindero 6
                                celdaActual = row.getCell(19, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                strLindero6 = formatter.formatCellValue(celdaActual);
// Lindero 7
                                celdaActual = row.getCell(20, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                strLindero7 = formatter.formatCellValue(celdaActual);
// Lindero 8
                                celdaActual = row.getCell(21, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                                strLindero8 = formatter.formatCellValue(celdaActual);
                            }
// superficie
                            celdaActual = row.getCell(10, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String TalSuperficieString = formatter.formatCellValue(celdaActual).replace(",", "");
                            //String TalSuperficieString = formatter.formatCellValue(celdaActual);
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add("Error campo vacío. Celda: " + celdaActual.getAddress());
                            }
                            BigDecimal TalSuperficie = new BigDecimal("0");
                            if (ValidarNumeros(TalSuperficieString)) {
                                addErrorMessage("Error no se puede convertir la superficie en numerico. Celda: " + celdaActual.getAddress());
                            } else {
                                TalSuperficie = new BigDecimal(formatter.formatCellValue(row.getCell(10)).replace(",", "."));
                            }

// unidadmedida
                            celdaActual = row.getCell(11, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String unidadMedida = formatter.formatCellValue(celdaActual);
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add(mensajeError.Error_vacio.toString() + celdaActual.getAddress());
                            }
                            UnidMedida unidadMedidaSel = servicioUnidadMedida.buscarUnidadMedidadPorCodigo(unidadMedida);
                            if (unidadMedidaSel == null) {
                                listaErrores.add("No existe Código de Unidad de Medida. Celda: " + celdaActual.getAddress());
                                //listaErrores.add(mensajeError.Error_clave_foranea_Unidad_Medida.toString() + celdaActual.getAddress());
                            }
// tipoPropiedad
                            celdaActual = row.getCell(7, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String tipoPropiedadString = formatter.formatCellValue(celdaActual);
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add(mensajeError.Error_vacio.toString() + celdaActual.getAddress());
                            }
                            TipoPropiedad tipoPropiedadSel = servicioTipoPropiedad.buscarPorCodigo(tipoPropiedadString);
                            if (tipoPropiedadSel == null) {
                                listaErrores.add("No existe Código de Tipo Propiedad. Celda: " + celdaActual.getAddress());
                                //listaErrores.add(mensajeError.Error_clave_foranea_TipoPropiedad.toString() + celdaActual.getAddress());
                            }
// Conjunto (Compañia)
//                        celdaActual = row.getCell(5, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                        String nombreConjuntoString = formatter.formatCellValue(celdaActual);
//                        if (esCampoVacio(celdaActual)) {
//                             listaErrores.add("Error, campo Conjunto vacío. Celda: " + celdaActual.getAddress());
//                        }
//                        if(!nombreConjuntoString.equals("") ){
//                            Compania compania = companiaDao.buscarCompaniaPorNombre(nombreConjuntoString);
//                        if (compania == null) {
//                            listaErrores.add("Error, compañía ingresada no existe. Celda: " + celdaActual.getAddress());                            
//                        }
//                            
//                        }

// talObservacion
                            celdaActual = row.getCell(22, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String talObservacion = formatter.formatCellValue(celdaActual);

// traAgrupacion 
//                        celdaActual = row.getCell(4, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
//                        String traAgrupacion = formatter.formatCellValue(celdaActual);
//                        if (esCampoVacio(celdaActual)) {
//                            listaErrores.add(mensajeError.Error_vacio.toString() + celdaActual.getAddress());
//                        }
// taPrincipal
                            celdaActual = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                            String taPrincipalStrng = formatter.formatCellValue(celdaActual);
                            auxPrincipal = taPrincipalStrng;
                            Short taPrincipal = Short.valueOf("0");
                            if (esCampoVacio(celdaActual)) {
                                listaErrores.add(mensajeError.Error_vacio.toString() + celdaActual.getAddress());
                            }

                            if (ValidarNumerosShort(taPrincipalStrng)) {
                                listaErrores.add("Error, el campo Principal debe ser numerico. Celda: " + celdaActual.getAddress());
                                //addErrorMessage(mensajeError.Error_numero.toString() + celdaActual.getAddress());
                            } else if (!taPrincipalStrng.equals("1") && !taPrincipalStrng.equals("0")) {
                                listaErrores.add("Error. El campo Principal debe contener 1 ó 0. Celda: " + celdaActual.getAddress());
                            } else {
                                taPrincipal = new Short(formatter.formatCellValue(celdaActual).replace(",", "."));
                            }

                            //Bloque
                            Bloque bloqExc = new Bloque(null,
                                    tipoBloq,
                                    codigoTipoStr,
                                    subTipoBloqString,
                                    TalArea,
                                    Calendar.getInstance().getTime(),
                                    DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            bloqExc.setPrdMatricula(propiedadPadre);
                            ListaBloqueExcel.add(bloqExc);
                            //Linderos

                            if (taPrincipal == 1) {
                                linderosExcel lindExc = new linderosExcel(
                                        talPredioString, talCatastroString,
                                        strLindero1, strLindero2,
                                        strLindero3, strLindero4);
                                if (!strLindero5.equals("")) {
                                    lindExc.setLindero5Excel(strLindero5);
                                }
                                if (!strLindero6.equals("")) {
                                    lindExc.setLindero6Excel(strLindero6);
                                }
                                if (!strLindero7.equals("")) {
                                    lindExc.setLindero7Excel(strLindero7);
                                }
                                if (!strLindero8.equals("")) {
                                    lindExc.setLindero8Excel(strLindero8);
                                }

                                ListalinderosExcel.add(lindExc);

                            }

                            //TmpAlicuota
                            Long conjuntoId = propiedadPadre.getPrdConjuntoId();
                            String conjunto = propiedadPadre.getPrdConjunto();
                            TmpAlicuota itemTmpAlicuota = new TmpAlicuota(
                                    null,
                                    TalNumero,
                                    TalAlicuota,
                                    DataManagerSesion.getUsuarioLogeado().getUsuLogin(),
                                    Calendar.getInstance().getTime(),
                                    "A",
                                    taPrincipal,
                                    conjunto,
                                    new Short("0"),
                                    " ");
                            itemTmpAlicuota.setTalArea(TalArea);
                            itemTmpAlicuota.setTalPiso(TalPiso);
                            itemTmpAlicuota.setTalSuperficie(TalSuperficie);
                            itemTmpAlicuota.setTalObservacion(talObservacion);
                            itemTmpAlicuota.setTalDescripcion(talNombreTipoPropiedad);
                            itemTmpAlicuota.setTalTipoMedidaCodigo(unidadMedida);
                            itemTmpAlicuota.setTalTipoPropiedadCodigo(tipoPropiedadString);
                            itemTmpAlicuota.setTraAgrupacion(" ");
                            itemTmpAlicuota.setTalPredio(talPredioString);
                            itemTmpAlicuota.setTalCatastro(talCatastroString);
                            itemTmpAlicuota.setTraConjuntoId(conjuntoId);
                            itemTmpAlicuota.setTalMatricula(propiedadPadre.getPrdMatricula());
                            if (itemTmpAlicuota.getTaPrincipal() == 1) {
                                itemTmpAlicuota.setTalObservacion(talNombreUnidad);
                            }
                            listaTmpAlicuota.add(itemTmpAlicuota);

//                    break;
                        }
                    }
                }

                if (listaErrores.isEmpty()) {
                    //VALIDAR QUE EXISTE UNICAMENTE UN CAMPO PRINCIPAL               
                    List<TmpAlicuota> tmpAlicCop = new ArrayList<>();
                    tmpAlicCop.addAll(listaTmpAlicuota);
                    int aux = 0;
                    for (TmpAlicuota tmpAlic1 : listaTmpAlicuota) {
                        aux = 0;
                        String predio1 = tmpAlic1.getTalPredio();
                        String catastro1 = tmpAlic1.getTalCatastro();
                        for (TmpAlicuota tmpAlic2 : tmpAlicCop) {
                            String predio2 = tmpAlic2.getTalPredio();
                            String catastro2 = tmpAlic2.getTalCatastro();
                            if (predio2.equals(predio1) && catastro2.equals(catastro1) && tmpAlic2.getTaPrincipal() == 1) {
                                aux++;
                            }
                        }
                        if (aux == 0) {
                            String errorLista = "Error. Para el predio: " + tmpAlic1.getTalPredio()
                                    + " y Catastro: " + tmpAlic1.getTalCatastro() + " debe existir un valor del campo Principal = 1";
                            if (!listaErrores.contains(errorLista)) {
                                listaErrores.add(errorLista);
                            }

                        }
                        if (aux > 1) {
                            String errorLista = "Error. Para el predio: " + tmpAlic1.getTalPredio()
                                    + " y Catastro: " + tmpAlic1.getTalCatastro() + " existe más de un valor del campo Principal = 1";
                            if (!listaErrores.contains(errorLista)) {
                                listaErrores.add(errorLista);
                            }
                        }
                    }

                    //VALIDAR ALICUOTA Y SUPERFICIE
                    BigDecimal validarTotalCien = BigDecimal.ZERO;
                    for (TmpAlicuota tmpAlic1 : listaTmpAlicuota) {
                        aux = 0;
                        BigDecimal totalAlicuota = BigDecimal.valueOf(0);
                        BigDecimal totalSuperficie = BigDecimal.valueOf(0);
                        BigDecimal superficiePrincipal = BigDecimal.valueOf(0);
                        BigDecimal alicuotaPrincipal = BigDecimal.valueOf(0);
                        String predio1 = tmpAlic1.getTalPredio();
                        String catastro1 = tmpAlic1.getTalCatastro();
                        Short principal = Short.valueOf("0");
                        int auxRegistro = 0;
                        for (TmpAlicuota tmpAlic2 : tmpAlicCop) {
                            String predio2 = tmpAlic2.getTalPredio();
                            String catastro2 = tmpAlic2.getTalCatastro();
                            if (predio2.equals(predio1) && catastro2.equals(catastro1)) {
                                auxRegistro++;
                            }

                            if (predio2.equals(predio1) && catastro2.equals(catastro1) && tmpAlic2.getTaPrincipal() != 1) {

                                totalAlicuota = totalAlicuota.add(tmpAlic2.getTalAlicuota());
                                totalSuperficie = totalSuperficie.add(tmpAlic2.getTalSuperficie());

                            }
                            if (predio2.equals(predio1) && catastro2.equals(catastro1) && tmpAlic2.getTaPrincipal() == 1) {
                                alicuotaPrincipal = tmpAlic2.getTalAlicuota();
                                superficiePrincipal = tmpAlic2.getTalSuperficie();
                                principal = tmpAlic2.getTaPrincipal();

                                //validar que para el campo principal, la alicuota sea igual 1
//                                if (tmpAlic2.getTalAlicuota().compareTo(BigDecimal.valueOf(1)) != 0) {
//                                    String errorLista = "Error. Para el campo Principal:"
//                                            + tmpAlic2.getTaPrincipal() + ", predio: " + tmpAlic1.getTalPredio()
//                                            + " y Catastro: " + tmpAlic1.getTalCatastro() + " el valor de la Alicuota debe ser 1";
//                                    if (!listaErrores.contains(errorLista)) {
//                                        listaErrores.add(errorLista);
//                                    }
//
//                                }
                                //validar que para el campo principal, la superficie tenga un valor
                                if (!tmpAlic2.getTalPredio().equals("0") && !tmpAlic2.getTalCatastro().equals("0")) {
                                    if (tmpAlic2.getTalSuperficie().compareTo(BigDecimal.valueOf(0)) == 0) {
                                        String errorLista = "Error. Para el campo Principal: "
                                                + tmpAlic2.getTaPrincipal() + ", predio: " + tmpAlic1.getTalPredio()
                                                + " y Catastro: " + tmpAlic1.getTalCatastro() + " el campo Superficie debe tener una valor distinto de 0";
                                        if (!listaErrores.contains(errorLista)) {
                                            listaErrores.add(errorLista);
                                        }

                                    }
                                }

                            }

                        }
                        if (auxRegistro == 1) {
                            totalAlicuota = tmpAlic1.getTalAlicuota();
                            totalSuperficie = tmpAlic1.getTalSuperficie();
                        }

                        //VALIDAR QUE LA ALICUOTA PRINCIPAL SEA IGUAL AL TOTAL DEL RESTO DE ALICUOTAS (DEL MISMO PREDIO Y CATASTRO)
                        // BigDecimal alicuotaUno = BigDecimal.valueOf(1);
                        if (alicuotaPrincipal.compareTo(BigDecimal.valueOf(0)) != 0) {
                            if (totalAlicuota.compareTo(alicuotaPrincipal) != 0) {
                                String errorLista = "Error. Para el predio: " + tmpAlic1.getTalPredio()
                                        + " y Catastro: " + tmpAlic1.getTalCatastro() + " el total de alicuotas debe ser igual a la alicuota principal.";
                                if (!listaErrores.contains(errorLista)) {
                                    listaErrores.add(errorLista);
                                }
                            }
                        }
                        //VALIDAR QUE LA SUPERFICIE PRINCIPAL SEA IGUAL AL TOTAL DEL RESTO SUPERFICIES (DEL MISMO PREDIO Y CATASTRO)
                        if (superficiePrincipal.compareTo(BigDecimal.valueOf(0)) != 0) {
                            if (totalSuperficie.compareTo(superficiePrincipal) != 0) {
                                String errorLista = "Error. Para el predio: " + tmpAlic1.getTalPredio()
                                        + " y Catastro: " + tmpAlic1.getTalCatastro() + " el total de superficies debe ser igual a la superficie principal.";
                                if (!listaErrores.contains(errorLista)) {
                                    listaErrores.add(errorLista);
                                }
                            }
                        }
                    }
                    //VALIDAR TOTAL ALICUOTAS SEA IGUAL A CIEN
                    List<TmpAlicuota> listTmpAlicCop = new ArrayList<>();
                    listTmpAlicCop.addAll(listaTmpAlicuota);
                    int contador = 0;
                    for (TmpAlicuota tmpAlic1 : listaTmpAlicuota) {
                        String predio1 = tmpAlic1.getTalPredio();
                        String catastro1 = tmpAlic1.getTalCatastro();
                        contador = 0;
                        for (TmpAlicuota tmpAlicuota : listTmpAlicCop) {
                            String predio2 = tmpAlicuota.getTalPredio();
                            String catastro2 = tmpAlicuota.getTalCatastro();
                            if (predio1.equals(predio2) && catastro1.equals(catastro2)) {
                                contador++;
                            }

                        }
                        if (contador == 1) {
                            validarTotalCien = validarTotalCien.add(tmpAlic1.getTalAlicuota());
                        }
                        if (tmpAlic1.getTaPrincipal() == 0) {
                            validarTotalCien = validarTotalCien.add(tmpAlic1.getTalAlicuota());
                        }

                    }
                    if (validarTotalCien.compareTo(BigDecimal.valueOf(100)) != 0) {
                        String errorLista = "Error. El total de alicuotas ingresados es " + validarTotalCien
                                + ". El total debe ser igual a 100.";
                        if (!listaErrores.contains(errorLista)) {
                            listaErrores.add(errorLista);
                        }
                    }

                    //VALIDAR QUE PREDIO Y CATASTRO AUN NO EXISTEN EN LA BASE DE DATOS
                    for (TmpAlicuota tmpAlic1 : listaTmpAlicuota) {
                        Propiedad prop = propiedadServicio.buscarPropiedadPor_predio_o_catastroEstadoActivo(tmpAlic1.getTalPredio(), tmpAlic1.getTalCatastro());
                        if (prop != null) {
                            String errorLista = "Error. El predio: " + tmpAlic1.getTalPredio()
                                    + " y Catastro: " + tmpAlic1.getTalCatastro() + " ya existen en la Base de Datos.";
                            if (!listaErrores.contains(errorLista)) {
                                listaErrores.add(errorLista);
                            }

                        }

                    }

                } else {
                    int rownum = 0;

                    for (String errorStr : listaErrores) {
                        Row row = sheet.createRow(rownum++);

                        int cellnum = 0;

                        Cell cell = row.createCell(cellnum);
                        if (errorStr instanceof String) {
                            cell.setCellValue(errorStr);
                        }

                    }
                    try {
                        //Write the workbook in file system
                        FileOutputStream out = new FileOutputStream(new File("C:/listaErrores.xlsx"));
                        workbook.write(out);
                        out.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        workbook.close();
                    }

                }
//                for (TmpAlicuota item : listaTmpAlicuota) {
//                    System.out.println("TalNumero: " + item.getTalNumero() + " TalAlicuota: " + item.getTalAlicuota());
//                }

            } else {

                addErrorMessage("Error el archivo es nulo");

            }
            if (listaErrores.isEmpty()) {
                renderedPanelError = false;
                renderedPanelExcel = true;
            } else {
                renderedPanelError = true;
                renderedPanelExcel = false;
            }

            for (linderosExcel excel : ListalinderosExcel) {
                System.out.println("P: " + excel.getPredioExcel() + " C: " + excel.getCatastroExcel());
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
            addErrorMessage("Error al cargar el archivo");
        }

    }

    public boolean ValidarNumeros(String Numero) {
        boolean validar = false;
        try {
            new BigDecimal(Numero);
            validar = false;
        } catch (NumberFormatException Exception) {
            validar = true;
        }
        return validar;
    }

    public boolean ValidarCodigoTipo(String Numero) {
        boolean validar = false;
        try {
            switch (Numero) {
                case "01":
                case "02":
                case "03":
                case "04":
                    validar = false;
                    break;
                default:
                    validar = true;
            }
        } catch (NumberFormatException Exception) {
            validar = true;
        }
        return validar;
    }

    public boolean ValidarNumerosShort(String Numero) {
        boolean validar = false;
        try {
            new Short(Numero);
            validar = false;
        } catch (NumberFormatException Exception) {
            validar = true;
        }
        return validar;
    }

    public boolean esCampoVacio(Cell c) {
        if (c == null) {
            return true;
        } else {
            switch (c.getCellTypeEnum()) {
                case BLANK:
                    return true;

            }
        }
        return false;
    }

    public StreamedContent getFile() {
        return file;
    }

    public void guardarInformacion() {
        if (listaErrores.isEmpty()) {
            try {

                Tramite tramiteSeleccionado = repertorioSeleccionado.getTraNumero();
                TramiteDetalle tramDet = servicioTramiteDetalle.buscarPorNumTramite(tramiteSeleccionado.getTraNumero());
                TipoTramite tipoTramit = servicioTipoTramite.buscarID(tramDet.getTdtTtrId().intValue());
                if (tipoTramit.getTtrCodigo() == 10) {
                    List<Alicuota> listAlc = new ArrayList<>();
                    List<Alicuota> listAlicPropVendidas = new ArrayList<>();
                    List<TmpAlicuota> listTmpAlicuota = new ArrayList<>();
                    TmpAlicuota tmpAlc;
                    listTmpAlicuota = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoDistintoE(matricula);
                    //CAMBIAR ESTADO PROPIEDADES HIJAS
                    List<Propiedad> propHijas = new ArrayList<>();
                    propHijas = propiedadServicio.listarPorMatriculaPadre(matricula);
                    for (Propiedad propHija : propHijas) {
                        if (!propHija.getPrdVendio().equals("S")) {
                            propHija.setPrdEstadoRegistro("I");
                            propiedadServicio.edit(propHija);
                            tmpAlc = new TmpAlicuota();
                            tmpAlc = tmpAlicuotaDao.buscarPorPredio_Catastro_Principal(propHija.getPrdPredio(), propHija.getPrdCatastro());
                            if (tmpAlc != null) {
                                if (!listTmpAlicuota.contains(tmpAlc)) {
                                    listTmpAlicuota.add(tmpAlc);
                                }
                            }
                        } else {
                            listAlc.clear();
                            listAlc = alicuotaServicio.listarPorMatricula(propHija.getPrdMatricula());
                            listAlicPropVendidas.addAll(listAlc);
                        }
                    }
                    //CAMBIAR ESTADO ALICUOTAS 
                    List<Alicuota> listAlicuota = new ArrayList<>();
                    for (Propiedad propHija : propHijas) {
                        if (!propHija.getPrdVendio().equals("S")) {
                            listAlicuota.clear();
                            listAlicuota = alicuotaServicio.listarPorMatricula(propHija.getPrdMatricula());
                            for (Alicuota alicuota1 : listAlicuota) {
                                alicuota1.setAltEstado("I");
                                alicuotaServicio.edit(alicuota1);
                            }
                        }
                    }

                    //CAMBIAR ESTADO TMPALICUOTA
                    List<TmpAlicuota> listTmpAlicPrevia = new ArrayList<>();
                    List<TmpAlicuota> listTmpAlicDescartar = new ArrayList<>();
                    for (Alicuota alicuota1 : listAlicPropVendidas) {
                        listTmpAlicPrevia.clear();
                        listTmpAlicPrevia = tmpAlicuotaDao.listarPorIdAlicuotaAsociada(alicuota1.getAltId());
                        for (TmpAlicuota tmpAlicuota : listTmpAlicPrevia) {
                            if (!listTmpAlicDescartar.contains(tmpAlicuota)) {
                                listTmpAlicDescartar.add(tmpAlicuota);
                            }
                        }
                    }

                    for (TmpAlicuota tmpAlic : listTmpAlicDescartar) {
                        if (listTmpAlicuota.contains(tmpAlic)) {
                            listTmpAlicuota.remove(tmpAlic);
                        }
                    }

                    for (TmpAlicuota tmpAlicuota1 : listTmpAlicuota) {
                        tmpAlicuota1.setTalEstado("E"); //E: ELIMINADA
                        tmpAlicuotaDao.edit(tmpAlicuota1);
                    }

                }
                matriculalPadre = matricula;
                propiedadPadre = propiedadServicio.obtenerPorMatricula(matriculalPadre);
                //OBTENER Y GENERAR EL SIGUIENTE SECUENCIAL
                TmpAlicuota tmpAlic = tmpAlicuotaDao.buscarAlicuotaUltimoSecuencial();
                BigInteger secuencial;
                if (tmpAlic != null) {
                    if (tmpAlic.getTalSecuencia() != null) {
                        secuencial = tmpAlic.getTalSecuencia().add(BigInteger.ONE);
                    } else {
                        secuencial = BigInteger.ONE;
                    }

                } else {
                    secuencial = BigInteger.ONE;
                }
                //GUARDAR COPIA DEL ARCHIVO EXCEL SUBIDO PARA LA CREACION DE TmpAlicuotas
                guardarCopiaArchivoExcel();
                //CREAR TmpAlicutas, Propiedad Hija, Alicuotas
                for (TmpAlicuota tmpAlicuota : listaTmpAlicuota) {
                    tmpAlicuota.setTalPath(direccion.toString());
                    tmpAlicuota.setTalSecuencia(secuencial);
//                    tmpAlicuota.setTalEstado("A");
//                    tmpAlicuotaDao.create(tmpAlicuota);
                    if (tmpAlicuota.getTaPrincipal() == 1 && !tmpAlicuota.getTalPredio().equals("0") && !tmpAlicuota.getTalCatastro().equals("0")) {
                        nuevaMatriculaHijaExc(tmpAlicuota);
                        guardarPropiedadHijaExc();
                        tmpAlicuota.setTalEstado("TP"); //ESTADO AUXILIAR PARA  LA TmpAlicuota con campo principal = 1 QUE SE CREA CUANDO SE CREA LA MATRICULA PH   
                        tmpAlicuotaDao.create(tmpAlicuota);
//                        Bloque bloq;
//                        bloq = bloqueDao.encontrarBloquePorNumMatriculaPorNombre(propiedadPadre.getPrdMatricula(), tmpAlicuota.getTalArea());
//                        if (bloq == null) {
//                            bloq = new Bloque();
//                            bloq.setBloCodigo(" ");
//                            bloq.setBloSubTipo(" ");
//                            bloq.setBloTipo(" ");
//                            bloq.setBloNombre(tmpAlicuota.getTalArea());
//                            bloq.setPrdMatricula(propiedadPadre);
//                            bloq.setBloFHR(Calendar.getInstance().getTime());
//                            bloq.setBloUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
//                            bloqueDao.create(bloq);
//                        }

                    } else {
                        Alicuota alcuot = new Alicuota();
                        alcuot = nuevaAlicuota(tmpAlicuota);
                        alicuotaServicio.create(alcuot);
                        //obtener alicuota creada y setear el id en la respectivo registro TmpAlicuota
                        Alicuota alic = alicuotaServicio.obtenerUltima(DataManagerSesion.getUsuarioLogeado().getUsuLogin());

                        tmpAlicuota.setTalAltId(alic.getAltId());
                        //crea TmpAlicuota con estado  C (CREADO) cuando se ha creado la alicuota en base a la respectiva tmpAlicuota
                        tmpAlicuota.setTalEstado("C");
                        tmpAlicuotaDao.create(tmpAlicuota);
                    }

                }
                //GuardarBloque

                for (Bloque bloque : ListaBloqueExcel) {
                    Bloque bloq;
                    bloq = bloqueDao.encontrarBloquePorNumMatriculaPorCodigoPorNombre(propiedadPadre.getPrdMatricula(), bloque.getBloCodigo(), bloque.getBloNombre());
                    if (bloq == null) {
                        bloq = new Bloque();
                        bloq = bloque;
//                            bloq.setPrdMatricula(propiedadPadre);
                        bloq.setBloFHR(Calendar.getInstance().getTime());

                        bloqueDao.create(bloq);
                    }

                }
                listaTmpAlicuota.clear();
                //listarPropiedadesHijas();
                listaPropiedadHija.clear();
                listaPropiedadHija = propiedadServicio.listarPorMatriculaPadre(propiedadPadre.getPrdMatricula());
                renderedPanelExcel = false;
            } catch (Exception e) {
                System.out.println(e);
                JsfUtil.addErrorMessage("Ha ocurrido un error al guardar.");
            }

        }

    }

    public Alicuota nuevaAlicuota(TmpAlicuota tmpAlic) {

        Alicuota alic = new Alicuota();
        Propiedad prop = propiedadServicio.obtenerPorMatricula(numMatriculaHija);
        alic.setPrdMatricula(prop);
        alic.setAltNumero(tmpAlic.getTalNumero());
        alic.setAltAlicuota(tmpAlic.getTalAlicuota());
        alic.setAltArea(tmpAlic.getTalArea());
        alic.setAltPiso(tmpAlic.getTalPiso());
        alic.setAltDescripcion(tmpAlic.getTalDescripcion());
        alic.setAltUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
        alic.setAltFHR(Calendar.getInstance().getTime());
        alic.setAltEstado("A");
        alic.setAltObservacion(tmpAlic.getTalObservacion());
        return alic;

    }

    public void nuevaMatriculaHijaExc(TmpAlicuota tmpAlic) {
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
            numMatriculaHija = idGenerado;

//            Propiedad prop = new Propiedad();
//            prop = propiedadServicio.buscarPorMatriculaPadre_UnidViv(propiedadPadre.getPrdMatricula());
//            int numUnidViv;
//            if (prop == null) {
//                numUnidViv = 1;
//            } else {
//                if (prop.getPrdUnidadVivienda().equals("")) {
//                    numUnidViv = 1;
//                } else {
//                    numUnidViv = Integer.valueOf(prop.getPrdUnidadVivienda()) + 1;
//                }
//
//            }
            propiedadNueva.setPrdMatricula(idGenerado);
            propiedadNueva.setPrdPadreMatricula(propiedadPadre);
            propiedadNueva.setPrdAgregado("A");
            //propiedadNueva.setPrdDescripcion1("");
            propiedadNueva.setPrdUnidadVivienda(tmpAlic.getTalObservacion());
            propiedadNueva.setPrdConjunto(propiedadPadre.getPrdConjunto());
            propiedadNueva.setPrdConjuntoId(propiedadPadre.getPrdConjuntoId());
            //propiedadNueva.setUmdId(propiedadPadre.getUmdId());
            propiedadNueva.setTclId(propiedadPadre.getTclId());
            propiedadNueva.setPrdClasePropiedad(propiedadPadre.getPrdClasePropiedad());
            //propiedadNueva.setTpdId(propiedadPadre.getTpdId());
            propiedadNueva.setPrdDescripcion2(propiedadPadre.getPrdDescripcion2());
            propiedadNueva.setPrdFHM(Calendar.getInstance().getTime());
            propiedadNueva.setPrdUserCreador(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
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
            propiedadNueva.setPrdAlicuota(tmpAlic.getTalAlicuota());
            propiedadNueva.setPrdBloque(tmpAlic.getTalArea());
            propiedadNueva.setPrdCatastro(tmpAlic.getTalCatastro().toString());
            propiedadNueva.setPrdPredio(tmpAlic.getTalPredio().toString());
            propiedadNueva.setPrdSuperficie(tmpAlic.getTalSuperficie());
            propiedadNueva.setPrdPiso(tmpAlic.getTalPiso());
            TipoPropiedad tipoProp = new TipoPropiedad();
            tipoProp = tipoPropiedadDao.buscarPorCodigo(tmpAlic.getTalTipoPropiedadCodigo());
            propiedadNueva.setTpdId(tipoProp);
            UnidMedida unidaMed = new UnidMedida();
            unidaMed = unidMedidaDao.buscarUnidadMedidadPorAbreviatura(tmpAlic.getTalTipoMedidaCodigo());
            propiedadNueva.setUmdId(unidaMed);
            llenarDescripcion1();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void guardarPropiedadHijaExc() {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            if (ListalinderosExcel.size() >= 2) {
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
//                        Propiedad propInactiva = propiedadDao.buscarPropiedadPor_predio_o_catastro_Estado(propiedadNueva.getPrdPredio(), propiedadNueva.getPrdCatastro(),"I" );
//                        if(propInactiva == null){
//                            propiedadDao.create(propiedadNueva);
//                        }
//                        else{
//                            List<Lindero> listLind = new ArrayList<>();
//                            listLind = servicioLindero.listarPorNumMatricula(propInactiva.getPrdMatricula());
//                            for (Lindero lind : listLind) {
//                                servicioLindero.remove(lind);
//                            }
//                            propiedadNueva.setPrdEstadoRegistro("A");
//                            propInactiva = propiedadNueva;
//                             propiedadDao.edit(propInactiva);
//                        }
                        propiedadDao.create(propiedadNueva);

                        tramiteUtil.insertarTramiteAccion(repertorioSeleccionado.getTraNumero(),
                                propiedadNueva.getPrdMatricula(),
                                "Propiedad creada con matricula "
                                + propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
                                + " y predio " + propiedadNueva.getPrdPredio(),
                                repertorioSeleccionado.getTraNumero().getTraEstado(),
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
                        for (linderosExcel lindero : ListalinderosExcel) {

                            if (propiedadNueva.getPrdCatastro().equals(lindero.getCatastroExcel()) && propiedadNueva.getPrdPredio().equals(lindero.getPredioExcel())) {

                                if (!lindero.getLindero1Excel().equals("")) {
                                    crearLinderoExcel(lindero.getLindero1Excel(), propiedadNueva);
                                }
                                if (!lindero.getLindero2Excel().equals("")) {
                                    crearLinderoExcel(lindero.getLindero2Excel(), propiedadNueva);
                                }
                                if (!lindero.getLindero3Excel().equals("")) {
                                    crearLinderoExcel(lindero.getLindero3Excel(), propiedadNueva);
                                }
                                if (!lindero.getLindero4Excel().equals("")) {
                                    crearLinderoExcel(lindero.getLindero4Excel(), propiedadNueva);
                                }
                                if (!lindero.getLindero5Excel().equals("")) {
                                    crearLinderoExcel(lindero.getLindero5Excel(), propiedadNueva);
                                }
                                if (!lindero.getLindero6Excel().equals("")) {
                                    crearLinderoExcel(lindero.getLindero6Excel(), propiedadNueva);
                                }
                                if (!lindero.getLindero7Excel().equals("")) {
                                    crearLinderoExcel(lindero.getLindero7Excel(), propiedadNueva);
                                }
                                if (!lindero.getLindero8Excel().equals("")) {
                                    crearLinderoExcel(lindero.getLindero8Excel(), propiedadNueva);
                                }

                            }

                        }
                        propiedadNueva = new Propiedad();
                        //nuevaMatriculaHija();
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

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad No Creada", "");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }

    }

    public void crearLinderoExcel(String nombreLindero, Propiedad propiedad) {
        Lindero lind = new Lindero();
        lind.setLdrNombre(nombreLindero);
        lind.setLdrFHR(Calendar.getInstance().getTime());
        lind.setLdrUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
        lind.setPrdMatricula(propiedad);
        servicioLindero.create(lind);
    }

    public void guardarCopiaArchivoExcel() throws IOException {

        try {
            InputStream inputSt = inpStr;

            Boolean exitoSubirArchivo = false;
//            Path folder;
            String extension = "xlsx";
            Path fileExcel;
            String nombreArchivo = "";
//            String dirPrincipal = "";
//            String subDirectorio = "archivos_Excel\\";
            ConfigDetalle configDet = configDetalleDao.buscarPorConfig("COPIA EXCEL PH");
            String directorio = configDet.getConfigDetalleTexto();
            //Path direccion;
            //parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            //dirPrincipal = parametroPath.getPrpPath();
            //dirPrincipal = "F:\\RM\\excel_PH\\";

            //obtengo la secuencia
            try {
                Date fecha = Calendar.getInstance().getTime();
                String fechaString = fechasUtil.convertirFechaA_ddMMAAAAhhmmss(fecha);

                nombreArchivo = "EXCEL_PH_" + fechaString;
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Ha ocurrido un error");
                e.printStackTrace();
            }

            //Crear archivo y copiar
            try (InputStream input = inputSt) {
                direccion = Paths.get(directorio + nombreArchivo + "." + extension);
                fileExcel = Files.createFile(direccion);
                Files.copy(input, fileExcel, StandardCopyOption.REPLACE_EXISTING);
                exitoSubirArchivo = true;
            } catch (Exception e) {
                exitoSubirArchivo = false;
                JsfUtil.addErrorMessage("Error al subir archivo");
                e.printStackTrace();
                System.out.println(e);
            }
            if (exitoSubirArchivo) {
                JsfUtil.addSuccessMessage("Exito. ¡Archivo subido! ");

            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error general al subir archivo");
        }

    }

    public void llenarHojasExcelDeTipos() throws InvalidFormatException, ServicioExcepcion {
        String excelFilePath = "";
        try {
            ConfigDetalle confDet = configDetalleDao.buscarPorConfig("DESCARGAR PLANTILLA");
            excelFilePath = confDet.getConfigDetalleTexto();
        } catch (Exception e) {
            System.out.println(e);
        }
        // String excelFilePath = "F:\\RM\\Proyectos Netbeans\\MP_RME\\RM-EMPRESARIAL-web\\src\\main\\webapp\\resources\\plantillaExcel\\PH_PLANTILLA.xlsx";
//        String[] columns = {"Cod", "Nom"};

        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
// 
            //Sheet sheet = workbook.createSheet("prueba");

//            Row headerRow = sheet.createRow(0);
//            for (int i = 0; i < columns.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(columns[i]);
//            }
            //LLENAR HOJA DE TIPO UNIDAD DE MEDIDA
            Sheet sheet = workbook.getSheetAt(2);
            int rowNum = 2;
            for (UnidMedida unidMed : listUnMed) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0).setCellValue(unidMed.getUmdCodigo());

                row.createCell(1).setCellValue(unidMed.getUmdNombre());

            }
            //LLENAR HOJA DE TIPO DE PROPIEDAD
            Sheet sheet3 = workbook.getSheetAt(3);
            rowNum = 2;
            for (TipoPropiedad tipoProp : listaTipoProp) {
                Row row = sheet3.createRow(rowNum++);

                row.createCell(0).setCellValue(tipoProp.getTpdCodigo());

                row.createCell(1).setCellValue(tipoProp.getTpdNombre());

            }
            //LLENAR HOJA DE CONJUNTO (COMPAÑIA)
//            Sheet sheet4 = workbook.getSheetAt(3);
//            rowNum = 2;
//            for (Compania comp : listaCompaniaExcel) {
//                Row row = sheet4.createRow(rowNum++);
//                row.createCell(0).setCellValue(comp.getComNombre());
//
//            }

            // Resize all columns to fit the content size
//            for (int i = 0; i < columns.length; i++) {
//                sheet.autoSizeColumn(i);
//            }
            FileOutputStream outputStream = new FileOutputStream("F:\\RM\\Proyectos Netbeans\\MP_RME\\RM-EMPRESARIAL-web\\src\\main\\webapp\\resources\\plantillaExcel\\PH_PLANTILLA.xlsx");
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /* ******************************* CREAR COMPANIA
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

    public void listarParroquiasComp() {
        nuevaCompania = new Compania();
        //identificacion = "";
        nombreComp = "";
        construcComp = "";
        listaPersonaCompania.clear();
        getTramitesControladorBb().setNombrePersona("");
        listaParriquias.clear();
        listaParriquias = servicioParroquia.listarTodo();
        parroquiaCompania = null;
    }

    public void setearDatosCompania() {
        nuevaCompania.setComNombre(nombreComp.toUpperCase());
        nuevaCompania.setComConstructora(construcComp.toUpperCase());
        nuevaCompania.setParId(parroquiaCompania);
    }

    public void guardarCompaniaExcel() throws ServicioExcepcion {
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
                        listaCompaniaExcel = companiaServicio.listarTodo();
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

    public void listarDetalleAlicuotas(Propiedad propiedad) throws ServicioExcepcion {
        propUV = propiedad;
        listaAlicuotaMostrar.clear();
        listaAlicuotaMostrar = alicuotaServicio.listarPorMatricula(propiedad.getPrdMatricula());
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

                //CAMBIAR ESTADO PROPIEDADES HIJAS, ELIMINAR LINDEROS 
                List<Propiedad> propHijas = new ArrayList<>();
                List<Lindero> listLinderos = new ArrayList<>();
                propHijas = propiedadServicio.listarPorMatriculaPadre(propiedadNueva.getPrdMatricula());
                for (Propiedad propHija : propHijas) {
                    propHija.setPrdEstadoRegistro("I");
                    propiedadServicio.edit(propHija);
                    listLinderos.clear();
                    listLinderos = servicioLindero.listarPorNumMatricula(propHija.getPrdMatricula());
                    for (Lindero listLindero : listLinderos) {
                        servicioLindero.remove(listLindero);
                    }
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
                listaErrores.clear();
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * **************************** DESHACER DECLARATORIA
     * ***************************
     */
    public void deshacerDeclaratoria() throws ServicioExcepcion {
        try {
            //CAMBIAR ESTADO PROPIEDAD PADRE
            propiedadNueva.setPrdEstadoRegistro("A");
            propiedadServicio.edit(propiedadNueva);

            List<Propiedad> propHijas = new ArrayList<>();

            propHijas = propiedadServicio.listarPropiedadPorMatriculaPadre(propiedadNueva.getPrdMatricula());

            //ELIMINAR  ALICUOTAS 
            List<Alicuota> listAlicuota = new ArrayList<>();
            for (Propiedad propHija : propHijas) {
                listAlicuota.clear();
                listAlicuota = alicuotaServicio.listarAlicuotaPorMatricula(propHija.getPrdMatricula());
                for (Alicuota alicuota1 : listAlicuota) {
                    alicuotaServicio.remove(alicuota1);
                }

            }

            //ELIMINAR  TMPALICUOTA
            List<TmpAlicuota> listTmpAlicuota = new ArrayList<>();
            listTmpAlicuota = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoDistintoE(propiedadNueva.getPrdMatricula());
            for (TmpAlicuota tmpAlicuota1 : listTmpAlicuota) {
                tmpAlicuotaDao.remove(tmpAlicuota1);
            }
            //ELIMINAR PROPIEDADES HIJAS, ELIMINAR LINDEROS 
            List<Lindero> listLinderos = new ArrayList<>();
            for (Propiedad propHija : propHijas) {
                listLinderos.clear();
                listLinderos = servicioLindero.listarPorNumMatricula(propHija.getPrdMatricula());
                for (Lindero listLindero : listLinderos) {
                    servicioLindero.remove(listLindero);
                }
                propiedadServicio.remove(propHija);
            }
            listaErrores.clear();
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Declaratoria deshacida", "");
            FacesContext.getCurrentInstance().addMessage("", facesMsg);

        } catch (Exception e) {
            System.out.println(e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ha ocurrido un error", "");
            FacesContext.getCurrentInstance().addMessage("", facesMsg);
        }

    }

    /**
     * *************************************** GEOLOCALIZACION
     * ****************************************
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
            String replace = direccionGeolocalizacion.replaceAll("\\s", reemplazo);

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
                direccionGeolocalizacion = "";
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

    /**
     * *************************************************************************
     */
    /**
     * *********** DESCARGAR EXCEL ACLARATORIA
     * *********************************
     */
    public void descargarExcelAclaratoria() throws ServicioExcepcion {
        List<Alicuota> listAlicuota = new ArrayList<>();
        List<Alicuota> listAlicPropVendidas = new ArrayList<>();
        List<TmpAlicuota> listTmpAlic = new ArrayList<>();
        List<TmpAlicuota> listTmpAlicPrevia = new ArrayList<>();
        List<TmpAlicuota> listTmpAlicDescartar = new ArrayList<>();
        TmpAlicuota tmpAlc;
        listTmpAlic = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoDistintoE(matricula);
        for (Propiedad propiedad : listaPropiedadHija) {
            if (!propiedad.getPrdVendio().equals("S")) {
//                listAlicuota.clear();
//                listAlicuota = alicuotaServicio.listarPorMatricula(propiedad.getPrdMatricula());
//                listAlicuotaExcel.addAll(listAlicuota);
                /**
                 *
                 */
//                tmpAlc = new TmpAlicuota();
//                tmpAlc = tmpAlicuotaDao.buscarPorPredio_Catastro_Principal(propiedad.getPrdPredio(), propiedad.getPrdCatastro());
//                if (tmpAlc != null) {
//                    if (!listTmpAlic.contains(tmpAlc)) {
//                        listTmpAlic.add(tmpAlc);
//                    }
//                }
            } else {
                listAlicuota.clear();
                listAlicuota = alicuotaServicio.listarPorMatricula(propiedad.getPrdMatricula());
                listAlicPropVendidas.addAll(listAlicuota);
            }
        }

        for (Alicuota alicuota1 : listAlicPropVendidas) {
            listTmpAlicPrevia.clear();
            listTmpAlicPrevia = tmpAlicuotaDao.listarPorIdAlicuotaAsociada(alicuota1.getAltId());
            for (TmpAlicuota tmpAlicuota : listTmpAlicPrevia) {

                if (!listTmpAlicDescartar.contains(tmpAlicuota)) {
                    listTmpAlicDescartar.add(tmpAlicuota);
                }
            }
        }
        for (TmpAlicuota tmpAlic : listTmpAlicDescartar) {
            if (listTmpAlic.contains(tmpAlic)) {
                listTmpAlic.remove(tmpAlic);
            }
        }
        try {
            /* crear excel nuevo*/
//            String[] columns = {"Es Principal ?", "Codigo Tipo", "Subtipo",
//                "Bloque", "Nombre Unidad", "Piso", "Secuencial", "Tipo Propiedad",
//                "Tipo Propiead solo Referencial", "Alicuota", "Superficie", "Und", "Catastro",
//                "Predio", "LINDERO1", "LINDERO2", "LINDERO3", "LINDERO4", "LINDERO5",
//                "LINDERO6", "LINDERO7", "LINDERO8", "Observación"};
//          
//            Workbook workbook = new XSSFWorkbook();
//            Sheet sheet = workbook.createSheet("HISTORIAL");

            /* buscar el excel (plantilla) y crear nuevo */
            String excelFilePath = "F:\\RM\\Proyectos Netbeans\\MP_RME\\RM-EMPRESARIAL-web\\src\\main\\webapp\\resources\\plantillaExcel\\PH_PLANTILLA.xlsx";
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

//eliminar filas del excel buscado
            int lastRowNum = sheet.getLastRowNum();
            int rowNum = 2;
            for (int fila = 2; fila <= sheet.getLastRowNum() + 1; fila++) {
                if (fila >= 2 && fila < lastRowNum) {
                    sheet.shiftRows(fila + 1, lastRowNum, -1);
                }
                if (fila == lastRowNum) {
                    Row removingRow = sheet.getRow(fila);
                    if (removingRow != null) {
                        sheet.removeRow(removingRow);

                    }
                }
            }

//                for(int fila = 2; fila <=lastRowNum; fila++ ){
//                    if (sheet.getRow(fila) != null) {
//      sheet.removeRow(sheet.getRow(fila));
//    }
//
//    sheet.shiftRows(fila +1, lastRowNum, -1);
//
//                }
            //crear fila cabecera 
//            Row headerRow = sheet.createRow(1);           
//            for (int i = 0; i < columns.length; i++) {
//                Cell cell = headerRow.createCell(i);
//                cell.setCellValue(columns[i]);
//            }
            Bloque bloq;
            List<Lindero> listLindExc = new ArrayList<>();
            int numRowLindero = 13;
            Propiedad propExc = new Propiedad();
            for (TmpAlicuota tmpAlic : listTmpAlic) {
                Row row = sheet.createRow(rowNum++);
                //Bloque
                bloq = new Bloque();
                bloq = bloqueDao.encontrarBloquePorNumMatriculaPorNombre(tmpAlic.getTalMatricula(), tmpAlic.getTalArea());
                if (bloq != null) {
                    row.createCell(1).setCellValue(bloq.getBloCodigo());
                    row.createCell(2).setCellValue(bloq.getBloSubTipo());
                } else {
                    row.createCell(1).setCellValue("");
                    row.createCell(2).setCellValue("");
                }
                //Predio, Catastro, Linderos
                if (tmpAlic.getTalPredio() != null && tmpAlic.getTalCatastro() != null) {
                    row.createCell(13).setCellValue(tmpAlic.getTalPredio());
                    row.createCell(12).setCellValue(tmpAlic.getTalCatastro());
                    listLindExc.clear();
                    propExc = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tmpAlic.getTalPredio(), tmpAlic.getTalCatastro());
                    if (propExc != null) {
                        row.createCell(4).setCellValue(propExc.getPrdUnidadVivienda());
                        listLindExc = servicioLindero.listarPorNumMatricula(propExc.getPrdMatricula());
                    }
                    numRowLindero = 13;
                    for (Lindero lindero : listLindExc) {
                        numRowLindero++;
                        row.createCell(numRowLindero).setCellValue(lindero.getLdrNombre());
                    }

                } else {
                    Alicuota alic = new Alicuota();
                    alic = alicuotaServicio.buscarPorId(tmpAlic.getTalAltId());
                    if (alic != null) {
                        row.createCell(12).setCellValue(alic.getPrdMatricula().getPrdCatastro());
                        row.createCell(13).setCellValue(alic.getPrdMatricula().getPrdPredio());
                        listLindExc.clear();
                        propExc = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(alic.getPrdMatricula().getPrdPredio(), alic.getPrdMatricula().getPrdCatastro());
                        if (propExc != null) {
                            row.createCell(4).setCellValue(propExc.getPrdUnidadVivienda());
                            listLindExc = servicioLindero.listarPorNumMatricula(propExc.getPrdMatricula());
                        }
                        numRowLindero = 13;
                        for (Lindero lindero : listLindExc) {
                            numRowLindero++;
                            row.createCell(numRowLindero).setCellValue(lindero.getLdrNombre());
                        }
                    } else {
                        row.createCell(12).setCellValue("");
                        row.createCell(13).setCellValue("");
                    }
                }
//**********************************************//
                row.createCell(0).setCellValue(tmpAlic.getTaPrincipal());
                row.createCell(3).setCellValue(tmpAlic.getTalArea());
                row.createCell(5).setCellValue(tmpAlic.getTalPiso());
                row.createCell(6).setCellValue(tmpAlic.getTalNumero());
                row.createCell(7).setCellValue(tmpAlic.getTalTipoPropiedadCodigo());
                row.createCell(8).setCellValue(tmpAlic.getTalDescripcion());
                row.createCell(9).setCellValue(tmpAlic.getTalAlicuota().toString());
                row.createCell(10).setCellValue(tmpAlic.getTalSuperficie().toString());
                row.createCell(11).setCellValue(tmpAlic.getTalTipoMedidaCodigo());
                row.createCell(22).setCellValue(tmpAlic.getTalObservacion());

            }

            // Resize all columns to fit the content size
//            for (int i = 0; i < columns.length; i++) {
//                sheet.autoSizeColumn(i);
//            }
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            bos.close();
            workbook.close();
            byte[] bytes = bos.toByteArray();
            InputStream myInputStream = new ByteArrayInputStream(bytes);
            fileExcelAclar = new DefaultStreamedContent(myInputStream, "application/vnd.ms-excel", "Excel_PH.xlsx");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}

enum mensajeError {

    Error_vacio("Error campo vacío. Celda: "),
    Error_clave_foranea_Unidad_Medida("No existe Código de Unidad de Vivienda. Celda: "),
    Error_clave_foranea_TipoPropiedad("No existe Código de Tipo Propiedad. Celda: "),
    Error_numero("Error no se puede convertir la superficie en numerico. Celda: ");

    private mensajeError(String mensaje) {
        this.mensaje = mensaje;
    }

    private final String mensaje;
}

class linderosExcel {

    @Getter
    @Setter
    private String predioExcel;
    @Getter
    @Setter
    private String catastroExcel;
    @Getter
    @Setter
    private String lindero1Excel;
    @Getter
    @Setter
    private String lindero2Excel;
    @Getter
    @Setter
    private String lindero3Excel;
    @Getter
    @Setter
    private String lindero4Excel;
    @Getter
    @Setter
    private String lindero5Excel;
    @Getter
    @Setter
    private String lindero6Excel;
    @Getter
    @Setter
    private String lindero7Excel;
    @Getter
    @Setter
    private String lindero8Excel;

    public linderosExcel(String pred, String cat, String lind1, String lind2, String lind3, String lind4) {
        this.predioExcel = pred;
        this.catastroExcel = cat;
        this.lindero1Excel = lind1;
        this.lindero2Excel = lind2;
        this.lindero3Excel = lind3;
        this.lindero4Excel = lind4;

    }

}
