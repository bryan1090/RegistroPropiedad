/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.Usuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class TramiteUsuarioDao extends Generico<TramiteUsuario> implements Serializable {

    private static final long serialVersionUID = 8072505130306964546L;

    public void guardarTramiteUsuario(TramiteUsuario tramiteUsuario) {
        try {
            guardar(tramiteUsuario);
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(TramiteUsuarioDao.class.getName()).log(Level.SEVERE, "Error al guardar el registro", ex);
        }

    }

    public List<TramiteUsuario> buscarTramitePorUsuario(Usuario usuario) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", usuario);
        return listarPorConsultaJpaNombrada(TramiteUsuario.BUSCAR_POR_USUARIO, parametros);
    }

    public TramiteUsuario buscarTramitePorUsuarioYNumTramiteActivos(Tramite traNumero, Usuario usuario) throws ServicioExcepcion {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("traNumero", traNumero);
            parametros.put("usuId", usuario);
            return obtenerPorConsultaJpaNombrada(TramiteUsuario.BUSCAR_POR_TRAMITE_USUARIO_ACTIVOS, parametros);
        } catch (Exception e) {
            return null;
        }
    }

    public List<TramiteUsuario> listarPorUsuId(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        return listarPorConsultaNativa("SELECT TramiteUsuario.* FROM TramiteUsuario "
                + "WHERE TramiteUsuario.UsuId = ?", parametros, TramiteUsuario.class);
    }

    public List<TramiteUsuario> buscarTramitePorUsuarioModelo(Usuario usuario) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", usuario);
        return listarPorConsultaJpaNombrada(TramiteUsuario.BUSCAR_POR_USUARIO_MODELO, parametros);
    }

    public List<TramiteUsuario> buscarTodos() throws ServicioExcepcion {

        return listarPorConsultaJpaNombrada(TramiteUsuario.BUSCAR_TODOS_MODELOS, null);
    }

    public List<TramiteUsuario> buscarTramitePorModeloTodos() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TramiteUsuario.BUSCAR_POR_MODELO_TODOS, null);
    }

//    public TramiteUsuario buscarPorTramite(Tramite tramite) throws ServicioExcepcion {
//        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("traNumero", tramite);
//        return obtenerPorConsultaJpaNombrada(TramiteUsuario.BUSCAR_POR_TRAMITE, parametros);
//    }
    
    public TramiteUsuario buscarPorTramite(Tramite tramite) throws ServicioExcepcion {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT t FROM TramiteUsuario t WHERE t.traNumero=:traNumero AND t.tusEstado ='A'");
            q.setParameter("traNumero", tramite);
            q.setMaxResults(1);

            return (TramiteUsuario) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }
      
    
    

    public List<TramiteUsuario> listarTramiteUsuario() {

        String sql = "select * from TramiteUsuario ";

        Query q = getEntityManager().createNativeQuery(sql, TramiteUsuario.class);
        return q.getResultList();
    }

    public List<TramiteUsuario> listarTramite(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<TramiteUsuario> lista = listarPorConsultaNativa("SELECT TramiteUsuario.* FROM TramiteUsuario where  TusEstado='A' AND UsuId= ? ",
                parametros, TramiteUsuario.class);
        return lista;
    }

    public List<TramiteUsuario> persona(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<TramiteUsuario> lista = listarPorConsultaNativa("SELECT top 1 TramiteUsuario.* FROM TramiteUsuario where  TusEstado='A' AND UsuId= ? ",
                parametros, TramiteUsuario.class);
        return lista;
    }

    public int actualizar(Long tusId) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("UPDATE TramiteUsuario t SET t.tusEstado = 'I' WHERE t.tusId = :tusId ");
        q.setParameter("tusId", tusId);

        return q.executeUpdate();
    }

    public void TramiteUsuarioEstado(Long tusId) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tusId);
        String sql = "UPDATE TramiteUsuario "
                + " SET TusEstado = 'I' "
                + " WHERE TusId = ? ";
        Query q = getEntityManager().createQuery(sql, TramiteUsuario.class);
        q.executeUpdate();
    }

//    public List<TramiteUsuario> listarRVT(Long usuId) throws ServicioExcepcion {
//        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("1", usuId);
//        return listarPorConsultaNativa("SELECT * "
//                + "FROM "
//                + "TramiteUsuario "
//                + " WHERE "
//                + " TramiteUsuario.TusEstado = 'A' AND "
//                + " TramiteUsuario.UsuId = ? ", parametros);
//    }
//          public List<TramiteUsuario> listarRVT(Long usuId) throws ServicioExcepcion {
//        
//        Query q ;
//        
//        q = getEntityManager().createQuery("SELECT t FROM TramiteUsuario t WHERE t.tusEstado='A' AND t.usuId= :usuId ");
//        
//        q.setParameter("usuId", usuId);        
//
//        return q.getResultList();
//
//    }
    public List<TramiteUsuario> usuarios(Date fecha) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fecha);
        List<TramiteUsuario> lista = listarPorConsultaNativa("SELECT *\n"
                + "FROM Tramite\n"
                + "INNER JOIN Notaria ON Notaria.NotId=Tramite.TraNotaria\n"
                + "INNER JOIN Ciudad ON Ciudad.CiuId =Notaria.CiuId\n"
                + "INNER JOIN TramiteResponsable ON TramiteResponsable.TraNumero=Tramite.TraNumero\n"
                + "INNER JOIN Persona res ON res.PerId = TramiteResponsable.PerId\n"
                + "INNER JOIN TipoTramite ON TipoTramite.TtrId = TramiteResponsable.TtrId\n"
                + "INNER JOIN TramiteUsuario ON TramiteUsuario.TraNumero=Tramite.TraNumero\n"
                + "WHERE CONVERT(DATE,TramiteUsuario.TusFHR)= ? \n"
                + "ORDER BY TramiteUsuario.UsuId ",
                parametros, TramiteUsuario.class);
        return lista;
    }

    public List<TramiteUsuario> tramiteUsuarioCarga(Date fechaIni, Date fechaFin) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        List<TramiteUsuario> lista = listarPorConsultaNativa("SELECT * "
                + "FROM "
                + "dbo.TramiteUsuario "
                + "INNER JOIN TramiteResponsable ON TramiteUsuario.TraNumero = TramiteResponsable.TraNumero "
                + "INNER JOIN TipoTramite on TramiteResponsable.TtrId = TipoTramite.TtrId "
                + "WHERE CONVERT(DATE, TramiteUsuario.TusFHR)>=? AND CONVERT(DATE, TramiteUsuario.TusFHR)<=? ",
                parametros, TramiteUsuario.class);
        return lista;
    }

}
