package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.CertificadoCargaUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.dao.CertificadoAccionDao;
import com.rm.empresarial.modelo.CertificadoAccion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
@Named(value = "controladorCertificadoCarga")
@ViewScoped
public class ControladorCertificadoCarga implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @EJB
    private CertificadoCargaServicio servicioCertificadoCarga;
    @EJB
    private FacturaServicio servicioFactura;
    @EJB
    private CertificadoAccionDao servicioCertificadoAccion;

    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;
    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;
    @Inject
    @Getter
    @Setter
    private CertificadoCargaUtil certificadoCargaUtil;

    @Getter
    @Setter
    private List<CertificadoCarga> listaCertificados;
    @Getter
    @Setter
    private List<CertificadoCarga> listaCertificadosRevisados;
    @Getter
    @Setter
    private List<String> listarNomFormulario;
    @Getter
    @Setter
    private List<Integer> listarNumCertificado;
    @Getter
    @Setter
    private CertificadoCarga certificadoCargaSeleccionado;
    @Getter
    @Setter
    private String estadoBtnTerminar;
    @Getter
    @Setter
    private String estadoBtnCheck;
    @Getter
    @Setter
    private Date fechaActual;
    @Getter
    @Setter
    private Date fechaBusqueda;
    @Getter
    @Setter
    private int numCertificado;

    public ControladorCertificadoCarga() {
        estadoBtnTerminar = "true";
        certificadoCargaSeleccionado = new CertificadoCarga();
        listaCertificados = new ArrayList<>();
        listarNomFormulario = new ArrayList();
        listaCertificadosRevisados = new ArrayList<>();
        listarNumCertificado = new ArrayList<>();
    }

    @PostConstruct
    public void postControladorCertificadoCarga() {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            fechaActual = df.parse(df.format(Calendar.getInstance().getTime()));
            listarCaertificado();
        } catch (ParseException e) {
        }
    }

    public void listarCaertificado() {
        setListaCertificados(servicioCertificadoCarga.listarCertificadosActivos(fechaActual));
        setListarNomFormulario(servicioCertificadoCarga.listarNomFormulario(fechaActual));
        setListarNumCertificado(servicioCertificadoCarga.listarnNumCertificado(fechaActual));
    }
    
    public void generarFormulario() {
        try {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("formularioWebId", numCertificado);
            reporteUtil.imprimirReporteEnPDF("RPC-01", "RPC-01" + numCertificado, parametros);
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se Pudo Descargar Formulario");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    public void revisarFormWeb() {
        try {
            getCertificadoCargaSeleccionado().setCdcEstado("I");
            servicioCertificadoCarga.edit(certificadoCargaSeleccionado);
            setEstadoBtnCheck("true");
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        try {
            Boolean cambiarEstado = false;
            //recorro certificadoCarga y verifico que todos tenga I para  habilitar cambiarEstado(true)
            for (CertificadoCarga certCarga : listaCertificados) {
                if (certCarga.getFacId().equals(certificadoCargaSeleccionado.getFacId())) {
                    cambiarEstado = certCarga.getCdcEstado().equals("I");
                }
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/ModuloWeb/CertificadoCarga.xhtml");
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void terminarFormWeb() {
        Factura facturaSeleccionada = certificadoCargaSeleccionado.getFacId();
        try {
            Boolean cambiarEstado = false;
            for (CertificadoCarga certCarga : listaCertificados) {
                if (certCarga.getFacId().equals(certificadoCargaSeleccionado.getFacId())) {
                    cambiarEstado = certCarga.getCdcEstado().equals("I");
                }
            }
            //certificadoCargaUtil.generarCertificadoCarga("CER", "certificado", getDataManagerSesion().getUsuarioLogeado().getUsuLogin());
            //CREO NUEVO CERTIFICADO CARGA SELECCIONADO
            //codigo CargaLaboral
            if (cambiarEstado) {
                setListaCertificadosRevisados(servicioCertificadoCarga.listarCertificadosPorNumFactura(certificadoCargaSeleccionado.getFacId().getFacId().intValue()));
                Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("CER", "certificado", listaCertificadosRevisados.size());
                for (int i = 0; i < listaCertificadosRevisados.size(); i++) {
                    CertificadoCarga nuevoCertSeleccionado = new CertificadoCarga();
                    nuevoCertSeleccionado = certificadoCargaSeleccionado;
                    nuevoCertSeleccionado.setCdcDocumento(getListaCertificadosRevisados().get(i).getCdcDocumento());
                    nuevoCertSeleccionado.setCdcTipo("CER");
                    nuevoCertSeleccionado.setCdcEstado("A");
                    nuevoCertSeleccionado.setCdcEstadoProceso("ACTIVO");
                    nuevoCertSeleccionado.setCdcUser(getDataManagerSesion().getUsuarioLogeado().getUsuLogin());
                    nuevoCertSeleccionado.setCdcFHR(Calendar.getInstance().getTime());
                    nuevoCertSeleccionado.setUsuId(usuarioAsignado);
                    nuevoCertSeleccionado.setCdcCantidadCertificado(getListaCertificadosRevisados().get(i).getCdcCantidadCertificado());
                    nuevoCertSeleccionado.setFacId(getListaCertificadosRevisados().get(i).getFacId());
                    servicioCertificadoCarga.create(nuevoCertSeleccionado);
                    generarCertificadoAccion(nuevoCertSeleccionado.getCdcDocumento(), "REVISIÓN DE CERTIFICADO N° "+numCertificado, "RFW", getCertificadoCargaSeleccionado().getUsuId().getUsuLogin());
                }
                facturaSeleccionada.setFacEstadoCertificado("C");
                servicioFactura.edit(facturaSeleccionada);
                FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/ModuloWeb/CertificadoCarga.xhtml");
            } else {
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Debe Completar los Certificados Pertenecientes a la Misma Factura");
                FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
            }
        } catch (Exception e) {
        }

    }

    public void generarCertificadoAccion(String numCertificado, String observacion, String estado, String nombreUsuario) {
        CertificadoAccion certificadoAccionNuevo = new CertificadoAccion(null,
                numCertificado, estado, "A", observacion, nombreUsuario, Calendar.getInstance().getTime());
        servicioCertificadoAccion.create(certificadoAccionNuevo);
    }

}
