package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.dao.ActaRazonDao;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.RazonDao;
import com.rm.empresarial.dao.RazonNuevaDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.ActaRazon;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Razon;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioDigital;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteSuspension;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.CertificadoServicio;
import com.rm.empresarial.servicio.FacturaDetalleServicio;
import com.rm.empresarial.servicio.GravamenDetalleServicio;
import com.rm.empresarial.servicio.LinderoServicio;
import com.rm.empresarial.servicio.MarginacionServicio;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.PropietarioServicio;
import com.rm.empresarial.servicio.RazonNuevaServicio;
import com.rm.empresarial.servicio.RepertorioDigitalServicio;
import com.rm.empresarial.servicio.TramiteSuspensionServicio;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorIndiceConsulta")
@ViewScoped
public class ControladorIndiceConsulta implements Serializable {

    @EJB
    private PersonaServicio servicioPersona;
    @EJB
    private TramiteDetalleDao tramiteDetalleDao;
    @EJB
    private RepertorioPropiedadDao repertorioPropiedadDao;
    @EJB
    private RepertorioDao repertorioDao;
    @EJB
    private PropietarioServicio servicioPropietario;
    @EJB
    private RepertorioPropiedadDao servicioRepertorioPropiedad;
    @EJB
    private ActaRazonDao servicioActaRazon;
    @EJB
    private ActaServicio servicioActa;
    @EJB
    private CertificadoServicio servicioCertificado;
    @EJB
    private GravamenDao servicioGravamen;
    @EJB
    private GravamenDetalleServicio servicioGravamenDetalle;
    @EJB
    private MarginacionServicio servicioMarginacion;
    @EJB
    private PropiedadServicio servicioPropiedad;
    @EJB
    private RepertorioDigitalServicio servicioRepertorioDigital;
    @EJB
    private ParametroPathServicio servicioParametroPath;
    @EJB
    private FacturaDetalleServicio servicioFacturaDetalle;
    @EJB
    private LinderoServicio servicioLindero;
    @EJB
    private RazonNuevaDao servicioRazon;
    @EJB
    private TramiteSuspensionServicio servicioTramiteSuspension;

    @Inject
    @Getter
    @Setter
    private ControladorGenerarRide controladorGenerarRide;

    @Getter
    @Setter
    private Propiedad propiedadSeleccionada;
    @Getter
    @Setter
    private TramiteDetalle tramiteSeleccionado;
    @Getter
    @Setter
    private Acta actaSeleccionada;
    @Getter
    @Setter
    private Razon razon;
    @Getter
    @Setter
    private Certificado certificadoSeleccionado;
    @Getter
    @Setter
    private Propietario propietarioSeleccionado;
    @Getter
    @Setter
    private RepertorioDigital repertorioDigitalSeleccionado;
    @Getter
    @Setter
    private ParametroPath parametroPath;
    @Getter
    @Setter
    private Factura facturaSeleccionada;
    @Getter
    @Setter
    private Propiedad propiedadConsultada;
    @Getter
    @Setter
    private Propiedad matriculaSeleccionada;
    @Getter
    @Setter
    private Gravamen gravamenConsultado;

