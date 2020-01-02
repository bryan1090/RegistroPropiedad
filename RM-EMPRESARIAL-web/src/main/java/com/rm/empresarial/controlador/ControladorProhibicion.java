/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.dao.InscripcionDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.TipoComparecienteDao;
import com.rm.empresarial.dao.TipoTramiteComparecienteDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.dao.TramiteValorDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.TipoCompareciente;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorProhibicion")
@ViewScoped
public class ControladorProhibicion implements Serializable {

   @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Inject
    private PersonaUtil personaUtil;

    @Getter
    @Setter
    private List<Repertorio> listaRepertorioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramiteDetalle = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoCompareciente> listaTipoCompareciente = new ArrayList<>();

    @Getter
    @Setter
    private List<TipoTramiteCompareciente> listaTipoTramiteCompareciente = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropiedad = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadPorMatricula = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropiedadesSeleccionadas = new ArrayList<>();

    @Getter
    @Setter
    private Date fecha;

    @Getter
    @Setter
    private Boolean propiedadSeleccionada;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private String tipoComparecienteId;

    @Getter
    @Setter
    private String nombreCompareciente;

    @Getter
    @Setter
    private String apellidoPaternoCompareciente;

    @Getter
    @Setter
    private String apellidoMaternoCompareciente;

    @Getter
    @Setter
    private String numeroTramite;

    @Getter
    @Setter
    private String numeroRepertorio;

    @Getter
    @Setter
    private String descripcionContrato;

    @Getter
    @Setter
    private Boolean deshabilitarBoton;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalle;

    @Getter
    @Setter
    private Repertorio repertorio;

    @Getter
    @Setter
    private TipoTramite tipoTramite;
    
    @Getter
    @Setter
    private Boolean renderedPanel;
    
    @Getter
    @Setter
    private Boolean renderedPanelTab;

    @EJB
    private RepertorioDao repertorioDao;

    @EJB
    private InscripcionDao inscripcionDao;

    @EJB
    private TipoTramiteDao tipoTramiteDao;

    @EJB
    private TramiteDetalleDao tramiteDetalleDao;

    @EJB
    private TipoTramiteComparecienteDao tipoTramiteComparecienteDao;

    @EJB
    private TipoComparecienteDao tipoComparecienteDao;

    @EJB
    private PropiedadDao propieadadDao;

    @EJB
    private TramiteValorDao tramiteValorDao;

    @EJB
    private TramiteValorServicio servicioTramiteValor;

    @EJB
    private InstitucionServicio institucionServicio;
    public ControladorProhibicion() {
        propiedadSeleccionada = false;
        tramiteDetalle = new TramiteDetalle();
        tipoTramite = new TipoTramite();
        repertorio = new Repertorio();
        deshabilitarBoton = true;
        renderedPanel = true;
        renderedPanelTab = false;
    }

    public void llenarDatos() throws ServicioExcepcion, ParseException {
        listaPropiedadPorMatricula.clear();
        setListaRepertorioFecha(inscripcionDao.ListarRepertorioPorFHRPorUsrLog_tipo(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId(),"INP"));
        listaTramiteDetalle.clear();
        deshabilitarBoton = true;

    }

    public void onRowSelect(SelectEvent event) throws ServicioExcepcion, ParseException {

        limpiarCampos();
        listaPropiedadPorMatricula.clear();
        numeroRepertorio = "" + (((Repertorio) event.getObject()).getRepNumero()).toString() + "";
        numeroTramite = "" + (((Repertorio) event.getObject()).getTraNumero().getTraNumero()).toString() + "";
        descripcionContrato = "" + (((Repertorio) event.getObject()).getRepTtrDescripcion()) + "";
        repertorio = repertorioDao.encontrarRepertorioPorId(numeroRepertorio);
        cargarTablaComparecientes();
        listarPropiedadPorMatricula();
        deshabilitarBoton = false;

    }

