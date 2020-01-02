package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.ControladorLibroNegroBb;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.JsfUtil.PersistAction;
import com.rm.empresarial.dao.LibroNegroDao;
import com.rm.empresarial.dao.MatriculacionDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.LibroNegro;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.RepositorioSRI;
import com.rm.empresarial.servicio.LibroNegroServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.RepositorioSRIServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Bryan_Mora
 */
@ViewScoped
@Named
public class ControladorLibroNegro implements Serializable {

    //servicios
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;
    @EJB
    LibroNegroDao LibroNegroDao;

    @EJB
    private PersonaServicio servicioPersona;

    @EJB
    private RepositorioSRIServicio repositorioSRIServicio;

    @EJB
    private LibroNegroServicio servicioLibroNegro;

    //objetos y variables
    @Getter
    @Setter
    private Date fhr;
    @Getter
    @Setter
    private Date fechaActualizacion;
    @Getter
    @Setter
    private LibroNegro libroNegro;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private RepositorioSRI repositorioSRI;
    @Getter
    @Setter
    private Boolean nuevoIngreso;

    @Getter
    @Setter
    private List<LibroNegro> listaLibroNegro;
    @Getter
    @Setter
    private String identificacionParaBusqueda;
    @Getter
    @Setter
    private String FHRLista;
    @Getter
    @Setter
    private String fechaLista;
    @Getter
    @Setter
    private String habilitacionBotonGuardar = "true";
    @Getter
    @Setter
    private LibroNegro cambioEstadoLibroNegro;
    @Getter
    @Setter
    private String bloquearCampoObservacion = "false";
    @Getter
    @Setter
    private List<String> listaFHR = new ArrayList<String>();

    @Getter
    @Setter
    private List<LibroNegro> listaPrueba = new ArrayList<>();
    @Getter
    @Setter
    private LibroNegro libroNegroPrueba;

    public ControladorLibroNegro() {
        System.out.println("com.rm.empresarial.controlador.ControladorLibroNegro.<init>()");
        libroNegro = new LibroNegro();

        repositorioSRI = new RepositorioSRI();
        nuevoIngreso = false;
    }

    @PostConstruct
    public void inicializar() {
        persona = new Persona();

        fhr = Calendar.getInstance().getTime();
        fechaActualizacion = Calendar.getInstance().getTime();
    }

    public void cargarLibroNegro() throws ServicioExcepcion {

        libroNegro = servicioLibroNegro.buscarPorIdentificacion(getIdentificacion());

    }

    public void buscarPersona() throws IOException {
        System.out.println("buscando...");

        try {//INTENTO BUSCAR

            if (LibroNegroDao.buscarPorIdentificacion(identificacion) != null) {
                //CARGANDO PERSONA DE TABLA LIBRO NEGRO
                setLibroNegro(servicioLibroNegro.buscarPorIdentificacion(identificacion));
                persona = new Persona();
                if (servicioPersona.encontrarPersonaPorIdentificacion(getIdentificacion()) != null) {                        //devuelve la persona con perId registrado en el libro negro
                    //LLENO DATOS PERSONA

                    persona = servicioPersona.encontrarPersonaPorIdentificacion(getIdentificacion());
                    persona.setPerIdentificacion(getLibroNegro().getLbnPerIdentificacion());
                    persona.setPerNombre(getLibroNegro().getLbnPerNombre());
                    persona.setPerApellidoPaterno(getLibroNegro().getLbnPerApellidoPaterno());
                    persona.setPerApellidoMaterno(getLibroNegro().getLbnPerApellidoMaterno());
                    setPersona(persona);
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargando de libro negro", ""));
                    cargarListaParaTabla();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Posible doble registro de identificación", ""));
                }
            } else {
                //CARGANDO PERSONA DE TABLA PERSONA

                if (getIdentificacion() != null && getIdentificacion().length() < 13) {
                    setPersona(servicioPersona.encontrarPersonaPorIdentificacion(getIdentificacion()));

                }
                if (getIdentificacion().length() == 13) {
                    System.out.print("Ingresa a la validacion de ruc");
                    setRepositorioSRI(repositorioSRIServicio.buscarPorIdentificacion(getIdentificacion()));
                    transformarPersona();
                }

                libroNegro.setLbnFHR(Calendar.getInstance());
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cargando de Persona", ""));

            }
            setHabilitacionBotonGuardar("false");

        } catch (ServicioExcepcion ex) {//FALLO AL BUSCAR
            nuevoIngreso = true;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Persona no encontrada", ""));

            Logger.getLogger(RecepcionDocumentacionControlador.class.getName()).log(Level.SEVERE, null, ex);
            setHabilitacionBotonGuardar("true");
        }

        // capturar identificacion para utilizar en la busqueda e imprimir en tabla
        setIdentificacionParaBusqueda(identificacion);
    }

