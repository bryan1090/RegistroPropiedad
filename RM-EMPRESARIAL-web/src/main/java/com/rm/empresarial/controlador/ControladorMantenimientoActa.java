package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.ControladorMantenimientoTramiteBb;
import com.rm.empresarial.controlador.util.JsfUtil;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.controlador.util.TransformadorNumerosLetrasUtil;
import com.rm.empresarial.dao.RepertorioPropiedadDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.Acta;
import com.rm.empresarial.modelo.Lindero;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.PropiedadDetalle;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioPropiedad;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Secuencia;
import com.rm.empresarial.modelo.TipoActa;
import com.rm.empresarial.modelo.TipoPropiedad;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.UnidMedida;
import com.rm.empresarial.servicio.ActaServicio;
import com.rm.empresarial.servicio.LinderoServicio;
import com.rm.empresarial.servicio.NuevasMatriculasServicio;
import com.rm.empresarial.servicio.ParroquiaServicio;
import com.rm.empresarial.servicio.PropiedadDetalleServicio;
import com.rm.empresarial.servicio.PropiedadServicio;
import com.rm.empresarial.servicio.RepertorioServicio;
import com.rm.empresarial.servicio.RepertorioUsuarioServicio;
import com.rm.empresarial.servicio.SecuenciaServicio;
import com.rm.empresarial.servicio.TipoActaServicio;
import com.rm.empresarial.servicio.TipoPropiedadClaseServicio;
import com.rm.empresarial.servicio.TipoPropiedadServicio;
import com.rm.empresarial.servicio.TramiteServicio;
import com.rm.empresarial.servicio.TramiteValorServicio;
import com.rm.empresarial.servicio.UnidMedidaServicio;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DecimalDV;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.ZoomEvent;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import jdk.nashorn.internal.parser.JSONParser;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.event.map.PointSelectEvent;
import org.primefaces.event.map.ReverseGeocodeEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.LatLngBounds;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

/**
 *
 * @author DJimenez
 */
@Named(value = "controladorMantenimientoActa")
@ViewScoped
public class ControladorMantenimientoActa implements Serializable {

	@Inject
	DataManagerSesion dataManagerSesion;

	@Inject
	private TramiteUtil tramiteUtil;

	@Inject
	@Getter
	@Setter
	private SecuenciaControlador secuenciaControlador;

	@Inject
	@Getter
	@Setter
	private TransformadorNumerosLetrasUtil TransformadorNumerosLetrasUtil;

	@Getter
	@Setter
	@Inject
	private ControladorMantenimientoTramiteBb controladorMantenimientoTramiteBb;

	@EJB
	private ActaServicio servicioActa;

	@EJB
	private TramiteValorServicio servicioTramiteValor;

	@EJB
	private TramiteServicio servicioTramite;

	@EJB
	private RepertorioServicio servicioRepertorio;

	@EJB
	private RepertorioPropiedadDao servicioRepertorioPropiedad;

	@EJB
	private PropiedadServicio servicioPropiedad;

	@EJB
	private ParroquiaServicio servicioParroquia;

	@EJB
	private LinderoServicio servicioLindero;

	@EJB
	private SecuenciaServicio secuenciaServicio;

	@EJB
	private NuevasMatriculasServicio servicioNuevasMatriculas;

	@EJB
	private TipoPropiedadServicio servicioTipoPropiedad;

	@EJB
	private TipoPropiedadClaseServicio servicioTipoPropiedadClase;

	@EJB
	private UnidMedidaServicio servicioUnidMedida;

	@EJB
	private RepertorioUsuarioServicio servicioRepertorioUsuario;

	@EJB
	private PropiedadDetalleServicio servicioPropiedadDetalle;

	@EJB
	private TipoActaServicio servicioTipoActa;

	@Getter
	@Setter
	private List<Parroquia> listaParroquias;

	@Getter
	@Setter
	private List<Lindero> listaLinderos;

	@Getter
	@Setter
	private List<UnidMedida> listaUnidMedida;

