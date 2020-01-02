package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Incidencia;
import com.rm.empresarial.modelo.IncidenciaAuditoria;
import com.rm.empresarial.modelo.IncidenciaHistorico;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.servicio.IncidenciaAuditoriaServicio;
import com.rm.empresarial.servicio.IncidenciaHistoricoServicio;
import com.rm.empresarial.servicio.IncidenciaServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.PropietarioServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.primefaces.component.tabview.TabView;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorIncidenciaUsuario")
@ViewScoped
public class ControladorIncidenciaUsuario implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    
    @Inject
    @Getter
    @Setter
    private ControladorIncidenciaActa controladorIncidenciaActa;

    @Inject
    private PersonaUtil personaUtil;

    @EJB
    private IncidenciaServicio servicioIncidencia;
    @EJB
    private IncidenciaAuditoriaServicio servicioIncidenciaAuditoria;
    @EJB
    private IncidenciaHistoricoServicio servicioIncidenciaHistorico;
    @EJB
    private PersonaServicio servicioPersona;
    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;
    @EJB
    private PropietarioServicio servicioPropietario;

    @Getter
    @Setter
    private List<Incidencia> listaIncidencias;
    @Getter
    @Setter
    private List<TramiteDetalle> listaTramitesDetalle;
    @Getter
    @Setter
    private List<Propietario> listaPropietarios;
    @Getter
    @Setter
    private List<Persona> listaPersonasFiltradas;

    @Getter
    @Setter
    private Incidencia incidenciaSeleccionada;
    @Getter
    @Setter
    private Persona personaEncontrada;
    @Getter
    @Setter
    private IncidenciaAuditoria incidenciaAuditoria;
    @Getter
    @Setter
    private Persona personaAntigua;
    @Getter
    @Setter
    private IncidenciaHistorico incidenciaHistorico;

    @Getter
    @Setter
    private TabView tabView;

    @Getter
    @Setter
    private Boolean rendPnlActa = false;
    @Getter
    @Setter
    private int txtEditPorNumero;

    /**
     * *********************************************************
     */
    @PostConstruct
    public void postControladorIncidenciaUsuario() {
        listarIncidenciasPorUsuarioLogueado();
    }

    public ControladorIncidenciaUsuario() {
        listaIncidencias = new ArrayList<>();

        incidenciaSeleccionada = new Incidencia();
        incidenciaAuditoria = new IncidenciaAuditoria();
        personaEncontrada = new Persona();
        personaAntigua = new Persona();
        incidenciaHistorico = new IncidenciaHistorico();
        tabView = new TabView();
    }

    public void listarIncidenciasPorUsuarioLogueado() {
        setListaIncidencias(servicioIncidencia.listarPorUsuarioLogueado(dataManagerSesion.getUsuarioLogeado()));
    }

    public void cambioEstado() throws ServicioExcepcion, ParseException{
        controladorIncidenciaActa.limpiarCamposTxtEdit();
        rendPnlActa = false;
        switch (incidenciaSeleccionada.getIncEstado()) {
            case "A":
            case "P":
                if (incidenciaSeleccionada.getIncEstado().equals("A")) {
                    getIncidenciaSeleccionada().setIncEstado("P");
                    incidenciaHistorico = new IncidenciaHistorico(null, "Incidencia Procesada", dataManagerSesion.getUsuarioLogeado().getUsuLogin(), Calendar.getInstance().getTime());
                    incidenciaHistorico.setIncId(incidenciaSeleccionada);
                    servicioIncidencia.edit(incidenciaSeleccionada);
                    servicioIncidenciaHistorico.create(incidenciaHistorico);
                }
                
                if (incidenciaSeleccionada.getTidId().getTipCodigoPadre().trim().equals("4")){ 
                    if(incidenciaSeleccionada.getIncRepNumero() != null){
                        controladorIncidenciaActa.limpiarCamposTxtEdit();
                    controladorIncidenciaActa.onRowSelect(incidenciaSeleccionada);
                    rendPnlActa = true;  
                    renderedTxtEditActa();
                    }
                    
                   
                }
                break;
            case "T":
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se Puede Seleccionar Incidencia", "La Incidencia se Encuentra en Estado Terminado"));
                break;
            case "E":
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se Puede Seleccionar Incidencia", "La Incidencia se Encuentra en Estado Entregado"));
                break;
            default:
                break;
        }

    }
    
    public void renderedTxtEditActa(){
         switch (incidenciaSeleccionada.getTidId().getTipCodigoHijo().trim()){
                        case "1":
                            txtEditPorNumero = 1;
                            break;
                        case "2":
                            txtEditPorNumero = 2;
                            break;
                            case "3":
                            txtEditPorNumero = 3;
                            break;
                        case "4":
                            txtEditPorNumero = 4;
                            break;
                            case "5":
                            txtEditPorNumero = 5;
                            break;
                        case "6":
                            txtEditPorNumero = 6;
                            break;
                            case "7":
                            txtEditPorNumero = 7;
                            break;
                        case "8":
                            txtEditPorNumero = 8;
                            break;
                           
                            
                    }
    }

    public void encontrarPersona() {
        if (personaEncontrada.getPerIdentificacion() != null && personaEncontrada.getPerIdentificacion().length() <= 13) {
            try {
                setPersonaEncontrada(personaUtil.obtenerDatosPersona(personaEncontrada.getPerIdentificacion()));
                setPersonaAntigua(personaUtil.obtenerDatosPersona(personaEncontrada.getPerIdentificacion()));
            } catch (ServicioExcepcion e) {
                System.out.print(e);
                personaEncontrada = new Persona();
                personaAntigua = new Persona();
            }
        } else {
            personaEncontrada = new Persona();
            personaAntigua = new Persona();
        }
    }

    public void actualizarDatos() throws IOException {
        if (incidenciaSeleccionada != null && (incidenciaSeleccionada.getIncEstado().equals("A") || incidenciaSeleccionada.getIncEstado().equals("P"))) {
            if (!personaEncontrada.getPerApellidoPaterno().equals(personaAntigua.getPerApellidoPaterno()) || !personaEncontrada.getPerApellidoMaterno().equals(personaAntigua.getPerApellidoMaterno()) || !personaEncontrada.getPerNombre().equals(personaAntigua.getPerNombre())) {
                setListaTramitesDetalle(servicioTramiteDetalle.obtenerTramitePorIdPersona(personaEncontrada));
                setListaPropietarios(servicioPropietario.listarPor_Id_Persona(personaEncontrada.getPerId()));

                servicioPersona.edit(personaEncontrada);
                for (Propietario propietario : listaPropietarios) {
                    propietario.setPprPerApellidoPaterno(personaEncontrada.getPerApellidoPaterno().toUpperCase());
                    propietario.setPpPerApellidoMaterno(personaEncontrada.getPerApellidoMaterno().toUpperCase());
                    propietario.setPprPerNombre(personaEncontrada.getPerNombre().toUpperCase());
                    servicioPropietario.edit(propietario);
                }
                for (TramiteDetalle tramiteDetalle : listaTramitesDetalle) {
                    tramiteDetalle.setTdtPerApellidoPaterno(personaEncontrada.getPerApellidoPaterno().toUpperCase());
                    tramiteDetalle.setTdtPerApellidoMaterno(personaEncontrada.getPerApellidoMaterno().toUpperCase());
                    tramiteDetalle.setTdtPerNombre(personaEncontrada.getPerNombre().toUpperCase());
                    servicioTramiteDetalle.edit(tramiteDetalle);
                }

                if (!personaAntigua.getPerApellidoPaterno().equals(personaEncontrada.getPerApellidoPaterno())) {

                    incidenciaAuditoria.setIncId(incidenciaSeleccionada);
                    incidenciaAuditoria.setIadTabla("Persona");
                    incidenciaAuditoria.setIadAtributo("PerApellidoPaterno");
                    incidenciaAuditoria.setIadAnterior(personaAntigua.getPerApellidoPaterno().toUpperCase());
                    incidenciaAuditoria.setIadActual(personaEncontrada.getPerApellidoPaterno().toUpperCase());
                    incidenciaAuditoria.setIadUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    incidenciaAuditoria.setIadFHR(Calendar.getInstance().getTime());
                    servicioIncidenciaAuditoria.create(incidenciaAuditoria);

                    if (!listaTramitesDetalle.isEmpty()) {
                        incidenciaAuditoria.setIncId(incidenciaSeleccionada);
                        incidenciaAuditoria.setIadTabla("TramiteDetalle");
                        incidenciaAuditoria.setIadAtributo("TdtPerApellidoPaterno");
                        incidenciaAuditoria.setIadAnterior(personaAntigua.getPerApellidoPaterno().toUpperCase());
                        incidenciaAuditoria.setIadActual(personaEncontrada.getPerApellidoPaterno().toUpperCase());
                        incidenciaAuditoria.setIadUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        incidenciaAuditoria.setIadFHR(Calendar.getInstance().getTime());
                        servicioIncidenciaAuditoria.create(incidenciaAuditoria);
                    }

                    if (!listaPropietarios.isEmpty()) {
                        incidenciaAuditoria.setIncId(incidenciaSeleccionada);
                        incidenciaAuditoria.setIadTabla("Propietario");
                        incidenciaAuditoria.setIadAtributo("PprPerApellidoPaterno");
                        incidenciaAuditoria.setIadAnterior(personaAntigua.getPerApellidoPaterno().toUpperCase());
                        incidenciaAuditoria.setIadActual(personaEncontrada.getPerApellidoPaterno().toUpperCase());
                        incidenciaAuditoria.setIadUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        incidenciaAuditoria.setIadFHR(Calendar.getInstance().getTime());
                        servicioIncidenciaAuditoria.create(incidenciaAuditoria);
                    }

                }
                if (!personaAntigua.getPerApellidoMaterno().equals(personaEncontrada.getPerApellidoMaterno())) {
                    incidenciaAuditoria.setIncId(incidenciaSeleccionada);
                    incidenciaAuditoria.setIadTabla("Persona");
                    incidenciaAuditoria.setIadAtributo("PerApellidoMaterno");
                    incidenciaAuditoria.setIadAnterior(personaAntigua.getPerApellidoMaterno().toUpperCase());
                    incidenciaAuditoria.setIadActual(personaEncontrada.getPerApellidoMaterno().toUpperCase());
                    incidenciaAuditoria.setIadUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    incidenciaAuditoria.setIadFHR(Calendar.getInstance().getTime());
                    servicioIncidenciaAuditoria.create(incidenciaAuditoria);

                    if (!listaTramitesDetalle.isEmpty()) {
                        incidenciaAuditoria.setIncId(incidenciaSeleccionada);
                        incidenciaAuditoria.setIadTabla("TramiteDetalle");
                        incidenciaAuditoria.setIadAtributo("TdtPerApellidoMaterno");
                        incidenciaAuditoria.setIadAnterior(personaAntigua.getPerApellidoMaterno().toUpperCase());
                        incidenciaAuditoria.setIadActual(personaEncontrada.getPerApellidoMaterno().toUpperCase());
                        incidenciaAuditoria.setIadUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        incidenciaAuditoria.setIadFHR(Calendar.getInstance().getTime());
                        servicioIncidenciaAuditoria.create(incidenciaAuditoria);
                    }

                    if (!listaPropietarios.isEmpty()) {
                        incidenciaAuditoria.setIncId(incidenciaSeleccionada);
                        incidenciaAuditoria.setIadTabla("Propietario");
                        incidenciaAuditoria.setIadAtributo("PprPerApellidoMaterno");
                        incidenciaAuditoria.setIadAnterior(personaAntigua.getPerApellidoMaterno().toUpperCase());
                        incidenciaAuditoria.setIadActual(personaEncontrada.getPerApellidoMaterno().toUpperCase());
                        incidenciaAuditoria.setIadUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        incidenciaAuditoria.setIadFHR(Calendar.getInstance().getTime());
                        servicioIncidenciaAuditoria.create(incidenciaAuditoria);
                    }

                }
                if (!personaAntigua.getPerNombre().equals(personaEncontrada.getPerNombre())) {
                    incidenciaAuditoria.setIncId(incidenciaSeleccionada);
                    incidenciaAuditoria.setIadTabla("Persona");
                    incidenciaAuditoria.setIadAtributo("PerNombre");
                    incidenciaAuditoria.setIadAnterior(personaAntigua.getPerNombre().toUpperCase());
                    incidenciaAuditoria.setIadActual(personaEncontrada.getPerNombre().toUpperCase());
                    incidenciaAuditoria.setIadUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    incidenciaAuditoria.setIadFHR(Calendar.getInstance().getTime());
                    servicioIncidenciaAuditoria.create(incidenciaAuditoria);

                    if (!listaTramitesDetalle.isEmpty()) {
                        incidenciaAuditoria.setIncId(incidenciaSeleccionada);
                        incidenciaAuditoria.setIadTabla("TramiteDetalle");
                        incidenciaAuditoria.setIadAtributo("TdtPerNombre");
                        incidenciaAuditoria.setIadAnterior(personaAntigua.getPerNombre().toUpperCase());
                        incidenciaAuditoria.setIadActual(personaEncontrada.getPerNombre().toUpperCase());
                        incidenciaAuditoria.setIadUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        incidenciaAuditoria.setIadFHR(Calendar.getInstance().getTime());
                        servicioIncidenciaAuditoria.create(incidenciaAuditoria);
                    }

                    if (!listaPropietarios.isEmpty()) {
                        incidenciaAuditoria.setIncId(incidenciaSeleccionada);
                        incidenciaAuditoria.setIadTabla("Propietario");
                        incidenciaAuditoria.setIadAtributo("PprPerNombre");
                        incidenciaAuditoria.setIadAnterior(personaAntigua.getPerNombre().toUpperCase());
                        incidenciaAuditoria.setIadActual(personaEncontrada.getPerNombre().toUpperCase());
                        incidenciaAuditoria.setIadUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        incidenciaAuditoria.setIadFHR(Calendar.getInstance().getTime());
                        servicioIncidenciaAuditoria.create(incidenciaAuditoria);
                    }

                }

                getIncidenciaSeleccionada().setIncEstado("T");
                incidenciaHistorico = new IncidenciaHistorico(null, "Incidencia Terminada", dataManagerSesion.getUsuarioLogeado().getUsuLogin(), Calendar.getInstance().getTime());
                incidenciaHistorico.setIncId(incidenciaSeleccionada);

                servicioIncidencia.edit(incidenciaSeleccionada);
                servicioIncidenciaHistorico.create(incidenciaHistorico);

                personaAntigua = new Persona();
                personaEncontrada = new Persona();
                incidenciaAuditoria = new IncidenciaAuditoria();
                incidenciaSeleccionada = new Incidencia();

                FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/Incidencias/Incidencias.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No se ha Cambiado Ningun Dato de la Persona", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Primero Seleccione una Incidencia", ""));
        }
    }

}
