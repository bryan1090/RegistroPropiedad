/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.puente.RepositorioSRIPuente;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class RepositorioSRIServicio extends RepositorioSRIPuente implements Serializable {
    
    private static final long serialVersionUID = 1505213061024031480L;
    
    
}
