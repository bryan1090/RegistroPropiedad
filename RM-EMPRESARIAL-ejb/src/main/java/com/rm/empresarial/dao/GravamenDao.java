/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.FormatoTramite;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.Propiedad;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class GravamenDao extends Generico<Gravamen> implements Serializable {

    public List<Gravamen> buscarPorMatricula(Propiedad matricula) {
        try {
            Query q;

            q = getEntityManager().createQuery("SELECT gr FROM Gravamen gr "
                    + " WHERE gr.prdMatricula = :matricula"
                    + " AND gr.graEstado = 'A'");

            q.setParameter("matricula", matricula);

            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }
    
    public List<Gravamen> buscarPorMatricula_EstadoA_EstadoR(Propiedad matricula) {
        try {
            Query q;

            q = getEntityManager().createQuery("SELECT gr FROM Gravamen gr "
                    + " WHERE gr.prdMatricula = :matricula"
                    + " AND (gr.graEstado = 'A' OR gr.graEstado = 'R')");

            q.setParameter("matricula", matricula);

            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    public List<Gravamen> buscarPorMatriculaYRepertorio(int numRepertorio, String numMatricula) {
        String sql = "SELECT * FROM Gravamen WHERE RepNumero= ? AND PrdMatricula= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        q.setParameter("1", numRepertorio);
        q.setParameter("2", numMatricula);

        return q.getResultList();

    }
    
    public List<Gravamen> buscarporMatricula(String numMatricula) {
        String sql = "SELECT * FROM Gravamen WHERE PrdMatricula= ? AND GraEstado='A' ";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        q.setParameter("1", numMatricula);

        return q.getResultList();
    }

    public List<Gravamen> buscarPorRepertorio(Long numRepertorio) {
        String sql = "SELECT * FROM Gravamen g "
                + "INNER JOIN Repertorio r ON r.RepNumero = g.RepNumero "
                + "WHERE g.RepNumero= ? ORDER BY g.PrdMatricula";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        q.setParameter("1", numRepertorio);

        return q.getResultList();

    }

    public List<String> buscarTipRepPorRepertorio(int numRepertorio) {
        String sql = "SELECT r.RepTtrDescripcion FROM Gravamen g "
                + "INNER JOIN Repertorio r ON r.RepNumero = g.RepNumero "
                + "WHERE g.RepNumero= ? ORDER BY g.PrdMatricula ";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", numRepertorio);

        return q.getResultList();

    }

    public List<Gravamen> buscarPorMatriculaYRepertorioYEstado(int numRepertorio, String numMatricula, String estado) {
        String sql = "SELECT * FROM Gravamen WHERE RepNumero= ? AND PrdMatricula= ? AND GraEstado = ?";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        q.setParameter("1", numRepertorio);
        q.setParameter("2", numMatricula);
        q.setParameter("3", estado);

        return q.getResultList();

    }

    public Gravamen buscarTopPorMatriculaYRepertorioYEstado(int numRepertorio, String numMatricula, String estado) {
        String sql = "SELECT TOP 1 * FROM Gravamen WHERE RepNumero= ? AND PrdMatricula= ? AND GraEstado = ?"
                + " ORDER BY GraId DESC";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        q.setParameter("1", numRepertorio);
        q.setParameter("2", numMatricula);
        q.setParameter("3", estado);

        try {
            return (Gravamen) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
        

    }

    public Gravamen buscarPorRepertorioYEstado(int numRepertorio, String estado) {
        String sql = "SELECT TOP 1 * FROM Gravamen WHERE RepNumero= ? AND AND GraEstado = ?"
                + " ORDER BY GraId DESC";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        q.setParameter("1", numRepertorio);
        q.setParameter("2", estado);

        return (Gravamen) q.getSingleResult();

    }
    
     public Gravamen buscarGrav_PorIdPersona_Estado(Long idPersona, String estado) {
        String sql = "SELECT * FROM Gravamen"
                + " INNER JOIN GravamenDetalle ON GravamenDetalle.GraId = Gravamen.GraId"
                + " WHERE GravamenDetalle.PerId = ? AND GravamenDetalle.GvdEstado = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        
        q.setParameter("1", idPersona);
        q.setParameter("2", estado);

        try {
            return (Gravamen) q.getSingleResult();
        } catch (Exception e) {            
            return null;
        }

    }
     
     public Gravamen buscarGrav_PorIdPersona_EstadoA_EstadoP(Long idPersona) {
        String sql = "SELECT * FROM Gravamen"
                + " INNER JOIN GravamenDetalle ON GravamenDetalle.GraId = Gravamen.GraId"
                + " WHERE GravamenDetalle.PerId = ? AND (GravamenDetalle.GvdEstado = 'A' OR GravamenDetalle.GvdEstado = 'P') ";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        
        q.setParameter("1", idPersona);        

        try {
            return (Gravamen) q.getSingleResult();
        } catch (Exception e) {            
            return null;
        }

    }   
     
     
      public Gravamen buscarGrav_PorNumMatricula_EstadoR_EstadoP(String numMatricula) {
        String sql = "SELECT * FROM Gravamen"
                + " WHERE Gravamen.PrdMatricula = ? AND (GravamenDetalle.GvdEstado = 'R' OR GravamenDetalle.GvdEstado = 'P') ";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        
        q.setParameter("1", numMatricula);        

        try {
            return (Gravamen) q.getSingleResult();
        } catch (Exception e) {            
            return null;
        }

    }  
          
    
      public Gravamen buscarGrav_PorRepertorio_IdPersona_Estado(Long numRepertorio, Long idPersona, String estado) {
        String sql = "SELECT * FROM Gravamen"
                + " INNER JOIN GravamenDetalle ON GravamenDetalle.GraId = Gravamen.GraId"
                + " WHERE Gravamen.RepNumero = ? AND"
                + " GravamenDetalle.PerId = ? AND GravamenDetalle.GvdEstado = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        q.setParameter("1", numRepertorio);
        q.setParameter("2", idPersona);
        q.setParameter("3", estado);

        try {
            return (Gravamen) q.getSingleResult();
        } catch (Exception e) {            
            return null;
        }

    }
   

    public int buscarPorRepertorio_IdPersona_Estado(Long numRepertorio, Long idPersona, String estado) {
        String sql = "SELECT * FROM Gravamen"
                + " INNER JOIN GravamenDetalle ON GravamenDetalle.GraId = Gravamen.GraId"
                + " WHERE Gravamen.RepNumero = ? AND"
                + " GravamenDetalle.PerId = ? AND GravamenDetalle.GvdEstado = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Gravamen.class);
        q.setParameter("1", numRepertorio);
        q.setParameter("2", idPersona);
        q.setParameter("3", estado);

        try {
            return (int) q.getResultList().size();

        } catch (Exception e) {
            System.out.println("exc sql " + e);
            return 0;
        }

    }

    public int actualizarEstadoPorNumRepPorNumMat(Long numRepertorio, String numMatricula, String usuarioLogeado) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("UPDATE Gravamen g SET g.graEstado='I' "
                + " WHERE g.repNumero.repNumero=:numRepertorio "
                + " AND g.prdMatricula.prdMatricula = :numMatricula"
                + " AND g.graUser= :user"
                + " AND g.graEstado LIKE 'A'");
        q.setParameter("user", usuarioLogeado);
        q.setParameter("numMatricula", numMatricula);
        q.setParameter("numRepertorio", numRepertorio);
        return q.executeUpdate();
    }

    public String obtenerPorMatriculayRepertorio(String numMatricula, int numRepertorio) {
        try {
            String sql = "SELECT TOP 1 g.GraEstado FROM Gravamen g WHERE g.PrdMatricula= ? AND g.RepNumero= ? AND g.GraEstado = 'A' ";
            Query q = getEntityManager().createNativeQuery(sql);
            q.setParameter("1", numMatricula);
            q.setParameter("2", numRepertorio);
            return (String) q.getSingleResult();
        } catch (Exception e) {
            return "";
        }
    }
    
    public String obtenerPorMatricula(String numMatricula) {
        try {
            String sql = "SELECT TOP 1 g.GraEstado FROM Gravamen g WHERE g.PrdMatricula= ? AND g.GraEstado = 'A' ";
            Query q = getEntityManager().createNativeQuery(sql);
            q.setParameter("1", numMatricula);
            return (String) q.getSingleResult();
        } catch (Exception e) {
            return "";
        }
    }
    public List<Gravamen> listarPorMatriculaPorEstadoR_EstadoP(Propiedad matricula) {
        try {
            Query q;

            q = getEntityManager().createQuery("SELECT gr FROM Gravamen gr "
                    + " WHERE gr.prdMatricula = :matricula"
                    + " AND (gr.graEstado = 'R' OR gr.graEstado = 'P')");

            q.setParameter("matricula", matricula);

            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    public List<Gravamen> buscarPorMatriculaPorEstado(Propiedad matricula) {
        try {
            Query q;

            q = getEntityManager().createQuery("SELECT gr FROM Gravamen gr "
                    + " WHERE gr.prdMatricula = :matricula"
                    + " AND (gr.graEstado = 'A' OR gr.graEstado = 'P')");

            q.setParameter("matricula", matricula);

            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }
     public List<Gravamen> buscarPorMatriculas(String matriculas) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT gr FROM Gravamen gr WHERE gr.prdMatricula IN(:matriculas)  AND gr.graEstado = 'A'");
            q.setParameter("matriculas", matriculas);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

}
