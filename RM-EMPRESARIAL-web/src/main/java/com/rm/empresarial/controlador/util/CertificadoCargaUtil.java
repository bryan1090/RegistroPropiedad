/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.dao.CertificadoCargaDao;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Prueba
 */

@Dependent
public class CertificadoCargaUtil implements Serializable{

@Getter
@Setter
private CertificadoCarga certificadoCarga;

@Getter
@Setter
private CertificadoCarga certificadoCargaActualizar;

@Getter
@Setter
private List<CertificadoCarga> listaCertificadoCarga = new ArrayList<>();

@EJB
private CertificadoCargaDao certificadoCargaDao; 

@EJB
private CertificadoCargaServicio certificadoCargaServicio;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;
    


    public CertificadoCargaUtil() {
        certificadoCarga = new CertificadoCarga();
        
    }



    
    public void generarCertificadoCarga(String tipo, String nombrePagina, String usuarioLogueado, CertificadoCarga certificadoC){
        
         
        try {
            
            
            Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral(tipo, nombrePagina, 1);
            Long usuId = usuarioAsignado.getUsuId();
            
             //lista de Certifiado carga con estado "A"
            listaCertificadoCarga = certificadoCargaDao.listarPorEstadoPorUsrLogPorTipo(usuId,tipo);
            if(!listaCertificadoCarga.isEmpty() || !listaCertificadoCarga.equals(null)){
                  for (CertificadoCarga listCertificadoCarga : listaCertificadoCarga) {
                 listCertificadoCarga.setCdcEstado("I");
                 certificadoCargaDao.edit(listCertificadoCarga);
            }
                
            }
          
            
          
            
            certificadoCarga.setCdcTipo(tipo);
            certificadoCarga.setUsuId(usuarioAsignado);
            certificadoCarga.setCdcEstado("A");
            certificadoCarga.setCdcUser(usuarioLogueado);
            certificadoCarga.setCdcFHR(Calendar.getInstance().getTime());
                
            certificadoCargaServicio.create(certificadoCarga);
            
           
            
                
            
        } catch (Exception e) {
            System.out.println("com.rm.empresarial.controlador.util.CertificadoCargaUtil.certificadoCargaUtil()");
            System.out.println(e);
        }
               
        
        
    }
    
}
