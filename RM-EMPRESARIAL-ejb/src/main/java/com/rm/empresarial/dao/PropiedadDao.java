/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.TramiteDetalle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class PropiedadDao extends Generico<Propiedad> implements Serializable {

    public List<Propiedad> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Propiedad.LISTAR_TODO, null);
    }
    
    public List<Propiedad> listarTodoPorEstadoPropiedad() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Propiedad.LISTAR_TODO_ACTIVO, null);
    }

    public Propiedad encontrarPropiedadPorId(String prdMatricula) throws ServicioExcepcion {
        Long id = new Long(prdMatricula);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("prdMatricula", prdMatricula);
        return obtenerPorConsultaJpaNombrada(Propiedad.LISTAR_PORID, parametros);
    }

    public Propiedad obtenerPorMatricula(String numMatricula) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("prdMatricula", numMatricula);
            return obtenerPorConsultaJpaNombrada(Propiedad.LISTAR_PORID, parametros);
        } catch (Exception e) {
            return null;
        }
    }

    public Propiedad buscarPropiedadPor_predio_o_catastro(String prdPredio, String prdCatastro) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT p FROM Propiedad p WHERE p.prdCatastro=:prdCatastro OR p.prdPredio=:prdPredio");
            q.setParameter("prdCatastro", prdCatastro);
            q.setParameter("prdPredio", prdPredio);

            return (Propiedad) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            Propiedad propiedad = new Propiedad();
            propiedad = null;
            return propiedad;
        }

    }
    
    public Propiedad buscarPropiedadPor_predio_o_catastroEstadoActivo(String prdPredio, String prdCatastro) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT p FROM Propiedad p WHERE (p.prdCatastro=:prdCatastro OR p.prdPredio=:prdPredio) AND p.prdEstadoRegistro = 'A'");
            q.setParameter("prdCatastro", prdCatastro);
            q.setParameter("prdPredio", prdPredio);

            return (Propiedad) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            Propiedad propiedad = new Propiedad();
            propiedad = null;
            return propiedad;
        }

    }
    
     public Propiedad buscarPropiedadPor_predio_Y_catastroEstadoActivo(String prdPredio, String prdCatastro) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT p FROM Propiedad p WHERE p.prdCatastro=:prdCatastro AND p.prdPredio=:prdPredio AND p.prdEstadoRegistro = 'A'");
            q.setParameter("prdCatastro", prdCatastro);
            q.setParameter("prdPredio", prdPredio);

            return (Propiedad) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            Propiedad propiedad = new Propiedad();
            propiedad = null;
            return propiedad;
        }

    }
     
      public Propiedad buscarPropiedadPor_predio_o_catastro_Estado(String prdPredio, String prdCatastro,String estadoRegistro) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT p FROM Propiedad p WHERE (p.prdCatastro=:prdCatastro OR p.prdPredio=:prdPredio) AND p.prdEstadoRegistro=:prdEstadoRegistro");
            q.setParameter("prdCatastro", prdCatastro);
            q.setParameter("prdPredio", prdPredio);
            q.setParameter("prdEstadoRegistro", estadoRegistro);

            return (Propiedad) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            Propiedad propiedad = new Propiedad();
            propiedad = null;
            return propiedad;
        }

    }
        public Propiedad buscarPropiedadPor_predio_Y_catastro_Estado(String prdPredio, String prdCatastro,String estadoRegistro) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT p FROM Propiedad p WHERE (p.prdCatastro=:prdCatastro AND p.prdPredio=:prdPredio) AND p.prdEstadoRegistro=:prdEstadoRegistro");
            q.setParameter("prdCatastro", prdCatastro);
            q.setParameter("prdPredio", prdPredio);
            q.setParameter("prdEstadoRegistro", estadoRegistro);

            return (Propiedad) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            Propiedad propiedad = new Propiedad();
            propiedad = null;
            return propiedad;
        }

    }
    

    public Propiedad buscarPropiedadEditarPor_predio_o_catastro(String prdPredio, String prdCatastro, String numMatricula) {
        Map<String, Object> parametros = new HashMap<>();
        String sql = "SELECT * FROM Propiedad WHERE (PrdCatastro = ? OR PrdPredio = ?)"
                + " AND PrdMatricula <> ?";
        parametros.put("1", prdCatastro);
        parametros.put("2", prdPredio);
        parametros.put("3", numMatricula);
        try {
            return (Propiedad) obtenerPorConsultaNativa(sql, parametros, Propiedad.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }

    }

    public Propiedad buscarPropiedadPor_predio_Y_catastro(String prdPredio, String prdCatastro) {
        Query q;
        q = getEntityManager().createQuery("SELECT p FROM Propiedad p WHERE p.prdCatastro=:prdCatastro AND p.prdPredio=:prdPredio");
        q.setParameter("prdCatastro", prdCatastro);
        q.setParameter("prdPredio", prdPredio);
        try {
            return (Propiedad) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }    

    public List<Propiedad> listarPropiedadPorMatricula(String matricula) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT * FROM Propiedad p "
                + " WHERE p.prdMatricula = :matricula");
        q.setParameter("matricula", matricula);
        return q.getResultList();

    }

    public List<Propiedad> listarPropiedad_Por_Propietario(Long perId) {
        Query q;
        String catastro = "0";
        String predio = "0";
        System.out.println("perId: "+perId);
        q = getEntityManager().createQuery("SELECT p.prdMatricula FROM Propietario p WHERE p.perId.perId = :perId AND (p.prdMatricula.prdCatastro <> :catastro AND p.prdMatricula.prdPredio <> :predio) AND p.pprEstado='A'");
        q.setParameter("perId", perId);
        q.setParameter("predio", predio);
        q.setParameter("catastro", catastro);

        return q.getResultList();
    }

    public List<Propiedad> listarPropiedad_ConMatricula_DistintaCero_Por_Propietario(Long perId) {
        Query q;
        q = getEntityManager().createQuery("SELECT p.prdMatricula FROM Propietario p "
                + " WHERE p.perId.perId = :perId AND p.prdMatricula.prdMatricula != '0' AND p.pprEstado = 'A'");
        q.setParameter("perId", perId);
        return q.getResultList();
    }

