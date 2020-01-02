package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Factura;
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
import static javax.xml.bind.DatatypeConverter.parseString;
import org.joda.time.DateTime;




@LocalBean
@Stateless
public class ImpresionDao extends Generico<Tramite> implements Serializable { 
    
        public  List<Tramite> listarTramite(){
        
            String sql = "select * from Tramite where TraEstadoRegistro = 'RA' AND TraEstado <> 'RVT'"
                
                ;
        Query q = getEntityManager().createNativeQuery(sql , Tramite.class);
        return q.getResultList();
    }   
      public  List<Tramite> listarTodo(){
        
            String sql = "select * from Tramite"
                
                ;
        Query q = getEntityManager().createNativeQuery(sql , Tramite.class);
        return q.getResultList();
    }   
      
        
       public List<Tramite> listarPorNumeroTramite(Long traNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        return listarPorConsultaJpa("SELECT t FROM Tramite t \n" +
                    "WHERE t.traEstado <> 'RVT' \n" +
                  "AND t.traEstadoRegistro= 'RA'\n" +
                "AND t.traNumero = :traNumero ", parametros);
    }
       public List<Tramite> listarPorTramite(Long traNumero) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("traNumero", traNumero);
        return listarPorConsultaJpa("SELECT t FROM Tramite t  " + 
                "WHERE t.traNumero = :traNumero ", parametros);
    }
       
         public List<Tramite> listarFecha(Date fechaIni,Date fechaFin) throws ServicioExcepcion, ParseException { 
             Map<String, Object> parametros = new HashMap<>();
             parametros.put("fechaIni", fechaIni);
             parametros.put("fechaFin", fechaFin);
        return listarPorConsultaJpa("SELECT t FROM Tramite t  " +
                                 " WHERE t.traEstado <> 'RVT'  " +
                                 " AND t.traEstadoRegistro= 'RA' " +
                                " AND t.traFechaReIngreso >= :fechaIni " + 
                              " AND t.traFechaReIngreso <= :fechaFin ", parametros);
    }
         public List<Tramite> listarFechaDiferente(Date fechaIni,Date fechaFin) throws ServicioExcepcion, ParseException { 
             Map<String, Object> parametros = new HashMap<>();
             parametros.put("fechaIni", fechaIni);
             parametros.put("fechaFin", fechaFin);
        return listarPorConsultaJpa("SELECT t FROM Tramite t  " +                             
                                " WHERE t.traFechaRecepcion >= :fechaIni " + 
                              " AND t.traFechaRecepcion <= :fechaFin ", parametros);
    }
        public List<Tramite> listarFechaIgual(Date fechaIni, Date fechaFin) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
         List<Tramite> lista = listarPorConsultaNativa("SELECT Tramite.* FROM Tramite "
                + " WHERE CONVERT(DATE, Tramite.TraFechaRecepcion) =  ? "
                + " AND CONVERT(DATE, Tramite.TraFechaRecepcion) =  ? " , parametros,Tramite.class);
        return lista; 
    }
       public List<Tramite> listarPorFecha(Date fechaIni, Date fechaFin) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", fechaIni);
        parametros.put("2", fechaFin);
         List<Tramite> lista = listarPorConsultaNativa("SELECT Tramite.* FROM Tramite"
                + " WHERE Tramite.TraEstado <> 'RVT' "
                + " AND Tramite.TraEstadoRegistro = 'RA' "
                + " AND CONVERT(DATE, Tramite.TraFechaReIngreso) =  ? "
                + " AND CONVERT(DATE, Tramite.TraFechaReIngreso) =  ? " , parametros,Tramite.class);
        return lista; 
    }
    
}
