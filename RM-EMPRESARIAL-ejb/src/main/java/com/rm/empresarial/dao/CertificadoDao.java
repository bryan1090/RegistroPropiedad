/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.Razon;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;
import org.apache.commons.collections.map.HashedMap;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class CertificadoDao extends Generico<Certificado> implements Serializable {

    public Certificado obtenerPorCerNumero(String cerNumero) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cerNumero", "%" + cerNumero + "%");
        try {
            return obtenerPorConsultaJpaNombrada(Certificado.BUSCAR_POR_CER_NUM, parametros);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public List<Certificado> listarPorCerFHR(Date cerFHRI, Date cerFHRF) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", cerFHRI);
        parametros.put("2", cerFHRF);
        try {
            return listarPorConsultaNativa("SELECT Certificado.* FROM Certificado "
                    + "WHERE CONVERT(DATE, Certificado.cerFHR) >= ? "
                    + "AND CONVERT(DATE, Certificado.cerFHR) <= ?", parametros, Certificado.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public List<Certificado> listarPorCerNumero(String cerNumero) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cerNumero", cerNumero);
        try {
            return listarPorConsultaJpaNombrada(Certificado.BUSCAR_POR_CER_NUM, parametros);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public List<Certificado> listarPorCerNumeroLikeTop50(String cerNumero) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", "%" + cerNumero + "%");
        try {
            return listarPorConsultaNativa("SELECT TOP 50 Certificado.* "
                    + "FROM Certificado WHERE "
                    + "Certificado.cerNumero LIKE ?", parametros, Certificado.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public short obtenerSecuencialSiguiente(BigDecimal facId) {
        Query q;
        q = getEntityManager().createQuery("SELECT MAX(c.certificadoPK.cerSecuencial) FROM Certificado c WHERE c.facId.facId = :facId");
        q.setParameter("facId", facId);
        short resultado = (short) q.getSingleResult();
        if (resultado == 0) {
            return 1;
        }

        return (short) (resultado + 1);
    }

    public BigDecimal obtenerCuantiaPor_Propiedad(Integer prdMatricula) {
        Query q;
        q = getEntityManager().createNativeQuery("Select MAX(t.TrvValorBien) From TramiteValor t "
                + " Join Repertorio r on t.TraNumero =r.TraNumero"
                + " JOIN Acta a on a.RepNumero=r.RepNumero"
                + " JOIN Propiedad p on p.PrdMatricula=a.PrdMatricula"
                + " where p.PrdMatricula = ?");
        q.setParameter("1", prdMatricula);

        return (BigDecimal) q.getSingleResult();
    }

    public List<Certificado> listarPorPredio_O_Catastro(String predio, String catastro) {
        String sql = "SELECT * FROM Certificado WHERE CerPredio = ? or CerCatastro = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Certificado.class);
        q.setParameter("1", predio);
        q.setParameter("2", catastro);
        return q.getResultList();
    }

    public List<Certificado> listarPorPredio_Y_Catastro(String predio, String catastro) {
        String sql = "SELECT * FROM Certificado WHERE CerPredio = ? AND CerCatastro = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Certificado.class);
        q.setParameter("1", predio);
        q.setParameter("2", catastro);
        return q.getResultList();
    }

    public List<Certificado> listarPor_PropiedadCatastro_PropiedadPredio_OrdenadoPorFecha(String prdPredio, String prdCatastro) {
        if (prdPredio == null) {
            prdPredio = "0";
        }
        if (prdCatastro == null) {
            prdCatastro = "0";
        }

        Query q;
        q = getEntityManager().createQuery("SELECT c from Certificado c  WHERE (c.cerPredio =:prdPredio OR c.cerCatastro =:prdCatastro) ORDER BY c.cerFHR DESC");
        q.setParameter("prdPredio", prdPredio);
        q.setParameter("prdCatastro", prdCatastro);
        return q.getResultList();
    }

    public List<Certificado> listarPorFecha_Y_Usuario(String usuario, Date fecha) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuario);
        parametros.put("2", fecha);
        String sql = "SELECT * FROM Certificado c "
                + "INNER JOIN CertificadoTramite ct ON ct.CerNumero = c.CerNumero "
                + "INNER JOIN Tramite t ON t.TraNumero = ct.TraNumero "
                + "WHERE c.CerUsu = ? AND CONVERT(DATE,c.CerFHR) = ? AND t.TraEstado <> 'FIN'";
        return listarPorConsultaNativa(sql, parametros, Certificado.class);
    }

}
