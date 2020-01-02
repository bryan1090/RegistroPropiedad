/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.RecepcionDocumentacionControladorBb;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.FechasUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.TextoUtil;
import com.rm.empresarial.dao.CertificadoAccionDao;
import com.rm.empresarial.dao.CertificadoAuditoriaDao;
import com.rm.empresarial.dao.FacturaDao;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.PropietarioDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.TipoCertificadoDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.dao.ParroquiaDao;
import com.rm.empresarial.dao.FacturaDetalleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoAccion;
import com.rm.empresarial.modelo.CertificadoAuditoria;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.CertificadoPK;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FormularioDigital;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.GravamenDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.TipoCertificado;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.FormularioDigitalServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.PropiedadDetalleServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.PropietarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoCertificadoServicio;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.text.StrSubstitutor;
import org.primefaces.PrimeFaces;
import org.primefaces.apollo.domain.Book;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

/**
 *
 * @author Prueba
 */
@Named("controladorCertificado")
@ViewScoped
public class ControladorCertificado implements Serializable {

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    @Getter
    @Setter
    private PersonaUtil personaUtil;

    @Getter
    @Setter
    private Boolean busquedaUno;

    @Getter
    @Setter
    private Boolean busqueda;

    @Getter
    @Setter
    private Boolean busquedaDos;

    @Getter
    @Setter
    private Boolean busquedaTres;

    @Getter
    @Setter
    private Boolean disabledPredioCatastro;

    @Getter
    @Setter
    private Boolean disabledRepertorio;

    @Getter
    @Setter
    private Boolean disabledInscripcion;

    @Getter
    @Setter
    private Long idTipoCertificado;

    @Getter
    @Setter
    private Long idTipoC;

    @Getter
    @Setter
    private Long idTipC;

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private Date fecha;

    @Getter
    @Setter
    private String numFactura;

    @Getter
    @Setter
    private String numCertificados;

    @Getter
    @Setter
    private boolean agregarImagenEspacioEnBlanco;

    @Getter
    @Setter
    private List<TipoCertificado> listaTipoCertificado = new ArrayList<>();

//    @Getter
//    @Setter
//    private List<Factura> listaFacturaPorEstadoPorNumeroPorFechaPorTipoCer = new ArrayList<>();
    @Getter
    @Setter
    private List<CertificadoCarga> listaCertificadoCarga;

    @EJB
    private TipoCertificadoDao tipoCertificadoDao;

    @EJB
    private FacturaDao facturaDao;

    @EJB
    private PropiedadServicio servicioPropiedad;

    @EJB
    private RepertorioPropiedadDao servicioRepertorioPropiedad;

    @EJB
    private RepertorioDao servicioRepertorio;

    @EJB
    private ActaServicio servicioActa;

    @EJB
    private PersonaServicio servicioPersona;

//    @Getter
//    @Setter
//    private Secuencia secuencia;
    @EJB
    private PropietarioServicio servicioPropietario;

    @EJB
    private CertificadoCargaServicio servicioCertificadoCarga;

    @EJB
    private GravamenDao servicioGravamen;

    @EJB
    private CertificadoServicio servicioCertificado;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @EJB
    private TipoCertificadoServicio servicioTipoCertificado;

    @EJB
    private FacturaServicio servicioFactura;

    @EJB
    private CertificadoAccionDao servicioCertificadoAccion;

    @EJB
    private InstitucionServicio servicioInstitucion;

//    @EJB
//    private InstitucionServicio servicioParroquia;
    @EJB
    private PropiedadDetalleServicio servicioPropiedadDetalle;
    @EJB
    private ParroquiaDao servicioParroquia;

    @EJB
    private CertificadoAuditoriaDao servicioCertificadoAuditoria;

    @EJB
    private ConfigDetalleServicio servicioConfigDetalle;

    @EJB
    private FormularioDigitalServicio servicioFormularioDigital;

    @EJB
    private ParametroPathServicio servicioParametroPath;

    @EJB
    private TramiteDetalleDao tramiteDetalleDao;

    @EJB
    private PropietarioDao propietarioDao;

    @EJB
    private FacturaDetalleDao servicioFacturaDetalle;

    @Inject
    private PdfTempUtil pdfTempUtil;

    @Inject
    @Getter
    @Setter
    private RecepcionDocumentacionControladorBb recepcionDocumentacionControladorBb;

    @Getter
    @Setter
    private ConfigDetalle configDetalle;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private FechasUtil fechasUtil;

    @Getter
    @Setter
    private String predio;

    @Getter
    @Setter
    private String catastro;

    @Getter
    @Setter
    private Propiedad propiedadSeleccionada;

    @Getter
    @Setter
    private List<Acta> listaActasSeleccionadas;

    @Getter
    @Setter
    private List<Propietario> listaPropietariosSeleccionados;

    @Getter
    @Setter
    private Boolean mostrarPnlTabs = false;
    @Getter
    @Setter
    private Boolean mostrarPnlTabs2;

    @Getter
    @Setter
    private String descripcionPropiedad;

    @Getter
    @Setter
    private String descripcionPropietarios;

    @Getter
    @Setter
    private String gravamen;

    @Getter
    @Setter
    private String adquisicionAntecedentes;

    @Getter
    @Setter
    private Acta actaSeleccionada;

    @Getter
    @Setter
    private List<Gravamen> listaGravamenesSelec;

    @Getter
    @Setter
    private CertificadoCarga certificadoCargaSeleccionado;

    @Getter
    @Setter
    private Certificado certificadoSeleccionado;

    @Getter
    @Setter
    private Factura facturaSeleccionada;

    @Getter
    @Setter
    private Boolean esNuevo;

    @Getter
    @Setter
    private String textoCertificado;

    @Getter
    @Setter
    private String tipoGravamen;

    @Getter
    @Setter
    private TipoCertificado tipoCertificadoSeleccionado;

    @Getter
    @Setter
    private Boolean mostrarPnlCertPlantilla;

//    @Getter
//    @Setter
//    private String cabecera;
    @Getter
    @Setter
    private List<String> listaPartesCertificado;

    @Getter
    @Setter
    private String[] variables;

    @Getter
    @Setter
    private String plantillaCertificado;

    @Getter
    @Setter
    private List<Certificado> listaCertificadoAnteriores;

    @Getter
    @Setter
    private Integer numTrabajosAsignados = 0;

    @Getter
    @Setter
    private Integer numTrabajosHabilitados = 0;

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadMostrar;
    @Getter
    @Setter
    private List<Propiedad> listaPropiedadSelec;
//    @Getter
//    @Setter
//    private List<Propiedad> listaPropiedadMostrar;
//    
//    @Getter
//    @Setter
//    private List<Propiedad> listaPropiedadSelec;

    @Getter
    @Setter
    private String tabSeleccionado;

    @Getter
    @Setter
    private List<Acta> listaActaSeleccionadas2;

    @Getter
    @Setter
    private Long numRepertorio;

    @Getter
    @Setter
    private Date fchRepertorio;

    @Getter
    @Setter
    private Date fchActa;

    @Getter
    @Setter
    private Long numInscripcion;

    @Getter
    @Setter
    private TipoLibro tipoLibroSeleccionado;

    @Getter
    @Setter
    private String tipoTrabajo;

    @Getter
    @Setter
    private Integer indiceTabViewCertificados;

    @Getter
    @Setter
    private String path;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String urlCertificadoPdf;

    private int numSaltosLinea;

//    @Getter
//    @Setter
//    private Boolean mostrarTab;
    @Getter
    @Setter
    private Boolean mostrarTabReferencias;
    @Getter
    @Setter
    private Boolean mostrarTabPropiedad;
    @Getter
    @Setter
    private Boolean mostrarTabPropietarios;
    @Getter
    @Setter
    private Boolean mostrarTabAdquisiciones;
    @Getter
    @Setter
    private Boolean mostrarTabGravamenes;
    @Getter
    @Setter
    private Boolean mostrarTabObservaciones;
    @Getter
    @Setter
    private Boolean mostrarTabCertificado;

    @Getter
    @Setter
    private Boolean mostrarTabSolicitante;

    @Getter
    @Setter
    private Boolean esCertificadoVacio;

    @Getter
    @Setter
    private Boolean deshabilitarBusqueda;

    @Getter
    @Setter
    private Boolean esCertificadoBusqueda;

    @Getter
    @Setter
    private Boolean esCertificadoBienesRaices;

    @Getter
    @Setter
    private Boolean esCertificadoEstatuto;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesCertBusqueda;

    @Getter
    @Setter
    private String txtSolicitanteNombre;
    @Getter
    @Setter
    private String txtSolicitanteIdentificacion;
    @Getter
    @Setter
    private String txtObservacion;
    @Getter
    @Setter
    private Boolean tieneGravamenes;

    @Getter
    @Setter
    List<Propiedad> listaPropiedadesSel;

    @Getter
    @Setter
    Propiedad propiedadSelAgregarObs;

    Tramite tramiteSel;
    Repertorio repertorioSel;

    List<TramiteDetalle> listTramDet;

    @Getter
    @Setter
    String txtComparecientes;

    private String numPeticion;

    @Getter
    @Setter
    private boolean esCertificadoPlantilla;

    @Getter
    @Setter
    private String textoPropiedades;
    @Getter
    @Setter
    private List<PropiedadDetalle> listaPropiedadDetSelDerAcc;

    public ControladorCertificado() {

        disabledPredioCatastro = true;
        disabledRepertorio = true;
        disabledInscripcion = true;
        busquedaUno = false;
        busquedaDos = false;
        busquedaTres = false;
        tabSeleccionado = "";
//        mostrarTab = false;

        mostrarTabReferencias = false;
        mostrarTabPropiedad = false;
        mostrarTabPropietarios = false;
        mostrarTabAdquisiciones = false;
        mostrarTabGravamenes = false;
        mostrarTabObservaciones = false;
        mostrarTabCertificado = false;
        mostrarTabSolicitante = false;

        esNuevo = true;
        deshabilitarBusqueda = false;
        esCertificadoBusqueda = false;
        esCertificadoBienesRaices = false;
        esCertificadoEstatuto = false;
        esCertificadoPlantilla = false;

        mostrarPnlTabs2 = false;
        tieneGravamenes = false;

    }

