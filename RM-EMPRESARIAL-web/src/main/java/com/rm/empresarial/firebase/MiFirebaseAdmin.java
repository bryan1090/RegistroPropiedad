/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.servicio.ConfigDetalleServicio;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "firebaseAdmin")
@Dependent
public class MiFirebaseAdmin implements Serializable {

    @EJB
    private ConfigDetalleServicio configDetalleServicio;

    @PostConstruct
    public void init() {
        inicializarFirebaseAdmin();
    }

    private void inicializarFirebaseAdmin() {
        try {
            String path = configDetalleServicio.buscarPorConfig("FIREBASEADMINSDK").getConfigDetalleTexto();
            if (FirebaseApp.getApps().size() < 1) {
                FileInputStream serviceAccount
                        = new FileInputStream(path);

                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://rp-empresarial.firebaseio.com")
                        .build();

                FirebaseApp.initializeApp(options);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } catch (IOException | ServicioExcepcion ex) {
            System.out.println(ex.getMessage());
        }
    }

    //Firebase Authentication
    public String generarToken(String usuario) {
        String customToken = null;
        try {
            String uid = usuario;
            customToken = FirebaseAuth.getInstance().createCustomToken(uid);
        } catch (FirebaseAuthException ex) {
            System.out.println(ex.getMessage());
        }
        return customToken;
    }

    //Firebase Cloud Messaging
    public void notificarDispositivo(String tokenRegistro, String tituloNotificacion, String textoNotificacion) {
        try {
            Message message = Message.builder()
                    .setNotification(new Notification(tituloNotificacion, textoNotificacion))
                    .setToken(tokenRegistro)
                    .build();
//            String response = 
            FirebaseMessaging.getInstance().send(message);
//            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException ex) {

        }

    }

}
