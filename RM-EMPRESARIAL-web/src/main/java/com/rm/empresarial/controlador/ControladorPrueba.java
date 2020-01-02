/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.dao.ActaRazonDao;
import com.rm.empresarial.modelo.Persona;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorPrueba")
@ViewScoped
public class ControladorPrueba implements Serializable {

    /**
     * Creates a new instance of ControladorPrueba
     */
    @Inject
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    String nombre;
    @Getter
    @Setter
    String edad;
    @Getter
    @Setter
    Date fecha;
    @Getter
    @Setter
    List<String> combo;
    @Getter
    @Setter
    String seleccion;
    @Getter
    @Setter
    Persona personaSeleccionada;

    @EJB
    private ActaRazonDao actaRazonDao;

    @Getter
    @Setter
    List<String> lista;

    @Getter
    @Setter
    List<Persona> listaPersonas;

    public ControladorPrueba() {
        combo = new ArrayList<>();
        combo.add("opcion 1");
        combo.add("opcion 2");
    }

    @PostConstruct
    public void postConstructor() {
    }

    public void procesar() {
        actaRazonDao.obtenerRazonPornumActa(new Long(nombre));
    }

    public void llenarListaTexto() {
        lista = new ArrayList<>();
        lista.add("a");
        lista.add("b");
        lista.add("a");
    }

    public void llenarListaPersonas(boolean crearIDs) {
        listaPersonas = new ArrayList<Persona>();
        Persona persona1 = new Persona();
        Persona persona2 = new Persona();
        Persona persona3 = new Persona();
        Persona persona5 = new Persona();

        if (crearIDs) {
            persona1.setPerId(1L);
            persona2.setPerId(2L);
            persona3.setPerId(3L);
        }

        persona1.setPerNombre("Carlos");
        persona2.setPerNombre("Pedro");
        persona3.setPerNombre("Carlos");
        Persona persona4 = new Persona();
        persona4.setPerNombre("Andres");
        persona4.setPerId(2L);
        persona5.setPerNombre("Juan");

        listaPersonas.add(persona1);
        listaPersonas.add(persona2);
        listaPersonas.add(persona3);
        listaPersonas.add(persona4);
        listaPersonas.add(persona5);

    }

    public void guardar() {

    }

    public void eliminarTexto(String texto) {
        lista.remove(texto);
    }

    public void eliminarPersonaSel(Persona persona) {
        listaPersonas.remove(persona);
    }

    public void eliminarPersonaSelPorIndice(int indice) {
        listaPersonas.remove(indice);
    }

    public void eliminarPersonaSelPF() {
        listaPersonas.remove(personaSeleccionada);
    }

    public void seleccionarPersona() {
        System.out.println(personaSeleccionada.toString());

    }
}
