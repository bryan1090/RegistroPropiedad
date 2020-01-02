package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.servicio.PersonaServicio;
import java.io.Serializable;
import java.util.List;
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
 * @author DJimenez
 */
@Named(value = "controladorFiltradoPersona")
@ViewScoped
public class ControladorFiltradoPersona implements Serializable {

    @EJB
    private PersonaServicio servicioPersona;

    @Inject
    private ControladorCajasRecaudacionRM controladorCajasRecaudacionRM;

    @Inject
    private ControladorCajasRecaudacion controladorCajasRecaudacion;

    @Inject
    private TramitesControlador tramitesControlador;

    @Inject
    private ControladorTramiteProforma controladorTramiteProforma;

    @Inject
    private ControladorCajaCertificado controladorCajaCertificado;

    @Inject
    private PersonaController controladorPersona;

    @Inject
    private ControladorNuevasMatriculasPH controladorNuevasMatriculasPH;
    @Inject
    private ControladorTmpAlicuota controladorTmpAlicuota;
    @Inject
    private ControladorIncidenciaActa controladorIncidenciaActa;
    @Inject
    private ControladorInscripcionProceso controladorInscripcionProceso;
    @Inject
    private ControladorTipoDescuento controladorTipoDescuento;
    @Inject
    private ControladorBuscarTramite controladorBuscarTramite;
    @Inject
    private ControladorCancelacion controladorCancelacion;
    @Inject
    private ControladorDemanda controladorDemanda;
    @Inject
    private ControladorInscripcionJudicial controladorInscripcionJudicial;
    @Inject
    private ControladorSentenciaPE controladorSentenciaPE;
    @Inject
    private controladorGestionVolumenes controladorGestionVolumenes;
    @Inject
    private ControladorMantenimientoTramite controladorMantenimientoTramite;
    @Inject
    private RecepcionDocumentacionControlador recepcionDocumentacionControlador;
    @Inject
    private ControladorCertificado controladorCertificado;
    @Inject
    private ControladorUtilPropiedad controladorUtilPropiedad;
    @Inject
    private ControladorIncidenciaUsuario controladorIncidenciaUsuario;

    @Inject
    @Getter
    @Setter
    private ControladorEditarPersona controladorEditarPersona;

    @Getter
    @Setter
    private List<Persona> listaPersonasFiltradas;

    @Getter
    @Setter
    private Persona personaEncontrada;

    @Getter
    @Setter
    private String apellidoPaterno;

    @Getter
    @Setter
    private String apellidoMaterno;

    @Getter
    @Setter
    private String nombre;

    @Getter
    @Setter
    private String controlador;

    @Getter
    @Setter
    private String frmActualizar;

    @Getter
    @Setter
    private List<Persona> listaConyugueFiltradas;

    @Getter
    @Setter
    private Persona personaSeleccionada;

    public void prepararFiltrado(String controlador, String frmActualizar) {
        this.controlador = controlador;
        this.frmActualizar = frmActualizar;
        limpiarCamposBusqueda();
        switch (controlador) {
            case "CtrlIns":
                PrimeFaces current = PrimeFaces.current();
                current.ajax().update("formFilterPersona");
                break;
        }

    }

    public void limpiarCamposBusqueda() {
        apellidoPaterno = "";
        apellidoMaterno = "";
        nombre = "";
    }

    public void clear() {
        listaPersonasFiltradas = null;
        apellidoPaterno = "";
        apellidoMaterno = "";
        nombre = "";
    }

    public void encontrarPersona() {
        try {
            Persona persona = servicioPersona.buscarPersonaPorId(personaEncontrada.getPerId());
            if (persona.getPerMigrado() != null) {
                if (persona.getPerMigrado().equals("SI")) {
                    getControladorEditarPersona().setPersonaEditar(persona);
                    getControladorEditarPersona().getPersonaEditar().setPerTipoIdentificacion("C");
                    PrimeFaces current = PrimeFaces.current();
                    switch (controlador) {
                        case "CtrlIns":
                            current.ajax().update("PersonaEditForm");
                            break;
                    }
                    current.executeScript("PF('PersonaEditDialog').show();");
                } else {
                    PrimeFaces current = PrimeFaces.current();
                    setearPersona(persona);
                    current.executeScript("PF('dlgFiltradoPersona').hide();");
                }

            } else {
                getControladorEditarPersona().setPersonaEditar(persona);
                setearPersona(persona);
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dlgFiltradoPersona').hide();");
            }
            if (listaPersonasFiltradas != null) {
                listaPersonasFiltradas.clear();
            }

        } catch (ServicioExcepcion e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Persona No Encontrada", ""));
        }

    }

