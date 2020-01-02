/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.servicio.PersonaServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

/**
 *
 * @author P
 */
@Named(value = "controladorEditarPersona")
@ViewScoped
public class ControladorEditarPersona implements Serializable {

    private static final long serialVersionUID = 5604226631508490479L;

    @Inject
    @Getter
    @Setter
    private ControladorFiltradoPersona controladorFiltradoPersona;

    @EJB
    private PersonaDao servicioPersona;

    @Getter
    @Setter
    private String identificacion;

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
    private String ciConyuge;

    @Getter
    @Setter
    private String disableConyuge;

    @Getter
    @Setter
    private boolean perDisolucionConyugal;

    @Getter
    @Setter
    private boolean perCalidad;

    @Getter
    @Setter
    private boolean cerraDlgEditarPersona = false;

    @Getter
    @Setter
    private String auxIdentificacion;

    @EJB
    @Getter
    private PersonaServicio personaServicio;

    @Inject
    private DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private List<Persona> items;

    @Getter
    @Setter
    private Persona personaEditar;

    private JsfUtil jsfUtil;

    @Getter
    @Setter
    private Boolean agregandoConyugue;

    @PostConstruct
    public void cargarDatos() {
        jsfUtil = new JsfUtil();
        items = new ArrayList<>();
        disableConyuge = "true";
    }

    public void prepareEdit() {
        compConyuge();
        if (disableConyuge.equals("false")) {
            setCiConyuge(personaEditar.getPerIdConyuge().getPerIdentificacion());
        }
        if (!(personaEditar.getPerCalidad() == null || personaEditar.getPerCalidad().isEmpty())) {
            setPerCalidad(personaEditar.getPerCalidad().equals("S"));
            setPerDisolucionConyugal(personaEditar.getPerDisolucionConyugal().equals("S"));
        }
        setAuxIdentificacion(personaEditar.getPerIdentificacion());
    }

    public Persona prepareCreate() {
        personaEditar = new Persona();
        return personaEditar;
    }

    public void clear() {
        setCiConyuge(null);
        setPerCalidad(false);
        setPerDisolucionConyugal(false);
        setPersonaEditar(null);
    }

    public void create() throws ServicioExcepcion {
        switch (personaEditar.getPerTipoIdentificacion()) {
            case "C":
                if (jsfUtil.validadorDeCedula(personaEditar.getPerIdentificacion().trim())) {
                    if (!personaServicio.personaDuplicado(personaEditar.getPerIdentificacion().trim())) {
                        if (perDisolucionConyugal) {
                            personaEditar.setPerDisolucionConyugal("S");
                        } else {
                            personaEditar.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            personaEditar.setPerCalidad("S");
                        } else {
                            personaEditar.setPerCalidad("N");
                        }
                        personaEditar.setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        personaEditar.setPerFHR(Calendar.getInstance().getTime());
                        personaEditar.setPerId(personaServicio.asignarID());
                        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonaCreated"));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La Cédula ingresada ya existe", ""));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La Cédula ingresada es incorrecta", ""));
                }
                break;
            case "R":
                if (jsfUtil.validaRuc(personaEditar.getPerIdentificacion())) {
                    if (!personaServicio.personaDuplicado(personaEditar.getPerIdentificacion())) {
                        if (perDisolucionConyugal) {
                            personaEditar.setPerDisolucionConyugal("S");
                        } else {
                            personaEditar.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            personaEditar.setPerCalidad("S");
                        } else {
                            personaEditar.setPerCalidad("N");
                        }
                        personaEditar.setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        personaEditar.setPerFHR(Calendar.getInstance().getTime());
                        personaEditar.setPerId(personaServicio.asignarID());
                        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonaCreated"));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Ruc ingresado ya existe", ""));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El Ruc ingresado es incorrecto", ""));
                }
                break;
            case "P":
                if (!personaServicio.personaDuplicado(personaEditar.getPerIdentificacion())) {
                    if (perDisolucionConyugal) {
                        personaEditar.setPerDisolucionConyugal("S");
                    } else {
                        personaEditar.setPerDisolucionConyugal("N");
                    }
                    if (perCalidad) {
                        personaEditar.setPerCalidad("S");
                    } else {
                        personaEditar.setPerCalidad("N");
                    }
                    personaEditar.setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    personaEditar.setPerFHR(Calendar.getInstance().getTime());
                    personaEditar.setPerId(personaServicio.asignarID());
                    persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonaCreated"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Pasaporte ingresado ya existe", ""));
                }
                break;
            default:
                break;
        }
        clear();
    }

    public void update() throws ServicioExcepcion {
        cerraDlgEditarPersona = false;

        switch (personaEditar.getPerTipoIdentificacion()) {
            case "C":
                if (jsfUtil.validadorDeCedula(personaEditar.getPerIdentificacion().trim())) {
                    if (!personaEditar.getPerIdentificacion().trim().equals(auxIdentificacion)) {

                        if (perDisolucionConyugal) {
                            personaEditar.setPerDisolucionConyugal("S");
                        } else {
                            personaEditar.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            personaEditar.setPerCalidad("S");
                        } else {
                            personaEditar.setPerCalidad("N");
                        }
                        personaEditar.setPerMigrado("AC");
                        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));

                    } else {
                        if (perDisolucionConyugal) {
                            personaEditar.setPerDisolucionConyugal("S");
                        } else {
                            personaEditar.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            personaEditar.setPerCalidad("S");
                        } else {
                            personaEditar.setPerCalidad("N");
                        }
                        personaEditar.setPerMigrado("AC");

                        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La Cédula ingresada es incorrecta", ""));
                }
                break;
            case "R":
                if (jsfUtil.validaRuc(personaEditar.getPerIdentificacion())) {
                    if (!personaEditar.getPerIdentificacion().trim().equals(auxIdentificacion)) {

                        if (perDisolucionConyugal) {
                            personaEditar.setPerDisolucionConyugal("S");
                        } else {
                            personaEditar.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            personaEditar.setPerCalidad("S");
                        } else {
                            personaEditar.setPerCalidad("N");
                        }
                        personaEditar.setPerMigrado("AC");
                        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));

                    } else {
                        if (perDisolucionConyugal) {
                            personaEditar.setPerDisolucionConyugal("S");
                        } else {
                            personaEditar.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            personaEditar.setPerCalidad("S");
                        } else {
                            personaEditar.setPerCalidad("N");
                        }
                        personaEditar.setPerMigrado("AC");
                        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El Ruc ingresado es incorrecto", ""));
                }
                break;
            case "P":
                if (!personaEditar.getPerIdentificacion().trim().equals(auxIdentificacion)) {

                    if (perDisolucionConyugal) {
                        personaEditar.setPerDisolucionConyugal("S");
                    } else {
                        personaEditar.setPerDisolucionConyugal("N");
                    }
                    if (perCalidad) {
                        personaEditar.setPerCalidad("S");
                    } else {
                        personaEditar.setPerCalidad("N");
                    }
                    personaEditar.setPerMigrado("AC");
                    persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));

                } else {
                    if (perDisolucionConyugal) {
                        personaEditar.setPerDisolucionConyugal("S");
                    } else {
                        personaEditar.setPerDisolucionConyugal("N");
                    }
                    if (perCalidad) {
                        personaEditar.setPerCalidad("S");
                    } else {
                        personaEditar.setPerCalidad("N");
                    }
                    personaEditar.setPerMigrado("AC");
                    persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
                }
                break;
            default:
                break;
        }

