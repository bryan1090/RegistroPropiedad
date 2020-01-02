/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.persistence.Query;

/**
 *
 * @author Wilson
 */
@LocalBean
@Stateless
public class TipoTramiteDao extends Generico<TipoTramite> implements Serializable {

	private static final long serialVersionUID = -3446901733796198738L;

	public List<TipoTramite> listarTodo() throws ServicioExcepcion {
		return listarPorConsultaJpaNombrada(TipoTramite.LISTAR_TODO, null);

	}

	public TipoTramite buscarPorID(Long ttrId) throws ServicioExcepcion {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("ttrId", ttrId);
		return obtenerPorConsultaJpaNombrada(TipoTramite.BUSCAR_POR_ID, parametros);
	}

	public TipoTramite buscarID(int ttrId) throws ServicioExcepcion {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("ttrId", ttrId);
		return obtenerPorConsultaJpaNombrada(TipoTramite.BUSCAR_POR_ID, parametros);
	}

	public TipoTramite buscarTramitePorID(String ttrId) throws ServicioExcepcion {
		Long id = new Long(ttrId);
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("ttrId", id);
		return obtenerPorConsultaJpaNombrada(TipoTramite.BUSCAR_POR_ID, parametros);
	}

	public boolean validarDescripCrear(String ttrDescripcion) throws ServicioExcepcion {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("ttrDescripcion", ttrDescripcion);
		return listarPorConsultaJpaNombrada(TipoTramite.BUSCAR_POR_DESC, parametros).isEmpty();
	}

	public boolean validarDescripEditar(Long ttrId, String ttrDescripcion) throws ServicioExcepcion {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("ttrId", ttrId);
		parametros.put("ttrDescripcion", ttrDescripcion);
		return listarPorConsultaJpaNombrada(TipoTramite.BUSCAR_POR_DESC_EDITAR, parametros).isEmpty();
	}
        
        public List<TipoTramite> listarPorTipoLibro(Long idLibro){
            Query q;
		try {
			q = getEntityManager().createQuery("SELECT t FROM TipoTramite t WHERE t.tplId.tplId =:tplId", TipoTramite.class);
			q.setParameter("tplId", idLibro);
			return q.getResultList();
		} catch (Exception e) {
			return null;
		}
            
        }

	public TipoTramite buscarPorDescripcion(String ttrDescripcion) throws ServicioExcepcion {
		Query q;
		try {
			q = getEntityManager().createQuery("SELECT t FROM TipoTramite t WHERE t.ttrDescripcion=:ttrDescripcion", TipoTramite.class);
			q.setParameter("ttrDescripcion", ttrDescripcion);
			q.setMaxResults(1);
			return (TipoTramite) q.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}
        public boolean buscarPorDescripcionTRUE(String ttrDescripcion) throws ServicioExcepcion {
		Query q;
		try {
			q = getEntityManager().createNativeQuery("SELECT * FROM TipoTramite WHERE TtrDescripcion= ? ", TipoTramite.class);
			q.setParameter("1", ttrDescripcion);
			q.getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public List<TipoTramite> listarPorTraClase(String ttrClase) throws ServicioExcepcion {
		Query q;
		try {
			q = getEntityManager().createQuery("SELECT t FROM TipoTramite t WHERE t.ttrClase =:ttrClase", TipoTramite.class);
			q.setParameter("ttrClase", ttrClase);
			return q.getResultList();
		} catch (Exception e) {
			return null;
		}

	}

	public List<SelectItem> listarPorClase(String ttrClase) throws ServicioExcepcion {
		Map<String, Object> parametros = new HashMap<>();
		parametros.put("1", ttrClase);
		List<TipoTramite> listaTipoTramites = null;
		List<SelectItem> listaTipoTramitesItems = new ArrayList<>();
		listaTipoTramites = listarPorConsultaNativa("SELECT TipoTramite.* FROM TipoTramite "
				+ "WHERE TipoTramite.TtrClase = ?", parametros, TipoTramite.class);
		for (TipoTramite tipo : listaTipoTramites) {
			String aux = tipo.getTtrDescripcion();
			String descripcion = String.valueOf(aux);
			SelectItem selectItem = new SelectItem(tipo.getTtrId(), descripcion);
			listaTipoTramitesItems.add(selectItem);
		}
		return listaTipoTramitesItems;
	}
        
        public List<TipoTramite> tramiteUsuarioCarga(Date fechaIni, Date fechaFin) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        List<TipoTramite> lista = listarPorConsultaNativa("SELECT * "
                + "FROM "
                + "dbo.TramiteUsuario "
                + "INNER JOIN TramiteResponsable ON TramiteUsuario.TraNumero = TramiteResponsable.TraNumero "
                + "INNER JOIN TipoTramite on TramiteResponsable.TtrId = TipoTramite.TtrId "
                + "WHERE CONVERT(DATE, TramiteUsuario.TusFHR)>= ? AND CONVERT(DATE, TramiteUsuario.TusFHR)<= ? ",
                parametros, TipoTramite.class);
        return lista;
    }
}
