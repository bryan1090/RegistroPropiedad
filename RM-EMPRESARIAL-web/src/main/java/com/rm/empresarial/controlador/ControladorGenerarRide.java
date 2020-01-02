/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.dao.PathDocEleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.PathDocEle;
import doel.sri.bridge.ConfiguracionRepositorio;
import doel.sri.servicios.ServicioArchivo;
import doel.sri.util.Util;
import ec.rideexport.util.RideExport;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;


/**
 *
 * @author Wilson Herrera
 */
@Dependent
public class ControladorGenerarRide extends JsfUtil implements Serializable  {
    
    @EJB
    private PathDocEleDao pathDocEleDao;
    @Getter
    @Setter
    private PathDocEle pathElec;
    public String generarRide(String numeroComprobante,String claveAcceso,String fechaAutorizacionString,String xmlPuro ) throws InterruptedException {
       
        String numeroAutorizacion = claveAcceso;//en offline la clave de acceso es el numero de autorizacion
        try {
            setPathElec(pathDocEleDao.obtenerPaths());
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorGenerarRide.class.getName()).log(Level.SEVERE, null, ex);
        }
        ConfiguracionRepositorio configuracionRepositorio = new ConfiguracionRepositorio();
        
        String pathRepositorioTemp = getPathElec().getPdeRepTemporal();
        String nombreArchivoPdf = Util.aString("RIDE-", numeroComprobante, ".pdf");//RIDE-001-001-000000001.pdf
        String pathLogo = getPathElec().getPdeLogo();
        InputStream inputStream = null;
        try {
            inputStream = RideExport.export(pathLogo, numeroAutorizacion, fechaAutorizacionString, xmlPuro.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            addErrorMessage("Error al procesar ride");
            Logger.getLogger(ControladorGenerarRide.class.getName()).log(Level.SEVERE, null, ex);
        }

        //almacenar PDF
        ServicioArchivo servicioA = new ServicioArchivo();
        //servicioA.almacenar_File(pathArchivoXml, claseEmail.getXmlConAutorizacion());//esta en NULL, revisar
        String pathArchivoPdf = servicioA.almacenar_EnRepositorio(pathRepositorioTemp, nombreArchivoPdf, inputStream);
        
        System.out.println("RIDE generador correctamente en " + pathArchivoPdf);

       return pathArchivoPdf;
    }
}
