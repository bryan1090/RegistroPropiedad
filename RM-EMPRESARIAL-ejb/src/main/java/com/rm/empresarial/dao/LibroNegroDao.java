package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.LibroNegro;
import java.io.Serializable;
import java.util.AbstractMap;
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
public class LibroNegroDao extends Generico<LibroNegro> implements Serializable {

    public LibroNegro buscarPorIdentificacion(String identificacion) throws ServicioExcepcion
    {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", identificacion);
            String sql = "SELECT TOP(1) * FROM LibroNegro l "
                    + "WHERE l.LbnPerIdentificacion= ? ";
            return (LibroNegro) obtenerPorConsultaNativa(sql, parametros, LibroNegro.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<LibroNegro> ListarLibroNegro(String identificacion) throws ServicioExcepcion {
        Map<String, Object> parametros=new HashMap<>();
        parametros.put("1", identificacion);
        String sql="SELECT * FROM LibroNegro ln WHERE ln.LbnPerIdentificacion= ? ";
        List<LibroNegro> listaLibroNegro = listarPorConsultaNativa(sql, parametros,LibroNegro.class); 
        return listaLibroNegro;
    }

    public void actualizarEstadoLibroNegro(LibroNegro cambioEstadoLibroNegro){
        edit(cambioEstadoLibroNegro);
    }
    
    public List<LibroNegro> ListarPrueba() throws ServicioExcepcion {
        String sql="SELECT * FROM Persona";
        Query q= (Query) (LibroNegro) getEntityManager().createNativeQuery(sql); 
        return q.getResultList();
    }
    
    
}

