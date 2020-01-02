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
@Table(name = "Seleccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Seleccion.findAll", query = "SELECT s FROM Seleccion s ORDER BY s.slcCodigo")
    , @NamedQuery(name = "Seleccion.findBySlcId", query = "SELECT s FROM Seleccion s WHERE s.slcId = :slcId")
    , @NamedQuery(name = "Seleccion.findBySlcCodigo", query = "SELECT s FROM Seleccion s WHERE s.slcCodigo = :slcCodigo")
    , @NamedQuery(name = "Seleccion.findBySlcDescripcion", query = "SELECT s FROM Seleccion s WHERE s.slcDescripcion = :slcDescripcion")
    , @NamedQuery(name = "Seleccion.findBySlcUser", query = "SELECT s FROM Seleccion s WHERE s.slcUser = :slcUser")
    , @NamedQuery(name = "Seleccion.findBySlcFHR", query = "SELECT s FROM Seleccion s WHERE s.slcFHR = :slcFHR")})
public class Seleccion implements Serializable {
public static final String LISTAR_TODOS = "Seleccion.findAll";
    public static final String LISTAR_PORID = "Seleccion.findBySlcId";
    private static final long serialVersionUID = 1L;
    
// @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SlcId")
    private Long slcId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "SlcCodigo")
    private String slcCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "SlcDescripcion")
    private String slcDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SlcUser")
    private String slcUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SlcFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date slcFHR;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "slcId")
    private List<SeleccionAtributo> seleccionAtributoList;

    public Seleccion() {
    }

    public Seleccion(Long slcId) {
        this.slcId = slcId;
    }

    public Seleccion(Long slcId, String slcCodigo, String slcDescripcion, String slcUser, Date slcFHR) {
        this.slcId = slcId;
        this.slcCodigo = slcCodigo;
        this.slcDescripcion = slcDescripcion;
        this.slcUser = slcUser;
        this.slcFHR = slcFHR;
    }

    public Long getSlcId() {
        return slcId;
    }

    public void setSlcId(Long slcId) {
        this.slcId = slcId;
    }

    public String getSlcCodigo() {
        return slcCodigo;
    }

    public void setSlcCodigo(String slcCodigo) {
        this.slcCodigo = slcCodigo;
    }

    public String getSlcDescripcion() {
        return slcDescripcion;
    }

    public void setSlcDescripcion(String slcDescripcion) {
        this.slcDescripcion = slcDescripcion;
    }

    public String getSlcUser() {
        return slcUser;
    }

    public void setSlcUser(String slcUser) {
        this.slcUser = slcUser;
    }

    public Date getSlcFHR() {
        return slcFHR;
    }

    public void setSlcFHR(Date slcFHR) {
        this.slcFHR = slcFHR;
    }

    @XmlTransient
    public List<SeleccionAtributo> getSeleccionAtributoList() {
        return seleccionAtributoList;
    }

    public void setSeleccionAtributoList(List<SeleccionAtributo> seleccionAtributoList) {
        this.seleccionAtributoList = seleccionAtributoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (slcId != null ? slcId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Seleccion)) {
            return false;
        }
        Seleccion other = (Seleccion) object;
        if ((this.slcId == null && other.slcId != null) || (this.slcId != null && !this.slcId.equals(other.slcId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.Seleccion[ slcId=" + slcId + " ]";
    }
    
}
