/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.ec.controladores.EnviarEmailController;
import com.rm.empresarial.dao.EmailDao;
import com.rm.empresarial.dao.PathDocEleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.EMensaje;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FacturaDetalleImpuesto;
import com.rm.empresarial.modelo.FacturaFormaPago;
import com.rm.empresarial.modelo.HostMail;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.PathDocEle;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.servicio.EMensajeServicio;
import com.rm.empresarial.servicio.FacturaDetalleImpuestoServicio;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.HostMailServicio;
import com.rm.empresarial.servicio.InstitucionServicio;
import com.rm.empresarial.servicio.PathDocEleServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import doel.sri.bridge.ConfiguracionMaestro;
import doel.sri.bridge.ConfiguracionRepositorio;
import doel.sri.bridge.ConfiguracionWS;
import doel.sri.modelo.BCliente;
import doel.sri.modelo.BEmpresa;
import doel.sri.modelo.BFactura;
import doel.sri.modelo.BFacturaDetalle;
import doel.sri.modelo.BImpuesto;
import doel.sri.modelo.BPago;
import doel.sri.modelosri.MensajeSri;
import doel.sri.modelosri.RespuestaAutorizacion;
import doel.sri.modelosri.RespuestaRecepcion;
import doel.sri.util.FirmaService;
import doel.sri.util.Util;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import lombok.Getter;
import lombok.Setter;
import sri.match.modelo.ParametroMatch;
import sri.match.modelo.RespuestaMatch;
import sri.match.servicios.ConsultaAutorizacion;
import sri.match.servicios.SOAPService;
import sri.match.servicios.ServicioMatch;
import sri.match.servicios.Transmisor;

/**
 *
 * @author Wilson Herrera
 */
@Dependent
public class ControladorModelosSRI implements Serializable {
    
    @EJB
    private FacturaDetalleImpuestoServicio facturaDetalleImpuestoServicio;
    @EJB
    private FacturaServicio facturaServicio;
    
    @EJB
    private EMensajeServicio eMensajeServicio;
    
    @EJB
    private InstitucionServicio institucionServicio;
    
    @EJB
    private FacturaDetalleServicio facturaDetalleServicio;
    
    @EJB
    private TramiteServicio tramiteServicio;
    
    @EJB
    private TramiteAccionServicio tramiteAccionServicio;
    
    @EJB
    private PathDocEleServicio pathDocEleServicio;
    
    @EJB
    private HostMailServicio hostMailServicio;
    @Getter
    @Setter
    private HostMail mail;
    
    @Inject
    private EnviarEmailController enviarEmailUtil;
    
    private BFactura bFactura;
    private BCliente bCliente;
    private BEmpresa bEmpresa;
    private RespuestaRecepcion respuestaRecepcion;
    private PathDocEle pathDocEle;
    private Tramite tramite;
    private TramiteAccion tramiteAccion;
    private List<BPago> bPago = new ArrayList<BPago>();
    private List<BFacturaDetalle> listaBfacturaDetalle = new ArrayList<BFacturaDetalle>();
    private List<EMensaje> listaMensajes = new ArrayList<EMensaje>();
    private ConfiguracionMaestro conf_MASTER = new ConfiguracionMaestro();
    private Institucion institucion = new Institucion();
    
    public void cargarModelos() throws ServicioExcepcion, Exception {
  //      hostMailServicio = new HostMailServicio();
//        mail = hostMailServicio.obtenerPorConsultaJpaNombrada(mail.LISTAR_TODO, null);
        institucion = institucionServicio.obtenerInstitucion();
        pathDocEle = pathDocEleServicio.obtenerPaths();
        List<Factura> listaFactura = facturaServicio.buscarPorClaveAcceso();
        for (Factura factura : listaFactura) {
            
            List<FacturaDetalle> facturaDetalle = facturaDetalleServicio.lisarPorNumeroFactura(factura.getFacNumero());
            asignaModeloSri(factura, institucion, facturaDetalle, pathDocEle);
        }
    }
    
