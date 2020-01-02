/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.GravamenDetalleDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.TramiteValorDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.GravamenDetalle;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.TipoMarginacion;
import com.rm.empresarial.modelo.TipoSuspension;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteSuspension;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TipoMarginacionServicio;
import com.rm.empresarial.servicio.TipoSuspensionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteSuspensionServicio;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorCancelacionesSuspension")
@ViewScoped
public class ControladorCancelacionSuspencion implements Serializable {

    @Inject
    private DataManagerSesion dataManagerSesion;

    @Inject
    private TramiteUtil tramiteUtil;

    @EJB
    private TipoSuspensionServicio servicioTipoSuspension;
    @EJB
    private TramiteServicio servicioTramite;

    @EJB
    private TramiteSuspensionServicio servicioTramiteSuspension;

    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @Getter
    @Setter
    private Long numProforma;

    @Getter
    @Setter
    private String numeroRepertorio;

    @Getter
    @Setter
    private String observacion;

    @Getter
    @Setter
    private List<TipoSuspension> listaTipoSuspension;

    @Getter
    @Setter
    private List<Propietario> listaPropietarioPropiedad;

    @Getter
    @Setter
    private List<TipoSuspension> listaTipoSuspensionBDD;

    @Getter
    @Setter
    private List<String> listaIDTipoSuspensionSeleccionados;

    @Getter
    @Setter
    private List<TipoSuspension> listaTipoSuspensionSeleccionados;

    @Getter
    @Setter
    private List<Long> listaMarginacion1;

    @Getter
    @Setter
    private List<Marginacion> listaMarginacion2;

    @Getter
    @Setter
    private List<Marginacion> listaMarginacion3;

    @Getter
    @Setter
    private TipoSuspension nuevoTipoSuspension;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    //segundo many checkbox (SEGUNDO CASO)
    @Getter
    @Setter
    private List<TramiteSuspension> listaTramiteSuspension;

    @Getter
    @Setter
    private List<TramiteSuspension> listaTramiteSuspensionesAntes;

    @Getter
    @Setter
    private List<String> listaIDTramiteSuspensionSeleccionados;
    @Getter
    @Setter
    private Boolean mostrarGrid1;
    @Getter
    @Setter
    private Boolean mostrarGrid2;

    @Getter
    @Setter
    private TramiteSuspension nuevoTramiteSuspension;

    @Getter
    @Setter
    private String usuarioLogeado;

    @Getter
    @Setter
    private String numTramite;

    @Inject
    @Getter
    @Setter
    private ControladorCancelacion ControladorCancelacion;

    @EJB
    private MarginacionServicio marginacionServicio;

    @EJB
    private ActaServicio actaServicio;

    @EJB
    private GravamenDao gravamenDao;

    @EJB
    private TramiteValorDao tramiteValorDao;

    @EJB
    private TipoMarginacionServicio tipoMarginacionServicio;

    @EJB
    private GravamenDetalleDao gravamenDetalleDao;

    @EJB
    private RepertorioDao repertorioDao;

    public ControladorCancelacionSuspencion() {

        listaTipoSuspension = new ArrayList<>();
        listaTipoSuspensionSeleccionados = new ArrayList<>();
        listaIDTramiteSuspensionSeleccionados = new ArrayList<>();
        listaMarginacion1 = new ArrayList<>();
        listaMarginacion2 = new ArrayList<>();
        listaMarginacion3 = new ArrayList<>();
    }

