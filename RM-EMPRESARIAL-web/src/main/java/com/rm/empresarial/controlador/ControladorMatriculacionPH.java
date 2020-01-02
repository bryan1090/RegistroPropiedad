/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.MatriculacionDao;
import com.rm.empresarial.dao.NuevasMatriculasDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TmpAlicuotaDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.dao.VersionPHDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TmpAlicuota;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.modelo.VersionPH;
import com.rm.empresarial.modelo.VersionPHPK;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.PropietarioServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorMatriculacionPH")
@ViewScoped
public class ControladorMatriculacionPH implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @EJB
    MatriculacionDao MatriculacionDao;

    @EJB
    RepertorioUsuarioDao RepertorioUsuarioDao;

    @EJB
    TramiteDetalleDao TramiteDetalleDao;

    @EJB
    SecuenciaServicio secuenciaServicio;

    @EJB
    TramiteValorServicio servicioTramiteValor;

    @EJB
    TramiteServicio servicioTramite;

    @EJB
    TipoTramiteServicio tipoTramitServicio;

    @EJB
    RepertorioServicio servicioRepertorio;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    TipoTramiteServicio servicioTipoTramite;

    @EJB
    RepertorioPropiedadDao repertorioPropiedadDao;

    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;

    @EJB
    PropiedadServicio servicioPropiedad;

    @EJB
    VersionPHDao versionPHDao;

    @EJB
    TmpAlicuotaDao tmpAlicuotaDao;

    @Inject
    private TramiteUtil tramiteUtil;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Getter
    @Setter
    private Secuencia secuencia;

    @Getter
    @Setter
    private Date FHR;
    @Getter
    @Setter
    private List<RepertorioUsuario> listaRepertorioUsuarioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listadeTramitePopup = new ArrayList<>();

    @Getter
    @Setter
    private String repertorio;

    @Getter
    @Setter
    private String numTramite;

    @Getter
    @Setter
    private String contrato;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalle;
    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleSeleccionado;
    @Getter
    @Setter
    private Parroquia parroquia;

    @Getter
    @Setter
    private List<Parroquia> listaParroquia = new ArrayList<>();

    @Getter
    @Setter
    private Parroquia parroquiaSeleccionada;

    @Getter
    @Setter
    private String fechaPopup;

    @Getter
    @Setter
    private Long parIdActualizado;
    @Getter
    @Setter
    private String parNombreActualizado;
    @Getter
    @Setter
    private Parroquia parroquiaActualizada;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil CargaLaboralUtil;
    @Getter
    @Setter
    private RepertorioUsuario RepertorioUsuario;
    @Getter
    @Setter
    private Repertorio repertorioNum;

    @Getter
    @Setter
    private Boolean nuevoIngreso;
    @Getter
    @Setter
    private Persona personaSeleccionada;
    @Getter
    @Setter
    private String nombrePersona;
    @Getter
    @Setter
    private Long numeroTramite;

    @Getter
    @Setter
    private Long idParroquia;
    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleActualizado;
    @Getter
    @Setter
    private Long parId;
    @Getter
    @Setter
    private String parNombre;

    @Getter
    @Setter
    private String estadoBotonNuevaPropiedad;
    @Getter
    @Setter
    private String estadoBotonEditarPropiedad;

    @Getter
    @Setter
    private Long matricula;
    @Getter
    @Setter
    private Long numRepertorio;
    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleNueva;

    @Getter
    @Setter
    private String catastro;
    @Getter
    @Setter
    private String predio;
    @Getter
    @Setter
    private String fechaFormulario;
    @Getter
    @Setter
    private Propiedad propiedadExistente;
    @Getter
    @Setter
    private Boolean mostrarDlg = Boolean.FALSE;
    @Getter
    @Setter
    private Boolean renderedPanelPropiedad = Boolean.FALSE;

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor;

    @Getter
    @Setter
    private TramiteValor tramiteValorSeleccionado;

    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSeleccionado;

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetaSelec;

    @Getter
    @Setter
    private Persona personaSelec;

    @Getter
    @Setter
    List<Propiedad> listaPropiedadPorPropietario;

    @Getter
    @Setter
    List<Propiedad> listaPropiedadSelec;

    public ControladorMatriculacionPH() {
        System.out.println("com.rm.empresarial.controlador.ControladorMatriculacion.<init>()");
        RepertorioUsuario = new RepertorioUsuario();
        nuevoIngreso = true;
        tramiteDetalleNueva = new TramiteDetalle();
        estadoBotonNuevaPropiedad = "true";
        estadoBotonEditarPropiedad = "true";
        listaTramiteValor = new ArrayList<>();
        FHR = Calendar.getInstance().getTime();
    }

    @PostConstruct
    public void postConstructor() {
        try {
            validarVariableSesionFecha();
            llenarDatos();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorMatriculacionPH.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorMatriculacionPH.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void validarVariableSesionFecha() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (session.getAttribute("fchMatSel") != null) {
            FHR = new Date();
            FHR = (Date) session.getAttribute("fchMatSel");
            try {
                llenarDatos();
            } catch (ServicioExcepcion ex) {
                Logger.getLogger(ControladorMatriculacionPH.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(ControladorMatriculacionPH.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void llenarDatos() throws ServicioExcepcion, ParseException {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);

        session.setAttribute("fchMatSel", FHR);
        listaRepertorioUsuarioFecha = MatriculacionDao.ListarPorFHRPorUsrLogPorTipo(FHR, dataManagerSesion.getUsuarioLogeado().getUsuId(), "MPH");
        if (listaRepertorioUsuarioFecha.isEmpty()) {
            JsfUtil.addErrorMessage("Repertorio No Encontrado");
        }
    }
    //    public void llenarDatosDePopup() throws ServicioExcepcion, ParseException {
    //        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    //        Calendar c = MatriculacionDao.ObtenerPorTramite(Integer.parseInt(numTramite), Integer.parseInt(repertorio)).getTdtFechaRegistro();
    //        fmt.setCalendar(c);
    //        setTramiteDetalle(MatriculacionDao.ObtenerPorTramite(Integer.parseInt(numTramite), Integer.parseInt(repertorio)));
    //        String dateFormatted = fmt.format(c.getTime());
    //        setFechaPopup(dateFormatted);
    //        setRepertorioNum(MatriculacionDao.obtenerPorNumRepertorio(getTramiteDetalle().getTdtNumeroRepertorio()));
    //
    //    }

    public void redireccionarNormal() throws ServicioExcepcion, ParseException, IOException {
//        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        numRepertorio = new Long(params.get("paramRepertorio").toString());

        Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();
        TramiteDetalle tramDet = servicioTramiteDetalle.buscarPorNumTramite(tramiteSeleccionado.getTraNumero());
        TipoTramite tipoTramit = tipoTramitServicio.buscarID(tramDet.getTdtTtrId().intValue());
//        if(tipoTramit.getTtrCodigo() == 9){
//            renderedPanelPropiedad = true;
//        }
//        else{
//            renderedPanelPropiedad = false;
//        }

        if (!tramiteSeleccionado.getTraClase().equals("J")) {
            actualizarEstadoProcesoRepUsuSel();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MatriculacionPH/NuevasMatriculas.xhtml?paramRepertorio=" + repertorioUsuarioSeleccionado.getRepNumero().getRepNumero());
        } else {

            List<TramiteDetalle> listaTramiteDet = new ArrayList<>();

            listaTramiteDet = servicioTramiteDetalle.listar_por_repertorio_tramite(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().intValue(), tramiteSeleccionado.getTraNumero());

            Long tipoTramiteId = new Long(0);
            for (TramiteDetalle tramiteDetalle1 : listaTramiteDet) {
                tipoTramiteId = tramiteDetalle1.getTdtTtrId();
            }
            TipoTramite tipoTram = tipoTramitServicio.buscarPorID(tipoTramiteId);
            if (tipoTram.getTtrCodigo() == 3) {
                listaTramDetaSelec = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().intValue(), tramiteSeleccionado.getTraNumero(), "S");
            } else {
                listaTramDetaSelec = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().intValue(), tramiteSeleccionado.getTraNumero(), "N");
            }

        }

    }

    public void redireccionar() throws ServicioExcepcion, ParseException, IOException {
//        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
//        numRepertorio = new Long(params.get("paramRepertorio").toString());

        Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();

        if (!tramiteSeleccionado.getTraClase().equals("J")) {
            actualizarEstadoProcesoRepUsuSel();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MatriculacionPH/MatriculacionPHExcel.xhtml?paramRepertorio2=" + repertorioUsuarioSeleccionado.getRepNumero().getRepNumero());
        } else {

            List<TramiteDetalle> listaTramiteDet = new ArrayList<>();

            listaTramiteDet = servicioTramiteDetalle.listar_por_repertorio_tramite(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().intValue(), tramiteSeleccionado.getTraNumero());

            Long tipoTramiteId = new Long(0);
            for (TramiteDetalle tramiteDetalle1 : listaTramiteDet) {
                tipoTramiteId = tramiteDetalle1.getTdtTtrId();
            }
            TipoTramite tipoTram = tipoTramitServicio.buscarPorID(tipoTramiteId);
            if (tipoTram.getTtrCodigo() == 3) {
                listaTramDetaSelec = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().intValue(), tramiteSeleccionado.getTraNumero(), "S");
            } else {
                listaTramDetaSelec = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().intValue(), tramiteSeleccionado.getTraNumero(), "N");
            }

        }

    }

    public void cargarPropiedades() throws ServicioExcepcion {
        listaPropiedadSelec = new ArrayList<>();

        listaPropiedadPorPropietario = servicioPropiedad.listarPropiedad_Por_Propietario(personaSelec.getPerId());
        Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();

        for (Propiedad propiedad : listaPropiedadPorPropietario) {
            //consulto registro en tramite valor
            TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(tramiteSeleccionado.getTraNumero(), propiedad.getPrdPredio(), propiedad.getPrdCatastro());

            if (tramiteValorPorPropiedad != null) {
                listaPropiedadSelec.add(propiedad);
            }

        }
    }

    public void guardarTrmVlr() throws ServicioExcepcion {
//        eliminarTrmVlr();
        Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();

        for (Propiedad propiedad : listaPropiedadPorPropietario) {
            //consulto registro en tramite valor
            TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(tramiteSeleccionado.getTraNumero(), propiedad.getPrdPredio(), propiedad.getPrdCatastro());
            if (listaPropiedadSelec.contains(propiedad)) { //si propiedad fue seleccionada
                if (tramiteValorPorPropiedad == null) { //si no existe tramite valor, creo nuevo
                    //si ya existe un tramite valor lo elimino para crear
                    crearTramiteValor(propiedad, tramiteSeleccionado);
                }//si existe tramite valor, no hago nada
            } else { //si la propiedad fue deseleccionada
                if (tramiteValorPorPropiedad != null) { //si ya existe tramite valor, lo elimino
                    servicioTramiteValor.remove(tramiteValorPorPropiedad);
                }//si no existe tramite valor, no hago nada
            }
        }
    }

    public void terminarAgregarPrpdLegal() throws IOException {
        actualizarEstadoProcesoRepUsuSel();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MatriculacionPH/NuevasMatriculas.xhtml?paramRepertorio=" + repertorioUsuarioSeleccionado.getRepNumero().getRepNumero());
    }

    public void terminarAgregarPrpdLegalExcel() throws IOException {
        actualizarEstadoProcesoRepUsuSel();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MatriculacionPH/MatriculacionPHExcel.xhtml?paramRepertorio2=" + repertorioUsuarioSeleccionado.getRepNumero().getRepNumero());
    }

//    public void eliminarTrmVlr() throws ServicioExcepcion {
//        Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();
//
//        for (Propiedad propiedad : listaPropiedadPorPropietario) {
////            List<TramiteValor> listaTramiteValorPorPropiedad = servicioTramiteValor.ListarPor_Propiedad(new Long(propiedad.getPrdCatastro()));
////            for (TramiteValor tramiteValor : listaTramiteValorPorPropiedad) {
////                servicioTramiteValor.remove(tramiteValor);
////            }
//            int numTrmVlrEliminados = servicioTramiteValor.eliminarPor_Propiedad(new Long(propiedad.getPrdCatastro()),tramiteSeleccionado.getTraNumero());
//            System.out.println("Se borraron: " + numTrmVlrEliminados + " registros de tramite valor");
//        }
//    }
    public void crearTramiteValor(Propiedad propiedad, Tramite tramite) throws ServicioExcepcion {
        Repertorio repertorioSeleccionado = repertorioUsuarioSeleccionado.getRepNumero();

//----        campos requeridos-----
        TramiteValor nuevoTrmVlr = new TramiteValor();
        nuevoTrmVlr.setTtrId(servicioTipoTramite.buscarPorDescripcion(repertorioSeleccionado.getRepTtrDescripcion()));
        nuevoTrmVlr.setTraNumero(repertorioSeleccionado.getTraNumero());
        nuevoTrmVlr.setTrvValor(BigDecimal.ZERO);
        nuevoTrmVlr.setTrvEstado("A");
        nuevoTrmVlr.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        nuevoTrmVlr.setTrvNumCatastro(propiedad.getPrdCatastro());
        nuevoTrmVlr.setTraNumPredio(propiedad.getPrdPredio());
        //-----------
        //llenando datos parroquia-----
        nuevoTrmVlr.setTrvParId(tramite.getTraParId().longValue());
        nuevoTrmVlr.setTrvParNombre(tramite.getTraParNombre());
        //-------------------------------
        nuevoTrmVlr.setTrvFHR(Calendar.getInstance().getTime());
        nuevoTrmVlr.setTrvAccion(0);
        nuevoTrmVlr.setTrvIva(BigDecimal.ZERO);
        nuevoTrmVlr.setTrvCantidad(BigDecimal.ZERO);
        nuevoTrmVlr.setTtrvPorIva(institucionServicio.porcentajeIva());
        servicioTramiteValor.create(nuevoTrmVlr);

//        Boolean esValorRepetido = false;
//        for (TramiteValor tramiteValor : listaTramiteValor) {
//            if (tramiteValor.getTrvNumCatastro().equals(tramiteValorSeleccionado.getTrvNumCatastro())) {
//                esValorRepetido = true;
//                JsfUtil.addErrorMessage("Error al guardar. Número de Catastro repetido");
//                break;
//            }
//            if (tramiteValor.getTraNumPredio().equals(tramiteValorSeleccionado.getTraNumPredio())) {
//                esValorRepetido = true;
//                JsfUtil.addErrorMessage("Error al guardar. Número de Predio repetido");
//                break;
//            }
//        }
//        if (!esValorRepetido) {
//            servicioTramiteValor.create(tramiteValorSeleccionado);
//            listaTramiteValor.add(tramiteValorSeleccionado);
//            JsfUtil.addSuccessMessage("Ingresado");
//        }
//        tramiteValorSeleccionado = new TramiteValor();
    }

    public void actualizarEstadoProcesoRepUsuSel() {

        try {
            if (repertorioUsuarioSeleccionado.getRpuEstadoProceso().equals("ACTIVO")) {
                repertorioUsuarioSeleccionado.setRpuEstadoProceso("PROCESO");
                servicioRepertorioUsuario.edit(repertorioUsuarioSeleccionado);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cargarListaParroquia() throws ServicioExcepcion, ServicioExcepcion, ParseException, ParseException {
        setListaParroquia(MatriculacionDao.ListarParroquia());
    }

    public void guardar() throws IOException, ParseException, ServicioExcepcion {
        llenarDatosRepertorioUsuario();
    }

    public void llenarDatosRepertorioUsuario() throws ServicioExcepcion, ParseException, IOException {
        Usuario usuarioAsignado = CargaLaboralUtil.obtenerCargaLaboral("INS", "Matriculacion", 1);

        actualizarTramiteValor();
        getRepertorioUsuario().setUsuId(usuarioAsignado);
        getRepertorioUsuario().setRpuEstado("A");
        getRepertorioUsuario().setRpuUser(getDataManagerSesion().getUsuarioLogeado().getUsuLogin());
        getRepertorioUsuario().setRpuFHR(Calendar.getInstance().getTime());
        getRepertorioUsuario().setRpuTipo("INS");
        getRepertorioUsuario().setRepNumero(repertorioUsuarioSeleccionado.getRepNumero());

        personaSeleccionada = new Persona();
        setPersonaSeleccionada(MatriculacionDao.ObtenerPersonaPorIdUsuario(getRepertorioUsuario().getUsuId().getUsuId()));

        Boolean existeRepertorioUsuario = RepertorioUsuarioDao.existeRepertorioUsuario(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero(),
                usuarioAsignado.getUsuId(), "INS");
        if (!existeRepertorioUsuario) {
            create();
        }

        setEstadoBotonNuevaPropiedad("false");

        redireccionarNuevaPropiedad();
    }

    public void actualizarTramiteValor() throws ServicioExcepcion, ParseException {
        //actualizo tramite valor seleccionado
        servicioTramiteValor.edit(tramiteValorSeleccionado);

    }

    public void create() {
        persist(JsfUtil.PersistAction.CREATE);
    }

    private void persist(JsfUtil.PersistAction persistAction) {
        if (getRepertorioUsuario() != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {
                    if (nuevoIngreso) {
//                        System.out.println("Ingresando nuevo...");
                        RepertorioUsuarioDao.create(RepertorioUsuario);
                    }

                }

            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public void limpiar() {

    }

    public void ObtenerPorUsuario() throws ServicioExcepcion, ParseException {
        personaSeleccionada = new Persona();
        setPersonaSeleccionada(MatriculacionDao.ObtenerPersonaPorIdUsuario(Long.parseLong(getRepertorioUsuario().getRpuUser())));
        setNombrePersona(getPersonaSeleccionada().getPerApellidoPaterno() + getPersonaSeleccionada().getPerApellidoMaterno() + getPersonaSeleccionada().getPerNombre());
        setNumeroTramite(getTramiteDetalle().getTraNumero().getTraNumero());
    }

    public void setearCamposdeFormulario() {
        setNumTramite("");
        setRepertorio("");
        setContrato("");
        setTramiteDetalle(null);
        setFechaPopup(null);
    }

    public void redireccionarNuevaPropiedad() throws ServicioExcepcion, IOException {
        System.out.print("Iniciado Obtencion Matricula");
        //obtengo la secuencia
        secuenciaControlador.generarSecuenciaMatricula("MAT");

        //obtengo el siguiente valor de la secuencia
        setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
        int auxSecuencia = getSecuencia().getSecActual();
        getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
        Long idGenerado = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
        secuenciaServicio.guardar(getSecuencia());

        //obtengo datos de repertorio seleccionado//
        setMatricula(idGenerado);

        setCatastro(tramiteValorSeleccionado.getTrvNumCatastro().toString());
        setPredio(tramiteValorSeleccionado.getTraNumPredio().toString());

        //consulto si existe una propiedad con el catastro y predio
        setPropiedadExistente(MatriculacionDao.consultarExistenciaPropiedad(catastro, predio));
        //Cargo los datos para el siguiente formulario

        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MatriculacionPH/NuevasMatriculas.xhtml?matricula=" + getMatricula() + "&catastro=" + getCatastro() + "&predio=" + getPredio() + "&numRepertorio=" + repertorioUsuarioSeleccionado.getRepNumero().getRepNumero() + "&numTramite=" + repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraNumero() + "&fecha=" + repertorioUsuarioSeleccionado.getRepNumero().getRepFHR());

    }

    public void terminarProcesoMatriculacion() throws ServicioExcepcion, IOException, ParseException {

        Usuario usuariologeado = dataManagerSesion.getUsuarioLogeado();
        Tramite tramiteSelec = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();

        Boolean todosRevisados = false;
        Integer contador = 0;

        listaTramiteValor = servicioTramiteValor.ListarPor_NumTramite_Estado(tramiteSelec);
        System.out.println("lista tramite valor tamaño: " + listaTramiteValor.size());
        for (TramiteValor tramiteValor : listaTramiteValor) {
            if (tramiteValor.getTrvAccion() != null && tramiteValor.getTrvAccion() > 0) {
                contador++;
            }
        }
        if (contador == listaTramiteValor.size()) {
            todosRevisados = true;
        }

        //Verificar si todas las propiedades estan validadas
        List<RepertorioPropiedad> listRepProp = new ArrayList<>();
        List<TmpAlicuota> TmpAlicValidar = new ArrayList<>();
        BigDecimal totalTmpAlic = BigDecimal.ZERO;
        String matriculas = "";
        listRepProp = repertorioPropiedadDao.buscarPorNumRepertorio(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero());
        int aux = 0;
        int auxPadre = 0;
        //Boolean propiedadesValidadas = false;
        Boolean alicuotasValidadas = false;
        for (RepertorioPropiedad repertorioPropiedad : listRepProp) {
            if (repertorioPropiedad.getPrdMatricula().getPrdPadreMatricula() != null) {
//                 if (repertorioPropiedad.getPrdMatricula().getPrdValidacion() != null) {
//                if (repertorioPropiedad.getPrdMatricula().getPrdValidacion().equals("V")) {
//                    aux++;
//                }
//
//            }

            } else {
                auxPadre++;
                TmpAlicValidar.clear();
                totalTmpAlic = BigDecimal.ZERO;
                TmpAlicValidar = tmpAlicuotaDao.listarPorMatriculaPadre_SinEstadoTP(repertorioPropiedad.getPrdMatricula().getPrdMatricula());
                for (TmpAlicuota tmpAlicuota : TmpAlicValidar) {
                    if (tmpAlicuota.getTalAlicuota() != null) {
                        totalTmpAlic = totalTmpAlic.add(tmpAlicuota.getTalAlicuota());
                    }

                }
                if (totalTmpAlic.compareTo(BigDecimal.valueOf(100)) == 0) {
                    aux++;
                } else {
                    matriculas = matriculas + repertorioPropiedad.getPrdMatricula().getPrdMatricula() + " ";
                }

            }

        }
//        if (aux == listRepProp.size() - auxPadre) {
//            propiedadesValidadas = true;
//        }
        if (aux == auxPadre) {
            alicuotasValidadas = true;
        }
        //OBTENER LAS PROPIEDADES PADRE
        List<Propiedad> listPropPadre = new ArrayList<>();
        for (RepertorioPropiedad repertorioPropiedad : listRepProp) {
            if (repertorioPropiedad.getPrdMatricula().getPrdPadreMatricula() == null) {
                if (!listPropPadre.contains(repertorioPropiedad.getPrdMatricula())) {
                    listPropPadre.add(repertorioPropiedad.getPrdMatricula());
                }

            }
        }
        //////////////////////////////

        if (todosRevisados) {
            if (alicuotasValidadas) {
                try {
                    //GUARDAR VERSION PH
                    List<TmpAlicuota> listTmpAlic = new ArrayList<>();
                    VersionPH versionPH;
                    for (Propiedad propiedad : listPropPadre) {
                        int numeroVersion = 0;
                        VersionPH ultimaVersion = versionPHDao.buscarUltimaVersionPorMatricula(propiedad.getPrdMatricula());
                        if (ultimaVersion != null) {
                            numeroVersion = ultimaVersion.getVersionPHPK().getVphVersion() + 1;
                        } else {
                            numeroVersion = 1;
                        }
                        listTmpAlic.clear();
                        listTmpAlic = tmpAlicuotaDao.listarPorMatriculaPadre_EstadoDistintoE(propiedad.getPrdMatricula());
                        BigInteger secuencial = BigInteger.ZERO;
                        for (TmpAlicuota tmpAlicuota : listTmpAlic) {
                            secuencial = secuencial.add(BigInteger.ONE);
                            versionPH = new VersionPH();
                            versionPH.setVersionPHPK(new VersionPHPK(secuencial, propiedad.getPrdMatricula(), numeroVersion));
                            versionPH.setTalId(tmpAlicuota);
                            versionPH.setVphFHR(Calendar.getInstance().getTime());
                            versionPH.setVphpUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            versionPHDao.create(versionPH);
                        }

                    }

                } catch (Exception e) {
                    System.out.println(e);
                }

                //ACTUALIZANDO REPERTORIOS USUARIO
                int numRepUsuActualizados = 0;

                numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPor_Tram_Usr_Tipo(repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraNumero(),
                        usuariologeado.getUsuId(), "MPH");
                System.out.println("# repertorios actualizados" + numRepUsuActualizados);

                //ACTUALIZANDO EL TRAMITE
                tramiteSelec.setTraEstado("INS");
                servicioTramite.edit(tramiteSelec);
                tramiteUtil.insertarTramiteAccion(tramiteSelec, tramiteSelec.getTraNumero().toString(),
                        "Actualización del estado de Tramite a 'INS'",
                        "MPH", tramiteSelec.getTraEstadoRegistro(), null);
                Usuario usuarioAsignado = null;
                String tipo = "";
                try {
                    switch (tramiteSelec.getTraClase()) {
                        case "J":

                            TipoTramite tipoTramite = servicioTipoTramite.buscarPorDescripcion(repertorioUsuarioSeleccionado.getRepNumero().getRepTtrDescripcion());

                            switch (tipoTramite.getTtrCodigo()) {
                                case 1:
                                    tipo = "INP";
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("INP", "PROHIBICION", 1);
                                    break;
                                case 2:
                                    tipo = "IND";
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("IND", "DEMANDA", 1);
                                    break;
                                case 3:
                                    tipo = "INA";
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("INA", "SENTENCIA", 1);
                                    break;
                                case 6:
                                    tipo = "INC";
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("INC", "CANCELACION", 1);
                                    break;

                            }
                            break;
                        default:
                            TipoTramite tipoTram = servicioTipoTramite.buscarPorDescripcion(repertorioUsuarioSeleccionado.getRepNumero().getRepTtrDescripcion());
                            if (tipoTram.getTtrCodigo() != null) {
                                if (tipoTram.getTtrCodigo() == 6) {
                                    tipo = "INC";
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("INC", "CANCELACION", 1);

                                } else {
                                    tipo = "INS";
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("INS", "Inscripcion", 1);
                                }
                            } else {
                                tipo = "INS";
                                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("INS", "Inscripcion", 1);
                            }
                    }

                    for (RepertorioUsuario repertorio1 : listaRepertorioUsuarioFecha) {
                        if (repertorio1.getRepNumero().getTraNumero().getTraNumero().equals(tramiteSelec.getTraNumero())) {

                            RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();

                            nuevoRepUsu.setRpuTipo(tipo);
                            nuevoRepUsu.setUsuId(usuarioAsignado);
                            nuevoRepUsu.setRpuEstado("A");
                            nuevoRepUsu.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            nuevoRepUsu.setRpuFHR(Calendar.getInstance().getTime());
                            nuevoRepUsu.setRepNumero(repertorio1.getRepNumero());
                            servicioRepertorioUsuario.create(nuevoRepUsu);
                            tramiteUtil.insertarTramiteAccion(nuevoRepUsu.getRepNumero().getTraNumero(),
                                    nuevoRepUsu.getRepNumero().getRepNumero().toString(),
                                    "Repertorio Usuario " + nuevoRepUsu.getRepNumero().getRepNumero()
                                    + " asignado al usuario: " + usuarioAsignado.getUsuLogin(),
                                    "MPH", nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                    usuarioAsignado.getUsuLogin());

                        }
                    }

                } catch (Exception e) {
                    Logger.getLogger(ControladorNuevasMatriculas.class.getName()).log(Level.SEVERE, null, e);
                    JsfUtil.addErrorMessage("Error al generar nuevos repertorios usuario con estado 'INS'");
                }
                try {
                    listaRepertorioUsuarioFecha = MatriculacionDao.ListarPorFHRPorUsrLog(FHR, dataManagerSesion.getUsuarioLogeado().getUsuId());
                } catch (Exception e) {
                    Logger.getLogger(ControladorNuevasMatriculas.class.getName()).log(Level.SEVERE, null, e);
                }
            } else {
                JsfUtil.addErrorMessage("El total de alicuotas no suma 100. Matrículas: " + matriculas);
            }

        } else {
            JsfUtil.addErrorMessage("Faltan " + (listaTramiteValor.size() - contador) + " propiedades por revisar");
        }
    }

}
