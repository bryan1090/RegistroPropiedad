/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ModeloRest;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author JeanCarlos
 */
public class PersonaREST {
    
    @Getter
    @Setter
    private String perIdentificacion;
    @Getter
    @Setter
    private String perNombre;
    @Getter
    @Setter
    private String perApellidoPaterno;
    @Getter
    @Setter
    private String perApellidoMaterno;
    
}