    @Getter
    @Setter
    private List<Propiedad> listaDePropiedades;
    @Getter
    @Setter
    private List<Integer> listaRepertorioPropiedades;
    @Getter
    @Setter
    private List<TramiteDetalle> listaPersonas;
    @Getter
    @Setter
    private List<TramiteSuspension> listaSuspensiones;
    @Getter
    @Setter
    private List<TramiteSuspension> listaSusAux;
    @Getter
    @Setter
    private List<Propietario> listaPropietarios;
    @Getter
    @Setter
    private List<Propietario> listaPropietariosPropS;
    @Getter
    @Setter
    private List<Propietario> listaMatriculasdePropietario;
    @Getter
    @Setter
    private List<RepertorioPropiedad> listaRepPropiedad;
    @Getter
    @Setter
    private Repertorio repertorio;
    @Getter
    @Setter
    private List<Acta> listaActas;
    @Getter
    @Setter
    private List<Certificado> listaCertificados;
    @Getter
    @Setter
    private List<Gravamen> listaGravamen;
    @Getter
    @Setter
    private List<Marginacion> listaMarginacion;
    @Getter
    @Setter
    private List<Marginacion> listaMarginacionNuevos;
    @Getter
    @Setter
    private List<RepertorioDigital> listaRepertoriosDigi;
    @Getter
    @Setter
    private List<FacturaDetalle> listaFacturaDetalle;
    @Getter
    @Setter
    private List<Gravamen> listaPropiedadesBuscadasEnGravamen;
    @Getter
    @Setter
    private List<Lindero> listaLinderos;
    @Getter
    @Setter
    private List<String> listaTipoTtrRepertorio;
    @Getter
    @Setter
    private String gravamenesConsultados;
    @Getter
    @Setter
    private List<String> listaGravamenesExistentes;
    @Getter
    @Setter
    private List<String> listaTraDetalleDesc;
    @Getter
    @Setter
    private List<String> listaContratoDesc;
    @Getter
    @Setter
    private List<String> listaGravamenesPersonas;
    @Getter
    @Setter
    private List<RepertorioPropiedad> repertorioPropiedad;

    @Getter
    @Setter
    private String identificacion;
    @Getter
    @Setter
    private String predio;
    @Getter
    @Setter
    private String catastro;
    @Getter
    @Setter
    private String numMatricula;
    @Getter
    @Setter
    private String numRepertorio;
    @Getter
    @Setter
    private String estadoWizard;
    @Getter
    @Setter
    private Long numRepertorioSeleccionado;
    @Getter
    @Setter
    private String tipoRepertorio;
    @Getter
    @Setter
    private String gravamenPersona;
    @Getter
    @Setter
    private String existenciaGravamen;

    @PostConstruct
    public void init() {
        listaDePropiedades = new ArrayList<>();
        listaRepertorioPropiedades = new ArrayList<>();
        propiedadSeleccionada = new Propiedad();
        tramiteSeleccionado = new TramiteDetalle();
        actaSeleccionada = new Acta();
        certificadoSeleccionado = new Certificado();
        propietarioSeleccionado = new Propietario();
        repertorioDigitalSeleccionado = new RepertorioDigital();
        facturaSeleccionada = new Factura();
        propiedadConsultada = new Propiedad();
        matriculaSeleccionada = new Propiedad();
        listaPersonas = new ArrayList<>();
        listaPropietarios = new ArrayList<>();
        listaPropietariosPropS = new ArrayList<>();
        listaMatriculasdePropietario = new ArrayList<>();
        listaRepPropiedad = new ArrayList<>();
        listaActas = new ArrayList<>();
        listaCertificados = new ArrayList<>();
        listaGravamen = new ArrayList<>();
        listaRepertoriosDigi = new ArrayList<>();
        listaFacturaDetalle = new ArrayList<>();
        listaSusAux = new ArrayList<>();
        listaSuspensiones = new ArrayList<>();
        listaPropiedadesBuscadasEnGravamen = new ArrayList<>();
        listaLinderos = new ArrayList<>();
        listaTipoTtrRepertorio = new ArrayList<>();
        listaGravamenesExistentes = new ArrayList<>();
        listaGravamenesPersonas = new ArrayList<>();
        identificacion = new String();
        numRepertorio = new String();
        numMatricula = new String();
        predio = new String();
        catastro = new String();
        estadoWizard = "false";
    }