    public void limpiarCampos() {
        numeroRepertorio = "";
        numeroTramite = "";
        descripcionContrato = "";
    }

    public void cargarTablaComparecientes() throws ServicioExcepcion {
        try {
            listaTramiteDetalle.clear();
            listaTipoTramiteCompareciente.clear();
            Repertorio repert = new Repertorio();
            repert = repertorioDao.encontrarRepertorioPorId(numeroRepertorio);
            List<TramiteDetalle> listaPreviaTramiteDetalle = new ArrayList<>();
            listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                    repert.getTraNumero().getTraNumero(), repert.getRepTtrId(), Long.valueOf(numeroRepertorio));

            listaTipoCompareciente = tipoTramiteComparecienteDao.listarTipoComparecientePorTipoTramite(Long.valueOf(repert.getRepTtrId()));
//            for (TramiteDetalle tramiteDetalle : listaPreviaTramiteDetalle) {
//                TipoTramiteCompareciente tipoTramCompareciente = new TipoTramiteCompareciente();
//                tipoTramCompareciente = tipoTramiteComparecienteDao.buscarPorId(tramiteDetalle.getTtcId().getTtcId());
//                if (tipoTramCompareciente.getTtcPropietario().equals("N")) {
//                    listaTramiteDetalle.add(tramiteDetalle);
//                    listaTipoTramiteCompareciente.add(tipoTramCompareciente);
//
//                }
//            }

            /*
        List<TipoCompareciente> listaPreviaTipoCompareciente = new ArrayList<>();   
        listaPreviaTipoCompareciente = tipoTramiteComparecienteDao.listarTipoComparecientePorTipoTramite(Long.valueOf(repert.getRepTtrId()));
        for (TipoCompareciente tipoCompareciente : listaPreviaTipoCompareciente) {
            TipoTramiteCompareciente tipoTramCompareciente = new TipoTramiteCompareciente();
            for (TramiteDetalle tramDetalle : listaPreviaTramiteDetalle) {
            tipoTramCompareciente = tipoTramiteComparecienteDao.buscarPorId(tramDetalle.getTtcId().getTtcId());
           if(tipoTramCompareciente.getTtcPropietario().equals("N")){
            listaTipoCompareciente.add(tipoCompareciente);
        } 
            }
        }
             */
        } catch (Exception e) {
        }

    }

    public void buscarPersona() throws IOException, ServicioExcepcion {

        if (identificacion != null) {
            getTramitesControladorBb().setIdentificacion(identificacion);

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

    public void nombrePersona() {
        getTramitesControladorBb().setNombrePersona(getTramitesControladorBb().getPersona().getPerApellidoPaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerApellidoMaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerNombre().trim());
        String Nombre = getTramitesControladorBb().getNombrePersona();
        getTramitesControladorBb().setNombrePersona(Nombre.replace("null", ""));
        getTramitesControladorBb().setEstado(Boolean.TRUE);

    }

    public void guardarTramiteDetalle() throws ServicioExcepcion, ParseException {
        try {
            TipoCompareciente tipoComparec = new TipoCompareciente();
            tipoComparec = tipoComparecienteDao.encontrarTipoComparecientePorId(tipoComparecienteId);
            TipoTramiteCompareciente tipoTramCompareciente = new TipoTramiteCompareciente();
            tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numeroTramite), descripcionContrato);
            tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
            tipoTramCompareciente = tipoTramiteComparecienteDao.buscarPorIdTramitePorTipoCompareciente(
                    tipoTramite.getTtrId(), tipoComparec.getTpcId());
            short idLibro = tipoTramite.getTplId().getTplId().shortValue();
            TramiteDetalle tramDetalle = new TramiteDetalle();
            tramDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(repertorio.getTraNumero().getTraNumero(), tipoTramite.getTtrDescripcion());

            TramiteDetalle tramiteDetalle = new TramiteDetalle();
            getTramitesControladorBb().setPersonaConyugue(getTramitesControladorBb().getPersona().getPerIdConyuge());
            tramiteDetalle.setPerId(getTramitesControladorBb().getPersona());
            tramiteDetalle.setTdtConyuguePerId(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerId());
            tramiteDetalle.setTdtConyuguePerNombre(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerNombre() + " " + getTramitesControladorBb().getPersona().getPerIdConyuge().getPerApellidoPaterno());
            tramiteDetalle.setTdtConyuguePerTipoIden(getTramitesControladorBb().getPersona().getPerIdConyuge().getPerTipoIdentificacion());
            tramiteDetalle.setTdtEstado("A");
            tramiteDetalle.setTdtFHR(Calendar.getInstance());
            tramiteDetalle.setTdtFechaRegistro(Calendar.getInstance());
            tramiteDetalle.setTdtNumeroRepertorio(Integer.valueOf(numeroRepertorio));
            tramiteDetalle.setTdtPerApellidoPaterno(getTramitesControladorBb().getPersona().getPerApellidoPaterno());
            tramiteDetalle.setTdtPerApellidoMaterno(getTramitesControladorBb().getPersona().getPerApellidoMaterno());
            tramiteDetalle.setTdtPerIdentificacion(getTramitesControladorBb().getPersona().getPerIdentificacion());
            tramiteDetalle.setTdtPerNombre(getTramitesControladorBb().getPersona().getPerNombre());
            tramiteDetalle.setTdtPerTipoContribuyente(" ");
            tramiteDetalle.setTdtPerTipoIdentificacion(getTramitesControladorBb().getPersona().getPerTipoIdentificacion());
            tramiteDetalle.setTdtTpcCodigo(tipoComparec.getTpcCodigo());
            tramiteDetalle.setTdtTpcDescripcion(tipoComparec.getTpcDescripcion());
            tramiteDetalle.setTdtTpcId(tipoComparec.getTpcId());
            tramiteDetalle.setTdtTplId(idLibro);
            tramiteDetalle.setTdtTtrDescripcion(tipoTramite.getTtrDescripcion());
            tramiteDetalle.setTdtTtrId(tipoTramite.getTtrId());
            tramiteDetalle.setTdtUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            tramiteDetalle.setTraNumero(repertorio.getTraNumero());
            tramiteDetalle.setTtcId(tipoTramCompareciente);
            tramiteDetalle.setTdtTplDescripcion(tipoTramite.getTtrDescripcion());
            tramiteDetalle.setTdtParId(tramDetalle.getTdtParId());
            tramiteDetalle.setTdtParNombre(tramDetalle.getTdtParNombre());
            tramiteDetalle.setTdtCatastro(tramDetalle.getTdtCatastro());
            tramiteDetalle.setTdtPredio(tramDetalle.getTdtPredio());
            tramiteDetalleDao.guardarDetalleTramite(tramiteDetalle);

            cargarTablaComparecientes();
            System.out.println("tramite detalle guardado");

        } catch (Exception e) {
            System.out.println("com.rm.empresarial.controlador.ControladorInscripcion.guardarTramiteDetalle()");
            System.err.println(e);
        }

    }

    public void seleccionarPropiedades(String identificacion) throws ServicioExcepcion {
        try {
            listaPropiedad = propieadadDao.listarPropiedadPorMatriculaPorIdentificacion(identificacion);
            for (Propiedad propiedad : listaPropiedad) {
                TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(Long.valueOf(numeroTramite), propiedad.getPrdPredio(), propiedad.getPrdCatastro());
                if (tramiteValorPorPropiedad != null) {
                    listaPropiedadesSeleccionadas.add(propiedad);
                }
            }

        } catch (Exception e) {
        }
    }

    public void guardarPropiedades() throws ServicioExcepcion {
        try {
            tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numeroTramite), descripcionContrato);
            tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());

            for (Propiedad propiedad : listaPropiedad) {
                //consulto registro en tramite valor
                TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(Long.valueOf(numeroTramite), propiedad.getPrdPredio(), propiedad.getPrdCatastro());
                if (listaPropiedadesSeleccionadas.contains(propiedad)) { //si propiedad fue seleccionada
                    if (tramiteValorPorPropiedad == null) { //si no existe tramite valor, creo nuevo
                        //si ya existe un tramite valor lo elimino para crear
                        TramiteValor tramiteValor = new TramiteValor();
                        tramiteValor.setTraNumero(tramiteDetalle.getTraNumero());
                        tramiteValor.setTraNumPredio(propiedad.getPrdPredio());
                        tramiteValor.setTrvNumCatastro(propiedad.getPrdCatastro());
                        tramiteValor.setTrvEstado("A");
                        tramiteValor.setTrvFHR(Calendar.getInstance().getTime());
                        tramiteValor.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        tramiteValor.setTtrId(tipoTramite);
                        tramiteValor.setTrvValor(new BigDecimal(0));
                        tramiteValor.setTrvParId(tramiteDetalle.getTdtParId());
                        tramiteValor.setTrvParNombre(tramiteDetalle.getTdtParNombre());
                        tramiteValor.setTrvAccion(0);
                        tramiteValor.setTrvIva(BigDecimal.ZERO);
                        tramiteValor.setTrvCantidad(BigDecimal.ZERO);
                        tramiteValor.setTtrvPorIva(institucionServicio.porcentajeIva());
                        tramiteValorDao.guardar(tramiteValor);

                    }//si existe tramite valor, no hago nada
                } else { //si la propiedad fue deseleccionada
                    if (tramiteValorPorPropiedad != null) { //si ya existe tramite valor, lo elimino
                        servicioTramiteValor.remove(tramiteValorPorPropiedad);
                    }//si no existe tramite valor, no hago nada
                }
            }
            PrimeFaces current = PrimeFaces.current();
            current.executeScript("PF('dlgPropiedades').hide();");
            listarPropiedadPorMatricula();