    @PostConstruct
    public void postConstructor() {
        mostrarGrid1 = false;
        mostrarGrid2 = false;

        usuarioLogeado = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
        try {
            listarTipoSuspencion();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("#items seleccionados en lista id tramite suspension: " + listaIDTramiteSuspensionSeleccionados.size());
//        try {
////            listaTipoSuspensionBDD = servicioTipoSuspension.listarTodo();
//
//        } catch (ServicioExcepcion ex) {
//            Logger.getLogger(RazonesSuspensionController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void agregarNuevo() {
        System.out.println("com.rm.empresarial.controlador.RazonesSuspensionController.agregarNuevo()");
        try {
            String nombreUsuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();

            if (!observacion.isEmpty()) {
                if (mostrarGrid2) {
                    Long idNuevo = servicioTipoSuspension.asignarID();
                    nuevoTramiteSuspension = new TramiteSuspension(idNuevo, nombreUsuario, Calendar.getInstance().getTime(), observacion);
                    listaTramiteSuspension.add(nuevoTramiteSuspension);
                    listaIDTramiteSuspensionSeleccionados.add(idNuevo.toString());

                } else {
                    Long idNuevo = servicioTipoSuspension.asignarID();
                    nuevoTipoSuspension = new TipoSuspension(idNuevo, observacion, "A", nombreUsuario, Calendar.getInstance().getTime());
                    listaTipoSuspension.add(nuevoTipoSuspension); //agregando nuevo objecto a la lista select items
                    listaTipoSuspensionSeleccionados.add(nuevoTipoSuspension);//nuevo tipo suspension agregado lista de objetos
                    listaIDTipoSuspensionSeleccionados.add(idNuevo.toString());//nuevo tipo suspencion como seleccionado
                }

            }
            observacion = "";

        } catch (Exception e) {
            System.out.println(e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Realice primero una búsqueda por el número de trámite ", ""));

        }

    }

    public void listarTipoSuspencion() throws ServicioExcepcion {
        listaTipoSuspension = servicioTipoSuspension.listarPorTipoInscripcion();
        mostrarGrid1 = true;

    }

    public void salir() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/SentenciasPE/SentenciasPE.xhtml");
        //FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");    
    }

    public void insertarTramitesSuspension() throws ServicioExcepcion, ParseException {
        System.out.println("com.rm.empresarial.controlador.RazonesSuspensionController.insertarTramitesSuspension()");
        String nombreUsuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();

        Boolean exito = false;
        if (numProforma != null) {
            //tramiteSeleccionado = servicioTramite.buscarTramitePor_numero_estado_Insc_INP(getNumProforma());
            tramiteSeleccionado = servicioTramite.buscarTramitePorNumero(getNumProforma());
        }

        if (tramiteSeleccionado != null) {

            if (listaTipoSuspensionSeleccionados.size() + listaIDTipoSuspensionSeleccionados.size() > 0) {
                for (TipoSuspension tipSus : listaTipoSuspension) {
                    if (listaIDTipoSuspensionSeleccionados.contains(tipSus.getTpsId().toString())) {
                        TramiteSuspension nuevoTraSus = new TramiteSuspension(1L, nombreUsuario, Calendar.getInstance().getTime(), tipSus.getTpsNombre());

                        tramiteSeleccionado.setTraEstadoRegistro("RZ");
                        nuevoTraSus.setTraNumero(tramiteSeleccionado);
                        nuevoTraSus.setTmsTpsId(tipSus.getTpsId());
                        nuevoTraSus.setTmsEstado("S");
                        nuevoTraSus.setTrmTipo("INA");

                        System.out.println("tram num " + tramiteSeleccionado.getTraNumero());

                        try {
                            servicioTramiteSuspension.create(nuevoTraSus);
                            servicioTramite.edit(tramiteSeleccionado);

                            exito = true;
                            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
                                    tramiteSeleccionado.getTraNumero().toString(),
                                    "Tramite " + tramiteSeleccionado.getTraNumero() + " suspendido por: "
                                    + tipSus.getTpsNombre(),
                                    tramiteSeleccionado.getTraEstado(),
                                    tramiteSeleccionado.getTraEstadoRegistro(), null);
                        } catch (Exception e) {
                            exito = false;
                        }

                    }
                }
                if (exito) {
                    //ACTUALIZAR ESTADO EN REPERTORIO USUARIO
//                    List<RepertorioUsuario> listRepUs = new ArrayList<>();
//                    listRepUs.clear();
//                    listRepUs = servicioRepertorioUsuario.listarPorNumRepertorio(Long.valueOf(numeroRepertorio));
//                    for (RepertorioUsuario repUs : listRepUs) {
//                        repUs.setRpuEstado("I");
//                        servicioRepertorioUsuario.edit(repUs);
//                    }
                    cambiarEstadoGravamen();
                    crearMarginacion();
                    cambiarEstadoRepUsr();
                    cambiarTramValorAccion();
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Trámite(s) suspensión ingresado(s)!", ""));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:¡No se pudo ingresar el tramite(s) de suspension!", ""));
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe seleccionar los tipos de suspensión", ""));

            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe ingresar un número de trámite", ""));
        }
        numProforma = null;
        listaIDTipoSuspensionSeleccionados = new ArrayList<>();
        listaTipoSuspensionSeleccionados = new ArrayList<>();
        tramiteSeleccionado = null;
        mostrarGrid1 = true;
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlgRznSus').hide();");

    }

