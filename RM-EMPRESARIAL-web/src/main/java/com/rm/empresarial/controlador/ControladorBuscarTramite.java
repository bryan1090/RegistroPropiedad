/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.ibm.icu.util.Calendar;
import com.rm.empresarial.bb.TramitesControladorBb;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.dao.PersonaDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteEntregadoDao;
import com.rm.empresarial.dao.TramiteResponsableDao;
import com.rm.empresarial.dao.TramiteValorDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteEntegado;
import com.rm.empresarial.modelo.TramiteResponsable;
import com.rm.empresarial.modelo.TramiteValor;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Prueba
 */
@Named("controladorBuscarTramite")
@SessionScoped
public class ControladorBuscarTramite implements Serializable {

    @Inject
    private PersonaUtil personaUtil;
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;
    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;
    

    @Getter
    @Setter
    private List<Tramite> listTramite;
    @Getter
    @Setter
    private List<TramiteValor> listTramiteValor;
    @Getter
    @Setter
    private Long numTramite;
    @Getter
    @Setter
    private Date fechaTramite;
    @Getter
    @Setter
    private String tipoBusquedaTramite;
    @Getter
    @Setter
    private Boolean rendBuscarPorNumero;
    @Getter
    @Setter
    private Boolean rendBuscarPorFecha;
    @Getter
    @Setter
    private Boolean rendBuscarPorPersona;
    @Getter
    @Setter
    private Boolean rendFieldSetDatosBusq;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;
    @Getter
    @Setter
    private TramiteEntegado tramiteEntregado;
    @Getter
    @Setter
    private Persona persona;

    @EJB
    private TramiteDao tramiteDao;
    @EJB
    private TramiteEntregadoDao tramiteEntregadoDao;
    @EJB
    private TramiteValorDao tramiteValorDao; 
    @EJB
    private TramiteResponsableDao tramiteResponsableDao;

    public ControladorBuscarTramite() {
        rendBuscarPorNumero = false;
        rendBuscarPorFecha = false;
        rendBuscarPorPersona = false;
        rendFieldSetDatosBusq = false;
        listTramite = new ArrayList<>();
        listTramiteValor = new ArrayList<>();
        tramiteEntregado = new TramiteEntegado();
        persona = new Persona();
    }

    public void tipoBusqueda() {
        switch (tipoBusquedaTramite) {
            case "numTramite":
                rendBuscarPorNumero = true;
                rendBuscarPorFecha = false;
                rendBuscarPorPersona = false;
                break;
            case "fechaTramite":
                rendBuscarPorNumero = false;
                rendBuscarPorFecha = true;
                rendBuscarPorPersona = false;
                break;
            case "personaTramite":
            case "responsableTramite":
                rendBuscarPorPersona = true;
                rendBuscarPorNumero = false;
                rendBuscarPorFecha = false;
                break;
        }
        rendFieldSetDatosBusq = true;
        listTramite.clear();

    }

    public void buscarTramite() {
        listTramite.clear();
        switch (tipoBusquedaTramite) {
            case "numTramite":
                listTramite = tramiteDao.listarPorTramite(numTramite);
                break;
            case "fechaTramite":
                listTramite = tramiteDao.listarPorFecha(fechaTramite);
                break;
            case "personaTramite":
                persona = getTramitesControladorBb().getPersona();
                listTramite = tramiteDao.listarPorNombrePersona_ApellidoPaterno_ApellidoMaterno(persona.getPerNombre(), persona.getPerApellidoPaterno(), persona.getPerApellidoMaterno());
                break;
            case "responsableTramite":
                persona = getTramitesControladorBb().getPersona();
                List<TramiteResponsable> listaTramResp = new ArrayList<>();
                listaTramResp = tramiteResponsableDao.listarPorIdPersona(persona.getPerId());
                for (TramiteResponsable tramiteResponsable : listaTramResp) {
                    if(!listTramite.contains(tramiteResponsable.getTraNumero())){
                        listTramite.add(tramiteResponsable.getTraNumero());
                    }
                }
                break;
        }
        if (listTramite.isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            String mensaje = "No se encontraron Trámites";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));

        }

    }

    public void prepararTramiteEntregado(Tramite tramite) throws ServicioExcepcion {
        tramiteEntregado.setTraNumero(tramite);
        tramiteEntregado.setTmeTipo("PRO");
        tramiteEntregado.setTmeDescripcion("");
        listTramiteValor = tramiteValorDao.ListarPor_NumTramite_Estado(tramite);
    }

    public void guardarTramiteEntregado() {
        try {
            tramiteEntregado.setTmeFHR(Calendar.getInstance().getTime());
            tramiteEntregado.setUsuId(dataManagerSesion.getUsuarioLogeado());
            tramiteEntregadoDao.create(tramiteEntregado);
            FacesContext context = FacesContext.getCurrentInstance();
            String mensaje = "Trámite guardado";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, mensaje));
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void buscarPersona() throws IOException, ServicioExcepcion {

        if (getTramitesControladorBb().getIdentificacion() != null && !getTramitesControladorBb().getIdentificacion().equals("")) {
            //getTramitesControladorBb().setIdentificacion(identificacion);            

            try {

                getTramitesControladorBb().setPersona(personaUtil.obtenerDatosPersona(getTramitesControladorBb().getIdentificacion()));

                if (getTramitesControladorBb().getPersona().getPerIdentificacion() != null) {
                    nombrePersona();
                } else {
                    getTramitesControladorBb().setIdentificacion(" ");
                    getTramitesControladorBb().setNombrePersona(" ");

                }

            } catch (Exception ex) {
                Logger.getLogger(ControladorInscripcion.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            addErrorMessage("Ingrese Cédula/RUC/Pas");
        }

    }

    public void nombrePersona() {
        getTramitesControladorBb().setNombrePersona(getTramitesControladorBb().getPersona().getPerApellidoPaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerApellidoMaterno().trim() + " " + getTramitesControladorBb().getPersona().getPerNombre().trim());
        String Nombre = getTramitesControladorBb().getNombrePersona();
        getTramitesControladorBb().setNombrePersona(Nombre.replace("null", ""));
        getTramitesControladorBb().setEstado(Boolean.TRUE);

    }

}
