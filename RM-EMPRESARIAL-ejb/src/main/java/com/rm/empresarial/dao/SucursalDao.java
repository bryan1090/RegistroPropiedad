/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Sucursal;
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
public class SucursalDao extends Generico<Sucursal> implements Serializable {

    public List<Sucursal> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Sucursal.LISTAR_TODO, null);
    }

    public Sucursal buscarSucursalPorID(String sucId) throws ServicioExcepcion {
        Long id = new Long(sucId);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("sucId", id);
        return obtenerPorConsultaJpaNombrada(Sucursal.BUSCAR_POR_ID, parametros);
    }
    
    public boolean validarNombreCrear(String sucNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("sucNombre", sucNombre);
        return listarPorConsultaJpaNombrada(Sucursal.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar(Long sucId, String sucNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("sucId", sucId);
        parametros.put("sucNombre", sucNombre);
        return listarPorConsultaJpaNombrada(Sucursal.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }

}
