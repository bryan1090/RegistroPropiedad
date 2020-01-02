/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.ListaLinderoSuperficie;
import com.rm.empresarial.modelo.ListaLinderos;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Marco
 */

@LocalBean
@Stateless
public class ListaLinderoSuperficieDao extends Generico<ListaLinderoSuperficie> implements Serializable{
    
    public List<String> ObtenerDatosCompareciente( String sql) throws ServicioExcepcion, ParseException {
        try {
            Query query = getEntityManager().createNativeQuery(sql);
           
            List<String> resultado= new ArrayList<>();
            for (int i = 0; i < query.getResultList().size(); i++) {
                
                resultado.add(query.getResultList().get(i).toString());
            }
        
            return resultado;
            
        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }    
    }
    
     public List<ListaLinderoSuperficie> buscarLinderosSuperficie(String sql) throws ServicioExcepcion, ParseException {
    
       
        
         List<ListaLinderoSuperficie> lista = listarPorConsultaNativa(sql , null ,ListaLinderoSuperficie.class);
                 
         
        return lista; 
    }
  
    
    
}
