/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import java.io.Serializable;
import java.util.Date;
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
public class RepertorioUsuarioDao extends Generico<RepertorioUsuario> implements Serializable {

    public List<RepertorioUsuario> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(RepertorioUsuario.LISTAR_TODO, null);
    }

    public List<RepertorioUsuario> listarPorTipoPorEstado(String estado, String rpuEstado) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rpuTipo", estado);
        parametros.put("rpuEstado", rpuEstado);
        return listarPorConsultaJpaNombrada("RepertorioUsuario.listarPorTipoPorEstado", parametros);
    }

    public List<RepertorioUsuario> listarPorUsuarioPorTipoPorEstado(Long usuarioLogeado, String rpuTipo) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", usuarioLogeado);
        parametros.put("rpuTipo", rpuTipo);
        return listarPorConsultaJpa("SELECT r FROM RepertorioUsuario r "
                + "WHERE r.usuId.usuId = :usuId AND r.rpuTipo = :rpuTipo "
                + "AND r.rpuEstado LIKE 'A'", parametros);
    }
    
     public List<RepertorioUsuario> listarPorNumRepertorio(Long repNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("repNumero", repNumero);        
        return listarPorConsultaJpa("SELECT r FROM RepertorioUsuario r "
                + "WHERE r.repNumero.repNumero = :repNumero", parametros);
    }

    public List<RepertorioUsuario> listarPorTipoPorRepertorioPorEstadoPorUsrLog(String rpuTipo, Long idRepNumero, String estado, Long idUsrLog) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", idUsrLog);
        parametros.put("rpuTipo", rpuTipo);
        parametros.put("rpuEstado", estado);
        parametros.put("repNumero", idRepNumero);
        return listarPorConsultaJpa("SELECT ru FROM RepertorioUsuario ru "
                + "WHERE ru.usuId.usuId = :usuId "
                + "AND ru.rpuTipo = :rpuTipo "
                + "AND ru.rpuEstado LIKE :rpuEstado "
                + "AND ru.repNumero.repNumero = :repNumero", parametros);
    }

    public List<RepertorioUsuario> listarPor_Tramite_Tipo_Estado_Usr(String rpuTipo, Long idTranumero, String rpuEstado, Long usuId) throws ServicioExcepcion {
        Query q;

        q = getEntityManager().createQuery("SELECT ru FROM RepertorioUsuario ru WHERE ru.usuId.usuId = :usuId AND ru.rpuTipo = :rpuTipo AND ru.rpuEstado LIKE :rpuEstado AND ru.repNumero.traNumero.traNumero = :idTranumero");
        q.setParameter("usuId", usuId);
        q.setParameter("rpuTipo", rpuTipo);
        q.setParameter("rpuEstado", rpuEstado);
        q.setParameter("idTranumero", idTranumero);
        return q.getResultList();
    }

    public Boolean existeRepertorioUsuario(Long repNumero, Long usuId, String rpuTipo) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT COUNT(ru.rpuId) FROM RepertorioUsuario ru WHERE ru.repNumero.repNumero LIKE :repNumero AND ru.usuId.usuId LIKE :usuId AND ru.rpuTipo LIKE :rpuTipo AND ru.rpuEstado LIKE 'A'");
        q.setParameter("repNumero", repNumero);
        q.setParameter("usuId", usuId);
        q.setParameter("rpuTipo", rpuTipo);

        Long resultado;
        resultado = (Long) q.getSingleResult();
//        System.out.println("resultado: "+resultado);
        return resultado > 0;

    }