    public void setearPersona(Persona personaAsignar) {
        try {
            personaEncontrada = personaAsignar;
            switch (controlador) {
                case "CR":
                    controladorCajasRecaudacion.getCajasRecBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorCajasRecaudacion.getCajasRecBb().setIdentificacion(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()).getPerIdentificacion());
                    break;
                case "RM":
                    controladorCajasRecaudacionRM.getCajasRecBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorCajasRecaudacionRM.getCajasRecBb().setIdentificacion(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()).getPerIdentificacion());
                    break;
                case "TR":
                    tramitesControlador.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    tramitesControlador.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    tramitesControlador.nombrePersona();
                    break;
                case "TRIN":
                    controladorTramiteProforma.getReTramiteProformaBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorTramiteProforma.getReTramiteProformaBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorTramiteProforma.nombrePersona();
                    break;

                case "CC":
                    controladorCajaCertificado.getCajasRecBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorCajaCertificado.getCajasRecBb().setIdentificacion(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()).getPerIdentificacion());
                    break;
                case "PerC":
                    controladorPersona.getSelected().setPerIdConyuge(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorPersona.setCiConyuge(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()).getPerIdentificacion());
                    break;
                case "CtrlPH":
                    controladorNuevasMatriculasPH.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorNuevasMatriculasPH.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorNuevasMatriculasPH.nombrePersona();
                    break;
                case "CtrlPHExcel":
                    controladorTmpAlicuota.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorTmpAlicuota.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorTmpAlicuota.nombrePersona();
                    break;
                case "CtrlIncActa":
                    controladorIncidenciaActa.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorIncidenciaActa.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorIncidenciaActa.nombrePersona();
                    break;
                case "CtrlIns":
                    controladorInscripcionProceso.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorInscripcionProceso.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorInscripcionProceso.nombrePersona();
                    break;
                case "CtrlTipDesc":
                    controladorTipoDescuento.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorTipoDescuento.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorTipoDescuento.nombrePersona();
                    break;    
                case "CtrlCAN":
                    controladorCancelacion.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorCancelacion.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorCancelacion.nombrePersona();
                    break;
                case "CtrlDEM":
                    controladorDemanda.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorDemanda.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorDemanda.nombrePersona();
                    break;
                case "CtrlINJ":
                    controladorInscripcionJudicial.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorInscripcionJudicial.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorInscripcionJudicial.nombrePersona();
                    break;
                case "CtrlSPE":
                    controladorSentenciaPE.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorSentenciaPE.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorSentenciaPE.nombrePersona();
                    break;
                case "CtlGVol":
                    controladorGestionVolumenes.setPersonaSeleccionada(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorGestionVolumenes.setPrvNombre(controladorGestionVolumenes.getPersonaSeleccionada().getPerNombre());
                    controladorGestionVolumenes.setPrvApellido(controladorGestionVolumenes.getPersonaSeleccionada().getPerApellidoPaterno());
                    controladorGestionVolumenes.setPerTipoIdentificacion(controladorGestionVolumenes.getPersonaSeleccionada().getPerTipoIdentificacion());
                    controladorGestionVolumenes.setPerIdentificacion(controladorGestionVolumenes.getPersonaSeleccionada().getPerIdentificacion());
                    break;
                case "ToolTraAct":
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorMantenimientoTramite.setIdentificacionTramiteTools(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerIdentificacion());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setPerId(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setTdtPerIdentificacion(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerIdentificacion());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setTdtPerNombre(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerNombre());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setTdtPerApellidoPaterno(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerApellidoPaterno());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setTdtPerApellidoMaterno(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerApellidoMaterno());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setTdtConyuguePerId(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerIdConyuge().getPerId());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setTdtConyuguePerNombre(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerNombre() + " " + controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerApellidoPaterno() + " " + controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerApellidoMaterno());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setTdtConyuguePerTipoIden(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerIdConyuge().getPerTipoIdentificacion());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setTdtPerTipoIdentificacion(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerTipoIdentificacion());
                    controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getTramiteDetalle().setTdtPerTipoContribuyente(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerTipoContribuyente());
                    System.out.print(controladorMantenimientoTramite.getControladorMantenimientoTramiteBb().getPersona().getPerIdentificacion());
                    break;
                case "CtrlBuscarTram":
                    controladorBuscarTramite.getTramitesControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorBuscarTramite.getTramitesControladorBb().setIdentificacion(personaEncontrada.getPerIdentificacion());
                    controladorBuscarTramite.nombrePersona();
                    break;
                case "CtrlRecTra":
                    recepcionDocumentacionControlador.getRecepcionDocumentacionControladorBb().setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    recepcionDocumentacionControlador.getRecepcionDocumentacionControladorBb().setIdentificacion(recepcionDocumentacionControlador.getRecepcionDocumentacionControladorBb().getPersona().getPerIdentificacion());
                    break;
                case "CtrlRecTraI":
                    recepcionDocumentacionControlador.getContrAgregarDocumentosEntregados().setPersonaResponsable(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    recepcionDocumentacionControlador.getContrAgregarDocumentosEntregados().setEstadoResponsable("false");
                    recepcionDocumentacionControlador.setIdentificacionResponsable(recepcionDocumentacionControlador.getContrAgregarDocumentosEntregados().getPersonaResponsable().getPerIdentificacion());
                    recepcionDocumentacionControlador.setDisableComboTipoTramite(false);
                    break;
                case "PropTools":
                    controladorMantenimientoTramite.setPersonaPropietaria(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorMantenimientoTramite.setNombrePersonaProp(controladorMantenimientoTramite.getPersonaPropietaria().getPerApellidoPaterno() + " " + controladorMantenimientoTramite.getPersonaPropietaria().getPerApellidoMaterno() + " " + controladorMantenimientoTramite.getPersonaPropietaria().getPerNombre());
                    controladorMantenimientoTramite.setIdentificacion(controladorMantenimientoTramite.getPersonaPropietaria().getPerIdentificacion());
                    break;
                case "ctrlCert":
                    controladorCertificado.setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorCertificado.setIdentificacion(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()).getPerIdentificacion());
                    break;
                case "IncPro":
                    controladorIncidenciaUsuario.setPersonaEncontrada(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorIncidenciaUsuario.setPersonaAntigua(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    break;
                    case "ctrlUtilPropiedad":
                    controladorUtilPropiedad.setPersona(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()));
                    controladorUtilPropiedad.setIdentificacion(servicioPersona.encontrarPersonaPorIdentificacion(personaEncontrada.getPerIdentificacion()).getPerIdentificacion());
                    break;
            }
            clear();
        } catch (ServicioExcepcion e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Persona No Encontrada", ""));
        }

    }

    public void filtrarPersona() {
        if (!(apellidoPaterno.isEmpty() || apellidoPaterno == null)
                || !(apellidoMaterno.isEmpty() || apellidoMaterno == null)
                || !(nombre.isEmpty() || nombre == null)) {
            setListaPersonasFiltradas(servicioPersona.listarPorNom_ApeP_ApeM(apellidoPaterno.toUpperCase(), apellidoMaterno.toUpperCase(), nombre.toUpperCase()));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Llenar al menos un campo", ""));
        }
//        if (apellidoPaterno.isEmpty()) {
//            apellidoPaterno = apellidoPaterno + "^";
//        }
//        if (apellidoMaterno.isEmpty()) {
//            apellidoMaterno = apellidoMaterno + "^";
//        }
//        if (nombre.isEmpty()) {
//            nombre = nombre + "^";
//        }
    }

    public void filtrarConyugue() {
        setListaConyugueFiltradas(servicioPersona.listarPorNom_ApeP_ApeM(apellidoPaterno.toUpperCase(), apellidoMaterno.toUpperCase(), nombre.toUpperCase()));
        if (listaConyugueFiltradas.contains(personaEncontrada)) {
            listaConyugueFiltradas.remove(personaEncontrada);
        }

    }

    public void asignarConyugue() throws ServicioExcepcion {
        Persona persona = servicioPersona.buscarPersonaPorId(personaSeleccionada.getPerId());
        PrimeFaces current = PrimeFaces.current();
        current.executeScript("PF('dlgFiltradoPersonaI').hide();");
        getControladorEditarPersona().getPersonaEditar().setPerIdConyuge(persona);
        getControladorEditarPersona().setCiConyuge(persona.getPerIdentificacion());
        personaSeleccionada = persona;

    }

}
