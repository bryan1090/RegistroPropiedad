/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author JeanCarlos
 */
@Entity
@Table(name = "UnidMedida")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UnidMedida.findAll", query = "SELECT u FROM UnidMedida u ORDER BY u.umdNombre")
    , @NamedQuery(name = "UnidMedida.findByUmdId", query = "SELECT u FROM UnidMedida u WHERE u.umdId = :umdId")
    , @NamedQuery(name = "UnidMedida.findByUmdNombre", query = "SELECT u FROM UnidMedida u WHERE u.umdNombre = :umdNombre")
    , @NamedQuery(name = "UnidMedida.findByUmdAbreviatura", query = "SELECT u FROM UnidMedida u WHERE u.umdAbreviatura = :umdAbreviatura")
    , @NamedQuery(name = "UnidMedida.findByUmdEstado", query = "SELECT u FROM UnidMedida u WHERE u.umdEstado = :umdEstado")
    , @NamedQuery(name = "UnidMedida.findByUmdUser", query = "SELECT u FROM UnidMedida u WHERE u.umdUser = :umdUser")
    , @NamedQuery(name = "UnidMedida.findByUmdFHR", query = "SELECT u FROM UnidMedida u WHERE u.umdFHR = :umdFHR")
    , @NamedQuery(name = "UnidMedida.findByUmdNombreEditar", query = "SELECT u FROM UnidMedida u WHERE u.umdNombre = :umdNombre AND u.umdId != :umdId")
    , @NamedQuery(name = "UnidMedida.findByUmdAbreviaturaEditar", query = "SELECT u FROM UnidMedida u WHERE u.umdAbreviatura = :umdAbreviatura AND u.umdId != :umdId")
, @NamedQuery(name = "UnidMedida.findByUmdCodigo", query = "SELECT u FROM UnidMedida u WHERE u.umdCodigo = :umdCodigo")})
public class UnidMedida implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "UnidMedida.findAll";
    public static final String BUSCAR_POR_NOM = "UnidMedida.findByUmdNombre";
    public static final String BUSCAR_POR_ABREVI = "UnidMedida.findByUmdAbreviatura";
    public static final String BUSCAR_POR_NOM_EDITAR = "UnidMedida.findByUmdNombreEditar";
    public static final String BUSCAR_POR_ABREVI_EDITAR = "UnidMedida.findByUmdAbreviaturaEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UmdId")
    private Long umdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "UmdNombre")
    private String umdNombre;
    @Size(max = 3)
    @Column(name = "UmdAbreviatura")
    private String umdAbreviatura;
    @Size(max = 3)
    @Column(name = "UmdEstado")
    private String umdEstado;
    @Size(max = 20)
    @Column(name = "UmdUser")
    private String umdUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "UmdFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date umdFHR;
    @Size(max = 10)
    @Column(name = "UmdCodigo")
    private String umdCodigo;
    @OneToMany(mappedBy = "umdId")
    private List<Propiedad> propiedadList;

    public UnidMedida() {
    }

    public UnidMedida(Long umdId) {
        this.umdId = umdId;
    }

    public UnidMedida(Long umdId, String umdNombre, Date umdFHR) {
        this.umdId = umdId;
        this.umdNombre = umdNombre;
        this.umdFHR = umdFHR;
    }

    public Long getUmdId() {
        return umdId;
    }

    public void setUmdId(Long umdId) {
        this.umdId = umdId;
    }

    public String getUmdNombre() {
        return umdNombre;
    }

    public void setUmdNombre(String umdNombre) {
        this.umdNombre = umdNombre;
    }

    public String getUmdAbreviatura() {
        return umdAbreviatura;
    }

    public void setUmdAbreviatura(String umdAbreviatura) {
        this.umdAbreviatura = umdAbreviatura;
    }

    public String getUmdEstado() {
        return umdEstado;
    }

    public void setUmdEstado(String umdEstado) {
        this.umdEstado = umdEstado;
    }

    public String getUmdUser() {
        return umdUser;
    }

    public void setUmdUser(String umdUser) {
        this.umdUser = umdUser;
    }

    public Date getUmdFHR() {
        return umdFHR;
    }

    public void setUmdFHR(Date umdFHR) {
        this.umdFHR = umdFHR;
    }
    
    public String getUmdCodigo() {
        return umdCodigo;
    }

    public void setUmdCodigo(String umdCodigo) {
        this.umdCodigo = umdCodigo;
    }

    @XmlTransient
    public List<Propiedad> getPropiedadList() {
        return propiedadList;
    }

    public void setPropiedadList(List<Propiedad> propiedadList) {
        this.propiedadList = propiedadList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (umdId != null ? umdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UnidMedida)) {
            return false;
        }
        UnidMedida other = (UnidMedida) object;
        if ((this.umdId == null && other.umdId != null) || (this.umdId != null && !this.umdId.equals(other.umdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.UnidMedida[ umdId=" + umdId + " ]";
    }
    
}
