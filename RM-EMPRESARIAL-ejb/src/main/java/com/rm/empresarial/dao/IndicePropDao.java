/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.IndiceProp;
import java.io.Serializable;
import java.util.Date;
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
public class IndicePropDao extends Generico<IndiceProp> implements Serializable {

    public List<IndiceProp> listarIndiceProp_Like_Nombre(String nombre) {
        String sql = "select TOP(100) * "
                + "from indice_prop "
                + "where ind_nombre like ? "
                + "order by ind_nombre";
        Query q = getEntityManager().createNativeQuery(sql, IndiceProp.class);
        q.setParameter("1", nombre);
        return q.getResultList();
    }

    public List<IndiceProp> listarIndiceProp_Por_Fecha_Repertorio_Inscrpcion(Date fecha, int repertorio, int inscripcion) {
        String sql = "select TOP(100) * "
                + "from indice_prop "
                + "where CONVERT(DATE,ind_fecha)= ? and ind_repertorio= ? and ind_inscripcion= ? "
                + "order by ind_fecha,ind_repertorio,ind_inscripcion,ind_contratante";
        Query q = getEntityManager().createNativeQuery(sql, IndiceProp.class);
        q.setParameter("1", fecha);
        q.setParameter("2", repertorio);
        q.setParameter("3", inscripcion);
        return q.getResultList();
    }

    public List<IndiceProp> listarIndiceProp_Por_Crva(int crva) {
        String sql = "select TOP(100) * "
                + "from indice_prop "
                + "where ind_crva= ? "
                + "order by ind_crva";
        Query q = getEntityManager().createNativeQuery(sql, IndiceProp.class);
        q.setParameter("1", crva);
        return q.getResultList();
    }
}
