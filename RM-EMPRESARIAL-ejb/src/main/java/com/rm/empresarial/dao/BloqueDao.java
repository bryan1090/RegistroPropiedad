/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Alicuota;
import com.rm.empresarial.modelo.Bloque;
import com.rm.empresarial.modelo.Propiedad;
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
public class BloqueDao extends Generico<Bloque> implements Serializable {

    public List<Bloque> listarPorMatricula(String numMatricula) throws ServicioExcepcion {
        String sql = "SELECT * FROM Bloque WHERE PrdMatricula = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Bloque.class);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }
    
    public List<String> listarTodoPorNombreUnico() throws ServicioExcepcion {
        String sql = "SELECT DISTINCT bloNombre FROM BLOQUE";
        Query q = getEntityManager().createNativeQuery(sql);        
        return q.getResultList();
    }

    public Bloque encontrarBloquePorId(Long idBloque) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("bloId", idBloque);
        try {
            return obtenerPorConsultaJpaNombrada(Bloque.ENCONTRAR_POR_ID, parametros);
        } catch (Exception e) {
            return null;
        }
        
    }

    public Bloque encontrarBloquePorNumMatricula(String numMatricula) throws ServicioExcepcion {
        String sql = "SELECT TOP 1 * FROM Bloque WHERE PrdMatricula = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Bloque.class);
        q.setParameter("1", numMatricula);
        try {
            return (Bloque) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public Bloque encontrarBloquePorNumMatriculaPorNombre(String numMatricula, String nombreBloque) throws ServicioExcepcion {
        String sql = "SELECT TOP 1 * FROM Bloque WHERE PrdMatricula = ? AND BloNombre = ?";
        Query q = getEntityManager().createNativeQuery(sql, Bloque.class);
        q.setParameter("1", numMatricula);
        q.setParameter("2", nombreBloque);
        try {
            return (Bloque) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
    public Bloque encontrarBloquePorNumMatriculaPorCodigoPorNombre(String numMatricula, String codigo, String nombreBloque) throws ServicioExcepcion {
        String sql = "SELECT TOP 1 * FROM Bloque WHERE PrdMatricula = ? AND BloCodigo = ? AND BloNombre = ?";
        Query q = getEntityManager().createNativeQuery(sql, Bloque.class);
        q.setParameter("1", numMatricula);
        q.setParameter("2", codigo);
        q.setParameter("3", nombreBloque);
        try {
            return (Bloque) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }
     public List<Bloque> listarPorNombreBloque(String nombreBloque) throws ServicioExcepcion {
        String sql = "SELECT * FROM Bloque WHERE BloNombre = ? ";               
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", nombreBloque);
        return listarPorConsultaNativa(sql, parametros, Bloque.class);
    }

}
