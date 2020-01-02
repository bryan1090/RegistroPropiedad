/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.rm.empresarial.controlador.util.FooterPdfEvent;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.PdfTempUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.dao.CertificadoAccionDao;
import com.rm.empresarial.dao.CertificadoDao;
import com.rm.empresarial.dao.FacturaDao;
import com.rm.empresarial.dao.FacturaDetalleDao;
import com.rm.empresarial.dao.FormularioDigitalDao;
import com.rm.empresarial.dao.FormularioWebDao;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.dao.TramiteAccionDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoAccion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FormularioDigital;
import com.rm.empresarial.modelo.FormularioWeb;
import com.rm.empresarial.modelo.Margen;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.MargenServicio;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorDocumento")
@ViewScoped
public class ControladorDocumento implements Serializable {

    @Getter
    @Setter
    private Long nTramiteIni;
    @Getter
    @Setter
    private Long nTramiteFin;

    @Getter
    @Setter
    private Long nTramite;

    @Getter
    @Setter
    private String traNumero;

    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;
    @EJB
    private ParametroPathServicio servicioParametroPath;
    @Getter
    @Setter
    private String numeroDocumento;
    @Getter
    @Setter
    private String url;
    @Getter
    @Setter
    private List<Certificado> seleccionCertificado;
    @Getter
    @Setter
    private List<FacturaDetalle> listaFacturaDetalle;

    @Getter
    @Setter
    private String dirCarpetaTemp;
    @Getter
    @Setter
    private ParametroPath parametroPath;

    @EJB
    private TramiteServicio servicioTramite;
    @Getter
    @Setter
    private FormularioDigital formularioDigitalSeleccionado;

    @Getter
    @Setter
    private FormularioWeb formularioWeb;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private PersonaServicio servicioPersona;

    @Inject
    @Getter
    @Setter
    private ControladorGenerarRide controladorGenerarRide;

    @EJB
    private PersonaDao personaDao;
    @Getter
    @Setter
    private Boolean filtro = Boolean.FALSE;

    @Getter
    @Setter
    private Date fechaIni;
    @Getter
    @Setter
    private Date fechaFin;
    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    @EJB
    private FacturaDetalleDao facturaDetalleDao;
    @EJB
    private TramiteAccionDao tramiteAccionDao;
    @EJB
    private CertificadoDao certificadoDaoo;
    @EJB
    private FormularioDigitalDao formularioDigitalDao;
    @EJB
    private FacturaDao facturaDao;

    @EJB
    private FormularioWebDao formularioWebDao;

    @EJB
    private CertificadoAccionDao certificadoDao;

    @Getter
    @Setter
    private TramiteAccion tramiteAccionSeleccionado;

    @Getter
    @Setter
    private FacturaDetalle facturaDetalleSeleccionada;

    @Getter
    @Setter
    private Factura FacturaSeleccionado;

    @Getter
    @Setter
    private FormularioWeb formularioSeleccionado;
    @Getter
    @Setter
    private Certificado certificadoSeleccionado;

    @Getter
    @Setter
    private CertificadoAccion documentoSeleccionado;

    @Getter
    @Setter
    private List<TramiteAccion> listaTramiteAccion;
    @Getter
    @Setter
    private List<CertificadoAccion> listaCertificadoAccion;

    @Getter
    @Setter
    private List<FormularioWeb> listaFormularioWeb;

    @Getter
    @Setter
    private List<FormularioWeb> tramiteSeleccionado;

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private List<Tramite> listaTramites;

    @Getter
    @Setter
    private List<Tramite> listaPrueba;

    @Getter
    @Setter
    private Margen margen;
    @EJB
    private TramiteDao tramiteDao;

    @EJB
    private MargenServicio servicioMargen;

    @Inject
    private PdfTempUtil pdfTempUtil;

    @Getter
    @Setter
    private Boolean filtro2 = Boolean.TRUE;

    @Getter
    private TreeNode arbol;

    @Getter
    @Setter
    private TreeNode nodoSeleccionado;

    @Getter
    @Setter
    private Tramite traSeleccionado;

    @Inject
    DataManagerSesion dataManagerSesion;

    public ControladorDocumento() {
        setFechaIni(Calendar.getInstance().getTime());
        setFechaFin(Calendar.getInstance().getTime());
    }

