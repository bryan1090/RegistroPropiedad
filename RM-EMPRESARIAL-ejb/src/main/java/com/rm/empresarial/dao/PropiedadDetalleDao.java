/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Cuantia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
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
public class PropiedadDetalleDao extends Generico<PropiedadDetalle> implements Serializable {

    public List<PropiedadDetalle> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada("PropiedadDetalle.findAll", null);
    }

    public List<PropiedadDetalle> listar_Por_Propiedad_Tipo(String prdMatricula, String tipo) {
        Query q;
        q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd WHERE PD.prdMatricula.prdMatricula =:prdMatricula AND pd.pdtTipo =:tipo");
        q.setParameter("prdMatricula", prdMatricula);
        q.setParameter("tipo", tipo);
        return q.getResultList();
    }

    public List<PropiedadDetalle> listar_Por_Propiedad_Tipo_Estado(String prdMatricula, String tipo) {
        Query q;
        q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                + " WHERE pd.prdMatricula.prdMatricula =:prdMatricula AND pd.pdtTipo =:tipo"
                + " AND pd.pdtEstado = 'A'");
        q.setParameter("prdMatricula", prdMatricula);
        q.setParameter("tipo", tipo);
//        List<PropiedadDetalle> resultado=q.getResultList();
//        for (PropiedadDetalle propDet : resultado) {
//            BigInteger idusuario=propDet.getPdtPerId();
//            Persona persona=getEntityManager().find(entityClass, propDet);
//            propDet.setPersona(persona);
//        }
        return q.getResultList();
    }

    public PropiedadDetalle obtener_Por_Propiedad_Tipo_Estado_Propiedad2(String prdMatricula, String tipo, String pdtPrdMatricula) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                    + " WHERE pd.prdMatricula.prdMatricula =:prdMatricula AND pd.pdtTipo =:tipo"
                    + " AND pd.pdtEstado = 'A'"
                    + " AND pd.pdtPrdMatricula =:pdtPrdMatricula");
            q.setParameter("prdMatricula", prdMatricula);
            q.setParameter("tipo", tipo);
            q.setParameter("pdtPrdMatricula", pdtPrdMatricula);
            q.setMaxResults(1);
            return (PropiedadDetalle) q.getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

    }

    public PropiedadDetalle obtener_Por_pdtPrdPropiedad_Tipo_Repertorio_EstadoActivo(String pdtPrdMMatricula, String tipo, Long numRepertorio) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                    + " WHERE pd.pdtPrdMatricula =:pdtPrdMatricula AND pd.pdtTipo =:tipo"
                    + " AND pd.pdtRepNumero =:pdtRepNumero AND pd.pdtEstado = 'A'");
            q.setParameter("pdtPrdMatricula", pdtPrdMMatricula);
            q.setParameter("tipo", tipo);
            q.setParameter("pdtRepNumero", numRepertorio);
            q.setMaxResults(1);
            return (PropiedadDetalle) q.getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

    }
    
     public PropiedadDetalle obtener_Por_PrdMatricula_Tipo_Repertorio_EstadoActivo(String prdMatricula, String tipo, Long numRepertorio) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                    + " WHERE pd.prdMatricula =:prdMatricula AND pd.pdtTipo =:tipo"
                    + " AND pd.pdtRepNumero =:pdtRepNumero AND pd.pdtEstado = 'A'");
            q.setParameter("prdMatricula", prdMatricula);
            q.setParameter("tipo", tipo);
            q.setParameter("pdtRepNumero", numRepertorio);
            q.setMaxResults(1);
            return (PropiedadDetalle) q.getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

    }

    public PropiedadDetalle obtener_Por_pdtPrdPropiedad_Tipo_EstadoActivo(String pdtPrdMMatricula, String tipo) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                    + " WHERE pd.pdtPrdMatricula =:pdtPrdMatricula AND pd.pdtTipo =:tipo"
                    + " AND pd.pdtEstado = 'A'");
            q.setParameter("pdtPrdMatricula", pdtPrdMMatricula);
            q.setParameter("tipo", tipo);
            q.setMaxResults(1);
            return (PropiedadDetalle) q.getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

    }
    
    public PropiedadDetalle obtener_Por_PrdMatricula_Tipo_EstadoActivo(String prdMatricula, String tipo) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                    + " WHERE pd.prdMatricula =:prdMatricula AND pd.pdtTipo =:tipo"
                    + " AND pd.pdtEstado = 'A'");
            q.setParameter("prdMatricula", prdMatricula);
            q.setParameter("tipo", tipo);
            q.setMaxResults(1);
            return (PropiedadDetalle) q.getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

    }
    public PropiedadDetalle obtener_Por_PrdMatricula_idPersona_Estado(String prdMatricula, BigInteger idPersona, String estado) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                    + " WHERE pd.prdMatricula.prdMatricula =:prdMatricula AND pd.pdtPerId =:idPersona "
                    + " AND pd.pdtEstado =:estado");
            q.setParameter("prdMatricula", prdMatricula);
            q.setParameter("idPersona", idPersona);
            q.setParameter("estado", estado);
            q.setMaxResults(1);
            return (PropiedadDetalle) q.getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

    }

    
    public PropiedadDetalle obtener_Por_PdtPrdMatricula_EstadoActivo(String pdtPrdMatricula, String estado) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                    + " WHERE pd.pdtPrdMatricula =:pdtPrdMatricula"
                    + " AND pd.pdtEstado =:estado");
            q.setParameter("pdtPrdMatricula", pdtPrdMatricula);
            q.setParameter("estado", estado);
            q.setMaxResults(1);
            return (PropiedadDetalle) q.getSingleResult();
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }

    }
    
    public List<PropiedadDetalle> listar_Por_Matricula(String prdMatricula) {
        Query q;
        q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                + " WHERE pd.prdMatricula.prdMatricula =:prdMatricula ");
        q.setParameter("prdMatricula", prdMatricula);

        return q.getResultList();
    }

    public List<PropiedadDetalle> listar_Por_Matricula_PorEstadoActivo(String prdMatricula) {
        Query q;
        q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd "
                + " WHERE pd.prdMatricula.prdMatricula =:prdMatricula AND pd.pdtEstado = 'A'");
        q.setParameter("prdMatricula", prdMatricula);

        return q.getResultList();
    }

    public String obtenerTipoOperacionPropiedadRealizada(String prdMatricula) {
        try {
            for (tipoOperacion tipo : tipoOperacion.values()) {
                Query q;
                q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd WHERE pd.prdMatricula.prdMatricula =:prdMatricula AND pd.pdtEstado = 'A' AND pd.pdtTipo= :tipo");
                q.setParameter("prdMatricula", prdMatricula);
                q.setParameter("tipo", tipo.name());
                if (!q.getResultList().isEmpty()) {
                    return tipo.name();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }
    public List<Propiedad> listarPor_Acta(Long actNumero)
    {
        Query q;
        q=getEntityManager().createQuery("Select pd from Propiedad pd where pd.prdMatricula IN(select rp.prdMatricula.prdMatricula from RepertorioPropiedad rp where rp.repNumero.repNumero IN(select a.repNumero.repNumero from Acta a where a.actNumero = :actNumero))");
        q.setParameter("actNumero",actNumero);
        return q.getResultList();
    }
    public List<PropiedadDetalle> listar_Por_Matricula_Estado_Tipo(String prdMatricula, String pdtTipo) {
        Query q;
        q = getEntityManager().createQuery("SELECT pd FROM PropiedadDetalle pd  WHERE pd.prdMatricula.prdMatricula =:prdMatricula AND pd.pdtEstado = 'A' and pd.pdtTipo = :pdtTipo");
        q.setParameter("prdMatricula", prdMatricula);
        q.setParameter("pdtTipo", pdtTipo);

        return q.getResultList();
    }
}

enum tipoOperacion {
    UNF, DIV, GDA;
}
