/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class TramiteValorDao extends Generico<TramiteValor> implements Serializable {

    private static final long serialVersionUID = 5175358047653047732L;

    @EJB
    private PropiedadServicio servicioPropiedad;

    public Tramite buscarPorNumeroTramite(int TraNumero) {
        Query q;
        try {
            Tramite tramite = new Tramite();
            q = getEntityManager().createNativeQuery("SELECT * FROM Tramite WHERE Tramite.TraNumero = ?", Tramite.class);
            q.setParameter("1", TraNumero);

            tramite = (Tramite) q.getSingleResult();
            return tramite;
        } catch (Exception e) {
            return null;
        }
    }

    public List<TramiteValor> buscarPorTramite(Tramite tramite) throws ServicioExcepcion {
        Query q;
        q=getEntityManager().createQuery("SELECT t FROM TramiteValor t WHERE t.traNumero = :traNumero");
        q.setParameter("traNumero", tramite);
        return q.getResultList();
    }
  
    
    public List<TramiteValor> buscarPorTramite_NoFacturado(Tramite tramite) throws ServicioExcepcion {
        Query q;
        q=getEntityManager().createQuery("SELECT t FROM TramiteValor t WHERE t.traNumero = :traNumero and t.trvFacturado <> 'F'");
        q.setParameter("traNumero", tramite);
        return q.getResultList();
    }
     public List<TramiteValor> buscarPorTramite_Facturado(Tramite tramite) throws ServicioExcepcion {
        Query q;
        q=getEntityManager().createQuery("SELECT t FROM TramiteValor t WHERE t.traNumero = :traNumero and t.trvFacturado = 'F'");
        q.setParameter("traNumero", tramite);
        return q.getResultList();
    }

    public void guardarTramite(TramiteValor tramiteValor) throws ServicioExcepcion {
        guardarSalida(tramiteValor);
    }

    public TramiteValor buscarPorTipo(Tramite tramite, TipoTramite tipoTramite) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", tramite);
        parametros.put("ttrId", tipoTramite);
        try {
            return obtenerPorConsultaJpaNombrada(TramiteValor.BUSCAR_POR_TRAMITE_TIPO, parametros);
        } catch (Exception e) {
            return null;
        }

    }

    public List<TramiteValor> listarPorTipo(Tramite tramite) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", tramite);
        return listarPorConsultaJpaNombrada(TramiteValor.BUSCAR_POR_TRAMITE, parametros);
    }

    public boolean eliminarTramiteValor(Long TraNumero) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("DELETE FROM TramiteValor WHERE TraNumero=?");
            q.setParameter("1", TraNumero);
            q.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }
    
    public List<TramiteValor> buscarPorTramiteT(Tramite tramite) throws ServicioExcepcion {
        Query q;
            q = getEntityManager().createQuery("SELECT t FROM TramiteValor t WHERE t.traNumero = :traNumero");
        q.setParameter("traNumero", tramite);
        return q.getResultList();
    }
    
    public List<TramiteValor> buscarPorTramiteReingreso(Tramite tramite) throws ServicioExcepcion {
        Query q;
        q=getEntityManager().createQuery("SELECT t FROM TramiteValor t WHERE t.traNumero = :traNumero");
        q.setParameter("traNumero", tramite);
        return q.getResultList();
    }

    /* */
    public List<TramiteValor> ListarPor_TipoTramite_NumTramite_Estado(Tramite tramite, TipoTramite tipoTramite) throws ServicioExcepcion {
        List<TramiteValor> resultado;
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", tramite);
        parametros.put("ttrId", tipoTramite);
        resultado = listarPorConsultaJpaNombrada(TramiteValor.BUSCAR_POR_TRAMITE_TIPO, parametros);
        for (Iterator<TramiteValor> iterator = resultado.iterator(); iterator.hasNext();) {
            TramiteValor next = iterator.next();
            if (next.getTraNumPredio() == null || next.getTrvNumCatastro() == null) {
                iterator.remove();
            }
        }
        return resultado;
    }

