/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.servicio.PersonaServicio;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Java
 */
@Named(value = "controladorCrearPersona")
@ViewScoped
public class ControladorCrearPersona implements Serializable {

    @EJB
    private PersonaServicio personaServicio;

    @Getter
    @Setter
    private Persona persona;
    
    @Getter
    @Setter
    private Boolean disolucionConyugal;

    private JsfUtil jsfUtil;

    @EJB
    private PersonaDao personaDao;

    @Getter
    @Setter
    private String estadoBoton;

    @Inject
    private DataManagerSesion dataManagerSesion;

    /**
     * Creates a new instance of ControladorCrearPersona
     */
    public ControladorCrearPersona() {
        persona = new Persona();
        persona.setPerTipoIdentificacion("C");
        persona.setPerDireccion("");
        jsfUtil = new JsfUtil();
        disolucionConyugal = false;
    }

    public void crearPersona() {
        try {
            quitarEspacios();
            boolean identificacionCorrecta = true;
            getPersona().setPerDireccion(getPersona().getPerDireccion().toUpperCase());
            if(disolucionConyugal == true){
                getPersona().setPerDisolucionConyugal("S");
            }
            else{
                getPersona().setPerDisolucionConyugal("N");
            }                
            switch (getPersona().getPerTipoIdentificacion()) {
                case "C":

                    if (jsfUtil.validadorDeCedula(getPersona().getPerIdentificacion())) {
                        if (!personaServicio.personaDuplicado(getPersona().getPerIdentificacion())) {
                            getPersona().setPerId(personaServicio.asignarID());
                            getPersona().setPerEstado("A");
                            Persona personaConyugue = new Persona();
                            personaConyugue = personaServicio.buscarPersonaPorId((long) 0);
                            getPersona().setPerIdConyuge(personaConyugue);
                            getPersona().setPerTipoContribuyente("1");
                            getPersona().setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            if (getPersona().getPerApellidoMaterno().isEmpty() || getPersona().getPerApellidoMaterno() == null) {
                                getPersona().setPerApellidoMaterno(" ");
                            }
                            personaServicio.guardar(getPersona());
                            addSuccessMessage("Persona creada correctamente");
                            persona = new Persona();
                            getPersona().setPerTipoIdentificacion("C");
                        } else {
                            if (getPersona().getPerApellidoMaterno().isEmpty() || getPersona().getPerApellidoMaterno() == null) {
                                getPersona().setPerApellidoMaterno(" ");
                            }
                            personaDao.edit(persona);
                            addSuccessMessage("Persona Actualizado");
                            persona = new Persona();
                            getPersona().setPerTipoIdentificacion("C");
                        }
                    } else {
                        addErrorMessage("La Cédula ingresada es incorrecta");
                        persona = new Persona();
                        getPersona().setPerTipoIdentificacion("C");
                    }

                    break;
                case "R":

                    if (jsfUtil.validaRuc(getPersona().getPerIdentificacion())) {
                        if (!personaServicio.personaDuplicado(getPersona().getPerIdentificacion())) {
                            getPersona().setPerId(personaServicio.asignarID());
                            getPersona().setPerEstado("A");
                            Persona personaConyugue = new Persona();
                            personaConyugue = personaServicio.buscarPersonaPorId((long) 0);
                            if (getPersona().getPerApellidoMaterno().isEmpty() || getPersona().getPerApellidoMaterno() == null) {
                                getPersona().setPerApellidoMaterno(" ");
                            }
                            getPersona().setPerIdConyuge(personaConyugue);
                            getPersona().setPerTipoContribuyente("1");
                            getPersona().setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            if(getPersona().getPerApellidoPaterno().isEmpty() || getPersona().getPerApellidoPaterno() ==null)
                            {
                                getPersona().setPerApellidoPaterno(" ");
                            }
                            personaServicio.guardar(getPersona());
                            addSuccessMessage("Persona creada correctamente");
                            persona = new Persona();
                            getPersona().setPerTipoIdentificacion("R");
                        } else {
                            if (getPersona().getPerApellidoMaterno().isEmpty() || getPersona().getPerApellidoMaterno() == null) {
                                getPersona().setPerApellidoMaterno(" ");
                            }
                            personaDao.edit(persona);
                            addSuccessMessage("Persona Actualizado");
                            persona = new Persona();
                            getPersona().setPerTipoIdentificacion("R");
                        }
                    } else {
                        addErrorMessage("El Ruc ingresado es incorrecta");
                        persona = new Persona();
                        getPersona().setPerTipoIdentificacion("R");
                    }
                    break;

                case "P":
                    if (!personaServicio.personaDuplicado(getPersona().getPerIdentificacion())) {
                        getPersona().setPerId(personaServicio.asignarID());
                        getPersona().setPerEstado("A");
                        Persona personaConyugue = new Persona();
                        personaConyugue = personaServicio.buscarPersonaPorId((long) 0);
                        getPersona().setPerIdConyuge(personaConyugue);
                        getPersona().setPerTipoContribuyente("1");
                        getPersona().setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        if (getPersona().getPerApellidoMaterno().isEmpty() || getPersona().getPerApellidoMaterno() == null) {
                            getPersona().setPerApellidoMaterno(" ");
                        }
                        personaServicio.guardar(getPersona());
                        addSuccessMessage("Persona creada correctamente");
                        persona = new Persona();
                        getPersona().setPerTipoIdentificacion("P");

                    } else {
                        if (getPersona().getPerApellidoMaterno().isEmpty() || getPersona().getPerApellidoMaterno() == null) {
                            getPersona().setPerApellidoMaterno(" ");
                        }
                        personaDao.edit(persona);
                        addSuccessMessage("Persona Actualizado");
                        getPersona().setPerTipoIdentificacion("P");

                    }
                    break;
            }
            limpiar();

        } catch (ServicioExcepcion ex) {
            JsfUtil.addErrorMessage("Error al crear/actualizar la Persona");
            Logger.getLogger(PersonaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
//public void crearPersona() {
//        try {
//            boolean identificacionCorrecta = true;
//            switch (getPersona().getPerTipoIdentificacion()) {
//                case "C":
//
//                    if (!jsfUtil.validadorDeCedula(getPersona().getPerIdentificacion())) {
//                        addErrorMessage("La Cédula ingresada es incorrecta");
//                        identificacionCorrecta = false;
//                    }
//
//                    break;
//                case "R":
//
//                    if (!jsfUtil.validaRuc(getPersona().getPerIdentificacion())) {
//                        addErrorMessage("El RUC ingresado es incorrecto");
//                        identificacionCorrecta = false;
//                    }
//                    break;
//            }
//
//            if (!personaServicio.personaDuplicado(getPersona().getPerIdentificacion())) {
//                getPersona().setPerId(personaServicio.asignarID());
//                getPersona().setPerEstado("A");
//                Persona personaConyugue = new Persona();
//                personaConyugue = personaServicio.buscarPersonaPorId((long) 0);
//                getPersona().setPerIdConyuge(personaConyugue);
//                getPersona().setPerTipoContribuyente("1");
//
//                personaServicio.guardar(getPersona());
//                addSuccessMessage("Persona creada correctamente");
//                persona = new Persona();
//
//            } else {
//                personaDao.edit(persona);
//                addSuccessMessage("Usuario Actualizado");
//
//            }
//
//        } catch (ServicioExcepcion ex) {
//            JsfUtil.addErrorMessage("Error al crear la Persona");
//            Logger.getLogger(PersonaUtil.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

    public void validarCedula() {

        switch (getPersona().getPerTipoIdentificacion()) {
            case "C":

                if (!jsfUtil.validadorDeCedula(getPersona().getPerIdentificacion())) {
                    addErrorMessage("La Cédula ingresada es incorrecta");
                }
                break;
            case "R":

                if (!jsfUtil.validaRuc(getPersona().getPerIdentificacion())) {
                    addErrorMessage("El RUC ingresado es incorrecto");
                }
                break;
        }

    }

    public void limpiar() {
        setPersona(new Persona());
        getPersona().setPerTipoIdentificacion("C");
        getPersona().setPerDisolucionConyugal("N");
        disolucionConyugal = false;
    }

    public void quitarEspacios() {
        
        if (!(persona.getPerIdentificacion() == null || persona.getPerIdentificacion().isEmpty())) {
            persona.setPerIdentificacion(persona.getPerIdentificacion().trim());
        }
        if (!(persona.getPerApellidoPaterno() == null || persona.getPerApellidoPaterno().isEmpty())) {
            persona.setPerApellidoPaterno(persona.getPerApellidoPaterno().trim());
        }
        if (!(persona.getPerApellidoMaterno() == null || persona.getPerApellidoMaterno().isEmpty())) {
            persona.setPerApellidoMaterno(persona.getPerApellidoMaterno().trim());
        }
        if (!(persona.getPerNombre() == null || persona.getPerNombre().isEmpty())) {
            persona.setPerNombre(persona.getPerNombre().trim());
        }
        if (!(persona.getPerEmail() == null || persona.getPerEmail().isEmpty())) {
            persona.setPerEmail(persona.getPerEmail().trim());
        }
        if (!(persona.getPerDireccion() == null || persona.getPerDireccion().isEmpty())) {
            persona.setPerDireccion(persona.getPerDireccion().trim());
        }
    }

    public void traer() throws ServicioExcepcion {
        try {
            if (!getPersona().getPerIdentificacion().trim().equals("")) {
                Persona auxPer = personaDao.buscarPersona(getPersona().getPerIdentificacion().trim());
                if (auxPer != null) {
                    setPersona(auxPer);
//                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Ya existe Persona");
//                FacesContext.getCurrentInstance().addMessage("Info", facesMsg);
                } else {
                    String perIdentificacion = persona.getPerIdentificacion();
                    limpiar();
                    persona.setPerIdentificacion(perIdentificacion);
                    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "Persona no encontrada");
                    FacesContext.getCurrentInstance().addMessage("Aviso", facesMsg);
                }
            } else {
                getPersona().setPerIdentificacion("");
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la identificación", "");
                FacesContext.getCurrentInstance().addMessage("", facesMsg);
            }
        } catch (Exception e) {
        }

    }

}
