/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FacturaDetalleAdicional;
import com.rm.empresarial.modelo.FacturaDetalleImpuesto;
import com.rm.empresarial.puente.FacturaDetallePuente;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class FacturaDetalleServicio extends FacturaDetallePuente {

    @EJB
    private FacturaDetalleImpuestoServicio facturaDetalleImpuestoServicio;

    @EJB
    private FacturaDetalleAdicionalServicio facturaDetalleAdicionalServicio;

    /**
     * <B> METODO QUE GUARDA EL DETALLE DE LA FACTURA Y SUS DETALLES</B>
     *
     * @param factura
     * @return FacturaDetalle
     */
    public FacturaDetalle guardarFactura(FacturaDetalle facturaDet) {
        FacturaDetalleImpuesto detalleImpuesto = new FacturaDetalleImpuesto();
        FacturaDetalleAdicional detalleAdicional = new FacturaDetalleAdicional();
        try {
            // bFactura.setNumeroComprobante(factura.getFacNumero());
            //guardar detalle impuestos

            detalleImpuesto.setFdiCodigo(facturaDet.getFdiCodigo());
            detalleImpuesto.setFdiCodigoPorcentaje(facturaDet.getFdiCodigoPorcentaje());
            detalleImpuesto.setFdiTarifa(facturaDet.getFdiTarifa());
            detalleImpuesto.setFdiBaseImponible(facturaDet.getFdiBaseImponible());

            //detalleAdicional setep de valores 
            detalleAdicional.setFdaNombre("detalle adicional contrato");
            detalleAdicional.setFdaValor(facturaDet.getFdtTtrDescripcion());
            facturaDet.setFdtId(asignarID());
            guardarRetorno(facturaDet);

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(FacturaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacturaDetalle salida = find(facturaDet, facturaDet.getFdtId());
        System.out.print("Salida  " + salida);
        detalleImpuesto.setFdtId(salida);
        detalleAdicional.setFdtId(salida);
        try {
            facturaDetalleImpuestoServicio.guardarFactura(detalleImpuesto);
            facturaDetalleAdicionalServicio.guardarFactura(detalleAdicional);
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(FacturaDetalleServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (salida == null) {
            salida = find(facturaDet, facturaDet.getFdtId());
        }
        return salida;
    }

    public BigDecimal asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.fdtId) from FacturaDetalle a");
            return ((BigDecimal) q.getSingleResult()).add(BigDecimal.ONE);
        } catch (Exception e) {
            System.out.println(e);
            return new BigDecimal(1);
        }
    }

   
     public List<Factura> listarFacturaPor_Tramite(int fdtTraNumero) {
        Query q;
        try {
            q = getEntityManager().createQuery("SELECT fd.facId from FacturaDetalle fd WHERE fd.fdtTraNumero= :fdtTraNumero");
            q.setParameter("fdtTraNumero", fdtTraNumero);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
     
     public List<FacturaDetalle> listarPor_Tramite(int fdtTraNumero) {
        Query q;
        try {
            q = getEntityManager().createQuery("SELECT fd from FacturaDetalle fd WHERE fd.fdtTraNumero= :fdtTraNumero");
            q.setParameter("fdtTraNumero", fdtTraNumero);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    
}
