/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
public class ObjetoPrueba {

    
    @Getter
    @Setter
    private Integer id;
    @Getter
    @Setter
    private String s1;
    @Getter
    @Setter
    private String s2;
    @Getter
    @Setter
    private String s3;
    @Getter
    @Setter
    private Integer int1;
    @Getter
    @Setter
    private Integer int2;
    @Getter
    @Setter
    private Integer int3;

    public ObjetoPrueba()
    {
        
    }
    
    public ObjetoPrueba(Integer id,String s1, String s2, String s3, Integer int1, Integer int2, Integer int3) {
        this.id=id;
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
        this.int1 = int1;
        this.int2 = int2;
        this.int3 = int3;
    }
    
    

}
