/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Caja;
import com.rm.empresarial.modelo.Sucursal;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class CajaDao extends Generico<Caja> implements Serializable {
    
    public List<Caja> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Caja.LISTAR_TODO, null);
    }

    public Caja encontrarCajaPorId(Long id) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cajId", id);
        return obtenerPorConsultaJpaNombrada(Caja.BUSCAR_POR_ID, parametros);
    }
    
    public Caja buscarCajaPorId(String cajId) throws ServicioExcepcion {
        Long id = new Long(cajId);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cajId", id);
        return obtenerPorConsultaJpaNombrada(Caja.BUSCAR_POR_ID, parametros);
    }
    
    public boolean validacionIngresarCrear(String cajNombre, Long sucId) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cajNombre", cajNombre);
        parametros.put("sucId", sucId);
        return listarPorConsultaJpaNombrada(Caja.VALIDACION_INGRESO, parametros).isEmpty();
    }
    
    public boolean validarNombreCrear(String cajNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cajNombre", cajNombre);
        return listarPorConsultaJpaNombrada(Caja.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar(Long cajId, String cajNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cajId", cajId);
        parametros.put("cajNombre", cajNombre);
        return listarPorConsultaJpaNombrada(Caja.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }
    
    public boolean validacionIngresarEditar(Long cajId, String cajNombre, Long sucId) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cajId", cajId);
        parametros.put("cajNombre", cajNombre);
        parametros.put("sucId", sucId);
        return listarPorConsultaJpaNombrada(Caja.VALIDACION_INGRESO_EDITAR, parametros).isEmpty();
    }
}
