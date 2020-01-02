/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Factura")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f")
    , @NamedQuery(name = "Factura.findByFacId", query = "SELECT f FROM Factura f WHERE f.facId = :facId")
    , @NamedQuery(name = "Factura.findByFacNumero", query = "SELECT f FROM Factura f WHERE f.facNumero = :facNumero")
    , @NamedQuery(name = "Factura.findByFacTraNumero", query = "SELECT f FROM Factura f WHERE f.facTraNumero = :facTraNumero")
    , @NamedQuery(name = "Factura.findByFacPerTipoContribuyente", query = "SELECT f FROM Factura f WHERE f.facPerTipoContribuyente = :facPerTipoContribuyente")
    , @NamedQuery(name = "Factura.findByFacPerTipoIdentificacion", query = "SELECT f FROM Factura f WHERE f.facPerTipoIdentificacion = :facPerTipoIdentificacion")
    , @NamedQuery(name = "Factura.findByFacPerIdentificacion", query = "SELECT f FROM Factura f WHERE f.facPerIdentificacion = :facPerIdentificacion")
    , @NamedQuery(name = "Factura.findByFacPerNombre", query = "SELECT f FROM Factura f WHERE f.facPerNombre = :facPerNombre")
    , @NamedQuery(name = "Factura.findByFacPerApellidoPaterno", query = "SELECT f FROM Factura f WHERE f.facPerApellidoPaterno = :facPerApellidoPaterno")
    , @NamedQuery(name = "Factura.findByFacPerApellidoMaterno", query = "SELECT f FROM Factura f WHERE f.facPerApellidoMaterno = :facPerApellidoMaterno")
    , @NamedQuery(name = "Factura.findByFacPerTelefono", query = "SELECT f FROM Factura f WHERE f.facPerTelefono = :facPerTelefono")
    , @NamedQuery(name = "Factura.findByFacPerCelular", query = "SELECT f FROM Factura f WHERE f.facPerCelular = :facPerCelular")
    , @NamedQuery(name = "Factura.findByFacPerEmail", query = "SELECT f FROM Factura f WHERE f.facPerEmail = :facPerEmail")
    , @NamedQuery(name = "Factura.findByFacPerDireccion", query = "SELECT f FROM Factura f WHERE f.facPerDireccion = :facPerDireccion")
    , @NamedQuery(name = "Factura.findByFacNumeroDerecho", query = "SELECT f FROM Factura f WHERE f.facNumeroDerecho = :facNumeroDerecho")
    , @NamedQuery(name = "Factura.findByFacCertificado", query = "SELECT f FROM Factura f WHERE f.facCertificado = :facCertificado")
    , @NamedQuery(name = "Factura.findByFacObservacion", query = "SELECT f FROM Factura f WHERE f.facObservacion = :facObservacion")
    , @NamedQuery(name = "Factura.findByFacFechaEntrega", query = "SELECT f FROM Factura f WHERE f.facFechaEntrega = :facFechaEntrega")
    , @NamedQuery(name = "Factura.findByFacNumeroEscritura", query = "SELECT f FROM Factura f WHERE f.facNumeroEscritura = :facNumeroEscritura")
    , @NamedQuery(name = "Factura.findByFacRegistrador", query = "SELECT f FROM Factura f WHERE f.facRegistrador = :facRegistrador")
    , @NamedQuery(name = "Factura.findByFacAmanuence", query = "SELECT f FROM Factura f WHERE f.facAmanuence = :facAmanuence")
    , @NamedQuery(name = "Factura.findByFacMaterial", query = "SELECT f FROM Factura f WHERE f.facMaterial = :facMaterial")
    , @NamedQuery(name = "Factura.findByFacSubTotal", query = "SELECT f FROM Factura f WHERE f.facSubTotal = :facSubTotal")
    , @NamedQuery(name = "Factura.findByFacDescuento", query = "SELECT f FROM Factura f WHERE f.facDescuento = :facDescuento")
    , @NamedQuery(name = "Factura.findByFacBaseIva", query = "SELECT f FROM Factura f WHERE f.facBaseIva = :facBaseIva")
    , @NamedQuery(name = "Factura.findByFacIva", query = "SELECT f FROM Factura f WHERE f.facIva = :facIva")
    , @NamedQuery(name = "Factura.findByFacTotal", query = "SELECT f FROM Factura f WHERE f.facTotal = :facTotal")
    , @NamedQuery(name = "Factura.findByFacEstado", query = "SELECT f FROM Factura f WHERE f.facEstado = :facEstado")
    , @NamedQuery(name = "Factura.findByFacUser", query = "SELECT f FROM Factura f WHERE f.facUser = :facUser")
    , @NamedQuery(name = "Factura.findByFacFHR", query = "SELECT f FROM Factura f WHERE f.facFHR = :facFHR")
    , @NamedQuery(name = "Factura.findByFacFechaTramite", query = "SELECT f FROM Factura f WHERE f.facFechaTramite = :facFechaTramite")
    , @NamedQuery(name = "Factura.buscarPorFechaFactura", query = "SELECT f FROM Factura f WHERE f.facFecha >= :facFechaInicio AND f.facFecha <= :facFechaFin ")
    , @NamedQuery(name = "Factura.findByFacEstadoCertificado", query = "SELECT f FROM Factura f WHERE f.facEstadoCertificado = :facEstadoCertificado")
    , @NamedQuery(name = "Factura.buscarPorClaveAcceso", query = "SELECT f FROM Factura f WHERE f.facClaveAcceso is NULL AND f.facTipo='FAC'")})
