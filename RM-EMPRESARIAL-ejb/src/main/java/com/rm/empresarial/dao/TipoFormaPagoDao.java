/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoFormaPago;
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
public class TipoFormaPagoDao extends Generico<TipoFormaPago> implements Serializable {

    public List<TipoFormaPago> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoFormaPago.LISTAR_TODO, null);
    }

    public boolean validarDescripcion(String tpfDescripcion) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpfDescripcion", tpfDescripcion);
        return listarPorConsultaJpaNombrada(TipoFormaPago.VALIDAR_DESC, parametros).isEmpty();
    }

    public TipoFormaPago buscarPorId(String id) throws ServicioExcepcion {
        Long tpfId = new Long(id);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpfId", tpfId);
        return obtenerPorConsultaJpaNombrada(TipoFormaPago.BUSCAR_POR_ID, parametros);

    }

    public boolean validarDescripcionCrear(String tpfDescripcion) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpfDescripcion", tpfDescripcion);
        return listarPorConsultaJpaNombrada(TipoFormaPago.VALIDAR_DESC_CREAR, parametros).isEmpty();
    }

    public boolean validarDescripcionEditar(Long tpfId, String tpfDescripcion) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpfId", tpfId);
        parametros.put("tpfDescripcion", tpfDescripcion);
        return listarPorConsultaJpaNombrada(TipoFormaPago.VALIDAR_DESC_EDITAR, parametros).isEmpty();
    }
}