    public void procesarConsulta() throws ServicioExcepcion {
        listaDePropiedades = new ArrayList<>();
        listaRepertorioPropiedades = new ArrayList<>();
        listaTipoTtrRepertorio = new ArrayList<>();
        if (numMatricula.isEmpty() && numRepertorio.isEmpty() && predio.isEmpty() && catastro.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese almenos un parámetro de busqueda", ""));
        } else {
            if (!numMatricula.isEmpty()) {
                numMatricula = numMatricula + "%";
            } else {
                numMatricula = numMatricula + "^";
            }
            if (!numRepertorio.isEmpty()) {
                numRepertorio = numRepertorio + "%";
            } else {
                numRepertorio = numRepertorio + "^";
            }
            if (!predio.isEmpty()) {
                predio = predio + "%";
            } else {
                predio = predio + "^";
            }
            if (!catastro.isEmpty()) {
                catastro = catastro + "%";
            } else {
                catastro = catastro + "^";
            }
            setListaDePropiedades(servicioPropiedad.listarPropiedadesPorLike(numMatricula, numRepertorio, predio, catastro));
            if (!numRepertorio.equals("^")) {
                setListaRepertorioPropiedades(servicioPropiedad.listarRepertorioPropiedadesPorLike(numMatricula, numRepertorio, predio, catastro));
                setListaTipoTtrRepertorio(servicioPropiedad.listarTtrTipoRepertorioPropiedadesPorLike(numMatricula, numRepertorio, predio, catastro));
            } else {
                for (Propiedad p : listaDePropiedades) {
                    listaRepertorioPropiedades.add(0);
                    listaTipoTtrRepertorio.add("");
                }
            }

            int i = 0;
            listaGravamenesExistentes = new ArrayList<>();
            if (!numRepertorio.equals("^")) {
                for (Propiedad p : listaDePropiedades) {
                    setGravamenesConsultados(servicioGravamen.obtenerPorMatriculayRepertorio(p.getPrdMatricula(), listaRepertorioPropiedades.get(i)));
                    if (!gravamenesConsultados.isEmpty()) {
                        listaGravamenesExistentes.add(gravamenesConsultados);
                    } else {
                        listaGravamenesExistentes.add("");
                    }
                    i++;
                }
            } else {
                for (Propiedad p : listaDePropiedades) {
                    setGravamenesConsultados(servicioGravamen.obtenerPorMatricula(p.getPrdMatricula()));
                    if (!gravamenesConsultados.isEmpty()) {
                        listaGravamenesExistentes.add(gravamenesConsultados);
                    } else {
                        listaGravamenesExistentes.add("");
                    }
                    i++;
                }
            }
        }

    }

