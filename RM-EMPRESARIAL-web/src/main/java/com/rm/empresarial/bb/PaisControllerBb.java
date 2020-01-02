/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;

import com.rm.empresarial.modelo.Pais;
import java.io.Serializable;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Wilson
 */
@Dependent
public class PaisControllerBb implements Serializable {
    
    private static final long serialVersionUID = -8536402003674338442L;
    
    @Getter
    @Setter
    private Pais pais;
    
    public void nuevoPais(){
        setPais(new Pais());
    }
    
}
