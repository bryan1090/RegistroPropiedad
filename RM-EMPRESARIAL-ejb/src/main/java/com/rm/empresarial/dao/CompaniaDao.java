/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Compania;
import com.rm.empresarial.modelo.Gravamen;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class CompaniaDao extends Generico<Compania> implements Serializable {

    public List<Compania> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Compania.LISTAR_TODOS, null);
    }

    public Compania buscarPorIdCompania(Long idCompania) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("comId", idCompania);

        return obtenerPorConsultaJpaNombrada(Compania.BUSCAR_POR_ID_COMPANIA, parametros);
    }

    public Compania buscarCompaniaReciente() throws ServicioExcepcion {
        String sql = "SELECT TOP 1 * FROM Compania ORDER BY ComId DESC";
        return (Compania) obtenerPorConsultaNativa(sql, null, Compania.class);
    }

    public Compania buscarCompaniaPorNombre(String nombreCompania) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", nombreCompania);
            String sql = "SELECT TOP 1 * FROM Compania WHERE ComNombre = ? ";
            return (Compania) obtenerPorConsultaNativa(sql, parametros, Compania.class);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

}