//        clear();      
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PersonaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            setPersonaEditar(null); // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public Persona getPersona(Long id) {
        return getPersonaServicio().find(new Persona(), id);
    }

    public void filtrarPersona() {
        if (!(identificacion.isEmpty() || identificacion == null)
                || !(apellidoPaterno.isEmpty() || apellidoPaterno == null)
                || !(apellidoMaterno.isEmpty() || apellidoMaterno == null)
                || !(nombre.isEmpty() || nombre == null)) {
            List<String> params = new ArrayList<>();
            if (!(identificacion.isEmpty() || identificacion == null)) {
                setIdentificacion("PerIdentificacion LIKE '%" + identificacion.trim() + "%' ");
                params.add(identificacion);
            }
            if (!(apellidoPaterno.isEmpty() || apellidoPaterno == null)) {
                setApellidoPaterno("PerApellidoPaterno LIKE '%" + apellidoPaterno.trim().toUpperCase() + "%' ");
                params.add(apellidoPaterno);
            }
            if (!(apellidoMaterno.isEmpty() || apellidoMaterno == null)) {
                setApellidoMaterno("PerApellidoMaterno LIKE '%" + apellidoMaterno.trim().toUpperCase() + "%' ");
                params.add(apellidoMaterno);
            }
            if (!(nombre.isEmpty() || nombre == null)) {
                setNombre("PerNombre LIKE '%" + nombre.trim().toUpperCase() + "%' ");
                params.add(nombre);
            }
            items = personaServicio.listarPorIdent_Nom_ApeP_ApeM(params);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Llenar al menos un campo", ""));
        }
    }

    public void compConyuge() {
        if (personaEditar.getPerEstadoCivil().equals("C") || personaEditar.getPerEstadoCivil().equals("UH")) {
            disableConyuge = "false";
        } else {
            setCiConyuge("0");
            disableConyuge = "true";
        }
        if (personaEditar.getPerEstadoCivil().equals("S")) {
            try {
                personaEditar.setPerIdConyuge(servicioPersona.buscarConyugue(0));
            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
    }

    public void traerConyuge() throws ServicioExcepcion {
        try {
            if (!ciConyuge.trim().equals("")) {
                Persona auxPer = personaServicio.buscarPersona(ciConyuge.trim());
                if (auxPer != null) {
                    personaEditar.setPerIdConyuge(auxPer);
                } else {
                    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Persona no encontrada");
                    FacesContext.getCurrentInstance().addMessage("Aviso", facesMsg);
                }
            } else {
                ciConyuge = "";
                personaEditar.setPerIdConyuge(null);
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese identificación de conyuge", "");
                FacesContext.getCurrentInstance().addMessage("", facesMsg);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (personaEditar != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {

                    personaServicio.edit(personaEditar);
                    if (disableConyuge != null && disableConyuge.equals("false")) {
                        Persona conyugue = servicioPersona.buscarPersonaPorId(personaEditar.getPerIdConyuge().getPerId());
                        conyugue.setPerIdConyuge(personaEditar);
                        personaServicio.edit(conyugue);
                    }

                    clear();
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('PersonaEditDialog').hide();");
                    current.executeScript("PF('dlgFiltradoPersona').hide();");

                } else {
                    //personaServicio.remove(personaEditar);
                }
                JsfUtil.addSuccessMessage(successMessage);
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

}
