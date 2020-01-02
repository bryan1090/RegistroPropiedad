/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.Usuario;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "dataManagerSesion")
@SessionScoped
public class DataManagerSesion implements Serializable {

    /**
     * Creates a new instance of DataManagerSesion
     */
    @Getter
    @Setter
    private Usuario usuarioLogeado;

    public DataManagerSesion() throws IOException {
        

    }

    @PostConstruct
    public void postConstructor() {
        System.out.println("com.rm.empresarial.controlador.DataManagerSesion.postConstructor()");
        try {
            controlarAcceso();
        } catch (IOException ex) {
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/login.xhtml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void controlarAcceso() throws IOException { //este metodos es llamado cada vez que inicia el bean, cada vez que inicia la peticion, sea por primera vez o después que se ha expirado
        try {
            System.out.println("com.rm.empresarial.controlador.DataManagerSesion.controlarAcceso()");
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String uri = request.getRequestURI();
            System.out.println("Accediendo a la pagina: "+uri);
//si no hay usuario logeado (todas las pantallas excepto login) destruye la session y redireciona al login
            if (usuarioLogeado == null && !uri.equals("/RM-EMPRESARIAL-web/login.xhtml")) {
                
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/login.xhtml");
            }
        } catch (Exception e) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/login.xhtml");
        }

    }

    public void logout() throws IOException {
        try {
            usuarioLogeado = new Usuario();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/login.xhtml");

        } catch (Exception e) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/login.xhtml");
        }

    }

    public void validarAcceso() {

    }

}
