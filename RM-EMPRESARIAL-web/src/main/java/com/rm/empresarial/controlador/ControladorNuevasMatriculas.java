/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
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
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.MatriculacionDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.TipoTramiteComparecienteDao;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Provincia;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.Usuario;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import static org.primefaces.component.contextmenu.ContextMenu.PropertyKeys.event;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorNuevasMatriculas")
@ViewScoped
public class ControladorNuevasMatriculas implements Serializable {

    @Inject
    DataManagerSesion DataManagerSesion;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Inject
    private TramiteUtil tramiteUtil;

    @EJB
    private TipoTramiteComparecienteDao tipoTramiteComparecienteDao;

    @Inject
    private ControladorInscripcionProceso controladorInscripcionProceso;

    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;

    @EJB
    NuevasMatriculasServicio NuevasMatriculasServicio;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @EJB
    private PropiedadDetalleServicio servicioPropiedadDetalle;

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
    private UnidMedidaServicio servicioUnidadMedida;

    @EJB
    private PersonaServicio personaServicio;

    @EJB
    private PropietarioServicio propietarioServicio;

    @EJB
    private RepertorioUsuarioServicio repertorioUsuarioServicio;

    @EJB
    private GravamenDao servicioGravamen;

    @Getter
    @Setter
    private String estadoFocusMatricula;

    @Getter
    @Setter
    private Secuencia secuencia;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

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
    private int idTipoParroquia;
    @Getter
    @Setter
    private TipoPropiedad tipoPropiedadSeleccionada;
    @Getter
    @Setter
    private List<Parroquia> listaParroquiaTramValor = new ArrayList<>();
    @Getter
    @Setter
    private List<Parroquia> listaParroquia = new ArrayList<>();
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

//    @Getter
//    @Setter
//    private List<Lindero> listaLindero = new ArrayList<>();
    @Getter
    @Setter
    private String matricula;
    @Getter
    @Setter
    private Long numRepertorio;

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
    private Propiedad propiedadNueva;
    @Getter
    @Setter
    private String estadoPropiedad;
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
    private List<Lindero> listaLinderos;

    @Getter
    @Setter
    private Lindero linderoSeleccionado;

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
    private Boolean esPrincipal;

    @Getter
    @Setter
    private Integer filaSeleccionada;

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
    private PropiedadDetalle propiedadDetElim;

    @Getter
    @Setter
    private Repertorio repertorioSeleccionado;

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor;
    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValorSeleccionados;

    @Getter
    @Setter
    private List<TipoTramite> listaTipoTramite;

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValorUnf;

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValorDiv;

    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropDetActualizarEstado;

    @Getter
    @Setter
    private TramiteValor tramiteValorSeleccionado;

    @Getter
    @Setter
    private TramiteValor nuevoTramiteValor;

    @Getter
    @Setter
    private TramiteValor tramiteValorUNFSeleccionado;

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
    private Boolean boolSeleccionadaPropPrincipalUnif = false;

    @Getter
    @Setter
    private Boolean propSecundariaUNF = false;

    @Getter
    @Setter
    private String tipoMatriculacion;

    @Getter
    @Setter
    private int indiceActivoTabView;

    @Getter
    @Setter
    private double totalSuperficie;

    private BigDecimal totalSuperficieUnf;

    @Getter
    @Setter
    private BigDecimal numPorcentajeAccionesRestantes;

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

    public ControladorNuevasMatriculas() throws ServicioExcepcion {
        estadoFocusMatricula = "false";
        secuencia = new Secuencia();
        boolOcultarCmbParroquia = false;
        propiedadNueva = new Propiedad();
        listaLinderos = new ArrayList<>();
        listaPropiedadDetalle = new ArrayList<>();
        totalAcc = new BigDecimal(0);
        numPorcentajeAccionesRestantes = new BigDecimal(100);
        listaPropiedadesUnf = new ArrayList<>();
        listaPropiedadesDiv = new ArrayList<>();
        propiedadNueva = new Propiedad();
        propiedadNueva.setPrdEspacial("");
        repertorioSeleccionado = new Repertorio();
        mostrarComboGrupo = true;
        esPropiedadNueva = true;
        revisado = false;
        listaTipoTramite = new ArrayList<>();
        mostrarTabView = false;
        tramiteValorSeleccionado = new TramiteValor();
        nuevoTramiteValor = new TramiteValor();
        tramiteValorUNFSeleccionado = new TramiteValor();
        repertorioUsuarioSeleccionado = new RepertorioUsuario();
        listaTramiteValorUnf = new ArrayList<>();
        listaTramiteValorDiv = new ArrayList<>();
        listaTramiteValorSeleccionados = new ArrayList<>();
        propiedadDetElim = new PropiedadDetalle();
        listaPropDetActualizarEstado = new ArrayList<>();
        disabledBtnTerminar = true;
        renderedBtnTerminar = true;
        tramiteDetalleSeleccionado = new TramiteDetalle();
        centerGeoMap = "-0.2029,-78.4911";
        geoModel = new DefaultMapModel();
        zoom = 15;
        direccion = "";
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

    }

//    public void editarTramiteValor(RowEditEvent event){
//        TramiteValor tramValorEdit = (TramiteValor) event.getObject();
//        Boolean esValorRepetido = false;
//        for (TramiteValor tramiteValor : listaTramiteValor) {
//            if (tramiteValor.getTrvNumCatastro().equals(tramValorEdit.getTrvNumCatastro())) {
//                esValorRepetido = true;
//                JsfUtil.addErrorMessage("Error al guardar. Número de Catastro repetido");
//                break;
//            }
//            if (tramiteValor.getTraNumPredio().equals(tramValorEdit.getTraNumPredio())) {
//                esValorRepetido = true;
//                JsfUtil.addErrorMessage("Error al guardar. Número de Predio repetido");
//                break;
//            }
//        }
//
//        /**
//         * ************************
//         */
//        /* VALIDAR SI ENCUENTRA UNA PROPIEDAD CON PREDIO Y CATASTRO INGRESADOS Y 
//        QUE ÉSTA PERTENESCA A UNO DE LOS COMPARECIENTES*/
//        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
//        try {
////            Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();
//            listaTramDetComparecientes.clear();
//            RepertorioUsuario repertorioUsuarioSelec = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
//                    repertorioSeleccionado.getRepNumero(), DataManagerSesion.getUsuarioLogeado().getUsuId(), "MAT");
//            listaTramDetComparecientes = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "N");
//
//            esPropietario = false;
//            if (tramiteSelec.getTraClase().equals("J")) {
//                Propiedad propiedad = new Propiedad();
//                propiedad = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tramValorEdit.getTraNumPredio().toString(), tramValorEdit.getTrvNumCatastro().toString());
//                if (propiedad != null) {
//                    List<Propietario> listaPropietario = new ArrayList<>();
//                    listaPropietario.clear();
//                    for (TramiteDetalle listaTramDetCompareciente : listaTramDetComparecientes) {
//                        List<Propietario> listaPro = new ArrayList<>();
//                        if (listaTramDetCompareciente.getTtcId().getTtcPropietario().trim().equals("N")) {
//                            listaPro = propietarioServicio.buscarPor_Id_Persona_Por_Matricula(listaTramDetCompareciente.getPerId().getPerId(), propiedad.getPrdMatricula());
//                        }
//                        listaPropietario.addAll(listaPro);
//                    }
//                    if (!listaPropietario.isEmpty()) {
//                        esPropietario = true;
//                    }
//
//                }
//            } else if (!esValorRepetido) {
//                servicioTramiteValor.edit(tramValorEdit);
//             
//                JsfUtil.addSuccessMessage("Editado");
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//
//        /**
//         * ****************************************************
//         */
//        if (!esValorRepetido && esPropietario == true && tramiteSelec.getTraClase().equals("J")) {
//            servicioTramiteValor.edit(tramValorEdit);
//           
//            JsfUtil.addSuccessMessage("Editado");
//        } else if (esPropietario == false && tramiteSelec.getTraClase().equals("J")) {
//            JsfUtil.addErrorMessage("Propiedad no pertenece a ningún compareciente");
//        }
//        tramValorEdit = new TramiteValor();
//
//        if (listaTramiteValor.isEmpty() && tramiteSelec.getTraClase().equals("J")) {
//            disabledBtnTerminar = false;
//            renderedBtnTerminar = true;
//        } else {
//            renderedBtnTerminar = false;
//        }
//    }
    public String verificarGravamenes(TramiteValor traval) {
        String mensajeGravamen = "";

        Propiedad propiedadCargada = NuevasMatriculasServicio.buscarPropiedadConCatastroPredio(traval.getTrvNumCatastro().toString(), traval.getTraNumPredio().toString());
        if (propiedadCargada != null) {
            List<Gravamen> listaGravamenes = servicioGravamen.buscarPorMatricula(propiedadCargada);
            if (listaGravamenes.isEmpty()) {
                mensajeGravamen = "NINGUNO";
                return mensajeGravamen;
            } else {
                mensajeGravamen = "Tiene gravamenes";
                return mensajeGravamen;
            }
        } else {
            mensajeGravamen = "No existe propiedad";
            return mensajeGravamen;
        }
    }

