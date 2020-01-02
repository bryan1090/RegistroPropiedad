/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
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
@Table(name = "Ciudad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ciudad.findAll", query = "SELECT c FROM Ciudad c ORDER BY c.ciuNombre")
    , @NamedQuery(name = "Ciudad.findByCiuId", query = "SELECT c FROM Ciudad c WHERE c.ciuId = :ciuId")
    , @NamedQuery(name = "Ciudad.findByCiuNombre", query = "SELECT c FROM Ciudad c WHERE c.ciuNombre = :ciuNombre")
    , @NamedQuery(name = "Ciudad.findByCiuEstado", query = "SELECT c FROM Ciudad c WHERE c.ciuEstado = :ciuEstado")
    , @NamedQuery(name = "Ciudad.findByCiuFHR", query = "SELECT c FROM Ciudad c WHERE c.ciuFHR = :ciuFHR")
    , @NamedQuery(name = "Ciudad.findByCiuUser", query = "SELECT c FROM Ciudad c WHERE c.ciuUser = :ciuUser")
    , @NamedQuery(name = "Ciudad.findByCiuCodigo", query = "SELECT c FROM Ciudad c WHERE c.ciuCodigo = :ciuCodigo")
    , @NamedQuery(name = "Ciudad.findByCiuInicial", query = "SELECT c FROM Ciudad c WHERE c.ciuInicial = :ciuInicial")
    , @NamedQuery(name = "Ciudad.findByCanton", query = "SELECT c FROM Ciudad c WHERE c.canId.canId = :canId")
    , @NamedQuery(name = "Ciudad.findByCiuNombreEditar", query = "SELECT c FROM Ciudad c WHERE c.ciuNombre = :ciuNombre AND c.ciuId != :ciuId")})
public class Ciudad implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "Ciudad.findAll";
    public static final String LISTAR_PORID = "Ciudad.findByCiuId";
    public static final String BUSCAR_POR_NOM = "Ciudad.findByCiuNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "Ciudad.findByCiuNombreEditar";
    public static final String BUSCAR_POR_CANTON = "Ciudad.findByCiuNombreEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CiuId")
    private Long ciuId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "CiuNombre")
    private String ciuNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "CiuEstado")
    private String ciuEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "CiuFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ciuFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "CiuUser")
    private String ciuUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CiuCodigo")
    private String ciuCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CiuInicial")
    private String ciuInicial;
    @JoinColumn(name = "CanId", referencedColumnName = "CanId")
    @ManyToOne(optional = false)
    private Canton canId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ciuId")
    private List<Parroquia> parroquiaList;

    public Ciudad() {
    }

    public Ciudad(Long ciuId) {
        this.ciuId = ciuId;
    }

    public Ciudad(Long ciuId, String ciuNombre, String ciuEstado, Date ciuFHR, String ciuUser, String ciuCodigo, String ciuInicial) {
        this.ciuId = ciuId;
        this.ciuNombre = ciuNombre;
        this.ciuEstado = ciuEstado;
        this.ciuFHR = ciuFHR;
        this.ciuUser = ciuUser;
        this.ciuCodigo = ciuCodigo;
        this.ciuInicial = ciuInicial;
    }

    public Long getCiuId() {
        return ciuId;
    }

    public void setCiuId(Long ciuId) {
        this.ciuId = ciuId;
    }

    public String getCiuNombre() {
        return ciuNombre;
    }

    public void setCiuNombre(String ciuNombre) {
        this.ciuNombre = ciuNombre;
    }

    public String getCiuEstado() {
        return ciuEstado;
    }

    public void setCiuEstado(String ciuEstado) {
        this.ciuEstado = ciuEstado;
    }

    public Date getCiuFHR() {
        return ciuFHR;
    }

    public void setCiuFHR(Date ciuFHR) {
        this.ciuFHR = ciuFHR;
    }

    public String getCiuUser() {
        return ciuUser;
    }

    public void setCiuUser(String ciuUser) {
        this.ciuUser = ciuUser;
    }

    public String getCiuCodigo() {
        return ciuCodigo;
    }

    public void setCiuCodigo(String ciuCodigo) {
        this.ciuCodigo = ciuCodigo;
    }

    public String getCiuInicial() {
        return ciuInicial;
    }

    public void setCiuInicial(String ciuInicial) {
        this.ciuInicial = ciuInicial;
    }

    public Canton getCanId() {
        return canId;
    }

    public void setCanId(Canton canId) {
        this.canId = canId;
    }

    @XmlTransient
    public List<Parroquia> getParroquiaList() {
        return parroquiaList;
    }

    public void setParroquiaList(List<Parroquia> parroquiaList) {
        this.parroquiaList = parroquiaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ciuId != null ? ciuId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciudad)) {
            return false;
        }
        Ciudad other = (Ciudad) object;
        if ((this.ciuId == null && other.ciuId != null) || (this.ciuId != null && !this.ciuId.equals(other.ciuId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Ciudad[ ciuId=" + ciuId + " ]";
    }

}
