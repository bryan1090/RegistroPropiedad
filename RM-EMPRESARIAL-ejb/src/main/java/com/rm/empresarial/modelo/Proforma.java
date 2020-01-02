/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Proforma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Proforma.findAll", query = "SELECT p FROM Proforma p")
    , @NamedQuery(name = "Proforma.findByPrfId", query = "SELECT p FROM Proforma p WHERE p.prfId = :prfId")
    , @NamedQuery(name = "Proforma.findByPrfNumero", query = "SELECT p FROM Proforma p WHERE p.prfNumero = :prfNumero")
    , @NamedQuery(name = "Proforma.findByPrfNumeroDerecho", query = "SELECT p FROM Proforma p WHERE p.prfNumeroDerecho = :prfNumeroDerecho")
    , @NamedQuery(name = "Proforma.findByPrfNumeroEscritura", query = "SELECT p FROM Proforma p WHERE p.prfNumeroEscritura = :prfNumeroEscritura")
    , @NamedQuery(name = "Proforma.findByPrfCertificado", query = "SELECT p FROM Proforma p WHERE p.prfCertificado = :prfCertificado")
    , @NamedQuery(name = "Proforma.findByPrfObservacion", query = "SELECT p FROM Proforma p WHERE p.prfObservacion = :prfObservacion")
    , @NamedQuery(name = "Proforma.findByPrfFechaTramite", query = "SELECT p FROM Proforma p WHERE p.prfFechaTramite = :prfFechaTramite")
    , @NamedQuery(name = "Proforma.findByPrfcRegistrador", query = "SELECT p FROM Proforma p WHERE p.prfcRegistrador = :prfcRegistrador")
    , @NamedQuery(name = "Proforma.findByPrfAmanuence", query = "SELECT p FROM Proforma p WHERE p.prfAmanuence = :prfAmanuence")
    , @NamedQuery(name = "Proforma.findByPrfMaterial", query = "SELECT p FROM Proforma p WHERE p.prfMaterial = :prfMaterial")
    , @NamedQuery(name = "Proforma.findByPrfSubTotal", query = "SELECT p FROM Proforma p WHERE p.prfSubTotal = :prfSubTotal")
    , @NamedQuery(name = "Proforma.findByPrfDescuento", query = "SELECT p FROM Proforma p WHERE p.prfDescuento = :prfDescuento")
    , @NamedQuery(name = "Proforma.findByPrfBaseIva", query = "SELECT p FROM Proforma p WHERE p.prfBaseIva = :prfBaseIva")
    , @NamedQuery(name = "Proforma.findByPrfIva", query = "SELECT p FROM Proforma p WHERE p.prfIva = :prfIva")
    , @NamedQuery(name = "Proforma.findByPrfTotal", query = "SELECT p FROM Proforma p WHERE p.prfTotal = :prfTotal")
    , @NamedQuery(name = "Proforma.findByPrfEstado", query = "SELECT p FROM Proforma p WHERE p.prfEstado = :prfEstado")
    , @NamedQuery(name = "Proforma.findByPrfUser", query = "SELECT p FROM Proforma p WHERE p.prfUser = :prfUser")
    , @NamedQuery(name = "Proforma.findByPrfFHR", query = "SELECT p FROM Proforma p WHERE p.prfFHR = :prfFHR")})
