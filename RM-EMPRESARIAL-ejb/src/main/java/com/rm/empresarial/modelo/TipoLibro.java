/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rm.empresarial.modelo;

import java.io.Serializable;
import java.util.Collection;
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
 * @author Bryan_Mora
 */
@Entity
@Table(name = "TipoLibro")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoLibro.findAll", query = "SELECT t FROM TipoLibro t ORDER BY t.tplDescripcion ASC")
    , @NamedQuery(name = "TipoLibro.findByTplId", query = "SELECT t FROM TipoLibro t WHERE t.tplId = :tplId")
    , @NamedQuery(name = "TipoLibro.findByTplDescripcion", query = "SELECT t FROM TipoLibro t WHERE t.tplDescripcion = :tplDescripcion")
    , @NamedQuery(name = "TipoLibro.findByTplUser", query = "SELECT t FROM TipoLibro t WHERE t.tplUser = :tplUser")
    , @NamedQuery(name = "TipoLibro.findByTplFHR", query = "SELECT t FROM TipoLibro t WHERE t.tplFHR = :tplFHR")
    , @NamedQuery(name = "TipoLibro.findByTplTomo", query = "SELECT t FROM TipoLibro t WHERE t.tplTomo = :tplTomo")
    , @NamedQuery(name = "TipoLibro.findByTplDescripcionEditar", query = "SELECT t FROM TipoLibro t WHERE t.tplDescripcion = :tplDescripcion AND t.tplId != :tplId")
    , @NamedQuery(name = "TipoLibro.findByTplGravamen", query = "SELECT t FROM TipoLibro t WHERE t.tplGravamen = :tplGravamen")
    , @NamedQuery(name = "TipoLibro.findByTplPropietario", query = "SELECT t FROM TipoLibro t WHERE t.tplPropietario = :tplPropietario")})
public class TipoLibro implements Serializable {

    @Column(name = "TplTomo")
    private Short tplTomo;
    @OneToMany(mappedBy = "tplId")
    private List<TipoTramite> tipoTramiteList;

    private static final long serialVersionUID = 1L;
    public static final String LISTAR_TODOS = "TipoLibro.findAll";
    public static final String LISTAR_PORID = "TipoLibro.findByTplId";
    public static final String BUSCAR_POR_DESC = "TipoLibro.findByTplDescripcion";
    public static final String BUSCAR_POR_DESC_EDITAR = "TipoLibro.findByTplDescripcionEditar";
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TplId")
    private Long tplId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "TplDescripcion")
    private String tplDescripcion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "TplUser")
    private String tplUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "TplFHR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tplFHR;
    @Column(name = "TplGravamen")
    private Boolean tplGravamen;
    @Column(name = "TplPropietario")
    private Boolean tplPropietario;
    @Size(max = 10)
    @Column(name = "TplCodigo")
    private String tplCodigo= " ";
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tplId")
    private List<Tomo> tomoList;

    public TipoLibro() {
    }

    public TipoLibro(Long tplId) {
        this.tplId = tplId;
    }

    public TipoLibro(Long tplId, String tplDescripcion, String tplUser, Date tplFHR, short tplTomo) {
        this.tplId = tplId;
        this.tplDescripcion = tplDescripcion;
        this.tplUser = tplUser;
        this.tplFHR = tplFHR;
        this.tplTomo = tplTomo;
    }

    public Long getTplId() {
        return tplId;
    }

    public void setTplId(Long tplId) {
        this.tplId = tplId;
    }

    public String getTplDescripcion() {
        return tplDescripcion;
    }

    public void setTplDescripcion(String tplDescripcion) {
        this.tplDescripcion = tplDescripcion;
    }

    public String getTplUser() {
        return tplUser;
    }

    public void setTplUser(String tplUser) {
        this.tplUser = tplUser;
    }

    public Date getTplFHR() {
        return tplFHR;
    }

    public void setTplFHR(Date tplFHR) {
        this.tplFHR = tplFHR;
    }


    public Boolean getTplGravamen() {
        return tplGravamen;
    }

    public void setTplGravamen(Boolean tplGravamen) {
        this.tplGravamen = tplGravamen;
    }

    public String getTplCodigo() {
        return tplCodigo;
    }

    public void setTplCodigo(String tplCodigo) {
        this.tplCodigo = tplCodigo;
    }

    public Boolean getTplPropietario() {
        return tplPropietario;
    }

    public void setTplPropietario(Boolean tplPropietario) {
        this.tplPropietario = tplPropietario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tplId != null ? tplId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoLibro)) {
            return false;
        }
        TipoLibro other = (TipoLibro) object;
        if ((this.tplId == null && other.tplId != null) || (this.tplId != null && !this.tplId.equals(other.tplId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.TipoLibro[ tplId=" + tplId + " ]";
    }

    @XmlTransient
    public List<Tomo> getTomoList() {
        return tomoList;
    }

    public void setTomoList(List<Tomo> tomoList) {
        this.tomoList = tomoList;
    }

    public Short getTplTomo() {
        return tplTomo;
    }

    public void setTplTomo(Short tplTomo) {
        this.tplTomo = tplTomo;
    }

    @XmlTransient
    public List<TipoTramite> getTipoTramiteList() {
        return tipoTramiteList;
    }

    public void setTipoTramiteList(List<TipoTramite> tipoTramiteList) {
        this.tipoTramiteList = tipoTramiteList;
    }

}
