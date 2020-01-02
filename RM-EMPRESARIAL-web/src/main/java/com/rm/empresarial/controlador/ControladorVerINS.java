/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.servicio.ActaServicio;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorVerINS")
@ViewScoped
public class ControladorVerINS implements Serializable {

    @Getter
    @Setter
    private Acta acta;

    @Getter
    @Setter
    private Tramite tramite;

    @EJB
    private ActaServicio servicioActa;
    
    public void clear(){
        setTramite(null);
        setActa(null);
    }

    public void mostrar(String doc, Tramite tramite) throws ServicioExcepcion {
        clear();
        if (servicioActa.obtenerPorNActa(Long.valueOf(doc)) != null) {
            setActa(servicioActa.obtenerPorNActa(Long.valueOf(doc)));
            setTramite(tramite);
        } else {
            JsfUtil.addWarningMessage("Acta no encontrada");
        }
    }

}
