/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.puente.NuevasMatriculasPuente;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author DJimenez
 */

@LocalBean
@Stateless
public class NuevasMatriculasServicio extends NuevasMatriculasPuente implements Serializable{
    
    private static final long serialVersionUID = 3824296783361990611L;    
}
