/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Cuantia;
import com.rm.empresarial.modelo.TipoTramite;
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
 * @author Wilson
 */
@LocalBean
@Stateless
public class CuantiaDao extends Generico<Cuantia> implements Serializable {
    
    private static final long serialVersionUID = -107424063735029733L;
    
    public List<Cuantia> buscarPorTraId(TipoTramite tipoTramite) throws ServicioExcepcion{
        List<Cuantia> cuantia=null;
         Map<String, Object> parametros = new HashMap<>();
        parametros.put("ttrId", tipoTramite);
       return  cuantia=listarPorConsultaJpaNombrada(Cuantia.BUSCAR_POR_TRA_ID, parametros);
       
    }
      public List<Cuantia> ListarTodos() throws ServicioExcepcion{
          return listarPorConsultaJpaNombrada(Cuantia.LISTAR_TODOS, null);
      }
      
     public BigDecimal valorCuantia(BigDecimal Valor) {
        Query q;
        BigDecimal CuaValorAplica = BigDecimal.ZERO;
        try {
            q = getEntityManager().createNativeQuery("SELECT (Cuantia.CuaValorAplica + ((Cuantia.CuaValorAplica * IsNull(Cuantia.CuaPorcentaje, 0)) /100)) FROM Cuantia WHERE ? BETWEEN Cuantia.CuaValorInicial AND Cuantia.CuaValorFinal");
            q.setParameter("1", Valor);

            CuaValorAplica = new BigDecimal(q.getSingleResult().toString());

            return CuaValorAplica;

        } catch (Exception e) {
            BigDecimal saldo = BigDecimal.ZERO;
            BigDecimal porcentaje = BigDecimal.ZERO;
            BigDecimal porcentajeAdministracion = BigDecimal.ZERO;
            
            Cuantia cuantia = new Cuantia();
            
            q = getEntityManager().createNativeQuery("SELECT TOP 1 * FROM dbo.Cuantia WHERE dbo.Cuantia.CuaValorFinal = (SELECT Max(dbo.Cuantia.CuaValorFinal) FROM dbo.Cuantia)", Cuantia.class);
            cuantia = (Cuantia) q.getSingleResult();
            porcentajeAdministracion = cuantia.getCuaPorcentaje();
            
            q = getEntityManager().createNativeQuery("SELECT C.ConfigDetalleNumero FROM ConfigDetalle C WHERE C.ConfigDetalleTexto = 'PORCENTAJEEXEDECUANTIA'");
            porcentaje = new BigDecimal(q.getSingleResult().toString());
            
            porcentaje = porcentaje.divide(BigDecimal.valueOf(100));
            porcentajeAdministracion = porcentajeAdministracion.divide(BigDecimal.valueOf(100));
            
            saldo = Valor.subtract(cuantia.getCuaValorFinal());
            
            CuaValorAplica = CuaValorAplica.add(cuantia.getCuaValorAplica().add(saldo.multiply(porcentaje)));
            CuaValorAplica = CuaValorAplica.add(CuaValorAplica.multiply(porcentajeAdministracion));
            return CuaValorAplica ;
        }
    }
    
}
