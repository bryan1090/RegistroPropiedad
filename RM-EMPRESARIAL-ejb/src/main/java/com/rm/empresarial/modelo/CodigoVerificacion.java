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
@Table(name = "CodigoVerificacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CodigoVerificacion.findAll", query = "SELECT c FROM CodigoVerificacion c")
    , @NamedQuery(name = "CodigoVerificacion.findByCvnId", query = "SELECT c FROM CodigoVerificacion c WHERE c.cvnId = :cvnId")
    , @NamedQuery(name = "CodigoVerificacion.findByCvnCodigo", query = "SELECT c FROM CodigoVerificacion c WHERE c.cvnCodigo = :cvnCodigo")
    , @NamedQuery(name = "CodigoVerificacion.findByCvnEstado", query = "SELECT c FROM CodigoVerificacion c WHERE c.cvnEstado = :cvnEstado")
    , @NamedQuery(name = "CodigoVerificacion.findByCvnFHR", query = "SELECT c FROM CodigoVerificacion c WHERE c.cvnFHR = :cvnFHR")
    , @NamedQuery(name = "CodigoVerificacion.findByCvnUser", query = "SELECT c FROM CodigoVerificacion c WHERE c.cvnUser = :cvnUser")
    , @NamedQuery(name = "CodigoVerificacion.findByCvnVigencia", query = "SELECT c FROM CodigoVerificacion c WHERE c.cvnVigencia = :cvnVigencia")})
public class CodigoVerificacion implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CvnId")
    private Long cvnId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "CvnCodigo")
    private String cvnCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CvnEstado")
    private String cvnEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CvnFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cvnFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CvnUser")
    private String cvnUser;
    @Column(name = "CvnVigencia")
    private BigDecimal cvnVigencia;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public CodigoVerificacion() {
    }

    public CodigoVerificacion(Long cvnId) {
        this.cvnId = cvnId;
    }

    public CodigoVerificacion(Long cvnId, String cvnCodigo, String cvnEstado, Date cvnFHR, String cvnUser) {
        this.cvnId = cvnId;
        this.cvnCodigo = cvnCodigo;
        this.cvnEstado = cvnEstado;
        this.cvnFHR = cvnFHR;
        this.cvnUser = cvnUser;
    }

    public Long getCvnId() {
        return cvnId;
    }

    public void setCvnId(Long cvnId) {
        this.cvnId = cvnId;
    }

    public String getCvnCodigo() {
        return cvnCodigo;
    }

    public void setCvnCodigo(String cvnCodigo) {
        this.cvnCodigo = cvnCodigo;
    }

    public String getCvnEstado() {
        return cvnEstado;
    }

    public void setCvnEstado(String cvnEstado) {
        this.cvnEstado = cvnEstado;
    }

    public Date getCvnFHR() {
        return cvnFHR;
    }

    public void setCvnFHR(Date cvnFHR) {
        this.cvnFHR = cvnFHR;
    }

    public String getCvnUser() {
        return cvnUser;
    }

    public void setCvnUser(String cvnUser) {
        this.cvnUser = cvnUser;
    }

    public BigDecimal getCvnVigencia() {
        return cvnVigencia;
    }

    public void setCvnVigencia(BigDecimal cvnVigencia) {
        this.cvnVigencia = cvnVigencia;
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
        hash += (cvnId != null ? cvnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodigoVerificacion)) {
            return false;
        }
        CodigoVerificacion other = (CodigoVerificacion) object;
        if ((this.cvnId == null && other.cvnId != null) || (this.cvnId != null && !this.cvnId.equals(other.cvnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CodigoVerificacion[ cvnId=" + cvnId + " ]";
    }
    
}
