/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.Propiedad;
import java.io.Serializable;
import java.util.Date;
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
public class PropietarioDao extends Generico<Propietario> implements Serializable {

    public List<Propietario> buscarPor_PropiedadMatricula_Estado(String prdMatricula) {
        Query q;
        q = getEntityManager().createQuery("SELECT p FROM Propietario p WHERE p.prdMatricula.prdMatricula=:prdMatricula AND p.pprEstado='A'");
        q.setParameter("prdMatricula", prdMatricula);
        return q.getResultList();
    }

    public List<Propietario> buscarPor_PropiedadMatricula_EstadoActivo(String prdMatricula) {
        Query q;
        q = getEntityManager().createQuery("SELECT p FROM Propietario p WHERE p.prdMatricula.prdMatricula=:prdMatricula AND p.pprEstado='A'");
        q.setParameter("prdMatricula", prdMatricula);
        return q.getResultList();
    }

    public List<Propietario> buscarPor_Id_Persona_Por_Matricula(Long idPersona, String prdMatricula) {
        Query q;
        q = getEntityManager().createQuery("SELECT p FROM Propietario p WHERE p.prdMatricula.prdMatricula=:prdMatricula"
                + " AND p.perId.perId = :perId");
        q.setParameter("perId", idPersona);
        q.setParameter("prdMatricula", prdMatricula);
        return q.getResultList();
    }

