/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.CertificadoCarga;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class CertificadoCargaDao extends Generico<CertificadoCarga> implements Serializable {

    private static final long serialVersionUID = -5978514054808084896L;

    public List<CertificadoCarga> listarPorEstadoPorUsrLogPorTipo(Long idUsrLog, String tipo) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("usuId", idUsrLog);
        parametros.put("tipo", tipo);

        return listarPorConsultaJpa("SELECT c FROM CertificadoCarga c "
                + "WHERE c.usuId.usuId = :usuId "
                + "AND c.cdcTipo = :tipo "
                + "AND c.cdcEstado = 'A'", parametros);
    }

    public int actualizarPorEstadoPorUsrPorTipo(Long usuarioLogeado, String ccTipo) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("UPDATE CertificadoCarga r SET r.cdcEstado='I' WHERE r.usuId.usuId = :usuId AND r.cdcTipo = :ccTipo AND r.cdcEstado LIKE 'A'");
        q.setParameter("usuId", usuarioLogeado);
        q.setParameter("ccTipo", ccTipo);

        return q.executeUpdate();
    }

    public List<CertificadoCarga> listarPor_Usuario_TipoCertificado_Tipo_Fecha(BigDecimal usuId, Long facTroId, String tipoCertCarg, Date fecha) {
        Query q;
        q = getEntityManager().createNativeQuery("SELECT c.* from CertificadoCarga c "
                + "JOIN Usuario u on c.UsuId=u.UsuId "
                + "JOIN Factura f on c.FacId=f.FacId "
                + "WHERE CONVERT(DATE,c.CdcFHR) = ? AND c.usuId=? "
                + "AND f.FacTroId= ? AND c.cdcTipo = ? "
                + "AND c.cdcEstado='A'", CertificadoCarga.class);
        q.setParameter("1", fecha);
        q.setParameter("2", usuId);
        q.setParameter("3", facTroId);
        q.setParameter("4", tipoCertCarg);
        return q.getResultList();
    }

    public List<CertificadoCarga> listarPor_Usuario_TipoCertificadoEnFactura_TipoCertificadoCarga_Estado_EstadoProcesoActPrc(BigDecimal usuId, Long facTroId, String tipoCertCarg, String estado) {
        Query q;
        q = getEntityManager().createNativeQuery("SELECT c.* from CertificadoCarga c "
                + "JOIN Usuario u on c.UsuId=u.UsuId "
                + "JOIN Factura f on c.FacId=f.FacId "
                + "WHERE c.usuId=? "
                + "AND f.FacTroId= ? AND c.cdcTipo = ? "
                + "AND c.cdcEstado=? AND c.cdcEstadoProceso IN ('ACTIVO','PROCESO') ", CertificadoCarga.class);
        q.setParameter("1", usuId);
        q.setParameter("2", facTroId);
        q.setParameter("3", tipoCertCarg);
        q.setParameter("4", estado);
        return q.getResultList();
    }

    public List<CertificadoCarga> listarPor_Usuario_Factura_TipoCertificado_TipoCertificadoCarga_EstadoA(BigDecimal usuId, Long facTroId,String tipoCertificado, String tipoCertCarg) {
        Query q;
        q = getEntityManager().createNativeQuery("SELECT c.* from CertificadoCarga c "
                + "JOIN Usuario u on c.UsuId=u.UsuId "
                + "JOIN Factura f on c.FacId=f.FacId "
                + "JOIN TipoCertificado tc on tc.TroId=f.facTroId "
                + "WHERE c.usuId=? AND f.FacTroId= ? "
                + "AND tc.TroTipo= ?  AND c.cdcTipo = ? "
                + "AND c.cdcEstado='A' ", CertificadoCarga.class);
        q.setParameter("1", usuId);
        q.setParameter("2", facTroId);
        q.setParameter("3", tipoCertificado);
        q.setParameter("4", tipoCertCarg);

        return q.getResultList();
    }

