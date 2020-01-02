/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

/**
 *
 * @author DESARROLLO
 */
public class UtilTexto {
    
    
    public static String generarEspaciosEnBlanco(int numEspacios)
    {
        String textoEspacios="";
        for (int i = 0; i < numEspacios; i++) {
           textoEspacios+= "&nbsp;";
        }
        return textoEspacios;
    }
}
