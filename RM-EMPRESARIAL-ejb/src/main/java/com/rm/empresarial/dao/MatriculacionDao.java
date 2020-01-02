/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Tramite;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class MatriculacionDao extends Generico<Repertorio> implements Serializable {

    public List<RepertorioUsuario> ListarPorFHRPorUsrLog(Date FHR, Long idUsuario) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        FHR = df.parse(df.format(FHR));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idUsuario);
        parametros.put("2", FHR);
        String sql = "SELECT "
                + "ru.* "
                + "FROM "
                + "RepertorioUsuario ru "
                + "INNER JOIN Repertorio r ON ru.RepNumero = r.RepNumero "
                + "WHERE "
                + "ru.RpuTipo = 'MAT' AND "
                + "ru.RpuEstado = 'A' AND "
                + "ru.UsuId = ? AND "
                + "CONVERT(DATE,r.RepFHR) = ? ";
        List<RepertorioUsuario> lista = listarPorConsultaNativa(sql, parametros, RepertorioUsuario.class);
        return lista;
    }
    
    public List<RepertorioUsuario> ListarPorFHRPorUsrLogPorTipo(Date FHR, Long idUsuario, String tipo) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        FHR = df.parse(df.format(FHR));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tipo);
        parametros.put("2", idUsuario);
        parametros.put("3", FHR);
        
        String sql = "SELECT "
                + "ru.* "
                + "FROM "
                + "RepertorioUsuario ru "
                + "INNER JOIN Repertorio r ON ru.RepNumero = r.RepNumero "
                + "WHERE "
                + "ru.RpuTipo = ? AND "
                + "ru.RpuEstado = 'A' AND "
                + "ru.UsuId = ? AND "
                + "CONVERT(DATE,r.RepFHR) = ? ";
        List<RepertorioUsuario> lista = listarPorConsultaNativa(sql, parametros, RepertorioUsuario.class);
        return lista;
    }
    
   

    //(MAT,usrLogId,proceso) 
    public void actualizarEstadoProcesoRepUsu_proceso(Long numTramite,String tipoRepUsu, Long idUsuario ) {
        Query q;
        q = getEntityManager().createQuery("UPDATE RepertorioUsuario ru SET ru.rpuEstadoProceso='PROCESO' WHERE ru.rpuTipo=:tipoRepUsu AND ru.rpuEstado='A' AND ru.usuId=:idUsuario AND ru.repNumero.traNumero.traNumero=:");
        q.setParameter("tipoRepUsu", tipoRepUsu);

        q.setParameter("idUsuario", idUsuario);

    }

    public TramiteDetalle ObtenerPorTramite(int numTramite, int numRepertorio) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numTramite);
        parametros.put("2", numRepertorio);

        String sql = "SELECT TOP(1) "
                + "TramiteDetalle.TdtId, "
                + "TramiteDetalle.traNumero, "
                + "TramiteDetalle.TdtNumeroRepertorio, "
                + "TramiteDetalle.TdtFechaRegistro, "
                + "TramiteDetalle.TdtParNombre, "
                + "TramiteDetalle.TdtParId "
                + "FROM "
                + "TramiteDetalle "
                + "WHERE "
                + "TramiteDetalle.TraNumero = ?  AND TramiteDetalle.TdtNumeroRepertorio = ? ";
        TramiteDetalle TramiteDetalle = (TramiteDetalle) obtenerPorConsultaNativa(sql, parametros, TramiteDetalle.class);
        return TramiteDetalle;
    }

    public Parroquia ObtenerParroquiaPorId(Long parId) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", parId);

        String sql = "SELECT "
                + "p.ParNombre, "
                + "p.ParId "
                + "FROM "
                + "parroquia p "
                + "WHERE "
                + "p.ParId= ? ";
        Parroquia parroquiaSeleccionada = (Parroquia) obtenerPorConsultaNativa(sql, parametros, Parroquia.class);
        return parroquiaSeleccionada;
    }

    public List<Parroquia> ListarParroquia() throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();

        String sql = "SELECT "
                + "p.ParNombre, "
                + "p.ParId "
                + "FROM "
                + "Parroquia p ";
        return listarPorConsultaNativa(sql, parametros, Parroquia.class);
    }

    public void ActualizarParroquiaTramite(String parNombre, Long parId, int numRepertorio) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", parNombre);
        parametros.put("2", parId);
        parametros.put("3", numRepertorio);
        String sql = "UPDATE TramiteDetalle "
                + "SET TramiteDetalle.TdtParNombre = ? , TramiteDetalle.TdtParId = ? "
                + "WHERE TramiteDetalle.TdtNumeroRepertorio= ? ";
        Query q = getEntityManager().createQuery(sql, TramiteDetalle.class);
        q.executeUpdate();
    }

    public TramiteDetalle ObtenerTramitedetallePorNumRepertorio(Long numRepertorio) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numRepertorio);
        String sql = "SELECT TOP(1) * FROM TramiteDetalle WHERE TramiteDetalle.TdtNumeroRepertorio = ? ";
        return (TramiteDetalle) obtenerPorConsultaNativa(sql, parametros, TramiteDetalle.class);
    }

    public Repertorio obtenerPorNumRepertorio(int repNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("repNumero", repNumero);
        return obtenerPorConsultaJpaNombrada(Repertorio.LISTAR_POR_REP_NUMERO, parametros);
    }

    public Persona ObtenerPersonaPorIdUsuario(Long usuId) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);

        String sql = "SELECT * FROM Persona WHERE PerId = ?";
        Persona personaSeleccionada = (Persona) obtenerPorConsultaNativa(sql, parametros, Persona.class);
        return personaSeleccionada;
    }

    public Propiedad consultarExistenciaPropiedad(String catastro, String predio) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", catastro);
            parametros.put("2", predio);
            String sql = "SELECT * FROM Propiedad WHERE Propiedad.PrdCatastro= ? AND PrdPredio= ? ";
            return (Propiedad) obtenerPorConsultaNativa(sql, parametros, Propiedad.class);
        } catch (Exception e) {
            return null;
        }
    }
}
