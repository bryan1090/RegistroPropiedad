/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.tipoTramites;

import com.rm.empresarial.controlador.DataManagerSesion;
import com.rm.empresarial.controlador.util.FechasUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.ActaDao;
import com.rm.empresarial.dao.CantonDao;
import com.rm.empresarial.dao.GravamenDao;
import com.rm.empresarial.dao.GravamenDetalleDao;
import com.rm.empresarial.dao.InstitucionDao;
import com.rm.empresarial.dao.LinderoDao;
import com.rm.empresarial.dao.MarginacionDao;
import com.rm.empresarial.dao.NotariaDao;
import com.rm.empresarial.dao.PropiedadDetalleDao;
import com.rm.empresarial.dao.PropietarioDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.TipoLibroDao;
import com.rm.empresarial.dao.TipoTramiteDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.dao.TramiteValorDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Canton;
import com.rm.empresarial.modelo.Gravamen;
import com.rm.empresarial.modelo.GravamenDetalle;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.TramiteUsuarioServicio;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "declaratoria")
@Dependent
public class Declaratoria implements Serializable {

    public Declaratoria() {
    }

    @Inject
    private TransformadorNumerosLetrasUtil transformadorNumerosLetrasUtil;

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramiteDetalle = new ArrayList<>();
    @Getter
    @Setter
    private List<RepertorioPropiedad> listaRepertorioPropiedad = new ArrayList<>();
    @Getter
    @Setter
    private List<Gravamen> listaGravamenMostrar = new ArrayList<>();
    @Getter
    @Setter
    private List<Gravamen> listaGravamen = new ArrayList<>();

    @Getter
    @Setter
    BigInteger numInscripcion;

    @Getter
    @Setter
    private Repertorio repertorio;

    @Getter
    @Setter
    private FechasUtil fechasUtil;

    @Getter
    @Setter
    private String cabeceraHTML;
    @Getter
    @Setter
    private String cabeceraPDF;
    @Getter
    @Setter
    private Institucion institucion;
    @Getter
    @Setter
    private Canton cantonAct;

    @EJB
    private TramiteDetalleDao tramiteDetalleDao;
    @EJB
    private RepertorioDao repertorioDao;
    @EJB
    private NotariaDao notariaDao;
    @EJB
    private MarginacionDao marginacionDao;
    @EJB
    private ActaDao actaDao;
    @EJB
    private RepertorioPropiedadDao repertorioPropiedadDao;
    @EJB
    private LinderoDao linderoDao;
    @EJB
    private GravamenDao gravamenDao;
    @EJB
    private GravamenDetalleDao gravamenDetalleDao;
    @EJB
    private TipoTramiteDao tipoTramiteDao;
    @EJB
    private TramiteDao tramiteDao;
    @EJB
    private TramiteValorDao tramiteValorDao;
    @EJB
    private TipoLibroDao tipoLibroDao;
    @EJB
    private PropiedadDetalleDao propiedadDetalleDao;
    @EJB
    private InstitucionDao institucionDao;
    @EJB
    private CantonDao cantonDao;
    @EJB
    private TramiteUsuarioServicio serviciotramiteUsuario;
    @EJB
    private PropietarioDao propietarioDao;

    public static String tituloCompareciente() {
        String titComparecientes = "1.- APELLIDOS Y NOMBRES DE LOS CONTRATANTES";
        return titComparecientes;
    }

    public static String tituloOtorgamiento() {
        String titOtorgamiento = "2.- FECHA DE OTORGAMIENTO .-";
        return titOtorgamiento;
    }

    public static String tituloCuantias() {
        String titCuantias = "6.- CUANTIAS .-";
        return titCuantias;
    }

    public static String tituloGravamen() {
        String titGravamen = "7.- GRAVAMENES Y LIMITACIONES";
        return titGravamen;
    }

    public static String tituloObservacion() {
        String titObservacion = "8.- OBSERVACIONES";
        return titObservacion;
    }

    public static String tituloAntecedentes() {
        String titAntecedentes = "3.- ANTECEDENTES .-";
        return titAntecedentes;
    }

    public static String tituloObjetoContrato() {
        String titObjetoContrato = "4.- OBJETO DEL CONTRATO .-";
        return titObjetoContrato;
    }

