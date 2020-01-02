/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.MarginacionDetalleDao;
import com.rm.empresarial.dao.PropiedadDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.MarginacionDetalle;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoMarginacion;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.NuevasMatriculasServicio;
import com.rm.empresarial.servicio.ParroquiaServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.PropietarioServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.TipoLibroServicio;
import com.rm.empresarial.servicio.TipoMarginacionServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
@Named(value = "controladorMarginacionGrupal")
@ViewScoped
public class ControladorMarginacionGrupal implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @EJB
    private RepertorioServicio servicioRepertorio;

    @EJB
    TramiteValorServicio servicioTramiteValor;

    @EJB
    TipoTramiteServicio servicioTipoTramite;

    @EJB
    TipoLibroServicio servicioTipoLibro;

    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;

    @EJB
    private RepertorioUsuarioServicio repertorioUsuarioServicio;

    @EJB
    NuevasMatriculasServicio NuevasMatriculasServicio;

    @EJB
    GravamenDao servicioGravamen;

    @EJB
    TramiteDetalleDao tramiteDetalleDao;

    @EJB
    TipoTramiteDao tipoTramiteDao;

    @Inject
    private CargaLaboralUtil cargaLaboralUtil;

    @EJB
    private PropietarioServicio propietarioServicio;

    @EJB
    ParroquiaServicio servicioParroquia;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;

    @EJB
    private TramiteDao servicioTramite;

    @Inject
    private TramiteUtil tramiteUtil;

    @Getter
    @Setter
    @EJB
    private ActaServicio servicioActa;

    @EJB
    private MarginacionServicio servicioMarginacion;

    @EJB
    private TipoMarginacionServicio servicioTipoMarginacion;

    @EJB
    private MarginacionDetalleDao servicioMarginacionDetalle;

    @EJB
    private PropiedadDao servicioPropiedad;

    @Getter
    @Setter
    private Repertorio repertorioSeleccionado;

    @Getter
    @Setter
    private Tramite tramiteParam;

    @Getter
    @Setter
    private Parroquia parroquiaSeleccionada;

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor;

    @Getter
    @Setter
    private List<Parroquia> listaParroquia = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramDetComparecientes = new ArrayList<>();

    @Getter
    @Setter
    private TramiteValor tramiteValorSeleccionado;

    @Getter
    @Setter
    private Boolean renderedBtnTerminar;

    @Getter
    @Setter
    private Boolean disabledBtnTerminar;

    @Getter
    @Setter
    private String catastro;
    @Getter
    @Setter
    private String predio;

    @Getter
    @Setter
    private Boolean esPropietario;

    @EJB
    private PropiedadServicio propiedadServicio;

