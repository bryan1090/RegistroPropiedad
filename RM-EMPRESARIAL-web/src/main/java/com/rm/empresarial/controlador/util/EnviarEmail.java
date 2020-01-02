/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.dao.EmailDao;
import com.rm.empresarial.modelo.HostMail;
import javax.mail.Authenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Marco
 */
public class EnviarEmail {

    public static Email conectaEmail(String hostName, int SmtpPort, Authenticator authenticator, boolean SSL, String From) throws EmailException {

        Email email = new SimpleEmail();

        try {
            email.setHostName(hostName);
            email.setSmtpPort(SmtpPort);
            email.setAuthenticator(authenticator);
            email.setSSL(SSL);
            email.setFrom(From);
        } catch (Exception e) {
        }
        return email;
    }
    
    public static MultiPartEmail conectaEmailMulti(String hostName, int SmtpPort, Authenticator authenticator, boolean SSL, String From) throws EmailException {

        MultiPartEmail email = new MultiPartEmail();

        try {
            email.setHostName(hostName);
            email.setSmtpPort(SmtpPort);
            email.setAuthenticator(authenticator);
            email.setSSL(SSL);
            email.setFrom(From);
        } catch (Exception e) {
        }
        return email;
    }

}
