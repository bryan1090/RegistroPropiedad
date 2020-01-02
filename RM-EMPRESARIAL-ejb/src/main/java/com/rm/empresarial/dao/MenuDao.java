/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Menu;
import com.rm.empresarial.modelo.TipoTramite;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author JeanCarlos
 */
@LocalBean
@Stateless
public class MenuDao extends Generico<Menu> implements Serializable {

    public List<Menu> ListarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Menu.LISTAR_TODO, null);
        //
    }

    public List<Menu> Listar() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Menu.LISTAR, null);
        //
    }

    public Menu buscarMenuPorId(String ttrId) throws ServicioExcepcion {
        //Long id = new Long(ttrId);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("menId", ttrId);
        return obtenerPorConsultaJpaNombrada(Menu.BUSCAR_POR_ID, parametros);
    }

    public List<Menu> ListarporPadre() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Menu.LISTAR_TODO, null);
        //
    }

    public boolean BuscarPorRolYLink(Long rolId, String link) {

        Query q;

        String sql = "SELECT COUNT(RolMenu.rolMenId)"
                + " FROM Menu"
                + " INNER JOIN RolMenu ON RolMenu.menId = Menu.menId"
                + " WHERE Menu.menLink = ?"
                + " AND RolMenu.rolId = ?";

        try {

            q = getEntityManager().createNativeQuery(sql);

            q.setParameter("1", link);

            q.setParameter("2", rolId);

            int existe = (int) q.getSingleResult();

            if (existe > 0) {

                return true;

            } else {

                return false;

            }

        } catch (Exception e) {

            return true;

        }
    }

}
