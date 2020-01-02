/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TramiteSuspension;
import java.io.Serializable;
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
public class TramiteSuspensionDao extends Generico<TramiteSuspension> implements Serializable {

    public List<TramiteSuspension> buscarTramitesSuspensionPorNumTram(Long traNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);

        return listarPorConsultaJpaNombrada(TramiteSuspension.LISTAR_POR_NUM_TRAMITE, parametros);

    }

    public List<TramiteSuspension> buscarTramitesSuspensionPorNumTramYporEstadoS(Long traNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);

        return listarPorConsultaJpa("SELECT t FROM TramiteSuspension t WHERE "
                + "t.traNumero.traNumero = :traNumero "
                + "AND t.tmsEstado = 'S' AND t.trmTipo='RVT'", parametros);

    }

    public List<TramiteSuspension> buscarTramitesSuspensionPorNumTramYporEstadoS_Inscripcion(Long traNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);

        return listarPorConsultaJpa("SELECT t FROM TramiteSuspension t WHERE "
                + "t.traNumero.traNumero = :traNumero "
                + "AND t.tmsEstado = 'S' AND (t.trmTipo='INS' "
                + "OR t.trmTipo='INP' OR t.trmTipo='IND' OR t.trmTipo='INC' "
                + "OR t.trmTipo='INA')", parametros);

    }
    public List<TramiteSuspension> buscarTramitesSuspensionPorNumTramYporEstadoS_Inscripcion_INP(Long traNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);

        return listarPorConsultaJpa("SELECT t FROM TramiteSuspension t WHERE "
                + "t.traNumero.traNumero = :traNumero "
                + "AND t.tmsEstado = 'S' AND t.trmTipo='INP' ", parametros);

    }

    public List<TramiteSuspension> buscarTramitesSuspensionPorNumTramYporUsuario(Long traNumero, Long usuario) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        parametros.put("usuId", usuario);
        return listarPorConsultaJpaNombrada("TramiteSuspension.listarPorNumeroTramiteYporUsuario", parametros);

    }

    public List<TramiteSuspension> buscarTramitesSuspensionPorNumTramYporUsuario_Inscripcion(Long traNumero, Long usuario) throws ServicioExcepcion {
        Query q;
//        q=getEntityManager().createQuery("SELECT t FROM TramiteSuspension t JOIN TramiteUsuario tu on t.traNumero=tu.traNumero"
//                + " WHERE t.traNumero.traNumero = :traNumero AND tu.usuId.usuId = :usuId AND t.trmTipo = 'INS'");
        q = getEntityManager().createQuery("SELECT t FROM TramiteSuspension t "
                + "WHERE t.traNumero.traNumero = :traNumero "
                + "AND (t.trmTipo='INS' OR t.trmTipo='INP' OR t.trmTipo='IND' "
                + "OR t.trmTipo='INC' OR t.trmTipo='INA')");
        q.setParameter("traNumero", traNumero);

        return q.getResultList();

    }
    
    public List<TramiteSuspension> buscarTramitesSuspensionPorNumTramYporUsuario_Inscripcion_INP(Long traNumero, Long usuario) throws ServicioExcepcion {
        Query q;
//        q=getEntityManager().createQuery("SELECT t FROM TramiteSuspension t JOIN TramiteUsuario tu on t.traNumero=tu.traNumero"
//                + " WHERE t.traNumero.traNumero = :traNumero AND tu.usuId.usuId = :usuId AND t.trmTipo = 'INS'");
        q = getEntityManager().createQuery("SELECT t FROM TramiteSuspension t WHERE t.traNumero.traNumero = :traNumero AND t.trmTipo = 'INP'");
        q.setParameter("traNumero", traNumero);

        return q.getResultList();

    }
    public TramiteSuspension buscarPorNumTramite_PorIdTipoSuspension(Long numTramite, Long idTipoSuspension) throws ServicioExcepcion {
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("1", numTramite);
      parametros.put("2", idTipoSuspension);      
      try {
          return (TramiteSuspension) obtenerPorConsultaNativa("SELECT TOP 1 * FROM TramiteSuspension "
                  + " WHERE TraNumero = ? AND TmsTpsId = ?", parametros, TramiteSuspension.class);
      } catch (ServicioExcepcion ex) {
          return null;
      }
    }
       
   

}
