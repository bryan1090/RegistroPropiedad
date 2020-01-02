/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FacturaDetalleImpuesto;
import com.rm.empresarial.modelo.Repertorio;

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
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class FacturaDao extends Generico<Factura> implements Serializable {

    public Factura encontrarFacturaPorNumeroTramite(int traNumero) throws ServicioExcepcion {

        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("facTraNumero", traNumero);
            return obtenerPorConsultaJpaNombrada(Factura.ENCONTRAR_POR_NUMERO_TRAMITE, parametros);
        } catch (Exception e) {
            return null;
        }

    }

    public Factura buscarPorComprobante(String numero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("facNumero", numero);

        return obtenerPorConsultaJpaNombrada(Factura.BUSCAR_POR_NUMERO_FACTURA, parametros);

    }

    public List<Factura> listarPorNVEAbierta(Date fecha, String facUser, String estado) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fecha);
        parametros.put("2", facUser);
        parametros.put("3", estado);
        List<Factura> lista = listarPorConsultaNativa("SELECT * FROM Factura INNER JOIN Caja ON Factura.CajId = Caja.CajId WHERE  CONVERT(DATE, Factura.FacFecha) = ? AND Factura.FacUser =?  AND (Factura.FacEstadoCierre = ? OR Factura.FacEstadoCierre is NULL AND Factura.FacTipo='NVE') ", parametros, Factura.class);
        return lista;
    }

    public List<Factura> listarPorNVECerrada(Date fecha, String facUser, String estado) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fecha);
        parametros.put("2", facUser);
        parametros.put("3", estado);
        List<Factura> lista = listarPorConsultaNativa("SELECT * FROM Factura INNER JOIN Caja ON Factura.CajId = Caja.CajId WHERE  CONVERT(DATE, Factura.FacFecha) = ? AND Factura.FacUser = ? AND Factura.FacEstadoCierre = ? AND Factura.FacTipo='NVE' ", parametros, Factura.class);
        return lista;
    }

    public List<Factura> listarPorFechaAbierta(Date fecha, String facUser, String estado) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fecha);
        parametros.put("2", facUser);
        parametros.put("3", estado);
        List<Factura> lista = listarPorConsultaNativa("SELECT * FROM Factura INNER JOIN Caja ON Factura.CajId = Caja.CajId WHERE  CONVERT(DATE, Factura.FacFecha) = ? AND Factura.FacUser =?  AND (Factura.FacEstadoCierre = ? OR Factura.FacEstadoCierre is NULL AND Factura.FacTipo='FAC') ", parametros, Factura.class);
        return lista;
    }

    public List<Factura> listarPorFecha(Date fecha, String facUser, String estado) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fecha);
        parametros.put("2", facUser);
        parametros.put("3", estado);
        List<Factura> lista = listarPorConsultaNativa("SELECT * FROM Factura INNER JOIN Caja ON Factura.CajId = Caja.CajId WHERE  CONVERT(DATE, Factura.FacFecha) = ? AND Factura.FacUser = ? AND Factura.FacEstadoCierre = ? AND Factura.FacTipo='FAC' ", parametros, Factura.class);
        return lista;
    }

    public List<Factura> listarValidar(Date fecha, String cajNombre, String estado) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fecha);
        parametros.put("2", cajNombre);
        parametros.put("3", estado);
        try {
            List<Factura> lista = listarPorConsultaNativa("SELECT * FROM Factura INNER JOIN Caja ON Factura.CajId = Caja.CajId WHERE  CONVERT(DATE, Factura.FacFecha) < ? AND Caja.CajNombre = ? AND (Factura.FacEstadoCierre = 'A' OR Factura.FacEstadoCierre is NULL) AND Factura.FacTipo='FAC' ", parametros, Factura.class);
            return lista;
        } catch (Exception e) {
            return null;
        }

    }

    public List<Factura> listarValidarNVE(Date fecha, String cajNombre, String estado) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fecha);
        parametros.put("2", cajNombre);
        parametros.put("3", estado);
        try {
            List<Factura> lista = listarPorConsultaNativa("SELECT * FROM Factura INNER JOIN Caja ON Factura.CajId = Caja.CajId WHERE  CONVERT(DATE, Factura.FacFecha) < ? AND Caja.CajNombre = ? AND (Factura.FacEstadoCierre = 'A' OR Factura.FacEstadoCierre is NULL) AND Factura.FacTipo='NVE' ", parametros, Factura.class);
            return lista;
        } catch (Exception e) {
            return null;
        }

    }

    public List<Factura> listarFactura(Date fechaInicio, Date fechaFin) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        fechaInicio = df.parse(df.format(fechaInicio));
        fechaFin = df.parse(df.format(fechaFin));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaInicio);
        parametros.put("2", fechaFin);
        return listarPorConsultaNativa("SELECT Factura.* FROM Factura "
                + "WHERE CONVERT(DATE, Factura.facFecha) >= ? "
                + "AND CONVERT(DATE, Factura.facFecha) <= ?", parametros, Factura.class);
    }

    public List<Factura> listarFacturaWeb(Date fechaInicio, Date fechaFin, String user) throws ServicioExcepcion, ParseException {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        fechaInicio = df.parse(df.format(fechaInicio));
        fechaFin = df.parse(df.format(fechaFin));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaInicio);
        parametros.put("2", fechaFin);
        parametros.put("3", user);
        return listarPorConsultaNativa("SELECT Factura.* FROM Factura "
                + "WHERE CONVERT(DATE, Factura.facFecha) >= ? "
                + "AND CONVERT(DATE, Factura.facFecha) <= ? AND FacPerIdentificacion = ? ", parametros, Factura.class);
    }

    public List<Factura> FacturaNumeroWeb(String facNumero, String user) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", facNumero);
        parametros.put("2", user);
        return listarPorConsultaNativa("SELECT * FROM Factura WHERE Factura.FacNumero = ? AND Factura.FacPerIdentificacion = ? ", parametros, Factura.class);
    }

    public List<Factura> listarPorComprobante(String numero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("facNumero", numero);

        return listarPorConsultaJpaNombrada(Factura.BUSCAR_POR_NUMERO_FACTURA, parametros);

    }

    public Factura buscarPorNumFactura(String facNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("facNumero", facNumero);
        return obtenerPorConsultaJpaNombrada(Factura.BUSCAR_POR_NUMERO_FACTURA, parametros);
    }

    public Factura buscarPorNumFacturaYcodigo(String facNumero, String facCodigoVerificacion) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", facNumero);
        parametros.put("2", facCodigoVerificacion);
        try {
            return (Factura) obtenerPorConsultaNativa("SELECT Factura.* FROM Factura "
                    + "WHERE Factura.FacNumero = ? AND Factura.FacCodigoVerificacion = ?", parametros, Factura.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

//    public List<Factura> listarPorEstadoPorFechaPorNumeroPorTipoCert(Date fecha, Long usuarioId, Long tipoCertificado) throws ParseException, ServicioExcepcion {
//
////        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
////        fecha = df.parse(df.format(fecha));
//        Map<String, Object> parametros = new HashMap<>();
//
//        parametros.put("1", tipoCertificado);
//        parametros.put("2", fecha);
//        parametros.put("3", usuarioId);
//        String sql = " SELECT Factura.* FROM Factura"
//                + " INNER JOIN Caja ON Factura.CajId = Caja.CajId"
//                + " WHERE Factura.FacTroId = ? "
//                + " AND Factura.FacEstado = 'A'"
//                + " AND Factura.FacEstadoCertificado = 'A'"
//                + " AND CONVERT(DATE, Factura.FacFHR) = ?"
//                + " AND Factura.FacNumero IN (SELECT CertificadoCarga.CdcDocumento"
//                + " FROM CertificadoCarga WHERE CertificadoCarga.CdcTipo = 'CER' "
//                + " AND CertificadoCarga.UsuId = ? "
//                + " AND CertificadoCarga.CdcEstado = 'A')";
//
//        List<Factura> lista = listarPorConsultaNativa(sql, parametros, Factura.class);
//        return lista;
//
//    }
    public List<Factura> buscarPorClaveAcceso() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Factura.BUSCAR_POR_CLAVE_ACCESO_NULL, null);
    }

    public List<Factura> reporteFactura(Date fechaIni, Date fechaFin) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        return listarPorConsultaNativa("SELECT *\n"
                + "FROM\n"
                + "dbo.Factura\n"
                + "INNER JOIN dbo.FacturaDetalle ON dbo.FacturaDetalle.FacId = dbo.Factura.FacId\n"
                + "WHERE Factura.FacTipo='FAC' AND CONVERT(DATE,Factura.FacFecha)>=?\n"
                + "AND CONVERT(DATE,Factura.FacFecha)<=? ", parametros, Factura.class);
    }

    public String generarSecuenciaFac(Long usuId) {
        String sql = "SELECT "
                + "SucSerie + '-' + CAST(CajEstablecimiento AS varchar(20)) + '-' + RIGHT('000000000' + Ltrim(Rtrim(CAST(CajSecuenciaFactura + 1 AS varchar(20)))),9) as NumeroFactura "
                + "FROM "
                + "Caja "
                + "INNER JOIN UsuarioCaja ON UsuarioCaja.CajId = Caja.CajId "
                + "INNER JOIN Sucursal ON Caja.SucId = Sucursal.SucId "
                + "WHERE "
                + "UsuarioCaja.UsuId = ? ";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", usuId);
        return (String) q.getSingleResult();
    }

    public void actualizarSecuenciaFac(Long usuId) {
        String sql = "UPDATE Caja SET Caja.CajSecuenciaFactura = (SELECT Caja.CajSecuenciaFactura + 1 FROM UsuarioCaja INNER JOIN Caja ON UsuarioCaja.CajId = Caja.CajId WHERE UsuarioCaja.UsuId = ? "
                + ") WHERE Caja.CajId = (SELECT Caja.CajId FROM UsuarioCaja INNER JOIN Caja ON UsuarioCaja.CajId = Caja.CajId WHERE UsuarioCaja.UsuId = ? "
                + ")";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", usuId);
        q.setParameter("2", usuId);
        q.executeUpdate();
    }

}
