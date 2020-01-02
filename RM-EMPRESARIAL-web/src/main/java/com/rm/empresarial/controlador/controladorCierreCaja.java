/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import static com.ibm.icu.util.LocalePriorityList.add;
import com.rm.empresarial.bb.TramitesControladorBb;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.dao.CajaDao;
import com.rm.empresarial.dao.FacturaDao;
import com.rm.empresarial.dao.FacturaDetalleDao;
import com.rm.empresarial.dao.FacturaFormaDePagoDao;
import com.rm.empresarial.dao.ImpresionDao;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Caja;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FacturaFormaPago;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.servicio.CuantiaServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

@Named("controladorCierreCaja")
@SessionScoped
public class controladorCierreCaja implements Serializable {

    @EJB
    private ImpresionDao impresionDao;
    @EJB
    private CajaDao cajaDao;

    @EJB
    private FacturaFormaDePagoDao formaPagoDao;
    @EJB
    private FacturaDao facturaDao;

    @EJB
    private InstitucionDao servicioInstitucionDao;

    @Getter
    @Setter
    private List<Factura> listaValidar = new ArrayList<>();

    @Getter
    @Setter
    private List<Factura> listaFactura = new ArrayList<>();

    @Getter
    @Setter
    private List<Factura> listaFacturaMOSTRAR = new ArrayList<>();

    @Getter
    @Setter
    private List<Factura> listaFacturaNVE = new ArrayList<>();

    @Getter
    @Setter
    private List<Factura> listaFacturaNVEMOSTRAR = new ArrayList<>();

    @Getter
    @Setter
    private List<FacturaFormaPago> listaFormaPago = new ArrayList<>();

    @Getter
    @Setter
    private FacturaDetalle facturaDetalle;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Getter
    @Setter
    double descuento;
    @Getter
    @Setter
    double iva;
    @Getter
    @Setter
    double valor;
    @Getter
    @Setter
    double valorSinU;
    @Getter
    @Setter
    String sinUsar = "SIN UTILIZACIÓN DEL SISTEMA FINANCIERO";
    @Getter
    @Setter
    double valorSinU2;
    @Getter
    @Setter
    double compDeu;
    @Getter
    @Setter
    double tDebito, tPrepago, tCredito, sisFin, endoso, test;
    @Getter
    @Setter
    double dElectronico;
    @Getter
    @Setter
    double total;
    @Getter
    @Setter
    double subtotalFactura;
    @Getter
    @Setter
    double descuentoNVE;
    @Getter
    @Setter
    double ivaNVE;
    @Getter
    @Setter
    double totalNVE;
    @Getter
    @Setter
    double subtotalFacturaNVE;
    @Getter
    @Setter
    String cajNombre;
    @Getter
    @Setter
    String estado;

    @Getter
    @Setter
    private List<Caja> listaCaja = new ArrayList<>();

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private Factura factura;

    @Getter
    @Setter
    private boolean facturaTipo;

    @Getter
    @Setter
    private String pathImagen;

    @Getter
    @Setter
    private Date fecha;

    @Inject
    DataManagerSesion dataManagerSesion;

    @Inject
    ReporteUtil reporteUtil;

    @EJB
    FacturaDetalleDao facturaDetalleDao;

    @EJB
    TipoTramiteDao tipoTramiteDao;

    @EJB
    TramiteServicio tramiteServicio;
    @EJB
    CuantiaServicio servicioCuantia;
    @Getter
    @Setter
    private BigDecimal porcentajeCuantia;

    public controladorCierreCaja() throws ServicioExcepcion {

        listaFactura = new ArrayList<>();
        listaFacturaMOSTRAR = new ArrayList<>();

    }

    @PostConstruct

