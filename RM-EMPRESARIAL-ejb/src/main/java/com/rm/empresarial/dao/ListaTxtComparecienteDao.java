/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.ListaTxtCompareciente;
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
public class ListaTxtComparecienteDao extends Generico<ListaTxtCompareciente> implements Serializable{
    
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
    
    public List<ListaTxtCompareciente> listarCompareciente(Long traNumero, Long numRepertorio) throws ServicioExcepcion, ParseException {
         String sql = "SELECT * FROM ListaTxtCompareciente WHERE TraNumero = ? AND TdtEstado = 'A' AND TdtNumeroRepertorio = ?";
        Query q = getEntityManager().createNativeQuery(sql, ListaTxtCompareciente.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", numRepertorio);
        return  q.getResultList();       
        
    }
      public List<ListaTxtCompareciente> buscarAccidente(String sql) throws ServicioExcepcion, ParseException {
    
       
        
         List<ListaTxtCompareciente> lista = listarPorConsultaNativa(sql , null ,ListaTxtCompareciente.class);
                 
         
        return lista; 
    }
    
  
    
}
