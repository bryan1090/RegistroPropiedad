/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoCompareciente;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author DJimenez
 */
@LocalBean
@Stateless
public class TipoComparecienteDao extends Generico<TipoCompareciente> implements Serializable {

    public List<TipoCompareciente> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoCompareciente.LISTAR_TODO, null);
    }

    public TipoCompareciente encontrarTipoComparecientePorId(String idTipoCompareciente) throws ServicioExcepcion {
        try {
            Long id = new Long(idTipoCompareciente);

            Map<String, Object> parametros = new HashMap<>();
            parametros.put("tpcId", id);
            return obtenerPorConsultaJpaNombrada(TipoCompareciente.LISTAR_PORID, parametros);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public boolean validarDescripCrear(String tpcDescripcion) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpcDescripcion", tpcDescripcion);
        return listarPorConsultaJpaNombrada(TipoCompareciente.BUSCAR_POR_DESC, parametros).isEmpty();
    }

    public boolean validarDescripEditar(Long tpcId, String tpcDescripcion) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tpcId", tpcId);
        parametros.put("tpcDescripcion", tpcDescripcion);
        return listarPorConsultaJpaNombrada(TipoCompareciente.BUSCAR_POR_DESC_EDITAR, parametros).isEmpty();
    }

    public boolean validarCodigoCrear(String tpcCodigo) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tpcCodigo);
        return listarPorConsultaNativa("SELECT * FROM TipoCompareciente "
                + "WHERE TipoCompareciente.TpcCodigo = ?", parametros, TipoCompareciente.class).isEmpty();
    }

    public boolean validarCodigoEditar(Long tpcId, String tpcCodigo) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tpcCodigo);
        parametros.put("2", tpcId);
        return listarPorConsultaNativa("SELECT * FROM TipoCompareciente "
                + "WHERE TipoCompareciente.TpcCodigo = ? "
                + "AND TipoCompareciente.TpcId != ?", parametros, TipoCompareciente.class).isEmpty();
    }

    public List<TipoCompareciente> listarTipoCompareciente() {
        String sql = "SELECT * FROM Persona,TipoTramite\n"
                + "INNER JOIN TramiteDetalle ON TramiteDetalle.TdtTtrId = TipoTramite.TtrId \n"
                + "INNER JOIN TipoCompareciente on TramiteDetalle.TdtTpcId = TipoCompareciente.TpcId\n"
                + "WHERE Persona.PerId = TramiteDetalle.PerId \n"
                + "AND TipoTramite.TrCodigoAgrupacion1='UAFE' \n"
                + "AND TramiteDetalle.TdtNumeroRepertorio<>0\n"
                + "ORDER BY TramiteDetalle.TdtNumeroRepertorio ";
        Query q = getEntityManager().createNativeQuery(sql, TipoCompareciente.class);
//        q.setParameter("1", fechaIni);
//        q.setParameter("2", fechaFin);
        return q.getResultList();
    }

}
