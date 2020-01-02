/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DJimenez
 */
@Entity
@Table(name = "TramiteResponsable")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteResponsable.findAll", query = "SELECT t FROM TramiteResponsable t")
    , @NamedQuery(name = "TramiteResponsable.findByTrsId", query = "SELECT t FROM TramiteResponsable t WHERE t.trsId = :trsId")
    , @NamedQuery(name = "TramiteResponsable.findByPerId", query = "SELECT t FROM TramiteResponsable t WHERE t.perId = :perId")
    , @NamedQuery(name = "TramiteResponsable.findByTtrId", query = "SELECT t FROM TramiteResponsable t WHERE t.ttrId = :ttrId")})
public class TramiteResponsable implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrsId")
    private Long trsId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PerId")
    private Long perId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TtrId")
    private Long ttrId;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;

    public TramiteResponsable() {
    }

    public TramiteResponsable(Long trsId) {
        this.trsId = trsId;
    }

    public TramiteResponsable(Long trsId, Long perId, Long ttrId) {
        this.trsId = trsId;
        this.perId = perId;
        this.ttrId = ttrId;
    }

    public Long getTrsId() {
        return trsId;
    }

    public void setTrsId(Long trsId) {
        this.trsId = trsId;
    }

    public Long getPerId() {
        return perId;
    }

    public void setPerId(Long perId) {
        this.perId = perId;
    }

    public Long getTtrId() {
        return ttrId;
    }

    public void setTtrId(Long ttrId) {
        this.ttrId = ttrId;
    }

    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trsId != null ? trsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteResponsable)) {
            return false;
        }
        TramiteResponsable other = (TramiteResponsable) object;
        if ((this.trsId == null && other.trsId != null) || (this.trsId != null && !this.trsId.equals(other.trsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TramiteResponsable[ trsId=" + trsId + " ]";
    }
    
}
