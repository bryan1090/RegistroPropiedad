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
@Table(name = "FacturaInfoAdicional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaInfoAdicional.findAll", query = "SELECT f FROM FacturaInfoAdicional f")
    , @NamedQuery(name = "FacturaInfoAdicional.findByFiaId", query = "SELECT f FROM FacturaInfoAdicional f WHERE f.fiaId = :fiaId")
    , @NamedQuery(name = "FacturaInfoAdicional.findByFiaNombre", query = "SELECT f FROM FacturaInfoAdicional f WHERE f.fiaNombre = :fiaNombre")
    , @NamedQuery(name = "FacturaInfoAdicional.findByFiaValor", query = "SELECT f FROM FacturaInfoAdicional f WHERE f.fiaValor = :fiaValor")})
public class FacturaInfoAdicional implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "FiaId",nullable = false)
    private BigDecimal fiaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "FiaNombre")
    private String fiaNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FiaValor")
    private String fiaValor;
    @JoinColumn(name = "FacId", referencedColumnName = "FacId")
    @ManyToOne(optional = false)
    private Factura facId;

    public FacturaInfoAdicional() {
    }

    public FacturaInfoAdicional(BigDecimal fiaId) {
        this.fiaId = fiaId;
    }

    public FacturaInfoAdicional(BigDecimal fiaId, String fiaNombre, String fiaValor) {
        this.fiaId = fiaId;
        this.fiaNombre = fiaNombre;
        this.fiaValor = fiaValor;
    }

    public BigDecimal getFiaId() {
        return fiaId;
    }

    public void setFiaId(BigDecimal fiaId) {
        this.fiaId = fiaId;
    }

    public String getFiaNombre() {
        return fiaNombre;
    }

    public void setFiaNombre(String fiaNombre) {
        this.fiaNombre = fiaNombre;
    }

    public String getFiaValor() {
        return fiaValor;
    }

    public void setFiaValor(String fiaValor) {
        this.fiaValor = fiaValor;
    }

    public Factura getFacId() {
        return facId;
    }

    public void setFacId(Factura facId) {
        this.facId = facId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fiaId != null ? fiaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaInfoAdicional)) {
            return false;
        }
        FacturaInfoAdicional other = (FacturaInfoAdicional) object;
        if ((this.fiaId == null && other.fiaId != null) || (this.fiaId != null && !this.fiaId.equals(other.fiaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.FacturaInfoAdicional[ fiaId=" + fiaId + " ]";
    }
    
}
