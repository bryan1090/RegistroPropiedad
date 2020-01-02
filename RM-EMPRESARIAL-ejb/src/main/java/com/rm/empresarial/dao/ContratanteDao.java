/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Contratante;
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
public class ContratanteDao extends Generico<Contratante> implements Serializable {

    public List<Contratante> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Contratante.LISTAR_TODOS, null);
    }
    
    public boolean validarCodigoCrear(String conCodigo) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("conCodigo", conCodigo);
        return listarPorConsultaJpaNombrada(Contratante.BUSCAR_POR_COD, parametros).isEmpty();
    }
    
    public boolean validarDescripCrear(String conDescripcion) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("conDescripcion", conDescripcion);
        return listarPorConsultaJpaNombrada(Contratante.BUSCAR_POR_DESC, parametros).isEmpty();
    }
    
    public boolean validarCodigoEditar(Long conId, String conCodigo) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("conId", conId);
        parametros.put("conCodigo", conCodigo);
        return listarPorConsultaJpaNombrada(Contratante.BUSCAR_POR_COD_EDITAR, parametros).isEmpty();
    }
    
    public boolean validarDescripEditar(Long conId, String conDescripcion) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("conId", conId);
        parametros.put("conDescripcion", conDescripcion);
        return listarPorConsultaJpaNombrada(Contratante.BUSCAR_POR_DESC_EDITAR, parametros).isEmpty();
    }
    
}
