/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.puente.NotariaPuente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class NotariaServicio extends NotariaPuente implements Serializable {
    
    private static final long serialVersionUID = 3804741738462740090L;
    
    public List<SelectItem> listaNotaria() throws ServicioExcepcion{
        List<Notaria> listaNotarias=null;
        List<SelectItem> listaNotariasItems=new ArrayList<>();
        listaNotarias=listarTodo();
        for(Notaria notaria:listaNotarias){
            
            String numeroNotaria=notaria.getNotNotario().trim();
            SelectItem selectItem=new SelectItem(notaria.getNotId(),numeroNotaria);
            listaNotariasItems.add(selectItem);
            
        }
        return listaNotariasItems;
        
    }
    
}