    public void post() {
        try {
            selecMenuCaja();
            porcentajeCuantia = servicioCuantia.ListarTodos().get(0).getCuaPorcentaje().divide(new BigDecimal(100));

        } catch (ServicioExcepcion ex) {
            Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void listarFacturaFecha(Date fecha, String estado) throws ServicioExcepcion, ParseException {
        listaFacturaMOSTRAR = new ArrayList<>();
        setValor(0);
        setSubtotalFactura(0);
        setDescuento(0);
        setIva(0);
        setTotal(0);
        setCompDeu(0);
        setDElectronico(0);
        setEndoso(0);
        setSisFin(0);
        setTCredito(0);
        setTDebito(0);
        setTPrepago(0);
        setTest(0);
        setValorSinU(0);
        setListaFormaPago(null);
        if (getEstado().equals("A")) {
            setListaFactura(facturaDao.listarPorFechaAbierta(fecha, dataManagerSesion.getUsuarioLogeado().getUsuLogin(), estado));
            for (Factura factura : listaFactura) {
                setFacturaDetalle(facturaDetalleDao.PorNumeroFactura(factura.getFacNumero()));
                setFacturaTipo(tipoTramiteDao.buscarPorDescripcionTRUE(getFacturaDetalle().getFdtTtrDescripcion()));

                if (facturaTipo == true) {
                    factura.setFacTipo("INSCRIPCIÓN");
                } else {
                    factura.setFacTipo("CERTIFICADO");
                }
                listaFacturaMOSTRAR.add(factura);

            }
        } else {
            setListaFactura(facturaDao.listarPorFecha(fecha, dataManagerSesion.getUsuarioLogeado().getUsuLogin(), estado));
            for (Factura factura : listaFactura) {
                setFacturaDetalle(facturaDetalleDao.PorNumeroFactura(factura.getFacNumero()));
                setFacturaTipo(tipoTramiteDao.buscarPorDescripcionTRUE(getFacturaDetalle().getFdtTtrDescripcion()));

                if (facturaTipo == true) {
                    factura.setFacTipo("INSCRIPCION");
                } else {
                    factura.setFacTipo("CERTIFICADO");
                }
                listaFacturaMOSTRAR.add(factura);

            }
        }
        listarFormasPago();
        suma(fecha, estado);

    }

    public void listarFacturaNVE(Date fecha, String estado) throws ServicioExcepcion, ParseException {
        listaFacturaNVEMOSTRAR = new ArrayList<>();
        setSubtotalFacturaNVE(0);
        setDescuentoNVE(0);
        setIvaNVE(0);
        setTotalNVE(0);
        setListaFacturaNVE(null);
        if (getEstado().equals("A")) {
            setListaFacturaNVE(facturaDao.listarPorNVEAbierta(fecha, dataManagerSesion.getUsuarioLogeado().getUsuLogin(), estado));
            for (Factura factura : listaFacturaNVE) {
                setFacturaDetalle(facturaDetalleDao.PorNumeroFactura(factura.getFacNumero()));
                setFacturaTipo(tipoTramiteDao.buscarPorDescripcionTRUE(getFacturaDetalle().getFdtTtrDescripcion()));
                if (facturaTipo == true) {
                    factura.setFacTipo("INSCRIPCION");
                } else {
                    factura.setFacTipo("CERTIFICADO");
                }
                listaFacturaNVEMOSTRAR.add(factura);

            }
        } else {
            setListaFacturaNVE(facturaDao.listarPorNVECerrada(fecha, dataManagerSesion.getUsuarioLogeado().getUsuLogin(), estado));
            for (Factura factura : listaFacturaNVE) {
                setFacturaDetalle(facturaDetalleDao.PorNumeroFactura(factura.getFacNumero()));
                setFacturaTipo(tipoTramiteDao.buscarPorDescripcionTRUE(getFacturaDetalle().getFdtTtrDescripcion()));

                if (facturaTipo == true) {
                    factura.setFacTipo("INSCRIPCION");
                } else {
                    factura.setFacTipo("CERTIFICADO");
                }
                listaFacturaNVEMOSTRAR.add(factura);

            }
        }
        sumarTotaleNVE();

    }

    public void cerrarCajaNVE(Date fecha, String cajNombre, String estado) throws ServicioExcepcion {
        listaValidar = facturaDao.listarValidarNVE(fecha, cajNombre, estado);
        if (listaValidar.isEmpty()) {
            if (listaFacturaNVE != null) {
                for (Factura factura : listaFacturaNVE) {
                    factura.setFacEstadoCierre("C");
                    facturaDao.edit(factura);
                    setListaFacturaNVE(null);
                    setSubtotalFactura(0);
                    setDescuentoNVE(0);
                    setIvaNVE(0);
                    setTotalNVE(0);

                }
                addSuccessMessage("Se cerro Caja Correctamente");
            } else {
                addErrorMessage("Error No Hay Facturas");
            }
        } else {
            addErrorMessage("Tiene cajas pendientes");
        }

    }

    public void cerrarCaja(Date fecha, String cajNombre, String estado) throws ServicioExcepcion {
        listaValidar = facturaDao.listarValidar(fecha, cajNombre, estado);
        if (listaValidar.isEmpty()) {
            if (listaFactura != null) {
                for (Factura factura : listaFactura) {
                    factura.setFacEstadoCierre("C");
                    facturaDao.edit(factura);
                    setListaFactura(null);
                    setListaFormaPago(null);
                    setValor(0);
                    setSubtotalFactura(0);
                    setDescuento(0);
                    setIva(0);
                    setTotal(0);
                    setCompDeu(0);
                    setDElectronico(0);
                    setEndoso(0);
                    setSisFin(0);
                    setTCredito(0);
                    setTDebito(0);
                    setTPrepago(0);
                    setTest(0);
                    setValorSinU(0);
                }
                addSuccessMessage("Se cerro Caja Correctamente");
            } else {
                addErrorMessage("Error No Hay Facturas");
            }
        } else {
            addErrorMessage("Tiene cajas pendientes");
        }

    }

    public void limpiar() throws ServicioExcepcion {
        setListaFactura(null);
        setListaFormaPago(null);
        setValor(0);
        setIva(0);
        setSubtotalFactura(0);
        setTotal(0);
        setDescuento(0);
        setCompDeu(0);
        setDElectronico(0);
        setEndoso(0);
        setSisFin(0);
        setTCredito(0);
        setTDebito(0);
        setTPrepago(0);
        setTest(0);
        setValorSinU(0);

    }

    public void listarFormasPago() throws ServicioExcepcion, ParseException {
        for (Factura factura : listaFactura) {

            subtotalFactura = subtotalFactura + factura.getFacSubTotal().doubleValue();
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("#.##", simbolos);
            subtotalFactura = Double.parseDouble(df.format(subtotalFactura));
            descuento = descuento + factura.getFacDescuento().doubleValue();
            descuento = Double.parseDouble(df.format(descuento));
            iva = iva + factura.getFacIva().doubleValue();
            iva = Double.parseDouble(df.format(iva));
            total = total + factura.getFacTotal().doubleValue();
            total = Double.parseDouble(df.format(total));
        }

    }

    public void sumarTotaleNVE() throws ServicioExcepcion, ParseException {
        for (Factura factura : listaFacturaNVE) {

            subtotalFacturaNVE = subtotalFacturaNVE + factura.getFacSubTotal().doubleValue();
            DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
            simbolos.setDecimalSeparator('.');
            DecimalFormat df = new DecimalFormat("#.##", simbolos);
            subtotalFacturaNVE = Double.parseDouble(df.format(subtotalFacturaNVE));
            descuentoNVE = descuentoNVE + factura.getFacDescuento().doubleValue();
            descuentoNVE = Double.parseDouble(df.format(descuentoNVE));
            ivaNVE = ivaNVE + factura.getFacIva().doubleValue();
            ivaNVE = Double.parseDouble(df.format(ivaNVE));
            totalNVE = totalNVE + factura.getFacTotal().doubleValue();
            totalNVE = Double.parseDouble(df.format(totalNVE));
        }

    }

    public void suma(Date fecha, String estado) throws ServicioExcepcion {
        if (listaFactura.isEmpty()) {
            setListaFormaPago(null);
        } else {
            setListaFormaPago(formaPagoDao.listarPorFecha(fecha, estado));
            for (FacturaFormaPago formas : listaFormaPago) {
                valor = valor + formas.getFfpValor().doubleValue();
                DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
                simbolos.setDecimalSeparator('.');
                DecimalFormat df = new DecimalFormat("#.##", simbolos);
                valor = Double.parseDouble(df.format(valor));

                if (formas.getTpfId().getTpfDescripcion().equals("SIN UTILIZACIÓN DEL SISTEMA FINANCIERO")) {

                    valorSinU = Double.parseDouble(df.format(valorSinU + formas.getFfpValor().doubleValue()));

                }
                if (formas.getTpfId().getTpfDescripcion().equals("COMPENSACIÓN DE DEUDAS")) {
                    compDeu = Double.parseDouble(df.format(compDeu + formas.getFfpValor().doubleValue()));
                }
                if (formas.getTpfId().getTpfDescripcion().equals("TARJETA DE DÉBITO")) {
                    tDebito = Double.parseDouble(df.format(tDebito + formas.getFfpValor().doubleValue()));
                }
                if (formas.getTpfId().getTpfDescripcion().equals("DINERO ELECTRÓNICO")) {
                    dElectronico = Double.parseDouble(df.format(dElectronico + formas.getFfpValor().doubleValue()));
                }
                if (formas.getTpfId().getTpfDescripcion().equals("TARJETA PREPAGO")) {
                    tPrepago = Double.parseDouble(df.format(tPrepago + formas.getFfpValor().doubleValue()));
                }
                if (formas.getTpfId().getTpfDescripcion().equals("TARJETA DE CRÉDITO")) {
                    tCredito = Double.parseDouble(df.format(tCredito + formas.getFfpValor().doubleValue()));
                }
                if (formas.getTpfId().getTpfDescripcion().equals("OTROS CON UTILIZACIÓN DEL SISTEMA FINANCIERO")) {
                    sisFin = Double.parseDouble(df.format(sisFin + formas.getFfpValor().doubleValue()));
                }
                if (formas.getTpfId().getTpfDescripcion().equals("ENDOSO DE TÍTULOS")) {
                    endoso = Double.parseDouble(df.format(endoso + formas.getFfpValor().doubleValue()));
                }
                if (formas.getTpfId().getTpfDescripcion().equals("TEST")) {
                    test = Double.parseDouble(df.format(test + formas.getFfpValor().doubleValue()));
                }
            }

        }

    }

    public void onRowSelect() throws IOException, ServicioExcepcion, ServicioExcepcion, ServicioExcepcion {
        try {

            getTramitesControladorBb().setTramite(tramiteServicio.buscarTramitePorNumero(getTramitesControladorBb().getTramiteSeleccionado().getTraNumero()));

            if (getTramitesControladorBb().getTramite() != null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/tramites.jsf?numero=" + getTramitesControladorBb().getTramite().getTraNumero());

            }
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generarReporte() {
        System.out.println("com.rm.empresarial.controlador.Impresion.generarReporte()");
        if (getTramitesControladorBb().getTramiteSeleccionado().getTraNumero() != null) {
            int Tramite = getTramitesControladorBb().getTramiteSeleccionado().getTraNumero().intValue();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numTramite", Tramite);
            try {
                reporteUtil.imprimirReporteEnPDF("repo", "proforma", parametros);
            } catch (JRException | IOException | NamingException | SQLException ex) {
                Logger.getLogger(TramitesControlador.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void busquedaPorTramite() {
        getTramitesControladorBb().setFiltro(Boolean.TRUE);
    }

    public void selecMenuCaja() throws ServicioExcepcion {
        setListaCaja(cajaDao.listarTodo());
    }

    public void generarReporteCierre() throws ServicioExcepcion {
        if (getFecha() != null) {
            Date Fecha = getFecha();
            Institucion institucion = servicioInstitucionDao.obtenerInstitucion();
            setPathImagen(institucion.getInsLogo());
            File archivo = new File(getPathImagen());
            if (!archivo.exists()) {
                setPathImagen("");
                System.out.println("OJO: ¡¡No existe el archivo de configuración!!");
            }
            String Usuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
            String Estado = getEstado();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Fecha", Fecha);
            parametros.put("Usuario", Usuario);
            parametros.put("Estado", Estado);
            parametros.put("pathImagen", getPathImagen());
            try {
                reporteUtil.imprimirReporteEnPDF("cierre", "cierreDeCaja", parametros);

            } catch (JRException ex) {
                Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException ex) {
                Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void generarReporteCierreNVE() throws ServicioExcepcion {
        if (getFecha() != null) {
            Date Fecha = getFecha();
            Institucion institucion = servicioInstitucionDao.obtenerInstitucion();
            setPathImagen(institucion.getInsLogo());
            File archivo = new File(getPathImagen());
            if (!archivo.exists()) {
                setPathImagen("");
                System.out.println("OJO: ¡¡No existe el archivo de configuración!!");
            }
            String Usuario = dataManagerSesion.getUsuarioLogeado().getUsuLogin();
            String Estado = getEstado();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("Fecha", Fecha);
            parametros.put("Usuario", Usuario);
            parametros.put("Estado", Estado);
            parametros.put("pathImagen", getPathImagen());
            try {
                reporteUtil.imprimirReporteEnPDF("cierreNVE", "cierreDeCaja", parametros);

            } catch (JRException ex) {
                Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException ex) {
                Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(controladorCierreCaja.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
