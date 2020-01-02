package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.controlador.util.UtilFechaEntrega;
import com.rm.empresarial.dao.FacturaDao;
import com.rm.empresarial.dao.FacturaDetalleDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorCajasReingresoTramite")
@ViewScoped
public class ControladorCajasReingresoTramite implements Serializable {

    @Inject
    private DataManagerSesion dataManagerSesion;
    @Inject
    private ReporteUtil reporteUtil;

    @EJB
    private TramiteDao servicioTramite;
    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;
    @EJB
    private TramiteValorServicio servicioTramiteValor;
    @EJB
    private RepertorioDao repertorioDao;
    @EJB
    private FacturaDetalleDao facturaDetalleDao;
    @EJB
    private FacturaDao facturaDao;
    @EJB
    private InstitucionServicio institucionServicio;

    @Getter
    @Setter
    private Tramite tramite;

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramiteDetalle;
    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor;

    @Getter
    @Setter
    private Long numeroTramite;
    @Getter
    @Setter
    private String estadoBtnGuardar;
    @Getter
    @Setter
    private String estadoBtnImprimir;
    @Getter
    @Setter
    private Boolean disabledReingreso;
    @Getter
    @Setter
    private Boolean rendFechaEntrega;
   

    @Inject
    private UtilFechaEntrega utilFechaEntrega;

    public ControladorCajasReingresoTramite() {
        disabledReingreso = false;
        rendFechaEntrega = false;
    }

    @PostConstruct
    public void postControladorCajasReingresoTramite() {
        numeroTramite = null;
        estadoBtnGuardar = "true";
        estadoBtnImprimir = "true";
        tramite = new Tramite();
        listaTramiteDetalle = new ArrayList<>();
        listaTramiteValor = new ArrayList<>();
    }

    public void consultarTramiteEnReingreso() throws ServicioExcepcion {

        setTramite(servicioTramite.buscarTramitePor_numero_estadoRI(numeroTramite));
        if (tramite != null) {
//            utilFechaEntrega.calcularFechaEntregaTramite(tramite.getTraNumero());
//            tramite.setTraFechaEntrega(utilFechaEntrega.getFechaEntregaAux());
            List<TramiteValor> preListaTramValor = new ArrayList<>();
            preListaTramValor.clear();
            setListaTramiteDetalle(servicioTramiteDetalle.buscarPorNumeroDeTramite(numeroTramite));
            preListaTramValor = (servicioTramiteValor.buscarPorTramite(servicioTramite.buscarTramitePorNumero(numeroTramite)));
            for (TramiteValor tramiteValor : preListaTramValor) {
                if(tramiteValor.getTrvValor().compareTo(BigDecimal.ZERO) != 0){
                    listaTramiteValor.add(tramiteValor);
                }
            }
            setEstadoBtnGuardar("false");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Existe Trámite Para Reingreso", ""));
        }
    }

    public void generarReporte() {
//        if (getTramite().getTraNumero() != null) {
//            int numtTramite = getTramite().getTraNumero().intValue();
//            Map<String, Object> parametros = new HashMap<>();
//            parametros.put("NumeroTramite", numtTramite);
//            try {
//                reporteUtil.imprimirReporteEnPDF("ReIngresoTramite", "Reingreso_N°" + numtTramite, parametros);
//            } catch (JRException | IOException | NamingException | SQLException ex) {
//                Logger.getLogger(TramitesControlador.class.getName()).log(Level.SEVERE, null, ex);
//            }
//
//        }
        /***/
        try {
            FacturaDetalle factDetall = new FacturaDetalle();
            factDetall = facturaDetalleDao.PorNumeroTramite(getTramite().getTraNumero().intValue());
            Factura factura = new Factura();
            factura = facturaDao.buscarPorNumFactura(factDetall.getFacId().getFacNumero());
            String numeroFactura = factura.getFacNumero();
            BigDecimal total = factura.getFacTotal();
            String pathImagen = institucionServicio.encontrarInstitucionPorId("1").getInsLogo();
            String nomApePer = factura.getFacPerApellidoPaterno().trim()
                    + " " + factura.getFacPerApellidoMaterno().trim()
                    + " " + factura.getFacPerNombre().trim();
            
            Map<String, Object> parametros = new HashMap<>();
            if(factura.getFacCodigoVerificacion() == null){
                parametros.put("codigoVerif", " ");
            }
            else{
                parametros.put("codigoVerif", factura.getFacCodigoVerificacion());
            }           
            
            parametros.put("numeroFactura", numeroFactura);
            parametros.put("nomApePer", nomApePer);
            parametros.put("pathImagen", pathImagen);
            
            parametros.put("valorNum", total);
            parametros.put("valorAlf", TransformadorNumerosLetrasUtil.transformador(total.toString()).toString());

            reporteUtil.imprimirReporteEnPDF("FacturaRI", "Comprobante", parametros);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Comprobante Generado"));
        } catch (Exception e) {
            System.err.println(e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al generar NV");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    public void reingresarTramite() throws ServicioExcepcion{
        
        getTramite().setTraEstado("REP");
        getTramite().setTraEstadoRegistro("RA");
        utilFechaEntrega.cargarParametros("CAJ");
        utilFechaEntrega.calcularFechaEntregaTramite(tramite.getTraNumero());
        getTramite().setTraFechaEntrega(utilFechaEntrega.getFechaEntregaAux());
        getTramite().setTraFechaReIngreso(Calendar.getInstance());
        servicioTramite.edit(tramite);
        setEstadoBtnGuardar("true");
        setEstadoBtnImprimir("false");
        disabledReingreso = true;
        rendFechaEntrega = true;
        cambiarEstadoRepertorio();
        resetNumRepertorioTramDetalle();
    }

    public void refrescar() {
        postControladorCajasReingresoTramite();
        disabledReingreso = false;
    }

    public void cerrarCaja() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/cierreCaja.xhtml");
    }
    
    public void resetNumRepertorioTramDetalle() throws ServicioExcepcion{
        List<TramiteDetalle> listTramDet = new ArrayList<>();
        listTramDet.clear();
        listTramDet = servicioTramiteDetalle.buscarPorNumeroDeTramiteYestado(getTramite().getTraNumero());
            for (TramiteDetalle tramiteDetalle : listTramDet) {
                tramiteDetalle.setTdtNumeroRepertorio(0);
                servicioTramiteDetalle.edit(tramiteDetalle);
            }
    }

    public void cambiarEstadoRepertorio() {
        try {
            List<Repertorio> listaRepertorio = new ArrayList<>();
            listaRepertorio.clear();
            listaRepertorio = repertorioDao.listarPorNumTramite(getTramite().getTraNumero());
            for (Repertorio repertorio : listaRepertorio) {
                repertorio.setRepEstado("I");
                repertorioDao.edit(repertorio);
            }

        } catch (Exception e) {
            System.err.println(e);
        }

    }
}
