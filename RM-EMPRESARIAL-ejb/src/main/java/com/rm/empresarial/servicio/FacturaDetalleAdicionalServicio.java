/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.FacturaDetalleAdicionalDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.FacturaDetalleAdicional;
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
public class FacturaDetalleAdicionalServicio extends FacturaDetalleAdicionalDao implements Serializable {

    public void guardarFactura(FacturaDetalleAdicional facturaDetalleAdicional) throws ServicioExcepcion {
        facturaDetalleAdicional.setFdaId(asignarID());
        guardarSalida(facturaDetalleAdicional);
    }

    public BigDecimal asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.fdaId) from FacturaDetalleAdicional a");
            return ((BigDecimal) q.getSingleResult()).add(BigDecimal.ONE);
        } catch (Exception e) {
            System.out.println(e);
            return new BigDecimal(1);
        }
    }

    public boolean eliminarPor_FacturaDetalle(BigDecimal fdtId) {
        Query q;
        int numRegistrosBorrados = 0;
        try {
            q = getEntityManager().createQuery("DELETE FROM FacturaDetalleAdicional fda WHERE fda.fdtId.fdtId = :fdtId");
            q.setParameter("fdtId", fdtId);

            numRegistrosBorrados = q.executeUpdate();
            System.out.println("numRegistrosBorrados: " + numRegistrosBorrados);
            if (numRegistrosBorrados > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
