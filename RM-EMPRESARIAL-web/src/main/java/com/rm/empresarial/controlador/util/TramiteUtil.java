/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.controlador.DataManagerSesion;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.Serializable;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Dependent
public class TramiteUtil implements Serializable {

    //servicios
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private TramiteServicio tramiteServicio;

    public TramiteUtil() {

    }

    @PostConstruct
    public void postConstructor() {

    }

    public void insertarTramiteAccion(Tramite tramite, String numDocumento, String Observacion, String estadoProceso, String estadoRegistro, String traUserAsignado) {
        //ingreso tramite accion
        try {
            
            TramiteAccion nuevoTramAccn = new TramiteAccion(null,
                    dataManagerSesion.getUsuarioLogeado().getUsuLogin(),
                    Calendar.getInstance().getTime(),
                    numDocumento,
                    estadoProceso, estadoRegistro);
            nuevoTramAccn.setTraNumero(tramite);
            nuevoTramAccn.setTmaObservacion(Observacion.toUpperCase());
            nuevoTramAccn.setTmaUserAsg(traUserAsignado);
            servicioTramiteAccion.edit(nuevoTramAccn);
            
            System.out.println("Tramite Acción generado");
        } catch (Exception e) {
            System.out.println("Error al ingresar Trámite Acción");
            e.printStackTrace();
        }

    }

    public void cambiarEstadoTramite(Tramite parametroTramite, String TraEstado, String TraEstadoRegistro) {
        //Actualiza estados de tramites en todo el proceso
        try {
            
            parametroTramite.setTraEstado(TraEstado);
            parametroTramite.setTraEstadoRegistro(TraEstadoRegistro);
            tramiteServicio.guardar(parametroTramite);
            System.out.println("Actualización de trámite realizada correctamente");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
