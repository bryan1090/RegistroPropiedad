/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.EncryptPassword;
import com.rm.empresarial.controlador.util.EnviarEmailController;
import com.rm.empresarial.controlador.util.JsfUtil;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.controlador.util.VerificarReCaptcha;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.CodigoVerificacion;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.HistoricoContrasenia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Rol;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CodigoVerificacionServicio;
import com.rm.empresarial.servicio.FacturaServicio;
import com.rm.empresarial.servicio.HistoricoContraseniaServicio;
import com.rm.empresarial.servicio.PersonaServicio;
import com.rm.empresarial.servicio.RolServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import com.rm.empresarial.servicio.ZonaServicio;
import java.io.Serializable;
import java.util.Calendar;
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
import org.apache.commons.mail.EmailException;
import org.primefaces.PrimeFaces;
import org.slf4j.MDC;

/**
 *
 * @author Java
 */
@Named(value = "controladorRegistro")
@ViewScoped
public class ControladorRegistro implements Serializable {

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private Usuario usuario;

    @Getter
    @Setter
    private HistoricoContrasenia historicoContrasenia;

    @Getter
    @Setter
    private CodigoVerificacion codigoVerificacion;

    @Getter
    @Setter
    private String estadoBoton;

    @Getter
    @Setter
    private String msgReCaptcha;

    @Getter
    @Setter
    private String txtNfactura;

    @Getter
    @Setter
    private String txtCodigo;

    @Getter
    @Setter
    private boolean disableInput;

    private JsfUtil jsfUtil;

    @EJB
    private PersonaServicio personaServicio;

    @EJB
    private UsuarioServicio usuarioServicio;

    @EJB
    private RolServicio rolServicio;

    @EJB
    private ZonaServicio zonaServicio;

    @EJB
    private CodigoVerificacionServicio codigoVerificacionServicio;

    @EJB
    private HistoricoContraseniaServicio historicoContraseinaServicio;

    @EJB
    private FacturaServicio facturaServicio;

    @Inject
    private DataManagerSesion dataManagerSesion;

    @Inject
    private EncryptPassword encryptPassword;

    @Inject
    private EnviarEmailController enviarEmail;

    /**
     * Creates a new instance of ControladorCrearPersona
     */
    @PostConstruct
    public void init() {
        persona = new Persona();
        usuario = new Usuario();
        historicoContrasenia = new HistoricoContrasenia();
        codigoVerificacion = new CodigoVerificacion();
        persona.setPerTipoIdentificacion("N");
        persona.setPerDireccion("");
        jsfUtil = new JsfUtil();
        disableInput = false;
        msgReCaptcha = "";
        txtNfactura = "";
        txtCodigo = "";
    }

    private void prepararPersona() throws ServicioExcepcion, Exception {
        getPersona().setPerId(personaServicio.asignarID());
        getPersona().setPerEstado("A");
        Persona personaConyugue = personaServicio.buscarPersonaPorId((long) 0);
        getPersona().setPerIdConyuge(personaConyugue);
        getPersona().setPerTipoContribuyente("1");
        getPersona().setPerUser("REGISTROWEB");
    }

    private void prepararUsuario() throws ServicioExcepcion, Exception {
        usuario.setRolId(rolServicio.encontrarRolPorNom("WEB"));
        usuario.setUsuLogin(getPersona().getPerIdentificacion());
        usuario.setUsuContrasenia(encryptPassword.encrypt(getPersona().getPerIdentificacion()));
        usuario.setUsuEmail(persona.getPerEmail());
        usuario.setUsuEstado("R");
        usuario.setUsuUser("REGISTROWEB");
        usuario.setUsuFHR(Calendar.getInstance().getTime());
        usuario.setPerId(personaServicio.buscarPersona(getPersona().getPerIdentificacion()));
        usuario.setZonId(zonaServicio.encontrarPrimerRegistro());
        usuario.setUsuFechaInicio(Calendar.getInstance().getTime());
        usuario.setUsuFechaFin(Calendar.getInstance().getTime());
    }

    private void prepararHistContr() {
        historicoContrasenia.setUsuId(usuario);
        historicoContrasenia.setHisConPas(usuario.getUsuContrasenia());
        historicoContrasenia.setHisConFHR(Calendar.getInstance().getTime());
        historicoContrasenia.setHisConUser("REGISTROWEB");
    }

