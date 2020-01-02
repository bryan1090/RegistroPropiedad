/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloRest;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
public class UsuarioREST {
    
    @Getter
    @Setter
    private Long usuId;
    @Getter
    @Setter
    private String usuLogin;
    @Getter
    @Setter
    private String usuContrasenia;
    @Getter
    @Setter
    private String customToken;
    @Getter
    @Setter
    private PersonaREST persona;
    
    public UsuarioREST(){
        this.persona = new PersonaREST();
    }
    
}
