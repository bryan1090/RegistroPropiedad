/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.FacturaDetalleAdicional;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Wilson Herrera
 */
@LocalBean
@Stateless
public class FacturaDetalleAdicionalDao extends Generico<FacturaDetalleAdicional> implements Serializable {
    
     private static final long serialVersionUID = 1L;

}
