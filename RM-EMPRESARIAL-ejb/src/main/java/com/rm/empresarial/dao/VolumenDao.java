/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Volumen;
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
public class VolumenDao extends Generico<Volumen> implements Serializable{
    
    public List<Volumen> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Volumen.LISTAR_TODO, null);
    }
    
    public Volumen buscarPorTomo(Long tomId) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tomId);
        try {
            return (Volumen) obtenerPorConsultaNativa("SELECT TOP 1 Volumen.* FROM "
                + "Volumen WHERE Volumen.TomId = ? ORDER BY Volumen.VleVolumen DESC", parametros, Volumen.class);
        } catch (ServicioExcepcion e) {
            return new Volumen();
        }
    }
    
    public List<Volumen> listarPorTomo (Long tomId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tomId);
        return listarPorConsultaNativa("SELECT Volumen.* FROM "
                + "Volumen WHERE Volumen.TomId = ? ORDER BY Volumen.VleVolumen ASC", parametros, Volumen.class);
    }
    
      public List<Volumen> listarPorTipoYaño (Long tomId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tomId);
        return listarPorConsultaNativa("SELECT Volumen.* FROM "
                + " Volumen WHERE Volumen.TomId = ? "
                + " ORDER BY Volumen.VleVolumen ASC ", parametros, Volumen.class);
    }
    
}
