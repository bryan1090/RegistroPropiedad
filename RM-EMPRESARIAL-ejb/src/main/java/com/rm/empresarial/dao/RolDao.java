/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Rol;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Zona;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class RolDao extends Generico<Rol> implements Serializable {

    public List<Rol> listarTodo() {
        try {
            return listarPorConsultaJpaNombrada(Rol.LISTAR_TODO, null);
        } catch (ServicioExcepcion e) {
            return null;
        }

    }

    public Rol encontrarRolPorId(String idRol) throws ServicioExcepcion {
        Long id = new Long(idRol);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rolId", id);
        return obtenerPorConsultaJpaNombrada(Rol.ENCONTRAR_ROL_POR_ID, parametros);
    }
    
    public Rol encontrarRolPorNom(String rolNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", rolNombre);
        return (Rol) obtenerPorConsultaNativa("SELECT Rol.* FROM Rol WHERE Rol.RolNombre = ?", parametros, Rol.class);
    }

    public boolean validarNombreCrear(String rolNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rolNombre", rolNombre);
        return listarPorConsultaJpaNombrada(Rol.BUSCAR_POR_NOM, parametros).isEmpty();
    }

    public boolean validarNombreEditar(Long rolId, String rolNombre) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("rolId", rolId);
        parametros.put("rolNombre", rolNombre);
        return listarPorConsultaJpaNombrada(Rol.BUSCAR_POR_NOM_EDITAR, parametros).isEmpty();
    }

}
