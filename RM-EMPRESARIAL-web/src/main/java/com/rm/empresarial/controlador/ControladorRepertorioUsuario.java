/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.dao.MatriculacionDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TramiteDetalleDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioDigital;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.persistence.Query;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import java.util.ArrayList;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorRepertorioUsuario")
@ViewScoped
public class ControladorRepertorioUsuario implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;
    
    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;

    
    @EJB
    RepertorioUsuarioDao RepertorioUsuarioDao;
    
 

    @Getter
    @Setter
    private String repertorio;

    @Getter
    @Setter
    private String numTramite;

    
    @Getter
    @Setter
    private RepertorioUsuario RepertorioUsuario;
    @Getter
    @Setter
    private Repertorio repertorioNum;

    @Getter
    @Setter
    private Boolean nuevoIngreso;
    @Getter
    @Setter
    private Persona personaSeleccionada;
    @Getter
    @Setter
    private String nombrePersona;
    @Getter
    @Setter
    private Long numeroTramite;

    @Getter
    @Setter
    private Long idParroquia;
    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleActualizado;
    
    @Getter
    @Setter
    private String parNombre;
    
    @Getter
    @Setter
    private String estadoBotonNuevaPropiedad;
    
    
     
      
    

    public ControladorRepertorioUsuario() {
        System.out.println("com.rm.empresarial.controlador.ControladorMatriculacion.<init>()");
        RepertorioUsuario = new RepertorioUsuario();
        nuevoIngreso = false;
        estadoBotonNuevaPropiedad = "true";
    }
    }

    

   

    

