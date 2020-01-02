/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.puente.RepositorioRCPuente;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author USUARIO
 */
@LocalBean
@Stateless
public class RepositorioRCServicio extends RepositorioRCPuente implements Serializable {
    
    private static final long serialVersionUID = 1579766536424602107L;
    
}
