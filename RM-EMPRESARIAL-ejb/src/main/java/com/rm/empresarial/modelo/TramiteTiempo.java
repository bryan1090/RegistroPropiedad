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
@Table(name = "TramiteTiempo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteTiempo.findAll", query = "SELECT t FROM TramiteTiempo t")
    , @NamedQuery(name = "TramiteTiempo.findByTtiId", query = "SELECT t FROM TramiteTiempo t WHERE t.ttiId = :ttiId")
    , @NamedQuery(name = "TramiteTiempo.findByTtiUnidadTiempo", query = "SELECT t FROM TramiteTiempo t WHERE t.ttiUnidadTiempo = :ttiUnidadTiempo")
    , @NamedQuery(name = "TramiteTiempo.findByTtiTiempo", query = "SELECT t FROM TramiteTiempo t WHERE t.ttiTiempo = :ttiTiempo")
    , @NamedQuery(name = "TramiteTiempo.findByTtiTipo", query = "SELECT t FROM TramiteTiempo t WHERE t.ttiTipo = :ttiTipo")
    , @NamedQuery(name = "TramiteTiempo.findByTtiFechaEntrega", query = "SELECT t FROM TramiteTiempo t WHERE t.ttiFechaEntrega = :ttiFechaEntrega")})
public class TramiteTiempo implements Serializable {

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "TtiTiempo")
    private Long ttiTiempo;
    @JoinColumn(name = "TtrId", referencedColumnName = "TtrId")
    @ManyToOne(optional = false)
    private TipoTramite ttrId;

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TtiId")
    private Long ttiId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "TtiUnidadTiempo")
    private String ttiUnidadTiempo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TtiTipo")
    private String ttiTipo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TtiFechaEntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ttiFechaEntrega;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;

    public TramiteTiempo() {
    }

    public TramiteTiempo(Long ttiId) {
        this.ttiId = ttiId;
    }

    public TramiteTiempo(Long ttiId, String ttiUnidadTiempo, Long ttiTiempo, String ttiTipo, Date ttiFechaEntrega) {
        this.ttiId = ttiId;
        this.ttiUnidadTiempo = ttiUnidadTiempo;
        this.ttiTiempo = ttiTiempo;
        this.ttiTipo = ttiTipo;
        this.ttiFechaEntrega = ttiFechaEntrega;
    }

    public Long getTtiId() {
        return ttiId;
    }

    public void setTtiId(Long ttiId) {
        this.ttiId = ttiId;
    }

    public String getTtiUnidadTiempo() {
        return ttiUnidadTiempo;
    }

    public void setTtiUnidadTiempo(String ttiUnidadTiempo) {
        this.ttiUnidadTiempo = ttiUnidadTiempo;
    }


    public String getTtiTipo() {
        return ttiTipo;
    }

    public void setTtiTipo(String ttiTipo) {
        this.ttiTipo = ttiTipo;
    }

    public Date getTtiFechaEntrega() {
        return ttiFechaEntrega;
    }

    public void setTtiFechaEntrega(Date ttiFechaEntrega) {
        this.ttiFechaEntrega = ttiFechaEntrega;
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
        hash += (ttiId != null ? ttiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteTiempo)) {
            return false;
        }
        TramiteTiempo other = (TramiteTiempo) object;
        if ((this.ttiId == null && other.ttiId != null) || (this.ttiId != null && !this.ttiId.equals(other.ttiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TramiteTiempo[ ttiId=" + ttiId + " ]";
    }

    public Long getTtiTiempo() {
        return ttiTiempo;
    }

    public void setTtiTiempo(Long ttiTiempo) {
        this.ttiTiempo = ttiTiempo;
    }

    public TipoTramite getTtrId() {
        return ttrId;
    }

    public void setTtrId(TipoTramite ttrId) {
        this.ttrId = ttrId;
    }
    
}
