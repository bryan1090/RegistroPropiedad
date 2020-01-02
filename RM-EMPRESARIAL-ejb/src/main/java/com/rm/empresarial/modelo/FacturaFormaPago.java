/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wilson Herrera
 */
@Entity
@Table(name = "FacturaFormaPago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacturaFormaPago.findAll", query = "SELECT f FROM FacturaFormaPago f")
    , @NamedQuery(name = "FacturaFormaPago.findByFfpId", query = "SELECT f FROM FacturaFormaPago f WHERE f.ffpId = :ffpId")
    , @NamedQuery(name = "FacturaFormaPago.findByFfpValor", query = "SELECT f FROM FacturaFormaPago f WHERE f.ffpValor = :ffpValor")
    , @NamedQuery(name = "FacturaFormaPago.findByFfpDescripcion", query = "SELECT f FROM FacturaFormaPago f WHERE f.ffpDescripcion = :ffpDescripcion")
    , @NamedQuery(name = "FacturaFormaPago.findByFfpUser", query = "SELECT f FROM FacturaFormaPago f WHERE f.ffpUser = :ffpUser")
    , @NamedQuery(name = "FacturaFormaPago.findByFfpFHR", query = "SELECT f FROM FacturaFormaPago f WHERE f.ffpFHR = :ffpFHR")})
public class FacturaFormaPago implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FfpId",nullable = false)
    private BigDecimal ffpId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FfpValor")
    private BigDecimal ffpValor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "FfpDescripcion")
    private String ffpDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FfpUser")
    private String ffpUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FfpFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ffpFHR;
    @JoinColumn(name = "FacId", referencedColumnName = "FacId")
    @ManyToOne(optional = false)
    private Factura facId;
    @JoinColumn(name = "TpfId", referencedColumnName = "TpfId")
    @ManyToOne(optional = false)
    private TipoFormaPago tpfId;

    public FacturaFormaPago() {
    }

    public FacturaFormaPago(BigDecimal ffpId) {
        this.ffpId = ffpId;
    }

    public FacturaFormaPago(BigDecimal ffpId, BigDecimal ffpValor, String ffpDescripcion, String ffpUser, Date ffpFHR,TipoFormaPago tipoFormaPago,Factura factura) {
        this.ffpId = ffpId;
        this.ffpValor = ffpValor;
        this.ffpDescripcion = ffpDescripcion;
        this.ffpUser = ffpUser;
        this.ffpFHR = ffpFHR;
        this.tpfId=tipoFormaPago;
        this.facId=factura;
    }

    public BigDecimal getFfpId() {
        return ffpId;
    }

    public void setFfpId(BigDecimal ffpId) {
        this.ffpId = ffpId;
    }

    public BigDecimal getFfpValor() {
        return ffpValor;
    }

    public void setFfpValor(BigDecimal ffpValor) {
        this.ffpValor = ffpValor;
    }

    public String getFfpDescripcion() {
        return ffpDescripcion;
    }

    public void setFfpDescripcion(String ffpDescripcion) {
        this.ffpDescripcion = ffpDescripcion;
    }

    public String getFfpUser() {
        return ffpUser;
    }

    public void setFfpUser(String ffpUser) {
        this.ffpUser = ffpUser;
    }

    public Date getFfpFHR() {
        return ffpFHR;
    }

    public void setFfpFHR(Date ffpFHR) {
        this.ffpFHR = ffpFHR;
    }

    public Factura getFacId() {
        return facId;
    }

    public void setFacId(Factura facId) {
        this.facId = facId;
    }

    public TipoFormaPago getTpfId() {
        return tpfId;
    }

    public void setTpfId(TipoFormaPago tpfId) {
        this.tpfId = tpfId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ffpId != null ? ffpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacturaFormaPago)) {
            return false;
        }
        FacturaFormaPago other = (FacturaFormaPago) object;
        if ((this.ffpId == null && other.ffpId != null) || (this.ffpId != null && !this.ffpId.equals(other.ffpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.FacturaFormaPago[ ffpId=" + ffpId + " ]";
    }
    
}
