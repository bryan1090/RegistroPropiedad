/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;

import com.rm.empresarial.controlador.DataManagerSesion;
import com.rm.empresarial.dao.FacturaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.EMensaje;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.servicio.FacturaServicio;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Wilson Herrera
 */
@Dependent
public class ControladorConsultaComprobantesBb implements Serializable {

    @Getter
    @Setter
    private List<Factura> listaFactura;
    
     @Getter
    @Setter
    private List<Factura> listaFacturaWeb;

    @Getter
    @Setter
    private Date fechaIinicio;

    @Getter
    @Setter
    private Date fechaFin;

    @Getter
    @Setter
    private String numeroFactura;

    @Getter
    @Setter
    private Factura facturaSeleccionada;

    @Getter
    @Setter
    private Boolean filtro = Boolean.FALSE;

    @Getter
    @Setter
    private EMensaje eMensaje;

    @EJB
    private FacturaServicio facturaServicio;
    
    @EJB
    private FacturaDao facturaDao;
    
     @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;


    @PostConstruct
    public void postControladorConsultaComprobantesBb() {
        this.fechaIinicio = Calendar.getInstance().getTime();
        this.fechaFin = Calendar.getInstance().getTime();
        try {
            setListaFacturaWeb(facturaDao.listarFacturaWeb(Calendar.getInstance().getTime(), Calendar.getInstance().getTime(),dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
            this.listaFactura = facturaServicio.listarFactura(Calendar.getInstance().getTime(), Calendar.getInstance().getTime());
        } catch (ServicioExcepcion | ParseException ex) {}
    }
    
    public ControladorConsultaComprobantesBb() {

    }

    public void init() {
        setListaFactura(new ArrayList<Factura>());
        setFacturaSeleccionada(new Factura());
        setEMensaje(new EMensaje());
    }

}
