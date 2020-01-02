/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.RepositorioRC;
import com.rm.empresarial.modelo.RepositorioSRI;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author WILSON
 */
@LocalBean
@Stateless
public class RepositorioRCDao extends Generico<RepositorioRC> implements Serializable{
    
    private static final long serialVersionUID = 3470970399080994730L;
    
    public RepositorioRC buscarPorIdentificacion(String identificacion) throws ServicioExcepcion{

        Map<String,Object> parametros=new HashMap<>();
        parametros.put("rrcIdentificacion", identificacion.trim());
        return obtenerPorConsultaJpaNombrada(RepositorioRC.BUSCAR_POR_IDENTIFICACION,parametros);
        
    }

    
    
}