    @PostConstruct()
    public void cargarDatos() {
        try {
            listaTipoCertificado = tipoCertificadoDao.listarTodo();
            inicializar();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void inicializar() {
        fecha = Calendar.getInstance().getTime(); //siempre carga la fecha del día
        tipoTrabajo = "A";
        numSaltosLinea = 0;
        contabilizarCertificados();
        esCertificadoVacio = false;
        descripcionPropiedad = "Ninguno";
        descripcionPropietarios = "Ninguno";
        adquisicionAntecedentes = "Ninguno";
        gravamen = "Ninguno";
        textoCertificado = "Ninguno";

    }

    public void contabilizarCertificados() {
        Long idUsuario = dataManagerSesion.getUsuarioLogeado().getUsuId();

        numTrabajosAsignados = servicioCertificadoCarga.numCerPendientesPor_usuario_tipo_estado_fecha_Activo(idUsuario, "CER", fecha, Boolean.FALSE).intValue();
        numTrabajosHabilitados = servicioCertificadoCarga.numCerPendientesPor_usuario_tipo_estado_fecha_Activo(idUsuario, "CER", fecha, Boolean.TRUE).intValue();

//        numCertificadosRealizados = servicioCertificadoCarga.numCerRealizadosPor_usuario_tipo_estado_fecha(idUsuario, "CER", fecha).intValue();
    }

    public void habilitarBusquedaUno() {

        if (busquedaUno == true) {
            busquedaDos = false;
            busquedaTres = false;
            disabledPredioCatastro = false;
            disabledRepertorio = true;
            disabledInscripcion = true;

        }
        if (busquedaUno == false) {
            disabledPredioCatastro = true;
        }

    }

    public void habilitarBusquedaDos() {

        if (busquedaDos == true) {
            busquedaUno = false;
            busquedaTres = false;
            disabledRepertorio = false;
            disabledPredioCatastro = true;
            disabledInscripcion = true;

        }
        if (busquedaDos == false) {
            disabledRepertorio = true;
        }

    }

    public void habilitarBusquedaTres() {

        if (busquedaTres == true) {
            busquedaDos = false;
            busquedaUno = false;
            disabledInscripcion = false;
            disabledPredioCatastro = true;
            disabledRepertorio = true;

        }
        if (busquedaTres == false) {
            disabledInscripcion = true;
        }
    }

    public boolean tieneDerAcc(String prdMatricula) {
        if (servicioPropiedadDetalle.listar_Por_Matricula_Estado_Tipo(prdMatricula, "GDA").isEmpty()) {
            return false;
        } else {
            return true;
        }

    }

    public void obtenerDerAcc(String prdMatricula) {
        listaPropiedadDetSelDerAcc = servicioPropiedadDetalle.listar_Por_Matricula_Estado_Tipo(prdMatricula, "GDA");
    }

    public void cambiarTab(TabChangeEvent event) {
        System.out.println("Se cambió el tab");
        PrimeFaces current = PrimeFaces.current();
        tabSeleccionado = event.getTab().getId();
        System.out.println("tabSeleccionado:"+tabSeleccionado);
        if (event.getTab().getId().equals("tab_Propiedad")) {
            if (actaSeleccionada != null) {
//                                listaPropiedadMostrar = servicioPropiedadDetalle.listar_Por_Propiedad_Tipo(propiedadSeleccionada.getPrdMatricula(), "GDA");

//                listaPropiedadMostrar = servicioPropiedadDetalle.listarPor_Acta(actaSeleccionada.getActNumero());
                listaPropiedadMostrar = servicioPropiedadDetalle.listarPor_Acta(actaSeleccionada.getActNumero());
                if (listaPropiedadMostrar.contains(propiedadSeleccionada)) {
                    listaPropiedadMostrar.remove(propiedadSeleccionada);
                }
//                if (!listaPropiedadMostrar.isEmpty()) {
//                    RequestContext.getCurrentInstance().execute("PF('dlgDerAcc').show()");
//                    current.executeScript("PF('dlgDerAcc').show()");
//                }
            }
        }
        if (tabSeleccionado.equals("tab_adquisicion")) {
            if (certificadoCargaSeleccionado.getFacId().getFacFormaAdquisicion() > 1) {
//                RequestContext.getCurrentInstance().execute("PF('dialogoActas').show()");
                current.executeScript("PF('dlgDerAcc').show()");

            }
        }

        if (tabSeleccionado.equals("tab_certificado")) {
            generarCertificadoSimple();
        }
    }

    public void cambiarTipoGravamen() {
        limpiarPanelDerecho();
        numFactura = "";
        numCertificados = "";
        mostrarPnlTabs = false;
        mostrarPnlCertPlantilla = false;
    }

    public void llenarTablaCertificadoCarga() throws ServicioExcepcion, ParseException {
        Long usuarioId = dataManagerSesion.getUsuarioLogeado().getUsuId();
        numFactura = "";
        numCertificados = "";
        listaCertificadoCarga = servicioCertificadoCarga.listarPor_Usuario_TipoCertificado_Tipo_Fecha(new BigDecimal(usuarioId), tipoCertificadoSeleccionado.getTroId(), "CER", fecha);
        switch (tipoTrabajo) {
            case "A":
                for (Iterator<CertificadoCarga> iterator = listaCertificadoCarga.iterator(); iterator.hasNext();) {
                    CertificadoCarga next = iterator.next();

                    if (next.getCdcActivo() != null && next.getCdcActivo()) { //elimina solo los que fueron habilitados(activo=true). Deja los que tienen nulo
                        iterator.remove();
                    }
                }

                break;
            case "H":
                for (Iterator<CertificadoCarga> iterator = listaCertificadoCarga.iterator(); iterator.hasNext();) {
                    CertificadoCarga next = iterator.next();
                    if (next.getCdcActivo() != null) {
                        if (!next.getCdcActivo()) {//elimina los que no estan habilitados(tienen activo=0=false)

                            iterator.remove();
                        }
                    } else {
                        //elimina a los que tienen nulo
                        iterator.remove();
                    }
                }

                break;
        }

//        contabilizarCertificados();
        for (Iterator<CertificadoCarga> iterator = listaCertificadoCarga.iterator(); iterator.hasNext();) {
            CertificadoCarga next = iterator.next();
            if (next.getCdcEstadoProceso() != null && next.getCdcEstadoProceso().equals("TERMINADO")) {//elimina los terminados
                iterator.remove();
            }
        }
//        if (listaCertificadoCarga.isEmpty()) {
//            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "No se encontraron datos", null));
//        }
    }

    public void seleccionarFila(SelectEvent event) throws ServicioExcepcion, ParseException {
        certificadoCargaSeleccionado = (CertificadoCarga) event.getObject();
        facturaSeleccionada = certificadoCargaSeleccionado.getFacId();

        numFactura = "" + certificadoCargaSeleccionado.getFacId().getFacNumero();
        numCertificados = "" + certificadoCargaSeleccionado.getFacId().getFacCertificado();
        numPeticion = "" + servicioFacturaDetalle.PorNumeroFactura(numFactura).getFdtTraNumero();
        //***VERIFICAR SI ES O NO NUEVO ---------//
        if (certificadoCargaSeleccionado.getCdcCertificado() == null || certificadoCargaSeleccionado.getCdcCertificado().isEmpty()) {
            esNuevo = true;
            limpiarPanelDerecho();
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage(FacesMessage.SEVERITY_INFO, "Certificado Nuevo", "Está creando un nuevo certificado"));
        } else {
            esNuevo = false;
            FacesContext.getCurrentInstance().addMessage("mensajes", new FacesMessage(FacesMessage.SEVERITY_INFO, "Certificado cargado", "Está editando un certificado existente"));
        }
        //----------------------------------***//

        //seleccionando el valor para el tipo de gravamen, si el certificado ya fue guardado con algun tipo especifico
        if (certificadoCargaSeleccionado.getCdcTipoGravamen() != null
                && !certificadoCargaSeleccionado.getCdcTipoGravamen().isEmpty()) {
            tipoGravamen = certificadoCargaSeleccionado.getCdcTipoGravamen();
        }

        switch (tipoCertificadoSeleccionado.getTroTipo()) {
            case "G":
                switch (tipoGravamen) {

                    case "NEGATIVA":
                        mostrarPnlTabs = false;
                        mostrarPnlCertPlantilla = true;
                        plantillaCertificado = tipoCertificadoSeleccionado.getTroDescripcion1();
                        cortarPlantillaEnPartes();
                        llenarVariables();
                        esCertificadoPlantilla = true;
                        break;
                    case "ESTATUTO":
                        mostrarPnlTabs = false;
                        mostrarPnlCertPlantilla = true;
                        plantillaCertificado = tipoCertificadoSeleccionado.getTroDescripcion2();
                        cortarPlantillaEnPartes();
                        llenarVariables();
                        esCertificadoPlantilla = true;
                        break;
                    default: //GRAVAMEN
                        mostrarPnlTabs = true;
                        esCertificadoPlantilla = false;
                        indiceTabViewCertificados=0;
                        if (esNuevo) {
//                            mostrarTab = false;
                            mostrarTabReferencias = true;
                            mostrarTabPropiedad = false;
                            mostrarTabPropietarios = false;
                            mostrarTabAdquisiciones = false;
                            mostrarTabGravamenes = false;
                            mostrarTabObservaciones = false;
                            mostrarTabCertificado = false;

                        } else {
//                            mostrarTab = false;
                            mostrarTabReferencias = true;
                            mostrarTabPropiedad = true;
                            mostrarTabPropietarios = true;
                            mostrarTabAdquisiciones = true;
                            mostrarTabGravamenes = true;
                            mostrarTabObservaciones = true;
                            mostrarTabCertificado = true;

                        }
                        break;
                }
                break;

            case "M":
                switch (tipoGravamen) {

                    case "NEGATIVA":
                        mostrarPnlTabs = false;
                        mostrarPnlCertPlantilla = true;
                        plantillaCertificado = tipoCertificadoSeleccionado.getTroDescripcion1();
                        if (plantillaCertificado != null && plantillaCertificado.isEmpty()) {
                            cortarPlantillaEnPartes();
                            llenarVariables();
                            esCertificadoPlantilla = true;
                        } else {
                            JsfUtil.addErrorMessage("Error, no se encontró una plantilla");
                        }
                        break;
                    case "ESTATUTO":
                        mostrarPnlTabs = false;
                        mostrarPnlCertPlantilla = true;
                        plantillaCertificado = tipoCertificadoSeleccionado.getTroDescripcion2();
                        if (plantillaCertificado != null && plantillaCertificado.isEmpty()) {
                            cortarPlantillaEnPartes();
                            llenarVariables();
                            esCertificadoPlantilla = true;
                        } else {
                            JsfUtil.addErrorMessage("Error, no se encontró una plantilla");
                        }
                        break;
                    default: //GRAVAMEN
                        mostrarPnlTabs = true;
                        esCertificadoPlantilla = false;
                        indiceTabViewCertificados=0;
                        if (esNuevo) {
//                            mostrarTab = false;
                            mostrarTabReferencias = true;
                            mostrarTabPropiedad = false;
                            mostrarTabPropietarios = false;
                            mostrarTabAdquisiciones = false;
                            mostrarTabGravamenes = false;
                            mostrarTabObservaciones = false;
                            mostrarTabCertificado = false;

                        } else {
//                            mostrarTab = false;
                            mostrarTabReferencias = true;
                            mostrarTabPropiedad = true;
                            mostrarTabPropietarios = true;
                            mostrarTabAdquisiciones = true;
                            mostrarTabGravamenes = true;
                            mostrarTabObservaciones = true;
                            mostrarTabCertificado = true;

                        }
                        break;
                }
                break;
            case "R":
                esCertificadoBienesRaices = true;
                mostrarTabSolicitante = false;
                mostrarPnlTabs2 = true;
                persona = null;
                identificacion = "";
                listaPropiedadesSel = new ArrayList<>();
                listaPropiedadesCertBusqueda = new ArrayList<>();
                propiedadSelAgregarObs = null;
                esCertificadoPlantilla = false;
//                mostrarPnlTabs = true;
//                mostrarPnlCertPlantilla = true;
//                plantillaCertificado = tipoCertificadoSeleccionado.getTroDescripcion1();
//                cortarPlantillaEnPartes();
//                llenarVariables();
                break;
            case "B":
                esCertificadoBusqueda = true;
                mostrarTabSolicitante = false;
                mostrarPnlTabs2 = true;
                persona = null;
                identificacion = "";
                listaPropiedadesSel = new ArrayList<>();
                listaPropiedadesCertBusqueda = new ArrayList<>();
                propiedadSelAgregarObs = null;
                esCertificadoPlantilla = false;

//                mostrarPnlTabs = true;
//                mostrarTabReferencias = true;
//                mostrarTabPropiedad = false;
//                mostrarTabPropietarios = false;
//                mostrarTabAdquisiciones = false;
//                mostrarTabGravamenes = false;
//                mostrarTabObservaciones = false;
//                mostrarTabCertificado = false;
                break;
            case "E":
                esCertificadoEstatuto = true;
                mostrarTabSolicitante = false;
                mostrarPnlTabs2 = true;
                tieneGravamenes = false;
                persona = null;
                identificacion = "";
                listaPropiedadesSel = new ArrayList<>();
                listaPropiedadesCertBusqueda = new ArrayList<>();
                propiedadSelAgregarObs = null;
                esCertificadoPlantilla = false;
                break;
            case "N":
                mostrarPnlTabs = false;
                mostrarPnlCertPlantilla = true;
                plantillaCertificado = tipoCertificadoSeleccionado.getTroDescripcion1();
                if (plantillaCertificado != null && plantillaCertificado.isEmpty()) {
                    cortarPlantillaEnPartes();
                    llenarVariables();
                    esCertificadoPlantilla = true;
                } else {
                    JsfUtil.addErrorMessage("Error, no se encontró una plantilla");
                }

                break;
            case "P":
                mostrarPnlTabs = false;
                mostrarPnlCertPlantilla = true;
                plantillaCertificado = tipoCertificadoSeleccionado.getTroDescripcion1();
                if (plantillaCertificado != null && plantillaCertificado.isEmpty()) {
                    cortarPlantillaEnPartes();
                    llenarVariables();
                    esCertificadoPlantilla = true;
                } else {
                    JsfUtil.addErrorMessage("Error, no se encontró una plantilla");
                }
                break;
            default:
                break;

        }

        if (!esNuevo) {
            cargarCertificado();
        } else {
            certificadoSeleccionado = new Certificado();
        }
        actualizarEstadoCertCarg();
        indiceTabViewCertificados = 0;
    }

    public void cortarPlantillaEnPartes() {
        listaPartesCertificado = TextoUtil.recortarAlEncontrarCaracter(plantillaCertificado);
        System.out.println("texto cortado en: " + listaPartesCertificado.size() + " partes");
        variables = new String[listaPartesCertificado.size()];
        for (int i = 0; i < listaPartesCertificado.size(); i++) {
            variables[i] = " ";
        }

    }

    public void llenarVariables() throws ServicioExcepcion {
        String resultado = "";
        String nombres = "";
        String parroquia = "";
        String fechaFactura = "";
        if (facturaSeleccionada != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

            nombres += facturaSeleccionada.getFacCertificadoPrimerApe()
                    + " " + facturaSeleccionada.getFacCertificadoSegundoApe() + " " + facturaSeleccionada.getFacCertificadoNombre();
//            fechaFactura+=format.format(facturaSeleccionada.getFacFHR());
            Calendar fechaCal = Calendar.getInstance();
            fechaCal.setTime(facturaSeleccionada.getFacFHR());
            fechaFactura = FechasUtil.convertir_A_letras(fechaCal.get(Calendar.DAY_OF_MONTH), fechaCal.get(Calendar.MONTH), fechaCal.get(Calendar.YEAR));
        }

        parroquia += dataManagerSesion.getUsuarioLogeado().getZonId().getParId().getParNombre();

        switch (tipoCertificadoSeleccionado.getTroTipo()) {
            case "G": //GRAVAMEN
                switch (tipoGravamen) {

                    case "NEGATIVA":
                        variables[0] = fechaFactura;
                        break;
                    case "ESTATUTO":
                        variables[0] = nombres;
                        variables[1] = parroquia;
                        variables[2] = fechaFactura;
                        break;
                }
                break;

            case "M": //GRAVAMEN
                switch (tipoGravamen) {

                    case "NEGATIVA":
                        variables[0] = fechaFactura;
                        break;
                    case "ESTATUTO":
                        variables[0] = nombres;
                        variables[1] = parroquia;
                        variables[2] = fechaFactura;
                        break;
                }
                break;
//            case "R":
//                variables[0] = "";
//                break;
//            case "B":
//                variables[0] = "";
//                break;
//            case "N":
//                variables[0] = "";
//                break;
            case "P"://PROPIEDAD
                variables[0] = "El Infrascrito Registrador de la Propiedad de este Cantón,"
                        + " en legal forma certifica, que revisados los índices de los Registro de Propiedad de este Cantón";
                variables[1] = fechaFactura;
                break;
            default:
                break;

        }

//        resultado = String.format(textoCertificado, nombres, parroquia, fechaFactura);
//        System.out.println("**resultado: " + resultado);
//        Map<String, String> substitutes = new HashMap<>();
//        substitutes.put("name", "John");
//        substitutes.put("college", "University of Stanford");
//        String templateString = "My name is ${name} and I am a student at the ${college}.";
//        StrSubstitutor sub = new StrSubstitutor(substitutes);
//        String result = sub.replace(templateString);
//
//        System.out.println("**result: " + result);
//        return resultado;
    }

    public String crearCabeceraCertificadoSimple() {
        return crearCabeceraCertificado("Certificado");
    }

    public String crearCabeceraCertificadoPropiedad() {
        String cabecera = "";
        Date fechaIng = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String txtFechaIng = format.format(fechaIng);
        String numCertificado = generarNumeroCertificado();
        String canton = servicioInstitucion.encontrarCanton();
        
        for (int i = 0; i < numSaltosLinea; i++) {
            cabecera += "<br>";
        }
        cabecera = ""
                + "<p align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + canton + " </strong></p>"
                + "<p align='center'><strong>CERTIFICADO DE VENTAS No. : " + numCertificado + " </strong></p>"
                + "<p align='center'><strong>FECHA DE INGRESO:</strong>" + txtFechaIng + "</p><br>"
                + "<p align='center'><strong>" + "CERTIFICACIÓN" + "</strong></p><br>";
        return cabecera;
    }

    public String crearCabeceraCertificado(String nombreCertificado) {
        String cabecera = "";

        Date fechaIng = Calendar.getInstance().getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        String txtFechaIng = format.format(fechaIng);
        String numCertificado = generarNumeroCertificado();
        String canton = servicioInstitucion.encontrarCanton();
        for (int i = 0; i < numSaltosLinea; i++) {
            cabecera += "<br>";
        }
        cabecera += ""
                + "<p align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + canton + " </strong></p>"
                + "<p align='center'><strong>CERTIFICADO No. : " + numCertificado + " </strong></p>"
                + "<p align='center'><strong>FECHA DE INGRESO:</strong>" + txtFechaIng + "</p><br>"
                + "<p align='center'><strong>" + nombreCertificado + "</strong></p><br>";

        return cabecera;
    }

    public String crearPieCertificado() {
        String pie = "";
        String numCertificado = "" + generarNumeroCertificado();
        Persona responsable = dataManagerSesion.getUsuarioLogeado().getPerId();
        String nombresResponsable = responsable.getPerApellidoPaterno()
                + " " + responsable.getPerApellidoMaterno()
                + " " + responsable.getPerNombre();
        pie += "<p><strong>Responsable: </strong> " + nombresResponsable + "</p>"
                + "<p><br></p>"
                + "<p><strong> </strong></p>"
                + "<p><strong> EL REGISTRADOR</strong></p>"
                + "<p><strong> </strong>&nbsp;</p>"
                + "<p>" + numCertificado + "</p>";
        String urlImagenEspacioEnBlanco = "";
        if (agregarImagenEspacioEnBlanco) {
            try {
                urlImagenEspacioEnBlanco = servicioConfigDetalle.buscarPorConfig("IMAGENBLANCO").getConfigDetalleTexto();
            } catch (ServicioExcepcion ex) {
                Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
            }
            pie += "<img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }
        return pie;
    }

    public void generarCertificado() {
        switch (tipoCertificadoSeleccionado.getTroTipo()) {
            case "G":
                switch (tipoGravamen) {

                    case "NEGATIVA":

                        unirCertificadoPlantilla();
                        break;
                    case "ESTATUTO":
                        unirCertificadoPlantilla();
                        break;
                    default: //GRAVAMEN
                        generarCertificadoSimple();
                }
                break;
            case "M":
                switch (tipoGravamen) {

                    case "NEGATIVA":

                        unirCertificadoPlantilla();
                        break;
                    case "ESTATUTO":
                        unirCertificadoPlantilla();
                        break;
                    default: //GRAVAMEN
                        generarCertificadoMatriculaSimple();
                }
                break;
            case "R":
                generarCertificadoBienesRaices();
                break;
            case "B":
                generarCertificadoBusqueda();
                break;
            case "E":
                generarCertificadoEstatuto();
                break;
            case "N":
                unirCertificadoPlantilla();
            case "P":
                unirCertificadoPlantilla();
                break;
            default:
                break;

        }
    }

    public void unirCertificadoPlantilla() {
        String cabecera = "";
        String cuerpo = "";
        String pie = "";

        textoCertificado = "";
        switch (tipoCertificadoSeleccionado.getTroTipo()) {
            case "G":
                switch (tipoGravamen) {
                    case "NEGATIVA":
                        cabecera = crearCabeceraCertificado("NEGATIVA");
                        break;
                    case "ESTATUTO":
                        cabecera = crearCabeceraCertificado("CERTIFICACIÓN");
                        break;
                    default:
                        break;
                }
                break;
            case "M":
                switch (tipoGravamen) {
                    case "NEGATIVA":
                        cabecera = crearCabeceraCertificado("NEGATIVA");
                        break;
                    case "ESTATUTO":
                        cabecera = crearCabeceraCertificado("CERTIFICACIÓN");
                        break;
                    default:
                        break;
                }
                break;
            case "R":
                cabecera = crearCabeceraCertificado("CERTIFICADO");
                break;
            case "B":
//                cabecera = crearCabeceraCertificadoBusqueda();
                break;
            case "N":
                cabecera = crearCabeceraCertificado("CERTIFICADO");
                break;
            case "P":
                cabecera = crearCabeceraCertificadoPropiedad();
                break;
            default:
                break;

        }

        //variable:cabecera
        for (int i = 0; i < listaPartesCertificado.size(); i++) {
            cuerpo += listaPartesCertificado.get(i) + variables[i];
        }
        pie = crearPieCertificado();
        System.out.println("cuerpo certificado: " + cuerpo);
        textoCertificado += "<font size='-1'>" + cabecera + cuerpo + pie + "</font>";
        try {
            generarCertificadoPdf();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void sumarSaltoLinea() throws IOException, ServicioExcepcion {
        numSaltosLinea++;

    }

    public void restarSaltoLinea() throws IOException, ServicioExcepcion {
        if (numSaltosLinea > 0) {
            numSaltosLinea--;

        }

    }

    //carga los datos del certificado si ya existe
    public void cargarCertificado() {
        certificadoSeleccionado = new Certificado();

        try {
            certificadoSeleccionado = servicioCertificado.listarPorCerNumero(certificadoCargaSeleccionado.getCdcCertificado()).get(0);

            switch (tipoCertificadoSeleccionado.getTroTipo()) {
                case "G":
                    switch (tipoGravamen) {

                        case "NEGATIVA":

                            textoCertificado = certificadoSeleccionado.getCerCertificado();
                            break;
                        case "ESTATUTO":

                            textoCertificado = certificadoSeleccionado.getCerCertificado();
                            break;
                        default:
                            //con el certificado se cargan las listas dependientes: acta,propietario,gravamen,propiedad
                            if (certificadoSeleccionado.getPropiedadList() != null && !certificadoSeleccionado.getPropiedadList().isEmpty()) {
                                propiedadSeleccionada = certificadoSeleccionado.getPropiedadList().get(0);
                            }
                            if (listaGravamenesSelec != null && !listaGravamenesSelec.isEmpty()) {
                                listaGravamenesSelec = certificadoSeleccionado.getGravamenList();
                            }
                            if (propiedadSeleccionada != null) {
                                listaActasSeleccionadas = servicioActa.listarActaPor_PropiedadCatastro_PropiedadPredio_OrdenadoPorFecha(propiedadSeleccionada.getPrdPredio(), propiedadSeleccionada.getPrdCatastro());
                            }
                            if (certificadoSeleccionado.getPropietarioList() != null && !certificadoSeleccionado.getPropietarioList().isEmpty()) {
                                listaPropietariosSeleccionados = certificadoSeleccionado.getPropietarioList();
                            }

                            descripcionPropietarios = certificadoSeleccionado.getCerPropietario();
                            gravamen = certificadoSeleccionado.getCerGravamen();
                            adquisicionAntecedentes = certificadoSeleccionado.getCerAdquisicion();
                            descripcionPropiedad = certificadoSeleccionado.getCerPropiedad();
                            //observacion ya se carga con el objeto
                            textoCertificado = certificadoSeleccionado.getCerCertificado();
                    }
                    break;
                case "M":
                    switch (tipoGravamen) {

                        case "NEGATIVA":

                            textoCertificado = certificadoSeleccionado.getCerCertificado();
                            break;
                        case "ESTATUTO":

                            textoCertificado = certificadoSeleccionado.getCerCertificado();
                            break;
                        default:
                            //con el certificado se cargan las listas dependientes: acta,propietario,gravamen,propiedad
                            if (certificadoSeleccionado.getPropiedadList() != null && !certificadoSeleccionado.getPropiedadList().isEmpty()) {
                                propiedadSeleccionada = certificadoSeleccionado.getPropiedadList().get(0);
                            }
                            if (listaGravamenesSelec != null && !listaGravamenesSelec.isEmpty()) {
                                listaGravamenesSelec = certificadoSeleccionado.getGravamenList();
                            }
                            if (propiedadSeleccionada != null) {
                                listaActasSeleccionadas = servicioActa.listarActaPor_PropiedadCatastro_PropiedadPredio_OrdenadoPorFecha(propiedadSeleccionada.getPrdPredio(), propiedadSeleccionada.getPrdCatastro());
                            }
                            if (certificadoSeleccionado.getPropietarioList() != null && !certificadoSeleccionado.getPropietarioList().isEmpty()) {
                                listaPropietariosSeleccionados = certificadoSeleccionado.getPropietarioList();
                            }

                            descripcionPropietarios = certificadoSeleccionado.getCerPropietario();
                            gravamen = certificadoSeleccionado.getCerGravamen();
                            adquisicionAntecedentes = certificadoSeleccionado.getCerAdquisicion();
                            descripcionPropiedad = certificadoSeleccionado.getCerPropiedad();
                            //observacion ya se carga con el objeto
                            textoCertificado = certificadoSeleccionado.getCerCertificado();
                    }
                    break;
                case "R":
                    textoCertificado = certificadoSeleccionado.getCerCertificado();
                    break;
                case "B":
                    textoCertificado = certificadoSeleccionado.getCerCertificado();
                    break;
                case "N":
                    break;
                case "P":
                    break;
                default:
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualizarEstadoCertCarg() {
        if (certificadoCargaSeleccionado.getCdcEstadoProceso().equals("ACTIVO")) {
            certificadoCargaSeleccionado.setCdcEstadoProceso("PROCESO");
            servicioCertificadoCarga.edit(certificadoCargaSeleccionado);
        }
    }

    public void inicializarCamposEditablesTabs() {
        descripcionPropiedad = "Ninguno";
        descripcionPropietarios = "Ninguno";
        adquisicionAntecedentes = "Ninguno";
        gravamen = "Ninguno";
        textoCertificado = "Ninguno";
    }

    public void incializarListas() {
        listaActasSeleccionadas = new ArrayList();
        listaGravamenesSelec = new ArrayList();
        listaPropietariosSeleccionados = new ArrayList();
        listaActaSeleccionadas2 = new ArrayList();
        listaPropiedadSelec = new ArrayList<>();
        listaPropiedadMostrar = new ArrayList<>();

    }

    public void inicializarObjetos() {
        certificadoSeleccionado = null;
        propiedadSeleccionada = null;
        actaSeleccionada = null;
    }

    public void limpiarPanelDerecho() {
        inicializarCamposEditablesTabs();
        incializarListas();
        inicializarObjetos();
        predio = "";
        catastro = "";
        busquedaUno = false;
        busquedaDos = false;
        busquedaTres = false;

        disabledRepertorio = true;
        disabledInscripcion = true;
        disabledPredioCatastro = true;
        esCertificadoVacio = false;
        deshabilitarBusqueda = false;

        agregarImagenEspacioEnBlanco = false;
        numSaltosLinea = 0;

        esCertificadoBienesRaices = false;
        esCertificadoBusqueda = false;
        esCertificadoEstatuto = false;
        esCertificadoPlantilla = false;
        esCertificadoVacio = false;

    }

    public void buscarReferencias() {
        try {
            certificadoSeleccionado.setCerObservacion("Ninguna");
            if (busquedaUno) {
                //ACTA 
                listaActasSeleccionadas = servicioActa.listarActaPor_PropiedadCatastro_PropiedadPredio_OrdenadoPorFecha(predio, catastro);
                if (!listaActasSeleccionadas.isEmpty()) {
                    //PROPIEDAD
                    propiedadSeleccionada = listaActasSeleccionadas.get(0).getPrdMatricula();

                    //PROPIETARIOS
                    listaPropietariosSeleccionados = servicioPropietario.buscarPor_PropiedadMatricula_Estado(propiedadSeleccionada.getPrdMatricula());

                    //GRAVAMENES
                    listaGravamenesSelec = servicioGravamen.buscarPorMatricula(propiedadSeleccionada);

//                    gravamen = "Ninguna";
//                    switch (tipoCertificadoSeleccionado.getTroTipo()) {
//                        case "G":
//                            generarCertificadoSimple();
//                            break;
//                        case "R":
//
//                            break;
//                        case "B":
//                            break;
//                    }
                    esCertificadoVacio = false;
                } else {
                    esCertificadoVacio = true;
                    FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_WARN, "No se encontraron datos, proceda a crear una acta", null));
                }
                listaCertificadoAnteriores = servicioCertificado.listarPor_PropiedadCatastro_PropiedadPredio_OrdenadoPorFecha(predio, catastro);
            } else if (busquedaDos) {
//PROPIEDAD
                propiedadSeleccionada = servicioRepertorioPropiedad.buscarPropiedad_Por_Repertorio(numRepertorio, fchRepertorio);
                if (propiedadSeleccionada != null) {
                    //PROPIETARIOS
                    listaPropietariosSeleccionados = servicioPropietario.buscarPor_PropiedadMatricula_Estado(propiedadSeleccionada.getPrdMatricula());
                    //ACTA 
                    listaActasSeleccionadas = servicioActa.listarActaPor_PropiedadCatastro_PropiedadPredio_OrdenadoPorFecha(propiedadSeleccionada.getPrdPredio(), propiedadSeleccionada.getPrdCatastro());
                    //GRAVAMENES
                    listaGravamenesSelec = servicioGravamen.buscarPorMatricula(propiedadSeleccionada);

//                    switch (tipoCertificadoSeleccionado.getTroTipo()) {
//                        case "G":
//                            generarCertificadoSimple();
//                            break;
//                        case "M":
//                            generarCertificadoSimple();
//                            break;
//                        case "R":
//
//                            break;
//                        case "B":
//                            break;
//                    }
                    esCertificadoVacio = false;

                } else {
                    esCertificadoVacio = true;

                    FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_WARN, "No se encontraron datos, proceda a crear una acta", null));
                }

            } else if (busquedaTres) {
                //ACTA 
                actaSeleccionada = servicioActa.buscarActa_por_NumInscripcion_Fecha_TipoLibro(BigInteger.valueOf(numInscripcion), fchActa, tipoLibroSeleccionado.getTplId());
                if (actaSeleccionada != null) {
                    listaActasSeleccionadas = new ArrayList<>();
                    //PROPIEDAD
                    propiedadSeleccionada = servicioRepertorioPropiedad.buscarPropiedad_Por_Repertorio(
                            actaSeleccionada.getRepNumero().getRepNumero(), actaSeleccionada.getRepNumero().getRepFHR());
                    //PROPIETARIOS
                    listaPropietariosSeleccionados = servicioPropietario.buscarPor_PropiedadMatricula_Estado(propiedadSeleccionada.getPrdMatricula());
                    //ACTA 
                    listaActasSeleccionadas.add(actaSeleccionada);
                    //GRAVAMENES
                    listaGravamenesSelec = servicioGravamen.buscarPorMatricula(propiedadSeleccionada);
//                    switch (tipoCertificadoSeleccionado.getTroTipo()) {
//                        case "G":
//                            generarCertificadoSimple();
//                            break;
//                        case "M":
//                            generarCertificadoSimple();
//                            break;
//                        case "R":
//
//                            break;
//                        case "B":
//                            break;
//                    }
                    esCertificadoVacio = false;
                } else {
                    esCertificadoVacio = true;
                    FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_WARN, "No se encontraron datos, proceda a crear una acta", null));
                }
            }
            seleccionarPropiedades();
//            if (esCertificadoVacio) {
//                habilitarTabsCertificadoSimple();
//            } else {
//                desHabilitarTabsCertificadoSimple();
//            }
            deshabilitarBusqueda = true;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_WARN, "Búsqueda sin resultado. Aún puede crear un certificado.", null));
        }

    }

    public void habilitarTabsCertificadoSimple() {
        mostrarTabPropiedad = true;
        mostrarTabPropietarios = true;
        mostrarTabAdquisiciones = true;
        mostrarTabGravamenes = true;
        mostrarTabObservaciones = true;
        mostrarTabCertificado = true;
    }

    public void desHabilitarTabsCertificadoSimple() {
        mostrarTabPropiedad = false;
        mostrarTabPropietarios = false;
        mostrarTabAdquisiciones = false;
        mostrarTabGravamenes = false;
        mostrarTabObservaciones = false;
        mostrarTabCertificado = false;
    }

    public void seleccionarActa(Acta acta) {
        actaSeleccionada = acta;
        switch (tipoCertificadoSeleccionado.getTroTipo()) {
            case "G":
                generarCertificadoSimple();
                mostrarTabReferencias = true;
                mostrarTabPropiedad = true;
                mostrarTabPropietarios = true;
                mostrarTabAdquisiciones = true;
                mostrarTabGravamenes = true;
                mostrarTabObservaciones = true;
                mostrarTabCertificado = true;
                break;
            case "M":
                generarCertificadoMatriculaSimple();
                mostrarTabReferencias = true;
                mostrarTabPropiedad = true;
                mostrarTabPropietarios = true;
                mostrarTabAdquisiciones = true;
                mostrarTabGravamenes = true;
                mostrarTabObservaciones = true;
                mostrarTabCertificado = true;
                break;
            case "R":

                break;
            case "B":
//                generarCertificadoBusqueda();
//                mostrarTabPropietarios = true;
//                mostrarTabObservaciones = true;
//                mostrarTabCertificado = true;
                break;
        }

    }

    public String removerHTML(String textoHtml) {
        return textoHtml.replaceAll("<.+?>", "");
    }

    public void seleccionarPropiedades() {
        int numPropiedadesPuedeSeleccionar = certificadoCargaSeleccionado.getCdcCantidadDerecho() - 1;
        if (listaPropiedadSelec.size() <= numPropiedadesPuedeSeleccionar) {
            generarDescripcionPropiedad();
            generarDescripcionPropietarios();
            generarDescripcionGravamenes();
        } else {
            listaPropiedadSelec.clear();
            JsfUtil.addErrorMessage("Solo puede seleccionar " + numPropiedadesPuedeSeleccionar + " " + "propiedades");
        }

    }

    public void generarDescripcionPropiedad() {
        descripcionPropiedad = "";
        if (propiedadSeleccionada != null) {
            descripcionPropiedad += propiedadSeleccionada.getPrdDescripcion2() + " " + propiedadSeleccionada.getPrdDescripcion1()
                    + "de la Propiedad con matrícula " + propiedadSeleccionada.getPrdMatricula() + "; " + "\n\n";
        }

        if (listaPropiedadSelec != null && !listaPropiedadSelec.isEmpty()) {
            for (Propiedad propiedad : listaPropiedadSelec) {
//                Propiedad propiedad=prpdDtll.getPrdMatricula();
                descripcionPropiedad += propiedad.getPrdDescripcion2() + " " + propiedad.getPrdDescripcion1()
                        + "de la Propiedad con matrícula " + propiedad.getPrdMatricula() + "; ";
                if (tieneDerAcc(propiedad.getPrdMatricula())) {
                    for (PropiedadDetalle prpdDtll : listaPropiedadDetSelDerAcc) {
                        if (prpdDtll.getPdtTipo().equalsIgnoreCase("GDA")) {
                            Persona persona = servicioPersona.find(new Persona(), prpdDtll.getPdtPerId().longValue());

                            descripcionPropiedad += "\n" + prpdDtll.getPdtDescripcion() + " de la Propiedad con matrícula "
                                    + prpdDtll.getPdtPrdMatricula() + " a la persona " + persona.getPerApellidoPaterno().trim()
                                    + " " + persona.getPerApellidoMaterno().trim() + " " + persona.getPerNombre().trim() + "; " + "\n";
                        }
                    }

                }
                descripcionPropiedad += "\n" + "\n";

            }
        }

    }

    public void generarDescripcionPropietarios() {
        listaPropietariosSeleccionados = new ArrayList();
        descripcionPropietarios = "";
        if (propiedadSeleccionada != null) {
            listaPropietariosSeleccionados.addAll(servicioPropietario.buscarPor_PropiedadMatricula_Estado(propiedadSeleccionada.getPrdMatricula()));
        }
        System.out.println("tamaño lista propietarios: " + listaPropietariosSeleccionados.size());
        for (Propiedad prpd : listaPropiedadSelec) {
            listaPropietariosSeleccionados.addAll(servicioPropietario.buscarPor_PropiedadMatricula_Estado(prpd.getPrdMatricula()));
        }
        System.out.println("tamaño lista propietarios: " + listaPropietariosSeleccionados.size());
        //PROPIETARIOS
        if (listaPropietariosSeleccionados != null && !listaPropietariosSeleccionados.isEmpty()) {
            descripcionPropietarios = "";
            for (Propietario prop : listaPropietariosSeleccionados) {
                Persona persona = prop.getPerId();
                descripcionPropietarios += persona.getPerApellidoPaterno().trim()
                        + " " + persona.getPerApellidoMaterno().trim()
                        + " " + persona.getPerNombre().trim() + " con cédula de identidad"
                        + " " + persona.getPerIdentificacion().trim() + " ";
                if (persona.getPerIdConyuge() != null && persona.getPerIdConyuge().getPerId() != 0) {
                    descripcionPropietarios += " casado con"
                            + " " + persona.getPerIdConyuge().getPerApellidoPaterno().trim()
                            + " " + persona.getPerIdConyuge().getPerApellidoMaterno().trim()
                            + " " + persona.getPerIdConyuge().getPerNombre().trim() + " con cédula de identidad"
                            + " " + persona.getPerIdConyuge().getPerIdentificacion().trim();
                }
                descripcionPropietarios += ";" + "\n\n";
            }
        }

    }

    public void generarDescripcionGravamenes() {
        listaGravamenesSelec = new ArrayList<>();
        if (propiedadSeleccionada != null) {
            listaGravamenesSelec.addAll(servicioGravamen.buscarPorMatricula(propiedadSeleccionada));
        }

        for (Propiedad prpd : listaPropiedadSelec) {
//            Propiedad prpd = prpdDtll.getPrdMatricula();
            listaGravamenesSelec.addAll(servicioGravamen.buscarPorMatricula(prpd));
        }

        if (listaGravamenesSelec != null && !listaGravamenesSelec.isEmpty()) {
            gravamen = "";
            String fecha = "";
            Acta acta = new Acta();
            for (Gravamen grav : listaGravamenesSelec) {

                List<TramiteDetalle> listTramDet = new ArrayList<>();
                listTramDet.clear();

                try {
                    listTramDet = tramiteDetalleDao.listarRepertorio(grav.getRepNumero().getRepNumero().intValue());
                } catch (ServicioExcepcion ex) {
                    Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                }

                String nombreComparecientes = "";
                for (TramiteDetalle tramiteDetalle : listTramDet) {

                    if (tramiteDetalle.getTtcId().getTtcPropietario().equals("S")) {
                        String apMaterno = "";
                        if (tramiteDetalle.getPerId().getPerApellidoMaterno() != null) {
                            apMaterno = tramiteDetalle.getPerId().getPerApellidoMaterno();
                        } else {
                            apMaterno = "";
                        }
                        nombreComparecientes = tramiteDetalle.getPerId().getPerNombre().trim() + " "
                                + tramiteDetalle.getPerId().getPerApellidoPaterno().trim() + " "
                                + apMaterno.trim() + ", " + tramiteDetalle.getTdtTpcDescripcion() + "<br></br>";
                    }
                }
                try {
                    acta = servicioActa.buscarActaPorNumRepertorio(grav.getRepNumero().getRepNumero());
                } catch (ServicioExcepcion ex) {
                    Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                }
                gravamen = gravamen + "Matrícula: " + grav.getPrdMatricula().getPrdMatricula()
                        + ", Predio: " + grav.getPrdMatricula().getPrdPredio()
                        + ", Catastro: " + grav.getPrdMatricula().getPrdCatastro()
                        + ", " + grav.getRepNumero().getRepTtrDescripcion().trim()
                        + ", " + fechasUtil.convertirFecha_A_letras(grav.getRepNumero().getRepFHR()).trim()
                        + ", Rep: " + grav.getRepNumero().getRepNumero()
                        + ", Nro. Inscripción: " + acta.getActInscripcion() + ".<br></br>"
                        + "Comparecientes: <br></br>"
                        //                                    + gravamenDetalle.getPerId().getPerNombre().trim() + " "
                        //                                    + gravamenDetalle.getPerId().getPerApellidoPaterno().trim() + " "
                        //                                    + apMaterno.trim()
                        + nombreComparecientes;

                gravamen = removerHTML(gravamen);
                gravamen += ";" + "\n\n";
            }

        }
    }

    public String reemplazarPuntoComaConSalto(String texto) {
        texto = texto.replaceAll(";", "<br>");
        return texto;
    }

    public void generarCertificadoSimple() {
        String fechaActa = "";
        String tipoPropiedad = "";
        String parroquia = "";
        String matricula = "";
        String observacion = "";
        String numCertificado = "";
        String predio = "";
        String catastro = "";
        String numActa = "";
        String numInstitucion = "";
        String numeroRepertorio = "";
        String nombresResponsable = "";
//        descripcionPropiedad = "";
        textoCertificado = "";
//        descripcionPropietarios = "";

        //CERTIFICADO
        numCertificado = generarNumeroCertificado();

        //PROPIEDAD
        if (propiedadSeleccionada != null) {
//            descripcionPropiedad = "";
            tipoPropiedad += propiedadSeleccionada.getTpdId().getTpdNombre();
            parroquia += propiedadSeleccionada.getPrdTdtParNombre();
            matricula += propiedadSeleccionada.getPrdMatricula().toString();
            predio += propiedadSeleccionada.getPrdPredio();
            catastro += propiedadSeleccionada.getPrdCatastro();
//            descripcionPropiedad += propiedadSeleccionada.getPrdDescripcion2() + " " + propiedadSeleccionada.getPrdDescripcion1()
//                    + "de la Propiedad con matrícula " + propiedadSeleccionada.getPrdMatricula() + "; " + "\n\n";
//            if (listaPropiedadSelec != null && !listaPropiedadSelec.isEmpty()) {
//                System.out.println("Agregando propiedades seleccionadas en dlgderacc");
//                for (PropiedadDetalle prpdDtll : listaPropiedadSelec) {
//                    Persona persona = servicioPersona.find(new Persona(), prpdDtll.getPdtPerId().longValue());
//                    descripcionPropiedad += prpdDtll.getPdtDescripcion() + " de la Propiedad con matrícula "
//                            + prpdDtll.getPdtPrdMatricula() + " a la persona " + persona.getPerApellidoPaterno().trim()
//                            + " " + persona.getPerApellidoMaterno().trim() + " " + persona.getPerNombre().trim() + "; " + "\n\n";
//                }
//            }

        }
//PROPIETARIOS
//        if (listaPropietariosSeleccionados != null && !listaPropietariosSeleccionados.isEmpty()) {
//            descripcionPropietarios = "";
//            for (Propietario prop : listaPropietariosSeleccionados) {
//                Persona persona = prop.getPerId();
//                descripcionPropietarios += persona.getPerApellidoPaterno().trim()
//                        + " " + persona.getPerApellidoMaterno().trim()
//                        + " " + persona.getPerNombre().trim() + " con cédula de identidad"
//                        + " " + persona.getPerIdentificacion().trim() + " ";
//                if (persona.getPerIdConyuge() != null && persona.getPerIdConyuge().getPerId() != 0) {
//                    descripcionPropietarios += " casado con"
//                            + " " + persona.getPerIdConyuge().getPerApellidoPaterno().trim()
//                            + " " + persona.getPerIdConyuge().getPerApellidoMaterno().trim()
//                            + " " + persona.getPerIdConyuge().getPerNombre().trim() + " con cédula de identidad"
//                            + " " + persona.getPerIdConyuge().getPerIdentificacion().trim() + "\n\n";
//                }
//
//            }
//        }
//ADQUISICION ANTECEDENTES ACTA
        if (actaSeleccionada != null) {
            //ACTA
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            fechaActa = format.format(actaSeleccionada.getActFch());
            numActa = actaSeleccionada.getActNumero().toString();
            //ANTECEDENTES
            if (adquisicionAntecedentes.equalsIgnoreCase("Ninguno") || adquisicionAntecedentes.equalsIgnoreCase("Ninguna")) {
                adquisicionAntecedentes = "";
                if (actaSeleccionada.getActDescripcion3() == null) {
                    adquisicionAntecedentes = "Ninguna";
                } else {
                    adquisicionAntecedentes = removerHTML(actaSeleccionada.getActDescripcion3()) + "; " + "\n\n";
                }
            }
            //repertorio
            numeroRepertorio = actaSeleccionada.getRepNumero().getRepNumero().toString();

        }
        //caso especial, se reemplaza la adquisicion con las actas seleccionadas en el dialogo que se abre despues de seleccionar el tab

//else {
//            adquisicionAntecedentes = certificadoSeleccionado.getCerAdquisicion();
//        }
//GRAVAMENES
//        if (listaGravamenesSelec != null && !listaGravamenesSelec.isEmpty()) {
//            gravamen = "";
//            String fecha = "";
//            Acta acta = new Acta();
//            for (Gravamen grav : listaGravamenesSelec) {
////                gravamen += grav.getGraDescripcion();
////                if (listaGravamenesSelec.indexOf(grav) + 1 < listaGravamenesSelec.size()) {
////                    gravamen += ", ";
////                }
//                List<TramiteDetalle> listTramDet = new ArrayList<>();
//                listTramDet.clear();
//
//                try {
//                    listTramDet = tramiteDetalleDao.listarRepertorio(grav.getRepNumero().getRepNumero().intValue());
//                } catch (ServicioExcepcion ex) {
//                    Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                String nombreComparecientes = "";
//                for (TramiteDetalle tramiteDetalle : listTramDet) {
//
//                    if (tramiteDetalle.getTtcId().getTtcPropietario().equals("S")) {
//                        String apMaterno = "";
//                        if (tramiteDetalle.getPerId().getPerApellidoMaterno() != null) {
//                            apMaterno = tramiteDetalle.getPerId().getPerApellidoMaterno();
//                        } else {
//                            apMaterno = "";
//                        }
//                        nombreComparecientes = tramiteDetalle.getPerId().getPerNombre().trim() + " "
//                                + tramiteDetalle.getPerId().getPerApellidoPaterno().trim() + " "
//                                + apMaterno.trim() + ", " + tramiteDetalle.getTdtTpcDescripcion() + "<br></br>";
//                    }
//                }
//                try {
//                    acta = servicioActa.buscarActaPorNumRepertorio(grav.getRepNumero().getRepNumero());
//                } catch (ServicioExcepcion ex) {
//                    Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                gravamen = gravamen + "Matrícula: " + grav.getPrdMatricula().getPrdMatricula()
//                        + "; Predio: " + grav.getPrdMatricula().getPrdPredio()
//                        + "; Catastro: " + grav.getPrdMatricula().getPrdCatastro()
//                        + "; " + grav.getRepNumero().getRepTtrDescripcion().trim()
//                        + "; " + fechasUtil.convertirFecha_A_letras(grav.getRepNumero().getRepFHR()).trim()
//                        + "; Rep: " + grav.getRepNumero().getRepNumero()
//                        + "; Nro. Inscripción: " + acta.getActInscripcion() + ".<br></br>"
//                        + "Comparecientes: <br></br>"
//                        //                                    + gravamenDetalle.getPerId().getPerNombre().trim() + " "
//                        //                                    + gravamenDetalle.getPerId().getPerApellidoPaterno().trim() + " "
//                        //                                    + apMaterno.trim()
//                        + nombreComparecientes;
//                gravamen = removerHTML(gravamen);
//            }
//        }
        //OBSERVACIONES
        if (certificadoSeleccionado.getCerObservacion() != null) {
            observacion = "";
            observacion += certificadoSeleccionado.getCerObservacion();
        }
        try {
            Institucion institucion;
            institucion = servicioInstitucion.encontrarInstitucionPorId("1");
            numInstitucion = institucion.getInsCodigoRegistro();

        } catch (Exception e) {
            System.out.println(e);
        }

        Persona responsable = dataManagerSesion.getUsuarioLogeado().getPerId();
        nombresResponsable = responsable.getPerApellidoPaterno()
                + " " + responsable.getPerApellidoMaterno()
                + " " + responsable.getPerNombre();

        textoCertificado += ""
                + "<font size='-1'>"
                + crearCabeceraCertificadoSimple()
                + "<p><strong>Referencias:</strong> " + fechaActa + "-" + numeroRepertorio + "-" + numActa + "</p>"
                + "<p><strong>Predio:</strong>" + predio + "</p>"
                + "<p><strong>Catastro:</strong>" + catastro + "</p>"
                + "<p><strong>Matriculas: </strong>" + matricula + "</p><br>"
                + "<p align='justify'>El infrascrito Registrador de la Propiedad de este Cantón,"
                + " luego de revisados los índices y libros que reposan en el archivo, "
                + "en legal y debida forma certifica que:</p><br>"
                + "<p><strong>1.- DESCRIPCION DE LA PROPIEDAD:</strong> </p>"
                + "<p align='justify'>" + tipoPropiedad + " " + reemplazarPuntoComaConSalto(descripcionPropiedad)
                + ", situado en la parroquia " + parroquia
                + ", de este cantón, con matrícula número " + matricula + ".</p><br>"
                + "<p><strong>2.- PROPIETARIO(S):</strong></p>"
                + "<p align='justify'>"
                + reemplazarPuntoComaConSalto(descripcionPropietarios)
                + "</p><br>"
                + "<p><strong>3.- FORMA DE ADQUISICION Y ANTECEDENTES:</strong> </p>"
                + "<p align='justify'>" + adquisicionAntecedentes + ".</p><br>"
                + "<p><strong>4.- GRAVAMENES Y OBSERVACIONES:</strong> </p>"
                + "<p align='justify'>" + reemplazarPuntoComaConSalto(gravamen) + ".</p><br>"
                + "<p><strong>5.- OBSERVACIONES GENERALES:</strong> </p>"
                + "<p align='justify'>" + observacion + ".</p><br>"
                + "<p><strong>Responsable:</strong> " + nombresResponsable + "</p>"
                + "<p><br></p>"
                + "<p><strong> </strong></p>"
                + "<p><strong> EL REGISTRADOR</strong></p>"
                + "<p><strong> </strong>&nbsp;</p>"
                + "<p>C" + numInstitucion + "</p>"
                + "</font>";

        String urlImagenEspacioEnBlanco = "";
        if (agregarImagenEspacioEnBlanco) {
            try {
                urlImagenEspacioEnBlanco = servicioConfigDetalle.buscarPorConfig("IMAGENBLANCO").getConfigDetalleTexto();
            } catch (ServicioExcepcion ex) {
                Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
            }
            textoCertificado += "<img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }
        try {
            generarCertificadoPdf();
        } catch (ServicioExcepcion | IOException ex) {
            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarCertificadoMatriculaSimple() {
        String fechaActa = "";
        String tipoPropiedad = "";
        String parroquia = "";
        String matricula = "";
        String observacion = "";
        String numCertificado = "";
        String predio = "";
        String catastro = "";
        String numActa = "";
        String numInstitucion = "";
        String numeroRepertorio = "";
        String nombresResponsable = "";
//        descripcionPropiedad = "";
        textoCertificado = "";
//        descripcionPropietarios = "";

        //CERTIFICADO
        numCertificado = generarNumeroCertificado();

        //PROPIEDAD
        if (propiedadSeleccionada != null) {
//            descripcionPropiedad = "";
            tipoPropiedad += propiedadSeleccionada.getTpdId().getTpdNombre();
            parroquia += propiedadSeleccionada.getPrdTdtParNombre();
            matricula += propiedadSeleccionada.getPrdMatricula().toString();
            predio += propiedadSeleccionada.getPrdPredio();
            catastro += propiedadSeleccionada.getPrdCatastro();
//            descripcionPropiedad += propiedadSeleccionada.getPrdDescripcion2() + " " + propiedadSeleccionada.getPrdDescripcion1()
//                    + "de la Propiedad con matrícula " + propiedadSeleccionada.getPrdMatricula() + "; " + "\n\n";
//            if (listaPropiedadSelec != null && !listaPropiedadSelec.isEmpty()) {
//                System.out.println("Agregando propiedades seleccionadas en dlgderacc");
//                for (PropiedadDetalle prpdDtll : listaPropiedadSelec) {
//                    Persona persona = servicioPersona.find(new Persona(), prpdDtll.getPdtPerId().longValue());
//                    descripcionPropiedad += prpdDtll.getPdtDescripcion() + " de la Propiedad con matrícula "
//                            + prpdDtll.getPdtPrdMatricula() + " a la persona " + persona.getPerApellidoPaterno().trim()
//                            + " " + persona.getPerApellidoMaterno().trim() + " " + persona.getPerNombre().trim() + "; " + "\n\n";
//                }
//            }

        }
//PROPIETARIOS
//        if (listaPropietariosSeleccionados != null && !listaPropietariosSeleccionados.isEmpty()) {
//            descripcionPropietarios = "";
//            for (Propietario prop : listaPropietariosSeleccionados) {
//                Persona persona = prop.getPerId();
//                descripcionPropietarios += persona.getPerApellidoPaterno().trim()
//                        + " " + persona.getPerApellidoMaterno().trim()
//                        + " " + persona.getPerNombre().trim() + " con cédula de identidad"
//                        + " " + persona.getPerIdentificacion().trim() + " ";
//                if (persona.getPerIdConyuge() != null && persona.getPerIdConyuge().getPerId() != 0) {
//                    descripcionPropietarios += " casado con"
//                            + " " + persona.getPerIdConyuge().getPerApellidoPaterno().trim()
//                            + " " + persona.getPerIdConyuge().getPerApellidoMaterno().trim()
//                            + " " + persona.getPerIdConyuge().getPerNombre().trim() + " con cédula de identidad"
//                            + " " + persona.getPerIdConyuge().getPerIdentificacion().trim() + "\n\n";
//                }
//
//            }
//        }
//ADQUISICION ANTECEDENTES ACTA
        if (actaSeleccionada != null) {
            //ACTA
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            fechaActa = format.format(actaSeleccionada.getActFch());
            numActa = actaSeleccionada.getActNumero().toString();
            //ANTECEDENTES
            if (adquisicionAntecedentes.equalsIgnoreCase("Ninguno") || adquisicionAntecedentes.equalsIgnoreCase("Ninguna")) {
                adquisicionAntecedentes = "";
                if (actaSeleccionada.getActDescripcion3() == null) {
                    adquisicionAntecedentes = "Ninguna";
                } else {
                    adquisicionAntecedentes = removerHTML(actaSeleccionada.getActDescripcion3()) + "; " + "\n\n";
                }
            }
            //repertorio
            numeroRepertorio = actaSeleccionada.getRepNumero().getRepNumero().toString();

        }
        //caso especial, se reemplaza la adquisicion con las actas seleccionadas en el dialogo que se abre despues de seleccionar el tab

//else {
//            adquisicionAntecedentes = certificadoSeleccionado.getCerAdquisicion();
//        }
//GRAVAMENES
//        if (listaGravamenesSelec != null && !listaGravamenesSelec.isEmpty()) {
//            gravamen = "";
//            String fecha = "";
//            Acta acta = new Acta();
//            for (Gravamen grav : listaGravamenesSelec) {
////                gravamen += grav.getGraDescripcion();
////                if (listaGravamenesSelec.indexOf(grav) + 1 < listaGravamenesSelec.size()) {
////                    gravamen += ", ";
////                }
//                List<TramiteDetalle> listTramDet = new ArrayList<>();
//                listTramDet.clear();
//
//                try {
//                    listTramDet = tramiteDetalleDao.listarRepertorio(grav.getRepNumero().getRepNumero().intValue());
//                } catch (ServicioExcepcion ex) {
//                    Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//                String nombreComparecientes = "";
//                for (TramiteDetalle tramiteDetalle : listTramDet) {
//
//                    if (tramiteDetalle.getTtcId().getTtcPropietario().equals("S")) {
//                        String apMaterno = "";
//                        if (tramiteDetalle.getPerId().getPerApellidoMaterno() != null) {
//                            apMaterno = tramiteDetalle.getPerId().getPerApellidoMaterno();
//                        } else {
//                            apMaterno = "";
//                        }
//                        nombreComparecientes = tramiteDetalle.getPerId().getPerNombre().trim() + " "
//                                + tramiteDetalle.getPerId().getPerApellidoPaterno().trim() + " "
//                                + apMaterno.trim() + ", " + tramiteDetalle.getTdtTpcDescripcion() + "<br></br>";
//                    }
//                }
//                try {
//                    acta = servicioActa.buscarActaPorNumRepertorio(grav.getRepNumero().getRepNumero());
//                } catch (ServicioExcepcion ex) {
//                    Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                gravamen = gravamen + "Matrícula: " + grav.getPrdMatricula().getPrdMatricula()
//                        + "; Predio: " + grav.getPrdMatricula().getPrdPredio()
//                        + "; Catastro: " + grav.getPrdMatricula().getPrdCatastro()
//                        + "; " + grav.getRepNumero().getRepTtrDescripcion().trim()
//                        + "; " + fechasUtil.convertirFecha_A_letras(grav.getRepNumero().getRepFHR()).trim()
//                        + "; Rep: " + grav.getRepNumero().getRepNumero()
//                        + "; Nro. Inscripción: " + acta.getActInscripcion() + ".<br></br>"
//                        + "Comparecientes: <br></br>"
//                        //                                    + gravamenDetalle.getPerId().getPerNombre().trim() + " "
//                        //                                    + gravamenDetalle.getPerId().getPerApellidoPaterno().trim() + " "
//                        //                                    + apMaterno.trim()
//                        + nombreComparecientes;
//                gravamen = removerHTML(gravamen);
//            }
//        }
        //OBSERVACIONES
        if (certificadoSeleccionado.getCerObservacion() != null) {
            observacion = "";
            observacion += certificadoSeleccionado.getCerObservacion();
        }
        try {
            Institucion institucion;
            institucion = servicioInstitucion.encontrarInstitucionPorId("1");
            numInstitucion = institucion.getInsCodigoRegistro();

        } catch (Exception e) {
            System.out.println(e);
        }

        Persona responsable = dataManagerSesion.getUsuarioLogeado().getPerId();
        nombresResponsable = responsable.getPerApellidoPaterno()
                + " " + responsable.getPerApellidoMaterno()
                + " " + responsable.getPerNombre();

        textoCertificado += ""
                + "<font size='-1'>"
                + crearCabeceraCertificadoSimple()
                + "<p><strong>Referencias:</strong> " + fechaActa + "-" + numeroRepertorio + "-" + numActa + "</p>"
                + "<p><strong>Predio:</strong>" + predio + "</p>"
                + "<p><strong>Catastro:</strong>" + catastro + "</p>"
                + "<p><strong>Matriculas: </strong>" + matricula + "</p><br>"
                + "<p align='justify'>El infrascrito Registrador de la Propiedad de este Cantón,"
                + " luego de revisados los índices y libros que reposan en el archivo, "
                + "en legal y debida forma certifica que:</p><br>"
                + "<p><strong>1.- DESCRIPCION DE LA PROPIEDAD:</strong> </p>"
                + "<p align='justify'>" + tipoPropiedad + " " + reemplazarPuntoComaConSalto(descripcionPropiedad)
                + ", situado en la parroquia " + parroquia
                + ", de este cantón, con matrícula número " + matricula + ".</p><br>"
                + "<p><strong>2.- PROPIETARIO(S):</strong></p>"
                + "<p align='justify'>"
                + reemplazarPuntoComaConSalto(descripcionPropietarios)
                + "</p><br>"
                + "<p><strong>3.- FORMA DE ADQUISICION Y ANTECEDENTES:</strong> </p>"
                + "<p align='justify'>" + adquisicionAntecedentes + ".</p><br>"
                + "<p><strong>4.- GRAVAMENES Y OBSERVACIONES:</strong> </p>"
                + "<p align='justify'>" + reemplazarPuntoComaConSalto(gravamen) + ".</p><br>"
                + "<p><strong>5.- OBSERVACIONES GENERALES:</strong> </p>"
                + "<p align='justify'>" + observacion + ".</p><br>"
                + "<p><strong>Responsable:</strong> " + nombresResponsable + "</p>"
                + "<p><br></p>"
                + "<p><strong> </strong></p>"
                + "<p><strong> EL REGISTRADOR</strong></p>"
                + "<p><strong> </strong>&nbsp;</p>"
                + "<p>C" + numInstitucion + "</p>"
                + "</font>";

        String urlImagenEspacioEnBlanco = "";
        if (agregarImagenEspacioEnBlanco) {
            try {
                urlImagenEspacioEnBlanco = servicioConfigDetalle.buscarPorConfig("IMAGENBLANCO").getConfigDetalleTexto();
            } catch (ServicioExcepcion ex) {
                Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
            }
            textoCertificado += "<img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }
        try {
            generarCertificadoPdf();
        } catch (ServicioExcepcion | IOException ex) {
            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarCertificadoPdf() throws IOException, ServicioExcepcion {
        pdfTempUtil.crearPDFconTextoHTML("cert1.pdf", textoCertificado, "CER");
        urlCertificadoPdf = pdfTempUtil.generarLinksAccesoAlPdf();
        System.out.println("url: " + pdfTempUtil.getUrl());
        System.out.println("direccionRecurso: " + pdfTempUtil.getDirArchivoComoRecurso());

//        urlCertificadoPdf = pdfTempUtil.getDirArchivoComoRecurso();
    }

    public void llenarAdquisicionDerAcc() {
        if (listaActaSeleccionadas2 != null && !listaActaSeleccionadas2.isEmpty()) {
            adquisicionAntecedentes = "";
            for (Acta acta : listaActaSeleccionadas2) {
                adquisicionAntecedentes += acta.getActDescripcion3() + "; " + "\n\n";
            }
        }
        generarCertificadoSimple();
    }

    //    public Long generarSecuencia() throws ServicioExcepcion {
    //        //obtengo la secuencia
    //        secuenciaControlador.generarSecuencia("CER");
    //        //obtengo el siguiente valor de la secuencia
    //        setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
    //        int auxSecuencia = getSecuencia().getSecActual();
    //        getSecuencia().setSecActual(auxSecuencia + 1);
    ////                System.out.print("Secuencia-- " + auxSecuencia);
    //        Long idGenerado = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
    ////        setMatricula(idGenerado);
    //        secuenciaServicio.guardarCertificado(getSecuencia());
    //        return idGenerado;
    //    }
    public String darFormatoANumero(int cantidadCeros, int numero) {
        return String.format("%0" + cantidadCeros + "d", numero);
    }

    public short obtenerNumeroSecuencia() {
        short secuencia = servicioCertificado.obtenerSecuencialSiguiente(facturaSeleccionada.getFacId());
//        System.out.println("secuencia: " + secuencia);

        return secuencia;
    }

    public String generarNumeroCertificado() {

        String secuenciaFormateada = darFormatoANumero(3, obtenerNumeroSecuencia());

        String numCertificado = "C" + facturaSeleccionada.getFacNumero() + secuenciaFormateada;
        return numCertificado;
    }

//    public void guardarCertificado() throws ServicioExcepcion {
//        if (esNuevo) {
//            if (estanLlenosCamposRequeridosCertificado()) {
//                JsfUtil.addSuccessMessage("certificado guardado");
//            }
//        }
//    }
    public void cargarFormulario() throws ServicioExcepcion {
        if (facturaSeleccionada != null) {
            if (facturaSeleccionada.getFacWeb()) //es web
            {

            } else { // no es web
                FormularioDigital formDig = servicioFormularioDigital.PorNumeroFactura(facturaSeleccionada.getFacNumero());
                abrirArchivoComoRecurso(formDig);
            }
        }
    }

    public void abrirArchivoComoRecurso(FormularioDigital formularioDigitalSeleccionado) {
        //este metodo carga la direccion del archivo en el disco duro, genera un inputstream del archivo
        //copia su contenido a un archivo temporal creado en la carpeta web en la que está corriendo el programa
        //y finalmente llena la direccion de este archivo temporal para ser accedido como un recurso
        String dirPrincipal = "";
        String subDirectorio = "formulario\\";
//        String url = "";
        String nombreArchivo = "";
        String contentType = "application/pdf";
        String extension = "pdf";
        path = "";
        FacesContext fc = FacesContext.getCurrentInstance();
//
        ExternalContext ec = fc.getExternalContext();

        try {
            //OBTENER DIRECCION 
            ParametroPath parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            dirPrincipal = parametroPath.getPrpPath();
            nombreArchivo = formularioDigitalSeleccionado.getFmdNombreArchivo();
            subDirectorio = formularioDigitalSeleccionado.getFmdPath();
            url = "file:///" + dirPrincipal + subDirectorio + nombreArchivo + "." + extension;
            path = dirPrincipal + subDirectorio + nombreArchivo + "." + extension;

            Path file1 = Paths.get(path);
            Path file2;
            String dirRealAplicacion = ec.getRealPath("/");
            String dirContexto = ec.getRequestContextPath();
            //Crear archivo y copiar
            try (InputStream input = new BufferedInputStream(Files.newInputStream(file1))) {
                Path direccion = Paths.get(dirRealAplicacion + "temp/");
                file2 = Files.createTempFile(direccion, "asd", ".pdf");
                Files.copy(input, file2, StandardCopyOption.REPLACE_EXISTING);
                url = "/" + "temp" + "/" + file2.getFileName().toString();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al subir archivo");
                e.printStackTrace(System.out);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al cargar el archivo");
        }
    }

    public void guardarCertificadoGravamenSimple() {
        if (esNuevo) {

            if (estanLlenosCamposRequeridosCertificado()) {

                try {
                    String numCertificado = generarNumeroCertificado();

                    short secuencial = obtenerNumeroSecuencia();
                    String cerNumero = numCertificado;
                    //CERTIFICADO
                    certificadoSeleccionado.setFacId(certificadoCargaSeleccionado.getFacId());
//            TipoCertificado tipoCertificado = servicioTipoCertificado.find(new TipoCertificado(), idTipC);
                    certificadoSeleccionado.setTroId(tipoCertificadoSeleccionado);
                    certificadoSeleccionado.setCerFechaIngreso(Calendar.getInstance().getTime());
                    if (propiedadSeleccionada != null && (propiedadSeleccionada.getPrdMatricula() != null
                            && !propiedadSeleccionada.getPrdMatricula().isEmpty())) {
                        certificadoSeleccionado.setCerPredio(propiedadSeleccionada.getPrdPredio());
                    } else {
                        certificadoSeleccionado.setCerPredio(" ");
                    }
                    certificadoSeleccionado.setCerPropiedad(descripcionPropiedad);
                    if (propiedadSeleccionada != null && (propiedadSeleccionada.getPrdCatastro() != null
                            && !propiedadSeleccionada.getPrdCatastro().isEmpty())) {
                        certificadoSeleccionado.setCerCatastro(propiedadSeleccionada.getPrdCatastro());
                    } else {
                        certificadoSeleccionado.setCerCatastro(" ");
                    }
//                certificadoSeleccionado.setCerCuantia(servicioCertificado.obtenerCuantiaPor_Propiedad(propiedadSeleccionada.getPrdMatricula()));
                    certificadoSeleccionado.setCerCuantia(BigDecimal.ZERO);
                    certificadoSeleccionado.setCerAdquisicion(adquisicionAntecedentes);
                    certificadoSeleccionado.setCerCertificado(textoCertificado);
                    certificadoSeleccionado.setCerPropietario(descripcionPropietarios);
                    certificadoSeleccionado.setCerEstado("A");
                    certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());
                    certificadoSeleccionado.setCerGravamen(gravamen);
                    certificadoSeleccionado.setCertificadoPK(new CertificadoPK(cerNumero, secuencial));

                    List<Propiedad> listaPropiedadesSelec = new ArrayList<>();
                    if (propiedadSeleccionada != null && propiedadSeleccionada.getPrdMatricula() != null) {
                        listaPropiedadesSelec.add(propiedadSeleccionada);
                        certificadoSeleccionado.setPropiedadList(listaPropiedadesSelec);
                    }
                    if (listaGravamenesSelec != null && !listaGravamenesSelec.isEmpty()) {
                        certificadoSeleccionado.setGravamenList(listaGravamenesSelec);
                    }
                    if (listaPropietariosSeleccionados != null && !listaPropietariosSeleccionados.isEmpty()) {
                        certificadoSeleccionado.setPropietarioList(listaPropietariosSeleccionados);
                    }
//                    certificadoSeleccionado.setActaList(listaActasSeleccionadas);
                    try {
                        certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
                    } catch (ParseException ex) {
                        Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    servicioCertificado.create(certificadoSeleccionado);
                    certificadoSeleccionado = servicioCertificado.find(new Certificado(), certificadoSeleccionado.getCertificadoPK());
                    if (certificadoSeleccionado != null) {
                        //CERTIFICADO CARGA
                        certificadoCargaSeleccionado.setCdcCertificado(numCertificado);
                        certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");

                        servicioCertificadoCarga.edit(certificadoCargaSeleccionado);

                        //GENERO LOG CERTIFICADO ACCION
                        generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "CREACION DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        limpiarPanelDerecho();
                        ocultarPanelDerecho();

                        try {
                            llenarTablaCertificadoCarga();
                        } catch (ParseException ex) {
                            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JsfUtil.addErrorMessage("Error al generar el certificado nuevo");
                    }

                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error al generar el certificado nuevo");
                    e.printStackTrace(System.out);
                }
            }
        } else {
            try {

//            certificadoSeleccionado.setFacId(certificadoCargaSeleccionado.getFacId());
//            TipoCertificado tipoCertificado = servicioTipoCertificado.find(new TipoCertificado(), idTipC);
//            certificadoSeleccionado.setTroId(tipoCertificado);
//            certificadoSeleccionado.setCerFechaIngreso(Calendar.getInstance().getTime());
//            certificadoSeleccionado.setCerPredio(propiedadSeleccionada.getPrdPredio());
                certificadoSeleccionado.setCerPropiedad(descripcionPropiedad);

//            certificadoSeleccionado.setCerCatastro(propiedadSeleccionada.getPrdCatastro());
//            certificadoSeleccionado.setCerCuantia(servicioCertificado.obtenerCuantiaPor_Propiedad(propiedadSeleccionada.getPrdMatricula()));
                certificadoSeleccionado.setCerAdquisicion(adquisicionAntecedentes);

                certificadoSeleccionado.setCerCertificado(textoCertificado);

//            certificadoSeleccionado.setCerPropietario(descripcionPropietarios);
//            certificadoSeleccionado.setCerEstado("A");
                certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());
//            certificadoSeleccionado.setCerGravamen(gravamen);
//            certificadoSeleccionado.setCertificadoPK(new CertificadoPK(cerNumero, secuencial));

//            List<Propiedad> listaPropiedadesSelec = new ArrayList<>();
//            listaPropiedadesSelec.add(propiedadSeleccionada);
//            certificadoSeleccionado.setPropiedadList(listaPropiedadesSelec);
//            certificadoSeleccionado.setGravamenList(listaGravamenesSelec);
//            certificadoSeleccionado.setActaList(listaActasSeleccionadas);
//            certificadoSeleccionado.setPropietarioList(listaPropietariosSeleccionados);
                switch (tipoTrabajo) {
                    case "A":
                        break;
                    case "H":
                        //CERTIFICADO AUDITORIA
                        Certificado certificadoPrevioEdicion = servicioCertificado.find(new Certificado(),
                                certificadoSeleccionado.getCertificadoPK()); //certificado con valores antes de guardar la actualizacion
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "PROPIEDAD", certificadoPrevioEdicion.getCerPropiedad(),
                                certificadoSeleccionado.getCerPropiedad());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "CERTIFICADO", certificadoPrevioEdicion.getCerCertificado(),
                                certificadoSeleccionado.getCerCertificado());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "ADQUISICION", certificadoPrevioEdicion.getCerAdquisicion(),
                                certificadoSeleccionado.getCerAdquisicion());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "OBSERVACION", certificadoPrevioEdicion.getCerObservacion(),
                                certificadoSeleccionado.getCerObservacion());

                        //CERTIFICADO CARGA
//            certificadoCargaSeleccionado.setCdcCertificado(numCertificado);
                        certificadoCargaSeleccionado.setCdcActivo(Boolean.FALSE);
                        certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");
                        try {
                            certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
                        } catch (ParseException ex) {
                            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        servicioCertificadoCarga.edit(certificadoCargaSeleccionado);
                        break;

                }
                //**CERTIFICADO ACTUALIZACION**
                if (certificadoSeleccionado != null) {
                    servicioCertificado.edit(certificadoSeleccionado);
                    try {
                        llenarTablaCertificadoCarga();
                    } catch (ParseException ex) {
                        Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //GENERO LOG CERTIFICADO ACCION
                    generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "ACTUALIZACIÓN DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    limpiarPanelDerecho();
                    ocultarPanelDerecho();
                } else {
                    JsfUtil.addErrorMessage("Error al actualizar el certificado.");
                }

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar el certificado.");
                e.printStackTrace(System.out);
            }
        }
    }

    public void guardarCertificadoMatriculaSimple() {
        if (esNuevo) {

            if (estanLlenosCamposRequeridosCertificado()) {

                try {
                    String numCertificado = generarNumeroCertificado();

                    short secuencial = obtenerNumeroSecuencia();
                    String cerNumero = numCertificado;
                    //CERTIFICADO
                    certificadoSeleccionado.setFacId(certificadoCargaSeleccionado.getFacId());
//            TipoCertificado tipoCertificado = servicioTipoCertificado.find(new TipoCertificado(), idTipC);
                    certificadoSeleccionado.setTroId(tipoCertificadoSeleccionado);
                    certificadoSeleccionado.setCerFechaIngreso(Calendar.getInstance().getTime());
                    if (propiedadSeleccionada != null && (propiedadSeleccionada.getPrdMatricula() != null
                            && !propiedadSeleccionada.getPrdMatricula().isEmpty())) {
                        certificadoSeleccionado.setCerPredio(propiedadSeleccionada.getPrdPredio());
                    } else {
                        certificadoSeleccionado.setCerPredio(" ");
                    }
                    certificadoSeleccionado.setCerPropiedad(descripcionPropiedad);
                    if (propiedadSeleccionada != null && (propiedadSeleccionada.getPrdCatastro() != null
                            && !propiedadSeleccionada.getPrdCatastro().isEmpty())) {
                        certificadoSeleccionado.setCerCatastro(propiedadSeleccionada.getPrdCatastro());
                    } else {
                        certificadoSeleccionado.setCerCatastro(" ");
                    }
//                certificadoSeleccionado.setCerCuantia(servicioCertificado.obtenerCuantiaPor_Propiedad(propiedadSeleccionada.getPrdMatricula()));
                    certificadoSeleccionado.setCerCuantia(BigDecimal.ZERO);
                    certificadoSeleccionado.setCerAdquisicion(adquisicionAntecedentes);
                    certificadoSeleccionado.setCerCertificado(textoCertificado);
                    certificadoSeleccionado.setCerPropietario(descripcionPropietarios);
                    certificadoSeleccionado.setCerEstado("A");
                    certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());
                    certificadoSeleccionado.setCerGravamen(gravamen);
                    certificadoSeleccionado.setCertificadoPK(new CertificadoPK(cerNumero, secuencial));

                    List<Propiedad> listaPropiedadesSelec = new ArrayList<>();
                    if (propiedadSeleccionada != null && propiedadSeleccionada.getPrdMatricula() != null) {
                        listaPropiedadesSelec.add(propiedadSeleccionada);
                        certificadoSeleccionado.setPropiedadList(listaPropiedadesSelec);
                    }
                    if (listaGravamenesSelec != null && !listaGravamenesSelec.isEmpty()) {
                        certificadoSeleccionado.setGravamenList(listaGravamenesSelec);
                    }
                    if (listaPropietariosSeleccionados != null && !listaPropietariosSeleccionados.isEmpty()) {
                        certificadoSeleccionado.setPropietarioList(listaPropietariosSeleccionados);
                    }
//                    certificadoSeleccionado.setActaList(listaActasSeleccionadas);
                    try {
                        certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
                    } catch (ParseException ex) {
                        Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    servicioCertificado.create(certificadoSeleccionado);
                    certificadoSeleccionado = servicioCertificado.find(new Certificado(), certificadoSeleccionado.getCertificadoPK());
                    if (certificadoSeleccionado != null) {
                        //CERTIFICADO CARGA
                        certificadoCargaSeleccionado.setCdcCertificado(numCertificado);
                        certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");

                        servicioCertificadoCarga.edit(certificadoCargaSeleccionado);

                        //GENERO LOG CERTIFICADO ACCION
                        generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "CREACION DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        limpiarPanelDerecho();
                        ocultarPanelDerecho();

                        try {
                            llenarTablaCertificadoCarga();
                        } catch (ParseException ex) {
                            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JsfUtil.addErrorMessage("Error al generar el certificado nuevo");
                    }

                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error al generar el certificado nuevo");
                    e.printStackTrace(System.out);
                }
            }
        } else {
            try {

//            certificadoSeleccionado.setFacId(certificadoCargaSeleccionado.getFacId());
//            TipoCertificado tipoCertificado = servicioTipoCertificado.find(new TipoCertificado(), idTipC);
//            certificadoSeleccionado.setTroId(tipoCertificado);
//            certificadoSeleccionado.setCerFechaIngreso(Calendar.getInstance().getTime());
//            certificadoSeleccionado.setCerPredio(propiedadSeleccionada.getPrdPredio());
                certificadoSeleccionado.setCerPropiedad(descripcionPropiedad);

//            certificadoSeleccionado.setCerCatastro(propiedadSeleccionada.getPrdCatastro());
//            certificadoSeleccionado.setCerCuantia(servicioCertificado.obtenerCuantiaPor_Propiedad(propiedadSeleccionada.getPrdMatricula()));
                certificadoSeleccionado.setCerAdquisicion(adquisicionAntecedentes);

                certificadoSeleccionado.setCerCertificado(textoCertificado);

//            certificadoSeleccionado.setCerPropietario(descripcionPropietarios);
//            certificadoSeleccionado.setCerEstado("A");
                certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());
//            certificadoSeleccionado.setCerGravamen(gravamen);
//            certificadoSeleccionado.setCertificadoPK(new CertificadoPK(cerNumero, secuencial));

//            List<Propiedad> listaPropiedadesSelec = new ArrayList<>();
//            listaPropiedadesSelec.add(propiedadSeleccionada);
//            certificadoSeleccionado.setPropiedadList(listaPropiedadesSelec);
//            certificadoSeleccionado.setGravamenList(listaGravamenesSelec);
//            certificadoSeleccionado.setActaList(listaActasSeleccionadas);
//            certificadoSeleccionado.setPropietarioList(listaPropietariosSeleccionados);
                switch (tipoTrabajo) {
                    case "A":
                        break;
                    case "H":
                        //CERTIFICADO AUDITORIA
                        Certificado certificadoPrevioEdicion = servicioCertificado.find(new Certificado(),
                                certificadoSeleccionado.getCertificadoPK()); //certificado con valores antes de guardar la actualizacion
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "PROPIEDAD", certificadoPrevioEdicion.getCerPropiedad(),
                                certificadoSeleccionado.getCerPropiedad());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "CERTIFICADO", certificadoPrevioEdicion.getCerCertificado(),
                                certificadoSeleccionado.getCerCertificado());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "ADQUISICION", certificadoPrevioEdicion.getCerAdquisicion(),
                                certificadoSeleccionado.getCerAdquisicion());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "OBSERVACION", certificadoPrevioEdicion.getCerObservacion(),
                                certificadoSeleccionado.getCerObservacion());

                        //CERTIFICADO CARGA
//            certificadoCargaSeleccionado.setCdcCertificado(numCertificado);
                        certificadoCargaSeleccionado.setCdcActivo(Boolean.FALSE);
                        certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");
                        try {
                            certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
                        } catch (ParseException ex) {
                            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        servicioCertificadoCarga.edit(certificadoCargaSeleccionado);
                        break;

                }
                //**CERTIFICADO ACTUALIZACION**
                if (certificadoSeleccionado != null) {
                    servicioCertificado.edit(certificadoSeleccionado);
                    try {
                        llenarTablaCertificadoCarga();
                    } catch (ParseException ex) {
                        Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //GENERO LOG CERTIFICADO ACCION
                    generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "ACTUALIZACIÓN DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    limpiarPanelDerecho();
                    ocultarPanelDerecho();
                } else {
                    JsfUtil.addErrorMessage("Error al actualizar el certificado.");
                }

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar el certificado.");
                e.printStackTrace(System.out);
            }
        }
    }

    public void guardarCertificadoBusqueda() {
        if (esNuevo) {

//            if (estanLlenosCamposRequeridosCertificado()) {
            try {
                String numCertificado = generarNumeroCertificado();

                short secuencial = obtenerNumeroSecuencia();
                String cerNumero = numCertificado;
                //CERTIFICADO
                certificadoSeleccionado.setFacId(certificadoCargaSeleccionado.getFacId());
//            TipoCertificado tipoCertificado = servicioTipoCertificado.find(new TipoCertificado(), idTipC);
                certificadoSeleccionado.setTroId(tipoCertificadoSeleccionado);
                certificadoSeleccionado.setCerFechaIngreso(Calendar.getInstance().getTime());
                certificadoSeleccionado.setCerPredio(propiedadSeleccionada.getPrdPredio());
                certificadoSeleccionado.setCerPropiedad(descripcionPropiedad);
                certificadoSeleccionado.setCerCatastro(propiedadSeleccionada.getPrdCatastro());
//                certificadoSeleccionado.setCerCuantia(servicioCertificado.obtenerCuantiaPor_Propiedad(propiedadSeleccionada.getPrdMatricula()));
                certificadoSeleccionado.setCerCuantia(BigDecimal.ZERO);
                certificadoSeleccionado.setCerAdquisicion(adquisicionAntecedentes);
                certificadoSeleccionado.setCerCertificado(textoCertificado);
                certificadoSeleccionado.setCerPropietario(descripcionPropietarios);
                certificadoSeleccionado.setCerEstado("A");
                certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());
                certificadoSeleccionado.setCerGravamen(gravamen);
                certificadoSeleccionado.setCertificadoPK(new CertificadoPK(cerNumero, secuencial));

                List<Propiedad> listaPropiedadesSelec = new ArrayList<>();
                listaPropiedadesSelec.add(propiedadSeleccionada);
                certificadoSeleccionado.setPropiedadList(listaPropiedadesSelec);
                certificadoSeleccionado.setGravamenList(listaGravamenesSelec);
//                    certificadoSeleccionado.setActaList(listaActasSeleccionadas);
                certificadoSeleccionado.setPropietarioList(listaPropietariosSeleccionados);
                try {
                    certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
                } catch (ParseException ex) {
                    Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                }

                servicioCertificado.create(certificadoSeleccionado);
                certificadoSeleccionado = servicioCertificado.find(new Certificado(), certificadoSeleccionado.getCertificadoPK());
                if (certificadoSeleccionado != null) {
                    //CERTIFICADO CARGA
                    certificadoCargaSeleccionado.setCdcCertificado(numCertificado);
                    certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");

                    servicioCertificadoCarga.edit(certificadoCargaSeleccionado);

                    //GENERO LOG CERTIFICADO ACCION
                    generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "CREACION DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    limpiarPanelDerecho();
                    ocultarPanelDerecho();

                    try {
                        llenarTablaCertificadoCarga();
                    } catch (ParseException ex) {
                        Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JsfUtil.addErrorMessage("Error al generar el certificado nuevo");
                }

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al generar el certificado nuevo");
                e.printStackTrace(System.out);
            }
//            }
        } else {
            try {

                certificadoSeleccionado.setCerPropiedad(descripcionPropiedad);

                certificadoSeleccionado.setCerAdquisicion(adquisicionAntecedentes);

                certificadoSeleccionado.setCerCertificado(textoCertificado);

                certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());
                switch (tipoTrabajo) {
                    case "A":
                        break;
                    case "H":
                        //CERTIFICADO AUDITORIA
                        Certificado certificadoPrevioEdicion = servicioCertificado.find(new Certificado(),
                                certificadoSeleccionado.getCertificadoPK()); //certificado con valores antes de guardar la actualizacion
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "PROPIEDAD", certificadoPrevioEdicion.getCerPropiedad(),
                                certificadoSeleccionado.getCerPropiedad());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "CERTIFICADO", certificadoPrevioEdicion.getCerCertificado(),
                                certificadoSeleccionado.getCerCertificado());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "ADQUISICION", certificadoPrevioEdicion.getCerAdquisicion(),
                                certificadoSeleccionado.getCerAdquisicion());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "OBSERVACION", certificadoPrevioEdicion.getCerObservacion(),
                                certificadoSeleccionado.getCerObservacion());

                        //CERTIFICADO CARGA
//            certificadoCargaSeleccionado.setCdcCertificado(numCertificado);
                        certificadoCargaSeleccionado.setCdcActivo(Boolean.FALSE);
                        certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");
                        try {
                            certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
                        } catch (ParseException ex) {
                            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        servicioCertificadoCarga.edit(certificadoCargaSeleccionado);
                        break;

                }
                //**CERTIFICADO ACTUALIZACION**
                if (certificadoSeleccionado != null) {
                    servicioCertificado.edit(certificadoSeleccionado);
                    try {
                        llenarTablaCertificadoCarga();
                    } catch (ParseException ex) {
                        Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //GENERO LOG CERTIFICADO ACCION
                    generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "ACTUALIZACIÓN DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    limpiarPanelDerecho();
                    ocultarPanelDerecho();

                } else {
                    JsfUtil.addErrorMessage("Error al actualizar el certificado.");
                }

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar el certificado.");
                e.printStackTrace(System.out);
            }
        }
    }

    public void guardarCertificadoBienesRaices() {
        if (esNuevo) {

            if (estanLlenosCamposRequeridosCertificado()) {

                try {
                    String numCertificado = generarNumeroCertificado();

                    short secuencial = obtenerNumeroSecuencia();
                    String cerNumero = numCertificado;
                    //CERTIFICADO
                    certificadoSeleccionado.setFacId(certificadoCargaSeleccionado.getFacId());
//            TipoCertificado tipoCertificado = servicioTipoCertificado.find(new TipoCertificado(), idTipC);
                    certificadoSeleccionado.setTroId(tipoCertificadoSeleccionado);
                    certificadoSeleccionado.setCerFechaIngreso(Calendar.getInstance().getTime());
                    certificadoSeleccionado.setCerPredio(propiedadSeleccionada.getPrdPredio());
                    certificadoSeleccionado.setCerPropiedad(descripcionPropiedad);
                    certificadoSeleccionado.setCerCatastro(propiedadSeleccionada.getPrdCatastro());
//                certificadoSeleccionado.setCerCuantia(servicioCertificado.obtenerCuantiaPor_Propiedad(propiedadSeleccionada.getPrdMatricula()));
                    certificadoSeleccionado.setCerCuantia(BigDecimal.ZERO);
                    certificadoSeleccionado.setCerAdquisicion(adquisicionAntecedentes);
                    certificadoSeleccionado.setCerCertificado(textoCertificado);
                    certificadoSeleccionado.setCerPropietario(descripcionPropietarios);
                    certificadoSeleccionado.setCerEstado("A");
                    certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());
                    certificadoSeleccionado.setCerGravamen(gravamen);
                    certificadoSeleccionado.setCertificadoPK(new CertificadoPK(cerNumero, secuencial));

                    List<Propiedad> listaPropiedadesSelec = new ArrayList<>();
                    listaPropiedadesSelec.add(propiedadSeleccionada);
                    certificadoSeleccionado.setPropiedadList(listaPropiedadesSelec);
                    certificadoSeleccionado.setGravamenList(listaGravamenesSelec);
//                    certificadoSeleccionado.setActaList(listaActasSeleccionadas);
                    certificadoSeleccionado.setPropietarioList(listaPropietariosSeleccionados);
                    try {
                        certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
                    } catch (ParseException ex) {
                        Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    servicioCertificado.create(certificadoSeleccionado);
                    certificadoSeleccionado = servicioCertificado.find(new Certificado(), certificadoSeleccionado.getCertificadoPK());
                    if (certificadoSeleccionado != null) {
                        //CERTIFICADO CARGA
                        certificadoCargaSeleccionado.setCdcCertificado(numCertificado);
                        certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");

                        servicioCertificadoCarga.edit(certificadoCargaSeleccionado);

                        //GENERO LOG CERTIFICADO ACCION
                        generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "CREACION DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        mostrarPnlTabs2 = false;
                        try {
                            llenarTablaCertificadoCarga();
                        } catch (ParseException ex) {
                            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        JsfUtil.addErrorMessage("Error al generar el certificado nuevo");
                    }

                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error al generar el certificado nuevo");
                    e.printStackTrace(System.out);
                }
            }
        } else {
            try {

                certificadoSeleccionado.setCerPropiedad(descripcionPropiedad);

                certificadoSeleccionado.setCerAdquisicion(adquisicionAntecedentes);

                certificadoSeleccionado.setCerCertificado(textoCertificado);

                certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());
                switch (tipoTrabajo) {
                    case "A":
                        break;
                    case "H":
                        //CERTIFICADO AUDITORIA
                        Certificado certificadoPrevioEdicion = servicioCertificado.find(new Certificado(),
                                certificadoSeleccionado.getCertificadoPK()); //certificado con valores antes de guardar la actualizacion
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "PROPIEDAD", certificadoPrevioEdicion.getCerPropiedad(),
                                certificadoSeleccionado.getCerPropiedad());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "CERTIFICADO", certificadoPrevioEdicion.getCerCertificado(),
                                certificadoSeleccionado.getCerCertificado());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "ADQUISICION", certificadoPrevioEdicion.getCerAdquisicion(),
                                certificadoSeleccionado.getCerAdquisicion());
                        crearCertificadoAuditoria(certificadoSeleccionado.getCertificadoPK().getCerNumero(),
                                "OBSERVACION", certificadoPrevioEdicion.getCerObservacion(),
                                certificadoSeleccionado.getCerObservacion());

                        //CERTIFICADO CARGA
//            certificadoCargaSeleccionado.setCdcCertificado(numCertificado);
                        certificadoCargaSeleccionado.setCdcActivo(Boolean.FALSE);
                        certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");
                        try {
                            certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
                        } catch (ParseException ex) {
                            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        servicioCertificadoCarga.edit(certificadoCargaSeleccionado);
                        break;

                }
                //**CERTIFICADO ACTUALIZACION**
                if (certificadoSeleccionado != null) {
                    servicioCertificado.edit(certificadoSeleccionado);
                    try {
                        llenarTablaCertificadoCarga();
                    } catch (ParseException ex) {
                        Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    //GENERO LOG CERTIFICADO ACCION
                    generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "ACTUALIZACIÓN DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    limpiarPanelDerecho();
                    ocultarPanelDerecho();
                    mostrarPnlTabs2 = false;
                } else {
                    JsfUtil.addErrorMessage("Error al actualizar el certificado.");
                }

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar el certificado.");
                e.printStackTrace(System.out);
            }
        }
    }

    public boolean validarCamposCertificadoBusqueda() {
        if (txtSolicitanteIdentificacion == null || txtSolicitanteIdentificacion.isEmpty()
                && txtSolicitanteNombre == null || txtSolicitanteNombre.isEmpty()) {
            JsfUtil.addErrorMessage("Error, debe llenar los campos requeridos");
            return false;
        } else {
            return true;
        }
    }

    public boolean validarCamposCertificadoBienesRaices() {
        if (txtSolicitanteIdentificacion == null || txtSolicitanteIdentificacion.isEmpty()
                && txtSolicitanteNombre == null || txtSolicitanteNombre.isEmpty()) {
            JsfUtil.addErrorMessage("Error, debe llenar los campos requeridos");
            return false;
        } else {
            return true;
        }
    }

    public boolean validarCamposCertificadoEstatuto() {
        if (txtSolicitanteIdentificacion == null || txtSolicitanteIdentificacion.isEmpty()
                && txtSolicitanteNombre == null || txtSolicitanteNombre.isEmpty()) {
            JsfUtil.addErrorMessage("Error, debe llenar los campos requeridos");
            return false;
        } else {
            return true;
        }
    }

    public void guardarCertificado() throws ServicioExcepcion {
        switch (tipoCertificadoSeleccionado.getTroTipo()) {
            case "G":
                switch (tipoGravamen) {
                    case "NEGATIVA":
                        guardarCertificadoConPlantilla();
                        break;
                    case "ESTATUTO":
                        guardarCertificadoConPlantilla();
                        break;
                    default: //GRAVAMEN
                        guardarCertificadoGravamenSimple();
                        break;
                }
                break;
            case "M":
                switch (tipoGravamen) {
                    case "NEGATIVA":
                        guardarCertificadoConPlantilla();
                        break;
                    case "ESTATUTO":
                        guardarCertificadoConPlantilla();
                        break;
                    default: //GRAVAMEN
                        guardarCertificadoMatriculaSimple();
                        break;
                }
                break;
            case "R":
                if (validarCamposCertificadoBienesRaices()) {
                    guardarCertificadoBienesRaices();
                }
                try {
                    terminarProceso();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case "B":
                if (validarCamposCertificadoBusqueda()) {
                    guardarCertificadoBusqueda();
                }
                try {
                    terminarProceso();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case "E":
                if (validarCamposCertificadoEstatuto()) {
                    guardarCertificadoBusqueda();
                }
                try {
                    terminarProceso();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case "N":
                   guardarCertificadoConPlantilla();
                try {
                    terminarProceso();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case "P":
                    guardarCertificadoConPlantilla();
                try {
                    terminarProceso();
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            default:
                break;

        }

        contabilizarCertificados();
        indiceTabViewCertificados = 0;
    }

    public void crearCertificadoAuditoria(String cerNumero, String cadAtributo, String cadAnterior, String cadActual) {
        CertificadoAuditoria nuevoCertAud = new CertificadoAuditoria(null,
                cerNumero, cadAtributo, cadAnterior, cadActual, dataManagerSesion.getUsuarioLogeado().getUsuLogin(),
                Calendar.getInstance().getTime());
        servicioCertificadoAuditoria.create(nuevoCertAud);
    }

    public void terminarProceso() throws ServicioExcepcion, ParseException, IOException {
        try {
            System.out.println("TERMINAR PROCESO");
            Boolean estanTodosTerminados = true;
            List<CertificadoCarga> listaCertCargaPorDia = new ArrayList<>();
            if (tipoCertificadoSeleccionado.getTroTipo().equalsIgnoreCase("G")||tipoCertificadoSeleccionado.getTroTipo().equalsIgnoreCase("M")) {
                listaCertCargaPorDia = servicioCertificadoCarga.listarPor_Usuario_Factura_TipoCertificado_TipoCertificadoCarga_EstadoA(
                        new BigDecimal(dataManagerSesion.getUsuarioLogeado().getUsuId()), tipoCertificadoSeleccionado.getTroId(),tipoCertificadoSeleccionado.getTroTipo(), "CER");
                System.out.println("tam lista cert carga:" + listaCertCargaPorDia.size());
                for (CertificadoCarga certCarg : listaCertCargaPorDia) {
                    if (!certCarg.getCdcEstadoProceso().equals("TERMINADO")) {
                        estanTodosTerminados = false;
                    }
                }
            } else {
                listaCertCargaPorDia.add(certificadoCargaSeleccionado);
            }

            if (estanTodosTerminados) {

                Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("IMC", "IMPRESION CERTIFICADO", listaCertCargaPorDia.size());

                for (CertificadoCarga certCarg : listaCertCargaPorDia) {
                    //ACTUALIZO TODOS LOS CERTIFICADO CARGA
                    certCarg.setCdcEstado("I");
                    servicioCertificadoCarga.edit(certCarg);
//CREO LA NUEVA ASIGNACION
                    CertificadoCarga nuevoCertSeleccionado = new CertificadoCarga();
                    nuevoCertSeleccionado = certCarg;
                    nuevoCertSeleccionado.setCdcTipo("IMC");
                    nuevoCertSeleccionado.setCdcEstado("A");
                    nuevoCertSeleccionado.setCdcUser(getDataManagerSesion().getUsuarioLogeado().getUsuLogin());
                    nuevoCertSeleccionado.setCdcFHR(Calendar.getInstance().getTime());
                    nuevoCertSeleccionado.setUsuId(usuarioAsignado);

                    servicioCertificadoCarga.create(nuevoCertSeleccionado);

                    if (certCarg.getCdcCertificado() != null) {
                        if (!certCarg.getCdcCertificado().isEmpty()) {
                            //GENERO LOG CERTIFICADO ACCION
                            String cerNumero = certCarg.getCdcCertificado();
                            short secuencial = new Short(cerNumero.substring(cerNumero.length() - 3, cerNumero.length()));
                            System.out.println("secuencial: " + secuencial);
                            Certificado cert = servicioCertificado.find(new Certificado(), new CertificadoPK(cerNumero, secuencial));
                            generarCertificadoAccion(cert.getCertificadoPK().getCerNumero(), "Paso de Certificado a Impresión", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            CertificadoAccion certAc = new CertificadoAccion();
                            certAc.setCtaNumeroDocumento(cert.getCertificadoPK().getCerNumero());
                            certAc.setCtaObservacion("Usuatrio asignado a " + usuarioAsignado.getUsuLogin());
                            certAc.setCtaEstadoProceso("CER");
                            certAc.setCtaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            certAc.setCtaFHR(Calendar.getInstance().getTime());
                            certAc.setCtaEstadoRegistro("A");
                            certAc.setCtaUsuarioAsignado(usuarioAsignado.getUsuLogin());
                            servicioCertificadoAccion.create(certAc);
                        }
                    }

                    if (!certCarg.getCdcDocumento().isEmpty()) {
                        //FACTURA
                        String facNumero = certCarg.getCdcDocumento();
                        Factura fact = servicioFactura.buscarPorNumFactura(facNumero);
                        fact.setFacEstadoCertificado("P");
                        servicioFactura.edit(fact);
                    }
                }

                for (CertificadoCarga certCarg : listaCertCargaPorDia) {
                    certCarg.setCdcActivo(Boolean.FALSE);
                    servicioCertificadoCarga.edit(certCarg);
                }

                FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_INFO, "Certificado(s) pasados a impresión", null));

                if (tipoCertificadoSeleccionado.getTroTipo().equalsIgnoreCase("G")) {
                    recargarPagina();
                } else {
                    //INICIALIZAR TABLA
                    llenarTablaCertificadoCarga();
                    limpiarPanelDerecho();
                    ocultarPanelDerecho();
                }

//        crearAsignacionSiguiente();
            } else {
                FacesContext.getCurrentInstance().addMessage("growl", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Debe Completar los Certificados Pertenecientes a la Misma Factura", null));
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

    }

    public void recargarPagina() throws IOException {
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(origRequest.getRequestURL().toString());
    }

    public void generarCertificadoAccion(String numCertificado, String observacion, String estado, String nombreUsuario) {
        CertificadoAccion certificadoAccionNuevo = new CertificadoAccion(null,
                numCertificado, estado, "A", observacion, nombreUsuario, Calendar.getInstance().getTime());
        servicioCertificadoAccion.create(certificadoAccionNuevo);
    }

    public boolean estanLlenosCamposRequeridosCertificado() {
        if (descripcionPropiedad == null || descripcionPropiedad.isEmpty()) {
            JsfUtil.addErrorMessage("Error, la descripción de propiedad está vacía");
            return false;
        }
        if (descripcionPropietarios.isEmpty() || descripcionPropietarios == null) {
            JsfUtil.addErrorMessage("Error, la descripción de propietarios está vacía");
            return false;
        }
//        if (gravamen.isEmpty() || gravamen == null) {
//            JsfUtil.addErrorMessage("Error, debe seleccionar una propiedad");
//            return false;
//        }

        if (actaSeleccionada == null) {
            if (esCertificadoVacio) {
                return true;
            } else {
                JsfUtil.addErrorMessage("Error, debe seleccionar una acta");
                return false;
            }
        }
        return true;
    }

    public void ocultarPanelDerecho() {
        mostrarPnlTabs = false;
        indiceTabViewCertificados=0;
        mostrarPnlCertPlantilla = false;
        mostrarPnlTabs2 = false;
    }

    public void guardarCertificadoConPlantilla() throws ServicioExcepcion {
        if (esNuevo) {

            String numCertificado = generarNumeroCertificado();
            short secuencial = obtenerNumeroSecuencia();
            String cerNumero = numCertificado;
            //CERTIFICADO

            certificadoSeleccionado.setFacId(certificadoCargaSeleccionado.getFacId());
//            TipoCertificado tipoCertificado = servicioTipoCertificado.find(new TipoCertificado(), idTipC);
            certificadoSeleccionado.setTroId(tipoCertificadoSeleccionado);
            certificadoSeleccionado.setCerFechaIngreso(Calendar.getInstance().getTime());
            certificadoSeleccionado.setCerPredio(" ");
            certificadoSeleccionado.setCerPropiedad(" ");
            certificadoSeleccionado.setCerCatastro(" ");
            certificadoSeleccionado.setCerCuantia(BigDecimal.ZERO);
            certificadoSeleccionado.setCerAdquisicion(" ");
            certificadoSeleccionado.setCerCertificado(textoCertificado);
            certificadoSeleccionado.setCerPropietario(" ");
            certificadoSeleccionado.setCerEstado("A");
            certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());
            certificadoSeleccionado.setCerGravamen(" ");
            certificadoSeleccionado.setCertificadoPK(new CertificadoPK(cerNumero, secuencial));
            try {
                certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
            } catch (ParseException ex) {
                Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
            }

            servicioCertificado.create(certificadoSeleccionado);
            //CERTIFICADO CARGA
            certificadoCargaSeleccionado.setCdcCertificado(numCertificado);
            certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");
            certificadoCargaSeleccionado.setCdcTipoGravamen(tipoGravamen);
            servicioCertificadoCarga.edit(certificadoCargaSeleccionado);

            //GENERO LOG CERTIFICADO ACCION
            generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "GENERACIÓN DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());

            limpiarPanelDerecho();
            ocultarPanelDerecho();

        } else {

            certificadoSeleccionado.setCerEstado("A");
            certificadoSeleccionado.setCerUsu(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            certificadoSeleccionado.setCerFHR(Calendar.getInstance().getTime());

            certificadoSeleccionado.setCerCertificado(textoCertificado);
            try {
                certificadoSeleccionado.setCerFechaImpresion(calcularFecha(Calendar.getInstance().getTime()));
            } catch (ParseException ex) {
                Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
            }

            servicioCertificado.edit(certificadoSeleccionado);
            //CERTIFICADO CARGA
            certificadoCargaSeleccionado.setCdcEstadoProceso("TERMINADO");
            certificadoCargaSeleccionado.setCdcTipoGravamen(tipoGravamen);

            servicioCertificadoCarga.edit(certificadoCargaSeleccionado);

            //GENERO LOG CERTIFICADO ACCION
            generarCertificadoAccion(certificadoSeleccionado.getCertificadoPK().getCerNumero(), "ACTUALIZACIÓN DE CERTIFICADO", "CER", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            limpiarPanelDerecho();
            ocultarPanelDerecho();
        }

        indiceTabViewCertificados = 0;

        try {
            llenarTablaCertificadoCarga();
        } catch (ParseException ex) {
            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Date calcularFecha(Date fechaReg) throws ParseException, ServicioExcepcion {
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar fechaSumada = Calendar.getInstance();
        fechaSumada.setTime((formatoFecha.parse(formatoFecha.format(fechaReg)))); //tuFechaBase es un Date;
        setConfigDetalle(servicioConfigDetalle.buscarPorConfig());
        switch (configDetalle.getConfigDetalleTexto()) {
            case "H":
                fechaSumada.add(Calendar.HOUR, configDetalle.getConfigDetalleNumero().intValueExact()); //horasASumar es int.
                break;
            case "D":
                fechaSumada.add(Calendar.DAY_OF_YEAR, configDetalle.getConfigDetalleNumero().intValueExact()); //diasASumar es int.
                break;
        }
        return fechaSumada.getTime();
    }

    public void comprobarFecha(Date fechaReg) throws ParseException, ServicioExcepcion {
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar fechaSumada = Calendar.getInstance();
        fechaSumada.setTime((formatoFecha.parse(formatoFecha.format(fechaReg)))); //tuFechaBase es un Date;
        setConfigDetalle(servicioConfigDetalle.buscarPorConfig());
        switch (configDetalle.getConfigDetalleTexto()) {
            case "H":
                fechaSumada.add(Calendar.HOUR, configDetalle.getConfigDetalleNumero().intValueExact()); //horasASumar es int.
                break;
            case "D":
                fechaSumada.add(Calendar.DAY_OF_YEAR, configDetalle.getConfigDetalleNumero().intValueExact()); //diasASumar es int.
                break;
        }
        Calendar fechaActual = Calendar.getInstance();
        fechaActual.setTime((formatoFecha.parse(formatoFecha.format(Calendar.getInstance().getTime())))); //tuFechaBase es un Date;
//        System.out.println("Fecha Actual: " + fechaActual.getTime());
//        System.out.println("Fecha Sumada: " + fechaSumada.getTime());
        if (fechaSumada.getTime().before(fechaActual.getTime()) || fechaSumada.getTime().equals(fechaActual.getTime())) {
//            System.out.println("Se puede imprimir");
//            setBtnDisablePorFecha(false);
        } else if (fechaSumada.getTime().after(fechaActual.getTime())) {
//            System.out.println("No se Puede imprimir");
//            setMsgfechaHabilitada("Los certificados de esta fecha se habilitan el " + fechaSumada.getTime());
//            setBtnDisablePorFecha(true);
            String pattern = "dd/MM/yyyy HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            JsfUtil.addWarningMessage("Fecha para imprimir certificados: " + simpleDateFormat.format(fechaSumada.getTime()));
        }
    }

    public void imprimirCertificado() {
        System.out.println("====================");
        System.out.println(textoCertificado);
        System.out.println("====================");

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

    //metodo de busqueda para certificado: busqueda, bienes raices y estatuto personal
    public void buscarDatosCertificadoBusqueda() throws ServicioExcepcion {
        listaPropiedadesCertBusqueda = new ArrayList();
        List<Repertorio> listaRepertorio = new ArrayList();
        tramiteSel = new Tramite();
        repertorioSel = new Repertorio();
        actaSeleccionada = new Acta();
        propiedadSeleccionada = new Propiedad();
        listaPropiedadesCertBusqueda = new ArrayList();
        listaPropiedadesSel = new ArrayList();

        try {
            if (persona != null && persona.getPerId() != null) {
                if (listaPropiedadesCertBusqueda.isEmpty()) {
                    mostrarTabSolicitante = true;
                } else {
                    mostrarTabSolicitante = false;
                }
                if (esCertificadoBienesRaices || esCertificadoBusqueda) {
                    listaPropiedadesCertBusqueda = propietarioDao.listarPropiedadesPor_Persona(persona.getPerIdentificacion());
                    propiedadSeleccionada = listaPropiedadesCertBusqueda.get(0);
                    listaRepertorio = servicioRepertorioPropiedad.listarRepertorioPor_Propiedad(propiedadSeleccionada.getPrdMatricula());
                    if (listaRepertorio != null && !listaRepertorio.isEmpty()) {
                        repertorioSel = listaRepertorio.get(0);
                        tramiteSel = repertorioSel.getTraNumero();

                        actaSeleccionada = servicioActa.buscarActaPorNumRepertorio(repertorioSel.getRepNumero());
                        listTramDet = tramiteDetalleDao.listarTraNumero(repertorioSel.getTraNumero().getTraNumero());
                    }

//                    generarTextoComparecientes(listTramDet);
                } else if (esCertificadoEstatuto) {
                    listaPropiedadesCertBusqueda = propietarioDao.listarPropiedadesPor_Persona(persona.getPerIdentificacion());
                    if (listaPropiedadesCertBusqueda != null && !listaPropiedadesCertBusqueda.isEmpty()) {
                        propiedadSeleccionada = listaPropiedadesCertBusqueda.get(0);

                        listaRepertorio = servicioRepertorio.listarPor_PropiedadEnGravamen_o_PersonaEnGravamenDetalle(listaPropiedadesCertBusqueda, persona.getPerId());
                    }
                    if (listaRepertorio == null || listaRepertorio.isEmpty()) {
                        tieneGravamenes = false;
                    } else {
                        tieneGravamenes = true;
                        repertorioSel = listaRepertorio.get(0);
                        tramiteSel = repertorioSel.getTraNumero();
                        actaSeleccionada = servicioActa.buscarActaPorNumRepertorio(repertorioSel.getRepNumero());
                    }

//                    listTramDet=tramiteDetalleDao.listarTraNumero(repertorioSel.getTraNumero().getTraNumero()));
//
//                    generarTextoComparecientes(listTramDet);
                }
                switch (tipoCertificadoSeleccionado.getTroTipo()) {
//                    case "G":
//                        break;
                    case "R":
                        if (listaPropiedadesCertBusqueda != null && !listaPropiedadesCertBusqueda.isEmpty()) {
                            listaPropiedadesSel.addAll(listaPropiedadesCertBusqueda);
                        }
                        break;
//                    case "B":
//                        break;
//                    case "E":
//                        break;
//                    case "N":
//                        break;
//                    case "P":
//                        break;
//                    default:
//                        break;

                }

            } else {
                JsfUtil.addErrorMessage("Debe seleccionar una Persona");
            }
        } catch (Exception e) {
//            JsfUtil.addWarningMessage("Datos incompletos en la búsqueda");
            System.out.println(e);
        }

    }

    public void verificarSeleccionPropiedades() {

        if (listaPropiedadesSel.isEmpty()) {
            JsfUtil.addErrorMessage("Debe seleccionar al menos una propiedad");
        } else {
            mostrarTabSolicitante = true;
        }
    }

    public void cambiarTab2(TabChangeEvent event) {
        System.out.println("Se cambió el tab");
        PrimeFaces current = PrimeFaces.current();
        tabSeleccionado = event.getTab().getId();
        if (event.getTab().getId().equals("tab_certificado2")) {
            switch (tipoCertificadoSeleccionado.getTroTipo()) {
//                case "G":
//                    switch (tipoGravamen) {
//
//                        case "NEGATIVA":
//
//                            break;
//                        case "ESTATUTO":
//
//                            break;
//                        default: //GRAVAMEN
//
//                            break;
//                    }
//                    break;
                case "R":
                    generarCertificadoBienesRaices();
                    break;
                case "B":
                    generarCertificadoBusqueda();
                    break;
                case "E":
                    generarCertificadoEstatuto();
                    break;
                case "N":
                    break;
                case "P":
                    break;
                default:
                    break;

            }

        }
    }

//    public String crearCabeceraCertificadoBusqueda() {
//        String cabecera = "";
//        Date fechaIngreso = Calendar.getInstance().getTime();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//        String txtFechaIng = format.format(fechaIngreso);
//        String numeroFactura = facturaSeleccionada.getFacNumero();
//        cabecera = ""
//                + "<p align='center'><strong>CERTIFICADOS DE BÚSQUEDA</strong></p>"
//                + "<br>"
//                + "<p><strong>Factura: </strong>" + numeroFactura + "</p>"
//                + "<br>"
//                + "<p><strong>FECHA DE INGRESO:</strong>" + txtFechaIng + "</p>"
//                + "<br>";
//        return cabecera;
//    }
    public void generarTextoComparecientes(List<TramiteDetalle> listTramDet) {
        String nombreComparecientes = "";

        for (TramiteDetalle tramiteDetalle : listTramDet) {

            if (tramiteDetalle.getTtcId().getTtcPropietario().equals("S")) {
                String apMaterno = "";
                if (tramiteDetalle.getPerId().getPerApellidoMaterno() != null) {
                    apMaterno = tramiteDetalle.getPerId().getPerApellidoMaterno();
                } else {
                    apMaterno = "";
                }
                nombreComparecientes += tramiteDetalle.getPerId().getPerNombre().trim() + " "
                        + tramiteDetalle.getPerId().getPerApellidoPaterno().trim() + " "
                        + apMaterno.trim() + ", " + tramiteDetalle.getTdtTpcDescripcion() + "<br></br>";
            }
        }
        txtComparecientes = nombreComparecientes;
    }

    public String generarTextoParroquias(List<Propiedad> listaPropiedades) {
        String txtParroquias = "";
        List<String> listaNombresParroquias = new ArrayList();
        for (Propiedad propiedad : listaPropiedades) {
            String nombreParroquia = propiedad.getPrdTdtParNombre().trim();
            if (!listaNombresParroquias.contains(nombreParroquia)) {
                listaNombresParroquias.add(nombreParroquia);
            }

        }
        int contador = 1;
        for (String nombreParroquia : listaNombresParroquias) {
            txtParroquias += nombreParroquia;
            if (contador != listaPropiedades.size()) {
                txtParroquias += ",";
            }
            contador++;
        }
        return txtParroquias;
    }
//busqueda

    public void generarTextoPropiedades() {
        textoPropiedades = "";
        String txtActaFecha = "";
        String txtActaFoja = "";
        String txtActaInscripcion = "";
        String txtActaNotaria = "";
        String txtActaTomo = "";
        String numeroRepertorio = "";
        String txtDescObs = "";
        String descripcionPropiedad = "";
        String textoPropiedades1 = "";

        String contrato = "";
        String parroquia = "";

        List<TramiteDetalle> listaTramDetaItemSel = new ArrayList();

        for (Propiedad propiedadItem : listaPropiedadesSel) {
            List<Repertorio> listaRepertorioItemSel = new ArrayList();
            Repertorio repItemSel = new Repertorio();
            Tramite traItemSel = new Tramite();
            Acta actaItemSel = new Acta();
            Propiedad prpdSel = new Propiedad();
//            propiedadSeleccionada = listaPropiedadesCertBusqueda.get(0);
            try {
                //OBTENGO LOS DATOS
                listaRepertorioItemSel = servicioRepertorioPropiedad.listarRepertorioPor_Propiedad(propiedadItem.getPrdMatricula());
                repItemSel = listaRepertorioItemSel.get(0);
                traItemSel = repItemSel.getTraNumero();
                actaItemSel = servicioActa.buscarActaPorNumRepertorio(repItemSel.getRepNumero());
                listaTramDetaItemSel.addAll(tramiteDetalleDao.listarTraNumero(repItemSel.getTraNumero().getTraNumero()));
//LLENO LOS TEXTOS
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                if (actaItemSel != null) {
                    if (actaItemSel.getActFch() != null) {
                        txtActaFecha = format.format(actaItemSel.getActFch());
                    }
                    txtActaFoja = "" + actaItemSel.getActFoja();

                    txtActaInscripcion = "" + actaItemSel.getActInscripcion();

                    txtActaTomo = "" + actaItemSel.getActTomo();

                    if (actaItemSel.getActNotaria() != null) {
                        txtActaNotaria = "" + actaItemSel.getActNotaria();
                    }

                }
                if (repItemSel.getRepNumero() != null) {
                    numeroRepertorio = "" + repItemSel.getRepNumero();
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            descripcionPropiedad = propiedadItem.getPrdDescripcion2() + " " + propiedadItem.getPrdDescripcion1()
                    + " de la Propiedad con matrícula " + propiedadItem.getPrdMatricula() + ". " + "\n";
            txtDescObs = propiedadItem.getObservacionT() + "." + "\n";

            textoPropiedades += ""
                    + "<p><strong>Descripción Propiedad: </strong>" + descripcionPropiedad + " </p>"
                    + "<p><strong>Observación: </strong>" + txtDescObs + " </p>"
                    + "<br>"
                    + "<table>"
                    + "  <tr>"
                    + "    <td>R.P o R.H: </td>"
                    + "    <td> " + numeroRepertorio + "</td>"
                    + "    <td>Fecha de Celebración: </td>"
                    + "    <td>" + txtActaFecha + "</td>"
                    + "  </tr>"
                    + "  <tr>"
                    + "    <td>Fojas: </td>"
                    + "    <td>" + txtActaFoja + "</td>"
                    + "    <td>No. Inscripción: </td>"
                    + "    <td>" + txtActaInscripcion + "</td>"
                    + "  </tr>"
                    + "  <tr>"
                    + "    <td>Tomo: </td>"
                    + "    <td>" + txtActaTomo + " </td>"
                    + "    <td>Fecha de Inscripción: </td>"
                    + "    <td>" + txtActaFecha + " </td>"
                    + "  </tr>"
                    + "  <tr>"
                    + "    <td>Notaría: </td>"
                    + "    <td>" + txtActaNotaria + " </td>"
                    + "    <td> </td>"
                    + "    <td> </td>"
                    + "  </tr>"
                    + "</table>"
                    + "<br>";
        }
        try {
            System.out.println("tamaño listaTramDetaItemSel:" + listaTramDetaItemSel.size());
            generarTextoComparecientes(listaTramDetaItemSel);

        } catch (Exception e) {
            System.out.println(e);
        }

        if (repertorioSel != null) {
            contrato = "" + repertorioSel.getRepTtrDescripcion();
        }
        if (propiedadSeleccionada != null) {
            parroquia = "" + propiedadSeleccionada.getPrdTdtParNombre();
        }
        if (!listaPropiedadesSel.isEmpty()) {
            textoPropiedades1 += "<strong>Comparecientes: </strong>" + txtComparecientes + "<br>"
                    + "<strong>Contrato: </strong>" + contrato + "<br>"
                    + "<strong>Parroquia: </strong>" + parroquia + "<br>";

        }
        textoPropiedades = textoPropiedades1 + textoPropiedades;

    }

    public void generarCertificadoBusqueda() {
        String canton = "";
        String numCertificado = "";
        String numTramite = "";
        String numFactura2 = "";

        String personaSel = "";
        String textoCertificaQue = "";

        textoPropiedades = "";
        String tipoCertificado = "BÚSQUEDA";
        textoCertificado = "";
        if (txtSolicitanteNombre != null) {
            txtSolicitanteNombre = txtSolicitanteNombre.toUpperCase();
        }
        try {
            canton = "" + servicioInstitucion.encontrarCanton();
            numCertificado = "" + generarNumeroCertificado();
            numTramite = "" + facturaSeleccionada.getFacTraNumero().toString();
            numFactura2 = "" + facturaSeleccionada.getFacNumero();

            personaSel = "" + persona.getPerApellidoPaterno() + " " + persona.getPerApellidoMaterno() + " " + persona.getPerNombre();
            generarTextoPropiedades();
        } catch (Exception e) {
            System.out.println(e);
        }

        textoCertificado = "";
        for (int i = 0; i < numSaltosLinea; i++) {
            textoCertificado += "<br>";
        }
        if (listaPropiedadesSel.isEmpty()) {
            textoCertificaQue = "NO se encuentra inscrita o registrada propiedad alguna a nombre de la señor(a) " + personaSel;
        } else {
            textoCertificaQue = "SI se encuentra inscrita o registrada propiedad alguna a nombre de la señor(a) " + personaSel;
        }
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");

        textoCertificado += ""
                + "<font size='-1'>"
                + "<p align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + canton + "</strong></p>"
                + "<p align='right'>Petición No." + numPeticion + "</p>"
                + "<p align='right'>Factura No. " + numFactura2 + "</p>"
                + "<p align='center'><strong>Certificado No.: " + numCertificado + "</strong></p>"
                + "<p align='center'><strong>CERTIFICACIÓN </strong></p><br>"
                + "<p align='left'><strong>Peticionario</strong></p>"
                + "<p align='justify'>El Registro de la Propiedad del cantón " + canton + ", en ejercicio "
                + "de las atribuciones contenidas en el artículo 11, literal e) de la Ley de Registro, "
                + "una vez revisados los índices y libros entregados y que reposan en el "
                + "Registro de la Propiedad del cantón " + canton + ", a petición de " + txtSolicitanteNombre + ""
                + ", portador(a) de la C.C./RUC N° " + txtSolicitanteIdentificacion + ", en legal forma certifica "
                + "que: " + textoCertificaQue + ".</p>"
                + "<br>"
                + textoPropiedades
                + "<strong>Nota:</strong><br>"
                + "La presente certificación es exclusivamente de " + tipoCertificado + ", más no de hipotecas y gravámenes."
                + "<br>"
                + canton + ", " + fechasUtil.convertirFecha_A_letras(Calendar.getInstance().getTime()) + "," + formatoHora.format(Calendar.getInstance().getTime())
                + "<br><br><br><br>"
                + "<table border='1'>"
                + "  <tr>"
                + "    <td>"
                + "     Resp: <br>"
                + "     VM: <br>"
                + "    </td>"
                + "    <td>"
                + "     Rev:"
                + "     LC:"
                + "    </td> "
                + "    <td>"
                + "     Autoriz."
                + "     JB."
                + "    </td>"
                + "  </tr>"
                + "</table>"
                + "<font size='-2'><p align='justify' style = 'line-height: 1em'><strong>"
                + "a) Se aclara que la presente certificación se ha conferido luego de revisado el contenido de los índices, libros, registros y base de datos entregados al Registro de la Propiedad del Gobierno Autónomo Descentralizado Municipal del Cantón " + canton + ", mediante acta de "
                + "1 de Septiembre de 2011. Y con el historial de quince años atrás.- b) Esta Administración no se responsabiliza de los datos erróneos o falsos que se hayan proporcionado por los particulares y que puedan inducir a error o equivocación, así como tampoco del uso doloroso o fraudulento que se pueda hacer del certificado."
                + " c) Es de responsabilidad del Solicitante, el uso de la presente certificación, de conformidad con lo dispuesto en el Inciso quinto del Art.6 de la Ley del Sistema Nacional de Registro de Datos Públicos. En virtud de que los datos registrales del sistema son susceptibles de actualización, rectificación o supresión, con arreglo a la Ley del Sistema Registrado de la Propiedad o a sus funcionarios, para su inmediata modificación."
                + "</strong></p></font><br>"
                + "<p align='center'><strong>LOS DATOS CONSIGNADOS ERRONEA O DOLOSAMENTE EXIMEN DE RESPONSABILIDAD AL CERTIFICANTE - VALIDEZ DEL CERTIFICADO 30 DÍAS</strong></p>"
                + "</font>"
                + "";
        String urlImagenEspacioEnBlanco = "";
        if (agregarImagenEspacioEnBlanco) {
            try {
                urlImagenEspacioEnBlanco = servicioConfigDetalle.buscarPorConfig("IMAGENBLANCO").getConfigDetalleTexto();
            } catch (ServicioExcepcion ex) {
                Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
            }
            textoCertificado += "<img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }

        try {
            generarCertificadoPdf();
        } catch (IOException ex) {
            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generarCertificadoBienesRaices() {
        String canton = "";
        String numCertificado = "";
        String numTramite = "";
        String numFactura2 = "";
        String contrato = "";
        String parroquia = "";
        String personaSel = "";
        String textoCertificaQue = "";

        if (txtSolicitanteNombre != null) {
            txtSolicitanteNombre = txtSolicitanteNombre.toUpperCase();
        }

        textoPropiedades = "";
        String tipoCertificado = "BIENES RAÍCES";

        textoCertificado = "";

        try {
            canton = "" + servicioInstitucion.encontrarCanton();
            numCertificado = "" + generarNumeroCertificado();
            numTramite = "" + facturaSeleccionada.getFacTraNumero().toString();
            numFactura2 = "" + facturaSeleccionada.getFacNumero();
            if (repertorioSel != null) {
                contrato = "" + repertorioSel.getRepTtrDescripcion();
            }
            if (propiedadSeleccionada != null) {
                parroquia = "" + propiedadSeleccionada.getPrdTdtParNombre();
            }
            personaSel = "" + persona.getPerApellidoPaterno() + " " + persona.getPerApellidoMaterno() + " " + persona.getPerNombre();
            generarTextoPropiedades();

        } catch (Exception e) {
            System.out.println(e);
        }
        String parroquias = "";
        try {
            parroquias = generarTextoParroquias(listaPropiedadesSel);
        } catch (Exception e) {
            System.out.println(e);
        }

        textoCertificado = "";
        for (int i = 0;
                i < numSaltosLinea;
                i++) {
            textoCertificado += "<br>";
        }

        if (listaPropiedadesSel.isEmpty()) {
            textoCertificaQue = "NO se encuentra inscrita o registrada propiedad alguna en las parroquias " + parroquias + " a nombre de la señor(a) " + personaSel;
        } else {
            textoCertificaQue = "SI se encuentra inscrita o registrada propiedad alguna en las parroquias " + parroquias + " a nombre de la señor(a) " + personaSel;
        }
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        textoCertificado += ""
                + "<font size='-1'>"
                + "<p align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + canton + "</strong></p>"
                + "<p align='right'>Petición No." + numPeticion + "</p>"
                + "<p align='right'>Factura No. " + numFactura2 + "</p>"
                + "<p align='center'><strong>Certificado No.: " + numCertificado + "</strong></p>"
                + "<p align='center'><strong>CERTIFICACIÓN </strong></p><br>"
                + "<p align='left'><strong>Peticionario</strong></p>"
                + "<p align='justify'>El Registro de la Propiedad del cantón " + canton + ", en ejercicio "
                + "de las atribuciones contenidas en el artículo 11, literal e) de la Ley de Registro, "
                + "una vez revisados los índices y libros entregados y que reposan en el "
                + "Registro de la Propiedad del cantón " + canton + ", a petición de " + txtSolicitanteNombre + ""
                + ", portador(a) de la C.C./RUC N° " + txtSolicitanteIdentificacion + ", en legal forma certifica "
                + "que: " + textoCertificaQue + ".</p>"
                + "<br>"
                + textoPropiedades
                + "<strong>Nota:</strong><br>"
                + "La presente certificación es exclusivamente de " + tipoCertificado + ", más no de hipotecas y gravámenes."
                + "<br>"
                + canton + ", " + fechasUtil.convertirFecha_A_letras(Calendar.getInstance().getTime()) + "," + formatoHora.format(Calendar.getInstance().getTime())
                + "<br><br><br><br>"
                + "<table border='1'>"
                + "  <tr>"
                + "    <td>"
                + "     Resp: <br>"
                + "     VM: <br>"
                + "    </td>"
                + "    <td>"
                + "     Rev:"
                + "     LC:"
                + "    </td> "
                + "    <td>"
                + "     Autoriz."
                + "     JB."
                + "    </td>"
                + "  </tr>"
                + "</table>"
                + "<font size='-2'><p align='justify' style = 'line-height: 1em'><strong>"
                + "a) Se aclara que la presente certificación se ha conferido luego de revisado el contenido de los índices, libros, registros y base de datos entregados al Registro de la Propiedad del Gobierno Autónomo Descentralizado Municipal del Cantón " + canton + ", mediante acta de "
                + "1 de Septiembre de 2011. Y con el historial de quince años atrás.- b) Esta Administración no se responsabiliza de los datos erróneos o falsos que se hayan proporcionado por los particulares y que puedan inducir a error o equivocación, así como tampoco del uso doloroso o fraudulento que se pueda hacer del certificado."
                + " c) Es de responsabilidad del Solicitante, el uso de la presente certificación, de conformidad con lo dispuesto en el Inciso quinto del Art.6 de la Ley del Sistema Nacional de Registro de Datos Públicos. En virtud de que los datos registrales del sistema son susceptibles de actualización, rectificación o supresión, con arreglo a la Ley del Sistema Registrado de la Propiedad o a sus funcionarios, para su inmediata modificación."
                + "</strong></p></font><br>"
                + "<p align='center'><strong>LOS DATOS CONSIGNADOS ERRONEA O DOLOSAMENTE EXIMEN DE RESPONSABILIDAD AL CERTIFICANTE - VALIDEZ DEL CERTIFICADO 30 DÍAS</strong></p>"
                + "</font>"
                + "";
        String urlImagenEspacioEnBlanco = "";
        if (agregarImagenEspacioEnBlanco) {
            try {
                urlImagenEspacioEnBlanco = servicioConfigDetalle.buscarPorConfig("IMAGENBLANCO").getConfigDetalleTexto();
            } catch (ServicioExcepcion ex) {
                Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
            }
            textoCertificado += "<img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }

        try {
            generarCertificadoPdf();
        } catch (IOException ex) {
            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCertificado.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//Estatuto
    public void generarCertificadoEstatuto() {
        String canton = "";
        String numCertificado = "";
        String numTramite = "";
        String numFactura2 = "";
        String contrato = "";
        String parroquia = "";
        String personaSel = "";
        String textoCertificaQue = "";

        textoPropiedades = "";
        String tipoCertificado = "ESTATUTO PERSONAL";
        textoCertificado = "";

        generarDescripcionGravamenes();

        if (txtSolicitanteNombre != null) {
            txtSolicitanteNombre = txtSolicitanteNombre.toUpperCase();
        }
        try {
            canton = "" + servicioInstitucion.encontrarCanton();
            numCertificado = "" + generarNumeroCertificado();
            numTramite = "" + facturaSeleccionada.getFacTraNumero().toString();
            numFactura2 = "" + facturaSeleccionada.getFacNumero();
            if (repertorioSel != null) {
                contrato = "" + repertorioSel.getRepTtrDescripcion();
            }
            if (propiedadSeleccionada != null) {
                parroquia = "" + propiedadSeleccionada.getPrdTdtParNombre();
            }
            personaSel = "" + persona.getPerApellidoPaterno() + " " + persona.getPerApellidoMaterno() + " " + persona.getPerNombre();
            generarTextoPropiedades();
        } catch (Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < numSaltosLinea; i++) {
            textoCertificado += "<br>";
        }
        if (tieneGravamenes) {
            textoCertificaQue = "Revisados los índices de los Registros de Demandas, "
                    + "Prohibiciones de enajenar, Embargos, Interdicciones e insolvencias del o la señor(a) "
                    + "" + personaSel + ", con C.C." + identificacion + ", no se encuentra ningún gravamen";
        } else {
            textoCertificaQue = "Revisados los índices de los Registros de Demandas, "
                    + "Prohibiciones de enajenar, Embargos, Interdicciones e insolvencias del o la señor(a) "
                    + "" + personaSel + ", con C.C." + identificacion + ", SI se encuentran gravamenes";
        }
        DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
        textoCertificado += ""
                + "<font size='-1'>"
                + "<p align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + canton + "</strong></p>"
                + "<p align='right'>Petición No." + numPeticion + "</p>"
                + "<p align='right'>Factura No. " + numFactura2 + "</p>"
                + "<p align='center'><strong>Certificado No.: " + numCertificado + "</strong></p>"
                + "<p align='center'><strong>CERTIFICACIÓN </strong></p><br>"
                + "<p align='left'><strong>Peticionario</strong></p>"
                + "<p align='justify'>El Registro de la Propiedad del cantón " + canton + ", en ejercicio "
                + "de las atribuciones contenidas en el artículo 11, literal e) de la Ley de Registro, "
                + "una vez revisados los índices y libros entregados y que reposan en el "
                + "Registro de la Propiedad del cantón " + canton + ", a petición de " + txtSolicitanteNombre + ""
                + ", portador(a) de la C.C./RUC N° " + txtSolicitanteIdentificacion + ", en legal forma certifica "
                + "que: " + textoCertificaQue + ".</p>"
                + "<br>"
                + "<p align='justify'><strong>Gravamen: </strong>" + reemplazarPuntoComaConSalto(gravamen) + "</p>"
                + "<br>"
                + textoPropiedades
                + "<strong>Nota:</strong><br>"
                + "La presente certificación es exclusivamente de " + tipoCertificado + "."
                + "<br>"
                + canton + ", " + fechasUtil.convertirFecha_A_letras(Calendar.getInstance().getTime()) + "," + formatoHora.format(Calendar.getInstance().getTime())
                + "<br><br><br><br>"
                + "<table border='1'>"
                + "  <tr>"
                + "    <td>"
                + "     Resp: <br>"
                + "     VM: <br>"
                + "    </td>"
                + "    <td>"
                + "     Rev:"
                + "     LC:"
                + "    </td> "
                + "    <td>"
                + "     Autoriz."
                + "     JB."
                + "    </td>"
                + "  </tr>"
                + "</table>"
                + "<font size='-2'><p align='justify' style = 'line-height: 1em'><strong>"
                + "a) Se aclara que la presente certificación se ha conferido luego de revisado el contenido de los índices, libros, registros y base de datos entregados al Registro de la Propiedad del Gobierno Autónomo Descentralizado Municipal del Cantón " + canton + ", mediante acta de "
                + "1 de Septiembre de 2011. Y con el historial de quince años atrás.- b) Esta Administración no se responsabiliza de los datos erróneos o falsos que se hayan proporcionado por los particulares y que puedan inducir a error o equivocación, así como tampoco del uso doloroso o fraudulento que se pueda hacer del certificado."
                + " c) Es de responsabilidad del Solicitante, el uso de la presente certificación, de conformidad con lo dispuesto en el Inciso quinto del Art.6 de la Ley del Sistema Nacional de Registro de Datos Públicos. En virtud de que los datos registrales del sistema son susceptibles de actualización, rectificación o supresión, con arreglo a la Ley del Sistema Registrado de la Propiedad o a sus funcionarios, para su inmediata modificación."
                + "</strong></p></font><br>"
                + "<p align='center'><strong>LOS DATOS CONSIGNADOS ERRONEA O DOLOSAMENTE EXIMEN DE RESPONSABILIDAD AL CERTIFICANTE - VALIDEZ DEL CERTIFICADO 30 DÍAS</strong></p>"
                + "</font>"
                + "";

        String urlImagenEspacioEnBlanco = "";
        if (agregarImagenEspacioEnBlanco) {
            try {
                urlImagenEspacioEnBlanco = servicioConfigDetalle.buscarPorConfig("IMAGENBLANCO").getConfigDetalleTexto();

            } catch (ServicioExcepcion ex) {
                Logger.getLogger(ControladorCertificado.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            textoCertificado += "<img src='" + urlImagenEspacioEnBlanco + "' alt=''/>";
        }

        try {
            generarCertificadoPdf();

        } catch (IOException ex) {
            Logger.getLogger(ControladorCertificado.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCertificado.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

}
