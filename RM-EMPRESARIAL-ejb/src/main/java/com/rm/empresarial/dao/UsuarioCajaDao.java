/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.modelo.UsuarioCaja;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Wilson Herrera
 */
@LocalBean
@Stateless
public class UsuarioCajaDao extends Generico<UsuarioCaja> implements Serializable {
    
    public List<UsuarioCaja> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(UsuarioCaja.LISTAR_TODO, null);
    }
    
    public UsuarioCaja buscarCajaPorUsuario(Usuario usuario){
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", usuario);
        try{
        return obtenerPorConsultaJpaNombrada(UsuarioCaja.CAJA_POR_USARIO, parametros);
        }catch(ServicioExcepcion e){
          e.printStackTrace();
        }
        return null;
    }
    
    public boolean validacionIngresarCrear(Long cajId, Long usuId) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cajId", cajId);
        parametros.put("usuId", usuId);
        return listarPorConsultaJpaNombrada(UsuarioCaja.VALIDACION_INGRESO, parametros).isEmpty();
    }
    
    public boolean validacionIngresarEditar(Long uscId, Long cajId, Long usuId) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("uscId", uscId);
        parametros.put("cajId", cajId);
        parametros.put("usuId", usuId);
        return listarPorConsultaJpaNombrada(UsuarioCaja.VALIDACION_INGRESO_EDITAR, parametros).isEmpty();
    }
}
