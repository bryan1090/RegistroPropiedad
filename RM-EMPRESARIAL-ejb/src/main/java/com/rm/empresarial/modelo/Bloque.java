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
@Table(name = "Bloque")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Bloque.findAll", query = "SELECT b FROM Bloque b")
    , @NamedQuery(name = "Bloque.findByBloId", query = "SELECT b FROM Bloque b WHERE b.bloId = :bloId")
    , @NamedQuery(name = "Bloque.findByBloTipo", query = "SELECT b FROM Bloque b WHERE b.bloTipo = :bloTipo")
    , @NamedQuery(name = "Bloque.findByBloCodigo", query = "SELECT b FROM Bloque b WHERE b.bloCodigo = :bloCodigo")
    , @NamedQuery(name = "Bloque.findByBloSubTipo", query = "SELECT b FROM Bloque b WHERE b.bloSubTipo = :bloSubTipo")
    , @NamedQuery(name = "Bloque.findByBloNombre", query = "SELECT b FROM Bloque b WHERE b.bloNombre = :bloNombre")
    , @NamedQuery(name = "Bloque.findByBloFHR", query = "SELECT b FROM Bloque b WHERE b.bloFHR = :bloFHR")
    , @NamedQuery(name = "Bloque.findByBloUser", query = "SELECT b FROM Bloque b WHERE b.bloUser = :bloUser")})
public class Bloque implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String ENCONTRAR_POR_ID = "Bloque.findByBloId";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BloId")
    private Long bloId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "BloTipo")
    private String bloTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "BloCodigo")
    private String bloCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "BloSubTipo")
    private String bloSubTipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "BloNombre")
    private String bloNombre;
    @Basic(optional = false)
    @NotNull
    @Column(name = "BloFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bloFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "BloUser")
    private String bloUser;
    @JoinColumn(name = "PrdMatricula", referencedColumnName = "PrdMatricula")
    @ManyToOne(optional = false)
    private Propiedad prdMatricula;

    public Bloque() {
    }

    public Bloque(Long bloId) {
        this.bloId = bloId;
    }

    public Bloque(Long bloId, String bloTipo, String bloCodigo, String bloSubTipo, String bloNombre, Date bloFHR, String bloUser) {
        this.bloId = bloId;
        this.bloTipo = bloTipo;
        this.bloCodigo = bloCodigo;
        this.bloSubTipo = bloSubTipo;
        this.bloNombre = bloNombre;
        this.bloFHR = bloFHR;
        this.bloUser = bloUser;
    }

    public Long getBloId() {
        return bloId;
    }

    public void setBloId(Long bloId) {
        this.bloId = bloId;
    }

    public String getBloTipo() {
        return bloTipo;
    }

    public void setBloTipo(String bloTipo) {
        this.bloTipo = bloTipo;
    }

    public String getBloCodigo() {
        return bloCodigo;
    }

    public void setBloCodigo(String bloCodigo) {
        this.bloCodigo = bloCodigo;
    }

    public String getBloSubTipo() {
        return bloSubTipo;
    }

    public void setBloSubTipo(String bloSubTipo) {
        this.bloSubTipo = bloSubTipo;
    }

    public String getBloNombre() {
        return bloNombre;
    }

    public void setBloNombre(String bloNombre) {
        this.bloNombre = bloNombre;
    }

    public Date getBloFHR() {
        return bloFHR;
    }

    public void setBloFHR(Date bloFHR) {
        this.bloFHR = bloFHR;
    }

    public String getBloUser() {
        return bloUser;
    }

    public void setBloUser(String bloUser) {
        this.bloUser = bloUser;
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
        hash += (bloId != null ? bloId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Bloque)) {
            return false;
        }
        Bloque other = (Bloque) object;
        if ((this.bloId == null && other.bloId != null) || (this.bloId != null && !this.bloId.equals(other.bloId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.tipocertificado.Bloque[ bloId=" + bloId + " ]";
    }
    
}