    private void prepararCodVerif() throws Exception {
        codigoVerificacion.setUsuId(usuarioServicio.encontrarUsuarioPorNombreUsuario(usuario.getUsuLogin()));
        codigoVerificacion.setCvnCodigo(encryptPassword.encrypt(codigoRandom()));
        codigoVerificacion.setCvnEstado("A");
        codigoVerificacion.setCvnFHR(Calendar.getInstance().getTime());
        codigoVerificacion.setCvnUser("REGISTROWEB");
    }

    private void enviarCodigoPorMail() throws ServicioExcepcion, Exception {
        try {
            String nombre = usuario.getPerId().getPerNombre().trim();
            String apellido = usuario.getPerId().getPerApellidoPaterno().trim();
            String asunto = "Código de verificación";
            String mensaje = "Estimado(a) " + nombre + " " + apellido + "\n \n Su código es: " + encryptPassword.decrypt(codigoVerificacion.getCvnCodigo());
            enviarEmail.enviaEmail(usuario.getUsuEmail(), asunto, mensaje);
        } catch (EmailException ex) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error: ", "Ocurrió un problema al enviar el código de confirmación a su correo"));
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

    public void crearPersona() throws Exception {
        try {
            String gRecaptchaResponse = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("g-recaptcha-response");
            if (VerificarReCaptcha.verify(gRecaptchaResponse)) {
                Factura fac = facturaServicio.buscarPorNumFacturaYcodigo(txtNfactura, txtCodigo);
                switch (getPersona().getPerTipoIdentificacion()) {
                    case "C":
                        if (fac != null) {
                            if (jsfUtil.validadorDeCedula(getPersona().getPerIdentificacion())) {
                                if (!personaServicio.personaDuplicado(getPersona().getPerIdentificacion())) {
                                    prepararPersona();
                                    personaServicio.guardar(getPersona());
                                    prepararUsuario();
                                    usuarioServicio.guardar(usuario);
                                    prepararHistContr();
                                    historicoContraseinaServicio.guardar(historicoContrasenia);
                                    prepararCodVerif();
                                    codigoVerificacionServicio.guardar(codigoVerificacion);
                                    enviarCodigoPorMail();
                                    FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro completo", "Se ha enviado un código de confirmación a su correo"));
                                    limpiar();
                                } else {
                                    Usuario auxUsu = usuarioServicio.encontrarUsuarioPorNombreUsuario(getPersona().getPerIdentificacion().trim());
                                    if (auxUsu == null) {
                                        personaServicio.edit(getPersona());
                                        prepararUsuario();
                                        usuarioServicio.guardar(usuario);
                                        prepararHistContr();
                                        historicoContraseinaServicio.guardar(historicoContrasenia);
                                        prepararCodVerif();
                                        codigoVerificacionServicio.guardar(codigoVerificacion);
                                        enviarCodigoPorMail();
                                        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro completo", "Se ha enviado un código de confirmación a su correo"));
                                        limpiar();
                                    } else {
                                        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "La identificación ingresada está relacionada con un usuario"));
                                        limpiar();
                                    }
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "La Cédula ingresada es incorrecta"));
                                limpiar();
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Número de factura o código incorrecto", ""));
                            limpiar();
                        }
                        break;
                    case "R":
                        if (fac != null) {
                            if (jsfUtil.validaRuc(getPersona().getPerIdentificacion())) {
                                if (!personaServicio.personaDuplicado(getPersona().getPerIdentificacion())) {
                                    prepararPersona();
                                    personaServicio.guardar(getPersona());
                                    prepararUsuario();
                                    usuarioServicio.guardar(usuario);
                                    prepararHistContr();
                                    historicoContraseinaServicio.guardar(historicoContrasenia);
                                    prepararCodVerif();
                                    codigoVerificacionServicio.guardar(codigoVerificacion);
                                    enviarCodigoPorMail();
                                    FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro completo", "Se ha enviado un código de confirmación a su correo"));
                                    limpiar();
                                } else {
                                    Usuario auxUsu = usuarioServicio.encontrarUsuarioPorNombreUsuario(getPersona().getPerIdentificacion().trim());
                                    if (auxUsu == null) {
                                        personaServicio.edit(getPersona());
                                        prepararUsuario();
                                        usuarioServicio.guardar(usuario);
                                        prepararHistContr();
                                        historicoContraseinaServicio.guardar(historicoContrasenia);
                                        prepararCodVerif();
                                        codigoVerificacionServicio.guardar(codigoVerificacion);
                                        enviarCodigoPorMail();
                                        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro completo", "Se ha enviado un código de confirmación a su correo"));
                                        limpiar();
                                    } else {
                                        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "La identificación ingresada está relacionada con un usuario"));
                                        limpiar();
                                    }
                                }
                            } else {
                                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "El Ruc ingresado es incorrecto"));
                                limpiar();
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Número de factura o código incorrecto", ""));
                            limpiar();
                        }
                        break;
                    case "P":
                        if (fac != null) {
                            if (!personaServicio.personaDuplicado(getPersona().getPerIdentificacion())) {
                                prepararPersona();
                                personaServicio.guardar(getPersona());
                                prepararUsuario();
                                usuarioServicio.guardar(usuario);
                                prepararHistContr();
                                historicoContraseinaServicio.guardar(historicoContrasenia);
                                prepararCodVerif();
                                codigoVerificacionServicio.guardar(codigoVerificacion);
                                enviarCodigoPorMail();
                                FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro completo", "Se ha enviado un código de confirmación a su correo"));
                                limpiar();
                            } else {
                                Usuario auxUsu = usuarioServicio.encontrarUsuarioPorNombreUsuario(getPersona().getPerIdentificacion().trim());
                                if (auxUsu == null) {
                                    personaServicio.edit(getPersona());
                                    prepararUsuario();
                                    usuarioServicio.guardar(usuario);
                                    prepararHistContr();
                                    historicoContraseinaServicio.guardar(historicoContrasenia);
                                    prepararCodVerif();
                                    codigoVerificacionServicio.guardar(codigoVerificacion);
                                    enviarCodigoPorMail();
                                    FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro completo", "Se ha enviado un código de confirmación a su correo"));
                                    limpiar();
                                } else {
                                    FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "La identificación ingresada está relacionada con un usuario"));
                                    limpiar();
                                }
                            }
                        } else {
                            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Número de factura o código incorrecto", ""));
                            limpiar();
                        }
                        break;
                }
                PrimeFaces current = PrimeFaces.current();
                current.executeScript("PF('dlgRegistro').hide();grecaptcha.reset();");
                limpiar();
            } else {
                msgReCaptcha = "Comprueba que no eres un robot.";
            }

        } catch (ServicioExcepcion ex) {
            FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error al crear usuario", ""));
            Logger.getLogger(PersonaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void validarCedula() {

        switch (getPersona().getPerTipoIdentificacion()) {
            case "C":
                if (!jsfUtil.validadorDeCedula(getPersona().getPerIdentificacion())) {
                    addErrorMessage("La Cédula ingresada es incorrecta");
                }
                break;
            case "R":
                if (!jsfUtil.validaRuc(getPersona().getPerIdentificacion())) {
                    addErrorMessage("El RUC ingresado es incorrecto");
                }
                break;
        }

    }

    public void limpiar() {
        init();
    }

    public void quitarEspacios() {
        if (!(persona.getPerIdentificacion() == null || persona.getPerIdentificacion().isEmpty())) {
            persona.setPerIdentificacion(persona.getPerIdentificacion().trim());
        }
        if (!(persona.getPerApellidoPaterno() == null || persona.getPerApellidoPaterno().isEmpty())) {
            persona.setPerApellidoPaterno(persona.getPerApellidoPaterno().trim());
        }
        if (!(persona.getPerApellidoMaterno() == null || persona.getPerApellidoMaterno().isEmpty())) {
            persona.setPerApellidoMaterno(persona.getPerApellidoMaterno().trim());
        }
        if (!(persona.getPerNombre() == null || persona.getPerNombre().isEmpty())) {
            persona.setPerNombre(persona.getPerNombre().trim());
        }
        if (!(persona.getPerEmail() == null || persona.getPerEmail().isEmpty())) {
            persona.setPerEmail(persona.getPerEmail().trim());
        }
        if (!(persona.getPerDireccion() == null || persona.getPerDireccion().isEmpty())) {
            persona.setPerDireccion(persona.getPerDireccion().trim());
        }
    }

    public void traer() throws ServicioExcepcion {
        try {
            if (!getPersona().getPerIdentificacion().trim().equals("")) {
                Persona auxPer = personaServicio.buscarPersona(getPersona().getPerIdentificacion().trim());
                if (auxPer != null) {
                    Usuario auxUsu = usuarioServicio.encontrarUsuarioPorNombreUsuario(getPersona().getPerIdentificacion().trim());
                    if (auxUsu == null) {
                        setPersona(auxPer);
                        disableInput = true;
                    } else {
                        limpiar();
                        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", "La identificación ingresada está relacionada con un usuario"));
                    }
                }
            } else {
                limpiar();
                FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la identificación", "");
                FacesContext.getCurrentInstance().addMessage("", facesMsg);
            }
        } catch (Exception e) {
        }

    }

}
