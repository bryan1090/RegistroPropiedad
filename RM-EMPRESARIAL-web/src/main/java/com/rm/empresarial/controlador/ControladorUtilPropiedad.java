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
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.MatriculacionDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.PropietarioDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.TipoTramiteComparecienteDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoPropiedad;
import com.rm.empresarial.modelo.TipoPropiedadClase;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.UnidMedida;
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
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import com.rm.empresarial.servicio.UnidMedidaServicio;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
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
 * @author DESARROLLO
 */
@Named("controladorUtilPropiedad")
@ViewScoped
public class ControladorUtilPropiedad implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    @EJB
    PropiedadDao servicioPropiedad;

    @Getter
    @Setter
    @EJB
    PropietarioDao propietarioDao;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;
    @Getter
    @Setter
    String predio;

    @Getter
    @Setter
    String catastro;

    @Getter
    @Setter
    private Propiedad propiedadNueva;

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesBuscadas;
    @Getter
    @Setter
    private List<Propietario> listaPropietarios;
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

    //filtrado persona
    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private String identificacion;

    public ControladorUtilPropiedad() {
    }

    public void buscarPropiedades() {
        listaPropiedadesBuscadas = new ArrayList<>();
        listaPropiedadesBuscadas = servicioPropiedad.listarPor_predio_catastro_estadoA(predio, catastro);

    }

    public void buscarPropietarios(Propiedad propiedad) {
        listaPropietarios = propietarioDao.buscarPor_PropiedadMatricula_Estado(propiedad.getPrdMatricula());
    }

    public void inicializarPropiedad() throws ServicioExcepcion {
//        Propiedad propiedadCargada = null;
//        try {
//            propiedadCargada = NuevasMatriculasServicio.buscarPropiedadConCatastroPredio(catastro, predio);
//        } catch (Exception e) {
//            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
//        }
//SI NO ENCONTRÓ LA PROPIEDAD
//        if (propiedadCargada == null) {

        //-- campos de Tramite Valor
        getPropiedadNueva().setPrdCatastro(catastro);
        getPropiedadNueva().setPrdPredio(predio);
//            getPropiedadNueva().setPrdTdtParIdH(tramiteValorSeleccionado.getTrvParId());
//            getPropiedadNueva().setPrdTdtParNombreH(tramiteValorSeleccionado.getTrvParNombre());
//            getPropiedadNueva().setPrdTdtParId(tramiteValorSeleccionado.getTrvParId());
//            getPropiedadNueva().setPrdTdtParNombre(tramiteValorSeleccionado.getTrvParNombre());
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
//            actualizarMatricula();
//ejecutando metodos ajax
        actualizarMatricula();
        seleccionarParroquiaHistorica();
        llenarDescripcion1();
        llenarDescripcion2();
//            esPropiedadNueva = true;
//        }
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

    public void prepararPropiedad() {
        try {
            propiedadNueva = new Propiedad();
            listaLinderos = new ArrayList<>();
            cargarCombos();
            inicializarPropiedad();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorUtilPropiedad.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarCombos() {
        try {
            //consulto propiedad para editar//
//            setPropiedadNueva(NuevasMatriculasServicio.obtenerPropiedad(getCatastro(), getPredio()));
//            tramiteDetalleSelec = servicioTramiteDetalle.buscarPorNumRepertorio(superficie);
            setListaTipoPropiedad(NuevasMatriculasServicio.listaTipoPropiedad());
            setListaUnidMedida(NuevasMatriculasServicio.listarUnidadMedidad());
            listaGrupo = NuevasMatriculasServicio.listarTipoPropiedadClase();
            setListaParroquia(NuevasMatriculasServicio.listaParroquia());

//            setListaLindero(NuevasMatriculasServicio.listarLindero());
//            setLinderoConsultado(NuevasMatriculasServicio.obtenerLinderoPorMatricula(getMatricula().intValue()));
            /**
             * **** cargar lista parroquia para tramite valor y propiedad*****
             */
//            if (tipoMatriculacion != null) {
//                switch (tipoMatriculacion) {
//                    case "UNF":
//                    case "DIV":
//                        if (listaTramiteValor.isEmpty()) {
//                            setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
//                        } else {
//                            TramiteValor tramValor = listaTramiteValor.get(0);
//                            Parroquia parroquia = servicioParroquia.buscarPorId(tramValor.getTrvParId());
//                            listaParroquiaTramValor.add(parroquia);
//                        }
//                        setListaParroquia(NuevasMatriculasServicio.listaParroquia());
//                        break;
//                    default:
//                        setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
//                        setListaParroquia(NuevasMatriculasServicio.listaParroquia());
//                }
//
//            } else {
//                setListaParroquiaTramValor(NuevasMatriculasServicio.listaParroquia());
//                setListaParroquia(NuevasMatriculasServicio.listaParroquia());
//
//            }
            /**
             * ******************
             */
//            listaTipoTramite = NuevasMatriculasServicio.listarTipoTramite();
//            setEstadoPropiedad(getPropiedadNueva().getPrdEstadoPropiedad());
        } catch (Exception e) {
            System.out.print("FALLO AL CONSULTAR");
        }
    }

    public void guardarPropiedad() {
        FacesContext context = FacesContext.getCurrentInstance();

//        marcarComoRevisado();
//        actualizarEstProcRepUsu();
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

//                            tramiteValorSeleccionado.setTrvParId(propiedadNueva.getPrdTdtParId());
//                            tramiteValorSeleccionado.setTrvParNombre(propiedadNueva.getPrdTdtParNombre());
//                            servicioTramiteValor.edit(tramiteValorSeleccionado);
                        context.addMessage(null, new FacesMessage("Propiedad Creada", ""));
                    } catch (Exception e) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad no creada", ""));

                    }

                    try {
                        for (Lindero lindero : listaLinderos) {
                            lindero.setLdrFHR(Calendar.getInstance().getTime());
                            lindero.setLdrUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            lindero.setPrdMatricula(propiedadNueva);
                            servicioLindero.create(lindero);
                        }
//                        context.addMessage(null, new FacesMessage("Linderos agregados", ""));
                    } catch (Exception e) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Linderos no creados ", ""));
                    }

//                        tramiteUtil.insertarTramiteAccion(repertorioSeleccionado.getTraNumero(),
//                                propiedadNueva.getPrdMatricula().toString(),
//                                "Propiedad creada con matricula "
//                                + propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
//                                + " y predio " + propiedadNueva.getPrdPredio(),
//                                repertorioSeleccionado.getTraNumero().getTraEstado(),
//                                repertorioSeleccionado.getTraNumero().getTraEstadoRegistro(),
//                                null);
                } else {
                    context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La propiedad debe tener mínimo 2 linderos.", ""));
                }
            } else {
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Descripción de la Propiedad", ""));
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

    public void preCrearPropietario() {
        persona = null;
        identificacion = "";
        numRepertorio=0L;
    }

    public void crearPropietario(Propiedad propiedad) {
        try {
//            if(persona!=null)
//            {
              Propietario propietario = new Propietario();
            propietario.setPerId(persona);
            propietario.setPprPerIdentificacion(persona.getPerIdentificacion());
            propietario.setPprPerNombre(persona.getPerNombre());
            propietario.setPprPerApellidoPaterno(persona.getPerApellidoPaterno());
            propietario.setPpPerApellidoMaterno(persona.getPerApellidoMaterno());
            propietario.setPprEstado("A");
            propietario.setPprFHR(Calendar.getInstance().getTime());
            propietario.setPprUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            propietario.setPrdMatricula(propiedad);
            propietario.setPrdRepertorio(numRepertorio.intValue());
            propietarioDao.create(propietario);
            JsfUtil.addSuccessMessage("Propietario Creado");  
//            }else{
//              JsfUtil.addErrorMessage("Debe seleccionar una persona");  
//            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void guardarRepertorioPropiedad() {
        //GUARDANDO REPERTORIO PROPIEDAD
//        if (!servicioRepertorioPropiedad.existeRepertorioPropiedad(repertorioSeleccionado.getRepNumero(), propiedadNueva.getPrdMatricula())) {
//            RepertorioPropiedad nuevoRepPro = new RepertorioPropiedad();
//            nuevoRepPro.setPrdMatricula(propiedadNueva);
//            nuevoRepPro.setRepNumero(repertorioSeleccionado);
//            nuevoRepPro.setRpdFHR(Calendar.getInstance().getTime());
//            nuevoRepPro.setRpdUser(DataManagerSesion.getUsuarioLogeado().getUsuLogin());
//            servicioRepertorioPropiedad.create(nuevoRepPro);
//            tramiteUtil.insertarTramiteAccion(nuevoRepPro.getRepNumero().getTraNumero(),
//                    nuevoRepPro.getRepNumero().getRepNumero().toString(),
//                    "Repertorio " + nuevoRepPro.getRpdId() + " asignado a la propiedad "
//                    + propiedadNueva.getPrdMatricula(),
//                    nuevoRepPro.getRepNumero().getTraNumero().getTraEstado(),
//                    nuevoRepPro.getRepNumero().getTraNumero().getTraEstadoRegistro(),
//                    null);
//        }
    }
//*****MAPA

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
        if (lat != null && lng != null) {
            getPropiedadNueva().setPrdEspacial(lat + "," + lng);
            getPropiedadNueva().setPrdLatitud(String.valueOf(lat));
            getPropiedadNueva().setPrdLongitud(String.valueOf(lng));
        } else {
            JsfUtil.addWarningMessage("Seleccione una ubicación");
        }

    }

    public void buscarPersona() throws IOException, ServicioExcepcion {
//        System.out.println("com.rm.empresarial.controlador.RecepcionDocumentacionControlador.buscarPersona()");
        if (this.getIdentificacion() != null && this.getIdentificacion().length() <= 13) {
            try {
                this.setPersona(personaUtil.obtenerDatosPersona(this.getIdentificacion()));
            } catch (ServicioExcepcion e) {
                System.out.print(e);
                this.setPersona(new Persona());
                this.getPersona().setPerIdentificacion("");
            }
        } else {
            this.setPersona(new Persona());
            this.getPersona().setPerIdentificacion("");
        }
    }
}
