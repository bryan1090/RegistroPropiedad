/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.NotariaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Caja;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CajaServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.TipoLibroDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Parametros;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.TipoCompareciente;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.ParametrosServicio;
import com.rm.empresarial.servicio.TipoTramiteComparecienteServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExternalContext;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorRepertorio")
@ViewScoped
public class ControladorRepertorio implements Serializable {

    /**
     * Creates a new instance of ControladorRepertorio
     */
    //SERVICIOS
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Inject
    @Getter
    @Setter
    PersonaUtil personaUtil;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Getter
    @Setter
    private Date fechaIni;

    @Getter
    @Setter
    private Date fechaDia;

    @Getter
    @Setter
    private Date fechaFin;

    @Inject
    ReporteUtil reporteUtil;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @EJB
    private RepertorioServicio servicioRepertorio;

    @EJB
    private TramiteServicio servicioTramite;

    @EJB
    private NotariaDao notariaDao;

    @EJB
    private TipoLibroDao tipoLibroDao;

    @EJB
    private FacturaServicio servicioFactura;

    @EJB
    private FacturaDetalleServicio servicioFacturaDetalle;

    @EJB
    private CajaServicio servicioCaja;

    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;

    @EJB
    private TipoTramiteServicio servicioTipoTramite;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private UsuarioServicio servicioUsuario;

    @EJB
    private CargaLaboralServicio servicioCargaLaboral;

    @EJB
    RepertorioDao repertorioDao;

    @EJB
    InstitucionDao institucionDao;

    @EJB
    TipoTramiteComparecienteServicio servicioTipoTramiteCompareciente;

    @Inject
    private TramiteUtil tramiteUtil;

    @EJB
    private TramiteDetalleDao tramiteDetalleDao;

    @EJB
    ParametrosServicio servicioParametros;
    //objetos, variables y listas
    @Getter
    @Setter
    List<Repertorio> listaRepertorio;

    @Getter
    @Setter
    List<Tramite> listaTramites;

    @Getter
    @Setter
    List<TramiteDetalle> listaTramitesDetallesSelec;

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramitesDetalle;

    @Getter
    @Setter
    private List<Notaria> listaNotaria;

    @Getter
    @Setter
    private List<TipoLibro> listaTipoLibro;

    @Getter
    @Setter
    List<Object[]> listaTramiteCaja;

    @Getter
    @Setter
    List<Factura> listaFacturas;

    @Getter
    @Setter
    private Repertorio repertorioMenor;
    @Getter
    @Setter
    private Repertorio repertorioMayor;

    @Getter
    @Setter
    private Institucion institucion;

    @Getter
    @Setter
    private List<Repertorio> listaRepertorios;

    @Getter
    @Setter
    private List<Repertorio> listaRepertorioLibro;

    @Getter
    @Setter
    private List<String> listaEstadoReTramite;

    @Getter
    @Setter
    private Repertorio repertorio;

    @Getter
    @Setter
    private Repertorio repertorioTotal;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private Secuencia secuencia;

    @Getter
    @Setter
    private CargaLaboral cargaLaboral;

    @Getter
    @Setter
    private Usuario usuarioAsignado;

    @Getter
    @Setter
    private String usuarioNombre;

    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuario;

    @Getter
    @Setter
    private CargaLaboral cargaLaboralm;

    @Getter
    @Setter
    private Date FHR;

    @Getter
    @Setter
    private List<Repertorio> listaRepertorioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoTramiteCompareciente> listaTipoTramiteComparecientes = new ArrayList<>();

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleSeleccionado;

    @Getter
    @Setter
    TipoTramiteCompareciente tipoTramiteComparecienteSelec;

    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    @Getter
    @Setter
    private Boolean bolTieneRepertorios;

    @Getter
    @Setter
    private String identificacion;
    @Getter
    @Setter
    private List<TipoTramite> listaTipoTramiteFiltrada;
    @Getter
    @Setter
    List<Long> listaTipoTramiteId;

    @Getter
    @Setter
    private Integer numCopias;

    @Getter
    @Setter
    private Boolean bolDeshabilitarSel = Boolean.TRUE;

