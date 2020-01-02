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
@Table(name = "IncidenciaHistorico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "IncidenciaHistorico.findAll", query = "SELECT i FROM IncidenciaHistorico i")
    , @NamedQuery(name = "IncidenciaHistorico.findByIchId", query = "SELECT i FROM IncidenciaHistorico i WHERE i.ichId = :ichId")
    , @NamedQuery(name = "IncidenciaHistorico.findByIchDescripcion", query = "SELECT i FROM IncidenciaHistorico i WHERE i.ichDescripcion = :ichDescripcion")
    , @NamedQuery(name = "IncidenciaHistorico.findByIchUser", query = "SELECT i FROM IncidenciaHistorico i WHERE i.ichUser = :ichUser")
    , @NamedQuery(name = "IncidenciaHistorico.findByIchFHR", query = "SELECT i FROM IncidenciaHistorico i WHERE i.ichFHR = :ichFHR")
    , @NamedQuery(name = "IncidenciaHistorico.findByIncId", query = "SELECT i FROM IncidenciaHistorico i WHERE i.incId = :incId")})
public class IncidenciaHistorico implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IchId")
    private Long ichId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "IchDescripcion")
    private String ichDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "IchUser")
    private String ichUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IchFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ichFHR;
    @JoinColumn(name = "IncId", referencedColumnName = "IncId")
    @ManyToOne(optional = false)
    private Incidencia incId;

    public IncidenciaHistorico() {
    }

    public IncidenciaHistorico(Long ichId) {
        this.ichId = ichId;
    }

    public IncidenciaHistorico(Long ichId, String ichDescripcion, String ichUser, Date ichFHR) {
        this.ichId = ichId;
        this.ichDescripcion = ichDescripcion;
        this.ichUser = ichUser;
        this.ichFHR = ichFHR;
    }

    public Long getIchId() {
        return ichId;
    }

    public void setIchId(Long ichId) {
        this.ichId = ichId;
    }

    public String getIchDescripcion() {
        return ichDescripcion;
    }

    public void setIchDescripcion(String ichDescripcion) {
        this.ichDescripcion = ichDescripcion;
    }

    public String getIchUser() {
        return ichUser;
    }

    public void setIchUser(String ichUser) {
        this.ichUser = ichUser;
    }

    public Date getIchFHR() {
        return ichFHR;
    }

    public void setIchFHR(Date ichFHR) {
        this.ichFHR = ichFHR;
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
        hash += (ichId != null ? ichId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof IncidenciaHistorico)) {
            return false;
        }
        IncidenciaHistorico other = (IncidenciaHistorico) object;
        if ((this.ichId == null && other.ichId != null) || (this.ichId != null && !this.ichId.equals(other.ichId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.IncidenciaHistorico[ ichId=" + ichId + " ]";
    }

}
