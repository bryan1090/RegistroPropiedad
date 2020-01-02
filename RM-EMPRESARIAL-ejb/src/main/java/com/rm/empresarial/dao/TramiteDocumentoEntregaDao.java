package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TiempoProceso;
import com.rm.empresarial.modelo.TramiteDocumentoEntrega;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author DJimenez
 */
@LocalBean
@Stateless
public class TramiteDocumentoEntregaDao extends Generico<TramiteDocumentoEntrega> implements Serializable {

    @EJB
    private TiempoProcesoDao servicioTiempoProceso;

    public TramiteDocumentoEntrega buscarPrimeroPor_Tramite(Long numTramite) {
        Query q;
        q = getEntityManager().createQuery("Select tde FROM TramiteDocumentoEntrega tde WHERE tde.traNumero.traNumero=:numTramite");
        q.setParameter("numTramite", numTramite);
        q.setMaxResults(1);
        return (TramiteDocumentoEntrega) q.getSingleResult();
    }

//    public TiempoProceso buscarTiempoProcesoPor_Tramite(Long numTramite) {
//        Query q;
//        q = getEntityManager().createQuery("Select tde FROM TramiteDocumentoEntrega tde WHERE tde.traNumero.traNumero=:numTramite and tde.ttrId.ttrId= :numTipoTramite", TramiteDocumentoEntrega.class);
//        q.setParameter("numTramite", numTramite);
//        q.setMaxResults(1);
//        TramiteDocumentoEntrega traDocEnt = (TramiteDocumentoEntrega) q.getSingleResult();
//        if (traDocEnt != null) {
//            Long numTipoTramite = traDocEnt.getTtrId().getTtrId();
//            Query q2;
//            q2 = getEntityManager().createQuery("Select tp FROM TiempoProceso tp WHERE tp.ttrId.ttrId= :numTipoTramite");
//            q2.setParameter("numTipoTramite", numTipoTramite);
//            q2.setMaxResults(1);
//            return (TiempoProceso) q2.getSingleResult();
//        } else {
//            return null;
//        }
//    }
}
