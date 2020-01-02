package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.dao.ActaRazonDao;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.RazonNuevaDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.ActaRazon;
import com.rm.empresarial.modelo.Certificado;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.GravamenDetalle;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Marginacion;
import com.rm.empresarial.modelo.ParametroPath;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Razon;
import com.rm.empresarial.modelo.RepertorioDigital;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
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
import com.rm.empresarial.servicio.TramiteServicio;
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
@Named(value = "controladorIndicesConsultaPersonas")
@ViewScoped
public class ControladorIndicesConsultaPersonas implements Serializable {

    @EJB
    private PersonaServicio servicioPersona;
    @EJB
    private PropietarioServicio servicioPropietario;
    @EJB
    private RepertorioPropiedadDao servicioRepertorioPropiedad;
    @EJB
    private TramiteDetalleDao tramiteDetalleDao;
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
    private TramiteServicio servicioTramite;
    @EJB
    private RazonNuevaDao servicioRazon;

    @Inject
    @Getter
    @Setter
    private ControladorGenerarRide controladorGenerarRide;

    @Getter
    @Setter
    private Propiedad propiedadSeleccionada;
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
    private Long numRepertorioSeleccionado;
    @Getter
    @Setter
    private Propiedad matriculaSeleccionada;
    @Getter
    @Setter
    private GravamenDetalle personaConGraDetalle;
    @Getter
    @Setter
    private Propietario personaConPropiedad;
    @Getter
    @Setter
    private Persona personaSeleccionada;

    @Getter
    @Setter
    private List<Persona> listaPersonas;
    @Getter
    @Setter
    private List<Persona> listaPersonasConPropiedad;
    @Getter
    @Setter
    private List<Persona> listaPersonasEnGraDetalle;
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
    private List<Propiedad> listaDePropiedades;
    @Getter
    @Setter
    private List<Lindero> listaLinderos;
    @Getter
    @Setter
    private List<String> listaGravamenesExistentes;
    @Getter
    @Setter
    private List<String> listaGravamenesConsultados;
    @Getter
    @Setter
    private List<String> listaGravamenesExistentes1;
    @Getter
    @Setter
    private String GravamenesConsultados1;
    @Getter
    @Setter
    private List<String> listaTipoTtrRepertorio;
    @Getter
    @Setter
    private List<String> listaGravamenesPersonas;
    @Getter
    @Setter
    private List<String> listaTraDetalleDesc;
    @Getter
    @Setter
    private List<String> listaContratoDetalleDesc;
    @Getter
    @Setter
    private List<TramiteDetalle> listaSuspensiones;

    @Getter
    @Setter
    private String gravamenConsultado;
    @Getter
    @Setter
    private String identificacion;
    @Getter
    @Setter
    private String apellidoPaterno;
    @Getter
    @Setter
    private String apellidoMaterno;
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private String numRepertorio;
    @Getter
    @Setter
    private String estadoWizard;
    @Getter
    @Setter
    private int numMatricula;
    @Getter
    @Setter
    private String tipoRepertorio;
    @Getter
    @Setter
    private String gravamenPersona;
    @Getter
    @Setter
    private String estadoTab;
    @Getter
    @Setter
    private String estadobtnRegresar;
    @Getter
    @Setter
    private String gravamenPersonaSeleccionado;
    @Getter
    @Setter
    private String renderTab;

    public ControladorIndicesConsultaPersonas() {
        propiedadSeleccionada = new Propiedad();
        actaSeleccionada = new Acta();
        certificadoSeleccionado = new Certificado();
        propietarioSeleccionado = new Propietario();
        repertorioDigitalSeleccionado = new RepertorioDigital();
        facturaSeleccionada = new Factura();
        propiedadConsultada = new Propiedad();
        matriculaSeleccionada = new Propiedad();
        personaConGraDetalle = new GravamenDetalle();
        personaConPropiedad = new Propietario();
        personaSeleccionada = new Persona();
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
        listaPropiedadesBuscadasEnGravamen = new ArrayList<>();
        listaDePropiedades = new ArrayList<>();
        listaLinderos = new ArrayList<>();
        listaGravamenesExistentes = new ArrayList<>();
        listaGravamenesConsultados = new ArrayList<>();
        listaGravamenesExistentes1 = new ArrayList<>();
        listaTipoTtrRepertorio = new ArrayList<>();
        listaGravamenesPersonas = new ArrayList<>();
        listaPersonasConPropiedad = new ArrayList<>();
        listaPersonasEnGraDetalle = new ArrayList<>();
        listaSuspensiones = new ArrayList<>();
        identificacion = new String();      
        apellidoPaterno = new String();
        apellidoMaterno = new String();
        nombre = new String();
        numRepertorio = new String();
        estadoWizard = "false";
        estadoTab = "true";
        estadobtnRegresar = "false";
        renderTab = "true";
    }

