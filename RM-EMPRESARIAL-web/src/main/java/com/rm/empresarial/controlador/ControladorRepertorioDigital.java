/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioDigital;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.RepertorioDigitalServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.util.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorRepertorioDigital")
@ViewScoped
public class ControladorRepertorioDigital implements Serializable {

    //servicios
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
    private RepertorioUsuarioServicio servicioRepertorioUsuario;
    @EJB
    private ParametroPathServicio servicioParametroPath;
    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;
    @EJB
    private SecuenciaServicio secuenciaServicio;
    @EJB
    private RepertorioDigitalServicio servicioRepertorioDigital;
    @EJB
    private TramiteAccionServicio servicioTramiteAccion;
    @Inject
    private TramiteUtil tramiteUtil;
    //objetos, variables y listas
    @Getter
    @Setter
    List<Repertorio> listaRepertorio;

    @Getter
    @Setter
    List<RepertorioUsuario> listaRepertorioUsuario;

    @Getter
    @Setter
    List<Tramite> listaTramites;

    @Getter
    @Setter
    private UploadedFile file;

    @Getter
    @Setter
    private CargaLaboral cargaLaboral;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Getter
    @Setter
    private ParametroPath parametroPath;
    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSeleccionado;

    @Getter
    @Setter
    private Secuencia secuencia;
    @Getter
    @Setter
    private RepertorioDigital repertorioDigital;

    public ControladorRepertorioDigital() {
        listaRepertorio = new ArrayList<>();
        listaTramites = new ArrayList<>();
        listaRepertorioUsuario = new ArrayList<>();
    }

    @PostConstruct
    public void postConstructor() {
        inicializar();

    }

    public void inicializar() {
        try {
            listaRepertorioUsuario = servicioRepertorioUsuario.listarPorTipoPorEstado("REP", "A");
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
            String subDirectorio = "repertorio\\";
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
                nombreArchivo = repertorioUsuarioSeleccionado.getRepNumero().getRepNumero() + "_" + secuenciaControlador.getControladorBb().getNumeroTramite().toString();
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
            //actualizo repertorio seleccionado
            try {
                repertorioUsuarioSeleccionado.setRpuEstado("I");
                servicioRepertorioUsuario.edit(repertorioUsuarioSeleccionado);

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar repertorio usuario");
                e.printStackTrace();
            }

            //creo nuevo repertorio digital
            try {
                repertorioDigital = new RepertorioDigital(0L, nombreArchivo, subDirectorio, extension,
                        dataManagerSesion.getUsuarioLogeado().getUsuLogin(), Calendar.getInstance().getTime(), "A");
                repertorioDigital.setRepNumero(repertorioUsuarioSeleccionado.getRepNumero());
                servicioRepertorioDigital.edit(repertorioDigital);
                tramiteUtil.insertarTramiteAccion(repertorioUsuarioSeleccionado.getRepNumero().getTraNumero(),
                        repertorioDigital.getRtdNombreArchivo(),
                        "Subida de Archivo ",
                        repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstado(),
                        repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstadoRegistro(), null);
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al ingresar repertorio digital");
            }

            //actualizo tramite
            try {
                Boolean cambiarEstado = false;
                //recorro repertorio usuarioAsignado y verifico que todos tenga I para  habilitar cambiarEstado(true)
                for (RepertorioUsuario repUsu : listaRepertorioUsuario) {
                    if (repUsu.getRepNumero().getRepNumero().equals(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero())) {
                        cambiarEstado = repUsu.getRpuEstado().equals("I");
                    }
                }
                if (cambiarEstado) {

                    Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();
                    tramiteSeleccionado.setTraEstado("MRG");
                    servicioTramite.edit(tramiteSeleccionado);

                    tramiteUtil.insertarTramiteAccion(repertorioUsuarioSeleccionado.getRepNumero().getTraNumero(),
                            repertorioDigital.getRtdNombreArchivo(),
                            "Actualización del estado del Trámite",
                            repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstado(),
                            repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstadoRegistro(), null);

                    //CREO NUEVO REPERTORIO USUARIO SELECCIONADO
                    Usuario usuarioAsignado;
                    RepertorioUsuario nuevoRepUsu;

                    switch (tramiteSeleccionado.getTraClase()) {
                        case "J":
                            usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MGL", "MarginacionLegal", 1);
                            cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSeleccionado.getUsuId().getUsuId(), repertorioUsuarioSeleccionado.getRpuTipo());
                            
                            nuevoRepUsu = repertorioUsuarioSeleccionado;
                            nuevoRepUsu.setRepNumero(repertorioUsuarioSeleccionado.getRepNumero());
                            nuevoRepUsu.setRpuTipo("MGL");
                            nuevoRepUsu.setUsuId(usuarioAsignado);
                            nuevoRepUsu.setRpuEstado("A");
                            nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
                            servicioRepertorioUsuario.create(nuevoRepUsu);
                            tramiteUtil.insertarTramiteAccion(repertorioUsuarioSeleccionado.getRepNumero().getTraNumero(),
                                    nuevoRepUsu.getRepNumero().getRepNumero().toString(),
                                    "Asignación de repertorio a " + usuarioAsignado.getUsuLogin(),
                                    repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstado(),
                                    repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                    usuarioAsignado.getUsuLogin());
                            break;
                        default:
                            //codigo CargaLaboral
                            usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MRG", "repertorioDigital", 1);
                            cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSeleccionado.getUsuId().getUsuId(), repertorioUsuarioSeleccionado.getRpuTipo());
                            
                            nuevoRepUsu = repertorioUsuarioSeleccionado;
                            nuevoRepUsu.setRepNumero(repertorioUsuarioSeleccionado.getRepNumero());
                            nuevoRepUsu.setRpuTipo("MRG");
                            nuevoRepUsu.setUsuId(usuarioAsignado);
                            nuevoRepUsu.setRpuEstado("A");
                            servicioRepertorioUsuario.create(nuevoRepUsu);
                            tramiteUtil.insertarTramiteAccion(repertorioUsuarioSeleccionado.getRepNumero().getTraNumero(),
                                    nuevoRepUsu.getRepNumero().getRepNumero().toString(),
                                    "Asignación de repertorio a " + usuarioAsignado.getUsuLogin(),
                                    repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstado(),
                                    repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                    usuarioAsignado.getUsuLogin());
                            break;
                    }

                }
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar Tramite");
            }

//            try {
//                TramiteAccion nuevoTramAccn = new TramiteAccion(null,
//                        dataManagerSesion.getUsuarioLogeado().getUsuUser(),
//                        Calendar.getInstance().getTime(),
//                        repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().toString(),
//                        "DIG", "RA");
//
//                nuevoTramAccn.setTraNumero(repertorioUsuarioSeleccionado.getRepNumero().getTraNumero());
//                nuevoTramAccn.setTmaObservacion("DIGITALIZACIÓN CONTRATO");
//                servicioTramiteAccion.edit(nuevoTramAccn);
//            } catch (Exception e) {
//                JsfUtil.addErrorMessage("Error al ingresar Trámite Acción");
//                e.printStackTrace();
//            }
            //actualizo secuencia
            secuenciaServicio.guardar(getSecuencia());

            //inicializando
            inicializar();

            JsfUtil.addSuccessMessage("Exito. ¡Archivo subido! ");

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error general al subir archivo");
        }

    }

}
