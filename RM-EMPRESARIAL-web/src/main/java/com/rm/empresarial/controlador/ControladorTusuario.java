/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.controlador;

import com.rm.empresarial.bb.TramitesControladorBb;
import static com.rm.empresarial.controlador.util.JsfUtil.addErrorMessage;
import static com.rm.empresarial.controlador.util.JsfUtil.addSuccessMessage;
import com.rm.empresarial.controlador.util.TramiteUtil;
import com.rm.empresarial.dao.CargaLaboralDao;
import com.rm.empresarial.dao.CertificadoAccionDao;
import com.rm.empresarial.dao.CertificadoCargaDao;
import com.rm.empresarial.dao.RepertorioUsuarioDao;
import com.rm.empresarial.dao.TramiteUsuarioDao;
import com.rm.empresarial.dao.UsuarioDao;
import com.rm.empresarial.excepciones.ServicioExcepcion;
import com.rm.empresarial.modelo.CargaLaboral;
import com.rm.empresarial.modelo.CertificadoAccion;
import com.rm.empresarial.modelo.CertificadoCarga;
import com.rm.empresarial.modelo.Repertorio;
import com.rm.empresarial.modelo.RepertorioUsuario;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteUsuario;
import com.rm.empresarial.modelo.Usuario;
import com.rm.empresarial.servicio.CertificadoCargaServicio;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.Setter;

@Named("controladorTusuario")
@SessionScoped
public class ControladorTusuario implements Serializable {

    @Inject
    private TramiteUtil tramiteUtil;

    @EJB
    private TramiteUsuarioDao tramiteUsuarioDao;
    @EJB
    private CertificadoCargaDao certificadoDao;

    @EJB
    private UsuarioDao usuarioDao;

    @EJB
    private RepertorioUsuarioDao repertorioDao;

    @EJB
    private CargaLaboralDao cargaLaboralDao;

    @EJB
    private CertificadoCargaDao certificadoCargaDao;
    
    @EJB
    private CertificadoAccionDao certificadoAccionDao;

    @Getter
    @Setter
    TramiteUsuario tramiteSeleccionado;

    @Getter
    @Setter
    private int totalCarga;

    @Getter
    @Setter
    private CargaLaboral cargaLaboralSeleccionada;

    @Getter
    @Setter
    private CargaLaboral cargaLaboralAsignada;

    @Getter
    @Setter
    private List<TramiteUsuario> lista = new ArrayList<>();

    @Getter
    @Setter
    private List<Usuario> listaUsuario = new ArrayList<>();

    @Getter
    @Setter
    private List<RepertorioUsuario> listaRepertorio = new ArrayList<>();

    @Getter
    @Setter
    private List<RepertorioUsuario> listaRepertorioINS = new ArrayList<>();

    @Getter
    @Setter
    private List<CertificadoCarga> listaCER = new ArrayList<>();

    @Getter
    @Setter
    private String persona;

    @Inject
    @Getter
    @Setter
    private TramitesControladorBb tramitesControladorBb;

    @Getter
    @Setter
    CargaLaboral cargaLaboral;

    @Getter
    @Setter
    CertificadoCarga certificadoCarga;

    @Getter
    @Setter
    TramiteUsuario tramiteUsuario;
    @Getter
    @Setter
    RepertorioUsuario repertorioUsuario;

    @Getter
    @Setter
    private String tipo;

    @Getter
    @Setter
    Usuario usuario;

    @Inject
    cargaControlador cargaControlador;

    public ControladorTusuario() {
        System.out.println("com.rm.empresarial.controlador.ControladorRpu.<init>()");
        lista = new ArrayList<>();
        listaRepertorio = new ArrayList<>();

    }

    @PostConstruct
    public void postConstructor() {
        listar();

    }

    public void listar() {
        setLista(tramiteUsuarioDao.listarTramiteUsuario());
    }

