/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.servicio;

import com.rm.empresarial.dao.MenuDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Menu;
import com.rm.empresarial.modelo.TipoTramite;
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
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class MenuServicio extends MenuDao implements Serializable {
    private static final long serialVersionUID = 7030506855808306529L;
    
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
        
    }
    
    public boolean validarIdCrear (String menId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("menId", menId);
        return listarPorConsultaJpaNombrada(Menu.BUSCAR_POR_ID, parametros).isEmpty();
    }
    
    public boolean validarNombreCrear (String menNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("menNombre", menNombre);
        return listarPorConsultaJpaNombrada(Menu.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar (String menId, String menNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("menId", menId);
        parametros.put("menNombre", menNombre);
        return listarPorConsultaJpaNombrada(Menu.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }

    
}
