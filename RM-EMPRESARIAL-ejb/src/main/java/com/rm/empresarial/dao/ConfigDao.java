/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Config;
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
public class ConfigDao extends Generico<Config> implements Serializable {

    public List<Config> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Config.LISTAR_TODOS, null);
    }

    public Config encontrarEstadoAtributoPorId(String idConfig) throws ServicioExcepcion {
        Long id = new Long(idConfig);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("configId", id);
        return obtenerPorConsultaJpaNombrada(Config.LISTAR_PORID, parametros);
    }
}
