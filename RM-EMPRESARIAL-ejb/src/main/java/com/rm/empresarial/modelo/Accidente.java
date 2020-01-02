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
 * @author Marco
 */
@Entity
@Table(name = "Accidente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Accidente.findAll", query = "SELECT a FROM Accidente a")
    , @NamedQuery(name = "Accidente.findByAccId", query = "SELECT a FROM Accidente a WHERE a.accId = :accId")
    , @NamedQuery(name = "Accidente.findByAccDescripcion", query = "SELECT a FROM Accidente a WHERE a.accDescripcion = :accDescripcion")
    , @NamedQuery(name = "Accidente.findByAccEstado", query = "SELECT a FROM Accidente a WHERE a.accEstado = :accEstado")
    , @NamedQuery(name = "Accidente.findByAccFHR", query = "SELECT a FROM Accidente a WHERE a.accFHR = :accFHR")
    , @NamedQuery(name = "Accidente.findByAccUser", query = "SELECT a FROM Accidente a WHERE a.accUser = :accUser")
    , @NamedQuery(name = "Accidente.findByAccDocumento", query = "SELECT a FROM Accidente a WHERE a.accDocumento = :accDocumento")})
public class Accidente implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccId")
    private BigDecimal accId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "AccDescripcion")
    private String accDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "AccEstado")
    private String accEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AccFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date accFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "AccUser")
    private String accUser;
    @Size(max = 100)
    @Column(name = "AccDocumento")
    private String accDocumento;
    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;
    @JoinColumn(name = "RepNumero", referencedColumnName = "RepNumero")
    @ManyToOne(optional = false)
    private Repertorio repNumero;

    public Accidente() {
    }

    public Accidente(BigDecimal accId) {
        this.accId = accId;
    }

    public Accidente(BigDecimal accId, String accDescripcion, String accEstado, Date accFHR, String accUser) {
        this.accId = accId;
        this.accDescripcion = accDescripcion;
        this.accEstado = accEstado;
        this.accFHR = accFHR;
        this.accUser = accUser;
    }

    public BigDecimal getAccId() {
        return accId;
    }

    public void setAccId(BigDecimal accId) {
        this.accId = accId;
    }

    public String getAccDescripcion() {
        return accDescripcion;
    }

    public void setAccDescripcion(String accDescripcion) {
        this.accDescripcion = accDescripcion;
    }

    public String getAccEstado() {
        return accEstado;
    }

    public void setAccEstado(String accEstado) {
        this.accEstado = accEstado;
    }

    public Date getAccFHR() {
        return accFHR;
    }

    public void setAccFHR(Date accFHR) {
        this.accFHR = accFHR;
    }

    public String getAccUser() {
        return accUser;
    }

    public void setAccUser(String accUser) {
        this.accUser = accUser;
    }

    public String getAccDocumento() {
        return accDocumento;
    }

    public void setAccDocumento(String accDocumento) {
        this.accDocumento = accDocumento;
    }

    public Propiedad getPrdMatricula() {
        return prdMatricula;
    }

    public void setPrdMatricula(Propiedad prdMatricula) {
        this.prdMatricula = prdMatricula;
    }

    public Repertorio getRepNumero() {
        return repNumero;
    }

    public void setRepNumero(Repertorio repNumero) {
        this.repNumero = repNumero;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accId != null ? accId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accidente)) {
            return false;
        }
        Accidente other = (Accidente) object;
        if ((this.accId == null && other.accId != null) || (this.accId != null && !this.accId.equals(other.accId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.menun.Accidente[ accId=" + accId + " ]";
    }
    
}
