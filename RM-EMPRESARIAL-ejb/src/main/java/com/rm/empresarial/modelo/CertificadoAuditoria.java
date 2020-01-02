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
@Table(name = "CertificadoAuditoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CertificadoAuditoria.findAll", query = "SELECT c FROM CertificadoAuditoria c")
    , @NamedQuery(name = "CertificadoAuditoria.findByCadId", query = "SELECT c FROM CertificadoAuditoria c WHERE c.cadId = :cadId")
    , @NamedQuery(name = "CertificadoAuditoria.findByCerNumero", query = "SELECT c FROM CertificadoAuditoria c WHERE c.cerNumero = :cerNumero")
    , @NamedQuery(name = "CertificadoAuditoria.findByCadAtributo", query = "SELECT c FROM CertificadoAuditoria c WHERE c.cadAtributo = :cadAtributo")
    , @NamedQuery(name = "CertificadoAuditoria.findByCadAnterior", query = "SELECT c FROM CertificadoAuditoria c WHERE c.cadAnterior = :cadAnterior")
    , @NamedQuery(name = "CertificadoAuditoria.findByCadActual", query = "SELECT c FROM CertificadoAuditoria c WHERE c.cadActual = :cadActual")
    , @NamedQuery(name = "CertificadoAuditoria.findByCadUser", query = "SELECT c FROM CertificadoAuditoria c WHERE c.cadUser = :cadUser")
    , @NamedQuery(name = "CertificadoAuditoria.findByCadFHR", query = "SELECT c FROM CertificadoAuditoria c WHERE c.cadFHR = :cadFHR")})
public class CertificadoAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CadId")
    private Long cadId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CerNumero")
    private String cerNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CadAtributo")
    private String cadAtributo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "CadAnterior")
    private String cadAnterior;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "CadActual")
    private String cadActual;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CadUser")
    private String cadUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CadFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cadFHR;

    public CertificadoAuditoria() {
    }

    public CertificadoAuditoria(Long cadId) {
        this.cadId = cadId;
    }

    public CertificadoAuditoria(Long cadId, String cerNumero, String cadAtributo, String cadAnterior, String cadActual, String cadUser, Date cadFHR) {
        this.cadId = cadId;
        this.cerNumero = cerNumero;
        this.cadAtributo = cadAtributo;
        this.cadAnterior = cadAnterior;
        this.cadActual = cadActual;
        this.cadUser = cadUser;
        this.cadFHR = cadFHR;
    }

    public Long getCadId() {
        return cadId;
    }

    public void setCadId(Long cadId) {
        this.cadId = cadId;
    }

    public String getCerNumero() {
        return cerNumero;
    }

    public void setCerNumero(String cerNumero) {
        this.cerNumero = cerNumero;
    }

    public String getCadAtributo() {
        return cadAtributo;
    }

    public void setCadAtributo(String cadAtributo) {
        this.cadAtributo = cadAtributo;
    }

    public String getCadAnterior() {
        return cadAnterior;
    }

    public void setCadAnterior(String cadAnterior) {
        this.cadAnterior = cadAnterior;
    }

    public String getCadActual() {
        return cadActual;
    }

    public void setCadActual(String cadActual) {
        this.cadActual = cadActual;
    }

    public String getCadUser() {
        return cadUser;
    }

    public void setCadUser(String cadUser) {
        this.cadUser = cadUser;
    }

    public Date getCadFHR() {
        return cadFHR;
    }

    public void setCadFHR(Date cadFHR) {
        this.cadFHR = cadFHR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cadId != null ? cadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CertificadoAuditoria)) {
            return false;
        }
        CertificadoAuditoria other = (CertificadoAuditoria) object;
        if ((this.cadId == null && other.cadId != null) || (this.cadId != null && !this.cadId.equals(other.cadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CertificadoAuditoria[ cadId=" + cadId + " ]";
    }
    
}