//    public List<TramiteValor> ListarPor_TipoTramite_NumTramite_Estado_TtcPropietario(Tramite tramite, TipoTramite tipoTramite) throws ServicioExcepcion {
//        List<TramiteValor> resultado;
//        System.out.println("numTramite: " + tramite.getTraNumero());
//        System.out.println("tipoTramite: " + tipoTramite.getTtrId());
////        Query q= getEntityManager().createNativeQuery(sqlString)
//        Query q = getEntityManager().createNativeQuery("SELECT t.* FROM TramiteValor t WHERE t.traNumero = ? AND t.trvEstado='A' and t.ttrId IN (Select td.TdtTtrId from TramiteDetalle td INNER JOIN TipoTramiteCompareciente ttc ON td.TtcId=ttc.TtcId where td.TdtTtrId=? and ttc.TtcPropietario='S' and td.TraNumero= ? )"
//                , TramiteValor.class);
//
//        q.setParameter("1", tramite.getTraNumero());
//        q.setParameter("2", tipoTramite.getTtrId());
//        q.setParameter("3", tramite.getTraNumero());
//
//        resultado = q.getResultList();
//        for (Iterator<TramiteValor> iterator = resultado.iterator(); iterator.hasNext();) {
//            TramiteValor next = iterator.next();
//            if (next.getTraNumPredio() == null || next.getTrvNumCatastro() == null) {
//                iterator.remove();
//            }
//        }
//        return resultado;
//    }
    public List<TramiteValor> ListarPor_TipoTramite_NumTramite_Estado_CertificadoNulo(Tramite tramite, TipoTramite tipoTramite) throws ServicioExcepcion {
        List<TramiteValor> resultado;
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", tramite);
        parametros.put("ttrId", tipoTramite);
        resultado = listarPorConsultaJpaNombrada(TramiteValor.BUSCAR_POR_TRAMITE_TIPO_CERTIFICADO_NULL, parametros);
        for (Iterator<TramiteValor> iterator = resultado.iterator(); iterator.hasNext();) {
            TramiteValor next = iterator.next();
            if (next.getTraNumPredio() == null || next.getTrvNumCatastro() == null) {
                iterator.remove();
            }
        }
        return resultado;
    }

    public List<TramiteValor> ListarPor_NumTramite_Estado(Tramite tramite) throws ServicioExcepcion {
        Query q;
        List<TramiteValor> resultado;
        q = getEntityManager().createQuery("SELECT t FROM TramiteValor t WHERE t.traNumero = :traNumero AND t.trvEstado='A' AND t.trvCertificado IS NULL");
        q.setParameter("traNumero", tramite);
        resultado = q.getResultList();
        for (Iterator<TramiteValor> iterator = resultado.iterator(); iterator.hasNext();) {
            TramiteValor next = iterator.next();
            if (next.getTraNumPredio() == null || next.getTrvNumCatastro() == null) {
                iterator.remove();
            }
        }
        return resultado;
    }

    public TramiteValor obtenerPor_NumTramite_Propiedad(Long numTramite, String predio, String catastro) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM TramiteValor t WHERE t.traNumero.traNumero = :numTramite AND t.trvNumCatastro =:catastro AND t.traNumPredio =:predio");
        q.setParameter("catastro", catastro);
        q.setParameter("predio", predio);
        q.setParameter("numTramite", numTramite);
        try {
            return (TramiteValor) q.getSingleResult();
        } catch (Exception e) {
//            Logger.getLogger(TramiteValorDao.class.toString()).log(Level.SEVERE, "Error en la consulta ", e);
            return null;
        }

    }

    public List<TramiteValor> ListarPor_Propiedad(String catastro) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM TramiteValor t WHERE t.trvNumCatastro =:catastro");
        q.setParameter("catastro", catastro);
        return q.getResultList();
    }

    public Boolean existePorPropiedad(Long numTramite, String catastro, String predio) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM TramiteValor t WHERE t.traNumero.traNumero = :numTramite AND  t.trvNumCatastro =:catastro and t.traNumPredio =:predio");
        q.setParameter("catastro", catastro);
        q.setParameter("predio", predio);
        q.setParameter("numTramite", numTramite);

        return !(q.getResultList().isEmpty());

    }
