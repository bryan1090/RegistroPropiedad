/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.MarginacionR;
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
public class MarginacionRDao extends Generico<MarginacionR> implements Serializable {

    public List<MarginacionR> listarMarginacionR_Por_libro_Anio_Inscripcion(int libro, int anio, int inscripcion) {
        String sql = "select * "
                + "from marginacionR "
                + "where mar_libro= ? and mar_anio= ? and mar_inscripcion= ? ";
        Query q = getEntityManager().createNativeQuery(sql, MarginacionR.class);
        q.setParameter("1", libro);
        q.setParameter("2", anio);
        q.setParameter("3", inscripcion);
        return q.getResultList();
    }

}
