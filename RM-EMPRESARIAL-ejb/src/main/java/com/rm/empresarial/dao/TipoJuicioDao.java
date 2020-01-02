/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoJuicio;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class TipoJuicioDao extends Generico<TipoJuicio> implements Serializable{
    
    public List<TipoJuicio> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoJuicio.LISTAR_TODO, null);
    }
    
    public TipoJuicio encontrarTipoJuicioPorId(Long idTipoJuicio) throws ServicioExcepcion {
        
        Map<String, Object> parametros = new HashMap<>();        
        parametros.put("tpjId", idTipoJuicio);
        return obtenerPorConsultaJpaNombrada(TipoJuicio.BUSCAR_POR_ID, parametros);
    }
    
}
