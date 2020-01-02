/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoPropiedadClase;
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
public class TipoPropiedadClaseDao extends Generico<TipoPropiedadClase> implements Serializable {

    public List<TipoPropiedadClase> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoPropiedadClase.LISTAR_TODO, null);
    }

    public TipoPropiedadClase encontrarTipoPropiedadClasePorId(String tclId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tclId", tclId);
        return obtenerPorConsultaJpaNombrada(TipoPropiedadClase.LISTAR_PORID, parametros);
    }

    public boolean validarNombreCrear(String tclNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tclNombre", tclNombre);
        return listarPorConsultaJpaNombrada(TipoPropiedadClase.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarIdCrear(String tclId) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tclId", tclId);
        return listarPorConsultaJpaNombrada(TipoPropiedadClase.LISTAR_PORID, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar(String tclId, String tclNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tclId", tclId);
        parametros.put("tclNombre", tclNombre);
        return listarPorConsultaJpaNombrada(TipoPropiedadClase.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }
}
