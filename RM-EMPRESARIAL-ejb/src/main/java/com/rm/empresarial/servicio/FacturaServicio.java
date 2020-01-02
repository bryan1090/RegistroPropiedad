/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

//import cde.sri.modelo.BFactura;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.puente.FacturaPuente;
import java.math.BigDecimal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class FacturaServicio extends FacturaPuente {

    public Factura guardarFactura(Factura factura) {
        try {
            factura.setFacId(asignarID());
            guardarSalida(factura);

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(FacturaServicio.class.getName()).log(Level.SEVERE, null, ex);
        }
        Factura salida = find(factura, factura.getFacId());

        return factura;

    }

    public BigDecimal asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.facId) from Factura a");
            return ((BigDecimal) q.getSingleResult()).add(BigDecimal.ONE);
        } catch (Exception e) {
            return new BigDecimal(1);
        }
    }
    
     public boolean eliminarPor_Factura(BigDecimal facId) {
        Query q;
        try {
            q = getEntityManager().createQuery("DELETE FROM Factura f WHERE f.facId = :facId");
            q.setParameter("facId", facId);
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

}