    public void asignaModeloSri(Factura factura, Institucion institucion, List<FacturaDetalle> listaDetalle, PathDocEle pathDocEle) throws ServicioExcepcion, Exception {
        //BCLIENTEB
        pathDocEle = pathDocEle;
        conf_MASTER.setEnviarEmail(false);
        BigDecimal baseImponibleImpFac0 = BigDecimal.ZERO;
        BigDecimal baseImponibleImpFac12 = BigDecimal.ZERO;
        BCliente bCliente = new BCliente();
        
        bCliente.setDireccion(verificarTildes(factura.getFacPerDireccion()));
        bCliente.setEmail(factura.getFacPerEmail());
        bCliente.setIdTipoIdentificacion(1);
        bCliente.setNombreCliente(verificarTildes(factura.getFacPerNombre()));
        bCliente.setNumeroIdentificacion(factura.getFacPerIdentificacion());
        bCliente.setTelefonoCelular(factura.getFacPerCelular());
        bCliente.setTelefonoConvencional(factura.getFacPerTelefono());

        //BEMPRESA
        BEmpresa bEmpresa = new BEmpresa();
        bEmpresa.setDireccion(verificarTildes(institucion.getInsDireccion()));
        bEmpresa.setRUC(institucion.getInsRuc());
        bEmpresa.setRazonSocial(verificarTildes(institucion.getInsNombre()));
        //BFACTURADETALLE
        Factura facturaAux = factura;

        //ITERATIR
        Iterator<FacturaDetalle> it = facturaAux.getFacturaDetalleList().iterator();
        
        while (it.hasNext()) {
            BFacturaDetalle add1 = new BFacturaDetalle();
            FacturaDetalle next = it.next();
            FacturaDetalleImpuesto facturaDetalleImpuesto = next.getFacturaDetalleImpuestoList().get(0);
            add1.setCantidad(next.getFdtCantidad().intValue());
            add1.setCodigo("COD1");
            add1.setDescripcion(verificarTildes(next.getFdtTtrDescripcion()));
            add1.setValorUnitario(facturaDetalleImpuesto.getFdiBaseImponible().doubleValue());
            add1.setSubtotal(next.getFdtTotal().doubleValue());
            add1.setTotal(facturaDetalleImpuesto.getFdiBaseImponible().multiply(institucion.getInsIva().add(facturaDetalleImpuesto.getFdiBaseImponible())).doubleValue());
            add1.setValorIva(facturaDetalleImpuesto.getFdiBaseImponible().multiply(institucion.getInsIva()).doubleValue());
            System.out.print("subtotal agregado" + add1.getValorUnitario());
            //impuestos
            List<BImpuesto> impuestosDetalle = new ArrayList<BImpuesto>();
            BImpuesto iva = new BImpuesto();
            iva.setCodigoSri(facturaDetalleImpuesto.getFdiCodigo());
            iva.setCodigoPorcentaje(facturaDetalleImpuesto.getFdiCodigoPorcentaje());
            iva.setBaseImponible(facturaDetalleImpuesto.getFdiBaseImponible().doubleValue());
            if (facturaDetalleImpuesto.getFdiTarifa().compareTo(BigDecimal.ZERO) == 0) {
                iva.setCodigoPorcentaje("0");
                iva.setValor(0);
            } else {
                iva.setValor((facturaDetalleImpuesto.getFdiBaseImponible().multiply(institucion.getInsIva())).doubleValue());
            }
            iva.setTarifa(facturaDetalleImpuesto.getFdiTarifa().doubleValue());
            impuestosDetalle.add(iva);
            
            add1.setImpuestos(impuestosDetalle);
            listaBfacturaDetalle.add(add1);
            //impuestos total factura

            if (facturaDetalleImpuesto.getFdiTarifa().compareTo(BigDecimal.ZERO) == 0) {
                baseImponibleImpFac0 = baseImponibleImpFac0.add(facturaDetalleImpuesto.getFdiBaseImponible());
            } else {
                baseImponibleImpFac12 = baseImponibleImpFac12.add(facturaDetalleImpuesto.getFdiBaseImponible());
            }
            
        }

        //pagos
        for(FacturaFormaPago pago: facturaAux.getFacturaFormaPagoCollection()){
        BPago addp1 = new BPago();
        addp1.setCodigoFormaPagoSRI(pago.getTpfId().getTpfCodigo());//efectivo
        addp1.setIdTipoFormaPago(pago.getTpfId().getTpfId().intValue());
        addp1.setPlazo("15");
        addp1.setValor(pago.getFfpValor().doubleValue());
        addp1.setUnidadTiempo("dias");
        bPago.add(addp1);
        }
        //BFACTURA
        BFactura bFactura = new BFactura();
        bFactura.setCliente(bCliente);
        bFactura.setDetalles(listaBfacturaDetalle);
        bFactura.setFechaEmision(factura.getFacFHR());
        bFactura.setNumeroComprobante(factura.getFacNumero());
        bFactura.setPagos(bPago);
        bFactura.setSubTotal(factura.getFacSubTotal().doubleValue());
        bFactura.setTotal(factura.getFacTotal().doubleValue());
        bFactura.setValorDescuento(factura.getFacDescuento().doubleValue());
        bFactura.setValorIva(factura.getFacIva().doubleValue());

        //Impuestos factura
        List<BImpuesto> impuestosFactura = new ArrayList<BImpuesto>();
        if (baseImponibleImpFac0.compareTo(BigDecimal.ZERO) > 0) {
            BImpuesto impuesto0 = new BImpuesto();
            impuesto0.setCodigoSri("2");
            impuesto0.setCodigoPorcentaje("0");
            impuesto0.setBaseImponible(baseImponibleImpFac0.doubleValue());
            impuesto0.setValor(0);
            impuesto0.setTarifa(0);
            impuestosFactura.add(impuesto0);
            System.out.print("BASE IMPONIBLE TOTAL 0 " + impuesto0.getBaseImponible());
        }
        if (baseImponibleImpFac12.compareTo(BigDecimal.ZERO) > 0) {
            BImpuesto impuesto12 = new BImpuesto();
            impuesto12.setCodigoSri("2");
            impuesto12.setCodigoPorcentaje("2");
            impuesto12.setBaseImponible(baseImponibleImpFac12.doubleValue());
            impuesto12.setValor(baseImponibleImpFac12.multiply(institucion.getInsIva()).setScale(2, BigDecimal.ROUND_DOWN).doubleValue());
            impuesto12.setTarifa(12);
            impuestosFactura.add(impuesto12);
            System.out.print("BASE IMPONIBLE TOTAL 12 " + impuesto12.getBaseImponible());
        }
        
        bFactura.setImpuestos(impuestosFactura);
        //parametro match
        ParametroMatch parametro_Match = new ParametroMatch();
        parametro_Match.setEmpresa(bEmpresa);
        parametro_Match.setCodigoContribuyenteEspecial("586");
        parametro_Match.setDireccionEstablecimiento(bEmpresa.getDireccion());
        parametro_Match.setNombreEmpleado("EMPLEADO TEST");
        parametro_Match.setNumeroDecimales(6);
        parametro_Match.setNumeroDecimalesRedondeo(2);
        Boolean ambiente = Boolean.TRUE;
        if ("PRUEBAS".equals(institucion.getInsAmbiente())) {
            ambiente = Boolean.FALSE;
        }
        parametro_Match.setProduccion(ambiente);

        //el servicio match
        ServicioMatch servicio_Match = new ServicioMatch();
        servicio_Match.setParametroMatch(parametro_Match);

        //la respuesta del match 
        //se utiliza para convertir toda la informacion a XML y
        //para capturar la clave de acceso
        RespuestaMatch respuesta_Match = new RespuestaMatch();
        respuesta_Match = servicio_Match.empatar_Factura(bFactura);
        String xmlSinFirma = servicio_Match.generarXml_Factura(respuesta_Match);
        bFactura.setXml(xmlSinFirma);
        bFactura.setClaveAcceso(respuesta_Match.getFacturaCompel().getInfoTributaria().getClaveAcceso());

        //la configuracion
        //se puede llenar la informacion de esta clase con informacion de base de datos
        //caso contrario utiliza valores por default (quemados)
        conf_MASTER.getConf_REPO().setPath_Firma(pathDocEle.getPdeFirma());
        conf_MASTER.getConf_REPO().setPath_Certificado(pathDocEle.getPdeCertificado());
        conf_MASTER.getConf_REPO().setPath_Repositorio_Temporales(pathDocEle.getPdeRepTemporal());
        conf_MASTER.getConf_WS().setUrlServicioWeb_Autorizacion(pathDocEle.getPdeWsAutorizacion());
        conf_MASTER.getConf_WS().setUrlServicioWeb_Recepcion(pathDocEle.getPdeWsRecepcion());
        conf_MASTER.getConf_WS().setNameSpaceURI_Autorizacion(pathDocEle.getPdeNameSpaceURIAutorizacion());
        conf_MASTER.getConf_WS().setNameSpaceURI_Recepcion(pathDocEle.getPdeNameSpaceURIRecepcion());
        conf_MASTER.getConf_WS().setNombreMetodo_Autorizacion(pathDocEle.getPdeMetodoAutorizacion());
        conf_MASTER.getConf_WS().setNombreMetodo_Recepcion(pathDocEle.getPdeMetodoRecepcion());

        //la transmision
        //esta clase se encarga de generar
        Transmisor transmisor = new Transmisor(conf_MASTER);
        transmisor.transmitirFactura(bFactura);
        //respuestaRecepcion=transmisor.getRespuestaRecepcion();
        //factura.setFacXml(bFactura.getXml());
        factura.setFacClaveAcceso(bFactura.getClaveAcceso());
        String identificador = enviarComprobante(xmlSinFirma, factura.getFacNumero(), factura,pathDocEle);
        try {
            if (identificador.equals("43")) {
                consultarAutorizacion(factura, xmlSinFirma);
            }
        } catch (Exception e) {
            Config.log.print(e);
        }
    }
    
