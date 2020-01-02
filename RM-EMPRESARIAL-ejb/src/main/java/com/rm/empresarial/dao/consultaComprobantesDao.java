/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Institucion;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author DJimenez
 */
@LocalBean
@Stateless
public class consultaComprobantesDao extends Generico<Institucion> implements Serializable {

    public Institucion obtenerInstitucion(int insUser) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", insUser);
            String sql = "SELECT * FROM Institucion";
            return (Institucion) obtenerPorConsultaNativa(sql, parametros, Institucion.class);
        } catch (Exception e) {
            return null;
        }

    }
}
