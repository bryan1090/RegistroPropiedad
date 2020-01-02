/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Tomo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class TomoDao extends Generico<Tomo> implements Serializable{
    
    public List<Tomo> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Tomo.LISTAR_TODO, null);
    }
    
    public Tomo buscarPorAnioYtipoLib(Long tplId, Long tomAnio) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tplId);
        parametros.put("2", tomAnio);
        try{
            return (Tomo) obtenerPorConsultaNativa("SELECT Tomo.* FROM Tomo "
                + "WHERE Tomo.TplId = ? AND Tomo.TomAnio = ?", parametros, Tomo.class);
        }catch (ServicioExcepcion e) {
            return new Tomo();
        }
    }
    
   public List<Tomo> listarPorAnioYtipoLib(Long tplId, String tomAnio) throws ServicioExcepcion {
        try {
             Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tplId);
        parametros.put("2", tomAnio);
        return listarPorConsultaNativa("SELECT Tomo.* FROM Tomo "
                + "WHERE Tomo.TplId = ? AND Tomo.TomAnio = ?", parametros, Tomo.class);
    
        } catch (Exception e) {
            return null;
        }
       }
    
    public List<Tomo> listarAnio() throws ServicioExcepcion {
       
        return listarPorConsultaNativa("select DISTINCT TomAnio from Tomo  ", null, Tomo.class);
    }
    
    public List<Tomo> listarPorTplId(Long tplId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tplId);
        return listarPorConsultaNativa("SELECT Tomo.* FROM Tomo "
                + "WHERE Tomo.TplId = ?", parametros, Tomo.class);
    }
    
}
