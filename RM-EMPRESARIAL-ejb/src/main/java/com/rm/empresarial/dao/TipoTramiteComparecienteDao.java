/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoCompareciente;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author WILSON
 */
@LocalBean
@Stateless
public class TipoTramiteComparecienteDao extends Generico<TipoTramiteCompareciente> implements Serializable {

    private static final long serialVersionUID = -7307329915848556791L;

    public List<TipoTramiteCompareciente> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoTramiteCompareciente.LISTAR_TODO, null);

    }

    public TipoTramiteCompareciente buscarPorId(BigDecimal id) throws ServicioExcepcion {
        TipoTramiteCompareciente tipoTramiteCompareciente = new TipoTramiteCompareciente();
        return find(new TipoTramiteCompareciente(), id);
        /*TipoTramiteCompareciente tipoTramite=null;
         Map<String, Object> parametros = new HashMap<>();
        parametros.put("ttcId", id);
       return  tipoTramite=obtenerPorConsultaJpaNombrada(TipoTramiteCompareciente.BUSCAR_POR_ID, parametros);*/

    }

    public TipoTramiteCompareciente buscarTramitePorID(String ttrId) throws ServicioExcepcion {
        Long id = new Long(ttrId);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ttcId", id);
        return obtenerPorConsultaJpaNombrada(TipoTramiteCompareciente.BUSCAR_POR_ID, parametros);
    }

    public TipoTramiteCompareciente buscarPorTramite(BigDecimal tipoTramite) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ttrId", tipoTramite);
        return obtenerPorConsultaJpaNombrada(TipoTramiteCompareciente.BUSCAR_POR_TIPO_TRAMTE, parametros);
    }

//    NO SE UTILIZA EL METODO GENERICO
    //SE CREA EL QUERY MANUALMENTE
    //SE RETORNA UN OBJETO DIFERENTE AL DE LA TABLA
    public List<TipoCompareciente> listarTipoComparecientePorTipoTramite(Long tipoTramite) throws ServicioExcepcion {

        Query q;
        q = getEntityManager().createNamedQuery(TipoTramiteCompareciente.LISTAR_TIPO_COMPARECIENTE_POR_TIPO_TRAMITE);
        q.setParameter("ttrId", tipoTramite);
        return q.getResultList();
    }

    public List<TipoTramiteCompareciente> listarTipoTramiteComparecientePorTipoTramite(Long tipoTramite) {
        String sql = "SELECT * FROM TipoTramiteCompareciente WHERE TtrId = ? AND TtcEstado = 'A'";
        Query q = getEntityManager().createNativeQuery(sql, TipoTramiteCompareciente.class);
        q.setParameter("1", tipoTramite);
        return (List<TipoTramiteCompareciente>) q.getResultList();
    }

    public TipoTramiteCompareciente buscarPorIdTramitePorTipoCompareciente(Long ttrId, Long tipoCompareciente) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ttrId", ttrId);
        parametros.put("tpcId", tipoCompareciente);
        return obtenerPorConsultaJpaNombrada(TipoTramiteCompareciente.BUSCAR_POR_TRAMITE_ID_POR_TIPO_COMPARECIENTE, parametros);
    }
}
