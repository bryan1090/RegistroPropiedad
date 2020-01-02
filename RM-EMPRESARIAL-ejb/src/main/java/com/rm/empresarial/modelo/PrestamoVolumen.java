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
 * @author Brandon
 */
@Entity
@Table(name = "PrestamoVolumen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrestamoVolumen.findAll", query = "SELECT p FROM PrestamoVolumen p")
    , @NamedQuery(name = "PrestamoVolumen.findByPrvId", query = "SELECT p FROM PrestamoVolumen p WHERE p.prvId = :prvId")
    , @NamedQuery(name = "PrestamoVolumen.findByPrvNombre", query = "SELECT p FROM PrestamoVolumen p WHERE p.prvNombre = :prvNombre")
    , @NamedQuery(name = "PrestamoVolumen.findByPrvApellido", query = "SELECT p FROM PrestamoVolumen p WHERE p.prvApellido = :prvApellido")
    , @NamedQuery(name = "PrestamoVolumen.findByPrvFechaInicio", query = "SELECT p FROM PrestamoVolumen p WHERE p.prvFechaInicio = :prvFechaInicio")
    , @NamedQuery(name = "PrestamoVolumen.findByPrvFechaFin", query = "SELECT p FROM PrestamoVolumen p WHERE p.prvFechaFin = :prvFechaFin")
    , @NamedQuery(name = "PrestamoVolumen.findByPrvEstado", query = "SELECT p FROM PrestamoVolumen p WHERE p.prvEstado = :prvEstado")
    , @NamedQuery(name = "PrestamoVolumen.findByPrvFHR", query = "SELECT p FROM PrestamoVolumen p WHERE p.prvFHR = :prvFHR")
    , @NamedQuery(name = "PrestamoVolumen.findByPrvUser", query = "SELECT p FROM PrestamoVolumen p WHERE p.prvUser = :prvUser")})
public class PrestamoVolumen implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PrvId")
    private BigDecimal prvId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "PrvNombre")
    private String prvNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "PrvApellido")
    private String prvApellido;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrvFechaInicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prvFechaInicio;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrvFechaFin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prvFechaFin;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PrvEstado")
    private String prvEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PrvFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prvFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PrvUser")
    private String prvUser;
    @Column(name = "PrvUserEntrega")
    private String prvUserEntrega;
    @Column(name = "PrvFHREntrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prvFHREntrega;
    @Column(name = "PrvIdentificacion")
    private String prvIdentificacion;
    @Column(name = "PrvTipoIdentificacion")
    private String prvTipoIdentificacion;
    @JoinColumn(name = "VleId", referencedColumnName = "VleId")
    @ManyToOne(optional = false)
    private Volumen vleId;

    public PrestamoVolumen() {
    }

    public PrestamoVolumen(BigDecimal prvId) {
        this.prvId = prvId;
    }

    public PrestamoVolumen(BigDecimal prvId, String prvNombre, String prvApellido, Date prvFechaInicio, Date prvFechaFin, String prvEstado, Date prvFHR, String prvUser) {
        this.prvId = prvId;
        this.prvNombre = prvNombre;
        this.prvApellido = prvApellido;
        this.prvFechaInicio = prvFechaInicio;
        this.prvFechaFin = prvFechaFin;
        this.prvEstado = prvEstado;
        this.prvFHR = prvFHR;
        this.prvUser = prvUser;
    }

    public Date getPrvFHREntrega() {
        return prvFHREntrega;
    }

    public void setPrvFHREntrega(Date prvFHREntrega) {
        this.prvFHREntrega = prvFHREntrega;
    }

    public String getPrvIdentificacion() {
        return prvIdentificacion;
    }

    public void setPrvIdentificacion(String prvIdentificacion) {
        this.prvIdentificacion = prvIdentificacion;
    }

    public String getPrvTipoIdentificacion() {
        return prvTipoIdentificacion;
    }

    public void setPrvTipoIdentificacion(String prvTipoIdentificacion) {
        this.prvTipoIdentificacion = prvTipoIdentificacion;
    }
    
    

    public String getPrvUserEntrega() {
        return prvUserEntrega;
    }

    public void setPrvUserEntrega(String prvUserEntrega) {
        this.prvUserEntrega = prvUserEntrega;
    }

    public BigDecimal getPrvId() {
        return prvId;
    }

    public void setPrvId(BigDecimal prvId) {
        this.prvId = prvId;
    }

    public String getPrvNombre() {
        return prvNombre;
    }

    public void setPrvNombre(String prvNombre) {
        this.prvNombre = prvNombre;
    }

    public String getPrvApellido() {
        return prvApellido;
    }

    public void setPrvApellido(String prvApellido) {
        this.prvApellido = prvApellido;
    }

    public Date getPrvFechaInicio() {
        return prvFechaInicio;
    }

    public void setPrvFechaInicio(Date prvFechaInicio) {
        this.prvFechaInicio = prvFechaInicio;
    }

    public Date getPrvFechaFin() {
        return prvFechaFin;
    }

    public void setPrvFechaFin(Date prvFechaFin) {
        this.prvFechaFin = prvFechaFin;
    }

    public String getPrvEstado() {
        return prvEstado;
    }

    public void setPrvEstado(String prvEstado) {
        this.prvEstado = prvEstado;
    }

    public Date getPrvFHR() {
        return prvFHR;
    }

    public void setPrvFHR(Date prvFHR) {
        this.prvFHR = prvFHR;
    }

    public String getPrvUser() {
        return prvUser;
    }

    public void setPrvUser(String prvUser) {
        this.prvUser = prvUser;
    }

    public Volumen getVleId() {
        return vleId;
    }

    public void setVleId(Volumen vleId) {
        this.vleId = vleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (prvId != null ? prvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrestamoVolumen)) {
            return false;
        }
        PrestamoVolumen other = (PrestamoVolumen) object;
        if ((this.prvId == null && other.prvId != null) || (this.prvId != null && !this.prvId.equals(other.prvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.rm.empresarial.modelo.PrestamoVolumen[ prvId=" + prvId + " ]";
    }

}
