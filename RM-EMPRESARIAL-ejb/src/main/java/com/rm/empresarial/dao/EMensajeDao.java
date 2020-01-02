/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.EMensaje;
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
public class EMensajeDao extends Generico<EMensaje> implements Serializable {

    public List<EMensaje> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(EMensaje.LISTAR_TODO, null);
    }
    
    public EMensaje obtenerPorID(Long mensajeId) throws ServicioExcepcion {
         Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", mensajeId);
        return (EMensaje) obtenerPorConsultaNativa("select * from EMensaje WHERE EMsIdSec= ? ", parametros ,EMensaje.class);
    }

    public EMensaje buscarPorFactura(String numero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("eMsgAutorizacion", numero);
        return obtenerPorConsultaJpaNombrada(EMensaje.BUSCAR_POR_NUMERO, parametros);
    }

    public List<EMensaje> buscarPorSerie_Y_Numero(String serie, String numero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("eMsgSerie", serie);
        parametros.put("eMsgNumero", numero);
        return listarPorConsultaJpaNombrada(EMensaje.BUSCAR_POR_SERIE_Y_NUMERO, parametros);
    }
}
