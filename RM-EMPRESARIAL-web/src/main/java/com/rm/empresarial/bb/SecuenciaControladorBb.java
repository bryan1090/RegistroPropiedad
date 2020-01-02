/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;

import com.rm.empresarial.modelo.Secuencia;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author WILSON
 */
@Dependent
public class SecuenciaControladorBb {
    
   @Getter
   @Setter
   private Secuencia secuencia;
   
   @Getter
   @Setter
   private Integer numeroTramite; 
   
   @Getter
   @Setter
   private String numeroMatricula; 
  
   @Getter
   @Setter
   private String secuenciaFactura;
     
   public void inicializar(){
       setSecuencia(new Secuencia());
   }
    
}
