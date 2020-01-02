/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfSignatureAppearance;
import com.lowagie.text.pdf.PdfStamper;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.EncryptPassword;
import com.rm.empresarial.controlador.util.EnviarEmailController;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.UtilitarioPdf;
import com.rm.empresarial.dao.InscripcionDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.UsuarioServicio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import java.security.cert.Certificate;

/**
 *
 * @author Prueba
 */
@Named(value = "controladorFirmaElectronica")
@ViewScoped
public class ControladorFirmaElectronica implements Serializable {

    @Inject
    private EnviarEmailController utilCorreo;

    @Inject
    @Getter
    @Setter
    private DataManagerSesion dataManagerSesion;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    private TramiteUtil tramiteUtil;

    @Inject
    private UtilitarioPdf utilitarioPdf;

    @Inject
    private EncryptPassword encryptPassword;

    @Getter
    @Setter
    private List<Repertorio> listaRepertorioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<Repertorio> listaRepertorioSeleccionados = new ArrayList();

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private Usuario usuarioAsignado;

    @Getter
    @Setter
    private Date FHR;

    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSelec;

    @EJB
    private InscripcionDao inscripcionDao;

    @EJB
    private RepertorioUsuarioDao repertorioUsuarioDao;

    @EJB
    private TramiteDao tramiteDao;

    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private CargaLaboralServicio servicioCargaLaboral;

    @EJB
    private UsuarioServicio servicioUsuario;

    @EJB
    private ActaServicio servicioActa;

    @EJB
    private FacturaDetalleServicio facturaDetalleServicio;

    @EJB
    private FacturaServicio facturaServicio;

    @Getter
    @Setter
    String urlActaPdf;
    @Getter
    @Setter
    private String pathActaPdf;

    //ruta certificado y firma
    private String path_Firma;
    private String path_Certificado;
    private String nombreFirma;
    private String passFirma;

    public ControladorFirmaElectronica() {

        FHR = Calendar.getInstance().getTime();
        tramiteAccion = new TramiteAccion();
        repertorioUsuarioSelec = new RepertorioUsuario();

    }

