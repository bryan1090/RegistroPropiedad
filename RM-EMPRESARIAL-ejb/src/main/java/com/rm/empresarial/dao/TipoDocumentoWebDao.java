/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoDocumentoWeb;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author DJimenez
 */
@LocalBean
@Stateless
public class TipoDocumentoWebDao extends Generico<TipoDocumentoWeb> implements Serializable {

    public List<TipoDocumentoWeb> listarTodo() {
        try {
            return listarPorConsultaJpaNombrada(TipoDocumentoWeb.LISTAR_TODO, null);
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean validarNombreCrear(String tdwNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tdwNombre", tdwNombre);
        return listarPorConsultaJpaNombrada(TipoDocumentoWeb.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar(Long tdwId, String tdwNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tdwId", tdwId);
        parametros.put("tdwNombre", tdwNombre);
        return listarPorConsultaJpaNombrada(TipoDocumentoWeb.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }
    
}
