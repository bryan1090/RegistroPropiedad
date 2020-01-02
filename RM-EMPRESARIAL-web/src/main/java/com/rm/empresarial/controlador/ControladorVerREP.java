/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorVerREP")
@ViewScoped
public class ControladorVerREP implements Serializable{
    
    @Getter
    @Setter
    private List<TramiteDetalle> listaTramiteDetalle;
    
    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;

    public ControladorVerREP() {
    }
    
    public void clear(){
        setListaTramiteDetalle(null);
    }
    
    public void mostrar(String doc){
        try{
            setListaTramiteDetalle(servicioTramiteDetalle.listarPorNumRepertorio(Integer.valueOf(doc)));
        }catch (ServicioExcepcion | NumberFormatException e){}
    }
    
    
}
