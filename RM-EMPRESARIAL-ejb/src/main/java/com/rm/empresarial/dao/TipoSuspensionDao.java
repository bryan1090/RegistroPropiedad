/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoSuspension;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.Usuario;
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
public class TipoSuspensionDao extends Generico<TipoSuspension> implements Serializable {

    public List<TipoSuspension> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoSuspension.LISTAR_TODO, null);
    }

    public List<TipoSuspension> listarPorTipoRevision() {
        Query q;
        q = getEntityManager().createQuery("SELECT t from TipoSuspension t WHERE t.tpsRevision = true");
        return q.getResultList();
    }

    public List<TipoSuspension> listarPorTipoInscripcion() {
        Query q;
        q = getEntityManager().createQuery("SELECT t from TipoSuspension t WHERE t.tpsInscripcion = true");
        return q.getResultList();
    }

    public List<TipoSuspension> listarPorEstadoA() throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpsEstado", "A");
        return listarPorConsultaJpaNombrada("TipoSuspension.findByTpsEstado", null);
    }

    public TipoSuspension buscarTipoSuspensionPorId(Long id) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpsId", id);
        try {
             return obtenerPorConsultaJpaNombrada(TipoSuspension.OBTENER_POR_ID, parametros);
        } catch (Exception e) {
            return null;
        }
       
    }

    public Long asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(t.tpsId) from TipoSuspension t");
            return ((Long) q.getSingleResult()) + 1;
        } catch (Exception e) {
            return new Long(1);
        }
    }

    public boolean validarNombreCrear(String tpsNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpsNombre", tpsNombre);
        return listarPorConsultaJpaNombrada(TipoSuspension.BUSCAR_POR_NOM, parametros).isEmpty();
    }

    public boolean validarNombreEditar(Long tpsId, String tpsNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpsId", tpsId);
        parametros.put("tpsNombre", tpsNombre);
        return listarPorConsultaJpaNombrada(TipoSuspension.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }
}
