/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Seleccion;
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
public class SeleccionDao extends Generico<Seleccion> implements Serializable{
    public List<Seleccion> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Seleccion.LISTAR_TODOS, null);
    }
      public Seleccion encontrarSeleccionAtributoPorId(String idSeleccion) throws ServicioExcepcion
    {
        Long id=new Long(idSeleccion);
        
        Map<String,Object> parametros=new HashMap<>();
        parametros.put("slcId", id);
        return obtenerPorConsultaJpaNombrada(Seleccion.LISTAR_PORID, parametros);
    }
}
