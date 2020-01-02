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
 * @author Prueba
 */
@Entity
@Table(name = "SeleccionAtributo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SeleccionAtributo.findAll", query = "SELECT s FROM SeleccionAtributo s ORDER BY s.scaAtrAtributo")
    , @NamedQuery(name = "SeleccionAtributo.findByScaId", query = "SELECT s FROM SeleccionAtributo s WHERE s.scaId = :scaId")
    , @NamedQuery(name = "SeleccionAtributo.findByScaAtrAtributo", query = "SELECT s FROM SeleccionAtributo s WHERE s.scaAtrAtributo = :scaAtrAtributo")
    , @NamedQuery(name = "SeleccionAtributo.findByScaUser", query = "SELECT s FROM SeleccionAtributo s WHERE s.scaUser = :scaUser")
    , @NamedQuery(name = "SeleccionAtributo.findByScaFHR", query = "SELECT s FROM SeleccionAtributo s WHERE s.scaFHR = :scaFHR")})
public class SeleccionAtributo implements Serializable {

    public static final String LISTAR_TODOS = "SeleccionAtributo.findAll";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScaId")
    private Long scaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ScaAtrAtributo")
    private String scaAtrAtributo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ScaUser")
    private String scaUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ScaFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date scaFHR;
    @JoinColumn(name = "SlcId", referencedColumnName = "SlcId")
    @ManyToOne(optional = false)
    private Seleccion slcId;

    public SeleccionAtributo() {
    }

    public SeleccionAtributo(Long scaId) {
        this.scaId = scaId;
    }

    public SeleccionAtributo(Long scaId, String scaAtrAtributo, String scaUser, Date scaFHR) {
        this.scaId = scaId;
        this.scaAtrAtributo = scaAtrAtributo;
        this.scaUser = scaUser;
        this.scaFHR = scaFHR;
    }

    public Long getScaId() {
        return scaId;
    }

    public void setScaId(Long scaId) {
        this.scaId = scaId;
    }

    public String getScaAtrAtributo() {
        return scaAtrAtributo;
    }

    public void setScaAtrAtributo(String scaAtrAtributo) {
        this.scaAtrAtributo = scaAtrAtributo;
    }

    public String getScaUser() {
        return scaUser;
    }

    public void setScaUser(String scaUser) {
        this.scaUser = scaUser;
    }

    public Date getScaFHR() {
        return scaFHR;
    }

    public void setScaFHR(Date scaFHR) {
        this.scaFHR = scaFHR;
    }

    public Seleccion getSlcId() {
        return slcId;
    }

    public void setSlcId(Seleccion slcId) {
        this.slcId = slcId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scaId != null ? scaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SeleccionAtributo)) {
            return false;
        }
        SeleccionAtributo other = (SeleccionAtributo) object;
        if ((this.scaId == null && other.scaId != null) || (this.scaId != null && !this.scaId.equals(other.scaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.SeleccionAtributo[ scaId=" + scaId + " ]";
    }
    
}
