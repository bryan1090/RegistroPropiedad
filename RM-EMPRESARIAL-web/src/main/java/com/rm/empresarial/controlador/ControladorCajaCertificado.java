/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.ControladorCajasRecBb;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.CertificadoAccionDao;
import com.rm.empresarial.dao.FormularioWebDao;
import com.rm.empresarial.dao.FormularioWebDetalleDao;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.dao.TipoCertificadoDao;
import com.rm.empresarial.dao.UsuarioDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.CertificadoAccion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FacturaDetalleImpuesto;
import com.rm.empresarial.modelo.FacturaFormaPago;
import com.rm.empresarial.modelo.FormularioWeb;
import com.rm.empresarial.modelo.FormularioWebDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Serie;
import com.rm.empresarial.modelo.TipoCertificado;
import com.rm.empresarial.modelo.TipoDescuento;
import com.rm.empresarial.modelo.TipoFormaPago;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.FacturaFormaPagoServicio;
import com.rm.empresarial.servicio.FacturaInfoAdicionalServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.RepositorioSRIServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoDescuentoServicio;
import com.rm.empresarial.servicio.TipoFormaPagoServicio;
import com.rm.empresarial.servicio.UsuarioCajaServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

/**
 *
 * @author Wilson Herrera
 */
@ViewScoped
@Named
public class ControladorCajaCertificado extends JsfUtil implements Serializable {

    @EJB
    private RepositorioSRIServicio repositorioSRIServicio;

    @EJB
    private FacturaServicio facturaServicio;

    @EJB
    private InstitucionServicio institucionServicio;

    @EJB
    private FacturaDetalleServicio facturaDetalleServicio;

    @EJB
    private CertificadoCargaServicio servicioCertificadoCarga;

    @EJB
    private FacturaInfoAdicionalServicio facturaInfoAdicionalServicio;

    @EJB
    private SecuenciaServicio secuenciaServicio;

    @EJB
    private UsuarioCajaServicio usuarioCajaServicio;

    @EJB
    private TipoDescuentoServicio servicioTipoDescuento;

    @EJB
    private CertificadoAccionDao certificadoAccionDao;

    @EJB
    private TipoFormaPagoServicio tipoFormaPagoServicio;

    @EJB
    private TipoCertificadoDao tipoCertificadoDao;

    @EJB
    private FormularioWebDao formularioWebDao;

    @EJB
    private FormularioWebDetalleDao formularioWebDetalleDao;

    @EJB
    private PersonaDao personaDao;

    @EJB
    private UsuarioDao usuarioDao;

    @EJB
    private FacturaFormaPagoServicio facturaFormaPagoServicio;

    @EJB
    private ConfigDetalleServicio servicioDetalleConfig;

    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;

    @Getter
    @Setter
    private FormularioWeb formularioWeb = new FormularioWeb();

    @Getter
    @Setter
    private List<TipoCertificado> listaTipoCertificado = new ArrayList<>();

    @Getter
    @Setter
    private TipoCertificado tipoCertificado = new TipoCertificado();

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

    @Getter
    @Setter
    private Institucion institucion;

    @Getter
    @Setter
    Long troId;

    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;

    @Getter
    @Setter
    int FlwId;

    @Getter
    @Setter
    private TipoDescuento tipoDescuentoSel;

    @Getter
    @Setter
    private List<TipoDescuento> listaTipoDescuento;