    public void consultarAutorizacion(Factura factura, String xmlSinFirma) throws Exception {
        try {
            
            String codigoSRI = "01";
            //indica si se envia o no email
            conf_MASTER.setEnviarEmail(false);
            conf_MASTER.getClase_EMAIL().setEmail_Destinatario(factura.getFacPerEmail());
            conf_MASTER.getClase_EMAIL().setNumeroComprobante(factura.getFacNumero());
            conf_MASTER.getClase_EMAIL().setClaveAcceso(factura.getFacClaveAcceso());
            conf_MASTER.getClase_EMAIL().setCodigoComprobante(codigoSRI);
            //la consulta
            //esta clase se encarga de consultar la autorizacion
            ConsultaAutorizacion servicioConsulta = new ConsultaAutorizacion(conf_MASTER);
            RespuestaAutorizacion consultar_Autorizacion = servicioConsulta.consultar_Autorizacion();
            System.out.println(consultar_Autorizacion.getEstado());
            if (consultar_Autorizacion.getListaMensajes().size() > 0) {
                guardarMensaje(consultar_Autorizacion.getListaMensajes(), factura);
            }
            factura.setFacRespuestaSri(consultar_Autorizacion.getEstado());
            if(facturaServicio==null){
                facturaServicio=new FacturaServicio();
            }
            facturaServicio.guardar(factura);
            
           String mensaje="Estimado Cliente: ".concat(factura.getFacPerNombre()).concat(" ").concat(factura.getFacPerApellidoPaterno().concat("\n").concat("Nos complace informarle que su documento electrónico ha sido generado."));
            enviarEmailUtil.enviaEmail(pathDocEle,factura.getFacPerEmail(),factura.getFacNumero(),mensaje ,factura.getFacXml(),factura.getFacClaveAcceso());
        } catch (Exception e) {
            Config.log.print(e);
        }
    }
    
