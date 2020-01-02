/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.excepciones;

import java.io.Serializable;
import javax.ejb.ApplicationException;

/**
 *
 * @author RC_SALAZARG
 */
@ApplicationException(rollback = true)
public class ServicioExcepcion extends Exception implements Serializable{
   

    public ServicioExcepcion() {
    }

    public ServicioExcepcion(String message) {
        super(message);
    }

    public ServicioExcepcion(Throwable cause) {
        super(cause);
    }

    public ServicioExcepcion(String message, Throwable cause) {
        super(message, cause);
    }
    
    
}
