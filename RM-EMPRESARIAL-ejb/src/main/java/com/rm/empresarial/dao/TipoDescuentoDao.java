/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Ciudad;
import com.rm.empresarial.modelo.TipoDescuento;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author DESARROLLO
 */
@LocalBean
@Stateless
public class TipoDescuentoDao extends Generico<TipoDescuento> implements Serializable{
    
    public List<TipoDescuento> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoDescuento.LISTAR_TODOS, null);
    }
    
    public List<TipoDescuento> listarTodo_EstadoA() throws ServicioExcepcion {
        String sql = "SELECT * FROM TipoDescuento WHERE TpdEstado = 'A'";
        Map<String, Object> parametros = new HashMap<>();        
        return listarPorConsultaNativa(sql, null, TipoDescuento.class);
    }
    
    public TipoDescuento obtenerTipo100PorCiento()
    {
        try {
          Query q;
          BigDecimal tpdPorcentaje=new BigDecimal(100);
        q=getEntityManager().createQuery("Select td from TipoDescuento td where td.tpdPorcentaje= :tpdPorcentaje  and td.tpdEstado='A'");
        q.setParameter("tpdPorcentaje", tpdPorcentaje);
        q.setMaxResults(1);
        return (TipoDescuento)q.getSingleResult();  
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
        
    }
    
}
