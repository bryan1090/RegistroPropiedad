/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Pais;

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
public class PaisDao extends Generico<Pais> implements Serializable {

    private static final long serialVersionUID = 2984506540292265944L;

    public List<Pais> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Pais.LISTAR_TODO, null);
    }
    //lista x id

    public Pais encontrarPaisPorId(String idPais) throws ServicioExcepcion {
        Long id = new Long(idPais);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("paiId", id);
        return obtenerPorConsultaJpaNombrada(Pais.LISTAR_PORID, parametros);
    }

    public void guardar(Pais pais) {
        guardar(pais);
    }

    public Pais asignarID() throws ServicioExcepcion {
        return obtenerPorConsultaJpaNombrada(Pais.ASIGNARID, null);
    }

    public Long asignarID2() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.paiId)+1 from Pais a");
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            return new Long(1);
        }
    }

    public List<Pais> listarSegunNumero(int number) {
        Query q;
        try {

            q = getEntityManager().createQuery("select a from Zona a");

            return q.setMaxResults(number).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public boolean validarNombreCrear(String paiNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("paiNombre", paiNombre);
        return listarPorConsultaJpaNombrada(Pais.BUSCAR_POR_NOM, parametros).isEmpty();
    }
    
    public boolean validarNombreEditar(Long paiId, String paiNombre) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("paiId", paiId);
        parametros.put("paiNombre", paiNombre);
        return listarPorConsultaJpaNombrada(Pais.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }
}
