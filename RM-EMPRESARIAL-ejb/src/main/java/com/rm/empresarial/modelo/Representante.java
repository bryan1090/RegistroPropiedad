/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Prueba
 */
@Entity
@Table(name = "Representante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Representante.findAll", query = "SELECT r FROM Representante r")
    , @NamedQuery(name = "Representante.findByRptId", query = "SELECT r FROM Representante r WHERE r.rptId = :rptId")})
public class Representante implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RptId")
    private Long rptId;
    @JoinColumn(name = "ComId", referencedColumnName = "ComId")
    @ManyToOne(optional = false)
    private Compania comId;
    @JoinColumn(name = "PerId", referencedColumnName = "PerId")
    @ManyToOne(optional = false)
    private Persona perId;

    public Representante() {
    }

    public Representante(Long rptId) {
        this.rptId = rptId;
    }

    public Long getRptId() {
        return rptId;
    }

    public void setRptId(Long rptId) {
        this.rptId = rptId;
    }

    public Compania getComId() {
        return comId;
    }

    public void setComId(Compania comId) {
        this.comId = comId;
    }

    public Persona getPerId() {
        return perId;
    }

    public void setPerId(Persona perId) {
        this.perId = perId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rptId != null ? rptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Representante)) {
            return false;
        }
        Representante other = (Representante) object;
        if ((this.rptId == null && other.rptId != null) || (this.rptId != null && !this.rptId.equals(other.rptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.Representante[ rptId=" + rptId + " ]";
    }
    
}
