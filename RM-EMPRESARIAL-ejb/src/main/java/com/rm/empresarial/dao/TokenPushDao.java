/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TokenPush;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class TokenPushDao extends Generico<TokenPush>implements Serializable {
    
    public TokenPush obtenerPorUsuId(Long usuId) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        try {
            return (TokenPush) obtenerPorConsultaNativa("SELECT TokenPush.* FROM TokenPush WHERE TokenPush.UsuId = ?", parametros, TokenPush.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
    
    public List<TokenPush> listarUsuariosActivosAPP() throws ServicioExcepcion {
        return listarPorConsultaNativa("SELECT TokenPush.* FROM TokenPush WHERE TokenPush.TkpEstado = 'A'", null, TokenPush.class);
    }
    
}
