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
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
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
 * @author Bryan_Mora
 */
@Named(value = "controladorTramiteDigital")
@ViewScoped
public class ControladorTramiteDigital implements Serializable {

    //servicios
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;
    
    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;
    
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
    @EJB
    private UsuarioServicio servicioUsuario;
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
    
    @Getter
    @Setter
    private TramiteAccion tramiteAccion;
    @Getter
    @Setter
    private CargaLaboral cargaLaboralm;
    
    public ControladorTramiteDigital() {
        listaRepertorio = new ArrayList<>();
        listaTramites = new ArrayList<>();
        listaRepertorioUsuario = new ArrayList<>();
        tramiteAccion = new TramiteAccion();
    }
    
    @PostConstruct
    public void postConstructor() {
        inicializar();
        
    }
    
    public void inicializar() {
        try {
            listaRepertorioUsuario = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(dataManagerSesion.getUsuarioLogeado().getUsuId(), "REP");
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
            Boolean exitoSubirArchivo = false;
//            Path folder;
            String extension = "pdf";
            Path file;
            String nombreArchivo = "";
            String dirPrincipal = "";
            String subDirectorio = "tramite\\";
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
                nombreArchivo = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraNumero() + "_" + secuenciaControlador.getControladorBb().getNumeroTramite().toString();
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al obtener la Secuencia");
                e.printStackTrace();
            }

            //Crear archivo y copiar
            try (InputStream input = is) {
                direccion = Paths.get(dirPrincipal + subDirectorio + nombreArchivo + "." + extension);
                file = Files.createFile(direccion);
                Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
                exitoSubirArchivo = true;
            } catch (Exception e) {
                exitoSubirArchivo = false;
                JsfUtil.addErrorMessage("Error al subir archivo");
                e.printStackTrace();
            }
            if (exitoSubirArchivo) {
                //actualizo repertorio seleccionado
//                try {
//                    repertorioUsuarioSeleccionado.setRpuEstado("I");
//                    servicioRepertorioUsuario.edit(repertorioUsuarioSeleccionado);
//                } catch (Exception e) {
//                    JsfUtil.addErrorMessage("Error al actualizar repertorio usuarioAsignado");
//                    e.printStackTrace();
//                }

                //creo nuevo repertorio digital
                try {
                    repertorioDigital = new RepertorioDigital(0L, nombreArchivo, subDirectorio, extension, dataManagerSesion.getUsuarioLogeado().getUsuLogin(), Calendar.getInstance().getTime(), "A");
                    repertorioDigital.setRepNumero(repertorioUsuarioSeleccionado.getRepNumero());
                    servicioRepertorioDigital.edit(repertorioDigital);
                    tramiteUtil.insertarTramiteAccion(repertorioUsuarioSeleccionado.getRepNumero().getTraNumero(),
                            repertorioDigital.getRtdNombreArchivo(),
                            "Trámite " + repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraNumero() + " digitalización",
                            repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstado(),
                            repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                            null);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error al ingresar repertorio digital");
                }

                //Actualizo estado de repertorios
                try {
                    
                    for (RepertorioUsuario repUsu : listaRepertorioUsuario) {
                        if (repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().equals(
                                repUsu.getRepNumero().getTraNumero())) {
                            repUsu.setRpuEstado("I");
                            servicioRepertorioUsuario.edit(repUsu);
                            
                            tramiteUtil.insertarTramiteAccion(repertorioUsuarioSeleccionado.getRepNumero().getTraNumero(),
                                    repUsu.getRepNumero().getRepNumero().toString(),
                                    "Actualización del estado del repertorio " + repUsu.getRepNumero().getRepNumero().toString() + " a 'I'",
                                    repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstado(),
                                    repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                    null);
                        }
                        
                    }
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error al actualizar estado de repertorios");
                }

                //actualizo tramite
                try {
                    Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();
                    tramiteSeleccionado.setTraEstado("MRG");
                    servicioTramite.edit(tramiteSeleccionado);
                    
                    tramiteUtil.insertarTramiteAccion(tramiteSeleccionado,
                            repertorioDigital.getRtdNombreArchivo(),
                            "Trámite " + tramiteSeleccionado.getTraNumero() + " pasó a marginación",
                            "DIG",
                            tramiteSeleccionado.getTraEstadoRegistro(),
                            null);
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error al actualizar Tramite");
                }
                //CREO NUEVOS REPERTORIOS USUARIO CON usuarioAsignado asignado
                //codigo CargaLaboral

                try {
                    Usuario usuarioAsignado;
                    RepertorioUsuario nuevoRepUsu;
                    
                    switch (repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraClase()) {
                        case "J":
                            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(getRepertorioUsuarioSeleccionado().getRepNumero().getTraNumero().getTraNumero(), "MGL"));
                            if (tramiteAccion == null) {
                                //codigo CargaLaboral
                                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MGL", "MarginacionLegal", 1);
                                //revisar daniel//
                                cargaLaboralUtil.restarCargaAsignadaAlUsuario(dataManagerSesion.getUsuarioLogeado().getUsuId(), "DIG");
                            } else {
                                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                                int auxCarga = cargaAdd.getCarAsignado() + 1;
                                cargaAdd.setCarAsignado((short) auxCarga);
                                servicioCargaLaboral.edit(cargaAdd);
                            }
                            break;
                        default:
                            //codigo CargaLaboral
                            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(getRepertorioUsuarioSeleccionado().getRepNumero().getTraNumero().getTraNumero(), "MRG"));
                            if (tramiteAccion == null) {
                                //codigo CargaLaboral
                                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MRG", "repertorioDigital", 1);
                                cargaLaboralUtil.restarCargaAsignadaAlUsuario(dataManagerSesion.getUsuarioLogeado().getUsuId(), "DIG");
                            } else {
                                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                                int auxCarga = cargaAdd.getCarAsignado() + 1;
                                cargaAdd.setCarAsignado((short) auxCarga);
                                servicioCargaLaboral.edit(cargaAdd);
                            }
                            
                            break;
                    }
                    for (RepertorioUsuario repUsu : listaRepertorioUsuario) {
                        if (repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().equals(
                                repUsu.getRepNumero().getTraNumero())) {
                            switch (repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraClase()) {
                                case "J":
                                    nuevoRepUsu = new RepertorioUsuario();
                                    nuevoRepUsu.setRepNumero(repUsu.getRepNumero());
                                    nuevoRepUsu.setRpuTipo("MGL");
                                    nuevoRepUsu.setUsuId(usuarioAsignado);
                                    nuevoRepUsu.setRpuEstado("A");
                                    nuevoRepUsu.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                    nuevoRepUsu.setRpuFHR(Calendar.getInstance().getTime());
                                    nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
                                    
                                    servicioRepertorioUsuario.create(nuevoRepUsu);
                                    tramiteUtil.insertarTramiteAccion(repUsu.getRepNumero().getTraNumero(),
                                            nuevoRepUsu.getRepNumero().getRepNumero().toString(),
                                            "Repertorio " + nuevoRepUsu.getRepNumero().getRepNumero().toString() + " asignado al usuario " + usuarioAsignado.getUsuLogin(),
                                            "DIG",
                                            repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                            usuarioAsignado.getUsuLogin());
                                    break;
                                default:
                                    nuevoRepUsu = new RepertorioUsuario();
                                    nuevoRepUsu.setRepNumero(repUsu.getRepNumero());
                                    nuevoRepUsu.setRpuTipo("MRG");
                                    nuevoRepUsu.setUsuId(usuarioAsignado);
                                    nuevoRepUsu.setRpuEstado("A");
                                    nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
                                    nuevoRepUsu.setRpuUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                    nuevoRepUsu.setRpuFHR(Calendar.getInstance().getTime());
                                    servicioRepertorioUsuario.create(nuevoRepUsu);
                                    tramiteUtil.insertarTramiteAccion(repUsu.getRepNumero().getTraNumero(),
                                            nuevoRepUsu.getRepNumero().getRepNumero().toString(),
                                            "Repertorio " + nuevoRepUsu.getRepNumero().getRepNumero().toString() + " asignado al usuario " + usuarioAsignado.getUsuLogin(),
                                            "DIG",
                                            repertorioUsuarioSeleccionado.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                            usuarioAsignado.getUsuLogin());
                                    
                                    break;
                            }
                            
                        }
                    }
                    
                } catch (Exception e) {
                    JsfUtil.addErrorMessage("Error al generar nuevos repertorios usuario con estado 'MRG'");
                }

//                   Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MRG", "repertorioDigital", 1);
//                    RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
//                    nuevoRepUsu = repertorioUsuarioSeleccionado;
//                    nuevoRepUsu.setRepNumero(repertorioUsuarioSeleccionado.getRepNumero());
//                    nuevoRepUsu.setRpuTipo("MRG");
//                    nuevoRepUsu.setUsuId(usuarioAsignado);
//                    nuevoRepUsu.setRpuEstado("A");
//                    servicioRepertorioUsuario.create(nuevoRepUsu);
                JsfUtil.addSuccessMessage("Exito. ¡Archivo subido! ");
                
            }

//       
            //actualizo secuencia
            secuenciaServicio.guardar(getSecuencia());

            //inicializando
            inicializar();
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error general al subir archivo");
        }
        
    }
    
    public boolean agruparColumnaBoton(int indiceActual) {
        int indiceAnterior = 0;
        
        if (indiceActual > 0) {
            indiceAnterior = indiceActual - 1;
            if (listaRepertorioUsuario.get(indiceActual).getRepNumero().getTraNumero().getTraNumero().equals(
                    listaRepertorioUsuario.get(indiceAnterior).getRepNumero().getTraNumero().getTraNumero())) {
                return true;
            }
        }
        
        return false;
    }
}
