/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Marco
 */


@Named("nscripcionCuantiaControlador")
@SessionScoped
public class InscripcionCuantiaControlador implements Serializable{
    
    
    @Getter
    @Setter
    private float cuantiaMonto;

    @Getter
    @Setter
    private float cuantiaAlcabalas;

    @Getter
    @Setter
    private float cuantiaPlusvalia;

  
    @Getter
    @Setter
    private float cuantiaRegistro;

    public void obtenerTextoCuantia(){
        
      
}
}
