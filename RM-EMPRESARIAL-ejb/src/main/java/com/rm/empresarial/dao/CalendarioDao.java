/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Calendario;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Query;

/**
 *
 * @author Bryan_Mora
 */
@LocalBean
@Stateless
public class CalendarioDao extends Generico<Calendario> implements Serializable {

    public List<Calendario> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(Calendario.LISTAR_TODO, null);
    }

    public Boolean esDiaLaborable(Date fecha) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        try {
            fecha =formatter.parse(formatter.format(fecha));
        } catch (ParseException ex) {
            Logger.getLogger(CalendarioDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        Query q;
        q = getEntityManager().createNamedQuery("Calendario.findByCldFecha");
        q.setParameter("cldFecha", fecha);
        q.setMaxResults(1);
        Calendario calendario = null;
        try {
            calendario = (Calendario) q.getSingleResult();
            if (calendario != null) {
                return calendario.getCldFestivo().equalsIgnoreCase("N");
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

}
