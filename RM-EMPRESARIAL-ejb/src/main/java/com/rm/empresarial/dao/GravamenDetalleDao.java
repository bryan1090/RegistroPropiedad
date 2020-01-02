/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.GravamenDetalle;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class GravamenDetalleDao extends Generico<GravamenDetalle> implements Serializable {

    public GravamenDetalle buscarPorIdPersona(Long idPersona) {
        GravamenDetalle gravDetalle = new GravamenDetalle();
        String sql = "SELECT TOP 1 * FROM GravamenDetalle WHERE PerId = ?";
        Query q = getEntityManager().createNativeQuery(sql, GravamenDetalle.class);
        q.setParameter("1", idPersona);

        try {
            return (GravamenDetalle) q.getSingleResult();
        } catch (Exception e) {
            return gravDetalle = null;
        }

    }
    
    public GravamenDetalle buscarPor_IdGravamne_IdPersona_Estado(Long idGravamen, Long idPersona, String estado) {
        GravamenDetalle gravDetalle = new GravamenDetalle();
        String sql = "SELECT TOP 1 * FROM GravamenDetalle WHERE "
                + " GraId = ? AND PerId = ? AND GvdEstado = ?";
        Query q = getEntityManager().createNativeQuery(sql, GravamenDetalle.class);
        q.setParameter("1", idGravamen);
        q.setParameter("2", idPersona);
        q.setParameter("3", estado);

        try {
            return (GravamenDetalle) q.getSingleResult();
        } catch (Exception e) {
            return gravDetalle = null;
        }

    }

    public String buscarPorPersona(Long idPersona) {
        String sql = "SELECT TOP 1 GvdEstado FROM GravamenDetalle WHERE PerId = ? AND GvdEstado = 'A'";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", idPersona);

        try {
            return (String) q.getSingleResult();
        } catch (Exception e) {
            return "";
        }

    }

    public List<GravamenDetalle> listarPorGravamenId_PorIdPersona(Long idPersona, Long IdGravamen) {
        String sql = "SELECT * FROM GravamenDetalle WHERE GraId = ? AND PerId = ?";
        Query q = getEntityManager().createNativeQuery(sql, GravamenDetalle.class);
        q.setParameter("1", IdGravamen);
        q.setParameter("2", idPersona);
        return q.getResultList();
    }

    public List<GravamenDetalle> listarPorGravamenId_PorIdPersona_PorEstado(Long idPersona, Long IdGravamen, String estado) {
        String sql = "SELECT * FROM GravamenDetalle WHERE GraId = ? AND PerId = ? AND GvdEstado = ?";
        Query q = getEntityManager().createNativeQuery(sql, GravamenDetalle.class);
        q.setParameter("1", IdGravamen);
        q.setParameter("2", idPersona);
        q.setParameter("3", estado);
        return q.getResultList();
    }

    public List<GravamenDetalle> listarPorGravamenId(Long IdGravamen) {
        String sql = "SELECT * FROM GravamenDetalle WHERE GraId = ? AND GvdEstado != 'I'";
        Query q = getEntityManager().createNativeQuery(sql, GravamenDetalle.class);
        q.setParameter("1", IdGravamen);

        return q.getResultList();
    }
    public List<GravamenDetalle> listarPorGravamenId_NoEstadoInactivo(Long IdGravamen) {
        String sql = "SELECT * FROM GravamenDetalle WHERE GraId = ? AND GvdEstado != 'I'";
        Query q = getEntityManager().createNativeQuery(sql, GravamenDetalle.class);
        q.setParameter("1", IdGravamen);
    

        return q.getResultList();
    }
    public List<GravamenDetalle> listarPorGravamenId_Estado(Long IdGravamen, String estado) {
        String sql = "SELECT * FROM GravamenDetalle WHERE GraId = ? AND GvdEstado = ?";
        Query q = getEntityManager().createNativeQuery(sql, GravamenDetalle.class);
        q.setParameter("1", IdGravamen);
        q.setParameter("2", estado);

        return q.getResultList();
    }
    
    public List<GravamenDetalle> listarPorGravamenId_PorIdPersona_PorEstadosAP(Long idPersona, Long IdGravamen){
        String sql = "SELECT * FROM GravamenDetalle WHERE GraId = ? AND PerId = ? AND (GvdEstado = 'A' OR GvdEstado = 'P')";
        Query q =  getEntityManager().createNativeQuery(sql, GravamenDetalle.class);
        q.setParameter("1", IdGravamen);
        q.setParameter("2", idPersona);        
        return q.getResultList();
    }
     public List<GravamenDetalle> listarPorIdPersona(Long idPersona){
        String sql = "SELECT * FROM GravamenDetalle WHERE PerId = ? AND GvdEstado = 'A' ";
        Query q =  getEntityManager().createNativeQuery(sql, GravamenDetalle.class);        
        q.setParameter("1", idPersona);        
        return q.getResultList();
    }

}
