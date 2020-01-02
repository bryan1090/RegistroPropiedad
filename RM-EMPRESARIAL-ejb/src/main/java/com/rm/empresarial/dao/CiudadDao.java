/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Ciudad;
import com.rm.empresarial.modelo.TmpAlicuota;
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
public class CiudadDao extends Generico<Ciudad> implements Serializable {

    public List<Ciudad> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Ciudad.LISTAR_TODOS, null);
    }

     public Ciudad encontrarPorCantonId(Long idCanton) throws ServicioExcepcion {
        try {
             Map<String, Object> parametros = new HashMap<>();
             parametros.put("1", idCanton);
            return (Ciudad) obtenerPorConsultaNativa("SELECT TOP 1 * FROM Ciudad "
                    + " WHERE Ciudad.CanId = ?", null, Ciudad.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
    public Ciudad encontrarCiudadPorId(String idCiudad) throws ServicioExcepcion {
        Long id = new Long(idCiudad);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ciuId", id);
        return obtenerPorConsultaJpaNombrada(Ciudad.LISTAR_PORID, parametros);
    }

    public boolean validaNombreCrear(String ciuNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ciuNombre", ciuNombre);
        return listarPorConsultaJpaNombrada(Ciudad.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    

    public boolean validarNombreEditar(Long ciuId, String ciuNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ciuId", ciuId);
        parametros.put("ciuNombre", ciuNombre);
        return listarPorConsultaJpaNombrada(Ciudad.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }
}