public class Factura implements Serializable {

    @Column(name = "FacTraNumero")
    private Integer facTraNumero;
    @Column(name = "FacNumeroDerecho")
    private Short facNumeroDerecho;
    @Column(name = "FacCertificado")
    private Short facCertificado;
    @Column(name = "FacNumeroEscritura")
    private Short facNumeroEscritura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacFecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date facFecha;
    @Column(name = "FacTroId")
    private BigInteger facTroId;
    @Column(name = "FacTroNombre")
    private String facTroNombre;
    @Getter
    @Setter
    @Column(name = "FacPosecionEfectiva")
    private Boolean facPosesionEfectiva;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facId")
    private Collection<FacturaFormaPago> facturaFormaPagoCollection;

    private static final long serialVersionUID = 1L;
    public static final String ENCONTRAR_POR_NUMERO_TRAMITE = "Factura.findByFacTraNumero";
    public static final String BUSCAR_POR_NUMERO_FACTURA = "Factura.findByFacNumero";
    public static final String BUSCAR_POR_FECHA_FACTURA = "Factura.buscarPorFechaFactura";
    public static final String BUSCAR_POR_CLAVE_ACCESO_NULL="Factura.buscarPorClaveAcceso";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FacId", nullable = false)
    private BigDecimal facId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FacNumero")
    private String facNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FacPerTipoContribuyente")
    private String facPerTipoContribuyente;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FacPerTipoIdentificacion")
    private String facPerTipoIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FacPerIdentificacion")
    private String facPerIdentificacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FacPerNombre")
    private String facPerNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "FacPerApellidoPaterno")
    private String facPerApellidoPaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "FacPerApellidoMaterno")
    private String facPerApellidoMaterno;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FacPerTelefono")
    private String facPerTelefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FacPerCelular")
    private String facPerCelular;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FacPerEmail")
    private String facPerEmail;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "FacPerDireccion")
    private String facPerDireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "FacObservacion")
    private String facObservacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacFechaEntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date facFechaEntrega;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacRegistrador")
    private BigDecimal facRegistrador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacAmanuence")
    private BigDecimal facAmanuence;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacMaterial")
    private BigDecimal facMaterial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacSubTotal")
    private BigDecimal facSubTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacDescuento")
    private BigDecimal facDescuento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacBaseIva")
    private BigDecimal facBaseIva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacIva")
    private BigDecimal facIva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacTotal")
    private BigDecimal facTotal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "FacEstado")
    private String facEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FacUser")
    private String facUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date facFHR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FacFechaTramite")
    @Temporal(TemporalType.TIMESTAMP)
    private Date facFechaTramite;
    @Size(max = 3)
    @Column(name = "FacEstadoCertificado")
    private String facEstadoCertificado;
    @Column(name = "FacEstadoCierre")
    private String facEstadoCierre;
    @Getter
    @Setter
    @Size(max = 10)
    @Column(name = "FacCodigoVerificacion")
    private String facCodigoVerificacion;
    
      @Column(name = "FacFormaAdquisicion")
    private Integer facFormaAdquisicion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facId")
    private List<FacturaInfoAdicional> facturaInfoAdicionalList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facId")
    private List<FacturaDetalle> facturaDetalleList;
    @JoinColumn(name = "CajId", referencedColumnName = "CajId")
    @ManyToOne(optional = false)
    private Caja cajId;
    @JoinColumn(name = "PerId", referencedColumnName = "PerId")
    @ManyToOne(optional = false)
    private Persona perId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facId")
    private List<FacturaTrabajo> facturaTrabajoList;

    @Getter
    @Setter
    @Column(name = "FacXml")
    private String facXml;

    @Getter
    @Setter
    @Column(name = "FacClaveAcceso")
    private String facClaveAcceso;

    @Getter
    @Setter
    @Column(name = "FacRespuestaSri")
    private String facRespuestaSri;

    @Getter
    @Setter
    @Column(name = "FacTipo")
    private String facTipo;

    @Getter
    @Setter
    @Column(name = "FacWeb")
    private Boolean facWeb;
    
    @Column(name = "FacNumeroAcciones")
    private Integer facNumeroAcciones;

    @Getter
    @Setter
    @Column(name = "FacCertificadoNombre")
    private String facCertificadoNombre;

    @Getter
    @Setter
    @Column(name = "FacCertificadoPrimerApe")
    private String facCertificadoPrimerApe;

    @Getter
    @Setter
    @Column(name = "FacCertificadoSegundoApe")
    private String facCertificadoSegundoApe;
    
    

    public Factura() {
    }

    public Factura(BigDecimal facId) {
        this.facId = facId;
    }

    public Factura(BigDecimal facId, String facNumero, int facTraNumero, String facPerTipoContribuyente, String facPerTipoIdentificacion, String facPerIdentificacion, String facPerNombre, String facPerApellidoPaterno, String facPerApellidoMaterno, String facPerTelefono, String facPerCelular, String facPerEmail, String facPerDireccion, short facNumeroDerecho, short facCertificado, String facObservacion, Date facFechaEntrega, short facNumeroEscritura, BigDecimal facRegistrador, BigDecimal facAmanuence, BigDecimal facMaterial, BigDecimal facSubTotal, BigDecimal facDescuento, BigDecimal facBaseIva, BigDecimal facIva, BigDecimal facTotal, String facEstado, String facUser, Date facFHR, Date facFechaTramite, String facTipo) {
        this.facId = facId;
        this.facNumero = facNumero;
        this.facTraNumero = facTraNumero;
        this.facPerTipoContribuyente = facPerTipoContribuyente;
        this.facPerTipoIdentificacion = facPerTipoIdentificacion;
        this.facPerIdentificacion = facPerIdentificacion;
        this.facPerNombre = facPerNombre;
        this.facPerApellidoPaterno = facPerApellidoPaterno;
        this.facPerApellidoMaterno = facPerApellidoMaterno;
        this.facPerTelefono = facPerTelefono;
        this.facPerCelular = facPerCelular;
        this.facPerEmail = facPerEmail;
        this.facPerDireccion = facPerDireccion;
        this.facNumeroDerecho = facNumeroDerecho;
        this.facCertificado = facCertificado;
        this.facObservacion = facObservacion;
        this.facFechaEntrega = facFechaEntrega;
        this.facNumeroEscritura = facNumeroEscritura;
        this.facRegistrador = facRegistrador;
        this.facAmanuence = facAmanuence;
        this.facMaterial = facMaterial;
        this.facSubTotal = facSubTotal;
        this.facDescuento = facDescuento;
        this.facBaseIva = facBaseIva;
        this.facIva = facIva;
        this.facTotal = facTotal;
        this.facEstado = facEstado;
        this.facUser = facUser;
        this.facFHR = facFHR;
        this.facFechaTramite = facFechaTramite;
        this.facTipo = facTipo;
    }

    public String getFacEstadoCierre() {
        return facEstadoCierre;
    }

    public void setFacEstadoCierre(String facEstadoCierre) {
        this.facEstadoCierre = facEstadoCierre;
    }
    

    public BigDecimal getFacId() {
        return facId;
    }

    public void setFacId(BigDecimal facId) {
        this.facId = facId;
    }

    public String getFacNumero() {
        return facNumero;
    }

    public void setFacNumero(String facNumero) {
        this.facNumero = facNumero;
    }

    public String getFacPerTipoContribuyente() {
        return facPerTipoContribuyente;
    }

    public void setFacPerTipoContribuyente(String facPerTipoContribuyente) {
        this.facPerTipoContribuyente = facPerTipoContribuyente;
    }

    public String getFacPerTipoIdentificacion() {
        return facPerTipoIdentificacion;
    }

    public void setFacPerTipoIdentificacion(String facPerTipoIdentificacion) {
        this.facPerTipoIdentificacion = facPerTipoIdentificacion;
    }

    public String getFacPerIdentificacion() {
        return facPerIdentificacion;
    }

    public void setFacPerIdentificacion(String facPerIdentificacion) {
        this.facPerIdentificacion = facPerIdentificacion;
    }

    public String getFacPerNombre() {
        return facPerNombre;
    }

    public void setFacPerNombre(String facPerNombre) {
        this.facPerNombre = facPerNombre;
    }

    public String getFacPerApellidoPaterno() {
        return facPerApellidoPaterno;
    }

    public void setFacPerApellidoPaterno(String facPerApellidoPaterno) {
        this.facPerApellidoPaterno = facPerApellidoPaterno;
    }

    public String getFacPerApellidoMaterno() {
        return facPerApellidoMaterno;
    }

    public void setFacPerApellidoMaterno(String facPerApellidoMaterno) {
        this.facPerApellidoMaterno = facPerApellidoMaterno;
    }

    public String getFacPerTelefono() {
        return facPerTelefono;
    }

    public void setFacPerTelefono(String facPerTelefono) {
        this.facPerTelefono = facPerTelefono;
    }

    public String getFacPerCelular() {
        return facPerCelular;
    }

    public void setFacPerCelular(String facPerCelular) {
        this.facPerCelular = facPerCelular;
    }

    public String getFacPerEmail() {
        return facPerEmail;
    }

    public void setFacPerEmail(String facPerEmail) {
        this.facPerEmail = facPerEmail;
    }

    public String getFacPerDireccion() {
        return facPerDireccion;
    }

    public void setFacPerDireccion(String facPerDireccion) {
        this.facPerDireccion = facPerDireccion;
    }

    public String getFacObservacion() {
        return facObservacion;
    }

    public void setFacObservacion(String facObservacion) {
        this.facObservacion = facObservacion;
    }

    public Date getFacFechaEntrega() {
        return facFechaEntrega;
    }

    public void setFacFechaEntrega(Date facFechaEntrega) {
        this.facFechaEntrega = facFechaEntrega;
    }

    public BigDecimal getFacRegistrador() {
        return facRegistrador;
    }

    public void setFacRegistrador(BigDecimal facRegistrador) {
        this.facRegistrador = facRegistrador;
    }

    public BigDecimal getFacAmanuence() {
        return facAmanuence;
    }

    public void setFacAmanuence(BigDecimal facAmanuence) {
        this.facAmanuence = facAmanuence;
    }

    public BigDecimal getFacMaterial() {
        return facMaterial;
    }

    public void setFacMaterial(BigDecimal facMaterial) {
        this.facMaterial = facMaterial;
    }

    public BigDecimal getFacSubTotal() {
        return facSubTotal;
    }

    public void setFacSubTotal(BigDecimal facSubTotal) {
        this.facSubTotal = facSubTotal;
    }

    public BigDecimal getFacDescuento() {
        return facDescuento;
    }

    public void setFacDescuento(BigDecimal facDescuento) {
        this.facDescuento = facDescuento;
    }

    public BigDecimal getFacBaseIva() {
        return facBaseIva;
    }

    public void setFacBaseIva(BigDecimal facBaseIva) {
        this.facBaseIva = facBaseIva;
    }

    public BigDecimal getFacIva() {
        return facIva;
    }

    public void setFacIva(BigDecimal facIva) {
        this.facIva = facIva;
    }

    public BigDecimal getFacTotal() {
        return facTotal;
    }

    public void setFacTotal(BigDecimal facTotal) {
        this.facTotal = facTotal;
    }

    public String getFacEstado() {
        return facEstado;
    }

    public void setFacEstado(String facEstado) {
        this.facEstado = facEstado;
    }

    public String getFacUser() {
        return facUser;
    }

    public void setFacUser(String facUser) {
        this.facUser = facUser;
    }

    public Date getFacFHR() {
        return facFHR;
    }

    public void setFacFHR(Date facFHR) {
        this.facFHR = facFHR;
    }

    public Date getFacFechaTramite() {
        return facFechaTramite;
    }

    public void setFacFechaTramite(Date facFechaTramite) {
        this.facFechaTramite = facFechaTramite;
    }

    public BigInteger getFacTroId() {
        return facTroId;
    }

    public void setFacTroId(BigInteger facTroId) {
        this.facTroId = facTroId;
    }
    
    public Integer getFacFormaAdquisicion() {
        return facFormaAdquisicion;
    }

    public void setFacFormaAdquisicion(Integer facFormaAdquisicion) {
        this.facFormaAdquisicion = facFormaAdquisicion;
    }

    @XmlTransient
    public List<FacturaInfoAdicional> getFacturaInfoAdicionalList() {
        return facturaInfoAdicionalList;
    }

    public void setFacturaInfoAdicionalList(List<FacturaInfoAdicional> facturaInfoAdicionalList) {
        this.facturaInfoAdicionalList = facturaInfoAdicionalList;
    }

    public String getFacTroNombre() {
        return facTroNombre;
    }

    public void setFacTroNombre(String facTroNombre) {
        this.facTroNombre = facTroNombre;
    }
    public Integer getFacNumeroAcciones() {
        return facNumeroAcciones;
    }

    public void setFacNumeroAcciones(Integer facNumeroAcciones) {
        this.facNumeroAcciones = facNumeroAcciones;
    }

    @XmlTransient
    public List<FacturaDetalle> getFacturaDetalleList() {
        return facturaDetalleList;
    }

    public void setFacturaDetalleList(List<FacturaDetalle> facturaDetalleList) {
        this.facturaDetalleList = facturaDetalleList;
    }

    public Caja getCajId() {
        return cajId;
    }

    public void setCajId(Caja cajId) {
        this.cajId = cajId;
    }

    public Persona getPerId() {
        return perId;
    }

    public void setPerId(Persona perId) {
        this.perId = perId;
    }

    @XmlTransient
    public List<FacturaTrabajo> getFacturaTrabajoList() {
        return facturaTrabajoList;
    }

    public void setFacturaTrabajoList(List<FacturaTrabajo> facturaTrabajoList) {
        this.facturaTrabajoList = facturaTrabajoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (facId != null ? facId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.facId == null && other.facId != null) || (this.facId != null && !this.facId.equals(other.facId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Factura[ facId=" + facId + " ]";
    }

    public Integer getFacTraNumero() {
        return facTraNumero;
    }

    public void setFacTraNumero(Integer facTraNumero) {
        this.facTraNumero = facTraNumero;
    }

    public Short getFacNumeroDerecho() {
        return facNumeroDerecho;
    }

    public void setFacNumeroDerecho(Short facNumeroDerecho) {
        this.facNumeroDerecho = facNumeroDerecho;
    }

    public Short getFacCertificado() {
        return facCertificado;
    }

    public void setFacCertificado(Short facCertificado) {
        this.facCertificado = facCertificado;
    }

    public Short getFacNumeroEscritura() {
        return facNumeroEscritura;
    }

    public void setFacNumeroEscritura(Short facNumeroEscritura) {
        this.facNumeroEscritura = facNumeroEscritura;
    }

    public Date getFacFecha() {
        return facFecha;
    }

    public void setFacFecha(Date facFecha) {
        this.facFecha = facFecha;
    }

    public String getFacEstadoCertificado() {
        return facEstadoCertificado;
    }

    public void setFacEstadoCertificado(String facEstadoCertificado) {
        this.facEstadoCertificado = facEstadoCertificado;
    }

    @XmlTransient
    public Collection<FacturaFormaPago> getFacturaFormaPagoCollection() {
        return facturaFormaPagoCollection;
    }

    public void setFacturaFormaPagoCollection(Collection<FacturaFormaPago> facturaFormaPagoCollection) {
        this.facturaFormaPagoCollection = facturaFormaPagoCollection;
    }

}
