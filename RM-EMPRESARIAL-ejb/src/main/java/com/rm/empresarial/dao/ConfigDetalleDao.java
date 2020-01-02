/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.ConfigDetalle;
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
public class ConfigDetalleDao extends Generico<ConfigDetalle> implements Serializable {

    public List<ConfigDetalle> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(ConfigDetalle.LISTAR_TODOS, null);
    }

    public ConfigDetalle buscarPorConfig() throws ServicioExcepcion {
        return (ConfigDetalle) obtenerPorConsultaNativa("SELECT TOP 1 ConfigDetalle.* FROM Config "
                + "INNER JOIN ConfigDetalle ON ConfigDetalle.ConfigId = Config.ConfigId WHERE "
                + "Config.ConfigDescripcion = 'CERTIFICADO'", null, ConfigDetalle.class);
    }

    public ConfigDetalle buscarPorConfig(String ConfigDescripcion) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", ConfigDescripcion);
        return (ConfigDetalle) obtenerPorConsultaNativa("SELECT TOP 1 ConfigDetalle.* FROM Config "
                + "INNER JOIN ConfigDetalle ON ConfigDetalle.ConfigId = Config.ConfigId WHERE "
                + "Config.ConfigDescripcion = ?", parametros, ConfigDetalle.class);
    }

    public List<ConfigDetalle> listarPorConfigDesc(String ConfigDescripcion) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", ConfigDescripcion);
        return listarPorConsultaNativa("SELECT ConfigDetalle.* FROM Config "
                + "INNER JOIN ConfigDetalle ON ConfigDetalle.ConfigId = Config.ConfigId WHERE "
                + "Config.ConfigDescripcion = ?", parametros, ConfigDetalle.class);
    }

    public ConfigDetalle obtenerConfigDetallePorDescripcion(String configDescripcion) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", configDescripcion);
        String sql = "SELECT * FROM ConfigDetalle cd "
                + "INNER JOIN Config c ON c.ConfigId = cd.ConfigId "
                + "WHERE c.ConfigDescripcion = ? ";
        return (ConfigDetalle) obtenerPorConsultaNativa(sql, parametros, ConfigDetalle.class);
    }
}