	@Getter
	@Setter
	private Propiedad propiedadNueva;

	@Getter
	@Setter
	private Lindero linderoSeleccionado;

	@Getter
	@Setter
	private List<TipoPropiedad> listaTipoPropiedad;

	@Getter
	@Setter
	private List<Propiedad> listaPropiedadesSeleccionadas;

	@Getter
	@Setter
	private List<Propiedad> listaPropiedades;

	@Getter
	@Setter
	private TramiteValor tramiteValorSeleccionado;

	@Getter
	@Setter
	private Acta acta;

	@Getter
	@Setter
	private Tramite tramiteDigitado;

	@Getter
	@Setter
	private List<TramiteValor> listaTramiteValor;

	@Getter
	@Setter
	private List<RepertorioUsuario> listaRepertorioUsuario;

	@Getter
	@Setter
	private Repertorio repertorio;

	@Getter
	@Setter
	private Boolean mostrarComboGrupo;

	@Getter
	@Setter
	private Boolean boolOcultarCmbParroquia = false;

	@Getter
	@Setter
	private Boolean boolVerNuevaProp = false;

	@Getter
	@Setter
	private Boolean esPropiedadNueva;

	@Getter
	@Setter
	private String catastro;

	@Getter
	@Setter
	private String predio;

	@Getter
	@Setter
	private Boolean estaActa;

	@Getter
	@Setter
	private Boolean boolOcultartabs = false;

	@Getter
	@Setter
	private String centerGeoMap;

	@Getter
	@Setter
	private String centerRevGeoMap;

	@Getter
	@Setter
	private Secuencia secuencia;

	@Getter
	@Setter
	private MapModel geoModel;

	@Getter
	@Setter
	private MapModel revGeoModel;

	@Getter
	@Setter
	private String direccion;

	@Getter
	@Setter
	private String url;

	@Getter
	@Setter
	private int zoom;

	@Getter
	@Setter
	private Double lat;

	@Getter
	@Setter
	private Double lng;
	
	@Getter
	@Setter
	private Long matricula;

	@PostConstruct
	public void postControladorMantenimientoActa() {
		System.out.print("com.rm.empresarial.controlador.ControladorMantenimientoActa.inicialize()");
		controladorMantenimientoTramiteBb.inicializar();
	}

	public ControladorMantenimientoActa() {
		repertorio = new Repertorio();
		tramiteDigitado = new Tramite();
		tramiteValorSeleccionado = new TramiteValor();
		propiedadNueva = new Propiedad();
		propiedadNueva.setPrdEspacial("");
		linderoSeleccionado = new Lindero();
		secuencia = new Secuencia();
		acta = new Acta();
		listaTramiteValor = new ArrayList<>();
		listaPropiedades = new ArrayList<>();
		listaPropiedadesSeleccionadas = new ArrayList<>();
		listaTipoPropiedad = new ArrayList<>();
		listaParroquias = new ArrayList<>();
		listaUnidMedida = new ArrayList<>();
		listaRepertorioUsuario = new ArrayList<>();
		listaLinderos = new ArrayList<>();
		geoModel = new DefaultMapModel();
		revGeoModel = new DefaultMapModel();
		esPropiedadNueva = true;
		estaActa = false;
		centerGeoMap = "-0.2029,-78.4911";
		zoom = 15;
	}

	public void buscarTramiteValor() {
		try {
			if (Objects.equals(controladorMantenimientoTramiteBb.getNumTramite(), controladorMantenimientoTramiteBb.getTramite().getTraNumero())) {
				setListaTramiteValor(servicioTramiteValor.buscarPorTramite(controladorMantenimientoTramiteBb.getTramite()));
			} else {
				setTramiteDigitado(servicioTramite.buscarTramitePorNumero(controladorMantenimientoTramiteBb.getNumTramite()));
				setListaTramiteValor(servicioTramiteValor.buscarPorTramite(tramiteDigitado));
			}
		} catch (ServicioExcepcion e) {
			System.out.print(e);
		}
	}