    public static String tituloLinderos() {
        String titLinderos = "5.- LINDEROS .-";
        return titLinderos;
    }

    public List<Propiedad> cargarListaPropiedadComparecientes(String numRepertorio) throws ServicioExcepcion, ParseException {
        List<Propiedad> listaPropiedadComparecientes = new ArrayList<>();
        listaPropiedadComparecientes.clear();
        listaRepertorioPropiedad.clear();
        listaRepertorioPropiedad = repertorioPropiedadDao.buscarPorNumRepertorio(Long.valueOf(numRepertorio));
        repertorio = repertorioDao.encontrarRepertorioPorId(numRepertorio);
        listaTramiteDetalle.clear();
        listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                repertorio.getTraNumero().getTraNumero(), repertorio.getRepTtrId(), Long.valueOf(numRepertorio));

        List<RepertorioPropiedad> listRepPropAux = new ArrayList<>();
        listRepPropAux.clear();
        listRepPropAux = repertorioPropiedadDao.buscarPorNumRepertorio(Long.valueOf(numRepertorio));
        //BUSCA SI LOS COMPARECIENTES SON PROPIETARIOS USANDO LA MATRICULA DE REPERTORIO PROPIEDAD PARA AGREGAR A LA LISTA
        for (RepertorioPropiedad repertorioPropiedad : listRepPropAux) {
            for (TramiteDetalle tramDet : listaTramiteDetalle) {
                Propietario propietario = propietarioDao.buscarPropietarioPor_Persona_Matricula_Estado(tramDet.getPerId().getPerId(), repertorioPropiedad.getPrdMatricula().getPrdMatricula(), "A");
                if (propietario != null) {
                    if (!listaRepertorioPropiedad.contains(repertorioPropiedad)) {
                        listaRepertorioPropiedad.add(repertorioPropiedad);
                    }
                }

            }

        }
        for (RepertorioPropiedad repertorioPropiedad : listaRepertorioPropiedad) {
            if (!listaPropiedadComparecientes.contains(repertorioPropiedad.getPrdMatricula())) {
                listaPropiedadComparecientes.add(repertorioPropiedad.getPrdMatricula());
            }
        }
        //BUSCA SI LOS COMPARECIENTES SON PROPIETARIOS (de cualquier propiedad) PARA AGREGAR A LA LISTA
        for (TramiteDetalle tramDet : listaTramiteDetalle) {
            List<Propietario> listPropietario = new ArrayList<>();
            listPropietario.clear();
            listPropietario = propietarioDao.listarPor_Id_Persona(tramDet.getPerId().getPerId());
            for (Propietario propietario : listPropietario) {
                if (!listaPropiedadComparecientes.contains(propietario.getPrdMatricula())) {
                    listaPropiedadComparecientes.add(propietario.getPrdMatricula());
                }

            }

        }
        return listaPropiedadComparecientes;
    }

    public String txtCompareciente(String numRepertorio, String numTramite) throws ServicioExcepcion {
        repertorio = repertorioDao.encontrarRepertorioPorId(numRepertorio);
        listaTramiteDetalle.clear();
        listaTramiteDetalle = tramiteDetalleDao.listarPorNumTramitePorIdTipoTramitePorEstadoPorRepertorio(
                repertorio.getTraNumero().getTraNumero(), repertorio.getRepTtrId(), Long.valueOf(numRepertorio));
        int aux = 0;
        String comparecietes = "";
        String estadoCivil = "";
        String tipoIdentificacion = "";
        String signoPuntuacion = "";

        for (TramiteDetalle listaTxtCompareciente : listaTramiteDetalle) {
            aux++;
            if (aux < listaTramiteDetalle.size()) {
                signoPuntuacion = ", ";
            } else {
                signoPuntuacion = ".";
            }
            switch (listaTxtCompareciente.getTdtPerTipoIdentificacion().trim()) {
                case "C":
                    tipoIdentificacion = "cédula";
                    break;
                case "R":
                    tipoIdentificacion = "RUC";
                    break;
                case "P":
                    tipoIdentificacion = "pasaporte";
                    break;

            }
            String apMaterno = "";
            if (listaTxtCompareciente.getPerId().getPerApellidoMaterno() != null) {
                apMaterno = listaTxtCompareciente.getPerId().getPerApellidoMaterno();
            } else {
                apMaterno = "";
            }
            if (listaTxtCompareciente.getPerId().getPerEstadoCivil() == null) {
                if (tipoIdentificacion.equals("RUC")) {
                    comparecietes = comparecietes
                            + " " + listaTxtCompareciente.getPerId().getPerNombre().trim()
                            + " " + "<strong>" + listaTxtCompareciente.getTdtTpcDescripcion().trim() + "</strong>"
                            + " " + listaTxtCompareciente.getPerId().getPerIdentificacion().trim()
                            + "<br>";
                } else {
                    comparecietes = comparecietes
                            + " " + listaTxtCompareciente.getPerId().getPerNombre().trim()
                            + " " + listaTxtCompareciente.getPerId().getPerApellidoPaterno().trim()
                            + " " + apMaterno.trim()
                            + " " + "<strong>" + listaTxtCompareciente.getTdtTpcDescripcion().trim() + "</strong>"
                            + " " + listaTxtCompareciente.getPerId().getPerIdentificacion().trim()
                            + "<br>";
                }

            } else {
                switch (listaTxtCompareciente.getPerId().getPerEstadoCivil().trim()) {
                    case "S":
                        estadoCivil = "soltero(a)";
                        break;
                    case "C":
                        estadoCivil = "casado(a)";
                        break;
                    case "D":
                        estadoCivil = "divorciado(a)";
                        break;
                    case "V":
                        estadoCivil = "viudo(a)";
                        break;
                    case "UH":
                        estadoCivil = "unión de hecho";
                        break;

                }
                comparecietes = comparecietes
                        + " " + listaTxtCompareciente.getPerId().getPerNombre().trim()
                        + " " + listaTxtCompareciente.getPerId().getPerApellidoPaterno().trim()
                        + " " + apMaterno.trim()
                        + " " + "<strong>" + listaTxtCompareciente.getTdtTpcDescripcion().trim() + "</strong>"
                        + " " + listaTxtCompareciente.getPerId().getPerIdentificacion().trim()
                        + "<br>";
            }

        }
        return comparecietes;
    }

    public String txtOtorgamiento(Date fechaOtorgamiento, String numeroNotario, String canton, String nombreNotario) throws ServicioExcepcion {
        String MES[] = {"ENERO", "FEBRERO", "MARZO", "ABRIL", "MAYO", "JUNIO", "JULIO", "AGOSTO", "SEPTIEMBRE", "OCTUBRE", "NOVIEMBRE", "DICIEMBRE"};
        String DIASEMANA[] = {"Domingo", "Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String DIA[] = {"UNO", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE",
            "OCHO", "NUEVE", "DIEZ", "ONCE", "DOCE", "TRECE", "CATORCE", "QUINCE", "DIECISÉIS", "DIECISIETE,",
            "DIECIOCHO", "DIECINUEVE", "VEINTE", "VEINTIUNO", "VEINTIDÓS", "VEINTITRES", "VEINTICUATRO",
            "VEINTICINCO", "VEINTISÉIS", "VEINTISIETE", "VEINTIOCHO", "VEINTINUEVE", "TREINTA",
            "TREINTA Y UNO"};

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaOtorgamiento);

        String año = (transformadorNumerosLetrasUtil.transformador(String.valueOf(calendar.get(Calendar.YEAR)))).toString();
        String fechaLetras = "";

        fechaLetras = (DIA[calendar.get(Calendar.DAY_OF_MONTH) - 1] + " DE " + MES[calendar.get(Calendar.MONTH)] + " DEL " + año);
        //Notaria notaria = notariaDao.encontrarPorNumNotaria(Short.valueOf(numeroNotario));

        String fechaOtrog = "En esta fecha se me presentó la PRIMERA copia de la escritura"
                + " pública otorgada el " + fechaLetras + ", ante el Notario "
                + numeroNotario.toUpperCase() + " Encargado del cantón " + canton.toUpperCase() + ", Doctor " + nombreNotario.toUpperCase();
        return fechaOtrog;
    }

    public String txtAntecedentes(String numRepertorio) {
        String txtAntecedentes = "";
        List<Long> listaNumActasDesdeMarginacion = new ArrayList<>();
        listaNumActasDesdeMarginacion = marginacionDao.listarPor_NumRepertorio(numRepertorio);
        for (Long numActa : listaNumActasDesdeMarginacion) {
            Acta actaAnterior = actaDao.obtenerActaPorNumeroActa(numActa);
            if (actaAnterior.getActDescripcion4() != null) {
                txtAntecedentes = txtAntecedentes + actaAnterior.getActDescripcion4() + "<br>";
            }

        }
        return txtAntecedentes;
    }

    public String txtObjetoContrato() {

        String comparecietes = "";
        String estadoCivil = "";
        String tipoIdentificacion = "";
        String signoPuntuacion = "";
        int aux = 0;
        for (TramiteDetalle listaTxtCompareciente : listaTramiteDetalle) {
            aux++;
            if (aux < listaTramiteDetalle.size()) {
                signoPuntuacion = ", ";
            } else {
                signoPuntuacion = ".";
            }
            switch (listaTxtCompareciente.getTdtPerTipoIdentificacion().trim()) {
                case "C":
                    tipoIdentificacion = "cédula";
                    break;
                case "R":
                    tipoIdentificacion = "RUC";
                    break;
                case "P":
                    tipoIdentificacion = "pasaporte";
                    break;

            }
            String apMaterno = "";
            if (listaTxtCompareciente.getPerId().getPerApellidoMaterno() != null) {
                apMaterno = listaTxtCompareciente.getPerId().getPerApellidoMaterno();
            } else {
                apMaterno = "";
            }
            if (listaTxtCompareciente.getPerId().getPerEstadoCivil() == null) {
                comparecietes = comparecietes + listaTxtCompareciente.getPerId().getPerNombre()
                        + " " + listaTxtCompareciente.getPerId().getPerNombre().trim()
                        + " " + listaTxtCompareciente.getPerId().getPerApellidoPaterno().trim()
                        + " " + apMaterno.trim()
                        + " " + listaTxtCompareciente.getTdtTpcDescripcion() + " con "
                        + tipoIdentificacion + ": " + listaTxtCompareciente.getTdtPerIdentificacion().trim()
                        + "" + signoPuntuacion;
            } else {
                switch (listaTxtCompareciente.getPerId().getPerEstadoCivil().trim()) {
                    case "S":
                        estadoCivil = "soltero(a)";
                        break;
                    case "C":
                        estadoCivil = "casado(a)";
                        break;
                    case "D":
                        estadoCivil = "divorciado(a)";
                        break;
                    case "V":
                        estadoCivil = "viudo(a)";
                        break;
                    case "UH":
                        estadoCivil = "unión de hecho";
                        break;

                }
                comparecietes = comparecietes + listaTxtCompareciente.getPerId().getPerNombre()
                        + " " + listaTxtCompareciente.getPerId().getPerNombre().trim()
                        + " " + listaTxtCompareciente.getPerId().getPerApellidoPaterno().trim()
                        + " " + apMaterno.trim()
                        + " " + listaTxtCompareciente.getTdtTpcDescripcion() + " con "
                        + tipoIdentificacion + ": " + listaTxtCompareciente.getTdtPerIdentificacion().trim()
                        + " " + estadoCivil + signoPuntuacion;
            }

        }
        return "Con estos antecedentes " + comparecietes;
    }

    public String txtLinderos(String numRepertorio) throws ServicioExcepcion, ParseException {
        List<Propiedad> listPropiedad = new ArrayList<>();
        listPropiedad.clear();
        listPropiedad = cargarListaPropiedadComparecientes(numRepertorio);
        List<Lindero> listaLindero = new ArrayList<>();
        String linderosActa = "";
        for (Propiedad propiedad : listPropiedad) {
            listaLindero = linderoDao.listarPorNumMatricula(propiedad.getPrdMatricula());
            String txtLinderos = "";
            for (Lindero lindero : listaLindero) {
                txtLinderos = txtLinderos + lindero.getLdrNombre().trim() + ", ";
            }

            linderosActa = linderosActa + "Matrícula: " + propiedad.getPrdMatricula()
                    + ", linderos: " + txtLinderos + "<br>";
        }
        return linderosActa;

    }

    public String txtCuantias(float cuantiaAlcabalas, float cuantiaMonto, float cuantiaPlusvalia, float cuantiaRegistro, String cuantiaTipo, String cuantiaObservacion) {

        float totalCuantia = cuantiaAlcabalas + cuantiaMonto + cuantiaPlusvalia + cuantiaRegistro;

        String totalCuantiaTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(totalCuantia))).toString();
        String cuantiaAlcabalasTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(cuantiaAlcabalas))).toString();
        String cuantiaMontoTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(cuantiaMonto))).toString();
        String cuantiaPlusvaliaTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(cuantiaPlusvalia))).toString();
        String cuantiaRegistroTexto = (transformadorNumerosLetrasUtil.transformador(String.valueOf(cuantiaRegistro))).toString();
        String txtCuantia = "";
        switch (cuantiaTipo) {
            case "Normal":
            case "Determinado":
                if (cuantiaAlcabalas != 0
                        && cuantiaMonto != 0
                        && cuantiaPlusvalia != 0
                        && cuantiaRegistro != 0) {

                    txtCuantia = "La cuantía del contrato es " + totalCuantiaTexto + " DÓLARES"
                            + ". Por impuestos de Alcabalas " + cuantiaAlcabalasTexto + " DÓLARES: Por registro y adicionales "
                            + cuantiaRegistroTexto + " DÓLARES; Por plusvalía " + cuantiaPlusvaliaTexto
                            + " DÓLARES, según  Art. 047 BEV. " + cuantiaObservacion.toUpperCase();

                } else {
                    FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "En cuantía tipo NORMAL y DETERMINADO los valores ingresados no deben ser CERO", "");
                    FacesContext.getCurrentInstance().addMessage("", facesMsg);
                }
                break;
            case "Indeterminado":

                txtCuantia = "La cuantía del contrato es " + totalCuantiaTexto + " DÓLARES"
                        + ". Por impuestos de Alcabalas " + cuantiaAlcabalasTexto + " DÓLARES: Por registro y adicionales "
                        + cuantiaRegistroTexto + " DÓLARES; Por plusvalía " + cuantiaPlusvaliaTexto
                        + " DÓLARES." + cuantiaObservacion.toUpperCase();

                break;
            case "Exonerado":

                txtCuantia = "La cuantía del contrato es " + totalCuantiaTexto + " DÓLARES"
                        + ". Por impuestos de Alcabalas " + cuantiaAlcabalasTexto + ". " + cuantiaObservacion.toUpperCase();
                break;

        }
        return txtCuantia;
    }

    public String txtGravamenes(String numRepertorio, String numTramite) throws ServicioExcepcion, ParseException {
        listaGravamenMostrar.clear();
        listaGravamen.clear();
        String txtGravamen = "";
        List<Propiedad> listPropiedad = new ArrayList<>();
        listPropiedad.clear();
        listPropiedad = cargarListaPropiedadComparecientes(numRepertorio);
        //List<TramiteDetalle> listaTramDetalleN = tramiteDetalleDao.listar_por_repertorio_tramite_propietarioTipoTramComp(Integer.valueOf(numRepertorio), Long.valueOf(numTramite), "N");

        for (Propiedad propied : listPropiedad) {
            listaGravamen.clear();
            listaGravamen = gravamenDao.buscarPorMatricula(propied);

            if (!listaGravamen.isEmpty() || listaGravamen != null) {
                listaGravamenMostrar.addAll(listaGravamen);
            }

        }

        for (Gravamen gravamen : listaGravamenMostrar) {
            String fecha = fechasUtil.convertirFecha_A_letras(gravamen.getRepNumero().getRepFHR());
//            txtGravamen = txtGravamen + "Matrícula: " + gravamen.getPrdMatricula().getPrdMatricula()
//                    + ", " + gravamen.getGraDescripcion().trim() + " con fecha: "
//                    + fecha + "<br>";
            Acta acta = new Acta();
            acta = actaDao.buscarActaPorNumRepertorio(gravamen.getRepNumero().getRepNumero());
            List<TramiteDetalle> listTramDet = new ArrayList<>();
            listTramDet.clear();
            listTramDet = tramiteDetalleDao.listarRepertorio(gravamen.getRepNumero().getRepNumero().intValue());
            String nombreComparecientes = "";
            for (TramiteDetalle tramiteDetalle : listTramDet) {
                if (tramiteDetalle.getTtcId().getTtcPropietario().equals("N")) {
                    String apMaterno = "";
                    if (tramiteDetalle.getPerId().getPerApellidoMaterno() != null) {
                        apMaterno = tramiteDetalle.getPerId().getPerApellidoMaterno();
                    } else {
                        apMaterno = "";
                    }
                    nombreComparecientes = tramiteDetalle.getPerId().getPerNombre().trim() + " "
                            + tramiteDetalle.getPerId().getPerApellidoPaterno().trim() + " "
                            + apMaterno.trim() + ", " + tramiteDetalle.getTdtTpcDescripcion() + "." + "<br></br>";
                }
            }

            txtGravamen = txtGravamen + "Matrícula: " + gravamen.getPrdMatricula().getPrdMatricula()
                    + "; Predio: " + gravamen.getPrdMatricula().getPrdPredio()
                    + "; Catastro: " + gravamen.getPrdMatricula().getPrdCatastro()
                    + "; " + gravamen.getRepNumero().getRepTtrDescripcion().trim()
                    + "; " + fecha.trim() + "; Rep: " + gravamen.getRepNumero().getRepNumero()
                    + "; Nro. Inscripción: " + acta.getActInscripcion() + ".<br></br>"
                    + "Comparecientes: <br></br>"
                    //                                    + gravamenDetalle.getPerId().getPerNombre().trim() + " "
                    //                                    + gravamenDetalle.getPerId().getPerApellidoPaterno().trim() + " "
                    //                                    + apMaterno.trim()
                    + nombreComparecientes;

        }

        if (listaGravamenMostrar.isEmpty()) {
            txtGravamen = "No existen gravámenes";
        }
        return txtGravamen;
    }

    public String txtObservaciones(String observacion) {
        String txtObservaciones = "";
        if (observacion.equals("") || observacion == null) {
            txtObservaciones = "NINGUNA";
            return txtObservaciones;
        } else {
            return observacion;
        }

    }

    public String responsables(String numTramite) throws ServicioExcepcion {
        //ASESOR
        String apellidoMaternoAsesor = "";
        if (dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno() != null) {
            apellidoMaternoAsesor = dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno().trim();
        }
        String nombreAsesor = dataManagerSesion.getUsuarioLogeado().getPerId().getPerNombre().trim()
                + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoPaterno().trim()
                + " " + apellidoMaternoAsesor;
        //REVISOR
        String apellidoMaternoRevisor = "";
        Tramite tramite = tramiteDao.buscarTramitePorNumero(Long.valueOf(numTramite));
        TramiteUsuario tramUsuario = new TramiteUsuario();
        tramUsuario = serviciotramiteUsuario.buscarPorTramite(tramite);
        if (tramUsuario.getUsuId().getPerId().getPerApellidoMaterno() != null) {
            apellidoMaternoRevisor = tramUsuario.getUsuId().getPerId().getPerApellidoMaterno().trim();
        }
        String nombreRevisor = tramUsuario.getUsuId().getPerId().getPerNombre().trim()
                + " " + tramUsuario.getUsuId().getPerId().getPerApellidoPaterno().trim()
                + " " + apellidoMaternoRevisor;

        String responsables = "<strong>RESPONSABLES</strong><br></br><br></br>"
                + "<strong>ASESOR: </strong> " + nombreAsesor + "<br></br><br></br>"
                + "<strong>REVISOR: </strong> " + nombreRevisor + "<br></br>";
        return responsables;
    }

    public String generarCabeceraHTML(Long tipoTramiteId, String numTramite, String numRepertorio, String contratoDescripcion) throws ServicioExcepcion, ParseException {

        Repertorio repertorioSelected = repertorioDao.encontrarRepertorioPorId(numRepertorio);
        List<String> TraNotaria = new ArrayList<>();
        TipoTramite tipoTramite = tipoTramiteDao.buscarPorID(tipoTramiteId);
        Date today = new Date();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date todayWithZeroTime = formatter.parse(formatter.format(today));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        int anio = calendar.get(Calendar.YEAR);
        numInscripcion = actaDao.buscarPorAnioYTipoLibroId(anio, tipoTramite.getTplId().getTplId());
        String sql = "SELECT TraNotaria from Tramite WHERE TraNumero = " + numTramite;
        TraNotaria = (tramiteDao.ObtenerTramiteNotaria(sql));

        institucion = institucionDao.obtenerInstitucion();
        cantonAct = cantonDao.encontrarCantonPorId(institucion.getInsCanId().toString());
        //Titulo
        String titulo = "<strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + cantonAct.getCanNombre().toUpperCase() + "</strong>";

        //numero Inscripcion
        String numeroInscripcion = "<strong>Nro. de Inscripción:</strong>" + " " + numInscripcion + "";

        //numero Tramite
        String numeroTramite = "<strong>Nro. de Trámite:</strong>" + " " + numTramite + "";

        //repertorio
        String numeroRepertorio = "<strong>Nro. de Repertorio:</strong>" + " " + numRepertorio + "";

        //fecha
        String MES[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        String DIA[] = {"01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17,",
            "18", "19", "20", "21", "22", "23", "24",
            "25", "26", "27", "28", "29", "30",
            "31"};

        //Calendar calendar = Calendar.getInstance();
        calendar.setTime(repertorioSelected.getRepFHR());

        String fecha = "";

        fecha = (DIA[calendar.get(Calendar.DAY_OF_MONTH) - 1] + "/" + MES[calendar.get(Calendar.MONTH)] + "/" + calendar.get(Calendar.YEAR))
                + " " + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))
                + ":" + String.valueOf(calendar.get(Calendar.MINUTE))
                + ":" + String.valueOf(calendar.get(Calendar.SECOND));

        String fechaRepertorio = "<strong>Fecha de Repertorio: </strong>" + " " + fecha + "";

        //Tomo
        TipoLibro tipoL = new TipoLibro();
        tipoL = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());
        String tomo = "<strong>Tomo:</strong>" + tipoL.getTplTomo() + "";

        //Notaria
        String notaria = "<strong>Notaria:</strong>" + " " + String.join(", ", TraNotaria) + "";

        //Tipo Contrato
        String tipoContrato = "<strong>Tipo de Contrato:</strong>" + " " + contratoDescripcion + "";

        //Matriculas
        List<String> numMatricula = new ArrayList<>();
        List<Propiedad> listProp = cargarListaPropiedadComparecientes(numRepertorio);
        for (Propiedad propiedad : listProp) {
            numMatricula.add(propiedad.getPrdMatricula());
        }
        String matriculaActa = "<strong>Matrículas:</strong>" + " " + String.join(", ", numMatricula) + "";

        //Parroquia
        Tramite tram = tramiteDao.buscarTramitePorNumero(Long.valueOf(numTramite));
        String parroq = "";
        if (tram != null) {
            TramiteValor tramValor = tramiteValorDao.buscarPorTipo(tram, tipoTramite);
            if (tramValor != null) {
                parroq = tramValor.getTrvParNombre().trim();
            }
        }
        String parroquia = "<strong>Parroquia: </strong>" + parroq + "";

        cabeceraHTML = titulo + numeroInscripcion + numeroTramite
                + numeroRepertorio + fechaRepertorio + tomo + notaria
                + tipoContrato + matriculaActa + parroquia;

        return cabeceraHTML;

    }

    public String generarCabeceraPDF(Long tipoTramiteId, String numTramite, String numRepertorio, String contratoDescripcion) throws ServicioExcepcion, ParseException {

        Repertorio repertorioSelected = repertorioDao.encontrarRepertorioPorId(numRepertorio);
        List<String> TraNotaria = new ArrayList<>();
        TipoTramite tipoTramite = tipoTramiteDao.buscarPorID(tipoTramiteId);
        Date today = new Date();
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date todayWithZeroTime = formatter.parse(formatter.format(today));

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        int anio = calendar.get(Calendar.YEAR);
        numInscripcion = actaDao.buscarPorAnioYTipoLibroId(anio, tipoTramite.getTplId().getTplId());
        String sql = "SELECT TraNotaria from Tramite WHERE TraNumero = " + numTramite;
        TraNotaria = (tramiteDao.ObtenerTramiteNotaria(sql));

        String MES[] = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};

        String DIA[] = {"01", "02", "03", "04", "05", "06", "07",
            "08", "09", "10", "11", "12", "13", "14", "15", "16", "17,",
            "18", "19", "20", "21", "22", "23", "24",
            "25", "26", "27", "28", "29", "30",
            "31"};

        institucion = institucionDao.obtenerInstitucion();
        cantonAct = cantonDao.encontrarCantonPorId(institucion.getInsCanId().toString());
        //Titulo
        String tituloPDF = "<h3 align='center'><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN " + cantonAct.getCanNombre().toUpperCase() + "</strong></h3>";

        //numero Inscripcion
        String numeroInscripcionPDF = " " + numInscripcion;

        //numero Tramite
        String numeroTramitePDF = " " + numTramite;

        //repertorio
        String numeroRepertorioPDF = " " + numRepertorio;

        //fecha
        calendar.setTime(repertorioSelected.getRepFHR());
        String fecha = " ";

        fecha = (DIA[calendar.get(Calendar.DAY_OF_MONTH) - 1] + "/" + MES[calendar.get(Calendar.MONTH)] + "/" + calendar.get(Calendar.YEAR))
                + " " + String.valueOf(calendar.get(Calendar.HOUR_OF_DAY))
                + ":" + String.valueOf(calendar.get(Calendar.MINUTE))
                + ":" + String.valueOf(calendar.get(Calendar.SECOND));
        String fechaRepertorioPDF = " " + fecha;

        //Tomo
        TipoLibro tipoL = new TipoLibro();
        tipoL = tipoLibroDao.encontrarTipoLibroPorId(tipoTramite.getTplId().getTplId().toString());

        String tomoPDF = " " + tipoL.getTplTomo();
        //Notaria
        String notariaPDF = " " + String.join(", ", TraNotaria);

        //Tipo Contrato
        String tipoContratoPDF = contratoDescripcion;

        //Matriculas
        List<String> numMatricula = new ArrayList<>();
        List<Propiedad> listProp = cargarListaPropiedadComparecientes(numRepertorio);
        for (Propiedad propiedad : listProp) {
            numMatricula.add(propiedad.getPrdMatricula());
        }

        String matriculaActaPDF = " " + String.join(", ", numMatricula);

        //Parroquia
        Tramite tram = tramiteDao.buscarTramitePorNumero(Long.valueOf(numTramite));
        String parroq = "";
        if (tram != null) {
            TramiteValor tramValor = tramiteValorDao.buscarPorTipo(tram, tipoTramite);
            if (tramValor != null) {
                parroq = tramValor.getTrvParNombre().trim();
            }
        }

        String parroquiaPDF = " " + parroq;

        cabeceraPDF = "";
        cabeceraPDF = tituloPDF
                + "<strong>Nro. de Inscripción: </strong>&nbsp;&nbsp;&nbsp;" + numeroInscripcionPDF + "<br>"
                + "<strong>Nro. de Trámite: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + numeroTramitePDF + "<br>"
                + "<strong>Nro. de Repertorio: </strong>&nbsp;&nbsp;&nbsp;&nbsp;" + numeroRepertorioPDF + "<br>"
                + "<strong>Fecha de Repertorio:  </strong>&nbsp;" + fechaRepertorioPDF + "<br>"
                + "<strong>Tomo: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + tomoPDF + "<br>"
                + "<strong>Notaria: </strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + notariaPDF + "<br>"
                + "<strong>Tipo de Contrato: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + tipoContratoPDF + "<br>"
                + "<strong>Matriculas: </strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + matriculaActaPDF + "<br>"
                + "<strong>Parroquia: </strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; " + parroquiaPDF + "<br>";

        return cabeceraPDF;

    }

}
