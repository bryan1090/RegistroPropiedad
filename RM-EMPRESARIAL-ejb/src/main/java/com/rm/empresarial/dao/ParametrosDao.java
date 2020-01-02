/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Parametros;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class ParametrosDao extends Generico<Parametros> implements Serializable {

    private static final long serialVersionUID = 7268251635241861559L;

    public List<Parametros> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Parametros.LISTAR_TODO, null);
    }

    public Parametros buscarPorParametro(String parametro) throws ServicioExcepcion {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("prmParametro", parametro.trim());
            return obtenerPorConsultaJpaNombrada(Parametros.BUSCAR_POR_PARAMETRO, parametros);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }

    }

}
