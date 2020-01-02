/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Secuencia;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author WILSON
 */
@LocalBean
@Stateless

public class SecuenciaDao extends Generico<Secuencia> implements Serializable {

    private static final long serialVersionUID = 54039175709069621L;

    public Secuencia obtenerSecuencia(String codigo) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("secCodigo", codigo);
        return obtenerPorConsultaJpaNombrada(Secuencia.BUSCAR_POR_CODIGO, parametros);
    }

    public void guardarSecuencia(Secuencia secuencia) {
        try {
            guardar(secuencia);

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(TramiteDao.class.getName()).log(Level.SEVERE, "Error al guardar secuencia", ex);
        }
    }

    public List<Secuencia> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Secuencia.LISTAR_TODOS, null);
    }
    
    public boolean validarCodigoCrear(String secCodigo) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("secCodigo", secCodigo);
        return listarPorConsultaJpaNombrada(Secuencia.BUSCAR_POR_CODIGO, parametros).isEmpty();
    }
    
    public boolean validarCodigoEditar(Long secId, String secCodigo) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("secId", secId);
        parametros.put("secCodigo", secCodigo);
        return listarPorConsultaJpaNombrada(Secuencia.BUSCAR_POR_CODIGO_EDITAR, parametros).isEmpty();
    }

}
