/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
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
@Table(name = "Provincia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Provincia.findAll", query = "SELECT p FROM Provincia p ORDER BY p.proNombre")
    , @NamedQuery(name = "Provincia.findByProId", query = "SELECT p FROM Provincia p WHERE p.proId = :proId")
    , @NamedQuery(name = "Provincia.findByProNombre", query = "SELECT p FROM Provincia p WHERE p.proNombre = :proNombre")
    , @NamedQuery(name = "Provincia.findByProEstado", query = "SELECT p FROM Provincia p WHERE p.proEstado = :proEstado")
    , @NamedQuery(name = "Provincia.findByProFHR", query = "SELECT p FROM Provincia p WHERE p.proFHR = :proFHR")
    , @NamedQuery(name = "Provincia.findByProUser", query = "SELECT p FROM Provincia p WHERE p.proUser = :proUser")
    , @NamedQuery(name = "Provincia.findByProCodigo", query = "SELECT p FROM Provincia p WHERE p.proCodigo = :proCodigo")
    , @NamedQuery(name = "Provincia.findByProInicial", query = "SELECT p FROM Provincia p WHERE p.proInicial = :proInicial")
    , @NamedQuery(name = "Provincia.findByProNombreEditar", query = "SELECT p FROM Provincia p WHERE p.proNombre = :proNombre AND p.proId != :proId")})
public class Provincia implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "Provincia.findAll";
    public static final String LISTAR_PORID = "Provincia.findByProId";
    public static final String BUSCAR_POR_NOM = "Provincia.findByProNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "Provincia.findByProNombreEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProId")
    private Long proId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "ProNombre")
    private String proNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "ProEstado")
    private String proEstado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ProFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date proFHR;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "ProUser")
    private String proUser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ProCodigo")
    private String proCodigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "ProInicial")
    private String proInicial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "proId")
    private List<Canton> cantonList;
    @JoinColumn(name = "PaiId", referencedColumnName = "PaiId")
    @ManyToOne(optional = false)
    private Pais paiId;

    public Provincia() {
    }

    public Provincia(Long proId) {
        this.proId = proId;
    }

    public Provincia(Long proId, String proNombre, String proEstado, Date proFHR, String proUser, String proCodigo, String proInicial) {
        this.proId = proId;
        this.proNombre = proNombre;
        this.proEstado = proEstado;
        this.proFHR = proFHR;
        this.proUser = proUser;
        this.proCodigo = proCodigo;
        this.proInicial = proInicial;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public String getProNombre() {
        return proNombre;
    }

    public void setProNombre(String proNombre) {
        this.proNombre = proNombre;
    }

    public String getProEstado() {
        return proEstado;
    }

    public void setProEstado(String proEstado) {
        this.proEstado = proEstado;
    }

    public Date getProFHR() {
        return proFHR;
    }

    public void setProFHR(Date proFHR) {
        this.proFHR = proFHR;
    }

    public String getProUser() {
        return proUser;
    }

    public void setProUser(String proUser) {
        this.proUser = proUser;
    }

    public String getProCodigo() {
        return proCodigo;
    }

    public void setProCodigo(String proCodigo) {
        this.proCodigo = proCodigo;
    }

    public String getProInicial() {
        return proInicial;
    }

    public void setProInicial(String proInicial) {
        this.proInicial = proInicial;
    }

    @XmlTransient
    public List<Canton> getCantonList() {
        return cantonList;
    }

    public void setCantonList(List<Canton> cantonList) {
        this.cantonList = cantonList;
    }

    public Pais getPaiId() {
        return paiId;
    }

    public void setPaiId(Pais paiId) {
        this.paiId = paiId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (proId != null ? proId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Provincia)) {
            return false;
        }
        Provincia other = (Provincia) object;
        if ((this.proId == null && other.proId != null) || (this.proId != null && !this.proId.equals(other.proId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Provincia[ proId=" + proId + " ]";
    }

}
