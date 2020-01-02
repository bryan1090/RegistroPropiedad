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

/**
 *
 * @author Marco
 */
@LocalBean
@Stateless
public class InscripcionDao extends Generico<Repertorio> implements Serializable {

    public List<Repertorio> ListarPorFHRPorUsrLog(Date FHR, Long idUsuario) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        FHR = df.parse(df.format(FHR));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idUsuario);
        parametros.put("2", FHR);
        String sql = "SELECT "
                + " * "
                + "FROM "
                + "Repertorio r "
                + "INNER JOIN RepertorioUsuario ru ON ru.RepNumero = r.RepNumero "
                + "WHERE "
                + "ru.RpuTipo = 'INS' AND "
                + "ru.RpuEstado = 'A' AND "
                + "ru.UsuId = ? AND "
                + "CONVERT(DATE,r.RepFHR) = ? ";
        List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
    }

    public List<Repertorio> ListarRepertorioINP_PorFHRPorUsrLog(Date FHR, Long idUsuario) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        FHR = df.parse(df.format(FHR));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idUsuario);
        parametros.put("2", FHR);
        String sql = "SELECT "
                + " * "
                + "FROM "
                + "Repertorio r "
                + "INNER JOIN RepertorioUsuario ru ON ru.RepNumero = r.RepNumero "
                + "WHERE "
                + "ru.RpuTipo = 'INP' AND "
                + "ru.RpuEstado = 'A' AND "
                + "ru.UsuId = ? AND "
                + "CONVERT(DATE,r.RepFHR) = ? ";
        List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
    }

    public List<Repertorio> ListarRepertorioTipo_PorFHRPorUsrLog(Date FHR, Long idUsuario, String tipo) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        FHR = df.parse(df.format(FHR));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tipo);
        parametros.put("2", idUsuario);
        parametros.put("3", FHR);

        String sql = "SELECT "
                + " * "
                + "FROM "
                + "Repertorio r "
                + "INNER JOIN RepertorioUsuario ru ON ru.RepNumero = r.RepNumero "
                + "WHERE "
                + "ru.RpuTipo = ? AND "
                + "ru.RpuEstado = 'A' AND "
                + "ru.UsuId = ? AND "
                + "CONVERT(DATE,r.RepFHR) = ? ";
        List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
    }

    public List<Repertorio> ListarRepertorioPorNumTramiteYFecha(Date FHR, int numTramite) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        FHR = df.parse(df.format(FHR));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numTramite);
        parametros.put("2", FHR);

        String sql = "SELECT * FROM "
                + "Repertorio r "
                + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero "
                + "WHERE "
                + "t.TraEstado = 'FIN' AND t.TraNumero = ? AND CONVERT(DATE,r.RepFHR) = ? ";
        List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
    }
    
    public List<Repertorio> ListarRepertorioPorFecha(Date FHR) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        FHR = df.parse(df.format(FHR));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", FHR);

        String sql = "SELECT * FROM "
                + "Repertorio r "
                + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero "
                + "WHERE "
                + "t.TraEstado = 'FIN' AND CONVERT(DATE,r.RepFHR) = ? ";
        List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
    }
    
    public List<Repertorio> ListarRepertorioPorNumTramite(int numTramite) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", numTramite);

        String sql = "SELECT * FROM "
                + "Repertorio r "
                + "INNER JOIN Tramite t ON t.TraNumero = r.TraNumero "
                + "WHERE "
                + "t.TraEstado = 'FIN' AND t.TraNumero = ? ";
        List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
    }

    public List<Repertorio> ListarRepertorioTipo_PorFHRPorUsrLog_DatosPersona(Long idUsuario, String tipo, String identificacion, String apellidoPaterno, String apellidoMaterno, String nombre) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tipo);
        parametros.put("2", idUsuario);
        parametros.put("3", identificacion);
        parametros.put("4", apellidoPaterno);
        parametros.put("5", apellidoMaterno);
        parametros.put("6", nombre);

        String sql = "SELECT * "
                + "FROM Repertorio r "
                + "INNER JOIN RepertorioUsuario ru ON ru.RepNumero = r.RepNumero "
                + "INNER JOIN TramiteDetalle td ON td.TraNumero = r.TraNumero "
                + "WHERE "
                + "(ru.RpuTipo = ? AND "
                + "ru.RpuEstado = 'A' AND "
                + "ru.UsuId = ?) AND "
                + "td.TdtPerIdentificacion = ? OR td.TdtPerApellidoPaterno = ? OR td.TdtPerApellidoMaterno = ? OR td.TdtPerNombre = ? ";
        List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
    }

    public List<Repertorio> ListarRepertorioINA_PorFHRPorUsrLog(Date FHR, Long idUsuario) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        FHR = df.parse(df.format(FHR));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idUsuario);
        parametros.put("2", FHR);
        String sql = "SELECT "
                + " * "
                + "FROM "
                + "Repertorio r "
                + "INNER JOIN RepertorioUsuario ru ON ru.RepNumero = r.RepNumero "
                + "WHERE "
                + "ru.RpuTipo = 'INA' AND "
                + "ru.RpuEstado = 'A' AND "
                + "ru.UsuId = ? AND "
                + "CONVERT(DATE,r.RepFHR) = ? ";
        List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
    }

    public List<Repertorio> ListarRepertorioPorFHRPorUsrLog_tipo(Date FHR, Long idUsuario, String tipo) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        FHR = df.parse(df.format(FHR));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", tipo);
        parametros.put("2", idUsuario);
        parametros.put("3", FHR);
        String sql = "SELECT "
                + " * "
                + "FROM "
                + "Repertorio r "
                + "INNER JOIN RepertorioUsuario ru ON ru.RepNumero = r.RepNumero "
                + "WHERE "
                + "ru.RpuTipo = ? AND "
                + "ru.RpuEstado = 'A' AND "
                + "ru.UsuId = ? AND "
                + "CONVERT(DATE,r.RepFHR) = ? ";
        List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
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

    public Repertorio obtenerPorNumRepertorio(int repNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("repNumero", repNumero);
        return obtenerPorConsultaJpaNombrada(Repertorio.LISTAR_POR_REP_NUMERO, parametros);
    }

}