    public void listarDatosPropiedad() throws ServicioExcepcion {
        try {
            listaCertificados = new ArrayList<>();
            listaGravamen = new ArrayList<>();
            listaPropietariosPropS = new ArrayList<>();
            listaActas = new ArrayList<>();
            listaFacturaDetalle = new ArrayList<>();
            listaTraDetalleDesc = new ArrayList<>();
            listaContratoDesc = new ArrayList<>();
            listaSuspensiones = new ArrayList<>();
            listaSusAux = new ArrayList<>();
            if (!numRepertorio.isEmpty()) {
                setListaCertificados(servicioCertificado.listarPorPredio_Y_Catastro(propiedadSeleccionada.getPrdPredio(), propiedadSeleccionada.getPrdCatastro()));
                setListaGravamen(servicioGravamen.buscarPorMatriculaYRepertorio(numRepertorioSeleccionado.intValue(), propiedadSeleccionada.getPrdMatricula()));
                setListaPropietariosPropS(servicioPropietario.listarPornumRepYMatricula(propiedadSeleccionada.getPrdMatricula(), numRepertorioSeleccionado.intValue()));
                setListaActas(servicioActa.listarActasPorRepertorioyNumMatricula(numRepertorioSeleccionado.intValue(), propiedadSeleccionada.getPrdMatricula()));
                setListaRepertoriosDigi(servicioRepertorioDigital.listarRepDigPorNumRepertorio(numRepertorioSeleccionado));
                setListaTraDetalleDesc(servicioPropietario.listarTraDescPornumRepYMatricula(propiedadSeleccionada.getPrdMatricula(), numRepertorioSeleccionado.intValue()));

                setListaContratoDesc(servicioPropietario.listarContratoPornumRepYMatricula(propiedadSeleccionada.getPrdMatricula(), numRepertorioSeleccionado.intValue()));

                listaGravamenesPersonas = new ArrayList<>();
                for (Propietario p : listaPropietariosPropS) {
                    setGravamenPersona(servicioGravamenDetalle.buscarPorPersona(p.getPerId().getPerId()));
                    if (!gravamenPersona.isEmpty()) {
                        listaGravamenesPersonas.add(gravamenPersona);
                    } else {
                        listaGravamenesPersonas.add("");
                    }
                }
            } else {
                setRepertorioPropiedad(repertorioPropiedadDao.listarRepertorioPorNumMatricula(propiedadSeleccionada.getPrdMatricula()));
                setListaCertificados(servicioCertificado.listarPorPredio_Y_Catastro(propiedadSeleccionada.getPrdPredio(), propiedadSeleccionada.getPrdCatastro()));
                setListaGravamen(servicioGravamen.buscarporMatricula(propiedadSeleccionada.getPrdMatricula()));
                setListaPropietariosPropS(servicioPropietario.listarPornumMatricula(propiedadSeleccionada.getPrdMatricula()));
                setListaActas(servicioActa.listarActasPorNumMatricula(propiedadSeleccionada.getPrdMatricula()));

                List<RepertorioPropiedad> listaPropiedadRep = servicioRepertorioPropiedad.listarRepertorioPorNumMatricula(propiedadSeleccionada.getPrdMatricula());
                for (RepertorioPropiedad pro : listaPropiedadRep) {
                    List<RepertorioDigital> listAux = servicioRepertorioDigital.listarRepDigPorNumRepertorio(pro.getRepNumero().getRepNumero());
                    for (RepertorioDigital repdig : listAux) {
                        listaRepertoriosDigi.add(repdig);
                    }
                }
                setListaTraDetalleDesc(servicioPropietario.listarTraDescPornumMatricula(propiedadSeleccionada.getPrdMatricula()));
                setListaContratoDesc(servicioPropietario.listarContratoPornumMatricula(propiedadSeleccionada.getPrdMatricula()));
                listaGravamenesPersonas = new ArrayList<>();
                for (Propietario p : listaPropietariosPropS) {
                    setGravamenPersona(servicioGravamenDetalle.buscarPorPersona(p.getPerId().getPerId()));
                    if (!gravamenPersona.isEmpty()) {
                        listaGravamenesPersonas.add(gravamenPersona);
                    } else {
                        listaGravamenesPersonas.add("");
                    }
                }
                for (RepertorioPropiedad repertorioProp : repertorioPropiedad) {
                    setRepertorio(repertorioDao.encontrarRepertorioPorId(repertorioProp.getRepNumero().getRepNumero().toString()));
                    setListaSusAux(servicioTramiteSuspension.buscarTramitesSuspensionPorNumTram(repertorio.getTraNumero().getTraNumero()));

                    for (TramiteSuspension tramiteSuspension : listaSusAux) {
                        listaSuspensiones.add(tramiteSuspension);
                    }

                }
            }
        } catch (Exception e) {

        }
        setEstadoWizard("true");
    }

    public void obtenerRazonActa() {
        System.out.print("Acta seleccionada:" + actaSeleccionada.getActNumero());
        setRazon(servicioRazon.obtenerRazonPornumTramite(actaSeleccionada.getRepNumero().getTraNumero().getTraNumero()));
    }

    public void consultarMarginaciones() {
        System.out.print("Acta seleccionada:" + actaSeleccionada.getActNumero());
        setListaMarginacion(servicioMarginacion.listarPor_NumActa(actaSeleccionada.getActNumero()));
    }