//    public int eliminarPor_Propiedad(Long catastro, Long traNumero) throws ServicioExcepcion {
//        Query q;
////        q = getEntityManager().createQuery("Delete FROM TramiteValor t WHERE t.trvNumCatastro =:catastro");
//        q = getEntityManager().createQuery("SELECT COUNT(t) FROM TramiteValor t WHERE t.trvNumCatastro =:catastro AND t.traNumero.traNumero =:traNumero");
//
//        q.setParameter("catastro", catastro);
//        q.setParameter("traNumero", traNumero);
//
////        return q.executeUpdate();
//        return ((Long) q.getSingleResult()).intValue();
//    }

    public boolean eliminarTramiteValorPorNumTramitePorCatastroPorPredio(Long TraNumero, String predio, String catastro) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("DELETE FROM TramiteValor WHERE TraNumero=? AND TraNumPredio =? AND TrvNumCatastro");
            q.setParameter("1", TraNumero);
            q.setParameter("2", predio);
            q.setParameter("3", catastro);
            q.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public List<TramiteValor> listarPorNumTramitePorTipoTramite(Long TraNumero, Long idTipoTramite) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        String sql = "SELECT tv.* FROM TramiteValor tv WHERE tv.TraNumero = ? AND tv.TraNumPredio IS NOT NULL AND tv.TrvNumCatastro IS NOT NULL"
                + " AND tv.TtrId = ?";
        parametros.put("1", TraNumero);
        parametros.put("2", idTipoTramite);

        List<TramiteValor> lista = listarPorConsultaNativa(sql, parametros, TramiteValor.class);
        return lista;
    }

    public int actualizarEstado(Long numTramite, String numPredio, String numCatastro) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("UPDATE TramiteValor tv SET tv.trvEstado='A' "
                + " WHERE tv.traNumero.traNumero = :numTramite "
                + " AND tv.traNumPredio = :numPredio AND tv.trvNumCatastro = :numCatastro");
        q.setParameter("numTramite", numTramite);
        q.setParameter("numPredio", numPredio);
        q.setParameter("numCatastro", numCatastro);

        return q.executeUpdate();
    }

    public TramiteValor obtenerPor_NumTramite_TipoTramite_Castrato_Predio(Long numTramite, Long idTipoTramite, String predio, String catastro) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM TramiteValor t "
                + " WHERE t.traNumero.traNumero = :numTramite AND "
                + " t.trvNumCatastro =:catastro AND t.traNumPredio =:predio"
                + " AND t.ttrId.ttrId = :idTipoTramite");
        q.setParameter("catastro", catastro);
        q.setParameter("predio", predio);
        q.setParameter("numTramite", numTramite);
        q.setParameter("idTipoTramite", idTipoTramite);        
        try {
            return (TramiteValor) q.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(TramiteValorDao.class.toString()).log(Level.SEVERE, "Error en la consulta ", e);
            return null;
        }

    }
    
    public TramiteValor obtenerPor_NumTramite_TipoTramite_Castrato_Predio_Estado(Long numTramite, Long idTipoTramite, String predio, String catastro, String estado) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM TramiteValor t "
                + " WHERE t.traNumero.traNumero = :numTramite AND "
                + " t.trvNumCatastro =:catastro AND t.traNumPredio =:predio"
                + " AND t.ttrId.ttrId = :idTipoTramite AND t.trvEstado =:estado ");
        q.setParameter("catastro", catastro);
        q.setParameter("predio", predio);
        q.setParameter("numTramite", numTramite);
        q.setParameter("idTipoTramite", idTipoTramite);        
        q.setParameter("estado", estado);        
        try {
            return (TramiteValor) q.getSingleResult();
        } catch (Exception e) {
            Logger.getLogger(TramiteValorDao.class.toString()).log(Level.SEVERE, "Error en la consulta ", e);
            return null;
        }

    }
    
