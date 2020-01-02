/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.DocumentoEntrega;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class DocumentoEntregaDao extends Generico<DocumentoEntrega> implements Serializable{
    
    public List<DocumentoEntrega> listarTodo() throws ServicioExcepcion{
        return listarPorConsultaJpaNombrada(DocumentoEntrega.LISTAR_TODO, null);
    } 
    
    public Long asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(d.dteId) from DocumentoEntrega d");
            return ((Long) q.getSingleResult()) + 1;
        } catch (Exception e) {
            return new Long(1);
        }
    }
    
}
