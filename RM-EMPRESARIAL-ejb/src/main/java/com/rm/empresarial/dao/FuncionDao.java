/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Funcion;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class FuncionDao extends Generico<Funcion> implements Serializable {

    public List<Funcion> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Funcion.LISTAR_TODO, null);
    }

    public Funcion buscarPorNombrePagina(String nombrePagina) throws ServicioExcepcion {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("funPrograma", nombrePagina);
            return obtenerPorConsultaJpaNombrada(Funcion.ENCONTRAR_POR_PROGRAMA, parametros);
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean validarIngresoCrear(Long rolId, String funPrograma) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rolId", rolId);
        parametros.put("funPrograma", funPrograma);
        return listarPorConsultaJpaNombrada(Funcion.VALIDAR_INGRESO, parametros).isEmpty();
    }
    
    public boolean validarIngresoEditar(Long funId, Long rolId, String funPrograma) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("funId", funId);
        parametros.put("rolId", rolId);
        parametros.put("funPrograma", funPrograma);
        return listarPorConsultaJpaNombrada(Funcion.VALIDAR_INGRESO_EDITAR, parametros).isEmpty();
    }
}
