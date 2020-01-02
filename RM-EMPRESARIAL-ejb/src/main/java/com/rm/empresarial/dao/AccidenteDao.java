/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Accidente;
import com.rm.empresarial.modelo.ListaLinderoSuperficie;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Marco
 */
@LocalBean
@Stateless
public class AccidenteDao extends Generico<ListaLinderoSuperficie> implements Serializable {
       public List<Accidente> buscarAccidentes(String sql) throws ServicioExcepcion, ParseException {
    
       
        
         List<Accidente> lista = listarPorConsultaNativa(sql , null ,Accidente.class);
                 
         
        return lista; 
    }
  
    
}
