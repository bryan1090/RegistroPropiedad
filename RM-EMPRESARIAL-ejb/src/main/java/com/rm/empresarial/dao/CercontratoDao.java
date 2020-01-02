/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Cercontrato;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author DJimenez
 */
@LocalBean
@Stateless
public class CercontratoDao extends Generico<Cercontrato> implements Serializable {

    public List<Cercontrato> listarCerContrato_Por_Crva(int crva) {
        String sql = "select * from cercontrato c,dinardap d "
                + "where c.cer_id=d.din_id and c.cer_crva= ? "
                + "order by c.cer_id desc";
        Query q = getEntityManager().createNativeQuery(sql, Cercontrato.class);
        q.setParameter("1", crva);
        return q.getResultList();
    }
}
