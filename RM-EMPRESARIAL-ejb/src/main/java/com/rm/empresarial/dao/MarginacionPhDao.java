/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.MarginacionPH;
import java.io.Serializable;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class MarginacionPhDao extends Generico<MarginacionPH> implements Serializable {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method
    public MarginacionPH obtenerPor_Propiedad_Repertorio(String matricula, Long numRepertorio) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT m FROM MarginacionPH m WHERE m.prdMatricula.prdMatricula = :matricula AND m.repNumero.repNumero = :numRepertorio");
            q.setParameter("matricula", matricula);
            q.setParameter("numRepertorio", numRepertorio);
            q.setMaxResults(1);
            return (MarginacionPH) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    

}
