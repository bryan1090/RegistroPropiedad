/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import com.rm.empresarial.controlador.util.CargaLaboralUtil;
import com.rm.empresarial.dao.InscripcionDao;
import com.rm.empresarial.dao.RepertorioDao;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.dao.TramiteDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Alicuota;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoTramite;

import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.AlicuotaServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Prueba
 */
@Named(value = "controladorVentas")
@ViewScoped
public class ControladorVentas implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

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
    private TramitesControladorBb tramitesControladorBb;

 
    @Getter
    @Setter
    private List<Repertorio> listaRepertorioFecha = new ArrayList<>();

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor = new ArrayList<>();

    @Getter
    @Setter
    private List<Repertorio> preListaRepertorioFecha = new ArrayList<>();
    
    @Getter
    @Setter
    private List<Propiedad> listaPropiedadHija = new ArrayList<>();
    
    @Getter
    @Setter
    private List<RepertorioPropiedad> listaRepPropiedad = new ArrayList<>();
    
    @Getter
    @Setter
    private List<Alicuota> listaAlicuotaMostrar = new ArrayList<>();
    
    
    
    @Getter
    @Setter
    private Date fecha;
    @Getter
    @Setter
    private Secuencia secuencia;  
    
    @Getter
    @Setter
    private Propiedad propUV = new Propiedad();


    @Getter
    @Setter
    private String numeroTramite;

    @Getter
    @Setter
    private String numeroRepertorio;

    @Getter
    @Setter
    private String descripcionContrato;


    @Getter
    @Setter
    private TramiteDetalle tramiteDetalle;

    @Getter
    @Setter
    private Repertorio repertorio;

    @Getter
    @Setter
    private TipoTramite tipoTramite;


    @Getter
    @Setter
    private Boolean todosEstadoProcTerminado = false; 

    @Getter
    @Setter
    private Tramite tramite;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;  

    @Getter
    @Setter
    private Usuario usuarioAsignado; 
  

    @EJB
    private RepertorioDao repertorioDao;

    @EJB
    private InscripcionDao inscripcionDao;

    @EJB
    private TramiteDao tramiteDao;
    
    @EJB
    private RepertorioUsuarioServicio repertorioUsuarioServicio;
    
    @EJB
    private PropiedadServicio  propiedadServicio;
    
    @EJB
    private RepertorioPropiedadDao  repertorioPropiedadDao;

    @EJB
    private AlicuotaServicio alicuotaServicio;
   

    public ControladorVentas() {

        setFecha(Calendar.getInstance().getTime());  
        tramiteDetalle = new TramiteDetalle();
        tipoTramite = new TipoTramite();
        repertorio = new Repertorio();
        tramite = new Tramite();       
        tramiteSeleccionado = new Tramite();
        usuarioAsignado = new Usuario();
       

    }

    @PostConstruct
    public void postConstructor() {
        inicializar();

    }

    public void inicializar() {
        try {
            llenarDatos();        

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void llenarDatos() throws ServicioExcepcion, ParseException {
       
        preListaRepertorioFecha.clear();
        limpiarCampos();
      
        setPreListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId(), "VPH"));
        listaRepertorioFecha.clear();
        for (Repertorio repertorio1 : preListaRepertorioFecha) {

            Tramite tramit = new Tramite();
            tramit = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());
            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramit.getTraNumero().longValue()) && !tramit.getTraEstadoRegistro().trim().equals("RZ")) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorio1.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "VPH");
                if (repUsu.getRpuEstado().equals("A")) {
                    if (repUsu.getRpuEstadoProceso() != null && !repUsu.getRpuEstadoProceso().equals("")) {
                        repertorio1.setEstadoProceso(repUsu.getRpuEstadoProceso());
                    } else {
                        repertorio1.setEstadoProceso("EN PROCESO");
                    }

                }
                listaRepertorioFecha.add(repertorio1);
            }

        }

        //setListaRepertorioFecha(inscripcionDao.ListarRepertorioINJPorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId()));
    }

    public void llenarDatosActualizar() throws ServicioExcepcion, ParseException {

        listaRepertorioFecha.clear();
        preListaRepertorioFecha.clear();
        
        setPreListaRepertorioFecha(inscripcionDao.ListarRepertorioTipo_PorFHRPorUsrLog(fecha, dataManagerSesion.getUsuarioLogeado().getUsuId(), "VPH"));

        for (Repertorio repertorio1 : preListaRepertorioFecha) {

            Tramite tramit = new Tramite();
            tramit = tramiteDao.buscarTramitePorNumero(repertorio1.getTraNumero().getTraNumero());
            if ((repertorio1.getTraNumero().getTraNumero().longValue() == tramit.getTraNumero().longValue()) && !tramit.getTraEstadoRegistro().trim().equals("RZ")) {
                RepertorioUsuario repUsu = repertorioUsuarioServicio.obtenerRepUsu_Por_Rep_Usr_Tipo(repertorio1.getRepNumero(), dataManagerSesion.getUsuarioLogeado().getUsuId(), "IND");
                if (repUsu.getRpuEstado().equals("A")) {
                    if (repUsu.getRpuEstadoProceso() != null && !repUsu.getRpuEstadoProceso().equals("")) {
                        repertorio1.setEstadoProceso(repUsu.getRpuEstadoProceso());
                    } else {
                        repertorio1.setEstadoProceso("EN PROCESO");
                    }

                }
                listaRepertorioFecha.add(repertorio1);
            }

        }

    }

    public void onRowSelect(SelectEvent event) throws ServicioExcepcion, ParseException {

        limpiarCampos();
        numeroRepertorio = "" + (((Repertorio) event.getObject()).getRepNumero()).toString() + "";
        numeroTramite = "" + (((Repertorio) event.getObject()).getTraNumero().getTraNumero()).toString() + "";
        descripcionContrato = "" + (((Repertorio) event.getObject()).getRepTtrDescripcion()) + "";
        repertorio = repertorioDao.encontrarRepertorioPorId(numeroRepertorio);
        mostrarPropiedades();

    }

    public void limpiarCampos() {
        numeroRepertorio = "";
        numeroTramite = "";
        descripcionContrato = "";     
      

    }
    
    public void mostrarPropiedades() throws ServicioExcepcion, ParseException{
        listaRepPropiedad.clear();
        listaRepPropiedad = repertorioPropiedadDao.buscarPorNumRepertorio(Long.valueOf(numeroRepertorio));
        
        
    }
    
    public void listarDetalleAlicuotas(Propiedad propiedad) throws ServicioExcepcion {
        propUV = propiedad;
        listaAlicuotaMostrar.clear();
        listaAlicuotaMostrar = alicuotaServicio.listarPorMatricula(propiedad.getPrdMatricula());
    }


    

}
