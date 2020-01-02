/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.VersionPH;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class VersionPHDao extends Generico<VersionPH> implements Serializable{
    
     public VersionPH buscarUltimaVersionPorMatricula(String numMatricula) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numMatricula);
        try {
            return (VersionPH) obtenerPorConsultaNativa("SELECT TOP 1 * FROM VersionPH "
                    + " WHERE VphMatricula = ? ORDER BY VphVersion DESC", parametros, VersionPH.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
     
      public List<Integer> listarSecuencialVersionesPorMatricula(String numMatricula) throws ServicioExcepcion {
        
        
        String sql = "SELECT DISTINCT VphVersion FROM VersionPH WHERE VphMatricula = ?";
        Query q;
        q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", numMatricula);
        
        
        List<Integer> listInt =  new ArrayList<>();
        listInt = q.getResultList();
        
        return  listInt;
        
    } 
      
      public List<VersionPH> listarPorNumMatriculaPorSecuencial(String numMatricula, int secuencial) throws ServicioExcepcion {
        String sql = "SELECT TOP 1 * FROM VersionPH WHERE VphMatricula = ? AND VphSecuencial = ?";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numMatricula);
        parametros.put("2", secuencial);

        return listarPorConsultaNativa(sql, parametros, VersionPH.class);
    } 
      public List<VersionPH> listarPorNumMatriculaPorVersion(String numMatricula, int version) throws ServicioExcepcion {
        String sql = "SELECT * FROM VersionPH WHERE VphMatricula = ? AND VphVersion = ?";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numMatricula);
        parametros.put("2", version);

        return listarPorConsultaNativa(sql, parametros, VersionPH.class);
    } 
     
    
}
