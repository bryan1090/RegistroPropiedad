/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author DJimenez
 */
@Entity
@Table(name = "FormularioWeb")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FormularioWeb.findAll", query = "SELECT f FROM FormularioWeb f")
    , @NamedQuery(name = "FormularioWeb.findByFlwId", query = "SELECT f FROM FormularioWeb f WHERE f.flwId = :flwId")
    , @NamedQuery(name = "FormularioWeb.findByFlwFecha", query = "SELECT f FROM FormularioWeb f WHERE f.flwFecha = :flwFecha")
    , @NamedQuery(name = "FormularioWeb.findByFlwObservacion", query = "SELECT f FROM FormularioWeb f WHERE f.flwObservacion = :flwObservacion")
    , @NamedQuery(name = "FormularioWeb.findByFlwFHR", query = "SELECT f FROM FormularioWeb f WHERE f.flwFHR = :flwFHR")
    , @NamedQuery(name = "FormularioWeb.findByFlwUser", query = "SELECT f FROM FormularioWeb f WHERE f.flwUser = :flwUser")
    , @NamedQuery(name = "FormularioWeb.findByFlwEstado", query = "SELECT f FROM FormularioWeb f WHERE f.flwEstado = :flwEstado")
    , @NamedQuery(name = "FormularioWeb.findByFlwCodigoIso", query = "SELECT f FROM FormularioWeb f WHERE f.flwCodigoIso = :flwCodigoIso")})
public class FormularioWeb implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "FlwId")
    private Integer flwId;
    @Basic(optional = false)
    @Column(name = "FlwFecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date flwFecha;
    @Size(max = 100)
    @Column(name = "FlwObservacion")
    private String flwObservacion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "FlwFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date flwFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "FlwUser")
    private String flwUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "FlwEstado")
    private String flwEstado;
    @Size(max = 100)
    @Column(name = "FlwCodigoIso")
    private String flwCodigoIso;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "flwId")
    private Collection<FormularioWebDetalle> formularioWebDetalleCollection;
    @JoinColumn(name = "TdwId", referencedColumnName = "TdwId")
    @ManyToOne
    private TipoDocumentoWeb tdwId;

    public FormularioWeb() {
    }

    public FormularioWeb(Integer flwId) {
        this.flwId = flwId;
    }

    public FormularioWeb(Integer flwId, Date flwFecha, Date flwFHR, String flwUser, String flwEstado) {
        this.flwId = flwId;
        this.flwFecha = flwFecha;
        this.flwFHR = flwFHR;
        this.flwUser = flwUser;
        this.flwEstado = flwEstado;
    }

    public Integer getFlwId() {
        return flwId;
    }

    public void setFlwId(Integer flwId) {
        this.flwId = flwId;
    }

    public Date getFlwFecha() {
        return flwFecha;
    }

    public void setFlwFecha(Date flwFecha) {
        this.flwFecha = flwFecha;
    }

    public String getFlwObservacion() {
        return flwObservacion;
    }

    public void setFlwObservacion(String flwObservacion) {
        this.flwObservacion = flwObservacion;
    }

    public Date getFlwFHR() {
        return flwFHR;
    }

    public void setFlwFHR(Date flwFHR) {
        this.flwFHR = flwFHR;
    }

    public String getFlwUser() {
        return flwUser;
    }

    public void setFlwUser(String flwUser) {
        this.flwUser = flwUser;
    }

    public String getFlwEstado() {
        return flwEstado;
    }

    public void setFlwEstado(String flwEstado) {
        this.flwEstado = flwEstado;
    }

    public String getFlwCodigoIso() {
        return flwCodigoIso;
    }

    public void setFlwCodigoIso(String flwCodigoIso) {
        this.flwCodigoIso = flwCodigoIso;
    }

    @XmlTransient
    public Collection<FormularioWebDetalle> getFormularioWebDetalleCollection() {
        return formularioWebDetalleCollection;
    }

    public void setFormularioWebDetalleCollection(Collection<FormularioWebDetalle> formularioWebDetalleCollection) {
        this.formularioWebDetalleCollection = formularioWebDetalleCollection;
    }

    public TipoDocumentoWeb getTdwId() {
        return tdwId;
    }

    public void setTdwId(TipoDocumentoWeb tdwId) {
        this.tdwId = tdwId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (flwId != null ? flwId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FormularioWeb)) {
            return false;
        }
        FormularioWeb other = (FormularioWeb) object;
        if ((this.flwId == null && other.flwId != null) || (this.flwId != null && !this.flwId.equals(other.flwId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.FormularioWeb[ flwId=" + flwId + " ]";
    }
    
}
