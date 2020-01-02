package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.FormularioWeb;
import com.rm.empresarial.modelo.FormularioWebArchivo;
import com.rm.empresarial.modelo.FormularioWebDetalle;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoDocumentoWeb;
import com.rm.empresarial.servicio.FormularioWebArchivoServicio;
import com.rm.empresarial.servicio.FormularioWebDetalleServicio;
import com.rm.empresarial.servicio.FormularioWebServicio;
import com.rm.empresarial.servicio.ParametroPathServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorFormularioWebDetalle")
@ViewScoped
public class ControladorFormularioWebDetalle implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;
    @EJB
    private FormularioWebArchivoServicio servicioFormularioWebArchivo;
    @EJB
    private FormularioWebDetalleServicio servicioFormularioWebDetalle;
    @EJB
    private FormularioWebServicio servicioFormularioWeb;
    @EJB
    private SecuenciaServicio secuenciaServicio;
    @EJB
    private ParametroPathServicio servicioParametroPath;
    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;

    private JsfUtil jsfUtil;

    @Getter
    @Setter
    private Secuencia secuenciaDetalle;
    @Getter
    @Setter
    private Secuencia secuenciaCabecera;
    @Getter
    @Setter
    private Secuencia secuenciaArchivo;
    @Getter
    @Setter
    private TipoDocumentoWeb tipoDocumentoWeb;
    @Getter
    @Setter
    private FormularioWeb cabeceraNuevoFormulario;
    @Getter
    @Setter
    private FormularioWebDetalle nuevoFormulario;
    @Getter
    @Setter
    private List<FormularioWebDetalle> nuevoFormularioCompleto1;
    @Getter
    @Setter
    private List<FormularioWebDetalle> nuevoFormularioCompleto2;
    @Getter
    @Setter
    private List<FormularioWebDetalle> nuevoFormularioCompleto3;
    @Getter
    @Setter
    private List<FormularioWebDetalle> nuevoFormularioCompleto4;
    @Getter
    @Setter
    private List<FormularioWebDetalle> nuevoFormularioCompleto5;
    @Getter
    @Setter
    private List<FormularioWebDetalle> nuevoFormularioCompleto6;
    @Getter
    @Setter
    private List<Parroquia> listaParroquias;
    @Getter
    @Setter
    private String seleccionLineaUno;
    @Getter
    @Setter
    private Long tipoFormularioId;
    @Getter
    @Setter
    private int secuenciaFormularioDetalle;
    @Getter
    @Setter
    private int secuenciaFormularioCabecera;
    @Getter
    @Setter
    private int secuenciaFormularioWebArchivo;
    @Getter
    @Setter
    private String grupo;
    @Getter
    @Setter
    private String nombreFormulario;
    @Getter
    @Setter
    private String bool;
    @Getter
    @Setter
    private List<String> datosTablaPropietarios = new ArrayList();
    @Getter
    @Setter
    private List<FormularioWebDetalle> listaParaMostrar;
    @Getter
    @Setter
    private int auxLineaTabla;
    @Getter
    @Setter
    private Persona personaEncontrada;
    @Getter
    @Setter
    private int comprobarBools;
    @Getter
    @Setter
    private String estadoBoton;
    @Getter
    @Setter
    private FormularioWebArchivo archivoFormulario;

    public ControladorFormularioWebDetalle() {
        setComprobarBools(0);
        cabeceraNuevoFormulario = new FormularioWeb();
        nuevoFormulario = new FormularioWebDetalle();
        archivoFormulario = new FormularioWebArchivo();
        nuevoFormularioCompleto1 = new ArrayList<>();
        nuevoFormularioCompleto2 = new ArrayList<>();
        nuevoFormularioCompleto3 = new ArrayList<>();
        nuevoFormularioCompleto4 = new ArrayList<>();
        nuevoFormularioCompleto5 = new ArrayList<>();
        nuevoFormularioCompleto6 = new ArrayList<>();
        jsfUtil = new JsfUtil();
        listaParroquias = new ArrayList<>();
        listaParaMostrar = new ArrayList<>();
        secuenciaCabecera = new Secuencia();
        secuenciaDetalle = new Secuencia();
        secuenciaArchivo = new Secuencia();
        setNombreFormulario("RPC-01");
        seleccionLineaUno = new String();
        auxLineaTabla = 0;
        for (int i = 0; i < 15; i++) {
            nuevoFormulario = new FormularioWebDetalle();
            setGrupo("A002");
            //cargo grupo 1 y 2 //
            switch (i) {
                case 0:
                    nuevoFormulario.setFwdDescripcion("GRAVAMEN DEL INMUEBLE");
                    nuevoFormulario.setFwdLinea(1);
                    nuevoFormulario.setFwdGrupo("A001");
                    nuevoFormulario.setFwdVariable("VAR_01");
                    break;
                case 1:
                    nuevoFormulario.setFwdDescripcion("1.-LOTE/DERECHO O ACCIONES");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    nuevoFormulario.setFwdValorC01("");
                    break;
                case 2:
                    nuevoFormulario.setFwdDescripcion("2.-CASA N°");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    nuevoFormulario.setFwdValorC01("");
                    break;
                case 3:
                    nuevoFormulario.setFwdDescripcion("3.-DEPARTAMENTO N°");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    nuevoFormulario.setFwdValorC01("");
                    break;
                case 4:
                    nuevoFormulario.setFwdDescripcion("4.-BODEGA N°");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    nuevoFormulario.setFwdValorC01("");
                    break;
                case 5:
                    nuevoFormulario.setFwdDescripcion("5.-LOCAL/ALMACEN N°");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    nuevoFormulario.setFwdValorC01("");
                    break;
                case 6:
                    nuevoFormulario.setFwdDescripcion("6.-PARQUEADERO N°");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    nuevoFormulario.setFwdValorC01("");
                    break;
                case 7:
                    nuevoFormulario.setFwdDescripcion("7.-OFICINA N°");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    nuevoFormulario.setFwdValorC01("");
                    break;
                case 8:
                    nuevoFormulario.setFwdDescripcion("8.-SUITE N°");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    nuevoFormulario.setFwdValorC01("");
                    break;
                case 9:
                    nuevoFormulario.setFwdDescripcion("9.-SECADERO N°");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    nuevoFormulario.setFwdValorC01("");
                    break;
                case 10:
                    nuevoFormulario.setFwdDescripcion("10.- OTRAS ALÍCUOTAS");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    break;
                case 11:
                    nuevoFormulario.setFwdDescripcion("11");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    break;
                case 12:
                    nuevoFormulario.setFwdDescripcion("12");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    break;
                case 13:
                    nuevoFormulario.setFwdDescripcion("N°.CERTIFICADOS");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    break;
                case 14:
                    nuevoFormulario.setFwdDescripcion("DOCUMENTACIÓN QUE ADJUNTA");
                    nuevoFormulario.setFwdLinea(i);
                    nuevoFormulario.setFwdGrupo(grupo);
                    nuevoFormulario.setFwdVariable("VAR_01");
                    break;
            }
            nuevoFormularioCompleto1.add(nuevoFormulario);
        }
        for (int i = 0; i < 5; i++) {
            nuevoFormulario = new FormularioWebDetalle();
            ////cargo grupo 4 //
            switch (i) {
                case 0:
                    nuevoFormulario.setFwdGrupo("A004");
                    break;
                case 1:
                    nuevoFormulario.setFwdGrupo("A004");
                    break;
                case 2:
                    nuevoFormulario.setFwdGrupo("A004");
                    break;
                case 3:
                    nuevoFormulario.setFwdGrupo("A004");
                    break;
                case 4:
                    nuevoFormulario.setFwdGrupo("A004");
                    break;
            }
            nuevoFormularioCompleto3.add(nuevoFormulario);
        }
        for (int i = 0; i < 5; i++) {
            nuevoFormulario = new FormularioWebDetalle();
            //cargamos grupo 5//
            switch (i) {
                case 0:
                    nuevoFormulario.setFwdGrupo("A005");
                    break;
                case 1:
                    nuevoFormulario.setFwdGrupo("A005");
                    break;
                case 2:
                    nuevoFormulario.setFwdGrupo("A005");
                    break;
                case 3:
                    nuevoFormulario.setFwdGrupo("A005");
                    break;
                case 4:
                    nuevoFormulario.setFwdGrupo("A005");
                    break;
            }
            nuevoFormularioCompleto4.add(nuevoFormulario);
        }
        for (int i = 0; i < 4; i++) {
            nuevoFormulario = new FormularioWebDetalle();
            //cargamos grupo 6//
            switch (i) {
                case 0:
                    nuevoFormulario.setFwdGrupo("A006");
                    break;
                case 1:
                    nuevoFormulario.setFwdGrupo("A006");
                    break;
                case 2:
                    nuevoFormulario.setFwdGrupo("A006");
                    break;
                case 3:
                    nuevoFormulario.setFwdGrupo("A006");
                    break;
            }
            nuevoFormularioCompleto5.add(nuevoFormulario);
        }
        for (int i = 0; i < 2; i++) {
            nuevoFormulario = new FormularioWebDetalle();
            //cargamos grupo 7//
            switch (i) {
                case 0:
                    nuevoFormulario.setFwdGrupo("A007");
                    break;
                case 1:
                    nuevoFormulario.setFwdGrupo("A007");
                    break;
            }
            nuevoFormularioCompleto6.add(nuevoFormulario);
        }
        nuevoFormulario = new FormularioWebDetalle();
    }

    @PostConstruct
    public void postControladorFormularioWebDetalle() {
        listarParroquias();
    }

    public void añadirAFormulario1(int linea, String descripcionLinea) throws ServicioExcepcion {
        setGrupo("A001");
        if (seleccionLineaUno.equals("A")) {
            getNuevoFormularioCompleto1().get(0).setFwdValorC01("X");
            getNuevoFormularioCompleto1().get(0).setFwdValorC02("");
        } else if (seleccionLineaUno.equals("B")) {
            getNuevoFormularioCompleto1().get(0).setFwdValorC01("");
            getNuevoFormularioCompleto1().get(0).setFwdValorC02("X");
        }
        getNuevoFormularioCompleto1().get(0).setFwdDescripcion(descripcionLinea);
        getNuevoFormularioCompleto1().get(0).setFwdGrupo(grupo);
        getNuevoFormularioCompleto1().get(0).setFwdLinea(1);
        getNuevoFormularioCompleto1().get(0).setFwdVariable("VAR_01");
        System.out.print("" + descripcionLinea + "" + linea + "" + seleccionLineaUno);
        nuevoFormulario = new FormularioWebDetalle();
        listarParroquias();
    }

    public void añadirAFormulario2(int linea, String descripcionLinea) {
        setGrupo("A002");
        if (bool.equals("true")) {
            comprobarBools++;
            getNuevoFormularioCompleto1().get(linea).setFwdValorC01("X");
        } else if (bool.equals("false")) {
            comprobarBools--;
            getNuevoFormularioCompleto1().get(linea).setFwdValorC01("");
        } else {

        }
        switch (linea) {
            case 1:
                getNuevoFormularioCompleto1().get(linea).setFwdDescripcion("1.-LOTE/DERECHO O ACCIONES");
                getNuevoFormularioCompleto1().get(linea).setFwdLinea(linea);
                getNuevoFormularioCompleto1().get(linea).setFwdGrupo(grupo);
                getNuevoFormularioCompleto1().get(linea).setFwdVariable("VAR_01");
                break;
            case 2:
                getNuevoFormularioCompleto1().get(linea).setFwdDescripcion("2.-CASA N°");
                getNuevoFormularioCompleto1().get(linea).setFwdLinea(linea);
                getNuevoFormularioCompleto1().get(linea).setFwdGrupo(grupo);
                getNuevoFormularioCompleto1().get(linea).setFwdVariable("VAR_01");
                break;
            case 3:
                getNuevoFormularioCompleto1().get(linea).setFwdDescripcion("3.-DEPARTAMENTO N°");
                getNuevoFormularioCompleto1().get(linea).setFwdLinea(linea);
                getNuevoFormularioCompleto1().get(linea).setFwdGrupo(grupo);
                getNuevoFormularioCompleto1().get(linea).setFwdVariable("VAR_01");
                break;
            case 4:
                getNuevoFormularioCompleto1().get(linea).setFwdDescripcion("4.-BODEGA N°");
                getNuevoFormularioCompleto1().get(linea).setFwdLinea(linea);
                getNuevoFormularioCompleto1().get(linea).setFwdGrupo(grupo);
                getNuevoFormularioCompleto1().get(linea).setFwdVariable("VAR_01");
                break;
            case 5:
                getNuevoFormularioCompleto1().get(linea).setFwdDescripcion("5.-LOCAL/ALMACEN N°");
                getNuevoFormularioCompleto1().get(linea).setFwdLinea(linea);
                getNuevoFormularioCompleto1().get(linea).setFwdGrupo(grupo);
                getNuevoFormularioCompleto1().get(linea).setFwdVariable("VAR_01");
                break;
            case 6:
                getNuevoFormularioCompleto1().get(linea).setFwdDescripcion("6.-PARQUEADERO N°");
                getNuevoFormularioCompleto1().get(linea).setFwdLinea(linea);
                getNuevoFormularioCompleto1().get(linea).setFwdGrupo(grupo);
                getNuevoFormularioCompleto1().get(linea).setFwdVariable("VAR_01");
                break;
            case 7:
                getNuevoFormularioCompleto1().get(linea).setFwdDescripcion("7.-OFICINA N°");
                getNuevoFormularioCompleto1().get(linea).setFwdLinea(linea);
                getNuevoFormularioCompleto1().get(linea).setFwdGrupo(grupo);
                getNuevoFormularioCompleto1().get(linea).setFwdVariable("VAR_01");
                break;
            case 8:
                getNuevoFormularioCompleto1().get(linea).setFwdDescripcion("8.-SUITE N°");
                getNuevoFormularioCompleto1().get(linea).setFwdLinea(linea);
                getNuevoFormularioCompleto1().get(linea).setFwdGrupo(grupo);
                getNuevoFormularioCompleto1().get(linea).setFwdVariable("VAR_01");
                break;
            case 9:
                getNuevoFormularioCompleto1().get(linea).setFwdDescripcion("9.-SECADERO N°");
                getNuevoFormularioCompleto1().get(linea).setFwdLinea(linea);
                getNuevoFormularioCompleto1().get(linea).setFwdGrupo(grupo);
                getNuevoFormularioCompleto1().get(linea).setFwdVariable("VAR_01");
                nuevoFormularioCompleto1.add(nuevoFormulario);
                break;
            case 10:
                getNuevoFormulario().setFwdDescripcion("10.- OTRAS ALÍCUOTAS");
                getNuevoFormulario().setFwdLinea(linea);
                getNuevoFormulario().setFwdGrupo(grupo);
                getNuevoFormulario().setFwdVariable("VAR_01");
                nuevoFormularioCompleto1.set(linea, nuevoFormulario);
                break;
            case 11:
                getNuevoFormulario().setFwdDescripcion("11");
                getNuevoFormulario().setFwdLinea(linea);
                getNuevoFormulario().setFwdGrupo(grupo);
                getNuevoFormulario().setFwdVariable("VAR_01");
                nuevoFormularioCompleto1.set(linea, nuevoFormulario);
                break;
            case 12:
                getNuevoFormulario().setFwdDescripcion("12");
                getNuevoFormulario().setFwdLinea(linea);
                getNuevoFormulario().setFwdGrupo(grupo);
                getNuevoFormulario().setFwdVariable("VAR_01");
                nuevoFormularioCompleto1.set(linea, nuevoFormulario);
                break;
            case 13:
                getNuevoFormulario().setFwdDescripcion("N°.CERTIFICADOS");
                getNuevoFormulario().setFwdLinea(linea);
                getNuevoFormulario().setFwdGrupo(grupo);
                getNuevoFormulario().setFwdVariable("VAR_01");
                nuevoFormularioCompleto1.set(linea, nuevoFormulario);
                break;
            case 14:
                getNuevoFormulario().setFwdDescripcion("DOCUMENTACIÓN QUE ADJUNTA");
                getNuevoFormulario().setFwdLinea(linea);
                getNuevoFormulario().setFwdGrupo(grupo);
                getNuevoFormulario().setFwdVariable("VAR_01");
                nuevoFormularioCompleto1.set(linea, nuevoFormulario);
                break;
        }
        System.out.print("" + descripcionLinea + "" + linea + "" + bool);
        bool = new String();
        nuevoFormulario = new FormularioWebDetalle();
    }

    public void añadirAFormulario3(int linea, String descripcionLinea) {
        auxLineaTabla = auxLineaTabla + linea;
        setGrupo("A003");

        getNuevoFormulario().setFwdDescripcion(descripcionLinea);
        getNuevoFormulario().setFwdLinea(auxLineaTabla);
        getNuevoFormulario().setFwdGrupo(grupo);
        getNuevoFormulario().setFwdVariable("VAR_01");
        nuevoFormularioCompleto2.add(nuevoFormulario);
        if (descripcionLinea.equals("NOMBRES PROPIETARIOS")) {
            listaParaMostrar.add(nuevoFormulario);
        }
        nuevoFormulario = new FormularioWebDetalle();
    }

    public void añadirAFormulario4(int linea, String descripcionLinea) {
        setGrupo("A004");
        getNuevoFormulario().setFwdDescripcion(descripcionLinea);
        getNuevoFormulario().setFwdLinea(linea);
        getNuevoFormulario().setFwdGrupo(grupo);
        getNuevoFormulario().setFwdVariable("VAR_01");
        nuevoFormularioCompleto3.set((linea - 1), nuevoFormulario);
        nuevoFormulario = new FormularioWebDetalle();
    }

    public void añadirAFormulario5(int linea, String descripcionLinea) {
        setGrupo("A005");
        getNuevoFormulario().setFwdDescripcion(descripcionLinea);
        getNuevoFormulario().setFwdLinea(linea);
        getNuevoFormulario().setFwdGrupo(grupo);
        getNuevoFormulario().setFwdVariable("VAR_01");
        nuevoFormularioCompleto4.set((linea - 1), nuevoFormulario);
        nuevoFormulario = new FormularioWebDetalle();
    }

    public void añadirAFormulario6(int linea, String descripcionLinea) {
        if ((jsfUtil.validadorDeCedula(getNuevoFormulario().getFwdValorC02())) == false) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Ingrese Cédula o RUC Válido ");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }
        setGrupo("A006");
        getNuevoFormulario().setFwdDescripcion(descripcionLinea);
        getNuevoFormulario().setFwdLinea(linea);
        getNuevoFormulario().setFwdGrupo(grupo);
        getNuevoFormulario().setFwdVariable("VAR_01");
        nuevoFormularioCompleto5.set((linea - 1), nuevoFormulario);
        nuevoFormulario = new FormularioWebDetalle();
    }

    public void añadirAFormulario7(int linea, String descripcionLinea) {
        setGrupo("A007");
        getNuevoFormulario().setFwdDescripcion(descripcionLinea);
        getNuevoFormulario().setFwdLinea(linea);
        getNuevoFormulario().setFwdGrupo(grupo);
        getNuevoFormulario().setFwdVariable("VAR_01");
        nuevoFormularioCompleto6.set((linea - 1), nuevoFormulario);
        nuevoFormulario = new FormularioWebDetalle();
    }

    public void guardarNuevoFormulario() throws ServicioExcepcion {
        if (comprobarBools == 0) {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Seleccione Almenos una Opción del 1-9 Descripción del Inmueble");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        } else {
            secuenciaControlador.generarSecuencia("FWC");
            setSecuenciaCabecera(getSecuenciaControlador().getControladorBb().getSecuencia());
            int auxSecuenciaCabecera = getSecuenciaCabecera().getSecActual();
            getSecuenciaCabecera().setSecActual(auxSecuenciaCabecera + 1);
            setSecuenciaFormularioCabecera(getSecuenciaControlador().getControladorBb().getNumeroTramite());
            secuenciaServicio.guardar(getSecuenciaCabecera());
            setTipoDocumentoWeb(servicioFormularioWebDetalle.obtenerTipoDocumentoWeb(nombreFormulario));

            int tamañoDeListaFormulario1 = nuevoFormularioCompleto1.size();
            int tamañoDeListaFormulario2 = nuevoFormularioCompleto2.size();
            int tamañoDeListaFormulario3 = nuevoFormularioCompleto3.size();
            int tamañoDeListaFormulario4 = nuevoFormularioCompleto4.size();
            int tamañoDeListaFormulario5 = nuevoFormularioCompleto5.size();
            int tamañoDeListaFormulario6 = nuevoFormularioCompleto6.size();
            getCabeceraNuevoFormulario().setFlwId(secuenciaFormularioCabecera);
            getCabeceraNuevoFormulario().setFlwFecha(Calendar.getInstance().getTime());
            getCabeceraNuevoFormulario().setFlwFHR(Calendar.getInstance().getTime());
            getCabeceraNuevoFormulario().setFlwObservacion("PRUEBA");
            getCabeceraNuevoFormulario().setFlwUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            getCabeceraNuevoFormulario().setFlwEstado("A");
            getCabeceraNuevoFormulario().setFlwCodigoIso("ISO0002");
            getCabeceraNuevoFormulario().setTdwId(tipoDocumentoWeb);

            servicioFormularioWeb.create(cabeceraNuevoFormulario);
            for (int i = 0; i < tamañoDeListaFormulario1; i++) {

                secuenciaControlador.generarSecuencia("FWD");
                setSecuenciaDetalle(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuenciaDetalle = getSecuenciaDetalle().getSecActual();
                getSecuenciaDetalle().setSecActual(auxSecuenciaDetalle + 1);
                setSecuenciaFormularioDetalle(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                secuenciaServicio.guardar(getSecuenciaDetalle());

                getNuevoFormularioCompleto1().get(i).setFwdId(secuenciaFormularioDetalle);
                getNuevoFormularioCompleto1().get(i).setFlwId(cabeceraNuevoFormulario);

                servicioFormularioWebDetalle.create(nuevoFormularioCompleto1.get(i));
            }

            for (int i = 0; i < tamañoDeListaFormulario2; i++) {

                secuenciaControlador.generarSecuencia("FWD");
                setSecuenciaDetalle(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuenciaDetalle = getSecuenciaDetalle().getSecActual();
                getSecuenciaDetalle().setSecActual(auxSecuenciaDetalle + 1);
                setSecuenciaFormularioDetalle(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                secuenciaServicio.guardar(getSecuenciaDetalle());

                getNuevoFormularioCompleto2().get(i).setFwdId(secuenciaFormularioDetalle);
                getNuevoFormularioCompleto2().get(i).setFlwId(cabeceraNuevoFormulario);

                servicioFormularioWebDetalle.create(nuevoFormularioCompleto2.get(i));
            }

            for (int i = 0; i < tamañoDeListaFormulario3; i++) {

                secuenciaControlador.generarSecuencia("FWD");
                setSecuenciaDetalle(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuenciaDetalle = getSecuenciaDetalle().getSecActual();
                getSecuenciaDetalle().setSecActual(auxSecuenciaDetalle + 1);
                setSecuenciaFormularioDetalle(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                secuenciaServicio.guardar(getSecuenciaDetalle());

                getNuevoFormularioCompleto3().get(i).setFwdId(secuenciaFormularioDetalle);
                getNuevoFormularioCompleto3().get(i).setFlwId(cabeceraNuevoFormulario);

                servicioFormularioWebDetalle.create(nuevoFormularioCompleto3.get(i));
            }

            for (int i = 0; i < tamañoDeListaFormulario4; i++) {

                secuenciaControlador.generarSecuencia("FWD");
                setSecuenciaDetalle(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuenciaDetalle = getSecuenciaDetalle().getSecActual();
                getSecuenciaDetalle().setSecActual(auxSecuenciaDetalle + 1);
                setSecuenciaFormularioDetalle(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                secuenciaServicio.guardar(getSecuenciaDetalle());

                getNuevoFormularioCompleto4().get(i).setFwdId(secuenciaFormularioDetalle);
                getNuevoFormularioCompleto4().get(i).setFlwId(cabeceraNuevoFormulario);

                servicioFormularioWebDetalle.create(nuevoFormularioCompleto4.get(i));
            }

            for (int i = 0; i < tamañoDeListaFormulario5; i++) {

                secuenciaControlador.generarSecuencia("FWD");
                setSecuenciaDetalle(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuenciaDetalle = getSecuenciaDetalle().getSecActual();
                getSecuenciaDetalle().setSecActual(auxSecuenciaDetalle + 1);
                setSecuenciaFormularioDetalle(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                secuenciaServicio.guardar(getSecuenciaDetalle());

                getNuevoFormularioCompleto5().get(i).setFwdId(secuenciaFormularioDetalle);
                getNuevoFormularioCompleto5().get(i).setFlwId(cabeceraNuevoFormulario);

                servicioFormularioWebDetalle.create(nuevoFormularioCompleto5.get(i));
            }

            for (int i = 0; i < tamañoDeListaFormulario6; i++) {

                secuenciaControlador.generarSecuencia("FWD");
                setSecuenciaDetalle(getSecuenciaControlador().getControladorBb().getSecuencia());
                int auxSecuenciaDetalle = getSecuenciaDetalle().getSecActual();
                getSecuenciaDetalle().setSecActual(auxSecuenciaDetalle + 1);
                setSecuenciaFormularioDetalle(getSecuenciaControlador().getControladorBb().getNumeroTramite());
                secuenciaServicio.guardar(getSecuenciaDetalle());

                getNuevoFormularioCompleto6().get(i).setFwdId(secuenciaFormularioDetalle);
                getNuevoFormularioCompleto6().get(i).setFlwId(cabeceraNuevoFormulario);

                servicioFormularioWebDetalle.create(nuevoFormularioCompleto6.get(i));
            }
            servicioFormularioWebArchivo.create(archivoFormulario);
        }

    }

    public void listarParroquias() {
        setListaParroquias(servicioFormularioWebDetalle.listaParroquia());
    }

    public void generarRPC01() throws IOException, JRException, NamingException, SQLException {
        if (secuenciaFormularioCabecera != 0) {
            int flwId = secuenciaFormularioCabecera;
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("formularioWebId", flwId);
            reporteUtil.imprimirReporteEnPDF("RPC-01", "FormRPC-01-" + flwId, parametros);
        } else {
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Nose Puede Descargar Formulario Sin Haber Guardado ");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
        }

    }

    public void buscarPersona() {
        try {
            setPersonaEncontrada(servicioFormularioWebDetalle.buscarPersona(getNuevoFormulario().getFwdValorC04()));
            getNuevoFormulario().setFwdValorC01(personaEncontrada.getPerNombre());
            getNuevoFormulario().setFwdValorC02(personaEncontrada.getPerApellidoPaterno());
            getNuevoFormulario().setFwdValorC03(personaEncontrada.getPerApellidoMaterno());
        } catch (Exception e) {
            setEstadoBoton("true");
            FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Persona No Encontrada");
            FacesContext.getCurrentInstance().addMessage("Error", facesMsg);

        }
    }

    public void redireccionar() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/ModuloWeb/TipoDocumentoWeb.xhtml");
    }

    public void subirArchivo(FileUploadEvent event) throws IOException, ServicioExcepcion {
        secuenciaControlador.generarSecuencia("FWC");
        setSecuenciaCabecera(getSecuenciaControlador().getControladorBb().getSecuencia());
        int auxSecuenciaCabecera = getSecuenciaCabecera().getSecActual();
        getSecuenciaCabecera().setSecActual(auxSecuenciaCabecera + 1);
        setSecuenciaFormularioCabecera(getSecuenciaControlador().getControladorBb().getNumeroTramite());

        secuenciaControlador.generarSecuencia("FWA");
        setSecuenciaArchivo(getSecuenciaControlador().getControladorBb().getSecuencia());
        int auxSecuenciaArchivo = getSecuenciaCabecera().getSecActual();
        getSecuenciaArchivo().setSecActual(auxSecuenciaArchivo + 1);
        setSecuenciaFormularioWebArchivo(getSecuenciaControlador().getControladorBb().getNumeroTramite());
        secuenciaServicio.guardar(getSecuenciaArchivo());

        InputStream is = event.getFile().getInputstream();
        System.out.println(" extension:" + event.getFile().getContentType());
        Boolean exitoSubirArchivo = false;
//            Path folder;
        String extension = "pdf";
        Path file;
        String nombreArchivo = "FormRPC01" + secuenciaFormularioCabecera;
        String dirPrincipal = "F:\\RM\\";
        String subDirectorio = "FormulariosWeb\\";
        Path direccion = null;

        try (InputStream input = is) {
            direccion = Paths.get(dirPrincipal + subDirectorio + nombreArchivo + "." + extension);
            file = Files.createFile(direccion);
            Files.copy(input, file, StandardCopyOption.REPLACE_EXISTING);
            exitoSubirArchivo = true;
        } catch (Exception e) {
            exitoSubirArchivo = false;
            JsfUtil.addErrorMessage("Error al subir archivo");
            e.printStackTrace();
        }

        cabeceraNuevoFormulario.setFlwId(secuenciaFormularioCabecera);
        getArchivoFormulario().setFwaId(secuenciaFormularioWebArchivo);
        getArchivoFormulario().setFlwId(getCabeceraNuevoFormulario());
        getArchivoFormulario().setFwaNombreArchivo(nombreArchivo);
        getArchivoFormulario().setFwaPath(direccion.getFileName().toString());
        getArchivoFormulario().setFwaEstado("A");
        getArchivoFormulario().setFwaFHR(Calendar.getInstance().getTime());
        getArchivoFormulario().setFwaUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
    }

}
