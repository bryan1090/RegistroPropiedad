/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "ProformaDetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProformaDetalle.findAll", query = "SELECT p FROM ProformaDetalle p")
    , @NamedQuery(name = "ProformaDetalle.findByPfdId", query = "SELECT p FROM ProformaDetalle p WHERE p.pfdId = :pfdId")
    , @NamedQuery(name = "ProformaDetalle.findByPfdTtrId", query = "SELECT p FROM ProformaDetalle p WHERE p.pfdTtrId = :pfdTtrId")
    , @NamedQuery(name = "ProformaDetalle.findByPfdTtrDescripcion", query = "SELECT p FROM ProformaDetalle p WHERE p.pfdTtrDescripcion = :pfdTtrDescripcion")
    , @NamedQuery(name = "ProformaDetalle.findByPfdCantidad", query = "SELECT p FROM ProformaDetalle p WHERE p.pfdCantidad = :pfdCantidad")
    , @NamedQuery(name = "ProformaDetalle.findByPfdValor", query = "SELECT p FROM ProformaDetalle p WHERE p.pfdValor = :pfdValor")
    , @NamedQuery(name = "ProformaDetalle.findByPfdDescuento", query = "SELECT p FROM ProformaDetalle p WHERE p.pfdDescuento = :pfdDescuento")
    , @NamedQuery(name = "ProformaDetalle.findByPfdTotal", query = "SELECT p FROM ProformaDetalle p WHERE p.pfdTotal = :pfdTotal")})
public class ProformaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "PfdId")
    private BigDecimal pfdId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PfdTtrId")
    private short pfdTtrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PfdTtrDescripcion")
    private short pfdTtrDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PfdCantidad")
    private short pfdCantidad;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PfdValor")
    private BigDecimal pfdValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PfdDescuento")
    private short pfdDescuento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PfdTotal")
    private BigDecimal pfdTotal;
    @JoinColumn(name = "PrfId", referencedColumnName = "PrfId")
    @ManyToOne(optional = false)
    private Proforma prfId;

    public ProformaDetalle() {
    }

    public ProformaDetalle(BigDecimal pfdId) {
        this.pfdId = pfdId;
    }

    public ProformaDetalle(BigDecimal pfdId, short pfdTtrId, short pfdTtrDescripcion, short pfdCantidad, BigDecimal pfdValor, short pfdDescuento, BigDecimal pfdTotal) {
        this.pfdId = pfdId;
        this.pfdTtrId = pfdTtrId;
        this.pfdTtrDescripcion = pfdTtrDescripcion;
        this.pfdCantidad = pfdCantidad;
        this.pfdValor = pfdValor;
        this.pfdDescuento = pfdDescuento;
        this.pfdTotal = pfdTotal;
    }

    public BigDecimal getPfdId() {
        return pfdId;
    }

    public void setPfdId(BigDecimal pfdId) {
        this.pfdId = pfdId;
    }

    public short getPfdTtrId() {
        return pfdTtrId;
    }

    public void setPfdTtrId(short pfdTtrId) {
        this.pfdTtrId = pfdTtrId;
    }

    public short getPfdTtrDescripcion() {
        return pfdTtrDescripcion;
    }

    public void setPfdTtrDescripcion(short pfdTtrDescripcion) {
        this.pfdTtrDescripcion = pfdTtrDescripcion;
    }

    public short getPfdCantidad() {
        return pfdCantidad;
    }

    public void setPfdCantidad(short pfdCantidad) {
        this.pfdCantidad = pfdCantidad;
    }

    public BigDecimal getPfdValor() {
        return pfdValor;
    }

    public void setPfdValor(BigDecimal pfdValor) {
        this.pfdValor = pfdValor;
    }

    public short getPfdDescuento() {
        return pfdDescuento;
    }

    public void setPfdDescuento(short pfdDescuento) {
        this.pfdDescuento = pfdDescuento;
    }

    public BigDecimal getPfdTotal() {
        return pfdTotal;
    }

    public void setPfdTotal(BigDecimal pfdTotal) {
        this.pfdTotal = pfdTotal;
    }

    public Proforma getPrfId() {
        return prfId;
    }

    public void setPrfId(Proforma prfId) {
        this.prfId = prfId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pfdId != null ? pfdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProformaDetalle)) {
            return false;
        }
        ProformaDetalle other = (ProformaDetalle) object;
        if ((this.pfdId == null && other.pfdId != null) || (this.pfdId != null && !this.pfdId.equals(other.pfdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ProformaDetalle[ pfdId=" + pfdId + " ]";
    }
    
}
