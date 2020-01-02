/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.MarginacionDetalle;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class MarginacionDetalleDao extends Generico<MarginacionDetalle> implements Serializable{
    
    public void listarTodo()
    {
        Query q;
        q=getEntityManager().createNamedQuery("MarginacionDetalle.findAll");
    }
    
    public List<MarginacionDetalle> listarPor_idMarginacion(Long mrgId)
    {
        Query q;
        q=getEntityManager().createQuery("SELECT md FROM MarginacionDetalle md WHERE md.mrgId.mrgId = :mrgId");
        q.setParameter("mrgId", mrgId);
        return q.getResultList();
    }
}
