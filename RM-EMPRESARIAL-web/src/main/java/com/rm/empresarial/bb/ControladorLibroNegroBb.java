/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;

import com.rm.empresarial.modelo.LibroNegro;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.RepositorioSRI;
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Dependent
public class ControladorLibroNegroBb implements Serializable{

    @Getter
    @Setter
    private String identificacion;
    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private RepositorioSRI repositorioSRI;
    @Getter
    @Setter
    private LibroNegro libroNegro;

    public void inicializarObjetos() {
        identificacion="";
       setPersona(new Persona());
       setRepositorioSRI(new RepositorioSRI());
        setLibroNegro(new LibroNegro());

    }
}
