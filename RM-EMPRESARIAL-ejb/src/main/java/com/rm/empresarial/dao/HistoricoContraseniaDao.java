/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.HistoricoContrasenia;
import com.rm.empresarial.modelo.HostMail;
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
 * @author Marco
 */
@LocalBean
@Stateless
public class HistoricoContraseniaDao extends Generico<HistoricoContrasenia> implements Serializable {

    public List<HistoricoContrasenia> ListarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(HistoricoContrasenia.LISTAR_TODO, null);
        //
    }

    public List<HistoricoContrasenia> encontrarPorUsuario(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        return listarPorConsultaNativa("SELECT HistoricoContrasenia.* FROM HistoricoContrasenia "
                + "WHERE HistoricoContrasenia.UsuId = ?", parametros, HistoricoContrasenia.class);
    }

    public Long asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(h.hisConId) from HistoricoContrasenia h");
            return ((Long) q.getSingleResult()) + 1;
        } catch (Exception e) {
            return new Long(1);
        }
    }

}
