/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import java.io.Serializable;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Wilson
 */
@Dependent
public class ControladorTipoTramiteUnico implements Serializable {

    @Getter
    @Setter
    private Long ttrId;

    @Getter
    @Setter
    private String ttrDescripcion;

    public ControladorTipoTramiteUnico() {
    }

    public void inicializar() {

    }

    public ControladorTipoTramiteUnico(Long ttrId, String ttrDescripcion) {
        this.ttrId = ttrId;
        this.ttrDescripcion = ttrDescripcion;
    }    
}
