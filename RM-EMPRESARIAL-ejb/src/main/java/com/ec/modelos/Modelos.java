/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.modelos;

import com.ec.integrador.EnviarModelosSRI;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.FacturaServicio;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
/**
 *
 * @author Wilson Herrera
 */
@LocalBean
@Singleton
public class Modelos implements Serializable{
    
     
    @Inject
    private EnviarModelosSRI controladorModelosSRI;
    
    @Lock(LockType.READ)
    //@Schedule(second = "0", minute = "35", hour = "16", dayOfWeek = "*")
     @Schedule(minute = "5,10,15,20,25,30,35,40,45,50,55,00", hour = "*") 
    public void execute() throws ServicioExcepcion, Exception {
        System.out.print("Ejecutado correctamente");
        obtnerFacturas();
    }
    public void obtnerFacturas() throws ServicioExcepcion, Exception{
       controladorModelosSRI.cargarModelos();
    }
}
