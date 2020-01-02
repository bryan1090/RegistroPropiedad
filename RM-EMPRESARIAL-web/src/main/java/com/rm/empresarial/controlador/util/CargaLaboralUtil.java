/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador.util;

import com.rm.empresarial.controlador.DataManagerSesion;
import com.rm.empresarial.dao.TramiteDocumentoEntregaDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Funcion;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDocumentoEntrega;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CargaLaboralServicio;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import com.rm.empresarial.servicio.FuncionServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Dependent
public class CargaLaboralUtil implements Serializable {
    
    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;
    @EJB
    private CargaLaboralServicio cargaLaboralServicio;
    
    @EJB
    private CertificadoCargaServicio certificadoCargaServicio;
    
    @EJB
    private UsuarioServicio servicioUsuario;
    @EJB
    private FuncionServicio servicioFuncion;
    
    @EJB
    private TipoTramiteServicio servicioTipoTramite;
    
    @EJB
    private TramiteServicio servicioTramite;
    
    @EJB
    private TramiteDocumentoEntregaDao servicioTramiteDocumentoEntrega;
    
    @Getter
    @Setter
    private CargaLaboral cargaLaboral;
    
    @Getter
    @Setter
    private CertificadoCarga certificadoCarga;
    
    @Getter
    @Setter
    private Usuario usuario;
    @Getter
    @Setter
    private String usuarioNombre;
    @Getter
    @Setter
    private RepertorioUsuario repertorioUsuario;
    @Getter
    @Setter
    private Funcion funcion;
    
    private List<CargaLaboral> listaCargaLaboral;
    
    private List<Usuario> listaUsuariosEnCargaLaboral;
    private List<Usuario> listaUsuariosEnCargaCertificados;
    private List<Usuario> listaUsuariosPorRol;
    
    public CargaLaboralUtil() {
        System.out.println("com.rm.empresarial.controlador.util.CargaLaboralUtil.<init>()");
        usuario = new Usuario();
        cargaLaboral = new CargaLaboral();
        listaCargaLaboral = new ArrayList<>();
    }
    
    @PostConstruct
    public void postConstructor() {

//            listaUsuarios = servicioUsuario.listarTodo();
    }
    
