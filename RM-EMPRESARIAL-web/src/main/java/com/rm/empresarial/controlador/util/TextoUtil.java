/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Bryan_Mora
 */
public class TextoUtil {

    public static List<String> recortarAlEncontrarCaracter(String texto) {
        
        List<String> listaTexto = new ArrayList<>();
        Pattern pattern = Pattern.compile("%s");
        Matcher matcher = pattern.matcher(texto);
        int inicio = 0;
        int fin = 0;
        while (matcher.find()) {
            fin = matcher.start();
            String sub = texto.substring(inicio, fin);
            listaTexto.add(sub);
            inicio = matcher.end();
        }
        if (listaTexto.isEmpty()) {
            listaTexto.add(texto);
        }
        return listaTexto;
    }
}
