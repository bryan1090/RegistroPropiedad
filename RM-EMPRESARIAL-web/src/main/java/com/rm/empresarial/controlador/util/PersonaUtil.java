/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.controlador.ControladorEditarPersona;
import com.rm.empresarial.controlador.DataManagerSesion;
import com.rm.empresarial.controlador.RecepcionDocumentacionControlador;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.RepositorioRC;
import com.rm.empresarial.modelo.RepositorioSRI;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.RepositorioRCServicio;
import com.rm.empresarial.servicio.RepositorioSRIServicio;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Bryan_Mora
 */
@Dependent
public class PersonaUtil implements Serializable {
//SERVICIOS

    @EJB
    private PersonaServicio servicioPersona;

    @EJB
    private RepositorioSRIServicio repositorioSRIServicio;

    @EJB
    private RepositorioRCServicio repositorioRCServicio;

    @EJB
    private PersonaServicio personaServicio;

    @Inject
    @Getter
    @Setter
    private ControladorEditarPersona controladorEditarPersona;
//OBJETOS
    @Getter
    @Setter
    private Persona persona;

    @Inject
    private DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private RepositorioSRI repositorioSRI;

    @Getter
    @Setter
    private RepositorioRC repositorioRC;

    //VARIABLES
    @Getter
    @Setter
    private String identificacion;

    public PersonaUtil() {
        repositorioSRI = new RepositorioSRI();
        repositorioRC = new RepositorioRC();
        identificacion = "";

    }

    public Persona obtenerDatosPersona(String numIdentificacion) throws ServicioExcepcion {

        persona = new Persona();
        setIdentificacion(numIdentificacion);
        try {

            setPersona(servicioPersona.encontrarPersonaPorIdentificacion(getIdentificacion()));
            if (persona.getPerMigrado() != null) {
                if (persona.getPerMigrado().equals("SI")) {
                    getControladorEditarPersona().setPersonaEditar(persona);
//                    getControladorEditarPersona().getPersonaEditar().setPerTipoIdentificacion("C");
                    setPersona(new Persona());
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('PersonaEditDialog').show();");
                } else {
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('dlgFiltradoPersona').hide();");
                }
            } else {
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dlgFiltradoPersona').hide();");
            }

        } catch (Exception e) {

            try {

                setRepositorioRC(repositorioRCServicio.buscarPorIdentificacion(getIdentificacion()));
                transformarPersonaRC();
                crearPersona();
                setPersona(servicioPersona.encontrarPersonaPorIdentificacion(getIdentificacion()));
                if (persona.getPerMigrado() != null) {
                    if (persona.getPerMigrado().equals("SI")) {
                        getControladorEditarPersona().setPersonaEditar(persona);
                        setPersona(new Persona());
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('PersonaEditDialog').show();");
                    } else {
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('dlgFiltradoPersona').hide();");
                    }
                } else {
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('dlgFiltradoPersona').hide();");
                }
            } catch (Exception i) {

                try {
                    setRepositorioSRI(repositorioSRIServicio.buscarPorIdentificacion(getIdentificacion()));
                    transformarPersonaSRI();
                    crearPersona();
                    setPersona(servicioPersona.encontrarPersonaPorIdentificacion(getIdentificacion()));
                    if (persona.getPerMigrado() != null) {
                        if (persona.getPerMigrado().equals("SI")) {
                            getControladorEditarPersona().setPersonaEditar(persona);
                            setPersona(new Persona());
                            PrimeFaces current = PrimeFaces.current();
                            current.executeScript("PF('PersonaEditDialog').show();");
                        } else {
                            PrimeFaces current = PrimeFaces.current();
                            current.executeScript("PF('dlgFiltradoPersona').hide();");
                        }
                    } else {
                        PrimeFaces current = PrimeFaces.current();
                        current.executeScript("PF('dlgFiltradoPersona').hide();");
                    }
                } catch (Exception ex) {
                    JsfUtil.addErrorMessage("Persona no existe");
                    Logger.getLogger(RecepcionDocumentacionControlador.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return persona;
    }

    public void transformarPersonaSRI() {
        getPersona().setPerId(personaServicio.asignarID());
        getPersona().setPerTipoIdentificacion("R");
        getPersona().setPerIdentificacion(getRepositorioSRI().getSriRuc());
        getPersona().setPerNombre(getRepositorioSRI().getSriRazonSocial());
        getPersona().setPerApellidoPaterno(" ");
        getPersona().setPerDireccion(getRepositorioSRI().getSriCalle() + " " + getRepositorioSRI().getSriInterseccion());
        getPersona().setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());

    }

    public void transformarPersonaRC() {
        try {

            getPersona().setPerId(personaServicio.asignarID());
            getPersona().setPerTipoContribuyente(getRepositorioRC().getRrcTipoContribuyente());
            getPersona().setPerTipoIdentificacion(getRepositorioRC().getRrcTipoIdentificacion());
            getPersona().setPerIdentificacion(getRepositorioRC().getRrcIdentificacion());
            getPersona().setPerNombre(getRepositorioRC().getRrcNombre());
            getPersona().setPerApellidoPaterno(getRepositorioRC().getRrcApellidoPaterno());
            getPersona().setPerFechaNacimiento(getRepositorioRC().getRrcFechaNacimiento());
            getPersona().setPerFechaDefuncion(getRepositorioRC().getRrcFechaDefuncion());
            getPersona().setPerTelefono(getRepositorioRC().getRrcTelefono());
            getPersona().setPerCelular(getRepositorioRC().getRrcCelular());
            getPersona().setPerDireccion(getRepositorioRC().getRrcDireccion());
            getPersona().setPerEmail(getRepositorioRC().getRrcEmail());
            getPersona().setPerEstadoCivil(getRepositorioRC().getRrcEstadoCivil());
            getPersona().setPerCalidad(getRepositorioRC().getRrcCalidad());
            getPersona().setPerSexo(getRepositorioRC().getRrcSexo());
            getPersona().setPerUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());

        } catch (Exception e) {
            System.out.println("Error " + e);
            Logger.getLogger(RecepcionDocumentacionControlador.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    public void crearPersona() {
        try {
            servicioPersona.guardar(getPersona());
            persona = new Persona();

        } catch (ServicioExcepcion ex) {
            JsfUtil.addErrorMessage("Error al crear la Persona");
            Logger.getLogger(PersonaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
