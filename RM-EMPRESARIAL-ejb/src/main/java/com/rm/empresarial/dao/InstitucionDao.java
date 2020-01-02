/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Canton;
import com.rm.empresarial.modelo.Ciudad;
import com.rm.empresarial.modelo.Institucion;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class InstitucionDao extends Generico<Institucion> implements Serializable {

    @EJB
    private CiudadDao ciudadDao;

    private static final long serialVersionUID = 8961299416241756838L;

    public List<Institucion> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Institucion.LISTAR_TODO, null);
    }

    public Institucion obtenerInstitucion() throws ServicioExcepcion {
        return obtenerPorConsultaJpaNombrada(Institucion.LISTAR_TODO, null);
    }

    public Institucion obtenerUltimoId() {
        try {
            Query q;

            q = getEntityManager().createQuery("SELECT i FROM Institucion i ORDER BY i.insId DESC");
            q.setMaxResults(1);
            return (Institucion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public Institucion encontrarInstitucionPorId(String idInstitucion) {
        try {
            Long id = new Long(idInstitucion);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("insId", id);
            return obtenerPorConsultaJpaNombrada(Institucion.LISTAR_PORID, parametros);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public BigDecimal porcentajeIva() {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("SELECT TOP 1 Institucion.InsIva FROM Institucion");

            BigDecimal CuaValorAplica = new BigDecimal(q.getSingleResult().toString());
            return CuaValorAplica;

        } catch (Exception e) {
            return BigDecimal.valueOf(0);
        }
    }

    public Ciudad encontrarCiudad() {
        try {
            Query q;
            q = getEntityManager().createNamedQuery("Institucion.findAll");
            q.setMaxResults(1);
            Institucion ins = (Institucion) q.getSingleResult();
            Long idCanton = ins.getInsCanId();
            Ciudad ciudad = ciudadDao.encontrarPorCantonId(idCanton);
            return ciudad;
//        q=getEntityManager().createNamedQuery("Ciudad.findByCiuId");
//        q.setParameter("ciuId", idCanton);
//        q.setMaxResults(1);
//        return (Ciudad) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public String encontrarCanton() {
        Query q;
        q = getEntityManager().createNamedQuery("Institucion.findAll");
        q.setMaxResults(1);
        Institucion ins = (Institucion) q.getSingleResult();
        Long idCanton = ins.getInsCanId();
        q = getEntityManager().createNamedQuery("Canton.findByCanId");
        q.setParameter("canId", idCanton);
        q.setMaxResults(1);
        Canton canton = (Canton) q.getSingleResult();
        return canton.getCanNombre();
    }
}
