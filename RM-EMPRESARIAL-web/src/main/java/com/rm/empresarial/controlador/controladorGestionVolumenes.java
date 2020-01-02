package com.rm.empresarial.controlador;


import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.dao.ConfigDetalleDao;
import com.rm.empresarial.dao.PrestamoVolumenDao;
import com.rm.empresarial.dao.TomoDao;
import com.rm.empresarial.dao.VolumenDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.PrestamoVolumen;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.Tomo;
import com.rm.empresarial.modelo.Volumen;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

@Named("controladorGestionVolumenes")
@ViewScoped
public class controladorGestionVolumenes implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private List<ConfigDetalle> año;

    @Getter
    @Setter
    private String datos;

    @Getter
    @Setter
    private String perIdentificacion;

    @Getter
    @Setter
    private String perTipoIdentificacion;

    @Getter
    @Setter
    private List<Tomo> Tomo;

    @Getter
    @Setter
    private List<String> añoCombo;

    @Getter
    @Setter
    private List<Persona> persona;

    @Getter
    @Setter
    private Persona personaSeleccionada;

    @Getter
    @Setter
    private List<Volumen> listaVolumen;

    @Getter
    @Setter
    private List<PrestamoVolumen> listaPrestamoVolumen;

    @Getter
    @Setter
    private Volumen volumenSeleccionado;

    @Getter
    @Setter
    private String fechaIngreso;

    @EJB
    private TomoDao tomoDao;

    @EJB
    private ConfigDetalleDao configDetalleDao;

    @EJB
    private PrestamoVolumenDao prestamoVolumenDao;

    @EJB
    private VolumenDao volumenDao;

    @Getter
    @Setter
    private boolean estado = true;

    @Getter
    @Setter
    private String anio;

    @Getter
    @Setter
    private int id;

    @Getter
    @Setter
    private String prvNombre;

    @Getter
    @Setter
    private String prvApellido;

    @Getter
    @Setter
    private Date prvFechaIni;

    @Getter
    @Setter
    private Date prvFechaFin;

    @Getter
    @Setter
    private TipoLibro tipoLibroSeleccionado;

    @Getter
    @Setter
    private PrestamoVolumen prestamoVolumen;

    @Getter
    @Setter
    private PrestamoVolumen itemsPrestamoVolumen;


    @PostConstruct

    public void post() {
        try {
            añoCombo();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(controladorGestionVolumenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public controladorGestionVolumenes() {

        prestamoVolumen = new PrestamoVolumen();
    }

    public void añoCombo() throws ServicioExcepcion {
        setAño(configDetalleDao.listarPorConfigDesc("ANIO"));
    }

    public void limpiar() throws ServicioExcepcion {
        setPrvNombre(null);
        setPrvApellido(null);
        setPrvFechaIni(null);
        setPrvFechaFin(null);
        setPerIdentificacion(null);
        setPerTipoIdentificacion(null);

    }

    public void filtro() throws ServicioExcepcion {
        setEstado(false);
    }

    public void listaPrestamoVolumenId(Long volumenId, Volumen volumen) throws ServicioExcepcion {

        setVolumenSeleccionado(volumen);
        setPrvNombre(null);
        setPrvApellido(null);
        setPrvFechaIni(null);
        setPrvFechaFin(null);
        setListaPrestamoVolumen(prestamoVolumenDao.listarPretamoVolumenPorId(volumenId));

    }

    public void crearPrestamo() throws ServicioExcepcion {
        prestamoVolumen = new PrestamoVolumen();
        try {
            switch (getPerTipoIdentificacion()) {
                case "C":
                    if (validadorDeCedula(getPerIdentificacion())) {
                       
                            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String ini = formatter.format(getPrvFechaIni());
                            String fin = formatter.format(getPrvFechaFin());

                            if (fin.compareTo(ini) < 0) {
                                JsfUtil.addErrorMessage("La fecha Inicial no puede se mayor que la Final");
                            } else {
                                prestamoVolumen.setPrvIdentificacion(getPerIdentificacion());
                                prestamoVolumen.setPrvTipoIdentificacion(getPerTipoIdentificacion());
                                prestamoVolumen.setPrvNombre(getPrvNombre());
                                prestamoVolumen.setPrvApellido(getPrvApellido());
                                prestamoVolumen.setPrvEstado("A");
                                prestamoVolumen.setPrvFechaInicio(getPrvFechaIni());
                                prestamoVolumen.setPrvFechaFin(getPrvFechaFin());
                                prestamoVolumen.setPrvFHR(Calendar.getInstance().getTime());
                                prestamoVolumen.setVleId(volumenSeleccionado);
                                prestamoVolumen.setPrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                prestamoVolumenDao.create(prestamoVolumen);
                                FacesContext context = FacesContext.getCurrentInstance();
                                context.addMessage(null, new FacesMessage("Creado", "Se creo correctamente"));
                                setPrvNombre(null);
                                setPrvApellido(null);
                                setPrvFechaIni(null);
                                setPrvFechaFin(null);
                                setPerIdentificacion(null);
                                setPerTipoIdentificacion(null);
                                setListaPrestamoVolumen(prestamoVolumenDao.listarPretamoVolumenPorId(volumenSeleccionado.getVleId()));
                            }
                        
                    } else {
                         JsfUtil.addErrorMessage("La cedula ingresada es incorrecta");
                    }
                    break;
                case "R":
                    if (validaRuc(getPerIdentificacion())) {
                       
                            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                            String ini = formatter.format(getPrvFechaIni());
                            String fin = formatter.format(getPrvFechaFin());

                            if (fin.compareTo(ini) < 0) {
                                JsfUtil.addErrorMessage("La fecha Inicial no puede se mayor que la Final");
                            } else {
                                prestamoVolumen.setPrvIdentificacion(getPerIdentificacion());
                                prestamoVolumen.setPrvTipoIdentificacion(getPerTipoIdentificacion());
                                prestamoVolumen.setPrvNombre(getPrvNombre());
                                prestamoVolumen.setPrvApellido(getPrvApellido());
                                prestamoVolumen.setPrvEstado("A");
                                prestamoVolumen.setPrvFechaInicio(getPrvFechaIni());
                                prestamoVolumen.setPrvFechaFin(getPrvFechaFin());
                                prestamoVolumen.setPrvFHR(Calendar.getInstance().getTime());
                                prestamoVolumen.setVleId(volumenSeleccionado);
                                prestamoVolumen.setPrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                                prestamoVolumenDao.create(prestamoVolumen);
                                FacesContext context = FacesContext.getCurrentInstance();
                                context.addMessage(null, new FacesMessage("Creado", "Se creo correctamente"));
                                setPrvNombre(null);
                                setPrvApellido(null);
                                setPrvFechaIni(null);
                                setPrvFechaFin(null);
                                setPerIdentificacion(null);
                                setPerTipoIdentificacion(null);
                                setListaPrestamoVolumen(prestamoVolumenDao.listarPretamoVolumenPorId(volumenSeleccionado.getVleId()));
                            }

                        
                    } else {
                         JsfUtil.addErrorMessage("El ruc ingresado es incorrecto.");
                    }
                    break;
                case "P":
                   
                        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                        String ini = formatter.format(getPrvFechaIni());
                        String fin = formatter.format(getPrvFechaFin());

                        if (fin.compareTo(ini) < 0) {
                            JsfUtil.addErrorMessage("La fecha Inicial no puede se mayor que la Final");
                        } else {
                            prestamoVolumen.setPrvIdentificacion(getPerIdentificacion());
                            prestamoVolumen.setPrvTipoIdentificacion(getPerTipoIdentificacion());
                            prestamoVolumen.setPrvNombre(getPrvNombre());
                            prestamoVolumen.setPrvApellido(getPrvApellido());
                            prestamoVolumen.setPrvEstado("A");
                            prestamoVolumen.setPrvFechaInicio(getPrvFechaIni());
                            prestamoVolumen.setPrvFechaFin(getPrvFechaFin());
                            prestamoVolumen.setPrvFHR(Calendar.getInstance().getTime());
                            prestamoVolumen.setVleId(volumenSeleccionado);
                            prestamoVolumen.setPrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                            prestamoVolumenDao.create(prestamoVolumen);
                            FacesContext context = FacesContext.getCurrentInstance();
                            context.addMessage(null, new FacesMessage("Creado", "Se creo correctamente"));
                            setPrvNombre(null);
                            setPrvApellido(null);
                            setPrvFechaIni(null);
                            setPrvFechaFin(null);
                            setPerIdentificacion(null);
                            setPerTipoIdentificacion(null);
                            setListaPrestamoVolumen(prestamoVolumenDao.listarPretamoVolumenPorId(volumenSeleccionado.getVleId()));
                        }
                    
                    break;
            }
        } catch (ServicioExcepcion e) {
            JsfUtil.addErrorMessage("No se creo correctamente");
        }
    }

    public void listarVolumenes(TipoLibro tplId, String año) throws ServicioExcepcion {
        setListaVolumen(null);
        if (tplId == null || año == null) {
            JsfUtil.addErrorMessage("Error elija correctamente");
        } else {
            setTomo(tomoDao.listarPorAnioYtipoLib(tplId.getTplId(), año));
            if (getTomo() == null) {
                JsfUtil.addErrorMessage("Error elija correctamente");
            } else {
                for (Tomo tomo : Tomo) {
                    setListaVolumen(volumenDao.listarPorTipoYaño(tomo.getTomId()));
                }

            }

        }

    }

    public void entregar(PrestamoVolumen prestamoVol) {
        try {
            prestamoVol.setPrvEstado("I");
            prestamoVol.setPrvUserEntrega(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
            prestamoVol.setPrvFHREntrega(Calendar.getInstance().getTime());
            prestamoVolumenDao.edit(prestamoVol);
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("Entregado Correctamente", "Se entrego correctamente"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error al entregar ");

        }

    }

    public void llenarPersona() {
        setPrvNombre(personaSeleccionada.getPerNombre());
        setPrvApellido(personaSeleccionada.getPerApellidoPaterno());

    }
    
    public boolean validadorDeCedula(String cedula) {
        Boolean cedulaCorrecta = Boolean.FALSE;

        try {

            if (cedula.length() == 10) // ConstantesApp.LongitudCedula
            {
                int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
                if (tercerDigito < 6) {
// Coeficientes de validación cédula
// El decimo digito se lo considera dígito verificador
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }

                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;
                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }
        } catch (NumberFormatException nfe) {
            cedulaCorrecta = false;
        } catch (Exception err) {
            System.out.println("Una excepcion ocurrio en el proceso de validadcion");
            cedulaCorrecta = false;
        }

        if (!cedulaCorrecta) {
            System.out.println("La Cédula ingresada es Incorrecta");
        }
        return cedulaCorrecta;
    }

    public boolean validaRuc(String ruc) {
        try {
            boolean resultado = true;
            
            if (ruc.length() != 13){
               resultado = false;                 
            } else {
                resultado = true;                  
            }                
            return resultado;
        } catch (NumberFormatException x) {
            return false;
        }
    }
}
