package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.ControladorMantenimientoTramiteBb;
import com.rm.empresarial.controlador.util.EncryptPassword;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.PersonaUtil;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.servicio.ParroquiaServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.TipoLibroServicio;
import com.rm.empresarial.servicio.TipoTramiteComparecienteServicio;
import com.rm.empresarial.servicio.TipoTramiteServicio;
import com.rm.empresarial.servicio.TramiteDetalleServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
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
@Named(value = "controladorMantenimientoTramiteDetalle")
@ViewScoped
public class ControladorMantenimientoTramiteDetalle implements Serializable {

	@EJB
	private TramiteServicio servicioTramite;

	@EJB
	private TipoTramiteServicio servicioTipoTramite;

	@EJB
	private TipoTramiteComparecienteServicio servicioTipoTramiteCompareciente;

	@EJB
	private TipoLibroServicio servicioTipoLibro;

	@EJB
	private ParroquiaServicio servicioParroquia;

	@EJB
	private TramiteDetalleServicio servicioTramiteDetalle;

	@EJB
	private RepertorioServicio servicioRepertorio;

	@EJB
	private TramiteValorServicio servicioTramiteValor;

	@Inject
	private DataManagerSesion dataManagerSesion;

	@Inject
	private PersonaUtil personaUtil;

	@Inject
	private EncryptPassword encryptPassword;

	@Inject
	@Getter
	@Setter
	private ControladorMantenimientoTramiteBb controladorMantenimientoTramiteBb;

	@Getter
	@Setter
	private Boolean blockTabPro;

	@PostConstruct
	public void postControladorMantenimientoTramiteDetalle() {
		controladorMantenimientoTramiteBb.inicializar();
		try {
//            controladorMantenimientoTramiteBb.setTramite(servicioTramite.buscarTramitePorNumero(controladorMantenimientoTramiteBb.getTramite().getTraNumero()));
			controladorMantenimientoTramiteBb.setListaTipoTramite(servicioTipoTramite.listarTodo());
			controladorMantenimientoTramiteBb.setListaTipoLibro(servicioTipoLibro.listarTodo());
			controladorMantenimientoTramiteBb.setListaParroquias(servicioParroquia.listarTodo());
//            buscarTramiteDetalle();
		} catch (ServicioExcepcion e) {
			System.out.print(e);
		}
	}

	public ControladorMantenimientoTramiteDetalle() {
		setBlockTabPro(Boolean.TRUE);
	}

	public void cargarTipoTramiteCompareciente() {
		controladorMantenimientoTramiteBb.setListTipoTramiteCompareciente(servicioTipoTramiteCompareciente.listarTipoTramiteComparecientePorTipoTramite(controladorMantenimientoTramiteBb.getTipoTramiteSeleccionado().getTtrId()));
	}

	public void agregarTramiteDetalle() {
		Calendar fecha = Calendar.getInstance();
		fecha.setTime(controladorMantenimientoTramiteBb.getFechaRegistro());

		controladorMantenimientoTramiteBb.getTramiteDetalle().setTraNumero(controladorMantenimientoTramiteBb.getTramite());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtTtrId(controladorMantenimientoTramiteBb.getTipoTramiteSeleccionado().getTtrId());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtTtrDescripcion(controladorMantenimientoTramiteBb.getTipoTramiteSeleccionado().getTtrDescripcion());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTtcId(controladorMantenimientoTramiteBb.getTipoTramiteComparecienteSeleccionado());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtTpcId(controladorMantenimientoTramiteBb.getTipoTramiteComparecienteSeleccionado().getTpcId().getTpcId());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtTpcDescripcion(controladorMantenimientoTramiteBb.getTipoTramiteComparecienteSeleccionado().getTpcId().getTpcDescripcion());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtTpcCodigo(controladorMantenimientoTramiteBb.getTipoTramiteComparecienteSeleccionado().getTpcId().getTpcCodigo());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtParId(controladorMantenimientoTramiteBb.getParroquiaSeleccionada().getParId());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtParNombre(controladorMantenimientoTramiteBb.getParroquiaSeleccionada().getParNombre());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtTplId(controladorMantenimientoTramiteBb.getTipoTramiteSeleccionado().getTplId().getTplId().shortValue());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtTplDescripcion(controladorMantenimientoTramiteBb.getTipoTramiteSeleccionado().getTplId().getTplDescripcion());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtFHR(Calendar.getInstance());
		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtFechaRegistro(fecha);
		controladorMantenimientoTramiteBb.getRepertorio().setTraNumero(controladorMantenimientoTramiteBb.getTramite());
		controladorMantenimientoTramiteBb.getRepertorio().setRepTtrId(controladorMantenimientoTramiteBb.getTipoTramiteSeleccionado().getTtrId().shortValue());
		controladorMantenimientoTramiteBb.getRepertorio().setRepTtrDescripcion(controladorMantenimientoTramiteBb.getTipoTramiteSeleccionado().getTtrDescripcion());
		controladorMantenimientoTramiteBb.getRepertorio().setRepEstado("A");
		controladorMantenimientoTramiteBb.getRepertorio().setRepUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
		controladorMantenimientoTramiteBb.getRepertorio().setRepFHR(Calendar.getInstance().getTime());

		controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtNumeroRepertorio(controladorMantenimientoTramiteBb.getRepertorio().getRepNumero().intValue());

		controladorMantenimientoTramiteBb.getTramiteValor().setTtrId(controladorMantenimientoTramiteBb.getTipoTramiteSeleccionado());
		controladorMantenimientoTramiteBb.getTramiteValor().setTraNumero(controladorMantenimientoTramiteBb.getTramite());
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvValor(BigDecimal.ZERO);
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvEstado("A");
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvFHR(Calendar.getInstance().getTime());
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvIva(BigDecimal.ZERO);
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvValorBien(BigDecimal.ZERO);
		controladorMantenimientoTramiteBb.getTramiteValor().setTraNumPredio("0");
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvCantidad(BigDecimal.ZERO);
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvParId(Long.parseLong("0"));
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvParNombre("");
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvNumCatastro("0");
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvAccion(0);
		controladorMantenimientoTramiteBb.getTramiteValor().setTrvPrincipal(Boolean.TRUE);

		controladorMantenimientoTramiteBb.getListaRepertorios().add(controladorMantenimientoTramiteBb.getRepertorio());
		controladorMantenimientoTramiteBb.getListaTramiteDetalle().add(controladorMantenimientoTramiteBb.getTramiteDetalle());
		controladorMantenimientoTramiteBb.getListTramiteValor().add(controladorMantenimientoTramiteBb.getTramiteValor());

		controladorMantenimientoTramiteBb.setTramiteDetalle(new TramiteDetalle());
		controladorMantenimientoTramiteBb.setTramiteValor(new TramiteValor());
		controladorMantenimientoTramiteBb.setRepertorio(new Repertorio());
		controladorMantenimientoTramiteBb.setPersona(new Persona());
	}

