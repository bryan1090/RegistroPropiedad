/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.cargaDiferida;

import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.servicio.ActaServicio;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

/**
 *
 * @author Bryan_Mora
 */
@Dependent
public class ModeloCargaDiferidaActa extends LazyDataModel<Acta> {

    @EJB
    private ActaServicio servicioActa;

    @Getter
    @Setter
    private Date fechaInicio;

    @Getter
    @Setter
    private Date fechaFin;
    @Getter
    @Setter
    private String tipoBusqueda;

    @Getter
    @Setter
    private Long numTramite;

    @Getter
    @Setter
    private Long numActa;

    @Getter
    @Setter
    private Long idTipoLibro;

    @Getter
    @Setter
    private BigInteger numInscripcion;

    public ModeloCargaDiferidaActa() {
        System.out.println("com.rm.empresarial.controlador.cargaDiferida.ModeloCargaDiferidaActa.<init>()");
        tipoBusqueda = "";

    }

    public void inicializar() {
        numInscripcion = null;
        idTipoLibro = null;
        numActa = null;
        numTramite = null;
        fechaInicio = null;
        fechaFin = null;
    }

    @PostConstruct
    public void postConstructor() {
        idTipoLibro = 0L;
        System.out.println("com.rm.empresarial.controlador.cargaDiferida.ModeloCargaDiferidaActa.postConstructor()");
//        this.setRowCount(servicioActa.numTotalRegistros());
    }

    @Override
    public List<Acta> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
        System.out.println("com.rm.empresarial.controlador.cargaDiferida.ModeloCargaDiferidaActa.load()");
        List<Acta> lista = new ArrayList<>();
        try {

            switch (tipoBusqueda) {
                case "F"://fechas
                    if (idTipoLibro == 0) {
                        lista = servicioActa.listarDiferidoPor_Fecha_SinFiltros(first, pageSize, fechaInicio, fechaFin);
                        this.setRowCount(servicioActa.numActasFirmadasPorFecha(fechaInicio, fechaFin));
                    } else {
                        lista = servicioActa.listarDiferidoPor_Fecha_Libro_SinFiltros(first, pageSize, fechaInicio, fechaFin, idTipoLibro);
                        this.setRowCount(servicioActa.numActasFirmadasPor_Fecha_Libro(fechaInicio, fechaFin, idTipoLibro));
                    }
//                    lista = servicioActa.listarDiferidoPor_Fecha_ConFiltrosDtb(first, pageSize, filters, fechaInicio, fechaFin);     
                    break;
                case "T"://tramite
                    lista = servicioActa.listarDiferidoPor_Tramite_SinFiltros(first, pageSize, numTramite);
                    this.setRowCount(servicioActa.numActasFirmadasPorTramite(numTramite));
//                    listaActasTabla = servicioActa.listarActasFirmadasPorTramite(numTramite);
                    break;
                case "A"://acta
                    lista.add(servicioActa.obtenerActaPorNumeroActa(numActa));
                    this.setRowCount(lista.size());
//                    listaActasTabla = servicioActa.listarActasFirmadasPorTramite(numTramite);
                    break;
                case "I"://inscripcion
                    if (idTipoLibro == 0) {
                        lista = servicioActa.listarDiferidoPor_Inscripcion_SinFiltros(first, pageSize, numInscripcion);
                        this.setRowCount(servicioActa.numActasFirmadasPor_Inscripcion(numInscripcion));
                    } else {
                        lista = servicioActa.listarDiferidoPor_Inscripcion_Libro_SinFiltros(first, pageSize, numInscripcion, idTipoLibro);
                        this.setRowCount(servicioActa.numActasFirmadasPor_Inscripcion_Libro(numInscripcion, idTipoLibro));
                    }

//                    listaActasTabla = servicioActa.listarActasFirmadasPorTramite(numTramite);
                    break;
                default:
                    break;

            }

            if (filters != null && filters.size() > 0) {
                //otherwise it will still show all page links; pages at end will be empty
//                this.setRowCount(servicioActa.numRegistrosConFiltros(filters));
            } else {
//                this.setRowCount(servicioActa.numTotalRegistros());
            }
            System.out.println("Mostrando " + lista.size() + " registros");

        } catch (Exception e) {
            System.out.println(e);
        }

        return lista;

    }

}
