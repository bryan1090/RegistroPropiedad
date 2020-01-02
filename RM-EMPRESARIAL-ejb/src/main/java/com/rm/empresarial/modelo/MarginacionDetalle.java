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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "MarginacionDetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarginacionDetalle.findAll", query = "SELECT m FROM MarginacionDetalle m")
    , @NamedQuery(name = "MarginacionDetalle.findByMgdId", query = "SELECT m FROM MarginacionDetalle m WHERE m.mgdId = :mgdId")
    , @NamedQuery(name = "MarginacionDetalle.findByMgdDescripcion", query = "SELECT m FROM MarginacionDetalle m WHERE m.mgdDescripcion = :mgdDescripcion")
    , @NamedQuery(name = "MarginacionDetalle.findByMgdValor", query = "SELECT m FROM MarginacionDetalle m WHERE m.mgdValor = :mgdValor")
    , @NamedQuery(name = "MarginacionDetalle.findByMgdFHR", query = "SELECT m FROM MarginacionDetalle m WHERE m.mgdFHR = :mgdFHR")
    , @NamedQuery(name = "MarginacionDetalle.findByMgdUser", query = "SELECT m FROM MarginacionDetalle m WHERE m.mgdUser = :mgdUser")})
public class MarginacionDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MgdId")
    private BigDecimal mgdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "MgdDescripcion")
    private String mgdDescripcion;
    @Size(max = 1000)
    @Column(name = "MgdValor")
    private String mgdValor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MgdFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mgdFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MgdUser")
    private String mgdUser;
    @JoinColumn(name = "MrgId", referencedColumnName = "MrgId")
    @ManyToOne(optional = false)
    private Marginacion mrgId;

    public MarginacionDetalle() {
    }

    public MarginacionDetalle(BigDecimal mgdId) {
        this.mgdId = mgdId;
    }

    public MarginacionDetalle(BigDecimal mgdId, String mgdDescripcion, Date mgdFHR, String mgdUser) {
        this.mgdId = mgdId;
        this.mgdDescripcion = mgdDescripcion;
        this.mgdFHR = mgdFHR;
        this.mgdUser = mgdUser;
    }

    public BigDecimal getMgdId() {
        return mgdId;
    }

    public void setMgdId(BigDecimal mgdId) {
        this.mgdId = mgdId;
    }

    public String getMgdDescripcion() {
        return mgdDescripcion;
    }

    public void setMgdDescripcion(String mgdDescripcion) {
        this.mgdDescripcion = mgdDescripcion;
    }

    public String getMgdValor() {
        return mgdValor;
    }

    public void setMgdValor(String mgdValor) {
        this.mgdValor = mgdValor;
    }

    public Date getMgdFHR() {
        return mgdFHR;
    }

    public void setMgdFHR(Date mgdFHR) {
        this.mgdFHR = mgdFHR;
    }

    public String getMgdUser() {
        return mgdUser;
    }

    public void setMgdUser(String mgdUser) {
        this.mgdUser = mgdUser;
    }

    public Marginacion getMrgId() {
        return mrgId;
    }

    public void setMrgId(Marginacion mrgId) {
        this.mrgId = mrgId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mgdId != null ? mgdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarginacionDetalle)) {
            return false;
        }
        MarginacionDetalle other = (MarginacionDetalle) object;
        if ((this.mgdId == null && other.mgdId != null) || (this.mgdId != null && !this.mgdId.equals(other.mgdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MarginacionDetalle[ mgdId=" + mgdId + " ]";
    }

}