    public Boolean existePropietarioPor_Persona_Matricula_Repertorio(Long idPersona, String prdMatricula, Long prdRepertorio) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT p FROM Propietario p WHERE p.prdMatricula.prdMatricula=:prdMatricula AND p.perId.perId = :perId AND p.prdRepertorio= :prdRepertorio AND p.pprEstado = 'A'");
            q.setParameter("perId", idPersona);
            q.setParameter("prdMatricula", prdMatricula);
            q.setParameter("prdRepertorio", prdRepertorio);
            List<Propietario> resultado = q.getResultList();
            return !resultado.isEmpty();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }

    }
    
    public Boolean existePropietarioPor_Persona_Matricula_Repertorio_Estado(Long idPersona, String prdMatricula, Long prdRepertorio, String estado) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT p FROM Propietario p WHERE p.prdMatricula.prdMatricula=:prdMatricula AND p.perId.perId = :perId AND p.prdRepertorio= :prdRepertorio AND p.pprEstado =:prdEstado");
            q.setParameter("perId", idPersona);
            q.setParameter("prdMatricula", prdMatricula);
            q.setParameter("prdRepertorio", prdRepertorio);
            q.setParameter("prdEstado", estado);
            List<Propietario> resultado = q.getResultList();
            return !resultado.isEmpty();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }

    }
    
     public Propietario buscarPropietarioPor_Persona_Matricula_Estado(Long idPersona, String prdMatricula, String estado) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT p FROM Propietario p WHERE p.prdMatricula.prdMatricula=:prdMatricula AND p.perId.perId = :perId AND p.pprEstado = :estado");
            q.setParameter("perId", idPersona);
            q.setParameter("prdMatricula", prdMatricula);
            q.setParameter("estado", estado);
            
            return (Propietario) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }

    }
     
    public Propietario buscarPropietarioPor_Persona_Matricula_Estado_Fideicomiso(Long idPersona, String prdMatricula, String estado, String fideicomiso) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT p FROM Propietario p WHERE p.prdMatricula.prdMatricula=:prdMatricula AND p.perId.perId = :perId AND p.pprEstado = :estado AND p.prdFideicomiso =:fideicomiso");
            q.setParameter("perId", idPersona);
            q.setParameter("prdMatricula", prdMatricula);
            q.setParameter("estado", estado);
            q.setParameter("fideicomiso", fideicomiso);
            
            return (Propietario) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }

    } 

    public List<Propietario> listarPor_Id_Persona(Long idPersona) {
        Query q;
        q = getEntityManager().createQuery("SELECT p FROM Propietario p WHERE p.perId.perId = :perId AND p.pprEstado = 'A'");
        q.setParameter("perId", idPersona);

        return q.getResultList();
    }

    public Propietario encontrarPropietarioPorIdPersona(Long idPersona) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("pprPerIdentificacion", idPersona);
        return obtenerPorConsultaJpaNombrada(Propietario.BUSCAR_POR_ID_PERSONA, parametros);
    }

    public Propietario buscarPropietarioPorIdPersona(String idPersona) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("pprPerIdentificacion", idPersona);
        return obtenerPorConsultaJpaNombrada(Propietario.BUSCAR_POR_ID_PERSONA, parametros);
    }

    public List<Propietario> listarPornumRepYPerId(Long perId, int numRepertorio) {
        String sql = "SELECT * FROM Propietario p "
                + "INNER JOIN Repertorio r ON r.RepNumero = p.PrdRepertorio "
                + "WHERE p.PerId= ? AND p.PrdRepertorio= ? "
                + "ORDER BY p.PrdMatricula";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        q.setParameter("1", perId);
        q.setParameter("2", numRepertorio);
        return q.getResultList();
    }

    public List<Propietario> listarPorPerId(Long perId) {
        String sql = "SELECT * FROM Propietario p "
                + "WHERE p.PerId= ? AND p.PprEstado = 'A' "
                + "ORDER BY p.PrdMatricula";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        q.setParameter("1", perId);
        return q.getResultList();
    }

    public List<Propietario> listarPorLikes(String identificacion, String apellidoPaterno, String apellidoMaterno, String nombre) {
        String sql = "SELECT * "
                + "FROM Propietario "
                + "WHERE PprPerIdentificacion LIKE ? OR "
                + "PprPerApellidoPaterno LIKE ? OR "
                + "PpPerApellidoMaterno LIKE ? OR "
                + "PprPerNombre LIKE ? ";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        q.setParameter("1", identificacion);
        q.setParameter("2", apellidoPaterno);
        q.setParameter("3", apellidoMaterno);
        q.setParameter("4", nombre);
        return q.getResultList();
    }

    public List<String> listarTipoRepPornumRepYPerId(Long perId) {
        String sql = "SELECT r.RepTtrDescripcion FROM Propietario p "
                + "INNER JOIN Repertorio r ON r.RepNumero = p.PrdRepertorio "
                + "WHERE p.PerId= ? "
                + "ORDER BY p.PrdMatricula";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", perId);
        return q.getResultList();
    }

    public List<Propietario> listarPorPerIdPorNumRepertorioPorNumMatricula(Long perId, int numRepertorio, String numMatricula) {
        String sql = "SELECT * FROM Propietario WHERE PerId= ? AND PrdRepertorio= ? AND PrdMatricula = ? AND PprEstado = 'A'";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        q.setParameter("1", perId);
        q.setParameter("2", numRepertorio);
        q.setParameter("3", numMatricula);
        return q.getResultList();
    }

    public List<Propietario> listarPornumRepYMatricula(String numMatricula, int numRepertorio) {
        String sql = "SELECT p.* "
                + "FROM "
                + "Propietario p "
                + "INNER JOIN Repertorio r ON r.RepNumero = p.PrdRepertorio "
                + "INNER JOIN TramiteDetalle AS td ON td.TraNumero = r.TraNumero AND td.PerId = p.PerId "
                + "WHERE "
                + "p.PrdMatricula = ? AND "
                + "p.PrdRepertorio = ? "
                + "ORDER BY td.TraNumero ";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        q.setParameter("1", numMatricula);
        q.setParameter("2", numRepertorio);
        return q.getResultList();
    }

    public List<Propietario> listarPornumMatricula(String numMatricula) {
        String sql = "SELECT p.* "
                + "FROM "
                + "Propietario p "
                + "INNER JOIN RepertorioPropiedad rp ON rp.PrdMatricula = p.PrdMatricula "
                + "INNER JOIN Repertorio r ON r.RepNumero = rp.RepNumero "
                + "INNER JOIN TramiteDetalle AS td ON td.TraNumero = r.TraNumero AND td.PerId = p.PerId "
                + "WHERE "
                + "p.PrdMatricula = ? "
                + "ORDER BY td.TraNumero ";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }

    public List<String> listarTraDescPornumRepYMatricula(String numMatricula, int numRepertorio) throws ServicioExcepcion {
        String sql = "SELECT td.TdtTpcDescripcion "
                + "FROM "
                + "Propietario p "
                + "INNER JOIN RepertorioPropiedad rp ON rp.PrdMatricula = p.PrdMatricula "
                + "INNER JOIN Repertorio r ON r.RepNumero = rp.RepNumero "
                + "INNER JOIN TramiteDetalle AS td ON td.TraNumero = r.TraNumero AND td.PerId = p.PerId "
                + "WHERE "
                + "p.PrdMatricula = ? AND "
                + "p.PrdRepertorio = ? "
                + "ORDER BY td.TraNumero ";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", numMatricula);
        q.setParameter("2", numRepertorio);
        return q.getResultList();
    }

    public List<String> listarContratoPornumRepYMatricula(String numMatricula, int numRepertorio) throws ServicioExcepcion {
        String sql = "SELECT td.TdtTtrDescripcion "
                + "FROM "
                + "Propietario p "
                + "INNER JOIN RepertorioPropiedad rp ON rp.PrdMatricula = p.PrdMatricula "
                + "INNER JOIN Repertorio r ON r.RepNumero = rp.RepNumero "
                + "INNER JOIN TramiteDetalle AS td ON td.TraNumero = r.TraNumero AND td.PerId = p.PerId "
                + "WHERE "
                + "p.PrdMatricula = ? AND "
                + "p.PrdRepertorio = ? "
                + "ORDER BY td.TraNumero ";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", numMatricula);
        q.setParameter("2", numRepertorio);
        return q.getResultList();
    }

    public List<String> listarTraDescPornumMatricula(String numMatricula) throws ServicioExcepcion {
        String sql = "SELECT td.TdtTpcDescripcion "
                + "FROM "
                + "Propietario p "
                + "INNER JOIN RepertorioPropiedad rp ON rp.PrdMatricula = p.PrdMatricula "
                + "INNER JOIN Repertorio r ON r.RepNumero = rp.RepNumero "
                + "INNER JOIN TramiteDetalle AS td ON td.TraNumero = r.TraNumero AND td.PerId = p.PerId "
                + "WHERE "
                + "p.PrdMatricula = ? "
                + "ORDER BY td.TraNumero ";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }

    public List<String> listarContratoPornumMatricula(String numMatricula) throws ServicioExcepcion {
        String sql = "SELECT td.TdtTtrDescripcion "
                + "FROM "
                + "Propietario p "
                + "INNER JOIN RepertorioPropiedad rp ON rp.PrdMatricula = p.PrdMatricula "
                + "INNER JOIN Repertorio r ON r.RepNumero = rp.RepNumero "
                + "INNER JOIN TramiteDetalle AS td ON td.TraNumero = r.TraNumero AND td.PerId = p.PerId "
                + "WHERE "
                + "p.PrdMatricula = ? "
                + "ORDER BY td.TraNumero ";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }

    public List<Propietario> listarEstado(int perId) throws ServicioExcepcion {
        String sql = "SELECT * FROM Propietario WHERE PerId= ? AND  PprEstado = 'A'";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        q.setParameter("1", perId);
        return q.getResultList();
    }

    public Propietario listarPorMatricula(int perId, String matricula) throws ServicioExcepcion {
        String sql = "SELECT * FROM Propietario WHERE PerId= ? AND  PrdMatricula = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        q.setParameter("1", perId);
        q.setParameter("2", matricula);
        return (Propietario) q.getSingleResult();
    }

    public List<Propietario> listarReporte(Date fechaIni, Date fechaFin) {
        String sql = "SELECT * "
                + "FROM "
                + "Acta a "
                + "INNER JOIN TramiteDetalle td ON td.TdtNumeroRepertorio = a.RepNumero "
                + "INNER JOIN Tramite t ON t.TraNumero = td.TraNumero "
                + "INNER JOIN TipoTramiteCompareciente ttc ON ttc.TtcId = td.TtcId "
                + "INNER JOIN TipoCompareciente tp ON tp.TpcId = ttc.TpcId "
                + "INNER JOIN Propietario p ON p.PerId = td.PerId "
                + "INNER JOIN Notaria n ON n.NotId = t.TraNotaria "
                + "WHERE  "
                + " ttc.TtcPropietario='N'  "
                + " AND p.PprEstado = 'A' AND CONVERT(DATE,a.ActFch) >= ? AND CONVERT(DATE,a.ActFch) <= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Propietario.class);
        q.setParameter("1", fechaIni);
        q.setParameter("2", fechaFin);
        return q.getResultList();
    }
    
    public List<Propiedad> listarPropiedadesPor_Persona(String perIdentificacion)
    {
        Query q;
        q=getEntityManager().createQuery("Select p from Propiedad p where p.prdMatricula in (Select Distinct(ptr.prdMatricula.prdMatricula) from Propietario ptr where ptr.perId.perIdentificacion = :perIdentificacion and ptr.pprEstado = 'A')");
        q.setParameter("perIdentificacion",perIdentificacion);
        return q.getResultList();
    }

}
