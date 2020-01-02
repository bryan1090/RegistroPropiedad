/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteUsuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class TramiteDetalleDao extends Generico<TramiteDetalle> implements Serializable {

    private static final long serialVersionUID = -7964113230937667105L;

    public void guardarDetalleTramite(TramiteDetalle tramiteDetalle) throws ServicioExcepcion {
        guardarSalida(tramiteDetalle);
    }

    public Integer maximoTdtOrden(Long numeroTramite) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("SELECT MAX(TdtOrden) FROM TramiteDetalle WHERE TraNumero = ?");
            q.setParameter("1", numeroTramite);
            BigDecimal resultado = (BigDecimal) q.getSingleResult();
            Integer resultadoInt = resultado.intValue();
            return resultadoInt;
        } catch (Exception e) {
            return 0;
        }
    }

    public void actualizarTdtOrden(Long numeroTramite, BigDecimal actualizarTdtOrden, BigDecimal AntTdtOrden) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("UPDATE TramiteDetalle SET TdtOrden =? WHERE TraNumero = ? AND TdtOrden = ?");
            q.setParameter("1", actualizarTdtOrden);
            q.setParameter("2", numeroTramite);
            q.setParameter("3", AntTdtOrden);
            q.executeUpdate();

        } catch (Exception e) {

        }
    }

    public void actualizarOrdenTramite(Long numeroTramite, BigDecimal actualizarTdtOrden, Long TdtTtrId) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("UPDATE TramiteDetalle SET TdtOrden =? WHERE TraNumero =? AND TdtTtrId =?");
            q.setParameter("1", actualizarTdtOrden);
            q.setParameter("2", numeroTramite);
            q.setParameter("3", TdtTtrId);
            q.executeUpdate();

        } catch (Exception e) {

        }
    }

    public Integer buscarOrden(Long numeroTramite, Long TdtTtrId) {
        Query q;
        try {

            q = getEntityManager().createNativeQuery("SELECT TOP 1 TdtOrden FROM TramiteDetalle WHERE TraNumero = ? AND TdtTtrId = ?");
            q.setParameter("1", numeroTramite);
            q.setParameter("2", TdtTtrId);

            BigDecimal resultado = (BigDecimal) q.getSingleResult();
            Integer resultadoInt = resultado.intValue();
            return resultadoInt;

        } catch (Exception e) {

            try {
                q = getEntityManager().createNativeQuery("SELECT MAX(TdtOrden) FROM TramiteDetalle WHERE TraNumero = ?");
                q.setParameter("1", numeroTramite);
                BigDecimal resultado = (BigDecimal) q.getSingleResult();
                Integer resultadoInt = resultado.intValue();
                return resultadoInt + 1;

            } catch (Exception i) {
                return 1;
            }

        }

    }

    public boolean contarDetalle(Long numeroTramite) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("SELECT Count(TramiteDetalle.TdtId) FROM TramiteDetalle WHERE TramiteDetalle.TraNumero = ?");
            q.setParameter("1", numeroTramite);
            int existe = (int) q.getSingleResult();
            System.out.println("cantidad" + existe);
            if (existe <= 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    public List<TramiteDetalle> buscarPorNumeroDeTramite(Long tramite) throws ServicioExcepcion {

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("traNumero", tramite);
            return listarPorConsultaJpaNombrada(TramiteDetalle.BUSCAR_POR_NUMERO_TRAMITE_ORDER_BY_NUM_REPERTORIO, parametros);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<TramiteDetalle> buscarPorNumeroDeTramiteYestado(Long tramite) throws ServicioExcepcion {

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("traNumero", tramite);
            return listarPorConsultaJpaNombrada(TramiteDetalle.BUSCAR_POR_NUMERO_TRAMITE_Y_ESTADO_ORDER_BY_NUM_REPERTORIO, parametros);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Long> buscarIdTipoTramitePorNumeroDeTramiteDistinctTtrID(Long tramite) throws ServicioExcepcion {
        Query q = getEntityManager().createQuery("SELECT DISTINCT t.tdtTtrId FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero ORDER BY t.tdtOrden");
        q.setParameter("traNumero", tramite);
        return q.getResultList();
    }

    public List<TramiteDetalle> buscarPorNumTramiteYporIdTipoTramite(Long traNumero, short repTtrId) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        parametros.put("repTtrId", repTtrId);

        return listarPorConsultaJpaNombrada(TramiteDetalle.BUSCAR_POR_NUM_TRAMITE_Y_TIPO_TRAMITE, parametros);

    }

    public List<TramiteDetalle> listarPorNumTramiteYporIdTipoTramite(Long traNumero, short repTtrId) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        parametros.put("tipoTr", repTtrId);

        return listarPorConsultaJpaNombrada(TramiteDetalle.LISTAR_POR_NUM_TRAMITE_Y_TIPO_TRAMITE, parametros);

    }

    public List<TramiteDetalle> listarPor_NumTramite_TipoTramite_Persona(Long traNumero, short repTtrId, BigInteger perId) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero AND t.tdtTtrId = :tipoTr AND t.perId.perId = :perId");
        q.setParameter("traNumero", traNumero);
        q.setParameter("tipoTr", repTtrId);
        q.setParameter("perId", perId);

        return q.getResultList();

    }

    public List<TramiteDetalle> listarPorNumTramiteYporIdTipoTramitePorEstado(Long traNumero, short repTtrId) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        parametros.put("tipoTr", repTtrId);

        return listarPorConsultaJpaNombrada(TramiteDetalle.LISTAR_POR_NUM_TRAMITE_Y_TIPO_TRAMITE_POR_ESTADO, parametros);

    }

    public List<TramiteDetalle> listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(Long traNumero, short repTtrId, Long numRepertorio) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        parametros.put("tipoTr", repTtrId);
        parametros.put("numRepertorio", numRepertorio);

        return listarPorConsultaJpaNombrada(TramiteDetalle.LISTAR_POR_NUM_TRAMITE_TIPO_TRAMITE_ESTADO_REPERTORIO, parametros);

    }

    public Long asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.tdtId) from TramiteDetalle a");
            return ((Long) q.getSingleResult()) + 1;
        } catch (Exception e) {
            return new Long(1);
        }
    }

    public List<TramiteUsuario> buscarPorTramiteUsuario(String usuario) throws ServicioExcepcion {
        Query q = getEntityManager().createNativeQuery(" SELECT DISTINCT  tusu.*  from TramiteDetalle tde "
                + " INNER JOIN Tramite tra on tra.traNumero=tde.traNumero"
                + " INNER JOIN TramiteUsuario tusu on tusu.TraNumero=tra.TraNumero "
                + " where tra.TraUser ='" + usuario + "'");

        return q.getResultList();
        // listarPorConsultaNativa(TramiteDetalle.BUSCAR_POR_TRAMITE, parametros);
    }

    public List<TramiteDetalle> buscarPorTramite(Long tramite) throws ServicioExcepcion {
        Query q = getEntityManager().createNativeQuery(" SELECT DISTINCT  tde.*  from TramiteDetalle tde "
                + " INNER JOIN Tramite tra on tra.traNumero=tde.traNumero where tra.TraNumero =" + tramite + "");

        return q.getResultList();
        // listarPorConsultaNativa(TramiteDetalle.BUSCAR_POR_TRAMITE, parametros);
    }

    public List<TramiteDetalle> buscarPorTramiteUsuarioModelo(String TraTipo) throws ServicioExcepcion {
        System.out.println("TraTipo" + TraTipo);
        Query q = getEntityManager().createNativeQuery(" SELECT DISTINCT  tde.*  from TramiteDetalle tde "
                + " INNER JOIN Tramite tra on tra.traNumero=tde.traNumero"
                + " where tra.traModelo='S' AND tra.TraTipo='" + TraTipo + "'");
        System.out.print("qUERY " + q.toString() + "Tamaño de lista" + q.getResultList().size());
        return q.getResultList();
        // listarPorConsultaNativa(TramiteDetalle.BUSCAR_POR_TRAMITE, parametros);
    }

    public TramiteDetalle buscarPorNumRepertorio(String numRepertorio) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery();
        Root td = cq.from(TramiteDetalle.class);
        cq.where(cb.equal(td.get("tdtNumeroRepertorio"), numRepertorio));
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(1);
        return (TramiteDetalle) q.getSingleResult();

    }

    public Boolean existeTramiteDetalle(Tramite traNumero, Persona perId, TipoTramiteCompareciente TtcId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        parametros.put("perId", perId);
        parametros.put("ttcId", TtcId);

        try {
            obtenerPorConsultaJpaNombrada(TramiteDetalle.BUSCAR_POR_NUM_TRAMITE_POR_PERSONA_POR_TIPO_TRAM_COMPARECIENTE_POR_ESTADO, parametros);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /*
    public String ObtenerporNombreYDescripcion( String sql) throws ServicioExcepcion, ParseException {
        try {
            Query query = getEntityManager().createNativeQuery(sql);
            
            return (String)query.getResultList();
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }        
        
    }
     */
    public TramiteDetalle buscarPorNumTramiteYDescripcion(Long traNumero, String descripcion) {
        /*
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        parametros.put("traDescripcion", descripcion);

        return obtenerPorConsultaJpaNombrada(TramiteDetalle.BUSCAR_POR_NUM_TRAMITE_Y_DESCRIPCION, parametros);
         */

        Query q;

        String sql = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero AND t.tdtTtrDescripcion = :traDescripcion ORDER BY t.tdtTtrId";

        q = getEntityManager().createQuery(sql);

        q.setParameter("traNumero", traNumero);
        q.setParameter("traDescripcion", descripcion);

        q.setMaxResults(1);

        return (TramiteDetalle) q.getSingleResult();
    }

    public TramiteDetalle buscarPorNumTramite(Long traNumero) {
        /*
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        parametros.put("traDescripcion", descripcion);

        return obtenerPorConsultaJpaNombrada(TramiteDetalle.BUSCAR_POR_NUM_TRAMITE_Y_DESCRIPCION, parametros);
         */
    try {
        Query q;

        String sql = "SELECT t FROM TramiteDetalle t WHERE t.traNumero.traNumero = :traNumero";

        q = getEntityManager().createQuery(sql);

        q.setParameter("traNumero", traNumero);

        q.setMaxResults(1);
        
    
            return (TramiteDetalle) q.getSingleResult();
            
        } catch (Exception e) {
            System.out.println(e);
            return null;
            
        }

        
    }

    public boolean eliminarTramiteDetalle(Long TraNumero) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("DELETE FROM TramiteDetalle WHERE TraNumero=?");
            q.setParameter("1", TraNumero);
            q.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<TramiteDetalle> listarPorNumRepertorio(int tdtNumeroRepertorio) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tdtNumeroRepertorio", tdtNumeroRepertorio);
        return listarPorConsultaJpaNombrada(TramiteDetalle.LISTAR_POR_NUM_REPERTORIO, parametros);
    }

    public List<TramiteDetalle> listarRepertorio(int repertorio) throws ServicioExcepcion {
        String sql = "SELECT  * FROM TramiteDetalle t where t.tdtNumeroRepertorio= ? ";
        Query q = getEntityManager().createNativeQuery(sql, TramiteDetalle.class);
        q.setParameter("1", repertorio);
        return q.getResultList();
    }

    public TramiteDetalle buscarPorId(Long idTramiteDetalle) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tdtId", idTramiteDetalle);
        return obtenerPorConsultaJpaNombrada(TramiteDetalle.BUSCAR_POR_ID_TRAMITE_DETALLE, parametros);

    }

    //legal
    public List<TramiteDetalle> listar_por_repertorio_tramite_propietarioTipoTramComp(Integer numRepertorio, Long traNumero, String ttcPropietario) {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM TramiteDetalle t where t.tdtNumeroRepertorio= :numRepertorio AND t.traNumero.traNumero = :traNumero AND t.ttcId.ttcPropietario = :ttcPropietario AND t.tdtEstado='A'");
        q.setParameter("numRepertorio", numRepertorio);
        q.setParameter("traNumero", traNumero);
        q.setParameter("ttcPropietario", ttcPropietario);
        return q.getResultList();
    }

    public List<TramiteDetalle> listar_por_repertorio_tramite(Integer numRepertorio, Long traNumero) {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM TramiteDetalle t where t.tdtNumeroRepertorio= :numRepertorio AND t.traNumero.traNumero = :traNumero");
        q.setParameter("numRepertorio", numRepertorio);
        q.setParameter("traNumero", traNumero);

        return q.getResultList();
    }

    public List<TramiteDetalle> obtenerTramitePorIdPersona(Persona idPersona) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("perId", idPersona);
            return listarPorConsultaJpaNombrada("TramiteDetalle.obtenerPorPerId", parametros);
        } catch (ServicioExcepcion e) {
            return null;
        }

    }

    public List<TramiteDetalle> listarTramite() {
        String sql = "SELECT * FROM Persona,TipoTramite\n"
                + "INNER JOIN TramiteDetalle ON TramiteDetalle.TdtTtrId = TipoTramite.TtrId \n"
                + "INNER JOIN TipoCompareciente on TramiteDetalle.TdtTpcId = TipoCompareciente.TpcId\n"
                + "WHERE Persona.PerId = TramiteDetalle.PerId \n"
                + "AND TipoTramite.TrCodigoAgrupacion1='UAFE' \n"
                + "AND TramiteDetalle.TdtNumeroRepertorio<>0\n"
                + "ORDER BY TramiteDetalle.TdtNumeroRepertorio ";
        Query q = getEntityManager().createNativeQuery(sql, TramiteDetalle.class);
//        q.setParameter("1", fechaIni);
//        q.setParameter("2", fechaFin);
        return q.getResultList();
    }

    public List<TramiteDetalle> listarTramiteEstado(Long perId) {
        String sql = "SELECT * FROM Tramite INNER JOIN TramiteDetalle ON TramiteDetalle.TraNumero = Tramite.TraNumero\n"
                + "WHERE TramiteDetalle.PerId = ?";
        Query q = getEntityManager().createNativeQuery(sql, TramiteDetalle.class);
        q.setParameter("1", perId);
        return q.getResultList();
    }

    public List<TramiteDetalle> listarTraNumero(Long traNum) {
        try {
            String sql = "SELECT  * FROM Tramite INNER JOIN TramiteDetalle ON TramiteDetalle.TraNumero = Tramite.TraNumero\n"
                    + "WHERE TramiteDetalle.TraNumero =  ?  ORDER BY TramiteDetalle.TraNumero ";
            Query q = getEntityManager().createNativeQuery(sql, TramiteDetalle.class);
            q.setParameter("1", traNum);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    public List<TramiteDetalle> listarPorFecha(Date fechaIni, Date fechaFin) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        List<TramiteDetalle> lista = listarPorConsultaNativa("SELECT * \n"
                + "FROM Repertorio r\n"
                + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero\n"
                + "INNER JOIN TramiteDetalle td ON td.TraNumero = t.TraNumero\n"
                + "INNER JOIN TipoLibro tl ON tl.TplId = td.TdtTplId\n"
                + "INNER JOIN Notaria n ON n.NotId = t.TraNotaria\n"
                + "WHERE \n"
                + "CONVERT(DATE,r.RepFHR) >= ? AND CONVERT(DATE,r.RepFHR) <= ? AND td.TdtTtrId = r.RepTtrId ORDER BY r.RepNumero ASC", parametros, TramiteDetalle.class);
        return lista;
    }
    
}