    public Usuario obtenerCargaLaboralTramite(String tipoCargaLaboral, String nombrePagina, Integer cargaAdicional, Long idTipoTram) {
        System.out.println("metodo carga laboral222");
        //MODIFICANDO CARGA LABORAL
        try {
            TipoTramite tipoTramiteSel = servicioTipoTramite.buscarPorID(idTipoTram);
            if (tipoTramiteSel != null) {
                Integer peso = tipoTramiteSel.getTtrPeso().intValue();
                if (peso != null && peso > 0) {
                    cargaAdicional = peso;
                }
                
            }
            setFuncion(servicioFuncion.buscarPorNombrePagina(nombrePagina));
            listaUsuariosPorRol = servicioUsuario.listarUsuariosPorRol(getFuncion().getRolId().getRolId());
            listaUsuariosEnCargaLaboral = cargaLaboralServicio.listarUsuariosDeCargaLaboral(tipoCargaLaboral);
            
            Boolean agregadosTodos = true;
            for (Usuario usu : listaUsuariosPorRol) {
                if (!listaUsuariosEnCargaLaboral.contains(usu)) {
                    agregadosTodos = false;
                    setUsuario(usu); //selecciono el usuario a asignar
                    break;
                }
            }
            
            if (agregadosTodos) {
                setCargaLaboral(cargaLaboralServicio.buscarPorCargaLaboralConTipo(tipoCargaLaboral));
                setUsuarioNombre(getCargaLaboral().getUsuId().getPerId().getPerNombre().concat(" ").concat(getCargaLaboral().getUsuId().getPerId().getPerApellidoPaterno()));
                setUsuario(getCargaLaboral().getUsuId());
                
                short aux = getCargaLaboral().getCarAsignado();
                int carga = aux;
                System.out.print("Carga-- " + aux);
                getCargaLaboral().setCarAsignado((short) (carga + cargaAdicional));
                getCargaLaboral().setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                getCargaLaboral().setCarFHR(Calendar.getInstance());
                getCargaLaboral().setCarTipo(tipoCargaLaboral);
                getCargaLaboral().setCarCantidad(getCargaLaboral().getCarCantidad() + new Long(1));
                cargaLaboralServicio.edit(getCargaLaboral());
                System.out.println("Carga Laboral asignada");
            } else {
                cargaLaboral = new CargaLaboral();
                getCargaLaboral().setUsuId(getUsuario());
                if (cargaAdicional > 0) {
                    getCargaLaboral().setCarAsignado((cargaAdicional.shortValue()));
                } else {
                    getCargaLaboral().setCarAsignado((short) 1);
                }
                getCargaLaboral().setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                getCargaLaboral().setCarFHR(Calendar.getInstance());
                getCargaLaboral().setCarTipo(tipoCargaLaboral);
                getCargaLaboral().setCarCantidad(new Long(1));
                cargaLaboralServicio.guardar(getCargaLaboral());
                System.out.println("Carga Laboral creada y asignada");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
    
    public Usuario obtenerCargaLaboral(String tipoCargaLaboral, String nombrePagina, Integer cargaAdicional) {
        System.out.println("metodo carga laboral222");
        //MODIFICANDO CARGA LABORAL
        try {
            setFuncion(servicioFuncion.buscarPorNombrePagina(nombrePagina));
            listaUsuariosPorRol = servicioUsuario.listarUsuariosPorRol(getFuncion().getRolId().getRolId());
            listaUsuariosEnCargaLaboral = cargaLaboralServicio.listarUsuariosDeCargaLaboral(tipoCargaLaboral);
            
            Boolean agregadosTodos = true;
            for (Usuario usu : listaUsuariosPorRol) {
                if (!listaUsuariosEnCargaLaboral.contains(usu)) {
                    agregadosTodos = false;
                    setUsuario(usu); //selecciono el usuario a asignar
                    break;
                }
            }
            if (agregadosTodos) {
                setCargaLaboral(cargaLaboralServicio.buscarPorCargaLaboralConTipo(tipoCargaLaboral));
                setUsuarioNombre(getCargaLaboral().getUsuId().getPerId().getPerNombre().concat(" ").concat(getCargaLaboral().getUsuId().getPerId().getPerApellidoPaterno()));
                setUsuario(getCargaLaboral().getUsuId());
                
                short aux = getCargaLaboral().getCarAsignado();
                int carga = aux;
                System.out.print("Carga-- " + aux);
                getCargaLaboral().setCarAsignado((short) (carga + cargaAdicional));
                getCargaLaboral().setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                getCargaLaboral().setCarFHR(Calendar.getInstance());
                getCargaLaboral().setCarTipo(tipoCargaLaboral);
                getCargaLaboral().setCarCantidad(getCargaLaboral().getCarCantidad() + new Long(1));
                cargaLaboralServicio.edit(getCargaLaboral());
                System.out.println("Carga Laboral asignada");
            } else {
                cargaLaboral = new CargaLaboral();
                getCargaLaboral().setUsuId(getUsuario());
                getCargaLaboral().setCarAsignado((short) (1));
                getCargaLaboral().setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                getCargaLaboral().setCarFHR(Calendar.getInstance());
                getCargaLaboral().setCarTipo(tipoCargaLaboral);
                getCargaLaboral().setCarCantidad(new Long(1));
                cargaLaboralServicio.create(getCargaLaboral());
                
                System.out.println("Carga Laboral creada y asignada");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
    
    public Usuario cargaLaboralCertificado(String tipoCargaLaboral, String nombrePagina, Integer cargaAdicional) {
        try {
            setFuncion(servicioFuncion.buscarPorNombrePagina(nombrePagina));
            listaUsuariosPorRol = servicioUsuario.listarUsuariosPorRol(getFuncion().getRolId().getRolId());
            listaUsuariosEnCargaLaboral = cargaLaboralServicio.listarUsuariosDeCargaLaboral(tipoCargaLaboral);
            
            Boolean agregadosTodos = true;
            for (Usuario usu : listaUsuariosPorRol) {
                if (!listaUsuariosEnCargaLaboral.contains(usu)) {
                    agregadosTodos = false;
                    setUsuario(usu); //selecciono el usuario a asignar
                    break;
                }
            }
            if (agregadosTodos) {
                setCargaLaboral(cargaLaboralServicio.buscarPorCargaLaboralConTipo(tipoCargaLaboral));
                setUsuarioNombre(getCargaLaboral().getUsuId().getPerId().getPerNombre().concat(" ").concat(getCargaLaboral().getUsuId().getPerId().getPerApellidoPaterno()));
                setUsuario(getCargaLaboral().getUsuId());
                
                short aux = getCargaLaboral().getCarAsignado();
                int carga = aux;
                System.out.print("Carga-- " + aux);
                getCargaLaboral().setCarAsignado((short) (carga + cargaAdicional));
                getCargaLaboral().setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                getCargaLaboral().setCarFHR(Calendar.getInstance());
                getCargaLaboral().setCarTipo(tipoCargaLaboral);
                getCargaLaboral().setCarCantidad(getCargaLaboral().getCarCantidad() + new Long(1));
                cargaLaboralServicio.edit(getCargaLaboral());
                System.out.println("Carga Laboral asignada");
            } else {
                cargaLaboral = new CargaLaboral();
                getCargaLaboral().setUsuId(getUsuario());
                getCargaLaboral().setCarAsignado((short) (1));
                getCargaLaboral().setCarUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
                getCargaLaboral().setCarFHR(Calendar.getInstance());
                getCargaLaboral().setCarTipo(tipoCargaLaboral);
                getCargaLaboral().setCarCantidad(new Long(1));
                cargaLaboralServicio.guardar(getCargaLaboral());
                System.out.println("Carga Laboral creada y asignada");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
        
    }
    
    public void restarCargaAsignadaAlUsuario(Long idUsuario, String carTipo) {
        try {
//            RESTA CARGA LABORAL
            CargaLaboral cargaLaboralSeleccionado = cargaLaboralServicio.buscarCargaLaboral(idUsuario, carTipo);
            if (cargaLaboralSeleccionado != null) {
                if (cargaLaboralSeleccionado.getCarCantidad() > 0) {
//                    short valor = (short) (cargaLaboralSeleccionado.getCarAsignado() - 1);
//                    cargaLaboralSeleccionado.setCarAsignado(valor);
//                    System.out.println(cargaLaboralSeleccionado.getCarId());
                    cargaLaboralSeleccionado.setCarCantidad(cargaLaboralSeleccionado.getCarCantidad() - new Long(1));
                    cargaLaboralServicio.edit(cargaLaboralSeleccionado);
                }
            }
            
        } catch (ServicioExcepcion e) {
            System.out.println(e);
        }
        
    }
    
    public void reasignarCargalaboralMismoUsuario(String usuarioAsignado) {
        Usuario usuarioSeleccionado = servicioUsuario.encontrarUsuarioPorNombreUsuario(usuarioAsignado);
        CargaLaboral cargaAdd = cargaLaboralServicio.buscarPorUsuario(usuarioSeleccionado);
        int auxCarga = cargaAdd.getCarAsignado() + 1;
        cargaAdd.setCarAsignado((short) auxCarga);
        if (cargaAdd.getCarCantidad() > 0) {
            cargaAdd.setCarCantidad(cargaAdd.getCarCantidad() + new Long(1));
        }
        cargaLaboralServicio.edit(cargaAdd);
    }
    
    public void actualizarCargaLaboralPor_TipoTramite(Long idTramite, Long idCargaLaboralSelec, boolean primeraLlamada) throws ServicioExcepcion {
        try {
            
            int cargaAdicional = 0;
            Tramite tramite = servicioTramite.buscarTramitePorNumero(idTramite);
            CargaLaboral cargaLaboralSelec = cargaLaboralServicio.find(new CargaLaboral(), idCargaLaboralSelec);
            TramiteDocumentoEntrega tde = servicioTramiteDocumentoEntrega.buscarPrimeroPor_Tramite(tramite.getTraNumero());
            if (tde != null) {
                TipoTramite tipoTram = tde.getTtrId();
                if (tipoTram.getTtrPeso() != null) {
                    cargaAdicional = tipoTram.getTtrPeso().intValue();
                }
            }
            if (cargaAdicional > 0) {
                if (primeraLlamada) {
                    cargaAdicional = cargaAdicional - 1;
                    cargaLaboralSelec.setCarAsignado((short) (cargaLaboralSelec.getCarAsignado() + cargaAdicional));//se resta 1 porque la carga al momento de ingresar el tramite se crea con 1   
                } else {
                    cargaLaboralSelec.setCarAsignado((short) (cargaLaboralSelec.getCarAsignado() + cargaAdicional));//se resta 1 porque la carga al momento de ingresar el tramite se crea con 1   
                }
//                if (cargaLaboralSelec.getCarCantidad() > 0) {
//                    cargaLaboralSelec.setCarCantidad(cargaLaboralSelec.getCarCantidad() + new Long(1));
//                }
                cargaLaboralServicio.edit(cargaLaboralSelec);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