//    public List<CertificadoCarga> listarPor_Usuario_Factura_TipoCertificadoNoG_Tipo_Fecha_EstadoA(BigDecimal usuId, Long facTroId, String tipoCertCarg, Date fecha) {
//        Query q;
//        q = getEntityManager().createNativeQuery("SELECT c.* from CertificadoCarga c "
//                + "JOIN Usuario u on c.UsuId=u.UsuId "
//                + "JOIN Factura f on c.FacId=f.FacId "
//                + "JOIN TipoCertificado tc on tc.TroId=f.facTroId "
//                + "WHERE c.usuId=? AND f.FacTroId= ? "
//                + "AND tc.TroTipo<>'G'  AND c.cdcTipo = ? "
//                + "AND CONVERT(DATE,c.CdcFHR) = ? "
//                + "AND c.cdcEstado='A' ", CertificadoCarga.class);
//        q.setParameter("1", usuId);
//        q.setParameter("2", facTroId);
//        q.setParameter("3", tipoCertCarg);
//        q.setParameter("4", fecha);
//
//        return q.getResultList();
//    }
    public List<CertificadoCarga> listarCertificadosActivos(Date fecha) {
        try {
            String sql = "SELECT c.* , fd.FdtTraNumero "
                    + "FROM "
                    + "CertificadoCarga c "
                    + "INNER JOIN FacturaDetalle fd ON c.FacId = fd.FacId "
                    + "INNER JOIN Factura f ON f.FacId = c.FacId "
                    + "INNER JOIN FormularioWeb fw ON fw.FlwId = fd.FdtTraNumero "
                    + "INNER JOIN TipoDocumentoWeb tp ON fw.TdwId=tp.TdwId "
                    + "WHERE "
                    + "f.FacEstadoCertificado='R' AND C.CdcTipo = 'RFW' AND CONVERT(DATE,c.CdcFHR)= ? "
                    + "ORDER BY c.FacId ASC";
            Query q = getEntityManager().createNativeQuery(sql, CertificadoCarga.class);
            q.setParameter("1", fecha);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> listarNomFormulario(Date fecha) {
        try {
            String sql = "SELECT tp.TdwNombre "
                    + "FROM "
                    + "CertificadoCarga c "
                    + "INNER JOIN FacturaDetalle fd ON c.FacId = fd.FacId "
                    + "INNER JOIN Factura f ON f.FacId = c.FacId "
                    + "INNER JOIN FormularioWeb fw ON fw.FlwId = fd.FdtTraNumero "
                    + "INNER JOIN TipoDocumentoWeb tp ON fw.TdwId=tp.TdwId "
                    + "WHERE "
                    + "f.FacEstadoCertificado='R' AND C.CdcTipo = 'RFW' AND CONVERT(DATE,c.CdcFHR)= ? "
                    + "ORDER BY c.FacId ASC";
            Query q = getEntityManager().createNativeQuery(sql);
            q.setParameter("1", fecha);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Integer> listarnNumCertificado(Date fecha) {
        try {
            String sql = "SELECT fd.FdtTraNumero "
                    + "FROM "
                    + "CertificadoCarga c "
                    + "INNER JOIN FacturaDetalle fd ON c.FacId = fd.FacId "
                    + "INNER JOIN Factura f ON f.FacId = c.FacId "
                    + "INNER JOIN FormularioWeb fw ON fw.FlwId = fd.FdtTraNumero "
                    + "INNER JOIN TipoDocumentoWeb tp ON fw.TdwId=tp.TdwId "
                    + "WHERE "
                    + "f.FacEstadoCertificado='R' AND C.CdcTipo = 'RFW' AND CONVERT(DATE,c.CdcFHR)= ? "
                    + "ORDER BY c.FacId ASC";
            Query q = getEntityManager().createNativeQuery(sql);
            q.setParameter("1", fecha);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<CertificadoCarga> listarCertificadosPorNumFactura(int numFactura) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", numFactura);
            String sql = "SELECT * FROM CertificadoCarga WHERE CdcEstado='I' AND CdcTipo='RFW' AND FacId= ?";
            return listarPorConsultaNativa(sql, parametros, CertificadoCarga.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<CertificadoCarga> listarCertifPorNumFacturaPorUsuId(BigDecimal numFactura, Long idUser) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", numFactura);
            parametros.put("2", idUser);
            String sql = "SELECT * FROM CertificadoCarga WHERE CdcTipo='DFC' AND FacId= ? AND UsuId = ?";
            return listarPorConsultaNativa(sql, parametros, CertificadoCarga.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<CertificadoCarga> listarPoDocumentoPorUsuId(Long idUsuario, String documento) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idUsuario);
        parametros.put("2", documento);

        String sql = "SELECT CertificadoCarga.* FROM CertificadoCarga"
                + " WHERE CertificadoCarga.usuId = ? "
                + " AND CertificadoCarga.cdcDocumento = ?";

        List<CertificadoCarga> lista = listarPorConsultaNativa(sql, parametros, CertificadoCarga.class);
        return lista;
    }

    public List<CertificadoCarga> listarPorTipoPorEstadoPorUsuId(Long idUsuario, String tipo) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idUsuario);
        parametros.put("2", tipo);

        String sql = "SELECT CertificadoCarga.* FROM CertificadoCarga"
                + " WHERE CertificadoCarga.usuId = ? "
                + " AND CertificadoCarga.cdcTipo = ?"
                + " AND CertificadoCarga.cdcEstado = 'A' ";

        List<CertificadoCarga> lista = listarPorConsultaNativa(sql, parametros, CertificadoCarga.class);
        return lista;
    }

    public List<CertificadoCarga> listarPorTipoPorEstadoPorUsuIdPorFecha(Long idUsuario, String tipo, Date fecha) throws ParseException, ServicioExcepcion {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        fecha = df.parse(df.format(fecha));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fecha);
        parametros.put("2", idUsuario);
        parametros.put("3", tipo);

        String sql = "SELECT cc.* FROM CertificadoCarga cc INNER JOIN Certificado c ON c.CerNumero = cc.CdcCertificado "
                + "WHERE CONVERT(DATE, c.CerFechaImpresion) = ? "
                + "AND cc.UsuId = ? "
                + "AND cc.CdcTipo = ? "
                + "AND cc.CdcEstado = 'A'";
        List<CertificadoCarga> lista = listarPorConsultaNativa(sql, parametros, CertificadoCarga.class);
        return lista;
    }

    public List<CertificadoCarga> listarUnicoPorTipoPorEstadoPorUsuIdPorFecha(Long idUsuario, String tipo, Date fecha) throws ParseException, ServicioExcepcion {
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        fecha = df.parse(df.format(fecha));
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", idUsuario);
        parametros.put("2", tipo);
        parametros.put("3", fecha);

        String sql = "SELECT * FROM CertificadoCarga"
                + " WHERE CertificadoCarga.usuId = ? "
                + " AND CertificadoCarga.cdcTipo = ?"
                + " AND CONVERT(DATE, CertificadoCarga.cdcFHR) = ?"
                + " AND CertificadoCarga.cdcEstado = 'A' ";
        List<CertificadoCarga> lista = listarPorConsultaNativa(sql, parametros, CertificadoCarga.class);
        return lista;
    }

    public List<CertificadoCarga> listarCertificadosRevisadosPor_NumFactura_Tipo(int numFactura, String tipo) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", tipo);
            parametros.put("2", numFactura);

            String sql = "SELECT * FROM CertificadoCarga WHERE CdcEstado='I' AND CdcTipo=? AND FacId= ?";
            return listarPorConsultaNativa(sql, parametros, CertificadoCarga.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<CertificadoCarga> listarCER(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<CertificadoCarga> lista = listarPorConsultaNativa("SELECT CertificadoCarga.* FROM CertificadoCarga WHERE CdcTipo='CER' AND CdcEstado='A' AND UsuId = ? ",
                parametros, CertificadoCarga.class);
        return lista;
    }

    public List<CertificadoCarga> listarFechaETC(Date fechaIni, Date fechaFin, Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        parametros.put("3", usuId);
        List<CertificadoCarga> lista = listarPorConsultaNativa("SELECT * from CertificadoCarga WHERE CONVERT(DATE, CdcFHR)>= ? AND  CONVERT(DATE, CdcFHR)<= ? AND UsuId= ? AND CdcTipo='ETC' AND CdcEstado='A' ",
                parametros, CertificadoCarga.class);
        return lista;
    }

    public List<CertificadoCarga> listarETC(String certificado, Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", certificado);
        parametros.put("2", usuId);
        List<CertificadoCarga> lista = listarPorConsultaNativa("SELECT * from CertificadoCarga where CdcCertificado= ? AND CdcTipo='ETC' AND UsuId= ? AND CdcEstado='A' ",
                parametros, CertificadoCarga.class);
        return lista;
    }

    public Long numCerPendientesPor_usuario_tipo_estado_fecha(Long usuId, String tipo, Date fecha) {
        Calendar fechaInicioHoy = Calendar.getInstance();
        Calendar fechaFinHoy = Calendar.getInstance();

        fechaInicioHoy.setTime(fecha);
        fechaFinHoy.setTime(fecha);

        fechaInicioHoy.set(Calendar.HOUR_OF_DAY, 0);
        fechaInicioHoy.set(Calendar.MINUTE, 0);
        fechaInicioHoy.set(Calendar.SECOND, 0);

        fechaFinHoy.set(Calendar.HOUR_OF_DAY, 23);
        fechaFinHoy.set(Calendar.MINUTE, 59);
        fechaFinHoy.set(Calendar.SECOND, 59);

        Query q;
        q = getEntityManager().createQuery("SELECT COUNT(c.cdcId) FROM CertificadoCarga c WHERE c.cdcTipo = :tipo AND c.usuId.usuId = :usuId AND (c.cdcFHR BETWEEN :fechaInicioHoy AND :fechaFinHoy) AND c.cdcEstado = 'A' AND c.cdcEstadoProceso <> 'TERMINADO'");
        q.setParameter("usuId", usuId);
        q.setParameter("tipo", tipo);
        q.setParameter("fechaInicioHoy", fechaInicioHoy.getTime());
        q.setParameter("fechaFinHoy", fechaFinHoy.getTime());
//
        return (Long) q.getSingleResult();
    }

//    public Long numCerRealizadosPor_usuario_tipo_estado_fecha(Long usuId, String tipo, Date fecha) {
//        Calendar fechaInicioHoy = Calendar.getInstance();
//        Calendar fechaFinHoy = Calendar.getInstance();
//
//        fechaInicioHoy.setTime(fecha);
//        fechaFinHoy.setTime(fecha);
//
//        fechaInicioHoy.set(Calendar.HOUR_OF_DAY, 0);
//        fechaInicioHoy.set(Calendar.MINUTE, 0);
//        fechaInicioHoy.set(Calendar.SECOND, 0);
//
//        fechaFinHoy.set(Calendar.HOUR_OF_DAY, 23);
//        fechaFinHoy.set(Calendar.MINUTE, 59);
//        fechaFinHoy.set(Calendar.SECOND, 59);
//
//        Query q;
//        q = getEntityManager().createQuery("SELECT COUNT(c.cdcId) FROM CertificadoCarga c WHERE c.cdcTipo = :tipo AND c.usuId.usuId = :usuId AND (c.cdcFHR BETWEEN :fechaInicioHoy AND :fechaFinHoy) AND c.cdcEstado = 'A' AND c.cdcEstadoProceso ='TERMINADO'");
//        q.setParameter("usuId", usuId);
//        q.setParameter("tipo", tipo);
//        q.setParameter("fechaInicioHoy", fechaInicioHoy.getTime());
//        q.setParameter("fechaFinHoy", fechaFinHoy.getTime());
////
//        return (Long) q.getSingleResult();
//    }
    public Long numCerPendientesPor_usuario_tipo_estado_fecha_Activo(Long usuId, String tipo, Date fecha, Boolean activo) {
        Calendar fechaInicioHoy = Calendar.getInstance();
        Calendar fechaFinHoy = Calendar.getInstance();

        fechaInicioHoy.setTime(fecha);
        fechaFinHoy.setTime(fecha);

        fechaInicioHoy.set(Calendar.HOUR_OF_DAY, 0);
        fechaInicioHoy.set(Calendar.MINUTE, 0);
        fechaInicioHoy.set(Calendar.SECOND, 0);

        fechaFinHoy.set(Calendar.HOUR_OF_DAY, 23);
        fechaFinHoy.set(Calendar.MINUTE, 59);
        fechaFinHoy.set(Calendar.SECOND, 59);

        Query q;

        if (activo) {
            q = getEntityManager().createQuery("SELECT COUNT(c.cdcId) FROM CertificadoCarga c WHERE c.cdcTipo = :tipo AND c.usuId.usuId = :usuId AND (c.cdcFHR BETWEEN :fechaInicioHoy AND :fechaFinHoy) AND c.cdcEstado = 'A' AND c.cdcEstadoProceso <> 'TERMINADO' AND c.cdcActivo=true");
        } else {
            q = getEntityManager().createQuery("SELECT COUNT(c.cdcId) FROM CertificadoCarga c WHERE c.cdcTipo = :tipo AND c.usuId.usuId = :usuId AND (c.cdcFHR BETWEEN :fechaInicioHoy AND :fechaFinHoy) AND c.cdcEstado = 'A' AND c.cdcEstadoProceso <> 'TERMINADO' AND c.cdcActivo IS NULL");
        }

        q.setParameter("usuId", usuId);
        q.setParameter("tipo", tipo);
        q.setParameter("fechaInicioHoy", fechaInicioHoy.getTime());
        q.setParameter("fechaFinHoy", fechaFinHoy.getTime());
//           q.setParameter("activo", activo);

//
        return (Long) q.getSingleResult();
    }

    public Long numCerPendientesPor_Usuario_tipo_estado_estadoProceso_activo(Long usuId, String tipo,String estado, List<String> estadoProceso,Boolean activo) {
        Query q;
        if (activo) {
            q = getEntityManager().createQuery("SELECT COUNT(c.cdcId) FROM CertificadoCarga c WHERE c.cdcTipo = :tipo AND c.usuId.usuId = :usuId AND c.cdcEstado = :estado AND c.cdcEstadoProceso IN :estadoProceso AND c.cdcActivo=true");
        } else {
            q = getEntityManager().createQuery("SELECT COUNT(c.cdcId) FROM CertificadoCarga c WHERE c.cdcTipo = :tipo AND c.usuId.usuId = :usuId  AND c.cdcEstado = :estado AND c.cdcEstadoProceso IN :estadoProceso AND c.cdcActivo IS NULL");
        }

        q.setParameter("usuId", usuId);
        q.setParameter("tipo", tipo);
        q.setParameter("estado", estado);
        q.setParameter("estadoProceso", estadoProceso);
        
        return (Long) q.getSingleResult();
    }

    public List<CertificadoCarga> listarDFC(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        List<CertificadoCarga> lista = listarPorConsultaNativa("SELECT CertificadoCarga.* FROM CertificadoCarga WHERE CdcTipo='DFC' AND CdcEstado='A' AND UsuId = ? ",
                parametros, CertificadoCarga.class);
        return lista;
    }

    public int actualizar(Long cdcId) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createQuery("UPDATE CertificadoCarga c SET c.cdcEstado = 'I' WHERE c.cdcId = :cdcId ");
        q.setParameter("cdcId", cdcId);
        return q.executeUpdate();
    }

    public CertificadoCarga buscarCertCargPor_Certificado(String cdcCertificado) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT c FROM CertificadoCarga c WHERE c.cdcCertificado = :cdcCertificado ");
            q.setParameter("cdcCertificado", cdcCertificado);
            q.setMaxResults(1);
            return (CertificadoCarga) q.getSingleResult();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public List<CertificadoCarga> listarPorUsuId(Long usuId) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", usuId);
        return listarPorConsultaNativa("SELECT CertificadoCarga.* FROM CertificadoCarga "
                + "WHERE CertificadoCarga.usuId = ?", parametros, CertificadoCarga.class);
    }

    public CertificadoCarga buscarPorNumFactura(String cdcDocumento) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", cdcDocumento);
        try {
            return (CertificadoCarga) obtenerPorConsultaNativa("SELECT TOP 1 CertificadoCarga.* FROM CertificadoCarga "
                    + "WHERE CertificadoCarga.CdcDocumento = ? "
                    + "ORDER BY CertificadoCarga.CdcId DESC", parametros, CertificadoCarga.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

    public List<CertificadoCarga> listarPorDatosUsuario(String identificacion, String apellidoPaterno, String apellidoMaterno, String nombre) {
        if (identificacion.equals("")) {
            identificacion = identificacion + "^";
        }
        if (nombre.equals("")) {
            nombre = nombre + "^";
        }
        if (apellidoPaterno.equals("")) {
            apellidoPaterno = apellidoPaterno + "^";
        }
        if (apellidoMaterno.equals("")) {
            apellidoMaterno = apellidoMaterno + "^";
        }
        String sql = "SELECT * "
                + "FROM CertificadoCarga cc "
                + "INNER JOIN Usuario u ON cc.UsuId = u.UsuId "
                + "INNER JOIN Persona p ON u.PerId = p.PerId "
                + "WHERE "
                + "p.PerIdentificacion = ? OR "
                + "p.PerApellidoPaterno = ? OR "
                + "p.PerApellidoMaterno = ? OR "
                + "p.PerNombre = ?";
        Query q = getEntityManager().createNativeQuery(sql, CertificadoCarga.class);
        q.setParameter("1", identificacion);
        q.setParameter("2", apellidoPaterno);
        q.setParameter("3", apellidoMaterno);
        q.setParameter("4", nombre);
        return q.getResultList();
    }

    public List<CertificadoCarga> listarPorDatosPersonaFactura(String apellidoPaterno, String apellidoMaterno, String nombre) {
        if (nombre.equals("")) {
            nombre = nombre + "^";
        }
        if (apellidoPaterno.equals("")) {
            apellidoPaterno = apellidoPaterno + "^";
        }
        if (apellidoMaterno.equals("")) {
            apellidoMaterno = apellidoMaterno + "^";
        }
        String sql = "SELECT * FROM CertificadoCarga cc "
                + "INNER JOIN Factura f ON cc.FacId = f.FacId "
                + "WHERE f.FacCertificadoPrimerApe = ? OR "
                + "f.FacCertificadoSegundoApe = ? OR "
                + "f.FacCertificadoNombre = ? ";
        Query q = getEntityManager().createNativeQuery(sql, CertificadoCarga.class);
        q.setParameter("1", apellidoPaterno);
        q.setParameter("2", apellidoMaterno);
        q.setParameter("3", nombre);
        return q.getResultList();
    }

    public List<CertificadoCarga> listarPorRangoFecha(Date fechaInicio, Date fechaFin) {
        String sql = "SELECT * FROM CertificadoCarga "
                + "WHERE CONVERT(DATE,CdcFHR) >= ? AND CONVERT(DATE,CdcFHR) <= ?";
        Query q = getEntityManager().createNativeQuery(sql, CertificadoCarga.class);
        q.setParameter("1", fechaInicio);
        q.setParameter("2", fechaFin);
        return q.getResultList();
    }
}
