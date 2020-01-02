/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Propietario;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.PropietarioServicio;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Named(value = "controladorCertificadosGenerar")
@ViewScoped
public class controladorCertificadosGenerar implements Serializable {

    /**
     * Creates a new instance of controladorCertificadosGenerar
     */
    @EJB
    private ActaServicio servicioActa;
    @EJB
    private PropietarioServicio servicioPropietario;

    @Inject
    DataManagerSesion dataManagerSesion;

    @Getter
    @Setter
    private List<Acta> listaActasSeleccionadas;

    @Getter
    @Setter
    private String certificadoGenerado;

    @Getter
    @Setter
    private String certificadoGenerado2;

    public controladorCertificadosGenerar() {
    }

    @PostConstruct
    public void postConstructor() {
        try {
            inicializarListas();
        } catch (ServicioExcepcion ex) {
            Logger.getLogger(controladorCertificadosGenerar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inicializarListas() throws ServicioExcepcion {
        listaActasSeleccionadas = servicioActa.listarTodo();
        System.out.println("tamaño lista: " + listaActasSeleccionadas.size());
    }

    public void generarCertificado(Acta actaSeleccionada) {

        certificadoGenerado = "";
        if (actaSeleccionada != null) {

            Propiedad propiedadSeleccionada = actaSeleccionada.getPrdMatricula();
            List<Propietario> listaPropietarios = servicioPropietario.buscarPor_PropiedadMatricula_Estado(propiedadSeleccionada.getPrdMatricula());
            String nombresPropietarios = "";
            String antecedentes = "";
            String fechaActa = "";
            String tipoPropiedad = "";
            String descripcionPropiedad = "";
            String parroquia = "";
            String matricula = "";
            String gravamenes = "";
            for (Propietario propietario : listaPropietarios) {
                Persona persona = propietario.getPerId();
                nombresPropietarios += persona.getPerApellidoPaterno().trim()
                        + " " + persona.getPerApellidoMaterno().trim()
                        + " " + persona.getPerNombre().trim() + " con cédula de identidad"
                        + " " + persona.getPerIdentificacion().trim() + " casado con"
                        + " " + persona.getPerIdConyuge().getPerApellidoPaterno().trim()
                        + " " + persona.getPerIdConyuge().getPerApellidoMaterno().trim()
                        + " " + persona.getPerIdConyuge().getPerNombre().trim() + " con cédula de identidad"
                        + " " + persona.getPerIdConyuge().getPerIdentificacion().trim() + "; ";
            }
            if (actaSeleccionada.getActDescripcion3() != null) {
                antecedentes = actaSeleccionada.getActDescripcion3();
            }
            if (actaSeleccionada.getActDescripcion7() != null) {
                gravamenes = actaSeleccionada.getActDescripcion7();

            }
            if (actaSeleccionada.getActFch() != null) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
                fechaActa = format.format(actaSeleccionada.getActFch());
            }
            if (propiedadSeleccionada != null) {
                tipoPropiedad = propiedadSeleccionada.getTpdId().getTpdNombre();
                descripcionPropiedad = propiedadSeleccionada.getPrdDescripcion2() + " " + propiedadSeleccionada.getPrdDescripcion1();
                parroquia = propiedadSeleccionada.getPrdTdtParNombre();
                matricula = propiedadSeleccionada.getPrdMatricula().toString();
            }

            certificadoGenerado = ""
                    
                    + "<center>"
                    + "<p><strong>REGISTRO DE LA PROPIEDAD DEL CANTÓN QUITO </strong></p>"
                    + "<p><strong>CERTIFICADO No.: </strong></p>"
                    + "<p><strong>FECHA DE INGRESO:</strong>" + fechaActa + "</p>"
                    + "<p><br></p>"
                    + "<h2><strong> CERTIFICACION</strong></h2>"
                    + "<p>&nbsp;</p>"
                    + "</center>"
                    + "<p><strong>Referencias:</strong> 13/02/2009-PO-11370f-4466i-11586r</p>"
                    + "<p><strong>Tarjetas:</strong>;T00000319953;</p>"
                    + "<p><strong>Matriculas:</strong>;0</p>"
                    + "<p>El infrascrito Registrador de la Propiedad de este Cantón,"
                    + " luego de revisados los índices y libros que reposan en el archivo, "
                    + "en legal y debida forma certifica que:</p>"
                    + "<p><strong>1.- DESCRIPCION DE LA PROPIEDAD:</strong> </p>"
                    + "<p>" + tipoPropiedad + " " + descripcionPropiedad
                    + ", situado en la parroquia " + parroquia
                    + ", de este cantón, con matrícula número " + matricula + ".</p>"
                    + "<p><strong>2.- PROPIETARIO(S):</strong></p>"
                    + "<p>"
                    + nombresPropietarios
                    + "</p>"
                    + "<p><strong>3.- FORMA DE ADQUISICION Y ANTECEDENTES:</strong> </p>"
                    + "<p>" + antecedentes + ".</p>"
                    + "<p><strong>4.- GRAVAMENES Y OBSERVACIONES:</strong> </p>"
                    + "<p>" + gravamenes + ".</p>"
                    + "<p><strong>Responsable:</strong> " + dataManagerSesion.getUsuarioLogeado().getUsuLogin() + "</p>"
                    + "<p><br></p><p><strong> </strong></p><p><strong> EL REGISTRADOR</strong></p>"
                    + "<p><strong> </strong>&nbsp;</p><p>C11542801.001</p>";
        }
    }

    public void imprimirCertificadoConsola() {
        System.out.println(certificadoGenerado2);

    }

}