	public void buscarRepertorio() {
		acta = new Acta();
		setRepertorio(servicioRepertorio.encontrarPorTramiteyTipo(tramiteValorSeleccionado.getTraNumero(), tramiteValorSeleccionado.getTtrId().getTtrId()));
		buscarPropiedades();
		setBoolVerNuevaProp(Boolean.TRUE);
		setBoolOcultartabs(Boolean.TRUE);
	}

	public void buscarPropiedades() {
		setListaPropiedades(servicioPropiedad.listarPorRepertorio(repertorio.getRepNumero()));
		setListaPropiedadesSeleccionadas(servicioPropiedad.listarPorRepertorio(repertorio.getRepNumero()));
	}

	public void seleccionarClase() {
		System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.seleccionarClase()");
		System.out.println("" + propiedadNueva.getPrdClasePropiedad());
		if (propiedadNueva.getPrdClasePropiedad().equals("H")) {
			mostrarComboGrupo = true;
		} else {
			mostrarComboGrupo = false;
		}

	}

	public void prepararActa() throws ParseException {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		Date today = new Date();
		Date todayWithZeroTime = formatter.parse(formatter.format(today));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);
		int anio = calendar.get(Calendar.YEAR);
		Long idTipoActa = servicioActa.buscarTipoActa();
		acta.setTpaId(servicioTipoActa.find(new TipoActa(), idTipoActa));
		acta.setRepNumero(repertorio);
		acta.setActBis("0");
		acta.setActInscripcion(BigInteger.ZERO);
		acta.setActAnio(anio);
		acta.setActUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
		acta.setActFch(Calendar.getInstance().getTime());
		acta.setActFHR(Calendar.getInstance().getTime());
		acta.setActEstado("A");
		acta.setActMigrado("TL");
		setEstaActa(Boolean.TRUE);
	}

	public void guardarActa() {
		try {
			if (listaPropiedadesSeleccionadas.size() != 0) {
				for (Propiedad prop : listaPropiedadesSeleccionadas) {
					secuenciaControlador.generarSecuencia("ACT");
					//obtengo el siguiente valor de la secuencia
					setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
					int auxSecuencia = getSecuencia().getSecActual();
					getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
					Long sec = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
					Long idGenerado = new Long(sec);
					secuenciaServicio.guardar(getSecuencia());

					acta.setActActa(acta.getActActaPdf());
					acta.setActNumero(idGenerado);
					acta.setPrdMatricula(prop);
					acta.setActCatastro(prop.getPrdCatastro());
					acta.setActPredio(prop.getPrdPredio());

					servicioActa.guardar(acta);
					guardarRepertorioPropiedad(prop);
				}
				refresh();
			} else {
				JsfUtil.addErrorMessage("Debe Seleccionar Almenos Una Propiedad");
			}
		} catch (ServicioExcepcion e) {
			System.out.print(e);
		}
	}

	public void guardarPropiedad() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {

			//obtengo la secuencia
			secuenciaControlador.generarSecuencia("MAT");
			//obtengo el siguiente valor de la secuencia
			setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
			int auxSecuencia = getSecuencia().getSecActual();
			getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
			Long idGenerado = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());
