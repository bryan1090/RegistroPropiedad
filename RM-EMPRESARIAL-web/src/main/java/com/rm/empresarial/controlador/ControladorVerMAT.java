/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.servicio.LinderoServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorVerMAT")
@ViewScoped
public class ControladorVerMAT implements Serializable {

    @Getter
    @Setter
    private Propiedad propiedad;

    @Getter
    @Setter
    private List<Lindero> listaLindero;

    @EJB
    private PropiedadServicio servicioPropiedad;

    @EJB
    private LinderoServicio servicioLindero;

    public ControladorVerMAT() {
    }

    public void mostrar(TramiteAccion tramAccion) throws ServicioExcepcion {
        try {
            if (tramAccion.getTmaObservacion().contains("PROPIEDAD CREADA CON MATR")) {
                setPropiedad(servicioPropiedad.encontrarPropiedadPorId(tramAccion.getTmaNumeroDocumento()));
                setListaLindero(servicioLindero.listarPorNumMatricula(tramAccion.getTmaNumeroDocumento()));
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('MatDialogo').show();");

            } else if (tramAccion.getTmaObservacion().contains("ACTUALIZACIÓN DEL ESTADO DE TRAMITE")) {

            } else if (tramAccion.getTmaObservacion().contains("ASIGNADO REPERTORIO")) {

            } 

        } catch (ServicioExcepcion ex) {
            JsfUtil.addWarningMessage("Propiedad no encontrada");
        }
    }

}
