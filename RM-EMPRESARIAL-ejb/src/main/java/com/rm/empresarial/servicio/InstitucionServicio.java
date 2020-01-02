/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.puente.InstitucionPuente;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class InstitucionServicio extends InstitucionPuente implements Serializable {
    
    private static final long serialVersionUID = 2741826309764205178L;
    
}
