/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoActa;
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
public class TipoActaDao extends Generico<TipoActa> implements Serializable {

    public List<TipoActa> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoActa.LISTAR_TODO, null);
    }

    public TipoActa encontrarTipoActaPorId(String tpaId) throws ServicioExcepcion {
        Long id = new Long(tpaId);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpaId", id);
        return obtenerPorConsultaJpaNombrada(TipoActa.LISTAR_PORID, parametros);
    }
    
    public boolean validarNombreCrear(String tpaNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpaNombre", tpaNombre);
        return listarPorConsultaJpaNombrada(TipoActa.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar(Long tpaId, String tpaNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpaId", tpaId);
        parametros.put("tpaNombre", tpaNombre);
        return listarPorConsultaJpaNombrada(TipoActa.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }

}
