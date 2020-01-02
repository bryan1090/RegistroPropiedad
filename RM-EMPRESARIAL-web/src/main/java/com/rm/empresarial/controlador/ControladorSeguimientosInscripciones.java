/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.ControladorConsultaTramiteCertificadoBb;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.dao.CertificadoAccionDao;
import com.rm.empresarial.dao.TramiteAccionDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.CertificadoAccion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "controladorSeguimientosInscripciones")
@ViewScoped
public class ControladorSeguimientosInscripciones implements Serializable {

    @Getter
    @Setter
    private String nTramite;
    @Getter
    @Setter
    private String nCertificado;

    @Inject
    @Getter
    @Setter
    private ControladorConsultaTramiteCertificadoBb controladorConsultaTramiteCertificadoBb;

    @EJB
    private TramiteServicio servicioTramite;

    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;
    @EJB
    private CertificadoAccionDao certificadoAccionDao;

    @EJB
    private CertificadoServicio servicioCertificado;

    @EJB
    private TramiteAccionDao tramiteAccionDao;

    @EJB
    private CertificadoAccionDao servicioCertificadoAccion;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private PersonaServicio servicioPersona;

    @Getter
    @Setter
    private TramiteAccion tramiteAccionSeleccionado;

    @Getter
    @Setter
    private CertificadoAccion certificadoAccionSeleccionado;

    @Getter
    @Setter
    private List<TramiteAccion> listaTramiteAccion;

    @Getter
    @Setter
    private List<CertificadoAccion> listaCertificadoAccion;

    @Getter
    @Setter
    private TreeNode arbol;

    @Getter
    @Setter
    private TreeNode nodoSeleccionado;

    @Getter
    @Setter
    private String seleccion;

    @PostConstruct
    public void postControladorSeguimientosInscripciones() {
        Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        if (params.get("numTramite") != null) {
            nTramite = params.get("numTramite").toString();
        }else if (params.get("numCertificado") != null) {
            nCertificado = params.get("numCertificado").toString();
        }
        if (nTramite != null) {
            seleccion = "T";
            certificadoAccionSeleccionado = new CertificadoAccion();
            controladorConsultaTramiteCertificadoBb.setCertificadoSeleccionado(new CertificadoCarga());
            listaCertificadoAccion = new ArrayList<>();
            listaTramiteAccion = new ArrayList<>();
            cargarArbol(Long.valueOf(nTramite));
        } else if (nCertificado != null) {
            seleccion = "C";
            tramiteAccionSeleccionado = new TramiteAccion();
            controladorConsultaTramiteCertificadoBb.setTramiteSeleccionado(new Tramite());
            listaCertificadoAccion = new ArrayList<>();
            listaTramiteAccion = new ArrayList<>();
            nTramite = "";
            cargarArbol(Long.valueOf("0"));
        } else {
            seleccion = "T";
            tramiteAccionSeleccionado = new TramiteAccion();
            certificadoAccionSeleccionado = new CertificadoAccion();
            controladorConsultaTramiteCertificadoBb.setTramiteSeleccionado(new Tramite());
            controladorConsultaTramiteCertificadoBb.setCertificadoSeleccionado(new CertificadoCarga());
            listaCertificadoAccion = new ArrayList<>();
            listaTramiteAccion = new ArrayList<>();
        }

    }

    public ControladorSeguimientosInscripciones() {

    }