    public void guardarMensaje(List mensaje, Factura factura) throws ServicioExcepcion {
        MensajeSri mensajeSri = new MensajeSri();
        Iterator<MensajeSri> it = mensaje.iterator();
        if (factura.getFacClaveAcceso() == null) {
            factura.setFacClaveAcceso("");
        }
        while (it.hasNext()) {
            MensajeSri next = it.next();
            EMensaje emensaje = new EMensaje();
            emensaje.setEMsgAutorizacion(factura.getFacClaveAcceso());
            emensaje.setEMsgClave(factura.getFacClaveAcceso());
            emensaje.setEMsgCodigoSri(next.getIdentificador());
            emensaje.setEMsgDetalle(next.getMensaje());
            emensaje.setEMsgDocumento("FC");
            if (next.getTipo().equals("ERROR")) {
                emensaje.setEMsgErrorSri("S");
            } else {
                emensaje.setEMsgErrorSri("N");
            }
            emensaje.setEMsgEstado("SRI");
            emensaje.setEMsgFHR(new Date());
            emensaje.setEMsgMensaje("AUTORIZACIONES RESPUESTA");
            emensaje.setEMsgNumero(factura.getFacNumero().substring(8, 17));
            emensaje.setEMsgPrograma("SRI");
            emensaje.setEMsgSerie(factura.getFacNumero().substring(0, 7));
            emensaje.setEMsgTipo(next.getTipo());
            emensaje.setEMsgUser(factura.getFacUser());
            if(eMensajeServicio==null){
                eMensajeServicio=new EMensajeServicio();
            }
            eMensajeServicio.guardar(emensaje);
        }
    }
    
