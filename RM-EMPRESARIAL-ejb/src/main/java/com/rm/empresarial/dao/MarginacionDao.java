/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.Repertorio;
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
public class MarginacionDao extends Generico<Marginacion> implements Serializable {

    public List<Marginacion> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada("Marginacion.findAll", null);
    }

    public boolean estanTodosRepertoriosMarginados(Long traNumero) {
        Query q;
        q = getEntityManager().createQuery("SELECT COUNT(r.repNumero)"
                + " FROM Repertorio r WHERE r.traNumero.traNumero=:traNumero"
                + " AND r.repNumero NOT IN(SELECT m.mrgAlt2 FROM Marginacion m)");
        q.setParameter("traNumero", traNumero);
        Long result = (Long) q.getSingleResult();
        return result == 0;
    }
    public String listarRepertoriosNoMarginados(Long traNumero) {
        String numerosRepFaltanMarginar="";
        Query q;
        q = getEntityManager().createQuery("SELECT r"
                + " FROM Repertorio r WHERE r.traNumero.traNumero=:traNumero"
                + " AND r.repNumero NOT IN(SELECT m.mrgAlt2 FROM Marginacion m)");
        q.setParameter("traNumero", traNumero);
        List<Repertorio> listaRepertoriosPendientesMrg=q.getResultList();
        for (Repertorio repertorio : listaRepertoriosPendientesMrg) {
            numerosRepFaltanMarginar+=" "+repertorio.getRepNumero();
        }
        return numerosRepFaltanMarginar;
    }

    public List<Marginacion> listarPor_NumActa(Long actNumero) {
        Query q;
        q = getEntityManager().createQuery("SELECT m FROM Marginacion m WHERE m.actNumero.actNumero=:actNumero ORDER BY m.mrgFch ASC");
        q.setParameter("actNumero", actNumero);
        return q.getResultList();
    }
    
    public List<Marginacion> listarPorMrgAlt2(String doc) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("mrgAlt2", doc);
        return listarPorConsultaJpaNombrada(Marginacion.LISTAR_POR_MRGALT2, parametros);
    }
    
    public List<Marginacion> listarPorMrgAlt2YActNum(String mrgAlt2, Long actNumero) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("mrgAlt2", mrgAlt2);
        parametros.put("actNumero", actNumero);
        return listarPorConsultaJpaNombrada(Marginacion.LISTAR_POR_MRGALT2_ACTNUM, parametros);
    }
    
    public List<Marginacion> listarPor_Repertorio_Acta(String mrgAlt2, Long actNumero) throws ServicioExcepcion{
        try {
            Query q;
        q=getEntityManager().createQuery("SELECT m FROM Marginacion m WHERE m.actNumero.actNumero = :actNumero AND m.mrgAlt2 = :mrgAlt2");
        
        q.setParameter("mrgAlt2", mrgAlt2);
        q.setParameter("actNumero", actNumero);
        
        return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
        
    }
    
    public List<Marginacion> listarPor_Repertorio(String mrgAlt2) throws ServicioExcepcion{
        try {
            Query q;
        q=getEntityManager().createQuery("SELECT m FROM Marginacion m WHERE m.mrgAlt2 = :mrgAlt2");
        
        q.setParameter("mrgAlt2", mrgAlt2);       
        
        return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
        
    }
    
    public List<Long> listarPor_NumRepertorio(String numRepertorio) {
        Query q;
        q = getEntityManager().createQuery("SELECT DISTINCT (m.actNumero.actNumero) FROM Marginacion m WHERE m.mrgAlt2 = :numRepertorio ORDER BY m.actNumero.actNumero ASC");
        q.setParameter("numRepertorio", numRepertorio);
        return q.getResultList();
    }
    
    public Marginacion obtenerPorNumRepertorio(String numRepertorio) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numRepertorio);
        try {
            return (Marginacion) obtenerPorConsultaNativa("SELECT DISTINCT Marginacion.* FROM Marginacion "
                    + "WHERE Marginacion.MrgAlt2 = ?", parametros, Acta.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
    
    public Marginacion obtenerPorActNumero(Long actNumero){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", actNumero);
        try{
            return (Marginacion) obtenerPorConsultaNativa("SELECT TOP 1 Marginacion.* FROM Marginacion "
                    + "WHERE Marginacion.ActNumero = ? ORDER BY Marginacion.MrgId DESC", parametros, Marginacion.class);
        } catch (ServicioExcepcion ex){
            return null;
        }
    }
    
    public Marginacion obtenerPorActNumero_PorNumRepertorio(Long actNumero, String numRepertorio){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", actNumero);
        parametros.put("2", numRepertorio);
        try{
            return (Marginacion) obtenerPorConsultaNativa("SELECT TOP 1 Marginacion.* FROM Marginacion "
                    + "WHERE Marginacion.ActNumero = ? AND Marginacion.MrgAlt2 = ? ORDER BY Marginacion.MrgId DESC", parametros, Marginacion.class);
        } catch (ServicioExcepcion ex){
            return null;
        }
    }
}