    @PostConstruct
    public void postConstructor() {
        try {
            llenarDatos();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorEntregas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ControladorEntregas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarDatos() throws ServicioExcepcion, ParseException {
        listaRepertorioFecha.clear();
        setListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog(FHR, dataManagerSesion.getUsuarioLogeado().getUsuId(), "FME"));

    }

    public void generarPdfActa(String textoHtml) {
        try {
            utilitarioPdf.crearPDFconTextoHTML("acta.pdf", textoHtml, "LIB");
            utilitarioPdf.generarLinksAccesoAlPdf();
            pathActaPdf = utilitarioPdf.getDirFinalArchivo();

//            urlActaPdf = utilitarioPdf.getUrl();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorInscripcionProceso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void firmarDocumentoPdf() {
        try {
            //ruta certificado y firma
            this.nombreFirma = "firma_test";
//        this.passFirma = encryptPassword.decrypt(dataManagerSesion.getUsuarioLogeado().getUsuFirmaContrasenia());
//            this.passFirma = "Coinjih123";
            this.passFirma = "test";

            String extension = ".p12";
            this.path_Firma = "F:\\RM\\FacturacionElectronica\\repo_local_elec\\recursos\\firma\\firma_test.p12";
//            this.path_Certificado = "/usr/lib/jvm/java-1.7.0-openjdk-1.7.0.9/jre/lib/security/cacerts";
            KeyStore ks;
            PrivateKey pk;
//        KeyStore ks = KeyStore.getInstance("pkcs12", "BC");

            ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(path_Firma), passFirma.toCharArray());

            String alias = (String) ks.aliases().nextElement();

            pk = (PrivateKey) ks.getKey(alias, passFirma.toCharArray());
            System.out.println("pk: " + pk.toString());
            Certificate[] chain = ks.getCertificateChain(alias);

            PdfReader reader = new PdfReader(utilitarioPdf.getDirFinalArchivo());
            PdfStamper stamper = PdfStamper.createSignature(reader, new FileOutputStream(utilitarioPdf.getDirFinalArchivo() + "_terminado.pdf"), '\0');
            PdfSignatureAppearance appearance
                    = stamper.getSignatureAppearance();

            appearance.setVisibleSignature("mySig");
            appearance.setReason("Razón de la Firma");
            appearance.setLocation("Ubicación");
            appearance.setCrypto(
                    pk, chain, null, PdfSignatureAppearance.WINCER_SIGNED);
//            if (certified) {
            appearance.setCertificationLevel(
                    PdfSignatureAppearance.CERTIFIED_NO_CHANGES_ALLOWED);
//            }
//            if (graphic) {
//                appearance.setAcro6Layers(true);
//                appearance.setSignatureGraphic(Image.getInstance(RESOURCE));
//                appearance.setRender(PdfSignatureAppearance.SignatureRenderGraphicAndDescription);
//            }
            stamper.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ControladorFirmaElectronica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | DocumentException | IOException ex) {
            Logger.getLogger(ControladorFirmaElectronica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void firmar() throws ServicioExcepcion, ParseException, IOException, Exception {

        FacesContext context = FacesContext.getCurrentInstance();
        List<Acta> actasSelec = new ArrayList<>();
        Boolean actaFirmada = false;
        for (Repertorio rep : listaRepertorioSeleccionados) {
            actasSelec.addAll(servicioActa.listarActaPorNumRepertorio(rep.getRepNumero().intValue()));
        }

        System.out.println("Numero de Actas a firmar: " + actasSelec.size());
        for (Acta acta : actasSelec) {
            generarPdfActa(acta.getActActaPdf()); //genero el archivo pdf dentro de la carpeta temp del sistema
            utilitarioPdf.firmarPdf(utilitarioPdf.getDirFinalArchivo(), acta.getActNumero().toString());//genero un archivo pdf firmado por el archivo p12 del usuario logueado, en un carpeta en el servidor
            if (utilitarioPdf.getDirArchivoFirmado() != null) {
                acta.setActFirmaDigital(utilitarioPdf.getDirArchivoFirmado()); //guardo la direccion del archivo firmado
                servicioActa.edit(acta);
                actaFirmada = true;

                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream(utilitarioPdf.getDirArchivoFirmado());
                    enviarCorreo(inputStream, acta.getRepNumero());
                } catch (Exception e) {
                    JsfUtil.addWarningMessage("No se envió el correo porque no se encontró una dirección de Email");
                    System.err.println(e);
                }

            }
        }
        if (actaFirmada) {
            for (Repertorio listaRepertorioSelec : listaRepertorioSeleccionados) {
                RepertorioUsuario repUsu = repertorioUsuarioDao.obtenerRepUsu_Por_Rep_Usr_Tipo(listaRepertorioSelec.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "FME");
                repUsu.setRpuEstado("I");
                repertorioUsuarioDao.edit(repUsu);
                crearRepUsuario_TramAccion(repUsu);

            }
            String mensaje = "Actas firmadas";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", mensaje));
        }

        llenarDatos();
    }

    public void enviarCorreo(InputStream adjunto, Repertorio repertorio) throws ServicioExcepcion {
        try {

            Tramite tramit = repertorio.getTraNumero();
            FacturaDetalle factDetalle = facturaDetalleServicio.encontrarPorNumeroTramite(tramit.getTraNumero().intValue());
            Factura fact = factDetalle.getFacId();
            String nomnbrePersona = fact.getFacPerNombre().trim() + " " + fact.getFacPerApellidoPaterno().trim() + " " + fact.getFacPerApellidoMaterno().trim();

            String asunto = "Creación de Documento con Firma Digital : " + tramit.getTraNumero();
            String mensaje = "Estimado " + nomnbrePersona + ", su documento con número " + tramit.getTraNumero()
                    + " ha sido generado.";
            String correo = "" + fact.getFacPerEmail();
            if (correo != null || !correo.isEmpty()) {
                utilCorreo.enviaEmailConAdjunto(correo, asunto, mensaje, "pdf", adjunto, "DocFirmElectronica.pdf", " ");
            } else {
                JsfUtil.addWarningMessage("No se envió el correo porque no se encontró una dirección de Email");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void crearRepUsuario_TramAccion(RepertorioUsuario repertorioUsuSelec) throws ServicioExcepcion {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(repertorioUsuSelec.getRepNumero().getTraNumero().getTraNumero());
            setRepertorioUsuarioSelec(repertorioUsuSelec);

            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(tramiteSeleccionado.getTraNumero(), "ETG"));
            if (tramiteAccion == null) {
                //codigo CargaLaboral
                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("ETG", "Entrega", 1);
                cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSelec.getUsuId().getUsuId(), repertorioUsuarioSelec.getRpuTipo());
            } else {
                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                int auxCarga = cargaAdd.getCarAsignado() + 1;
                cargaAdd.setCarAsignado((short) auxCarga);
                servicioCargaLaboral.edit(cargaAdd);
            }

            RepertorioUsuario repertorioUsuario = new RepertorioUsuario();
            repertorioUsuario.setRpuTipo("ETG");
            repertorioUsuario.setUsuId(usuarioAsignado);
            repertorioUsuario.setRpuEstado("A");
            repertorioUsuario.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            repertorioUsuario.setRpuFHR(Calendar.getInstance().getTime());
            repertorioUsuario.setRepNumero(repertorioUsuSelec.getRepNumero());

            servicioRepertorioUsuario.create(repertorioUsuario);

            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteSeleccionado.getTraNumero().toString(),
                    "Tramite: " + tramiteSeleccionado.getTraNumero()
                    + " asignado al usuario " + usuarioAsignado.getUsuLogin(),
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), usuarioAsignado.getUsuLogin());

            tramiteSeleccionado.setTraEstado("ETG");
            tramiteDao.edit(tramiteSeleccionado);
            tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteSeleccionado.getTraNumero().toString(),
                    "ACTUALIZACIÓN DEL ESTADO DE TRAMITE A 'ETG'",
                    tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), null);

        } catch (ServicioExcepcion e) {
            e.printStackTrace();
            String mensaje = "No se pudo procesar";

            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", mensaje));
        }

    }

}