    public void procesarConsulta() throws ServicioExcepcion {
        if(identificacion == null){
            identificacion = "";
        }
        if (identificacion.isEmpty() && apellidoPaterno.isEmpty() && apellidoMaterno.isEmpty() && nombre.isEmpty() && numRepertorio.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese almenos un parámetro de busqueda", ""));
        } else {
            if (!identificacion.isEmpty()) {
                identificacion = "%" + identificacion + "%";
            } else {
                identificacion = identificacion + "^";
            }
            if (!apellidoPaterno.isEmpty()) {
                apellidoPaterno = "%" + apellidoPaterno + "%";
            } else {
                apellidoPaterno = apellidoPaterno + "^";
            }
            if (!apellidoMaterno.isEmpty()) {
                apellidoMaterno = "%" + apellidoMaterno + "%";
            } else {
                apellidoMaterno = apellidoMaterno + "^";
            }
            if (!nombre.isEmpty()) {
                nombre = "%" + nombre + "%";
            } else {
                nombre = nombre + "^";
            }
            if (!numRepertorio.isEmpty()) {
                numRepertorio = "%" + numRepertorio + "%";
            } else {
                numRepertorio = numRepertorio + "^";
            }

//            setListaPersonas(servicioPersona.listarPersonasPorLike(identificacion, apellidoPaterno.toUpperCase(), apellidoMaterno.toUpperCase(), nombre.toUpperCase()));
            setListaPersonasConPropiedad(servicioPersona.listarPersonasPorLikesPropietario(identificacion, apellidoPaterno.toUpperCase(), apellidoMaterno.toUpperCase(), nombre.toUpperCase()));
            setListaPersonasEnGraDetalle(servicioPersona.listarPersonasPorLikesGraDetalle(identificacion, apellidoPaterno.toUpperCase(), apellidoMaterno.toUpperCase(), nombre.toUpperCase()));

            listaPersonas = new ArrayList<>();
            for (Persona pp : listaPersonasConPropiedad) {
                listaPersonas.add(pp);
            }

            for (Persona pg : listaPersonasEnGraDetalle) {
                listaPersonas.add(pg);
            }
            listaGravamenesExistentes = new ArrayList<>();
            for (Persona p : listaPersonas) {
                setGravamenConsultado(servicioGravamenDetalle.buscarPorPersona(p.getPerId()));
                if (!gravamenConsultado.isEmpty()) {
                    listaGravamenesExistentes.add(gravamenConsultado);
                } else {
                    listaGravamenesExistentes.add("");
                }
            }

        }

    }

    public void listarPropiedadesdePropietarioS() {
        setListaMatriculasdePropietario(servicioPropietario.listarPorPerId(personaSeleccionada.getPerId()));
        setListaTipoTtrRepertorio(servicioPropietario.listarTipoRepPornumRepYPerId(personaSeleccionada.getPerId()));
        setListaSuspensiones(tramiteDetalleDao.listarTramiteEstado(personaSeleccionada.getPerId()));

        if (listaMatriculasdePropietario.isEmpty()) {
            List<GravamenDetalle> listPersonaEnGraDetalle = new ArrayList<>();
            listPersonaEnGraDetalle = servicioGravamenDetalle.listarPorIdPersona(personaSeleccionada.getPerId());
            listaCertificados = new ArrayList<>();
            listaGravamen = new ArrayList<>();
            listaPropietariosPropS = new ArrayList<>();
            listaFacturaDetalle = new ArrayList<>();
            listaTraDetalleDesc = new ArrayList<>();
            listaContratoDetalleDesc = new ArrayList<>();
            for (GravamenDetalle gravDetalle : listPersonaEnGraDetalle) {
                List<Gravamen> listGravamenaux = servicioGravamen.buscarPorRepertorio(gravDetalle.getGraId().getRepNumero().getRepNumero());
                for (Gravamen gravamenAux : listGravamenaux) {
                    listaGravamen.add(gravamenAux);
                }
                List<Acta> listActaAux = servicioActa.listarActasPorNumRepertorio(gravDetalle.getGraId().getRepNumero().getRepNumero());
                for (Acta actaAux : listActaAux) {
                    listaActas.add(actaAux);
                }

                List<RepertorioDigital> listAux = servicioRepertorioDigital.listarRepDigPorNumRepertorio(gravDetalle.getGraId().getRepNumero().getRepNumero());
                for (RepertorioDigital repdig : listAux) {
                    listaRepertoriosDigi.add(repdig);
                }
            }

            setRenderTab("false");
        } else {
            listaGravamenesExistentes1 = new ArrayList<>();
            for (Propietario pr : listaMatriculasdePropietario) {
                setGravamenesConsultados1(servicioGravamen.obtenerPorMatriculayRepertorio(pr.getPrdMatricula().getPrdMatricula(), pr.getPrdRepertorio()));
                if (!GravamenesConsultados1.isEmpty()) {
                    listaGravamenesExistentes1.add(GravamenesConsultados1);
                } else {
                    listaGravamenesExistentes1.add("");
                }
            }
        }

    }

