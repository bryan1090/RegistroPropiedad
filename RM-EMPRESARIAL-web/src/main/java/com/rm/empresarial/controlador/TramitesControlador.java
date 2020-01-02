/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.EnviarEmailController;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.CuantiaServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.NotariaServicio;
import com.rm.empresarial.servicio.ParroquiaServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.RepositorioSRIServicio;
import com.rm.empresarial.servicio.TipoTramiteComparecienteServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteUsuarioServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.TipoCertificadoDao;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Institucion;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.TipoCertificado;
import com.rm.empresarial.modelo.TipoDescuento;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.TipoDescuentoServicio;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author WILSON
 */
@ViewScoped
@Named
public class TramitesControlador extends JsfUtil implements Serializable {

    private static final long serialVersionUID = 112889626544006816L;

    @Inject
    private EnviarEmailController utilCorreo;

    @EJB
    private TramiteServicio tramiteServicio;

    @EJB
    private TramiteUsuarioServicio tramiteUsuarioServicio;

    @EJB
    private NotariaServicio notariaSerivicio;

    @EJB
    private ParroquiaServicio parroquiaServicio;

    @EJB
    private PersonaServicio personaServicio;

    @EJB
    private RepositorioSRIServicio repositorioSRIServicio;

    @EJB
    private TipoTramiteServicio tipoTramiteServicio;

    @EJB
    private TipoTramiteComparecienteServicio comparecienteServicio;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    private TramiteDetalleServicio tramiteDetalleServicio;

    @EJB
    private CuantiaServicio cuantiaServicio;

    @EJB
    private TramiteValorServicio tramiteValorServicio;

    @EJB
    private ConfigDetalleServicio configDetalleServicio;

    @EJB
    private TipoCertificadoDao servicioTipoCertificado;

    @EJB
    private PersonaServicio servicioPersona;
    
    @EJB
    private TipoDescuentoServicio tipoDescuentoServicio;

    @Inject
    private TransformadorNumerosLetrasUtil transformadorNumerosLetrasUtil;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Inject
    @Getter
    @Setter
    private ControladorCrearPersonaTramiteProforma controladorCrearPersonaTramiteProforma;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Inject
    private DataManagerSesion dataManagerSesion;

    @Inject
    private TramiteUtil tramiteUtil;

    @Inject
    private PersonaUtil personaUtil;

    @Getter
    @Setter
    private boolean txtNumeroContrato;

    @Getter
    @Setter
    private boolean txtNotaria;

    @Getter
    @Setter
    private boolean txtParroquia;

    @Getter
    @Setter
    private boolean txtContratos;

    @Getter
    @Setter
    private boolean txtCopiaModelo;

    @Getter
    @Setter
    private boolean txtTdtCatastro;

    @Getter
    @Setter
    private boolean txtTdtPredio, txtCantidad, lblCantidad;

    @Getter
    @Setter
    Boolean readOnlyDatos = false;

    @Getter
    @Setter
    private long cuantiaContratoId;

    @Getter
    @Setter
    private Long ttrId;

    @Getter
    @Setter
    private float valorCuantia;

    @Getter
    @Setter
    private String ttrDescripcion;

    @Getter
    @Setter
    private TipoTramite tipoTramite = new TipoTramite();
    
    @Getter
    @Setter
    private List<TipoDescuento> listaTipoDescuento = new ArrayList<>();

    @Getter
    @Setter
    private TramiteValor tramiteValor;

    @Getter
    @Setter
    private TipoDescuento tipoDescuentoSeleccionado = new TipoDescuento(); 
             
    @Getter
    @Setter
    private TramiteValor tramiteValorSeleccionado;

    @Getter
    @Setter
    private ConfigDetalle configDetalleFactura;

    @Getter
    @Setter
    private Boolean readOnly;

    @Getter
    @Setter
    private Boolean existeDetalle;

    @Getter
    @Setter
    private Boolean txtValor = Boolean.FALSE;

    @Getter
    @Setter
    private String textoBoton;

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor = new ArrayList<TramiteValor>();

    private String valor;

    private ArrayList<String> listaContratos = new ArrayList<String>();

    @Getter
    @Setter
    private List<ControladorTipoTramiteUnico> listaUnicoTramite = new ArrayList<ControladorTipoTramiteUnico>();

    @Getter
    @Setter
    private BigDecimal totalTraValor;

    @Inject
    ReporteUtil reporteUtil;

