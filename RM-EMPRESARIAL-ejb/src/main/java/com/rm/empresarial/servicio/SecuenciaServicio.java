/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.puente.SecuenciaPuente;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author WILSON
 */
@LocalBean
@Stateless
public class SecuenciaServicio extends SecuenciaPuente implements Serializable{

    private static final long serialVersionUID = 3824296783361990611L;

    
}
