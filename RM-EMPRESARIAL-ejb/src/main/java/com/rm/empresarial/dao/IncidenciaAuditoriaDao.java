package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Incidencia;
import com.rm.empresarial.modelo.IncidenciaAuditoria;
import com.rm.empresarial.modelo.IncidenciaHistorico;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author DJimenez
 */
@LocalBean
@Stateless
public class IncidenciaAuditoriaDao extends Generico<IncidenciaAuditoria> implements Serializable{
    
    public List<IncidenciaAuditoria> listarPorIdIncidencia(Incidencia incId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("incId", incId);
            return listarPorConsultaJpaNombrada("IncidenciaAuditoria.findByIncId", parametros);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }
    
}
