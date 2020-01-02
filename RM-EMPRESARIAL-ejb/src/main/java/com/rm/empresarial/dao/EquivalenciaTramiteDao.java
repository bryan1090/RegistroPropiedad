/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.EquivalenciaTramite;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class EquivalenciaTramiteDao extends Generico<EquivalenciaTramite> implements Serializable{
    
    public EquivalenciaTramite buscarPorTipoTramiteId1_PorTipoTramiteId2(Long ttrId1, Long ttrId2){
        EquivalenciaTramite equivTram = new EquivalenciaTramite();
        String sql ="SELECT * FROM EquivalenciaTramite WHERE EvtTtrId1 = ? AND EvtTtrId2 = ?";
        
        Query q = getEntityManager().createNativeQuery(sql, EquivalenciaTramite.class);
        
        q.setParameter("1", ttrId1);
        q.setParameter("2", ttrId2);        
        try {
            return (EquivalenciaTramite) q.getSingleResult();
        } catch (Exception e) {
            return equivTram = null;
        }

        
        
    }
    
}