    public ControladorRepertorio() {
        System.out.println("com.rm.empresarial.controlador.ControladorRepertorio.<init>()");
        listaRepertorio = new ArrayList<>();
        listaTramites = new ArrayList<>();
        listaTramitesDetallesSelec = new ArrayList<>();
        listaRepertorios = new ArrayList<>();
        listaEstadoReTramite = new ArrayList<>();
        repertorio = new Repertorio();
//        tramiteSeleccionado = new Tramite();
        secuencia = new Secuencia();
        tramiteAccion = new TramiteAccion();
        cargaLaboralm = new CargaLaboral();
        identificacion = "";
        setFechaDia(Calendar.getInstance().getTime());
    }

    @PostConstruct
    public void postConstructor() {

        inicializar();

    }

    public void inicializar() {

        try {
//            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
//            tramiteSeleccionado = new Tramite();

            repertorio = servicioRepertorio.buscarUltimoPorRepertorioNumero();
            listaTramites = servicioTramite.buscarTramitePorEstadoYporEstadoRegistro2("REP", "RA");
            listaTramitesDetallesSelec = new ArrayList<>();
            listaTipoTramiteFiltrada = new ArrayList<>();
            listaTipoTramiteId = new ArrayList<>();
            numCopias = 1;

            //CARGANDO VALOR DE CAJA EN TABLA DE TRAMITES
            for (Tramite tram : listaTramites) {
                try {
                    FacturaDetalle facDet = new FacturaDetalle();
                    Factura factura = new Factura();
                    Caja caja = new Caja();
                    facDet = servicioFacturaDetalle.encontrarPorNumeroTramite(tram.getTraNumero().intValue());
                    caja = servicioCaja.encontrarCajaPorId(facDet.getFacId().getCajId().getCajId());

//                    factura = servicioFactura.encontrarFacturaPorNumeroTramite(tram.getTraNumero().intValue());
                    tram.setFactura(facDet.getFacId());
//                    caja = servicioCaja.encontrarCajaPorId(factura.getCajId().getCajId());
                    tram.setCajaNombre(caja.getCajNombre()); //variable transient de Tramite
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }

            //obtener parametro para decidir si permite seleccionar solo el primer registro en la tabla de tramites, o si puede seleccionar cualquiera
            try {
                if(dataManagerSesion.getUsuarioLogeado().getRolId().getRolId() == 1){
                    Parametros parametroRep = servicioParametros.buscarPorParametro("REPINI");
                    bolDeshabilitarSel = !parametroRep.getPrmValor1().trim().equals("1");
                }                

            } catch (Exception e) {
                e.printStackTrace(System.out);
            }

        } catch (ServicioExcepcion ex) {
            ex.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar datos", ""));
            Logger.getLogger(ControladorRepertorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void seleccionarTramite() throws ServicioExcepcion {
        System.out.println("seleccionando...");
        setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(getTramiteSeleccionado().getTraNumero(), "REP"));
        try {
            System.out.println("tram seleccionado: " + tramiteSeleccionado.getTraNumero());
            listaTramitesDetallesSelec = servicioTramiteDetalle.buscarPorNumeroDeTramiteYestado(tramiteSeleccionado.getTraNumero());
            if (tramiteAccion != null) {
                listaEstadoReTramite = new ArrayList<String>();
                for (TramiteDetalle tr : listaTramitesDetallesSelec) {
                    listaEstadoReTramite.add("REINGRESO");
                }
            } else {
                listaEstadoReTramite = new ArrayList<String>();
                for (TramiteDetalle tr : listaTramitesDetallesSelec) {
                    listaEstadoReTramite.add(" ");
                }
            }
            System.out.println("# tram detalles: " + listaTramitesDetallesSelec.size());
        } catch (ServicioExcepcion e) {
            System.out.print(e);
        }
//        if (servicioRepertorio.listarPorNumTramite(tramiteSeleccionado.getTraNumero()).size() == 0) {
        crearYactualizar();

    }

    public void crearYactualizar() throws ServicioExcepcion {
        listaTipoTramiteFiltrada = new ArrayList<>();
        repertorio.setTraNumero(tramiteSeleccionado);

        List<Long> listaTipoTramiteId = new ArrayList<>();

        //CARGO LISTA TIPO TRAMITE
        try {
            listaTipoTramiteId = servicioTramiteDetalle.buscarIdTipoTramitePorNumeroDeTramiteDistinctTtrID(tramiteSeleccionado.getTraNumero());
            for (Long idTipoTramite : listaTipoTramiteId) {
                TipoTramite tipoTramite = servicioTipoTramite.buscarPorID(idTipoTramite.longValue());
                if (!listaTipoTramiteFiltrada.contains(tipoTramite)) {
                    listaTipoTramiteFiltrada.add(tipoTramite);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("tam lista tipo tramites: " + listaTipoTramiteFiltrada.size());

        int contador = 0;
        for (TipoTramite tipoTramite : listaTipoTramiteFiltrada) {
            try {
                //obtengo la secuencia
                secuenciaControlador.generarSecuencia("REP");

                //obtengo el siguiente valor de la secuencia
                setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuencia = getSecuencia().getSecActual();
                getSecuencia().setSecActual(auxSecuencia);

                Long idGenerado = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());

                Boolean valorAsignado = false;
                //ACTUALIZO TRAMITES DETALLE
                for (TramiteDetalle tramDet : listaTramitesDetallesSelec) {
                    if (tramDet.getTdtTtrId().toString().equals(tipoTramite.getTtrId().toString())) {
                        tramDet.setTdtNumeroRepertorio(idGenerado.intValue() + contador);

                        valorAsignado = true;//                        servicioTramiteDetalle.edit(tramDet);
                    }
                }
                if (valorAsignado) {
                    contador++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void confirmarTramite() throws ServicioExcepcion {
        try {
            //setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(getTramiteSeleccionado().getTraNumero(), "DIG"));
            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(getTramiteSeleccionado().getTraNumero(), "REP"));
            if (tramiteAccion == null) {
                //codigo CargaLaboral
                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("DIG", "repertorioElectronico", 1);
            } else {
                setUsuarioAsignado(servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUserAsg()));
                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                int auxCarga = cargaAdd.getCarAsignado() + 1;
                getCargaLaboralm().setCarAsignado((short) auxCarga);
                setListaRepertorios(servicioRepertorio.listarPorNumTramite(getTramiteSeleccionado().getTraNumero()));
                for (Repertorio repEncontrado : listaRepertorios) {
                    repEncontrado.setRepEstado("I");
                    servicioRepertorio.edit(repEncontrado);
                }
            }
            for (TipoTramite tipoTramite : listaTipoTramiteFiltrada) {

                //obtengo la secuencia
                secuenciaControlador.generarSecuencia("REP");

                //obtengo el siguiente valor de la secuencia
                setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuencia = getSecuencia().getSecActual();
                getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
                Long idGenerado = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
                //asigno id con el valor de la secuencia
//CREO REPERTORIO 
                try {
                    Repertorio nuevoRepertorio = new Repertorio(idGenerado, tipoTramite.getTtrId().shortValue(), tipoTramite.getTtrDescripcion(), "A", dataManagerSesion.getUsuarioLogeado().getUsuLogin(), tramiteSeleccionado.getFactura().getFacFecha());
                    nuevoRepertorio.setTraNumero(tramiteSeleccionado);
                    nuevoRepertorio.setRepNumeroImpresion(numCopias);

                    servicioRepertorio.edit(nuevoRepertorio);

                    tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, nuevoRepertorio.getRepNumero().toString(),
                            "Repertorio " + nuevoRepertorio.getRepNumero() + " creado",
                            tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), null);

                } catch (Exception e) {
                    addErrorMessage("Error en guardar repertorio. Datos duplicados");
                }

                for (TramiteDetalle tramDet : listaTramitesDetallesSelec) {
                    if (tramDet.getTdtTtrId().toString().equals(tipoTramite.getTtrId().toString())) {
                        tramDet.setTdtNumeroRepertorio(idGenerado.intValue());
                        servicioTramiteDetalle.edit(tramDet);

                    }
                }
                //actualizo secuencia
//                System.out.println("secuencia: " + secuenciaControlador.getControladorBb().getNumeroTramite());
                secuenciaServicio.guardar(getSecuencia());

                // CREANDO REPERTORIO USUARIO
                try {
                    repertorioUsuario = new RepertorioUsuario(null, "A", dataManagerSesion.getUsuarioLogeado().getUsuLogin(), Calendar.getInstance().getTime());
                    Repertorio repertorioGuardado = servicioRepertorio.find(new Repertorio(), new Long(secuenciaControlador.getControladorBb().getNumeroTramite()));
                    repertorioUsuario.setRepNumero(repertorioGuardado);
                    repertorioUsuario.setUsuId(usuarioAsignado);
                    repertorioUsuario.setRpuTipo("REP");
                    servicioRepertorioUsuario.edit(repertorioUsuario);
                    tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, repertorioUsuario.getRepNumero().getRepNumero().toString(),
                            "Repertorio " + repertorioUsuario.getRepNumero().getRepNumero()
                            + " asignado al usuario " + usuarioAsignado.getUsuLogin(),
                            tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), usuarioAsignado.getUsuLogin());

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            tramiteSeleccionado.setTraEstado("DIG");
            servicioTramite.edit(tramiteSeleccionado);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteSeleccionado.getTraNumero().toString(),
                    "Trámite " + tramiteSeleccionado.getTraNumero() + " enviado a digitalización",
                    "REP", tramiteSeleccionado.getTraEstadoRegistro(), null);
            inicializar();
            //actualizar valor del parametro para decidir si permite seleccionar solo el primer registro en la tabla de tramites, o si puede seleccionar cualquiera
            Parametros parametroRep = servicioParametros.buscarPorParametro("REPINI");
            parametroRep.setPrmValor1("0");
            servicioParametros.edit(parametroRep);
                    //bolDeshabilitarSel = !parametroRep.getPrmValor1().trim().equals("1");
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    public void buscarPersona() throws Exception {
        System.out.println("identificacion " + identificacion);
        setPersona(personaUtil.obtenerDatosPersona(identificacion.trim()));

    }

    public void preCrearTramiteDetalle() {
        System.out.println("com.rm.empresarial.controlador.ControladorRepertorio.preCrearTramite()");
        identificacion = "";
        persona = null;
    }

    public void crearTramiteDetalle() throws ServicioExcepcion {

        if (getPersona().getPerId() != null) {
            TramiteDetalle nuevoTraDet = new TramiteDetalle();
            nuevoTraDet = tramiteDetalleSeleccionado;
            nuevoTraDet.setTdtNumeroRepertorio(0);
            nuevoTraDet.setPerId(getPersona());
            //cambiando columnas de tabla
            nuevoTraDet.setTdtPerTipoIdentificacion(getPersona().getPerTipoIdentificacion());
            nuevoTraDet.setTdtPerIdentificacion(getPersona().getPerIdentificacion());
            nuevoTraDet.setTdtPerNombre(getPersona().getPerNombre());
            nuevoTraDet.setTdtPerApellidoPaterno(getPersona().getPerApellidoPaterno());
            nuevoTraDet.setTdtPerApellidoMaterno(getPersona().getPerApellidoMaterno());
            nuevoTraDet.setTdtPerTipoContribuyente(" ");
            nuevoTraDet.setTdtEstado("A");
            if (!servicioTramiteDetalle.existeTramiteDetalle(tramiteSeleccionado, getPersona(), tramiteDetalleSeleccionado.getTtcId())) {
                servicioTramiteDetalle.create(nuevoTraDet);
                tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteDetalleSeleccionado.getTdtId().toString(),
                        "Creación de Tramite Detalle " + nuevoTraDet.getTdtId() + " del Trámite " + tramiteSeleccionado.getTraNumero(),
                        tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), null);
            } else {
                addErrorMessage("Detalle ya existe");

            }

        }
        listaTramitesDetallesSelec = servicioTramiteDetalle.buscarPorNumeroDeTramiteYestado(tramiteSeleccionado.getTraNumero());
        crearYactualizar();

//        reset();
    }

    public void subirTipoTramite() throws ServicioExcepcion {
        if (tramiteDetalleSeleccionado.getTdtOrden() > 1) {

            BigDecimal AntTdtOrden = BigDecimal.valueOf(tramiteDetalleSeleccionado.getTdtOrden() - 1);
            BigDecimal actualizarTdtOrden = BigDecimal.valueOf(tramiteDetalleSeleccionado.getTdtOrden());
            servicioTramiteDetalle.actualizarTdtOrden(tramiteSeleccionado.getTraNumero(), actualizarTdtOrden, AntTdtOrden);

            servicioTramiteDetalle.actualizarOrdenTramite(tramiteSeleccionado.getTraNumero(), AntTdtOrden, tramiteDetalleSeleccionado.getTdtTtrId());
            listaTramitesDetallesSelec = new ArrayList<>();
            listaTramitesDetallesSelec = servicioTramiteDetalle.buscarPorNumeroDeTramiteYestado(tramiteSeleccionado.getTraNumero());
            crearYactualizar();

        } else {
            addErrorMessage("No permitido. No existe un contrato anterior.");
        }
    }

    public void bajarTipoTramite() throws ServicioExcepcion {

        if (tramiteDetalleSeleccionado.getTdtOrden() == servicioTramiteDetalle.maximoTdtOrden(tramiteDetalleSeleccionado.getTraNumero().getTraNumero())) {
            addErrorMessage("No permitido. No existe un contrato siguiente.");
        } else {

            BigDecimal AntTdtOrden = BigDecimal.valueOf(tramiteDetalleSeleccionado.getTdtOrden() + 1);
            BigDecimal actualizarTdtOrden = BigDecimal.valueOf(tramiteDetalleSeleccionado.getTdtOrden());
            servicioTramiteDetalle.actualizarTdtOrden(tramiteSeleccionado.getTraNumero(), actualizarTdtOrden, AntTdtOrden);

            servicioTramiteDetalle.actualizarOrdenTramite(tramiteSeleccionado.getTraNumero(), AntTdtOrden, tramiteDetalleSeleccionado.getTdtTtrId());
            listaTramitesDetallesSelec = new ArrayList<>();
            listaTramitesDetallesSelec = servicioTramiteDetalle.buscarPorNumeroDeTramiteYestado(tramiteSeleccionado.getTraNumero());
            crearYactualizar();

        }
    }

    public void eliminarTramiteDetalle() throws ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.ControladorRepertorio.eliminarTramiteDetalle()");
        List<TipoCompareciente> listaTipoComp = servicioTipoTramiteCompareciente.listarTipoComparecientePorTipoTramite(tramiteDetalleSeleccionado.getTtcId().getTtrId().getTtrId());
        List<Long> listaIdTipoComparencientes = new ArrayList<>();
        int numComparecientesPorTipo = 0;
        for (TramiteDetalle tramDet : listaTramitesDetallesSelec) {
//            if (tramDet.getTraNumero().equals(tramiteDetalleSeleccionado.getTraNumero())) {
            if (tramDet.getTtcId().getTtrId().equals(tramiteDetalleSeleccionado.getTtcId().getTtrId())) { //si es del mismo contrato ej:compraventa
                if (tramDet.getTtcId().getTpcId().getTpcId().equals(tramiteDetalleSeleccionado.getTtcId().getTpcId().getTpcId()))//si es del mismo tipo compareciente, ej en compraventa: comprador
                {
                    numComparecientesPorTipo++;
                }
//                if (!listaIdTipoComparencientes.contains(tramDet.getTtcId().getTpcId().getTpcId())) {
//                    listaIdTipoComparencientes.add(tramDet.getTtcId().getTpcId().getTpcId());//agrego id tipo compareciente ej: compraventa debe ser 2(comprador y vendedor)
//                }
            }
//            }
        }
        if (numComparecientesPorTipo > 1) {
            tramiteDetalleSeleccionado.setTdtEstado("I");
            servicioTramiteDetalle.edit(tramiteDetalleSeleccionado);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteDetalleSeleccionado.getTdtId().toString(),
                    "Eliminación de Tramite Detalle(Inactivo) " + tramiteDetalleSeleccionado.getTdtId(),
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), null);
        } else {
            addErrorMessage("No permitido. Deben existir mínimo " + listaTipoComp.size() + " comparecientes");

        }

        listaTramitesDetallesSelec = servicioTramiteDetalle.buscarPorNumeroDeTramiteYestado(tramiteSeleccionado.getTraNumero());
        crearYactualizar();

    }

    public void throwNullPointerException() {
        throw new NullPointerException("A NullPointerException!");
    }

    public void throwWrappedIllegalStateException() {
        Throwable t = new IllegalStateException("A wrapped IllegalStateException!");
        throw new FacesException(t);
    }

    public void throwViewExpiredException() {
        throw new ViewExpiredException("A ViewExpiredException!",
                FacesContext.getCurrentInstance().getViewRoot().getViewId());
    }

    public void generarPdfLibroRepertorio(Date fechaIni, Date fechaFin) {
        try {
            SimpleDateFormat formatoEsMX = new SimpleDateFormat(
                    "dd 'de' MMMM 'de' yyyy", new Locale("ES", "MX"));

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("FechaIni", fechaIni);
            parametros.put("FechaFin", fechaFin);
            parametros.put("fechaDia", formatoEsMX.format(getFechaDia()));

            reporteUtil.imprimirReporteEnPDF("repertorioDig", "RepertorioLibro", parametros);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Pdf Generado"));
        } catch (Exception e) {
            System.err.println(e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al generar pdf");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    public void exportarExcelRepertorio(Date fechaIni, Date fechaFin) throws IOException, InvalidFormatException, ServicioExcepcion {
        setListaRepertorioLibro(repertorioDao.listarPorFecha(fechaIni, fechaFin));
        setListaTramitesDetalle(tramiteDetalleDao.listarPorFecha(fechaIni, fechaFin));
        setListaNotaria(notariaDao.listarPorFecha(fechaIni, fechaFin));
        setListaTipoLibro(tipoLibroDao.listarPorFecha(fechaIni, fechaFin));
        setRepertorioMenor(repertorioDao.RepertorioMenor(fechaIni, fechaFin));
        setRepertorioMayor(repertorioDao.RepertorioMayor(fechaIni, fechaFin));
        setRepertorioTotal(repertorioDao.RepertorioTotal(fechaIni, fechaFin));
        setInstitucion(institucionDao.obtenerInstitucion());             
       
        try {
            Repertorio repert = repertorioDao.encontrarRepertorioPorId(repertorioMayor.getRepNumero().toString());
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            File archivo = new File("repertorioLibro.xlsx");
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Acta");
            System.out.println("hoja 1: " + wb.getSheetName(0));
            //LLENANDO EL EXCEL CON LA LISTA DE OBJETOS
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerFont.getBold();
            CellStyle styleHeader = wb.createCellStyle();
            styleHeader.setFont(headerFont);
            styleHeader.setAlignment(HorizontalAlignment.CENTER);
            CellStyle nombre = wb.createCellStyle();
            nombre.setAlignment(HorizontalAlignment.LEFT);
            Row titulo = sheet.createRow(0);
            DateFormat formatoFecha = new SimpleDateFormat("yyyy");

            //titulo.createCell(1).setCellValue("LIBRO REPERTORIO PARA EL AÑO " + formatoFecha.format(getRepertorio().getRepFHR()));
            titulo.createCell(1).setCellValue("LIBRO REPERTORIO PARA EL AÑO " + formatoFecha.format(repert.getRepFHR()));
            titulo.getCell(1).setCellStyle(styleHeader);
            Row header = sheet.createRow(1);
            header.createCell(0).setCellValue("Comparecientes");
            header.createCell(1).setCellValue("Contrato");
            header.createCell(2).setCellValue("Clase de inscripcion");
            header.createCell(3).setCellValue("Fecha/Hora");
            header.createCell(4).setCellValue("Libro e Inscripcion");
            header.createCell(5).setCellValue("Num.");
            header.createCell(6).setCellValue("Fecha Celeb.");
            header.createCell(7).setCellValue("Notaria");
            header.createCell(8).setCellValue("Observaciones");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 1, 6));
            header.getCell(0).setCellStyle(styleHeader);
            header.getCell(1).setCellStyle(styleHeader);
            header.getCell(2).setCellStyle(styleHeader);
            header.getCell(3).setCellStyle(styleHeader);
            header.getCell(4).setCellStyle(styleHeader);
            header.getCell(5).setCellStyle(styleHeader);
            header.getCell(6).setCellStyle(styleHeader);
            header.getCell(7).setCellStyle(styleHeader);
            header.getCell(8).setCellStyle(styleHeader);
//           sheet.addMergedRegion(new CellRangeAddress(0,0,2,4));
            CellStyle style = wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setWrapText(true);
            CellStyle cellStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            cellStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/mm/yyyy"));
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            CellStyle descripcion = wb.createCellStyle();
            descripcion.setWrapText(true);
            descripcion.setVerticalAlignment(VerticalAlignment.TOP);

            int i = 1;//comienza a escribir desde la fila 2
            sheet.autoSizeColumn(i++);
            for (Repertorio repertorio : listaRepertorioLibro) {
                Row row = sheet.createRow(i);
                Cell cell;

                cell = row.createCell(0);
                cell.setCellValue(" ");
                cell.setCellStyle(descripcion);
                sheet.setColumnWidth(0, 5000);

                cell = row.createCell(1);
                cell.setCellValue(repertorio.getRepTtrDescripcion());
                cell.setCellStyle(style);
                sheet.setColumnWidth(1, 5000);

                cell = row.createCell(2);
                cell.setCellValue("     ");
                cell.setCellStyle(style);
                sheet.setColumnWidth(2, 5000);

                cell = row.createCell(3);
                cell.setCellValue(repertorio.getRepFHR());
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(3, 5000);

                cell = row.createCell(5);
                cell.setCellValue(repertorio.getRepNumero());
                cell.setCellStyle(style);
                sheet.setColumnWidth(5, 3000);

                cell = row.createCell(4);
                cell.setCellValue(" ");
                cell.setCellStyle(style);
                sheet.setColumnWidth(4, 5000);

                cell = row.createCell(6);
                cell.setCellValue("    ");
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(6, 3000);

                cell = row.createCell(7);
                cell.setCellValue("    ");
                cell.setCellStyle(cellStyle);
                sheet.setColumnWidth(7, 3000);

                cell = row.createCell(8);
                cell.setCellValue(" ");
                cell.setCellStyle(style);
                sheet.setColumnWidth(8, 5000);
                i++;

            }
            SimpleDateFormat formatoEsMX = new SimpleDateFormat(
                    "dd 'de' MMMM 'de' yyyy", new Locale("ES", "MX"));

            Row piePagina = sheet.createRow(i + 1);
            piePagina.createCell(0).setCellValue("Hoy ingresaron para su inscripcion " + getRepertorioTotal().getRepNumero() + " contratos desde el número " + getRepertorioMenor().getRepNumero() + " hasta el número " + getRepertorioMayor().getRepNumero() + " para el repertorio. " + getInstitucion().getInsNombre() + " , a  " + formatoEsMX.format(getFechaDia()) + ".-EL REGISTRADOR.");

            int b = 2;
            for (TramiteDetalle tramiteDetalle : listaTramitesDetalle) {

                if (tramiteDetalle.getTdtTpcDescripcion() == null || tramiteDetalle.getTdtPerApellidoPaterno() == null || tramiteDetalle.getTdtPerApellidoMaterno() == null || tramiteDetalle.getTdtPerNombre() == null) {

                    sheet.getRow(b).getCell(0).setCellValue(" ");
                } else {
                    sheet.getRow(b).getCell(0).setCellValue(tramiteDetalle.getTdtPerApellidoPaterno().trim() + " " + tramiteDetalle.getTdtPerApellidoMaterno().trim() + " " + tramiteDetalle.getTdtPerNombre().trim() + "-" + tramiteDetalle.getTdtTpcDescripcion());

                }
                b++;
            }
            int c = 2;
            for (Notaria notaria : listaNotaria) {
                if (notaria.getNotNotario() == null) {

                    sheet.getRow(c).getCell(7).setCellValue(" ");
                } else {
                    sheet.getRow(c).getCell(7).setCellValue(notaria.getNotNotario());
                }
                c++;
            }
            int d = 2;
            for (TipoLibro tipoLibro : listaTipoLibro) {
                if (tipoLibro.getTplDescripcion() == null) {

                    sheet.getRow(d).getCell(4).setCellValue(" ");
                } else {
                    sheet.getRow(d).getCell(4).setCellValue(tipoLibro.getTplDescripcion());
                }
                d++;
            }

            String nombreArchivo = "Repertorio_" + Calendar.getInstance().getTime().toString() + ".xlsx";
            ec.responseReset(); // limpia las cabeceras de la respuesta
            ec.setResponseContentType("vnd.ms-excel"); // se define el tipo de contenido del archivo
            ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + nombreArchivo + "\""); //Se descarga desde el navegador

            OutputStream output = ec.getResponseOutputStream();
            wb.write(output);//Se escribe el documento
            fc.responseComplete();//cierra la respuesta

        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al cargar la informacion ", ""));

        }
    }
}
