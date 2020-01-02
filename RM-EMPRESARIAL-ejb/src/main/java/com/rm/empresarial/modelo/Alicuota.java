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
 * @author Prueba
 */
@Entity
@Table(name = "Alicuota")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alicuota.findAll", query = "SELECT a FROM Alicuota a")
    , @NamedQuery(name = "Alicuota.findByAltId", query = "SELECT a FROM Alicuota a WHERE a.altId = :altId")
    , @NamedQuery(name = "Alicuota.findByAltNumero", query = "SELECT a FROM Alicuota a WHERE a.altNumero = :altNumero")
    , @NamedQuery(name = "Alicuota.findByAltAlicuota", query = "SELECT a FROM Alicuota a WHERE a.altAlicuota = :altAlicuota")
    , @NamedQuery(name = "Alicuota.findByAltArea", query = "SELECT a FROM Alicuota a WHERE a.altArea = :altArea")
    , @NamedQuery(name = "Alicuota.findByAltPiso", query = "SELECT a FROM Alicuota a WHERE a.altPiso = :altPiso")
    , @NamedQuery(name = "Alicuota.findByAltDescripcion", query = "SELECT a FROM Alicuota a WHERE a.altDescripcion = :altDescripcion")
    , @NamedQuery(name = "Alicuota.findByAltUser", query = "SELECT a FROM Alicuota a WHERE a.altUser = :altUser")
    , @NamedQuery(name = "Alicuota.findByAltFHR", query = "SELECT a FROM Alicuota a WHERE a.altFHR = :altFHR")
    , @NamedQuery(name = "Alicuota.findByAltEstado", query = "SELECT a FROM Alicuota a WHERE a.altEstado = :altEstado")})
public class Alicuota implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String BUSCAR_POR_ID = "Alicuota.findByAltId";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AltId")
    private Long altId;
    @Basic(optional = false)
    @NotNull
    @Size(max = 40)
    @Column(name = "AltNumero")
    private String altNumero;
    @Column(name = "AltAlicuota")
    private BigDecimal altAlicuota;
    @Size(max = 40)
    @Column(name = "AltArea")
    private String altArea;
    @Size(max = 40)
    @Column(name = "AltPiso")
    private String altPiso;
    @Size(max = 200)
    @Column(name = "AltDescripcion")
    private String altDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "AltUser")
    private String altUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "AltFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date altFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "AltEstado")
    private String altEstado;
    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;
    @Size(max = 100)
    @Column(name = "AltObservacion")
    private String altObservacion;

    public Alicuota() {
    }

    public Alicuota(Long altId) {
        this.altId = altId;
    }

    public Alicuota(Long altId, String altNumero, String altUser, Date altFHR, String altEstado) {
        this.altId = altId;
        this.altNumero = altNumero;
        this.altUser = altUser;
        this.altFHR = altFHR;
        this.altEstado = altEstado;
    }

    public Long getAltId() {
        return altId;
    }

    public void setAltId(Long altId) {
        this.altId = altId;
    }

    public String getAltNumero() {
        return altNumero;
    }

    public void setAltNumero(String altNumero) {
        this.altNumero = altNumero;
    }

    public BigDecimal getAltAlicuota() {
        return altAlicuota;
    }

    public void setAltAlicuota(BigDecimal altAlicuota) {
        this.altAlicuota = altAlicuota;
    }

    public String getAltArea() {
        return altArea;
    }

    public void setAltArea(String altArea) {
        this.altArea = altArea;
    }

    public String getAltPiso() {
        return altPiso;
    }

    public void setAltPiso(String altPiso) {
        this.altPiso = altPiso;
    }

    public String getAltDescripcion() {
        return altDescripcion;
    }

    public void setAltDescripcion(String altDescripcion) {
        this.altDescripcion = altDescripcion;
    }

    public String getAltUser() {
        return altUser;
    }

    public void setAltUser(String altUser) {
        this.altUser = altUser;
    }

    public Date getAltFHR() {
        return altFHR;
    }

    public void setAltFHR(Date altFHR) {
        this.altFHR = altFHR;
    }

    public String getAltEstado() {
        return altEstado;
    }

    public void setAltEstado(String altEstado) {
        this.altEstado = altEstado;
    }

    public Propiedad getPrdMatricula() {
        return prdMatricula;
    }

    public void setPrdMatricula(Propiedad prdMatricula) {
        this.prdMatricula = prdMatricula;
    }
    public String getAltObservacion() {
        return altObservacion;
    }

    public void setAltObservacion(String altObservacion) {
        this.altObservacion = altObservacion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (altId != null ? altId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alicuota)) {
            return false;
        }
        Alicuota other = (Alicuota) object;
        if ((this.altId == null && other.altId != null) || (this.altId != null && !this.altId.equals(other.altId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.Alicuota[ altId=" + altId + " ]";
    }
    
}