    public void actualizarTramite() throws ServicioExcepcion {
        Long cantidad = new Long(0);
        if (tramiteUsuarioDao.actualizar(getTramiteUsuario().getTusId().longValue()) == 1) {
            getTramiteUsuario().setUsuId(getUsuario());
            tramiteUsuarioDao.create(tramiteUsuario);

            setLista(tramiteUsuarioDao.listarTramite(getUsuario().getUsuId()));
            setTotalCarga(getLista().size());

            setCargaLaboralAsignada(cargaLaboralDao.buscarPorUsuario(getUsuario()));
            cantidad = getCargaLaboralAsignada().getCarCantidad() + new Long(1);
            //getCargaLaboralAsignada().setCarAsignado((short) totalCarga);
            getCargaLaboralAsignada().setCarCantidad(cantidad);
            cargaLaboralDao.edit(cargaLaboralAsignada);

            setLista(tramiteUsuarioDao.listarTramite(cargaLaboralSeleccionada.getUsuId().getUsuId()));
            setTotalCarga(getLista().size());
            cantidad = getCargaLaboralSeleccionada().getCarCantidad() - new Long(1);
            if (cantidad < 0) {
                cantidad = new Long(0);
            }
            getCargaLaboralSeleccionada().setCarCantidad(cantidad);
            //getCargaLaboral().setCarAsignado((short) totalCarga);
            getCargaLaboral().setCarCantidad(cantidad);
            cargaLaboralDao.edit(getCargaLaboral());
            cargaControlador.listarCarga();

            addSuccessMessage("Se asigno Correctamente");
            //crear tramite accion
            Tramite tramite = new Tramite();
            tramite = getTramiteUsuario().getTraNumero();
            tramiteUtil.insertarTramiteAccion(tramite, tramite.getTraNumero().toString(),
                    "TRÁMITE: " + tramite.getTraNumero()
                    + " REASIGNADO A " + getUsuario().getUsuLogin(),
                    tramite.getTraEstado(), tramite.getTraEstadoRegistro(), getUsuario().getUsuLogin());

        } else {
            addErrorMessage("Error al asignar");
        }
    }

    public void listarTramiteRVT(String tipo) throws ServicioExcepcion {
        setTipo(tipo);
        setLista(tramiteUsuarioDao.listarTramite(getCargaLaboral().getUsuId().getUsuId()));
        setCargaLaboralSeleccionada(getCargaLaboral());

    }

    public void listarTramiteDIG(String tipo) throws ServicioExcepcion {
        setTipo(tipo);
        setListaRepertorio(repertorioDao.listarRepertorioDIG(getCargaLaboral().getUsuId().getUsuId()));
        setCargaLaboralSeleccionada(getCargaLaboral());
    }

    public void listarTramiteIMP(String tipo) throws ServicioExcepcion {
        setTipo(tipo);
        setListaRepertorio(repertorioDao.listarRepertorioIMP(getCargaLaboral().getUsuId().getUsuId()));
    }

    public void listarTramiteMRG(String tipo) throws ServicioExcepcion {
        setTipo(tipo);
        setListaRepertorio(repertorioDao.listarRepertorioMRG(getCargaLaboral().getUsuId().getUsuId()));
        setCargaLaboralSeleccionada(getCargaLaboral());
    }

    public void listarTramiteMAT(String tipo) throws ServicioExcepcion {
        setTipo(tipo);
        setListaRepertorio(repertorioDao.listarRepertorioMAT(getCargaLaboral().getUsuId().getUsuId()));
    }

    public void listarTramiteINS(String tipo) throws ServicioExcepcion {
        setTipo(tipo);
        setListaRepertorio(repertorioDao.listarRepertorioINS(getCargaLaboral().getUsuId().getUsuId()));
        setCargaLaboralSeleccionada(getCargaLaboral());
    }

