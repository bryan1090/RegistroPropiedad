/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.GravamenDetalle;
import com.rm.empresarial.modelo.LibroNegro;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.Propiedad;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class RepertorioDao extends Generico<Repertorio> implements Serializable {

    @EJB
    GravamenDao servicioGravamen;

    @EJB
    GravamenDetalleDao servicioGravamenDetalle;

    public Repertorio buscarUltimoPorRepertorioNumero() throws ServicioExcepcion {
        try {

            return obtenerPorConsultaJpaNombrada(Repertorio.BUSCAR_ULTIMO_POR_REP_NUMERO, null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Repertorio> listarPorNumTramite(Long traNumero) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        return listarPorConsultaJpaNombrada(Repertorio.LISTAR_POR_TRA_NUMERO, parametros);

    }

    public Repertorio encontrarRepertorioPorId(String repNumero) throws ServicioExcepcion {
        Long id = new Long(repNumero);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("repNumero", id);
        return obtenerPorConsultaJpaNombrada(Repertorio.LISTAR_PORID, parametros);
    }

    public List<Repertorio> listarPorFecha(Date fechaIni, Date fechaFin) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        List<Repertorio> lista = listarPorConsultaNativa("SELECT r.RepNumero,r.TraNumero,td.TdtTpcDescripcion,r.RepTtrDescripcion,r.RepFHR,tl.TplDescripcion,n.NotNotario\n"
                + "FROM Repertorio r\n"
                + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero\n"
                + "INNER JOIN TramiteDetalle td ON td.TraNumero = t.TraNumero\n"
                + "INNER JOIN TipoLibro tl ON tl.TplId = td.TdtTplId\n"
                + "INNER JOIN Notaria n ON n.NotId = t.TraNotaria\n"
                + "WHERE\n"
                + "CONVERT(DATE,r.RepFHR) >= ? AND CONVERT(DATE,r.RepFHR) <= ? AND td.TdtTtrId = r.RepTtrId ORDER BY r.RepNumero ASC", parametros, Repertorio.class);
        return lista;
    }
    
    public List<Repertorio> listarPorFechaRepertorio(Date fechaRepertorio) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaRepertorio);
        List<Repertorio> lista = listarPorConsultaNativa("SELECT * FROM Repertorio r"
                + " WHERE CONVERT(DATE,r.RepFHR) = ? ", parametros, Repertorio.class);
        return lista;
    } 

    public Repertorio RepertorioMenor(Date fechaIni, Date fechaFin) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        try {
            return (Repertorio) obtenerPorConsultaNativa("SELECT MIN(r.RepNumero) as RepNumero FROM Repertorio r "
                    + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero "
                    + "INNER JOIN TramiteDetalle td ON td.TraNumero = t.TraNumero "
                    + "INNER JOIN TipoLibro tl ON tl.TplId = td.TdtTplId "
                    + "INNER JOIN Notaria n ON n.NotId = t.TraNotaria "
                    + "WHERE "
                    + "CONVERT(DATE,r.RepFHR) >= ? AND CONVERT(DATE,r.RepFHR) <= ?", parametros, Repertorio.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public Repertorio encontrarRepertorio_PorNumero_Fecha(Long numRepertorio, Date fecha) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numRepertorio);
        parametros.put("2", fecha);
        try {
            return (Repertorio) obtenerPorConsultaNativa("SELECT * FROM Repertorio r "
                    + " WHERE r.RepNumero = ? AND "
                    + " CONVERT(DATE,r.RepFHR) = ? ", parametros, Repertorio.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public Repertorio RepertorioMayor(Date fechaIni, Date fechaFin) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        try {
            return (Repertorio) obtenerPorConsultaNativa("SELECT MAX(r.RepNumero) as RepNumero FROM Repertorio r "
                    + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero "
                    + "INNER JOIN TramiteDetalle td ON td.TraNumero = t.TraNumero "
                    + "INNER JOIN TipoLibro tl ON tl.TplId = td.TdtTplId "
                    + "INNER JOIN Notaria n ON n.NotId = t.TraNotaria "
                    + "WHERE "
                    + "CONVERT(DATE,r.RepFHR) >= ? AND CONVERT(DATE,r.RepFHR) <= ?", parametros, Repertorio.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public Repertorio RepertorioTotal(Date fechaIni, Date fechaFin) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        try {
            return (Repertorio) obtenerPorConsultaNativa("SELECT count(DISTINCT r.RepNumero) as RepNumero FROM Repertorio r\n"
                    + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero\n"
                    + "INNER JOIN TramiteDetalle td ON td.TraNumero = t.TraNumero\n"
                    + "INNER JOIN TipoLibro tl ON tl.TplId = td.TdtTplId\n"
                    + "INNER JOIN Notaria n ON n.NotId = t.TraNotaria\n"
                    + "WHERE\n"
                    + "CONVERT(DATE,r.RepFHR) >= ? AND CONVERT(DATE,r.RepFHR) <= ?", parametros, Repertorio.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public Repertorio encontrarPorTramite(Long traNumero) {
        Query q = getEntityManager().createNamedQuery(Repertorio.LISTAR_POR_TRA_NUMERO, Repertorio.class);
        q.setParameter("traNumero", traNumero);
        q.setMaxResults(1);
        try {
            return (Repertorio) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    
    
    public Repertorio encontrarRecientePorTramite(Long traNumero) {
        Query q = getEntityManager().createNamedQuery(Repertorio.ENCONTRAR_RECIENTE_POR_NUM_TRAMITE, Repertorio.class);
        q.setParameter("traNumero", traNumero);
        q.setMaxResults(1);
        try {
            return (Repertorio) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Repertorio encontrarPorTramiteyTipo(Tramite tramite, Long repTtrId) {
        Query q = getEntityManager().createNamedQuery(Repertorio.LISTAR_POR_TRA_NUMERO_TIPO, Repertorio.class);
        q.setParameter("traNumero", tramite);
        q.setParameter("repTtrId", repTtrId);
        q.setMaxResults(1);
        try {
            return (Repertorio) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Repertorio> listarPor_PropiedadEnGravamen_o_PersonaEnGravamenDetalle(List<Propiedad> listaPropiedades, Long perId) {
        try {
            String matriculas = "";
            int contador = 1;
            List<Repertorio> listaRepertorio = new ArrayList<>();
            for (Propiedad prpd : listaPropiedades) {
                matriculas += prpd.getPrdMatricula();
                if (contador != listaPropiedades.size()) {
                    matriculas += ",";
                }
                contador++;
            }
            if (!matriculas.isEmpty()) {
                List<Gravamen> listaGravamen = new ArrayList<>();
                listaGravamen = servicioGravamen.buscarPorMatriculas(matriculas);
                if (listaGravamen != null && listaGravamen.isEmpty()) {
                    for (Gravamen gravamen : listaGravamen) {
                        if (!listaRepertorio.contains(gravamen.getRepNumero())) {
                            listaRepertorio.add(gravamen.getRepNumero());
                        }

                    }
                    return listaRepertorio;
                } else {
                    List<GravamenDetalle> listaGravDet = servicioGravamenDetalle.listarPorIdPersona(perId);
                    if (listaGravDet != null && listaGravDet.isEmpty()) {
                        for (GravamenDetalle gravDeta : listaGravDet) {
                            if (!listaRepertorio.contains(gravDeta.getGraId().getRepNumero())) {
                                listaRepertorio.add(gravDeta.getGraId().getRepNumero());
                            }
                        }
                    }

                }
                return listaRepertorio;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    return new ArrayList<> ();

    }



}
