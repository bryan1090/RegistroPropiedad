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
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
@Named(value = "controladorMarginacionLegalSelRep")
@ViewScoped
public class ControladorMarginacionLegalSelRep implements Serializable {

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
    TramiteValorServicio servicioTramiteValor;

    @EJB
    TipoTramiteServicio servicioTipoTramite;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;

    @EJB
    TipoTramiteServicio tipoTramitServicio;

    @EJB
    PropiedadServicio servicioPropiedad;

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
    List<Propiedad> listaPropiedadSelec;

    @Getter
    @Setter
    List<Propiedad> listaPropiedadPorPropietario;

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetaSelec;

    @Getter
    @Setter
    private Persona personaSelec;

    @Getter
    @Setter
    private Boolean bolSeSeleccionaronPropiedades;

    public ControladorMarginacionLegalSelRep() {
    }

    @PostConstruct
    public void postConstructor() {
        IDusuarioLogeado = dataManagerSesion.getUsuarioLogeado().getUsuId();

    }

    public List<RepertorioUsuario> getListaRepertorio() throws ServicioExcepcion {
        listaRepUsu = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(IDusuarioLogeado, "MGL");
        if (listaRepUsu.isEmpty()) {
            JsfUtil.addErrorMessage("Usted no tiene asignado ningun repertorio");
        }
        return listaRepUsu;
    }

