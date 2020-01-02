/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorVerMRG")
@ViewScoped
public class ControladorVerMRG implements Serializable{
    
    @Getter
    @Setter
    private Marginacion marginacion;
    
    @Getter
    @Setter
    private List<Marginacion> listaMarginacion;
    
    @Getter
    @Setter
    private Acta acta;
    
    @Getter
    @Setter
    private Tramite tramite;
    
    @EJB
    private MarginacionServicio servicioMarginacion;
    
    @EJB
    private ActaServicio servicioActa;

    public ControladorVerMRG() {
    }
    
    public void mostrarListaActas(String doc, Tramite tram){
        try {
            setActa(null);
            setTramite(tram);
            setListaMarginacion(servicioMarginacion.listarPorMrgAlt2(doc));
        } catch (ServicioExcepcion ex) {}
    }
    
    public void mostrarActa(Long ActNumero, String MrgAlt2){
        try {
            setActa(servicioActa.obtenerPorNActa(ActNumero));
            setListaMarginacion(servicioMarginacion.listarPorMrgAlt2YActNum(MrgAlt2, getActa().getActNumero()));
        } catch (ServicioExcepcion ex) {}
    }
    
}
