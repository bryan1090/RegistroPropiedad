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
@Named(value = "controladorSeguimiento")
@ViewScoped
public class ControladorSeguimiento implements Serializable {

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
    private String TraNumero;
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
      
     @EJB
    private FacturaDetalleDao facturaDetalleDao;
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
    private  Factura FacturaSeleccionado;
    
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

   
    @PostConstruct
    public void postConstructor(){
        
       listarTramitePorUsuario();
      
    }
    public void listarTramitePorUsuario(){
        setListaTramites(tramiteDao.listarPorUsuario((int) dataManagerSesion.getUsuarioLogeado().getPerId().getPerId().longValue()));
    }
     public void seguir(Long traNumero){
          
          try {
               FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/facturaWeb.xhtml?paramTramite=" + traNumero);
            } catch (Exception e) {
                addErrorMessage("Error al redireccionar la pagina");
            } 
         
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
        setListaFormularioWeb(formularioWebDao.listarPorTramiteRango(getNTramiteIni(),getNTramiteFin(),dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
    }
    public void listarPorFecha() throws ServicioExcepcion, ParseException {
           if (getFechaIni().equals(getFechaFin())) {
               
              setListaFormularioWeb(formularioWebDao.listarPorFecha(getFechaIni(),getFechaFin(),dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
          }
         
        else {
            setListaFormularioWeb(formularioWebDao.listarFecha(getFechaIni(), getFechaFin(),dataManagerSesion.getUsuarioLogeado().getUsuLogin()));    
        }

    }
    
     public void listarFacturaDeralle() {
        setListaFacturaDetalle(facturaDetalleDao.listarPorNumtramite(getFormularioSeleccionado().getFlwId().longValue()));
    }
      public void listarCertificadoAccion() {
        setListaCertificadoAccion(certificadoDao.listarCertificado("%"+getFacturaDetalleSeleccionada().getFacId().getFacNumero()+"%"));
    }
     
     public void onNodeSelect(NodeSelectEvent event) throws ServicioExcepcion {
        String[] parts = event.getTreeNode().toString().split("[: -]+");
        setListaTramiteAccion(null);
            switch (getNodoSeleccionado().getType()) {
                case "rfw":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getNumeroDocumento()+"%", "RFW"));
                    break;
                case "caj":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getNumeroDocumento()+"%", "CAJ"));
                    break;
                case "cer":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getNumeroDocumento()+"%", "CER"));
                    break;
                case "dfc":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getNumeroDocumento()+"%", "DFC"));
                    break;
                case "imc":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getNumeroDocumento()+"%", "IMC"));
                    break;
                default:
                    break;
            }

        
    }
    public void NodeSelect() throws ServicioExcepcion {
        setDocumentoSeleccionado(certificadoDao.buscarCertificado(getFacturaDetalleSeleccionada().getFacId().getFacNumero()));
            switch (getNodoSeleccionado().getType()) {
                case "rfw":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getDocumentoSeleccionado().getCtaNumeroDocumento()+"%", "RFW"));
                    break;
                case "caj":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getDocumentoSeleccionado().getCtaNumeroDocumento()+"%", "CAJ"));
                    break;
                case "cer":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getDocumentoSeleccionado().getCtaNumeroDocumento()+"%", "CER"));
                    break;
                case "dfc":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getDocumentoSeleccionado().getCtaNumeroDocumento()+"%", "DFC"));
                    break;
                case "imc":
                    setListaCertificadoAccion(certificadoDao.listarCertificadoAccion("%"+getDocumentoSeleccionado().getCtaNumeroDocumento()+"%", "IMC"));
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
                setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getNumeroDocumento()+"%", "CAJ"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo2 = new DefaultTreeNode("caj","Caja", arbol);
                    nodo2.getChildren().add(new DefaultTreeNode("caj", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-CAJ", nodo2));
                    nodo2.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo2.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo2.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                }
                setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getNumeroDocumento()+"%", "DFC"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo1 = new DefaultTreeNode("dfc","Digitalizacion Formulario", arbol);
                    nodo1.getChildren().add(new DefaultTreeNode("dfc", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-DFC", nodo1));
                    nodo1.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo1.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo1.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                }
                setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getNumeroDocumento()+"%", "RFW"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo3 = new DefaultTreeNode("rfw","Revision Formulario Web", arbol);
                    nodo3.getChildren().add(new DefaultTreeNode("rfw", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-RFW", nodo3));
                    nodo3.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo3.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo3.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                } 
                setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getNumeroDocumento()+"%", "CER"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo4 = new DefaultTreeNode("cer","Generacion Certificado", arbol);
                    nodo4.getChildren().add(new DefaultTreeNode("cer", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-CER", nodo4));
                    nodo4.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo4.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo4.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                } 
                setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getNumeroDocumento()+"%", "IMC"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo5 = new DefaultTreeNode("imc","Impresion Certificado", arbol);
                    nodo5.getChildren().add(new DefaultTreeNode("imc", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-IMC", nodo5));
                    nodo5.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo5.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo5.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                } 
    }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "No existe Documento"));
            }}