    public void redireccionar() throws IOException, ServicioExcepcion {
        Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();

        if (!tramiteSeleccionado.getTraClase().equals("J")) {
            actualizarEstadoProcesoRepUsuSel();
            FacesContext.getCurrentInstance().getExternalContext().redirect(
                    "Marginacion.xhtml?numero="
                    + repertorioUsuarioSeleccionado.getRepNumero().getRepNumero());
        } else {
            List<TramiteDetalle> listaTramiteDet = new ArrayList<>();

            listaTramiteDet = servicioTramiteDetalle.listar_por_repertorio_tramite(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().intValue(), tramiteSeleccionado.getTraNumero());

            Long tipoTramiteId = new Long(0);
            for (TramiteDetalle tramiteDetalle1 : listaTramiteDet) {
                tipoTramiteId = tramiteDetalle1.getTdtTtrId();
            }
            TipoTramite tipoTram = tipoTramitServicio.buscarPorID(tipoTramiteId);
            
//            if (tipoTram.getTtrCodigo() == 3) {
                listaTramDetaSelec = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().intValue(), tramiteSeleccionado.getTraNumero(), "S");
//            } else {
//                listaTramDetaSelec = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSeleccionado.getRepNumero().getRepNumero().intValue(), tramiteSeleccionado.getTraNumero(), "N");
//            }

        }

    }

    public void actualizarEstadoProcesoRepUsuSel() {

        try {
            if (repertorioUsuarioSeleccionado.getRpuEstadoProceso() == null || repertorioUsuarioSeleccionado.getRpuEstadoProceso().equals("ACTIVO")) {
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
        if (estanTodosRepertoriosMarginados) {
            listaRepUsu = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(IDusuarioLogeado, "MGL");
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
                    if (tipoTramite.getTtrCodigo() == 7) {
                        Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MPH", "PHMATRICULACION", 1);
                        cargaLaboralUtil.restarCargaAsignadaAlUsuario(dataManagerSesion.getUsuarioLogeado().getUsuId(), "MGL");
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
                                        "Repertorio Usuario " + nuevoRepUsu.getRepNumero().getRepNumero()
                                        + "asignado al usuario: " + usuarioAsignado.getUsuLogin(),
                                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
                                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                        usuarioAsignado.getUsuLogin());
                            }
                        }

                    } else {

                        Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MAT", "Matriculacion", 1);
                        cargaLaboralUtil.restarCargaAsignadaAlUsuario(dataManagerSesion.getUsuarioLogeado().getUsuId(), "MGL");
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
                                        "Repertorio Usuario " + nuevoRepUsu.getRepNumero().getRepNumero()
                                        + "asignado al usuario: " + usuarioAsignado.getUsuLogin(),
                                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
                                        nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                        usuarioAsignado.getUsuLogin());
                            }
                        }
                    }

                } else {
                    Usuario usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("MAT", "Matriculacion", 1);
                    cargaLaboralUtil.restarCargaAsignadaAlUsuario(dataManagerSesion.getUsuarioLogeado().getUsuId(), "MGL");
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
                                    "Repertorio Usuario " + nuevoRepUsu.getRepNumero().getRepNumero()
                                    + "asignado al usuario: " + usuarioAsignado.getUsuLogin(),
                                    nuevoRepUsu.getRepNumero().getTraNumero().getTraEstado(),
                                    nuevoRepUsu.getRepNumero().getTraNumero().getTraEstadoRegistro(),
                                    usuarioAsignado.getUsuLogin());
                        }
                    }

                }

            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al generar nuevos repertorios usuario con estado 'MAT'");
            }
            try {
                tramiteParam.setTraEstado("MAT");
                servicioTramite.edit(tramiteParam);

                tramiteUtil.insertarTramiteAccion(tramiteParam, tramiteParam.getTraNumero().toString(),
                        "Actualización del estado de Tramite a 'MAT'",
                        tramiteParam.getTraEstado(), tramiteParam.getTraEstadoRegistro(), null);

                JsfUtil.addSuccessMessage("Tramite actualizado");
            } catch (Exception e) {
                JsfUtil.addErrorMessage("Error al actualizar estado del Trámite");

            }

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(
                        "MarginacionLegalSelRep.xhtml");
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

    public void terminar() throws IOException, ServicioExcepcion {

        if (todosComparecientesSeleccionaronPropiedad()) {
            actualizarEstadoProcesoRepUsuSel();
            FacesContext.getCurrentInstance().getExternalContext().redirect("MarginacionGrupal.xhtml?paramRepertorio=" + repertorioUsuarioSeleccionado.getRepNumero().getRepNumero());
        } else {
            JsfUtil.addErrorMessage("Debe seleccionar al menos una propiedad por compareciente si existen disponibles");
        }

    }

    public Boolean todosComparecientesSeleccionaronPropiedad() throws ServicioExcepcion {
        Boolean tienePropiedadesSeleccionadas = false;
        for (TramiteDetalle tramiteDetalle1 : listaTramDetaSelec) { //por cada propietario
            Persona persona = tramiteDetalle1.getPerId();
            List<Propiedad> listaPropiedadPropietario = servicioPropiedad.listarPropiedad_Por_Propietario(persona.getPerId());
            Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();
            System.out.println("Compareciente: " + tramiteDetalle1.getTdtPerNombre() + " " + tramiteDetalle1.getTdtPerApellidoPaterno());
            if (!listaPropiedadPropietario.isEmpty()) { //si tiene propiedades que seleccionar
                for (Propiedad propiedad : listaPropiedadPropietario) {
                    System.out.println("Propiedad: " + "predio: " + propiedad.getPrdPredio() + " catastro: " + propiedad.getPrdCatastro());

                    //consulto registros en tramite valor para saber si las propiedades ya fueron seleccionadas anteriormente, y para mostrarlas como seleccionadas.
                    TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(tramiteSeleccionado.getTraNumero(), propiedad.getPrdPredio(), propiedad.getPrdCatastro());
                    if (tramiteValorPorPropiedad != null) { //si ya seleccionó la propiedad
                        tienePropiedadesSeleccionadas = true;
                        System.out.println("propiedad seleccionada");
                        break; //basta con que seleccione una
                    } else {//si no ha seleccionado la propiedad
                        tienePropiedadesSeleccionadas = false;
                        System.out.println("propiedad no seleccionada");
                    }
                }
            } else {// si no tiene propiedades que seleccionar
                crearTramiteValorPorDefecto(repertorioUsuarioSeleccionado.getRepNumero().getTraNumero());
                System.out.println("propiedad seleccionada(por defecto)");
                tienePropiedadesSeleccionadas = true;
            }
            if (!tienePropiedadesSeleccionadas)//si el propietario tiene propiedades seleccionadas, ya sea porque se creo el tramite valor por defecto o porque seleccionó alguna de una lista
            {
                return false; //hay un compareciente que no seleccionó propiedad
            }

            System.out.println("tienePropiedadesSeleccionadas: " + tienePropiedadesSeleccionadas);
        }
        return tienePropiedadesSeleccionadas;
    }

    public void cargarPropiedades() throws ServicioExcepcion {
        listaPropiedadSelec = new ArrayList<>();
        bolSeSeleccionaronPropiedades = false;
        System.out.println("perid: " +personaSelec.getPerId());
        System.out.println("perIdentificacion: "+personaSelec.getPerIdentificacion());
        listaPropiedadPorPropietario = servicioPropiedad.listarPropiedad_Por_Propietario(personaSelec.getPerId());
        Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();

        for (Propiedad propiedad : listaPropiedadPorPropietario) {
            //consulto registros en tramite valor para saber si las propiedades ya fueron seleccionadas anteriormente, y para mostrarlas como seleccionadas.
            TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(tramiteSeleccionado.getTraNumero(), propiedad.getPrdPredio(), propiedad.getPrdCatastro());
            if (tramiteValorPorPropiedad != null) {
                listaPropiedadSelec.add(propiedad); //pongo como seleccionada
            }
        }
    }

    public void crearTramiteValorPorDefecto(Tramite tramite) {
        try {
            if (!servicioTramiteValor.existePorPropiedad(tramite.getTraNumero(), "0", "0")) {
                //----        campos requeridos-----
                TramiteValor tramVal0 = new TramiteValor();
                tramVal0.setTtrId(servicioTipoTramite.buscarPorDescripcion(repertorioUsuarioSeleccionado.getRepNumero().getRepTtrDescripcion()));
                tramVal0.setTraNumero(tramite);
                tramVal0.setTrvValor(BigDecimal.ZERO);
                tramVal0.setTrvEstado("A");
                tramVal0.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                //-----------
                //llenando datos parroquia-----
//            Parroquia parroquia0 = servicioParroquia.find(new Parroquia(), new Long(0));
                tramVal0.setTrvParId(0L);
                tramVal0.setTrvParNombre(" ");
                //-------------------------------
                tramVal0.setTrvFHR(Calendar.getInstance().getTime());
                tramVal0.setTrvAccion(0);
                tramVal0.setTrvIva(BigDecimal.ZERO);
                tramVal0.setTrvCantidad(BigDecimal.ZERO);
                tramVal0.setTtrvPorIva(institucionServicio.porcentajeIva());
                tramVal0.setTrvNumCatastro("0");
                tramVal0.setTraNumPredio("0");
                servicioTramiteValor.create(tramVal0);
//            listaTramiteValor.add(tramVal0);
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al crear tramite valor por defecto.");
            e.printStackTrace(System.out);
        }
    }

    public void guardarTrmVlr() throws ServicioExcepcion {
//        eliminarTrmVlr();
        Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();

        for (Propiedad propiedad : listaPropiedadPorPropietario) {
            //consulto registro en tramite valor
            TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(tramiteSeleccionado.getTraNumero(), propiedad.getPrdPredio(), propiedad.getPrdCatastro());
            if (listaPropiedadSelec.contains(propiedad)) { //si propiedad fue seleccionada
                if (tramiteValorPorPropiedad == null) { //si no existe tramite valor, creo nuevo
                    //si ya existe un tramite valor lo elimino para crear
                    crearTramiteValor(propiedad, tramiteSeleccionado);
                }//si existe tramite valor, no hago nada
            } else { //si la propiedad fue deseleccionada
                if (tramiteValorPorPropiedad != null) { //si ya existe tramite valor, lo elimino
                    servicioTramiteValor.remove(tramiteValorPorPropiedad);
                }//si no existe tramite valor, no hago nada
            }
        }
    }

//    public void guardarTrmVlr() throws ServicioExcepcion {
//        Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();
//
//        if (listaPropiedadPorPropietario.isEmpty()) {//si la lista a mostrar esta vacia
//            crearTramiteValorPorDefecto();
//            bolSeSeleccionaronPropiedades=true;
//            System.out.println(bolSeSeleccionaronPropiedades);
//
//        } else {//si la lista a mostrar no esta vacia
//            if(listaPropiedadSelec.isEmpty())//si no se seleccionó ninguna propiedad
//            {
//                bolSeSeleccionaronPropiedades=false;                
//                System.out.println(bolSeSeleccionaronPropiedades);
//            }
//            else{
//                bolSeSeleccionaronPropiedades=true;                
//                System.out.println(bolSeSeleccionaronPropiedades);
//            }
//        }
//    }
    public void crearTramiteValor(Propiedad propiedad, Tramite tramite) throws ServicioExcepcion {
        Repertorio repertorioSeleccionado = repertorioUsuarioSeleccionado.getRepNumero();

//----        campos requeridos-----
        TramiteValor nuevoTrmVlr = new TramiteValor();
        nuevoTrmVlr.setTtrId(servicioTipoTramite.buscarPorDescripcion(repertorioSeleccionado.getRepTtrDescripcion()));
        nuevoTrmVlr.setTraNumero(repertorioSeleccionado.getTraNumero());
        nuevoTrmVlr.setTrvValor(BigDecimal.ZERO);
        nuevoTrmVlr.setTrvEstado("A");
        nuevoTrmVlr.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        nuevoTrmVlr.setTrvNumCatastro(propiedad.getPrdCatastro());
        nuevoTrmVlr.setTraNumPredio(propiedad.getPrdPredio());
        //-----------
        //llenando datos parroquia-----
        nuevoTrmVlr.setTrvParId(tramite.getTraParId().longValue());
        nuevoTrmVlr.setTrvParNombre(tramite.getTraParNombre());
        //-------------------------------
        nuevoTrmVlr.setTrvFHR(Calendar.getInstance().getTime());
        nuevoTrmVlr.setTrvAccion(0);
        nuevoTrmVlr.setTrvIva(BigDecimal.ZERO);
        nuevoTrmVlr.setTrvCantidad(BigDecimal.ZERO);
        nuevoTrmVlr.setTtrvPorIva(institucionServicio.porcentajeIva());
        servicioTramiteValor.create(nuevoTrmVlr);

    }

}
