/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.RepositorioSRI;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class RepositorioSRIDao extends Generico<RepositorioSRI> implements Serializable {

    private static final long serialVersionUID = 5058335123959296911L;

    public RepositorioSRI buscarPorIdentificacion(String identificacion) throws ServicioExcepcion, ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("sriRuc", identificacion.trim());

        return obtenerPorConsultaJpaNombrada(RepositorioSRI.BUSCAR_POR_IDENTIFICACION, parametros);

    }

}
