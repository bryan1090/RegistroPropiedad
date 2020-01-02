package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.modelo.FormularioWeb;
import com.rm.empresarial.servicio.FormularioWebServicio;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorFormularioWeb")
@ViewScoped
public class ControladorFormularioWeb implements Serializable {

    @EJB
    private FormularioWebServicio servicioFormularioWeb;
    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;

    @Getter
    @Setter
    private List<FormularioWeb> listaFormularioWeb;

    @Getter
    @Setter
    private String numeroFormulario;
    @Getter
    @Setter
    private Boolean estadoRenderFiltro = Boolean.FALSE;
    @Getter
    @Setter
    private Date fechaInicio;

    @Getter
    @Setter
    private Date fechaFin;

    @Getter
    @Setter
    private FormularioWeb formularioSeleccionado;

    @PostConstruct
    public void postControladorFormularioWeb() {
        listaFormularioWeb = new ArrayList<>();
        formularioSeleccionado = new FormularioWeb();
    }

    public ControladorFormularioWeb() {

    }

    public void buscarFormularioWeb() {
        try {
            if (!getEstadoRenderFiltro()) {
                setListaFormularioWeb(servicioFormularioWeb.buscarFormularioPorFecha(fechaInicio,fechaFin));
            } else {
                setListaFormularioWeb(servicioFormularioWeb.buscarPorNumeroFormulario(Integer.parseInt(numeroFormulario)));
            }
        } catch (NumberFormatException e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error en el Parámetro de Búsqueda");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    public void generarFormulario() throws IOException, JRException, NamingException, SQLException {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("formularioWebId", getFormularioSeleccionado().getFlwId());
            reporteUtil.imprimirReporteEnPDF(getFormularioSeleccionado().getTdwId().getTdwNombre(), getFormularioSeleccionado().getTdwId().getTdwNombre() + "_" + getFormularioSeleccionado().getFlwId(), parametros);
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se Pudo Descargar Formulario");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }

    }

    public void busquedaPorFactura() {
        setEstadoRenderFiltro(Boolean.TRUE);
        setNumeroFormulario(null);
        setListaFormularioWeb(null);
    }

    public void busquedaPorFechas() {
        setEstadoRenderFiltro(Boolean.FALSE);
        setListaFormularioWeb(null);
        setFechaInicio(null);
        setFechaFin(null);
    }
}
