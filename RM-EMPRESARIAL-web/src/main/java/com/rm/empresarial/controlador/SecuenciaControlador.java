/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.SecuenciaControladorBb;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.servicio.CajaServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
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
 * @author WILSON
 */
@Dependent
public class SecuenciaControlador implements Serializable {

    private static final long serialVersionUID = 4908393225372482685L;

    @Inject
    @Getter
    @Setter
    private SecuenciaControladorBb controladorBb;

    @Inject
    DataManagerSesion dataManagerSession;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @EJB
    private CajaServicio servicioCaja;

    @EJB
    private FacturaServicio servicioFactura;

    @PostConstruct
    public void inicializar() {
        getControladorBb().inicializar();
    }

    public void generarSecuencia(String codigo) throws ServicioExcepcion {
        int añoActual = (Calendar.getInstance()).get(Calendar.YEAR) % 100;
        int auxAñoActual = (Calendar.getInstance()).get(Calendar.YEAR);
        try {
            getControladorBb().setSecuencia(secuenciaServicio.obtenerSecuencia(codigo));
            if (getControladorBb().getSecuencia().getSecAnio() == añoActual) {
                if (getControladorBb().getSecuencia().getSecAnio() == añoActual) {
                    short auxAnio = getControladorBb().getSecuencia().getSecAnio();
                    int auxSec = getControladorBb().getSecuencia().getSecActual();
                    int año = auxAnio * 1000000;
                    int secuencia = auxSec + 1;
                    getControladorBb().setNumeroTramite(año + secuencia);
                } else {
                    getControladorBb().getSecuencia().setSecAnio((short) añoActual);
                    getControladorBb().getSecuencia().setSecActual(0);
                    getControladorBb().getSecuencia().setSecUser(dataManagerSession.getUsuarioLogeado().getUsuLogin());
                    getControladorBb().getSecuencia().setSecFHR(Calendar.getInstance().getTime());
                    secuenciaServicio.edit(getControladorBb().getSecuencia());

                    short auxAnio = getControladorBb().getSecuencia().getSecAnio();
                    int auxSec = getControladorBb().getSecuencia().getSecActual();
                    int año = auxAnio * 1000000;
                    int secuencia = auxSec + 1;
                    getControladorBb().setNumeroTramite(año + secuencia);
                }
            } else if (getControladorBb().getSecuencia().getSecAnio() == auxAñoActual) {
                if (getControladorBb().getSecuencia().getSecAnio() == auxAñoActual) {
                    short auxAnio = getControladorBb().getSecuencia().getSecAnio();
                    int auxSec = getControladorBb().getSecuencia().getSecActual();
                    int año = auxAnio * 1000000;
                    int secuencia = auxSec + 1;
                    getControladorBb().setNumeroTramite(año + secuencia);
                } else {
                    getControladorBb().getSecuencia().setSecAnio((short) auxAñoActual);
                    getControladorBb().getSecuencia().setSecActual(0);
                    getControladorBb().getSecuencia().setSecUser(dataManagerSession.getUsuarioLogeado().getUsuLogin());
                    getControladorBb().getSecuencia().setSecFHR(Calendar.getInstance().getTime());
                    secuenciaServicio.edit(getControladorBb().getSecuencia());

                    short auxAnio = getControladorBb().getSecuencia().getSecAnio();
                    int auxSec = getControladorBb().getSecuencia().getSecActual();
                    int año = auxAnio * 1000000;
                    int secuencia = auxSec + 1;
                    getControladorBb().setNumeroTramite(año + secuencia);
                }
            }
        } catch (ServicioExcepcion e) {
            if (getControladorBb().getSecuencia().getSecAnio() == añoActual) {
                getControladorBb().setSecuencia(new Secuencia());
                getControladorBb().getSecuencia().setSecCodigo(codigo);
                getControladorBb().getSecuencia().setSecAnio((short) añoActual);
                getControladorBb().getSecuencia().setSecActual(0);
                getControladorBb().getSecuencia().setSecUser(dataManagerSession.getUsuarioLogeado().getUsuLogin());
                getControladorBb().getSecuencia().setSecFHR(Calendar.getInstance().getTime());

                secuenciaServicio.create(getControladorBb().getSecuencia());
            } else if (getControladorBb().getSecuencia().getSecAnio() == auxAñoActual) {
                getControladorBb().setSecuencia(new Secuencia());
                getControladorBb().getSecuencia().setSecCodigo(codigo);
                getControladorBb().getSecuencia().setSecAnio((short) auxAñoActual);
                getControladorBb().getSecuencia().setSecActual(0);
                getControladorBb().getSecuencia().setSecUser(dataManagerSession.getUsuarioLogeado().getUsuLogin());
                getControladorBb().getSecuencia().setSecFHR(Calendar.getInstance().getTime());

                secuenciaServicio.create(getControladorBb().getSecuencia());
            }
        }

    }

