/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Menu;
import com.rm.empresarial.modelo.Rol;
import com.rm.empresarial.modelo.RolMenu;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Usuario;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Marco
 */

@LocalBean
@Stateless
public class RolMenuDao extends Generico<RolMenu> implements Serializable{


    
    public List<RolMenu> ListarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(RolMenu.LISTAR_TODO, null);
        //
    }
    public Boolean existeRolMenu(Menu menuId, Rol rolId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();        
        parametros.put("MenId", menuId);
        parametros.put("RolId", rolId);
       

        try {
            obtenerPorConsultaJpaNombrada(RolMenu.BUSCAR_POR_IDIDMENU_POR_IDROL, parametros);
            return true;
        } catch (Exception e) {
            return false;
        }
      
    }
    
     public RolMenu listarPorId(Long id) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rolMenuId", id);
        return obtenerPorConsultaJpaNombrada(RolMenu.LISTAR_POR_ID, parametros);
    }
         
      public List<RolMenu> ObtenerAcceso(Long rolId, String menuId ) throws ServicioExcepcion {
        Map<String, Object> parametros=new HashMap<>();
         String sql ="SELECT COUNT(RolMenu.rolMenId"
                 + " INNER JOIN RolMenu ON RolMenu.menId = ?"
                 + " WHERE Menu.menLink = '/paginas/MANTENIMIENTOS/tipoTramiteCompareciente/List.xhtml'"
                 + " AND RolMenu.rolId = ?";       
        
        parametros.put("1", menuId);
        parametros.put("2", rolId);
        
        List<RolMenu> rolMenu = listarPorConsultaNativa(sql, parametros,RolMenu.class); 
        return rolMenu;
    }
    
}

    

