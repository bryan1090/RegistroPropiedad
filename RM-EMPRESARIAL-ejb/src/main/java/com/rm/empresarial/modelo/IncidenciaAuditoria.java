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
 * @author DJimenez
 */
@Entity
@Table(name = "IncidenciaAuditoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IncidenciaAuditoria.findAll", query = "SELECT i FROM IncidenciaAuditoria i")
    , @NamedQuery(name = "IncidenciaAuditoria.findByIadId", query = "SELECT i FROM IncidenciaAuditoria i WHERE i.iadId = :iadId")
    , @NamedQuery(name = "IncidenciaAuditoria.findByIadObservacion", query = "SELECT i FROM IncidenciaAuditoria i WHERE i.iadObservacion = :iadObservacion")
    , @NamedQuery(name = "IncidenciaAuditoria.findByIadTabla", query = "SELECT i FROM IncidenciaAuditoria i WHERE i.iadTabla = :iadTabla")
    , @NamedQuery(name = "IncidenciaAuditoria.findByIadAtributo", query = "SELECT i FROM IncidenciaAuditoria i WHERE i.iadAtributo = :iadAtributo")
    , @NamedQuery(name = "IncidenciaAuditoria.findByIadAnterior", query = "SELECT i FROM IncidenciaAuditoria i WHERE i.iadAnterior = :iadAnterior")
    , @NamedQuery(name = "IncidenciaAuditoria.findByIadActual", query = "SELECT i FROM IncidenciaAuditoria i WHERE i.iadActual = :iadActual")
    , @NamedQuery(name = "IncidenciaAuditoria.findByIadUser", query = "SELECT i FROM IncidenciaAuditoria i WHERE i.iadUser = :iadUser")
    , @NamedQuery(name = "IncidenciaAuditoria.findByIadFHR", query = "SELECT i FROM IncidenciaAuditoria i WHERE i.iadFHR = :iadFHR")
    , @NamedQuery(name = "IncidenciaAuditoria.findByIncId", query = "SELECT i FROM IncidenciaAuditoria i WHERE i.incId = :incId")})
public class IncidenciaAuditoria implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IadId")
    private Long iadId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "IadObservacion")
    private String iadObservacion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "IadTabla")
    private String iadTabla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "IadAtributo")
    private String iadAtributo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "IadAnterior")
    private String iadAnterior;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "IadActual")
    private String iadActual;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "IadUser")
    private String iadUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IadFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date iadFHR;
    @JoinColumn(name = "IncId", referencedColumnName = "IncId")
    @ManyToOne(optional = false)
    private Incidencia incId;

    public IncidenciaAuditoria() {
    }

    public IncidenciaAuditoria(Long iadId) {
        this.iadId = iadId;
    }

    public IncidenciaAuditoria(Long iadId, String iadObservacion, String iadTabla, String iadAtributo, String iadAnterior, String iadActual, String iadUser, Date iadFHR) {
        this.iadId = iadId;
        this.iadObservacion = iadObservacion;
        this.iadTabla = iadTabla;
        this.iadAtributo = iadAtributo;
        this.iadAnterior = iadAnterior;
        this.iadActual = iadActual;
        this.iadUser = iadUser;
        this.iadFHR = iadFHR;
    }

    public Long getIadId() {
        return iadId;
    }

    public void setIadId(Long iadId) {
        this.iadId = iadId;
    }

    public String getIadObservacion() {
        return iadObservacion;
    }

    public void setIadObservacion(String iadObservacion) {
        this.iadObservacion = iadObservacion;
    }

    public String getIadTabla() {
        return iadTabla;
    }

    public void setIadTabla(String iadTabla) {
        this.iadTabla = iadTabla;
    }

    public String getIadAtributo() {
        return iadAtributo;
    }

    public void setIadAtributo(String iadAtributo) {
        this.iadAtributo = iadAtributo;
    }

    public String getIadAnterior() {
        return iadAnterior;
    }

    public void setIadAnterior(String iadAnterior) {
        this.iadAnterior = iadAnterior;
    }

    public String getIadActual() {
        return iadActual;
    }

    public void setIadActual(String iadActual) {
        this.iadActual = iadActual;
    }

    public String getIadUser() {
        return iadUser;
    }

    public void setIadUser(String iadUser) {
        this.iadUser = iadUser;
    }

    public Date getIadFHR() {
        return iadFHR;
    }

    public void setIadFHR(Date iadFHR) {
        this.iadFHR = iadFHR;
    }

    public Incidencia getIncId() {
        return incId;
    }

    public void setIncId(Incidencia incId) {
        this.incId = incId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iadId != null ? iadId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncidenciaAuditoria)) {
            return false;
        }
        IncidenciaAuditoria other = (IncidenciaAuditoria) object;
        if ((this.iadId == null && other.iadId != null) || (this.iadId != null && !this.iadId.equals(other.iadId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.IncidenciaAuditoria[ iadId=" + iadId + " ]";
    }

}
