/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "PropiedadDetalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PropiedadDetalle.findAll", query = "SELECT p FROM PropiedadDetalle p")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtId", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtId = :pdtId")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtDescripcion", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtDescripcion = :pdtDescripcion")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtSuperficie", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtSuperficie = :pdtSuperficie")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtUmdId", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtUmdId = :pdtUmdId")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtUmdAbreviatura", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtUmdAbreviatura = :pdtUmdAbreviatura")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtUser", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtUser = :pdtUser")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtFHR", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtFHR = :pdtFHR")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtValor", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtValor = :pdtValor")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtTipo", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtTipo = :pdtTipo")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtPerId", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtPerId = :pdtPerId")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtPrdMatricula", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtPrdMatricula = :pdtPrdMatricula")
    , @NamedQuery(name = "PropiedadDetalle.findByPdtEstado", query = "SELECT p FROM PropiedadDetalle p WHERE p.pdtEstado = :pdtEstado")})
public class PropiedadDetalle implements Serializable {

    public static final String LISTAR_POR_MATRICULA = "PropiedadDetalle.findByPdtPrdMatricula";
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PdtId")
    private Long pdtId;
    @Size(max = 200)
    @Column(name = "PdtDescripcion")
    private String pdtDescripcion;
    @Column(name = "PdtSuperficie")
    private Long pdtSuperficie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PdtUmdId")
    private BigInteger pdtUmdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PdtUmdAbreviatura")
    private String pdtUmdAbreviatura;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PdtUser")
    private String pdtUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PdtFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pdtFHR;
    @Column(name = "PdtValor")
    private BigDecimal pdtValor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "PdtTipo")
    private String pdtTipo;
    @Column(name = "PdtPerId")
    private BigInteger pdtPerId;
    @Size(min = 1, max = 100)
    @Column(name = "PdtPrdMatricula")
    private String pdtPrdMatricula;
    @Size(max = 3)
    @Column(name = "PdtEstado")
    private String pdtEstado;
    @Size(max = 1)
    @Column(name = "PdtClase")
    private String pdtClase;
    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;
    @Getter
    @Setter
    @Transient
    private Persona persona; 
    
    @Getter
    @Setter
    @Transient
    private String tempPropietario;
   
    @Getter
    @Setter
    @Column(name = "PdtRepNumero")
    private Long pdtRepNumero;

    public PropiedadDetalle() {
    }

    public PropiedadDetalle(Long pdtId) {
        this.pdtId = pdtId;
    }

    public PropiedadDetalle(Long pdtId, BigInteger pdtUmdId, String pdtUmdAbreviatura, String pdtUser, Date pdtFHR, String pdtTipo) {
        this.pdtId = pdtId;
        this.pdtUmdId = pdtUmdId;
        this.pdtUmdAbreviatura = pdtUmdAbreviatura;
        this.pdtUser = pdtUser;
        this.pdtFHR = pdtFHR;
        this.pdtTipo = pdtTipo;
    }

    public Long getPdtId() {
        return pdtId;
    }

    public void setPdtId(Long pdtId) {
        this.pdtId = pdtId;
    }

    public String getPdtDescripcion() {
        return pdtDescripcion;
    }

    public void setPdtDescripcion(String pdtDescripcion) {
        this.pdtDescripcion = pdtDescripcion;
    }

    public Long getPdtSuperficie() {
        return pdtSuperficie;
    }

    public void setPdtSuperficie(Long pdtSuperficie) {
        this.pdtSuperficie = pdtSuperficie;
    }

    public BigInteger getPdtUmdId() {
        return pdtUmdId;
    }

    public void setPdtUmdId(BigInteger pdtUmdId) {
        this.pdtUmdId = pdtUmdId;
    }

    public String getPdtUmdAbreviatura() {
        return pdtUmdAbreviatura;
    }

    public void setPdtUmdAbreviatura(String pdtUmdAbreviatura) {
        this.pdtUmdAbreviatura = pdtUmdAbreviatura;
    }

    public String getPdtUser() {
        return pdtUser;
    }

    public void setPdtUser(String pdtUser) {
        this.pdtUser = pdtUser;
    }

    public Date getPdtFHR() {
        return pdtFHR;
    }

    public void setPdtFHR(Date pdtFHR) {
        this.pdtFHR = pdtFHR;
    }

    public BigDecimal getPdtValor() {
        return pdtValor;
    }

    public void setPdtValor(BigDecimal pdtValor) {
        this.pdtValor = pdtValor;
    }

    public String getPdtTipo() {
        return pdtTipo;
    }

    public void setPdtTipo(String pdtTipo) {
        this.pdtTipo = pdtTipo;
    }

    public BigInteger getPdtPerId() {
        return pdtPerId;
    }

    public void setPdtPerId(BigInteger pdtPerId) {
        this.pdtPerId = pdtPerId;
    }

    public String getPdtPrdMatricula() {
        return pdtPrdMatricula;
    }

    public void setPdtPrdMatricula(String pdtPrdMatricula) {
        this.pdtPrdMatricula = pdtPrdMatricula;
    }

    public String getPdtEstado() {
        return pdtEstado;
    }

    public void setPdtEstado(String pdtEstado) {
        this.pdtEstado = pdtEstado;
    }

    public String getPdtClase() {
        return pdtClase;
    }

    public void setPdtClase(String pdtClase) {
        this.pdtClase = pdtClase;
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
        hash += (pdtId != null ? pdtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PropiedadDetalle)) {
            return false;
        }
        PropiedadDetalle other = (PropiedadDetalle) object;
        if ((this.pdtId == null && other.pdtId != null) || (this.pdtId != null && !this.pdtId.equals(other.pdtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.PropiedadDetalle[ pdtId=" + pdtId + " ]";
    }

}
