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
 * @author JeanCarlos
 */
@Entity
@Table(name = "TramiteEntrega")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteEntrega.findAll", query = "SELECT t FROM TramiteEntrega t")
    , @NamedQuery(name = "TramiteEntrega.findByTdeId", query = "SELECT t FROM TramiteEntrega t WHERE t.tdeId = :tdeId")
    , @NamedQuery(name = "TramiteEntrega.findByTdeUser", query = "SELECT t FROM TramiteEntrega t WHERE t.tdeUser = :tdeUser")
    , @NamedQuery(name = "TramiteEntrega.findByTdeFHR", query = "SELECT t FROM TramiteEntrega t WHERE t.tdeFHR = :tdeFHR")})
public class TramiteEntrega implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TdeId")
    private Long tdeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TdeUser")
    private String tdeUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdeFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tdeFHR;
    @JoinColumn(name = "DetId", referencedColumnName = "DetId")
    @ManyToOne(optional = false)
    private DocumentoEntregaTramite detId;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;

    public TramiteEntrega() {
    }

    public TramiteEntrega(Long tdeId) {
        this.tdeId = tdeId;
    }

    public TramiteEntrega(Long tdeId, String tdeUser, Date tdeFHR) {
        this.tdeId = tdeId;
        this.tdeUser = tdeUser;
        this.tdeFHR = tdeFHR;
    }
    
    public TramiteEntrega(Tramite traNumero, DocumentoEntregaTramite detId, String tdeUser, Date tdeFHR) {
        this.traNumero = traNumero;
        this.detId = detId;
        this.tdeUser = tdeUser;
        this.tdeFHR = tdeFHR;
    }

    public Long getTdeId() {
        return tdeId;
    }

    public void setTdeId(Long tdeId) {
        this.tdeId = tdeId;
    }

    public String getTdeUser() {
        return tdeUser;
    }

    public void setTdeUser(String tdeUser) {
        this.tdeUser = tdeUser;
    }

    public Date getTdeFHR() {
        return tdeFHR;
    }

    public void setTdeFHR(Date tdeFHR) {
        this.tdeFHR = tdeFHR;
    }

    public DocumentoEntregaTramite getDetId() {
        return detId;
    }

    public void setDetId(DocumentoEntregaTramite detId) {
        this.detId = detId;
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
        hash += (tdeId != null ? tdeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteEntrega)) {
            return false;
        }
        TramiteEntrega other = (TramiteEntrega) object;
        if ((this.tdeId == null && other.tdeId != null) || (this.tdeId != null && !this.tdeId.equals(other.tdeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TramiteEntrega[ tdeId=" + tdeId + " ]";
    }
    
}
