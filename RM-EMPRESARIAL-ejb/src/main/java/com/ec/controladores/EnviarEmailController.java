/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controladores;

import com.rm.empresarial.dao.EmailDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.HostMail;
import com.rm.empresarial.modelo.PathDocEle;
import com.rm.empresarial.servicio.HostMailServicio;
import doel.sri.bridge.ConfiguracionEmail;
import doel.sri.bridge.ConfiguracionRepositorio;
import doel.sri.servicios.ServicioArchivo;
import doel.sri.util.Util;
import ec.rideexport.util.RideExport;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Marco
 */
@Named(value = "enviarEmailControllerJdbc")
@ViewScoped
@Getter
@Setter
public class EnviarEmailController implements Serializable {

    @EJB
    private HostMailServicio hostMailServicio;

    private HostMail mail;
    private String usuario = "wherrera.web@gmail.com";
    private String contrasena = "nrbjogxelctetmdr";
    private Integer seguridad = 1;
    private String correo = "wherrera.web@gmail.com";
    private String smtp = "smtp.gmail.com";
    private Integer puertoGmail = 465;
    private String remitente;
    private String asunto;
    private String pathCorreo;

    //Eliminar los parametros si se lee esta informacion desde la vista
    public Boolean enviaEmail(PathDocEle pathDocEle, String emailDestino, String numeroComprobante, String mensaje, String xmlSinFirma, String numeroAutorizacion) throws ServicioExcepcion, EmailException, MessagingException, UnsupportedEncodingException {
        if (!emailDestino.isEmpty()) {
            ConfiguracionRepositorio configuracionRepositorio = new ConfiguracionRepositorio();
            hostMailServicio = new HostMailServicio();
            /*mail = hostMailServicio.obtenerPorConsultaJpaNombrada(mail.LISTAR_TODO, null);
            //seteo de variables de correo
            usuario = mail.getHtmCorreo();
            contrasena = mail.getHtmContrasenia();
            seguridad = new Integer(mail.getHtmSeguridad());
            smtp = mail.getHtmSmtp();
            puertoGmail = new Integer(mail.getHtmPuerto());
            remitente=mail.getHtmRemitente();
*/              
            pathCorreo=pathDocEle.getPdeRepTemporal();
            asunto="Facturación Electrónica:  ".concat(numeroComprobante);
            String pathRepositorioTemp = pathDocEle.getPdeRepTemporal();
            String nombreArchivoPdf = Util.aString("RIDE-", numeroComprobante, ".pdf");//RIDE-001-001-000000001.pdf
            String nombreArchivoXml = Util.aString(numeroComprobante, "-firmado.xml");//RIDE-001-001-000000001.xml
            String pathArchivoXml = Util.aString(pathRepositorioTemp, nombreArchivoXml, "");//home/repositorio/temporales/001-001-000000001.xml
            String pathLogo = pathDocEle.getPdeLogo();
            String fechaAutorizacionString = Util.aFechaHora_String(new Date());
            //generar RIDE pdf
            String xmlPuro = xmlSinFirma;//viene xml sin tag de autorizacion
            InputStream inputStream = RideExport.export(pathLogo, numeroAutorizacion, fechaAutorizacionString, xmlPuro.getBytes("UTF-8"));
            //almacenar PDF
            ServicioArchivo servicioA = new ServicioArchivo();
            //servicioA.almacenar_File(pathArchivoXml, claseEmail.getXmlConAutorizacion());//esta en NULL, revisar
            servicioA.almacenar_EnRepositorio(pathRepositorioTemp, nombreArchivoPdf, inputStream);
            Util.aString(pathRepositorioTemp, nombreArchivoPdf, "");
            String pathArchivoPdf=pathCorreo.concat(nombreArchivoPdf);
            
            Multipart multipart = new MimeMultipart();
             BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(mensaje, "text/html");
            multipart.addBodyPart(messageBodyPart);

            //adjunto el PDF
            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(pathArchivoPdf);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(nombreArchivoPdf);
            multipart.addBodyPart(messageBodyPart);

            //adjunto el XML
            pathArchivoXml=pathCorreo.concat(numeroComprobante).concat("-firmado.xml");
            messageBodyPart = new MimeBodyPart();
            source = new FileDataSource(pathArchivoXml);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(numeroComprobante.concat("-firmado.xml"));
            multipart.addBodyPart(messageBodyPart);

            //envio de correo
            Email email = new MultiPartEmail();
            email.setHostName(smtp);//servidor de correos
            email.setAuthenticator(new DefaultAuthenticator(usuario, contrasena));
            if(seguridad==1){
            email.setSSLOnConnect(true);
            }
            email.setSmtpPort(puertoGmail);
            email.addTo(emailDestino, "");//Para quien se envia
            email.setFrom(correo, remitente);//quien  envia
            email.setSubject(asunto);

            // add the attachment
            email.setContent((MimeMultipart) multipart);
            email.send();

//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "E-mail enviado con éxito para: " + Emaildestino, "Información"));
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

}
