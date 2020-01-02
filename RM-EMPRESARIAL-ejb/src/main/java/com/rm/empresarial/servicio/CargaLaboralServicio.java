/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.puente.CargaLaboralPuente;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class CargaLaboralServicio extends CargaLaboralPuente implements Serializable {
    
    private static final long serialVersionUID = 3523811509735283225L;
    
}
