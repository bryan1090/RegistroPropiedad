/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.TipoLibro;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Richard
 */
@LocalBean
@Stateless
public class TipoLibroDao extends Generico<TipoLibro> implements Serializable {

    public List<TipoLibro> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoLibro.LISTAR_TODOS, null);
    }

    public TipoLibro encontrarTipoLibroPorId(String tplId) throws ServicioExcepcion {
        Long id = new Long(tplId);
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tplId", id);
        return obtenerPorConsultaJpaNombrada(TipoLibro.LISTAR_PORID, parametros);
    }
    
    public boolean validarDescripCrear(String tplDescripcion) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tplDescripcion", tplDescripcion);
        return listarPorConsultaJpaNombrada(TipoLibro.BUSCAR_POR_DESC, parametros).isEmpty();
    }
    
    public boolean validarDescripEditar(Long tplId, String tplDescripcion) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("tplId", tplId);
        parametros.put("tplDescripcion", tplDescripcion);
        return listarPorConsultaJpaNombrada(TipoLibro.BUSCAR_POR_DESC_EDITAR, parametros).isEmpty();
    }
    
    public List<TipoLibro> listarPorFecha(Date fechaIni, Date fechaFin) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        List<TipoLibro> lista = listarPorConsultaNativa("SELECT * \n"
                + "FROM Repertorio r\n"
                + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero\n"
                + "INNER JOIN TramiteDetalle td ON td.TraNumero = t.TraNumero\n"
                + "INNER JOIN TipoLibro tl ON tl.TplId = td.TdtTplId\n"
                + "INNER JOIN Notaria n ON n.NotId = t.TraNotaria\n"
                + "WHERE \n"
                + "CONVERT(DATE,r.RepFHR) >= ? AND CONVERT(DATE,r.RepFHR) <= ? AND td.TdtTtrId = r.RepTtrId ORDER BY r.RepNumero ASC", parametros, TipoLibro.class);
        return lista;
    }
    
    public TipoLibro encontrarPor_Repertorio(Long numRepertorio)
    {
        Query q;
        q=getEntityManager().createNativeQuery("SELECT * FROM TipoLibro TL JOIN TipoTramite TT on TT.TplId=TL.TplId join Repertorio R ON TT.TtrId = R.RepTtrId WHERE R.RepNumero = ?",TipoLibro.class);
        q.setParameter("1",numRepertorio);
        q.setMaxResults(1);
       
        return  (TipoLibro) q.getSingleResult();
    }

}
