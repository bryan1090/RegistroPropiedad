/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.modelo.ObjetoPrueba;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "newJSFManagedBean")
@ViewScoped
public class NewJSFManagedBean implements Serializable {

    @Getter
    @Setter
    List<String> lista;

    @Getter
    @Setter
    List<String> lista2;
    @Getter
    @Setter
    private Integer numFilas;
    @Getter
    @Setter
    private String nombre;
    @Getter
    @Setter
    private String seleccion;
    @Getter
    @Setter
    private ObjetoPrueba objeto;
    @Getter
    @Setter
    private List<ObjetoPrueba> listaObjetos;
    @Getter
    @Setter
    private Boolean mostrar;

    public NewJSFManagedBean() {
        System.out.println("com.rm.empresarial.controlador.NewJSFManagedBean.<init>()");
        lista = new ArrayList<>();
        objeto = new ObjetoPrueba();
        mostrar = false;
    }

    @PostConstruct
    public void postConstructor() {
        System.out.println("com.rm.empresarial.controlador.NewJSFManagedBean.postConstructor()");
    }

    //metodo ejecutado por ajax
    public void llenarLista() {
        lista = new ArrayList<>();
        listaObjetos = new ArrayList<>();
        if (numFilas >= 0) {
            
            try {
                for (int i = numFilas; i > 0; i--) {
                    lista.add(" " + i);
                }
                for (int i = 0; i < numFilas; i++) {
                    objeto = new ObjetoPrueba();
                    objeto.setId(i + 1);
                    listaObjetos.add(objeto);
                }
                mostrar = true;
                System.out.println("mostrar: "+mostrar);
            } catch (Exception e) {
                
            }

        }

        lista2=new ArrayList<>(lista);
        Collections.reverse(lista2);
    }

    public void guardar() {

        if (numFilas > 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "¡Guardado!", ""));

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "El numero de pisos debe ser mayor a 0", ""));

        }

        limpiar();
    }

    public void limpiar() {
        numFilas = null;
        nombre = "";
        seleccion = "";
        lista = new ArrayList<>();
        lista2 = new ArrayList<>();
        listaObjetos = new ArrayList<>();
        mostrar=false;
    }
    
    public void salir() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");

    }

    public List<String> devolverLista() {
        lista.add("asd");
        lista.add("asd");
        lista.add("asd");
        return lista;
    }

}
