package com.rm.empresarial.controlador;

import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Rol;
import com.rm.empresarial.modelo.TramiteAccion;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.RolServicio;
import com.rm.empresarial.servicio.TramiteAccionServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.UsuarioServicio;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorListadoTrabajo")
@ViewScoped
public class ControladorListadoTrabajo implements Serializable {

    @Inject
    @Getter
    @Setter
    DataManagerSesion dataManagerSesion;

    @EJB
    private TramiteAccionServicio servicioTramiteAccion;
    @EJB
    private RolServicio servicioRol;
    @EJB
    private UsuarioServicio servicioUsuario;
    @EJB
    private TramiteDetalleServicio servicioTramiteDetalle;

    @Getter
    @Setter
    private List<Rol> listaRol;
    @Getter
    @Setter
    private List<Usuario> listaUsuario;
    @Getter
    @Setter
    private List<Usuario> listaUsuarioTramites;
    @Getter
    @Setter
    private List<TramiteAccion> listaConsultada;
    @Getter
    @Setter
    private List<TramiteAccion> listaTramiteSeleccionado;
    @Getter
    @Setter
    private List<String> listUsuarios;

    @Getter
    @Setter
    private TramiteAccion tramiteAccionSeleccionado;
    @Getter
    @Setter
    private TramiteAccion tramiteAccionFiltradoSeleccionado;

    @Getter
    @Setter
    private Date busquedaFecha;
    @Getter
    @Setter
    private String busquedaUsuario;
    @Getter
    @Setter
    private String busquedaRol;
    @Getter
    @Setter
    private int cantidadRegistros;

    @PostConstruct
    public void postControladorListadoTrabajo() {
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            busquedaFecha = df.parse(df.format(Calendar.getInstance().getTime()));
            cargarDatos();
        } catch (ParseException e) {
        }
    }

    public ControladorListadoTrabajo() {
        listaRol = new ArrayList<>();
        listaUsuario = new ArrayList<>();
        listaConsultada = new ArrayList<>();
        listaTramiteSeleccionado = new ArrayList<>();
        listaUsuarioTramites = new ArrayList<>();
        listUsuarios = new ArrayList<>();
        tramiteAccionSeleccionado = new TramiteAccion();
        tramiteAccionFiltradoSeleccionado = new TramiteAccion();
        busquedaUsuario = new String();
        busquedaRol = new String();
    }

    public void cargarDatos() {
        setListaRol(servicioRol.listarTodo());
        setListaUsuario(servicioUsuario.listarTodo());
        setListaConsultada(servicioTramiteAccion.listarTramiteAccionPorFecha(busquedaFecha));
        setListaTramiteSeleccionado(servicioTramiteAccion.listarPorFecha(busquedaFecha));
        setListUsuarios(servicioTramiteAccion.listarUsuPorFecha(busquedaFecha));
    }

    public void procesarConsulta() {
        if (busquedaFecha != null) {
            setListaConsultada(servicioTramiteAccion.listarTramiteAccionPorFecha(busquedaFecha));
        } else if (!busquedaUsuario.isEmpty()) {
            setListaConsultada(servicioTramiteAccion.listarTramiteAccionPorUsuario(busquedaUsuario));
        } else if (!busquedaRol.isEmpty()) {
            setListaConsultada(servicioTramiteAccion.listarTramiteAccionPorRol(busquedaRol));
        }
        if (!busquedaUsuario.isEmpty() && busquedaFecha != null) {
            setListaConsultada(servicioTramiteAccion.listarTramiteAccionPorUsuarioyFecha(busquedaUsuario, busquedaFecha));
        } else if (!busquedaRol.isEmpty() && busquedaFecha != null) {
            setListaConsultada(servicioTramiteAccion.listarTramiteAccionPorRolyFecha(busquedaRol, busquedaFecha));
        } else if (!busquedaUsuario.isEmpty() && !busquedaRol.isEmpty()) {
            setListaConsultada(servicioTramiteAccion.listarTramiteAccionPorUsuarioyRol(busquedaUsuario, busquedaRol));
        }
        if (!busquedaUsuario.isEmpty() && !busquedaRol.isEmpty() && busquedaFecha != null) {
            setListaConsultada(servicioTramiteAccion.listarTramiteAccionPorUsuarioRolyFecha(busquedaUsuario, busquedaRol, busquedaFecha));
        }

        listaTramiteSeleccionado = new ArrayList<>();
    }

    public void buscarPorTramite() {
        if (busquedaFecha != null) {
            setListaTramiteSeleccionado(servicioTramiteAccion.listarPorNtramiteyFecha(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaFecha));
            setListUsuarios(servicioTramiteAccion.listarUsuariosPorFecha(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaFecha));
        } else if (!busquedaUsuario.isEmpty()) {
            setListaTramiteSeleccionado(servicioTramiteAccion.listarPorNtramiteUsuario(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaUsuario));
            setListUsuarios(servicioTramiteAccion.listarUsuariosPorUsuario(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaUsuario));
        } else if (!busquedaRol.isEmpty()) {
            setListaTramiteSeleccionado(servicioTramiteAccion.listarPorNtramiteRol(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaRol));
            setListUsuarios(servicioTramiteAccion.listarUsuariosPorRol(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaRol));
        }
        if (!busquedaUsuario.isEmpty() && busquedaFecha != null) {
            setListaTramiteSeleccionado(servicioTramiteAccion.listarPorNtramiteUsuarioFecha(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaUsuario, busquedaFecha));
            setListUsuarios(servicioTramiteAccion.listarUsuariosPorUsuarioFecha(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaUsuario, busquedaFecha));
        } else if (!busquedaRol.isEmpty() && busquedaFecha != null) {
            setListaTramiteSeleccionado(servicioTramiteAccion.listarPorNtramiteRolFecha(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaRol, busquedaFecha));
            setListUsuarios(servicioTramiteAccion.listarUsuariosPorRolFecha(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaRol, busquedaFecha));
        } else if (!busquedaUsuario.isEmpty() && !busquedaRol.isEmpty()) {
            setListaTramiteSeleccionado(servicioTramiteAccion.listarPorNtramiteUsuarioRol(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaUsuario, busquedaRol));
            setListUsuarios(servicioTramiteAccion.listarUsuariosPorUsuarioRol(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaUsuario, busquedaRol));
        }
        if (!busquedaUsuario.isEmpty() && !busquedaRol.isEmpty() && busquedaFecha != null) {
            setListaTramiteSeleccionado(servicioTramiteAccion.listarPorNtramiteUsuarioRolFecha(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaUsuario, busquedaRol, busquedaFecha));
            setListUsuarios(servicioTramiteAccion.listarUsuariosPorUsuarioRolFecha(getTramiteAccionSeleccionado().getTraNumero().getTraNumero(), busquedaUsuario, busquedaRol, busquedaFecha));
        }
    }

    public void setearBusqueda() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/registroTrabajo/listadoTrabajo.xhtml");
    }

}