//        setMatricula(idGenerado);
			secuenciaServicio.guardar(getSecuencia());

			if (esPropiedadNueva) {
				if (listaLinderos.size() >= 2) {
					try {
						propiedadNueva.setPrdMatricula(String.valueOf(idGenerado));
						propiedadNueva.setPrdUserCreador(" ");
						propiedadNueva.setPrdEstadoRegistro("A");
						propiedadNueva.setPrdEstadoPropiedad("AC");
						propiedadNueva.setPrdSector(" ");
						propiedadNueva.setPrdFHM(Calendar.getInstance().getTime());
						propiedadNueva.setPrdFHR(Calendar.getInstance().getTime());
						propiedadNueva.setPrdUserModificacion(" ");
						propiedadNueva.setPrdUbicacion(" ");

//                        TipoPropiedadClase tipoPropiedadClasePorDefecto;
//                        tipoPropiedadClasePorDefecto = servicioTipoPropiedadClase.find(new TipoPropiedadClase(), "NIN");
//                        propiedadNueva.setTclId(tipoPropiedadClasePorDefecto);
						servicioNuevasMatriculas.create(propiedadNueva);
						listaPropiedades.add(propiedadNueva);
						listaPropiedadesSeleccionadas.add(propiedadNueva);
						context.addMessage(null, new FacesMessage("Propiedad Creada", ""));
					} catch (Exception e) {
						Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad no creada", ""));

					}

					try {
						for (Lindero lindero : listaLinderos) {
							lindero.setLdrFHR(Calendar.getInstance().getTime());
							lindero.setLdrUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
							lindero.setPrdMatricula(propiedadNueva);
							servicioLindero.create(lindero);
						}
//                        context.addMessage(null, new FacesMessage("Linderos agregados", ""));
					} catch (Exception e) {
						Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
						context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Linderos no creados ", ""));
					}

					tramiteUtil.insertarTramiteAccion(controladorMantenimientoTramiteBb.getRepertorio().getTraNumero(),
							propiedadNueva.getPrdMatricula(),
							"Propiedad creada con matricula "
							+ propiedadNueva.getPrdMatricula() + ", " + "catastro " + propiedadNueva.getPrdCatastro()
							+ " y predio " + propiedadNueva.getPrdPredio(),
							controladorMantenimientoTramiteBb.getRepertorio().getTraNumero().getTraEstado(),
							controladorMantenimientoTramiteBb.getRepertorio().getTraNumero().getTraEstadoRegistro(),
							null);
				} else {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "La propiedad debe tener mínimo 2 linderos.", ""));
				}

			} else {
				propiedadNueva.setTpdId(propiedadNueva.getTpdId());
				servicioNuevasMatriculas.edit(propiedadNueva);
				context.addMessage(null, new FacesMessage("Propiedad Actualizada", ""));
				tramiteUtil.insertarTramiteAccion(repertorio.getTraNumero(),
						propiedadNueva.getPrdMatricula().toString(),
						"Propiedad con matricula "
						+ propiedadNueva.getPrdMatricula() + "/catastro " + propiedadNueva.getPrdCatastro()
						+ "/predio " + propiedadNueva.getPrdPredio() + " actualizada",
						repertorio.getTraNumero().getTraEstado(),
						repertorio.getTraNumero().getTraEstadoRegistro(),
						null);

				for (Lindero lindero : listaLinderos) {
					lindero.setLdrFHR(Calendar.getInstance().getTime());
					lindero.setLdrUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
					lindero.setPrdMatricula(propiedadNueva);
					servicioLindero.edit(lindero);
				}
//                context.addMessage(null, new FacesMessage("Linderos Actualizados", ""));

			}

		} catch (ServicioExcepcion e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
			FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Propiedad No Actualizada", "");
			FacesContext.getCurrentInstance().addMessage("Error", facesMsg);
		}

	}

	public void guardarRepertorioPropiedad(Propiedad propiedad) {
		//GUARDANDO REPERTORIO PROPIEDAD
		if (!servicioRepertorioPropiedad.existeRepertorioPropiedad(repertorio.getRepNumero(), propiedad.getPrdMatricula())) {
			RepertorioPropiedad nuevoRepPro = new RepertorioPropiedad();
			nuevoRepPro.setPrdMatricula(propiedad);
			nuevoRepPro.setRepNumero(repertorio);
			nuevoRepPro.setRpdFHR(Calendar.getInstance().getTime());
			nuevoRepPro.setRpdUser(dataManagerSesion.getUsuarioLogeado().getUsuLogin());
			servicioRepertorioPropiedad.create(nuevoRepPro);
			tramiteUtil.insertarTramiteAccion(nuevoRepPro.getRepNumero().getTraNumero(),
					nuevoRepPro.getRepNumero().getRepNumero().toString(),
					"Repertorio " + nuevoRepPro.getRpdId() + " asignado a la propiedad "
					+ propiedad.getPrdMatricula(),
					nuevoRepPro.getRepNumero().getTraNumero().getTraEstado(),
					nuevoRepPro.getRepNumero().getTraNumero().getTraEstadoRegistro(),
					null);
		}
	}

	public void crearLindero() {
		System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.crearLindero()");
		FacesContext context = FacesContext.getCurrentInstance();

		listaLinderos.add(linderoSeleccionado);

		linderoSeleccionado = new Lindero();
	}

	public void preCrearLindero() {
		System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.preCrearLindero()");

		linderoSeleccionado = new Lindero();
	}

	public void seleccionarParroquiaActual() {
		try {
			if (propiedadNueva.getPrdMatricula() != null) {
				Parroquia parroquiaActual = servicioParroquia.find(new Parroquia(), getPropiedadNueva().getPrdTdtParId());
				getPropiedadNueva().setPrdTdtParNombre(parroquiaActual.getParNombre());

			}

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

	public void seleccionarParroquiaHistorica() {
//        Parroquia parroquiaActual= servicioParroquia.find(new Parroquia(), getPropiedadNueva().getPrdTdtParId());
//        getPropiedadNueva().setPrdTdtParNombre(parroquiaActual.getParNombre());
		try {

			Parroquia parroquiaHistorica = servicioParroquia.find(new Parroquia(), getPropiedadNueva().getPrdTdtParIdH());
			getPropiedadNueva().setPrdTdtParNombreH(parroquiaHistorica.getParNombre());
//        getPropiedadNueva().setPrdTdtParIdH();
			getPropiedadNueva().setPrdTdtParNombre(parroquiaHistorica.getParNombre());
			getPropiedadNueva().setPrdTdtParId(parroquiaHistorica.getParId());
			llenarDescripcion2();
//        getPropiedadNueva().setPrdDescripcion2("Departamento situado en la parroquia " + getPropiedadNueva().getPrdTdtParNombreH() + " de este Cantón");

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}

	}

	public void llenarDescripcion1() {
		String texto1 = "";
		String texto2 = "";
		try {
			texto1 = (TransformadorNumerosLetrasUtil.transformador(getPropiedadNueva().getPrdSuperficie().toString())).toString();
			texto2 = getPropiedadNueva().getUmdId().getUmdNombre();
		} catch (Exception e) {
		}
		getPropiedadNueva().setPrdDescripcion1(texto1 + texto2);
//        System.out.println("desc1: " + getPropiedadNueva().getPrdDescripcion1());
	}

	public void llenarDescripcion2() {

		String texto = "";
		String tipoPropiedad = "";
		String parroquia = "";
		try {
			tipoPropiedad = getPropiedadNueva().getTpdId().getTpdNombre().trim();
			parroquia = getPropiedadNueva().getPrdTdtParNombre().trim();
			texto = tipoPropiedad + " situado en la Parroquia " + parroquia + " de este Cantón";
		} catch (Exception e) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
		}
		getPropiedadNueva().setPrdDescripcion2(texto);
//        System.out.println("desc2: " + getPropiedadNueva().getPrdDescripcion2());
	}

	public void borrarLindero() {
		System.out.println("com.rm.empresarial.controlador.ControladorNuevasMatriculas.borrarLinero()");
		System.out.println(linderoSeleccionado.getLdrNombre());
		Iterator<Lindero> iterador = listaLinderos.iterator();
		while (iterador.hasNext()) {
			Lindero next = iterador.next();
			if (next.getLdrNombre().equals(linderoSeleccionado.getLdrNombre())) {
				iterador.remove();
				break;
			}
		}
		if (linderoSeleccionado.getLdrId() != null) {
			servicioLindero.remove(linderoSeleccionado);
		}

//        if (servicioLindero.contains(linderoSeleccionado)) {
//            System.out.println("lindero managed");
//        }
//        linderoSeleccionado = servicioLindero.find(new Lindero(), linderoSeleccionado.getLdrId());
//        if (servicioLindero.contains(linderoSeleccionado)) {
//            System.out.println("lindero managed 2");
//        }
		linderoSeleccionado = new Lindero();
	}

	public void abrirNuevaPropiedad() throws ServicioExcepcion {
		setListaParroquias(servicioParroquia.listarTodo());
		setListaTipoPropiedad(servicioTipoPropiedad.listarTodo());
		setListaUnidMedida(servicioUnidMedida.listarTodo());

	}

	public void actualizarMatricula() throws ServicioExcepcion {
		secuenciaControlador.generarSecuencia("MAT");
		//obtengo el siguiente valor de la secuencia
		setSecuencia(getSecuenciaControlador().getControladorBb().getSecuencia());
		int auxSecuencia = getSecuencia().getSecActual();
		getSecuencia().setSecActual(auxSecuencia + 1);
//                System.out.print("Secuencia-- " + auxSecuencia);
		Long idGenerado = new Long(secuenciaControlador.getControladorBb().getNumeroTramite());

		//        setMatricula(idGenerado);
		secuenciaServicio.guardar(getSecuencia());

		setMatricula(idGenerado);
		propiedadNueva.setPrdMatricula(String.valueOf(idGenerado));

	}

	public void buscarPropiedad() {
		try {
			Boolean entra = Boolean.TRUE;
			if (servicioPropiedad.buscarPropiedadPor_predio_Y_catastro(predio, catastro) != null) {
				for (Propiedad pro : listaPropiedades) {
					if (pro.equals(servicioPropiedad.buscarPropiedadPor_predio_Y_catastro(predio, catastro))) {
						entra = Boolean.FALSE;
					}
				}
				if (entra) {
					listaPropiedades.add(servicioPropiedad.buscarPropiedadPor_predio_Y_catastro(predio, catastro));
					listaPropiedadesSeleccionadas.add(servicioPropiedad.buscarPropiedadPor_predio_Y_catastro(predio, catastro));
				} else {
					JsfUtil.addErrorMessage("Propiedad Ya Se Encuentra Añadida");
				}
			} else {
				JsfUtil.addErrorMessage("Propiedad No Encontrada");
			}
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	public void refresh() {
		acta = new Acta();
		listaPropiedades = new ArrayList<>();
		listaPropiedadesSeleccionadas = new ArrayList<>();
		predio = "";
		catastro = "";
		setBoolVerNuevaProp(Boolean.FALSE);
		setBoolOcultartabs(Boolean.FALSE);
	}

	public void obtenerJsonLatLong() {
		JSONObject json = new JSONObject(url);

		JSONArray jsonArray = json.getJSONArray("address_components");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject objectJson = (JSONObject) jsonArray.get(i);
			String lat = objectJson.get("lat").toString();
			String lon = objectJson.get("lng").toString();
			System.out.print("Lat:" + lat + ",Long=" + lon);
		}
	}

	public void armarDireccionCoordenadas() {
		setUrl("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + centerGeoMap + "&key=AIzaSyC7R9us6s7z77X-uy5zaAc6DzUM6u0-7c4");
	}

	public void armarDireccionConNombre() {
		setUrl("https://maps.googleapis.com/maps/api/geocode/json?address=" + direccion + "&key=AIzaSyC7R9us6s7z77X-uy5zaAc6DzUM6u0-7c4");
		obtenerJsonLatLong();
	}

	public void verificarPosicion() throws IOException {
		if (!propiedadNueva.getPrdTdtParNombreH().isEmpty()) {
			localizarPorParroquia();
		}
		if (propiedadNueva.getPrdEspacial().equals("") || propiedadNueva.getPrdEspacial().equals("0")) {

		} else {
			setGeoModel(new DefaultMapModel());
			LatLng latlng = new LatLng(Double.parseDouble(propiedadNueva.getPrdLatitud()), Double.parseDouble(propiedadNueva.getPrdLongitud()));
			Marker point = new Marker(latlng);
			geoModel.addOverlay(point);
			setCenterGeoMap(latlng.getLat() + "," + latlng.getLng());
			setZoom(17);
		}
	}

	public void onZoomAction(StateChangeEvent event) {
		setZoom(event.getZoomLevel());
	}

	public void onPointSelect(PointSelectEvent event) {
		setGeoModel(new DefaultMapModel());
		LatLng latlng = event.getLatLng();
		Marker point = new Marker(latlng);
		geoModel.addOverlay(point);
		setCenterGeoMap(latlng.getLat() + "," + latlng.getLng());
		setLat(latlng.getLat());
		setLng(latlng.getLng());
	}

	public void localizarPorParroquia() throws IOException {
		try {
			char espacio = ' ';
			char reemplazo = '%';
			String replace = propiedadNueva.getPrdTdtParNombreH().replace(espacio, reemplazo);

			setUrl("https://maps.googleapis.com/maps/api/geocode/json?address=Ecuador," + replace + "&key=AIzaSyC7R9us6s7z77X-uy5zaAc6DzUM6u0-7c4");
			URL dir = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) dir.openConnection();

			int responseCode = conn.getResponseCode();
			System.out.print(responseCode);
			if (responseCode == 0) {

			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				JSONObject json1 = new JSONObject(response.toString());
				JSONArray jsonArray = json1.getJSONArray("results");
				JSONObject json2 = jsonArray.getJSONObject(0);
				JSONObject json3 = json2.getJSONObject("geometry");
				JSONObject jsonLocation = json3.getJSONObject("location");
				setCenterGeoMap(jsonLocation.getDouble("lat") + "," + jsonLocation.getDouble("lng"));
				setZoom(15);
				System.out.print(jsonLocation);
			}

		} catch (MalformedURLException | JSONException e) {
			JsfUtil.addErrorMessage("Error con JSON");
		}

	}

	public void localizarPorDireccion() throws IOException {
		try {
			String reemplazo = "%20";
			String replace = direccion.replaceAll("\\s", reemplazo);

			setUrl("https://maps.googleapis.com/maps/api/geocode/json?address=" + replace + ",Ecuador&key=AIzaSyC7R9us6s7z77X-uy5zaAc6DzUM6u0-7c4");
			URL dir = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) dir.openConnection();

			int responseCode = conn.getResponseCode();
			System.out.print(responseCode);
			if (responseCode == 400) {
				JsfUtil.addErrorMessage("Url inválido");
			} else {
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				StringBuffer response = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
					response.append(inputLine);
				}
				in.close();

				JSONObject json1 = new JSONObject(response.toString());
				JSONArray jsonArray = json1.getJSONArray("results");
				JSONObject json2 = jsonArray.getJSONObject(0);
				JSONObject json3 = json2.getJSONObject("geometry");
				JSONObject jsonLocation = json3.getJSONObject("location");
				setCenterGeoMap(jsonLocation.getDouble("lat") + "," + jsonLocation.getDouble("lng"));
				setZoom(17);

				geoModel = new DefaultMapModel();
				LatLng latlng = new LatLng(jsonLocation.getDouble("lat"), jsonLocation.getDouble("lng"));
				Marker point = new Marker(latlng);
				geoModel.addOverlay(point);
				setLat(jsonLocation.getDouble("lat"));
				setLng(jsonLocation.getDouble("lng"));
				direccion = "";
				System.out.print(jsonLocation);
			}

		} catch (MalformedURLException | JSONException e) {
			JsfUtil.addWarningMessage("Dirección no encontrada");
		}

	}

	public void guadarLocalizacion() {
		getPropiedadNueva().setPrdEspacial(lat + "," + lng);
		getPropiedadNueva().setPrdLatitud(String.valueOf(lat));
		getPropiedadNueva().setPrdLongitud(String.valueOf(lng));
	}

}
