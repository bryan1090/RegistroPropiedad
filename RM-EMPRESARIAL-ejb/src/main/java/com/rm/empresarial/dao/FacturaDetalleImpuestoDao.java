/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FacturaDetalleImpuesto;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Wilson Herrera
 */
@LocalBean
@Stateless
public class FacturaDetalleImpuestoDao extends Generico<FacturaDetalleImpuesto> implements Serializable {

    public FacturaDetalleImpuesto buscarPorComprobante(FacturaDetalle facturaDetalle) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("fdtId", facturaDetalle);
        try {
            return obtenerPorConsultaJpaNombrada(FacturaDetalleImpuesto.BUSCAR_POR_DETALLE_FACTURA, parametros);
        } catch (Exception e) {
            return null;
        }

    }

    public boolean eliminarPor_FacturaDetalle(BigDecimal fdtId) {
        Query q;
        int numRegistrosBorrados = 0;
        try {
            q = getEntityManager().createQuery("DELETE FROM FacturaDetalleImpuesto fdi WHERE fdi.fdtId.fdtId = :fdtId");
            q.setParameter("fdtId", fdtId);
            numRegistrosBorrados = q.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