    public void listarTramiteRAZ(String tipo) throws ServicioExcepcion {
        setTipo(tipo);
        setListaRepertorio(repertorioDao.listarRepertorioRAZ(getCargaLaboral().getUsuId().getUsuId()));
    }

    public void listarCER(String tipo) throws ServicioExcepcion {
        setTipo(tipo);
        setListaCER(certificadoDao.listarCER(getCargaLaboral().getUsuId().getUsuId()));
        setCargaLaboralSeleccionada(getCargaLaboral());
    }

    public void listarDFC(String tipo) throws ServicioExcepcion {
        setTipo(tipo);
        setListaCER(certificadoDao.listarDFC(getCargaLaboral().getUsuId().getUsuId()));
        setCargaLaboralSeleccionada(getCargaLaboral());
    }

    public void listarUsuarioRvt() throws ServicioExcepcion {
        setListaUsuario(usuarioDao.listarUsuarioRolRvt(getTramiteUsuario().getUsuId().getUsuId()));
    }

    public void listarUsuarioMAT() throws ServicioExcepcion {
        setListaUsuario(usuarioDao.listarUsuarioRolMAT(getCargaLaboral().getUsuId().getUsuId()));
    }

    public void listarUsuarioINS() throws ServicioExcepcion {
        setListaUsuario(usuarioDao.listarUsuarioRolINS(getCargaLaboral().getUsuId().getUsuId()));
    }

    public void listarUsuarioIMP() throws ServicioExcepcion {
        setListaUsuario(usuarioDao.listarUsuarioRolIMP(getCargaLaboral().getUsuId().getUsuId()));
    }

    public void listarUsuarioMRG() throws ServicioExcepcion {
        setListaUsuario(usuarioDao.listarUsuarioRolMRG(getCargaLaboral().getUsuId().getUsuId()));
    }

    public void listarUsuarioDIG() throws ServicioExcepcion {
        setListaUsuario(usuarioDao.listarUsuarioRolDIG(getCargaLaboral().getUsuId().getUsuId()));
    }

    public void listarUsuarioCER() throws ServicioExcepcion {
        setListaUsuario(usuarioDao.listarUsuarioRolCER(getCargaLaboral().getUsuId().getUsuId()));
    }

