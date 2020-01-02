/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.ControladorCajasRecBb;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.num_provincias;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.controlador.util.UtilFechaEntrega;
import com.rm.empresarial.dao.FacturaDetalleImpuestoDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteTiempoDao;
import com.rm.empresarial.dao.consultaComprobantesDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FacturaDetalleImpuesto;
import com.rm.empresarial.modelo.FacturaFormaPago;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Parametros;
import com.rm.empresarial.modelo.Serie;
import com.rm.empresarial.modelo.TipoFormaPago;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteTiempo;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.CajaServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.ConfigServicio;
import com.rm.empresarial.servicio.FacturaDetalleAdicionalServicio;
import com.rm.empresarial.servicio.FacturaDetalleImpuestoServicio;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.FacturaFormaPagoServicio;
import com.rm.empresarial.servicio.FacturaInfoAdicionalServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.ParametrosServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.RepositorioSRIServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoFormaPagoServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import com.rm.empresarial.servicio.UsuarioCajaServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Wilson Herrera
 */
@ViewScoped
@Named
public class ControladorCajasRecaudacion extends JsfUtil implements Serializable {

    @Inject
    private UtilFechaEntrega utilFechaEntrega;

    @EJB
    private TramiteServicio tramiteServicio;

    @EJB
    private PersonaServicio personaServicio;

    @EJB
    private RepositorioSRIServicio repositorioSRIServicio;

    @EJB
    private TramiteValorServicio tramiteValorServicio;

    @EJB
    private FacturaServicio facturaServicio;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    private FacturaDetalleServicio facturaDetalleServicio;

    @EJB
    private FacturaDetalleAdicionalServicio facturaDetalleAdicionalServicio;

    @EJB
    private consultaComprobantesDao consultaComprobantesDao;

    @EJB
    private FacturaInfoAdicionalServicio facturaInfoAdicionalServicio;

    @EJB
    private CajaServicio cajaServicio;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @EJB
    private UsuarioCajaServicio usuarioCajaServicio;

    @EJB
    private TipoFormaPagoServicio tipoFormaPagoServicio;

    @EJB
    private FacturaFormaPagoServicio facturaFormaPagoServicio;

    @EJB
    private ConfigDetalleServicio servicioDetalleConfig;

    @EJB
    private ParametrosServicio servicioParametros;

    @EJB
    private TramiteTiempoDao servicioTramiteTiempo;

    @EJB
    private TramiteDao servicioTramite;

    @EJB
    private FacturaServicio servicioFactura;

    @EJB
    private FacturaDetalleImpuestoDao servicioFacturaDetalleImpuesto;

    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;

    @Getter
    @Setter
    @Inject
    private ControladorCajasRecBb cajasRecBb;

    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    @Getter
    @Setter
    private ControladorModelosSRI controladorModelosSRI;

    @Inject
    private DataManagerSesion dataManagerSesion;

    @Inject
    private PersonaUtil personaUtil;

    @Inject
    private TramiteUtil tramiteUtil;

    @Getter
    @Setter
    private Institucion institucion;

    @Getter
    @Setter
    private Parametros parametros;

    @Getter
    @Setter
    private Date fechaEntrega;

    @Getter
    @Setter
    private Calendar fechaEntregaAux;

//    private List<FacturaDetalle> listaFacturaDetalleGuardadas;
    private List<Factura> listaFacturaGuardadas;

