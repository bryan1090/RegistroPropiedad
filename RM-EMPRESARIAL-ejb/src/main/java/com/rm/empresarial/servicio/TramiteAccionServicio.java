/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.puente.TramiteAccionPuente;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author USUARIO
 */
@LocalBean
@Stateless
 public class TramiteAccionServicio extends TramiteAccionPuente implements Serializable {   

    private static final long serialVersionUID = -2816683817362003948L;
}
