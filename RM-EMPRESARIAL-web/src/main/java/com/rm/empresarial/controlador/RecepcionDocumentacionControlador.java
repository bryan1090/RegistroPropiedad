/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.bb.RecepcionDocumentacionControladorBb;
import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.EnviarEmailController;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.UtilFechaEntrega;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.dao.TiempoProcesoDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.dao.TramiteTiempoDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.Ciudad;
import com.rm.empresarial.modelo.DocumentoEntrega;
import com.rm.empresarial.modelo.DocumentoEntregaTramite;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Parametros;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.RepositorioSRI;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TiempoProceso;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteDocumentoEntrega;
import com.rm.empresarial.modelo.TramiteResponsable;
import com.rm.empresarial.modelo.TramiteTiempo;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.CiudadServicio;
import com.rm.empresarial.servicio.DocumentoEntregaServicio;
import com.rm.empresarial.servicio.DocumentoEntregaTramiteServicio;
import com.rm.empresarial.servicio.NotariaServicio;
import com.rm.empresarial.servicio.ParametrosServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.RepositorioSRIServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteDocumentoEntregaServicio;
import com.rm.empresarial.servicio.TramiteEntregaServicio;
import com.rm.empresarial.servicio.TramiteResponsableServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteUsuarioServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.apache.commons.mail.EmailException;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Wilson
 */
@ViewScoped
@Named
public class RecepcionDocumentacionControlador extends JsfUtil implements Serializable {

    private static final long serialVersionUID = 1550715478219521056L;

    //SE SUBIERON LOS CAMBIOS BIEN //
    @EJB
    private PersonaServicio servicioPersona;

    @EJB
    private RepositorioSRIServicio repositorioSRIServicio;

    @EJB
    private NotariaServicio notariaServicio;

    @EJB
    private ParametrosServicio parametrosServicio;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @EJB
    private TramiteServicio tramiteServicio;

    @EJB
    private CargaLaboralServicio cargaLaboralServicio;

    @EJB
    private UsuarioServicio usuarioServicio;

    @EJB
    private TramiteUsuarioServicio tramiteUsuarioServicio;

    @EJB
    private TramiteAccionServicio tramiteAccionServicio;

    @EJB
    private TramiteEntregaServicio tramiteEntregaServicio;

    @EJB
    private DocumentoEntregaTramiteServicio servicioDocumentoEntregaTramite;

    @EJB
    private DocumentoEntregaServicio servicioDocumentoEntrega;

    @EJB
    private TramiteResponsableServicio servicioTramiteResponsable;

    @EJB
    private CiudadServicio servicioCiudad;

    @EJB
    private TramiteDocumentoEntregaServicio servicioTramiteDocumentoEntrega;

    @EJB
    private TramiteTiempoDao servicioTramiteTiempo;

    @EJB
    private TramiteServicio servicioTramite;

    @EJB
    private TiempoProcesoDao servicioTiempoProceso;

    @EJB
    private InstitucionDao servicioInstitucion;

    @EJB
    private PersonaDao personaDao;

    @EJB
    private TipoTramiteServicio servicioTipoTramite;
    
    @EJB
    private TramiteDetalleDao tramiteDetalleDao;

    @Inject
    private DataManagerSesion dataManagerSesion;

    @Inject
    ReporteUtil reporteUtil;

    @Inject
    UtilFechaEntrega utilFechaEntrega;

    @Inject
    @Getter
    @Setter
    private RecepcionDocumentacionControladorBb recepcionDocumentacionControladorBb;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;
    @Inject
    @Getter
    @Setter
    private ControladorEditarPersona controladorEditarPersona;

    @Inject
    private TramiteUtil tramiteUtil;

    @Inject
    private EnviarEmailController enviarEmailUtil;

    @Inject
    private PersonaUtil personaUtil;

    @Inject
    @Getter
    @Setter
    private ControladorAgregarDocumentosEntregados contrAgregarDocumentosEntregados;

    @Getter
    @Setter
    private Long numTramite;

    @Getter
    @Setter
    private Persona personaReIngreso;

    @Getter
    @Setter
    private Persona personaResponsable;

    @Getter
    @Setter
    private Tramite tramiteReIngreso;

    @Getter
    @Setter
    private List<TramiteDocumentoEntrega> listTramiteDocumentoEntrega = new ArrayList<>();

    @Getter
    @Setter
    private List<Persona> listaPersonasFiltradas;

    @Getter
    @Setter
    private List<Persona> listaConyugueFiltradas;

    @Getter
    @Setter
    private String apellidoPaterno, apellidoMaterno, nombre;

    @Getter
    @Setter
    private String estadoReIngreso = "";

    @Getter
    @Setter
    private Ciudad ciudadSeleccionada;

    @Getter
    @Setter
    private List<Ciudad> listaCiudades;

    @Getter
    @Setter
    private String observacion;

    @Getter
    @Setter
    private String identificacionResponsable;

    @Getter
    @Setter
    private DocumentoEntregaTramite nuevoDocumentoEntregaTramite;

    @Getter
    @Setter
    private int numTramites;

    @Getter
    @Setter
    private boolean disableComboTipoTramite;

    @Getter
    @Setter
    private List<String> listaObservaciones;

    @Getter
    @Setter
    private List<String> listaObservacionesSeleccionadas;

    @Getter
    @Setter
    private List<TramiteDocumentoEntrega> listaTramiteDocumentoEntrega;

    @Getter
    @Setter
    private List<Integer> columns;

    @Getter
    @Setter
    private Persona personaSeleccionada;

    @Getter
    @Setter
    private Boolean añadir = Boolean.TRUE;

    @Getter
    @Setter
    private Boolean cerrarDlgBuscarPersona = Boolean.FALSE;

    @Getter
    @Setter
    private String pathImagen;

    @Getter
    @Setter
    private Boolean requiredNotaria = true;

    @Getter
    @Setter
    private List<List<TramiteDocumentoEntrega>> listadeListasTramiteDocEntrega;

    @Getter
    @Setter
    private List<TipoTramite> listaTipoTramites;

    @Getter
    @Setter
    private String notariaRep;
    
    @Getter
    @Setter
    private Boolean disabledBtn = false;

