/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.ReporteUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.InscripcionDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.dao.TramiteEntregadoDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteEntegado;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.modelo.UsuarioEntrega;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.UsuarioEntregaServicio;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.NamingException;
import lombok.Getter;
import lombok.Setter;
import net.sf.jasperreports.engine.JRException;
import org.joda.time.DateTime;

/**
 *
 * @author Prueba
 */
@Named(value = "controladorEntregas")
@ViewScoped
public class ControladorEntregas implements Serializable {
    
    @Inject
    @Getter
    @Setter
    private DataManagerSesion dataManagerSesion;
    
    @Inject
    @Getter
    @Setter
    private CargaLaboralUtil cargaLaboralUtil;
    
    @Inject
    @Getter
    @Setter
    private SecuenciaControlador secuenciaControlador;
    
    @Inject
    @Getter
    @Setter
    private ReporteUtil reporteUtil;
    
    @Inject
    private TramiteUtil tramiteUtil;
    
    @Getter
    @Setter
    private List<Repertorio> listaRepertorioFecha = new ArrayList<>();
    
    @Getter
    @Setter
    private List<Repertorio> listaRepertorioSeleccionados = new ArrayList();
    
    @Getter
    @Setter
    private Tramite tramiteSeleccionado;
    
    @Getter
    @Setter
    private Usuario usuarioAsignado;
    
    @Getter
    @Setter
    private Date FHR;
    
    @Getter
    @Setter
    private Date FHRReimpresion;
    
    @Getter
    @Setter
    private String descripcionEntrega;
    
    @Getter
    @Setter
    private String estadoImprimir = "true";
    
    @Getter
    @Setter
    private List<Repertorio> listaRepertoriosImprimir;
    
    @Getter
    @Setter
    private List<Repertorio> listaRepertoriosTerminados;
    
    @Getter
    @Setter
    private Persona persona;
    
    @Getter
    @Setter
    private Boolean estadoBusqueda = Boolean.TRUE;
    
    @Getter
    @Setter
    private String numTramite;
    
    @Getter
    @Setter
    private Repertorio repertorioReimprimir;
    
    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuarioSelec;
    
    @EJB
    private InscripcionDao inscripcionDao;
    
    @EJB
    private RepertorioUsuarioServicio servicioRepertorioUsuario;
    
    @EJB
    private TramiteDao tramiteDao;
    
    @EJB
    private TramiteEntregadoDao tramiteEntregadoDao;
    
    @EJB
    private UsuarioEntregaServicio servicioUsuarioEntrega;
    
    public ControladorEntregas() {
        
        FHR = Calendar.getInstance().getTime();
        listaRepertoriosImprimir = new ArrayList<>();
        listaRepertoriosTerminados = new ArrayList<>();
        persona = new Persona();
        repertorioUsuarioSelec = new RepertorioUsuario();
    }
    
    @PostConstruct
    public void postConstructor() {
        persona.setPerIdentificacion(" ");
        persona.setPerApellidoPaterno(" ");
        persona.setPerApellidoMaterno(" ");
        persona.setPerNombre(" ");
    }
    
