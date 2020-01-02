/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Alicuota;
import com.rm.empresarial.modelo.Marginacion;
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
public class AlicuotaDao extends Generico<Alicuota> implements Serializable {
    
     public Alicuota encontrarPorId(Long idAlicuota) throws ServicioExcepcion {      
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("altId", idAlicuota);
        return obtenerPorConsultaJpaNombrada(Alicuota.BUSCAR_POR_ID, parametros);
    }  
     
    public Alicuota buscarPorId(Long idAlicuota) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idAlicuota);
        try {
            return (Alicuota) obtenerPorConsultaNativa("SELECT * FROM Alicuota "
                    + " WHERE AltId = ?", parametros, Alicuota.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public List<Alicuota> listarPorMatricula(String numMatricula) throws ServicioExcepcion {
        String sql = "SELECT * FROM Alicuota WHERE PrdMatricula = ? AND AltEstado = 'A'";
        Query q = getEntityManager().createNativeQuery(sql, Alicuota.class);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }
    public List<Alicuota> listarAlicuotaPorMatricula(String numMatricula) throws ServicioExcepcion {
        String sql = "SELECT * FROM Alicuota WHERE PrdMatricula = ?";
        Query q = getEntityManager().createNativeQuery(sql, Alicuota.class);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }
    
    public Alicuota obtenerUltima(String user) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", user);
        try {
            return (Alicuota) obtenerPorConsultaNativa("SELECT TOP 1 * FROM Alicuota "
                    + " WHERE AltUser = ?"
                    + " ORDER BY AltId DESC", parametros, Alicuota.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
    public Alicuota buscarPorNumMatricula(String numMatricula) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numMatricula);
        try {
            return (Alicuota) obtenerPorConsultaNativa("SELECT TOP 1 * FROM Alicuota "
                    + " WHERE PrdMatricula = ? AND AltEstado = 'A'"
                    + " ORDER BY PrdMatricula DESC", parametros, Alicuota.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
}
