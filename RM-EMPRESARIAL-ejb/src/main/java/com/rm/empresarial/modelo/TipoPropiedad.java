/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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

/**
 *
 * @author Prueba
 */
@Entity
@Table(name = "TipoPropiedad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPropiedad.findAll", query = "SELECT t FROM TipoPropiedad t ORDER BY t.tpdNombre")
    , @NamedQuery(name = "TipoPropiedad.findByTpdId", query = "SELECT t FROM TipoPropiedad t WHERE t.tpdId = :tpdId")
    , @NamedQuery(name = "TipoPropiedad.findByTpdNombre", query = "SELECT t FROM TipoPropiedad t WHERE t.tpdNombre = :tpdNombre")
    , @NamedQuery(name = "TipoPropiedad.findByTpdEstado", query = "SELECT t FROM TipoPropiedad t WHERE t.tpdEstado = :tpdEstado")
    , @NamedQuery(name = "TipoPropiedad.findByTpdUser", query = "SELECT t FROM TipoPropiedad t WHERE t.tpdUser = :tpdUser")
    , @NamedQuery(name = "TipoPropiedad.findByTpdFHR", query = "SELECT t FROM TipoPropiedad t WHERE t.tpdFHR = :tpdFHR")
    , @NamedQuery(name = "TipoPropiedad.findByTpdNombreEditar", query = "SELECT t FROM TipoPropiedad t WHERE t.tpdNombre = :tpdNombre AND t.tpdId != :tpdId")
    , @NamedQuery(name = "TipoPropiedad.findByTpdCodigo", query = "SELECT t FROM TipoPropiedad t WHERE t.tpdCodigo = :tpdCodigo")})

public class TipoPropiedad implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "TipoPropiedad.findAll";
    public static final String BUSCAR_POR_NOM = "TipoPropiedad.findByTpdNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "TipoPropiedad.findByTpdNombreEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TpdId")
    private Long tpdId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TpdNombre")
    private String tpdNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "TpdEstado")
    private String tpdEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TpdUser")
    private String tpdUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpdFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tpdFHR;
    @Size(max = 10)
    @Column(name = "TpdCodigo")
    private String tpdCodigo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tpdId")
    private Collection<Propiedad> propiedadCollection;

    public TipoPropiedad() {
    }

    public TipoPropiedad(Long tpdId) {
        this.tpdId = tpdId;
    }

    public TipoPropiedad(Long tpdId, String tpdNombre, String tpdEstado, String tpdUser, Date tpdFHR) {
        this.tpdId = tpdId;
        this.tpdNombre = tpdNombre;
        this.tpdEstado = tpdEstado;
        this.tpdUser = tpdUser;
        this.tpdFHR = tpdFHR;
    }

    public Long getTpdId() {
        return tpdId;
    }

    public void setTpdId(Long tpdId) {
        this.tpdId = tpdId;
    }

    public String getTpdNombre() {
        return tpdNombre;
    }

    public void setTpdNombre(String tpdNombre) {
        this.tpdNombre = tpdNombre;
    }

    public String getTpdEstado() {
        return tpdEstado;
    }

    public void setTpdEstado(String tpdEstado) {
        this.tpdEstado = tpdEstado;
    }

    public String getTpdUser() {
        return tpdUser;
    }

    public void setTpdUser(String tpdUser) {
        this.tpdUser = tpdUser;
    }

    public Date getTpdFHR() {
        return tpdFHR;
    }

    public void setTpdFHR(Date tpdFHR) {
        this.tpdFHR = tpdFHR;
    }

    public String getTpdCodigo() {
        return tpdCodigo;
    }

    public void setTpdCodigo(String tpdCodigo) {
        this.tpdCodigo = tpdCodigo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpdId != null ? tpdId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPropiedad)) {
            return false;
        }
        TipoPropiedad other = (TipoPropiedad) object;
        if ((this.tpdId == null && other.tpdId != null) || (this.tpdId != null && !this.tpdId.equals(other.tpdId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Modelo.TipoPropiedad[ tpdId=" + tpdId + " ]";
    }

}
