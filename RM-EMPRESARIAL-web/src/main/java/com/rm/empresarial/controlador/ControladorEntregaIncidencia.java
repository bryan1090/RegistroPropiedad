package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.Incidencia;
import com.rm.empresarial.modelo.IncidenciaHistorico;
import com.rm.empresarial.servicio.IncidenciaHistoricoServicio;
import com.rm.empresarial.servicio.IncidenciaServicio;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorEntregaIncidencia")
@ViewScoped
public class ControladorEntregaIncidencia implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @EJB
    private IncidenciaServicio servicioIncidencia;
    @EJB
    private IncidenciaHistoricoServicio servicioIncidenciaHistorico;

    @Getter
    @Setter
    private List<Incidencia> listaIncidenciasTerminadas;

    @Getter
    @Setter
    private Incidencia incidenciaSeleccionada;
    @Getter
    @Setter
    private IncidenciaHistorico incidenciaHistorico;

    @PostConstruct
    public void postControladorEntregaIncidencia() {
        listarIncidenciasTerminadas();
    }

    public ControladorEntregaIncidencia() {
        listaIncidenciasTerminadas = new ArrayList<>();
        incidenciaSeleccionada = new Incidencia();
        incidenciaHistorico = new IncidenciaHistorico();
    }

    public void listarIncidenciasTerminadas() {
        setListaIncidenciasTerminadas(servicioIncidencia.listarIncidenciasTerminadas());
    }

    public void entregarIncidencia() throws IOException {
        incidenciaHistorico = new IncidenciaHistorico(null, "Incidencia Entregada", dataManagerSesion.getUsuarioLogeado().getUsuLogin(), Calendar.getInstance().getTime());
        incidenciaHistorico.setIncId(incidenciaSeleccionada);
        incidenciaSeleccionada.setIncEstado("E");
        servicioIncidencia.edit(incidenciaSeleccionada);
        servicioIncidenciaHistorico.create(incidenciaHistorico);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/Incidencias/EntregadoIncidencia.xhtml");
    }

}
