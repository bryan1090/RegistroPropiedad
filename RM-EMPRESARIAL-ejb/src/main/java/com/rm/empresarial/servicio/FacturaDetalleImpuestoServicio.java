/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.FacturaDetalleImpuestoDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.FacturaDetalleImpuesto;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Wilson Herrera
 */
@LocalBean
@Stateless
public class FacturaDetalleImpuestoServicio extends FacturaDetalleImpuestoDao implements Serializable
{
 
    public void guardarFactura(FacturaDetalleImpuesto facturaDetalleImpuesto) throws ServicioExcepcion{
       facturaDetalleImpuesto.setFdiId(asignarID());
        guardarSalida(facturaDetalleImpuesto);
    }
    
     public BigDecimal asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.fdiId) from FacturaDetalleImpuesto a");
            return ((BigDecimal) q.getSingleResult()).add(BigDecimal.ONE);
        } catch (Exception e) {
            return new BigDecimal(1);
        }
    }
     
    
}