    public String obtenerEstadoPropiedad(TramiteValor traval) {
        String mensaje = "";

        Propiedad propiedadCargada = NuevasMatriculasServicio.buscarPropiedadConCatastro_Y_Predio(traval.getTrvNumCatastro(), traval.getTraNumPredio());
        if (propiedadCargada != null) {
            switch (propiedadCargada.getPrdEstadoRegistro()) {
                case "A":
                    mensaje = "Activo";
                    break;
                case "I":
                    mensaje = "Inactivo";
                    break;
            }
            return mensaje;
        } else {
            return mensaje;
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

//                verificarGravamenes(tramiteValorSiguiente);
            }
            if (listaTramiteValor.isEmpty() && tramiteSelec.getTraClase().equals("J")) {
                disabledBtnTerminar = false;
            } else {
                renderedBtnTerminar = false;
            }
            //setear campo trvPrincipal como false (0)
            for (TramiteValor tramiteValor : listaTramiteValor) {
                tramiteValor.setTrvPrincipal(false);
                servicioTramiteValor.edit(tramiteValor);
            }

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorNuevasMatriculas.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //cargar lista de parroquias para tramite valor y propiedad
            if (tipoMatriculacion != null) {
                switch (tipoMatriculacion) {
                    case "UNF":
                    case "DIV":
                        if (listaTramiteValor.isEmpty()) {
                            setListaParroquiaTramValor(MatriculacionDao.ListarParroquia());
                        } else {
                            TramiteValor tramValor = listaTramiteValor.get(0);
                            Parroquia parroquia = servicioParroquia.buscarPorId(tramValor.getTrvParId());
                            listaParroquiaTramValor.add(parroquia);
                        }
                        setListaParroquia(MatriculacionDao.ListarParroquia());
                        break;
                    default:
                        setListaParroquiaTramValor(MatriculacionDao.ListarParroquia());
                        setListaParroquia(MatriculacionDao.ListarParroquia());
                }

            } else {
                setListaParroquiaTramValor(MatriculacionDao.ListarParroquia());
                setListaParroquia(MatriculacionDao.ListarParroquia());

            }

            //**//
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorNuevasMatriculas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorNuevasMatriculas.class.getName()).log(Level.SEVERE, null, ex);
        }

