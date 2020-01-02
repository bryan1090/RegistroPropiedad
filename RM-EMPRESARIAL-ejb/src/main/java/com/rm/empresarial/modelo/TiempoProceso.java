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
@Table(name = "TiempoProceso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TiempoProceso.findAll", query = "SELECT t FROM TiempoProceso t")
    , @NamedQuery(name = "TiempoProceso.findByTpoId", query = "SELECT t FROM TiempoProceso t WHERE t.tpoId = :tpoId")
    , @NamedQuery(name = "TiempoProceso.findByTpoUnidadTiempo", query = "SELECT t FROM TiempoProceso t WHERE t.tpoUnidadTiempo = :tpoUnidadTiempo")
    , @NamedQuery(name = "TiempoProceso.findByTpoTiempo", query = "SELECT t FROM TiempoProceso t WHERE t.tpoTiempo = :tpoTiempo")
    , @NamedQuery(name = "TiempoProceso.findByTpoEstado", query = "SELECT t FROM TiempoProceso t WHERE t.tpoEstado = :tpoEstado")
    , @NamedQuery(name = "TiempoProceso.findByTpoFHR", query = "SELECT t FROM TiempoProceso t WHERE t.tpoFHR = :tpoFHR")
    , @NamedQuery(name = "TiempoProceso.findByTpoUser", query = "SELECT t FROM TiempoProceso t WHERE t.tpoUser = :tpoUser")})
public class TiempoProceso implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TpoId")
    private Long tpoId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TpoUnidadTiempo")
    private String tpoUnidadTiempo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpoTiempo")
    private BigDecimal tpoTiempo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TpoEstado")
    private String tpoEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpoFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tpoFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TpoUser")
    private String tpoUser;
    @JoinColumn(name = "EstId", referencedColumnName = "EstId")
    @ManyToOne(optional = false)
    private Estado estId;
    @JoinColumn(name = "TtrId", referencedColumnName = "TtrId")
    @ManyToOne(optional = false)
    private TipoTramite ttrId;

    public TiempoProceso() {
    }

    public TiempoProceso(Long tpoId) {
        this.tpoId = tpoId;
    }

    public TiempoProceso(Long tpoId, String tpoUnidadTiempo, BigDecimal tpoTiempo, String tpoEstado, Date tpoFHR, String tpoUser) {
        this.tpoId = tpoId;
        this.tpoUnidadTiempo = tpoUnidadTiempo;
        this.tpoTiempo = tpoTiempo;
        this.tpoEstado = tpoEstado;
        this.tpoFHR = tpoFHR;
        this.tpoUser = tpoUser;
    }

    public Long getTpoId() {
        return tpoId;
    }

    public void setTpoId(Long tpoId) {
        this.tpoId = tpoId;
    }

    public String getTpoUnidadTiempo() {
        return tpoUnidadTiempo;
    }

    public void setTpoUnidadTiempo(String tpoUnidadTiempo) {
        this.tpoUnidadTiempo = tpoUnidadTiempo;
    }

    public BigDecimal getTpoTiempo() {
        return tpoTiempo;
    }

    public void setTpoTiempo(BigDecimal tpoTiempo) {
        this.tpoTiempo = tpoTiempo;
    }

    public String getTpoEstado() {
        return tpoEstado;
    }

    public void setTpoEstado(String tpoEstado) {
        this.tpoEstado = tpoEstado;
    }

    public Date getTpoFHR() {
        return tpoFHR;
    }

    public void setTpoFHR(Date tpoFHR) {
        this.tpoFHR = tpoFHR;
    }

    public String getTpoUser() {
        return tpoUser;
    }

    public void setTpoUser(String tpoUser) {
        this.tpoUser = tpoUser;
    }

    public Estado getEstId() {
        return estId;
    }

    public void setEstId(Estado estId) {
        this.estId = estId;
    }

    public TipoTramite getTtrId() {
        return ttrId;
    }

    public void setTtrId(TipoTramite ttrId) {
        this.ttrId = ttrId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpoId != null ? tpoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TiempoProceso)) {
            return false;
        }
        TiempoProceso other = (TiempoProceso) object;
        if ((this.tpoId == null && other.tpoId != null) || (this.tpoId != null && !this.tpoId.equals(other.tpoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.TiempoProceso[ tpoId=" + tpoId + " ]";
    }
    
}
