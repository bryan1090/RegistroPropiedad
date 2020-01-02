/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoMarginacionMensaje;
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
public class TipoMarginacionMensajeDao extends Generico<TipoMarginacionMensaje> implements Serializable {
    
    public List<TipoMarginacionMensaje> listarTodo() throws ServicioExcepcion{
        return listarPorConsultaJpaNombrada(TipoMarginacionMensaje.LISTAR_TODO, null);
    }
    
    public boolean validarVaribleCrear(String tmmVariable) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tmmVariable", tmmVariable);
        return listarPorConsultaJpaNombrada(TipoMarginacionMensaje.BUSCAR_POR_VAR, parametros).isEmpty();
    }
    
    public boolean validarVaribleEditar(Long tmmId, String tmmVariable) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tmmId", tmmId);
        parametros.put("tmmVariable", tmmVariable);
        return listarPorConsultaJpaNombrada(TipoMarginacionMensaje.BUSCAR_POR_VAR_EDITAR, parametros).isEmpty();
    }
    
}
