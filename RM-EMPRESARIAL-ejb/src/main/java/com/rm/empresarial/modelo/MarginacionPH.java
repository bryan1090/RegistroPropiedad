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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "MarginacionPH")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MarginacionPH.findAll", query = "SELECT m FROM MarginacionPH m")
    , @NamedQuery(name = "MarginacionPH.findByMrpId", query = "SELECT m FROM MarginacionPH m WHERE m.mrpId = :mrpId")
    , @NamedQuery(name = "MarginacionPH.findByMrpDescripcion", query = "SELECT m FROM MarginacionPH m WHERE m.mrpDescripcion = :mrpDescripcion")
    , @NamedQuery(name = "MarginacionPH.findByMrpFHR", query = "SELECT m FROM MarginacionPH m WHERE m.mrpFHR = :mrpFHR")
    , @NamedQuery(name = "MarginacionPH.findByMrpUser", query = "SELECT m FROM MarginacionPH m WHERE m.mrpUser = :mrpUser")})
public class MarginacionPH implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MrpId")
    private Long mrpId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "MrpDescripcion")
    private String mrpDescripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MrpFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mrpFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "MrpUser")
    private String mrpUser;
    
    @Size(max = 3)
    @Column(name = "MrpEstado")
    private String mrpEstado;

    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;
    @JoinColumn(name = "RepNumero", referencedColumnName = "RepNumero")
    @ManyToOne(optional = false)
    private Repertorio repNumero;
    
    

    public MarginacionPH() {
    }

    public MarginacionPH(Long mrpId) {
        this.mrpId = mrpId;
    }

    public MarginacionPH(Long mrpId, String mrpDescripcion, Date mrpFHR, String mrpUser) {
        this.mrpId = mrpId;
        this.mrpDescripcion = mrpDescripcion;
        this.mrpFHR = mrpFHR;
        this.mrpUser = mrpUser;
    }

    public Long getMrpId() {
        return mrpId;
    }

    public void setMrpId(Long mrpId) {
        this.mrpId = mrpId;
    }

    public String getMrpDescripcion() {
        return mrpDescripcion;
    }

    public void setMrpDescripcion(String mrpDescripcion) {
        this.mrpDescripcion = mrpDescripcion;
    }

    public Date getMrpFHR() {
        return mrpFHR;
    }

    public void setMrpFHR(Date mrpFHR) {
        this.mrpFHR = mrpFHR;
    }

    public String getMrpUser() {
        return mrpUser;
    }

    public void setMrpUser(String mrpUser) {
        this.mrpUser = mrpUser;
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

    public String getMrpEstado() {
        return mrpEstado;
    }

    public void setMrpEstado(String mrpEstado) {
        this.mrpEstado = mrpEstado;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mrpId != null ? mrpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MarginacionPH)) {
            return false;
        }
        MarginacionPH other = (MarginacionPH) object;
        if ((this.mrpId == null && other.mrpId != null) || (this.mrpId != null && !this.mrpId.equals(other.mrpId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MarginacionPH[ mrpId=" + mrpId + " ]";
    }

}
