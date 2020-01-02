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
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.servicio.PersonaServicio;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Java
 */
@Named(value = "controladorCrearPersonaTramiteProforma")
@ViewScoped
public class ControladorCrearPersonaTramiteProforma implements Serializable {

    @EJB
    private PersonaServicio personaServicio;

    @Getter
    @Setter
    private Persona persona;
    
    @Getter
    @Setter
    private Persona personaCompareciente;
    
    @Getter
    @Setter
    private Boolean readOnly;
    
    @Getter
    @Setter
    private String textoBoton;

    private JsfUtil jsfUtil;
    @Inject
    private DataManagerSesion dataManagerSesion;

    /**
     * Creates a new instance of ControladorCrearPersona
     */
    public ControladorCrearPersonaTramiteProforma() {
        persona = new Persona();
        personaCompareciente = new Persona();
        persona.setPerDireccion("");
        jsfUtil = new JsfUtil();
    }

    public void crearPersona() {
        try {
            boolean identificacionCorrecta = true;
            switch (getPersona().getPerTipoIdentificacion()) {
                case "C":

                    if (!jsfUtil.validadorDeCedula(getPersona().getPerIdentificacion())) {
                        addErrorMessage("La Cédula ingresada es incorrecta");
                        identificacionCorrecta = false;
                    }

                    break;
                case "R":

                    if (!jsfUtil.validaRuc(getPersona().getPerIdentificacion())) {
                        addErrorMessage("El RUC ingresado es incorrecto");
                        identificacionCorrecta = false;
                    }
                    break;
            }

            if (!personaServicio.personaDuplicado(getPersona().getPerIdentificacion())) {
                getPersona().setPerId(personaServicio.asignarID());
                getPersona().setPerEstado("A");
                //Persona personaConyugue = new Persona();
                //personaConyugue = personaServicio.buscarPersonaPorId((long) 0);
                getPersona().setPerIdConyuge(personaCompareciente);
                getPersona().setPerTipoContribuyente("1");
                personaServicio.guardar(getPersona());
                personaCompareciente.setPerIdConyuge(getPersona());
                getPersona().setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                personaServicio.edit(personaCompareciente);
                
                addSuccessMessage("Persona creada correctamente");
                persona = new Persona();
                personaCompareciente = new Persona(); 

            } else {
                //addErrorMessage("La identificación ya se encuentra registrada");  
               if(getPersona().getPerId() != null){
                   personaServicio.edit(persona);
                   personaCompareciente.setPerIdConyuge(getPersona());
                    getPersona().setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    personaServicio.edit(personaCompareciente);
                persona = new Persona();  
                personaCompareciente = new Persona();  
               }
               else{
                   //JsfUtil.addErrorMessage("La persona ya existe. No se puede crear nuevamente.");
//                    personaServicio.edit(getPersona());
//                    personaCompareciente.setPerIdConyuge(getPersona().getPerIdConyuge());
//                    personaServicio.edit(personaCompareciente);
               }
                
            }

        } catch (ServicioExcepcion ex) {
            JsfUtil.addErrorMessage("Error al crear la Persona");
            Logger.getLogger(PersonaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
     

    }

    public void cargarDatosPorIdentifcacion() throws ServicioExcepcion {

        try {
            switch (getPersona().getPerTipoIdentificacion()) {
            case "C":

                if (!jsfUtil.validadorDeCedula(getPersona().getPerIdentificacion())) {
                    addErrorMessage("La Cédula ingresada es incorrecta");
                }
                else{
                     Persona personaCony = new Persona();
        personaCony = personaServicio.encontrarPersonaPorIdentificacion(getPersona().getPerIdentificacion());
        if(personaCony.getPerId()!= 0){
            setPersona(personaCony);            
            getPersona().setPerIdConyuge(personaCompareciente);
            
        }
                }
                break;
            case "R":

                if (!jsfUtil.validaRuc(getPersona().getPerIdentificacion())) {
                    addErrorMessage("El RUC ingresado es incorrecto");
                }
                 else{
                     Persona personaCony = new Persona();
        personaCony = personaServicio.encontrarPersonaPorIdentificacion(getPersona().getPerIdentificacion());
        if(persona.getPerId()!= 0){
            setPersona(personaCony); 
            getPersona().setPerIdConyuge(personaCompareciente);
        }
                }
                break;
                
        }
            
        } catch (Exception e) {
        }
        
       
        

    }

    public void limpiar() {
        persona = new Persona();
    }

}