//        parroquiaSeleccionada = listaParroquia.get(0);
    }

    public void preCrearNuevoTramiteValor() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.preCrearNuevoTramiteValor()");
        parroquiaSeleccionada = new Parroquia();
        nuevoTramiteValor = new TramiteValor();

    }

    public void crearTramiteValor() throws ServicioExcepcion {
        if (!nuevoTramiteValor.getTrvNumCatastro().trim().isEmpty()) {

//            if(listaTramiteValor.size() == 1 && tramiteValorSeleccionado.getTrvParId() != null){
//                listaParroquia.clear();
//                Parroquia parr = servicioParroquia.buscarPorId(tramiteValorSeleccionado.getTrvParId());
//                listaParroquia.add(parr);                
//            }
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
                        repertorioSeleccionado.getRepNumero(), DataManagerSesion.getUsuarioLogeado().getUsuId(), "MAT");
                listaTramDetComparecientes = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "N");

                esPropietario = false;
                if (tramiteSelec.getTraClase().equals("J")) {
                    Propiedad propiedad = new Propiedad();
                    propiedad = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(nuevoTramiteValor.getTraNumPredio(), nuevoTramiteValor.getTrvNumCatastro());
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
//                JsfUtil.addSuccessMessage("Ingresado");

                }

            } catch (Exception e) {
                e.printStackTrace(System.out);
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
        limpiarPropiedad();
    }

    public void limpiarPropiedad() {
        matricula = "";
        propiedadNueva = new Propiedad();
        listaLinderos = new ArrayList<>();
        listaPropiedadDetalle.clear();
    }

    public void seleccionarTramiteValor() throws ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.seleccionarTramiteValor()");
        if (!tramiteValorSeleccionado.getTrvNumCatastro().trim().isEmpty()) {

            propiedadNueva = new Propiedad();

//        Parroquia parroquiaSelec = servicioParroquia.find(new Parroquia(), tramiteValorSeleccionado.getTrvParId());
            actualizarTramiteValor();
            esPropiedadNueva = true;
            try {
                limpiarPropiedad();
                inicializarPropiedad();
            } catch (ServicioExcepcion ex) {
                Logger.getLogger(ControladorNuevasMatriculas.class.getName()).log(Level.SEVERE, null, ex);
            }

            mostrarTabView = true;
        } else {
            JsfUtil.addErrorMessage("Ingrese el Catrastro en el Trámite Valor seleccionado");
        }

    }

    public void actualizarTramiteValor() throws ServicioExcepcion {
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
        tramiteValorSeleccionado.setTrvPrincipal(true);
        servicioTramiteValor.edit(tramiteValorSeleccionado);
        for (TramiteValor tramiteValor : listaTramiteValor) {
            if (tramiteValor.getTrvId().longValue() != tramiteValorSeleccionado.getTrvId().longValue()) {
                tramiteValor.setTrvPrincipal(false);
                servicioTramiteValor.edit(tramiteValor);
            }

        }
        //PONER EL ITEM SELECCIONADO EN LA PRIMERA POSICION DE LA LISTA
        int index = listaTramiteValor.indexOf(tramiteValorSeleccionado);
        listaTramiteValor.remove(index);
        listaTramiteValor.add(0, tramiteValorSeleccionado);

//        listaTramiteValor.clear();
//        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
//        TipoTramite tipoTramiteSelec = servicioTipoTramite.find(new TipoTramite(), new Long(repertorioSeleccionado.getRepTtrId()));
//        listaTramiteValor = servicioTramiteValor.ListarPor_TipoTramite_NumTramite_Estado_CertificadoNulo(tramiteSelec, tipoTramiteSelec);
    }

    public void seleccionarTramiteValorUNF() throws ServicioExcepcion {
        listaTramiteValor.clear();
        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
        TipoTramite tipoTramiteSelec = servicioTipoTramite.find(new TipoTramite(), new Long(repertorioSeleccionado.getRepTtrId()));
        listaTramiteValor = servicioTramiteValor.ListarPor_TipoTramite_NumTramite_Estado_CertificadoNulo(tramiteSelec, tipoTramiteSelec);

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
            setListaUnidMedida(NuevasMatriculasServicio.listarUnidadMedidad());
            listaGrupo = NuevasMatriculasServicio.listarTipoPropiedadClase();
//            setListaLindero(NuevasMatriculasServicio.listarLindero());
//            setLinderoConsultado(NuevasMatriculasServicio.obtenerLinderoPorMatricula(getMatricula().intValue()));

            /**
             * **** cargar lista parroquia para tramite valor y propiedad*****
             */
            if (tipoMatriculacion != null) {
                switch (tipoMatriculacion) {
                    case "UNF":
                    case "DIV":
                        if (listaTramiteValor.isEmpty()) {
                            setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
                        } else {
                            TramiteValor tramValor = listaTramiteValor.get(0);
                            Parroquia parroquia = servicioParroquia.buscarPorId(tramValor.getTrvParId());
                            listaParroquiaTramValor.add(parroquia);
                        }
                        setListaParroquia(NuevasMatriculasServicio.listaParroquia());
                        break;
                    default:
                        setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
                        setListaParroquia(NuevasMatriculasServicio.listaParroquia());
                }

            } else {
                setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
                setListaParroquia(NuevasMatriculasServicio.listaParroquia());

            }

            /**
             * ******************
             */
            listaTipoTramite = NuevasMatriculasServicio.listarTipoTramite();
//            setEstadoPropiedad(getPropiedadNueva().getPrdEstadoPropiedad());
        } catch (Exception e) {
            System.out.print("FALLO AL CONSULTAR");
        }
    }

    public void cargarListaParroquia() {
        listaParroquiaTramValor.clear();
        listaParroquia.clear();
        if (tipoMatriculacion != null) {
            switch (tipoMatriculacion) {
                case "UNF":
                case "DIV":
                    if (listaTramiteValor.isEmpty()) {
                        setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
                    } else {
                        TramiteValor tramValor = listaTramiteValor.get(0);
                        Parroquia parroquia = servicioParroquia.buscarPorId(tramValor.getTrvParId());
                        listaParroquiaTramValor.add(parroquia);
                    }
                    setListaParroquia(NuevasMatriculasServicio.listaParroquia());
                    break;
                default:
                    setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
                    setListaParroquia(NuevasMatriculasServicio.listaParroquia());
            }

        } else {
            setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
            setListaParroquia(NuevasMatriculasServicio.listaParroquia());

        }
    }

    public void inicializarPropiedad() throws ServicioExcepcion {
        Propiedad propiedadCargada = null;
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
            propiedadNueva.setPrdClasePropiedad("O");
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

        } else {
            propiedadNueva = propiedadCargada;
            matricula = propiedadNueva.getPrdMatricula();
            esPropiedadNueva = false;
            JsfUtil.addSuccessMessage("Propiedad Cargada para actualizar");

            try {
                listaLinderos = servicioLindero.listarPorNumMatricula(getPropiedadNueva().getPrdMatricula());
            } catch (Exception e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            }
            seleccionarClase();
            String tipoOperacionMatriculaRealizada = servicioPropiedadDetalle.obtenerTipoOperacionPropiedadRealizada(propiedadNueva.getPrdMatricula());
            if (tipoOperacionMatriculaRealizada != null) {
                switch (tipoOperacionMatriculaRealizada) {
                    case "UNF": {
//                        indiceActivoTabView = 0;
                        tipoMatriculacion = tipoOperacionMatriculaRealizada;
                        listaPropiedadesUnf = new ArrayList<>();
                        List<PropiedadDetalle> listaPropiedadDetalleUnf;
                        listaPropiedadDetalleUnf = servicioPropiedadDetalle.listar_Por_Propiedad_Tipo(propiedadNueva.getPrdMatricula(), "UNF");
                        if (!listaPropiedadDetalleUnf.isEmpty()) {
                            for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalleUnf) {
                                Propiedad propiedadUnf = NuevasMatriculasServicio.find(new Propiedad(), propiedadDetalle.getPdtPrdMatricula());
                                listaPropiedadesUnf.add(propiedadUnf);
                            }
                        }
                        break;
                    }
                    case "DIV": {
//                        indiceActivoTabView = 1;
                        tipoMatriculacion = tipoOperacionMatriculaRealizada;
                        listaPropiedadesDiv = new ArrayList<>();
                        List<PropiedadDetalle> listaPropiedadDetalleDiv;
                        listaPropiedadDetalleDiv = servicioPropiedadDetalle.listar_Por_Propiedad_Tipo(propiedadNueva.getPrdMatricula(), "DIV");
                        if (!listaPropiedadDetalleDiv.isEmpty()) {
                            for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalleDiv) {
                                Propiedad propiedad = NuevasMatriculasServicio.find(new Propiedad(), propiedadDetalle.getPdtPrdMatricula());
                                listaPropiedadesDiv.add(propiedad);
                            }
                        }
                        break;
                    }
                    case "GDA": {
//                        indiceActivoTabView = 1;
                        tipoMatriculacion = tipoOperacionMatriculaRealizada;
                        cargarPropiedadDetalle();
                        calcularTotalAcciones();
                        break;
                    }
                }
            } else {
                if (tipoMatriculacion != null) {
                    switch (tipoMatriculacion) {
                        case "UNF": {
//                            indiceActivoTabView = 1;
                            listaPropiedadesUnf = new ArrayList<>();
                            List<PropiedadDetalle> listaPropiedadDetalleUnf;
//                            listaPropiedadDetalleUnf = servicioPropiedadDetalle.listar_Por_Propiedad_Tipo(propiedadNueva.getPrdMatricula(), "UNF");
//                            if (!listaPropiedadDetalleUnf.isEmpty()) {
//                                for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalleUnf) {
//                                    Propiedad propiedadUnf = NuevasMatriculasServicio.find(new Propiedad(), propiedadDetalle.getPdtPrdMatricula());
//                                    listaPropiedadesUnf.add(propiedadUnf);
//                                }
//                            }
                            break;
                        }
                        case "DIV": {
//                            indiceActivoTabView = 1;
                            listaPropiedadesDiv = new ArrayList<>();
                            List<PropiedadDetalle> listaPropiedadDetalleDiv;
//                            listaPropiedadDetalleDiv = servicioPropiedadDetalle.listar_Por_Propiedad_Tipo(propiedadNueva.getPrdMatricula(), "DIV");
//                            if (!listaPropiedadDetalleDiv.isEmpty()) {
//                                for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalleDiv) {
//                                    Propiedad propiedad = NuevasMatriculasServicio.find(new Propiedad(), propiedadDetalle.getPdtPrdMatricula());
//                                    listaPropiedadesDiv.add(propiedad);
//                                }
//                            }
                            break;
                        }
                        case "GDA": {
//                            indiceActivoTabView = 1;
//                            cargarPropiedadDetalle();
//                            calcularTotalAcciones();
                            break;
                        }
                    }
                }

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
        try {
            if (propiedadNueva.getPrdMatricula() != null) {
                Parroquia parroquiaActual = servicioParroquia.find(new Parroquia(), getPropiedadNueva().getPrdTdtParId());
                getPropiedadNueva().setPrdTdtParNombre(parroquiaActual.getParNombre());

            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

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
            tipoPropiedad = getPropiedadNueva().getTpdId().getTpdNombre().trim();
            parroquia = getPropiedadNueva().getPrdTdtParNombre().trim();
            texto = tipoPropiedad + " " + getPropiedadNueva().getPrdUnidadVivienda() + " situado en la Parroquia " + parroquia + " de este Cantón";

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        getPropiedadNueva().setPrdDescripcion2(texto);
//        System.out.println("desc2: " + getPropiedadNueva().getPrdDescripcion2());
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
//        if (!esPropiedadNueva) {
//            for (Lindero lindero : listaLinderos) {
//                servicioLindero.remove(lindero);
//            }
//            listaLinderos = new ArrayList<>();
//        }
        linderoSeleccionado = new Lindero();
    }

    public void crearLindero() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.crearLindero()");
        FacesContext context = FacesContext.getCurrentInstance();

//        if (listaLinderos.size() < 4) {
        listaLinderos.add(linderoSeleccionado);
//        } else {
//            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Solo puede agregar 4 linderos", ""));
//        }
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

//        if (servicioLindero.contains(linderoSeleccionado)) {
//            System.out.println("lindero managed");
//        }
//        linderoSeleccionado = servicioLindero.find(new Lindero(), linderoSeleccionado.getLdrId());
//        if (servicioLindero.contains(linderoSeleccionado)) {
//            System.out.println("lindero managed 2");
//        }
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
                        repertorioSeleccionado.getRepNumero(), DataManagerSesion.getUsuarioLogeado().getUsuId(), "MAT");
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
            String idGenerado = secuenciaControlador.getControladorBb().getNumeroMatricula();
//        setMatricula(idGenerado);
            secuenciaServicio.guardar(getSecuencia());

            if (esPropiedadNueva) {
                if (!propiedadNueva.getPrdDescripcion2().trim().isEmpty()) {
                    if (listaLinderos.size() >= 2) {
                        try {
                            propiedadNueva.setPrdMatricula(idGenerado);
                            propiedadNueva.setPrdUserCreador(" ");
                            propiedadNueva.setPrdEstadoRegistro("A");
                            propiedadNueva.setPrdSector(" ");
                            propiedadNueva.setPrdFHM(Calendar.getInstance().getTime());
                            propiedadNueva.setPrdFHR(Calendar.getInstance().getTime());
                            propiedadNueva.setPrdUserModificacion(" ");
                            propiedadNueva.setPrdUbicacion(" ");
                            propiedadNueva.setPrdTdtParIdH(propiedadNueva.getPrdTdtParId());
                            propiedadNueva.setPrdTdtParNombreH(propiedadNueva.getPrdTdtParNombre());

//                        TipoPropiedadClase tipoPropiedadClasePorDefecto;
//                        tipoPropiedadClasePorDefecto = servicioTipoPropiedadClase.find(new TipoPropiedadClase(), "NIN");
//                        propiedadNueva.setTclId(tipoPropiedadClasePorDefecto);
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
                    } else {
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La propiedad debe tener mínimo 2 linderos.", ""));
                    }
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Descripción de la Propiedad", ""));
                }

            } else {
                if (!propiedadNueva.getPrdDescripcion2().trim().isEmpty()) {
                    propiedadNueva.setTpdId(propiedadNueva.getTpdId());
                    NuevasMatriculasServicio.edit(propiedadNueva);
                    tramiteValorSeleccionado.setTrvParId(propiedadNueva.getPrdTdtParId());
                    tramiteValorSeleccionado.setTrvParNombre(propiedadNueva.getPrdTdtParNombre());
                    servicioTramiteValor.edit(tramiteValorSeleccionado);
                    context.addMessage(null, new FacesMessage("Propiedad Actualizada", ""));
                    tramiteUtil.insertarTramiteAccion(repertorioSeleccionado.getTraNumero(),
                            propiedadNueva.getPrdMatricula().toString(),
                            "Propiedad con matricula "
                            + propiedadNueva.getPrdMatricula() + "/catastro " + propiedadNueva.getPrdCatastro()
                            + "/predio " + propiedadNueva.getPrdPredio() + " actualizada",
                            repertorioSeleccionado.getTraNumero().getTraEstado(),
                            repertorioSeleccionado.getTraNumero().getTraEstadoRegistro(),
                            null);

                    for (Lindero lindero : listaLinderos) {
                        lindero.setLdrFHR(Calendar.getInstance().getTime());
                        lindero.setLdrUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        lindero.setPrdMatricula(propiedadNueva);
                        servicioLindero.edit(lindero);
                    }
//                context.addMessage(null, new FacesMessage("Linderos Actualizados", ""));
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Descripción de la Propiedad", ""));
                }

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
                    "Repertorio " + nuevoRepPro.getRpdId() + " asignado a la propiedad "
                    + propiedadNueva.getPrdMatricula(),
                    nuevoRepPro.getRepNumero().getTraNumero().getTraEstado(),
                    nuevoRepPro.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                    null);
        }
    }

    public void preBuscar() {
        numDocumento = null;
        boolDocEncontrado = false;
    }

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

