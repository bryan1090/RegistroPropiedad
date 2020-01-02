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
 * @author Marco
 */
@Entity
@Table(name = "RepertorioPropiedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepertorioPropiedad.findAll", query = "SELECT r FROM RepertorioPropiedad r")
    , @NamedQuery(name = "RepertorioPropiedad.findByRpdId", query = "SELECT r FROM RepertorioPropiedad r WHERE r.rpdId = :rpdId")
    , @NamedQuery(name = "RepertorioPropiedad.findByRpdUser", query = "SELECT r FROM RepertorioPropiedad r WHERE r.rpdUser = :rpdUser")
    , @NamedQuery(name = "RepertorioPropiedad.findByRpdFHR", query = "SELECT r FROM RepertorioPropiedad r WHERE r.rpdFHR = :rpdFHR")})
public class RepertorioPropiedad implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RpdId")
    private BigDecimal rpdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RpdUser")
    private String rpdUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RpdFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rpdFHR;
    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;
    @JoinColumn(name = "RepNumero", referencedColumnName = "RepNumero")
    @ManyToOne(optional = false)
    private Repertorio repNumero;

    public RepertorioPropiedad() {
    }

    public RepertorioPropiedad(BigDecimal rpdId) {
        this.rpdId = rpdId;
    }

    public RepertorioPropiedad(BigDecimal rpdId, String rpdUser, Date rpdFHR) {
        this.rpdId = rpdId;
        this.rpdUser = rpdUser;
        this.rpdFHR = rpdFHR;
    }

    public BigDecimal getRpdId() {
        return rpdId;
    }

    public void setRpdId(BigDecimal rpdId) {
        this.rpdId = rpdId;
    }

    public String getRpdUser() {
        return rpdUser;
    }

    public void setRpdUser(String rpdUser) {
        this.rpdUser = rpdUser;
    }

    public Date getRpdFHR() {
        return rpdFHR;
    }

    public void setRpdFHR(Date rpdFHR) {
        this.rpdFHR = rpdFHR;
    }

    public Propiedad getPrdMatricula() {
        return prdMatricula;
    }

    public void setPrdMatricula(Propiedad prdMatricula) {
        this.prdMatricula = prdMatricula;
    }

    public Repertorio getRepNumero() {
        return repNumero;
    }

    public void setRepNumero(Repertorio repNumero) {
        this.repNumero = repNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rpdId != null ? rpdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepertorioPropiedad)) {
            return false;
        }
        RepertorioPropiedad other = (RepertorioPropiedad) object;
        if ((this.rpdId == null && other.rpdId != null) || (this.rpdId != null && !this.rpdId.equals(other.rpdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.menun.RepertorioPropiedad[ rpdId=" + rpdId + " ]";
    }
    
}
