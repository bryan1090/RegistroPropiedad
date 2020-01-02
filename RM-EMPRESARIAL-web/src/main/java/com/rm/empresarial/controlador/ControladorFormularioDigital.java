/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.CertificadoAccionDao;
import com.rm.empresarial.dao.TipoCertificadoDao;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.CertificadoAccion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FormularioDigital;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioDigital;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoCertificado;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.FormularioDigitalServicio;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.RepertorioDigitalServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Prueba
 */
@Named(value = "controladorFormularioDigital")
@ViewScoped
public class ControladorFormularioDigital implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @EJB
    private RepertorioServicio servicioRepertorio;
    @EJB
    private TramiteServicio servicioTramite;
    @EJB
    private CargaLaboralServicio servicioCargaLaboral;
    @EJB
    private CertificadoCargaServicio servicioCertificadoCarga;
    @EJB
    private ParametroPathServicio servicioParametroPath;
    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;
    @EJB
    private SecuenciaServicio secuenciaServicio;
    @EJB
    private FormularioDigitalServicio servicioFormularioDigital;
    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private FacturaServicio servicioFactura;

    @EJB
    private CertificadoAccionDao certificadoAccionDao;
    @EJB
    private TipoCertificadoDao servicioTipoCertificado;

    @Inject
    private TramiteUtil tramiteUtil;
    //objetos, variables y listas

    @Getter
    @Setter
    List<CertificadoCarga> listaCertificadoCarga = new ArrayList<>();
    ;
    
    @Getter
    @Setter
    List<CertificadoCarga> listaCertificadoCargaPorDocPorUsuId;

    @Getter
    @Setter
    private UploadedFile file;

    @Getter
    @Setter
    private CargaLaboral cargaLaboral;

    @Getter
    @Setter
    private Date fecha;

    @Getter
    @Setter
    private Factura factura;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Getter
    @Setter
    private ParametroPath parametroPath;

    @Getter
    @Setter
    private CertificadoCarga certificadoCargaSeleccionado;

    @Getter
    @Setter
    private Secuencia secuencia;

    @Getter
    @Setter
    private FormularioDigital formularioDigital;

    public ControladorFormularioDigital() {

        factura = new Factura();
        setFecha(Calendar.getInstance().getTime());
        //inicializar();
    }

    @PostConstruct
    public void postConstructor() {
        inicializar();

    }

    public void inicializar() {
        try {
            listaCertificadoCarga = servicioCertificadoCarga.listarUnicoPorTipoPorEstadoPorUsuIdPorFecha(
                    dataManagerSesion.getUsuarioLogeado().getUsuId(), "DFC", fecha);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage(file.getFileName(), " subido.");
            FacesContext.getCurrentInstance().addMessage(null, message);
            System.out.println("filename: " + file.getFileName());
        }
    }

    public void subirArchivo(FileUploadEvent event) throws IOException {

        try {
            InputStream is = event.getFile().getInputstream();
            System.out.println(" extension:" + event.getFile().getContentType());

//            Path folder;
            String extension = "pdf";
            Path file;
            String nombreArchivo = "";
            String dirPrincipal = "";
            String subDirectorio = "formulario\\";
            Path direccion;
            parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            dirPrincipal = parametroPath.getPrpPath();

            //obtengo la secuencia
            try {
                secuenciaControlador.generarSecuencia("DIG");
                //obtengo el siguiente valor de la secuencia
                setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuencia = getSecuencia().getSecActual();
                getSecuencia().setSecActual(auxSecuencia + 1);
                //asigno la secuencia
                nombreArchivo = certificadoCargaSeleccionado.getCdcDocumento() + "_" + secuenciaControlador.getControladorBb().getNumeroTramite().toString();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al obtener la Secuencia");
                e.printStackTrace();
            }

            //Crear archivo y copiar
            try (InputStream input = is) {
                direccion = Paths.get(dirPrincipal + subDirectorio + nombreArchivo + "." + extension);
                file = Files.createFile(direccion);
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al subir archivo");
                e.printStackTrace();
            }
            //actualizar certificado carga seleccionado
            try {
                List<CertificadoCarga> listaCertCargaEdit = new ArrayList<>();
                listaCertCargaEdit = servicioCertificadoCarga.listarCertifPorNumFacturaPorUsuId(certificadoCargaSeleccionado.getFacId().getFacId(), certificadoCargaSeleccionado.getUsuId().getUsuId());
                for (CertificadoCarga certificadoCarga : listaCertCargaEdit) {
                    certificadoCarga.setCdcEstado("I");
                    servicioCertificadoCarga.edit(certificadoCarga);
                }
                //certificadoCargaSeleccionado.setCdcEstado("I");
                //servicioCertificadoCarga.edit(certificadoCargaSeleccionado);

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar certificado carga");
                e.printStackTrace();
            }

            //crear nuevo formulario digital
            try {
                factura = servicioFactura.buscarPorNumFactura(certificadoCargaSeleccionado.getCdcDocumento());
                formularioDigital = new FormularioDigital(0L, nombreArchivo, subDirectorio, extension, "A",
                        dataManagerSesion.getUsuarioLogeado().getUsuLogin(), Calendar.getInstance().getTime());
                formularioDigital.setFacId(factura);
                servicioFormularioDigital.edit(formularioDigital);
//                tramiteUtil.insertarTramiteAccion(certificadoCargaSeleccionado.getCdcDocumento(),
//                        repertorioDigital.getRtdNombreArchivo(),
//                        "Subida de Archivo ",
//                        repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstado(),
//                        repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstadoRegistro(),null);
            } catch (Exception e) {
                System.out.println(e);
                JsfUtil.addErrorMessage("Error al ingresar formulario digital");
            }

            try {
                Boolean cambiarEstado = false;
                String verificarEstado = "";
                listaCertificadoCargaPorDocPorUsuId = servicioCertificadoCarga.listarPoDocumentoPorUsuId(
                        dataManagerSesion.getUsuarioLogeado().getUsuId(), certificadoCargaSeleccionado.getCdcDocumento());
                //recorro la lista de certificado carga para verificar que todos tenga I para  habilitar cambiarEstado(true)
                for (CertificadoCarga certifCarga : listaCertificadoCargaPorDocPorUsuId) {

                    //cambiarEstado = certifCarga.getCdcEstado().equals("I");
                    verificarEstado = verificarEstado + certifCarga.getCdcEstado();

                }
                System.out.println("ESTADO: " + verificarEstado);
                if (!verificarEstado.contains("A")) {
                    cambiarEstado = true;
                }
                if (cambiarEstado) {

                    for (CertificadoCarga certCarga : listaCertificadoCargaPorDocPorUsuId) {
                        if (certCarga.getCdcDocumento().equals(certificadoCargaSeleccionado.getCdcDocumento())) {
                            String numFactura = certificadoCargaSeleccionado.getCdcDocumento();
                            TipoCertificado tipoCertificado = servicioTipoCertificado.buscarPor_Factura(numFactura);
                            Usuario usuarioAsignado =null;

                            switch (tipoCertificado.getTroTipo()) {
                                case "G":
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("GRV", "CERTIFICADO GRAVAMEN", 1);
                                    break;
                                case "R":
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("BNR", "CERTIFICADO BIENES RAICES", 1);
                                    break;
                                case "B":
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("BQD", "CERTIFICADO BUSQUEDA", 1);
                                    break;
                                case "N":
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("NMB", "CERTIFICADO NOMBRAMIENTOS", 1);
                                    break;
                                case "P":
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("PPD", "CERTIFICADO PROPIEDAD", 1);
                                    break;
                                case "E":
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("ETP", "CERTIFICADO ESTATUTO PERSONAL", 1);
                                    break;
                                case "M":
                                    usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MIF", "CERTIFICADO MATRICULAS INMOBILIARIAS", 1);
                                    break;
                            }
                            
                            CertificadoCarga nuevoCertCar = new CertificadoCarga();
                            //nuevoCertCar = certCarga;
                            nuevoCertCar.setCdcDocumento(certificadoCargaSeleccionado.getCdcDocumento());
                            nuevoCertCar.setCdcTipo("CER");
                            nuevoCertCar.setUsuId(usuarioAsignado);
                            nuevoCertCar.setCdcEstado("A");
                            nuevoCertCar.setFacId(factura);
                            nuevoCertCar.setCdcEstadoProceso("ACTIVO");
                            nuevoCertCar.setCdcCantidadCertificado(certificadoCargaSeleccionado.getCdcCantidadCertificado());
                            nuevoCertCar.setCdcCantidadDerecho(certificadoCargaSeleccionado.getCdcCantidadDerecho());
                            nuevoCertCar.setCdcUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            nuevoCertCar.setCdcFHR(Calendar.getInstance().getTime());

                            servicioCertificadoCarga.create(nuevoCertCar);

                            CertificadoAccion certAccion = new CertificadoAccion();
                            certAccion.setCtaNumeroDocumento(certCarga.getCdcDocumento());
                            certAccion.setCtaEstadoProceso("DFC");
                            certAccion.setCtaEstadoRegistro("A");
                            certAccion.setCtaObservacion("Creación de archivo digitalizado");
                            certAccion.setCtaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            certAccion.setCtaFHR(Calendar.getInstance().getTime());
                            certificadoAccionDao.create(certAccion);

                            CertificadoAccion certAc = new CertificadoAccion();
                            certAc.setCtaNumeroDocumento(certCarga.getCdcDocumento());
                            certAc.setCtaEstadoProceso("DFC");
                            certAc.setCtaEstadoRegistro("A");
                            certAc.setCtaObservacion("Usuario Asignado a " + usuarioAsignado.getUsuLogin());
                            certAc.setCtaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            certAc.setCtaFHR(Calendar.getInstance().getTime());
                            certAc.setCtaUsuarioAsignado(usuarioAsignado.getUsuLogin());
                            certificadoAccionDao.create(certAc);

                        }
                    }

                }
            } catch (Exception e) {
                System.out.println(e);
                JsfUtil.addErrorMessage("Error al generar Certifcado carga laboral");
            }

            secuenciaServicio.guardar(getSecuencia());

            //inicializando
            inicializar();

            JsfUtil.addSuccessMessage("Exito. ¡Archivo subido! ");

        } catch (Exception e) {
            System.out.println(e);
            JsfUtil.addErrorMessage("Error general al subir archivo");
        }

    }

}
