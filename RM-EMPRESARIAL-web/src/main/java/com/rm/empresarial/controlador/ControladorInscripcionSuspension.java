/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.TipoMarginacion;
import com.rm.empresarial.modelo.TipoSuspension;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteSuspension;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TipoMarginacionServicio;
import com.rm.empresarial.servicio.TipoSuspensionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteSuspensionServicio;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorInscripcionSuspension")
@ViewScoped
public class ControladorInscripcionSuspension implements Serializable {

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
    private ControladorInscripcion controladorInscripcion;

    @Getter
    @Setter
    private Boolean disabledBtnImprimir = true;

    @EJB
    private MarginacionServicio marginacionServicio;

    @EJB
    private InstitucionDao institucionDao;

    @EJB
    private ActaServicio actaServicio;

    @EJB
    private TipoMarginacionServicio tipoMarginacionServicio;

    @Inject
    ReporteUtil reporteUtil;

    public ControladorInscripcionSuspension() {
        System.out.println("com.rm.empresarial.controlador.RazonesSuspensionController.<init>()");
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

    public void buscarTramite() throws ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.RazonesSuspensionController.buscarTramite()");
        if (numProforma != null) {
            try {
                if (servicioTramiteSuspension.buscarTramitesSuspensionPorNumTramYporEstadoS_Inscripcion(getNumProforma()).size() > 0) {
//                if (servicioTramiteSuspension.buscarTramitesSuspensionPorNumTramYporEstadoS_Inscripcion(numProforma).size() > 0) {
                    listaTramiteSuspension = servicioTramiteSuspension.buscarTramitesSuspensionPorNumTramYporUsuario_Inscripcion(numProforma,
                            dataManagerSesion.getUsuarioLogeado().getUsuId());
                    listaTramiteSuspensionesAntes = servicioTramiteSuspension.buscarTramitesSuspensionPorNumTramYporUsuario_Inscripcion(numProforma,
                            dataManagerSesion.getUsuarioLogeado().getUsuId());
                    if (listaTramiteSuspension.size() > 0) {
                        for (TramiteSuspension tramSusp : listaTramiteSuspension) {
                            System.out.println("id: " + tramSusp.getTmsId() + " estado: " + tramSusp.getTmsEstado());
                            if (tramSusp.getTmsEstado().trim().equals("S")) {
                                if (listaIDTramiteSuspensionSeleccionados == null) {
                                    listaIDTramiteSuspensionSeleccionados = new ArrayList<>();
                                }
                                listaIDTramiteSuspensionSeleccionados.add(tramSusp.getTmsId().toString());
                            }
                        }
                        tramiteSeleccionado = servicioTramite.buscarTramitePorNumero(getNumProforma());
                        System.out.println("tramite seleccionado: " + tramiteSeleccionado.getTraNumero());
                        mostrarGrid2 = true;
                        mostrarGrid1 = false;
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Se encontraron " + listaTramiteSuspension.size() + " trámites suspensión", ""));
                    } else {
                        JsfUtil.addErrorMessage("Error, Tramite suspendido por otro funcionario");
                    }

//                } else {
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
//                            "Todos los trámites suspensión están inactivos", ""));
//                }
                } else if (servicioTramite.buscarTramitePor_numero_estado_Insc(getNumProforma()) != null) {
                    tramiteSeleccionado = servicioTramite.buscarTramitePor_numero_estado_Insc(getNumProforma());
                    listaTipoSuspension = servicioTipoSuspension.listarPorTipoInscripcion();
                    mostrarGrid1 = true;
                    mostrarGrid2 = false;
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Numero de Trámite encontrado ", ""));

                }

            } catch (Exception e) {
                e.printStackTrace();

                System.out.println("Tramite no existe");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Trámite no existe", ""));
            }

        }

    }

    public void salir() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");

    }

    public void insertarTramitesSuspension() throws ServicioExcepcion {
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
                        nuevoTraSus.setTrmTipo("INS");

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
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Trámite(s) suspensión ingresado(s)!", ""));
                    numProforma = null;
                    controladorInscripcion.setNumTramite(null);
                    listaIDTipoSuspensionSeleccionados = new ArrayList<>();
                    listaTipoSuspensionSeleccionados = new ArrayList<>();
                    tramiteSeleccionado = null;
                    mostrarGrid1 = false;
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:¡No se pudo ingresar el tramite(s) de suspension!", ""));
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe seleccionar los tipos de suspensión", ""));

            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Debe ingresar un número de trámite", ""));
        }
