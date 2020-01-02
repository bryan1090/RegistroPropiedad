/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author Prueba
 */
@Entity
@Table(name = "Config")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Config.findAll", query = "SELECT c FROM Config c ORDER BY c.configDescripcion")
    , @NamedQuery(name = "Config.findByConfigId", query = "SELECT c FROM Config c WHERE c.configId = :configId")
    , @NamedQuery(name = "Config.findByConfigDescripcion", query = "SELECT c FROM Config c WHERE c.configDescripcion = :configDescripcion")
    , @NamedQuery(name = "Config.findByConfigUser", query = "SELECT c FROM Config c WHERE c.configUser = :configUser")
    , @NamedQuery(name = "Config.findByConfigFHR", query = "SELECT c FROM Config c WHERE c.configFHR = :configFHR")})

public class Config implements Serializable {
    public static final String LISTAR_TODOS = "Config.findAll";
public static final String LISTAR_PORID = "Config.findByConfigId";

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ConfigId")
    private Short configId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ConfigDescripcion")
    private String configDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ConfigUser")
    private String configUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ConfigFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date configFHR;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "configId")
    private List<ConfigDetalle> configDetalleList;

    public Config() {
    }

    public Config(Short configId) {
        this.configId = configId;
    }

    public Config(Short configId, String configDescripcion, String configUser, Date configFHR) {
        this.configId = configId;
        this.configDescripcion = configDescripcion;
        this.configUser = configUser;
        this.configFHR = configFHR;
    }

    public Short getConfigId() {
        return configId;
    }

    public void setConfigId(Short configId) {
        this.configId = configId;
    }

    public String getConfigDescripcion() {
        return configDescripcion;
    }

    public void setConfigDescripcion(String configDescripcion) {
        this.configDescripcion = configDescripcion;
    }

    public String getConfigUser() {
        return configUser;
    }

    public void setConfigUser(String configUser) {
        this.configUser = configUser;
    }

    public Date getConfigFHR() {
        return configFHR;
    }

    public void setConfigFHR(Date configFHR) {
        this.configFHR = configFHR;
    }

    @XmlTransient
    public List<ConfigDetalle> getConfigDetalleList() {
        return configDetalleList;
    }

    public void setConfigDetalleList(List<ConfigDetalle> configDetalleList) {
        this.configDetalleList = configDetalleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (configId != null ? configId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Config)) {
            return false;
        }
        Config other = (Config) object;
        if ((this.configId == null && other.configId != null) || (this.configId != null && !this.configId.equals(other.configId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Config[ configId=" + configId + " ]";
    }
    
}