    @PostConstruct
    public void inicializar() {
        getCajasRecBb().inicializar();
        cargarInstitucion();

        cargarCajas();
        cargarPago();
        getCajasRecBb().getNuevaPersona().setPerApellidoPaterno(" ");
        getCajasRecBb().getNuevaPersona().setPerNombre(" ");
        getCajasRecBb().getNuevaPersona().setPerIdentificacion(" ");
        getCajasRecBb().getNuevaPersona().setPerApellidoMaterno(" ");
        getCajasRecBb().getNuevaPersona().setPerCelular(" ");
        getCajasRecBb().getNuevaPersona().setPerTelefono(" ");
        getCajasRecBb().getNuevaPersona().setPerEmail(" ");
        getCajasRecBb().setObservacion(" ");
        if (getCajasRecBb().getFactura().getFacSubTotal() == null) {
            getCajasRecBb().getFactura().setFacSubTotal(BigDecimal.ZERO);
            getCajasRecBb().getFactura().setFacIva(BigDecimal.ZERO);
            getCajasRecBb().getFactura().setFacTotal(BigDecimal.ZERO);
            getCajasRecBb().getFactura().setFacDescuento(BigDecimal.ZERO);
        }

        if (getCajasRecBb().getNombrePersona() == null) {
            getCajasRecBb().setNombrePersona(" ");
        }

        try {
            cargarParametros();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarParametros() throws ServicioExcepcion {
        this.setParametros(servicioParametros.buscarPorParametro("CAJ"));
    }

    public void crearTramiteTiempo(Tramite tramite, String unidadTiempo, Long cantidadTiempo, Date fechaEntrega, TipoTramite tipoTram) {
        //***tramite tiempo
        TramiteTiempo nuevoTramiteTiempo = new TramiteTiempo(
                null,
                unidadTiempo,
                cantidadTiempo,
                tramite.getTraEstado(),
                fechaEntrega);
        nuevoTramiteTiempo.setTraNumero(tramite);
        nuevoTramiteTiempo.setTtrId(tipoTram);
        servicioTramiteTiempo.create(nuevoTramiteTiempo);
    }

    public void cargarInstitucion() {
        try {
            getCajasRecBb().setInstitucion(institucionServicio.obtenerInstitucion());
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cargarCajas() {
        if (usuarioCajaServicio.buscarCajaPorUsuario(dataManagerSesion.getUsuarioLogeado()) != null) {
            getCajasRecBb().setUsuarioCaja(usuarioCajaServicio.buscarCajaPorUsuario(dataManagerSesion.getUsuarioLogeado()));
            ///info crear registro en la tabla usuario caja
            getCajasRecBb().setCaja(getCajasRecBb().getUsuarioCaja().getCajId());
        } else {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");
            } catch (Exception e) {
                addErrorMessage("Error al redireccionar la pagina");
            }
        }

    }

    public void cargarSecuenciaFactura() {
        try {
//            short puntoEmision = 0;
            if (dataManagerSesion.getUsuarioLogeado().getUsuLogin() != null) {
                secuenciaControlador.generarSecuenciaFactura(dataManagerSesion.getUsuarioLogeado().getUsuId());
//                for (Serie serie : getCajasRecBb().getCaja().getSucId().getSerieList()) {
//                    if (serie.getSerSecuenciaFactura() > 0) {
//                        puntoEmision = serie.getSerPuntoEmision();
//                    }
//                }
//
//                String secuencia = Integer.toString(getSecuenciaControlador().getControladorBb().getNumeroTramite());
//                getCajasRecBb().setNumeroFactura(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                getCajasRecBb().setNumeroComprobante(getSecuenciaControlador().getControladorBb().getSecuenciaFactura());

            }
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//    public void borrarDatosExistentesDeFacturaPorTramite() {
//        System.out.println("Borrando datos relacionados a factura y factura detalle por el tramite ingresado");
//        List<Factura> listaFacturaSel;
//        List<FacturaDetalle> listaFacturaDetalleSel;
//        try {
//
//            listaFacturaSel = facturaDetalleServicio.listarFacturaPor_Tramite(new Integer(getCajasRecBb().getNumeroTramite()));
//            listaFacturaDetalleSel = facturaDetalleServicio.listarPor_Tramite(new Integer(getCajasRecBb().getNumeroTramite()));
//
//            if (listaFacturaDetalleSel != null) {
//                for (FacturaDetalle facturaDetalle : listaFacturaDetalleSel) {
//                    facturaDetalleAdicionalServicio.eliminarPor_FacturaDetalle(facturaDetalle.getFdtId());
//                    servicioFacturaDetalleImpuesto.eliminarPor_FacturaDetalle(facturaDetalle.getFdtId());
//                }
//            }
//            if (listaFacturaSel != null) {
//                for (Factura factura : listaFacturaSel) {
//                    facturaDetalleServicio.eliminarPor_Factura(factura.getFacId());
//                    facturaFormaPagoServicio.eliminarPor_Factura(factura.getFacId());
//                    facturaInfoAdicionalServicio.eliminarPor_Factura(factura.getFacId());
//                    facturaServicio.eliminarPor_Factura(factura.getFacId());
//                }
//            }
//
//            List<TramiteValor> listTramVal = tramiteValorServicio.buscarPorTramite_Facturado(getCajasRecBb().getTramite());
//            if (listTramVal != null) {
//                for (TramiteValor tramiteValor : listTramVal) {
//                    tramiteValor.setTrvFacturado("N");
//                    tramiteValorServicio.edit(tramiteValor);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
////                
//    }
    public void buscarTramite() throws ServicioExcepcion {

        getCajasRecBb().getListaFacturaDetalle().clear();
        if (!getCajasRecBb().getNumeroTramite().isEmpty()) {
            getCajasRecBb().setTramite(tramiteServicio.buscarTramitePorNumero(Long.valueOf(getCajasRecBb().getNumeroTramite().trim())));
        }
        if (getCajasRecBb().getTramite() != null) {
            if (getCajasRecBb().getTramite().getTraEstado().equals("CAJ")) {
//                borrarDatosExistentesDeFacturaPorTramite();

                SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Calendar c = getCajasRecBb().getTramite().getTraFechaRecepcion();
                fmt.setCalendar(c);
                getCajasRecBb().setFechaIngreso(fmt.format(c.getTime()));
                SimpleDateFormat fmt1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Calendar c1 = getCajasRecBb().getTramite().getTraFechaEntrega();
                fmt1.setCalendar(c1);
                getCajasRecBb().setFechaEntrega(fmt1.format(c1.getTime()));
                getCajasRecBb().setListaTramiteValor(tramiteValorServicio.buscarPorTramite_NoFacturado(getCajasRecBb().getTramite()));

                if (getCajasRecBb().getListaTramiteValor().size() > 0) {
                    Logger.getLogger("la lista de tramites no esta vacia " + getCajasRecBb().getListaTramiteValor().size());
                    verificarDetalle(getCajasRecBb().getTramite());
                    if (!getCajasRecBb().getEstado()) {
                        llenarDetalle();
                    } else {
                        addErrorMessage("Error Tramite ya Agregado");
                    }
                } else {
                    addErrorMessage("El Tramite no Tiene valores");
                }
            } else {
                addErrorMessage("El Tramite no esta en Cajas");
            }
        } else {
            addErrorMessage("El Tramite no Existe");
        }
    }

    public void buscarPersona() throws IOException {

        try {

            if (getCajasRecBb().getIdentificacion() != null) {

                getCajasRecBb().setPersona(personaUtil.obtenerDatosPersona(getCajasRecBb().getIdentificacion()));
                if (getCajasRecBb().getPersona() == null) {
                    addErrorMessage("Persona no Registrada");
                }
            }
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(RecepcionDocumentacionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void nommbrePersona() {
        getCajasRecBb().setNombrePersona(getCajasRecBb().getPersona().getPerNombre() + " " + getCajasRecBb().getPersona().getPerApellidoPaterno());

    }

    /**
     * <b>Metodo que llena la lista de detalle de la factura </b>
     *
     * @param size size
     * @return lista
     * @throws ServicioExcepcion excepxion
     */
    public List<FacturaDetalle> crearDetalleFac(int size) throws ServicioExcepcion {

        List<FacturaDetalle> list = new ArrayList<FacturaDetalle>();
        FacturaDetalleImpuesto list2 = new FacturaDetalleImpuesto();
        Integer tarifa = 0;
        String codigoPorcentaje = "0";
        int contador = 0;
        int contador1 = 1;
        for (TramiteValor tramite : getCajasRecBb().getListaTramiteValor()) {
            BigDecimal trvIvaDet = BigDecimal.ZERO;
            BigDecimal valorSinDescuento = BigDecimal.ZERO;
            BigDecimal totalAux = tramite.getTrvValor().subtract(BigDecimal.ZERO).setScale(2, RoundingMode.DOWN);
            Logger.getLogger("total " + totalAux);
            Integer numeroAux = tramite.getTraNumero().getTraNumero().intValue();
            BigInteger idTipTraAux = BigInteger.valueOf(tramite.getTtrId().getTtrId());
            if (tramite.getTrvIva().compareTo(BigDecimal.ZERO) == 0) {
                tarifa = 0;
                trvIvaDet = trvIvaDet.add(tramite.getTrvValor()).setScale(2, RoundingMode.DOWN);
            } else {
                BigDecimal tarifas = getCajasRecBb().getInstitucion().getInsIva().multiply(BigDecimal.valueOf(100));
                tarifa = tarifas.intValue();
                codigoPorcentaje = "2";
                trvIvaDet = trvIvaDet.add(getCajasRecBb().getInstitucion().getInsIva().multiply(tramite.getTrvValor())).setScale(2, RoundingMode.DOWN);

            }
            getCajasRecBb().getFacturaDetalleImpuesto().setFdiCodigo("2");
            getCajasRecBb().getFacturaDetalleImpuesto().setFdiCodigoPorcentaje(codigoPorcentaje);
            getCajasRecBb().getFacturaDetalleImpuesto().setFdiTarifa(new BigDecimal(tarifa));
            System.out.print("" + tramite.getTrvValor());
            getCajasRecBb().getFacturaDetalleImpuesto().setFdiBaseImponible(tramite.getTrvValor());
            //Agregar detalles inpuesto a la lista

            //Agregar detalles inpuesto a la lista
            System.out.print("base de la lista" + list2.getFdiBaseImponible());
            if (tramite.getTraNumPredio() == null) {
                tramite.setTraNumPredio("0");
            }
            if (tramite.getTrvNumCatastro() == null) {
                tramite.setTrvNumCatastro("0");
            }
            String ttrDescripcion = "";
            if (tramite.getTrvCertificado() != null && !tramite.getTrvCertificado().isEmpty()) {
                ttrDescripcion = tramite.getTrvCertificado();
            } else {
                ttrDescripcion = tramite.getTtrId().getTtrDescripcion();
            }

            if (tramite.getTrvCertificado() != null && !tramite.getTrvCertificado().isEmpty()) {
                tramite.getTrvCertificado();
            }
            if(tramite.getTrvDescuento() != null){
                valorSinDescuento =  tramite.getTrvDescuento().add(tramite.getTrvValor()).setScale(2, RoundingMode.DOWN);                
            }
            else{
                valorSinDescuento = tramite.getTrvValor().setScale(2, RoundingMode.DOWN);
            }
            
            list.add(new FacturaDetalle(null, numeroAux, BigDecimal.ONE, valorSinDescuento, tramite.getTrvDescuento(), tramite.getTrvValor(), idTipTraAux, ttrDescripcion, trvIvaDet, getCajasRecBb().getFacturaDetalleImpuesto().getFdiCodigo(), getCajasRecBb().getFacturaDetalleImpuesto().getFdiCodigoPorcentaje(), getCajasRecBb().getFacturaDetalleImpuesto().getFdiTarifa(), getCajasRecBb().getFacturaDetalleImpuesto().getFdiBaseImponible(), tramite.getTraNumPredio().toString(), tramite.getTrvNumCatastro().toString()));

            contador++;
            contador1++;
        }
        //Collections.sort(list, Collections.reverseOrder());
        return list;
    }

    /**
     * <b>Metodo que agrega los detalles de la lista al objeto </b>
     *
     * @throws ServicioExcepcion excepxion
     */
    public void llenarDetalle() throws ServicioExcepcion {
        int contador = 0;
        int contador1 = 1;
        Integer tarifa = 0;
        String codigoPorcentaje = "0";
        BigDecimal trvIvaDet = BigDecimal.ZERO;
        for (TramiteValor tramite : getCajasRecBb().getListaTramiteValor()) {
            if (getCajasRecBb().getListaFacturaDetalle().size() > 1) {

                if (!getCajasRecBb().getEstado()) {
                    FacturaDetalle detalle = crearDetalleFac(contador1).get(contador);
                    getCajasRecBb().getListaFacturaDetalle().add(detalle);
                    // addSuccessMessage("Tramite Agregado Correctamente");
                    calcularValoresFactura();

                }

            } else {
                FacturaDetalle detalle = crearDetalleFac(contador1).get(contador);
                if (tramite.getTrvIva().compareTo(BigDecimal.ZERO) == 0) {

                    trvIvaDet = trvIvaDet.add(tramite.getTrvValor()).setScale(2, RoundingMode.DOWN);

                } else {
                    BigDecimal tarifas = getCajasRecBb().getInstitucion().getInsIva().multiply(BigDecimal.valueOf(100));
                    tarifa = tarifas.intValue();
                    codigoPorcentaje = "2";
                    trvIvaDet = trvIvaDet.add(getCajasRecBb().getInstitucion().getInsIva().multiply(tramite.getTrvValor())).setScale(2, RoundingMode.DOWN);
                }
                getCajasRecBb().getListaFacturaDetalle().add(detalle);
                System.out.println("base impuestos" + detalle.getFdiBaseImponible());
//                addSuccessMessage("Tramite Agregado Correctamente");
                calcularValoresFactura();
            }

            contador++;
            contador1++;
        }
    }

    /**
     * <b>Metodo que verifica si un tramite ya esta agregado al detalle</b>
     *
     * @param tramite tramite
     *
     */
    public void verificarDetalle(Tramite tramite) {
        if (getCajasRecBb().getListaFacturaDetalle().size() > 1) {
            for (FacturaDetalle detalleFac : getCajasRecBb().getListaFacturaDetalle()) {
                System.out.println("tramite " + tramite.getTraNumero());
                System.out.println("tramite detalle " + detalleFac.getFdtTraNumero());
                if (tramite.getTraNumero() == (detalleFac.getFdtTraNumero())) {
                    getCajasRecBb().setEstado(Boolean.TRUE);
                    break;
                }

            }
        }
    }

    /**
     *
     */
    public void calcularValoresFactura() {
        BigDecimal subtotal12 = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        BigDecimal totalDescuento = BigDecimal.ZERO;
        for (FacturaDetalle detalleFac : getCajasRecBb().getListaFacturaDetalle()) {
            //subtotal12 = subtotal12.add(detalleFac.getFdtTotal()).setScale(2, RoundingMode.DOWN);
            subtotal12 = subtotal12.add(detalleFac.getFdtValor()).setScale(2, RoundingMode.DOWN);
            iva = iva.add((detalleFac.getFdiTarifa().multiply(detalleFac.getFdiBaseImponible())).divide(new BigDecimal(100)));
            //
            if(detalleFac.getFdtDescuento() != null){
                totalDescuento = totalDescuento.add(detalleFac.getFdtDescuento()).setScale(2, RoundingMode.DOWN);
            }
            
        }
        //BigDecimal iva = new BigDecimal(0.12);
        iva = iva.setScale(2, RoundingMode.DOWN);
        //subtotal de la factura
        getCajasRecBb().getFactura().setFacSubTotal(subtotal12);
        //iva de la factura
        getCajasRecBb().getFactura().setFacIva(iva);
        //descuento total de la fatura
        getCajasRecBb().getFactura().setFacDescuento(totalDescuento);
        //**Valores seteados esperando logica
        getCajasRecBb().getFactura().setFacBaseIva(BigDecimal.ZERO);
        

        //Total de la factura        
        getCajasRecBb().getFactura().setFacTotal((getCajasRecBb().getFactura().getFacSubTotal().add(getCajasRecBb().getFactura().getFacIva())).subtract(getCajasRecBb().getFactura().getFacDescuento()).setScale(2, RoundingMode.DOWN));
        //getCajasRecBb().getFactura().setFacTotal((getCajasRecBb().getFactura().getFacSubTotal().add(getCajasRecBb().getFactura().getFacIva())).setScale(2, RoundingMode.DOWN));
        //
        //Asignar a Pagos
        getCajasRecBb().setValorPago(getCajasRecBb().getFactura().getFacTotal());
        //Verificar valor 0 en factura
        int res = getCajasRecBb().getFactura().getFacTotal().compareTo(BigDecimal.ZERO);
        if (res == 0) {
            cargarSecuenciaNotaCredito();
            getCajasRecBb().setEsNotaVenta(Boolean.TRUE);
            getCajasRecBb().getFactura().setFacTipo("NVE");
        } else {
            getCajasRecBb().getFactura().setFacTipo("FAC");
        }

    }

    /**
     *
     */
    public void cabeceraFactura() {
        cargarSecuenciaFactura();
        //** caja
        if (getCajasRecBb().getObservacion() == null) {
            getCajasRecBb().setObservacion(" ");
        }
        getCajasRecBb().getFactura().setCajId(getCajasRecBb().getCaja());
        getCajasRecBb().getFactura().setFacNumero(getCajasRecBb().getNumeroComprobante());
        getCajasRecBb().getFactura().setPerId(getCajasRecBb().getPersona());
        getCajasRecBb().getFactura().setFacTraNumero(0);
        getCajasRecBb().getFactura().setFacPerTipoContribuyente(getCajasRecBb().getPersona().getPerTipoContribuyente());
        getCajasRecBb().getFactura().setFacPerTipoIdentificacion(getCajasRecBb().getPersona().getPerTipoIdentificacion());
        getCajasRecBb().getFactura().setFacPerIdentificacion(getCajasRecBb().getPersona().getPerIdentificacion());
        getCajasRecBb().getFactura().setFacPerNombre(getCajasRecBb().getPersona().getPerNombre());
        getCajasRecBb().getFactura().setFacPerApellidoPaterno(getCajasRecBb().getPersona().getPerApellidoPaterno());
        getCajasRecBb().getFactura().setFacCodigoVerificacion(codigoRandom());
        if (getCajasRecBb().getPersona().getPerApellidoMaterno() == null || getCajasRecBb().getPersona().getPerApellidoMaterno().equals("")) {
            getCajasRecBb().getFactura().setFacPerApellidoMaterno(" ");
        } else {
            getCajasRecBb().getFactura().setFacPerApellidoMaterno(getCajasRecBb().getPersona().getPerApellidoMaterno());
        }
        getCajasRecBb().getFactura().setFacPerTelefono(getCajasRecBb().getPersona().getPerCelular());
        getCajasRecBb().getFactura().setFacPerCelular(getCajasRecBb().getPersona().getPerCelular());
        getCajasRecBb().getFactura().setFacPerEmail(getCajasRecBb().getPersona().getPerEmail());
        getCajasRecBb().getFactura().setFacPerDireccion(getCajasRecBb().getPersona().getPerDireccion());
        //**numero derecho, cetificado,numero de escritura
        Short aux = 1;
        getCajasRecBb().getFactura().setFacNumeroDerecho(aux);
        getCajasRecBb().getFactura().setFacCertificado(aux);
        getCajasRecBb().getFactura().setFacObservacion(getCajasRecBb().getObservacion());
        if (getCajasRecBb().getFactura().getFacObservacion() == null) {
            getCajasRecBb().getFactura().setFacObservacion(" ");
        }
        Date auxFecha = getCajasRecBb().getTramite().getTraFechaEntrega().getTime();
        getCajasRecBb().getFactura().setFacFechaEntrega(auxFecha);
        getCajasRecBb().getFactura().setFacNumeroEscritura(aux);
        //** valores en 0.0
        getCajasRecBb().getFactura().setFacRegistrador(BigDecimal.ZERO);
        getCajasRecBb().getFactura().setFacAmanuence(BigDecimal.ZERO);
        getCajasRecBb().getFactura().setFacMaterial(BigDecimal.ZERO);
        getCajasRecBb().getFactura().setFacEstado("A");
        getCajasRecBb().getFactura().setFacUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
        getCajasRecBb().getFactura().setFacFHR(new Date());
        getCajasRecBb().getFactura().setFacFechaTramite(new Date());
        getCajasRecBb().getFactura().setFacFecha(Calendar.getInstance().getTime());

    }

    public void facturaDetalleAdicional() {
        //** CONSIDERAR VALORES DE PRUEBA
        getCajasRecBb().getFacturaDetalleAdicional().setFdaNombre("detalle adicional del contrato");
        getCajasRecBb().getFacturaDetalleAdicional().setFdaValor("HIPOTECA LIN 1");
        getCajasRecBb().getFacturaDetalleAdicional().setFdtId(getCajasRecBb().getFacturaDetalle());
    }

    public void facturaDetalleImpuesto(BigDecimal tarifa, BigDecimal base) {
        getCajasRecBb().getFacturaDetalleImpuesto().setFdiId(getCajasRecBb().getFacturaDetalle().getFdtId());
        getCajasRecBb().getFacturaDetalleImpuesto().setFdiCodigo("2");
        getCajasRecBb().getFacturaDetalleImpuesto().setFdiCodigoPorcentaje("2");
        getCajasRecBb().getFacturaDetalleImpuesto().setFdiTarifa(tarifa);
        getCajasRecBb().getFacturaDetalleImpuesto().setFdiBaseImponible(base);
    }

    public void facturaInfoAdicional() {
        //**
        getCajasRecBb().getFacturaInfoAdicional().setFacId(getCajasRecBb().getFactura());
        getCajasRecBb().getFacturaInfoAdicional().setFiaNombre("LINE 1");
        getCajasRecBb().getFacturaInfoAdicional().setFiaValor("FACTURACION INSCRIPCIONES");
    }

//    public void calcularFechaEntrega(Tramite tramite) {
//
//        int aux = this.getParametros().getPrmCantidadTiempo();
//        Calendar aux1 = tramite.getTraFechaEntrega();
//        if (this.getParametros().getPrmUnidadTiempo().equals("H")) {
//            aux1.add(Calendar.HOUR, aux);
//        } else if (this.getParametros().getPrmUnidadTiempo().equals("D")) {
//            aux1.add(Calendar.DAY_OF_YEAR, aux);
//        }
//        this.setFechaEntrega(aux1.getTime());// date
//        this.setFechaEntregaAux(aux1);// calendar
//    }
    public void actualizarDatosPersona() {
        try {
            if (getCajasRecBb().getPersona() != null && getCajasRecBb().getPersona().getPerId() != null) {
                personaServicio.edit(getCajasRecBb().getPersona());
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void guardarFactura() throws ServicioExcepcion {
        try {
            cabeceraFactura();
            // facturaDetalleAdicional();
            facturaInfoAdicional();
            actualizarDatosPersona();
            getCajasRecBb().setFactura(facturaServicio.guardarFactura(getCajasRecBb().getFactura()));
            getCajasRecBb().getFacturaDetalle().setFacId(getCajasRecBb().getFactura());
            //GUARDAR DETALLES FACTURA
            for (FacturaDetalle facturaDetalle : getCajasRecBb().getListaFacturaDetalle()) {
                getCajasRecBb().getFacturaDetalle().setFdtCantidad(BigDecimal.ONE);
                getCajasRecBb().getFacturaDetalle().setFdtDescuento(facturaDetalle.getFdtDescuento());

                getCajasRecBb().getFacturaDetalle().setFdtTotal(facturaDetalle.getFdtTotal());
                getCajasRecBb().getFacturaDetalle().setFdtTraNumero(facturaDetalle.getFdtTraNumero());
                getCajasRecBb().getFacturaDetalle().setFdtTtrDescripcion(facturaDetalle.getFdtTtrDescripcion());
                getCajasRecBb().getFacturaDetalle().setFdtTtrId(facturaDetalle.getFdtTtrId());
                getCajasRecBb().getFacturaDetalle().setFdtValor(facturaDetalle.getFdtValor());
                getCajasRecBb().getFacturaDetalle().setFdiCodigo(facturaDetalle.getFdiCodigo());
                getCajasRecBb().getFacturaDetalle().setFdiCodigoPorcentaje(facturaDetalle.getFdiCodigoPorcentaje());
                getCajasRecBb().getFacturaDetalle().setFdiTarifa(facturaDetalle.getFdiTarifa());
                getCajasRecBb().getFacturaDetalle().setFdiBaseImponible(facturaDetalle.getFdiBaseImponible());

                facturaDetalleServicio.guardarFactura(getCajasRecBb().getFacturaDetalle());

                Tramite tramite = new Tramite();
                tramite = tramiteValorServicio.buscarPorNumeroTramite(getCajasRecBb().getFacturaDetalle().getFdtTraNumero());
                if (tramite != null) {
                    if (tramite.getTraEstado().equals("CAJ")) {
                        if (tramite.getTraDescripcion().isEmpty()) {
                            tramite.setTraDescripcion(" ");
                        }
                        tramite.setTraEstado("REP");
                        getCajasRecBb().setTramite(tramite);
//FECHA DE ENTREGA
                        utilFechaEntrega.cargarParametros("CAJ");
                        utilFechaEntrega.calcularFechaEntregaTramite(tramite.getTraNumero());

                        tramite.setTraFechaEntrega(utilFechaEntrega.getFechaEntregaAux());

                        tramiteServicio.guardar(getCajasRecBb().getTramite());
                        if (!getCajasRecBb().getEsNotaVenta()) {
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Trámite Facturado " + getCajasRecBb().getFactura().getFacNumero(), "CAJ", "RA", null);
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Factura enviada al SRI", "CAJ", "RA", null);
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Trámite pasó a repertorio", "CAJ", "RA", null);
                        } else {
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Trámite Facturado Nota de Venta " + getCajasRecBb().getFactura().getFacNumero(), "CAJ", "RA", null);
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Trámite pasó a repertorio", "CAJ", "RA", null);
                        }
                    }
                }

            }
            //seteo del detalle guardado
            getCajasRecBb().getFacturaInfoAdicional().setFacId(getCajasRecBb().getFactura());
            facturaInfoAdicionalServicio.guardarFactura(getCajasRecBb().getFacturaInfoAdicional());
            for (FacturaFormaPago itemFormaPago : getCajasRecBb().getListapagos()) {

                FacturaFormaPago facturaFormaPago = new FacturaFormaPago();

                facturaFormaPago.setFacId(getCajasRecBb().getFactura());
                facturaFormaPago.setFfpDescripcion(itemFormaPago.getFfpDescripcion());
                facturaFormaPago.setFfpFHR(getCajasRecBb().getFactura().getFacFHR());
                facturaFormaPago.setTpfId(itemFormaPago.getTpfId());
                facturaFormaPago.setFfpUser(getCajasRecBb().getFactura().getFacUser());
                facturaFormaPago.setFfpValor(itemFormaPago.getFfpValor());

                facturaFormaPagoServicio.create(facturaFormaPago);
            }
            addSuccessMessage("Factura Guardada Correctamente");
//            actualizarSecuencia();
            secuenciaControlador.actualizarSecuenciaFac(dataManagerSesion.getUsuarioLogeado().getUsuId());

            try {
                getCajasRecBb().setFacturado(Boolean.TRUE);
                getCajasRecBb().setEstadoImprimir(Boolean.FALSE);
                RequestContext.getCurrentInstance().update("formCajas:btnImprimir");
                if (!getCajasRecBb().getEsNotaVenta()) {
                    //transmitirSRI();
                    //trasmitirSriThread
                }
            } catch (Exception ex) {
                Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            //inicializar();
        } catch (ServicioExcepcion e) {
            addErrorMessage("Error al Guardar Factura" + e);
        }
    }

    public void verificarDatos() throws ServicioExcepcion {
        Integer error = 0;

        if (getCajasRecBb().getIdentificacion() == null) {
            error = 1;
        } else {
            if (getCajasRecBb().getIdentificacion().equals("")) {
                error = 1;
            }
        }

//        if (getCajasRecBb().getPersona().getPerNombre() != null) {
//            if (!getCajasRecBb().getPersona().getPerNombre().matches("^\\s*[a-zA-Z\\ñÑÁÉÍÓÚáéíóú'´][a-zA-Z\\sñÑÁÉÍÓÚáéíóú'´]*$")) {
//                error = 2;
//            }
//        }
        if (getCajasRecBb().getPersona().getPerDireccion() == null) {
            error = 3;
        } else {
            if (getCajasRecBb().getPersona().getPerDireccion().equals("")) {
                error = 3;
            }
        }

        if (getCajasRecBb().getPersona().getPerCelular() == null) {
            error = 4;
        } else {
            if (getCajasRecBb().getPersona().getPerCelular().equals("")) {
                error = 4;
            }
        }

        if (getCajasRecBb().getPersona().getPerEmail() == null) {
            error = 5;
        } else {
            if (getCajasRecBb().getPersona().getPerEmail().equals("")) {
                error = 5;
            }
        }

        switch (error) {
            case 1:
                addErrorMessage("Ingrese la identificación");
                break;
//            case 2:
//                addErrorMessage("Ingrese solo letras en el nombre");
//                break;
            case 3:
                addErrorMessage("Ingrese la dirección");
                break;
            case 4:
                addErrorMessage("Ingrese el celular");
                break;
            case 5:
                addErrorMessage("Ingrese el email");
                break;
            default:
                getCajasRecBb().setConfigDetalleFactura(servicioDetalleConfig.obtenerConfigDetallePorDescripcion("MAXIMOFACTURA"));
                if (getCajasRecBb().getFactura().getFacTotal().compareTo(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero()) <= 0) {
                    guardarFactura();
                } else {

                    System.out.print("IMPRESION POR SEPARADO DE DOCUMENTOS");
                    listaFacturaGuardadas = new ArrayList();
                    int i = 0;
                    BigDecimal totalSumatoria = BigDecimal.ZERO;
                    BigDecimal facturatotal = getCajasRecBb().getFactura().getFacTotal();
                    BigDecimal maximo = getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero(); //el valor maximo
//                    int numFactDetaGuardado = 0;
                    for (FacturaDetalle facDetalle : getCajasRecBb().getListaFacturaDetalle()) {
                        System.out.print(facDetalle.getFdtTraNumero());
                        getCajasRecBb().setFacturaDetalleIndividual(facDetalle);
                        //creo los que tiene valor igual al limite
                        if (getCajasRecBb().getFacturaDetalleIndividual().getFdtValor().compareTo(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero()) == 0) {
                            BigDecimal residuo = BigDecimal.ZERO;
                            BigDecimal valorDetalleAntiguo = BigDecimal.ZERO;
                            BigDecimal cienporciento = new BigDecimal(0.12);
                            valorDetalleAntiguo = getCajasRecBb().getFacturaDetalleIndividual().getFdtValor();
                            residuo = getCajasRecBb().getFacturaDetalleIndividual().getFdtValor().subtract(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero());
                            residuo = residuo.add(residuo.multiply(cienporciento));
                            facturatotal = getCajasRecBb().getFactura().getFacTotal().subtract(residuo);
                            getCajasRecBb().getFacturaDetalleIndividual().setFdtValor(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero());
                            getCajasRecBb().getFacturaDetalleIndividual().setFdtTotal(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero());
                            getCajasRecBb().getFacturaDetalleIndividual().setFdiBaseImponible(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero());
                            guardarFacturaPorTramite(false, facturatotal, residuo, valorDetalleAntiguo);
//                            numFactDetaGuardado++;

                        } else if (getCajasRecBb().getFacturaDetalleIndividual().getFdtValor().compareTo(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero()) < 0) {//considero solo los que estan debajo del limite
                            ////guardo los que son menores al limite mediante sumatoria
//                                System.out.println("*** iteracion " + i);
//sumo hasta llegar o sobrepasar al limite, entonces guardo con el valor limite y asigno el restante para la siguiente iteracion
                            totalSumatoria = totalSumatoria.add(facDetalle.getFdtValor());
//                                System.out.println("comenzamos ");
//                                System.out.println("totalSumatoria: " + totalSumatoria.floatValue());
                            if (totalSumatoria.compareTo(maximo) >= 0) {
                                BigDecimal residuo = BigDecimal.ZERO;
                                BigDecimal valorDetalleAntiguo = BigDecimal.ZERO;
                                getCajasRecBb().getFacturaDetalleIndividual().setFdtValor(maximo);

                                guardarFacturaPorTramite(false, facturatotal, residuo, valorDetalleAntiguo);
//                                numFactDetaGuardado++;

                                totalSumatoria = totalSumatoria.subtract(maximo);
//                                    System.out.println("es mayor o igual al max, facturo");
//                                    System.out.println("totalSumatoria: " + totalSumatoria.floatValue());
                            }
                            //si es la ultima iteracion, guardo con el valor restante.
                            if (i == getCajasRecBb().getListaFacturaDetalle().size() - 1)//si es el ultimo item
                            {
                                BigDecimal residuo = BigDecimal.ZERO;
                                BigDecimal valorDetalleAntiguo = BigDecimal.ZERO;
                                getCajasRecBb().getFacturaDetalleIndividual().setFdtValor(totalSumatoria);
                                getCajasRecBb().getFacturaDetalleIndividual().setFdtTotal(totalSumatoria);
                                guardarFacturaPorTramite(true, facturatotal, residuo, valorDetalleAntiguo);
//                                numFactDetaGuardado++;

//                                    System.out.println("facturo el restante");
//                                    System.out.println("totalSumatoria: " + totalSumatoria.floatValue());
                            }
                        }
                        i++;
                    }
                    addSuccessMessage("Factura Guardada Correctamente");

                    guardarFormaPago();

                }
                break;
        }

        ponerTramitesValorComoFacturado();
    }

    public void ponerTramitesValorComoFacturado() {
        for (TramiteValor itemTramVal : getCajasRecBb().getListaTramiteValor()) {
            itemTramVal.setTrvFacturado("F");
            tramiteValorServicio.edit(itemTramVal);
        }
    }

    public void guardarFormaPago() {
//        BigDecimal facturatotal = getCajasRecBb().getFactura().getFacTotal();
        BigDecimal nuevoFfpValor = BigDecimal.ZERO;
        int numFactura = listaFacturaGuardadas.size();

        for (FacturaFormaPago itemFormaPago : getCajasRecBb().getListapagos()) {
            int i = 0;
            for (Factura itemFactura : listaFacturaGuardadas) {

//            for (int i = 0; i < numFactura; i++) {
                nuevoFfpValor = BigDecimal.ZERO;
                FacturaFormaPago facturaFormaPago = new FacturaFormaPago();
                nuevoFfpValor = itemFormaPago.getFfpValor().divide(new BigDecimal(numFactura), 2, RoundingMode.HALF_UP);
                System.out.println("nuevoFfpValor: " + nuevoFfpValor);
                facturaFormaPago.setFacId(itemFactura);
                facturaFormaPago.setFfpDescripcion(itemFormaPago.getFfpDescripcion());
//                facturaFormaPago.setFfpFHR(getCajasRecBb().getFactura().getFacFHR());
                facturaFormaPago.setFfpFHR(itemFactura.getFacFHR());
                facturaFormaPago.setTpfId(itemFormaPago.getTpfId());
                facturaFormaPago.setFfpUser(itemFactura.getFacUser());
                facturaFormaPago.setFfpValor(nuevoFfpValor);

                if (i == numFactura - 1) {
                    nuevoFfpValor = itemFormaPago.getFfpValor().subtract((nuevoFfpValor.multiply(new BigDecimal(numFactura - 1))));
                    facturaFormaPago.setFfpValor(nuevoFfpValor);
                }
                facturaFormaPagoServicio.create(facturaFormaPago);
                i++;
            }

        }
    }

    public void reload() throws ServicioExcepcion {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarSecuencia() throws ServicioExcepcion {
        int auxSecuencia = getSecuenciaControlador().getControladorBb().getSecuencia().getSecActual();
        getSecuenciaControlador().getControladorBb().getSecuencia().setSecActual(auxSecuencia + 1);
        secuenciaServicio.guardar(getSecuenciaControlador().getControladorBb().getSecuencia());
        //reload();
    }

    public void transmitirSRI() throws Exception {
//        controladorModelosSRI.asignarModeloSri(getCajasRecBb().getFactura(), getCajasRecBb().getInstitucion(), getCajasRecBb().getListaFacturaDetalle());
    }

    public void validarCedula() {
        if (validadorDeCedula(getCajasRecBb().getNuevaPersona().getPerIdentificacion())) {
            addSuccessMessage("La Cédula Ingresada es Correcta");
        } else {
            addErrorMessage("La Cédula Ingresada es Incorrecta");
        }

    }

    public void cargarPago() {
        getCajasRecBb().setIdPago("1");
        try {
            getCajasRecBb().setSelectPago(tipoFormaPagoServicio.listaTipoFormapago());
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generarNV() {
        try {
            String numeroFactura = getCajasRecBb().getNumeroComprobante();
            BigDecimal total = getCajasRecBb().getFactura().getFacTotal();
            String pathImagen = institucionServicio.encontrarInstitucionPorId("1").getInsLogo();
            String nomApePer = getCajasRecBb().getFactura().getFacPerApellidoPaterno().trim()
                    + " " + getCajasRecBb().getFactura().getFacPerApellidoMaterno().trim()
                    + " " + getCajasRecBb().getFactura().getFacPerNombre().trim();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numeroFactura", numeroFactura);
            parametros.put("nomApePer", nomApePer);
            parametros.put("pathImagen", pathImagen);
            parametros.put("codigoVerif", getCajasRecBb().getFactura().getFacCodigoVerificacion());
            parametros.put("valorNum", total);
            parametros.put("valorAlf", TransformadorNumerosLetrasUtil.transformador(total.toString()).toString());
            List<FacturaDetalle> listFactDetalle = new ArrayList<>();
            List<String> listContrato = new ArrayList<String>();
            
            listFactDetalle = facturaDetalleServicio.listarPorNumeroFactura(numeroFactura);            
             for (FacturaDetalle facturaDetalle : listFactDetalle) {
                if(!listContrato.contains(facturaDetalle.getFdtTtrDescripcion())){
                    listContrato.add(facturaDetalle.getFdtTtrDescripcion());
                }                
            }
            int aux = 0;
            String simbolo = "";
            String detalleContratos = "";
            for (String contrato : listContrato) {
                aux ++;
                if(aux < listContrato.size()){
                    simbolo = ", ";
                }
                else{
                    simbolo = ".";
                }
                detalleContratos = detalleContratos + contrato + simbolo;
            }
            
            parametros.put("detalleContratos", detalleContratos);

            reporteUtil.imprimirReporteEnPDF("Factura", "Comprobante", parametros);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Comprobante Generado"));
        } catch (Exception e) {
            System.err.println(e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al generar NV");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    private void pago() throws ServicioExcepcion {
        BigDecimal totalPago = BigDecimal.ZERO;
        if (getCajasRecBb().getValorPago().compareTo(getCajasRecBb().getSaldoPago()) == -1 || getCajasRecBb().getValorPago().compareTo(getCajasRecBb().getSaldoPago()) == 0) {
            TipoFormaPago tipoFormaPago = tipoFormaPagoServicio.buscarFormaPagoPorId(getCajasRecBb().getIdPago());
            FacturaFormaPago facturaFormaPago = new FacturaFormaPago(null, getCajasRecBb().getValorPago(), tipoFormaPago.getTpfDescripcion(),
                    dataManagerSesion.getUsuarioLogeado().getUsuLogin(), new Date(), tipoFormaPago, null);
            boolean comp = true;
            if (!getCajasRecBb().getListapagos().isEmpty()) {
                for (int i = 0; i < getCajasRecBb().getListapagos().size(); i++) {
                    if (getCajasRecBb().getListapagos().get(i).getTpfId().equals(tipoFormaPago)) {
                        comp = false;
                        break;
                    } else {
                        comp = true;
                    }
                }
            }
            if (comp) {
                getCajasRecBb().getListapagos().add(facturaFormaPago);
                System.out.println("forma de pago: " + facturaFormaPago.getFfpDescripcion() + "; valor: " + facturaFormaPago.getFfpValor());
                for (FacturaFormaPago formaPago : getCajasRecBb().getListapagos()) {
                    totalPago = totalPago.add(formaPago.getFfpValor());
                }
                if (getCajasRecBb().getFactura().getFacTotal().compareTo(totalPago) == 0) {
                    getCajasRecBb().setSaldoPago(BigDecimal.ZERO);
                    getCajasRecBb().setValorPago(getCajasRecBb().getSaldoPago());
                    getCajasRecBb().setBtnPago(Boolean.FALSE);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Pago Completo", ""));
                } else if (getCajasRecBb().getFactura().getFacTotal().compareTo(totalPago) == 1) {
                    if (getCajasRecBb().getSaldoPago().compareTo(getCajasRecBb().getValorPago()) == 1) {
                        getCajasRecBb().setTotalPago(totalPago);
                        getCajasRecBb().setSaldoPago(getCajasRecBb().getSaldoPago().subtract(getCajasRecBb().getValorPago()));
                        getCajasRecBb().setValorPago(getCajasRecBb().getSaldoPago());
                    }
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El tipo de pago que intenta agregar ya está en la lista", ""));
            }
        } else {
            getCajasRecBb().setBtnPago(Boolean.TRUE);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El valor de pago ingresado es mayor al saldo", ""));
        }
    }

    public void verificarPago() throws ServicioExcepcion {
        if (getCajasRecBb().getFactura().getFacTotal().compareTo(BigDecimal.ZERO) == 0) {
            if (getCajasRecBb().getListapagos().size() != 1) {
                if (getCajasRecBb().getValorPago().compareTo(BigDecimal.ZERO) == 0) {
                    pago();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "No puede ingresar valores mayores o menores de 0", ""));
                }
            } else if (getCajasRecBb().getListapagos().size() == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya se ha agregado un tipo de pago a la lista", ""));
            }
        } else {
            if (getCajasRecBb().getSaldoPago().compareTo(BigDecimal.ZERO) != 0) {
                if (getCajasRecBb().getValorPago().compareTo(BigDecimal.ZERO) == 1) {
                    pago();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingresar un valor mayor a 0", ""));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El saldo ya está en 0", ""));
            }
        }
    }

    public void actualizarValorPago() {
        getCajasRecBb().setAuxValorPago(getCajasRecBb().getFactura().getFacTotal());
        getCajasRecBb().setAuxSaldoPago(getCajasRecBb().getFactura().getFacTotal());
        if (!getCajasRecBb().getListapagos().isEmpty()) {
            BigDecimal totalPago = BigDecimal.ZERO;
            for (FacturaFormaPago formaPago : getCajasRecBb().getListapagos()) {
                totalPago = totalPago.add(formaPago.getFfpValor());
            }
            getCajasRecBb().setValorPago(getCajasRecBb().getAuxValorPago().subtract(totalPago));
            getCajasRecBb().setSaldoPago(getCajasRecBb().getAuxSaldoPago().subtract(totalPago));
        } else {
            getCajasRecBb().setValorPago(getCajasRecBb().getAuxValorPago());
            getCajasRecBb().setSaldoPago(getCajasRecBb().getAuxSaldoPago());
        }
    }

    private void trasmitirSriThread() {
        Thread hilo = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    transmitirSRI();
                } catch (Exception ex) {
                    Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };

        StringBuilder sb = new StringBuilder();
        sb.append("DEMON_").append(1 / 1000).append("_S").append("_").append(getCajasRecBb().getFactura().getFacNumero());

        hilo.setName(sb.toString());
        hilo.start();
    }

    public void cargarSecuenciaNotaCredito() {
        try {
            short puntoEmision = 0;
            if (dataManagerSesion.getUsuarioLogeado().getUsuLogin() != null) {
                secuenciaControlador.generarSecuencia("NVE");
                for (Serie serie : getCajasRecBb().getCaja().getSucId().getSerieList()) {
                    if (serie.getSerSecuenciaFactura() > 0) {
                        puntoEmision = serie.getSerPuntoEmision();
                    }
                }

                String secuencia = Integer.toString(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                getCajasRecBb().setNumeroFactura(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                getCajasRecBb().setNumeroComprobante(getCajasRecBb().getInstitucion().getInsEstablecimiento() + "-" + puntoEmision + "-" + secuencia);
                RequestContext.getCurrentInstance().update("formCajas:txtNumFactura");
            }
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void elminarFila() throws ServicioExcepcion {
        for (int i = 0; i < getCajasRecBb().getListapagos().size(); i++) {
            if (getCajasRecBb().getPagoSeleccionado() == getCajasRecBb().getListapagos().get(i)) {
                getCajasRecBb().getListapagos().remove(i);
                getCajasRecBb().setSaldoPago(getCajasRecBb().getSaldoPago().add(getCajasRecBb().getPagoSeleccionado().getFfpValor()));
                getCajasRecBb().setValorPago(getCajasRecBb().getSaldoPago());
                getCajasRecBb().setBtnPago(Boolean.TRUE);
            }
        }
    }

    private String codigoRandom() {
        int codigo = 0;
        String codigoFinal = "";
        while (codigoFinal.length() != 4) {
            if (codigo == 0) {
                codigo = (int) (Math.random() * 10) + 1;
                codigoFinal = String.valueOf(codigo);
            } else {
                codigo = (int) (Math.random() * 10) + 1;
                codigoFinal += String.valueOf(codigo);
            }
        }
        return codigoFinal;
    }

    public void guardarFacturaPorTramite(boolean ultimo, BigDecimal facturatotal, BigDecimal residuo, BigDecimal valorDetalleAntiguo) {
        try {
            cabeceraFactura();
            // facturaDetalleAdicional();
            facturaInfoAdicional();

            System.out.println("valor factura detalle: " + getCajasRecBb().getFacturaDetalleIndividual().getFdtValor());
            System.out.println("total factura detalle: " + getCajasRecBb().getFacturaDetalleIndividual().getFdtTotal());
            //nuevo calculo de iva de solo el tramite
            BigDecimal total = BigDecimal.ZERO;
            BigDecimal iva = BigDecimal.ZERO;
            iva = iva.add((getCajasRecBb().getFacturaDetalleIndividual().getFdiTarifa().multiply(getCajasRecBb().getFacturaDetalleIndividual().getFdiBaseImponible())).divide(new BigDecimal(100)));
            //BigDecimal iva = new BigDecimal(0.12);
            iva = iva.setScale(2, RoundingMode.DOWN);
            total = total.add(getCajasRecBb().getFacturaDetalleIndividual().getFdtTotal());
            total = total.add(iva);

            //
            getCajasRecBb().getFactura().setFacSubTotal(getCajasRecBb().getFacturaDetalleIndividual().getFdtTotal());
            getCajasRecBb().getFactura().setFacIva(iva);
            getCajasRecBb().getFactura().setFacTotal(total);//valor del detalle mas iva
            getCajasRecBb().setFactura(facturaServicio.guardarFactura(getCajasRecBb().getFactura()));
            getCajasRecBb().getFacturaDetalle().setFacId(getCajasRecBb().getFactura());

            listaFacturaGuardadas.add(servicioFactura.find(new Factura(), getCajasRecBb().getFactura().getFacId()));

            //GUARDAR DETALLE FACTURA
            getCajasRecBb().getFacturaDetalle().setFdtCantidad(BigDecimal.ONE);
            getCajasRecBb().getFacturaDetalle().setFdtDescuento(BigDecimal.ZERO);

            getCajasRecBb().getFacturaDetalle().setFdtTotal(getCajasRecBb().getFacturaDetalleIndividual().getFdtTotal());
            getCajasRecBb().getFacturaDetalle().setFdtTraNumero(getCajasRecBb().getFacturaDetalleIndividual().getFdtTraNumero());
            getCajasRecBb().getFacturaDetalle().setFdtTtrDescripcion(getCajasRecBb().getFacturaDetalleIndividual().getFdtTtrDescripcion());
            getCajasRecBb().getFacturaDetalle().setFdtTtrId(getCajasRecBb().getFacturaDetalleIndividual().getFdtTtrId());
            getCajasRecBb().getFacturaDetalle().setFdtValor(getCajasRecBb().getFacturaDetalleIndividual().getFdtValor());
            getCajasRecBb().getFacturaDetalle().setFdiCodigo(getCajasRecBb().getFacturaDetalleIndividual().getFdiCodigo());
            getCajasRecBb().getFacturaDetalle().setFdiCodigoPorcentaje(getCajasRecBb().getFacturaDetalleIndividual().getFdiCodigoPorcentaje());
            getCajasRecBb().getFacturaDetalle().setFdiTarifa(getCajasRecBb().getFacturaDetalleIndividual().getFdiTarifa());
            getCajasRecBb().getFacturaDetalle().setFdiBaseImponible(getCajasRecBb().getFacturaDetalleIndividual().getFdiBaseImponible());

            facturaDetalleServicio.guardarFactura(getCajasRecBb().getFacturaDetalle());

            if (ultimo) {
                Tramite tramite = new Tramite();
                tramite = tramiteValorServicio.buscarPorNumeroTramite(getCajasRecBb().getFacturaDetalle().getFdtTraNumero());
                if (tramite != null) {
                    if (tramite.getTraEstado().equals("CAJ")) {
                        if (tramite.getTraDescripcion().isEmpty()) {
                            tramite.setTraDescripcion(" ");
                        }
                        tramite.setTraEstado("REP");
                        getCajasRecBb().setTramite(tramite);

                        utilFechaEntrega.cargarParametros("CAJ");
                        utilFechaEntrega.calcularFechaEntregaTramite(tramite.getTraNumero());

                        tramite.setTraFechaEntrega(utilFechaEntrega.getFechaEntregaAux());

                        tramiteServicio.guardar(getCajasRecBb().getTramite());

                        if (!getCajasRecBb().getEsNotaVenta()) {
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Trámite Facturado " + getCajasRecBb().getFactura().getFacNumero(), "CAJ", "RA", null);
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Factura enviada al SRI", "CAJ", "RA", null);
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Trámite pasó a repertorio", "CAJ", "RA", null);
                        } else {
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Trámite Facturado Nota de Venta " + getCajasRecBb().getFactura().getFacNumero(), "CAJ", "RA", null);
                            tramiteUtil.insertarTramiteAccion(tramite, getCajasRecBb().getFactura().getFacNumero(), "Trámite pasó a repertorio", "CAJ", "RA", null);
                        }
                    }
                }
            }

            //seteo del detalle guardado
            getCajasRecBb().getFacturaInfoAdicional().setFacId(getCajasRecBb().getFactura());
            facturaInfoAdicionalServicio.guardarFactura(getCajasRecBb().getFacturaInfoAdicional());

//            BigDecimal porcentajeEquivalente = BigDecimal.ZERO;
//            BigDecimal porcentajeValorDetalle = BigDecimal.ZERO;
//            BigDecimal nuevoFfpValor = BigDecimal.ZERO;
//            BigDecimal cienporciento = new BigDecimal(100.00);
//            porcentajeEquivalente = porcentajeEquivalente.add(getCajasRecBb().getFactura().getFacTotal().multiply(cienporciento));
//            porcentajeEquivalente = porcentajeEquivalente.divide(facturatotal, 2, RoundingMode.DOWN);
//            porcentajeEquivalente = porcentajeEquivalente.divide(cienporciento);
//
//            if (valorDetalleAntiguo.compareTo(BigDecimal.ZERO) == 0) {
//                valorDetalleAntiguo = new BigDecimal(1.00);
//                porcentajeValorDetalle = new BigDecimal(1.00);
//            } else {
//                porcentajeValorDetalle = getCajasRecBb().getFacturaDetalleIndividual().getFdtTotal().multiply(cienporciento);
//                porcentajeValorDetalle = porcentajeValorDetalle.divide(valorDetalleAntiguo, 2, RoundingMode.DOWN);
//                porcentajeValorDetalle = porcentajeValorDetalle.divide(cienporciento);
//            }
//            System.out.print("porcentaje=" + porcentajeEquivalente);
////            if (ultimo) {
//                for (FacturaFormaPago itemFormaPago : getCajasRecBb().getListapagos()) {
//
//                    FacturaFormaPago facturaFormaPago = new FacturaFormaPago();
//
//                    nuevoFfpValor = nuevoFfpValor.add(porcentajeEquivalente.multiply(itemFormaPago.getFfpValor().multiply(porcentajeValorDetalle)));
//                    System.out.println("nuevoFfpValor: "+nuevoFfpValor);
//                    facturaFormaPago.setFacId(getCajasRecBb().getFactura());
//                    facturaFormaPago.setFfpDescripcion(itemFormaPago.getFfpDescripcion());
//                    facturaFormaPago.setFfpFHR(getCajasRecBb().getFactura().getFacFHR());
//                    facturaFormaPago.setTpfId(itemFormaPago.getTpfId());
//                    facturaFormaPago.setFfpUser(getCajasRecBb().getFactura().getFacUser());
//                    facturaFormaPago.setFfpValor(nuevoFfpValor);
//                    System.out.println("forma de pago creada");
//
//                    facturaFormaPagoServicio.create(facturaFormaPago);
//                }
////            }
//            actualizarSecuencia();
            secuenciaControlador.actualizarSecuenciaFac(dataManagerSesion.getUsuarioLogeado().getUsuId());
            try {
                getCajasRecBb().setFacturado(Boolean.TRUE);
                getCajasRecBb().setEstadoImprimir(Boolean.FALSE);
                RequestContext.getCurrentInstance().update("formCajas:btnImprimir");
                if (!getCajasRecBb().getEsNotaVenta()) {
                    //transmitirSRI();
                    //trasmitirSriThread
                }
            } catch (Exception ex) {
                Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            //inicializar();
            facturaServicio.getEntityManager().clear();
        } catch (ServicioExcepcion e) {
            System.out.println(e);
            addErrorMessage("Error al Guardar Factura" + e);
        }

    }

}
//
