/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.FormularioWeb;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author DJimenez
 */
@LocalBean
@Stateless
public class FormularioWebDao extends Generico<FormularioWeb> implements Serializable {

    public FormularioWeb buscarFormulario(int FlwId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", FlwId);
            String sql = "SELECT * FROM FormularioWeb fb WHERE fb.FlwId = ?";
            return (FormularioWeb) obtenerPorConsultaNativa(sql, parametros, FormularioWeb.class);
        } catch (Exception e) {            
            return null;
        }
    }
    
    public List<FormularioWeb> buscarPorNumeroFormulario(int flwId) {
        try {
            String sql = "SELECT * FROM FormularioWeb fb WHERE fb.FlwId = ?";
            Query q= getEntityManager().createNativeQuery(sql, FormularioWeb.class);
            q.setParameter("1", flwId);
            return q.getResultList();
        } catch (Exception e) {            
            return null;
        }
    }
    public List<FormularioWeb> listarPorFecha(Date fechaIni, Date fechaFin,String usuario) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        parametros.put("3", usuario);
         List<FormularioWeb> lista = listarPorConsultaNativa("SELECT FormularioWeb.* FROM FormularioWeb "
                + " WHERE CONVERT(DATE,  FormularioWeb.FlwFecha) = ? "
                + " AND CONVERT(DATE,  FormularioWeb.FlwFecha) = ? "
                + " AND FormularioWeb.FlwUser = ?  "
                , parametros,FormularioWeb.class);
        return lista; 
    }
     public List<FormularioWeb> listarFecha(Date fechaIni,Date fechaFin,String user) throws ServicioExcepcion { 
             Map<String, Object> parametros = new HashMap<>();
             parametros.put("fechaIni", fechaIni);
             parametros.put("fechaFin", fechaFin);
             parametros.put("user", user);
        return listarPorConsultaJpa("SELECT fw FROM FormularioWeb fw  " +
                                 " WHERE fw.flwFecha >= :fechaIni  " +
                                 " AND fw.flwFecha <= :fechaFin " +
                                " AND fw.flwUser = :user ", parametros);
    }
     public List<FormularioWeb> listarPorTramite(Long traNumero,String usuario) {
        try {
            String sql = "SELECT * FROM FormularioWeb fw WHERE fw.FlwId = ? AND fw.FlwUser = ? ";
            Query q= getEntityManager().createNativeQuery(sql, FormularioWeb.class);
            q.setParameter("1", traNumero);
            q.setParameter("2", usuario);
            return q.getResultList();
        } catch (Exception e) {            
            return null;
        }
    }
      public List<FormularioWeb> listarPorTramiteRango(Long traNumeroIni,Long traNumeroFin,String usuario) {
        try {
            String sql = "SELECT * FROM FormularioWeb fw WHERE fw.FlwId >= ? AND fw.FlwId <= ? AND fw.FlwUser = ? ";
            Query q= getEntityManager().createNativeQuery(sql, FormularioWeb.class);
            q.setParameter("1", traNumeroIni);
            q.setParameter("2", traNumeroFin);
            q.setParameter("3", usuario);
            return q.getResultList();
        } catch (Exception e) {            
            return null;
        }
    }
      public List<FormularioWeb> listarPorUsuario(String usuario) {
        try {
            String sql = "SELECT * FROM FormularioWeb fw WHERE fw.FlwUser = ? ";
            Query q= getEntityManager().createNativeQuery(sql, FormularioWeb.class);
           
            q.setParameter("1", usuario);
            return q.getResultList();
        } catch (Exception e) {            
            return null;
        }
    }
     
     public FormularioWeb buscarTramite(String traNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", traNumero);
        
        try {
             return (FormularioWeb) obtenerPorConsultaNativa(" SELECT * FROM FormularioWeb fb WHERE fb.FlwId = ? ", parametros, FormularioWeb.class);
        } 
        catch (Exception e) {
            return null;
        }
        }
        
    
    public List<FormularioWeb> buscarFormularioPorFecha(Date fechaInicio, Date fechaFin) {
        try {
            String sql = "SELECT * FROM FormularioWeb fb WHERE CONVERT(DATE,fb.FlwFHR) >= ? AND CONVERT(DATE,fb.FlwFHR)<=? ";
            Query q= getEntityManager().createNativeQuery(sql, FormularioWeb.class);
            q.setParameter("1", fechaInicio);
            q.setParameter("2", fechaFin);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
