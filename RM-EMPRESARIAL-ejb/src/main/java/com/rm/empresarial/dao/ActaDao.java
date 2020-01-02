/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.TramiteValor;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.eclipse.persistence.internal.jpa.EJBQueryImpl;
import org.eclipse.persistence.internal.jpa.JPAQuery;
import org.eclipse.persistence.jpa.JpaQuery;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class ActaDao extends Generico<Acta> implements Serializable {

    public List<Acta> listarTodo() {
        try {
            return listarPorConsultaJpaNombrada(Acta.LISTAR_TODO, null);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

    public Acta obtenerPorNActa(Long actNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", actNumero);
        try {
            return (Acta) obtenerPorConsultaNativa("SELECT DISTINCT Acta.* FROM Marginacion "
                    + "INNER JOIN Acta ON Marginacion.ActNumero = Acta.ActNumero "
                    + "WHERE Marginacion.ActNumero = ?", parametros, Acta.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public Acta buscarActaPorNumRepFechaRepTipoLibro(Long repNumero, Date fechaRepertorio,
            Long IDtipoLibro) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", repNumero);
        parametros.put("2", fechaRepertorio);
        parametros.put("3", IDtipoLibro);
        try {
            return (Acta) obtenerPorConsultaNativa("SELECT Acta.* FROM Acta"
                    + " INNER JOIN Repertorio ON Acta.RepNumero = Repertorio.RepNumero"
                    + " INNER JOIN  TipoLibro ON  Acta.TplId =  TipoLibro.TplId"
                    + " WHERE Acta.RepNumero = ? "
                    + " AND CONVERT(DATE, Repertorio.RepFHR) =  ? "
                    + " AND  TipoLibro.TplId = ?", parametros, Acta.class);

        } catch (Exception e) {
            return null;
        }

    }

    public Acta encontrarActaAnterior(Long IDtipoLibro) {
        String sql = "SELECT TOP 1 * FROM Acta WHERE TplId = ? ORDER BY ActFHR DESC";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", IDtipoLibro);
        try {
            return (Acta) obtenerPorConsultaNativa(sql, parametros, Acta.class);
        } catch (Exception e) {
            return null;
        }

    }

    public Acta buscarPor_Acta(Long numActa) {
        Query q;
        try {
            q = getEntityManager().createNamedQuery("Acta.findByActNumero");
            q.setParameter("actNumero", numActa);
            return (Acta) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Acta buscarActaPorNumRepertorio(Long repNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", repNumero);
        try {
            return (Acta) obtenerPorConsultaNativa("SELECT TOP 1 * FROM Acta"
                    + " WHERE Acta.RepNumero = ?", parametros, Acta.class);

        } catch (Exception e) {
            return null;

        }
    }

    /*
    public Acta buscarPorAnioYTipoLibroId(int anio, Long tplId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tplId);
        parametros.put("2", anio);
        
        return (Acta) obtenerPorConsultaNativa("SELECT Max(Acta.ActInscripcion) FROM Acta" 
                + " WHERE Acta.TplId = ? AND Acta.ActAnio = ?", parametros,Acta.class);
    }
     */
    //retorna el numero de inscripcion mas 1 (actIncripcion + 1).
    public BigInteger buscarPorAnioYTipoLibroId(int anio, Long tplId) {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("SELECT Max(Acta.ActInscripcion) FROM Acta WHERE Acta.TplId = ? AND Acta.ActAnio = ?");
            q.setParameter("1", tplId);
            q.setParameter("2", anio);
            BigInteger secuencial = new BigInteger(q.getSingleResult().toString());
            return secuencial.add(BigInteger.valueOf(1));

        } catch (Exception e) {
            System.out.println(e);
            return BigInteger.valueOf(1);

        }
    }

    public Long buscarTipoActa() {
        Query q;
        try {
            q = getEntityManager().createNativeQuery("SELECT Top 1(TipoActa.TpaId) FROM TipoActa");

            Long idTipoActa = new Long(q.getSingleResult().toString());
            return idTipoActa;

        } catch (Exception e) {

            return Long.valueOf(0);

        }

    }

    public List<Acta> buscarActaPorFechaYLibro(Date fecha, Long tplId) throws ServicioExcepcion, ParseException {

        Map<String, Object> parametros = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        fecha = df.parse(df.format(fecha));
        parametros.put("1", fecha);
        parametros.put("2", tplId);

        List<Acta> lista = listarPorConsultaNativa("SELECT Acta.* FROM Acta JOIN Repertorio "
                + "ON  Acta.RepNumero= Repertorio.RepNumero "
                + "WHERE CONVERT(DATE, Repertorio.RepFHR) = ? AND Acta.TplId =? "
                + "ORDER BY Repertorio.RepNumero ASC, Repertorio.RepFHR ASC", parametros, Acta.class);

        return lista;
    }

    public Integer obtenerUltimoNumInscripcion() {
        try {
            Query q;
            q = getEntityManager().createQuery("Select MAX(a.actInscripcion) FROM Acta a");
            Integer resultado = ((BigInteger) q.getSingleResult()).intValue();
            return resultado;
        } catch (Exception e) {

            e.printStackTrace(System.out);
            return 0;
        }
    }

    public Acta obtenerUltimoNumInsPorTplYAnio(Long tplId, int actAnio) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tplId);
        parametros.put("2", actAnio);
        try {
            return (Acta) obtenerPorConsultaNativa("SELECT TOP 1 Acta.* FROM Acta "
                    + "WHERE Acta.TplId = ? AND Acta.ActAnio = ? ORDER BY Acta.ActInscripcion DESC", parametros, Acta.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public List<Acta> listarPor_TipoLibro_Volumen(Long tplId, Long actVolumen) {
        Query q;
        q = getEntityManager().createQuery("SELECT a FROM Acta a WHERE a.tplId.tplId=:tplId AND a.actVolumen = :actVolumen");
        q.setParameter("tplId", tplId);
        q.setParameter("actVolumen", actVolumen);

        return q.getResultList();
    }

    public List<Acta> listarPorTipoLibPorTomoPorVolumenYfechIniFechFin(Long tplId, int actTomo, Long actVolumen, Date fechaInicio, Date fechaFin) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tplId);
        parametros.put("2", actTomo);
        parametros.put("3", actVolumen);
        parametros.put("4", fechaInicio);
        parametros.put("5", fechaFin);
        return listarPorConsultaNativa("SELECT Acta.* FROM Acta "
                + "WHERE Acta.tplId = ? AND Acta.ActTomo = ? AND Acta.ActVolumen = ? "
                + "AND CONVERT(DATE, Acta.ActFch) >= ? AND CONVERT(DATE, Acta.ActFch) <= ?", parametros, Acta.class);
    }

    public Acta buscarActaPorTipoLibro(Long tplId) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("1", tplId);

        return (Acta) obtenerPorConsultaNativa("SELECT TOP 1 Acta.* FROM Acta"
                + " WHERE Acta.TplId = ? "
                + " ORDER BY Acta.ActInscripcion ASC", parametros, Acta.class);

    }
//    public Acta buscarActaPorNumRepertorioPorMatriculaPorTipoLibroPorTipoContrato(Long tplId) throws ServicioExcepcion, ParseException {
//        Map<String, Object> parametros = new HashMap<>();
//
//        parametros.put("1", tplId);
//
//        return (Acta) obtenerPorConsultaNativa("SELECT TOP 1 Acta.* FROM Acta"
//                + " WHERE Acta.TplId = ? "
//                + " ORDER BY Acta.ActInscripcion ASC", parametros, Acta.class);
//
//    }

    public List<Acta> listarActaPor_PropiedadCatastro_PropiedadPredio_OrdenadoPorFecha(String prdPredio, String prdCatastro) {
        if (prdPredio == null) {
            prdPredio = "0";
        }
        if (prdCatastro == null) {
            prdCatastro = "0";
        }

        Query q;
        q = getEntityManager().createQuery("SELECT a from Acta a  WHERE (a.prdMatricula.prdPredio=:prdPredio OR a.prdMatricula.prdCatastro=:prdCatastro) AND a.actEstado='A' ORDER BY a.actFHR DESC");
        q.setParameter("prdPredio", prdPredio);
        q.setParameter("prdCatastro", prdCatastro);
        return q.getResultList();
    }

//    public List<Acta> listarActaPor_TipoLibroPropiedad_OrdenadoPorFecha(String descripcionTipoLibro) {
//        
//        Query q;
//        q = getEntityManager().createQuery("SELECT a from Acta a  WHERE a.tplId.tplDescripcion = :descripcionTipoLibro AND a.actEstado='A' ORDER BY a.actFHR DESC");
//        q.setParameter("descripcionTipoLibro", descripcionTipoLibro);
//        return q.getResultList();
//    }
    public List<Acta> listarActa(String descripcionTipoLibro) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT a from Acta a  WHERE (a.repNumero IN(SELECT rp FROM RepertorioPropiedad rp )) AND a.tplId.tplDescripcion = :descripcionTipoLibro AND a.actEstado='A' ORDER BY a.actFHR DESC");
            q.setParameter("descripcionTipoLibro", descripcionTipoLibro);
            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public List<Acta> listarActaPorNumRepertorio(int numRepertorio) {
        String sql = "SELECT * from Acta WHERE RepNumero= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Acta.class);
        q.setParameter("1", numRepertorio);
        return q.getResultList();
    }

    public List<Acta> listarActaPorFechaIngreso(Date fechaIni, Date fechaFin) {
        String sql = "Select * from Acta \n"
                + "INNER JOIN Repertorio ON Acta.RepNumero = Repertorio.RepNumero\n"
                + "INNER JOIN TipoTramite ON Repertorio.RepTtrId = TipoTramite.TtrId\n"
                + "INNER JOIN Propiedad on Propiedad.PrdMatricula = Acta.PrdMatricula\n"
                + "INNER JOIN TramiteValor on TramiteValor.TtrId = TipoTramite.TtrId\n"
                + "INNER JOIN TipoPropiedad on TipoPropiedad.TpdId = Propiedad.TpdId \n"
                + "INNER JOIN Parroquia on Propiedad.PrdTdtParId = Parroquia.ParId  \n"
                + "INNER JOIN Ciudad on Ciudad.CiuId = Parroquia.CiuId \n"
                + "INNER JOIN Canton on Canton.CanId = Ciudad.CanId \n"
                + "where CONVERT(DATE,Acta.ActFch) >= ? AND CONVERT(DATE,Acta.ActFch) <= ? AND TipoTramite.TrCodigoAgrupacion1='UAFE' AND Repertorio.TraNumero = TramiteValor.TraNumero ";
        Query q = getEntityManager().createNativeQuery(sql, Acta.class);
        q.setParameter("1", fechaIni);
        q.setParameter("2", fechaFin);
        return q.getResultList();
    }

    public List<Acta> listarActaPor_Compareciente_TipoLibroPropiedad(List<TramiteValor> listaTramValr, Integer tipoLibroPropietario) {
        List<Acta> resultado = new ArrayList<>();
        for (TramiteValor tramiteValor : listaTramValr) {
            Query q;
            System.out.println("tramiteValor.getTrvNumCatastro(): " + tramiteValor.getTrvNumCatastro());
            System.out.println("tramiteValor.getTraNumPredio(): " + tramiteValor.getTraNumPredio());
            System.out.println("tipoLibroPropietario: " + tipoLibroPropietario);

            q = getEntityManager().createNativeQuery("Select * from Acta \n"
                    + "Join TipoLibro on Acta.TplId = TipoLibro.TplId \n"
                    + "where Acta.RepNumero IN (SELECT RepertorioPropiedad.RepNumero FROM RepertorioPropiedad \n"
                    + "WHERE RepertorioPropiedad.PrdMatricula \n"
                    + "IN (SELECT Propiedad.PrdMatricula from Propiedad INNER JOIN Propietario on Propietario.PrdMatricula= Propiedad.PrdMatricula\n"
                    + "WHERE Propiedad.PrdCatastro='1' and Propiedad.PrdPredio='1' and Propietario.PprEstado='A'  )) \n"
                    + "AND TipoLibro.TplId IN(SELECT TipoLibro.TplId FROM TipoLibro where TipoLibro.TplPropietario='1') AND Acta.ActEstado='A'", Acta.class);
            q.setParameter("1", tramiteValor.getTrvNumCatastro());
            q.setParameter("2", tramiteValor.getTraNumPredio());
            q.setParameter("3", tipoLibroPropietario);
            List<Acta> listaActa = q.getResultList();
            if (listaActa != null) {
                resultado.addAll((listaActa));
            }
        }
        return resultado;
    }

    public Acta buscarActa_por_NumInscripcion_Fecha_TipoLibro(BigInteger inscripcion, Date fechaActa, Long idTipoLibro) {

        try {
            Query q;
            q = getEntityManager().createQuery("SELECT a from Acta a  WHERE a.actInscripcion= :inscripcion AND a.actFch= :fechaActa AND a.tplId.tplId= :idTipoLibro");
            q.setParameter("inscripcion", inscripcion);
            q.setParameter("fechaActa", fechaActa);
            q.setParameter("idTipoLibro", idTipoLibro);
            q.setMaxResults(1);
            return (Acta) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    public Acta obtenerActaPorNumeroActa(Long numActa) {
        String sql = "SELECT * FROM Acta WHERE ActNumero= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Acta.class);
        q.setParameter("1", numActa);
        try {
            return (Acta) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public List<Acta> listarActasPorRepertorioyNumMatricula(int numRepertorio, String numMatricula) {
        String sql = "SELECT * FROM Acta WHERE RepNumero= ? AND PrdMatricula= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Acta.class);
        q.setParameter("1", numRepertorio);
        q.setParameter("2", numMatricula);
        return q.getResultList();
    }

    public List<Acta> listarActasPorNumMatricula(String numMatricula) {
        String sql = "SELECT * FROM Acta WHERE PrdMatricula = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Acta.class);
        q.setParameter("1", numMatricula);
        return q.getResultList();
    }

    public List<Acta> listarActasPorNumRepertorio(Long numRepertorio) {
        String sql = "SELECT * FROM Acta WHERE RepNumero = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Acta.class);
        q.setParameter("1", numRepertorio);
        return q.getResultList();
    }

    public List<Acta> listarActaReporte(Date fechaIni, Date fechaFin) {
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
                + " AND p.PprEstado = 'A' AND CONVERT(DATE,a.ActFch) >= ? and CONVERT(DATE,a.ActFch) <= ?  ";
        Query q = getEntityManager().createNativeQuery(sql, Acta.class);
        q.setParameter("1", fechaIni);
        q.setParameter("2", fechaFin);
        return q.getResultList();
    }

    //*****CARGA DIFERIDA*****
    public int numActasFirmadasPorFecha(Date fechaInicio, Date fechaFin) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT COUNT(a.actNumero) from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.actFch <= :fechaFin AND a.actFch>= :fechaInicio");
            q.setParameter("fechaInicio", fechaInicio);
            q.setParameter("fechaFin", fechaFin);
            return ((Long) q.getSingleResult()).intValue();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return 0;
        }
    }

    public List<Acta> listarDiferidoPor_Fecha_SinFiltros(int start, int size,
            Date fechaInicio, Date fechaFin) {
        try {
            Query q = null;
            q = getEntityManager().createQuery("SELECT a from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.actFch <= :fechaFin AND a.actFch>= :fechaInicio");
            q.setParameter("fechaInicio", fechaInicio);
            q.setParameter("fechaFin", fechaFin);
            q.setFirstResult(start);
            q.setMaxResults(size);

            return q.getResultList();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public int numActasFirmadasPor_Fecha_Libro(Date fechaInicio, Date fechaFin, Long tplId) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT COUNT(a.actNumero) from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.actFch <= :fechaFin AND a.actFch>= :fechaInicio AND a.tplId.tplId= :tplId");
            q.setParameter("fechaInicio", fechaInicio);
            q.setParameter("fechaFin", fechaFin);
            q.setParameter("tplId", tplId);
            return ((Long) q.getSingleResult()).intValue();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return 0;
        }
    }

    public List<Acta> listarDiferidoPor_Fecha_Libro_SinFiltros(int start, int size,
            Date fechaInicio, Date fechaFin, Long tplId) {
        try {
            Query q = null;
            q = getEntityManager().createQuery("SELECT a from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.actFch <= :fechaFin AND a.actFch>= :fechaInicio AND a.tplId.tplId= :tplId");
            q.setParameter("fechaInicio", fechaInicio);
            q.setParameter("fechaFin", fechaFin);
            q.setParameter("tplId", tplId);

            q.setFirstResult(start);
            q.setMaxResults(size);

            return q.getResultList();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

//    public List<Acta> listarDiferidoPor_Fecha_ConFiltrosDtb(int start, int size,
//            Map<String, Object> filters, Date fechaInicio, Date fechaFin) {
//        try {
//
//            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//            CriteriaQuery<Acta> criteriaQuery = cb.createQuery(Acta.class);
//            Root<Acta> root = criteriaQuery.from(Acta.class);
//            CriteriaQuery<Acta> select = criteriaQuery.select(root);
//
////            Expression<Date> expr1 = root.get("actFch").as(Date.class);
////            Predicate p1 = cb.between(expr1, fechaInicio, fechaInicio);
////            Predicate p1 = cb.equals(cb);
////            criteriaQuery.where(p1);
////            if (filters != null && filters.size() > 0) {
////                for (Map.Entry<String, Object> entry : filters.entrySet()) {
////                    String field = entry.getKey();
////                    Object value = entry.getValue();
////                    if (value == null) {
////                        continue;
////                    }
////
////                    Expression<String> expr = root.get(field).as(String.class);
////                    Predicate p = cb.like(cb.lower(expr),
////                            "%" + value.toString().toLowerCase() + "%");
////                    predicates.add(p);
////                }
////                if (predicates.size() > 0) {
////                    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
////                }
////            }
//            TypedQuery<Acta> query = getEntityManager().createQuery(select);
////            String jpql =query.unwrap(JpaQuery.class).getDatabaseQuery().getJPQLString();;
////            String sql=query.unwrap(EJBQueryImpl.class).getDatabaseQuery().getSQLString();
//
////            System.out.println("sql: "+sql);
//            query.setFirstResult(start);
//            query.setMaxResults(size);
//            List<Acta> list = query.getResultList();
//            return list;
//
//        } catch (Exception e) {
//            System.out.println(e);
//            return null;
//        }
//    }
    public int numActasFirmadasPorTramite(Long numTramite) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT COUNT(a.actNumero) from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.repNumero.traNumero.traNumero= :numTramite");
            q.setParameter("numTramite", numTramite);
            return ((Long) q.getSingleResult()).intValue();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return 0;
        }
    }

    public List<Acta> listarDiferidoPor_Tramite_SinFiltros(int start, int size,
            Long numTramite) {
        try {
            Query q = null;
            q = getEntityManager().createQuery("SELECT a from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.repNumero.traNumero.traNumero= :numTramite");
            q.setParameter("numTramite", numTramite);
            q.setFirstResult(start);
            q.setMaxResults(size);

            return q.getResultList();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public Acta obtenerActaPor_Numero(int start, int size,
            Date fechaInicio, Date fechaFin) {
        try {
            Query q = null;
            q = getEntityManager().createNamedQuery("Acta.findByActNumero");
            q.setMaxResults(1);
            return (Acta) q.getSingleResult();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public int numActasFirmadasPor_Inscripcion_Libro(BigInteger actInscripcion, Long tplId) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT COUNT(a.actNumero) from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.actInscripcion= :actInscripcion AND a.tplId.tplId= :tplId");
            q.setParameter("actInscripcion", actInscripcion);
            q.setParameter("tplId", tplId);

            return ((Long) q.getSingleResult()).intValue();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return 0;
        }
    }

    public List<Acta> listarDiferidoPor_Inscripcion_Libro_SinFiltros(int start, int size,
            BigInteger actInscripcion, Long tplId) {
        try {
            Query q = null;
            q = getEntityManager().createQuery("SELECT a from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.actInscripcion= :actInscripcion AND a.tplId.tplId= :tplId");
            q.setParameter("actInscripcion", actInscripcion);
            q.setParameter("tplId", tplId);

            q.setFirstResult(start);
            q.setMaxResults(size);

            return q.getResultList();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public int numActasFirmadasPor_Inscripcion(BigInteger actInscripcion) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT COUNT(a.actNumero) from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.actInscripcion= :actInscripcion");
            q.setParameter("actInscripcion", actInscripcion);
            return ((Long) q.getSingleResult()).intValue();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            return 0;
        }
    }

    public List<Acta> listarDiferidoPor_Inscripcion_SinFiltros(int start, int size,
            BigInteger actInscripcion) {
        try {
            Query q = null;
            q = getEntityManager().createQuery("SELECT a from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.actInscripcion = :actInscripcion");
            q.setParameter("actInscripcion", actInscripcion);
            q.setFirstResult(start);
            q.setMaxResults(size);

            return q.getResultList();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }
    
    public List<Acta> buscarPor_Matricula(Propiedad prdMatricula) throws ServicioExcepcion
    {      
        
        String sql = "SELECT Acta.* FROM Repertorio INNER JOIN RepertorioPropiedad ON RepertorioPropiedad.RepNumero = Repertorio.RepNumero INNER JOIN Acta ON Acta.RepNumero = Repertorio.RepNumero INNER JOIN TipoLibro ON Acta.TplId = TipoLibro.TplId WHERE Acta.ActEstado = 'A' AND RepertorioPropiedad.PrdMatricula =  ? AND TipoLibro.TplPropietario = 1";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", prdMatricula.getPrdMatricula());
        
        return listarPorConsultaNativa(sql, parametros, Acta.class);       
               
    }

//    public int numRegistrosConFiltros(Map<String, Object> filters) {
//        try {
//            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
//            Root<Acta> root = criteriaQuery.from(Acta.class);
//            CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));
//
//            if (filters != null && filters.size() > 0) {
//                List<Predicate> predicates = new ArrayList<>();
//                for (Map.Entry<String, Object> entry : filters.entrySet()) {
//                    String field = entry.getKey();
//                    Object value = entry.getValue();
//                    if (value == null) {
//                        continue;
//                    }
//
//                    Expression<String> expr = root.get(field).as(String.class);
//                    Predicate p = cb.like(cb.lower(expr),
//                            "%" + value.toString().toLowerCase() + "%");
//                    predicates.add(p);
//                }
//                if (predicates.size() > 0) {
//                    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
//                }
//            }
//            Long count = getEntityManager().createQuery(select).getSingleResult();
//            return count.intValue();
//
//        } catch (Exception e) {
//            System.out.println(e);
//            return 0;
//        }
//
//    }
//    public List<Acta> listarActasFirmadasPorFecha(Date fechaInicio, Date fechaFin) {
//        try {
//            Query q;
//            q = getEntityManager().createQuery("SELECT a from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.actFch <= :fechaFin AND a.actFch>= :fechaInicio");
//            q.setParameter("fechaInicio", fechaInicio);
//            q.setParameter("fechaFin", fechaFin);
//            return q.getResultList();
//
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//            return null;
//        }
//    }
//    public List<Acta> listarActasFirmadasPorTramite(Long numTramite) {
//        try {
//            Query q;
//            q = getEntityManager().createQuery("SELECT a from Acta a  WHERE a.actFirmaDigital IS NOT NULL AND a.actFirmaDigital <>'' AND a.repNumero.traNumero.traNumero= :numTramite");
//            q.setParameter("numTramite", numTramite);
//            return q.getResultList();
//
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//            return null;
//        }
//    }
//    public int numTotalRegistros() {
//        Query q;
//        q = getEntityManager().createQuery("SELECT COUNT(ta.actNumero) FROM Acta ta");
//        return ((Long) q.getSingleResult()).intValue();
//    }
    //*****CARGA DIFERIDA*****//
}
