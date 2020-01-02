/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.FormularioWeb;
import com.rm.empresarial.modelo.LibroNegro;
import com.rm.empresarial.modelo.TipoCertificado;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class TipoCertificadoDao extends Generico<TipoCertificado> implements Serializable {

    public List<TipoCertificado> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(TipoCertificado.LISTAR_TODOS, null);
    }

    public TipoCertificado valorCertificado(Long TroId) {
        
           try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", TroId);
            String sql = "SELECT TOP(1) * FROM TipoCertificado tf "
                    + "WHERE tf.TroId = ? ";
            return (TipoCertificado) obtenerPorConsultaNativa(sql, parametros, TipoCertificado.class);
        } catch (Exception e) {
                  System.out.println(e);
            return null;
        }

    
    }
    public TipoCertificado buscarPorNombre(String nombre) {
        
           try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("1", nombre);
            String sql = "SELECT TOP(1) * FROM TipoCertificado tf "
                    + "WHERE tf.TroNombre = ? ";
            return (TipoCertificado) obtenerPorConsultaNativa(sql, parametros, TipoCertificado.class);
        } catch (Exception e) {
                  System.out.println(e);
            return null;
        }

    
    }
    public TipoCertificado buscarPorTipo(String troTipo)
    {
        Query q;
        q=getEntityManager().createNamedQuery("TipoCertificado.findByTroTipo");
        q.setParameter("troTipo", troTipo);
        q.setMaxResults(1);
        return (TipoCertificado) q.getSingleResult();
    }
    
    public TipoCertificado buscarPor_Factura(String numFactura)
    {
        Query q;
        q=getEntityManager().createNativeQuery("Select tc.* from  TipoCertificado tc join Factura f on tc.TroId = f.FacTroId where f.FacNumero = ? ",TipoCertificado.class);
        q.setParameter("1", numFactura);
        q.setMaxResults(1);
        return (TipoCertificado) q.getSingleResult();
    }

}
