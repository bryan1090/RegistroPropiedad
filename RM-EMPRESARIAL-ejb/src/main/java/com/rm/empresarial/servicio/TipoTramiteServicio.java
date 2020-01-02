/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Estado;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.puente.TipoTramitePuente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class TipoTramiteServicio extends TipoTramitePuente implements Serializable{
    
    private static final long serialVersionUID = 7030506855808306529L;
    
    public List<SelectItem> listaTipoTramite() throws ServicioExcepcion{
        List<TipoTramite> listaTipoTramites=null;
        List<SelectItem> listaTipoTramitesItems=new ArrayList<>();
        listaTipoTramites=listarTodo();
        for(TipoTramite tipo:listaTipoTramites){
            String aux=tipo.getTtrDescripcion();
            String descripcion=String.valueOf(aux);
            SelectItem selectItem=new SelectItem(tipo.getTtrId(),descripcion);
            listaTipoTramitesItems.add(selectItem);
            
        }
        return listaTipoTramitesItems;
        
    }

   
    
}