    public void consultarMarginaciones() {
        setListaMarginacion(servicioMarginacion.listarPor_NumActa(actaSeleccionada.getActNumero()));
    }

//    public void listarPropietariosComparados() {
//        listaFiltradaporPersonas = new ArrayList<>();
//        setListaPropietarios(servicioPersona.listaPropietarios());
//        for (Persona persona : listaPersonas) {
//            for (Propietario propietario : listaPropietarios) {
//                if (Objects.equals(persona.getPerId(), propietario.getPerId().getPerId())) {
//                    listaFiltradaporPersonas.add(propietario);
//                }
//            }
//        }
//    }
    public void listarPropietariosPorMatricula() {
        try {
            listaCertificados = new ArrayList<>();
            listaGravamen = new ArrayList<>();
            listaPropietariosPropS = new ArrayList<>();
            listaActas = new ArrayList<>();
            listaFacturaDetalle = new ArrayList<>();
            listaTraDetalleDesc = new ArrayList<>();
            listaRepertoriosDigi = new ArrayList<>();
            setListaCertificados(servicioCertificado.listarPorPredio_O_Catastro(propiedadSeleccionada.getPrdPredio(), propiedadSeleccionada.getPrdCatastro()));
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
            setListaContratoDetalleDesc(servicioPropietario.listarContratoPornumMatricula(propiedadSeleccionada.getPrdMatricula()));
//            setListaFacturaDetalle(servicioFacturaDetalle.listarPorNumtramite(tramiteSeleccionado.getTraNumero().getTraNumero()));

            listaGravamenesPersonas = new ArrayList<>();
            for (Propietario p : listaPropietariosPropS) {
                setGravamenPersona(servicioGravamenDetalle.buscarPorPersona(p.getPerId().getPerId()));
                if (!gravamenPersona.isEmpty()) {
                    listaGravamenesPersonas.add(gravamenPersona);
                } else {
                    listaGravamenesPersonas.add("");
                }
            }
        } catch (Exception e) {
        }
        setEstadoWizard("true");
        setEstadoTab("false");
    }

    public void cambiarEstadoTab() {
        setEstadoTab("true");
    }

    public void obtenerRazonActa() {
        setRazon(servicioRazon.obtenerRazonPornumTramite(actaSeleccionada.getRepNumero().getTraNumero().getTraNumero()));
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
        setPropiedadConsultada(servicioPropiedad.obtenerPorMatricula(propietarioSeleccionado.getPrdMatricula().getPrdMatricula()));
        setListaLinderos(servicioLindero.listarPorNumMatricula(propietarioSeleccionado.getPrdMatricula().getPrdMatricula()));
    }

    public void redirect() {
        propiedadSeleccionada = new Propiedad();
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
        listaDePropiedades = new ArrayList<>();
        listaLinderos = new ArrayList<>();
        identificacion = new String();
        apellidoPaterno = new String();
        apellidoMaterno = new String();
        nombre = new String();
        numRepertorio = new String();
        estadoWizard = "false";
        setEstadoTab("true");
        setRenderTab("true");
    }

    public void verificar() {
        if (propiedadSeleccionada == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Primero seleccione una propiedad", ""));
        }
    }

}
