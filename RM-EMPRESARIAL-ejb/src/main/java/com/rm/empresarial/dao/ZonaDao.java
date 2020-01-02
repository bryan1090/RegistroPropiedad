/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Zona;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class ZonaDao extends Generico<Zona> implements Serializable {

    private static final long serialVersionUID = 8018905450651268093L;

    public List<Zona> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Zona.LISTAR_TODO, null);
    }

    public Long asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(a.zonId) from Zona a");
            return ((Long) q.getSingleResult()) + 1;
        } catch (Exception e) {
            return new Long(1);
        }
    }

    public Zona encontrarZonaPorId(String idZona) throws ServicioExcepcion
    {
        Long id=new Long(idZona);
        Map<String,Object> parametros=new HashMap<>();
        parametros.put("zonId", id);
        return obtenerPorConsultaJpaNombrada(Zona.ENCONTRAR_ZONA_POR_ID, parametros);
    }
    
    public Zona encontrarPrimerRegistro () throws ServicioExcepcion {
        return (Zona) obtenerPorConsultaNativa("SELECT TOP 1 Zona.* FROM ZONA", null, Zona.class);
    }
   

}