    public void cambiarEstadoRepUsr() throws ServicioExcepcion {
        int numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPor_Tram_Usr_Tipo(numProforma,
                dataManagerSesion.getUsuarioLogeado().getUsuId(), "INC");
        System.out.println("- " + numRepUsuActualizados + " repertorios actualizados");

//        List<Repertorio> lista = inscripcionDao.ListarPorFHRPorUsrLog(FHR, dataManagerSesion.getUsuarioLogeado().getUsuId()) 
    }

    public void cambiarTramValorAccion() throws ServicioExcepcion {
        List<TramiteValor> listTramiteValor = new ArrayList<>();
        listTramiteValor.clear();
        Tramite tram = servicioTramite.buscarTramitePorNumero(numProforma);
        if (tram != null) {
            listTramiteValor = tramiteValorDao.ListarPor_NumTramite_Estado(tram);
            for (TramiteValor tramiteValor : listTramiteValor) {
                tramiteValor.setTrvAccion(0);
                tramiteValorDao.edit(tramiteValor);
            }
        }
    }

    public void insertarTramitesSuspensionCaso2() throws ServicioExcepcion, ParseException {
//        String nombreUsuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
        Boolean exito = false;

        if (numProforma != null) {
            //tramiteSeleccionado = servicioTramite.buscarTramitePor_numero_estado_Insc_INA(getNumProforma());
            tramiteSeleccionado = servicioTramite.buscarTramitePorNumero(getNumProforma());
        }

        if (tramiteSeleccionado != null) {
//            if (listaTipoSuspensionSeleccionados.size() + listaIDTramiteSuspensionSeleccionados.size() > 0) {
            if (listaIDTramiteSuspensionSeleccionados.isEmpty()) {
                tramiteSeleccionado.setTraEstadoRegistro("RI");
            } else {
                tramiteSeleccionado.setTraEstadoRegistro("RZ");

            }

            for (TramiteSuspension tramSusp : listaTramiteSuspension) {
                if (listaIDTramiteSuspensionSeleccionados.contains(tramSusp.getTmsId().toString())) {
                    tramSusp.setTmsEstado("S");

//                    tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, 
//                            tramiteSeleccionado.getTraNumero().toString(), 
//                            "Tramite "+tramiteSeleccionado.getTraNumero()+ "suspendido por: "
//                                    +tramSusp.getTmsNombre()
//                                    , tramiteSeleccionado.getTraEstado(), 
//                                    tramiteSeleccionado.getTraEstadoRegistro());  
                } else {
                    tramSusp.setTmsEstado("N");
//                    tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, 
//                            tramiteSeleccionado.getTraNumero().toString(), 
//                            "Tramite "+tramiteSeleccionado.getTraNumero()+ "levantada suspensión por: "
//                                    +tramSusp.getTmsNombre()
//                                    , tramiteSeleccionado.getTraEstado(), 
//                                    tramiteSeleccionado.getTraEstadoRegistro());
                }

                //*--
                try {
                    tramSusp.setTraNumero(tramiteSeleccionado);
                    servicioTramiteSuspension.edit(tramSusp);
                    servicioTramite.edit(tramiteSeleccionado);
                    exito = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (exito) {

                //ACTUALIZAR ESTADO EN REPERTORIO USUARIO
//                List<RepertorioUsuario> listRepUs = new ArrayList<>();
//                listRepUs.clear();
//                listRepUs = servicioRepertorioUsuario.listarPorNumRepertorio(Long.valueOf(numeroRepertorio));
//                for (RepertorioUsuario repUs : listRepUs) {
//                    repUs.setRpuEstado("I");
//                    servicioRepertorioUsuario.edit(repUs);
//                }
                cambiarEstadoRepUsr();
                cambiarTramValorAccion();
                //*-- Se guarda un registro de si se levanto o se añadio una suspension. Y tambien nuevas suspensiones
                for (TramiteSuspension tramiteSuspensionDespues : listaTramiteSuspension) {
                    for (TramiteSuspension tramSuspAntes : listaTramiteSuspensionesAntes) {
                        if (tramSuspAntes.getTmsId().equals(tramiteSuspensionDespues.getTmsId())
                                && !tramSuspAntes.getTmsEstado().equals(tramiteSuspensionDespues.getTmsEstado())) {
                            if (tramSuspAntes.getTmsEstado().equals("S")) {
                                tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
                                        tramiteSeleccionado.getTraNumero().toString(),
                                        "Tramite " + tramiteSeleccionado.getTraNumero() + "suspendido por: "
                                        + tramSuspAntes.getTmsNombre(),
                                        tramiteSeleccionado.getTraEstado(),
                                        tramiteSeleccionado.getTraEstadoRegistro(), null);
                            } else {
                                tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
                                        tramiteSeleccionado.getTraNumero().toString(),
                                        "Tramite " + tramiteSeleccionado.getTraNumero() + "levantada suspensión por: "
                                        + tramSuspAntes.getTmsNombre(),
                                        tramiteSeleccionado.getTraEstado(),
                                        tramiteSeleccionado.getTraEstadoRegistro(), null);
                            }
                        }
                    }
                    // codigo para guardar nuevos
                    if (!listaTramiteSuspensionesAntes.contains(tramiteSuspensionDespues)) {
                        if (tramiteSuspensionDespues.getTmsEstado().equals("S")) {
                            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
                                    tramiteSeleccionado.getTraNumero().toString(),
                                    "Tramite " + tramiteSeleccionado.getTraNumero() + "suspendido por: "
                                    + tramiteSuspensionDespues.getTmsNombre(),
                                    tramiteSeleccionado.getTraEstado(),
                                    tramiteSeleccionado.getTraEstadoRegistro(), null);
                        } else {
                            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
                                    tramiteSeleccionado.getTraNumero().toString(),
                                    "Tramite " + tramiteSeleccionado.getTraNumero() + "levantada suspensión por: "
                                    + tramiteSuspensionDespues.getTmsNombre(),
                                    tramiteSeleccionado.getTraEstado(),
                                    tramiteSeleccionado.getTraEstadoRegistro(), null);
                        }
                    }
                }

                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Trámite(s) suspensión ingresado(s)!", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:¡No se pudo actualizar los Trámites Suspension!", ""));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe ingresar un número de trámite", ""));
        }
        numProforma = null;
        listaIDTramiteSuspensionSeleccionados = new ArrayList<>();
        listaTramiteSuspension = new ArrayList<>();
        tramiteSeleccionado = null;
        mostrarGrid2 = false;

        listaMarginacion1 = marginacionServicio.listarPor_NumRepertorio(numeroRepertorio);

