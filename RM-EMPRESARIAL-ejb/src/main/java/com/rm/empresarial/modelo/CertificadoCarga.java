/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import com.rm.empresarial.modelo.Factura;
import java.io.Serializable;
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
 * @author DJimenez
 */
@Entity
@Table(name = "CertificadoCarga")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CertificadoCarga.findAll", query = "SELECT c FROM CertificadoCarga c")
    , @NamedQuery(name = "CertificadoCarga.findByCdcId", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcId = :cdcId")
    , @NamedQuery(name = "CertificadoCarga.findByCdcTipo", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcTipo = :cdcTipo")
    , @NamedQuery(name = "CertificadoCarga.findByCdcEstado", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcEstado = :cdcEstado")
    , @NamedQuery(name = "CertificadoCarga.findByCdcUser", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcUser = :cdcUser")
    , @NamedQuery(name = "CertificadoCarga.findByCdcFHR", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcFHR = :cdcFHR")
    , @NamedQuery(name = "CertificadoCarga.findByCdcDocumento", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcDocumento = :cdcDocumento")
    , @NamedQuery(name = "CertificadoCarga.findByCdcCaja", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcCaja = :cdcCaja")
    , @NamedQuery(name = "CertificadoCarga.findByCdcCantidadDerecho", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcCantidadDerecho = :cdcCantidadDerecho")
    , @NamedQuery(name = "CertificadoCarga.findByCdcCantidadCertificado", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcCantidadCertificado = :cdcCantidadCertificado")
    , @NamedQuery(name = "CertificadoCarga.findByCdcCertificado", query = "SELECT c FROM CertificadoCarga c WHERE c.cdcCertificado = :cdcCertificado")})
public class CertificadoCarga implements Serializable {

    private static final long serialVersionUID = 1L;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CdcId")
    private Long cdcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CdcTipo")
    private String cdcTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CdcEstado")
    private String cdcEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CdcUser")
    private String cdcUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CdcFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cdcFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "CdcDocumento")
    private String cdcDocumento;
    @Size(max = 40)
    @Column(name = "CdcCaja")
    private String cdcCaja;
    @Column(name = "CdcCantidadDerecho")
    private Short cdcCantidadDerecho;
    @Column(name = "CdcCantidadCertificado")
    private Short cdcCantidadCertificado;
    @Size(max = 40)
    @Column(name = "CdcCertificado")
    private String cdcCertificado;

    @Size(max = 20)
    @Column(name = "CdcEstadoProceso")
    private String cdcEstadoProceso;
    
    @Size(max = 20)
    @Column(name = "CdcTipoGravamen")
    private String cdcTipoGravamen;
    
      @Column(name = "CdcActivo")
    private Boolean cdcActivo;

    @JoinColumn(name = "FacId", referencedColumnName = "FacId")
    @ManyToOne
    private Factura facId;

    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;

    public CertificadoCarga() {
    }

    public CertificadoCarga(Long cdcId) {
        this.cdcId = cdcId;
    }

    public CertificadoCarga(Long cdcId, String cdcTipo, String cdcEstado, String cdcUser, Date cdcFHR, String cdcDocumento) {
        this.cdcId = cdcId;
        this.cdcTipo = cdcTipo;
        this.cdcEstado = cdcEstado;
        this.cdcUser = cdcUser;
        this.cdcFHR = cdcFHR;
        this.cdcDocumento = cdcDocumento;
    }

    public Long getCdcId() {
        return cdcId;
    }

    public void setCdcId(Long cdcId) {
        this.cdcId = cdcId;
    }

    public String getCdcTipo() {
        return cdcTipo;
    }

    public void setCdcTipo(String cdcTipo) {
        this.cdcTipo = cdcTipo;
    }

    public String getCdcEstado() {
        return cdcEstado;
    }

    public void setCdcEstado(String cdcEstado) {
        this.cdcEstado = cdcEstado;
    }

    public String getCdcUser() {
        return cdcUser;
    }

    public void setCdcUser(String cdcUser) {
        this.cdcUser = cdcUser;
    }

    public Date getCdcFHR() {
        return cdcFHR;
    }

    public void setCdcFHR(Date cdcFHR) {
        this.cdcFHR = cdcFHR;
    }

    public String getCdcDocumento() {
        return cdcDocumento;
    }

    public void setCdcDocumento(String cdcDocumento) {
        this.cdcDocumento = cdcDocumento;
    }

    public String getCdcCaja() {
        return cdcCaja;
    }

    public void setCdcCaja(String cdcCaja) {
        this.cdcCaja = cdcCaja;
    }

    public Short getCdcCantidadDerecho() {
        return cdcCantidadDerecho;
    }

    public void setCdcCantidadDerecho(Short cdcCantidadDerecho) {
        this.cdcCantidadDerecho = cdcCantidadDerecho;
    }

    public Short getCdcCantidadCertificado() {
        return cdcCantidadCertificado;
    }

    public void setCdcCantidadCertificado(Short cdcCantidadCertificado) {
        this.cdcCantidadCertificado = cdcCantidadCertificado;
    }

    public String getCdcCertificado() {
        return cdcCertificado;
    }

    public void setCdcCertificado(String cdcCertificado) {
        this.cdcCertificado = cdcCertificado;
    }

    public String getCdcEstadoProceso() {
        return cdcEstadoProceso;
    }

    public void setCdcEstadoProceso(String cdcEstadoProceso) {
        this.cdcEstadoProceso = cdcEstadoProceso;
    }
    
    public Boolean getCdcActivo() {
        return cdcActivo;
    }

    public void setCdcActivo(Boolean cdcActivo) {
        this.cdcActivo = cdcActivo;
    }

    public Factura getFacId() {
        return facId;
    }

    public void setFacId(Factura facId) {
        this.facId = facId;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

     public String getCdcTipoGravamen() {
        return cdcTipoGravamen;
    }

    public void setCdcTipoGravamen(String cdcTipoGravamen) {
        this.cdcTipoGravamen = cdcTipoGravamen;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cdcId != null ? cdcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CertificadoCarga)) {
            return false;
        }
        CertificadoCarga other = (CertificadoCarga) object;
        if ((this.cdcId == null && other.cdcId != null) || (this.cdcId != null && !this.cdcId.equals(other.cdcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CertificadoCarga[ cdcId=" + cdcId + " ]";
    }

}
