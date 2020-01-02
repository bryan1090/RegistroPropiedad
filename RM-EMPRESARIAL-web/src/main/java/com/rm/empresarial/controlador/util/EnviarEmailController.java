/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.dao.EmailDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.HostMail;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.mail.Authenticator;
import javax.mail.util.ByteArrayDataSource;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Marco
 */
@Named(value = "enviarEmailController")
@ViewScoped
public class EnviarEmailController implements Serializable {

    @EJB
    private EmailDao emailDao;
    @Getter
    @Setter
    private HostMail mail;

    private Authenticator authenticator;
    private boolean seguridadSSL;
    /*
    private static final String username = "coin.jih";
    private static final String password = "Coin2011";
    private static final String Fromemail = "coin.jih@gmail.com"; 
     */

    private InfoEmail mensagem = new InfoEmail();

    public InfoEmail getMensagem() {
        return mensagem;
    }

    public void setMensagem(InfoEmail mensagem) {
        this.mensagem = mensagem;
    }

    //Eliminar los parametros si se lee esta informacion desde la vista
    public Boolean enviaEmail(String Emaildestino, String asunto, String mensaje) throws ServicioExcepcion, EmailException {
        if (!Emaildestino.isEmpty()) {
            mail = emailDao.obtenerHostMail();
            authenticator = new DefaultAuthenticator(mail.getHtmUsuario(), mail.getHtmContrasenia());
            if (mail.getHtmSeguridad() == 1) {
                seguridadSSL = true;
            } else {
                seguridadSSL = false;
            }
            System.out.println("correo: " + mail.getHtmCorreo());

            try {
                Email email = new SimpleEmail();
                email = EnviarEmail.conectaEmail(mail.getHtmSmtp(), mail.getHtmPuerto(), authenticator, seguridadSSL, mail.getHtmCorreo());

                /* Tomar datos desde la vista
            email.setSubject(mensagem.getAsunto());
            email.setMsg(mensagem.getMensaje());
            email.addTo(mensagem.getEmailDestino());
                 */
                email.setSubject(asunto);
                email.setMsg(mensaje);
                email.addTo(Emaildestino);

                String respuesta = email.send();
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-mail enviado con éxito para: " + Emaildestino, "Información"));
                return Boolean.TRUE;

            } catch (EmailException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No sepudo enviar el e-mail para: " + mensagem.getEmailDestino(), "Información"));

                System.out.println(e + "  com.rm.empresarial.controlador.util.EnviarEmail.enviaEmail()");
            }
        }
        return Boolean.FALSE;
    }

    public Boolean enviaEmailConAdjunto(String Emaildestino, String asunto, String mensaje, String tipoAdjunto, InputStream adjunto, String nombreAdjunto, String descripcionArchivoAdjunto) throws ServicioExcepcion, EmailException {
        if (!Emaildestino.isEmpty()) {
            mail = emailDao.obtenerHostMail();
            authenticator = new DefaultAuthenticator(mail.getHtmUsuario(), mail.getHtmContrasenia());
            if (mail.getHtmSeguridad() == 1) {
                seguridadSSL = true;
            } else {
                seguridadSSL = false;
            }
            System.out.println("correo: " + mail.getHtmCorreo());

            try {
                MultiPartEmail email = new MultiPartEmail();
                email = EnviarEmail.conectaEmailMulti(mail.getHtmSmtp(), mail.getHtmPuerto(), authenticator, seguridadSSL, mail.getHtmCorreo());

                /* Tomar datos desde la vista
            email.setSubject(mensagem.getAsunto());
            email.setMsg(mensagem.getMensaje());
            email.addTo(mensagem.getEmailDestino());
                 */
                if (adjunto != null) {
                    // Create the attachment
//                    EmailAttachment attachment = new EmailAttachment();
//                    attachment.setPath("mypictures/john.jpg");
//                    attachment.setDisposition(EmailAttachment.ATTACHMENT);
//                    attachment.setDescription("Picture of John");
//                    attachment.setName("John");
//                    // add the attachment
//                    email.attach(attachment);

                    DataSource source = null;
                    Boolean AdjuntoDeFormatoConocido = false;
                    String tipoArchivoAdjunto = "";
                    try {
                        switch (tipoAdjunto) {
                            case "pdf":
                                AdjuntoDeFormatoConocido = true;
                                tipoArchivoAdjunto = "application/pdf";
                        }
                        if (!tipoArchivoAdjunto.isEmpty()) {
                            source = new ByteArrayDataSource(adjunto, tipoArchivoAdjunto);
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(EnviarEmailController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (source!=null) {
                        email.attach(source, nombreAdjunto, descripcionArchivoAdjunto);
                    }
                }

                email.setSubject(asunto);
                email.setMsg(mensaje);
                email.addTo(Emaildestino);
                String respuesta = email.send();
                //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-mail enviado con éxito para: " + Emaildestino, "Información"));
                return Boolean.TRUE;

            } catch (EmailException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No sepudo enviar el e-mail para: " + mensagem.getEmailDestino(), "Información"));

                System.out.println(e + "  com.rm.empresarial.controlador.util.EnviarEmail.enviaEmail()");

            }
        }
        return Boolean.FALSE;

    }

}
