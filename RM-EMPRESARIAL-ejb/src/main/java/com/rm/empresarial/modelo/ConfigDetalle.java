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
 * @author Prueba
 */
@Entity
@Table(name = "ConfigDetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfigDetalle.findAll", query = "SELECT c FROM ConfigDetalle c ORDER BY c.configDetalleTexto")
    , @NamedQuery(name = "ConfigDetalle.findByConfigDetalleCodigo", query = "SELECT c FROM ConfigDetalle c WHERE c.configDetalleCodigo = :configDetalleCodigo")
    , @NamedQuery(name = "ConfigDetalle.findByConfigDetalleTexto", query = "SELECT c FROM ConfigDetalle c WHERE c.configDetalleTexto = :configDetalleTexto")
    , @NamedQuery(name = "ConfigDetalle.findByConfigDetalleNumero", query = "SELECT c FROM ConfigDetalle c WHERE c.configDetalleNumero = :configDetalleNumero")
    , @NamedQuery(name = "ConfigDetalle.findByConfigDetalleFecha", query = "SELECT c FROM ConfigDetalle c WHERE c.configDetalleFecha = :configDetalleFecha")
    , @NamedQuery(name = "ConfigDetalle.findByConfigDetalleUser", query = "SELECT c FROM ConfigDetalle c WHERE c.configDetalleUser = :configDetalleUser")
    , @NamedQuery(name = "ConfigDetalle.findByConfigDetalleFHR", query = "SELECT c FROM ConfigDetalle c WHERE c.configDetalleFHR = :configDetalleFHR")})

public class ConfigDetalle implements Serializable {
public static final String LISTAR_TODOS = "ConfigDetalle.findAll";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConfigDetalleCodigo")
    private Long configDetalleCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ConfigDetalleTexto")
    private String configDetalleTexto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ConfigDetalleNumero")
    private BigDecimal configDetalleNumero;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ConfigDetalleFecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date configDetalleFecha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ConfigDetalleUser")
    private String configDetalleUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ConfigDetalleFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date configDetalleFHR;
    @JoinColumn(name = "ConfigId", referencedColumnName = "ConfigId")
    @ManyToOne(optional = false)
    private Config configId;

    public ConfigDetalle() {
    }

    public ConfigDetalle(Long configDetalleCodigo) {
        this.configDetalleCodigo = configDetalleCodigo;
    }

    public ConfigDetalle(Long configDetalleCodigo, String configDetalleTexto, BigDecimal configDetalleNumero, Date configDetalleFecha, String configDetalleUser, Date configDetalleFHR) {
        this.configDetalleCodigo = configDetalleCodigo;
        this.configDetalleTexto = configDetalleTexto;
        this.configDetalleNumero = configDetalleNumero;
        this.configDetalleFecha = configDetalleFecha;
        this.configDetalleUser = configDetalleUser;
        this.configDetalleFHR = configDetalleFHR;
    }

    public Long getConfigDetalleCodigo() {
        return configDetalleCodigo;
    }

    public void setConfigDetalleCodigo(Long configDetalleCodigo) {
        this.configDetalleCodigo = configDetalleCodigo;
    }

    public String getConfigDetalleTexto() {
        return configDetalleTexto;
    }

    public void setConfigDetalleTexto(String configDetalleTexto) {
        this.configDetalleTexto = configDetalleTexto;
    }

    public BigDecimal getConfigDetalleNumero() {
        return configDetalleNumero;
    }

    public void setConfigDetalleNumero(BigDecimal configDetalleNumero) {
        this.configDetalleNumero = configDetalleNumero;
    }

    public Date getConfigDetalleFecha() {
        return configDetalleFecha;
    }

    public void setConfigDetalleFecha(Date configDetalleFecha) {
        this.configDetalleFecha = configDetalleFecha;
    }

    public String getConfigDetalleUser() {
        return configDetalleUser;
    }

    public void setConfigDetalleUser(String configDetalleUser) {
        this.configDetalleUser = configDetalleUser;
    }

    public Date getConfigDetalleFHR() {
        return configDetalleFHR;
    }

    public void setConfigDetalleFHR(Date configDetalleFHR) {
        this.configDetalleFHR = configDetalleFHR;
    }

    public Config getConfigId() {
        return configId;
    }

    public void setConfigId(Config configId) {
        this.configId = configId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configDetalleCodigo != null ? configDetalleCodigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfigDetalle)) {
            return false;
        }
        ConfigDetalle other = (ConfigDetalle) object;
        if ((this.configDetalleCodigo == null && other.configDetalleCodigo != null) || (this.configDetalleCodigo != null && !this.configDetalleCodigo.equals(other.configDetalleCodigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.ConfigDetalle[ configDetalleCodigo=" + configDetalleCodigo + " ]";
    }
    
}
