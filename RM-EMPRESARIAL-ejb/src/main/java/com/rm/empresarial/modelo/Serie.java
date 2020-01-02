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
@Table(name = "Serie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Serie.findAll", query = "SELECT s FROM Serie s")
    , @NamedQuery(name = "Serie.findBySerId", query = "SELECT s FROM Serie s WHERE s.serId = :serId")
    , @NamedQuery(name = "Serie.findBySerEstablecimiento", query = "SELECT s FROM Serie s WHERE s.serEstablecimiento = :serEstablecimiento")
    , @NamedQuery(name = "Serie.findBySerPuntoEmision", query = "SELECT s FROM Serie s WHERE s.serPuntoEmision = :serPuntoEmision")
    , @NamedQuery(name = "Serie.findBySerSecuenciaFactura", query = "SELECT s FROM Serie s WHERE s.serSecuenciaFactura = :serSecuenciaFactura")
    , @NamedQuery(name = "Serie.findBySerSecuencaNotaCredito", query = "SELECT s FROM Serie s WHERE s.serSecuencaNotaCredito = :serSecuencaNotaCredito")
    , @NamedQuery(name = "Serie.findBySerSecuenciaRetencion", query = "SELECT s FROM Serie s WHERE s.serSecuenciaRetencion = :serSecuenciaRetencion")
    , @NamedQuery(name = "Serie.findBySerUser", query = "SELECT s FROM Serie s WHERE s.serUser = :serUser")
    , @NamedQuery(name = "Serie.findBySerFHR", query = "SELECT s FROM Serie s WHERE s.serFHR = :serFHR")})
public class Serie implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Serie.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SerId")
    private Long serId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SerEstablecimiento")
    private short serEstablecimiento;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SerPuntoEmision")
    private short serPuntoEmision;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SerSecuenciaFactura")
    private int serSecuenciaFactura;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SerSecuencaNotaCredito")
    private int serSecuencaNotaCredito;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SerSecuenciaRetencion")
    private int serSecuenciaRetencion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SerUser")
    private String serUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SerFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date serFHR;
    @JoinColumn(name = "SucId", referencedColumnName = "SucId")
    @ManyToOne(optional = false)
    private Sucursal sucId;

    public Serie() {
    }

    public Serie(Long serId) {
        this.serId = serId;
    }

    public Serie(Long serId, short serEstablecimiento, short serPuntoEmision, int serSecuenciaFactura, int serSecuencaNotaCredito, int serSecuenciaRetencion, String serUser, Date serFHR) {
        this.serId = serId;
        this.serEstablecimiento = serEstablecimiento;
        this.serPuntoEmision = serPuntoEmision;
        this.serSecuenciaFactura = serSecuenciaFactura;
        this.serSecuencaNotaCredito = serSecuencaNotaCredito;
        this.serSecuenciaRetencion = serSecuenciaRetencion;
        this.serUser = serUser;
        this.serFHR = serFHR;
    }

    public Long getSerId() {
        return serId;
    }

    public void setSerId(Long serId) {
        this.serId = serId;
    }

    public short getSerEstablecimiento() {
        return serEstablecimiento;
    }

    public void setSerEstablecimiento(short serEstablecimiento) {
        this.serEstablecimiento = serEstablecimiento;
    }

    public short getSerPuntoEmision() {
        return serPuntoEmision;
    }

    public void setSerPuntoEmision(short serPuntoEmision) {
        this.serPuntoEmision = serPuntoEmision;
    }

    public int getSerSecuenciaFactura() {
        return serSecuenciaFactura;
    }

    public void setSerSecuenciaFactura(int serSecuenciaFactura) {
        this.serSecuenciaFactura = serSecuenciaFactura;
    }

    public int getSerSecuencaNotaCredito() {
        return serSecuencaNotaCredito;
    }

    public void setSerSecuencaNotaCredito(int serSecuencaNotaCredito) {
        this.serSecuencaNotaCredito = serSecuencaNotaCredito;
    }

    public int getSerSecuenciaRetencion() {
        return serSecuenciaRetencion;
    }

    public void setSerSecuenciaRetencion(int serSecuenciaRetencion) {
        this.serSecuenciaRetencion = serSecuenciaRetencion;
    }

    public String getSerUser() {
        return serUser;
    }

    public void setSerUser(String serUser) {
        this.serUser = serUser;
    }

    public Date getSerFHR() {
        return serFHR;
    }

    public void setSerFHR(Date serFHR) {
        this.serFHR = serFHR;
    }

    public Sucursal getSucId() {
        return sucId;
    }

    public void setSucId(Sucursal sucId) {
        this.sucId = sucId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (serId != null ? serId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Serie)) {
            return false;
        }
        Serie other = (Serie) object;
        if ((this.serId == null && other.serId != null) || (this.serId != null && !this.serId.equals(other.serId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Serie[ serId=" + serId + " ]";
    }
    
}
