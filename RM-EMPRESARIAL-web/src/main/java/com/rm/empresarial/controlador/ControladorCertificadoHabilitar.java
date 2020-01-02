/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.dao.CertificadoActivacionDao;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoActivacion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.CertificadoServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorCertificadoHabilitar")
@ViewScoped
public class ControladorCertificadoHabilitar implements Serializable {

    /**
     * Creates a new instance of ControladorCertificadoHabilitar
     */
    @Inject
    DataManagerSesion dataManagerSesion;

    @EJB
    CertificadoServicio servicioCertificado;

    @EJB
    CertificadoCargaServicio servicioCertificadoCarga;

    @EJB
    CertificadoActivacionDao servicioCertificadoActivacion;

    @Getter
    @Setter
    private Date fchInicio;

    @Getter
    @Setter
    private Date fchFin;

    @Getter
    @Setter
    private List<Certificado> listaCertificadosBuscados;

    public ControladorCertificadoHabilitar() {
        fchInicio = Calendar.getInstance().getTime();
        fchInicio.setTime(0);
        fchFin = Calendar.getInstance().getTime();
        fchInicio.setTime(0);
    }

    @PostConstruct
    public void postControlador() {
        buscarPorFecha();
    }

    public void buscarPorFecha() {
        listaCertificadosBuscados = servicioCertificado.listarPorCerFHR(fchInicio, fchFin);
        actualizarCampoSeleccion();
    }

    public void actualizarCampoSeleccion() {
        for (Certificado cert : listaCertificadosBuscados) {
            CertificadoCarga certCarg = servicioCertificadoCarga.buscarCertCargPor_Certificado(cert.getCertificadoPK().getCerNumero());
            if (certCarg != null) {
                if (certCarg.getCdcActivo() != null) {
                    cert.setBolActivo(certCarg.getCdcActivo());
                }
            }

        }
    }

    public void cambiarEstadoCertificado(Certificado cert) {
        CertificadoCarga certCarg = servicioCertificadoCarga.buscarCertCargPor_Certificado(cert.getCertificadoPK().getCerNumero());

        if (cert.getBolActivo()) {
            certCarg.setCdcActivo(Boolean.TRUE);
            certCarg.setCdcEstadoProceso("ACTIVO");
            certCarg.setCdcEstado("A");
            servicioCertificadoCarga.edit(certCarg);
            crearCertificadoActivacion(cert.getCertificadoPK().getCerNumero(), cert.getBolActivo());
        } else {
            certCarg.setCdcActivo(Boolean.FALSE);
            servicioCertificadoCarga.edit(certCarg);
            crearCertificadoActivacion(cert.getCertificadoPK().getCerNumero(), cert.getBolActivo());
        }
    }

    public void crearCertificadoActivacion(String cerNumero, Boolean activado) {
        String user = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
        CertificadoActivacion certActv = new CertificadoActivacion(
                null, cerNumero, user, Calendar.getInstance().getTime());
        certActv.setCacActivacion(activado);
        servicioCertificadoActivacion.create(certActv);
    }

}