    public void llenarDatos() throws ServicioExcepcion, ParseException {
        listaRepertorioFecha.clear();
        setListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog(FHR, dataManagerSesion.getUsuarioLogeado().getUsuId(), "ETG"));
    }
    
    public void llenarDatosPorIdentificacion() throws ServicioExcepcion, ParseException {
        listaRepertorioFecha.clear();
        if (persona != null) {
            if (persona.getPerIdentificacion().equals(" ")) {
                persona.setPerIdentificacion("^");
            }
            if (persona.getPerApellidoPaterno().equals(" ")) {
                persona.setPerApellidoPaterno("^");
            }
            if (persona.getPerApellidoMaterno().equals(" ")) {
                persona.setPerApellidoMaterno("^");
            }
            if (persona.getPerNombre().equals(" ")) {
                persona.setPerNombre("^");
            }
        }
        setListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog_DatosPersona(dataManagerSesion.getUsuarioLogeado().getUsuId(), "ETG", persona.getPerIdentificacion(), persona.getPerApellidoPaterno(), persona.getPerApellidoMaterno(), persona.getPerNombre()));
    }
    
    public void entregar() throws ServicioExcepcion, ParseException {
        try {
            if (listaRepertorioSeleccionados != null) {
                
                FacesContext context = FacesContext.getCurrentInstance();
                listaRepertoriosImprimir = new ArrayList<>();
                Tramite tramite = new Tramite();
                Repertorio rep = new Repertorio();
                rep = listaRepertorioSeleccionados.get(0);
                tramite = tramiteDao.buscarTramitePorNumero(rep.getTraNumero().getTraNumero());
                guardarTramiteEntregado(tramite);
                crearTramAccion();
                crearUsuarioEntrega(tramite);
                List<RepertorioUsuario> listRepUs = new ArrayList<>();
                listRepUs = servicioRepertorioUsuario.listarPor_Tramite_Tipo_Estado_Usr("ETG", tramite.getTraNumero(), "A", dataManagerSesion.getUsuarioLogeado().getUsuId());
                for (RepertorioUsuario repUsuario : listRepUs) {
                    if (!listaRepertoriosImprimir.contains(repUsuario.getRepNumero())) {
                        listaRepertoriosImprimir.add(repUsuario.getRepNumero());
                    }
                    
                    repUsuario.setRpuEstado("I");
                    servicioRepertorioUsuario.edit(repUsuario);
                    
                }
                descripcionEntrega = "";
                String mensaje = " Trámite procesado con éxito";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", mensaje));
                setEstadoImprimir("false");
                
                if (!persona.getPerIdentificacion().equals(" ") && !persona.getPerApellidoPaterno().equals(" ") && !persona.getPerApellidoMaterno().equals(" ") && !persona.getPerNombre().equals(" ")) {
                    setListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog_DatosPersona(dataManagerSesion.getUsuarioLogeado().getUsuId(), "ETG", persona.getPerIdentificacion(), persona.getPerApellidoPaterno(), persona.getPerApellidoMaterno(), persona.getPerNombre()));
                } else {
                    llenarDatos();
                }
            } else {
                JsfUtil.addErrorMessage("No se ha seleccionado ningún Tramite");
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    public void crearTramAccion() throws ServicioExcepcion {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            
            for (Repertorio repertorioSelec : listaRepertorioSeleccionados) {
                
                setRepertorioUsuarioSelec(servicioRepertorioUsuario.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorioSelec.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "ETG"));
                
                tramiteSeleccionado = tramiteDao.buscarTramitePorNumero(repertorioSelec.getTraNumero().getTraNumero());
                
                tramiteSeleccionado.setTraEstado("FIN");
                tramiteDao.edit(tramiteSeleccionado);
                tramiteUtil.insertarTramiteAccion(tramiteSeleccionado, tramiteSeleccionado.getTraNumero().toString(),
                        "ACTUALIZACIÓN DEL ESTADO DE TRAMITE A 'FIN'",
                        tramiteSeleccionado.getTraEstado(), tramiteSeleccionado.getTraEstadoRegistro(), dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                
                cargaLaboralUtil.restarCargaAsignadaAlUsuario(repertorioUsuarioSelec.getUsuId().getUsuId(), repertorioUsuarioSelec.getRpuTipo());
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            String mensaje = "No se pudo procesar";
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", mensaje));
        }
        
    }
    
    public void guardarTramiteEntregado(Tramite tramite) {
        try {
            TramiteEntegado tramEntregado = new TramiteEntegado();
            tramEntregado.setTraNumero(tramite);
            tramEntregado.setTmeFHR(Calendar.getInstance().getTime());
            tramEntregado.setTmeDescripcion(descripcionEntrega);
            tramEntregado.setUsuId(dataManagerSesion.getUsuarioLogeado());
            tramiteEntregadoDao.create(tramEntregado);
            
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    
    public void imprimirTramitesEntregados() {
        try {
            String encargado = dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoPaterno() + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno() + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerNombre();
            Date fechaActual = new Date();
            fechaActual = Calendar.getInstance().getTime();
            for (Repertorio repSelect : listaRepertoriosImprimir) {
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("numeroTramite", repSelect.getTraNumero().getTraNumero().intValue());
                parametros.put("encargado", encargado);
                parametros.put("fechaEntrega", fechaActual);
                reporteUtil.imprimirReporteEnPDF("EntregaTramite", "Tramite_Entregado_" + repSelect.getTraNumero().getTraNumero(), parametros);
            }
            setEstadoImprimir("true");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void refresh() throws IOException {
        listaRepertorioFecha = new ArrayList<>();
        listaRepertorioSeleccionados = new ArrayList<>();
        persona = new Persona();
        
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/EntregaTramite/Entrega.xhtml");
    }
    
    public void buscarTramitesEntregado() {
        try {
            if (FHRReimpresion != null && !numTramite.isEmpty()) {
                setListaRepertoriosTerminados(inscripcionDao.ListarRepertorioPorNumTramiteYFecha(FHRReimpresion, Integer.parseInt(numTramite)));
            } else if (FHRReimpresion != null && numTramite.isEmpty()) {
                setListaRepertoriosTerminados(inscripcionDao.ListarRepertorioPorFecha(FHRReimpresion));
            } else if (!numTramite.isEmpty() && FHRReimpresion == null) {
                setListaRepertoriosTerminados(inscripcionDao.ListarRepertorioPorNumTramite(Integer.parseInt(numTramite)));
            }
        } catch (ServicioExcepcion | NumberFormatException | ParseException e) {
            System.out.print(e);
        }
    }
    
    public void reimprimirTramiteEntregado() {
        try {
            String encargado = dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoPaterno() + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerApellidoMaterno() + " " + dataManagerSesion.getUsuarioLogeado().getPerId().getPerNombre();
            Date fechaActual = new Date();
            fechaActual = Calendar.getInstance().getTime();
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numeroTramite", repertorioReimprimir.getTraNumero().getTraNumero().intValue());
            parametros.put("encargado", encargado);
            parametros.put("fechaEntrega", fechaActual);
            reporteUtil.imprimirReporteEnPDF("EntregaTramite", "Tramite_Entregado_" + repertorioReimprimir.getTraNumero().getTraNumero(), parametros);
        } catch (IOException | SQLException | NamingException | JRException e) {
            System.out.print(e);
        }
    }
    
    public void crearUsuarioEntrega(Tramite tramite) {
        Usuario encargado = dataManagerSesion.getUsuarioLogeado();
        UsuarioEntrega usuarioEntrega = new UsuarioEntrega();
        usuarioEntrega.setTraNumero(tramite);
        usuarioEntrega.setUsuId(encargado);
        usuarioEntrega.setUseDescripcion(descripcionEntrega);
        usuarioEntrega.setUseFHR(Calendar.getInstance().getTime());
        
        servicioUsuarioEntrega.create(usuarioEntrega);
    }
}