    public void actualizarRepertorio() throws ServicioExcepcion {
        Long cantidad = new Long(0);
        if (repertorioDao.actualizar(getRepertorioUsuario().getRpuId()) == 1) {
            getRepertorioUsuario().setUsuId(getUsuario());
            repertorioDao.create(repertorioUsuario);
            switch (getRepertorioUsuario().getRpuTipo()) {
                case "INS":
                    setCargaLaboralAsignada(cargaLaboralDao.buscarPorUsuario(getUsuario()));
                    setListaRepertorio(repertorioDao.listarRepertorioINS(getUsuario().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralAsignada().getCarCantidad() + new Long(1);
                    getCargaLaboralAsignada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralAsignada);

                    setListaRepertorio(repertorioDao.listarRepertorioINS(cargaLaboralSeleccionada.getUsuId().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralSeleccionada().getCarCantidad() - new Long(1);
                    if (cantidad < 0) {
                        cantidad = new Long(0);
                    }
                    getCargaLaboralSeleccionada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralSeleccionada);
                    cargaControlador.listarCarga();
                    addSuccessMessage("Se asigno Correctamente");
                    //crear tramite accion
                    crearTramiteAccionActualizacionRepertorio(getRepertorioUsuario().getRpuTipo());
                    break;
                case "MRG":
                    setCargaLaboralAsignada(cargaLaboralDao.buscarPorUsuario(getUsuario()));
                    setListaRepertorio(repertorioDao.listarRepertorioMRG(getUsuario().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralAsignada().getCarCantidad() + new Long(1);
                    getCargaLaboralAsignada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralAsignada);

                    setListaRepertorio(repertorioDao.listarRepertorioMRG(cargaLaboralSeleccionada.getUsuId().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralSeleccionada().getCarCantidad() - new Long(1);
                    if (cantidad < 0) {
                        cantidad = new Long(0);
                    }
                    getCargaLaboralSeleccionada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralSeleccionada);
                    cargaControlador.listarCarga();
                    addSuccessMessage("Se asigno Correctamente");
                    //crear tramite accion
                    crearTramiteAccionActualizacionRepertorio(getRepertorioUsuario().getRpuTipo());
                    break;
                case "DIG":
                    setCargaLaboralAsignada(cargaLaboralDao.buscarPorUsuario(getUsuario()));
                    setListaRepertorio(repertorioDao.listarRepertorioDIG(getUsuario().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralAsignada().getCarCantidad() + new Long(1);
                    getCargaLaboralAsignada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralAsignada);

                    setListaRepertorio(repertorioDao.listarRepertorioDIG(cargaLaboralSeleccionada.getUsuId().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralSeleccionada().getCarCantidad() - new Long(1);
                    if (cantidad < 0) {
                        cantidad = new Long(0);
                    }
                    getCargaLaboralSeleccionada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralSeleccionada);
                    cargaControlador.listarCarga();
                    addSuccessMessage("Se asigno Correctamente");
                    //crear tramite accion
                    crearTramiteAccionActualizacionRepertorio(getRepertorioUsuario().getRpuTipo());
                    break;
                case "IMP":
                    setCargaLaboralAsignada(cargaLaboralDao.buscarPorUsuario(getUsuario()));
                    setListaRepertorio(repertorioDao.listarRepertorioIMP(getUsuario().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralAsignada().getCarCantidad() + new Long(1);
                    getCargaLaboralAsignada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralAsignada);

                    setListaRepertorio(repertorioDao.listarRepertorioIMP(cargaLaboralSeleccionada.getUsuId().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralSeleccionada().getCarCantidad() - new Long(1);
                    if (cantidad < 0) {
                        cantidad = new Long(0);
                    }
                    getCargaLaboralSeleccionada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralSeleccionada);
                    cargaControlador.listarCarga();
                    addSuccessMessage("Se asigno Correctamente");
                    //crear tramite accion
                    crearTramiteAccionActualizacionRepertorio(getRepertorioUsuario().getRpuTipo());
                    break;
                case "MAT":
                    setCargaLaboralAsignada(cargaLaboralDao.buscarPorUsuario(getUsuario()));
                    setListaRepertorio(repertorioDao.listarRepertorioMAT(getUsuario().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralAsignada().getCarCantidad() + new Long(1);
                    getCargaLaboralAsignada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralAsignada);

                    setListaRepertorio(repertorioDao.listarRepertorioMAT(cargaLaboralSeleccionada.getUsuId().getUsuId()));
                    setTotalCarga(getListaRepertorio().size());
                    cantidad = getCargaLaboralSeleccionada().getCarCantidad() - new Long(1);
                    if (cantidad < 0) {
                        cantidad = new Long(0);
                    }
                    getCargaLaboralSeleccionada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralSeleccionada);
                    cargaControlador.listarCarga();
                    addSuccessMessage("Se asigno Correctamente");
                    //crear tramite accion
                    crearTramiteAccionActualizacionRepertorio(getRepertorioUsuario().getRpuTipo());
                    break;

            }
        } else {
            addErrorMessage("Error al asignar");
        }
    }

    public void crearTramiteAccionActualizacionRepertorio(String tipoEstado) {
        Tramite tramite = new Tramite();
        tramite = getRepertorioUsuario().getRepNumero().getTraNumero();
        Repertorio repertorio = new Repertorio();
        repertorio = getRepertorioUsuario().getRepNumero();
        tramiteUtil.insertarTramiteAccion(tramite, repertorio.getRepNumero().toString(),
                "REPERTORIO: " + repertorio.getRepNumero()
                + " REASIGNADO A " + getUsuario().getUsuLogin(),
                tipoEstado, tramite.getTraEstadoRegistro(), getUsuario().getUsuLogin());
    }

    public void crearTramiteAccionActualizacionCertificado(String tipoEstado) {
        CertificadoAccion certificadoAccion = new CertificadoAccion();
        certificadoAccion.setCtaNumeroDocumento(getCertificadoCarga().getCdcDocumento());
        certificadoAccion.setCtaEstadoProceso(tipoEstado);
        certificadoAccion.setCtaEstadoRegistro("A");
        certificadoAccion.setCtaObservacion("CERTIFICADO: " + getCertificadoCarga().getCdcDocumento()
                + " REASIGNADO A " + getUsuario().getUsuLogin());
        certificadoAccion.setCtaFHR(Calendar.getInstance().getTime());
        certificadoAccion.setCtaUsuarioAsignado(getUsuario().getUsuLogin());
        certificadoAccionDao.create(certificadoAccion);
    }

    public void actualizarCertificado() throws ServicioExcepcion {
        Long cantidad = new Long(0);
        if (certificadoDao.actualizar(getCertificadoCarga().getCdcId()) == 1) {
            getCertificadoCarga().setUsuId(getUsuario());
            certificadoDao.create(certificadoCarga);

            switch (getCertificadoCarga().getCdcTipo()) {
                case "DFC":
                    setCargaLaboralAsignada(cargaLaboralDao.buscarPorUsuario(getUsuario()));
                    setListaCER(certificadoDao.listarDFC(getUsuario().getUsuId()));
                    setTotalCarga(getListaCER().size());
                    //getCargaLaboralAsignada().setCarAsignado((short) totalCarga);                    
                    cantidad = getCargaLaboralAsignada().getCarCantidad() + new Long(1);
                    getCargaLaboralAsignada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralAsignada);

                    setListaCER(certificadoDao.listarDFC(cargaLaboralSeleccionada.getUsuId().getUsuId()));
                    setTotalCarga(getListaCER().size());
                    cantidad = getCargaLaboralSeleccionada().getCarCantidad() - new Long(1);
                    if (cantidad < 0) {
                        cantidad = new Long(0);
                    }
                    getCargaLaboralSeleccionada().setCarCantidad(cantidad);
                    //getCargaLaboralSeleccionada().setCarAsignado((short) totalCarga);
                    getCargaLaboralSeleccionada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralSeleccionada);
                    cargaControlador.listarCarga();

                    addSuccessMessage("Se asigno Correctamente");
                    crearTramiteAccionActualizacionCertificado(getCertificadoCarga().getCdcTipo());
                    break;
                case "CER":
                    setCargaLaboralAsignada(cargaLaboralDao.buscarPorUsuario(getUsuario()));
                    setListaCER(certificadoDao.listarCER(getUsuario().getUsuId()));
                    setTotalCarga(getListaCER().size());
                    cantidad = getCargaLaboralAsignada().getCarCantidad() + new Long(1);
                    //getCargaLaboralAsignada().setCarAsignado((short) totalCarga);
                    getCargaLaboralAsignada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralAsignada);

                    setListaCER(certificadoDao.listarCER(cargaLaboralSeleccionada.getUsuId().getUsuId()));
                    setTotalCarga(getListaCER().size());
                    cantidad = getCargaLaboralSeleccionada().getCarCantidad() - new Long(1);
                    //getCargaLaboralSeleccionada().setCarAsignado((short) totalCarga);
                    getCargaLaboralSeleccionada().setCarCantidad(cantidad);
                    cargaLaboralDao.edit(cargaLaboralSeleccionada);
                    cargaControlador.listarCarga();
                    addSuccessMessage("Se asigno Correctamente");
                    crearTramiteAccionActualizacionCertificado(getCertificadoCarga().getCdcTipo());
                    break;
            }
        } else {
            addErrorMessage("Error al asignar");
        }
    }
}
