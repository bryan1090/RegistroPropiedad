/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;

import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Rol;
import com.rm.empresarial.modelo.Zona;
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Dependent
public class UsuarioControllerBb implements Serializable{
    
    @Getter
    @Setter
    private Usuario usuario;      
//    @Getter
//    @Setter
//    private Persona persona;
//    
//     @Getter
//    @Setter
//    private Rol rol;
//     @Getter
//    @Setter
//     private Zona zona;
    
    public void nuevoUsuario(){
        setUsuario(new Usuario());
        getUsuario().setRolId(new Rol());
        getUsuario().setZonId(new Zona());
        getUsuario().setPerId(new Persona());
    }
    
//    public void nuevaPersona()
//    {
//        setPersona(new Persona());
//    }
//     public void nuevoRol()
//    {
//        setRol(new Rol());
//    }
//      public void nuevaZona()
//    {
//        setZona(new Zona());
//    }
}