    @PostConstruct
    public void inicializar() {
        listadeListasTramiteDocEntrega = new ArrayList<>();
        columns = new ArrayList<>();
        listaTipoTramites = new ArrayList<>();
        columns.add(0);
        personaResponsable = new Persona();
        personaSeleccionada = new Persona();
        setDisableComboTipoTramite(true);
        listaPersonasFiltradas = new ArrayList<>();
        listaConyugueFiltradas = new ArrayList<>();

        ciudadSeleccionada = new Ciudad();
        listaCiudades = new ArrayList<>();
        listaObservaciones = new ArrayList<>();
        List<DocumentoEntregaTramite> auxlistDocumentoEntregaTramite = new ArrayList<>();
        Persona auxPersona = new Persona();
        List<String> auxListPersonasResponsables = new ArrayList<>();
        List<TramiteResponsable> auxListTramiteResponsable = new ArrayList<>();
        getContrAgregarDocumentosEntregados().getListaInfoTramites().add(auxlistDocumentoEntregaTramite);
        getContrAgregarDocumentosEntregados().getListaInfoTramitesFiltrado().add(auxlistDocumentoEntregaTramite);
        getContrAgregarDocumentosEntregados().getListaInfoTramitesSeleccion().add(auxlistDocumentoEntregaTramite);
        getContrAgregarDocumentosEntregados().getListaPersonasResponsables().add(auxPersona);
        getContrAgregarDocumentosEntregados().getListaPersonasResponsablesPorDocumento().add(auxListPersonasResponsables);
        getContrAgregarDocumentosEntregados().getListaTramitesResponsable().add(auxListTramiteResponsable);
        getRecepcionDocumentacionControladorBb().inicializar();
        getRecepcionDocumentacionControladorBb().setPersona(new Persona());
        //getRecepcionDocumentacionControladorBb().getPersona().setPerNombre(" ");
        getRecepcionDocumentacionControladorBb().getPersona().setPerApellidoPaterno(" ");
        getRecepcionDocumentacionControladorBb().getPersona().setPerApellidoMaterno(" ");
        getRecepcionDocumentacionControladorBb().getPersona().setPerDireccion(" ");
        recepcionDocumentacionControladorBb.setObservacion(" ");
        getRecepcionDocumentacionControladorBb().getTramite().setTraTipo("N");
        try {
            cargarNotaria();
            listaSexo();
            cargarParametros();
            inicializarFechaEntrega();
//            generarSecuencia();
            setListaCiudades(servicioCiudad.listarTodo());
            ciudadSeleccionada = servicioInstitucion.encontrarCiudad();

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(RecepcionDocumentacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inicializarFechaEntrega() {
        Calendar cal = Calendar.getInstance();
        getRecepcionDocumentacionControladorBb().setFechaEntregaAux(cal);
        getRecepcionDocumentacionControladorBb().setFechaEntrega(cal.getTime());
    }

    public void buscarPersona() throws IOException, ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.RecepcionDocumentacionControlador.buscarPersona()");
        if (getRecepcionDocumentacionControladorBb().getIdentificacion() != null && getRecepcionDocumentacionControladorBb().getIdentificacion().length() <= 13) {
            try {
                getRecepcionDocumentacionControladorBb().setPersona(personaUtil.obtenerDatosPersona(getRecepcionDocumentacionControladorBb().getIdentificacion()));
            } catch (ServicioExcepcion e) {
                System.out.print(e);
                getRecepcionDocumentacionControladorBb().setPersona(new Persona());
                getRecepcionDocumentacionControladorBb().getPersona().setPerIdentificacion("");
            }
        } else {
            getRecepcionDocumentacionControladorBb().setPersona(new Persona());
            getRecepcionDocumentacionControladorBb().getPersona().setPerIdentificacion("");
        }
    }

    public void transformarPersona() {
        getRecepcionDocumentacionControladorBb().getPersona().setPerIdentificacion(getRecepcionDocumentacionControladorBb().getRepositorioSRI().getSriRuc());
        getRecepcionDocumentacionControladorBb().getPersona().setPerNombre(getRecepcionDocumentacionControladorBb().getRepositorioSRI().getSriRazonSocial());
        getRecepcionDocumentacionControladorBb().getPersona().setPerDireccion(getRecepcionDocumentacionControladorBb().getRepositorioSRI().getSriCalle() + " " + getRecepcionDocumentacionControladorBb().getRepositorioSRI().getSriInterseccion());

    }

    public void cargarNotaria() throws ServicioExcepcion {
        recepcionDocumentacionControladorBb.setNotarias(notariaServicio.listaNotaria());
        recepcionDocumentacionControladorBb.setListaNotarias(notariaServicio.listarTodo());

    }

    public List<SelectItem> listaSexo() throws ServicioExcepcion {
        List<SelectItem> listaSexoItems = new ArrayList<>();
        listaSexoItems.add(new SelectItem("M", "MASCULINO"));
        listaSexoItems.add(new SelectItem("F", "FEMENINO"));
        recepcionDocumentacionControladorBb.setSexo(listaSexoItems);
        return listaSexoItems;

    }

    public void reload() throws IOException {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
    }

    public void cargarParametros() throws ServicioExcepcion {
        getRecepcionDocumentacionControladorBb().setParametros(parametrosServicio.buscarPorParametro("RVT"));

//        calcularFechaEntrega();
    }

    public void crearTramiteTiempo(Tramite tramite, String unidadTiempo, Long cantidadTiempo, Date fechaEntrega, TipoTramite tipoTram) {
        //***tramite tiempo
        TramiteTiempo nuevoTramiteTiempo = new TramiteTiempo(
                null,
                unidadTiempo,
                cantidadTiempo,
                tramite.getTraEstado(),
                fechaEntrega);
        nuevoTramiteTiempo.setTraNumero(tramite);
        nuevoTramiteTiempo.setTtrId(tipoTram);
        servicioTramiteTiempo.create(nuevoTramiteTiempo);
    }

    public void generarSecuencia() throws ServicioExcepcion {
        if (dataManagerSesion.getUsuarioLogeado().getUsuLogin() != null) {
            secuenciaControlador.generarSecuencia("TRA");
            System.out.print("numero de secuencia---- " + secuenciaControlador.getControladorBb().getNumeroTramite());
            if (getRecepcionDocumentacionControladorBb().getNumeroTramites() != null) {
                if (getRecepcionDocumentacionControladorBb().getNumeroTramites() == 1) {
                    getRecepcionDocumentacionControladorBb().setNumeroTramite(secuenciaControlador.getControladorBb().getNumeroTramite());
                    getRecepcionDocumentacionControladorBb().setNumeroTramiteInicial(secuenciaControlador.getControladorBb().getNumeroTramite());
                    getRecepcionDocumentacionControladorBb().setNumeroTramiteFinal(secuenciaControlador.getControladorBb().getNumeroTramite());
                } else {
                    getRecepcionDocumentacionControladorBb().setNumeroTramite(secuenciaControlador.getControladorBb().getNumeroTramite());

                    getRecepcionDocumentacionControladorBb().setNumeroTramiteFinal(secuenciaControlador.getControladorBb().getNumeroTramite());
                }
            }
            getRecepcionDocumentacionControladorBb().setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
        } else {
            addErrorMessage(" Error, Sessión Inactiva Vuelva a Loguearse");
        }

    }

    public void salir() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");

    }

    public void generarTramite() throws ServicioExcepcion, EmailException, EmailException, ParseException {
        int factor = 1;
        int contador = 0;
        boolean documentacionAgregada = false;
        Integer tramiteAux = 0;
        List<Tramite> listaTramitesGuardados = new ArrayList<>();
        Map<Long, Long> listaIdTramiteIdCargaLaboral = new HashMap<>();
        // VERIFICAR DOCUMENTACION AGREGADA PARA TODOS LOS TRAMITES
        for (Integer column : columns) {

            if (!getContrAgregarDocumentosEntregados().getListaInfoTramites().get(column).isEmpty()) {
                contador++;
            }
        }
        if (contador == columns.size()) {
            documentacionAgregada = true;
        }
        //GENERAR TRAMITE
        if (documentacionAgregada) {

            while (factor <= getRecepcionDocumentacionControladorBb().getNumeroTramites()) {
                generarSecuencia();
                if (factor == 1) {
                    cargaLaboralUtil.obtenerCargaLaboral("RVT", "recepcionDocumentos", 1);
                }
                getRecepcionDocumentacionControladorBb().setCargaLaboral(cargaLaboralUtil.getCargaLaboral());
                tramiteAux = getRecepcionDocumentacionControladorBb().getNumeroTramite();
                if (factor == 1) {
                    getRecepcionDocumentacionControladorBb().setNumeroTramiteInicial(secuenciaControlador.getControladorBb().getNumeroTramite());

                }
                Short juicio = 0, jusgado = 0, recu = 0, canTramite = 0, reimpre = 0;
                short cantidadadTiempoaux = getRecepcionDocumentacionControladorBb().getParametros().getPrmCantidadTiempo();
                int cantidadTiempo = cantidadadTiempoaux;
                String notaria = getRecepcionDocumentacionControladorBb().getValorNotaria();
                getRecepcionDocumentacionControladorBb().getTramite().setTraNumero(Long.valueOf(tramiteAux));
                getRecepcionDocumentacionControladorBb().getTramite().setTraUnidadTiempo(getRecepcionDocumentacionControladorBb().getParametros().getPrmUnidadTiempo());
                getRecepcionDocumentacionControladorBb().getTramite().setTraTiempo(cantidadTiempo);
                getRecepcionDocumentacionControladorBb().getTramite().setTraTipo(getRecepcionDocumentacionControladorBb().getTramite().getTraTipo());
                getRecepcionDocumentacionControladorBb().getTramite().setTraUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                getRecepcionDocumentacionControladorBb().getTramite().setTraFHR(Calendar.getInstance());
                if (recepcionDocumentacionControladorBb.getTramite().getTraTipo().equals("L")) {
                    getRecepcionDocumentacionControladorBb().getTramite().setTraClase("J");
                } else {
                    getRecepcionDocumentacionControladorBb().getTramite().setTraClase("E");
                }
                getRecepcionDocumentacionControladorBb().getTramite().setTraEstado("RVT");
                if (!(notaria == null || notaria.isEmpty())) {
                    getRecepcionDocumentacionControladorBb().getTramite().setTraNotaria(Short.valueOf(notaria));
                } else {
                    getRecepcionDocumentacionControladorBb().getTramite().setTraNotaria(Short.valueOf("0"));
                }
                getRecepcionDocumentacionControladorBb().getTramite().setTraParId(Long.parseLong("0"));
                getRecepcionDocumentacionControladorBb().getTramite().setTraParNombre(" ");
                getRecepcionDocumentacionControladorBb().getTramite().setTraJucio(getRecepcionDocumentacionControladorBb().getTramite().getTraJucio());
                getRecepcionDocumentacionControladorBb().getTramite().setTraNumeroJusgado(getRecepcionDocumentacionControladorBb().getTramite().getTraNumeroJusgado());
                getRecepcionDocumentacionControladorBb().getTramite().setTraRecuValor(recu);
                getRecepcionDocumentacionControladorBb().getTramite().setTraCantidadTramite(canTramite);
                getRecepcionDocumentacionControladorBb().getTramite().setTraFechaRecepcion(Calendar.getInstance());
                getRecepcionDocumentacionControladorBb().getTramite().setTraFechaEntrega(getRecepcionDocumentacionControladorBb().getFechaEntregaAux());
                getRecepcionDocumentacionControladorBb().getTramite().setTraNumeroReImpresion(reimpre);
                getRecepcionDocumentacionControladorBb().getTramite().setTraEstadoRegistro("RA");
                getRecepcionDocumentacionControladorBb().getTramite().setTraModelo("N");
                getRecepcionDocumentacionControladorBb().getTramite().setTraNumeroReIngreso(reimpre);
                getRecepcionDocumentacionControladorBb().getTramite().setTraFechaReIngreso(null);
                getRecepcionDocumentacionControladorBb().getTramite().setTraInicial(getRecepcionDocumentacionControladorBb().getNumeroTramiteInicial());
                getRecepcionDocumentacionControladorBb().getTramite().setTraFinal(getRecepcionDocumentacionControladorBb().getNumeroTramiteFinal());
                if (getRecepcionDocumentacionControladorBb().getPersona().getPerIdentificacion() != null) {
                    getRecepcionDocumentacionControladorBb().getTramite().setTraPerIdentificacion(getRecepcionDocumentacionControladorBb().getPersona().getPerIdentificacion());
                } else {
                    getRecepcionDocumentacionControladorBb().getTramite().setTraPerIdentificacion("999999999");
                }
                getRecepcionDocumentacionControladorBb().getTramite().setTraPerNombre(getRecepcionDocumentacionControladorBb().getPersona().getPerNombre());
                getRecepcionDocumentacionControladorBb().getTramite().setTraPerApellidoPaterno(getRecepcionDocumentacionControladorBb().getPersona().getPerApellidoPaterno());
                getRecepcionDocumentacionControladorBb().getTramite().setTraPerApellidoMaterno(getRecepcionDocumentacionControladorBb().getPersona().getPerApellidoMaterno());
                getRecepcionDocumentacionControladorBb().getTramite().setTraDescripcion(getRecepcionDocumentacionControladorBb().getObservacion());
                getRecepcionDocumentacionControladorBb().getTramite().setTraCantidadTramite(getRecepcionDocumentacionControladorBb().getNumeroTramites().shortValue());
                try {

                    getRecepcionDocumentacionControladorBb().setTramite(tramiteServicio.guardarTramite(getRecepcionDocumentacionControladorBb().getTramite()));

                    if (getRecepcionDocumentacionControladorBb().getTramite() == null) {
                        getRecepcionDocumentacionControladorBb().setTramite(tramiteServicio.buscarTramitePorNumero(Long.valueOf(tramiteAux)));
                    }

                    if (contrAgregarDocumentosEntregados.getListaInfoTramites().get(factor - 1).size() > 0) { //si abrio el tramite e ingreso 1 o mas tipos de tramites

                        List<DocumentoEntregaTramite> listdet = getContrAgregarDocumentosEntregados().getListaInfoTramites().get(factor - 1);
                        for (DocumentoEntregaTramite det : listdet) {
                            TramiteDocumentoEntrega tramiteDocumentoEntrega = new TramiteDocumentoEntrega();
                            tramiteDocumentoEntrega.setTtrId(det.getTtrId());
                            tramiteDocumentoEntrega.setTraNumero(tramiteServicio.buscarTramitePorNumero(Long.valueOf(tramiteAux)));
                            tramiteDocumentoEntrega.setTdaDescripcion(det.getDteId().getDteDescripcion());
                            tramiteDocumentoEntrega.setTdaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            tramiteDocumentoEntrega.setTdaFHR(Calendar.getInstance().getTime());
                            listTramiteDocumentoEntrega.add(tramiteDocumentoEntrega);

                        }
                        listadeListasTramiteDocEntrega.add(listTramiteDocumentoEntrega);
                        setListTramiteDocumentoEntrega(new ArrayList());
                    } else {
                        cargaLaboralUtil.obtenerCargaLaboral("RVT", "recepcionDocumentos", 1);
                    }

                    int auxSecuencia = getRecepcionDocumentacionControladorBb().getSecuencia().getSecActual();
                    getRecepcionDocumentacionControladorBb().getSecuencia().setSecActual(auxSecuencia + 1);
                    secuenciaServicio.guardar(getRecepcionDocumentacionControladorBb().getSecuencia());
                    getRecepcionDocumentacionControladorBb().setUsuarioNombre(cargaLaboralUtil.getUsuario().getPerId().getPerNombre().concat(" ").concat(cargaLaboralUtil.getUsuario().getPerId().getPerApellidoPaterno()));
                    registroTramiteUsuario(tramiteAux);

                    tramiteUtil.insertarTramiteAccion(getRecepcionDocumentacionControladorBb().getTramite(),
                            String.valueOf(getRecepcionDocumentacionControladorBb().getTramite().getTraNumero()),
                            "Trámite " + getRecepcionDocumentacionControladorBb().getTramite().getTraNumero()
                            + " asignado al usuario " + getRecepcionDocumentacionControladorBb().getCargaLaboral().getUsuId().getUsuLogin(),
                            getRecepcionDocumentacionControladorBb().getTramite().getTraEstado(),
                            getRecepcionDocumentacionControladorBb().getTramite().getTraEstadoRegistro(),
                            getRecepcionDocumentacionControladorBb().getCargaLaboral().getUsuId().getUsuLogin());

                } catch (ServicioExcepcion exception) {
                    addErrorMessage("Ocurrio un error al realizar el Registro");
                }

                if (getRecepcionDocumentacionControladorBb().getPersona().getPerIdentificacion() != null) {
                    servicioPersona.guardar(getRecepcionDocumentacionControladorBb().getPersona());
                }
                if (getContrAgregarDocumentosEntregados().getListaTramitesResponsable().get(factor - 1).size() > 0) {
                    List<TramiteResponsable> listTramiteResponsable = contrAgregarDocumentosEntregados.getListaTramitesResponsable().get(factor - 1);
                    for (TramiteResponsable traResponsable : listTramiteResponsable) {
                        traResponsable.setTraNumero(getRecepcionDocumentacionControladorBb().getTramite());
                        servicioTramiteResponsable.create(traResponsable);
                    }
                }

                Tramite tramiteGuardado = null; //almaceno una lista de los tramites que se guardan
                try {
                    tramiteGuardado = servicioTramite.find(new Tramite(), getRecepcionDocumentacionControladorBb().getTramite().getTraNumero());
                } catch (Exception e) {
                    System.out.println(e);
                }
                if (tramiteGuardado != null) {
                    listaTramitesGuardados.add(tramiteGuardado);
                }

                listaIdTramiteIdCargaLaboral.put(getRecepcionDocumentacionControladorBb().getTramite().getTraNumero(), getRecepcionDocumentacionControladorBb().getCargaLaboral().getCarId());//se actualiza la misma carga laboral

                factor++;
            }

            if (listadeListasTramiteDocEntrega.size() > 0) {
                for (List<TramiteDocumentoEntrega> listtramiteDocEntrega : listadeListasTramiteDocEntrega) {
                    for (TramiteDocumentoEntrega tramiteDocumentoEntrega : listtramiteDocEntrega) {
                        servicioTramiteDocumentoEntrega.guardar(tramiteDocumentoEntrega);
                    }
                }
            }

            //calcular fecha de entrega
            for (Tramite tram : listaTramitesGuardados) {
                //se actualiza la fecha de entrega y se genera registro en tramite tiempo
                utilFechaEntrega.calcularFechaEntregaTramite(tram.getTraNumero());
                if (utilFechaEntrega.getFechaEntregaAux() != null) {
                    tram.setTraFechaEntrega(utilFechaEntrega.getFechaEntregaAux());
                    servicioTramite.edit(tram);
                } else {
                    JsfUtil.addErrorMessage("Ocurrió un error al calcular la fecha de entrega");
                }

            }

            int aux = 0;//contador para verificar que es la primera corrida
            for (Map.Entry<Long, Long> item : listaIdTramiteIdCargaLaboral.entrySet()) {
                if (aux == 0) {//envio true, para que reste 1, debido a que la carga laboral creada antes, se crea con el valor cargaadicional=1
                    cargaLaboralUtil.actualizarCargaLaboralPor_TipoTramite(item.getKey(), item.getValue(), true);//busca en tramitedocumentoEntrega el tipo de tramite
                } else {
                    cargaLaboralUtil.actualizarCargaLaboralPor_TipoTramite(item.getKey(), item.getValue(), false);//busca en tramitedocumentoEntrega el tipo de tramite
                }
                aux++;
            }

            try {
                String correo = getRecepcionDocumentacionControladorBb().getPersona().getPerEmail().trim();
                String asunto = "Ingreso Tramite " + getRecepcionDocumentacionControladorBb().getTramite().getTraNumero();
                String mensaje = "Se ingresó el tramite, desde: " + getRecepcionDocumentacionControladorBb().getTramite().getTraInicial()
                        + " hasta: " + getRecepcionDocumentacionControladorBb().getTramite().getTraFinal();
                if (correo != null && !correo.isEmpty()) {
                    if (enviarEmailUtil.enviaEmail(correo, asunto, mensaje)) {
                        tramiteUtil.insertarTramiteAccion(getRecepcionDocumentacionControladorBb().getTramite(),
                                String.valueOf(getRecepcionDocumentacionControladorBb().getTramite().getTraNumero()),
                                "Correo enviado a '" + correo + "'",
                                getRecepcionDocumentacionControladorBb().getTramite().getTraEstado(),
                                getRecepcionDocumentacionControladorBb().getTramite().getTraEstadoRegistro(),
                                null);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            contrAgregarDocumentosEntregados.setListaInfoTramites(new ArrayList());
            contrAgregarDocumentosEntregados.setListaDocumentoEntregaTramite(new ArrayList());
            personaResponsable = new Persona();

            System.out.print(contrAgregarDocumentosEntregados.getListaInfoTramites().size());
            System.out.print(contrAgregarDocumentosEntregados.getListaDocumentoEntregaTramite().size());
            addSuccessMessage("Trámite creado correctamente");
        } else {
            addWarningMessage("No se puede guardar. Aún no se ha ingresado la documentación de todos los trámites");
        }
    }

    public void actualizarFechaEntrega(Tramite tramite) {
        TiempoProceso tiempoProceso = servicioTiempoProceso.buscarTiempoProcesoPor_Tramite(tramite.getTraNumero());
        if (tiempoProceso == null) {
            int aux = getRecepcionDocumentacionControladorBb().getParametros().getPrmCantidadTiempo();
            Calendar aux1 = Calendar.getInstance();
            if (getRecepcionDocumentacionControladorBb().getParametros().getPrmUnidadTiempo().equals("H")) {
                aux1.add(Calendar.HOUR, aux);
            } else if (getRecepcionDocumentacionControladorBb().getParametros().getPrmUnidadTiempo().equals("D")) {
                aux1.add(Calendar.DAY_OF_YEAR, aux);
            }
            getRecepcionDocumentacionControladorBb().setFechaEntrega(aux1.getTime());
            getRecepcionDocumentacionControladorBb().setFechaEntregaAux(aux1);

            crearTramiteTiempo(tramite,
                    getRecepcionDocumentacionControladorBb().getParametros().getPrmUnidadTiempo(),
                    new Long(getRecepcionDocumentacionControladorBb().getParametros().getPrmCantidadTiempo()),
                    getRecepcionDocumentacionControladorBb().getFechaEntrega(), null);
        } else {
            int aux = tiempoProceso.getTpoTiempo().intValue();
            Calendar aux1 = Calendar.getInstance();
            if (tiempoProceso.getTpoUnidadTiempo().equals("H")) {
                aux1.add(Calendar.HOUR, aux);
            } else if (tiempoProceso.getTpoUnidadTiempo().equals("D")) {
                aux1.add(Calendar.DAY_OF_YEAR, aux);
            }
            getRecepcionDocumentacionControladorBb().setFechaEntrega(aux1.getTime());
            getRecepcionDocumentacionControladorBb().setFechaEntregaAux(aux1);
            crearTramiteTiempo(tramite,
                    tiempoProceso.getTpoUnidadTiempo(),
                    tiempoProceso.getTpoTiempo().longValue(),
                    getRecepcionDocumentacionControladorBb().getFechaEntrega(),
                    tiempoProceso.getTtrId());
        }

        tramite.setTraFechaEntrega(getRecepcionDocumentacionControladorBb().getFechaEntregaAux());//asigno fecha entrega
        servicioTramite.edit(tramite);//actualizo tramite
    }

    public void registroTramiteUsuario(Integer tramite) throws ServicioExcepcion {
        guardarTramiteAccion();
        getRecepcionDocumentacionControladorBb().getTramiteUsuario().setTraNumero(getRecepcionDocumentacionControladorBb().getTramite());
        getRecepcionDocumentacionControladorBb().getTramiteUsuario().setUsuId(getRecepcionDocumentacionControladorBb().getCargaLaboral().getUsuId());
        getRecepcionDocumentacionControladorBb().getTramiteUsuario().setTusEstado("A");
        getRecepcionDocumentacionControladorBb().getTramiteUsuario().setTusUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        getRecepcionDocumentacionControladorBb().getTramiteUsuario().setTusFHR(Calendar.getInstance());
        tramiteUsuarioServicio.guardarTramiteUsuario(getRecepcionDocumentacionControladorBb().getTramiteUsuario());
        System.out.println("Registro almacenado correctamente" + tramite);
        getRecepcionDocumentacionControladorBb().setEstado(Boolean.TRUE);
    }

    public void actualizaIdentificacion() {
        System.out.print("Identificacion " + getRecepcionDocumentacionControladorBb().getIdentificacion());

        getRecepcionDocumentacionControladorBb().setIdentificacion("999999999");
        getRecepcionDocumentacionControladorBb().getPersona().setPerDireccion(" ");

    }

    public void actualizarCalidad() {

        if (getRecepcionDocumentacionControladorBb().getFiltro()) {
            getRecepcionDocumentacionControladorBb().getPersona().setPerCalidad("S");
            System.out.print("Valor calidad" + getRecepcionDocumentacionControladorBb().getPersona().getPerCalidad());
        } else {
            getRecepcionDocumentacionControladorBb().getPersona().setPerCalidad("N");
        }

    }

    public void guardarTramiteAccion() throws ServicioExcepcion {

        getRecepcionDocumentacionControladorBb().getTramiteAccion().setTmaEstadoProceso("RVT");
        getRecepcionDocumentacionControladorBb().getTramiteAccion().setTmaEstadoRegistro("RA");
        getRecepcionDocumentacionControladorBb().getTramiteAccion().setTmaFHR(Calendar.getInstance().getTime());
        getRecepcionDocumentacionControladorBb().getTramiteAccion().setTmaNumeroDocumento(String.valueOf(getRecepcionDocumentacionControladorBb().getTramite().getTraNumero()));
        getRecepcionDocumentacionControladorBb().getTramiteAccion().setTmaObservacion("TRÁMITE " + String.valueOf(getRecepcionDocumentacionControladorBb().getTramite().getTraNumero()) + " INGRESADO");
        getRecepcionDocumentacionControladorBb().getTramiteAccion().setTmaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        getRecepcionDocumentacionControladorBb().getTramiteAccion().setTraNumero(getRecepcionDocumentacionControladorBb().getTramite());
        tramiteAccionServicio.guardarTramiteAccion(getRecepcionDocumentacionControladorBb().getTramiteAccion());
    }

    public void preReIngresoTramite() {
        System.out.println("com.rm.empresarial.controlador.RecepcionDocumentacionControlador.preReIngresoTramite()");
        personaReIngreso = new Persona();
        tramiteReIngreso = new Tramite();
        numTramite = null;
        disabledBtn = false;
        System.out.println("estado: " + getEstadoReIngreso());
    }

    public void cargarDatosReingresoTramite() {
        System.out.println("com.rm.empresarial.controlador.RecepcionDocumentacionControlador.cargarDatosReingresoTramite()");
        System.out.println("estado: " + getEstadoReIngreso());

        try {
            personaReIngreso = tramiteServicio.obtenerPersonaConNumeroTramite(numTramite, estadoReIngreso);
            tramiteReIngreso = tramiteServicio.find(new Tramite(), numTramite);
            if (personaReIngreso == null || tramiteReIngreso == null) {
                setPersonaReIngreso(null);
                setTramiteReIngreso(null);
                JsfUtil.addErrorMessage("Tramite no existe o ingreso incorrecto");
            } else {
                JsfUtil.addSuccessMessage("Datos cargados");
            }
        } catch (Exception e) {
            System.out.print(e);
        }

    }

    public void guardarReingresoTramite() {
        if (tramiteReIngreso.getTraNumero() != null) {
            tramiteReIngreso.setTraEstadoRegistro("RA");
            tramiteReIngreso.setTraFechaReIngreso(Calendar.getInstance());
            utilFechaEntrega.calcularFechaEntregaTramite(tramiteReIngreso.getTraNumero());
            tramiteReIngreso.setTraFechaEntrega(utilFechaEntrega.getFechaEntregaAux());
            tramiteReIngreso.setTraNumeroReIngreso((short) (tramiteReIngreso.getTraNumeroReIngreso() + 1));
            tramiteServicio.edit(tramiteReIngreso);
            disabledBtn = true;
            tramiteUtil.insertarTramiteAccion(tramiteReIngreso,
                    tramiteReIngreso.getTraNumero().toString(),
                    "Reingreso de Tramite ",
                    tramiteReIngreso.getTraEstado(),
                    tramiteReIngreso.getTraEstadoRegistro(), null);

        }

    }

    public void cancelar() {

    }
    
     public void generarReporteTramiteReingreso() {
        if (numTramite != null) {
             Tramite tramiteReingreso = new Tramite();
            try {
                Institucion institucion = servicioInstitucion.obtenerInstitucion();
                setPathImagen(institucion.getInsLogo());
                tramiteReingreso = tramiteServicio.buscarTramitePorNumero(numTramite);
            } catch (ServicioExcepcion e) {                
                JsfUtil.addWarningMessage("Error al cargar path de imagen");
            }
            int numTramiteReingreso = numTramite.intValue();
            int traInicial = 0;
            int traFinal = 0;
            String notariaRep = "";
            
             if (tramiteReingreso.getTraNotaria() != Short.valueOf("0")) {
                notariaRep = notariaServicio.encontrarPorNumNotariaTopUno(tramiteReingreso.getTraNotaria()).getNotNotario();
            } else {
                notariaRep = "";
            }
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("NumeroTramite", numTramiteReingreso);
            parametros.put("pathImagen", pathImagen);
            parametros.put("traInicial", traInicial);
            parametros.put("traFinal", traFinal);
            parametros.put("notaria", notariaRep);
            try {
                reporteUtil.imprimirReporteEnPDF("ReimpTramiteDesc", "reporteTramite", parametros);

            } catch (JRException | IOException | NamingException | SQLException ex) {
                Logger.getLogger(RecepcionDocumentacionControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void generarReporte() throws ServicioExcepcion {
        if (getRecepcionDocumentacionControladorBb().getTramite().getTraNumero() != null) {
            try {
                Institucion institucion = servicioInstitucion.obtenerInstitucion();
                setPathImagen(institucion.getInsLogo());
            } catch (ServicioExcepcion e) {
                JsfUtil.addWarningMessage("Error al cargar path de imagen");
            }
            int numTramite = getRecepcionDocumentacionControladorBb().getTramite().getTraNumero().intValue();
            if (!Objects.equals(getRecepcionDocumentacionControladorBb().getTramite().getTraNotaria(), Short.valueOf("0"))) {
                notariaRep = notariaServicio.encontrarPorNumNotaria(getRecepcionDocumentacionControladorBb().getTramite().getTraNotaria(),ciudadSeleccionada).getNotNotario();
            } else {
                notariaRep = "";
            }
           
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("NumeroTramite", numTramite);
            parametros.put("pathImagen", pathImagen);
            parametros.put("traInicial", getRecepcionDocumentacionControladorBb().getNumeroTramiteInicial());
            parametros.put("traFinal", getRecepcionDocumentacionControladorBb().getNumeroTramiteFinal());
            parametros.put("notaria", notariaRep);
            
            try {
                reporteUtil.imprimirReporteEnPDF("TramiteDesc", "reporteTramite", parametros);
            } catch (JRException | IOException | NamingException | SQLException ex) {

                Logger.getLogger(RecepcionDocumentacionControlador.class
                        .getName()).log(Level.SEVERE, null, ex);
                addErrorMessage("ERROR AL GENERAR PDF");

            }
        }
    }

    public void buscarPersonaResponsable() throws ServicioExcepcion {
        if (identificacionResponsable != null && identificacionResponsable.length() <= 13) {
            try {
                setPersonaResponsable(personaUtil.obtenerDatosPersona(identificacionResponsable));
                getContrAgregarDocumentosEntregados().setPersonaResponsable(personaResponsable);
                getContrAgregarDocumentosEntregados().setEstadoResponsable("false");
                setDisableComboTipoTramite(false);
            } catch (ServicioExcepcion e) {
                setDisableComboTipoTramite(true);
                setPersonaResponsable(new Persona());
            }
        }
    }

    public void filtrarPersona() {
        setListaPersonasFiltradas(servicioPersona.listarPorNom_ApeP_ApeM(apellidoPaterno.toUpperCase(), apellidoMaterno.toUpperCase(), nombre.toUpperCase()));
    }

    public void filtrarConyugue() {
        setListaConyugueFiltradas(servicioPersona.listarPorNom_ApeP_ApeM(apellidoPaterno.toUpperCase(), apellidoMaterno.toUpperCase(), nombre.toUpperCase()));
        if (listaConyugueFiltradas != null) {

            if (listaConyugueFiltradas.contains(personaResponsable)) {
                listaConyugueFiltradas.remove(personaResponsable);
            }
        }

    }

    public void limpiarFiltradoPersona() {
        listaConyugueFiltradas = new ArrayList<>();
        listaPersonasFiltradas = new ArrayList<>();
        apellidoMaterno = "";
        apellidoPaterno = "";
        nombre = "";
    }

    public void asignarResponsable() throws ServicioExcepcion {
        getContrAgregarDocumentosEntregados().setEstadoResponsable("false");
        Persona persona = personaDao.buscarPersonaPorId(personaResponsable.getPerId());
        if (persona.getPerMigrado() != null) {
            if (persona.getPerMigrado().equals("SI")) {
                getControladorEditarPersona().setPersonaEditar(persona);
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('PersonaEditDialog').show();");
                cerrarDlgBuscarPersona = false;
            } else {
                cerrarDlgBuscarPersona = true;
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dlgFiltradoPersona').hide();");
            }
        } else {
            cerrarDlgBuscarPersona = true;
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dlgFiltradoPersona').hide();");
        }
        personaResponsable = persona;
        getContrAgregarDocumentosEntregados().setPersonaResponsable(personaResponsable);
        setIdentificacionResponsable(personaResponsable.getPerIdentificacion());
        System.out.println("ident " + getIdentificacionResponsable());
        setDisableComboTipoTramite(false);
        limpiarFiltradoPersona();
    }

    public void asignarResponsableI() throws ServicioExcepcion {
        Persona persona = personaDao.buscarPersonaPorId(personaResponsable.getPerId());
        if (persona.getPerMigrado() != null) {
            if (persona.getPerMigrado().equals("SI")) {
                getControladorEditarPersona().setPersonaEditar(persona);
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('PersonaEditDialog').show();");
                cerrarDlgBuscarPersona = false;
            } else {
                cerrarDlgBuscarPersona = true;
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dlgFiltradoPersona').hide();");
            }
        } else {
            cerrarDlgBuscarPersona = true;
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dlgFiltradoPersona').hide();");
        }
        personaResponsable = persona;
        getContrAgregarDocumentosEntregados().setPersonaResponsable(personaResponsable);
        setIdentificacionResponsable(personaResponsable.getPerIdentificacion());
        System.out.println("ident " + getIdentificacionResponsable());
        setDisableComboTipoTramite(false);
        limpiarFiltradoPersona();
    }

    public void asignarConyugue() throws ServicioExcepcion {
        Persona persona = personaDao.buscarPersonaPorId(personaSeleccionada.getPerId());

        cerrarDlgBuscarPersona = true;
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlgFiltradoPersonaI').hide();");
        if (getControladorEditarPersona().getPersonaEditar() != null) {
            getControladorEditarPersona().getPersonaEditar().setPerIdConyuge(persona);
        }
        getControladorEditarPersona().setCiConyuge(persona.getPerIdentificacion());
        personaSeleccionada = persona;
        getRecepcionDocumentacionControladorBb().setPersona(persona);
        getRecepcionDocumentacionControladorBb().setIdentificacion(persona.getPerIdentificacion());
        limpiarFiltradoPersona();
//        getRecepcionDocumentacionControladorBb().setPersona(personaSeleccionada);
//        
//        getRecepcionDocumentacionControladorBb().setIdentificacion(personaSeleccionada.getPerIdentificacion());
//        
//        setDisableComboTipoTramite(false);
    }

    public void cargarNotariasPorCiudad() {
        getRecepcionDocumentacionControladorBb().setListaNotarias(notariaServicio.listarPorCiudad(ciudadSeleccionada));
    }

    public void añadirNuevoDocumentoEntregaTramite() {
        setAñadir(Boolean.TRUE);
        if (!observacion.trim().isEmpty()) {
            for (DocumentoEntregaTramite docTipo : contrAgregarDocumentosEntregados.getListaDocumentoEntregaTramiteFiltrado()) {
                if (docTipo.getDteId().getDteDescripcion().equals(observacion.toUpperCase())) {
                    setAñadir(Boolean.FALSE);
                    JsfUtil.addWarningMessage("Documento ya existente en la lista");
                }
            }

            for (String observ : listaObservaciones) {
                if (observ.equals(observacion.toUpperCase())) {
                    setAñadir(Boolean.FALSE);
                    JsfUtil.addWarningMessage("Documento ya existente en la lista");
                }
            }

            if (añadir) {
                listaObservaciones.add(observacion.toUpperCase());
                listaObservacionesSeleccionadas.add(observacion.toUpperCase());
                setObservacion("");
            }
        } else {
            JsfUtil.addWarningMessage("Ingrese la Observación");
        }
    }

    public void verificarNumTramites() {
        for (String obser : listaObservacionesSeleccionadas) {
            String nombreUsuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
            if (!obser.isEmpty()) {
                Long idNuevoDocumentoEntrega = servicioDocumentoEntrega.asignarID();
                Long idNuevoDocumentoEntregaTramite = servicioDocumentoEntregaTramite.asignarID();
                DocumentoEntrega nuevoDocumentoEntrega = new DocumentoEntrega(idNuevoDocumentoEntrega, obser.toUpperCase(), "A", nombreUsuario, Calendar.getInstance().getTime());
                nuevoDocumentoEntregaTramite = new DocumentoEntregaTramite();
                nuevoDocumentoEntregaTramite.setDetId(idNuevoDocumentoEntregaTramite);
                nuevoDocumentoEntregaTramite.setTtrId(getContrAgregarDocumentosEntregados().getTipoTramite());
                nuevoDocumentoEntregaTramite.setDteId(nuevoDocumentoEntrega);
                nuevoDocumentoEntregaTramite.setDetUser(nombreUsuario);
                nuevoDocumentoEntregaTramite.setDetFHR(Calendar.getInstance().getTime());
                nuevoDocumentoEntregaTramite.setDteId(nuevoDocumentoEntrega);

                getContrAgregarDocumentosEntregados().getListaDocumentoEntregaTramiteSeleccion().add(nuevoDocumentoEntregaTramite);
                getContrAgregarDocumentosEntregados().getListaDocumentoEntregaTramiteFiltrado().add(nuevoDocumentoEntregaTramite);

            }
        }
        contrAgregarDocumentosEntregados.agregarDocumento();
        contrAgregarDocumentosEntregados.comprobarNumTramites(getRecepcionDocumentacionControladorBb().getNumeroTramites());
        listaObservaciones.clear();
        listaObservacionesSeleccionadas.clear();
    }

    public void auxNumTramites() {
        setNumTramites(getRecepcionDocumentacionControladorBb().getNumeroTramites());
    }

    public void add() {
        columns = new ArrayList<>();
        for (int i = 0; i < getRecepcionDocumentacionControladorBb().getNumeroTramites(); i++) {
            columns.add(i);
        }
        for (int i = getContrAgregarDocumentosEntregados().getListaInfoTramites().size(); i < getRecepcionDocumentacionControladorBb().getNumeroTramites(); i++) {
            List<DocumentoEntregaTramite> auxlistDocumentoEntregaTramite = new ArrayList<>();
            Persona auxPersona = new Persona();
            List<String> auxListPersonasResponsables = new ArrayList<>();
            List<TramiteResponsable> auxListTramiteResponsable = new ArrayList<>();
            getContrAgregarDocumentosEntregados().getListaInfoTramites().add(auxlistDocumentoEntregaTramite);
            getContrAgregarDocumentosEntregados().getListaInfoTramitesFiltrado().add(auxlistDocumentoEntregaTramite);
            getContrAgregarDocumentosEntregados().getListaInfoTramitesSeleccion().add(auxlistDocumentoEntregaTramite);
            getContrAgregarDocumentosEntregados().getListaPersonasResponsables().add(auxPersona);
            getContrAgregarDocumentosEntregados().getListaPersonasResponsablesPorDocumento().add(auxListPersonasResponsables);
            getContrAgregarDocumentosEntregados().getListaTramitesResponsable().add(auxListTramiteResponsable);
        }
    }

    public void cargarTramite() {
        identificacionResponsable = "";
        int numeroTramite = getContrAgregarDocumentosEntregados().getNum();
        if (!getContrAgregarDocumentosEntregados().getListaInfoTramites().isEmpty()) {
            if (numeroTramite < getContrAgregarDocumentosEntregados().getListaInfoTramites().size()) {
                getContrAgregarDocumentosEntregados().setPersonaResponsable(getContrAgregarDocumentosEntregados().getListaPersonasResponsables().get(numeroTramite));
                getContrAgregarDocumentosEntregados().setListaPersonaResponsable(getContrAgregarDocumentosEntregados().getListaPersonasResponsablesPorDocumento().get(numeroTramite));
                getContrAgregarDocumentosEntregados().setListaDocumentoEntregaTramite(getContrAgregarDocumentosEntregados().getListaInfoTramites().get(numeroTramite));
                getContrAgregarDocumentosEntregados().setListaDocumentoEntregaTramiteFiltrado(getContrAgregarDocumentosEntregados().getListaInfoTramitesFiltrado().get(numeroTramite));
                getContrAgregarDocumentosEntregados().setListaDocumentoEntregaTramiteSeleccion(getContrAgregarDocumentosEntregados().getListaInfoTramitesSeleccion().get(numeroTramite));
                getContrAgregarDocumentosEntregados().setListaTramiteResponsable(getContrAgregarDocumentosEntregados().getListaTramitesResponsable().get(numeroTramite));
                setIdentificacionResponsable(getContrAgregarDocumentosEntregados().getListaPersonasResponsables().get(numeroTramite).getPerIdentificacion());
                System.out.println(getIdentificacionResponsable());
                System.out.println(getContrAgregarDocumentosEntregados().getPersonaResponsable());
            } else {
                setPersonaResponsable(null);
                identificacionResponsable = "";
                getContrAgregarDocumentosEntregados().setPersonaResponsable(null);
                getContrAgregarDocumentosEntregados().setListaDocumentoEntregaTramite(null);
                getContrAgregarDocumentosEntregados().setTipoTramite(null);

            }
        }
        cargarTipoTramites();
    }

    public void limpiarListasObservaciones() {
        listaObservaciones = new ArrayList<>();
        listaObservacionesSeleccionadas = new ArrayList<>();
    }

    //METODO USADO EN EL BOTON GRABAR
    public void guardarEnMemoria() {
        if (personaResponsable != null) {
            setObservacion("");
            setIdentificacionResponsable("");
            getContrAgregarDocumentosEntregados().setTipoTramite(null);
            listaObservaciones = new ArrayList<>();
            contrAgregarDocumentosEntregados.guardarTramiteEnMemoria();
        }

    }

    public void setearParaNuevoDocumento() {
        listadeListasTramiteDocEntrega = new ArrayList<>();
        columns = new ArrayList<>();
        columns.add(0);
        personaResponsable = new Persona();
//        ciudadSeleccionada = servicioInstitucion.encontrarCiudad();
        listaCiudades = new ArrayList<>();
        listaObservaciones = new ArrayList<>();
        getContrAgregarDocumentosEntregados().setListaInfoTramites(new ArrayList());
        getContrAgregarDocumentosEntregados().setListaInfoTramitesFiltrado(new ArrayList());
        getContrAgregarDocumentosEntregados().setListaInfoTramitesSeleccion(new ArrayList());
        getContrAgregarDocumentosEntregados().setListaPersonasResponsables(new ArrayList());
        getContrAgregarDocumentosEntregados().setListaPersonasResponsablesPorDocumento(new ArrayList());
        getContrAgregarDocumentosEntregados().setListaTramitesResponsable(new ArrayList());
        getRecepcionDocumentacionControladorBb().setPersona(new Persona());
        getRecepcionDocumentacionControladorBb().getPersona().setPerNombre(" ");
        getRecepcionDocumentacionControladorBb().getPersona().setPerApellidoPaterno(" ");
        getRecepcionDocumentacionControladorBb().getPersona().setPerApellidoMaterno(" ");
        getRecepcionDocumentacionControladorBb().getPersona().setPerDireccion(" ");
        List<DocumentoEntregaTramite> auxlistDocumentoEntregaTramite = new ArrayList<>();
        Persona auxPersona = new Persona();
        List<String> auxListPersonasResponsables = new ArrayList<>();
        List<TramiteResponsable> auxListTramiteResponsable = new ArrayList<>();
        getContrAgregarDocumentosEntregados().getListaInfoTramites().add(auxlistDocumentoEntregaTramite);
        getContrAgregarDocumentosEntregados().getListaInfoTramitesFiltrado().add(auxlistDocumentoEntregaTramite);
        getContrAgregarDocumentosEntregados().getListaInfoTramitesSeleccion().add(auxlistDocumentoEntregaTramite);
        getContrAgregarDocumentosEntregados().getListaPersonasResponsables().add(auxPersona);
        getContrAgregarDocumentosEntregados().getListaPersonasResponsablesPorDocumento().add(auxListPersonasResponsables);
        getContrAgregarDocumentosEntregados().getListaTramitesResponsable().add(auxListTramiteResponsable);
        recepcionDocumentacionControladorBb.setObservacion(" ");
        recepcionDocumentacionControladorBb.setRepositorioSRI(new RepositorioSRI());
        recepcionDocumentacionControladorBb.setListaNotaria(new ArrayList());
        recepcionDocumentacionControladorBb.setNotaria(new Notaria());
        recepcionDocumentacionControladorBb.setParametros(new Parametros());
        recepcionDocumentacionControladorBb.setSecuencia(new Secuencia());
        recepcionDocumentacionControladorBb.setCargaLaboral(new CargaLaboral());
        recepcionDocumentacionControladorBb.setUsuario(new Usuario());
        recepcionDocumentacionControladorBb.setTramiteUsuario(new TramiteUsuario());
        recepcionDocumentacionControladorBb.setTramiteAccion(new TramiteAccion());
        recepcionDocumentacionControladorBb.setEstado(Boolean.FALSE);
        recepcionDocumentacionControladorBb.setIdentificacion("");
        recepcionDocumentacionControladorBb.setValorNotaria("");
        recepcionDocumentacionControladorBb.setNumeroTramites(0);
        String tipoTramite = getRecepcionDocumentacionControladorBb().getTramite().getTraTipo();
        switch (tipoTramite) {
            case "N":
                requiredNotaria = true;
                break;
            case "L":
                requiredNotaria = false;
                break;

        }

        try {
            cargarNotaria();
            listaSexo();
            cargarParametros();
            setListaCiudades(servicioCiudad.listarTodo());
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(RecepcionDocumentacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarTipoTramites() {
        try {
            if ("N".equals(recepcionDocumentacionControladorBb.getTramite().getTraTipo())) {
                setListaTipoTramites(servicioTipoTramite.listarPorTraClase("E"));
            } else if ("L".equals(recepcionDocumentacionControladorBb.getTramite().getTraTipo())) {
                setListaTipoTramites(servicioTipoTramite.listarPorTraClase("J"));
            }
        } catch (ServicioExcepcion e) {
            System.out.print(e);
        }
    }
}