//        for (Propiedad listaPropiedadesSeleccionada : listaPropiedadesSeleccionadas) {            
//            TramiteValor tramiteValor = new TramiteValor();
//            tramiteValor.setTraNumero(tramiteDetalle.getTraNumero());
//            tramiteValor.setTraNumPredio(Long.valueOf(listaPropiedadesSeleccionada.getPrdPredio()));
//            tramiteValor.setTrvNumCatastro(Long.valueOf(listaPropiedadesSeleccionada.getPrdCatastro()));
//            tramiteValor.setTrvEstado("A");
//            tramiteValor.setTrvFHR(Calendar.getInstance().getTime());
//            tramiteValor.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
//            tramiteValor.setTtrId(tipoTramite);
//            tramiteValor.setTrvValor(new BigDecimal(0));
//            tramiteValorDao.guardar(tramiteValor);            
//        }
//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null, new FacesMessage("", "Propiedad guardada"));
        } catch (Exception e) {
        }

    }

    public void listarPropiedadPorMatricula() throws ServicioExcepcion {
        try {
            tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(Long.parseLong(numeroTramite), descripcionContrato);
            tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
            listaPropiedadPorMatricula.clear();
            listaPropiedadPorMatricula = propieadadDao.listarPropiedadPorMatriculaPorTramitePorPredioPorCatastro(Long.valueOf(numeroTramite), tipoTramite.getTtrId());

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public void eliminarCompareciente(Long id) throws ServicioExcepcion, ParseException {
        try {
            TramiteDetalle tramiteDetalle = new TramiteDetalle();
            tramiteDetalle = tramiteDetalleDao.buscarPorId(Long.valueOf(id));
            tramiteDetalle.setTdtEstado("I");
            tramiteDetalleDao.edit(tramiteDetalle);
            cargarTablaComparecientes();
          


        } catch (Exception e) {
            System.out.println(e);

        }

    }
    
    public void mostrarPanel(){
        renderedPanel = false;
        renderedPanelTab = true;
    }

}
