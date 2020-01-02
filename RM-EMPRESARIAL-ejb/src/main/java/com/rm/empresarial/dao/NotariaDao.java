/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Ciudad;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Tramite;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless

public class NotariaDao extends Generico<Notaria> implements Serializable {

    private static final long serialVersionUID = 142280870625822323L;

    public List<Notaria> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Notaria.LISTAR_TODO, null);
    }

    public boolean validarNumNotariaCrear(short notNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("notNumero", notNumero);
        return listarPorConsultaJpaNombrada(Notaria.BUSCAR_POR_NUM, parametros).isEmpty();
    }

    public boolean validarNumNotariaEditar(Long notId, short notNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("notId", notId);
        parametros.put("notNumero", notNumero);
        return listarPorConsultaJpaNombrada(Notaria.BUSCAR_POR_NUM_EDITAR, parametros).isEmpty();
    }

    public Notaria listarNOtariaPorId(int notId) throws Exception {
        try {
            String sql = "SELECT top 1 * FROM Notaria WHERE NotId = ? ";
            Query q = getEntityManager().createNativeQuery(sql, Notaria.class);
            q.setParameter("1", notId);

            return (Notaria) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

//    public Notaria encontrarPorNumNotaria(short notNumero) {
//
//        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("notNumero", notNumero);
//        try {
//            return obtenerPorConsultaJpaNombrada(Notaria.BUSCAR_POR_NUM, parametros);
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//
//    }
    
    public Notaria encontrarPorNumNotariaTopUno(short notNumero) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT n FROM Notaria n WHERE n.notNumero = :notNumero");
            q.setParameter("notNumero", notNumero);            
            q.setMaxResults(1);
            return (Notaria) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);           
            return null;
        }

    }
    
     public Notaria encontrarPorNumNotaria(short notNumero, Ciudad ciudad) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT n FROM Notaria n WHERE n.notNumero = :notNumero AND n.ciuId = :ciuId");
            q.setParameter("notNumero", notNumero);
            q.setParameter("ciuId", ciudad);
            q.setMaxResults(1);
            return (Notaria) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);           
            return null;
        }

    }
    
    public Notaria encontrarPorNumNotaria_PorCiudadId(short notNumero, Ciudad ciudad) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("notNumero", notNumero);
        parametros.put("ciuId", ciudad);
        try {
            return obtenerPorConsultaJpaNombrada(Notaria.BUSCAR_POR_NUM_POR_ID_CIUDAD, parametros);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public List<Notaria> listarPorCiudad(Ciudad ciuId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("ciuId", ciuId);
            return listarPorConsultaJpaNombrada(Notaria.LISTAR_POR_CIUDAD, parametros);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Notaria> listarPorFecha(Date fechaIni, Date fechaFin) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        List<Notaria> lista = listarPorConsultaNativa("SELECT * \n"
                + "FROM Repertorio r\n"
                + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero\n"
                + "INNER JOIN TramiteDetalle td ON td.TraNumero = t.TraNumero\n"
                + "INNER JOIN TipoLibro tl ON tl.TplId = td.TdtTplId\n"
                + "INNER JOIN Notaria n ON n.NotId = t.TraNotaria\n"
                + "WHERE \n"
                + "CONVERT(DATE,r.RepFHR) >= ? AND CONVERT(DATE,r.RepFHR) <= ? AND td.TdtTtrId = r.RepTtrId ORDER BY r.RepNumero ASC", parametros, Notaria.class);
        return lista;
    }

    public List<Notaria> listarReporte(Date fechaIni, Date fechaFin) {
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
        Query q = getEntityManager().createNativeQuery(sql, Notaria.class);
        q.setParameter("1", fechaIni);
        q.setParameter("2", fechaFin);
        return q.getResultList();
    }
    
     public List<Notaria> tramitesUsu(Date fecha,int usu) {
        String sql = "SELECT *\n" +
"FROM Tramite\n" +
"INNER JOIN Notaria ON Notaria.NotId=Tramite.TraNotaria\n" +
"INNER JOIN Ciudad ON Ciudad.CiuId =Notaria.CiuId\n" +
"INNER JOIN TramiteResponsable ON TramiteResponsable.TraNumero=Tramite.TraNumero\n" +
"INNER JOIN Persona res ON res.PerId = TramiteResponsable.PerId\n" +
"INNER JOIN TipoTramite ON TipoTramite.TtrId = TramiteResponsable.TtrId\n" +
"INNER JOIN TramiteUsuario ON TramiteUsuario.TraNumero=Tramite.TraNumero\n" +
"WHERE CONVERT(DATE,TramiteUsuario.TusFHR)= ? AND TramiteUsuario.UsuId= ? \n" +
"ORDER BY TramiteUsuario.UsuId ";
        Query q = getEntityManager().createNativeQuery(sql, Notaria.class);
        q.setParameter("1", fecha);
        q.setParameter("2", usu);

        return q.getResultList();
    }

}
