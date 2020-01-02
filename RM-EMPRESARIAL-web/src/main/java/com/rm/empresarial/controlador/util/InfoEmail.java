/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import java.io.Serializable;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Marco
 */
@Named
@RequestScoped
public class InfoEmail implements Serializable {

    @Getter
    @Setter
    private String mensaje;

    @Getter
    @Setter
    private String asunto;

    @Getter
    @Setter
    private String emailDestino;

    @Getter
    @Setter
    private String statusMessage = "";

    public InfoEmail() {
    }

    public InfoEmail(String mensaje, String asunto, String emailDestino) {
        this.mensaje = mensaje;
        this.asunto = asunto;
        this.emailDestino = emailDestino;
    }

}
