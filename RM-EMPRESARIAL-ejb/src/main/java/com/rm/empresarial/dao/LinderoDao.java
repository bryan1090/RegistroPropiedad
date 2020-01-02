/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Lindero;
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
public class LinderoDao extends Generico<Lindero> implements Serializable {

    public List<Lindero> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Lindero.LISTAR_TODO, null);
    }

    public List<Lindero> listarPorNumMatricula(String prdMatricula) {
        Query q;
        q = getEntityManager().createQuery("SELECT l FROM Lindero l WHERE l.prdMatricula.prdMatricula=:prdMatricula ORDER BY l.prdMatricula ASC");
        q.setParameter("prdMatricula", prdMatricula);
        return q.getResultList();
    }

}
