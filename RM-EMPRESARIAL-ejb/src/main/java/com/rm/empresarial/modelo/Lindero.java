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
 * @author DJimenez
 */
@Entity
@Table(name = "Lindero")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lindero.findAll", query = "SELECT l FROM Lindero l ORDER BY l.ldrNombre")
    , @NamedQuery(name = "Lindero.findByLdrId", query = "SELECT l FROM Lindero l WHERE l.ldrId = :ldrId")
    , @NamedQuery(name = "Lindero.findByLdrNombre", query = "SELECT l FROM Lindero l WHERE l.ldrNombre = :ldrNombre")
    , @NamedQuery(name = "Lindero.findByLdrUser", query = "SELECT l FROM Lindero l WHERE l.ldrUser = :ldrUser")
    , @NamedQuery(name = "Lindero.findByLdrFHR", query = "SELECT l FROM Lindero l WHERE l.ldrFHR = :ldrFHR")})
public class Lindero implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Lindero.findAll";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LdrId")
    private Long ldrId;
    @Basic(optional = false)
    @NotNull
    @Size(max = 600)
    @Column(name = "LdrNombre")
    private String ldrNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "LdrUser")
    private String ldrUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "LdrFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ldrFHR;
    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;

    public Lindero() {
    }

    public Lindero(Long ldrId) {
        this.ldrId = ldrId;
    }

    public Lindero(Long ldrId, String ldrNombre, String ldrUser, Date ldrFHR) {
        this.ldrId = ldrId;
        this.ldrNombre = ldrNombre;
        this.ldrUser = ldrUser;
        this.ldrFHR = ldrFHR;
    }

    public Long getLdrId() {
        return ldrId;
    }

    public void setLdrId(Long ldrId) {
        this.ldrId = ldrId;
    }

    public String getLdrNombre() {
        return ldrNombre;
    }

    public void setLdrNombre(String ldrNombre) {
        this.ldrNombre = ldrNombre;
    }

    public String getLdrUser() {
        return ldrUser;
    }

    public void setLdrUser(String ldrUser) {
        this.ldrUser = ldrUser;
    }

    public Date getLdrFHR() {
        return ldrFHR;
    }

    public void setLdrFHR(Date ldrFHR) {
        this.ldrFHR = ldrFHR;
    }

    public Propiedad getPrdMatricula() {
        return prdMatricula;
    }

    public void setPrdMatricula(Propiedad prdMatricula) {
        this.prdMatricula = prdMatricula;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ldrId != null ? ldrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lindero)) {
            return false;
        }
        Lindero other = (Lindero) object;
        if ((this.ldrId == null && other.ldrId != null) || (this.ldrId != null && !this.ldrId.equals(other.ldrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Lindero[ ldrId=" + ldrId + " ]";
    }
    
}
