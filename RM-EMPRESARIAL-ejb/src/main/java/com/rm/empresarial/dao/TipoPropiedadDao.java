/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoPropiedad;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class TipoPropiedadDao extends Generico<TipoPropiedad> implements Serializable {

    public List<TipoPropiedad> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoPropiedad.LISTAR_TODOS, null);
    }

    public boolean validarNombreCrear(String tpdNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpdNombre", tpdNombre);
        return listarPorConsultaJpaNombrada(TipoPropiedad.BUSCAR_POR_NOM, parametros).isEmpty();
    }

    public boolean validarNombreEditar(Long tpdId, String tpdNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpdId", tpdId);
        parametros.put("tpdNombre", tpdNombre);
        return listarPorConsultaJpaNombrada(TipoPropiedad.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }

    public TipoPropiedad buscarPorCodigo(String tpdCodigo) {
        try {
            Query q;
            q = getEntityManager().createNamedQuery("TipoPropiedad.findByTpdCodigo");
            q.setParameter("tpdCodigo", tpdCodigo);
            q.setMaxResults(1);
            return (TipoPropiedad) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
}
