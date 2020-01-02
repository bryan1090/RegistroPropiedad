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
@Table(name = "ActaRazon")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActaRazon.findAll", query = "SELECT a FROM ActaRazon a")
    , @NamedQuery(name = "ActaRazon.findByAtrNumero", query = "SELECT a FROM ActaRazon a WHERE a.atrNumero = :atrNumero")
    , @NamedQuery(name = "ActaRazon.findByAtrRazonHtml", query = "SELECT a FROM ActaRazon a WHERE a.atrRazonHtml = :atrRazonHtml")
    , @NamedQuery(name = "ActaRazon.findByAtrRazonPdf", query = "SELECT a FROM ActaRazon a WHERE a.atrRazonPdf = :atrRazonPdf")
    , @NamedQuery(name = "ActaRazon.findByAtrUser", query = "SELECT a FROM ActaRazon a WHERE a.atrUser = :atrUser")
    , @NamedQuery(name = "ActaRazon.findByAtrFHR", query = "SELECT a FROM ActaRazon a WHERE a.atrFHR = :atrFHR")})
public class ActaRazon implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "AtrNumero")
    private Long atrNumero;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "AtrRazonHtml")
    private String atrRazonHtml;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "AtrRazonPdf")
    private String atrRazonPdf;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "AtrUser")
    private String atrUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AtrFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date atrFHR;
    @JoinColumn(name = "ActNumero", referencedColumnName = "ActNumero")
    @ManyToOne(optional = false)
    private Acta actNumero;

    public ActaRazon() {
    }

    public ActaRazon(Long atrNumero) {
        this.atrNumero = atrNumero;
    }

    public ActaRazon(Long atrNumero, String atrRazonHtml, String atrRazonPdf, String atrUser, Date atrFHR) {
        this.atrNumero = atrNumero;
        this.atrRazonHtml = atrRazonHtml;
        this.atrRazonPdf = atrRazonPdf;
        this.atrUser = atrUser;
        this.atrFHR = atrFHR;
    }

    public Long getAtrNumero() {
        return atrNumero;
    }

    public void setAtrNumero(Long atrNumero) {
        this.atrNumero = atrNumero;
    }

    public String getAtrRazonHtml() {
        return atrRazonHtml;
    }

    public void setAtrRazonHtml(String atrRazonHtml) {
        this.atrRazonHtml = atrRazonHtml;
    }

    public String getAtrRazonPdf() {
        return atrRazonPdf;
    }

    public void setAtrRazonPdf(String atrRazonPdf) {
        this.atrRazonPdf = atrRazonPdf;
    }

    public String getAtrUser() {
        return atrUser;
    }

    public void setAtrUser(String atrUser) {
        this.atrUser = atrUser;
    }

    public Date getAtrFHR() {
        return atrFHR;
    }

    public void setAtrFHR(Date atrFHR) {
        this.atrFHR = atrFHR;
    }

    public Acta getActNumero() {
        return actNumero;
    }

    public void setActNumero(Acta actNumero) {
        this.actNumero = actNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (atrNumero != null ? atrNumero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActaRazon)) {
            return false;
        }
        ActaRazon other = (ActaRazon) object;
        if ((this.atrNumero == null && other.atrNumero != null) || (this.atrNumero != null && !this.atrNumero.equals(other.atrNumero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.ActaRazon[ atrNumero=" + atrNumero + " ]";
    }
    
}
