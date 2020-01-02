/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.Estado;
import com.rm.empresarial.modelo.Usuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Query;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class CargaLaboralDao extends Generico<CargaLaboral> implements Serializable {

    private static final long serialVersionUID = -5978514054808084896L;

    public CargaLaboral buscarPorCargaLaboral() {
        Object carga = null;
        CargaLaboral cargaLaboral = null;
        try {
            cargaLaboral = (CargaLaboral) obtenerPorConsultaNativa(CargaLaboral.BUSCAR_POR_CARGA_LABORAL, null, CargaLaboral.class);

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(CargaLaboralDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cargaLaboral;
    }

    public void guardarCargaLaboral(CargaLaboral cargaLaboral) {
        try {
            guardar(cargaLaboral);
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(CargaLaboralDao.class.getName()).log(Level.SEVERE, "Error al registrar carga", ex);
        }
    }

    public CargaLaboral buscarPorCargaLaboralConTipo(String tipo) {
//        Object carga = null;
        CargaLaboral cargaLaboral = null;
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", tipo);
            cargaLaboral = (CargaLaboral) obtenerPorConsultaNativa(CargaLaboral.BUSCAR_POR_CARGA_LABORAL_TIPO, parametros, CargaLaboral.class);
            return cargaLaboral;

        } catch (ServicioExcepcion ex) {
//            Logger.getLogger(CargaLaboralDao.class.getName()).log(Level.SEVERE, "Error al registrar carga", ex);
            return null;

        }
    }

    public List<Usuario> listarUsuariosDeCargaLaboral(String tipo) throws ServicioExcepcion {
        Query q = getEntityManager().createNamedQuery(CargaLaboral.LISTAR_USUARIOS_DE_CARGA_LABORAL);
        q.setParameter("carTipo", tipo);
        return q.getResultList();

    }

    public List<CargaLaboral> listaCargaLaboral() {
        try {
            String sql = "SELECT * from CargaLaboral ";
            Query q = getEntityManager().createNativeQuery(sql, CargaLaboral.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }

    public String buscarEstadoPorCarTipo(String carTipo) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", carTipo);
        try {
            return ((Estado) obtenerPorConsultaNativa("SELECT Estado.* FROM Estado "
                    + "WHERE Estado.EstCodigo = ?", parametros, Estado.class)).getEstDescripcion();
        } catch (ServicioExcepcion ex) {
            return "";
        }
    }

    public List<CargaLaboral> listarFechaYrol(Date fechaIni, Date fechaFin, String rolId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        String sql = "SELECT CargaLaboral.* FROM CargaLaboral "
                + "INNER JOIN Usuario ON CargaLaboral.UsuId = Usuario.UsuId "
                + "WHERE CONVERT(DATE, CargaLaboral.CarFHR) >= ? "
                + "AND CONVERT(DATE, CargaLaboral.CarFHR) <= ? ";
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        parametros.put("3", rolId);
        if (!rolId.equals("todos")) {
            sql += "AND Usuario.RolId = ?";
        } else {
            parametros.remove("3");
        }
        return listarPorConsultaNativa(sql, parametros, CargaLaboral.class);
    }

    public List<CargaLaboral> listarPorFechaYrol(Date fechaIni, Date fechaFin, String rolId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        String sql = "SELECT CargaLaboral.* FROM CargaLaboral "
                + "INNER JOIN Usuario ON CargaLaboral.UsuId = Usuario.UsuId "
                + "WHERE CONVERT(DATE, CargaLaboral.CarFHR) = ? "
                + "AND CONVERT(DATE, CargaLaboral.CarFHR) = ? ";
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        parametros.put("3", rolId);
        if (!rolId.equals("todos")) {
            sql += "AND Usuario.RolId = ?";
        } else {
            parametros.remove("3");
        }
        return listarPorConsultaNativa(sql, parametros, CargaLaboral.class);
    }

    public Boolean existeCargaLaboral(Long usuId, String tipo) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT COUNT(cl.carId) FROM CargaLaboral cl WHERE cl.carTipo LIKE :tipo AND cl.usuId.usuId = :usuId");

        q.setParameter("usuId", usuId);
        q.setParameter("tipo", tipo);

        Long resultado;
        resultado = (Long) q.getSingleResult();

        return resultado > 0;

    }

    public CargaLaboral buscarCargaLaboral(Long usuId, String tipo) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("SELECT cl FROM CargaLaboral cl WHERE cl.carTipo LIKE :tipo AND cl.usuId.usuId = :usuId ORDER BY cl.carAsignado ASC");
        q.setMaxResults(1);
        q.setParameter("usuId", usuId);
        q.setParameter("tipo", tipo);

//        Long resultado;
//        resultado = (Long) q.getSingleResult();
        try {
            return (CargaLaboral) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    

    public CargaLaboral buscarPorUsuario(Usuario usuId) {
        Query q;
        q = getEntityManager().createQuery("SELECT cl FROM CargaLaboral cl WHERE cl.usuId = :usuId");
        q.setMaxResults(1);
        q.setParameter("usuId", usuId);

        return (CargaLaboral) q.getSingleResult();
    }

}
