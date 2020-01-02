/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.CodigoVerificacion;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class CodigoVerificacionDao extends Generico<CodigoVerificacion> implements Serializable {

    public CodigoVerificacion encontrarPorUsuario(Long usuId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", usuId);
            return (CodigoVerificacion) obtenerPorConsultaNativa("SELECT CodigoVerificacion.* FROM CodigoVerificacion "
                    + "WHERE CodigoVerificacion.UsuId = ?", parametros, CodigoVerificacion.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

}
