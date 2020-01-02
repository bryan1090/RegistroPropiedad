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
 * @author JeanCarlos
 */
@Entity
@Table(name = "Descuento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Descuento.findAll", query = "SELECT d FROM Descuento d")
    , @NamedQuery(name = "Descuento.findByDesId", query = "SELECT d FROM Descuento d WHERE d.desId = :desId")
    , @NamedQuery(name = "Descuento.findByDesNombre", query = "SELECT d FROM Descuento d WHERE d.desNombre = :desNombre")
    , @NamedQuery(name = "Descuento.findByDesValor", query = "SELECT d FROM Descuento d WHERE d.desValor = :desValor")
    , @NamedQuery(name = "Descuento.findByDesEstado", query = "SELECT d FROM Descuento d WHERE d.desEstado = :desEstado")
    , @NamedQuery(name = "Descuento.findByDesFHR", query = "SELECT d FROM Descuento d WHERE d.desFHR = :desFHR")
    , @NamedQuery(name = "Descuento.findByDesUsuario", query = "SELECT d FROM Descuento d WHERE d.desUsuario = :desUsuario")})
public class Descuento implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DesId")
    private Long desId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DesNombre")
    private String desNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DesValor")
    private BigDecimal desValor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "DesEstado")
    private String desEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DesFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date desFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "DesUsuario")
    private String desUsuario;

    public Descuento() {
    }

    public Descuento(Long desId) {
        this.desId = desId;
    }

    public Descuento(Long desId, String desNombre, BigDecimal desValor, String desEstado, Date desFHR, String desUsuario) {
        this.desId = desId;
        this.desNombre = desNombre;
        this.desValor = desValor;
        this.desEstado = desEstado;
        this.desFHR = desFHR;
        this.desUsuario = desUsuario;
    }

    public Long getDesId() {
        return desId;
    }

    public void setDesId(Long desId) {
        this.desId = desId;
    }

    public String getDesNombre() {
        return desNombre;
    }

    public void setDesNombre(String desNombre) {
        this.desNombre = desNombre;
    }

    public BigDecimal getDesValor() {
        return desValor;
    }

    public void setDesValor(BigDecimal desValor) {
        this.desValor = desValor;
    }

    public String getDesEstado() {
        return desEstado;
    }

    public void setDesEstado(String desEstado) {
        this.desEstado = desEstado;
    }

    public Date getDesFHR() {
        return desFHR;
    }

    public void setDesFHR(Date desFHR) {
        this.desFHR = desFHR;
    }

    public String getDesUsuario() {
        return desUsuario;
    }

    public void setDesUsuario(String desUsuario) {
        this.desUsuario = desUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (desId != null ? desId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Descuento)) {
            return false;
        }
        Descuento other = (Descuento) object;
        if ((this.desId == null && other.desId != null) || (this.desId != null && !this.desId.equals(other.desId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Descuento[ desId=" + desId + " ]";
    }
    
}
