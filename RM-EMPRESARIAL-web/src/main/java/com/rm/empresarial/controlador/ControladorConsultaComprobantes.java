/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.ControladorConsultaComprobantesBb;
import com.rm.empresarial.controlador.util.DescargarArchivo;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.PathDocEleDao;
import com.rm.empresarial.dao.consultaComprobantesDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.EMensaje;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.PathDocEle;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.EMensajeServicio;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.PathDocEleServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Wilson Herrera
 */
@ViewScoped
@Named
public class ControladorConsultaComprobantes extends JsfUtil implements Serializable {

    @EJB
    private FacturaServicio facturaServicio;
    @EJB
    private EMensajeServicio mensajeServicio;
    @EJB
    private consultaComprobantesDao consultaComprobantesDao;
    @EJB
    private FacturaDetalleServicio facturaDetalleServicio;
    @EJB
    private InstitucionServicio institucionServicio;
    @EJB
    private PathDocEleDao pathDocEleDao;
    @EJB
    private PathDocEleServicio pathDocEleServicio;
    @EJB
    private TramiteServicio tramiteServicio;
    @EJB
    private TramiteDetalleServicio tramiteDetalleServicio;
    @EJB
    private TramiteValorServicio tramiteValorServicio;

    @Inject
    @Getter
    @Setter
    private ControladorConsultaComprobantesBb consultaComprobantesBb;
    
    @Inject
    private ControladorModelosSRI controladorModelosSRI;

    @Inject
    @Getter
    @Setter
    private ControladorGenerarRide controladorGenerarRide;

    @Getter
    @Setter
    private PathDocEle path;

    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;

    @Getter
    @Setter
    private StreamedContent files;

    @Getter
    @Setter
    private Institucion institucion;
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private List<EMensaje> listarespuestaSRI;

    @Getter
    @Setter
    private List<EMensaje> listaVer;

    @Getter
    @Setter
    private EMensaje respuestaSRI;

    @Getter
    @Setter
    private String serie;

    @Getter
    @Setter
    private String numero;

    public void inicializar() {
        consultaComprobantesBb.init();
        listarespuestaSRI = new ArrayList<>();
        listaVer = new ArrayList<>();
    }

    public void obtenerInformacion(EMensaje idmensaje) throws ServicioExcepcion {
        listaVer = new ArrayList<>();
        setRespuestaSRI(mensajeServicio.obtenerPorID(idmensaje.getEMsIdSec()));
        listaVer.add(getRespuestaSRI());
    }

    public void listarPorFechaFacturas() throws ServicioExcepcion, ParseException {
        if (!getConsultaComprobantesBb().getFiltro()) {
            getConsultaComprobantesBb().setListaFactura(facturaServicio.listarFactura(getConsultaComprobantesBb().getFechaIinicio(), getConsultaComprobantesBb().getFechaFin()));
        } else {
            getConsultaComprobantesBb().setListaFactura(facturaServicio.listarPorComprobante(getConsultaComprobantesBb().getNumeroFactura()));
        }

    }

