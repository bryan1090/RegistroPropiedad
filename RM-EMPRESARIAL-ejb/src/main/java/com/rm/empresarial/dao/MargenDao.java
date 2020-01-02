/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Margen;
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
public class MargenDao extends Generico<Margen> implements Serializable{
    
    public List<Margen> listarTodo() throws ServicioExcepcion{
        return listarPorConsultaJpaNombrada(Margen.LISTAR_TODO, null);
    }
    
    public Margen obtenerPorMarTipo(String marTipo) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("marTipo", marTipo);
        return obtenerPorConsultaJpaNombrada(Margen.BUSCAR_POR_MAR_TIPO, parametros);
    }
    
}
