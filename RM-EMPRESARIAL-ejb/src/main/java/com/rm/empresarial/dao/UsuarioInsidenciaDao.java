package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Incidencia;
import com.rm.empresarial.modelo.UsuarioInsidencia;
import java.io.Serializable;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;


/**
 *
 * @author DJimenez
 */
@LocalBean
@Stateless
public class UsuarioInsidenciaDao extends Generico<UsuarioInsidencia> implements Serializable{
    
    public List<UsuarioInsidencia> listarTodo() {
        try {
            return listarPorConsultaJpaNombrada(UsuarioInsidencia.LISTAR_TODO, null);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }
}
