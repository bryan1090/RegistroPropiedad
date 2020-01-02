/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
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

/**
 *
 * @author Wilson
 */
@Named(value = "personaController")
@ViewScoped
public class PersonaController implements Serializable {

    private static final long serialVersionUID = 5604226631508490479L;

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
    private Persona selected;

    private JsfUtil jsfUtil;

    @PostConstruct
    public void cargarDatos() {
        jsfUtil = new JsfUtil();
        items = new ArrayList<>();
        disableConyuge = "true";
    }

    public void prepareEdit() {
        compConyuge();
        if (disableConyuge.equals("false")) {
            setCiConyuge(selected.getPerIdConyuge().getPerIdentificacion());
        }
        if (!(selected.getPerCalidad() == null || selected.getPerCalidad().isEmpty())) {
            setPerCalidad(selected.getPerCalidad().equals("S"));
            setPerDisolucionConyugal(selected.getPerDisolucionConyugal().equals("S"));
        }
        setAuxIdentificacion(selected.getPerIdentificacion());
    }

    public Persona prepareCreate() {
        selected = new Persona();
        return selected;
    }

    public void clear() {
        setCiConyuge(null);
        setPerCalidad(false);
        setPerDisolucionConyugal(false);
        setSelected(null);
    }

    public void create() throws ServicioExcepcion {
        switch (selected.getPerTipoIdentificacion()) {
            case "C":
                if (jsfUtil.validadorDeCedula(selected.getPerIdentificacion().trim())) {
                    if (!personaServicio.personaDuplicado(selected.getPerIdentificacion().trim())) {
                        if (perDisolucionConyugal) {
                            selected.setPerDisolucionConyugal("S");
                        } else {
                            selected.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            selected.setPerCalidad("S");
                        } else {
                            selected.setPerCalidad("N");
                        }
                        selected.setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        selected.setPerFHR(Calendar.getInstance().getTime());
                        selected.setPerId(personaServicio.asignarID());
                        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonaCreated"));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La Cédula ingresada ya existe", ""));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La Cédula ingresada es incorrecta", ""));
                }
                break;
            case "R":
                if (jsfUtil.validaRuc(selected.getPerIdentificacion())) {
                    if (!personaServicio.personaDuplicado(selected.getPerIdentificacion())) {
                        if (perDisolucionConyugal) {
                            selected.setPerDisolucionConyugal("S");
                        } else {
                            selected.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            selected.setPerCalidad("S");
                        } else {
                            selected.setPerCalidad("N");
                        }
                        selected.setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        selected.setPerFHR(Calendar.getInstance().getTime());
                        selected.setPerId(personaServicio.asignarID());
                        persist(JsfUtil.PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("PersonaCreated"));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Ruc ingresado ya existe", ""));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El Ruc ingresado es incorrecto", ""));
                }
                break;
            case "P":
                if (!personaServicio.personaDuplicado(selected.getPerIdentificacion())) {
                    if (perDisolucionConyugal) {
                        selected.setPerDisolucionConyugal("S");
                    } else {
                        selected.setPerDisolucionConyugal("N");
                    }
                    if (perCalidad) {
                        selected.setPerCalidad("S");
                    } else {
                        selected.setPerCalidad("N");
                    }
                    selected.setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    selected.setPerFHR(Calendar.getInstance().getTime());
                    selected.setPerId(personaServicio.asignarID());
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

    public void update() {
//        if (perDisolucionConyugal) {
//            selected.setPerDisolucionConyugal("S");
//        } else {
//            selected.setPerDisolucionConyugal("N");
//        }
//        if (perCalidad) {
//            selected.setPerCalidad("S");
//        } else {
//            selected.setPerCalidad("N");
//        }
//        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
        switch (selected.getPerTipoIdentificacion()) {
            case "C":
                if (jsfUtil.validadorDeCedula(selected.getPerIdentificacion().trim())) {
                    if (!selected.getPerIdentificacion().trim().equals(auxIdentificacion)) {
                        if (!personaServicio.personaDuplicado(selected.getPerIdentificacion().trim())) {
                            if (perDisolucionConyugal) {
                                selected.setPerDisolucionConyugal("S");
                            } else {
                                selected.setPerDisolucionConyugal("N");
                            }
                            if (perCalidad) {
                                selected.setPerCalidad("S");
                            } else {
                                selected.setPerCalidad("N");
                            }
                            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La Cédula ingresada ya existe", ""));
                        }
                    } else {
                        if (perDisolucionConyugal) {
                            selected.setPerDisolucionConyugal("S");
                        } else {
                            selected.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            selected.setPerCalidad("S");
                        } else {
                            selected.setPerCalidad("N");
                        }
                        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La Cédula ingresada es incorrecta", ""));
                }
                break;
            case "R":
                if (jsfUtil.validaRuc(selected.getPerIdentificacion())) {
                    if (!selected.getPerIdentificacion().trim().equals(auxIdentificacion)) {
                        if (!personaServicio.personaDuplicado(selected.getPerIdentificacion())) {
                            if (perDisolucionConyugal) {
                                selected.setPerDisolucionConyugal("S");
                            } else {
                                selected.setPerDisolucionConyugal("N");
                            }
                            if (perCalidad) {
                                selected.setPerCalidad("S");
                            } else {
                                selected.setPerCalidad("N");
                            }
                            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Ruc ingresado ya existe", ""));
                        }
                    } else {
                        if (perDisolucionConyugal) {
                            selected.setPerDisolucionConyugal("S");
                        } else {
                            selected.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            selected.setPerCalidad("S");
                        } else {
                            selected.setPerCalidad("N");
                        }
                        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El Ruc ingresado es incorrecto", ""));
                }
                break;
            case "P":
                if (!selected.getPerIdentificacion().trim().equals(auxIdentificacion)) {
                    if (!personaServicio.personaDuplicado(selected.getPerIdentificacion())) {
                        if (perDisolucionConyugal) {
                            selected.setPerDisolucionConyugal("S");
                        } else {
                            selected.setPerDisolucionConyugal("N");
                        }
                        if (perCalidad) {
                            selected.setPerCalidad("S");
                        } else {
                            selected.setPerCalidad("N");
                        }
                        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El Pasaporte ingresado ya existe", ""));
                    }
                } else {
                    if (perDisolucionConyugal) {
                        selected.setPerDisolucionConyugal("S");
                    } else {
                        selected.setPerDisolucionConyugal("N");
                    }
                    if (perCalidad) {
                        selected.setPerCalidad("S");
                    } else {
                        selected.setPerCalidad("N");
                    }
                    persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("PersonaUpdated"));
                }
                break;
            default:
                break;
        }
        clear();
        filtrarPersona();
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("PersonaDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            setSelected(null); // Remove selection
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
        if (selected.getPerEstadoCivil().equals("C") || selected.getPerEstadoCivil().equals("UH")) {
            disableConyuge = "false";
        } else {
            setCiConyuge(null);
            selected.setPerIdConyuge(null);
            disableConyuge = "true";
        }
    }

    public void traerConyuge() throws ServicioExcepcion {
        try {
            if (!ciConyuge.trim().equals("")) {
                Persona auxPer = personaServicio.buscarPersona(ciConyuge.trim());
                if (auxPer != null) {
                    selected.setPerIdConyuge(auxPer);
                } else {
                    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Persona no encontrada");
                    FacesContext.getCurrentInstance().addMessage("Aviso", facesMsg);
                }
            } else {
                ciConyuge = "";
                selected.setPerIdConyuge(null);
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese identificación de conyuge", "");
                FacesContext.getCurrentInstance().addMessage("", facesMsg);
            }
        } catch (Exception e) {
        }

    }

    private void persist(JsfUtil.PersistAction persistAction, String successMessage) {
        if (selected != null) {
            try {
                if (persistAction != JsfUtil.PersistAction.DELETE) {

                    personaServicio.edit(selected);
                } else {
                    personaServicio.remove(selected);
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
