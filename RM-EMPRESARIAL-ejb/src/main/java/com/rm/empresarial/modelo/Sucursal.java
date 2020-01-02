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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "Sucursal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sucursal.findAll", query = "SELECT s FROM Sucursal s ORDER BY s.sucNombre")
    , @NamedQuery(name = "Sucursal.findBySucId", query = "SELECT s FROM Sucursal s WHERE s.sucId = :sucId")
    , @NamedQuery(name = "Sucursal.findBySucNombre", query = "SELECT s FROM Sucursal s WHERE s.sucNombre = :sucNombre")
    , @NamedQuery(name = "Sucursal.findBySucTelefono", query = "SELECT s FROM Sucursal s WHERE s.sucTelefono = :sucTelefono")
    , @NamedQuery(name = "Sucursal.findBySucMatriz", query = "SELECT s FROM Sucursal s WHERE s.sucMatriz = :sucMatriz")
    , @NamedQuery(name = "Sucursal.findBySucDireccion", query = "SELECT s FROM Sucursal s WHERE s.sucDireccion = :sucDireccion")
    , @NamedQuery(name = "Sucursal.findBySucCorreo", query = "SELECT s FROM Sucursal s WHERE s.sucCorreo = :sucCorreo")
    , @NamedQuery(name = "Sucursal.findBySucEstado", query = "SELECT s FROM Sucursal s WHERE s.sucEstado = :sucEstado")
    , @NamedQuery(name = "Sucursal.findBySucUser", query = "SELECT s FROM Sucursal s WHERE s.sucUser = :sucUser")
    , @NamedQuery(name = "Sucursal.findBySucFHR", query = "SELECT s FROM Sucursal s WHERE s.sucFHR = :sucFHR")
    , @NamedQuery(name = "Sucursal.findBySucSerie", query = "SELECT s FROM Sucursal s WHERE s.sucSerie = :sucSerie")
    , @NamedQuery(name = "Sucursal.findBySucNombreEditar", query = "SELECT s FROM Sucursal s WHERE s.sucNombre = :sucNombre AND s.sucId != :sucId")})
public class Sucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "Sucursal.findAll";
    public static final String BUSCAR_POR_ID = "Sucursal.findBySucId";
    public static final String BUSCAR_POR_NOM = "Sucursal.findBySucNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "Sucursal.findBySucNombreEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SucId")
    private Long sucId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "SucNombre")
    private String sucNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SucTelefono")
    private String sucTelefono;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "SucMatriz")
    private String sucMatriz;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1024)
    @Column(name = "SucDireccion")
    private String sucDireccion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SucCorreo")
    private String sucCorreo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "SucEstado")
    private String sucEstado;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "SucUser")
    private String sucUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SucFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sucFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "SucSerie")
    private String sucSerie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucId")
    private List<Serie> serieList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sucId")
    private List<Caja> cajaList;
    @JoinColumn(name = "ZonId", referencedColumnName = "ZonId")
    @ManyToOne(optional = false)
    private Zona zonId;

    public Sucursal() {
    }

    public Sucursal(Long sucId) {
        this.sucId = sucId;
    }

    public Sucursal(Long sucId, String sucNombre, String sucTelefono, String sucMatriz, String sucDireccion, String sucCorreo, String sucEstado, String sucUser, Date sucFHR, String sucSerie) {
        this.sucId = sucId;
        this.sucNombre = sucNombre;
        this.sucTelefono = sucTelefono;
        this.sucMatriz = sucMatriz;
        this.sucDireccion = sucDireccion;
        this.sucCorreo = sucCorreo;
        this.sucEstado = sucEstado;
        this.sucUser = sucUser;
        this.sucFHR = sucFHR;
        this.sucSerie = sucSerie;
    }

    public Long getSucId() {
        return sucId;
    }

    public void setSucId(Long sucId) {
        this.sucId = sucId;
    }

    public String getSucNombre() {
        return sucNombre;
    }

    public void setSucNombre(String sucNombre) {
        this.sucNombre = sucNombre;
    }

    public String getSucTelefono() {
        return sucTelefono;
    }

    public void setSucTelefono(String sucTelefono) {
        this.sucTelefono = sucTelefono;
    }

    public String getSucMatriz() {
        return sucMatriz;
    }

    public void setSucMatriz(String sucMatriz) {
        this.sucMatriz = sucMatriz;
    }

    public String getSucDireccion() {
        return sucDireccion;
    }

    public void setSucDireccion(String sucDireccion) {
        this.sucDireccion = sucDireccion;
    }

    public String getSucCorreo() {
        return sucCorreo;
    }

    public void setSucCorreo(String sucCorreo) {
        this.sucCorreo = sucCorreo;
    }

    public String getSucEstado() {
        return sucEstado;
    }

    public void setSucEstado(String sucEstado) {
        this.sucEstado = sucEstado;
    }

    public String getSucUser() {
        return sucUser;
    }

    public void setSucUser(String sucUser) {
        this.sucUser = sucUser;
    }

    public Date getSucFHR() {
        return sucFHR;
    }

    public void setSucFHR(Date sucFHR) {
        this.sucFHR = sucFHR;
    }

    public String getSucSerie() {
        return sucSerie;
    }

    public void setSucSerie(String sucSerie) {
        this.sucSerie = sucSerie;
    }

    @XmlTransient
    public List<Serie> getSerieList() {
        return serieList;
    }

    public void setSerieList(List<Serie> serieList) {
        this.serieList = serieList;
    }

    @XmlTransient
    public List<Caja> getCajaList() {
        return cajaList;
    }

    public void setCajaList(List<Caja> cajaList) {
        this.cajaList = cajaList;
    }

    public Zona getZonId() {
        return zonId;
    }

    public void setZonId(Zona zonId) {
        this.zonId = zonId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sucId != null ? sucId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sucursal)) {
            return false;
        }
        Sucursal other = (Sucursal) object;
        if ((this.sucId == null && other.sucId != null) || (this.sucId != null && !this.sucId.equals(other.sucId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Sucursal[ sucId=" + sucId + " ]";
    }
    
}
