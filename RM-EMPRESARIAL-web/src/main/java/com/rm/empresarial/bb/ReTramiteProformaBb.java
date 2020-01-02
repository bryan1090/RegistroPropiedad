package com.rm.empresarial.bb;

import com.rm.empresarial.controlador.ControladorCompareciente;
import com.rm.empresarial.modelo.Cuantia;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Notaria;
import com.rm.empresarial.modelo.Parroquia;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.Propiedad;
import com.rm.empresarial.modelo.RepositorioSRI;
import com.rm.empresarial.modelo.TipoTramite;
import com.rm.empresarial.modelo.TipoTramiteCompareciente;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteDetalle;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.TramiteValor;
import com.rm.empresarial.modelo.Usuario;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

/**
 *
 * @author Dany
 */
@Named(value = "reTramiteProformaBb")
@Dependent
public class ReTramiteProformaBb implements Serializable {

    private static final long serialVersionUID = -3601851846304997183L;

    @Getter
    @Setter
    private List<Tramite> listaTramite;

    @Getter
    @Setter
    private Tramite tramite;

    @Getter
    @Setter
    private Tramite tramiteSeleccionado;

    @Getter
    @Setter
    private List<TramiteUsuario> listaTramiteUsuario;

    @Getter
    @Setter
    private TramiteUsuario tramiteUsuario;

    @Getter
    @Setter
    private TramiteUsuario tramiteUsuarioSeleccionado;

    @Getter
    @Setter
    private List<TramiteUsuario> listaTramiteUsuaFiltrada;

    @Getter
    @Setter
    private Usuario usuario;

    @Getter
    @Setter
    private List<SelectItem> notarias;

    @Getter
    @Setter
    private Boolean estado = Boolean.FALSE;

    @Getter
    @Setter
    private List<Notaria> listaNotaria;

    @Getter
    @Setter
    private String valorNotaria;

    @Getter
    @Setter
    private Long traNumero;

    @Getter
    @Setter
    private String Contrato;

    @Getter
    @Setter
    private Date fechaIni;

    @Getter
    @Setter
    private Date fechaFin;

    @Getter
    @Setter
    private List<Parroquia> listaParroquia;

    @Getter
    @Setter
    private List<Parroquia> parroquias;

    @Getter
    @Setter
    private String valorParroquia;

    @Getter
    @Setter
    private String numero;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private RepositorioSRI repositorioSRI;

    @Getter
    @Setter
    private String nombrePersona;

    @Getter
    @Setter
    private List<SelectItem> tipoTramite;

    @Getter
    @Setter
    private String valorTipoTramite;

    @Getter
    @Setter
    private List<SelectItem> listaTipoTramiteCompareciente;

    @Getter
    @Setter
    private String valorTramiteComp;

    @Getter
    @Setter
    private TipoTramite tipoTramiteOb;

    @Getter
    @Setter
    private List<ControladorCompareciente> listacomparecientes;
    @Getter
    @Setter
    private List<ControladorCompareciente> listacomparecientesDetalle;

    @Inject
    @Getter
    @Setter
    private ControladorCompareciente controladorCompareciente;

    @Getter
    @Setter
    private TipoTramiteCompareciente tipoTramiteCompareciente;

    @Getter
    @Setter
    private Integer contratos;

    @Getter
    @Setter
    private ControladorCompareciente comparecienteSeleccionado;

    @Getter
    @Setter
    private Persona personaConyugue;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalle;

    @Getter
    @Setter
    private BigDecimal valorCuantia;

    @Getter
    @Setter
    private Cuantia cuantia;

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramitesDetalle;

    @Getter
    @Setter
    private List<TramiteDetalle> listaTramiteModelo;

    @Getter
    @Setter
    private Boolean filtro = Boolean.FALSE;

    @Getter
    @Setter
    private TramiteDetalle tramiteDetalleSeleccionado;

    @Getter
    @Setter
    private Boolean copia = Boolean.FALSE;

    @Getter
    @Setter
    private List<Cuantia> listaCuantia;

    @Getter
    @Setter
    private BigDecimal cuantiaValor;

    @Getter
    @Setter
    private Institucion institucion;

    @Getter
    @Setter
    private Boolean modelo = Boolean.FALSE;

    @Getter
    @Setter
    private List<SelectItem> listaTipoTramite;

    @Getter
    @Setter
    private String valorTipTramite;

    @Getter
    @Setter
    private TramiteValor tramiteValorSeleccionado;

    public ReTramiteProformaBb() {
        setFechaIni(Calendar.getInstance().getTime());
        setFechaFin(Calendar.getInstance().getTime());
    }

    public void inicializar() {
        setListaTramite(new ArrayList<Tramite>());
        setTramite(new Tramite());
        setTramiteSeleccionado(new Tramite());
        setTramiteUsuario(new TramiteUsuario());
        setTramiteUsuarioSeleccionado(new TramiteUsuario());
        setListaTramiteUsuario(new ArrayList<TramiteUsuario>());
        setListaTramiteUsuaFiltrada(new ArrayList<TramiteUsuario>());
        setUsuario(new Usuario());
        setListaNotaria(new ArrayList<Notaria>());
        setListaParroquia(new ArrayList<Parroquia>());
        setPersona(new Persona());
        setRepositorioSRI(new RepositorioSRI());
        setTipoTramiteOb(new TipoTramite());
        setListacomparecientes(new ArrayList<ControladorCompareciente>());
        setListacomparecientesDetalle(new ArrayList<ControladorCompareciente>());
        setControladorCompareciente(new ControladorCompareciente());
        setTipoTramiteCompareciente(new TipoTramiteCompareciente());
        setComparecienteSeleccionado(new ControladorCompareciente());
        setPersonaConyugue(new Persona());
        setTramiteDetalle(new TramiteDetalle());
        setCuantia(new Cuantia());
        setListaTramitesDetalle(new ArrayList<TramiteDetalle>());
        setListaTramiteModelo(new ArrayList<TramiteDetalle>());
        setTramiteDetalleSeleccionado(new TramiteDetalle());
        setListaCuantia(new ArrayList<Cuantia>());
        setInstitucion(new Institucion());
        setTramiteValorSeleccionado(new TramiteValor());

    }

}