    @PostConstruct
    public void postConstructor() {
        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Long numTramite = new Long(params.get("paramTramite").toString());
        setListaPrueba(tramiteDao.listarPorTramite(numTramite));
        setTraNumero(numTramite.toString());

        try {
            cargarArbolTramite(numTramite);
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorDocumento.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listarTramitePorUsuario() {
        setListaTramites(tramiteDao.listarPorUsuario((int) dataManagerSesion.getUsuarioLogeado().getPerId().getPerId().longValue()));
    }

    public void clear() {
        setListaFormularioWeb(null);
    }

    public void busquedaPorTramite() {
        setFiltro(Boolean.TRUE);
    }

    public void busquedaPorFecha() {
        setFiltro(Boolean.FALSE);
    }

    public void MostarArbol() {
        setFiltro2(Boolean.FALSE);
    }

    public void listarFormulario() {
        setListaFormularioWeb(formularioWebDao.listarPorUsuario(dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
    }

    public void listarFormularioWeb() {
        setListaFormularioWeb(formularioWebDao.listarPorTramiteRango(getNTramiteIni(), getNTramiteFin(), dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
    }

    public void listarPorFecha() throws ServicioExcepcion, ParseException {
        if (getFechaIni().equals(getFechaFin())) {

            setListaFormularioWeb(formularioWebDao.listarPorFecha(getFechaIni(), getFechaFin(), dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
        } else {
            setListaFormularioWeb(formularioWebDao.listarFecha(getFechaIni(), getFechaFin(), dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
        }

    }

    public void listarFacturaDeralle() {
        setListaFacturaDetalle(facturaDetalleDao.listarPorNumtramite(getFormularioSeleccionado().getFlwId().longValue()));
    }

    public void listarCertificadoAccion() {
        setListaCertificadoAccion(certificadoDao.listarCertificado("%" + getFacturaDetalleSeleccionada().getFacId().getFacNumero() + "%"));
    }

    public void onNodeSelect(NodeSelectEvent event) throws ServicioExcepcion {
        String[] parts = event.getTreeNode().toString().split("[: -]+");
        setListaTramiteAccion(null);
        switch (getNodoSeleccionado().getType()) {
            case "rfw":
                setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%" + getNumeroDocumento() + "%", "RFW"));
                break;
            case "caj":
                setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%" + getNumeroDocumento() + "%", "CAJ"));
                break;
            case "cer":
                setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%" + getNumeroDocumento() + "%", "CER"));
                break;
            case "dfc":
                setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%" + getNumeroDocumento() + "%", "DFC"));
                break;
            case "imc":
                setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%" + getNumeroDocumento() + "%", "IMC"));
                break;
            default:
                break;
        }

    }

    public void NodeSelect() throws ServicioExcepcion {
        switch (getNodoSeleccionado().getType()) {
            case "mat":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "MAT"));
                break;
            case "caj":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "CAJ"));
                break;
            case "inc":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "INC"));
                break;
            case "ins":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "INS"));
                break;
            case "pro":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "PRO"));
                break;
            case "imp":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "IMP"));
                break;
            case "rvt":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "RVT"));
                break;
            case "rep":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "REP"));
                break;
            case "mrg":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "MRG"));
                break;
            case "dfc":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "DIG"));
                break;
            case "fme":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "FME"));
                break;
            case "fin":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "FIN"));
                break;
            case "mph":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "MPH"));
                break;
            case "etg":
                setListaTramiteAccion(tramiteAccionDao.listarProceso(getTraNumero(), "ETG"));
                break;
            default:
                break;
        }

    }

    public void cargarArbol() throws ServicioExcepcion {
        String pattern = "dd/MM/yyyy ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String documento = String.valueOf(numeroDocumento);
        if (certificadoDao.buscarDocumento(documento) != null) {
            arbol = new DefaultTreeNode("Root", null);
            setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%" + getNumeroDocumento() + "%", "CAJ"));
            if (getDocumentoSeleccionado() != null) {
                TreeNode nodo2 = new DefaultTreeNode("caj", "Caja", arbol);
                nodo2.getChildren().add(new DefaultTreeNode("caj", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento() + "-CAJ", nodo2));
                nodo2.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                nodo2.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                nodo2.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
            }
            setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%" + getNumeroDocumento() + "%", "DFC"));
            if (getDocumentoSeleccionado() != null) {
                TreeNode nodo1 = new DefaultTreeNode("dfc", "Digitalizacion Formulario", arbol);
                nodo1.getChildren().add(new DefaultTreeNode("dfc", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento() + "-DFC", nodo1));
                nodo1.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                nodo1.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                nodo1.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
            }
            setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%" + getNumeroDocumento() + "%", "RFW"));
            if (getDocumentoSeleccionado() != null) {
                TreeNode nodo3 = new DefaultTreeNode("rfw", "Revision Formulario Web", arbol);
                nodo3.getChildren().add(new DefaultTreeNode("rfw", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento() + "-RFW", nodo3));
                nodo3.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                nodo3.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                nodo3.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
            }
            setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%" + getNumeroDocumento() + "%", "CER"));
            if (getDocumentoSeleccionado() != null) {
                TreeNode nodo4 = new DefaultTreeNode("cer", "Generacion Certificado", arbol);
                nodo4.getChildren().add(new DefaultTreeNode("cer", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento() + "-CER", nodo4));
                nodo4.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                nodo4.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                nodo4.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
            }
            setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%" + getNumeroDocumento() + "%", "IMC"));
            if (getDocumentoSeleccionado() != null) {
                TreeNode nodo5 = new DefaultTreeNode("imc", "Impresion Certificado", arbol);
                nodo5.getChildren().add(new DefaultTreeNode("imc", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento() + "-IMC", nodo5));
                nodo5.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                nodo5.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                nodo5.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "No existe Documento"));
        }
    }

    public void cargarArbolTramite(Long tramite) throws ServicioExcepcion {
        setFiltro2(Boolean.FALSE);
        String pattern = "dd/MM/yyyy ";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        if (tramiteAccionDao.listarPorTramite(tramite) != null) {
            arbol = new DefaultTreeNode("Root", null);

            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "RVT"));
            if (getTramiteAccion() != null) {
                TreeNode nodo15 = new DefaultTreeNode("rvt", "Revicion Tramite", arbol);
                nodo15.getChildren().add(new DefaultTreeNode("rvt", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-RVT", nodo15));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "PRO"));
            if (getTramiteAccion() != null) {
                TreeNode nodo9 = new DefaultTreeNode("pro", "Proforma", arbol);
                nodo9.getChildren().add(new DefaultTreeNode("pro", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-PRO", nodo9));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "CAJ"));
            if (getTramiteAccion() != null) {
                TreeNode nodo2 = new DefaultTreeNode("caj", "Caja", arbol);
                nodo2.getChildren().add(new DefaultTreeNode("caj", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-CAJ", nodo2));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "REP"));
            if (getTramiteAccion() != null) {
                TreeNode nodo12 = new DefaultTreeNode("rep", "Repertorio", arbol);
                nodo12.getChildren().add(new DefaultTreeNode("rep", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-REP", nodo12));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "DIG"));
            if (getTramiteAccion() != null) {
                TreeNode nodo1 = new DefaultTreeNode("dfc", "Digitalizacion Formulario", arbol);
                nodo1.getChildren().add(new DefaultTreeNode("dfc", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-DIG", nodo1));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "MRG"));
            if (getTramiteAccion() != null) {
                TreeNode nodo14 = new DefaultTreeNode("mrg", "Marginacion", arbol);
                nodo14.getChildren().add(new DefaultTreeNode("mrg", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-MRG", nodo14));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "MAT"));
            if (getTramiteAccion() != null) {
                TreeNode nodo6 = new DefaultTreeNode("mat", "Matriculacion", arbol);
                nodo6.getChildren().add(new DefaultTreeNode("mat", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-MAT", nodo6));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "MPH"));
            if (getTramiteAccion() != null) {
                TreeNode nodo41 = new DefaultTreeNode("mph", "Matriculacion PH", arbol);
                nodo41.getChildren().add(new DefaultTreeNode("mph", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-MPH", nodo41));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "INS"));
            if (getTramiteAccion() != null) {
                TreeNode nodo8 = new DefaultTreeNode("ins", "Inscripcion", arbol);
                nodo8.getChildren().add(new DefaultTreeNode("ins", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-INS", nodo8));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "INC"));
            if (getTramiteAccion() != null) {
                TreeNode nodo7 = new DefaultTreeNode("inc", "Cancelacion", arbol);
                nodo7.getChildren().add(new DefaultTreeNode("inc", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-INC", nodo7));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "IMP"));
            if (getTramiteAccion() != null) {
                TreeNode nodo11 = new DefaultTreeNode("imp", "Impresion", arbol);
                nodo11.getChildren().add(new DefaultTreeNode("imp", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-IMP", nodo11));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "FME"));
            if (getTramiteAccion() != null) {
                TreeNode nodo20 = new DefaultTreeNode("fme", "Firma Electronica", arbol);
                nodo20.getChildren().add(new DefaultTreeNode("fme", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-FME", nodo20));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "ETG"));
            if (getTramiteAccion() != null) {
                TreeNode nodo40 = new DefaultTreeNode("etg", "Entrega", arbol);
                nodo40.getChildren().add(new DefaultTreeNode("etg", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-ETG", nodo40));
            }
            setTramiteAccion(tramiteAccionDao.obtenerPorProceso(tramite, "FIN"));
            if (getTramiteAccion() != null) {
                TreeNode nodo21 = new DefaultTreeNode("fin", "Finalizacion Tramite", arbol);
                nodo21.getChildren().add(new DefaultTreeNode("fin", "Ver: " + tramiteAccion.getTraNumero().getTraNumero() + "-FIN", nodo21));
            }
        } else {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "No existe Documento"));
        }

    }

    public void generarRide(int documento) throws InterruptedException, IOException, ServicioExcepcion {
        try {
            setFacturaSeleccionado(facturaDao.encontrarFacturaPorNumeroTramite(documento));
            String contentType = "application/pdf";
            String numeroFac = getFacturaSeleccionado().getFacNumero();
            String claveAcce = getFacturaSeleccionado().getFacClaveAcceso();
            String fechaAutrizacion = "2018-12-06 18:48:54";
            String xml = getFacturaSeleccionado().getFacXml();
            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();
            String path = controladorGenerarRide.generarRide(numeroFac, claveAcce, fechaAutrizacion, xml);

            Path file = Paths.get(path);
            InputStream input = null;
            OutputStream output = ec.getResponseOutputStream();
            File file2 = new File(path);
            Long tamaño = file2.length();
            input = new BufferedInputStream(Files.newInputStream(file));

//FORMATO RESPUESTA
            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
            ec.setResponseContentLength(tamaño.intValue()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
            ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + "xxx" + "." + "pdf" + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

//ESCRIBIR A LA SALIDA OUTPUT
            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = input.read(bytesBuffer)) > 0) {
                output.write(bytesBuffer, 0, bytesRead);
            }

//            output.write(input);
            fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.

            //REPERTORIO DIGITAL
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se Pudo mostrar el archivo");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }

    }

    public void abrirArchivo() {
        String dirPrincipal = "";
        String subDirectorio = "formulario\\";
//        String url = "";
        String nombreArchivo = "";
        String contentType = "application/pdf";
        String extension = "pdf";
        String path = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();

        try {
            //OBTENER DIRECCION 
            setFormularioDigitalSeleccionado(formularioDigitalDao.PorNumeroFactura(getDocumentoSeleccionado().getCtaNumeroDocumento()));
            parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            dirPrincipal = parametroPath.getPrpPath();
            nombreArchivo = formularioDigitalSeleccionado.getFmdNombreArchivo();
            subDirectorio = formularioDigitalSeleccionado.getFmdPath();
//            url = "file:///" + dirPrincipal + subDirectorio + nombreArchivo + "." + extension;
            path = dirPrincipal + subDirectorio + nombreArchivo + "." + extension;

            Path file = Paths.get(path);
            InputStream input = null;
//            BufferedOutputStream bos = null;
            OutputStream output = ec.getResponseOutputStream();
            File file2 = new File(path);
            Long tamaño = file2.length();
            input = new BufferedInputStream(Files.newInputStream(file));

//FORMATO RESPUESTA
            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
            ec.setResponseContentLength(tamaño.intValue()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
            ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + nombreArchivo + "." + extension + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

//LEER EXISTENTE Y MODIFICAR
//            Document document = new Document();
//            PdfReader reader = new PdfReader(path);
//            document.open();
//            System.out.println("# paginas: " + document.getPageNumber());
//            document.close();
            //ESCRIBIR NUEVO
//                        Document document = new Document();
//            FileOutputStream fos = new FileOutputStream(path);
//            PdfWriter writer = PdfWriter.getInstance(document, fos);
//            document.open();
//            document.add(new Paragraph("Hola mundo"));
//            document.close();
//            writer.close();
//ESCRIBIR A LA SALIDA OUTPUT
            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = input.read(bytesBuffer)) > 0) {
                output.write(bytesBuffer, 0, bytesRead);
            }

//            output.write(input);
            fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.

        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al cargar el archivo");
        }
    }

    public void generarFormulario() throws IOException, JRException, NamingException, SQLException {
        try {
            setFacturaDetalleSeleccionada(facturaDetalleDao.PorNumeroFactura(getDocumentoSeleccionado().getCtaNumeroDocumento()));
            setFormularioSeleccionado(formularioWebDao.buscarFormulario(getFacturaDetalleSeleccionada().getFdtTraNumero()));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("formularioWebId", getFormularioSeleccionado().getFlwId());
            reporteUtil.imprimirReporteEnPDF(getFormularioSeleccionado().getTdwId().getTdwNombre(), getFormularioSeleccionado().getTdwId().getTdwNombre() + "_" + getFormularioSeleccionado().getFlwId(), parametros);
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se Pudo Descargar Formulario");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }

    }

    public void crearPDFconTextoHTML(String Documento) {
        try {
            setSeleccionCertificado(certificadoDaoo.listarPorCerNumero(Documento));
            List<InputStream> listPdfs = new ArrayList<>();
            setMargen(servicioMargen.obtenerPorMarTipo("CER"));
            if (!seleccionCertificado.isEmpty() && seleccionCertificado.size() >= 1) {
                int i = 1;
                setDirCarpetaTemp(pdfTempUtil.directorio());//Creo el directorio temporal sin los archivos
                for (Certificado certificado : seleccionCertificado) {
                    String nombreArchivo = "certificadoReImp" + i + ".pdf";//Nombre por cada archivo
                    String dirFinalArchivo = dirCarpetaTemp + nombreArchivo;//Directorio final por cada archivo
                    FileOutputStream fos = new FileOutputStream(dirFinalArchivo);
                    Document document = new Document(PageSize.A4, margen.getMarIzquierdo(), margen.getMarDerecho(), margen.getMarSuperior(), margen.getMarInferior());
                    PdfWriter writer = PdfWriter.getInstance(document, fos);
                    writer.setPageEvent(new FooterPdfEvent());
                    document.setMarginMirroring(true);
                    document.open();

                    Paragraph parrafo = new Paragraph();
                    parrafo = pdfTempUtil.convertirHtmlConFormatoAParrafoPdfOld(parrafo, certificado.getCerCertificado());
                    parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                    document.add(parrafo);

                    document.close();//cierro el documento
                    writer.close();//cierro la escritura

                    i++;
                    listPdfs.add(new FileInputStream(new File(dirFinalArchivo)));//Agregar el directorio de cada archivo a la lista
                }
                String nombreArchivo = "certificadosReImp.pdf";
                pdfTempUtil.setNombreArchivo(nombreArchivo);//Agregar el nombre archivo final a la dependencia
                OutputStream fos = new FileOutputStream(dirCarpetaTemp + nombreArchivo);//Crear archivo final
                PdfTempUtil.doMerge(listPdfs, fos);//Combinar pdfs que se agregaron en la lista
                setUrl(pdfTempUtil.generarLinksAccesoAlPdf());//Generar la url que se mostrará en la lista
            }
        } catch (DocumentException | IOException | ServicioExcepcion ex) {
            ex.getMessage();
        }
    }

    public void busqueda() {
        setFiltro2(Boolean.FALSE);
    }

    public void busquedaTrue() {
        arbol = null;
        setListaTramiteAccion(null);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/seguimientoTramite.xhtml");
        } catch (Exception e) {
            addErrorMessage("Error al redireccionar la pagina");
        }

    }

}