public void cargarArbolTramite(int tramite) throws ServicioExcepcion {
            setFiltro2(Boolean.FALSE);
            String pattern = "dd/MM/yyyy ";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
        setFacturaDetalleSeleccionada(facturaDetalleDao.PorNumeroTramite(tramite));
    } catch (Exception e) {
         FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "No existe Documento"));
    }
            
         
            if(certificadoDao.buscarDocumento(getFacturaDetalleSeleccionada().getFacId().getFacNumero()) != null) {
                arbol = new DefaultTreeNode("Root", null);
                   setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getFacturaDetalleSeleccionada().getFacId().getFacNumero()+"%","CAJ"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo2 = new DefaultTreeNode("caj","Caja", arbol);
                    nodo2.getChildren().add(new DefaultTreeNode("caj", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-CAJ", nodo2));
                    nodo2.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo2.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo2.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                }
                setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getFacturaDetalleSeleccionada().getFacId().getFacNumero()+"%","DFC"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo1 = new DefaultTreeNode("dfc","Digitalizacion Formulario", arbol);
                    nodo1.getChildren().add(new DefaultTreeNode("dfc", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-DFC", nodo1));
                    nodo1.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo1.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo1.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                }
               setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getFacturaDetalleSeleccionada().getFacId().getFacNumero()+"%","RFW"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo3 = new DefaultTreeNode("rfw","Revision Formulario Web", arbol);
                    nodo3.getChildren().add(new DefaultTreeNode("rfw", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-RFW", nodo3));
                    nodo3.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo3.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo3.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                } 
                setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getFacturaDetalleSeleccionada().getFacId().getFacNumero()+"%","CER"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo4 = new DefaultTreeNode("cer","Generacion Certificado", arbol);
                    nodo4.getChildren().add(new DefaultTreeNode("cer", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-CER", nodo4));
                    nodo4.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo4.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo4.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                } 
                setDocumentoSeleccionado(certificadoDao.buscarDocumentoEstado("%"+getFacturaDetalleSeleccionada().getFacId().getFacNumero()+"%","IMC"));
                if (getDocumentoSeleccionado() != null) {
                    TreeNode nodo5 = new DefaultTreeNode("imc","Impresion Certificado", arbol);
                    nodo5.getChildren().add(new DefaultTreeNode("imc", "Ver: " + documentoSeleccionado.getCtaNumeroDocumento()+ "-IMC", nodo5));
                    nodo5.getChildren().add(new DefaultTreeNode("Usuario: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUser())));
                    nodo5.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(documentoSeleccionado.getCtaFHR())));
                    nodo5.getChildren().add(new DefaultTreeNode("Usuario Asignado: " + personaDao.buscarPersonaPorUsuario(documentoSeleccionado.getCtaUsuarioAsignado())));
                } 
                
                
    }else{
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Error", "No existe Documento"));
            }
        
}

public void generarRide(String documento) throws InterruptedException, IOException, ServicioExcepcion {
        try {
                 setFacturaSeleccionado(facturaDao.buscarPorNumFactura(documento));
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
            subDirectorio= formularioDigitalSeleccionado.getFmdPath();
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
       setListaCertificadoAccion(null);
       setFiltro2(Boolean.TRUE);
    }

}
