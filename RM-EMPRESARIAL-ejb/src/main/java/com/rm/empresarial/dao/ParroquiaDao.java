/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Parroquia;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Wilson
 */
@Stateless
@LocalBean
public class ParroquiaDao extends Generico<Parroquia> implements Serializable {

    private static final long serialVersionUID = 2371394902671607496L;

    public List<Parroquia> listarTodo() {
        try {
            return listarPorConsultaJpaNombrada(Parroquia.LISTAR_TODO, null);
        } catch (Exception e) {
            return null;
        }

    }

    public boolean validarNombreCrear(String parNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("parNombre", parNombre);
        return listarPorConsultaJpaNombrada(Parroquia.BUSCAR_POR_NOM, parametros).isEmpty();
    }

    public boolean validarNombreEditar(Long parId, String parNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("parId", parId);
        parametros.put("parNombre", parNombre);
        return listarPorConsultaJpaNombrada(Parroquia.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }

    public Parroquia buscarPorId(Long parId) {

        String sql = "SELECT *  from Parroquia  WHERE ParId= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Parroquia.class);
        q.setParameter("1", parId);

        return (Parroquia) q.getSingleResult();
    }
    
}
