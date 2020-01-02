/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoTramiteClase;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class TipoTramiteClaseDao extends Generico<TipoTramiteClase> implements Serializable {
    
    public List<TipoTramiteClase> listarTodo() throws ServicioExcepcion
    {
        return listarPorConsultaJpaNombrada(TipoTramiteClase.LISTAR_TODO, null);
    }
    
}
