/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaFormaPago;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
public class FacturaFormaDePagoDao extends Generico<FacturaFormaPago> implements Serializable {
    
  public void guardarFormaPago(FacturaFormaPago facturaFormaPago) throws ServicioExcepcion{
      guardarSalida(facturaFormaPago);
  } 
  public List<FacturaFormaPago> listarPorFactura(Long factura) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", factura);
         List<FacturaFormaPago> lista = listarPorConsultaNativa("SELECT * FROM FacturaFormaPago  WHERE  FacId = ? " , parametros,FacturaFormaPago.class);
        return lista; 
    }
    public List<FacturaFormaPago> listarPorFecha(Date fecha,String estado) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1",fecha);
          parametros.put("2",estado);
         List<FacturaFormaPago> lista = listarPorConsultaNativa("SELECT * FROM FacturaFormaPago \n" +
"INNER JOIN Factura ON FacturaFormaPago.FacId = Factura.FacId\n" +
"WHERE  CONVERT(DATE, FacturaFormaPago.FfpFHR) = ? AND (Factura.FacEstadoCierre = ? OR Factura.FacEstadoCierre is NULL ) AND Factura.FacTipo='FAC' order by FacturaFormaPago.FfpDescripcion  " , parametros,FacturaFormaPago.class);
        return lista; 
    }
  
   public FacturaFormaPago PorFactura(Long facId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", facId);
        try {
            return (FacturaFormaPago) obtenerPorConsultaNativa("SELECT * FROM FacturaFormaPago  WHERE  FacId = ? ", parametros, FacturaFormaPago.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
   
   public boolean eliminarPor_Factura(BigDecimal facId) {
        Query q;
        try {
            q = getEntityManager().createQuery("DELETE FROM FacturaFormaPago ffp WHERE ffp.facId.facId = :facId");
            q.setParameter("facId", facId);
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
