/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Tramite;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora modificado por Wilson Herrera
 */
@LocalBean
@Stateless
public class TramiteDao extends Generico<Tramite> implements Serializable {

    private static final long serialVersionUID = 7588074984585083686L;

    public List<Tramite> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Tramite.LISTAR_TODOS, null);
    }

    public Tramite buscarTramitePorNumero(Long tramite) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", tramite);
        try {
            return obtenerPorConsultaJpaNombrada(Tramite.BUSCAR_POR_NUMERO_TRAMITE, parametros);
        } catch (ServicioExcepcion e) {
            e.printStackTrace();
        }
        return null;
    }

    public Tramite buscarTramitePor_numero_estadoRI(Long tramite) {
        try {
            Query q;
            q = getEntityManager().createQuery("SELECT t FROM Tramite t WHERE t.traNumero= :tramite AND t.traEstadoRegistro = 'RI'");
            q.setParameter("tramite", tramite);
            q.setMaxResults(1);
            return (Tramite) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Tramite buscarTramitePor_numero_estado_Susp(Long tramite) {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM Tramite t WHERE t.traNumero= :tramite AND t.traEstado IN ('PRO','RVT')");
        q.setParameter("tramite", tramite);
        q.setMaxResults(1);
        return (Tramite) q.getSingleResult();
    }

    public Tramite buscarTramitePor_numero_estado_Insc(Long tramite) {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM Tramite t WHERE t.traNumero= :tramite AND t.traEstado IN ('INS')");
        q.setParameter("tramite", tramite);
        q.setMaxResults(1);
        return (Tramite) q.getSingleResult();
    }

    public Tramite buscarTramitePor_numero_estado_Insc_INP(Long tramite) {
        Query q;
        q = getEntityManager().createQuery("SELECT t FROM Tramite t WHERE t.traNumero= :tramite ");
        q.setParameter("tramite", tramite);
        q.setMaxResults(1);
        return (Tramite) q.getSingleResult();
    }

    public Tramite guardarTramite(Tramite tramite) throws ServicioExcepcion {

        guardarSalida(tramite);
        Tramite salida = find(tramite, tramite.getTraNumero());
        System.out.print("Salida  " + salida);
        return salida;
    }

    public List<Tramite> buscarTramitePorEstado(String estado) throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createNamedQuery("Tramite.buscarPorTraEstado");
        q.setParameter("traEstado", estado);
        return q.getResultList();
    }
    public List<Tramite> buscarTramitePorVariosEstados() throws ServicioExcepcion {
        Query q;
        q = getEntityManager().createNamedQuery("Tramite.buscarPorTraEstadoVarios");        
        return q.getResultList();
    }

    public List<Tramite> buscarTramitePorEstadoYporEstadoRegistro(String estado, String estadoRegistro) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traEstado", estado);
        parametros.put("traEstadoRegistro", estadoRegistro);
        List<Tramite> listaTramites = listarPorConsultaJpaNombrada(Tramite.BUSCAR_POR_ESTADO_Y_ESTADO_REGISTRO, parametros);
        for (Tramite tram : listaTramites) {
            System.out.println("tram: " + tram.getTraNumero() + " estado: " + tram.getTraEstado());
        }
        return listaTramites;
    }

    public List<Tramite> buscarTramitePorEstadoYporEstadoRegistro2(String estado, String estadoRegistro) throws ServicioExcepcion {
        Query q = getEntityManager().createQuery("SELECT t FROM Tramite t WHERE t.traEstado=:traEstado AND t.traEstadoRegistro=:traEstadoRegistro");
        q.setParameter("traEstado", estado);
        q.setParameter("traEstadoRegistro", estadoRegistro);

        List<Tramite> listaTramites = q.getResultList();

        for (Tramite tram : listaTramites) {
            System.out.println("tram: " + tram.getTraNumero() + " estado: " + tram.getTraEstado());
        }
        return listaTramites;
    }

    public Persona obtenerPersonaConNumeroTramite(Long traNumero, String estado) {
        Query q = getEntityManager().createNativeQuery("SELECT Persona.* FROM Tramite "
                + "INNER JOIN  TramiteUsuario ON  Tramite.TraNumero =  TramiteUsuario.TraNumero "
                + "INNER JOIN  Usuario ON  TramiteUsuario.UsuId =  Usuario.UsuId "
                + "INNER JOIN  Persona ON  Usuario.PerId = Persona.PerId "
                + " WHERE Tramite.TraNumero=? AND Tramite.TraEstado=? AND Tramite.TraEstadoRegistro='RI'", Persona.class);
        q.setParameter("1", traNumero);
        q.setParameter("2", estado);

        try {
            return (Persona) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> ObtenerTramiteNotaria(String sql) throws ServicioExcepcion, ParseException {
        try {
            Query query = getEntityManager().createNativeQuery(sql);

            List<String> resultado = new ArrayList<>();
            for (int i = 0; i < query.getResultList().size(); i++) {
                if(query.getResultList().get(i) != null){
                     resultado.add(query.getResultList().get(i).toString());
                }
               
            }

            return resultado;

        } catch (RuntimeException e) {
            throw new ServicioExcepcion(e);            
            
        }
    }

//    public List<Object[]> buscarTramiteJoinCaja() throws ServicioExcepcion {
//        Query q = getEntityManager().createQuery("SELECT t.traNumero,f.facTraNumero FROM Tramite t INNER JOIN Factura f on t.traNumero=f.facTraNumero");
//        List<Object[]> resultado = q.getResultList();
//        for (Object[] obj : resultado) {
//            System.out.println("**objeto: " + obj.toString());
//            System.out.println("atributo: " + (Long) obj[0]);
//            System.out.println("atributo: " + (Integer) obj[1]);
//        }
//        return null;
//    }
    public List<Tramite> listarPorRangoFecha(Date fechaIni, Date fechaFin) {
        String sql = "SELECT * FROM Tramite WHERE CONVERT(DATE,TraFechaRecepcion) >= ? AND CONVERT(DATE,TraFechaRecepcion) <= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", fechaIni);
        q.setParameter("2", fechaFin);
        return q.getResultList();
    }

    public List<Tramite> listarPorFecha(Date fechaTramite) {
        String sql = "SELECT * FROM Tramite WHERE CONVERT(DATE,TraFechaRecepcion) = ?";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", fechaTramite);
        return q.getResultList();
    }

    public List<Tramite> listaTramitePorUsuario(Long UsuId) {
        String sql = "SELECT * FROM Tramite t "
                + "INNER JOIN Repertorio r ON r.TraNumero = t.TraNumero "
                + "INNER JOIN RepertorioUsuario ru ON ru.RepNumero = r.RepNumero "
                + "WHERE "
                + "t.TraEstado = 'INS' AND T.TraEstadoRegistro = 'RZ' AND ru.UsuId = ?";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", UsuId);
        return q.getResultList();
    }

    public List<Tramite> listaTramitePor_Usuario_Estado(Long UsuId, String estado) {
        String sql = "SELECT DISTINCT(t.TraNumero) FROM Tramite t "
                + "INNER JOIN Repertorio r ON r.TraNumero = t.TraNumero "
                + "INNER JOIN RepertorioUsuario ru ON ru.RepNumero = r.RepNumero "
                + "WHERE "
                + "t.TraEstado = ? AND ru.UsuId = ?";
        Query q = getEntityManager().createNativeQuery(sql);
        q.setParameter("1", estado);
        q.setParameter("2", UsuId);
        String idsTramite = "";
        int contador=0;
        for (Object object : q.getResultList()) {
            Integer idTramite = (Integer) object;
            idsTramite += idTramite;
            if(contador!=q.getResultList().size()-1)
            {
                idsTramite+=",";
            }
                contador++;
        }
        System.out.println("ids:"+idsTramite);
        if (!idsTramite.isEmpty()) {
            String sql2 = "SELECT t.* FROM Tramite t WHERE t.TraNumero IN(" + idsTramite + ")";
            Query q2 = getEntityManager().createNativeQuery(sql2, Tramite.class);
            q2.setParameter("2", UsuId);
            return q2.getResultList();
        } else {
            return new ArrayList();
        }

    }
    
    public List<Tramite> listaTramitePor_Usuario_VariosEstados(Long UsuId) {
        String sql = "SELECT DISTINCT(t.TraNumero) FROM Tramite t "
                + "INNER JOIN Repertorio r ON r.TraNumero = t.TraNumero "
                + "INNER JOIN RepertorioUsuario ru ON ru.RepNumero = r.RepNumero "
                + "WHERE t.TraEstadoRegistro = 'RZ' AND "
                + "t.TraEstado IN ('INS','INP','INC','IND','INA') AND ru.UsuId = ?";
        Query q = getEntityManager().createNativeQuery(sql);        
        q.setParameter("1", UsuId);
        String idsTramite = "";
        int contador=0;
        for (Object object : q.getResultList()) {
            Integer idTramite = (Integer) object;
            idsTramite += idTramite;
            if(contador!=q.getResultList().size()-1)
            {
                idsTramite+=",";
            }
                contador++;
        }
        System.out.println("ids:"+idsTramite);
        if (!idsTramite.isEmpty()) {
            String sql2 = "SELECT t.* FROM Tramite t WHERE t.TraNumero IN(" + idsTramite + ")";
            Query q2 = getEntityManager().createNativeQuery(sql2, Tramite.class);
            q2.setParameter("2", UsuId);
            return q2.getResultList();
        } else {
            return new ArrayList();
        }

    }


    public List<Tramite> listarPorTramite(Long traNumero) {
        String sql = "SELECT * FROM Tramite WHERE traNumero= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", traNumero);

        return q.getResultList();
    }

    public List<Tramite> listarPorNombrePersona_ApellidoPaterno_ApellidoMaterno(String nombre, String apellidoPaterno, String apelllidoMaterno) {
        String sql = "SELECT * FROM Tramite WHERE TraPerNombre= ? AND TraPerApellidoPaterno = ? AND TraPerApellidoMaterno = ? ";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", nombre);
        q.setParameter("2", apellidoPaterno);
        q.setParameter("3", apelllidoMaterno);
        return q.getResultList();
    }

    public List<Tramite> listarPorDatosCompareciente(String identificacion, String nombre, String apellidoPaterno, String apelllidoMaterno) {
        String sql = "SELECT * FROM Tramite t "
                + "INNER JOIN TramiteDetalle td ON td.TraNumero = t.TraNumero "
                + "WHERE td.TdtPerIdentificacion = ? OR "
                + "td.TdtPerApellidoPaterno = ? OR "
                + "td.TdtPerApellidoMaterno = ? OR "
                + "td.TdtPerNombre = ?";
        if (identificacion.equals("")) {
            identificacion = identificacion + "^";
        }
        if (nombre.equals("")) {
            nombre = nombre + "^";
        }
        if (apellidoPaterno.equals("")) {
            apellidoPaterno = apellidoPaterno + "^";
        }
        if (apelllidoMaterno.equals("")) {
            apelllidoMaterno = apelllidoMaterno + "^";
        }
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", identificacion);
        q.setParameter("2", apellidoPaterno);
        q.setParameter("3", apelllidoMaterno);
        q.setParameter("4", nombre);
        return q.getResultList();
    }

    public List<Tramite> listarPorTramite1(Long traNumero) {
        String sql = "SELECT  * FROM Tramite WHERE traNumero= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", traNumero);

        return q.getResultList();
    }

    public List<Tramite> listarPorRangoNumero(int numTramiteIni, int numTramiteFin) {
        String sql = "SELECT * FROM Tramite WHERE TraNumero >= ? AND TraNumero <= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", numTramiteIni);
        q.setParameter("2", numTramiteFin);
        return q.getResultList();
    }

    public List<Tramite> listarPorEstadoRegistro(Long numTramite) {
        String sql = "SELECT * FROM Tramite WHERE TraNumero= ? AND TraEstadoRegistro = 'RZ' ";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", numTramite);
        return q.getResultList();
    }

    public List<Tramite> listarPorUsuario(int perId) {
        String sql = "SELECT * FROM Tramite "
                + "INNER JOIN TramiteDetalle ON Tramite.TraNumero = TramiteDetalle.TraNumero  "
                + "WHERE TramiteDetalle.PerId= ? ";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", perId);

        return q.getResultList();
    }

    public List<Tramite> tramitesUsu(Date fecha, int usu) {
        String sql = "SELECT * \n"
                + "FROM Tramite\n"
                + "INNER JOIN Notaria ON Notaria.NotId=Tramite.TraNotaria\n"
                + "INNER JOIN Ciudad ON Ciudad.CiuId =Notaria.CiuId\n"
                + "INNER JOIN TramiteResponsable ON TramiteResponsable.TraNumero=Tramite.TraNumero\n"
                + "INNER JOIN Persona res ON res.PerId = TramiteResponsable.PerId\n"
                + "INNER JOIN TipoTramite ON TipoTramite.TtrId = TramiteResponsable.TtrId\n"
                + "INNER JOIN TramiteUsuario ON TramiteUsuario.TraNumero=Tramite.TraNumero\n"
                + "WHERE CONVERT(DATE,TramiteUsuario.TusFHR)= ? AND TramiteUsuario.UsuId= ? \n"
                + "ORDER BY TramiteUsuario.UsuId ";
        Query q = getEntityManager().createNativeQuery(sql, Tramite.class);
        q.setParameter("1", fecha);
        q.setParameter("2", usu);

        return q.getResultList();
    }

}