    public void abrirArchivo() {
        String dirPrincipal = "";
        String subDirectorio = "repertorio\\";
//        String url = "";
        String nombreArchivo = "";
        String contentType = "application/pdf";
        String extension = "pdf";
        String path = "";
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();

        try {
            //OBTENGO DIRECCION 
            parametroPath = servicioParametroPath.obtenerParamPorTipoPorFecha("DIG", Calendar.getInstance().getTime());
            dirPrincipal = parametroPath.getPrpPath();
            nombreArchivo = repertorioDigitalSeleccionado.getRtdNombreArchivo();
            subDirectorio = repertorioDigitalSeleccionado.getRtdPath();
//            url = "file:///" + dirPrincipal + subDirectorio + nombreArchivo + "." + extension;
            path = dirPrincipal + subDirectorio + nombreArchivo + "." + extension;

            Path file = Paths.get(path);
            InputStream input = null;
//            BufferedOutputStream bos = null;
            OutputStream output = ec.getResponseOutputStream();
            File file2 = new File(path);
            Long tamaño = file2.length();
            input = new BufferedInputStream(Files.newInputStream(file));

//FORMATO RESPUESTA
            ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
            ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
            ec.setResponseContentLength(tamaño.intValue()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
            ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + nombreArchivo + "." + extension + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

//LEER EXISTENTE Y MODIFICAR
//            Document document = new Document();
//            PdfReader reader = new PdfReader(path);
//            document.open();
//            System.out.println("# paginas: " + document.getPageNumber());
//            document.close();
            //ESCRIBIR NUEVO
//                        Document document = new Document();
//            FileOutputStream fos = new FileOutputStream(path);
//            PdfWriter writer = PdfWriter.getInstance(document, fos);
//            document.open();
//            document.add(new Paragraph("Hola mundo"));
//            document.close();
//            writer.close();
//ESCRIBIR A LA SALIDA OUTPUT
            byte[] bytesBuffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = input.read(bytesBuffer)) > 0) {
                output.write(bytesBuffer, 0, bytesRead);
            }

//            output.write(input);
            fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.

            //REPERTORIO DIGITAL
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage("Error al cargar el archivo");
        }
    }

    public void generarRide() throws InterruptedException, IOException {

        String contentType = "application/pdf";
        String numeroFac = getFacturaSeleccionada().getFacNumero();
        String claveAcce = getFacturaSeleccionada().getFacClaveAcceso();
        String fechaAutrizacion = "2018-12-06 18:48:54";
        String xml = getFacturaSeleccionada().getFacXml();
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            HttpServletResponse response = (HttpServletResponse) ec.getResponse();
        String path = controladorGenerarRide.generarRide(numeroFac, claveAcce, fechaAutrizacion, xml);

        Path file = Paths.get(path);
        InputStream input = null;
        OutputStream output = ec.getResponseOutputStream();
        File file2 = new File(path);
        Long tamaño = file2.length();
        input = new BufferedInputStream(Files.newInputStream(file));

//FORMATO RESPUESTA
        ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        ec.setResponseContentType(contentType); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
        ec.setResponseContentLength(tamaño.intValue()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
        ec.setResponseHeader("Content-Disposition", "inline; filename=\"" + "xxx" + "." + "pdf" + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

//ESCRIBIR A LA SALIDA OUTPUT
        byte[] bytesBuffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = input.read(bytesBuffer)) > 0) {
            output.write(bytesBuffer, 0, bytesRead);
        }

//            output.write(input);
        fc.responseComplete(); // Important! Otherwise JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.

        //REPERTORIO DIGITAL
    }

    public void consultarInformacionPropiedad() throws ServicioExcepcion {
        setPropiedadConsultada(servicioPropiedad.obtenerPorMatricula(propiedadSeleccionada.getPrdMatricula()));
        setListaLinderos(servicioLindero.listarPorNumMatricula(propiedadSeleccionada.getPrdMatricula()));
    }

    public void redirect() {
        propiedadSeleccionada = new Propiedad();
        tramiteSeleccionado = new TramiteDetalle();
        actaSeleccionada = new Acta();
        certificadoSeleccionado = new Certificado();
        propietarioSeleccionado = new Propietario();
        repertorioDigitalSeleccionado = new RepertorioDigital();
        facturaSeleccionada = new Factura();
        propiedadConsultada = new Propiedad();
        matriculaSeleccionada = new Propiedad();
        listaPropietarios = new ArrayList<>();
        listaPropietariosPropS = new ArrayList<>();
        listaMatriculasdePropietario = new ArrayList<>();
        listaRepPropiedad = new ArrayList<>();
        listaActas = new ArrayList<>();
        listaCertificados = new ArrayList<>();
        listaGravamen = new ArrayList<>();
        listaRepertoriosDigi = new ArrayList<>();
        listaFacturaDetalle = new ArrayList<>();
        listaPropiedadesBuscadasEnGravamen = new ArrayList<>();
        listaLinderos = new ArrayList<>();
        identificacion = new String();
        predio = new String();
        catastro = new String();
        numMatricula = new String();
        numRepertorio = new String();
        estadoWizard = "false";
    }

}