//        numProforma = null;
//        listaIDTipoSuspensionSeleccionados = new ArrayList<>();
//        listaTipoSuspensionSeleccionados = new ArrayList<>();
//        tramiteSeleccionado = null;
//        mostrarGrid1 = false;        

    }

    public void listarTipoSuspencion() throws ServicioExcepcion {
        listaTipoSuspension = servicioTipoSuspension.listarPorTipoInscripcion();
        mostrarGrid1 = true;
    }

    public void cambiarEstadoRepUsr() throws ServicioExcepcion {
        int numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPor_Tram_Usr_Tipo(numProforma,
                dataManagerSesion.getUsuarioLogeado().getUsuId(), "INS");
        System.out.println("- " + numRepUsuActualizados + " repertorios actualizados");

//        List<Repertorio> lista = inscripcionDao.ListarPorFHRPorUsrLog(FHR, dataManagerSesion.getUsuarioLogeado().getUsuId()) 
    }

    public void insertarTramitesSuspensionCaso2() throws ServicioExcepcion, ParseException {
//        String nombreUsuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
        Boolean exito = false;

        if (numProforma != null) {
            //tramiteSeleccionado = servicioTramite.buscarTramitePor_numero_estado_Insc_INP(getNumProforma());
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
                    tramSusp.setTrmTipo("INS");
                    servicioTramiteSuspension.edit(tramSusp);
                    servicioTramite.edit(tramiteSeleccionado);
                    exito = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (exito) {

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
                disabledBtnImprimir=false;
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

//        listaMarginacion2 = listaMarginacion1;
//        
//        for (Marginacion item1 : listaMarginacion1) {
//            
//            int cantidad = 0;
//            for (Marginacion item2 : listaMarginacion2) {
//                if (item1.getActNumero().getActNumero().longValue() == item2.getActNumero().getActNumero().longValue()){
//                    cantidad ++;
//                }
//                if (item1.getActNumero().getActNumero().longValue() == item2.getActNumero().getActNumero().longValue()&& cantidad >1){
//                   
//                          listaMarginacion3.add(item1);
//                    cantidad ++;
//                }
//                
//            }
//            
//        }
        /* 
      int count = 0;
        boolean existe = false;
        for (Marginacion marginacion1 : listaMarginacion1) {
            count ++;
           
            if(count ==1 ){
                listaMarginacion2.add(marginacion1);
            }       
            for (Marginacion marginacion2 : listaMarginacion2) {
                
               if(marginacion1.getActNumero().getActNumero().longValue() ==(marginacion2.getActNumero().getActNumero().longValue())){
                   existe = true;
               }
            }
            if(existe == false){
                listaMarginacion3.add(marginacion1);
                
            }
        }
         */
//        for (Marginacion marginac : listaMarginacion1) {
//            System.out.println("lista NR " + marginac.getActNumero().getActNumero());
//            
//        }
        DateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        Date today = new Date();

        Date todayWithZeroTime = formatter.parse(formatter.format(today));

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
        controladorInscripcion.reset();
        controladorInscripcion.llenarDatos();

    }

    public void recargarPagina() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/suspension/RazonesSuspension.xhtml");
    }

    
    public void descargarPDF() throws ServicioExcepcion, JRException {
        Institucion institucion = institucionDao.obtenerInstitucion();
        String pathImagen = institucion.getInsLogo();
        String nomInstitucion = institucion.getInsNombre();
        String usuarioLog = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
        String tipo="INS";

        Date fechaRep = com.ibm.icu.util.Calendar.getInstance().getTime();
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("NumTramite", tramiteSeleccionado.getTraNumero().intValue());
        parametros.put("pathImagen", pathImagen);
        parametros.put("institucion", nomInstitucion);
        parametros.put("fechaReporte", fechaRep);
        parametros.put("usuario", usuarioLog);
        parametros.put("Estado", tipo);
        
        try {
            reporteUtil.imprimirReporteEnPDF("RazonesSuspension", "RazonesSuspensiòn-Tràmite" + tramiteSeleccionado.getTraNumero(), parametros);
        
        } catch (JRException | IOException | NamingException | SQLException ex) {
            
            addErrorMessage("ERROR AL GENERAR PDF");

        }
    }

    //CUSTOM CONVERTER
//    public TipoSuspension getTipoSuspension(Long id) throws ServicioExcepcion {
//        return servicioTipoSuspension.buscarTipoSuspensionPorId(id);
//    }
//    public List<TipoSuspension> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
//    public List<TipoSuspension> getItemsAvailableSelectOne() {
//        return getFacade().findAll();
//    }
//    @FacesConverter(value="converter", forClass = TipoSuspension.class)
//    public static class TipoSuspensionControllerConverter implements Converter {
//
//        @Override
//        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
//            if (value == null || value.length() == 0) {
//                return null;
//            }
//            RazonesSuspensionController controller = (RazonesSuspensionController) facesContext.getApplication().getELResolver().
//                    getValue(facesContext.getELContext(), null, "razonesSuspensionController");
//            Object objeto;
//            try {
//                objeto=controller.getTipoSuspension(getKey(value));
//                return objeto;
//            } catch (ServicioExcepcion ex) {
//                Logger.getLogger(RazonesSuspensionController.class.getName()).log(Level.SEVERE, null, ex);
//                return null;
//
//            }
//        }
//
//        Long getKey(String value) {
//            Long key;
//            key = new Long(value);
//            return key;
//        }
//
//        String getStringKey(Long value) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(value);
//            return sb.toString();
//        }
//
//        @Override
//        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
//            if (object == null) {
//                return null;
//            }
//            if (object instanceof TipoSuspension) {
//                TipoSuspension o = (TipoSuspension) object;
//                return getStringKey(o.getTpsId());
//            } else {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TipoSuspension.class.getName()});
//                return null;
//            }
//        }
//
//    }
}
