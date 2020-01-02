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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Prueba
 */
@Entity
@Table(name = "Gravamen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gravamen.findAll", query = "SELECT g FROM Gravamen g")
    , @NamedQuery(name = "Gravamen.findByGraId", query = "SELECT g FROM Gravamen g WHERE g.graId = :graId")    
    , @NamedQuery(name = "Gravamen.findByGraDescripcion", query = "SELECT g FROM Gravamen g WHERE g.graDescripcion = :graDescripcion")
    , @NamedQuery(name = "Gravamen.findByGraEstado", query = "SELECT g FROM Gravamen g WHERE g.graEstado = :graEstado")
    , @NamedQuery(name = "Gravamen.findByGraUser", query = "SELECT g FROM Gravamen g WHERE g.graUser = :graUser")
    , @NamedQuery(name = "Gravamen.findByGraFHR", query = "SELECT g FROM Gravamen g WHERE g.graFHR = :graFHR")})
public class Gravamen implements Serializable {

    private static final long serialVersionUID = 1L;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GraId")
    private Long graId;    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "GraDescripcion")
    private String graDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "GraEstado")
    private String graEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "GraUser")
    private String graUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "GraFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date graFHR;
    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;
    @JoinColumn(name = "RepNumero", referencedColumnName = "RepNumero")
    @ManyToOne(optional = false)
    private Repertorio repNumero;
    @Transient
    @Getter
    @Setter
    private boolean existeDetalleGrav; 

    public Gravamen() {
    }

    public Gravamen(Long graId) {
        this.graId = graId;
    }

    public Gravamen(Long graId, String graDescripcion, String graEstado, String graUser, Date graFHR) {
        this.graId = graId;       
        this.graDescripcion = graDescripcion;
        this.graEstado = graEstado;
        this.graUser = graUser;
        this.graFHR = graFHR;
    }

    public Long getGraId() {
        return graId;
    }

    public void setGraId(Long graId) {
        this.graId = graId;
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

    public String getGraDescripcion() {
        return graDescripcion;
    }

    public void setGraDescripcion(String graDescripcion) {
        this.graDescripcion = graDescripcion;
    }

    public String getGraEstado() {
        return graEstado;
    }

    public void setGraEstado(String graEstado) {
        this.graEstado = graEstado;
    }

    public String getGraUser() {
        return graUser;
    }

    public void setGraUser(String graUser) {
        this.graUser = graUser;
    }

    public Date getGraFHR() {
        return graFHR;
    }

    public void setGraFHR(Date graFHR) {
        this.graFHR = graFHR;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (graId != null ? graId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gravamen)) {
            return false;
        }
        Gravamen other = (Gravamen) object;
        if ((this.graId == null && other.graId != null) || (this.graId != null && !this.graId.equals(other.graId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoCertifcado.Gravamen[ graId=" + graId + " ]";
    }
    
}
