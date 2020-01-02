/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Indices;
import com.rm.empresarial.servicio.IndicesServicio;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "indicesController")
@ViewScoped
public class IndicesController implements Serializable {

    /**
     * Creates a new instance of IndicesController
     */
    @Inject
    private DataManagerSesion dataManagerSesion;
    @Getter
    @Setter
    private Indices indice;
    @Getter
    @Setter

    private Indices seleccionado;
    @Getter
    @Setter
    private List<Indices> listaIndices;
    @Getter
    @Setter
    private Boolean mostrarLibro;
    @Getter
    @Setter
    private Date fechaInicio;
    @Getter
    @Setter
    private Date fechaFin;

    @EJB
    private IndicesServicio servicioIndices;

    public IndicesController() {
        indice = new Indices();
        listaIndices = new ArrayList<>();
        fechaInicio = new GregorianCalendar(1980, 0, 1).getTime();
        fechaFin = new GregorianCalendar(2011, 5, 30).getTime();
//        SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
//        System.out.println("fecha inicio: " + sdf.format(calendarInicio.getTime()));
    }

    @PostConstruct
    public void postConstructor() {
        System.out.println("com.rm.empresarial.controlador.IndicesController.postConstructor()");
    }

    public void buscar() throws ServicioExcepcion {
        System.out.println("com.rm.empresarial.controlador.IndicesController.buscar()");
        listaIndices = servicioIndices.listarPorBusqueda2(indice.getIndCedula(), indice.getIndApellidop1(), indice.getIndApellidom1(), indice.getIndNombres1());

        System.out.println("Fin...");
    }

    public void seleccionar() {
        String libro = seleccionado.getIndLibro().trim().toLowerCase();
        if (libro.equals("embargo")
                || libro.equals("prohibiciones")
                || libro.equals("demanda")
                || libro.equals("demandas")
                || libro.equals("prohibicion")) {
            mostrarLibro = true;
            FacesContext.getCurrentInstance().addMessage("messages", new FacesMessage(FacesMessage.SEVERITY_ERROR, libro, ""));

        }
        System.out.println("com.rm.empresarial.controlador.IndicesController.seleccionar()");
    }

    public void limpiar() {
        
        PrimeFaces.current().resetInputs("form:pnlIngreso");
    }

    public void salir() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/dashboard.xhtml");

    }

    //convertidor de texto a mayuscula
    @FacesConverter(value = "upper")
    public static class UpperCaseConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
            try {
                return string.toUpperCase();
            } catch (Exception e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }

        @Override
        public String getAsString(FacesContext fc, UIComponent uic, Object o) {
            try {
                return o.toString();
            } catch (Exception e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

            }
        }

    }

}
