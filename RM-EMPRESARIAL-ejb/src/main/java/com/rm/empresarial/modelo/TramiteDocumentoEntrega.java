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
@Table(name = "TramiteDocumentoEntrega")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteDocumentoEntrega.findAll", query = "SELECT t FROM TramiteDocumentoEntrega t")
    , @NamedQuery(name = "TramiteDocumentoEntrega.findByTdaId", query = "SELECT t FROM TramiteDocumentoEntrega t WHERE t.tdaId = :tdaId")
    , @NamedQuery(name = "TramiteDocumentoEntrega.findByTdaDescripcion", query = "SELECT t FROM TramiteDocumentoEntrega t WHERE t.tdaDescripcion = :tdaDescripcion")
    , @NamedQuery(name = "TramiteDocumentoEntrega.findByTdaUser", query = "SELECT t FROM TramiteDocumentoEntrega t WHERE t.tdaUser = :tdaUser")
    , @NamedQuery(name = "TramiteDocumentoEntrega.findByTdaFHR", query = "SELECT t FROM TramiteDocumentoEntrega t WHERE t.tdaFHR = :tdaFHR")})
public class TramiteDocumentoEntrega implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TdaId")
    private Long tdaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "TdaDescripcion")
    private String tdaDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TdaUser")
    private String tdaUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdaFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tdaFHR;
    @JoinColumn(name = "TtrId", referencedColumnName = "TtrId")
    @ManyToOne(optional = false)
    private TipoTramite ttrId;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;

    public TramiteDocumentoEntrega() {
    }

    public TramiteDocumentoEntrega(Long tdaId) {
        this.tdaId = tdaId;
    }

    public TramiteDocumentoEntrega(Long tdaId, String tdaDescripcion, String tdaUser, Date tdaFHR) {
        this.tdaId = tdaId;
        this.tdaDescripcion = tdaDescripcion;
        this.tdaUser = tdaUser;
        this.tdaFHR = tdaFHR;
    }

    public Long getTdaId() {
        return tdaId;
    }

    public void setTdaId(Long tdaId) {
        this.tdaId = tdaId;
    }

    public String getTdaDescripcion() {
        return tdaDescripcion;
    }

    public void setTdaDescripcion(String tdaDescripcion) {
        this.tdaDescripcion = tdaDescripcion;
    }

    public String getTdaUser() {
        return tdaUser;
    }

    public void setTdaUser(String tdaUser) {
        this.tdaUser = tdaUser;
    }

    public Date getTdaFHR() {
        return tdaFHR;
    }

    public void setTdaFHR(Date tdaFHR) {
        this.tdaFHR = tdaFHR;
    }

    public TipoTramite getTtrId() {
        return ttrId;
    }

    public void setTtrId(TipoTramite ttrId) {
        this.ttrId = ttrId;
    }

    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tdaId != null ? tdaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteDocumentoEntrega)) {
            return false;
        }
        TramiteDocumentoEntrega other = (TramiteDocumentoEntrega) object;
        if ((this.tdaId == null && other.tdaId != null) || (this.tdaId != null && !this.tdaId.equals(other.tdaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TramiteDocumentoEntrega[ tdaId=" + tdaId + " ]";
    }
    
}