    public void listarPorFechaFacturaWeb() throws ServicioExcepcion, ParseException {
        if (!getConsultaComprobantesBb().getFiltro()) {
            getConsultaComprobantesBb().setListaFacturaWeb(facturaServicio.listarFacturaWeb(getConsultaComprobantesBb().getFechaIinicio(), getConsultaComprobantesBb().getFechaFin(), dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
        } else {
            getConsultaComprobantesBb().setListaFacturaWeb(facturaServicio.FacturaNumeroWeb(getConsultaComprobantesBb().getNumeroFactura(), dataManagerSesion.getUsuarioLogeado().getUsuLogin()));
        }

    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void generarRide() throws InterruptedException, IOException, ServicioExcepcion {

        String contentType = "application/pdf";
        String numeroFac = getConsultaComprobantesBb().getFacturaSeleccionada().getFacNumero();
        String claveAcce = getConsultaComprobantesBb().getFacturaSeleccionada().getFacClaveAcceso();
        String fechaAutrizacion = "2018-12-06 18:48:54";
        String xml = getConsultaComprobantesBb().getFacturaSeleccionada().getFacXml();
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();

        if (xml == null || numeroFac == null) {
            addErrorMessage("No se encontro xml");
        } else {
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
        }
    }
    
    public void generarComprobante() throws ServicioExcepcion{
       if(getConsultaComprobantesBb().getFacturaSeleccionada().getFacTroId() == null){
           generarNV();
       }
       else{
           generarNVParaCertificado();
           
       }
    }
    
    public void generarReporte() throws ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.TramitesControlador.generarReporte()");
        Date fechaImpresion = Calendar.getInstance().getTime();
        Institucion instit = institucionServicio.obtenerInstitucion();
        String numeroFactura = getConsultaComprobantesBb().getFacturaSeleccionada().getFacNumero();
        FacturaDetalle factDetalle = facturaDetalleServicio.PorNumeroFactura(numeroFactura);
        Tramite tramite = tramiteServicio.buscarTramitePorNumero(new Long(factDetalle.getFdtTraNumero()));
        if (tramite != null) {
            BigDecimal totalTraValor = BigDecimal.ZERO;
            TramiteDetalle traDetalleConsultado = tramiteDetalleServicio.buscarPorNumTramite(tramite.getTraNumero());
            String cliente = traDetalleConsultado.getPerId().getPerApellidoPaterno() + " " + traDetalleConsultado.getPerId().getPerApellidoMaterno() + " " + traDetalleConsultado.getPerId().getPerNombre();
            String ciCLiente = traDetalleConsultado.getPerId().getPerIdentificacion();
            int numTramite = tramite.getTraNumero().intValue();
            List<TramiteValor> listtravalor = tramiteValorServicio.buscarTraValor(numTramite);
            for (TramiteValor travalor : listtravalor) {
                BigDecimal subTot = travalor.getTrvValor();
                BigDecimal iva = travalor.getTrvIva();
                totalTraValor = totalTraValor.add(subTot.add(iva));
            }

            String[] cantidades = (totalTraValor).toString().split("\\.");

            if (listtravalor.size() > 0) {
                String totalLetras = TransformadorNumerosLetrasUtil.transformador(cantidades[0]).toString();
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("numTramite", tramite);
                parametros.put("letrasTotal", totalLetras);
                parametros.put("fechaImpresion", fechaImpresion);
                parametros.put("nomInstitucion", institucion.getInsNombre());
                parametros.put("numTotal", cantidades[1]);
                parametros.put("pathImagen", institucion.getInsLogo());
                parametros.put("cliente", cliente);
                parametros.put("ciCliente", ciCLiente);
                try {
                    reporteUtil.imprimirReporteEnPDF("Proforma", "Proforma|" + tramite, parametros);

                } catch (JRException | IOException | NamingException | SQLException ex) {
                    Logger.getLogger(TramitesControlador.class
                            .getName()).log(Level.SEVERE, null, ex);

                }

            } else {
                JsfUtil.addErrorMessage("No existen tramitesValor con estado distinto de F");

            }
        }
    }

     public void generarNVParaCertificado() {
        try {
            String numeroFactura = getConsultaComprobantesBb().getFacturaSeleccionada().getFacNumero();
            Factura fact = facturaServicio.buscarPorNumFactura(numeroFactura);
            BigDecimal total = fact.getFacTotal();
            String pathImagen = institucionServicio.encontrarInstitucionPorId("1").getInsLogo();
            String nomApePer = fact.getFacPerApellidoPaterno().trim() + " " + fact.getFacPerApellidoMaterno().trim() + " " + fact.getFacPerNombre().trim();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numeroFactura", numeroFactura);
            parametros.put("nomApePer", nomApePer);
            parametros.put("pathImagen", pathImagen);
            parametros.put("codigoVerif", fact.getFacCodigoVerificacion());
            parametros.put("valorNum", total);
            parametros.put("valorAlf", TransformadorNumerosLetrasUtil.transformador(total.toString()).toString());

            reporteUtil.imprimirReporteEnPDF("FacturaCertificado", "Comprobante", parametros);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Comprobante Generado"));
        } catch (Exception e) {
            System.err.println(e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al generar NV");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    public void generarNV() {
        try {
            String numeroFactura = getConsultaComprobantesBb().getFacturaSeleccionada().getFacNumero();
            BigDecimal total = getConsultaComprobantesBb().getFacturaSeleccionada().getFacTotal();
            String pathImagen = institucionServicio.encontrarInstitucionPorId("1").getInsLogo();
            String nomApePer = getConsultaComprobantesBb().getFacturaSeleccionada().getFacPerApellidoPaterno().trim()
                    + " " + getConsultaComprobantesBb().getFacturaSeleccionada().getFacPerApellidoMaterno().trim()
                    + " " + getConsultaComprobantesBb().getFacturaSeleccionada().getFacPerNombre().trim();
            setInstitucion(consultaComprobantesDao.obtenerInstitucion(num_provincias));
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numeroFactura", numeroFactura);
            parametros.put("nomApePer", nomApePer);
            parametros.put("pathImagen", pathImagen);
            parametros.put("codigoVerif", getConsultaComprobantesBb().getFacturaSeleccionada().getFacCodigoVerificacion());
            parametros.put("valorNum", total);
            parametros.put("valorAlf", TransformadorNumerosLetrasUtil.transformador(total.toString()).toString());
            
            reporteUtil.imprimirReporteEnPDF("Factura", "Comprobante" + numeroFactura, parametros);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Comprobante Generado"));
        } catch (Exception e) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error Al Generar NV");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    public void descargarArchivo(Factura numero) throws FileNotFoundException, IOException {
        try {
            setPath(pathDocEleDao.obtenerPathTemporales());
            System.err.println(getPath().getPdeRepTemporal());
            crearArchivo(numero.getFacXml(), numero.getFacNumero(), getPath().getPdeRepTemporal());
            // InputStream stream = new BufferedInputStream(new FileInputStream("F:/RM/FacturacionElectronica/repo_local_elec/temporales/"+getConsultaComprobantesBb().getFacturaSeleccionada().getFacNumero()+".xml"));
            InputStream stream = new BufferedInputStream(new FileInputStream(getPath().getPdeRepTemporal() + numero.getFacNumero() + "-firmado.xml"));
            //InputStream stream = ((ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext()).getResourceAsStream("C:/repo_local_elec/temporales/001-999-180000028.xml");
            files = new DefaultStreamedContent(stream, "application/xml", numero.getFacNumero() + ".xml");

        } catch (Exception e) {
            addErrorMessage("No se pudo descargar archivo");
        }
    }

    public void busquedaPorFactura() {
        getConsultaComprobantesBb().setFiltro(Boolean.TRUE);
    }

    public void busquedaPorFechas() {
        getConsultaComprobantesBb().setFiltro(Boolean.FALSE);
    }

    public void crearArchivo(String texto, String numero, String path) throws IOException {
//        String ruta = "C:/repo_local_elec/temporales/" + numero + ".xml";
        String ruta = path + numero + ".xml";
        File archivo = new File(ruta);
        BufferedWriter bw;
        try {
            if (archivo.exists()) {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(texto);
            } else {
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(texto);
            }
            bw.close();
        } catch (Exception e) {
            addErrorMessage("Error no contiene xml");
        }

    }

    public void cargarRespuestaSri() throws ServicioExcepcion {
        listarespuestaSRI = new ArrayList<>();
        if (getConsultaComprobantesBb().getFacturaSeleccionada() != null) {
            getConsultaComprobantesBb().setFacturaSeleccionada(getConsultaComprobantesBb().getFacturaSeleccionada());
            String[] numFactura = consultaComprobantesBb.getFacturaSeleccionada().getFacNumero().split("-");
            int i = 0;
            for (String aux : numFactura) {
                i++;
                switch (i) {
                    case 1:
                        serie = aux + "-";
                        break;
                    case 2:
                        serie = serie + aux;
                        break;
                    case 3:
                        numero = aux;
                        break;
                    default:
                        break;
                }
            }
            setListarespuestaSRI(mensajeServicio.buscarPorSerie_Y_Numero(serie, numero));
            if (!listarespuestaSRI.isEmpty()) {

            } else {
                addErrorMessage("Respuesta SRI no encontrada");
            }

        } else {
            addWarningMessage("Sin mensajes");
        }
    }

    public void reenviarFactura() throws Exception {
        if (getConsultaComprobantesBb().getFacturaSeleccionada() != null && getConsultaComprobantesBb().getFacturaSeleccionada().getFacClaveAcceso() == null) {
            getConsultaComprobantesBb().setFacturaSeleccionada(getConsultaComprobantesBb().getFacturaSeleccionada());
            
            PathDocEle pathDocEle = new PathDocEle();
            pathDocEle = pathDocEleServicio.obtenerPaths();
            institucion = institucionServicio.obtenerInstitucion();
            List<FacturaDetalle> facturaDetalle = facturaDetalleServicio.lisarPorNumeroFactura(getConsultaComprobantesBb().getFacturaSeleccionada().getFacNumero());
            if(controladorModelosSRI==null){
                controladorModelosSRI=new ControladorModelosSRI();
            }
            
            controladorModelosSRI.cargarModelos();
            
            addSuccessMessage("Factura Enviada Correctamente");
            listarPorFechaFacturas();
        } else {
            addWarningMessage("Verifique datos de factura( campo claveAcceso debe ser null)");
        }

    }
}