	public void guardarTramiteDetalle() throws IOException {

		for (Repertorio repertorio : controladorMantenimientoTramiteBb.getListaRepertorios()) {
			try {
				servicioRepertorio.guardar(repertorio);
			} catch (ServicioExcepcion e) {
				System.out.print(e);
			}
		}

		for (TramiteDetalle traDetalle : controladorMantenimientoTramiteBb.getListaTramiteDetalle()) {
			try {
				servicioTramiteDetalle.guardar(traDetalle);
			} catch (ServicioExcepcion e) {
				System.out.print(e);
			}
		}

		for (TramiteValor traValor : controladorMantenimientoTramiteBb.getListTramiteValor()) {
			try {
				servicioTramiteValor.guardar(traValor);
			} catch (ServicioExcepcion e) {
				System.out.print(e);
			}
		}

		terminar();

	}

	public void buscarPersona() {
		System.out.println("com.rm.empresarial.controlador.RecepcionDocumentacionControlador.buscarPersona()");
		if (controladorMantenimientoTramiteBb.getPersona().getPerIdentificacion() != null && controladorMantenimientoTramiteBb.getPersona().getPerIdentificacion().length() < 13) {
			try {
				controladorMantenimientoTramiteBb.setPersona(personaUtil.obtenerDatosPersona(controladorMantenimientoTramiteBb.getPersona().getPerIdentificacion()));
			} catch (ServicioExcepcion e) {
				System.out.print(e);
			}

		}

		if (controladorMantenimientoTramiteBb.getPersona() != null) {
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerTipoContribuyente(controladorMantenimientoTramiteBb.getPersona().getPerTipoContribuyente());
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerTipoIdentificacion(controladorMantenimientoTramiteBb.getPersona().getPerTipoIdentificacion());
			controladorMantenimientoTramiteBb.getTramiteDetalle().setPerId(controladorMantenimientoTramiteBb.getPersona());
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerIdentificacion(controladorMantenimientoTramiteBb.getPersona().getPerIdentificacion());
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerNombre(controladorMantenimientoTramiteBb.getPersona().getPerNombre());
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerApellidoPaterno(controladorMantenimientoTramiteBb.getPersona().getPerApellidoPaterno());
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerApellidoMaterno(controladorMantenimientoTramiteBb.getPersona().getPerApellidoMaterno());
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtConyuguePerId(controladorMantenimientoTramiteBb.getPersona().getPerIdConyuge().getPerId());
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtConyuguePerNombre(controladorMantenimientoTramiteBb.getPersona().getPerNombre() + " " + controladorMantenimientoTramiteBb.getPersona().getPerApellidoPaterno() + " " + controladorMantenimientoTramiteBb.getPersona().getPerApellidoMaterno());
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtConyuguePerTipoIden(controladorMantenimientoTramiteBb.getPersona().getPerIdConyuge().getPerTipoIdentificacion());
		} else {
			controladorMantenimientoTramiteBb.getTramiteDetalle().setPerId(null);
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerIdentificacion(null);
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerNombre(null);
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerApellidoPaterno(null);
			controladorMantenimientoTramiteBb.getTramiteDetalle().setTdtPerApellidoMaterno(null);
		}

	}

	public void buscarTramiteDetalle() {
		try {
			controladorMantenimientoTramiteBb.setListaTramiteDetalle(servicioTramiteDetalle.buscarPorNumeroDeTramite(controladorMantenimientoTramiteBb.getTramite().getTraNumero()));
		} catch (ServicioExcepcion e) {
			System.out.print(e);
		}
	}

	public void eliminarTramiteDetalle() throws IOException {
		controladorMantenimientoTramiteBb.getListaTramiteDetalle().remove(controladorMantenimientoTramiteBb.getTramiteDetalleSeleccionado());
		controladorMantenimientoTramiteBb.getListaRepertorios().remove(controladorMantenimientoTramiteBb.getRepertorioSeleccionado());
		controladorMantenimientoTramiteBb.getListTramiteValor().remove(controladorMantenimientoTramiteBb.getTramiteValorSeleccionado());
	}

	public void refrescar() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MantenimientoTramite/MantenimientoTramiteDetalle.xhtml?numTramite=" + controladorMantenimientoTramiteBb.getTramite().getTraNumero());
	}

	public void terminar() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/RM-EMPRESARIAL-web/paginas/PROCESOS/MantenimientoTramite/MantenimientoTramite.xhtml");
		} catch (IOException e) {
			System.out.print(e);
		}
	}

}
