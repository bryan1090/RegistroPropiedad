/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoPropiedad;
import com.rm.empresarial.modelo.TipoPropiedadClase;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.UnidMedida;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author DJimenez
 */
@LocalBean
@Stateless
public class NuevasMatriculasDao extends Generico<Propiedad> implements Serializable {

    public TipoPropiedad ObtenerTipoPropiedad(int tpdId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", tpdId);
            String sql = "Select * from TipoPropiedad where tpdId = ? ";
            return (TipoPropiedad) obtenerPorConsultaNativa(sql, parametros, TipoPropiedad.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Secuencia obtenerSecuencia() {
        try {
            String sql = "SELECT * FROM Secuencia WHERE Secuencia.SecCodigo = 'MAT'";
            Query q = getEntityManager().createNativeQuery(sql, Secuencia.class);
            return (Secuencia) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Parroquia> listaParroquia() {
        try {
            Map<String, Object> parametros = new HashMap<>();
            String sql = "SELECT "
                    + "p.ParNombre, "
                    + "p.ParId "
                    + "FROM Parroquia p";
            return listarPorConsultaNativa(sql, parametros, Parroquia.class);

        } catch (Exception e) {
            return null;
        }

    }

    public List<TipoPropiedad> listaTipoPropiedad() {
        try {
            Map<String, Object> parametros = new HashMap<>();
            String sql = "Select * from TipoPropiedad";
            return listarPorConsultaNativa(sql, parametros, TipoPropiedad.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<UnidMedida> listarUnidadMedidad() {
        try {
            Map<String, Object> parametros = new HashMap<>();
            String sql = "Select * from UnidMedida";
            return listarPorConsultaNativa(sql, parametros, UnidMedida.class);
        } catch (Exception e) {
            System.out.println(e);
            return null;            
        }
    }

    public List<TipoPropiedadClase> listarTipoPropiedadClase() {
        try {
            Map<String, Object> parametros = new HashMap<>();
            String sql = "Select * from TipoPropiedadClase";
            return listarPorConsultaNativa(sql, parametros, TipoPropiedadClase.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Lindero> listarLindero() {
        try {
            Map<String, Object> parametros = new HashMap<>();
            String sql = "SELECT * FROM Lindero ";
            return listarPorConsultaNativa(sql, parametros, Lindero.class);
        } catch (Exception e) {
            return null;
        }
    }

    public Lindero obtenerLinderoPorMatricula(int prdMatricula) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", prdMatricula);
            String sql = "SELECT * FROM Lindero WHERE Lindero.PrdMatricula = ? ";
            return (Lindero) obtenerPorConsultaNativa(sql, parametros, Lindero.class);
        } catch (Exception e) {
            return null;
        }
    }

    public TramiteDetalle obtenerPorRepertorio(int numRepertorio) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numRepertorio);
        String sql = "SELECT TOP(1) * FROM TramiteDetalle WHERE TdtNumeroRepertorio = ? ";
        Query q = getEntityManager().createNativeQuery(sql, TramiteDetalle.class);
        return (TramiteDetalle) q.getSingleResult();
    }

    public Propiedad obtenerPropiedad(String catastro, String predio) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", catastro);
        parametros.put("2", predio);
        String sql = "SELECT * FROM Propiedad "
                + "WHERE Propiedad.PrdCatastro= ? AND Propiedad.PrdPredio= ? ";
        return (Propiedad) obtenerPorConsultaNativa(sql, parametros, Propiedad.class);
    }

    public Propiedad listarPorNumCatastroPredioMatricula(Integer numDocumento) {
        String stringNumDocumento=numDocumento.toString();
        Query q;
        
        q = getEntityManager().createQuery("SELECT p FROM Propiedad p WHERE p.prdMatricula LIKE :numDocumento OR p.prdCatastro LIKE :stringNumDocumento OR p.prdPredio LIKE :stringNumDocumento");
        q.setParameter("numDocumento", numDocumento);
         q.setParameter("stringNumDocumento", stringNumDocumento);
        q.setMaxResults(1);
        return (Propiedad) q.getSingleResult();
    }

    public Long obtenerUltimo() {
        Query q;
        q = getEntityManager().createQuery("Select MAX(p.prdMatricula) FROM Propiedad p");
        Integer resultado = (Integer) q.getSingleResult();
        return resultado.longValue();
    }

    
    public boolean existePropiedadConCatastroPredio(String prdCatastro, String prdPredio )
    {
        Query q;
        q = getEntityManager().createQuery("Select p FROM Propiedad p WHERE p.prdCatastro=:prdCatastro OR p.prdPredio=:prdPredio");
        q.setParameter("prdCatastro", prdCatastro);
        q.setParameter("prdPredio", prdPredio);
        return q.getResultList().size()>0;
    }
    
    public Propiedad buscarPropiedadConCatastroPredio(String prdCatastro, String prdPredio )
    {
        try {
            Query q;
        q=getEntityManager().createQuery("Select p FROM Propiedad p WHERE p.prdCatastro=:prdCatastro OR p.prdPredio=:prdPredio");
        q.setParameter("prdCatastro", prdCatastro);
        q.setParameter("prdPredio", prdPredio);
        q.setMaxResults(1);
        return  (Propiedad) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        
    }
    public Propiedad buscarPropiedadConCatastro_Y_Predio(String prdCatastro, String prdPredio )
    {
        try {
            Query q;
        q=getEntityManager().createQuery("Select p FROM Propiedad p WHERE p.prdCatastro=:prdCatastro AND p.prdPredio=:prdPredio");
        q.setParameter("prdCatastro", prdCatastro);
        q.setParameter("prdPredio", prdPredio);
        q.setMaxResults(1);
        return  (Propiedad) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        
    }
    
    public List<TipoTramite> listarTipoTramite()
    {
        Query q;
        q=getEntityManager().createQuery("SELECT t FROM TipoTramite t");
        return q.getResultList();
    }
}
