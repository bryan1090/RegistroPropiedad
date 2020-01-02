package com.rm.empresarial.bb;

import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Ciudad;
import com.rm.empresarial.modelo.Estado;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.TipoLibro;
import com.rm.empresarial.modelo.TipoPropiedad;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteValor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorMantenimientoTramiteBb")
@Dependent
public class ControladorMantenimientoTramiteBb implements Serializable {

	@Getter
	@Setter
	private Tramite tramite;

	@Getter
	@Setter
	private Persona persona;

	@Getter
	@Setter
	private Ciudad ciudadSeleccionada;

	@Getter
	@Setter
	private Parroquia parroquiaSeleccionada;

	@Getter
	@Setter
	private TipoTramite tipoTramiteSeleccionado;

	@Getter
	@Setter
	private TramiteDetalle tramiteDetalle;

	@Getter
	@Setter
	private TipoTramiteCompareciente tipoTramiteComparecienteSeleccionado;

	@Getter
	@Setter
	private TramiteDetalle tramiteDetalleSeleccionado;

	@Getter
	@Setter
	private TipoLibro tipoLibroSeleccionado;

	@Getter
	@Setter
	private Repertorio repertorio;

	@Getter
	@Setter
	private Repertorio repertorioSeleccionado;

	@Getter
	@Setter
	private TramiteValor tramiteValor;

	@Getter
	@Setter
	private TramiteValor tramiteValorSeleccionado;

	@Getter
	@Setter
	private List<Notaria> listaNotarias;

	@Getter
	@Setter
	private List<Ciudad> listaCiudades;

	@Getter
	@Setter
	private List<Parroquia> listaParroquias;

	@Getter
	@Setter
	private List<Estado> listaEstados;

	@Getter
	@Setter
	private List<TipoTramite> listaTipoTramite;

	@Getter
	@Setter
	private List<TipoTramiteCompareciente> listTipoTramiteCompareciente;

	@Getter
	@Setter
	private List<TipoLibro> listaTipoLibro;

	@Getter
	@Setter
	private List<TramiteDetalle> listaTramiteDetalle;

	@Getter
	@Setter
	private List<Repertorio> listaRepertorios;

	@Getter
	@Setter
	private List<TramiteValor> listTramiteValor;

	@Getter
	@Setter
	private Boolean estado = Boolean.FALSE;

	@Getter
	@Setter
	private String identificacion;

	@Getter
	@Setter
	private Date fechaRegistro;

	@Getter
	@Setter
	private String numRepertorio;

	@Getter
	@Setter
	private Long numTramite;

	///

	public ControladorMantenimientoTramiteBb() {

	}

	public void inicializar() {
		System.out.print("com.rm.empresarial.controlador.ControladorMantenimientoTramiteBb.inicialize()");
		tramite = new Tramite();
		persona = new Persona();
		parroquiaSeleccionada = new Parroquia();
		ciudadSeleccionada = new Ciudad();
		tipoTramiteSeleccionado = new TipoTramite();
		tramiteDetalle = new TramiteDetalle();
		tramiteDetalleSeleccionado = new TramiteDetalle();
		tipoTramiteComparecienteSeleccionado = new TipoTramiteCompareciente();
		tipoLibroSeleccionado = new TipoLibro();
		repertorioSeleccionado = new Repertorio();
		tramiteValor = new TramiteValor();
		tramiteValorSeleccionado = new TramiteValor();
		listaNotarias = new ArrayList<>();
		listaCiudades = new ArrayList<>();
		listaEstados = new ArrayList<>();
		listaParroquias = new ArrayList<>();
		listaTipoTramite = new ArrayList<>();
		listTipoTramiteCompareciente = new ArrayList<>();
		listaTipoLibro = new ArrayList<>();
		listaTramiteDetalle = new ArrayList<>();
		listaRepertorios = new ArrayList<>();
		listTramiteValor = new ArrayList<>();
		repertorio = new Repertorio();

	}

}
