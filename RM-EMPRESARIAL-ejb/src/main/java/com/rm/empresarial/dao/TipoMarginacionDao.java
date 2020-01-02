/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoMarginacion;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Prueba
 */
public class TipoMarginacionDao extends Generico<TipoMarginacion> implements Serializable {
    public List<TipoMarginacion> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoMarginacion.LISTAR_TODOS, null);
    }
    
    public boolean validarNombreCrear(String tmcNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tmcNombre", tmcNombre);
        return listarPorConsultaJpaNombrada(TipoMarginacion.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar(Long tmcId, String tmcNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tmcId", tmcId);
        parametros.put("tmcNombre", tmcNombre);
        return listarPorConsultaJpaNombrada(TipoMarginacion.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }
    
}
