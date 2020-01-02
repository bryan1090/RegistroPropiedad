/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "TramiteSuspension")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteSuspension.findAll", query = "SELECT t FROM TramiteSuspension t")
    , @NamedQuery(name = "TramiteSuspension.findByTmsId", query = "SELECT t FROM TramiteSuspension t WHERE t.tmsId = :tmsId")
    , @NamedQuery(name = "TramiteSuspension.findByTmsUser", query = "SELECT t FROM TramiteSuspension t WHERE t.tmsUser = :tmsUser")
    , @NamedQuery(name = "TramiteSuspension.findByTmsFHR", query = "SELECT t FROM TramiteSuspension t WHERE t.tmsFHR = :tmsFHR")
    , @NamedQuery(name = "TramiteSuspension.findByTmsNombre", query = "SELECT t FROM TramiteSuspension t WHERE t.tmsNombre = :tmsNombre")
    , @NamedQuery(name = "TramiteSuspension.findByTmsTpsId", query = "SELECT t FROM TramiteSuspension t WHERE t.tmsTpsId = :tmsTpsId")
    , @NamedQuery(name = "TramiteSuspension.findByTmsEstado", query = "SELECT t FROM TramiteSuspension t WHERE t.tmsEstado = :tmsEstado")
    , @NamedQuery(name = "TramiteSuspension.findByTraNumero", query = "SELECT t FROM TramiteSuspension t WHERE t.traNumero.traNumero = :traNumero")
    , @NamedQuery(name = "TramiteSuspension.listarPorNumeroTramiteYporUsuario", query = "SELECT t FROM TramiteSuspension t JOIN TramiteUsuario tu on t.traNumero=tu.traNumero WHERE t.traNumero.traNumero = :traNumero AND tu.usuId.usuId = :usuId AND t.trmTipo = 'RVT'")
})
public class TramiteSuspension implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_POR_NUM_TRAMITE = "TramiteSuspension.findByTraNumero";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "TmsId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tmsId;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TmsUser")
    private String tmsUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TmsFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tmsFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "TmsNombre")
    private String tmsNombre;
    @Column(name = "TmsTpsId")
    private Long tmsTpsId;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = true)
    private Tramite traNumero;
    @Size(max = 2)
    @Column(name = "TmsEstado")
    private String tmsEstado;
     @Size(max = 3)
    @Column(name = "TrmTipo")
    private String trmTipo;

    public TramiteSuspension() {
    }

    public TramiteSuspension(Long tmsId) {
        this.tmsId = tmsId;
    }

    public TramiteSuspension(Long tmsId, String tmsUser, Date tmsFHR, String tmsNombre) {
        this.tmsId = tmsId;
        this.tmsUser = tmsUser;
        this.tmsFHR = tmsFHR;
        this.tmsNombre = tmsNombre;
    }

    public Long getTmsId() {
        return tmsId;
    }

    public void setTmsId(Long tmsId) {
        this.tmsId = tmsId;
    }

    public String getTmsUser() {
        return tmsUser;
    }

    public void setTmsUser(String tmsUser) {
        this.tmsUser = tmsUser;
    }

    public Date getTmsFHR() {
        return tmsFHR;
    }

    public void setTmsFHR(Date tmsFHR) {
        this.tmsFHR = tmsFHR;
    }

    public String getTmsNombre() {
        return tmsNombre;
    }

    public void setTmsNombre(String tmsNombre) {
        this.tmsNombre = tmsNombre;
    }

    public Long getTmsTpsId() {
        return tmsTpsId;
    }

    public void setTmsTpsId(Long tmsTpsId) {
        this.tmsTpsId = tmsTpsId;
    }

    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
    }

    public String getTmsEstado() {
        return tmsEstado;
    }

    public void setTmsEstado(String tmsEstado) {
        this.tmsEstado = tmsEstado;
    }
    
     public String getTrmTipo() {
        return trmTipo;
    }

    public void setTrmTipo(String trmTipo) {
        this.trmTipo = trmTipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tmsId != null ? tmsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteSuspension)) {
            return false;
        }
        TramiteSuspension other = (TramiteSuspension) object;
        if ((this.tmsId == null && other.tmsId != null) || (this.tmsId != null && !this.tmsId.equals(other.tmsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TramiteSuspension[ tmsId=" + tmsId + " ]";
    }

}