    private String enviarComprobante(String xml, String numeroComprobante, Factura factura,PathDocEle pathDocEle) throws IOException, Exception {
        System.out.println("enviarComprobante");
        ConfiguracionRepositorio conf_REPO = conf_MASTER.getConf_REPO();
        ConfiguracionWS conf_WS = conf_MASTER.getConf_WS();
        
        byte[] xml_bytes = xml.getBytes();
        String nombreFirma = conf_REPO.getNombreFirma();
        String passFirma = conf_REPO.getPassFirma();
        String nombreArchivo = numeroComprobante;
        char[] password = passFirma.toCharArray();
        String path_ctr = pathDocEle.getPdeCertificado();
        String path_Firma = pathDocEle.getPdeFirma();
       

        //cambiamos la propiedad del sistema para que reconozca la firma
        System.setProperty("javax.net.ssl.keyStore", path_ctr);
        System.setProperty("javax.net.ssl.keyStorePassword", "changeit");
        System.setProperty("javax.net.ssl.trustStore", path_ctr);
        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");

        //firma de comprobante
        FirmaService util_Firma = new FirmaService();
        util_Firma.setPathTemporales(pathDocEle.getPdeRepTemporal());
        
        File fila_firma = new File(path_Firma);
        if (!fila_firma.exists()) {
            System.out.println(Util.aString("path_firma=", path_Firma, " no existe"));
            return null;
        }
        byte[] xmlFirmado = util_Firma.firmarDocumento(xml_bytes, nombreFirma, password, nombreArchivo, path_Firma, false);
        String xmlFirmadoString = new String(xmlFirmado, "UTF-8");

        //enviar comprobante firmado
        String pathTemporales = pathDocEle.getPdeRepTemporal();
        String path_ArchivoSoap_Recepcion = Util.aString(pathTemporales, numeroComprobante, "_REC_SOAP", ".xml");
        String path_Recepcion_Response = Util.aString(pathTemporales, numeroComprobante, "_REC_SOAP_RESPONSE", ".xml");
        factura.setFacXml(xmlFirmadoString);
        //creamos el mensaje SOAP
        SOAPService servicioSOAP = new SOAPService(conf_WS);
        servicioSOAP.crear_SOAP_Recepcion_Contenido(xmlFirmadoString, path_ArchivoSoap_Recepcion);

        //enviamos el mensaje SOAP
        String xml_Resultante = servicioSOAP.enviar_SOAP_Recepcion(path_ArchivoSoap_Recepcion);

        //leemos el xml resultante
        RespuestaRecepcion get = servicioSOAP.leer_Resultante_Recepcion(xml_Resultante, path_Recepcion_Response);
        get.setXmlResponse(xml_Resultante);
        
        String identificador = guardarMensajeEnvioSri(get.getListaMensajes(), factura);
        //borrar temporales
        Util.borrarArchivo(path_ArchivoSoap_Recepcion);
        
        return identificador;
    }
    
    public String guardarMensajeEnvioSri(List mensaje, Factura factura) throws ServicioExcepcion {
        String identificador = null;
        try {
            if (factura.getFacClaveAcceso() == null) {
                factura.setFacClaveAcceso("");
            }
            MensajeSri mensajeSri = new MensajeSri();
            Iterator<MensajeSri> it = mensaje.iterator();
            
            while (it.hasNext()) {
                MensajeSri next = it.next();
                EMensaje emensaje = new EMensaje();
                emensaje.setEMsgAutorizacion(factura.getFacClaveAcceso());
                emensaje.setEMsgClave(factura.getFacClaveAcceso());
                emensaje.setEMsgCodigoSri(next.getIdentificador());
                emensaje.setEMsgDetalle(next.getMensaje());
                emensaje.setEMsgDocumento("FC");
                if (next.getIdentificador().equals("43")) {
                    emensaje.setEMsgErrorSri("N");
                    emensaje.setEMsgTipo("INFORMACION");
                } else {
                    emensaje.setEMsgErrorSri("S");
                    emensaje.setEMsgTipo("ERROR");
                }
                emensaje.setEMsgEstado("SRI");
                emensaje.setEMsgFHR(new Date());
                emensaje.setEMsgMensaje("AUTORIZACIONES RESPUESTA");
                emensaje.setEMsgNumero(factura.getFacNumero().substring(8, 17));
                emensaje.setEMsgPrograma("SRI");
                emensaje.setEMsgSerie(factura.getFacNumero().substring(0, 7));
                
                emensaje.setEMsgUser("Proceso");
                identificador = next.getIdentificador();
                if(eMensajeServicio==null){
                    eMensajeServicio=new EMensajeServicio();
                }
                eMensajeServicio.guardar(emensaje);
            }
        } catch (Exception e) {
            Config.log.print(e);
        }
        return identificador;
    }
     public String verificarTildes(String cadena){
       if(cadena!=null){
        String cadenaNormalize = Normalizer.normalize(cadena, Normalizer.Form.NFD);   
        String cadenaSinAcentos = cadenaNormalize.replaceAll("[^\\p{ASCII}]", "");
        return cadena=cadenaSinAcentos;
        } 
       return null;
    }
}
