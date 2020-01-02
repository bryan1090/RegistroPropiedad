/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.ImpresionDao;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.NotariaServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;

@Named("impresionControlador")
@SessionScoped
public class impresionControlador implements Serializable {

    @EJB
    private ImpresionDao impresionDao;

    @Getter
    @Setter
    private List<Tramite> lista = new ArrayList<>();

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Inject
    ReporteUtil reporteUtil;

    @EJB
    TramiteServicio tramiteServicio;

    @EJB
    private InstitucionDao servicioInstitucion;

    @EJB
    private TramiteValorServicio tramiteValorServicio;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    private TramiteDetalleServicio tramiteDetalleServicio;
    
    @EJB
    private NotariaServicio notariaServicio;

    @Getter
    @Setter
    private String pathImagen;

    @Getter
    @Setter
    private BigDecimal totalTraValor;

    public impresionControlador() {

        lista = new ArrayList<>();

    }

    public void listar() {
        setLista(impresionDao.listarTramite());
    }

    public void onRowSelect() throws IOException, ServicioExcepcion, ServicioExcepcion, ServicioExcepcion {
        try {

            getTramitesControladorBb().setTramite(tramiteServicio.buscarTramitePorNumero(getTramitesControladorBb().getTramiteSeleccionado().getTraNumero()));

            if (getTramitesControladorBb().getTramite() != null) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/tramites.jsf?numero=" + getTramitesControladorBb().getTramite().getTraNumero());

            }
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(impresionControlador.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void generarReporte() throws ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.Impresion.generarReporte()");
        Date fechaImpresion = Calendar.getInstance().getTime();
        Institucion institucion = institucionServicio.obtenerInstitucion();
        if (getTramitesControladorBb().getTramiteSeleccionado().getTraNumero() != null) {
            totalTraValor = BigDecimal.ZERO;
            TramiteDetalle traDetalleConsultado = tramiteDetalleServicio.buscarPorNumTramite(getTramitesControladorBb().getTramiteSeleccionado().getTraNumero());
            String cliente = traDetalleConsultado.getPerId().getPerApellidoPaterno() + " " + traDetalleConsultado.getPerId().getPerApellidoMaterno() + " " + traDetalleConsultado.getPerId().getPerNombre();
            String ciCLiente = traDetalleConsultado.getPerId().getPerIdentificacion();
            int tramite = getTramitesControladorBb().getTramiteSeleccionado().getTraNumero().intValue();
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
            }else {
                JsfUtil.addErrorMessage("No existen tramitesValor con estado distinto de F");
            }

        }
    }

    public void busquedaPorTramite() {
        getTramitesControladorBb().setFiltro(Boolean.TRUE);
    }

    public void busquedaPorFecha() {
        getTramitesControladorBb().setFiltro(Boolean.FALSE);
    }

    public void listarPorTramite() throws ServicioExcepcion, ParseException {
        setLista(impresionDao.listarPorNumeroTramite(getTramitesControladorBb().getTraNumero()));
    }

    public void listarTramiteNumero() throws ServicioExcepcion, ParseException {
        setLista(impresionDao.listarPorTramite(getTramitesControladorBb().getTraNumero()));
    }

    public void listarPorFecha() throws ServicioExcepcion, ParseException {
        if (getTramitesControladorBb().getFechaIni().equals(getTramitesControladorBb().getFechaFin())) {

            setLista(impresionDao.listarPorFecha(getTramitesControladorBb().getFechaIni(), getTramitesControladorBb().getFechaFin()));
        } else {
            setLista(impresionDao.listarFecha(getTramitesControladorBb().getFechaIni(), getTramitesControladorBb().getFechaFin()));
        }

    }

    public void listarTramite() {
        setLista(impresionDao.listarTodo());
    }

    public void generarReporteTramite() {
        if (getTramitesControladorBb().getTramiteSeleccionado().getTraNumero() != null) {
            try {
                Institucion institucion = servicioInstitucion.obtenerInstitucion();
                setPathImagen(institucion.getInsLogo());
            } catch (ServicioExcepcion e) {
                JsfUtil.addWarningMessage("Error al cargar path de imagen");
            }
            int numTramite = getTramitesControladorBb().getTramiteSeleccionado().getTraNumero().intValue();
            int traInicial = getTramitesControladorBb().getTramiteSeleccionado().getTraInicial();
            int traFinal = getTramitesControladorBb().getTramiteSeleccionado().getTraFinal();
            String notariaRep = "";
             if (getTramitesControladorBb().getTramiteSeleccionado().getTraNotaria() != Short.valueOf("0")) {
                notariaRep = notariaServicio.encontrarPorNumNotariaTopUno(getTramitesControladorBb().getTramiteSeleccionado().getTraNotaria()).getNotNotario();
            } else {
                notariaRep = "";
            }
            
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("NumeroTramite", numTramite);
            parametros.put("pathImagen", pathImagen);
            parametros.put("traInicial", traInicial);
            parametros.put("traFinal", traFinal);
            parametros.put("notaria", notariaRep);
            try {
                reporteUtil.imprimirReporteEnPDF("ReimpTramiteDesc", "reporteTramite", parametros);

            } catch (JRException | IOException | NamingException | SQLException ex) {
                Logger.getLogger(RecepcionDocumentacionControlador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void listarTramiteFecha() throws ServicioExcepcion, ParseException {
        if (getTramitesControladorBb().getFechaIni().equals(getTramitesControladorBb().getFechaFin())) {

            setLista(impresionDao.listarFechaIgual(getTramitesControladorBb().getFechaIni(), getTramitesControladorBb().getFechaFin()));
        } else {
            setLista(impresionDao.listarFechaDiferente(getTramitesControladorBb().getFechaIni(), getTramitesControladorBb().getFechaFin()));
        }

    }

}
