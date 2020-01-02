/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.ActaDao;
import com.rm.empresarial.dao.ActaRazonDao;
import com.rm.empresarial.dao.CantonDao;
import com.rm.empresarial.dao.InscripcionDao;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.RazonDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TipoLibroDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.ActaRazon;
import com.rm.empresarial.modelo.Canton;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Prueba
 */
@Named(value = "controladorRazon")
@ViewScoped
public class ControladorRazon implements Serializable {

    @Getter
    @Setter
    private List<Repertorio> listaRepertorioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<RepertorioPropiedad> listaRepertorioPropiedad = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramiteDetalle = new ArrayList<>();

    @Getter
    @Setter
    private List<Propiedad> listaPropiedad = new ArrayList<>();

    @Getter
    @Setter
    private Boolean deshabilitarBotonGuardar;

    @Getter
    @Setter
    private Date FHR;

    @Getter
    @Setter
    private String gravamenes;

    @Getter
    @Setter
    private String razonInscripcion;

    @Inject
    @Getter
    @Setter
    private DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private boolean renderedTxtEdit;

    @Getter
    @Setter
    private String numRepertorio;

    @Getter
    @Setter
    private String tramite;

    @Getter
    @Setter
    private String contrato;

    @Getter
    @Setter
    private Canton canton;

    @Getter
    @Setter
    private Institucion institucion;

    @Getter
    @Setter
    private TipoTramite tipoTramite;

    @Getter
    @Setter
    private TipoLibro tipoLibro;

    @Getter
    @Setter
    private Repertorio repertorio;

    @Getter
    @Setter
    private Date repertorioFecha;

    @Getter
    @Setter
    private Secuencia secuencia;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private Usuario usuarioAsignado;

    @EJB
    private RazonDao razonDao;
    @EJB
    private CantonDao cantonDao;
    @EJB
    private InstitucionDao institucionDao;
    @EJB
    private TipoTramiteDao tipoTramiteDao;
    @EJB
    private TipoLibroDao tipoLibroDao;
    @EJB
    private RepertorioPropiedadDao repertorioPropiedadDao;
    @EJB
    private PropiedadDao propiedadDao;
    @EJB
    private RepertorioDao repertorioDao;
    @EJB
    private TramiteDetalleDao tramiteDetalleDao;
    @EJB
    private SecuenciaServicio secuenciaServicio;
    @EJB
    private ActaRazonDao actaRazonDao;
    @EJB
    private ActaDao actaDao;
    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;
    @EJB
    private TramiteDao tramiteDao;
    @EJB
    private TramiteServicio servicioTramite;
    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;
    @Inject
    private TramiteUtil tramiteUtil;

    public ControladorRazon() {
        canton = new Canton();
        institucion = new Institucion();
        tipoLibro = new TipoLibro();
        tipoTramite = new TipoTramite();
        repertorio = new Repertorio();
        secuencia = new Secuencia();
        usuarioAsignado = new Usuario();
        deshabilitarBotonGuardar = true;
        renderedTxtEdit = false;
        FHR=Calendar.getInstance().getTime();

    }
    
