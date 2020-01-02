/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.dao.CalendarioDao;
import com.rm.empresarial.dao.TiempoProcesoDao;
import com.rm.empresarial.dao.TramiteTiempoDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Parametros;
import com.rm.empresarial.modelo.TiempoProceso;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteTiempo;
import com.rm.empresarial.servicio.ParametrosServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Dependent
public class UtilFechaEntrega implements Serializable {

    @EJB
    TiempoProcesoDao servicioTiempoProceso;

    @EJB
    ParametrosServicio servicioParametros;

    @EJB
    TramiteServicio servicioTramite;

    @EJB
    TramiteTiempoDao servicioTramiteTiempo;

    @EJB
    CalendarioDao servicioCalendario;

    @Getter
    @Setter
    Parametros parametros;
    @Getter
    @Setter
    Date fechaEntrega;
    @Getter
    @Setter
    Calendar fechaEntregaAux;
    @Getter
    @Setter
    TiempoProceso tiempoProceso;

    @PostConstruct
    public void postConstructor() {
        try {
            cargarParametros("RVT");
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(UtilFechaEntrega.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarParametros(String tipoParametros) throws ServicioExcepcion {
        setParametros(servicioParametros.buscarPorParametro(tipoParametros));

    }

    public void calcularFechaEntregaTramitePor_Parametros(Long numTramite) {
        Boolean errorCalendario = false;
        int valorAdicional = getParametros().getPrmCantidadTiempo();
        Tramite tramite = servicioTramite.find(new Tramite(), numTramite);

        Calendar fechaEntr = Calendar.getInstance();
        if (getParametros().getPrmUnidadTiempo().equals("H")) {
            fechaEntr.add(Calendar.HOUR, valorAdicional);
        } else if (getParametros().getPrmUnidadTiempo().equals("D")) {
            for (int i = 0; i < valorAdicional; i++) {

                Boolean esDiaLaborable = null;
                Calendar diaSiguiente = new GregorianCalendar(fechaEntr.get(Calendar.YEAR), fechaEntr.get(Calendar.MONTH), fechaEntr.get(Calendar.DAY_OF_MONTH));
                diaSiguiente.add(Calendar.DAY_OF_YEAR, 1);
                esDiaLaborable = servicioCalendario.esDiaLaborable(diaSiguiente.getTime());
                if (esDiaLaborable != null) {
                    while (!esDiaLaborable) {
                        fechaEntr.add(Calendar.DAY_OF_YEAR, 1);
                        diaSiguiente.add(Calendar.DAY_OF_YEAR, 1);
                        esDiaLaborable = servicioCalendario.esDiaLaborable(diaSiguiente.getTime());
                    }
                    if (esDiaLaborable) {
                        fechaEntr.add(Calendar.DAY_OF_YEAR, 1);
                    }

                } else {
                    errorCalendario = true;
                }

            }
        }
        setFechaEntrega(fechaEntr.getTime());
        setFechaEntregaAux(fechaEntr);

        crearTramiteTiempo(tramite, getParametros().getPrmUnidadTiempo(),
                new Long(getParametros().getPrmCantidadTiempo()),
                getFechaEntrega(), null);

        if (errorCalendario) {
            JsfUtil.addWarningMessage("El calendario no ha sido configurado correctamente.");
        }
    }

    public void calcularFechaEntregaTramite(Long numTramite) {
        try {

            Tramite tramite = servicioTramite.find(new Tramite(), numTramite);
            Boolean errorCalendario = false;
            if (tramite != null) {
                tiempoProceso = servicioTiempoProceso.buscarTiempoProcesoPor_Tramite(tramite.getTraNumero());
                if(tiempoProceso==null)
                {
                 tiempoProceso = servicioTiempoProceso.buscarTiempoProcesoPor_Tramite2(tramite.getTraNumero());   
                }
                if (tiempoProceso == null) {
                    int valorAdicional = getParametros().getPrmCantidadTiempo();
                    Calendar fechaEntr = Calendar.getInstance();
                    if (getParametros().getPrmUnidadTiempo().equals("H")) {
                        fechaEntr.add(Calendar.HOUR, valorAdicional);
                    } else if (getParametros().getPrmUnidadTiempo().equals("D")) {
                        for (int i = 0; i < valorAdicional; i++) {

                            Boolean esDiaLaborable = null;
                            Calendar diaSiguiente = new GregorianCalendar(fechaEntr.get(Calendar.YEAR), fechaEntr.get(Calendar.MONTH), fechaEntr.get(Calendar.DAY_OF_MONTH));
                            diaSiguiente.add(Calendar.DAY_OF_YEAR, 1);
                            esDiaLaborable = servicioCalendario.esDiaLaborable(diaSiguiente.getTime());
                            if (esDiaLaborable != null) {
                                while (!esDiaLaborable) {
                                    fechaEntr.add(Calendar.DAY_OF_YEAR, 1);
                                    diaSiguiente.add(Calendar.DAY_OF_YEAR, 1);
                                    esDiaLaborable = servicioCalendario.esDiaLaborable(diaSiguiente.getTime());
                                }
                                if (esDiaLaborable) {
                                    fechaEntr.add(Calendar.DAY_OF_YEAR, 1);
                                }

                            } else {
                                errorCalendario = true;
                            }

                        }
                    }
                    setFechaEntrega(fechaEntr.getTime());
                    setFechaEntregaAux(fechaEntr);

                    crearTramiteTiempo(tramite, getParametros().getPrmUnidadTiempo(),
                            new Long(getParametros().getPrmCantidadTiempo()),
                            getFechaEntrega(), null);
                } else {
                    int valorAdicional = tiempoProceso.getTpoTiempo().intValue();
                    Calendar fechaEntr = Calendar.getInstance();
                    if (tiempoProceso.getTpoUnidadTiempo().equals("H")) {
                        fechaEntr.add(Calendar.HOUR, valorAdicional);
                    } else if (tiempoProceso.getTpoUnidadTiempo().equals("D")) {
                        for (int i = 0; i < valorAdicional; i++) {

                            Boolean esDiaLaborable = null;
                            Calendar diaSiguiente = new GregorianCalendar(fechaEntr.get(Calendar.YEAR), fechaEntr.get(Calendar.MONTH), fechaEntr.get(Calendar.DAY_OF_MONTH));
                            diaSiguiente.add(Calendar.DAY_OF_YEAR, 1);
                            esDiaLaborable = servicioCalendario.esDiaLaborable(diaSiguiente.getTime());

                            if (esDiaLaborable != null) {
                                while (!esDiaLaborable) {
                                    fechaEntr.add(Calendar.DAY_OF_YEAR, 1);
                                    diaSiguiente.add(Calendar.DAY_OF_YEAR, 1);
                                    esDiaLaborable = servicioCalendario.esDiaLaborable(diaSiguiente.getTime());
                                }
                                if (esDiaLaborable) {
                                    fechaEntr.add(Calendar.DAY_OF_YEAR, 1);
                                }
                            } else {
                                errorCalendario = true;
                            }
                        }

                    }
                    setFechaEntrega(fechaEntr.getTime());
                    setFechaEntregaAux(fechaEntr);
                    crearTramiteTiempo(tramite,
                            tiempoProceso.getTpoUnidadTiempo(),
                            tiempoProceso.getTpoTiempo().longValue(),
                            getFechaEntrega(),
                            tiempoProceso.getTtrId());
                }

            }
            if (errorCalendario) {
                JsfUtil.addWarningMessage("El calendario no ha sido configurado correctamente.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void crearTramiteTiempo(Tramite tramite, String unidadTiempo, Long cantidadTiempo, Date fechaEntrega, TipoTramite tipoTram) {
        //***tramite tiempo
        TramiteTiempo nuevoTramiteTiempo = new TramiteTiempo(
                null,
                unidadTiempo,
                cantidadTiempo,
                tramite.getTraEstado(),
                fechaEntrega);
        nuevoTramiteTiempo.setTraNumero(tramite);
        nuevoTramiteTiempo.setTtrId(tipoTram);
        servicioTramiteTiempo.create(nuevoTramiteTiempo);
    }

}
