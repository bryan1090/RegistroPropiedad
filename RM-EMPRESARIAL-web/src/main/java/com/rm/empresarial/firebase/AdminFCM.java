/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.firebase;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.TokenPush;
import com.rm.empresarial.servicio.TokenPushServicio;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
@Named(value = "adminFCM")
@ViewScoped
public class AdminFCM implements Serializable{

    @Getter
    @Setter
    private String tokenRegistro;
    @Getter
    @Setter
    private String tituloNotificacion;
    @Getter
    @Setter
    private String textoNotificacion;
    @Getter
    @Setter
    private List<TokenPush> listaTokenDeUsuarios;
    @Inject
    private MiFirebaseAdmin miFirebaseAdmin;
    
    @EJB
    private TokenPushServicio tokenPushServicio;
    
    @PostConstruct
    public void init() {
        try {
            listaTokenDeUsuarios = tokenPushServicio.listarUsuariosActivosAPP();
        } catch (ServicioExcepcion ex) {
        }
    }
    
    public void enviarNotificacion(){
        miFirebaseAdmin.notificarDispositivo(tokenRegistro, tituloNotificacion, textoNotificacion);
    }
    
    
    
}
