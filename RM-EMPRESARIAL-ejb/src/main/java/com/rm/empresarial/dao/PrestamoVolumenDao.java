/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.PrestamoVolumen;
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
public class PrestamoVolumenDao extends Generico<PrestamoVolumen> implements Serializable {

    public PrestamoVolumen idPrestamoVolumen() {
        String sql = "SELECT Max(PrestamoVolumen.PrvId) as PrvId from PrestamoVolumen";
        Query q = getEntityManager().createNativeQuery(sql, PrestamoVolumen.class);
        return (PrestamoVolumen) q.getSingleResult();
    }

    public List<PrestamoVolumen> listarPretamoVolumenPorId(Long vleId) throws ServicioExcepcion {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", vleId);
            return listarPorConsultaNativa("SELECT * from PrestamoVolumen where VleId=? ", parametros, PrestamoVolumen.class);

        } catch (Exception e) {
            return null;
        }

    }

}
