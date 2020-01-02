/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Alicuota;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.TmpAlicuota;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Java
 */
@LocalBean
@Stateless
public class TmpAlicuotaDao extends Generico<TmpAlicuota> implements Serializable {

    public TmpAlicuota encontrarTmpAlicuotaPorId(Long tmpAlicuotaId) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("talId", tmpAlicuotaId);
        return obtenerPorConsultaJpaNombrada(TmpAlicuota.BUSCAR_POR_ID, parametros);
    }
//public List<TmpAlicuota> listarPorMatriculaPadre_EstadoActivo(int matriculaPadre) throws ServicioExcepcion {
//        String sql = "SELECT * FROM TmpAlicuota WHERE TalMatricula = ? "
//                + " AND TalEstado = 'A' AND TaPrincipal <> 1";
//        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("1", matriculaPadre);
//        return listarPorConsultaNativa(sql, parametros, TmpAlicuota.class);
//    }
    public List<TmpAlicuota> listarPorMatriculaPadre_EstadoN_EstadoA(String matriculaPadre) throws ServicioExcepcion {
        String sql = "SELECT * FROM TmpAlicuota WHERE TalMatricula = ? "
                + " AND (TalEstado = 'N' OR TalEstado = 'A') AND TaPrincipal <> 1";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", matriculaPadre);
        return listarPorConsultaNativa(sql, parametros, TmpAlicuota.class);
    }
    public List<TmpAlicuota> listarPorMatriculaPadre_EstadoActivo_Bloque(String matriculaPadre, String bloqNombre) throws ServicioExcepcion {
        String sql = "SELECT * FROM TmpAlicuota WHERE TalMatricula = ? "
                + " AND (TalEstado = 'A' OR TalEstado = 'N') AND TaPrincipal <> 1"
                + " AND TalArea = ?";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", matriculaPadre);
        parametros.put("2", bloqNombre);
        return listarPorConsultaNativa(sql, parametros, TmpAlicuota.class);
    }
     

//    public List<TmpAlicuota> listarPorMatriculaPadre(int matriculaPadre) throws ServicioExcepcion {
//        String sql = "SELECT * FROM TmpAlicuota WHERE TalMatricula = ?"
//                + " AND (TalEstado = 'A' OR TalEstado = 'I')"
//                + " AND TaPrincipal <> 1";
//        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("1", matriculaPadre);
//        return listarPorConsultaNativa(sql, parametros, TmpAlicuota.class);
//    }     
    
    //ESTADO E: Eliminado 
    //ESTADO TP: ESTADO AUXILIAR PARA  LA TmpAlicuota con campo principal = 1 QUE SE CREA CUANDO SE CREA LA MATRICULA PH   
    public List<TmpAlicuota> listarPorMatriculaPadre_SinEstadoTP(String matriculaPadre) throws ServicioExcepcion {
        String sql = "SELECT * FROM TmpAlicuota WHERE TalMatricula = ?"
                + " AND (TalEstado <> 'TP' AND TalEstado <> 'E')";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", matriculaPadre);
        return listarPorConsultaNativa(sql, parametros, TmpAlicuota.class);
    }
    public List<TmpAlicuota> listarPorMatriculaPadre_EstadoDistintoE(String matriculaPadre) throws ServicioExcepcion {
        String sql = "SELECT * FROM TmpAlicuota WHERE TalMatricula = ?"
                + " AND TalEstado <> 'E'";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", matriculaPadre);
        return listarPorConsultaNativa(sql, parametros, TmpAlicuota.class);
    }
    public List<TmpAlicuota> listarPorIdAlicuotaAsociada(Long idAlicuota) throws ServicioExcepcion {
        String sql = "SELECT * FROM TmpAlicuota WHERE TalAltId = ?";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idAlicuota);
        return listarPorConsultaNativa(sql, parametros, TmpAlicuota.class);
    }

    public TmpAlicuota buscarPorIdAlicuotaAsociada(Long idAlicuota) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idAlicuota);
        try {
            return (TmpAlicuota) obtenerPorConsultaNativa("SELECT TOP 1 * FROM TmpAlicuota "
                    + " WHERE TalAltId = ?", parametros, TmpAlicuota.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public TmpAlicuota buscarAlicuotaUltimoSecuencial() throws ServicioExcepcion {
        try {
            return (TmpAlicuota) obtenerPorConsultaNativa("SELECT TOP 1 * FROM TmpAlicuota "
                    + " ORDER BY TalSecuencia DESC", null, TmpAlicuota.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }
    public List<TmpAlicuota> listarPorIdAlicuota_PorNumMatricAlicuota(String numMatricula) throws ServicioExcepcion {
        String sql = "SELECT * FROM TmpAlicuota, Alicuota "
                + " WHERE Alicuota.PrdMatricula = ? AND TmpAlicuota.TalAltId = Alicuota.AltId";
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numMatricula);
        return listarPorConsultaNativa(sql, parametros, TmpAlicuota.class);
    }   
    public TmpAlicuota buscarPorPredio_Catastro(String predio, String catastro) throws ServicioExcepcion {
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("1", predio);
      parametros.put("2", catastro);      
      try {
          return (TmpAlicuota) obtenerPorConsultaNativa("SELECT TOP 1 * FROM TmpAlicuota "
                  + " WHERE TalPredio = ? AND TalCatastro = ?", parametros, TmpAlicuota.class);
      } catch (ServicioExcepcion ex) {
          return null;
      }
    }
    public TmpAlicuota buscarPorPredio_Catastro_Principal(String predio, String catastro) throws ServicioExcepcion {
      Map<String, Object> parametros = new HashMap<>();
      parametros.put("1", predio);
      parametros.put("2", catastro);      
      try {
          return (TmpAlicuota) obtenerPorConsultaNativa("SELECT TOP 1 * FROM TmpAlicuota "
                  + " WHERE TalPredio = ? AND TalCatastro = ? AND TaPrincipal = 1", parametros, TmpAlicuota.class);
      } catch (ServicioExcepcion ex) {
          return null;
      }
    }
    

}
