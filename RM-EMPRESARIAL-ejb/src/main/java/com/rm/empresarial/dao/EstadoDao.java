/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Estado;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Richard
 */
@LocalBean
@Stateless
public class EstadoDao extends Generico<Estado> implements Serializable {

    public List<Estado> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Estado.LISTAR_TODOS, null);
    }

    public Estado encontrarEstadoAtributoPorId(String idEstado) throws ServicioExcepcion {
        Long id = new Long(idEstado);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("estId", id);
        return obtenerPorConsultaJpaNombrada(Estado.LISTAR_PORID, parametros);
    }
    
    public boolean validarCodigoCrear(String estCodigo) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("estCodigo", estCodigo);
        return listarPorConsultaJpaNombrada(Estado.BUSCAR_POR_COD, parametros).isEmpty();
    }
    
    public boolean validarDescripCrear(String estDescripcion) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("estDescripcion", estDescripcion);
        return listarPorConsultaJpaNombrada(Estado.BUSCAR_POR_DESC, parametros).isEmpty();
    }
    
    public boolean validarCodigoEditar(Long estId, String estCodigo) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("estId", estId);
        parametros.put("estCodigo", estCodigo);
        return listarPorConsultaJpaNombrada(Estado.BUSCAR_POR_COD_EDITAR, parametros).isEmpty();
    }
    
    public boolean validarDescripEditar(Long estId, String estDescripcion) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("estId", estId);
        parametros.put("estDescripcion", estDescripcion);
        return listarPorConsultaJpaNombrada(Estado.BUSCAR_POR_DESC_EDITAR, parametros).isEmpty();
    }
}
