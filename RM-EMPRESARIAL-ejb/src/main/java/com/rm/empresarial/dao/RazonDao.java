/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Repertorio;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class RazonDao extends Generico<Repertorio> implements Serializable  {
    
    
    public List<Repertorio> listaRepertorioPorFHRPorUsr(Date FHR, Long idUser) {
        try {
            Map<String, Object> parametros = new HashMap<>();
            String sql = "SELECT * FROM Repertorio INNER JOIN RepertorioUsuario"
                    + " ON RepertorioUsuario.RepNumero = Repertorio.RepNumero" 
                    + " WHERE RepertorioUsuario.RpuTipo = 'RAZ' AND RepertorioUsuario.UsuId = ?"
                    + " AND CONVERT(DATE,RepertorioUsuario.RpuFHR) = ? "
                    + " AND RepertorioUsuario.RpuEstado = 'A'";
            parametros.put("1", idUser);
            parametros.put("2", FHR);
            List<Repertorio> lista = listarPorConsultaNativa(sql, parametros, Repertorio.class);
        return lista;
           

        } catch (Exception e) {
            return null;
        }

    }
    
}
