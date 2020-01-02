/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Incidencia;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.modelo.Zona;
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
 * @author DJimenez
 */
@LocalBean
@Stateless
public class IncidenciaDao extends Generico<Incidencia> implements Serializable {

    public List<Incidencia> listarTodo() {
        try {
            return listarPorConsultaJpaNombrada(Incidencia.LISTAR_TODO, null);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

    public List<Incidencia> listarPorUsuarioLogueado(Usuario usuId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("usuId", usuId);
            return listarPorConsultaJpaNombrada(Incidencia.LISTAR_POR_USU_LOGUEADO, parametros);
        } catch (ServicioExcepcion e) {
            return null;
        }

    }
    
    public List<Incidencia> listarIncidenciasTerminadas() {
        String sql="SELECT * FROM Incidencia WHERE IncEstado = 'T'";
        Query q = getEntityManager().createNativeQuery(sql, Incidencia.class);
        return q.getResultList();
    }
    
    public List<Incidencia> listarIncidenciasPorRangoNumIncidencia(int incidenciaIni, int incidenciaFin){
        String sql="SELECT * FROM Incidencia WHERE IncId >= ? AND IncId <= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Incidencia.class);
        q.setParameter("1", incidenciaIni);
        q.setParameter("2", incidenciaFin);
        return q.getResultList();
    }
    
    public List<Incidencia> listarIncidenciasPorRangoFecha(Date fechaIni, Date fechaFin){
        String sql="SELECT * FROM Incidencia WHERE CONVERT(DATE,IncFHR) >= ? AND CONVERT(DATE,IncFHR) <= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Incidencia.class);
        q.setParameter("1", fechaIni);
        q.setParameter("2", fechaFin);
        return q.getResultList();
    }

}
