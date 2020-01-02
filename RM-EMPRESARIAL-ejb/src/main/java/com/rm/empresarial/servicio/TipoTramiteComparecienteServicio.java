/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.puente.TipoTramiteComparecientePuente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author WILSON
 */

@LocalBean
@Stateless
public class TipoTramiteComparecienteServicio extends TipoTramiteComparecientePuente implements Serializable {

    private static final long serialVersionUID = 84833725989271474L;

    
    public List<SelectItem> listaTipoT(TipoTramite tipTramite) throws ServicioExcepcion{
        List<TipoTramiteCompareciente> listaTipoTs=null;
        List<SelectItem> listaTipoTsItems=new ArrayList<>();
        listaTipoTs=listarTodo();
        for(TipoTramiteCompareciente tipoTramite:listaTipoTs){
            if(tipTramite.equals(tipoTramite.getTtrId())){
            String aux = tipoTramite.getTpcId().getTpcDescripcion();
            String numeroTipoT=String.valueOf(aux);
            SelectItem selectItem=new SelectItem(tipoTramite.getTtcId(),numeroTipoT);
            listaTipoTsItems.add(selectItem);
            }
            
        }
        return listaTipoTsItems;
        
    }
    
}
