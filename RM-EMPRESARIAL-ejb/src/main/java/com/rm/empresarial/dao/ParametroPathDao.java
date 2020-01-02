/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Rol;
import java.io.Serializable;
import java.util.Date;
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
public class ParametroPathDao extends Generico<ParametroPath> implements Serializable {

    public List<ParametroPath> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(ParametroPath.LISTAR_TODO, null);
    }

    public ParametroPath obtenerParamPorTipoPorFecha(String tipo, Date fecha) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("prpTipo", tipo);
            parametros.put("fecha", fecha);
            return obtenerPorConsultaJpaNombrada(ParametroPath.OBTENER_POR_TIPO_Y_POR_FECHA, parametros);
        } catch (Exception e) {
            return null;
        }

    }

}