//    public TramiteValor obtenerPor_NumTramite_TipoTramite_Castrato_O_Predio(Long numTramite, String predio, String catastro) throws ServicioExcepcion {
//        Query q;
//        q = getEntityManager().createQuery("SELECT t FROM TramiteValor t "
//                + " WHERE t.traNumero.traNumero = :numTramite "
//                + " AND t.trvNumCatastro =:catastro OR t.traNumPredio =:predio"
//                + " AND t.trvCertificado IS NOT NULL");
//        q.setParameter("catastro", catastro);
//        q.setParameter("predio", predio);
//        q.setParameter("numTramite", numTramite);        
//        q.setMaxResults(1);
//        try {
//            return (TramiteValor) q.getSingleResult();
//        } catch (Exception e) {
//            Logger.getLogger(TramiteValorDao.class.toString()).log(Level.SEVERE, "Error en la consulta ", e);
//            return null;
//        }
//
//    }
    
     public TramiteValor obtenerPor_NumTramite_TipoTramite_Castrato_O_Predio(Long numTramite, String predio, String catastro) throws ServicioExcepcion {
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("1", numTramite);
      parametros.put("2", catastro);
      parametros.put("3", predio);  
      String sql = "SELECT TOP 1 * FROM TramiteValor"
                + " WHERE TraNumero = ? "
                + " AND (TrvNumCatastro = ? OR TraNumPredio = ?)"
                + " AND TrvCertificado IS NOT NULL";
      try {
          return (TramiteValor) obtenerPorConsultaNativa(sql, parametros, TramiteValor.class);
      } catch (ServicioExcepcion ex) {
          System.err.println(ex);
          return null;
      }
    }

    public List<TramiteValor> listarPorFecha(Date fechaIni, Date fechaFin) {

        String sql = "Select * from TramiteValor\n"
                + ",Acta\n"
                + "INNER JOIN Repertorio ON Acta.RepNumero = Repertorio.RepNumero\n"
                + "INNER JOIN TipoTramite ON Repertorio.RepTtrId = TipoTramite.TtrId\n"
                + "INNER JOIN Propiedad on Propiedad.PrdMatricula = Acta.PrdMatricula\n"
                + "INNER JOIN TipoPropiedad on TipoPropiedad.TpdId = Propiedad.TpdId \n"
                + "INNER JOIN Parroquia on Propiedad.PrdTdtParId = Parroquia.ParId  \n"
                + "INNER JOIN Ciudad on Ciudad.CiuId = Parroquia.CiuId \n"
                + "INNER JOIN Canton on Canton.CanId = Ciudad.CanId \n"
                + "where CONVERT(DATE,Acta.ActFechaIngreso) >= ? AND CONVERT(DATE,Acta.ActFechaIngreso) <= ? AND TipoTramite.TrCodigoAgrupacion1='UAFE' AND Repertorio.TraNumero = TramiteValor.TraNumero \n"
                + "AND TramiteValor.TtrId = TipoTramite.TtrId ";
        Query q = getEntityManager().createNativeQuery(sql, TramiteValor.class);
        q.setParameter("1", fechaIni);
        q.setParameter("2", fechaFin);
        return q.getResultList();

    }

//    public List<TramiteValor> todosComparecientesTienenPropiedadesSelec(Integer numRepertorio, Long traNumero, String ttcPropietario) throws ServicioExcepcion {
//        Query q;
//        q=getEntityManager().createQuery("SELECT t FROM TramiteDetalle t where t.tdtNumeroRepertorio= :numRepertorio AND t.traNumero.traNumero = :traNumero AND t.ttcId.ttcPropietario = :ttcPropietario");
//    }
    public List<TramiteValor> buscarTraValor(int numTramite) {
        String sql = "SELECT * "
                + "from TramiteValor "
                + "where TraNumero= ? AND "
                + "TrvValor > '0.00' AND "
                + "(TrvFacturado <> 'F' OR "
                + "TrvFacturado IS NULL)";
        Query q = getEntityManager().createNativeQuery(sql, TramiteValor.class);
//		Query q = getEntityManager().createQuery(TramiteValor.BUSCAR_TOTAL_TRA_VALOR, String.class);
//		q.setParameter("traNumero", numTramite);
        q.setParameter("1", numTramite);
        return q.getResultList();
    }

}
