/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TiempoProceso;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteDocumentoEntrega;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class TiempoProcesoDao extends Generico<TiempoProceso> implements Serializable {

    public List<TiempoProceso> listarTodo() {
        Query q;
        List<TiempoProceso> resultado = new ArrayList<>();
        q = getEntityManager().createNamedQuery("TiempoProceso.findAll");
        resultado = q.getResultList();
        return resultado;
    }

    public TiempoProceso buscarTiempoProcesoPor_Tramite(Long numTramite) {
        try {
            Query q;
            q = getEntityManager().createQuery("Select tde FROM TramiteDocumentoEntrega tde WHERE tde.traNumero.traNumero=:numTramite", TramiteDocumentoEntrega.class);
            q.setParameter("numTramite", numTramite);
            q.setMaxResults(1);
            TramiteDocumentoEntrega traDocEnt = (TramiteDocumentoEntrega) q.getSingleResult();
            if (traDocEnt != null) {
                Long numTipoTramite = traDocEnt.getTtrId().getTtrId();
                Query q2;
                q2 = getEntityManager().createQuery("Select tp FROM TiempoProceso tp WHERE tp.ttrId.ttrId= :numTipoTramite");
                q2.setParameter("numTipoTramite", numTipoTramite);
                q2.setMaxResults(1);
                return (TiempoProceso) q2.getSingleResult();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
    //para cajas
    public TiempoProceso buscarTiempoProcesoPor_Tramite2(Long numTramite) {
        try {
            Query q;
            q = getEntityManager().createQuery("Select tde FROM TramiteDetalle tde WHERE tde.traNumero.traNumero=:numTramite", TramiteDetalle.class);
            q.setParameter("numTramite", numTramite);
            q.setMaxResults(1);
            TramiteDetalle traDet = (TramiteDetalle) q.getSingleResult();
            if (traDet != null) {
                Long numTipoTramite = traDet.getTdtTtrId();
                Query q2;
                q2 = getEntityManager().createQuery("Select tp FROM TiempoProceso tp WHERE tp.ttrId.ttrId= :numTipoTramite");
                q2.setParameter("numTipoTramite", numTipoTramite);
                q2.setMaxResults(1);
                return (TiempoProceso) q2.getSingleResult();
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
}
