/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorVerPRO")
@ViewScoped
public class ControladorVerPRO implements Serializable {

    @Getter
    @Setter
    private Tramite tramite;
    
    @Getter
    @Setter
    private List<TramiteDetalle> tramiteDetalle;
    
    @Getter
    @Setter
    private List<TramiteValor> tramiteValor;
    
    @EJB
    private TramiteServicio servicioTramite;
    
    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;
    
    @EJB
    private TramiteValorServicio servicioTramiteValor;
    
    @Inject
    ReporteUtil reporteUtil;
    
    public ControladorVerPRO() {
    }
    
    public void clear(){
        setTramite(null);
        setTramiteDetalle(null);
        setTramiteValor(null);
    }
    
    public void mostrar(String doc){
        try {
            clear();
            setTramite(servicioTramite.buscarTramitePorNumero(Long.valueOf(doc)));
            setTramiteDetalle(servicioTramiteDetalle.buscarPorNumeroDeTramite(Long.valueOf(doc)));
            setTramiteValor(servicioTramiteValor.buscarPorTramite(servicioTramite.buscarTramitePorNumero(Long.valueOf(doc))));
        } catch (ServicioExcepcion ex) {}
    }
    
    public void generarReporte() {
        if (getTramite().getTraNumero() != null) {
            int Tramite = getTramite().getTraNumero().intValue();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numTramite", Tramite);
            try {
                reporteUtil.imprimirReporteEnPDF("repo", "proforma", parametros);
            } catch (JRException | IOException | NamingException | SQLException ex) {
                Logger.getLogger(TramitesControlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }
    
}
