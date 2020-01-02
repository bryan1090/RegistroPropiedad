/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;

import com.rm.empresarial.modelo.Persona;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Wilson
 */
@Dependent
public class PersonaControllerBb implements Serializable  {
    
    private static final long serialVersionUID = 9212714522671976053L;
    
    @Getter
    @Setter
    private List<Persona> listaPersona; 
    
    @Getter
    @Setter
    private Persona Persona; 
    
    public void iniciarDatos(){
        setListaPersona(new ArrayList<Persona>());
    }
    
    public void nuevaPersona()
    {
        setPersona(new Persona());
    }
    
}
