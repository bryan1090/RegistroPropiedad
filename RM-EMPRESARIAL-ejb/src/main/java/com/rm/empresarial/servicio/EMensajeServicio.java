/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.EMensajeDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.EMensaje;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class EMensajeServicio extends EMensajeDao implements Serializable {
    
        
    public void guardarMensaje(EMensaje mensaje) throws ServicioExcepcion{
        mensaje.setEMsIdSec(asignarID().longValue());
        guardarSalida(mensaje);
    }
    
     public BigDecimal asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.eMsIdSec) from EMensaje a");
            return ((BigDecimal) q.getSingleResult()).add(BigDecimal.ONE);
        } catch (Exception e) {
            return new BigDecimal(1);
        }
    }
     public Long asignarID1() {
     
        try {
            EMensaje resultado =obtenerPorConsultaJpaNombrada(EMensaje.OBTENER_ID, null);
           
           resultado.getEMsIdSec();
            return  resultado.getEMsIdSec()+1l;
        } catch (Exception e) {
            return new Long(1);
        }
    }
     
      public void guardar(EMensaje mensaje) throws ServicioExcepcion{
          guardarRetorno(mensaje);
          
    }
 }
