/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.dao.TipoCertificadoDao;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.TipoCertificado;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DESARROLLO
 */
@Named("controladorCertificadoBuscar")
@ViewScoped
public class ControladorCertificadoBuscar implements Serializable {

    @EJB
    private TipoCertificadoDao servicioTipoCertificado;

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;
    
    @Inject
    @Getter
    @Setter
    private ControladorCertificado controladorCertificado;

    @EJB
    private CertificadoCargaServicio servicioCertificadoCarga;
    
    @Getter
    @Setter
    private String tipoGravamen;
    
    @Getter
    @Setter
    private Integer numTrabajosAsignados = 0;

    @Getter
    @Setter
    private Integer numTrabajosHabilitados = 0;
    
    

    public ControladorCertificadoBuscar() {
    }

    @PostConstruct
    public void postConstructor() {
    }

    public List<CertificadoCarga> llenarTablaCertificadoCarga(String tipoCertificado) 
    {   
        List<CertificadoCarga> listaCertificadoCarga = new ArrayList<>();
        Long usuarioId = dataManagerSesion.getUsuarioLogeado().getUsuId();
        TipoCertificado tipoCertificadoSeleccionado = servicioTipoCertificado.buscarPorTipo(tipoCertificado);
        if (tipoCertificadoSeleccionado != null) {
            listaCertificadoCarga = servicioCertificadoCarga.listarPor_Usuario_TipoCertificadoEnFactura_TipoCertificadoCarga_Estado_EstadoProcesoActPrc(new BigDecimal(usuarioId), tipoCertificadoSeleccionado.getTroId(), "CER", "A");
            getControladorCertificado().setListaCertificadoCarga(listaCertificadoCarga);
        }
            getControladorCertificado().setTipoCertificadoSeleccionado(tipoCertificadoSeleccionado);
        return listaCertificadoCarga;
    }
    
    public Long calcularNumTrbAsig()
    {
        List<String> listaEstadosRegistro=new ArrayList<>();
        listaEstadosRegistro.add("ACTIVO");
        listaEstadosRegistro.add("PROCESO");
                Long idUsuario = dataManagerSesion.getUsuarioLogeado().getUsuId();
        return servicioCertificadoCarga.numCerPendientesPor_Usuario_tipo_estado_estadoProceso_activo(idUsuario,"CER","A",listaEstadosRegistro,Boolean.FALSE );
    }
    public Long calcularNumTrbHab()
    {
        List<String> listaEstadosRegistro=new ArrayList<>();
        listaEstadosRegistro.add("ACTIVO");
        listaEstadosRegistro.add("PROCESO");
                Long idUsuario = dataManagerSesion.getUsuarioLogeado().getUsuId();
        return servicioCertificadoCarga.numCerPendientesPor_Usuario_tipo_estado_estadoProceso_activo(idUsuario,"CER","A",listaEstadosRegistro,Boolean.TRUE );
    }
}
