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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Bryan_Mora
 */
@Entity
@Table(name = "FacturaDetalleAdicional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaDetalleAdicional.findAll", query = "SELECT f FROM FacturaDetalleAdicional f")
    , @NamedQuery(name = "FacturaDetalleAdicional.findByFdaId", query = "SELECT f FROM FacturaDetalleAdicional f WHERE f.fdaId = :fdaId")
    , @NamedQuery(name = "FacturaDetalleAdicional.findByFdaNombre", query = "SELECT f FROM FacturaDetalleAdicional f WHERE f.fdaNombre = :fdaNombre")
    , @NamedQuery(name = "FacturaDetalleAdicional.findByFdaValor", query = "SELECT f FROM FacturaDetalleAdicional f WHERE f.fdaValor = :fdaValor")})
public class FacturaDetalleAdicional implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "FdaId",nullable = false)
    private BigDecimal fdaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FdaNombre")
    private String fdaNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FdaValor")
    private String fdaValor;
    @JoinColumn(name = "FdtId", referencedColumnName = "FdtId")
    @ManyToOne(optional = false)
    private FacturaDetalle fdtId;

    public FacturaDetalleAdicional() {
    }

    public FacturaDetalleAdicional(BigDecimal fdaId) {
        this.fdaId = fdaId;
    }

    public FacturaDetalleAdicional(BigDecimal fdaId, String fdaNombre, String fdaValor) {
        this.fdaId = fdaId;
        this.fdaNombre = fdaNombre;
        this.fdaValor = fdaValor;
    }

    public BigDecimal getFdaId() {
        return fdaId;
    }

    public void setFdaId(BigDecimal fdaId) {
        this.fdaId = fdaId;
    }

    public String getFdaNombre() {
        return fdaNombre;
    }

    public void setFdaNombre(String fdaNombre) {
        this.fdaNombre = fdaNombre;
    }

    public String getFdaValor() {
        return fdaValor;
    }

    public void setFdaValor(String fdaValor) {
        this.fdaValor = fdaValor;
    }

    public FacturaDetalle getFdtId() {
        return fdtId;
    }

    public void setFdtId(FacturaDetalle fdtId) {
        this.fdtId = fdtId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fdaId != null ? fdaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaDetalleAdicional)) {
            return false;
        }
        FacturaDetalleAdicional other = (FacturaDetalleAdicional) object;
        if ((this.fdaId == null && other.fdaId != null) || (this.fdaId != null && !this.fdaId.equals(other.fdaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.FacturaDetalleAdicional[ fdaId=" + fdaId + " ]";
    }
    
}