    @PostConstruct
    public void postConstructor()
    {
        try {
            llenarDatos();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorRazon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorRazon.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarDatos() throws ServicioExcepcion, ParseException {

        setListaRepertorioFecha(razonDao.listaRepertorioPorFHRPorUsr(FHR, dataManagerSesion.getUsuarioLogeado().getUsuId()));
        if (!listaRepertorioFecha.isEmpty()) {

        } else {

            limpiarCamposTxtEdit();
            renderedTxtEdit = false;
        }
    }

    public void limpiarCamposTxtEdit() {

        gravamenes = "";
        razonInscripcion = "";
        numRepertorio = "";
        tramite = "";
        contrato = "";        
    }

    public void onRowSelect(SelectEvent event) throws ServicioExcepcion, ParseException {

        numRepertorio = "" + (((Repertorio) event.getObject()).getRepNumero()).toString() + "";
        tramite = "" + (((Repertorio) event.getObject()).getTraNumero().getTraNumero()).toString() + "";
        contrato = "" + (((Repertorio) event.getObject()).getRepTtrDescripcion()) + "";

        txtRazonInscripcion();
        renderedTxtEdit = true;
        deshabilitarBotonGuardar = true;
    }

    public void txtRazonInscripcion() throws ServicioExcepcion, ParseException {
        try {
            listaRepertorioPropiedad.clear();
            institucion = institucionDao.obtenerInstitucion();
            canton = cantonDao.encontrarCantonPorId(institucion.getInsCanId().toString());
            tipoTramite = tipoTramiteDao.buscarPorDescripcion(contrato);
            tipoLibro = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
            listaRepertorioPropiedad = repertorioPropiedadDao.buscarPorNumRepertorio(Long.valueOf(numRepertorio));
            repertorio = repertorioDao.encontrarRepertorioPorId(numRepertorio);

            String titulo = "<strong>Registro de la Propiedad del Cantón "
                    + canton.getCanNombre() + "</strong><br/>"
                    + "<strong>Razón de Inscripción </strong><br/><br/>";

            String linea1 = "Con esta fecha queda inscrita la presente escritura en el: <br/><br/>";

            String linea2 = "REGISTRO DE " + contrato
                    + ", " + "tomo " + tipoLibro.getTplTomo() + ", repertorio(s) -" + numRepertorio + "<br/><br/>";

            String txtMatriculas = "Matrículas Asignadas.-<br/>";
            String lineasTodasMatriculas = "";
            for (RepertorioPropiedad repPropiedad : listaRepertorioPropiedad) {
                Propiedad propiedad = new Propiedad();
                propiedad = propiedadDao.encontrarPropiedadPorId(repPropiedad.getPrdMatricula().getPrdMatricula().toString());
                String lineasMatriculas = propiedad.getPrdTdtParNombre() + " "
                        + propiedad.getPrdMatricula()
                        + " Departamento situado en la parroquia "
                        + propiedad.getPrdTdtParNombre()
                        + " de este Cantón"
                        + " Catastro: " + propiedad.getPrdCatastro()
                        + " Predio: " + propiedad.getPrdPredio()
                        + "<br/>";
                lineasTodasMatriculas = lineasTodasMatriculas + lineasMatriculas;

            }

            String MES[] = {"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};
            String DIASEMANA[] = {"Domingo", "Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado"};
            String DIA[] = {"UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE",
                "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISÉIS", "DIECISIETE,",
                "DIECIOCHO", "DIECINUEVE", "VEINTE", "VEINTIUNO", "VEINTIDÓS", "VEINTITRES", "VEINTICUATRO",
                "VEINTICINCO", "VEINTISÉIS", "VEINTISIETE", "VEINTIOCHO", "VEINTINUEVE", "TREINTA",
                "TREINTA Y UNO"};

            Calendar calendar = Calendar.getInstance();

            Date hora = calendar.getTime();

            String strDateFormat = "hh:mm:ss a";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate = dateFormat.format(hora);

            String fecha = "";

            fecha = DIASEMANA[calendar.get(Calendar.DAY_OF_WEEK) - 1] + ", "
                    + calendar.get(Calendar.DAY_OF_MONTH) + " "
                    + MES[calendar.get(Calendar.MONTH)] + " "
                    + calendar.get(Calendar.YEAR) + ", "
                    + formattedDate;

            String tituloRegistrador = "EL REGISTRADOR <br/><br/>";
            String txtContrantes = "Contratantes.-";
            
            listaTramiteDetalle.clear();

            listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramiteYporIdTipoTramite(
                    repertorio.getTraNumero().getTraNumero(), repertorio.getRepTtrId());

            String lineasNombresContratantes = "";
            for (TramiteDetalle tramiteDetalle : listaTramiteDetalle) {

                String nombreContratante = tramiteDetalle.getTdtPerNombre()
                        + " " + tramiteDetalle.getTdtPerApellidoPaterno()
                        + " " + tramiteDetalle.getTdtPerApellidoMaterno()
                        + " en su calidad de " + tramiteDetalle.getTdtTpcDescripcion()
                        + "<br/>";
                lineasNombresContratantes = lineasNombresContratantes + nombreContratante;

            }

            String textoFinal = "Los número de matrícula le servirán para cualquier trámite posterior.<br/><br/>"
                    + "Responsables.- <br/>";
            String asesor = "Asesor.- " + "<br/>";
            String revisor = "Revisor.- " + "<br/><br/><br/>";
            String numRazon = "RR-";

            razonInscripcion = titulo + linea1 + linea2 + txtMatriculas
                    + lineasTodasMatriculas + "<br/><br/>" + fecha
                    + "<br/><br/><br/><br/><br/><br/>" + tituloRegistrador
                    + txtContrantes + "<br/>" + lineasNombresContratantes
                    + "<br/><br/>" + textoFinal  
                    + asesor + revisor + numRazon;
                    

        } catch (Exception e) {
        }

    }

    public void guardarRazon() throws ServicioExcepcion, ParseException {
        if (razonInscripcion != "") {

            Acta acta = new Acta();
            acta = actaDao.buscarActaPorNumRepertorio(Long.valueOf(numRepertorio));

            secuenciaControlador.generarSecuencia("RAZ");
            //obtengo el siguiente valor de la secuencia
            setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
            int auxSecuencia = getSecuencia().getSecActual();
            getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
            Long sec = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
            Long idGenerado = new Long(sec);
            secuenciaServicio.guardar(getSecuencia());

            ActaRazon actaRazon = new ActaRazon();
            actaRazon.setAtrNumero(idGenerado);
            actaRazon.setAtrFHR(Calendar.getInstance().getTime());
            actaRazon.setAtrUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            actaRazon.setAtrRazonHtml(razonInscripcion);
            actaRazon.setAtrRazonPdf(razonInscripcion);
            actaRazon.setActNumero(acta);

            actaRazonDao.create(actaRazon);

            deshabilitarBotonGuardar = true;
            renderedTxtEdit = true;

            procesarEnRepertorioUsuario();

            limpiarCamposTxtEdit();
            llenarDatos();
//            FacesContext context = FacesContext.getCurrentInstance();
//            context.addMessage(null, new FacesMessage("Razón Guardada", ""));

        }
    }

    public void previsualizarRazon() throws ServicioExcepcion, ParseException {

        txtRazonInscripcion();
        deshabilitarBotonGuardar = false;

    }

    public void procesarEnRepertorioUsuario() throws ServicioExcepcion {

        try {
            int numRepUsuActualizados = 0;

            numRepUsuActualizados = servicioRepertorioUsuario.actualizarEstadoRepUsuPorRepPorUsrPorTipo(Long.valueOf(numRepertorio),
                    dataManagerSesion.getUsuarioLogeado().getUsuId(), "RAZ");

            tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(Long.valueOf(tramite));

            usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("IMP", "Impresion", 1);

            RepertorioUsuario repertorioUsuario = new RepertorioUsuario();
            repertorioUsuario.setRpuTipo("IMP");
            repertorioUsuario.setUsuId(usuarioAsignado);
            repertorioUsuario.setRpuEstado("A");
            repertorioUsuario.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            repertorioUsuario.setRpuFHR(Calendar.getInstance().getTime());
            repertorioUsuario.setRepNumero(repertorio);

            servicioRepertorioUsuario.create(repertorioUsuario);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, secuenciaControlador.getControladorBb().getNumeroTramite().toString(),
                    "Razon: " + secuenciaControlador.getControladorBb().getNumeroTramite()
                    + " asignada al usuario " + usuarioAsignado.getUsuLogin(),
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), usuarioAsignado.getUsuLogin());

            tramiteSeleccionado.setTraEstado("IMP");
            servicioTramite.edit(tramiteSeleccionado);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, secuenciaControlador.getControladorBb().getNumeroTramite().toString(),
                    "ACTUALIZACIÓN DEL ESTADO DE TRAMITE A 'IMP'",
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), dataManagerSesion.getUsuarioLogeado().getUsuLogin());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
