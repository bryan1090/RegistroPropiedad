/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.bb;

import com.rm.empresarial.modelo.Caja;
import com.rm.empresarial.modelo.ConfigDetalle;
import com.rm.empresarial.modelo.UsuarioCaja;
import com.rm.empresarial.modelo.Factura;
import com.rm.empresarial.modelo.FacturaDetalle;
import com.rm.empresarial.modelo.FacturaDetalleAdicional;
import com.rm.empresarial.modelo.FacturaDetalleImpuesto;
import com.rm.empresarial.modelo.FacturaFormaPago;
import com.rm.empresarial.modelo.FacturaInfoAdicional;
import com.rm.empresarial.modelo.Institucion;
import com.rm.empresarial.modelo.Persona;
import com.rm.empresarial.modelo.RepositorioSRI;
import com.rm.empresarial.modelo.TipoFormaPago;
import com.rm.empresarial.modelo.Tramite;
import com.rm.empresarial.modelo.TramiteValor;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.Dependent;
import javax.faces.model.SelectItem;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Wilson Herrera
 */
@Dependent
public class ControladorCajasRecBb implements Serializable {

    @Getter
    @Setter
    private Integer numeroFactura;

    @Getter
    @Setter
    private Short FacNumeroDerecho;

    @Getter
    @Setter
    private Short FacCertificado;

    @Getter
    @Setter
    private Tramite tramite;

    @Getter
    @Setter
    private String numeroTramite;

    @Getter
    @Setter
    private String identificacion;

    @Getter
    @Setter
    private Persona persona;

    @Getter
    @Setter
    private String nombrePersona;

    @Getter
    @Setter
    private RepositorioSRI repositorioSRI;

    @Getter
    @Setter
    private String observacion;

    @Getter
    @Setter
    private List<TramiteValor> listaTramiteValor;

    @Getter
    @Setter
    private Factura factura;

    @Getter
    @Setter
    private FacturaDetalle facturaDetalle;

    @Getter
    @Setter
    private List<FacturaDetalle> listaFacturaDetalle;

    @Getter
    @Setter
    private Boolean estado = Boolean.FALSE;

    @Getter
    @Setter
    private Boolean estadoImprimir = Boolean.TRUE;

    @Getter
    @Setter
    private String fechaIngreso;

    @Getter
    @Setter
    private String fechaEntrega;

    @Getter
    @Setter
    private String numeroComprobante;

    @Getter
    @Setter
    private BigDecimal iva0 = BigDecimal.ZERO;

    @Getter
    @Setter
    private FacturaDetalleAdicional facturaDetalleAdicional;

    @Getter
    @Setter
    private FacturaDetalleImpuesto facturaDetalleImpuesto;

    @Getter
    @Setter
    private Institucion institucion;

    @Getter
    @Setter
    private FacturaInfoAdicional facturaInfoAdicional;

    @Getter
    @Setter
    private Caja caja;

    @Getter
    @Setter
    private List<FacturaDetalleAdicional> listaFacturaDetalleAdicional;

    @Getter
    @Setter
    private List<FacturaDetalleImpuesto> listaFacturaDetalleImpuesto;

    @Getter
    @Setter
    private Boolean agregado = Boolean.FALSE;

    @Getter
    @Setter
    private UsuarioCaja usuarioCaja;

    @Getter
    @Setter
    private Persona nuevaPersona;

    @Getter
    @Setter
    private Boolean facturado = Boolean.FALSE;

    @Getter
    @Setter
    private String idPago;

    @Getter
    @Setter
    private List<SelectItem> selectPago;

    @Getter
    @Setter
    private List<FacturaFormaPago> listapagos;

    @Getter
    @Setter
    private TipoFormaPago tipoFormaPago;

    @Getter
    @Setter
    private BigDecimal totalPago = BigDecimal.ZERO;

    @Getter
    @Setter
    private Boolean btnPago = Boolean.TRUE;

    @Getter
    @Setter
    private BigDecimal saldoPago = BigDecimal.ZERO;
    
    @Getter
    @Setter
    private BigDecimal valorPago = BigDecimal.ZERO;
    
    @Getter
    @Setter
    private BigDecimal auxSaldoPago = BigDecimal.ZERO;
    
    @Getter
    @Setter
    private BigDecimal auxValorPago = BigDecimal.ZERO;

    @Getter
    @Setter
    private Boolean esNotaVenta = Boolean.FALSE;

    @Getter
    @Setter
    private FacturaFormaPago pagoSeleccionado;

    @Getter
    @Setter
    private Boolean facWeb;

    @Getter
    @Setter
    private String facCertificadoNombre;

    @Getter
    @Setter
    private String facCertificadoPrimerApe;

    @Getter
    @Setter
    private String facCertificadoSegundoApe;

    @Getter
    @Setter
    private String numeroPredio;

    @Getter
    @Setter
    private String numeroCatastro;

    @Getter
    @Setter
    private Boolean bloquearAccion = Boolean.FALSE;

    @Getter
    @Setter
    private Integer FacFormaAdquisicion = 1;
    
    @Getter
    @Setter
    private Boolean bloquearPredioCatastro = Boolean.FALSE;
    
    @Getter
    @Setter
    private ConfigDetalle configDetalleFactura;
    
    @Getter
    @Setter
    private FacturaDetalle facturaDetalleIndividual;
    
    @Getter
    @Setter
    private Boolean bolPosesionEfectiva;

    public void inicializar() {
        setTramite(new Tramite());
        setPersona(new Persona());
        setRepositorioSRI(new RepositorioSRI());
        setListaTramiteValor(new ArrayList<TramiteValor>());
        setFactura(new Factura());
        setFacturaDetalle(new FacturaDetalle());
        setListaFacturaDetalle(new ArrayList<FacturaDetalle>());
        setFacturaDetalleAdicional(new FacturaDetalleAdicional());
        setFacturaDetalleImpuesto(new FacturaDetalleImpuesto());
        setInstitucion(new Institucion());
        setFacturaInfoAdicional(new FacturaInfoAdicional());
        setCaja(new Caja());
        setListaFacturaDetalleAdicional(new ArrayList<FacturaDetalleAdicional>());
        setListaFacturaDetalleImpuesto(new ArrayList<FacturaDetalleImpuesto>());
        setNuevaPersona(new Persona());
        setListapagos(new ArrayList<FacturaFormaPago>());
        setTipoFormaPago(new TipoFormaPago());
        setConfigDetalleFactura(new ConfigDetalle());
        setFacturaDetalleIndividual(new FacturaDetalle());
        setBolPosesionEfectiva(false);
    }
}