    @PostConstruct
    public void inicializar() {
        getCajasRecBb().inicializar();

        cargarInstitucion();
        try {

            listaTipoCertificado = tipoCertificadoDao.listarTodo();

        } catch (ServicioExcepcion e) {
            System.out.println(e);
        }

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

        Date hoy = new Date();
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        getCajasRecBb().setFechaIngreso(formatoFecha.format(hoy));

        if (getCajasRecBb().getFactura().getFacSubTotal() == null) {
            getCajasRecBb().getFactura().setFacSubTotal(BigDecimal.ZERO);
            getCajasRecBb().getFactura().setFacIva(BigDecimal.ZERO);
            getCajasRecBb().getFactura().setFacTotal(BigDecimal.ZERO);
        }

        if (getCajasRecBb().getNombrePersona() == null) {
            getCajasRecBb().setNombrePersona(" ");
        }
        try {
            listaTipoDescuento = servicioTipoDescuento.listarTodo_EstadoA();
            //tipoDescuentoSel = servicioTipoDescuento.obtenerTipo100PorCiento();
            tipoDescuentoSel = new TipoDescuento();

        } catch (Exception e) {
            System.out.println(e);
        }
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

//                String secuencia = Integer.toString(getSecuenciaControlador().getControladorBb().getNumeroTramite());
//                getCajasRecBb().setNumeroFactura(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                getCajasRecBb().setNumeroComprobante(getSecuenciaControlador().getControladorBb().getSecuenciaFactura());

            }
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(ControladorCajasRecaudacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscarTramite() {
        Integer error = 0;
        if (getCajasRecBb().getFacCertificadoNombre() != null) {
            if (!getCajasRecBb().getFacCertificadoNombre().matches("^\\s*[a-zA-Z\\ñÑÁÉÍÓÚáéíóú'´][a-zA-Z\\sñÑÁÉÍÓÚáéíóú'´]*$")) {
                error = 1;
            }
        }

        if (getCajasRecBb().getFacCertificadoPrimerApe() != null) {
            if (!getCajasRecBb().getFacCertificadoPrimerApe().matches("^\\s*[a-zA-Z\\ñÑÁÉÍÓÚáéíóú'´][a-zA-Z\\sñÑÁÉÍÓÚáéíóú'´]*$")) {
                error = 2;
            }
        }

        if (getCajasRecBb().getFacCertificadoSegundoApe() != null && !getCajasRecBb().getFacCertificadoSegundoApe().isEmpty()) {
            if (!getCajasRecBb().getFacCertificadoSegundoApe().matches("^\\s*[a-zA-Z\\ñÑÁÉÍÓÚáéíóú'´][a-zA-Z\\sñÑÁÉÍÓÚáéíóú'´]*$")) {
                error = 3;
            }
        }

        if (getCajasRecBb().getFacCertificado() == null) {
            error = 4;

        } else {
            if (getCajasRecBb().getFacCertificado() < 1) {
                error = 4;
            }
        }

        if (getCajasRecBb().getFacNumeroDerecho() == null) {
            error = 5;

        } else {
            if (getCajasRecBb().getFacNumeroDerecho() < 1) {
                error = 5;
            }
        }

        switch (error) {
            case 1:
                addErrorMessage("Ingrese solo letras en el nombre");
                break;
            case 2:
                addErrorMessage("Ingrese solo letras en el apellido paterno");
                break;
            case 3:
                addErrorMessage("Ingrese solo letras en el apellido materno");
                break;
            case 4:
                addErrorMessage("Ingrese la acntidad de certificados");
                break;
            case 5:
                addErrorMessage("Ingrese la cantidad de derechos");
                break;

            default:
                if (getCajasRecBb().getFacWeb()) {
                    try {

                        if (getFormularioWeb() != null) {

                            verificarDetalle(Integer.parseInt(getCajasRecBb().getNumeroTramite()));
                            if (!getCajasRecBb().getEstado()) {
                                llenarDetalle();
                                getCajasRecBb().setNumeroTramite("");
                            } else {
                                addErrorMessage("Error el formulario ya fue agregado");
                            }

                        } else {
                            addErrorMessage("El formulario no existe");
                        }
                    } catch (Exception e) {
                        addErrorMessage("El formulario no existe1");
                    }

                } else {
                    try {
                        verificarDetalle(Integer.parseInt(getCajasRecBb().getNumeroTramite()));
                        if (!getCajasRecBb().getEstado()) {
                            llenarDetalle();
                            getCajasRecBb().setNumeroTramite("");
                        } else {
                            addErrorMessage("Error el formulario ya fue agregado");
                        }
                    } catch (Exception e) {
                        addErrorMessage("Ingrese el N° Pedido");
                    }

                }
                break;
        }

    }

    public void seleccionarTipoCertificado() {
        setTipoCertificado(tipoCertificadoDao.valorCertificado((getTroId())));
    }

    public void agregarFechaEntrega() {
        try {
            boolean error = false;
            if (getCajasRecBb().getFacWeb()) {

                Persona persona = new Persona();
                setFormularioWeb(formularioWebDao.buscarFormulario(Integer.parseInt(getCajasRecBb().getNumeroTramite())));
                persona = usuarioDao.buscarPersona(getFormularioWeb().getFlwUser());

                if (persona != null) {
                    getCajasRecBb().setFacCertificadoNombre(persona.getPerNombre());
                    getCajasRecBb().setFacCertificadoPrimerApe(persona.getPerApellidoPaterno());
                    getCajasRecBb().setFacCertificadoSegundoApe(persona.getPerApellidoMaterno());
                }

                if (getFormularioWeb() != null) {
                    List<FormularioWebDetalle> formularioWebDetalle = new ArrayList<FormularioWebDetalle>();
                    formularioWebDetalle = formularioWebDetalleDao.buscarDocumentoWeb(Integer.parseInt(getCajasRecBb().getNumeroTramite()));

                    if (formularioWebDetalle != null) {
                        for (FormularioWebDetalle item : formularioWebDetalle) {
                            if (item.getFwdGrupo().equals("A002") && item.getFwdLinea() == 11) {
                                getCajasRecBb().setNumeroPredio(item.getFwdValorC03());
                            }

                            if (item.getFwdGrupo().equals("A002") && item.getFwdLinea() == 12) {
                                getCajasRecBb().setNumeroCatastro(item.getFwdValorC03());
                            }

                            if (item.getFwdGrupo().equals("A002") && item.getFwdLinea() == 13) {
                                getCajasRecBb().setFacCertificado(Short.parseShort(item.getFwdValorN01().toString()));
                            }

                            if (item.getFwdGrupo().equals("A002") && item.getFwdLinea() == 13) {
                                getCajasRecBb().setFacNumeroDerecho(Short.parseShort(item.getFwdValorN02().toString()));
                            }

                        }

                    } else {
                        addErrorMessage("El formulario no existe");
                        error = true;
                    }

                } else {
                    addErrorMessage("El formulario no existe");
                    error = true;
                }

            }

            setTipoCertificado(tipoCertificadoDao.valorCertificado((getTroId())));
            DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime((formatoFecha.parse(getCajasRecBb().getFechaIngreso()))); //tuFechaBase es un Date;
            switch (getTipoCertificado().getTroUnidadTiempo()) {
                case "H":
                    calendar.add(Calendar.HOUR, getTipoCertificado().getTroCantidadTiempo()); //horasASumar es int.
                    break;
                case "D":
                    calendar.add(Calendar.DAY_OF_YEAR, getTipoCertificado().getTroCantidadTiempo()); //horasASumar es int.
                    break;
            }

            Date fechaEntrega = calendar.getTime(); //Y ya tienes la fecha sumada.
            getCajasRecBb().setFechaEntrega(formatoFecha.format(fechaEntrega));

            if (!error) {
                getCajasRecBb().setBloquearAccion(Boolean.TRUE);

                switch (getTipoCertificado().getTroTipo()) {

                    case "B":
                        getCajasRecBb().setBloquearPredioCatastro(Boolean.TRUE);
                        break;
                    case "N":
                        getCajasRecBb().setBloquearPredioCatastro(Boolean.TRUE);
                        break;
                    case "P":
                        getCajasRecBb().setBloquearPredioCatastro(Boolean.TRUE);
                        break;
                }
            }

        } catch (Exception e) {

            addErrorMessage("Error al calcular la fecha de entrega.");
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

    public List<FacturaDetalle> crearDetalleFac(int size) throws ServicioExcepcion {

        List<FacturaDetalle> list = new ArrayList<FacturaDetalle>();
        FacturaDetalleImpuesto list2 = new FacturaDetalleImpuesto();
        Integer tarifa = 0;
        Integer numeroAux = 0;
        String codigoPorcentaje = "0";
        BigDecimal cantidad = BigDecimal.ZERO;
        String catastro = "0";
        String predio = "0";
        int contador = 0;
        int contador1 = 1;
        BigDecimal trvIvaDet = BigDecimal.ZERO;
        BigDecimal valor = BigDecimal.ZERO;
        BigDecimal totalAux = BigDecimal.ZERO;
        BigDecimal descuento = BigDecimal.ZERO;

        if (getCajasRecBb().getNumeroCatastro() != null) {
            catastro = getCajasRecBb().getNumeroCatastro();
        }

        if (getCajasRecBb().getNumeroPredio() != null) {
            predio = getCajasRecBb().getNumeroPredio();
        }

        int A = getCajasRecBb().getFacCertificado(); //num certificados
        int B = getCajasRecBb().getFacNumeroDerecho();//num derechos
        if(A > B){
            cantidad = BigDecimal.valueOf(A);
        }
        if(B > A){
            cantidad = BigDecimal.valueOf(B);
        }
        if(A == B){
            cantidad = BigDecimal.valueOf(A);
        }
       
//        int B = getCajasRecBb().getFacNumeroDerecho();//num derechos
//
//        int C = getCajasRecBb().getFacFormaAdquisicion(); //inicializa en 1

//        if (A >= B && A >= C) {
//
//            cantidad = BigDecimal.valueOf(A);
//
//        } else {
//            if (B >= A && B >= C) {
//                cantidad = BigDecimal.valueOf(B);
//
//            } else {
//                cantidad = BigDecimal.valueOf(C);
//
//            }
//        }
        if (getCajasRecBb().getFacWeb()) {
            numeroAux = getFormularioWeb().getFlwId();
        } else {
            numeroAux = Integer.parseInt(getCajasRecBb().getNumeroTramite());
        }

        valor = getTipoCertificado().getTroValor().setScale(2, RoundingMode.DOWN);
        totalAux = totalAux.add(valor.multiply(cantidad.setScale(2, RoundingMode.DOWN)));
        BigInteger idTipTraAux = BigInteger.valueOf(getTipoCertificado().getTroId());

        if (getTipoCertificado().getTroIva().equals("N")) {
            tarifa = 0;
            trvIvaDet = trvIvaDet.add(valor.multiply(cantidad.setScale(2, RoundingMode.DOWN)));

        } else {
            BigDecimal tarifas = getCajasRecBb().getInstitucion().getInsIva().multiply(BigDecimal.valueOf(100));
            tarifa = tarifas.intValue();
            codigoPorcentaje = "2";
            trvIvaDet = trvIvaDet.add(getCajasRecBb().getInstitucion().getInsIva().multiply(valor.multiply(cantidad.setScale(2, RoundingMode.DOWN)))).setScale(2, RoundingMode.DOWN);

        }

        getCajasRecBb().getFacturaDetalleImpuesto().setFdiCodigo("2");
        getCajasRecBb().getFacturaDetalleImpuesto().setFdiCodigoPorcentaje(codigoPorcentaje);
        getCajasRecBb().getFacturaDetalleImpuesto().setFdiTarifa(new BigDecimal(tarifa));
        getCajasRecBb().getFacturaDetalleImpuesto().setFdiBaseImponible(totalAux);

        //descuento
        if (tipoDescuentoSel == null) {
            tipoDescuentoSel = new TipoDescuento();
            tipoDescuentoSel.setTpdPorcentaje(new BigDecimal(0));
        } else if (tipoDescuentoSel.getTpdPorcentaje() == null) {
            tipoDescuentoSel.setTpdPorcentaje(new BigDecimal(0));
        }
        descuento = descuento.add(totalAux).multiply(tipoDescuentoSel.getTpdPorcentaje()).divide(new BigDecimal(100));
        System.out.println("descuento" + descuento.floatValue());
        totalAux = totalAux.subtract(descuento);
        System.out.println("total" + totalAux);

        //Agregar detalles inpuesto a la lista
        list.add(new FacturaDetalle(null, numeroAux, BigDecimal.ONE, valor.multiply(cantidad.setScale(2, RoundingMode.DOWN)), descuento, totalAux, idTipTraAux, getTipoCertificado().getTroNombre(), trvIvaDet, getCajasRecBb().getFacturaDetalleImpuesto().getFdiCodigo(), getCajasRecBb().getFacturaDetalleImpuesto().getFdiCodigoPorcentaje(), getCajasRecBb().getFacturaDetalleImpuesto().getFdiTarifa(), getCajasRecBb().getFacturaDetalleImpuesto().getFdiBaseImponible(), predio, catastro));

        contador++;
        contador1++;

        return list;
    }

    public List<FacturaDetalle> crearDetallePosesionEf() {
        List<FacturaDetalle> list = new ArrayList<FacturaDetalle>();
        FacturaDetalleImpuesto list2 = new FacturaDetalleImpuesto();
        Integer tarifa = 0;
        Integer numeroAux = 0;
        String codigoPorcentaje = "0";
        BigDecimal cantidad = BigDecimal.ONE;
        String catastro = "0";
        String predio = "0";
        int contador = 0;
        int contador1 = 1;
        BigDecimal trvIvaDet = BigDecimal.ZERO;
        BigDecimal valor = BigDecimal.ZERO;
        BigDecimal totalAux = BigDecimal.ZERO;

        if (getCajasRecBb().getNumeroCatastro() != null) {
            catastro = getCajasRecBb().getNumeroCatastro();
        }

        if (getCajasRecBb().getNumeroPredio() != null) {
            predio = getCajasRecBb().getNumeroPredio();
        }

//        if (getCajasRecBb().getFacWeb()) {
//            numeroAux = getFormularioWeb().getFlwId();
//        } else {
//            numeroAux = Integer.parseInt(getCajasRecBb().getNumeroTramite());
//        }
        valor = getTipoCertificado().getTroValor().setScale(2, RoundingMode.DOWN);
        totalAux = totalAux.add(valor.multiply(cantidad.setScale(2, RoundingMode.DOWN)));
        BigInteger idTipTraAux = BigInteger.valueOf(getTipoCertificado().getTroId());

        if (getTipoCertificado().getTroIva().equals("N")) {
            tarifa = 0;
            trvIvaDet = trvIvaDet.add(valor.multiply(cantidad.setScale(2, RoundingMode.DOWN)));

        } else {
            BigDecimal tarifas = getCajasRecBb().getInstitucion().getInsIva().multiply(BigDecimal.valueOf(100));
            tarifa = tarifas.intValue();
            codigoPorcentaje = "2";
            trvIvaDet = trvIvaDet.add(getCajasRecBb().getInstitucion().getInsIva().multiply(valor.multiply(cantidad.setScale(2, RoundingMode.DOWN)))).setScale(2, RoundingMode.DOWN);

        }

//        getCajasRecBb().getFacturaDetalleImpuesto().setFdiCodigo("2");
//        getCajasRecBb().getFacturaDetalleImpuesto().setFdiCodigoPorcentaje(codigoPorcentaje);
//        getCajasRecBb().getFacturaDetalleImpuesto().setFdiTarifa(new BigDecimal(tarifa));
//        getCajasRecBb().getFacturaDetalleImpuesto().setFdiBaseImponible(totalAux);
        //Agregar detalles inpuesto a la lista
        //Guardo el certificado de posesion efectiva, si lo seleccionó
//        if (getCajasRecBb().getBolPosesionEfectiva()) {
        list.add(new FacturaDetalle(null, numeroAux,
                cantidad, valor.multiply(cantidad.setScale(2, RoundingMode.DOWN)),
                BigDecimal.ZERO, totalAux, idTipTraAux, "POSESIÓN EFECTIVA",
                trvIvaDet, getCajasRecBb().getFacturaDetalleImpuesto().getFdiCodigo(),
                getCajasRecBb().getFacturaDetalleImpuesto().getFdiCodigoPorcentaje(),
                getCajasRecBb().getFacturaDetalleImpuesto().getFdiTarifa(),
                getCajasRecBb().getFacturaDetalleImpuesto().getFdiBaseImponible(),
                predio, catastro));
//        }

        contador++;
        contador1++;

        return list;
    }

    public void llenarDetalle() throws ServicioExcepcion {
        int contador = 0;
        int contador1 = 1;
        Integer tarifa = 0;
        String codigoPorcentaje = "0";
        BigDecimal trvIvaDet = BigDecimal.ZERO;

        FacturaDetalle detalle = crearDetalleFac(contador1).get(contador);
        getCajasRecBb().getListaFacturaDetalle().add(detalle);

        if (getCajasRecBb().getBolPosesionEfectiva()) {
            FacturaDetalle detalle2 = crearDetallePosesionEf().get(contador);
            getCajasRecBb().getListaFacturaDetalle().add(detalle2);
        }

        //addSuccessMessage("Tramite Agregado Correctamente");
        calcularValoresFactura();

        contador++;
        contador1++;

    }

    public void verificarDetalle(int FlwId) {
        if (getCajasRecBb().getListaFacturaDetalle().size() > 1) {
            for (FacturaDetalle detalleFac : getCajasRecBb().getListaFacturaDetalle()) {

                if (FlwId == (detalleFac.getFdtTraNumero())) {
                    getCajasRecBb().setEstado(Boolean.TRUE);
                    break;
                }

            }
        }
    }

    public void calcularValoresFactura() {
        BigDecimal subtotal12 = BigDecimal.ZERO;
        BigDecimal iva = BigDecimal.ZERO;
        BigDecimal descuento = BigDecimal.ZERO;
        for (FacturaDetalle detalleFac : getCajasRecBb().getListaFacturaDetalle()) {
            subtotal12 = subtotal12.add(detalleFac.getFdtValor()).setScale(2, RoundingMode.DOWN);
            iva = iva.add((detalleFac.getFdiTarifa().multiply(detalleFac.getFdiBaseImponible())).divide(new BigDecimal(100)));
        }
        //BigDecimal iva = new BigDecimal(0.12);
        iva = iva.setScale(2, RoundingMode.DOWN);
        //subtotal de la factura
        getCajasRecBb().getFactura().setFacSubTotal(subtotal12);
        //iva de la factura
        getCajasRecBb().getFactura().setFacIva(iva);

        //**Valores seteados esperando logica
        getCajasRecBb().getFactura().setFacBaseIva(BigDecimal.ZERO);

        //descuento
        if (tipoDescuentoSel == null) {
            tipoDescuentoSel = new TipoDescuento();
            tipoDescuentoSel.setTpdPorcentaje(new BigDecimal(0));
        } else if (tipoDescuentoSel.getTpdPorcentaje() == null) {
            tipoDescuentoSel.setTpdPorcentaje(new BigDecimal(0));
        }
        descuento = descuento.add(subtotal12).multiply(tipoDescuentoSel.getTpdPorcentaje()).divide(new BigDecimal(100));
        getCajasRecBb().getFactura().setFacDescuento(descuento);

        //Total de la factura        
        getCajasRecBb().getFactura().setFacTotal((getCajasRecBb().getFactura().getFacSubTotal().add(getCajasRecBb().getFactura().getFacIva())).subtract(getCajasRecBb().getFactura().getFacDescuento()).setScale(2, RoundingMode.DOWN));
        //
        //Asignar a Pagos
//        getCajasRecBb().setValorPago(getCajasRecBb().getFactura().getFacTotal());
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
        if (getCajasRecBb().getPersona().getPerApellidoMaterno() == null || getCajasRecBb().getPersona().getPerApellidoMaterno().isEmpty()) {
            getCajasRecBb().getFactura().setFacPerApellidoMaterno(" ");
        } else {
            getCajasRecBb().getFactura().setFacPerApellidoMaterno(getCajasRecBb().getPersona().getPerApellidoMaterno());
        }
        getCajasRecBb().getFactura().setFacPerTelefono(getCajasRecBb().getPersona().getPerCelular());
        getCajasRecBb().getFactura().setFacPerCelular(getCajasRecBb().getPersona().getPerCelular());
        getCajasRecBb().getFactura().setFacPerEmail(getCajasRecBb().getPersona().getPerEmail());
        getCajasRecBb().getFactura().setFacPerDireccion(getCajasRecBb().getPersona().getPerDireccion());

        //numero de escritura
        short aux = 0;

        getCajasRecBb().getFactura().setFacNumeroDerecho(getCajasRecBb().getFacNumeroDerecho());
        getCajasRecBb().getFactura().setFacCertificado(getCajasRecBb().getFacCertificado());
        getCajasRecBb().getFactura().setFacObservacion(getCajasRecBb().getObservacion());
        getCajasRecBb().getFactura().setFacTroId(BigInteger.valueOf(getTipoCertificado().getTroId()));
        getCajasRecBb().getFactura().setFacTroNombre(getTipoCertificado().getTroNombre());
        getCajasRecBb().getFactura().setFacEstadoCertificado("R");

        if (getCajasRecBb().getFactura().getFacObservacion() == null) {
            getCajasRecBb().getFactura().setFacObservacion(" ");
        }

        try {
            DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date fechaEntrega = formatoFecha.parse(getCajasRecBb().getFechaEntrega());
            getCajasRecBb().getFactura().setFacFechaEntrega(fechaEntrega);
        } catch (Exception e) {
            addErrorMessage("Error al cargar la fecha de entrega");
        }

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
        getCajasRecBb().getFactura().setFacWeb(getCajasRecBb().getFacWeb());
        getCajasRecBb().getFactura().setFacCertificadoNombre(getCajasRecBb().getFacCertificadoNombre().toUpperCase());
        getCajasRecBb().getFactura().setFacCertificadoPrimerApe(getCajasRecBb().getFacCertificadoPrimerApe().toUpperCase());
        getCajasRecBb().getFactura().setFacCertificadoSegundoApe(getCajasRecBb().getFacCertificadoSegundoApe().toUpperCase());
        getCajasRecBb().getFactura().setFacFormaAdquisicion(getCajasRecBb().getFacFormaAdquisicion());
        personaDao.edit(getCajasRecBb().getPersona());

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
        getCajasRecBb().getFacturaInfoAdicional().setFiaValor("PETICIÓN CERTIFICADO");
    }
//AQUI SE GUARDA LA FACTURA Y LA FACTURA DETALLE

    public void guardarFactura() throws ServicioExcepcion {
        try {

            cabeceraFactura();

            // facturaDetalleAdicional();
            facturaInfoAdicional();
            getCajasRecBb().getFactura().setFacPosesionEfectiva(getCajasRecBb().getBolPosesionEfectiva());

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
            Cargalaboral();

            try {
                getCajasRecBb().setFacturado(Boolean.TRUE);
                getCajasRecBb().setBloquearPredioCatastro(Boolean.TRUE);
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

    public void Cargalaboral() {
        Usuario usuarioAsignado = new Usuario();
        if (getCajasRecBb().getFacWeb()) {
            usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("RFW", "certificado", 1);
        } else {

            usuarioAsignado = cargaLaboralUtil.obtenerCargaLaboral("DFC", "certificado", 1);
        }

        for (int i = 0; i < getCajasRecBb().getFacCertificado(); i++) {

            CertificadoCarga nuevoCertCar = new CertificadoCarga();
            nuevoCertCar.setCdcDocumento(getCajasRecBb().getFactura().getFacNumero());

            if (getCajasRecBb().getFacWeb()) {
                nuevoCertCar.setCdcTipo("RFW");
            } else {
                nuevoCertCar.setCdcTipo("DFC");
            }

            nuevoCertCar.setUsuId(usuarioAsignado);
            nuevoCertCar.setCdcEstado("A");
            nuevoCertCar.setFacId(getCajasRecBb().getFactura());
            nuevoCertCar.setCdcCantidadCertificado((short) 1);
            nuevoCertCar.setCdcCantidadDerecho(getCajasRecBb().getFactura().getFacNumeroDerecho());
            nuevoCertCar.setCdcUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            nuevoCertCar.setCdcFHR(Calendar.getInstance().getTime());
            nuevoCertCar.setCdcEstadoProceso("ACTIVO");
            servicioCertificadoCarga.create(nuevoCertCar);

            CertificadoAccion certAccion = new CertificadoAccion();
            certAccion.setCtaNumeroDocumento(getCajasRecBb().getFactura().getFacNumero());
            certAccion.setCtaEstadoProceso("CAJ");
            certAccion.setCtaEstadoRegistro("A");
            certAccion.setCtaObservacion("Creación de Factura");
            certAccion.setCtaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            certAccion.setCtaFHR(Calendar.getInstance().getTime());
            certificadoAccionDao.create(certAccion);

            CertificadoAccion certAc = new CertificadoAccion();
            certAc.setCtaNumeroDocumento(getCajasRecBb().getFactura().getFacNumero());
            certAc.setCtaEstadoProceso("CAJ");
            certAc.setCtaEstadoRegistro("A");
            certAc.setCtaObservacion("Asignacion de usuario a " + usuarioAsignado.getUsuLogin());
            certAc.setCtaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            certAc.setCtaFHR(Calendar.getInstance().getTime());
            certAc.setCtaUsuarioAsignado(usuarioAsignado.getUsuLogin());
            certificadoAccionDao.create(certAc);

        }
    }

    public void verificarDatos() throws ServicioExcepcion {
        Integer error = 0;

        if (getCajasRecBb().getFacCertificadoNombre() != null) {
            if (!getCajasRecBb().getFacCertificadoNombre().matches("^\\s*[a-zA-Z\\ñÑÁÉÍÓÚáéíóú'´][a-zA-Z\\sñÑÁÉÍÓÚáéíóú'´]*$")) {
                error = 1;
            }
        }

        if (getCajasRecBb().getFacCertificadoPrimerApe() != null) {
            if (!getCajasRecBb().getFacCertificadoPrimerApe().matches("^\\s*[a-zA-Z\\ñÑÁÉÍÓÚáéíóú'´][a-zA-Z\\sñÑÁÉÍÓÚáéíóú'´]*$")) {
                error = 2;
            }
        }

        if (getCajasRecBb().getFacCertificadoSegundoApe() != null && !getCajasRecBb().getFacCertificadoSegundoApe().isEmpty()) {
            if (!getCajasRecBb().getFacCertificadoSegundoApe().matches("^\\s*[a-zA-Z\\ñÑÁÉÍÓÚáéíóú'´][a-zA-Z\\sñÑÁÉÍÓÚáéíóú'´]*$")) {
                error = 3;
            }
        }

        if (getCajasRecBb().getFacCertificado() == null) {
            error = 4;

        } else {
            if (getCajasRecBb().getFacCertificado() < 1) {
                error = 4;
            }
        }

        if (getCajasRecBb().getFacNumeroDerecho() == null) {
            error = 5;

        } else {
            if (getCajasRecBb().getFacNumeroDerecho() < 1) {
                error = 5;
            }
        }

        if (getCajasRecBb().getIdentificacion() == null) {
            error = 6;
        } else {
            if (getCajasRecBb().getIdentificacion().equals("")) {
                error = 6;
            }
        }

        if (getCajasRecBb().getListaFacturaDetalle().isEmpty()) {
            error = 7;
        }

        if (getCajasRecBb().getPersona().getPerDireccion() == null) {
            error = 8;
        } else {
            if (getCajasRecBb().getPersona().getPerDireccion().equals("")) {
                error = 8;
            }
        }

        if (getCajasRecBb().getPersona().getPerCelular() == null) {
            error = 9;
        } else {
            if (getCajasRecBb().getPersona().getPerCelular().equals("")) {
                error = 9;
            }
        }

        if (getCajasRecBb().getPersona().getPerEmail() == null) {
            error = 10;
        } else {
            if (getCajasRecBb().getPersona().getPerEmail().equals("")) {
                error = 10;
            }
        }

        switch (error) {
            case 1:
                addErrorMessage("Ingrese solo letras en el nombre");
                break;
            case 2:
                addErrorMessage("Ingrese solo letras en el apellido paterno");
                break;
            case 3:
                addErrorMessage("Ingrese solo letras en el apellido materno");
                break;
            case 4:
                addErrorMessage("Ingrese la cantidad de certificados");
                break;
            case 5:
                addErrorMessage("Ingrese la cantidad de derechos");
                break;
            case 6:
                addErrorMessage("Ingrese la identificación");
                break;
            case 7:
                addErrorMessage("Ingrese el detalle de la factura");
                break;
            case 8:
                addErrorMessage("Ingrese la dirección");
                break;
            case 9:
                addErrorMessage("Ingrese el celular");
                break;
            case 10:
                addErrorMessage("Ingrese el email");
                break;

            default:
                getCajasRecBb().setConfigDetalleFactura(servicioDetalleConfig.obtenerConfigDetallePorDescripcion("MAXIMOFACTURA"));
                if (getCajasRecBb().getFactura().getFacTotal().compareTo(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero()) <= 0) {
                    guardarFactura();
                } else {
                    System.out.print("IMPRESION POR SEPARADO DE DOCUMENTOS");
                    int i = 0;
                    for (FacturaDetalle facDetalle : getCajasRecBb().getListaFacturaDetalle()) {
                        System.out.print(facDetalle.getFdtTraNumero());
                        getCajasRecBb().setFacturaDetalleIndividual(facDetalle);
                        i++;
                        if (getCajasRecBb().getFacturaDetalleIndividual().getFdtValor().compareTo(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero()) > 0) {
                            BigDecimal residuo = BigDecimal.ZERO;
                            BigDecimal valorDetalleAntiguo = BigDecimal.ZERO;
                            BigDecimal cienporciento = new BigDecimal(0.12);
                            valorDetalleAntiguo = getCajasRecBb().getFacturaDetalleIndividual().getFdtValor();
                            residuo = getCajasRecBb().getFacturaDetalleIndividual().getFdtValor().subtract(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero());
                            residuo = residuo.add(residuo.multiply(cienporciento));
                            BigDecimal facturatotal = BigDecimal.ZERO;
                            facturatotal = getCajasRecBb().getFactura().getFacTotal().subtract(residuo);
                            getCajasRecBb().getFacturaDetalleIndividual().setFdtValor(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero());
                            getCajasRecBb().getFacturaDetalleIndividual().setFdtTotal(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero());
                            getCajasRecBb().getFacturaDetalleIndividual().setFdiBaseImponible(getCajasRecBb().getConfigDetalleFactura().getConfigDetalleNumero());
                            guardarFacturaPorTramite(i, facturatotal, residuo, valorDetalleAntiguo);
                        } else {
                            BigDecimal facturatotal = BigDecimal.ZERO;
                            BigDecimal residuo = BigDecimal.ZERO;
                            BigDecimal valorDetalleAntiguo = BigDecimal.ZERO;
                            facturatotal = getCajasRecBb().getFactura().getFacTotal();
                            guardarFacturaPorTramite(i, facturatotal, residuo, valorDetalleAntiguo);
                        }
                    }
                }
                break;
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
            String nomApePer = getCajasRecBb().getFactura().getFacPerApellidoPaterno().trim() + " " + getCajasRecBb().getFactura().getFacPerApellidoMaterno().trim() + " " + getCajasRecBb().getFactura().getFacPerNombre().trim();
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
            reporteUtil.imprimirReporteEnPDF("FacturaCertificado", "Comprobante", parametros);

            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Successful", "Comprobante Generado"));
        } catch (Exception e) {
            System.err.println(e);
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Error al generar NV");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
    }

    public void verificarPago() throws ServicioExcepcion {
        agregarFechaEntrega();
        if (getCajasRecBb().getSaldoPago().compareTo(BigDecimal.ZERO) != 0) {
            if (getCajasRecBb().getValorPago().compareTo(BigDecimal.ZERO) == 1) {
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
//                if (getCajasRecBb().getValorPago().compareTo(getCajasRecBb().getFactura().getFacTotal()) == -1 || getCajasRecBb().getValorPago().compareTo(getCajasRecBb().getFactura().getFacTotal()) == 0) {
//                } else if (getCajasRecBb().getValorPago().compareTo(getCajasRecBb().getFactura().getFacTotal()) == 1) {
//                    getCajasRecBb().setBtnPago(Boolean.TRUE);
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El valor de pago ingresado es mayor al pago total", ""));
//                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingresar un valor mayor a 0", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "El saldo ya está en 0", ""));
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

    public void guardarFacturaPorTramite(int num, BigDecimal facturatotal, BigDecimal residuo, BigDecimal valorDetalleAntiguo) {
        try {
            cabeceraFactura();
            // facturaDetalleAdicional();
            facturaInfoAdicional();
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
            getCajasRecBb().getFactura().setFacTotal(total);
            getCajasRecBb().getFactura().setFacPosesionEfectiva(getCajasRecBb().getBolPosesionEfectiva());

            getCajasRecBb().setFactura(facturaServicio.guardarFactura(getCajasRecBb().getFactura()));
            getCajasRecBb().getFacturaDetalle().setFacId(getCajasRecBb().getFactura());
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

            //seteo del detalle guardado
            getCajasRecBb().getFacturaInfoAdicional().setFacId(getCajasRecBb().getFactura());
            facturaInfoAdicionalServicio.guardarFactura(getCajasRecBb().getFacturaInfoAdicional());

            BigDecimal porcentajeEquivalente = BigDecimal.ZERO;
            BigDecimal porcentajeValorDetalle = BigDecimal.ZERO;
            BigDecimal nuevoFfpValor = BigDecimal.ZERO;
            BigDecimal cienporciento = new BigDecimal(100.00);
            porcentajeEquivalente = porcentajeEquivalente.add(getCajasRecBb().getFactura().getFacTotal().multiply(cienporciento));
            porcentajeEquivalente = porcentajeEquivalente.divide(facturatotal, 2, RoundingMode.DOWN);
            porcentajeEquivalente = porcentajeEquivalente.divide(cienporciento);

            if (valorDetalleAntiguo.compareTo(BigDecimal.ZERO) == 0) {
                valorDetalleAntiguo = new BigDecimal(1.00);
                porcentajeValorDetalle = new BigDecimal(1.00);
            } else {
                porcentajeValorDetalle = getCajasRecBb().getFacturaDetalleIndividual().getFdtTotal().multiply(cienporciento);
                porcentajeValorDetalle = porcentajeValorDetalle.divide(valorDetalleAntiguo, 2, RoundingMode.DOWN);
                porcentajeValorDetalle = porcentajeValorDetalle.divide(cienporciento);
            }

            System.out.print("porcentaje=" + porcentajeEquivalente);

            for (FacturaFormaPago itemFormaPago : getCajasRecBb().getListapagos()) {

                FacturaFormaPago facturaFormaPago = new FacturaFormaPago();

                nuevoFfpValor = nuevoFfpValor.add(porcentajeEquivalente.multiply(itemFormaPago.getFfpValor().multiply(porcentajeValorDetalle)));

                facturaFormaPago.setFacId(getCajasRecBb().getFactura());
                facturaFormaPago.setFfpDescripcion(itemFormaPago.getFfpDescripcion());
                facturaFormaPago.setFfpFHR(getCajasRecBb().getFactura().getFacFHR());
                facturaFormaPago.setTpfId(itemFormaPago.getTpfId());
                facturaFormaPago.setFfpUser(getCajasRecBb().getFactura().getFacUser());
                facturaFormaPago.setFfpValor(nuevoFfpValor);

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
            facturaServicio.getEntityManager().clear();
        } catch (ServicioExcepcion e) {
            addErrorMessage("Error al Guardar Factura" + e);
        }
    }
}
