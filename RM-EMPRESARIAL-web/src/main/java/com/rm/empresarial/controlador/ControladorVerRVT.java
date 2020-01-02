/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
@Named(value = "controladorVerRVT")
@ViewScoped
public class ControladorVerRVT implements Serializable{

    @Getter
    @Setter
    private Tramite tramite;
    
    @EJB
    private TramiteServicio servicioTramite;
    
    @Inject
    ReporteUtil reporteUtil;
    
    public ControladorVerRVT() {
    }
    
    public void clear(){
        setTramite(null);
    }
    
    public void mostrar(String doc){
        try {
            clear();
            setTramite(servicioTramite.buscarTramitePorNumero(Long.valueOf(doc)));
        } catch (ServicioExcepcion ex) {}
    }
    
    public void generarReporte() {
        if (getTramite().getTraNumero() != null) {
            int numTramite = getTramite().getTraNumero().intValue();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("NumeroTramite", numTramite);
            try {
                reporteUtil.imprimirReporteEnPDF("IngresoTramite", "reporteTramite", parametros);
            } catch (JRException | IOException | NamingException | SQLException ex) {
                Logger.getLogger(RecepcionDocumentacionControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
