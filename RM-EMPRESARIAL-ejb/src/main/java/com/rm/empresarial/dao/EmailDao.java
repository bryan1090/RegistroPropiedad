/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Cuantia;
import com.rm.empresarial.modelo.Estado;
import com.rm.empresarial.modelo.HostMail;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TramiteAccion;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Marco
 */
@LocalBean
@Stateless
public class EmailDao extends Generico<HostMail> implements Serializable {

    public HostMail obtenerHostMail() throws ServicioExcepcion {
        String sql = "SELECT TOP 1 * FROM HostMail";
        HostMail hostMail = null;
        Map<String, Object> parametros = new HashMap<>();
        //parametros.put("HtmSmtp", "smtp.gmail.com");
        hostMail = (HostMail) obtenerPorConsultaNativa(sql, null, HostMail.class);
        return hostMail;

    }
    
    public HostMail buscarHostmail() throws ServicioExcepcion{
        HostMail hostMail =new HostMail();
         Query  q = getEntityManager().createNativeQuery("SELECT * FROM HostMail");
                  q.setMaxResults(1);
            return (HostMail) q.getSingleResult();
    }
}