//    public List<RepertorioUsuario> listarPorNumeroPorUsuarioPorTipoPorEstado(Long numRepertorio, Long usuarioLogeado, String rpuTipo ) throws ServicioExcepcion
//    {
//        Map<String,Object>parametros =new HashMap<>();
//        parametros.put("repNumero", numRepertorio);
//        parametros.put("usuId", usuarioLogeado);
//        parametros.put("rpuTipo", rpuTipo);
//        return listarPorConsultaJpa("SELECT r FROM RepertorioUsuario r "
//                + "WHERE r.repNumero.repNumero = :repNumero "
//                + "AND r.usuId.usuId = :usuId AND r.rpuTipo = :rpuTipo "
//                + "AND r.rpuEstado LIKE 'A'", parametros);
//    }
//    
    public int actualizarEstadoRepUsuPorRepPorUsrPorTipo(Long numRepertorio, Long usuarioLogeado, String rpuTipo) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("UPDATE RepertorioUsuario r SET r.rpuEstado='I' WHERE r.repNumero.repNumero=:numRepertorio AND r.usuId.usuId = :usuId AND r.rpuTipo = :rpuTipo AND r.rpuEstado LIKE 'A'");
        q.setParameter("usuId", usuarioLogeado);
        q.setParameter("rpuTipo", rpuTipo);
        q.setParameter("numRepertorio", numRepertorio);
        return q.executeUpdate();
    }

    public List<RepertorioUsuario> listarPorRepNumPorEstadoPorUsrLog(Long repNumero, Long idUsrLog) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", idUsrLog);
        parametros.put("repNumero", repNumero);

        return listarPorConsultaJpa("SELECT ru FROM RepertorioUsuario ru "
                + "WHERE ru.usuId.usuId = :usuId "
                + "AND ru.rpuTipo = 'IMP' "
                + "AND ru.rpuEstado = 'A' "
                + "AND ru.repNumero.repNumero = :repNumero", parametros);
    }

    //*METODO REUTILIZABLE*
    public int actualizarEstadoRepUsuPor_Tram_Usr_Tipo(Long numTramite, Long usuarioLogeado, String rpuTipo) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("UPDATE RepertorioUsuario r SET r.rpuEstado='I' WHERE r.repNumero.traNumero.traNumero=:numTramite AND r.usuId.usuId = :usuId AND r.rpuTipo = :rpuTipo AND r.rpuEstado LIKE 'A'");
        q.setParameter("usuId", usuarioLogeado);
        q.setParameter("rpuTipo", rpuTipo);
        q.setParameter("numTramite", numTramite);
        return q.executeUpdate();
    }

    public RepertorioUsuario obtenerRepUsu_Por_Rep_Usr_Tipo(Long numRepertorio, Long usuarioLogeado, String rpuTipo) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numRepertorio);
        parametros.put("2", usuarioLogeado);
        parametros.put("3", rpuTipo);
        try {
            return (RepertorioUsuario) obtenerPorConsultaNativa("SELECT TOP 1 RepertorioUsuario.* FROM RepertorioUsuario WHERE "
                    + "RepertorioUsuario.repNumero = ? "
                    + "AND RepertorioUsuario.usuId = ? "
                    + "AND RepertorioUsuario.rpuTipo = ? "
                    + "AND RepertorioUsuario.rpuEstado LIKE 'A'", parametros, RepertorioUsuario.class);
        } catch (ServicioExcepcion e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
    
    public List<RepertorioUsuario> listarRepUsu_Por_Rep_Usr_Tipo(Long numRepertorio, Long usuarioLogeado, String rpuTipo) {
        Query q;
        q = getEntityManager().createQuery("SELECT r FROM RepertorioUsuario r  WHERE r.repNumero.repNumero=:numRepertorio AND r.usuId.usuId = :usuId AND r.rpuTipo = :rpuTipo AND r.rpuEstado LIKE 'A'",RepertorioUsuario.class);
        q.setParameter("usuId", usuarioLogeado);
        q.setParameter("rpuTipo", rpuTipo);
        q.setParameter("numRepertorio", numRepertorio);
        try {
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }
    
    public List<RepertorioUsuario> listarPorFechaRepertorio_IdTipoTramite_Tipo(Date fechaRepertorio, Long idTipoTramite) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaRepertorio);
        parametros.put("2", idTipoTramite);
        List<RepertorioUsuario> lista = listarPorConsultaNativa("SELECT ru.* FROM RepertorioUsuario ru "
                + " INNER JOIN Repertorio r ON r.RepNumero = ru.RepNumero"
                + " WHERE CONVERT(DATE, r.RepFHR) = ? AND r.RepTtrId = ?"
                + " AND (ru.RpuTipo = 'INS' OR ru.RpuTipo = 'IND' OR ru.RpuTipo = 'INC'"
                + " OR ru.RpuTipo = 'INA' OR ru.RpuTipo = 'INP') ", parametros, RepertorioUsuario.class);
        return lista;
    }
    
    public int actualizar(Long repNumero) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("UPDATE RepertorioUsuario r SET r.rpuEstado = 'I' WHERE r.rpuId = :repNumero ");
        q.setParameter("repNumero", repNumero);
        return q.executeUpdate();
    }

    public List<RepertorioUsuario> listarRepertorioDIG(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<RepertorioUsuario> lista = listarPorConsultaNativa("SELECT RepertorioUsuario.* FROM RepertorioUsuario WHERE RpuTipo='DIG' AND RpuEstado='A' AND UsuId = ? ",
                 parametros, RepertorioUsuario.class);
        return lista;
    }

    public List<RepertorioUsuario> listarRepertorioIMP(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<RepertorioUsuario> lista = listarPorConsultaNativa("SELECT RepertorioUsuario.* FROM RepertorioUsuario WHERE RpuTipo='IMP' AND RpuEstado='A' AND UsuId = ? ",
                 parametros, RepertorioUsuario.class);
        return lista;
    }

    public List<RepertorioUsuario> listarRepertorioMRG(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<RepertorioUsuario> lista = listarPorConsultaNativa("SELECT RepertorioUsuario.* FROM RepertorioUsuario WHERE RpuTipo='MRG' AND RpuEstado='A' AND UsuId = ? ",
                 parametros, RepertorioUsuario.class);
        return lista;
    }

    public List<RepertorioUsuario> listarRepertorioMAT(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<RepertorioUsuario> lista = listarPorConsultaNativa("SELECT RepertorioUsuario.* FROM RepertorioUsuario WHERE RpuTipo='MAT' AND RpuEstado='A' AND UsuId = ? ",
                 parametros, RepertorioUsuario.class);
        return lista;
    }

    public List<RepertorioUsuario> listarRepertorioINS(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<RepertorioUsuario> lista = listarPorConsultaNativa("SELECT RepertorioUsuario.* FROM RepertorioUsuario WHERE RpuTipo='INS' AND RpuEstado='A' AND UsuId = ? ",
                 parametros, RepertorioUsuario.class);
        return lista;
    }

    public List<RepertorioUsuario> listarRepertorioRAZ(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<RepertorioUsuario> lista = listarPorConsultaNativa("SELECT RepertorioUsuario.* FROM RepertorioUsuario WHERE RpuTipo='RAZ' AND RpuEstado='A' AND UsuId = ? ",
                 parametros, RepertorioUsuario.class);
        return lista;
    }

    public List<RepertorioUsuario> listarRepertorioCER(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<RepertorioUsuario> lista = listarPorConsultaNativa("SELECT RepertorioUsuario.* FROM RepertorioUsuario WHERE RpuTipo='CER' AND RpuEstado='A' AND UsuId = ? ",
                 parametros, RepertorioUsuario.class);
        return lista;
    }

    public int actualizarEstadoProcesoPor_NumRepertorio_Tipo(Long numRepertorio, String rpuTipo) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("UPDATE RepertorioUsuario r SET r.rpuEstadoProceso='TERMINADO'"
                + " WHERE r.repNumero.repNumero = :numRepertorio AND r.rpuTipo = :rpuTipo AND r.rpuEstado LIKE 'A'");
        q.setParameter("rpuTipo", rpuTipo);
        q.setParameter("numRepertorio", numRepertorio);
        return q.executeUpdate();
    }
    
    public List<RepertorioUsuario> listarPorUsuId(Long usuId) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        return listarPorConsultaNativa("SELECT RepertorioUsuario.* FROM RepertorioUsuario "
                + "WHERE RepertorioUsuario.usuId = ?", parametros, RepertorioUsuario.class);
    }

//       public List<RepertorioUsuario> listarPorUsuarioPorTipoPorEstado(Long usuarioLogeado, String rpuTipo) throws ServicioExcepcion {
//        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("usuId", usuarioLogeado);
//        parametros.put("rpuTipo", rpuTipo);
//        return listarPorConsultaJpa("SELECT r FROM RepertorioUsuario r "
//                + "WHERE r.usuId.usuId = :usuId AND r.rpuTipo = :rpuTipo "
//                + "AND r.rpuEstado LIKE 'A'", parametros);
//    }
}
