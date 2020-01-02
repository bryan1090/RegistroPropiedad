/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.ActaDao;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class ActaServicio extends ActaDao implements Serializable {
    
}
