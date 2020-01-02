/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.FormularioWebDetalle;
import com.rm.empresarial.modelo.LibroNegro;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.TipoDocumentoWeb;
import java.io.Serializable;
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
public class FormularioWebDetalleDao extends Generico<FormularioWebDetalle> implements Serializable {

    public TipoDocumentoWeb obtenerTipoDocumentoWeb(String nombreFormulario) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", nombreFormulario);
            String sql = "SELECT * FROM TipoDocumentoWeb tdw "
                    + "WHERE tdw.TdwNombre='RPC-01' ";
            return (TipoDocumentoWeb) obtenerPorConsultaNativa(sql, parametros, TipoDocumentoWeb.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Parroquia> listaParroquia() {
        try {
            Query q = getEntityManager().createNamedQuery(Parroquia.LISTAR_TODO, Parroquia.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public Persona buscarPersona(String identificacion) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1",identificacion);
            String sql="SELECT * FROM Persona WHERE PerIdentificacion= ?"; 
            return (Persona) obtenerPorConsultaNativa(sql, parametros, Persona.class);
        } catch (Exception e) {
            return null;
        }
    }
    
     public List<FormularioWebDetalle> buscarDocumentoWeb(Integer fwdId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", fwdId);
            String sql = "SELECT * FROM FormularioWebDetalle"
                    + " WHERE FlwId = ?";
            return (List<FormularioWebDetalle>) listarPorConsultaNativa(sql, parametros, FormularioWebDetalle.class);
        } catch (Exception e) {
            return null;
        }
    }

}