    public void generarSecuenciaFactura(Long usuId) throws ServicioExcepcion {
        getControladorBb().setSecuenciaFactura(servicioFactura.generarSecuenciaFac(usuId));
//		int añoActual = (Calendar.getInstance()).get(Calendar.YEAR) % 100;
//		getControladorBb().setSecuencia(secuenciaServicio.obtenerSecuencia(codigo));
//		if (getControladorBb().getSecuencia().getSecAnio() == añoActual) {
//			short auxAnio = getControladorBb().getSecuencia().getSecAnio();
//			int auxSec = getControladorBb().getSecuencia().getSecActual();
//			int año = auxAnio * 10000000;
//			int secuencia = auxSec + 1;
//			getControladorBb().setNumeroTramite(año + secuencia);
//		} else {
//			getControladorBb().getSecuencia().setSecAnio((short) añoActual);
//			getControladorBb().getSecuencia().setSecActual(0);
//			short auxAnio = getControladorBb().getSecuencia().getSecAnio();
//			int auxSec = getControladorBb().getSecuencia().getSecActual();
//			int año = auxAnio * 10000000;
//			int secuencia = auxSec + 1;
//			getControladorBb().setNumeroTramite(año + secuencia);
//		}
    }

    public void actualizarSecuenciaFac(Long usuId) {
        servicioFactura.actualizarSecuenciaFac(usuId);
    }

    public void generarSecuenciaMatricula(String codigo) throws ServicioExcepcion {
        int numeroDigitosMatricula = 7;
        int añoActual = (Calendar.getInstance()).get(Calendar.YEAR) % 100;
        getControladorBb().setSecuencia(secuenciaServicio.obtenerSecuencia(codigo));
        if (getControladorBb().getSecuencia().getSecAnio() == añoActual) {
            int auxSec = getControladorBb().getSecuencia().getSecActual();
            int secuencia = auxSec + 1;
            int secuenciaLongitud = String.valueOf(secuencia).length();
            int cantidadCeros = numeroDigitosMatricula - secuenciaLongitud;
            String strCeros = "";
            for (int i = 1; i <= cantidadCeros; i++) {
                strCeros = strCeros + "0";
            }
            getControladorBb().setNumeroMatricula(strCeros + String.valueOf(secuencia));
        } else {
            getControladorBb().getSecuencia().setSecAnio((short) añoActual);
            getControladorBb().getSecuencia().setSecActual(0);
            int auxSec = getControladorBb().getSecuencia().getSecActual();
            int secuencia = auxSec + 1;
            int secuenciaLongitud = String.valueOf(secuencia).length();
            int cantidadCeros = numeroDigitosMatricula - secuenciaLongitud;
            String strCeros = "";
            for (int i = 1; i <= cantidadCeros; i++) {
                strCeros = strCeros + "0";
            }
            getControladorBb().setNumeroMatricula(strCeros + String.valueOf(secuencia));
        }

    }

}
