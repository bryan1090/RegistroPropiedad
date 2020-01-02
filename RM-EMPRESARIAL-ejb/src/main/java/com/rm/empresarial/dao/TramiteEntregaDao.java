/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteEntrega;
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
public class TramiteEntregaDao extends Generico<TramiteEntrega> implements Serializable {

    public List<TramiteEntrega> listarPorNumTramite(Long traNumero) {
        String sql="SELECT * FROM TramiteEntrega WHERE TraNumero= ? ORDER BY DetId";
        Query q = getEntityManager().createNativeQuery(sql, TramiteEntrega.class);
        q.setParameter("1", traNumero);
        return q.getResultList();
    }

}
