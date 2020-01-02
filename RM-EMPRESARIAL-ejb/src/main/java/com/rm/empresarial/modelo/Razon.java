
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
@Table(name = "Razon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Razon.findAll", query = "SELECT r FROM Razon r")
    , @NamedQuery(name = "Razon.findByRazId", query = "SELECT r FROM Razon r WHERE r.razId = :razId")
    , @NamedQuery(name = "Razon.findByRazHtml", query = "SELECT r FROM Razon r WHERE r.razHtml = :razHtml")
    , @NamedQuery(name = "Razon.findByRazUser", query = "SELECT r FROM Razon r WHERE r.razUser = :razUser")
    , @NamedQuery(name = "Razon.findByRazFHR", query = "SELECT r FROM Razon r WHERE r.razFHR = :razFHR")})
public class Razon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "RazId")
    private Long razId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "RazHtml")
    private String razHtml;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "RazUser")
    private String razUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "RazFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date razFHR;
    @JoinColumn(name = "TraNumero", referencedColumnName = "TraNumero")
    @ManyToOne(optional = false)
    private Tramite traNumero;

    public Razon() {
    }

    public Razon(Long razId) {
        this.razId = razId;
    }

    public Razon(Long razId, String razHtml, String razUser, Date razFHR) {
        this.razId = razId;
        this.razHtml = razHtml;
        this.razUser = razUser;
        this.razFHR = razFHR;
    }

    public Long getRazId() {
        return razId;
    }

    public void setRazId(Long razId) {
        this.razId = razId;
    }

    public String getRazHtml() {
        return razHtml;
    }

    public void setRazHtml(String razHtml) {
        this.razHtml = razHtml;
    }

    public String getRazUser() {
        return razUser;
    }

    public void setRazUser(String razUser) {
        this.razUser = razUser;
    }

    public Date getRazFHR() {
        return razFHR;
    }

    public void setRazFHR(Date razFHR) {
        this.razFHR = razFHR;
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
        hash += (razId != null ? razId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Razon)) {
            return false;
        }
        Razon other = (Razon) object;
        if ((this.razId == null && other.razId != null) || (this.razId != null && !this.razId.equals(other.razId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.Razon[ razId=" + razId + " ]";
    }
    
}
