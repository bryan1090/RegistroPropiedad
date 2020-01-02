/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.PathDocEle;
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
public class PathDocEleDao extends Generico<PathDocEle> implements Serializable {
    
    public List<PathDocEle> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(PathDocEle.LISTAR_TODO, null);
    }
    
    public PathDocEle obtenerPathTemporales() throws ServicioExcepcion {
        return (PathDocEle) obtenerPorConsultaNativa("select top 1 * from PathDocEle", null,PathDocEle.class);
    }
    
    public PathDocEle obtenerPaths() throws ServicioExcepcion {
        return obtenerPorConsultaJpaNombrada(PathDocEle.LISTAR_TODO, null);
    }
}