//    @Getter
//    @Setter
//    private Acta actaSeleccionada;
    @Getter
    @Setter
    List<Marginacion> listaMarginacion;

    @Getter
    @Setter
    private List<Acta> listaActasTabla;

    @Getter
    @Setter
    private List<Acta> listaActasSeleccionadas;

    @Getter
    @Setter
    private Acta actaSeleccionada;

    @Getter
    @Setter
    private Marginacion nuevaMarginacion;

    @Getter
    @Setter
    private TipoMarginacion tipoMarginacionSeleccionado;

    @Getter
    @Setter
    private Long IDtipoMarginacionSeleccionado;

    // marginacion detalle 
    @Getter
    @Setter
    private Double porcentajeDerAcn;

    @Getter
    @Setter
    private String txtEdificioBloque;

    @Getter
    @Setter
    private String txtNumOficio;

    @Getter
    @Setter
    private Date fechaOficio;

    @Getter
    @Setter
    private String txtNotario;

    @Getter
    @Setter
    private String txtObservacion;

    @Getter
    @Setter
    private List<MarginacionDetalle> listaMarginacionDetalle;

    /**
     * Creates a new instance of ControladorMarginacionGrupal
     */
    public ControladorMarginacionGrupal() {
        repertorioSeleccionado = new Repertorio();
        listaActasSeleccionadas = new ArrayList<>();
        listaActasTabla = new ArrayList<>();
        actaSeleccionada = new Acta();

    }

    @PostConstruct
    public void PostConstruct() {
        try {
            Map params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
            Long numRepertorio = new Long(params.get("paramRepertorio").toString());
            repertorioSeleccionado = servicioRepertorio.find(new Repertorio(), numRepertorio);
            tramiteParam = repertorioSeleccionado.getTraNumero();
            llenarTablaActas();
//            verificarTablaTramVal();
        } catch (Exception ex) {
            Logger.getLogger(ControladorMarginacionGrupal.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public List<TipoLibro> getlistaTipoLibro() throws ServicioExcepcion {
        return servicioTipoLibro.listarTodo();
    }

    public List<TipoMarginacion> getlistaTipoMarginacion() throws ServicioExcepcion {
        return servicioTipoMarginacion.listarTodo();
    }

    public void llenarTablaActas() throws ServicioExcepcion {
        buscarActa(); //lleno la lista de actas en la tabla por seleccionar
        listaActasSeleccionadas = new ArrayList<>(); //limpia las lista de actas seleccionaddas
        
    }

    public void agregarActaPorDefecto() throws ServicioExcepcion {
//        if (listaActasTabla.isEmpty() || listaActasTabla == null) {
//            TramiteValor tramVal0 = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(repertorioSeleccionado.getTraNumero().getTraNumero(), "0", "0");
//            if (tramVal0 != null) {
        try {
            Acta acta0 = servicioActa.find(new Acta(), 0L);
            if (acta0 != null) {
                listaActasTabla.add(acta0);
            } else {
                JsfUtil.addErrorMessage("No existe el acta 0");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
//            } else {
//                JsfUtil.addErrorMessage("No existe tramite valor 0");
//
//            }
//        }
    }

    public void buscarActa() throws ServicioExcepcion {
        try {
            List<TramiteDetalle> listaTramiteDet = new ArrayList<>();
            boolean agregarActa0=false;
            listaTramiteDet = servicioTramiteDetalle.listar_por_repertorio_tramite(repertorioSeleccionado.getRepNumero().intValue(), tramiteParam.getTraNumero());

            Long tipoTramiteId = new Long(0);
            for (TramiteDetalle tramiteDetalle1 : listaTramiteDet) {
                tipoTramiteId = tramiteDetalle1.getTdtTtrId();
            }
            List<TramiteDetalle> listaTramDetaSelec = new ArrayList<>();
            TipoTramite tipoTram = servicioTipoTramite.buscarPorID(tipoTramiteId);

            listaTramDetaSelec = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioSeleccionado.getRepNumero().intValue(), tramiteParam.getTraNumero(), "S");

            List<Propiedad> listaPropiedadSelec = new ArrayList<>();
            for (TramiteDetalle tramiteDetalle : listaTramDetaSelec) {
                Persona personaSelec = tramiteDetalle.getPerId();
//                System.out.println("perid: " + personaSelec.getPerId());
//                System.out.println("perIdentificacion: " + personaSelec.getPerIdentificacion());
                List<Propiedad> listaPropiedadPorPropietario = servicioPropiedad.listarPropiedad_Por_Propietario(personaSelec.getPerId());
                Tramite tramiteSeleccionado = repertorioSeleccionado.getTraNumero();
                
                if(listaPropiedadPorPropietario.isEmpty()||listaPropiedadPorPropietario==null)
                {
                    agregarActa0=true;
                }

                for (Propiedad propiedad : listaPropiedadPorPropietario) {
                    //consulto registros en tramite valor para saber si las propiedades ya fueron seleccionadas anteriormente, y para mostrarlas como seleccionadas.
                    TramiteValor tramiteValorPorPropiedad = servicioTramiteValor.obtenerPor_NumTramite_Propiedad(tramiteSeleccionado.getTraNumero(), propiedad.getPrdPredio(), propiedad.getPrdCatastro());
                    if (tramiteValorPorPropiedad != null) {
                        listaPropiedadSelec.add(propiedad); //pongo como seleccionada
                    }
                }
                
            }

            List<Acta> listaActasEncontradas = new ArrayList<>();
            for (Propiedad propiedad : listaPropiedadSelec) {
                listaActasEncontradas.addAll(servicioActa.buscarPor_Matricula(propiedad));
            }
            
            if (!listaActasEncontradas.isEmpty()) {
                listaActasTabla = listaActasEncontradas;
            } 
            if(agregarActa0)
            {
                agregarActaPorDefecto();
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
//            JsfUtil.addErrorMessage("Error, No se encontró actas.");
        }
    }

//    public void buscarActa1() throws ServicioExcepcion {
//        try {
//            Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
//            TipoTramite tipoTramiteSelec = servicioTipoTramite.find(new TipoTramite(), new Long(repertorioSeleccionado.getRepTtrId()));
//            listaTramiteValor = servicioTramiteValor.ListarPor_TipoTramite_NumTramite_Estado_TtcPropietario(tramiteSelec, tipoTramiteSelec);
//            System.out.println("listaTramiteValor: " + listaTramiteValor.size());
//            for (Iterator<TramiteValor> iterator = listaTramiteValor.iterator(); iterator.hasNext();) {
//                TramiteValor tramiteValorSiguiente = iterator.next();
//                if (tramiteValorSiguiente.getTrvNumCatastro().equals("0") && tramiteValorSiguiente.getTraNumPredio().equals("0")) {
//                    tramiteValorSiguiente.setTrvAccion(1);
//                    servicioTramiteValor.edit(tramiteValorSiguiente);
//                    iterator.remove();
//                }
//                if (tramiteValorSiguiente.getTrvCertificado() != null && !tramiteValorSiguiente.getTrvCertificado().isEmpty()) {
//                    iterator.remove();
//                }
//            }
//            listaActasTabla = servicioActa.listarActaPor_Compareciente_TipoLibroPropiedad(listaTramiteValor, 1);
//            System.out.println("listaActasTabla: " + listaActasTabla.size());
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
////            JsfUtil.addErrorMessage("Error, No se encontró actas.");
//        }
//    }
//    public void verificarTablaTramVal() {
//        if (listaTramiteValor.isEmpty()) {
//            crearTramiteValorPorDefecto();
//        }
//    }
    public void seleccionarActas() {
        System.out.println("# Actas seleccionadas: " + listaActasSeleccionadas.size());
        if (listaActasSeleccionadas.isEmpty()) {
            JsfUtil.addErrorMessage("Debe seleccionar al menos una Acta");
        }
    }
//    public void crearTramiteValorPorDefecto() {
//        try {
////----        campos requeridos-----
//            TramiteValor tramVal0 = new TramiteValor();
//            tramVal0.setTtrId(servicioTipoTramite.buscarPorDescripcion(repertorioSeleccionado.getRepTtrDescripcion()));
//            tramVal0.setTraNumero(repertorioSeleccionado.getTraNumero());
//            tramVal0.setTrvValor(BigDecimal.ZERO);
//            tramVal0.setTrvEstado("A");
//            tramVal0.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
//            //-----------
//            //llenando datos parroquia-----
////            Parroquia parroquia0 = servicioParroquia.find(new Parroquia(), new Long(0));
//            tramVal0.setTrvParId(0L);
//            tramVal0.setTrvParNombre(" ");
//            //-------------------------------
//            tramVal0.setTrvFHR(Calendar.getInstance().getTime());
//            tramVal0.setTrvAccion(0);
//            tramVal0.setTrvIva(BigDecimal.ZERO);
//            tramVal0.setTrvCantidad(BigDecimal.ZERO);
//            tramVal0.setTtrvPorIva(institucionServicio.porcentajeIva());
//            tramVal0.setTrvNumCatastro(0L);
//            tramVal0.setTraNumPredio(0L);
//            servicioTramiteValor.create(tramVal0);
//            listaTramiteValor.add(tramVal0);
//
//        } catch (Exception e) {
//            JsfUtil.addErrorMessage("Error al crear tramite valor por defecto.");
//            e.printStackTrace(System.out);
//        }
//
//    }

//    public void cargarDatosSeleccion() throws ServicioExcepcion {
//        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
//        TipoTramite tipoTramiteSelec = servicioTipoTramite.find(new TipoTramite(), new Long(repertorioSeleccionado.getRepTtrId()));
//        listaTramiteValor = servicioTramiteValor.ListarPor_TipoTramite_NumTramite_Estado(tramiteSelec, tipoTramiteSelec);
//        for (Iterator<TramiteValor> iterator = listaTramiteValor.iterator(); iterator.hasNext();) {
//            TramiteValor tramiteValorSiguiente = iterator.next();
//            if (tramiteValorSiguiente.getTrvNumCatastro() == 0 && tramiteValorSiguiente.getTraNumPredio() == 0) {
//                tramiteValorSiguiente.setTrvAccion(1);
//                servicioTramiteValor.edit(tramiteValorSiguiente);
//                iterator.remove();
//            }
//
//        }
//        if (listaTramiteValor.isEmpty() && tramiteSelec.getTraClase().equals("J")) {
//            disabledBtnTerminar = false;
//        } else {
//            renderedBtnTerminar = false;
//        }
//
//        setListaParroquia(servicioParroquia.listarTodo());
//
//    }
//    public void preCrearNuevoTramiteValor() {
//        System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.preCrearNuevoTramiteValor()");
//        parroquiaSeleccionada = new Parroquia();
//        tramiteValorSeleccionado = new TramiteValor();
//
//    }
    //**TERMINAR PROCESO**
    public void redireccionarPaginaSeleccion() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("MarginacionLegalSelRep.xhtml");

        } catch (IOException ex) {
            Logger.getLogger(ControladorNuevasMatriculas.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void terminarProceso() throws ServicioExcepcion {
        try {
            Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
            TipoTramite tipoTramiteSelec = servicioTipoTramite.find(new TipoTramite(), new Long(repertorioSeleccionado.getRepTtrId()));
            int numRegistrosActualizados = 0;
            numRegistrosActualizados = repertorioUsuarioServicio.actualizarEstadoProcesoPor_NumRepertorio_Tipo(repertorioSeleccionado.getRepNumero(), "MAT");
            TramiteValor tramValorBuscar = new TramiteValor();
            tramValorBuscar = servicioTramiteValor.obtenerPor_NumTramite_TipoTramite_Castrato_Predio(tramiteSelec.getTraNumero(), tipoTramiteSelec.getTtrId(), "0", "0");
            if (tramValorBuscar == null) {

                TramiteValor tramValor = new TramiteValor();
                tramValor.setTtrId(tipoTramiteSelec);
                tramValor.setTraNumero(tramiteSelec);
                tramValor.setTrvValor(new BigDecimal(0));
                tramValor.setTrvEstado("A");
                tramValor.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                tramValor.setTrvFHR(Calendar.getInstance().getTime());
                tramValor.setTrvIva(new BigDecimal(0));
                tramValor.setTrvValorBien(new BigDecimal(0));
                tramValor.setTraNumPredio("0");
                tramValor.setTrvNumCatastro("0");
                tramValor.setTtrvPorIva(new BigDecimal(0));
                tramValor.setTrvCantidad(new BigDecimal(0));
                tramValor.setTrvParId(new Long(0));
                tramValor.setTrvParNombre("");
                tramValor.setTrvAccion(0);
                servicioTramiteValor.create(tramValor);
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/matriculacion/Matriculacion.xhtml");
        } catch (Exception e) {
            Logger.getLogger(MarginacionControler.class.getName()).log(Level.SEVERE, null, e);

        }

    }

    public boolean esActaMarginada(Acta acta) {
        try {

            if (acta != null) {
                List<Marginacion> listaMarginaciones = servicioMarginacion.listarPor_Repertorio_Acta(repertorioSeleccionado.getRepNumero().toString(), acta.getActNumero());
                return listaMarginaciones != null && !listaMarginaciones.isEmpty();
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;

        }
    }

//    public void seleccionarTramiteValor() throws ServicioExcepcion, IOException {
////        propiedadNueva = new Propiedad();
//
//        actualizarTramiteValor();
//        buscarActa(); //lleno la lista de actas en la tabla por seleccionar
//        listaActasSeleccionadas = new ArrayList<>(); //limpia las lista de actas seleccionaddas
//
//    }
//    public void crearTramiteValor() throws ServicioExcepcion {
////----        campos requeridos-----
//        tramiteValorSeleccionado.setTtrId(servicioTipoTramite.buscarPorDescripcion(repertorioSeleccionado.getRepTtrDescripcion()));
//        tramiteValorSeleccionado.setTraNumero(repertorioSeleccionado.getTraNumero());
//        tramiteValorSeleccionado.setTrvValor(BigDecimal.ZERO);
//        tramiteValorSeleccionado.setTrvEstado("A");
//        tramiteValorSeleccionado.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
//        //-----------
//        //llenando datos parroquia-----
//        tramiteValorSeleccionado.setTrvParId(parroquiaSeleccionada.getParId());
//        tramiteValorSeleccionado.setTrvParNombre(parroquiaSeleccionada.getParNombre());
//        //-------------------------------
//        tramiteValorSeleccionado.setTrvFHR(Calendar.getInstance().getTime());
//        tramiteValorSeleccionado.setTrvAccion(0);
//        tramiteValorSeleccionado.setTrvIva(BigDecimal.ZERO);
//        tramiteValorSeleccionado.setTrvCantidad(BigDecimal.ZERO);
//        tramiteValorSeleccionado.setTtrvPorIva(institucionServicio.porcentajeIva());
//
//        Boolean esValorRepetido = false;
//        for (TramiteValor tramiteValor : listaTramiteValor) {
//            if (tramiteValor.getTrvNumCatastro().equals(tramiteValorSeleccionado.getTrvNumCatastro())) {
//                esValorRepetido = true;
//                JsfUtil.addErrorMessage("Error al guardar. Número de Catastro repetido");
//                break;
//            }
//            if (tramiteValor.getTraNumPredio().equals(tramiteValorSeleccionado.getTraNumPredio())) {
//                esValorRepetido = true;
//                JsfUtil.addErrorMessage("Error al guardar. Número de Predio repetido");
//                break;
//            }
//        }
//
//        /**
//         * ************************
//         */
//        /* VALIDAR SI ENCUENTRA UNA PROPIEDAD CON PREDIO Y CATASTRO INGRESADOS Y 
//        QUE ÉSTA PERTENESCA A UNO DE LOS COMPARECIENTES*/
//        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
//        try {
////            Tramite tramiteSeleccionado = repertorioUsuarioSeleccionado.getRepNumero().getTraNumero();
//            listaTramDetComparecientes.clear();
//            RepertorioUsuario repertorioUsuarioSelec = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorioSeleccionado.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "MAT");
//            listaTramDetComparecientes = servicioTramiteDetalle.listar_por_repertorio_tramite_propietarioTipoTramComp(repertorioUsuarioSelec.getRepNumero().getRepNumero().intValue(), tramiteSelec.getTraNumero(), "N");
//
//            esPropietario = false;
//            if (tramiteSelec.getTraClase().equals("J")) {
//                Propiedad propiedad = new Propiedad();
//                propiedad = propiedadServicio.buscarPropiedadPor_predio_Y_catastro(tramiteValorSeleccionado.getTraNumPredio().toString(), tramiteValorSeleccionado.getTrvNumCatastro().toString());
//                if (propiedad != null) {
//                    List<Propietario> listaPropietario = new ArrayList<>();
//                    listaPropietario.clear();
//                    for (TramiteDetalle listaTramDetCompareciente : listaTramDetComparecientes) {
//                        List<Propietario> listaPro = new ArrayList<>();
//                        if (listaTramDetCompareciente.getTtcId().getTtcPropietario().trim().equals("N")) {
//                            listaPro = propietarioServicio.buscarPor_Id_Persona_Por_Matricula(listaTramDetCompareciente.getPerId().getPerId(), propiedad.getPrdMatricula());
//                        }
//                        listaPropietario.addAll(listaPro);
//                    }
//                    if (!listaPropietario.isEmpty()) {
//                        esPropietario = true;
//                    }
//
//                }
//            } else if (!esValorRepetido) {
//                servicioTramiteValor.create(tramiteValorSeleccionado);
//                listaTramiteValor.add(tramiteValorSeleccionado);
////                JsfUtil.addSuccessMessage("Ingresado");
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//
//        /**
//         * ****************************************************
//         */
//        if (!esValorRepetido && esPropietario == true && tramiteSelec.getTraClase().equals("J")) {
//            servicioTramiteValor.create(tramiteValorSeleccionado);
//            listaTramiteValor.add(tramiteValorSeleccionado);
//            JsfUtil.addSuccessMessage("Ingresado");
//        } else if (esPropietario == false && tramiteSelec.getTraClase().equals("J")) {
//            JsfUtil.addErrorMessage("Propiedad no pertenece a ningún compareciente");
//        }
//        tramiteValorSeleccionado = new TramiteValor();
//
//        if (listaTramiteValor.isEmpty() && tramiteSelec.getTraClase().equals("J")) {
//            disabledBtnTerminar = false;
//            renderedBtnTerminar = true;
//        } else {
//            renderedBtnTerminar = false;
//        }
//
//    }
//
//    public void borrarTramiteValor() {
//        //elimino de forma logica----
//        tramiteValorSeleccionado.setTrvEstado("I");
//        servicioTramiteValor.edit(tramiteValorSeleccionado);
//        //--------------
//        //elimino de la lista
//        listaTramiteValor.remove(tramiteValorSeleccionado);
//        Tramite tramiteSelec = repertorioSeleccionado.getTraNumero();
//        if (listaTramiteValor.isEmpty() && tramiteSelec.getTraClase().equals("J")) {
//            disabledBtnTerminar = false;
//            renderedBtnTerminar = true;
//        } else {
//            renderedBtnTerminar = false;
//        }
////        limpiarPropiedad();
//    }
//
//    public void actualizarTramiteValor() {
//        //actualizo tramite valor seleccionado
//        //busco el objeto parroquia para almacenar el id correspondiente
//        Parroquia parroquiaSelec = new Parroquia();
//        for (Parroquia parroquia : listaParroquia) {
//            if (parroquia.getParNombre().equals(tramiteValorSeleccionado.getTrvParNombre())) {
//                parroquiaSelec = parroquia;
//            }
//        }
//        tramiteValorSeleccionado.setTrvParId(parroquiaSelec.getParId());
//        catastro = tramiteValorSeleccionado.getTrvNumCatastro().toString();
//        predio = tramiteValorSeleccionado.getTraNumPredio().toString();
//        tramiteValorSeleccionado.setTrvPrincipal(false);
//        servicioTramiteValor.edit(tramiteValorSeleccionado);
//    }
    public String verificarGravamenes(TramiteValor traval) {
        String mensajeGravamen = "";
        if (traval.getTrvNumCatastro() != null && !traval.getTrvNumCatastro().equals("0") && traval.getTraNumPredio() != null && !traval.getTraNumPredio().equals("0")) {

            Propiedad propiedadCargada = NuevasMatriculasServicio.buscarPropiedadConCatastroPredio(traval.getTrvNumCatastro().toString(), traval.getTraNumPredio().toString());
            if (propiedadCargada != null) {
                List<Gravamen> listaGravamenes = servicioGravamen.buscarPorMatricula(propiedadCargada);
                if (listaGravamenes.isEmpty()) {
                    mensajeGravamen = "NINGUNO";
                    return mensajeGravamen;
                } else {
                    mensajeGravamen = "Tiene gravamenes";
                    return mensajeGravamen;
                }
            } else {
                mensajeGravamen = "No existe propiedad";
                return mensajeGravamen;
            }
        }
        return mensajeGravamen;
    }

    public String obtenerEstadoPropiedad(TramiteValor traval) {
        String mensaje = "";

        Propiedad propiedadCargada = NuevasMatriculasServicio.buscarPropiedadConCatastroPredio(traval.getTrvNumCatastro().toString(), traval.getTraNumPredio().toString());
        if (propiedadCargada != null) {
            switch (propiedadCargada.getPrdEstadoRegistro()) {
                case "A":
                    mensaje = "Activo";
                case "I":
                    mensaje = "Inactivo";
            }
            return mensaje;
        } else {
            return mensaje;
        }

    }

    public void verActa(Acta acta) {
        listaMarginacion = new ArrayList<>();
        listaMarginacion = servicioMarginacion.listarPor_NumActa(acta.getActNumero());
        actaSeleccionada = acta;
    }

    public void actualizarEstProcRepUsu() throws Exception {

        RepertorioUsuario repertorioUsuarioSeleccionado = servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(
                repertorioSeleccionado.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "MGL");
        repertorioUsuarioSeleccionado.setRpuEstadoProceso("TERMINADO");
        servicioRepertorioUsuario.edit(repertorioUsuarioSeleccionado);
    }

    public String buscarNombreParroquia() {
        String nombreParroquia = "";
        try {
            Parroquia parroquiaSel = servicioParroquia.find(new Parroquia(), tramiteParam.getTraParId());
            nombreParroquia = parroquiaSel.getParNombre();
        } catch (Exception e) {
            System.out.println(e);
        }
        return nombreParroquia;

    }

    public List<MarginacionDetalle> listarMarginacionesDetalle(Marginacion marginacion) {
        listaMarginacionDetalle = servicioMarginacionDetalle.listarPor_idMarginacion(marginacion.getMrgId());
        return listaMarginacionDetalle;
    }

    public void preGuardarMarginacion() {
        nuevaMarginacion = new Marginacion();
        tipoMarginacionSeleccionado = servicioTipoMarginacion.find(new TipoMarginacion(), IDtipoMarginacionSeleccionado);
        nuevaMarginacion.setMrgAlt1(tipoMarginacionSeleccionado.getTmcTextoFijo());
        limpiarDatosMarginacionDetalle();

    }

    public void limpiarDatosMarginacionDetalle() {
        porcentajeDerAcn = null;
        txtEdificioBloque = null;
        txtNumOficio = null;
        fechaOficio = null;
        txtNotario = null;
        txtObservacion = null;
    }

//    public void seleccionarActa() {
//        JsfUtil.addSuccessMessage("Acta Seleccionada "+actaSeleccionada.getActNumero());
//    }
    public void guardarmarginacion() {
        try {
            for (Acta acta : listaActasSeleccionadas) {
                nuevaMarginacion.setMrgUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                nuevaMarginacion.setMrgFHR(Calendar.getInstance().getTime());

                nuevaMarginacion.setTmcId(tipoMarginacionSeleccionado);
                nuevaMarginacion.setActNumero(acta);
                nuevaMarginacion.setMrgAlt2(repertorioSeleccionado.getRepNumero().toString());
                nuevaMarginacion.setMrgFch(repertorioSeleccionado.getRepFHR());
                servicioMarginacion.create(nuevaMarginacion);
            }
//            switch (tipoMarginacionSeleccionado.getTmcCodigo()) {
//                case "01": //compraventa y donaciones 
//                    if (porcentajeDerAcn != null) {
//                        guardarMarginacionDetalle(nuevaMarginacion, "% Derechos y acciones", porcentajeDerAcn.toString());
//                    }
//                    break;
//                case "02": //hipoteca
//                    if (porcentajeDerAcn != null) {
//                        guardarMarginacionDetalle(nuevaMarginacion, "% Derechos y acciones", porcentajeDerAcn.toString());
//                    }
//                    break;
//                case "03": //declaratoria y modificatoria
//                    if (txtEdificioBloque != null) {
//                        guardarMarginacionDetalle(nuevaMarginacion, "Edificio o Bloque", txtEdificioBloque);
//                    }
//                    break;
//                case "04": //cancelacion de hipoteca
//                    if (txtNumOficio != null) {
//                        guardarMarginacionDetalle(nuevaMarginacion, "Num oficio", txtNumOficio);
//                    }
//                    if (fechaOficio != null) {
//                        guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Oficio", fechaOficio.toString());
//                    }
//                    if (txtNotario != null) {
//                        guardarMarginacionDetalle(nuevaMarginacion, "Notario", txtNotario);
//
//                    }
//                    if (txtNotario != null) {
//                        guardarMarginacionDetalle(nuevaMarginacion, "Observación", txtObservacion);
//                    }
//                    break;
//                case "05": //aclaratoria posesion efectiva
//                    if (txtObservacion != null) {
//                        guardarMarginacionDetalle(nuevaMarginacion, "Observaciones", txtObservacion);
//                    }
//                    break;
//                case "06": //sentencias varias
//                    if (txtObservacion != null) {
//                        guardarMarginacionDetalle(nuevaMarginacion, "Observaciones", txtObservacion);
//                    }
//                    break;
//                default:
//                    break;
//            }

            switch (tipoMarginacionSeleccionado.getTmcCodigo()) {
                case "01": //compraventa y donaciones 

                    if (repertorioSeleccionado.getRepFHR() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()));
                    }
                    if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Tipo Propiedad",
                                actaSeleccionada.getPrdMatricula().getPrdDescripcion2() + " - "
                                + actaSeleccionada.getPrdMatricula().getPrdDescripcion1());
                    }
                    if (repertorioSeleccionado.getRepFHR() != null && repertorioSeleccionado.getRepNumero() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        guardarMarginacionDetalle(nuevaMarginacion, "Vendido", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()) + " - " + repertorioSeleccionado.getRepNumero());
                    }
                    if (porcentajeDerAcn != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "% Derechos y acciones", porcentajeDerAcn.toString());
                    }

                    break;
                case "02": //hipoteca
                    if (repertorioSeleccionado.getRepFHR() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()));
                    }
                    if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Tipo Propiedad",
                                actaSeleccionada.getPrdMatricula().getPrdDescripcion2() + " - "
                                + actaSeleccionada.getPrdMatricula().getPrdDescripcion1());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Hipoteca", actaSeleccionada.getPrdMatricula().getPrdDescripcion2());
                    }
                    if (porcentajeDerAcn != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "% Derechos y acciones", porcentajeDerAcn.toString());
                    }
                    break;
                case "03": //declaratoria y modificatoria
                    if (repertorioSeleccionado.getRepFHR() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()));
                    }
                    if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (txtEdificioBloque != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Edificio o Bloque", txtEdificioBloque);
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Vendido", actaSeleccionada.getPrdMatricula().getPrdDescripcion2());
                    }
                    if (porcentajeDerAcn != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "% Derechos y acciones", porcentajeDerAcn.toString());
                    }
                    break;
                case "04": //cancelacion de hipoteca
                    if (repertorioSeleccionado.getRepFHR() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()));
                    }
                    if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (txtNumOficio != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Num oficio", txtNumOficio);
                    }
                    if (fechaOficio != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Oficio", fechaOficio.toString());
                    }
                    if (txtNotario != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Notario", txtNotario);

                    }
                    if (txtObservacion != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Observación", txtObservacion);
                    }
                    if (repertorioSeleccionado.getRepFHR() != null && repertorioSeleccionado.getRepNumero() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        guardarMarginacionDetalle(nuevaMarginacion, "Cancelación de Hipoteca", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()) + " - " + repertorioSeleccionado.getRepNumero());
                    }
                    break;
                case "05": //aclaratoria posesion efectiva

                    if (repertorioSeleccionado.getRepFHR() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()));
                    }
                    if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Tipo Propiedad",
                                actaSeleccionada.getPrdMatricula().getPrdDescripcion2() + " - "
                                + actaSeleccionada.getPrdMatricula().getPrdDescripcion1());
                    }
                    if (repertorioSeleccionado.getRepFHR() != null && repertorioSeleccionado.getRepNumero() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        guardarMarginacionDetalle(nuevaMarginacion, "Aclaratoria de Posesión Efectiva", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()) + " - " + repertorioSeleccionado.getRepNumero());
                    }
                    if (txtObservacion != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Observaciones", txtObservacion);
                    }
                    break;
                case "06": //sentencias varias
                    if (repertorioSeleccionado.getRepFHR() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        //guardarMarginacionDetalle(nuevaMarginacion, "Fecha de Marginación", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()));
                    }
                    if (dataManagerSesion != null) {
                        //guardarMarginacionDetalle(nuevaMarginacion, "Responsable", dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                    }
                    if (buscarNombreParroquia() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Parroquia", buscarNombreParroquia());
                    }
                    if (actaSeleccionada.getPrdMatricula() != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Tipo Propiedad",
                                actaSeleccionada.getPrdMatricula().getPrdDescripcion2() + " - "
                                + actaSeleccionada.getPrdMatricula().getPrdDescripcion1());
                    }
                    if (repertorioSeleccionado.getRepFHR() != null && repertorioSeleccionado.getRepNumero() != null) {
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        guardarMarginacionDetalle(nuevaMarginacion, "Aclaratoria de Posesión Efectiva", simpleDateFormat.format(repertorioSeleccionado.getRepFHR()) + " - " + repertorioSeleccionado.getRepNumero());
                    }
                    if (txtObservacion != null) {
                        guardarMarginacionDetalle(nuevaMarginacion, "Observaciones", txtObservacion);
                    }
                    break;
                default:
                    break;
            }

