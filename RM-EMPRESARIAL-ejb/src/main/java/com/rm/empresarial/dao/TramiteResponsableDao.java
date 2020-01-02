package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.TramiteResponsable;
import java.io.Serializable;
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
public class TramiteResponsableDao extends Generico<TramiteResponsable> implements Serializable{
    public List<TramiteResponsable> listarPorIdPersona(Long idPersona) {
        String sql = "SELECT * FROM TramiteResponsable WHERE PerId = ?";
        Query q = getEntityManager().createNativeQuery(sql, TramiteResponsable.class);
        q.setParameter("1", idPersona);          
        return q.getResultList();
    }
    	 public List<TramiteResponsable> tramitesUsu(Date fecha,int usu) {
        String sql = "SELECT *\n" +
"FROM Tramite\n" +
"INNER JOIN Notaria ON Notaria.NotId=Tramite.TraNotaria\n" +
"INNER JOIN Ciudad ON Ciudad.CiuId =Notaria.CiuId\n" +
"INNER JOIN TramiteResponsable ON TramiteResponsable.TraNumero=Tramite.TraNumero\n" +
"INNER JOIN Persona res ON res.PerId = TramiteResponsable.PerId\n" +
"INNER JOIN TipoTramite ON TipoTramite.TtrId = TramiteResponsable.TtrId\n" +
"INNER JOIN TramiteUsuario ON TramiteUsuario.TraNumero=Tramite.TraNumero\n" +
"WHERE CONVERT(DATE,TramiteUsuario.TusFHR)= ? AND TramiteUsuario.UsuId= ? \n" +
"ORDER BY TramiteUsuario.UsuId ";
        Query q = getEntityManager().createNativeQuery(sql, TramiteResponsable.class);
        q.setParameter("1", fecha);
        q.setParameter("2", usu);

        return q.getResultList();
    }
    
         public List<TramiteResponsable> tramiteUsuarioCarga(Date fechaIni, Date fechaFin) throws ServicioExcepcion{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
        List<TramiteResponsable> lista = listarPorConsultaNativa("SELECT * "
                + "FROM "
                + "dbo.TramiteUsuario "
                + "INNER JOIN TramiteResponsable ON TramiteUsuario.TraNumero = TramiteResponsable.TraNumero "
                + "INNER JOIN TipoTramite on TramiteResponsable.TtrId = TipoTramite.TtrId "
                + "WHERE CONVERT(DATE, TramiteUsuario.TusFHR)>= ? AND CONVERT(DATE, TramiteUsuario.TusFHR)<= ? ",
                parametros, TramiteResponsable.class);
        return lista;
    }
         
         
}
