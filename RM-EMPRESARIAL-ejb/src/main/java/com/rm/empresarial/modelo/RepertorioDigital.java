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
@Table(name = "RepertorioDigital")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RepertorioDigital.findAll", query = "SELECT r FROM RepertorioDigital r ORDER BY r.repNumero.repNumero")
    , @NamedQuery(name = "RepertorioDigital.findByRtdId", query = "SELECT r FROM RepertorioDigital r WHERE r.rtdId = :rtdId")
    , @NamedQuery(name = "RepertorioDigital.findByRtdNombreArchivo", query = "SELECT r FROM RepertorioDigital r WHERE r.rtdNombreArchivo = :rtdNombreArchivo")
    , @NamedQuery(name = "RepertorioDigital.findByRtdPath", query = "SELECT r FROM RepertorioDigital r WHERE r.rtdPath = :rtdPath")
    , @NamedQuery(name = "RepertorioDigital.findByRtdExtension", query = "SELECT r FROM RepertorioDigital r WHERE r.rtdExtension = :rtdExtension")
    , @NamedQuery(name = "RepertorioDigital.findByRtdUser", query = "SELECT r FROM RepertorioDigital r WHERE r.rtdUser = :rtdUser")
    , @NamedQuery(name = "RepertorioDigital.findByRtdFHR", query = "SELECT r FROM RepertorioDigital r WHERE r.rtdFHR = :rtdFHR")
    , @NamedQuery(name = "RepertorioDigital.findByRtdEstado", query = "SELECT r FROM RepertorioDigital r WHERE r.rtdEstado = :rtdEstado")})
public class RepertorioDigital implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO="RepertorioDigital.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "RtdId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rtdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "RtdNombreArchivo")
    private String rtdNombreArchivo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 512)
    @Column(name = "RtdPath")
    private String rtdPath;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "RtdExtension")
    private String rtdExtension;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RtdUser")
    private String rtdUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RtdFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rtdFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "RtdEstado")
    private String rtdEstado;
    @JoinColumn(name = "RepNumero", referencedColumnName = "RepNumero")
    @ManyToOne(optional = false)
    private Repertorio repNumero;

    public RepertorioDigital() {
    }

    public RepertorioDigital(Long rtdId) {
        this.rtdId = rtdId;
    }

    public RepertorioDigital(Long rtdId, String rtdNombreArchivo, String rtdPath, String rtdExtension, String rtdUser, Date rtdFHR, String rtdEstado) {
        this.rtdId = rtdId;
        this.rtdNombreArchivo = rtdNombreArchivo;
        this.rtdPath = rtdPath;
        this.rtdExtension = rtdExtension;
        this.rtdUser = rtdUser;
        this.rtdFHR = rtdFHR;
        this.rtdEstado = rtdEstado;
    }

    public Long getRtdId() {
        return rtdId;
    }

    public void setRtdId(Long rtdId) {
        this.rtdId = rtdId;
    }

    public String getRtdNombreArchivo() {
        return rtdNombreArchivo;
    }

    public void setRtdNombreArchivo(String rtdNombreArchivo) {
        this.rtdNombreArchivo = rtdNombreArchivo;
    }

    public String getRtdPath() {
        return rtdPath;
    }

    public void setRtdPath(String rtdPath) {
        this.rtdPath = rtdPath;
    }

    public String getRtdExtension() {
        return rtdExtension;
    }

    public void setRtdExtension(String rtdExtension) {
        this.rtdExtension = rtdExtension;
    }

    public String getRtdUser() {
        return rtdUser;
    }

    public void setRtdUser(String rtdUser) {
        this.rtdUser = rtdUser;
    }

    public Date getRtdFHR() {
        return rtdFHR;
    }

    public void setRtdFHR(Date rtdFHR) {
        this.rtdFHR = rtdFHR;
    }

    public String getRtdEstado() {
        return rtdEstado;
    }

    public void setRtdEstado(String rtdEstado) {
        this.rtdEstado = rtdEstado;
    }

    public Repertorio getRepNumero() {
        return repNumero;
    }

    public void setRepNumero(Repertorio repNumero) {
        this.repNumero = repNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rtdId != null ? rtdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RepertorioDigital)) {
            return false;
        }
        RepertorioDigital other = (RepertorioDigital) object;
        if ((this.rtdId == null && other.rtdId != null) || (this.rtdId != null && !this.rtdId.equals(other.rtdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.RepertorioDigital[ rtdId=" + rtdId + " ]";
    }
    
}
