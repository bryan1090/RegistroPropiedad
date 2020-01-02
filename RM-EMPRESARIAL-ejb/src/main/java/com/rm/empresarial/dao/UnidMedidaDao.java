/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.UnidMedida;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class UnidMedidaDao extends Generico<UnidMedida> implements Serializable {

    public List<UnidMedida> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(UnidMedida.LISTAR_TODO, null);
    }

    public boolean validarNombreCrear(String umdNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("umdNombre", umdNombre);
        return listarPorConsultaJpaNombrada(UnidMedida.BUSCAR_POR_NOM, parametros).isEmpty();
    }

    public boolean validarNombreEditar(Long umdId, String umdNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("umdId", umdId);
        parametros.put("umdNombre", umdNombre);
        return listarPorConsultaJpaNombrada(UnidMedida.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }

    public boolean validarAbreviaturaCrear(String umdAbreviatura) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("umdAbreviatura", umdAbreviatura);
        return listarPorConsultaJpaNombrada(UnidMedida.BUSCAR_POR_ABREVI, parametros).isEmpty();
    }

    public boolean validarAbreviaturaEditar(Long umdId, String umdAbreviatura) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("umdId", umdId);
        parametros.put("umdAbreviatura", umdAbreviatura);
        return listarPorConsultaJpaNombrada(UnidMedida.BUSCAR_POR_ABREVI_EDITAR, parametros).isEmpty();
    }

    public UnidMedida buscarUnidadMedidadPorAbreviatura(String umdAbreviatura) {
        Query q;
        q = getEntityManager().createQuery("SELECT u FROM UnidMedida u WHERE u.umdAbreviatura=:umdAbreviatura");
        q.setParameter("umdAbreviatura", umdAbreviatura);
        q.setMaxResults(1);
        return (UnidMedida) q.getSingleResult();
    }

    public UnidMedida buscarUnidadMedidadPorCodigo(String umdCodigo) {
        try {
            Query q;
            q = getEntityManager().createNamedQuery("UnidMedida.findByUmdCodigo");
            q.setParameter("umdCodigo", umdCodigo);
            q.setMaxResults(1);
            return (UnidMedida) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
