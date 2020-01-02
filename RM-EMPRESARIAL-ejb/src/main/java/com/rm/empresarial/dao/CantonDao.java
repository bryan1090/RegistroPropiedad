/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Canton;
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
public class CantonDao extends Generico<Canton> implements Serializable {

    public List<Canton> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Canton.LISTAR_TODOS, null);
    }

    public Canton encontrarCantonPorId(String idCanton) throws ServicioExcepcion {
        Long id = new Long(idCanton);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("canId", id);
        return obtenerPorConsultaJpaNombrada(Canton.LISTAR_PORID, parametros);
    }
    
    public boolean validarNombreCrear(String canNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("canNombre", canNombre);
        return listarPorConsultaJpaNombrada(Canton.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar(Long canId, String canNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("canId", canId);
        parametros.put("canNombre", canNombre);
        return listarPorConsultaJpaNombrada(Canton.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }
}
