/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.Repertorio;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Marco
 */
@LocalBean
@Stateless
public class RepertorioPropiedadDao extends Generico<RepertorioPropiedad> implements Serializable {

    public List<String> ObtenerPropiedadNumMatricula(String sql) throws ServicioExcepcion, ParseException {
        try {
            Query query = getEntityManager().createNativeQuery(sql);

            List<String> resultado = new ArrayList<>();
            for (int i = 0; i < query.getResultList().size(); i++) {

                resultado.add(query.getResultList().get(i).toString());
            }

            return resultado;

        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);
        }
    }

    public List<RepertorioPropiedad> buscarPorNumRepertorio(Long repNumero) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("1", repNumero);

        List<RepertorioPropiedad> lista = listarPorConsultaNativa("SELECT RepertorioPropiedad.* FROM RepertorioPropiedad"
                + " WHERE RepertorioPropiedad.RepNumero = ? ", parametros, RepertorioPropiedad.class);

        return lista;
    }

    public boolean existeRepertorioPropiedad(Long numRepertorio, String numMatricula) {
        Query q;
        q = getEntityManager().createQuery("SELECT r FROM RepertorioPropiedad r WHERE r.repNumero.repNumero=:numRepertorio AND r.prdMatricula.prdMatricula=:numMatricula");
        q.setParameter("numRepertorio", numRepertorio);
        q.setParameter("numMatricula", numMatricula);
        return !q.getResultList().isEmpty();
    }

    public List<RepertorioPropiedad> listarRepertorioPorNumMatricula(String numMatricula) {
        String sql = "SELECT * FROM RepertorioPropiedad WHERE PrdMatricula= ? ";
        Query q = getEntityManager().createNativeQuery(sql, RepertorioPropiedad.class);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }

    public List<RepertorioPropiedad> listarPorNumRepertorioPorNumMatricula(Long numRepertorio, String numMatricula) {
        String sql = "SELECT * FROM RepertorioPropiedad WHERE PrdMatricula= ? AND RepNumero = ?";
        Query q = getEntityManager().createNativeQuery(sql, RepertorioPropiedad.class);
        q.setParameter("1", numMatricula);
        q.setParameter("2", numRepertorio);
        return q.getResultList();
    }

    public Propiedad buscarPropiedad_Por_Repertorio(Long repNumero, Date fechaRepertorio) {
        try {

            Calendar fechaInicioHoy = Calendar.getInstance();
            Calendar fechaFinHoy = Calendar.getInstance();

            fechaInicioHoy.setTime(fechaRepertorio);
            fechaFinHoy.setTime(fechaRepertorio);

            fechaInicioHoy.set(Calendar.HOUR_OF_DAY, 0);
            fechaInicioHoy.set(Calendar.MINUTE, 0);
            fechaInicioHoy.set(Calendar.SECOND, 0);

            fechaFinHoy.set(Calendar.HOUR_OF_DAY, 23);
            fechaFinHoy.set(Calendar.MINUTE, 59);
            fechaFinHoy.set(Calendar.SECOND, 59);
            Query q;
            q = getEntityManager().createQuery("SELECT rp.prdMatricula FROM RepertorioPropiedad rp WHERE rp.repNumero.repNumero =:repNumero  AND (rp.repNumero.repFHR BETWEEN :fechaInicioHoy AND :fechaFinHoy)");
            q.setParameter("repNumero", repNumero);
            q.setParameter("fechaInicioHoy", fechaInicioHoy.getTime());
            q.setParameter("fechaFinHoy", fechaFinHoy.getTime());

            q.setMaxResults(1);
            return (Propiedad) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }

    }

    public List<Repertorio> listarRepertorioPor_Propiedad(String prdMatricula) {
        try {
            Query q;
            q = getEntityManager().createQuery("Select rp.repNumero from RepertorioPropiedad rp where rp.prdMatricula.prdMatricula =:prdMatricula");
            q.setParameter("prdMatricula",prdMatricula);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

}
