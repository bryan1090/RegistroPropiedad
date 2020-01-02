/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.var;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class TramiteAccionDao extends Generico<TramiteAccion> implements Serializable {

    private static final long serialVersionUID = -7970485244487934971L;

    public void guardarTramiteAccion(TramiteAccion tramiteAccion) throws ServicioExcepcion {
        guardarSalida(tramiteAccion);
    }

    public TramiteAccion obtenerPorNtramiteEproceso(Tramite traNumero, String tmaEstadoProceso) throws ServicioExcepcion {
        Query q;
        try {
            q = getEntityManager().createNamedQuery(TramiteAccion.OBTENER_POR_TRAMITE_FILTRADO);
            q.setParameter("traNumero", traNumero);
            q.setParameter("tmaEstadoProceso", tmaEstadoProceso);
            q.setMaxResults(1);
            return (TramiteAccion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public TramiteAccion obtenerPorNtramiteEprocesoUasigNull(Tramite traNumero, String tmaEstadoProceso) throws ServicioExcepcion {
        Query q;
        try {
            q = getEntityManager().createNamedQuery(TramiteAccion.OBTENER_POR_TRAMITE_FILTRADO_NULL);
            q.setParameter("traNumero", traNumero);
            q.setParameter("tmaEstadoProceso", tmaEstadoProceso);
            q.setMaxResults(1);
            return (TramiteAccion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<TramiteAccion> listarPorNtramiteEproceso(Tramite traNumero, String tmaEstadoProceso) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        parametros.put("tmaEstadoProceso", tmaEstadoProceso);
        return listarPorConsultaJpaNombrada(TramiteAccion.LISTAR_POR_TRAMITE_FILTRADO, parametros);
    }

    public List<TramiteAccion> listarProceso(String traNumero, String tmaEstadoProceso) {
        String sql = "select * from TramiteAccion WHERE TramiteAccion.TraNumero=? AND TramiteAccion.TmaEstadoProceso=?  AND TramiteAccion.TmaUserAsg is NULL";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", tmaEstadoProceso);
        return q.getResultList();
    }
/////////////////////consulta Selectiva//////////////////////////////////

    public List<TramiteAccion> listarTramiteAccionPorUsuario(String usuario) {
        String sql = "SELECT * "
                + "FROM "
                + "TramiteAccion ta "
                + "JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "JOIN Rol r ON r.RolId = u.RolId "
                + "WHERE "
                + "ta.TmaUserAsg = ? ";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", usuario);
        return q.getResultList();
    }

    public List<TramiteAccion> listarTramiteAccionPorUsuarioyFecha(String usuario, Date fecha) {
        String sql = "SELECT * "
                + "FROM "
                + "TramiteAccion ta "
                + "JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "JOIN Rol r ON r.RolId = u.RolId "
                + "WHERE "
                + "ta.TmaUserAsg = ? AND CONVERT(DATE,ta.TmaFHR)= ?";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", usuario);
        q.setParameter("2", fecha);
        return q.getResultList();
    }

    public List<TramiteAccion> listarTramiteAccionPorRol(String rol) {
        String sql = "SELECT * "
                + "FROM "
                + "TramiteAccion ta "
                + "JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "JOIN Rol r ON r.RolId = u.RolId "
                + "WHERE "
                + "r.RolNombre= ? ";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", rol);
        return q.getResultList();
    }

    public List<TramiteAccion> listarTramiteAccionPorRolyFecha(String rol, Date Fecha) {
        String sql = "SELECT * "
                + "FROM "
                + "TramiteAccion ta "
                + "JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "JOIN Rol r ON r.RolId = u.RolId "
                + "WHERE "
                + "r.RolNombre= ? AND CONVERT(DATE,ta.TmaFHR)= ? ";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", rol);
        q.setParameter("2", Fecha);
        return q.getResultList();
    }

    public List<TramiteAccion> listarTramiteAccionPorFecha(Date fecha) {
        String sql = "SELECT * "
                + "FROM "
                + "TramiteAccion ta "
                + "JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "JOIN Rol r ON r.RolId = u.RolId "
                + "WHERE "
                + "CONVERT(DATE,ta.TmaFHR)= ? ";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", fecha);
        return q.getResultList();
    }

    public List<TramiteAccion> listarTramiteAccionPorUsuarioyRol(String usuario, String rol) {
        String sql = "SELECT * "
                + "FROM "
                + "TramiteAccion ta "
                + "JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "JOIN Rol r ON r.RolId = u.RolId "
                + "WHERE "
                + "ta.TmaUserAsg = ? AND  r.RolNombre= ?";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", usuario);
        q.setParameter("1", rol);
        return q.getResultList();
    }

    public List<TramiteAccion> listarTramiteAccionPorUsuarioRolyFecha(String usuario, String rol, Date fecha) {
        String sql = "SELECT * "
                + "FROM "
                + "TramiteAccion ta "
                + "JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "JOIN Rol r ON r.RolId = u.RolId "
                + "WHERE "
                + "ta.TmaUserAsg = ? AND r.RolNombre= ? AND CONVERT(DATE,ta.TmaFHR)= ?";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", usuario);
        q.setParameter("2", rol);
        q.setParameter("3", fecha);
        return q.getResultList();
    }

    public List<TramiteAccion> listarPorNtramiteyFecha(Long traNumero, Date fecha) {
        String sql = "SELECT * "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", fecha);
        return q.getResultList();
    }

    public List<String> listarUsuariosPorFecha(Long traNumero, Date fecha) {
        String sql = "SELECT CONCAT(p.PerApellidoPaterno , p.PerApellidoMaterno, p.PerNombre) as nombreUsuario "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", traNumero);
        q.setParameter("2", fecha);
        return q.getResultList();
    }

    public List<TramiteAccion> listarPorNtramiteUsuario(Long traNumero, String usuario) {
        String sql = "SELECT * "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND ta.TmaUserAsg = ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", usuario);
        return q.getResultList();
    }

    public List<String> listarUsuariosPorUsuario(Long traNumero, String usuario) {
        String sql = "SELECT CONCAT(p.PerApellidoPaterno , p.PerApellidoMaterno, p.PerNombre) as nombreUsuario "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND ta.TmaUserAsg = ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", traNumero);
        q.setParameter("2", usuario);
        return q.getResultList();
    }

    public List<TramiteAccion> listarPorNtramiteRol(Long traNumero, String rol) {
        String sql = "SELECT * "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND r.RolNombre= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", rol);
        return q.getResultList();
    }

    public List<String> listarUsuariosPorRol(Long traNumero, String rol) {
        String sql = "SELECT CONCAT(p.PerApellidoPaterno , p.PerApellidoMaterno, p.PerNombre) as nombreUsuario "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND r.RolNombre= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", traNumero);
        q.setParameter("2", rol);
        return q.getResultList();
    }

    public List<TramiteAccion> listarPorNtramiteUsuarioFecha(Long traNumero, String usuario, Date fecha) {
        String sql = "SELECT * "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND ta.TmaUserAsg = ? AND CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", usuario);
        q.setParameter("3", fecha);
        return q.getResultList();
    }

    public List<String> listarUsuariosPorUsuarioFecha(Long traNumero, String usuario, Date fecha) {
        String sql = "SELECT CONCAT(p.PerApellidoPaterno , p.PerApellidoMaterno, p.PerNombre) as nombreUsuario "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND ta.TmaUserAsg = ? AND CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", traNumero);
        q.setParameter("2", usuario);
        q.setParameter("3", fecha);
        return q.getResultList();
    }

    public List<TramiteAccion> listarPorNtramiteRolFecha(Long traNumero, String rol, Date fecha) {
        String sql = "SELECT * "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND r.RolNombre= ? AND CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", rol);
        q.setParameter("3", fecha);
        return q.getResultList();
    }

    public List<String> listarUsuariosPorRolFecha(Long traNumero, String rol, Date fecha) {
        String sql = "SELECT CONCAT(p.PerApellidoPaterno , p.PerApellidoMaterno, p.PerNombre) as nombreUsuario "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND r.RolNombre= ? AND CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", traNumero);
        q.setParameter("2", rol);
        q.setParameter("3", fecha);
        return q.getResultList();
    }

    public List<TramiteAccion> listarPorNtramiteUsuarioRol(Long traNumero, String usuario, String rol) {
        String sql = "SELECT * "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND ta.TmaUserAsg = ? AND r.RolNombre= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", usuario);
        q.setParameter("3", rol);
        return q.getResultList();
    }

    public List<String> listarUsuariosPorUsuarioRol(Long traNumero, String usuario, String rol) {
        String sql = "SELECT CONCAT(p.PerApellidoPaterno , p.PerApellidoMaterno, p.PerNombre) as nombreUsuario "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND ta.TmaUserAsg = ? AND r.RolNombre= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", traNumero);
        q.setParameter("2", usuario);
        q.setParameter("3", rol);
        return q.getResultList();
    }

    public List<TramiteAccion> listarPorNtramiteUsuarioRolFecha(Long traNumero, String usuario, String rol, Date fecha) {
        String sql = "SELECT * "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND ta.TmaUserAsg = ? AND r.RolNombre= ? AND CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", usuario);
        q.setParameter("3", rol);
        q.setParameter("4", fecha);
        return q.getResultList();
    }

    public List<String> listarUsuariosPorUsuarioRolFecha(Long traNumero, String usuario, String rol, Date fecha) {
        String sql = "SELECT CONCAT(p.PerApellidoPaterno , p.PerApellidoMaterno, p.PerNombre) as nombreUsuario "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE ta.TraNumero= ? AND ta.TmaUserAsg = ? AND r.RolNombre= ? AND CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", traNumero);
        q.setParameter("2", usuario);
        q.setParameter("3", rol);
        q.setParameter("4", fecha);
        return q.getResultList();
    }

    public List<TramiteAccion> listarPorFecha(Date fecha) {
        String sql = "SELECT * "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
        q.setParameter("1", fecha);
        return q.getResultList();
    }

    public List<String> listarUsuPorFecha(Date fecha) {
        String sql = "SELECT CONCAT(p.PerApellidoPaterno , p.PerApellidoMaterno, p.PerNombre) as nombreUsuario "
                + "FROM TramiteAccion ta "
                + "INNER JOIN Usuario u ON u.UsuLogin = ta.TmaUserAsg "
                + "INNER JOIN Rol r ON r.RolId = u.RolId "
                + "INNER JOIN Persona p ON p.PerId = u.PerId "
                + "WHERE CONVERT(DATE,ta.TmaFHR)= ? "
                + "ORDER BY ta.TraNumero";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", fecha);
        return q.getResultList();
    }
    /////////////////////consulta Selectiva//////////////////////////////////

    public TramiteAccion buscarUser_TramiteConRepertorioAsignado(Long numTramite, String EstadoProc) {
        try {
            String sql = "SELECT TOP 1 * FROM TramiteAccion "
                    + "WHERE TraNumero = ? AND TmaEstadoProceso = ? AND TmaUserAsg <> '' "
                    + "ORDER BY TmaId DESC";
            Query q = getEntityManager().createNativeQuery(sql, TramiteAccion.class);
            q.setParameter("1", numTramite);
            q.setParameter("2", EstadoProc);
            q.setMaxResults(1);
            return (TramiteAccion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    public TramiteAccion listarPorTramite(Long traNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", traNumero);
        try {
            return (TramiteAccion) obtenerPorConsultaNativa("Select top 1 * from TramiteAccion where TraNumero = ? ",
                    parametros, TramiteAccion.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public TramiteAccion obtenerPorProceso(Long traNumero, String proceso) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", traNumero);
        parametros.put("2", proceso);
        try {
            return (TramiteAccion) obtenerPorConsultaNativa("select top 1 * from TramiteAccion where TraNumero=? AND TmaEstadoProceso=? ",
                    parametros, TramiteAccion.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
     public TramiteAccion obtenerPorProcesoConUserAsignado(Long traNumero, String proceso) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", traNumero);
        parametros.put("2", proceso);
        try {
            return (TramiteAccion) obtenerPorConsultaNativa("SELECT TOP 1 * FROM TramiteAccion WHERE TraNumero=? AND TmaEstadoProceso=? "
                    + " AND TmaUserAsg IS NOT NULL",
                    parametros, TramiteAccion.class);
        } catch (ServicioExcepcion ex) {
            return null;           
        }
    }
///////////////prueba carga diferida:
    public int numTotalRegistros() {
        Query q;
        q = getEntityManager().createQuery("SELECT COUNT(ta.tmaId) FROM TramiteAccion ta");
        return ((Long) q.getSingleResult()).intValue();
    }

    public int numRegistrosConFiltros(Map<String, Object> filters) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
            Root<TramiteAccion> root = criteriaQuery.from(TramiteAccion.class);
            CriteriaQuery<Long> select = criteriaQuery.select(cb.count(root));

            if (filters != null && filters.size() > 0) {
                List<Predicate> predicates = new ArrayList<>();
                for (Map.Entry<String, Object> entry : filters.entrySet()) {
                    String field = entry.getKey();
                    Object value = entry.getValue();
                    if (value == null) {
                        continue;
                    }

                    Expression<String> expr = root.get(field).as(String.class);
                    Predicate p = cb.like(cb.lower(expr),
                            "%" + value.toString().toLowerCase() + "%");
                    predicates.add(p);
                }
                if (predicates.size() > 0) {
                    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                }
            }
            Long count = getEntityManager().createQuery(select).getSingleResult();
            return count.intValue();

        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }

    }

    public List<TramiteAccion> listarPorDemandaSinFiltros(int start, int size,
            Map<String, Object> filters) {
        try {
            Query q = null;
            String sql = "SELECT ta FROM TramiteAccion ta";

            q = getEntityManager().createQuery(sql);
            q.setFirstResult(start);
            q.setMaxResults(size);

            return q.getResultList();

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public List<TramiteAccion> listarPorPaginaConOsinFiltros(int start, int size,
            Map<String, Object> filters) {
        try {

            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<TramiteAccion> criteriaQuery = cb.createQuery(TramiteAccion.class);
            Root<TramiteAccion> root = criteriaQuery.from(TramiteAccion.class);
            CriteriaQuery<TramiteAccion> select = criteriaQuery.select(root);

            if (filters != null && filters.size() > 0) {
                List<Predicate> predicates = new ArrayList<>();
                for (Map.Entry<String, Object> entry : filters.entrySet()) {
                    String field = entry.getKey();
                    Object value = entry.getValue();
                    if (value == null) {
                        continue;
                    }

                    Expression<String> expr = root.get(field).as(String.class);
                    Predicate p = cb.like(cb.lower(expr),
                            "%" + value.toString().toLowerCase() + "%");
                    predicates.add(p);
                }
                if (predicates.size() > 0) {
                    criteriaQuery.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
                }
            }

            TypedQuery<TramiteAccion> query = getEntityManager().createQuery(select);
            query.setFirstResult(start);
            query.setMaxResults(size);
            List<TramiteAccion> list = query.getResultList();
            return list;

        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

}
