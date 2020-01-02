/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.DocumentoEntregaTramite;
import com.rm.empresarial.modelo.UsuarioCaja;
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
public class DocumentoEntregaTramiteDao extends Generico<DocumentoEntregaTramite> implements Serializable {

    public List<DocumentoEntregaTramite> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(DocumentoEntregaTramite.LISTAR_TODO, null);
    }

    public boolean validacionIngresarCrear(Long dteId, Long ttrId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("dteId", dteId);
        parametros.put("ttrId", ttrId);
        return listarPorConsultaJpaNombrada(DocumentoEntregaTramite.VALIDACION_INGRESO, parametros).isEmpty();
    }

    public boolean validacionIngresarEditar(Long detId, Long dteId, Long ttrId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("detId", detId);
        parametros.put("dteId", dteId);
        parametros.put("ttrId", ttrId);
        return listarPorConsultaJpaNombrada(DocumentoEntregaTramite.VALIDACION_INGRESO_EDITAR, parametros).isEmpty();
    }

    public List<DocumentoEntregaTramite> listarPorTipoTramite(Long ttrId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("ttrId", ttrId);
        return listarPorConsultaJpaNombrada(DocumentoEntregaTramite.LISTAR_POR_TIPO_TRAMITE, parametros);
    }

    public DocumentoEntregaTramite buscarPorDteIdYttrId(Long dteId, Long ttrId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("dteId", dteId);
        parametros.put("ttrId", ttrId);
        return obtenerPorConsultaJpaNombrada(DocumentoEntregaTramite.BUSCAR_POR_DTEID_Y_TTRID, parametros);
    }

    public Long asignarID() {
        Query q;
        try {
            q = getEntityManager().createQuery("select max(d.detId) from DocumentoEntregaTramite d");
            return ((Long) q.getSingleResult()) + 1;
        } catch (Exception e) {
            return new Long(1);
        }
    }

}
