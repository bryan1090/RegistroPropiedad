package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Incidencia;
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
public class IncidenciaHistoricoDao extends Generico<IncidenciaHistorico> implements Serializable {

    public List<IncidenciaHistorico> listarPorIdIncidencia(Incidencia incId) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("incId", incId);
            return listarPorConsultaJpaNombrada("IncidenciaHistorico.findByIncId", parametros);
        } catch (ServicioExcepcion e) {
            return null;
        }
    }

}
