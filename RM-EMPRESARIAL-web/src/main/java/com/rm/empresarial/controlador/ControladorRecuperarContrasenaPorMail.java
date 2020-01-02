/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.UsuarioControllerBb;
import com.rm.empresarial.controlador.util.ContraseniaRandom;
import com.rm.empresarial.controlador.util.ControladorCambiarContraseña;
import com.rm.empresarial.controlador.util.EnviarEmailController;
import com.rm.empresarial.controlador.util.VerificarReCaptcha;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CloseEvent;

/**
 *
 * @author Marco
 */
@Named("recuperarContrasenaPorMail")
@ViewScoped
public class ControladorRecuperarContrasenaPorMail implements Serializable {

    @Inject
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String usurioLogin;

    @Getter
    @Setter
    private String msgReCaptcha;

    @Inject
    private UsuarioControllerBb usuarioControllerBb;

    @Inject
    private EnviarEmailController enviarEmail;

    @EJB
    private UsuarioServicio usuarioServicio;

    @EJB
    private PersonaDao personaDao;

    private Usuario usuario = new Usuario();

    private Persona persona = new Persona();

    private ContraseniaRandom contraseniaRandom = new ContraseniaRandom();

    @Inject
    private ControladorCambiarContraseña controladadorCambiarContraseña;
    
    @PostConstruct
    public void init(){
        msgReCaptcha = "";
    }

    public void clear() {
        this.email = null;
        this.usurioLogin = null;
        msgReCaptcha = "";
    }

    public void handleClose(CloseEvent event) {
        clear();
    }

    public void recuperarContraseña() {

        try {
            String gRecaptchaResponse = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("g-recaptcha-response");
            if (VerificarReCaptcha.verify(gRecaptchaResponse)) {
                usuario = usuarioServicio.encontrarUsuarioPorNombreEmail(usurioLogin, email);
                if (usuario != null) {

                    String randomPassword = contraseniaRandom.getPassword(10);

                    controladadorCambiarContraseña.cambiarContraseñaPorMail(randomPassword, usuario);

                    String nombre = usuario.getPerId().getPerNombre().trim();
                    String apellido = usuario.getPerId().getPerApellidoPaterno().trim();

                    String asunto = "Recuperacion de contraseña";
                    String mensaje = "Estimado(a) " + nombre + " " + apellido + "\n \n Su contraseña es: " + randomPassword;

                    enviarEmail.enviaEmail(email, asunto, mensaje);
                    PrimeFaces current = PrimeFaces.current();
                    current.executeScript("PF('dlgContrasena').hide();grecaptcha.reset();");
                    FacesContext context = FacesContext.getCurrentInstance();
                    context.addMessage(null, new FacesMessage("Información", "Si la información proporcionada es correcta recibirá un email con la información de recuperación de contraseña"));
                    clear();
                }
            } else {
                msgReCaptcha = "Comprueba que no eres un robot.";
            }

        } catch (Exception e) {
            clear();
        }
    }

}