//        
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        Date today = new Date();

        Date todayWithZeroTime = formatter.parse(formatter.format(today));
        try {

            for (Long marginacion1 : listaMarginacion1) {
                Acta acta = new Acta();
                acta = actaServicio.obtenerPorNActa(marginacion1);
                TipoMarginacion tipoMarginacion = new TipoMarginacion();
                tipoMarginacion = tipoMarginacionServicio.find(new TipoMarginacion(), 13);
                //acta = actaServicio.buscarActaPorNumRepFechaRepTipoLibro(numProforma, today, numProforma)
                Marginacion marginacion = new Marginacion();
                marginacion.setActNumero(acta);
                marginacion.setMrgAlt2(numeroRepertorio);
                marginacion.setMrgAlt1("SUSPENSION DEL TRAMITE " + numTramite);
                marginacion.setMrgFHR(Calendar.getInstance().getTime());
                marginacion.setMrgFch(todayWithZeroTime);
                marginacion.setTmcId(tipoMarginacion);
                marginacion.setMrgUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                marginacionServicio.create(marginacion);

            }

            for (Propietario propietario : listaPropietarioPropiedad) {
                List<Gravamen> listaGravamen = new ArrayList<>();
                listaGravamen = gravamenDao.buscarPorMatriculaYRepertorio(Integer.valueOf(numeroRepertorio), propietario.getPrdMatricula().getPrdMatricula());
                for (Gravamen gravamen : listaGravamen) {
                    gravamen.setGraEstado("I");
                    gravamenDao.edit(gravamen);
                }

            }

            ControladorCancelacion.llenarDatos();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void cambiarEstadoGravamen() throws ServicioExcepcion {
        List<Repertorio> listRep = new ArrayList<>();
        listRep = repertorioDao.listarPorNumTramite(numProforma);
        for (Repertorio repertorio : listRep) {
            List<Gravamen> listGrav = new ArrayList<>();
            listGrav.clear();
            listGrav = gravamenDao.buscarPorRepertorio(repertorio.getRepNumero());
            for (Gravamen gravamen : listGrav) {
                List<GravamenDetalle> listGravDet = new ArrayList<>();
                listGravDet.clear();
                listGravDet = gravamenDetalleDao.listarPorGravamenId(gravamen.getGraId());
                for (GravamenDetalle gravamenDetalle : listGravDet) {
                    gravamenDetalle.setGvdEstado("I");
                    gravamenDetalleDao.edit(gravamenDetalle);
                }
                gravamen.setGraEstado("I");
                gravamenDao.edit(gravamen);
            }
        }
    }

    public void crearMarginacion() throws ServicioExcepcion, ParseException {
        List<Repertorio> listRep = new ArrayList<>();
        List<Marginacion> listMarg = new ArrayList<>();
        listRep = repertorioDao.listarPorNumTramite(numProforma);
        for (Repertorio repertorio : listRep) {
            listMarg.clear();
            listMarg = marginacionServicio.listarPor_Repertorio(repertorio.getRepNumero().toString());
            for (Marginacion marg : listMarg) {
                DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date today = new Date();
                Date todayWithZeroTime = formatter.parse(formatter.format(today));
                TipoMarginacion tipoMarginacion = new TipoMarginacion();
                tipoMarginacion = tipoMarginacionServicio.find(new TipoMarginacion(), new Long(13));
                Marginacion marginacion = new Marginacion();
                marginacion.setActNumero(marg.getActNumero());
                marginacion.setMrgAlt2(repertorio.getRepNumero().toString());
                marginacion.setMrgAlt1("SUSPENSION DEL TRAMITE " + numTramite);
                marginacion.setMrgFHR(Calendar.getInstance().getTime());
                marginacion.setMrgFch(todayWithZeroTime);
                marginacion.setTmcId(tipoMarginacion);
                marginacion.setMrgUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                marginacionServicio.create(marginacion);
            }
        }
    }

    public void recargarPagina() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/suspension/RazonesSuspension.xhtml");
    }

}
