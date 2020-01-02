/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.dao;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.generico.Generico;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FormularioDigital;
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
import javax.persistence.TemporalType;

/**
 *
 * @author Prueba
 */
@LocalBean
@Stateless
public class FormularioDigitalDao extends Generico<FormularioDigital> implements Serializable {

    public List<FormularioDigital> listarTodo() throws ServicioExcepcion {
        return listarPorConsultaJpaNombrada(FormularioDigital.LISTAR_TODO, null);
    }

    public List<FormularioDigital> listarPorFechaInicioPorFechaFin(Date fechaInicio, Date fechaFin) throws ServicioExcepcion, ParseException {

        Map<String, Object> parametros = new HashMap<>();
        //parametros.put("1", fechaInicio);
        //parametros.put("2", fechaFin);
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        fechaInicio = df.parse(df.format(fechaInicio));
        fechaFin = df.parse(df.format(fechaFin));
        parametros.put("fechaInicio", fechaInicio);
        parametros.put("fechaFin", fechaFin);
        return listarPorConsultaJpaNombrada(FormularioDigital.BUSCAR_POR_FECHA_INICIO_FECHA_FIN, parametros);

//       List<FormularioDigital> lista = listarPorConsultaNativa("SELECT FormularioDigital.* FROM  FormularioDigital"
//                + " WHERE CONVERT(DATE,FormularioDigital.fmdFHR) = ? "
//                + " AND CONVERT(DATE,FormularioDigital.fmdFHR) = ?" , parametros,FormularioDigital.class);
//        return lista;   
    }

    public List<FormularioDigital> listarPorPorFecha(Date fecha) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        fecha = df.parse(df.format(fecha));
        parametros.put("1", fecha);

        String sql = "SELECT FormularioDigital.* FROM FormularioDigital"
                + " WHERE CONVERT(DATE,FormularioDigital.fmdFHR) = ?";

        List<FormularioDigital> lista = listarPorConsultaNativa(sql, parametros, FormularioDigital.class);
        return lista;
    }

    public List<FormularioDigital> listarPorPorFechaInicFechaFin(Date fechaInicial, Date fechaFinal) throws ServicioExcepcion, ParseException {
        Map<String, Object> parametros = new HashMap<>();
        DateFormat df = new SimpleDateFormat("yyyy/dd/MM");
        fechaInicial = df.parse(df.format(fechaInicial));
        fechaFinal = df.parse(df.format(fechaFinal));
        parametros.put("1", fechaInicial);
        parametros.put("2", fechaFinal);

        String sql = "SELECT FormularioDigital.* FROM FormularioDigital"
                + " WHERE CONVERT(DATE,FormularioDigital.fmdFHR) >= ?"
                + " AND CONVERT(DATE,FormularioDigital.fmdFHR) <= ?";

        List<FormularioDigital> lista = listarPorConsultaNativa(sql, parametros, FormularioDigital.class);
        return lista;
    }

    public List<FormularioDigital> listarNumeroFactura(String numeroFactura) throws ServicioExcepcion {

        Map<String, Object> parametros = new HashMap<>();

        parametros.put("numeroFactura", numeroFactura);

        return listarPorConsultaJpaNombrada(FormularioDigital.BUSCAR_POR_NUMERO_FACTURA, parametros);

    }

    public FormularioDigital PorNumeroFactura(String nFactura) throws ServicioExcepcion {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("1", nFactura);
        try {
            return (FormularioDigital) obtenerPorConsultaNativa("select TOP 1 * from FormularioDigital where FacId= (SELECT FacId FROM Factura WHERE Factura.FacNumero = ?  ) AND FmdEstado='A'", parametros, FormularioDigital.class);
        } catch (ServicioExcepcion ex) {
            return null;
        }
    }

}
