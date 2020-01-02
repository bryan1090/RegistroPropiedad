/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class FacturaDetalleDao extends Generico<FacturaDetalle> implements Serializable {

    public FacturaDetalle encontrarPorNumeroTramite(int traNumero) throws ServicioExcepcion {

        Query q = getEntityManager().createNamedQuery(FacturaDetalle.ENCONTRAR_POR_NUMERO_TRAMITE);
        q.setParameter("fdtTraNumero", traNumero);
        q.setMaxResults(1);
        return (FacturaDetalle) q.getSingleResult();
    }

    public List<FacturaDetalle> listarPorFacId(BigDecimal facId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("facId", facId);
        return listarPorConsultaJpaNombrada(FacturaDetalle.LISTAR_POR_FACID, parametros);
    }

    public FacturaDetalle PorNumeroFactura(String nFactura) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", nFactura);
        try {
            return (FacturaDetalle) obtenerPorConsultaNativa("select TOP 1 * from FacturaDetalle where FacId= (SELECT FacId FROM Factura WHERE Factura.FacNumero = ?  ) ", parametros, FacturaDetalle.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public FacturaDetalle PorNumeroTramite(int nTramite) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", nTramite);
        try {
            return (FacturaDetalle) obtenerPorConsultaNativa("select TOP 1 * from FacturaDetalle where FacturaDetalle.FdtTraNumero= ? ", parametros, FacturaDetalle.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public List<FacturaDetalle> listarNumtramite(int traNumero) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fdtTraNumero", traNumero);
            return listarPorConsultaJpaNombrada("FacturaDetalle.findByFdtTraNumero", parametros);
        } catch (Exception e) {
            return null;
        }
    }

    public List<FacturaDetalle> listarPorNumtramite(Long traNumero) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("fdtTraNumero", traNumero);
            return listarPorConsultaJpaNombrada("FacturaDetalle.findByFdtTraNumero", parametros);
        } catch (Exception e) {
            return null;
        }
    }

    public List<FacturaDetalle> lisarPorNumeroFactura(String nFactura) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", nFactura);
        try {
            return listarPorConsultaNativa("select  * from FacturaDetalle where FacId IN (SELECT FacId FROM Factura WHERE Factura.FacNumero = ?  ) ", parametros);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
    
     public List<FacturaDetalle> listarPorNumeroFactura(String numFactura) throws ServicioExcepcion {
        String sql = "SELECT  * FROM FacturaDetalle WHERE FacId IN (SELECT FacId FROM Factura WHERE Factura.FacNumero = ?  )";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numFactura);
        
        return listarPorConsultaNativa(sql, parametros, FacturaDetalle.class);
    }
     

    public List<FacturaDetalle> reporteFactura(Date fechaIni, Date fechaFin) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        return listarPorConsultaNativa("SELECT * "
                + "FROM\n"
                + "dbo.Factura\n"
                + "INNER JOIN dbo.FacturaDetalle ON dbo.FacturaDetalle.FacId = dbo.Factura.FacId\n"
                + "WHERE Factura.FacTipo='FAC' AND CONVERT(DATE,Factura.FacFecha)>=?\n"
                + "AND CONVERT(DATE,Factura.FacFecha)<=? ", parametros, FacturaDetalle.class);
    }

    public boolean eliminarFacturaDetallePor_Tramite(Long traNumero) {
        Query q;
        try {
            q = getEntityManager().createQuery("DELETE FROM FacturaDetalle fd WHERE fd.fdtTraNumero = :traNumero");
            q.setParameter("traNumero", traNumero);
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarPor_Factura(BigDecimal facId) {
        Query q;
        try {
            q = getEntityManager().createQuery("DELETE FROM FacturaDetalle fd WHERE fd.facId.facId = :facId");
            q.setParameter("facId", facId);
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public List<Integer> listarTramitePorFactura(String facNumero) {
        Query q = getEntityManager().createNativeQuery("SELECT DISTINCT FacturaDetalle.FdtTraNumero FROM FacturaDetalle "
                + "INNER JOIN Factura ON Factura.FacId = FacturaDetalle.FacId WHERE Factura.FacNumero = ?");
        q.setParameter("1", facNumero);
        return q.getResultList();
    }
}
