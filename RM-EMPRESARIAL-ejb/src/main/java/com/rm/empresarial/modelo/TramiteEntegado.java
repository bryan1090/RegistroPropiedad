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
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Prueba
 */
@Entity
@Table(name = "TramiteEntegado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TramiteEntegado.findAll", query = "SELECT t FROM TramiteEntegado t")
    , @NamedQuery(name = "TramiteEntegado.findByTmeId", query = "SELECT t FROM TramiteEntegado t WHERE t.tmeId = :tmeId")
    , @NamedQuery(name = "TramiteEntegado.findByTmeFHR", query = "SELECT t FROM TramiteEntegado t WHERE t.tmeFHR = :tmeFHR")
    , @NamedQuery(name = "TramiteEntegado.findByTmeDescripcion", query = "SELECT t FROM TramiteEntegado t WHERE t.tmeDescripcion = :tmeDescripcion")})
public class TramiteEntegado implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TmeId")
    private Long tmeId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TmeFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tmeFHR;
    @Size(max = 512)
    @Column(name = "TmeDescripcion")
    private String tmeDescripcion;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;
    @JoinColumn(name = "UsuId", referencedColumnName = "UsuId")
    @ManyToOne(optional = false)
    private Usuario usuId;
    @Getter
    @Setter
    @Size(max = 5)
    @Column(name = "TmeTipo")
    private String tmeTipo;


    public TramiteEntegado() {
    }

    public TramiteEntegado(Long tmeId) {
        this.tmeId = tmeId;
    }

    public TramiteEntegado(Long tmeId, Date tmeFHR) {
        this.tmeId = tmeId;
        this.tmeFHR = tmeFHR;
    }

    public Long getTmeId() {
        return tmeId;
    }

    public void setTmeId(Long tmeId) {
        this.tmeId = tmeId;
    }

    public Date getTmeFHR() {
        return tmeFHR;
    }

    public void setTmeFHR(Date tmeFHR) {
        this.tmeFHR = tmeFHR;
    }

    public String getTmeDescripcion() {
        return tmeDescripcion;
    }

    public void setTmeDescripcion(String tmeDescripcion) {
        this.tmeDescripcion = tmeDescripcion;
    }

    public Tramite getTraNumero() {
        return traNumero;
    }

    public void setTraNumero(Tramite traNumero) {
        this.traNumero = traNumero;
    }

    public Usuario getUsuId() {
        return usuId;
    }

    public void setUsuId(Usuario usuId) {
        this.usuId = usuId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tmeId != null ? tmeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TramiteEntegado)) {
            return false;
        }
        TramiteEntegado other = (TramiteEntegado) object;
        if ((this.tmeId == null && other.tmeId != null) || (this.tmeId != null && !this.tmeId.equals(other.tmeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.TramiteEntegado[ tmeId=" + tmeId + " ]";
    }
    
}