//    public void seleccionarPropiedad() {
//        try {
//            //            matricula = new Long(params.get("matricula").toString());
////            catastro = new String(params.get("catastro").toString());
////            predio = new String(params.get("predio").toString());
////            numRepertorio = new Long(params.get("numRepertorio").toString());
////            numTramite = new Long(params.get("numTramite").toString());
////            fecha = new String(params.get("fecha").toString());
//            setMatricula(new Long(propiedadSelec.getPrdMatricula()));
//            setCatastro(propiedadSelec.getPrdCatastro());
//            setPredio(propiedadSelec.getPrdPredio());
//            propiedadNueva = propiedadSelec;
//
////            llenarDescripcion1();
////            llenarDescripcion2();
//            esPropiedadNueva = false;
//            boolOcultarCmbParroquia = true;
//            listaLinderos = servicioLindero.listarPorNumMatricula(getPropiedadNueva().getPrdMatricula());
//
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage("Error al seleccionar propiedad");
//
//        }
//
//    }
    public void seleccionarParroquiaHistorica() {
//        Parroquia parroquiaActual= servicioParroquia.find(new Parroquia(), getPropiedadNueva().getPrdTdtParId());
//        getPropiedadNueva().setPrdTdtParNombre(parroquiaActual.getParNombre());
        try {
            if (propiedadNueva.getPrdMatricula() != null) {
                Parroquia parroquiaHistorica = servicioParroquia.find(new Parroquia(), getPropiedadNueva().getPrdTdtParIdH());
                getPropiedadNueva().setPrdTdtParNombreH(parroquiaHistorica.getParNombre());
//        getPropiedadNueva().setPrdTdtParIdH();
                getPropiedadNueva().setPrdTdtParNombre(parroquiaHistorica.getParNombre());
                getPropiedadNueva().setPrdTdtParId(parroquiaHistorica.getParId());
                llenarDescripcion2();
//        getPropiedadNueva().setPrdDescripcion2("Departamento situado en la parroquia " + getPropiedadNueva().getPrdTdtParNombreH() + " de este Cantón");

            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
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

    //******************** DERECHOS Y ACCIONES *****************
    public void generarDescripcion() {
        try {

            propiedadDetalleSeleccionada.setPdtDescripcion(TransformadorNumerosLetrasUtil.transformador(propiedadDetalleSeleccionada.getPdtValor().toString())
                    + "POR CIENTO DE ACCIONES Y DERECHOS SOBRE " + propiedadNueva.getTpdId().getTpdNombre().trim()
                    + " UBICADO EN "
                    + propiedadNueva.getPrdTdtParNombre() + " DE ESTE CANTON");

        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

//    public void cargarDatosPersona() {
//        personaSeleccionada = new Persona();
//        try {
//            if (personaSeleccionada != null) {
//                personaSeleccionada = personaUtil.obtenerDatosPersona(numIdentificacionPersona);
//                //JsfUtil.addSuccessMessage("Persona encontrada");
//            } else {
//                JsfUtil.addErrorMessage("Identificación incorrecta o no válida");
//            }
//
//        } catch (ServicioExcepcion ex) {
//            JsfUtil.addErrorMessage("Ocurrió un error");
//            Logger
//                    .getLogger(ControladorNuevasMatriculas.class
//                            .getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void cargarListaComparecientes() {
        try {
            Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
            listaTramDetComparecientes.clear();
            RepertorioUsuario repertorioUsuarioSelec = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
                    repertorioSeleccionado.getRepNumero(), DataManagerSesion.getUsuarioLogeado().getUsuId(), "MAT");
            //TRAE LOS PRINCIPALES DE LA PROPIEDAD
            listaTramDetComparecientes = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "S");

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

    public void crearDetallePropiedad() {
        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.crearDetallePropiedad()");

        calcularTotalAcciones();
        BigDecimal valorAdicional = new BigDecimal(0);
        valorAdicional = propiedadDetalleSeleccionada.getPdtValor();
        BigDecimal bigDec100 = new BigDecimal(100);
        BigDecimal bigDec0 = new BigDecimal(0);
        if (totalAcc.add(valorAdicional).compareTo(bigDec0) == 1)//valor a agregar es mayor que 0
        {

            if ((totalAcc.add(valorAdicional)).compareTo(bigDec100) <= 0) {  //if (totalAcc + valorAdicional <= 100) { //si el valor a agregar es menor o igual que 100
                //propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(personaSeleccionada.getPerId()));
                //BigInteger idusuario = BigInteger.valueOf(personaSeleccionada.getPerId());
                if (tramiteDetalleSeleccionado != null) { //verifica si seleccionó un compareciente

                    propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(tramiteDetalleSeleccionado.getPerId().getPerId()));
                    BigInteger idusuario = BigInteger.valueOf(tramiteDetalleSeleccionado.getPerId().getPerId());
                    Persona persona = personaServicio.find(new Persona(), idusuario.longValue());
                    propiedadDetalleSeleccionada.setPersona(persona);
                    propiedadDetalleSeleccionada.setPdtEstado("A");
                    propiedadDetalleSeleccionada.setTempPropietario("N");
                    boolean yaExiste = false;
                    for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) { //recorre la lista para encontrar un registro con el mismo id de persona y solo sumar el valor
                        if (tramiteDetalleSeleccionado.getPerId().getPerId().equals(propiedadDetalle.getPdtPerId().longValue())) {
                            propiedadDetalle.setPdtValor(propiedadDetalle.getPdtValor().add(propiedadDetalleSeleccionada.getPdtValor()));
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

            if (totalAcc.compareTo(bigDec100) == 0) { //si es igual a 100
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
            Tramite tramiteSeleccionado = repertorioSeleccionado.getTraNumero();
            List<TramiteDetalle> lista = servicioTramiteDetalle.listarPor_NumTramite_TipoTramite_Persona(tramiteSeleccionado.getTraNumero(), repertorioSeleccionado.getRepTtrId(), numPerId);
            return lista.isEmpty();//deshabilito si la lista esta vaica
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorNuevasMatriculas.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;

    }

    public void borrarDetallePropiedad(int row) {
//        Iterator<PropiedadDetalle> iterador = listaPropiedadDetalle.iterator();
//        while (iterador.hasNext()) {
//            PropiedadDetalle next = iterador.next();
//            if (listaPropiedadDetalle.indexOf(next) == filaSeleccionada) {
//                iterador.remove();
//                break;
//            }
//        }
        propiedadDetElim.setPdtEstado("I");
        listaPropiedadDetalle.set(row, propiedadDetElim);
        if (propiedadDetElim.getPdtId() != null) {
            listaPropDetActualizarEstado.add(propiedadDetElim);
        }

        listaPropiedadDetalle.remove(row);
        calcularTotalAcciones();
        disabledBtnAgregarDerAcc = false;
    }

    public void crearDetallePropiedadCompradores() {
        calcularTotalAcciones(); //calcula el total antes de

        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
        listaTramDetComparecientes.clear();
        RepertorioUsuario repertorioUsuarioSelec = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
                repertorioSeleccionado.getRepNumero(), DataManagerSesion.getUsuarioLogeado().getUsuId(), "MAT");
        listaTramDetComparecientes = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "S");
        boolean bolContieneCompradorAgregado = false; //boleano para 
        BigDecimal valorAdicional = new BigDecimal(100).subtract(totalAcc);
        for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
            if (propiedadDetalle.getTempPropietario().equals("S")) {//ya se agrego compradores, entonces actualizo el porcentaje de acciones
                bolContieneCompradorAgregado = true;//si al menos encuentra un comprador
                propiedadDetalle.setPdtValor(propiedadDetalle.getPdtValor().add(valorAdicional));
            }
        }
        if (!bolContieneCompradorAgregado) { //no se han agregado compradores, entonces agrego
            for (TramiteDetalle tramDetCompComprador : listaTramDetComparecientes) {
                if (tramiteDetalleSeleccionado != null) { //verifica si seleccionó un compareciente
                    propiedadDetalleSeleccionada = new PropiedadDetalle();
                    propiedadDetalleSeleccionada.setPdtClase("C");//CONJUNTO
                    propiedadDetalleSeleccionada.setPdtValor(new BigDecimal(100).subtract(totalAcc));
                    propiedadDetalleSeleccionada.setPdtEstado("A");
                    propiedadDetalleSeleccionada.setPdtPerId(BigInteger.valueOf(tramDetCompComprador.getPerId().getPerId()));
                    propiedadDetalleSeleccionada.setTempPropietario("S");
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
    }

    public void guardarDerAcc() throws ServicioExcepcion {

        calcularTotalAcciones();

        BigDecimal bigDec100 = new BigDecimal(100);
        if (totalAcc.compareTo(bigDec100) == 0) {   //if (totalAcc == 100) {
            for (PropiedadDetalle propiedadDetalle : listaPropiedadDetalle) {
                if (propiedadDetalle.getPdtEstado().equals("A")) {

                    propiedadDetalle.setPdtTipo("GDA");
                    propiedadDetalle.setPdtUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    propiedadDetalle.setPdtUmdId(BigInteger.valueOf(propiedadNueva.getUmdId().getUmdId()));
                    propiedadDetalle.setPdtUmdAbreviatura(propiedadNueva.getUmdId().getUmdAbreviatura());
                    propiedadDetalle.setPdtFHR(Calendar.getInstance().getTime());
                    propiedadDetalle.setPrdMatricula(propiedadNueva);
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
            guardarRepertorioPropiedad();
            JsfUtil.addSuccessMessage("Derechos y Acciones guardado");
        } else {
            JsfUtil.addErrorMessage("Debe llenar el 100%");
        }
    }

    public void crearPropiedadDetalle(PropiedadDetalle propiedadDetalle) {
        try {
            PropiedadDetalle propDetCargada = servicioPropiedadDetalle.obtener_Por_Propiedad_Tipo_Estado_Propiedad2(
                    propiedadDetalle.getPrdMatricula().getPrdMatricula(), propiedadDetalle.getPdtTipo(), propiedadDetalle.getPdtPrdMatricula());
            if (propDetCargada == null) {
                servicioPropiedadDetalle.create(propiedadDetalle);
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
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

    //******** UNIFICACIONES *****
//    public void seleccionarPropiedadDeUnificacion(TramiteValor tramValParam) {
//
//        listaPropiedadesUnf = new ArrayList<>();
//
//        tramiteValorSeleccionado.setTrvPrincipal(false);
//
//        for (TramiteValor tramVal : listaTramiteValor) {
//            if (tramVal != tramValParam) {
//                tramVal.setTrvPrincipal(false);//solo uno puede seleccionarse, el resto como falso.
//            }
//            servicioTramiteValor.edit(tramVal);//actualizo todos los tramites valor
//        }
//
//    }
    //**//
//    public void agregarUnificacion(TramiteValor tramValor){
//        if(propSecundariaUNF == true){
//            listaTramiteValorSeleccionados.add(tramValor);
//        }        
//    }
    //*****************************************//
//    public void onRowSelect(SelectEvent event) {
//    if (event != null && event.getObject() != null && 
//        event.getObject() instanceof TramiteValor) {
//        if (listaTramiteValorSeleccionados== null) {
//            listaTramiteValorSeleccionados= new ArrayList<TramiteValor>();
//        }
//        if (!listaTramiteValorSeleccionados.contains((TramiteValor) event.getObject())) {
//            listaTramiteValorSeleccionados.add((TramiteValor) event.getObject());
//        }
//    }
//}
//public void onRowUnselect(UnselectEvent event) {
//    if (event != null && event.getObject() != null && 
//        event.getObject() instanceof TramiteValor && 
//        listaTramiteValorSeleccionados != null && listaTramiteValorSeleccionados.contains((TramiteValor) event.getObject())) {
//        listaTramiteValorSeleccionados.remove((TramiteValor) event.getObject());
//    }
//}
    public boolean verificarMismaUnidadMedida() {
        int contador = 0;
        Long auxIdUndMedida = Long.valueOf("0");
        for (TramiteValor tramiteValor : listaTramiteValor) {
            Propiedad prop = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tramiteValor.getTraNumPredio(), tramiteValor.getTrvNumCatastro());
            if (prop != null) {
                Long idUndMedida = prop.getUmdId().getUmdId();
                if (idUndMedida.longValue() != auxIdUndMedida.longValue()) {
                    contador++;
                }
                auxIdUndMedida = idUndMedida;
            }
        }
        if (contador == 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean verificarTodasPropiedadesRevisadas() {
        int contador = 0;
        for (TramiteValor tramiteValor : listaTramiteValor) {
            if (tramiteValor.getTrvAccion() != null) {
                if (tramiteValor.getTrvAccion() >= 1) //si ya fue revisado       
                {
                    contador++;
                }
            }
        }
        if (contador == listaTramiteValor.size()) {
            return true;
        } else {
            return false;
        }

    }

    public void preBuscarPropiedadUnf() {
        numDocumento = null;
        boolDocEncontrado = false;
        listaTramiteValorUnf = new ArrayList<>();
        //MP        
        List<Propiedad> listPropiedadNoAgregadas = new ArrayList<>();
        listPropiedadNoAgregadas.clear();
        Propiedad prop = new Propiedad();
        prop = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tramiteValorSeleccionado.getTraNumPredio(), tramiteValorSeleccionado.getTrvNumCatastro());

        if (verificarTodasPropiedadesRevisadas()) { //si ya fue revisado  
            if (verificarMismaUnidadMedida() == true) {
                if (verificarUnificacion(prop) == false && verificarDivision(prop) == false) { //validar si el tramite valor seleccionado corresponde a una propiedad que ha sido parte de un proceso de unificacion o division
                    for (TramiteValor tramiteValor : listaTramiteValor) {
                        // verificar si las propiedades que se quieren agregar aun no pertencen a una unificacion                            
                        Propiedad propUnif = new Propiedad();
                        propUnif = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tramiteValor.getTraNumPredio(), tramiteValor.getTrvNumCatastro());
                        if (verificarUnificacion(propUnif) == false && verificarDivision(propUnif) == false) {
                            if (tramiteValor.getTrvPrincipal() != null && !tramiteValor.getTrvPrincipal()) { //si fue seleccionada
                                if (tramiteValor != tramiteValorSeleccionado) {
                                    if (!listaTramiteValorUnf.contains(tramiteValor)) {//si no está ya agregado, agrego
                                        listaTramiteValorUnf.add(tramiteValor);
                                    }
                                }
                            }
                        } else {
                            if (propUnif != null) {
                                listPropiedadNoAgregadas.add(propUnif);
                            }
                        }
                    }
                } else {
                    JsfUtil.addErrorMessage("No se puede agregar Propiedades porque la Propiedad seleccionada: " + prop.getPrdMatricula()
                            + " con Predio: " + prop.getPrdPredio()
                            + " y Catastro: " + prop.getPrdCatastro() + " ya es parte de una Unificación o División");
                }

            } else {
                JsfUtil.addErrorMessage("Las propiedades deben tener la misma Unidad de Medida");
            }
        } else {
            JsfUtil.addErrorMessage("No se han revisado todas las propiedades");
        }

        if (!listPropiedadNoAgregadas.isEmpty()) {
            String propiedadesNoAgregadas = "";
//            for (Propiedad propNoAgreg : listPropiedadNoAgregadas) {
//                propiedadesNoAgregadas = propiedadesNoAgregadas + "Matrícula: " + propNoAgreg.getPrdMatricula()
//                        + " con Predio: " + propNoAgreg.getPrdPredio()
//                        + " y Catastro: " + propNoAgreg.getPrdCatastro() + " - ";
//            }
            JsfUtil.addWarningMessage("Existen propiedades no agregadas porque ya son parte de una Unificación o División"
                    + propiedadesNoAgregadas);
        }
        //ANTERIOR: BM
//        for (TramiteValor tramiteValor : listaTramiteValor) {
//            if (tramiteValor.getTrvAccion() != null) {
//                if (tramiteValor.getTrvAccion() >= 1) //si ya fue revisado
//                {
//                    if (tramiteValor.getTrvPrincipal() != null && tramiteValor.getTrvPrincipal()) { //si fue seleccionada
//                        if (tramiteValor != tramiteValorSeleccionado) {
//                            if (!listaTramiteValorUnf.contains(tramiteValor)) {//si no está ya agregado, agrego
//
//                                listaTramiteValorUnf.add(tramiteValor);
//                            }
//                        }
//                    }
//
//                }
//
//            }
//        }
        buscarPropiedadUnf();//llena la lista de propiedades de unificacion,
    }

    public Boolean verificarUnificacion(Propiedad propiedad) {

        Boolean esPropiedadUnificada = false;
        if (propiedad != null) {
            PropiedadDetalle propDet = servicioPropiedadDetalle.obtener_Por_pdtPrdPropiedad_Tipo_Repertorio_EstadoActivo(propiedad.getPrdMatricula(), "UNF", repertorioSeleccionado.getRepNumero());
            if (propDet != null) {
                esPropiedadUnificada = true;
            }
        }

        return esPropiedadUnificada;
    }

    public Boolean verificarDivision(Propiedad propiedad) {

        Boolean esPropiedaDividida = false;
        if (propiedad != null) {
            PropiedadDetalle propDet = servicioPropiedadDetalle.obtener_Por_pdtPrdPropiedad_Tipo_Repertorio_EstadoActivo(propiedad.getPrdMatricula(), "DIV", repertorioSeleccionado.getRepNumero());
            if (propDet != null) {
                esPropiedaDividida = true;
            }
        }

        return esPropiedaDividida;
    }

    public void buscarPropiedadUnf() {
        try {
            for (TramiteValor traval : listaTramiteValorUnf) {
                propiedadSeleccionadaUnf = NuevasMatriculasServicio.buscarPropiedadConCatastroPredio(
                        traval.getTrvNumCatastro(), traval.getTraNumPredio());
                if (propiedadSeleccionadaUnf != null) {
                    if (!listaPropiedadesUnf.contains(propiedadSeleccionadaUnf)) {
                        listaPropiedadesUnf.add(propiedadSeleccionadaUnf);
//                        JsfUtil.addSuccessMessage("Propiedad agregada.");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

//    public void agregarPropiedadListaUnf() {
//        if (!listaPropiedadesUnf.contains(propiedadSeleccionadaUnf)) {
//            listaPropiedadesUnf.add(propiedadSeleccionadaUnf);
//        } else {
//            JsfUtil.addErrorMessage("Las propiedades no se pueden repetir");
//        }
//    }
    public void eliminarPropiedadListaUnf() throws ServicioExcepcion {

        List<PropiedadDetalle> listaPropDetaBDD = servicioPropiedadDetalle.listar_Por_Propiedad_Tipo(propiedadNueva.getPrdMatricula(), "UNF");
        if (!listaPropDetaBDD.isEmpty()) {

            for (PropiedadDetalle propiedadDetalle : listaPropDetaBDD) {
                Propiedad propiedadHija = NuevasMatriculasServicio.find(new Propiedad(), propiedadDetalle.getPdtPrdMatricula());
                if (propiedadHija.getPrdMatricula().equals(propiedadSeleccionadaUnf.getPrdMatricula())) {
                    servicioPropiedadDetalle.remove(propiedadDetalle);
                    propiedadHija.setPrdEstadoRegistro("A");
                    NuevasMatriculasServicio.edit(propiedadHija);
                }
            }

        }
        listaPropiedadesUnf.remove(propiedadSeleccionadaUnf);

        inicializarPropiedad();
    }

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
    public void guardarUnificaciones() {
        try {
            totalSuperficieUnf = new BigDecimal(0);
            for (Propiedad propiedad : listaPropiedadesUnf) {
                totalSuperficieUnf = totalSuperficieUnf.add(propiedad.getPrdSuperficie());
            }
            if (listaPropiedadesUnf.size() >= 2) {
                if (totalSuperficieUnf.compareTo(propiedadNueva.getPrdSuperficie()) == 0) {

                    guardarPropiedad();
                    //totalSuperficieUnf = new BigDecimal(0);
                    for (Propiedad propiedad : listaPropiedadesUnf) {
                        //propiedades a unificar
                        propiedad.setPrdEstadoPropiedad("AN");
                        propiedad.setPrdEstadoRegistro("I");
                        //totalSuperficieUnf = totalSuperficieUnf.add(propiedad.getPrdSuperficie());
                        NuevasMatriculasServicio.edit(propiedad);
//propiedad detalle
                        PropiedadDetalle propiedadDetalleNuevo = new PropiedadDetalle();
                        propiedadDetalleNuevo.setPrdMatricula(propiedadNueva);
                        propiedadDetalleNuevo.setPdtPrdMatricula(propiedad.getPrdMatricula());
                        propiedadDetalleNuevo.setPdtUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        propiedadDetalleNuevo.setPdtFHR(Calendar.getInstance().getTime());
                        propiedadDetalleNuevo.setPdtTipo("UNF");
                        propiedadDetalleNuevo.setPdtUmdId(BigInteger.valueOf(propiedadNueva.getUmdId().getUmdId()));
                        propiedadDetalleNuevo.setPdtUmdAbreviatura(propiedadNueva.getUmdId().getUmdAbreviatura());
                        propiedadDetalleNuevo.setPdtEstado("A");
                        propiedadDetalleNuevo.setPdtRepNumero(repertorioSeleccionado.getRepNumero());
                        crearPropiedadDetalle(propiedadDetalleNuevo);

                    }
                    //propiedad unificada
                    //propiedadNueva.setPrdSuperficie(totalSuperficieUnf);
                    propiedadNueva.setPrdEstadoRegistro("A");
                    llenarDescripcion1();
                    NuevasMatriculasServicio.edit(propiedadNueva);
                    //JsfUtil.addSuccessMessage("Unificaciones realizadas");
                } else {
                    JsfUtil.addErrorMessage("Total de superficies de las propiedades a unificar no es igual a la superficie de la Propiedad Principal");
                }

            } else {
                JsfUtil.addErrorMessage("Necesita mínimo 2 elementos");
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Ocurrió un error");
            Logger.getLogger(ControladorNuevasMatriculas.class
                    .getName()).log(Level.SEVERE, null, e);

        }
    }

    //DIVISIONES
    public void preBuscarPropiedadDiv() {
        numDocumento = null;
        boolDocEncontrado = false;
        listaTramiteValorDiv = new ArrayList<>();
        List<Propiedad> listPropiedadNoAgregadas = new ArrayList<>();
        Propiedad prop = new Propiedad();
        prop = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tramiteValorSeleccionado.getTraNumPredio(), tramiteValorSeleccionado.getTrvNumCatastro());
        if (verificarTodasPropiedadesRevisadas()) { //si ya fue revisado  
            if (verificarMismaUnidadMedida()) {
                if (verificarUnificacion(prop) == false && verificarDivision(prop) == false) {
                    for (TramiteValor tramiteValor : listaTramiteValor) {
                        Propiedad propUnif = new Propiedad();
                        propUnif = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tramiteValor.getTraNumPredio(), tramiteValor.getTrvNumCatastro());
                        if (verificarUnificacion(propUnif) == false && verificarDivision(propUnif) == false) {
                            if (tramiteValor.getTrvPrincipal() != null && !tramiteValor.getTrvPrincipal()) { //si fue seleccionada
                                if (tramiteValor != tramiteValorSeleccionado) {
                                    if (!listaTramiteValorDiv.contains(tramiteValor)) {//si no está ya agregado, agrego
                                        listaTramiteValorDiv.add(tramiteValor);
                                    }
                                }
                            }
                        } else {
                            if (propUnif != null) {
                                listPropiedadNoAgregadas.add(propUnif);
                            }
                        }

                    }
                } else {
                    JsfUtil.addErrorMessage("No se puede agregar Propiedades porque la Propiedad seleccionada: " + prop.getPrdMatricula()
                            + " con Predio: " + prop.getPrdPredio()
                            + " y Catastro: " + prop.getPrdCatastro() + " ya es parte de una Unificación o División");
                }
            } else {
                JsfUtil.addErrorMessage("Las propiedades deben tener la misma Unidad de Medida");
            }
        } else {
            JsfUtil.addErrorMessage("No se han revisado todas las propiedades");
        }

        if (!listPropiedadNoAgregadas.isEmpty()) {
            String propiedadesNoAgregadas = "";
//            for (Propiedad propNoAgreg : listPropiedadNoAgregadas) {
//                propiedadesNoAgregadas = propiedadesNoAgregadas + "Matrícula: " + propNoAgreg.getPrdMatricula()
//                        + " con Predio: " + propNoAgreg.getPrdPredio()
//                        + " y Catastro: " + propNoAgreg.getPrdCatastro() + " - ";
//            }
            JsfUtil.addWarningMessage("Existen propiedades no agregadas porque ya son parte de una Unificación o División"
                    + propiedadesNoAgregadas);
        }
        buscarPropiedadDiv();//llena la lista de propiedades de divisiones,
    }

    public void buscarPropiedadDiv() {
        try {
            for (TramiteValor traval : listaTramiteValorDiv) {
                Propiedad propiedadSeleccionadaDiv = NuevasMatriculasServicio.buscarPropiedadConCatastroPredio(
                        traval.getTrvNumCatastro(), traval.getTraNumPredio());
                if (propiedadSeleccionadaDiv != null) {
                    if (!listaPropiedadesDiv.contains(propiedadSeleccionadaDiv)) {
                        listaPropiedadesDiv.add(propiedadSeleccionadaDiv);
                    }
                }
            }
            calcularTotalSuperficeDiv();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void calcularTotalSuperficeDiv() {
        totalSuperficie = 0;
        if (!listaPropiedadesDiv.isEmpty()) {
            for (Propiedad propiedad : listaPropiedadesDiv) {
                totalSuperficie += propiedad.getPrdSuperficie().doubleValue();
            }
        }

    }

    public void eliminarPropiedadListaDiv(Propiedad propiedad) {

        List<PropiedadDetalle> listaPropDetaBDD = servicioPropiedadDetalle.listar_Por_Propiedad_Tipo(propiedadNueva.getPrdMatricula(), "DIV");
        if (!listaPropDetaBDD.isEmpty()) {
            Propiedad propiedadPadre = propiedadNueva;
            for (PropiedadDetalle propiedadDetalle : listaPropDetaBDD) {
                if (propiedadDetalle.getPdtPrdMatricula().equals(propiedad.getPrdMatricula())) {
                    servicioPropiedadDetalle.remove(propiedadDetalle);
                }
            }
            propiedadPadre.setPrdEstadoRegistro("A");
            NuevasMatriculasServicio.edit(propiedadPadre);
        }
        listaPropiedadesDiv.remove(propiedad);
    }

    public void guardarDivisiones() {
        try {
            totalSuperficie = 0.0;
//            secuenciaControlador.generarSecuencia("MAT");
//            //obtengo el siguiente valor de la secuencia
//            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
//            int auxSecuencia = getSecuencia().getSecActual();
//            getSecuencia().setSecActual(auxSecuencia + 1);
////                System.out.print("Secuencia-- " + auxSecuencia);
//            Long idGenerado = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
//
//            //        setMatricula(idGenerado);
//            secuenciaServicio.guardar(getSecuencia());
//
//            setMatricula(idGenerado);
            for (Propiedad propiedad : listaPropiedadesDiv) {
                totalSuperficie += propiedad.getPrdSuperficie().doubleValue();
            }
            if (totalSuperficie == propiedadNueva.getPrdSuperficie().doubleValue()) {
                guardarPropiedad();

                for (Propiedad propiedad : listaPropiedadesDiv) {
                    propiedad.setPrdEstadoPropiedad("AN");
                    propiedad.setPrdEstadoRegistro("A");

//                    propiedad.setPrdMatricula(idGenerado.intValue());
                    propiedad.setPrdUserCreador(" ");
                    propiedad.setPrdClasePropiedad(" ");
                    propiedad.setPrdEstadoRegistro("A");
                    propiedad.setPrdSector(" ");
                    propiedad.setPrdTdtParIdH(1L);
                    propiedad.setPrdFHM(Calendar.getInstance().getTime());
                    propiedad.setPrdFHR(Calendar.getInstance().getTime());
                    propiedad.setPrdUserModificacion(" ");
                    propiedad.setPrdUbicacion(" ");
                    propiedad.setPrdEstadoPropiedad("AC");
                    propiedad.setPrdPadreMatricula(propiedadNueva);
                    NuevasMatriculasServicio.edit(propiedad);

//                    for (Lindero lindero : propiedad.getListalinderos()) {
//                        lindero.setLdrFHR(Calendar.getInstance().getTime());
//                        lindero.setLdrUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
//                        lindero.setPrdMatricula(propiedad);
//                        servicioLindero.create(lindero);
//                    }
                    //PROPIEDAD DETALLE
                    PropiedadDetalle propiedadDetalleNuevo = new PropiedadDetalle();
                    propiedadDetalleNuevo.setPrdMatricula(propiedadNueva);
                    propiedadDetalleNuevo.setPdtPrdMatricula(propiedad.getPrdMatricula());
                    propiedadDetalleNuevo.setPdtUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    propiedadDetalleNuevo.setPdtFHR(Calendar.getInstance().getTime());
                    propiedadDetalleNuevo.setPdtTipo("DIV");
                    propiedadDetalleNuevo.setPdtUmdId(BigInteger.valueOf(propiedadNueva.getUmdId().getUmdId()));
                    propiedadDetalleNuevo.setPdtUmdAbreviatura(propiedadNueva.getUmdId().getUmdAbreviatura());
                    propiedadDetalleNuevo.setPdtEstado("A");
                    propiedadDetalleNuevo.setPdtRepNumero(repertorioSeleccionado.getRepNumero());
                    crearPropiedadDetalle(propiedadDetalleNuevo);
                }
                propiedadNueva.setPrdEstadoPropiedad("AN");
                propiedadNueva.setPrdEstadoRegistro("I");
                NuevasMatriculasServicio.edit(propiedadNueva);
                JsfUtil.addSuccessMessage("Divisiones realizadas");
            } else {
                JsfUtil.addErrorMessage("El total: " + totalSuperficie + " no corresponde al valor "
                        + propiedadNueva.getPrdSuperficie().doubleValue()
                        + " de la superficie a dividir");

            }
        } catch (Exception e) {
            Logger.getLogger(ControladorNuevasMatriculas.class
                    .getName()).log(Level.SEVERE, null, e);

        }
    }

    //**TERMINAR PROCESO**
    public void redireccionarPaginaSeleccion() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/matriculacion/Matriculacion.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(ControladorNuevasMatriculas.class
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
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/matriculacion/Matriculacion.xhtml");
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void verificarPosicion() throws IOException {
        if (!propiedadNueva.getPrdTdtParNombre().isEmpty()) {
            localizarPorParroquia();
        }
        if (propiedadNueva.getPrdEspacial().equals("") || propiedadNueva.getPrdEspacial().equals("0")) {

        } else {
            setGeoModel(new DefaultMapModel());
            LatLng latlng = new LatLng(Double.parseDouble(propiedadNueva.getPrdLatitud()), Double.parseDouble(propiedadNueva.getPrdLongitud()));
            Marker point = new Marker(latlng);
            geoModel.addOverlay(point);
            setCenterGeoMap(latlng.getLat() + "," + latlng.getLng());
            setZoom(17);
        }
    }

    public void prepararDialogoComparecientes() throws ServicioExcepcion {
        controladorInscripcionProceso.setNumRepertorio(numRepertorio.toString());
        controladorInscripcionProceso.setNumTramite(repertorioSeleccionado.getTraNumero().getTraNumero().toString());
        controladorInscripcionProceso.setContratoDescripcion(repertorioSeleccionado.getRepTtrDescripcion());
        controladorInscripcionProceso.setTipoTramite(servicioTipoTramite.buscarPorID(new Long(repertorioSeleccionado.getRepTtrId())));
        controladorInscripcionProceso.setListaTipoCompareciente(tipoTramiteComparecienteDao.listarTipoComparecientePorTipoTramite(Long.valueOf(repertorioSeleccionado.getRepTtrId())));
        controladorInscripcionProceso.setRepertorio(repertorioSeleccionado);
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
            String replace = propiedadNueva.getPrdTdtParNombre().replace(espacio, reemplazo);

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
        if(lat != null && lng != null){
            getPropiedadNueva().setPrdEspacial(lat + "," + lng);
        getPropiedadNueva().setPrdLatitud(String.valueOf(lat));
        getPropiedadNueva().setPrdLongitud(String.valueOf(lng));
        }
        else{
            JsfUtil.addWarningMessage("Seleccione una ubicación");
        }
        
    }

}
