/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.MenuDao;
import com.rm.empresarial.dao.RolMenuDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Menu;
import com.rm.empresarial.modelo.RolMenu;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author Marco
 */

@LocalBean
@Stateless
public class RolMenuServicio extends  RolMenuDao implements Serializable{
   

    private static final long serialVersionUID = 7030506855808306529L;
    /*
    public List<SelectItem> listaMenu() throws ServicioExcepcion{
        List<Menu> listaMenu=null;
        List<SelectItem> listaMenuItems=new ArrayList<>();
        listaMenu=ListarTodo();
        for(Menu tipo:listaMenu){
            String aux=tipo.getMenNombre();
            String descripcion=String.valueOf(aux);
            SelectItem selectItem=new SelectItem(tipo.getMenId(),descripcion);
            listaMenuItems.add(selectItem);
            
        }
        return listaMenuItems;
       */ 
    }
    

