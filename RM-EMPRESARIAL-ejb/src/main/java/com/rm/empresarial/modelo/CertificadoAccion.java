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
@Table(name = "CertificadoAccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CertificadoAccion.findAll", query = "SELECT c FROM CertificadoAccion c")
    , @NamedQuery(name = "CertificadoAccion.findByCtaId", query = "SELECT c FROM CertificadoAccion c WHERE c.ctaId = :ctaId")
    , @NamedQuery(name = "CertificadoAccion.findByCtaNumeroDocumento", query = "SELECT c FROM CertificadoAccion c WHERE c.ctaNumeroDocumento = :ctaNumeroDocumento")
    , @NamedQuery(name = "CertificadoAccion.findByCtaEstadoProceso", query = "SELECT c FROM CertificadoAccion c WHERE c.ctaEstadoProceso = :ctaEstadoProceso")
    , @NamedQuery(name = "CertificadoAccion.findByCtaEstadoRegistro", query = "SELECT c FROM CertificadoAccion c WHERE c.ctaEstadoRegistro = :ctaEstadoRegistro")
    , @NamedQuery(name = "CertificadoAccion.findByCtaObservacion", query = "SELECT c FROM CertificadoAccion c WHERE c.ctaObservacion = :ctaObservacion")
    , @NamedQuery(name = "CertificadoAccion.findByCtaUser", query = "SELECT c FROM CertificadoAccion c WHERE c.ctaUser = :ctaUser")
    , @NamedQuery(name = "CertificadoAccion.findByCtaFHR", query = "SELECT c FROM CertificadoAccion c WHERE c.ctaFHR = :ctaFHR")
    , @NamedQuery(name = "CertificadoAccion.findByCerNumeroFiltrado", query = "SELECT c FROM CertificadoAccion c WHERE c.ctaNumeroDocumento LIKE :ctaNumeroDocumento AND c.ctaEstadoProceso = :ctaEstadoProceso ORDER BY c.ctaFHR DESC")
    , @NamedQuery(name = "CertificadoAccion.findByCerNumeroListaFiltrado", query = "SELECT c FROM CertificadoAccion c WHERE c.ctaNumeroDocumento LIKE :ctaNumeroDocumento AND c.ctaEstadoProceso = :ctaEstadoProceso ORDER BY c.ctaFHR DESC")})
public class CertificadoAccion implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String OBTENER_POR_CERTIFICADO_FILTRADO = "CertificadoAccion.findByCerNumeroFiltrado";
    public static final String LISTAR_POR_CERTIFICADO_FILTRADO = "CertificadoAccion.findByCerNumeroListaFiltrado";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CtaId")
    private Long ctaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CtaNumeroDocumento")
    private String ctaNumeroDocumento;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CtaEstadoProceso")
    private String ctaEstadoProceso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CtaEstadoRegistro")
    private String ctaEstadoRegistro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "CtaObservacion")
    private String ctaObservacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CtaUser")
    private String ctaUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CtaFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ctaFHR;
    @Size(max = 20)
    @Column(name = "CtaUsuarioAsignado")
    private String ctaUsuarioAsignado;

    public CertificadoAccion() {
    }

    public CertificadoAccion(Long ctaId) {
        this.ctaId = ctaId;
    }

    public CertificadoAccion(Long ctaId, String ctaNumeroDocumento, String ctaEstadoProceso, String ctaEstadoRegistro, String ctaObservacion, String ctaUser, Date ctaFHR) {
        this.ctaId = ctaId;
        this.ctaNumeroDocumento = ctaNumeroDocumento;
        this.ctaEstadoProceso = ctaEstadoProceso;
        this.ctaEstadoRegistro = ctaEstadoRegistro;
        this.ctaObservacion = ctaObservacion;
        this.ctaUser = ctaUser;
        this.ctaFHR = ctaFHR;
    }

    public Long getCtaId() {
        return ctaId;
    }

    public void setCtaId(Long ctaId) {
        this.ctaId = ctaId;
    }

    public String getCtaNumeroDocumento() {
        return ctaNumeroDocumento;
    }

    public void setCtaNumeroDocumento(String ctaNumeroDocumento) {
        this.ctaNumeroDocumento = ctaNumeroDocumento;
    }

    public String getCtaEstadoProceso() {
        return ctaEstadoProceso;
    }

    public void setCtaEstadoProceso(String ctaEstadoProceso) {
        this.ctaEstadoProceso = ctaEstadoProceso;
    }

    public String getCtaEstadoRegistro() {
        return ctaEstadoRegistro;
    }

    public void setCtaEstadoRegistro(String ctaEstadoRegistro) {
        this.ctaEstadoRegistro = ctaEstadoRegistro;
    }

    public String getCtaObservacion() {
        return ctaObservacion;
    }

    public void setCtaObservacion(String ctaObservacion) {
        this.ctaObservacion = ctaObservacion;
    }

    public String getCtaUser() {
        return ctaUser;
    }

    public void setCtaUser(String ctaUser) {
        this.ctaUser = ctaUser;
    }

    public Date getCtaFHR() {
        return ctaFHR;
    }

    public void setCtaFHR(Date ctaFHR) {
        this.ctaFHR = ctaFHR;
    }

    public String getCtaUsuarioAsignado() {
        return ctaUsuarioAsignado;
    }

    public void setCtaUsuarioAsignado(String ctaUsuarioAsignado) {
        this.ctaUsuarioAsignado = ctaUsuarioAsignado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ctaId != null ? ctaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CertificadoAccion)) {
            return false;
        }
        CertificadoAccion other = (CertificadoAccion) object;
        if ((this.ctaId == null && other.ctaId != null) || (this.ctaId != null && !this.ctaId.equals(other.ctaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.CertificadoAccion[ ctaId=" + ctaId + " ]";
    }

}
