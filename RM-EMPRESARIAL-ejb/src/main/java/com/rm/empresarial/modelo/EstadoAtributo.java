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
 * @author Richard
 */
@Entity
@Table(name = "EstadoAtributo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoAtributo.findAll", query = "SELECT e FROM EstadoAtributo e ORDER BY e.estAtrAtributo")
    , @NamedQuery(name = "EstadoAtributo.findByEstAtrId", query = "SELECT e FROM EstadoAtributo e WHERE e.estAtrId = :estAtrId")
    , @NamedQuery(name = "EstadoAtributo.findByEstAtrAtributo", query = "SELECT e FROM EstadoAtributo e WHERE e.estAtrAtributo = :estAtrAtributo")
    , @NamedQuery(name = "EstadoAtributo.findByEstAtrUser", query = "SELECT e FROM EstadoAtributo e WHERE e.estAtrUser = :estAtrUser")
    , @NamedQuery(name = "EstadoAtributo.findByEstAtrFHR", query = "SELECT e FROM EstadoAtributo e WHERE e.estAtrFHR = :estAtrFHR")})

public class EstadoAtributo implements Serializable {
public static final String LISTAR_TODOS = "EstadoAtributo.findAll";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EstAtrId")
    private Long estAtrId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "EstAtrAtributo")
    private String estAtrAtributo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "EstAtrUser")
    private String estAtrUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EstAtrFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date estAtrFHR;
    @JoinColumn(name = "EstId", referencedColumnName = "EstId")
    @ManyToOne(optional = false)
    private Estado estId;

    public EstadoAtributo() {
    }

    public EstadoAtributo(Long estAtrId) {
        this.estAtrId = estAtrId;
    }

    public EstadoAtributo(Long estAtrId, String estAtrAtributo, String estAtrUser, Date estAtrFHR) {
        this.estAtrId = estAtrId;
        this.estAtrAtributo = estAtrAtributo;
        this.estAtrUser = estAtrUser;
        this.estAtrFHR = estAtrFHR;
    }

    public Long getEstAtrId() {
        return estAtrId;
    }

    public void setEstAtrId(Long estAtrId) {
        this.estAtrId = estAtrId;
    }

    public String getEstAtrAtributo() {
        return estAtrAtributo;
    }

    public void setEstAtrAtributo(String estAtrAtributo) {
        this.estAtrAtributo = estAtrAtributo;
    }

    public String getEstAtrUser() {
        return estAtrUser;
    }

    public void setEstAtrUser(String estAtrUser) {
        this.estAtrUser = estAtrUser;
    }

    public Date getEstAtrFHR() {
        return estAtrFHR;
    }

    public void setEstAtrFHR(Date estAtrFHR) {
        this.estAtrFHR = estAtrFHR;
    }

    public Estado getEstId() {
        return estId;
    }

    public void setEstId(Estado estId) {
        this.estId = estId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (estAtrId != null ? estAtrId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoAtributo)) {
            return false;
        }
        EstadoAtributo other = (EstadoAtributo) object;
        if ((this.estAtrId == null && other.estAtrId != null) || (this.estAtrId != null && !this.estAtrId.equals(other.estAtrId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.EstadoAtributo[ estAtrId=" + estAtrId + " ]";
    }
    
}

