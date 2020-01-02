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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "HistoricoSesion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "HistoricoSesion.findAll", query = "SELECT h FROM HistoricoSesion h")
    , @NamedQuery(name = "HistoricoSesion.findByHisSesId", query = "SELECT h FROM HistoricoSesion h WHERE h.hisSesId = :hisSesId")
    , @NamedQuery(name = "HistoricoSesion.findByHisSesIp", query = "SELECT h FROM HistoricoSesion h WHERE h.hisSesIp = :hisSesIp")
    , @NamedQuery(name = "HistoricoSesion.findByHisSesFHR", query = "SELECT h FROM HistoricoSesion h WHERE h.hisSesFHR = :hisSesFHR")
    , @NamedQuery(name = "HistoricoSesion.findByHisSesTipo", query = "SELECT h FROM HistoricoSesion h WHERE h.hisSesTipo = :hisSesTipo")})
public class HistoricoSesion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "HisSesId")
    private BigDecimal hisSesId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "HisSesIp")
    private String hisSesIp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "HisSesFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hisSesFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "HisSesTipo")
    private String hisSesTipo;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public HistoricoSesion() {
    }

    public HistoricoSesion(BigDecimal hisSesId) {
        this.hisSesId = hisSesId;
    }

    public HistoricoSesion(BigDecimal hisSesId, String hisSesIp, Date hisSesFHR, String hisSesTipo) {
        this.hisSesId = hisSesId;
        this.hisSesIp = hisSesIp;
        this.hisSesFHR = hisSesFHR;
        this.hisSesTipo = hisSesTipo;
    }

    public BigDecimal getHisSesId() {
        return hisSesId;
    }

    public void setHisSesId(BigDecimal hisSesId) {
        this.hisSesId = hisSesId;
    }

    public String getHisSesIp() {
        return hisSesIp;
    }

    public void setHisSesIp(String hisSesIp) {
        this.hisSesIp = hisSesIp;
    }

    public Date getHisSesFHR() {
        return hisSesFHR;
    }

    public void setHisSesFHR(Date hisSesFHR) {
        this.hisSesFHR = hisSesFHR;
    }

    public String getHisSesTipo() {
        return hisSesTipo;
    }

    public void setHisSesTipo(String hisSesTipo) {
        this.hisSesTipo = hisSesTipo;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hisSesId != null ? hisSesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof HistoricoSesion)) {
            return false;
        }
        HistoricoSesion other = (HistoricoSesion) object;
        if ((this.hisSesId == null && other.hisSesId != null) || (this.hisSesId != null && !this.hisSesId.equals(other.hisSesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.HistoricoSesion[ hisSesId=" + hisSesId + " ]";
    }
    
}
