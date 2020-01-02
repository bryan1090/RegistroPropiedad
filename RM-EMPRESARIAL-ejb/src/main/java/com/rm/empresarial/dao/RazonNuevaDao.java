/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.ActaRazon;
import com.rm.empresarial.modelo.Razon;
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
 * @author Prueba
 */
@LocalBean
@Stateless
public class RazonNuevaDao extends Generico<Razon> implements Serializable {

    public List<Razon> listarPorFecha_Y_Usuario(String usuario, Date fecha) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuario);
        parametros.put("2", fecha);
        String sql = "SELECT * FROM Razon r "
                + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero "
                + "WHERE "
                + "r.RazUser = ? AND CONVERT(DATE,r.RazFHR) = ? AND t.TraEstado <> 'FIN'";
        return listarPorConsultaNativa(sql, parametros, Razon.class);
    }
    
    public Razon obtenerRazonPornumTramite(Long traNumero) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", traNumero);
            String sql = "SELECT TOP(1) * FROM Razon WHERE TraNumero = ? ";
            return (Razon) obtenerPorConsultaNativa(sql, parametros, Razon.class);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }
    
    public List<Razon>  obtenerPorFechaRepertorio_Usuario_Tramite(String usuario, Date fecha) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fecha);
        parametros.put("2", usuario);
        String sql = "SELECT DISTINCT r.RazId, r.TraNumero, r.RazHtml FROM Razon r "
                + " INNER JOIN Tramite t ON t.TraNumero = r.TraNumero "
                + " INNER JOIN Repertorio rep ON rep.TraNumero = t.TraNumero"
                + " WHERE CONVERT(DATE,rep.RepFHR) = ?"
                + " AND r.RazUser = ? AND t.TraEstado <> 'FIN'";
        return listarPorConsultaNativa(sql, parametros, Razon.class);
    }
}