public class Proforma implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfId")
    private BigDecimal prfId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "PrfNumero")
    private String prfNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfNumeroDerecho")
    private short prfNumeroDerecho;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfNumeroEscritura")
    private short prfNumeroEscritura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfCertificado")
    private short prfCertificado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "PrfObservacion")
    private String prfObservacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfFechaTramite")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prfFechaTramite;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfcRegistrador")
    private BigDecimal prfcRegistrador;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfAmanuence")
    private BigDecimal prfAmanuence;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfMaterial")
    private BigDecimal prfMaterial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfSubTotal")
    private BigDecimal prfSubTotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfDescuento")
    private BigDecimal prfDescuento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfBaseIva")
    private BigDecimal prfBaseIva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfIva")
    private BigDecimal prfIva;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfTotal")
    private BigDecimal prfTotal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PrfEstado")
    private String prfEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PrfUser")
    private String prfUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrfFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prfFHR;
    @JoinColumn(name = "CajId", referencedColumnName = "CajId")
    @ManyToOne(optional = false)
    private Caja cajId;
    @JoinColumn(name = "PerId", referencedColumnName = "PerId")
    @ManyToOne(optional = false)
    private Persona perId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prfId")
    private List<ProformaDetalle> proformaDetalleList;

    public Proforma() {
    }

    public Proforma(BigDecimal prfId) {
        this.prfId = prfId;
    }

    public Proforma(BigDecimal prfId, String prfNumero, short prfNumeroDerecho, short prfNumeroEscritura, short prfCertificado, String prfObservacion, Date prfFechaTramite, BigDecimal prfcRegistrador, BigDecimal prfAmanuence, BigDecimal prfMaterial, BigDecimal prfSubTotal, BigDecimal prfDescuento, BigDecimal prfBaseIva, BigDecimal prfIva, BigDecimal prfTotal, String prfEstado, String prfUser, Date prfFHR) {
        this.prfId = prfId;
        this.prfNumero = prfNumero;
        this.prfNumeroDerecho = prfNumeroDerecho;
        this.prfNumeroEscritura = prfNumeroEscritura;
        this.prfCertificado = prfCertificado;
        this.prfObservacion = prfObservacion;
        this.prfFechaTramite = prfFechaTramite;
        this.prfcRegistrador = prfcRegistrador;
        this.prfAmanuence = prfAmanuence;
        this.prfMaterial = prfMaterial;
        this.prfSubTotal = prfSubTotal;
        this.prfDescuento = prfDescuento;
        this.prfBaseIva = prfBaseIva;
        this.prfIva = prfIva;
        this.prfTotal = prfTotal;
        this.prfEstado = prfEstado;
        this.prfUser = prfUser;
        this.prfFHR = prfFHR;
    }

    public BigDecimal getPrfId() {
        return prfId;
    }

    public void setPrfId(BigDecimal prfId) {
        this.prfId = prfId;
    }

    public String getPrfNumero() {
        return prfNumero;
    }

    public void setPrfNumero(String prfNumero) {
        this.prfNumero = prfNumero;
    }

    public short getPrfNumeroDerecho() {
        return prfNumeroDerecho;
    }

    public void setPrfNumeroDerecho(short prfNumeroDerecho) {
        this.prfNumeroDerecho = prfNumeroDerecho;
    }

    public short getPrfNumeroEscritura() {
        return prfNumeroEscritura;
    }

    public void setPrfNumeroEscritura(short prfNumeroEscritura) {
        this.prfNumeroEscritura = prfNumeroEscritura;
    }

    public short getPrfCertificado() {
        return prfCertificado;
    }

    public void setPrfCertificado(short prfCertificado) {
        this.prfCertificado = prfCertificado;
    }

    public String getPrfObservacion() {
        return prfObservacion;
    }

    public void setPrfObservacion(String prfObservacion) {
        this.prfObservacion = prfObservacion;
    }

    public Date getPrfFechaTramite() {
        return prfFechaTramite;
    }

    public void setPrfFechaTramite(Date prfFechaTramite) {
        this.prfFechaTramite = prfFechaTramite;
    }

    public BigDecimal getPrfcRegistrador() {
        return prfcRegistrador;
    }

    public void setPrfcRegistrador(BigDecimal prfcRegistrador) {
        this.prfcRegistrador = prfcRegistrador;
    }

    public BigDecimal getPrfAmanuence() {
        return prfAmanuence;
    }

    public void setPrfAmanuence(BigDecimal prfAmanuence) {
        this.prfAmanuence = prfAmanuence;
    }

    public BigDecimal getPrfMaterial() {
        return prfMaterial;
    }

    public void setPrfMaterial(BigDecimal prfMaterial) {
        this.prfMaterial = prfMaterial;
    }

    public BigDecimal getPrfSubTotal() {
        return prfSubTotal;
    }

    public void setPrfSubTotal(BigDecimal prfSubTotal) {
        this.prfSubTotal = prfSubTotal;
    }

    public BigDecimal getPrfDescuento() {
        return prfDescuento;
    }

    public void setPrfDescuento(BigDecimal prfDescuento) {
        this.prfDescuento = prfDescuento;
    }

    public BigDecimal getPrfBaseIva() {
        return prfBaseIva;
    }

    public void setPrfBaseIva(BigDecimal prfBaseIva) {
        this.prfBaseIva = prfBaseIva;
    }

    public BigDecimal getPrfIva() {
        return prfIva;
    }

    public void setPrfIva(BigDecimal prfIva) {
        this.prfIva = prfIva;
    }

    public BigDecimal getPrfTotal() {
        return prfTotal;
    }

    public void setPrfTotal(BigDecimal prfTotal) {
        this.prfTotal = prfTotal;
    }

    public String getPrfEstado() {
        return prfEstado;
    }

    public void setPrfEstado(String prfEstado) {
        this.prfEstado = prfEstado;
    }

    public String getPrfUser() {
        return prfUser;
    }

    public void setPrfUser(String prfUser) {
        this.prfUser = prfUser;
    }

    public Date getPrfFHR() {
        return prfFHR;
    }

    public void setPrfFHR(Date prfFHR) {
        this.prfFHR = prfFHR;
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
    public List<ProformaDetalle> getProformaDetalleList() {
        return proformaDetalleList;
    }

    public void setProformaDetalleList(List<ProformaDetalle> proformaDetalleList) {
        this.proformaDetalleList = proformaDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prfId != null ? prfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Proforma)) {
            return false;
        }
        Proforma other = (Proforma) object;
        if ((this.prfId == null && other.prfId != null) || (this.prfId != null && !this.prfId.equals(other.prfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Proforma[ prfId=" + prfId + " ]";
    }
    
}
