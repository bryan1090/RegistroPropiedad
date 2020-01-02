/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.FormatoTramite;
import com.rm.empresarial.modelo.TramiteDetalle;
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
public class FormatoTramiteDao extends Generico<FormatoTramite> implements Serializable {
    
    public List<FormatoTramite> buscarPorIdTramite(Long tramiteId) throws ServicioExcepcion {
        
        String sql = "SELECT * FROM FormatoTramite WHERE TtrId = ?";
        Query q = getEntityManager().createNativeQuery(sql, FormatoTramite.class);
        q.setParameter("1", tramiteId);

        return q.getResultList();
        
//        Query q ;
//        
//        q = getEntityManager().createQuery("SELECT ft FROM FormatoTramite ft WHERE ft.ttrId.ttrId = :tramiteId");
//        
//        q.setParameter("tramiteId", tramiteId);        
//
//        return q.getResultList();

    }
    
     public List<FormatoTramite> buscarFormatoTramite(Long tramiteId, String fmtIdCuadro) throws ServicioExcepcion {
        
        Query q ;
        
        q = getEntityManager().createQuery("SELECT ft FROM FormatoTramite ft WHERE ft.ttrId.ttrId = :tramiteId AND ft.fmtIdCuadro = :fmtIdCuadro ORDER BY ft.fmtLinea");
        
        q.setParameter("tramiteId", tramiteId); 
        
        q.setParameter("fmtIdCuadro", fmtIdCuadro); 

        return q.getResultList();

    }
    
}
