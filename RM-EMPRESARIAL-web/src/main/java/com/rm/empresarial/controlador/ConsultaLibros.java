/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Java
 */
@Named(value = "consultalibros")
@ViewScoped

public class ConsultaLibros implements Serializable {

    @Getter
    @Setter
    private boolean marginacion;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String fojas;

    public ConsultaLibros() {

    }

    public void generar() {
        if (marginacion) {
            url = "http://192.168.1.50:8086/RM-EMPRESARIAL-web/temp/libro2.pdf";
        } else {
            url = "http://192.168.1.50:8086/RM-EMPRESARIAL-web/temp/libro1.pdf";
        }
        fojas = "5";
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Libro generado correctamente", ""));
    }

    public void limpiar() {
        /*Limpiar campos*/
        url = "";
        marginacion = false;
         fojas = "";
        PrimeFaces.current().resetInputs("frmLibros:plnLibro");

    }
    
    public void salir() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");

    }

}
