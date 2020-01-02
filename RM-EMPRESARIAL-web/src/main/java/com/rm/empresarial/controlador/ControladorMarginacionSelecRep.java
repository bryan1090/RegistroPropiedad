/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Bryan_Mora
 */
@Named(value = "controladorMarginacionSelecRep")
@ViewScoped
public class ControladorMarginacionSelecRep implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Inject
    private TramiteUtil tramiteUtil;

    @Getter
    @Setter
    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @Getter
    @Setter
    @EJB
    private MarginacionServicio servicioMarginacion;

    @EJB
    private TramiteServicio servicioTramite;

    @Getter
    @Setter
    List<RepertorioUsuario> listaRepUsu;

    @Getter
    @Setter
    private Long IDusuarioLogeado;

    @EJB
    private TipoTramiteDao tipoTramiteDao;

    @EJB
    private TramiteDetalleDao tramiteDetalleDao;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;

    @EJB
    private UsuarioServicio servicioUsuario;

    @EJB
    private CargaLaboralServicio servicioCargaLaboral;

//    @Getter
//    @Setter
//    private Long IDrepertorioUsuarioSeleccionado;
    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSeleccionado;

    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSelec;

    @Getter
    @Setter
    private TipoTramite tipoTramite;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalle;
    @Getter
    @Setter
    private TramiteAccion tramiteAccion;

    public ControladorMarginacionSelecRep() {
        tramiteAccion = new TramiteAccion();
    }

    @PostConstruct
    public void postConstructor() {
        IDusuarioLogeado = dataManagerSesion.getUsuarioLogeado().getUsuId();

    }

    public List<RepertorioUsuario> getListaRepertorio() throws ServicioExcepcion {
        listaRepUsu = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(IDusuarioLogeado, "MRG");
        if (listaRepUsu.isEmpty()) {
            JsfUtil.addErrorMessage("Usted no tiene asignado ningun repertorio");
        }
        return listaRepUsu;
    }

    public void redireccionar() throws IOException {
        actualizarEstadoProcesoRepUsuSel();

        FacesContext.getCurrentInstance().getExternalContext().redirect(
                "/RM-EMPRESARIAL-web/paginas/PROCESOS/Marginacion/Marginacion.xhtml?numero="
                + repertorioUsuarioSeleccionado.getRepNumero().getRepNumero());
    }

    public void actualizarEstadoProcesoRepUsuSel() {

        try {
            if (repertorioUsuarioSeleccionado.getRpuEstadoProceso().equals("ACTIVO")) {
                repertorioUsuarioSeleccionado.setRpuEstadoProceso("PROCESO");
            }
            servicioRepertorioUsuario.edit(repertorioUsuarioSeleccionado);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void cambiarEstado(Tramite tramiteParam) throws ServicioExcepcion {

        Boolean estanTodosRepertoriosMarginados = servicioMarginacion.estanTodosRepertoriosMarginados(
                tramiteParam.getTraNumero());
        
        boolean estanTodosEnEstadoTerminado = false;
        listaRepUsu = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(IDusuarioLogeado, "MRG");
        for (RepertorioUsuario repertorioUsuario : listaRepUsu) {
            if (repertorioUsuario.getRepNumero().getTraNumero().equals(tramiteParam)) {
                if (repertorioUsuario.getRpuEstadoProceso()!=null && repertorioUsuario.getRpuEstadoProceso().equals("TERMINADO")) {
                    estanTodosEnEstadoTerminado = true;
                } else {
                    estanTodosEnEstadoTerminado = false;
                    break;
                }
            }
        }
        if (estanTodosRepertoriosMarginados && estanTodosEnEstadoTerminado) {
            listaRepUsu = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(IDusuarioLogeado, "MRG");
            for (RepertorioUsuario repertorioUsuario : listaRepUsu) {
                if (repertorioUsuario.getRepNumero().getTraNumero().equals(tramiteParam)) {
                    repertorioUsuario.setRpuEstado("I");
                    servicioRepertorioUsuario.edit(repertorioUsuario);
                }
            }

            try {
                tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(repertorioUsuarioSelec.getRepNumero().getTraNumero().getTraNumero(), repertorioUsuarioSelec.getRepNumero().getRepTtrDescripcion());
                tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
                if (tipoTramite.getTtrCodigo() != null) {
                    Usuario usuarioAsignado = new Usuario();
                    switch (tipoTramite.getTtrCodigo()) {
                        case 7:
                        case 9:
                        case 10:
                            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(tramiteParam.getTraNumero(), "MPH"));
                            if (tramiteAccion == null) {
                                //codigo CargaLaboral
                                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MPH", "PHMATRICULACION", 1);
                                cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSelec.getUsuId().getUsuId(), repertorioUsuarioSelec.getRpuTipo());
                            } else {
                                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                                int auxCarga = cargaAdd.getCarAsignado() + 1;
                                cargaAdd.setCarAsignado((short) auxCarga);
                                servicioCargaLaboral.edit(cargaAdd);
                            }
                            for (RepertorioUsuario repUsu : listaRepUsu) {
                                if (tramiteParam.equals(
                                        repUsu.getRepNumero().getTraNumero())) {
                                    RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
                                    nuevoRepUsu = repUsu;
                                    nuevoRepUsu.setRpuTipo("MPH");
                                    nuevoRepUsu.setUsuId(usuarioAsignado);
                                    nuevoRepUsu.setRpuEstado("A");
                                    nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
                                    servicioRepertorioUsuario.create(nuevoRepUsu);

                                    tramiteUtil.insertarTramiteAccion(nuevoRepUsu.getRepNumero().getTraNumero(),
                                            nuevoRepUsu.getRepNumero().getRepNumero().toString(),
                                            "Repertorio " + nuevoRepUsu.getRepNumero().getRepNumero()
                                            + " asignado al usuario " + usuarioAsignado.getUsuLogin(),
                                            nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
                                            nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                            usuarioAsignado.getUsuLogin());
                                }
                            }
                            break;

                        default:
                            setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(tramiteParam.getTraNumero(), "MAT"));
                            if (tramiteAccion == null) {
                                //codigo CargaLaboral
                                usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MAT", "Matriculacion", 1);
                                cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSelec.getUsuId().getUsuId(), repertorioUsuarioSelec.getRpuTipo());
                            } else {
                                usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                                CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                                int auxCarga = cargaAdd.getCarAsignado() + 1;
                                cargaAdd.setCarAsignado((short) auxCarga);
                                servicioCargaLaboral.edit(cargaAdd);
                            }
                            for (RepertorioUsuario repUsu : listaRepUsu) {
                                if (tramiteParam.equals(
                                        repUsu.getRepNumero().getTraNumero())) {
                                    RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
                                    nuevoRepUsu = repUsu;
                                    nuevoRepUsu.setRpuTipo("MAT");
                                    nuevoRepUsu.setUsuId(usuarioAsignado);
                                    nuevoRepUsu.setRpuEstado("A");
                                    nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
                                    servicioRepertorioUsuario.create(nuevoRepUsu);

                                    tramiteUtil.insertarTramiteAccion(nuevoRepUsu.getRepNumero().getTraNumero(),
                                            nuevoRepUsu.getRepNumero().getRepNumero().toString(),
                                            "Repertorio " + nuevoRepUsu.getRepNumero().getRepNumero()
                                            + " asignado al usuario " + usuarioAsignado.getUsuLogin(),
                                            nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
                                            nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                            usuarioAsignado.getUsuLogin());
                                }
                            }

                    }
//                    if (tipoTramite.getTtrCodigo() == 7) {
//                        Usuario usuarioAsignado = new Usuario();
//                        setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(tramiteParam.getTraNumero(), "MPH"));
//                        if (tramiteAccion == null) {
//                            //codigo CargaLaboral
//                            usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MPH", "PHMATRICULACION", 1);
//                        } else {
//                            usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUserAsg());
//                            CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
//                            int auxCarga = cargaAdd.getCarAsignado() + 1;
//                            cargaAdd.setCarAsignado((short) auxCarga);
//                            servicioCargaLaboral.edit(cargaAdd);
//                        }
//                        for (RepertorioUsuario repUsu : listaRepUsu) {
//                            if (tramiteParam.equals(
//                                    repUsu.getRepNumero().getTraNumero())) {
//                                RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
//                                nuevoRepUsu = repUsu;
//                                nuevoRepUsu.setRpuTipo("MPH");
//                                nuevoRepUsu.setUsuId(usuarioAsignado);
//                                nuevoRepUsu.setRpuEstado("A");
//                                nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
//                                servicioRepertorioUsuario.create(nuevoRepUsu);
//
//                                tramiteUtil.insertarTramiteAccion(nuevoRepUsu.getRepNumero().getTraNumero(),
//                                        nuevoRepUsu.getRepNumero().getRepNumero().toString(),
//                                        "Repertorio " + nuevoRepUsu.getRepNumero().getRepNumero()
//                                        + " asignado al usuario " + usuarioAsignado.getUsuLogin(),
//                                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
//                                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
//                                        usuarioAsignado.getUsuLogin());
//                            }
//                        }
//
//                    } else {
//                        Usuario usuarioAsignado = new Usuario();
//                        setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(tramiteParam.getTraNumero(), "MAT"));
//                        if (tramiteAccion == null) {
//                            //codigo CargaLaboral
//                            usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MAT", "MarginacionSelectRep", 1);
//                        } else {
//                            usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUserAsg());
//                            CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
//                            int auxCarga = cargaAdd.getCarAsignado() + 1;
//                            cargaAdd.setCarAsignado((short) auxCarga);
//                            servicioCargaLaboral.edit(cargaAdd);
//                        }
//                        for (RepertorioUsuario repUsu : listaRepUsu) {
//                            if (tramiteParam.equals(
//                                    repUsu.getRepNumero().getTraNumero())) {
//                                RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
//                                nuevoRepUsu = repUsu;
//                                nuevoRepUsu.setRpuTipo("MAT");
//                                nuevoRepUsu.setUsuId(usuarioAsignado);
//                                nuevoRepUsu.setRpuEstado("A");
//                                nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
//                                servicioRepertorioUsuario.create(nuevoRepUsu);
//
//                                tramiteUtil.insertarTramiteAccion(nuevoRepUsu.getRepNumero().getTraNumero(),
//                                        nuevoRepUsu.getRepNumero().getRepNumero().toString(),
//                                        "Repertorio " + nuevoRepUsu.getRepNumero().getRepNumero()
//                                        + " asignado al usuario " + usuarioAsignado.getUsuLogin(),
//                                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
//                                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
//                                        usuarioAsignado.getUsuLogin());
//                            }
//                        }
//                    }

                } else {
                    Usuario usuarioAsignado = new Usuario();
                    setTramiteAccion(servicioTramiteAccion.buscarUser_TramiteConRepertorioAsignado(tramiteParam.getTraNumero(), "MAT"));
                    if (tramiteAccion == null) {
                        //codigo CargaLaboral
                        usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MAT", "Matriculacion", 1);
                    } else {
                        usuarioAsignado = servicioUsuario.encontrarUsuarioPorNombreUsuario(tramiteAccion.getTmaUser());
                        CargaLaboral cargaAdd = servicioCargaLaboral.buscarPorUsuario(usuarioAsignado);
                        int auxCarga = cargaAdd.getCarAsignado() + 1;
                        cargaAdd.setCarAsignado((short) auxCarga);
                        servicioCargaLaboral.edit(cargaAdd);
                    }
                    for (RepertorioUsuario repUsu : listaRepUsu) {
                        if (tramiteParam.equals(
                                repUsu.getRepNumero().getTraNumero())) {
                            RepertorioUsuario nuevoRepUsu = new RepertorioUsuario();
                            nuevoRepUsu = repUsu;
                            nuevoRepUsu.setRpuTipo("MAT");
                            nuevoRepUsu.setUsuId(usuarioAsignado);
                            nuevoRepUsu.setRpuEstado("A");
                            nuevoRepUsu.setRpuEstadoProceso("ACTIVO");
                            servicioRepertorioUsuario.create(nuevoRepUsu);

                            tramiteUtil.insertarTramiteAccion(nuevoRepUsu.getRepNumero().getTraNumero(),
                                    nuevoRepUsu.getRepNumero().getRepNumero().toString(),
                                    "Repertorio " + nuevoRepUsu.getRepNumero().getRepNumero()
                                    + " asignado al usuario " + usuarioAsignado.getUsuLogin(),
                                    nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
                                    nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                    usuarioAsignado.getUsuLogin());
                        }
                    }

                }

            } catch (Exception e) {
                System.out.println(e);
                JsfUtil.addErrorMessage("Error al generar nuevos repertorios usuario con estado 'MAT'");
            }
            try {
                tramiteParam.setTraEstado("MAT");
                servicioTramite.edit(tramiteParam);

                tramiteUtil.insertarTramiteAccion(tramiteParam, tramiteParam.getTraNumero().toString(),
                        "Trámite " + tramiteParam.getTraNumero().toString() + " pasó a matriculación",
                        tramiteParam.getTraEstado(), tramiteParam.getTraEstadoRegistro(), null);

                JsfUtil.addSuccessMessage("Tramite actualizado");
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar estado del Trámite");

            }

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(
                        "/RM-EMPRESARIAL-web/paginas/PROCESOS/Marginacion/MarginacionSelectRep.xhtml");
            } catch (IOException ex) {
                JsfUtil.addErrorMessage("Error al redireccionar");

                Logger.getLogger(MarginacionControler.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {

            JsfUtil.addErrorMessage("Aún no se han marginado todos "
                    + "los repertorios asignados al trámite " + tramiteParam.getTraNumero());
//                    +servicioMarginacion.listarRepertoriosNoMarginados(tramiteParam.getTraNumero()
        }

    }

}
