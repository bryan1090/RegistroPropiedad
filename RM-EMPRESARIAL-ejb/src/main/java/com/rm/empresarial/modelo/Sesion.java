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
@Table(name = "Sesion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sesion.findAll", query = "SELECT s FROM Sesion s")
    , @NamedQuery(name = "Sesion.findBySesId", query = "SELECT s FROM Sesion s WHERE s.sesId = :sesId")
    , @NamedQuery(name = "Sesion.findBySesIp", query = "SELECT s FROM Sesion s WHERE s.sesIp = :sesIp")
    , @NamedQuery(name = "Sesion.findBySesFHR", query = "SELECT s FROM Sesion s WHERE s.sesFHR = :sesFHR")})
public class Sesion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "SesId")
    private BigDecimal sesId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SesIp")
    private String sesIp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SesFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sesFHR;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public Sesion() {
    }

    public Sesion(BigDecimal sesId) {
        this.sesId = sesId;
    }

    public Sesion(BigDecimal sesId, String sesIp, Date sesFHR) {
        this.sesId = sesId;
        this.sesIp = sesIp;
        this.sesFHR = sesFHR;
    }

    public BigDecimal getSesId() {
        return sesId;
    }

    public void setSesId(BigDecimal sesId) {
        this.sesId = sesId;
    }

    public String getSesIp() {
        return sesIp;
    }

    public void setSesIp(String sesIp) {
        this.sesIp = sesIp;
    }

    public Date getSesFHR() {
        return sesFHR;
    }

    public void setSesFHR(Date sesFHR) {
        this.sesFHR = sesFHR;
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
        hash += (sesId != null ? sesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sesion)) {
            return false;
        }
        Sesion other = (Sesion) object;
        if ((this.sesId == null && other.sesId != null) || (this.sesId != null && !this.sesId.equals(other.sesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Sesion[ sesId=" + sesId + " ]";
    }
    
}
