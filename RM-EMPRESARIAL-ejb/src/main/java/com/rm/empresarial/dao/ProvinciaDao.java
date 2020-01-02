/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Provincia;
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
public class ProvinciaDao extends Generico<Provincia> implements Serializable {

    public List<Provincia> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Provincia.LISTAR_TODOS, null);
    }

    public Provincia encontrarProvinciaPorId(String idProvincia) throws ServicioExcepcion {
        Long id = new Long(idProvincia);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("proId", id);
        return obtenerPorConsultaJpaNombrada(Provincia.LISTAR_PORID, parametros);
    }
    
    public boolean validarNombreCrear(String proNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("proNombre", proNombre);
        return listarPorConsultaJpaNombrada(Provincia.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar(Long proId, String proNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("proId", proId);
        parametros.put("proNombre", proNombre);
        return listarPorConsultaJpaNombrada(Provincia.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }
}