    @PostConstruct
    public void inicializar() {
        getTramitesControladorBb().inicializar();
        getTramitesControladorBb().setUsuario(dataManagerSesion.getUsuarioLogeado());
        listarTramites();
        setTramiteValor(new TramiteValor());
        setTramiteValorSeleccionado(new TramiteValor());
        getTramitesControladorBb().getPersonaConyugue().setPerIdentificacion(" ");
        getTramitesControladorBb().getPersonaConyugue().setPerNombre(" ");
        getTramitesControladorBb().getPersonaConyugue().setPerApellidoPaterno(" ");
        getTramitesControladorBb().getPersonaConyugue().setPerApellidoMaterno(" ");
        getTramitesControladorBb().getPersonaConyugue().setPerEstado(" ");
        getTramitesControladorBb().getPersonaConyugue().setPerTipoIdentificacion(" ");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map params = ec.getRequestParameterMap();
        getTramitesControladorBb().setNumero((String) params.get("numero"));
        Short contratoaux = 1;
        getTramitesControladorBb().getTramite().setTraNumeroContrato(contratoaux);

        try {
            cargarParroquias();
            cargarNotaria();
            cargarTipoServicio();
            cargarInstitucion();
            cargarTipoTramite();
            cargarTipoDescuento();

            if (getTramitesControladorBb().getNumero() != null) {
                cargarTramite(Long.valueOf(getTramitesControladorBb().getNumero()));
                cargarTipoTramitePorClase();
                cargarCompareciente();
                inciarCuantia();
            }

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(TramitesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void redireccionarAPaginaReingreso() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/tramiteProforma.xhtml");

    }
    
    public void cargarTipoDescuento() throws ServicioExcepcion{
        listaTipoDescuento.clear();
        listaTipoDescuento = tipoDescuentoServicio.listarTodo_EstadoA();
    }

    private void cargarTipoTramitePorClase() throws ServicioExcepcion {
        getTramitesControladorBb().setListaTipoTramite(tipoTramiteServicio.listarPorClase(getTramitesControladorBb().getTramite().getTraClase()));
    }

    public void cargarCompareciente() {
        try {
            List<TramiteDetalle> listTramiteDetalle = new ArrayList<TramiteDetalle>();
            listTramiteDetalle = tramiteDetalleServicio.buscarPorNumeroDeTramite(Long.valueOf(getTramitesControladorBb().getNumero()));

            for (TramiteDetalle itemtramiteDetalle : listTramiteDetalle) {

                String nombres = itemtramiteDetalle.getTdtPerApellidoPaterno().trim() + " " + itemtramiteDetalle.getTdtPerApellidoMaterno().trim() + " " + itemtramiteDetalle.getTdtPerNombre().trim();

                nombres = nombres.replace("null", "");

                getTramitesControladorBb().getListacomparecientes().add(new ControladorCompareciente(nombres, itemtramiteDetalle.getTdtPerIdentificacion(), itemtramiteDetalle.getTtcId().getTtrId().getTtrDescripcion(), itemtramiteDetalle.getTtcId().getTpcId().getTpcDescripcion(), 1, itemtramiteDetalle.getTtcId().getTtrId().getTtrId(), personaServicio.buscarPersonaPorId(itemtramiteDetalle.getTdtConyuguePerId()), itemtramiteDetalle.getPerId(), itemtramiteDetalle.getTtcId(), itemtramiteDetalle.getTdtParId(), itemtramiteDetalle.getTdtParNombre(), itemtramiteDetalle.getTtcId().getTtcId(), itemtramiteDetalle.getTdtCatastro(), itemtramiteDetalle.getTdtPredio()));
                getTramitesControladorBb().setFiltro(true);

                if (!getTramitesControladorBb().getListacomparecientes().isEmpty()) {
                    existeDetalle = true;
                } else {
                    existeDetalle = false;

                }
            }

        } catch (Exception e) {
            addErrorMessage("Error al cargar el detalle del tramite");
        }

    }

    public void inciarCuantia() {

        try {
            valorCuantia = 0;
            List<TramiteValor> listTramiteValor = new ArrayList<TramiteValor>();
            listTramiteValor = tramiteValorServicio.buscarPorTramite(getTramitesControladorBb().getTramite());
            for (TramiteValor itemTramiteValor : listTramiteValor) {
                valorCuantia += itemTramiteValor.getTrvValor().floatValue();
                itemTramiteValor.setTtrDescripcion(itemTramiteValor.getTtrId().getTtrDescripcion());
                listaTramiteValor.add(itemTramiteValor);
            }
            unicoTramite();

        } catch (Exception e) {
            addErrorMessage("Error al cargar la cuantia");
        }
    }

    public void listarTramites() {
        System.out.print("id USUARIO-- " + dataManagerSesion.getUsuarioLogeado().getUsuId());
        try {
            getTramitesControladorBb().setListaTramiteUsuario(tramiteUsuarioServicio.buscarTramitePorUsuario(dataManagerSesion.getUsuarioLogeado()));
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(TramitesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void onRowSelect() throws IOException, ServicioExcepcion, ServicioExcepcion, ServicioExcepcion {
        try {

            getTramitesControladorBb().setTramite(tramiteServicio.buscarTramitePorNumero(getTramitesControladorBb().getTramiteUsuarioSeleccionado().getTraNumero().getTraNumero()));

            if (getTramitesControladorBb().getTramite() != null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/tramites.jsf?numero=" + getTramitesControladorBb().getTramite().getTraNumero());
                inicializar();
            }
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(TramitesControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarNotaria() throws ServicioExcepcion {
        getTramitesControladorBb().setNotarias(notariaSerivicio.listaNotaria());
    }

    public void cargarParroquias() throws ServicioExcepcion {
        //        getTramitesControladorBb().setParroquias(parroquiaServicio.listaParroquia());
        getTramitesControladorBb().setParroquias(parroquiaServicio.listarTodo());
    }

    public void cargarTramite(Long tramite) throws ServicioExcepcion {
        getTramitesControladorBb().setTramite(tramiteServicio.buscarTramitePorNumero(tramite));
    }

    public void buscarPersona() throws IOException, ServicioExcepcion {

        if (getTramitesControladorBb().getTramite().getTraNumeroContrato() > 0) {

            if (getTramitesControladorBb().getIdentificacion() != null) {

                try {

                    getTramitesControladorBb().setPersona(personaUtil.obtenerDatosPersona(getTramitesControladorBb().getIdentificacion()));
                    if (getTramitesControladorBb().getPersona() != null) {
                        if (getTramitesControladorBb().getPersona().getPerIdentificacion() != null) {
                            nombrePersona();
                        } else {
                            limpiarComparecientes();
                            bloquearCampos();
                        }
                    } else {
                        getTramitesControladorBb().setNombrePersona(" ");
                        getTramitesControladorBb().setValorTramiteComp(" ");
                        getTramitesControladorBb().setEstado(Boolean.FALSE);
                        addErrorMessage("Persona no Encontrada");
                    }

                } catch (ServicioExcepcion ex) {
                    Logger.getLogger(TramitesControlador.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                addErrorMessage("Ingrese Cédula/RUC/Pas");
            }
        } else {
            addErrorMessage("Contratos no Ingresado");
            limpiarComparecientes();

        }
    }

    public void nombrePersona() {
        if (getTramitesControladorBb().getPersona().getPerApellidoMaterno() == null) {
            getTramitesControladorBb().setNombrePersona(getTramitesControladorBb().getPersona().getPerApellidoPaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerNombre().trim());
        } else {
            getTramitesControladorBb().setNombrePersona(getTramitesControladorBb().getPersona().getPerApellidoPaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerApellidoMaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerNombre().trim());
        }

        String Nombre = getTramitesControladorBb().getNombrePersona();
        getTramitesControladorBb().setNombrePersona(Nombre.replace("null", ""));
        getTramitesControladorBb().setEstado(Boolean.TRUE);
    }

    public void cargarTipoServicio() throws ServicioExcepcion {
        getTramitesControladorBb().setTipoTramite(tipoTramiteServicio.listaTipoTramite());
    }

    public void cargarTipoTramiteCom() throws ServicioExcepcion {
        valor = "";
        valor = getTramitesControladorBb().getValorTipTramite();
        getTramitesControladorBb().setTipoTramiteOb(tipoTramiteServicio.buscarTramitePorID(valor));
        getTramitesControladorBb().setContrato(getTramitesControladorBb().getTipoTramiteOb().getTtrDescripcion());
        getTramitesControladorBb().setListaTipoTramiteCompareciente(comparecienteServicio.listaTipoT(getTramitesControladorBb().getTipoTramiteOb()));

    }

    public void copiarTramite() throws ServicioExcepcion {

        List<ControladorCompareciente> list = new ArrayList<ControladorCompareciente>();

        for (TramiteDetalle copiaDetalle : getTramitesControladorBb().getListaTramitesDetalle()) {
            list.clear();
            getTramitesControladorBb().setNombrePersona(copiaDetalle.getTdtPerNombre() + " " + copiaDetalle.getTdtPerApellidoPaterno());
            getTramitesControladorBb().setPersona(copiaDetalle.getPerId());
            getTramitesControladorBb().getTipoTramiteCompareciente().setTtrId(copiaDetalle.getTtcId().getTtrId());
            getTramitesControladorBb().setTipoTramiteCompareciente(copiaDetalle.getTtcId());

            Parroquia buscarParrouia = parroquiaServicio.find(new Parroquia(), Long.parseLong(getTramitesControladorBb().getTramite().getTraParId().toString()));
            if (buscarParrouia == null) {
                buscarParrouia = new Parroquia();
                Long idPar = Long.valueOf(0);
                buscarParrouia.setParId(idPar);
                buscarParrouia.setParNombre(" ");

            }
            list.add(new ControladorCompareciente(getTramitesControladorBb().getNombrePersona(), getTramitesControladorBb().getPersona().getPerIdentificacion(), getTramitesControladorBb().getTipoTramiteCompareciente().getTtrId().getTtrDescripcion(), getTramitesControladorBb().getTipoTramiteCompareciente().getTpcId().getTpcDescripcion(), Integer.valueOf(getTramitesControladorBb().getTipoTramiteCompareciente().getTtcOrden()), getTramitesControladorBb().getTipoTramiteCompareciente().getTtrId().getTtrId(), getTramitesControladorBb().getPersona().getPerIdConyuge(), getTramitesControladorBb().getPersona(), getTramitesControladorBb().getTipoTramiteCompareciente(), buscarParrouia.getParId(), buscarParrouia.getParNombre(), getTramitesControladorBb().getTipoTramiteCompareciente().getTtcId(), getTramitesControladorBb().getTramiteDetalle().getTdtCatastro(), getTramitesControladorBb().getTramiteDetalle().getTdtPredio()));

            ControladorCompareciente compareciente = list.get(0);
            getTramitesControladorBb().getListacomparecientes().add(compareciente);

        }
        short cantidadContrato = (short) validarContratos();
        getTramitesControladorBb().getTramite().setTraRtd(String.valueOf(cantidadContrato));
        RequestContext.getCurrentInstance().update("frmTramites:formCajas:txtRtd");
        System.out.println("Cantidad de tramites " + getTramitesControladorBb().getTramite().getTraRtd());
        limpiarComparecientes();
    }

    public void llenarComparecientes() throws ServicioExcepcion {

        if (getTramitesControladorBb().getValorTramiteComp() == null || getTramitesControladorBb().getValorTramiteComp() == " ") {
            addErrorMessage("Por favor ingrese compareciente");
            limpiarComparecientes();
            bloquearCampos();
        } else {

            /*int cantidadContratos = 0;
            getTramitesControladorBb().setValorTipTramite(valor);
            cantidadContratos = validarContratos();
            if (cantidadContratos < (int) getTramitesControladorBb().getTramite().getTraNumeroContrato()) {*/
            boolean existeDetalle = false;
            boolean existeCompareciente = false;

            ControladorCompareciente compareciente = crearCompareciente(1).get(0);

            for (ControladorCompareciente compareciente1 : getTramitesControladorBb().getListacomparecientes()) {
                if (compareciente1.getTtcId().equals(compareciente.getTtcId()) && compareciente1.getIdentificacion().equals(compareciente.getIdentificacion())) {

                    existeCompareciente = true;

                } else {
                    if (compareciente1.getIdentificacion().equals(compareciente.getIdentificacion()) && compareciente1.getTramiteCompareciente().getTtrId().equals(compareciente.getTramiteCompareciente().getTtrId())) {

                        existeCompareciente = true;

                    }
                }

                existeDetalle = true;
            }

            if (!existeDetalle || !existeCompareciente) {
                getTramitesControladorBb().getListacomparecientes().add(compareciente);
                addSuccessMessage("Compareciente agregado");
                limpiarComparecientes();
                bloquearCampos();

            } else {
                addErrorMessage("Compareciente se encuentra registrado");
                limpiarComparecientes();
                bloquearCampos();
            }

            /* } else {
                addErrorMessage("Contratos ya ingresados");
            }*/
        }
    }

    public void desbloquearCampos() {
        int cantidadContratos = 0;
        try {
            getTramitesControladorBb().setValorTipTramite(valor);
            cantidadContratos = validarContratos();
            if (cantidadContratos < (int) getTramitesControladorBb().getTramite().getTraNumeroContrato()) {

                boolean cantidadCompareciente = validarComparecientesTramite();

                if (cantidadCompareciente) {
                    addErrorMessage("Contratos deben  tener mínimo dos comparecientes");
                } else {
                    txtNotaria = false;
                    txtParroquia = false;
                    txtContratos = false;
                    txtCopiaModelo = false;
                    txtTdtCatastro = false;
                    txtTdtPredio = false;
                    getTramitesControladorBb().setValorTipTramite("");
                    List<SelectItem> borrarListaTipoTramiteCompareciente = null;
                    getTramitesControladorBb().setListaTipoTramiteCompareciente(borrarListaTipoTramiteCompareciente);
                }

            } else {
                addErrorMessage("Contratos ya ingresados");
            }

        } catch (Exception e) {
            addErrorMessage("Error al desbloquear los campos");
        }

    }

    public void seleccionarContrato() throws ServicioExcepcion {

        getTramitesControladorBb().setValorTipTramite(getTramitesControladorBb().getComparecienteSeleccionado().getTtrId().toString());
        cargarTipoTramiteCom();

    }

    public void bloquearCampos() {
        getTramitesControladorBb().setValorTipTramite(valor);
        txtNumeroContrato = true;
        txtNotaria = true;
        txtParroquia = true;
        txtContratos = true;
        txtCopiaModelo = true;
        txtTdtCatastro = true;
        txtTdtPredio = true;

    }

    public void unicoTramite() {

        tramiteValor = new TramiteValor();
        //Limpiamos el array
        listaContratos.clear();
        for (ControladorCompareciente compareciente : getTramitesControladorBb().getListacomparecientes()) {

            listaContratos.add(compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString());

        }

        //Creamos un objeto HashSet
        HashSet hs = new HashSet();

        //Lo cargamos con los valores del array, esto hace quite los repetidos
        hs.addAll(listaContratos);

        //Limpiamos el array
        listaContratos.clear();

        //Agregamos los valores sin repetir
        listaContratos.addAll(hs);

        String ttrDescripcion = "";
        listaUnicoTramite.clear();

        for (int i = 0; i < listaContratos.size(); i++) {
            ControladorTipoTramiteUnico controladorTipoTramiteUnico = new ControladorTipoTramiteUnico();

            for (ControladorCompareciente compareciente : getTramitesControladorBb().getListacomparecientes()) {

                if (compareciente.getTramiteCompareciente().getTtrId().getTtrId() == Long.parseLong(listaContratos.get(i))) {

                    ttrDescripcion = compareciente.getTramiteCompareciente().getTtrId().getTtrDescripcion();
                }
            }
            controladorTipoTramiteUnico.setTtrId(Long.parseLong(listaContratos.get(i)));
            controladorTipoTramiteUnico.setTtrDescripcion(ttrDescripcion);
            listaUnicoTramite.add(controladorTipoTramiteUnico);

            txtCantidad = false;
            lblCantidad = false;

            tramiteValor.setTrvValor(BigDecimal.valueOf(0));
            tramiteValor.setTrvIva(BigDecimal.valueOf(0));
            tramiteValor.setTrvCantidad(BigDecimal.valueOf(0));
        }

    }

    public boolean validarComparecientesTramite() {
        /* Valida la cantidad de contrataos ingresado en un tramite*/
        boolean cantidaContratos = false;

        try {

            int cantidadPropietario = 0;
            int cantidadNoPropietario = 0;

            //Limpiamos el array
            listaContratos.clear();
            for (ControladorCompareciente compareciente : getTramitesControladorBb().getListacomparecientes()) {

                listaContratos.add(compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString());

            }

            //Creamos un objeto HashSet
            HashSet hs = new HashSet();

            //Lo cargamos con los valores del array, esto hace quite los repetidos
            hs.addAll(listaContratos);

            //Limpiamos el array
            listaContratos.clear();

            //Agregamos los valores sin repetir
            listaContratos.addAll(hs);

            for (int i = 0; i < listaContratos.size(); i++) {
                String get = listaContratos.get(i);
                cantidadPropietario = 0;

                for (ControladorCompareciente compareciente : getTramitesControladorBb().getListacomparecientes()) {

                    if (compareciente.getTramiteCompareciente().getTtcPropietario().equals("S") && compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString().equals(get)) {
                        cantidadPropietario += 1;
                    }

                    if (compareciente.getTramiteCompareciente().getTtcPropietario().equals("N") && compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString().equals(get)) {
                        cantidadPropietario += 1;
                    }

                }

                if (cantidadPropietario < 2) {
                    cantidaContratos = true;
                }

            }

            return cantidaContratos;

        } catch (Exception e) {

            addErrorMessage("Error al validar contratos");

            return cantidaContratos;
        }
    }

    public int validarContratos() {
        /* Valida la cantidad de contrataos ingresado en un tramite*/
        int cantidaContratos = 0;
        try {
            //Limpiamos el array
            listaContratos.clear();
            for (ControladorCompareciente compareciente : getTramitesControladorBb().getListacomparecientes()) {

                listaContratos.add(compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString());

            }

            //Creamos un objeto HashSet
            HashSet hs = new HashSet();

            //Lo cargamos con los valores del array, esto hace quite los repetidos
            hs.addAll(listaContratos);

            //Limpiamos el array
            listaContratos.clear();

            //Agregamos los valores sin repetir
            listaContratos.addAll(hs);

            for (int i = 0; i < listaContratos.size(); i++) {
                cantidaContratos += 1;
                String get = listaContratos.get(i);

            }
            return cantidaContratos;

        } catch (Exception e) {

            addErrorMessage("Error al validar contratos");

            return cantidaContratos;
        }
    }

    public List<ControladorCompareciente> crearCompareciente(int size) throws ServicioExcepcion {
        BigDecimal valor = new BigDecimal(getTramitesControladorBb().getValorTramiteComp().replaceAll(",", ""));
        getTramitesControladorBb().setTipoTramiteCompareciente(comparecienteServicio.buscarPorId(valor));
        List<ControladorCompareciente> list = new ArrayList<ControladorCompareciente>();
        for (int i = 0; i < size; i++) {

            // Parroquia buscarParrouia = parroquiaServicio.find(new Parroquia(), Long.parseLong(getTramitesControladorBb().getTramite().getTraParId().toString()));
            list.add(new ControladorCompareciente(getTramitesControladorBb().getNombrePersona(), getTramitesControladorBb().getPersona().getPerIdentificacion(), getTramitesControladorBb().getTipoTramiteCompareciente().getTtrId().getTtrDescripcion(), getTramitesControladorBb().getTipoTramiteCompareciente().getTpcId().getTpcDescripcion(), Integer.valueOf(getTramitesControladorBb().getTipoTramiteCompareciente().getTtcOrden()), getTramitesControladorBb().getTipoTramiteCompareciente().getTtrId().getTtrId(), getTramitesControladorBb().getPersona().getPerIdConyuge(), getTramitesControladorBb().getPersona(), getTramitesControladorBb().getTipoTramiteCompareciente(), (long) 0, "0", getTramitesControladorBb().getTipoTramiteCompareciente().getTtcId(), "0", "0"));
        }
        Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    public void limpiarComparecientes() {
        getTramitesControladorBb().setIdentificacion(" ");
        getTramitesControladorBb().setNombrePersona(" ");
        getTramitesControladorBb().setValorTramiteComp(" ");
        getTramitesControladorBb().setEstado(Boolean.FALSE);
    }

    public void cargarConyugue() throws ServicioExcepcion {
        System.out.print("id conyugue " + getTramitesControladorBb().getComparecienteSeleccionado().getPersona().getPerIdConyuge().getPerId());
        if (getTramitesControladorBb().getComparecienteSeleccionado().getPersona().getPerIdConyuge().getPerId() == 0) {
            getTramitesControladorBb().setPersonaConyugue(new Persona());
        } else {
            getTramitesControladorBb().setPersonaConyugue(getTramitesControladorBb().getComparecienteSeleccionado().getPersonaConyugue());
            System.out.print("Conyugue-- " + getTramitesControladorBb().getPersonaConyugue().getPerNombre());
        }
    }

    public void buscarConyugue(String identificacionPersona) throws ServicioExcepcion {
        Persona persona = new Persona();
        Persona personaConyugue = new Persona();
        try {

            persona = personaServicio.encontrarPersonaPorIdentificacion(identificacionPersona);
            personaConyugue = personaServicio.encontrarPersonaPorIdentificacion(persona.getPerIdConyuge().getPerIdentificacion());
            //controladorCrearPersonaTramiteProforma.setPersona(personaConyugue);
            System.out.println("id " + identificacionPersona);
            System.out.println("nom Cony " + personaConyugue.getPerNombre());
            //Si el compareciente tiene un conyugue, se cargan los datos de éste en el dialogo permitiendo que 
            //se pueda editar sus datos (a excepcion de la identificacion)
            if (!personaConyugue.equals(null) && personaConyugue.getPerId() != 0) {
                controladorCrearPersonaTramiteProforma.setPersona(personaConyugue);
                controladorCrearPersonaTramiteProforma.setPersonaCompareciente(persona);
                textoBoton = "Modificar";
                readOnly = true;
                //personaConyugue = new Persona();

            } else {
                controladorCrearPersonaTramiteProforma.setPersona(new Persona());
                controladorCrearPersonaTramiteProforma.setPersonaCompareciente(persona);
                textoBoton = "Guardar";
                readOnly = false;
                persona = new Persona();

            }

        } catch (Exception e) {
            controladorCrearPersonaTramiteProforma.setPersona(new Persona());
            controladorCrearPersonaTramiteProforma.setPersonaCompareciente(persona);

            System.out.println(e);
        }

    }

    public void guardarConyugue() throws ServicioExcepcion {

        if (getTramitesControladorBb().getPersonaConyugue().getPerId() == null) {
            if (validadorDeCedula(getTramitesControladorBb().getPersonaConyugue().getPerIdentificacion())) {
                System.out.print("Ingresa aqui per id null");
                getTramitesControladorBb().getPersonaConyugue().setPerIdConyuge(getTramitesControladorBb().getComparecienteSeleccionado().getPersona());
                getTramitesControladorBb().getPersonaConyugue().setPerId(personaServicio.asignarID());
                getTramitesControladorBb().getPersonaConyugue().setPerEstado("A");
                personaServicio.guardarPersona(getTramitesControladorBb().getPersonaConyugue());
                getTramitesControladorBb().getComparecienteSeleccionado().getPersona().setPerIdConyuge(getTramitesControladorBb().getPersonaConyugue());
                personaServicio.guardar(getTramitesControladorBb().getComparecienteSeleccionado().getPersona());
            } else {
                addErrorMessage("La Cédula Ingresada es Incorrecta");
            }
        } else {
            System.out.print("Ingresa aqui persona actualiza");
            personaServicio.guardar(getTramitesControladorBb().getPersonaConyugue());
            getTramitesControladorBb().getPersonaConyugue().setPerIdConyuge(getTramitesControladorBb().getComparecienteSeleccionado().getPersona());
            getTramitesControladorBb().setPersonaConyugue(new Persona());
        }
        getTramitesControladorBb().getComparecienteSeleccionado().getPersona().setPerIdConyuge(getTramitesControladorBb().getPersonaConyugue());
    }

    public void deleteRow() {
        getTramitesControladorBb().getListacomparecientes().remove(getTramitesControladorBb().getComparecienteSeleccionado());
    }

    public void guardarDetalle() throws ServicioExcepcion, IOException {

        if (tramiteDetalleServicio.eliminarTramiteDetalle(getTramitesControladorBb().getTramite().getTraNumero())) {

            for (ControladorTipoTramiteUnico item : listaUnicoTramite) {
                for (ControladorCompareciente compareciente : getTramitesControladorBb().getListacomparecientes()) {
                    if (item.getTtrId().equals(compareciente.getTramiteCompareciente().getTtrId().getTtrId())) {

                        getTramitesControladorBb().setPersonaConyugue(compareciente.getPersona().getPerIdConyuge());
                        getTramitesControladorBb().getTramiteDetalle().setPerId(compareciente.getPersona());
                        getTramitesControladorBb().getTramiteDetalle().setTdtConyuguePerId(compareciente.getPersona().getPerIdConyuge().getPerId());
                        getTramitesControladorBb().getTramiteDetalle().setTdtConyuguePerNombre(compareciente.getPersona().getPerIdConyuge().getPerNombre() + " " + compareciente.getPersona().getPerIdConyuge().getPerApellidoPaterno());
                        getTramitesControladorBb().getTramiteDetalle().setTdtConyuguePerTipoIden(compareciente.getPersona().getPerIdConyuge().getPerTipoIdentificacion());
                        getTramitesControladorBb().getTramiteDetalle().setTdtEstado("A");
                        getTramitesControladorBb().getTramiteDetalle().setTdtFHR(Calendar.getInstance());
                        getTramitesControladorBb().getTramiteDetalle().setTdtFechaRegistro(Calendar.getInstance());
                        getTramitesControladorBb().getTramiteDetalle().setTdtNumeroRepertorio(0);
                        getTramitesControladorBb().getTramiteDetalle().setTdtPerApellidoPaterno(compareciente.getPersona().getPerApellidoPaterno());
                        getTramitesControladorBb().getTramiteDetalle().setTdtPerApellidoMaterno(compareciente.getPersona().getPerApellidoMaterno());
                        getTramitesControladorBb().getTramiteDetalle().setTdtPerIdentificacion(compareciente.getPersona().getPerIdentificacion());
                        getTramitesControladorBb().getTramiteDetalle().setTdtPerNombre(compareciente.getPersona().getPerNombre());
                        getTramitesControladorBb().getTramiteDetalle().setTdtPerTipoContribuyente(" ");
                        getTramitesControladorBb().getTramiteDetalle().setTdtPerTipoIdentificacion(compareciente.getPersona().getPerTipoIdentificacion());
                        getTramitesControladorBb().getTramiteDetalle().setTdtTpcCodigo(compareciente.getTramiteCompareciente().getTpcId().getTpcCodigo());
                        getTramitesControladorBb().getTramiteDetalle().setTdtTpcDescripcion(compareciente.getTramiteCompareciente().getTpcId().getTpcDescripcion());
                        getTramitesControladorBb().getTramiteDetalle().setTdtTpcId(compareciente.getTramiteCompareciente().getTpcId().getTpcId());
                        getTramitesControladorBb().getTramiteDetalle().setTdtTplId(compareciente.getTramiteCompareciente().getTtrId().getTtrValor());
                        getTramitesControladorBb().getTramiteDetalle().setTdtTtrDescripcion(compareciente.getTramiteCompareciente().getTtrId().getTtrDescripcion());
                        getTramitesControladorBb().getTramiteDetalle().setTdtTtrId(compareciente.getTramiteCompareciente().getTtrId().getTtrId());
                        getTramitesControladorBb().getTramiteDetalle().setTdtUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                        getTramitesControladorBb().getTramiteDetalle().setTraNumero(getTramitesControladorBb().getTramite());
                        getTramitesControladorBb().getTramiteDetalle().setTtcId(compareciente.getTramiteCompareciente());
                        getTramitesControladorBb().getTramiteDetalle().setTdtTplDescripcion(compareciente.getTramiteCompareciente().getTtrId().getTtrDescripcion());
                        getTramitesControladorBb().getTramiteDetalle().setTdtParId(compareciente.getParId());
                        getTramitesControladorBb().getTramiteDetalle().setTdtParNombre(compareciente.getParNombre());

                        Integer TdtOrden = tramiteDetalleServicio.buscarOrden(getTramitesControladorBb().getTramite().getTraNumero(), compareciente.getTramiteCompareciente().getTtrId().getTtrId());

                        getTramitesControladorBb().getTramiteDetalle().setTdtOrden(TdtOrden);

                        tramiteDetalleServicio.guardarDetalleTramite(getTramitesControladorBb().getTramiteDetalle());
                        getTramitesControladorBb().getTramite().setTraEstado("PRO");
                        getTramitesControladorBb().getTramite().setTraEstadoRegistro("RA");
                        tramiteServicio.guardar(getTramitesControladorBb().getTramite());

                        //Guardar detalle de acciones
                        Tramite objTramite = getTramitesControladorBb().getTramite();
                        long TraNumero = getTramitesControladorBb().getTramite().getTraNumero();
                        String TraEstado = getTramitesControladorBb().getTramite().getTraEstado();
                        String TraEstadoRegistro = getTramitesControladorBb().getTramite().getTraEstadoRegistro();
                        tramiteUtil.insertarTramiteAccion(objTramite, Long.toString(TraNumero), "TRÁMITE " + compareciente.getTramiteCompareciente().getTtrId().getTtrDescripcion().trim() + ", " + compareciente.getPersona().getPerApellidoPaterno().trim() + " " + compareciente.getPersona().getPerApellidoMaterno().trim() + " " + compareciente.getPersona().getPerNombre().trim() + " " + compareciente.getTramiteCompareciente().getTpcId().getTpcDescripcion().trim(), TraEstado, TraEstadoRegistro, null);
                    }

                }
            }

        } else {
            addErrorMessage("No se puede eliminar el tramite detalle");
        }
    }

    public void guardarCuantia() throws ServicioExcepcion {
        if (tramiteValorServicio.eliminarTramiteValor(getTramitesControladorBb().getTramite().getTraNumero())) {
            for (TramiteValor item : listaTramiteValor) {
                item.setTrvFHR(Calendar.getInstance().getTime());
                item.setTrvFacturado("N");
                item.setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                tramiteValorServicio.guardar(item);
            }
        } else {
            addErrorMessage("No se puede eliminar el tramite valor");
        }
    }

    public void validarCuantiaTipo3() throws ServicioExcepcion {
        if (tipoTramite.getTtrId() != null) {
            tramiteValor.setTtrId(getTipoTramite());
            TipoTramite validarTipoTramite = new TipoTramite();
            validarTipoTramite = tipoTramiteServicio.buscarPorID(getTipoTramite().getTtrId());

            if (validarTipoTramite.getTtrValor() == 3) {
                txtCantidad = true;
                lblCantidad = true;
                txtValor = false;

            } else {
                txtValor = true;
                txtCantidad = false;
                lblCantidad = false;
            }

        }

    }

    public void deleteRowCuantia(int numFila) {
        valorCuantia -= getTramiteValorSeleccionado().getTrvValor().floatValue();
        listaTramiteValor.remove(numFila);

    }

    private BigDecimal valorFinal(BigDecimal v) throws ServicioExcepcion {
        configDetalleFactura = configDetalleServicio.obtenerConfigDetallePorDescripcion("MAXIMOFACTURA");
        if (v.compareTo(configDetalleFactura.getConfigDetalleNumero()) > 0) {
            v = configDetalleFactura.getConfigDetalleNumero();
        }
        return v;
    }

    public void cargarCuantia() throws ServicioExcepcion {

        boolean existePredio = false;
        boolean existeCatastro = false;
        
        tramiteValor.setTtrId(getTipoTramite());
        TipoTramite tipoTramiteConsulta = new TipoTramite();
        tipoTramiteConsulta = tipoTramiteServicio.buscarPorID(getTipoTramite().getTtrId());
        if (tipoTramiteConsulta.getTtrValor() == 3) {
            tramiteValor.setTrvValorBien(BigDecimal.ONE);
        }
        if (tramiteValor.getTrvValorBien() == null) {
            tramiteValor.setTrvValorBien(BigDecimal.ZERO);
        }

        for (TramiteValor listaTramValor : listaTramiteValor) {
            if (tramiteValor.getTraNumPredio() != null) {
                if (Objects.equals(listaTramValor.getTraNumPredio().trim().toUpperCase(), tramiteValor.getTraNumPredio().trim().toUpperCase()) && Objects.equals(listaTramValor.getTtrDescripcion(), tipoTramiteConsulta.getTtrDescripcion())) {
                    existePredio = true;
                    break;
                }
            }

        }

        for (TramiteValor listaTramValor : listaTramiteValor) {
            if (tramiteValor.getTrvNumCatastro() != null) {
                if (Objects.equals(listaTramValor.getTrvNumCatastro().trim().toUpperCase(), tramiteValor.getTrvNumCatastro().trim().toUpperCase()) && Objects.equals(listaTramValor.getTtrDescripcion(), tipoTramiteConsulta.getTtrDescripcion())) {
                    existeCatastro = true;
                    break;
                }
            }
        }

        if (existePredio) {

            addErrorMessage("El predio ya fue ingresado");

        } else {
            if (existeCatastro) {

                addErrorMessage("El catastro ya fue ingresado");

            } else {

                Parroquia buscarParrouia = parroquiaServicio.find(new Parroquia(), Long.parseLong(getTramitesControladorBb().getTramite().getTraParId().toString()));

                if (tipoTramiteConsulta.getTtrValor() == 2 && tramiteValor.getTrvValorBien() == BigDecimal.ZERO) {

                    addErrorMessage("El valor de la cuantía debe ser mayor que cero");

                } else {
                    BigDecimal trvValor = BigDecimal.ZERO;
                    BigDecimal trvValorFinal = BigDecimal.ZERO;
                    BigDecimal trvDescuento = BigDecimal.ZERO;
                    if(tipoDescuentoSeleccionado == null){
                        tipoDescuentoSeleccionado = new TipoDescuento();
                        tipoDescuentoSeleccionado.setTpdPorcentaje(new BigDecimal(0));
                    }
                    else if(tipoDescuentoSeleccionado.getTpdPorcentaje() == null){
                        tipoDescuentoSeleccionado.setTpdPorcentaje(new BigDecimal(0));
                    }                    
                   
                    switch (tipoTramiteConsulta.getTtrValor()) {
                        case 0:
                            trvValor = valorFinal(tramiteValor.getTrvValorBien());
                            trvDescuento = (trvValor.multiply(tipoDescuentoSeleccionado.getTpdPorcentaje())).divide(new BigDecimal(100)); 
                            trvValorFinal = trvValor.subtract(trvDescuento);
                            tramiteValor.setTrvValor(trvValorFinal);
                            tramiteValor.setTrvIva(trvValor.multiply(institucionServicio.porcentajeIva()));
                            tramiteValor.setTrvCantidad(BigDecimal.valueOf(0));
                            tramiteValor.setTtrvPorIva(institucionServicio.porcentajeIva());
                            tramiteValor.setTrvParId(getTramitesControladorBb().getTramite().getTraParId().longValue());
                            tramiteValor.setTrvParNombre(buscarParrouia.getParNombre());
                            tramiteValor.setTrvNumCatastro(tramiteValor.getTrvNumCatastro());
                            tramiteValor.setTvrPorcentajeDescuento(tipoDescuentoSeleccionado.getTpdPorcentaje());
                            tramiteValor.setTrvDescuento(trvDescuento);
                            break;
                        case 1:
                            trvValor = valorFinal(tipoTramiteConsulta.getTtrValorFijo());
                            trvDescuento = (trvValor.multiply(tipoDescuentoSeleccionado.getTpdPorcentaje())).divide(new BigDecimal(100)); 
                            trvValorFinal = trvValor.subtract(trvDescuento);
                            tramiteValor.setTrvValor(trvValorFinal);
                            tramiteValor.setTrvIva(trvValor.multiply(institucionServicio.porcentajeIva()));
                            tramiteValor.setTrvCantidad(BigDecimal.valueOf(0));
                            tramiteValor.setTtrvPorIva(institucionServicio.porcentajeIva());
                            tramiteValor.setTrvParId(getTramitesControladorBb().getTramite().getTraParId().longValue());
                            tramiteValor.setTrvParNombre(buscarParrouia.getParNombre());
                            tramiteValor.setTrvNumCatastro(tramiteValor.getTrvNumCatastro());
                            tramiteValor.setTvrPorcentajeDescuento(tipoDescuentoSeleccionado.getTpdPorcentaje());
                            tramiteValor.setTrvDescuento(trvDescuento);
                            break;
                        case 2:
                            trvValor = valorFinal(cuantiaServicio.valorCuantia(tramiteValor.getTrvValorBien()));
                            trvDescuento = (trvValor.multiply(tipoDescuentoSeleccionado.getTpdPorcentaje())).divide(new BigDecimal(100)); 
                            trvValorFinal = trvValor.subtract(trvDescuento);
                            tramiteValor.setTrvValor(trvValorFinal);
                            tramiteValor.setTrvIva(trvValor.multiply(institucionServicio.porcentajeIva()));
                            tramiteValor.setTrvCantidad(BigDecimal.valueOf(0));
                            tramiteValor.setTtrvPorIva(institucionServicio.porcentajeIva());
                            tramiteValor.setTrvParId(getTramitesControladorBb().getTramite().getTraParId().longValue());
                            tramiteValor.setTrvParNombre(buscarParrouia.getParNombre());
                            tramiteValor.setTrvNumCatastro(tramiteValor.getTrvNumCatastro());
                            tramiteValor.setTvrPorcentajeDescuento(tipoDescuentoSeleccionado.getTpdPorcentaje());
                            tramiteValor.setTrvDescuento(trvDescuento);
                            break;
                        case 3:
                            trvValor = valorFinal(tramiteValor.getTrvValorBien().multiply(tramiteValor.getTrvCantidad().multiply(tipoTramiteConsulta.getTtrValorFijo())));
                            trvDescuento = (trvValor.multiply(tipoDescuentoSeleccionado.getTpdPorcentaje())).divide(new BigDecimal(100)); 
                            trvValorFinal = trvValor.subtract(trvDescuento);
                            tramiteValor.setTrvValor(trvValorFinal);
                            tramiteValor.setTrvIva(trvValor.multiply(institucionServicio.porcentajeIva()));
                            tramiteValor.setTrvCantidad(tramiteValor.getTrvCantidad());
                            tramiteValor.setTtrvPorIva(institucionServicio.porcentajeIva());
                            tramiteValor.setTrvParId(getTramitesControladorBb().getTramite().getTraParId().longValue());
                            tramiteValor.setTrvParNombre(buscarParrouia.getParNombre());
                            tramiteValor.setTrvNumCatastro(tramiteValor.getTrvNumCatastro());
                            tramiteValor.setTvrPorcentajeDescuento(tipoDescuentoSeleccionado.getTpdPorcentaje());
                            tramiteValor.setTrvDescuento(trvDescuento);
                            break;
                    }

					tramiteValor.setTrvEstado("A");
					tramiteValor.setTraNumero(this.getTramitesControladorBb().getTramite());
					for (ControladorTipoTramiteUnico listaUnicoTramiteItem : listaUnicoTramite) {
						if (Objects.equals(listaUnicoTramiteItem.getTtrId(), tramiteValor.getTtrId().getTtrId())) {
							tramiteValor.setTtrDescripcion(listaUnicoTramiteItem.getTtrDescripcion());
							break;
						}
					}
					// poner mayusculas y setear catastro o predio en caso de alguno de estos no se haya ingresado
					if (!tramiteValor.getTrvNumCatastro().trim().isEmpty() && (tramiteValor.getTraNumPredio().trim().isEmpty() || tramiteValor.getTraNumPredio() == null)) {
                                            tramiteValor.setTrvNumCatastro(tramiteValor.getTrvNumCatastro().trim().toUpperCase());	
                                            tramiteValor.setTraNumPredio(tramiteValor.getTrvNumCatastro().trim().toUpperCase());
					} else if (!tramiteValor.getTraNumPredio().trim().isEmpty() && (tramiteValor.getTrvNumCatastro().trim().isEmpty() || tramiteValor.getTrvNumCatastro() == null)) {
						tramiteValor.setTraNumPredio(tramiteValor.getTraNumPredio().trim().toUpperCase());	
                                            tramiteValor.setTrvNumCatastro(tramiteValor.getTraNumPredio().trim().toUpperCase());
					}

                    listaTramiteValor.add(tramiteValor);

                    //CREAR LINEA CERTIFICADO
                    if (!getTramitesControladorBb().getTramite().getTraClase().equals("J")
                            && (getTramiteValor().getTraNumPredio() != null || getTramiteValor().getTrvNumCatastro() != null)) {
                        TipoCertificado tipoCert = servicioTipoCertificado.buscarPorTipo("G");

                        Boolean existeTramValCertConPreCat = false;
                        BigDecimal trvDescCert = BigDecimal.ZERO;
                        BigDecimal trvValorFinCert = BigDecimal.ZERO;
                        trvDescCert = (tipoCert.getTroValor().multiply(tipoDescuentoSeleccionado.getTpdPorcentaje())).divide(new BigDecimal(100)); 
                        trvValorFinCert = tipoCert.getTroValor().subtract(trvDescCert);
                        TramiteValor tramVal = new TramiteValor(
                                null,
                                trvValorFinCert,
                                tramiteValor.getTrvEstado(),
                                tramiteValor.getTrvUser(),
                                tramiteValor.getTrvFHR(),
                                tipoCert.getTroValor().multiply(institucionServicio.porcentajeIva()),
                                tramiteValor.getTtrId(),
                                tramiteValor.getTraNumero(),
                                tramiteValor.getTraNumPredio(),
                                tramiteValor.getTrvValorBien(),
                                tramiteValor.getTrvCantidad(),
                                tramiteValor.geTtrvPorIva(),
                                tramiteValor.getTrvParId(),
                                tramiteValor.getTrvParNombre(),
                                tramiteValor.getTrvNumCatastro(),
                                tramiteValor.getTrvAccion(),
                                tramiteValor.getTtrDescripcion(),
                                tramiteValor.getTienePropHija(),
                                tramiteValor.getTrvPrincipal(),
                                "CERTIFICADO DE GRAVAMEN");
                        tramVal.setTrvDescuento(trvDescCert);
                        tramVal.setTvrPorcentajeDescuento(tipoDescuentoSeleccionado.getTpdPorcentaje());
                        for (TramiteValor tramiteValor1 : listaTramiteValor) {
                            if (tramiteValor1.getTrvCertificado() != null && !tramiteValor1.getTrvCertificado().isEmpty()) {
                                if (tramiteValor1.getTrvNumCatastro().equals(tramiteValor.getTrvNumCatastro())
                                        || tramiteValor1.getTraNumPredio().equals(tramiteValor.getTraNumPredio())) {
                                    existeTramValCertConPreCat = true;
                                }
                            }
                        }
                        if (!existeTramValCertConPreCat) {
                            listaTramiteValor.add(tramVal);
                        }
                    }

                    valorCuantia = 0;
                    for (TramiteValor listaTramiteValorSuma : listaTramiteValor) {
                        valorCuantia += listaTramiteValorSuma.getTrvValor().floatValue();
                    }
                }

                tramiteValor = new TramiteValor();
                tipoTramite = new TipoTramite();
                txtCantidad = false;
                lblCantidad = false;
            }
        }
    }

    public boolean validarIngresoCuantia() {
        boolean existe = false;
        int cantidaContratos = 0;
        //Limpiamos el array
        listaContratos.clear();
        for (TramiteValor tramiteValorUnico : getListaTramiteValor()) {

            listaContratos.add(tramiteValorUnico.getTtrId().getTtrId().toString());

        }

        //Creamos un objeto HashSet
        HashSet hs = new HashSet();

        //Lo cargamos con los valores del array, esto hace quite los repetidos
        hs.addAll(listaContratos);

        //Limpiamos el array
        listaContratos.clear();

        //Agregamos los valores sin repetir
        listaContratos.addAll(hs);

        for (int i = 0; i < listaContratos.size(); i++) {
            cantidaContratos += 1;

        }

        if (cantidaContratos < getTramitesControladorBb().getTramite().getTraNumeroContrato()) {
            existe = true;
        }

        return existe;
    }

    public void actualizarEstado() {
        getTramitesControladorBb().setFiltro(Boolean.TRUE);
        getTramitesControladorBb().setCopia(Boolean.FALSE);
    }

    public void cargarTramitesModelo() throws ServicioExcepcion {
        getTramitesControladorBb().setIdentificacion(" ");
        getTramitesControladorBb().setListaTramiteModelo(tramiteDetalleServicio.buscarPorTramiteUsuarioModelo(getTramitesControladorBb().getTramite().getTraTipo()));
    }

    public void seleccionarModelo(SelectEvent event) throws IOException {
        try {

            getTramitesControladorBb().setTramite(tramiteServicio.buscarTramitePorNumero(((TramiteUsuario) event.getObject()).getTraNumero().getTraNumero()));

            if (getTramitesControladorBb().getTramite() != null) {
                getTramitesControladorBb().setListaTramitesDetalle(tramiteDetalleServicio.buscarPorNumeroDeTramite(((TramiteUsuario) event.getObject()).getTraNumero().getTraNumero()));

            }

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(TramitesControlador.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarCopia(SelectEvent event) throws ServicioExcepcion {
        getTramitesControladorBb().setCopia(Boolean.TRUE);

        if (getTramitesControladorBb().getTramite() != null) {
            getTramitesControladorBb().setListaTramitesDetalle(tramiteDetalleServicio.buscarPorNumeroDeTramite(((TramiteUsuario) event.getObject()).getTraNumero().getTraNumero()));

        }

    }

    public void verificarComparecientesContrato() throws ServicioExcepcion {
        if (getTramitesControladorBb().getTramite().getTraNumeroContrato() == null) {

            addErrorMessage("Error. Ingrese Nro Contratos");

        } else {
            if (!validarContratosGuardar()) {

                /*Validar que la cuantia este repartida */
                if (validarIngresoCuantia()) {
                    addErrorMessage("Por favor revise la distribución de la cuantía, debe contener una distribución por contrato como mínimo.");
                } else {

                    try {
                        guardarDetalle();
                        guardarCuantia();

                        TramiteUsuario tramiteUsuario = new TramiteUsuario();
                        tramiteUsuario = tramiteUsuarioServicio.buscarPorTramite(getTramitesControladorBb().getTramite());

                        // Actualizando el estado de tramite usuario al terminar el trabajo  
                        tramiteUsuario.setTusEstadoDetalle("TIENE COMPARECIENTES AGREGADOS");
                        tramiteUsuarioServicio.edit(tramiteUsuario);

                        //FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/bandejaAsignacionTrabajos.jsf");
                        readOnlyDatos = true;
                    } catch (IOException ex) {
                        Logger.getLogger(TramitesControlador.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void cargarInstitucion() throws ServicioExcepcion {
        getTramitesControladorBb().setInstitucion(institucionServicio.obtenerInstitucion());
    }

    public void validarCedula() {
        if (validadorDeCedula(getTramitesControladorBb().getPersonaConyugue().getPerIdentificacion())) {
            addSuccessMessage("La Cédula Ingresada es Correcta");
        } else {
            addErrorMessage("La Cédula Ingresada es Incorrecta");
        }

    }

    public void cuantiaCopiaCompareciente() throws ServicioExcepcion {
        if (getTramitesControladorBb().getCopia()) {
            for (ControladorCompareciente compareciente : getTramitesControladorBb().getListacomparecientes()) {
                cargarCuantiaCopia(compareciente.getTramiteCompareciente());
            }
        }
    }

    public void cargarCuantiaCopia(TipoTramiteCompareciente tipoTramiteCompareciente) throws ServicioExcepcion {

    }

    public void aCaja() throws IOException, ServicioExcepcion {
        if (getTramitesControladorBb().getTramite().getTraNumeroContrato() == null) {

            addErrorMessage("Error. Ingrese Nro Contratos");

        } else {
            if (!validarContratosGuardar()) {

                /*Validar que la cuantia este repartida */
                if (validarIngresoCuantia()) {
                    addErrorMessage("Por favor revise la distribución de la cuantía, debe contener una distribución por contrato como mínimo.");
                } else {

                    try {

                        guardarDetalle();
                        guardarCuantia();
                        //generarReporte();

                        // Setar variables con daros por defecto
                        Tramite objTramite = getTramitesControladorBb().getTramite();
                        long TraNumero = getTramitesControladorBb().getTramite().getTraNumero();
                        String TraEstado = "CAJ";
                        String TraEstadoRegistro = "RA";

                        TramiteUsuario tramiteUsuario = new TramiteUsuario();
                        tramiteUsuario = tramiteUsuarioServicio.buscarPorTramite(objTramite);
                        // Actualizando el estado de tramite usuario al terminar el trabajo
                        tramiteUsuario.setTusEstado("I");
                        tramiteUsuario.setTusEstadoDetalle("TIENE COMPARECIENTES AGREGADOS");
                        tramiteUsuarioServicio.guardar(tramiteUsuario);

                        //Actualizar estado de tramite
                        tramiteUtil.cambiarEstadoTramite(objTramite, TraEstado, TraEstadoRegistro);

                        //Guardar detalle de acciones
                        tramiteUtil.insertarTramiteAccion(objTramite, Long.toString(TraNumero), "Trámite pasó a caja", "PRO", TraEstadoRegistro, null);
                        cargaLaboralUtil.restarCargaAsignadaAlUsuario(dataManagerSesion.getUsuarioLogeado().getUsuId(), "RVT");
                        listarTramites();

                        //FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/bandejaAsignacionTrabajos.jsf");
                        readOnlyDatos = true;
                        InputStream is = crearReporte();
                        enviarCorreo(is);

                    } catch (IOException ex) {
                        Logger.getLogger(TramitesControlador.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }

    public void cajasYenviarCorreo() throws ServicioExcepcion {
        if (cajas()) {
            InputStream is = crearReporte();
            enviarCorreo(is);
        }
    }

    public void enviarCorreo(InputStream adjunto) {
        try {

            Tramite tramiteSeleccionado = getTramitesControladorBb().getTramite();
            String idPersona = tramiteSeleccionado.getTraPerIdentificacion();
            Persona personaSel = null;

            personaSel = servicioPersona.encontrarPersonaPorIdentificacion(idPersona);
            if (personaSel != null) {
                String asunto = "Creación de proforma : " + tramiteSeleccionado.getTraNumero();
                String mensaje = "Estimado " + personaSel.getPerApellidoMaterno()
                        + " " + personaSel.getPerApellidoPaterno()
                        + " " + personaSel.getPerNombre() + ", su proforma con número " + tramiteSeleccionado.getTraNumero()
                        + " ha sido generada.";
                String correo = "" + personaSel.getPerEmail();
                if (correo != null || !correo.isEmpty()) {
                    utilCorreo.enviaEmailConAdjunto(correo, asunto, mensaje, "pdf", adjunto, "Proforma.pdf", " ");
                }
            } else {
                JsfUtil.addWarningMessage("No se envió el correo porque no se encontró los datos de la persona");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean cajas() throws ServicioExcepcion {//Pasa el estado del tramite a cajas y termina el trabajo de tramite usuario
        boolean bolPasarAcajas = false;
        try {
            Tramite objTramite = getTramitesControladorBb().getTramiteUsuarioSeleccionado().getTraNumero();
            long TraNumero = getTramitesControladorBb().getTramiteUsuarioSeleccionado().getTraNumero().getTraNumero();

            if (!tramiteDetalleServicio.contarDetalle(TraNumero)) {
                // Setar variables con daros por defecto            
                String TraEstado = "CAJ";
                String TraEstadoRegistro = "RA";

                // Actualizando el estado de tramite usuario al terminar el trabajo
                getTramitesControladorBb().getTramiteUsuarioSeleccionado().setTusEstado("I");
                getTramitesControladorBb().getTramiteUsuarioSeleccionado().setTusEstadoDetalle("S");
                tramiteUsuarioServicio.guardar(getTramitesControladorBb().getTramiteUsuarioSeleccionado());

                //Actualizar estado de tramite
                tramiteUtil.cambiarEstadoTramite(objTramite, TraEstado, TraEstadoRegistro);

                //Guardar detalle de acciones
                tramiteUtil.insertarTramiteAccion(objTramite, Long.toString(TraNumero), "Trámite pasó a caja", TraEstado, TraEstadoRegistro, null);
                listarTramites();
                cargaLaboralUtil.restarCargaAsignadaAlUsuario(dataManagerSesion.getUsuarioLogeado().getUsuId(), "RVT");

                bolPasarAcajas = true;

            } else {
                addErrorMessage("No existe comparecientes ingresados en el trámite");
            }

        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al ingresar el trámite a cajas");
            e.printStackTrace();
        }
        return bolPasarAcajas;
    }

    public void cargarTipoTramite() throws ServicioExcepcion {
        getTramitesControladorBb().setListaTipoTramite(tipoTramiteServicio.listaTipoTramite());
    }

    public void actualizarContrato() {
        getTramitesControladorBb().getTramite().setTraNumeroContrato(getTramitesControladorBb().getTramite().getTraNumeroContrato().shortValue());
    }

    public void salir() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");
    }

    public void limpiar() throws IOException {
        listarTramites();
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/bandejaAsignacionTrabajos.jsf");

    }

    private Object getTramite() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean validarContratosGuardar() {
        /* Valida la cantidad de contrataos ingresado en un tramite*/
        boolean validacion = false;
        try {
            int cantidaContratos = 0;
            //Limpiamos el array
            listaContratos.clear();
            for (ControladorCompareciente compareciente : getTramitesControladorBb().getListacomparecientes()) {

                listaContratos.add(compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString());

            }

            //Creamos un objeto HashSet
            HashSet hs = new HashSet();

            //Lo cargamos con los valores del array, esto hace quite los repetidos
            hs.addAll(listaContratos);

            //Limpiamos el array
            listaContratos.clear();

            //Agregamos los valores sin repetir
            listaContratos.addAll(hs);

            boolean noExisteCompareciente = false;
            boolean validacionCompareciente = false;

            for (int i = 0; i < listaContratos.size(); i++) {
                cantidaContratos++;
                String get = listaContratos.get(i);
                int contarS = 0;
                int contarN = 0;

                for (ControladorCompareciente compareciente : getTramitesControladorBb().getListacomparecientes()) {
                    if (getTramitesControladorBb().getListacomparecientes().size() == 1) {
                        switch (compareciente.getTramiteCompareciente().getTtrId().getTtrCodigo()) {
                            case 7:
                                break;
                            case 9:
                                break;
                            case 10:
                                break;
                            default:
                                if (compareciente.getTramiteCompareciente().getTtcPropietario().equals("S") && compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString().equals(get)) {
                                    contarS++;
                                }
                                if (compareciente.getTramiteCompareciente().getTtcPropietario().equals("N") && compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString().equals(get)) {
                                    contarN++;
                                }
                                validacionCompareciente = true;
                                break;
                        }
                    } else {
                        if (compareciente.getTramiteCompareciente().getTtcPropietario().equals("S") && compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString().equals(get)) {
                            contarS++;
                        }
                        if (compareciente.getTramiteCompareciente().getTtcPropietario().equals("N") && compareciente.getTramiteCompareciente().getTtrId().getTtrId().toString().equals(get)) {
                            contarN++;
                        }
                        validacionCompareciente = true;
                    }
                }

                if (validacionCompareciente) {
                    if (contarS < 1 || contarN < 1) {
                        noExisteCompareciente = true;
                    }
                }

            }

            if (cantidaContratos != getTramitesControladorBb().getTramite().getTraNumeroContrato()) {
                validacion = true;
                addErrorMessage("Error. El N° de contratos es diferente al ingresado en los comparecientes.");

            } else {
                if (noExisteCompareciente) {
                    validacion = true;
                    addErrorMessage("Error. Comparecientes Incompletos");
                }
            }

            return validacion;

        } catch (Exception e) {

            addErrorMessage("Error al validar contratos");

            return validacion;
        }
    }

    public InputStream crearReporte() {
        System.out.println("com.rm.empresarial.controlador.TramitesControlador.generarReporte()");
        InputStream is = null;
        if (getTramitesControladorBb().getTramite().getTraNumero() != null) {
            int Tramite = getTramitesControladorBb().getTramite().getTraNumero().intValue();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numTramite", Tramite);
            try {
                ByteArrayOutputStream bos = reporteUtil.crearReporteEnPDF("repo", "proforma", parametros);

                is = new ByteArrayInputStream(bos.toByteArray());

            } catch (JRException ex) {
                Logger.getLogger(TramitesControlador.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (IOException ex) {
                Logger.getLogger(TramitesControlador.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (NamingException ex) {
                Logger.getLogger(TramitesControlador.class
                        .getName()).log(Level.SEVERE, null, ex);

            } catch (SQLException ex) {
                Logger.getLogger(TramitesControlador.class
                        .getName()).log(Level.SEVERE, null, ex);
            }

        }
        return is;

    }

//    public void enviarCorreoConPdfAdjunto() {
//
//        try {
//            String idPersona = tramiteSeleccionado.getTraPerIdentificacion();
//            Persona personaSel = servicioPersona.encontrarPersonaPorIdentificacion(idPersona);
//            if (personaSel != null) {
//                String asunto = "Suspensión del trámite: " + tramiteSeleccionado.getTraNumero();
//                String mensaje = "Estimado " + personaSel.getPerApellidoMaterno()
//                        + " " + personaSel.getPerApellidoPaterno()
//                        + " " + personaSel.getPerNombre() + ", su trámite con numero " + tramiteSeleccionado.getTraNumero()
//                        + " ha sido suspendido por las razones adjuntas en el documento.";
//                String correo = "" + personaSel.getPerEmail();
//                System.out.println("Enviando correo a: " + correo);
//                if (correo != null || !correo.isEmpty()) {
//                    utilCorreo.enviaEmailConAdjunto(correo, asunto, mensaje, "pdf", stream, "RazonesDeSuspension.pdf", " ");
//                }
//            } else {
//                JsfUtil.addWarningMessage("No se envió el correo porque no se encontró los datos de la persona");
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
////            String correo = getPersona().getPerEmail().trim();
////            String asunto = "Ingreso Tramite " + getTramite().getTraNumero();
////            String mensaje = "Se ingresó el tramite, desde: " + getTramite().getTraInicial()
////                    + " hasta: " + getTramite().getTraFinal();
//
//    }
    public void generarReporte() throws ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.TramitesControlador.generarReporte()");
        Date fechaImpresion = Calendar.getInstance().getTime();
        Institucion institucion = institucionServicio.obtenerInstitucion();
        if (getTramitesControladorBb().getTramite().getTraNumero() != null) {
            totalTraValor = BigDecimal.ZERO;
            TramiteDetalle traDetalleConsultado = tramiteDetalleServicio.buscarPorNumTramite(getTramitesControladorBb().getTramite().getTraNumero());
            String cliente = traDetalleConsultado.getPerId().getPerApellidoPaterno() + " " + traDetalleConsultado.getPerId().getPerApellidoMaterno() + " " + traDetalleConsultado.getPerId().getPerNombre();
            String ciCLiente = traDetalleConsultado.getPerId().getPerIdentificacion();
            int tramite = getTramitesControladorBb().getTramite().getTraNumero().intValue();
            List<TramiteValor> listtravalor = tramiteValorServicio.buscarTraValor(tramite);
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

    public void setearVariables() {
        tramiteValor = new TramiteValor();
        tipoTramite = new TipoTramite();
    }

}
