/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoAccion;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class CertificadoAccionDao extends Generico<CertificadoAccion> implements Serializable {

    public CertificadoAccion buscarDocumento(String documento) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", documento);
        try {
            return (CertificadoAccion) obtenerPorConsultaNativa("SELECT TOP 1 CertificadoAccion.*  FROM CertificadoAccion "
                    + " WHERE CtaNumeroDocumento = ? ", parametros, CertificadoAccion.class);
        } catch (Exception e) {
            return null;
        }

    }

    public CertificadoAccion obtenerPorNcertificadoEproceso(String cerNumero, String ctaEstadoProceso) throws ServicioExcepcion {
        Query q;
        try {
            q = getEntityManager().createNamedQuery(CertificadoAccion.OBTENER_POR_CERTIFICADO_FILTRADO);
            q.setParameter("ctaNumeroDocumento", "%" + cerNumero + "%");
            q.setParameter("ctaEstadoProceso", ctaEstadoProceso);
            q.setMaxResults(1);
            return (CertificadoAccion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public CertificadoAccion buscarDocumentoEstado(String documento, String estado) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", documento);
        parametros.put("2", estado);
        try {
            return (CertificadoAccion) obtenerPorConsultaNativa(" select top 1 CertificadoAccion.* from CertificadoAccion "
                    + " where CtaNumeroDocumento "
                    + " LIKE ? "
                    + "AND CtaEstadoProceso = ? ", parametros, CertificadoAccion.class);
        } catch (Exception e) {
            return null;
        }

    }

    public CertificadoAccion buscarCertificado(String documento) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", documento);

        try {
            return (CertificadoAccion) obtenerPorConsultaNativa(" select top 1 CertificadoAccion.* from CertificadoAccion "
                    + " where CtaNumeroDocumento "
                    + " LIKE ? ",
                    parametros, CertificadoAccion.class);
        } catch (Exception e) {
            return null;
        }

    }

    public List<CertificadoAccion> listarCertificado(String CtaNumeroDocumento) {
        String sql = "SELECT * "
                + "FROM "
                + "CertificadoAccion c WHERE CtaNumeroDocumento LIKE ? ";
        Query q = getEntityManager().createNativeQuery(sql, CertificadoAccion.class);
        q.setParameter("1", CtaNumeroDocumento);

        return q.getResultList();
    }

    public List<CertificadoAccion> listarCertificadoAccion(String CtaNumeroDocumento, String estado) {
        String sql = "SELECT * "
                + "FROM "
                + "CertificadoAccion c WHERE CtaNumeroDocumento LIKE ? AND CtaEstadoProceso = ? ";
        Query q = getEntityManager().createNativeQuery(sql, CertificadoAccion.class);
        q.setParameter("1", CtaNumeroDocumento);
        q.setParameter("2", estado);

        return q.getResultList();
    }

    public List<CertificadoAccion> listarPorNcertificadoEproceso(String cerNumero, String tmaEstadoProceso) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ctaNumeroDocumento", "%" + cerNumero + "%");
        parametros.put("ctaEstadoProceso", tmaEstadoProceso);
        return listarPorConsultaJpaNombrada(CertificadoAccion.LISTAR_POR_CERTIFICADO_FILTRADO, parametros);
    }

}
