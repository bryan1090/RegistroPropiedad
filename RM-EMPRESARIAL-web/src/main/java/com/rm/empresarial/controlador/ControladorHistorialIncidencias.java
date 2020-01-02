package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.Incidencia;
import com.rm.empresarial.modelo.IncidenciaAuditoria;
import com.rm.empresarial.modelo.IncidenciaHistorico;
import com.rm.empresarial.servicio.IncidenciaAuditoriaServicio;
import com.rm.empresarial.servicio.IncidenciaHistoricoServicio;
import com.rm.empresarial.servicio.IncidenciaServicio;
import com.sun.org.apache.xpath.internal.operations.Bool;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorHistorialIncidencias")
@ViewScoped
public class ControladorHistorialIncidencias implements Serializable {

    @EJB
    private IncidenciaServicio servicioIncidencia;
    @EJB
    private IncidenciaAuditoriaServicio servicioIncidenciaAuditoria;
    @EJB
    private IncidenciaHistoricoServicio servicioIncidenciaHistorico;

    @Getter
    @Setter
    private List<Incidencia> listaIncidencias;
    @Getter
    @Setter
    private List<IncidenciaAuditoria> listaIncidenciaAuditoria;
    @Getter
    @Setter
    private List<IncidenciaHistorico> listaIncidenciaHistorico;

    @Getter
    @Setter
    private Incidencia incidenciaSeleccionada;

    @Getter
    @Setter
    private String incidenciaIni;
    @Getter
    @Setter
    private String incidenciaFin;
    @Getter
    @Setter
    private Date fechaIni;
    @Getter
    @Setter
    private Date fechaFin;
    @Getter
    @Setter
    private Boolean filtro = Boolean.FALSE;

    @PostConstruct
    public void postControladorHistorialIncidencias() {

    }

    public ControladorHistorialIncidencias() {
        listaIncidencias = new ArrayList<>();
        listaIncidenciaAuditoria = new ArrayList<>();
        listaIncidenciaHistorico = new ArrayList<>();

        incidenciaSeleccionada = new Incidencia();
    }

    public void listarIncidenciasPorNumIncidencias() {
        setListaIncidencias(servicioIncidencia.listarIncidenciasPorRangoNumIncidencia(Integer.parseInt(incidenciaIni), Integer.parseInt(incidenciaFin)));
    }

    public void listarIncidenciasPorFecha() {
        setListaIncidencias(servicioIncidencia.listarIncidenciasPorRangoFecha(fechaIni, fechaFin));
    }

    public void busquedaPornumIncidencia() {
        incidenciaIni = new String();
        incidenciaFin = new String();
        listaIncidencias = new ArrayList<>();
        setFiltro(Boolean.TRUE);
    }

    public void busquedaPorFechas() {
        setFechaIni(null);
        setFechaFin(null);
        listaIncidencias = new ArrayList<>();
        setFiltro(Boolean.FALSE);
    }

    public void listarHistorialIncidencia() {
        setListaIncidenciaHistorico(servicioIncidenciaHistorico.listarPorIdIncidencia(incidenciaSeleccionada));
        setListaIncidenciaAuditoria(servicioIncidenciaAuditoria.listarPorIdIncidencia(incidenciaSeleccionada));
    }

}
