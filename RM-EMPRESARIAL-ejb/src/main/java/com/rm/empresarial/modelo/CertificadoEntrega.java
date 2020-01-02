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
import javax.persistence.JoinColumns;
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
 * @author BRANDON
 */
@Entity
@Table(name = "CertificadoEntrega")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CertificadoEntrega.findAll", query = "SELECT c FROM CertificadoEntrega c")
    , @NamedQuery(name = "CertificadoEntrega.findByCetId", query = "SELECT c FROM CertificadoEntrega c WHERE c.cetId = :cetId")
    , @NamedQuery(name = "CertificadoEntrega.findByCetDecripcion", query = "SELECT c FROM CertificadoEntrega c WHERE c.cetDecripcion = :cetDecripcion")
    , @NamedQuery(name = "CertificadoEntrega.findByCetFHR", query = "SELECT c FROM CertificadoEntrega c WHERE c.cetFHR = :cetFHR")})
public class CertificadoEntrega implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CetId")
    private BigDecimal cetId;
    @Size(max = 200)
    @Column(name = "CetDecripcion")
    private String cetDecripcion;
    @Column(name = "CetFHR")
    @Temporal(TemporalType.DATE)
    private Date cetFHR;
    @JoinColumns({
        @JoinColumn(name = "CerNumero", referencedColumnName = "CerNumero")
        , @JoinColumn(name = "CerSecuencial", referencedColumnName = "CerSecuencial")})
    @ManyToOne(optional = false)
    private Certificado certificado;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public CertificadoEntrega() {
    }

    public CertificadoEntrega(BigDecimal cetId) {
        this.cetId = cetId;
    }

    public BigDecimal getCetId() {
        return cetId;
    }

    public void setCetId(BigDecimal cetId) {
        this.cetId = cetId;
    }

    public String getCetDecripcion() {
        return cetDecripcion;
    }

    public void setCetDecripcion(String cetDecripcion) {
        this.cetDecripcion = cetDecripcion;
    }

    public Date getCetFHR() {
        return cetFHR;
    }

    public void setCetFHR(Date cetFHR) {
        this.cetFHR = cetFHR;
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
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
        hash += (cetId != null ? cetId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CertificadoEntrega)) {
            return false;
        }
        CertificadoEntrega other = (CertificadoEntrega) object;
        if ((this.cetId == null && other.cetId != null) || (this.cetId != null && !this.cetId.equals(other.cetId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CertificadoEntrega[ cetId=" + cetId + " ]";
    }
    
}