    public void transformarPersona() {
        getPersona().setPerIdentificacion(getRepositorioSRI().getSriRuc());
        getPersona().setPerNombre(getRepositorioSRI().getSriRazonSocial());
        getPersona().setPerDireccion(getRepositorioSRI().getSriCalle() + " " + getRepositorioSRI().getSriInterseccion());

    }

    public void guardar() throws ServicioExcepcion {

        //llenando datos de persona en el objeto de LibroNegro
        getLibroNegro().setLbnPerIdentificacion(getPersona().getPerIdentificacion());
        getLibroNegro().setLbnPerApellidoPaterno(getPersona().getPerApellidoPaterno().toUpperCase());
        getLibroNegro().setLbnPerApellidoMaterno(getPersona().getPerApellidoMaterno().toUpperCase());
        getLibroNegro().setLbnPerNombre(getPersona().getPerNombre().toUpperCase());
        getLibroNegro().setPerId(persona);
        //llenando datos obligatorios del objeto de LibroNegro
        getLibroNegro().setLbnFHR(Calendar.getInstance());
        getLibroNegro().setLbnFechaActualizacion(Calendar.getInstance());
        getLibroNegro().setLbnUser(getDataManagerSesion().getUsuarioLogeado().getUsuLogin());
        getLibroNegro().setLbnEstado("A");
        getLibroNegro().setLbnUserActualizador(getDataManagerSesion().getUsuarioLogeado().getUsuLogin());

        //- el valor del estado se envia directamente desde la vista
        //lenando campos opcionales
//        getLibroNegro().setLbnFHR(calendar);
        //lamada a persistir
        create();

        //Quemar lista de libro negro 
        setListaLibroNegro(LibroNegroDao.ListarLibroNegro(identificacionParaBusqueda));

        setHabilitacionBotonGuardar("true");
        

    }

    public void limpiar() {
        libroNegro = new LibroNegro();
        persona = new Persona();
        identificacion = "";
        nuevoIngreso = false;
        PrimeFaces.current().resetInputs(":form:panelRegistro");
        setListaLibroNegro(null);
        setHabilitacionBotonGuardar("true");
        setBloquearCampoObservacion("false");
    }

    public void salir() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");

    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LibroNegroCreated"));
        limpiar();
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (getLibroNegro() != null) {
            try {
                if (persistAction != PersistAction.DELETE) {
                    if (nuevoIngreso) {
                        System.out.println("Ingresando nuevo...");
                        //crea una nueva persona
                        Calendar fecha = new GregorianCalendar(1753, 1, 1, 0, 0, 0);
                        persona = new Persona(servicioPersona.asignarID(), " ", identificacion,
                                getLibroNegro().getLbnPerNombre().toUpperCase(), getLibroNegro().getLbnPerApellidoPaterno().toUpperCase(), getLibroNegro().getLbnPerApellidoMaterno().toUpperCase(),
                                fecha.getTime(), " ",
                                " ", " ", " ", " ",
                                " ", " ", dataManagerSesion.getUsuarioLogeado().getUsuLogin(),
                                fecha.getTime(), BigInteger.ZERO, " ");
                        libroNegro.setLbnFHR(Calendar.getInstance());
                        libroNegro.setLbnPerIdentificacion(identificacion);
                        libroNegro.setPerId(persona);
                        servicioPersona.create(persona);
                        libroNegro.setPerId(servicioPersona.encontrarPersonaPorIdentificacion(identificacion));
                        servicioLibroNegro.create(libroNegro);
                    } else {
                        System.out.println("Actualizando...");
                        servicioLibroNegro.create(getLibroNegro());
                    }
                }
//                else {
//                    getFacade().remove(selected);
//                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public void cargarListaParaTabla() throws ServicioExcepcion, ServicioExcepcion {
        //Quemar lista de libro negro 
        setListaLibroNegro(LibroNegroDao.ListarLibroNegro(identificacion));
        //trasnformando gregoryan calendar a string

        setHabilitacionBotonGuardar("false");
    }

    public void cambiarEstadoLibroNegro() {
        LibroNegroDao.actualizarEstadoLibroNegro(cambioEstadoLibroNegro);
    }

//    public void insertadoMasivo() throws ServicioExcepcion {
//        setListaPrueba(LibroNegroDao.ListarPrueba());
//
//        for (int i = 0; i < listaPrueba.size(); i++) {
//            setLibroNegroPrueba(getListaPrueba().get(i));
//            for (int j = 0; j < 20; j++) {
//                LibroNegroDao.create(libroNegroPrueba);
//            }
//        }
//
//    }

    //    public void update() {
//        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LibroNegroUpdated"));
//    }
//
//    public void destroy() {
//        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("LibroNegroDeleted"));
//        if (!JsfUtil.isValidationFailed()) {
//            selected = null; // Remove selection
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
//    }
//
//    public List<LibroNegro> getItems() {
//        if (items == null) {
//            items = getFacade().findAll();
//        }
//        return items;
//    }
}
