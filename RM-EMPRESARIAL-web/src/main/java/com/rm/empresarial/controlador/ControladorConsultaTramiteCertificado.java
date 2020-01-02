package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.ControladorConsultaTramiteCertificadoBb;
import java.io.IOException;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.util.ArrayList;
import java.util.Date;
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
 * @author Dany
 */
@Named(value = "controladorConsultaTramiteCertificado")
@ViewScoped
public class ControladorConsultaTramiteCertificado implements Serializable {

    @Inject
    @Getter
    @Setter
    private ControladorConsultaTramiteCertificadoBb controladorConsultaTramiteCertificadoBb;

    @EJB
    private TramiteServicio servicioTramite;

    @EJB
    private CertificadoServicio servicioCertificado;

    @EJB
    private CertificadoCargaServicio servicioCertificadoCarga;

    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;

    @Getter
    @Setter
    private List<Tramite> listaTramitesBuscados;

    @Getter
    @Setter
    private List<CertificadoCarga> listaCertificadosBuscados;

    @Getter
    @Setter
    private Persona personaCompareciente;

    @Getter
    @Setter
    private Persona personaUsuario;

    @Getter
    @Setter
    private Persona personaFactura;

    @Getter
    @Setter
    private Date fechaInicio;

    @Getter
    @Setter
    private Date fechaFin;

    @Getter
    @Setter
    private Date fechaInicioC;

    @Getter
    @Setter
    private Date fechaFinC;

    @Getter
    @Setter
    private String filtroNumTramite;

    @Getter
    @Setter
    private String seleccion;

    public ControladorConsultaTramiteCertificado() {
        seleccion = "T";
        personaCompareciente = new Persona();
        personaUsuario = new Persona();
        personaFactura = new Persona();
        listaTramitesBuscados = new ArrayList<>();
        listaCertificadosBuscados = new ArrayList<>();
    }

    public void buscar() {
        switch (seleccion) {
            case "T":
                try {
                    listaTramitesBuscados = new ArrayList<>();
                    if (!personaCompareciente.getPerIdentificacion().isEmpty() || !personaCompareciente.getPerApellidoPaterno().isEmpty() || !personaCompareciente.getPerApellidoMaterno().isEmpty() || !personaCompareciente.getPerNombre().isEmpty()) {
                        setListaTramitesBuscados(servicioTramite.listarPorDatosCompareciente(personaCompareciente.getPerIdentificacion(), personaCompareciente.getPerNombre(), personaCompareciente.getPerApellidoPaterno(), personaCompareciente.getPerApellidoMaterno()));
                    } else if (fechaInicio != null && fechaFin != null) {
                        setListaTramitesBuscados(servicioTramite.listarPorRangoFecha(fechaInicio, fechaFin));
                    } else if (!filtroNumTramite.equals("")) {
                        setListaTramitesBuscados(servicioTramite.listarPorTramite(Long.valueOf(filtroNumTramite)));
                    } else {
                        JsfUtil.addErrorMessage("Ingrese almenos uno de los parametros de búsqueda");
                    }
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("No se encontró ningún trámite");
                    System.out.print("este es el error:" + e);
                }
                break;
            case "C":
                try {
                    listaCertificadosBuscados = new ArrayList<>();
                    if (!personaUsuario.getPerIdentificacion().isEmpty() || !personaUsuario.getPerApellidoPaterno().isEmpty() || !personaUsuario.getPerApellidoMaterno().isEmpty() || !personaUsuario.getPerNombre().isEmpty()) {
                        setListaCertificadosBuscados(servicioCertificadoCarga.listarPorDatosUsuario(personaUsuario.getPerIdentificacion(), personaUsuario.getPerApellidoPaterno(), personaUsuario.getPerApellidoMaterno(), personaUsuario.getPerNombre()));
                    } else if (!personaFactura.getPerApellidoPaterno().isEmpty() || !personaFactura.getPerApellidoMaterno().isEmpty() || !personaFactura.getPerNombre().isEmpty()) {
                        setListaCertificadosBuscados(servicioCertificadoCarga.listarPorDatosPersonaFactura(personaFactura.getPerApellidoPaterno(), personaFactura.getPerApellidoMaterno(), personaFactura.getPerNombre()));
                    } else if (fechaInicioC != null && fechaFinC != null) {
                        setListaCertificadosBuscados(servicioCertificadoCarga.listarPorRangoFecha(fechaInicioC, fechaFinC));
                    }
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("No se encontró ningún certificado");
                    System.out.print("este es el error:" + e);
                }
                break;
        }
    }

    public void verFlow() throws IOException {
        switch (seleccion) {
            case "T":
                if (controladorConsultaTramiteCertificadoBb.getTramiteSeleccionado() != null) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/seguimientosInscripciones/seguimientosInscripciones.xhtml?numTramite=" + controladorConsultaTramiteCertificadoBb.getTramiteSeleccionado().getTraNumero());
                } else {
                    System.out.print("Error al cargar FLOW");
                }
                break;
            case "C":
                if (controladorConsultaTramiteCertificadoBb.getCertificadoSeleccionado() != null) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/seguimientosInscripciones/seguimientosInscripciones.xhtml?numCertificado=" + controladorConsultaTramiteCertificadoBb.getCertificadoSeleccionado().getCdcDocumento());
                } else {
                    System.out.print("Error al cargar FLOW");
                }
                break;
        }
    }

}
