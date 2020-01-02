/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.RepertorioDigital;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class RepertorioDigitalDao extends Generico<RepertorioDigital> implements Serializable {

    public List<RepertorioDigital> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(RepertorioDigital.LISTAR_TODO, null);
    }

    public List<RepertorioDigital> listarRepDigPorNTramite(int nTramite) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", nTramite);
        return listarPorConsultaNativa("SELECT RepertorioDigital.* FROM Repertorio "
                + "INNER JOIN RepertorioDigital ON RepertorioDigital.RepNumero = Repertorio.RepNumero "
                + "WHERE Repertorio.TraNumero = ?", parametros, RepertorioDigital.class);
    }
    
    public List<RepertorioDigital> listarRepDigPorNumRepertorio(Long numRepertorio){
        String sql="SELECT * FROM RepertorioDigital WHERE RepNumero= ? ";
        Query q=getEntityManager().createNativeQuery(sql, RepertorioDigital.class);
        q.setParameter("1", numRepertorio);
        return q.getResultList();
    }
}