    public void refrescar() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/seguimientosInscripciones/seguimientosInscripciones.xhtml");
    }
    
    public void cancelar() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/seguimientosInscripciones/ConsultaTramiteCertificado.xhtml");
    }

    public void clear() {
        setTramiteAccionSeleccionado(null);
        setCertificadoAccionSeleccionado(null);
    }

    public void onNodeSelect(NodeSelectEvent event) throws ServicioExcepcion {
        String[] parts = event.getTreeNode().toString().split("[: -]+");
        if (seleccion.equals("T")) {
            setListaTramiteAccion(null);

            switch (nodoSeleccionado.getType()) {
                case "mat":
                    setListaTramiteAccion(tramiteAccionDao.listarProceso(nTramite, "MAT"));
                    break;
                case "ins":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "INS"));
                    break;
                case "fme":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "FME"));
                    break;
                case "pro":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "PRO"));
                    break;
                case "etg":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "ETG"));
                    break;
                case "dig":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "DIG"));
                    break;
                case "caj":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "CAJ"));
                    break;
                case "imp":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "IMP"));
                    break;
                case "rvt":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "RVT"));
                    break;
                case "rep":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "REP"));
                    break;
                case "fin":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "FIN"));
                    break;
                case "mrg":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "MRG"));
                    break;
                case "mph":
                    setListaTramiteAccion(servicioTramiteAccion.listarProceso(nTramite, "MPH"));
                    break;
                default:
                    break;
            }
        } else if (seleccion.equals("C")) {
            setListaCertificadoAccion(null);
            switch (nodoSeleccionado.getType()) {
                case "caj":
                    setListaCertificadoAccion(servicioCertificadoAccion.listarPorNcertificadoEproceso(nCertificado, "CAJ"));
                    break;
                case "rfw":
                    setListaCertificadoAccion(servicioCertificadoAccion.listarPorNcertificadoEproceso(nCertificado, "RFW"));
                    break;
                case "dfc":
                    setListaCertificadoAccion(servicioCertificadoAccion.listarPorNcertificadoEproceso(nCertificado, "DFC"));
                    break;
                case "cer":
                    setListaCertificadoAccion(servicioCertificadoAccion.listarPorNcertificadoEproceso(nCertificado, "CER"));
                    break;
                case "imc":
                    setListaCertificadoAccion(servicioCertificadoAccion.listarPorNcertificadoEproceso(nCertificado, "IMC"));
                    break;
                case "fic":
                    setListaCertificadoAccion(servicioCertificadoAccion.listarPorNcertificadoEproceso(nCertificado, "FIC"));
                    break;
                default:
                    break;
            }
        }
    }

    public void formatearNtramite() {
        setNTramite(nTramite.replaceAll("\\s", ""));
    }

    public void formatearNcertificado() {
        setNCertificado(nCertificado.replaceAll("\\s", ""));
    }

    public void cargarArbol(Long tramite) {
        clear();
        if (seleccion.equals("T")) {
            formatearNtramite();
            String pattern = "dd/MM/yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            if (!nTramite.equals("")) {
                try {
                    if (servicioTramite.buscarTramitePorNumero(tramite) != null) {
                        arbol = new DefaultTreeNode("Root", null);

                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "RVT"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo9 = new DefaultTreeNode("rvt", "Revisión Trámite", arbol);
                            nodo9.getChildren().add(new DefaultTreeNode("rvt", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-RVT", nodo9));
                            nodo9.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo9.getChildren().add(new DefaultTreeNode("Receptor: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            nodo9.getChildren().add(new DefaultTreeNode("Asignado a: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }
                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProceso(tramite, "PRO"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo3 = new DefaultTreeNode("pro", "Proforma", arbol);
                            nodo3.getChildren().add(new DefaultTreeNode("pro", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-PRO", nodo3));
                            nodo3.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo3.getChildren().add(new DefaultTreeNode("Proformador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                        }
                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProceso(tramite, "CAJ"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo6 = new DefaultTreeNode("caj", "Caja", arbol);
                            nodo6.getChildren().add(new DefaultTreeNode("caj", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-CAJ", nodo6));
                            nodo6.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo6.getChildren().add(new DefaultTreeNode("Cajero: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            //nodo6.getChildren().add(new DefaultTreeNode("Matriculador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }
                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "REP"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo12 = new DefaultTreeNode("rep", "Repertorio", arbol);
                            nodo12.getChildren().add(new DefaultTreeNode("rep", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-REP", nodo12));
                            nodo12.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo12.getChildren().add(new DefaultTreeNode("Repertorista: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            nodo12.getChildren().add(new DefaultTreeNode("Asignado a Digitalizador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }
                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "DIG"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo4 = new DefaultTreeNode("dig", "Digitalización", arbol);
                            nodo4.getChildren().add(new DefaultTreeNode("dig", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-DIG", nodo4));
                            nodo4.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo4.getChildren().add(new DefaultTreeNode("Digitalizador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            nodo4.getChildren().add(new DefaultTreeNode("Asignado a Marginador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }
                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "MRG"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo14 = new DefaultTreeNode("mrg", "Marginacion", arbol);
                            nodo14.getChildren().add(new DefaultTreeNode("mrg", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-MRG", nodo14));
                            nodo14.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo14.getChildren().add(new DefaultTreeNode("Marginador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            nodo14.getChildren().add(new DefaultTreeNode("Asignado a Matriculador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }

                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "MAT"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo8 = new DefaultTreeNode("mat", "Matriculación", arbol);
                            nodo8.getChildren().add(new DefaultTreeNode("mat", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-MAT", nodo8));
                            nodo8.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo8.getChildren().add(new DefaultTreeNode("Matriculador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            nodo8.getChildren().add(new DefaultTreeNode("Asignado a Inscriptor: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }

                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "MPH"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo9 = new DefaultTreeNode("mph", "Matriculación PH", arbol);
                            nodo9.getChildren().add(new DefaultTreeNode("mph", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-MAT", nodo9));
                            nodo9.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo9.getChildren().add(new DefaultTreeNode("Matriculador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            nodo9.getChildren().add(new DefaultTreeNode("Asignado a Inscriptor: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }

                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "INS"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo1 = new DefaultTreeNode("ins", "Inscripcion", arbol);
                            nodo1.getChildren().add(new DefaultTreeNode("ins", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-INS", nodo1));
                            nodo1.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo1.getChildren().add(new DefaultTreeNode("Inscriptor: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            //nodo1.getChildren().add(new DefaultTreeNode("Proformador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }
                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "IMP"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo7 = new DefaultTreeNode("imp", "Impresion Libro", arbol);
                            nodo7.getChildren().add(new DefaultTreeNode("imp", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-IMP", nodo7));
                            nodo7.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo7.getChildren().add(new DefaultTreeNode("Impresor: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            //nodo7.getChildren().add(new DefaultTreeNode("Impresor: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }
                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "FME"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo2 = new DefaultTreeNode("fme", "Firma electronica", arbol);
                            nodo2.getChildren().add(new DefaultTreeNode("fme", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-FME", nodo2));
                            nodo2.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo2.getChildren().add(new DefaultTreeNode("Firmado Elect.: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                        }

                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "ETG"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo5 = new DefaultTreeNode("etg", "Entrega", arbol);
                            nodo5.getChildren().add(new DefaultTreeNode("etg", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-ETG", nodo5));
                            nodo5.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo5.getChildren().add(new DefaultTreeNode("Entrega: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            //nodo5.getChildren().add(new DefaultTreeNode("Digitalizador: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }
                        setTramiteAccionSeleccionado(tramiteAccionDao.obtenerPorProcesoConUserAsignado(tramite, "FIN"));
                        if (getTramiteAccionSeleccionado() != null) {
                            TreeNode nodo13 = new DefaultTreeNode("fin", "Finalizacion Tramite", arbol);
                            nodo13.getChildren().add(new DefaultTreeNode("fin", "Ver: " + tramiteAccionSeleccionado.getTraNumero().getTraNumero() + "-FIN", nodo13));
                            nodo13.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(tramiteAccionSeleccionado.getTmaFHR())));
                            nodo13.getChildren().add(new DefaultTreeNode("Cajero: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUser())));
                            //nodo13.getChildren().add(new DefaultTreeNode("Sri: " + servicioPersona.buscarPersonaPorUsuario(tramiteAccionSeleccionado.getTmaUserAsg())));
                        }
                    } else {
                        arbol = new DefaultTreeNode("Root", null);
                        JsfUtil.addErrorMessage("Tramite No Encontrado");
                    }
                } catch (ServicioExcepcion ex) {
                    System.out.print(ex);
                }

            }

        } else if (seleccion.equals("C")) {
            formatearNcertificado();
            String pattern = "dd/MM/yyyy HH:mm:ss";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            if (!nCertificado.equals("")) {
                try {
                    if (certificadoAccionDao.listarCertificado(nCertificado) != null) {
                        arbol = new DefaultTreeNode("Root", null);
                        setCertificadoAccionSeleccionado(servicioCertificadoAccion.obtenerPorNcertificadoEproceso(nCertificado, "CAJ"));
                        if (getCertificadoAccionSeleccionado() != null) {
                            TreeNode nodo1 = new DefaultTreeNode("caj", "Caja", arbol);
                            nodo1.getChildren().add(new DefaultTreeNode("caj", "Ver: " + certificadoAccionSeleccionado.getCtaNumeroDocumento() + "-CAJ", nodo1));
                            nodo1.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(certificadoAccionSeleccionado.getCtaFHR())));
                            nodo1.getChildren().add(new DefaultTreeNode("Cajero: " + servicioPersona.buscarPersonaPorUsuario(certificadoAccionSeleccionado.getCtaUser())));
                        }
                        setCertificadoAccionSeleccionado(servicioCertificadoAccion.obtenerPorNcertificadoEproceso(nCertificado, "DFC"));
                        if (getCertificadoAccionSeleccionado() != null) {
                            TreeNode nodo2 = new DefaultTreeNode("dfc", "Digitalización Formulario", arbol);
                            nodo2.getChildren().add(new DefaultTreeNode("dfc", "Ver: " + certificadoAccionSeleccionado.getCtaNumeroDocumento() + "-DFC", nodo2));
                            nodo2.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(certificadoAccionSeleccionado.getCtaFHR())));
                            nodo2.getChildren().add(new DefaultTreeNode("Digitalizador: " + servicioPersona.buscarPersonaPorUsuario(certificadoAccionSeleccionado.getCtaUser())));
                        }
                        setCertificadoAccionSeleccionado(servicioCertificadoAccion.obtenerPorNcertificadoEproceso(nCertificado, "RFW"));
                        if (getCertificadoAccionSeleccionado() != null) {
                            TreeNode nodo3 = new DefaultTreeNode("rfw", "Revisión Formulario Web", arbol);
                            nodo3.getChildren().add(new DefaultTreeNode("rfw", "Ver: " + certificadoAccionSeleccionado.getCtaNumeroDocumento() + "-RFW", nodo3));
                            nodo3.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(certificadoAccionSeleccionado.getCtaFHR())));
                            nodo3.getChildren().add(new DefaultTreeNode("Revisor: " + servicioPersona.buscarPersonaPorUsuario(certificadoAccionSeleccionado.getCtaUser())));
                        }
                        setCertificadoAccionSeleccionado(servicioCertificadoAccion.obtenerPorNcertificadoEproceso(nCertificado, "CER"));
                        if (getCertificadoAccionSeleccionado() != null) {
                            TreeNode nodo4 = new DefaultTreeNode("cer", "Generación Certificado", arbol);
                            nodo4.getChildren().add(new DefaultTreeNode("cer", "Ver: " + certificadoAccionSeleccionado.getCtaNumeroDocumento() + "-CER", nodo4));
                            nodo4.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(certificadoAccionSeleccionado.getCtaFHR())));
                            nodo4.getChildren().add(new DefaultTreeNode("Generador: " + servicioPersona.buscarPersonaPorUsuario(certificadoAccionSeleccionado.getCtaUser())));
                        }
                        setCertificadoAccionSeleccionado(servicioCertificadoAccion.obtenerPorNcertificadoEproceso(nCertificado, "IMC"));
                        if (getCertificadoAccionSeleccionado() != null) {
                            TreeNode nodo5 = new DefaultTreeNode("imc", "Impresión Certificado", arbol);
                            nodo5.getChildren().add(new DefaultTreeNode("imc", "Ver: " + certificadoAccionSeleccionado.getCtaNumeroDocumento() + "-IMC", nodo5));
                            nodo5.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(certificadoAccionSeleccionado.getCtaFHR())));
                            nodo5.getChildren().add(new DefaultTreeNode("Impresor: " + servicioPersona.buscarPersonaPorUsuario(certificadoAccionSeleccionado.getCtaUser())));
                        }
                        setCertificadoAccionSeleccionado(servicioCertificadoAccion.obtenerPorNcertificadoEproceso(nCertificado, "FIC"));
                        if (getCertificadoAccionSeleccionado() != null) {
                            TreeNode nodo6 = new DefaultTreeNode("fic", "Entrega Certificado", arbol);
                            nodo6.getChildren().add(new DefaultTreeNode("fic", "Ver: " + certificadoAccionSeleccionado.getCtaNumeroDocumento() + "-fic", nodo6));
                            nodo6.getChildren().add(new DefaultTreeNode("Fecha: " + simpleDateFormat.format(certificadoAccionSeleccionado.getCtaFHR())));
                            nodo6.getChildren().add(new DefaultTreeNode("Entrega: " + servicioPersona.buscarPersonaPorUsuario(certificadoAccionSeleccionado.getCtaUser())));
                        }
                    } else {
                        arbol = new DefaultTreeNode("Root", null);
                        JsfUtil.addErrorMessage("Certificado No Encontrado");
                    }
                } catch (ServicioExcepcion ex) {
                    System.out.print(ex);
                }

            }
        }
    }

}
