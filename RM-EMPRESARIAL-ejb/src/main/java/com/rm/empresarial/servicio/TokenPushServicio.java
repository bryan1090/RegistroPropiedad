/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.TokenPushDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TokenPush;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Wilson
 */

@LocalBean
@Stateless
public class TokenPushServicio extends TokenPushDao implements Serializable {
    
    
    public void guardarMensaje(TokenPush mensaje) throws ServicioExcepcion{
        guardarSalida(mensaje);
    }
    
     public void guardarM(TokenPush mensaje) throws ServicioExcepcion{
        guardar(mensaje);
    }
}
