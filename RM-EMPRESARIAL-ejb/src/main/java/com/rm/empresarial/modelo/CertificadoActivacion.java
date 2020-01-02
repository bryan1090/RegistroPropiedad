/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "CertificadoActivacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CertificadoActivacion.findAll", query = "SELECT c FROM CertificadoActivacion c")
    , @NamedQuery(name = "CertificadoActivacion.findByCacId", query = "SELECT c FROM CertificadoActivacion c WHERE c.cacId = :cacId")
    , @NamedQuery(name = "CertificadoActivacion.findByCerNumero", query = "SELECT c FROM CertificadoActivacion c WHERE c.cerNumero = :cerNumero")
    , @NamedQuery(name = "CertificadoActivacion.findByCacUser", query = "SELECT c FROM CertificadoActivacion c WHERE c.cacUser = :cacUser")
    , @NamedQuery(name = "CertificadoActivacion.findByCacFHR", query = "SELECT c FROM CertificadoActivacion c WHERE c.cacFHR = :cacFHR")})
public class CertificadoActivacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CacId")
    private Long cacId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CerNumero")
    private String cerNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CacUser")
    private String cacUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CacFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cacFHR;
    
    @Column(name = "CacActivacion")
    private Boolean cacActivacion;

    public CertificadoActivacion() {
    }

    public CertificadoActivacion(Long cacId) {
        this.cacId = cacId;
    }

    public CertificadoActivacion(Long cacId, String cerNumero, String cacUser, Date cacFHR) {
        this.cacId = cacId;
        this.cerNumero = cerNumero;
        this.cacUser = cacUser;
        this.cacFHR = cacFHR;
    }

    public Long getCacId() {
        return cacId;
    }

    public void setCacId(Long cacId) {
        this.cacId = cacId;
    }

    public String getCerNumero() {
        return cerNumero;
    }

    public void setCerNumero(String cerNumero) {
        this.cerNumero = cerNumero;
    }

    public String getCacUser() {
        return cacUser;
    }

    public void setCacUser(String cacUser) {
        this.cacUser = cacUser;
    }

    public Date getCacFHR() {
        return cacFHR;
    }

    public void setCacFHR(Date cacFHR) {
        this.cacFHR = cacFHR;
    }
    
    public Boolean getCacActivacion() {
        return cacActivacion;
    }

    public void setCacActivacion(Boolean cacActivacion) {
        this.cacActivacion = cacActivacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cacId != null ? cacId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CertificadoActivacion)) {
            return false;
        }
        CertificadoActivacion other = (CertificadoActivacion) object;
        if ((this.cacId == null && other.cacId != null) || (this.cacId != null && !this.cacId.equals(other.cacId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CertificadoActivacion[ cacId=" + cacId + " ]";
    }

}
