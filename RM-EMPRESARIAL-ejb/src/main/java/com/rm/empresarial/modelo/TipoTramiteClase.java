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
@Table(name = "TipoTramiteClase")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoTramiteClase.findAll", query = "SELECT t FROM TipoTramiteClase t ORDER BY t.tclId.tclNombre")
    , @NamedQuery(name = "TipoTramiteClase.findByTdcId", query = "SELECT t FROM TipoTramiteClase t WHERE t.tdcId = :tdcId")
    , @NamedQuery(name = "TipoTramiteClase.findByTdcEstado", query = "SELECT t FROM TipoTramiteClase t WHERE t.tdcEstado = :tdcEstado")
    , @NamedQuery(name = "TipoTramiteClase.findByTdcUser", query = "SELECT t FROM TipoTramiteClase t WHERE t.tdcUser = :tdcUser")
    , @NamedQuery(name = "TipoTramiteClase.findByTdcFHR", query = "SELECT t FROM TipoTramiteClase t WHERE t.tdcFHR = :tdcFHR")})
public class TipoTramiteClase implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoTramiteClase.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TdcId")
    private Long tdcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TdcEstado")
    private String tdcEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TdcUser")
    private String tdcUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TdcFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tdcFHR;
    @JoinColumn(name = "TclId", referencedColumnName = "TclId")
    @ManyToOne(optional = false)
    private TipoPropiedadClase tclId;
    @JoinColumn(name = "TtrId", referencedColumnName = "TtrId")
    @ManyToOne(optional = false)
    private TipoTramite ttrId;

    public TipoTramiteClase() {
    }

    public TipoTramiteClase(Long tdcId) {
        this.tdcId = tdcId;
    }

    public TipoTramiteClase(Long tdcId, String tdcEstado, String tdcUser, Date tdcFHR) {
        this.tdcId = tdcId;
        this.tdcEstado = tdcEstado;
        this.tdcUser = tdcUser;
        this.tdcFHR = tdcFHR;
    }

    public Long getTdcId() {
        return tdcId;
    }

    public void setTdcId(Long tdcId) {
        this.tdcId = tdcId;
    }

    public String getTdcEstado() {
        return tdcEstado;
    }

    public void setTdcEstado(String tdcEstado) {
        this.tdcEstado = tdcEstado;
    }

    public String getTdcUser() {
        return tdcUser;
    }

    public void setTdcUser(String tdcUser) {
        this.tdcUser = tdcUser;
    }

    public Date getTdcFHR() {
        return tdcFHR;
    }

    public void setTdcFHR(Date tdcFHR) {
        this.tdcFHR = tdcFHR;
    }

    public TipoPropiedadClase getTclId() {
        return tclId;
    }

    public void setTclId(TipoPropiedadClase tclId) {
        this.tclId = tclId;
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
        hash += (tdcId != null ? tdcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoTramiteClase)) {
            return false;
        }
        TipoTramiteClase other = (TipoTramiteClase) object;
        if ((this.tdcId == null && other.tdcId != null) || (this.tdcId != null && !this.tdcId.equals(other.tdcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoTramiteClase[ tdcId=" + tdcId + " ]";
    }
    
}