//            if (idRepertorio2 != null) {
//                nuevaMarginacion.setMrgAlt3(idRepertorio2.toString());
//            }
//        tipoLibroSeleccionado = servicioTipoLibro.find(new TipoLibro(), IDtipoLibroSeleccionado);
//            listaMarginacionNuevos.add(nuevaMarginacion); //lleno lista nuevos
            /*Limpiar campos*/
//        PrimeFaces.current().resetInputs("formMarginacion:pnlMarginacionP");
            tramiteUtil.insertarTramiteAccion(tramiteParam, nuevaMarginacion.getMrgAlt2(),
                    "Marginación de Repertorio",
                    tramiteParam.getTraEstado(), tramiteParam.getTraEstadoRegistro(), null);

            actualizarEstProcRepUsu();
//            contabilizarCertificados();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Actas marginadas correctamente", ""));
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al marginar", ""));

        }
    }

    public void guardarMarginacionDetalle(Marginacion mrg, String descripcion, String valor) {
        MarginacionDetalle nuevaMarginacionDetalle = new MarginacionDetalle();
        nuevaMarginacionDetalle.setMrgId(mrg);
        nuevaMarginacionDetalle.setMgdDescripcion(descripcion);
        nuevaMarginacionDetalle.setMgdValor(valor);
        nuevaMarginacionDetalle.setMgdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        nuevaMarginacionDetalle.setMgdFHR(Calendar.getInstance().getTime());
        servicioMarginacionDetalle.edit(nuevaMarginacionDetalle);

    }

    public void cambiarEstado() throws ServicioExcepcion {
        List<RepertorioUsuario> listaRepUsu;
        Long IDusuarioLogeado = dataManagerSesion.getUsuarioLogeado().getUsuId();
        Boolean estanTodosRepertoriosMarginados = servicioMarginacion.estanTodosRepertoriosMarginados(
                tramiteParam.getTraNumero());
        if (estanTodosRepertoriosMarginados) {
            listaRepUsu = servicioRepertorioUsuario.listarPorUsuarioPorTipoPorEstado(IDusuarioLogeado, "MGL");
            for (RepertorioUsuario repertorioUsuario : listaRepUsu) {
                if (repertorioUsuario.getRepNumero().getTraNumero().equals(tramiteParam)) {
                    repertorioUsuario.setRpuEstado("I");
                    servicioRepertorioUsuario.edit(repertorioUsuario);
                }
                cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuario.getUsuId().getUsuId(), "MGL");
            }
            try {
                TramiteDetalle tramiteDetalle = tramiteDetalleDao.buscarPorNumTramiteYDescripcion(repertorioSeleccionado.getTraNumero().getTraNumero(), repertorioSeleccionado.getRepTtrDescripcion());
                TipoTramite tipoTramite = tipoTramiteDao.buscarPorID(tramiteDetalle.getTdtTtrId());
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
                e.printStackTrace(System.out);
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
                e.printStackTrace(System.out);
                JsfUtil.addErrorMessage("Error al actualizar estado del Trámite");

            }

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(
                        "MarginacionLegalSelRep.xhtml");
            } catch (IOException ex) {
                System.out.println(ex);
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
