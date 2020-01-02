/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.FacturaInfoAdicionalDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.FacturaInfoAdicional;
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
public class FacturaInfoAdicionalServicio extends FacturaInfoAdicionalDao implements Serializable {
    
    public void guardarFactura(FacturaInfoAdicional facturaInfoAdicional) throws ServicioExcepcion{
        facturaInfoAdicional.setFiaId(asignarID());
        guardarSalida(facturaInfoAdicional);
    }
    
    public BigDecimal asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.fiaId) from FacturaInfoAdicional a");
            return ((BigDecimal) q.getSingleResult()).add(BigDecimal.ONE);
        } catch (Exception e) {
            return new BigDecimal(1);
        }
    }
    
    public boolean eliminarPor_Factura(BigDecimal facId) {
        Query q;
        try {
            q = getEntityManager().createQuery("DELETE FROM FacturaInfoAdicional fia WHERE fia.facId.facId = :facId");
            q.setParameter("facId", facId);
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