//      public List<Propiedad> listarPropiedad_ConMatricula_DistintaCero_Por_Propietario(Long perId)
//      {
//          Query q ;
//        q = getEntityManager().createQuery("SELECT p.prdMatricula FROM Propietario p "
//                + " WHERE p.perId.perId = :perId AND p.prdMatricula.prdMatricula != 0");
//        q.setParameter("perId", perId);
//        return q.getResultList();
//      }
    public List<Propiedad> listarPropiedadPorMatriculaPorIdentificacion(String identificacion) throws ServicioExcepcion {

        Query q;

        q = getEntityManager().createQuery("SELECT p FROM Propiedad p"
                + " INNER JOIN Propietario pr ON pr.prdMatricula.prdMatricula = p.prdMatricula"
                + " WHERE p.prdMatricula = pr.prdMatricula.prdMatricula AND"
                + " pr.pprPerIdentificacion = :identificacion");

        q.setParameter("identificacion", identificacion);

        return q.getResultList();

    }

    public List<Propiedad> listarPropiedadPorMatriculaPorTramitePorPredioPorCatastro(Long numTramite, Long idTipoTramite) {
        Query q;
        String sql = "SELECT Propiedad.PrdMatricula, Propiedad.PrdDescripcion2"
                + " FROM Propiedad WHERE Propiedad.PrdPredio IN ("
                + " SELECT TramiteValor.TraNumPredio FROM TramiteValor"
                + " WHERE TramiteValor.TraNumero = ? AND TramiteValor.TtrId = ?)"
                + " AND Propiedad.PrdCatastro IN(SELECT TramiteValor.TrvNumCatastro"
                + " FROM TramiteValor WHERE TramiteValor.TraNumero = ? "
                + " AND TramiteValor.TtrId = ?) AND Propiedad.PrdMatricula != '0'";
        q = getEntityManager().createNativeQuery(sql, Propiedad.class);
        q.setParameter("1", numTramite);
        q.setParameter("2", idTipoTramite);
        q.setParameter("3", numTramite);
        q.setParameter("4", idTipoTramite);
        return q.getResultList();
    }

    public boolean existeMatriculaConPredioCatastroDistintoCero(String numMatricula) {
        Query q;

        q = getEntityManager().createQuery("SELECT p FROM Propiedad p"
                + " WHERE p.prdMatricula = :numMatricula "
                + " AND p.prdPredio LIKE '0' AND p.prdCatastro LIKE '0'");

        q.setParameter("numMatricula", numMatricula);

        if (q.getResultList().size() >= 0) {
            return true;
        } else {
            return false;
        }

    }

    public List<Propiedad> listarPropiedadesPorLike(String numMatricula, String numRepertorio, String predio, String catastro) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numMatricula);
        parametros.put("2", catastro);
        parametros.put("3", predio);
        parametros.put("4", numRepertorio);
        String sql = "SELECT * "
                + "FROM "
                + "Propiedad AS p "
                + "INNER JOIN RepertorioPropiedad AS rp ON rp.PrdMatricula = p.PrdMatricula "
                + "INNER JOIN Repertorio AS r ON r.RepNumero = rp.RepNumero "
                + "INNER JOIN Propietario pr ON pr.PrdMatricula = p.PrdMatricula AND pr.PrdRepertorio = rp.RepNumero "
                + "WHERE "
                + "p.PrdMatricula LIKE ? OR "
                + "p.PrdCatastro LIKE ? OR "
                + "p.PrdPredio LIKE ? OR "
                + "rp.RepNumero LIKE ? "
                + "ORDER BY p.PrdMatricula";
        return listarPorConsultaNativa(sql, parametros, Propiedad.class);
    }

    public List<Integer> listarRepertorioPropiedadesPorLike(String numMatricula, String numRepertorio, String predio, String catastro) {
        String sql = "SELECT rp.RepNumero "
                + "FROM "
                + "Propiedad AS p "
                + "INNER JOIN RepertorioPropiedad AS rp ON rp.PrdMatricula = p.PrdMatricula "
                + "INNER JOIN Repertorio AS r ON r.RepNumero = rp.RepNumero "
                + "INNER JOIN Propietario pr ON pr.PrdMatricula = p.PrdMatricula AND pr.PrdRepertorio = rp.RepNumero "
                + "WHERE "
                + "p.PrdMatricula LIKE ? OR "
                + "p.PrdCatastro LIKE ? OR "
                + "p.PrdPredio LIKE ? OR "
                + "rp.RepNumero LIKE ? "
                + "ORDER BY p.PrdMatricula";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", numMatricula);
        q.setParameter("2", catastro);
        q.setParameter("3", predio);
        q.setParameter("4", numRepertorio);
        return q.getResultList();
    }

    public List<Propiedad> listarPorMatriculaPadre(String matriculaPadre) throws ServicioExcepcion {        
        String sql = "SELECT * FROM Propiedad WHERE PrdPadreMatricula = ? AND PrdEstadoRegistro = 'A'";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", matriculaPadre);

        return listarPorConsultaNativa(sql, parametros, Propiedad.class);
    }
     public List<Propiedad> listarPropiedadPorMatriculaPadre(String matriculaPadre) throws ServicioExcepcion {        
        String sql = "SELECT * FROM Propiedad WHERE PrdPadreMatricula = ?";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", matriculaPadre);

        return listarPorConsultaNativa(sql, parametros, Propiedad.class);
    }

    public List<Propiedad> listarPorMatriculaPadre_PrdAgregado(String matriculaPadre) throws ServicioExcepcion {
        String sql = "SELECT * FROM Propiedad WHERE PrdPadreMatricula = ? "
                + " AND PrdAgregado ='I' AND PrdEstadoRegistro = 'A'";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", matriculaPadre);

        return listarPorConsultaNativa(sql, parametros, Propiedad.class);
    }
    public List<Propiedad> listarPorMatriculaPadre_PrdAgregado_Bloque(String matriculaPadre, String bloqNombre) throws ServicioExcepcion {
        String sql = "SELECT * FROM Propiedad WHERE PrdPadreMatricula = ? "
                + " AND PrdAgregado ='I' AND PrdEstadoRegistro = 'A'"
                + " AND PrdBloque = ?";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", matriculaPadre);
        parametros.put("2", bloqNombre);

        return listarPorConsultaNativa(sql, parametros, Propiedad.class);
    }

    public List<String> listarTtrTipoRepertorioPropiedadesPorLike(String numMatricula, String numRepertorio, String predio, String catastro) {
        String sql = "SELECT r.RepTtrDescripcion "
                + "FROM "
                + "Propiedad AS p "
                + "INNER JOIN RepertorioPropiedad AS rp ON rp.PrdMatricula = p.PrdMatricula "
                + "INNER JOIN Repertorio AS r ON r.RepNumero = rp.RepNumero "
                + "INNER JOIN Propietario pr ON pr.PrdMatricula = p.PrdMatricula AND pr.PrdRepertorio = rp.RepNumero "
                + "WHERE "
                + "p.PrdMatricula LIKE ? OR "
                + "p.PrdCatastro LIKE ? OR "
                + "p.PrdPredio LIKE ? OR "
                + "rp.RepNumero LIKE ? "
                + "ORDER BY p.PrdMatricula";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", numMatricula);
        q.setParameter("2", catastro);
        q.setParameter("3", predio);
        q.setParameter("4", numRepertorio);
        return q.getResultList();
    }

    public Propiedad buscarPorMatriculaPadre_UnidViv(String numMatricula) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numMatricula);
        try {
            return (Propiedad) obtenerPorConsultaNativa("SELECT TOP 1 * FROM Propiedad "
                    + " WHERE PrdPadreMatricula = ? AND ISNUMERIC(PrdUnidadVivienda)=1 "
                    + " ORDER BY CAST(PrdUnidadVivienda AS INT) DESC", parametros, Propiedad.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public List<Propiedad> listarPropiedadesHijasPH_Por_Repertorio(Long repNumero) throws ServicioExcepcion {
        Query q;

        q = getEntityManager().createNativeQuery("SELECT * FROM Propiedad p WHERE p.PrdPadreMatricula "
                + " IN (SELECT rp.PrdMatricula FROM RepertorioPropiedad rp WHERE rp.RepNumero = ?) "
                + " AND p.prdPadreMatricula IN(SELECT Propiedad.PrdMatricula FROM Propiedad WHERE Propiedad.PrdPH = 'S' "
                + " AND Propiedad.prdPadreMatricula IS NULL)", Propiedad.class);
        q.setParameter("1", repNumero);
        return q.getResultList();
    }

    public List<Propiedad> listarPropiedadesVendidaPorMatriculaPadre_ConAlicuata(String numMatricula) throws ServicioExcepcion {
        Query q;
        String sql = "SELECT * FROM Propiedad INNER JOIN Alicuota"
                + " ON Alicuota.PrdMatricula = Propiedad.PrdMatricula "
                + " WHERE Propiedad.PrdPadreMatricula = ?"
                + " AND Propiedad.PrdVendio = 'S'";

        q = getEntityManager().createNativeQuery(sql, Propiedad.class);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }

    public List<Propiedad> listarPropiedadesVendidaPorMatriculaPadre(String numMatricula) throws ServicioExcepcion {
        Query q;
        String sql = "SELECT * FROM Propiedad "
                + " WHERE Propiedad.PrdPadreMatricula = ?"
                + " AND Propiedad.PrdVendio = 'S'";

        q = getEntityManager().createNativeQuery(sql, Propiedad.class);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }

    public List<Propiedad> listarPorConjuntoId(Long prdConjuntoId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", prdConjuntoId);
            return listarPorConsultaNativa("SELECT p.* FROM Propiedad p "
                    + "INNER JOIN Compania c ON p.PrdConjuntoId = c.ComId "
                    + "WHERE c.ComId = ? AND p.PrdPadreMatricula IS NULL", parametros, Propiedad.class);
        } catch (ServicioExcepcion ex) {
            return new ArrayList<>();
        }
    }

    public List<Propiedad> listarPorRepertorio(Long numRepertorio) {
        String sql = "SELECT * FROM Propiedad p "
                + "INNER JOIN RepertorioPropiedad rp ON rp.PrdMatricula = p.PrdMatricula "
                + "WHERE rp.RepNumero = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Propiedad.class);
        q.setParameter("1", numRepertorio);

        return q.getResultList();

    }
    
    public List<Propiedad> listarPor_predio_catastro_estadoA(String predio, String catastro){
        Query q;
        q=getEntityManager().createQuery("SELECT P FROM Propiedad P WHERE P.prdPredio =:predio OR P.prdCatastro=:catastro and P.prdEstadoRegistro='A'");
        q.setParameter("predio", predio);
        q.setParameter("catastro", catastro);
       return q.getResultList();
    }
    
}
