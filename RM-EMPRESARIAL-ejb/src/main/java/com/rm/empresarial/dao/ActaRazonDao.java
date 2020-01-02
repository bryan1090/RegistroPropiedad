/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.ActaRazon;
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
public class ActaRazonDao extends Generico<ActaRazon> implements Serializable {

    public ActaRazon obtenerRazonPornumActa(Long numActa) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", numActa);
            String sql = "SELECT TOP(1) * FROM Razon WHERE TraNumero = ? ";
            return (ActaRazon) obtenerPorConsultaNativa(sql, parametros, ActaRazon.class);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

}
