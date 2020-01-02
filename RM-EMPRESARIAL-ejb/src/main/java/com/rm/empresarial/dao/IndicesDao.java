/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Indices;
import com.rm.empresarial.modelo.Rol;
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
public class IndicesDao extends Generico<Indices> implements Serializable {

    public List<Indices> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Indices.LISTAR_TODO, null);
    }

    public List<Indices> listarPorBusqueda(String indCedula, String indApellidoP, String indApellidoM, String indNombres) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", "%" + indCedula + "%");
        parametros.put("2", "%" + indApellidoP + "%");
        parametros.put("3", "%" + indApellidoM + "%");
        parametros.put("4", "%" + indNombres + "%");
        String sql = "SELECT TOP 1000 * FROM Indices WHERE IND_CEDULA LIKE ?1 OR IND_APELLIDOP1 LIKE ?2 OR IND_APELLIDOM1 LIKE ?3 OR IND_NOMBRES1 LIKE ?4";
        return listarPorConsultaNativa(sql, parametros);
    }

    public List<Indices> listarPorBusqueda2(String indCedula, String indApellidoP, String indApellidoM, String indNombres) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("indCedula", "%" + indCedula + "%");
        parametros.put("indApellidop1", "%" + indApellidoP + "%");
        parametros.put("indApellidom1", "%" + indApellidoM + "%");
        parametros.put("indNombres1", "%" + indNombres + "%");
        return listarPorConsultaJpaNombrada(Indices.LISTAR_POR_BUSQUEDA, parametros);
    }

    public List<Indices> listarPorBusqueda3(String indCedula, String indApellidoP, String indApellidoM, String indNombres) throws ServicioExcepcion {
        Query q = getEntityManager().createQuery("SELECT i FROM Indices i WHERE i.indCedula LIKE :indCedula AND i.indApellidop1 LIKE :indApellidop1 AND i.indApellidom1 LIKE :indApellidom1 AND i.indNombres1 LIKE :indNombres1");
        q.setParameter("indCedula", "%" + indCedula + "%");
        q.setParameter("indApellidop1", "%" + indApellidoP + "%");
        q.setParameter("indApellidom1", "%" + indApellidoM + "%");
        q.setParameter("indNombres1", "%" + indNombres + "%");
        q.setMaxResults(1000);

        return q.getResultList();
    }
}
