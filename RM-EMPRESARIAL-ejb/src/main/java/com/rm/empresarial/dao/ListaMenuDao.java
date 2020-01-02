/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.HostMail;
import com.rm.empresarial.modelo.LibroNegro;
import com.rm.empresarial.modelo.ListaMenu;
import com.rm.empresarial.modelo.Menu;
import com.rm.empresarial.modelo.TipoActa;
import com.rm.empresarial.modelo.Tramite;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Marco
 */
@LocalBean
@Stateless
public class ListaMenuDao extends Generico<ListaMenu> implements Serializable{    
    
        
      public List<ListaMenu> ObtenerListaMenu(Long rolId ) throws ServicioExcepcion {
        Map<String, Object> parametros=new HashMap<>();
         String sql ="SELECT ListaMenu.menId, ListaMenu.menPadre, ListaMenu.menNombre, ListaMenu.menLink, ListaMenu.menIcono,ListaMenu.nivel"
                 + " FROM ListaMenu WHERE ListaMenu.menId IN (SELECT RolMenu.menId FROM RolMenu WHERE RolMenu.rolId = ?)"
                 + " ORDER BY ListaMenu.MenAgrupa2";
        parametros.put("1", rolId);
        
        List<ListaMenu> listaMenu = listarPorConsultaNativa(sql, parametros,ListaMenu.class); 
        return listaMenu;
    }
  

}

    
