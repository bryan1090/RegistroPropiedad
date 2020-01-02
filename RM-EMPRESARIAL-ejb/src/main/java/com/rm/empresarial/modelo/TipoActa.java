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
@Table(name = "TipoActa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoActa.findAll", query = "SELECT t FROM TipoActa t ORDER BY t.tpaNombre")
    , @NamedQuery(name = "TipoActa.findByTpaId", query = "SELECT t FROM TipoActa t WHERE t.tpaId = :tpaId")
    , @NamedQuery(name = "TipoActa.findByTpaNombre", query = "SELECT t FROM TipoActa t WHERE t.tpaNombre = :tpaNombre")
    , @NamedQuery(name = "TipoActa.findByTpaUser", query = "SELECT t FROM TipoActa t WHERE t.tpaUser = :tpaUser")
    , @NamedQuery(name = "TipoActa.findByTpaFHR", query = "SELECT t FROM TipoActa t WHERE t.tpaFHR = :tpaFHR")
    , @NamedQuery(name = "TipoActa.findByTpaNombreEditar", query = "SELECT t FROM TipoActa t WHERE t.tpaNombre = :tpaNombre AND t.tpaId != :tpaId")})
public class TipoActa implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODO = "TipoActa.findAll";
    public static final String LISTAR_PORID = "TipoActa.findByTpaId";
    public static final String BUSCAR_POR_NOM = "TipoActa.findByTpaNombre";
    public static final String BUSCAR_POR_NOM_EDITAR = "TipoActa.findByTpaNombreEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TpaId")
    private Long tpaId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "TpaNombre")
    private String tpaNombre;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TpaUser")
    private String tpaUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TpaFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tpaFHR;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tpaId")
    private List<Acta> actaList;

    public TipoActa() {
    }

    public TipoActa(Long tpaId) {
        this.tpaId = tpaId;
    }

    public TipoActa(Long tpaId, String tpaNombre, String tpaUser, Date tpaFHR) {
        this.tpaId = tpaId;
        this.tpaNombre = tpaNombre;
        this.tpaUser = tpaUser;
        this.tpaFHR = tpaFHR;
    }

    public Long getTpaId() {
        return tpaId;
    }

    public void setTpaId(Long tpaId) {
        this.tpaId = tpaId;
    }

    public String getTpaNombre() {
        return tpaNombre;
    }

    public void setTpaNombre(String tpaNombre) {
        this.tpaNombre = tpaNombre;
    }

    public String getTpaUser() {
        return tpaUser;
    }

    public void setTpaUser(String tpaUser) {
        this.tpaUser = tpaUser;
    }

    public Date getTpaFHR() {
        return tpaFHR;
    }

    public void setTpaFHR(Date tpaFHR) {
        this.tpaFHR = tpaFHR;
    }

    @XmlTransient
    public List<Acta> getActaList() {
        return actaList;
    }

    public void setActaList(List<Acta> actaList) {
        this.actaList = actaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tpaId != null ? tpaId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoActa)) {
            return false;
        }
        TipoActa other = (TipoActa) object;
        if ((this.tpaId == null && other.tpaId != null) || (this.tpaId != null && !this.tpaId.equals(other.tpaId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.TipoActa[ tpaId=" + tpaId + " ]";
    }
    
}
