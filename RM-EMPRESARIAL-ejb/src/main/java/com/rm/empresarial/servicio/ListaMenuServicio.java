/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.ListaMenuDao;
import com.rm.empresarial.dao.MenuDao;
import com.rm.empresarial.modelo.ListaMenu;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Marco
 */
@LocalBean
@Stateless
public class ListaMenuServicio  extends ListaMenuDao implements Serializable  {
    
}
