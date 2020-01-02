/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Mensaje;
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
public class MensajeDao extends Generico<Mensaje> implements Serializable {

    public List<Mensaje> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Mensaje.LISTAR_TODOS, null);
    }
    
    public boolean validarNumeroCrear(short msjNumero) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("msjNumero", msjNumero);
        return listarPorConsultaJpaNombrada(Mensaje.BUSCAR_POR_NUM, parametros).isEmpty();
    }
    
    public boolean validarDescripCrear(String msjDescripcion) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("msjDescripcion", msjDescripcion);
        return listarPorConsultaJpaNombrada(Mensaje.BUSCAR_POR_DESC, parametros).isEmpty();
    }
    
    public boolean validarNumeroEditar(Long msjId, short msjNumero) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("msjId", msjId);
        parametros.put("msjNumero", msjNumero);
        return listarPorConsultaJpaNombrada(Mensaje.BUSCAR_POR_NUM_EDITAR, parametros).isEmpty();
    }
    
    public boolean validarDescripEditar(Long msjId, String msjDescripcion) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("msjId", msjId);
        parametros.put("msjDescripcion", msjDescripcion);
        return listarPorConsultaJpaNombrada(Mensaje.BUSCAR_POR_DESC_EDITAR, parametros).isEmpty();
    }

}
